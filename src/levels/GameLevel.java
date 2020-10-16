package levels;
import java.awt.Color;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.PauseScreenAnimation;
import arkanoidgame.Counter;
import arkanoidgame.GameEnvironment;
import arkanoidgame.LivesIndicator;
import arkanoidgame.Paddle;
import arkanoidgame.ScoreIndicator;
import arkanoidgame.ScoreTrackingListener;
import arkanoidgame.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.BallRemover;
import collision.BlockRemover;
import collision.Collidable;
import geometry.Line;
import geometry.Point;
import sprites.Ball;
import sprites.Block;
import sprites.Colors;
import sprites.Sprite;
import sprites.SpriteCollection;

/**
 * The Game class is responsible to hold the sprites & collidables, and in charge of the animation.
 */
public class GameLevel implements Animation {
    // Declare the members of the class.
    private DrawSurface drawSurface;
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private AnimationRunner runner;
    private boolean running;
    private LevelInformation levelInformation;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter gameScore;
    private Counter numberOfLives;
    private Paddle paddle;
    private KeyboardSensor keyboard;

    // Set some sizes as constants.
    public static final int SURFACE_WIDTH = 800;
    public static final int SURFACE_HEIGHT = 600;
    public static final int SIDES_BORDERS_WIDTH = 25;
    public static final int UP_DOWN_BORDERS_HEIGHT = 30;
    public static final int PADDLE_HEIGHT = 20;

    /**
     * Constructor.
     * @param levelInformation the information required to fully describe a level.
     * @param keyboard a keyboard sensor.
     * @param runner takes an animation object and runs it.
     * @param gameScore the current score of the game.
     * @param lives the current number of the lives in the game.
     */
    public GameLevel(LevelInformation levelInformation, KeyboardSensor keyboard,
        AnimationRunner runner, Counter gameScore, Counter lives) {
        this.runner = runner;
        // Get a draw surface to draw on.
        this.drawSurface = this.runner.getDrawSurface();
        this.levelInformation = levelInformation;
        this.blocksCounter = new Counter(levelInformation.numberOfBlocksToRemove());
        this.ballsCounter = new Counter(0);
        this.gameScore = gameScore;
        numberOfLives = lives;
        this.keyboard = keyboard;
        // Set the borders blocks (the same borders in each level).
        setBordersBlocks();
        // Set the info block at the top of the screen (and draw on it the name of the level).
        setInfo();
        // Set the lives indicator and the score indicator.
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.gameScore);
        this.sprites.addSprite(livesIndicator);
        this.sprites.addSprite(scoreIndicator);
    }

    /**
     * @return the game environment.
     */
    public GameEnvironment getGameEnvironment() {
        return this.environment;
    }

    /**
     * Add the given collidable to the game environment.
     * @param c the collidable object that should be added to the game environment.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add the given sprite to the game environment.
     * @param s the sprite that should be added to the Sprite collection of the game.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * @param c the collidable object that should be removed from the game environment.
     */
    public void removeCollidable(Collidable c) {
        this.getGameEnvironment().removeCollidable(c);
    }

    /**
     * @param s the sprite object that should be removed from Sprite collection of the game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSprites().remove(s);
    }

    /**
     * Create a block and add it to the game.
     * @param block the block that should be added to the game
     */
    public void addBlock(Block block) {
        // BlockRemover is in charge of removing blocks from the game, as well as counting the remaining blocks.
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        // stk is in charge of updating the score counter when blocks are being hit and removed.
        ScoreTrackingListener stk = new ScoreTrackingListener(this.gameScore);
        // Create a new block and add it to the game.
        block.addHitListener(blockRemover);
        block.addHitListener(stk);
        block.addToGame(this);
    }

    /**
     * Create a border block and add it to the game.
     * @param startPoint the start point of the block.
     * @param blockWidth the width of the block.
     * @param blockHeight the height of the block.
     * @param blockHits the number of hits of the block.
     * @param blockColor the color of the block.
     * @return the block added to the game.
     */
    private Block addBorderBlock(Point startPoint, int blockWidth,
        int blockHeight, int blockHits, Color blockColor) {
        Block borderBlock = new Block(startPoint, blockWidth, blockHeight, blockHits, blockColor);
        borderBlock.addToGame(this);
        return borderBlock;
    }

    /**
     * Set the blocks at the borders of the surface of the game.
     */
    private void setBordersBlocks() {
        // The number of hits that the ball should hit the borders blocks is 0.
        int blockHits = 0;
        Color bordersColor = new Color(255, 255, 204);
        // Set the left block border and add it to the game environment.
        addBorderBlock(new Point(0, 0), SIDES_BORDERS_WIDTH, SURFACE_HEIGHT, blockHits, bordersColor);
        // Set the right block border and add it to the game environment.
        addBorderBlock(new Point(SURFACE_WIDTH - SIDES_BORDERS_WIDTH, 0), SIDES_BORDERS_WIDTH,
            SURFACE_HEIGHT, blockHits, bordersColor);
        // Set the up block border and add it to the game environment.
        addBorderBlock(new Point(0, 0), SURFACE_WIDTH, UP_DOWN_BORDERS_HEIGHT, blockHits, bordersColor);
        // Set the down block border (the killer block) and add it to the game environment.
        Block killerBlock = addBorderBlock(new Point(0, SURFACE_HEIGHT), SURFACE_WIDTH, UP_DOWN_BORDERS_HEIGHT,
            blockHits, bordersColor);
        // Add a ball remover to the down block border.
        BallRemover ballRemover = new BallRemover(this);
        killerBlock.addHitListener(ballRemover);
        killerBlock.addToGame(this);
    } // setBordersBlocks

    /**
     * Set the info block at the top of the screen.
     */
    private void setInfo() {
        Color infoColor = new Color(255, 255, 204);
        Sprite info = new Block(new Point(0, 0), this.drawSurface.getWidth(),
                35, 0, infoColor);
        info.addToGame(this);
        // The level name that is displayed on the info block.
        LevelNameIndicator levelName = new LevelNameIndicator(this.levelInformation.levelName());
        levelName.addToGame(this);
    }

    /**
     * Set the balls of the current turn.
     */
    private void setBalls() {
        int topPaddle = 10;
        Point ballCenter = new Point((float)(SURFACE_WIDTH / 2), SURFACE_HEIGHT - PADDLE_HEIGHT - topPaddle);
        int ballsRadius = 5;
        Colors colors = new Colors();
        for (int i = 0; i < this.levelInformation.numberOfBalls(); i++) {
            addBall(ballCenter, ballsRadius, colors.setRainbowColor(-1),
                this.levelInformation.initialBallVelocities().get(i));
        }
    }

    /**
     * Set the paddle of the current turn.
     */
    private void setPaddle() {
        // Create the paddle.
        double paddleWidth = this.levelInformation.paddleWidth();
        Point paddleStartPoint = new Point((float)(SURFACE_WIDTH / 2) - paddleWidth / 2,
            SURFACE_HEIGHT - PADDLE_HEIGHT);
        Colors colors = new Colors();
        this.paddle = new Paddle(paddleStartPoint, paddleWidth, PADDLE_HEIGHT,
            colors.setRainbowColor(-1), this.levelInformation.paddleSpeed());
        paddle.setKeyboardSensor(this.keyboard);
        // The line of the range that the paddle can move in it.
        Line paddleRange = new Line(new Point(SIDES_BORDERS_WIDTH, 0),
            new Point(SURFACE_WIDTH - SIDES_BORDERS_WIDTH, 0));
        paddle.setMovementRange(paddleRange);
        // Set the speed of the paddle.
        paddle.setSpeed(this.levelInformation.paddleSpeed());
        // Add the paddle to the game.
        paddle.addToGame(this);
    }

    /**
     * Add a ball to the game.
     * @param position the center point of the ball.
     * @param radius the radius of the ball.
     * @param ballColor the color of the ball.
     * @param ballVelocity the velocity of the ball.
     */
    private void addBall(Point position, int radius, Color ballColor, Velocity ballVelocity) {
        Ball ball = new Ball(position, radius, ballColor);
        ball.setVelocity(ballVelocity);
        ball.addToGame(this);
        ball.setGameEnvironment(this.environment);
    }

    /**
     * Set the game blocks and add them to the game.
     */
    public void setGameBlocks() {
        for (int i = 0; i < levelInformation.blocks().size(); i++) {
            addBlock(levelInformation.blocks().get(i));
        }
    }

    /**
     * Initialize a new game: set the game blocks and add them to the game, and also initialize the balls counter.
     */
    public void initialize() {
        setGameBlocks();
        this.sprites.getSprites().add(0, levelInformation.getBackground());
        this.ballsCounter = new Counter(this.levelInformation.numberOfBalls());
      } // initialize

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     *  Run the game - start the animation loop that goes over all the sprites and calls drawOn and timePassed methods
     *  on each Sprite.
     */
    public void playOneTurn() {
        setPaddle();
        setBalls();
        this.ballsCounter = new Counter(this.levelInformation.numberOfBalls());
        CountdownAnimation countdownAnimation = new CountdownAnimation(2.0, 3, this.sprites);
        this.runner.run(countdownAnimation);
        // Use the runner to run the current animation, which is one turn of the game.
        this.running = true;
        this.runner.run(this);

        // If the turn is over and the number of blocks is > 1, it means that the player lost, so decrease the number
        // of the lives by 1.
        if (this.getBlocksNumber() > 0) {
            this.numberOfLives.decrease(1);
        }
        // Remove the paddle since the turn is over.
        this.removeSprite(paddle);
        this.removeCollidable(paddle);

    } // playOneTurn

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        int winningScore = 100;
        this.sprites.drawAllOn(d);
        // Notify all the sprites that time has passed (change the state of the sprites in each frame).
        this.sprites.notifyAllTimePassed(dt);
        Tools helperFunctions = new Tools();
        if (this.keyboard.isPressed("p")) {
            helperFunctions.runStoppableAnimation(this.runner, new PauseScreenAnimation(), "c");

         }
        if (this.ballsCounter.getValue() == 0) {
            this.running = false;
        }
        // Clearing an entire level (destroying all the blocks) is worth 100 points.
        if (this.blocksCounter.getValue() == 0) {
            this.gameScore.increase(winningScore);
            this.running = false;
        }
    }

    /**
     * @return the current number of blocks.
     */
    public int getBlocksNumber() {
        return this.blocksCounter.getValue();
    }

    /**
     * @return the current number of lives.
     */
    public int getLivesNumber() {
        return this.numberOfLives.getValue();
    }

    /**
     * @return the counter of the balls.
     */
    public Counter getBallsCounter() {
        return this.ballsCounter;
    }

} // class GameLevel
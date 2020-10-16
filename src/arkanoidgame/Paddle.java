package arkanoidgame;
import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import levels.GameLevel;
import sprites.Ball;
import sprites.Colors;
import sprites.Sprite;

/**
 * The Paddle class represents the paddle that is used by the player. It's a rectangle that is controlled by the arrow
 * keys, and moves according to the player key presses. Class Paddle implements the Sprite and Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {
    // Declare the members of the class.
    private Line movementRange;  // The range that the paddle can move in.
    private KeyboardSensor keyboard;
    private Rectangle rectangle;
    private java.awt.Color color;
    // The change that the paddle makes in a case of a key pressing to the left or right.
    private int paddleSpeed;

    /**
     * Constructor.
     * @param location the location where the paddle starts.
     * @param width the width of the paddle.
     * @param height the height of the paddle.
     * @param color the color of the paddle.
     * @param paddleSpeed the speed of the paddle.
     */
    public Paddle(Point location, double width, double height,
        Color color, int paddleSpeed) {
        this.rectangle = new Rectangle(location, width, height);
        this.color = color;
        this.paddleSpeed = paddleSpeed;
    }

    /**
     * @param speed the speed that should be set to the paddle.
     */
    public void setSpeed(int speed) {
        this.paddleSpeed = speed;
    }

    /**
     * @param paddleColor the color that should be set to the paddle.
     */
    public void setColor(Color paddleColor) {
        this.color = paddleColor;
    }

    /**
     * @param keyboardS a KeyboardSensor that is used to detect the key presses.
     */
    public void setKeyboardSensor(KeyboardSensor keyboardS) {
        this.keyboard = keyboardS;
    }

    /**
     * @param range the range that the paddle can move in.
     */
    public void setMovementRange(Line range) {
        this.movementRange = range;
    }

    /**
     * @param d the movement that the paddle should make in the x-axis.
     */
    private void changePaddlePosition(double d) {
        Point currentPosition = this.rectangle.getUpperLeft();
        // Update the x value of the start point of the rectangle.
        this.rectangle.setUpperLeft(new Point(currentPosition.getX() + d, currentPosition.getY()));
    }

    /**
     * @return true if the paddle is going to exceed from the right side, false otherwise.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    private boolean rightExceeding(double dt) {
        return this.getCollisionRectangle().getUpperEdge().end().getX() + paddleSpeed * dt
            > this.movementRange.end().getX();
    }

    /**
     * @return true if the paddle is going to exceed from the left side, false otherwise.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    private boolean leftExceeding(double dt) {
        return this.getCollisionRectangle().getUpperEdge().start().getX() - paddleSpeed * dt
            < this.movementRange.start().getX();
    }

    /**
     * Perform the movement of the paddle to the left side.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        this.changePaddlePosition((-paddleSpeed * dt));
        Colors colors = new Colors();
        this.setColor(colors.setRainbowColor(-1));
    }

    /**
     * Perform the movement of the paddle to the right side.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    public void moveRight(double dt) {
        this.changePaddlePosition((paddleSpeed * dt));
        Colors colors = new Colors();
        this.setColor(colors.setRainbowColor(-1));
    }

    /**
     * Notify the paddle that time has passed.
     * If the left or the right key are pressed, the paddle moves respectively.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY) && !leftExceeding(dt)) {
            moveLeft(dt);
        } else if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY) && !rightExceeding(dt)) {
            moveRight(dt);
        }
    }

    /**
     * Draw the paddle on the screen.
     * @param d a surface you can draw on.
     */
    public void drawOn(DrawSurface d) {
        // Find the x and y values of the upper left, the width and the height of the rectangle.
        int upperLeftX = (int) this.rectangle.getUpperLeft().getX();
        int upperLeftY = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        // Draw the rectangle shape of the block.
        d.setColor(this.color);
        d.fillRectangle(upperLeftX, upperLeftY, width, height);
    }

    /**
     * @return the "collision shape" of the object.
     */
    public Rectangle getCollisionRectangle() {
        return (this.rectangle);
    }

    /**
     * @param collisionPoint the collision point of the ball with the paddle.
     * @return the number of the region of the collision - the paddle has 5 equally-spaced regions,
     * the behavior of the ball's bounce depends on where it hits the paddle.
     */
    private int findPaddleRegion(Point collisionPoint) {
        Rectangle shapeOfPaddle = this.getCollisionRectangle();
        // Divide the paddle to 5 equally-spaced regions.
        double divisionLengthBy5 = shapeOfPaddle.getUpperEdge().length() / 5;
        double leftXPaddle = shapeOfPaddle.getUpperEdge().start().getX();
        // Return the number of the region of the collision.
        if (leftXPaddle <= collisionPoint.getX() && collisionPoint.getX() < leftXPaddle + divisionLengthBy5) {
            return 1;
        } else if (leftXPaddle + divisionLengthBy5 <= collisionPoint.getX()
                && collisionPoint.getX() < leftXPaddle + 2 * divisionLengthBy5) {
            return 2;
        } else if (leftXPaddle + 2 * divisionLengthBy5 <= collisionPoint.getX()
                && collisionPoint.getX() < leftXPaddle + 3 * divisionLengthBy5) {
            return 3;
        } else if (leftXPaddle + 3 * divisionLengthBy5 <= collisionPoint.getX()
                && collisionPoint.getX() < leftXPaddle + 4 * divisionLengthBy5) {
            return 4;
        }
        // Otherwise - the collision is in region 5.
        return 5;
    }

    /**
     * Set the velocity of the ball by the region of the collision of it with the paddle.
     * @param region the region of the collision with the ball.
     * @param currentVelocity the current velocity of the ball.
     * @return the new velocity after the collision.
     */
    private Velocity setVelocityByRegion(int region, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        // Find the speed of the ball, using Pythagoras theorem.
        double speed = Math.sqrt((dx * dx) + (dy * dy));
        // Return the new velocity by the region of the collision of the ball with the paddle.
        if (region == 1) {
            return Velocity.fromAngleAndSpeed(300, speed);
        }
        if (region == 2) {
            return Velocity.fromAngleAndSpeed(330, speed);
        }
        // If the ball hits the middle region (region 3), it should keep its horizontal direction and only change its
        // vertical direction.
        if (region == 3) {
            dy = -dy;
            return new Velocity(dx, dy);
        }
        if (region == 4) {
            return Velocity.fromAngleAndSpeed(30, speed);
        }
        return Velocity.fromAngleAndSpeed(60, speed);
    }

    /**
     * Perform the hit with the paddle.
     * @param hitter the Ball that's doing the hitting, and we save it in notifyHit.
     * @param collisionPoint the collision point with the paddle.
     * @param currentVelocity the current velocity of the ball.
     * @return the new velocity after the hitting, according to the region of the collision.
     * @param dt specifies the amount of seconds passed since the last call.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity, double dt) {
        int paddleRegion = findPaddleRegion(collisionPoint);
        return setVelocityByRegion(paddleRegion, currentVelocity);
        }

    /**
     * Add the paddle to the game by calling the appropriate game methods.
     * @param g the game that the paddle should be added to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

} // class Paddle
package sprites;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import arkanoidgame.Velocity;
import biuoop.DrawSurface;
import collision.Collidable;
import collision.HitListener;
import collision.HitNotifier;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import levels.GameLevel;

/**
 * The Block class represents a block that the ball can collide with.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    // Declare the members of the class.
    private Rectangle rectangle;
    private BlockBackground blockBackground = null;
    private Stroke stroke = null;
    // The number of hits that the ball should hit the block. Each hit decreases this number by 1.
    private int numOfHits;
    // A map that maps between the current number of hits of the block and the fit background.
    private Map<Integer, BlockBackground> hitsToBackground;
    private List<HitListener> hitListeners = new ArrayList<>();

    /**
     * Constructor (first type).
     * @param location the location of the start of the block.
     * @param width the width of the block.
     * @param height the height of the block
     * @param numOfHits the number of hits that the ball should hit the block, each hit decreases this number by 1.
     * @param blockColor the color of the block.
     */
    public Block(Point location, double width, double height, int numOfHits, Color blockColor) {
        this.rectangle = new Rectangle(location, width, height);
        this.numOfHits = numOfHits;
        this.blockBackground = new BlockColorBackground(blockColor);
        this.stroke = null;
        this.hitsToBackground = new TreeMap<>();
    }

    /**
     * Constructor (second type).
     * @param location the location of the start of the block.
     */
    public Block(Point location) {
        int initializedValue = 0;
        this.rectangle = new Rectangle(location, initializedValue, initializedValue);
        this.numOfHits = 1;
        this.hitsToBackground = new TreeMap<>();
    }

    /**
     * @param blockWidth the width to set to the block.
     */
    public void setWidth(int blockWidth) {
        this.rectangle.setWidth(blockWidth);
    }

    /**
     * @param blockHeight the height to set to the block.
     */
    public void setHeight(int blockHeight) {
        this.rectangle.setHeight(blockHeight);
    }

    /**
     * @param rec the rectangle shape to set to the block.
     */
    public void setRectangle(Rectangle rec) {
        this.rectangle = rec;
    }

    /**
     * @param blockStroke the stroke to set to the block.
     */
    public void setStroke(Stroke blockStroke) {
        this.stroke = blockStroke;
    }

    /**
     * @param hits the number of hits that the ball should hit the block.
     */
    public void setNumOfHits(int hits) {
        this.numOfHits = hits;
    }

    /**
     * @return the "collision shape" of the object.
     */
     public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

     /**
      * @return the number of the hits of the block.
      */
     public int getHitPoints() {
         return this.numOfHits;
     }

     /**
      * Decrease the number of the hits of the block by 1.
      */
     public void decreaseNumOfHits() {
         if (this.numOfHits > 1) {
            this.numOfHits = this.numOfHits - 1;
        } else if (this.numOfHits == 1) {
            numOfHits = 0;
        }
     }

     /**
      * Return a new velocity of the ball in a case of hitting the corners of a block.
      * @param collisionPoint the current collision with the block.
      * @param currentVelocity the current velocity activated on the block.
      * @return the new velocity of the ball
      */
    private Velocity collisionWithTheCorners(Point collisionPoint, Velocity currentVelocity) {
        Rectangle recBlock = this.rectangle;
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        boolean flag = false;
        // If the ball hits the left edge.
        if (recBlock.getLeftEdge().isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            dx = (-1) * Math.abs(dx);
            flag = true;
        }
        // If the ball hits the right edge.
        if (recBlock.getRightEdge().isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            dx = Math.abs(dx);
            flag = true;
        }
        // If the ball hits the upper edge.
        if (recBlock.getUpperEdge().isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            dy = (-1) * Math.abs(dy);
            flag = true;
        }
        // If the ball hits the lower edge.
        if (recBlock.getLowerEdge().isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            dy = Math.abs(dy);
            flag = true;
        }
        if (!flag) {
            return null;
        }
        return new Velocity(dx, dy);
    } // collisionWithTheCorners

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity, double dt) {
        // The hitter is the Ball that's doing the hitting. Save it in notifyHit.
        this.notifyHit(hitter);
        // Get the dx & dy values of the velocity.
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        // Get the lengths of the rectangle.
        Line leftEdge = this.getCollisionRectangle().getLeftEdge();
        Line rightEdge = this.getCollisionRectangle().getRightEdge();
        Line upperEdge = this.getCollisionRectangle().getUpperEdge();
        Line lowerEdge = this.getCollisionRectangle().getLowerEdge();
        Velocity newVelocityCorners = collisionWithTheCorners(collisionPoint, currentVelocity);
        // Decrease the number of the hits of the block.
        this.decreaseNumOfHits();
        if (newVelocityCorners != null) {
           return newVelocityCorners;
       }
        // If the ball collided with the left edge of the block.
        if (leftEdge.isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            newDx = (-1) * newDx;
        }
        // If the ball collided with the right edge of the block.
        if (rightEdge.isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            newDx = (-1) * newDx;
        }
        // If the ball collided with the upper edge of the block.
        if (upperEdge.isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            newDy = (-1) * newDy;
        }
        // If the ball collided with the lower edge of the block.
        if (lowerEdge.isInTheSegment(collisionPoint.getX(), collisionPoint.getY())) {
            newDy = (-1) * newDy;
        }
        return new Velocity(newDx, newDy);
    }

    /**
     * Whenever a hit() occurs, notify all the registered HitListener objects by calling their hitEvent.
     * @param hitter the Ball that's doing the hitting.
     */
    private void notifyHit(Ball hitter) {
        /*
         * Calling the removeHitListener or the addHitListener methods from inside the notifyHit method,
         * may cause an exception. For this reason, perform the iteration on a copy of hitListeners list instead.
         */
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event.
        for (HitListener hl : listeners) {
           hl.hitEvent(this, hitter);
        }
     }

    /**
     * @param hits the number of hits of the block to which a background should be added.
     * @param background the background that should be added to the block.
     */
    public void addBackgroundByHits(int hits, BlockBackground background) {
        this.hitsToBackground.put(hits, background);
    }

    /**
     * Draw the block on the screen.
     * @param d a surface you can draw on.
     */
    public void drawOn(DrawSurface d) {
        if (this.hitsToBackground.containsKey(this.numOfHits)) {
            this.blockBackground = this.hitsToBackground.get(this.numOfHits);
            this.blockBackground.draw(d, this.rectangle);
        } else if (this.blockBackground != null) {
            this.blockBackground.draw(d, this.rectangle);
        }
        // If the block has an outline, draw it.
        if (this.stroke != null) {
            this.stroke.draw(d, this.rectangle);
        }
    } // drawOn

    /**
     * Notify the block that time has passed.
     * @param dt specifies the number of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }

    /**
     * Add the block to the game, calling the appropriate game methods.
     * @param game the game that the block should be added to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * Remove the block from the Sprite collection.
     * @param game the game that the block should be removed from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * @return the list of the hit listeners of the block.
     */
    public List<HitListener> getHitListenersList() {
        return this.hitListeners;
    }

} // class Block
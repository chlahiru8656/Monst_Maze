public abstract class Character extends GameObject {
    public enum Direction { NONE, UP, DOWN, LEFT, RIGHT }

    protected int velocityX, velocityY;
    protected int speed = 8;
    protected Direction currentDirection = Direction.RIGHT;

    public Character(int x, int y) {
        super(x, y);
    }
    
    public abstract void move();

    public abstract void setDirection(Direction dir);
    public abstract Direction getOppositeDirection();

    public void updatePosition() {
        x += velocityX;
        y += velocityY;
    }
}
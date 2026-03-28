import java.awt.Image;
import java.util.Random;

public abstract class Monster extends Character {
    protected Image image;
    private Random random = new Random();

    public Monster(int x, int y, Image image) {
        super(x, y);
        this.image = image;
        move();
    }
    
    public abstract Image getImage();

    @Override
    public void move() {
        int dir = random.nextInt(4);
        if (dir == 0) setDirection(Direction.UP);
        else if (dir == 1) setDirection(Direction.DOWN);
        else if (dir == 2) setDirection(Direction.LEFT);
        else setDirection(Direction.RIGHT);
    }
    
    @Override
    public void setDirection(Direction dir) {
        this.currentDirection = dir;
        switch (dir) {
            case UP:    velocityX = 0; velocityY = -speed; break;
            case DOWN:  velocityX = 0; velocityY = speed;  break;
            case LEFT:  velocityX = -speed; velocityY = 0; break;
            case RIGHT: velocityX = speed; velocityY = 0;  break;
            case NONE:  velocityX = 0; velocityY = 0; break;
        }
    }

    @Override
    public Direction getOppositeDirection() {
        switch (currentDirection) {
            case UP:    return Direction.DOWN;
            case DOWN:  return Direction.UP;
            case LEFT:  return Direction.RIGHT;
            case RIGHT: return Direction.LEFT;
            default:    return Direction.NONE;
        }
    }
}
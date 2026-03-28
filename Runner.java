public class Runner extends Character {
    private Direction nextDirection = Direction.NONE;

    public Runner(int x, int y) {
        super(x, y);
    }

    public Direction getNextDirection() {
        return this.nextDirection;
    }

    public Direction getDirection() {
        return this.currentDirection;
    }

    @Override
    public void setDirection(Direction dir) {
        this.nextDirection = dir;
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

    @Override
    public void move() {
        switch (nextDirection) {
            case UP:
                velocityX = 0;
                velocityY = -speed;
                break;
            case DOWN:
                velocityX = 0;
                velocityY = speed;
                break;
            case LEFT:
                velocityX = -speed;
                velocityY = 0;
                break;
            case RIGHT:
                velocityX = speed;
                velocityY = 0;
                break;
            case NONE:
                break;
        }
  
        if (nextDirection != Direction.NONE) {
           this.currentDirection = nextDirection;
        }
    }
}
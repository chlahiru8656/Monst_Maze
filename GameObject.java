import java.awt.Rectangle;

public abstract class GameObject {

    protected int x, y; // position on the bord
    protected int width, height;

    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 32;
        this.height = 32;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
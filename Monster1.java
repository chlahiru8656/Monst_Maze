import java.awt.Image;

public class Monster1 extends Monster {
    
    public Monster1(int x, int y, Image image) {
        super(x, y, image);
    }
    
    @Override
    public Image getImage() {
        return this.image;
    }
}
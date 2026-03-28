import java.awt.Image;

public class Monster2 extends Monster {
    
    public Monster2(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public Image getImage() {
        return this.image;
    }
}
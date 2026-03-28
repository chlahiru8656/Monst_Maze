import java.awt.Image;

public class Monster3 extends Monster {
    
    public Monster3(int x, int y, Image image) {
        super(x, y, image);
    }

    @Override
    public Image getImage() {
        return this.image;
    }
}
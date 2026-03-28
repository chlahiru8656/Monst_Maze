import javax.swing.JFrame;
import java.awt.EventQueue;

public class Game extends JFrame {

    public Game() {
        initUI();
    }

    private void initUI() {
        add(new Board()); //add game board to the window
        setTitle("Monst-Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game ex = new Game();
            ex.setVisible(true);
        });
    }
}
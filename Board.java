import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements ActionListener {
    private final int tileSize = 32;
    private int boardWidth;
    private int boardHeight;
    private Timer timer;
    private Runner runner;
    private List<Monster> monsters;
    private boolean[][] walls;
    private boolean[][] coin;
    private int score = 0, lives = 3;
    private boolean inGame = true, gameWon = false;
    private boolean levelTransition = false;
    private List<Level> allLevels;
    private Level currentLevelData;
    private int currentLevelIndex = 0;
    private Point runnerStart;
    private List<Point> monsterStarts;
    private Image runnerImage, runnerUp, runnerDown, runnerLeft, runnerRight;
    private Image wallImage, coinImage, monster1Image, monster2Image, monster3Image;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);

        allLevels = new ArrayList<>();
        allLevels.add(new Level1());
        allLevels.add(new Level2());
        allLevels.add(new Level3());

        loadLevel(currentLevelIndex);
        timer = new Timer(50, this);
        timer.start();
    }

private void loadLevel(int levelIndex) {
    if (levelIndex >= allLevels.size()) {
        gameWon = true;
        inGame = false;
        return;
    }

    currentLevelData = allLevels.get(levelIndex);
    boardWidth = currentLevelData.getBoardWidth() * tileSize;
    boardHeight = currentLevelData.getBoardHeight() * tileSize;
    setPreferredSize(new Dimension(boardWidth, boardHeight));

    loadImages();
    loadMap();
}

    private void loadImages() {
        wallImage = new ImageIcon(currentLevelData.getWallImagePath()).getImage();
        coinImage = new ImageIcon(currentLevelData.getCoinImagePath()).getImage();
        runnerUp = new ImageIcon("images/runnerUp.png").getImage();
        runnerDown = new ImageIcon("images/runnerDown.png").getImage();
        runnerLeft = new ImageIcon("images/runnerLeft.png").getImage();
        runnerRight = new ImageIcon("images/runnerRight.png").getImage();
        runnerImage = runnerRight;
        monster1Image = new ImageIcon("images/monster1.png").getImage();
        monster2Image = new ImageIcon("images/monster2.png").getImage();
        monster3Image = new ImageIcon("images/monster3.png").getImage();
    }

    private void loadMap() {
        int rows = currentLevelData.getBoardHeight();
        int cols = currentLevelData.getBoardWidth();
        String[] currentMap = currentLevelData.getTileMap();

        walls = new boolean[rows][cols];
        coin = new boolean[rows][cols];
        monsters = new ArrayList<>();
        monsterStarts = new ArrayList<>();

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                char tile = currentMap[r].charAt(c);
                if (tile == 'X') {
                    walls[r][c] = true;
                } else if (tile == 'R') {
                    runnerStart = new Point(c*tileSize, r*tileSize);
                    runner = new Runner(runnerStart.x, runnerStart.y);
                } else if (tile == '1') {
                    Point start = new Point(c*tileSize, r*tileSize);
                    monsterStarts.add(start);
                    monsters.add(new Monster1(start.x, start.y, monster1Image));
                } else if (tile == '2') {
                    Point start = new Point(c*tileSize, r*tileSize);
                    monsterStarts.add(start);
                    monsters.add(new Monster2(start.x, start.y, monster2Image));    
                } else if (tile == '3') {
                    Point start = new Point(c*tileSize, r*tileSize);
                    monsterStarts.add(start);
                    monsters.add(new Monster3(start.x, start.y, monster3Image));
                } else if (tile == ' ') {
                    coin[r][c] = true;
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            drawBoard(g);
            if (levelTransition) {
                drawLevelTransitionScreen(g);
            }
        } else{
            drawEndScreen(g);
        }
    }

    private void drawBoard(Graphics g) {
        int rows = currentLevelData.getBoardHeight();
        int cols = currentLevelData.getBoardWidth();

        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (walls[r][c]) {
                    g.drawImage(wallImage, c*tileSize, r*tileSize, this);
                }
                if (coin[r][c]) {
                    g.drawImage(coinImage, c*tileSize + 14, r*tileSize + 14, this);
                }
            }
        }

        switch (runner.getDirection()) {
            case LEFT: runnerImage = runnerLeft; break;
            case RIGHT: runnerImage = runnerRight; break;
            case UP: runnerImage = runnerUp; break;
            case DOWN: runnerImage = runnerDown; break;
            default: break;
        }

        g.drawImage(runnerImage, runner.x, runner.y, this);
        for (Monster monster : monsters) {
            g.drawImage(monster.getImage(), monster.x, monster.y, this);
        }
        drawScore(g);
    }
    
    private void drawEndScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 48));
        String msg = gameWon ? "YOU WIN!": "GAME OVER";
        FontMetrics fm = getFontMetrics(g.getFont());
        g.drawString(msg, (boardWidth - fm.stringWidth(msg))/2, boardHeight/2);

        g.setColor(Color.white);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        String scoreMsg = "Your Score: " + score;
        fm = getFontMetrics(g.getFont());
        g.drawString(scoreMsg, (boardWidth - fm.stringWidth(scoreMsg))/2, boardHeight/2 + 20);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String restartMsg = "Press any key to play again..";
        fm = getFontMetrics(g.getFont());
        g.drawString(restartMsg, (boardWidth - fm.stringWidth(restartMsg))/2, boardHeight/2 + 40);
    }
    
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Score: " + score + "   Lives: " + lives + "   Level: " + (currentLevelIndex + 1), 10, 20);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!inGame || levelTransition) {
            repaint();
            return;
        }

        if (inGame) {
            moveRunner();
            moveMonsters();
            checkCollisions();
            checkLevelCompletion();
            repaint();
        }
    }

    private void checkLevelCompletion() {
        boolean levelFinished = true;
        int rows = currentLevelData.getBoardHeight();
        int cols = currentLevelData.getBoardWidth();
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                if (coin[r][c]) {
                    levelFinished = false;
                    break;
                }
            }
            if (!levelFinished) {
                break;
            }
        }
    
        if (levelFinished) {
            levelTransition = true;
        }
    }

    private void drawLevelTransitionScreen(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, boardWidth, boardHeight);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 40));
        String msg = "Level Finished!";
        FontMetrics fm = getFontMetrics(g.getFont());
        g.drawString(msg, (boardWidth - fm.stringWidth(msg))/2, boardHeight/2);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));
        String continueMsg = "Press any key for the next level..";
        fm = getFontMetrics(g.getFont());
        g.drawString(continueMsg, (boardWidth - fm.stringWidth(continueMsg))/2, boardHeight/2 + 20);
    }
    
    private void moveRunner() {
        if (runner.x % tileSize == 0 && runner.y % tileSize == 0) {
            int nextMoveX = runner.x;
            int nextMoveY = runner.y;
            switch (runner.getNextDirection()) {
                case UP:    nextMoveY -= tileSize; break;
                case DOWN:  nextMoveY += tileSize; break;
                case LEFT:  nextMoveX -= tileSize; break;
                case RIGHT: nextMoveX += tileSize; break;
                default: break;
            }
            if (runner.getNextDirection() != Runner.Direction.NONE && !isWall(nextMoveX, nextMoveY)) {
                runner.move();
            } else {
                if (isWall(runner.x + runner.velocityX, runner.y + runner.velocityY)) {
                    runner.velocityX = 0;
                    runner.velocityY = 0;
                }
            }
        }

        runner.updatePosition();
        int gridX = (runner.x + tileSize/2) / tileSize;
        int gridY = (runner.y + tileSize/2) / tileSize;
        if (gridY >= 0 && gridY < currentLevelData.getBoardHeight() && gridX >= 0 && gridX < currentLevelData.getBoardWidth() && coin[gridY][gridX]) {
            coin[gridY][gridX] = false;
            score+=1;
        }
    }
    
private void moveMonsters() {
    for (Monster monster : monsters) {
        if (monster.x % tileSize == 0 && monster.y % tileSize == 0) {
            boolean isStuck = isWall(monster.x + monster.velocityX, monster.y + monster.velocityY);
            if (isStuck) {
                List<Character.Direction> validDirections = new ArrayList<>();
                if (!isWall(monster.x, monster.y - tileSize)) validDirections.add(Character.Direction.UP);
                if (!isWall(monster.x, monster.y + tileSize)) validDirections.add(Character.Direction.DOWN);
                if (!isWall(monster.x - tileSize, monster.y)) validDirections.add(Character.Direction.LEFT);
                if (!isWall(monster.x + tileSize, monster.y)) validDirections.add(Character.Direction.RIGHT);

                Character.Direction opposite = monster.getOppositeDirection();
                if (validDirections.size() > 1) {
                    validDirections.remove(opposite);
                }

                if (!validDirections.isEmpty()) {
                    int choice = new Random().nextInt(validDirections.size());
                    monster.setDirection(validDirections.get(choice));
                }
            }
        }
        monster.updatePosition();
    }
}

    private boolean isWall(int x, int y) {
        if (x<0 || x>boardWidth - tileSize || y<0 || y>boardHeight - tileSize) return true;
        int gridX = x/tileSize;
        int gridY = y/tileSize;
        if (walls[gridY][gridX]) return true;
        if (walls[(y + tileSize - 1) / tileSize][gridX]) return true;
        if (walls[gridY][(x + tileSize - 1) / tileSize]) return true;
        if (walls[(y + tileSize - 1) / tileSize][(x + tileSize - 1) / tileSize]) return true;
        return false;
    }

    private void checkCollisions() {
        int runnerTileX = runner.x / tileSize;
        int runnerTileY = runner.y / tileSize;
        for (Monster monster : monsters) {
            int monsterTileX = monster.x / tileSize;
            int monsterTileY = monster.y / tileSize;
            if (runnerTileX == monsterTileX && runnerTileY == monsterTileY) {
                lives--;
                if (lives <= 0) {
                    inGame = false;
                } else {
                    runner.x = runnerStart.x;
                    runner.y = runnerStart.y;
                    for (int i = 0; i < monsters.size(); i++) {
                        monsters.get(i).x = monsterStarts.get(i).x;
                        monsters.get(i).y = monsterStarts.get(i).y;
                    }
                }
                break;
            }
        }
    }

    private void restartGame() {
        score = 0;
        lives = 3;
        currentLevelIndex = 0;
        gameWon = false;
        levelTransition = false;
        loadLevel(currentLevelIndex);
    
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.pack();
            topFrame.setLocationRelativeTo(null);
        }

        inGame = true;
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!inGame){
                restartGame();
                return;
            }

            if (levelTransition){
                currentLevelIndex++;
                loadLevel(currentLevelIndex);
                levelTransition = false;

                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(Board.this);
                if (topFrame != null) {
                    topFrame.pack();
                    topFrame.setLocationRelativeTo(null);
                } 
               return;
            }

            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) {
                runner.setDirection(Character.Direction.LEFT); 
            } else if (key == KeyEvent.VK_RIGHT) { 
                runner.setDirection(Character.Direction.RIGHT);
            } else if (key == KeyEvent.VK_UP) { 
                runner.setDirection(Character.Direction.UP); 
            } else if (key == KeyEvent.VK_DOWN) { 
                runner.setDirection(Character.Direction.DOWN); 
            }
        }
    }
}
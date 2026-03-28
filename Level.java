public abstract class Level {

    public abstract int getBoardWidth();
    public abstract int getBoardHeight();
    public abstract String[] getTileMap();
    public abstract String getWallImagePath();
    public abstract String getCoinImagePath();

    public int getMonsterSpeed() {
        return 8;
    }
}
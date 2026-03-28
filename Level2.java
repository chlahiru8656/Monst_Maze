public class Level2 extends Level {
    private final String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X                 X",
        "X XXXXX XXXXX XXX X",
        "X     X  R  X     X",
        "XXX X X XXX XXX X X",
        "X   X     X   X X X",
        "X X XXX XXX XXX X X",
        "X X   X     X   X X",
        "X1XXX XXXXX X XXX X",
        "X                 X",
        "X XXX XXXXX XXX XXX",
        "X   X   X X X   X X",
        "X X X X X   X X X X",
        "X X   X   X   X2  X",
        "XXXXXXXXXXXXXXXXXXX"
    };

    @Override
    public int getBoardWidth(){ 
        return 19; 
    }

    @Override
    public int getBoardHeight(){ 
        return 15; 
    }

    @Override
    public String[] getTileMap(){ 
        return tileMap; 
    }

    @Override
    public String getWallImagePath(){ 
        return "images/shrub.png"; 
    }

    @Override
    public String getCoinImagePath(){ 
        return "images/coin.png"; 
    }
}
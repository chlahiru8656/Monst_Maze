public class Level1 extends Level {
    private final String[] tileMap = {
        "XXXXXXXXXXXX",
        "X R        X",
        "X XXXX X XXX",
        "X    X X   X",
        "X XX    X XX",
        "X X  XX X XX",
        "X XX XX X  X",
        "X XX XX   XX",
        "X XX X  X XX",
        "X  1   XX  X",
        "XXXXXXXXXXXX"
    };

    @Override
    public int getBoardWidth(){ 
        return 12;
    }

    @Override
    public int getBoardHeight(){ 
        return 11;
    }

    @Override
    public String[] getTileMap(){ 
        return tileMap;
    }

    @Override
    public String getWallImagePath(){ 
        return "images/brick.png";
    }

    @Override
    public String getCoinImagePath(){ 
        return "images/coins.png";
    }
}
public class Level3 extends Level {
    private final String[] tileMap = {
        "XXXXXXXXXXXXXXXXXXX",
        "X     XX   X      X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XX X XXXX XXXX X XX",
        "XX X X 2     X X XX",
        "XX X X XXXXX X XXXX",
        "X        1        X",
        "XXXX X XXXXX X XX X",
        "XXXX X       X    X",
        "XXXX X XXXXX X XX X",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     R     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X 3               X",
        "XXXXXXXXXXXXXXXXXXX"
    };

    @Override
    public int getBoardWidth(){ 
        return 19; 
    }

    @Override
    public int getBoardHeight(){ 
        return 21; 
    }

    @Override
    public String[] getTileMap(){ 
        return tileMap; 
    }

    @Override
    public String getWallImagePath(){ 
        return "images/brick2.png"; 
    }

    @Override
    public String getCoinImagePath(){ 
        return "images/dollar.png";
    }
}
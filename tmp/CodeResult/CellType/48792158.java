package fr.jsimforest.app.model;

public enum CellType
{
    EMPTY (0),
    SAPLING (1),
    BUSH (2),
    TREE (3),
    ASH (4),
    INFESTED (5),
    FIRE (6);
    
    private final int i;   

    CellType(int i) {
        this.i = i;
    }
    
    public static CellType fromInteger(int x) {
        switch(x) {
        case 0:
            return EMPTY;
        case 1:
            return SAPLING;
        case 2:
            return BUSH;
        case 3:
            return TREE;
        case 4:
            return ASH;
        case 5:
            return INFESTED;
        case 6:
            return FIRE;
        }
        return EMPTY;
    }
    
    public int i() { 
        return i; 
    }
    
}


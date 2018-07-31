package tsyban.tet.logic.shapes;

import tsyban.tet.logic.Position;

public enum ShapeType {

    BOX() {
	public static final int CENTER = 0;
	@Override
	public int[][] getIndexes(Position position) {
	    return null;
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j}  , {i,j+1},
		    {i+1,j}, {i+1,j+1}
	    };
	    return initIndexes;
	}
    },

    LINE() {
	public static final int CENTER = 1;
	@Override
	public int[][] getIndexes(Position position) {
	    switch (position) {
	    case CW_90:
		return new int[][] { { -1, +1 }, { 0, 0 }, { +1, -1 }, { +2, -2 } };
	    default:
		return new int[][] { { +1, -1 }, { 0, 0 }, { -1, +1 }, { -2, +2 } };
	    }
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j-1}, {i,j},
		    {i,j+1}, {i,j+2}
	    };
	    return initIndexes;
	}
    },

    T_SHAPED() {
	public static final int CENTER = 1;
	@Override
	public int[][] getIndexes(Position position) {
	    switch (position) {
	    case CW_90:
		return new int[][] { { +1, +1 }, { 0, 0 }, { +1, -1 }, { -1, -1 } };
	    case CW_180:
		return new int[][] { { +1, -1 }, { 0, 0 }, { -1, -1 }, { -1, +1 } };
	    case CW_270:
		return new int[][] { { -1, -1 }, { 0, 0 }, { -1, +1 }, { +1, +1 } };
	    default:
		return new int[][] { { -1, +1 }, { 0, 0 }, { +1, +1 }, { +1, -1 } };
	    }
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j}    , {i+1,j},
		    {i+1,j+1}, {i+2,j}
	    };
	    return initIndexes;
	}
    },

    S_SHAPED() {
	public static final int CENTER = 1;
	@Override
	public int[][] getIndexes(Position position) {
	    switch (position) {
	    case CW_90:
		return new int[][] { { +1, +1 }, { 0, 0 }, { +1, -1 }, { 0, -2 } };
	    case CW_180:
		return new int[][] { { -1, -1 }, { 0, 0 }, { -1, +1 }, { 0, +2 } };
	    case CW_270:
		return new int[][] { { +1, +1 }, { 0, 0 }, { +1, -1 }, { 0, -2 } };
	    default:
		return new int[][] { { -1, -1 }, { 0, 0 }, { -1, +1 }, { 0, +2 } };
	    }
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j}    , {i+1,j},
		    {i+1,j+1}, {i+2,j+1}
	    };
	    return initIndexes;
	}
    },

    S_FLIPPED_SHAPED() {
	public static final int CENTER = 2;
	@Override
	public int[][] getIndexes(Position position) {
	    switch (position) {
	    case CW_90:
		return new int[][] { { +1, -1 }, { +1, +1 }, { 0, 0 }, { 0, +2 } };
	    case CW_180:
		return new int[][] { { -1, +1 }, { -1, -1 }, { 0, 0 }, { 0, -2 } };
	    case CW_270:
		return new int[][] { { +1, -1 }, { +1, +1 }, { 0, 0 }, { 0, +2 } };
	    default:
		return new int[][] { { -1, +1 }, { -1, -1 }, { 0, 0 }, { 0, -2 } };
	    }
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j+1}  , {i+1,j},
		    {i+1,j+1}, {i+2,j}
	    };
	    return initIndexes;
	}
    },

    L_SHAPED() {
	public static final int CENTER = 1;
	@Override
	public int[][] getIndexes(Position position) {
	    switch (position) {
	    case CW_90:
		return new int[][] { { +1, +1 }, { 0, 0 }, { -1, -1 }, { 0, -2 } };
	    case CW_180:
		return new int[][] { { +1, -1 }, { 0, 0 }, { -1, +1 }, { -2, 0 } };
	    case CW_270:
		return new int[][] { { -1, -1 }, { 0, 0 }, { +1, +1 }, { 0, +2 } };
	    default:
		return new int[][] { { -1, +1 }, { 0, 0 }, { +1, -1 }, { +2, 0 } };
	    }
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j}  , {i+1,j},
		    {i+2,j}, {i+2,j+1}
	    };
	    return initIndexes;
	}
    },

    L_FLIPPED_SHAPED() {
	public static final int CENTER = 1;
	@Override
	public int[][] getIndexes(Position position) {

	    switch (position) {
	    case CW_90:
		return new int[][] { { -1, +1 }, { 0, 0 }, { 0, -2 }, { +1, -1 } };
	    case CW_180:
		return new int[][] { { +1, +1 }, { 0, 0 }, { -2, 0 }, { -1, -1 } };
	    case CW_270:
		return new int[][] { { +1, -1 }, { 0, 0 }, { 0, +2 }, { -1, +1 } };
	    default:
		return new int[][] { { -1, -1 }, { 0, 0 }, { +2, 0 }, { +1, +1 } };
	    }
	}
	@Override
	public int center() {
	    return CENTER;
	}
	@Override
	public int[][] getInitialPosition(int i, int j) {
	    int[][] initIndexes ={
		    {i,j+1}, {i+1,j+1},
		    {i+2,j}, {i+2,j+1}
	    };
	    return initIndexes;
	}
    };
    
    public abstract int[][] getInitialPosition(int i, int j);
    public abstract int[][] getIndexes(Position position);
    public abstract int center();
}

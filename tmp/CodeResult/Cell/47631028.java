/*
 * @(#)Cell.java
 */
package games.Battle.shared.sys;

/**
 * A base class for an individual cell (square) in the game board. Both the
 * client and server derive more specialized versions of cell to store the
 * infomration that is of interest to them.
 *
 * @version 1.00
 * @author  Jay Steele
 * @author  Alex Nicolaou
 */

public class Cell
{
	/**
	 * A symbols symbol is used to indicate who is in this cell.
	 */
	int occupancy = Symbols.UNOCCUPIED;
	/**
	 * An int is used to represent cities to allow for partial destruction
	 * of cities in the future.
	 */
	int city = 0;
	/**
	 * A boolean array that indicates the presence of pipes in each compass
	 * point direction.
	 */
	boolean pipe[] = {false, false, false, false};
	/**
	 * The height of the terrain in this square.
	 */
	int terrain = 1;
	/**
	 * The number of troops in the cell.
	 */
	int troops = 0;
	/**
	 * The row and column where this cell is located on the game board.
	 */
	int row, col;
	/**
	 * A flag that indicates if the contents of the cell have changed.
	 */
	boolean modified = false;
	/**
	 * A flag to indicate if the terrain in the cell has changed (not 
	 * yet used).
	 */
	boolean terrainModified = false;

	/**
	 * construct a general battle game cell
	 * @param r the row
	 * @param c the column
	 */
	public Cell(int r, int c) {
		row = r;
		col = c;
	}

	/**
	 * set the occupancy
	 */
	public final void setOccupancy(int occ) {
		if (occupancy != occ) {
			modified = true;
			occupancy = occ; 
		}
	}
	/**
	 * set the city construction amount 
	 * @param amt the amount of city that is here. Always complete 
	 * cities for now.
	 */
	public final void setCity(int amt) { 
		if (city != amt) {
			terrainModified = true;
			city = amt; 
		}
	}
	/**
	 * set or clear a pipe in the given direction.
	 * @param dir the direction; 0 is north. defines are in Symbols
	 * @param state the state of the pipe, on or off.
	 */
	public final void setPipe(int dir, boolean state) {
		if (pipe[dir] != state) {
			modified = true;
			pipe[dir] = state; 
		}
	}

	/**
	 * clear all the pipes in this cell.
	 */
	public final void clearPipes() { 
		modified = true;
		for (int dir = 0; dir < pipe.length; dir++)
			pipe[dir] = false;
	}

	/**
	 * set the height of the terrain in this cell.
	 * @param level the height of the land.
	 */
	public final void setTerrain(int level)	{ 
		if (terrain != level) {
			terrainModified = true;
			terrain = level; 
		}
	}

	/**
	 * set how many troops are in this cell.
	 * @param  num the number of troops.
	 */
	public final void setTroops(int num) { 
		if (num > Symbols.MAX_SERVER_TROOPS + 5 || num < -100) {
			// FIXME
			System.out.println("error set troops to num=" + num);
            try {

                throw new Exception("too many troops!");

            }

            catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
			//num = Symbols.MAX_SERVER_TROOPS;
		}
		if (troops != num) {
			modified = true;
			troops = num; 
		}
	}

	/**
	 * special method for setting pipes. the lower four bits in 
	 * mask indicate which pipes are on and which are off. bits
	 * 3, 2, 1, and 0 represent the north, east, south and west
	 * pipes, respectively
	 * <P>
	 * This method is convenient because of the way data is transmitted
	 * between client and server.
	 * @param mask the bitmask of pipes.
	 */
	public final void setPipes(int mask) {
		modified = true;
		for (int dir=0; dir<4; dir++) {
			if ((mask & Symbols.PIPE_MASK[dir]) != 0x0000) {
				setPipe(dir, true);
			} else {
				setPipe(dir, false);
			}
		}
	}

	/**
	 * return the occupancy
	 */
	public final int getOccupancy()				{ return occupancy; }
	/**
	 * return the city amount
	 */
	public final int getCity()					{ return city; }
	/**
	 * return the array of pipes.
	 */
	public final boolean[] getPipes()			{ return pipe; }
	/**
	 * return the state of the pipe in the direction given 
	 * @param dir the direction
	 */
	public final boolean getPipe(int dir)		{ return pipe[dir]; }
	/**
	 * build a pipe mask from the current pipe state. The lower four
	 * bits of the returned int represent the state of each pipe.
	 */
	public final int getPipeMask() {
		int result = 0;
		for (int i=0; i<4; i++)
			if (pipe[i]) 
				result |= Symbols.PIPE_MASK[i];
		return result;
	}
	/**
	 * return the level of the terrain in this cell.
	 */
	public final int getTerrain()				{ return terrain; }
	/**
	 * return the number of troops in this cell
	 */
	public final int getTroops()				{ return troops; }
	/**
	 * return the row that this cell is in 
	 */
	public final int getRow()					{ return row; }
	/**
	 * return the column that this cell is in 
	 */
	public final int getCol()					{ return col; }

	/**
	 * return true if this cell's contents have been modified
	 */
	public final boolean isModified() 			{ return modified; }
	/**
	 * return true if this cell's terrain has been modified
	 */
	public final boolean isTerrainModified()	{ return terrainModified; }

	/**
	 * clear the contents modified flag
	 */
	public void clearModified()					{ modified = false; }
	/**
	 * clear the terrain modified flag
	 */
	public void clearTerrainModified()			{ terrainModified = false; }

	/**
	 * set the content modified flag
	 */
	public final void setModified()				{ modified = true; }
	/**
	 * set the terrain modified flag
	 */
	public final void setTerrainModified()		{ terrainModified = true; }

	/**
	 * return whether or not this cell is visible to a particuler player.
	 * this method should be overridden to create the effect of invisible
	 * cells.
	 */
	public boolean isVisible(int player) 	{ return true; }

	/**
	 * Make this cell a functioning city (i.e. a city with the maximum
	 * number of city segments.
	 */
	public void setCity() {
		setCity(Symbols.MAX_CLIENT_TROOPS);
	}
}

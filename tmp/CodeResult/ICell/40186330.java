package potentialgames.data;

public interface ICell {
	/**
	 * 
	 * @param plr new Player
	 * @param p new Pin
	 * @param a new Position for Pin
	 */
	void setCell(IPlayer plr, IPin p, int a);

	/**
	 * 
	 * @return returns the new Player
	 */
	IPlayer getPlayer();
	/**
	 * Sets a new Player on the field
	 * @param p the new IPlayer element on the field
	 */
	void setPlayer(IPlayer p);

	/**
	 * 
	 * @return returns the Pin on the Cell
	 */
	IPin getPin();

	/**
	 * 
	 * @param a new value for x
	 * @param b new value for b
	 */
	void setXY(int a, int b);

	/**
	 * 
	 * @return returns x value
	 */
	int getx();

	/**
	 * 
	 * @return returns y value
	 */
	int gety();
	/**
	 * 
	 * @param p new Pin
	 */
	void setPin(IPin p);
}

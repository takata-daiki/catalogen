package potentialgames.data;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @author Michael + Felix Creates a New Gamfield for MADN
 */
public class GameField implements IGameField{
	private ICell[] way;
	private List<Queue<IPin>> homes;
	private List<Queue<IPin>> targetArray;
	

	/**
	 * Create a new Gamefield!
	 * @param length Length of the Way
	 * @param numberOfPins Number of Pins for House
	 * @param numberOfPlayers Number of Players
	 */
	public GameField(int length, int numberOfPlayers) {
		way = new Cell[length];
		fillCells(way);
		homes = new LinkedList<Queue<IPin>>();
		fillQueues(numberOfPlayers);
		targetArray = new LinkedList<Queue<IPin>>();
		fillArrays(numberOfPlayers);
		
	}
	/**
	 * Get the logic way array
	 * @return array of cells
	 */
	public ICell[] getWay() {
		return way;
	}
	
	private void fillCells(ICell[] toFill){
		for(int i =0; i < toFill.length; i++){
			toFill[i] = new Cell();
		}
	}
	
	private void fillArrays(int playerAmount){
		for(int p = 0; p < playerAmount; p++){
			targetArray.add(new ArrayDeque<IPin>());
		}
	}
	private void fillQueues(int playerAmount){
		for(int p = 0; p < playerAmount; p++){
			homes.add(new ArrayDeque<IPin>());
		}
	}
	
	public Queue<IPin> getHome(int playerNumber){
		return homes.get(playerNumber);
	}	
	
	public Queue<IPin> getTarget(int playerNumber){
		return targetArray.get(playerNumber);
	}
}

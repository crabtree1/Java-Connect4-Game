import java.util.ArrayList;
import java.util.Observable;

/**
 * This class is a model in the MVC architecture for a connect 4 game. This class holds all the data 
 * for the game in an array of arrays of ints that are based on the following color scheme: 
 * 0 = white
 * 1 = red
 * 2 = yellow
 * 
 * 
 * @author Christopher Crabtree
 * @author Luke Cernetic
 * @version 1.0
 *
 */
public class Connect4Model extends Observable {
	
	/**
	 * The grid that holds the data of the current game state
	 */
	private ArrayList<ArrayList<Integer>> grid;
	
	/**
	 * Holds the int value of the current objects color
	 */
	private int player;
	
	/**
	 * This is the constructor for a connect4Model which builds the grid used to 
	 * hold the game state
	 */
	public Connect4Model() {
		grid = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<6; i++) {
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for(int j=0; j<7; j++) {
				temp.add(0);
			}
			grid.add(temp);
		}
		
	}
	
	/**
	 * This method sets the int representing the color of the current object
	 * 
	 * @param player: An int representing the color of the current object
	 */
	public void setPlayer(int player) {
		this.player = player;
	}
	
	/**
	 * This method returns the current gird containing the game state
	 * 
	 * @return An Array of Arrays of Integers representing the current game state
	 */
	public ArrayList<ArrayList<Integer>> getGrid() {
		return grid;
	}
	
	/**
	 * This method returns a move message based on the selected column
	 * 
	 * @param place: An int of the selected column
	 * @return a Connect4MoveMessage with the data of the current input
	 */
	public Connect4MoveMessage getMessage(int place) {
		return new Connect4MoveMessage(0, place, this.player);
	}
	
	/**
	 * This method adds a piece to the game of a different color than the current color
	 * 
	 * @param place: An int of the selected column
	 * @param player: An int representing the color to set the selected piece
	 */
	public void addPiece(int place, int player) {
		for(int i=5; i>=0; i--) {
			if(grid.get(i).get(place) == 0) {
				grid.get(i).set(place, player);
				break;
			}
		}
		setChanged();
		notifyObservers(grid);
	}
	
	/**
	 * This method adds a piece to the game of the current objects color
	 * 
	 * @param place: An int of the selected column
	 */
	public void addPiece(int place) {
		for(int i=5; i>=0; i--) {
			if(grid.get(i).get(place) == 0) {
				grid.get(i).set(place, player);
				break;
			}
		}
		setChanged();
		notifyObservers(grid);
	}
	
	/**
	 * This method returns a boolean indicating if the selected guess is legal on the board
	 * 
	 * @param col: An int of the selected column
	 * @return A boolean indicating if the selection is valid
	 */
	public boolean isLegal(int col) {
		if (grid.get(0).get(col) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns a boolean indicating of the game is complete
	 * 
	 * @return A boolean indicating the completion of the game
	 */
	public boolean isComplete() {
		if(isBoardFilled() || hasWon() != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if the current grid is filled
	 * 
	 * @return A boolean indicating if the board is filled (no white spaces left)
	 */
	public boolean isBoardFilled() {
		for(int i=0; i<7; i++) {
			if(grid.get(0).get(i) == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method checks to see if the current object has won. In order to win, there must be 4 
	 * of the same color piece in a row, vertical, horizontal or either diagnoal. 
	 * 
	 * @return An int; 0 if not won, 1 if won
	 */
	public int hasWon() {
		for (int r = 0; r < grid.size(); r++) { // iterate rows, bottom to top
	        for (int c = 0; c < grid.get(r).size(); c++) { // iterate columns, left to right
	        	int player = grid.get(r).get(c);
	            if (player == 0)
	                continue; // don't check empty slots

	            if (c + 3 < grid.get(r).size() &&
	                player == grid.get(r).get(c + 1) && // look right
	                player == grid.get(r).get(c + 2) &&
	                player == grid.get(r).get(c + 3))
	                return player;
	            if (r + 3 < grid.size()) {
	                if (player == grid.get(r + 1).get(c) && // look up
	                    player == grid.get(r + 2).get(c) &&
	                    player == grid.get(r + 3).get(c))
	                    return player;
	                if (c + 3 < grid.get(r).size() &&
	                    player == grid.get(r + 1).get(c + 1) && // look up & right
	                    player == grid.get(r + 2).get(c + 2) &&
	                    player == grid.get(r + 3).get(c + 3))
	                    return player;
	                if (c - 3 >= 0 &&
	                    player == grid.get(r + 1).get(c - 1) && // look up & left
	                    player == grid.get(r + 2).get(c - 2) &&
	                    player == grid.get(r + 3).get(c - 3))
	                    return player;
	            }
	        }
	    }
	    return 0; // no winner found
	}
	
	/**
	 * This method prints the current game state to the console
	 * 
	 * @Override
	 */
	public String toString() {
		String toPrint = "-------------------------\n";
		for (int i = 0; i < grid.size(); i++) {
			toPrint = toPrint + "|  ";
			for (int j = 0; j < grid.get(i).size(); j++) {
				toPrint = toPrint + grid.get(i).get(j) + "  ";
			}
			toPrint = toPrint + "|\n";
		}
		toPrint = toPrint + "-------------------------";
		return toPrint;
		
	}

}

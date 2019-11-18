import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class Connect4Model extends Observable {
	private ArrayList<ArrayList<Integer>> grid;
	private int player;
	
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
	
	public void setPlayer(int player) {
		this.player = player;
	}
	
	public ArrayList<ArrayList<Integer>> getGrid() {
		return grid;
	}
	
	public void addPiece(int place, int player, boolean isClient) {
		for(int i=5; i>=0; i--) {
			if(grid.get(i).get(place) == 0) {
				Connect4MoveMessage message = new Connect4MoveMessage(i, place, player);
				if(isClient) {
				try {
					Connect4Client.setMessage(message);
					Connect4Client.callServer();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				} else if (!isClient) {
					Connect4Server.setMessage(message);
					try {
						Connect4Server.getMessage();
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
					}
				}
				
				break;
			}
		}
	}
	
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
	
	public boolean isLegal(int col) {
		if (grid.get(0).get(col) == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isComplete() {
		if(isBoardFilled() || hasWon() != 0) {
			return true;
		}
		return false;
	}
	
	public boolean isBoardFilled() {
		for(int i=0; i<7; i++) {
			if(grid.get(0).get(i) == 0) {
				return false;
			}
		}
		return true;
	}
	
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

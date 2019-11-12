import java.util.ArrayList;

public class Connect4Model {
	private ArrayList<ArrayList<Integer>> grid;
	
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
	
	public void addPiece(int place, int player) {
		for(int i=5; i>=0; i--) {
			if(grid.get(i).get(place) == 0) {
				grid.get(i).set(place, player);
			}
		}
	}
	
	public boolean isComplete() {
		if(isBoardFilled() || hasWon(1) || hasWon(2)) {
			return true;
		}
		return false;
	}
	
	public boolean hasWon(int player) {
		int count = 0;
		// Horizontal
		for(int i=0; i<6; i++) {
			for(int j=0; j<7; j++ ) {
				if(grid.get(i).get(j) == player ) {
					count++;
				} else {
					count = 0;
				}
				if(count == 4) {
					return true;
				}
			}
		}
		count = 0;
		// Vertical
		for(int i=0; i<7; i++) {
			for(int j=0; j<6; j++ ) {
				if(grid.get(j).get(i) == player ) {
					count++;
				} else {
					count = 0;
				}
				if(count == 4) {
					return true;
				}
			}
		}
		// Diagonal top-left -> bottom-rights
		for(int rowCount = 0; rowCount<3; rowCount++) {
			count = 0;
			int col,row;
			for(row = rowCount, col = 0; row<7 && col < 6; row++, col++) {
				if(grid.get(row).get(col) == player) {
					count++;
					if(count == 4) {
						return true;
					}
				} else {
					count = 0;
				}
			}
		}
		
		// Diagonal top-left -> bottom-right
		int rowCount = 0;
		for(int colCount = 1; colCount<2; rowCount++) {
			count = 0;
			int col,row;
			for(row = 0, col = colCount; row < 7 && col < 6; row++, col++) {
				if(grid.get(row).get(col) == player) {
					count++;
					if(count == 4) {
						return true;
					} else {
						count = 0;
					}
				}
			}
		return false;
	}
	}
	public boolean isBoardFilled() {
		for(int i=0; i<7; i++) {
			if(grid.get(0).get(i) == 0) {
				return false;
			}
		}
		return true;
	}
}

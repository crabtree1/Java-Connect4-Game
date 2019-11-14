import java.util.ArrayList;

public class Connect4Controller {

	private Connect4Model model;
	
	public Connect4Controller(Connect4Model model) {
		this.model = model;
	}
	
	public void addPiece(int place, int player) {
		model.addPiece(place, player);
	}
	
	public ArrayList<ArrayList<Integer>> getGrid() {
		return model.getGrid();
	}
	
	public boolean isLegal(int col) {
		return model.isLegal(col);
	}
	
	public int hasWon() {
		return model.hasWon();
	}
	
	public void createNewGame() {
		Connect4Model newModel = new Connect4Model();
		
		this.model = newModel;
	}
}

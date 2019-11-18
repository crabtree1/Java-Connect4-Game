import java.util.ArrayList;
import java.util.Random;

public class Connect4Controller {

	private Connect4Model model;
	
	public Connect4Controller(Connect4Model model) {
		this.model = model;
	}
	
	public void addPiece(int place, int player, boolean isClient) {
		model.addPiece(place, player, isClient);
		model.addPiece(place, player);
	}
	
	public void computerTurn() {
		Random rand = new Random();
		int selected = rand.nextInt(6);
		while(!this.isLegal(selected)) {
			selected = rand.nextInt(6);
		} 
		//this.addPiece(, player, false, true);
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
	
	public void setModel(Connect4Model newModel) {
		model = newModel;
	}

	public void addMessage(int column, int color) {
		model.addPiece(column, color);
	}
}

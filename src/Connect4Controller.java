import java.util.ArrayList;

public class Connect4Controller {

	private Connect4Model model;
	
	public Connect4Controller(Connect4Model model) {
		this.model = model;
	}
	
	public void addPiece(int place, int player, boolean isClient, boolean isClientServer) {
		if(isClientServer) {
			model.addPiece(place, player, isClient);
		}
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
	
	public void setModel(Connect4Model newModel) {
		model = newModel;
	}
}

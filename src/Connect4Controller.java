import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Connect4Controller {

	private Connect4Model model;
	private Connect4View view;
	private boolean gameOn = true;
	private boolean isClient = false;
	private boolean isHuman = true;
	private boolean isTurn = false;
	
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
		this.addPiece(selected, 1, false);
	}
	
	public void startGame() {
		while(gameOn) {
			
		}
	}
	
	public void updateClientStat(boolean isClient) {
		this.isClient = isClient;
	}
	
	public void setGameStatus(boolean gameStat) {
		gameOn = gameStat;
	}
	
	public void setPlayAs(boolean playAs) {
		this.isHuman = playAs;
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
	
	public boolean getTurn() {
		return this.isTurn;
	}

	public void addMessage(int column, int color) {
		model.addPiece(column, color);
	}
	
	public void addView(Connect4View view) {
		this.view = view;
	}
	
	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public void addPiece(int column, int color) {
		model.addPiece(column, color);
	}
	
	public boolean getClientStat() {
		return this.isClient;
	}
}

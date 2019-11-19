import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;

public class Connect4Controller {

	private Connect4Model model;
	private Connect4View view;
	private boolean isHuman = true;
	private boolean isTurn = false;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Connect4MoveMessage otherMessage;
	private Connect4Controller controller;
	
	public Connect4Controller(Connect4Model model) {
		this.model = model;
		this.controller = this;
	}
	
	public void initStreams(Socket socket) {
		 try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startTurn() {
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				otherMessage = null;
				try {
					otherMessage = (Connect4MoveMessage) ois.readObject();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							model.addPiece(otherMessage.getColumn(), otherMessage.getColor());
							if(model.hasWon() != 0) {
								view.showLost();
							} else if(controller.isHuman == false) {
								controller.computerTurn();
							}
						}
					});
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				controller.setTurn(true);
			}
		});
		inputThread.start();
	}
	
	public void humanTurn(int col) {
		this.model.addPiece(col);
		try {
			oos.writeObject(this.model.getMessage(col));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.startTurn();
	}
	
	public void computerTurn() {
		Random rand = new Random();
		int selected = rand.nextInt(6);
		while(!this.isLegal(selected)) {
			selected = rand.nextInt(6);
		} 
		model.addPiece(selected);
		try {
			oos.writeObject(this.model.getMessage(selected));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(this.model.hasWon() != 0) {
			this.view.showWon();
		}
		this.isTurn = false;
		this.startTurn();
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
	
	public void addView(Connect4View view) {
		this.view = view;
	}
	
	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public void addPiece(int column, int color) {
		model.addPiece(column, color);
	}
	
	public boolean isHuman() {
		return this.isHuman;
	}

	public void addPiece(int col) {
		model.addPiece(col);
	}
}

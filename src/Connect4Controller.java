import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;

/**
 * This class is a controller in an MVC architecture for a connect 4 game. This game is networked and
 * has options to run the game as a server or client. It also has the option to play against a computer
 * Opponent or a human opponent.This class holds methods that allow the view to interact indirectly with 
 * the model. This class is parameterized differently depending on if it is a server or client
 * 
 * 
 * @author Christopher Crabtree
 * @author Luke Cernetic
 * @version 1.0
 *
 */
public class Connect4Controller {

	/**
	 * Holds a reference to a model holding the data of the game
	 */
	private Connect4Model model;
	
	/**
	 * Holds a reference to a view of the game
	 */
	private Connect4View view;
	
	/**
	 * Indicates if the current object is playing as a human or as a computer
	 */
	private boolean isHuman = true;
	
	/**
	 * Indicates if it is the current object's turn
	 */
	private boolean isTurn = false;
	
	/**
	 * Reference to the input stream used for receiving data
	 */
	private ObjectInputStream ois;
	
	/**
	 * Reference to the output stream used for sending data
	 */
	private ObjectOutputStream oos;
	
	/**
	 * Holds the opponents last move data 
	 */
	private Connect4MoveMessage otherMessage;
	
	/**
	 * Reference to the current controller(this), used for making new thread 
	 */
	private Connect4Controller controller = this;
	
	/**
	 * This is the constructor for a Connect4Controller object
	 * 
	 * @param model: A reference to a Connect4Model that holds the data for the game
	 */
	public Connect4Controller(Connect4Model model) {
		this.model = model;
	}
	
	/**
	 * This method initializes the data streams for network communications
	 * 
	 * This method initializes the ObjectOutputStream and ObjectInputSteam used
	 * to pass data through the network
	 * 
	 * @param socket: A socket from the current server connection
	 */
	public void initStreams(Socket socket) {
		 try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
			this.ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
		}
	}
	
	/**
	 * This method starts the turn of the current object
	 * 
	 * This method starts the current turn of an object by starting the thread that hold ois.readObject().
	 * We use a new thread because this is a blocking call, and it would cause the GUI to freeze. We also use
	 * Platfrom.runLater() to send GUI update commands to the GUI, which is the data from the opponents turn.
	 * 
	 * @see javafx.application.Platform.runLater()
	 */
	public void startTurn() {
		// creates a new thread to run ois.readObject()
		Thread inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				otherMessage = null;
				try {
					otherMessage = (Connect4MoveMessage) ois.readObject();
					// after reading the object, it sends an update event to the main thread to update the gui
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							model.addPiece(otherMessage.getColumn(), otherMessage.getColor());
							// if the opponet won on the last turn, we show a lost message
							if(model.hasWon() != 0) {
								view.showLost();
								controller.setTurn(false);
							// if the current object is a computer, we must run the computerTurn()
							} else if(controller.isHuman == false) {
								controller.computerTurn();
							}
						}
					});
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
				// after finishing getting the opponets data, it is the current objects turn
				controller.setTurn(true);
			}
		});
		inputThread.start();
	}
	
	/**
	 * This method executes a turn from a human player
	 * 
	 * This method exectures a turn from a human player by adding thier guess to the current GUI and 
	 * sending the data to the opponet to update thier GUI
	 * 
	 * @param col: An int of column picked by the user
	 */
	public void humanTurn(int col) {
		this.model.addPiece(col);
		try {
			oos.writeObject(this.model.getMessage(col));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.startTurn();
	}
	
	/**
	 * This method execute a turn of the computer
	 * 
	 * This method executes a turn of the computer by taking choosing a random column and making that its guess.
	 * It then updates its GUI and sends the data to the opponent
	 */
	public void computerTurn() {
		// select and random int 0-6 exclusive 
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
	
	/**
	 * This method sets the mode of playing (human or computer). True for human
	 * false for computer
	 * 
	 * @param playAs: A boolean indicating which mode the game is played in
	 */
	public void setPlayAs(boolean playAs) {
		this.isHuman = playAs;
	}

	/**
	 * This method returns the grid containing the game state from the model
	 * @return The grid of the current game state
	 */
	public ArrayList<ArrayList<Integer>> getGrid() {
		return model.getGrid();
	}
	
	/**
	 * This method returns a boolean indicating if the current selection is legal on the board
	 * 
	 * @param col: An int of the selected row
	 * @return A boolean indicating if the placement is legal
	 */
	public boolean isLegal(int col) {
		return model.isLegal(col);
	}
	
	/**
	 * This method returns if the game is won or not
	 * 
	 * @return An int indicating if the game is won (1 = won, 0 = not won)
	 */
	public int hasWon() {
		return model.hasWon();
	}
	
	/**
	 * This method sets the model to the current controller
	 * 
	 * @param newModel: A Connect4Model to be set to the controller
	 */
	public void setModel(Connect4Model newModel) {
		model = newModel;
	}
	
	/**
	 * This method returns a boolean indicating if it is its turn
	 * 
	 * @return A boolean indicating if it is its turn
	 */
	public boolean getTurn() {
		return this.isTurn;
	}
	
	/**
	 * This method adds a view to the controller
	 * @param view: A Connect4View to be set to the controller
	 */
	public void addView(Connect4View view) {
		this.view = view;
	}
	
	/**
	 * This method sets the turn of the current object
	 * 
	 * @param isTurn: A boolean represting the current turn of the object
	 */
	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	/**
	 * This method adds a piece to the board of a diffrent color than its own
	 * 
	 * @param column: An int of the colum selected
	 * @param color: An int indicating which color to place based on the colors in Connect4MoveMessage
	 * @see Connect4MoveMessage
	 */
	public void addPiece(int column, int color) {
		model.addPiece(column, color);
	}
	
	/**
	 * This method returns a boolean indicating of the current object is human or computer
	 * 
	 * @return A boolean indicating if the current object is computer or human controlled
	 */
	public boolean isHuman() {
		return this.isHuman;
	}

	/**
	 * This method adds a piece to the board with its own color
	 * 
	 * @param col: An int indicating the column to add the piece 
	 */
	public void addPiece(int col) {
		model.addPiece(col);
	}
}

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class is the view in the MVC architecture for a connect 4 game. This class holds all the methods
 * to create a GUI for the user to view and interact with. The color of the pieces is as follows:
 * Player 1 = red
 * Player 2 = yellow
 * Player 1 will be the server and player 2 will be the client
 * 
 * 
 * @author Christopher Crabtree
 * @author Luke Cernetic
 * @version 1.0
 *
 */
public class Connect4View extends Application implements Observer{

	/**
	 * This is a private inner class for the Connect4View class. This class creates and 
	 * holds the data for the user to select how the game will be played and by who (human/ computer)
	 * 
	 * 
	 * @author Christopher Crabtree
	 * @author Luke Cernetic
	 * @version 1.0
	 *
	 */
	private class Connect4DialogBox extends Stage {
		
		/**
		 * Holds the data to select if it is a server or client
		 */
		private ToggleGroup createGroup;
		
		/**
		 * Holds the port selected by the user; default 4000
		 */
		private TextField portField;
		
		/**
		 * Holds the data to select if the game is played by a human or computer
		 */
		private ToggleGroup playAsGroup;
		
		/**
		 * Holds the address of the server; default localhost
		 */
		private TextField serverField;
		
		/**
		 * This is the constructor of the dialog box used by the user to select options about the
		 * game will be run. It creates the box and the shows it to the user when the user clicks new game
		 */
		public Connect4DialogBox() {
			DialogPane dPane = new DialogPane();
			
			dPane.setMinHeight(130);
			dPane.setMinWidth(260);
			
			Alert dialog = new Alert(AlertType.NONE);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.setTitle("Network Setup");
			
			createGroup = new ToggleGroup();
			
			RadioButton serverRadio = new RadioButton("Server");
			serverRadio.setSelected(true);
			serverRadio.setMinWidth(70);
			serverRadio.setToggleGroup(createGroup);
			
			RadioButton clientRadio = new RadioButton("Client");
			clientRadio.setMinWidth(70);
			clientRadio.setToggleGroup(createGroup);
			
			playAsGroup = new ToggleGroup();
			
			RadioButton humanRadio = new RadioButton("Human");
			humanRadio.setSelected(true);
			humanRadio.setMinWidth(70);
			humanRadio.setToggleGroup(playAsGroup);
			
			RadioButton computerRadio = new RadioButton("Computer");
			computerRadio.setMinWidth(80);
			computerRadio.setToggleGroup(playAsGroup);
			
			Text createText = new Text("   Create: ");
			Text playAsText = new Text("   Play as: ");
			Text serverText = new Text("   Server");
			Text portText = new Text("Port");
			
			serverField = new TextField("localhost");
			// default address = localhost
			serverField.setText("localhost");
			serverField.setMinWidth(90);
			
			portField = new TextField("4000");
			// default port = 4000
			portField.setText("4000");
			portField.setMinWidth(90);
			
			HBox createBox = new HBox(10);
			
			createBox.getChildren().addAll(createText, serverRadio, clientRadio);
			
			HBox playAsBox = new HBox(10);
			playAsBox.getChildren().addAll(playAsText, humanRadio, computerRadio);
			
			HBox serverPortBox = new HBox(10);
			serverPortBox.getChildren().addAll(serverText, serverField, portText, portField);
			
			VBox holder = new VBox(10);
			holder.getChildren().addAll(createBox, playAsBox, serverPortBox);
			
			
			dPane.getChildren().addAll(holder);
			dialog.setDialogPane(dPane);
			
			ButtonType ok = new ButtonType("OK");
			ButtonType cancel = new ButtonType("Cancel");
			dialog.getButtonTypes().addAll(ok, cancel);
			
			dialog.showAndWait();
		}
		
		/**
		 * This method returns the port selected by the use. Default of 4000
		 * 
		 * @return An int representing the port to run the game on
		 */
		public int getPort() {
			return  Integer.parseInt(portField.getText());
		}
		
		/**
		 * This method returns the address of the server. default of localhost
		 * 
		 * @return A string of the address of the server
		 */
		public String getAddress() {
			return serverField.getText();
			
		}
		
		/**
		 * This method returns a boolean indicating if the game is run by human or computer.
		 * true = human, false = computer
		 * 
		 * @return A boolean indicating how the game will be run (computer or human)
		 */
		public boolean playAs() {
			if(this.playAsGroup.getSelectedToggle().toString().contains("Human")) {
				return true;
			}
			return false;
		}
		
		/**
		 * This method returns a boolean indicating if the current instance is a server
		 * or a client
		 * 
		 * @return A boolean indicating if the instance is a server or client
		 */
		public boolean createType() {
			if(this.createGroup.getSelectedToggle().toString().contains("Server")) {
				return false;
			}
			return true;
		}
	}

	/**
	 * A gridPane used to hold all the circles
	 */
	private GridPane window;
	
	/**
	 * Reference to the controller
	 */
	private Connect4Controller controller;
	
	/**
	 * VBox used to hold the menu
	 */
	private VBox border;
	
	/**
	 * Holds the newGame option in the bar
	 */
	private MenuBar bar;
	
	/**
	 * Pop up box used to get data from the user when they select a new game
	 */
	private Connect4DialogBox dialogBox;

	/**
	 * This is the method that starts the stage design, sets the params of the stage and shows it as
	 * a GUI to the user
	 * 
	 * @param stage: A Stage object where we put all the GUI elements 
	 * 
	 * @Override
	 */
	public void start(Stage stage) {
		Connect4Model model = new Connect4Model();
		controller = new Connect4Controller(model);
		model.addObserver(this);
		stage.setTitle("Connect 4");
		border = new VBox();
		//Sets up MenuBar
		bar = new MenuBar();
		Menu file = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		// sets the actions for a new game
		newGame.setOnAction((event) -> {
			dialogBox = new Connect4DialogBox();
			this.newGame();
		});
		
		file.getItems().add(newGame);
		bar.getMenus().add(file);
		fillGrid(controller.getGrid());
		Scene scene = new Scene(border, 344, 321);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * This method fills the grid with 0s, which will print out to white spaces
	 * 
	 * @param grid: An Array of Arrays of Integers that represent the state of the game
	 * 
	 */
	private void fillGrid(ArrayList<ArrayList<Integer>> grid) {
		//set gridPane and values
		window = new GridPane();
		window.setStyle("-fx-background-color:#0000ff");
		window.setHgap(8);
		window.setVgap(8);
		window.setPadding(new Insets(8, 8, 8, 8));
				
		//Fills in GridPane
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				Circle cir = new Circle(20);
				cir.setFill(getPlayerColor(grid.get(i).get(j)));
				window.add(cir, j, i, 1, 1);
				window.setOnMouseClicked((event) -> mouseClick(event.getX()));
			}
		}
		
		border.getChildren().clear();
		border.getChildren().addAll(bar, window);
	}
	
	/**
	 * This method returns the color of an int
	 * 
	 * @param player: An int representing a color
	 * @return A Color of that int
	 */
	private Color getPlayerColor(int player) {
		if (player == 1) {
			return Color.RED;
		}
		else if (player == 2) {
			return Color.YELLOW;
		}
		else {
			return Color.WHITE;
		}
	}
	
	
	private void mouseClick(double x) {
		int col;
		if (x < 52) {
			col = 0;
		}
		else if (x >= 52 && x < 100) {
			col = 1;
		}
		else if(x >= 100 && x < 148) {
			col = 2;
		}
		else if(x >= 148 && x < 196) {
			col = 3;
		}
		else if(x >= 196 && x < 244) {
			col = 4;
		}
		else if(x >= 244 && x < 292) {
			col = 5;
		}
		else {
			col = 6;
		}
		if (!controller.isHuman()) {
			window.setOnMouseClicked((event) -> {});
		} else if(!controller.getTurn()) {
			window.setOnMouseClicked((event) -> {});
		} else if (controller.isLegal(col)) {
			controller.humanTurn(col);
			controller.setTurn(false);
			//check if game won
			int winner = controller.hasWon();
			if (winner != 0) {
				window.setOnMouseClicked((event) -> {});
				Alert winAlert = new Alert(AlertType.INFORMATION);
				winAlert.setHeaderText("Message");
				winAlert.setContentText("You won!");
				winAlert.showAndWait();
			}
		}
		else {
			Alert illegalMove = new Alert(AlertType.ERROR);
			illegalMove.setHeaderText("Error");
			illegalMove.setContentText("Column full, pick somewhere else!");
			illegalMove.showAndWait();
		}
	}
	
	public void showWon() {
		Alert winAlert = new Alert(AlertType.INFORMATION);
		winAlert.setHeaderText("Message");
		winAlert.setContentText("You won!");
		winAlert.showAndWait();
	}
	
	public void showLost() {
		Alert winAlert = new Alert(AlertType.INFORMATION);
		winAlert.setHeaderText("Message");
		winAlert.setContentText("You lost!");
		winAlert.showAndWait();
	}
	
	private void newGame() {
		Connect4Model model = new Connect4Model();
		this.controller.setModel(model);
		this.controller.addView(this);
		model.addObserver(this);
		this.fillGrid(model.getGrid());
		Socket socket = null;
		int currPlayer = 1;
		if(!this.dialogBox.createType()) {
			try {
				ServerSocket server = new ServerSocket(this.dialogBox.getPort());
				socket = server.accept();
				server.close();
				controller.setTurn(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			currPlayer = 2;
			try {
				socket = new Socket(this.dialogBox.getAddress(), this.dialogBox.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.setPlayer(currPlayer);
		controller.setPlayAs(this.dialogBox.playAs());
		controller.initStreams(socket);
		if(this.dialogBox.createType()) {
			this.controller.startTurn();
		} else if(!this.dialogBox.playAs() && !this.dialogBox.createType()) {
			this.controller.computerTurn();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ArrayList<ArrayList<Integer>> grid = (ArrayList<ArrayList<Integer>>) arg;
		fillGrid(grid);
	}

	
}

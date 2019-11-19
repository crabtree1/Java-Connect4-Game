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

public class Connect4View extends Application implements Observer{
	
	private GridPane window;
	private Connect4Model model = new Connect4Model();
	private Connect4Controller controller = new Connect4Controller(model);
	private VBox border;
	private MenuBar bar;
	private Connect4DialogBox dialogBox;

	private class Connect4DialogBox extends Stage {
		private ToggleGroup createGroup;
		private TextField portField;
		private ToggleGroup playAsGroup;
		
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
			
			TextField serverField = new TextField("localhost");
			serverField.setMinWidth(90);
			
			portField = new TextField("4000");
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
		
		public int getPort() {
			return  Integer.parseInt(portField.getText());
		}
		
		public boolean playAs() {
			if(this.playAsGroup.getSelectedToggle().toString().contains("Human")) {
				return true;
			}
			return false;
		}
		
		public boolean createType() {
			if(this.createGroup.getSelectedToggle().toString().contains("Server")) {
				return false;
			}
			return true;
		}
	}


	@Override
	public void start(Stage stage) {
		model.addObserver(this);
		stage.setTitle("Connect 4");
		
		border = new VBox();
		
		//Sets up MenuBar
		bar = new MenuBar();
		Menu file = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
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
		model = new Connect4Model();
		this.controller.setModel(model);
		this.controller.addView(this);
		model.addObserver(this);
		this.fillGrid(model.getGrid());
		Socket socket = null;
		int currPlayer = 1;
		if(!this.dialogBox.createType()) {
			try {
				controller.setTurn(true);
				ServerSocket server = new ServerSocket(this.dialogBox.getPort());
				socket = server.accept();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			currPlayer = 2;
			try {
				InetAddress host = InetAddress.getLocalHost();
				socket = new Socket(host.getHostName(), this.dialogBox.getPort());
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

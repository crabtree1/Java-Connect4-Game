import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Connect4View extends Application implements Observer{

	private class Connect4DialogBox extends Stage {
		public Connect4DialogBox() {
			DialogPane dPane = new DialogPane();
			Alert dialog = new Alert(AlertType.NONE);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.setTitle("Network Setup");
			RadioButton rb = new RadioButton();
			rb.setText("Test");
			dPane.getChildren().add(rb);
			//dialog.getDialogPane().getChildren().add(rb);
			dialog.setDialogPane(dPane);
			ButtonType ok = new ButtonType("Ok");
			ButtonType cancel = new ButtonType("Cancel");
			dialog.getButtonTypes().addAll(ok, cancel);
			dialog.showAndWait();
		}
	}
	
	GridPane window;
	int currPlayer = 1;
	Connect4Model model = new Connect4Model();
	Connect4Controller controller = new Connect4Controller(model);
	VBox border;
	MenuBar bar;

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
			Connect4DialogBox dialogBox = new Connect4DialogBox();
		});
		file.getItems().add(newGame);
		bar.getMenus().add(file);
		
		fillGrid(controller.getGrid());
		
		Scene scene = new Scene(border, 344, 321);
		stage.setScene(scene);
		stage.show();
	}
	
	public void fillGrid(ArrayList<ArrayList<Integer>> grid) {
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
	
	public Color getPlayerColor(int player) {
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
	
	
	public void mouseClick(double x) {
		
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
		
		if (controller.isLegal(col)) {
			controller.addPiece(col, currPlayer);
			
			//check if game won
			int winner = controller.hasWon();
			if (winner != 0) {
				window.setOnMouseClicked((event) -> {});
				Alert winAlert = new Alert(AlertType.INFORMATION);
				winAlert.setHeaderText("Message");
				winAlert.setContentText("You won!");
				winAlert.showAndWait();
			}
			
			//Change current player
			if (currPlayer == 1) {
				currPlayer = 2;
			}
			else if (currPlayer == 2) {
				currPlayer = 1;
			}
		}
		else {
			Alert illegalMove = new Alert(AlertType.ERROR);
			illegalMove.setHeaderText("Error");
			illegalMove.setContentText("Column full, pick somewhere else!");
			illegalMove.showAndWait();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ArrayList<ArrayList<Integer>> grid = (ArrayList<ArrayList<Integer>>) arg;
		fillGrid(grid);
	}

	
}

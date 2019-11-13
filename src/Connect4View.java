import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Connect4View extends Application {

	@Override
	public void start(Stage stage) {
		stage.setTitle("Connect 4");
		
		//Sets up MenuBar
		MenuBar bar = new MenuBar();
		Menu file = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		file.getItems().add(newGame);
		bar.getMenus().add(file);
		
		//set gridPane and values
		GridPane window = new GridPane();
		window.setStyle("-fx-background-color:#0000ff");
		window.setHgap(8);
		window.setVgap(8);
		window.setPadding(new Insets(8, 8, 8, 8));
		
		//Fills in GridPane
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				Circle cir = new Circle(20);
				cir.setFill(javafx.scene.paint.Color.WHITE);
				window.add(cir, i, j, 1, 1);
				
				window.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> mouseClick(event.getX()));
			}
		}
		
		//Adds menu and GridPane to a VBox to show in order.
		VBox border = new VBox();
		border.getChildren().addAll(bar, window);
		
		Scene scene = new Scene(border, 344, 321);
		stage.setScene(scene);
		stage.show();
	}
	
	public void mouseClick(double x) {
		if (x < 52) {
			//addPiece(0, player)
		}
		
		else if (x >= 52 && x < 100) {
			//addPiece(1, player)
		}
		
		else if(x >= 100 && x < 148) {
			//addPiece(2, player)
		}
		
		else if(x >= 148 && x < 196) {
			//addPiece(3, player)
		}
		
		else if(x >= 196 && x < 244) {
			//addPiece(4, player)
		}
		
		else if(x >= 244 && x < 292) {
			//addPiece(5, player)
		}
		
		else {
			//addPiece(6, player)
		}
	}

	
}

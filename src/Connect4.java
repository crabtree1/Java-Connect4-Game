
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;

public class Connect4  {
	
	public static void main(String[] args) {
		//serverThread.setDaemon(true);
		//serverThread.start();
		Application.launch(Connect4View.class, args);
	}
	
	
/**
 * 				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							Connect4Server.startServer(new Connect4Controller(new Connect4Model()));
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
					}
					
				});
 */
	
}

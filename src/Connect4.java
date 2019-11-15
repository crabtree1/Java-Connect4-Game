
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;

public class Connect4  {
	
	public static void main(String[] args) {
		Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						//try {
							//Connect4Server.StartServer();
						//}// catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						//}
					}
					
				});
			}
		});
		//serverThread.setDaemon(true);
		//serverThread.start();
		Application.launch(Connect4View.class, args);
	}
}

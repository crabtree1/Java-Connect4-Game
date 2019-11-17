import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class Connect4Server {
	private static ServerSocket server;
	
	private static int port = 4000;
	private static Socket socket;
	private static Connect4MoveMessage message;
	private static boolean isDoneRunning = false;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		server = new ServerSocket(port);
		System.out.println("Server Open");
		while(true) {
			Socket socket = server.accept();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Connect4MoveMessage message = (Connect4MoveMessage) ois.readObject();
			System.out.println("Row: " + message.getRow());
			System.out.println("Col: " + message.getColumn());
			System.out.println("Color: " + message.getColor());
			ois.close();
			socket.close();
		}
	}
	
	public static void startServer() throws IOException {
		
		server = new ServerSocket(port);
		System.out.println("Server Open");
	}
		
	public static void getMessage (Connect4Controller controller) throws IOException, 
	ClassNotFoundException {
		message = null;
		socket = null;
		Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socket = server.accept();
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					message = (Connect4MoveMessage) ois.readObject();
					System.out.println("Row: " + message.getRow());
					System.out.println("Col: " + message.getColumn());
					System.out.println("Color: " + message.getColor());
					ois.close();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							controller.addPiece(message.getColumn(), message.getColor());
						}
					});
					isDoneRunning = true;
					System.out.print("Here");
					socket.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		serverThread.start();
	}
}

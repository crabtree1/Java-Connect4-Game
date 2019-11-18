import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class Connect4Server {
	private static ServerSocket server = null;
	private static int port = 4000;
	private static Socket socket;
	private static Connect4MoveMessage myMessage;
	private static Connect4MoveMessage otherMessage;
	private static Connect4Controller controller;
	
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
		if(server != null) {
			server.close();
		}
		server = new ServerSocket(port);
		System.out.println("Server Open");
	}
	
	
	public static void setMessage(Connect4MoveMessage message) {
		myMessage = message;
	}
	
	public static void setController(Connect4Controller newController) {
		controller = newController;
	}
	
	public static Connect4MoveMessage getOtherMessage() {
		return otherMessage;
	}
	
	public static void setPort(int newPort) {
		port = newPort;
	}
		
	public static void getMessage () throws IOException, 
	ClassNotFoundException {
		Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					socket = server.accept();
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					System.out.println("Server Sending Data...");
					oos.writeObject(myMessage);
					System.out.println("Server Reciving Data...");
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					otherMessage = (Connect4MoveMessage) ois.readObject();
					System.out.println("Server Data Recived...");
					ois.close();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							controller.addPiece(otherMessage.getColumn(), otherMessage.getColor());
						}
					});
					socket.close();
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
				controller.setTurn(true);
			}
			
		});
		serverThread.start();
	}
}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class Connect4Server {
	private static ServerSocket server;
	
	private static int port = 4000;
	private static Socket socket;
	private static Connect4MoveMessage myMessage;
	private static Connect4MoveMessage otherMessage;
	
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
	
	public static void setMessage(Connect4MoveMessage message) {
		myMessage = message;
	}
	
	public static Connect4MoveMessage getOtherMessage() {
		return otherMessage;
	}
		
	public static void getMessage (Connect4Model model) throws IOException, 
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
					System.out.println("Row: " + otherMessage.getRow());
					System.out.println("Col: " + otherMessage.getColumn());
					System.out.println("Color: " + otherMessage.getColor());
					ois.close();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							model.addPiece(otherMessage.getColumn(), otherMessage.getColor());
						}
					});
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

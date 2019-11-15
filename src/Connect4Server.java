import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Platform;

public class Connect4Server {
	private static ServerSocket server;
	
	private static int port = 4000;
	
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
	
	public static Connect4MoveMessage startServer(Connect4Controller controller) throws IOException,
	ClassNotFoundException {
		
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
			//controller.addPiece(message.getColumn(), message.getColor());
			System.err.print("Here");
			//thread.start();
			socket.close();
			return message;
			//thread.interrupt();
		}
	}
}

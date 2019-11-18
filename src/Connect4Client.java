import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Platform;

public class Connect4Client {
	private static Connect4MoveMessage myMessage;
	public static Connect4MoveMessage otherMessage;
	private static int port = 4000;
	
	public static void callServer(Connect4Model model) throws IOException, ClassNotFoundException {
		Thread serverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InetAddress host = InetAddress.getLocalHost();
					Socket socket = new Socket(host.getHostName(), port);
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					otherMessage = (Connect4MoveMessage) ois.readObject();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							model.addPiece(otherMessage.getColumn(), otherMessage.getColor());
						}
					});
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					System.out.println("Calling Server...");
					oos.writeObject(myMessage);
					System.out.println(otherMessage.getColor());
					socket.close();
				} catch (IOException | ClassNotFoundException e) {
				}
			}
		});
		serverThread.start();
	}
	
	public static void setMessage(Connect4MoveMessage message) {
		myMessage = message;
	}
	
	public static Connect4MoveMessage getOtherMessage() {
		return otherMessage;
	}
	
	public static void setPort(int newPort) {
		port = newPort;
	}
}

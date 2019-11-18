import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Platform;

public class Connect4Client {
	private static Connect4MoveMessage myMessage;
	public static Connect4MoveMessage otherMessage;
	private static int port = 4000;
	private static Socket socket;
	
	public static void callServer(Connect4Model model) throws IOException, ClassNotFoundException {
		Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					System.out.println("Calling Server...");
					oos.writeObject(myMessage);
					System.out.println(otherMessage.getColor());
					socket.close();
				} catch (IOException e) {
				}
			}
		});
		clientThread.start();
	}
	
	public static void getMessage(Connect4Controller controller) throws IOException {
		InetAddress host = InetAddress.getLocalHost();
		socket = new Socket(host.getHostName(), port);
		Thread clientListenerThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					otherMessage = (Connect4MoveMessage) ois.readObject();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							controller.addMessage(otherMessage.getColumn(), otherMessage.getColor());
						}
					});
					
				} catch (IOException | ClassNotFoundException e) {
				}
			}
		});
		clientListenerThread.start();
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

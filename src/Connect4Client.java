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
	private static Connect4Controller controller;
	private static boolean isListening = false;
	private static InetAddress host;
	private static Thread clientListenerThread;
	private static Runnable clientListener = new Runnable() {
		@Override
		public void run() {
			try {
				isListening = true;
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				otherMessage = (Connect4MoveMessage) ois.readObject();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						controller.addMessage(otherMessage.getColumn(), otherMessage.getColor());
					}
				});
				isListening = false;
			} catch (IOException | ClassNotFoundException e) {
			}
			controller.setTurn(true);
		}
	};
	
	public static void setUpClient() {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public static void callServer() throws IOException, ClassNotFoundException {
		Thread clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Thread clientListenerThread = new Thread(clientListener);
				try {
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(myMessage);
					socket.close();
				} catch (IOException e) {
				} finally {
					clientListenerThread.start();
				}
			}
		});
		clientThread.start();
		getMessage(controller);
		//getMessage(controller);
	}
	
	public static void getMessage(Connect4Controller controller) throws IOException {
		socket = new Socket(host.getHostName(), port);
		clientListenerThread = new Thread(clientListener);
		clientListenerThread.start();
	}
			
	public static void setController(Connect4Controller newController) {
		controller = newController;
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
	
	public static boolean isListening() {
		return isListening;
	}
}

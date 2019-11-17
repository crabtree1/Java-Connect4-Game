import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Platform;

public class Connect4Client {
	private static Connect4MoveMessage newMessage;

	public static void callServer(Connect4MoveMessage message) throws IOException {
		InetAddress host = InetAddress.getLocalHost();
		Socket socket = new Socket(host.getHostName(), 4000);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		System.out.println("Calling Server...");
		oos.writeObject(message);
	}
}

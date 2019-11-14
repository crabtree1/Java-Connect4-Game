import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerExample {
	private static ServerSocket server;
	
	private static int port = 4000;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		server = new ServerSocket(port);
		Socket socket = server.accept();
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		String message = (String) ois.readObject();
		System.out.println(message);
		while(!message.equalsIgnoreCase("exit")) {
			socket = server.accept();
			ois = new ObjectInputStream(socket.getInputStream());
			message = (String) ois.readObject();
			System.out.println(message);
		}
		ois.close();
		socket.close();
	}
}

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerExample {
	private static ServerSocket server;
	
	private static int port = 4000;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		server = new ServerSocket(port);
		String message = "";
		System.out.println("server up");
		while(!message.equalsIgnoreCase("exit")) {
			Socket socket = server.accept();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			message = (String) ois.readObject();
			System.out.println(message);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oos.writeObject(message.toUpperCase());
			ois.close();
			socket.close();
		}
	}
}

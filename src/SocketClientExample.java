import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketClientExample {

	public static void main(String[] args) throws IOException {
		InetAddress host = InetAddress.getLocalHost();
		Socket socket;
		ObjectOutputStream oos;
		ObjectInputStream ois;
		String message = "";
		Scanner scanner = new Scanner(System.in);
		socket = new Socket(host.getHostName(), 4000);
		while(!message.equalsIgnoreCase("exit")) {
			//socket = new Socket(host.getHostName(), 4000);
			System.out.println("Input Here:");
			message = scanner.nextLine();
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);
			//socket.close();
		}
		socket.close();
		scanner.close();
	}
}

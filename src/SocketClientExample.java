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
		socket = new Socket(host.getHostName(), 4000);
		String message = "";
		while(!message.equalsIgnoreCase("exit")) {
			//Scanner scanner = new Scanner(System.in);
			System.out.println("Input Here:");
			//message = scanner.nextLine();
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(message);
			//scanner.close();
		}
		socket.close();
	}
}

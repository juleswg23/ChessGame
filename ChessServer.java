// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class ChessServer
{

	// Vector to store active clients
	static Vector<ClientHandler> ar = new Vector<>();

	// counter for clients
	static int i = 0;
	static int MAX_PLAYERS = 2;
	final static int SERVER_PORT = 6172;
	private final static String PASSWORD = "jandschess";

	public static void main(String[] args) throws IOException {
		ServerSocket ss = new ServerSocket(SERVER_PORT);
		Socket s;

		// running infinite loop for getting
		// client request
		while (i < MAX_PLAYERS)
		{
			// Accept the incoming request
			s = ss.accept();

			System.out.println("New client request received : " + s);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());


			System.out.println("Creating a new handler for this client...");

			// Create a new handler object for handling this request.
			ClientHandler ch = new ClientHandler(s,"client " + i, ois, oos);

			System.out.println("got here");
			if (ch.ois.readUTF().equals(PASSWORD)) {
				ch.oos.writeBoolean(true);
				ch.oos.flush();

				// Create a new Thread with this object.
				Thread t = new Thread(ch);

				System.out.println("Adding this client to active client list");

				// add this client to active clients list
				ar.add(ch);

				// start the thread.
				t.start();

				// increment i for new client.
				// i is used for naming only, and can be replaced
				// by any naming scheme
				System.out.println(ar);
				i++;
			}

		}
	}
}

// ClientHandler class
class ClientHandler implements Runnable
{
	Scanner scn = new Scanner(System.in);
	private String name;
	public final ObjectInputStream ois;
	public final ObjectOutputStream oos;
	private Socket s;
	public boolean isloggedin;
	private Board b;

	// constructor
	public ClientHandler(Socket s, String name,
							ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
		this.name = name;
		this.s = s;
		this.isloggedin=true;
	}

	@Override
	public void run() {

		String received = "";

		while (true) {
			try {
				// receive the board
				b = (Board) ois.readUnshared();
				System.out.println("Received from board: " + this.name);


				if(received.equals("logout")) {
					//tell other user that this dude logged out
					break;
				}
				// search for the recipient in the connected devices list.
				// ar is the vector storing client of active users
				for (ClientHandler ch : ChessServer.ar) {
					// if the recipient is found, write on its
					// output stream
					if (!ch.name.equals(name)) {
						ch.oos.reset();
						ch.oos.writeObject(b);
						ch.oos.flush();
						System.out.println("sent to board: " + ch.name);
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try {
			// closing resources
			this.ois.close();
			this.oos.close();
			this.s.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

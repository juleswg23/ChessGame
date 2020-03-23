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

	public static void main(String[] args) throws IOException {
		// server is listening on port 6789
		ServerSocket ss = new ServerSocket(6789);

		Socket s;

		// running infinite loop for getting
		// client request
		while (i < MAX_PLAYERS)
		{
			// Accept the incoming request
			s = ss.accept();

			System.out.println("New client request received : " + s);

			ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream dis = new ObjectInputStream(s.getInputStream());


			System.out.println("Creating a new handler for this client...");

			// Create a new handler object for handling this request.
			ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos);

			// Create a new Thread with this object.
			Thread t = new Thread(mtch);

			System.out.println("Adding this client to active client list");

			// add this client to active clients list
			ar.add(mtch);

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

// ClientHandler class
class ClientHandler implements Runnable
{
	Scanner scn = new Scanner(System.in);
	private String name;
	final ObjectInputStream dis;
	final ObjectOutputStream dos;
	Socket s;
	boolean isloggedin;
	private Board b;

	// constructor
	public ClientHandler(Socket s, String name,
							ObjectInputStream dis, ObjectOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
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
				b = (Board) dis.readUnshared();
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
						ch.dos.reset();
						ch.dos.writeObject(b);
						ch.dos.flush();
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
			this.dis.close();
			this.dos.close();
			this.s.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

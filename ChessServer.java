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

	private int openConnections = 1;
	final static int MAX_PLAYERS = 2;
	final static int SERVER_PORT = 6172;
	private final static String PASSWORD = "jandschess";
	private static ServerSocket ss;

	public ChessServer() throws IOException {
		ss = new ServerSocket(SERVER_PORT);
	}

	public void runServer() throws IOException {

		// running infinite loop for getting client request
		while (openConnections <= MAX_PLAYERS)
		{
			// Accept the incoming request
			Socket s = ss.accept();

			System.out.println("New client request received : " + s);

			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

			System.out.println("Creating a new handler for this client...");

			// Create a new handler object for handling this request.
			ClientHandler ch = new ClientHandler(s, "player " + openConnections, ois, oos, this);

			if (ch.ois.readUTF().equals(PASSWORD)) {
				ch.oos.writeBoolean(true);
				ch.oos.flush();

				//send color
				ch.oos.reset();
				ch.oos.writeBoolean(openConnections == 1);
				ch.oos.flush();

				// Create a new Thread with this object.
				Thread t = new Thread(ch);
				System.out.println("Adding this client to active client list");

				// add this client to active clients list
				ar.add(ch);
				// start the thread.
				t.start();
				openConnections++;
			}
		}

		while (openConnections > 0) {
			//do some closing?
			openConnections--;
		}

	}

	// public void closeThread(String name) {
	// 	System.out.println("Closing thread of: " + name);
	// 	System.out.println(Thread.currentThread().getThreadGroup());
	// 	System.out.println(Thread.currentThread());
	// 	Thread.currentThread().interrupt();
	// 	System.out.println("Did it work? " + name);
	// 	System.out.println(Thread.currentThread().getThreadGroup());
	// 	System.out.println(Thread.currentThread());
	//
	// }

	public static void main(String[] args) throws IOException {
		ChessServer cs = new ChessServer();
		cs.runServer();
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
	private Board b;
	private ChessServer server;
	static boolean running;

	// constructor
	public ClientHandler(Socket s, String name, ObjectInputStream ois, ObjectOutputStream oos, ChessServer cs) {
		this.ois = ois;
		this.oos = oos;
		this.name = name;
		this.s = s;
		this.server = cs;
		running = true;
	}

	@Override
	public void run() {
		while (running) {
			try {
				// receive the board
				b = (Board) ois.readUnshared();
				System.out.println("Received from: " + this.name);

				// search for the recipient in the connected devices list.
				// ar is the vector storing client of active users
				for (ClientHandler ch : ChessServer.ar) {
					// if the recipient is found, write on its
					// output stream
					if (!ch.name.equals(name)) {
						ch.oos.reset();
						ch.oos.writeObject(b);
						ch.oos.flush();
						System.out.println("Sent to: " + ch.name);

						break;
					}
				}

				if (b.getCloseConnection()) {
					this.closeConnection();
					running = false;
				}

			} catch (IOException e) {
				System.out.println("Trying to read?");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void closeConnection() throws IOException {
		this.s.close();
		ChessServer.ar.remove(this);
		System.out.println(this.name + " disconnected");
	}

}

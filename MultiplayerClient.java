// Java implementation for multithreaded chat client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.awt.Point;


public class MultiplayerClient
{
	final static int SERVER_PORT = 6789;
	public static boolean done = false;
	Socket s = null;
	Board myBoard;

	public MultiplayerClient(Board b) throws UnknownHostException, IOException {
		myBoard = b;
		play();

	}

	public void play() throws UnknownHostException, IOException {

		// getting localhost ip
		InetAddress ip = InetAddress.getByName("localhost");

		// establish the connection
    try {
  		s = new Socket(ip, SERVER_PORT);
    } catch (ConnectException eOne) {
      try {
        s = new Socket("10.0.1.106", SERVER_PORT);
      } catch (Exception eTwo) {
        eTwo.printStackTrace();
      }
    }

		ObjectOutputStream dos = new ObjectOutputStream(s.getOutputStream());
		ObjectInputStream dis = new ObjectInputStream(s.getInputStream());

    Thread sendBoard = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
            // make a board and make a move to send.
						// This will be user input
						myBoard.makeMove(myBoard.getPieces().get(0), new Point(0,3));
            dos.writeObject(myBoard);
            dos.flush();
						System.out.println("Sent: " + myBoard.toString());
						//eventually remove break
            break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

    Thread readBoard = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while (true) {
					try {
						// read the message sent to this client
						if (done) {
              break;
            }
						Board b = (Board) dis.readObject();

						// replace this to display to chess game.
						System.out.println(b.toString());

					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
				}

				try {
					// closing resources
					done = true;
					dos.close();
					dis.close();

				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		});

    sendBoard.start();
    readBoard.start();

	}

	public static void main(String args[]) throws UnknownHostException, IOException {
		MultiplayerClient m = new MultiplayerClient(new Board());
	}

}

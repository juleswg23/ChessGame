// Java implementation for multithreaded chat client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.awt.Point;


public class MultiplayerClient implements Serializable
{
	final static int SERVER_PORT = 6172;
	public static boolean done = false;
	Socket s = null;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	private final static String PASSWORD = "jandschess";

	Board myBoard;

	public MultiplayerClient(Board b) throws UnknownHostException, IOException {
		myBoard = b;
		setup();
	}

	public void setup() throws UnknownHostException, IOException {

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

		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());

		//maybe have a gameid/password the user can use
		oos.writeUTF(PASSWORD);
		oos.flush();

		if (ois.readBoolean()) {
			play();
		}
	}

	public void play() {

    // Thread sendBoard = new Thread(new Runnable() {
		// 	@Override
		// 	public void run() {
		// 			while (true) {
		// 				try {
		//
		// 					// make a board and make a move to send.
		// 					// This will be user input
		// 					myBoard.makeMove(myBoard.getPieces().get(0), new Point(0,4));
		//
	  //           oos.writeObject(myBoard);
	  //           oos.flush();
		// 					System.out.println("Sent: " + myBoard.toString());
		// 					//eventually remove break
	  //           break;
		// 				} catch (IOException e) {
		// 					e.printStackTrace();
		// 				}
		// 			}
		// 	}
		// });

    Thread readBoard = new Thread(new Runnable()
		{
			@Override
			public void run() {
				while (true) {
					try {
						if (done) {
              break;
            }
						//this might not work too hot.
						Board b = (Board) ois.readUnshared();
						myBoard.setUpBoard(b.getWhiteTurn(), b.getWhiteKing(), b.getBlackKing(), b.getPieces());
						System.out.println("Received");

					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
            e.printStackTrace();
          }
				}

				try {
					// closing resources
					done = true;
					oos.close();
					ois.close();

				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		});

    //sendBoard.start();
    readBoard.start();

	}

	public void sendToServer(Board b) {
		try {
			oos.reset();
			oos.writeObject(b);
			oos.flush();
			System.out.println("Sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws UnknownHostException, IOException {
		MultiplayerClient m = new MultiplayerClient(new Board());
	}

}

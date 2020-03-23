// Java implementation for multithreaded chat client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.awt.Point;


public class MultiplayerClient implements Serializable
{
	final static int SERVER_PORT = 6789;
	public static boolean done = false;
	Socket s = null;
	InputStream fromServer;
	OutputStream toServer;
	ObjectOutputStream dos;
	ObjectInputStream dis;

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

		toServer = s.getOutputStream();
		fromServer = s.getInputStream();
		dos = new ObjectOutputStream(toServer);
		dis = new ObjectInputStream(fromServer);

		play();
	}

	public void play() {

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
						Board b = (Board) dis.readObject();
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
					dos.close();
					dis.close();

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
			dos = new ObjectOutputStream(toServer);
			dos.writeObject(b);
			System.out.println(b);
			dos.flush();
			dos.close();
			System.out.println("Sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws UnknownHostException, IOException {
		MultiplayerClient m = new MultiplayerClient(new Board());
	}

}

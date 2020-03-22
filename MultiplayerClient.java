// Java implementation for multithreaded chat client
// Save file as Client.java

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiplayerClient
{
	final static int SERVER_PORT = 6789;
	public static boolean done = false;
	Socket s = null;
	Board myBoard;

	public MultiplayerClient(Board b) throws UnknownHostException, IOException {
		play();
		myBoard = b;
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

		// // sendMessage thread
		// Thread sendMessage = new Thread(new Runnable() {
		// 	@Override
		// 	public void run() {
		// 		while (true) {
		//
		// 			// read the message to deliver.
		// 			String msg = scn.nextLine();
		//
		// 			try {
		// 				// write on the output stream
		// 				dos.writeUTF(msg);
		// 				dos.flush();
		// 				System.out.println("Sent: " + msg);
		// 				if (msg.equals("logout")) {
		// 					break;
		// 				}
		// 			} catch (IOException e) {
		// 				e.printStackTrace();
		// 			}
		// 		}
		//
		// 		try {
		// 			// closing resources
		// 			done = true;
		// 			dos.close();
		// 			dis.close();
		//
		// 		} catch(IOException e) {
		// 			e.printStackTrace();
		// 		}
		// 	}
		// });

    Thread sendBoard = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
            // make a board to send.
            dos.writeObject(myBoard);
            dos.flush();
						System.out.println("Sent: " + b.toString());
            break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		// readMessage thread
		// Thread readMessage = new Thread(new Runnable()
		// {
		// 	@Override
		// 	public void run() {
		//
		// 		while (true) {
		// 			try {
		// 				// read the message sent to this client
		// 				if (done) break;
		// 				String msg = dis.readUTF();
		// 				System.out.println(msg);
		// 			} catch (IOException e) {
		// 				e.printStackTrace();
		// 			}
		// 		}
		//
		// 		try {
		// 			// closing resources
		// 			done = true;
		// 			dos.close();
		// 			dis.close();
		//
		// 		} catch(IOException e) {
		// 			e.printStackTrace();
		// 		}
		// 	}
		// });

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

		//sendMessage.start();
    sendBoard.start();
		//readMessage.start();
    readBoard.start();

	}

	public static void main(String args[]) throws UnknownHostException, IOException {
		MultiplayerClient m = new MultiplayerClient(new Board());
	}

}

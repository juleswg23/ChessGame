import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChessServerNEW
{

  //public static final int MAX_CONNECTIONS = 2;
  public static final int PORT_NUMBER = 6789;

  public static void main(String[] args) throws InterruptedException {

    // make the new socket and threads
    final Connection connection = new Connection();
    //final ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
    Thread t1 = new Thread(() -> connection.createConnection(1));
    Thread t2 = new Thread(() -> connection.createConnection(2));
    t1.start();
    t2.start();
    t1.join();
    t2.join();
  }

  public static class Connection
  {
    ServerSocket serverSocket = null;

    public Connection() {
      try {
        serverSocket = new ServerSocket(PORT_NUMBER);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void createConnection(int i) {
      ServerSocket finalServerSocket = serverSocket;

      int USERS_PORT = 17643;
      System.out.println("Server is active and waiting for players");
      int player = (int) Thread.currentThread().getId();

      try (
        Socket listenerSocket = finalServerSocket.accept();
      ) {
        System.out.println("Player " + player + " has connected");

        InputStream toServer = listenerSocket.getInputStream();
        OutputStream fromServer = listenerSocket.getOutputStream();

        Scanner input = new Scanner(toServer, "UTF-8");
        PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(fromServer, "UTF-8"), true);
        serverPrintOut.println("You have connected to the multiplayer chess server. You are player #" + player);

        boolean done = false;
        // maybe pass it the thread name/number?

        // TODO
        // receive IP and send to other thread.
        // send other threads IP to this.


        while(!done && input.hasNextLine()) {
          String line = input.nextLine();
          // prints and sends back to client
          System.out.println("Recived from client " + player + ": " + line);
          serverPrintOut.println();

          if (line.equals("QUIT")) {
            done = true;
            serverPrintOut.println("Closing Connection.");
          }
        }
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}

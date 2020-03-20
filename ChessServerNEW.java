import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChessServerNEW
{

  //public static final int MAX_CONNECTIONS = 2;
  public static final int PORT_NUMBER = 6789;
  public int playerOne;
  public int playerTwo;

  public static void main(String[] args) throws InterruptedException {
    // make the new socket and threads
    final Connection connection = new Connection();
    //final ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

    Thread t1 = new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        try {
          connection.createConnection(1);
        } catch(InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    Thread t2 = new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        try {
          connection.createConnection(2);
        } catch(InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    t1.start();
    t2.start();
    t1.join();
    t2.join();
  }

  public static class Connection
  {
    ServerSocket serverSocket = null;
    public static String messageInTransit = "";
    public static String lastSentMessage = "";
    public static int sendingPlayer;

    public Connection() {
      try {
        serverSocket = new ServerSocket(PORT_NUMBER);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public void createConnection(int i) throws InterruptedException {
      synchronized(this){
        ServerSocket finalServerSocket = serverSocket;

        int USERS_PORT = 17643;
        System.out.println("Server is active and waiting for players");
        int player = i;

        try (
          Socket listenerSocket = finalServerSocket.accept();
        ) {
          System.out.println("Player " + player + " has connected");

          InputStream toServer = listenerSocket.getInputStream();
          OutputStream fromServer = listenerSocket.getOutputStream();

          Scanner input = new Scanner(toServer, "UTF-8");
          PrintWriter serverSendOut = new PrintWriter(new OutputStreamWriter(fromServer, "UTF-8"), true);

          serverSendOut.println("You have connected to the multiplayer chess server. You are player #" + player);

          boolean done = false;

          notify();
          wait();

          while (messageInTransit != lastSentMessage || input.hasNextLine()) {
            if (messageInTransit != lastSentMessage) {
                // prints and sends to other client
                System.out.println("To " + player + ", " + "From " + sendingPlayer + ": " + messageInTransit);
                serverSendOut.println("To " + player + ", " + "From " + sendingPlayer + ": " + messageInTransit);
                lastSentMessage = messageInTransit;
                notify();
            } else if (input.hasNextLine()) {
                // read message and pass on to other thread.
                sendingPlayer = player;
                messageInTransit = input.nextLine();
                notify();
                wait();
            }
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class ChessServerTEST
{

  // Vector to store active clients
  // TODO remove
  public static Vector<Connection> ar = new Vector<Connection>();
  static int i = 0;
  public static final int PORT_NUMBER = 6789;


  public static void main(String[] args) throws IOException
  {
      // server is listening on port 1234
      ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
      Socket listenerSocket;

      // running infinite loop for getting
      // client request
      while (true)
      {
          // Accept the incoming request
          listenerSocket = serverSocket.accept();

          System.out.println("New client request received : " + listenerSocket);

          InputStream toServer = listenerSocket.getInputStream();
          OutputStream fromServer = listenerSocket.getOutputStream();

          Scanner input = new Scanner(toServer, "UTF-8");
          PrintWriter serverSendOut = new PrintWriter(new OutputStreamWriter(fromServer, "UTF-8"), true);

          // obtain input and output streams
          //DataInputStream dis = new DataInputStream(toServer);
          //DataOutputStream dos = new DataOutputStream(s.getOutputStream());

          System.out.println("Creating a new connection for this client...");

          // Create a new handler object for handling this request.
          Connection mtch = new Connection(listenerSocket, "" + i, input, serverSendOut);

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
          i++;

      }
  }

}

// ClientHandler class
class Connection implements Runnable
{
  private String name;
  private Scanner input;
  private PrintWriter serverSendOut;
  private Socket listenerSocket;
  private boolean isloggedin;

  // constructor
  public Connection(Socket s, String n, Scanner sc, PrintWriter pr) {
      input = sc;
      serverSendOut = pr;
      name = n;
      listenerSocket = s;
      isloggedin = true;
  }

  @Override
  public void run() {
    System.out.println("HERE1");
    while (input.hasNextLine()) {

        System.out.println("HERE2");
        // receive the string
        String messageInTransit = input.nextLine();
        System.out.println(messageInTransit);

        if (messageInTransit.equals("QUIT")){
          try {
            this.isloggedin = false;
            this.listenerSocket.close();
            break;
          } catch (Exception e) {
            e.printStackTrace();
          }
        }

        // search for the recipient in the connected devices list.
        // ar is the vector storing client of active users
        for (Connection mc : ChessServerTEST.ar) {
          System.out.println("here");
          // if the recipient is found, write on its
          // output stream
          if (mc.name.equals(this.name)) {
            mc.serverSendOut.println(this.name+" : "+ messageInTransit);
            break;
          }
        }

    }
      try
      {
          // closing resources
          this.input.close();
          this.serverSendOut.close();

      }catch(Exception e){
          e.printStackTrace();
      }
  }
}

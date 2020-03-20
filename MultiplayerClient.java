import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiplayerClient
{

  public static void main(String[] args) {

    String HOST_NAME = "localhost";
    final int PORT_NUMBER = 6789;

    try (
      Socket userSocket = new Socket(HOST_NAME, PORT_NUMBER);
      // PrintWriter clientOut = new PrintWriter(userSocket.getOutputStream(), true);
      // BufferedReader in = new BufferedReader(
      //   new InputStreamReader(userSocket.getInputStream()));
    ) {

      InputStream toClient = userSocket.getInputStream();
      OutputStream fromClient = userSocket.getOutputStream();

      Scanner input = new Scanner(toClient, "UTF-8");
      PrintWriter clientPrintOut = new PrintWriter(new OutputStreamWriter(fromClient, "UTF-8"), true);

      //System.out.println(userSocket.getLocalAddress().toString());
      clientPrintOut.println(userSocket.getLocalAddress().toString());

      //main event happens here
      while (input.hasNextLine()) {
        // interpret text and send to
        System.out.println(input.nextLine());
      }

      userSocket.close();

    } catch (UnknownHostException e) {
        System.err.println("Don't know about host " + HOST_NAME);
        System.exit(1);
    } catch (IOException e) {
        System.err.println("Couldn't get I/O for the connection to " +
            HOST_NAME);
        System.exit(1);
    }
  }

}

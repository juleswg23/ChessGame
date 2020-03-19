import java.io.*;
import java.net.*;

public class MultiplayerClient {

  public static void main(String[] args) {

    // if (args.length != 2) {
    //   System.err.println(
    //     "Usage: java EchoClient <host name> <port number>");
    //   System.exit(1);
    // }

    final String HOST_NAME = "10.1.0.106";
    final int PORT_NUMBER = 6789;

    try (
      Socket userSocket = new Socket(HOST_NAME, PORT_NUMBER);
      PrintWriter out = new PrintWriter(userSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(
        new InputStreamReader(userSocket.getInputStream()));
    ) {
      BufferedReader stdIn =
          new BufferedReader(new InputStreamReader(System.in));
      String fromServer;
      String fromUser;

      while ((fromServer = in.readLine()) != null) {
        System.out.println("Server: " + fromServer);

        if (fromServer.startsWith("NEW")) {

        } else if (fromServer.startsWith("MOVE")) {

        } else if (fromServer.equals("QUIT"))
          break;

        fromUser = stdIn.readLine();
        if (fromUser != null) {
          System.out.println("Client: " + fromUser);
          out.println(fromUser);
        }
      }
    } catch (UnknownHostException e) {
        System.err.println("Don't know about host " + hostName);
        System.exit(1);
    } catch (IOException e) {
        System.err.println("Couldn't get I/O for the connection to " +
            hostName);
        System.exit(1);
    }
  }

}

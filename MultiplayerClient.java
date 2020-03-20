import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.*;


public class MultiplayerClient
{

  public static void main(String[] args) {
    //String playerName = args[0];

    String HOST_NAME = "localhost";
    final int PORT_NUMBER = 6789;

    try (
      Socket userSocket = new Socket(HOST_NAME, PORT_NUMBER);
    ) {

      InputStream toClient = userSocket.getInputStream();
      OutputStream fromClient = userSocket.getOutputStream();

      ObjectInputStream is = new ObjectInputStream(userSocket.getInputStream());
      ObjectOutputStream os = new ObjectOutputStream(userSocket.getOutputStream());

      Scanner input = new Scanner(toClient, "UTF-8");
      PrintWriter clientSendOut = new PrintWriter(new OutputStreamWriter(fromClient, "UTF-8"), true);

      //temp
      Scanner userType = new Scanner(System.in);

      Object objectToSend = "";
      Object objectToReceive = is.readObject();
      System.out.println(messageToReceive);

      int player = Integer.parseInt(messageToReceive.substring(messageToReceive.length() - 1));

      if (player == 1) {
        // TODO last thing
        // replace this with the game the gets passed back and forth
        // instead of the user typing.
        while (userType.hasNextLine()) {
          os.writeObject(new Board());
          break;
        }
      }

      while (true) {
        objectToReceive = is.readObject();
        System.out.println(objectToReceive);

        while (userType.hasNextLine()) {
          os.writeObject(new Board());
          break;
        }
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

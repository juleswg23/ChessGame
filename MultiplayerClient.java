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

      Scanner input = new Scanner(toClient, "UTF-8");
      PrintWriter clientSendOut = new PrintWriter(new OutputStreamWriter(fromClient, "UTF-8"), true);

      ObjectInputStream is = new ObjectInputStream(toClient);
      ObjectOutputStream os = new ObjectOutputStream(fromClient);

      //temp
      Scanner userType = new Scanner(System.in);

      String messageToSend = "";
      String messageToReceive = input.nextLine();
      System.out.println(messageToReceive);

      Object objectToSend = "";
      Object objectToReceive = null;

      //int player = 2; //this was a test
      int player = Integer.parseInt(messageToReceive.substring(messageToReceive.length() - 1));

      if (player == 1) {
        // TODO last thing
        // replace this with the game the gets passed back and forth
        // instead of the user typing.
        while (userType.hasNextLine()) {
          clientSendOut.println(userType.nextLine());
          os.writeObject(new Board());
          break;
        }
      }

      while (input.hasNextLine()) {
        messageToReceive = input.nextLine();
        System.out.println(messageToReceive);
        try {
          objectToReceive = is.readObject();
          System.out.println(objectToReceive);
        } catch (Exception e) {
          e.printStackTrace();
        }

        while (userType.hasNextLine()) {
          clientSendOut.println(userType.nextLine());
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

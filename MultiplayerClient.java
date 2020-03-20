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
    ) {

      InputStream toClient = userSocket.getInputStream();
      OutputStream fromClient = userSocket.getOutputStream();

      Scanner input = new Scanner(toClient, "UTF-8");
      PrintWriter clientPrintOut = new PrintWriter(new OutputStreamWriter(fromClient, "UTF-8"), true);

      clientPrintOut.println(userSocket.getLocalAddress().toString());
      //clientPrintOut.println("QUIT");

      //temp
      Scanner userType = new Scanner(System.in);

      String messageToReceive = "";
      String messageToSend = "";


      while (true) {
        messageToReceive = waitForInput(input);
        System.out.println(messageToReceive);
        if (messageToReceive.contains("QUIT")) {
          break;
        }

        messageToSend = waitForInput(userType);
        clientPrintOut.println(messageToSend);
      }

      // receiving and sending happens here
      // while (input.hasNextLine()) {
      //
      //   messageToReceive = input.nextLine();
      //
      //   if (messageToReceive != null) {
      //     System.out.println(messageToReceive);
      //     if (messageToReceive.contains("QUIT")) {
      //       break;
      //     }
      //   }
      //
      //   messageToSend = userType.nextLine();
      //
      //   if (messageToSend != null) {
      //     clientPrintOut.println(messageToSend);
      //   }
      //
      // }
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

  public static String waitForInput(Scanner sc) {
    String message = "";
    while (sc.hasNextLine()) {
      message = sc.nextLine();
      break;
    }
    return message;
  }
}

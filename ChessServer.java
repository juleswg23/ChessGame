import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChessServer
{

  public static final int MAX_CONNECTIONS = 2;
  public static final int PORT_NUMBER = 6789;

  public static void main(String[] args) {
    operateMultipleServers();
  }

  public static void operateMultipleServers() {
    //Try connect to the server on an unused port eg 6789. A successful connection will return a socket
    ServerSocket serverSocket = null;

    try {
      serverSocket = new ServerSocket(PORT_NUMBER);
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Server is active and waiting for players");

    for (int i = 0; i < MAX_CONNECTIONS; i++) {
      ServerSocket finalServerSocket = serverSocket;

      //create thread
      Runnable runnable = () -> {
        int player = Integer.parseInt(Thread.currentThread().getName().substring(5, 6));

        try (
          Socket listener = finalServerSocket.accept();
        ) {
          System.out.println("Player " + player + " has connected");

          InputStream toServer = listener.getInputStream();
          OutputStream fromServer = listener.getOutputStream();

          Scanner input = new Scanner(toServer, "UTF-8");
          PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(fromServer, "UTF-8"), true);
          serverPrintOut.println("You have connected to the multiplayer chess server. You are player #" + player);

          boolean done = false;
          // maybe pass it the thread name/number?
          ChessServerProtocol csp = new ChessServerProtocol(player);

          while(!done && input.hasNextLine()) {
            String line = input.nextLine();
            // prints and sends back to client
            System.out.println("Recived from client " + player + ": " + line);
            serverPrintOut.println(csp.processInput(line));

            if (line.equals("QUIT")) {
              done = true;
              serverPrintOut.println("Closing Connection.");
            }
          }
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      };
      Executors.newCachedThreadPool().execute(runnable);
    }
  }

  // public static void operateServer() {
  // //Try connect to the server on an unused port eg 6789. A successful connection will return a socket
  //   ServerSocket serverSocket = null;
  //
  //   try(ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
  //     Socket connectionSocket = serverSocket.accept();
  //
  //     //Create Input&Outputstreams for the connection
  //     InputStream inputToServer = connectionSocket.getInputStream();
  //     OutputStream outputFromServer = connectionSocket.getOutputStream();
  //
  //     Scanner scanner = new Scanner(inputToServer, "UTF-8");
  //     PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);
  //
  //     serverPrintOut.println("You have connected to the multiplayer chess server.");
  //
  //     //Have the server take input from the client and echo it back
  //     //This should be placed in a loop that listens for a terminator text e.g. bye
  //     boolean done = false;
  //     ChessServerProtocol csp = new ChessServerProtocol();
  //
  //     while(!done && scanner.hasNextLine()) {
  //       String line = scanner.nextLine();
  //       // sends back to client
  //       serverPrintOut.println(csp.processInput(line));
  //
  //       System.out.println("Recived from client: " + line);
  //
  //       if (line.equals("QUIT")) {
  //         done = true;
  //       }
  //     }
  //   } catch (IOException e) {
  //     e.printStackTrace();
  //   }
  // }

}

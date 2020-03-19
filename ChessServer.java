import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChessServer{

  public static void main(String[] args) {
    operateServer();
  }

  public static void operateServer() {
  //Try connect to the server on an unused port eg 6789. A successful connection will return a socket
    try(ServerSocket serverSocket = new ServerSocket(6789)) {
      Socket connectionSocket = serverSocket.accept();

      //Create Input&Outputstreams for the connection
      InputStream inputToServer = connectionSocket.getInputStream();
      OutputStream outputFromServer = connectionSocket.getOutputStream();

      Scanner scanner = new Scanner(inputToServer, "UTF-8");
      PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

      serverPrintOut.println("Hello World! Enter Peace to exit.");

      //Have the server take input from the client and echo it back
      //This should be placed in a loop that listens for a terminator text e.g. bye
      boolean done = false;
      ChessServerProtocol csp = new ChessServerProtocol();

      while(!done && scanner.hasNextLine()) {
        String line = scanner.nextLine();
        serverPrintOut.println(csp.processInput(line));

        if (line.equals("QUIT")) {
          done = true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

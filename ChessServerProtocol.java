import java.net.*;
import java.io.*;

public class ChessServerProtocol {

  String player;

  public ChessServerProtocol() {
    player = "one?";
  }

  public String processInput(String theInput) {
    String theOutput = "";

    if (theInput.startsWith("NEW")) {
      theOutput = theInput;
    } else if (theInput.startsWith("MOVE")) {
      theOutput = theInput;
    } else if (theInput.equals("QUIT")) {
      theOutput = theInput;
    } else {
      theOutput = "ERR - illegal start of input.";
    }

    return theOutput;
  }


}

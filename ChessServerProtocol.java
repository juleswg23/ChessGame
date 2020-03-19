import java.net.*;
import java.io.*;

public class ChessServerProtocol {

  String player;

  public ChessServerProtocol(String p) {
    player = p;
  }

  public String processInput(String theInput) {
    String theOutput = "";

    if (theInput.startsWith("NEW")) {
      theOutput = theInput;
    } else if (theInput.startsWith("MOVE")) {
      theOutput = theInput;
    } else {
      theOutput = "ERR - illegal start of input.";
    }

    return theOutput;
  }


}

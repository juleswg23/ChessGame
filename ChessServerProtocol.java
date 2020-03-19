import java.net.*;
import java.io.*;

public class ChessServerProtocol {

  int player;

  public ChessServerProtocol(int p) {
    player = p;
  }

  public String processInput(String theInput) {
    String theOutput = "";

    if (theInput.startsWith("NEW")) {
      theOutput = "NEW GAME";

    } else if (theInput.startsWith("MOVE")) {
      if (theInput.length() > 8) {
        theOutput = "Move " + theInput.substring(5, 6) + ", " +
                    theInput.substring(6, 7) + " to " +
                    theInput.substring(7, 8) + ", " +
                    theInput.substring(8, 9) + ".";
      } else {
        theOutput = "ERR - Incorrect use of MOVE, use: \"MOVE 3435\" ";
      }

    } else if (theInput.equals("QUIT")) {
      theOutput = theInput;
    } else {
      theOutput = "ERR - illegal start of input.";
    }

    return theOutput;
  }

}

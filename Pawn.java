import java.awt.Point;
import java.util.ArrayList;
import javax.swing.*;

public class Pawn extends Piece
{

  public Pawn(Point p, boolean t) {
    position = new Point(p);
    setWhite(t);
  }

  @Override
  public boolean captureMoveLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    if (newPosition.equals(position)) return false;
    if (getWhite()) {
      if (Math.abs(position.x - newPosition.x) == 1) {
        if (position.y - newPosition.y == -1) return true;
        else return false;
      }
    } else {
      if (Math.abs(position.x - newPosition.x) == 1) {
        if (position.y - newPosition.y == 1) return true;
        else return false;
      }
    }
    return false;
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    if (newPosition.equals(position)) return false;
    //White pawns
    if (getWhite()) {
      if (newPosition.x == position.x) {
        if (position.y == 1 && newPosition.y == 3) return true;
        else if (position.y - newPosition.y == -1) return true;
        else return false;
      }
    }
    //Black pawns
    else {
       if (newPosition.x == position.x) {
        if (position.y == 6 && newPosition.y == 4) return true;
        else if (position.y - newPosition.y == 1) return true;
        else return false;
      }
    }
    return false;
  }

  public ArrayList<Point> jumpedSquares (Point toMove) {
    ArrayList<Point> arr = new ArrayList<Point>();
    if (position.y == 1 && toMove.y == 3) arr.add(new Point(position.x, 2));
    if (position.y == 6 && toMove.y == 4) arr.add(new Point(position.x, 5));
    //this last one only for pawns because they cannot capture if moving vertical.
    if (position.x == toMove.x) arr.add(toMove);

    return arr;
  }

  @Override
  public void promotion(Point destPos, ArrayList<Piece> pieces) {
    Piece piece = null;
      if (position.y == 7 || position.y == 0) {

        String[] filePaths = new String[4];
        String[] type = {"B", "N", "R", "Q"};
        String c = "W";
        if (!getWhite()) c = "B";

        for (int i = 0; i < filePaths.length; i++) {
          filePaths[i] = "Pictures/" + c + type[i] + ".png";
        }

        ImageIcon[] imageOptions = new ImageIcon[4];
        for (int i = 0; i < filePaths.length; i++) {
          try {
            imageOptions[i] = new ImageIcon(filePaths[i]);
          } catch (Exception e) {}
        }

        int promoterWindow = JOptionPane.showOptionDialog(null, "Pick a piece for promotion",
                        "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, imageOptions, imageOptions);
        switch(promoterWindow) {
          case 0:
            piece = new Bishop(destPos, getWhite());
            break;
          case 1:
            piece = new Knight(destPos, getWhite());
            break;
          case 2:
            piece = new Rook(destPos, getWhite());
            break;
          case 3:
            piece = new Queen(destPos, getWhite());
            break;
          default:
            piece = new Queen(destPos, getWhite());
            break;
        }

        int location = -1; //Find index of p in pieces and remove it

        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i) == this) location = i;
        }
        pieces.remove(location);
        pieces.add(piece);
      }
    }
}

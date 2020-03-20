import java.awt.Point;
import java.util.ArrayList;
import javax.swing.*;

abstract class Piece
{
//maybe make private
  public Point position;
  private boolean white;
  private boolean clicked = false;

  public abstract boolean isLegal(Point toMove);
  public abstract ArrayList<Point> jumpedSquares(Point toMove);
  //public abstract boolean checkMate();

  public void setPos(Point p) {
    position.setLocation(p);
  }

  public void promotion(Point destPos, ArrayList<Piece> pieces) {
    return;
  }

  public boolean getWhite() {
    return white;
  }

  public void setWhite(boolean w) {
    white = w;
  }

  public boolean captureMoveLegal(Point toMove) {
    return isLegal(toMove);
  }

  public boolean getClicked() {
    return clicked;
  }

  public void setClicked(boolean c) {
    clicked = c;
  }

  public String toString() {
    String str = "B";
    if (white) str = "W";
    str += this.getClass().getName().substring(0, 1);
    if (clicked) str += "H";
    return str;
  }

  public int movePiece(ArrayList<Piece> pieces, Point originalPos, Point destPos) {
    if (originalPos == destPos) return -1;
    boolean isLegal = true;
    int toCapture = -1;
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i).position.x == destPos.x && pieces.get(i).position.y == destPos.y) toCapture = i;
    }
    if (toCapture == -1) {
      if (this.isLegal(destPos)) {
        ArrayList<Point> a = null;
        a = jumpedSquares(destPos);
        if (jumpedSquaresHelper(a, pieces)) {
          this.position.setLocation(destPos);
          promotion(destPos, pieces);
          return -1;
        }
      }
    } else {
      if (this.captureMoveLegal(destPos) && getWhite() != pieces.get(toCapture).getWhite()) {
        this.position.setLocation(destPos);
        // pieces.remove(toCapture);
        return toCapture;
      }
    }
    return -2;
  }

  public boolean jumpedSquaresHelper(ArrayList<Point> arr, ArrayList<Piece> pieces) {
    for (Point square : arr) {
      for (Piece p : pieces) {
        if (p.position.equals(square)) return false;
      }
    }
    return true;
  }
}

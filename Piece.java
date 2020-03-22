import java.awt.Point;
import java.util.ArrayList;
import java.io.Serializable;


abstract class Piece implements Serializable
{
//maybe make private
  public Point position;
  private boolean white;
  private boolean clicked = false;

  public abstract boolean isLegal(Point toMove);
  public abstract ArrayList<Point> jumpedSquares(Point toMove);

  public void setPos(Point p) {
    position.setLocation(p);
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
    else += "U"

    // new but not needed
    str += position.x;
    str += position.y;

    return str;
  }

  public boolean equals(Piece p) {
    return getWhite() == p.getWhite() &&
           getClicked() == p.getClicked() &&
           position.x == p.position.x && position.y == p.position.y;
  }

}

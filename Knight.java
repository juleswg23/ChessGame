import java.awt.Point;
import java.util.ArrayList;

public class Knight extends Piece
{

  public Knight(Point p, boolean t) {
    position = new Point(p);
    setWhite(t);
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    else if (Math.abs(position.x-newPosition.x) == 1 && Math.abs(position.y - newPosition.y) == 2) return true;
    else if (Math.abs(position.x-newPosition.x) == 2 && Math.abs(position.y - newPosition.y) == 1) return true;
    else return false;
  }

  public ArrayList<Point> jumpedSquares (Point toMove) {
    return new ArrayList<Point>();
  }

  @Override
  public String toString() {
    String str = "BN";
    if (getWhite()) str = "WN";
    if (getClicked()) str = str + "H";
// this is new but not needed
    str += position.x;
    str += position.y;
    return str;
  }

}

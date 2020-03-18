import java.awt.Point;
import java.util.ArrayList;

public class Rook extends Piece
{
  public Rook(Point p, boolean t) {
    position = new Point(p);
    white = t;
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    else if (newPosition.equals(position)) return false;
    else if (newPosition.x == position.x || newPosition.y == position.y) return true;
    else return false;
  }

  public ArrayList<Point> jumpedSquares(Point newPosition) {
    ArrayList<Point> arr = new ArrayList<Point>();

    if (position.x == newPosition.x) { // move is vertical
      // n is positive if we're moving up
      int n = (newPosition.y-position.y)/Math.abs(position.y-newPosition.y);
      int i = position.y + n;
      while (i != newPosition.y) {
        arr.add(new Point(position.x, i));
        i += n;
      }
    } else { // move is horizontal
      int n = (newPosition.x-position.x)/Math.abs(position.x-newPosition.x);
      int i = position.x + n;
      while (i != newPosition.x) {
        arr.add(new Point(i, position.y));
        i += n;
      }
    }
    return arr;
  }

}

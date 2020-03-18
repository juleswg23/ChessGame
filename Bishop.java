import java.awt.Point;
import java.util.ArrayList;

public class Bishop extends Piece
{
  public Bishop(Point p, boolean t) {
    position = new Point(p);
    white = t;
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    else if (newPosition.x == position.x || newPosition.y == position.y) return false;
    else if ((position.x + position.y) % 2 == (newPosition.x + newPosition.y) % 2) {
      if (Math.abs(position.x - newPosition.x) == Math.abs(position.y - newPosition.y)) return true;
    }
    return false;
  }

  public ArrayList jumpedSquares (Point toMove) {
    ArrayList arr = new ArrayList<Point>();
    return arr;
  }

}

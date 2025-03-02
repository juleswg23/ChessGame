import java.awt.Point;
import java.util.ArrayList;

public class King extends Piece
{
  public King(Point p, boolean t) {
    position = new Point(p);
    white = t;
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    else if (newPosition.equals(position)) return false;
    else if (Math.abs(position.x-newPosition.x) > 1) return false;
    else if (Math.abs(position.y-newPosition.y) > 1) return false;
    else return true;
  }

  public ArrayList<Point> jumpedSquares (Point toMove) {
    return new ArrayList<Point>();
  }

}

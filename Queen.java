import java.awt.Point;
import java.util.ArrayList;

public class Queen extends Piece
{
  public Queen(Point p, boolean t) {
    position = new Point(p);
    white = t;
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    else if (newPosition.equals(position)) return false;

    boolean diagonal = false;
    boolean horizontal_vertical = false;
    //Check for diagonal move
    if ((position.x + position.y) % 2 == (newPosition.x + newPosition.y) % 2) {
      if (Math.abs(position.x - newPosition.x) == Math.abs(position.y - newPosition.y)) diagonal = true;
    }
    //Check for horizontal/vertical move
    if (newPosition.x == position.x || newPosition.y == position.y) horizontal_vertical = true;

    return diagonal || horizontal_vertical;
  }

  public ArrayList jumpedSquares(Point toMove) {
    ArrayList arr = new ArrayList<Point>();
    return arr;
  }

}

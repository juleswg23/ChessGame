import java.awt.Point;
import java.util.ArrayList;

public class Pawn extends Piece
{
  public Pawn(Point p, boolean t) {
    position = new Point(p);
    white = t;
  }
  @Override
  public boolean captureMoveLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    if (newPosition.equals(position)) return false;
    if (white) {
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
    if (white) {
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

}

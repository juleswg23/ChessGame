import java.awt.Point;
import java.util.ArrayList;

public class King extends Piece
{

  public King(Point p, boolean t) {
    position = new Point(p);
    setWhite(t);
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

  public ArrayList<Point> possibleMoves() {
    ArrayList<Point> moves = new ArrayList<Point>();

    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        Point newPosition = new Point(position.x + i, position.y + j);
        if (newPosition.x <= 7 && newPosition.x >= 0 && newPosition.y <= 7 && newPosition.y >= 0) {
          if (newPosition != this.position) moves.add(newPosition);
        }
      }
    }
    return moves;
  }

}

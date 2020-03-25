import java.awt.Point;
import java.util.ArrayList;

public class Queen extends Piece
{

  public Queen(Point p, boolean t) {
    position = new Point(p);
    setWhite(t);
  }

  public boolean isLegal(Point newPosition) {
    if (newPosition.x > 7 || newPosition.x < 0 || newPosition.y > 7 || newPosition.y < 0) return false;
    else if (newPosition.equals(position)) return false;

    //Check for diagonal move
    if ((position.x + position.y) % 2 == (newPosition.x + newPosition.y) % 2) {
      if (Math.abs(position.x - newPosition.x) == Math.abs(position.y - newPosition.y)) return true;
    }
    //Check for horizontal/vertical move
    if (newPosition.x == position.x || newPosition.y == position.y) return true;
    return false;
  }

  public ArrayList<Point> jumpedSquares(Point newPosition) {
    ArrayList<Point> arr = new ArrayList<Point>();
    int xStep = 0;
    int yStep = 0;

    try {
      xStep = (newPosition.x-position.x)/Math.abs(position.x-newPosition.x);
    } catch (Exception e) {}

    try {
      yStep = (newPosition.y-position.y)/Math.abs(position.y-newPosition.y);
    } catch (Exception e) {}

    int i = position.x + xStep;
    int j = position.y + yStep;

    while (i != newPosition.x || j != newPosition.y) {
      arr.add(new Point(i, j));
      i += xStep;
      j += yStep;
    }
    return arr;
  }

  public ArrayList<Point> possibleMoves() {
    ArrayList<Point> moves = new ArrayList<Point>();

    for (int rowInc = -1; rowInc <= 1; rowInc++) {
      for (int colInc = -1; colInc <= 1; colInc++) {
        int i = 1;
        int j = 1;
        if (colInc == 0 && rowInc == 0) continue;
        while (position.x + (rowInc * i) <= 7 && position.y + (colInc * j) <= 7 &&
               position.x + (rowInc * i) >= 0 && position.y + (colInc * j) >= 0) {
          moves.add(new Point(position.x + (rowInc * i), position.y + (colInc * j)));
          i++;
          j++;
        }
      }
    }
    return moves;
  }


}

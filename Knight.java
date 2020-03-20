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

  // public boolean checkMate(ArrayList<Piece> pieces) {
  //   Point originalPosition = this.position;
  //   for (int i = -2; i < 3; i++) {
  //     if (i != 0) {
  //       Point newLocationOne = new Point(position.x + i, position.y + (3-Math.abs(i)));
  //       Point newLocationTwo = new Point(position.x + i, position.y + (3-Math.abs(i)));
  //
  //
  //
  //     }
  //   }
  //
  //   return true;
  // }


  public boolean jumpedSquares(Point toMove, ArrayList<Piece> pieces) {
    return true;
  }

  @Override
  public String toString() {
    String str = "BN";
    if (getWhite()) str = "WN";
    if (getClicked()) str = str + "H";
    return str;
  }

}

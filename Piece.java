import java.awt.Point;
import java.util.ArrayList;

abstract class Piece
{
  public Point position;
  public boolean white;
  public boolean clicked = false;

  public abstract boolean isLegal(Point toMove);
  public abstract String toString();
  public abstract ArrayList jumpedSquares(Point toMove);

  public void setPos(Point p) {
    position.setLocation(p);
  }
  public boolean getWhite() {
    return white;
  }
  public boolean captureMove(Point toMove) {
    return isLegal(toMove);
  }

}

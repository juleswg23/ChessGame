import java.awt.*;

abstract class Piece
{
  public Point position;
  public int columnPos;
  public int rowPos;
  public boolean white;
  public boolean clicked = false;

  public abstract boolean isLegal(int newC, int newR);
  public abstract String toString();

  public void setPos(int newC, int newR) {
    columnPos = newC;
    rowPos = newR;
  }
  public boolean getWhite() {
    return white;
  }
  public boolean captureMove(int newC, int newR) {
    return isLegal(newC, newR);
  }
}

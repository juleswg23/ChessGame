public class Bishop extends Piece
{
  public Bishop(int x, int y, boolean t) {
    xPosition = x;
    yPosition = y;
    white = t;
  }
  public boolean isLegal(int newX, int newY) {
    if (newX > 7 || newX < 0 || newY > 7 || newY < 0) return false;
    else if (newX == xPosition || newY == yPosition) return false;
    else if ((xPosition + yPosition) % 2 == (newX + newY % 2)) {
      int changeX = xPosition - newX;
      int changeY = yPosition - newY;
      if (changeX == changeY) return true;
      else if (-changeX == changeY) return true;
      else return false;
    } else return false;
  }


}

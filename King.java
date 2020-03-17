public class King extends Piece
{
  public King(int c, int r, boolean t) {
    columnPos = r;
    rowPos = c;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) return false;
    else if (newC == columnPos && newR == rowPos) return false;
    else if (Math.abs(columnPos-newC) > 1) return false;
    else if (Math.abs(rowPos-newR) > 1) return false;
    else return true;
  }


}

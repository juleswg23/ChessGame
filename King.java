public class King extends Piece
{
  public King(int c, int r, boolean t) {
    columnPos = c;
    rowPos = r;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) return false;
    else if (newC == columnPos && newR == rowPos) return false;
    else if (Math.abs(columnPos-newC) > 1) return false;
    else if (Math.abs(rowPos-newR) > 1) return false;
    else return true;
  }

  public String toString() {
    if (!clicked) {
      if (white) return "WK";
      else return "BK";
    } else {
      if (white) return "WKH";
      else return "BKH";
    }
  }

}

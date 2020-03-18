public class Knight extends Piece
{
  public Knight(int c, int r, boolean t) {
    columnPos = c;
    rowPos = r;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) return false;
    else if (newC == columnPos && newR == rowPos) return false;
    else if (Math.abs(columnPos-newC) == 1 && Math.abs(rowPos - newR) == 2) return true;
    else if (Math.abs(columnPos-newC) == 2 && Math.abs(rowPos - newR) == 1) return true;
    else return false;
  }

  public String toString() {
    if (!clicked) {
      if (white) return "WN";
      else return "BN";
    } else {
      if (white) return "WNH";
      else return "BNH";
    }

  }

}

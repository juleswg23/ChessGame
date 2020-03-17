public class Bishop extends Piece
{
  public Bishop(int c, int r, boolean t) {
    columnPos = c;
    rowPos = r;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) {
      return false;
    } else if (newC == columnPos || newR == rowPos) {
      return false;
    } else if ((columnPos + rowPos) % 2 == (newC + newR) % 2) {
      if (Math.abs(columnPos - newC) == Math.abs(rowPos - newR)) return true;
    }
    return false;
  }

  public String toString() {
    return "B";
  }

}

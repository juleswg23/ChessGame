public class Queen extends Piece
{
  public Queen(int c, int r, boolean t) {
    columnPos = c;
    rowPos = r;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) return false;
    else if (newC == columnPos && newR == rowPos) return false;

    boolean diagonal = false;
    boolean horizontal_vertical = false;
//Check for diagonal move
    if ((columnPos + rowPos) % 2 == (newC + newR) % 2) {
      if (Math.abs(columnPos - newC) == Math.abs(rowPos - newR)) diagonal = true;
    }
//Check for horizontal/vertical move
    if (newC == columnPos || newR == rowPos) horizontal_vertical = true;

    return diagonal || horizontal_vertical;
  }

  public String toString() {
    if (!clicked) {
      if (white) return "WQ";
      else return "BQ";
    } else {
      if (white) return "WQH";
      else return "BQH";
    }
  }


}

public class Pawn extends Piece
{
  public Pawn(int c, int r, boolean t) {
    columnPos = c;
    rowPos = r;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) return false;
    if (newC == columnPos && newR == rowPos) return false;
    //White pawns
    if (white) {
      if (newC == columnPos) {
        if (rowPos == 1 && newR == 3) return true;
        else if (rowPos - newR == -1) return true;
        else return false;
      }
      else if (Math.abs(columnPos - newC) == 1) {
        if (rowPos - newR == -1) return true;
        else return false;
      }
    }
    //Black pawns
    else {
       if (newC == columnPos) {
        if (rowPos == 6 && newR == 4) return true;
        else if (rowPos - newR == 1) return true;
        else return false;
      }
      else if (Math.abs(columnPos - newC) == 1) {
        if (rowPos - newR == 1) return true;
        else return false;
      }
    }
    return false;
  }

  public String toString() {
    return "P";
  }


}

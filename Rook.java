public class Rook extends Piece
{
  public Rook(int c, int r, boolean t) {
    columnPos = r;
    rowPos = c;
    white = t;
  }

  public boolean isLegal(int newC, int newR) {
    if (newC > 7 || newC < 0 || newR > 7 || newR < 0) return false;
    else if (newC == columnPos && newR == rowPos) return false;
    else if (newC == columnPos || newR == rowPos) return true;
    else return false;
  }

}

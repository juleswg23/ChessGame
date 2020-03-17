public class Square {

  private final int columnPos;
  private final int rowPos;
  private Piece piece;

  public Square(int c, int r, Piece p) {
    columnPos = c;
    rowPos = r;
    piece = p;
  }

  public Square(int c, int r) {
    this(c, r, null);
  }

  public Piece getPiece() {
    return piece;
  }

  public void setPiece(Piece p) {
    piece = p;
  }




}

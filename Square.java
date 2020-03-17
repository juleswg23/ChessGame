public class Square {

  private final int xPosition;
  private final int yPosition;
  private Piece piece;

  public Square(int x, int y, Piece p) {
    xPosition = x;
    yPosition = y;
    piece = p;
  }

  public Square(int x, int y) {
    this(x, y, null);
  }

  public Piece getPiece() {
    return piece;
  }

  public Piece setPiece(Piece p) {
    piece = p;
  }




}

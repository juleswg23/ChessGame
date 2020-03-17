abstract class Piece
{
  public int columnPos;
  public int rowPos;
  public boolean white;

  public abstract boolean isLegal(int newC, int newR);
  public abstract String toString();

  public void setPos(int newC, int newR) {
    columnPos = newC;
    rowPos = newR;
  }
  public boolean getWhite() {
    return white;
  }

}

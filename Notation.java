import java.awt.*;
import java.util.*;
import java.io.Serializable;


public class Notation implements Serializable
{
  ArrayList<Move> moves;

  public Notation() {
    moves = new ArrayList<>();
  }

  public void add(Piece p, Point destPos, Boolean capture) {
    Move m = new Move(p, destPos, capture);
    moves.add(m);
  }

  public void add(Piece p, Point destPos, Boolean capture, Boolean check) {
    Move m = new Move(p, destPos, capture, check);
    moves.add(m);
  }

  public void add(Piece p, Point destPos, Boolean capture, Boolean check, Boolean mate) {
    Move m = new Move(p, destPos, capture, check, mate);
    moves.add(m);
  }

  public void clear() {
    moves.clear();
  }

  @Override
  public String toString() {
    String str = "";
    int count = 1;
    for (int i = 0; i < moves.size(); i++) {
      str += count + ". " + moves.get(i);
      i++;
      try {
        str += "\t" + moves.get(i) + "\n";
      } catch (Exception e) {

      }
      count++;
    }
    return str;
  }

}

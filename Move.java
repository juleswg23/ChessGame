import java.awt.*;
import java.util.*;

public class Move {
  String move = "";
  final int a = (char) 'a';

  //Normal move
  public Move(Piece p, Point destPos, Boolean capture) {
    switch (p.getClass().getName()) {
      case "Rook":
        move += "R";
        break;
      case "Bishop":
        move += "B";
        break;
      case "King":
        move += "K";
        break;
      case "Queen":
        move += "Q";
        break;
      case "Knight":
        move += "N";
        break;
      case "Pawn":
        if (capture) {
          char f = (char) (a + p.position.getX());
          move += f;
        }
    }
    if (capture) move += "x";

    char file = (char) (a + destPos.getX());
    move += file;
    move += (int) (1 + destPos.getY());
  }

  public Move(Piece p, Point destPos, Boolean capture, Boolean check) {
    this(p, destPos, capture);
    if (check) move += "+";
  }

  public Move(Piece p, Point destPos, Boolean capture, Boolean check, Boolean mate) {
    this(p, destPos, capture);
    if (check && !mate) move += "+";
    else if (mate) move += "#";
  }

  @Override
  public String toString() {
    return move;
  }
}

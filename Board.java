/**
 * Board class has a visual represenation of the board.
 * @author Stefan W-G and co.
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

  public final int ROWS = 8;
  public final int COLUMNS = 8;

  public Board() {
    super();
    this.setLayout(new GridLayout(ROWS, COLUMNS));
    drawBoard();
  }

  public void drawBoard() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS ; j++) {
        
        JLabel label = new JLabel();
        label.setOpaque(true);
        if ((i + j) % 2 == 0) label.setBackground(Color.white);
        else label.setBackground(Color.black);
        add(label);
      }
    }
  }

  public void newGame() {
    //add pieces to board
  }
}

/**
 * Board class has a visual represenation of the board.
 * @author Stefan W-G and co.
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Board extends JPanel
{

  public final int ROWS = 8;
  public final int COLUMNS = 8;
  public final int WIDTH = 512;
  public final int HEIGHT = 512;
  public final int CELL_SIZE = WIDTH/8;


  public Board() {
    super();
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
  }

  @Override
  public void paintComponent(Graphics g) {
      drawBoard(g);
  }
//TODO Add drawing of pieces
  public void drawBoard(Graphics g) {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS ; j++) {
        if ((i + j) % 2 == 0) {
          g.setColor(Color.white);
        } else {
          g.setColor(new Color(128,128,128));
        }
        g.fillRect(i*CELL_SIZE, j*CELL_SIZE, CELL_SIZE, CELL_SIZE);
      }
    }
  }

  public void newGame() {
    //add pieces to board
  }
}

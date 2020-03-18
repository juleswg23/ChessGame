/**
 * Board class has a visual represenation of the board.
 * @author Stefan W-G and co.
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel
{

  public final int ROWS = 8;
  public final int COLUMNS = 8;
  public final int WIDTH = 512;
  public final int HEIGHT = 512;
  public final int CELL_SIZE = WIDTH/8;

  private ArrayList<Piece> pieces = new ArrayList<>();


  public Board() {
    super();
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    newGame();
    createMouseListener();
  }

  @Override
  public void paintComponent(Graphics g) {
      drawBoard(g);
  }
  public void drawBoard(Graphics g) {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {

        if ((row + col) % 2 == 0) {
          g.setColor(new Color(189,183,107));
        } else {
          g.setColor(new Color(139,69,19));
        }
        g.fillRect((col)*CELL_SIZE, (7 - row)*CELL_SIZE, CELL_SIZE, CELL_SIZE);
      }
    }
    for (Piece p : pieces) {
      String filePath = "Pictures/" + p.toString() + ".png";
      int x = (7 - p.columnPos) * CELL_SIZE;
      int y = (7 - p.rowPos) * CELL_SIZE;
      try {
        BufferedImage img = ImageIO.read(new File(filePath));
        Image newImage = img.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
        g.drawImage(newImage, x, y, null);
      } catch (Exception e) {

      }
    }
  }

  public void newGame() {
    // set pawns
    for (int col = 0; col < COLUMNS; col++) {
      // white pawns then black ones
      pieces.add(new Pawn(col, 1, true));
      pieces.add(new Pawn(col, 6, false));
    }

    // rooks
    pieces.add(new Rook(0, 0, true));
    pieces.add(new Rook(7, 0, true));
    pieces.add(new Rook(0, 7, false));
    pieces.add(new Rook(7, 7, false));

    // knights
    pieces.add(new Knight(1, 0, true));
    pieces.add(new Knight(6, 0, true));
    pieces.add(new Knight(1, 7, false));
    pieces.add(new Knight(6, 7, false));

    // bishops
    pieces.add(new Bishop(2, 0, true));
    pieces.add(new Bishop(5, 0, true));
    pieces.add(new Bishop(2, 7, false));
    pieces.add(new Bishop(5, 7, false));

    // queens
    pieces.add(new Queen(3, 0, true));
    pieces.add(new Queen(3, 7, false));

    // kings
    pieces.add(new King(4, 0, true));
    pieces.add(new King(4, 7, false));
  }

  public void createMouseListener() {
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }
        @Override
          public void mousePressed(MouseEvent e) {
              int c = 7 - e.getX() / CELL_SIZE;
              int r = 7 - e.getY() / CELL_SIZE;
              for (Piece p : pieces) {
                if (p.columnPos == c && p.rowPos == r) {
                  p.clicked = !p.clicked;
                  repaint();
                  break;
                }
              }
          }

          @Override
          public void mouseReleased(MouseEvent mouseEvent) {

          }

          @Override
          public void mouseEntered(MouseEvent mouseEvent) {

          }

          @Override
          public void mouseExited(MouseEvent mouseEvent) {

          }
    };
    this.addMouseListener(mouseListener);
}

}

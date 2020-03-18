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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel
{

  public final int ROWS = 8;
  public final int COLUMNS = 8;
  public final int WIDTH = 512;
  public final int HEIGHT = 512;
  public final int CELL_SIZE = WIDTH/8;

  public boolean clicked = false;
  public Piece clickedPiece = null;

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
        g.fillRect((col)*CELL_SIZE, (row)*CELL_SIZE, CELL_SIZE, CELL_SIZE);
      }
    }
    for (Piece p : pieces) {
      String filePath = "Pictures/" + p.toString() + ".png";
      int x = (p.position.x) * CELL_SIZE;
      int y = (7 - p.position.y) * CELL_SIZE;
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
      pieces.add(new Pawn(new Point(col, 1), true));
      pieces.add(new Pawn(new Point(col, 6), false));
    }

    // rooks
    pieces.add(new Rook(new Point(0, 0), true));
    pieces.add(new Rook(new Point(7, 0), true));
    pieces.add(new Rook(new Point(0, 7), false));
    pieces.add(new Rook(new Point(7, 7), false));

    // knights
    pieces.add(new Knight(new Point(1, 0), true));
    pieces.add(new Knight(new Point(6, 0), true));
    pieces.add(new Knight(new Point(1, 7), false));
    pieces.add(new Knight(new Point(6, 7), false));

    // bishops
    pieces.add(new Bishop(new Point(2, 0), true));
    pieces.add(new Bishop(new Point(5, 0), true));
    pieces.add(new Bishop(new Point(2, 7), false));
    pieces.add(new Bishop(new Point(5, 7), false));

    // queens
    pieces.add(new Queen(new Point(3, 0), true));
    pieces.add(new Queen(new Point(3, 7), false));

    // kings
    pieces.add(new King(new Point(4, 0), true));
    pieces.add(new King(new Point(4, 7), false));
  }

  public void createMouseListener() {
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }
        @Override
          public void mousePressed(MouseEvent e) {
              int c = e.getX() / CELL_SIZE;
              int r = 7 - e.getY() / CELL_SIZE;
              Piece p = findPiece(new Point(c,r));
              if (!clicked && p != null) {
                p.clicked = !p.clicked;
                clickedPiece = p;
                repaint();
                clicked = !clicked;
              } else if (clicked && p != null){
                if (p.clicked) { //reset clicked piece
                  p.clicked = !p.clicked;
                  clickedPiece = null;
                  clicked = !clicked;
                  repaint();
                } else if (clickedPiece.white != p.white){
                  if (isLegalCapture(clickedPiece, new Point(c,r)))  {
                    System.out.println("here");
                  }
                }
              } else if (clicked && p == null) {
                if (isLegal(clickedPiece, new Point(c,r)))  {
                  clickedPiece.position.setLocation(new Point(c,r));
                  clickedPiece.clicked = false;
                  clicked = false;
                  clickedPiece = null;
                  repaint();
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

  public Piece findPiece(Point toFind) {
    for (Piece p : pieces) {
      if (p.position.equals(toFind)) return p;
    }
    return null;
  }

  public boolean isLegal(Piece piece, Point destPos) {
    if (!piece.isLegal(destPos)) return false;

    ArrayList<Point> jumpedSquares = piece.jumpedSquares(destPos);
    for (Point square : jumpedSquares) {
      for (Piece p : pieces) {
        if (p.position.equals(square)) return false;
      }
    }
    return true;
  }

  public boolean isLegalCapture(Piece piece, Point destPos) {
    if (!piece.captureMoveLegal(destPos)) return false;

    ArrayList<Point> jumpedSquares = piece.jumpedSquares(destPos);
    for (Point square : jumpedSquares) {
      for (Piece p : pieces) {
        if (p.position.equals(square)) return false;
      }
    }
    return true;
  }

}

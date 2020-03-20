/**
 * Board class has a visual represenation of the board.
 * @author Stefan W-G and co.
 */

 //TO DO Promotion when capture, checkmate, bishops and rooks capture move legal, castle en pessant
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

  public static final int ROWS = 8;
  public static final int COLUMNS = 8;
  public static final int WIDTH = 512;
  public static final int HEIGHT = 512;
  public static final int CELL_SIZE = WIDTH/8;

  public boolean clicked = false;
  public boolean whiteTurn = true;
  public Piece clickedPiece = null;
  public King whiteKing = new King(new Point(4, 0), true);
  public King blackKing = new King(new Point(4, 7), false);

  private ArrayList<Piece> pieces = new ArrayList<>();

  public Point originalPos = null;

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

  public void clearArrayList() {
    pieces.clear();
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
      } catch (Exception e) {}
    }
  }

  public void newGame() {
    whiteTurn = true;
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
    whiteKing = new King(new Point(4, 0), true);
    blackKing = new King(new Point(4, 7), false);
    pieces.add(whiteKing);
    pieces.add(blackKing);
  }

  public void createMouseListener() {
    MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
          clickAction(e);
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

  // put comments at some point
  private void clickAction(MouseEvent e) {
    int c = e.getX() / CELL_SIZE;
    int r = 7 - e.getY() / CELL_SIZE;
    Piece p = findPiece(new Point(c,r));
    if (originalPos == null && p != null && p.getWhite() == whiteTurn) {
      originalPos = new Point(c,r);
      p.setClicked(true);
      clickedPiece = p;
      repaint();
    } else if (originalPos != null && originalPos.x == c && originalPos.y == r) {
      p.setClicked(false);
      clickedPiece = null;
      originalPos = null;
      repaint();
    } else if (originalPos != null) {
      Point oldLocation = new Point(clickedPiece.position.x, clickedPiece.position.y);
      int result = clickedPiece.movePiece(pieces, originalPos, new Point(c,r));
      King king = whiteKing;
      if (!whiteTurn) king = blackKing;
      if (result == -1) {
        System.out.println(check(king));
        if (!check(king)) {
          originalPos = null;
          clickedPiece.setClicked(false);
          repaint();
          whiteTurn = !whiteTurn;
        } else {
          clickedPiece.position.setLocation(oldLocation);
        }
      } else if (result >= 0) {
        System.out.println(check(whiteKing));
        if (!check(king)) {
          clickedPiece.position.setLocation(new Point(c,r));
          pieces.remove(result);
          originalPos = null;
          clickedPiece.setClicked(false);
          repaint();
          whiteTurn = !whiteTurn;
        }
      }
    }
  }

  private boolean check(Piece king) {
    for (Piece p : pieces) {
      if (p.getWhite() != king.getWhite() && p.captureMoveLegal(king.position)) {
        return true;
      }
    }
    return false;
  }
}

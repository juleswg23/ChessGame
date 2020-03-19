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
  public boolean whiteTurn = true;
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
      } catch (Exception e) {}
    }
  }

  public void newGame() {
    // set pawns
    for (int col = 0; col < COLUMNS; col++) {
      // white pawns then black ones
      pieces.add(new Pawn(new Point(col, 6), true));
      //pieces.add(new Pawn(new Point(col, 6), false));
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

    if (!clicked && p != null && p.getWhite() == whiteTurn) {
      p.setClicked(!p.getClicked());
      resetHelper(p);

    } else if (clicked && p != null){
      if (p.getClicked()) { //reset clicked piece
        p.setClicked(!p.getClicked());
        resetHelper(null);

      } else if (clickedPiece.getWhite() != p.getWhite() && clickedPiece.getWhite() == whiteTurn){
        if (isLegalCapture(clickedPiece, new Point(c,r)))  {
          clickedPiece.position.setLocation(new Point(c,r));
          clickedPiece.setClicked(false);
          int location = -1; //Find index of p in pieces and remove it

          for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i) == p) location = i;
          }

          pieces.remove(location);
          whiteTurn = !whiteTurn;
          clickedPiece.setClicked(true);
          repaint();
          promotion(clickedPiece, new Point (c,r));
          resetHelper(null);
        }
      }
    } else if (clicked && p == null) {
      if (isLegal(clickedPiece, new Point(c,r)))  {
        clickedPiece.position.setLocation(new Point(c,r));
<<<<<<< HEAD
        repaint();
        promotion(clickedPiece, new Point (c,r));
        clickedPiece.setClicked(false);
=======
        clickedPiece.setClicked(false);
        promotion(clickedPiece, new Point (c,r));
>>>>>>> 9117078ac093e772051c0f85e3b572fa42ba2c0f
        resetHelper(null);
        whiteTurn = !whiteTurn;
      }
    }
  }

  private void resetHelper(Piece p) {
    clickedPiece = p;
    clicked = !clicked;
    repaint();
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

  private void promotion(Piece p, Point destPos) {
    Piece piece = null;
    if (p.getClass().getName().equals("Pawn")) {
      if (p.position.y == 7 || p.position.y == 0) {

        String[] filePaths = new String[4];
        String[] type = {"B", "N", "R", "Q"};
        String c = "W";
        if (p.position.y == 0) c = "B";

        for (int i = 0; i < filePaths.length; i++) {
          filePaths[i] = "Pictures/" + c + type[i] + ".png";
        }

        ImageIcon[] imageOptions = new ImageIcon[4];
        for (int i = 0; i < filePaths.length; i++) {
          try {
            imageOptions[i] = new ImageIcon(filePaths[i]);
          } catch (Exception e) {}
        }

        boolean color = p.getWhite();
        int promoterWindow = JOptionPane.showOptionDialog(null, "Pick a piece for promotion",
                        "", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, imageOptions, imageOptions);
        switch(promoterWindow) {
          case 0:
            piece = new Bishop(destPos, color);
            break;
          case 1:
            piece = new Knight(destPos, color);
            break;
          case 2:
            piece = new Rook(destPos, color);
            break;
          case 3:
            piece = new Queen(destPos, color);
            break;
          default:
            piece = new Queen(destPos, color);
            break;
        }

        int location = -1; //Find index of p in pieces and remove it

        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i) == p) location = i;
        }
        pieces.remove(location);
        pieces.add(piece);
      }
    }
  }

}

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
import java.io.Serializable;

public class Board extends JPanel implements Serializable
{

  public static final int ROWS = 8;
  public static final int COLUMNS = 8;
  public static final int WIDTH = 512;
  public static final int HEIGHT = 512;
  public static final int CELL_SIZE = WIDTH/8;

  private boolean clicked = false;
  private boolean boardClickable = true; //if multiplayer this should be used
  private boolean whiteTurn = true;
  private Piece clickedPiece = null;
  private King whiteKing = new King(new Point(4, 0), true);
  private King blackKing = new King(new Point(4, 7), false);

  private boolean multiplayer = false;
  transient public MultiplayerClient mc;

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
      String filePath = "Pictures/" + p.toString().substring(0,3) + ".png";
      int x = (p.position.x) * CELL_SIZE;
      int y = (7 - p.position.y) * CELL_SIZE;
      try {
        BufferedImage img = ImageIO.read(new File(filePath));
        Image newImage = img.getScaledInstance(CELL_SIZE, CELL_SIZE, Image.SCALE_SMOOTH);
        g.drawImage(newImage, x, y, null);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void newGame() {
    whiteTurn = true;
    clicked = false;
    clickedPiece = null;
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

    if (!clicked && p != null && p.getWhite() == whiteTurn) {
      p.setClicked(true);
      resetHelper(p);
    } else if (clicked) {
      if (makeMove(clickedPiece, new Point(c,r))) {
        whiteTurn = !whiteTurn;
        resetHelper(null);

        if (multiplayer) {
          mc.sendToServer(this);
        }
      } else if (clickedPiece == p) {
        resetHelper(clickedPiece);
      }
    }
  }

  // returns true if move was made
  public boolean makeMove(Piece pieceToMove, Point destPos) {
    Piece whereToMove = findPiece(destPos);

    if (whereToMove != null) {
      if (whereToMove.equals(pieceToMove)) { //reset clicked piece
        pieceToMove.setClicked(false);
        return false;
      } else if (pieceToMove.getWhite() != whereToMove.getWhite() &&
                isLegalCapture(pieceToMove, destPos) && movePiece(pieceToMove, destPos)) {
        //capture so remove captured piece
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i) == whereToMove) pieces.remove(i);
        }
        repaint();
        promotion(pieceToMove, destPos);
        pieceToMove.setClicked(false);
        return true;
      }
    } else if (isLegal(pieceToMove, destPos) && movePiece(pieceToMove, destPos)) {
      repaint();
      promotion(pieceToMove, destPos);
      pieceToMove.setClicked(false);
      return true;
    }
    return false;
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
          filePaths[i] = "Pictures/" + c + type[i] + "U.png";
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

  public void clearArrayList() {
    pieces.clear();
  }

  private boolean check(Piece king) {
    for (Piece p : pieces) {
      if (p.getWhite() != king.getWhite() &&isLegalCapture(p, king.position)) {
        return true;
      }
    }
    return false;
  }

  public boolean movePiece(Piece pieceToMove, Point destPos) {
    Piece whereToMove = findPiece(destPos);
    boolean notCheck = !isCheck(pieceToMove, destPos);
    // if move doesn't lead to check, make move.
    if (notCheck) {
      pieceToMove.position.setLocation(destPos);
      if (whereToMove != null) {
        for (int i = 0; i < pieces.size(); i++) {
          if (pieces.get(i) == whereToMove) pieces.remove(i);
        }
      }
    }
    return notCheck;
  }

  private boolean isCheck(Piece pieceToMove, Point destPos) {
    boolean isCheck = false;
    Piece whereToMove = findPiece(destPos);
    Point oldLocation = new Point(pieceToMove.position.x, pieceToMove.position.y);
    //pretend to move the piece to check for checks.
    pieceToMove.position.setLocation(destPos);

    //pretend to remove if capture.
    for (int i = 0; i < pieces.size(); i++) {
      if (pieces.get(i) == whereToMove) pieces.remove(i);
    }
    if (whiteTurn && check(whiteKing)) {
      isCheck = true;
    } else if (!whiteTurn && check(blackKing)) {
      isCheck = true;
    }

    //reset stuff
    if (whereToMove != null) {
      pieces.add(whereToMove);
    }
    pieceToMove.position.setLocation(oldLocation);
    return isCheck;
  }

  //GETTERS and SETTERS
  public void setUpBoard(boolean wt, King whiteK, King blackK, ArrayList<Piece> p) {
    setWhiteTurn(wt);
    setWhiteKing(whiteK);
    setBlackKing(blackK);
    setPieces(p);
    System.out.println("input: " + p);
    System.out.println("output: " + pieces);
    setClicked(false);
    setClickedPiece(null);
    repaint();
    System.out.println("updated board");
  }

  public boolean getClicked() {
    return clicked;
  }

  public void setClicked(boolean c) {
    clicked = c;
  }

  public boolean getWhiteTurn () {
    return whiteTurn;
  }

  public void setWhiteTurn(boolean t) {
    whiteTurn = t;
  }

  public Piece getClickedPiece() {
    return clickedPiece;
  }

  public void setClickedPiece(Piece p) {
    clickedPiece = p;
  }

  public King getWhiteKing() {
    return whiteKing;
  }

  public void setWhiteKing(King k) {
    whiteKing = k;
  }

  public King getBlackKing() {
    return blackKing;
  }

  public void setBlackKing(King k) {
    blackKing = k;
  }

  public ArrayList<Piece> getPieces() {
    return pieces;
  }

  public void setPieces(ArrayList<Piece> p) {
    pieces = p;
  }

  public boolean getMultiplayer() {
    return multiplayer;
  }

  public void setMultiplayer(boolean m) {
    multiplayer = m;
  }


  // Just for checks for Serializable stuff
  public String toString() {
    return pieces.toString();
  }

  // Just for checks for Serializable stuff
  public boolean equals(Board b) {
    boolean same = this.toString().equals(b.toString()) &&
            this.getClicked() == b.getClicked() &&
            this.getWhiteKing().equals(b.getWhiteKing()) &&
            this.getBlackKing().equals(b.getBlackKing()) &&
            this.getWhiteTurn() == b.getWhiteTurn();

    if (this.getClickedPiece() != null && b.getClickedPiece() != null) {
      same = same && this.getClickedPiece().equals(b.getClickedPiece());
    } else {
      same = same && this.getClickedPiece() == null && b.getClickedPiece() == null;
    }
    return same;
  }

}

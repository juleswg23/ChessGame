/**
 * Board class has a visual represenation of the board.
 * @author Stefan W-G and co.
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel
{

  public final int ROWS = 8;
  public final int COLUMNS = 8;
  public final int WIDTH = 512;
  public final int HEIGHT = 512;
  public final int CELL_SIZE = WIDTH/8;

  public int firstClickRow;
  public int firstClickColumn;
  public int secondClickRow;
  public int secondClickColumn;
  public boolean clicked = false;

  private Square[][] boardState = new Square[ROWS][COLUMNS];


  public Board() {
    super();
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    newEmptyBoard();
    newGame();
    // createMouseListener();
  }

  @Override
  public void paintComponent(Graphics g) {
      drawBoard(g, boardState);
  }

  public void drawBoard(Graphics g, Square[][] boardState) {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {

        if ((row + col) % 2 == 0) {
          g.setColor(new Color(189,183,107));
        } else {
          g.setColor(new Color(139,69,19));
        }
        g.fillRect((col)*CELL_SIZE, (7 - row)*CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // if a piece exists, print it
        if (boardState[col][row].getPiece() != null) {
          Piece p = boardState[col][row].getPiece();
          if (p.getWhite()) g.setColor(Color.white);
          else g.setColor(Color.black);
          g.drawString(p.toString(), col*CELL_SIZE+CELL_SIZE/2, (7 - row)*CELL_SIZE+CELL_SIZE/2);
        }
      }
    }
  }

  public void newEmptyBoard() {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        boardState[col][row] = new Square(col, row);
      }
    }
  }
 
  public void newGame() {
    // set pawns
    for (int col = 0; col < COLUMNS; col++) {
      // white pawns then black ones
      boardState[col][1].setPiece(new Pawn(col, 1, true));
      boardState[col][6].setPiece(new Pawn(col, 6, false));
    }

    // rooks
    boardState[0][0].setPiece(new Rook(0, 0, true));
    boardState[7][0].setPiece(new Rook(7, 0, true));
    boardState[0][7].setPiece(new Rook(0, 7, false));
    boardState[7][7].setPiece(new Rook(7, 7, false));

    // knights
    boardState[1][0].setPiece(new Knight(1, 0, true));
    boardState[6][0].setPiece(new Knight(6, 0, true));
    boardState[1][7].setPiece(new Knight(1, 7, false));
    boardState[6][7].setPiece(new Knight(6, 7, false));

    // bishops
    boardState[2][0].setPiece(new Bishop(2, 0, true));
    boardState[5][0].setPiece(new Bishop(5, 0, true));
    boardState[2][7].setPiece(new Bishop(2, 7, false));
    boardState[5][7].setPiece(new Bishop(5, 7, false));

    // queens
    boardState[3][0].setPiece(new Queen(3, 0, true));
    boardState[3][7].setPiece(new Queen(3, 7, false));

    // kings
    boardState[4][0].setPiece(new King(4, 0, true));
    boardState[4][7].setPiece(new King(4, 7, false));
  }

  // public void createMouseListener() {
  //     MouseListener mouseListener = new MouseListener() {
  //         @Override
  //         public void mouseClicked(MouseEvent mouseEvent) {
  //
  //         }
  //         @Override
  //           public void mousePressed(MouseEvent e) {
  //               int c = e.getX() % CELL_SIZE;
  //               int r = e.getY() % CELL_SIZE;
  //               System.out.println(c + "," + r);
  //               if (!clicked && boardState[c][r].getPiece() != null) {
  //                 firstClickRow = r;
  //                 firstClickColumn = c;
  //               } else if (clicked && boardState[c][r].getPiece() != null) {
  //
  //               } else if (clicked && boardState[c][r].getPiece() == null) {
  //
  //               }
  //           }
  //
  //           @Override
  //           public void mouseReleased(MouseEvent mouseEvent) {
  //
  //           }
  //
  //           @Override
  //           public void mouseEntered(MouseEvent mouseEvent) {
  //
  //           }
  //
  //           @Override
  //           public void mouseExited(MouseEvent mouseEvent) {
  //
  //           }
  //     };
  //     this.addMouseListener(mouseListener);
  // }

  // public boolean isLegal(int origCol, int origRow, int destCol, int destRow) {
  //   if (boardState[origCol][origRow].getPiece() == null) {
  //     return false;
  //   }
  //   boolean legalByPiece = boardState[origCol][origRow].getPiece().isLegal(destCol, destRow);
  //   boolean legalByBoard = false;
  //
  //   switch (boardState[origCol][origRow].getPiece().toString()) {
  //     case "K":
  //       //do something
  //       break;
  //     case "Q":
  //       //
  //       break;
  //     case "B":
  //       //do something
  //       break;
  //     case "N":
  //       // do something
  //       break;
  //     case "R":
  //       //do something
  //       break;
  //     case "P":
  //       //do something
  //       break;
  //   }
  //
  //   return legalByPiece && legalByBoard;
  // }

}

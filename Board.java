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

  private Square[][] boardState;


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
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS ; col++) {
        if ((row + col) % 2 == 0) {
          g.setColor(Color.white);
        } else {
          g.setColor(new Color(128,128,128));
        }
        g.fillRect(row*CELL_SIZE, col*CELL_SIZE, CELL_SIZE, CELL_SIZE);
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
    boardState[4][0].setPiece(new Queen(4, 0, true));
    boardState[4][7].setPiece(new Queen(4, 7, false));
  }
}

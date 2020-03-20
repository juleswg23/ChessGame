import java.awt.event.*;
import javax.swing.JFrame;

public class NewGameAction implements ActionListener {
  private Board board;
  public NewGameAction(Board b) {
    board = b;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    board.clearArrayList();
    board.newGame();
    board.repaint();
  }
}

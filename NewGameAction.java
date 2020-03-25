import java.awt.event.*;
import javax.swing.JFrame;

public class NewGameAction implements ActionListener {
  private Board b;
  public NewGameAction(Board b) {
    this.b = b;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println(b.getMoves());
    b.resetMoves();
    b.clearArrayList();
    b.newGame();
    b.repaint();
  }
}

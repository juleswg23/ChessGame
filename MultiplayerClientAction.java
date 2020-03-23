import java.awt.event.*;
import javax.swing.JFrame;

public class MultiplayerClientAction implements ActionListener {
  MultiplayerClient mc;
  Board b;

  public MultiplayerClientAction(Board b) {
    this.b = b;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      b.setMultiplayer(true);
      b.mc = new MultiplayerClient(b);
    } catch (Exception ex) {
      System.out.println("Ran Test");
      TestSerial ts = new TestSerial();
    }
  }
}

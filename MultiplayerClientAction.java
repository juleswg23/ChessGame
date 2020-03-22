import java.awt.event.*;
import javax.swing.JFrame;

public class MultiplayerClientAction implements ActionListener {
  MultiplayerClient mc;
  public MultiplayerClientAction() {
  }
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Here");
    //mc = new MultiplayerClient();
    TestSerial ts = new TestSerial();
  }
}

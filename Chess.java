/**
 * Chess game.
 * @author Stefan W-G and co.
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Chess
{

  public static final int WIDTH = 512;
  public static final int HEIGHT = 512;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Chess");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setContentPane(new Board());
    frame.pack();
    frame.setVisible(true);
  }
}

/**
 * Chess game.
 * @author Stefan W-G and co.
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Chess {

  public final int WIDTH = 1024;
  public final int HEIGHT = 1024;

  public static void main(String[] args) {
    JFrame frame = new JFrame("Chess");
    frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    Board board = new Board();
  }
}

/**
 * Chess game.
 * @author Stefan W-G and co.
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Chess
{

  public static final int WIDTH = 512;
  public static final int HEIGHT = 512;

  public static void main(String[] args) {


    JFrame frame = new JFrame("Chess");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    Board b = new Board();
    frame.setContentPane(b);
    frame.pack();

    JMenuBar mb = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem newGameMenu = new JMenuItem("New Game");
    newGameMenu.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.META_MASK));
    newGameMenu.addActionListener(new NewGameAction(b));
    menu.add(newGameMenu);

    mb.add(menu);
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    frame.setJMenuBar(mb);

    frame.setVisible(true);
  }
}

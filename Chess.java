/**
 * Chess game.
 * @author Stefan W-G and co.
 */

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//run in command line to create jar file:
//jar cmf Chess.mf Chess.jar *.class pictures

//run in command line to run jar file
//java -jar Chess.jar

public class Chess
{

  public static final int WIDTH = 512;
  public static final int HEIGHT = 512;

  public static void main(String[] args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");

    JFrame frame = new JFrame("Chess");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    Board b = new Board();
    frame.setContentPane(b);
    frame.pack();

    JMenuBar mb = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem newGame = new JMenuItem("New Game");
    newGame.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_N, ActionEvent.META_MASK));
    newGame.addActionListener(new NewGameAction(b));
    menu.add(newGame);

    JMenuItem multiplayerGame = new JMenuItem("Multiplayer Game");
    multiplayerGame.setAccelerator(KeyStroke.getKeyStroke(
        KeyEvent.VK_M, ActionEvent.META_MASK));
    multiplayerGame.addActionListener(new MultiplayerClientAction(b));
    menu.add(multiplayerGame);

    mb.add(menu);

    frame.setJMenuBar(mb);

    WindowListener windowListener = new WindowAdapter(){
      @Override
      public void windowClosing(WindowEvent w) {
        if (b.getMultiplayer()) {
          b.setCloseConnection(true);
          b.mc.sendToServer(b);
        }
      }
    };

    frame.addWindowListener(windowListener);

    frame.setVisible(true);
  }
}

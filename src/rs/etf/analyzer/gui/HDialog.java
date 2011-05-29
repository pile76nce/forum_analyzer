package rs.etf.analyzer.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;

/**
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class HDialog extends JDialog implements KeyListener, WindowListener
{
  public static final String OK_OPTION = "OK";
  public static final String CANCEL_OPTION = "CANCEL";

  protected MainFrame ioMainFrame;

  protected String isStatus;

  public HDialog()
  {
    super();

    setResizable(false);
  }

  public HDialog(JFrame aoMainFrame, String asTitle)
  {
    super(aoMainFrame, asTitle, true);

    setResizable(false);
  }

  protected void showDialog()
  {
    Dimension ioDlgSize = getPreferredSize();

    Dimension ioFrmSize = Toolkit.getDefaultToolkit().getScreenSize();
    Point loc = new Point(0, 0);

    setLocation( (ioFrmSize.width - ioDlgSize.width) / 2 + loc.x,
                 (ioFrmSize.height - ioDlgSize.height) / 2 + loc.y);

    setVisible(true);
  }

  public String getCloseStatus()
  {
    return isStatus;
  }

  public void keyPressed(KeyEvent e)
  {
  }

  public void keyTyped(KeyEvent e)
  {
  }

  public void keyReleased(KeyEvent e)
  {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
    {
      dispose();
    }
  }

  public void windowClosing(WindowEvent e)
  {
    dispose();
  }

  public void windowClosed(WindowEvent e)
  {
  }

  public void windowActivated(WindowEvent e)
  {
  }

  public void windowDeactivated(WindowEvent e)
  {
  }

  public void windowIconified(WindowEvent e)
  {
  }

  public void windowDeiconified(WindowEvent e)
  {
  }

  public void windowOpened(WindowEvent e)
  {
  }
}

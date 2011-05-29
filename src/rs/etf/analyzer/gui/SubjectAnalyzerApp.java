package rs.etf.analyzer.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Glavna aplikacija</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class SubjectAnalyzerApp
{
  private boolean packFrame = false;

  private MainFrame frame;

  public SubjectAnalyzerApp()
  {
    frame = new MainFrame();

    if (packFrame)
    {
      frame.pack();
    }
    else
    {
      frame.validate();
    }

    // Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
    {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width)
    {
      frameSize.width = screenSize.width;
    }
    frame.setLocation( (screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);

    frame.setVisible(true);

    try
    {
      frame.openProject();
    }
    catch (Exception ex)
    {
    }
  }

  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
          ex.printStackTrace();
        }

        new SubjectAnalyzerApp();
      }
    });
  }
}

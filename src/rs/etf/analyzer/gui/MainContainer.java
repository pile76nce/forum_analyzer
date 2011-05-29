package rs.etf.analyzer.gui;

import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class MainContainer extends JPanel
{
  private JTabbedPane ioTabbedPane;

  private MainFrame ioParentFrame;

  private int iiComponentIndex = 0;

  protected BorderLayout borderLayout1 = new BorderLayout();

  public MainContainer(MainFrame aoParentFrame)
  {
    super();

    ioParentFrame = aoParentFrame;

    try
    {
      jbInit();
    }
    catch (Exception ex)
    {
    }
  }

  private void jbInit() throws Exception
  {
    ioTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
    setLayout(borderLayout1);
    add(ioTabbedPane, java.awt.BorderLayout.CENTER);
  }

  public void add(MainPanel aoMainPanel, String asTitle)
  {
    iiComponentIndex = ioTabbedPane.getTabCount();

    ioTabbedPane.add(asTitle, aoMainPanel);
    ioTabbedPane.setTabComponentAt(iiComponentIndex, new ButtonTabComponent(ioTabbedPane));
  }

  public MainPanel getSelectedPanel()
  {
    return (MainPanel) ioTabbedPane.getSelectedComponent();
  }
}

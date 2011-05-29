package rs.etf.analyzer.gui;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class ProjectPanel extends JPanel
{
  private JTabbedPane ioTabbedPane;
  private BorderLayout borderLayout1 = new BorderLayout();

  public ProjectPanel()
  {
    try
    {
      jbInit();
    }
    catch (Exception e)
    {
    }
  }

  private void jbInit() throws Exception
  {
    ioTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.WRAP_TAB_LAYOUT);
    this.setLayout(borderLayout1);
    this.add(ioTabbedPane, java.awt.BorderLayout.CENTER);
  }

  public void addPanel(JPanel aoPanel, String asTitle)
  {
    ioTabbedPane.addTab(asTitle, aoPanel);
  }

  public int getSelectedIndex()
  {
    return ioTabbedPane.getSelectedIndex();
  }
}

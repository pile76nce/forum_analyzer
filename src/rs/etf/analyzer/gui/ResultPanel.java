package rs.etf.analyzer.gui;

import javax.swing.JScrollPane;
import java.util.List;
import rs.etf.analyzer.parser.ParseResult;
import rs.etf.analyzer.parser.Category;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Panel za prikaz rezultata</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class ResultPanel extends JPanel
{
  private JTabbedPane ioTabbedPane;

  protected MainFrame ioParentFrame;

  protected BorderLayout borderLayout1 = new BorderLayout();

  public ResultPanel(MainFrame aoParentFrame)
  {
    super();

    ioParentFrame = aoParentFrame;

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
//      ioTabbedPane.add(asTitle, aoPanel);
//      ioTabbedPane.setTabComponentAt(0, new ButtonTabComponent(ioTabbedPane));
  }

  public int getSelectedIndex()
  {
    return ioTabbedPane.getSelectedIndex();
  }

  public void clear()
  {
  }
}

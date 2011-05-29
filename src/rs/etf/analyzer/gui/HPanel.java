package rs.etf.analyzer.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
public class HPanel extends JPanel
{
  protected JScrollPane jScrollPane;
  protected BorderLayout borderLayout1 = new BorderLayout();

  public HPanel()
  {
    super();

    try
    {
      jbInit();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    setLayout(borderLayout1);
  }
}

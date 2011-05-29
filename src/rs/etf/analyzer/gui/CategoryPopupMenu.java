package rs.etf.analyzer.gui;

import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

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
public class CategoryPopupMenu extends JPopupMenu implements ActionListener
{
  private JMenuItem jMINew = new JMenuItem();
  private JMenuItem jMIEdit = new JMenuItem();
  private JMenuItem jMIDelete = new JMenuItem();

  private MainFrame ioParentFrame;

  public CategoryPopupMenu(MainFrame aoParentFrame)
  {
    super();

    ioParentFrame = aoParentFrame;

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
    jMINew.setText("New Category");
    jMINew.addActionListener(this);
    jMIEdit.setText("Edit Category");
    jMIEdit.addActionListener(this);
    jMIDelete.setText("Delete Category");
    jMIDelete.addActionListener(this);

    add(jMINew);
    add(jMIEdit);
    add(jMIDelete);
  }

  public void actionPerformed(ActionEvent e)
  {
    final String lsActionCommand = e.getActionCommand();
  }
}

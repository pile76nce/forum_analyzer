package rs.etf.analyzer.gui;

import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

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
public class ConceptPopupMenu extends JPopupMenu implements ActionListener
{
  private JMenuItem jMIAdd = new JMenuItem();
  private JMenuItem jMIEdit = new JMenuItem();
  private JMenuItem jMIDelete = new JMenuItem();

  private MainFrame ioParentFrame;

  public ConceptPopupMenu(MainFrame aoParentFrame)
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
    jMIAdd.setText("Add Concept");
    jMIAdd.addActionListener(this);
    jMIAdd.setIcon(SwingUtil.addConcept);
    jMIEdit.setText("Edit Concept");
    jMIEdit.addActionListener(this);
    jMIEdit.setIcon(SwingUtil.editConcept);
    jMIDelete.setText("Delete Concept");
    jMIDelete.addActionListener(this);
    jMIDelete.setIcon(SwingUtil.deleteConcept);

    add(jMIAdd);
    add(jMIEdit);
    add(jMIDelete);
  }

  public void actionPerformed(ActionEvent e)
  {
    String lsActionCommand = e.getActionCommand();

    if (lsActionCommand.equalsIgnoreCase("EDIT CONCEPT") == true)
      ioParentFrame.editConcept();
    else
    if (lsActionCommand.equalsIgnoreCase("NEW CONCEPT") == true)
      ioParentFrame.editConcept();
  }
}

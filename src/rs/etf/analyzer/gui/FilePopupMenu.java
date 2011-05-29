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
public class FilePopupMenu extends JPopupMenu implements ActionListener
{
  private JMenuItem jMIAddFile;
  private JMenuItem jMIRemoveFile;
  private JMenuItem jMIAnalyzeFile;
  private JMenuItem jMIProcessFile;

  private MainFrame ioParentFrame;

  public FilePopupMenu(MainFrame aoParentFrame)
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
    jMIAddFile = new JMenuItem("Add File(s)");
    jMIAddFile.setActionCommand("ADD");
    jMIAddFile.setIcon(SwingUtil.addFile);
    jMIAddFile.addActionListener(this);
    jMIRemoveFile = new JMenuItem("Remove File(s)");
    jMIRemoveFile.setActionCommand("REMOVE");
    jMIRemoveFile.addActionListener(this);
    jMIRemoveFile.setIcon(SwingUtil.removeFile);
    jMIAnalyzeFile = new JMenuItem("Analyze");
    jMIAnalyzeFile.setIcon(SwingUtil.analyzing);
    jMIAnalyzeFile.addActionListener(this);

    jMIProcessFile = new JMenuItem("Process...");
    jMIProcessFile.addActionListener(this);
    jMIProcessFile.setActionCommand("PROCESS");
    jMIProcessFile.setIcon(SwingUtil.processing);

//    jMenuItem1.setText("...all words");
//    jMenuItem1.setActionCommand("all words");
//    jMenuItem1.addActionListener(this);
//    jMenuItem2.setText("...defined words");
//    jMenuItem2.setActionCommand("defined words");
//    jMenuItem2.addActionListener(this);

    add(jMIAddFile);
    add(jMIRemoveFile);
    add(jMIAnalyzeFile);
    add(jMIProcessFile);
//    jMenu1.add(jMenuItem2);
//    jMenu1.add(jMenuItem1);
  }

  public void actionPerformed(ActionEvent e)
  {
    final String lsActionCommand = e.getActionCommand();

    if (lsActionCommand.equalsIgnoreCase("ADD") == true)
    {
      ioParentFrame.addCategoryFile();
    }
    else
    if (lsActionCommand.equalsIgnoreCase("REMOVE") == true)
    {
      ioParentFrame.removeFiles();
    }
    else
    if (lsActionCommand.equalsIgnoreCase("ANALYZE") == true)
    {
      try
      {
        ioParentFrame.analyze();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    else
    if (lsActionCommand.equalsIgnoreCase("PROCESS") == true)
    {
      try
      {
        ioParentFrame.countWords();
      }
      catch (Exception ex)
      {
      }
    }
    else
    if (lsActionCommand.equalsIgnoreCase("PROCESS") == true)
    {
    }
  }
}

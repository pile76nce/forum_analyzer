package rs.etf.analyzer.gui;

import org.jdesktop.swingx.JXTreeTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

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
public class AnalyzerTreeTable extends JXTreeTable
{
  private AnalyzerTreeTableModel ioAnalyzerModel;

  private ConceptPopupMenu ioConceptPopupMenu;
  private CategoryPopupMenu ioCategoryPopupMenu;

  private MainFrame ioParentFrame;

  public AnalyzerTreeTable(MainFrame aoParentFrame)
  {
    super();

    ioParentFrame = aoParentFrame;

    setAutoResizeMode(JXTreeTable.AUTO_RESIZE_OFF);

    ioAnalyzerModel = new AnalyzerTreeTableModel(new ItemNode());

    addMouseListener(new PopupListener());

    addTreeSelectionListener(new ConceptSelectionListener());

    setTreeTableModel(ioAnalyzerModel);

    getColumnModel().getColumn(0).setPreferredWidth(600);
    getColumnModel().getColumn(1).setPreferredWidth(100);
    getColumnModel().getColumn(2).setPreferredWidth(100);

    ioConceptPopupMenu = new ConceptPopupMenu(ioParentFrame);
    ioCategoryPopupMenu = new CategoryPopupMenu(ioParentFrame);
  }

  public AnalyzerTreeTableModel getAnalyzerModel()
  {
    return ioAnalyzerModel;
  }

  public void clear()
  {
    removeAll();
  }

  public class PopupListener extends MouseAdapter
  {
    /*public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
    }*/

    public void mouseReleased(MouseEvent e)
    {
      if (e.isPopupTrigger() == true)
        popupEvent(e);
    }

    private void popupEvent(MouseEvent e)
    {
      int x = e.getX();
      int y = e.getY();

      JXTreeTable tt = (JXTreeTable)e.getSource();

      TreePath tp = tt.getPathForLocation(x, y);
      if (tp == null)
        return;

      ItemNode node = (ItemNode)tp.getLastPathComponent();

      if (node.isNodeLeaf() == true)
        ioConceptPopupMenu.show(tt, x, y);
      else
        ioCategoryPopupMenu.show(tt, x, y);
    }
  }

  public class ConceptSelectionListener implements TreeSelectionListener
  {
    public void valueChanged(TreeSelectionEvent evt)
    {
      TreePath tp = evt.getPath();
      ItemNode node = (ItemNode)tp.getLastPathComponent();

//      LogPanel.getInstance().setText(node.getText(), LogMessage.INFO);

      ioParentFrame.select(node.getText());
    }
  }
}

package rs.etf.analyzer.gui;

import org.jdesktop.swingx.JXTreeTable;
import javax.swing.JTree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.tree.TreePath;

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
public class CategoryTree extends JTree
{
  private CategoryTreeModel ioCategoryModel;

  private FilePopupMenu ioFilePopupMenu;

  private MainFrame ioParentFrame;

  private CategoryNode ioSelectedNode;

  public CategoryTree(MainFrame aoParentFrame)
  {
    super();

    ioParentFrame = aoParentFrame;

    ioFilePopupMenu = new FilePopupMenu(ioParentFrame);

    CategoryNode node = new CategoryNode("CE-CAOR");

    ioCategoryModel = new CategoryTreeModel(node);

    addMouseListener(new PopupListener());

    setModel(ioCategoryModel);
  }

  public CategoryTreeModel getCategoryModel()
  {
    return ioCategoryModel;
  }

  public void clear()
  {
    removeAll();
  }

  public CategoryNode getSelectedNode()
  {
    return ioSelectedNode;
  }

  public class PopupListener extends MouseAdapter
  {
    public void mouseReleased(MouseEvent e)
    {
      if (e.isPopupTrigger() == true)
        popupEvent(e);
    }

    private void popupEvent(MouseEvent e)
    {
      int x = e.getX();
      int y = e.getY();

      JTree tt = (JTree)e.getSource();

      TreePath tp = tt.getPathForLocation(x, y);
      if (tp == null)
        return;

      ioSelectedNode = (CategoryNode)tp.getLastPathComponent();

      ioFilePopupMenu.show(tt, x, y);
    }
  }
}

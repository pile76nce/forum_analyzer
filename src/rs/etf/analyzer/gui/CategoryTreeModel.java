package rs.etf.analyzer.gui;

import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;
import rs.etf.analyzer.parser.ParseResult;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.Enumeration;
import javax.swing.SwingUtilities;

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
public class CategoryTreeModel extends DefaultTreeModel
{
  public CategoryTreeModel(CategoryNode aoNode)
  {
    super(aoNode);
  }

  public void addRecord(CategoryNode node)
  {
    CategoryNode root = (CategoryNode)getRoot();

    insertNodeInto(node, root, root.getChildCount());
    refresh(node);
  }

  public void addRecord(CategoryNode node, CategoryNode parent)
  {
    insertNodeInto(node, parent, parent.getChildCount());
    refresh(node);
  }

  public void deleteRecord(CategoryNode node)
  {
    removeNodeFromParent(node);
  }

  protected void refresh(final CategoryNode node) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        nodeChanged(node); // remind the tree to render the new node
      }
    });
  }


  private void addCategory()
  {
    CategoryNode loRoot = (CategoryNode)getRoot();
    CategoryNode loParentNode = loRoot;

    boolean lbNodeAlreadyExist = false;

    Enumeration e = loParentNode.children();

  }
}

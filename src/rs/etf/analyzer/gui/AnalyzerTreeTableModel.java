package rs.etf.analyzer.gui;

import org.jdesktop.swingx.treetable.TreeTableModel;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import rs.etf.analyzer.parser.ParseResult;

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
public class AnalyzerTreeTableModel extends DefaultTreeTableModel
{
  // Names of the columns.
  protected static String[] cNames = {"Concept", "Count", "Score", ""};

  // Types of the columns.
  protected static Class[] cTypes = {TreeTableModel.class, String.class, String.class, String.class};

  public AnalyzerTreeTableModel()
  {
    super();
  }

  public AnalyzerTreeTableModel(ItemNode node)
  {
    super(node);
  }

  public void clear()
  {
    ItemNode root = (ItemNode)getRoot();
    ItemNode parent = root;

    while (true)
    {
      int liChildCount = parent.getChildCount();

      if (liChildCount == 0) break;

      ItemNode loNode = (ItemNode) parent.getChildAt(0);
      removeNodeFromParent(loNode);
    }
  }

  public void addRecord(ItemNode node)
  {
    ItemNode loRoot = (ItemNode)getRoot();
    insertNodeInto(node, loRoot, loRoot.getChildCount());
  }

  public void addRecord(ItemNode parent, ItemNode node)
  {
    insertNodeInto(node, parent, parent.getChildCount());

    parent.setLeaf(false);
    node.setLeaf(true);
  }

  public void deleteRecord(ItemNode node)
  {
    removeNodeFromParent(node);
  }

  public ItemNode getItem()
  {
    ItemNode root = (ItemNode)getRoot();
    ItemNode parent = root;

    return null;
  }

  public boolean isCellEditable(Object aoNode, int aiColumnIndex)
  {
    return false;
  }

  public int getColumnCount()
  {
    return cNames.length;
  }

  public Class getColumnClass(int aiColumnIndex)
  {
    return cTypes[aiColumnIndex];
  }

  public String getColumnName(int aiColumnIndex)
  {
    return cNames[aiColumnIndex];
  }

  public Object getValueAt(Object aoValue, int aiColumnIndex)
  {
    ItemNode n = (ItemNode)aoValue;
    ParseResult pr = n.getItem();

    switch (aiColumnIndex)
    {
      case 0:
        return n.getText();
      case 1:
        return n.getCount();
    }

    return null;
  }
}

package rs.etf.analyzer.gui;

import org.jdesktop.swingx.treetable.DefaultMutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;
import rs.etf.analyzer.parser.ParseResult;
import rs.etf.analyzer.parser.Position;

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
public class ItemNode extends DefaultMutableTreeTableNode
{
  private String isItemText;
  private String isCount;
  private String isScore;

  private ParseResult ioItem;

  private boolean ibLeaf;

  public ItemNode()
  {
    super();
  }

  public ItemNode(final String asItemText)
  {
    isItemText = asItemText;

    setUserObject(isItemText);
  }

  public ItemNode(final String asItemText, final String asCount)
  {
    isItemText = asItemText;
    isCount = asCount;

    setUserObject(isItemText);
  }

  public ItemNode(ParseResult aoItem)
  {
    ioItem = aoItem;

    isItemText = ioItem.getText();
    isCount = String.valueOf(ioItem.getCount());

    setUserObject(ioItem);
  }

  public ItemNode(Position aoItem, int len)
  {
//    ioItem = aoItem;

    isItemText = aoItem.getToken();
    isCount = String.valueOf(len);

    setUserObject(aoItem);
  }


  public ParseResult getItem()
  {
    return ioItem;
  }

  public String getText()
  {
    return isItemText;
//    return ioItem.getText();
  }

  public String getCount()
  {
    return isCount;
  }

  public void setLeaf(final boolean abLeaf)
  {
    ibLeaf = abLeaf;
  }

  public boolean isNodeLeaf()
  {
    return ibLeaf;
  }

  public void addRecord()
  {
    addRecordToParent();
  }

  public void updateRecord()
  {
  }

  public void deleteRecord()
  {
    TreeTableNode loParent = getParent();

    if (loParent != null)
    {
      removeFromParent();
    }
  }

  protected void addRecordToParent()
  {
    TreeTableNode loParent = getParent();

    if (loParent == null)
      return;

    ((ItemNode)loParent).addRecordFromChild();
  }

  protected void addRecordFromChild()
  {
    addRecordToParent();
  }
}

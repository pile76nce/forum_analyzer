package rs.etf.analyzer.gui;

import javax.swing.tree.DefaultMutableTreeNode;

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
public class CategoryNode extends DefaultMutableTreeNode
{
  private String isText;

  private boolean ibSelected = true;

  public CategoryNode(String asText)
  {
    isText = asText;
    setUserObject(isText);
  }

  public String getText()
  {
    return isText;
  }

  public void setSelected(boolean abSelected)
  {
    ibSelected = abSelected;
  }

  public boolean isSelected()
  {
    return ibSelected;
  }
}

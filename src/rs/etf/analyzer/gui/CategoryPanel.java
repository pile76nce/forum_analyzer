package rs.etf.analyzer.gui;

import javax.swing.JScrollPane;
import rs.etf.analyzer.parser.Category;
import rs.etf.analyzer.parser.Chapter;
import java.util.List;
import java.io.File;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeNode;

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
public class CategoryPanel extends HPanel
{
  private CategoryTree ioCategoryTree;
  private CategoryTreeModel ioCategoryTreeModel;

  private MainFrame ioParentFrame;

  public CategoryPanel(MainFrame aoParentFrame)
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

    ioCategoryTreeModel = ioCategoryTree.getCategoryModel();
  }

  private void jbInit() throws Exception
  {
    ioCategoryTree = new CategoryTree(ioParentFrame);

    jScrollPane = new JScrollPane(ioCategoryTree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    add(jScrollPane, java.awt.BorderLayout.CENTER);
  }

  public void load()
  {
    CategoryNode node = new CategoryNode("pile");
    ioCategoryTreeModel.addRecord(node);

    node = new CategoryNode("cupa");
    ioCategoryTreeModel.addRecord(node);

    CategoryNode node1 = new CategoryNode("disha");
    ioCategoryTreeModel.addRecord(node1, node);
  }

  public void load(Chapter aoChapter)
  {
    Chapter loChapter = aoChapter;
    List<Category> loCategoryList = loChapter.getCategoryList();
    Category loCategory;

    CategoryNode loParentNode = new CategoryNode(loChapter.getChapterName());
    ioCategoryTreeModel.addRecord(loParentNode);

    for (int i = 0; i < loCategoryList.size(); i++)
    {
      loCategory = loCategoryList.get(i);

      CategoryNode loNewNode = new CategoryNode(loCategory.getCategory());
      ioCategoryTreeModel.addRecord(loNewNode, loParentNode);
    }
  }

  public void addFiles(File[] aoFiles)
  {
    CategoryNode loSelectedNode = ioCategoryTree.getSelectedNode();
    CategoryNode loNewNode;
    String lsFileName;

    for (int i = 0; i < aoFiles.length; i++)
    {
      lsFileName = aoFiles[i].getAbsolutePath();
      loNewNode = new CategoryNode(lsFileName);

      ioCategoryTreeModel.addRecord(loNewNode, loSelectedNode);
    }
  }

  public void removeFiles(CategoryNode[] aoCategoryNode)
  {
    for (int i = 0; i < aoCategoryNode.length; i++)
    {
      ioCategoryTreeModel.deleteRecord(aoCategoryNode[i]);
    }
  }

  public CategoryNode getSelectedNode()
  {
    return ioCategoryTree.getSelectedNode();
  }

  public void setExpanded(boolean abExpanded)
  {
    if (abExpanded)
    {
      TreePath tp = new TreePath((TreeNode)ioCategoryTreeModel.getRoot());
      ioCategoryTree.expandPath(tp);
    }
  }
}

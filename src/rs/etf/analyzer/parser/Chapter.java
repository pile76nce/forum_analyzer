package rs.etf.analyzer.parser;

import java.util.List;
import java.util.ArrayList;

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
public class Chapter
{
  private final String isChapterName;

  private List<Category> ioCategoryList = new ArrayList<Category>();

  public Chapter(final String asChapterName)
  {
    isChapterName = asChapterName;
  }

  public String getChapterName()
  {
    return isChapterName;
  }

  public List<Category> getCategoryList()
  {
    return ioCategoryList;
  }

  public void addCategory(Category aoCategory)
  {
    ioCategoryList.add(aoCategory);
  }
}

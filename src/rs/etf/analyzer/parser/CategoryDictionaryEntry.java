package rs.etf.analyzer.parser;

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
public class CategoryDictionaryEntry extends AbstractDictionaryEntry
{
  private String isCategory;
  private int iiCategoryId;

  public CategoryDictionaryEntry(final String asCategory, final int aiCategoryId)
  {
    isCategory = asCategory;
    iiCategoryId= aiCategoryId;
  }

  public String getCategory()
  {
    return isCategory;
  }

  public int getCategoryId()
  {
    return iiCategoryId;
  }

  public String getToken()
  {
    return null;
  }

  public ArrayList<Position> getPositionList()
  {
    return null;
  }
}

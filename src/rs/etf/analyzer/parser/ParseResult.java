package rs.etf.analyzer.parser;

import java.util.ArrayList;
import java.util.List;

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
public class ParseResult implements Comparable
{
  private String isCategory;
  private String isConcept;

  private int iiCount;

  private List<Position> ioPositionList = new ArrayList<Position>();

  public ParseResult(String asCategory, String asConcept)
  {
    isCategory = asCategory;
    isConcept = asConcept;

    iiCount = 0;
  }

  public void addPosition(final int aiStart, final int aiLen)
  {
    ioPositionList.add(new Position(aiStart, aiLen));
    iiCount++;
  }

  public List<Position> getPositionList()
  {
    return ioPositionList;
  }

  public String getCategory()
  {
    return isCategory;
  }

  public String getConcept()
  {
    return isConcept;
  }

  public int getCount()
  {
    return iiCount;
  }

  public void setCount(final int aiCount)
  {
    iiCount = aiCount;
  }

  public String getText()
  {
    if (isConcept.equals("") == false)
      return isConcept;
    else if (isCategory.equals("") == false)
      return isCategory;

    return "";
  }

  public int compareTo(Object o)
  {
    try
    {
      ParseResult p = (ParseResult) o;

      if (iiCount > p.getCount())
        return 1;
      else
        if (iiCount < p.getCount())
          return -1;

      return 0;
    }
    catch (Exception e)
    {
      return 0;
    }
  }

  public class Position
  {
    private int iiStart;
    private int iiLen;

    public Position(final int aiStart, final int aiLen)
    {
      iiStart = aiStart;
      iiLen = aiLen;
    }

    public int getStart()
    {
      return iiStart;
    }

    public int getLen()
    {
      return iiLen;
    }

    public String toString()
    {
      return String.format("start: %d, end: %d\n\r", iiStart, iiLen);
    }
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer(String.format("Category: %s, Concept: %s, Count: %d\n\r", isCategory, isConcept, iiCount));

    for (int i = 0; i < ioPositionList.size(); i++)
      sb.append(ioPositionList.get(i));

    return sb.toString();
  }
}

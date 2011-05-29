package rs.etf.analyzer.parser;

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
public class RedWordObject
{
  private long iiID;
  private String isWord;
  private long iiClassifiedWordID;
  private int iiCount;

  private int iiType;

  public RedWordObject(final String asWord, final int aiCount, final int aiType)
  {
    isWord = asWord;
    iiCount = aiCount;
    iiType = aiType;
  }

  public RedWordObject()
  {
    iiCount = 1;
  }

  public static RedWordObject createInstance()
  {
    return new RedWordObject();
  }

  public static RedWordObject createInstance(final String asWord, final int aiCount)
  {
    return new RedWordObject(asWord, aiCount, Type.NONE);
  }

  public long getID()
  {
    return iiID;
  }

  public String getWord()
  {
    return isWord;
  }

  public int getCount()
  {
    return iiCount;
  }

  public void updateCount(final int aiCount)
  {
    iiCount += aiCount;
  }

  public void setType(final int aiType)
  {
    iiType = aiType;
  }
}

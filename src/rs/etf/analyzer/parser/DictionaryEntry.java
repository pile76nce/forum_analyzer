package rs.etf.analyzer.parser;

import java.io.IOException;
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
public class DictionaryEntry<Category>
{
  private final String isConcept;
  private final String isStemmedConcept;
  private final Category isCategory;
  private final int iiCount;
  private final double idScore;

  private ParseResult ioParseResult;

  public DictionaryEntry(final Category asCategory, final String asConcept)
  {
    isConcept = asConcept;
    isCategory = asCategory;
    isStemmedConcept = "";
    iiCount = 0;
    idScore = 0.0;
  }

  public DictionaryEntry(final Category asCategory, final String asConcept,
                         final String asStemmedConcept)
  {
    isConcept = asConcept;
    isCategory = asCategory;
    isStemmedConcept = asStemmedConcept;
    iiCount = 0;
    idScore = 0.0;
  }

  public DictionaryEntry(final String asConcept, final Category asCategory,
                         final int aiCount, final double adScore)
  {
    isConcept = asConcept;
    isCategory = asCategory;
    isStemmedConcept = "";
    iiCount = aiCount;
    idScore = adScore;
  }

  public void setResult(ParseResult pr)
  {
    ioParseResult = pr;
  }

  public String getConcept()
  {
    return isConcept;
  }

  public String getStemmedConcept()
  {
    return isStemmedConcept;
  }

  public Category getCategory()
  {
    return isCategory;
  }

  public int getCount()
  {
    return iiCount;
  }

  public double getScore()
  {
    return idScore;
  }

  public void serialize() throws IOException
  {
  }

  public boolean equals(Object o)
  {
    try
    {
      DictionaryEntry<String> loEntry = (DictionaryEntry<String>) o;
      return (isCategory.equals(loEntry.getCategory())
              && isConcept.equals(loEntry.getConcept())
              && iiCount == loEntry.getCount());

    }
    catch (Exception e)
    {
      return false;
    }
  }

  public String toString()
  {
    return null;
  }
}

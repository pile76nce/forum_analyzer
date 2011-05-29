package rs.etf.analyzer.parser;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Klasa <code>ConceptDictionaryEntry</code> sluzi za reprezentovanje jednog zapisa u recniku sa konceptima
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class ConceptDictionaryEntry extends AbstractDictionaryEntry implements Comparable
{
  private int iiTokenId;
  private int iiCategoryId;

  private String isConcept;
  private String isCategory;

  private String isStemmedConcept;
  private String[] ioStemmedConcept;

  private Concept ioConcept = null;

  private static Stemmer ioStemmer = new Stemmer();

  private int iiCount;
  private double idScore;

  private ArrayList<Position> ioPositionList = new ArrayList<Position>();

  public ConceptDictionaryEntry(final String asConcept, final String asCategory, final int aiCategoryId)
  {
    isConcept = asConcept;
    isCategory = asCategory;

    iiCategoryId = aiCategoryId;

    isStemmedConcept = ioStemmer.stemStringArray(isConcept);

    ioStemmedConcept = ioStemmer.stemStringToArray(isConcept);
  }

  public ConceptDictionaryEntry(final Concept aoConcept)
  {
    ioConcept = aoConcept;
    isConcept = ioConcept.getConcept();

    isStemmedConcept = ioStemmer.stemStringArray(isConcept);

    ioStemmedConcept = ioStemmer.stemStringToArray(isConcept);
  }

  public ConceptDictionaryEntry(final String asConcept)
  {
    isConcept = asConcept;

    isStemmedConcept = ioStemmer.stemStringArray(isConcept);

    ioStemmedConcept = ioStemmer.stemStringToArray(isConcept);
  }


  public void addPosition(Position p)
  {
    iiTokenId = p.getTokenId();
    ioPositionList.add(new Position(isStemmedConcept, p.getTokenId(), p.getStart(), isConcept.length()));
  }

  public void addPositionList(ArrayList<Position> aoPositionList)
  {
    ioPositionList.addAll(aoPositionList);
  }

  public String getToken()
  {
    return null;
  }

  public int getTokenId()
  {
    return iiTokenId;
  }

  public int getCategoryId()
  {
    return iiCategoryId;
  }

  public String getCategory()
  {
    return isCategory;
  }

  public ArrayList<Position> get()
  {
    return ioPositionList;
  }

  public int getCount()
  {
    iiCount = ioPositionList.size();

    return iiCount;
  }

  public void setConcept(Concept aoConcept)
  {
    ioConcept = aoConcept;
  }

  public void setScore(final double adScore)
  {
    idScore = adScore;
  }

  public double getScore()
  {
    return idScore;
  }

  public Concept getConceptObject()
  {
    return ioConcept;
  }

  public String getConcept()
  {
    return isConcept;
  }

  public String getStemmedConcept()
  {
    return isStemmedConcept;
  }

  public String[] getStemmedConceptArray()
  {
    return ioStemmedConcept;
  }

  public int compareTo(Object o)
  {
    try
    {
      ConceptDictionaryEntry loCDE = (ConceptDictionaryEntry) o;

      if (iiTokenId > loCDE.getTokenId())
        return 1;
      else
        if (iiTokenId < loCDE.getTokenId())
        return -1;

      return 0;
    }
    catch (Exception e)
    {
      return 0;
    }
  }

  public String toString()
  {
    return ioConcept.toString();
  }
}

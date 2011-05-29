package rs.etf.analyzer.parser;

import java.util.ArrayList;

/**
 *
 * Klasa <code>StemmedTokenDictionaryEntry</code> služi za predstavljanje zapis u recniku tokena
 * ciji su nazivi svedeni na osnovni oblik
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class StemmedTokenDictionaryEntry extends AbstractDictionaryEntry implements Comparable
{
  private String[] ioStemmedTokens;

  private int iiTokenId;

  public StemmedTokenDictionaryEntry(final String asToken, int aiTokenId, int aiStart)
  {
    isToken = asToken;
    iiTokenId = aiTokenId;
    iiStart = aiStart;
    ioStemmedTokens = ioStemmer.stemStringToArray(isToken);
  }

  public StemmedTokenDictionaryEntry(final String asToken, ArrayList<Position> o)
  {
    isToken = asToken;
    ioPositionList.addAll(o);
  }

  public StemmedTokenDictionaryEntry(final String asToken)
  {
    isToken = asToken;
  }

  public void addPositionList(ArrayList<Position> a)
  {
    ioPositionList.addAll(a);
  }

  public int getTokenId()
  {
    return iiTokenId;
  }

  public int getStart()
  {
    return iiStart;
  }

  public String[] getStemmedTokens()
  {
    return ioStemmedTokens;
  }

  public int compareTo(Object o)
  {
    try
    {
      StemmedTokenDictionaryEntry loSTDE = (StemmedTokenDictionaryEntry)o;

      if (iiTokenId > loSTDE.getTokenId())
        return 1;
      else
        if (iiTokenId < loSTDE.getTokenId())
          return -1;

      return 0;
    }
    catch (Exception e)
    {
      return 0;
    }
  }
}

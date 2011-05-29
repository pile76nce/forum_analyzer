package rs.etf.analyzer.parser;

import java.util.ArrayList;
import java.util.Collections;

/** *
 * <p>Klasa <code>SubjectAnalyzer</code> reprezentuje recnik sa tokenima ciji su nazivi svedeni na osnovni oblik</p>
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class StemmedTokenDictionary extends AbstractDictionary
{
  private static Stemmer ioStemmer = new Stemmer();

  public StemmedTokenDictionary()
  {
    ioEntryMap = new ObjectPositionMap();
  }

  /**
   * Metoda koja dodaje novi zapis u recnik tokena
   * @param aoEntry AbstractDictionaryEntry
   */
  public void addEntry(AbstractDictionaryEntry aoEntry)
  {
    StemmedTokenDictionaryEntry loSTDE = (StemmedTokenDictionaryEntry)aoEntry;

    iiCountAllTokens++;

    if (!NoiseWords.checkFor(loSTDE.getToken()))
    {
      String[] lsStemmedTokens = loSTDE.getStemmedTokens();
      int liTempIndex = 0;

      for (int i = 0; i < lsStemmedTokens.length; i++)
      {
        ioEntryMap.put(lsStemmedTokens[i],
                       new Position(lsStemmedTokens[i], loSTDE.getTokenId(),
                                    loSTDE.getStart() + liTempIndex,
                                    lsStemmedTokens[i].length()));

        liTempIndex += lsStemmedTokens[i].length();
      }

      iiCountNonNoiseTokens++;
    }
  }

  public int addEntry(int aiTokenId, String asToken, int aiStart, int aiLength)
  {
    String[] lsStemmedTokens = ioStemmer.stemStringToArray(asToken);
    int liTempIndex = 0;

    for (int i = 0; i < lsStemmedTokens.length; i++)
    {
      ioEntryMap.put(lsStemmedTokens[i], new Position(lsStemmedTokens[i], aiTokenId, aiStart + liTempIndex, aiLength));
      liTempIndex+=lsStemmedTokens[i].length();
    }

    return lsStemmedTokens.length;
  }

  public void addEntry(String s, Position p)
  {
    ioEntryMap.put(s, p);
  }

  public ArrayList<Position> getEntry1(String asToken)
  {
    String[] lsStemmedTokens = ioStemmer.stemStringToArray(asToken);

    ArrayList<Position> loPositionList = new ArrayList<Position>();
    ArrayList<Position> p = null;

    for (int i = 0; i < lsStemmedTokens.length; i++)
    {
      p = ioEntryMap.get(lsStemmedTokens[i]);

      if (p != null)
        loPositionList.addAll(p);
    }

    Collections.sort(loPositionList, Collections.reverseOrder());

    return loPositionList;
  }

  public StemmedTokenDictionaryEntry getEntry(final String asToken)
  {
//    String lsToken = ioStemmer.stemOneWord(asToken);

    StemmedTokenDictionaryEntry loRetEntry = new StemmedTokenDictionaryEntry(asToken);

    ArrayList<Position> loPositionList = ioEntryMap.get(asToken);

    if (loPositionList != null)
      loRetEntry = new StemmedTokenDictionaryEntry(asToken, loPositionList);

    return loRetEntry;
  }

  public int getAllTokens()
  {
    return iiCountAllTokens;
  }

  public int getNonNoiseTokens()
  {
    return iiCountNonNoiseTokens;
  }
}

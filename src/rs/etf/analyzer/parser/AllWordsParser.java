package rs.etf.analyzer.parser;

import rs.etf.analyzer.util.ObjectCounterMap;
import java.util.Set;
import java.util.StringTokenizer;

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
public class AllWordsParser
{
  private String isText = null;

  public AllWordsParser()
  {
  }

  public ObjectCounterMap createResultList(final String asText)
  {
    isText = asText;

    ObjectCounterMap loCounter = new ObjectCounterMap();
    StringTokenizer loTokenizer = new StringTokenizer(isText, " ,.!?:+-*/()[]{}"+"\n\r\t");

    Stemmer loStemmer = new Stemmer();

    Set ioNoiseWords = NoiseWords.getAllNoiseWords();
    String lsWord;
    while (loTokenizer.hasMoreTokens())
    {
      lsWord = loTokenizer.nextToken().toLowerCase();
//      lsWord = loStemmer.stemOneWord(lsWord);

      if (ioNoiseWords.contains(lsWord) == false)
        loCounter.increment(lsWord);
    }

//    List loList = loCounter.keysOrderedByCountList();

    return loCounter;
  }
}

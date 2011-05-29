package rs.etf.analyzer.parser;

import java.util.Iterator;

/**
 * <p>Title: </p>
 *
 * <p>Description: Apstraktni recnik</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public abstract class AbstractDictionary
{
  /**
   * Brojac svih tokena u recniku
   */
  protected int iiCountAllTokens;

  /**
   * Brojac tokena koji se ne pojavljuju u skupu jako frekventnih reci
   */
  protected int iiCountNonNoiseTokens;

  protected ObjectPositionMap ioEntryMap;

  protected AbstractDictionary()
  {
    ioEntryMap = new ObjectPositionMap();

    iiCountAllTokens = 0;
    iiCountNonNoiseTokens = 0;
  }

  public Iterator<String> getIterator()
  {
    return ioEntryMap.keySet().iterator();
  }

  public abstract void addEntry(AbstractDictionaryEntry aoEntry);

  public abstract AbstractDictionaryEntry getEntry(final String asToken);
}

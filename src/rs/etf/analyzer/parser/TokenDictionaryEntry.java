package rs.etf.analyzer.parser;

import java.util.ArrayList;

/**
 * Klasa <code>TokenDictionaryEntry</code> predstavlja zapis u recniku sa tokenima</p>
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class TokenDictionaryEntry extends AbstractDictionaryEntry implements Comparable
{
  /**
   * Identifikator tokena
   */
  private int iiTokenId;

  /**
   * Duzina tokena
   */
  private int iiLength;

  /**
   * Kreiranje zapisa u recniku tokena
   * @param asToken procitani token
   * @param aiTokenId redni broj tokena
   * @param aiStart pocetna pozicija tokena
   */
  public TokenDictionaryEntry(final String asToken, final int aiTokenId, final int aiStart)
  {
    isToken = asToken;

    iiTokenId = aiTokenId;

    iiStart = aiStart;

    iiLength = isToken.length();
  }

  public TokenDictionaryEntry(final String asToken, ArrayList<Position> aoPositionList)
  {
    isToken = asToken;
    ioPositionList.addAll(aoPositionList);
  }

  /**
   * Metoda koja redni vraca redni broj tokena
   * @return Redni broj tokena
   */
  public int getTokenId()
  {
    return iiTokenId;
  }

  /**
   * Metoda koja vraca pocetnu poziciju tokena
   * @return Redni broj tokena
   */
  public int getStart()
  {
    return iiStart;
  }

  /**
   * Metoda koja vraca duzinu tokena
   * @return Redni broj tokena
   */
  public int getLength()
  {
    return iiLength;
  }

  /**
   * Metoda koja poredi polja iiTokenId postojeceg i sa zadatim zapisom
   * @param o Zadati zapis u recniku
   * @return  1 ako je veci
   *         -1 ako je manji
   *          0 ako su jednaki
   */
  public int compareTo(Object o)
  {
    try
    {
      TokenDictionaryEntry loSTDE = (TokenDictionaryEntry)o;

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

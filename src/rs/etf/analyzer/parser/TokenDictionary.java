package rs.etf.analyzer.parser;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * <p>Title: </p>
 *
 * Klasa <code></code> služi za čuvanje rečnika sa tokenima koji su izdvojeni iz nekog teksta</p>
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class TokenDictionary extends AbstractDictionary
{
  public TokenDictionary()
  {
    super();
  }

  /**
   * Metoda koja dodaje novi zapis u recnik tokena
   * @param aoEntry AbstractDictionaryEntry
   */
  public void addEntry(AbstractDictionaryEntry aoEntry)
  {
    TokenDictionaryEntry loTDE = (TokenDictionaryEntry)aoEntry;
    final String lsToken = loTDE.getToken();

    iiCountAllTokens++;

    if (!NoiseWords.checkFor(lsToken))
    {
      iiCountNonNoiseTokens++;
      ioEntryMap.put(loTDE.getToken(), new Position(loTDE.getToken(), loTDE.getTokenId(), loTDE.getStart(), loTDE.getLength()));
    }
  }

  /**
   * Metoda koja vraca listu sa pozicijama tokena
   * @param asToken String
   * @return ArrayList
   */
  public ArrayList<Position> getEntry1(final String asToken)
  {
    return ioEntryMap.get(asToken);
  }

  /**
   * Metoda koja vraca zapis iz recnika za token sa zadatim nazivom
   * @param asToken Token sa zadatim nazivom
   * @return Zapis iz recnika za token sa zadatim nazivom
   */
  public TokenDictionaryEntry getEntry(final String asToken)
  {
//     naziv tokena sa malim slovima
     String lsToken = asToken.toLowerCase();
//     listu sa pozicijama
     ArrayList<Position> loPositionList = ioEntryMap.get(lsToken);

//     ako lista postoji pravi se zapis iz recnika sa listom pozicija, inace se pravi zapis sa praznom listom
     TokenDictionaryEntry loRetEntry = null;
     if (loPositionList != null)
       loRetEntry = new TokenDictionaryEntry(lsToken, loPositionList);
     else
       loRetEntry = new TokenDictionaryEntry(lsToken, new ArrayList<Position>());

     return loRetEntry;
  }

  /**
   * Metoda koja vraca broj svih tokena u recniku
   * @return Broj svih tokena u recniku
   */
  public int getAllTokens()
  {
    return iiCountAllTokens;
  }

  /**
   * Metoda koja vraca broj tokena koji ne pripadaju skupu jako frekventnih reci
   * @return Broj tokena koji ne pripadaju skupu jako frekventnih reci
   */
  public int getNonNoiseTokens()
  {
    return iiCountNonNoiseTokens;
  }

  /**
   * Metoda koja prikazuje sadrzaj recnika
   */
  public void print()
  {
    Iterator loIterator = ioEntryMap.entrySet().iterator();

    String lsConcept;
    while (loIterator.hasNext())
    {
      lsConcept = (String)loIterator.next();
      System.out.println(String.format("concept: %s", lsConcept));
    }
  }
}

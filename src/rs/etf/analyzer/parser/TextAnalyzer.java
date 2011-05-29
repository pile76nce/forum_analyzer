package rs.etf.analyzer.parser;

import rs.etf.analyzer.parser.tokens.TextTokenizer;
import java.util.List;
import rs.etf.analyzer.parser.TokenDictionary;
import java.util.Iterator;
import rs.etf.analyzer.parser.TokenDictionaryEntry;
import java.util.Scanner;
import java.io.File;

/**
 * Klasa <code>SubjectAnalyzer</code> služi za analizu teksta i smeštanje pročitanih reči u bazu
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class TextAnalyzer
{
  private TextTokenizer ioTextTokenizer;

  private RedWordMapper ioRedWordMapper = null;

  /**
   * Konstruktor
   * @throws Exception
   */
  public TextAnalyzer() throws Exception
  {
    ioRedWordMapper = new RedWordMapper();
    ioRedWordMapper.doLoad();

    ioTextTokenizer = new TextTokenizer();
  }

  /**
   * Metoda koja cita tekst u dokumentu i smesta ga u string
   * @param aoDocument datoteka u kojoj se nalazi tekst
   * @return Tekst u dokumentu
   * @throws Exception
   */
  public String doLoad(final File aoDocument) throws Exception
  {
    return (new Scanner(aoDocument).useDelimiter("\\Z")).next();
  }

  /**
   * Metoda koja izdvaja reci iz teksta i smesta ih u bazu. U bazi se nalazi rec i broj njenog pojavljivanja u tekstu.
   * @param asText Sadrzaj teksta koji se analizira
   * @throws Exception
   */
  public void doAnalyze(final String asText) throws Exception
  {
    ioTextTokenizer.getAllTokens(asText);

    TokenDictionary loTokenDictionary = ioTextTokenizer.getTokenDictionary();
    Iterator<String> loTokenDictionaryIterator = loTokenDictionary.getIterator();

    String lsWord = null;
    int liCount;

    TokenDictionaryEntry loTDE = null;

    while (loTokenDictionaryIterator.hasNext())
    {
      lsWord = loTokenDictionaryIterator.next();
      loTDE = loTokenDictionary.getEntry(lsWord);
      liCount = loTDE.getListSize();

//      napravi objekat, upisi u njega rec, oznaci ga za upis
      RedWordObject rwo = ioRedWordMapper.findBy(lsWord);

//      ako objekat vec postoji u hash-mapi procitanih reci, povecaj broj pojavljivanja
//      oznaci ga za izmenu(update)
      if (rwo != null)
      {
        rwo.setType(Type.UPDATE);
        ioRedWordMapper.update(rwo, liCount);
      }
      else
      {
        rwo = new RedWordObject(lsWord, liCount, Type.INSERT);
        ioRedWordMapper.insert(rwo);
      }
    }
  }
}

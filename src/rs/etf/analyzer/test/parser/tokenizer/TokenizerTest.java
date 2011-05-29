package rs.etf.analyzer.test.parser.tokenizer;

import rs.etf.analyzer.test.BaseTestCase;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import rs.etf.analyzer.parser.Position;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.TokenDictionary;
import rs.etf.analyzer.parser.StemmedTokenDictionary;

/**
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class TokenizerTest extends BaseTestCase
{
  private final String TEST_FILE = "./test/pipeline.txt";

  public void testOne()
  {
    File file = new File(TEST_FILE);

    Scanner loScanner = null;
    String lsTestString = null;

    try
    {
      loScanner = new Scanner(file).useDelimiter("\\Z");

      lsTestString = loScanner.next();
    }
    catch (FileNotFoundException ex)
    {
      ex.printStackTrace();
    }

    TextTokenizer ioTokenizer = new TextTokenizer();

//    analiza teksta i formiranje recnika
    ioTokenizer.getAllTokens(lsTestString);

//    dohvatanje recnika procitanih reci
    TokenDictionary loTokenDictionary = ioTokenizer.getTokenDictionary();
//    dohvatanje recnika procitanih reci u svom osnovnom obliku
    StemmedTokenDictionary loStemmedTokenDictionary = ioTokenizer.getStemmedTokenDictionary();

//    lista sa pozicijama za odgovarajucu rec u recniku procitanih reci
    ArrayList<Position> ioPositionList = loTokenDictionary.getEntry1("pipeline");
    for (int i = 0; i < ioPositionList.size(); i++)
      System.out.println(ioPositionList.get(i));
    System.out.println(ioPositionList.size());
    assertEquals(ioPositionList.size(), 61);

//    lista sa pozicijama za odgovarajucu rec u recniku procitanih reci u svom osnovnom obliku
    ioPositionList = loStemmedTokenDictionary.getEntry1("pipelin");
    for (int i = 0; i < ioPositionList.size(); i++)
      System.out.println(ioPositionList.get(i));
    System.out.println(ioPositionList.size());
    assertEquals(ioPositionList.size(), 72);
  }
}

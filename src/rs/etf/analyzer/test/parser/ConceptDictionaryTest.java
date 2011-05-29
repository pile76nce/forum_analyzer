package rs.etf.analyzer.test.parser;

import rs.etf.analyzer.test.BaseTestCase;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import rs.etf.analyzer.parser.ConceptDictionaryEntry;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import rs.etf.analyzer.parser.ConceptDictionary;
import java.util.Scanner;
import java.util.Collections;
import java.io.File;
import rs.etf.analyzer.parser.ConfigParser;
import rs.etf.analyzer.parser.Position;

/**
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class ConceptDictionaryTest extends BaseTestCase
{
  public void testOne()
  {
    ConfigParser ioConfigParser = new ConfigParser();
    ioConfigParser.parseAll();
    File file = new File("./test/integer.txt");

    Scanner loScanner = null;
    String lsTestString = null;

    try
    {
      loScanner = new Scanner(file).useDelimiter("\\Z");

      lsTestString = loScanner.next(); // "1 fish 2 fish red fish blue fish";
    }
    catch (FileNotFoundException ex)
    {
    }

    TextTokenizer ioTokenizer = new TextTokenizer();

    ioTokenizer.getAllTokens(lsTestString);

    ConceptDictionary ioConceptDictionary = new ConceptDictionary();

    StemmedTokenDictionary ioStemmedTokenDictionary = ioTokenizer.getStemmedTokenDictionary();

    ConceptDictionaryEntry cde = ioConceptDictionary.getEntry("non-negative integers");

    String[] loStemmedConcept = cde.getStemmedConceptArray();

    ArrayList<Position> loPositionList = new ArrayList<Position>();
    ArrayList<Position> p = null;

    for (int i = 0; i < loStemmedConcept.length; i++)
    {
      if (p != null)
        loPositionList.addAll(p);
    }

    Collections.sort(loPositionList/*, Collections.reverseOrder()*/);

    ArrayList<Position> loFoundPositionList = new ArrayList<Position>();

    int liPreviousTokenId;
    int liCurrentTokenId;

    String lsPreviousToken;
    String lsCurrentToken;

    for (int i = 0; i <= loPositionList.size() - loStemmedConcept.length; i++)
    {
      boolean lbContinue = true;
      liPreviousTokenId = loPositionList.get(i).getTokenId();
      lsPreviousToken = loPositionList.get(i).getToken();

      for (int j = 1; j < loStemmedConcept.length; j++)
      {
        liCurrentTokenId = loPositionList.get(i + j).getTokenId();
        lsCurrentToken = loPositionList.get(i + j).getToken();

        if ((liCurrentTokenId - liPreviousTokenId == 1) && (lsCurrentToken.equals(lsPreviousToken) == false))
        {
          liPreviousTokenId = liCurrentTokenId;
          lsPreviousToken = lsCurrentToken;
        }
        else
        {
          lbContinue = false;
          break;
        }
      }

      if (lbContinue)
      {
        cde.addPosition(loPositionList.get(i));
        loFoundPositionList.add(loPositionList.get(i));
      }
    }

    ArrayList<Position> l = cde.get();

    for (int i = 0; i < l.size(); i++)
      System.out.println(String.format("position: %s", l.get(i)));
  }
}

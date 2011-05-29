package rs.etf.analyzer.test.parser;

import rs.etf.analyzer.test.BaseTestCase;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import rs.etf.analyzer.parser.ConceptDictionaryEntry;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.StemmedTokenDictionaryEntry;
import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import java.util.Scanner;
import rs.etf.analyzer.parser.ConfigParser;
import rs.etf.analyzer.parser.Position;

/**
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class ConceptParserTest extends BaseTestCase
{
  private final String TEST_ONE_FILE = "./test/integer.txt";

  public void testOne() throws IOException
  {
    ConfigParser ioConfigParser = new ConfigParser();
    ioConfigParser.parseAll();
    ConceptDictionary loConceptDictionary = ioConfigParser.getConceptDictionary();

    File file = new File(TEST_ONE_FILE);

    String lsConcept = "two's complement";

    Scanner loScanner = null;
    String lsTestString = null;

    try
    {
      loScanner = new Scanner(file).useDelimiter("\\Z");

      lsTestString = loScanner.next();
    }
    catch (FileNotFoundException ex)
    {
    }

    TextTokenizer loTokenizer = new TextTokenizer();
    loTokenizer.getAllTokens(lsTestString);

    StemmedTokenDictionary loStemmedTokenDictionary = loTokenizer.
        getStemmedTokenDictionary();
    ConceptDictionaryEntry loCDE = loConceptDictionary.getEntry(lsConcept);

    ArrayList<ConceptDictionaryEntry>
        loFoundPositionList = new ArrayList<ConceptDictionaryEntry> ();

    String[] loStemmedConcept = loCDE.getStemmedConceptArray();

    ArrayList<Position> loPositionList = new ArrayList<Position> ();
    ArrayList<Position> p = null;

    StemmedTokenDictionaryEntry loSTDE = null;

    for (int i = 0; i < loStemmedConcept.length; i++)
    {
      loSTDE = loStemmedTokenDictionary.getEntry(loStemmedConcept[i]);

      if (loSTDE != null)
      {
        p = loSTDE.getPositionList();
        loPositionList.addAll(p);
      }
    }

    Collections.sort(loPositionList /*, Collections.reverseOrder()*/);

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

        if ( (liCurrentTokenId - liPreviousTokenId == 1) &&
            (lsCurrentToken.equals(lsPreviousToken) == false))
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
        loCDE.addPosition(loPositionList.get(i));
        loFoundPositionList.add(loCDE);
      }
    }

    loPositionList = loCDE.get();
    for (int i = 0; i < loPositionList.size(); i++)
    {
      System.out.println(loPositionList.get(i).toString());
    }
  }
}

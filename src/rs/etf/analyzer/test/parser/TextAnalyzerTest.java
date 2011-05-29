package rs.etf.analyzer.test.parser;

import rs.etf.analyzer.test.BaseTestCase;

import rs.etf.analyzer.parser.TextAnalyzer;
import java.io.File;

/**
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class TextAnalyzerTest extends BaseTestCase
{
  public void testOne()
  {
    try
    {
      TextAnalyzer ioTextAnalyzer = new TextAnalyzer();

      String isText = ioTextAnalyzer.doLoad(new File("./test_data/memorija.txt"));
      ioTextAnalyzer.doAnalyze(isText);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}

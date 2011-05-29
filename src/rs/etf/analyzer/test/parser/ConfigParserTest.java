package rs.etf.analyzer.test.parser;

import rs.etf.analyzer.test.BaseTestCase;
import rs.etf.analyzer.parser.ConfigParser;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import java.util.Scanner;
import java.io.File;
import rs.etf.analyzer.parser.Position;
import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.Concept;
import rs.etf.analyzer.parser.Label;
/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class ConfigParserTest extends BaseTestCase
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

      lsTestString = loScanner.next();
    }
    catch (FileNotFoundException ex)
    {
    }

    TextTokenizer ioTokenizer = new TextTokenizer();

    ioTokenizer.getAllTokens(lsTestString);

    StemmedTokenDictionary ioStemmedTokenDictionary = ioTokenizer.getStemmedTokenDictionary();

    ArrayList<Position> ioPositionList;

    String cn;

    cn = "two's complement";
    ioPositionList = ioStemmedTokenDictionary.getEntry1(cn);
    for (int j = 0; j < ioPositionList.size(); j++)
          System.out.println(String.format("concept: %s, pos: %s", cn, ioPositionList.get(j)));
  }

  public void testTwo() throws Exception
  {
    ConfigParser ioConfigParser = new ConfigParser();

    ioConfigParser.parseOntology();
    ArrayList<Concept> loConceptHierarchy = ioConfigParser.getConceptHierarchy();

    //
    ConceptDictionary cd = ioConfigParser.getConceptDictionary();
    ArrayList<Concept> conceptList = cd.getList();
    //

    for (int i = 0; i < loConceptHierarchy.size(); i++)
    {
      Concept c = loConceptHierarchy.get(i);

      if (c != null)
      {
        Concept loConcept = new Concept(c);
        System.out.println(String.format("ont class: %s", loConcept.getOntClass().getLocalName()));

        ArrayList<Label> loLabelList = loConcept.getLabelList();
        System.out.println(String.format("label: %s", loConcept.getConcept()));

        System.out.println(String.format("label list:"));
        for (int liLabelIndex = 0; liLabelIndex < loLabelList.size(); liLabelIndex++)
        {
          if (loLabelList.size() == 2)
            System.out.println(String.format("           %s", loLabelList.get(liLabelIndex).getName()));
        }
      }
    }
  }
}

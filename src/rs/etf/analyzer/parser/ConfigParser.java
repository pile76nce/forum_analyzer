package rs.etf.analyzer.parser;

import org.xml.sax.helpers.DefaultHandler;
import rs.etf.analyzer.parser.DictionaryEntry;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import java.util.List;
import java.util.Hashtable;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import javax.xml.parsers.SAXParserFactory;
import rs.etf.analyzer.parser.tokens.ConceptMap;
import java.util.ArrayList;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Collections;
import com.hp.hpl.jena.ontology.OntClass;
import java.util.Stack;

import rs.etf.analyzer.ontology.ClassHierarchy;
import java.util.HashMap;
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
public class ConfigParser
{
  private List<Chapter> ioChapterList = null;
  private List<Category> ioCategoryList = null;

  private ArrayList<Concept> ioConceptHierarchy = null;

//  private Hashtable<String, String> ioConceptDictionary = null;
//  private Hashtable<String, Category> ioCategoryDictionary = null;

  private HashMap<String, Integer> ioConceptMap = null;

  private ConceptDictionary ioConceptDictionary;

  private static Stemmer ioStemmer = new Stemmer();

  private DefaultHandler ioHandler;

  private int iiCategoryId;

  public ConfigParser()
  {
    ioHandler = new ConceptHandler();

    ioChapterList = new ArrayList<Chapter>();
    ioCategoryList = new ArrayList<Category>();

    ioConceptMap = new HashMap<String, Integer>();

    ioConceptDictionary = new ConceptDictionary();

    iiCategoryId = -1;
  }

  public void parseAll()
  {
    parseXmlFile("./test/concepts.xml", ioHandler, false);
  }

  public void parseOntology() throws Exception
  {
    parseHierarchy("ontology/AOR.owl");
  }

  public List<Category> getCategoryList()
  {
    return ioCategoryList;
  }

  public List<Chapter> getChapterlist()
  {
    return ioChapterList;
  }

  public ConceptDictionary getConceptDictionary()
  {
    return ioConceptDictionary;
  }

  public HashMap<String, Integer> getConceptMap()
  {
    return ioConceptMap;
  }

  private void parseXmlFile(final String asFilename, DefaultHandler aoHandler, final boolean abValidating)
  {
    SAXParserFactory loFactory = SAXParserFactory.newInstance();
    loFactory.setValidating(abValidating);

    try
    {
      loFactory.newSAXParser().parse(new File(asFilename), aoHandler);
    }
    catch (SAXException ex)
    {
    }
    catch (ParserConfigurationException ex)
    {
    }
    catch (IOException ex)
    {
    }
  }

  public void parseHierarchy(final String asFileName) throws Exception
  {
    ClassHierarchy ch = new ClassHierarchy(this);
//    ArrayList<Concept> loConceptList = ch.createConceptHierarchy(asFileName);

    ioConceptHierarchy = ch.createConceptHierarchy(asFileName);


    /*Concept loConcept = null;
    for (int i = 0; i < loConceptList.size(); i++)
    {
      loConcept = loConceptList.get(i);
      loConcept.calculate();
      System.out.println(String.format("label: %s", loConcept));
    }*/
  }

  public void addConcept(Concept aoConcept)
  {
    if (aoConcept.getOntClass().getLabel(null) != null)
    {
      StringTokenizer st = new StringTokenizer(aoConcept.getConcept(), ";");
      while (st.hasMoreTokens())
      {
        Concept c = new Concept(aoConcept);
        c.setLabel(st.nextToken(";"));

        ConceptDictionaryEntry loEntry = new ConceptDictionaryEntry(c);
        ioConceptDictionary.addEntry(loEntry);
      }
    }
  }

  public ArrayList<Concept> getConceptHierarchy()
  {
    return ioConceptHierarchy;
  }

  public static void main(String[] args)
  {
    ConfigParser ioConfigParser = new ConfigParser();

    ioConfigParser.test1();

    ioConfigParser.test2();

    ioConfigParser.test3();
  }

  public void test1()
  {
    parseAll();

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

    TextTokenizer ioTokenizer = new TextTokenizer("ADD", false, true, false);

    ioTokenizer.getAllTokens(lsTestString);

    StemmedTokenDictionary ioStemmedTokenDictionary = ioTokenizer.getStemmedTokenDictionary();

    List l = null;//ioConceptMap.keysList();

//    ioStemmedTokenDictionary.

    ArrayList<Position> ioPositionList;

    String cn;
    /*for (int i = 0; i < l.size(); i++)
    {
      cn = (String)l.get(i);
      System.out.println(String.format("concept: %s", cn));

      ioPositionList = ioStemmedTokenDictionary.getEntry1(cn);
      if (ioPositionList != null)
      {
        for (int j = 0; j < ioPositionList.size(); j++)
          System.out.println(String.format("concept: %s, pos: %s", cn,
                                           ioPositionList.get(j)));
      }
    }*/

    cn = "two's complement";
    ioPositionList = ioStemmedTokenDictionary.getEntry1(cn);
    for (int j = 0; j < ioPositionList.size(); j++)
          System.out.println(String.format("concept: %s, pos: %s", cn,
                                           ioPositionList.get(j)));
  }

  public void test2()
  {
    parseAll();

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

    TokenDictionary loTokenDictionary = ioTokenizer.getTokenDictionary();
    TokenDictionaryEntry loTDE = loTokenDictionary.getEntry("Antibiotic");

    StemmedTokenDictionary ioStemmedTokenDictionary = ioTokenizer.getStemmedTokenDictionary();

//    ArrayList<Position> ioPositionList = ioStemmedTokenDictionary.getEntry1("two").getPositionList();

    ConceptDictionaryEntry cde = ioConceptDictionary.getEntry("non-negative integers");

    String[] loStemmedConcept = cde.getStemmedConceptArray();

    ArrayList<Position> loPositionList = new ArrayList<Position>();
    ArrayList<Position> p = null;

    for (int i = 0; i < loStemmedConcept.length; i++)
    {
//      p = ioStemmedTokenDictionary.getEntry1(loStemmedConcept[i]).getPositionList();

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

  public void test3()
  {
    try
    {
      parseHierarchy("ontology/test.owl");

      File file = new File("./test/AntibioticDrugs1.txt");

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

      TokenDictionary loTokenDictionary = ioTokenizer.getTokenDictionary();

//      loTokenDictionary.print();

      TokenDictionaryEntry loTDE = loTokenDictionary.getEntry("Antibiotic");

      ArrayList<Position> loPos = loTDE.getPositionList();
      for (int i = 0; i < loPos.size(); i++)
      {
        Position p = loPos.get(i);
        System.out.println(String.format("pos: %s", loPos.get(i)));
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  class ConceptHandler extends DefaultHandler
  {
    int liDepth = 0;
    Chapter loChapter;
    Category loCategory;

    String lsCategory = null;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
    {
      ConceptDictionaryEntry loEntry = null;

      String lsChapter = null;
      String lsConcept = null;
      String lsStemmedConcept = null;

      // Get the number of attribute
      int length = atts.getLength();

      if (liDepth == 1)
      {
        lsChapter = atts.getValue(0);
        loChapter = new Chapter(lsChapter);

        ioChapterList.add(loChapter);
      }

      if (liDepth == 2)
      {
        lsCategory = atts.getValue(0);
        loCategory = new Category(lsCategory);

        ioCategoryList.add(loCategory);
        loChapter.getCategoryList().add(loCategory);

        iiCategoryId++;
      }

      if (liDepth == 3)
      {
        lsConcept = atts.getValue(0);
        lsStemmedConcept = ioStemmer.stemOneWord(lsConcept);

          loEntry = new ConceptDictionaryEntry(lsConcept, lsCategory, iiCategoryId);
          loCategory.add(loEntry);
//          ioConceptMap.put(loEntry);

          ioConceptDictionary.addEntry(loEntry);
      }

      liDepth++;
    }

    public void endElement(String uri, String localName, String qName) throws SAXException
    {
      liDepth--;
    }

    public void characters(char ch[], int start, int length) throws SAXException
    {
    }
  }
}

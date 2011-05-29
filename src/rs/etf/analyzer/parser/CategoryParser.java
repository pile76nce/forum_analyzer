package rs.etf.analyzer.parser;

import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import javax.xml.parsers.*;
import java.io.*;
import org.xml.sax.*;
import java.util.Hashtable;
import rs.etf.analyzer.parser.DictionaryEntry;
import java.util.List;
import java.util.ArrayList;

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
public class CategoryParser
{
//  private static Hashtable<String, Category> ioCategory = new Hashtable<String, Category>();
  private List<Chapter> ioChapterList = null;
  private List<Category> ioCategoryList = null;

  private Hashtable<String, String> ioConceptDictionary = null;
  private Hashtable<String, Category> ioCategoryDictionary = null;

  private static Stemmer ioStemmer = new Stemmer();

  private DefaultHandler ioHandler;

  public CategoryParser()
  {
    ioHandler = new CategoryHandler();
    ioChapterList = new ArrayList<Chapter>();
    ioCategoryList = new ArrayList<Category>();
    ioConceptDictionary = new Hashtable<String,String>();
    ioCategoryDictionary = new Hashtable<String, Category>();
  }

  public void parseAll()
  {
    parseXmlFile("./test/concepts.xml", ioHandler, false);
  }

  public List<Category> getCategoryList()
  {
    return ioCategoryList;
  }

  public List<Chapter> getChapterlist()
  {
//    getAll();

    return ioChapterList;
  }

  public Hashtable<String, String> getConceptDictionary()
  {
    return ioConceptDictionary;
  }

  public Hashtable<String, Category> getCategoryDictionary()
  {
    return ioCategoryDictionary;
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

  class CategoryHandler extends DefaultHandler
  {
    int liDepth = 0;
    Chapter loChapter;
    Category loCategory;

    String lsCategory = null;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
    {
      DictionaryEntry loEntry = null;

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
      }

      if (liDepth == 3)
      {
        lsConcept = atts.getValue(0);
        lsStemmedConcept = ioStemmer.stemOneWord(lsConcept);

        ioCategoryDictionary.put(lsStemmedConcept, loCategory);

//        LogPanel.getInstance().setText(String.format("concept: %s", lsConcept), LogMessage.WARNING);

        if (ioConceptDictionary.containsKey(lsConcept) == false)
        {
          ioConceptDictionary.put(lsStemmedConcept, lsCategory);
          loEntry = new DictionaryEntry(lsCategory, lsConcept, lsStemmedConcept);

//          loCategory.add(loEntry);
        }
//        System.out.println(String.format("Category: %s; Concept: %s", lsCategory, lsConcept));

        // Process each attribute
        /*for (int i = 0; i < length; i++)
        {
          // Get names and values for each attribute
          String name = atts.getQName(i);
          String value = atts.getValue(i);

          // The following methods are valid only if the parser is namespace-aware

          // The uri of the attribute's namespace
          String nsUri = atts.getURI(i);

          // This is the name without the prefix
          String lName = atts.getLocalName(i);
        }*/
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

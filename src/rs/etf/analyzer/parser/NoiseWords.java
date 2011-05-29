package rs.etf.analyzer.parser;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import java.util.Set;
import java.util.HashSet;

/**
 * Klasa <code>NoiseWords</code>za rad sa recima cije je pojavljivanje u tekstu jako cesto</p>
 *
 * @author Dejan Prodanovi\u0107
 * @version 1.0
 */
public class NoiseWords
{
//  skup u koji se smestaju reci procitane iz konfiguracije
  private static Set<String> ioNoiseWords = new HashSet<String>();

  public NoiseWords()
  {
  }

  /**
   * Vraca skup reci procitanih iz konfiguracije
   * @return Skup reci procitanih iz konfiguracije
   */
  public static Set getAllNoiseWords()
  {
    DefaultHandler handler = new WordsHandler();

    parseXmlFile("./test/noise.xml", handler, false);

    return ioNoiseWords;
  }

  /**
   * Utvrdjuje da li odgovarajuca rec pripada skupu jako frekventnih reci
   * @param asWord String rec
   * @return da li odgovarajuca rec pripada skupu jako frekventnih reci
   */
  public static boolean checkFor(String asWord)
  {
    boolean test = ioNoiseWords.contains(asWord);

    return test;
  }

  /**
   * Parsiranje xml datoteke u kojoj su smestene frekventne reci
   * @param asFilename Datoteka koja se parsira
   * @param aoHandler Klasa za obradu svakog procitanog elementa tokom parsiranja
   * @param abValidating Da li ce parser vrsiti validaciju dokumenata kako budu parsirani. Podrazumevana vrednost je false
   */
  private static void parseXmlFile(final String asFilename, DefaultHandler aoHandler, final boolean abValidating)
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

  static class WordsHandler extends DefaultHandler
  {
    int liDepth = 0;

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException
    {
      // Get the number of attribute
      int length = atts.getLength();

      if (liDepth == 1)
        ioNoiseWords.add(atts.getValue(0));

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

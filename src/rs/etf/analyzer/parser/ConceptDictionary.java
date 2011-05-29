package rs.etf.analyzer.parser;

import java.util.ArrayList;
import rs.etf.analyzer.parser.tokens.ConceptMap;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * Klasa <code>ConceptDictionary</code> reprezentuje recnik sa konceptima</p>
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class ConceptDictionary extends AbstractDictionary
{
  private HashMap<String, ConceptDictionaryEntry> ioEntryMap = null;

  /**
   * Instanca  klasa koja naziv koncepta svodi na osnovni oblik
   */
  private static Stemmer ioStemmer = new Stemmer();

  public ConceptDictionary()
  {
    ioEntryMap = new HashMap<String, ConceptDictionaryEntry>();
  }

  /**
   * Metoda koja dodavaje novi zapis u recnik
   * @param aoEntry AbstractDictionaryEntry
   */
  public void addEntry(AbstractDictionaryEntry aoEntry)
  {
    ConceptDictionaryEntry loCDE = (ConceptDictionaryEntry)aoEntry;
//    if (ioEntryMap.get(loCDE.getStemmedConcept()) != null)
//      System.out.println(String.format("concept %s vec postoji", loCDE.getStemmedConcept()));

    ioEntryMap.put(loCDE.getStemmedConcept(), loCDE);
  }

  /**
   * Metoda koja vrsi izmenu koncepta
   * @param aoConcept Concept
   */
  public void updateEntry(Concept aoConcept)
  {
    String lsConcept = ioStemmer.stemStringArray(aoConcept.getConcept());

    ConceptDictionaryEntry loCDE = ioEntryMap.get(lsConcept);
    if (loCDE == null)
      loCDE = new ConceptDictionaryEntry(aoConcept);
    else
      loCDE.setConcept(aoConcept);

    ioEntryMap.put(loCDE.getStemmedConcept(), loCDE);
  }

  /**
   * Metoda koja za zadati koncept iz recnika vraca odgovarajuci zapis
   * @param asConcept String
   * @return ConceptDictionaryEntry
   */
  public ConceptDictionaryEntry getEntry(String asConcept)
  {
    String lsConcept = ioStemmer.stemStringArray(asConcept);

    ConceptDictionaryEntry cde = ioEntryMap.get(lsConcept);

    return cde;
  }

  /**
   * Metoda koja vraca listu sa nazivima konceptata
   * @return lista sa nazivima konceptata
   */
  public ArrayList<String> getConceptList()
  {
    ArrayList<String> loRetList = new ArrayList<String>(ioEntryMap.keySet());

    return loRetList;
  }

  /**
   * Metoda koja vraca listu sa konceptima
   * @return lista sa konceptima
   */
  public ArrayList<Concept> getList()
  {
    ArrayList<Concept> loRetList = new ArrayList<Concept>(ioEntryMap.size());
    Iterator loIterator = ioEntryMap.values().iterator();

    ConceptDictionaryEntry loCDE;
    while (loIterator.hasNext())
    {
      loCDE = (ConceptDictionaryEntry) loIterator.next();
      loRetList.add(loCDE.getConceptObject());
    }

    return loRetList;
  }
}

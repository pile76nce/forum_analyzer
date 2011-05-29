package rs.etf.analyzer.parser.tokens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import rs.etf.analyzer.util.Counter;
import java.util.HashMap;
import rs.etf.analyzer.parser.DictionaryEntry;

import rs.etf.analyzer.parser.ConceptNode;
import rs.etf.analyzer.util.ObjectCounterMap;
import rs.etf.analyzer.parser.Stemmer;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class ConceptMap extends HashMap
{
  private static final int INITIAL_SIZE = 2000;

  private Stemmer ioStemmer;

  public ConceptMap()
  {
    super(INITIAL_SIZE, (float) 1.5);

    ioStemmer = new Stemmer();
  }

  public void put(DictionaryEntry key)
  {
    int liTokenNum = 0;
//    String[] loConcept = key.getConcept().split("\\s");
   // List<String> loStemmList = ioStemmer.stemString(key.getConcept());
    String[] loConcept = ioStemmer.stemStringToArray(key.getConcept());//loStemmList.toArray(new String[loStemmList.size()]);
    ConceptNode loConceptNode = (ConceptNode)super.get(loConcept[liTokenNum]);

    while (true)
    {
      if (loConceptNode == null)
      {
//        System.out.println(loConcept[liTokenNum]);

        loConceptNode = new ConceptNode(loConcept[liTokenNum]);
        put(loConcept[liTokenNum], loConceptNode);
      }
      else
      {
        if (liTokenNum == loConcept.length)
          break;

        ConceptNode child = loConceptNode.findByConcept(loConcept[liTokenNum]);
        if ((loConceptNode.getConcept().equals(loConcept[liTokenNum]) == false) && child == null)
        {
//          System.out.print(' ' + loConcept[liTokenNum]);

          child = new ConceptNode(loConcept[liTokenNum]);
          loConceptNode.addChild(child);
        }
      }

      liTokenNum++;
    }
  }

  public ConceptNode get(DictionaryEntry key)
  {
    String[] loConcept = key.getConcept().split("\\s");
    ConceptNode loConceptNode = (ConceptNode) super.get(loConcept[0]);

    return loConceptNode;
  }

  public ConceptNode get(String asConcept)
  {
//    String[] as = asConcept.split("\\s");
    String[] as = ioStemmer.stemStringToArray(asConcept);
    ConceptNode loConceptNode = (ConceptNode) super.get(as[0]);

    return loConceptNode;
  }

  public List keysList()
  {
    Set keySet = keySet();
    List result = new ArrayList(keySet().size());
    result.addAll(keySet);
//    Collections.sort(result, new CounterComparator());
    return result;
  }


  public String toString()
  {
    return null;
  }

  public static void main(String[] args)
  {
    ConceptMap ioConceptMap = new ConceptMap();

    ioConceptMap.put(new DictionaryEntry("Representing of integers", "complement"));
    ioConceptMap.put(new DictionaryEntry("Representing of integers", "complement of 2"));
    ioConceptMap.put(new DictionaryEntry("Representing of integers", "complement of two"));

    ConceptNode cn = ioConceptMap.get("complement");
    System.out.println(cn);
  }
}

package rs.etf.analyzer.parser;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;


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
public class Category
{
  private final String isCategoryName;

  private ArrayList<ConceptDictionaryEntry> ioConceptList = new ArrayList<ConceptDictionaryEntry>();
  private List<ParseResult> ioResultList;

  private ArrayList<Position> ioPositionList = new ArrayList<Position>();

  private Hashtable<String, ParseResult> ioResultSet = new Hashtable<String, ParseResult>();

  private ConceptParser ioConceptParser;

  private String isText;

  public Category(final String asCategoryName)
  {
    isCategoryName = asCategoryName;
  }

  public Category(final String asCategoryName, final String asText)
  {
    isCategoryName = asCategoryName;
    isText = asText;
  }

  public void addPosition(Position aoPosition)
  {
    ioPositionList.add(aoPosition);
  }

  public void addPositionList(ArrayList<Position> aoPositionList)
  {
    ioPositionList.addAll(aoPositionList);
  }

  public void add(ConceptDictionaryEntry aoEntry)
  {
    ioConceptList.add(aoEntry);
  }

  public String getCategory()
  {
    return isCategoryName;
  }

  public ArrayList<ConceptDictionaryEntry> getConceptList()
  {
    return ioConceptList;
  }

  public ParseResult getParseResult(String asConcept)
  {
    return ioResultSet.get(asConcept);
  }

  public void parse(final String asText)
  {
    isText = asText;
//    ioConceptParser = new ConceptParser(ioConceptList);

    ioResultList = ioConceptParser.createResultList(isText);
    Collections.sort(ioResultList, Collections.reverseOrder());

    for (int i = 0; i < ioResultList.size(); i++)
    {
      String lsConcept = ioResultList.get(i).getConcept();

      if (ioResultSet.containsKey(lsConcept) == false)
        ioResultSet.put(lsConcept, ioResultList.get(i));
    }
  }

  public void getConcepts(final String asText)
  {
    isText = asText;
  }

  public void print()
  {
    for (int i = 0; i < ioResultList.size(); i++)
      System.out.println(ioResultList.get(i));
  }

  public List<ParseResult> getResultList()
  {
    return ioResultList;
  }

  public ArrayList<Position> getPositionList()
  {
    return ioPositionList;
  }

  public String toString()
  {
    return String.format("Category: %s", isCategoryName);
  }
}

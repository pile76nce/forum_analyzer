package rs.etf.analyzer.parser;

import java.util.List;
import java.util.ArrayList;

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
public class ConceptNode
{
  private int iiTreeDepth;

  private String isConcept;

  private List<ConceptNode> ioChildren;

//  public ConceptNode(DictionaryEntry aoEntry)
//  {
//    ioEntry = aoEntry;
//    iiTreeDepth = 1;
//
//    ioChildren = new ArrayList<ConceptNode>();
//  }

  public ConceptNode(final String asConcept)
  {
    isConcept = asConcept;
    iiTreeDepth = 1;

    ioChildren = new ArrayList<ConceptNode>();
  }

  public void addChild(ConceptNode aoConcept)
  {
    ioChildren.add(aoConcept);
    iiTreeDepth++;
  }

//  public void setEntry(DictionaryEntry aoEntry)
//  {
//    ioEntry = aoEntry;
//  }

//  public DictionaryEntry getEntry()
//  {
//    return ioEntry;
//  }

  public String getConcept()
  {
    return isConcept;
  }

  public ConceptNode findByConcept(final String asConcept)
  {
    String lsConcept;
    ConceptNode loConceptNode;

    for (int i = 0; i < ioChildren.size(); i++)
    {
      loConceptNode = ioChildren.get(i);
      lsConcept = loConceptNode.getConcept();

      if (lsConcept.equals(asConcept) == true)
        return loConceptNode;
    }

    return null;
  }

  public boolean findByStemmedConcept()
  {
//    String lsStemmedConcept;
//    for (int i = 0; i < ioChildren.size(); i++)
//    {
//      DictionaryEntry loEntry = ioChildren.get(i).getEntry();
//      lsStemmedConcept = loEntry.getConcept();
//
//      if (ioEntry.getConcept().equals(lsStemmedConcept) == true)
//        return true;
//    }

    return false;
  }

  public boolean hasChildren()
  {
    return (ioChildren.size() > 0);
  }

  public String toString()
  {
    return String.format("tree depth: %d", iiTreeDepth);
  }
}

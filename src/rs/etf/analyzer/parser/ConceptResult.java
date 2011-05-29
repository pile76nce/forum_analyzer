package rs.etf.analyzer.parser;

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
public class ConceptResult
{
  private String isConcept;
  private float ifScore;
  private float ifConceptRelevance;
  private float ifDocumentRelevance;
  private float ifDefaultRelevance;

  public ConceptResult(String asConcept, float afScore, float afConceptRelevance, float afDocumentRelevance, float afDefaultRelevance)
  {
    isConcept = asConcept;
    ifScore = afScore;
    ifConceptRelevance = afConceptRelevance;
    ifDocumentRelevance = afDocumentRelevance;
    ifDefaultRelevance = afDefaultRelevance;
  }
}

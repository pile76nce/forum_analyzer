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
public class Label
{
  private String isName;
  private int iiWeight;

  public Label(final String asName, final int aiWeight)
  {
    isName = asName;
    iiWeight = aiWeight;
  }

  public String getName()
  {
    return isName;
  }

  public int getWeight()
  {
    return iiWeight;
  }
}

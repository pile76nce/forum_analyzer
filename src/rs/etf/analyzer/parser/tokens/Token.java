package rs.etf.analyzer.parser.tokens;

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
public class Token
{
  private String isName;
  private Object ioValue;

  protected Token()
  {
  }

  public void setName(final String asName)
  {
    isName = asName;
  }

  public void setValue(final Object aoValue)
  {
    ioValue = aoValue;
  }

  public String getName()
  {
    return isName;
  }

  public Object getValue()
  {
    return ioValue;
  }
}

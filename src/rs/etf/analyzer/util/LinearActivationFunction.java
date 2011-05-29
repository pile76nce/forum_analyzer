package rs.etf.analyzer.util;

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
public class LinearActivationFunction implements ActivationFunction
{
  public double activation(double parameter)
  {
    return parameter;
  }

  public double deriv(double parameter)
  {
    return 1;
  }
}

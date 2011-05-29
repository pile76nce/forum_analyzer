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
public class LogSigActivationFunction implements ActivationFunction
{
  public double activation(double parameter)
  {
//    return 1.0 / (1.0 + Math.pow(Math.E, ( -1.0 * parameter)));
      return parameter/100.0;
  }

  public double deriv(double parameter)
  {
    // parameter = induced field
    // e == activation
    double e = 1.0 / (1.0 + Math.pow(Math.E, ( -1.0 * parameter)));
    return e * (1.0 - e);
  }

  public static void main(String[] args)
  {
    LogSigActivationFunction f = new LogSigActivationFunction();

    System.out.println(f.activation(0));
    System.out.println(f.activation(30.0/300));
    System.out.println(f.activation(45.0/300));

    System.out.println(f.activation(15.0/300 + f.activation(15.0/300) + f.activation(15.0/300)));
    System.out.println(f.activation(15.0/300 + f.activation(30.0/300)));

    System.out.println(f.deriv(15.0/100 + f.deriv(30.0/100)));
  }
}

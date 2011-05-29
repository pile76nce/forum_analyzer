package rs.etf.analyzer.util;

/**
 * Klasa <code>SemanticDistance</code> služi za izračunavanje semantičkog rastojanja
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class SemanticDistance extends WeightedEditDistance
{
  public SemanticDistance()
  {
  }

  public double matchWeight(char cMatched)
  {
    return 0.0;
  }

  public double deleteWeight(char c)
  {
    return (Character.isLetter(c) || Character.isDigit(c)) ? -1 : 0;
  }

  public double insertWeight(char c)
  {
    return deleteWeight(c);
  }

  public double substituteWeight(char cDeleted, char cInserted)
  {
    return (Character.toLowerCase(cDeleted) == Character.toLowerCase(cInserted)) ? 0 : -1;
  }

  public double transposeWeight(char cFirst, char cSecond)
  {
    return Double.NEGATIVE_INFINITY;
  }

  public double distance(CharSequence csIn, CharSequence csOut)
  {
    String lsIn = (String)csIn;
    String lsOut = (String)csOut;
    int sign = 0;
    int liIndex = lsIn.indexOf(lsOut);
    if (csOut.length() > 0 && csIn.length() > csOut.length())
      sign = (liIndex == 0 ? 1 : 0);

    return sign * super.distance(csIn, csOut);
  }
}

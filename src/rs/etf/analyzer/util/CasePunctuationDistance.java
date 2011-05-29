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
public class CasePunctuationDistance extends WeightedEditDistance
{
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

  public double matchWeight(char cMatched)
  {
    return 0;
  }

  public double transposeWeight(char cFirst, char cSecond)
  {
    return Double.NEGATIVE_INFINITY;
  }

  public static void main(String[] args) {
        Distance<CharSequence> d = new CasePunctuationDistance();
        String s1 = "proces";
        String s2 = "procesor";

        System.out.printf("%12s  %12s  %5.1f\n",
                                  s1,s2, d.distance(s1, s2));

//        for (String s1 : args)
//            for (String s2 : args)
//                System.out.printf("%12s  %12s  %5.1f\n",
//                                  s1,s2, d.distance(s1,s2));
    }
}

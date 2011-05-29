package rs.etf.analyzer.test.parser;

import rs.etf.analyzer.test.BaseTestCase;
import rs.etf.analyzer.util.Distance;
import rs.etf.analyzer.util.CasePunctuationDistance;
import rs.etf.analyzer.util.FixedWeightEditDistance;
import rs.etf.analyzer.util.SemanticDistance;

/**
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class SemanticDistanceTest extends BaseTestCase
{
  public void testOne()
  {
    Distance<CharSequence> casePunctuationDistance = new CasePunctuationDistance();
    Distance<CharSequence> fixedWeightEditDistance = new FixedWeightEditDistance();
    Distance<CharSequence> semanticDistance = new SemanticDistance();
    String s1 = "procesni";
    String s2 = "proces";

    System.out.println(String.format("%s: %12s  %12s  %5.1f\n", "CasePunctuationDistance", s1, s2, casePunctuationDistance.distance(s1, s2)));
    System.out.println(String.format("%s: %12s  %12s  %5.1f\n", "FixedWeightEditDistance", s1, s2, fixedWeightEditDistance.distance(s1, s2)));
    System.out.println(String.format("%s: %12s  %12s  %5.1f\n", "SemanticDistance", s1, s2, semanticDistance.distance(s1, s2)));

    double ldDistance = semanticDistance.distance(s1, s2);
    assertEquals(ldDistance, 2.0);
  }
}

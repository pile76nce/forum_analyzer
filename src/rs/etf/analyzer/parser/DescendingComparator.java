package rs.etf.analyzer.parser;

import java.util.Comparator;

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
public class DescendingComparator implements Comparator
{
  public int compare(Object obj1, Object obj2)
  {
    return 0;

//    return obj1.score() > obj2.score();
  //        ? 1
  //        : (obj1.score() < obj2.score()
  //           ? -1
  //           : 0);
  }
}

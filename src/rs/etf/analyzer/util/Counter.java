package rs.etf.analyzer.util;

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
public class Counter extends Number
{
  private int iiCounter;

  public Counter()
  {
  }

  public Counter(int aiCounter)
  {
    iiCounter = aiCounter;
  }

  public int value()
  {
    return iiCounter;
  }

  public void increment()
  {
    iiCounter++;
  }

  public void increment(int n)
  {
    iiCounter += n;
  }

  public void set(int aiCount)
  {
    iiCounter = aiCount;
  }

  public double doubleValue()
  {
    return (double) iiCounter;
  }

  public float floatValue()
  {
    return (float) iiCounter;
  }

  public int intValue()
  {
    return iiCounter;
  }

  public long longValue()
  {
    return (long) iiCounter;
  }

  public String toString()
  {
    return String.valueOf(value());
  }
}

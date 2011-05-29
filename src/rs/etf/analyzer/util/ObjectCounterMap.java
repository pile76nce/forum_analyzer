package rs.etf.analyzer.util;

import java.util.HashMap;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * Klasa <code>ObjectCounterMap</code> nasleđuje HashMapu broj pojavljivanja svakog objekata i
 * daje mogućnost sortiranja
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class ObjectCounterMap extends HashMap
{
  private static final int INITIAL_SIZE = 2000;

  public ObjectCounterMap()
  {
    super(INITIAL_SIZE, (float)1.5);
  }

  /**
   * Metoda koja za dati objekat povećava brojač za 1
   * @param key Objekat
   */
  public void increment(Object aoKey)
  {
    increment(aoKey, 1);
  }

  /**
   * Metoda koja za dati objekat povećava brojač za određenu vrednost
   * @param key Objekat
   * @param value Vrednost za koliko se povećava brojač
   */
  public void increment(Object aoKey, int aiValue)
  {
    if (!containsKey(aoKey))
    {
      put(aoKey, new Counter(aiValue));
      return;
    }

    Counter counter = (Counter) get(aoKey);
    counter.increment(aiValue);

    if (counter.value() == 0)
      remove(aoKey);
  }

  public int getCount(Object aoKey)
  {
    if (!containsKey(aoKey))
      return 0;
    else
      return ((Counter)get(aoKey)).value();
  }

  public List keysOrderedByCountList()
  {
    Set keySet = keySet();
    List result = new ArrayList(keySet().size());
    result.addAll(keySet);
    Collections.sort(result, new CounterComparator());

    return result;
  }

  /**
   * Metoda koja daje skup objekata sortiranih po njihovom brojaču
   * @return Skup objekata sortiranih po brojaču
   */
  public Object[] keysOrderedByCount()
  {
    return keysOrderedByCountList().toArray();
  }

  public String toString()
  {
    return null;
  }

  class CounterComparator implements Comparator
  {
    public int compare(Object o1, Object o2)
    {
      int count1 = getCount(o1);
      int count2 = getCount(o2);

      if (count1 < count2)
        return 1;
      if (count1 > count2)
        return -1;

      return 0;
    }
  }
}

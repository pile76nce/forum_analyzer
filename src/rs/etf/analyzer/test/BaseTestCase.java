package rs.etf.analyzer.test;

import junit.framework.TestCase;

/**
 * Klasa koja nasleđuje <code>TestCase</code> i omogućava nekoliko dodatnih metoda za potvrđivanje.
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class BaseTestCase extends TestCase
{
  private static String EMPTY_MSG = "";

  /**
   * Kreiranje osnovnog test case.
   */
  public BaseTestCase()
  {
      super();
  }

  /**
   * Kreiranje osnovnog test case sa određenim nazivom.
   *
   * @param asName Naziv za test case.
   */
  public BaseTestCase(String asName)
  {
      super(asName);
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(Object[] aoXs, Object[] aoYs)
  {
      assertEqualsArray("", aoXs, aoYs);
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param asMsg Povratna poruka u skučaju da test nije prošao.
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(String asMsg, Object[] aoXs, Object[] aoYs)
  {
      assertNotNull("Null za prvi niz. " + asMsg, aoXs);
      assertNotNull("Null za drugi niz. " + asMsg, aoYs);
      for (int i = 0; i < aoXs.length && i < aoYs.length; ++i)
      {
          if (aoXs[i] == null && aoYs[i] == null) continue;
          if (aoXs[i] == null || aoYs[i] == null)
          {
              fail(" Element aoXs[" + i + "]=" + aoXs[i] + " != aoYs[" + i + "]=" + aoYs[i]);
          }
          else if (!aoXs[i].equals(aoYs[i]))
          {
              fail("aoXs[" + i + "]==" + aoXs[i] + " != aoYs[" + i + "]=" + aoYs[i]);
          }
      }
      if (aoXs.length != aoYs.length)
      {
          fail("aoXs.length=" + aoXs.length + " != aoYs.length=" + aoYs.length);
      }
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(int[] aoXs, int[] aoYs)
  {
      assertEqualsArray("", aoXs, aoYs);
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param asMsg Povratna poruka u skučaju da test nije prošao.
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(String asMsg, int[] aoXs, int[] aoYs)
  {
      assertNotNull("Null first array. " + asMsg, aoXs);
      assertNotNull("Null second array. " + asMsg, aoYs);
      assertEquals("Nizovi imaju razlicite duzine. " + asMsg, aoXs.length, aoYs.length);
      for (int i = 0; i < aoXs.length; ++i)
      {
          assertEquals("Element " + i + " differs. " + asMsg, aoXs[i], aoYs[i]);
      }
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(char[] aoXs, char[] aoYs)
  {
      assertEqualsArray("", aoXs, aoYs);
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param asMsg Povratna poruka u skučaju da test nije prošao.
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(String asMsg, char[] aoXs, char[] aoYs)
  {
      assertNotNull("Null za prvi niz. " + asMsg, aoXs);
      assertNotNull("Null za drugi niz. " + asMsg, aoYs);
      assertEquals("Nizovi imaju razlicite duzine. " + asMsg, aoXs.length, aoYs.length);
      for (int i = 0; i < aoXs.length; ++i)
      {
          assertEquals("Element " + i  + " se razlikuje. " + asMsg, aoXs[i], aoYs[i]);
      }
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(byte[] aoXs, byte[] aoYs)
  {
      assertEqualsArray("", aoXs, aoYs);
  }

  /**
   * Testiranje da li su dva navedena niza razlicita od null,
   * iste duzine, i elementi na istim indeksima isti
   *
   * @param asMsg Povratna poruka u skučaju da test nije prošao.
   * @param aoXs Prvi niz za testiranje.
   * @param aoYs Drugi niz za testiranje.
   */
  protected void assertEqualsArray(String asMsg, byte[] aoXs, byte[] aoYs)
  {
      assertNotNull("Null za prvi niz. " + asMsg, aoXs);
      assertNotNull("Null za drugi niz. " + asMsg, aoYs);
      assertEquals("Nizovi imaju razlicite duzine. " + asMsg, aoXs.length,aoYs.length);
      for (int i = 0; i < aoXs.length; ++i)
      {
          assertEquals("Element " + i  + " se razlikuje. " + asMsg, aoXs[i],aoYs[i]);
      }
  }
}

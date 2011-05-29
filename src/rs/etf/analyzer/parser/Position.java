package rs.etf.analyzer.parser;

/**
 * Klasa <code>Position</code> za predsta tokena u recnisadrzi id, pocetnu poziciju i duzinu tokena
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class Position implements Comparable
{
  private int iiTokenId;
  private int iiStart;
  private int iiLength;

  private String isToken;

  public Position(final String asToken, final int aiTokenId, final int aiStart, final int aiLength)
  {
    isToken = asToken;

    iiTokenId = aiTokenId;
    iiStart = aiStart;
    iiLength = aiLength;
  }

  public String getToken()
  {
    return isToken;
  }

  public int getTokenId()
  {
    return iiTokenId;
  }

  public int getStart()
  {
    return iiStart;
  }

  public int getLength()
  {
    return iiLength;
  }

  public int compareTo(Object aoPosition)
  {
    try
    {
      Position p = (Position) aoPosition;

      if (iiTokenId > p.getTokenId())
        return 1;
      else
        if (iiTokenId < p.getTokenId())
          return -1;

      return 0;
    }
    catch (Exception e)
    {
      return 0;
    }
  }

  public String toString()
  {
    return String.format("token: %s, id: %d, start: %d, length: %d", isToken, iiTokenId, iiStart, iiLength);
  }
}

package rs.etf.analyzer.parser;

import java.util.ArrayList;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Apstraktna klasa</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: etf</p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public abstract class AbstractDictionaryEntry
{
  /**
   * Instanca klase koja odredjeni token svod na osnovni oblik
   */
  protected static Stemmer ioStemmer = new Stemmer();

  /**
   * Originalni naziv tokena
   */
  protected String isToken;

  /**
   * Pocetna pozicija
   */
  protected int iiStart;

  protected ArrayList<Position> ioPositionList = new ArrayList<Position>();

  protected AbstractDictionaryEntry()
  {
  }

  public String getToken()
  {
    return isToken;
  }

  /**
   * Metoda koja vraca listu sa pozicijama
   * @return Lista sa pozicijama
   */
  public ArrayList<Position> getPositionList()
  {
    return ioPositionList;
  }

  /**
   * Metoda koja vraca velicinu liste
   * @return Velicina liste
   */
  public int getListSize()
  {
    return ioPositionList.size();
  }
}

package rs.etf.analyzer.parser;

import java.util.HashMap;
import java.util.ArrayList;

/**
 *
 * Klasa <code>ObjectPositionMap</code> nasledjuje HashMap i sadrzi listu sa
 * pozicijama pojavljivanja nekog objekta
 *
 * @author Dejan Prodanovic
 * @version 1.0
 */
public class ObjectPositionMap extends HashMap
{
  private static final int INITIAL_SIZE = 2000;
  private static final float GROW_FACTOR = (float) 1.5;

  public ObjectPositionMap()
  {
    super(INITIAL_SIZE, GROW_FACTOR);
  }

  /**
   * Metoda koja dodaje novu poziciju u listu pozicija za dati objekat
   * @param o Objekat
   * @param p Pozicija objekta
   */
  public void put(Object o, Position p)
  {
    ArrayList<Position> loPositionList = null;

    if (!containsKey(o))
      loPositionList = new ArrayList<Position>();
    else
      loPositionList = (ArrayList<Position>)super.get(o);

    loPositionList.add(p);

    put(o, loPositionList);
  }

  /**
   * Metoda koja vraca listu sa pozicijama za dati objekat
   * @param o Objekat
   * @return Lista pozicija objekta
   */
  public ArrayList<Position> get(Object o)
  {
    ArrayList<Position> loPositionList = null;

    if (containsKey(o))
      loPositionList = (ArrayList<Position>)super.get(o);

    return loPositionList;
  }
}

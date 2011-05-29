package rs.etf.analyzer.parser;

import java.util.Map;
import java.util.List;
import java.sql.Connection;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 * Klasa <code>RedWordMapper</code> sluzi za upis procitanih reci u bazu,
 * gde se za svaku rec vodi evidencija o ukupnom broju pojavljivanja
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class RedWordMapper extends AbstractMapper
{
  private Map<String, RedWordObject> ioMap;
  private List<RedWordObject> ioNewList;

  private final int WORD_INDEX = 2;
  private final int COUNT_INDEX = 5;

  /**
   * Konstruktor klase
   * @throws Exception
   */
  public RedWordMapper() throws Exception
  {
    super();
  }

  /**
   * Metoda koja cita sve zapise iz tabele procitanih reci i pravi odgovarajucu HashMap-u
   * gde je kljuc rec, a vrednost instanca klasa <code>RedWordObject</code> koja reprezentuje
   * zapis iz tabele procitanih reci
   * @throws Exception
   */
  public void doLoad() throws Exception
  {
    ioPreparedStatement = ioConnection.prepareStatement("select * from ProcitanaRec");

    ResultSet rs = ioPreparedStatement.executeQuery();

    ioMap = new HashMap<String, RedWordObject>();

    RedWordObject rwo = null;

    String lsWord;
    int liCount;

    while (rs.next() == true)
    {
      lsWord = rs.getString(WORD_INDEX);
      liCount = rs.getInt(COUNT_INDEX);

      rwo = RedWordObject.createInstance(lsWord, liCount);
      ioMap.put(rwo.getWord(), rwo);
    }
  }

  /**
   * Metoda koja vraca zapis iz tabele procitanih reci u objektnom modelu
   * @param Rec
   * @return Zapis iz tabele procitanih reci u objektnom modelu
   */
  public RedWordObject findBy(final String asWord)
  {
    return ioMap.get(asWord);
  }

  /**
   * Metoda u kojoj se vrsi izmena zapisa iz tabele procitanih reci
   * i u objektnom modelu i u bazi
   * @param Zapis iz tabele procitanih reci u objektnom modelu
   * @param Broj pojavljivanja reci
   * @throws Exception
   */
  public void update(final RedWordObject aoRWO, final int aiCount) throws Exception
  {
    aoRWO.updateCount(aiCount);
    ioMap.put(aoRWO.getWord(), aoRWO);

    PreparedStatement loPS = ioConnection.prepareStatement("update ProcitanaRec set BrojPojavljivanja = ? where Rec = ?");
    loPS.setInt(1, aoRWO.getCount());
    loPS.setString(2, aoRWO.getWord());

    loPS.executeUpdate();
  }

  /**
   * Metoda u kojoj se vrsi upis novog zapisa iz tabele procitanih reci
   * i u objektnom model i u bazu
   * @param Zapis iz tabele procitanih reci u objektnom modelu
   * @throws Exception
   */
  public void insert(final RedWordObject aoRWO) throws Exception
  {
    ioMap.put(aoRWO.getWord(), aoRWO);

    PreparedStatement loPS = ioConnection.prepareStatement("insert into ProcitanaRec(Rec, BrojPojavljivanja) values (?, ?)");
    loPS.setString(1, aoRWO.getWord());
    loPS.setInt(2, aoRWO.getCount());

    loPS.executeUpdate();
  }
}

package rs.etf.analyzer.config;

import java.io.IOException;
import java.io.FileInputStream;
import rs.etf.util.config.Configuration;
import org.apache.log4j.PropertyConfigurator;

/**
 * Klasa <code>ConfigGeneral</code> služi za otvaranje konfiguracione datoteke
 * i pripremu za dalju obradu
 *
 * @author  Dejan Prodanovi\u0107
 * @version 1.0
 */
public abstract class ConfigGeneral
{
  protected Configuration ioConfig = null;

  private String isIniFile = null;

  /**
   * Konstruktor klase
   * @param asIniFile String
   * @param abLog ako je true datoteka sadrži sekciju za log
   */
  protected ConfigGeneral(final String asIniFile, final boolean abLog)
  {
    isIniFile = asIniFile;
    ioConfig = new Configuration();
    try
    {
      ioConfig.load(new FileInputStream(isIniFile));
    }
    catch (IOException ex)
    {
    }

    if (abLog)
    {
//      parsiranje konfiguracije logera definisane u config.ini
      PropertyConfigurator.configure(asIniFile);
    }
  }

  /**
   * Glavna metoda za čitanje parametara
   * @throws Exception Ako neki od parametara nije ispravno naveden
   */
  protected abstract void parseConfig() throws Exception;
}

package rs.etf.analyzer.config;

import org.apache.log4j.Logger;

import rs.etf.util.config.Configuration;

/**
 * Klasa <code>ConfigAnalzyerGeneral</code> služi za čitanje paramatara definisanih u
 * konfiguracionoj datoteci
 *
 * @author  Dejan Prodanovi\u0107
 * @version 1.0
 */
public class ConfigAnalyzerGeneral extends ConfigGeneral
{
  private String[] ioSubjectArray = null;

  protected String isXmlPath = null;
  protected String isOntologyPath = null;

  protected static final Logger ioLog = Logger.getLogger(ConfigAnalyzerGeneral.class);

  /**
   * Konstruktor klase
   */
  protected ConfigAnalyzerGeneral(final String asIniFile)
  {
    super(asIniFile, true);
  }

  /**
   * Metoda za parsiranje sekcija definisanih u ini datoteci
   */
  protected void parseConfig() throws Exception
  {
    ioSubjectArray = ioConfig.getStringArray("rs.etf.analyzer.subject.AOR.xml");
    isXmlPath = ioConfig.getString("rs.etf.analyzer.subject.AOR.xml");
    isOntologyPath = ioConfig.getString("rs.etf.analyzer.subject.AOR.ontology");
  }
}

package rs.etf.analyzer.config;

/**
 * Klasa <code>ConfigAnalzyerFile</code> je realizovana kao unikat. Služi za dohvatanje
 * parametara pročitanih iz datoteke konfiguracije
 *
 * @author  Dejan Prodanovi\u0107
 * @version 1.0
 */
public class ConfigAnalyzerFile extends ConfigAnalyzerGeneral
{
  private static ConfigAnalyzerFile instance = new ConfigAnalyzerFile();

  private String CONFIG_FILE = "./config/config.ini";

  private ConfigAnalyzerFile()
  {
    super("./config/config.ini");

    try
    {
      parseConfig();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public static ConfigAnalyzerFile getInstance()
  {
    return instance;
  }

  public String getXmlPath()
  {
    return isXmlPath;
  }

  public String getOntologyPath()
  {
    return isOntologyPath;
  }
}

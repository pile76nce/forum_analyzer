package rs.etf.analyzer.test.config;

import rs.etf.analyzer.test.BaseTestCase;
import rs.etf.analyzer.config.ConfigAnalyzerFile;

/**
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class ConfigTest extends BaseTestCase
{
  public void testOne()
  {
    final String ONTOLOGY_PATH = "./ontology/AOR.owl";

    ConfigAnalyzerFile loConfig = ConfigAnalyzerFile.getInstance();
    String lsXmlPath = loConfig.getXmlPath();
    String lsOntologyPath = loConfig.getOntologyPath();

    System.out.println(String.format("ontology path: %s", lsOntologyPath));

    assertEquals(lsOntologyPath, ONTOLOGY_PATH);
  }
}

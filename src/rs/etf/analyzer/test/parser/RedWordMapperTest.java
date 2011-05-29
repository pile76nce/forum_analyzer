package rs.etf.analyzer.test.parser;

import rs.etf.analyzer.test.BaseTestCase;
import rs.etf.analyzer.parser.RedWordMapper;

/**
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class RedWordMapperTest extends BaseTestCase
{
  public void testOne()
  {
    try
    {
      RedWordMapper ioMapper = new RedWordMapper();
      ioMapper.doLoad();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
}

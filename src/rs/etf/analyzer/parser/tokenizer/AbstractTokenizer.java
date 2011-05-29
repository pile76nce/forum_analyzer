package rs.etf.analyzer.parser.tokenizer;

import java.io.File;
import java.io.Serializable;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public abstract class AbstractTokenizer implements Serializable
{
  private final int BLOCK_SIZE = 1024;

  private File ioFile = null;
  private String isFileName = null;

  private final byte[] ioBlock = new byte[BLOCK_SIZE];

  public AbstractTokenizer()
  {
  }

  public abstract void getTokens() throws Exception;
}

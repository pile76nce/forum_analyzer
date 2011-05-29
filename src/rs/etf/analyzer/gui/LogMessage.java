package rs.etf.analyzer.gui;

public interface LogMessage
{
  public static final String INFO      = "INFO";
  public static final String WARNING   = "WARNING";
  public static final String EXCEPTION = "EXCEPTION";
  public static final String ERROR     = "ERROR";

  public static final String CONNECTED = "connected";
  public static final String NOT_CONNECTED = "not connected";
  public static final String NOT_CONNECTED_ERROR = "Not connected to database";
  public static final String PARAMETER_ERROR = "Parameter number does not match " +
                                               "with number of parameters in query";
  public static final String TIMESTAMP_FORMAT = "dd.MM.yy-HH:mm:ss:S";

  public static final String MESSAGE_FORMAT = "{0} - {1}: {2}";

  public static final String CRLF = "\r\n";
}

package rs.etf.analyzer.parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
public class AbstractMapper
{
  protected Connection ioConnection;
  protected Statement ioStatement;

  protected PreparedStatement ioPreparedStatement;

  public AbstractMapper() throws Exception
  {
    loadDriver();
  }

  protected void loadDriver() throws ClassNotFoundException, SQLException
  {
    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    ioConnection = DriverManager.getConnection("jdbc:odbc:dictionary");
  }

  public Connection getConnection1() throws SQLException
  {
    Connection loConnection = DriverManager.getConnection("jdbc:odbc:dictionary");

    return loConnection;
  }

  public void closeConncetion()
  {
  }
}

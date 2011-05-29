package rs.etf.util.db;

import java.sql.DatabaseMetaData;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class DbManager
{
  /**
   * Konekcija na bazu podataka
   */
  protected Connection ioConn;

  /**
   * Naziv konekcije. Trebalo bi da bude jedinstven na nivou jedne konfiguracije
   */
  protected String isName;

  protected String isDriver;
  protected String isURL;
  protected String isUName;
  protected String isPass;
  protected int resultSetType;
  protected int resultSetConcur;

  protected DatabaseMetaData ioDBMetaData;

  protected static Logger ioLog = Logger.getLogger(DbManager.class);

  public DbManager(String asName)
  {
    if (asName != null || asName.length() > 0)
    {
      isName = asName;
    }
    else
    {
      // Upozorenje da je ime generisano!
      isName = String.valueOf(new Date().getTime());
//      addWarn(new Exception("Null ime ! Generisano ime je " + isName + " !"), ioLog);
    }
  }

  private void clear()
  {
    ioConn = null;
    isDriver = null;
    isURL = null;
    isUName = null;
    isPass = null;
    ioDBMetaData = null;
    resultSetType = ResultSet.TYPE_FORWARD_ONLY;
    resultSetConcur = ResultSet.CONCUR_READ_ONLY;
    System.gc();
  }

  public String getName()
  {
    if (isName != null && isName.length() > 0)
    {
      return isName;
    }
    else
    {
//            addWarn ( new Exception ( "Ime konkecije je nevalidno!" ), ioLog ) ;
      return "";
    }
  }

  public void connect()
  {

  }

  public void reconnect()
  {

  }

  public void disconnect()
  {

  }

  public void executeSql(String sql)
  {

  }

  public void executeSql(String sql, int ai_ResultSetType, int ai_ResultSetConcurency)
  {

  }

  public boolean isConnected()
  {
    try
    {
      return (ioConn != null && !ioConn.isClosed());
    }
    catch (SQLException sqle)
    {
      return false;
    }
  }

  public int setAutoCommit(boolean abSwitch)
  {
    if (ioConn != null)
    {
      try
      {
        ioConn.setAutoCommit(abSwitch);
        return 1;
      }
      catch (SQLException ex)
      {
//        addError(ex, ioLog);
        return -1;
      }
    }
    else
    {
//      addWarn(new Exception("Konekcija nije uspostavljanje!"), ioLog);
      return 0;
    }
  }

  public int commit()
  {
    try
    {
      if (ioConn != null && !ioConn.isClosed())
      {
        ioConn.commit();
        return 1;
      }
      else
      {
//        addWarn(new Exception("Konekcija nije uspostavljanje!"), ioLog);
        return 0;
      }
    }
    catch (SQLException ex)
    {
//      addError(ex, ioLog);
      return -1;
    }
  }

  public int rollback()
  {
    try
    {
      if (ioConn != null && !ioConn.isClosed())
      {
        ioConn.rollback();
        return 1;
      }
      else
      {
//        addWarn(new Exception("Konekcija nije uspostavljanje!"), ioLog);
        return 0;
      }
    }
    catch (SQLException ex)
    {
//      addError(ex, ioLog);
      return -1;
    }
  }

  public String toString()
  {
    String lsRes = "";
    if (isName != null && isName.length() > 0) lsRes += "NAME(" + isName + ")";
    else lsRes += "NAME()";
    if (isDriver != null && isDriver.length() > 0) lsRes += " DRIVER(" +
        isDriver + ")";
    else lsRes += " DRIVER()";
    if (isURL != null && isURL.length() > 0) lsRes += " URL(" + isURL + ")";
    else lsRes += " URL()";
    if (ioDBMetaData != null)
    {
      try
      {
        lsRes += "   PROPS( " +
            " DRV:" + ioDBMetaData.getDriverVersion() + "    " +
            " DB:" + ioDBMetaData.getDatabaseProductVersion() + ")";
      }
      catch (SQLException sqle)
      {
        lsRes += "  PROPS()";
      }
    }
    else
    {
      lsRes += "PROPS()";
    }
    return lsRes;

  }

  public DbManager clone()
  {
    DbManager loConn = new DbManager(isName);
//        loConn.connect(isDriver,isURL,isUName,isPass) ;

    return loConn;
  }
}

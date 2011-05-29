package rs.etf.analyzer.main;

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
public class Project implements Serializable
{
  public static final String EXT = "proj";

  /**
   *
   */
  private String isProjectName;

  /**
   *
   */
  private String isIniFile;

  private static Project ioInstance = new Project();

  private Project()
  {
  }

  public static void setInstance(Project aoInstance)
  {
    ioInstance = aoInstance;
  }

  public static Project getInstance()
  {
    return ioInstance;
  }

  public void setProjectName(final String asProjectName)
  {
    isProjectName = asProjectName;
  }

  public String getProjectName()
  {
    return isProjectName;
  }

  public String getIniFile()
  {
    return isIniFile;
  }

  public String toString()
  {
    return String.format("project: %s; ini file: %s", isProjectName, isIniFile);
  }
}

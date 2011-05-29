package rs.etf.analyzer.main;

import javax.swing.filechooser.FileFilter;
import java.io.File;

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
public class ProjectFilter extends FileFilter
{
  private String isFileFilter;

  public ProjectFilter(final String asFileFilter)
  {
    isFileFilter = asFileFilter;
  }

  public boolean accept(File aoFile)
  {
    final String lsTemp = aoFile.getName();
    return lsTemp.endsWith(isFileFilter);
  }

  public String getDescription()
  {
    return String.format("*.%s", isFileFilter);
  }
}

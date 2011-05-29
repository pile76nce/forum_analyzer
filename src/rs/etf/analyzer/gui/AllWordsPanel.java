package rs.etf.analyzer.gui;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import rs.etf.analyzer.util.ObjectCounterMap;
import org.jdesktop.swingx.JXTable;
import rs.etf.analyzer.parser.Stemmer;

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
public class AllWordsPanel extends ResultPanel
{
  private final Object[] HEADER = {"Word", "Stemmed Word", "Count"};

  private JXTable ioAllWordsTable;
  private DefaultTableModel ioModel;

  private JScrollPane ioScrollPane;

  private Stemmer ioStemmer;

  public AllWordsPanel(MainFrame aoParentFrame)
  {
    super(aoParentFrame);

    try
    {
      jbInit();
    }
    catch (Exception ex)
    {
    }
  }

  private void jbInit() throws Exception
  {
    ioStemmer = new Stemmer();

    ioModel = new DefaultTableModel(){
      public boolean isCellEditable(int a, int b)
      {
        return false;
      }
    };

    ioAllWordsTable = new JXTable(ioModel);

    ioScrollPane = new JScrollPane(ioAllWordsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    add(ioScrollPane, java.awt.BorderLayout.CENTER);
  }

  private void clearTable()
  {

  }

  public void setData(ObjectCounterMap aoData)
  {
    List loList = aoData.keysOrderedByCountList();
    Object[][] loData = new Object[aoData.size()][3];
    String lsWord;

    for (int i = 0; i < loList.size(); i++)
    {
      lsWord = (String)loList.get(i);
      loData[i][0] = lsWord;
      loData[i][1] = ioStemmer.stemOneWord(lsWord);
      loData[i][2] = aoData.getCount(lsWord);
      ioModel.setDataVector(loData, HEADER);
    }
  }
}

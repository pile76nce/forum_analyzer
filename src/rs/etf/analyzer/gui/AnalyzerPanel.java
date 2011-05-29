package rs.etf.analyzer.gui;

import javax.swing.JScrollPane;
import rs.etf.analyzer.parser.ParseResult;
import java.util.List;
import rs.etf.analyzer.parser.Category;
import rs.etf.analyzer.parser.Position;
import java.util.ArrayList;
import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.ConceptDictionaryEntry;

/**
 *
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Panel za prikaz rezultata</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi\u0107
 * @version 1.0
 */
public class AnalyzerPanel extends ResultPanel
{
  private AnalyzerTreeTable ioAnalyzerTreeTable;
  private AnalyzerTreeTableModel ioAnalyzerTreeTableModel;

  private JScrollPane ioScrollPane;

  public AnalyzerPanel(MainFrame aoParentFrame)
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
    ioAnalyzerTreeTable = new AnalyzerTreeTable(ioParentFrame);
    ioAnalyzerTreeTableModel = ioAnalyzerTreeTable.getAnalyzerModel();

    ioScrollPane = new JScrollPane(ioAnalyzerTreeTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    add(ioScrollPane, java.awt.BorderLayout.CENTER);
  }

  public void load(List<List<ParseResult>> aoResultList)
  {
    List<ParseResult> lo;
    ParseResult pr;
    ItemNode node;
    ItemNode node1;

    for (int i = 0; i < aoResultList.size(); i++)
    {
      lo = aoResultList.get(i);
      node = new ItemNode("pile");
      ioAnalyzerTreeTableModel.addRecord(node);

      for (int j = 0; j < lo.size(); j++)
      {
        pr = lo.get(j);
        node1 = new ItemNode(pr.getConcept());
        ioAnalyzerTreeTableModel.addRecord(node, node1);
      }
    }
  }

  public void load(Category aoCategory)
  {
    Category loCategory = aoCategory;

    ItemNode node = new ItemNode(loCategory.getCategory());
    ioAnalyzerTreeTableModel.addRecord(node);

    List<ParseResult> pr = loCategory.getResultList();
    List<Position> loPositionList = loCategory.getPositionList();

    ItemNode node1;

    Position loTemp;
    for (int i = 0; i < loPositionList.size(); i++)
    {
      loTemp = loPositionList.get(i);
      node1 = new ItemNode(loTemp, loPositionList.size());
      ioAnalyzerTreeTableModel.addRecord(node, node1);
    }
  }

  public void load(Category aoCategory, ConceptDictionary aoConceptDictionary)
  {
    Category loCategory = aoCategory;
    ConceptDictionary ioConceptDictionary = aoConceptDictionary;

    ItemNode node = new ItemNode(loCategory.getCategory());
    ioAnalyzerTreeTableModel.addRecord(node);

//    List<ParseResult> pr = loCategory.getResultList();
    ArrayList<ConceptDictionaryEntry> loConceptList = loCategory.getConceptList();

    List<Position> loPositionList = loCategory.getPositionList();
    ConceptDictionaryEntry loCDE;

    ItemNode node1;
    Position loTemp;

    for (int i = 0; i < loConceptList.size(); i++)
    {
      loCDE = ioConceptDictionary.getEntry(loConceptList.get(i).getConcept());

      if (loCDE == null)
        System.out.println(loConceptList.get(i).getStemmedConcept());

      loPositionList = loCDE.get();

      node1 = new ItemNode(loConceptList.get(i).getStemmedConcept(), String.valueOf(loPositionList.size()));
      ioAnalyzerTreeTableModel.addRecord(node, node1);
    }
//
//
//    for (int i = 0; i < loPositionList.size(); i++)
//    {
//      loTemp = loPositionList.get(i);
//      node1 = new ItemNode(loTemp, loPositionList.size());
//
//    }
  }
}

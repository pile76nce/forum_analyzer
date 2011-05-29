package rs.etf.analyzer.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import rs.etf.analyzer.parser.CategoryParser;
import java.util.List;
import rs.etf.analyzer.parser.Chapter;
import rs.etf.analyzer.parser.Category;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import rs.etf.analyzer.parser.AllWordsParser;
import rs.etf.analyzer.util.ObjectCounterMap;
import rs.etf.analyzer.parser.ParseResult;
import rs.etf.analyzer.parser.ConceptParser;
import java.util.Hashtable;
import java.util.Iterator;
import rs.etf.analyzer.parser.DictionaryEntry;
import rs.etf.analyzer.parser.tokens.TextTokenizer;
import rs.etf.analyzer.parser.StemmedTokenDictionary;
import java.util.ArrayList;
import rs.etf.analyzer.parser.ConfigParser;
import java.util.Collections;
import rs.etf.analyzer.parser.ConceptDictionaryEntry;
import rs.etf.analyzer.parser.Position;
import rs.etf.analyzer.parser.ConceptDictionary;
import rs.etf.analyzer.parser.StemmedTokenDictionaryEntry;
import java.io.FileWriter;
import java.io.BufferedWriter;
import rs.etf.analyzer.parser.Stemmer;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Glavni panel </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class MainPanel extends JPanel
{
  private JSplitPane ioSplitPane = new JSplitPane();

  private ResultPanel ioResultPanel;
  private TextEditPanel ioTextEditPanel;

  private MainFrame ioParentFrame;

  private BorderLayout borderLayout1 = new BorderLayout();

  private String isText;
  private String isTitle;

  private Hashtable<String, Category> ioCategoryDictionary;
  private ConceptDictionary ioConceptDictionary;

  private List<Category> ioCategoryList;

  private Stemmer ioStemmer;

  private ConfigParser ioConfigParser;

  public MainPanel(MainFrame aoParentFrame)
  {
    super();

    ioParentFrame = aoParentFrame;

    try
    {
      jbInit();

      ioConfigParser = new ConfigParser();

      ioConfigParser.parseAll();
      ioCategoryList = ioConfigParser.getCategoryList();
//      ioCategoryDictionary = ioConfigParser.getCategoryDictionary();
      ioConceptDictionary = ioConfigParser.getConceptDictionary();

      ioStemmer = new Stemmer();

//      print();
    }
    catch (Exception ex)
    {
    }
  }

//  public void print()
//  {
//    Category loCategory = null;
//    DictionaryEntry loDictionaryEntry = null;
//
//    for (int i = 0; i < ioCategoryList.size(); i++)
//    {
//      loCategory = ioCategoryList.get(i);
//      for (int j = 0; j < loCategory.getConceptList().size(); j++)
//      {
//        loDictionaryEntry = loCategory.getConceptList().get(j);
//        System.out.println(loDictionaryEntry.getConcept());
//      }
//    }
//  }

  private void jbInit() throws Exception
  {
//    ioResultPanel = new ResultPanel(ioParentFrame);
//    ioTextEditPanel = new TextEditPanel();

    ioSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

//    ioSplitPane.add(ioTextEditPanel, JSplitPane.TOP);
//    ioSplitPane.add(ioResultPanel, JSplitPane.BOTTOM);

    ioSplitPane.setDividerLocation(200);

    setLayout(borderLayout1);
    add(ioSplitPane, java.awt.BorderLayout.CENTER);
  }

  private void analyzeConcepts()
  {
//    ioCategoryList = CategoryParser.getCategoryList();

    Category loCategory = null;

    AnalyzerPanel loAnalyzerPanel = new AnalyzerPanel(ioParentFrame);

    TextTokenizer loTokenizer = new TextTokenizer();
    loTokenizer.getAllTokens(isText);

    StemmedTokenDictionary loStemmedTokenDictionary = loTokenizer.getStemmedTokenDictionary();

    ArrayList<ConceptDictionaryEntry> loFoundPositionList = new ArrayList<ConceptDictionaryEntry>();

//    za sve kategorije
    for (int liCategoryIndex = 0; liCategoryIndex < ioCategoryList.size(); liCategoryIndex++)
    {
      loCategory = ioCategoryList.get(liCategoryIndex);
      System.out.println(loCategory.getCategory());
//      loCategory.parse(isText);

//      lista koncepata za kategoriju
      List<ConceptDictionaryEntry> loConceptList = loCategory.getConceptList();

//      za svaki koncept u kategoriji
      for (int liConceptIndex = 0; liConceptIndex < loConceptList.size(); liConceptIndex++)
      {
        String lsConcept = loConceptList.get(liConceptIndex).getConcept();

        System.out.println(lsConcept);

        ConceptDictionaryEntry loCDE = ioConceptDictionary.getEntry(lsConcept);

        String[] loStemmedConcept = loCDE.getStemmedConceptArray();

        ArrayList<Position> loPositionList = new ArrayList<Position> ();
        ArrayList<Position> p = null;

        StemmedTokenDictionaryEntry loSTDE = null;

        for (int i = 0; i < loStemmedConcept.length; i++)
        {
          loSTDE = loStemmedTokenDictionary.getEntry(loStemmedConcept[i]);

          if (loSTDE != null)
          {
            p = loSTDE.getPositionList();
            loPositionList.addAll(p);
          }
        }

        Collections.sort(loPositionList/*, Collections.reverseOrder()*/);

        int liPreviousTokenId;
        int liCurrentTokenId;

        String lsPreviousToken;
        String lsCurrentToken;

        for (int i = 0; i <= loPositionList.size() - loStemmedConcept.length; i++)
        {
          boolean lbContinue = true;
          liPreviousTokenId = loPositionList.get(i).getTokenId();
          lsPreviousToken = loPositionList.get(i).getToken();

          for (int j = 1; j < loStemmedConcept.length; j++)
          {
            liCurrentTokenId = loPositionList.get(i + j).getTokenId();
            lsCurrentToken = loPositionList.get(i + j).getToken();

            if ( (liCurrentTokenId - liPreviousTokenId == 1) &&
                (lsCurrentToken.equals(lsPreviousToken) == false))
            {
              liPreviousTokenId = liCurrentTokenId;
              lsPreviousToken = lsCurrentToken;
            }
            else
            {
              lbContinue = false;
              break;
            }
          }

          if (lbContinue)
          {
            loCDE.addPosition(loPositionList.get(i));
            loFoundPositionList.add(loCDE);
          }
        }

        loCategory.addPositionList(loCDE.get());

        /*ArrayList<Position> l = cde.get();

        for (int i = 0; i < l.size(); i++)
          System.out.println(String.format("position: %s", l.get(i)));*/
      }

      loAnalyzerPanel.load(loCategory, ioConceptDictionary);
    }

    int liCategoryCount = ioCategoryList.size();
    ArrayList<String> loConceptList = ioConceptDictionary.getConceptList();
    int liConceptCount = loConceptList.size();

    Collections.sort(loFoundPositionList);

    float[][] lfCatToCatTransitionCount = new float[liCategoryCount][liCategoryCount];
    float[] lfCategoryCount = new float[liCategoryCount];

    float[][] lfProbabilityCatToCat = new float[liCategoryCount][liCategoryCount];

    float[][] lfProbabilityConceptToCat = new float[liConceptCount][liCategoryCount];

    for (int i = 0; i < liCategoryCount; i++)
    {
      lfCategoryCount[i] = 0;

      for (int j = 0; j < liCategoryCount; j++)
      {
        lfCatToCatTransitionCount[i][j] = 0;
        lfProbabilityCatToCat[i][j] = 0;
//        lfProbabilityConceptToCat[i][j] = 0;
      }
    }

    int liCurrentCategoryId = -1;
    int liNextCategoryId = -1;

    for (int i = 0; i < loFoundPositionList.size() - 1; i++)
    {
      liCurrentCategoryId = loFoundPositionList.get(i).getCategoryId();
      liNextCategoryId = loFoundPositionList.get(i + 1).getCategoryId();

      lfCatToCatTransitionCount[liCurrentCategoryId][liNextCategoryId]++;
      lfCategoryCount[liCurrentCategoryId]++;
    }
    lfCategoryCount[liNextCategoryId]++;

    for (int i = 0; i < liCategoryCount; i++)
    {
      for (int j = 0; j < liCategoryCount; j++)
      {
        lfProbabilityCatToCat[i][j] =
          0.0001f + lfCatToCatTransitionCount[i][j] / lfCategoryCount[i];
      }
    }

    String lsConcept;
    int liCategoryId;
    int liConceptWithCategoryOccurence;

    ConceptDictionaryEntry loCDE;

    for (int i = 0; i < loConceptList.size(); i++)
    {
      for (int j = 0; j < liCategoryCount; j++)
      {
        liConceptWithCategoryOccurence = 0;

        lsConcept = loConceptList.get(i);

        loCDE = ioConceptDictionary.getEntry(lsConcept);

        if (loCDE == null)
          continue;

        liCategoryId = loCDE.getCategoryId();

        if (liCategoryId == j)
          liConceptWithCategoryOccurence = loCDE.getCount();

        lfProbabilityConceptToCat[i][j] = 0.0f + liConceptWithCategoryOccurence / lfCategoryCount[43];
      }
    }

    try
    {
      BufferedWriter bw = new BufferedWriter(new FileWriter("./test/hmm.txt"));

      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < loConceptList.size(); i++)
      {
        for (int j = 0; j < liCategoryCount; j++)
          sb.append(lfProbabilityConceptToCat[i][j]).append(" ");

        sb.append("\r\n");
      }

      bw.write(sb.toString());
      bw.flush();

      bw.close();
    }
    catch (IOException ex)
    {
    }

    ioResultPanel.addPanel(loAnalyzerPanel, "Defined Concepts");
  }

  private void analyzeAllWords()
  {
    AllWordsParser loParser = new AllWordsParser();

    ObjectCounterMap loCounter = loParser.createResultList(isText);

    AllWordsPanel loAllWordsPanel = new AllWordsPanel(ioParentFrame);
    loAllWordsPanel.setData(loCounter);

    ioResultPanel.addPanel(loAllWordsPanel, "All Words Frequency");
  }

  /**
   * Kreiranje podpanela
   * @param asFile String
   * @throws Exception
   */
  public void createPanels(String asFile) throws Exception
  {
    int i = asFile.lastIndexOf("\\");
    isTitle = asFile.substring(i+1, asFile.length());

    open(asFile);

    ioTextEditPanel = new TextEditPanel();
    ioTextEditPanel.open(asFile);

    ioResultPanel = new ResultPanel(ioParentFrame);

    analyzeConcepts();
    analyzeAllWords();

    ioSplitPane.add(ioTextEditPanel, JSplitPane.TOP);
    ioSplitPane.add(ioResultPanel, JSplitPane.BOTTOM);
  }

  private void open(final String asFile) throws IOException
  {
    File loFile = new File(asFile);
    BufferedReader loFileReader = new BufferedReader(new FileReader(loFile), 4096);
    loFile.length();

    final char[] cb = new char[4096];
    int len;

    StringBuffer sb = new StringBuffer(4096);

    LogPanel.getInstance().setText(String.format("Opening file: %s", asFile), LogMessage.INFO);

    while ((len = loFileReader.read(cb)) != -1)
      sb.append(new String(cb, 0, len));

    loFileReader.close();

    isText = sb.toString();
  }

  public void addPanel(JPanel aoPanel)
  {
    ioResultPanel.addPanel(aoPanel, "Defined Words");
  }

  public void select1(String asConcept)
  {
    String lsConcept = ioStemmer.stemOneWord(asConcept);
    Category loSelectedCategory = ioCategoryDictionary.get(lsConcept);
    Category loCategory;

    if (loSelectedCategory == null)
      return;

    Iterator<Category> loI = ioCategoryList.iterator();
    ParseResult pr = null;

    while( loI.hasNext())
    {
      loCategory = loI.next();
      if (loCategory.getCategory().equals(loSelectedCategory.getCategory()))
      {
        pr = loCategory.getParseResult(asConcept);
        break;
      }
    }

    if (pr == null) return;

    ioTextEditPanel.markWords1(pr);
  }

  public void select(String asConcept)
  {
//    String lsConcept = ioStemmer.stemOneWord(asConcept);
    ConceptDictionary loConceptDictionary = ioConfigParser.getConceptDictionary();
    ConceptDictionaryEntry loCDE = loConceptDictionary.getEntry(asConcept);

    Category loCategory;

    if (loCDE == null)
      return;

    ArrayList<Position> loPositionList = loCDE.get();

    if (loPositionList == null) return;

    ioTextEditPanel.markWords(loPositionList);
  }

  public void find(String asConcept)
  {
    ConceptParser loConceptParser = new ConceptParser();
    ParseResult pr = loConceptParser.createResultList(isText, asConcept);

    if (pr == null) return;

    ioTextEditPanel.markWords1(pr);
  }
}

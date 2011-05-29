package rs.etf.analyzer.gui;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.Toolkit;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import rs.etf.analyzer.util.ObjectCounterMap;
import java.util.List;
import rs.etf.analyzer.parser.CategoryParser;
import java.util.Collections;
import rs.etf.analyzer.parser.ParseResult;
import rs.etf.analyzer.parser.ConceptParser;
import rs.etf.analyzer.parser.AllWordsParser;
import rs.etf.analyzer.parser.Category;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import rs.etf.analyzer.parser.Chapter;
import java.awt.Dimension;
import javax.swing.JComboBox;
import rs.etf.analyzer.main.Project;
import rs.etf.analyzer.main.ProjectFilter;
import java.awt.Cursor;
import rs.etf.analyzer.parser.ConfigParser;

/**
 * <p>Title: SubjectAnalyzer</p>
 *
 * <p>Description: Glavni prozor</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author Dejan Prodanovi?
 * @version 1.0
 */
public class MainFrame extends JFrame implements ActionListener
{
  JPanel contentPane;
  JMenuBar jMenuBar = new JMenuBar();

  JMenu jMenuFile = new JMenu();
  JMenuItem jMINewProject = new JMenuItem();
  JMenuItem jMINewFile = new JMenuItem();
  JMenuItem jMIOpenProject = new JMenuItem();
  JMenuItem jMIOpenFile = new JMenuItem();
  JMenuItem jMISaveProject = new JMenuItem();
  JMenuItem jMISaveProjectAs = new JMenuItem();

  JMenuItem jMenuFileExit = new JMenuItem();

  JMenu jMenuHelp = new JMenu();
  JMenuItem jMIHelpAbout = new JMenuItem();

  JMenu jMenuProject = new JMenu();
  JMenuItem jMIAnalyze = new JMenuItem();
  JMenuItem jMITrain = new JMenuItem();
  JMenuItem jMIProcess = new JMenuItem();

  JToolBar jToolBar = new JToolBar();

  JSplitPane jSplitPane1 = new JSplitPane();
  JSplitPane jSplitPane2 = new JSplitPane();
  JSplitPane jSplitPane3 = new JSplitPane();

  private JFileChooser ioFileChooser = new JFileChooser(".");
  private JFileChooser ioProjectChooser = new JFileChooser(".");

  CategoryPanel ioCategoryPanel = new CategoryPanel(this);
  FilesPanel ioFilesPanel = new FilesPanel(this);

  ProjectPanel ioControlPanel = new ProjectPanel();

  ResultPanel ioResultPanel = new ResultPanel(this);

  AnalyzerPanel ioAnalyzerPanel = new AnalyzerPanel(this);
  AllWordsPanel ioAllWordsPanel = new AllWordsPanel(this);

  TextEditPanel ioTextEditPanel = new TextEditPanel();
  LogPanel ioLogPanel = new LogPanel();

  MainPanel ioMainPanel;// = new MainPanel(this);
  MainContainer ioMainContainer = new MainContainer(this);

  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();

  JButton jBtnNewFile = new JButton();
  JButton jBtnOpenFile = new JButton();

  JButton jBtnNewProject = new JButton();
  JButton jBtnOpenProject = new JButton();

  JButton jBtnSaveProject = new JButton();
  JButton jBtnSaveProjectAs = new JButton();

  JButton jBtnAnalyze = new JButton();
  JButton jBtnTrain = new JButton();
  JButton jBtnProcess = new JButton();

  JButton jBtnFind = new JButton();

  JButton jBtnHelpAbout = new JButton();

  JComboBox ioFindText = new JComboBox();

  JLabel statusBar = new JLabel();
  JMenuItem jMIExit = new JMenuItem();

  CategoryDialog ioCategoryDialog;
  ConceptDialog ioConceptDialog;

  FindDialog ioFindDialog = new FindDialog(this);

  private String isText;

  private ConfigParser ioConfigParser;

  public MainFrame()
  {
    try
    {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jbInit();

      ioConfigParser = new ConfigParser();

      LogPanel.getInstance().setText("Application started...", LogMessage.INFO);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception
  {
    contentPane = (JPanel) getContentPane();

    ioProjectChooser.addChoosableFileFilter(new ProjectFilter(Project.EXT));

    ioFindText.setEditable(true);
    ioFindText.setMaximumSize(new Dimension(150, 20));

    ioFindText.setActionCommand("FIND");
//    ioFindText.addActionListener(this);

    jMINewProject.setText("New Project...");
    jMINewProject.setIcon(SwingUtil.newProject);
    jMINewFile.setText("New File...");
    jMINewFile.setIcon(SwingUtil.newFile);
    jMINewFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    jMIOpenProject.setText("Open Project...");
    jMIOpenProject.setActionCommand("Open Project");
    jMIOpenProject.setIcon(SwingUtil.openProject);
    jMIOpenFile.setText("Open File...");
    jMIOpenFile.setIcon(SwingUtil.openFile);
    jMISaveProject.setText("Save Project...");
    jMISaveProject.setIcon(SwingUtil.saveProject);
    jMISaveProjectAs.setText("Save Project As...");
    jMISaveProjectAs.setIcon(SwingUtil.saveProjectAs);
    jMIExit.setText("Exit");
    jMIOpenProject.addActionListener(this);
    jMIExit.addActionListener(this);

    jMIAnalyze.setText("Analyze file(s)");
    jMIAnalyze.setIcon(SwingUtil.analyzing);
    jMITrain.setText("Train");
    jMITrain.setIcon(SwingUtil.training);
    jMIProcess.setText("Process");
    jMIProcess.setIcon(SwingUtil.processing);

    jMIHelpAbout.setText("About");
    jMIHelpAbout.setIcon(SwingUtil.helpAbout);

    jMenuFile.setText("File");
    jMenuProject.setText("Project");
    jMenuHelp.setText("Help");

    jMenuFile.add(jMINewProject);
    jMenuFile.add(jMINewFile);
    jMenuFile.addSeparator();
    jMenuFile.add(jMIOpenProject);
    jMenuFile.add(jMIOpenFile);
    jMenuFile.addSeparator();
    jMenuFile.add(jMISaveProject);
    jMenuFile.add(jMISaveProjectAs);
    jMenuFile.addSeparator();
    jMenuFile.add(jMIExit);

    jMenuProject.add(jMIAnalyze);
    jMenuProject.add(jMITrain);
    jMenuProject.add(jMIProcess);

    jMenuHelp.add(jMIHelpAbout);

    jMenuBar.add(jMenuFile);
    jMenuBar.add(jMenuProject);
    jMenuBar.add(jMenuHelp);

    contentPane.setLayout(borderLayout1);
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setLastDividerLocation(150);
    ioLogPanel.setLayout(borderLayout2);
    jBtnNewFile.setIcon(SwingUtil.newFile);
    jBtnOpenFile.setIcon(SwingUtil.openFile);
    jBtnNewProject.setIcon(SwingUtil.newProject);
    jBtnOpenProject.setIcon(SwingUtil.openProject);
    jBtnSaveProject.setIcon(SwingUtil.saveProject);
    jBtnSaveProjectAs.setIcon(SwingUtil.saveProjectAs);
    jBtnAnalyze.setIcon(SwingUtil.analyzing);
    jBtnTrain.setIcon(SwingUtil.training);
    jBtnProcess.setIcon(SwingUtil.processing);
    jBtnFind.setIcon(SwingUtil.findText);
    jBtnFind.setActionCommand("FIND_DIALOG");
    jBtnFind.addActionListener(this);
    jBtnHelpAbout.setIcon(SwingUtil.helpAbout);
    jSplitPane2.setLastDividerLocation(150);

    jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jSplitPane3.setLastDividerLocation(150);

    jToolBar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

    jToolBar.add(jBtnNewFile);
    jToolBar.add(jBtnOpenFile);
    jToolBar.add(jBtnNewProject);
    jToolBar.add(jBtnOpenProject);
    jToolBar.add(jBtnSaveProject);
    jToolBar.add(jBtnSaveProjectAs);
    jToolBar.addSeparator();
    jToolBar.add(ioFindText);
    jToolBar.add(jBtnFind);
    jToolBar.addSeparator();
    jToolBar.add(jBtnAnalyze);
    jToolBar.add(jBtnTrain);
    jToolBar.add(jBtnProcess);
    jToolBar.addSeparator();
    jToolBar.add(jBtnHelpAbout);

    statusBar.setText(" ");

    ioControlPanel.addPanel(ioCategoryPanel, "Project");
    ioControlPanel.addPanel(ioFilesPanel, "Files");

    jSplitPane1.setDividerLocation(500);
    jSplitPane2.setDividerLocation(300);
    jSplitPane3.setDividerLocation(250);
    contentPane.add(jSplitPane1, java.awt.BorderLayout.CENTER);

    jSplitPane3.add(ioResultPanel, JSplitPane.TOP);
    jSplitPane3.add(ioTextEditPanel, JSplitPane.BOTTOM);

    jSplitPane2.add(ioControlPanel, JSplitPane.LEFT);
//    jSplitPane2.add(jSplitPane3, JSplitPane.RIGHT);
    jSplitPane2.add(ioMainContainer, JSplitPane.RIGHT);
    jSplitPane1.add(jSplitPane2, JSplitPane.TOP);

    jSplitPane1.add(LogPanel.getInstance(), JSplitPane.BOTTOM);
//    ioLogPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    contentPane.add(jToolBar, java.awt.BorderLayout.NORTH);
    contentPane.add(statusBar, java.awt.BorderLayout.SOUTH);

    setJMenuBar(jMenuBar);

    setTitle("Subject Analyzer");
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
  }

  public void openProject() throws Exception
  {
    ioConfigParser.parseAll();
    List<Chapter> loChapterList = ioConfigParser.getChapterlist();

    Chapter loChapter= null;
    Category loCategory = null;

    for (int i = 0; i < loChapterList.size(); i++)
    {
      loChapter = loChapterList.get(i);
      ioCategoryPanel.load(loChapter);
    }
  }

  public void analyze() throws Exception
  {
    CategoryNode[] loSelectedNodes = ioFilesPanel.getSelectedNodes();

    String lsFile;
    String lsTitle;
    int liIndex;

    setCursor(new Cursor(Cursor.WAIT_CURSOR));

    for (int i = 0; i < loSelectedNodes.length; i++)
    {
      lsFile = loSelectedNodes[i].getText();
      liIndex = lsFile.lastIndexOf("\\");
      lsTitle = lsFile.substring(liIndex + 1, lsFile.length());

      LogPanel.getInstance().setText(String.format("Analyzing file: %s", lsFile), LogMessage.INFO);

      ioMainPanel = new MainPanel(this);
      ioMainPanel.createPanels(lsFile);
      ioMainContainer.add(ioMainPanel, lsTitle);
    }

    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  public void analyzeFile() throws Exception
  {
    List<Category> loCategoryList = ioConfigParser.getCategoryList();//new ArrayList<Category>();
    List<Chapter> loChapterList = ioConfigParser.getChapterlist();

    Chapter loChapter= null;
    Category loCategory = null;

    CategoryNode n = ioFilesPanel.getSelectedNode();

    String lsFile = n.getText();

    LogPanel.getInstance().setText(String.format("Analyzing file: %s", lsFile), LogMessage.INFO);

    open(lsFile);

    for (int i = 0; i < loCategoryList.size(); i++)
    {
      loCategory = loCategoryList.get(i);
      loCategory.parse(isText);
    /*}

    for (int i = 0; i < loCategoryList.size(); i++)
    {
      loCategory = loCategoryList.get(i);
      loCategory.print();*/

      ioAnalyzerPanel.load(loCategory);
    }

    ioMainPanel.addPanel(ioAnalyzerPanel);

    ioMainContainer.add(ioMainPanel, lsFile);

//    ioResultPanel.addPanel(ioAnalyzerPanel, "Concept Frequency");

//    ioTextEditPanel.open(lsFile);
  }

  public void countWords() throws Exception
  {
    CategoryNode n = ioFilesPanel.getSelectedNode();

    String lsFile = n.getText();

    open(lsFile);
    AllWordsParser loParser = new AllWordsParser();
    ObjectCounterMap loCounter = loParser.createResultList(isText);

    ioAllWordsPanel.setData(loCounter);
    ioResultPanel.addPanel(ioAllWordsPanel, "All Words Frequency");

    ioTextEditPanel.open(lsFile);

    /*List loList = loCounter.keysOrderedByCountList();

    Iterator i = loList.iterator();

    String lsOutLine;
    String lsWord;

    while (i.hasNext())
    {
      lsWord = (String)i.next();
//      System.out.println(String.format("Word: %s, Counter: %d", lsWord, loCounter.getCount(lsWord)));

      lsOutLine = String.format("Word: %s, Counter: %d", lsWord, loCounter.getCount(lsWord));

      LogPanel.getInstance().setText(lsOutLine, LogMessage.INFO);
    }*/
  }

  public void select(String asConcept)
  {
//    ParseResult pr = Category.getParseResult(asConcept);
//
//    if (pr == null) return;
//
//    ioTextEditPanel.markWords(pr);
    MainPanel loMainPanel = ioMainContainer.getSelectedPanel();
    loMainPanel.select(asConcept);
  }

  private void open(final String asFile) throws IOException
  {
    File loFile = new File(asFile);
    BufferedReader loFileReader = new BufferedReader(new FileReader(loFile), 4096);
    loFile.length();

    final char[] cb = new char[4096];
    int len;

    StringBuffer sb = new StringBuffer(4096);

    while ((len = loFileReader.read(cb)) != -1)
      sb.append(new String(cb, 0, len));

    loFileReader.close();

    isText = sb.toString();
  }

  public void newConcept()
  {
    ioConceptDialog = new ConceptDialog(this, "New Concept...");
    ioConceptDialog.open();
  }

  public void editConcept()
  {
    ioConceptDialog = new ConceptDialog(this, "Edit Concept...");
    ioConceptDialog.open();
  }

  public void deleteConcept()
  {
  }

  public void addCategoryFile()
  {
    int liRet;
    ioFileChooser.setMultiSelectionEnabled(true);
    ioFileChooser.setCurrentDirectory(new File("."));
    if ( (liRet = ioFileChooser.showOpenDialog(this)) == JFileChooser.APPROVE_OPTION)
    {
      File[] loFiles = ioFileChooser.getSelectedFiles();

      int liIndex = ioControlPanel.getSelectedIndex();

      if (liIndex == 0)
      {
        ioCategoryPanel.addFiles(loFiles);
//        ioCategoryPanel.setExpanded(true);
      }
      else
        ioFilesPanel.addFiles(loFiles);
    }
  }

  public void removeFiles()
  {
    CategoryNode[] loSelectedNodes = ioFilesPanel.getSelectedNodes();

    setCursor(new Cursor(Cursor.WAIT_CURSOR));

    ioFilesPanel.removeFiles(loSelectedNodes);

    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
  }

  public void actionPerformed(ActionEvent e)
  {
    final String lsActionCommand = e.getActionCommand();

    if (lsActionCommand.equalsIgnoreCase("EXIT"))
    {
      LogPanel.getInstance().setText("Exit", LogMessage.INFO);
      System.exit(0);
    }
    else
    if (lsActionCommand.equalsIgnoreCase("OPEN PROJECT"))
    {
      if (ioProjectChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
      {
        ioProjectChooser.getSelectedFile();
      }
    }
    else
    if (lsActionCommand.equalsIgnoreCase("FIND"))
    {
      ioMainPanel = ioMainContainer.getSelectedPanel();
      String lsText = (String)ioFindText.getSelectedItem();

      if (lsText == null) return;

      if (lsText.equals("") == false)
        ioMainPanel.find(lsText);
    }
    else
    if (lsActionCommand.equalsIgnoreCase("FIND_DIALOG"))
    {
      ioMainPanel = ioMainContainer.getSelectedPanel();
      ioFindDialog.open();
    }
  }
}

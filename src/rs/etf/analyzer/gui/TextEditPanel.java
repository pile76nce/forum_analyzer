package rs.etf.analyzer.gui;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import rs.etf.analyzer.parser.ParseResult;
import java.util.List;
import javax.swing.text.Element;
import javax.swing.text.BadLocationException;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import rs.etf.analyzer.parser.Position;
import java.util.ArrayList;

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
public class TextEditPanel extends JPanel
{
  private JTextPane ioTextPane;

  private JScrollPane ioScrollPane;

  private StyledDocument ioStyledDoc;

  private Style ioNormalTextStyle;
  private Style ioSelectedTextStyle;

  private String isText;

  private boolean ibMarkedText = false;

  protected BorderLayout borderLayout1 = new BorderLayout();

  public TextEditPanel()
  {
    super();

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
    ioTextPane = new JTextPane();
    ioTextPane.setEditable(false);

    ioStyledDoc = ioTextPane.getStyledDocument();

    ioNormalTextStyle = ioStyledDoc.addStyle("normal", null);
    ioSelectedTextStyle = ioStyledDoc.addStyle("selected", null);

    StyleConstants.setForeground(ioNormalTextStyle, Color.black);

    StyleConstants.setForeground(ioSelectedTextStyle, Color.red);
    StyleConstants.setBold(ioSelectedTextStyle, true);

    ioScrollPane = new JScrollPane(ioTextPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                              JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    setLayout(borderLayout1);
    add(ioScrollPane, java.awt.BorderLayout.CENTER);
//    ioScrollPane.getViewport().add(ioTextPane);

//    open("./test/pipeline.txt");
  }

  private void setText() throws BadLocationException
  {
    ioTextPane.setText("");
    ioTextPane.getDocument().insertString(0, isText, ioTextPane.getStyle("normal"));
    ioTextPane.setCaretPosition(0);
    ibMarkedText = false;
  }

  public void open(String asFileName) throws Exception
  {
    BufferedReader loReader = new BufferedReader(new FileReader(asFileName), 4096);

    String lsTemp;
    String lsText = "";
    /*while ((lsTemp = loReader.readLine()) != null)
      isText += lsTemp + "\n\r";*/

    final char[] cb = new char[4096];
    int len;

    StringBuffer sb = new StringBuffer(4096);

    while ((len = loReader.read(cb)) != -1)
      sb.append(cb, 0, len);

    loReader.close();

    isText = sb.toString();

    setText();
  }

  public void markWords1(ParseResult aoParseResult)
  {
    List<ParseResult.Position> loPositionList = aoParseResult.getPositionList();
    ParseResult.Position loPosition;

    if (loPositionList.size() > 0)
    {
      if (ibMarkedText == true)
      {
        try
        {
          setText();
        }
        catch (BadLocationException ex)
        {
        }
      }

      int liCaretPosition = loPositionList.get(0).getStart();

      for (int i = 0; i < loPositionList.size(); i++)
      {
        loPosition = loPositionList.get(i);
        ioStyledDoc.setCharacterAttributes(loPosition.getStart(), loPosition.getLen(),
                                           ioTextPane.getStyle("selected"), true);
      }

      ibMarkedText = true;

      ioTextPane.setCaretPosition(liCaretPosition);
    }
  // Get section element
    /*Element section = ioTextPane.getDocument().getDefaultRootElement();

    int paraCount = section.getElementCount();

//    ioStyledDoc.setParagraphAttributes(0, 200, ioTextPane.getStyle("normal"), true);

    ioStyledDoc.setCharacterAttributes(20, 6, ioTextPane.getStyle("selected"), true);
    ioStyledDoc.setCharacterAttributes(102, 6, ioTextPane.getStyle("selected"), true);*/
  }

  public void markWords(ArrayList<Position> aoPositionList)
  {
    if (aoPositionList.size() > 0)
    {
      if (ibMarkedText == true)
      {
        try
        {
          setText();
        }
        catch (BadLocationException ex)
        {
        }
      }

      int liCaretPosition = aoPositionList.get(0).getStart();

      Position loPosition;

      for (int i = 0; i < aoPositionList.size(); i++)
      {
        loPosition = aoPositionList.get(i);
        ioStyledDoc.setCharacterAttributes(loPosition.getStart(), loPosition.getLength(),
                                           ioTextPane.getStyle("selected"), true);
      }

      ibMarkedText = true;

      ioTextPane.setCaretPosition(liCaretPosition);
    }
  // Get section element
    /*Element section = ioTextPane.getDocument().getDefaultRootElement();

    int paraCount = section.getElementCount();

//    ioStyledDoc.setParagraphAttributes(0, 200, ioTextPane.getStyle("normal"), true);

    ioStyledDoc.setCharacterAttributes(20, 6, ioTextPane.getStyle("selected"), true);
    ioStyledDoc.setCharacterAttributes(102, 6, ioTextPane.getStyle("selected"), true);*/
  }

}

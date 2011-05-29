package rs.etf.analyzer.gui;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import java.text.SimpleDateFormat;
import java.text.MessageFormat;
import javax.swing.text.StyleConstants;
import java.util.Date;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JButton;

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
public class LogPanel extends HPanel
{
  JToolBar ioLogToolBar = new JToolBar();
  JTextPane jTxtDebug = new JTextPane();

  JButton jBtnCopy = new JButton(SwingUtil.copy);
  JButton jBtnClear = new JButton(SwingUtil.delete);
  JButton jBtnSave = new JButton(SwingUtil.saveProject);

  StyledDocument ioDoc = (StyledDocument)jTxtDebug.getDocument();
  Style ioInfoStyle, ioWarningStyle, ioExceptionStyle, ioErrorStyle;

  private static LogPanel ioInstance = null;

  private MessageFormat ioMessage = new MessageFormat(LogMessage.MESSAGE_FORMAT);
  private SimpleDateFormat ioFormatter = null;

  private String isText = null;

  public LogPanel()
  {
    super();

    try
    {
      jbInit();
      ioFormatter = new SimpleDateFormat(LogMessage.TIMESTAMP_FORMAT);
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public static LogPanel getInstance()
  {
    if (ioInstance == null) ioInstance = new LogPanel();

    return ioInstance;
  }


  void jbInit() throws Exception
  {
    jTxtDebug.setEditable(false);

    ioLogToolBar.add(jBtnCopy);
    ioLogToolBar.add(jBtnClear);
    ioLogToolBar.add(jBtnSave);

    ioLogToolBar.setFloatable(false);

    jScrollPane = new JScrollPane(jTxtDebug, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    add(ioLogToolBar, BorderLayout.NORTH);
    add(jScrollPane, BorderLayout.CENTER);

    ioInfoStyle = ioDoc.addStyle(LogMessage.INFO, null);
    StyleConstants.setForeground(ioInfoStyle, Color.black);

    ioWarningStyle = ioDoc.addStyle(LogMessage.WARNING, null);
    StyleConstants.setForeground(ioWarningStyle, Color.blue);

    ioExceptionStyle = ioDoc.addStyle(LogMessage.EXCEPTION, null);
    StyleConstants.setForeground(ioExceptionStyle, Color.red);

    ioErrorStyle = ioDoc.addStyle(LogMessage.ERROR, null);
    StyleConstants.setForeground(ioErrorStyle, Color.red);
    StyleConstants.setBold(ioErrorStyle, true);
  }

  public void setText(String psText, String psMessageType)
  {
    Object[] loFormatArgs = {ioFormatter.format(new Date()), psMessageType, psText};
    isText = ioMessage.format(loFormatArgs);
    try
    {
      ioDoc.insertString(ioDoc.getLength(), isText + LogMessage.CRLF, ioDoc.getStyle(psMessageType));
    } catch (BadLocationException ex)
    {
    }
  }

  public void setInfo(String psInfo)
  {
    try
    {
      ioDoc.insertString(ioDoc.getLength(), psInfo, ioInfoStyle);
    } catch (BadLocationException ex)
    {
    }
  }

  public void setWarning(String psWarning)
  {
    try
    {
      ioDoc.insertString(ioDoc.getLength(), psWarning, ioWarningStyle);
    } catch (BadLocationException ex)
    {
    }
  }

  public void setException(String psException)
  {
    try
    {
      ioDoc.insertString(ioDoc.getLength(), psException, ioExceptionStyle);
    } catch (BadLocationException ex)
    {
    }
  }

  public void setError(String psError)
  {
    try
    {
      ioDoc.insertString(ioDoc.getLength(), psError, ioErrorStyle);
    } catch (BadLocationException ex)
    {
    }
  }
}

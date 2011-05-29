package rs.etf.analyzer.gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.*;

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
public class ConceptDialog extends HDialog implements ActionListener
{
  private MainFrame ioParentFrame;
  JLabel jLabel1 = new JLabel();
  JTextField jTxtConcept = new JTextField();
  JPanel jPanel1 = new JPanel();
  JButton jBtnOK = new JButton();
  JButton jBtnCancel = new JButton();

  public ConceptDialog()
  {
    super();

    try
    {
      jbInit();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public ConceptDialog(MainFrame aoParentFrame, final String asDialogTitle)
  {
    super(aoParentFrame, asDialogTitle);

    ioParentFrame = aoParentFrame;

    try
    {
      jbInit();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public void set()
  {
  }

  public void clear()
  {
    jTxtConcept.setText("");
  }

  public void open()
  {
    clear();

    showDialog();
  }

  private void jbInit() throws Exception
  {
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Concept:");
    jLabel1.setBounds(new Rectangle(15, 19, 51, 21));
    this.getContentPane().setLayout(null);
    jBtnOK.setPreferredSize(new Dimension(65, 23));
    this.getContentPane().add(jLabel1, null);
    jBtnOK.setText("OK");
    jBtnOK.addActionListener(this);
    jPanel1.setBounds(new Rectangle(3, 76, 269, 42));
    this.getContentPane().add(jTxtConcept);
    this.getContentPane().add(jPanel1);
    jBtnCancel.setText("Cancel");
    jBtnCancel.addActionListener(this);
    jPanel1.add(jBtnOK);
    jPanel1.add(jBtnCancel);
    jTxtConcept.setText("");
    jTxtConcept.setBounds(new Rectangle(70, 20, 190, 20));

    setMinimumSize(new Dimension(275, 150));
    setPreferredSize(new Dimension(275, 150));
  }

  public void actionPerformed(ActionEvent e)
  {
    final String lsActionCommand = e.getActionCommand();

    if (lsActionCommand.equalsIgnoreCase("OK") == true)
      isStatus = OK_OPTION;
    else
    if (lsActionCommand.equalsIgnoreCase("CANCEL") == true)
      isStatus = CANCEL_OPTION;

    dispose();
  }

}

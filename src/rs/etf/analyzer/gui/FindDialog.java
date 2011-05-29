package rs.etf.analyzer.gui;

import javax.swing.JTextField;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
public class FindDialog extends HDialog implements ActionListener, ChangeListener
{
  JLabel jLblFind = new JLabel();
  JTextField ioFindText = new JTextField();
  JButton jBtnFindAll = new JButton();
  JButton jButton2 = new JButton();
  JCheckBox jCheckBox1 = new JCheckBox();
  JCheckBox jCheckBox2 = new JCheckBox();
  JCheckBox jChkStem = new JCheckBox();
  JLabel jLabel1 = new JLabel();
  JLabel jLblStem = new JLabel();
  JButton jButton3 = new JButton();

  Stemmer ioStemmer = null;

  public FindDialog()
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

  public FindDialog(MainFrame aoParentFrame)
  {
    super(aoParentFrame, "Find");

    try
    {
      jbInit();
      ioStemmer = new Stemmer();
    }
    catch (Exception ex)
    {
    }
  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(null);
    ioFindText.setText("");
    ioFindText.setBounds(new Rectangle(67, 18, 178, 20));
    jLblFind.setHorizontalAlignment(SwingConstants.RIGHT);
    jBtnFindAll.setBounds(new Rectangle(270, 17, 80, 23));
    jBtnFindAll.setText("Find All");
    jBtnFindAll.addActionListener(this);
    jButton2.setBounds(new Rectangle(270, 47, 80, 23));
    jButton2.setText("Count All");
    jCheckBox1.setText("Match Case");
    jCheckBox1.setBounds(new Rectangle(8, 72, 128, 23));
    jCheckBox2.setText("Whole Words Only");
    jCheckBox2.setBounds(new Rectangle(8, 96, 123, 23));
    jChkStem.setText("Stem");
    jChkStem.setBounds(new Rectangle(8, 121, 113, 23));
    jChkStem.addChangeListener(this);
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Stem:");
    jLabel1.setBounds(new Rectangle(10, 46, 52, 15));
    jLblStem.setBounds(new Rectangle(67, 47, 178, 15));
    jButton3.setBounds(new Rectangle(270, 76, 80, 23));
    jButton3.setText("Cancel");
    this.getContentPane().add(jLblFind);
    this.getContentPane().add(ioFindText);
    this.getContentPane().add(jBtnFindAll);
    this.getContentPane().add(jLabel1);
    this.getContentPane().add(jLblStem);
    this.getContentPane().add(jButton2);
    this.getContentPane().add(jButton3);
    this.getContentPane().add(jCheckBox1);
    this.getContentPane().add(jCheckBox2);
    this.getContentPane().add(jChkStem);
    jLblFind.setText("Find Text:");
    jLblFind.setBounds(new Rectangle(7, 17, 55, 21));

    setMinimumSize(new Dimension(400, 180));
    setPreferredSize(new Dimension(400, 180));
    setSize(new Dimension(400, 160));
  }

  private void clear()
  {
    jLblStem.setVisible(false);
    ioFindText.setText("");
  }

  public void open()
  {
    clear();

    showDialog();
  }

  public void actionPerformed(ActionEvent e)
  {
    final String lsActionCommand = e.getActionCommand();


  }

  public void stateChanged(ChangeEvent e)
  {
    final String lsText = ioFindText.getText();

    if (jChkStem.isSelected() && lsText.trim().equals("") == false)
    {
      jLblStem.setText(ioStemmer.stemStringArray(lsText));
      jLblStem.setVisible(true);
    }
    else
      jLblStem.setVisible(false);
  }
}

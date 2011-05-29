package rs.etf.analyzer.gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
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
public class CategoryDialog extends HDialog implements ActionListener
{
  private MainFrame ioParentFrame;

  JLabel jLabel1 = new JLabel();
  JTextField jTxtConcept = new JTextField();
  JPanel jPanel1 = new JPanel();
  JButton jBtnOK = new JButton();
  JButton jBtnCancel = new JButton();

  public CategoryDialog()
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

  public CategoryDialog(MainFrame aoParentFrame, final String asTitle)
  {
    super(aoParentFrame, asTitle);

    ioParentFrame = aoParentFrame;

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
    jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel1.setText("Category:");
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

  public void set()
  {
  }

  public void clear()
  {
  }

  public void open()
  {
    clear();

    showDialog();
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

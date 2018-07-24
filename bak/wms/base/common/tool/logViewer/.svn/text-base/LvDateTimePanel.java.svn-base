//#CM643153
//$Id: LvDateTimePanel.java,v 1.2 2006/11/07 05:53:21 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643154
/**
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//#CM643155
/**
 * Panel Component of Processing date display Label and the text field used by the log viewer. 
 */
public class LvDateTimePanel extends JPanel
{
	//#CM643156
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/07 05:53:21 $";
	}

	//#CM643157
	/**
	 * The background color is acquired from the configuration file. 
	 */
    protected final int[] formBackColor = LogViewerParam.BackColor;

    //#CM643158
    /**
     * Text field RGB value of background color
     */
	protected final Color textColor = new Color(255, 255, 255);

    //#CM643159
    /**
     * Label size
     */
    final Dimension LabelSize = new Dimension(100, 26);

	//#CM643160
	/**
	 * Font of Button label
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	//#CM643161
	/**
	 * Default Constructor
	 */
	public LvDateTimePanel()
	{
	    super(new FlowLayout());
	}

	//#CM643162
	/**
	 * Constructor which specifies number of displayed label Character string
	 * 
	 * @param msgNo		Number of DispResource
	 * @param date 		Processing date
	 */
	public LvDateTimePanel(String msgNo, String date)
	{
		super();
		initialize(msgNo, date);
	}

	//#CM643163
	/**
	 * Specify the number of Label Character string to be Displayed. 
	 * 
	 * @param msgNo			Number of DispResource
	 * @param processDate 	Processing date
	 */
	public void initialize (String msgNo, String processDate) 
    {
	    //#CM643164
	    // Processing dateLabel
	    JLabel lblDate;

	    //#CM643165
	    // Processing date display text field
	    JTextField txtDate;

	    //#CM643166
	    // Panel composition
	    String title = DispResourceFile.getText(msgNo);
        lblDate = new JLabel(title);				// 処理日時ラベル
        txtDate = new JTextField(processDate);		// 処理日時表示テキストフィールド

        lblDate.setPreferredSize(LabelSize);
        lblDate.setHorizontalAlignment(JLabel.RIGHT);
        lblDate.setFont(LabelFont);

        //#CM643167
        // Setting in panel
        txtDate.setEditable(false);					// 入力不可設定
        txtDate.setBackground(textColor);			// 日時処理テキストフィールド背景色
        Color backColor = new Color(formBackColor[0], formBackColor[1], formBackColor[2]);
        lblDate.setBackground(backColor);
        this.setBackground(backColor);				// パネル背景色を指定
        this.setSize(100,100);        				// パネルサイズを指定

        //#CM643168
        // RFT input Label - RFT input column is Added.
        this.add(lblDate);
        this.add(txtDate);
    }
}


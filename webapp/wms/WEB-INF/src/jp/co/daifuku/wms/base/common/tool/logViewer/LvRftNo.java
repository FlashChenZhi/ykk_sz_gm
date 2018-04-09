package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643225
/*
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

import jp.co.daifuku.common.text.StringUtil;


//#CM643226
/**
 * Component for Rft Title machine No input
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/09</TD><TD>tsukoi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:54:49 $
 * @author  $Author: suresh $
 */

public class LvRftNo extends JPanel
{
    //#CM643227
    /**
     * Rft Title machine NoLabel
     */
    JLabel lblRftNo;

    //#CM643228
    /**
     * Rft Title machine No input column
     */
    JTextField txtRftNo;
    
	//#CM643229
	/**
	 * Character color in text field
	 */
	protected final Color fontColor = new Color (000, 000, 000);

    //#CM643230
    /**
     * RGB value of background color
     */
    int[] backColor = LogViewerParam.BackColor;
    
    //#CM643231
    /**
     * Panel size
     */
    final Dimension PanelSize = new Dimension(100, 100);
    
    //#CM643232
    /**
     * Label size
     */
    final Dimension LabelSize = new Dimension(100, 26);

	//#CM643233
	/**
	 * Font of Label
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	//#CM643234
	/**
     * Number of maximum digits of RFT title No. titles
     */
    static final int MaxLength = 3;

    //#CM643235
    /**
     * Generate the instance.
     */
    LvRftNo() 
    {
    	this(true);
    }

    //#CM643236
    /**
     * Generate the instance.
     * @param enabled Possible/Not possible to Input
     * @param text RFT title No.
     */
    LvRftNo(boolean enabled, String text) 
    {
    	this(enabled);
    	txtRftNo.setText(text);
    }

    //#CM643237
    /**
     * Generate the instance.
     * @param enabled Possible/Not possible to Input
     */
    LvRftNo(boolean enabled) 
    {
        super(new FlowLayout());
        

        //#CM643238
        // Definition of Rft Title machine No Label
        lblRftNo = new JLabel(DispResourceFile.getText("LBL-W0186"));
        lblRftNo.setPreferredSize(LabelSize);
        lblRftNo.setHorizontalAlignment(JLabel.RIGHT);
        lblRftNo.setFont(LabelFont);
        
        //#CM643239
        // Definition of Rft Title machine No input column
        txtRftNo = new LvTextField(MaxLength);
        txtRftNo.setColumns(MaxLength + 1);
        
        //#CM643240
        //The background color is set. 
        this.setBackground(new Color(backColor[0], backColor[1], backColor[2]));
		lblRftNo.setBackground(new Color(backColor[0], backColor[1], backColor[2]));
        txtRftNo.setDisabledTextColor(fontColor);
        
        //#CM643241
        // Specify Panel size. 
        this.setSize(PanelSize);
        txtRftNo.setEnabled(enabled);				// 入力可／不可設定

        //#CM643242
        // RFT input Label - RFT input column is Added.
        this.add(lblRftNo);
        this.add(txtRftNo);
    }
    
    //#CM643243
    /**
     * Clear the input value. 
     */
    public void clear()
    {
    	txtRftNo.setText("");
    }

    //#CM643244
    /**
     * Acquire entered Rft Title machine No.<BR>
     * Return it after only a necessary number buries 0 in case of less than triple. 
     * @return	Rft Title machine No
     */
    public String getRftNo()
    {
		String rftNo = txtRftNo.getText();

		if (! StringUtil.isBlank(rftNo))
		{
			//#CM643245
			// Overwrite RFT No. input on "000" Character string to numerical value input RFT No.
			StringBuffer rftBuf = new StringBuffer("000");
			rftBuf.replace(rftBuf.length() - rftNo.length(), rftBuf.length(), rftNo);
			rftNo = rftBuf.toString();
		}

		return rftNo;
    }
}

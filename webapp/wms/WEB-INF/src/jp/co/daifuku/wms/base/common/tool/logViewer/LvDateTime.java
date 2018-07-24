package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//#CM643134
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


//#CM643135
/**
 * Component making of extraction Start date and extraction End date
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/09</TD><TD>tsukoi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:52:27 $
 * @author  $Author: suresh $
 */

class LvDateTime extends JPanel
{
    //#CM643136
    /**
     * Label
     */
    JLabel lblDateTime;

    //#CM643137
    /**
     * Input item of date
     */
    JTextField txtDate;
    
    //#CM643138
    /**
     * Input item of time
     */
    JTextField txtTime;
    
    //#CM643139
    /**
     * RGB value of background color
     */
    int[] rgbColor = LogViewerParam.BackColor;
    
    //#CM643140
    /**
     * Label size
     */
    final Dimension LabelSize = new Dimension(100, 26);
    
	//#CM643141
	/**
	 * Font of Label
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	//#CM643142
	/**
	 * Number of maximum digits at date
	 */
	static final int MaxLengthDate = 8;
    
	//#CM643143
	/**
	 * Number of maximum digits at time
	 */
    static final int MaxLengthTime = 6;
    
    //#CM643144
    /**
     * Constructor
     * 
     * @param labelString	Label Character string to be Displayed
     */
    LvDateTime(String labelString)
    {
        //#CM643145
        // Make Label
        lblDateTime = new JLabel(labelString);
        lblDateTime.setPreferredSize(LabelSize);
        lblDateTime.setHorizontalAlignment(JLabel.RIGHT);
        lblDateTime.setFont(LabelFont);

        //#CM643146
        // Definition of input column of retrieval date
        txtDate = new LvTextField(MaxLengthDate);
        
        //#CM643147
        // Definition of input column of retrieval time
        txtTime = new LvTextField(MaxLengthTime);
        
        //#CM643148
        // Setting of background color
        Color backColor = new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
        lblDateTime.setBackground(backColor);
        this.setBackground(backColor);
        
        //#CM643149
        // Add Component
        this.setLayout(new FlowLayout());
        this.add(lblDateTime);
        this.add(txtDate);
        this.add(txtTime);
        
    }
    
    //#CM643150
    /**
     * Clear the input value. 
     */
    public void clear()
    {
        txtDate.setText("");
        txtTime.setText("");
    }
    
    //#CM643151
    /**
     * Acquire the input date. 
     * @return	Input date
     */
    public String getDate()
    {
    	return txtDate.getText();
    }

    //#CM643152
    /**
     * Acquire the input time. 
     * @return	Input time
     */
    public String getTime()
    {
    	return txtTime.getText();
    }
}

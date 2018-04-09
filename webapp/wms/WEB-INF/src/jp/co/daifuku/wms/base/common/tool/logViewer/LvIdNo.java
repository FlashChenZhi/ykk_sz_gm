package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//#CM643194
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


//#CM643195
/**
 * Making Component for ID No input
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/09</TD><TD>tsukoi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:54:11 $
 * @author  $Author: suresh $
 */

public class LvIdNo extends JPanel
{

    //#CM643196
    /**
     * Label
     */
    JLabel lblIdNo;
    
    //#CM643197
    /**
     * ID No Input area 1
     */
    JTextField txtIdNo1;
    
    //#CM643198
    /**
     * ID No Input area 2
     */
    JTextField txtIdNo2;
    
    //#CM643199
    /**
     * Panel size
     */
    final Dimension PanelSize = new Dimension(100, 26);
    
    //#CM643200
    /**
     * Label size
     */
    final Dimension LabelSize = new Dimension(100, 26);

	//#CM643201
	/**
	 * Font of Label
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	//#CM643202
	/**
     * Number of maximum digits of ID
     */
    static final int MaxLength = LogViewerParam.IdFigure;
    //#CM643203
    /**
     * Generate the instance.
     */
    LvIdNo() 
    {
        //#CM643204
        // Definition of panel
        super(new FlowLayout());
        
        //#CM643205
        // Making Input area Component of Label
        lblIdNo = new JLabel(DispResourceFile.getText("LBL-W0001"));
        lblIdNo.setPreferredSize(LabelSize);
        lblIdNo.setHorizontalAlignment(JLabel.RIGHT);
        lblIdNo.setFont(LabelFont);
        
        //#CM643206
        // Definition of ID input column
        txtIdNo1 = new LvTextField(MaxLength);
        txtIdNo2 = new LvTextField(MaxLength);
        
        //#CM643207
        //Setting of background color
        int[] rgbValue = LogViewerParam.BackColor;
        Color backColor = new Color(rgbValue[0], rgbValue[1], rgbValue[2]);
        lblIdNo.setBackground(backColor);
		this.setBackground(backColor);

		//#CM643208
		// Setting of Panel size
		this.setSize(PanelSize);

        //#CM643209
        // The input area of Label is Added in the panel. 
        this.add(lblIdNo);
        this.add(txtIdNo1);
        this.add(txtIdNo2);
    }
    
    //#CM643210
    /**
     * Clear the input value. 
     */
    public void clear()
    {
        txtIdNo1.setText("");
        txtIdNo2.setText("");
    }
    
    //#CM643211
    /**
     * Acquire identification number 1. 
     * @return		Identification number 1
     */
    public String getIdNo1()
    {
    	return txtIdNo1.getText();
    }

    //#CM643212
    /**
     * Acquire identification number 2. 
     * @return		Identification number 2
     */
    public String getIdNo2()
    {
    	return txtIdNo2.getText();
    }
}

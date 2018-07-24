package jp.co.daifuku.wms.base.common.tool.logViewer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

//#CM643169
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


//#CM643170
/**
 * Make Display flag Button Component
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/09</TD><TD>tsukoi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:53:37 $
 * @author  $Author: suresh $
 */
class LvDisplayConditionButton extends JPanel
{
    //#CM643171
    /**
     * Display flagLabel
     */
    JLabel lblDisplayConditionButton;
    
    //#CM643172
    /**
     * Display flagButton(All)
     */
    JRadioButton btnAll;
    
    //#CM643173
    /**
     * Display flagButton(Send)
     */
    JRadioButton btnSend;
    
    //#CM643174
    /**
     * Display flagButton(Receive)
     */
    JRadioButton btnReceive;

    //#CM643175
    /**
     * Radio Button group
     */
    ButtonGroup buttonGroup;
    
    //#CM643176
    /**
     * RGB value of background color
     */
    int[] rgbColor = LogViewerParam.BackColor;

    //#CM643177
    /**
     * Label size
     */
    final Dimension LabelSize = new Dimension(100, 26);

	//#CM643178
	/**
	 * Font of Label
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	protected final String commandAll = DispResourceFile.getText("RDB-W0051");
    protected final String commandSend = DispResourceFile.getText("RDB-W0052");
    protected final String commandRecv = DispResourceFile.getText("RDB-W0053");

    //#CM643179
    /**
     * Generate the instance.
     */
    LvDisplayConditionButton()
    {
        //#CM643180
        // Setting of title
        lblDisplayConditionButton = new JLabel(DispResourceFile.getText("LBL-W0044"));
        lblDisplayConditionButton.setPreferredSize(LabelSize);
        lblDisplayConditionButton.setHorizontalAlignment(JLabel.RIGHT);
        lblDisplayConditionButton.setFont(LabelFont);
        
        //#CM643181
        // Setting of title of radio Button. 
        btnAll = new JRadioButton(commandAll);
        btnSend = new JRadioButton(commandSend);
        btnReceive = new JRadioButton(commandRecv);
        
        //#CM643182
        // Setting of background color
        Color backColor = new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
        btnAll.setBackground(backColor);
        btnAll.setSelected(true);
        btnSend.setBackground(backColor);
        btnReceive.setBackground(backColor);
        btnAll.setFont(LabelFont);
        btnSend.setFont(LabelFont);
        btnReceive.setFont(LabelFont);
        lblDisplayConditionButton.setBackground(backColor);
        this.setBackground(backColor);
        
        //#CM643183
        // Making of each Button group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(btnAll);
        buttonGroup.add(btnSend);
        buttonGroup.add(btnReceive);

        btnAll.setActionCommand("1");
        btnSend.setActionCommand("2");
        btnReceive.setActionCommand("3");
        
        this.setLayout(new FlowLayout());
        this.add(lblDisplayConditionButton);
        this.add(btnAll);
        this.add(btnSend);
        this.add(btnReceive);
    }
    
    //#CM643184
    /**
     *  Make initial Component.
     */
    public void clear()
    {
    	btnAll.setSelected(true);
    }
    
    //#CM643185
    /**
     * Acquire selected Display condition. 
     * 
     * @return		Sending and receiving flag
     */
    public int getSendRecvDivision()
    {
    	ButtonModel bm = buttonGroup.getSelection();
    	String command = bm.getActionCommand();
    	return Integer.parseInt(command);
    }
}

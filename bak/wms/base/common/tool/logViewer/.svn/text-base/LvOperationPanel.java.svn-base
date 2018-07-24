package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643213
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

//#CM643214
/**
 * Component with display Button and clear Button. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:54:26 $
 * @author  $Author: suresh $
 */

class LvOperationPanel extends JPanel 
{
    //#CM643215
    /**
     * Display Button
     */
    LvButton btnDisplay;
    
    //#CM643216
    /**
     * Clear Button
     */
    LvButton btnClear;
    
    //#CM643217
    /**
     * RGB value of background color
     */
    final int[] rgbColor = LogViewerParam.BackColor;

	//#CM643218
	/**
	 * Button size
	 */
	protected final Dimension size = new Dimension(72, 26);
   
    //#CM643219
    /**
     * Constructor
     * 
     * @param parent	Parent Component
     */
    LvOperationPanel(LogViewer parent) 
    {
        //#CM643220
        // Make Button
        btnDisplay = new LvButton("BTN-9022");
        btnClear = new LvButton("BTN-9005");
        
        btnDisplay.addActionListener(parent);
        btnClear.addActionListener(parent);
     
        btnDisplay.setPreferredSize(size);
        btnClear.setPreferredSize(size);

        //#CM643221
        // Set Background color
        Color backColor = new Color(rgbColor[0], rgbColor[1], rgbColor[2]);
        this.setBackground(backColor);
        
        //#CM643222
        // Set Button
        this.add(btnDisplay);
        this.add(btnClear);
    }

	//#CM643223
	/**
	 * Acquire Clear Button. 
	 * 
	 * @return	Clear Button
	 */
	public LvButton getClearButton()
	{
		return btnClear;
	}

	//#CM643224
	/**
	 * Acquire Display Button. 
	 * 
	 * @return	Display Button
	 */
	public LvButton getDisplayButton()
	{
		return btnDisplay;
	}
}

    

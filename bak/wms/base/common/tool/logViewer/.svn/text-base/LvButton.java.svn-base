// $Id: LvButton.java,v 1.2 2006/11/07 05:52:09 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643125
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

//#CM643126
/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 * Button Component used by the log viewer. 
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:52:09 $
 * @author  $Author: suresh $
 */

public class LvButton extends JButton
{
	//#CM643127
	/**
	 * Background color of Button
	 */
	protected final Color bgColor = new Color(255, 165, 0);

	//#CM643128
	/**
	 * Font of Button label
	 */
	protected final Font LabelFont = new Font("Serif", Font.PLAIN, 12);

	//#CM643129
	/**
	 * Button size
	 */
	protected final Dimension size = new Dimension(72, 26);

	//#CM643130
	/**
	 * Default Constructor
	 */
	public LvButton()
	{
		super();
	}

	//#CM643131
	/**
	 * Constructor which specifies number of displayed label Character string
	 * 
	 * @param msgNo		Number of DispResource
	 */
	public LvButton(String msgNo)
	{
		super();
		initialize(msgNo);
	}

	//#CM643132
	/**
	 * Initialize Button Component. 
	 * 
	 * @param msgNo		Number of DispResource
	 */
	public void initialize(String msgNo)
	{
		setBackground(bgColor);
		setFont(LabelFont);
		setPreferredSize(size);
        
		//#CM643133
		// Acquire label Character string from DispResource. 
		String title = DispResourceFile.getText(msgNo);
		setText(title);
	}
}

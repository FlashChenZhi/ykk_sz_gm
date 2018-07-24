package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643186
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


//#CM643187
/**
 *  Component for Host name input
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/09</TD><TD>tsukoi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:53:53 $
 * @author  $Author: suresh $
 */

public class LvHostname extends LvRftNo
{
	//#CM643188
	/**
     *  Number of maximum digits of Host names
     */
    static final int MaxLength = 16;

    //#CM643189
    /**
     * Generate the instance.
     */
    LvHostname() 
    {
    	this(true);
    }

    //#CM643190
    /**
     * Generate the instance.
     */
    LvHostname(boolean enabled, String text) 
    {
    	this(enabled);
    	txtRftNo.setText(text);
    }

    //#CM643191
    /**
     * Generate the instance.
     * @param enabled Possible/Not possible to Input
     */
    LvHostname(boolean enabled) 
    {
    	super(enabled);
    	
        lblRftNo.setText(DispResourceFile.getText("LBL-W0187"));
        txtRftNo.setColumns(MaxLength);
    }
    
    //#CM643192
    /**
     * Clear the input value. 
     */
    public void clear()
    {
    	txtRftNo.setText("");
    }

    //#CM643193
    /**
     * Acquire entered Rft Title machine No.
     * Return it after only a necessary number buries 0 in case of less than triple. 
     * @return	Rft Title machine No
     */
    public String getRftNo()
    {
		return txtRftNo.getText();
    }
}

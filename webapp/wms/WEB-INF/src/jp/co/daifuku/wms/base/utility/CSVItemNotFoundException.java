//#CM686608
//$Id: CSVItemNotFoundException.java,v 1.3 2006/11/10 00:30:04 suresh Exp $
package jp.co.daifuku.wms.base.utility;

//#CM686609
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM686610
/**
 * Process exception when a specified field is not found in either csv file or properties file
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/24</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/10 00:30:04 $
 * @author  $Author: suresh $
 */
public class CSVItemNotFoundException extends Exception{
	
	//#CM686611
	// Class fields --------------------------------------------------

	//#CM686612
	// Class variables -----------------------------------------------

	//#CM686613
	// Class method --------------------------------------------------

	//#CM686614
	// Constructors --------------------------------------------------
	//#CM686615
	/**
	 * Process exception when a specified field is not found in either csv file or environment properties file
	 * 
	 * @param ColumName field name
	 */
	public CSVItemNotFoundException(String ColumName)
	{
		super(ColumName + " info does no exist in environment info or csv file" );
	}
	
	//#CM686616
	// Package methods -----------------------------------------------

	//#CM686617
	// Protected methods ---------------------------------------------

	//#CM686618
	// Private methods -----------------------------------------------

}
//#CM686619
//end of class

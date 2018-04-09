//#CM686593
//$Id: CsvIllegalDataException.java,v 1.2 2006/11/07 07:16:11 suresh Exp $
package jp.co.daifuku.wms.base.utility;

//#CM686594
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM686595
/**
 * throw exception if any field in the csv file contains improper values
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/24</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:16:11 $
 * @author  $Author: suresh $
 */
public class CsvIllegalDataException extends Exception
{
	//#CM686596
	// Class fields --------------------------------------------------

	//#CM686597
	// Class variables -----------------------------------------------	
	//#CM686598
	/**
	 * to save error message
	 */
	private String wMessage = null;

	//#CM686599
	// Class method --------------------------------------------------

	//#CM686600
	// Constructors --------------------------------------------------
	//#CM686601
	/**
	 * If the csv file field contains abnormal characters,
	 * set error details and throw exception
	 * 
	 * @param Value error details
	 */
	public CsvIllegalDataException(String Value)
	{
		super(Value);
		
		this.setMessage(Value);
	}
	
	//#CM686602
	/**
	 * set the error message conditions if any check fails in this class
	 * 
	 * @return error message
	 */
	public void setMessage(String Value)
	{
		wMessage = Value;
	}
	
	//#CM686603
	/**
	 * fetch the error message conditions if any check fails in this class
	 * 
	 * @return error message
	 */
	public String getMessage()
	{
		return wMessage;
	}
	
	//#CM686604
	// Package methods -----------------------------------------------

	//#CM686605
	// Protected methods ---------------------------------------------

	//#CM686606
	// Private methods -----------------------------------------------

}
//#CM686607
//end of class

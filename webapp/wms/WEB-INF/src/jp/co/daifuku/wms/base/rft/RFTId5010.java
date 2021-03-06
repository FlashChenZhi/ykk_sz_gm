// $Id: RFTId5010.java,v 1.2 2006/11/14 06:09:18 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702322
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702323
/**
 * Make "Person in charge code response ID =   1004" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id1004</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Person in charge name</td>			<td>20 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td> Detailed Error</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:18 $
 * @author  $Author: suresh $
 */

public class RFTId5010 extends SendIdMessage
{
	//#CM702324
	// Class field --------------------------------------------------
	//#CM702325
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702326
	/**
	 * Offset of person in charge name
	 */
	private static final int OFF_WORKER_NAME =
		OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702327
	/**
	 * ID Number
	 */
	public static final String ID = "5010";

	//#CM702328
	// Class method ------------------------------------------------
	//#CM702329
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $Date: 2006/11/14 06:09:18 $");
	}

	//#CM702330
	// Constructors -------------------------------------------------
	//#CM702331
	/**
	 * Generate the instance. 
	 */
	public RFTId5010()
	{
		super();

		offAnsCode = OFF_WORKER_NAME + LEN_WORKER_NAME;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702332
	// Public methods -----------------------------------------------
	//#CM702333
	/**
	 * Set Person in charge code. 
	 * @param	workerCode	Person in charge code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM702334
	/**
	 * Set the person in charge name. 
	 * @param	workerName	Person in charge Name
	 */
	public void setWorkerName(String workerName)
	{
		setToBuffer(workerName, OFF_WORKER_NAME);
	}

	//#CM702335
	// Package methods ----------------------------------------------

	//#CM702336
	// Protected methods --------------------------------------------

	//#CM702337
	// Private methods ----------------------------------------------

}
//#CM702338
//end of class

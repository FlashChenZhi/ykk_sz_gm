// $Id: RFTId5003.java,v 1.2 2006/11/14 06:09:16 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702261
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702262
/**
 * Make "Work Completed response ID =   5003" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id1002</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Work type</td>			<td>2 byte</td></tr>
 * <tr><td>Response flag</td>			<td>4 byte</td></tr>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:16 $
 * @author  $Author: suresh $
 */

public class RFTId5003 extends SendIdMessage
{
	//#CM702263
	// Class field --------------------------------------------------
	//#CM702264
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702265
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702266
	/**
	 * ID Number
	 */
	public static final String ID = "5003";

	//#CM702267
	// Class method ------------------------------------------------
	//#CM702268
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:16 $";
	}

	//#CM702269
	// Constructors -------------------------------------------------
	//#CM702270
	/**
	 * Generate the instance. 
	 */
	public RFTId5003()
	{
		super();

		offAnsCode = OFF_WORK_TYPE + LEN_WORK_TYPE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702271
	// Public methods -----------------------------------------------
	//#CM702272
	/**
	 * Set Person in charge code. 
	 * @param	workerCode	Worker code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM702273
	/**
	 * Set Work type
	 * @param	workType	Work type
	 */
	public void setWorkType(String workType)
	{
		setToBuffer(workType, OFF_WORK_TYPE);
	}

	//#CM702274
	// Package methods ----------------------------------------------

	//#CM702275
	// Protected methods --------------------------------------------

	//#CM702276
	// Private methods ----------------------------------------------

}
//#CM702277
//end of class

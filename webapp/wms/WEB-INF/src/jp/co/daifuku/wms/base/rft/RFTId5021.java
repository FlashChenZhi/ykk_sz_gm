// $Id: RFTId5021.java,v 1.2 2006/11/14 06:09:19 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702433
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702434
/**
 * Make "Plan date list response ID =   5021" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5021</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td></tr>
 * <tr><td>List file name</td>		<td>30 byte</td></tr>
 * <tr><td>Number of Failure Code</td>	<td>6 byte</td></tr>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:19 $
 * @author  $Author: suresh $
 */

public class RFTId5021 extends SendIdMessage
{
	//#CM702435
	// Class field --------------------------------------------------
	//#CM702436
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702437
	/**
	 * Offset of List file name
	 */
	private static final int OFF_TABLE_FILE_NAME = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702438
	/**
	 * Offset of Number of Failure Code
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_TABLE_FILE_NAME + LEN_FILE_NAME;

	//#CM702439
	/**
	 * ID Number
	 */
	public static final String ID = "5021";

	//#CM702440
	// Class method ------------------------------------------------
	//#CM702441
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:19 $";
	}

	//#CM702442
	// Constructors -------------------------------------------------
	//#CM702443
	/**
	 * Generate the instance. 
	 */
	public RFTId5021()
	{
		super();

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702444
	// Public methods -----------------------------------------------
	//#CM702445
	/**
	 * Set Worker code
	 * @param	workerCode	Worker code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM702446
	/**
	 * Set List file name date
	 * @param	tableFileName	List file name
	 */
	public void setTableFileName(String tableFileName)
	{
		setToBuffer(tableFileName, OFF_TABLE_FILE_NAME);
	}

	//#CM702447
	/**
	 * Set Number of Failure Code. 
	 * @param	fileRecordNumber	Number of Record list files
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}

	//#CM702448
	// Package methods ----------------------------------------------

	//#CM702449
	// Protected methods --------------------------------------------

	//#CM702450
	// Private methods ----------------------------------------------

}
//#CM702451
//end of class

// $Id: RFTId5022.java,v 1.2 2006/11/14 06:09:20 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702452
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702453
/**
 * Make "consignor list (It specifies and exist at the date) response ID =   5022" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5022</CAPTION>
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
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:20 $
 * @author  $Author: suresh $
 */

public class RFTId5022 extends SendIdMessage
{
	//#CM702454
	// Class field --------------------------------------------------
	//#CM702455
	/**
	 * Offset of Worker code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702456
	/**
	 * Offset of List file name
	 */
	private static final int OFF_LIST_FILE_NAME =
		OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702457
	/**
	 * Offset of Number of Failure Code
	 */
	private static final int OFF_FILE_RECORD_NUMBER =
		OFF_LIST_FILE_NAME + LEN_FILE_NAME;

	//#CM702458
	/**
	 * ID Number
	 */
	public static final String ID = "5022";

	//#CM702459
	// Class method ------------------------------------------------
	//#CM702460
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:20 $";
	}

	//#CM702461
	// Constructors -------------------------------------------------
	//#CM702462
	/**
	 * Generate the instance. 
	 */
	public RFTId5022()
	{
		super();

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702463
	// Public methods -----------------------------------------------
	//#CM702464
	/**
	 * Set Worker code
	 * @param	workerCode	Worker code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM702465
	/**
	 * Set Consignor List file name
	 * @param	listFileName	List file name
	 */
	public void setListFileName(String listFileName)
	{
		setToBuffer(listFileName, OFF_LIST_FILE_NAME);
	}

	//#CM702466
	/**
	 * Set Number of Failure Code. 
	 * @param	fileRecordNumber	Number of Record list files
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(
			fileRecordNumber,
			OFF_FILE_RECORD_NUMBER,
			LEN_FILE_RECORD_NUMBER);
	}

	//#CM702467
	// Package methods ----------------------------------------------

	//#CM702468
	// Protected methods --------------------------------------------

	//#CM702469
	// Private methods ----------------------------------------------

}
//#CM702470
//end of class

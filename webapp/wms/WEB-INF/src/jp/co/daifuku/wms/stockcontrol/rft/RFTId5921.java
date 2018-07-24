//#CM10503
//$Id: RFTId5921.java,v 1.2 2006/09/27 03:00:32 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM10504
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10505
/**
 * Creates electronic statements of "stock table responce ID = 5921" in socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id5921</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy Transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server -Transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy terminalNo</td>		<td>3 byte</td></tr>
 * <tr><td></td>		<td>4 byte</td></tr>
 * <tr><td>Table File Name</td>		<td>30 byte</td></tr>
 * <tr><td>File record Qty</td>	<td>6 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td>error detail</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:32 $
 * @author  $Author: suresh $
 */

public class RFTId5921 extends SendIdMessage
{
	//#CM10506
	// Class field --------------------------------------------------
	//#CM10507
	/**
	 * Offset
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM10508
	/**
	 * Offset the list file name.
	 */
	private static final int OFF_TABLE_FILE_NAME =
		OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM10509
	/**
	 * Offset the File record Qty.
	 */
	private static final int OFF_FILE_RECORD_NUMBER =
		OFF_TABLE_FILE_NAME + LEN_FILE_NAME;

	//#CM10510
	/**
	 * ID No.
	 */
	public static final String ID = "5921";

	//#CM10511
	// Class method ------------------------------------------------
	//#CM10512
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/09/27 03:00:32 $";
	}

	//#CM10513
	// Constructors -------------------------------------------------
	//#CM10514
	/**
	 * Generate Instance
	 */
	public RFTId5921()
	{
		super();

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM10515
	// Public methods -----------------------------------------------
	//#CM10516
	/**
	 * Set the person in charge code.
	 * @param	workerCode	
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM10517
	/**
	 * Set the stock list file name.
	 * @param	tableFileName	Table File Name
	 */
	public void setTableFileName(String tableFileName)
	{
		setToBuffer(tableFileName, OFF_TABLE_FILE_NAME);
	}

	//#CM10518
	/**
	 * Set the file record count.
	 * @param	fileRecordNumber	File record number of table
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(
			fileRecordNumber,
			OFF_FILE_RECORD_NUMBER,
			LEN_FILE_RECORD_NUMBER);
	}

	//#CM10519
	// Package methods ----------------------------------------------

	//#CM10520
	// Protected methods --------------------------------------------

	//#CM10521
	// Private methods ----------------------------------------------

}
//#CM10522
//end of class

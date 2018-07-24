//#CM702471
//$Id: RFTId5023.java,v 1.2 2006/11/14 06:09:20 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM702472
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702473
/**
 * Make "Area list response ID =   5023" telegram in the socket communication. <BR>
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5023</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
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
 * <TR><TD>2005/11/04</TD><TD>etakeda</TD><TD>New making</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:20 $
 * @author  $Author: suresh $
 */
public class RFTId5023 extends SendIdMessage
{

//#CM702474
//  Class field --------------------------------------------------
	//#CM702475
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702476
	/**
	 * Offset of List file name
	 */
	private static final int OFF_FILE_NAME = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM702477
	/**
	 * Offset of Number of Failure Code
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_FILE_NAME + LEN_FILE_NAME ;
	
	//#CM702478
	/**
	 * ID Number
	 */
	public static final String ID = "5023";

	//#CM702479
	// Class method ------------------------------------------------
	//#CM702480
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:20 $";
	}
	
	//#CM702481
	// Constructors -------------------------------------------------
	//#CM702482
	/**
	 * Constructor
	 */
	public RFTId5023()
	{
		super() ;

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM702483
	// Public methods -----------------------------------------------
	//#CM702484
	/**
	 * Set Person in charge code. 
	 * @param workerCode Person in charge code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE);
	}
	
	//#CM702485
	/**
	 * Set List file name
	 * @param tableFileName List file name
	 */
	public void setTableFileName(String tableFileName)
	{
		setToBuffer(tableFileName , OFF_FILE_NAME);
	}
	
	//#CM702486
	/**
	 * Set Number of Failure Code. 
	 * @param fileRecordNumber Number of Failure Code
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}
	
	//#CM702487
	// Package methods ----------------------------------------------
	
	//#CM702488
	// Protected methods --------------------------------------------
	
	//#CM702489
	// Private methods ----------------------------------------------

}
//#CM702490
//end of class

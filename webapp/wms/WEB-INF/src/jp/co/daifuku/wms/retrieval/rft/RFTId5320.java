// $Id: RFTId5320.java,v 1.3 2007/02/07 04:19:47 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM721813
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM721814
/**
 * Generate " ID = 5320 Response for Picking Order List" electronic statement electronic statement for socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Construction of Id5320 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>			<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td><td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td><td></td></tr>
 * <tr><td>ID</td>					<td>4 byte</td><td>5320</td></tr>
 * <tr><td>Period for sending by Handy</td>	<td>6 byte</td><td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td><td>HHMMSS</td></tr>
 * <tr><td>Handy Machine No.</td>		<td>3 byte</td><td></td></tr>
 * <tr><td>Personnel Code</td>		<td>4 byte</td><td></td></tr>
 * <tr><td>List file name</td>		<td>30 byte</td><td></td></tr>
 * <tr><td>Number of records in a file.</td>	<td>6 byte</td><td></td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td><td>0:Normal, 8:No corresponding data, 9: Error</td></tr>
 * <tr><td>Details of error</td>			<td>2 byte</td><td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td><td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:47 $
 * @author  $Author: suresh $
 */

public class RFTId5320 extends SendIdMessage
{
	//#CM721815
	// Class field --------------------------------------------------
	//#CM721816
	/**
	 * Offset the Personnel Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM721817
	/**
	 * Offset the Offset List File Name.
	 */
	private static final int OFF_FILE_NAME = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM721818
	/**
	 * Offset the number of records in a file.
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_FILE_NAME + LEN_FILE_NAME ;

	//#CM721819
	/**
	 * ID No.
	 */
	public static final String ID = "5320";

	//#CM721820
	// Class method ------------------------------------------------
	//#CM721821
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:47 $";
	}
	
	//#CM721822
	// Constructors -------------------------------------------------
	//#CM721823
	/**
	 * Constructor
	 */
	public RFTId5320 ()
	{
		super() ;
		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM721824
	// Public methods -----------------------------------------------
	//#CM721825
	/**
	 * Set the Personnel code.
	 * @param workerCode Personnel Code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE);
	}
	
	//#CM721826
	/**
	 * Set the list file name.
	 * @param tableFileName List file name
	 */
	public void setTableFileName(String tableFileName)
	{
		setToBuffer(tableFileName , OFF_FILE_NAME);
	}
	
	//#CM721827
	/**
	 * Set the file record count.
	 * @param fileRecordNumber Number of records in a file.
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}
		
	//#CM721828
	// Package methods ----------------------------------------------
	
	//#CM721829
	// Protected methods --------------------------------------------

	//#CM721830
	// Private methods ----------------------------------------------

}
//#CM721831
//end of class

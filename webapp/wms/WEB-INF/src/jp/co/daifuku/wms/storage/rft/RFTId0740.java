// $Id: RFTId0740.java,v 1.2 2006/12/07 09:00:06 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576488
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576489
/**
 * Relocation Storage start request ID=0740 telegraph message process<BR>
 * 
 * <p>
  * <table border="1">
 * <CAPTION>Structure of Id0740 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0740</td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Scan code 1</td>	<td>16 byte</td><td>From front</td></tr>
 * <tr><td>Scan code 2</td>	<td>16 byte</td><td>From front <br>
 * 													(If ITFtoJAN is enabled, converted value)</td></tr>
 * <tr><td>Relocation Job no.</td>		<td>8 byte</td>	<td></td></tr>
 * <tr><td>Case piece type</td><td>1 byte</td>	<td>Not used</td></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:06 $
 * @author  $Author: suresh $
 */
public class RFTId0740 extends RecvIdMessage
{
	//#CM576490
	// Class fields --------------------------------------------------
	//#CM576491
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576492
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576493
	/**
	 * Scan code 1 offset definition
	 */
	private static final int OFF_SCAN_CODE_1 = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576494
	/**
	 * Scan code 2 offset definition
	 */
	private static final int OFF_SCAN_CODE_2 = OFF_SCAN_CODE_1 + LEN_JAN_CODE ;
	
	//#CM576495
	/**
	 * Relocation Job no. offset definition
	 */
	private static final int OFF_MOVE_JOB_NO = OFF_SCAN_CODE_2 + LEN_JAN_CODE ;
	
	//#CM576496
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_MOVE_JOB_NO + LEN_MOVE_JOB_NO ;

	//#CM576497
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;
	
		
	//#CM576498
	/**
	 * ID number
	 */
	public static final String ID = "0740";

	//#CM576499
	// Class variables -----------------------------------------------
	//#CM576500
	// Class method --------------------------------------------------
	//#CM576501
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:06 $") ;
	}

	//#CM576502
	// Constructors --------------------------------------------------
	//#CM576503
	/**
	 * Constructor
	 */
	public RFTId0740 ()
	{
		super() ;
		offEtx = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576504
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0740 Relocation Storage start request ID=0740 telegraph message
	 */
	public RFTId0740 (byte[] rftId0740)
	{
		super() ;
		setReceiveMessage(rftId0740) ;
	}

	//#CM576505
	// Public methods ------------------------------------------------
	//#CM576506
	/**
	 * Relocation Fetch Worker code from Storage start request telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576507
	/**
	 * Relocation Fetch Consignor code from Storage start request telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576508
	/**
	 * Fetch Scan code 1 from Relocation Storage start request telegraph message
	 * @return		Scan code 1
	 */
	public String getScanCode1 ()
	{
		String scanCode1 = getFromBuffer(OFF_SCAN_CODE_1, LEN_JAN_CODE) ;
		return scanCode1.trim();
	}
	
	//#CM576509
	/**
	 * Fetch Scan code 2 from Relocation Storage start request telegraph message
	 * @return		Scan code 2
	 */
	public String getScanCode2 ()
	{
		String scanCode2 = getFromBuffer(OFF_SCAN_CODE_2, LEN_JAN_CODE) ;
		return scanCode2.trim();
	}
	
	//#CM576510
	/**
	 * Fetch Relocation Job no. from Relocation Storage start request telegraph message
	 * @return		Relocation Job no.
	 */
	public String getMoveJobNo ()
	{
		String moveJobNo = getFromBuffer(OFF_MOVE_JOB_NO, LEN_MOVE_JOB_NO) ;
		return moveJobNo.trim();
	}
	
	//#CM576511
	/**
	 * Relocation Fetch Case piece type from Storage start request telegraph message
	 * @return		Case piece type(1:Case 2:Piece)
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	//#CM576512
	/**
	 *  Fetch Expiry date from Relocation Storage start request telegraph message
	 * @return		Expiry date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE , LEN_USE_BY_DATE);
		return useByDate.trim();
	}

	//#CM576513
	// Package methods -----------------------------------------------
	//#CM576514
	// Protected methods ---------------------------------------------
	//#CM576515
	// Private methods -----------------------------------------------

}
//#CM576516
//end of class


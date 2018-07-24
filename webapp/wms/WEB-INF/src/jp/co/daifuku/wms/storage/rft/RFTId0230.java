// $Id: RFTId0230.java,v 1.2 2006/12/07 09:00:07 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576337
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576338
/**
 * This processes Storage start request ID=0230<BR>
 * 
 * ID0230 ID details
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegraph message Id0230</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td> <td></td></tr>
 * <tr><td>ID</td>					<td>4 byte</td> <td>0230</td></tr>
 * <tr><td>Handy send time</td>	<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>		<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>		<td>3 byte</td> <td></td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td> <td></td></tr>
 * <tr><td>Consignor code</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Supplier code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Storage plan date</td>			<td>8 byte</td> <td>YYYYMMDD</td></tr>
 * <tr><td>Scan Item code1</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>Scan Item code2</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>Case piece type</td> 	<td>1 byte</td> <td>0:All 1:Case 2:Piece 3:None</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:07 $
 * @author  $Author: suresh $
 */
public class RFTId0230 extends RecvIdMessage
{
	//#CM576339
	// Class fields --------------------------------------------------
	//#CM576340
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576341
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576342
	/**
	 * Supplier code offset definition
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576343
	/**
	 * Storage plan date offset definition
	 */
	private static final int OFF_PLAN_DATE = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE ;
	
	//#CM576344
	/**
	 * Scan Item code1 offset definition
	 */
	private static final int OFF_SCAN_ITEM_CODE1 = OFF_PLAN_DATE + LEN_PLAN_DATE ;
	
	//#CM576345
	/**
	 * Scan Item code2 offset definition
	 */
	private static final int OFF_SCAN_ITEM_CODE2 = OFF_SCAN_ITEM_CODE1  + LEN_ITEM_CODE  ;	
	
	//#CM576346
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_SCAN_ITEM_CODE2 + LEN_ITEM_CODE ;

	//#CM576347
	/**
	 * Case field of case piece type
	 */
	public static final String CASE_PIECE_CASE = "1" ;
	
	//#CM576348
	/**
	 * Piece field of case piece type
	 */
	public static final String CASE_PIECE_PIECE = "2" ;
	
	//#CM576349
	/**
	 * None field of case piece type
	 */
	public static final String CASE_PIECE_ = "3" ;

	//#CM576350
	/**
	 * All field of case piece type
	 */
	public static final String CASE_PIECE_All = "0" ;
	//#CM576351
	/**
	 * ID number
	 */
	public static final String ID = "0230";

	//#CM576352
	// Class variables -----------------------------------------------

	//#CM576353
	// Class method --------------------------------------------------
	//#CM576354
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:07 $") ;
	}

	//#CM576355
	// Constructors --------------------------------------------------
	//#CM576356
	/**
	 * Constructor
	 */
	public RFTId0230 ()
	{
		super() ;
		offEtx = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576357
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0230 Storage start request ID=0230 telegraph message
	 */
	public RFTId0230 (byte[] rftId0230)
	{
		super() ;
		setReceiveMessage(rftId0230) ;
	}

	//#CM576358
	// Public methods ------------------------------------------------
	//#CM576359
	/**
	 * Fetch Worker code from Storage start request telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576360
	/**
	 * Fetch Consignor code from Storage start request telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576361
	/**
	 * Fetch Supplier code from Storage start request telegraph message
	 * @return		Supplier code
	 */
	public String getSupplierCode ()
	{
		String supplierCode = getFromBuffer(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE) ;
		return supplierCode.trim();
	}
	
	//#CM576362
	/**
	 * Fetch Storage plan date from Storage start request telegraph message
	 * @return		Storage plan date
	 */
	public String getPlanDate ()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE) ;
		return planDate.trim();
	}
	
	//#CM576363
	/**
	 * Fetch Scan Item code1 from Storage start request telegraph message
	 * @return		Item code
	 */
	public String getScanItemCode1 ()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE1, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}

	//#CM576364
	/**
	 * Fetch Scan Item code2 from Storage start request telegraph message
	 * @return		Item code converted from ITF to JAN
	 */
	public String getScanItemCode2 ()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE2, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}	
	
	//#CM576365
	/**
	 * Fetch Case piece type from Storage start request telegraph message
	 * @return		Case piece type(1:Case 2:Piece)
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}

	//#CM576366
	// Package methods -----------------------------------------------

	//#CM576367
	// Protected methods ---------------------------------------------

	//#CM576368
	// Private methods -----------------------------------------------

}
//#CM576369
//end of class


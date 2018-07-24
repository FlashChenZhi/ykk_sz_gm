// $Id: RFTId0731.java,v 1.2 2006/12/07 09:00:06 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576439
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576440
/**
 * Relocation picking result send ID=0731 telegraph message process<BR>
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of Id0731 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0731</td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Consignor name</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>Relocation origin areaNo.		</td><td>3 byte</td><td></td></tr>
 * <tr><td>Origin location no.</td><td>16 byte</td><td></td></tr>
 * <tr><td>Product identification code</td>	<td>16 byte</td><td>Not used</td></tr>
 * <tr><td>Item code</td>		<td>16 byte</td><td>Not used</td></tr>
 * <tr><td>JAN code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>Qty per bundle</td>		<td>6 byte</td>	<td></td></tr>
 * <tr><td>Qty per case</td>		<td>6 byte</td>	<td></td></tr>
 * <tr><td>Relocation picking result qty</td>	<td>9 byte</td>	<td></td></tr>
 * <tr><td>Case piece type</td><td>1 byte</td><td>Not used</td></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td>	<td></td></tr>
 * <tr><td>work time</td>		<td>5 byte</td>	<td>Seconds</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:06 $
 * @author  $Author: suresh $
 */
public class RFTId0731 extends RecvIdMessage
{
	//#CM576441
	// Class fields --------------------------------------------------
	//#CM576442
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576443
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576444
	/**
	 * Consignor name offset definition
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576445
	/**
	 * Relocation origin area No. offset definition
	 */
	private static final int OFF_SOURCE_AREA_NO = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME ;

	//#CM576446
	/**
	 * Origin location no. offset definition
	 */
	private static final int OFF_SOURCE_LOCATION_NO = OFF_SOURCE_AREA_NO + LEN_AREA_NO ;
	
	//#CM576447
	/**
	 * Product identification code offset definition
	 */
	private static final int OFF_ITEM_ID = OFF_SOURCE_LOCATION_NO + LEN_LOCATION;
	
	//#CM576448
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;
	
	//#CM576449
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM576450
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM576451
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM576452
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM576453
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM576454
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM576455
	/**
	 * Relocation picking result qty offset definition
	 */
	private static final int OFF_MOVEMENT_RESULT_QTY = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576456
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_MOVEMENT_RESULT_QTY + LEN_RESULT_QTY ;
	
	//#CM576457
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;
	
	//#CM576458
	/**
	 * work time offset definition
	 */
	private static final int OFF_WORK_SECONDS = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	
	//#CM576459
	/**
	 * ID number
	 */
	public static final String ID = "0731";
		
	//#CM576460
	// Class variables -----------------------------------------------

	//#CM576461
	// Class method --------------------------------------------------
	//#CM576462
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:06 $") ;
	}

	//#CM576463
	// Constructors --------------------------------------------------
	//#CM576464
	/**
	 * Constructor
	 */
	public RFTId0731 ()
	{
		super() ;
		offEtx = OFF_WORK_SECONDS + LEN_WORK_TIME;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576465
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0731 Relocation picking result send (ID=0731) telegraph message
	 */
	public RFTId0731 (byte[] rftId0731)
	{
		super() ;
		setReceiveMessage(rftId0731) ;
	}

	//#CM576466
	// Public methods ------------------------------------------------
	//#CM576467
	/**
	 * Fetch Worker code from Relocation picking result send telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576468
	/**
	 * Fetch Consignor code from Relocation picking result send telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576469
	/**
	 * Fetch Consignor name from Relocation picking result send telegraph message
	 * @return  consignorName  Consignor name
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME) ;
		return consignorName.trim();
	}
	
	
	//#CM576470
	/**
	 * Fetch Relocation origin area no. from Relocation picking result send telegraph message
	 * @return		Relocation origin areaNo.
	 */
	public String getSourceAreaNo ()
	{
		String areano = getFromBuffer(OFF_SOURCE_AREA_NO, LEN_AREA_NO) ;
		return areano.trim();
	}
	
	//#CM576471
	/**
	 * Fetch Origin location from Relocation picking result send telegraph message
	 * @return		Origin location
	 */
	public String getSourceLocationNo ()
	{
		String planDate = getFromBuffer(OFF_SOURCE_LOCATION_NO, LEN_LOCATION) ;
		return planDate.trim();
	}
	
	//#CM576472
	/**
	 * Fetch Product identification code from Relocation picking result send telegraph message
	 * @return		Product identification code
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_ITEM_ID, LEN_ITEM_ID) ;
		return itemId.trim();
	}
	
	//#CM576473
	/**
	 * Fetch Item code from Relocation picking result send telegraph message
	 * @return		Item code
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	//#CM576474
	/**
	 * Fetch JAN code from Relocation picking result send telegraph message
	 * @return		JAN code
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	//#CM576475
	/**
	 * Fetch Bundle ITF from Relocation picking result send telegraph message
	 * @return		Bundle ITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleITF.trim();
	}
	
	//#CM576476
	/**
	 * Fetch Case ITF from Relocation picking result send telegraph message
	 * @return		Case ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return ITF.trim();
	}
	
	//#CM576477
	/**
	 * Fetch Item name from Relocation picking result send telegraph message
	 * @return		Item name
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	//#CM576478
	/**
	 * Fetch Qty per bundle from Relocation picking result send telegraph message
	 * @return		Qty per bundle
	 */
	public int getBundleEnteringQty ()
	{
		int bundleEnteringQty = getIntFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
		return bundleEnteringQty;
	}
	
	//#CM576479
	/**
	 * Fetch Qty per case from Relocation picking result send telegraph message
	 * @return		Qty per case
	 */
	public int getEnteringQty ()
	{
		int enteringQty = getIntFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
		return enteringQty;
	}
	
	//#CM576480
	/**
	 * Fetch Relocation picking result qty from Relocation picking result send telegraph message
	 * @return		Relocation picking result qty
	 */
	public int getMovementResultQty ()
	{
		int movementResultQty = getIntFromBuffer(OFF_MOVEMENT_RESULT_QTY, LEN_RESULT_QTY) ;
		return movementResultQty;
	}
	
	//#CM576481
	/**
	 * Fetch Case piece type from Relocation picking result send telegraph message
	 * @return		Case piece type(1:Case 2:Piece)
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}

	//#CM576482
	/**
	 * Fetch Expiry date from Relocation picking result send telegraph message
	 * @return		Expiry date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE) ;
		return useByDate.trim();
	}

	//#CM576483
	/**
	 * Fetch work time from Relocation picking result send telegraph message
	 * @return  work time
	 */
	public int getWorkSeconds()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}
	
	//#CM576484
	// Package methods -----------------------------------------------
	//#CM576485
	// Protected methods ---------------------------------------------
	//#CM576486
	// Private methods -----------------------------------------------

}
//#CM576487
//end of class


// $Id: RFTId0741.java,v 1.2 2006/12/07 09:00:06 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576517
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576518
/**
 * Relocation Storage result send ID=00741 telegraph message process
 * 
 * <p>
  * <table border="1">
 * <CAPTION>Structure of Id0741 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0741</td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Consignor name</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>Origin location no.</td><td>16 byte</td><td></td></tr>
 * <tr><td>Relocation Job no.</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>Item code</td>		<td>16 byte</td><td>Not used</td></tr>
 * <tr><td>JAN code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>Qty per bundle</td>		<td>6 byte</td>	<td></td></tr>
 * <tr><td>Qty per case</td>		<td>6 byte</td>	<td></td></tr>
 * <tr><td>Relocation storage possible qty</td>	<td>9 byte</td>	<td></td></tr>
 * <tr><td>Case piece type</td><td>1 byte</td><td>Not used</td></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td>	<td></td></tr>
 * <tr><td>Relocation Job no.</td>		<td>8 byte</td>	<td></td></tr>
 * <tr><td>Actual relocation storage result qty</td>	<td>9 byte</td>	<td></td></tr>
 * <tr><td>Destination location no..</td><td>16 byte</td><td></td></tr>
 * <tr><td>work time</td>		<td>5 byte</td>	<td>Seconds</td></tr>
 * <tr><td>completion flag</td>		<td>1 byte</td><td>0:Commit 9:Cancel</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td><td>0x03</td></tr>
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
public class RFTId0741 extends RecvIdMessage
{
	//#CM576519
	// Class fields --------------------------------------------------
	//#CM576520
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576521
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576522
	/**
	 * Consignor name offset definition
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576523
	/**
	 * Relocation origin area No. offset definition
	 */
	private static final int OFF_SOURCE_AREA_NO = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME ;
	
	//#CM576524
	/**
	 * Origin location no. offset definition
	 */
	private static final int OFF_SOURCE_LOCATION_NO = OFF_SOURCE_AREA_NO + LEN_AREA_NO ;
	
	//#CM576525
	/**
	 * Relocation Job no. offset definition
	 */
	private static final int OFF_MOVE_JOB_NO = OFF_SOURCE_LOCATION_NO + LEN_LOCATION ;
	
	//#CM576526
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_MOVE_JOB_NO + LEN_MOVE_JOB_NO ;
	
	//#CM576527
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM576528
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM576529
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM576530
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	//#CM576531
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM576532
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM576533
	/**
	 * Relocation storage possible qty offset definition
	 */
	private static final int OFF_MOVEMENT_INSTORE_ABLE_QTY = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576534
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_MOVEMENT_INSTORE_ABLE_QTY + LEN_PLAN_QTY ;
	
	//#CM576535
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;

	//#CM576536
	/**
	 * Actual relocation storage result qty offset definition
	 */
	private static final int OFF_MOVEMENT_INSTORE_RESULT_QTY = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;
	
	//#CM576537
	/**
	 * Relocation destination area no. offset definition
	 */
	private static final int OFF_DEST_AREA_NO = OFF_MOVEMENT_INSTORE_RESULT_QTY + LEN_RESULT_QTY ;
	
	//#CM576538
	/**
	 * Destination location no. offset definition
	 */
	private static final int OFF_DEST_LOCATION_NO = OFF_DEST_AREA_NO + LEN_AREA_NO ;
	
	//#CM576539
	/**
	 * work time offset definition
	 */
	private static final int OFF_WORK_SECONDS = OFF_DEST_LOCATION_NO + LEN_LOCATION;
	
	//#CM576540
	/**
	 * completion flag offset definition
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_WORK_SECONDS + LEN_WORK_TIME;

	//#CM576541
	/**
	 * ID number
	 */
	public static final String ID = "0741";

	//#CM576542
	/**
	 * completion flag Normal default value
	 */
	public static final String COMPLETION_FLAG_NORMAL = "0" ;
		
	//#CM576543
	/**
	 * completion flag Cancel default value
	 */
	public static final String COMPLETION_FLAG_CANCEL = "9" ;

	//#CM576544
	// Class variables -----------------------------------------------
	//#CM576545
	// Class method --------------------------------------------------
	//#CM576546
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:06 $") ;
	}

	//#CM576547
	// Constructors --------------------------------------------------
	//#CM576548
	/**
	 * Constructor
	 */
	public RFTId0741 ()
	{
		super() ;
		offEtx = OFF_WORK_SECONDS + LEN_WORK_TIME;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576549
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0741 Relocation storageStorage result send ID=0741 telegraph message
	 */
	public RFTId0741 (byte[] rftId0741)
	{
		super() ;
		setReceiveMessage(rftId0741) ;
	}

	//#CM576550
	// Public methods ------------------------------------------------
	//#CM576551
	/**
	 * Relocation Fetch Worker code from Storage result send telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576552
	/**
	 * Relocation Fetch Consignor code from Storage result send telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576553
	/**
	 * Fetch Consignor name
	 * @return  consignorName  Consignor name
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME, OFF_CONSIGNOR_NAME) ;
		return consignorName.trim();
	}
	
	//#CM576554
	/**
	 * Fetch Relocation origin area from Relocation Storage result send telegraph message
	 * @return		Relocation origin area
	 */
	public String getSourceAreaNo ()
	{
		String areaNo = getFromBuffer(OFF_SOURCE_AREA_NO, LEN_AREA_NO) ;
		return areaNo.trim();
	}
	
	//#CM576555
	/**
	 * Fetch Origin location from Relocation Storage result send telegraph message
	 * @return		Origin location
	 */
	public String getSourceLocationNo ()
	{
		String planDate = getFromBuffer(OFF_SOURCE_LOCATION_NO, LEN_LOCATION) ;
		return planDate.trim();
	}
	
	//#CM576556
	/**
	 * Fetch Relocation Job no. from Relocation Storage result send telegraph message
	 */
	public String getMoveJobNo()
	{
		String moveJobNo = getFromBuffer(OFF_MOVE_JOB_NO, LEN_MOVE_JOB_NO);
		return moveJobNo.trim();
	}

	//#CM576557
	/**
	 * Relocation Fetch Item code from Storage result send telegraph message
	 * @return		Item code
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	//#CM576558
	/**
	 * Relocation Fetch JAN code from Storage result send telegraph message
	 * @return		JAN code
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	//#CM576559
	/**
	 * Relocation Fetch Bundle ITF from Storage result send telegraph message
	 * @return		Bundle ITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleITF.trim();
	}
	
	//#CM576560
	/**
	 * Relocation Fetch Case ITF from Storage result send telegraph message
	 * @return		Case ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return ITF.trim();
	}
	
	//#CM576561
	/**
	 * Relocation Fetch Item name from Storage result send telegraph message
	 * @return		Item name
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	//#CM576562
	/**
	 * Relocation Fetch Qty per bundle from Storage result send telegraph message
	 * @return		Qty per bundle
	 */
	public String getBundleEnteringQty ()
	{
		String bundleEnteringQty = getFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
		return bundleEnteringQty.trim();
	}
	
	//#CM576563
	/**
	 * Relocation Fetch Qty per case from Storage result send telegraph message
	 * @return		Qty per case
	 */
	public String getEnteringQty ()
	{
		String enteringQty = getFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
		return enteringQty.trim();
	}
	
	//#CM576564
	/**
	 * Fetch Relocation storage possible qty from Relocation Storage result send telegraph message
	 * @return		Relocation storage possible qty
	 */
	public int getMovementInstoreAbleQty ()
	{
		int movementInstoreAbleQty = getIntFromBuffer(OFF_MOVEMENT_INSTORE_ABLE_QTY, LEN_PLAN_QTY) ;
		return movementInstoreAbleQty;
	}

	//#CM576565
	/**
	 * Relocation Fetch Case piece type from Storage result send telegraph message
	 * @return		Case piece type
	 * 				1:Case 
	 * 				2:Piece 
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	//#CM576566
	/**
	 * Fetch Expiry date from Relocation Storage result send telegraph message
	 * @return		Expiry date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE) ;
		return useByDate.trim();
	}

	//#CM576567
	/**
	 * Fetch Actual relocation storage result qty from Relocation Storage result send telegraph message
	 * @return		Actual relocation storage result qty
	 */
	public int getMovementInstoreResultQty ()
	{
		int movementInstoreResultQty = getIntFromBuffer(OFF_MOVEMENT_INSTORE_RESULT_QTY, LEN_RESULT_QTY) ;
		return movementInstoreResultQty;
	}
	
	//#CM576568
	/**
	 * Fetch Destination area from Relocation Storage result send telegraph message
	 * @return		Relocation origin area
	 */
	public String getDestAreaNo ()
	{
		String areaNo = getFromBuffer(OFF_DEST_AREA_NO, LEN_AREA_NO) ;
		return areaNo.trim();
	}
	
	
	//#CM576569
	/**
	 * Fetch Destination location from Relocation Storage result send telegraph message
	 * @return		Destination location
	 */
	public String getDestLocationNo ()
	{
		String destLocationNo = getFromBuffer(OFF_DEST_LOCATION_NO, LEN_LOCATION) ;
		return destLocationNo.trim();
	}
	
	//#CM576570
	/**
	 * Relocation Fetch work time from Storage result send telegraph message
	 * @return	work time
	 */
	public int getWorkSeconds()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}
	
	//#CM576571
	/**
	 * Relocation Fetch completion flag from Storage result send telegraph message
	 * @return		completion flag
	 */
	public String getCompletionFlag ()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG) ;
		return completionFlag.trim();
	}
	
	//#CM576572
	// Package methods -----------------------------------------------
	//#CM576573
	// Protected methods ---------------------------------------------
	//#CM576574
	// Private methods -----------------------------------------------

}
//#CM576575
//end of class


// $Id: RFTId0231.java,v 1.2 2006/12/07 09:00:07 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576370
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576371
/**
 * Storage result send ID=0231 telegraph message process<BR>
 * 
 * ID0231 telegraph message
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegraph message Id0231</CAPTION>
 * <TR><TH>Item name</TH>					<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>						<td>1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>					<td>4 byte</td> <td></td></tr>
 * <tr><td>ID</td>						<td>4 byte</td> <td>0231</td></tr>
 * <tr><td>Handy send time</td>		<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>			<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>			<td>3 byte</td> <td></td></tr>
 * <tr><td>Worker code</td>			<td>4 byte</td> <td></td></tr>
 * <tr><td>Consignor code</td>				<td>16 byte</td><td></td></tr>
 * <tr><td>Storage plan date</td>				<td>8 byte</td> <td>YYYYMMDD</td></tr>
 * <tr><td>Storage instructed area no.</td>		<td>3 byte</td> <td></td></tr>
 * <tr><td>Storage instruction location no.</td> <td>16 byte</td><td></td></tr>
 * <tr><td>Product identification code</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Item code</td>				<td>16 byte</td><td></td></tr>
 * <tr><td>JAN code</td>				<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>				<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>				<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>				<td>40 byte</td><td></td></tr>
 * <tr><td>Qty per bundle</td>				<td>6 byte</td> <td></td></tr>
 * <tr><td>Qty per case</td>				<td>6 byte</td> <td></td></tr>
 * <tr><td>Position</td>					<td>6 byte</td> <td></td></tr>
 * <tr><td>Lot no.</td>				<td>10 byte</td><td></td></tr>
 * <tr><td>Expiry date</td>				<td>8 byte</td> <td></td></tr>
 * <tr><td>Production day</td>					<td>8 byte</td> <td></td></tr>
 * <tr><td>Storage plan qty</td>				<td>9 byte</td> <td></td></tr>
 * <tr><td>Storage complete qty</td>				<td>9 byte</td> <td></td></tr>
 * <tr><td>Storage complete area no.</td>		<td>3 byte</td> <td></td></tr>
 * <tr><td>Storage complete location no.</td> <td>16 byte</td><td></td></tr>
 * <tr><td>Case piece type</td>		<td>1 byte</td> <td>1:Case  2:Piece  3:None </td></tr>
 * <tr><td>work time</td>				<td>5 byte</td> <td></td></tr>
 * <tr><td>miss count</td>				<td>5 byte</td> <td></td></tr>
 * <tr><td>completion flag</td>				<td>1 byte</td> <td>0:Normal  1:Shortage  2:Pending  9:Cancel</td></tr>
 * <tr><td>ETX</td>						<td>1 byte</td> <td></td></tr>
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
public class RFTId0231 extends RecvIdMessage

{

	//#CM576372
	// Class fields --------------------------------------------------
	//#CM576373
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;

	//#CM576374
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576375
	/**
	 * Storage plan date offset definition
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;

	//#CM576376
	/**
	 * Storage instructed area no. offset definition
	 */
	private static final int OFF_STORAGE_AREA_NO = OFF_PLAN_DATE + LEN_PLAN_DATE ;

	//#CM576377
	/**
	 * Storage instruction location no. offset definition
	 */
	private static final int OFF_STORAGE_LOCATION_NO = OFF_STORAGE_AREA_NO + LEN_AREA_NO ;

	//#CM576378
	/**
	 * Product identification code offset definition
	 */
	private static final int OFF_ITEM_ID = OFF_STORAGE_LOCATION_NO + LEN_LOCATION ;

	//#CM576379
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;

	//#CM576380
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;

	//#CM576381
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;

	//#CM576382
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;

	//#CM576383
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;

	//#CM576384
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;

	//#CM576385
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;

	//#CM576386
	/**
	 * Position offset definition
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576387
	/**
	 * Lot no. offset definition
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT ;

	//#CM576388
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO ;

	//#CM576389
	/**
	 * Production day offset definition
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;

	//#CM576390
	/**
	 * Storage plan qty offset definition
	 */
	private static final int OFF_STORAGE_PLAN_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE ;

	//#CM576391
	/**
	 * Storage complete qty offset definition
	 */
	private static final int OFF_STORAGE_COMPLETION_QTY = OFF_STORAGE_PLAN_QTY + LEN_PLAN_QTY ;

	//#CM576392
	/**
	 * Storage complete area no. offset definition
	 */
	private static final int OFF_STORAGE_COMPLETION_AREA_NO = OFF_STORAGE_COMPLETION_QTY + LEN_RESULT_QTY ;

	//#CM576393
	/**
	 * Storage complete location no. offset definition
	 */
	private static final int OFF_STORAGE_COMPLETION_LOCATION_NO = OFF_STORAGE_COMPLETION_AREA_NO + LEN_AREA_NO ;

	//#CM576394
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_STORAGE_COMPLETION_LOCATION_NO + LEN_LOCATION ;

	//#CM576395
	/**
	 * work time offset definition
	 */
	private static final int OFF_WORK_SECONDS = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;

	//#CM576396
	/**
	 * miss count offset definition
	 */
	private static final int OFF_MISS_COUNT = OFF_WORK_SECONDS + LEN_WORK_TIME;

	//#CM576397
	/**
	 * completion flag offset definition
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_MISS_COUNT + LEN_MISS_COUNT ;

	//#CM576398
	/**
	 * completion flag Normal default value
	 */
	public static final String COMPLETION_FLAG_NORMAL = "0" ;
	
	//#CM576399
	/**
	 * completion flag Shortage default value
	 */
	public static final String COMPLETION_FLAG_LACK = "1" ;
	
	//#CM576400
	/**
	 * completion flag Pending default value
	 */
	public static final String COMPLETION_FLAG_DIVIDE = "2" ;
	
	//#CM576401
	/**
	 * completion flag Cancel default value
	 */
	public static final String COMPLETION_FLAG_CANCEL = "9" ;
	
	//#CM576402
	/**
	 * ID number
	 */
	public static final String ID = "0231";

	//#CM576403
	// Class variables -----------------------------------------------

	//#CM576404
	// Class method --------------------------------------------------
	//#CM576405
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:07 $") ;
	}

	//#CM576406
	// Constructors --------------------------------------------------
	//#CM576407
	/**
	 * Constructor
	 */
	public RFTId0231 ()
	{
		super() ;
		offEtx = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576408
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0231 Storage result send ID=0231 telegraph message
	 */
	public RFTId0231 (byte[] rftId0231)
	{
		super() ;
		setReceiveMessage(rftId0231) ;
	}

	//#CM576409
	// Public methods ------------------------------------------------
	//#CM576410
	/**
	 * Fetch Worker code from Storage result send telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576411
	/**
	 * Fetch Consignor code from Storage result send telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576412
	/**
	 * Fetch Storage plan date from Storage result send telegraph message
	 * @return		Storage plan date
	 */
	public String getPlanDate ()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE) ;
		return planDate.trim();
	}
	
	//#CM576413
	/**
	 * Fetch Storage complete area no. from Storage result send telegraph message
	 * @return		Storage instructed area no.
	 */
	public String getStorageAreaNo ()
	{
		String storageAreaNo = getFromBuffer(OFF_STORAGE_AREA_NO, LEN_AREA_NO) ;
		return storageAreaNo.trim();
	}
	
	//#CM576414
	/**
	 * Fetch Storage complete location no. from Storage result send telegraph message
	 * @return		Storage instruction location no.
	 */
	public String getStrageLocationNo ()
	{
		String strageLocationNo = getFromBuffer(OFF_STORAGE_LOCATION_NO, LEN_LOCATION) ;
		return strageLocationNo.trim();
	}
	
	//#CM576415
	/**
	 * Fetch Product identification code from Storage result send telegraph message
	 * @return		Product identification code
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_ITEM_ID, LEN_ITEM_ID) ;
		return itemId.trim();
	}
	
	//#CM576416
	/**
	 * Fetch Item code from Storage result send telegraph message
	 * @return		Item code
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	//#CM576417
	/**
	 * Fetch JAN code from Storage result send telegraph message
	 * @return		JAN code
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	//#CM576418
	/**
	 * Fetch Bundle ITF from Storage result send telegraph message
	 * @return		Bundle ITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleITF.trim();
	}
	
	//#CM576419
	/**
	 * Fetch Case ITF from Storage result send telegraph message
	 * @return		Case ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return ITF.trim();
	}
	
	//#CM576420
	/**
	 * Fetch Item name from Storage result send telegraph message
	 * @return		Item name
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	//#CM576421
	/**
	 * Fetch Qty per bundle from Storage result send telegraph message
	 * @return		Qty per bundle
	 */
	public String getBundleEnteringQty ()
	{
		String bundleEnteringQty = getFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
		return bundleEnteringQty.trim();
	}
	
	//#CM576422
	/**
	 * Fetch Qty per case from Storage result send telegraph message
	 * @return		Qty per case
	 */
	public String getEnteringQty ()
	{
		String enteringQty = getFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
		return enteringQty.trim();
	}
	
	//#CM576423
	/**
	 * Fetch Position from Storage result send telegraph message
	 * @return		Position
	 */
	public String getUnit ()
	{
		String unit = getFromBuffer(OFF_UNIT, LEN_UNIT) ;
		return unit.trim();
	}
	
	//#CM576424
	/**
	 * Fetch Lot no. from Storage result send telegraph message
	 * @return		Lot no.
	 */
	public String getLotNo ()
	{
		String lotNo = getFromBuffer(OFF_LOT_NO, LEN_LOT_NO) ;
		return lotNo.trim();
	}
	
	//#CM576425
	/**
	 * Fetch Expiry date from Storage result send telegraph message
	 * @return		Expiry date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE) ;
		return useByDate.trim();
	}
	
	//#CM576426
	/**
	 * Fetch Production day from Storage result send telegraph message
	 * @return		Production day
	 */
	public String getManufactureDate ()
	{
		String manufactureDate = getFromBuffer(OFF_MANUFACTURE_DATE, LEN_MANUFACTURE_DATE) ;
		return manufactureDate.trim();
	}
	
	//#CM576427
	/**
	 * Fetch Storage plan qty from Storage result send telegraph message
	 * @return		Storage plan qty
	 */
	public String getStragePlanQty ()
	{
		String storagePlanQty = getFromBuffer(OFF_STORAGE_PLAN_QTY, LEN_PLAN_QTY) ;
		return storagePlanQty.trim();
	}
	
	//#CM576428
	/**
	 * Fetch Storage complete qty from Storage result send telegraph message
	 * @return		Storage complete qty
	 */
	public String getStorageCompletionQty ()
	{
		String storageCompletionQty = getFromBuffer(OFF_STORAGE_COMPLETION_QTY, LEN_RESULT_QTY) ;
		return storageCompletionQty.trim();
	}
	
	//#CM576429
	/**
	 * Fetch Storage complete area no. from Storage result send telegraph message
	 * @return		Storage complete area no.
	 */
	public String getStorageCompletionAreaNo ()
	{
		String storageCompletionAreaNo = getFromBuffer(OFF_STORAGE_COMPLETION_AREA_NO, LEN_AREA_NO) ;
		return storageCompletionAreaNo.trim();
	}
	
	//#CM576430
	/**
	 * Fetch Storage complete location no. from Storage result send telegraph message
	 * @return		Storage complete location no.
	 */
	public String getStrorageCompletionLocationNo ()
	{
		String storageCompletionLocationNo = getFromBuffer(OFF_STORAGE_COMPLETION_LOCATION_NO, LEN_LOCATION) ;
		return storageCompletionLocationNo.trim();
	}
	
	//#CM576431
	/**
	 * Fetch Case piece type from Storage result send telegraph message
	 * @return		Case piece type(1:Case 2:Piece)
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	//#CM576432
	/**
	 * Fetch work time from Storage result send telegraph message
	 * @return		work time
	 */
	public int getWorkSeconds ()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}

	//#CM576433
	/**
	 * Fetch miss count from Storage result send telegraph message
	 * @return		miss count
	 */
	public int getMissCount ()
	{
		int count = getIntFromBuffer(OFF_MISS_COUNT, LEN_MISS_COUNT);
		return count;
	}
	
	//#CM576434
	/**
	 * Fetch completion flag from Storage result send telegraph message
	 * @return		completion flag(0:Complete 1:Shortage  2:Pending  9:Cancel)
	 */
	public String getCompletionFlag ()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG) ;
		return completionFlag.trim();
	}
	
	//#CM576435
	// Package methods -----------------------------------------------

	//#CM576436
	// Protected methods ---------------------------------------------

	//#CM576437
	// Private methods -----------------------------------------------

}
//#CM576438
//end of class


// $Id: RFTId0641.java,v 1.2 2006/09/27 03:00:35 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM10283
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10284
/**
 * Unplanned Picking Result send ID=0641 electronic statementを processします
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id0631</CAPTION>
 * <TR><TH>Item Name</TH>			<TH>Length</TH>	<TH>contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0641</td></tr>
 * <tr><td>Handy Transmission time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server Transmission time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy terminalNo.</td>	<td>3 byte</td>	<td>  </td></tr>
 * <tr><td></td>	<td>4 byte</td>	<td>  </td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td>  </td></tr>
 * <tr><td>JANCode</td>		<td>16 byte</td><td>Front aligned</td></tr>
 * <tr><td>Case/PieceDivision</td><td>1 byte</td><td>1：Case 2：Piece 3：Not Designated</td></tr>
 * <tr><td>Consignor name</td>		<td>40 byte</td><td>  </td></tr>
 * <tr><td>Set unit key</td>	<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Area No.</td>		<td>3 byte</td>	<td>  </td></tr>
 * <tr><td>Location No.</td>	<td>16 byte</td><td>  </td></tr>
 * <tr><td>under replenishment flag</td>	<td>1 byte</td>	<td>Not available</td></tr>
 * <tr><td>AggregationWork No.</td>		<td>16 byte</td><td>Set AggregationWork No. in Front aligned</td></tr>
 * <tr><td>Item code</td>		<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td><td>  </td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td><td>  </td></tr>
 * <tr><td>Item name</td>		<td>40 byte</td><td>  </td></tr>
 * <tr><td>Item category</td>		<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>packed qty per bundle</td>		<td>6 byte</td>	<td>  </td></tr>
 * <tr><td>Packed qty per case</td>		<td>6 byte</td>	<td>  </td></tr>
 * <tr><td>unit</td>			<td>6 byte</td>	<td>  </td></tr>
 * <tr><td>Lot No.</td>		<td>10 byte</td><td>Not available</td></tr>
 * <tr><td>Expiry Date</td>		<td>8 byte</td>	<td>  </td></tr>
 * <tr><td>manufactured date</td>			<td>8 byte</td>	<td>Not available</td></tr>
 * <tr><td>possible picking qty</td>		<td>9 byte</td>	<td>Piece conversion</td></tr>
 * <tr><td>picking Result file</td>		<td>9 byte</td>	<td>Piece conversion</td></tr>
 * <tr><td>result expiry date</td>	<td>8 byte</td>	<td>  </td></tr>
 * <tr><td>Work time</td>		<td>5 byte</td>	<td>Second No.</td></tr> 
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/15</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:35 $
 * @author  $Author: suresh $
 * 
 */

public class RFTId0641 extends RecvIdMessage {
	//#CM10285
	// Class fields --------------------------------------------------
	//#CM10286
	/**
	 * Define offset for the person in charge code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM10287
	/**
	 * Define offset for the Consignor code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM10288
	/**
	 * Define offset for JANCode.
	 */
	private static final int OFF_JAN_CODE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM10289
	/**
	 * Define offset for the Case/Piece division.
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_JAN_CODE + LEN_JAN_CODE;

	//#CM10290
	/**
	 * Define offset for the Consignor name.
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;
	
	//#CM10291
	/**
	 * Define offset for the setting unit key.
	 */
	private static final int OFF_SET_UNIT_KEY = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM10292
	/**
	 * Define offset for Area No.
	 */
	private static final int OFF_AREA_NO = OFF_SET_UNIT_KEY + LEN_SET_UNIT_KEY;

	//#CM10293
	/**
	 * Define offset for the Location No..
	 */
	private static final int OFF_LOCATION_NO = OFF_AREA_NO + LEN_AREA_NO ;

	//#CM10294
	/**
	 * Define offset for the replenishment in process flag.
	 */
	private static final int OFF_REPLENISHING_FLAG = OFF_LOCATION_NO + LEN_LOCATION;
	
	//#CM10295
	/**
	 * Define offset for the item distinct code.
	 */
	private static final int OFF_ITEM_ID = OFF_REPLENISHING_FLAG + LEN_REPLENISHING_FLAG ;

	//#CM10296
	/**
	 * Define offset for the item code.
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;

	//#CM10297
	/**
	 * Define offset for Bundle ITF
	 */
	private static final int OFF_BUNDLE_ITF = OFF_ITEM_CODE + LEN_ITEM_CODE ;

	//#CM10298
	/**
	 * Define offset for Case ITF
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;

	//#CM10299
	/**
	 * Define offset for the item name.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM10300
	/**
	 * Define offset for the item classification.
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME ;

	//#CM10301
	/**
	 * Define offset for packed qty per bundle
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE ;

	//#CM10302
	/**
	 * Define offset for Packed qty per case.
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;

	//#CM10303
	/**
	 * Define offset for the unit.
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM10304
	/**
	 * Define offset for Lot No.
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT ;

	//#CM10305
	/**
	 * Define offset for the expiry date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO ;

	//#CM10306
	/**
	 * Define offset for the manufactured date.
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;

	//#CM10307
	/**
	 * Define offset for the possible picking qty.
	 */
	private static final int OFF_STOCK_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;	
	
	//#CM10308
	/**
	 * Define offset for the picking result qty.
	 */
	private static final int OFF_RESULT_QTY = OFF_STOCK_QTY + LEN_STOCK_QTY;	
	
	//#CM10309
	/**
	 * Define offset for the result expiry date.
	 */
	private static final int OFF_RESULT_USE_BY_DATE = OFF_RESULT_QTY + LEN_RESULT_QTY ;
	
	//#CM10310
	/**
	 * Define offset for Work time
	 */
	private static final int OFF_WORK_SECONDS = OFF_RESULT_USE_BY_DATE + LEN_USE_BY_DATE;
	
	//#CM10311
	/**
	 * ID No.
	 */
	public static final String ID = "0641";
	
	//#CM10312
	// Class variables -----------------------------------------------
	//#CM10313
	// Class method --------------------------------------------------
	//#CM10314
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/09/27 03:00:35 $") ;
	}

	//#CM10315
	// Constructors --------------------------------------------------
	//#CM10316
	/**
	 * Constructors
	 */
	public RFTId0641 ()
	{
		super() ;
		offEtx = OFF_WORK_SECONDS + LEN_WORK_TIME;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM10317
	/**
	 * Pass the electronic statement received from RFT to Constructors.
	 * @param rftId0153 Unplanned picking result sending ID=0641 electronic statement
	 */
	public RFTId0641 (byte[] rftId0641)
	{
		super() ;
		setReceiveMessage(rftId0641) ;
	}

	//#CM10318
	// Public methods ------------------------------------------------
	//#CM10319
	/**
	 * Obtain the person in charge code from the unplanned picking result sending electronic statement.
	 * @return		
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM10320
	/**
	 * Obtain the Consignor code from the unplanned picking result sending electronic statement.
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM10321
	/**
	 * Obtain the JAN code from the unplanned picking result sending electronic statement.
	 * @return		JANCode
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	//#CM10322
	/**
	 * Obtain the Case/Piece division from the unplanned picking result sending electronic statement.
	 * @return		Case/PieceDivision
	 * 				1:Case
	 * 				2:Piece
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	//#CM10323
	/**
	 * Obtain the Consignor name from the unplanned picking result sending electronic statement.
	 * @return     Consignor name
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME);
		return consignorName.trim();
	}
	
	//#CM10324
	/**
	 * Obtain the set unit key from the unplanned picking result sending electronic statement.
	 * @return  Set unit key
	 */
	public String setUnit ()
	{
		String unit = getFromBuffer(OFF_SET_UNIT_KEY, LEN_SET_UNIT_KEY) ;
		return unit.trim();
	}
	
	//#CM10325
	/**
	 * Obtain the Area No. from the unplanned picking result sending electronic statement.
	 * @return		Area No.
	 */
	public String getAreaNo ()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO) ;
		return areaNo.trim();
	}
	
	//#CM10326
	/**
	 * Obtain the Location No. from the unplanned picking result sending electronic statement.
	 * @return		Location No.
	 */
	public String getLocationNo ()
	{
		String locationNo = getFromBuffer(OFF_LOCATION_NO, LEN_LOCATION) ;
		return locationNo.trim();
	}
	
	//#CM10327
	/**
	 * Obtain the replenishment in process flag from the unplanned picking result sending electronic statement.
	 * @return   Stock Location No.
	 */
	public String setReplenishingFlag ()
	{
		String replenishingFlag = getFromBuffer(OFF_REPLENISHING_FLAG, LEN_REPLENISHING_FLAG) ;
		return replenishingFlag.trim();
	}
	
	//#CM10328
	/**
	 * Obtain the item distinct code from the unplanned picking result sending electronic statement.
	 * @return		Item ID Code
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_ITEM_ID, LEN_ITEM_ID) ;
		return itemId.trim();
	}
	
	//#CM10329
	/**
	 * Obtain the item code from the unplanned picking result sending electronic statement.
	 * @return		Item code
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	//#CM10330
	/**
	 * Obtain the bundle ITF from the unplanned picking result sending electronic statement.
	 * @return		Bundle ITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleITF.trim();
	}
	
	//#CM10331
	/**
	 * Obtain the Case ITF from the unplanned picking result sending electronic statement.
	 * @return		Case ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return ITF.trim();
	}
	
	//#CM10332
	/**
	 * Obtain the item name from the unplanned picking result sending electronic statement.
	 * @return		Item name
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	//#CM10333
	/**
	 * Obtain the item classification from the unplanned picking result sending electronic statement.
	 * @return 	Item category
	 */
	public String getItemCategoryCode ()
	{
		String itemCategoryCode = getFromBuffer(OFF_ITEM_CATEGORY_CODE, LEN_ITEM_CATEGORY_CODE) ;
		return itemCategoryCode.trim();
	}
	
	//#CM10334
	/**
	 * Obtain the packed qty per bundle from the unplanned picking result sending electronic statement.
	 * @return		packed qty per bundle
	 */
	public int getBundleEnteringQty ()
	{
		int bundleEnteringQty = getIntFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
		return bundleEnteringQty;
	}
	
	//#CM10335
	/**
	 * Obtain the packed qty per case from the unplanned picking result sending electronic statement.
	 * @return		Packed qty per case
	 */
	public int getEnteringQty ()
	{
		int enteringQty = getIntFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
		return enteringQty;
	}
	
	//#CM10336
	/**
	 * Obtain the unit from the unplanned picking result sending electronic statement.
	 * @return		unit
	 */
	public String getUnit ()
	{
		String unit = getFromBuffer(OFF_UNIT, LEN_UNIT) ;
		return unit.trim();
	}
	
	//#CM10337
	/**
	 *Obtain the Lot No. from the unplanned picking result sending electronic statement.
	 * @return		Lot No.
	 */
	public String getLotNo ()
	{
		String lotNo = getFromBuffer(OFF_LOT_NO, LEN_LOT_NO) ;
		return lotNo.trim();
	}
	
	//#CM10338
	/**
	 *Obtain the expiry date from the unplanned picking result sending electronic statement.
	 * @return		Expiry Date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE) ;
		return useByDate.trim();
	}
	
	//#CM10339
	/**
	 *Obtain the expiry date from the unplanned picking result sending electronic statement.
	 * @return		Result Expiry Date
	 */
	public String getResultUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_RESULT_USE_BY_DATE, LEN_USE_BY_DATE) ;
		return useByDate.trim();
	}
	
	//#CM10340
	/**
	 * Obtain the manufactured date from the unplanned picking result sending electronic statement.
	 * @return		manufactured date
	 */
	public String getManufactureDate ()
	{
		String manufactureDate = getFromBuffer(OFF_MANUFACTURE_DATE, LEN_MANUFACTURE_DATE) ;
		return manufactureDate.trim();
	}
	
	//#CM10341
	/**
	 * Obtain the possible picking qty from the unplanned picking result sending electronic statement.
	 * @return		possible picking qty
	 */
	public int getStockQty ()
	{
		int stockQty = getIntFromBuffer(OFF_STOCK_QTY, LEN_STOCK_QTY) ;	
		return stockQty;
	}
	
	//#CM10342
	/**
	 * Obtain the picking result qty from the unplanned picking result sending electronic statement.
	 * @return resultQty picking Result file
	 */
	public int getResultQty ()
	{
		int resultQty = getIntFromBuffer(OFF_RESULT_QTY, LEN_RESULT_QTY) ;
		return resultQty;
	}
	
	//#CM10343
	/**
	 * Obtain the work time from the unplanned picking result sending electronic statement.
	 * @return workSecond Work time
	 */
	public int getWorkSecond()
	{
		int workSecond = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSecond;
	}
	//#CM10344
	// Package methods -----------------------------------------------
	//#CM10345
	// Protected methods ---------------------------------------------
	//#CM10346
	// Private methods -----------------------------------------------

}
//#CM10347
//end of class

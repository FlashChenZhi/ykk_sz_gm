// $Id: RFTId0631.java,v 1.2 2006/09/27 03:00:35 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM10224
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10225
/**
 * Execute process of Unplanned storage result sending ID=0631 electronic statement.
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id0631</CAPTION>
 * <TR><TH>Item Name</TH>			<TH>Length</TH>	<TH>contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>		<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td>		<td>Not available</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>		<td>0631</td></tr>
 * <tr><td>Handy Transmission time</td><td>6 byte</td>		<td>HHMMSS</td></tr>
 * <tr><td>Server Transmission time</td>	<td>6 byte</td>		<td>HHMMSS</td></tr>
 * <tr><td>Handy terminalNo.</td>	<td>3 byte</td>		<td>  </td></tr>
 * <tr><td></td>	<td>4 byte</td>		<td>  </td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td>	<td>  </td></tr>
 * <tr><td>Consignor name</td>		<td>40 byte</td>	<td>  </td></tr>
 * <tr><td>Area No.</td>		<td>3 byte</td>		<td>  </td></tr>
 * <tr><td>Location No.</td>	<td>16 byte</td>	<td>  </td></tr>
 * <tr><td>Item ID Code</td>	<td>16 byte</td>	<td>Not available</td></tr>
 * <tr><td>Item code</td>		<td>16 byte</td>	<td>Not available</td></tr>
 * <tr><td>JANCode</td>		<td>16 byte</td>	<td>Front aligned</td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td>	<td>  </td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td>	<td>  </td></tr>
 * <tr><td>Item name</td>		<td>40 byte</td>	<td>  </td></tr>
 * <tr><td>packed qty per bundle</td>		<td>6 byte</td>		<td>  </td></tr>
 * <tr><td>Packed qty per case</td>		<td>6 byte</td>		<td>  </td></tr>
 * <tr><td>unit</td>			<td>6 byte</td>		<td>Not available</td></tr>
 * <tr><td>Lot No.</td>		<td>10 byte</td>	<td>Not available</td></tr>
 * <tr><td>Expiry Date</td>		<td>8 byte</td>		<td>  </td></tr>
 * <tr><td>manufactured date</td>			<td>8 byte</td>		<td>Not available</td></tr>
 * <tr><td>Storage Completed No.</td>		<td>9 byte</td>		<td>  </td></tr>
 * <tr><td>Storage CompletedArea No.</td><td>3 byte</td>	<td>  </td></tr>
 * <tr><td>Storage CompletedLocation No.</td><td>16 byte</td><td>  </td></tr>
 * <tr><td>Case/PieceDivision</td><td>1 byte</td>	<td>1：Case 2：Piece 3：Not Designated</td></tr>
 * <tr><td>Work time</td>		<td>5 byte</td>		<td>Seconds No.</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>		<td>0x03</td></tr>
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
 */
public class RFTId0631 extends RecvIdMessage

{

	//#CM10226
	// Class fields --------------------------------------------------
	//#CM10227
	/**
	 * Define offset for the person in charge code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM10228
	/**
	 * Define offset for the Consignor code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM10229
	/**
	 * Define offset for the Consignor name.
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM10230
	/**
	 * Define offset for the Area No.
	 */
	private static final int OFF_AREA_NO = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;

	//#CM10231
	/**
	 * Define offset for the Location No..
	 */
	private static final int OFF_LOCATION_NO = OFF_AREA_NO + LEN_AREA_NO ;

	//#CM10232
	/**
	 * Define offset for the item distinct code.
	 */
	private static final int OFF_ITEM_ID = OFF_LOCATION_NO + LEN_LOCATION ;

	//#CM10233
	/**
	 * Define offset for the item code.
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;

	//#CM10234
	/**
	 * Define offset for the JANCode.
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;

	//#CM10235
	/**
	 * Define offset for the Bundle ITF.
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;

	//#CM10236
	/**
	 * Define offset for the Case ITF.
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;

	//#CM10237
	/**
	 * Define offset for the item name.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;

	//#CM10238
	/**
	 * Define offset for the packed qty per bundle.
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;

	//#CM10239
	/**
	 * Define offset for the Packed qty per case.
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;

	//#CM10240
	/**
	 * Define offset for the unit.
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM10241
	/**
	 * Define offset for the Lot No.
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT ;

	//#CM10242
	/**
	 * Define offset for the expiry date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO ;

	//#CM10243
	/**
	 * Define offset for the manufactured date.
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;

	//#CM10244
	/**
	 * Define offset for the completed storage qty.
	 */
	private static final int OFF_STORAGE_COMPLETION_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE ;

	//#CM10245
	/**
	 * Define offset for the Completed storage area No..
	 */
	private static final int OFF_STORAGE_COMPLETION_AREA_NO = OFF_STORAGE_COMPLETION_QTY + LEN_RESULT_QTY ;

	//#CM10246
	/**
	 * Define offset for the Completed storage Location No..
	 */
	private static final int OFF_STORAGE_COMPLETION_LOCATION_NO = OFF_STORAGE_COMPLETION_AREA_NO + LEN_AREA_NO ;

	//#CM10247
	/**
	 * Case･Define offset for the Piece division.
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_STORAGE_COMPLETION_LOCATION_NO + LEN_LOCATION ;

	//#CM10248
	/**
	 * Define offset for the Work time
	 */
	private static final int OFF_WORK_SECONDS = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;

	//#CM10249
	/**
	 * ID No.
	 */
	public static final String ID = "0631";
	
	//#CM10250
	// Class variables -----------------------------------------------
	//#CM10251
	// Class method --------------------------------------------------
	//#CM10252
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/09/27 03:00:35 $") ;
	}

	//#CM10253
	// Constructors --------------------------------------------------
	//#CM10254
	/**
	 * Constructors
	 */
	public RFTId0631 ()
	{
		super() ;
		offEtx = OFF_WORK_SECONDS + LEN_WORK_TIME;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM10255
	/**
	 * Pass the electronic statement received from RFT to the Constructors
	 * @param rftId0151 Unplanned Storage CompletedJAN report(StorageCompletionJANReport) ID=0151 electronic statement
	 */
	public RFTId0631 (byte[] rftId0631)
	{
		super() ;
		setReceiveMessage(rftId0631) ;
	}

	//#CM10256
	// Public methods ------------------------------------------------
	//#CM10257
	/**
	 * Obtain the person in charge code from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM10258
	/**
	 * Obtain the Consignor code from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM10259
	/**
	 * Obtain the Consignor name from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return     Consignor name
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME);
		return consignorName.trim();
	}
	
	//#CM10260
	/**
	 * Obtain the Area No. from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Area No.
	 */
	public String getAreaNo ()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO) ;
		return areaNo.trim();
	}
	
	//#CM10261
	/**
	 * Obtain the Location No. from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Location No.
	 */
	public String getLocationNo ()
	{
		String locationNo = getFromBuffer(OFF_LOCATION_NO, LEN_LOCATION) ;
		return locationNo.trim();
	}
	
	//#CM10262
	/**
	 * Obtain the item distinct code from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Item ID Code
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_ITEM_ID, LEN_ITEM_ID) ;
		return itemId.trim();
	}
	
	//#CM10263
	/**
	 * Obtain the item code from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Item code
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	//#CM10264
	/**
	 * Obtain the JAN code from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		JANCode
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	//#CM10265
	/**
	 * Obtain the bundle ITF from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Bundle ITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleITF.trim();
	}
	
	//#CM10266
	/**
	 * Obtain the Case ITF from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Case ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return ITF.trim();
	}
	
	//#CM10267
	/**
	 * Obtain the item name from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Item name
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	//#CM10268
	/**
	 * Obtain the packed qty per bundle from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		packed qty per bundle
	 */
	public String getBundleEnteringQty ()
	{
		String bundleEnteringQty = getFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
		return bundleEnteringQty.trim();
	}
	
	//#CM10269
	/**
	 * Obtain the packed qty per case from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Packed qty per case
	 */
	public String getEnteringQty ()
	{
		String enteringQty = getFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
		return enteringQty.trim();
	}
	
	//#CM10270
	/**
	 * Obtain the unit from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		unit
	 */
	public String getUnit ()
	{
		String unit = getFromBuffer(OFF_UNIT, LEN_UNIT) ;
		return unit.trim();
	}
	
	//#CM10271
	/**
	 * Obtain the Lot No. from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Lot No.
	 */
	public String getLotNo ()
	{
		String lotNo = getFromBuffer(OFF_LOT_NO, LEN_LOT_NO) ;
		return lotNo.trim();
	}
	
	//#CM10272
	/**
	 * Obtain the expiry date from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Expiry Date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE) ;
		return useByDate.trim();
	}
	
	//#CM10273
	/**
	 * Obtain the manufactured date from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		manufactured date
	 */
	public String getManufactureDate ()
	{
		String manufactureDate = getFromBuffer(OFF_MANUFACTURE_DATE, LEN_MANUFACTURE_DATE) ;
		return manufactureDate.trim();
	}
	
	//#CM10274
	/**
	 * Obtain the completed storage qty from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Storage Completed No.
	 */
	public String getStorageCompletionQty ()
	{
		String storageCompletionQty = getFromBuffer(OFF_STORAGE_COMPLETION_QTY, LEN_RESULT_QTY) ;
		return storageCompletionQty.trim();
	}
	
	//#CM10275
	/**
	 * Obtain the Completed storage area No. from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Storage CompletedArea No.
	 */
	public String getStorageCompletionAreaNo ()
	{
		String storageCompletionAreaNo = getFromBuffer(OFF_STORAGE_COMPLETION_AREA_NO, LEN_AREA_NO) ;
		return storageCompletionAreaNo.trim();
	}
	
	//#CM10276
	/**
	 * Obtain the storage complete Location No. from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return		Storage CompletedLocation No.
	 */
	public String getStorageCompletionLocationNo ()
	{
		String storageCompletionLocationNo = getFromBuffer(OFF_STORAGE_COMPLETION_LOCATION_NO, LEN_LOCATION) ;
		return storageCompletionLocationNo.trim();
	}
	
	//#CM10277
	/**
	 * Obtain the Case/Piece division from the unplanned storage result sending(StorageCompletionJANReport) electronic statement.
	 * @return		Case/PieceDivision
	 * 				1:Case
	 * 				2:Piece
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	//#CM10278
	/**
	 * Obtain the work time from the unplanned storage result sending (StorageCompletionJANReport) electronic statement.
	 * @return workSeconds	Work time
	 */
	public int getWorkSeconds()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}
	
	
	//#CM10279
	// Package methods -----------------------------------------------
	//#CM10280
	// Protected methods ---------------------------------------------
	//#CM10281
	// Private methods -----------------------------------------------

}
//#CM10282
//end of class


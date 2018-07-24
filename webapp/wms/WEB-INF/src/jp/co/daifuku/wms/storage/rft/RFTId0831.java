// $Id: RFTId0831.java,v 1.2 2006/12/07 09:00:05 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576607
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576608
/**
 * Inventory result send ID=0831 telegraph message process
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of ID0831 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0094</td></tr>
 * <tr><td>Handy send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no.</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Consignor name</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>Area no.</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>Inventory location</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>Product identification code</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>Item code</td>		<td>16 byte</td> </TD>Not used</TD></tr>
 * <tr><td>JAN code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>Qty per bundle</td>		<td>6 byte</td><td></td></tr>
 * <tr><td>Qty per case</td>		<td>6 byte</td><td></td></tr>
 * <tr><td>Stock qty</td>			<td>9 byte</td><td></td></tr>
 * <tr><td>Original Inventory qty</td>		<td>9 byte</td><td></td></tr>
 * <tr><td>Inventory result qty</td>		<td>9 byte</td><td></td></tr>
 * <tr><td>Case piece type</td>	<td>1 byte</td>	<TD>3:None </TD></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td><td></td></tr>
 * <tr><td>New stock create flag</td>	<td>1 byte</td><TD>0:disable create<BR>1:enable create</TD></tr>
 * <tr><td>work time</td>			<td>5 byte</td>	<td>Seconds</td></tr>
 * <tr><td>completion flag</td>		<td>1 byte</td><TD>0:New<BR>1:Overwrite<BR>2:Add</TD></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:05 $
 * @author  $Author: suresh $
 */
public class RFTId0831 extends RecvIdMessage
{
	//#CM576609
	// Class fields --------------------------------------------------
	//#CM576610
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576611
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576612
	/**
	 * Consignor name offset definition
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576613
	/**
	 * Area no. offset definition
	 */
	private static final int OFF_AREA_NO = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM576614
	/**
	 * Inventory location offset definition
	 */
	private static final int OFF_INVENTORY_LOCATION = OFF_AREA_NO + LEN_AREA_NO ;

	//#CM576615
	/**
	 * Product identification code offset definition
	 */
	private static final int OFF_ITEM_ID = OFF_INVENTORY_LOCATION + LEN_LOCATION ;
	
	//#CM576616
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;
	
	//#CM576617
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM576618
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM576619
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM576620
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM576621
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM576622
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM576623
	/**
	 * Stock qty offset definition
	 */
	private static final int OFF_STOCK_QTY = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576624
	/**
	 * Original inventory qty offset definition
	 */
	private static final int OFF_ORIGINAL_INVENTORY_QTY = OFF_STOCK_QTY + LEN_STOCK_QTY ;

	//#CM576625
	/**
	 * Original inventory qty length (byte)
	 */
	private static final int LEN_ORIGINAL_INVENTORY_QTY = LEN_PLAN_QTY;
	
	//#CM576626
	/**
	 * Inventory result qty offset definition
	 */
	private static final int OFF_INVENTORY_RESULT_QTY = OFF_ORIGINAL_INVENTORY_QTY + LEN_ORIGINAL_INVENTORY_QTY ;

	//#CM576627
	/**
	 * Inventory result qty Length(byte)
	 */
	private static final int LEN_INVENTORY_RESULT_QTY = LEN_RESULT_QTY;
	
	//#CM576628
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_ITEM_FORM = OFF_INVENTORY_RESULT_QTY + LEN_INVENTORY_RESULT_QTY ;

	//#CM576629
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_ITEM_FORM + LEN_CASE_PIECE_FLAG;

	//#CM576630
	/**
	 * New stock create flag offset definition
	 */
	private static final int OFF_ENABLE_CREATE_NEW_STOCK = OFF_USE_BY_DATE + LEN_USE_BY_DATE;

	//#CM576631
	/**
	 * work time offset definition
	 */
	private static final int OFF_WORK_SECONDS = OFF_ENABLE_CREATE_NEW_STOCK + LEN_ENABLE_CREATE_NEW_STOCK;
	
	//#CM576632
	/**
	 * completion flag offset definition
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_WORK_SECONDS + LEN_WORK_TIME;
	//#CM576633
	/**
	 * ID number
	 */
	public static final String ID = "0831";
	
	//#CM576634
	/**
	 * completion flag "New" default display
	 */
	public static final String COMPLETION_FLAG_NEW = "0" ;
	
	//#CM576635
	/**
	 * completion flag "Overwrite" default display
	 */
	public static final String COMPLETION_FLAG_UPDATE = "1" ;
	
	//#CM576636
	/**
	 * completion flag "Add" default display
	 */
	public static final String COMPLETION_FLAG_ADD = "2" ;


	//#CM576637
	// Class variables -----------------------------------------------
	//#CM576638
	// Class method --------------------------------------------------
	//#CM576639
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:05 $") ;
	}

	//#CM576640
	// Constructors --------------------------------------------------
	//#CM576641
	/**
	 * Constructor
	 */
	public RFTId0831 ()
	{
		super() ;
		offEtx = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576642
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0831 Inventory check result send ID=0831 telegraph message
	 */
	public RFTId0831 (byte[] rftId0831)
	{
		super() ;
		setReceiveMessage(rftId0831) ;
	}

	//#CM576643
	// Public methods ------------------------------------------------
	//#CM576644
	/**
	 * Fetch Worker code from Inventory check result send telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576645
	/**
	 * Fetch Consignor code from Inventory check result send telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576646
	/**
	 * Fetch Consignor name from Inventory check result send telegraph message
	 * @return		Consignor name
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME, LEN_CONSIGNOR_NAME) ;
		return consignorName.trim();
	}
	
	//#CM576647
	/**
	 * Fetch Area no. from Inventory check result send telegraph message
	 * @return Area no.
	 */
	public String getAreaNo()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO);
		return areaNo.trim();
	}
	
	//#CM576648
	/**
	 * Fetch Inventory location from Inventory check result send telegraph message
	 * @return		Inventory location
	 */
	public String getInventoryLocation ()
	{
		String inventoryLocation = getFromBuffer(OFF_INVENTORY_LOCATION, LEN_LOCATION) ;
		return inventoryLocation.trim();
	}
	
	//#CM576649
	/**
	 * Fetch Product identification code from Inventory check result send telegraph message
	 * @return		Product identification code
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_ITEM_ID, LEN_ITEM_ID) ;
		return itemId.trim();
	}
	
	//#CM576650
	/**
	 * Fetch Item code from Inventory check result send telegraph message
	 * @return		Item code
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	//#CM576651
	/**
	 * Fetch JAN code from Inventory check result send telegraph message
	 * @return		JAN code
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	//#CM576652
	/**
	 * Fetch Bundle ITF from Inventory check result send telegraph message
	 * @return		Bundle ITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF, LEN_BUNDLE_ITF) ;
		return bundleITF.trim();
	}
	
	//#CM576653
	/**
	 * Fetch Case ITF from Inventory check result send telegraph message
	 * @return		Case ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF, LEN_ITF) ;
		return ITF.trim();
	}
	
	//#CM576654
	/**
	 * Fetch Item name from Inventory check result send telegraph message
	 * @return		Item name
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME, LEN_ITEM_NAME) ;
		return itemName.trim();
	}
	
	//#CM576655
	/**
	 * Fetch Qty per bundle from Inventory check result send telegraph message
	 * @return		Qty per bundle
	 */
	public int getBundleEnteringQty ()
	{
		int bundleEnteringQty = getIntFromBuffer(OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
		return bundleEnteringQty;
	}
	
	//#CM576656
	/**
	 * Fetch Qty per case from Inventory check result send telegraph message
	 * @return		Qty per case
	 */
	public int getEnteringQty ()
	{
		int enteringQty = getIntFromBuffer(OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
		return enteringQty;
	}
	
	//#CM576657
	/**
	 * Fetch Stock qty from Inventory check result send telegraph message
	 * @return		Stock qty
	 */
	public int getStockQty ()
	{
		int stockQty = getIntFromBuffer(OFF_STOCK_QTY, LEN_STOCK_QTY) ;
		return stockQty;
	}
	
	//#CM576658
	/**
	 * Fetch Inventory qty from Inventory check result send telegraph message
	 * @return		Original Inventory qty
	 */
	public int getOriginalInventoryQty ()
	{
		int originalInventoryQty = getIntFromBuffer(OFF_ORIGINAL_INVENTORY_QTY, LEN_ORIGINAL_INVENTORY_QTY) ;
		return originalInventoryQty;
	}
	
	//#CM576659
	/**
	 * Fetch Inventory result qty from Inventory check result send telegraph message
	 * @return		Inventory result qty
	 */
	public int getInventoryResultQty ()
	{
		int inventoryResultQty = getIntFromBuffer(OFF_INVENTORY_RESULT_QTY, LEN_INVENTORY_RESULT_QTY) ;
		return inventoryResultQty;
	}
	
	//#CM576660
	/**
	 * Fetch Case piece type from Inventory check result send telegraph message
	 * @return		Case piece type
	 */
	public String getItemForm ()
	{
		String itemForm = getFromBuffer(OFF_ITEM_FORM, LEN_CASE_PIECE_FLAG) ;
		return itemForm.trim();
	}
	
	//#CM576661
	/**
	 * Fetch Expiry date from Inventory check result send telegraph message
	 * @return		Expiry date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE, LEN_USE_BY_DATE);
		return useByDate.trim();
	}

	//#CM576662
	/**
	 * Fetch New stock create flag from Inventory check result send telegraph message
	 * @return		New stock create flag
	 */
	public String getEnableCreateNewStock ()
	{
		String enableCreateNewStock = getFromBuffer(OFF_ENABLE_CREATE_NEW_STOCK, LEN_ENABLE_CREATE_NEW_STOCK);
		return enableCreateNewStock.trim();
	}

	//#CM576663
	/**
	 * Fetch work time from Inventory check result send telegraph message
	 * @return work time
	 */
	public int getWorkSeconds()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}
	
	//#CM576664
	/**
	 * Fetch completion flag from Inventory check result send telegraph message
	 * @return completion flag
	 */
	public String getCompletionFlag()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
		return completionFlag.trim();
	}
	
	//#CM576665
	// Package methods -----------------------------------------------
	//#CM576666
	// Protected methods ---------------------------------------------
	//#CM576667
	// Private methods -----------------------------------------------

}
//#CM576668
//end of class


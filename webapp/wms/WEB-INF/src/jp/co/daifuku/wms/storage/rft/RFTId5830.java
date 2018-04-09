// $Id: RFTId5830.java,v 1.2 2006/12/07 09:00:02 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576851
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576852
/**
 * Inventory item inquiry response ID=5830 telegraph message process<BR>
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of ID5830 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>1093</td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no.</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Consignor name</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>Area no.</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>Inventory location</td><td>16 byte</td><td></td></tr>
 * <tr><td>Product identification code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Item code</td>		<td>16 byte</td><td>Not used</td></tr>
 * <tr><td>JAN code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>		<td>40 byte</td><td></td></tr>
 *  <tr><td>Qty per bundle</td>		<td>6 byte</td>	<td></td></tr>
 * <tr><td>Qty per case</td>		<td>6 byte</td>	<td></td></tr>
 * <tr><td>Stock qty</td>			<td>9 byte</td>	<td></td></tr>
 * <tr><td>Inventory qty</td>			<td>9 byte</td>	<td></td></tr>
 * <tr><td>Case piece type</td>	<td>1 byte</td>	<td>Not used</td></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td>	<td>While commiting one Expiry date, set that value</td></tr>
 * <tr><td>Stock flag</td>		<td>1 byte</td>	
 *     <td>0:No Stock package<BR>1:Stock package available<BR>2:Stock data does not exist</td></tr>
 * <tr><td>Response flag</td>		<td>1 byte</td>
 *     <td>0:Normal  (NewInventory check) <BR>2:Inventory check completed<BR>5:date/time process<BR>9:Error</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td><td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:02 $
 * @author  $Author: suresh $
 */
public class RFTId5830 extends SendIdMessage
{
	//#CM576853
	// Class fields --------------------------------------------------
	//#CM576854
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576855
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576856
	/**
	 * Consignor name offset definition
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576857
	/**
	 * Area no. offset definition
	 */
	private static final int OFF_AREA_NO  = OFF_CONSIGNOR_NAME+ LEN_CONSIGNOR_NAME;
	
	//#CM576858
	/**
	 * Inventory location offset definition
	 */
	private static final int OFF_INVENTORY_LOCATION = OFF_AREA_NO + LEN_AREA_NO ;
	
	//#CM576859
	/**
	 * Product identification code offset definition
	 */
	private static final int OFF_ITEM_ID = OFF_INVENTORY_LOCATION + LEN_LOCATION ;
	//#CM576860
	/**
	 * Product identification code Length(byte)
	 */
	private static final int LEN_ITEM_ID = 16 ;
	
	//#CM576861
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID ;
	
	//#CM576862
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM576863
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM576864
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM576865
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM576866
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM576867
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM576868
	/**
	 * Stock qty offset definition
	 */
	private static final int OFF_STOCK_QTY = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576869
	/**
	 * Inventory qty offset definition
	 */
	private static final int OFF_INVENTORY_CHECK_QTY = OFF_STOCK_QTY + LEN_STOCK_QTY ;
	
	//#CM576870
	/**
	 * Inventory qty Length(byte)
	 */
	private static final int LEN_INVENTORY_CHECK_QTY = LEN_RESULT_QTY;
	
	//#CM576871
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_ITEM_FORM = OFF_INVENTORY_CHECK_QTY + LEN_INVENTORY_CHECK_QTY ;
	
	//#CM576872
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_ITEM_FORM + LEN_CASE_PIECE_FLAG;

	//#CM576873
	/**
	 * Stock flag offset definition
	 */
	private static final int OFF_STOCK_FLAG = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
		
	//#CM576874
	/**
	 * ID number
	 */
	public static final String ID = "5830";
	
	//#CM576875
	/**
	 * Stock flag "No Stock package" field
	 */
	public static final String STOCK_FLAG_STOCK_CONTROL_OFF = "0" ;
	
	//#CM576876
	/**
	 * Stock flag "Stock package available" field
	 */
	public static final String STOCK_FLAG_STOCK_ON = "1" ;
	
	//#CM576877
	/**
	 * Stock flag "Stock data does not exist" field
	 */
	public static final String STOCK_FLAG_STOCK_NULL = "2" ;

	//#CM576878
	// Class variables -----------------------------------------------

	//#CM576879
	// Class method --------------------------------------------------
	//#CM576880
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:02 $";
	}

	//#CM576881
	// Constructors --------------------------------------------------
	//#CM576882
	/**
	 * Constructor
	 */
	public RFTId5830 ()
	{
		super() ;
		offAnsCode = OFF_STOCK_FLAG + LEN_STOCK_FLAG;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576883
	// Public methods ------------------------------------------------
	//#CM576884
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM576885
	/**
	 * Set Consignor code
	 * @param  consignorCode  Consignor code
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM576886
	/**
	 * Set Consignor name
	 * @param  consignorName  Consignor name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME) ;
	}
	
	//#CM576887
	/**
	 * Set Area no.
	 * @param areaNo
	 */
	public void setAreaNo(String areaNo)
	{
		setToBuffer(areaNo, OFF_AREA_NO);
	}
	
	//#CM576888
	/**
	 * Set Inventory location
	 * @param  inventoryLocation  Inventory location
	 */
	public void setInventoryLocation (String inventoryLocation)
	{
		setToBuffer(inventoryLocation, OFF_INVENTORY_LOCATION) ;
	}
	
	//#CM576889
	/**
	 * Set Product identification code
	 * @param  itemId  Product identification code
	 */
	public void setItemId (String itemId)
	{
		setToBuffer(itemId, OFF_ITEM_ID) ;
	}
	
	//#CM576890
	/**
	 * Set Item code
	 * @param  itemCode  Item code
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE) ;
	}
	
	//#CM576891
	/**
	 * Set JAN code
	 * @param  JANCode  JAN code
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode, OFF_JAN_CODE) ;
	}
	
	//#CM576892
	/**
	 * Set Bundle ITF
	 * @param  bundleITF  Bundle ITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF) ;
	}
	
	//#CM576893
	/**
	 * Set Case ITF
	 * @param  ITF  Case ITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF, OFF_ITF) ;
	}
	
	//#CM576894
	/**
	 * Set Item name
	 * @param  itemName  Item name
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME) ;
	}
	
	//#CM576895
	/**
	 * Set Qty per bundle
	 * @param  bundleEnteringQty  Qty per bundle
	 */
	public void setBundleEnteringQty (int bundleEnteringQty)
	{
		//#CM576896
		// Store data with right justification
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
	}
	
	//#CM576897
	/**
	 * Set Qty per case
	 * @param  enteringQty  Qty per case
	 */
	public void setEnteringQty (int enteringQty)
	{
		//#CM576898
		// Store data with right justification
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
	}
	
	//#CM576899
	/**
	 * Set Stock qty
	 * @param  stockQty  Stock qty
	 */
	public void setStockQty (int stockQty)
	{
		//#CM576900
		// Store data with right justification
		setToBufferRight(stockQty, OFF_STOCK_QTY, LEN_STOCK_QTY) ;
	}
	
	//#CM576901
	/**
	 * Set Inventory qty
	 * @param  inventoryCheckQty  Inventory qty
	 */
	public void setInventoryCheckQty (int inventoryCheckQty)
	{
		//#CM576902
		// Store data with right justification
		setToBufferRight(inventoryCheckQty, OFF_INVENTORY_CHECK_QTY, LEN_INVENTORY_CHECK_QTY) ;
	}
	
	//#CM576903
	/**
	 * Set Case piece type
	 * @param  itemForm  Case piece type
	 */
	public void setItemForm (String itemForm)
	{
		setToBuffer(itemForm, OFF_ITEM_FORM) ;
	}
	
	//#CM576904
	/**
	 * Set Expiry date
	 * @param useByDate Expiry date
	 */
	public void setUseByDate (String useByDate)
	{
		setToBuffer(useByDate , OFF_USE_BY_DATE);
	}

	//#CM576905
	/**
	 * Set Stock flag
	 * @param  stockFlag  Stock flag
	 */
	public void setStockFlag (String stockFlag)
	{
		setToBuffer(stockFlag, OFF_STOCK_FLAG) ;
	}

	//#CM576906
	// Package methods -----------------------------------------------
	//#CM576907
	// Protected methods ---------------------------------------------
	//#CM576908
	// Private methods -----------------------------------------------

}
//#CM576909
//end of class


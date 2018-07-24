//#CM10446
//$Id: RFTId5911.java,v 1.2 2006/09/27 03:00:32 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM10447
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10448
/**
 * inventory information inquiry the response ID=5911 Process electronic statement.
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id5911</CAPTION>
 * <TR><TH>Item Name</TH>				<TH>Length</TH>	<TH>contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5911</td></tr>
 * <tr><td>Handy Transmission time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server Transmission time</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy terminalNo.</td>		<td>3 byte</td>	<td>  </td></tr>
 * <tr><td></td>		<td>4 byte</td>	<td>  </td></tr>
 * <tr><td>Consignor code</td>			<td>16 byte</td><td>  </td></tr>
 * <tr><td>Location No</td>		<td>16 byte</td><td>  </td></tr>
 * <tr><td>Case/Piece Division</td>		<td>1 byte</td>	<td>0：all 1：Case  2：Piece  3：Not Designated</tr>
 * <tr><td>Consignor Name</td>				<td>40 byte</td><td>  </td></tr>
 * <tr><td>Item ID Code</td>		<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Item code</td>			<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>JANCode</td>			<td>16 byte</td><td>  </td></tr>
 * <tr><td>BallITF</td>				<td>16 byte</td><td>  </td></tr>
 * <tr><td>CaseITF</td>				<td>16 byte</td><td>  </td></tr>
 * <tr><td>Item name</td> 			<td>40 byte</td><td>  </td></tr>
 * <tr><td>packed qty per bundle</td> 			<td>6 byte</td><td>  </td></tr>
 * <tr><td>Packed qty per case</td> 			<td>6 byte</td><td>  </td></tr>
 * <tr><td>Stock qty</td>				<td>9 byte</td><td>Except allocationくStock qty</td></tr>
 * <tr><td>Expiry Date</td>			<td>8 byte</td><td>  </td></tr>
 * <tr><td>manufactured date</td>				<td>8 byte</td><td>Not available</td></tr>
 * <tr><td>Table File Name</td>		<td>6 byte</td><td>  </td></tr>
 * <tr><td>File record Qty</td>	<td>6 byte</td><td>  </td></tr>		
 * <tr><td>Response flag</td>			<td>1 byte</td><td>0:Normal  3:Without stockcontrol  5:Daily process under process
 * 												   <BR>7: Multiple presence of corresponding data; 8 No corresponding stock; or 9:Error</td></tr>
 * <tr><td>error detail				<td>2 byte</td><td>  </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td><td>  </td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/14</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:32 $
 * @author  $Author: suresh $
 * 
 * 
 */

public class RFTId5911 extends SendIdMessage {

//#CM10449
//	 Class fields --------------------------------------------------
	//#CM10450
	/**
	 * Define offset for the person in charge code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM10451
	/**
	 * Define offset for the Consignor code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM10452
	/**
	 * Define offset for the Area No.
	 */
	private static final int OFF_AREA = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM10453
	/**
	 * Define offset for the Location No.
	 */
	private static final int OFF_LOCATION = OFF_AREA + LEN_AREA_NO;
	
	//#CM10454
	/**
	 * Define offset for the Case/Piece Division.
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_LOCATION + LEN_JAN_CODE ;
	
	//#CM10455
	/**
	 * Define offset for the Consignor Name.
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;
	
	//#CM10456
	/**
	 * Define offset for the item distinct code.
	 */
	private static final int OFF_ITEM_ID = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM10457
	/**
	 * Define offset for the item code.
	 */
	private static final int OFF_ITEM_CODE = OFF_ITEM_ID + LEN_ITEM_ID;
	
	//#CM10458
	/**
	 * Define offset for the JAN code.
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE;
		
	//#CM10459
	/**
	 * Define offset for the Bundle ITF.
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM10460
	/**
	 * Define offset for the Case ITF.
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM10461
	/**
	 * Define offset for the item name.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM10462
	/**
	 * Define offset for the packed qty per bundle.
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM10463
	/**
	 * Define offset for the Case contents.
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM10464
	/**
	 * Define offset for the stock qty.
	 */
	private static final int OFF_STOCK_QTY = OFF_ENTERING_QTY + LEN_ENTERING_QTY;
	
	//#CM10465
	/**
	 * Define offset for the expiry date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_STOCK_QTY + LEN_STOCK_QTY ;
	
	//#CM10466
	/**
	 * Define offset for the manufactured date.
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	
	//#CM10467
	/**
	 * Define offset for the Offset the list file name.
	 */
	private static final int OFF_FILE_NAME = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE ;
	
	//#CM10468
	/**
	 * Define offset for the File record Qty.
	 */
	private static final int OFF_FILE_RECORD_NO = OFF_FILE_NAME + LEN_FILE_NAME;
	
	//#CM10469
	/**
	 * ID No.
	 */
	public static final String ID = "5911";
	
	//#CM10470
	/**
	 * Field that represents "without stockcontrol" of responce flag.
	 */
	public static final String ANS_CODE_NO_STOCK_PACK = "3" ;
	
	//#CM10471
	// Class variables -----------------------------------------------
//#CM10472
//	 Class method --------------------------------------------------
	//#CM10473
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:32 $";
	}

	//#CM10474
	// Constructors --------------------------------------------------
	//#CM10475
	/**
	 * Constructors
	 */
	public RFTId5911 ()
	{
		super() ;
		offAnsCode = OFF_FILE_RECORD_NO + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM10476
	// Public methods ------------------------------------------------
	//#CM10477
	/**
	 * Set the person in charge code.
	 * @param  workerCode  
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM10478
	/**
	 * Set the Consignor code.
	 * @param  consignorCode  Consignor code
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM10479
	/**
	 * Set the Area No.
	 * @param  areaNo  Area No.
	 */
	public void setAreaNo (String areaNo)
	{
		setToBuffer(areaNo, OFF_AREA) ;
	}
		
	
	//#CM10480
	/**
	 * Set the Location No.
	 * @param  locationNo  Location No.
	 */
	public void setLocationNo (String locationNo)
	{
		setToBuffer(locationNo, OFF_LOCATION) ;
	}
	
	//#CM10481
	/**
	 * Set Case/Piece division.
	 * @param  casePieceFlag  Case/Piece division
	 */
	public void setCasePieceFlag (String casePieceFlag)
	{
		setToBuffer(casePieceFlag, OFF_CASE_PIECE_FLAG) ;
	}
	
	//#CM10482
	/**
	 * Set the Consignor Name.
	 * @param  consignorName  Consignor Name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME) ;
	}
	
	//#CM10483
	/**
	 * Set the item distinct code.
	 * @param  itemId  Item ID Code
	 */
	public void setItemId (String itemId)
	{
		setToBuffer(itemId, OFF_ITEM_ID) ;
	}
	

	//#CM10484
	/**
	 * Set the item code.
	 * @param  itemCode  Item code
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE) ;
	}
	
	//#CM10485
	/**
	 * Set JANCode.
	 * @param  JANCode  JANCode
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode, OFF_JAN_CODE) ;
	}
	
	//#CM10486
	/**
	 * Set the bundle ITF.
	 * @param  bundleITF  Bundle ITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF) ;
	}
	
	//#CM10487
	/**
	 * Set the Case ITF.
	 * @param  ITF  Case ITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF, OFF_ITF) ;
	}
	
	//#CM10488
	/**
	 * Set the item name.
	 * @param  itemName  Item name
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME) ;
	}
	

	//#CM10489
	/**
	 * Set the packed qty per bundle.
	 * @param  bundleEnteringQty  packed qty per bundle
	 */
	public void setBundleEnteringQty (int bundleEnteringQty)
	{
		//#CM10490
		// Store data right aligned
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
	}
	
	//#CM10491
	/**
	 * Set the packed qty per case.
	 * @param  enteringQty  Packed qty per case
	 */
	public void setEnteringQty (int enteringQty)
	{
		//#CM10492
		// Store data right aligned
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
	}
	
	//#CM10493
	/**
	 * Set the stock qty.
	 * @param  stockQty  Stock qty
	 */
	public void setStockQty (int stockQty)
	{	
		//#CM10494
		// Store data right aligned
		setToBufferRight(stockQty, OFF_STOCK_QTY, LEN_STOCK_QTY) ;
	}

	//#CM10495
	/**
	 * Set the expiry date.
	 * @param  useByDate  Expiry Date
	 */
	public void setUseByDate (String useByDate)
	{
		setToBuffer(useByDate, OFF_USE_BY_DATE) ;
	}
	
	//#CM10496
	/**
	 * Set the manufactured date.
	 * @param  manufactureDate  Manufactured date
	 */
	public void setManufactureDate (String manufactureDate)
	{
		setToBuffer(manufactureDate, OFF_MANUFACTURE_DATE) ;
	}
	
	//#CM10497
	/**
	 * Set the list file name.
	 * @param  fileName  Table File Name
	 */
	public void setFileName (String fileName)
	{
		setToBuffer(fileName, OFF_FILE_NAME) ;
	}
	
	//#CM10498
	/**
	 * Set the file record count.
	 * @param fileRecordNo File record Qty
	 */
	public void setFileRecordNo(int fileRecordNo)
	{
		setToBufferRight(fileRecordNo, OFF_FILE_RECORD_NO, LEN_FILE_RECORD_NUMBER);
	}




	//#CM10499
	// Package methods -----------------------------------------------
    //#CM10500
    // Protected methods ---------------------------------------------


//#CM10501
// Private methods -----------------------------------------------

}
//#CM10502
//end of class

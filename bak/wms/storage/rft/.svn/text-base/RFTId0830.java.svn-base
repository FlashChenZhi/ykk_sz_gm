// $Id: RFTId0830.java,v 1.2 2006/12/07 09:00:05 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM576576
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576577
/**
 * Inventory item inquiry ID=0830 telegraph message process
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of ID0830 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0093</td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no.</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Area no.</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>Inventory location</td><td>16 byte</td><td></td></tr>
 * <tr><td>Scan Item code1</td>		<td>16 byte</td><td>From front</td></tr>
 * <tr><td>Scan Item code2</td>		<td>16 byte</td><td>From front (If ITFtoJAN is enabled, converted value)</td></tr>
 * <tr><td>Case piece type</td>			<td>1 byte</td>	<TD>Not used</TD></tr>
 * <tr><td>Expiry date</td>		<td>8 byte</td>	<TD>Not used</TD></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td></td></tr>
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
public class RFTId0830 extends RecvIdMessage
{
	//#CM576578
	// Class fields --------------------------------------------------
	//#CM576579
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576580
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576581
	/**
	 * Area no. offset definition
	 */
	private static final int OFF_AREA_NO = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM576582
	/**
	 * Inventory location offset definition
	 */
	private static final int OFF_INVENTORY_LOCATION = OFF_AREA_NO + LEN_AREA_NO ;
	
	//#CM576583
	/**
	 * Scan Item code1 offset definition
	 */
	private static final int OFF_SCAN_CODE_1 = OFF_INVENTORY_LOCATION + LEN_LOCATION ;
	
	//#CM576584
	/**
	 * Scan Item code2 offset definition
	 */
	private static final int OFF_SCAN_CODE_2 = OFF_SCAN_CODE_1 + LEN_JAN_CODE;
	
	//#CM576585
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_ITEM_FORM = OFF_SCAN_CODE_2 + LEN_JAN_CODE ;
	
	//#CM576586
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_ITEM_FORM + LEN_WORK_FORM;

	//#CM576587
	/**
	 * ID number
	 */
	public static final String ID ="0830";
		

	//#CM576588
	// Class variables -----------------------------------------------
	//#CM576589
	// Class method --------------------------------------------------
	//#CM576590
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:05 $") ;
	}

	//#CM576591
	// Constructors --------------------------------------------------
	//#CM576592
	/**
	 * Constructor
	 */
	public RFTId0830 ()
	{
		super() ;
		offEtx = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM576593
	/**
	 * Pass the telegraph message received from the RFT to the Constructor
	 * @param rftId0830 Inventory item inquiry ID=0830 telegraph message
	 */
	public RFTId0830 (byte[] rftId0830)
	{
		super() ;
		setReceiveMessage(rftId0830) ;
	}

	//#CM576594
	// Public methods ------------------------------------------------
	//#CM576595
	/**
	 * Fetch Worker code from Inventory item inquiry telegraph message
	 * @return		Worker code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM576596
	/**
	 * Fetch Consignor code from Inventory item inquiry telegraph message
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM576597
	/**
	 * Fetch Area no. from Inventory item inquiry telegraph message
	 * @return Area no.
	 */
	public String getAreaNo()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO);
		return areaNo.trim();
	}
	//#CM576598
	/**
	 * Fetch Inventory location from Inventory item inquiry telegraph message
	 * @return		Inventory location
	 */
	public String getInventryLocation ()
	{
		String inventoryLocation = getFromBuffer(OFF_INVENTORY_LOCATION, LEN_LOCATION) ;
		return inventoryLocation.trim();
	}
	
	//#CM576599
	/**
	 * Fetch Scan Item code1 from Inventory item inquiry telegraph message
	 * @return		Scan Item code1
	 */
	public String getScanCode1 ()
	{
		String scanCode1 = getFromBuffer(OFF_SCAN_CODE_1, LEN_JAN_CODE) ;
		return scanCode1.trim();
	}
	
	//#CM576600
	/**
	 * Fetch Scan Item code2 from Inventory item inquiry telegraph message
	 * @return		Scan Item code2
	 */
	public String getScanCode2 ()
	{
		String scanCode2 = getFromBuffer(OFF_SCAN_CODE_2, LEN_JAN_CODE) ;
		return scanCode2.trim();
	}
	
	//#CM576601
	/**
	 * Fetch Case piece type from Inventory item inquiry telegraph message
	 * @return		Case piece type
	 */
	public String getItemForm ()
	{
		String casePieceFlag = getFromBuffer(OFF_ITEM_FORM, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	//#CM576602
	/**
	 * Fetch Expiry date from Inventory item inquiry telegraph message
	 * @return		Expiry date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE , LEN_USE_BY_DATE);
		return useByDate.trim();
	}
	
	//#CM576603
	// Package methods -----------------------------------------------
	//#CM576604
	// Protected methods ---------------------------------------------
	//#CM576605
	// Private methods -----------------------------------------------

}
//#CM576606
//end of class


//#CM10348
//$Id: RFTId0911.java,v 1.2 2006/09/27 03:00:34 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM10349
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10350
/**
 * inventory information inquiry ID=0911 electronic statementを processします
 * 
 * <p>  
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id0911</CAPTION>
 * <TR><TH>Item Name</TH>				<TH>Length</TH>		<TH>contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>		<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>		<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>		<td>0911</td></tr>
 * <tr><td>Handy Transmission time</td>	<td>6 byte</td>		<td>HHMMSS</td></tr>
 * <tr><td>Server Transmission time</td>		<td>6 byte</td>		<td>HHMMSS</td></tr>
 * <tr><td>Handy terminalNo.</td>		<td>3 byte</td>		<td>  </td></tr>
 * <tr><td></td>		<td>4 byte</td>		<td>  </td></tr>
 * <tr><td>Consignor code</td>			<td>16 byte</td>	<td>  </td></tr>
 * <tr><td>Location </td>		<td>16 byte</td>	<td>  </td></tr>
 * <tr><td>Scan code1</td>	<td>16 byte</td>	<td>Front aligned</td></tr>
 * <tr><td>Scan code2</td>	<td>16 byte</td>	<td>Front aligned</td><tr>
 * <tr><td>Case/Piece Division</td> 		<td>8 byte</td>		<td>0：All 1：Case  2：Piece  3：Not Designated</td></tr>
 * <tr><td>List select flag</td>		<td>1 byte</td>		<td>0：Not selected(1st time)
 * 														<BR>1：Selected from the list</td></tr>
 * <tr><td>Expiry Date					<td>8 byte</td>		<td>  </td><tr>
 * <tr><td>ETX</td>					<td>1 byte</td>		<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:34 $
 * @author  $Author: suresh $
 * @author etakeda
 * 
 */

public class RFTId0911 extends RecvIdMessage {
//#CM10351
//	 Class fields --------------------------------------------------
	//#CM10352
	/**
	 * Define offset for the person in charge code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM10353
	/**
	 * Define offset for the Consignor code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM10354
	/**
	 * Define offset for the area.
	 */
	private static final int OFF_AREA = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM10355
	/**
	 * Define offset for the location.
	 */
	private static final int OFF_LOCATION = OFF_AREA + LEN_AREA_NO ;
	
	//#CM10356
	/**
	 * Define offset for the Scan code1.
	 */
	private static final int OFF_SCAN_CODE_1 = OFF_LOCATION + LEN_LOCATION;
	
	//#CM10357
	/**
	 * Define offset for the Scan code2.
	 */
	private static final int OFF_SCAN_CODE_2 = OFF_SCAN_CODE_1 + LEN_JAN_CODE;
	
	//#CM10358
	/**
	 * Define offset for the Load Size.
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_SCAN_CODE_2 + LEN_JAN_CODE ;

	//#CM10359
	/**
	 * Define offset for the list select flag.
	 */
	private static final int OFF_LIST_SELECTION_FLAG = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;

	//#CM10360
	/**
	 * Define offset for the expiry date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_LIST_SELECTION_FLAG + LEN_LIST_SELECTION_FLAG ;
	
	//#CM10361
	/**
	 * Case/Piece Division0：Field indicating all
	 */
	public static final String CASE_PIECE_FLAG_ALL = "0";
	
	//#CM10362
	/**
	 * Field that represents when selection is made list select flag without selection (1st time)
	 */
	public static final String LIST_SELECTION_FALSE = "0" ;
	
	//#CM10363
	/**
	 * Field that represents when selection is made from list select flag
	 */
	public static final String LIST_SELECTION_TRUE = "1" ;
	
	//#CM10364
	/**
	 * ID No.
	 */
	public static final String ID = "0911";

	
	//#CM10365
	// Class variables -----------------------------------------------


	//#CM10366
	// Class method --------------------------------------------------
	//#CM10367
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:34 $";
	}
	
//#CM10368
//	 Constructors --------------------------------------------------
	//#CM10369
	/**
	 * Constructors
	 */
	public RFTId0911 ()
	{
		super() ;
		offEtx = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM10370
	/**
	 * Pass the electronic statement received from RFT to Constructors.
	 * @param rftId0911 Stock info inquiry ID=0911 electronic statement
	 */
	public RFTId0911 (byte[] rftId0911)
	{
		super() ;
		setReceiveMessage(rftId0911) ;
	}
	
   //#CM10371
   // Public methods ------------------------------------------------
	//#CM10372
	/**
	 * Obtain the person in charge code from the inventory information inquiry electronic statement.
	 * @return		
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM10373
	/**
	 * Obtain the Consignor code from the inventory information inquiry electronic statement.
	 * @return		Consignor code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
		
	//#CM10374
	/**
	 * Obtain the area from the inventory information inquiry electronic statement.
	 * @return		Area
	 */
	public String getAreaNo ()
	{
		String AreaNo = getFromBuffer(OFF_AREA, LEN_AREA_NO) ;
		return AreaNo.trim();
	}
	
	//#CM10375
	/**
	 * Obtain the location from the inventory information inquiry electronic statement.
	 * @return		Location 
	 */
	public String getLocationNo ()
	{
		String LocationNo = getFromBuffer(OFF_LOCATION, LEN_LOCATION) ;
		return LocationNo.trim();
	}
	
	//#CM10376
	/**
	 * Obtain the scan code1 from the inventory information inquiry electronic statement.
	 * @return scanCode1 Scan code1
	 */
	public String getScanCode1()
	{
		String scanCode1 = getFromBuffer(OFF_SCAN_CODE_1, LEN_JAN_CODE);
		return scanCode1.trim();
	}
	
	//#CM10377
	/**
	 * Obtain the scan code2 from the inventory information inquiry electronic statement.
	 * @return scanCode2 Scan code2
	 */
	public String getScanCode2()
	{
		String scanCode2 = getFromBuffer(OFF_SCAN_CODE_2, LEN_JAN_CODE);
		return scanCode2.trim();
	}
	
	//#CM10378
	/**
	 * Obtain the Case/Piece division from the Stock info inquiry electronic statement.
	 * @return		Case/Piece division
	 * 				1:Case
	 * 				2:Piece
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	//#CM10379
	/**
	 * Obtain the list select flag from the inventory information inquiry electronic statement.
	 * @return		List select flag
	 */
	public String getListSelectionFlag ()
	{
		String listSelectionFlag = getFromBuffer(OFF_LIST_SELECTION_FLAG , LEN_LIST_SELECTION_FLAG);
		return listSelectionFlag.trim();
	}
	
	//#CM10380
	/**
	 * Obtain the expiry date from the inventory information inquiry electronic statement.
	 * @return		Expiry Date
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE , LEN_USE_BY_DATE);
		return useByDate.trim();
	}

	//#CM10381
	// Package methods -----------------------------------------------
    //#CM10382
    // Protected methods ---------------------------------------------
	//#CM10383
	// Private methods -----------------------------------------------

}
//#CM10384
//end of class


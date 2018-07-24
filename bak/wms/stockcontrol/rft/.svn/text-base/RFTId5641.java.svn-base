// $Id: RFTId5641.java,v 1.2 2006/09/27 03:00:33 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;
//#CM10427
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10428
/**
 * Execute processing of Unplanned Picking Result data response ID=5641 electronic statement.
 * <p>
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id5641</CAPTION>
 * <TR><TH>Item Name</TH>				<TH>Length</TH>	<TH>contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5631</td></tr>
 * <tr><td>Handy Transmission time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server Transmission time</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy terminalNo.</td>		<td>3 byte</td>	<td>　</td></tr>
 * <tr><td></td>		<td>4 byte</td>	<td>　</td></tr>
 * <tr><td>possible picking Stock qty</td>		<td>9 byte</td>	<td>7: when the response flag overflows possible picking qty</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal　4:possible picking qty overflow　5:daily process under processing
 * 													<br>6:Maintenance in process at other terminal; 8: No corresponding stock; or 9: Error</td></tr>
 * <tr><td>error detail</td>			<td>2 byte</td>	<td>　</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/15</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:33 $
 * @author  $Author: suresh $
 */
public class RFTId5641 extends SendIdMessage {
//#CM10429
//	 Class fields --------------------------------------------------
	//#CM10430
	/**
	 * Define offset for the person in charge code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM10431
	/**
	 * Define offset for the possible picking stock qty flag.
	 */
	private static final int OFF_STORAGE_QTY = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM10432
	/**
	 * ID No.
	 */
	public static final String ID = "5641";
	
	//#CM10433
	/**
	 * Field that represents overflow of possible picking qty
	 */
	public static final String ANS_CODE_RETRIEVAL_OVER = "4" ;

	
	//#CM10434
	// Class variables -----------------------------------------------

	//#CM10435
	// Class method --------------------------------------------------
	//#CM10436
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/09/27 03:00:33 $") ;
	}

	//#CM10437
	// Constructors --------------------------------------------------
	//#CM10438
	/**
	 * Constructors
	 */
	public RFTId5641 ()
	{
		offAnsCode = OFF_STORAGE_QTY + LEN_STOCK_QTY;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM10439
	// Public methods ------------------------------------------------
	//#CM10440
	/**
	 * Set the person in charge code.
	 * @param  workerCode  
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM10441
	/**
	 * Set the possible picking stock qty.
	 * @param  storageQty  Response flag
	 */
	public void setStorageQty (int storageQty)
	{
		setToBufferRight(storageQty, OFF_STORAGE_QTY, LEN_STOCK_QTY);
	}
	
	//#CM10442
	// Package methods -----------------------------------------------
	//#CM10443
	// Protected methods ---------------------------------------------
	//#CM10444
	// Private methods -----------------------------------------------

}
//#CM10445
//end of class


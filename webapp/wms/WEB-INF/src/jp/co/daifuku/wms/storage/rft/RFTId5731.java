// $Id: RFTId5731.java,v 1.2 2006/12/07 09:00:04 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576763
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576764
/**
 * Relocation picking result data response ID=5731 telegraph message process<BR>
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of Id5731 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5731</td></tr>
 * <tr><td>Handy send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>Stock qty available for picking</td>		<td>9 byte</td>	<td>Response flag 7: If picking possible qty exceeds</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal  4:Picking possible qty exceeds 5:Daily updage in prgress
 * 													<br>6:Under maintenance in another terminal 8:No stock 9:Error</td></tr>
 * <tr><td>Error details</td>			<td>2 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:04 $
 * @author  $Author: suresh $
 */
public class RFTId5731 extends SendIdMessage
{
	//#CM576765
	// Class fields --------------------------------------------------
	//#CM576766
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576767
	/**
	 * Relocation picking possible qty offset definition
	 */
	private static final int OFF_STORAGE_QTY = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM576768
	/**
	 * ID number
	 */
	public static final String ID ="5731";
	
	//#CM576769
	/**
	 * Relocation possible qty exceeds field
	 */
	public static final String ANS_CODE_MOVEMENT_OVER = "4" ;


	//#CM576770
	// Class variables -----------------------------------------------

	//#CM576771
	// Class method --------------------------------------------------
	//#CM576772
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:04 $") ;
	}

	//#CM576773
	// Constructors --------------------------------------------------
	//#CM576774
	/**
	 * Constructor
	 */
	public RFTId5731 ()
	{
		super() ;
		offAnsCode = OFF_STORAGE_QTY + LEN_STOCK_QTY;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576775
	// Public methods ------------------------------------------------
	//#CM576776
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM576777
	/**
	 * Set Relocation picking possible qty
	 * @param  storageQty  Relocation picking possible qty
	 */
	public void setStorageQty (int storageQty)
	{
		setToBufferRight(storageQty, OFF_STORAGE_QTY, LEN_STOCK_QTY) ;
	}
	//#CM576778
	// Package methods -----------------------------------------------
	//#CM576779
	// Protected methods ---------------------------------------------
	//#CM576780
	// Private methods -----------------------------------------------

}
//#CM576781
//end of class


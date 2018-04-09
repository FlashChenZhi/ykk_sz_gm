// $Id: RFTId5231.java,v 1.2 2006/12/07 09:00:04 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576741
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576742
/**
 * Storage result response ID=5231 telegraph message process<BR>
 * 
 * ID5231 telegraph message
 * <p>
 * <table border="1">
 * <CAPTION>Structure of Id5231 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td> <td></td></tr>
 * <tr><td>ID</td>				<td>4 byte</td> <td>5231</td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>  <td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td> <td>3 byte</td> <td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td> <td></td></tr>
 * <tr><td>Total Storage qty</td> 		<td>9 byte</td> <td></td></tr>
 * <tr><td>Pending Storage qty</td>		<td>9 byte</td> <td></td></tr>
 * <tr><td>Response flag</td>		<td>1 byte</td> <td>0:Normal  6:Under maintenance in another terminal 9:Error</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td> <td></td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:04 $
 * @author  $Author: suresh $
 */
public class RFTId5231 extends SendIdMessage
{
	//#CM576743
	// Class fields --------------------------------------------------
	//#CM576744
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576745
	/**
	 * Total Storage qty offset definition
	 */
	private static final int OFF_TOTAL_STORAGE_CNT = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM576746
	/**
	 * Pending Storage qty offset definition
	 */
	private static final int OFF_REMAINING_STORAGE_CNT = OFF_TOTAL_STORAGE_CNT + LEN_TOTAL_ITEM_COUNT;

	//#CM576747
	/**
	 * ID number
	 */
	public static final String ID = "5231";
	//#CM576748
	// Class variables -----------------------------------------------

	//#CM576749
	// Class method --------------------------------------------------
	//#CM576750
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:04 $") ;
	}

	//#CM576751
	// Constructors --------------------------------------------------
	//#CM576752
	/**
	 * Constructor
	 */
	public RFTId5231 ()
	{
		super() ;
		offAnsCode = OFF_REMAINING_STORAGE_CNT + LEN_REMAINING_ITEM_COUNT;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576753
	// Public methods ------------------------------------------------
	//#CM576754
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM576755
	/**
	 * Set Total Storage qty
	 * @param  totalStorageCnt  Total Storage qty
	 */
	public void setTotalStorageCnt (int totalStorageCnt)
	{
		//#CM576756
		// Store data with right justification
		setToBufferRight(totalStorageCnt, OFF_TOTAL_STORAGE_CNT, LEN_TOTAL_ITEM_COUNT) ;
	}
	
	//#CM576757
	/**
	 * Set Pending Storage qty
	 * @param  remainingStorageCnt  Pending Storage qty
	 */
	public void setRemainingStorageCnt (int remainingStorageCnt)
	{
		//#CM576758
		// Store data with right justification
		setToBufferRight(remainingStorageCnt, OFF_REMAINING_STORAGE_CNT, LEN_REMAINING_ITEM_COUNT) ;
	}

	//#CM576759
	// Package methods -----------------------------------------------

	//#CM576760
	// Protected methods ---------------------------------------------

	//#CM576761
	// Private methods -----------------------------------------------

}
//#CM576762
//end of class


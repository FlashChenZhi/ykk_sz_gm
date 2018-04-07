// $Id: RFTId5741.java,v 1.2 2006/12/07 09:00:03 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576835
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576836
/**
 * Relocation storage result response ID=5741 telegraph message process<BR>
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of Id5741 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5731</td></tr>
 * <tr><td>Handy send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal  6:Under maintenance in another terminal 9:Error
 * <tr><td>Error details</td>			<td>2 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:03 $
 * @author  $Author: suresh $
 */
public class RFTId5741 extends SendIdMessage
{
	//#CM576837
	// Class fields --------------------------------------------------
	//#CM576838
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576839
	/**
	 * ID number
	 */
	public static final String ID = "5741";
	
	//#CM576840
	// Class variables -----------------------------------------------

	//#CM576841
	// Class method --------------------------------------------------
	//#CM576842
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:03 $") ;
	}

	//#CM576843
	// Constructors --------------------------------------------------
	//#CM576844
	/**
	 * Constructor
	 */
	public RFTId5741 ()
	{
		super() ;
		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576845
	// Public methods ------------------------------------------------
	//#CM576846
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM576847
	// Package methods -----------------------------------------------
	//#CM576848
	// Protected methods ---------------------------------------------
	//#CM576849
	// Private methods -----------------------------------------------

}
//#CM576850
//end of class


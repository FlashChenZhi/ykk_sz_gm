// $Id: RFTId5831.java,v 1.2 2006/12/07 09:00:02 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576910
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576911
/**
 * Inventory check result response ID=5831 telegraph message process<BR>
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of ID5831 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td><td></td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td><td></td></tr>
 * <tr><td>ID</td>				<td>4 byte</td><td></td></tr>
 * <tr><td>Handy send time</td><td>6 byte</td><td></td></tr>
 * <tr><td>Server send time</td>	<td>6 byte</td><td></td></tr>
 * <tr><td>Handy no..</td>	<td>3 byte</td><td></td></tr>
 * <tr><td>Worker code</td>	<td>4 byte</td><td></td></tr>
 * <tr><td>Response flag</td>		<td>1 byte</td>
 *     <td>0:Normal <BR>2:Update via another terminal<BR>5:date/time process<BR>8:Stock data does not exist<BR>9:Error</td></tr>
 * <tr><td>Error details</td>		<td>2 byte</td><td></td></tr>
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
public class RFTId5831 extends SendIdMessage
{
	//#CM576912
	// Class fields --------------------------------------------------
	//#CM576913
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM576914
	/**
	 * ID number
	 */
	public static final String ID = "5831";
	
	//#CM576915
	/**
	 * Field to express "updated via another terminal" error message
	 * This is defined here to use when the value differs
	 */
	public static final String ANS_CODE_UPDATE_FINISH = "2";

	//#CM576916
	// Class variables -----------------------------------------------

	//#CM576917
	// Class method --------------------------------------------------
	//#CM576918
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/12/07 09:00:02 $";
	}

	//#CM576919
	// Constructors --------------------------------------------------
	//#CM576920
	/**
	 * Constructor
	 */
	public RFTId5831 ()
	{
		super() ;
		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576921
	// Public methods ------------------------------------------------
	//#CM576922
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	//#CM576923
	// Package methods -----------------------------------------------
	//#CM576924
	// Protected methods ---------------------------------------------
	//#CM576925
	// Private methods -----------------------------------------------

}
//#CM576926
//end of class


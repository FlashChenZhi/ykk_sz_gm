// $Id: RFTId5631.java,v 1.2 2006/09/27 03:00:33 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM10411
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10412
/**
 * Process the response electronic statement of the Unplanned storage result.
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Electronic statement configuration of Id5631</CAPTION>
 * <TR><TH>Item Name</TH>				<TH>Length</TH>	<TH>contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5631</td></tr>
 * <tr><td>Handy Transmission time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server Transmission time</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy terminalNo.</td>		<td>3 byte</td>	<td>  </td></tr>
 * <tr><td></td>		<td>4 byte</td>	<td>  </td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal  5:Daily process under processing
 * 													<br>6:maintaining in process at other work stationit  9:error</td></tr>
 * <tr><td>error detail</td>			<td>2 byte</td>	<td>  </td></tr>
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
public class RFTId5631 extends SendIdMessage
{
	//#CM10413
	// Class fields --------------------------------------------------
	//#CM10414
	/**
	 * Define offset for the person in charge code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM10415
	/**
	 * ID No.
	 */
	public static final String ID = "5631";
	
	//#CM10416
	// Class variables -----------------------------------------------

	//#CM10417
	// Class method --------------------------------------------------
	//#CM10418
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/09/27 03:00:33 $") ;
	}

	//#CM10419
	// Constructors --------------------------------------------------
	//#CM10420
	/**
	 * Constructors
	 */
	public RFTId5631 ()
	{
		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM10421
	// Public methods ------------------------------------------------
	//#CM10422
	/**
	 * Set the person in charge code.
	 * @param  workerCode  
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}

	//#CM10423
	// Package methods -----------------------------------------------
	//#CM10424
	// Protected methods ---------------------------------------------
	//#CM10425
	// Private methods -----------------------------------------------

}
//#CM10426
//end of class


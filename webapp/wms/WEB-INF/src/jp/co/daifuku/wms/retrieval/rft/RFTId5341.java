// $Id: RFTId5341.java,v 1.3 2007/02/07 04:19:49 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM721991
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM721992
/**
 * Generate " ID = 5341 Response for Item Picking result data" electronic statement electronic statement for socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Construction of Id5341 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5341</td></tr>
 * <tr><td>Period for sending by Handy</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy Machine No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Worker Code</td>		<td>4 byte</td>	<td> </td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal, 6:Maintenance in progress via other terminal, 9:Error</td></tr>
 * <tr><td>Details of error</td>			<td>2 byte</td>	<td> </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>200?/??/??</TD><TD>matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:49 $
 * @author  $Author: suresh $
 */

public class RFTId5341 extends SendIdMessage
{
	//#CM721993
	// Class field --------------------------------------------------
	//#CM721994
	/**
	 * Offset the Personnel Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721995
	/**
	 * ID No.
	 */
	public static final String ID = "5341";

	
	//#CM721996
	// Class method ------------------------------------------------
	//#CM721997
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:49 $";
	}
	
	//#CM721998
	// Constructors -------------------------------------------------
	//#CM721999
	/**
	 * Constructor
	 */
	public RFTId5341 ()
	{
		super();

		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM722000
	// Public methods -----------------------------------------------
	//#CM722001
	/**
	 * Set the Personnel code.
	 * @param workerCode Personnel Code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE);
	}

	//#CM722002
	// Package methods ----------------------------------------------
	
	//#CM722003
	// Protected methods --------------------------------------------
	
	//#CM722004
	// Private methods ----------------------------------------------

}
//#CM722005
//end of class

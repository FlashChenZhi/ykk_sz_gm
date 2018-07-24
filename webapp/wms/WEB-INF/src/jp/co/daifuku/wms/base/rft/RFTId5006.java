//#CM702306
//$Id: RFTId5006.java,v 1.2 2006/11/14 06:09:17 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702307
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702308
/**
 * Make "Work Data response ID =   5006" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5006</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td> Detailed Error</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:17 $
 * @author  $Author: suresh $
 */

public class RFTId5006 extends SendIdMessage
{
	//#CM702309
	// Class field --------------------------------------------------
	//#CM702310
	/**
	 * Offset of Person in charge code
	 */
	public static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702311
	/**
	 * ID Number
	 */
	public static final String ID = "5006";

	//#CM702312
	// Class method ------------------------------------------------
	//#CM702313
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:17 $";
	}

	//#CM702314
	// Constructors -------------------------------------------------
	//#CM702315
	/**
	 * Generate the instance. 
	 */
	public RFTId5006()
	{
		super();

		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702316
	// Public methods -----------------------------------------------
	
	//#CM702317
	/**
	 * Set Person in charge code
	 * @param workerCode	Person in charge code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM702318
	// Package methods ----------------------------------------------

	//#CM702319
	// Protected methods --------------------------------------------

	//#CM702320
	// Private methods ----------------------------------------------

}
//#CM702321
//end of class

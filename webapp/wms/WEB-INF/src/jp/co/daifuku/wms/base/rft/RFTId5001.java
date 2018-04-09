//#CM702217
//$Id: RFTId5001.java,v 1.2 2006/11/14 06:09:15 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702218
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702219
/**
 * Make "System report response ID =   5001" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5001</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Server date and time</td>		<td>14 byte</td></tr>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:15 $
 * @author  $Author: suresh $
 */

public class RFTId5001 extends SendIdMessage
{
	//#CM702220
	// Class field --------------------------------------------------
	//#CM702221
	/**
	 * Offset of Server date and time
	 */
	private static final int OFF_SERVER_DATE = LEN_HEADER;
	
	//#CM702222
	/**
	 * ID Number
	 */
	public static final String ID = "5001";

	//#CM702223
	// Class method ------------------------------------------------
	//#CM702224
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:15 $";
	}

	//#CM702225
	// Constructors -------------------------------------------------
	//#CM702226
	/**
	 * Generate the instance. 
	 */
	public RFTId5001()
	{
		super();

		offAnsCode = OFF_SERVER_DATE + LEN_SERVER_DATE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702227
	// Public methods -----------------------------------------------
	//#CM702228
	/**
	 * Set Server date and time.
	 * @param	serverDate Server date and time
	 */
	public void setServerDate(String serverDate)
	{
		setToBuffer(serverDate, OFF_SERVER_DATE);
	}

	//#CM702229
	// Package methods ----------------------------------------------

	//#CM702230
	// Protected methods --------------------------------------------

	//#CM702231
	// Private methods ----------------------------------------------

}
//#CM702232
//end of class

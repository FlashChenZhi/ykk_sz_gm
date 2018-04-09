//#CM702292
//$Id: RFTId5005.java,v 1.2 2006/11/14 06:09:17 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702293
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702294
/**
 * Make "Response ID of the response sending again =   5005" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5005</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td> Detailed Error</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/05/22</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:17 $
 * @author  $Author: suresh $
 */

public class RFTId5005 extends SendIdMessage
{
	//#CM702295
	// Class field --------------------------------------------------
	
	//#CM702296
	/**
	 * ID Number
	 */
	public static final String ID = "5005";

	//#CM702297
	// Class method ------------------------------------------------
	//#CM702298
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:17 $";
	}

	//#CM702299
	// Constructors -------------------------------------------------
	//#CM702300
	/**
	 * Generate the instance. 
	 */
	public RFTId5005()
	{
		super();

		offAnsCode = LEN_HEADER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702301
	// Public methods -----------------------------------------------

	//#CM702302
	// Package methods ----------------------------------------------

	//#CM702303
	// Protected methods --------------------------------------------

	//#CM702304
	// Private methods ----------------------------------------------

}
//#CM702305
//end of class

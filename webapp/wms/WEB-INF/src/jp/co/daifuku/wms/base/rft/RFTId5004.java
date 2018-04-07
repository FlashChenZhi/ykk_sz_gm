//#CM702278
//$Id: RFTId5004.java,v 1.2 2006/11/14 06:09:16 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM702279
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM702280
/**
 * Make "Online confirmation response ID =   5004" telegram in the socket communication. <BR>
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5004</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:16 $
 * @author  $Author: suresh $
 */

public class RFTId5004 extends SendIdMessage
{
	//#CM702281
	// Class field --------------------------------------------------

	//#CM702282
	/**
	 * ID Number
	 */
	public static final String ID = "5004";

	//#CM702283
	// Class method ------------------------------------------------
	//#CM702284
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $Date: 2006/11/14 06:09:16 $");
	}

	//#CM702285
	// Constructors -------------------------------------------------
	//#CM702286
	/**
	 * Generate the instance. 
	 */
	public RFTId5004()
	{
		super();
		
		offAnsCode = OFF_RFTNO + LEN_RFTNO;
		offEtx = offAnsCode + LEN_ANS_CODE;
		length = offEtx + LEN_ETX;
	}

	//#CM702287
	// Public methods -----------------------------------------------

	//#CM702288
	// Package methods ----------------------------------------------

	//#CM702289
	// Protected methods --------------------------------------------

	//#CM702290
	// Private methods ----------------------------------------------

}
//#CM702291
//end of class



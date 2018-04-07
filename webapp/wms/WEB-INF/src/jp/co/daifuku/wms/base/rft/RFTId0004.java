//#CM701997
//$Id: RFTId0004.java,v 1.2 2006/11/14 06:09:11 suresh Exp $

package jp.co.daifuku.wms.base.rft;
import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM701998
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM701999
/**
 * Socket communication of  [Online confirmation request ID = 0004] Process telegram. <BR>
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id0004</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>	<td>3 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:11 $
 * @author  $Author: suresh $
 */
public class RFTId0004 extends RecvIdMessage{
	//#CM702000
	// Class field --------------------------------------------------

	//#CM702001
	// Class method ------------------------------------------------

	//#CM702002
	/**
	 * ID Number
	 */
	public static final String ID = "0004";

	//#CM702003
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $Date: 2006/11/14 06:09:11 $");
	}

	//#CM702004
	// Constructors -------------------------------------------------
	//#CM702005
	/**
	 * Generate the instance. 
	 */
	public RFTId0004()
	{
		super();
		
		offEtx = OFF_RFTNO + LEN_RFTNO;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
		
	}

	//#CM702006
	// Public methods -----------------------------------------------

	//#CM702007
	// Package methods ----------------------------------------------

	//#CM702008
	// Protected methods --------------------------------------------

    //#CM702009
    // Private methods ----------------------------------------------

}
//#CM702010
//end of class

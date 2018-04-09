//#CM721900
//$Id: RFTId5331.java,v 1.3 2007/02/07 04:19:48 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM721901
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
//#CM721902
/**
 * Process the " ID = 5331 Response for Order Picking result data" electronic statement electronic statement for socket communication.<BR>
 *
 * <p>
 * <TABLE BORDER="1">
 * <CAPTION>Construction of Id5331 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>			<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>			<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td>			<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>			<td>5331</td></tr>
 * <tr><td>Period for sending by Terminal</td>		<td>6 byte</td>			<td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>			<td>HHMMSS</td></tr>
 * <tr><td>Terminal machine No.</td>			<td>3 byte</td>			<td> </td></tr>
 * <tr><td>Worker Code</td>		<td>4 byte</td>			<td> </td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>			<td>0:Normal, 6:Maintenance in progress via other terminal, 9:Error</td></tr>
 * <tr><td>Details of error</td>			<td>2 byte</td>			<td> </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>			<td>0x03</td></tr>
 * </TABLE>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/04</TD><TD>E.Takeda</TD><TD>New creation</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:48 $
 * @author  $Author: suresh $
 */

public class RFTId5331 extends SendIdMessage
{
    //#CM721903
    // Class field --------------------------------------------------
	
	//#CM721904
	/**
	 * Offset the Personnel Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721905
	/**
	 * ID No.
	 */
	public static final String ID = "5331";
	
	//#CM721906
	// Class method ------------------------------------------------
	//#CM721907
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:48 $";
	}
	
	//#CM721908
	// Constructors -------------------------------------------------
	//#CM721909
	/**
	 * Constructor
	 */
	public RFTId5331 ()
	{
		super();
		
		offAnsCode = OFF_WORKER_CODE + LEN_WORKER_CODE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM721910
	// Public methods -----------------------------------------------
	//#CM721911
	/**
	 * Set the Personnel code.
	 * @param workerCode Personnel Code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE);
	}
	
	//#CM721912
	// Package methods ----------------------------------------------
	//#CM721913
	// Protected methods --------------------------------------------
	//#CM721914
	// Private methods ----------------------------------------------

}
//#CM721915
//end of class

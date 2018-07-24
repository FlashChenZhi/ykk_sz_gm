// $Id: RFTId0021.java,v 1.2 2006/11/14 06:09:14 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702152
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702153
/**
 * Process "List demand Id of the date =   0021" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id1021</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td></tr>
 * <tr><td>Work type</td>			<td>2 byte</td></tr>
 * <tr><td>Detail Work type</td>		<td>1 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:14 $
 * @author  $Author: suresh $
 */

public class RFTId0021 extends RecvIdMessage
{
	//#CM702154
	// Class field --------------------------------------------------
	//#CM702155
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702156
	/**
	 * Offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702157
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	//#CM702158
	/**
	 * Detail Offset of Work type
	 */
	private static final int OFF_WORK_DETAILS = OFF_WORK_TYPE + LEN_WORK_TYPE;

	//#CM702159
	// Class variables ----------------------------------------------

	//#CM702160
	// Class method -------------------------------------------------
	//#CM702161
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:14 $";
	}

	//#CM702162
	//Constructors --------------------------------------------------
	//#CM702163
	/**
	 * Generate the instance. 
	 */
	public RFTId0021()
	{
		super();

		offEtx = OFF_WORK_DETAILS + LEN_WORK_DETAILS;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM702164
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0021 Person in charge code input Id = 0021 telegram
	 */
	public RFTId0021(byte[] rftId0021)
	{
		this();

		setReceiveMessage(rftId0021);
	}

	//#CM702165
	// Public methods -----------------------------------------------
	//#CM702166
	/**
	 * Acquire Worker code from Plan date list request telegram. 
	 * @return   Worker code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}

	//#CM702167
	/**
	 * Acquire Consignor code from Plan date list request telegram. 
	 * @return   Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode =
			getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	//#CM702168
	/**
	 * Acquire Work type from Plan date list request telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE, LEN_WORK_TYPE);
		return workType;
	}

	//#CM702169
	/**
	 * Acquire Detail Work type from Plan date list request telegram. 
	 * @return   Detail Work type
	 */
	public String getWorkDetails()
	{
		String workDetails = getFromBuffer(OFF_WORK_DETAILS, LEN_WORK_DETAILS);
		return workDetails;
	}

	//#CM702170
	// Protected methods ----------------------------------------------

	//#CM702171
	// Package methods ----------------------------------------------

	//#CM702172
	// Private methods ----------------------------------------------

}
//#CM702173
//end of class

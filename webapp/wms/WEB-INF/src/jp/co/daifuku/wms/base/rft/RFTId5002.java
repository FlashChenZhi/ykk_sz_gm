//#CM702233
//$Id: RFTId5002.java,v 1.2 2006/11/14 06:09:16 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702234
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702235
/**
 * Make "Work beginning response ID =   5002" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5002</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Work type</td>			<td>2 byte</td></tr>
 * <tr><td>Detail Work type</td>		<td>1 byte</td></tr>
 * <tr><td>Plan date</td>				<td>8 byte</td></tr>
 * <tr><td>Worker name</td>			<td>20 byte</td></tr>
 * <tr><td>Server date and time</td>			<td>14 byte</td></tr>
 * <tr><td>Working ID</td>			<td>4 byte</td></tr>
 * <tr><td>Response flag</td>			<td>4 byte</td></tr>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:16 $
 * @author  $Author: suresh $
 */

public class RFTId5002 extends SendIdMessage
{
	//#CM702236
	// Class field --------------------------------------------------
	//#CM702237
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702238
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702239
	/**
	 * Detail Offset of Work type
	 */
	private static final int OFF_WORK_DETAILS = OFF_WORK_TYPE + LEN_WORK_TYPE;

	//#CM702240
	/**
	 * Offset of Plan date
	 */
	private static final int OFF_PLAN_DATE = OFF_WORK_DETAILS + LEN_WORK_DETAILS;
	
	//#CM702241
	/**
	 * Offset of Worker name
	 */
	private static final int OFF_WORKER_NAME = OFF_PLAN_DATE + LEN_PLAN_DATE;

	//#CM702242
	/**
	 * Offset of Server date and time
	 */
	private static final int OFF_SERVER_DATE = OFF_WORKER_NAME + LEN_WORKER_NAME;

	//#CM702243
	/**
	 * Offset of Working ID
	 */
	private static final int OFF_WORKING_ID = OFF_SERVER_DATE + LEN_SERVER_DATE;

	//#CM702244
	/**
	 * ID Number
	 */
	public static final String ID = "5002";

	//#CM702245
	// Class method ------------------------------------------------
	//#CM702246
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:16 $";
	}

	//#CM702247
	// Constructors -------------------------------------------------
	//#CM702248
	/**
	 * Generate the instance. 
	 */
	public RFTId5002()
	{
		super();

		offAnsCode = OFF_WORKING_ID + LEN_WORKING_ID;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	//#CM702249
	// Public methods -----------------------------------------------
	//#CM702250
	/**
	 * Set Person in charge code. 
	 * @param	workerCode	Person in charge code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	//#CM702251
	/**
	 * Set Work type
	 * @param	workType	Work type
	 */
	public void setWorkType(String workType)
	{
		setToBuffer(workType, OFF_WORK_TYPE);
	}

	//#CM702252
	/**
	 * Detail Set Work type
	 * @param	workDetails	Detail Work type
	 */
	public void setWorkDetails(String workDetails)
	{
		setToBuffer(workDetails, OFF_WORK_DETAILS);
	}

	//#CM702253
	/**
	 * Set Plan date.
	 * @param	planDate	Plan date
	 */
	public void setPlanDate(String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	//#CM702254
	/**
	 * Set the person in charge name. 
	 * @param	workerName	Person in charge Name
	 */
	public void setWorkerName(String workerName)
	{
		setToBuffer(workerName, OFF_WORKER_NAME);
	}

	//#CM702255
	/**
	 * Set Server date and time.
	 * @param	serverDate	Server date and time
	 */
	public void setServerDate(String serverDate)
	{
		setToBuffer(serverDate, OFF_SERVER_DATE);
	}

	//#CM702256
	/**
	 * Set Working ID. 
	 * @param workingId
	 */
	public void setWorkingId(String workingId)
	{
		setToBuffer(workingId, OFF_WORKING_ID);
	}

	//#CM702257
	// Package methods ----------------------------------------------

	//#CM702258
	// Protected methods --------------------------------------------

	//#CM702259
	// Private methods ----------------------------------------------

}
//#CM702260
//end of class

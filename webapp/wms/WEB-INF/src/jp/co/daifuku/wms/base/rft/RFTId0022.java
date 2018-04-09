// $Id: RFTId0022.java,v 1.2 2006/11/14 06:09:15 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702174
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702175
/**
 * "Consignor  list Consignor list inquiry Id =   0022" telegram in the socket communication is processed. 
 *
 * <p>
 * <table border="1">
 * <tr><td>STX</td><td>1 byte</td></tr>
 * <tr><td>SEQ No</td><td>4 byte</td></tr>
 * <tr><td>ID</td><td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td><td>6 byte</td></tr>
 * <tr><td>Server transmission time</td><td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td><td>3 byte</td></tr>
 * <tr><td>Person in charge code</td><td>4 byte</td></tr>
 * <tr><td>Work plan date</td><td>8 byte</td></tr>
 * <tr><td>Work type</td><td>2 byte</td></tr>
 * <tr><td>Detail Work type</td><td>1 byte</td></tr>
 * <tr><td>ETX</td><td>1 byte</td></tr>
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

public class RFTId0022 extends RecvIdMessage
{
	//#CM702176
	// Class field --------------------------------------------------
	//#CM702177
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702178
	/**
	 * Offset of Work plan date
	 */
	private static final int OFF_PLAN_DATE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702179
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_PLAN_DATE + LEN_PLAN_DATE;

	//#CM702180
	/**
	 * Detail Offset of Work type
	 */
	private static final int OFF_WORK_DETAILS = OFF_WORK_TYPE + LEN_WORK_TYPE;

	//#CM702181
	/**
	 * ID Number
	 */
	public static final String ID = "0022";

	//#CM702182
	// Class variables ----------------------------------------------

	//#CM702183
	// Class method -------------------------------------------------
	//#CM702184
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:15 $";
	}

	//#CM702185
	//Constructors --------------------------------------------------
	//#CM702186
	/**
	 * Generate the instance. 
	 */
	public RFTId0022()
	{
		super();

		offEtx = OFF_WORK_DETAILS + LEN_WORK_DETAILS;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM702187
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0022 Person in charge code input Id = 0022 telegram
	 */
	public RFTId0022(byte[] rftId0022)
	{
		super();
		setReceiveMessage(rftId0022);
	}

	//#CM702188
	// Public methods -----------------------------------------------
	//#CM702189
	/**
	 * Acquire Person in charge code from consignor list inquiry telegram. 
	 * @return   Person in charge code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}

	//#CM702190
	/**
	 * Acquire Work plan date from consignor list inquiry telegram. 
	 * @return   Work plan date
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}

	//#CM702191
	/**
	 * Acquire Work type from consignor list inquiry telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE, LEN_WORK_TYPE);
		return workType;
	}

	//#CM702192
	/**
	 * Acquire Detail Work type from consignor list inquiry telegram. 
	 * @return   Detail Work type
	 */
	public String getWorkDetails()
	{
		String workDetails = getFromBuffer(OFF_WORK_DETAILS, LEN_WORK_DETAILS);
		return workDetails;
	}

	//#CM702193
	// Package methods ----------------------------------------------

	//#CM702194
	// Package methods ----------------------------------------------

	//#CM702195
	// Private methods ----------------------------------------------

}
//#CM702196
//end of class

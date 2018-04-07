// $Id: RFTId0011.java,v 1.2 2006/11/14 06:09:13 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702075
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702076
/**
 * Socket communication of [Plan date Inquiry Id = 0011] 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id0011</CAPTION>
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
 * <tr><td>Work plan date</td>			<td>16 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:13 $
 * @author  $Author: suresh $
 */

public class RFTId0011 extends RecvIdMessage
{
	//#CM702077
	// Class field --------------------------------------------------
	//#CM702078
	/**
	 * Offset of Worker code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702079
	/**
	 * Offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702080
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	//#CM702081
	/**
	 * Detail Offset of Work type
	 */
	private static final int OFF_WORK_DETAILS = OFF_WORK_TYPE + LEN_WORK_TYPE;

	//#CM702082
	/**
	 * Offset of Work plan date
	 */
	private static final int OFF_PLAN_DATE = OFF_WORK_DETAILS + LEN_WORK_DETAILS;

	//#CM702083
	/**
	 * ID Number
	 */
	public static final String ID = "0011";

	//#CM702084
	// Class variables ----------------------------------------------

	//#CM702085
	// Class method -------------------------------------------------
	//#CM702086
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:13 $";
	}

	//#CM702087
	// Constructors -------------------------------------------------
	//#CM702088
	/**
	 * Generate the instance. 
	 */
	public RFTId0011()
	{
		super();

		offEtx = OFF_PLAN_DATE + LEN_PLAN_DATE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM702089
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0011 Plan date Inquiry ID = 0011 telegram
	 */
	public RFTId0011(byte[] rftId0011)
	{
		this();

		setReceiveMessage(rftId0011);
	}

	//#CM702090
	// Public methods -----------------------------------------------
	//#CM702091
	/**
	 * Acquire Work code from Plan date Inquiry telegram. 
	 * @return   Worker code
	 */
	public String getWorkerCode()
	{
		String workerCode =
			getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}

	//#CM702092
	/**
	 * Acquire Work plan date from Plan date Inquiry telegram. 
	 * @return	Work plan date
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}

	//#CM702093
	/**
	 * Acquire Work type from Plan date Inquiry telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE, LEN_WORK_TYPE);
		return workType;
	}

	//#CM702094
	/**
	 * Acquire Detail Work type from Plan date Inquiry telegram. 
	 * @return   Detail Work type
	 */
	public String getWorkDetails()
	{
		String workDetails = getFromBuffer(OFF_WORK_DETAILS, LEN_WORK_DETAILS);
		return workDetails;
	}

	//#CM702095
	/**
	 * Acquire Consignor Code from Plan date Inquiry telegram. 
	 * @return   Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	//#CM702096
	// Package methods ----------------------------------------------

	//#CM702097
	// Protected methods --------------------------------------------

	//#CM702098
	// Private methods ----------------------------------------------

}
//#CM702099
//end of class

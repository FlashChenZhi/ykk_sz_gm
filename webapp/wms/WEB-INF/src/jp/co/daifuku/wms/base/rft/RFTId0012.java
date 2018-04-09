// $Id: RFTId0012.java,v 1.2 2006/11/14 06:09:13 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702100
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702101
/**
 * Socket communication [Consignor Code Inquiry Id = 0012]
 *
 * <p>
 * <table>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Work plan date</td>			<td>8 byte</td></tr>
 * <tr><td>Work type</td>			<td>2 byte</td></tr>
 * <tr><td>Detail Work type</td>		<td>1 byte</td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td></tr>
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

public class RFTId0012 extends RecvIdMessage
{
	//#CM702102
	// Class field --------------------------------------------------
	//#CM702103
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM702104
	/**
	 * Offset of Work plan date
	 */
	private static final int OFF_PLAN_DATE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702105
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_PLAN_DATE + LEN_PLAN_DATE;

	//#CM702106
	/**
	 * Detail Offset of Work type
	 */
	private static final int OFF_WORK_DETAILS = OFF_WORK_TYPE + LEN_WORK_TYPE;

	//#CM702107
	/**
	 * Offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORK_DETAILS + LEN_WORK_DETAILS;

	//#CM702108
	/**
	 * ID Number
	 */
	public static final String ID = "0012";

	//#CM702109
	// Class variables ----------------------------------------------

	//#CM702110
	// Class method -------------------------------------------------
	//#CM702111
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:13 $";
	}

	//#CM702112
	// Constructors -------------------------------------------------
	//#CM702113
	/**
	 * Generate the instance. 
	 */
	public RFTId0012()
	{
		super();

		offEtx = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM702114
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0012 Consignor Code Inquiry ID = 0012 telegram
	 */
	public RFTId0012(byte[] rftId0022)
	{
		this();

		setReceiveMessage(rftId0022);
	}

	//#CM702115
	// Public methods -----------------------------------------------
	//#CM702116
	/**
	 * Acquire Person in charge code from load main inquiry telegram. 
	 * @return   Person in charge code
	 */
	public String getWorkerCode()
	{
		String workerCode =
			getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}

	//#CM702117
	/**
	 * Acquire Work plan date from load main inquiry telegram. 
	 * @return	Work plan date
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}

	//#CM702118
	/**
	 * Acquire Work type from load main inquiry telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE, LEN_WORK_TYPE);
		return workType;
	}

	//#CM702119
	/**
	 * Acquire Detail Work type from load main inquiry telegram. 
	 * @return   Detail Work type
	 */
	public String getWorkDetails()
	{
		String workDetails = getFromBuffer(OFF_WORK_DETAILS, LEN_WORK_DETAILS);
		return workDetails;
	}

	//#CM702120
	/**
	 * Acquire Consignor Code from load main inquiry telegram. 
	 * @return   Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	//#CM702121
	// Package methods ----------------------------------------------

	//#CM702122
	// Protected methods --------------------------------------------

	//#CM702123
	// Private methods ----------------------------------------------

}
//#CM702124
//end of class

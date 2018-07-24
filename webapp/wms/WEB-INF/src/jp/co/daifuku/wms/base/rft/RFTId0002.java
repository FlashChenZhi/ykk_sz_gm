//#CM701956
//$Id: RFTId0002.java,v 1.2 2006/11/14 06:09:11 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701957
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 //#CM701958
 /**
  * Process Socket communication of [Work start report ID = 0002] telegram.
  * 
  * <p>
  * <table>
  * <tr><td>STX</td>				<td>1 byte</td></tr>
  * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
  * <tr><td>ID</td>					<td>4 byte</td></tr>
  * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
  * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
  * <tr><td>Work type</td>			<td>2 byte</td></tr>
  * <tr><td>Detail Work type</td>		<td>1 byte</td></tr>
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
  
public class RFTId0002 extends RecvIdMessage
{
	//#CM701959
	// Class field --------------------------------------------------
	//#CM701960
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM701961
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM701962
	/**
	 * Detail Offset of Work type
	 */
	private static final int OFF_WORK_DETAILS = OFF_WORK_TYPE + LEN_WORK_TYPE;

	//#CM701963
	/**
	 * ID Number
	 */
	public static final String ID = "0002";

	//#CM701964
	// Class variables ----------------------------------------------
	
	//#CM701965
	// Class method ------------------------------------------------
	//#CM701966
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:11 $";
	}
	
	//#CM701967
	// Constructors -------------------------------------------------
	//#CM701968
	/**
	 * Generate the instance. 
	 */
	public RFTId0002 ()
	{
		super();

		offEtx = OFF_WORK_DETAILS + LEN_WORK_DETAILS;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM701969
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0002 Work start report ID = 0002 telegram
	 */
	public RFTId0002 (byte[] rftId0002)
	{
		this();

		setReceiveMessage(rftId0002);
	}
	
	//#CM701970
	// Public methods -----------------------------------------------
	//#CM701971
	/**
	 * Acquire Person in charge code from Work start report telegram. 
	 * @return   Person in charge code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	//#CM701972
	/**
	 * Acquire Work type from Work start report telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE, LEN_WORK_TYPE);
		return workType;
	}
	
	//#CM701973
	/**
	 * Acquire Detail Work type from Work start report telegram. 
	 * @return   Detail Work type
	 */
	public String getWorkDetails()
	{
		String workDetails = getFromBuffer(OFF_WORK_DETAILS, LEN_WORK_DETAILS);
		return workDetails;
	}
	
	//#CM701974
	// Package methods ----------------------------------------------
	
	//#CM701975
	// Protected methods --------------------------------------------
	
	//#CM701976
	// Private methods ----------------------------------------------
	
}
//#CM701977
//end of class

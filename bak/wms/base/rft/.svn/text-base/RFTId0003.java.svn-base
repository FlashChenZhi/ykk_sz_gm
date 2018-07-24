//#CM701978
//$Id: RFTId0003.java,v 1.2 2006/11/14 06:09:11 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701979
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
  
 //#CM701980
 /**
  * Process Socket communication of [Work Completed Report Id = 0003] telegram.
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
  * <tr><td>ETX</td>				<td>1 byte</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>200?/??/??</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:11 $
  * @author  $Author: suresh $
  */
  
public class RFTId0003 extends RecvIdMessage
{
	//#CM701981
	// Class field --------------------------------------------------
	//#CM701982
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM701983
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM701984
	// Class variables ----------------------------------------------
	
	//#CM701985
	// Class method ------------------------------------------------
	//#CM701986
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:11 $";
	}
	
	//#CM701987
	// Constructors -------------------------------------------------
	//#CM701988
	/**
	 * Generate the instance. 
	 */
	public RFTId0003 ()
	{
		super();

		offEtx = OFF_WORK_TYPE + LEN_WORK_TYPE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM701989
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0003 Work Completed Report Id = 0003 telegram
	 */
	public RFTId0003 (byte[] rftId0003)
	{
		super();

		setReceiveMessage(rftId0003);
	}
	
	//#CM701990
	// Public methods -----------------------------------------------
	//#CM701991
	/**
	 * Acquire Person in charge code from work Completed report telegram. 
	 * @return   Person in charge code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM701992
	/**
	 * Acquire Work type from work Completed report telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE , LEN_WORK_TYPE) ;
		return workType;
	}
	
	//#CM701993
	// Package methods ----------------------------------------------
	
	//#CM701994
	// Protected methods --------------------------------------------
	
	//#CM701995
	// Private methods ----------------------------------------------
	
}
//#CM701996
//end of class

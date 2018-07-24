//#CM702011
//$Id: RFTId0005.java,v 1.2 2006/11/14 06:09:12 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702012
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 //#CM702013
 /**
  * Process the [Request of sending response ID again = 0005] telegram.
  * 
  * <p>
  * <table>
  * <tr><td>STX</td>				<td>1 byte</td></tr>
  * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
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
  * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:12 $
  * @author  $Author: suresh $
  */
  
public class RFTId0005 extends RecvIdMessage
{
	//#CM702014
	// Class field --------------------------------------------------
	//#CM702015
	/**
	 * Offset of Person in charge code
	 */
	protected static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702016
	/**
	 * Offset of Work type
	 */
	private static final int OFF_WORK_TYPE = OFF_WORKER_CODE + LEN_WORKER_CODE ;

	//#CM702017
	/**
	 * ID Number
	 */
	public static final String ID = "0005";
	
	//#CM702018
	// Class variables ----------------------------------------------
	
	//#CM702019
	// Class method ------------------------------------------------
	//#CM702020
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:12 $";
	}
	
	//#CM702021
	// Constructors -------------------------------------------------
	//#CM702022
	/**
	 * Generate the instance. 
	 */
	public RFTId0005 ()
	{
		super();

		offEtx = OFF_WORK_TYPE + LEN_WORK_TYPE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM702023
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0005 Work start report ID = 0005 telegram
	 */
	public RFTId0005 (byte[] rftId0005)
	{
		this();

		setReceiveMessage(rftId0005);
	}
	
	//#CM702024
	// Public methods -----------------------------------------------
	//#CM702025
	/**
	 * Acquire Person in charge code from work Data notification telegram. 
	 * @return   Person in charge code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	//#CM702026
	/**
	 * Acquire Work type from work Data report telegram. 
	 * @return   Work type
	 */
	public String getWorkType()
	{
		String workType = getFromBuffer(OFF_WORK_TYPE , LEN_WORK_TYPE);
		return workType.trim();
	}
	
	//#CM702027
	// Package methods ----------------------------------------------
	
	//#CM702028
	// Protected methods --------------------------------------------
	
	//#CM702029
	// Private methods ----------------------------------------------
	
}
//#CM702030
//end of class

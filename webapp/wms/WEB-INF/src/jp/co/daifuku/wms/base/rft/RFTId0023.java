//#CM702197
//$Id: RFTId0023.java,v 1.2 2006/11/14 06:09:15 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM702198
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
  
 //#CM702199
 /**
  * "Area list Inquiry Id =   0023" telegram in the socket communication is processed. <BR>
  *
  * <p>
  * <table border="1">
  * <tr><td>STX</td>			<td>1 byte</td></tr>
  * <tr><td>SEQ NO</td>			<td>4 byte</td></tr>
  * <tr><td>ID</td>				<td>4 byte</td></tr>
  * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Handy title machine No</td>	<td>3 byte</td></tr>
  * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
  * <tr><td>Area No.</td>			<td>3 byte</td></tr>
  * <tr><td>ETX</td>			<td>1 byte</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2005/11/04</TD><TD>etakeda</TD><TD>New making</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:15 $
  * @author  $Author: suresh $
  */
  
public class RFTId0023 extends RecvIdMessage
{
//#CM702200
//  Class field --------------------------------------------------
	//#CM702201
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM702202
	/**
	 * Offset of Area No.
	 */
	private static final int OFF_AREA_NO = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM702203
	/**
	 * ID Number
	 */
	public static final String ID = "0023";
	
	//#CM702204
	// Class variables ----------------------------------------------
	
	//#CM702205
	// Class method
	//#CM702206
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:15 $";
	}
	
	//#CM702207
	//Constructors --------------------------------------------------
	//#CM702208
	/**
	 * Constructor
	 */
	public RFTId0023 ()
	{
		super() ;

		offEtx = OFF_AREA_NO + LEN_AREA_NO;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM702209
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0322  Id = 0322 telegram
	 */
	public RFTId0023 (byte[] rftId0322)
	{
		this();

		setReceiveMessage(rftId0322) ;
	}
	
	//#CM702210
	// Public methods -----------------------------------------------
	//#CM702211
	/**
	 * Acquire Person in charge code from area list demand telegram. 


	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM702212
	/**
	 * Acquire Area  No. from area list request telegram. 
	 * @return areaNo Area No.
	 */
	public String getAreaNo()
	{
	    String areaNo = getFromBuffer(OFF_AREA_NO , LEN_AREA_NO) ;
		return areaNo.trim();
	}
	
	//#CM702213
	// Package methods ----------------------------------------------
	
	//#CM702214
	// Protected methods ----------------------------------------------
	
	//#CM702215
	// Private methods ----------------------------------------------
	
}
//#CM702216
//end of class

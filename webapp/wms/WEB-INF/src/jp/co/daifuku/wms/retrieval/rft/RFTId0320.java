// $Id: RFTId0320.java,v 1.3 2007/02/07 04:19:45 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM721569
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
  
 //#CM721570
 /**
  * Process the " Id = 0320 Request for Picking Order List" electronic statement electronic statement for socket communication.
  *
  * <p>
  * <table border="1">
  * <CAPTION>Construction of Id0320 electronic statement.</CAPTION>
  * <TR><TH>Field item name</TH>				<TH>Length</TH>	<TH>Content</TH></TR>
  * <tr><td>STX</td>				<td>1 byte</td> <td>0x02</td></tr>
  * <tr><td>SEQ NO</td>				<td>4 byte</td> <td></td></tr>
  * <tr><td>ID</td>					<td>4 byte</td> <td>0320</td></tr>
  * <tr><td>Period for sending by Handy</td>	<td>6 byte</td> <td>HHMMSS</td></tr>
  * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td> <td>HHMMSS</td></tr>
  * <tr><td>Handy Machine No.</td>		<td>3 byte</td> <td></td></tr>
  * <tr><td>Personnel Code</td>		<td>4 byte</td> <td></td></tr>
  * <tr><td>Planned Work Date</td>			<td>8 byte</td> <td>YYYYMMDD</td></tr>
  * <tr><td>Consignor Code</td>			<td>16 byte</td><td></td></tr>
  * <tr><td>Case/Piece division</td>	<td>1 byte</td> <td>0: All, 1: Case, 2: Piece, 3: None</td></tr>
  * <tr><td>ETX</td>				<td>1 byte</td> <td>0x03</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:45 $
  * @author  $Author: suresh $
  */
  
  public class RFTId0320 extends RecvIdMessage
{
	//#CM721571
	// Class field --------------------------------------------------
	//#CM721572
	/**
	 * Offset the Personnel Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721573
	/**
	 * Offset the Planned Work Date.
	 */
	private static final int OFF_PLAN_DATE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM721574
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE;
	
	//#CM721575
	/**
	 * Offset the Case/Piece division.
	 */
	private static final int OFF_WORK_FORM = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	//#CM721576
	/**
	 * A field that represents "All" as Case/Piece division.
	 */
	public static final String CASE_PIECE_All = "0" ;
	
	//#CM721577
	/**
	 * ID No.
	 */
	public static final String ID = "0320";
	
	//#CM721578
	// Class variables ----------------------------------------------

	//#CM721579
	// Class method
	//#CM721580
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $Date: 2007/02/07 04:19:45 $") ;
	}
	
	//#CM721581
	//Constructors --------------------------------------------------
	//#CM721582
	/**
	 * Constructor
	 */
	public RFTId0320 ()
	{
		super() ;
		offEtx = OFF_WORK_FORM + LEN_WORK_FORM;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM721583
	/**
	 * Pass the electronic statement received from RFT to the Constructor.
	 * @param rftId0320 Input the Personnel Code Id = 0320 electronic statement
	 */
	public RFTId0320 (byte[] rftId0320)
	{
		super() ;
		setReceiveMessage(rftId0320) ;
	}
	
	//#CM721584
	// Public methods -----------------------------------------------
	//#CM721585
	/**
	 * Obtain the Personnel Code from an electronic statement requesting for the Picking Order List.
	 * @return   Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM721586
	/**
	 * Obtain the Planned Work Date from an electronic statement requesting for the Picking Order List.
	 * @return   WorkType
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE , LEN_PLAN_DATE) ;
		return planDate.trim();
	}
	
	//#CM721587
	/**
	 * Obtain the Consignor code from an electronic statement requesting for the Picking Order List.
	 * @return   ConsignorCode
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE , LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM721588
	/**
	 * Obtain the Case/Piece division from an electronic statement requesting for the Picking Order List.
	 * @return   casePieceFlag
	 */
	public String getWorkForm()
	{
		String workForm = getFromBuffer(OFF_WORK_FORM , LEN_WORK_FORM) ;
		return workForm.trim();
	}

	//#CM721589
	// Package methods ----------------------------------------------
	
	//#CM721590
	// Protected methods ----------------------------------------------
	
	//#CM721591
	// Private methods ----------------------------------------------
	
}
//#CM721592
//end of class

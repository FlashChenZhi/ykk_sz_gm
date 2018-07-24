// $Id: RFTId0330.java,v 1.3 2007/02/07 04:19:46 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM721593
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 //#CM721594
 /**
  * Process the " ID = 0330 Request for starting Order Picking" electronic statement electronic statement for socket communication.
  *
  * <p>
 * <table border="1">
 * <CAPTION>Construction of Id0330 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>	<TH>Content</TH></TR>
  * <tr><td>STX:</td>				<td>1 byte</td>	<td>0x02</td></tr>
  * <tr><td>SEQ NO:</td>			<td>4 byte</td>	<td>Not available</td></tr>
  * <tr><td>ID:</td>				<td>4 byte</td>	<td>0330</td></tr>
  * <tr><td>Period for sending by Terminal:</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
  * <tr><td>Period for sending by SERVER:</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
  * <tr><td>Terminal machine No.:</td>		<td>3 byte</td>	<td> </td></tr>
  * <tr><td>Worker Code:</td>		<td>4 byte</td>	<td> </td></tr>
  * <tr><td>Consignor Code:</td>		<td>16 byte</td><td> </td></tr>
  * <tr><td>Planned Picking Date:</td>		<td>8 byte</td>	<td> </td></tr>
  * <tr><td>Selected Case/Piece division</td><td>1 byte</td><td>0: All, 1: Case, 2: Piece, 3: None</td></tr>
  * <tr><td>Order No. (1):</td>		<td>16 byte</td><td> </td></tr>
  * <tr><td>Order No. (2):</td>		<td>16 byte</td><td> </td></tr>
  * <tr><td>Order No. (3):</td>		<td>16 byte</td><td> </td></tr>
  * <tr><td>Order No. (4):</td>		<td>16 byte</td><td> </td></tr>
  * <tr><td>Area No.:</td>			<td>3 byte</td>	<td> </td></tr>
  * <tr><td>Zone No.:</td>			<td>3 byte</td>	<td> </td></tr>
  * <tr><td>Picking Work division:</td>		<td>1 byte</td>	<td>1: Specify Order, 2: Specify Area</td></tr>
  * <tr><td>ETX:</td>				<td>1 byte</td>	<td>0x03</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2005/12/02</TD><TD>E.Takada</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:46 $
  * @author  $Author: suresh $
  */

public class RFTId0330 extends RecvIdMessage
{
	//#CM721595
	// Class field --------------------------------------------------
	//#CM721596
	/**
	 * Offset the Worker Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM721597
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM721598
	/**
	 * Offset the Planned Picking Date.
	 */
	private static final int OFF_PICKING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM721599
	/**
	 * Selected Case/Piece division
	 */
	private static final int OFF_SELECT_CASE_PIECE_FLAG = OFF_PICKING_PLAN_DATE + LEN_PLAN_DATE;
	
	//#CM721600
	/**
	 * Offset the Order No. (1).
	 */
	private static final int OFF_ORDER_NO_1 = OFF_SELECT_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;
	
	//#CM721601
	/**
	 * Offset the Order No. (2).
	 */
	private static final int OFF_ORDER_NO_2 = OFF_ORDER_NO_1 + LEN_ORDER_NO;
	
	//#CM721602
	/**
	 * Offset the Order No. (3).
	 */
	private static final int OFF_ORDER_NO_3 = OFF_ORDER_NO_2 + LEN_ORDER_NO;
	
	//#CM721603
	/**
	 * Offset the Order No. (4).
	 */
	private static final int OFF_ORDER_NO_4 = OFF_ORDER_NO_3 + LEN_ORDER_NO;
	
	//#CM721604
	/**
	 * Offset the Area No.
	 */
	private static final int OFF_AREA_NO = OFF_ORDER_NO_4 + LEN_ORDER_NO;
	
	//#CM721605
	/**
	 * Offset the Zone No.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;
	
	//#CM721606
	/**
	 * Offset the Picking Work division.
	 */
	private static final int OFF_PICK_WORK_TYPE = OFF_ZONE_NO + LEN_ZONE_NO;
	
	//#CM721607
	/**
	 * ID No.
	 */
	public static final String ID = "0330";
	
	//#CM721608
	/**
	 * A field that represents "All" as Work Type.
	 */
	public static final String WORK_FORM_All = "0";
	
	//#CM721609
	/**
	 * A field that represents Picking Work division (Specify Order).
	 */
	public static final String JOB_TYPE_ORDER = "1";
	
	//#CM721610
	/**
	 * A field that represents Picking Work division (Specify Area).
	 */
	public static final String JOB_TYPE_AREA = "2";

	//#CM721611
	// Class variables ----------------------------------------------
	//#CM721612
	/**
	 * A field that represents process name of this class.
	 */
	public static final String CLASS_NAME = "ID0330";
	//#CM721613
	// Class method -------------------------------------------------
	//#CM721614
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $Date: 2007/02/07 04:19:46 $") ;
	}
	
	//#CM721615
	// Constructors -------------------------------------------------
	//#CM721616
	/**
	 * Constructor
	 */
	public RFTId0330 ()
	{
		super() ;
		offEtx = OFF_PICK_WORK_TYPE + LEN_JOB_TYPE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM721617
	/**
	 * Pass the electronic statement received from RFT to the Constructor.
	 * @param rftId0330 Request for starting Order Picking ID = 0330 electronic statement
	 */
	public RFTId0330 (byte[] rftId0330)
	{
		this();

		setReceiveMessage(rftId0330);
	}
	
	//#CM721618
	// Public methods -----------------------------------------------
	//#CM721619
	/**
	 * Obtain the Worker code from an electronic statement requesting for starting the Order Picking.
	 * @return   Worker Code  Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	//#CM721620
	/**
	 * Obtain the Consignor code from an electronic statement requesting for starting the Order Picking.
	 * @return   Consignor Code  Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	//#CM721621
	/**
	 * Obtain the Planned Picking Date from an electronic statement requesting for starting the Order Picking.
	 * @return   Picking Plan Date  Planned Picking Date
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PICKING_PLAN_DATE, LEN_PLAN_DATE);
		return planDate.trim();
	}
	
	//#CM721622
	/**
	 * Obtain the Selected Case/Piece division from an electronic statement requesting for starting the Order Picking.
	 * @return		Select Case Piece Flag	Selected Case/Piece division
	 */
	public String getSelectCasePieceFlag()
	{
		String selectCasePieceFleg = getFromBuffer(OFF_SELECT_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG);
		return selectCasePieceFleg.trim();
	}
	
	//#CM721623
	/**
	 * Obtain the Order No. (1) from an electronic statement requesting for starting the Order Picking.
	 * @return   OrderNo_1 Order No. (1)
	 */
	public String getOrderNo_1()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_1, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721624
	/**
	 * Obtain the Order No. (2) from an electronic statement requesting for starting the Order Picking.
	 * @return   OrderNo_2 Order No. (2)		
	 */
	public String getOrderNo_2()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_2, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721625
	/**
	 * Obtain the Order No. (3) from an electronic statement requesting for starting the Order Picking.
	 * @return   OrderNo_3 Order No. (3)
	 */
	public String getOrderNo_3()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_3, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721626
	/**
	 * Obtain the Order No. (4) from an electronic statement requesting for starting the Order Picking.
	 * @return   OrderNo_4 Order No. (4)
	 */
	public String getOrderNo_4()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_4, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721627
	/**
	 * Obtain the Area No. from an electronic statement requesting for starting the Order Picking.
	 * @return   Area No	Area No.
	 */
	public String getAreaNo()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO);
		return areaNo.trim();
	}
	
	//#CM721628
	/**
	 * Obtain the Zone No. from an electronic statement requesting for starting the Order Picking.
	 * @return   Zone No	Zone No.		
	 */
	public String getZoneNo()
	{
		String zoneNo = getFromBuffer(OFF_ZONE_NO, LEN_ZONE_NO);
		return zoneNo.trim();
	}
	
	//#CM721629
	/**
	 * Obtain the Picking Work division from an electronic statement requesting for starting the Order Picking.
	 * @return   Pick Work Type	Picking Work division
	 */
	public String getPickWorkType()
	{
		String pickWorkType = getFromBuffer(OFF_PICK_WORK_TYPE, LEN_JOB_TYPE);
		return pickWorkType.trim();
	}
	
	//#CM721630
	// Package methods ----------------------------------------------
	
	//#CM721631
	// Protected methods --------------------------------------------
	
	//#CM721632
	// Private methods ----------------------------------------------
	
}
//#CM721633
//end of class

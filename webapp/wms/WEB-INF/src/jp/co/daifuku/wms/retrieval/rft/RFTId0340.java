// $Id: RFTId0340.java,v 1.3 2007/02/07 04:19:46 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM721689
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
//#CM721690
/**
 * Process the " ID = 0340 Request for starting Item Picking" electronic statement electronic statement for socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Construction of Id0340 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>0340</td></tr>
 * <tr><td>Period for sending by Handy</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy Machine No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Worker Code</td>		<td>4 byte</td>	<td> </td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td><td> </td></tr>
 * <tr><td>Planned Picking Date</td>			<td>8 byte</td>	<td> </td></tr>
 * <tr><td>Area No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Zone No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Approach direction</td>			<td>1 byte</td>	<td>0: Forward order, 1: Reverse order</td></tr>
 * <tr><td>Selected Case/Piece division</td><td>1 byte</td><td> </td></tr>
 * <tr><td>Super Location</td>	<td>16 byte</td><td> </td></tr>
 * <tr><td>Super JAN Code</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Super Case/Piece division</td><td>1 byte</td><td> </td></tr>
 * <tr><td>Super Expiry Date</td>		<td>8 byte</td>	<td> </td></tr>
 * <tr><td>Super Bundle ITF</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Super Case ITF</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Super Aggregation Work No.</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td> </td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:46 $
 * @author  $Author: suresh $
 */
  
public class RFTId0340 extends RecvIdMessage
{
	//#CM721691
	// Class field --------------------------------------------------
	//#CM721692
	/**
	 * Offset the Personnel Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721693
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM721694
	/**
	 * Offset the Planned Picking Date.
	 */
	private static final int OFF_PICKING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM721695
	/**
	 * Offset the Area No.
	 */
	private static final int OFF_AREA_NO = OFF_PICKING_PLAN_DATE + LEN_PLAN_DATE;
	
	//#CM721696
	/**
	 * Offset the Zone No.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;

	//#CM721697
	/**
	 * Offset the Approach direction.
	 */
	private static final int OFF_APPROACH_DIRECTION = OFF_ZONE_NO + LEN_ZONE_NO;

	//#CM721698
	/**
	 * Offset the Work Type (Case/Piece division).
	 */
	private static final int OFF_WORK_FORM = OFF_APPROACH_DIRECTION + LEN_APPROACH_DIRECTION;

	//#CM721699
	/**
	 * Offset the Super Location.
	 */
	private static final int OFF_BASE_LOCATION = OFF_WORK_FORM + LEN_WORK_FORM;

	//#CM721700
	/**
	 * Offset the Super JAN Code.
	 */
	private static final int OFF_BASE_JAN_CODE = OFF_BASE_LOCATION + LEN_LOCATION;
	
	//#CM721701
	/**
	 * Offset the Super Work Type (Case/Piece division).
	 */
	private static final int OFF_BASE_WORK_FORM = OFF_BASE_JAN_CODE + LEN_JAN_CODE;

	//#CM721702
	/**
	 * Offset the Super Offset Expiry Date.
	 */
	private static final int OFF_BASE_USE_BY_DATE = OFF_BASE_WORK_FORM + LEN_WORK_FORM;

	//#CM721703
	/**
	 * Offset the Super Bundle ITF.
	 */
	private static final int OFF_BASE_BUNDLE_ITF = OFF_BASE_USE_BY_DATE + LEN_USE_BY_DATE;

	//#CM721704
	/**
	 * Offset the Super Case ITF.
	 */
	private static final int OFF_BASE_CASE_ITF = OFF_BASE_BUNDLE_ITF + LEN_BUNDLE_ITF;

	//#CM721705
	/**
	 * Offset the Super Aggregation Work No.
	 */
	private static final int OFF_BASE_COLLECT_JOB_NO = OFF_BASE_CASE_ITF + LEN_ITF;

	//#CM721706
	/**
	 * ID No.
	 */
	public static final String ID = "0340";

	//#CM721707
	/**
	 * A field that represents "All" as Work Type.
	 */
	public static final String WORK_FORM_All = "0" ;
	
	//#CM721708
	/**
	 * A field that represents "0: Forward" as Approach direction.
	 */
	public static final String DIRECTION_TRUE = "0";
	
	

	//#CM721709
	// Class variables ----------------------------------------------
	
	//#CM721710
	// Class method -------------------------------------------------
	//#CM721711
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:46 $";
	}
	
	//#CM721712
	// Constructors -------------------------------------------------
	//#CM721713
	/**
	 * Constructor
	 */
	public RFTId0340 ()
	{
		super();

		offEtx = OFF_BASE_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM721714
	/**
	 * Pass the electronic statement received from RFT to the Constructor.
	 * @param rftId0036 Request for starting Item Picking ID = 0036 electronic statement
	 */
	public RFTId0340 (byte[] rftId0036)
	{
		this();

		setReceiveMessage(rftId0036);
	}
	
	//#CM721715
	// Public methods -----------------------------------------------
	//#CM721716
	/**
	 * Obtain the Personnel Code from an electronic statement requesting for starting the Item Picking.
	 * @return   Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	
	//#CM721717
	/**
	 * Obtain the Consignor code from an electronic statement requesting for starting the Item Picking.
	 * @return   consignorCode
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	//#CM721718
	/**
	 * Obtain the Planned Picking Date from an electronic statement requesting for starting the Item Picking.
	 * @return   pickingPlanDate
	 */
	public String getPlanDate ()
	{
		String planDate = getFromBuffer(OFF_PICKING_PLAN_DATE, LEN_PLAN_DATE);
		return planDate.trim();
	}
	
	//#CM721719
	/**
	 * Obtain the Area No. from an electronic statement requesting for the Item Picking.
	 * @return areaNo Area No.
	 */
	public String getAreaNo()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO);
		return areaNo.trim();
	}
	
	//#CM721720
	/**
	 * Obtain the Zone No. from an electronic statement requesting for the Item Picking.
	 * @return zoneNo Zone No.
	 */
	public String getZoneNo()
	{
		String zoneNo = getFromBuffer(OFF_ZONE_NO, LEN_ZONE_NO);
		return zoneNo.trim();
	}
		
	//#CM721721
	/**
	 * Obtain the Approach direction from an electronic statement requesting for starting the Item Picking.
	 * @return   approachDirection
	 */
	public String getApproachDirection ()
	{
		String dir = getFromBuffer(OFF_APPROACH_DIRECTION , LEN_APPROACH_DIRECTION);
		return dir.trim();
	}

	//#CM721722
	/**
	 * Obtain the selected Case/Piece division from an electronic statement requesting for starting the Item Picking.
	 * @return   CasePieceFlag
	 */
	public String getWorkForm ()
	{
		String workForm = getFromBuffer(OFF_WORK_FORM , LEN_WORK_FORM);
		return workForm.trim();
	}
	
	//#CM721723
	/**
	 * Obtain the Super Location No. from an electronic statement requesting for the Item Picking.
	 * @return locationNo	Super Location No.
	 */
	public String getLocationNo()
	{
		String locationNo = getFromBuffer(OFF_BASE_LOCATION, LEN_LOCATION);
		return locationNo.trim();
	}

	//#CM721724
	/**
	 * Obtain the super JAN Code from an electronic statement requesting for starting the Item Picking.
	 * @return   JANCode
	 */
	public String getBaseJANCode ()
	{
		String JANCode = getFromBuffer(OFF_BASE_JAN_CODE , LEN_JAN_CODE);
		return JANCode.trim();
	}
	
	//#CM721725
	/**
	 * Obtain the super Case/ Piece division from an electronic statement requesting for starting the Item Picking.
	 * @return baseCasePieceFlag Super Case/Piece division
	 */
	public String getBaseCasePieceFlag()
	{
		String baseCasePieceFlag = getFromBuffer(OFF_BASE_WORK_FORM, LEN_WORK_FORM);
		return baseCasePieceFlag.trim();
	}
	
	//#CM721726
	/**
	 * Obtain the super expiry date from an electronic statement requesting for starting the Item Picking.
	 * @return baseUseByDate Super Expiry Date
	 */
	public String getBaseUseByDate()
	{
		String baseUseByDate = getFromBuffer(OFF_BASE_USE_BY_DATE, LEN_USE_BY_DATE);
		return baseUseByDate.trim();
	}
	
	//#CM721727
	/**
	 * Obtain the super bundle ITF from an electronic statement requesting for starting the Item Picking.
	 * @return baseBundleITF Super Bundle ITF
	 */
	public String getBaseBundleITF()
	{
		String baseBundleITF = getFromBuffer(OFF_BASE_BUNDLE_ITF, LEN_BUNDLE_ITF);
		return baseBundleITF.trim();
	}
	
	//#CM721728
	/**
	 * Obtain the super Case ITF from an electronic statement requesting for starting the Item Picking.
	 * @return baseCaseITF Super Case ITF
	 */
	public String getBaseCaseITF()
	{
		String baseCaseITF = getFromBuffer(OFF_BASE_CASE_ITF, LEN_ITF);
		return baseCaseITF.trim();
	}
	
	//#CM721729
	/**
	 * Obtain the Super Aggregation Work No. from an electronic statement requesting for starting the Item Picking.
	 * @return baseTotalWorkNo Super Aggregation Work No.
	 */
	public String getBaseTotalWorkNo()
	{
		String baseTotalWorkNo = getFromBuffer(OFF_BASE_COLLECT_JOB_NO, LEN_COLLECT_JOB_NO);
		return baseTotalWorkNo.trim();
	}
	//#CM721730
	// Package methods ----------------------------------------------
	
	//#CM721731
	// Package nethods ----------------------------------------------
	
	//#CM721732
	// Protected methods --------------------------------------------
	
	//#CM721733
	// Private methods ---------------------------------------------
	
}
//#CM721734
//end of class

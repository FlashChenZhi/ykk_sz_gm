// $Id: RFTId5340.java,v 1.3 2007/02/07 04:19:48 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM721916
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM721917
/**
 * Generate " ID = 5340 Response for starting Item Picking" electronic statement electronic statement for socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Construction of Id5340 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5340</td></tr>
 * <tr><td>Period for sending by Handy</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy Machine No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Worker Code</td>		<td>4 byte</td>	<td> </td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td><td> </td></tr>
 * <tr><td>Planned Picking Date</td>			<td>8 byte</td>	<td> </td></tr>
 * <tr><td>Input area No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Input Zone No.</td>		<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Approach direction</td>			<td>1 byte</td>	<td>0: Forward order, 1: Reverse order</td></tr>
 * <tr><td>Selected Case/Piece division</td>		<td>1 byte</td>	<td>0: All, 1: Case, 2: Piece, 3: None</td></tr>
 * <tr><td>JAN Code</td>			<td>16 byte</td><td>Left aligned</td></tr>
 * <tr><td>Case/Piece division</td>	<td>1 byte</td>	<td>1: Case, 2: Piece, 3: None</td></tr>
 * <tr><td>Consignor Name</td>				<td>40 byte</td><td> </td></tr>
 * <tr><td>the setting unit key</td>		<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Picking No.</td>				<td>8 byte</td>	<td>Not available</td></tr>
 * <tr><td>Area No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Zone No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Location</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Location Position</td>				<td>1 byte</td>	<td> '0': Both, '1': Right, '2': Left</td></tr>
 * <tr><td>Processing Replenish flag</td>		<td>1 byte</td>	<td>Not available</td></tr>
 * <tr><td>Aggregation Work No.</td>			<td>16 byte</td><td>Set the Aggregation Work No. left aligned.</td></tr>
 * <tr><td>Item Code</td>			<td>16 byte</td><td>Not available</td></tr>
 * <tr><td>Bundle ITF</td>			<td>16 byte</td><td> </td></tr>
 * <tr><td>Case ITF</td>			<td>16 byte</td><td> </td></tr>
 * <tr><td>Item Name</td>				<td>40 byte</td><td> </td></tr>
 * <tr><td>Item Category</td>			<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>Packed qty per bundle</td>			<td>6 byte</td>	<td> </td></tr>
 * <tr><td>Packed Qty per Case</td>			<td>6 byte</td>	<td> </td></tr>
 * <tr><td>Lot No.</td>			<td>10 byte</td><td>Not available</td></tr>
 * <tr><td>Expiry Date</td>			<td>8 byte</td>	<td> </td></tr>
 * <tr><td>Manufactured Date</td>				<td>8 byte</td>	<td>Not available</td></tr>
 * <tr><td>Instructed picking qty</td>			<td>9 byte</td>	<td>Value translated into Piece</td></tr>
 * <tr><td>Total Picking Count</td>			<td>9 byte</td>	<td> </td></tr>
 * <tr><td>Count of remaining picking</td>			<td>9 byte</td>	<td> </td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal, 5: "Processing Daily Update", 6: Maintenance in progress via other terminal<br>
 * 														8:No corresponding data, 9: Error<td></tr>
 * <tr><td>Details of error</td>			<td>2 byte</td><td> </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/13</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:48 $
 * @author  $Author: suresh $
 */

public class RFTId5340 extends SendIdMessage
{
	//#CM721918
	// Class field --------------------------------------------------
	//#CM721919
	/**
	 * Offset the Worker Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721920
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM721921
	/**
	 * Offset the Planned Picking Date.
	 */
	private static final int OFF_PICKING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM721922
	/**
	 * Offset the Input area No.
	 */
	private static final int OFF_INPUT_AREA_NO = OFF_PICKING_PLAN_DATE + LEN_PLAN_DATE;

	//#CM721923
	/**
	 * Offset the Input Zone No.
	 */
	private static final int OFF_INPUT_ZONE_NO = OFF_INPUT_AREA_NO + LEN_AREA_NO;

	//#CM721924
	/**
	 * Offset the Approach direction.
	 */
	private static final int OFF_APPROACH_DIRECTION = OFF_INPUT_ZONE_NO + LEN_ZONE_NO;

	//#CM721925
	/**
	 * Offset the Selected Case/Piece division.
	 */
	private static final int OFF_SELECT_CASE_PIECE = OFF_APPROACH_DIRECTION + LEN_APPROACH_DIRECTION;
	
	//#CM721926
	/**
	 * Offset the JAN Code.
	 */
	private static final int OFF_JAN_CODE = OFF_SELECT_CASE_PIECE + LEN_CASE_PIECE_FLAG;
	
	//#CM721927
	/**
	 * Offset the Work Type (Case/Piece division).
	 */
	private static final int OFF_WORK_FORM = OFF_JAN_CODE + LEN_JAN_CODE;
	
	//#CM721928
	/**
	 * Offset the Consignor Name.
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_WORK_FORM + LEN_WORK_FORM;
	
	//#CM721929
	/**
	 * Offset the the setting unit key.
	 */
	private static final int OFF_SET_UNIT_KEY = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM721930
	/**
	 * Offset the Picking No.
	 */
	private static final int OFF_PICKING_NO = OFF_SET_UNIT_KEY + LEN_SET_UNIT_KEY;
	
	//#CM721931
	/**
	 * Offset the Area No.
	 */
	private static final int OFF_AREA_NO = OFF_PICKING_NO + LEN_PICKING_NO;
	
	//#CM721932
	/**
	 * Offset the Zone No.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;

	//#CM721933
	/**
	 * Offset the Location.
	 */
	private static final int OFF_LOCATION = OFF_ZONE_NO + LEN_ZONE_NO;
	
	//#CM721934
	/**
	 * Offset the Location Position.
	 */
	private static final int OFF_LOCATION_SIDE = OFF_LOCATION + LEN_LOCATION;

	//#CM721935
	/**
	 * Offset the Processing Replenish flag.
	 */
	private static final int OFF_REPLENISHING_FLAG = OFF_LOCATION_SIDE + LEN_LOCATION_SIDE;
	
	//#CM721936
	/**
	 * Offset the Aggregation Work No.
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_REPLENISHING_FLAG + LEN_REPLENISHING_FLAG;
	
	//#CM721937
	/**
	 * Offset the Offset Item Code.
	 */
	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO;
	
	//#CM721938
	/**
	 * Offset the Bundle ITF.
	 */
	private static final int OFF_BUNDLE_ITF = OFF_ITEM_CODE + LEN_ITEM_CODE;
	
	//#CM721939
	/**
	 * Offset the Case ITF.
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;
	
	//#CM721940
	/**
	 * Offset the Item Name.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF;
	
	//#CM721941
	/**
	 * Offset the Item Category.
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME;
	
	//#CM721942
	/**
	 * Offset the Packed qty per bundle.
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE;
	
	//#CM721943
	/**
	 * Offset the Packed Qty per Case.
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;
	
	//#CM721944
	/**
	 * Offset the Lot No.
	 */
	private static final int OFF_LOT_NO = OFF_ENTERING_QTY + LEN_ENTERING_QTY;
	
	//#CM721945
	/**
	 * Offset the Expiry Date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO;
	
	//#CM721946
	/**
	 * Offset the Manufactured Date.
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	
	//#CM721947
	/**
	 * Offset the Instructed picking qty.
	 */
	private static final int OFF_PICKING_INSTRUCTION_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;
	
	//#CM721948
	/**
	 * Offset the Total Picking Count.
	 */
	private static final int OFF_TOTAL_PICKING_QTY = OFF_PICKING_INSTRUCTION_QTY + LEN_PLAN_QTY;
	
	//#CM721949
	/**
	 * Offset the Count of remaining picking.
	 */
	private static final int OFF_REMAINING_PICKING_QTY = OFF_TOTAL_PICKING_QTY + LEN_TOTAL_ITEM_COUNT;
	
	//#CM721950
	/**
	 * ID No.
	 */
	public static final String ID = "5340";

	//#CM721951
	// Class method ------------------------------------------------
	//#CM721952
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return  "$Revision: 1.3 $Date: 2007/02/07 04:19:48 $";
	}
	
	//#CM721953
	// Constructors -------------------------------------------------
	//#CM721954
	/**
	 * Constructor
	 */
	public RFTId5340 ()
	{
		super();

		offAnsCode = OFF_REMAINING_PICKING_QTY + LEN_REMAINING_ITEM_COUNT;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM721955
	// Public methods -----------------------------------------------
	//#CM721956
	/**
	 * Set the Personnel code.
	 * @param workerCode Personnel Code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE);
	}
	
	//#CM721957
	/**
	 * Set the Consignor code.
	 * @param consignorCode Consignor Code
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode , OFF_CONSIGNOR_CODE);
	}
	
	//#CM721958
	/**
	 * Set the Planned Picking Date.
	 * @param pickingPlanDate Planned Picking Date
	 */
	public void setPickingPlanDate (String pickingPlanDate)
	{
		setToBuffer(pickingPlanDate , OFF_PICKING_PLAN_DATE);
	}
	
	//#CM721959
	/**
	 * Set the Input area No.
	 * @param inputAreaNo Input area No.
	 */
	public void setInputAreaNo(String inputAreaNo)
	{
		setToBuffer(inputAreaNo, OFF_INPUT_AREA_NO);
	}
	
	//#CM721960
	/**
	 * Set the Input Zone No..
	 * @param inputZoneNo Input Zone No.
	 */
	public void setInputZoneNo(String inputZoneNo)
	{
		setToBuffer(inputZoneNo, OFF_INPUT_ZONE_NO);
	}
	
	//#CM721961
	/**
	 * Set the Approach direction.
	 * @param approachDirection  Approach direction
	 */
	public void setApproachDirection(String approachDirection)
	{
		setToBuffer(approachDirection, OFF_APPROACH_DIRECTION);
	}
	
	//#CM721962
	/**
	 * Set the Selected Case/Piece division..
	 * @param selectCasePiece	Selected Case/Piece division
	 */
	public void setSelectCasePiece(String selectCasePiece)
	{
	    setToBuffer(selectCasePiece, OFF_SELECT_CASE_PIECE);
	}
	
	//#CM721963
	/**
	 * Set the JAN Code
	 * @param JANCode JAN Code
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode , OFF_JAN_CODE);
	}
	
	//#CM721964
	/**
	 * Set the Case/Piece division.
	 * @param casePieceFlag Case/Piece division
	 */
	public void setCasePieceFlag (String casePieceFlag)
	{
		setToBuffer(casePieceFlag , OFF_WORK_FORM);
	}
	
	//#CM721965
	/**
	 * Set the Consignor Name.
	 * @param consignorName Consignor Name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName , OFF_CONSIGNOR_NAME);
	}
	
	//#CM721966
	/**
	 * Set the the setting unit key.
	 * @param setUnitKey the setting unit key
	 */
	public void setSetUnitKey (String setUnitKey)
	{
		setToBuffer(setUnitKey , OFF_SET_UNIT_KEY);
	}
	
	//#CM721967
	/**
	 * Set the Picking No..
	 * @param pickingNo Picking No.
	 */
	public void setPickingNo (String pickingNo)
	{
		setToBuffer(pickingNo , OFF_PICKING_NO);
	}
	
	//#CM721968
	/**
	 * Set the Area No.
	 * @param areaNo Area No.
	 */
	public void setAreaNo (String areaNo)
	{
		setToBuffer(areaNo , OFF_AREA_NO);
	}
	
	//#CM721969
	/**
	 * Set the Zone No.
	 * @param zoneNo Area No.
	 */
	public void setZoneNo (String zoneNo)
	{
		setToBuffer(zoneNo , OFF_ZONE_NO);
	}

	//#CM721970
	/**
	 * Set the Location.
	 * @param location Location
	 */
	public void setLocation (String location)
	{
		setToBuffer(location , OFF_LOCATION);
	}
	
	//#CM721971
	/**
	 * Set the Location Position.
	 * @param locationSide Location Position
	 */
	public void setLocationSide(String locationSide)
	{
		setToBuffer(locationSide, OFF_LOCATION_SIDE);
	}
	//#CM721972
	/**
	 * Set the Replenishing flag.
	 * @param replenishingFlag Processing Replenish flag
	 */
	public void setReplenishingFlag (String replenishingFlag)
	{
		setToBuffer(replenishingFlag , OFF_REPLENISHING_FLAG);
	}
	
	//#CM721973
	/**
	 * Set the Aggregation Work No.
	 * @param collectJobNo Aggregation Work No.
	 */
	public void setCollectJobNo (String collectJobNo)
	{
		setToBuffer(collectJobNo, OFF_COLLECT_JOB_NO);
	}
	
	//#CM721974
	/**
	 * Set the item code..
	 * @param itemCode Item Code
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode , OFF_ITEM_CODE);
	}
	
	//#CM721975
	/**
	 * Set the bundle ITF.
	 * @param bundleITF Bundle ITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF , OFF_BUNDLE_ITF);
	}
	
	//#CM721976
	/**
	 * Set the Case ITF.
	 * @param ITF Case ITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF , OFF_ITF);
	}
	
	//#CM721977
	/**
	 * Set the Item Name.
	 * @param itemName Item Name
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName , OFF_ITEM_NAME);
	}
	
	//#CM721978
	/**
	 * Set the Item Category.
	 * @param itemCategoryCode Item Category
	 */
	public void setItemCategoryCode (String itemCategoryCode)
	{
		setToBuffer(itemCategoryCode , OFF_ITEM_CATEGORY_CODE);
	}
	
	//#CM721979
	/**
	 * Set the Packed qty per bundle.
	 * @param bundleEnteringQty Packed qty per bundle
	 */
	public void setBundleEnteringQty (String bundleEnteringQty)
	{
		setToBufferRight(bundleEnteringQty , OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}
	
	//#CM721980
	/**
	 * Set the Packed qty per case.
	 * @param enteringQty Packed Qty per Case
	 */
	public void setEnteringQty (String enteringQty)
	{
		setToBufferRight(enteringQty , OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}
	
	//#CM721981
	/**
	 * Set the Lot No.
	 * @param lotNo Lot No.
	 */
	public void setLotNo (String lotNo)
	{
		setToBuffer(lotNo , OFF_LOT_NO);
	}
	
	//#CM721982
	/**
	 * Set the expiry date..
	 * @param useByDate Expiry Date
	 */
	public void setUseByDate (String useByDate)
	{
		setToBuffer(useByDate , OFF_USE_BY_DATE);
	}
	
	//#CM721983
	/**
	 * Set the manufactured date..
	 * @param manufactureDate Manufactured Date
	 */
	public void setManufactureDate (String manufactureDate)
	{
		setToBuffer(manufactureDate , OFF_MANUFACTURE_DATE);
	}
	
	//#CM721984
	/**
	 * Set the Instructed picking qty.
	 * @param pickingInstructionQty Instructed picking qty
	 */
	public void setPickingInstructionQty (int pickingInstructionQty)
	{
		setToBufferRight(pickingInstructionQty , OFF_PICKING_INSTRUCTION_QTY, LEN_PLAN_QTY);
	}
	
	//#CM721985
	/**
	 * Set the Total Picking Count.
	 * @param totalPickingQty Total Picking Count
	 */
	public void setTotalPickingQty(int totalPickingQty)
	{
		setToBufferRight(totalPickingQty, OFF_TOTAL_PICKING_QTY, LEN_TOTAL_ITEM_COUNT);
	}
	
	//#CM721986
	/**
	 * Set the Count of remaining picking.
	 * @param remainingPickingQty Count of remaining picking
	 */
	public void setRemainingPickingQty(int remainingPickingQty)
	{
		setToBufferRight(remainingPickingQty, OFF_REMAINING_PICKING_QTY, LEN_REMAINING_ITEM_COUNT);
	}
	
	//#CM721987
	// Package methods ----------------------------------------------
	
	//#CM721988
	// Protected methods --------------------------------------------
	
	//#CM721989
	// Private methods ----------------------------------------------

}
//#CM721990
//end of class

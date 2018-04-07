// $Id: RFTId0341.java,v 1.3 2007/02/07 04:19:47 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM721735
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 //#CM721736
 /**
  * Process the " ID = 0341 Sending Item Picking result data" electronic statement electronic statement for socket communication.
  *
  * <p>
  * <table border="1">
  * <CAPTION>Construction of Id0341 electronic statement.</CAPTION>
  * <TR><TH>Field item name</TH>				<TH>Length</TH><TH>Content</TH></TR>
  * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
  * <tr><td>SEQ No</td>				<td>4 byte</td>	<td>Not available</td></tr>
  * <tr><td>ID</td>					<td>4 byte</td>	<td>0341</td></tr>
  * <tr><td>Period for sending by Handy</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
  * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
  * <tr><td>Handy Machine No.</td>		<td>3 byte</td>	<td> </td></tr>
  * <tr><td>Worker Code</td>		<td>4 byte</td>	<td> </td></tr>
  * <tr><td>Consignor Code</td>			<td>16 byte</td><td> </td></tr>
  * <tr><td>Planned Picking Date</td>			<td>8 byte</td>	<td> </td></tr>
  * <tr><td>Approach direction</td>			<td>1 byte</td>	<td>0: Forward order, 1: Reverse order</td></tr>
  * <tr><td>JAN Code</td>			<td>16 byte</td><td>Left aligned</td></tr>
  * <tr><td>Case/Piece division</td>	<td>1 byte</td>	<td>1: Case, 2: Piece, 3: None</td></tr>
  * <tr><td>Consignor Name</td>				<td>40 byte</td><td> </td></tr>
  * <tr><td>the setting unit key</td>		<td>16 byte</td><td>Not available</td></tr>
  * <tr><td>Picking No.</td>			<td>8 byte</td>	<td>Not available</td></tr>
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
  * <tr><td>Unit</td>				<td>6 byte</td>	<td>Not available</td></tr>
  * <tr><td>Lot No.</td>			<td>10 byte</td><td>Not available</td></tr>
  * <tr><td>Expiry Date</td>			<td>8 byte</td>	<td> </td></tr>
  * <tr><td>Manufactured Date</td>				<td>8 byte</td>	<td>Not available</td></tr>
  * <tr><td>Instructed picking qty</td>			<td>9 byte</td>	<td>Value translated into Piece</td></tr>
  * <tr><td>Picking Result qty</td>			<td>9 byte</td>	<td>Value translated into Piece</td></tr>
  * <tr><td>Work Time</td>			<td>5 byte</td> <td>seconds</td></tr>
  * <tr><td>Count of mistakes</td>			<td>1 byte</td> <td> </td></tr>
  * <tr><td>Completion class</td>			<td>1 byte</td>	<td>0: Commit, 9: Cancel</td></tr>
  * <tr><td>ETX</td>				<td>1 byte</td> <td> </td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>200512/13</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:47 $
  * @author  $Author: suresh $
  */
  
public class RFTId0341 extends RecvIdMessage
{
	//#CM721737
	// Class field --------------------------------------------------
	//#CM721738
	/**
	 * Offset the Worker Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721739
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM721740
	/**
	 * Offset the Planned Picking Date.
	 */
	private static final int OFF_PICKING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM721741
	/**
	 * Offset the Approach direction.
	 */
	private static final int OFF_APPROACH_DIRECTION = OFF_PICKING_PLAN_DATE + LEN_PLAN_DATE;
	
	//#CM721742
	/**
	 * Offset the JAN Code.
	 */
	private static final int OFF_JAN_CODE = OFF_APPROACH_DIRECTION + LEN_APPROACH_DIRECTION;
	
	//#CM721743
	/**
	 * Offset the Case/Piece division.
	 */
	private static final int OFF_WORK_FORM = OFF_JAN_CODE + LEN_JAN_CODE;
	
	//#CM721744
	/**
	 * Offset the Consignor Name.
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_WORK_FORM + LEN_WORK_FORM;
	
	//#CM721745
	/**
	 * Offset the the setting unit key.
	 */
	private static final int OFF_SET_UNIT_KEY = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM721746
	/**
	 * Offset the Picking No.
	 */
	private static final int OFF_PICKING_NO = OFF_SET_UNIT_KEY + LEN_SET_UNIT_KEY;
	
	//#CM721747
	/**
	 * Offset the Area No.
	 */
	private static final int OFF_AREA_NO = OFF_PICKING_NO + LEN_PICKING_NO;
	
	//#CM721748
	/**
	 * Offset the Zone No.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;

	//#CM721749
	/**
	 * Offset the Location.
	 */
	private static final int OFF_LOCATION = OFF_ZONE_NO + LEN_ZONE_NO;
	
	//#CM721750
	/**
	 * Offset the Location Position.
	 */
	private static final int OFF_LOCATION_SIDE = OFF_LOCATION + LEN_LOCATION;

	//#CM721751
	/**
	 * Offset the Processing Replenish flag.
	 */
	private static final int OFF_REPLENISHING_FLAG = OFF_LOCATION_SIDE + LEN_LOCATION_SIDE;
	
	//#CM721752
	/**
	 * Offset the Aggregation Work No.
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_REPLENISHING_FLAG + LEN_REPLENISHING_FLAG;
	
	//#CM721753
	/**
	 * Offset the Offset Item Code.
	 */
	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO;
	
	//#CM721754
	/**
	 * Offset the Bundle ITF.
	 */
	private static final int OFF_BUNDLE_ITF = OFF_ITEM_CODE + LEN_ITEM_CODE;
	
	//#CM721755
	/**
	 * Offset the Case ITF.
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;
	
	//#CM721756
	/**
	 * Offset the Item Name.
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF;
	
	//#CM721757
	/**
	 * Offset the Item Category.
	 */
	private static final int OFF_ITEM_CATEGORY_CODE = OFF_ITEM_NAME + LEN_ITEM_NAME;
	
	//#CM721758
	/**
	 * Offset the Packed qty per bundle.
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_CATEGORY_CODE + LEN_ITEM_CATEGORY_CODE;
	
	//#CM721759
	/**
	 * Offset the Packed Qty per Case.
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY;
	
	//#CM721760
	/**
	 * Offset the unit.
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY;
	
	//#CM721761
	/**
	 * Offset the Lot No.
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT;
	
	//#CM721762
	/**
	 * Offset the Expiry Date.
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO;
	
	//#CM721763
	/**
	 * Offset the Manufactured Date.
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE;
	
	//#CM721764
	/**
	 * Offset the Instructed picking qty.
	 */
	private static final int OFF_PICKING_INSTRUCTION_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE;
	
	//#CM721765
	/**
	 * Offset the Picking Result qty.
	 */
	private static final int OFF_PICKING_RESULT_QTY = OFF_PICKING_INSTRUCTION_QTY + LEN_PLAN_QTY;
	
	//#CM721766
	/**
	 * Offset the Work Time (seconds).
	 */
	private static final int OFF_WORK_SECONDS = OFF_PICKING_RESULT_QTY + LEN_RESULT_QTY;
	
	//#CM721767
	/**
	 * Offset the Count of mistakes.
	 */
	private static final int OFF_MISS_SCAN_CNT = OFF_WORK_SECONDS + LEN_WORK_TIME;

	//#CM721768
	/**
	 * Offset the Completion class.
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_MISS_SCAN_CNT + LEN_MISS_COUNT;

	//#CM721769
	/**
	 * A field that represents "Normal" as Completion class.
	 */
	public static final String COMPLETION_FLAG_COMPLETE = "0";

	//#CM721770
	/**
	 * A field that represents "Cancel" as Completion class.
	 */
	public static final String COMPLETION_FLAG_CANCEL = "9";
	
	//#CM721771
	/**
	 * ID No.
	 */
	public static final String ID = "0341";

	//#CM721772
	// Class variables ----------------------------------------------
	
	//#CM721773
	// Class method -------------------------------------------------
	//#CM721774
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:47 $";
	}
	
	//#CM721775
	// Constructors -------------------------------------------------
	//#CM721776
	/**
	 * Constructor
	 */
	public RFTId0341 ()
	{
		super();

		offEtx = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM721777
	/**
	 * Pass the electronic statement received from RFT to the Constructor.
	 * @param rftId0341 Send Item Picking Result Data ID = 0037 electronic statement
	 */
	public RFTId0341 (byte[] rftId0341)
	{
		this();

		setReceiveMessage(rftId0341);
	}
	
	//#CM721778
	// Public methods -----------------------------------------------
	//#CM721779
	/**
	 * Obtain the Personnel Code from an electronic statement sending the Item Picking result data.
	 * @return   Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	//#CM721780
	/**
	 * Obtain the Consignor code from an electronic statement sending the Item Picking result data.
	 * @return   consignorCode
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE , LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	//#CM721781
	/**
	 * Obtain the Planned Picking Date from an electronic statement sending the Item Picking result data.
	 * @return   pickingPlanDate
	 */
	public String getPickingPlanDate ()
	{
		String pickingPlanDate = getFromBuffer(OFF_PICKING_PLAN_DATE , LEN_PLAN_DATE);
		return pickingPlanDate;
	}
	
	//#CM721782
	/**
	 * Obtain the JAN Code from an electronic statement sending the Item Picking result data.
	 * @return   JANCode
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE , LEN_JAN_CODE);
		return JANCode.trim();
	}
	
	//#CM721783
	/**
	 * Obtain the Case/Piece division from an electronic statement sending the Item Picking result data.
	 * @return   CasePieceFlag
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_WORK_FORM, LEN_CASE_PIECE_FLAG);
		return casePieceFlag.trim();
	}
	
	//#CM721784
	/**
	 * Obtain the Consignor Name from an electronic statement sending the Item Picking result data.
	 * @return   consignorName
	 */
	public String getConsignorName ()
	{
		String consignorName = getFromBuffer(OFF_CONSIGNOR_NAME , LEN_CONSIGNOR_NAME);
		return consignorName.trim();
	}
	
	//#CM721785
	/**
	 * Obtain the the setting unit key from an electronic statement sending the Item Picking result data.
	 * @return   setUnitKey
	 */
	public String getSetUnitKey ()
	{
		String setUnitKey = getFromBuffer(OFF_SET_UNIT_KEY , LEN_SET_UNIT_KEY);
		return setUnitKey.trim();
	}
	
	//#CM721786
	/**
	 * Obtain the Picking No. from an electronic statement sending the Item Picking result data.
	 * @return   pickingNo
	 */
	public String getPickingNo ()
	{
		String pickingNo = getFromBuffer(OFF_PICKING_NO , LEN_PICKING_NO);
		return pickingNo.trim();
	}
	
	//#CM721787
	/**
	 * Obtain the Area No. from an electronic statement sending the Item Picking result data.
	 * @return   areaNo
	 */
	public String getAreaNo ()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO , LEN_AREA_NO);
		return areaNo.trim();
	}
	
	//#CM721788
	/**
	 * Obtain the location from an electronic statement sending the Item Picking result data.
	 * @return   location
	 */
	public String getLocation ()
	{
		String location = getFromBuffer(OFF_LOCATION , LEN_LOCATION);
		return location.trim();
	}
	
	//#CM721789
	/**
	 * Obtain the Location Position from an electronic statement sending the Item Picking result data.
	 * @return
	 */
	public String getLocationSide()
	{
		String locationSide = getFromBuffer(OFF_LOCATION_SIDE, LEN_LOCATION_SIDE);
		return locationSide.trim();
	}
	
	//#CM721790
	/**
	 * Obtain the Processing Replenish flag from an electronic statement sending the Item Picking result data.
	 * @return   replenishingFlag
	 */
	public String getReplenishingFlag ()
	{
		String replenishingFlag = getFromBuffer(OFF_REPLENISHING_FLAG , LEN_REPLENISHING_FLAG);
		return replenishingFlag.trim();
	}
	
	//#CM721791
	/**
	 * Obtain the Item identification code from an electronic statement sending the Item Picking result data.
	 * @return   itemId
	 */
	public String getItemId ()
	{
		String itemId = getFromBuffer(OFF_COLLECT_JOB_NO , LEN_ITEM_ID);
		return itemId.trim();
	}
	
	//#CM721792
	/**
	 * Obtain the item code from an electronic statement sending the Item Picking result data.
	 * @return   itemCode
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE , LEN_ITEM_CODE);
		return itemCode.trim();
	}
	
	//#CM721793
	/**
	 * Obtain the bundle ITF from an electronic statement sending the Item Picking result data.
	 * @return   bundleITF
	 */
	public String getBundleITF ()
	{
		String bundleITF = getFromBuffer(OFF_BUNDLE_ITF , LEN_BUNDLE_ITF);
		return bundleITF.trim();
	}
	
	//#CM721794
	/**
	 * Obtain the Case ITF from an electronic statement sending the Item Picking result data.
	 * @return   ITF
	 */
	public String getITF ()
	{
		String ITF = getFromBuffer(OFF_ITF , LEN_ITF);
		return ITF.trim();
	}
	
	//#CM721795
	/**
	 * Obtain the Item Name from an electronic statement sending the Item Picking result data.
	 * @return   itemName
	 */
	public String getItemName ()
	{
		String itemName = getFromBuffer(OFF_ITEM_NAME , LEN_ITEM_NAME);
		return itemName.trim();
	}
	
	//#CM721796
	/**
	 * Obtain the Item Category from an electronic statement sending the Item Picking result data.
	 * @return   itemCategoryCode
	 */
	public String getItemCategoryCode ()
	{
		String itemCategoryCode = getFromBuffer(OFF_ITEM_CATEGORY_CODE , LEN_ITEM_CATEGORY_CODE);
		return itemCategoryCode.trim();
	}
	
	//#CM721797
	/**
	 * Obtain the Packed qty per bundle from an electronic statement sending the Item Picking result data.
	 * @return   bundleEnteringQty
	 */
	public String getBundleEnteringQty ()
	{
		String bundleEnteringQty = getFromBuffer(OFF_BUNDLE_ENTERING_QTY , LEN_BUNDLE_ENTERING_QTY);
		return bundleEnteringQty.trim();
	}
	
	//#CM721798
	/**
	 * Obtain the Packed qty per case from an electronic statement sending the Item Picking result data.
	 * @return   EnteringQty
	 */
	public String getEnteringQty ()
	{
		String enteringQty = getFromBuffer(OFF_ENTERING_QTY , LEN_ENTERING_QTY);
		return enteringQty.trim();
	}
	
	//#CM721799
	/**
	 * Obtain the Unit from an electronic statement sending the Item Picking result data.
	 * @return   unit
	 */
	public String getUnit ()
	{
		String unit = getFromBuffer(OFF_UNIT , LEN_UNIT);
		return unit.trim();
	}
	
	//#CM721800
	/**
	 * Obtain the Lot No. from an electronic statement sending the Item Picking result data.
	 * @return   lotNo
	 */
	public String getLotNo ()
	{
		String lotNo = getFromBuffer(OFF_LOT_NO , LEN_LOT_NO);
		return lotNo.trim();
	}
	
	//#CM721801
	/**
	 * Obtain the Expiry Date from an electronic statement sending the Item Picking result data.
	 * @return   useByDate
	 */
	public String getUseByDate ()
	{
		String useByDate = getFromBuffer(OFF_USE_BY_DATE , LEN_USE_BY_DATE);
		return useByDate.trim();
	}
	
	//#CM721802
	/**
	 * Obtain the Manufactured Date from an electronic statement sending the Item Picking result data.
	 * @return   manufactureDate
	 */
	public String getManufactureDate ()
	{
		String manufactureDate = getFromBuffer(OFF_MANUFACTURE_DATE , LEN_MANUFACTURE_DATE);
		return manufactureDate.trim();
	}
	
	//#CM721803
	/**
	 * Obtain the Instructed picking qty from an electronic statement sending the Item Picking result data.
	 * @return   pickingInstructionQty
	 */
	public int getPickingInstructionQty ()
	{
		int pickingInstructionQty = getIntFromBuffer(OFF_PICKING_INSTRUCTION_QTY, LEN_PLAN_QTY);
		return pickingInstructionQty;
	}
	
	//#CM721804
	/**
	 * Obtain the Picking Result qty from an electronic statement sending the Item Picking result data.
	 * @return   pickingResultQty
	 */
	public int getPickingResultQty ()
	{
		int pickingResultQty = getIntFromBuffer(OFF_PICKING_RESULT_QTY , LEN_RESULT_QTY);
		return pickingResultQty;
	}
	
	//#CM721805
	/**
	 * Obtain the Work Time from an electronic statement sending the Item Picking result data.
	 * @return workSeconds Work Time
	 */
	public int getWorkSeconds()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}
	
	//#CM721806
	/**
	 * Obtain the Count of mis-scanning from an electronic statement sending the Item Picking result data.
	 * @return missScanCnt Count of mis-scanning
	 */
	public int getMissScanCnt()
	{
		int missScanCnt = getIntFromBuffer(OFF_MISS_SCAN_CNT, LEN_MISS_COUNT);
		return missScanCnt;
	}
	
	//#CM721807
	/**
	 * Obtain the Completion class from an electronic statement sending the Item Picking result data.
	 * @return   completionFlag
	 */
	public String getCompletionFlag ()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG , LEN_COMPLETION_FLAG);
		return completionFlag.trim();
	}
	
	//#CM721808
	// Package methods ----------------------------------------------
	
	//#CM721809
	// Package nethods ----------------------------------------------
	
	//#CM721810
	// Protected methods --------------------------------------------
	
	//#CM721811
	// Private methods ---------------------------------------------
	
}
//#CM721812
//end of class

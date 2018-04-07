//#CM721634
//$Id: RFTId0331.java,v 1.3 2007/02/07 04:19:46 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM721635
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM721636
/**
 * Process the " ID = 0331 Sending Order Picking result data" electronic statement electronic statement for socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Construction of Id0331 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>	<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td>	<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>0331</td></tr>
 * <tr><td>Period for sending by Terminal</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Terminal machine No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Worker Code</td>		<td>4 byte</td>	<td> </td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td><td> </td></tr>
 * <tr><td>Planned Picking Date</td>			<td>8 byte</td>	<td> </td></tr>
 * <tr><td>Selected Case/Piece division</td><td>1 byte</td>	<td>0: All, 1: Case, 2: Piece, 3: None</tr>
 * <tr><td>Order No. (1)</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Order No. (2)</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Order No. (3)</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Order No. (4)</td>		<td>16 byte</td><td> </td></tr>
 * <tr><td>Area No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Zone No.</td>			<td>3 byte</td>	<td> </td></tr>
 * <tr><td>Approach direction</td>			<td>1 byte</td>	<td>0: Forward order, 1: Reverse order</td></tr>
 * <tr><td>Picking Work division</td>		<td>1 byte</td>	<td>1: Specify Order, 2: Specify Area</td></tr>
 * <tr><td>Work Time</td>			<td>5 byte</td>	<td>seconds</td></tr>
 * <tr><td>Count of mistakes</td>			<td>5 byte</td>	<td> </td></tr>
 * <tr><td>"Complete" flag</td>			<td>1 byte</td>	<td>0: Commit, 3: Change Box, 9: Cancel</td></tr>
 * <tr><td>Box-change Order</td>		<td>1 byte</td>	<td>Index of Order to which Box-change was executed<br>
 * 														(1 to 4)</td></tr>
 * <tr><td>Result file name</td>		<td>30 byte</td><td>Disable to set if the Completion class is "9".</td></tr>
 * <tr><td>Number of records in a file.</td>	<td>6 byte</td>	<td> </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td>0x03</td></tr>
 * </TABLE>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/04</TD><TD>etakeda</TD><TD>New creation</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:46 $
 * @author  $Author: suresh $
 */
public class RFTId0331 extends RecvIdMessage
{

//#CM721637
//  Class field --------------------------------------------------
	//#CM721638
	/**
	 * Offset the Worker Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721639
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM721640
	/**
	 * Offset the Planned Picking Date.
	 */
	private static final int OFF_PICKING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM721641
	/**
	 * Offset the Selected Case/Piece division.
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_PICKING_PLAN_DATE + LEN_PLAN_DATE;
	
	//#CM721642
	/**
	 * Offset the Order No. (1).
	 */
	private static final int OFF_ORDER_NO_1 = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;
	
	//#CM721643
	/**
	 * Offset the Order No. (2).
	 */
	private static final int OFF_ORDER_NO_2 = OFF_ORDER_NO_1 + LEN_ORDER_NO;
	
	//#CM721644
	/**
	 * Offset the Order No. (3).
	 */
	private static final int OFF_ORDER_NO_3 = OFF_ORDER_NO_2 + LEN_ORDER_NO;
	
	//#CM721645
	/**
	 * Offset the Order No. (4).
	 */
	private static final int OFF_ORDER_NO_4 = OFF_ORDER_NO_3 + LEN_ORDER_NO;
	
	//#CM721646
	/**
	 * Offset the Area No.
	 */
	private static final int OFF_AREA_NO = OFF_ORDER_NO_4 + LEN_ORDER_NO;
	
	//#CM721647
	/**
	 * Offset the Approach direction.
	 */
	
	//#CM721648
	/**
	 * Offset the Zone No.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;
	
	//#CM721649
	/**
	 * Offset the Picking Work division.
	 */
	private static final int OFF_RETRIEVAL_JOB_TYPE = OFF_ZONE_NO + LEN_ZONE_NO;
	
	//#CM721650
	/**
	 * Offset the Work Time.
	 */
	private static final int OFF_WORK_TIME = OFF_RETRIEVAL_JOB_TYPE + LEN_JOB_TYPE;
	
	//#CM721651
	/**
	 * Offset the Count of mistakes.
	 */
	private static final int OFF_MISS_COUNT = OFF_WORK_TIME + LEN_WORK_TIME;
	
	//#CM721652
	/**
	 * Offset the "Complete" flag.
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_MISS_COUNT + LEN_MISS_COUNT;
	
	//#CM721653
	/**
	 * Offset the Box-change Order.
	 */
	private static final int OFF_BOX_INDEX = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	
	//#CM721654
	/**
	 * Offset the Result file name.
	 */
	private static final int OFF_RESULT_FILE_NAME = OFF_BOX_INDEX + LEN_BOX_INDEX;
	
	//#CM721655
	/**
	 * Offset the number of records in a file.
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_RESULT_FILE_NAME + LEN_WORK_FILE_NAME;

	
	//#CM721656
	/**
	 * Completion class (0: "Submit") 
	 */
	public static final String COMPLETION_FLAG_DECISION = "0";
	
	//#CM721657
	/**
	 * Completion class (3: Change Box) 
	 */
	public static final String COMPLETION_FLAG_BOX_CHANGE = "3";
	
	//#CM721658
	/**
	 * A field that represents "Cancel" as Completion class.
	 */
	public static final String COMPLETION_FLAG_CANCEL = "9";
	
	//#CM721659
	/**
	 * A field that represents a class name.
	 */
    public static final String CLASS_NAME = "ID0331";

	//#CM721660
	// Class variables ----------------------------------------------
	
	//#CM721661
	// Class method -------------------------------------------------
	//#CM721662
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:46 $";
	}
	
	//#CM721663
	// Constructors -------------------------------------------------
	//#CM721664
	/**
	 * Constructor
	 */
	public RFTId0331 ()
	{
		super();
		
		offEtx = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	//#CM721665
	/**
	 * Pass the electronic statement received from RFT to the Constructor.
	 * @param rftId0331 Order Picking the Result data Sent ID = 0331 electronic statement
	 */
	public RFTId0331 (byte[] rftId0331)
	{
		this();
		setReceiveMessage(rftId0331);
	}
	
	//#CM721666
	// Public methods -----------------------------------------------
	//#CM721667
	/**
	 * Obtain the Worker code from an electronic statement sending the Order Picking result data.
	 * @return   Worker Code	Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	//#CM721668
	/**
	 * Obtain the Consignor code from an electronic statement sending the Order Picking result data.
	 * @return   Consignor Code	Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	//#CM721669
	/**
	 * Obtain the Planned Picking Date from an electronic statement sending the Order Picking result data.
	 * @return   Picking Plan Date	Planned Picking Date
	 */
	public String getPickingPlanDate()
	{
		String pickingPlanDate = getFromBuffer(OFF_PICKING_PLAN_DATE, LEN_PLAN_DATE);
		return pickingPlanDate.trim();
	}
	
	//#CM721670
	/**
	 * Obtain the Case/Piece division from an electronic statement sending the Order Picking result data.
	 * @return   Case Piece Flag	Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		String workForm = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_WORK_FORM);
		return workForm.trim();
	}
	
	//#CM721671
	/**
	 * Obtain the Order No. from an electronic statement sending the Order Picking result data.
	 * @return   Order No 1	Order No. 1 (1)
	 */
	public String getOrderNo_1()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_1, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721672
	/**
	 * Obtain the Order No. from an electronic statement sending the Order Picking result data.
	 * @return   Order No 2	Order No. 1 (2)
	 */
	public String getOrderNo_2()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_2, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721673
	/**
	 * Obtain the Order No. from an electronic statement sending the Order Picking result data.
	 * @return   Order No 3	Order No. 1 (3)
	 */
	public String getOrderNo_3()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_3, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721674
	/**
	 * Obtain the Order No. from an electronic statement sending the Order Picking result data.
	 * @return   Order No 4	Order No. 1 (4)
	 */
	public String getOrderNo_4()
	{
		String orderNo = getFromBuffer(OFF_ORDER_NO_4, LEN_ORDER_NO);
		return orderNo.trim();
	}
	
	//#CM721675
	/**
	 * Obtain the Area No. from an electronic statement sending the Order Picking result data.
	 * @return areaNo	Area No.
	 */
	public String getAreaNo()
	{
	    String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO);
	    return areaNo.trim();
	}
	
	//#CM721676
	/**
	 * Obtain the Zone No. from an electronic statement sending the Order Picking result data.
	 * @return zoneNo	Zone No.
	 */
	public String getZoneNo()
	{
	    String zoneNo = getFromBuffer(OFF_ZONE_NO, LEN_ZONE_NO);
	    return zoneNo.trim();
	}
	
	//#CM721677
	/**
	 * Obtain the Approach direction from an electronic statement sending the Order Picking result data.
	 * @return	
	 */
	
	//#CM721678
	/**
	 * Obtain the Picking Work division from an electronic statement sending the Order Picking result data.
	 * @return pickWorkType
	 */
	public String getPickWorkType()
	{
		String pickWorkType = getFromBuffer(OFF_RETRIEVAL_JOB_TYPE, LEN_JOB_TYPE);
		return pickWorkType.trim();
	}
	
	//#CM721679
	/**
	 * Obtain the Work Time from an electronic statement sending the Order Picking result data.
	 * @return   workTime	Work Time
	 */
	public int getWorkTime()
	{
		int workTime = getIntFromBuffer(OFF_WORK_TIME, LEN_WORK_TIME);
		return workTime;
	}
	
	//#CM721680
	/**
	 * Obtain the Count of mistakes from an electronic statement sending the Order Picking result data.
	 * @return		missCount Count of mistakes
	 */
	public int getMissCount()
	{
		int missCount = getIntFromBuffer(OFF_MISS_COUNT, LEN_MISS_COUNT);
		return missCount;
	}
	
	//#CM721681
	/**
	 * Obtain the Completion class from an electronic statement sending the Order Picking result data.
	 * @return   Completion Flag	Completion class
	 */
	public String getCompletionFlag()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
		return completionFlag.trim();
	}
	
	//#CM721682
	/**
	 * Obtain the Box-change Order from an electronic statement sending the Order Picking result data.
	 * @return   BoxIndex	Box-change Order
	 */
	public String getBoxIndex()
	{
		String workForm = getFromBuffer(OFF_BOX_INDEX, LEN_COMPLETION_FLAG);
		return workForm.trim();
	}
	
	//#CM721683
	/**
	 * Obtain the Result file name from an electronic statement sending the Order Picking result data.
	 * @return   Result File Name	Result file name
	 */
	public String getResultFileName()
	{
		String resultFileName = getFromBuffer(OFF_RESULT_FILE_NAME, LEN_FILE_NAME);
		return resultFileName.trim();
	}
	
	//#CM721684
	/**
	 * Obtain the file record count from an electronic statement sending the Order Picking result data.
	 * @return fileRecord
	 */
	public int getFileRecord()
	{
	    int fileRecord = getIntFromBuffer(OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	    return fileRecord;
	}
	
	//#CM721685
	// Package nethods ----------------------------------------------
	//#CM721686
	// Protected methods --------------------------------------------
	//#CM721687
	// Private methods ---------------------------------------------
	
}
//#CM721688
//end of class

//#CM721832
//$Id: RFTId5330.java,v 1.3 2007/02/07 04:19:48 suresh Exp $

package jp.co.daifuku.wms.retrieval.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM721833
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
//#CM721834
/**
 * Process the " ID = 5330 Response for starting Order Picking" electronic statement electronic statement for socket communication.
 *
 * <p>
 * <table border="1">
 * <CAPTION>Construction of Id1034 electronic statement.</CAPTION>
 * <TR><TH>Field item name</TH>				<TH>Length</TH>			<TH>Content</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>			<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td>			<td>Not available</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>			<td>5330</td></tr>
 * <tr><td>Period for sending by Terminal</td>		<td>6 byte</td>			<td>HHMMSS</td></tr>
 * <tr><td>Period for sending by SERVER</td>	<td>6 byte</td>			<td>HHMMSS</td></tr>
 * <tr><td>Terminal machine No..</td>			<td>3 byte</td>			<td> </td></tr>
 * <tr><td>Worker Code</td>		<td>4 byte</td>			<td> </td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Planned Picking Date</td>			<td>8 byte</td>			<td> </td></tr>
 * <tr><td>Selected Case/Piece division</td><td>1 byte</td><td>0: All, 1: Case, 2: Piece, 3: None</td></tr>
 * <tr><td>Consignor Name</td>			<td>40 byte</td>		<td> </td></tr>
 * <tr><td>Order No. (1)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Code (1)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Name (1)</td>			<td>40 byte</td>		<td> </td></tr>
 * <tr><td>Response flag (1)</td>			<td>1 byte</td>		<td>0:Normal, 1:Processing via other terminal, 2:Work Completed <br>
 * 																5: "Processing Daily Update", 6: Maintenance in progress via other terminal <br>
 * 																8: No corresponding data, 9: Error, Blank: blank Order No.</tr>
 * <tr><td>Order No. (2)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Code (2)</td>		<td>16 byte</td>		<td> </td></tr>	
 * <tr><td>Customer Name (2)</td>			<td>40 byte</td>		<td> </td></tr>
 * <tr><td>Response flag (2)</td>		<td>1 byte</td>			<td>0:Normal, 1:Processing via other terminal, 2:Work Completed <br>
 * 																5: "Processing Daily Update", 6: Maintenance in progress via other terminal <br>
 * 																8: No corresponding data, 9: Error, Blank: blank Order No.</tr>
 * <tr><td>Order No. (3)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Code (3)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Name (3)</td>			<td>40 byte</td>		<td> </td></tr>
 * <tr><td>Response flag (3)</td>		<td>1 byte</td>			<td>0:Normal, 1:Processing via other terminal, 2:Work Completed <br>
 * 																5: "Processing Daily Update", 6: Maintenance in progress via other terminal <br>
 * 																8: No corresponding data, 9: Error, Blank: blank Order No.</tr>
 * <tr><td>Order No. (4)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Code (4)</td>		<td>16 byte</td>		<td> </td></tr>
 * <tr><td>Customer Name (4)</td>			<td>40 byte</td>		<td> </td></tr>
 * <tr><td>Response flag (4)</td>		<td>1 byte</td>			<td>0:Normal, 1:Processing via other terminal, 2:Work Completed <br>
 * 																5: "Processing Daily Update", 6: Maintenance in progress via other terminal <br>
 * 																8: No corresponding data, 9: Error, Blank: blank Order No.</tr>
 * <tr><td>Input area No.</td>		<td>3 byte</td>			<td> </td></tr>
 * <tr><td>Input Zone No.</td>		<td>3 byte</td>			<td> </td></tr>
 * <tr><td>Picking Work division</td>		<td>1 byte</td>			<td>1: Specify Order, 2: Specify Area</td><tr>
 * <tr><td>Work file name</td>		<td>30 byte</td>		<td> </td></tr>
 * <tr><td>Number of records in a file.</td>	<td>6 byte</td>			<td> </td></tr>	
 * <tr><td>Response flag</td>			<td>1 byte</td>			<td>0:Normal, 1:Processing via other terminal, 2:Work Completed <br>
 * 																5: "Processing Daily Update", 6: Maintenance in progress via other terminal <br>
 * 																8:No corresponding data, 9: Error</tr>
 * <tr><td>Details of error</td>			<td>2 byte</td>			<td> </td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>			<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/02</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:48 $
 * @author  $Author: suresh $
 */

public class RFTId5330 extends  SendIdMessage
{
	//#CM721835
	// Class field --------------------------------------------------
	//#CM721836
	/**
	 * Offset the Personnel Code.
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	//#CM721837
	/**
	 * Offset the Off-Set Consignor Code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	//#CM721838
	/**
	 * Offset the Planned Picking Date.
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM721839
	/**
	 * Offset the Selected Case/Piece division.
	 */
	private static final int OFF_SELECT_CASE_PIECE_FLAG = OFF_PLAN_DATE + LEN_PLAN_DATE;
	
	//#CM721840
	/**
	 * Offset the Consignor Name.
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_SELECT_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;
	
	//#CM721841
	/**
	 * Offset the Order No. (1).
	 */
	private static final int OFF_ORDER_NO_1 = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
	
	//#CM721842
	/**
	 * Offset the Customer Code (1).
	 */
	private static final int OFF_CUSTOMER_CODE_1 = OFF_ORDER_NO_1 + LEN_ORDER_NO;
	
	//#CM721843
	/**
	 * Offset the Customer Name (1).
	 */
	private static final int OFF_CUSTOMER_NAME_1 = OFF_CUSTOMER_CODE_1 + LEN_CUSTOMER_CODE;
	
	//#CM721844
	/**
	 * Offset the Response flag (1).
	 */
	private static final int OFF_ANS_CODE_1 = OFF_CUSTOMER_NAME_1 + LEN_CUSTOMER_NAME;

	//#CM721845
	/**
	 * Offset the Order No. (2).
	 */
	private static final int OFF_ORDER_NO_2 = OFF_ANS_CODE_1 + LEN_ANS_CODE;
	
	//#CM721846
	/**
	 * Offset the Customer Code (2).
	 */
	private static final int OFF_CUSTOMER_CODE_2 = OFF_ORDER_NO_2 + LEN_ORDER_NO;
	
	//#CM721847
	/**
	 * Offset the Customer Name (2).
	 */
	private static final int OFF_CUSTOMER_NAME_2 = OFF_CUSTOMER_CODE_2 + LEN_CUSTOMER_CODE;
	
	//#CM721848
	/**
	 * Offset the Response flag (2).
	 */
	private static final int OFF_ANS_CODE_2 = OFF_CUSTOMER_NAME_2 + LEN_CUSTOMER_NAME;

	//#CM721849
	/**
	 * Offset the Order No. (3).
	 */
	private static final int OFF_ORDER_NO_3 = OFF_ANS_CODE_2 + LEN_ANS_CODE;
	
	//#CM721850
	/**
	 * Offset the Customer Code (3).
	 */
	private static final int OFF_CUSTOMER_CODE_3 = OFF_ORDER_NO_3 + LEN_ORDER_NO;
	
	//#CM721851
	/**
	 * Offset the Customer Name (3).
	 */
	private static final int OFF_CUSTOMER_NAME_3 = OFF_CUSTOMER_CODE_3 + LEN_CUSTOMER_CODE;
	
	//#CM721852
	/**
	 * Offset the Response flag (3).
	 */
	private static final int OFF_ANS_CODE_3 = OFF_CUSTOMER_NAME_3 + LEN_CUSTOMER_NAME;

	//#CM721853
	/**
	 * Offset the Order No. (4).
	 */
	private static final int OFF_ORDER_NO_4 = OFF_ANS_CODE_3 + LEN_ANS_CODE;
	
	//#CM721854
	/**
	 * Offset the Customer Code (4).
	 */
	private static final int OFF_CUSTOMER_CODE_4 = OFF_ORDER_NO_4 + LEN_ORDER_NO;
	
	//#CM721855
	/**
	 * Offset the Customer Name (4).
	 */
	private static final int OFF_CUSTOMER_NAME_4 = OFF_CUSTOMER_CODE_4 + LEN_CUSTOMER_CODE;
	
	//#CM721856
	/**
	 * Offset the Response flag (4).
	 */
	private static final int OFF_ANS_CODE_4 = OFF_CUSTOMER_NAME_4 + LEN_CUSTOMER_NAME;
	
	//#CM721857
	/**
	 * Offset the Input area No.
	 */
	private static final int OFF_AREA_NO = OFF_ANS_CODE_4 + LEN_ANS_CODE;
	
	//#CM721858
	/**
	 * Offset the Input Zone No.
	 */
	private static final int OFF_ZONE_NO = OFF_AREA_NO + LEN_AREA_NO;
	
	//#CM721859
	/**
	 * Offset the Picking Work division.
	 */
	private static final int OFF_PICK_WORK_TYPE = OFF_ZONE_NO + LEN_ZONE_NO;

	//#CM721860
	/**
	 * Offset the Work file name.
	 */
	private static final int OFF_WORK_FILE_NAME = OFF_PICK_WORK_TYPE + LEN_RETRIEVAL_WORK_TYPE;
	
	//#CM721861
	/**
	 * Offset the number of records in a file.
	 */
	private static final int OFF_MAX_RECORD_NO = OFF_WORK_FILE_NAME + LEN_FILE_NAME;
		
	//#CM721862
	/**
	 * ID No.
	 */
	public static final String ID = "5330";
	
	//#CM721863
	// Class method ------------------------------------------------
	//#CM721864
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $Date: 2007/02/07 04:19:48 $";
	}
	
	//#CM721865
	// Constructors -------------------------------------------------
	//#CM721866
	/**
	 * Constructor
	 */
	public RFTId5330 ()
	{
		super();
		
		offAnsCode = OFF_MAX_RECORD_NO + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM721867
	// Public methods -----------------------------------------------
	//#CM721868
	/**
	 * Set the Worker Code.
	 * @param workerCode Worker Code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	//#CM721869
	/**
	 * Set the Consignor code.
	 * @param consignorCode Consignor Code
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}
	
	//#CM721870
	/**
	 * Set the Planned Picking Date.
	 * @param planDate Planned Picking Date
	 */
	public void setPlanDate (String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	//#CM721871
	/**
	 * Set the Selected Case/Piece division..
	 * @param casePieceFlag
	 */
	public void setCasePieceFlag (String casePieceFlag)
	{
		setToBuffer(casePieceFlag, OFF_SELECT_CASE_PIECE_FLAG);
	}
	
	//#CM721872
	/**
	 * Set the Consignor Name.
	 * @param consignorName Consignor Name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME);
	}
	
	//#CM721873
	/**
	 * Set the Order No. 1.
	 * @param orderNo Order No. 1
	 */
	public void setOrderNo_1 (String orderNo)
	{
		setToBuffer(orderNo, OFF_ORDER_NO_1);
	}
	
	//#CM721874
	/**
	 * Set the Customer Code 1.
	 * @param customerCode Customer Code1
	 */
	public void setCustomerCode_1 (String customerCode)
	{
		setToBuffer(customerCode, OFF_CUSTOMER_CODE_1);
	}
	
	//#CM721875
	/**
	 * Set the Customer Name 1.
	 * @param customerName Customer Name 1
	 */
	public void setCustomerName_1 (String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME_1);
	}
	
	//#CM721876
	/**
	 * Set the response flag for Order No. 1.
	 * @param ansCode Order No. 1
	 */
	public void setAnsCode_1(String ansCode)
	{
		setToBuffer(ansCode, OFF_ANS_CODE_1);
	}

	//#CM721877
	/**
	 * Set the Order No. 2.
	 * @param orderNo Order No. 2
	 */
	public void setOrderNo_2 (String orderNo)
	{
		setToBuffer(orderNo, OFF_ORDER_NO_2);
	}
	
	//#CM721878
	/**
	 * Set the Customer Code 2.
	 * @param customerCode Customer Code 2
	 */
	public void setCustomerCode_2 (String customerCode)
	{
		setToBuffer(customerCode, OFF_CUSTOMER_CODE_2);
	}
	
	//#CM721879
	/**
	 * Set the Customer Name 2.
	 * @param customerName Customer Name 2
	 */
	public void setCustomerName_2 (String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME_2);
	}
	
	//#CM721880
	/**
	 * Set the response flag for Order No. 2.
	 * @param ansCode Order No. 2
	 */
	public void setAnsCode_2(String ansCode)
	{
		setToBuffer(ansCode, OFF_ANS_CODE_2);
	}

	//#CM721881
	/**
	 * Set the Order No. 3.
	 * @param orderNo Order No. 3
	 */
	public void setOrderNo_3 (String orderNo)
	{
		setToBuffer(orderNo, OFF_ORDER_NO_3);
	}
	
	//#CM721882
	/**
	 * Set the Customer Code 3.
	 * @param customerCode Customer Code 3
	 */
	public void setCustomerCode_3 (String customerCode)
	{
		setToBuffer(customerCode, OFF_CUSTOMER_CODE_3);
	}
	
	//#CM721883
	/**
	 * Set the Customer Name 3.
	 * @param customerName Customer Name 3
	 */
	public void setCustomerName_3 (String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME_3);
	}
	
	//#CM721884
	/**
	 * Set the response flag for Order No. 3.
	 * @param ansCode Order No. 3
	 */
	public void setAnsCode_3(String ansCode)
	{
		setToBuffer(ansCode, OFF_ANS_CODE_3);
	}
	
	//#CM721885
	/**
	 * Set the Order No. 4.
	 * @param orderNo Order No. 4
	 */
	public void setOrderNo_4 (String orderNo)
	{
		setToBuffer(orderNo, OFF_ORDER_NO_4);
	}
	
	//#CM721886
	/**
	 * Set the Customer Code 4.
	 * @param customerCode Customer Code 4
	 */
	public void setCustomerCode_4 (String customerCode)
	{
		setToBuffer(customerCode, OFF_CUSTOMER_CODE_4);
	}
	
	//#CM721887
	/**
	 * Set the Customer Name 4.
	 * @param customerName Customer Name 4
	 */
	public void setCustomerName_4 (String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME_4);
	}
	
	//#CM721888
	/**
	 * Set the response flag for Order No. 4.
	 * @param ansCode Order No. 4
	 */
	public void setAnsCode_4(String ansCode)
	{
		setToBuffer(ansCode, OFF_ANS_CODE_4);
	}
	
	//#CM721889
	/**
	 * Set the Input area No.
	 * @param	areaNo Area No.
	 */
	public void setAreaNo(String areaNo)
	{
		setToBuffer(areaNo, OFF_AREA_NO);
	}
	
	//#CM721890
	/**
	 * Set the Input Zone No..
	 * @param	zoneNo Zone No.
	 */
	public void setZoneNo(String zoneNo)
	{
		setToBuffer(zoneNo, OFF_ZONE_NO);
	}
	
	//#CM721891
	/**
	 * Set the Picking Work division..
	 * @param	pickWorkType Picking Work division
	 */
	public void setPickWorkType(String pickWorkType)
	{
		setToBuffer(pickWorkType, OFF_PICK_WORK_TYPE);
	}
	
	//#CM721892
	/**
	 * Set the Work file name
	 * @param workFileName Response file name
	 */
	public void setWorkFileName (String workFileName)
	{
		setToBuffer(workFileName, OFF_WORK_FILE_NAME);
	}
	
	//#CM721893
	/**
	 * Set the file record count.
	 * @param	fileMaxRecordNo	Number of records in a file.
	 */
	public void setFileMaxRecordNo(int fileMaxRecordNo)
	{
		setToBufferRight(fileMaxRecordNo, OFF_MAX_RECORD_NO, LEN_FILE_RECORD_NUMBER);
	}

	//#CM721894
	/**
	 * Obtain the Consignor code from an electronic statement responding to starting the Order Picking.
	 * @return   Consignor Code  Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	//#CM721895
	/**
	 * Obtain the Planned Picking Date from an electronic statement responding to starting the Order Picking.
	 * @return   Picking Plan Date  Planned Picking Date
	 */
	public String getPlanDate ()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate.trim();
	}

	//#CM721896
	// Package methods ----------------------------------------------
	
	//#CM721897
	// Protected methods --------------------------------------------

	//#CM721898
	// Private methods ----------------------------------------------

}
//#CM721899
//end of class

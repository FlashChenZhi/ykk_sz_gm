// $Id: RFTId5230.java,v 1.2 2006/12/07 09:00:04 suresh Exp $
package jp.co.daifuku.wms.storage.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576669
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576670
/**
 * Storage start response(StorageJANResponse) ID=5230 telegraph message process
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of Id5230 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td> <td></td></tr>
 * <tr><td>ID</td>					<td>4 byte</td> <td>5230</td></tr>
 * <tr><td>Handy send time</td>		<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>		<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>		<td>3 byte</td> <td></td></tr>
 * <tr><td>Worker code</td>			<td>4 byte</td> <td></td></tr>
 * <tr><td>Consignor code</td				<td>16 byte</td><td></td></tr>
 * <tr><td>Consignor name</td>				<td>40 byte</td><td></td></tr>
 * <tr><td>Storage plan date</td>			<td>8 byte</td> <td>YYYYMMDD</td></tr>
 * <tr><td>Selected case piece type</td>		<td>1 byte</td> <td>0:All 1:Case 2:Piece 3:None </td></tr>
 * <tr><td>StorageArea no..</td>			<td>3 byte</td> <td></td></tr>
 * <tr><td>StorageLocation no..</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>Collect Job no..</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>Item code</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>JAN code</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>				<td>40 byte</td><td></td></tr>
 * <tr><td>Qty per bundle</td>			<td>6 byte</td> <td></td></tr>
 * <tr><td>Qty per case</td>			<td>6 byte</td> <td></td></tr>
 * <tr><td>Position</td>					<td>6 byte</td> <td></td></tr>
 * <tr><td>Lot no.</td>			<td>10 byte</td><td></td></tr>
 * <tr><td>Expiry date</td>			<td>8 byte</td> <td></td></tr>
 * <tr><td>Production day</td>				<td>8 byte</td> <td></td></tr>
 * <tr><td>Storage plan qty</td>			<td>9 byte</td> <td></td></tr>
 * <tr><td>Storage complete qty</td>			<td>9 byte</td> <td></td></tr>
 * <tr><td>Case piece type</td>		<td>1 byte</td> <td>1:Case 2:Piece 3:None </td></tr>
 * <tr><td>Total Storage qty</td>			<td>9 byte</td> <td></td></tr>
 * <tr><td>Pending Storage qty</td>			<td>9 byte</td> <td></td></tr>
 * <tr><td>Relocation enable/disable flag</td>	<td>1 byte</td> <td>Mobile rack package 0:No 1:Yes</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td> <td>0x03</td></tr>
 * <tr><td>Detail Error</td>			<td>1 byte</td> <td></td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td> <td></td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:04 $
 * @author  $Author: suresh $
 */
public class RFTId5230 extends SendIdMessage
{
	//#CM576671
	// Class fields --------------------------------------------------
	//#CM576672
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576673
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576674
	/**
	 * Consignor name offset definition
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576675
	/**
	 * Storage plan date offset definition
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME ;
	
	//#CM576676
	/**
	 * Selected case piece type offset definition
	 */
	private static final int OFF_SELECT_CASE_PIECE = OFF_PLAN_DATE + LEN_PLAN_DATE;

	//#CM576677
	/**
	 * Storage Area no. offset definition
	 */
	private static final int OFF_AREA_NO = OFF_SELECT_CASE_PIECE + LEN_CASE_PIECE_FLAG ;
	
	//#CM576678
	/**
	 * Storage Location no. offset definition
	 */
	private static final int OFF_LOCATION = OFF_AREA_NO + LEN_AREA_NO ;
	
	//#CM576679
	/**
	 * Collect Job no. offset definition
	 */
	private static final int OFF_COLLECT_JOB_NO = OFF_LOCATION + LEN_LOCATION ;
	
	//#CM576680
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_COLLECT_JOB_NO + LEN_COLLECT_JOB_NO ;
	
	//#CM576681
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM576682
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM576683
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM576684
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM576685
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM576686
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM576687
	/**
	 * Position offset definition
	 */
	private static final int OFF_UNIT = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576688
	/**
	 * Lot no. offset definition
	 */
	private static final int OFF_LOT_NO = OFF_UNIT + LEN_UNIT ;
	
	//#CM576689
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_LOT_NO + LEN_LOT_NO ;
	
	//#CM576690
	/**
	 * Production day offset definition
	 */
	private static final int OFF_MANUFACTURE_DATE = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;
	
	//#CM576691
	/**
	 * Storage plan qty offset definition
	 */
	private static final int OFF_PLAN_QTY = OFF_MANUFACTURE_DATE + LEN_MANUFACTURE_DATE ;
	
	//#CM576692
	/**
	 * Storage complete qty offset definition
	 */
	private static final int OFF_RESULT_QTY = OFF_PLAN_QTY + LEN_PLAN_QTY ;
	
	//#CM576693
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_RESULT_QTY + LEN_RESULT_QTY ;
	
	//#CM576694
	/**
	 * Total Storage qty offset definition
	 */
	private static final int OFF_TOTAL_STORAGE_QTY = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;
	
	//#CM576695
	/**
	 * Pending Storage qty offset definition
	 */
	private static final int OFF_REMAINING_STORAGE_QTY = OFF_TOTAL_STORAGE_QTY + LEN_TOTAL_ITEM_COUNT ;

	//#CM576696
	/**
	 * Relocation enable/disable flag offset definition
	 */
	private static final int OFF_MOBILE_RACK_FLAG = OFF_REMAINING_STORAGE_QTY + LEN_REMAINING_ITEM_COUNT;
	
	//#CM576697
	/**
	 * ID number
	 */
	public static final String ID = "5230";
	
	//#CM576698
	/**
	 * Mobile rack package "not available" flag
	 */
	public static final String NO_MOBILE_RACK_FLAG = "0";
	
	//#CM576699
	/**
	 * Mobile rack package "available" flag
	 */
	public static final String MOBILE_RACK_FLAG = "1";
	
	//#CM576700
	// Class variables -----------------------------------------------

	//#CM576701
	// Class method --------------------------------------------------
	//#CM576702
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:04 $") ;
	}

	//#CM576703
	// Constructors --------------------------------------------------
	//#CM576704
	/**
	 * Constructor
	 */
	public RFTId5230 ()
	{
		super() ;
		offAnsCode = OFF_MOBILE_RACK_FLAG + LEN_MOBILE_RACK_FLAG;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576705
	// Public methods ------------------------------------------------
	//#CM576706
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM576707
	/**
	 * Set Consignor code
	 * @param  consignorCode  Consignor code
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM576708
	/**
	 * Set Consignor name
	 * @param  consignorName  Consignor name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME) ;
	}
	
	//#CM576709
	/**
	 * Set Storage plan date
	 * @param  planDate  Storage plan date
	 */
	public void setPlanDate (String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE) ;
	}
	
	//#CM576710
	/**
	 * Set Selected case piece typ
	 * @param selectCasePiece	Selected case piece type
	 */
	public void setSelectCasePiece(String selectCasePiece)
	{
	    setToBuffer(selectCasePiece, OFF_SELECT_CASE_PIECE);
	}

	//#CM576711
	/**
	 * Set Storage Area no.
	 * @param  storageAreaNo  StorageArea no..
	 */
	public void setStorageAreaNo (String storageAreaNo)
	{
		setToBuffer(storageAreaNo, OFF_AREA_NO) ;
	}
	
	//#CM576712
	/**
	 * Set Storage Location no.
	 * @param  storageLocationNo  StorageLocation no..
	 */
	public void setStorageLocationNo (String storageLocationNo)
	{
		setToBuffer(storageLocationNo, OFF_LOCATION) ;
	}
	
	//#CM576713
	/**
	 * Set Collect Job no.
	 * @param  collectJobNo  Collect Job no..
	 */
	public void setCollectJobNo (String collectJobNo)
	{
		setToBuffer(collectJobNo, OFF_COLLECT_JOB_NO) ;
	}
	
	//#CM576714
	/**
	 * Set Item code
	 * @param  itemCode  Item code
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE) ;
	}
	
	//#CM576715
	/**
	 * Set JAN code
	 * @param  JANCode  JAN code
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode, OFF_JAN_CODE) ;
	}
	
	//#CM576716
	/**
	 * Set Bundle ITF
	 * @param  bundleITF  Bundle ITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF) ;
	}
	
	//#CM576717
	/**
	 * Set Case ITF
	 * @param  ITF  Case ITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF, OFF_ITF) ;
	}
	
	//#CM576718
	/**
	 * Set Item name
	 * @param  itemName  Item name
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME) ;
	}
	
	//#CM576719
	/**
	 * Set Qty per bundle
	 * @param  bundleEnteringQty  Qty per bundle
	 */
	public void setBundleEnteringQty (int bundleEnteringQty)
	{
		//#CM576720
		// Store data with right justification
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
	}
	
	//#CM576721
	/**
	 * Set Qty per case
	 * @param  enteringQty  Qty per case
	 */
	public void setEnteringQty (int enteringQty)
	{
		//#CM576722
		// Store data with right justification
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
	}
	
	//#CM576723
	/**
	 * Set Position
	 * @param  unit  Position
	 */
	public void setUnit (String unit)
	{
		setToBuffer(unit, OFF_UNIT) ;
	}
	
	//#CM576724
	/**
	 * Set Lot no.
	 * @param  lotNo  Lot no.
	 */
	public void setLotNo (String lotNo)
	{
		setToBuffer(lotNo, OFF_LOT_NO) ;
	}
	
	//#CM576725
	/**
	 * Set Expiry date
	 * @param  useByDate  Expiry date
	 */
	public void setUseByDate (String useByDate)
	{
		setToBuffer(useByDate, OFF_USE_BY_DATE) ;
	}
	
	//#CM576726
	/**
	 * Set Production day
	 * @param  manufactureDate  Production day
	 */
	public void setManufactureDate (String manufactureDate)
	{
		setToBuffer(manufactureDate, OFF_MANUFACTURE_DATE) ;
	}
	
	//#CM576727
	/**
	 * Set Storage plan qty
	 * @param  storagePlanQty  Storage plan qty
	 */
	public void setStoragePlanQty (int storagePlanQty)
	{
		//#CM576728
		// Store data with right justification
		setToBufferRight(storagePlanQty, OFF_PLAN_QTY, LEN_PLAN_QTY) ;
	}
	
	//#CM576729
	/**
	 * Set Storage complete qty
	 * @param  storageCompletionQty  Storage complete qty
	 */
	public void setStorageCompletionQty (int storageCompletionQty)
	{
		//#CM576730
		// Store data with right justification
		setToBufferRight(storageCompletionQty, OFF_RESULT_QTY, LEN_RESULT_QTY) ;
	}
	
	//#CM576731
	/**
	 * Set Case piece type
	 * @param  casePieceFlag  Case piece type
	 */
	public void setCasePieceFlag (String casePieceFlag)
	{
		setToBuffer(casePieceFlag, OFF_CASE_PIECE_FLAG) ;
	}
	
	//#CM576732
	/**
	 * Set Total Storage qty
	 * @param  totalStorageQty  Total Storage qty
	 */
	public void setTotalStorageQty (int totalStorageQty)
	{
		//#CM576733
		// Store data with right justification
		setToBufferRight(totalStorageQty, OFF_TOTAL_STORAGE_QTY, LEN_TOTAL_ITEM_COUNT) ;
	}
	
	//#CM576734
	/**
	 * Set Pending Storage qty
	 * @param  remainingStorageQty  Pending Storage qty
	 */
	public void setRemainingStorageQty (int remainingStorageQty)
	{
		//#CM576735
		// Store data with right justification
		setToBufferRight(remainingStorageQty, OFF_REMAINING_STORAGE_QTY, LEN_REMAINING_ITEM_COUNT) ;
	}

	//#CM576736
	/**
	 * Set Relocation enable/disable flag
	 * @param  mobileRackFlag  Relocation enable/disable flag
	 */
	public void setMobileRackFlag (String mobileRackFlag)
	{
		setToBuffer(mobileRackFlag, OFF_MOBILE_RACK_FLAG) ;
	}
	
	//#CM576737
	// Package methods -----------------------------------------------

	//#CM576738
	// Protected methods ---------------------------------------------

	//#CM576739
	// Private methods -----------------------------------------------

}
//#CM576740
//end of class


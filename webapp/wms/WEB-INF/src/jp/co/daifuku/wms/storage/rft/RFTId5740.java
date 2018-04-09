// $Id: RFTId5740.java,v 1.2 2006/12/07 09:00:03 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

//#CM576782
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM576783
/**
 * Relocation Storage start response ID=5740 telegraph message process
 * 
 * <p>
  * <table border="1">
 * <CAPTION>Structure of Id5740 telegraph message</CAPTION>
 * <TR><TH>Item name</TH>				<TH>Length</TH>	<TH>Contents</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td>	<td>Not used</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>5740</td></tr>
 * <tr><td>Handy send time</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Server send time</td>		<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>Handy no..</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>Consignor code</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Consignor name</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>Relocation origin areaNo.</td>		<td>3 byte</td><td></td></tr>
 * <tr><td>Origin location no.</td><td>16 byte</td><td></td></tr>
 * <tr><td>Relocation Job no.</td>			<td>8 byte</td><td>Set Relocation Work info DB Collect Job no.
 * 													<BR>From front</td></tr>
 * <tr><td>Item code</td>			<td>16 byte</td><td>Not used</td></tr>
 * <tr><td>JAN code</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Bundle ITF</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Case ITF</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>Item name</td>			<td>40 byte</td><td></td></tr>
 * <tr><td>Qty per bundle</td>			<td>6 byte</td>	<td></td></tr>
 * <tr><td>Qty per case</td>			<td>6 byte</td>	<td></td></tr>
 * <tr><td>Relocation storage possible qty</td>		<td>9 byte</td>	<td></td></tr>
 * <tr><td>Case piece type</td>	<td>1 byte</td>	<td>Not used</td></tr>
 * <tr><td>Expiry date</td>			<td>8 byte</td>	<td></td></tr>
 * <tr><td>List file name</td>		<td>30 byte</td><td></td></tr>
 * <tr><td>File record count</td>	<td>6 byte</td>	<td></td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td>	<td>0:Normal  1:In process in another terminal 2:Work already completed.
 * 													<br>5:Daily update in progress 7:Multiple target data
 * 													<br>8:Target data does not exist 9:Error<td></tr>
 * <tr><td>Error details				<td>6 byte</td>	<td> </td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td>	<td></td></tr>
 * </table>
 * </p>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:03 $
 * @author  $Author: suresh $
 */
public class RFTId5740 extends SendIdMessage
{
	//#CM576784
	// Class fields --------------------------------------------------
	//#CM576785
	/**
	 * Worker code offset definition
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM576786
	/**
	 * Consignor code offset definition
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM576787
	/**
	 * Consignor name offset definition
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM576788
	/**
	 * Relocation origin area No. offset definition
	 */
	private static final int OFF_SOURCE_AREA_NO = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME ;
	
	//#CM576789
	/**
	 * Origin location no. offset definition
	 */
	private static final int OFF_SOURCE_LOCATION_NO = OFF_SOURCE_AREA_NO + LEN_AREA_NO ;
	
	//#CM576790
	/**
	 * Relocation Job no. offset definition
	 */
	private static final int OFF_MOVILE_JOB_NO = OFF_SOURCE_LOCATION_NO + LEN_LOCATION ;
	
	//#CM576791
	/**
	 * Item code offset definition
	 */
	private static final int OFF_ITEM_CODE = OFF_MOVILE_JOB_NO + LEN_MOVE_JOB_NO; ;
	
	//#CM576792
	/**
	 * JAN code offset definition
	 */
	private static final int OFF_JAN_CODE = OFF_ITEM_CODE + LEN_ITEM_CODE ;
	
	//#CM576793
	/**
	 * Bundle ITF offset definition
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE ;
	
	//#CM576794
	/**
	 * Case ITF offset definition
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF ;
	
	//#CM576795
	/**
	 * Item name offset definition
	 */
	private static final int OFF_ITEM_NAME = OFF_ITF + LEN_ITF ;
	
	//#CM576796
	/**
	 * Qty per bundle offset definition
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	//#CM576797
	/**
	 * Qty per case offset definition
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;
	
	//#CM576798
	/**
	 * Relocation storage possible qty offset definition
	 */
	private static final int OFF_MOVEMENT_INSTORE_ABLE_QTY = OFF_ENTERING_QTY + LEN_ENTERING_QTY ;
	
	//#CM576799
	/**
	 * Case piece type offset definition
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_MOVEMENT_INSTORE_ABLE_QTY + LEN_PLAN_QTY ;
	
	//#CM576800
	/**
	 * Expiry date offset definition
	 */
	private static final int OFF_USE_BY_DATE = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;
	
	//#CM576801
	/**
	 * List file name offset definition
	 */
	private static final int OFF_ANS_FILE_NAME = OFF_USE_BY_DATE + LEN_USE_BY_DATE ;
	
	//#CM576802
	/**
	 * File record count offset definition
	 */
	private static final int OFF_FILE_RECORD_NO = OFF_ANS_FILE_NAME + LEN_FILE_NAME ;
		
	//#CM576803
	/**
	 * ID number
	 */
	public static final String ID = "5740";

	//#CM576804
	// Class variables -----------------------------------------------

	//#CM576805
	// Class method --------------------------------------------------
	//#CM576806
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 09:00:03 $") ;
	}

	//#CM576807
	// Constructors --------------------------------------------------
	//#CM576808
	/**
	 * Constructor
	 */
	public RFTId5740 ()
	{
		super() ;
		offAnsCode = OFF_FILE_RECORD_NO + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	

	//#CM576809
	// Public methods ------------------------------------------------
	//#CM576810
	/**
	 * Set Worker code
	 * @param  workerCode  Worker code
	 */
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	//#CM576811
	/**
	 * Set Consignor code
	 * @param  consignorCode  Consignor code
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM576812
	/**
	 * Set Consignor name
	 * @param  consignorName  Consignor name
	 */
	public void setConsignorName (String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME) ;
	}
	
	//#CM576813
	/**
	 * Set Relocation origin area no.
	 * @param  sourceAreaNo  Origin location no.
	 */
	public void setSourceAreaNo (String sourceAreaNo)
	{
		setToBuffer(sourceAreaNo, OFF_SOURCE_AREA_NO) ;
	}
	
	//#CM576814
	/**
	 * Set Origin location no.
	 * @param  sourceLocationNo  Origin location no.
	 */
	public void setSourceLocationNo (String sourceLocationNo)
	{
		setToBuffer(sourceLocationNo, OFF_SOURCE_LOCATION_NO) ;
	}
	
	//#CM576815
	/**
	 * Set Relocation Job no.
	 * @param  movileJobNo  Relocation Job no.
	 */
	public void setMovileJobNo (String movileJobNo)
	{
		setToBuffer(movileJobNo, OFF_MOVILE_JOB_NO) ;
	}
	
	//#CM576816
	/**
	 * Set Item code
	 * @param  itemCode  Item code
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE) ;
	}
	
	//#CM576817
	/**
	 * Set JAN code
	 * @param  JANCode  JAN code
	 */
	public void setJANCode (String JANCode)
	{
		setToBuffer(JANCode, OFF_JAN_CODE) ;
	}
	
	//#CM576818
	/**
	 * Set Bundle ITF
	 * @param  bundleITF  Bundle ITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF) ;
	}
	
	//#CM576819
	/**
	 * Set Case ITF
	 * @param  ITF  Case ITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF, OFF_ITF) ;
	}
	
	//#CM576820
	/**
	 * Set Item name
	 * @param  itemName  Item name
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME) ;
	}
	
	//#CM576821
	/**
	 * Set Qty per bundle
	 * @param  bundleEnteringQty  Qty per bundle
	 */
	public void setBundleEnteringQty (String bundleEnteringQty)
	{
		//#CM576822
		// Store data with right justification
		setToBufferRight(bundleEnteringQty, OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY) ;
	}
	
	//#CM576823
	/**
	 * Set Qty per case
	 * @param  enteringQty  Qty per case
	 */
	public void setEnteringQty (String enteringQty)
	{
		//#CM576824
		// Store data with right justification
		setToBufferRight(enteringQty, OFF_ENTERING_QTY, LEN_ENTERING_QTY) ;
	}
	
	//#CM576825
	/**
	 * Set Relocation storage possible qty
	 * @param  movementInstoreAbleQty  Relocation storage possible qty
	 */
	public void setMovementInstoreAbleQty (String movementInstoreAbleQty)
	{
		//#CM576826
		// Store data with right justification
		setToBufferRight(movementInstoreAbleQty, OFF_MOVEMENT_INSTORE_ABLE_QTY, LEN_PLAN_QTY) ;
	}
	
	//#CM576827
	/**
	 * Set Case piece type
	 * @param  casePieceFlag  Case piece type
	 */
	public void setCasePieceFlag (String casePieceFlag)
	{
		setToBuffer(casePieceFlag, OFF_CASE_PIECE_FLAG) ;
	}
	
	//#CM576828
	/**
	 * Set Expiry date
	 * @param useByDate Expiry date
	 */
	public void setUseByDate (String useByDate)
	{
		setToBuffer(useByDate , OFF_USE_BY_DATE);
	}

	//#CM576829
	/**
	 * Set List file name
	 * @param  ansFileName Relocation Job no.
	 */
	public void setAnsFileName (String ansFileName)
	{
		setToBuffer(ansFileName, OFF_ANS_FILE_NAME) ;
	}
	
	//#CM576830
	/**
	 * Set File record count
	 * @param fileRecordNo File record count
	 */
	public void setFileRecordNo(int fileRecordNo)
	{
		setToBufferRight(fileRecordNo, OFF_FILE_RECORD_NO, LEN_FILE_RECORD_NUMBER);
	}

	//#CM576831
	// Package methods -----------------------------------------------
	//#CM576832
	// Protected methods ---------------------------------------------
	//#CM576833
	// Private methods -----------------------------------------------

}
//#CM576834
//end of class


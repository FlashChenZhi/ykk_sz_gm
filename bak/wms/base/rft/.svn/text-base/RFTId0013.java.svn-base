// $Id: RFTId0013.java,v 1.2 2006/11/14 06:09:14 suresh Exp $
package jp.co.daifuku.wms.base.rft;


//#CM702125
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702126
/**
 * Process Item information inquiry ID=0013 telegram. 
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id0013</CAPTION>
 * <TR><TH>Item name</TH>			<TH>Length</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td></tr>
 * <tr><td>SEQ No.</td>			<td>4 byte</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>		<td>6 byte</td></tr>
 * <tr><td>Handy title machine No.</td>		<td>3 byte</td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td></tr>
 * <tr><td>Scan Item Code1</td>	<td>16 byte</td></tr>
 * <tr><td>Scan Item Code2</td>	<td>16 byte</td></tr>
 * <tr><td>Mode of packing</td>				<td>1 byte</td></tr>
 * <tr><td>Retrieval object flag</td>		<td>1 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:14 $
 * @author  $Author: suresh $
 */
public class RFTId0013 extends RecvIdMessage
{
	//#CM702127
	// Class fields --------------------------------------------------
	//#CM702128
	/**
	 * Definition of Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702129
	/**
	 * Definition of offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM702130
	/**
	 * Definition of scanning Item Code1 offset
	 */
	private static final int OFF_SCAN_ITEM_CODE1 = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	//#CM702131
	/**
	 * Definition of scanning Item Code2 offset
	 */
	private static final int OFF_SCAN_ITEM_CODE2 = OFF_SCAN_ITEM_CODE1 + LEN_ITEM_CODE;
	
	//#CM702132
	/**
	 * Definition of offset of Mode of packing
	 */
	private static final int OFF_ITEM_FORM = OFF_SCAN_ITEM_CODE2 + LEN_ITEM_CODE;

	//#CM702133
	/**
	 * Definition of retrieval object flag offset
	 */
	private static final int OFF_SEARCH_TABLES = OFF_ITEM_FORM + LEN_WORK_FORM;

	//#CM702134
	/**
	 * ID Number
	 */
	public static final String ID = "0013";
	

	//#CM702135
	// Class variables -----------------------------------------------

	//#CM702136
	// Class method --------------------------------------------------
	//#CM702137
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:14 $";
	}

	//#CM702138
	// Constructors --------------------------------------------------
	//#CM702139
	/**
	 * Constructor
	 */
	public RFTId0013 ()
	{
		super() ;

		offEtx = OFF_SEARCH_TABLES + LEN_SEARCH_TABLES;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	//#CM702140
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0013 Item information inquiry ID=0013 telegram
	 */
	public RFTId0013 (byte[] rftId0013)
	{
		this() ;

		setReceiveMessage(rftId0013) ;
	}

	//#CM702141
	// Public methods ------------------------------------------------
	//#CM702142
	/**
	 * Acquire Person in charge code from Item information inquiry telegram. 
	 * @return		Person in charge code
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM702143
	/**
	 * Acquire Consignor Code from Item information inquiry telegram. 
	 * @return		Consignor Code
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	//#CM702144
	/**
	 * Acquire Item Code1 from Item information inquiry telegram. 
	 * @return		Item Code
	 */
	public String getScanItemCode1 ()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE1, LEN_ITEM_CODE);
		return itemCode.trim();
	}
	
	//#CM702145
	/**
	 * Acquire Item Code2 from Item information inquiry telegram. 
	 * @return		Item Code
	 */
	public String getScanItemCode2 ()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE2, LEN_ITEM_CODE);
		return itemCode.trim();
	}

	//#CM702146
	/**
	 * Acquire Mode of packing from Item information inquiry telegram. 
	 * @return		Case/Piece flag
	 * 				1:Case 
	 * 				2:Piece
	 */
	public String getItemForm ()
	{
		String itemForm = getFromBuffer(OFF_ITEM_FORM, LEN_CASE_PIECE_FLAG);
		return itemForm.trim();
	}
	
	//#CM702147
	/**
	 * Acquire retrieval object flag from Item information inquiring telegram. 
	 * @return		"1"
	 */
	public String getSearchTables ()
	{
		String searchTables = getFromBuffer(OFF_SEARCH_TABLES, LEN_SEARCH_TABLES);
		return searchTables.trim();
	}

	//#CM702148
	// Package methods -----------------------------------------------

	//#CM702149
	// Protected methods ---------------------------------------------

	//#CM702150
	// Private methods -----------------------------------------------

}
//#CM702151
//end of class


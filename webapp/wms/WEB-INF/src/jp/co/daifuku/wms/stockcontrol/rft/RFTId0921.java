//#CM10385
//$Id: RFTId0921.java,v 1.2 2006/09/27 03:00:34 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

//#CM10386
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM10387
/**
 * Process the electronic statement of "stock list request Id = 0921" in the socket communication.
 *
 * <p>
 * <table>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy Transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server ーTransmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy terminalNo</td>		<td>3 byte</td></tr>
 * <tr><td></td>		<td>4 byte</td></tr>
 * <tr><td>Consignor code</td>			<td>16 byte</td></tr>
 * <tr><td>Location No.</td>		<td>16 byte</td></tr>
 * <tr><td>Load size</td>				<td>1 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:34 $
 * @author  $Author: suresh $
 */

public class RFTId0921 extends RecvIdMessage
{
	//#CM10388
	// Class field --------------------------------------------------
	//#CM10389
	/**
	 * Offset
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	//#CM10390
	/**
	 * Off-Set the Consignor code.
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM10391
	/**
	 * Offset Area No.
	 */
	private static final int OFF_AREA = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	//#CM10392
	/**
	 * Offset Location No.
	 */
	private static final int OFF_LOCATION = OFF_AREA + LEN_AREA_NO;
	
	//#CM10393
	/**
	 * Offset the Load Size.
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_LOCATION + LEN_LOCATION;
	
	//#CM10394
	/**
	 * Field that represents all the Load Sizes.
	 */
	public static final String ITEM_FORM_All = "0" ;

	//#CM10395
	/**
	 * ID No.
	 */
	public static final String ID = "0921";

	//#CM10396
	// Class variables ----------------------------------------------

	//#CM10397
	// Class method -------------------------------------------------
	//#CM10398
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/09/27 03:00:34 $";
	}

	//#CM10399
	//Constructors --------------------------------------------------
	//#CM10400
	/**
	 * Generate Instance
	 */
	public RFTId0921()
	{
		super();

		offEtx = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM10401
	/**
	 * Pass the electronic statement received from RFT to the Constructors.
	 * @param rftId0921 stock list requesting Id = 0921 electronic statement
	 */
	public RFTId0921(byte[] rftId0921)
	{
		this();

		setReceiveMessage(rftId0921);
	}

	//#CM10402
	// Public methods -----------------------------------------------
	//#CM10403
	/**
	 * Obtain person in charge code from stock list requesting electronic statement.
	 * @return   
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}

	//#CM10404
	/**
	 * Obtain the Consignor code from the stock list request electronic statement.
	 * @return   Consignor code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	//#CM10405
	/**
	 * Obtain the Area No. from the stock list request electronic statement.
	 * @return   Area No.
	 */
	public String getAreaNo()
	{
		String areaNo = getFromBuffer(OFF_AREA, LEN_AREA_NO);
		return areaNo.trim();
	}
	
	//#CM10406
	/**
	 * Obtain the Location No. from the stock list request electronic statement.
	 * @return   Location No.
	 */
	public String getLocationNo()
	{
		String locationNo = getFromBuffer(OFF_LOCATION, LEN_LOCATION);
		return locationNo.trim();
	}
	
	//#CM10407
	/**
	 * Obtain the Load Size from the stock list request electronic statement.
	 * @return   Load size
	 */
	public String getItemForm()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG);
		return casePieceFlag.trim();
	}

	//#CM10408
	// Package methods ----------------------------------------------

	//#CM10409
	// Private methods ----------------------------------------------

}
//#CM10410
//end of class

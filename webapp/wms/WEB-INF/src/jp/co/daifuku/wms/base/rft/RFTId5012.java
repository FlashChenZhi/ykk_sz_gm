// $Id: RFTId5012.java,v 1.2 2006/11/14 06:09:18 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702358
/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702359
/**
 * Make "Consignor Code response ID =   1005" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id1005</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td></tr>
 * <tr><td>Consignor Name</td>			<td>40 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td> Detailed Error</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:18 $
 * @author  $Author: suresh $
 */

public class RFTId5012 extends SendIdMessage
{
	//#CM702360
	// Class field --------------------------------------------------
	//#CM702361
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702362
	/**
	 * Offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM702363
	/**
	 * Offset of Consignor Name
	 */
	private static final int OFF_CONSIGNOR_NAME = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM702364
	/**
	 * ID Number
	 */
	public static final String ID = "5012";

	//#CM702365
	// Class method ------------------------------------------------
	//#CM702366
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:18 $";
	}
	
	//#CM702367
	// Constructors -------------------------------------------------
	//#CM702368
	/**
	 * Generate the instance. 
	 */
	public RFTId5012 ()
	{
		super();

		offAnsCode = OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM702369
	// Public methods -----------------------------------------------
	//#CM702370
	/**
	 * Set Person in charge code. 
	 * @param	workerCode	Person in charge code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE) ;
	}
	
	//#CM702371
	/**
	 * Set Consignor Code.
	 * @param	consignorCode	Consignor Code
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode , OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM702372
	/**
	 * Set Consignor Name.
	 * @param	consignorName	Consignor Name
	 */
	public void setConsignorName(String consignorName)
	{
		setToBuffer(consignorName , OFF_CONSIGNOR_NAME) ;
	}
	
	//#CM702373
	// Package methods ----------------------------------------------
	
	//#CM702374
	// Protected methods --------------------------------------------
	
	//#CM702375
	// Private methods ----------------------------------------------

}
//#CM702376
//end of class

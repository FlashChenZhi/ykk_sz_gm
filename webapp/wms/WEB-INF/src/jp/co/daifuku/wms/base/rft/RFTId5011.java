// $Id: RFTId5011.java,v 1.2 2006/11/14 06:09:18 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702339
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702340
/**
 * Make "Plan date response ID =   5011" telegram in the socket communication. 
 *
 * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id5001</CAPTION>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Worker code</td>		<td>4 byte</td></tr>
 * <tr><td>Consignor Code</td>			<td>16 byte</td></tr>
 * <tr><td>Work plan date</td>			<td>40 byte</td></tr>
 * <tr><td>Response flag</td>			<td>1 byte</td></tr>
 * <tr><td> Detailed Error</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/22</TD><TD>T.Konishi</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:18 $
 * @author  $Author: suresh $
 */

public class RFTId5011 extends SendIdMessage
{
	//#CM702341
	// Class field --------------------------------------------------
	//#CM702342
	/**
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702343
	/**
	 * Offset of Consignor Code
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	//#CM702344
	/**
	 * Offset of Work plan date
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	//#CM702345
	/**
	 * ID Number
	 */
	public static final String ID = "5011";

	//#CM702346
	// Class method ------------------------------------------------
	//#CM702347
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:18 $";
	}
	
	//#CM702348
	// Constructors -------------------------------------------------
	//#CM702349
	/**
	 * Generate the instance. 
	 */
	public RFTId5011 ()
	{
		super();

		offAnsCode = OFF_PLAN_DATE + LEN_PLAN_DATE;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	//#CM702350
	// Public methods -----------------------------------------------
	//#CM702351
	/**
	 * Set Person in charge code. 
	 * @param	workerCode	Person in charge code
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode , OFF_WORKER_CODE) ;
	}
	
	//#CM702352
	/**
	 * Set Consignor Code.
	 * @param	consignorCode	Consignor Code
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode , OFF_CONSIGNOR_CODE) ;
	}
	
	//#CM702353
	/**
	 * Set Work plan date
	 * @param	planDate	Work plan date
	 */
	public void setPlanDate(String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	//#CM702354
	// Package methods ----------------------------------------------
	
	//#CM702355
	// Protected methods --------------------------------------------
	
	//#CM702356
	// Private methods ----------------------------------------------

}
//#CM702357
//end of class

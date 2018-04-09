//#CM701930
//$Id: RFTId0001.java,v 1.2 2006/11/14 06:09:10 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701931
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 //#CM701932
 /**
  * Process Socket communication of [System report ID = 0001] telegram.
  * 
  * <p>
 * <table border="1">
 * <CAPTION>Structure of telegram of Id0003</CAPTION>
  * <tr><td>STX</td>			<td>1 byte</td></tr>
  * <tr><td>SEQ NO</td>			<td>4 byte</td></tr>
  * <tr><td>ID</td>				<td>4 byte</td></tr>
  * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Handy title machine No</td>	<td>3 byte</td></tr>
  * <tr><td>Report flag </td>		<td>1 byte</td></tr>
  * <tr><td>Terminal flag </td>		<td>2 byte</td></tr>
  * <tr><td>Terminal IP Address</td>	<td>15 byte</td></tr>
  * <tr><td>ETX</td>			<td>1 byte</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:10 $
  * @author  $Author: suresh $
  */
  
public class RFTId0001 extends RecvIdMessage
{
	//#CM701933
	// Class field --------------------------------------------------
	//#CM701934
	/**
	 * Offset of Report flag
	 */
	private static final int OFF_REPORT_FLAG = LEN_HEADER;
	
	//#CM701935
	/**
	 * Offset of Terminal flag
	 */
	private static final int OFF_TERMINAL_TYPE = OFF_REPORT_FLAG + LEN_REPORT_FLAG ;
	
	//#CM701936
	/**
	 * Offset of Terminal IP Address
	 */
	private static final int OFF_IP_ADDRESS = OFF_TERMINAL_TYPE + LEN_TERMINAL_TYPE ;
	
	//#CM701937
	/**
	 * ID Number
	 */
	public static final String ID = "0001";

	//#CM701938
	/**
	 * Report flag  : Start
	 */
	public static final String REPORT_FLAG_START = "0";
	
	//#CM701939
	/**
	 * Report flag  : Completed
	 */
	public static final String REPORT_FLAG_END = "1";
	
	//#CM701940
	/**
	 * Report flag  : Rest
	 */
	public static final String REPORT_FLAG_REST ="3";

	//#CM701941
	/**
	 * Report flag  : Restart
	 */
	public static final String REPORT_FLAG_REPENDING ="4";

	//#CM701942
	// Class variables ----------------------------------------------
	
	//#CM701943
	// Class method ------------------------------------------------
	//#CM701944
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:10 $";
	}
	
	//#CM701945
	// Constructors -------------------------------------------------
	//#CM701946
	/**
	 * Generate the instance. 
	 */
	public RFTId0001 ()
	{
		super() ;

		offEtx = OFF_IP_ADDRESS + LEN_IP_ADDRESS;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM701947
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0001 System report ID = 0001 telegram
	 */
	public RFTId0001 (byte[] rftId0001)
	{
		this();

		setReceiveMessage(rftId0001);
	}
	
	//#CM701948
	// Public methods -----------------------------------------------
	//#CM701949
	/**
	 * Acquire Work type from system report telegram. 
	 * @return   Report flag 
	 */
	public String getReportFlag()
	{
		String reportFlag = getFromBuffer(OFF_REPORT_FLAG , LEN_WORK_FORM) ;
		return reportFlag;
	}
	
	//#CM701950
	/**
	 * Acquire Terminal flag from system report telegram. 
	 * @return   Terminal flag 
	 */
	public String getTerminalType()
	{
		String terminalType = getFromBuffer(OFF_TERMINAL_TYPE , LEN_TERMINAL_TYPE) ;
		return terminalType.trim();
	}
	
	//#CM701951
	/**
	 * Acquire Terminal IP Address from system report telegram. 
	 * @return   Terminal IP Address
	 */
	public String getIpAddress()
	{
		String ipAddress = getFromBuffer(OFF_IP_ADDRESS , LEN_IP_ADDRESS) ;
		return ipAddress;
	}
	
	//#CM701952
	// Package methods ----------------------------------------------
	
	//#CM701953
	// Protected methods --------------------------------------------
	
	//#CM701954
	// Private methods ----------------------------------------------
	
}
//#CM701955
//end of class

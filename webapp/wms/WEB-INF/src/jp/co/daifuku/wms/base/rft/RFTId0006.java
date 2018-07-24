//#CM702031
//$Id: RFTId0006.java,v 1.2 2006/11/14 06:09:12 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702032
/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 //#CM702033
 /**
  *  Work data report ID = 0006 Telegram is processed. 
  * 
  * <p>
  * <table>
  * <CAPTION>Structure of telegram of Id0006</CAPTION>
  * <tr><td>STX</td>				<td>1 byte</td></tr>
  * <tr><td>SEQ NO</td>				<td>4 byte</td></tr>
  * <tr><td>ID</td>					<td>4 byte</td></tr>
  * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
  * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
  * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
  * <tr><td>ID when results are transmitted</td>		<td>4 byte</td></tr>
  * <tr><td>Work File Name</td>		<td>30 byte</td></tr>
  * <tr><td>Row No</td>				<td>4 byte</td></tr>
  * <tr><td>Data size</td>		<td>3 byte</td></tr>
  * <tr><td>Content Data</td>			<td>512 byte</td></tr>
  * <tr><td>ETX</td>				<td>1 byte</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:12 $
  * @author  $Author: suresh $
  */
  
public class RFTId0006 extends RecvIdMessage
{
	//#CM702034
	// Class field --------------------------------------------------
	//#CM702035
	/**
	 * Offset of Person in charge code
	 */
	public static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702036
	/**
	 * Offset of ID when results are transmitted
	 */
	private static final int OFF_RESULT_SEND_ID = OFF_WORKER_CODE + LEN_WORKER_CODE;

	//#CM702037
	/**
	 * Offset of Work File Name
	 */
	private static final int OFF_WORK_FILE_NAME = OFF_RESULT_SEND_ID + LEN_ID;
	
	//#CM702038
	/**
	 * Offset of Row No
	 */
	private static final int OFF_LINE_NO = OFF_WORK_FILE_NAME + LEN_WORK_FILE_NAME;

	//#CM702039
	/**
	 * Offset of Data size
	 */
	private static final int OFF_DATA_SIZE = OFF_LINE_NO + LEN_LINE_NO;
		
	//#CM702040
	/**
	 * Offset of Content Data
	 */
	private static final int OFF_DATA_CONTENTS = OFF_DATA_SIZE + LEN_DATA_SIZE;
	
	//#CM702041
	/**
	 * ID Number
	 */
	public static final String ID = "0006";
		
	
	//#CM702042
	// Class variables ----------------------------------------------
	
	//#CM702043
	// Class method ------------------------------------------------
	//#CM702044
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:12 $";
	}
	
	//#CM702045
	// Constructors -------------------------------------------------
	//#CM702046
	/**
	 * Generate the instance. 
	 */
	public RFTId0006 ()
	{
		super();

		offEtx = OFF_DATA_CONTENTS + LEN_DATA_CONTENTS;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM702047
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0006 Work start report ID = 0006 telegram
	 */
	public RFTId0006 (byte[] rftId0006)
	{
		this();

		setReceiveMessage(rftId0006);
	}
	
	//#CM702048
	// Public methods -----------------------------------------------
	//#CM702049
	/**
	 * Acquire Person in charge name from work Data report telegram. 
	 * @return   Person in charge name
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	
	//#CM702050
	/**
	 * Acquire Work file name from work Data report telegram. 
	 * @return   Work File Name
	 */
	public String getWorkFileName()
	{
		String workFileName = getFromBuffer(OFF_WORK_FILE_NAME , LEN_WORK_FILE_NAME);
		return workFileName.trim();
	}
	
	//#CM702051
	/**
	 * Acquire Row No from work Data report telegram. 
	 * @return   Row No
	 */
	public int getLineNo()
	{
		int lineNo = getIntFromBuffer(OFF_LINE_NO , LEN_LINE_NO);
		return lineNo;
	}
	
	//#CM702052
	/**
	 * Acquire Data size from work Data report telegram. 
	 * @return	   Data size
	 */
	public int getDataSize()
	{
		int dataSize = getIntFromBuffer(OFF_DATA_SIZE, LEN_DATA_SIZE);
		return dataSize;
	}
	
	//#CM702053
	/**
	 * Acquire Content Data from work Data report telegram. 
	 * @return    Content Data
	 */
	public byte[] getDataContents()
	{
		byte[] dataContents = new byte[getDataSize()];
		for (int i = 0; i < getDataSize(); i ++)
		{
			dataContents[i] = wDataBuffer[OFF_DATA_CONTENTS + i];
		}
		return dataContents;
	}
	
	//#CM702054
	// Package methods ----------------------------------------------
	
	//#CM702055
	// Protected methods --------------------------------------------
	
	//#CM702056
	// Private methods ----------------------------------------------
	
}
//#CM702057
//end of class

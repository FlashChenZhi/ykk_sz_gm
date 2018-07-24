// $Id: RFTId0010.java,v 1.2 2006/11/14 06:09:13 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702058
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702059
/**
 * Socket communication of  worker Inquiry Id=0010 Process telegram. 
 *
 * <p>
 * <table>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>Handy transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Server transmission time</td>	<td>6 byte</td></tr>
 * <tr><td>Handy title machine No</td>		<td>3 byte</td></tr>
 * <tr><td>Person in charge code</td>		<td>4 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:13 $
 * @author  $Author: suresh $
 */

 public class RFTId0010 extends RecvIdMessage
{
	//#CM702060
	// Class field --------------------------------------------------
	//#CM702061
	/*
	 * Offset of Person in charge code
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	//#CM702062
	// Class variables ----------------------------------------------
	
	//#CM702063
	// Class method
	//#CM702064
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $Date: 2006/11/14 06:09:13 $";
	}
	
	//#CM702065
	//Constructors --------------------------------------------------
	//#CM702066
	/**
	 * Generate the instance. 
	 */
	public RFTId0010 ()
	{
		super();

		offEtx = OFF_WORKER_CODE + LEN_WORKER_NAME;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	//#CM702067
	/**
	 * Pass Constructor telegram received from RFT. 
	 * @param rftId0010 Person in charge code input  Id = 0010 telegram
	 */
	public RFTId0010 (byte[] rftId0010)
	{
		this();

		setReceiveMessage(rftId0010);
	}

	//#CM702068
	// Public methods -----------------------------------------------
	//#CM702069
	/**
	 * Acquire Person in charge code from Person in charge code input telegram. 
	 * @return   Person in charge code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE , LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	//#CM702070
	/**
	 * Acquire the content of Person in charge code input telegram. 
	 * @return Content of Person in charge code input response TEXT
	 */
	public String toString()
	{
		return new String(wLocalBuffer);
	}
	
	//#CM702071
	// Package methods ----------------------------------------------
	
	//#CM702072
	// Protectedmethods ---------------------------------------------
	
	//#CM702073
	// Private methods ----------------------------------------------
	
}
//#CM702074
//end of class

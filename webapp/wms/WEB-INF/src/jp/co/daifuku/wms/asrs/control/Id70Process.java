// $Id: Id70Process.java,v 1.2 2006/10/26 02:20:32 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33526
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id70;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM33527
/**
 * This class processes the Message Data. It outputs the contents of received message in message logs.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:20:32 $
 * @author  $Author: suresh $
 */
public class Id70Process extends IdProcess
{
	//#CM33528
	// Class fields --------------------------------------------------
	//#CM33529
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;

	//#CM33530
	// Class variables -----------------------------------------------

	//#CM33531
	// Class method --------------------------------------------------
	//#CM33532
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:20:32 $") ;
	}

	//#CM33533
	// Constructors --------------------------------------------------
	//#CM33534
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id70Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}
	
	//#CM33535
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id70Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33536
	// Public methods ------------------------------------------------

	//#CM33537
	// Package methods -----------------------------------------------

	//#CM33538
	// Protected methods ---------------------------------------------
	//#CM33539
	/**
	 * Processes Message Data.
	 * Logging of Message Data in received text.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM33540
		// Generate the instance of As21Id70.
		As21Id70 id70dt = new As21Id70(rdt) ;
		
		//#CM33541
		// Logging of Message Data.
		Object[] tObj = new Object[1] ;
		tObj[0] = new String(id70dt.getMessageData()) ;
		RmiMsgLogClient.write(0, LogMessage.F_ERROR, "Id70Process", tObj);
	}
}
//#CM33542
//end of class


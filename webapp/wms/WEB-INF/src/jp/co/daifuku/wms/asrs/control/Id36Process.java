// $Id: Id36Process.java,v 1.2 2006/10/26 02:24:25 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33335
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id36;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM33336
/**
 * This class processes the system start-up unavailable report. It outputs the reasons why
 * it cannot start-up the system in message log.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:24:25 $
 * @author  $Author: suresh $
 */
public class Id36Process extends IdProcess
{
	//#CM33337
	// Class fields --------------------------------------------------
	//#CM33338
	/**
	 * No in work state.
	 */
	private static final String TRANS_CLASS_IN = "01" ;

	//#CM33339
	// Class variables -----------------------------------------------
	//#CM33340
	// AGCNo
	private int wAgcNumber ;

	//#CM33341
	// Class method --------------------------------------------------
	//#CM33342
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:24:25 $") ;
	}

	//#CM33343
	// Constructors --------------------------------------------------
	//#CM33344
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id36Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33345
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id36Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33346
	// Public methods ------------------------------------------------

	//#CM33347
	// Package methods -----------------------------------------------

	//#CM33348
	// Protected methods ---------------------------------------------
	//#CM33349
	/**
	 * Processest the system start-up unavailable report.
	 * Logging of the reason why the system could not start up the received text.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM33350
		// Generates the instance of As21Id30.
		As21Id36 id36dt = new As21Id36(rdt) ;

		//#CM33351
		// 6022034=Start-up failure report is received. SRC No.={0} Reason={1}
		Object[] tObj = new Object[2] ;
		tObj[0] = Integer.toString(wAgcNumber);
		tObj[1] = new String(id36dt.getReason()) ;
		RmiMsgLogClient.write(6022034, LogMessage.F_NOTICE, "Id36Process", tObj);
	}
}
//#CM33352
//end of class


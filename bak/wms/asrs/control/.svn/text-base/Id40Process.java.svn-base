// $Id: Id40Process.java,v 1.2 2006/10/26 02:23:40 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33369
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id40;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;

//#CM33370
/**
 * This class processes the transmission test request. It receives the Data and sends it as it is to AGC.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:23:40 $
 * @author  $Author: suresh $
 */
public class Id40Process extends IdProcess
{
	//#CM33371
	// Class variables -----------------------------------------------
	//#CM33372
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;

	//#CM33373
	// Class method --------------------------------------------------
	//#CM33374
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:23:40 $") ;
	}

	//#CM33375
	// Constructors --------------------------------------------------
	//#CM33376
	/**
	 * Default constructor
	 * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
	 */
	public Id40Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33377
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id40Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33378
	// Public methods ------------------------------------------------

	//#CM33379
	// Package methods -----------------------------------------------

	//#CM33380
	// Protected methods ---------------------------------------------
	//#CM33381
	/**
	 * Processes the communication message responding to the transmission test.
	 * It receives TestData and sends it to AGC as it is.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM33382
		// Generates the instance of As21Id40.
		As21Id40 id40dt = new As21Id40(rdt) ;
		
		//#CM33383
		// Sends the content of received TestData to AGC.
		SystemTextTransmission.id20send(wAgcNumber, id40dt.getTestData());
	}
}
//#CM33384
//end of class


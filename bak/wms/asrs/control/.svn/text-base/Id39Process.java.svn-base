// $Id: Id39Process.java,v 1.2 2006/10/26 02:24:08 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33353
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id39;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;

//#CM33354
/**
 * This class processes the response to the transmission test.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:24:08 $
 * @author  $Author: suresh $
 */
public class Id39Process extends IdProcess
{
	//#CM33355
	// Class variables -----------------------------------------------
	//#CM33356
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;

	//#CM33357
	// Class method --------------------------------------------------
	//#CM33358
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:24:08 $") ;
	}

	//#CM33359
	// Constructors --------------------------------------------------
	//#CM33360
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id39Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33361
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id39Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33362
	// Public methods ------------------------------------------------

	//#CM33363
	// Package methods -----------------------------------------------
	
	//#CM33364
	// Protected methods ---------------------------------------------
	//#CM33365
	/**
	 * Processes the communication message respondng to the transmission test.
	 * Inidicates the Test Data part of the received text at standard output.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM33366
		// Generates instance of As21Id39.
		As21Id39 id39dt = new As21Id39(rdt) ;

		//#CM33367
		//  Indicates calue of text received.
		System.out.println(id39dt.getTestData());
	}
}
//#CM33368
//end of class


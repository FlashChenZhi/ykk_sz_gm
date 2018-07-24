// $Id: Id66Process.java,v 1.2 2006/10/26 02:21:34 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33487
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id66;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;

//#CM33488
/**
 * This class processes the retrieval trigger report. In standard, the process is not built in.
 * If this text is valid, please implement the process for each job number.
 * <br>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:21:34 $
 * @author  $Author: suresh $
 */
public class Id66Process extends IdProcess
{
	//#CM33489
	// Class variables -----------------------------------------------
	//#CM33490
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;

	//#CM33491
	// Class method --------------------------------------------------
	//#CM33492
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:21:34 $") ;
	}

	//#CM33493
	// Constructors --------------------------------------------------
	//#CM33494
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER with AGCNo.
     */
	public Id66Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33495
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id66Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33496
	// Public methods ------------------------------------------------

	//#CM33497
	// Package methods -----------------------------------------------
	
	//#CM33498
	// Protected methods ---------------------------------------------
	//#CM33499
	/**
	 * Processes the communication message of retrieval trigger report.
	 * For actual processing, please implement by each job number.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM33500
		// Generates the instance of As21Id66.
		As21Id66 id66dt = new As21Id66(rdt) ;

		//#CM33501
		//  Indicate the values of received text.
		System.out.println(id66dt.getStationNo());
	}
}
//#CM33502
//end of class


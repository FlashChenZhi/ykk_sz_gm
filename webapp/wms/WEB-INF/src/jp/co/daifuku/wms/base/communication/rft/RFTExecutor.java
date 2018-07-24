// $Id: RFTExecutor.java,v 1.2 2006/11/07 06:46:49 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM644014
/*
 * Copyright 2002-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.LogMessage;

//#CM644015
/**
 * Class which starts communication module
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:46:49 $
 * @author  $Author: suresh $
 */
public class RFTExecutor
{
	//#CM644016
	// Class fields --------------------------------------------------
	//#CM644017
	/**
	 * Waiting time until starting the next processing when communication processing with RFT is started (msec). 
	 */
	private static final int WMS_EXEC_TIME = 500;

	//#CM644018
	// Class variables -----------------------------------------------
	//#CM644019
	// Class method --------------------------------------------------
	//#CM644020
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:46:49 $");
	}

	//#CM644021
	// Constructors --------------------------------------------------
	//#CM644022
	// Public methods ------------------------------------------------
	//#CM644023
	/**
	 * Start the communication module. 
	 * @param	args	(Unused)
	 */
	public static void main(String[] args)
	{
		try
		{
			//#CM644024
			// Start living confirmation thread (KeepAlive). 
			KeepAlive ke = new KeepAlive();
			ke.start();
			
			//#CM644025
			// Acquisition of instance of Utility class for connection with RFT
			//#CM644026
			// Thread making & start for connected watch
			RftWatcher rftwach = new RftWatcher();
			rftwach.start();

			Thread.sleep(WMS_EXEC_TIME);
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(
				6026011,
				LogMessage.F_ERROR,
				"RftExecutor",
				e);
		}
	}

	//#CM644027
	// Package methods -----------------------------------------------
	//#CM644028
	// Protected methods ---------------------------------------------
	//#CM644029
	// Private methods -----------------------------------------------
}
//#CM644030
//end of class

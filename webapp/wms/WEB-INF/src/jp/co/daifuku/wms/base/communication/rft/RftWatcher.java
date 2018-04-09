// $Id: RftWatcher.java,v 1.2 2006/11/07 06:35:31 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM644048
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.net.ServerSocket;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.rft.SystemParameter;

//#CM644049
/**
 * Both are stopped and it enters the state of accept with RFT newly when two tasks of sending and receiving  it to 
 * RFT are observed in start &, and abnormality occurs to either. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>miya</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:35:31 $
 * @author  $Author: suresh $
 */
public class RftWatcher extends Thread
{
	//#CM644050
	// Class fields --------------------------------------------------
	//#CM644051
	/**
	 * Define RFT server port (Default). 
	 */
	public static final int DEFAULT_PORT = 2500;
	//#CM644052
	/**
	 * Define RFT server port (Default). 
	 */
	public static final int DEFAULT_RFT_MAX = 64;

	//#CM644053
	// Class variables -----------------------------------------------
	//#CM644054
	/**
	* Variable which maintains reference to RFT sending and receiving processing instance. 
	*/
	private RftCommunicationControl RftCommunicationControlInstance = null;
	//#CM644055
	/**
	* Port  No. of connection
	*/
	private int wPort ;
	//#CM644056
	/**
	 * End request check of RFTWatcher
	 */
	private static boolean RftWatcherInstance;
	//#CM644057
	/**
	 * DBConnection etc. of all title machine are maintained. 
	 */
	private ClientTerminal clientTerminal;

	//#CM644058
	// Class method --------------------------------------------------
	//#CM644059
	/**
	* Return the version of this class. 
	* @return Version and date
	*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:35:31 $");
	}

	//#CM644060
	// Constructors --------------------------------------------------
	//#CM644061
	/**
	 * Generate the instance. 
	 */
	public RftWatcher()
	{
		this(DEFAULT_PORT);
		RftWatcherInstance = true;
	}

	//#CM644062
	/**
	 * Generate the instance. 
	 * @param port  Connection Port  No.
	 */
	public RftWatcher(int port)
	{
		wPort = port;
		RftWatcherInstance = true;
	}

	//#CM644063
	// Public methods ------------------------------------------------
	//#CM644064
	/**
	 * Execute processing. 
	 */
	public void run()
	{
		try
		{
			//#CM644065
			// Communication port acquisition
			wPort = WmsParam.RFT_SERVER_PORT;
			if (wPort < 0)
			{
				wPort = DEFAULT_PORT;
			}

			//#CM644066
			// Do not erase this console output. 
			System.out.println("***************************** " );
			System.out.println("RFT Server Port No. = " + wPort);

			//#CM644067
			// Set Connection for the system parameter class. 
			SystemParameter.setConnection(WmsParam.getConnection());

			//#CM644068
			// Initialize the package manager. 
			PackageManager.initialize(WmsParam.getConnection());
			
			//#CM644069
			//Information class instance making concerning client terminal
			clientTerminal = new ClientTerminal();
			
			//#CM644070
			// Server socket instance making
			ServerSocket Serverinstance = new ServerSocket(wPort);
			
			//#CM644071
			// Take Sleep Time from the resource file. 
			int wSleepTime = WmsParam.RFT_SLEEP_SEC;
			System.out.println("***************************** " );

			RftWatcherInstance = true;
			while (RftWatcherInstance)
			{
				//#CM644072
				// Do not erase this console output. 
				System.out.println("* RftWatcher Start !!");
				try
				{
					//#CM644073
					// Connected waiting from RFT  (client)
					CommunicationRft wServerCommunication = new CommunicationRft();
					wServerCommunication.connect(Serverinstance);
					//#CM644074
					// Do not erase this console output. 
					System.out.println(">>> Connect [RftWatcher] ");

					//#CM644075
					// Start of RFT sending and receiving management thread
					//#CM644076
					// Acquisition of instance of Utility class for connection with RFT
					RftCommunicationControlInstance =
						new RftCommunicationControl(
							wServerCommunication,
							clientTerminal);
					RftCommunicationControlInstance.start();
					Thread.sleep(wSleepTime);
					continue;
				}
				catch (InterruptedException e)
				{
					//#CM644077
					// Do not erase this console output. 
					System.out.println("InterruptedException" + e);
					//#CM644078
					// Drop the error to the log file. 
					RftLogMessage.printStackTrace(
						6026012,
						LogMessage.F_ERROR,
						"RftWatcher",
						e);
					break;
				}
				catch (Exception e)
				{
					//#CM644079
					// Drop the error to the log file. 
					RftLogMessage.printStackTrace(
						6026012,
						LogMessage.F_ERROR,
						"RftWatcher",
						e);
				}
			}
			//#CM644080
			// Close the Connection
			clientTerminal.closeConnections();
		}
		catch (Exception e)
		{
			//#CM644081
			// Drop the error to the log file. 
			RftLogMessage.printStackTrace(
				6026012,
				LogMessage.F_ERROR,
				"RftWatcher",
				e);
			//#CM644082
			// Do not erase this console output. 
			System.out.println("RFTWatecher DBConn close ");
			//#CM644083
			// Close the Connection
			clientTerminal.closeConnections();
		}
	}
	
	//#CM644084
	/**
	 * End Processing
	 */
	public void finishRequest()
	{

		RftWatcherInstance = false;
	}

	//#CM644085
	// Package methods -----------------------------------------------
	//#CM644086
	// Protected methods ---------------------------------------------
	//#CM644087
	// Private methods -----------------------------------------------
}
//#CM644088
//end of class

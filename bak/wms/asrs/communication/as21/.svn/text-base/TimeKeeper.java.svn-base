// $Id: TimeKeeper.java,v 1.2 2006/10/24 08:25:02 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31975
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;

//#CM31976
/**
 * This class adjust the interval of data checking of carry instruction and retrieval instruction.<BR>
 * It requests to perform the data-reading check periodicaly with carry instructions, retrieval instructions
 * and automatic mode switching retrieval instructions.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 08:25:02 $
 * @author  $Author: suresh $
 */
public class TimeKeeper extends RmiServAbstImpl implements java.lang.Runnable
{
	//#CM31977
	// Class fields --------------------------------------------------
	//#CM31978
	/**
	 * Object to bind to the remote object
	 */
	public static final String OBJECT_NAME = "TimeKeeper" ;

	//#CM31979
	// Class variables -----------------------------------------------
	//#CM31980
	/**
	 * Value of time keeping
	 */
	protected int wSleepTime = 1000 ;

	//#CM31981
	/**
	 * reference to the instance of carry instruction
	 */
	protected StorageSender wStorageSender;

	//#CM31982
	/**
	 * Reference to the instance of retrieval instruction
	 */
	protected RetrievalSender wRetrievalSender;

	//#CM31983
	/**
	 * Reference to the instance of automatic mode switching retrieval instructions
	 */
	protected AutomaticModeChangeSender wAutomaticModeChangeSender;

	//#CM31984
	/**
	 * This flag determines whether/not to terminate the TimeKeeper class.
	 * When ExitFlag changed to true, it pulls out of the infinite loop of run() method.
	 * In order to update this flag, stop() method should be used.
	 */
	private boolean wExitFlag = false;

	//#CM31985
	// Class method --------------------------------------------------
 	//#CM31986
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 08:25:02 $") ;
	}

	//#CM31987
	// Constructors --------------------------------------------------
	//#CM31988
	/**
	 * reference to the instance of carry instruction
	 */
	//#CM31989
	/**
	 * Create new instance of <code>TimeKeeper</code>.
	 * Request the carry instruction/retrieval instruction specifying the interval(ms) to read
	 * <code>CarryInformation</code>
	 * @param sleepTime : interval at which <code>CarryInformation</code> is read
	 * @param sSend     : instance of carry instruction
	 * @param rSend     : instance of retrieval instruction
	 * @param srSend    : instance of automatic mode switching retrieval instructions
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public TimeKeeper(int sleepTime, StorageSender sSend, RetrievalSender rSend, AutomaticModeChangeSender srSend) 
																							throws RemoteException
	{
		wSleepTime = sleepTime;
		wStorageSender = sSend;
		wRetrievalSender = rSend;
		wAutomaticModeChangeSender = srSend;
	}

	//#CM31990
	/**
	 * Create new instance of <code>TimeKeeper</code>.
	 * Request the carry instruction/retrieval instruction with default interval (1000ms) to read
	 * <code>CarryInformation</code>
	 * @param sSend     : instance of carry instruction
	 * @param rSend     : instance of retrieval instruction
	 * @param srSend    : instance of automatic mode switching retrieval instructions
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public TimeKeeper(StorageSender sSend, RetrievalSender rSend, AutomaticModeChangeSender srSend) 
																							throws RemoteException
	{
		wStorageSender = sSend;
		wRetrievalSender = rSend;
		wAutomaticModeChangeSender = srSend;
	}

	//#CM31991
	// Public methods ------------------------------------------------
	//#CM31992
	/**
	 * Check the specified time and request the carry isntruction/retrieval instruction
	 */
	public void run()
	{
		try
		{
			//#CM31993
			// Keep repeating the following action 
			while(wExitFlag == false)
			{
				try
				{
					//#CM31994
					// Terminate if already registered in RMI Server (avoiding multiple start-ups)
					if (this.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME ))
					{
						return;
					}
					//#CM31995
					// Register this class to the RMI Server
					this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);

					while (wExitFlag == false)
					{
						Thread.sleep(wSleepTime);

						//#CM31996
						// Request the carry instruction/retrieval instruction.
						wStorageSender.wakeup();
						wRetrievalSender.wakeup();
						wAutomaticModeChangeSender.wakeup();
					}
				}
				catch (Exception e)
				{
					StringWriter sw = new StringWriter();
					PrintWriter  pw = new PrintWriter(sw);
					String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
					e.printStackTrace(pw);
					//#CM31997
					// 6026043=Error occurred in transfer instruction task. Exception:{0}
					Object[] tobj = new Object[1];
					tobj[0] = stcomment + sw.toString();
					RmiMsgLogClient.write(6026043, LogMessage.F_ERROR, "TimeKeeper", tobj);
					Thread.sleep(3000);
				}
			}
		}
		catch(Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			e.printStackTrace(pw);
			//#CM31998
			// 6026043=Error occurred in transfer instruction task. Exception:{0}
			Object[] tobj = new Object[1];
			tobj[0] = stcomment + sw.toString();
			RmiMsgLogClient.write(6026043, LogMessage.F_ERROR, "TimeKeeper", tobj);
		}
		finally
		{
System.out.println("TimeKeeper:::::finally");
			System.exit(0);
		}
	}

	//#CM31999
	/**
	 * This method is unused. This is prepared to virtually implement declared in upperclass.
	 * @param params : unused.
	 */
	public synchronized void write(Object[] params) 
	{
	}

	//#CM32000
	/**
	 * Terminating the process.
	 * Process terminates if this method is called by external.
	 */
	public synchronized void stop()
	{
		//#CM32001
		// release this thread from the wait state.
		this.notify();
		//#CM32002
		// update the flag so that the thread stops looping.
		wExitFlag = true;
	}

	//#CM32003
	// Package methods -----------------------------------------------

	//#CM32004
	// Protected methods ---------------------------------------------

	//#CM32005
	// Private methods -----------------------------------------------

}
//#CM32006
//end of class


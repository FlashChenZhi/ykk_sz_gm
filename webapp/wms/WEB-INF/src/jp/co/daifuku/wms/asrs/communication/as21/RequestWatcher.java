// $Id: RequestWatcher.java,v 1.2 2006/10/26 00:55:32 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31395
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;

//#CM31396
/**
 * If the work mode switch completion report of the station is expected but has not been received
 * within certain time, this class changes the status as follows<BR>
 * Mode switch request : No request of swicthing mode<BR>
 * Start time for Mode switch : null<BR>
 * This class starts the process.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/09/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 00:55:32 $
 * @author  $Author: suresh $
 */
public class RequestWatcher extends RmiServAbstImpl implements Runnable
{
	//#CM31397
	// Class fields --------------------------------------------------
	//#CM31398
	/**
	 * Object to bind to the remote object
	 */
	public static final String OBJECT_NAME = "RequestWatcher" ;

	//#CM31399
	/**
	 * Start-up mode. If starting on debug mode, set constructor with <CODE>true</CODE>.
	 */
	//#CM31400
	// Default normal mode
	private static boolean wMode = false ; 

	//#CM31401
	// Class variables -----------------------------------------------
	//#CM31402
	/**
	 * The interval between the time the work mode switch request was sent and the completion is received.
	 * If the time prolongs over this time, consider that the request had not been made.
	 */
	//#CM31403
	// Wait for the reply for default 3 minutes
	protected int wWaitTime = 180000 ;

	//#CM31404
	/**
	 * Thread idle time
	 */
	//#CM31405
	// Default 10 seconds
	protected int wSleepTime = 10000 ;

	//#CM31406
	/**
	 * This flag determines whether/not to terminate the RequestWatcher class.
	 * If ExitFlag changes to true, it pulls out of the infinite loop of run() method.
	 * This stop() method must be used to update this flag.
	 */
	private boolean wExitFlag = false;

	//#CM31407
	// Class method --------------------------------------------------
	//#CM31408
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 00:55:32 $") ;
	}

	//#CM31409
	/**
	 * Output to the prompt display.
	 * @param str String to show on  the prompt display
	 * @param system Even if started up on normal mode, and if recording the log, set <CODE>true</CODE>.
	 */
	private static void println(String str, boolean system)
	{
		if (wMode || system)
		{
			System.out.println(str);
		}
	}

	//#CM31410
	// Constructors --------------------------------------------------
	//#CM31411
	/**
	 * Constructs a new instance of <code>RequestWatcher</code> using the default time of monitoring.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public RequestWatcher() throws RemoteException 
	{
	}

	//#CM31412
	/**
	 * Constructs a new instance of <code>RequestWatcher</code>.
	 * @param waittime Interval in monitoring: from sending of work mode switch request to receiving  the reply.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public RequestWatcher(int waittime) throws RemoteException 
	{
		wWaitTime = waittime;
	}

	//#CM31413
	/**
	 * Constructs a new instance of <code>RequestWatcher</code>.
	 * @param waittime Interval in monitoring: from sending of work mode switch request to receiving of reply.
	 * @param mode :designate the start-up mode. Set <CODE>true</CODE> if starting by debug mode.
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public RequestWatcher(int waittime, boolean mode) throws RemoteException 
	{
		wWaitTime = waittime;
		wMode     = mode;
	}

	//#CM31414
	// Public methods ------------------------------------------------
	//#CM31415
	/**
	 * Starts <CODE>RequestWatcher</CODE> using thread.
	 */
	public static void main(String[] args)
	{
		RequestWatcher reqwatch = null;
		try
		{
			//#CM31416
			// Usage
			if (args != null && args.length == 1 && (args[0].equals("-?") || args[0].equals("-help")))
			{
				println("example of the usage  j jp.co.daifuku.asrs.communication.as21.RequestWatcher 180000 true", true);
				println("Sets 1st argument to be monitor time, 2nd argument to be start mode, (true:debug mode)", true);
				System.exit(0);
			}
			else if (args != null && args.length == 2)
			{
				reqwatch = new RequestWatcher(Integer.parseInt(args[0]), new Boolean(args[1]).booleanValue());
			}
			else
			{
				reqwatch = new RequestWatcher();
			}

			new Thread(reqwatch).start();
		}
		catch (Exception e)
		{
			println("\n Cannot start up." + e.toString() + "\n", true);
			if (reqwatch != null)
			{
				//#CM31417
				// 6026064=Exception is notified. Abnormal ending. Task={0}
				Object[] tobj = {"RequestWatcher"};
				reqwatch.logging(6026064, tobj);
				println("Due to the exceptions, it abnormally ends." + new java.util.Date(), true);
				e.printStackTrace();
			}
		}
	}

	//#CM31418
	/**
	 * If the completion report to the work mode switch request will not be received within a certain time,
	 * mode switch request: no request has been made<BR>
	 * Start time of mode switch: null<BR>
	 */
	public void run()
	{
		Connection conn = null;
		int ret  = 0;
		try
		{
			//#CM31419
			// Repeat the following
			while(wExitFlag == false)
			{
				try
				{
					if (conn == null)
					{
						conn    = AsrsParam.getConnection();
					}
					//#CM31420
					// terminate if already registered to RMI servefr Server.(avoiding the multiple start-ups)
					if (this.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME ))
					{
						return;
					}
					//#CM31421
					// Register in the registo
					this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME);

					//#CM31422
					// Start up the request monitoring process.
					RmiMsgLogClient.write(6020020, "RequestWatcher");
					println(this.getClass().getName() + " Started !!  Start up the process of monitoring response to work mode switching.", true);

					while (wExitFlag == false)
					{
						Thread.sleep(wSleepTime);

						//#CM31423
						// Conduct seacrh over all stations to find any station that requesting the work mode change.
						//#CM31424
						// If there are any, update the status: switch request to no request at all.
						StationHandler handle  = new StationHandler(conn);
						Station[]      station = (Station[])handle.find(new StationSearchKey());
						for (int i = 0; i < station.length; i++)
						{
							if (station[i].getModeRequest() != Station.NO_REQUEST)
							{
								if (station[i].getModeRequestTime() != null)
								{
									long interval = System.currentTimeMillis() - station[i].getModeRequestTime().getTime();
									println("station:" + station[i].getStationNumber() + " time passed after the request has sent = " + interval + "ms", false);
									if (interval > wWaitTime)
									{
										println("station:" + station[i].getStationNumber() + " Cancelled the request for work mode of switchingis", false);
										//#CM31425
										// Update
										StationHandler shandler = new StationHandler(conn);
										StationAlterKey alterkey = new StationAlterKey();
										alterkey.KeyClear();
										alterkey.setStationNumber(station[i].getStationNumber());
										alterkey.updateModeRequest(Station.NO_REQUEST);
										alterkey.updateModeRequestTime(null);
										shandler.modify(alterkey);
										conn.commit();
										//#CM31426
										// 6024027=Request was canceled. Work mode switch completion was not reported within {0} seconds.
										Object[] tobj = {Integer.toString(wWaitTime / 1000)};
										logging(6024027, tobj);
									}
								}
							}
						}
					}
				}
				catch(Exception e)
				{
					//#CM31427
					// 6016102=A fatal error occurred. {0}
					logging(6016102, e);
					Thread.sleep(3000);
				}
				finally
				{
					try
					{
						if (conn != null)
						{
							conn.rollback();
						}
					}
					catch (SQLException e)
					{
						//#CM31428
						// 6007030=Database error occured. error code={0}
						Object[] tobj = {Integer.toString(e.getErrorCode())};
						logging(6007030, tobj);
						conn = null;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ret = 1;
		}
		finally
		{
System.out.println("RequestWatcher::::finally!!");
			//#CM31429
			// 6020021=Terminating request monitoring.
			RmiMsgLogClient.write(6020021, "RequestWatcher");
			try
			{
				this.unbind();
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
				if (ret > 0)
				{
					//#CM31430
					// 6026064=Exception is notified. Abnormal ending. Task={0}
					Object[] tobj = {"RequestWatcher"};
					logging(6026064, tobj);
					println("As the exception has been notified, it is abnormally ending." + new java.util.Date(), true);
				}
			}
			catch (SQLException sqle)
			{
				//#CM31431
				// 6007030=Database error occured. error code={0}
				Object[] tobj = {Integer.toString(sqle.getErrorCode())};
				logging(6007030, tobj);
				ret = 1;
			}
			finally
			{
				System.exit(ret);
			}
		}
	}

	//#CM31432
	/**
	 * This method is unused. This is prepared only to virtually implement the 
	 * method declared upperclass.
	 * @param params Unused
	 */
	public synchronized void write(Object[] params) 
	{
	}

	//#CM31433
	/**
	 * Terminating the process.
	 * It terminates the process when it is called exrternally.
	 */
	public synchronized void stop()
	{
		//#CM31434
		// Update the flag so taht loop of the thread terminates.
		wExitFlag = true;
	}

	//#CM31435
	// Package methods -----------------------------------------------

	//#CM31436
	// Protected methods ---------------------------------------------

	//#CM31437
	// Private methods -----------------------------------------------
	//#CM31438
	/**
	 * Outputting to the log file.
	 * @param msgnum Message no.
	 * @param tobj Parameter to write in log file
	 */
	private void logging(int msgnum, Object[] tobj)
	{
		RmiMsgLogClient.write(msgnum, "RequestWatcher", tobj);
	}

	//#CM31439
	/**
	 * Outputting to the log file.
	 * @param msgnum Message no.
	 * @param e Exception notified
	 */
	private void logging(int msgnum, Exception e)
	{
		Object[] tobj = {TraceHandler.getStackTrace(e)};
		RmiMsgLogClient.write(msgnum, "RequestWatcher", tobj);
	}

	//#CM31440
	/**
	 * Outputting to the log file.
	 * @param msgnum Message no.
	 */
	private void logging(int msgnum)
	{
		RmiMsgLogClient.write(msgnum, "RequestWatcher");
	}
}
//#CM31441
//end of class

// $Id: As21Watcher.java,v 1.2 2006/10/26 01:10:30 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30533
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.text.DecimalFormat;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.rmi.RmiServAbstImpl;

//#CM30534
/**
 * This class starts-up & monitors 2 tasks (sending and receiving with AGC). In case the error occurs, it
 * terminates both processes and regains new connection with AGC. Then again, sending  & receiving tasks will
 * be started.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:10:30 $
 * @author  $Author: suresh $
 */
public class As21Watcher extends RmiServAbstImpl implements java.lang.Runnable
{
	//#CM30535
	// Class fields --------------------------------------------------
	//#CM30536
	/**
	 * Object name which bind to the remote object
	 */
	public static final String OBJECT_NAME = "As21Watcher" ;

	//#CM30537
	// Class variables -----------------------------------------------
	 protected static DecimalFormat fmt = new DecimalFormat("00");

	//#CM30538
	/**
	* Variablese that preserves reference to the Utility class for the connection with AGC.
	*/
	private CommunicationAgc wAgc = null;

	//#CM30539
	/**
	* Variables which preserves AGC numbers
	*/
	private int wAgcNumber = 0;

	//#CM30540
	/**
	* Variables that preserves reference to the instance of receiving process
	*/
	private As21Receiver as21ReceiverInstance = null ;

	//#CM30541
	/**
	* Variables that preserves reference to the instance of sending process
	*/
	private As21Sender as21SenderInstance = null ;

	//#CM30542
	/**
	* Host of the AGC connecting
	*/
	private String wHost = "" ;

	//#CM30543
	/**
	* Port number of the AGC connected to
	*/
	private int wPort = 2000 ;

	//#CM30544
	/**
	* Flag which prevents the outputs of log.
	*/
	private boolean wLoggingFlag = true ;

	//#CM30545
	/**
	 * This flag determines whether/not to end As21Watcher class.
	 * If Exit Flag indicates true, it pulls out of the infinite loop of run() method.
	 * In order to update this flag, stop() method should be used.
	 */
	private boolean wExitFlag = false;

	//#CM30546
	// Class method --------------------------------------------------
 	//#CM30547
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:10:30 $") ;
	}

	//#CM30548
	// Constructors --------------------------------------------------
	//#CM30549
	/**
	 * constructor
	 * @param host      Host of the AGC connecting
	 * @param port      Port number of the AGC connected to
	 * @param agcNumber AGC numbers
	 * @throws RemoteException  Exception related to communication generated while executing remote method call
	 */
	public As21Watcher( String host, int port, int agcNumber ) throws RemoteException 
	{
		wHost = host;
		wPort = port;
		wAgcNumber = agcNumber;
		wExitFlag = false;
	}

	//#CM30550
	// Public methods ------------------------------------------------
	//#CM30551
	/**
	 * Clear reference to the instance of receiving process in order to determine that processing thread for receiving messages has terinated.
	 * This process is to be manipulated by the thread which has been started.
	 */
	public void setAs21ReceiverInstance()
	{
		as21ReceiverInstance = null;
	}

	//#CM30552
	/**
	 * Clear reference to the instance of sending process in order to determine that processing thread for sending messages has terinated.
	 * This process is to be manipulated by the thread which has been started.
	 */
	public void setAs21SenderInstance()
	{
		as21SenderInstance = null;
	}

	//#CM30553
	/**
	 * Permits that log will be recorded.
	 * This flag is to be manipulated by the thread which has been started.
	 */
	public void setLoggingON()
	{
		wLoggingFlag = true;
	}

	//#CM30554
	/**
	 * Not allow to record logs.
	 * This flag is to be manipulated by the thread which has been started.
	 */
	public void setLoggingOFF()
	{
		wLoggingFlag = false;
	}

	//#CM30555
	/**
	 * Returns flag whether/not the log should be recorded
	 */
	public boolean getLoggingFlag()
	{
		return wLoggingFlag;
	}

	//#CM30556
	/**
	 * Starts-up the text send & receive processing.
	 * Process of sending text can be implemented by the instantiation of As21Sender class, then by calling remote method by RMI.
	 * Process of receiving text can be implemented by instantiation of As21Receiver class, then by executing as a thread.
	 */
	public void run()
	{
		try
		{
			while(wExitFlag == false)
			{
				try
				{
					//#CM30557
					// If it has already been registered in RMI Server, terminates. (avoiding the multiple start-ups)
					if (this.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME + fmt.format( wAgcNumber )))
					{
						return;
					}
					//#CM30558
					// Registers As21Watcher in RMI Server.
					this.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + OBJECT_NAME + fmt.format( wAgcNumber ));

					while(wExitFlag == false)
					{
						try
						{
							//#CM30559
							// Connection with AGC 
							CommunicationAgc wAgc = new CommunicationAgc( wHost, wPort );

							//#CM30560
							// Starts up the receiving thread
							if( as21ReceiverInstance == null ){
							//#CM30561
							// Gets the instance of Utility class by which connection with AGC to be established.
								as21ReceiverInstance = new As21Receiver(wAgc, this, wAgcNumber);
								as21ReceiverInstance.start();
							}
							//#CM30562
							// Sending thread only creates the instance for RMI and records in registry.
							if( as21SenderInstance == null ){
								as21SenderInstance = new As21Sender(wAgc);
								//#CM30563
								// Does not record if already registered in RMI Server.
								if (!as21SenderInstance.isbind("//" + RmiSendClient.RMI_REG_SERVER + "/" + As21Sender.OBJECT_NAME + fmt.format( wAgcNumber )))
								{
									as21SenderInstance.bind("//" + RmiSendClient.RMI_REG_SERVER + "/" + As21Sender.OBJECT_NAME + fmt.format( wAgcNumber ));
								}
							}
							if (wLoggingFlag)
							{
								//#CM30564
								// Start
								//#CM30565
								// 6020011=Starting AS21 communication process. SRC NO={0}
								String msg = "6020011" + MessageResource.DELIM + (wAgcNumber);
								RmiMsgLogClient.write(msg, "As21Watcher");
							}
							synchronized ( this )
							{
								wait();
							}
						}
						catch (Exception e)
						{
							if (wLoggingFlag)
							{
								StringWriter sw = new StringWriter();
			    				PrintWriter  pw = new PrintWriter(sw);
								String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
								//#CM30566
								//Records errors in the log file.
								//#CM30567
								// 6026040=Error occurred in the monitoring task of communication module.
								e.printStackTrace(pw);
								Object[] tobj = new Object[1];
								tobj[0] = stcomment + sw.toString();
								RmiMsgLogClient.write(6026040, LogMessage.F_ERROR, "As21Watcher", tobj);
								setLoggingOFF();
							}
						}
						finally
						{
							//#CM30568
							// Orders sending thread to terminate processing.CarryInformationController fails to call RMI.
							as21SenderInstance.unbind();
							//#CM30569
							// Sets the termination of sending thread.
							// Reference for theiInitialization of receiving thread was cleared; as this.setAs21ReceiverInstance()
							// had been called on As21Receiver side.
							//#CM30570
							//CMENJP1341$CM In the receive thread, at the As21Receiver side, this.setAs21ReceiverInstance() call reference is cleared.
						setAs21SenderInstance();
							//#CM30571
							// Initialize the connection with AGC.
							if(wAgc != null) wAgc.disconnect();
							wAgc = null;
							//#CM30572
							// Retrieves the Sleep Time from the resource file.
							int wSleepTime = AsrsParam.AS21_SLEEP_SEC;
							//#CM30573
							// Sleep Time before the recovery of connection in case the line error occured during AS21 communication
							// processing.
							Thread.sleep(wSleepTime);
						}
					}
				}
				catch(Exception e)
				{
					System.out.println("rmiregistry has not started!!");
					Thread.sleep(3000);
				}
			}
		} 
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
			PrintWriter  pw = new PrintWriter(sw);
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM30574
			//Records errors in log file.
			//#CM30575
			// 6026040=Error occurred in the monitoring task of communication module.
			e.printStackTrace(pw);
			Object[] tobj = new Object[1];
			tobj[0] = stcomment + sw.toString();
			RmiMsgLogClient.write(6026040, LogMessage.F_ERROR, "As21Watcher", tobj);
		}
		finally
		{
			//#CM30576
			// 6020013=Terminating communication monitoring process.
			RmiMsgLogClient.write(6020013, "As21Watcher");
			this.unbind();
			System.out.println("AGC" + wAgcNumber + " Stopped");
			System.exit(0);
		}

	}

	//#CM30577
	/**
	 * This method is unused. It is prepared only for the tentative implementation of methods
	 * which have been declared by upper class.
	 * @param params Unused.
	 */
	public synchronized void write(Object[] params) 
	{
	}

	//#CM30578
	/**
	 * Terminates the As21 communications.
	 * If this method is called externally, it terminates the communication process.
	 * @throws IOException            : Notifies if file I/O error occured.
	 */
	public synchronized void stop() throws IOException
	{
		//#CM30579
		// Updates the flag so that thread loop should terminate.
		wExitFlag = true;

		//#CM30580
		// Terminates the AS21 receiving thread.
		if (as21ReceiverInstance != null)
		{
			as21ReceiverInstance.setStop();
			wAgc = as21ReceiverInstance.getCommunicationAgc();
			as21ReceiverInstance.interrupt();
		}

		//#CM30581
		// Cuts the connection with AGC
		if (wAgc != null && wAgc.isConnected()) wAgc.disconnect();

		//#CM30582
		// 6020012=Terminating AS21 communication process. SRC NO={0}
		String msg = "6020012" + MessageResource.DELIM + (wAgcNumber);
		RmiMsgLogClient.write(msg, "As21Watcher");

		//#CM30583
		// Releases this thread from wait state.
		this.notify();
	}

	//#CM30584
	/**
	 * Activates the thread in wait state.
	 */
	public synchronized void wakeup() 
	{
		this.notify();
	}

	//#CM30585
	// Package methods -----------------------------------------------

	//#CM30586
	// Protected methods ---------------------------------------------

	//#CM30587
	// Private methods -----------------------------------------------

}
//#CM30588
//end of class

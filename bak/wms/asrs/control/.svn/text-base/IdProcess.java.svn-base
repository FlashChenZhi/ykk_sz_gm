// $Id: IdProcess.java,v 1.2 2006/10/26 02:19:33 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33569
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.common.ByteArray;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

//#CM33570
/**
 * This is the superclass to process data receivied from subcontroller.
 * IT is requried that the process of each ID should be described in <code>run()</code> method.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:19:33 $
 * @author  $Author: suresh $
 */
public abstract class IdProcess extends Thread
{
	//#CM33571
	// Class fields --------------------------------------------------
	//#CM33572
	/**
	 * Default wait time (10 seconds)
	 */
	public static final int SLEEP_DEFAULT = 10 ;

	//#CM33573
	// Class variables -----------------------------------------------
	//#CM33574
	/**
	 * Preserves the database connection.
	 */
	protected Connection wConn ;

	//#CM33575
	/**
	 * Variable to cache the communication message received.
	 */
	private Vector wReceiveBufferVect = new Vector(10) ;

	//#CM33576
	/**
	 * Variable which preserves whether/not the termination has been instructed.
	 */
	protected boolean wFinish = false ;

	//#CM33577
	/**
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 * 	ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM33578
	// Class method --------------------------------------------------
	//#CM33579
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:19:33 $") ;
	}

	//#CM33580
	// Constructors --------------------------------------------------

	//#CM33581
	// Public methods ------------------------------------------------
	//#CM33582
	/**
	 * Set the communication message received from subcontroller, then start up the process.
	 * @param ba  :communication message received (please ensure to exclude STX,ETX,Seq-No and BCC.)
	 * @return    Comment for return value
	 */
	public synchronized void write(byte[] ba)
	{
		try
		{
			wReceiveBufferVect.add(new ByteArray(ba)) ;
			this.notify() ;
		}
		catch (Exception e)
		{
			//#CM33583
			// 6026050=Failed to deliver the text to control system process. StackTrace={0}
			RmiMsgLogClient.write( new TraceHandler(6026050, e), this.getClass().getName() ) ;
		}
	}
	
	//#CM33584
	/**
	 * Command to terminate this thread.
	 */
	public synchronized void finish()
	{
		try
		{
			wFinish = true ;
			this.notify() ;
		}
		catch (Exception e)
		{
			//#CM33585
			// 6026051=Failed to instruct to stop the control system process. StackTrace={0}
			RmiMsgLogClient.write( new TraceHandler(6026051, e), this.getClass().getName() ) ;
		}
	}
	
	//#CM33586
	/**
	 * This method is a description of process flow. COncerning the detailed process of each ID, 
	 * it is requried to override the <code>processReceivedInfo()</code> method.
	 */
	public void run()
	{
		byte[] buff ;
		try
		{
			while (true)
			{
				buff = read();

				//#CM33587
				// If there is no data
				if (buff == null)
				{
					if (wFinish)
					{
						return ;
					}
					
					synchronized (this)
					{
						try
						{
							this.wait() ;
						}
						catch (Exception e)
						{
							//#CM33588
							// 6026052=Error occurred in waiting for the text in control system process. StackTrace={0}
							RmiMsgLogClient.write( new TraceHandler(6026052, e), this.getClass().getName() ) ;
						}
					}
				}
				//#CM33589
				// If the data was received,
				else
				{
					try
					{
						//#CM33590
						// If the database is not connected yet, connect;
						if (wConn == null)
						{
							wConn = AsrsParam.getConnection() ;
						}

						//#CM33591
						// Processing. If error occurs, it receives exceotions.
						processReceivedInfo(buff) ;
						wConn.commit() ;
					}
					catch (Exception e)
					{
						//#CM33592
						// 6024034=Error or warning occurred in the control id process. StackTrace={0}
						RmiMsgLogClient.write(new TraceHandler(6024034, e), this.getClass().getName()) ;
						try
						{
							if (wConn != null)
							{
								wConn.rollback() ;
								wConn.close() ;
							}
						}
						catch (Exception ee)
						{ 
							//#CM33593
							// 6026054=Failed to operate the database in control system process. StackTrace={0}
							RmiMsgLogClient.write( new TraceHandler(6026054, e), this.getClass().getName() ) ;
						}
						wConn = null ;
						sleep() ;
					}
				}
			}
		}
		catch(Exception e)
		{
			//#CM33594
			// 6026053=Failed to execute the control system processing. StackTrace={0}
			RmiMsgLogClient.write( new TraceHandler(6026053, e), this.getClass().getName() ) ;		
		}
		finally
		{
System.out.println("IdProcess:::::finally");
			try
			{
				if (wConn != null)
				{
					wConn.rollback() ;
					wConn.close() ;
				}
			}
			catch (Exception ee)
			{ 
				//#CM33595
				// 6026054=Failed to operate the database in control system process. StackTrace={0}
				RmiMsgLogClient.write( new TraceHandler(6026054, ee), this.getClass().getName() ) ;
			}
		}
	}

	//#CM33596
	// Package methods -----------------------------------------------

	//#CM33597
	// Protected methods ---------------------------------------------
	//#CM33598
	/**
	 * This method actually carries out the process. Overriding in subclass is necessary.
	 * The transaction will not commit or rollback.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected abstract void processReceivedInfo(byte[] rdt) throws Exception ;

	//#CM33599
	/**
	 * Retrieves the received communication message which has been set. Or it returns null if there is no information.
	 * @return    communication message (STX,ETX,Seq-No or BCC are not included.)
	 *
	 */
	protected synchronized byte[] read()
	{
		ByteArray ba ;
		try
		{
			if (wReceiveBufferVect.size() > 0)
			{
				Object wk = wReceiveBufferVect.remove(0) ;
				if (wk instanceof ByteArray)
				{
					ba = (ByteArray)wk ;
					return (ba.getBytes()) ;
				}
			}
		}
		catch (Exception e)
		{
			//#CM33600
			// 6026055=Failed to acquire received text in control system process. StackTrace={0}
			RmiMsgLogClient.write( new TraceHandler(6026055, e), this.getClass().getName() ) ;
		}
		return (null) ;
	}
	//#CM33601
	/**
	 * It sleeps for the defined wait time when error occured.
	 */
	protected void sleep()
	{
		try
		{
			Thread.sleep(getWaitTimeSec()) ;
		}
		catch (Exception e)
		{
			//#CM33602
			// 6026056=Error occurred in control system process. StackTrace={0}
			RmiMsgLogClient.write( new TraceHandler(6026056, e), this.getClass().getName() ) ;
		}
	}
	//#CM33603
	/**
	 * Retrieves the wait time to retry when error occured.
	 * Designation of teh number of seconds will be gained from resource file.(CONTROL_SLEEP_SEC)
	 * return   wait time (second)
	 */
	protected int getWaitTimeSec()
	{
		int wsec = SLEEP_DEFAULT ;
		try
		{
			wsec = AsrsParam.CONTROL_SLEEP_SEC ;
		}
		catch (Exception e)
		{
			//#CM33604
			// 6026057=Error occurred in control system process. StackTrace={0}
			RmiMsgLogClient.write( new TraceHandler(6026057, e), this.getClass().getName() ) ;
		}
		return (wsec) ;
	}
	//#CM33605
	// Private methods -----------------------------------------------

}
//#CM33606
//end of class


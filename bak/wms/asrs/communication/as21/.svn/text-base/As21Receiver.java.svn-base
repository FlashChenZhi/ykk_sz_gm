// $Id: As21Receiver.java,v 1.3 2006/11/13 08:27:53 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM30439
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.wms.asrs.control.IdProcess;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM30440
/**
 * Receives the communication message from AGC, and starts up the corresponding task of each ID.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>kubo</TD><TD>Created the individual messages for AGC connections in run method.</TD></TR>
 * <TR><TD>2004/04/01</TD><TD>inoue</TD><TD>Corrected the method of log recording at the disconnection/reconnection.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/13 08:27:53 $
 * @author  $Author: suresh $
 */
public class As21Receiver extends Thread
{
	//#CM30441
	// Class fields --------------------------------------------------
	//#CM30442
	/**
	* Length of sequence No.
	*/
	public static final int SEQ_LENG = 4;

	//#CM30443
	/**
	 * Parameter for the error log
	 */
	protected static Object[] aErrParm = new Object[1];

	//#CM30444
	// Class variables -----------------------------------------------
	//#CM30445
	/**
	 * Variables which preserves the reference to the monitor thread, the base of start-up process
	 */
	private As21Watcher wAs21Watcher = null;

	//#CM30446
	/**
	 * Variables which preserves the reference to the utility class for the connection with AGC
	 */
	private CommunicationAgc wAgc = null;

	//#CM30447
	/**
	 * Variables which preserves AGC numbers
	 */
	private int wAgcNumber = 1;

	//#CM30448
	/**
	 * Termination Flag for receiving process
	 */
	private  boolean thStop = true;

	//#CM30449
	/**
	 * Memory location for the sequence number of received communication message for use of Sequence 
	 * Number Check
	 */
	//private int recvSeqNumInt = 0;

	//#CM30450
	/**
	 * Memory location for the Sequence Number of to-receive messages for use of Sequence Number Check
	 */
	private int seqNumInt = 0;

	//#CM30451
	/**
	 * Temporary memory area for checking Sequence Number
	 */
	private int wkSeqNumInt = 0;

	//#CM30452
	// Class method --------------------------------------------------
 	//#CM30453
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/13 08:27:53 $") ;
	}

	//#CM30454
	// Constructors --------------------------------------------------
	//#CM30455
	/**
	 * Sets the reference to the Unitility class for the connection with AGC and the reference to 
	 * the start-originating monitor thread and AGC numbers; then implements the initialization of this class.
	 * @param agc         : reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC 
	 * @param as21Watcher : reference to the monitor thread<code>As21Watcher</code>, which is the origination of start-up process 
	 * @param agcNumber   : AGC number
	 */
	public As21Receiver(CommunicationAgc agc, As21Watcher as21Watcher, int agcNumber)
	{
		//#CM30456
		// Sets the reference to the start-originating monitor thread.
		wAs21Watcher = as21Watcher;
		//#CM30457
		// Sets the reference to the Unitility class for the connection with AGC.
		wAgc = agc;
		//#CM30458
		// Sets AGC numbers.
		wAgcNumber = agcNumber;
	}

	//#CM30459
	// Public methods ------------------------------------------------
	//#CM30460
	/**
	 * Starts up the operation per ID, passes the received data and requests processing.
	 * @param id  :identification ID# for processing of received communication messages
	 * @param byteArray :communication message to pass to the processing of received message
	 * @param wHt :reference to the table in order to check whether/not the procesing per ID
	 * has already started .
	 * @throws InvalidDefineException :Notifies if any instance other than IdProcess was generated.
	 */
	public void execIdProcess(String id, byte[] byteArray, Hashtable wHt) throws InvalidDefineException
	{
		//#CM30461
		// Checks whether/not any instance has been alreade created and running in HashTable.
		IdProcess idPro = null;
		if( wHt.containsKey(id) )
		{
			//#CM30462
			// Remove the reference to the thread that data is given from HashTable.
			Object oIdPro = wHt.get(id);
			if( oIdPro instanceof IdProcess )
			{
				idPro = (IdProcess)oIdPro;
			}
			//#CM30463
			// Passes data to the active thread.
			idPro.write(byteArray);
		}
		else
		{
			//#CM30464
			// If there is no instance running, generate one and let run.
			try
			{
				idPro = this.firstExecIdProcess(id, byteArray);
				//#CM30465
				// Registers the instances, generated for each ID, to the HashTable
				wHt.put(id, idPro);
			}
			catch( InvalidDefineException e )
			{
				throw (new InvalidDefineException()) ;
			}
		}
	}

	//#CM30466
	/**
	 * Starts up the operation per ID on the first attempt only; passes the received data and request processing.
	 * @param id : identification ID# for processing the received communication messages
	 * @param bytedata :communication message to pass to the processing of received message
	 * @throws InvalidDefineException :Notifies if any instance other than IdProcess was generated.
	 */
	public IdProcess firstExecIdProcess (String id, byte[] bytedata) throws InvalidDefineException
	{

		IdProcess idP = null;
		try
		{
			//#CM30467
			// Loads class by the name IdXXProcess
			//#CM30468
			// XX can be filled with ID gained from the message text.
			String classname = "jp.co.daifuku.wms.asrs.control.Id" + id + "Process";
			//#CM30469
			//String classname = "Id" + id + "Process";
			Class tClass = Class.forName(classname) ;
			//#CM30470
			// Handles byte-type array as objects.
			Object pdt = Array.newInstance(Byte.TYPE, bytedata.length) ;
			//#CM30471
			// Gets class from the object (pdt) so that the proper constructor should be found. 
			// Practically, byte-type array is reuired.
			Class[] paraClass = new Class[1] ;
			paraClass[0] = pdt.getClass() ;
			//#CM30472
			// Here it gets the constructor of IdXXProcess class which has Int-type arguments.
			Class p1[] = {Integer.TYPE};
			Constructor tConst = tClass.getConstructor(p1) ;
			//#CM30473
			// Here, passes AGC number to the constructor of IdXXProcess class.
			Object[] objAgc = new Object[1];
			objAgc[0] = new Integer(wAgcNumber);
			Object tinst = tConst.newInstance(objAgc) ;
			//#CM30474
			// Sets instance of IdProcess
			if( tinst instanceof IdProcess )
			{
				idP = (IdProcess)tinst;
			}
			else
			{
				throw (new InvalidDefineException()) ;
			}
			//#CM30475
			// Starts-up IdXXProcess.
			idP.start();
			//#CM30476
			// Calls write method of IdXXProcess and passes the data.
			idP.write(bytedata);
		}
		catch( InvalidDefineException e )
		{
			throw (new InvalidDefineException()) ;
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
    		PrintWriter  pw = new PrintWriter(sw);
			String stcomment = WmsParam.STACKTRACE_COMMENT;
			//#CM30477
			//Records error in the log file.
			//#CM30478
			//6016030=Error occurred in receiving task of communication module when the ID process was started.
			e.printStackTrace(pw);
			Object[] tobj = new Object[1];
			tobj[0] = stcomment + sw.toString();
			RmiMsgLogClient.write(6016030, LogMessage.F_ERROR, "As21Receiver", tobj);
		}
		return ( idP );
	}

	//#CM30479
	/**
	 * Conducts the text receiving process.
	 */
	public void run()
	{
		int count = 0;
		Hashtable wHt = new Hashtable();
		
		try
		{
			count = 1;
			wAgc.connect();
			//#CM30480
			// Table to check whether/not the process per ID has already started.
			//#CM30481
			// Keeps looping until terminating flag changes to 'terminate' by command.
			while( thStop )
			{
				count = 2;
				//#CM30482
				// Lets record in log.
				if (!wAs21Watcher.getLoggingFlag())
				{
					Object[] objAgc = new Object[1];
					objAgc[0] = new Integer(wAgcNumber);
					//#CM30483
					// 6020032=Send/Receive task of the communication module is started. SRC NO={0}
					RmiMsgLogClient.write(6020032, LogMessage.F_INFO, "As21Receiver", objAgc);
					wAs21Watcher.setLoggingON();
				}

				//#CM30484
				// Receiving message from AGC
				byte[] rdata = wAgc.recv(); 
				
				//#CM30485
				// SEQ No. Check
				if(!seqNumCheck( rdata ) )
				{
					//#CM30486
					// 6024023=Sequence number of received message is the same as the previous one.
					Object[] tobj = new Object[1] ;
					tobj[0] = null;
					RmiMsgLogClient.write(6024023, LogMessage.F_WARN, "As21Receiver", tobj);
					continue;
				}

				int wKbyteLength = rdata.length;
				byte[] wKbyte = new byte[wKbyteLength-SEQ_LENG];
				for(int jj=0; jj < wKbyteLength-SEQ_LENG ; jj++)
				{
					wKbyte[jj] = rdata[jj+SEQ_LENG];
				}
				//#CM30487
				// Checks the ID adn determine type of the message.
				IdMessage IdMes = new IdMessage(wKbyte);
				//#CM30488
				// Start-up process of each ID
				this.execIdProcess( IdMes.getID(), wKbyte, wHt );
			}
		}
		catch (Exception e)
		{
			System.out.println("count;;;"+count);
			if (wAs21Watcher.getLoggingFlag())
			{
				if ( count == 1 )
				{
					StringWriter sw = new StringWriter();
		    		PrintWriter  pw = new PrintWriter(sw);
					//#CM30489
					//Records error in the log file.
					//#CM30490
					//6024033=Cannot connect with SRC. (Connection Refused) SRC NO={0}
					e.printStackTrace(pw);
					Object[] objAgc = new Object[1];
					objAgc[0] = new Integer(wAgcNumber);
					RmiMsgLogClient.write(6024033, LogMessage.F_WARN, "As21Receiver", objAgc);
					wAs21Watcher.setLoggingOFF();
				}
				else
				{
					StringWriter sw = new StringWriter();
		    		PrintWriter  pw = new PrintWriter(sw);
					//#CM30491
					//Records error in the log file.
					//#CM30492
					//6024032=Connection with SRC is disconnected. SRC NO={0}
					e.printStackTrace(pw);
					Object[] objAgc = new Object[1];
					objAgc[0] = new Integer(wAgcNumber);
					RmiMsgLogClient.write(6024032, LogMessage.F_WARN, "As21Receiver", objAgc);
					wAs21Watcher.setLoggingOFF();
				}
			}
		}
		finally
		{
System.out.println("As21Receiver ::: finally");
			//#CM30493
			// Terminating thread per ID
			finish(wHt);
			//#CM30494
			// Notifies monitor thread, the origination of start-up, that receiving process terminated.
			wAs21Watcher.setAs21ReceiverInstance();
			//#CM30495
			// Sends interruption to the monitor thread (the originaion of start-up)
			wAs21Watcher.wakeup();
		}
	}

	//#CM30496
	/**
	 * Terminates this thread.
	 * If this method is called externally, communication process will be terminated.
	 */
	public synchronized void setStop()
	{
		thStop = false;
	}

	//#CM30497
	/**
	 * Returns the reference to the Utility class for the connection with AGC.
	 * @return reference to the <code>CommunicationAgc</code>, which is the Unitility class for the connection with AGC 
	 */
	public CommunicationAgc getCommunicationAgc()
	{
		return wAgc;
	}

	//#CM30498
	// Package methods -----------------------------------------------

	//#CM30499
	// Protected methods ---------------------------------------------

	//#CM30500
	// Private methods -----------------------------------------------
	//#CM30501
	/**
	 * Handles termination process of the thread started by each ID.
	 * @param ht Reference to teh table to check whether/not the processing has alrady started
	 * by each ID.
	 */
	private void finish(Hashtable ht)
	{
		if (ht == null) return;

		IdProcess idPro = null;
		//#CM30502
		// Takes the list of value out of HashTable.
		for (Enumeration e = ht.elements() ; e.hasMoreElements() ;) 
		{
			Object elemnt = e.nextElement();
			if( elemnt instanceof IdProcess )
			{
				idPro = (IdProcess)elemnt;
			}

			//#CM30503
			// Handles termination process for active threads.
			idPro.finish();
		}

	}

	//#CM30504
	/**
	 * Checks the Sequence Number of received data.
	 * @param seqData received message
	 */
	boolean seqNumCheck( byte[] seqData )
	{
		//#CM30505
		// Takes out the Sequence Number in the message and convert to Int.
		byte[] seqWk = new byte[SEQ_LENG];
		for(int i=0; i<SEQ_LENG; i++)
		{
			seqWk[i] = seqData[i];
		}
		String seqNumString = new String(seqWk);
		int recvSeqNumInt = Integer.parseInt(seqNumString);
		//#CM30506
		// Compares the received Sequence Number and Sequence Numbr to-receive.
		//#CM30507
		// If the Sequence Number of received message is 0, it is the first message received.
		if(recvSeqNumInt == CommunicationAgc.INITIAL_NUMBER)
		{
			seqNumInt = CommunicationAgc.LOOP_START_NUMBER;
		}
		//#CM30508
		// If the Sequence Number of received message is the same as Sequence Number of to-receive
		// messages, normal
		else if(recvSeqNumInt == seqNumInt)
		{
			seqNumInt++;
			if(seqNumInt > CommunicationAgc.MAX_SEQ_NUMBER) seqNumInt = CommunicationAgc.LOOP_START_NUMBER;
		}
		//#CM30509
		// If the Sequence Number of received message is the last but one, or something unforeseen.
		else
		{
			wkSeqNumInt = seqNumInt -1;
			if(wkSeqNumInt < CommunicationAgc.LOOP_START_NUMBER) wkSeqNumInt = CommunicationAgc.MAX_SEQ_NUMBER;
			//#CM30510
			//  If the Sequence Number of received message is the last but one, scrap.
			if(recvSeqNumInt == wkSeqNumInt)
			{
				return(false);
			}

			//#CM30511
			//  If unforseen Sequence Number has been provided to the received message, number the next 
			//  Sequence Number by adding 1 to it (+1).
			seqNumInt = recvSeqNumInt + 1;
			if(seqNumInt > CommunicationAgc.MAX_SEQ_NUMBER) seqNumInt = CommunicationAgc.LOOP_START_NUMBER;
		}

		return(true);
	}
}
//#CM30512
//end of class

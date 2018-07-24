// $Id: ThreadOperation.java,v 1.2 2006/10/24 06:02:42 suresh Exp $
package jp.co.daifuku.wms.asrs.common ;

//#CM28657
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.UnmarshalException;
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.RmiMsgLogServer;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Watcher;
import jp.co.daifuku.wms.asrs.communication.as21.AutomaticModeChangeSender;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.RequestWatcher;
import jp.co.daifuku.wms.asrs.communication.as21.RetrievalSender;
import jp.co.daifuku.wms.asrs.communication.as21.StorageSender;
import jp.co.daifuku.wms.asrs.communication.as21.TimeKeeper;

//#CM28658
/**
 * This class preserves the main method to terminate the resident tasks.
 * It terminates each thread of message log server, AS21 communication process, communication monitoring, transmission of carrying instructions, 
 * transmission of output command, transmission of carrying instruction on automatic switching mode, time monitoring, request monitoring.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/06/26</TD><TD>miyashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 06:02:42 $
 * @author  $Author: suresh $
 */
public class ThreadOperation
{
	//#CM28659
	// Class fields --------------------------------------------------

	//#CM28660
	// Class variables -----------------------------------------------

	//#CM28661
	// Class method --------------------------------------------------
 	//#CM28662
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 06:02:42 $") ;
	}

	//#CM28663
	// Constructors --------------------------------------------------
	//#CM28664
	// Public methods ------------------------------------------------
	//#CM28665
	/**
	 * Processes termination of each task.
	 */
	public static void main(String[] args)
	{
		try
		{
			DecimalFormat fmt = new DecimalFormat("00");

			//#CM28666
			// Gets connection with DataBase. User's name and else will be obtained from resource file.
			Connection conn = AsrsParam.getConnection();

			//#CM28667
			// From the group controller class, gets all data of group controller existing in the system.
			GroupController[] gpColection = GroupController.getInstances( conn );
			//#CM28668
			// Termination of sending and receiving jobs for all group contoller machines.
			for( int i=0; i < gpColection.length; i++)
			{
				//#CM28669
			// Termination of monitoring thread for connection
				stop( As21Watcher.OBJECT_NAME + fmt.format(gpColection[i].getNumber()));
			}

			//#CM28670
			// Terminates thread for transmission job of carrying instruction.
			stop(StorageSender.OBJECT_NAME);

			//#CM28671
			// Terminates thread for tranmission job of output command.
			stop(RetrievalSender.OBJECT_NAME);

			//#CM28672
			// Terminates thread for transmission job of carrying instruction on automatic switching mode.
			stop(AutomaticModeChangeSender.OBJECT_NAME);

			//#CM28673
			// Terminates thread for time monitoring.
			stop(TimeKeeper.OBJECT_NAME);

			//#CM28674
			// Terminates thread for request monitoring job.
			stop(RequestWatcher.OBJECT_NAME);

			//#CM28675
			// Exit the message log server after terminating the above mentioned resident program.
			Thread.sleep(20000);
			
			//#CM28676
			// Terminates message log server.
			stop(RmiMsgLogServer.LOGSERVER_NAME);
		}
		catch(ConnectException e)
		{
			//#CM28677
			// RMI registry is not activated; thread termination cannot be processed.
			RmiMsgLogClient.write(6016099, "ThreadOperation");
		}
		catch(Exception e)
		{
			//#CM28678
			// Fatal error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6016102, e), "ThreadOperation");
		}
	}

	//#CM28679
	// Package methods -----------------------------------------------

	//#CM28680
	// Protected methods ---------------------------------------------

	//#CM28681
	// Private methods -----------------------------------------------
	//#CM28682
	/**
	 * Terminates thread by specified name.
	 * @param name : Name bound to remote object
	 */
	private static void stop(String name) throws ConnectException
	{
		try
		{
			RmiSendClient rmiSndC = new RmiSendClient();
			//#CM28683
			// Terminates the message log server.
			rmiSndC.stop(name);
			rmiSndC = null;
		}
		catch(NotBoundException e)
		{
			//#CM28684
			// Specified name is not bound to remote object.
			//#CM28685
			// Thread is not running. Thread name:{0}
			RmiMsgLogClient.write("6016100" + MessageResource.DELIM + name, "ThreadOperation");
		}
		catch(ConnectException e)
		{
			throw new ConnectException(e.getMessage());
		}
		catch(UnmarshalException e)
		{
			//#CM28686
			// As RmiLog Server is not operated on Thread, Exception occurs when System.exit(0); 
			//#CM28687
			// is invoked. 
		}
		catch(Exception e)
		{
			//#CM28688
			// Fatal error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6016102, e), "ThreadOperation");
		}
	}
}
//#CM28689
//end of class

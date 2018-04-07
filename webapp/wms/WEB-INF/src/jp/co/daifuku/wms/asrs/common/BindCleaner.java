// $Id: BindCleaner.java,v 1.2 2006/10/24 08:22:37 suresh Exp $
package jp.co.daifuku.wms.asrs.common ;

//#CM28578
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.text.DecimalFormat;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.rmi.RmiUnbinder;
import jp.co.daifuku.wms.asrs.communication.as21.As21Sender;
import jp.co.daifuku.wms.asrs.communication.as21.As21Watcher;
import jp.co.daifuku.wms.asrs.communication.as21.AutomaticModeChangeSender;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.RetrievalSender;
import jp.co.daifuku.wms.asrs.communication.as21.StorageSender;
import jp.co.daifuku.wms.asrs.communication.as21.TimeKeeper;

//#CM28579
 /**
 * This class is used to delete the objects registered in RMI registry server
 * from registry server.
 * Please specify as following when ecxecuting this process.
 * java BindClener [object name]
 * Ex. java BindClener messagelog_server
 * Even if the specified objects is not registered, Exception will not occur.
 * Besides, if AS21Watcher is specified, data of all group controllers will be deleted
 * since the same number of group controllers are registered in server.
  * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/11/11</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/24 08:22:37 $
 * @author  $Author: suresh $
 */
public class BindCleaner
{
	//#CM28580
	// Class fields --------------------------------------------------

	//#CM28581
	// Class variables -----------------------------------------------

	//#CM28582
	// Class method --------------------------------------------------
 	//#CM28583
	/**
	 * Return the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/24 08:22:37 $") ;
	}

	//#CM28584
	// Constructors --------------------------------------------------

	//#CM28585
	// Public methods ------------------------------------------------

	//#CM28586
	// Package methods -----------------------------------------------
	//#CM28587
	/**
	 * Delete the remote object from the registry server.
	 */
	public static void main (String[] args) throws Exception
	{
		if (args.length > 0)
		{
			BindCleaner clener = new BindCleaner();

			//#CM28588
			// If AS21 communication processing is specifed as an object name,
			//#CM28589
			// deletion will be processed as many number of times as the number of group controllers
			//#CM28590
			// since the group controllers are registerd according to the controller numbers, deletion
			//#CM28591
			// will be conducted by the same number of times as the number of group controllers.
			if (args[0].equals(As21Watcher.OBJECT_NAME))
			{
				//#CM28592
				// Acquire the connection with DataBase. User name and other data can be retrieved from resource file.
				Connection conn = AsrsParam.getConnection();
				//#CM28593
				// Get all the data of group controllers that exist in systen using group controller class.
				GroupController[] gpColection = GroupController.getInstances( conn );
				//#CM28594
				// Unbind as many number of times as the number of group controllers.
				//#CM28595
				// There are 2 types of target objects: As21Watcher and As21Sender.
				DecimalFormat fmt = new DecimalFormat("00");
				for (int i = 0; i < gpColection.length; i++)
				{
					BindCleaner.unbind (As21Watcher.OBJECT_NAME + fmt.format(gpColection[i].getNumber()));
					BindCleaner.unbind (As21Sender.OBJECT_NAME + fmt.format(gpColection[i].getNumber()));
				}
			}
			//#CM28596
			// In case the StorageSender is specified as the object name,
			else if (args[0].equals(StorageSender.OBJECT_NAME))
			{
				BindCleaner.unbind (TimeKeeper.OBJECT_NAME);
				BindCleaner.unbind (StorageSender.OBJECT_NAME);
				BindCleaner.unbind (RetrievalSender.OBJECT_NAME);
				BindCleaner.unbind (AutomaticModeChangeSender.OBJECT_NAME);
			}
			else
			{
				//#CM28597
				// Unbind the object of the specified name.
				BindCleaner.unbind(args[0]);
			}
		}
		else
		{
			System.out.println("Parameter Not Found [ex: RmiUnbinder object-name]");
		}
	}

	//#CM28598
	// Protected methods ---------------------------------------------

	//#CM28599
	// Private methods -----------------------------------------------
	//#CM28600
	/**
	 * Delete the remote object of specified bane from the registry server.
	 * If the specified name cannot be found in registry server, no further process will be done.
	 * @param name :remote object name to be deleted
	 * @throws Exception :Notify in case the deletion of data from registry server failed.
	 */
	private static void unbind(String name) throws Exception
	{
		try
		{
			RmiUnbinder unbinder = new RmiUnbinder();
			//#CM28601
			// Check to see if the specified name is registered in registry server, 
			//#CM28602
			// then delete data from registry server only if the data is found in registration.
			if (unbinder.isbind(name))
			{
				unbinder.unbind(name);
			}
			else
			{
				System.out.println(name + ": Registry Server Not Found");
			}
		}
		catch (Exception e)
		{
			//#CM28603
			// Fatal error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6016102, e), "BindCleaner");
			throw e;
		}
	}
}
//#CM28604
//end of class


// $Id: IdProcessControl.java,v 1.2 2006/11/07 06:49:54 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft ;

//#CM643832
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM643833
/**
 * Distribute the ID processing after request transmission message is received. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>A.Miyasita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:49:54 $
 * @author  $Author: suresh $
 */
public class IdProcessControl
{
	//#CM643834
	// Class fields --------------------------------------------------
	
	//#CM643835
	// Class variables -----------------------------------------------
 	//#CM643836
 	/**
	 * Connection
	 */
	Connection wConn = null;

	//#CM643837
	// Class method --------------------------------------------------
 	//#CM643838
 	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:49:54 $") ;
	}

	//#CM643839
	// Constructors --------------------------------------------------
	//#CM643840
	/**
	 * Constructor of ID distribution processing. <BR>
	 * Start the processing of each ID, pass receive data, and request processing. 
	 * @param conn Database connection
	 */
	public IdProcessControl(Connection conn)
	{
		wConn = conn;
	}

	//#CM643841
	// Public methods ------------------------------------------------
	//#CM643842
	/**
	 * Distribute ID and process it. <BR>
	 * Generate the class name from ID received by the package list and the argument 
	 * acquired from the package manager, and generate the object of the 
	 * processing class of the name. <BR>
	 * Set the DB connection object for the generated object. <BR>
	 * Pass received data to the generated object and request processing. <BR>
	 * Output error Message to the log as a communication error when the 
	 * object of the class which corresponds is not generable. 
	 * 
	 * @param id			Identification number of Receiving transmission message 
	 * @param recvByteArray Contains Receive data STX and ETX.
	 * @param sendByteArray Contains Send data STX and ETX.
	 * @param dbConn 		Reference to OracleDBConnection <code>Connection</code> of each RFT title machine
	 */
	public void executeIdProc(String id, byte[] recvByteArray,
					 byte[] sendByteArray , Connection dbConn)
	{
		//#CM643843
		// Start correspondence ID processing. 
		try
		{
			//#CM643844
			// Make the processing instance. 
			IdProcess idProc = newIdProcess(id);
			//#CM643845
			// Make the processing instance. 
			idProc.setConnection(dbConn);
			//#CM643846
			// Execute processing. 
			//#CM643847
			// Two or more processing must not be executed at the same time by using same DBConnection. 
			//#CM643848
			// Control it exclusively. 
			synchronized (dbConn)
			{
				idProc.processReceivedId(recvByteArray, sendByteArray);
			}
		}
		catch (IllegalAccessException e)
		{
			//#CM643849
			// When failing in the generation of the instance
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, "IdProcessControl", e);
		}
	}

	//#CM643850
	// Package methods -----------------------------------------------

	//#CM643851
	// Protected methods ---------------------------------------------

	//#CM643852
	// Private methods -----------------------------------------------
	//#CM643853
	/**
	 * Generate the instance of the ID processing class corresponding to specified ID. <BR>
	 * Acquire the list of an effective package from the package manager. 
	 * Generate the class name of all effective packages. <BR>
	 * Try the instance generation sequentially by the class name. 
	 * Return the instance when succeeding. <BR>
	 * Return < CODE>null</CODE > when failing in the instance generation. 
	 * 
	 * @param id	Identification number of Receiving transmission message 
	 * @return 	ID processing object
	 * @throws IllegalAccessException	When failing in the generation of the instance is notified. 
	 */
	private IdProcess newIdProcess(String id) throws IllegalAccessException
	{
		//#CM643854
		// Open the loading data file. 
		String className = "Id" + id + "Process";
		return (IdProcess) PackageManager.getObject(className);
	}
}
//#CM643855
//end of class

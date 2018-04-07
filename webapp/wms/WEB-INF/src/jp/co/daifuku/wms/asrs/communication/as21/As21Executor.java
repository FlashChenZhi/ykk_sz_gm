// $Id: As21Executor.java,v 1.2 2006/10/26 01:41:02 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM28690
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
//#CM28691
/**
 * This class starts up as many communication modules as the number of AGC machines required.
 * It terminates as it starts up.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:41:02 $
 * @author  $Author: suresh $
 */
public class As21Executor 
{
	//#CM28692
	// Class fields --------------------------------------------------
	//#CM28693
	/**
	 * When the communication process with AGC establishes, it starts up with respective AGC at  
	 * interval of this duration of time given.(mS)
	 */
	private static final int AGC_EXEC_TIME = 500;

	//#CM28694
	// Class variables -----------------------------------------------

	//#CM28695
	// Class method --------------------------------------------------
 	//#CM28696
 	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:41:02 $") ;
	}

	//#CM28697
	// Constructors --------------------------------------------------

	//#CM28698
	// Public methods ------------------------------------------------
	//#CM28699
	/**
	 * Starts up as many communication modules as AGC machines in system.
	 */
	public static void main( String[] args)
	{
		try
		{
			//#CM28700
			// Gains connection with DataBase. Required information such as users name nad else will be gained
			// from the resource file.
			Connection conn = AsrsParam.getConnection();
			//#CM28701
			// From the gourp controller class, it gains data of all group controller that exists in system. 
			GroupController[] gpColection = GroupController.getInstances( conn );
			//#CM28702
			// Creates sending and receiving jobs for all group contoller machines.
			for( int i=0; i < gpColection.length; i++)
			{
				//#CM28703
				// Acquires instances of utility class for AGC connection.
				//#CM28704
				// Creates and starts up the thread for connection monitoring.
				As21Watcher as21wach = new As21Watcher( gpColection[i].getIP().getHostName(), gpColection[i].getPort(), gpColection[i].getNumber() );
				new Thread(as21wach).start();

				Thread.sleep(AGC_EXEC_TIME);
			}
		}
		catch (Exception e)
		{
			StringWriter sw = new StringWriter();
    		PrintWriter  pw = new PrintWriter(sw);
			String stcomment = WmsParam.STACKTRACE_COMMENT;
			//#CM28705
			//Record errors in the log file.	
			//#CM28706
			// 6026039=communication module activation failed
			e.printStackTrace(pw);
			Object[] tobj = new Object[1];
			tobj[0] = stcomment + sw.toString();
			RmiMsgLogClient.write(6026039, LogMessage.F_ERROR, "As21Executor", tobj);
		}
	}

	//#CM28707
	// Package methods -----------------------------------------------

	//#CM28708
	// Protected methods ---------------------------------------------

	//#CM28709
	// Private methods -----------------------------------------------

}
//#CM28710
//end of class


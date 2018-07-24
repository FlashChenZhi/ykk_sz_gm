// $Id: RftLogMessage.java,v 1.2 2006/11/07 06:36:16 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM644031
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM644032
/**
 * Class to output to Message log
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:36:16 $
 * @author $Author: suresh $
 */
public class RftLogMessage
{
	//#CM644033
	// Class fields --------------------------------------------------
	//#CM644034
	// Class variables -----------------------------------------------
	//#CM644035
	// Class method --------------------------------------------------
	//#CM644036
	/**
	 * Output log Message adding specified Message. 
	 * 
	 * @param msgNo		Number of log Message
	 * @param className	Class Name
	 * @param msg		Message
	 */
	public static void print(int msgNo, String className, String msg)
	{
		Object[] tobj = new Object[1];
		tobj[0] = msg;
		RmiMsgLogClient.write(msgNo, className, tobj);
	}
	
	//#CM644037
	/**
	 * Output log Message adding specified Message. 
	 * 
	 * @param msgNo		Number of log Message
	 * @param facility	Facility
	 * @param className	Class Name
	 * @param msg		Message
	 */
	public static void print(int msgNo, String facility, String className, String msg)
	{
		Object[] tobj = new Object[1];
		tobj[0] = msg;
		RmiMsgLogClient.write(msgNo, facility, className, tobj);
	}
	
	//#CM644038
	/**
	 * Output log Message adding specified Message. 
	 * 
	 * @param msgNo		Number of log Message
	 * @param facility	Facility
	 * @param className	Class Name
	 * @param msg1		Message
	 * @param msg2		Message
	 */
	public static void print(int msgNo, String facility, String className, String msg1, String msg2)
	{
		Object[] tobj = new Object[2];
		tobj[0] = msg1;
		tobj[1] = msg2;
		RmiMsgLogClient.write(msgNo, facility, className, tobj);
	}
	
	//#CM644039
	/**
	 * Output the stack trace of the specified exception to log Message. 
	 * 
	 * @param msgNo		Number of log Message
	 * @param facility	Facility
	 * @param className	Class Name
	 * @param e			Exception
	 */
	public static void printStackTrace(int msgNo, String facility, String className, Exception e)
	{
		printStackTrace(msgNo, facility, className, e, null);
	}

	//#CM644040
	/**
	 * Output the stack trace of the specified exception to log Message. 
	 * 
	 * @param msgNo		Number of log Message
	 * @param facility	Facility
	 * @param className	Class Name
	 * @param e			Exception
	 * @param msg		Message
	 */
	static void printStackTrace(int msgNo, String facility, String className, Exception e, String msg)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		String stcomment = WmsParam.STACKTRACE_COMMENT;
		//#CM644041
		// Drop the error to the log file. 
		e.printStackTrace(pw);
		Object[] tobj;
		if (msg == null)
		{
			tobj = new Object[1];
		}
		else
		{
			tobj = new Object[2];
			tobj[1] = msg;
		}
		tobj[0] = stcomment + sw.toString();
		RmiMsgLogClient.write(msgNo, facility, className, tobj);
	}
	//#CM644042
	// Constructors --------------------------------------------------
	//#CM644043
	// Public methods ------------------------------------------------
	//#CM644044
	// Package methods -----------------------------------------------
	//#CM644045
	// Protected methods ---------------------------------------------
	//#CM644046
	// Private methods -----------------------------------------------
}
//#CM644047
//end of class

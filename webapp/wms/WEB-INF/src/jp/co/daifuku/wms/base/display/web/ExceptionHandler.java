// $Id: ExceptionHandler.java,v 1.2 2006/11/07 06:56:13 suresh Exp $

//#CM664485
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.display.web;

import java.sql.SQLException;

import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;



//#CM664486
/** 
 * The class which processes the exception received in the business logic class. 
 * <table border="1" cellpadding="3" cellspacing="0"><tr><td>
 * <pre>
 * Example of exception handling of XXXBusiness.java
 * public void btn_Add_Click(ActionEvent e) throws Exception
 * {
 *   Connection conn = null;
 *   try
 *   {
 *     // Disposal of business affairs
 *   }
 *   <font color="blue">
 *   // A peculiar exception in this screen and a slow situation catches here. 
 *   catch(UnsupportEncodingException e)
 *   {
 *     // Handling
 *   }
 *   </font>
 *   catch(Exception e)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e), this);</font>
 *   }
 *   finally
 *   {
 *     try
 *     {
 *       // Connection close
 *       if(conn != null) conn.close();
 *     }
 *     catch(SQLException se)
 *     {
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e), this);</font>
 *     }
 *   }
 * }
 * </pre>
 * </td></tr></table>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/15</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:56:13 $
 * @author  $Author: suresh $
 */
/** 
 * The class which processes the exception received in the business logic class. 
 * <table border="1" cellpadding="3" cellspacing="0"><tr><td>
 * <pre>
 * Example of exception handling of XXXBusiness.java
 * public void btn_Add_Click(ActionEvent e) throws Exception
 * {
 *   Connection conn = null;
 *   try
 *   {
 *     // Disposal of business affairs
 *   }
 *   <font color="blue">
 *   // A peculiar exception in this screen and a slow situation catches here. 
 *   catch(UnsupportEncodingException e)
 *   {
 *     // Handling
 *   }
 *   </font>
 *   catch(Exception e)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e), this);</font>
 *   }
 *   finally
 *   {
 *     try
 *     {
 *       // Connection close
 *       if(conn != null) conn.close();
 *     }
 *     catch(SQLException se)
 *     {
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e), this);</font>
 *     }
 *   }
 * }
 * </pre>
 * </td></tr></table>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/15</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:56:13 $
 * @author  $Author: suresh $
 */
public class ExceptionHandler extends Object
{
	//#CM664487
	// Class fields --------------------------------------------------

	//#CM664488
	// Class variables -----------------------------------------------

	//#CM664489
	// Class method --------------------------------------------------
	//#CM664490
	/** 
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:56:13 $") ;
	}

	//#CM664491
	/** 
	 * Return the message number corresponding to the type of the received exception. <br>
	 * Return the message for the following exceptions. <br>
	 * <code>ScheduleException</code>, <code>ReadWriteException</code>, <code>SQLException</code><br>
	 * Besides, the received exception is slow again<br>
	 * In that case, the error message is displayed for < code>ValidateException</code >. 
	 * As for exceptions other than < code>ValidateException</code >, the error page is displayed. 
	 * <table border="1" cellpadding="3" cellspacing="0">
	 * <tr><td>Exception class        </td><td>Message number</td></tr>
	 * <tr><td>ScheduleException </td><td>6017011</td></tr>
	 * <tr><td>ReadWriteException</td><td>6017001</td></tr>
	 * <tr><td>SQLException      </td><td>6017001</td></tr>
	 * </table>
	 * @param e  Exception
	 * @param c  Business logic object
	 * @return   Message number of message displayed on screen
	 * @throws   Exceptions other than ScheduleException,ReadWriteException,SQLException
	 */
	public static String getDisplayMessage(Exception e, Object c) throws Exception
	{
		String message = null;
		if (e instanceof ScheduleException)
		{
			//#CM664492
			// An internal error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006021, e), c.getClass().getName() ) ;
			//#CM664493
			// An internal error occurred. Refer to the log. 
			message = "6027005";
		}
		else if (e instanceof ReadWriteException)
		{
			//#CM664494
			// The data base error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), c.getClass().getName() ) ;
			//#CM664495
			// The data base error occurred. Refer to the log. 
			message = "6007002";
		}
		else if (e instanceof SQLException)
		{
			//#CM664496
			// 6006002 = The data base error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), c.getClass().getName() ) ;
			message = "6007002";
		}
		else if (e instanceof ValidateException)
		{
			//#CM664497
			// The processing when the mandatory input check is NG. 
			//#CM664498
			// Do not set it here to set the message by the framework. 
			throw e;
		}
		else
		{
			//#CM664499
			// RuntimeException processing
			//#CM664500
			// 6006001=The error not anticipated occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006001, e), c.getClass().getName() ) ;
			message = "6027009";
		}
		return message;
	}

	//#CM664501
	// Constructors --------------------------------------------------
	//#CM664502
	/** 
	 * It is not used. 
	 */
	private ExceptionHandler()
	{
	}

	//#CM664503
	// Public methods ------------------------------------------------

	//#CM664504
	// Package methods -----------------------------------------------

	//#CM664505
	// Protected methods ---------------------------------------------

	//#CM664506
	// Private methods -----------------------------------------------

}
//#CM664507
//end of class

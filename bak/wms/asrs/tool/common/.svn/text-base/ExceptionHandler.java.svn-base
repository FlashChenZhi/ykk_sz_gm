// $Id: ExceptionHandler.java,v 1.2 2006/10/30 01:40:56 suresh Exp $

//#CM46645
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.tool.common;

import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;

//#CM46646
/** <en>
 * This class deals with the exception that business logic class is thrown.
 * <table border="1" cellpadding="3" cellspacing="0"><tr><td>
 * <pre>
 * example Exception handling in XXXBusiness.java
 * public void btn_Add_Click(ActionEvent e) throws Exception
 * {
 *   Connection conn = null;
 *   try
 *   {
 *     // business logic 
 *   }
 *   <font color="blue">
 *   // It gets a special exception here. 
 *   catch(UnsupportEncodingException uee)
 *   {
 *     // handling
 *   }
 *   </font>
 *   catch(Exception e)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));</font>
 *   }
 *   finally
 *   {
 *     try
 *     {
 *       // close the database connection
 *       if(conn != null) conn.close();
 *     }
 *     catch(SQLException se)
 *     {
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));</font>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:56 $
 * @author  $Author: suresh $
 </en> */
/** <en>
 * This class deals with the exception that business logic class is thrown.
 * <table border="1" cellpadding="3" cellspacing="0"><tr><td>
 * <pre>
 * example Exception handling in XXXBusiness.java
 * public void btn_Add_Click(ActionEvent e) throws Exception
 * {
 *   Connection conn = null;
 *   try
 *   {
 *     // business logic 
 *   }
 *   <font color="blue">
 *   // It gets a special exception here. 
 *   catch(UnsupportEncodingException uee)
 *   {
 *     // handling
 *   }
 *   </font>
 *   catch(Exception e)
 *   {
 *     <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));</font>
 *   }
 *   finally
 *   {
 *     try
 *     {
 *       // close the database connection
 *       if(conn != null) conn.close();
 *     }
 *     catch(SQLException se)
 *     {
 *       <font color="blue">message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));</font>
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
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:56 $
 * @author  $Author: suresh $
 </en> */
public class ExceptionHandler extends Object
{
	//#CM46647
	// Class fields --------------------------------------------------

	//#CM46648
	// Class variables -----------------------------------------------

	//#CM46649
	// Class method --------------------------------------------------
	//#CM46650
	/** <en>
	 * The version of this class is returned.
	 * @return A version and a date
	 </en> */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:56 $");
	}

	//#CM46651
	/** <en>
	 * Return the Message number.<br>
	 * The exception of the object <br>
	 * <code>ScheduleException</code>, <code>ReadWriteException</code>, <code>SQLException</code><br>
	 * Throw the exception received except for that.<br>
	 * When <code>ValidateException</code> is caught, a message is indicated in the message area. 
	 * An error page is indicated in the exception except for <code>ValidateException</code>.
	 * <table border="1" cellpadding="3" cellspacing="0">
	 * <tr><td>Exception class   </td><td>return the message number</td></tr>
	 * <tr><td>ScheduleException </td><td>6017011</td></tr>
	 * <tr><td>ReadWriteException</td><td>6017001</td></tr>
	 * <tr><td>SQLException      </td><td>6017001</td></tr>
	 * </table>
	 * @param e  Exception
	 * @param c  business logic object
	 * @return   A number of message to indicate on the screen 
	 * @throws Exception an exception except for ScheduleException, ReadWriteException and SQLException 
	 </en>*/
	public static String getDisplayMessage(Exception e, Object c) throws Exception
	{
		String message = null;
		if (e instanceof ScheduleException)
		{
			message = "6127006";
		}
		else if (e instanceof ReadWriteException)
		{
			message = "6127005";
		}
		else if (e instanceof SQLException)
		{
			message = "6127005";
		}
		else
		{
			throw e;
		}
		return message;
	}

	//#CM46652
	// Constructors --------------------------------------------------
	//#CM46653
	/** <en>
	 * can not use.
	 </en>*/
	private ExceptionHandler()
	{
	}

	//#CM46654
	// Public methods ------------------------------------------------

	//#CM46655
	// Package methods -----------------------------------------------

	//#CM46656
	// Protected methods ---------------------------------------------

	//#CM46657
	// Private methods -----------------------------------------------

}
//#CM46658
//end of class

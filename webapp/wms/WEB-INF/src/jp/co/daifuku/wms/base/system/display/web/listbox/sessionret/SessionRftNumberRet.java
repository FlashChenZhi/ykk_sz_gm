// $Id: SessionRftNumberRet.java,v 1.2 2006/11/13 08:21:02 suresh Exp $

//#CM693247
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RftFinder;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693248
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to search for the RFT Control info and display it.<BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRftNumberRet(Connection conn, SystemParameter param)</CODE>method)<BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default.<BR>
 *   Invoke the <CODE>find(SystemParameter param)</CODE> method and search for the RFT Control info.<BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Machine No.<BR>
 *   </DIR>
 *   <Search table>
 *   <DIR>
 *     DNRFT<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Process for displaying(<CODE>getEntities()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result in the <CODE>Rft</CODE> array and return it.<BR>
 * <BR>
 *   <Field items to displayed>
 *   <DIR>
 *     Machine No.<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:02 $
 * @author  $Author: suresh $
 */
public class SessionRftNumberRet extends SessionRet
{
	//#CM693249
	// Class fields --------------------------------------------------

	//#CM693250
	// Class variables -----------------------------------------------

	//#CM693251
	// Class method --------------------------------------------------
	//#CM693252
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 08:21:02 $");
	}

	//#CM693253
	// Constructors --------------------------------------------------
	//#CM693254
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching.<BR>
	 * Allow the <CODE>find(SystemParameter param)</CODE> method to set the count of obtained data.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionRftNumberRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM693255
		// Search
		find(param);
	}

	//#CM693256
	// Public methods ------------------------------------------------
	//#CM693257
	/**
	 * Return the designated number of search results of RFT Control info.<BR>
	 * Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain the RFT Control info. from the result set.<BR>
	 *   3.Obtain the display data from the RFT Control info and set it in <CODE>Rft</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * 
	 * @return <CODE>SystemParameter</CODE> class that contains information to be displayed.
	 */
	public Rft[] getEntities()
	{
		Rft[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM693258
				// Obtain the search result.
				resultArray = (Rft[]) ((RftFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM693259
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return resultArray;
	}

	//#CM693260
	// Package methods -----------------------------------------------

	//#CM693261
	// Protected methods ---------------------------------------------

	//#CM693262
	// Private methods -----------------------------------------------
	//#CM693263
	/**
	 * Allow this method to obtain the search conditions from parameter and search for the RFT Control info.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	private void find(SystemParameter param) throws Exception
	{
		int count = 0;

		RftSearchKey rftKey = new RftSearchKey();
		//#CM693264
		// Set the search conditions.
		if(!StringUtil.isBlank(param.getRftNo()))
		{
			rftKey.setRftNo(param.getRftNo());
		}
		rftKey.setRftNoOrder(1, true);

		wFinder = new RftFinder(wConn);
		//#CM693265
		// Open the cursor.
		wFinder.open();
		//#CM693266
		// Execute the search.
		count = ((RftFinder) wFinder).search(rftKey);
		//#CM693267
		// Initialize.
		wLength = count;
		wCurrent = 0;
	}

}
//#CM693268
//end of class

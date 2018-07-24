// $Id: SessionSystemShortageInfoConsignorRet.java,v 1.2 2006/11/13 08:21:02 suresh Exp $

//#CM693269
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693270
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to search for the Consignor List through the shortage info and display it. <BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes. <BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionSystemShortageInfoConsignorRet(Connection conn, SystemControlParameter param)</CODE>method) <BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default. <BR>
 *   Invoke the <CODE>find(SystemParameter param)</CODE> method and search for the shortage info. <BR>
 * <BR>
 *   <DIR>
 *   <Search conditions> <BR>
 *     <DIR>
 *     Added Date/Time <BR>
 *     Consignor Code <BR>
 *     </DIR>
 *   <Search table> <BR>
 *     <DIR>
 *     DnShortageInfo <BR>
 *     </DIR>
 *   <Field items to be extracted> <BR>
 *     <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Process for displaying(<CODE>getEntities()</CODE>method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 * <BR>
 *   1.Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the array of the shortage info and return it. <BR>
 * <BR>
 *   <Field items to displayed>
 *   <DIR>
 *     Consignor Code <BR>
 *     Consignor Name <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/22</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:02 $
 * @author  $Author: suresh $
 */
public class SessionSystemShortageInfoConsignorRet extends SessionRet
{
	//#CM693271
	// Class fields --------------------------------------------------

	//#CM693272
	// Class variables -----------------------------------------------

	//#CM693273
	// Class method --------------------------------------------------
	//#CM693274
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $, $Date: 2006/11/13 08:21:02 $");
	}

	//#CM693275
	// Constructors --------------------------------------------------
	//#CM693276
	/**
	 * Invoke the <CODE>find(StockControlParameter param)</CODE> method for searching. <BR>
	 * Allow the <CODE>find(StockControlParameter param)</CODE> method to set the count of obtained data. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * <BR>
	 * <DIR>
	 * <Search conditions> <BR>
	 *   <DIR>
	 *   Added Date/Time <BR>
	 *   Consignor Code <BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <CODE>Connection</CODE>
	 * @param param      Parameter that contains the <CODE>StockControlParameter</CODE> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionSystemShortageInfoConsignorRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM693277
		// Search
		find(param);
	}

	//#CM693278
	// Public methods ------------------------------------------------
	//#CM693279
	/**
	 * Return the designated number of search results of the shortage info <CODE>(DnShortageInfo)</CODE>.<BR>
	 * <BR>
	 * <DIR>
	 * <Search result> <BR>
	 * <DIR>
	 * Consignor Code <BR>
	 * Consignor Name <BR>
	 * </DIR>
	 * </DIR>
	 * @return Array of search results of shortage info<CODE>(DnShortageInfo)</CODE>.
	 */
	public ShortageInfo[] getEntities()
	{
		ShortageInfo[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ShortageInfo[]) ((ShortageInfoFinder)wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM693280
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM693281
	// Package methods -----------------------------------------------

	//#CM693282
	// Protected methods ---------------------------------------------

	//#CM693283
	// Private methods -----------------------------------------------
	//#CM693284
	/** 
	 * Print the SQL statement based on the input parameter and search for the shortage info<CODE>(DnShortageInfo)</CODE>. <BR>
	 * Maintain the <CODE>ShortageFinder</CODE> that executes searching as an instance variable. <BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	private void find(SystemParameter param) throws Exception
	{
		//#CM693285
		// Obtain the Batch No. from the added date/time and the consignor code.
		String batchNo = getBatchNo(param);

		//#CM693286
		// Set the search conditions.
		ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();
		
		//#CM693287
		// Batch No.
		shortageKey.setBatchNo(batchNo);
		
		//#CM693288
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			shortageKey.setConsignorCode(param.getConsignorCode());
		}
		
		shortageKey.setConsignorCodeGroup(1);
		shortageKey.setConsignorNameGroup(2);
		shortageKey.setConsignorCodeCollect("");
		shortageKey.setConsignorNameCollect("");
		shortageKey.setConsignorCodeOrder(1, true);
		shortageKey.setConsignorNameOrder(2, true);
		
		wFinder = new ShortageInfoFinder(wConn);
		//#CM693289
		// Open the cursor.
		wFinder.open();
		int count = ((ShortageInfoFinder)wFinder).search(shortageKey);
		//#CM693290
		// Initialize.
		wLength = count;
		wCurrent = 0;
	}

	//#CM693291
	/**
	 * Allow this method to obtain the Batch No. from the added date/time and the consignor code.<BR>
	 * @param  param  Parameter
	 * @return String Batch No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private String getBatchNo(SystemParameter param) throws ReadWriteException
	{
		try 
		{
			//#CM693292
			// Set the search conditions.
			ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();
		
			//#CM693293
			// Added date
			if (!StringUtil.isBlank(param.getRegistDate()))
			{
				shortageKey.setRegistDate(param.getRegistDate());
			}
			//#CM693294
			// Consignor Code
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				shortageKey.setConsignorCode(param.getConsignorCode());
			}
		
			//#CM693295
			// Group the data by Batch No.
			shortageKey.setBatchNoGroup(1);
			shortageKey.setBatchNoCollect();
		
			//#CM693296
			// Execute searching.
			ShortageInfoHandler shortageHandler = new ShortageInfoHandler(wConn);
			ShortageInfo result = (ShortageInfo)shortageHandler.findPrimary(shortageKey);

			//#CM693297
			// Obtain the Batch No. from the search result and return it.
			if (result != null)
			{
				return result.getBatchNo();
			}
			else
			{
				return "";
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

}
//#CM693298
//end of class

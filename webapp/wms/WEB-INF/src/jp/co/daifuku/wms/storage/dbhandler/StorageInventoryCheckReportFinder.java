//#CM566455
//$Id: StorageInventoryCheckReportFinder.java,v 1.2 2006/12/07 08:56:14 suresh Exp $

package jp.co.daifuku.wms.storage.dbhandler;

//#CM566456
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckReportFinder;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM566457
/**
 * The class to retrieve the DnInventoryCheck table of the database and to do the mapping to InventoryCheck. <BR>
 * Use this class when you print the report. <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/07</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:56:14 $
 * @author  $Author: suresh $
 */
public class StorageInventoryCheckReportFinder extends InventoryCheckReportFinder
{
	//#CM566458
	// Class filelds -----------------------------------------------

	//#CM566459
	// Class method --------------------------------------------------
	//#CM566460
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:56:14 $");
	}

	//#CM566461
	// Constructors --------------------------------------------------
	//#CM566462
	/**
	 * Generate the instance specifying <code>Connection</code> for the database connection. 
	 * @param conn Connection Connection to database
	 */
	public StorageInventoryCheckReportFinder(Connection conn)
	{
		super(conn);
	}

	//#CM566463
	// Public methods ------------------------------------------------
	//#CM566464
	/**
	 * Retrieve InventoryCheck and acquire it. <BR>
	 * Change the retrieval method according to display type (pType). <BR>
	 * @param key SearchKey Key for retrieval
	 * @param pType String Retrieval type(Status)
	 * @return Number of retrieval results
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 */
	public int search(SearchKey key, String pType) throws ReadWriteException
	{
		close();
		isNextFlag = true;
		open();
		Object[] fmtObj = new Object[4];
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret = null;
		StringBuffer strbuffer = new StringBuffer();

		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " + "FROM DNINVENTORYCHECK " + "{1} {2} {3}";

			String fmtSQL = "SELECT {0} " + "FROM DNINVENTORYCHECK " + "{1} {2} {3}";

			//#CM566465
			// Edit the acquisition condition. 
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();
			}
			else
			{
				//#CM566466
				// It is assumed all item acquisition in the acquisition condition at a specified doing. 
				fmtObj[0] = " * ";
			}
			//#CM566467
			// Edit the acquisition condition. 
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				//#CM566468
				// It is assumed all item acquisition in the acquisition condition at a specified doing. 
				cntObj[0] = " * ";
			}

			//#CM566469
			// Edit the search condition. 
			if (key.getReferenceCondition() != null)
			{
				strbuffer.append("WHERE " + key.getReferenceCondition());
			}
			//#CM566470
			// When the display differs
			if(pType.equals(StorageSupportParameter.DISP_STATUS_DIFFERENCE))
			{
				strbuffer.append(" AND STOCK_QTY != RESULT_STOCK_QTY ");
			}
			//#CM566471
			// When the display is same
			else if(pType.equals(StorageSupportParameter.DISP_STATUS_EQUAL))
			{
				strbuffer.append(" AND STOCK_QTY = RESULT_STOCK_QTY ");
			}
			fmtObj[1] = strbuffer;
			cntObj[1] = strbuffer;

			//#CM566472
			// Edit the consolidating condition. 			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			//#CM566473
			// Edit the order of reading. 				
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = "ORDER BY " + key.getSortCondition();
				cntObj[3] = "ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj);
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			p_ResultSet = p_Statement.executeQuery(sqlstring);

		}
		catch (SQLException e)
		{
			//#CM566474
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			throw (new ReadWriteException("6006002" + wDelim + "DnInventoryCheck"));
		}
		return count;
	}

	//#CM566475
	// Package methods -----------------------------------------------

	//#CM566476
	// Protected methods ---------------------------------------------

	//#CM566477
	// Private methods -----------------------------------------------

}
//#CM566478
//end of class
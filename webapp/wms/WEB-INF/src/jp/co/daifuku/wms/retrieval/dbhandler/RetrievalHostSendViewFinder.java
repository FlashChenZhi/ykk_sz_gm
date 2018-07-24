//#CM709011
//$Id: RetrievalHostSendViewFinder.java,v 1.3 2007/02/07 04:16:12 suresh Exp $
package jp.co.daifuku.wms.retrieval.dbhandler ;

//#CM709012
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewReportFinder;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalHostSendViewSearchKey;

//#CM709013
/**
 * Allow this class to search Dvhostsendview table in database and map it in the HostSendView class.
 * Use this class to display the search result view.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:12 $
 * @author  $Author: suresh $
 */
public class RetrievalHostSendViewFinder extends HostSendViewReportFinder
{
	//#CM709014
	// Class filelds ------------------------------------------------

	//#CM709015
	// Class methods ------------------------------------------------
	//#CM709016
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:12 $") ;
	}

	//#CM709017
	// Class valiables -----------------------------------------------
	
	//#CM709018
	// Constructors --------------------------------------------------
	//#CM709019
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalHostSendViewFinder(Connection conn)
	{
		super(conn);
	}

	//#CM709020
	// Public methods ------------------------------------------------

	//#CM709021
	/**
	 * Search through the Dvhostsendview and obtain the result.
	 * @param key Key for search
	 * @return Count of the search results
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public int search(RetrievalHostSendViewSearchKey key, String sql) throws ReadWriteException
	{
		close();
		open();
		Object[]  fmtObj = new Object[5];
		Object[]  cntObj = new Object[5];
		int count = 0;
		ResultSet countret  = null ;

	 	try
	 	{
						
			String fmtCountSQL = "SELECT COUNT({0}) COUNT FROM DVHOSTSENDVIEW {1} {2} {3} {4} ";

			String fmtSQL = "SELECT DISTINCT {0} ";
			fmtSQL += "FROM DVHOSTSENDVIEW " + "{1} {2} {3} {4}";

			//#CM709022
			// 	Compile the conditions for obtaining data. (for COUNT) 
			//#CM709023
			// Obtain all field items.
			cntObj[0] = " * " ;

			//#CM709024
			// Compile the conditions for obtaining data.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
				fmtObj[0] = fmtObj[0] + ", " + key.ReferenceJoinColumns() ;
			}
			else
			{
				//#CM709025
				// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
				fmtObj[0] = " * " ;
			}

			//#CM709026
			// Compile the combined table.
			if(!StringUtil.isBlank(key.getJoinTable()))
			{
				if(StringUtil.isBlank(sql))
				{
					fmtObj[1] = ", " + key.getJoinTable();
					cntObj[1] = ", " + key.getJoinTable();
				}
				else
				{
					fmtObj[1] = ", ( " + sql + " ) " + key.getJoinTable();
					cntObj[1] = ", ( " + sql + " ) " + key.getJoinTable();
				}
			}

			fmtObj[2] = "";
			//#CM709027
			// Compile the search conditions.			
			if (key.getReferenceCondition() != null)
			{
				fmtObj[2] = "WHERE " + key.getReferenceCondition();
				cntObj[2] = "WHERE " + key.getReferenceCondition();
			}

			//#CM709028
			// Compile the condition for compiling.
			if (key.ReferenceJoinWhere() != null)
			{
				if(StringUtil.isBlank(fmtObj[2].toString()))
				{
					fmtObj[2] = " WHERE " + key.ReferenceJoinWhere();
					cntObj[2] = " WHERE " + key.ReferenceJoinWhere();
				}
				else
				{
					fmtObj[2] = fmtObj[2] + " AND " + key.ReferenceJoinWhere();
					cntObj[2] = cntObj[2] + " AND " + key.ReferenceJoinWhere();
				}
			}

			//#CM709029
			// Compile the aggregation conditions,
			if (key.getGroupCondition() != null)
			{
				fmtObj[3] = " GROUP BY " + key.getGroupCondition();
				cntObj[3] = " GROUP BY " + key.getGroupCondition();
			}

			//#CM709030
			// Compile the loading sequence.
			if (key.getSortCondition() != null)
			{
				fmtObj[4] = " ORDER BY " + key.getSortCondition();
				cntObj[4] = " ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", "RetrievalHostSendView Finder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM709031
			//Execute the search only when one or more data found.
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "RetrievalHostSendView Finder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM709032
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "RetrievalHostSendViewFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DvHostSendView")) ;
		}
		return count;
	}

	//#CM709033
	/**
	 * Generate a sql to search through the Dvhostsendview.
	 * @param key Key for search
	 * @return Generated SQL string
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public String createFindSql(SearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		Object[] fmtObj = new Object[4];

		String fmtSQL = "SELECT {0} ";
		fmtSQL += "FROM DVHOSTSENDVIEW " + "{1} {2} {3}";

		//#CM709034
		// Compile the conditions for obtaining data.
		if (key.getCollectCondition() != null)
		{
			fmtObj[0] = key.getCollectCondition() ;
		} else {
			//#CM709035
			// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
			fmtObj[0] = " * " ;
		}

		//#CM709036
		// Compile the search conditions.
		if (key.getReferenceCondition() != null)
		{
			fmtObj[1] = " WHERE " + key.getReferenceCondition();
		}

		//#CM709037
		// Compile the aggregation conditions,
		if (key.getGroupCondition() != null)
		{
			fmtObj[2] = " GROUP BY " + key.getGroupCondition();
		}

		//#CM709038
		// Compile the loading sequence.
		if (key.getSortCondition() != null)
		{
			fmtObj[3] = " ORDER BY " + key.getSortCondition();
		}
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

		return sqlstring;
	}

	//#CM709039
	// Package methods -----------------------------------------------

	//#CM709040
	// Protected methods ---------------------------------------------

	//#CM709041
	// Private methods -----------------------------------------------

}
//#CM709042
//end of class


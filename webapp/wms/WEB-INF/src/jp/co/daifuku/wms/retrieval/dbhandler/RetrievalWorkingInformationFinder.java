package jp.co.daifuku.wms.retrieval.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM709178
/**
 * Search through the Work Status (DnWorkInfo).
 * Describe the SQL statement not supported by WorkingInformationFinder.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/20</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:10 $
 * @author  $Author: suresh $
 */
public class RetrievalWorkingInformationFinder extends WorkingInformationFinder
{
	//#CM709179
	// Class feilds ------------------------------------------------
	//#CM709180
	// Class variables -----------------------------------------------
	//#CM709181
	// Class methods --------------------------------------------------
	//#CM709182
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:10 $");
	}

	//#CM709183
	// Constructors --------------------------------------------------
	//#CM709184
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalWorkingInformationFinder(Connection conn)
	{
		super(conn);
	}

	//#CM709185
	/**
	 * Search through the DnWorkInfoCarryInfo for data possible to cancel the allocation, and obtain the search result.
	 * @param key Key for search
	 * @param ckey Key for search (for CarryInformation)
	 * @return Count of the search results
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public int searchAllocClear(SearchKey key, SearchKey ckey) throws ReadWriteException
	{
		setTableName("DNWORKINFO, CARRYINFO");

		Object[]  fmtObj = new Object[4];
		Object[]  cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;

		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3}";
				
			//#CM709186
			// Compile the conditions for obtaining data.
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();

				if (ckey.getCollectConditionForCount() != null)
				{
					cntObj[0] = cntObj[0] + ckey.getCollectConditionForCount();
				}
			}
			else
			{
				if (ckey.getCollectConditionForCount() != null)
				{
					cntObj[0] = ckey.getCollectConditionForCount();
				}
				else
				{
					//#CM709187
					// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
					cntObj[0] = " * ";
				}
			}
			
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();

				if (ckey.getCollectCondition() != null)
				{
					fmtObj[0] = fmtObj[0] + ckey.getCollectCondition();
				}
			}
			else
			{
				if (ckey.getCollectCondition() != null)
				{
					fmtObj[0] = ckey.getCollectCondition();
				}
				else
				{
					//#CM709188
					// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
					fmtObj[0] = " * ";
				}
			}
			
			//#CM709189
			// Compile the search conditions.
			fmtObj[1] = "WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY(+)" ;
			fmtObj[1] = fmtObj[1] + " AND ((DNWORKINFO.SYSTEM_DISC_KEY != "  + SystemDefine.SYSTEM_DISC_KEY_ASRS;
			fmtObj[1] = fmtObj[1] + " AND DNWORKINFO.STATUS_FLAG = "  + SystemDefine.STATUS_FLAG_UNSTART + ")";
			fmtObj[1] = fmtObj[1] + " OR (DNWORKINFO.SYSTEM_DISC_KEY = "  + SystemDefine.SYSTEM_DISC_KEY_ASRS;
			fmtObj[1] = fmtObj[1] + " AND DNWORKINFO.STATUS_FLAG = "  + SystemDefine.STATUS_FLAG_NOWWORKING;
			fmtObj[1] = fmtObj[1] + " AND CARRYINFO.CMDSTATUS < "  + CarryInformation.CMDSTATUS_WAIT_RESPONSE + ")) ";
			cntObj[1] = "WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY(+)" ;
			cntObj[1] = cntObj[1] + " AND ((DNWORKINFO.SYSTEM_DISC_KEY != "  + SystemDefine.SYSTEM_DISC_KEY_ASRS;
			cntObj[1] = cntObj[1] + " AND DNWORKINFO.STATUS_FLAG = "  + SystemDefine.STATUS_FLAG_UNSTART + ")";
			cntObj[1] = cntObj[1] + " OR (DNWORKINFO.SYSTEM_DISC_KEY = "  + SystemDefine.SYSTEM_DISC_KEY_ASRS;
			cntObj[1] = cntObj[1] + " AND DNWORKINFO.STATUS_FLAG = "  + SystemDefine.STATUS_FLAG_NOWWORKING;
			cntObj[1] = cntObj[1] + " AND CARRYINFO.CMDSTATUS < "  + CarryInformation.CMDSTATUS_WAIT_RESPONSE + ")) ";

			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + "AND " + key.getReferenceCondition();
				cntObj[1] = cntObj[1] + "AND " + key.getReferenceCondition();

				if (ckey.getReferenceCondition() != null)
				{
					fmtObj[1] = fmtObj[1] + " AND " + ckey.getReferenceCondition();
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}
			else
			{
				if (ckey.getReferenceCondition() != null)
				{
					fmtObj[1] = fmtObj[1] + " AND " + ckey.getReferenceCondition();
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}

			//#CM709190
			// Compile the aggregation conditions,			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();

				if (ckey.getGroupCondition() != null)
				{
					fmtObj[2] = fmtObj[2] + ", " + ckey.getGroupCondition();
					cntObj[2] = cntObj[2] + ", " + ckey.getGroupCondition();
				}
			}
			else
			{
				if (ckey.getGroupCondition() != null)
				{
					fmtObj[2] = " GROUP BY " + ckey.getGroupCondition();
					cntObj[2] = " GROUP BY " + ckey.getGroupCondition();
				}
			}

			//#CM709191
			// Compile the loading sequence.				
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition();
				cntObj[3] = " ORDER BY " + key.getSortCondition();

				if (ckey.getSortCondition() != null)
				{
					fmtObj[3] = fmtObj[3] + ", " + ckey.getSortCondition();
					cntObj[3] = cntObj[3] + ", " + ckey.getSortCondition();
				}
			}
			else
			{
				if (ckey.getSortCondition() != null)
				{
					fmtObj[3] = " ORDER BY " + ckey.getSortCondition();
					cntObj[3] = " ORDER BY " + ckey.getSortCondition();
				}
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("FINDER", "RetrievalPlanOrderNoFinder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM709192
			//Execute the search only when one or more data found.
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("FINDER", "RetrievalPlanOrderNoFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
			}
			else
			{
				p_ResultSet = null;
			}
		}
		catch (SQLException e)
		{
			//#CM709193
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName()) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		return count;
	}
}

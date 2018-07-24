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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM709252
/**
 * Search through the Work Status (DnWorkInfo).
 * Describe the SQL statement not supported by WorkingInformationReportFinder.
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>New creation</b></td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2007/02/07 04:16:09 $
 * @author  C.Kaminishizono
 * @author  Last commit: $Author: suresh $
 */
public class RetrievalWorkingInformationReportFinder extends WorkingInformationReportFinder
{
	//#CM709253
	// Class feilds ---------------------------------------------------
	//#CM709254
	// Class variables ------------------------------------------------
	//#CM709255
	// Class methods --------------------------------------------------
	//#CM709256
	/**
	 * Return the revision of this class.
	 * @return String of revision
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:09 $") ;
	}
	
	//#CM709257
	// Constructors --------------------------------------------------
	//#CM709258
	/**
	 * Designate the database connection and generate an instance.
	 * @param conn Connection already connected to database
	 */
	public RetrievalWorkingInformationReportFinder(Connection conn)
	{
		super(conn);
	}

	//#CM709259
	// Public methods ------------------------------------------------
	//#CM709260
	/**
	 * Search for data based on the parameter through database, and return the search result possible to cancel the allocation.
	 * @param key Key for search
	 * @param ckey Key for search (for CarryInformation)
	 * @return Count of the search results
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public int searchAllocClear(SearchKey key, SearchKey ckey) throws ReadWriteException
	{
		close();
		open();
		
		setTableName("DNWORKINFO, CARRYINFO");

		Object[] fmtObj = new Object[4];
		Object[] cntObj = new Object[4];
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
				
			//#CM709261
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
					//#CM709262
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
					//#CM709263
					// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
					fmtObj[0] = " * ";
				}
			}
					
			//#CM709264
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

			//#CM709265
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

			//#CM709266
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
DEBUG.MSG("HANDLER", getTableName() + " RetrievalWorkingInformationReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM709267
			//Execute the search only when one or more data found.
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RetrievalWorkingInformationReportFinder SQL[" + sqlstring + "]") ;
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
			//#CM709268
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}

		return count;
	}

	//#CM709269
	/**
	 * Search through the DnWorkInfoCarryInfo for data possible to cancel the allocation, and obtain the search result.
	 * @param key Key for search
	 * @param ckey Key for search (for CarryInformation)
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int count(SearchKey key, SearchKey ckey) throws ReadWriteException
	{
		close();
		open();
		setTableName("DNWORKINFO, CARRYINFO");

		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			//#CM709270
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
					//#CM709271
					// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
					cntObj[0] = " * ";
				}
			}
			
			//#CM709272
			// Compile the search conditions.
			cntObj[1] = " WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;

			if (key.getReferenceCondition() != null)
			{
				cntObj[1] = cntObj[1] + " AND " + key.getReferenceCondition();

				if (ckey.getReferenceCondition() != null)
				{
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}
			else
			{
				if (ckey.getReferenceCondition() != null)
				{
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}

			//#CM709273
			// Compile the aggregation conditions,			
			if (ckey.getGroupCondition() != null)
			{
				cntObj[2] = " GROUP BY " + ckey.getGroupCondition();

				if (key.getGroupCondition() != null)
				{
					cntObj[2] = cntObj[2] + ", " + key.getGroupCondition();
				}
			}
			else
			{
				if (key.getGroupCondition() != null)
				{
					cntObj[2] = " GROUP BY " + key.getGroupCondition();
				}
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RetrievalWorkingInformationReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
		}
		catch (SQLException e)
		{
			//#CM709274
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		return count;
	}
}

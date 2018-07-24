//#CM699788
//$Id: SystemResultViewReportFinder.java,v 1.2 2006/10/30 06:39:18 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;
//#CM699789
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
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.Result;

//#CM699790
/**
 * <CODE>MasterResultFinder</CODE> class allows to operate the database (table).
 * Use this class to search for the data linked to the DVRESULTVIEW and the Master.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:18 $
 * @author  $Author: suresh $
 */
public class SystemResultViewReportFinder extends ResultViewReportFinder
{
	//#CM699791
	// Class variables -----------------------------------------------

	//#CM699792
	// Constructors --------------------------------------------------
	//#CM699793
	/**
	 * Designate the database connection and generate an instance.
	 * @param conn Connection already connected to database
	 */
	public SystemResultViewReportFinder(Connection conn)
	{
		super(conn);
	}

	//#CM699794
	/**
	 * Return the revision of this class.
	 * @return String of revision
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:18 $") ;
	}

	//#CM699795
	/**
	 * Initialize the AbstractEntity.
	 * @return AbstractEntity
	 */
	protected AbstractEntity createEntity()
	{
		return (new Result());
	}

	//#CM699796
	// Public methods ------------------------------------------------
	//#CM699797
	/**
	 * Combine DVRESULTVIEW table and Master table and search for the result info.
	 * Construct the SQL condition using MasterConditions.
	 * @param key				Key for search
	 * @param tableName		Table that combines with DVRESULTVIEW
	 * @param relationalKey	Key that combines with DVRESULTVIEW
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int searchMaster(SearchKey key, String tableName, String[] relationalKey)	throws ReadWriteException
	{
		//#CM699798
		// Obtain the SQL that connects the DVRESULTVIEW and the Master together.
		String sql = MasterConditions(relationalKey);
		
		return search(key, tableName, sql);
	}

	//#CM699799
	/**
	 * Generate a search SQL using DVRESULTVIEW and Parameter keys as a condition for combining.
	 * @param relarionalKey Key that combines with DVRESULTVIEW
	 * @return Statement of search conditions
	 */
	private String MasterConditions(String[] relarionalKey)
	{
		String conditions = "WHERE ";
		
		//#CM699800
		// Connect all data corresponding to the key with DNRTESULT.
		for(int i = 0; i < relarionalKey.length; i++)
		{
			if(i != 0)
			{
				conditions = conditions + " AND ";
			}
			conditions = conditions + "DVRESULTVIEW." + relarionalKey[i] + " = " + "MST." + relarionalKey[i];  
		}
		
		return conditions;
	}

	//#CM699801
	/**
	 * Combine DVRESULTVIEW table and Master table and search for the result info corresponding to the combined SQL.
	 * @param key				Key for search
	 * @param tableName		Table that combines with DVRESULTVIEW
	 * @param whereSql			SQL that combines with DVRESULTVIEW
	 * @return Count of the search results
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public int search(SearchKey key, String tableName, String whereSql) throws ReadWriteException
	{
		close();
		open();

		Object[] fmtObj = new Object[4] ;
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			//#CM699802
			// Set the Table name.
			setTableName("DVRESULTVIEW, " + tableName);

			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " MST "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " MST "
				+ "{1} {2} {3}";

			//#CM699803
			// Compile the conditions for obtaining data.
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				//#CM699804
				// Obtain all the field items if the Conditions for Obtaining Data is None.
				cntObj[0] = " * ";
			}
			
			//#CM699805
			// Compile the conditions for obtaining data.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			} else {
				//#CM699806
				// Obtain all the field items if the Conditions for Obtaining Data is None.
				fmtObj[0] = " * " ;
			}

			//#CM699807
			// Compile the search conditions.
			//#CM699808
			// Conditions to connect the DVRESULTVIEW and the Master together.
			fmtObj[1] = whereSql;
			cntObj[1] = whereSql;
			//#CM699809
			// Search conditions for DVRESULTVIEW
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + " AND " + key.getReferenceCondition();
				cntObj[1] = cntObj[1] + " AND " + key.getReferenceCondition();
			}

			//#CM699810
			// Compile the aggregation conditions,			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			//#CM699811
			// Compile the loading sequence.				
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = "ORDER BY " + key.getSortCondition();
				cntObj[3] = "ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM699812
			//Execute the search only when one or more data found.
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
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
			//#CM699813
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		return count;
	}

	//#CM699814
	// Private methods ------------------------------------------------

}
//#CM699815
//end of class

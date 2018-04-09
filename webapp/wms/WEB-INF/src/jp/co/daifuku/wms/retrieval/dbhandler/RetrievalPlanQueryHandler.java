//#CM709105
//$Id: RetrievalPlanQueryHandler.java,v 1.3 2007/02/07 04:16:11 suresh Exp $
package jp.co.daifuku.wms.retrieval.dbhandler;

//#CM709106
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

//#CM709107
/**
 * Allow this class to search for the Picking Plan Info.
 * Describe here the process not supported by RetrievalPlanHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/03/31</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:11 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanQueryHandler extends RetrievalPlanHandler
{
	//#CM709108
	// Class feilds --------------------------------------------------

	//#CM709109
	// Class variables -----------------------------------------------
	
	//#CM709110
	// Class method --------------------------------------------------
	//#CM709111
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:11 $");
	}

	//#CM709112
	// Constructors --------------------------------------------------
	//#CM709113
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalPlanQueryHandler(Connection conn)
	{
		super(conn);
	}

	//#CM709114
	// Public methods ------------------------------------------------
	//#CM709115
	/**
	 * Search through the DnRetrievalPlan and obtain the result as an array of Entity.
	 * @param key Key for search
	 * @return Array of the generated Entity
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public RetrievalPlan[] find(RetrievalPlanSearchKey key)
			throws ReadWriteException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;
		RetrievalPlan[] entity = null ;

		try
		{
			stmt = getConnection().createStatement() ;

			String fmtSQL = "SELECT {0} FROM " + getTableName() + " {1} {2} {3}" ;

			//#CM709116
			// Compile the conditions for obtaining data.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM709117
				// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
				fmtObj[0] = " * " ;
			}

			//#CM709118
			// Compile the search conditions.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}

			//#CM709119
			// Compile the aggregation conditions,
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition() ;
			}

			//#CM709120
			// Compile the loading sequence.
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition() ;
			}
			else
			{
				//#CM709121
				// Disable to determine whether to sort in the order of Case Order or Piece Order, if nothing is designated. 
				//#CM709122
				// Therefore, if null, translate it into "''" to connect them, and obtain in the ascending order.
				fmtObj[3] = " ORDER BY HOST_COLLECT_BATCHNO, NVL(CASE_ORDER_NO, '') || NVL(PIECE_ORDER_NO, ''), CASE_PIECE_FLAG, CUSTOMER_CODE";
			}
			
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " FIND SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = (RetrievalPlan[]) convertEntities(rset, key, 0) ;
		}
		catch (SQLException e)
		{
			//#CM709123
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM709124
			// Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM709125
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM709126
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
			}
		}
		return entity ;
	}


	//#CM709127
	// Package methods -----------------------------------------------

	//#CM709128
	// Protected methods ---------------------------------------------

	//#CM709129
	// Private methods -----------------------------------------------

}
//#CM709130
//end of class

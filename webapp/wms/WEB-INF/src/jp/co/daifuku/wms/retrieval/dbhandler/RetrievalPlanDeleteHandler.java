//#CM709075
//$Id: RetrievalPlanDeleteHandler.java,v 1.3 2007/02/07 04:16:11 suresh Exp $
package jp.co.daifuku.wms.retrieval.dbhandler;

//#CM709076
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
import java.sql.Timestamp;
import java.util.Vector;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.retrieval.entity.RetrievalPlanDelete;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709077
/**
 * Allow this class to operate Picking Plan Info (DNRETRIEVALPLAN) in the process for deleting Picking Plan data.
 * Describe the SQL statement not supported by RetrievalPlanHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/20</TD><TD>T.Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:16:11 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanDeleteHandler extends RetrievalPlanHandler
{
	//#CM709078
	// Class feilds ------------------------------------------------
	//#CM709079
	/**
	 * Connection instance for database connection.
	 * Disable to control any transaction in this class.
	 */
	protected Connection wConn;
	private static final String ITEMORDERFLAG		= 
				"(DECODE(PIECE_ORDER_NO, NULL, (" +
				"DECODE(CASE_ORDER_NO,  NULL, '" +
				RetrievalSupportParameter.ITEMORDERFLAG_ITEM + "', '" +
				RetrievalSupportParameter.ITEMORDERFLAG_ORDER + "')), '" +
				RetrievalSupportParameter.ITEMORDERFLAG_ORDER + "')) ITEM_ORDER_FLAG";
	private static final String ITEMORDERFLAG_GROUP		= 
				"(DECODE(PIECE_ORDER_NO, NULL, (" +
				"DECODE(CASE_ORDER_NO,  NULL, '" +
				RetrievalSupportParameter.ITEMORDERFLAG_ITEM + "', '" +
				RetrievalSupportParameter.ITEMORDERFLAG_ORDER + "')), '" +
				RetrievalSupportParameter.ITEMORDERFLAG_ORDER + "'))";

	//#CM709080
	// Class variables -----------------------------------------------

	//#CM709081
	// Class method --------------------------------------------------
	//#CM709082
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:11 $");
	}

	//#CM709083
	// Constructors --------------------------------------------------
	//#CM709084
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalPlanDeleteHandler(Connection conn)
	{
		super(conn);
	}

	//#CM709085
	// Public methods ------------------------------------------------
	//#CM709086
	/**
	 * Set the Set a <code>Connection</code> for database connection.
	 * @param conn Connection to be set
	 */
	public void setConnection(Connection conn)
	{
		wConn = conn;
	}

	//#CM709087
	/**
	 * Obtain the <code>Connection</code> for database connection.
	 * @return Currently maintaining <code>Connection</code>
	 */
	public Connection getConnection()
	{
		return (wConn);
	}

	//#CM709088
	/**
	 * Search through the DnRetrievalPlan and obtain the result as an array of Entity.
	 * @param key Key for search
	 * @return Array of the generated Entity
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public Entity[] find(SearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[4];
		RetrievalPlanDelete[] RetrievalPlanDelete = null;

		try
		{
			stmt = wConn.createStatement();

			String fmtSQL = "SELECT {0} ";
			fmtSQL += "FROM DNRETRIEVALPLAN " + "{1} {2} {3}";

			//#CM709089
			// Compile the conditions for obtaining data.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() + ", " + ITEMORDERFLAG ;
			} else {
				//#CM709090
				// Obtain all the field items if nothing is designated for the Conditions for Obtaining Data is None.
				fmtObj[0] = " * " ;
			}

			//#CM709091
			// Compile the search conditions.			
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition();
			}

			//#CM709092
			// Compile the aggregation conditions,			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition() + ", " + ITEMORDERFLAG_GROUP;
			}
			
			//#CM709093
			// Compile the loading sequence.
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition();
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
DEBUG.MSG("HANDLER", "RetrievalPlanDeleteHandler Handler SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring);
			RetrievalPlanDelete = convertRetrievalPlanDelete(rset, key);
		}
		catch (SQLException e)
		{
			//#CM709094
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "RetrievalPlanDeleteHandler");
			//#CM709095
			//Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + "DnRetrievalPlan"));
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch (SQLException e)
			{
				//#CM709096
				//6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), "RetrievalPlanDeleteHandler");
				//#CM709097
				//Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnRetrievalPlan"));
			}
		}
		return RetrievalPlanDelete;
	}
	//#CM709098
	/**
	 * Map the result set.
	 * @param rset <CODE>ResultSet</CODE> search result
	 * @param wSkey <CODE>SearchKey</CODE> search conditions
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	protected RetrievalPlanDelete[] convertRetrievalPlanDelete(ResultSet rset, SearchKey wSkey) throws ReadWriteException
	{
		Vector	vec = new Vector();

		Timestamp tims = null; 
		RetrievalPlanDelete[] RetrievalPlanDelete = null;

		boolean wAllflag = false ;
		
		try
		{
			//#CM709099
			// Determine for presence of any conditions for obtaining data.
			//#CM709100
			// Obtain all the field items if the conditions for obtaining data is not designated.
			
			String wStr = wSkey.getCollectCondition();
			if (wStr != null)
			{
				wAllflag = true ;
			}
			while (rset.next())
			{
				RetrievalPlanDelete vRetrievalPlan = new RetrievalPlanDelete() ;
				vRetrievalPlan.setPlanDate(DBFormat.replace(rset.getString("PLAN_DATE")));
				vRetrievalPlan.setConsignorCode(DBFormat.replace(rset.getString("CONSIGNOR_CODE")));
				vRetrievalPlan.setItemOrderFlag(DBFormat.replace(rset.getString("ITEM_ORDER_FLAG")));
				vec.addElement(vRetrievalPlan);
			}
			RetrievalPlanDelete = new RetrievalPlanDelete[vec.size()];
			vec.copyInto(RetrievalPlanDelete);
		}
		catch (SQLException e)
		{
			//#CM709101
			//6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "RetrievalPlanDeleteHandler");
			//#CM709102
			//Throw here the ReadWriteException with error message.
			//#CM709103
			//6006039=Failed to search for {0}. See log.
			throw (new ReadWriteException("6006039" + wDelim + "DnRetrievalPlan"));
		}

		return RetrievalPlanDelete;
	}
}
//#CM709104
//end of class

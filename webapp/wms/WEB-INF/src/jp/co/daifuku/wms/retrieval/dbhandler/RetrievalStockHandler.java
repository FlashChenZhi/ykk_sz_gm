//#CM709155
//$Id: RetrievalStockHandler.java,v 1.3 2007/02/07 04:16:10 suresh Exp $
package jp.co.daifuku.wms.retrieval.dbhandler;

//#CM709156
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
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM709157
/**
 * Allow this class to operate the inventory information (DmStock).
 * Describe the SQL statement not supported by StockHandler.
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
public class RetrievalStockHandler extends StockHandler
{
	//#CM709158
	// Class feilds ------------------------------------------------
	//#CM709159
	// Class variables -----------------------------------------------
	//#CM709160
	// Class methods --------------------------------------------------
	//#CM709161
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:16:10 $");
	}

	//#CM709162
	// Constructors --------------------------------------------------
	//#CM709163
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public RetrievalStockHandler(Connection conn)
	{
		super(conn);
	}

	//#CM709164
	// Public methods ------------------------------------------------
	//#CM709165
	/**
	 * Lock the corresponding record in the DMSTOCK table.
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * Use Consignor Code, and Item Code as keys.
	 * Use setlockPlanData method to construct the SQL condition.
	 * @param startParams search conditions
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce exception occurred in the connection with database as it is.
	 */
	public boolean lockPlanData(Parameter[] startParams ) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[2];
		String[] ukey = null;
		Vector vec = new Vector();
		String wPlanUkey = "";

		try
		{
			stmt = getConnection().createStatement();

			fmtObj[0] = setlockPlanData(startParams);

			String fmtSQL = "SELECT STOCK_ID FROM DMSTOCK DS"
			+ " WHERE {0} AND STATUS_FLAG ='" + Stock.STOCK_STATUSFLAG_OCCUPIED + "' FOR UPDATE ";

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			rset = stmt.executeQuery(sqlstring);
			if (rset == null)
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			//#CM709166
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM709167
			// Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
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
				//#CM709168
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM709169
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
		return true;
	}


	//#CM709170
	// Package methods -----------------------------------------------

	//#CM709171
	// Protected methods ---------------------------------------------

	//#CM709172
	// Private methods -----------------------------------------------
	//#CM709173
	/**
	 * Based on the obtained content of search condition (Parameter instance), generate a string array for SQL.
	 * @param  startParams search conditions
	 * @return Expression for searching to execute SQL
	 */
	private String setlockPlanData(Parameter[] startParams )
	{
		//#CM709174
		// Input information of the parameter
		RetrievalSupportParameter[] wParam = ( RetrievalSupportParameter[] )startParams;

		String conditionSQL = "( ";
		for (int i = 0;i < wParam.length; i++)
		{
			if (i == 0 )
				conditionSQL += " (";
			else
				conditionSQL += "OR (";
			
			//#CM709175
			// Consignor Code
			conditionSQL += "CONSIGNOR_CODE = '" + wParam[i].getConsignorCode() + "'";
			//#CM709176
			// Item Code
			conditionSQL += " AND " + "ITEM_CODE = '" + wParam[i].getItemCode() + "'";

			conditionSQL += ") ";
		}
		conditionSQL += " )";

		return conditionSQL;
	}

}
//#CM709177
//end of class

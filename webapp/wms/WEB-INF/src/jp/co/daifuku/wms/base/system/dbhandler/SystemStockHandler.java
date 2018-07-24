//#CM699853
//$Id: SystemStockHandler.java,v 1.2 2006/10/30 06:39:17 suresh Exp $
package jp.co.daifuku.wms.base.system.dbhandler;

//#CM699854
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.common.DEBUG;

//#CM699855
/**
 * Allow this class to operate the inventory information (Stock) using the system package.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/25</TD><TD>Yamashita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:39:17 $
 * @author  $Author: suresh $
 */
public class SystemStockHandler extends StockHandler
{
	//#CM699856
	// Class feilds ------------------------------------------------

	//#CM699857
	// Class variables -----------------------------------------------

	//#CM699858
	// Class method --------------------------------------------------
	//#CM699859
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:39:17 $");
	}

	//#CM699860
	// Constructors --------------------------------------------------
	//#CM699861
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn Connection for database connection 
	 */
	public SystemStockHandler(Connection conn)
	{
		super(conn);
	}

	//#CM699862
	// Public methods ------------------------------------------------
	//#CM699863
	/**
	 * Delete the DMSTOCK table using Stock ID as a key.
	 * Ensure to commit or rollback the transaction for applications that use this method.
	 * Use the Stock ID as a key.
	 * @param stockId Stock ID
	 * @return true: Normal,  false: Error
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws NotFoundException Announce if no info to be deleted is found.
	 */
	public boolean dropStock_MultiStockId(String[] stockId) throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;
		int count = 0;

		try
		{
			stmt = getConnection().createStatement();

			//#CM699864
			// Construction of IN operator
			String inSql = "";
			for( int i = 0 ; i < stockId.length ; i++ )
			{
				if( !StringUtil.isBlank(inSql) )
				{
					inSql = inSql + ", ";  
				}
				inSql = inSql + DBFormat.format(stockId[i]);
			}

			String dropSql = "DELETE FROM DMSTOCK " + 
								" WHERE STOCK_ID IN ( " + inSql + ")";
DEBUG.MSG("HANDLER", "SystemStock Handler DROP SQL[" + dropSql + "]") ;

			count = stmt.executeUpdate(dropSql);
			if (count == 0)
			{
				//#CM699865
				//6006006=There is no data to delete. Table Name: {0}
				Object[] tObj = new Object[1];
				tObj[0] = "DmStock";
				RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, "SystemStockHandler", tObj);
				throw (new NotFoundException("6006006" + wDelim + tObj[0]));
			}
		}
		catch (SQLException e)
		{
			//#CM699866
			// 6006002=Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			//#CM699867
			// Throw here the ReadWriteException with error message.
			throw (new ReadWriteException("6006002" + wDelim + "DmStock"));
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch (SQLException e)
			{
				//#CM699868
				// 6006002=Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				//#CM699869
				// Throw here the ReadWriteException with error message.
				throw (new ReadWriteException("6006002" + wDelim + "DmStock"));
			}
		}
		return true;
	}

	//#CM699870
	// Package methods -----------------------------------------------

	//#CM699871
	// Protected methods ---------------------------------------------

	//#CM699872
	// Private methods -----------------------------------------------
}
//#CM699873
//end of class

//$Id: WorkingInformationHandler.java,v 1.2 2006/11/09 07:50:14 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.SearchKey;

import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Database handler for WORKINFO
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- update history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:50:14 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkingInformationHandler
		extends AbstractDBHandler
{
	//------------------------------------------------------------
	// class variables (prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (prefix 'p_')
	//------------------------------------------------------------
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * Specify database connection and generate instance
	 * @param conn Database connection object
	 */
	public WorkingInformationHandler(Connection conn)
	{
		super(conn, WorkingInformation.TABLE_NAME) ;
	}


	//------------------------------------------------------------
	// public methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * DMSTOCK?,DNWORKINFO??????????????????????
	 * ???????????????????????????????commit???rollback??????
	 * SearchKey?ORDER BY???????????????
	 * @param key ??????Key
	 * @throws ReadWriteException ????????????????????????????
	 * @return ????????????true????????false?????
	 */
	public boolean lock(SearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		Object[] fmtObj = new Object[2];

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL = "SELECT {0} FROM DNWORKINFO,DMSTOCK"
			+ " WHERE  DNWORKINFO.STOCK_ID = DMSTOCK.STOCK_ID"
			+ "{1} FOR UPDATE ";

			// ??????????
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();
			}
			else
			{
				// ????????????????????
				fmtObj[0] = " * ";
			}

			// ??????????
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " AND " + key.getReferenceCondition();
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			return stmt.execute(sqlstring);
		}
		catch (SQLException e)
		{
			// 6006002 = ?????????????????{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			// ????ReadWriteException????????????throw???
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
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
				// 6006002 = ?????????????????{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				// ????ReadWriteException????????????throw???
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
	}

	/**
	 * DMSTOCK?,DNWORKINFO??????????????????????
	 * ???????????????????????????????commit???rollback??????
	 * SearchKey?ORDER BY??????????????????????
	 * @param key ??????Key
	 * @param second ??????
	 * @throws ReadWriteException ????????????????????????????
	 * @throws LockTimeOutException ?????????????????????
	 * @return ????????????true????????false?????
	 */
	public boolean lock(SearchKey key, int second) throws ReadWriteException, LockTimeOutException
	{
		Statement stmt = null;
		Object[] fmtObj = new Object[3];

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL = "SELECT {0} FROM DNWORKINFO,DMSTOCK"
			+ " WHERE  DNWORKINFO.STOCK_ID = DMSTOCK.STOCK_ID"
			+ "{1} FOR UPDATE  WAIT {2}";

			// ??????????
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();
			}
			else
			{
				// ????????????????????
				fmtObj[0] = " * ";
			}

			// ??????????
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " AND " + key.getReferenceCondition();
			}

			fmtObj[2] = Integer.toString(second);
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			return stmt.execute(sqlstring);
		}
		catch (SQLException e)
		{
			// ??????54?30006?Oracle????????????
			// ?????????????????????????
			// ???wait???0?????????nowait???????
			// nowoait????TimeOut???????54?????????????
			// ???0??????TimeOut?????30006???????
			if (e.getErrorCode() == 54)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE TIMEOUT (10) TABLE = DNWORKINFO");
			}
			else if (e.getErrorCode() == 30006)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE TIMEOUT (10) TABLE = DNWORKINFO");
			}
			// 6006002 = ?????????????????{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			// ????ReadWriteException????????????throw???
			throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
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
				// 6006002 = ?????????????????{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
				// ????ReadWriteException????????????throw???
				throw (new ReadWriteException("6006002" + wDelim + "DnWorkInfo"));
			}
		}
	}


	//------------------------------------------------------------
	// accessor methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// package methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------


	//------------------------------------------------------------
	// protected methods
	// use {@inheritDoc} in the comment, If the method is overridden.
	//------------------------------------------------------------
	/**
	 * @see AbstractDBHandler#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new WorkingInformation()) ;
	}


	//------------------------------------------------------------
	// private methods
	//------------------------------------------------------------


	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WorkingInformationHandler.java,v 1.2 2006/11/09 07:50:14 suresh Exp $" ;
	}


}

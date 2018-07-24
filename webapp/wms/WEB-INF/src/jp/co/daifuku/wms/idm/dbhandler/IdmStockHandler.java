package jp.co.daifuku.wms.idm.dbhandler;
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
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;

/**
 * データベースのStock表とLocation表を結合した検索専用のハンドラクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:09 $
 * @author  $Author: mori $
 */
public class IdmStockHandler extends StockHandler {

	// Class feilds ------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------


	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public IdmStockHandler(Connection conn)
	{
		super(conn);
	}

	/**
	 * Stockを検索し、取得します。<BR>
	 * find(Searchkey)との違いは、
	 * 移動ラックの棚番号を持つフィールドのみ選択することです。
	 * @param key 検索のためのKey
	 * @return 作成されたEntityの配列
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public Entity[] find(SearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[4];
		Entity[] stocks = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL = "SELECT {0} ";
			fmtSQL += "FROM DMSTOCK, DMLOCATE " + "{1} {2} {3}";

			// 取得条件を編集する。
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();
			}
			else
			{
				// 取得条件に指定なし時、全項目取得とする。
				fmtObj[0] = " * ";
			}

			// 検索条件を編集する。移動ラックであることを条件に付加する。		
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() 
				+ " AND DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}
			else
			{
				fmtObj[1] = " WHERE DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}

			// 集約条件を編集する。			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			// 読込み順を編集する。
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition();
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			DEBUG.MSG("HANDLER", "Stock Handler SQL[" + sqlstring + "]");
			rset = stmt.executeQuery(sqlstring);
			stocks = convertEntities(rset, key, 0);
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "StockHandler");
			// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
			throw (new ReadWriteException("6006002" + wDelim + "DmStock"));
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
				// 6006002 = データベースエラーが発生しました。{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), "StockHandler");
				// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				throw (new ReadWriteException("6006002" + wDelim + "DmStock"));
			}
		}
		return stocks;
	}
	
	
	/**
	 * データベースから、パラメータに基づいて情報を検索し、結果の件数（Stockのデータ件数）を返します。<BR>
	 * find(Searchkey)との違いは、
	 * 移動ラックの棚番号を持つフィールドのみ選択することです。
	 * @param key 検索のためのKey
	 * @return 検索結果の件数
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public int count(SearchKey key) throws ReadWriteException
	{
		int wCount = 0;
		Statement stmt = null;
		ResultSet rset = null;
		Object[] fmtObj = new Object[3];

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL = "SELECT COUNT({0}) COUNT " + "FROM DMSTOCK , DMLOCATE " + "{1} {2}";

			// 取得条件を編集する。
			if (key.getCollectConditionForCount() != null)
			{
				fmtObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				// 取得条件に指定なし時、全項目取得とする。
				fmtObj[0] = " * ";
			}

			// 検索条件を編集する。移動ラックであることを条件に付加する。		
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() 
				+ " AND DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}
			else
			{
				fmtObj[1] = " WHERE DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}

			// 集約条件を編集する。			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			DEBUG.MSG("HANDLER", "Stock Handler COUNT SQL[" + sqlstring + "]");
			rset = stmt.executeQuery(sqlstring);
			if (rset != null)
			{
				while (rset.next())
					wCount = rset.getInt("COUNT");
			}
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "StockHandler");
			// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
			throw (new ReadWriteException("6006002" + wDelim + "DmStock"));
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
				// 6006002 = データベースエラーが発生しました。{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), "StockHandler");
				// ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				throw (new ReadWriteException("6006002" + wDelim + "DmStock"));
			}
		}
		return wCount;
	}
}
//end of class

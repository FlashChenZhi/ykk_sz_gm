//$Id: InstockReceiveHostSendViewFinder.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.instockreceive.dbhandler;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewReportFinder;
import jp.co.daifuku.wms.instockreceive.dbhandler.InstockReceiveHostSendViewSearchKey;

/**
 * データベースからDvhostsendview表を検索しHostSendViewにマッピングするためのクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class InstockReceiveHostSendViewFinder extends HostSendViewReportFinder
{
	// Class filelds -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}

	// Class valiable ------------------------------------------------
	/**
	 *  このクラス内で一時的に検索条件を保持するための変数
	 */
	private SearchKey wSkey ;

	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public InstockReceiveHostSendViewFinder(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------

	/**
	 * HostSendを検索し、取得します。
	 * @param key 検索のためのKey
	 * @return 検索結果の件数
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public int search(InstockReceiveHostSendViewSearchKey key, String sql)
		throws ReadWriteException
	{
		close();
		open();
		Object[] fmtObj = new Object[5];
		Object[] cntObj = new Object[5];
		int count = 0;
		ResultSet countret = null;

		try
		{
			wSkey = key; // 検索条件の保管を行う。

			String fmtCountSQL = "SELECT COUNT({0}) COUNT FROM DVHOSTSENDVIEW {1} {2} {3} {4} ";

			String fmtSQL = "SELECT DISTINCT {0} ";
			fmtSQL += "FROM DVHOSTSENDVIEW " + "{1} {2} {3} {4}";

			// 	取得条件を編集する。(COUNT用)
			// 全項目取得とする。
			cntObj[0] = " * ";

			// 取得条件を編集する。
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition();
				fmtObj[0] = fmtObj[0] + ", " + key.ReferenceJoinColumns();
			}
			else
			{
				// 取得条件に指定なし時、全項目取得とする。
				fmtObj[0] = " * ";
			}

			// 結合テーブルを編集する。
			if (!StringUtil.isBlank(key.getJoinTable()))
			{
				if (StringUtil.isBlank(sql))
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
			// 検索条件を編集する。			
			if (key.getReferenceCondition() != null)
			{
				fmtObj[2] = "WHERE " + key.getReferenceCondition();
				cntObj[2] = "WHERE " + key.getReferenceCondition();
			}

			// 結合条件を編集する。
			if (key.ReferenceJoinWhere() != null)
			{
				if (StringUtil.isBlank(fmtObj[2].toString()))
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

			// 集約条件を編集する。
			if (key.getGroupCondition() != null)
			{
				fmtObj[3] = " GROUP BY " + key.getGroupCondition();
				cntObj[3] = " GROUP BY " + key.getGroupCondition();
			}

			// 読込み順を編集する。
			if (key.getSortCondition() != null)
			{
				fmtObj[4] = " ORDER BY " + key.getSortCondition();
				cntObj[4] = " ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj);
			DEBUG.MSG(
				"HANDLER",
				"InstockReceiveHostSendView Finder COUNT SQL[" + sqlcountstring + "]");
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//１件以上の場合にのみ検索を実行します
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
				DEBUG.MSG("HANDLER", "InstockReceiveHostSendView Finder SQL[" + sqlstring + "]");
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
			//6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "InstockReceiveHostSendViewFinder");
			throw (new ReadWriteException("6006002" + wDelim + "DvHostSendView"));
		}
		return count;
	}

	/**
	 * HostSendViewを検索するsqlを作成します。
	 * @param key 検索のためのKey
	 * @return 作成されたEntityの配列
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public String createFindSql(SearchKey key) throws ReadWriteException
	{
		Object[] fmtObj = new Object[4];

		String fmtSQL = "SELECT {0} ";
		fmtSQL += "FROM DVHOSTSENDVIEW " + "{1} {2} {3}";

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

		// 検索条件を編集する。
		if (key.getReferenceCondition() != null)
		{
			fmtObj[1] = " WHERE " + key.getReferenceCondition();
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

		return sqlstring;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

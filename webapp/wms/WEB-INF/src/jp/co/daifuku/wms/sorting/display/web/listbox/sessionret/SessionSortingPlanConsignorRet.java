package jp.co.daifuku.wms.sorting.display.web.listbox.sessionret;
/*
 * Created on Sep 30, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * 仕分予定情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingPlanConsignorRet(Connection conn,SortingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter param)</CODE>メソッドを呼び出し仕分予定情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード<BR>
 *     作業状態<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DNSORTINGPLAN <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を仕分予定情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/11/08</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SessionSortingPlanConsignorRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:32 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * 検索を行うための<CODE>find(SortingParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(SortingParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionSortingPlanConsignorRet(Connection conn, SortingParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 仕分予定情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから仕分予定情報を取得します。<BR>
	 *   3.仕分予定情報から表示データを取得し<CODE>SortingParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>Parameter</CODE>クラス
	 */
	public Parameter[] getEntities()
	{
		SortingParameter[] resultArray = null;
		SortingPlan sort[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				sort = (SortingPlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = convertToSortingParams(sort);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 入力されたパラメータをもとにSQL文を発行します。<BR>
	 * 検索を行う<code>SortingPlanFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter param) throws Exception
	{

		SortingPlanSearchKey skey = new SortingPlanSearchKey();
		// 検索実行
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}

		// グループ順をセットします
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		// 取得順をセットします
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");

		// ソート順をセットします
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);

		// 作業状態
		if (param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for (int i = 0; i < param.getSearchStatus().length; i++)
			{
				if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = SortingPlan.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_STARTED))
				{
					search[i] = SortingPlan.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_WORKING))
				{
					search[i] = SortingPlan.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = SortingPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = SortingPlan.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
		}
		wFinder = new SortingPlanFinder(wConn);
		// カーソルオープン
		wFinder.open();
		int count = wFinder.search(skey);
		// 初期化
		wLength = count;
		wCurrent = 0;
	}

	/**
	 * <CODE>SortingPlan</CODE>エンティティを<CODE>SortingParameter</CODE>に変換するためのクラスです。<BR>
	 * 
	 * @param sort 仕分予定情報
	 * @return SortingParameter[] 仕分予定情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private SortingParameter[] convertToSortingParams(SortingPlan[] sort)
	{
		if (sort == null || sort.length == 0)
		{
			return null;
		}
		SortingParameter[] stParam = new SortingParameter[sort.length];
		for (int i = 0; i < sort.length; i++)
		{
			stParam[i] = new SortingParameter();
			stParam[i].setConsignorCode(sort[i].getConsignorCode());
			stParam[i].setConsignorName(sort[i].getConsignorName());
		}

		return stParam;
	}

}
//end of class

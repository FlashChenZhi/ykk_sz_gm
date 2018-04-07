package jp.co.daifuku.wms.sorting.display.web.listbox.sessionret;

/*
 * Created on 2004/10/07
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
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * 実績情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingResultDateRet(Connection conn,SortingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter param)</CODE>メソッドを呼び出し実績情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード<BR>
 * 　　仕分日<BR>
 *     作業区分：仕分*<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DVRESULTVIEW <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を実績情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     仕分日<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/11/08</TD>
 * <TD>Muneendra</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SessionSortingResultDateRet extends SessionRet
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
	 * @param rtParam      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionSortingResultDateRet(Connection conn, SortingParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	// Public methods ------------------------------------------------
	/**
	 * 実績情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから実績情報を取得します。<BR>
	 *   3.実績情報から表示データを取得し<CODE>SortingParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>Parameter</CODE>クラス
	 */
	public Parameter[] getEntities()
	{
		SortingParameter[] resultArray = null;
		ResultView[] resultView = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultView = (ResultView[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (SortingParameter[]) convertToSortingSupportParams(resultView);
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
	 * 検索を行う<code>ResultViewFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param rtParam      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter rtParam) throws Exception
	{
		ResultViewSearchKey sKey = new ResultViewSearchKey();
		//検索条件をセットします
		//荷主コード
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//仕分日がセットされている場合
		if (!StringUtil.isBlank(rtParam.getSortingDate()))
		{
			sKey.setWorkDate(rtParam.getSortingDate());
		}

		//作業区分は　仕分にセットする。(03)
		sKey.setJobType(SystemDefine.JOB_TYPE_SORTING);

		//取得順をセットします
		sKey.setWorkDateCollect("");
		//グループ順をセットします
		sKey.setWorkDateGroup(1);

		//ソート順をセットします
		sKey.setWorkDateOrder(1, true);

		if (rtParam.getSearchStatus() != null && rtParam.getSearchStatus().length > 0)
		{
			String[] search = new String[rtParam.getSearchStatus().length];
			for (int i = 0; i < rtParam.getSearchStatus().length; ++i)
			{
				if (rtParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = ResultView.STATUS_FLAG_UNSTART;
				}
				else if (rtParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_STARTED))
				{
					search[i] = ResultView.STATUS_FLAG_START;
				}
				else if (rtParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_WORKING))
				{
					search[i] = ResultView.STATUS_FLAG_NOWWORKING;
				}
				else if (rtParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (rtParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			sKey.setStatusFlag(search);
		}
		else
		{
			sKey.setStatusFlag(ResultView.STATUS_FLAG_DELETE, "!=");
		}

		wFinder = new ResultViewFinder(wConn);
		// カーソルオープン
		wFinder.open();

		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;
	}

	/**
	 * <CODE>ResultView</CODE>エンティティを<CODE>SortingParameter</CODE>に変換するためのクラスです。<BR>
	 * 
	 * @param ety 実績情報
	 * @return Parameter[] 実績情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private Parameter[] convertToSortingSupportParams(Entity[] ety)
	{

		ResultView[] resultView = (ResultView[]) ety;
		if (resultView == null || resultView.length == 0)
		{
			return null;
		}
		SortingParameter[] rtParam = new SortingParameter[resultView.length];
		for (int i = 0; i < resultView.length; i++)
		{
			rtParam[i] = new SortingParameter();
			if (!StringUtil.isBlank(resultView[i].getWorkDate()))
			{
				rtParam[i].setSortingDate(resultView[i].getWorkDate()); //仕分実績日
			}
		}

		return rtParam;

	}

}
//end of class
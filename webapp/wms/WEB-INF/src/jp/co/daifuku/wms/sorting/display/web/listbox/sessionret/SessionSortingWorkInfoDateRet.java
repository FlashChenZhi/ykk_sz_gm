package jp.co.daifuku.wms.sorting.display.web.listbox.sessionret;
/*
 * Created on 2004/09/27 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * 作業情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingWorkInfoDateRet(Connection conn,SortingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード<BR>
 * 　　仕分予定日<BR>
 *     作業区分：仕分*<BR>
 * 　　作業状態<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を作業情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     仕分予定日<BR>
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
public class SessionSortingWorkInfoDateRet extends SessionRet
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
	 * @param stParam      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionSortingWorkInfoDateRet(Connection conn, SortingParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	// Public methods ------------------------------------------------
	/**
	 * 作業情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから作業情報を取得します。<BR>
	 *   3.作業情報から表示データを取得し<CODE>SortingParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>Parameter</CODE>クラス
	 */
	public Parameter[] getEntities()
	{
		SortingParameter[] resultArray = null;
		WorkingInformation[] workinfo = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				workinfo = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (SortingParameter[]) convertToSortingParams(workinfo);
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
	 * 検索を行う<code>WorkingInformationFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param stParam      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter stParam) throws Exception
	{
		WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
		// 検索条件をセットします
		// 作業区分
		sKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);

		// 荷主コード
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		// 仕分予定日がセットされている場合
		if (!StringUtil.isBlank(stParam.getPlanDate()))
		{
			sKey.setPlanDate(stParam.getPlanDate());
		}
		// 取得順をセットします
		sKey.setPlanDateCollect("");
		//グループ順をセットします
		sKey.setPlanDateGroup(1);
		//ソート順をセットします
		sKey.setPlanDateOrder(1, true);

		if (stParam.getSearchStatus() != null && stParam.getSearchStatus().length > 0)
		{
			String[] search = new String[stParam.getSearchStatus().length];
			for (int i = 0; i < stParam.getSearchStatus().length; i++)
			{
				if (stParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = SortingPlan.STATUS_FLAG_UNSTART;
				}
				else if (stParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_STARTED))
				{
					search[i] = SortingPlan.STATUS_FLAG_START;
				}
				else if (stParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_WORKING))
				{
					search[i] = SortingPlan.STATUS_FLAG_NOWWORKING;
				}
				else if (stParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = SortingPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (stParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = SortingPlan.STATUS_FLAG_COMPLETION;
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
			sKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
		}
		wFinder = new WorkingInformationFinder(wConn);
		//カーソルオープン
		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;

	}

	/**
	 * <CODE>WorkingInformation</CODE>エンティティを<CODE>SortingParameter</CODE>に変換するためのクラスです。<BR>
	 * 
	 * @param ety 作業情報
	 * @return Parameter[] 作業情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private Parameter[] convertToSortingParams(Entity[] ety)
	{
		WorkingInformation[] sortingPlan = (WorkingInformation[]) ety;
		if (sortingPlan == null || sortingPlan.length == 0)
		{
			return null;
		}
		SortingParameter[] stParam = new SortingParameter[sortingPlan.length];
		for (int i = 0; i < sortingPlan.length; i++)
		{
			stParam[i] = new SortingParameter();
			if (!StringUtil.isBlank(sortingPlan[i].getPlanDate()))
			{
				// 仕分予定日
				stParam[i].setPlanDate(sortingPlan[i].getPlanDate());
			}
		}

		return stParam;
	}

}
//end of class

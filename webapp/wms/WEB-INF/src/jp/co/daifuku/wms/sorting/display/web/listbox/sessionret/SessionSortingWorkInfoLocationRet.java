package jp.co.daifuku.wms.sorting.display.web.listbox.sessionret;
/*
 * Created on Oct 6, 2004
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * <BR>
 * 作業情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingWorkInfoLocationRet(Connection conn,SortingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード<BR>
 * 　　仕分予定日<BR>
 * 　　ロケーションNo.<BR>
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
 *     ロケーションNo.<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/09</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */

public class SessionSortingWorkInfoLocationRet extends SessionRet
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
	public SessionSortingWorkInfoLocationRet(Connection conn, SortingParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
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
		WorkingInformation temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToSortingSupportParams(temp);
			}
			catch (Exception e)
			{
				//エラーをログファイルに落とす
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
	 * @param param      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter param) throws Exception
	{

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		// 作業種別：仕分
		skey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		// 仕分場所  
		if (!StringUtil.isBlank(param.getSortingLocation()))
		{
			skey.setLocationNo(param.getSortingLocation());
		}
		// ケース品仕分場所
		if (!StringUtil.isBlank(param.getCaseSortingLocation()))
		{
			skey.setLocationNo(param.getCaseSortingLocation());
		}
		// ピース品仕分場所
		if (!StringUtil.isBlank(param.getPieceSortingLocation()))
		{
			skey.setLocationNo(param.getPieceSortingLocation());
		}

		// グループ順をセットします
		skey.setLocationNoGroup(1);
		// 取得順をセットします
		skey.setLocationNoCollect("");
		// ソート順をセットします
		skey.setLocationNoOrder(1, true);

		if (param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for (int i = 0; i < param.getSearchStatus().length; ++i)
			{
				if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
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
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}

		wFinder = new WorkingInformationFinder(wConn);
		// カーソルオープン
		wFinder.open();
		int count = ((WorkingInformationFinder) wFinder).search(skey);
		// 初期化
		wLength = count;
		wCurrent = 0;
	}

	/**
	 * <CODE>WorkingInformation</CODE>エンティティを<CODE>SortingParameter</CODE>に変換するためのクラスです。<BR>
	 * 
	 * @param workinfo 作業情報
	 * @return SortingParameter[] 作業情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private SortingParameter[] convertToSortingSupportParams(WorkingInformation[] workInfo)
	{

		if (workInfo == null || workInfo.length == 0)
		{
			return null;
		}
		SortingParameter[] stParam = new SortingParameter[workInfo.length];
		for (int i = 0; i < workInfo.length; i++)
		{
			stParam[i] = new SortingParameter();
			stParam[i].setSortingLocation(workInfo[i].getLocationNo());
		}
		return stParam;

	}

}
//end of class

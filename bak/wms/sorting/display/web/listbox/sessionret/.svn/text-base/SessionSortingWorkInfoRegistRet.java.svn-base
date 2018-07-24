package jp.co.daifuku.wms.sorting.display.web.listbox.sessionret;

/*
 * Created on Oct 22, 2004
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
import jp.co.daifuku.wms.base.dbhandler.SortingPlanReportFinder;
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
 * 1.検索処理(<CODE>SessionSortingWorkInfoRegistRet(Connection conn,SortingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter param)</CODE>メソッドを呼び出し仕分予定情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード*<BR>
 * 　　仕分予定日<BR>
 *     商品コード<BR>
 * 　　作業状態<BR>
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
 *     仕分予定日<BR>
 *     商品コード<BR>
 *     商品名称<BR>
 *     ケース入数<BR>
 *     ボール入数<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/09</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SessionSortingWorkInfoRegistRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主名称
	 */
	private String wConsignorName = "";

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
	public SessionSortingWorkInfoRegistRet(Connection conn, SortingParameter param) throws Exception
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
		SortingPlan temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (SortingPlan[]) ((SortingPlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToSortingParams(temp);
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
	private void find(SortingParameter searchParam) throws Exception
	{
		SortingPlanSearchKey searchkey = new SortingPlanSearchKey();
		SortingPlanSearchKey namekey = new SortingPlanSearchKey();

		// 荷主コード。必須項目です。
		if (!StringUtil.isBlank(searchParam.getConsignorCode()))
		{
			searchkey.setConsignorCode(searchParam.getConsignorCode());
			namekey.setConsignorCode(searchParam.getConsignorCode());
		}

		// 仕分予定日
		if (!StringUtil.isBlank(searchParam.getPlanDate()))
		{
			searchkey.setPlanDate(searchParam.getPlanDate());
			namekey.setPlanDate(searchParam.getPlanDate());
		}

		// 商品コード
		if (!StringUtil.isBlank(searchParam.getItemCode()))
		{
			searchkey.setItemCode(searchParam.getItemCode());
			namekey.setItemCode(searchParam.getItemCode());
		}
		// 状態フラグ
		if (searchParam.getSearchStatus() != null && searchParam.getSearchStatus().length > 0)
		{
			String[] search = new String[searchParam.getSearchStatus().length];
			for (int i = 0; i < searchParam.getSearchStatus().length; i++)
			{
				if (searchParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = SortingPlan.STATUS_FLAG_UNSTART;
				}
				else if (searchParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_STARTED))
				{
					search[i] = SortingPlan.STATUS_FLAG_START;
				}
				else if (searchParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_WORKING))
				{
					search[i] = SortingPlan.STATUS_FLAG_NOWWORKING;
				}
				else if (searchParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = SortingPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (searchParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = SortingPlan.STATUS_FLAG_COMPLETION;
				}
				else if (searchParam.getSearchStatus()[i].equals(SortingParameter.STATUS_FLAG_ALL))
				{
					search[i] = "*";
				}
			}
			searchkey.setStatusFlag(search);
			namekey.setStatusFlag(search);
		}
		else
		{
			searchkey.setStatusFlag(SortingParameter.STATUS_FLAG_DELETE, "!=");
			namekey.setStatusFlag(SortingParameter.STATUS_FLAG_DELETE, "!=");
		}

		// グループ順をセットします
		searchkey.setConsignorCodeGroup(1);
		searchkey.setPlanDateGroup(2);
		searchkey.setItemCodeGroup(3);
		searchkey.setItemName1Group(4);
		searchkey.setEnteringQtyGroup(5);
		searchkey.setBundleEnteringQtyGroup(6);
		searchkey.setItfGroup(7);
		searchkey.setBundleItfGroup(8);

		// 取得順をセットします
		searchkey.setConsignorCodeCollect("");
		searchkey.setPlanDateCollect("");
		searchkey.setItemCodeCollect("");
		searchkey.setItemName1Collect("");
		searchkey.setEnteringQtyCollect("");
		searchkey.setBundleEnteringQtyCollect("");
		searchkey.setItfCollect("");
		searchkey.setBundleItfCollect("");

		namekey.setConsignorNameCollect("");

		// ソート順をセットします		
		searchkey.setPlanDateOrder(1, true);
		searchkey.setItemCodeOrder(2, true);
		searchkey.setItemName1Order(3, true);
		searchkey.setItfOrder(4, true);
		searchkey.setBundleItfOrder(5, true);

		namekey.setRegistDateOrder(1, false);

		wFinder = new SortingPlanFinder(wConn);
		// カーソルオープン
		wFinder.open();
		int count = ((SortingPlanFinder) wFinder).search(searchkey);
		// 初期化
		wLength = count;
		wCurrent = 0;

		// 荷主名称を取得する
		SortingPlanReportFinder nameFinder = new SortingPlanReportFinder(wConn);
		nameFinder.open();
		int nameCount = nameFinder.search(namekey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			SortingPlan plan[] = (SortingPlan[]) nameFinder.getEntities(1);

			if (plan != null && plan.length != 0)
			{
				wConsignorName = plan[0].getConsignorName();
			}
		}
		nameFinder.close();

	}

	/**
	 * <CODE>sortPlan</CODE>エンティティを<CODE>SortingParameter</CODE>に変換するためのクラスです。<BR>
	 * 
	 * @param sortPlan 仕分予定情報
	 * @return SortingParameter[] 仕分予定情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private SortingParameter[] convertToSortingParams(SortingPlan[] sortPlan)
	{
		if (sortPlan == null || sortPlan.length == 0)
		{
			return null;
		}

		SortingParameter[] retrievalSupport = new SortingParameter[sortPlan.length];
		for (int i = 0; i < sortPlan.length; ++i)
		{
			retrievalSupport[i] = new SortingParameter();
			// 仕分予定日
			retrievalSupport[i].setPlanDate(sortPlan[i].getPlanDate());
			// 荷主コード
			retrievalSupport[i].setConsignorCode(sortPlan[i].getConsignorCode());
			// 荷主名称
			retrievalSupport[i].setConsignorName(wConsignorName);
			// 商品コード
			retrievalSupport[i].setItemCode(sortPlan[i].getItemCode());
			// 商品名称
			retrievalSupport[i].setItemName(sortPlan[i].getItemName1());
			// ボール入数
			retrievalSupport[i].setBundleEnteringQty(sortPlan[i].getBundleEnteringQty());
			// ケース入数
			retrievalSupport[i].setEnteringQty(sortPlan[i].getEnteringQty());
			// ケースITF
			retrievalSupport[i].setITF(sortPlan[i].getItf());
			// ボールITF
			retrievalSupport[i].setBundleITF(sortPlan[i].getBundleItf());

		}
		return retrievalSupport;

	}

}
//end of class

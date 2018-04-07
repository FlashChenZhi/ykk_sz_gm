// $Id: ListSortingDateBusiness.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingPlanDateRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingWorkInfoDateRet;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 仕分予定日検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、仕分予定日を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した仕分予定日をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ShpPlanDateSrch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の仕分予定日を親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ListSortingDateBusiness extends ListSortingDate implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 仕分予定日の受け渡しに使用するキーです
	 */
	public static final String SORTINGPLANDATE_KEY = "SORTINGPLANDATE_KEY";

	/** 
	 * 開始仕分予定日の受け渡しに使用するキーです
	 */
	public static final String STARTSORTINGPLANDATE_KEY = "STARTSORTINGPLANDATE_KEY";

	/** 
	 * 終了仕分予定日の受け渡しに使用するキーです
	 */
	public static final String ENDSORTINGPLANDATE_KEY = "ENDSORTINGPLANDATE_KEY";

	/** 
	 * 仕分予定日範囲フラグの受け渡しに使用するキーです
	 */
	public static final String RANGESORTINGPLANDATE_KEY = "RANGESORTINGPLANDATE_KEY";
	
	/** 
	 * 検索フラグの受け渡しに使用するキーです
	 */
	public static final String SEARCHSORTINGPLANDATE_KEY = "SEARCHSORTINGPLANDATE_KEY";

	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUSSORTINGPLANDATE_KEY = "WORKSTATUSSORTINGPLANDATE_KEY";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		選択 <BR>
	 *		仕分予定日 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 仕分予定日検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0082"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 仕分予定日
		String sortingplandate = request.getParameter(SORTINGPLANDATE_KEY);
		// 開始仕分予定日
		String startsortingplandate = request.getParameter(STARTSORTINGPLANDATE_KEY);
		// 終了仕分予定日
		String endsortingplandate = request.getParameter(ENDSORTINGPLANDATE_KEY);
		// 仕分予定日範囲フラグ
		String rangesortingplandate = request.getParameter(RANGESORTINGPLANDATE_KEY);
		// 検索テーブル
		String searchplandate = request.getParameter(SEARCHSORTINGPLANDATE_KEY);
		// 作業状態
		String[] search = request.getParameterValues(WORKSTATUSSORTINGPLANDATE_KEY);

		viewState.setString(SEARCHSORTINGPLANDATE_KEY, searchplandate);
		viewState.setString(RANGESORTINGPLANDATE_KEY, rangesortingplandate);

		// Sessionに取り残されているオブジェクトのコネクションをクローズする
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			//コネクションのクローズ
			sRet.closeConnection();
			//セッションから削除する
			this.getSession().removeAttribute("LISTBOX");
		}

		// コネクションの取得
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		// パラメータをセット
		SortingParameter param = new SortingParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 仕分予定日
		param.setPlanDate(sortingplandate);
		// 開始仕分予定日
		param.setFromPlanDate(startsortingplandate);
		// 終了仕分予定日
		param.setToPlanDate(endsortingplandate);
		// 作業状態配列
		param.setSearchStatus(search);

		// 検索する荷主が作業か予定か実績か判断する
		if (searchplandate.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// SessionShippingPlanDateRet インスタンス生成
			SessionSortingPlanDateRet listbox = new SessionSortingPlanDateRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchplandate.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// SessionSortingWorkInfoDateRet インスタンス生成
			SessionSortingWorkInfoDateRet listbox = new SessionSortingWorkInfoDateRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkInfoList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0136");
			String classname = this.getClass().getName();
			RmiMsgLogClient.write(6007039, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6007039" + wDelim + tObj[0]));
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setPlanList(SessionSortingPlanDateRet listbox, String actionName) throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();
		
		// ページ情報をセット
		listbox.setActionName(actionName);
		
		// 検索結果を取得
		SortingParameter[] splan = (SortingParameter[])listbox.getEntities();
		int len = 0;
		if (splan != null)
			len = splan.length;
			if (len > 0)
			{
				// Pagerへの値セット
				// 最大件数
				pgr_U.setMax(listbox.getLength());
				// 1Page表示件数
				pgr_U.setPage(listbox.getCondition());
				// 開始位置
				pgr_U.setIndex(listbox.getCurrent() + 1);
				// 最大件数
				pgr_D.setMax(listbox.getLength());
				// 1Page表示件数
				pgr_D.setPage(listbox.getCondition());
				// 開始位置
				pgr_D.setIndex(listbox.getCurrent() + 1);
				
				// メッセージを隠します
				lbl_InMsg.setVisible(false);
				
				// 行を全て削除
				lst_ListSortingDate.clearRow();

			for (int i = 0; i < len; i++)
		    {
			    // 最終行を取得
			    int count = lst_ListSortingDate.getMaxRows();
			    // 行追加
				lst_ListSortingDate.addRow();
	
			    // 最終行に移動
			    lst_ListSortingDate.setCurrentRow(count);
			    lst_ListSortingDate.setValue(1, Integer.toString(count + listbox.getCurrent()));
			    lst_ListSortingDate.setValue(2, WmsFormatter.toDispDate(splan[i].getPlanDate(), locale));
		    }
	    }
	    else
	    {
		    // Pagerへの値セット
		    // 最大件数
		    pgr_U.setMax(0);
		    // 1Page表示件数
		    pgr_U.setPage(0);
		    // 開始位置
		    pgr_U.setIndex(0);
		    // 最大件数
		    pgr_D.setMax(0);
		    // 1Page表示件数
		    pgr_D.setPage(0);
		    // 開始位置
		    pgr_D.setIndex(0);
	
		    // 検索結果の件数についてのチェックを行なう
		    String errorMsg = listbox.checkLength();
		    // ヘッダーを隠します
		    lst_ListSortingDate.setVisible(false);
		    // エラーメッセージ表示
		    lbl_InMsg.setResourceKey(errorMsg);
	    }
    }

	/**
	 * ページを変更するメソッド <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setWorkInfoList(SessionSortingWorkInfoDateRet listbox, String actionName) throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();
		
		// ページ情報をセット
		listbox.setActionName(actionName);
		
		// 検索結果を取得
		SortingParameter[] splan = (SortingParameter[])listbox.getEntities();
		int len = 0;
		if (splan != null)
			len = splan.length;
			if (len > 0)
			{
				// Pagerへの値セット
				// 最大件数
				pgr_U.setMax(listbox.getLength());
				// 1Page表示件数
				pgr_U.setPage(listbox.getCondition());
				// 開始位置
				pgr_U.setIndex(listbox.getCurrent() + 1);
				// 最大件数
				pgr_D.setMax(listbox.getLength());
				// 1Page表示件数
				pgr_D.setPage(listbox.getCondition());
				// 開始位置
				pgr_D.setIndex(listbox.getCurrent() + 1);
				
				// メッセージを隠します
				lbl_InMsg.setVisible(false);
				
				// 行を全て削除
				lst_ListSortingDate.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ListSortingDate.getMaxRows();
				// 行追加
				lst_ListSortingDate.addRow();
	
				// 最終行に移動
				lst_ListSortingDate.setCurrentRow(count);
				lst_ListSortingDate.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListSortingDate.setValue(2, WmsFormatter.toDispDate(splan[i].getPlanDate(), locale));
			}
		}
		else
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(0);
			// 1Page表示件数
			pgr_U.setPage(0);
			// 開始位置
			pgr_U.setIndex(0);
			// 最大件数
			pgr_D.setMax(0);
			// 1Page表示件数
			pgr_D.setPage(0);
			// 開始位置
			pgr_D.setIndex(0);
	
			// 検索結果の件数についてのチェックを行なう
			String errorMsg = listbox.checkLength();
			// ヘッダーを隠します
			lst_ListSortingDate.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「閉じる」ボタンを押したときの処理を行います。 <BR>
	 *  <BR>
	 * リストボックスを閉じ、親画面へ遷移します。 <BR>
	 *  <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	/** 
	 * 「＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 次のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	/** 
	 * 「＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * １ページ前を表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	/** 
	 * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 最終ページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	/** 
	 * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 一番最初のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingDate_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingDate_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に仕分予定日を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_ListSortingDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日範囲フラグを取得
		String flug = viewState.getString(RANGESORTINGPLANDATE_KEY);

		// 現在の行をセット
		lst_ListSortingDate.setCurrentRow(lst_ListSortingDate.getActiveRow());
		lst_ListSortingDate.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			// 仕分予定日
			param.setParameter(SORTINGPLANDATE_KEY, lst_ListSortingDate.getValue(2));
		}
		else if (flug.equals(SortingParameter.RANGE_START))
		{
			// 開始仕分予定日
			param.setParameter(STARTSORTINGPLANDATE_KEY, lst_ListSortingDate.getValue(2));
		}
		else if (flug.equals(SortingParameter.RANGE_END))
		{
			// 終了仕分予定日
			param.setParameter(ENDSORTINGPLANDATE_KEY, lst_ListSortingDate.getValue(2));
		}
		// 親画面に遷移する
		parentRedirect(param);
	}

	/** 
	 * 「＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 次のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		// 検索フラグを取得
		String flag = viewState.getString(SEARCHSORTINGPLANDATE_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanDateRet listbox = (SessionSortingPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoDateRet listbox = (SessionSortingWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "next");
		}
	}

	/** 
	 * 「＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * １ページ前を表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		// 検索フラグを取得
		String flag = viewState.getString(SEARCHSORTINGPLANDATE_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanDateRet listbox = (SessionSortingPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoDateRet listbox = (SessionSortingWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "previous");
		}
	}

	/** 
	 * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 最終ページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		// 検索フラグを取得
		String flag = viewState.getString(SEARCHSORTINGPLANDATE_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanDateRet listbox = (SessionSortingPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoDateRet listbox = (SessionSortingWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "last");
		}
	}

	/** 
	 * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 一番最初のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		// 検索フラグを取得
		String flag = viewState.getString(SEARCHSORTINGPLANDATE_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanDateRet listbox = (SessionSortingPlanDateRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoDateRet listbox = (SessionSortingWorkInfoDateRet) this.getSession().getAttribute("LISTBOX");
			setWorkInfoList(listbox, "first");
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「閉じる」ボタンを押したときの処理を行います。 <BR>
	 *  <BR>
	 * リストボックスを閉じ、親画面へ遷移します。 <BR>
	 *  <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		// Sessionにlistboxを保持
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		// Sessionに値がある場合
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				// ステートメントを閉じる
				finder.close();
			}
			// コネクションをクローズ
			sessionret.closeConnection();
		}
		// セッションから削除する
		this.getSession().removeAttribute("LISTBOX");
		// 親画面に戻る
		parentRedirect(null);
	}

}
//end of class

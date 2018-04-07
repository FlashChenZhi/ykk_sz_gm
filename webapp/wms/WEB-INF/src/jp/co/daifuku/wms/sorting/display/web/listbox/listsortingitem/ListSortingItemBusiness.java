// $Id: ListSortingItemBusiness.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingresultdate.ListSortingResultDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingPlanItemRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingResultItemRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingWorkInfoItemRet;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 商品検索リストボックスクラスです。<BR>
 * 親画面から入力した商品コードを基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した商品コードをキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ItemSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の商品コード、商品名称を親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ListSortingItemBusiness extends ListSortingItem implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 商品コードの受け渡しに使用するキーです
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";
	
	/** 
	 * 商品名称の受け渡しに使用するキーです
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";
	
	/** 
	 * 検索フラグの受け渡しに使用するキーです
	 */
	public static final String SEARCHITEM_KEY = "SEARCHITEM_KEY";
	
	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUSITEM_KEY = "WORKSTATUSITEM_KEY";
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
	 *		商品コード <BR>
	 *		商品名称 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 商品検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0043"));

		// パラメータを取得
		// 荷主コード
		String consignorcode =
			request.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 仕分予定日
		String sortingplandate =
			request.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY);
		// 仕分日
		String sortingdate =
			request.getParameter(ListSortingResultDateBusiness.SORTINGDATE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ITEMCODE_KEY);
		// 検索フラグ（予定 or 実績）
		String searchitem = request.getParameter(SEARCHITEM_KEY);
		// 作業状態
		String[] search = request.getParameterValues(WORKSTATUSITEM_KEY); 

		viewState.setString(SEARCHITEM_KEY, searchitem);

		// Sessionに取り残されているオブジェクトのコネクションをクローズする
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			// コネクションのクローズ
			sRet.closeConnection();
			// セッションから削除する
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
		// 仕分日
		param.setSortingDate(sortingdate);
		// 商品コード
		param.setItemCode(itemcode);
		// 作業状態配列
		param.setSearchStatus(search);

		// 検索する荷主が予定か実績か判断する
		if (searchitem.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// SessionSortingPlanItemRet インスタンス生成
			SessionSortingPlanItemRet listbox = new SessionSortingPlanItemRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchitem.equals(SortingParameter.SEARCH_SORTING_QTY))
		{
			// SessionSortingResultItemRet インスタンス生成
			SessionSortingResultItemRet listbox = new SessionSortingResultItemRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else if (searchitem.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// SessionSortingWorkInfoItemRet インスタンス生成
			SessionSortingWorkInfoItemRet listbox = new SessionSortingWorkInfoItemRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setWorkList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0099");
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
	 * 予定テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setPlanList(SessionSortingPlanItemRet listbox, String actionName)
		throws Exception
	{
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
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ItemSearch.getMaxRows();
				// 行追加
				lst_ItemSearch.addRow();

				// 最終行に移動
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, splan[i].getItemCode());
				lst_ItemSearch.setValue(3, splan[i].getItemName());
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
			lst_ItemSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	/**
	 * ページを変更するメソッド <BR>
	 * 実績テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setResultList(SessionSortingResultItemRet listbox, String actionName)
		throws Exception
	{
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
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ItemSearch.getMaxRows();
				// 行追加
				lst_ItemSearch.addRow();

				// 最終行に移動
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, splan[i].getItemCode());
				lst_ItemSearch.setValue(3, splan[i].getItemName());
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
			lst_ItemSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	/**
	 * ページを変更するメソッド <BR>
	 * 作業テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setWorkList(SessionSortingWorkInfoItemRet listbox, String actionName)
		throws Exception
	{
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
			lst_ItemSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ItemSearch.getMaxRows();
				// 行追加
				lst_ItemSearch.addRow();

				// 最終行に移動
				lst_ItemSearch.setCurrentRow(count);
				lst_ItemSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ItemSearch.setValue(2, splan[i].getItemCode());
				lst_ItemSearch.setValue(3, splan[i].getItemName());
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
			lst_ItemSearch.setVisible(false);
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
	public void lst_ItemSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ItemSearch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に商品コード、商品名称を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_ItemSearch_Click(ActionEvent e) throws Exception
	{
		// 現在の行をセット
		lst_ItemSearch.setCurrentRow(lst_ItemSearch.getActiveRow());
		lst_ItemSearch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		// 商品コード
		param.setParameter(ITEMCODE_KEY, lst_ItemSearch.getValue(2));
		// 商品名称
		param.setParameter(ITEMNAME_KEY, lst_ItemSearch.getValue(3));

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
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanItemRet listbox =
				(SessionSortingPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_QTY))
		{
			// Sessionにlistboxを保持
			SessionSortingResultItemRet listbox =
				(SessionSortingResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
		}
		if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoItemRet listbox =
				(SessionSortingWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "next");
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
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanItemRet listbox =
				(SessionSortingPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_QTY))
		{
			// Sessionにlistboxを保持
			SessionSortingResultItemRet listbox =
				(SessionSortingResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
		}
		if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoItemRet listbox =
				(SessionSortingWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "previous");
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
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanItemRet listbox =
				(SessionSortingPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_QTY))
		{
			// Sessionにlistboxを保持
			SessionSortingResultItemRet listbox =
				(SessionSortingResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
		}
		if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoItemRet listbox =
				(SessionSortingWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "last");
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
		String flag = viewState.getString(SEARCHITEM_KEY);

		if (flag.equals(SortingParameter.SEARCH_SORTING_PLAN))
		{
			// Sessionにlistboxを保持
			SessionSortingPlanItemRet listbox =
				(SessionSortingPlanItemRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(SortingParameter.SEARCH_SORTING_QTY))
		{
			// Sessionにlistboxを保持
			SessionSortingResultItemRet listbox =
				(SessionSortingResultItemRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
		}
		if (flag.equals(SortingParameter.SEARCH_SORTING_WORK))
		{
			// Sessionにlistboxを保持
			SessionSortingWorkInfoItemRet listbox =
				(SessionSortingWorkInfoItemRet) this.getSession().getAttribute("LISTBOX");
			setWorkList(listbox, "first");
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
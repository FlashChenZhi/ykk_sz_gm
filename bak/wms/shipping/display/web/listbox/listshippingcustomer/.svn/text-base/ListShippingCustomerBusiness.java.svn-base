// $Id: ListShippingCustomerBusiness.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.listbox.listshippingcustomer;
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
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingdate.ListShippingDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.sessionret.SessionShippingPlanCustomerRet;
import jp.co.daifuku.wms.shipping.display.web.listbox.sessionret.SessionShippingResultCustomerRet;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * 出荷先検索リストボックスクラスです。<BR>
 * 親画面から入力した出荷先コードを基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した出荷先コードをキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_CustomerSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の出荷先コード、出荷先名称を親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class ListShippingCustomerBusiness extends ListShippingCustomer implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 出荷先コードの受け渡しに使用するキーです
	 */
	public static final String CUSTOMERCODE_KEY = "CUSTOMERCODE_KEY";

	/** 
	 * 出荷先名称の受け渡しに使用するキーです
	 */
	public static final String CUSTOMERNAME_KEY = "CUSTOMERNAME_KEY";

	/** 
	 * 検索フラグの受け渡しに使用するキーです
	 */
	public static final String SEARCHCUSTOMER_KEY = "SEARCHCUSTOMER_KEY";

	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUSCUSTOMER_KEY = "WORKSTATUSCUSTOMER_KEY";
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
	 *		出荷先コード <BR>
	 *		出荷先名称 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 出荷先検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0031"));

		// パラメータを取得
		// 荷主コード
		String consignorcode =
			request.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);
		// 出荷予定日
		String shippingplandate =
			request.getParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY);
		// 開始出荷予定日
		String startshippingplandate =
			request.getParameter(ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY);
		// 終了出荷予定日
		String endshippingplandate =
			request.getParameter(ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY);
		// 開始出荷日
		String startshippingdate =
			request.getParameter(ListShippingDateBusiness.STARTSHIPPINGDATE_KEY);
		// 終了出荷日
		String endshippingdate = request.getParameter(ListShippingDateBusiness.ENDSHIPPINGDATE_KEY);
		// 出荷先コード
		String customercode = request.getParameter(CUSTOMERCODE_KEY);
		// 検索フラグ(予定 or 実績)
		String searchcustomer = request.getParameter(SEARCHCUSTOMER_KEY);
		// 作業状態
		String[] search = request.getParameterValues(WORKSTATUSCUSTOMER_KEY); 

		viewState.setString(SEARCHCUSTOMER_KEY, searchcustomer);

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
		ShippingParameter param = new ShippingParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 出荷予定日
		param.setPlanDate(shippingplandate);
		// 開始出荷予定日
		param.setFromPlanDate(startshippingplandate);
		// 終了出荷予定日
		param.setToPlanDate(endshippingplandate);
		// 開始出荷日
		param.setFromShippingDate(startshippingdate);
		// 終了出荷日
		param.setToShippingDate(endshippingdate);
		// 出荷先コード
		param.setCustomerCode(customercode);
		// 作業状態配列
		param.setSearchStatus(search);

		// 検索する出荷先が予定か実績か判断する
		if (searchcustomer.equals(ShippingParameter.SEARCHFLAG_PLAN))
		{
			// SessionShippingPlanCustomerRet インスタンス生成
			SessionShippingPlanCustomerRet listbox =
				new SessionShippingPlanCustomerRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setPlanList(listbox, "first");
		}
		else if (searchcustomer.equals(ShippingParameter.SEARCHFLAG_RESULT))
		{
			// SessionShippingResultCustomerRet インスタンス生成
			SessionShippingResultCustomerRet listbox =
				new SessionShippingResultCustomerRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setResultList(listbox, "first");
		}
		else
		{
			Object[] tObj = new Object[1];
			tObj[0] = DisplayText.getText("LBL-W0033");
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
	 * @param listbox このリストボックスのSession
	 * @param actionName 押されたボタンに対するactionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setPlanList(SessionShippingPlanCustomerRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		ShippingPlan[] splan = listbox.getEntities();
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
			lst_CustomerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_CustomerSearch.getMaxRows();
				// 行追加
				lst_CustomerSearch.addRow();

				// 最終行に移動
				lst_CustomerSearch.setCurrentRow(count);
				lst_CustomerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_CustomerSearch.setValue(2, splan[i].getCustomerCode());
				lst_CustomerSearch.setValue(3, splan[i].getCustomerName1());
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
			lst_CustomerSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	/**
	 * ページを変更するメソッド <BR>
	 * 実績テーブルを検索します。 <BR>
	 * @param listbox このリストボックスのSession
	 * @param actionName 押されたボタンに対するactionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setResultList(SessionShippingResultCustomerRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		ResultView[] rview = listbox.getEntities();
		int len = 0;
		if (rview != null)
			len = rview.length;
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
			lst_CustomerSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_CustomerSearch.getMaxRows();
				// 行追加
				lst_CustomerSearch.addRow();

				// 最終行に移動
				lst_CustomerSearch.setCurrentRow(count);
				lst_CustomerSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_CustomerSearch.setValue(2, rview[i].getCustomerCode());
				lst_CustomerSearch.setValue(3, rview[i].getCustomerName1());
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
			lst_CustomerSearch.setVisible(false);
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
	public void lst_CustomerSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_CustomerSearch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に出荷先コード、出荷先名称を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_CustomerSearch_Click(ActionEvent e) throws Exception
	{
		// 現在の行をセット
		lst_CustomerSearch.setCurrentRow(lst_CustomerSearch.getActiveRow());
		lst_CustomerSearch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		// 出荷先コード
		param.setParameter(CUSTOMERCODE_KEY, lst_CustomerSearch.getValue(2));
		// 出荷先名称
		param.setParameter(CUSTOMERNAME_KEY, lst_CustomerSearch.getValue(3));

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
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(ShippingParameter.SEARCHFLAG_PLAN))
		{
			// Sessionにlistboxを保持
			SessionShippingPlanCustomerRet listbox =
				(SessionShippingPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "next");
		}
		else if (flag.equals(ShippingParameter.SEARCHFLAG_RESULT))
		{
			// Sessionにlistboxを保持
			SessionShippingResultCustomerRet listbox =
				(SessionShippingResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "next");
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
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(ShippingParameter.SEARCHFLAG_PLAN))
		{
			// Sessionにlistboxを保持
			SessionShippingPlanCustomerRet listbox =
				(SessionShippingPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "previous");
		}
		else if (flag.equals(ShippingParameter.SEARCHFLAG_RESULT))
		{
			// Sessionにlistboxを保持
			SessionShippingResultCustomerRet listbox =
				(SessionShippingResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "previous");
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
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(ShippingParameter.SEARCHFLAG_PLAN))
		{
			// Sessionにlistboxを保持
			SessionShippingPlanCustomerRet listbox =
				(SessionShippingPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "last");
		}
		else if (flag.equals(ShippingParameter.SEARCHFLAG_RESULT))
		{
			// Sessionにlistboxを保持
			SessionShippingResultCustomerRet listbox =
				(SessionShippingResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "last");
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
		String flag = viewState.getString(SEARCHCUSTOMER_KEY);

		if (flag.equals(ShippingParameter.SEARCHFLAG_PLAN))
		{
			// Sessionにlistboxを保持
			SessionShippingPlanCustomerRet listbox =
				(SessionShippingPlanCustomerRet) this.getSession().getAttribute("LISTBOX");
			setPlanList(listbox, "first");
		}
		else if (flag.equals(ShippingParameter.SEARCHFLAG_RESULT))
		{
			// Sessionにlistboxを保持
			SessionShippingResultCustomerRet listbox =
				(SessionShippingResultCustomerRet) this.getSession().getAttribute("LISTBOX");
			setResultList(listbox, "first");
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

// $Id: ListInstockReceivePlanDateBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret.SessionInstockReceivePlanDateRet;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 入荷予定日検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、仕分予定日を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した入荷予定日をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ShpPlanDateSrch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の入荷予定日を親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:12 $
 * @author  $Author: mori $
 */
public class ListInstockReceivePlanDateBusiness extends ListInstockReceivePlanDate implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 入荷予定日の受け渡しに使用するキーです
	 */
	public static final String INSTOCKPLANDATE_KEY = "INSTOCKPLANDATE_KEY";

	/** 
	 * 開始入荷予定日の受け渡しに使用するキーです
	 */
	public static final String STARTINSTOCKPLANDATE_KEY = "STARTINSTOCKPLANDATE_KEY";

	/** 
	 * 終了入荷予定日の受け渡しに使用するキーです
	 */
	public static final String ENDINSTOCKPLANDATE_KEY = "ENDINSTOCKPLANDATE_KEY";

	/** 
	 * 入荷予定日範囲フラグの受け渡しに使用するキーです
	 */
	public static final String RANGEINSTOCKPLANDATE_KEY = "RANGEINSTOCKPLANDATE_KEY";
	
	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUSINSTOCKPLANDATE_KEY = "WORKSTATUSINSTOCKPLANDATE_KEY";
	
	/** 
	 * TCDC区分の受け渡しに使用するキーです
	 */
	public static final String TCDCFLAG_KEY = "TCDCFLAG_KEY";
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
	 *		入荷予定日 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 入荷予定日検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0053"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 入荷予定日
		String instockplandate = request.getParameter(INSTOCKPLANDATE_KEY);
		// 開始入荷予定日
		String startinstockplandate = request.getParameter(STARTINSTOCKPLANDATE_KEY);
		// 終了入荷予定日
		String endinstockplandate = request.getParameter(ENDINSTOCKPLANDATE_KEY);
		// 入荷予定日範囲フラグ
		String rangeinstockplandate = request.getParameter(RANGEINSTOCKPLANDATE_KEY);
		// 作業状態
		String[] search = request.getParameterValues(WORKSTATUSINSTOCKPLANDATE_KEY);
		// TCDC区分
		String searchtcdc = request.getParameter(TCDCFLAG_KEY);

		viewState.setString(RANGEINSTOCKPLANDATE_KEY, rangeinstockplandate);

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
		InstockReceiveParameter param = new InstockReceiveParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 入荷予定日
		param.setPlanDate(instockplandate);
		// 開始入荷予定日
		param.setFromPlanDate(startinstockplandate);
		// 終了入荷予定日
		param.setToPlanDate(endinstockplandate);
		// 作業状態配列
		param.setSearchStatus(search);
		// TCDC区分
		param.setTcdcFlag(searchtcdc);

		// SessionInstockReceivePlanDateRet インスタンス生成
		SessionInstockReceivePlanDateRet listbox = new SessionInstockReceivePlanDateRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * @param listbox
	 * @param string
	 */
	private void setList(SessionInstockReceivePlanDateRet listbox, String actionName)
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();
		
		// ページ情報をセット
		listbox.setActionName(actionName);
		
		// 検索結果を取得
		InstockReceiveParameter[] splan = (InstockReceiveParameter[]) listbox.getEntities();
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
				lst_InstkPlanDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_InstkPlanDateSrch.getMaxRows();
				// 行追加
				lst_InstkPlanDateSrch.addRow();
	
				// 最終行に移動
				lst_InstkPlanDateSrch.setCurrentRow(count);
				lst_InstkPlanDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_InstkPlanDateSrch.setValue(2, WmsFormatter.toDispDate(splan[i].getPlanDate(), locale));
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
			lst_InstkPlanDateSrch.setVisible(false);
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
	public void lst_InstkPlanDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に入荷予定日を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_InstkPlanDateSrch_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日範囲フラグを取得
		String flug = viewState.getString(RANGEINSTOCKPLANDATE_KEY);

		// 現在の行をセット
		lst_InstkPlanDateSrch.setCurrentRow(lst_InstkPlanDateSrch.getActiveRow());
		lst_InstkPlanDateSrch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		if (flug == null)
		{
			// 入荷予定日
			param.setParameter(INSTOCKPLANDATE_KEY, lst_InstkPlanDateSrch.getValue(2));
		}
		else if (flug.equals(InstockReceiveParameter.RANGE_START))
		{
			// 開始入荷予定日
			param.setParameter(STARTINSTOCKPLANDATE_KEY, lst_InstkPlanDateSrch.getValue(2));
		}
		else if (flug.equals(InstockReceiveParameter.RANGE_END))
		{
			// 終了入荷予定日
			param.setParameter(ENDINSTOCKPLANDATE_KEY, lst_InstkPlanDateSrch.getValue(2));
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
		// Sessionにlistboxを保持
		SessionInstockReceivePlanDateRet listbox = (SessionInstockReceivePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
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
		// Sessionにlistboxを保持
		SessionInstockReceivePlanDateRet listbox = (SessionInstockReceivePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
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
		// Sessionにlistboxを保持
		SessionInstockReceivePlanDateRet listbox = (SessionInstockReceivePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
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
		// Sessionにlistboxを保持
		SessionInstockReceivePlanDateRet listbox = (SessionInstockReceivePlanDateRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
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

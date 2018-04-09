// $Id: ListInstockReceiveDateBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivedate;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret.SessionInstockReceiveResultDateRet;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 入荷受付日検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、入荷受付日を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した入荷受付日をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ShpPlanDateSrch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の入荷受付日を親画面に渡し、リストボックスを閉じます。<BR>
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
public class ListInstockReceiveDateBusiness extends ListInstockReceiveDate implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 開始入荷受付日の受け渡しに使用するキーです
	 */
	public static final String STARTINSTOCKDATE_KEY = "STARTINSTOCKDATE_KEY";

	/** 
	 * 終了入荷受付日の受け渡しに使用するキーです
	 */
	public static final String ENDINSTOCKDATE_KEY = "ENDINSTOCKDATE_KEY";

	/** 
	 * 入荷受付日範囲フラグの受け渡しに使用するキーです
	 */
	public static final String RANGEINSTOCKDATE_KEY = "RANGEINSTOCKDATE_KEY";
	
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
	 *		入荷受付日 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 入荷受付日検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0051"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始入荷受付日
		String startinstockdate = request.getParameter(STARTINSTOCKDATE_KEY);
		// 終了入荷受付日
		String endinstockdate = request.getParameter(ENDINSTOCKDATE_KEY);
		// 入荷受付日範囲フラグ
		String rangeinstockplandate = request.getParameter(RANGEINSTOCKDATE_KEY);
		// TCDC区分
		String searchtcdc = request.getParameter(TCDCFLAG_KEY);

		viewState.setString(RANGEINSTOCKDATE_KEY, rangeinstockplandate);

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
		// 開始入荷受付日
		param.setFromInstockReceiveDate(startinstockdate);
		// 終了入荷受付日
		param.setToInstockReceiveDate(endinstockdate);
		// TCDC区分
		param.setTcdcFlag(searchtcdc);

		// SessionInstockReceiveResultDateRet インスタンス生成
		SessionInstockReceiveResultDateRet listbox = new SessionInstockReceiveResultDateRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * ページを変更するメソッド <BR>
	 * @param listbox
	 * @param string
	 */
	private void setList(SessionInstockReceiveResultDateRet listbox, String actionName)
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
				lst_InstkAcceptDateSrch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_InstkAcceptDateSrch.getMaxRows();
				// 行追加
				lst_InstkAcceptDateSrch.addRow();
	
				// 最終行に移動
				lst_InstkAcceptDateSrch.setCurrentRow(count);
				lst_InstkAcceptDateSrch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_InstkAcceptDateSrch.setValue(2, WmsFormatter.toDispDate(splan[i].getInstockReceiveDate(), locale));
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
			lst_InstkAcceptDateSrch.setVisible(false);
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
	public void lst_InstkAcceptDateSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkAcceptDateSrch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に入荷受付日を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_InstkAcceptDateSrch_Click(ActionEvent e) throws Exception
	{
		// 入荷受付日範囲フラグを取得
		String flug = viewState.getString(RANGEINSTOCKDATE_KEY);

		// 現在の行をセット
		lst_InstkAcceptDateSrch.setCurrentRow(lst_InstkAcceptDateSrch.getActiveRow());
		lst_InstkAcceptDateSrch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		if (flug.equals(InstockReceiveParameter.RANGE_START))
		{
			// 開始入荷受付日
			param.setParameter(STARTINSTOCKDATE_KEY, lst_InstkAcceptDateSrch.getValue(2));
		}
		else if (flug.equals(InstockReceiveParameter.RANGE_END))
		{
			// 終了入荷受付日
			param.setParameter(ENDINSTOCKDATE_KEY, lst_InstkAcceptDateSrch.getValue(2));
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
		SessionInstockReceiveResultDateRet listbox = (SessionInstockReceiveResultDateRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceiveResultDateRet listbox = (SessionInstockReceiveResultDateRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceiveResultDateRet listbox = (SessionInstockReceiveResultDateRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceiveResultDateRet listbox = (SessionInstockReceiveResultDateRet) this.getSession().getAttribute("LISTBOX");
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

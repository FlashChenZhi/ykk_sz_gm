// $Id: ListIdmLocationBusiness.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.listbox.listidmlocation;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmconsignor.ListIdmConsignorBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmitem.ListIdmItemBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.sessionret.SessionIdmLocationRet;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;

/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 出庫棚検索リストボックスクラスです。 <BR>
 * 親画面から入力した荷主コード、商品コード、ケース・ピース区分、開始棚No、終了棚Noをキーにして検索し、画面に表示します。<BR>
 * このクラスでは以下の処理を行います <BR>
 * 
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、商品コード、ケース・ピース区分、開始棚No、終了棚Noをキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ListStockLocation_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行のロケーションNo、バンクNo、ベイNo、レベルNoを親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class ListIdmLocationBusiness extends ListIdmLocation implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 開始棚情報の受け渡しに使用するキーです
	 */
	public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

	/** 
	 * 開始バンク情報の受け渡しに使用するキーです
	 */
	public static final String STARTBANK_KEY = "STARTBANK_KEY";

	/** 
	 * 開始ベイ情報の受け渡しに使用するキーです
	 */
	public static final String STARTBAY_KEY = "STARTBAY_KEY";

	/** 
	 * 開始レベル情報の受け渡しに使用するキーです
	 */
	public static final String STARTLEVEL_KEY = "STARTLEVEL_KEY";

	/** 
	 * 終了棚情報の受け渡しに使用するキーです
	 */
	public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";

	/** 
	 * 終了バンク情報の受け渡しに使用するキーです
	 */
	public static final String ENDBANK_KEY = "ENDBANK_KEY";

	/** 
	 * 終了ベイ情報の受け渡しに使用するキーです
	 */
	public static final String ENDBAY_KEY = "ENDBAY_KEY";

	/** 
	 * 終了レベル情報の受け渡しに使用するキーです
	 */
	public static final String ENDLEVEL_KEY = "ENDLEVEL_KEY";

	/** 
	 * ケース／ピース区分の受け渡しに使用するキーです
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

	/** 
	 * 棚範囲フラグの受け渡しに使用するキーです
	 */
	public static final String RANGELOCATION_KEY = "RANGELOCATION_KEY";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		選択 <BR>
	 *		棚 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 棚検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0064"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListIdmItemBusiness.ITEMCODE_KEY);
		// ケース／ピース区分
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		// 開始バンク
		String startbank = request.getParameter(STARTBANK_KEY);
		// 開始ベイ
		String startbay = request.getParameter(STARTBAY_KEY);
		// 開始レベル
		String startlevel = request.getParameter(STARTLEVEL_KEY);
		// 終了バンク
		String endbank = request.getParameter(ENDBANK_KEY);
		// 終了ベイ
		String endbay = request.getParameter(ENDBAY_KEY);
		// 終了レベル
		String endlevel = request.getParameter(ENDLEVEL_KEY);
		// 範囲フラグ
		String rangelocation = request.getParameter(RANGELOCATION_KEY);

		viewState.setString(RANGELOCATION_KEY, rangelocation);

		// コネクションの取得
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		// リストセル以外のヘッダ項目セット
		// 荷主コード
		lbl_JavaSetConsignorCode.setText(consignorcode);
		// 商品コード
		lbl_JavaSetItemCode.setText(itemcode);
		// ケース・ピース区分
		lbl_JavaSetFlag.setText(DisplayUtil.getPieceCaseValue(casepieceflag));
		// 検索条件に荷主名称をセット
		if (!StringUtil.isBlank(consignorcode))
		{
			lbl_JavaSetConsignorName.setText(this.getConsignorName(conn, consignorcode));
		}
		// 検索条件に商品名称をセット
		if (!StringUtil.isBlank(itemcode))
		{
			lbl_JavaSetItemName.setText(this.getItemName(conn, itemcode));
		}

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

		// パラメータをセット
		IdmControlParameter param = new IdmControlParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 商品コード
		param.setItemCode(itemcode);
		// ケース／ピース区分
		param.setCasePieceFlag(casepieceflag);
		// 開始棚
		param.setStartBankNo(startbank);
		param.setStartBayNo(startbay);
		param.setStartLevelNo(startlevel);
		// 終了棚
		if (rangelocation.equals(IdmControlParameter.RANGE_END))
		{
			param.setEndBankNo(endbank);
			param.setEndBayNo(endbay);
			param.setEndLevelNo(endlevel);
		}

		// SessionLocationRet インスタンス生成
		SessionIdmLocationRet listbox = new SessionIdmLocationRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * テーブルを検索します。 <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(SessionIdmLocationRet listbox, String actionName) throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		IdmControlParameter[] stcparam = listbox.getEntities();
		int len = 0;
		if (stcparam != null)
			len = stcparam.length;
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
			lst_ListLocationSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ListLocationSearch.getMaxRows();
				// 行追加
				lst_ListLocationSearch.addRow();

				// 最終行に移動
				lst_ListLocationSearch.setCurrentRow(count);
				lst_ListLocationSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListLocationSearch.setValue(2, stcparam[i].getLocationNo());
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
			lst_ListLocationSearch.setVisible(false);
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
	public void lst_ListLocationSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListLocationSearch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面にロケーションNoを渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_ListLocationSearch_Click(ActionEvent e) throws Exception
	{
		// 範囲フラグを取得
		String flug = viewState.getString(RANGELOCATION_KEY);

		// 現在の行をセット
		lst_ListLocationSearch.setCurrentRow(lst_ListLocationSearch.getActiveRow());
		lst_ListLocationSearch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		if (flug.equals(IdmControlParameter.RANGE_START))
		{
			// 開始棚
			param.setParameter(STARTLOCATION_KEY, lst_ListLocationSearch.getValue(2));
		}
		else if (flug.equals(IdmControlParameter.RANGE_END))
		{
			// 終了棚
			param.setParameter(ENDLOCATION_KEY, lst_ListLocationSearch.getValue(2));
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
		SessionIdmLocationRet listbox = (SessionIdmLocationRet) this.getSession().getAttribute("LISTBOX");
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
		SessionIdmLocationRet listbox = (SessionIdmLocationRet) this.getSession().getAttribute("LISTBOX");
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
		SessionIdmLocationRet listbox = (SessionIdmLocationRet) this.getSession().getAttribute("LISTBOX");
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
		SessionIdmLocationRet listbox = (SessionIdmLocationRet) this.getSession().getAttribute("LISTBOX");
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

	/** 
	 * 荷主コードより荷主名称を取得します。 <BR>
	 * @param pconn データベースコネクション
	 * @param pconsignorcode 荷主コード
	 * @return 荷主名称
	 * @throws Exception 全ての例外を報告します。
	 */
	public String getConsignorName(Connection pconn, String pconsignorcode) throws Exception
	{
		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;

		StockHandler wStockHandler = new StockHandler(pconn);
		StockSearchKey wStockSearchKey = new StockSearchKey();
		
		wStockSearchKey.KeyClear();
		wStockSearchKey.setAreaNo(w_AreaNo);
		wStockSearchKey.setConsignorCode(pconsignorcode);
		
		// 最新更新日時の降順
		wStockSearchKey.setLastUpdateDateOrder(1, false);
		
		Stock[] wStock = (Stock[])wStockHandler.find(wStockSearchKey);
		
		if (wStock == null || wStock.length <= 0)
		{
			return null;
		}
		else
		{
			return (wStock[0].getConsignorName());
		}
		
	}

	/** 
	 * 商品コードより商品名称を取得します。 <BR>
	 * @param pconn データベースコネクション
	 * @param pitemcode 商品コード
	 * @return 商品名称
	 * @throws Exception 全ての例外を報告します。
	 */
	public String getItemName(Connection pconn, String pitemcode) throws Exception
	{
		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;

		StockHandler wStockHandler = new StockHandler(pconn);
		StockSearchKey wStockSearchKey = new StockSearchKey();
		
		wStockSearchKey.KeyClear();
		wStockSearchKey.setAreaNo(w_AreaNo);
		wStockSearchKey.setItemCode(pitemcode);
		
		// 最新更新日時の降順
		wStockSearchKey.setLastUpdateDateOrder(1, false);
		
		Stock[] wStock = (Stock[])wStockHandler.find(wStockSearchKey);
		
		if (wStock == null || wStock.length <= 0)
		{
			return null;
		}
		else
		{
			return (wStock[0].getItemName1());
		}
		
	}
}
//end of class

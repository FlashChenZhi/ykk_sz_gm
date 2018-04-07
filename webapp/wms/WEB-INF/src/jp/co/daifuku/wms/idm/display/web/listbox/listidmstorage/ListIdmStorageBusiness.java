// $Id: ListIdmStorageBusiness.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.listbox.listidmstorage;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmconsignor.ListIdmConsignorBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmitem.ListIdmItemBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.sessionret.SessionIdmStorageRet;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;

/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 在庫照会一覧検索リストボックス（移動ラック）クラスです。<BR>
 * 親画面から入力した荷主コード、商品コード、ケース・ピース区分を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、商品コード、ケース・ピース区分をキーにして検索し、画面に表示します。<BR>
 * </DIR>
 * <BR>
 * 2.選択した行のボタン（<CODE>lst_ListIdmStockSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の <BR>
 * 	<DIR>
 *      荷主コード<BR>
 *      荷主名称<BR>
 * 		商品コード <BR>
 * 		商品名称 <BR>
 * 		ケース／ピース区分 <BR>
 *      ケース入数 <BR>
 *      ボール入数 <BR>
 *      ケースＩＴＦ <BR>
 *      ボールＩＴＦ <BR>
 *      賞味期限日 <BR>
 * 	</DIR>
 * を親画面に渡し、リストボックスを閉じます。<BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/06</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class ListIdmStorageBusiness extends ListIdmStorage implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * ケース・ピース区分の受け渡しに使用するキーです
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";
	
	/** 
	 * ケース入り数の受け渡しに使用するキーです
	 */
	public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";
	
	/** 
	 * ボール入り数の受け渡しに使用するキーです
	 */
	public static final String BUNDLE_ENTERINGQTY_KEY = "BUNDLE_ENTERINGQTY_KEY";
	
	/** 
	 * ケースITFの受け渡しに使用するキーです
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";
	
	/** 
	 * ボールITFの受け渡しに使用するキーです
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";
	
	/** 
	 * 賞味期限の受け渡しに使用するキーです
	 */
	public static final String USEBYDATE_KEY = "USEBYDATE_KEY";
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
	 *		区分 <BR>
	 *		棚 <BR>
	 *		ケース入数 <BR>
	 *		ケースITF <BR>
	 *		賞味期限 <BR>
	 *		商品名称 <BR>
	 *		ボール入数 <BR>
	 *		ボールITF <BR>
	 *      入庫日 <BR>
	 *      入庫時刻 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 在庫照会一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0014"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListIdmItemBusiness.ITEMCODE_KEY);
		// ケース／ピース区分
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ListIdmStockSearch, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_ListIdmStockSearch, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// コネクションの取得
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		// リストセル以外のヘッダ項目セット
		// 荷主コード
		lbl_JavaSetConsignorCode.setText(consignorcode);

		// 検索条件に荷主名称をセット
		if (!StringUtil.isBlank(consignorcode))
		{
			lbl_JavaSetConsignorName.setText(this.getConsignorName(conn, consignorcode));
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

		// SessionListStockMaintenanceRet インスタンス生成
		SessionIdmStorageRet listbox = new SessionIdmStorageRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * 在庫情報テーブルを検索します。 <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(SessionIdmStorageRet listbox, String actionName)
		throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();

		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		IdmControlParameter[] scparam = (IdmControlParameter[])listbox.getEntities();

		int len = 0;
		if (scparam != null)
			len = scparam.length;
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
			lst_ListIdmStockSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ListIdmStockSearch.getMaxRows();
				// 行追加
				lst_ListIdmStockSearch.addRow();

				// 最終行に移動
				lst_ListIdmStockSearch.setCurrentRow(count);
				lst_ListIdmStockSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListIdmStockSearch.setValue(2, scparam[i].getItemCode());
				lst_ListIdmStockSearch.setValue(3, scparam[i].getCasePieceFlagNameDisp());
				lst_ListIdmStockSearch.setValue(4, scparam[i].getLocationNo());
				lst_ListIdmStockSearch.setValue(5, WmsFormatter.getNumFormat(scparam[i].getEnteringQty()));
				lst_ListIdmStockSearch.setValue(6, scparam[i].getITF());
				lst_ListIdmStockSearch.setValue(7, WmsFormatter.toDispDate(WmsFormatter.toParamDate(scparam[i].getStorageDate()), locale));
				lst_ListIdmStockSearch.setValue(8, scparam[i].getUseByDate());
				lst_ListIdmStockSearch.setValue(9, scparam[i].getItemName());
				lst_ListIdmStockSearch.setValue(10, WmsFormatter.getNumFormat(scparam[i].getBundleEnteringQty()));
				lst_ListIdmStockSearch.setValue(11, scparam[i].getBundleITF());
				lst_ListIdmStockSearch.setValue(12, WmsFormatter.getTimeFormat(scparam[i].getStorageDate(), ""));
				List list = new Vector();
				list.add(scparam[i].getCasePieceFlagDisp());
				list.add(scparam[i].getConsignorCode());
				list.add(scparam[i].getConsignorName());
				lst_ListIdmStockSearch.setValue(0, CollectionUtils.getConnectedString(list));

				// ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				// 商品名称
				toolTip.add(DisplayText.getText("LBL-W0103"), scparam[i].getItemName());

				// カレント行にToolTipをセットする
				lst_ListIdmStockSearch.setToolTip(lst_ListIdmStockSearch.getCurrentRow(), toolTip.getText());
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
			lst_ListIdmStockSearch.setVisible(false);
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
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に <BR>
	 *	<DIR>
	 *		荷主コード <BR>
	 *		荷主名称 <BR>
	 *		商品コード <BR>
	 *		商品名称 <BR>
	 *		ケース／ピース区分 <BR>
	 *      ケース入数 <BR>
	 *      ボール入数 <BR>
	 *      ケースITF <BR>
	 *      ボールITF <BR>
	 *		賞味期限 <BR>
	 *	</DIR>
	 *	を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_ListIdmStockSearch_Click(ActionEvent e) throws Exception
	{
		// 現在の行をセット
		lst_ListIdmStockSearch.setCurrentRow(lst_ListIdmStockSearch.getActiveRow());
		lst_ListIdmStockSearch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		// 商品コード
		param.setParameter(ListIdmItemBusiness.ITEMCODE_KEY, lst_ListIdmStockSearch.getValue(2));
		// 商品名称
		param.setParameter(ListIdmItemBusiness.ITEMNAME_KEY, lst_ListIdmStockSearch.getValue(9));
		// ケース入数
		param.setParameter(ENTERINGQTY_KEY, lst_ListIdmStockSearch.getValue(5));
		// ボール入数
		param.setParameter(BUNDLE_ENTERINGQTY_KEY, lst_ListIdmStockSearch.getValue(10));
		// ケースITF
		param.setParameter(CASEITF_KEY, lst_ListIdmStockSearch.getValue(6));
		// ボールITF
		param.setParameter(BUNDLEITF_KEY, lst_ListIdmStockSearch.getValue(11));
		// 賞味期限
		param.setParameter(USEBYDATE_KEY, lst_ListIdmStockSearch.getValue(8));

		String hidden = lst_ListIdmStockSearch.getValue(0);
		// ケース・ピース区分
		param.setParameter(CASEPIECEFLAG_KEY, CollectionUtils.getString(0, hidden));
		// 荷主コード
		param.setParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY, CollectionUtils.getString(1, hidden));
		// 荷主名称
		param.setParameter(ListIdmConsignorBusiness.CONSIGNORNAME_KEY, CollectionUtils.getString(2, hidden));

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
		SessionIdmStorageRet listbox =
			(SessionIdmStorageRet) this.getSession().getAttribute("LISTBOX");
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
		SessionIdmStorageRet listbox =
			(SessionIdmStorageRet) this.getSession().getAttribute("LISTBOX");
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
		SessionIdmStorageRet listbox =
			(SessionIdmStorageRet) this.getSession().getAttribute("LISTBOX");
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
		SessionIdmStorageRet listbox =
			(SessionIdmStorageRet) this.getSession().getAttribute("LISTBOX");
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Flag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListIdmStockSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListIdmStockSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListIdmStockSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListIdmStockSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListIdmStockSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListIdmStockSearch_Change(ActionEvent e) throws Exception
	{
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
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}


}
//end of class

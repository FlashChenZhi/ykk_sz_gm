// $Id: ListIdmReplenishLocationBusiness.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.listbox.listidmreplenishlocation;
import java.sql.Connection;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmconsignor.ListIdmConsignorBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmemplocation.ListIdmEmpLocationBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmitem.ListIdmItemBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.sessionret.SessionIdmReplenishLocationRet;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;
import jp.co.daifuku.wms.idm.schedule.IdmOperate;

/**
 * <FONT COLOR="BLUE">
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 移動ラック補充棚検索リストボックスクラスです。<BR>
 * 親画面から入力したパラメータを基に移動ラックの補充可能（実）棚を検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、商品コード、ケース・ピース区分、入庫棚Noをキーにして検索し、画面に表示します。<BR>
 *  移動ラック倉庫の補充可能（実）棚のみ表示します。 <BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ItemSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行のロケーションNo、バンクNo、ベイNo、レベルNoを親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/05</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class ListIdmReplenishLocationBusiness extends ListIdmReplenishLocation implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * ケース/ピース区分の受け渡しに使用するキーです
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";

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
		// 補充棚検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0802"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListIdmItemBusiness.ITEMCODE_KEY);
		// ケース・ピース区分
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		// バンクNo
		String bankno = request.getParameter(ListIdmEmpLocationBusiness.BANKNO_KEY);
		// ベイNo
		String bayno = request.getParameter(ListIdmEmpLocationBusiness.BAYNO_KEY);
		// レベルNo
		String levelno = request.getParameter(ListIdmEmpLocationBusiness.LEVELNO_KEY);

		String location = "";

		if(StringUtil.isBlank(bankno))
		{
			location += "XX-" ;
		}
		else
		{
			location += bankno + "-" ;
		}
		if(StringUtil.isBlank(bayno))
		{
			location += "XX-" ;
		}
		else
		{
			location += bayno + "-" ;
		}
		if(StringUtil.isBlank(levelno))
		{
			location += "XX" ;
		}
		else
		{
			location += levelno ;
		}

		// コネクションの取得
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);
		
		// リストセル以外のヘッダ項目セット
		// 荷主コード
		lbl_JavaSetConsignorCode.setText(consignorcode);
		// 商品コード
		lbl_JavaSetItemCode.setText(itemcode);
		// ケース・ピース区分
		lbl_JavaSetFlag.setText(DisplayUtil.getPieceCaseValue(casepieceflag));
		// 開始棚
		lbl_JavaSetLocationNo.setText(location);
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
		// ケース・ピース区分
		if (!casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_ALL))
		{
			param.setCasePieceFlag(casepieceflag);
		}
		// バンクNo
		param.setBankNo(bankno);
		// ベイNo
		param.setBayNo(bayno);
		// レベルNo
		param.setLevelNo(levelno);

		// SessionIdmReplenishLocationRet インスタンス生成
		SessionIdmReplenishLocationRet listbox =
			new SessionIdmReplenishLocationRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setPlanList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * 予定テーブルを検索します。 <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setPlanList(SessionIdmReplenishLocationRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		IdmControlParameter[] irparam = (IdmControlParameter[]) listbox.getEntities();
		int len = 0;
		if (irparam != null)
			len = irparam.length;
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
			lst_ListReplenishLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ListReplenishLocation.getMaxRows();
				// 行追加
				lst_ListReplenishLocation.addRow();
				// 最終行に移動
				lst_ListReplenishLocation.setCurrentRow(count);
				lst_ListReplenishLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListReplenishLocation.setValue(2, irparam[i].getLocationNo());
				lst_ListReplenishLocation.setValue(3, irparam[i].getItemCode());
				lst_ListReplenishLocation.setValue(4, irparam[i].getItemName());
				lst_ListReplenishLocation.setValue(5, DisplayUtil.getPieceCaseValue(irparam[i].getCasePieceFlagDisp()));
				
				IdmOperate iOpe = new IdmOperate();
				String[] rObj = iOpe.exportFormatIdmLocation(irparam[i].getLocationNo());
				List list = new Vector();
				list.add(irparam[i].getCasePieceFlagDisp());
				list.add(rObj[0]);
				list.add(rObj[1]);
				list.add(rObj[2]);
				lst_ListReplenishLocation.setValue(0, CollectionUtils.getConnectedString(list));
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
			lst_ListReplenishLocation.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	/**
	 * ページを変更するメソッド <BR>
	 * 実績テーブルを検索します。 <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setResultList(SessionIdmReplenishLocationRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		IdmControlParameter[] irparam = (IdmControlParameter[]) listbox.getEntities();
		int len = 0;
		if (irparam != null)
			len = irparam.length;
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
			lst_ListReplenishLocation.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ListReplenishLocation.getMaxRows();
				// 行追加
				lst_ListReplenishLocation.addRow();

				// 最終行に移動
				lst_ListReplenishLocation.setCurrentRow(count);
				lst_ListReplenishLocation.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ListReplenishLocation.setValue(2, irparam[i].getLocationNo());
				lst_ListReplenishLocation.setValue(3, irparam[i].getItemCode());
				lst_ListReplenishLocation.setValue(4, irparam[i].getItemName());
				lst_ListReplenishLocation.setValue(5, DisplayUtil.getPieceCaseValue(irparam[i].getCasePieceFlagDisp()));

				IdmOperate iOpe = new IdmOperate();
				String[] rObj = iOpe.exportFormatIdmLocation(irparam[i].getLocationNo());
				List list = new Vector();
				list.add(irparam[i].getCasePieceFlagDisp());
				list.add(rObj[0]);
				list.add(rObj[1]);
				list.add(rObj[2]);
				lst_ListReplenishLocation.setValue(0, CollectionUtils.getConnectedString(list));
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
			lst_ListReplenishLocation.setVisible(false);
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
	public void lst_ListReplenishLocation_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListReplenishLocation_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListReplenishLocation_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListReplenishLocation_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListReplenishLocation_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListReplenishLocation_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面にバンクNo、ベイNo、レベルNoを渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_ListReplenishLocation_Click(ActionEvent e) throws Exception
	{
		// 現在の行をセット
		lst_ListReplenishLocation.setCurrentRow(lst_ListReplenishLocation.getActiveRow());
		lst_ListReplenishLocation.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();

		String hidden = lst_ListReplenishLocation.getValue(0);
		// バンクNo
		param.setParameter(ListIdmEmpLocationBusiness.BANKNO_KEY, CollectionUtils.getString(1, hidden));
		// ベイNo
		param.setParameter(ListIdmEmpLocationBusiness.BAYNO_KEY, CollectionUtils.getString(2, hidden));
		// レベルNo
		param.setParameter(ListIdmEmpLocationBusiness.LEVELNO_KEY, CollectionUtils.getString(3, hidden));
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
		SessionIdmReplenishLocationRet listbox =
			(SessionIdmReplenishLocationRet) this.getSession().getAttribute("LISTBOX");
		setPlanList(listbox, "next");
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
		SessionIdmReplenishLocationRet listbox =
			(SessionIdmReplenishLocationRet) this.getSession().getAttribute("LISTBOX");
		setPlanList(listbox, "previous");
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
		SessionIdmReplenishLocationRet listbox =
			(SessionIdmReplenishLocationRet) this.getSession().getAttribute("LISTBOX");
		setPlanList(listbox, "last");
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
		SessionIdmReplenishLocationRet listbox =
			(SessionIdmReplenishLocationRet) this.getSession().getAttribute("LISTBOX");
		setPlanList(listbox, "first");
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
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Search_Server(ActionEvent e) throws Exception
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
	public void lbl_LocationNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationNo_Server(ActionEvent e) throws Exception
	{
	}


}
//end of class

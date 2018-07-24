// $Id: MasterAsAddStorage1Business.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsaddstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetaillist.ListAsLocationDetailListBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail.ListAsStockDetailBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.master.schedule.MasterAsAddStorageSCH;
import jp.co.daifuku.wms.master.schedule.MasterAsScheduleParameter;

/**
 * マスタパッケージ導入時のAS/RS予定外入庫設定（積増）画面です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class MasterAsAddStorage1Business extends MasterAsAddStorage1 implements WMSConstants
{
	// Class fields --------------------------------------------------

	// ViewState用
	// 作業者コード
	protected static final String VS_WORKERCODE = "WORKER_CODE";
	// パスワード
	protected static final String VS_PASSWORD = "PASSWORD";
	// 荷主コード
	protected static final String VS_CONSIGNORCODE = "CONSIGNOR_CODE";
	// 荷主名称
	protected static final String VS_CONSIGNORNAME = "CONSIGNOR_NAME";
	// 商品コード
	protected static final String VS_ITEMCODE = "ITEM_CODE";
	// 商品名称
	protected static final String VS_ITEMNAME = "ITEM_NAME";
	// ケースピース区分
	protected static final String VS_CASEPIECE = "CASE_PIECE_FLAG";
	// 棚No
	protected static final String VS_LOCATION = "LOCATION";
	// 倉庫ステーションNo
	protected static final String VS_WHNUMBER = "WH_STATIONNUMBER";
	// タイトル
	protected static final String TITLE = "TITLE";
	// メッセージ
	protected static final String MESSAGE = "MESSAGE";

	// 予定外入庫設定(ASRS積増し)棚選択 画面アドレス
	protected static final String DO_ADDSTORAGE1 = "/master/masterasrsaddstorage/MasterAsAddStorage1.do";
	// 予定外入庫設定(ASRS積増し)入庫情報入力 画面アドレス
	private static final String DO_ADDSTORAGE2 = "/master/masterasrsaddstorage/MasterAsAddStorage2.do";

	/**
	 * 読取専用フラグを保持するViewState用キーです
	 */
	public static final String IS_READ_ONLY_KEY = "IS_READ_ONLY";
	
	/**
	 * 荷主コードの最終使用日を保持するViewState用キーです
	 */
	public static final String CONSIG_DATE_KEY = "CONSIG_DATE";

	/**
	 * 商品コードの最終使用日を保持するViewState用キーです
	 */
	public static final String ITEM_DATE_KEY = "ITEM_DATE";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// ２画面目から戻ってきた時にタイトルをセットする。
		if (!StringUtil.isBlank(this.getViewState().getString(TITLE)))
		{
			lbl_SettingName.setResourceKey(this.getViewState().getString(TITLE));
		}
		// 画面を初期化します。
		setInitView(true);

		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		Connection conn = null;
		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 本HttpのLocaleを取得
			Locale locale = this.getHttpRequest().getLocale();

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			// プルダウン表示データ（保管エリア）
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);

			// プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_WareHouse, areaid);

			// 棚選択のタブを前出しします
			tab_Add_Storage.setSelectedIndex(1);

			// 入庫情報入力.戻るボタン押下時、
			// ViewStateに値がセットされていれば、その値をセットします
			// 作業者コード
			if (!StringUtil.isBlank(this.getViewState().getString(VS_WORKERCODE)))
			{
				txt_WorkerCode.setText(this.getViewState().getString(VS_WORKERCODE));
			}
			// パスワード
			if (!StringUtil.isBlank(this.getViewState().getString(VS_PASSWORD)))
			{
				txt_Password.setText(this.getViewState().getString(VS_PASSWORD));
			}
			// 荷主コード
			if (!StringUtil.isBlank(this.getViewState().getString(VS_CONSIGNORCODE)))
			{
				txt_ConsignorCode.setText(this.getViewState().getString(VS_CONSIGNORCODE));
			}
			// 商品コード
			if (!StringUtil.isBlank(this.getViewState().getString(VS_ITEMCODE)))
			{
				txt_ItemCode.setText(this.getViewState().getString(VS_ITEMCODE));
			}
			// 棚No
			if (!StringUtil.isBlank(this.getViewState().getString(VS_LOCATION)))
			{
				txt_Location.setText(DisplayText.formatLocationnumber(this.getViewState().getString(VS_LOCATION)));
			}
		}
		catch (Exception ex)
		{
			// コネクションロールバック
			if (conn != null)
			{
				conn.rollback();
			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//パラメータを取得
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//ViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//画面名称をセットする
			lbl_SettingName.setResourceKey(title);
		}

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<BR><CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		// 棚No
		String locationno = param.getParameter(ListAsLocationDetailListBusiness.LOCATION_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 棚No
		if (!StringUtil.isBlank(locationno))
		{
			txt_Location.setText(DisplayText.formatLocationnumber(locationno));
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * [パラメーター] <BR>
	 * <DIR>
	 * wkrClrFlg trueの場合全画面を初期化、falseの場合クリア範囲のみ初期化 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者コード [なし/クリアボタン押下時はそのまま] <BR>
	 * パスワード [なし/クリアボタン押下時はそのまま] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 商品コード [なし] <BR>
	 * ケース/ピース区分 ［全て］<BR>
	 * 棚No      ［なし/クリアボタン押下時はそのまま] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR>
	 * @param wkrClrFlg 作業者コードのクリアを行うかどうかのフラグ
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		// 入力項目の初期化を行う
		if (wkrClrFlg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
			// 棚No
			txt_Location.setText("");
		}

		// 荷主コード
		txt_ConsignorCode.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// ケース/ピース区分をケース、ピース、指定なしをリセットする
		rdo_Cpf_Case.setChecked(false);
		rdo_Cpf_Piece.setChecked(false);
		rdo_Cpf_AppointOff.setChecked(false);
		// ケース/ピース区分を全てにセットする
		rdo_Cpf_All.setChecked(true);

		Connection conn = null;

		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsAddStorageSCH();
			MasterAsScheduleParameter param = (MasterAsScheduleParameter) schedule.initFind(conn, null);

			// 荷主コード
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが１件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			if (param != null)
			{
				if (param.getMergeType() == MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE)
				{
					this.getViewState().setBoolean(IS_READ_ONLY_KEY, true);
				}
				else
				{
					this.getViewState().setBoolean(IS_READ_ONLY_KEY, false);
				}
			}
		}
		catch (Exception ex)
		{
			// コネクションロールバック
			if (conn != null)
			{
				conn.rollback();
			}
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	// Event handler methods -----------------------------------------
	/** 
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：メニュー画面に遷移します。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 検索フラグ
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
	}

	/** 
	 * 問合せボタンが押下された際の処理を記述します。<BR>
	 * 空棚と実棚を検索し、ポップアップ表示します。<BR>
	 * ＜検索条件＞<BR>
	 * <DIR>
	 *   ・倉庫ステーションNo.<BR>
	 *   ・荷主コード<BR>
	 *   ・商品コード<BR>
	 *   ・ケースピース区分<BR>
	 *   ・状態（実棚・空PB棚）<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		// 棚別在庫一覧問合せ画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 倉庫ステーションNo から倉庫ナンバーを取得する。
		param.setParameter(ListAsLocationDetailListBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsLocationDetailListBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース/ピース区分
		String casePiece = "";
		if (rdo_Cpf_AppointOff.getChecked())
		{
			// 指定なし
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			// ケース
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			// ピース
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_PIECE;
		}
		else if(rdo_Cpf_All.getChecked())
		{
			// 全て
			casePiece = AsScheduleParameter.CASEPIECE_FLAG_ALL;
		}
		param.setParameter(ListAsLocationDetailListBusiness.CASEPIECEFLAG_KEY, casePiece);
		// 検索対象となる状態
		String[] searchStatus = {Integer.toString(AsScheduleParameter.STATUS_STORAGED), 
								 Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE)};
		param.setParameter(ListAsLocationDetailListBusiness.STATUS_KEY, searchStatus);

		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrslocationdetaillist/ListAsLocationDetailList.do", param, "/progress.do");
		
	}

	/** 
	 * クリアボタンが押されたときに呼ばれます<BR>
	 * 概要：画面のクリア処理を行います。<BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 画面を初期化します
		setInitView(false);

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/** 
	 * 棚明細ボタンが押されたときに呼ばれます<BR>
	 * 棚明細一覧リストボックスを表示します。<BR>
	 * [パラメータ]
	 * <DIR>
	 *   ・棚No.（ステーションNo.）<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Detail_Click(ActionEvent e) throws Exception
	{
		// 棚明細一覧問合せ画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		Connection conn = null;
		try
		{
			if( !StringUtil.isBlank(txt_Location.getText()) )
			{
				// 画面表示の棚からステーションNo.を生成し、パラメータにセットする
				String stationNo = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString());
				param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY, stationNo);
			}
			else
			{
				// 棚No.は必須入力のため、ここは通らないはず
				throw new ScheduleException();
			}

			// 倉庫ステーションNo.
			param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
			// 倉庫名称
			param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		
			// 処理中画面->結果画面
			redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * 次へボタンを押されたときに呼ばれます。<BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);

		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_Location.validate();

		// パラメータのセット
		AsScheduleParameter param = new AsScheduleParameter();

		// 作業者コード
		param.setWorkerCode(txt_WorkerCode.getText());
		// パスワード
		param.setPassword(txt_Password.getText());
		// 荷主コード
		param.setConsignorCode(txt_ConsignorCode.getText());
		// 商品コード
		param.setItemCode(txt_ItemCode.getText());
		Connection conn = null;
		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			// スケジュールクラスのインスタンス生成
			WmsScheduler schedule = new MasterAsAddStorageSCH(conn);

			MasterAsScheduleParameter mergeParam = new MasterAsScheduleParameter();
			// 荷主コード
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			// 商品コード
			mergeParam.setItemCode(txt_ItemCode.getText());
			
			// 補完処理を行った結果で、入力チェックを行う
			mergeParam = (MasterAsScheduleParameter) schedule.query(conn, mergeParam)[0];
            // 各コードの最終使用日を保持する
			this.getViewState().setString(CONSIG_DATE_KEY, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
			this.getViewState().setString(ITEM_DATE_KEY, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));

			// 棚No
			String tstation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString());
			param.setLocationNo(tstation);

			// スケジュールクラスで入力パラメーターチェックで問題がなければ
			// viewStateに画面パラメータをセットして在庫メンテナンス修正・削除(詳細)へ遷移します。
			if (schedule.nextCheck(conn, param))
			{
				// 作業者コード
				viewState.setString(VS_WORKERCODE, txt_WorkerCode.getText());
				// パスワード
				viewState.setString(VS_PASSWORD, txt_Password.getText());
				// 倉庫ステーションナンバー
				viewState.setString(VS_WHNUMBER, pul_WareHouse.getSelectedValue());
				// 棚No
				viewState.setString(VS_LOCATION, tstation);
				// タイトル
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());

				// 棚選択画面 -> 入庫情報入力画面
				forward(DO_ADDSTORAGE2);

				if (schedule.getMessage() != null)
				{
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
			else
			{
				// 対象データが存在しない場合は、エラーメッセージを表示します
				if (schedule.getMessage() != null)
				{
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

}
//end of class

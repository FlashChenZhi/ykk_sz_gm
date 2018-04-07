// $Id: MasterAsNoPlanRetrievalBusiness.java,v 1.2 2006/11/10 00:34:38 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsnoplanretrieval;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsbundleitf.ListAsBundleItfBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrscaseitf.ListAsCaseItfBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocation.ListAsLocationBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.master.schedule.MasterAsNoPlanRetrievalSCH;
import jp.co.daifuku.wms.master.schedule.MasterAsScheduleParameter;

/**
 * マスタパッケージ導入時のAS/RS予定外出庫画面です。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/08</TD><TD>toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:34:38 $
 * @author  $Author: suresh $
 */
public class MasterAsNoPlanRetrievalBusiness extends MasterAsNoPlanRetrieval implements WMSConstants
{
	// Class fields --------------------------------------------------

	// ViewState用
	// 荷主コード
	private static final String VSTCONSIGNOR = "CONSIGNOR_CODE";
	// 商品コード
	private static final String VSTITEMCODE = "ITEM_CODE";
	// ケースピース区分
	private static final String VSTCASEPIECE = "CASE_PIECE_FLAG";
	// 開始棚
	private static final String VSTLOCATIONFROM = "LOCATION_FROM";
	// 終了棚
	private static final String VSTLOCATIONTO = "LOCATION_TO";
	// ケースITF
	private static final String VSTCASEITF = "CASE_ITF";
	// ボールITF
	private static final String VSTBUNDLEITF = "BUNDLE_ITF";

	// プルダウン出庫
	private static final String STATION_RETRIEVAL = "11";

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
		Connection conn = null;
		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 本HttpのLocaleを取得
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			// プルダウン表示データ（保管エリア）
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			// プルダウン表示データ（作業場）
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_RETRIEVAL, true, "");
			// プルダウン表示データ（ステーション）
			String[] stno = pull.getStationPulldownData(conn, STATION_RETRIEVAL, true, "");

			// プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);

			// 子プルダウンを登録
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			
			// 初期表示をする
			setFirstDisp();
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
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
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
			// ヘルプファイルへのパスをセットする
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		// 出庫開始ボタン押下時確認ダイアログMSG-W0032=出庫しますか？
		btn_RetrievalStart.setBeforeConfirm("MSG-W0032");

		// 一覧クリアボタン押下時確認ダイアログMSG-W0012=一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");

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
		// 開始棚
		String startlocation = param.getParameter(ListAsLocationBusiness.STARTLOCATION_KEY);
		// 終了棚
		String endlocation = param.getParameter(ListAsLocationBusiness.ENDLOCATION_KEY);
		// ケースITF
		String caseitf = param.getParameter(ListAsCaseItfBusiness.CASEITF_KEY);
		// ボールITF
		String bundleitf = param.getParameter(ListAsBundleItfBusiness.BUNDLEITF_KEY);

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
		// 開始棚
		if (!StringUtil.isBlank(startlocation))
		{
			txt_StartLocation.setText(DisplayText.formatLocationnumber(startlocation));
		}
		// 終了棚
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(DisplayText.formatLocationnumber(endlocation));
		}
		// ケースITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		// ボールITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 画面初期表示、クリア処理の場合にこのメソッドが呼ばれます。
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsNoPlanRetrievalSCH();
			MasterAsScheduleParameter param = (MasterAsScheduleParameter) schedule.initFind(conn, null);
			
			if (MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE == param.getMergeType())
			{
				txt_CustomerName.setReadOnly(true);
			}

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが1件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが1件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}

			// 商品コード
			txt_ItemCode.setText("");

			// ケース/ピース区分を全てにセットする
			rdo_Cpf_All.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);

			// 開始棚
			txt_StartLocation.setText("");

			// 終了棚
			txt_EndLocation.setText("");

			// ケースITF
			txt_CaseItf.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 出荷先コード
			txt_CustomerCode.setText("");
			// 出荷先名称
			txt_CustomerName.setText("");
			
			// 予定出庫作業リストを発行するをチェックする
			chk_RetrievalPlanData.setChecked(true);

			// ボタン押下を不可にする
			// 出庫開始ボタン
			btn_RetrievalStart.setEnabled(false);
			// 出庫数クリアボタン
			btn_RetrievalQtyClear.setEnabled(false);
			// 全数選択ボタン
			btn_AllCheck.setEnabled(false);
			// 全選択解除ボタン
			btn_allCheckClear.setEnabled(false);
			// 一覧クリアボタン
			btn_ListClear.setEnabled(false);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
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
	 * ため打ちエリアに値をセットします。
	 * @param listParams DBから取得したパラメタクラス
	 * @throws Exception  全ての例外を報告します。 
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		// 行をすべて削除
		lst_NoPlanRetrieval.clearRow();

		AsScheduleParameter[] viewParam = (AsScheduleParameter[]) listParams;

		// 商品名称
		String label_itemname = DisplayText.getText("LBL-W0103");
		// ｹｰｽITF
		String label_caseitf = DisplayText.getText("LBL-W0010");
		// ﾎﾞｰﾙITF
		String label_bundleitf = DisplayText.getText("LBL-W0006");

		for (int i = 0; i < viewParam.length; i++)
		{
			// 行追加
			lst_NoPlanRetrieval.setCurrentRow(i + 1);
			lst_NoPlanRetrieval.addRow();
			lst_NoPlanRetrieval.setValue(1, viewParam[i].getItemCode());
			lst_NoPlanRetrieval.setValue(2, viewParam[i].getCasePieceFlagNameDisp());
			lst_NoPlanRetrieval.setValue(3, DisplayText.formatLocation(viewParam[i].getLocationNo()));
			lst_NoPlanRetrieval.setValue(9, viewParam[i].getITF());
			lst_NoPlanRetrieval.setValue(10, viewParam[i].getItemName());
			lst_NoPlanRetrieval.setValue(14, viewParam[i].getBundleITF());

			// 数値型からカンマ編集された文字列への変換を行います。
			lst_NoPlanRetrieval.setValue(
				4,
				WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_NoPlanRetrieval.setValue(
				5,
				WmsFormatter.getNumFormat(viewParam[i].getAllocateCaseQty()));
			lst_NoPlanRetrieval.setValue(
				11,
				WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_NoPlanRetrieval.setValue(
				12,
				WmsFormatter.getNumFormat(viewParam[i].getAllocatePieceQty()));

			// 最終更新日時
			String lastupdate = WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate());

			// 区分+在庫ID+最終更新日時+出庫ケース数+出庫ピース数+在庫数
			List list = new Vector();
			list.add(viewParam[i].getCasePieceFlag());
			list.add(viewParam[i].getStockId());
			list.add(lastupdate);
			list.add("");
			list.add("");
			list.add(viewParam[i].getLocationNo());
			lst_NoPlanRetrieval.setValue(0, CollectionUtils.getConnectedString(list));

			// 出荷ケース数、出荷ピース数に空白をセット
			lst_NoPlanRetrieval.setValue(6, (""));
			lst_NoPlanRetrieval.setValue(13, (""));

			// 賞味期限をセット
			lst_NoPlanRetrieval.setValue(8, viewParam[i].getUseByDate());

			// tool tipをセット
			ToolTipHelper toolTip = new ToolTipHelper();
			// 商品名称
			toolTip.add(label_itemname, viewParam[i].getItemName());
			// ｹｰｽITF			
			toolTip.add(label_caseitf, viewParam[i].getITF());
			// ﾎﾞｰﾙITF		
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());

			lst_NoPlanRetrieval.setToolTip(i + 1, toolTip.getText());

		}

		// ボタン押下を可能にする
		// 出庫開始タン
		btn_RetrievalStart.setEnabled(true);
		// 出荷数クリアボタン
		btn_RetrievalQtyClear.setEnabled(true);
		// 全件選択ボタン
		btn_AllCheck.setEnabled(true);
		// 全選択解除ボタン
		btn_allCheckClear.setEnabled(true);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(true);
	}

	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText()
	{
		WmsCheckker checker = new WmsCheckker();

		// 出荷先コード
		if (!checker.checkContainNgText(txt_CustomerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// 出荷先名称
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
	}

	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * <BR>
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText(int rowNo)
	{
		WmsCheckker checker = new WmsCheckker();

		lst_NoPlanRetrieval.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_NoPlanRetrieval.getValue(8) ,
				rowNo,
				lst_NoPlanRetrieval.getListCellColumn(8).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
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
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
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
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
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
	 * 開始棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
	 *       倉庫No <BR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 *       開始棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSStartLocation_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		// 開始棚
		if (!StringUtil.isBlank(txt_StartLocation.getString()))
		{
			String w_stlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, w_stlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getString());
		}

		// 開始フラグ
		param.setParameter(
			ListAsLocationBusiness.RANGELOCATION_KEY,
			AsScheduleParameter.RANGE_START);
		// 検索フラグ
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrslocation/ListAsLocation.do", param, "/progress.do");
	}

	/** 
	 * 終了棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
	 *       倉庫No <BR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 *       終了棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSEndLocation_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsLocationBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		// 終了棚
		if (!StringUtil.isBlank(txt_EndLocation.getString()))
		{
			String w_enlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, w_enlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getString());
		}

		// 終了フラグ
		param.setParameter(ListAsLocationBusiness.RANGELOCATION_KEY, AsScheduleParameter.RANGE_END);

		// 検索フラグ
		param.setParameter(ListAsLocationBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);

		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrslocation/ListAsLocation.do", param, "/progress.do");
	}

	/** 
	 * ケースITFの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件でケースITF一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
	 *       倉庫No <BR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 *       開始棚 <BR>
	 *       終了棚 <BR>
	 *       ケースITF <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSCaseItf_Click(ActionEvent e) throws Exception
	{
		// ケースITF検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsCaseItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		// 開始棚
		if (!StringUtil.isBlank(txt_StartLocation.getString()))
		{
			String w_stlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, w_stlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getString());
		}

		// 終了棚
		if (!StringUtil.isBlank(txt_EndLocation.getString()))
		{
			String w_enlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, w_enlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getString());
		}

		// ケースITF
		param.setParameter(ListAsCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());

		// 検索フラグ
		param.setParameter(ListAsCaseItfBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);

		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrscaseitf/ListAsCaseItf.do", param, "/progress.do");
	}

	/** 
	 * ボールITFの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件でボールITF一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
	 *       倉庫No <BR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 *       開始棚 <BR>
	 *       終了棚 <BR>
	 *       ケースITF <BR>
	 *       ボールITF <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSBundleItf_Click(ActionEvent e) throws Exception
	{
		// ボールITF検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫No
		param.setParameter(ListAsLocationBusiness.AREANO_KEY, pul_WareHouse.getSelectedValue());
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListAsBundleItfBusiness.CASEPIECEFLAG_KEY,
				AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
		}

		// 開始棚
		if (!StringUtil.isBlank(txt_StartLocation.getString()))
		{
			String w_stlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, w_stlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.STARTLOCATION_KEY, txt_StartLocation.getString());
		}

		// 終了棚
		if (!StringUtil.isBlank(txt_EndLocation.getString()))
		{
			String w_enlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, w_enlocation);
		}
		else
		{
			param.setParameter(ListAsLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getString());
		}

		// ケースITF
		param.setParameter(ListAsCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());
		// ボールITF
		param.setParameter(ListAsBundleItfBusiness.BUNDLEITF_KEY, txt_BundleItf.getText());

		// 検索フラグ
		param.setParameter(ListAsBundleItfBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);

		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrsbundleitf/ListAsBundleItf.do", param, "/progress.do");
	}

	/** 
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアの入力項目を条件に、在庫情報を検索し、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力エリアの入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   2.スケジュールを開始します。<BR>
	 *   3.ためうちエリアに表示を行います。<BR>
	 *   4.出荷数の初期入力を行うにチェックなし:作業者入力領域にNULLをセットします。<BR>
	 *   5.出庫開始ボタン･出庫数クリアボタン･全件選択ボタン･全選択解除ボタン･一覧クリアボタンを有効にします。<BR>
	 *   6.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   [リストセルの列番号一覧]<BR>
	 *   <BR>
	 *   <DIR>
	 *      1.商品コード <BR>
	 *      2.区分 <BR>
	 * 		3.出庫棚 <BR>
	 * 		4.ケース入数 <BR>
	 * 		5.引当可能ケース数 <BR>
	 * 		6.出庫ケース数 <BR>
	 * 		7.全数 <BR>
	 * 		8.賞味期限 <BR>
	 * 		9.ケースITF <BR>
	 * 		10.商品名称 <BR>
	 * 		11.ボール入数 <BR>
	 * 		12.引当可能ピース数 <BR>
	 * 		13.出庫ピース数 <BR>
	 * 		14.ボールITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 入力エラーの場合、ためうちエリアをクリアする。
			btn_ListClear_Click(e);

			// 入力チェック
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();

			// パターンマッチング文字
			txt_ItemCode.validate(false);
			txt_StartLocation.validate(false);
			txt_EndLocation.validate(false);
			txt_CaseItf.validate(false);
			txt_BundleItf.validate(false);
			txt_CustomerCode.validate(false);
			txt_CustomerName.validate(false);

			String startLocation = txt_StartLocation.getText();
			String endLocation = txt_EndLocation.getText();

			// 開始棚は終了棚より以下であること
			if (!StringUtil.isBlank(txt_StartLocation.getText())
				&& !StringUtil.isBlank(txt_EndLocation.getText()))
			{
				if (startLocation.compareTo(endLocation) > 0)
				{
					// 6023057 = {0}には{1}以上の値を入力してください。
					message.setMsgResourceKey(
						"6023057"
							+ Message.MSG_DELIM
							+ DisplayText.getText(lbl_StartLocation.getResourceKey())
							+ Message.MSG_DELIM
							+ DisplayText.getText(lbl_EndLocation.getResourceKey()));
					return;
				}
			}
	
			// eWareNavi用入力文字チェック
			if (!checkContainNgText())
			{
				return;
			}
			
			// スケジュールパラメータへセット
			AsScheduleParameter param = new AsScheduleParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 倉庫No
			param.setAreaNo(pul_WareHouse.getSelectedValue());
			// 荷主コード
			param.setConsignorCodeDisp(txt_ConsignorCode.getText());
			// 商品コード
			param.setItemCodeDisp(txt_ItemCode.getText());

			// ケース/ピース区分
			if (rdo_Cpf_All.getChecked())
			{
				// 全て
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_Cpf_Case.getChecked())
			{
				// ケース
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				// ピース
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_Cpf_AppointOff.getChecked())
			{
				// 指定無し
				param.setCasePieceFlagDisp(AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
			}

			// 開始棚
			if (!StringUtil.isBlank(txt_StartLocation.getText()))
			{
				String startlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_StartLocation.getString());
				param.setFromLocationNo(startlocation);
			}
			else
			{
				param.setFromLocationNo(txt_StartLocation.getText());
			}

			// 終了棚
			if (!StringUtil.isBlank(txt_EndLocation.getText()))
			{
				String endlocation = DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_EndLocation.getString());
				param.setToLocationNo(endlocation);
			}
			else
			{
				param.setToLocationNo(txt_EndLocation.getText());
			}

			// ケースITF
			param.setITFDisp(txt_CaseItf.getText());
			// ボールITF
			param.setBundleITFDisp(txt_BundleItf.getText());
			// 出荷先コード
			param.setCustomerCode(txt_CustomerCode.getText());
			// 出荷先名称
			param.setCustomerName(txt_CustomerName.getText());

			// ViewStateへの保存（処理後の再検索用）
			// 荷主コード
			this.getViewState().setString(VSTCONSIGNOR, param.getConsignorCodeDisp());
			// 商品コード
			this.getViewState().setString(VSTITEMCODE, param.getItemCodeDisp());
			// ケースピース区分
			this.getViewState().setString(VSTCASEPIECE, param.getCasePieceFlagDisp());
			// 開始棚
			this.getViewState().setString(VSTLOCATIONFROM, param.getFromLocationNo());
			// 終了棚
			this.getViewState().setString(VSTLOCATIONTO, param.getToLocationNo());
			// ケースITF
			this.getViewState().setString(VSTCASEITF, param.getITFDisp());
			// ボールITF
			this.getViewState().setString(VSTBUNDLEITF, param.getBundleITFDisp());

			WmsScheduler schedule = new AsNoPlanRetrievalSCH();
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());

				return;
			}

			// リストセルのセット
			setList(viewParam);

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

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
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsNoPlanRetrievalSCH();
			MasterAsScheduleParameter param = (MasterAsScheduleParameter) schedule.initFind(conn, null);
			
			if (MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE == param.getMergeType())
			{
				txt_CustomerName.setReadOnly(true);
			}

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが1件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
			}

			// 商品コード
			txt_ItemCode.setText("");

			// ケース/ピース区分を全てにセットする
			rdo_Cpf_All.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);

			// 開始棚
			txt_StartLocation.setText("");

			// 終了棚
			txt_EndLocation.setText("");

			// ケースITF
			txt_CaseItf.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 出荷先コード
			txt_CustomerCode.setText("");
			// 出荷先名称
			txt_CustomerName.setText("");
			
			// 予定出庫作業リストを発行するをチェックする
			chk_RetrievalPlanData.setChecked(true);

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
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
	 * 出庫開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、予定外出庫設定を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.登録を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "出庫しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ]<BR>
	 * 					<DIR>
	 * 						作業者コード <BR>
	 * 						パスワード <BR>
	 * 						荷主名称 <BR>
	 * 						荷主名称 <BR>
	 * 						予定外出庫作業リスト発行区分 <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.ケース/ピース区分 <BR>
	 *						ためうち.ロケーションNo. <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.ケース出庫数 <BR>
	 *						ためうち.ピース出庫数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールITF <BR>
	 *						ためうち.賞味期限 <BR>
	 *						ためうち.全数区分 <BR>
	 *						ためうち.在庫ID <BR>
	 *						ためうち.最終更新日時 <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時は入力エリア、ためうちの登録情報をクリアします。<BR>
	 *				3.ためうちの情報を再表示します。 <BR>
	 *              4.ためうち情報が0件だった場合、出庫開始･出庫数クリア･全件選択･全選択解除･一覧クリアボタンを <br>
	 *                使用不可にします。 <BR>
	 *              5.ためうち情報が0件だった場合、ためうちの荷主コード･荷主名称をクリアします。 <BR>
	 *    			falseの時はスケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 *      1.商品コード <BR>
	 *      2.区分 <BR>
	 * 		3.出庫棚 <BR>
	 * 		4.ケース入数 <BR>
	 * 		5.引当可能ケース数 <BR>
	 * 		6.出庫ケース数 <BR>
	 * 		7.全数 <BR>
	 * 		8.賞味期限 <BR>
	 * 		9.ケースITF <BR>
	 * 		10.商品名称 <BR>
	 * 		11.ボール入数 <BR>
	 * 		12.引当可能ピース数 <BR>
	 * 		13.出庫ピース数 <BR>
	 * 		14.ボールITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_RetrievalStart_Click(ActionEvent e) throws Exception
	{
		// 入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();

		txt_CustomerCode.validate(false);
		txt_CustomerName.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		
		// 最大行数を取得
		int index = lst_NoPlanRetrieval.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				// 行指定
				lst_NoPlanRetrieval.setCurrentRow(i);
				// 指定した列(賞味期限)のパターンマッチング文字チェックを行う
				lst_NoPlanRetrieval.validate(8, false);

				if (!checkContainNgText(i))
				{
					return;
				}
			}
			catch (ValidateException ve)
			{
				// メッセージをセット
				String errorMessage =
					MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		Connection conn = null;
		try
		{
			WmsScheduler schedule = new MasterAsNoPlanRetrievalSCH();
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			MasterAsScheduleParameter queryParam = new MasterAsScheduleParameter();
			queryParam.setProcessType(MasterAsScheduleParameter.PROCESS_TYPE_MERGE);
			queryParam.setConsignorCode(this.getViewState().getString(VSTCONSIGNOR));
			queryParam.setCustomerCode(txt_CustomerCode.getText());
			queryParam.setCustomerName(txt_CustomerName.getText());
			MasterAsScheduleParameter[] mergeParam = (MasterAsScheduleParameter[]) schedule.query(conn, queryParam);
			if (mergeParam != null && mergeParam.length != 0)
			{
				txt_CustomerCode.setText(mergeParam[0].getCustomerCode());
				txt_CustomerName.setText(mergeParam[0].getCustomerName());
			}
			else
			{
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
				return;
				
			}

			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				// 行指定
				lst_NoPlanRetrieval.setCurrentRow(i);

				// 全数出庫にチェックがはいっていない場合のみチェックする。
				if (lst_NoPlanRetrieval.getChecked(7) == false)
				{
					// リストセルから連結したパラメータを取得して、入力された値と違いがある場合、パラメータに値をセットする。
					// 出庫ケース数
					if (CollectionUtils
						.getString(3, lst_NoPlanRetrieval.getValue(0))
						.equals(lst_NoPlanRetrieval.getValue(6)))
					{
						// 出庫ピース数
						if (CollectionUtils
							.getString(4, lst_NoPlanRetrieval.getValue(0))
							.equals(lst_NoPlanRetrieval.getValue(13)))
						{
							continue;
						}
					}
				}

				// スケジュールパラメータへセット
				MasterAsScheduleParameter param = new MasterAsScheduleParameter();
				param.setConsignorCode(this.getViewState().getString(VSTCONSIGNOR));
				param.setProcessType(MasterAsScheduleParameter.PROCESS_TYPE_QUERY);
				// エリアNo.
				param.setAreaNo(pul_WareHouse.getSelectedValue());
				// 作業者コード
				param.setWorkerCode(txt_WorkerCode.getText());
				// パスワード
				param.setPassword(txt_Password.getText());
				// 出荷先コード
				param.setCustomerCode(txt_CustomerCode.getText());
				// 出荷先名称
				param.setCustomerName(txt_CustomerName.getText());
				// 搬送先ステーション
				param.setToStationNo(pul_Station.getSelectedValue());
				if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
				{
					// ステーションに「自動振分け」が指定された場合は、作業場をセットする。
					param.setSagyoba(pul_WorkPlace.getSelectedValue());
				}

				// 端末No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());

				// 予定外出庫作業リスト発行
				param.setListFlg(chk_RetrievalPlanData.getChecked());
				param.setItemCode(lst_NoPlanRetrieval.getValue(1));

				// カンマ編集された数値から数値型への変換を行います。
				param.setEnteringQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(4)));
				param.setAllocateCaseQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(5)));

				// 全数出庫にチェックがはいっている場合は、出庫数に全数を出庫する。
				if (lst_NoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalCaseQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(5)));
				}
				else
				{
					if (lst_NoPlanRetrieval.getValue(6).equals(""))
					{
						param.setRetrievalCaseQty(0);
					}
					else
					{
						param.setRetrievalCaseQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(6)));
					}
				}

				param.setTotalFlag(lst_NoPlanRetrieval.getChecked(7));
				param.setUseByDate(lst_NoPlanRetrieval.getValue(8));
				param.setITF(lst_NoPlanRetrieval.getValue(9));
				param.setItemName(lst_NoPlanRetrieval.getValue(10));

				// カンマ編集された数値から数値型への変換を行います。
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(11)));
				param.setAllocatePieceQty(WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(12)));

				// 全数出庫にチェックがはいっている場合は、出庫数に全数を出庫する。
				if (lst_NoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(12)));
				}
				else
				{
					if (lst_NoPlanRetrieval.getValue(13).equals(""))
					{
						param.setRetrievalPieceQty(0);
					}
					else
					{
						param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_NoPlanRetrieval.getValue(13)));
					}
				}

				param.setBundleITF(lst_NoPlanRetrieval.getValue(14));

				// HIDDEN項目セット(区分(0),在庫ID(1),最終更新日時(2),棚No.(5))
				String hidden = lst_NoPlanRetrieval.getValue(0);
				param.setCasePieceFlag(CollectionUtils.getString(0, hidden));
				param.setStockId(CollectionUtils.getString(1, hidden));
				// 最終更新日時
				// String型(YYYYMMDDHHMMSS)からDate型への変換を行います。
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, hidden)));
				param.setLocationNo(CollectionUtils.getString(5, hidden));

				// 行No.を保持しておく
				param.setRowNo(i);

				// 出庫開始後の再検索の検索条件をセット
				param.setConsignorCodeDisp(this.getViewState().getString(VSTCONSIGNOR));
				param.setItemCodeDisp(this.getViewState().getString(VSTITEMCODE));
				param.setCasePieceFlagDisp(this.getViewState().getString(VSTCASEPIECE));
				param.setFromLocationNo(this.getViewState().getString(VSTLOCATIONFROM));
				param.setToLocationNo(this.getViewState().getString(VSTLOCATIONTO));
				param.setITFDisp(this.getViewState().getString(VSTCASEITF));
				param.setBundleITFDisp(this.getViewState().getString(VSTBUNDLEITF));

				vecParam.addElement(param);
			}
			if (vecParam.size() <= 0)
			{
				// 6023154 = 更新対象データがありません。
				message.setMsgResourceKey("6023154");
				return;
			}

			MasterAsScheduleParameter[] paramArray = new MasterAsScheduleParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			AsScheduleParameter[] viewParam =
				(AsScheduleParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();

				// 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
				SendRequestor req = new SendRequestor();
				req.retrieval();

				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
				// 対象データがなくなった場合、ためうちエリアをクリアする。
				btn_ListClear_Click(e);

				if (viewParam.length != 0)
				{
					// リストセルのセット
					setList(viewParam);
				}

			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			conn.rollback();
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * 出庫数クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちの出庫ケース数･出庫ピース数･全数を全てクリアします。 <BR>
	 * <BR>
	 * <DIR>
	 *     1.ためうちの出庫ケース数･出庫ピース数を全てクリアします。 <BR>
	 *     2.ためうちの全数を全てfalseにします。 <BR>
	 *     3.カーソルを作業者コードにセットします。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_RetrievalQtyClear_Click(ActionEvent e) throws Exception
	{
		// リストセルのセット
		for (int i = 1; i < lst_NoPlanRetrieval.getMaxRows(); i++)
		{
			lst_NoPlanRetrieval.setCurrentRow(i);
			lst_NoPlanRetrieval.setValue(6, (""));
			lst_NoPlanRetrieval.setChecked(7, false);
			lst_NoPlanRetrieval.setValue(13, (""));
		}
	}

	/** 
	 * 全件選択ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちの情報を全て全数状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *     1.ためうちの全数を全てチェックありにします。 <BR>
	 *     2.ためうちの出庫ケース数に全て引当可能ケース数の値を入れます。 <BR>
	 *     3.ためうちの出庫ピース数に全て引当可能ピース数の値を入れます。 <BR>
	 *     4.カーソルを作業者コードにセットします。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		// リストセルのセット
		for (int i = 1; i < lst_NoPlanRetrieval.getMaxRows(); i++)
		{
			lst_NoPlanRetrieval.setCurrentRow(i);
			lst_NoPlanRetrieval.setChecked(7, true);
		}
	}

	/** 
	 * 全選択解除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちの情報の全数を全てチェック無しにします。<BR>
	 * <BR>
	 * <DIR>
	 *     1.ためうちの全数を全てチェックなしにします。 <BR>
	 *     2.カーソルを作業者コードにセットします。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_allCheckClear_Click(ActionEvent e) throws Exception
	{
		// リストセルのセット
		for (int i = 1; i < lst_NoPlanRetrieval.getMaxRows(); i++)
		{
			lst_NoPlanRetrieval.setCurrentRow(i);
			lst_NoPlanRetrieval.setChecked(7, false);
		}
	}

	/** 
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 * 	    "一覧入力情報がクリアされます。宜しいですか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.ためうちの表示情報を全てクリアします。<BR>
	 *				2.出荷開始ボタン･出庫数クリアボタン･全件選択･全選択解除･一覧クリアボタンを無効にします。<BR>
	 *				3.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行をすべて削除
		lst_NoPlanRetrieval.clearRow();

		// ボタン押下を不可にする
		//出庫開始ボタン
		btn_RetrievalStart.setEnabled(false);
		// 出荷数クリアボタン
		btn_RetrievalQtyClear.setEnabled(false);
		// 全件選択
		btn_AllCheck.setEnabled(false);
		// 全選択解除
		btn_allCheckClear.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);
	}

}
//end of class

// $Id: MasterNoPlanRetrievalBusiness.java,v 1.2 2006/11/10 00:35:06 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masternoplanretrieval;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.master.schedule.MasterNoPlanRetrievalSCH;
import jp.co.daifuku.wms.master.schedule.MasterStockControlParameter;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockbundleitf.ListStockBundleItfBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockcaseitf.ListStockCaseItfBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * 予定外出庫設定画面クラスです。<BR>
 * 予定外出庫設定を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが表示用のデータを検索します。<BR>
 *   スケジュールからためうちエリア出力用のデータを受け取り、ためうちエリアに出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   商品コード* <BR>
 *   ケース/ピース区分* <BR>
 *   開始棚 <BR>
 *   終了棚 <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   出荷先コード <BR>
 *   出荷先名称 <BR>
 *   <BR>
 *   [出力用のデータ] <BR>
 *   <BR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分 <BR>
 *   ロケーションNo. <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   引当可能ケース数((在庫数-引当数)/ケース入数) <BR>
 *   引当可能ピース数((在庫数-引当数)%ケース入数) <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   在庫ID <BR>
 *   最終更新日時 <BR>
 * </DIR>
 * <BR>
 * 2.出庫開始ボタン押下処理(<CODE>btn_RetrievalStart_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが予定外出庫設定を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード <BR>
 *   荷主名称* <BR>
 *   商品コード* <BR>
 *   商品名称* <BR>
 *   ケース/ピース区分* <BR>
 *   ロケーションNo.* <BR>
 *   ケース入数* <BR>
 *   ボール入数* <BR>
 *   ケース出庫数* <BR>
 *   ピース出庫数* <BR>
 *   ケースITF* <BR>
 *   ボールITF* <BR>
 *   賞味期限 <BR>
 *   予定外出庫作業リスト発行区分* <BR>
 *   全数区分* <BR>
 *   在庫ID* <BR>
 *   最終更新日時* <BR>
 *   <BR>
 *   [出力用のデータ] <BR>
 *   <BR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分 <BR>
 *   ロケーションNo. <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   引当可能ケース数((在庫数-引当数)/ケース入数) <BR>
 *   引当可能ピース数((在庫数-引当数)%ケース入数) <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   在庫ID <BR>
 *   最終更新日時 <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:35:06 $
 * @author  $Author: suresh $
 */
public class MasterNoPlanRetrievalBusiness extends MasterNoPlanRetrieval implements WMSConstants
{
	// Class fields --------------------------------------------------
	
	// ViewState用
	// 荷主コード
	protected static final String VSTCONSIGNOR = "CONSIGNOR_CODE";
	// 商品コード
	protected static final String VSTITEMCODE = "ITEM_CODE";
	// ケースピース区分
	protected static final String VSTCASEPIECE = "CASE_PIECE_FLAG";
	// 開始棚
	protected static final String VSTLOCATIONFROM = "LOCATION_FROM";
	// 終了棚
	protected static final String VSTLOCATIONTO = "LOCATION_TO";
	// ケースITF
	protected static final String VSTCASEITF = "CASE_ITF";
	// ボールITF
	protected static final String VSTBUNDLEITF = "BUNDLE_ITF";
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.タイトルを表示します。<BR>
	 *    2.入力エリアを初期設定します。<BR>
	 *    3.スケジュールを開始します。 <BR>
	 *    4.出庫開始ボタン･出庫数クリアボタン･全件選択ボタン･全選択解除ボタン･一覧クリアボタンを無効にします。 <BR>
	 *    5.カーソルを作業コードにセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 作業者コード		[なし] <BR>
	 * パスワード		[なし] <BR>
	 * 荷主コード		[検索値] <BR>
	 * 商品コード		[なし] <BR>
	 * ケース/ピース区分	[全て] <BR>
	 * 開始棚			[なし] <BR>
	 * 終了棚			[なし] <BR>
	 * ケースITF		[なし] <BR>
	 * ボールITF		[なし] <BR>
	 * 出荷先コード		[なし] <BR>
	 * 出荷先名称		[なし] <BR>
	 * 予定外出庫作業リスト発行する[true] <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// 20050822 modify start Y.Kagawa			
			// ケース/ピース区分を全てにセットする
			rdo_Cpf_All.setChecked(true);

			// 予定出庫作業リストを発行するをチェックする
			chk_CommonUse.setChecked(true);

			// ボタン押下を不可にする
			// 出庫開始ボタン
			btn_RetrievalStart.setEnabled(false);
			// 出庫数クリアボタン
			btn_RetrievalQtyClear.setEnabled(false);
			// 全数選択ボタン
			btn_AllCheck.setEnabled(false);
			// 全選択解除ボタン
			btn_AllCheckClear.setEnabled(false);
			// 一覧クリアボタン
			btn_ListClear.setEnabled(false);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new MasterNoPlanRetrievalSCH();
			MasterStockControlParameter param = (MasterStockControlParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが1件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			
			if (MasterStockControlParameter.MERGE_TYPE_OVERWRITE == param.getMergeType())
			{
				txt_CustomerName.setReadOnly(true);
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
	 * <BR>
	 * 概要:確認ダイアログを表示します。<BR>
	 * <BR>
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
		}
		// 出庫開始ボタン押下時確認ダイアログMSG-W0032=出庫しますか？
		btn_RetrievalStart.setBeforeConfirm("MSG-W0032");

		// 一覧クリアボタン押下時確認ダイアログMSG-W0012=一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		// カーソルを作業者コードにセットする
		setFocus(txtl_WorkerCode);
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
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		// 開始棚
		String startlocation = param.getParameter(ListStockLocationBusiness.STARTLOCATION_KEY);
		// 終了棚
		String endlocation = param.getParameter(ListStockLocationBusiness.ENDLOCATION_KEY);
		// ケースITF
		String caseitf = param.getParameter(ListStockCaseItfBusiness.CASEITF_KEY);
		// ボールITF
		String bundleitf = param.getParameter(ListStockBundleItfBusiness.BUNDLEITF_KEY);

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
			txt_StartLocation.setText(startlocation);
		}
		// 終了棚
		if (!StringUtil.isBlank(endlocation))
		{
			txt_EndLocation.setText(endlocation);
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

	/**
	 * ため打ちエリアに値をセットします。
	 * @param listParams DBから取得したパラメタクラス
	 * @throws Exception  全ての例外を報告します。 
	 */
	protected void setList(Parameter[] listParams) throws Exception
	{
		// 行をすべて削除
		lst_SNoPlanRetrieval.clearRow();

		StockControlParameter param = (StockControlParameter) listParams[0];

		// 荷主コード(読み取り専用)
		txt_RConsignorCode.setText(param.getConsignorCode());
		// 荷主名称(読み取り専用)
		txt_RConsignorName.setText(param.getConsignorName());

		StockControlParameter[] viewParam = (StockControlParameter[]) listParams;

		// 商品名称
		String label_itemname = DisplayText.getText("LBL-W0103");
		// ｹｰｽITF
		String label_caseitf = DisplayText.getText("LBL-W0010");
		// ﾎﾞｰﾙITF
		String label_bundleitf = DisplayText.getText("LBL-W0006");

		for (int i = 0; i < viewParam.length; i++)
		{
			// 行追加
			lst_SNoPlanRetrieval.setCurrentRow(i + 1);
			lst_SNoPlanRetrieval.addRow();

			lst_SNoPlanRetrieval.setValue(1, viewParam[i].getItemCode());
			lst_SNoPlanRetrieval.setValue(2, viewParam[i].getCasePieceFlagName());
			lst_SNoPlanRetrieval.setValue(3, viewParam[i].getLocationNo());
			lst_SNoPlanRetrieval.setValue(9, viewParam[i].getITF());
			lst_SNoPlanRetrieval.setValue(10, viewParam[i].getItemName());
			lst_SNoPlanRetrieval.setValue(14, viewParam[i].getBundleITF());

			// 数値型からカンマ編集された文字列への変換を行います。
			lst_SNoPlanRetrieval.setValue(
				4,
				WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_SNoPlanRetrieval.setValue(
				5,
				WmsFormatter.getNumFormat(viewParam[i].getAllocateCaseQty()));
			lst_SNoPlanRetrieval.setValue(
				11,
				WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_SNoPlanRetrieval.setValue(
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
			lst_SNoPlanRetrieval.setValue(0, CollectionUtils.getConnectedString(list));

			// 出荷ケース数、出荷ピース数に空白をセット
			lst_SNoPlanRetrieval.setValue(6, (""));
			lst_SNoPlanRetrieval.setValue(13, (""));

			// 賞味期限をセット
			lst_SNoPlanRetrieval.setValue(8, viewParam[i].getUseByDate());
			
			// tool tipをセット
			ToolTipHelper toolTip = new ToolTipHelper();
			// 商品名称
			toolTip.add(label_itemname, viewParam[i].getItemName());
			// ｹｰｽITF			
			toolTip.add(label_caseitf, viewParam[i].getITF());
			// ﾎﾞｰﾙITF		
			toolTip.add(label_bundleitf, viewParam[i].getBundleITF());

			lst_SNoPlanRetrieval.setToolTip(i + 1, toolTip.getText());

		}

		// ボタン押下を可能にする
		// 出庫開始タン
		btn_RetrievalStart.setEnabled(true);
		// 出荷数クリアボタン
		btn_RetrievalQtyClear.setEnabled(true);
		// 全件選択ボタン
		btn_AllCheck.setEnabled(true);
		// 全選択解除ボタン
		btn_AllCheckClear.setEnabled(true);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(true);
	}

	// Private methods -----------------------------------------------

	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
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
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText(int rowNo)
	{
		WmsCheckker checker = new WmsCheckker();

		lst_SNoPlanRetrieval.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SNoPlanRetrieval.getValue(8) ,
				rowNo,
				lst_SNoPlanRetrieval.getListCellColumn(8).getResourceKey() )
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
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, MasterStockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststockconsignor/ListStockConsignor.do",
			param,
			"/progress.do");
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
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, MasterStockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect("/stockcontrol/listbox/liststockitem/ListStockItem.do", param, "/progress.do");
	}

	/** 
	 * 開始棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
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
	public void btn_PSearchStrt_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 開始棚
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_StartLocation.getText());
		// 開始フラグ
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			MasterStockControlParameter.RANGE_START);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, MasterStockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	/** 
	 * 終了棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
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
	public void btn_PSearchEd_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 終了棚
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_EndLocation.getText());
		// 終了フラグ
		param.setParameter(
			ListStockLocationBusiness.RANGELOCATION_KEY,
			MasterStockControlParameter.RANGE_END);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, MasterStockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
	}

	/** 
	 * ケースITFの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件でケースITF一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
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
	public void btn_PSearchCase_Click(ActionEvent e) throws Exception
	{
		// ケースITF検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 開始棚
		param.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		// 終了棚
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		// ケースITF
		param.setParameter(ListStockCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, MasterStockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststockcaseitf/ListStockCaseItf.do",
			param,
			"/progress.do");
	}

	/** 
	 * ボールITFの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件でボールITF一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ]<BR>
	 *    <DIR>
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
	public void btn_PSearchBdl_Click(ActionEvent e) throws Exception
	{
		// ボールITF検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		// 全て
		if (rdo_Cpf_All.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_Cpf_Case.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_Cpf_Piece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_Cpf_AppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				MasterStockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 開始棚
		param.setParameter(
			ListStockLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		// 終了棚
		param.setParameter(ListStockLocationBusiness.ENDLOCATION_KEY, txt_EndLocation.getText());
		// ケースITF
		param.setParameter(ListStockCaseItfBusiness.CASEITF_KEY, txt_CaseItf.getText());
		// ボールITF
		param.setParameter(ListStockBundleItfBusiness.BUNDLEITF_KEY, txt_BundleItf.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, MasterStockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststockbundleitf/ListStockBundleItf.do",
			param,
			"/progress.do");
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
			// 入力エラーの場合、ためうちエリアをクリアする。
			btn_ListClear_Click(e);

			// 入力チェック
			txtl_WorkerCode.validate();
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

			// 開始棚は終了棚より以下であること
			if (!StringUtil.isBlank(txt_StartLocation.getText())
				&& !StringUtil.isBlank(txt_EndLocation.getText()))
			{
				if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
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
			StockControlParameter param = new StockControlParameter();
			// 作業者コード
			param.setWorkerCode(txtl_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCodeDisp(txt_ConsignorCode.getText());
			// 商品コード
			param.setItemCodeDisp(txt_ItemCode.getText());

			// ケース/ピース区分
			if (rdo_Cpf_All.getChecked())
			{
				// 全て
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_ALL);
			}
			else if (rdo_Cpf_Case.getChecked())
			{
				// ケース
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				// ピース
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_Cpf_AppointOff.getChecked())
			{
				// 指定無し
				param.setCasePieceFlagDisp(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}

			// 開始棚
			param.setFromLocationNoDisp(txt_StartLocation.getText());
			// 終了棚
			param.setToLocationNoDisp(txt_EndLocation.getText());
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
			this.getViewState().setString(VSTLOCATIONFROM, param.getFromLocationNoDisp());
			// 終了棚
			this.getViewState().setString(VSTLOCATIONTO, param.getToLocationNoDisp());
			// ケースITF
			this.getViewState().setString(VSTCASEITF, param.getITFDisp());
			// ボールITF
			this.getViewState().setString(VSTBUNDLEITF, param.getBundleITFDisp());
			
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterNoPlanRetrievalSCH();
			StockControlParameter[] viewParam =
				(StockControlParameter[]) schedule.query(conn, param);

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
			// 荷主コード
			txt_ConsignorCode.setText("");			
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
			chk_CommonUse.setChecked(true);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
 
			WmsScheduler schedule = new MasterNoPlanRetrievalSCH();
			MasterStockControlParameter param = (MasterStockControlParameter) schedule.initFind(conn, null);

			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが1件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
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
		txtl_WorkerCode.validate();
		txt_Password.validate();

		txt_CustomerCode.validate(false);
		txt_CustomerName.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		
		// 最大行数を取得
		int index = lst_SNoPlanRetrieval.getMaxRows();

		for (int i = 1; i < index; i++)
		{
			try
			{
				// 行指定
				lst_SNoPlanRetrieval.setCurrentRow(i);
				// 指定した列(賞味期限)のパターンマッチング文字チェックを行う
				lst_SNoPlanRetrieval.validate(8, false);

				// eWareNavi用入力文字チェック
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
			WmsScheduler schedule = new MasterNoPlanRetrievalSCH();
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			MasterStockControlParameter queryParam = new MasterStockControlParameter();
			queryParam.setProcessType(MasterStockControlParameter.PROCESS_TYPE_MERGE);
			queryParam.setConsignorCode(txt_RConsignorCode.getText());
			queryParam.setCustomerCode(txt_CustomerCode.getText());
			queryParam.setCustomerName(txt_CustomerName.getText());
			MasterStockControlParameter[] mergeParam = (MasterStockControlParameter[]) schedule.query(conn, queryParam);
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
				lst_SNoPlanRetrieval.setCurrentRow(i);
				
				// 全数出庫にチェックがはいっていない場合のみチェックする。
				if (lst_SNoPlanRetrieval.getChecked(7) == false)
				{
					// リストセルから連結したパラメータを取得して、入力された値と違いがある場合、パラメータに値をセットする。
					// 出庫ケース数
					if (CollectionUtils.getString(3, lst_SNoPlanRetrieval.getValue(0)).equals(lst_SNoPlanRetrieval.getValue(6)))
					{
						// 出庫ピース数
						if (CollectionUtils.getString(4, lst_SNoPlanRetrieval.getValue(0)).equals(lst_SNoPlanRetrieval.getValue(13)))
						{
							continue;
						}
					}
				}
				
				// スケジュールパラメータへセット
				MasterStockControlParameter param = new MasterStockControlParameter();
				param.setProcessType(MasterStockControlParameter.PROCESS_TYPE_QUERY);
				// 作業者コード
				param.setWorkerCode(txtl_WorkerCode.getText());
				// パスワード
				param.setPassword(txt_Password.getText());
				// 出荷先コード
				param.setCustomerCode(txt_CustomerCode.getText());
				// 出荷先名称
				param.setCustomerName(txt_CustomerName.getText());
				
				// 端末No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());	
							
				// 予定外出庫作業リスト発行
				param.setListFlg(chk_CommonUse.getChecked());
				// 荷主コード(読み取り専用)
				param.setConsignorCode(txt_RConsignorCode.getText());
				// 荷主名称(読み取り専用)
				param.setConsignorName(txt_RConsignorName.getText());

				param.setItemCode(lst_SNoPlanRetrieval.getValue(1));
				param.setLocationNo(lst_SNoPlanRetrieval.getValue(3));

				// カンマ編集された数値から数値型への変換を行います。
				param.setEnteringQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(4)));
				param.setAllocateCaseQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(5)));

				// 全数出庫にチェックがはいっている場合は、出庫数に全数を出庫する。
				if (lst_SNoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalCaseQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(5)));
				}
				else
				{
					if (lst_SNoPlanRetrieval.getValue(6).equals(""))
					{
						param.setRetrievalCaseQty(0);
					}
					else
					{
						param.setRetrievalCaseQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(6)));
					}
				}

				param.setTotalFlag(lst_SNoPlanRetrieval.getChecked(7));
				param.setUseByDate(lst_SNoPlanRetrieval.getValue(8));
				param.setITF(lst_SNoPlanRetrieval.getValue(9));
				param.setItemName(lst_SNoPlanRetrieval.getValue(10));

				// カンマ編集された数値から数値型への変換を行います。
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(11)));
				param.setAllocatePieceQty(WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(12)));

				// 全数出庫にチェックがはいっている場合は、出庫数に全数を出庫する。
				if (lst_SNoPlanRetrieval.getChecked(7) == true)
				{
					param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(12)));
				}
				else
				{
					if (lst_SNoPlanRetrieval.getValue(13).equals(""))
					{
						param.setRetrievalPieceQty(0);
					}
					else
					{
						param.setRetrievalPieceQty(
							WmsFormatter.getInt(lst_SNoPlanRetrieval.getValue(13)));
					}
				}

				param.setBundleITF(lst_SNoPlanRetrieval.getValue(14));

				// HIDDEN項目セット(区分(0),在庫ID(1),最終更新日時(2))
				String hidden = lst_SNoPlanRetrieval.getValue(0);
				param.setCasePieceFlag(CollectionUtils.getString(0, hidden));
				param.setStockId(CollectionUtils.getString(1, hidden));
				// 最終更新日時
				// String型(YYYYMMDDHHMMSS)からDate型への変換を行います。
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, hidden)));
					
				// 行No.を保持しておく
				param.setRowNo(i);


				// 出庫開始後の再検索の検索条件をセット
				param.setConsignorCodeDisp(this.getViewState().getString(VSTCONSIGNOR));
				param.setItemCodeDisp(this.getViewState().getString(VSTITEMCODE));
				param.setCasePieceFlagDisp(this.getViewState().getString(VSTCASEPIECE));
				param.setFromLocationNoDisp(this.getViewState().getString(VSTLOCATIONFROM));
				param.setToLocationNoDisp(this.getViewState().getString(VSTLOCATIONTO));
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

			MasterStockControlParameter[] paramArray = new MasterStockControlParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			StockControlParameter[] viewParam = viewParam = (StockControlParameter[]) schedule.startSCHgetParams(conn, paramArray);
				
			if (viewParam == null)
			{
				conn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				conn.commit();
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
		for (int i = 1; i < lst_SNoPlanRetrieval.getMaxRows(); i++)
		{
			lst_SNoPlanRetrieval.setCurrentRow(i);
			lst_SNoPlanRetrieval.setValue(6, (""));
			lst_SNoPlanRetrieval.setChecked(7, false);
			lst_SNoPlanRetrieval.setValue(13, (""));
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
		for (int i = 1; i < lst_SNoPlanRetrieval.getMaxRows(); i++)
		{
			lst_SNoPlanRetrieval.setCurrentRow(i);
			lst_SNoPlanRetrieval.setChecked(7, true);
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
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		// リストセルのセット
		for (int i = 1; i < lst_SNoPlanRetrieval.getMaxRows(); i++)
		{
			lst_SNoPlanRetrieval.setCurrentRow(i);
			lst_SNoPlanRetrieval.setChecked(7, false);
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
		lst_SNoPlanRetrieval.clearRow();

		// 荷主コード(読み取り専用)
		txt_RConsignorCode.setText("");
		// 荷主名称(読み取り専用)
		txt_RConsignorName.setText("");

		// ボタン押下を不可にする
		//出庫開始ボタン
		btn_RetrievalStart.setEnabled(false);
		// 出荷数クリアボタン
		btn_RetrievalQtyClear.setEnabled(false);
		// 全件選択
		btn_AllCheck.setEnabled(false);
		// 全選択解除
		btn_AllCheckClear.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);
	}

}
//end of class

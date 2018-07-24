// $Id: MasterAsNoPlanStorageBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsnoplanstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Vector;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Label;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.master.schedule.MasterAsNoPlanStorageSCH;
import jp.co.daifuku.wms.master.schedule.MasterAsScheduleParameter;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;

/**
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterAsNoPlanStorageBusiness extends MasterAsNoPlanStorage implements WMSConstants
{
	// Class fields --------------------------------------------------

	// プルダウン入庫
	private static final String STATION_STORAGE = "10";
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/master/listbox/listmasterconsignor/ListMasterConsignor.do";
	// 商品検索ポップアップアドレス
	private static final String DO_SRCH_ITEM = "/master/listbox/listmasteritem/ListMasterItem.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			// (デフォルト:-1)
			this.getViewState().setInt("LineNo", -1);

			// ボタン押下を不可にする
			// 出庫開始ボタン
			btn_StorageStart.setEnabled(false);
			// 一覧クリアボタン
			btn_AllCancel.setEnabled(false);
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 本HttpのLocaleを取得
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			// プルダウン表示データ（保管エリア）
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			// プルダウン表示データ（作業場）
			String[] sagyoba = pull.getSagyobaPulldownData(conn, STATION_STORAGE, true, "");
			// プルダウン表示データ（ステーション）
			String[] stno = pull.getStationPulldownData(conn, STATION_STORAGE, true, "");
			// プルダウン表示データ（ハードゾーン）
			String[] zoneno = pull.getHardZonePulldownData(conn, "");

			// プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);
			PulldownHelper.setLinkedPullDown(pul_Zone, zoneno);

			// 子プルダウンを登録
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);
			pul_WareHouse.addChild(pul_Zone);

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
		// 入庫開始ボタン押下時確認ダイアログ "設定しますか？"
		btn_StorageStart.setBeforeConfirm("MSG-9000");
		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_AllCancel.setBeforeConfirm("MSG-W0012");
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
		// ケース入数
		String caseenteringqty = param.getParameter(ListMasterItemBusiness.CASEETR_KEY);
		// ボール入数
		String bundleenteringqty = param.getParameter(ListMasterItemBusiness.BUNDLEETR_KEY);
		// ケースITF
		String caseitf = param.getParameter(ListMasterItemBusiness.CASEITF_KEY);
		// ボールITF
		String bundleitf = param.getParameter(ListMasterItemBusiness.BUNDLEITF_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 荷主名称
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);
		}
		// ケース入数
		if (!StringUtil.isBlank(caseenteringqty))
		{
			txt_CaseEntering.setText(caseenteringqty);
		}
		// ボール入数
		if (!StringUtil.isBlank(bundleenteringqty))
		{
			txt_PieceEntering.setText(bundleenteringqty);
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

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();

		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_UseByDate))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}
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
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsNoPlanStorageSCH();

			MasterAsScheduleParameter param = (MasterAsScheduleParameter) schedule.initFind(conn, null);

			// ケース/ピース区分をケースにセットする
			rdo_Cpf_Case.setChecked(true);
			
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());

				if (param.getMergeType() == MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE)
				{
					txt_ConsignorName.setReadOnly(true);
					txt_ItemName.setReadOnly(true);
					txt_CaseEntering.setReadOnly(true);
					txt_PieceEntering.setReadOnly(true);
					txt_CaseItf.setReadOnly(true);
					txt_BundleItf.setReadOnly(true);
				}
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}

			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// ケース/ピース区分(ケース)
			rdo_Cpf_Case.setChecked(true);
			// ケース/ピース区分(ピース)
			rdo_Cpf_Piece.setChecked(false);
			// ケース/ピース区分(指定なし)
			rdo_Cpf_AppointOff.setChecked(false);
			// ケース入数
			txt_CaseEntering.setText("");
			// 入庫ケース数
			txt_StrgCaseQty.setText("");
			// ケースITF
			txt_CaseItf.setText("");
			// ボール入数
			txt_PieceEntering.setText("");
			// 入庫ピース数
			txt_StrgPieceQty.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 賞味期限
			txt_UseByDate.setText("");
			// 「入庫作業リストを発行する」にチェックをする
			chk_CommonUse.setChecked(true);
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);
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
	 * ケース、ピースの値チェックを行うメソッドです。<BR>
	 * <BR>
	 * 概要:ケース、ピースの値が0以上かをチェックします。<BR>
	 * 
	 * @param figure 	  値チェックするための使用桁です。
	 * @param name 	  値チェックする項目名です。
	 * @throws Exception 全ての例外を報告します。
	 * @return itemName 使用桁か位置が1以上じゃなかった場合項目名を返します。
	 */
	private String checkNumber(NumberTextBox figure, Label name) throws Exception
	{
		String itemName = null;

		if (!StringUtil.isBlank(Integer.toString(figure.getInt())))
		{
			if (figure.getInt() < 0)
			{
				// itemNameに項目名をセット
				itemName = DisplayText.getText(name.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	/**
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。<BR>
	 * 3.登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			修正対象ためうち行No.* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private MasterAsScheduleParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();

		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

		for (int i = 1; i < lst_NoPlanStorage.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_NoPlanStorage.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterAsScheduleParameter param = new MasterAsScheduleParameter();

			// 倉庫
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			// ゾーン
			param.setHardZone(pul_Zone.getSelectedValue());
			// 搬送元ステーション
			param.setFromStationNo(pul_Station.getSelectedValue());
			if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
			{
				// ステーションに「自動振分け」が指定された場合は、作業場をセットする。
				param.setFromStationNo(pul_WorkPlace.getSelectedValue());
			}

			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 端末No.
			param.setTerminalNumber(userHandler.getTerminalNo());
			
			// リスト発行区分
			param.setListFlg(chk_CommonUse.getChecked());
			// ケース／ピース区分（隠し項目）
			//param.setCasePieceFlag(lst_NoPlanStorage.getValue(0));
			param.setCasePieceFlag(CollectionUtils.getString(0, lst_NoPlanStorage.getValue(0)));
			// 荷主コード
			param.setConsignorCode(lst_NoPlanStorage.getValue(3));
			// 商品コード
			param.setItemCode(lst_NoPlanStorage.getValue(4));
			// 入庫ケース数
			param.setStorageCaseQty(Formatter.getInt(lst_NoPlanStorage.getValue(5)));			
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_NoPlanStorage.getValue(6)));
			// ケース／ピース区分
			param.setCasePieceFlagNameDisp(lst_NoPlanStorage.getValue(7));
			// ケースITF
			param.setITF(lst_NoPlanStorage.getValue(8));
			// 賞味期限
			param.setUseByDate(lst_NoPlanStorage.getValue(9));
			// 荷主名称
			param.setConsignorName(lst_NoPlanStorage.getValue(10));
			// 商品名称
			param.setItemName(lst_NoPlanStorage.getValue(11));
			// 入庫ピース数
			param.setStoragePieceQty(Formatter.getInt(lst_NoPlanStorage.getValue(12)));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_NoPlanStorage.getValue(13)));
			// ボールITF
			param.setBundleITF(lst_NoPlanStorage.getValue(14));

			// 行No.を保持しておく
			param.setRowNo(i);

			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_NoPlanStorage.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, lst_NoPlanStorage.getValue(0))));

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterAsScheduleParameter[] listparam = new MasterAsScheduleParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			// セットするためうちデータがなければnullをセット
			return null;
		}
	}

	/**
	 * 入力エリア.ケースピース区分ラジオボタンから対応する出庫パラメータ.ケース/ピース区分を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		// ケース/ピース区分
		if (rdo_Cpf_AppointOff.getChecked())
		{
			// 指定なし
			return AsScheduleParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_Cpf_Case.getChecked())
		{
			// ケース
			return AsScheduleParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_Cpf_Piece.getChecked())
		{
			// ピース
			return AsScheduleParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	/**
	 * 在庫パラメータ.ケースピース区分から対応するケース/ピース区分を取得します。<BR>
	 * <BR>
	 * @param value ラジオボタンに対応する出庫パラメータ.ケース/ピース区分です。
	 * @throws Exception 全ての例外を報告します。  
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		// ケース/ピース区分
		if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なし
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	/**
	 * リストセル.区分の内容を入力エリアのケース/ピース区分チェックボックスへ表示します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (CollectionUtils.getString(0, lst_NoPlanStorage.getValue(0)).equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なしにチェック
			rdo_Cpf_AppointOff.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
		}
		else if (CollectionUtils.getString(0, lst_NoPlanStorage.getValue(0)).equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			// ケースにチェック
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
		else if (CollectionUtils.getString(0, lst_NoPlanStorage.getValue(0)).equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピースにチェック
			rdo_Cpf_Piece.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
	}

	/**
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setList(MasterAsScheduleParameter mergeParam) throws Exception
	{
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 荷主名称
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		// 賞味期限
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//カレント行にTOOL TIPをセットする
		lst_NoPlanStorage.setToolTip(lst_NoPlanStorage.getCurrentRow(), toolTip.getText());

		// ケース／ピース区分（隠し項目）
		//lst_NoPlanStorage.setValue(0, getCasePieceFlagFromInputArea());
		List hiddenList = new Vector();
		hiddenList.add(0, getCasePieceFlagFromInputArea());
		// 荷主コード最終使用日
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		// 商品コード最終使用日
		hiddenList.add(2, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_NoPlanStorage.setValue(0, CollectionUtils.getConnectedString(hiddenList));
		// 荷主コード
		lst_NoPlanStorage.setValue(3, txt_ConsignorCode.getText());
		// 商品コード
		lst_NoPlanStorage.setValue(4, txt_ItemCode.getText());
		// 入庫ケース数
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_NoPlanStorage.setValue(5, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(5, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_NoPlanStorage.setValue(6, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(6, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// 区分
		lst_NoPlanStorage.setValue(7, DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		// ケースITF
		lst_NoPlanStorage.setValue(8, txt_CaseItf.getText());
		// 賞味期限
		lst_NoPlanStorage.setValue(9, txt_UseByDate.getText());
		// 荷主名称
		lst_NoPlanStorage.setValue(10, txt_ConsignorName.getText());
		// 商品名称
		lst_NoPlanStorage.setValue(11, txt_ItemName.getText());
		// 入庫ピース数
		if (StringUtil.isBlank(txt_StrgPieceQty.getText()))
		{
			lst_NoPlanStorage.setValue(12, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(12, WmsFormatter.getNumFormat(txt_StrgPieceQty.getInt()));
		}

		// ボール入数
		if (StringUtil.isBlank(txt_PieceEntering.getText()))
		{
			lst_NoPlanStorage.setValue(13, "0");
		}
		else
		{
			lst_NoPlanStorage.setValue(13, WmsFormatter.getNumFormat(txt_PieceEntering.getInt()));
		}
		// ボールITF
		lst_NoPlanStorage.setValue(14, txt_BundleItf.getText());
	}


	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
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
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
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
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);

	}

	/**
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をチェックし、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.カーソルを作業者コードにセットします。<BR>
	 *   2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			作業者コード* <BR>
	 * 			パスワード* <BR>
	 * 			荷主コード* <BR>
	 * 			荷主名称 <BR>
	 * 			商品コード* <BR>
	 * 			商品名称 <BR>
	 * 			ケース／ピース区分* <BR>
	 * 			ケース入数*2 <BR>
	 * 			入庫ケース数*2 *4 <BR>
	 * 			ケースITF <BR>
	 * 			ボール入数 <BR>
	 *			入庫ピース数*3 *4 <BR>
	 *			ボールITF <BR>
	 *			賞味期限 <BR>
	 *		<BR>
	 * 			*2 <BR>
	 * 			ケース／ピース区分が1:ケース時、必須入力 <BR>
	 * 			*3 <BR>
	 * 			ケース／ピース区分が2:ピース時、必須入力 <BR>
	 * 			*4 <BR>
	 * 			ケース／ピース区分が0:指定なし時、<BR>
	 *			入庫ケース数、入庫ピース数何れかに1以上の入力が必須条件 <BR>
	 * 			ケース数×ケース入数＋ピース数が0でない事(>0) <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.スケジュールの結果がtrueであれば、ためうちエリアに追加します。<BR>
	 *     修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *   5.スケジュールの結果がtrueであれば、登録ボタン・一覧クリアボタンを有効にします。<BR>
	 *   6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *   7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 * 	 8.入力エリアの内容は保持します。<BR>
	 *   <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.荷主コード <BR>
	 * 		4.商品コード <BR>
	 * 		5.入庫ケース数 <BR>
	 * 		6.ケース入数 <BR>
	 * 		7.区分 <BR>
	 * 		8.ケースITF <BR>
	 * 		9.賞味期限 <BR>
	 * 		10.荷主名称 <BR>
	 * 		11.商品名称 <BR>
	 * 		12.入庫ピース数 <BR>
	 * 		13.ボール入数 <BR>
	 * 		14.ボールITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);

		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		
		// パターンマッチング文字
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_StrgCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_PieceEntering.validate(false);
		txt_StrgPieceQty.validate(false);
		txt_BundleItf.validate(false);
		txt_UseByDate.validate(false);

		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_PieceEntering, lbl_BundleEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgCaseQty, lbl_StorageCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgPieceQty, lbl_StoragePieceQty);

		if (!StringUtil.isBlank(itemname))
		{
			// エラーメッセージを表示し、終了する。
			// 6023057 = {0}には{1}以上の値を入力してください。
			message.setMsgResourceKey("6023057" + wDelim + itemname + wDelim + "0");
			return;
		}
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			MasterAsScheduleParameter mergeParam = new MasterAsScheduleParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_PieceEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			/* 2006/6/7 v2.6.0 START Y.Osawa DEL 複数回コネクションしていたのを削除 */
			/* 2006/6/7 END */
			WmsScheduler schedule = new MasterAsNoPlanStorageSCH(conn);
			
			mergeParam = (MasterAsScheduleParameter) schedule.query(conn, mergeParam)[0];
			// 入力エリアに補完結果を表示
			txt_ConsignorName.setText(mergeParam.getConsignorName());
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_PieceEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());

			// スケジュールパラメータへセット
			// 入力エリア
			MasterAsScheduleParameter param = new MasterAsScheduleParameter();
			// 倉庫ステーションNo.
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			// 作業者コード 
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(txt_ConsignorName.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケースピース区分
			param.setCasePieceFlag(getCasePieceFlagFromInputArea());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// 入庫ケース数
			param.setStorageCaseQty(txt_StrgCaseQty.getInt());
			// ケースITF
			param.setITF(txt_CaseItf.getText());
			// ボール入数
			param.setBundleEnteringQty(txt_PieceEntering.getInt());
			// 入庫ピース数
			param.setStoragePieceQty(txt_StrgPieceQty.getInt());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());
			// 賞味期限
			param.setUseByDate(txt_UseByDate.getText());
			// 予定外入庫リストを発行
			param.setListFlg(chk_CommonUse.getChecked());

			// スケジュールパラメータへセット
			// ためうちエリア
			MasterAsScheduleParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_NoPlanStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			if (schedule.check(conn, param, listparam))
			{
				param.setConsignorLastUpdateDate(mergeParam.getConsignorLastUpdateDate());
				param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				// 結果がtrueであればためうちエリアにデータをセット
				if (this.getViewState().getInt("LineNo") == -1)
				{
					// 新規入力であれば、ためうちに追加
					lst_NoPlanStorage.addRow();
					lst_NoPlanStorage.setCurrentRow(lst_NoPlanStorage.getMaxRows() - 1);
					setList(param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_NoPlanStorage.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(param);
					// 選択行の色を元に戻す
					lst_NoPlanStorage.resetHighlight();
				}

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を可能にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(true);
				// 一覧クリアボタン
				btn_AllCancel.setEnabled(true);
			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

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
		// クリア処理を行う
		setFirstDisp();
	}

	/**
	 * 入庫開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、予定外入庫設定を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.入庫開始を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "設定しますか？"<BR>
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
	 * 						ためうち.荷主コード <BR>
	 * 						ためうち.荷主名称 <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.区分 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.入庫ケース数 <BR>
	 *						ためうち.入庫ピース数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールITF <BR>
	 *						ためうち.賞味期限 <BR>
	 *						ためうち行No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時は入力エリア、ためうちの登録情報をクリアします。<BR>
	 *				3.修正状態を解除します。(ViewStateのためうち行No.にデフォルト:-1を設定します。)<BR>
	 *    			falseの時はスケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.荷主コード <BR>
	 * 		4.商品コード <BR>
	 * 		5.区分 <BR>
	 * 		7.ケース入数 <BR>
	 * 		8.入庫ケース数 <BR>
	 * 		9.ケースITF <BR>
	 * 		10.賞味期限 <BR>
	 * 		11.荷主名称 <BR>
	 * 		12.商品名称 <BR>
	 * 		13.ボール入数 <BR>
	 * 		14.入庫ピース数 <BR>
	 * 		15.ボールITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_StorageStart_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// スケジュールパラメータへセット
			MasterAsScheduleParameter[] param  = null ;
			// ためうちエリアの全データをセット
			param = setListParam(-1);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsNoPlanStorageSCH(conn);

			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、commit
				conn.commit();

				// 入庫指示送信を起動します。
				SendRequestor req = new SendRequestor();
				req.storage();
				
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());

				// 一覧クリア
				lst_NoPlanStorage.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を不可にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(false);
				// 一覧クリアボタン
				btn_AllCancel.setEnabled(false);
			}
			else
			{
				conn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
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
	 * 取消、修正ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 取消ボタン概要:ためうちの該当データをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "取消しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.入力エリア、ためうちの該当データをクリアします。<BR>
	 * 				2.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *              3.ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にします。<BR>
	 *				4.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.選択情報を上部の入力領域に表示します。<BR>
	 *    2.選択情報部を薄黄色にします。<BR>
	 *    3.ViewStateのためうち行No.に現在行を設定します。
	 *    4.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_NoPlanStorage_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_NoPlanStorage.getActiveCol() == 1)
		{
			// リスト削除
			lst_NoPlanStorage.removeRow(lst_NoPlanStorage.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
			// ためうちにデータがなければnullをセット
			if (lst_NoPlanStorage.getMaxRows() == 1)
			{
				// ボタン押下を不可にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(false);
				// 一覧クリアボタン
				btn_AllCancel.setEnabled(false);
			}

			// 修正状態をデフォルトに戻す
			this.getViewState().setInt("LineNo", -1);

			// 選択行の色を元に戻す
			lst_NoPlanStorage.resetHighlight();

			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

		}
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_NoPlanStorage.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_NoPlanStorage.setCurrentRow(lst_NoPlanStorage.getActiveRow());
			// 荷主コード
			txt_ConsignorCode.setText(lst_NoPlanStorage.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_NoPlanStorage.getValue(4));
			// 入庫ケース数
			txt_StrgCaseQty.setText(lst_NoPlanStorage.getValue(5));
			// ケース入数
			txt_CaseEntering.setText(lst_NoPlanStorage.getValue(6));
			// ケース／ピース区分
			setCasePieceRBFromList();
			// ケースITF
			txt_CaseItf.setText(lst_NoPlanStorage.getValue(8));
			// 賞味期限
			txt_UseByDate.setText(lst_NoPlanStorage.getValue(9));
			// 荷主名称
			txt_ConsignorName.setText(lst_NoPlanStorage.getValue(10));
			// 商品名称
			txt_ItemName.setText(lst_NoPlanStorage.getValue(11));
			// 入庫ピース数
			txt_StrgPieceQty.setText(lst_NoPlanStorage.getValue(12));
			// ボール入数
			txt_PieceEntering.setText(lst_NoPlanStorage.getValue(13));
			// ボールITF
			txt_BundleItf.setText(lst_NoPlanStorage.getValue(14));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt("LineNo", lst_NoPlanStorage.getActiveRow());

			//修正行を黄色に変更する
			lst_NoPlanStorage.setHighlight(lst_NoPlanStorage.getActiveRow());

			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);
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
	 *				2.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 *				3.入力エリアの項目を全てクリアする。<BR>
	 * 				4.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_AllCancel_Click(ActionEvent e) throws Exception
	{
		// 行をすべて削除
		lst_NoPlanStorage.clearRow();

		// ボタン押下を不可にする
		// 入庫開始ボタン
		btn_StorageStart.setEnabled(false);
		// 一覧クリアボタン
		btn_AllCancel.setEnabled(false);

		// 修正状態をデフォルトに戻す
		this.getViewState().setInt("LineNo", -1);

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}
}
//end of class

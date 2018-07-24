// $Id: MasterAsAddStorage2Business.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsaddstorage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

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
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail.ListAsStockDetailBusiness;
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
import jp.co.daifuku.wms.master.schedule.MasterAsAddStorageSCH;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.master.schedule.MasterAsScheduleParameter;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;

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
public class MasterAsAddStorage2Business extends MasterAsAddStorage2 implements WMSConstants
{
	// Class fields --------------------------------------------------

	// viewState用KEY(隠しパラメータ)
	// ためうち行No.
	private static final String LineNo = "LINENO";
	// 棚明細ボタン
	private static final String btnShelfDetail = "SHELFDETAIL";

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
		// タイトル
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(MasterAsAddStorage1Business.TITLE)));

		Connection conn = null;

		try
		{
			// 入庫情報入力のタブを前出しします
			tab_Add_Storage.setSelectedIndex(2);

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 倉庫ステーションNo から倉庫ナンバーを取得する。
			ASFindUtil util = new ASFindUtil(conn);
			String whstno = this.getViewState().getString(MasterAsAddStorage1Business.VS_WHNUMBER);
			lbl_JavaSetWareHouse.setText(util.getWareHouseName(whstno));

			// 棚No
			lbl_JavaSetLocationNo.setText(DisplayText.formatDispLocation(this.getViewState().getString(MasterAsAddStorage1Business.VS_LOCATION)));

			// 本HttpのLocaleを取得
			Locale locale = this.getHttpRequest().getLocale();

			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());

			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			// プルダウン表示データ（保管エリア）
			String[] areaid = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, whstno, false);
			// プルダウン表示データ（作業場）
			String[] sagyoba = pull.getSagyobaPulldownData(conn, PulldownData.STATION_ADD_STORAGE, true, "");
			// プルダウン表示データ（ステーション）
			String[] stno = pull.getStationPulldownData(conn, PulldownData.STATION_ADD_STORAGE, true, "");

			// プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_WareHouse, areaid);
			PulldownHelper.setLinkedPullDown(pul_WorkPlace, sagyoba);
			PulldownHelper.setLinkedPullDown(pul_Station, pul_WorkPlace, stno);

			// 子プルダウンを登録
			pul_WareHouse.addChild(pul_WorkPlace);
			pul_WorkPlace.addChild(pul_Station);

			// 連動プルダウンの為の親プルダウンなので、倉庫プルダウンは表示しない。
			pul_WareHouse.setVisible(false);

			// 入力エリアを初期化します
			inputAreaClear();
			
    		// １画面目で決定した読取専用かどうかの判断を引き継ぎ、２画面目も表示する
	    	if (this.getViewState().getBoolean(MasterAsAddStorage1Business.IS_READ_ONLY_KEY))
		    {
	    		txt_ConsignorName.setReadOnly(true);
			    txt_ItemName.setReadOnly(true);
			    txt_CaseEntering.setReadOnly(true);
			    txt_CaseItf.setReadOnly(true);
			    txt_PieceEntering.setReadOnly(true);
			    txt_BundleItf.setReadOnly(true);
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
				// コネクションをクローズします
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
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

		String type = param.getParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY);
		if( type == null )
		{
			type = "";
		}

		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
		// ケース／ピース区分
		String casePiece = param.getParameter(ListAsStockDetailBusiness.CASEPIECEFLAG_KEY);
		// ケース入数
		String enteringQty = param.getParameter(ListMasterItemBusiness.ENTERING_KEY);
		// ケースITF
		String caseItf = param.getParameter(ListMasterItemBusiness.ITF_KEY);
		// ピース入数
		String BundleEnteringQty = param.getParameter(ListMasterItemBusiness.BUNDLEENTERING_KEY);
		// ボールITF
		String bundleItf = param.getParameter(ListMasterItemBusiness.BUNDLEITF_KEY);
		// 賞味期限
		String useByDate = param.getParameter(ListAsStockDetailBusiness.USEBYDATE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード、荷主名称
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			txt_ConsignorName.setText(consignorname);
		}
		// 商品コード、商品名称
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
			txt_ItemName.setText(itemname);
			// ケース入数
			txt_CaseEntering.setText(enteringQty);
			// ケースITF
			txt_CaseItf.setText(caseItf);
			// ピース入数
			txt_PieceEntering.setText(BundleEnteringQty);
			// ボールITF
			txt_BundleItf.setText(bundleItf);
			// 賞味期限
			txt_UseByDate.setText(useByDate);
		}
		if( type.equals(btnShelfDetail) )
		{
			if (casePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				// 指定なしにチェック
				rdo_Cpf_AppointOff.setChecked(true);
				rdo_Cpf_Case.setChecked(false);
				rdo_Cpf_Piece.setChecked(false);
			}
			else if (casePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				// ケースにチェック
				rdo_Cpf_Case.setChecked(true);
				rdo_Cpf_Piece.setChecked(false);
				rdo_Cpf_AppointOff.setChecked(false);
			}
			else if (casePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				// ピースにチェック
				rdo_Cpf_Piece.setChecked(true);
				rdo_Cpf_Case.setChecked(false);
				rdo_Cpf_AppointOff.setChecked(false);
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
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
	 * 入力エリアを初期状態にします。 <BR>
	 * <BR>
	 * 概要:入力エリアを全て空にし、入力ボタン・クリアボタンを無効化します。 <BR>
	 * <BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	private void inputAreaClear() throws Exception
	{
		// 入力項目を空にします
		inputDataClear();

		// 入力エリアのボタンを無効化します
		// 入庫開始ボタン
		btn_StorageStart.setEnabled(false);
		// 全取消ボタン
		btn_AllCancel.setEnabled(false);

		// 修正状態(ためうち行No)を初期値へリセットします
		this.getViewState().setInt(LineNo, -1);

		// 選択行の色を初期値へリセットします
		lst_AddStorage.resetHighlight();

		// カーソルを荷主コードへセットします
		setFocus(txt_ConsignorCode);
	}

	/**
	 * 入力エリアを初期状態にします。 <BR>
	 * <BR>
	 * 概要:入力エリアを全て空にします。 <BR>
	 * <BR>
	 *  
	 * @throws Exception 全ての例外を報告します。
	 */
	private void inputDataClear() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsAddStorageSCH(conn);
			AsScheduleParameter param = (AsScheduleParameter) schedule.initFind(conn, null);
	
			// 荷主コード
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				// 荷主コードが１件の場合、初期表示させる。
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				// 荷主コード
				txt_ConsignorCode.setText("");
				// 荷主名称
				txt_ConsignorName.setText("");
			}
			
			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// ケース/ピース区分
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
			// ケース入数
			txt_CaseEntering.setText("");
			// ケース入庫数
			txt_StrgCaseQty.setText("");
			// ケースITF
			txt_CaseItf.setText("");
			// ピース入数
			txt_PieceEntering.setText("");
			// ピース入庫数
			txt_StrgPieceQty.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 賞味期限
			txt_UseByDate.setText("");
			// 「入庫作業リストを発行する」にチェックをする
			chk_CommonUse.setChecked(true);
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
	 * @param lineno 対象となる行No。
	 * @throws Exception 全ての例外を報告します。 
	 */
	private MasterAsScheduleParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();

		for (int i = 1; i < lst_AddStorage.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_AddStorage.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterAsScheduleParameter param = new MasterAsScheduleParameter();
			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(MasterAsAddStorage1Business.VS_WORKERCODE));
			// エリアNo
			param.setAreaNo(this.getViewState().getString(MasterAsAddStorage1Business.VS_WHNUMBER));
			// 倉庫
			param.setWareHouseNo(this.getViewState().getString(MasterAsAddStorage1Business.VS_WHNUMBER));
			// 棚No
			param.setLocationNo(this.getViewState().getString(MasterAsAddStorage1Business.VS_LOCATION));
			// 搬送元ステーション
			param.setFromStationNo(this.getViewState().getString(MasterAsAddStorage1Business.VS_LOCATION));
			// 搬送先ステーション
			param.setToStationNo(pul_Station.getSelectedValue());
			if( pul_Station.getSelectedValue().equals(Station.AUTO_SELECT_STATIONNO))
			{
				// ステーションに「自動振分け」が指定された場合は、作業場をセットする。
				param.setSagyoba(pul_WorkPlace.getSelectedValue());
			}
			
			// 入力エリア情報
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			
			// リスト発行区分
			param.setListFlg(chk_CommonUse.getChecked());
			// ケース／ピース区分（隠し項目）
			//param.setCasePieceFlag(lst_AddStorage.getValue(0));
			param.setCasePieceFlag(CollectionUtils.getString(0, lst_AddStorage.getValue(0)));
			// 荷主コード
			param.setConsignorCode(lst_AddStorage.getValue(3));
			// 商品コード
			param.setItemCode(lst_AddStorage.getValue(4));
			// 入庫ケース数
			param.setStorageCaseQty(Formatter.getInt(lst_AddStorage.getValue(5)));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_AddStorage.getValue(6)));
			// ケース／ピース区分
			param.setCasePieceFlagNameDisp(lst_AddStorage.getValue(7));
			// ケースITF
			param.setITF(lst_AddStorage.getValue(8));
			// 賞味期限
			String str = lst_AddStorage.getValue(9);
			if (str.length() <= 8)
			{
				param.setUseByDate(str);
			}
			else
			{
				param.setUseByDate(str.substring(0,4) +	str.substring(5,7) + str.substring(8,10));
			}
			// 荷主名称
			param.setConsignorName(lst_AddStorage.getValue(10));
			// 商品名称
			param.setItemName(lst_AddStorage.getValue(11));
			// 入庫ピース数
			param.setStoragePieceQty(Formatter.getInt(lst_AddStorage.getValue(12)));
			// ピース入数
			param.setBundleEnteringQty(Formatter.getInt(lst_AddStorage.getValue(13)));
			// ボールITF
			param.setBundleITF(lst_AddStorage.getValue(14));

			// 行No.を保持しておく
			param.setRowNo(i);

			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_AddStorage.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, lst_AddStorage.getValue(0))));
			
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
	 * 入力エリア.ケースピース区分ラジオボタンから対応する入庫パラメータ.ケース/ピース区分を取得します。<BR>
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
	 * @return ケースピース区分
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
		if (CollectionUtils.getString(0, lst_AddStorage.getValue(0)).equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なしにチェック
			rdo_Cpf_AppointOff.setChecked(true);
			rdo_Cpf_Case.setChecked(false);
			rdo_Cpf_Piece.setChecked(false);
		}
		else if (CollectionUtils.getString(0, lst_AddStorage.getValue(0)).equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			// ケースにチェック
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_AppointOff.setChecked(false);
		}
		else if (CollectionUtils.getString(0, lst_AddStorage.getValue(0)).equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
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
	 * @param mergeParam DBの値を格納しているパラメタクラス。
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
		lst_AddStorage.setToolTip(lst_AddStorage.getCurrentRow(), toolTip.getText());

		// ケース／ピース区分（隠し項目）
		//lst_AddStorage.setValue(0, getCasePieceFlagFromInputArea());
		List hiddenList = new Vector();
		hiddenList.add(0, getCasePieceFlagFromInputArea());
		// 荷主コード最終使用日
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		// 商品コード最終使用日
		hiddenList.add(2, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_AddStorage.setValue(0, CollectionUtils.getConnectedString(hiddenList));
		// 荷主コード
		lst_AddStorage.setValue(3, txt_ConsignorCode.getText());
		// 商品コード
		lst_AddStorage.setValue(4, txt_ItemCode.getText());
		// 入庫ケース数
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_AddStorage.setValue(5, "0");
		}
		else
		{
			lst_AddStorage.setValue(5, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_AddStorage.setValue(6, "0");
		}
		else
		{
			lst_AddStorage.setValue(6, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// 区分
		lst_AddStorage.setValue(7, DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		// ケースITF
		lst_AddStorage.setValue(8, txt_CaseItf.getText());
		// 賞味期限


		lst_AddStorage.setValue(9, txt_UseByDate.getText());
		// 荷主名称
		lst_AddStorage.setValue(10, txt_ConsignorName.getText());
		// 商品名称
		lst_AddStorage.setValue(11, txt_ItemName.getText());
		// 入庫ピース数
		if (StringUtil.isBlank(txt_StrgPieceQty.getText()))
		{
			lst_AddStorage.setValue(12, "0");
		}
		else
		{
			lst_AddStorage.setValue(12, WmsFormatter.getNumFormat(txt_StrgPieceQty.getInt()));
		}

		// ピース入数
		if (StringUtil.isBlank(txt_PieceEntering.getText()))
		{
			lst_AddStorage.setValue(13, "0");
		}
		else
		{
			lst_AddStorage.setValue(13, WmsFormatter.getNumFormat(txt_PieceEntering.getInt()));
		}
		// ボールITF
		lst_AddStorage.setValue(14, txt_BundleItf.getText());
	}

	// Event handler methods -----------------------------------------

	/** 
	 * メニューへボタンが押されたときに呼ばれます。
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 棚明細ボタンが押されたときに呼ばれます。
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		// 棚明細画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 倉庫ステーションNo.
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		// 倉庫名称
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		// 棚No
		String tno = this.getViewState().getString(MasterAsAddStorage1Business.VS_LOCATION);
		param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY, tno);
		// ボタンの受け渡し情報
		param.setParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY , btnShelfDetail);
		// 処理中画面->結果画面
		redirect("/asrs/listbox/listasrsstockdetail/ListAsStockDetail.do", param, "/progress.do");
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
		// 検索フラグ
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		// 処理中画面->結果画面
		redirect("/master/listbox/listmasterconsignor/ListMasterConsignor.do", param, "/progress.do");
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
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY,
						   AsScheduleParameter.SEARCHFLAG_STOCK);
		// 処理中画面->結果画面
		redirect("/master/listbox/listmasteritem/ListMasterItem.do", param, "/progress.do");
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
		// カーソルを荷主コードにセットする
		setFocus(txt_ConsignorCode);

		// 必須入力チェック
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

		// ケース入数・入庫数、ピース入数・入庫数のマイナス値チェック
		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_PieceEntering, lbl_PieceEntering);
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
			MasterAsScheduleParameter mergeParam = new MasterAsScheduleParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_PieceEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterAsAddStorageSCH(conn);
			
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

			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(MasterAsAddStorage1Business.VS_WORKERCODE));
			// 倉庫
			param.setWareHouseNo(this.getViewState().getString(MasterAsAddStorage1Business.VS_WHNUMBER));
			// 棚No
			param.setLocationNo(this.getViewState().getString(MasterAsAddStorage1Business.VS_LOCATION));
			// 搬送元ステーション
			param.setFromStationNo(pul_Station.getSelectedValue());
			
			// 入力エリア情報
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

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
			// ピース入数
			param.setBundleEnteringQty(txt_PieceEntering.getInt());
			// 入庫ピース数
			param.setStoragePieceQty(txt_StrgPieceQty.getInt());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());
			// 賞味期限
			param.setUseByDate(txt_UseByDate.getText());
			// 入庫リストを発行
			param.setListFlg(chk_CommonUse.getChecked());

			// スケジュールパラメータへセット
			// ためうちエリア
			MasterAsScheduleParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_AddStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt(LineNo));
			}

			if (schedule.check(conn, param, listparam))
			{
				param.setConsignorLastUpdateDate(mergeParam.getConsignorLastUpdateDate());
				param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				// 結果がtrueであればためうちエリアにデータをセット
				if (this.getViewState().getInt(LineNo) == -1)
				{
					// 新規入力であれば、ためうちに追加
					lst_AddStorage.addRow();
					lst_AddStorage.setCurrentRow(lst_AddStorage.getMaxRows() - 1);
					setList(param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_AddStorage.setCurrentRow(this.getViewState().getInt(LineNo));
					setList(param);
					// 選択行の色を元に戻す
					lst_AddStorage.resetHighlight();
				}

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt(LineNo, -1);

				// ボタン押下を可能にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(true);
				// 全取消ボタン
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
	 * 戻るボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:在庫修正・削除(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		// 入力情報入力画面 -> 棚選択画面
		forward(MasterAsAddStorage1Business.DO_ADDSTORAGE1);
	}

	/** 
	 * 入庫開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、予定外入庫設定(積増し)を行います。<BR>
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
			// カーソルを荷主コードにセットする
			setFocus(txt_ConsignorCode);

			// スケジュールパラメータへセット
			MasterAsScheduleParameter[] param  = null ;
			// ためうちエリアの全データをセット
			param = setListParam(-1);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterAsAddStorageSCH(conn);

			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、commit
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());

				// 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
				SendRequestor req = new SendRequestor();
				req.retrieval();

				// 一覧クリア
				lst_AddStorage.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt(LineNo, -1);

				// ボタン押下を不可にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(false);
				// 全取消ボタン
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
	public void lst_AddStorage_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_AddStorage.getActiveCol() == 1)
		{
			// リスト削除
			lst_AddStorage.removeRow(lst_AddStorage.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
			// ためうちにデータがなければnullをセット
			if (lst_AddStorage.getMaxRows() == 1)
			{
				// ボタン押下を不可にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(false);
				// 全取消ボタン
				btn_AllCancel.setEnabled(false);
			}

			// 修正状態をデフォルトに戻す
			this.getViewState().setInt(LineNo, -1);

			// 選択行の色を元に戻す
			lst_AddStorage.resetHighlight();

			// カーソルを作業者コードにセットする
			setFocus(txt_ConsignorCode);

		}
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_AddStorage.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_AddStorage.setCurrentRow(lst_AddStorage.getActiveRow());
			// 荷主コード
			txt_ConsignorCode.setText(lst_AddStorage.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_AddStorage.getValue(4));
			// 入庫ケース数
			txt_StrgCaseQty.setText(lst_AddStorage.getValue(5));
			// ケース入数
			txt_CaseEntering.setText(lst_AddStorage.getValue(6));
			// ケース／ピース区分
			setCasePieceRBFromList();
			// ケースITF
			txt_CaseItf.setText(lst_AddStorage.getValue(8));
			// 賞味期限
			txt_UseByDate.setText(lst_AddStorage.getValue(9));
			// 荷主名称
			txt_ConsignorName.setText(lst_AddStorage.getValue(10));
			// 商品名称
			txt_ItemName.setText(lst_AddStorage.getValue(11));
			// 入庫ピース数
			txt_StrgPieceQty.setText(lst_AddStorage.getValue(12));
			// ピース入数
			txt_PieceEntering.setText(lst_AddStorage.getValue(13));
			// ボールITF
			txt_BundleItf.setText(lst_AddStorage.getValue(14));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt(LineNo, lst_AddStorage.getActiveRow());

			//修正行を黄色に変更する
			lst_AddStorage.setHighlight(lst_AddStorage.getActiveRow());

			// カーソルを作業者コードにセットする
			setFocus(txt_ConsignorCode);
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
	 *    2.カーソルを荷主コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		inputDataClear();

		// カーソルを荷主コードにセットする
		setFocus(txt_ConsignorCode);
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
		lst_AddStorage.clearRow();

		// ボタン押下を不可にする
		// 入庫開始ボタン
		btn_StorageStart.setEnabled(false);
		// 全取消ボタン
		btn_AllCancel.setEnabled(false);

		// 修正状態をデフォルトに戻す
		this.getViewState().setInt(LineNo, -1);

		// カーソルを荷主コードにセットする
		setFocus(txt_ConsignorCode);
	}
}
//end of class

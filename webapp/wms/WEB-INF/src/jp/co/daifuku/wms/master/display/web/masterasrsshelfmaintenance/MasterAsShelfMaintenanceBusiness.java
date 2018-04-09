// $Id: MasterAsShelfMaintenanceBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterasrsshelfmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.ui.web.PulldownHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.display.web.PulldownData;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsconsignor.ListAsConsignorBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsitem.ListAsItemBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetail.ListAsStockDetailBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrsstockdetailnobtn.ListAsStockDetailNoBtnBusiness;
import jp.co.daifuku.wms.asrs.display.web.listbox.listasrslocationdetailstatuslist.ListAsLocationDetailStatusListBusiness;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.master.schedule.MasterAsShelfMaintenanceSCH;
import jp.co.daifuku.wms.master.schedule.MasterAsScheduleParameter;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;

/**
 * Designer : T.Yoshino <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * ASRS在庫メンテナンス(変更)クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが在庫メンテナンス登録・修正・削除をします。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.登録ボタン押下処理（<CODE>btn_Submit_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力内容にて、入力条件のチェックを行います。<BR>
 *  結果がtrueの時は詳細（在庫情報）入力エリアが入力可能状態となります。<BR>
 *  <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		倉庫No* <BR>
 * 		棚No* <BR>
 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	棚No(表示用) <BR>
 *  	棚状態 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.修正ボタン押下処理（<CODE>btn_Modify_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力内容にて、入力条件のチェックを行います。<BR>
 *  結果がtrueの時は棚明細一覧が表示されます。<BR>
 *  棚明細一覧より、修正情報を選択することにより、詳細（在庫情報）が表示されます。<BR>
 *  <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		倉庫No* <BR>
 * 		棚No* <BR>
 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	棚No(表示用) <BR>
 *  	棚状態 <BR>
 *      荷主コード <BR>
 *      荷主名称 <BR>
 *      商品コード <BR>
 *      商品名称 <BR>
 *      ケース/ピース区分 <BR>
 *      ケース入数 <BR>
 *      ボール入数 <BR>
 *      在庫ケース数 <BR>
 *      在庫ピース数 <BR>
 *      ケースITF <BR>
 *      ボールITF <BR>
 *      入庫区分 <BR>
 *      賞味期限日 <BR>
 *      入庫日付 <BR>
 *      入庫時間 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 3.削除ボタン押下処理（<CODE>btn_Delete_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力内容にて、入力条件のチェックを行います。<BR>
 *  結果がtrueの時は棚明細一覧が表示されます。<BR>
 *  棚明細一覧より、修正情報を選択することにより、詳細（在庫情報）が表示されます。<BR>
 *  <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		倉庫No* <BR>
 * 		棚No* <BR>
 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	棚No(表示用) <BR>
 *  	棚状態 <BR>
 *      荷主コード <BR>
 *      荷主名称 <BR>
 *      商品コード <BR>
 *      商品名称 <BR>
 *      ケース/ピース区分 <BR>
 *      ケース入数 <BR>
 *      ボール入数 <BR>
 *      在庫ケース数 <BR>
 *      在庫ピース数 <BR>
 *      ケースITF <BR>
 *      ボールITF <BR>
 *      入庫区分 <BR>
 *      賞味期限日 <BR>
 *      入庫日付 <BR>
 *      入庫時間 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 4.設定ボタン押下処理(<CODE>btn_Setting_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが在庫メンテナンス変更処理を行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * 	<DIR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 *   処理区分* <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 * 	 倉庫No* <BR>
 * 	 棚No* <BR>
 *   荷主コード* <BR>
 *   荷主名称*<BR>
 *   商品コード* <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分* <BR>
 *   ケース入数* <BR>
 *   ボール入数* <BR>
 *   在庫ケース数* <BR>
 *   在庫ピース数* <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   入庫区分* <BR>
 *   賞味期限日 <BR>
 *   入庫日付 <BR>
 *   入庫時間 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterAsShelfMaintenanceBusiness extends MasterAsShelfMaintenance implements WMSConstants
{
	// Class fields --------------------------------------------------
	
	// Class variables -----------------------------------------------
	//登録、修正、削除のどの処理が選択されたかを保持するためのキー
	private final static String PROCESSTYPE_KEY = "PROCESSTYPE_KEY";

	//修正、削除時の在庫IDを保持するためのキー
	private final static String MNTSTOCK_ID = "MNTSTOCK_ID";

	//修正、削除時の最終更新日時を保持するためのキー
	private final static String MNTLAST_UPDATE = "MNTLAST_UPDATE";

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

		// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
		// (デフォルト:-1)
		this.getViewState().setInt("LineNo", -1);

		// ボタン押下を不可にする
		// 設定ボタン
		btn_Setting.setEnabled(false);
		// クリアボタン
		btn_Clear.setEnabled(false);

		// 棚状態(空棚)
		chk_LocationStatus_Empty.setChecked(true);
		// 棚状態(空PB棚)
		chk_LocationStatus_Empty_PB.setChecked(false);
		// 棚状態(実棚)
		chk_LocationStatus_Unit.setChecked(false);
		
		// 棚No
		txt_Location.setText("");
		// 初期表示をする
		setFirstDisp();
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
		// 登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Setting.setBeforeConfirm("MSG-W0009");

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			DialogParameters param = ((DialogEvent) e).getDialogParameters();
			String type = param.getParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY);
			// リストボックスから選択されたパラメータを取得
			// 荷主コード
			String consignorcode = "";
			// 荷主名称
			String consignorname = "";
			// 商品コード
			String itemcode = "";
			// 商品名称
			String itemname = "";
			// ケース／ピース区分
			String casepieseflag = "";
			// 棚
			String locationNo = "";
			// ケース在庫数
			String stockcaseqty = "";
			// ボール在庫数
			String stockbundleqty = "";
			// ケース入数
			String caseqty = "";
			// ボール入数
			String bundleqty = "";
			// ケースITF
			String caseitf = "";
			// ボールITF
			String bundleitf = "";
			// 入庫日時
			Date instockday = null;
			// 賞味期限
			String usebydate = "";
			// 入庫区分
			String restoring = "";
			//修正、削除ボタンからの戻り
			if (!StringUtil.isBlank(type))
			{
				int proctype = Integer.parseInt(type);
				if (MasterAsShelfMaintenanceSCH.M_MODIFY == proctype || MasterAsShelfMaintenanceSCH.M_DELETE == proctype)
				{
					//コネクション取得
					conn = ConnectionManager.getConnection(DATASOURCE_NAME);
					//棚状態をチェック
					if (!checkLocation(conn, proctype))
					{
						//初期状態に戻す
						btn_Clear_Click(null);
						return;
					}
					// 荷主コード
					consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
					// 荷主名称
					consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
					// 商品コード
					itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
					// 商品名称
					itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
					// ケース／ピース区分
					casepieseflag = param.getParameter(ListAsStockDetailBusiness.CASEPIECEFLAG_KEY);
					// 棚
					locationNo = param.getParameter(ListAsLocationDetailStatusListBusiness.LOCATION_KEY);
					// ケース在庫数
					stockcaseqty = param.getParameter(ListAsStockDetailBusiness.CASEQTY_KEY);
					// ボール在庫数
					stockbundleqty = param.getParameter(ListAsStockDetailBusiness.PIECEQTY_KEY);
					// ケース入数
					caseqty = param.getParameter(ListAsStockDetailBusiness.ENTERING_KEY);
					// ボール入数
					bundleqty = param.getParameter(ListAsStockDetailBusiness.BUNDLEENTERING_KEY);
					// ケースITF
					caseitf = param.getParameter(ListAsStockDetailBusiness.ITF_KEY);
					// ボールITF
					bundleitf = param.getParameter(ListAsStockDetailBusiness.BUNDLEITF_KEY);
					// 入庫日時
					instockday = WmsFormatter.getTimeStampDate(param.getParameter(ListAsStockDetailBusiness.STORINGDATE_KEY));
					// 賞味期限
					usebydate = param.getParameter(ListAsStockDetailBusiness.USEBYDATE_KEY);
					// 入庫区分
					restoring = param.getParameter(ListAsStockDetailBusiness.RESTORING_KEY);
					// 在庫ID
					this.getViewState().setString(MNTSTOCK_ID, param.getParameter(ListAsStockDetailBusiness.STOCKID_KEY));				
					// 最終更新日時
					this.getViewState().setString(MNTLAST_UPDATE, param.getParameter(ListAsStockDetailBusiness.LASTUPDATE_KEY));				

					// 入力領域をクリア
					changeInputArea(proctype);	
				}
			}
			else
			{
				// 棚
				locationNo = param.getParameter(ListAsLocationDetailStatusListBusiness.LOCATION_KEY);
				// 荷主コード
				consignorcode = param.getParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY);
				// 荷主名称
				consignorname = param.getParameter(ListAsConsignorBusiness.CONSIGNORNAME_KEY);
				// 商品コード
				itemcode = param.getParameter(ListAsItemBusiness.ITEMCODE_KEY);
				// 商品名称
				itemname = param.getParameter(ListAsItemBusiness.ITEMNAME_KEY);
				// ケース入数
				caseqty = param.getParameter(ListMasterItemBusiness.CASEETR_KEY);
				// ボール入数
				bundleqty = param.getParameter(ListMasterItemBusiness.BUNDLEETR_KEY);
				// ケースITF
				caseitf = param.getParameter(ListMasterItemBusiness.CASEITF_KEY);
				// ボールITF
				bundleitf = param.getParameter(ListMasterItemBusiness.BUNDLEITF_KEY);
			}
			
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
			if (!StringUtil.isBlank(itemcode))
			{
				txt_ItemName.setText(itemname);
			}
			// ケース／ピース区分
			if (!StringUtil.isBlank(casepieseflag))
			{
				if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
				{
					rdo_Cpf_Case.setChecked(true);
					rdo_Cpf_Piece.setChecked(false);
					rdo_Cpf_AppointOff.setChecked(false);
				}
				else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
				{
					rdo_Cpf_Case.setChecked(false);
					rdo_Cpf_Piece.setChecked(true);
					rdo_Cpf_AppointOff.setChecked(false);
				}
				else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
				{
					rdo_Cpf_Case.setChecked(false);
					rdo_Cpf_Piece.setChecked(false);
					rdo_Cpf_AppointOff.setChecked(true);
				}
			}
			// 棚
			if (!StringUtil.isBlank(locationNo))
			{
				txt_Location.setText(DisplayText.formatLocationnumber(locationNo));
			}
			// ケース入数
			if (!StringUtil.isBlank(caseqty))
			{
				txt_CaseEntering.setText(caseqty);
			}
			// ボール入数
			if (!StringUtil.isBlank(bundleqty))
			{
				txt_BundleEntering.setText(bundleqty);
			}
			// ケース在庫数
			if (!StringUtil.isBlank(stockcaseqty))
			{
				txt_StockCaseQty.setText(stockcaseqty);
			}
			// ボール在庫数
			if (!StringUtil.isBlank(stockbundleqty))
			{
				txt_StockPieceQty.setText(stockbundleqty);
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
			// 入庫区分
			if (!StringUtil.isBlank(restoring))
			{
				pul_StorageFlag.setSelectedIndex(Integer.parseInt(restoring));
			}
			if (!StringUtil.isBlank(instockday))
			{
				// 入庫日
				txt_StorageDate.setDate(instockday);
				// 入庫時間
				txt_StorageTime.setTime(instockday);
			}
			// 賞味期限
			if (!StringUtil.isBlank(usebydate))
			{
				txt_UseByDate.setText(usebydate);
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//コネクションクローズ
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}

	}

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText()
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

	// Private methods ---------------------------------------------
	/**
	 * 画面初期表示、クリア処理の場合にこのメソッドが呼ばれます。
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			Locale locale = this.httpRequest.getLocale();
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 荷主コード
			txt_ConsignorCode.setText("");
			txt_ConsignorCode.setReadOnly(true);
			// 荷主検索ボタン
			btn_PSConsignorcode.setEnabled(false);
			// 荷主名称
			txt_ConsignorName.setText("");
			txt_ConsignorName.setReadOnly(true);
			// 商品コード
			txt_ItemCode.setText("");
			txt_ItemCode.setReadOnly(true);
			// 商品検索ボタン
			btn_PSItemCode.setEnabled(false);
			// 商品名称
			txt_ItemName.setText("");
			txt_ItemName.setReadOnly(true);
			// ケース/ピース区分(ケース)
			rdo_Cpf_Case.setChecked(true);
			rdo_Cpf_Case.setEnabled(false);
			// ケース/ピース区分(ピース)
			rdo_Cpf_Piece.setChecked(false);
			rdo_Cpf_Piece.setEnabled(false);
			// ケース/ピース区分(指定なし)
			rdo_Cpf_AppointOff.setChecked(false);
			rdo_Cpf_AppointOff.setEnabled(false);
			// ケース入数
			txt_CaseEntering.setText("");
			txt_CaseEntering.setReadOnly(true);
			// 入庫ケース数
			txt_StockCaseQty.setText("");
			txt_StockCaseQty.setReadOnly(true);
			// ケースITF
			txt_CaseItf.setText("");
			txt_CaseItf.setReadOnly(true);
			// ボール入数
			txt_BundleEntering.setText("");
			txt_BundleEntering.setReadOnly(true);
			// 入庫ピース数
			txt_StockPieceQty.setText("");
			txt_StockPieceQty.setReadOnly(true);
			// ボールITF
			txt_BundleItf.setText("");
			txt_BundleItf.setReadOnly(true);
			// 賞味期限
			txt_UseByDate.setText("");
			txt_UseByDate.setReadOnly(true);
			// 入庫日付
			txt_StorageDate.setText("");
			txt_StorageDate.setReadOnly(true);
			// 入庫時間
			txt_StorageTime.setText("");
			txt_StorageTime.setReadOnly(true);

			//端末Noをセット
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo)this.getUserInfo());
			PulldownData pull = new PulldownData(locale, userHandler.getTerminalNo());

			// プルダウン表示データ（倉庫）
			String[] whno = pull.getWareHousePulldownData(conn, WareHouse.AUTOMATID_WAREHOUSE, "", false);
			//プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_WareHouse, whno);
		
	
			// プルダウン表示データ（入庫区分）
			String[] storagestatus = pull.getReStoringPullDownData("");

			//プルダウンデータをプルダウンへセット
			PulldownHelper.setPullDown(pul_StorageFlag, storagestatus);
			pul_StorageFlag.setEnabled(false);

			// 登録ボタン
			btn_Submit.setEnabled(true);
			// 修正ボタン
			btn_Modify.setEnabled(true);
			// 削除ボタン
			btn_Delete.setEnabled(true);
			// 入力倉庫No
			pul_WareHouse.setEnabled(true);
			// 入力棚No
			txt_Location.setReadOnly(false);

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

	// Private methods -----------------------------------------------

	/**
	 * 棚の状態をチェックしメンテナンス可能かどうか判断します。
	 * @param conn Connection 
	 * @param processType 処理区分 
	 * @return true：メンテナンスOK 
	 * @throws Exception 
	 */
	private boolean checkLocation(Connection conn, int processType) throws Exception
	{
		WareHouseHandler wWhHandler = new WareHouseHandler(conn);
		WareHouseSearchKey wWhSearchKey = new WareHouseSearchKey();
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();

		wWhSearchKey.KeyClear();
		// 倉庫ステーションNoより、倉庫区分を取得します。
		wWhSearchKey.setStationNumber(pul_WareHouse.getSelectedValue());
		
		WareHouse wWareHouse = (WareHouse)wWhHandler.findPrimary(wWhSearchKey);
				
		wShSearchKey.KeyClear();
		// 倉庫区分＋入力棚No
		String currstnumber = DisplayText.formatLocation(wWareHouse.getWareHouseNumber(), txt_Location.getString());
		// 倉庫区分＋入力棚No
		wShSearchKey.setStationNumber(currstnumber);
		
		Shelf wShelf = (Shelf)wShHandler.findPrimary(wShSearchKey);

		if (wShelf == null)
		{
			// 実在する棚No.を入力してください
			this.message.setMsgResourceKey("6013090");
			return false;
		}

		// アクセス不可棚はメンテナンス不可とする。
		if (wShelf.getAccessNgFlag() == Shelf.ACCESS_NG)
		{		
			// 指定された棚は、アクセス不可棚のためメンテナンスできません
			this.message.setMsgResourceKey("6013160");	
			return false;
		}

		// 禁止棚チェックを行います。
		if (wShelf.getStatus() == Shelf.STATUS_NG)
		{		
			// 指定された棚は、禁止棚のためメンテナンスできません
			this.message.setMsgResourceKey("6013159");	
			return false;
		}
		
		// 棚の状態をチェック
		if (wShelf.getPresence() == (Shelf.PRESENCE_RESERVATION))
		{
			// 指定された棚は、予約棚のためメンテナンスできません
			this.message.setMsgResourceKey("6013158");	
			return false;
		}
		
		if (processType == MasterAsShelfMaintenanceSCH.M_MODIFY || processType == MasterAsShelfMaintenanceSCH.M_DELETE)
		{
			if (wShelf.getPresence() == Shelf.PRESENCE_EMPTY)
			{
				// 指定された棚には在庫が存在しません
				this.message.setMsgResourceKey("6013134");	
				return false;
			}
		}

		Palette[] wPalette = null;
		// 実棚ならば引当,異常棚チェック
		if (wShelf.getPresence() == Shelf.PRESENCE_STORAGED)
		{
			PaletteHandler wPlHandler = new PaletteHandler(conn);
			PaletteSearchKey wPlSearchKey = new PaletteSearchKey();
			
			// 検索条件をセットします。
			wPlSearchKey.KeyClear();
			wPlSearchKey.setCurrentStationNumber(currstnumber);
			
			wPalette = (Palette[])wPlHandler.find(wPlSearchKey);
			
			int pltstatus = wPalette[0].getStatus();
			switch (pltstatus)
			{
				// 実棚
				case Palette.REGULAR:
					break;
				// 入庫予約
				case Palette.STORAGE_PLAN:
				// 出庫予約
				case Palette.RETRIEVAL_PLAN:
				// 出庫中
				case Palette.RETRIEVAL:
					// 指定された棚の在庫は現在引当中です
					this.message.setMsgResourceKey("6013135");	
					return false;
				// 異常
				case Palette.IRREGULAR:
					// 処理区分が登録、修正の場合、異常棚はメンテナンス不可
					if (processType == MasterAsShelfMaintenanceSCH.M_CREATE || processType == MasterAsShelfMaintenanceSCH.M_MODIFY)
					{
						// 指定された棚は、異常棚のためメンテナンスできません。
						this.message.setMsgResourceKey("6013157");	
						return false;
					}
			}
		}

		// 画面で入力された棚No.の棚状態を取得します。
		//アクセス可能棚
		String str = "";
		if ( wShelf.getAccessNgFlag() == Shelf.ACCESS_OK )
		{
			//禁止棚
			if ( wShelf.getStatus() == Shelf.STATUS_NG )
			{
				str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
			}
			else
			{
				// 空棚
				if ( wShelf.getPresence() == Shelf.PRESENCE_EMPTY )
				{
					str = DisplayText.getText("FINDUTIL_EMPTY");
				}
				// 実棚
				else if ( wShelf.getPresence() == Shelf.PRESENCE_STORAGED )
				{

					// 実棚でかつ禁止棚のときは禁止棚として表示
					if ( wPalette[0].getStatus() == Shelf.STATUS_NG )
					{
						str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
					}
					//出庫中(作業棚として表示)
					else if(wPalette[0].getStatus() == Palette.RETRIEVAL_PLAN ||
					wPalette[0].getStatus() == Palette.RETRIEVAL)
					{
						str = DisplayText.getText("FINDUTIL_WORK");
					}
					else
					{
						//空パレット
						if(wPalette[0].getEmpty() == Palette.STATUS_EMPTY)
						{
							str = DisplayText.getText("FINDUTIL_EMPTYPALETTE");
						}
						else
						{
							//異常棚
							if(wPalette[0].getStatus() == Palette.IRREGULAR)
							{
								str = DisplayText.getText("FINDUTIL_IRREGULAR");
							}
							//実棚
							else
							{
								str = DisplayText.getText("FINDUTIL_STORAGED");
							}
						}
					}
				}
				// 予約棚(作業棚として表示)
				else if ( wShelf.getPresence() == Shelf.PRESENCE_RESERVATION )
				{
					str = DisplayText.getText("FINDUTIL_WORK");
				}
			}
		}
		// アクセス不可棚
		else
		{
			//アクセス不可棚であり、禁止棚である場合は禁止棚を優先として表示する。
			if ( wShelf.getStatus() == Shelf.STATUS_NG)
			{
				str = DisplayText.getText("FINDUTIL_UNAVAILABLE");
			}
			else
			{
				str = DisplayText.getText("FINDUTIL_ACCESSNGFLAG");
			}
		}
		lbl_JavaSetLocationStatus.setText(str);
		lbl_JavaSetLocationNo.setText(DisplayText.formatLocation(currstnumber));
		
		return true;
	}

	/**
	 * 処理区分によって入力項目の入力可能、不可を切り替えます。
	 * @param itemKey 
	 * @throws Exception 
	 */
	private void changeInputArea(int procType) throws Exception
	{
		Connection conn = null;
		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			MasterAsScheduleParameter param = new MasterAsScheduleParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new MasterAsShelfMaintenanceSCH();
			param = (MasterAsScheduleParameter) schedule.initFind(conn, param);
			
			// ViewStateに処理種別を保持する。
			this.getViewState().setInt(PROCESSTYPE_KEY, procType);
			switch (procType)
			{
				// 登録
				case MasterAsShelfMaintenanceSCH.M_CREATE:
					// 荷主コード
					txt_ConsignorCode.setText("");
					txt_ConsignorCode.setReadOnly(false);
					// 荷主検索ボタン
					btn_PSConsignorcode.setEnabled(true);
					// 荷主名称
					txt_ConsignorName.setText("");
					txt_ConsignorName.setReadOnly(false);
					// 商品コード
					txt_ItemCode.setText("");
					txt_ItemCode.setReadOnly(false);
					// 商品検索ボタン
					btn_PSItemCode.setEnabled(true);
					// 商品名称
					txt_ItemName.setText("");
					txt_ItemName.setReadOnly(false);
					// ケース/ピース区分(ケース)
					rdo_Cpf_Case.setChecked(true);
					rdo_Cpf_Case.setEnabled(true);
					// ケース/ピース区分(ピース)
					rdo_Cpf_Piece.setChecked(false);
					rdo_Cpf_Piece.setEnabled(true);
					// ケース/ピース区分(指定なし)
					rdo_Cpf_AppointOff.setChecked(false);
					rdo_Cpf_AppointOff.setEnabled(true);
					// ケース入数
					txt_CaseEntering.setText("");
					txt_CaseEntering.setReadOnly(false);
					// 入庫ケース数
					txt_StockCaseQty.setText("");
					txt_StockCaseQty.setReadOnly(false);
					// ケースITF
					txt_CaseItf.setText("");
					txt_CaseItf.setReadOnly(false);
					// ボール入数
					txt_BundleEntering.setText("");
					txt_BundleEntering.setReadOnly(false);
					// 入庫ピース数
					txt_StockPieceQty.setText("");
					txt_StockPieceQty.setReadOnly(false);
					// ボールITF
					txt_BundleItf.setText("");
					txt_BundleItf.setReadOnly(false);
					// 賞味期限
					txt_UseByDate.setText("");
					txt_UseByDate.setReadOnly(false);
					// 入庫日付
					txt_StorageDate.setText("");
					txt_StorageDate.setReadOnly(false);
					// 入庫時間
					txt_StorageTime.setText("");
					txt_StorageTime.setReadOnly(false);
					// 入庫区分
					pul_StorageFlag.setEnabled(true);

					// 問合せボタン
					btn_Query.setEnabled(false);
					// 登録ボタン
					btn_Submit.setEnabled(false);
					// 修正ボタン
					btn_Modify.setEnabled(false);
					// 削除ボタン
					btn_Delete.setEnabled(false);
					// 設定ボタン
					btn_Setting.setEnabled(true);
					// クリアボタン
					btn_Clear.setEnabled(true);
					// 入力棚No
					txt_Location.setReadOnly(true);
					// 入力倉庫No
					pul_WareHouse.setEnabled(false);
					// 棚状態(空棚)
					chk_LocationStatus_Empty.setEnabled(false);
					// 棚状態(空PB棚)
					chk_LocationStatus_Empty_PB.setEnabled(false);
					// 棚状態(実棚)
					chk_LocationStatus_Unit.setEnabled(false);
			
					// 登録ボタン押下時確認ダイアログ "登録しますか？"
					btn_Setting.setBeforeConfirm("MSG-W0009");

					if (param.getMergeType() == MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE)
					{
						txt_ConsignorName.setReadOnly(true);
						txt_ItemName.setReadOnly(true);
						txt_CaseEntering.setReadOnly(true);
						txt_BundleEntering.setReadOnly(true);
						txt_CaseItf.setReadOnly(true);
						txt_BundleItf.setReadOnly(true);
					}
					
					break;
				// 修正
				case MasterAsShelfMaintenanceSCH.M_MODIFY:
					// ケース/ピース区分(ケース)
					rdo_Cpf_Case.setChecked(true);
					rdo_Cpf_Case.setEnabled(true);
					// ケース/ピース区分(ピース)
					rdo_Cpf_Piece.setChecked(false);
					rdo_Cpf_Piece.setEnabled(true);
					// ケース/ピース区分(指定なし)
					rdo_Cpf_AppointOff.setChecked(false);
					rdo_Cpf_AppointOff.setEnabled(true);
					// ケース入数
					txt_CaseEntering.setText("");
					txt_CaseEntering.setReadOnly(false);
					// 入庫ケース数
					txt_StockCaseQty.setText("");
					txt_StockCaseQty.setReadOnly(false);
					// ケースITF
					txt_CaseItf.setText("");
					txt_CaseItf.setReadOnly(false);
					// ボール入数
					txt_BundleEntering.setText("");
					txt_BundleEntering.setReadOnly(false);
					// 入庫ピース数
					txt_StockPieceQty.setText("");
					txt_StockPieceQty.setReadOnly(false);
					// ボールITF
					txt_BundleItf.setText("");
					txt_BundleItf.setReadOnly(false);
					// 賞味期限
					txt_UseByDate.setText("");
					txt_UseByDate.setReadOnly(false);
					// 入庫日付
					txt_StorageDate.setText("");
					txt_StorageDate.setReadOnly(false);
					// 入庫時間
					txt_StorageTime.setText("");
					txt_StorageTime.setReadOnly(false);
					// 入庫区分
					pul_StorageFlag.setEnabled(true);
		
					// 問合せボタン
					btn_Query.setEnabled(false);
					// 登録ボタン
					btn_Submit.setEnabled(false);
					// 修正ボタン
					btn_Modify.setEnabled(false);
					// 削除ボタン
					btn_Delete.setEnabled(false);
					// 設定ボタン
					btn_Setting.setEnabled(true);
					// クリアボタン
					btn_Clear.setEnabled(true);
					// 入力棚No
					txt_Location.setReadOnly(true);
					// 入力倉庫No
					pul_WareHouse.setEnabled(false);
					// 棚状態(空棚)
					chk_LocationStatus_Empty.setEnabled(false);
					// 棚状態(空PB棚)
					chk_LocationStatus_Empty_PB.setEnabled(false);
					// 棚状態(実棚)
					chk_LocationStatus_Unit.setEnabled(false);
			
					// 修正ボタン押下時確認ダイアログ "修正しますか？"
					btn_Setting.setBeforeConfirm("MSG-W0014");

					if (param.getMergeType() == MasterAsScheduleParameter.MERGE_TYPE_OVERWRITE)
					{
						txt_ConsignorCode.setReadOnly(true);
						txt_ItemCode.setReadOnly(true);
					}
					
					break;
				// 削除
				case MasterAsShelfMaintenanceSCH.M_DELETE:
					// 問合せボタン
					btn_Query.setEnabled(false);
					// 登録ボタン
					btn_Submit.setEnabled(false);
					// 修正ボタン
					btn_Modify.setEnabled(false);
					// 削除ボタン
					btn_Delete.setEnabled(false);
					// 設定ボタン
					btn_Setting.setEnabled(true);
					// クリアボタン
					btn_Clear.setEnabled(true);
					// 入力棚No
					txt_Location.setReadOnly(true);
					// 入力倉庫No
					pul_WareHouse.setEnabled(false);
					// 棚状態(空棚)
					chk_LocationStatus_Empty.setEnabled(false);
					// 棚状態(空PB棚)
					chk_LocationStatus_Empty_PB.setEnabled(false);
					// 棚状態(実棚)
					chk_LocationStatus_Unit.setEnabled(false);
			
					// 削除ボタン押下時確認ダイアログ "削除しますか？"
					btn_Setting.setBeforeConfirm("MSG-W0007");

					break;
			}
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
	 * AS/RSの在庫に荷主コードが１件ならば、初期表示します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @throws Exception 
	 */
	private void dispConsignorInfo(Connection conn) throws Exception
	{
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		// 検索条件のセット
		// 在庫ステータス(センター在庫)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が1以上であること
		searchKey.setStockQty(1, ">=");
		// ASRSのエリアを取得し、検索条件に付加する。
		// 該当エリアがなかった場合はIS NULL検索
		int[] areaType = {Area.SYSTEM_DISC_KEY_ASRS};
		AreaOperator areaOperator = new AreaOperator(conn);
		String[] areaNo = areaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		// 取得条件のセット
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			// データの検索			
			searchKey.KeyClear();
			// 在庫ステータス(センター在庫) 且つ、在庫数が１以上
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			searchKey.setStockQty(1, ">=");
			// 登録日時の新しい荷主名称を取得します。
			searchKey.setLastUpdateDateOrder(1, false);
			// エリアNo
			searchKey.setAreaNo(areaNo);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				Stock[] consignor = (Stock[]) stockFinder.getEntities(1);
				
				txt_ConsignorCode.setText(consignor[0].getConsignorCode());
				txt_ConsignorName.setText(consignor[0].getConsignorName());
			}
		}
		stockFinder.close();
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
	 * 問合せボタンが押されたときに呼ばれます。
	 * 棚No.別在庫一覧リストボックスを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Query_Click(ActionEvent e) throws Exception
	{
		//棚状態を取得する
		Vector statusVec = new Vector();
		//空棚
		if (chk_LocationStatus_Empty.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_EMPTY));
		}
		//空PB棚
		if (chk_LocationStatus_Empty_PB.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE));
		}
		//実棚
		if (chk_LocationStatus_Unit.getChecked())
		{
			statusVec.add(Integer.toString(AsScheduleParameter.STATUS_STORAGED));
		}
		String[] statusList = new String[statusVec.size()];
		statusVec.copyInto(statusList);
		
		// 棚No別在庫一覧へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsLocationDetailStatusListBusiness.WAREHOUSE_KEY , pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsLocationDetailStatusListBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());		
		param.setParameter(ListAsLocationDetailStatusListBusiness.LOCATIONSTATUS_KEY , statusList);

		// 棚No別在庫一覧表示
		redirect("/asrs/listbox/listasrslocationdetailstatuslist/ListAsLocationDetailStatusList.do", param, "/progress.do");
		
	}

	/** 
	 * 棚明細ボタンが押されたときに呼ばれます。
	 * 棚明細一覧リストボックスを表示します。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_LocationDetails_Click(ActionEvent e) throws Exception
	{
		// 棚明細一覧表示へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSE_KEY , pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailNoBtnBusiness.WAREHOUSENAME_KEY , pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailNoBtnBusiness.LOCATION_KEY, 
							DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));

		// 棚明細一覧表示
		redirect("/asrs/listbox/listasrsstockdetailnobtn/ListAsStockDetailNoBtn.do", param, "/progress.do");	
	}

	/** 
	 * 登録ボタンが押されたときに呼ばれます。
	 * ためうちエリアを入力可にします。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		//入力チェック
		txt_Location.validate();
			
		try
		{
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//棚状態をチェック
			if (checkLocation(conn, MasterAsShelfMaintenanceSCH.M_CREATE))
			{
				// ViewStateに処理種別を保持する。
				this.getViewState().setInt(PROCESSTYPE_KEY, MasterAsShelfMaintenanceSCH.M_CREATE);
				// 入力領域をクリア
				changeInputArea(MasterAsShelfMaintenanceSCH.M_CREATE);
				// 荷主が一件であれば初期表示します。。
				dispConsignorInfo(conn);
				txt_StorageDate.setDate(new Date());
			}
			else
			{
				// 初期状態に戻す
				btn_Clear_Click(null);
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this) );
		}
		finally
		{
			try
			{
				//コネクションクローズ
				if(conn != null) conn.close();
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * 修正ボタンが押されたときに呼ばれます。
	 * 棚明細一覧リストボックス（ボタンあり）を表示します。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
		//棚明細一覧表示へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY,
				DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));

		param.setParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY , Integer.toString(MasterAsShelfMaintenanceSCH.M_MODIFY));
		// ViewStateに処理種別を保持する。
		this.getViewState().setInt(PROCESSTYPE_KEY, MasterAsShelfMaintenanceSCH.M_MODIFY);
	
		//棚明細一覧表示
		redirect("/asrs/listbox/listasrsstockdetail/ListAsStockDetail.do", param, "/progress.do");	
	}

	/** 
	 * 削除ボタンが押されたときに呼ばれます。
	 * 棚明細一覧リストボックス（ボタンあり）を表示します。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		//棚明細一覧表示へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSE_KEY, pul_WareHouse.getSelectedValue());
		param.setParameter(ListAsStockDetailBusiness.WAREHOUSENAME_KEY, pul_WareHouse.getSelectedItem().getText());
		param.setParameter(ListAsStockDetailBusiness.LOCATION_KEY,
			DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));
		param.setParameter(ListAsStockDetailBusiness.PROCESSTYPE_KEY , Integer.toString(MasterAsShelfMaintenanceSCH.M_DELETE));
		// ViewStateに処理種別を保持する。
		this.getViewState().setInt(PROCESSTYPE_KEY, MasterAsShelfMaintenanceSCH.M_DELETE);
		
		//棚明細一覧表示
		redirect("/asrs/listbox/listasrsstockdetail/ListAsStockDetail.do", param, "/progress.do");	
	}

	/** 
	 * 荷主コード検索ボタンが押されたときに呼ばれます。
	 * 荷主一覧リストボックスを表示します。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSConsignorcode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 検索条件
		param.setParameter(ListAsConsignorBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 処理中画面->結果画面
		if (this.getViewState().getInt(PROCESSTYPE_KEY) == MasterAsShelfMaintenanceSCH.M_CREATE)
		{
			redirect("/master/listbox/listmasterconsignor/ListMasterConsignor.do", param, "/progress.do");
		}
		else
		{
			redirect("/asrs/listbox/listasrsconsignor/ListAsConsignor.do", param, "/progress.do");
		}
	}

	/** 
	 * 商品コード検索ボタンが押されたときに呼ばれます。
	 * 商品一覧リストボックスを表示します。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 検索条件
		param.setParameter(ListAsItemBusiness.SEARCHITEM_KEY, AsScheduleParameter.SEARCHFLAG_STOCK);
		// 荷主コード
		param.setParameter(ListAsConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListAsItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 処理中画面->結果画面
		if (this.getViewState().getInt(PROCESSTYPE_KEY) == MasterAsShelfMaintenanceSCH.M_CREATE)
		{
			redirect("/master/listbox/listmasteritem/ListMasterItem.do", param, "/progress.do");
		}
		else
		{
			redirect("/asrs/listbox/listasrsitem/ListAsItem.do", param, "/progress.do");
		}
	}

	/** 
	 * 設定ボタンが押されたときに呼ばれます。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Setting_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);
	
			// ViewStateに処理種別を取得する。
			int processkey = this.getViewState().getInt(PROCESSTYPE_KEY);
		
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterAsShelfMaintenanceSCH(conn);
			// 入力必須チェック
			// パターンマッチング文字
			if (processkey == MasterAsShelfMaintenanceSCH.M_CREATE)
			{
				// 登録ボタン押下時確認ダイアログ "登録しますか？"
				btn_Setting.setBeforeConfirm("MSG-W0009");
				txt_WorkerCode.validate();
				txt_PassWord.validate();
				txt_ConsignorCode.validate();
				txt_ItemCode.validate();
				txt_StorageDate.validate();
				txt_ConsignorName.validate(false);
				txt_ItemName.validate(false);
				
				MasterAsScheduleParameter mergeParam = new MasterAsScheduleParameter();
				mergeParam.setConsignorCode(txt_ConsignorCode.getText());
				mergeParam.setConsignorName(txt_ConsignorName.getText());
				mergeParam.setItemCode(txt_ItemCode.getText());
				mergeParam.setItemName(txt_ItemName.getText());
				mergeParam.setEnteringQty(txt_CaseEntering.getInt());
				mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
				mergeParam.setITF(txt_CaseItf.getText());
				mergeParam.setBundleITF(txt_BundleItf.getText());
				
				// 補完処理を行った結果で、入力チェックを行う
				mergeParam = (MasterAsScheduleParameter) schedule.query(conn, mergeParam)[0];
				txt_ConsignorName.setText(mergeParam.getConsignorName());
				txt_ItemName.setText(mergeParam.getItemName());
				txt_CaseEntering.setInt(mergeParam.getEnteringQty());
				txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
				txt_CaseItf.setText(mergeParam.getITF());
				txt_BundleItf.setText(mergeParam.getBundleITF());
			}
			else if (processkey == MasterAsShelfMaintenanceSCH.M_MODIFY)
			{
				// 修正ボタン押下時確認ダイアログ "修正しますか？"
				btn_Setting.setBeforeConfirm("MSG-W0014");
				txt_WorkerCode.validate();
				txt_PassWord.validate();
				txt_StorageDate.validate();
			}
			else
			{
				// 削除ボタン押下時確認ダイアログ "削除しますか？"
				btn_Setting.setBeforeConfirm("MSG-W0007");
				txt_WorkerCode.validate();
				txt_PassWord.validate();
			}
		
			txt_CaseEntering.validate(false);
			txt_StockCaseQty.validate(false);
			txt_CaseItf.validate(false);
			txt_BundleEntering.validate(false);
			txt_StockPieceQty.validate(false);
			txt_BundleItf.validate(false);
			txt_UseByDate.validate(false);
			txt_StorageDate.validate(false);
			txt_StorageTime.validate(false);
	
			// eWareNavi用入力文字チェック
			if (!checkContainNgText())
			{
				return;
			}
			ASFindUtil findutil = new ASFindUtil(conn);
	
			// スケジュールパラメータへセット
			MasterAsScheduleParameter param = new MasterAsScheduleParameter();

			param.setWorkerCode(txt_WorkerCode.getText());
			param.setPassword(txt_PassWord.getText());
			param.setWareHouseNo(pul_WareHouse.getSelectedValue());
			param.setAreaNo(pul_WareHouse.getSelectedValue());
			param.setLocationNo(DisplayText.formatLocation(pul_WareHouse.getSelectedValue(), txt_Location.getString()));
			param.setProcessStatus(Integer.toString(processkey));
			param.setConsignorCode(txt_ConsignorCode.getText());
			param.setConsignorName(txt_ConsignorName.getText());
			param.setItemCode(txt_ItemCode.getText());
			param.setItemName(txt_ItemName.getText());
			if (rdo_Cpf_Case.getChecked())
			{
				param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_Cpf_Piece.getChecked())
			{
				param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_PIECE);
			}
			else
			{
				param.setCasePieceFlag(AsScheduleParameter.CASEPIECE_FLAG_NOTHING);
			}
			param.setEnteringQty(txt_CaseEntering.getInt());
			param.setStockCaseQty(txt_StockCaseQty.getInt());
			param.setITF(txt_CaseItf.getText());
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			param.setStockPieceQty(txt_StockPieceQty.getInt());
			param.setBundleITF(txt_BundleItf.getText());
			param.setStoringStatus(pul_StorageFlag.getSelectedValue());
			param.setUseByDate(txt_UseByDate.getText());
			Date storageDate = findutil.getDate(txt_StorageDate.getDate(), txt_StorageTime.getTime());
			if (storageDate == null) storageDate = new Date();
			param.setInStockDate(storageDate);
			if (processkey != MasterAsShelfMaintenanceSCH.M_CREATE)
			{
				param.setStockId(this.getViewState().getString(MNTSTOCK_ID));
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(MNTLAST_UPDATE)));
			}
			else
			{
				param.setStockId("");
			}

			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
	
			// 入力チェックを行います。
			if (schedule.check(conn, param))
			{
				MasterAsScheduleParameter[] paramArray = new MasterAsScheduleParameter[1];
				paramArray[0] = param;
				// スケジュールスタート
				if (schedule.startSCH(conn, paramArray))
				{
					// 結果がtrueであれば、commit
					conn.commit();
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());

					// 荷主コード
					txt_ConsignorCode.setReadOnly(true);
					// 荷主検索ボタン
					btn_PSConsignorcode.setEnabled(false);
					// 荷主名称
					txt_ConsignorName.setReadOnly(true);
					// 商品コード
					txt_ItemCode.setReadOnly(true);
					// 商品検索ボタン
					btn_PSItemCode.setEnabled(false);
					// 商品名称
					txt_ItemName.setReadOnly(true);
					// ケース/ピース区分(ケース)
					rdo_Cpf_Case.setEnabled(false);
					// ケース/ピース区分(ピース)
					rdo_Cpf_Piece.setEnabled(false);
					// ケース/ピース区分(指定なし)
					rdo_Cpf_AppointOff.setEnabled(false);
					// ケース入数
					txt_CaseEntering.setReadOnly(true);
					// 入庫ケース数
					txt_StockCaseQty.setReadOnly(true);
					// ケースITF
					txt_CaseItf.setReadOnly(true);
					// ボール入数
					txt_BundleEntering.setReadOnly(true);
					// 入庫ピース数
					txt_StockPieceQty.setReadOnly(true);
					// ボールITF
					txt_BundleItf.setReadOnly(true);
					// 賞味期限
					txt_UseByDate.setReadOnly(true);
					// 入庫日付
					txt_StorageDate.setReadOnly(true);
					// 入庫時間
					txt_StorageTime.setReadOnly(true);
					// 入庫区分
					pul_StorageFlag.setEnabled(false);

					// 問合せボタン
					btn_Query.setEnabled(true);
					// 登録ボタン
					btn_Submit.setEnabled(true);
					// 修正ボタン
					btn_Modify.setEnabled(true);
					// 削除ボタン
					btn_Delete.setEnabled(true);
					// 設定ボタン
					btn_Setting.setEnabled(false);
					// クリアボタン
					btn_Clear.setEnabled(false);
					// 入力棚No
					txt_Location.setReadOnly(false);
					// 入力倉庫No
					pul_WareHouse.setEnabled(true);
					if (processkey != MasterAsShelfMaintenanceSCH.M_MODIFY)
					{
						btn_Clear_Click(null);
					}
					// 棚状態(空棚)
					chk_LocationStatus_Empty.setEnabled(true);
					// 棚状態(空PB棚)
					chk_LocationStatus_Empty_PB.setEnabled(true);
					// 棚状態(実棚)
					chk_LocationStatus_Unit.setEnabled(true);
		

					// ViewStateの値を初期化する。
					this.getViewState().setInt(PROCESSTYPE_KEY, MasterAsShelfMaintenanceSCH.M_NOPROCESS);				

				}
				else
				{
					conn.rollback();
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
				}
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
	 * クリアボタンが押されたときに呼ばれます。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 表示棚No
		lbl_JavaSetLocationNo.setText("");
		// 表示棚状態
		lbl_JavaSetLocationStatus.setText("");
		// 荷主コード
		txt_ConsignorCode.setText("");
		txt_ConsignorCode.setReadOnly(true);
		// 荷主検索ボタン
		btn_PSConsignorcode.setEnabled(false);
		// 荷主名称
		txt_ConsignorName.setText("");
		txt_ConsignorName.setReadOnly(true);
		// 商品コード
		txt_ItemCode.setText("");
		txt_ItemCode.setReadOnly(true);
		// 商品検索ボタン
		btn_PSItemCode.setEnabled(false);
		// 商品名称
		txt_ItemName.setText("");
		txt_ItemName.setReadOnly(true);
		// ケース/ピース区分(ケース)
		rdo_Cpf_Case.setChecked(true);
		rdo_Cpf_Case.setEnabled(false);
		// ケース/ピース区分(ピース)
		rdo_Cpf_Piece.setChecked(false);
		rdo_Cpf_Piece.setEnabled(false);
		// ケース/ピース区分(指定なし)
		rdo_Cpf_AppointOff.setChecked(false);
		rdo_Cpf_AppointOff.setEnabled(false);
		// ケース入数
		txt_CaseEntering.setText("");
		txt_CaseEntering.setReadOnly(true);
		// 入庫ケース数
		txt_StockCaseQty.setText("");
		txt_StockCaseQty.setReadOnly(true);
		// ケースITF
		txt_CaseItf.setText("");
		txt_CaseItf.setReadOnly(true);
		// ボール入数
		txt_BundleEntering.setText("");
		txt_BundleEntering.setReadOnly(true);
		// 入庫ピース数
		txt_StockPieceQty.setText("");
		txt_StockPieceQty.setReadOnly(true);
		// ボールITF
		txt_BundleItf.setText("");
		txt_BundleItf.setReadOnly(true);
		// 賞味期限
		txt_UseByDate.setText("");
		txt_UseByDate.setReadOnly(true);
		// 入庫日付
		txt_StorageDate.setText("");
		txt_StorageDate.setReadOnly(true);
		// 入庫時間
		txt_StorageTime.setText("");
		txt_StorageTime.setReadOnly(true);
		// 入庫区分
		pul_StorageFlag.setSelectedIndex(0);
		pul_StorageFlag.setEnabled(false);

		// 問合せボタン
		btn_Query.setEnabled(true);
		// 登録ボタン
		btn_Submit.setEnabled(true);
		// 修正ボタン
		btn_Modify.setEnabled(true);
		// 削除ボタン
		btn_Delete.setEnabled(true);
		// 設定ボタン
		btn_Setting.setEnabled(false);
		// クリアボタン
		btn_Clear.setEnabled(false);
		// 入力棚No
		txt_Location.setReadOnly(false);
		// 入力倉庫No
		pul_WareHouse.setEnabled(true);
		// 棚状態(空棚)
		chk_LocationStatus_Empty.setEnabled(true);
		// 棚状態(空PB棚)
		chk_LocationStatus_Empty_PB.setEnabled(true);
		// 棚状態(実棚)
		chk_LocationStatus_Unit.setEnabled(true);
		
		// ViewStateの値を初期化する。
		this.getViewState().setInt(PROCESSTYPE_KEY, MasterAsShelfMaintenanceSCH.M_NOPROCESS);				
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Set_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WareHouse_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationStatus_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_PB_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Empty_PB_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Unit_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_LocationStatus_Unit_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationDetail_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Query_Server(ActionEvent e) throws Exception
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
	public void txt_Location_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Location_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_LocationDetails_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ProcessFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Modify_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LocationNoFlag_Server(ActionEvent e) throws Exception
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

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetLocationStatus_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSConsignorcode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StockPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StorageFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StorageFlag_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageDay_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageTime_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageTime_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Setting_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

}
//end of class

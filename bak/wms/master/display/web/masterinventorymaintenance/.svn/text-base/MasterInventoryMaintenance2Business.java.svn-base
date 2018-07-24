// $Id: MasterInventoryMaintenance2Business.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterinventorymaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.schedule.MasterInventoryMaintenanceSCH;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.master.schedule.MasterStorageSupportParameter;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * 棚卸し(詳細情報設定)クラスです。<BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示します。<BR>
 * 棚卸しを行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.入力ボタン押下処理(<CODE>btn_Input_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時は入力エリアの情報をためうちエリアに追加します。<BR>
 *   修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 *		商品コード* <BR>
 *		商品名称 <BR>
 *		ケース入数*2 <BR>
 *		棚卸ケース数*3 <BR>
 *		棚卸ピース数*3 <BR>
 *		棚* <BR>
 *		賞味期限 <BR>
 *   <BR>
 * 		*2 <BR>
 * 		棚卸ケース数(>0)入力時、必須入力 <BR>
 * 		*3 <BR>
 * 		棚卸ケース数、棚卸ピース数何れかに1以上の入力が必須条件 <BR>
 * 		棚卸ケース数×ケース入数＋棚卸ピース数が0でない事(>0) <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * 2.棚卸データ更新ボタン押下処理(<CODE>btn_InventoryData_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアの内容をパラメータにセットし、そのパラメータを基にスケジュールが棚卸しを行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時はためうちの登録情報をクリアします。<BR>
 *   <BR>
 *   [パラメータ]<BR>
 *   <BR>
 *   <DIR>
 *		作業者コード <BR>
 * 		荷主コード <BR>
 * 		荷主名称 <BR>
 * 		棚卸作業有無 <BR>
 *		商品コード <BR>
 *		商品名称 <BR>
 *		ケース入数 <BR>
 *		ボール入数 <BR>
 *		棚卸ケース数 <BR>
 *		棚卸ピース数 <BR>
 *		在庫ケース数 <BR>
 *		在庫ピース数 <BR>
 *		賞味期限 <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterInventoryMaintenance2Business extends MasterInventoryMaintenance2 implements WMSConstants
{
	// Class fields --------------------------------------------------
	/**
	 * 行No.の受け渡しに使用するViewState用キーです
	 */
	public static final String LINENO_KEY = "LINENO_KEY";

	/**
	 * 棚の受け渡しに使用するViewState用キーです
	 */
	public static final String LOCATION_KEY = "LOCATION_KEY";

	/**
	 * 商品コードの受け渡しに使用するViewState用キーです
	 */
	public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

	/**
	 * 商品名称の受け渡しに使用するViewState用キーです
	 */
	public static final String ITEMNAME_KEY = "ITEMNAME_KEY";

	/**
	 * ケース入数の受け渡しに使用するViewState用キーです
	 */
	public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";

	/**
	 * ボール入数の受け渡しに使用するViewState用キーです
	 */
	public static final String BUNDLEENTERINGQTY_KEY = "BUNDLEENTERINGQTY_KEY";
	
	/**
	 * 棚卸ケース数の受け渡しに使用するViewState用キーです
	 */
	public static final String INVENTORYCHECKCASEQTY_KEY = "INVENTORYCHECKCASEQTY_KEY";

	/**
	 * 棚卸ピース数の受け渡しに使用するViewState用キーです
	 */
	public static final String INVENTORYCHECKPIECEQTY_KEY = "INVENTORYCHECKPIECEQTY_KEY";

	/**
	 * 賞味期限の受け渡しに使用するViewState用キーです
	 */
	public static final String USEBYDATE_KEY = "USEBYDATE_KEY";

	/**
	 * 在庫ケース数の受け渡しに使用するViewState用キーです
	 */
	public static final String STOCKCASEQTY_KEY = "STOCKCASEQTY_KEY";
	
	/**
	 * 在庫ピース数の受け渡しに使用するViewState用キーです
	 */
	public static final String STOCKPIECEQTY_KEY = "STOCKPIECEQTY_KEY";
	
	/**
	 * 最終更新日時の受け渡しに使用するViewState用キーです
	 */
	public static final String LASTUPDATE_KEY = "LASTUPDATE_KEY";
	
	/**
	 * 作業№の受け渡しに使用するViewState用キーです
	 */
	public static final String JOBNO_KEY = "JOBNO_KEY";

	/**
	 * 在庫IDの受け渡しに使用するViewState用キーです
	 */
	public static final String STOCKID_KEY = "STOCKID_KEY";	

	/**
	 * 荷主コードの最終使用日を保持するViewState用キーです
	 */
	public static final String CONSIG_DATE_KEY = "CONSIG_DATE";
	
	/* 隠し項目のリストセル列番号 */
	private static final int HIDDEN = 0;
	/* 棚卸作業有無名称のリストセル列番号 */
	private static final int INVENTORYKINDNAME = 3;
	/* 棚のリストセル列番号 */
	private static final int LOCATION = 4;
	/* 商品コードのリストセル列番号 */
	private static final int ITEMCODE = 5;
	/* ケース入数のリストセル列番号 */
	private static final int ENTERINGQTY = 6;
	/* 棚卸ケース数のリストセル列番号 */
	private static final int INVENTORYCHECKCASEQTY = 7;
	/* 在庫ケース数のリストセル列番号 */
	private static final int STOCKCASEQTY = 8;
	/* 賞味期限のリストセル列番号 */
	private static final int USEBYDATE = 9;
	/* 商品名称のリストセル列番号 */
	private static final int ITEMNAME = 10;
	/* ボール入数のリストセル列番号 */
	private static final int BUNDLEENTERINGQTY = 11;
	/* 棚卸ピース数のリストセル列番号 */
	private static final int INVENTORYCHECKPIECEQTY = 12;
	/* 在庫ピース数のリストセル列番号 */
	private static final int STOCKPIECEQTY = 13;

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
	 * 	 1.スケジュールを開始します。
	 *   <DIR>
	 *  	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			荷主コード* <BR>
	 * 			開始棚 <BR>
	 * 			終了棚 <BR>
	 *		</DIR>
	 *		<BR>
	 *		[返却データ]<BR>
	 *		<DIR>
	 * 			棚卸作業有無名称 <BR>
	 * 			棚 <BR>
	 * 			商品コード <BR>
	 * 			商品名称 <BR>
	 * 			ケース入数 <BR>
	 * 			ボール入数 <BR>
	 * 			棚卸ケース数 <BR>
	 * 			棚卸ピース数 <BR>
	 * 			在庫ケース数 <BR>
	 * 			在庫ピース数 <BR>
	 * 			賞味期限 <BR>
	 *		</DIR>
	 *		<BR>
	 * 	 </DIR>
	 *	 2.処理が正常に完了した場合はスケジュールから取得したためうちエリア出力用のデータを元に、入力エリアとためうちエリアの表示を行います。<BR>
	 *   <DIR>
	 *   	ためうち隠し項目
	 * 		<DIR>
	 * 			棚卸作業有無 <BR>
	 *      </DIR>
	 *   	項目名[初期値]<BR>
	 *   	<DIR>
	 * 			荷主コード[基本情報.荷主コード] <BR>
	 * 			荷主名称[返却データ[0].荷主名称] <BR>
	 * 			開始棚[基本情報.開始棚] <BR>
	 * 			終了棚[基本情報.終了棚] <BR>
	 * 			商品コード[なし] <BR>
	 * 			商品名称[なし] <BR>
	 * 			ケース入数[なし] <BR>
	 * 			棚卸ケース数[なし] <BR>
	 * 			棚卸ピース数[なし] <BR>
	 * 			棚[なし] <BR>
	 * 			賞味期限[なし] <BR>
	 * 	 	</DIR>
	 *   	ためうち[初期値]<BR>
	 *   	<DIR>
	 * 			棚卸作業有無名称[登録／未] <BR>
	 * 			棚[返却データ.棚] <BR>
	 * 			商品コード[返却データ.商品コード] <BR>
	 * 			商品名称[返却データ.商品名称] <BR>
	 * 			ケース入数[返却データ.ケース入数] <BR>
	 * 			ボール入数[返却データ.ボール入数] <BR>
	 * 			棚卸ケース数[返却データ.棚卸ケース数] <BR>
	 * 			棚卸ピース数[返却データ.棚卸ピース数] <BR>
	 * 			在庫ケース数[返却データ.在庫ケース数] <BR>
	 * 			在庫ピース数[返却データ.在庫ピース数] <BR>
	 * 			賞味期限[返却データ.賞味期限] <BR>
	 * 	 	</DIR>
	 *   </DIR>
	 *	 該当データが見つからない場合は要素数0のParameter配列を、条件エラーなどが発生した場合はnullを受け取り、前画面に戻ります。<BR>
	 *   3.スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
	 *   4.ViewStateのためうち行No.に初期値:-1を設定します。<BR>
	 *   5.カーソルを商品コードに初期セットします。<BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		lbl_SettingName.setResourceKey(this.getViewState().getString(MasterInventoryMaintenanceBusiness.TITLE_KEY));		

		Connection conn = null;

		try
		{
			// 詳細情報設定のタブを前出しする
			tab_Inventory.setSelectedIndex(2);

			// 基本情報設定の入力されたパラメータを受け取ります
			String consignorcode =
				this.getViewState().getString(MasterInventoryMaintenanceBusiness.CONSIGNORCODE_KEY);
			String startlocation =
				this.getViewState().getString(MasterInventoryMaintenanceBusiness.STARTLOCATION_KEY);
			String endlocation =
				this.getViewState().getString(MasterInventoryMaintenanceBusiness.ENDLOCATION_KEY);
			
			// スケジュールパラメータへセット
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();

			// 荷主コード
			param.setConsignorCode(consignorcode);
			// 開始棚
			param.setFromLocation(startlocation);
			// 終了棚
			param.setToLocation(endlocation);
			// 検索モード
			param.setSearchMode(MasterStorageSupportParameter.SEARCH_MODE_LIST);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);

			MasterStorageSupportParameter[] viewParam =
				(MasterStorageSupportParameter[]) schedule.query(conn, param);

			// 処理が正常に完了した場合は、入力エリアとためうちエリアの表示を行う
			// 入力エリア
			// 荷主コード
			lbl_JavaSetCnsgnrCd.setText(consignorcode);
			// 開始棚
			lbl_JavaSetStartLocation.setText(startlocation);
			// 終了棚
			lbl_JavaSetEndLocation.setText(endlocation);
			
			// 荷主名称を荷主マスタ→在庫情報→棚卸情報の優先順で取得します
			// 荷主マスタ検索
			String masterConsignorName = dispMasterConsignorName(conn, param);
			if (!StringUtil.isBlank(masterConsignorName))
			{
				// 最新の荷主名称を表示します
				lbl_JavaSetCnsgnrNm.setText(masterConsignorName);
			}
			else
			{
				// 在庫情報検索
				String stockConsignorName = dispStockConsignorName(conn, param);
				if (!StringUtil.isBlank(stockConsignorName))
				{
					// 最新の荷主名称を表示します
					lbl_JavaSetCnsgnrNm.setText(stockConsignorName);
				}
				else
				{
					// 棚卸情報検索
					String inventoryConsignorName = dispInventoryConsignorName(conn, param);
					if (!StringUtil.isBlank(inventoryConsignorName))
					{
						// 最新の荷主名称を表示します
						lbl_JavaSetCnsgnrNm.setText(inventoryConsignorName);
					}
					else
					{
						// 最新の荷主名称を表示します
						lbl_JavaSetCnsgnrNm.setText("");
					}
				}
			}
			// ためうちエリア
			setList(viewParam);

			setItemReadOnly();
			
			// ためうち情報が存在しない場合、棚卸データ更新ボタンは無効にする
			if (lst_InventoryMaintenance.getMaxRows() == 1)
			{
				// ボタン押下を無効にする
				// 棚卸データ更新ボタン
				btn_InventoryData.setEnabled(false);
			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			// (デフォルト:-1)
			this.getViewState().setInt(LINENO_KEY, -1);

			// カーソルを商品コードにセットする
			setFocus(txt_ItemCode);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
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
		// 棚卸データ更新ボタン押下時確認ダイアログ "更新しますか？"
		btn_InventoryData.setBeforeConfirm("MSG-W0028");
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
		// 商品コード
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListStorageItemBusiness.ITEMNAME_KEY);
		// ケース入数
		String enteringqty = param.getParameter(ListMasterItemBusiness.ENTERING_KEY);
		// ボール入数
		String bundleenteringqty = param.getParameter(ListMasterItemBusiness.BUNDLEENTERING_KEY);

		// 空ではないときに値をセットする
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
		// ボール入数
		if (!StringUtil.isBlank(enteringqty))
		{
			txt_CaseEntering.setText(enteringqty);
		}
		// ケース入数
		if (!StringUtil.isBlank(bundleenteringqty))
		{
			txt_BundleEntering.setText(bundleenteringqty);
		}
		// カーソルを商品コードにセットする
		setFocus(txt_ItemCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
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
		if (!checker.checkContainNgText(txt_Location))
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

	/**
	 * 入力エリアをクリアします。
	 */
	public void clearInputArea()
	{
		setFocus(txt_ItemCode);
		txt_InventoryCaseQty.setText("");
		txt_InventoryPieceQty.setText("");
		txt_ItemCode.setText("");
		txt_ItemName.setText("");
		txt_CaseEntering.setText("");
		txt_BundleEntering.setText("");
		txt_InventoryCaseQty.setText("");
		txt_InventoryPieceQty.setText("");
		txt_Location.setText("");
		txt_UseByDate.setText("");
	}
	// Private methods -----------------------------------------------
	/**
	 * 最新の荷主名称を在庫より取得するメソッドです。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス
	 * @param param 取得条件を保持する<CODE>StorageSupportParameter</CODE>
	 * @throws Exception 全ての例外を報告します。
	 */
	private String dispStockConsignorName(Connection conn,StorageSupportParameter param) throws Exception
	{
		String consignorName = "";
		
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey nameKey = new StockSearchKey();
		
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			nameKey.setConsignorCode(param.getConsignorCode());
		}

		// 開始棚No
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			nameKey.setLocationNo(param.getFromLocation(), ">=");
		}

		// 終了棚No
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			nameKey.setLocationNo(param.getToLocation(), "<=");
		}

		// 在庫ステータス：センター在庫
		nameKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が１以上
		nameKey.setStockQty(0, ">");
		
		nameKey.setConsignorNameCollect("");
		nameKey.setLastUpdateDateOrder(1, false);
		
		if (stockFinder.search(nameKey) > 0)
		{
			Stock[] stock = (Stock[])stockFinder.getEntities(1);
			consignorName = stock[0].getConsignorName();
		}
		stockFinder.close();
		
		
		return consignorName;
	}
	
	/**
	 * 最新の荷主名称を在庫より取得するメソッドです。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス
	 * @param param 取得条件を保持する<CODE>StorageSupportParameter</CODE>
	 * @throws Exception 全ての例外を報告します。
	 */
	private String dispInventoryConsignorName(Connection conn,StorageSupportParameter param) throws Exception
	{
		String consignorName = "";
		
		InventoryCheckReportFinder invcheckFinder = new InventoryCheckReportFinder(conn);
		InventoryCheckSearchKey nameKey = new InventoryCheckSearchKey();
		
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			nameKey.setConsignorCode(param.getConsignorCode());
		}

		// 開始棚No
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			nameKey.setLocationNo(param.getFromLocation(), ">=");
		}

		// 終了棚No
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			nameKey.setLocationNo(param.getToLocation(), "<=");
		}

		nameKey.setConsignorNameCollect("");
		nameKey.setRegistDateOrder(1, false);
		
		if (invcheckFinder.search(nameKey) > 0)
		{
			InventoryCheck[] inventory = (InventoryCheck[])invcheckFinder.getEntities(1);
			consignorName = inventory[0].getConsignorName();
		}
		invcheckFinder.close();
		
		return consignorName;
	}
	/**
	 * 最新の荷主名称をマスタより取得するメソッドです。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス
	 * @param param 取得条件を保持する<CODE>StorageSupportParameter</CODE>
	 * @throws Exception 全ての例外を報告します。
	 */
	private String dispMasterConsignorName(Connection conn,StorageSupportParameter param) throws Exception
	{
		String consignorName = "";
		
		ConsignorSearchKey nameKey = new ConsignorSearchKey();
		ConsignorReportFinder masterFinder = new ConsignorReportFinder(conn);

		// 検索条件をセットする
		// 荷主コード
		nameKey.setConsignorCode(param.getConsignorCode());
		nameKey.setConsignorNameCollect();
		if (masterFinder.search(nameKey) > 0)
		{
			Consignor[] master = (Consignor[])masterFinder.getEntities(1);
			consignorName = master[0].getConsignorName();
		}
		masterFinder.close();
		
		return consignorName;
	}
	
	/**
	 * ためうちエリアにParameterの値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアにParameterの値をセットします。<BR>
	 * <DIR>
	 * 		隠し項目
	 * 		<DIR>
	 * 			0.棚卸作業有無 <BR>
	 * 			1.修正フラグ
	 * 			2.棚
	 * 			3.商品コード
	 * 			4.商品名称
	 * 			5.ケース入数
	 * 			6.ボール入数
	 * 			7.棚卸ケース数
	 * 			8.棚卸ピース数
	 * 			9.賞味期限
	 * 			10.最終更新日時
	 * 			11.作業№
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param param DBから取得した値を保持するパラメタクラス
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(MasterStorageSupportParameter[] param) throws Exception
	{
		for (int i = 0; i < param.length; i++)
		{
			// 行追加
			lst_InventoryMaintenance.addRow();
			// 現在の行をセット
			lst_InventoryMaintenance.setCurrentRow(i + 1);
			//ToolTip用のデータを編集
			ToolTipHelper toolTip = new ToolTipHelper();
			// 商品名称
			toolTip.add(DisplayText.getText("LBL-W0103"), param[i].getItemName());
			// ケース入数（棚卸データのケース入数）
			toolTip.add(DisplayText.getText("LBL-W0007"), param[i].getEnteringQty());
			// ボール入数（棚卸データのボール入数）
			toolTip.add(DisplayText.getText("LBL-W0005"), param[i].getBundleEnteringQty());
			// 在庫ケース数、在庫ピース数
			if (param[i].getTotalStockQty() == 0)
			{
				toolTip.add(DisplayText.getText("LBL-W0230"), "0");
				toolTip.add(DisplayText.getText("LBL-W0233"), "0");
			}
			else
			{
				toolTip.add(DisplayText.getText("LBL-W0230"),
					WmsFormatter.getNumFormat(param[i].getTotalStockCaseQty()));
				toolTip.add(DisplayText.getText("LBL-W0233"),
					WmsFormatter.getNumFormat(param[i].getTotalStockPieceQty()));
			}

			// 賞味期限
			toolTip.add(DisplayText.getText("LBL-W0270"), param[i].getUseByDate());
			//カレント行にTOOL TIPをセットする
			lst_InventoryMaintenance.setToolTip(i + 1, toolTip.getText());

			// 隠し項目セット準備
			List list = new Vector();
			// 棚卸作業有無
			list.add(param[i].getInventoryKind());
			// 修正フラグ
			list.add(StorageSupportParameter.UPDATEFLAG_NO);
			// 棚
			list.add(param[i].getLocation());
			// 商品コード
			list.add(param[i].getItemCode());
			// 商品名称
			list.add(param[i].getItemName());
			// ケース入数
			list.add(WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			// ボール入数
			list.add(WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			// 棚卸ケース数
			if (param[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
			{
				list.add(WmsFormatter.getNumFormat(param[i].getInventoryCheckCaseQty()));
			}
			else
			{
				list.add("");
			}
			// 棚卸ピース数
			if (param[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
			{
				list.add(WmsFormatter.getNumFormat(param[i].getInventoryCheckPieceQty()));
			}
			else
			{
				list.add("");
			}
			// 賞味期限
			list.add(param[i].getUseByDate());
			// 最終更新日時
			list.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			// 作業№
			list.add(param[i].getJobNo());
			// 在庫ID
			list.add(param[i].getStockID());
			// 商品コード最終使用日
			list.add(WmsFormatter.getTimeStampString(param[i].getItemLastUpdateDate()));
			// ケースITF
			list.add(param[i].getITF());
			// ボールITF
			list.add(param[i].getBundleITF());
			// 隠し項目にセットする
			lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));

			// 棚卸作業有無名称
			lst_InventoryMaintenance.setValue(INVENTORYKINDNAME, param[i].getInventoryKindName());
			// 棚
			lst_InventoryMaintenance.setValue(LOCATION, param[i].getLocation());
			// 商品コード
			lst_InventoryMaintenance.setValue(ITEMCODE, param[i].getItemCode());
			// ケース入数
			lst_InventoryMaintenance.setValue(ENTERINGQTY,
				WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			// 棚卸ケース数、棚卸ピース数
			if (param[i].getInventoryKind().equals(StorageSupportParameter.INVENTORY_KIND_FIND))
			{
				lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY,
					WmsFormatter.getNumFormat(param[i].getInventoryCheckCaseQty()));
				lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY,
					WmsFormatter.getNumFormat(param[i].getInventoryCheckPieceQty()));
			}
			else
			{
				lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY, "");
				lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY, "");
			}

			// 在庫ケース数、在庫ピース数
			if (param[i].getTotalStockQty() == 0)
			{
				lst_InventoryMaintenance.setValue(STOCKCASEQTY, "");
				lst_InventoryMaintenance.setValue(STOCKPIECEQTY, "");
			}
			else
			{
				lst_InventoryMaintenance.setValue(STOCKCASEQTY,
					WmsFormatter.getNumFormat(param[i].getTotalStockCaseQty()));
				lst_InventoryMaintenance.setValue(STOCKPIECEQTY,
					WmsFormatter.getNumFormat(param[i].getTotalStockPieceQty()));
			}
			// 賞味期限
			lst_InventoryMaintenance.setValue(USEBYDATE, param[i].getUseByDate());
			// 商品名称
			lst_InventoryMaintenance.setValue(ITEMNAME, param[i].getItemName());
			// ボール入数
			lst_InventoryMaintenance.setValue(BUNDLEENTERINGQTY,
				WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
				
		}
		
		// viewStateのクリア
		clearViewState();
	}

	/**
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.全てのためうちデータをParameterクラスにセットします。<BR>
	 * <DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineno 対象となる行No。
	 * @throws Exception 全ての例外を報告します。
	 */
	private MasterStorageSupportParameter[] setListParamater(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_InventoryMaintenance.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}
			// 行指定
			lst_InventoryMaintenance.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();
			// 入力エリア情報
			// 棚
			param.setLocation(lst_InventoryMaintenance.getValue(LOCATION));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInventoryMaintenance2Business.CONSIG_DATE_KEY)));

			// ためうちエリア情報
			// 商品コード
			param.setItemCode(lst_InventoryMaintenance.getValue(ITEMCODE));
			// 賞味期限
			param.setUseByDate(lst_InventoryMaintenance.getValue(USEBYDATE));
			
			// 隠し項目
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(13, lst_InventoryMaintenance.getValue(HIDDEN))));
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterStorageSupportParameter[] listparam = new MasterStorageSupportParameter[vecParam.size()];
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
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.修正フラグが"1"の場合、ためうちデータをParameterクラスにセットします。<BR>
	 * <DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。
	 */
	private MasterStorageSupportParameter[] setListParam() throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_InventoryMaintenance.getMaxRows(); i++)
		{
			// 行指定
			lst_InventoryMaintenance.setCurrentRow(i);

			if (CollectionUtils.getString(1, lst_InventoryMaintenance.getValue(HIDDEN))
				.equals(StorageSupportParameter.UPDATEFLAG_YES))
			{
				// スケジュールパラメータへセット
				MasterStorageSupportParameter param = new MasterStorageSupportParameter();
				// 入力エリア情報
				// 荷主コード
				param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				// 荷主名称
				param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
				if (StorageSupportParameter.INVENTORY_KIND_NOTHING.equals(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN))))
				{
					// 荷主コード最終使用日
					param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(
							this.getViewState().getString(MasterInventoryMaintenance2Business.CONSIG_DATE_KEY)));
				}
				// 開始棚
				param.setFromLocation(lbl_JavaSetStartLocation.getText());
				// 終了棚
				param.setToLocation(lbl_JavaSetEndLocation.getText());

				// ためうちエリア情報
				// 棚
				param.setLocation(lst_InventoryMaintenance.getValue(LOCATION));
				// 商品コード
				param.setItemCode(lst_InventoryMaintenance.getValue(ITEMCODE));
				// ケース入数
				param.setEnteringQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(ENTERINGQTY)));
				// 棚卸ケース数
				param.setInventoryCheckCaseQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY)));
				// 在庫ケース数
				param.setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
				// 賞味期限
				param.setUseByDate(lst_InventoryMaintenance.getValue(USEBYDATE));
				// 商品名称
				param.setItemName(lst_InventoryMaintenance.getValue(ITEMNAME));
				// ボール入数
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY)));
				// 棚卸ピース数
				param.setInventoryCheckPieceQty(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY)));
				// 在庫ピース数
				param.setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));
				// 隠し項目
				// 棚卸作業有無(隠し項目)
				param.setInventoryKind(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)));
				// 最終更新日時(隠し項目)
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(10, lst_InventoryMaintenance.getValue(HIDDEN))));
				// 作業№(隠し項目)
				param.setJobNo(CollectionUtils.getString(11, lst_InventoryMaintenance.getValue(HIDDEN)));
				// 在庫ID(隠し項目)
				param.setStockID(CollectionUtils.getString(12, lst_InventoryMaintenance.getValue(HIDDEN)));
				// 商品コード最終使用日
				param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(13, lst_InventoryMaintenance.getValue(HIDDEN))));
				// ケースITF
				param.setITF(CollectionUtils.getString(14, lst_InventoryMaintenance.getValue(HIDDEN)));
				// ボールITF
				param.setBundleITF(CollectionUtils.getString(15, lst_InventoryMaintenance.getValue(HIDDEN)));
				// 行No.を保持しておく
				param.setRowNo(i);
				// 作業者コード
				param.setWorkerCode(this.getViewState().getString(MasterInventoryMaintenanceBusiness.WORKERCODE_KEY));
				// パスワード
				param.setPassword(this.getViewState().getString(MasterInventoryMaintenanceBusiness.PASSWORD_KEY));

				// 端末Noをセット
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());
				
				vecParam.addElement(param);
			}
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterStorageSupportParameter[] listparam = new MasterStorageSupportParameter[vecParam.size()];
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
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * <DIR>
	 * 		隠し項目
	 * 		<DIR>
	 * 			0.TC/DCフラグ <BR>
	 *	 	</DIR>
	 * </DIR>	 * <BR>
	 * @param param DBから取得した値を保持するパラメタクラス。
	 * @param lineno 対象となる行No。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(MasterStorageSupportParameter param, int lineno) throws Exception
	{
		// ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		// ケース入数（棚卸データのケース入数）
		toolTip.add(DisplayText.getText("LBL-W0007"), txt_CaseEntering.getText());
		// ボール入数（棚卸データのボール入数）
		toolTip.add(DisplayText.getText("LBL-W0005"), txt_BundleEntering.getText());
		// 在庫ケース数、在庫ピース数
		if (param.getTotalStockQty() == 0)
		{
			toolTip.add(DisplayText.getText("LBL-W0230"),
			 WmsFormatter.getInt(""));
			toolTip.add(DisplayText.getText("LBL-W0233"),
			 WmsFormatter.getInt(""));
		}
		else
		{
			toolTip.add(DisplayText.getText("LBL-W0230"),
				WmsFormatter.getNumFormat(param.getTotalStockCaseQty()));
			toolTip.add(DisplayText.getText("LBL-W0233"),
				WmsFormatter.getNumFormat(param.getTotalStockPieceQty()));
		}
		// 賞味期限
		toolTip.add(DisplayText.getText("LBL-W0270"), param.getUseByDate());
		// カレント行にTOOL TIPをセットする
		lst_InventoryMaintenance.setToolTip(lst_InventoryMaintenance.getCurrentRow(),
		toolTip.getText());

		// 隠し項目にセットする
		List list = new Vector();
		if(lineno == -1)
		{
			// 棚卸作業有無
			list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);		
		}
		else if(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
		{		
			// 棚卸作業有無
			list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);
		}
		else
		{
			// 棚卸作業有無
			list.add(StorageSupportParameter.INVENTORY_KIND_FIND);	
		}
		// 修正フラグ
		list.add(StorageSupportParameter.UPDATEFLAG_YES);
		// 棚
		list.add("");
		// 商品コード
		list.add("");
		// 商品名称
		list.add("");
		// ケース入数
		list.add("");
		// ボール入数
		list.add("");
		// 棚卸ケース数
		list.add("");
		// 棚卸ピース数
		list.add("");
		// 賞味期限
		list.add("");
		// 最終更新日時
		list.add(this.getViewState().getString(LASTUPDATE_KEY));
		// 作業№
		list.add(this.getViewState().getString(JOBNO_KEY));
		// 在庫ID
		list.add(this.getViewState().getString(STOCKID_KEY));
		// 商品コード最終使用日
		list.add(WmsFormatter.getTimeStampString(param.getItemLastUpdateDate()));
		// ケースITF
		list.add(param.getITF());
		// ボールITF
		list.add(param.getBundleITF());
		// 隠し項目にセットする
		lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));

		// 棚卸作業有無名称
		// LBL-W0394=未
		if(CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
		{
			lst_InventoryMaintenance.setValue(INVENTORYKINDNAME, DisplayText.getText("LBL-W0394"));
		}
		// LBL-W0391=登録
		else
		{
			lst_InventoryMaintenance.setValue(INVENTORYKINDNAME, DisplayText.getText("LBL-W0391"));
		}
		
		// 棚
		lst_InventoryMaintenance.setValue(LOCATION, param.getLocation());
		// 商品コード
		lst_InventoryMaintenance.setValue(ITEMCODE, param.getItemCode());
		// ケース入数
		lst_InventoryMaintenance.setValue(ENTERINGQTY,
			WmsFormatter.getNumFormat(param.getEnteringQty()));
		// 棚卸ケース数
		lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY,
			WmsFormatter.getNumFormat(param.getInventoryCheckCaseQty()));
		// 在庫ケース数
		lst_InventoryMaintenance.setValue(STOCKCASEQTY,lst_InventoryMaintenance.getValue(STOCKCASEQTY));
		// 賞味期限
		lst_InventoryMaintenance.setValue(USEBYDATE, param.getUseByDate());
		// 商品名称
		lst_InventoryMaintenance.setValue(ITEMNAME, param.getItemName());
		// ボール入数
		lst_InventoryMaintenance.setValue(BUNDLEENTERINGQTY,
			WmsFormatter.getNumFormat(param.getBundleEnteringQty()));
		// 棚卸ピース数
		lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY,
			WmsFormatter.getNumFormat(param.getInventoryCheckPieceQty()));
		// 在庫ピース数
		lst_InventoryMaintenance.setValue(STOCKPIECEQTY,lst_InventoryMaintenance.getValue(STOCKPIECEQTY));
		
		// viewStateのクリア
		clearViewState();
	}

	/**
	 * ViewStateをクリアします。
	 *
	 */
	private void clearViewState()
	{
		this.getViewState().setInt(LINENO_KEY, -1);
		this.getViewState().setString(LOCATION_KEY,"");
		this.getViewState().setString(ITEMCODE_KEY,"");
		this.getViewState().setString(ITEMNAME_KEY,"");
		this.getViewState().setString(ENTERINGQTY_KEY,"");
		this.getViewState().setString(BUNDLEENTERINGQTY_KEY,"");
		this.getViewState().setString(INVENTORYCHECKCASEQTY_KEY,"");
		this.getViewState().setString(INVENTORYCHECKPIECEQTY_KEY,"");
		this.getViewState().setString(USEBYDATE_KEY,"");
		this.getViewState().setString(LASTUPDATE_KEY, "");
		this.getViewState().setString(JOBNO_KEY, "");
		this.getViewState().setString(STOCKID_KEY, "");
		
	}


	/**
	 * 入力エリアとためうちエリアで変更箇所があるか判断するメソッドです。<BR>
	 * <BR>
	 * 概要:入力エリアとためうちエリアを比較し、変更があればtrueを返します。<BR>
	 * 
	 * @param lineno 対象となる行No。
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean updateCheck(int lineno) throws Exception
	{
		// 修正行に移動
		lst_InventoryMaintenance.setCurrentRow(lineno);

		// 棚
		if (!txt_Location.getText()
			.equals(CollectionUtils.getString(2, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// 商品コード
		if (!txt_ItemCode.getText()
			.equals(CollectionUtils.getString(3, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// 商品名称
		if (!txt_ItemName.getText()
			.equals(CollectionUtils.getString(4, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// ケース入数
		if (!txt_CaseEntering.getText()
			.equals(CollectionUtils.getString(5, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// ボール入数
		if (!txt_BundleEntering.getText()
			.equals(CollectionUtils.getString(6, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// 棚卸ケース数
		if (!txt_InventoryCaseQty.getText()
			.equals(CollectionUtils.getString(7, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// 棚卸ピース数
		if (!txt_InventoryPieceQty.getText()
			.equals(CollectionUtils.getString(8, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}
		// 賞味期限
		if (!txt_UseByDate.getText()
			.equals(CollectionUtils.getString(9, lst_InventoryMaintenance.getValue(HIDDEN))))
		{
			return true;
		}

		return false;
	}

	/**
	 * 補完タイプによって、商品名称、ケース入数、ボール入数の読み取り専用か否かを切り替えます。
	 * 補完タイプは１画面目で、ViewStateに保持したものを判断に使用します。
	 *
	 */
	private void setItemReadOnly()
	{
		if (this.getViewState().getBoolean(MasterInventoryMaintenanceBusiness.IS_READ_ONLY_KEY))
		{
			txt_ItemName.setReadOnly(true);
			txt_CaseEntering.setReadOnly(true);
			txt_BundleEntering.setReadOnly(true);
		}

	}
	
	// Event handler methods -----------------------------------------
	/**
	 * 戻るボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:棚卸し(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		// 詳細情報設定画面->基本情報設定画面
		forward("/master/masterinventorymaintenance/MasterInventoryMaintenance.do");
	}

	/**
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/**
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearch_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// 処理中画面->結果画面
		redirect("/master/listbox/listmasteritem/ListMasterItem.do", param, "/progress.do");
	}

	/**
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：新規登録の場合、入力エリアの入力チェックをし、ためうちに表示します。<BR>
	 * 修正の場合、入力エリアの入力チェックをし、ためうちの修正対象データを更新します。<BR>
	 * <BR>
	 * <DIR>
	 *	1.カーソルを商品コードにセットします。<BR>
	 *  2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *  3.スケジュールを開始します。<BR>
	 * 	<DIR>
	 *		[パラメータ] *必須入力 <BR>
	 *		<DIR>
	 *			荷主コード* <BR>
	 *			荷主名称 <BR>
	 *			商品コード* <BR>
	 *			商品名称 <BR>
	 *			ケース入数 ※棚卸ケース数入力時* <BR>
	 *			棚卸ケース数 <BR>
	 *			棚卸ピース数 <BR>
	 *			棚* <BR>
	 *			賞味期限 <BR>
	 *			ためうち行No. <BR>
	 *		</DIR>
	 *	</DIR>
	 *	<BR>
	 *	スケジュールの結果がtrueの場合 <BR>
	 *		4.修正の場合は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *		  新規登録の場合は、入力エリアの情報でためうちに追加します。<BR>
	 *		5.修正の場合は、修正対象行の色を元に戻します。<BR>
	 *		6.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *		7.入力エリアの内容はクリアします。<BR>
	 *		8.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *	スケジュールの結果がfalseの場合 <BR>
	 *		9.スケジュールから取得したエラーメッセージを画面に出力します。<BR>
	 *	<BR>
	 *	リストセルの列番号一覧<BR>
	 *	<DIR>
	 *		3.棚卸作業有無名称 <BR>
	 *		4.棚 <BR>
	 *		5.商品コード <BR>
	 *		6.ケース入数 <BR>
	 *		7.棚卸ケース数 <BR>
	 *		8.在庫ケース数 <BR>
	 *		9.賞味期限 <BR>
	 *		10.商品名称 <BR>
	 *		11.ボール入数 <BR>
	 *		12.棚卸ピース数 <BR>
	 *		13.在庫ピース数 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		int lineno = this.getViewState().getInt(LINENO_KEY);
		
		// カーソルを商品コードにセットする
		if (!txt_ItemCode.getReadOnly())
		{
			setFocus(txt_ItemCode);
		}
		else
		{
			setFocus(txt_InventoryCaseQty);
		}
		
		// 必須入力チェック
		txt_ItemCode.validate();
		// 棚卸ケース数、ピース数両方が未入力の場合、
		// 入力を求めます。
		// 入力フィールドより、未入力かどうかを取得できないため
		// ValidateExceptionをcatchしてチェックを行います。
		try
		{
			txt_InventoryCaseQty.validate();
		}
		catch (ValidateException cve)
		{
			try
			{
				txt_InventoryPieceQty.validate();
			}
			catch (ValidateException pve)
			{
				// 6023323={0}または{1}を入力してください。
				// LBL-W0092=棚卸ケース数
				// LBL-W0094=棚卸ピース数
				message.setMsgResourceKey("6023323" + wDelim  + DispResources.getText("LBL-W0092") + wDelim + DispResources.getText("LBL-W0094"));
				return;
			}
		}
		txt_Location.validate();

		// パターンマッチング文字
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_BundleEntering.validate(false);
		txt_UseByDate.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);

			// スケジュールパラメータへセット
			// 入力エリア
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();
			
			MasterStorageSupportParameter mergeParam = null;
			// 新規入力の場合、補完処理を行う
			if (this.getViewState().getInt(LINENO_KEY) == -1
			|| CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
			{
				mergeParam = new MasterStorageSupportParameter();
				mergeParam.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				mergeParam.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
				mergeParam.setItemCode(txt_ItemCode.getText());
				mergeParam.setItemName(txt_ItemName.getText());
				mergeParam.setEnteringQty(txt_CaseEntering.getInt());
				mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
				mergeParam.setSearchMode(MasterStorageSupportParameter.SEARCH_MODE_MERGE);
				mergeParam = (MasterStorageSupportParameter) schedule.query(conn, mergeParam)[0];
				// 入力エリアに補完結果を表示
				txt_ItemName.setText(mergeParam.getItemName());
				txt_CaseEntering.setInt(mergeParam.getEnteringQty());
				txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
				// スケジュールパラメータへセット
				// 荷主と出荷先の最終更新日時をチェックするためセットする
				param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(
						this.getViewState().getString(MasterInventoryMaintenance2Business.CONSIG_DATE_KEY)));
				param.setITF(mergeParam.getITF());
				param.setBundleITF(mergeParam.getBundleITF());
			}
			// 行No.
			param.setRowNo(lineno);
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主名称
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// ボール入数
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			// 棚卸ケース数
			param.setInventoryCheckCaseQty(txt_InventoryCaseQty.getInt());
			// 棚卸ピース数
			param.setInventoryCheckPieceQty(txt_InventoryPieceQty.getInt());
			
			if(lineno == -1)
			{
				// 在庫ケース数
				param.setTotalStockCaseQty(0);
				// 在庫ピース数
				param.setTotalStockPieceQty(0);
				// 総在庫数
				param.setTotalStockQty(0);
			}
			else
			{
				lst_InventoryMaintenance.setCurrentRow(this.getViewState().getInt(LINENO_KEY));
				// 在庫ケース数
				param.setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
				// 在庫ピース数
				param.setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));
				// 総在庫数
				param.setTotalStockQty((WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY))
						* txt_CaseEntering.getInt() 
						+ WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY))));
			}

			// 棚
			param.setLocation(txt_Location.getText());
			// 賞味期限
			param.setUseByDate(txt_UseByDate.getText());
			// 最終更新日時
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDATE_KEY)));
			// 作業№
			param.setJobNo(this.getViewState().getString(JOBNO_KEY));
			// 在庫ID
			param.setStockID(this.getViewState().getString(STOCKID_KEY));
			
			// スケジュールパラメータへセット
			// ためうちエリア
			StorageSupportParameter[] listparam = null;
			// ためうちにデータがなければnullをセット
			if (lst_InventoryMaintenance.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParamater(this.getViewState().getInt(LINENO_KEY));
			}

			if (schedule.check(conn, param, listparam))
			{
				if (mergeParam != null)
				{
					param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				}
				// 結果がtrueであればためうちエリアにデータをセット
				if (this.getViewState().getInt(LINENO_KEY) == -1)
				{
					// 新規入力であれば、ためうちに追加
					lst_InventoryMaintenance.addRow();
					lst_InventoryMaintenance.setCurrentRow(lst_InventoryMaintenance.getMaxRows() - 1);
					setList(param, lineno);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_InventoryMaintenance.setCurrentRow(this.getViewState().getInt(LINENO_KEY));
					setList(param, lineno);
					// 選択行の色を元に戻す
					lst_InventoryMaintenance.resetHighlight();
				}
				
				// 棚卸データ更新ボタンを有効にする
				if (lst_InventoryMaintenance.getMaxRows() > 1)
				{
					// ボタン押下を有効にする
					// 棚卸データ更新ボタン
					btn_InventoryData.setEnabled(true);
				}
				// 状態をデフォルトに戻す
				this.getViewState().setInt(LINENO_KEY, -1);

				// 入力エリアの項目を全クリア
				txt_ItemCode.setText("");
				txt_ItemName.setText("");
				txt_CaseEntering.setText("");
				txt_BundleEntering.setText("");
				txt_InventoryCaseQty.setText("");
				txt_InventoryPieceQty.setText("");
				txt_Location.setText("");
				txt_UseByDate.setText("");
				
				btn_PSearch.setEnabled(true);
				txt_ItemCode.setReadOnly(false);
				txt_ItemName.setReadOnly(false);
				txt_CaseEntering.setReadOnly(false);
				txt_BundleEntering.setReadOnly(false);
				txt_Location.setReadOnly(false);
				txt_UseByDate.setReadOnly(false);
				
				setItemReadOnly();
				
				setFocus(txt_ItemCode);
			}

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
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを商品コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		
		// 項目を使用可にします。
		// 商品コードが入力不可の場合は既存データ修正時のため
		// 棚卸し入力エリアのみクリアします。
		if (txt_ItemCode.getReadOnly())
		{
			setFocus(txt_InventoryCaseQty);
			txt_InventoryCaseQty.setText("");
			txt_InventoryPieceQty.setText("");
		}
		else
		{
			setFocus(txt_ItemCode);
			txt_ItemCode.setText("");
			txt_ItemName.setText("");
			txt_CaseEntering.setText("");
			txt_BundleEntering.setText("");
			txt_InventoryCaseQty.setText("");
			txt_InventoryPieceQty.setText("");
			txt_Location.setText("");
			txt_UseByDate.setText("");
		}
	}

	/**
	 * 棚卸データ更新ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、棚卸データ更新を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	1.カーソルを商品コードにセットします。<BR>
	 *	2.棚卸データ更新を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *	<DIR>
	 *		"更新しますか？"<BR>
	 *		[確認ダイアログ キャンセル]<BR>
	 *		<DIR>
	 *			何も行いません。
	 *		</DIR>
	 *		[確認ダイアログ OK]<BR>
	 *		<DIR>
	 *			1.スケジュールを開始します。<BR>
	 *			<DIR>
	 *				[パラメータ]<BR>
	 *				<DIR>
	 *					荷主コード <BR>
	 *					荷主名称 <BR>
	 *					ためうち.棚卸作業有無名称 <BR>
	 *					ためうち.棚 <BR>
	 *					ためうち.商品コード <BR>
	 *					ためうち.商品名称 <BR>
	 *					ためうち.ケース入数 <BR>
	 *					ためうち.ボール入数 <BR>
	 *					ためうち.棚卸ケース数 <BR>
	 *					ためうち.棚卸ピース数 <BR>
	 *					ためうち.在庫ケース数 <BR>
	 *					ためうち.在庫ピース数 <BR>
	 *					ためうち.賞味期限 <BR>
	 *					棚卸作業有無 <BR>
	 *				</DIR>
	 *				<BR>
	 *				[返却データ]<BR>
	 *				<DIR>
	 *					棚卸作業有無名称 <BR>
	 *					棚 <BR>
	 *					商品コード <BR>
	 *					商品名称 <BR>
	 *					ケース入数 <BR>
	 *					ボール入数 <BR>
	 *					棚卸ケース数 <BR>
	 *					棚卸ピース数 <BR>
	 *					在庫ケース数 <BR>
	 *					在庫ピース数 <BR>
	 *					賞味期限 <BR>
	 *					棚卸作業有無 <BR>
	 *				</DIR>
	 *			</DIR>
	 *			<BR>
	 *			2.スケジュールの結果がtrueの時は、基本情報設定を元にためうちのデータを再表示します。<BR>
	 *			3.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。<BR>
	 *			4.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *			5.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。<BR>
	 *			6.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *		</DIR>
	 *	</DIR>
	 * <BR>
	 *	リストセルの列番号一覧<BR>
	 *	<DIR>
	 *		3.棚卸作業有無名称 <BR>
	 *		4.棚 <BR>
	 *		5.商品コード <BR>
	 *		6.ケース入数 <BR>
	 *		7.棚卸ケース数 <BR>
	 *		8.在庫ケース数 <BR>
	 *		9.賞味期限 <BR>
	 *		10.商品名称 <BR>
	 *		11.ボール入数 <BR>
	 *		12.棚卸ピース数 <BR>
	 *		13.在庫ピース数 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_InventoryData_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを商品コードにセットする
			setFocus(txt_ItemCode);

			// スケジュールパラメータへセット
			MasterStorageSupportParameter[] param = null;
			// ためうちエリアの全データをセット
			param = setListParam();
			
			if (param == null || (param != null && param.length == 0))
			{
				// 6023154=更新対象データがありません。
				message.setMsgResourceKey("6023154");
				return;
			}

			// ボタン種別
			param[0].setButtonType(StorageSupportParameter.BUTTON_MODIFYSUBMIT);
			// 検索モード
			param[0].setSearchMode(MasterStorageSupportParameter.SEARCH_MODE_LIST);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);
			
			MasterStorageSupportParameter[] viewParam =
				(MasterStorageSupportParameter[]) schedule.startSCHgetParams(conn, param);

			// エラーなどが発生した場合は、メッセージを受け取って終了します
			if (viewParam == null)
			{
				// コネクションをロールバックします
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// 正常に完了した場合はコミット
			conn.commit();

			// 入力エリアクリア
			txt_ItemCode.setText("");
			txt_ItemName.setText("");
			txt_CaseEntering.setText("");
			txt_BundleEntering.setText("");
			txt_InventoryCaseQty.setText("");
			txt_InventoryPieceQty.setText("");
			txt_Location.setText("");
			txt_UseByDate.setText("");
							
			btn_PSearch.setEnabled(true);
			txt_ItemCode.setReadOnly(false);
			txt_ItemName.setReadOnly(false);
			txt_CaseEntering.setReadOnly(false);
			txt_BundleEntering.setReadOnly(false);
			txt_Location.setReadOnly(false);
			txt_UseByDate.setReadOnly(false);

			setItemReadOnly();
			
			// ためうちエリアクリア
			lst_InventoryMaintenance.clearRow();

			// ためうちエリア
			setList(viewParam);
			
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

			// ためうち情報が存在しない場合、棚卸データ更新ボタンは無効にする
			if (lst_InventoryMaintenance.getMaxRows() == 1)
			{
				// ボタン押下を無効にする
				// 棚卸データ更新ボタン
				btn_InventoryData.setEnabled(false);
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
	 * 削除、修正ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 削除ボタン概要:ためうちの該当データを削除します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報の削除を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "削除しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ] *必須入力<BR>
	 *   				<DIR>
	 * 						ためうち行No. <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。<BR>
	 *				3.スケジュールの結果がtrueの時は、ためうちの該当データを削除します。<BR>
	 *    			4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.ためうち情報が存在しない場合、棚卸データ更新ボタンを無効にします。<BR>
	 *    			6.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *				7.カーソルを商品コードにセットします。<BR>
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
	 *    3.ViewStateのためうち行No.に現在行を設定します。<BR>
	 *    4.カーソルを商品コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_InventoryMaintenance_Click(ActionEvent e) throws Exception
	{
		if (!txt_ItemCode.getReadOnly())
		{
			setFocus(txt_ItemCode);
		}
		else
		{
			setFocus(txt_InventoryCaseQty);
		}
		
		// 削除ボタンクリック時
		if (lst_InventoryMaintenance.getActiveCol() == 1)
		{
			Connection conn = null;
			try
			{
				// 現在の行をセット
				lst_InventoryMaintenance.setCurrentRow(lst_InventoryMaintenance.getActiveRow());
				// スケジュールパラメータへセット
				StorageSupportParameter[] param = new StorageSupportParameter[1];

				param[0] = new StorageSupportParameter();

				// 登録区分が未登録の場合のみBusinessで削除可能かどうかを判断する
				if (CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
				{
					// 削除可能かどうかのチェック
					if (StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY)) 
						&& StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY)))
					{
						//6023363=棚卸データがないため削除できません。
						message.setMsgResourceKey("6023363");
						return ;
					}
					else if(param[0].getTotalStockCaseQty() != 0 || param[0].getTotalStockPieceQty() != 0)
					{
						//6023363=棚卸データがないため削除できません。
						message.setMsgResourceKey("6023363");
						return ;						
					}
				}
				
				// 入力エリア情報
				// 作業者コード
				param[0].setWorkerCode(this.getViewState().getString(MasterInventoryMaintenanceBusiness.WORKERCODE_KEY));
				// パスワード
				param[0].setPassword(this.getViewState().getString(MasterInventoryMaintenanceBusiness.PASSWORD_KEY));
				// 端末Noをセット
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param[0].setTerminalNumber(userHandler.getTerminalNo());
				// 棚
				param[0].setLocation(lst_InventoryMaintenance.getValue(LOCATION));
				// 荷主コード
				param[0].setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
				// 商品コード
				param[0].setItemCode(lst_InventoryMaintenance.getValue(ITEMCODE));
				
				if(lst_InventoryMaintenance.getValue(STOCKCASEQTY) == null 
				|| lst_InventoryMaintenance.getValue(STOCKPIECEQTY) == null)
				{
					// 在庫ケース数
					param[0].setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
					// 在庫ピース数
					param[0].setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));

				}
				else
				{
					// 在庫ケース数
					param[0].setTotalStockCaseQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKCASEQTY)));
					// 在庫ピース数
					param[0].setTotalStockPieceQty(WmsFormatter.getLong(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)));

				}
				// 賞味期限
				param[0].setUseByDate(lst_InventoryMaintenance.getValue(USEBYDATE));
				// 最終更新日時
				param[0].setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(10, lst_InventoryMaintenance.getValue(HIDDEN))));
				// 作業№
				param[0].setJobNo(CollectionUtils.getString(11, lst_InventoryMaintenance.getValue(HIDDEN)));
				// 在庫ID
				param[0].setStockID(CollectionUtils.getString(12, lst_InventoryMaintenance.getValue(HIDDEN)));
				// ボタン種別
				param[0].setButtonType(StorageSupportParameter.BUTTON_DELETESUBMIT);
				
				// 登録区分が未登録の場合のみBusinessで削除可能かどうかを判断する
				if (CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_NOTHING))
				{
					// 削除可能かどうかのチェック
					if (StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY)) 
						&& StringUtil.isBlank(lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY)))
					{
						//6023363=棚卸データがないため削除できません。
						message.setMsgResourceKey("6023363");
						return ;
					}
					else if(param[0].getTotalStockCaseQty() != 0 || param[0].getTotalStockPieceQty() != 0)
					{
						//6023363=棚卸データがないため削除できません。
						message.setMsgResourceKey("6023363");
						return ;						
					}
					else if(StringUtil.isBlank(lst_InventoryMaintenance.getValue(STOCKCASEQTY)) 
					&& StringUtil.isBlank(lst_InventoryMaintenance.getValue(STOCKPIECEQTY)))
					{
						btn_PSearch.setEnabled(true);
						// 削除されたためうち行を削除
						lst_InventoryMaintenance.removeRow(lst_InventoryMaintenance.getActiveRow());
						clearInputArea();
						// ためうち情報が存在しない場合、棚卸データ更新ボタンは無効にする
						if (lst_InventoryMaintenance.getMaxRows() == 1)
						{
							// ボタン押下を無効にする
							// 棚卸データ更新ボタン
							btn_InventoryData.setEnabled(false);
						}
						// viewStateのクリア
						clearViewState();
						//6001005=削除しました。
						message.setMsgResourceKey("6001005");	

						return ;
					}
					else
					{
						//隠し項目セット準備
					 	List list = new Vector();
					 	// 棚卸作業有無
					 	list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);
					 	// 修正フラグ
					 	list.add(StorageSupportParameter.UPDATEFLAG_NO);
					 	// 棚
					 	list.add(lst_InventoryMaintenance.getValue(LOCATION));
					 	// 商品コード
					 	list.add(lst_InventoryMaintenance.getValue(ITEMCODE));
					 	// 商品名称
					 	list.add(lst_InventoryMaintenance.getValue(ITEMNAME));
						// ケース入数
						list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(ENTERINGQTY))));
						// ボール入数
						list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY))));
					 	// 棚卸ケース数
					 	list.add("");
					 	// 棚卸ピース数
					 	list.add("");
					 	// 賞味期限
					 	list.add(lst_InventoryMaintenance.getValue(USEBYDATE));
						// 最終更新日時
						list.add(WmsFormatter.getTimeStampString(param[0].getLastUpdateDate()));
						// 作業№
						list.add(param[0].getJobNo());
						// 在庫ID
						list.add(param[0].getStockID());
					 	// 隠し項目にセットする
					 	lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));
						
						// 棚卸ケース数
						lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY, "");
						// 棚卸ピース数
						lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY, "");
						// 在庫ケース数
						lst_InventoryMaintenance.setValue(STOCKCASEQTY, WmsFormatter.getNumFormat(param[0].getTotalStockCaseQty()));
						// 在庫ピース数
						lst_InventoryMaintenance.setValue(STOCKPIECEQTY, WmsFormatter.getNumFormat(param[0].getTotalStockPieceQty()));

						// viewStateのクリア
						clearViewState();
						//6001005=削除しました。
						message.setMsgResourceKey("6001005");
						return ;
					}
				}
				else
				{
					
					conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
					WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);
	
					if (schedule.startSCH(conn, param))
					{
						// 処理が正常に完了すればコミット
						conn.commit();
	
						// 入力エリアクリア
						clearInputArea();
	
						// 在庫テーブルにデータがあるか判断する
						String invcase = lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY);
						String invpiece = lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY);
						String stockcase = lst_InventoryMaintenance.getValue(STOCKCASEQTY);
						String stockpiece = lst_InventoryMaintenance.getValue(STOCKPIECEQTY);
	
						// 在庫が存在する場合、棚卸ケース数、棚卸ピース数以外変更不可
						if ((!StringUtil.isBlank(invcase) && !StringUtil.isBlank(stockcase))
							|| (StringUtil.isBlank(invcase) && !StringUtil.isBlank(stockcase))
						    || (!StringUtil.isBlank(invpiece) && !StringUtil.isBlank(stockpiece))
						    || (StringUtil.isBlank(invpiece) && !StringUtil.isBlank(stockpiece)))
						{
							// 隠し項目セット準備
							List list = new Vector();
							// 棚卸作業有無
							list.add(StorageSupportParameter.INVENTORY_KIND_NOTHING);
							// 修正フラグ
							list.add(StorageSupportParameter.UPDATEFLAG_NO);
							// 棚
							list.add(lst_InventoryMaintenance.getValue(LOCATION));
							// 商品コード
							list.add(lst_InventoryMaintenance.getValue(ITEMCODE));
							// 商品名称
							list.add(lst_InventoryMaintenance.getValue(ITEMNAME));
							// ケース入数
							list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(ENTERINGQTY))));
							// ボール入数
							list.add(WmsFormatter.getNumFormat(WmsFormatter.getInt(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY))));
							// 棚卸ケース数
							list.add("");
							// 棚卸ピース数
							list.add("");			
							// 賞味期限
							list.add(lst_InventoryMaintenance.getValue(USEBYDATE));
							// 最終更新日時
							list.add(WmsFormatter.getTimeStampString(param[0].getLastUpdateDate()));
							// 作業№
							list.add(param[0].getJobNo());		
							// 在庫ID
							list.add(param[0].getStockID());						
							// 隠し項目にセットする
							lst_InventoryMaintenance.setValue(HIDDEN, CollectionUtils.getConnectedString(list));
							// 棚卸ケース数
							lst_InventoryMaintenance.setValue(INVENTORYCHECKCASEQTY, "");
							// 棚卸ピース数
							lst_InventoryMaintenance.setValue(INVENTORYCHECKPIECEQTY, "");
							// 登録区分
							lst_InventoryMaintenance.setValue(INVENTORYKINDNAME,DisplayText.getText("LBL-W0394"));
						}
						else
						{
							// 削除されたためうち行を削除
							lst_InventoryMaintenance.removeRow(lst_InventoryMaintenance.getActiveRow());
							clearViewState();
						}
	
						// ためうち情報が存在しない場合、棚卸データ更新ボタンは無効にする
						if (lst_InventoryMaintenance.getMaxRows() == 1)
						{
							// ボタン押下を無効にする
							// 棚卸データ更新ボタン
							btn_InventoryData.setEnabled(false);
						}
						clearViewState();
						// メッセージをセット
						message.setMsgResourceKey(schedule.getMessage());
					}
					else
					{
						// 異常終了時はロールバック
						conn.rollback();
						// メッセージをセット
						message.setMsgResourceKey(schedule.getMessage());
					}
				}
				// カーソルを商品コードにセットする
				setFocus(txt_ItemCode);
				// ためうちエリア入力可
				btn_PSearch.setEnabled(true);
				txt_ItemCode.setReadOnly(false);
				txt_ItemName.setReadOnly(false);
				txt_CaseEntering.setReadOnly(false);
				txt_BundleEntering.setReadOnly(false);
				txt_Location.setReadOnly(false);
				txt_UseByDate.setReadOnly(false);
				
				setItemReadOnly();
				// 選択行の色を元に戻す
				lst_InventoryMaintenance.setHighlight(-1);
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
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_InventoryMaintenance.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_InventoryMaintenance.setCurrentRow(lst_InventoryMaintenance.getActiveRow());

			// 在庫テーブルにデータがあるか判断する
			String invcase = lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY);
			String stockcase = lst_InventoryMaintenance.getValue(STOCKCASEQTY);
			String stockpiece = lst_InventoryMaintenance.getValue(STOCKPIECEQTY);
			// ケース入数
			String enteringQty = lst_InventoryMaintenance.getValue(ENTERINGQTY);
			
			long stkQty = WmsFormatter.getLong(stockcase) * WmsFormatter.getLong(enteringQty) + WmsFormatter.getLong(stockpiece);

			if(stkQty > WmsParam.MAX_STOCK_QTY)
			{
				// 6023348=在庫数が{0}をこえるため設定できません。
				message.setMsgResourceKey("6023348"	+ wDelim + WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY));
				return;
			}
			
			txt_ItemCode.setReadOnly(false);
			txt_ItemName.setReadOnly(false);
			txt_CaseEntering.setReadOnly(false);
			txt_BundleEntering.setReadOnly(false);
			txt_Location.setReadOnly(false);
			txt_UseByDate.setReadOnly(false);
			btn_PSearch.setEnabled(true);
			setItemReadOnly();

			// 在庫が存在する場合、棚卸ケース数、棚卸ピース数以外変更不可
			if (CollectionUtils.getString(0, lst_InventoryMaintenance.getValue(HIDDEN)).equals(StorageSupportParameter.INVENTORY_KIND_FIND) 
				||(WmsFormatter.getInt(stockcase) != 0 || WmsFormatter.getInt(stockpiece) != 0))
			{
				txt_ItemCode.setReadOnly(true);
				txt_ItemName.setReadOnly(true);
				txt_CaseEntering.setReadOnly(true);
				txt_BundleEntering.setReadOnly(true);
				txt_Location.setReadOnly(true);
				txt_UseByDate.setReadOnly(true);
				btn_PSearch.setEnabled(false);
				
				if (!StringUtil.isBlank(stockcase))
				{
					txt_CaseEntering.setReadOnly(true);
				}
				
				if (!StringUtil.isBlank(stockpiece))
				{
					txt_BundleEntering.setReadOnly(true);
				}
				
				setItemReadOnly();
				
				// カーソルを棚卸ケース数にセットする
				setFocus(txt_InventoryCaseQty);
			}
			else if (!StringUtil.isBlank(invcase) && StringUtil.isBlank(stockcase))
			{
				// カーソルを商品コードにセットする
				setFocus(txt_ItemCode);
			}
			// 商品コード
			txt_ItemCode.setText(lst_InventoryMaintenance.getValue(ITEMCODE));
			// 商品名称
			txt_ItemName.setText(lst_InventoryMaintenance.getValue(ITEMNAME));
			// ケース入数
			txt_CaseEntering.setText(lst_InventoryMaintenance.getValue(ENTERINGQTY));
			// ボール入数
			txt_BundleEntering.setText(lst_InventoryMaintenance.getValue(BUNDLEENTERINGQTY));
			// 棚卸ケース数、棚卸ピース数に値が無い場合、在庫ケース数、在庫ピース数の値をセットする
			if (StringUtil.isBlank(invcase) && !StringUtil.isBlank(stockcase))
			{
				// 棚卸ケース数
				txt_InventoryCaseQty.setText(lst_InventoryMaintenance.getValue(STOCKCASEQTY));
				// 棚卸ピース数
				txt_InventoryPieceQty.setText(lst_InventoryMaintenance.getValue(STOCKPIECEQTY));
			}
			else
			{
				// 棚卸ケース数
				txt_InventoryCaseQty.setText(
					lst_InventoryMaintenance.getValue(INVENTORYCHECKCASEQTY));
				// 棚卸ピース数
				txt_InventoryPieceQty.setText(
					lst_InventoryMaintenance.getValue(INVENTORYCHECKPIECEQTY));
			}
			// 棚
			txt_Location.setText(lst_InventoryMaintenance.getValue(LOCATION));
			// 賞味期限
			txt_UseByDate.setText(lst_InventoryMaintenance.getValue(USEBYDATE));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt(LINENO_KEY, lst_InventoryMaintenance.getActiveRow());
			// 棚(隠し項目)
			this.getViewState().setString(LOCATION_KEY,
				CollectionUtils.getString(2, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 商品コード(隠し項目)
			this.getViewState().setString(ITEMCODE_KEY,
				CollectionUtils.getString(3, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 商品名称(隠し項目)
			this.getViewState().setString(ITEMNAME_KEY,
				CollectionUtils.getString(4, lst_InventoryMaintenance.getValue(HIDDEN)));
			// ケース入数(隠し項目)
			this.getViewState().setString(ENTERINGQTY_KEY,
				CollectionUtils.getString(5, lst_InventoryMaintenance.getValue(HIDDEN)));
			// ボール入数(隠し項目)
			this.getViewState().setString(BUNDLEENTERINGQTY_KEY,
				CollectionUtils.getString(6, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 棚卸ケース数(隠し項目)
			this.getViewState().setString(INVENTORYCHECKCASEQTY_KEY,
				CollectionUtils.getString(7, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 棚卸ピース数(隠し項目)
			this.getViewState().setString(INVENTORYCHECKPIECEQTY_KEY,
				CollectionUtils.getString(8, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 賞味期限(隠し項目)
			this.getViewState().setString(USEBYDATE_KEY,
				CollectionUtils.getString(9, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 最終更新日時(隠し項目)
			this.getViewState().setString(LASTUPDATE_KEY,
				CollectionUtils.getString(10, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 作業№(隠し項目)
			this.getViewState().setString(JOBNO_KEY,
				CollectionUtils.getString(11, lst_InventoryMaintenance.getValue(HIDDEN)));
			// 在庫ID(隠し項目)
			this.getViewState().setString(STOCKID_KEY,
				CollectionUtils.getString(12, lst_InventoryMaintenance.getValue(HIDDEN)));
			//修正行を黄色に変更する
			lst_InventoryMaintenance.setHighlight(lst_InventoryMaintenance.getActiveRow());
		}
	}

}
//end of class

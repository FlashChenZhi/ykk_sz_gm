// $Id: MasterInventoryMaintenanceBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterinventorymaintenance;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.master.schedule.MasterInventoryMaintenanceSCH;
import jp.co.daifuku.wms.storage.display.web.listbox.listinventorylocation.ListInventoryLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
import jp.co.daifuku.wms.master.schedule.MasterStorageSupportParameter;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * 棚卸し（基本情報設定）クラスです。<BR>
 * 画面から入力した内容をViewStateにセットし、そのパラメータを基に棚卸し(設定)画面に遷移します。<BR>
 * 入力チェックで正常な場合はtrueを、条件エラーの場合はfalseを受け取ります。<BR>
 * 入力チェックの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.次へボタン押下処理（<CODE>btn_Next_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *  スケジュールから結果を受け取り、正常の場合はtrueを、エラーの場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *  結果がtrueの時は入力エリアの入力項目をViewStateにセットし、そのパラメータを基に棚卸し(設定)画面に遷移します。<BR>
 *  <BR>
 * 	[ViewStateパラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主コード* <BR>
 * 		開始棚 <BR>
 *		終了棚 <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/13</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterInventoryMaintenanceBusiness extends MasterInventoryMaintenance implements WMSConstants
{
	// Class fields --------------------------------------------------
	/**
	 * タイトルの受け渡しに使用するViewState用キーです
	 */
	public static final String TITLE_KEY = "TITLE_KEY";

	/**
	 * 作業者コードの受け渡しに使用するViewState用キーです
	 */
	public static final String WORKERCODE_KEY = "WORKERCODE_KEY";

	/**
	 * パスワードの受け渡しに使用するViewState用キーです
	 */
	public static final String PASSWORD_KEY = "PASSWORD_KEY";

	/**
	 * 荷主コードの受け渡しに使用するViewState用キーです
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	/**
	 * 開始棚の受け渡しに使用するViewState用キーです
	 */
	public static final String STARTLOCATION_KEY = "STARTLOCATION_KEY";

	/**
	 * 終了棚の受け渡しに使用するViewState用キーです
	 */
	public static final String ENDLOCATION_KEY = "ENDLOCATION_KEY";
	
	/**
	 * 読取専用フラグを保持するViewState用キーです
	 */
	public static final String IS_READ_ONLY_KEY = "IS_READ_ONLY";
	
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
	 *    1.カーソルを作業者コードに初期セットします。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 *       作業者コード[なし]<BR>
	 *       パスワード[なし]<BR>
	 *       荷主コード[棚卸し情報の荷主が1件の場合、荷主コードを初期表示します。]<BR>
	 * 	 	 開始棚[なし]<BR>
	 * 	 	 終了棚[なし]<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		if (!StringUtil.isBlank(this.viewState.getString(TITLE_KEY)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE_KEY));
		}

		// 基本情報設定のタブを前出しする
		tab_Inventory.setSelectedIndex(1);

		// 初期表示をする
		setFirstDisp();

		// 戻るボタン押下時、ViewStateに値がセットされていれば、その値をセットする
		String workercode = this.getViewState().getString(WORKERCODE_KEY);
		String password = this.getViewState().getString(PASSWORD_KEY);
		String consignorcode = this.getViewState().getString(CONSIGNORCODE_KEY);
		String startlocation = this.getViewState().getString(STARTLOCATION_KEY);
		String endlocation = this.getViewState().getString(ENDLOCATION_KEY);

		// 作業者コード
		if (!StringUtil.isBlank(workercode))
		{
			txt_WorkerCode.setText(workercode);
		}

		// パスワード
		if (!StringUtil.isBlank(password))
		{
			txt_Password.setText(password);
		}

		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
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
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。<BR>
	 * <CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始棚
		String startlocation = param.getParameter(ListInventoryLocationBusiness.STARTLOCATION_KEY);
		// 終了棚
		String endlocation = param.getParameter(ListInventoryLocationBusiness.ENDLOCATION_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
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

		// フォーカスを作業者コードにセットする
		setFocus(txt_WorkerCode);
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
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}
	// Private methods -----------------------------------------------
	/**
	 * 初期表示時、スケジュールから荷主コードを取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。 <BR>
	 * 
	 * @return 荷主コード <BR>
	 *         該当データが1件のときは荷主コードの文字列、 <BR>
	 *         該当データが0件または複数件あれば空文字を返します。 <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			// コネクションのクローズを行う
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
	}
	
	/**
	 * 画面初期表示、クリア処理の場合にこのメソッドが呼ばれます。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			// 荷主コード
			txt_ConsignorCode.setText(getConsignorCode());
			// 開始棚
			txt_StartLocation.setText("");
			// 終了棚
			txt_EndLocation.setText("");
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);

			MasterStorageSupportParameter param = (MasterStorageSupportParameter) schedule.initFind(conn, null);
			
			if (param != null)
			{
				if (param.getMergeType() == MasterStorageSupportParameter.MERGE_TYPE_OVERWRITE)
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
	// Event handler methods -----------------------------------------

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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
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
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 処理中画面->結果画面
		redirect(
			"/master/listbox/listmasterconsignor/ListMasterConsignor.do",
			param,
			"/progress.do");
	}

	/**
	 * 開始棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *		荷主コード <BR>
	 * 		開始棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStLoc_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 開始棚
		param.setParameter(
			ListInventoryLocationBusiness.STARTLOCATION_KEY,
			txt_StartLocation.getText());
		// 範囲フラグ
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_START);
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);

		// 処理中画面->結果画面
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	/**
	 * 終了棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *		荷主コード <BR>
	 * 		終了棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEndLoc_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 終了棚
		param.setParameter(
			ListInventoryLocationBusiness.ENDLOCATION_KEY,
			txt_EndLocation.getText());
		// 範囲フラグ
		param.setParameter(
			ListInventoryLocationBusiness.RANGELOCATION_KEY,
			StorageSupportParameter.RANGE_END);
		// 検索フラグ
		param.setParameter(
			ListInventoryLocationBusiness.SEARCHLOCATION_KEY,
			StorageSupportParameter.SEARCH_INVENTORY_AND_STOCK);
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStorageConsignorBusiness.AREA_TYPE_KEY, StorageSupportParameter.AREA_TYPE_FLAG_NOASRS);

		// 処理中画面->結果画面
		redirect(
			"/storage/listbox/listinventorylocation/ListInventoryLocation.do",
			param,
			"/progress.do");
	}

	/**
	 * 次へボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、棚卸し(設定)画面に遷移します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *    2.スケジュールを開始します。<BR>
	 * 	  <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			作業者コード* <BR>
	 * 			パスワード* <BR>
	 *      </DIR>
	 *    </DIR>
	 *    3.スケジュールの結果がtrueであれば、入力項目をViewStateに格納し、棚卸し（設定）画面に遷移します。<BR>
	 * 	  <DIR>
	 *   	[ViewStateパラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			作業者コード* <BR>
	 * 			パスワード* <BR>
	 * 			荷主コード* <BR>
	 * 			開始棚 <BR>
	 * 			終了棚 <BR>
	 *      </DIR>
	 *    </DIR>
	 *    falseの時はスケジュールから取得したメッセージを画面に出力します。<BR>
	 *    4.入力エリアの内容は保持します。<BR>
	 * <BR>
	 * </DIR>
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
		txt_ConsignorCode.validate();
		// パターンマッチング文字
		txt_StartLocation.validate(false);
		txt_EndLocation.validate(false);

		// 開始棚は終了棚より以下であること
		if (!StringUtil.isBlank(txt_StartLocation.getText())
			&& !StringUtil.isBlank(txt_EndLocation.getText()))
		{
			if (txt_StartLocation.getText().compareTo(txt_EndLocation.getText()) > 0)
			{
				// 6023124=開始棚Noは、終了棚Noより小さい値にしてください。
				message.setMsgResourceKey("6023124");
				return;
			}
		}
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			// スケジュールパラメータへセット
			StorageSupportParameter param = new StorageSupportParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());

			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 開始棚
			param.setFromLocation(txt_StartLocation.getText());
			// 終了棚
			param.setToLocation(txt_EndLocation.getText());

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterInventoryMaintenanceSCH(conn);

			if (schedule.nextCheck(conn, param))
			{
				// Checkの結果がtrueの場合、ViewStateに値をセットして、次画面に遷移する
				// タイトル
				this.getViewState().setString(TITLE_KEY, lbl_SettingName.getResourceKey());
				// 作業者コード
				this.getViewState().setString(WORKERCODE_KEY, txt_WorkerCode.getText());
				// パスワード
				this.getViewState().setString(PASSWORD_KEY, txt_Password.getText());
				// 荷主コード
				this.getViewState().setString(CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				// 開始棚
				this.getViewState().setString(STARTLOCATION_KEY, txt_StartLocation.getText());
				// 終了棚
				this.getViewState().setString(ENDLOCATION_KEY, txt_EndLocation.getText());
				// 基本情報設定画面->詳細情報登録画面
				forward("/master/masterinventorymaintenance/MasterInventoryMaintenance2.do");
			}
			else
			{
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			// コネクションロールバック
			if (conn != null)
			{

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
	 *    2.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// クリア処理を行う
		setFirstDisp();
	}

}
//end of class

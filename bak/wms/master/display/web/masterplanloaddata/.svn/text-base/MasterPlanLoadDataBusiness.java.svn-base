// $Id: MasterPlanLoadDataBusiness.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterplanloaddata;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.master.schedule.MasterPlanLoadDataSCH;
/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * データ取り込み画面クラスです。<BR>
 * 入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが作業予定データ取り込みを行います。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで <BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 開始ボタン押下処理（<CODE>btn_Start_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力エリア情報でデータ取込を行います。 <BR>
 * <BR>
 *   [パラメータ] *必須入力<BR>
 * <BR>
 * <DIR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   予定日* <BR>
 * </DIR>
 * <BR>
 *   [パラメータ]+1つ以上必須入力 <BR>
 * <BR>
 * <DIR>
 *   各導入パッケージのチェック有り無し+<BR>
 * </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterPlanLoadDataBusiness extends MasterPlanLoadData implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------


	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * <DIR>
	 *  概要:ダイアログを表示します。<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
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
		// MSG-0019=開始しますか？
		btn_Start.setBeforeConfirm("MSG-W0019");
	}

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * カーソルを作業者コードにセットします。 <BR>
	 * <DIR>
	 *   項目[初期値] <BR>
	 *   <DIR>
	 *     作業者コード[なし] <BR>
	 *     パスワード[なし] <BR>
	 *     予定日[検索値] <BR>
	 *     入荷予定データ[チェックなし] <BR>
	 *     入庫予定データ[チェックなし] <BR>
	 *     入庫棚の自動決定を行う[チェックなし]<BR>
	 *     出庫予定データ[チェックなし] <BR>
	 *     仕分予定データ[チェックなし] <BR>
	 *     出荷予定データ[チェックなし] <BR>
	 *   </DIR>
	 *   <BR>
	 *   チェックボックスの項目はパッケージが入っている項目のみ表示します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new MasterPlanLoadDataSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			// 作業者コードの初期値をセットします
			txt_WorkerCode.setText("");
			// パスワードの初期値をセットします
			txt_Password.setText("");

			// 予定日の初期値をセットします
			if (param != null)
			{
				txt_PlanDate.setDate(WmsFormatter.toDate(param.getPlanDate()));
			}

			// パッケージが入っている項目以外を読み取り専用にします
			// 入荷予定データ
			if (!param.getSelectLoadInstockData())
			{
				chk_InstockPlanData.setEnabled(false);
			}
			// 入庫予定データ
			if (!param.getSelectLoadStorageData())
			{
				chk_StoragePlanData.setEnabled(false);
				// 入庫棚の自動決定は入庫パッケージに依存する
				chk_IdmAuto.setEnabled(false);
			}
			// 出庫予定データ
			if (!param.getSelectLoadRetrievalData())
			{
				chk_RetrievalPlanData.setEnabled(false);
			}
			// 仕分予定データ
			if (!param.getSelectLoadSortingData())
			{
				chk_PickingPlanData.setEnabled(false);
			}
			// 出荷予定データ
			if (!param.getSelectLoadShippingData())
			{
				chk_ShippingPlanData.setEnabled(false);
			}
			// 入庫棚自動決定
			if (!param.getSelectLoadIdmAuto())
			{
				chk_IdmAuto.setEnabled(false);
			}

			// チェックボックスのデフォルト
			chk_InstockPlanData.setChecked(false);
			chk_StoragePlanData.setChecked(false);
			chk_RetrievalPlanData.setChecked(false);
			chk_PickingPlanData.setChecked(false);
			chk_ShippingPlanData.setChecked(false);
			chk_IdmAuto.setChecked(false);
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 各項目のチェックボックスが正しくチェックされているかチェックします。
	 * @throws Exception 全ての例外を報告します。 
	 */
	private boolean selectCheck(boolean storageCheck, boolean idmCheck) throws Exception
	{
		if (!storageCheck && idmCheck)
		{
			return false;
		}
		
		return true;
	}
	// Event handler methods -----------------------------------------

	/** 
	 * メニューボタンが押下された時に呼ばれます。 <BR>
	 * <BR>
	 * メニュー画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 開始ボタンが押された時に呼ばれます。 <BR>
	 * <BR>
	 * 概要:データ取込を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <BR>
	 * ダイアログを表示します。 <BR>
	 * ･開始確認ダイアログボックスを表示します。"開始しますか?" <BR>
	 * <DIR>
	 *   [確認ダイアログ キャンセル] <BR>
	 *   <DIR>
	 *     何も行いません。 <BR>
	 *   </DIR>
	 *   [確認ダイアログ OK] <BR>
	 *   <DIR>
	 *     概要:入力エリア情報でデータ取込を行います。 <BR>
	 *     1.入力領域チェックを行います。 <BR>
	 *     <DIR>
	 *       各チェックボックスにチェックがない場合:"開始対象を選択してください。"のメッセージを表示します。 <BR>
	 *     </DIR>
	 *     2.入力エリアの入力項目のチェックを行います。(必須入力,文字数,文字属性) <BR>
	 *     3.スケジュールを開始します。 <BR>
	 *     4.カーソルを作業者コードにセットします。 <BR>
	 *     5.入力エリアの内容は保持します。 <BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Start_Click(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		// 入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_PlanDate.validate();

		boolean wCheck_flag = false;

		// 1つもチェックボックスにチェックが入っていなければエラーを表示します
		// 入力チェック入荷予定データ
		if (chk_InstockPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック入庫予定データ
		if (chk_StoragePlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック出庫予定データ
		if (chk_RetrievalPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック仕分予定データ
		if (chk_PickingPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック出荷予定データ
		if (chk_ShippingPlanData.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック入庫棚自動決定
		if (chk_IdmAuto.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}		
		
		// エラーメッセージ
		if (!wCheck_flag)
		{
			// 6023232 = 開始対象を選択してください。
			message.setMsgResourceKey("6023232");
			return;
		}

		Connection conn = null;
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter[] param = new SystemParameter[1];
			param[0] = new SystemParameter();

			WmsScheduler schedule = new MasterPlanLoadDataSCH();

			// パラメータに値をセットします
			// 作業者コード
			param[0].setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param[0].setPassword(txt_Password.getText());
			// 予定日
			param[0].setPlanDate(WmsFormatter.toParamDate(txt_PlanDate.getDate()));

			// 入荷予定データ
			param[0].setSelectLoadInstockData(chk_InstockPlanData.getChecked());

			// 入庫予定データ
			param[0].setSelectLoadStorageData(chk_StoragePlanData.getChecked());
			// 入庫棚の自動決定を行う
			param[0].setSelectLoadIdmAuto(chk_IdmAuto.getChecked());

			// 入庫予定データにチェックが入っているかをチェックします
			if (!selectCheck(chk_StoragePlanData.getChecked(), chk_IdmAuto.getChecked()))
			{
				// 6023392=入庫棚の自動決定を行う場合は、入庫予定データの取り込みチェックも同時にチェックして下さい。
				message.setMsgResourceKey("6023392");
				return;
			}
			// 出庫予定データ
			param[0].setSelectLoadRetrievalData(chk_RetrievalPlanData.getChecked());

			// 仕分予定データ
			param[0].setSelectLoadSortingData(chk_PickingPlanData.getChecked());

			// 出荷予定データ
			param[0].setSelectLoadShippingData(chk_ShippingPlanData.getChecked());



			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param[0].setTerminalNumber(userHandler.getTerminalNo());

			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}

			if (schedule.getMessage() != null)
			{
				// メッセージを表示します
				message.setMsgResourceKey(schedule.getMessage());
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
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力エリアの作業者コードとパスワード以外の項目を初期値に戻します。 <BR>
	 *   <DIR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
	 *   </DIR>
	 *   2.カーソルを作業者コードにセットします。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		Connection conn = null;
		try
		{
            // 予定日をセット
            txt_PlanDate.setText("");
   
		    // チェックボックスのデフォルト
		    chk_InstockPlanData.setChecked(false);
		    chk_StoragePlanData.setChecked(false);
		    chk_RetrievalPlanData.setChecked(false);
		    chk_PickingPlanData.setChecked(false);
		    chk_ShippingPlanData.setChecked(false);
		    chk_IdmAuto.setChecked(false);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter initParam = new SystemParameter();
			WmsScheduler schedule = new MasterPlanLoadDataSCH();
			SystemParameter param = (SystemParameter) schedule.initFind(conn, initParam);

			// 予定日をセット
			if (param.getPlanDate() != null)
			{
				txt_PlanDate.setDate(WmsFormatter.toDate(param.getPlanDate()));
			}
			else
			{
				txt_PlanDate.setText("");
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

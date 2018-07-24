// $Id: MasterLoadDataBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterloaddata;
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
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.master.schedule.MasterLoadDataSCH;

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
public class MasterLoadDataBusiness extends MasterLoadData implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		// 作業者コードの初期値をセットします
		txt_WorkerCode.setText("");
		// パスワードの初期値をセットします
		txt_Password.setText("");



		// チェックボックスのデフォルト
		chk_MST_ConsignorMaster.setChecked(false);
		chk_MST_SupplierMaster.setChecked(false);
		chk_ShippingMaster.setChecked(false);
		chk_MST_ItemMaster.setChecked(false);

	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * @param e ActionEvent
	 * @throws Exception
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
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
	public void tab_DataLoad_Click(ActionEvent e) throws Exception
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
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MST_ConsignorMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MST_ConsignorMaster_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MST_SupplierMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MST_SupplierMaster_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShippingMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_ShippingMaster_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MST_ItemMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MST_ItemMaster_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Start_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * データ取込ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:選択したマスタデータの取り込みを行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.入庫開始を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "開始しますか？"<BR>
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
	 * 						作業者コード<BR>
	 * 						パスワード<BR>
	 * 						荷主マスタデータ<BR>
	 * 						仕入先マスタデータ<BR>
	 * 						出荷先マスタデータ<BR>
	 * 						商品マスタデータ<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.データ取り込みが正常終了した場合は、取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
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

		boolean wCheck_flag = false;

		// 1つもチェックボックスにチェックが入っていなければエラーを表示します
		// 入力チェック入荷予定データ
		if (chk_MST_ConsignorMaster.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック入庫予定データ
		if (chk_MST_SupplierMaster.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック出庫予定データ
		if (chk_ShippingMaster.getChecked() && !wCheck_flag)
		{
			wCheck_flag = true;
		}
		// 入力チェック仕分予定データ
		if (chk_MST_ItemMaster.getChecked() && !wCheck_flag)
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

			WmsScheduler schedule = new MasterLoadDataSCH();

			// パラメータに値をセットします
			// 作業者コード
			param[0].setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param[0].setPassword(txt_Password.getText());

			// 荷主マスタデータ
			param[0].setSelectLoadConsignorMasterData(chk_MST_ConsignorMaster.getChecked());

			// 仕入先マスタデータ
			param[0].setSelectLoadSupplierMasterData(chk_MST_SupplierMaster.getChecked());
			// 出荷先マスタデータ
			param[0].setSelectLoadCustomerMasterData(chk_ShippingMaster.getChecked());
			// 商品マスタデータ
			param[0].setSelectLoadItemMasterData(chk_MST_ItemMaster.getChecked());




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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:選択したマスタデータの取り込みを行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.各チェックボックスをデフォルト値へ戻します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);
   
	    // チェックボックスのデフォルト
        chk_MST_ConsignorMaster.setChecked(false);
        chk_MST_SupplierMaster.setChecked(false);
        chk_ShippingMaster.setChecked(false);
        chk_MST_ItemMaster.setChecked(false);

	}


}
//end of class

// $Id: RegistBatchMasterBusiness.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registbatchmaster;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.master.schedule.MasterParameter;
import jp.co.daifuku.wms.master.schedule.RegistBatchMasterSCH;

/**
 * Designer : mtakeuchi <BR>
 * Maker    : mtakeuchi <BR>
 * <BR>
 * 在庫データから必要な情報を取得してマスタに登録する画面です。<BR>
 * 画面から入力された内容をパラメータにセットしスケジュールに渡し、登録処理を行います。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで<BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.登録ボタン押下処理（<CODE>btn_Submit_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。<BR>
 *   スケジュールの結果をメッセージエリアにセットし表示します。<BR>
 *   
 * <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
 *   </DIR>
 * <BR>
 *    [返却データ] <BR>
 * <BR>
 *    <DIR>
 *     各メッセージ
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/03/27</TD><TD>mtakeuchi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class RegistBatchMasterBusiness extends RegistBatchMaster implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面が読み込まれたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <BR>
	 * <DIR>
	 *    1.画面タイトルを表示します。 <BR>
	 *    2.カーソルを作業者コードに移動します。 <BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 作業者コード[なし] <BR>
	 * パスワード[なし] <BR>
	 * 荷主マスタ[チェックなし] <BR>
	 * 仕入先マスタ[チェックなし] <BR>
	 * 商品マスタ[チェックなし] <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面の初期表示を行う
		txt_MstWorkerCode.setText("");
		txt_MstPassWord.setText("");
		chk_MstConsignor.setChecked(false);
		chk_MstSupplier.setChecked(false);
		chk_MstItem.setChecked(false);
		
		// カーソルを作業者コードに移動する
		setFocus(txt_MstWorkerCode);
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * <DIR>
	 *  概要:ダイアログを表示します。 <BR>
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
		//ダイアログを表示する MSG-0009=登録しますか
		btn_MstSubmit.setBeforeConfirm("MSG-W0009");
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
	public void tab_Click(ActionEvent e) throws Exception
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
	 * メニューボタンが押されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に戻ります。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		// メニュー画面に戻ります
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
	public void txt_MstWorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstWorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstWorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstWorkerCode_InputComplete(ActionEvent e) throws Exception
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
	public void txt_MstPassWord_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstPassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstPassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstPassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MstConsignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MstConsignor_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MstSupplier_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MstSupplier_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MstItem_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_MstItem_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_MstSubmit_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:登録を行うかを、ダイアログボックスを表示し確認し、パラメータを渡します。<BR>
	 * <BR>
	 * <DIR>
	 *    ・登録確認ダイアログボックスを表示します。("登録しますか?") <BR>
	 *    <DIR>
	 *        [登録確認ダイアログ OK ]<BR>
	 *        <DIR>	
	 *            1.カーソルを作業者コードにセットします。<BR>
	 *            2.入力エリアの入力項目のチェックを行います。(必須入力)<BR>
	 * 	          3.入力エリアの情報で、マスタ登録を行います。<BR>
	 *            4.スケジュールを開始します。<BR>
	 *            5.スケジュールから値を受け取る。<BR>
	 * 			  6.メッセージを表示します。<BR>
	 *        </DIR>
	 *            [登録確認ダイアログ キャンセル] <BR>
	 *        <DIR>
	 *             何も行ないません。<BR>
	 *        </DIR>
	 *    </DIR>
	 *   
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception  全ての例外を報告します。 
	 */	
	public void btn_MstSubmit_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードに移動する
		setFocus(txt_MstWorkerCode);
		// 入力チェック 必須チェック、文字チェック
		txt_MstWorkerCode.validate();
		txt_MstPassWord.validate();
		
		Connection conn = null;

		try
		{
			//	コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータへセット
			MasterParameter[] param = new MasterParameter[1];
			param[0] = new MasterParameter();
			// 作業者コード
			param[0].setWorkerCode(txt_MstWorkerCode.getText());
			// パスワード
			param[0].setPassword(txt_MstPassWord.getText());
			// 荷主マスタチェックボックス
			param[0].setChkConsignorMaster(chk_MstConsignor.getChecked());
			// 仕入先マスタチェックボックス
			param[0].setChkSupplierMaster(chk_MstSupplier.getChecked());
			// 商品マスタチェックボックス
			param[0].setChkItemMaster(chk_MstItem.getChecked());

			// スケジュールの宣言
			WmsScheduler schedule = new RegistBatchMasterSCH(conn);

			if (schedule.startSCH(conn, param))
			{
				// コミットを行う
				conn.commit();
			}
			else
			{
				// ロールバックを行う
				conn.rollback();
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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		
		
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_MstClear_Server(ActionEvent e) throws Exception
	{
	}

	/**
     * クリアボタンが押下されたときに呼ばれます。<BR>
     * <BR>
     * 概要:入力エリアをクリアします<BR>
     * </BR>
     * <DIR>
     *        1.入力エリアの項目を初期値に戻します。<BR>
     *        2.カーソルを作業者コードにセットします。 <BR>
     * </DIR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
     */
	public void btn_MstClear_Click(ActionEvent e) throws Exception
	{
		// 画面の初期表示を行う
		chk_MstConsignor.setChecked(false);
		chk_MstSupplier.setChecked(false);
		chk_MstItem.setChecked(false);
		
		// カーソルを作業者コードに移動する
		setFocus(txt_MstWorkerCode);
	}


}
//end of class

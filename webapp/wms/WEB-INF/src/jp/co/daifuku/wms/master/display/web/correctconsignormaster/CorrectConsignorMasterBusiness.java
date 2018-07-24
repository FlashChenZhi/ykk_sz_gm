// $Id: CorrectConsignorMasterBusiness.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.correctconsignormaster;
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
import jp.co.daifuku.wms.master.display.web.listbox.listmasterconsignor.ListMasterConsignorBusiness;
import jp.co.daifuku.wms.master.schedule.CorrectConsignorMasterSCH;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer : E.Takeda <BR>
 * Maker    : E.Takeda <BR>
 * <BR>
 * 荷主マスタ修正・削除(条件入力)画面です。<BR>
 * 画面から入力された内容をパラメータにセットした後に、スケジュールに渡し、２画面目に遷移します。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで <BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.次へボタン押下処理（<CODE>btn_Next_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。 <BR>
 *   スケジュールの結果がtureの場合はviewStateに保持し次画面へ遷移します。 <BR>
 *   スケジュールでエラーがあった場合はメッセージを表示します。 <BR>
 * <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード*<BR>
 *     荷主コード*<BR>
 *   </DIR>
 * <BR>
 *    [返却データ] <BR>
 *    <BR>
 *    <DIR>
 *     メッセージ
 *    </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/10</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 *
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class CorrectConsignorMasterBusiness extends CorrectConsignorMaster implements WMSConstants
{
	// Class fields --------------------------------------------------
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/master/listbox/listmasterconsignor/ListMasterConsignor.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 画面が読み込まれたときに呼ばれます。<BR>
     * <BR>
     * 概要:画面の初期表示を行います。<BR>
     * <BR>
     * <DIR>
     *     1.タイトルを表示します。<BR>
     *     2.ViewStateの作業者コードに値があるとき、表示します。<BR>
     *     3.カーソルを作業者コードに移動します。 <BR>
     * </DIR>
	 * <BR>
     *   項目[初期値] <BR>
     * <BR>
     *   作業者コード[なし] <BR>
     *   パスワード[なし] <BR>
     *   荷主コード[なし] <BR>
     * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */	
	public void page_Load(ActionEvent e) throws Exception
	{
	    // 2画面目から戻った時タイトルをセットします
		if (!StringUtil.isBlank(getViewState().getString("TITLE")))
		{
			lbl_SettingName.setResourceKey(getViewState().getString("TITLE"));
		}
		
		// １画面目のタブを前に表示します
		tab.setSelectedIndex(1);
	       
		// 作業者コードに入力値があるとき
		if (!StringUtil.isBlank(getViewState().getString("WorkerCode")))
		{
			txt_workerCode.setText(getViewState().getString("WorkerCode"));
		}
		else
		{
			txt_workerCode.setText("");
		}
		// パスワードに入力値があるとき
		if (!StringUtil.isBlank(getViewState().getString("Password")))
		{
			txt_password.setText(getViewState().getString("Password"));
		}
		// 荷主コードに入力値があるとき
		if (!StringUtil.isBlank(getViewState().getString("ConsignorCode")))
		{
			txt_consignorCode.setText(getViewState().getString("ConsignorCode"));
		} else {
			// 荷主コード、荷主名称
			setConsignor();
		}
	       
	    // カーソルを作業者コードに移動します
		setFocus(txt_workerCode);
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
	 * Pageに定義されているpage_DlgBackをオーバライドします。<BR>
	 * <BR>
	 * 概要:検索画面の返却データを取得しセットします<BR>
	 * <BR><DIR>
	 *      1.ポップアップの検索画面から返される値を取得します。<BR>
	 *      2.値が空でないときに画面にセットします。<BR>
	 * <BR></DIR>
	 * [返却データ] <BR>
	 * <DIR>
	 *     荷主コード <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	 public void page_DlgBack(ActionEvent e) throws Exception
	 {
		DialogParameters param = ((DialogEvent)e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		if (!StringUtil.isBlank(param.getParameter(ListMasterConsignorBusiness.CONSIGNORCODE_KEY)))
		{
		    txt_consignorCode.setText(param.getParameter(ListMasterConsignorBusiness.CONSIGNORCODE_KEY));
		}
        // カーソルを作業者コードに移動する
		setFocus(txt_workerCode);
	 }

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 初期表示時、スケジュールから荷主を取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主を入力エリアにセットします。 <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
		    txt_consignorCode.setText("");
			
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			MasterParameter param = new MasterParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new CorrectConsignorMasterSCH();
			param = (MasterParameter) schedule.initFind(conn, param);

			if (param != null)
			{
			    txt_consignorCode.setText(param.getConsignorCode());
			}
			else
			{
			    txt_consignorCode.setText("");
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

	// Event handler methods -----------------------------------------
	/** 
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に戻ります。<BR>
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:パラメータに入力データをセットし荷主検索リストボックスを表示します。<BR>
	 * <BR><DIR>
	 * [パラメータ] <BR>
	 * <BR></DIR> 
	 *      荷主コード <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Search_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListMasterConsignorBusiness.CONSIGNORCODE_KEY, txt_consignorCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 次へボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:該当荷主情報の修正画面を表示します。<BR>
     * <BR>
     * <DIR>
     *     1.作業者コードの入力チェックをします。<BR>
     *     2.入力データをパラメータにセットします。<BR>
     *     3.スケジュールを開始します。<BR>
     *     4.ViewStateにデータを保持します。<BR>
     *     5.次画面（荷主マスタ修正・削除画面）に遷移します。<BR>
     * </DIR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
	public void btn_next_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードに移動します
		setFocus(txt_workerCode);
		// 項目チェック
		txt_workerCode.validate();
		txt_password.validate();
		txt_consignorCode.validate();
		
		Connection conn = null;
		
		try
		{
            // スケジュールパラメータの宣言
			MasterParameter param = new MasterParameter();
			// 作業者コード
			param.setWorkerCode(txt_workerCode.getText());
			// パスワード
			param.setPassword(txt_password.getText());
			// 荷主コード
			param.setConsignorCode(txt_consignorCode.getText());
			
            // コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			//	スケジュールの宣言
			WmsScheduler schedule= new CorrectConsignorMasterSCH();
			        
            if (schedule.nextCheck(conn,param))
            {
            	// ViewStateに作業者コードをセットする
            	getViewState().setString("WorkerCode", txt_workerCode.getText());
            	// ViewStateにパスワードをセットする
            	getViewState().setString("Password", txt_password.getText());
            	// ViewStateに荷主コードをセットする
				getViewState().setString("ConsignorCode",txt_consignorCode.getText());
				// ViewStateに画面タイトルをセットする
				getViewState().setString("TITLE", lbl_SettingName.getResourceKey());
                // 画面遷移を行う
				forward("/master/correctconsignormaster/CorrectConsignorMaster2.do");
            }
            else
            {
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
     * 概要:入力エリアをクリアします<BR>
     * </BR>
     * <DIR>
     *     1.入力エリアの項目を初期値に戻します。<BR>
     *     <DIR>
     *     初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
     *     </DIR>
     *     2.カーソルを作業者コードにセットします。 <BR>
     * </DIR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
     */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 荷主コード、荷主名称
		setConsignor();

        // カーソルを作業者コードに移動する
		setFocus(txt_workerCode);
	}
}
//end of class

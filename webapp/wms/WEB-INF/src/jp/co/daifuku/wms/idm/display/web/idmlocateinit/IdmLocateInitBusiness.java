// $Id: IdmLocateInitBusiness.java,v 1.1.1.1 2006/08/17 09:34:09 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.idm.display.web.idmlocateinit;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.UserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.idm.schedule.IdmLocateInitSCH;
import jp.co.daifuku.wms.idm.schedule.IdmOperate;
import jp.co.daifuku.wms.idm.schedule.ToolParamater;

/**
 * Designer : Y.Kawai <BR>
 * Maker    : Y.Kawai <BR>
 * <BR>
 * 移動ラック初期データ設定クラスです。<BR>
 * 画面から入力された内容をパラメータにセットしスケジュールに渡し、棚情報の登録と削除を行います。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで<BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.ページロード時 （<CODE>page_Load</CODE>メソッド） <BR>
 * <BR>
 * <DIR>
 *  ラジオボタンのチェックをバンク優先にします。<BR>
 *  <BR>
 * 
 * 2.設定ボタン押下処理（<CODE>btn_Establish_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。<BR>
 *   スケジュールの結果をメッセージエリアにセットし表示します。<BR>
 * <BR>
 *  <DIR>
 *   登録選択時 <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     開始棚No*<BR>
 * 	   終了棚No* <BR>
 *     空棚検索* <BR>
 *   </DIR>
 * <BR>
 *    [返却データ] <BR>
 *   <DIR>
 *    なし<BR>
 *   </DIR>
 * <BR>
 * <DIR>
 *   削除選択時 <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     削除棚No*<BR>
 *   </DIR>
 * <BR>
 *    [返却データ] <BR>
 *   <DIR>
 *    なし<BR>
 *   </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/13</TD><TD>Y.Kawai(UTC)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:09 $
 * @author  $Author: mori $
 */
public class IdmLocateInitBusiness extends IdmLocateInit implements WMSConstants
{
	// Class fields --------------------------------------------------

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
	 *    1.入力エリアを初期設定します。<BR>
	 *    2.スケジュールを開始します。 <BR>
	 *    3.カーソルを開始棚のバンク№にセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 登録･削除[登録] <BR>
	 * 開始棚[なし] <BR>
	 * 終了棚[なし] <BR>
	 * 空棚検索	[バンク優先]
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// ボタン押下を不可にする
		// メニューボタン
		btn_ToMenu.setEnabled(false);

		setFirstDisp();
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * <DIR>
	 *  概要:ダイアログを表示します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			// パラメータを取得
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			// ViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			// 画面名称をセットする
			lbl_SettingName.setResourceKey(title);
		}
		// 登録ボタン押下時確認ダイアログMSG-9000=設定しますか？
		btn_Establish.setBeforeConfirm("MSG-9000");
			
		// カーソルを開始棚のバンクにセットする。
		setFocus(txt_StartBank);

	}
	


	/** 
	 * アドレスを直接指定して呼び出すためのメソッドです。<BR>
	 * http://localhost:8090/wms/idm/idmlocateinit/IdmLocateInit.do
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 **/
	public void page_LoginCheck(ActionEvent e) throws Exception
	{
		//  ログインユーザ情報をセッションにセット
		setUserInfo(new UserInfo());

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

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
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			rdo_Select_Establish_Regist.setChecked(true);
			rdo_Select_Establish_Delete.setChecked(false);

			// 開始棚№
			txt_StartBank.setText("");
			txt_StartBay.setText("01");
			txt_StartLevel.setText("01");
			txt_StartBay.setReadOnly(true);
			txt_StartLevel.setReadOnly(true);

			// 終了棚№
			txt_EndBank.setReadOnly(false);
			txt_EndBay.setReadOnly(false);
			txt_EndLevel.setReadOnly(false);
			txt_EndBank.setText("");
			txt_EndBay.setText("");
			txt_EndLevel.setText("");

			// ラジオボタンのチェックをバンク優先にする。
			rdo_Search_Priority_Bank.setEnabled(true);
			rdo_Search_Priority_Aisle.setEnabled(true);
			rdo_Search_Priority_Bank.setChecked(true);
			rdo_Search_Priority_Aisle.setChecked(false);

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{

				if (conn != null)
				{
					// コネクションロールバック
					conn.rollback();
					// コネクションクローズ
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
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Select_Establish_Regist_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 登録チェック時にこのメソッドが呼ばれます。<BR>
	 * <BR>
	 * 概要：開始棚のベイ･レベル№に01をSETして無効にします。 <BR>
	 *       終了棚、空棚検索フラグを有効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Select_Establish_Regist_Click(ActionEvent e) throws Exception
	{
		// 開始棚
		txt_StartBay.setText("01");
		txt_StartLevel.setText("01");
		txt_StartBay.setReadOnly(true);
		txt_StartLevel.setReadOnly(true);
		// 終了棚
		txt_EndBank.setReadOnly(false);
		txt_EndBay.setReadOnly(false);
		txt_EndLevel.setReadOnly(false);
		// 空棚検索フラグ
		rdo_Search_Priority_Bank.setEnabled(true);
		rdo_Search_Priority_Aisle.setEnabled(true);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Select_Establish_Delete_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 削除チェック時にこのメソッドが呼ばれます。<BR>
	 * <BR>
	 * 概要：開始棚を有効にします。<BR>
	 *       終了棚、空棚検索を無効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Select_Establish_Delete_Click(ActionEvent e) throws Exception
	{
		// 開始棚
		txt_StartBay.setReadOnly(false);
		txt_StartLevel.setReadOnly(false);
		// 終了棚
		txt_EndBank.setText("");
		txt_EndBay.setText("");
		txt_EndLevel.setText("");
		txt_EndBank.setReadOnly(true);
		txt_EndBay.setReadOnly(true);
		txt_EndLevel.setReadOnly(true);
		// 空棚検索フラグ
		rdo_Search_Priority_Bank.setEnabled(false);
		rdo_Search_Priority_Aisle.setEnabled(false);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartLocation_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBank_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBank_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphn1_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBay_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBay_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphn2_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLevel_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DeleteLocateNoTxt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndLocation_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBank_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBank_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphn3_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBay_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBay_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphn4_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLevel_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EmptyLocateSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Search_Priority_Bank_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Search_Priority_Bank_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Search_Priority_Aisle_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Search_Priority_Aisle_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Establish_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 設定ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をパラメータにセットし棚情報の登録又は削除を行います。<BR>
	 * 本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 登録時 <BR>
	 * 1.入力チェック（必須入力、文字数、文字属性）<BR>
	 * 2.入力チェック（開始棚№が終了棚№より小さいこと）<BR>
	 * 3.入力項目をパラメータにセットする。<BR>
	 * 4.入力項目にしたがってDBに棚情報を登録する。<BR>
	 * </DIR>
	 * <DIR>
	 * 削除時 <BR>
	 * 1.入力チェック（必須入力、文字数、文字属性）<BR>
	 * 2.入力項目をパラメータにセットする。<BR>
	 * 3.入力項目にしたがってDBから棚情報を削除する。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Establish_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		IdmOperate iOpe = new IdmOperate();
		try
		{
			// 登録選択時
			if (rdo_Select_Establish_Regist.getChecked())
			{
				// 入力チェック
				txt_StartBank.validate();
				txt_StartBay.validate();
				txt_StartLevel.validate();
				txt_EndBank.validate();
				txt_EndBay.validate();
				txt_EndLevel.validate();

				// パターンマッチング文字
				txt_StartBank.validate(true);
				txt_StartBay.validate(true);
				txt_StartLevel.validate(true);
				txt_EndBank.validate(true);
				txt_EndBay.validate(true);
				txt_EndLevel.validate(true);


				// 開始棚
				String startLocation = iOpe.importFormatIdmLocation(
						txt_StartBank.getText(), txt_StartBay.getText(), txt_StartLevel.getText());
				// 終了棚
				String endLocation =
					iOpe.importFormatIdmLocation(
						txt_EndBank.getText(), txt_EndBay.getText(), txt_EndLevel.getText());

				// 開始棚・終了棚の入力チェック
				if (!StringUtil.isBlank(txt_StartBank.getText())
					&& !StringUtil.isBlank(txt_EndBank.getText()))
				{
					// 開始棚のフォーマットチェック
					if (!iOpe.checkFormatIdmLocation(
							txt_StartBank.getText(), txt_StartBay.getText(), txt_StartLevel.getText()))
					{
						// 6003103={0}のフォーマットが違います
						message.setMsgResourceKey(
							"6003103" + Message.MSG_DELIM + DisplayText.getText(lbl_StartLocation.getResourceKey()));
						return;
					}
					// 終了棚のフォーマットチェック
					if (!iOpe.checkFormatIdmLocation(
							txt_EndBank.getText(), txt_EndBay.getText(), txt_EndLevel.getText()))
					{
						// 6003103={0}のフォーマットが違います
						message.setMsgResourceKey(
							"6003103" + Message.MSG_DELIM + DisplayText.getText(lbl_EndLocation.getResourceKey()));
						return;
					}
					// 終了棚は開始棚よりも大きいこと
					if (startLocation.compareTo(endLocation) > 0)
					{
						// 6023057 = {0}には{1}以上の値を入力してください。
						message.setMsgResourceKey(
							"6023057" + Message.MSG_DELIM + DisplayText.getText(lbl_StartLocation.getResourceKey())
								+ Message.MSG_DELIM + DisplayText.getText(lbl_EndLocation.getResourceKey()));
						return;
					}
				}

				// スケジュールパラメータへセット
				ToolParamater param = new ToolParamater();
				// 開始バンク･ベイ･レベル№
				param.setStartBankNo(txt_StartBank.getText());
				param.setStartBayNo(txt_StartBay.getText());
				param.setStartLevelNo(txt_StartLevel.getText());
				// 終了棚バンク･ベイ･レベル№
				param.setEndBankNo(txt_EndBank.getText());
				param.setEndBayNo(txt_EndBay.getText());
				param.setEndLevelNo(txt_EndLevel.getText());
				// 空棚検索優先順位
				if (rdo_Search_Priority_Bank.getChecked())
				{
					// バンク優先
					param.setBankAisleFlag(ToolParamater.BANKAISLE_FLAG_BANK);
				}
				else if (rdo_Search_Priority_Aisle.getChecked())
				{
					// アイル優先
					param.setBankAisleFlag(ToolParamater.BANKAISLE_FLAG_AISLE);
				}

				conn = ConnectionManager.getConnection(DATASOURCE_NAME);
				// スケジュール開始
				IdmLocateInitSCH schedule = new IdmLocateInitSCH();

				if (schedule.startSCH(conn, param))
				{
					conn.commit();
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					conn.rollback();

					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
					return;
				}
			}
			// 削除選択時
			if (rdo_Select_Establish_Delete.getChecked())
			{
				// 入力チェック
				txt_StartBank.validate();
				txt_StartBay.validate();
				txt_StartLevel.validate();

				// パターンマッチング文字
				txt_StartBank.validate(true);
				txt_StartBay.validate(true);
				txt_StartLevel.validate(true);

				// 削除棚の入力チェック
				if (!StringUtil.isBlank(txt_StartBank.getText()))
				{
					// 削除棚のフォーマットチェック
					if (!iOpe.checkFormatIdmLocation(
							txt_StartBank.getText(), txt_StartBay.getText(), txt_StartLevel.getText()))
					{
						// 6003103={0}のフォーマットが違います
						message.setMsgResourceKey(
							"6003103" + Message.MSG_DELIM + DisplayText.getText(lbl_StartLocation.getResourceKey()));
						return;
					}
				}

				// スケジュールパラメータへセット
				ToolParamater param = new ToolParamater();
				// 開始バンク･ベイ･レベル№
				param.setStartBankNo(txt_StartBank.getText());
				param.setStartBayNo(txt_StartBay.getText());
				param.setStartLevelNo(txt_StartLevel.getText());

				conn = ConnectionManager.getConnection(DATASOURCE_NAME);
				// スケジュール開始
				IdmLocateInitSCH schedule = new IdmLocateInitSCH();

				if (schedule.deleteLocate(conn, param))
				{
					conn.commit();
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					conn.rollback();

					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
					return;
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
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを開始棚にセットします。<BR>
	 * </DIR>
	 * @throws e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setFirstDisp();
	}

}
//end of class

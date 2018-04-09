//$Id: CorrectCustomerMaster2Business.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.correctcustomermaster;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.master.schedule.CorrectCustomerMasterSCH;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer : E.Takeda <BR>
 * Maker    : E.Takeda<BR>
 * <BR>
 * 出荷先マスタ修正・削除(修正入力)画面です。<BR>
 * １画面から渡されたデータを画面に表示します。 <BR>
 * 画面で入力された内容をパラメータにセットした後に、スケジュールに渡します。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで<BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.ページロード時 （<CODE>page_Load</CODE>メソッド） <BR>
 * <BR>
 * <DIR>
 *   ViewStateに保持している値で、検索を行います。 <BR>
 *   スケジュールの結果を入力エリアにセットし表示します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力 <BR>
 *   <DIR>
 *     荷主コード* <BR>
 *     出荷先コード* <BR>
 *   </DIR>
 * <BR>
 * [返却データ] <BR>
 * <BR>
 *   <DIR>
 *     荷主コード <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     郵便番号 <BR>
 *     県名 <BR>
 *     住所 <BR>
 *     ビル名等 <BR>
 *     連絡先1 <BR>
 *     連絡先2 <BR>
 *     連絡先3 <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 * </DIR>
 * 2.修正登録ボタン押下処理（<CODE>btn_modify_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。<BR>
 *   スケジュールの結果をメッセージエリアにセットし表示します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     出荷先コード* <BR>
 *     出荷先名称 <BR>
 *     郵便番号 <BR>
 *     県名 <BR>
 *     住所 <BR>
 *     ビル名等 <BR>
 *     連絡先1 <BR>
 *     連絡先2 <BR>
 *     連絡先3 <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR>
 *   <DIR>
 *     各メッセージ <BR>
 *     荷主コード <BR>
 *     出荷先コード <BR>
 *     出荷先名称 <BR>
 *     郵便番号 <BR>
 *     県名 <BR>
 *     住所 <BR>
 *     ビル名等 <BR>
 *     連絡先1 <BR>
 *     連絡先2 <BR>
 *     連絡先3 <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 * </DIR>
 * 3.削除ボタン押下処理（<CODE>btn_Delete_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。<BR>
 *   スケジュールの結果をメッセージエリアにセットし表示します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     出荷先コード* <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR>
 *   <DIR>
 *     各メッセージ <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/06</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class CorrectCustomerMaster2Business extends CorrectCustomerMaster2 implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.画面のタイトルを表示します。 <BR>
	 *   2.１画面から持ってきた値をパラメータをセットします。 <BR>
	 *   3.スケジュールを開始します。 <BR>
	 *   4.画面に返却データを設定します。 <BR>
	 *   5.カーソルを出荷先名称に初期セットします。<BR>
	 *   <BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 荷主コード[検索面] <BR>
	 * 出荷先コード [検索面] <BR>
	 * 出荷先名称[検索値] <BR>
	 * 郵便番号[検索値] <BR>
	 * 県名[検索値] <BR>
	 * 住所[検索値] <BR>
	 * ビル名等[検索値] <BR>
	 * 連絡先1[検索値] <BR>
	 * 連絡先2[検索値] <BR>
	 * 連絡先3[検索値] <BR>
	 * 最終更新日 [検索値] <BR>
	 * 最終使用日 [検索値] <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		lbl_SettingName.setResourceKey(this.getViewState().getString("TITLE"));

		Connection conn = null;
		try
		{
			// 2画面目のタブを前に表示します
			tab.setSelectedIndex(2);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールの宣言 
			WmsScheduler schedule = new CorrectCustomerMasterSCH();

			// スケジュールパラメータの宣言
			MasterParameter param = new MasterParameter();
			// １画面から持ってきた値をパラメータをセットします
			param.setConsignorCode(getViewState().getString("ConsignorCode"));
			param.setCustomerCode(getViewState().getString("CustomerCode"));
			// スケジュールを開始します。 
			MasterParameter[] initParam = (MasterParameter[]) schedule.query(conn, param);

			// 前画面で、作業者に対しチェックしているのでinitParamのnullチェックは不要  

			// 画面に返却データを設定します。 
			// 荷主コード
			lbl_Consignor.setText(initParam[0].getConsignorCode());
			// 出荷先コード
			lbl_Customer.setText(initParam[0].getCustomerCode());
			// 出荷先名称
			txt_CustomerName.setText(initParam[0].getCustomerName());
			// 郵便番号
			txt_postalCode.setText(initParam[0].getPostalCode());
			// 県名
			txt_prefecture.setText(initParam[0].getPrefecture());
			// 住所
			txa_Address.setText(initParam[0].getAddress());
			// ビル名等
			txa_Address2.setText(initParam[0].getAddress2());
			// 連絡先1
			txa_contact1.setText(initParam[0].getContact1());
			// 連絡先2
			txa_contact2.setText(initParam[0].getContact2());
			// 連絡先3
			txa_contact3.setText(initParam[0].getContact3());
			// 最終更新日
			txt_lastUpdateDate.setDate(initParam[0].getLastUpdateDate());
			this.getViewState().setString("LastUpDate",initParam[0].getLastUpdateDate().toString());
			// 最終使用日
			txt_lastUseDate.setDate(initParam[0].getLastUseDate());
			this.getViewState().setString("LastUseDate",initParam[0].getLastUseDate().toString());

			// カーソルを荷主名称に移動します。
			setFocus(txt_CustomerName);
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
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
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * <DIR> 
	 *   概要:ダイアログを表示します。<BR>
	 * </DIR>
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
		//ダイアログを表示します。"修正登録しますか?"
		btn_modify.setBeforeConfirm("MSG-W0033");
		//ダイアログを表示します。"削除しますか?"
		btn_Delete.setBeforeConfirm("MSG-W0007");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();

		// 出荷先名称
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// 郵便番号
		if (!checker.checkContainNgText(txt_postalCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// 県名
		if (!checker.checkContainNgText(txt_prefecture))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// 住所
		if (!checker.checkContainNgText(txa_Address))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// ビル名等
		if (!checker.checkContainNgText(txa_Address2))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// 連絡先1
		if (!checker.checkContainNgText(txa_contact1))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// 連絡先2
		if (!checker.checkContainNgText(txa_contact2))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// 連絡先3
		if (!checker.checkContainNgText(txa_contact3))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		return true;
	}
	
	// Event handler methods -----------------------------------------
	/** 
	 * 戻るボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:前画面に戻ります。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_back_Click(ActionEvent e) throws Exception
	{
		//画面遷移を行う
		forward("/master/correctcustomermaster/CorrectCustomerMaster.do");
	}

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
	 * 修正登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:修正登録を行うかを、ダイアログボックスを表示し確認し、パラメータを渡します。<BR>
	 * <BR>
	 * <DIR>
	 *   ・修正確認ダイアログボックスを表示します。("修正登録しますか?") <BR>
	 *   <DIR>
	 *     [修正登録確認ダイアログ ＯＫ ] <BR>
	 *     <DIR>
	 *       1.カーソルを出荷先名称に移動します。 <BR>
	 *       2.入力エリアの入力項目のチェックを行います。 <BR>
	 *       3.入力エリアの情報で、修正登録を行います。<BR>
	 *       4.スケジュールを開始します。<BR>
	 *       5.スケジュールから値を受け取る。<BR>
	 *       6.メッセージを表示します。<BR>
	 *     </DIR>
	 *     [修正登録確認ダイアログ キャンセル] <BR>
	 *     <DIR>
	 *       何も行ないません。<BR>
	 *     </DIR>
	 *   </DIR>
	 * </DIR><BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception  全ての例外を報告します。
	 */
	public void btn_modify_Click(ActionEvent e) throws Exception
	{
		//カーソルを出荷先名称に移動します。
		setFocus(txt_CustomerName);
		//入力チェック
		txt_CustomerName.validate(false);
		txt_postalCode.validate(false);
		txt_prefecture.validate(false);
		txa_Address.validate(false);
		txa_Address2.validate(false);
		txa_contact1.validate(false);
		txa_contact2.validate(false);
		txa_contact3.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//スケジュールパラメータへセット
			MasterParameter[] param = new MasterParameter[1];
			param[0] =new MasterParameter();
			//荷主コード
			param[0].setConsignorCode(lbl_Consignor.getText());
			//出荷先コード
			param[0].setCustomerCode((lbl_Customer.getText()));
			//出荷先名称
			param[0].setCustomerName(txt_CustomerName.getText());
			//郵便番号
			param[0].setPostalCode(txt_postalCode.getText());
			//県名
			param[0].setPrefecture(txt_prefecture.getText());
			//住所
			param[0].setAddress(txa_Address.getText());
			//ビル名等
			param[0].setAddress2(txa_Address2.getText());
			//連絡先1
			param[0].setContact1(txa_contact1.getText());
			//連絡先2
			param[0].setContact2(txa_contact2.getText());
			//連絡先3
			param[0].setContact3(txa_contact3.getText());
			//最終更新日 
			param[0].setLastUpdateDateString(this.getViewState().getString("LastUpDate"));

			//スケジュールの宣言
			WmsScheduler schedule = new CorrectCustomerMasterSCH();

			//スケジュールを開始します
			MasterParameter[] modifyParam = (MasterParameter[]) schedule.startSCHgetParams(conn, param);

			if (modifyParam == null || modifyParam.length == 0)
			{
				//ロールバック
				conn.rollback() ;
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			else
			{
				//コミット
				conn.commit();
			}

			//修正登録しました
			message.setMsgResourceKey(schedule.getMessage());

			//最終更新日を更新します
			txt_lastUpdateDate.setDate(modifyParam[0].getLastUpdateDate());
			//viewStateの最終更新日時を更新します
			this.getViewState().setString("LastUpDate",modifyParam[0].getLastUpdateDate().toString());
			//最終使用日を更新する
			txt_lastUseDate.setDate(modifyParam[0].getLastUseDate());
			//viewStateの最終使用日を更新します
			this.getViewState().setString("LastUseDate", modifyParam[0].getLastUseDate().toString());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//コネクションクローズ
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
	 * 削除ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:削除を行うかを、ダイアログボックスを表示し確認し、パラメータを渡します。<BR>
	 * <BR>
	 * <DIR>
	 *   ・削除確認ダイアログボックスを表示します。("削除しますか?") <BR>
	 *   <DIR>
	 *     [削除登録確認ダイアログ ＯＫ ] <BR>
	 *     <DIR>
	 *       1.カーソルを出荷先名称に移動します。 <BR>
	 *       2.入力エリアの入力項目のチェックを行います。 <BR>
	 *       3.入力エリアの情報で、削除処理を行います。<BR>
	 *       4.スケジュールを開始します。<BR>
	 *       5.スケジュールから値を受け取る。<BR>
	 *       6.メッセージを表示します。<BR>
	 *     </DIR>
	 *     [削除確認ダイアログ キャンセル] <BR>
	 *     <DIR>
	 *       何も行ないません。<BR>
	 *     </DIR>
	 *   </DIR>
	 * </DIR><BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception  全ての例外を報告します。
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		//カーソルを出荷先名称に移動します。
		setFocus(txt_CustomerName);
		//入力チェック
		txt_CustomerName.validate(false);
		txt_postalCode.validate(false);
		txt_prefecture.validate(false);
		txa_Address.validate(false);
		txa_Address2.validate(false);
		txa_contact1.validate(false);
		txa_contact2.validate(false);
		txa_contact3.validate(false);

		Connection conn = null;

		try
		{
			//コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			//スケジュールパラメータへセット
			MasterParameter[] param = new MasterParameter[1];
			param[0] =new MasterParameter();
			//荷主コード
			param[0].setConsignorCode(lbl_Consignor.getText());
			//出荷先コード
			param[0].setCustomerCode((lbl_Customer.getText()));
			//最終更新日 
			param[0].setLastUpdateDateString(this.getViewState().getString("LastUpDate"));

			//スケジュールの宣言
			WmsScheduler schedule = new CorrectCustomerMasterSCH();

			//スケジュールを開始します
			if (!schedule.startSCH(conn, param))
			{
				//ロールバック
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			else
			{
				//コミット
				conn.commit();
			}

			//削除しました。
			message.setMsgResourceKey(schedule.getMessage());
			txt_CustomerName.setText("");
			txt_postalCode.setText("");
			txt_prefecture.setText("");
			txa_Address.setText("");
			txa_Address2.setText("");
			txa_contact1.setText("");
			txa_contact2.setText("");
			txa_contact3.setText("");
			txt_lastUpdateDate.setText("");
			txt_lastUseDate.setText("");

			// 削除・クリアボタンを使用不可にする
			// 修正登録
			btn_modify.setEnabled(false);
			// 削除ボタン
			btn_Delete.setEnabled(false);
			// クリアボタン
			btn_Clear.setEnabled(false);
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				//コネクションクローズ
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
	 *   1.入力エリアの項目を下記の様にします。<BR>
	 *   <DIR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
	 *   </DIR>
	 *   2.カーソルを出荷先名称にセットします。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_CustomerName.setText("");
		txt_postalCode.setText("");
		txt_prefecture.setText("");
		txa_Address.setText("");
		txa_Address2.setText("");
		txa_contact1.setText("");
		txa_contact2.setText("");
		txa_contact3.setText("");

		//カーソルを出荷先名称に移動する
		setFocus(txt_CustomerName);
	}
}
//end of class

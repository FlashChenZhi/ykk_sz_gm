// $Id: CorrectItemMaster2Business.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.correctitemmaster;
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
import jp.co.daifuku.wms.master.schedule.CorrectItemMasterSCH;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer : S.Yoshida <BR>
 * Maker    : S.Yoshida <BR>
 * <BR>
 * 商品マスタ修正・削除(修正入力)画面です。<BR>
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
 *  ViewStateに保持している値で、検索を行います。 <BR>
 *  スケジュールの結果を入力エリアにセットし表示します。<BR>
 *  <BR>
 *   [パラメータ] *必須入力 <BR>
 *   <DIR>
 *     荷主コード* <BR>
 *     商品コード* <BR>
 *   </DIR>
 *  <BR>
 *    [返却データ] <BR>
 *  <BR>
 *   <DIR>
 *     荷主コード <BR>
 *     商品コード <BR>
 *     JANコード <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     商品分類コード <BR>
 *     上限在庫数 <BR>
 *     下限在庫数 <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 * </DIR>
 * 2.修正登録ボタン押下処理（<CODE>btn_modify_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。<BR>
 *   スケジュールの結果をメッセージエリアにセットし表示します。<BR>
 * <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     荷主コード* <BR>
 *     商品コード* <BR>
 *     JANコード <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     商品分類コード <BR>
 *     上限在庫数 <BR>
 *     下限在庫数 <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>         
 *   </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *   <DIR>
 *     各メッセージ <BR>
 *     荷主コード <BR>
 *     商品コード <BR>
 *     JANコード <BR>
 *     商品名称 <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     商品分類コード <BR>
 *     上限在庫数 <BR>
 *     下限在庫数 <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 * </DIR>
 * 3.削除ボタン押下処理（<CODE>btn_Delete_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力項目のチェックを行い、正しい場合パラメータにセットして渡します。<BR>
 *   スケジュールの結果をメッセージエリアにセットし表示します。<BR>
 * <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     荷主コード* <BR>
 *     商品コード* <BR>
 *     最終更新日 <BR>
 *     最終使用日 <BR>
 *   </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *   <DIR>
 *     各メッセージ <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/17</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class CorrectItemMaster2Business extends CorrectItemMaster2 implements WMSConstants
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
	 * <BR><DIR>
	 *   1.画面のタイトルを表示します。 <BR>
	 *   2.１画面から持ってきた値をパラメータをセットします。 <BR>
	 *   3.スケジュールを開始します。 <BR>
	 *   4.画面に返却データを設定します。 <BR>
	 *   5.カーソルをJANコードに初期セットします。<BR>
	 * <BR></DIR>
	 * <BR>
	 *   項目 <BR>
	 * <BR>
	 * <DIR>
	 *   荷主コード <BR>
	 *   商品コード <BR>
	 *   JANコード <BR>
	 *   商品名称 <BR>
	 *   ケース入数 <BR>
	 *   ボール入数 <BR>
	 *   ケースITF <BR>
	 *   ボールITF <BR>
	 *   商品分類コード <BR>
	 *   上限在庫数 <BR>
	 *   下限在庫数 <BR>
	 *   最終更新日 <BR>
	 *   最終使用日 <BR>
	 * </DIR>
	 * <BR>
	 * 
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
			WmsScheduler schedule = new CorrectItemMasterSCH();

			// スケジュールパラメータの宣言
			MasterParameter param = new MasterParameter();
			// １画面から持ってきた値をパラメータをセットします
			param.setConsignorCode(getViewState().getString("ConsignorCode"));
			param.setItemCode(getViewState().getString("ItemCode"));
			// スケジュールを開始します。 
			MasterParameter[] initParam = (MasterParameter[]) schedule.query(conn, param);

			// 前画面で、作業者に対しチェックしているのでinitParamのnullチェックは不要  
			// 画面に返却データを設定します。 
			// 荷主コード
			lbl_Consignor.setText(initParam[0].getConsignorCode());
			// 商品コード
			lbl_Item.setText(initParam[0].getItemCode());
			// 商品名称
			txt_itemName.setText(initParam[0].getItemName());
			// ケース入数
			txt_caseEntering.setInt(initParam[0].getEnteringQty());
			// ボール入数
			txt_bundleEntering.setInt(initParam[0].getBundleEnteringQty());
			// ケースITF
			txt_caseItf.setText(initParam[0].getITF());
			// ボールITF
			txt_bundleItf.setText(initParam[0].getBundleITF());
			// 最終更新日
			txt_lastUpdateDate.setDate(initParam[0].getLastUpdateDate());
			this.getViewState().setString("LastUpDate",initParam[0].getLastUpdateDate().toString());
			// 最終使用日
			txt_lastUseDate.setDate(initParam[0].getLastUseDate());
			this.getViewState().setString("LastUseDate",initParam[0].getLastUseDate().toString());
			setFocus(txt_itemName);
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
	 * <DIR> 概要:ダイアログを表示します。<BR>
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
		// ダイアログを表示します。"修正登録しますか?"
		btn_Modify.setBeforeConfirm("MSG-W0033");
		// ダイアログを表示します。"削除しますか?"
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

		// 商品名称
		if (!checker.checkContainNgText(txt_itemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		// ケースITF
		if (!checker.checkContainNgText(txt_caseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}

		// ボールITF
		if (!checker.checkContainNgText(txt_bundleItf))
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
	 * 概要:前画面に戻ります。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		//画面遷移を行う
		forward("/master/correctitemmaster/CorrectItemMaster.do");
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
	 *    ・修正確認ダイアログボックスを表示します。("修正登録しますか?") <BR>
	 *    <DIR>
	 *        [修正登録確認ダイアログ ＯＫ ]<BR>
	 *        <DIR>	
	 *            1.カーソルをJANコードに移動します。 <BR>
	 *            2.入力エリアの入力項目のチェックを行います。 <BR>
	 * 	          3.入力エリアの情報で、修正登録を行います。<BR>
	 *            4.スケジュールを開始します。<BR>
	 *            5.スケジュールから値を受け取る。<BR>
	 * 			  6.メッセージを表示します。<BR>
	 *        </DIR>
	 *        [修正登録確認ダイアログ キャンセル] <BR>
	 *        <DIR>
	 *             何も行ないません。<BR>
	 *        </DIR>
	 *    </DIR>
	 *    
	 * </DIR><BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception  全ての例外を報告します。
	 */
	public void btn_Modify_Click(ActionEvent e) throws Exception
	{
		// カーソルをJANコードに移動します。
		setFocus(txt_itemName);
		// 入力チェック
		txt_itemName.validate(false);
		txt_caseEntering.validate(false);
		txt_bundleEntering.validate(false);
		txt_caseItf.validate(false);
		txt_bundleItf.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			//	コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータへセット
			MasterParameter[] param = new MasterParameter[1];
			param[0] =new MasterParameter();
			// 荷主コード
			param[0].setConsignorCode(lbl_Consignor.getText());
			// 商品コード
			param[0].setItemCode((lbl_Item.getText()));
			// 商品名称
			param[0].setItemName(txt_itemName.getText());
			// ケース入数
			param[0].setEnteringQty(txt_caseEntering.getInt());
			// ボール入数
			param[0].setBundleEnteringQty(txt_bundleEntering.getInt());
			// ケースITF
			param[0].setITF(txt_caseItf.getText());
			// ボールITF
			param[0].setBundleITF(txt_bundleItf.getText());
			// 最終更新日 
			param[0].setLastUpdateDateString(this.getViewState().getString("LastUpDate"));						
			
			// スケジュールの宣言
			WmsScheduler schedule = new CorrectItemMasterSCH();
			// スケジュールを開始します
			MasterParameter[] modifyParam = (MasterParameter[]) schedule.startSCHgetParams(conn, param);
			
			if (modifyParam == null || modifyParam.length == 0)
			{
				// ロールバック
				conn.rollback() ;
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			else
			{
				// コミット
				conn.commit() ;
			}

			// 修正登録しました
			message.setMsgResourceKey(schedule.getMessage());
			// 最終更新日を更新します
			txt_lastUpdateDate.setDate(modifyParam[0].getLastUpdateDate());
			// viewStateの最終更新日時を更新します
			this.getViewState().setString("LastUpDate",modifyParam[0].getLastUpdateDate().toString());
			// 最終使用日を更新する
			txt_lastUseDate.setDate(modifyParam[0].getLastUseDate());
			// viewStateの最終使用日を更新します
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
	 * 削除ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:削除を行うかを、ダイアログボックスを表示し確認し、パラメータを渡します。<BR>
	 * <BR>
	 * <DIR>
	 *    ・削除確認ダイアログボックスを表示します。("削除しますか?") <BR>
	 *    <DIR>
	 *        [削除登録確認ダイアログ ＯＫ ]<BR>
	 *        <DIR>	
	 *            1.カーソルをJANコードに移動します。 <BR>
	 *            2.入力エリアの入力項目のチェックを行います。 <BR>
	 * 	          3.入力エリアの情報で、削除処理を行います。<BR>
	 *            4.スケジュールを開始します。<BR>
	 *            5.スケジュールから値を受け取る。<BR>
	 * 			  6.メッセージを表示します。<BR>
	 *        </DIR>
	 *        [削除確認ダイアログ キャンセル] <BR>
	 *        <DIR>
	 *             何も行ないません。<BR>
	 *        </DIR>
	 *    </DIR>
	 *    
	 * </DIR><BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception  全ての例外を報告します。
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		// カーソルをJANコードに移動します。
		setFocus(txt_itemName);
		// 入力チェック
		txt_itemName.validate(false);
		txt_caseEntering.validate(false);
		txt_bundleEntering.validate(false);
		txt_caseItf.validate(false);
		txt_bundleItf.validate(false);
		
		Connection conn = null;

		try
		{
			//	コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータへセット
			MasterParameter[] param = new MasterParameter[1];
			param[0] =new MasterParameter();
			// 荷主コード
			param[0].setConsignorCode(lbl_Consignor.getText());
			// 商品コード
			param[0].setItemCode((lbl_Item.getText()));
			// 最終更新日 
			param[0].setLastUpdateDateString(this.getViewState().getString("LastUpDate"));		
			
			// スケジュールの宣言
			WmsScheduler schedule = new CorrectItemMasterSCH();

			// スケジュールを開始します
			if (!schedule.startSCH(conn, param))
			{
				// ロールバック
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			else
			{
				// コミット
				conn.commit();
			}

			// 削除しました。
			message.setMsgResourceKey(schedule.getMessage());
			txt_itemName.setText("");
			txt_caseEntering.setText("");
			txt_bundleEntering.setText("");
			txt_caseItf.setText("");
			txt_bundleItf.setText("");
			txt_lastUpdateDate.setText("");
			txt_lastUseDate.setText("");
			
			// 削除・クリアボタンを使用不可にする
			// 修正登録
			btn_Modify.setEnabled(false);
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
     *     2.カーソルをJANコードにセットします。 <BR>
     * </DIR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
     */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		txt_itemName.setText("");
		txt_caseEntering.setText("");
		txt_bundleEntering.setText("");
		txt_caseItf.setText("");
		txt_bundleItf.setText("");
        // カーソルをJANコードに移動する
		setFocus(txt_itemName);
	}

}
//end of class

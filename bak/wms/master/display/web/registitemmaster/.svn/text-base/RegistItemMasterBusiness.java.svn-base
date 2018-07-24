// $Id: RegistItemMasterBusiness.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registitemmaster;
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
import jp.co.daifuku.wms.master.display.web.listbox.listmasterconsignor.ListMasterConsignorBusiness;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;
import jp.co.daifuku.wms.master.schedule.MasterParameter;
import jp.co.daifuku.wms.master.schedule.RegistItemMasterSCH;

/**
 * Designer : S.Yoshida <BR>
 * Maker    : S.Yoshida <BR>
 * <BR>
 * 商品マスタを登録する画面です。<BR>
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
 * <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     作業者コード* <BR>
 *     パスワード* <BR>
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
 * <TR><TD>2006/01/17</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class RegistItemMasterBusiness extends RegistItemMaster implements WMSConstants
{
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/master/listbox/listmasterconsignor/ListMasterConsignor.do";
	// 商品検索ポップアップアドレス
	private static final String DO_SRCH_ITEM = "/master/listbox/listmasteritem/ListMasterItem.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";

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
	 * 荷主コード[なし] <BR>
	 * 商品コード[なし] <BR>
	 * JANコード[なし] <BR>
	 * 商品名称[なし] <BR>
	 * ケース入数[なし] <BR>
	 * ボール入数[なし] <BR>
	 * ケースITF[なし] <BR>
	 * ボールITF[なし] <BR>
	 * 商品分類コード[なし] <BR>
	 * 上限在庫数 <BR>
	 * 下限在庫数 <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面の初期表示を行う
		txt_workerCode.setText("");
		txt_password.setText("");
		// 荷主コード、荷主名称
		setConsignor();
		txt_itemCode.setText("");
		txt_itemName.setText("");
		txt_caseEntering.setText("");
		txt_bundleEntering.setText("");
		txt_caseItf.setText("");
		txt_bundleItf.setText("");

		// カーソルを作業者コードに移動する
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
		//ダイアログを表示する MSG-0009=登録しますか
		btn_Submit.setBeforeConfirm("MSG-W0009");
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
	 *     商品コード <BR>
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
		// 商品コード
		if (!StringUtil.isBlank(param.getParameter(ListMasterItemBusiness.ITEMCODE_KEY)))
		{
			txt_itemCode.setText(param.getParameter(ListMasterItemBusiness.ITEMCODE_KEY));
		}
		// 商品名称
		if (!StringUtil.isBlank(param.getParameter(ListMasterItemBusiness.ITEMNAME_KEY)))
		{
			txt_itemName.setText(param.getParameter(ListMasterItemBusiness.ITEMNAME_KEY));
		}
		// ケース入数
		if (!StringUtil.isBlank(param.getParameter(ListMasterItemBusiness.CASEETR_KEY)))
		{
			txt_caseEntering.setText(param.getParameter(ListMasterItemBusiness.CASEETR_KEY));
		}
		// ボール入数
		if (!StringUtil.isBlank(param.getParameter(ListMasterItemBusiness.BUNDLEETR_KEY)))
		{
			txt_bundleEntering.setText(param.getParameter(ListMasterItemBusiness.BUNDLEETR_KEY));
		}
		// ケースITF
		if (!StringUtil.isBlank(param.getParameter(ListMasterItemBusiness.CASEITF_KEY)))
		{
			txt_caseItf.setText(param.getParameter(ListMasterItemBusiness.CASEITF_KEY));
		}
		// ボールITF
		if (!StringUtil.isBlank(param.getParameter(ListMasterItemBusiness.BUNDLEITF_KEY)))
		{
			txt_bundleItf.setText(param.getParameter(ListMasterItemBusiness.BUNDLEITF_KEY));
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
			WmsScheduler schedule = new RegistItemMasterSCH();
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


	/**
	 * 入力チェックを行います。<BR>
	 * <BR>
	 * 概要：異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();

		// 商品コード
		if (!checker.checkContainNgText(txt_itemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:荷主コードを検索します<BR>
	 * <BR>
	 * <DIR>
	 * [パラメータ] <BR>
	 * <DIR> 
	 *      荷主コード <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ConsignorSearch_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(ListMasterConsignorBusiness.CONSIGNORCODE_KEY, txt_consignorCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:商品コードを検索します<BR>
	 * <BR>
	 * <DIR>
	 * [パラメータ] <BR>
	 * <DIR> 
	 *      荷主コード <BR>
	 *      商品コード <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ItemSearch_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(ListMasterItemBusiness.CONSIGNORCODE_KEY, txt_consignorCode.getText());
		// 商品コード
		param.setParameter(ListMasterItemBusiness.ITEMCODE_KEY, txt_itemCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
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
	 *            2.入力エリアの入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 * 	          3.入力エリアの情報で、修正登録を行います。<BR>
	 *            4.スケジュールを開始します。<BR>
	 *            5.スケジュールから値を受け取る。<BR>
	 * 			  6.メッセージを表示します。<BR>
	 *        </DIR>
	 *            [登録確認ダイアログ キャンセル] <BR>
	 *        <DIR>
	 *             何も行ないません。<BR>
	 *        </DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception  全ての例外を報告します。 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードに移動する
		setFocus(txt_workerCode);
		// 入力チェック 必須チェック、文字チェック
		txt_workerCode.validate();
		txt_password.validate();
		txt_consignorCode.validate();
		txt_itemCode.validate();
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
			param[0] = new MasterParameter();
			// 作業者コード
			param[0].setWorkerCode(txt_workerCode.getText());
			// パスワード
			param[0].setPassword(txt_password.getText());
			// 荷主コード
			param[0].setConsignorCode(txt_consignorCode.getText());
			// 商品コード 
			param[0].setItemCode(txt_itemCode.getText());
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

			// スケジュールの宣言
			WmsScheduler schedule = new RegistItemMasterSCH();

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
     * クリアボタンが押下されたときに呼ばれます。<BR>
     * <BR>
     * 概要:入力エリアをクリアします<BR>
     * </BR>
     * <DIR>
     *        1.入力エリアの項目を初期値に戻します。<BR>
     *        <DIR>
     *        初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
     *        </DIR>
     *        2.カーソルを作業者コードにセットします。 <BR>
     * </DIR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
     */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力エリアの内容をクリアする
		setConsignor();
		txt_itemCode.setText("");
		txt_itemName.setText("");
		txt_caseEntering.setText("");
		txt_bundleEntering.setText("");
		txt_caseItf.setText("");
		txt_bundleItf.setText("");


		// カーソルを作業者コードに移動する
		setFocus(txt_workerCode);
	}
}
//end of class

// $Id: RegistCustomerMasterBusiness.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.registcustomermaster;
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
import jp.co.daifuku.wms.master.display.web.listbox.listmastercustomer.ListMasterCustomerBusiness;
import jp.co.daifuku.wms.master.schedule.MasterParameter;
import jp.co.daifuku.wms.master.schedule.RegistCustomerMasterSCH;

/**
 * Designer : E.Takeda <BR>
 * Maker    : E.Takeda <BR>
 * <BR>
 * 出荷先マスタを登録する画面です。<BR>
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
 *     出荷先コード* <BR>
 *     出荷先名称<BR>
 *     郵便番号<BR>
 *     県名<BR>
 *     住所<BR>
 *     ビル名等<BR>
 *     連絡先1<BR>
 *     連絡先2<BR>
 *     連絡先3<BR>
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
 * <TR><TD>2006/01/11</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class RegistCustomerMasterBusiness extends RegistCustomerMaster implements WMSConstants
{
	// Class fields --------------------------------------------------
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/master/listbox/listmasterconsignor/ListMasterConsignor.do";
	// 出荷先検索ポップアップアドレス
	private static final String DO_SRCH_CUSTOMER = "/master/listbox/listmastercustomer/ListMasterCustomer.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";

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
	 * 出荷先コード[なし] <BR>
	 * 出荷先名称[なし]<BR>
	 * 郵便番号[なし]<BR>
	 * 県名[なし]<BR>
	 * 住所[なし]<BR>
	 * ビル名等[なし]<BR>
	 * 連絡先1[なし]<BR>
	 * 連絡先2[なし]<BR>
	 * 連絡先3[なし]<BR>
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
		txt_customerCode.setText("");
		txt_customerName.setText("");
		txt_postalCode.setText("");
		txt_prefecture.setText("");
		txa_address.setText("");
		txa_address2.setText("");
		txa_contact1.setText("");
		txa_contact2.setText("");
		txa_contact3.setText("");

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
	 *     出荷先コード <BR>
	 *     出荷先名称 <BR>
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
		// 出荷先コード
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.CUSTOMERCODE_KEY)))
		{
		    txt_customerCode.setText(param.getParameter(ListMasterCustomerBusiness.CUSTOMERCODE_KEY));
		}
		// 出荷先名称
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.CUSTOMERNAME_KEY)))
		{
		    txt_customerName.setText(param.getParameter(ListMasterCustomerBusiness.CUSTOMERNAME_KEY));
		}
		// 郵便番号
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.POSTALCODE_KEY)))
		{
			txt_postalCode.setText(param.getParameter(ListMasterCustomerBusiness.POSTALCODE_KEY));
		}
		// 都道府県名
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.PREFECTURE_KEY)))
		{
			txt_prefecture.setText(param.getParameter(ListMasterCustomerBusiness.PREFECTURE_KEY));
		}
		// 住所
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.ADDRESS_KEY)))
		{
			txa_address.setText(param.getParameter(ListMasterCustomerBusiness.ADDRESS_KEY));
		}
		// ビル名等
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.ADDRESS2_KEY)))
		{
			txa_address2.setText(param.getParameter(ListMasterCustomerBusiness.ADDRESS2_KEY));
		}
		// 連絡先1
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.CONTACT1_KEY)))
		{
			txa_contact1.setText(param.getParameter(ListMasterCustomerBusiness.CONTACT1_KEY));
		}
		// 連絡先2
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.CONTACT2_KEY)))
		{
			txa_contact2.setText(param.getParameter(ListMasterCustomerBusiness.CONTACT2_KEY));
		}
		// 連絡先3
		if (!StringUtil.isBlank(param.getParameter(ListMasterCustomerBusiness.CONTACT3_KEY)))
		{
			txa_contact3.setText(param.getParameter(ListMasterCustomerBusiness.CONTACT3_KEY));
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
			WmsScheduler schedule = new RegistCustomerMasterSCH();
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
	 * 概要：異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();

		// 出荷先コード
		if (!checker.checkContainNgText(txt_customerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// 出荷先名称
		if (!checker.checkContainNgText(txt_customerName))
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
		if (!checker.checkContainNgText(txa_address))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// ビル名等
		if (!checker.checkContainNgText(txa_address2))
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
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コードを検索します<BR>
	 * <BR>
	 * <DIR>
	 * [パラメータ] <BR>
	 * <DIR>
	 *      荷主コード <BR>
	 *      出荷先コード <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_CustomerSearch_Click(ActionEvent e) throws Exception
	{
		// 出荷先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListMasterCustomerBusiness.CONSIGNORCODE_KEY, txt_consignorCode.getText());
		// 出荷先コード
		param.setParameter(ListMasterCustomerBusiness.CUSTOMERCODE_KEY, txt_customerCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_CUSTOMER, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:登録を行うかを、ダイアログボックスを表示し確認し、パラメータを渡します。<BR>
	 * <BR>
	 * <DIR>
	 *    ・登録確認ダイアログボックスを表示します。("登録しますか?") <BR>
	 *    <DIR>
	 *        [登録確認ダイアログ ＯＫ ]<BR>
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
		txt_customerCode.validate();
		txt_customerName.validate(false);
		txt_postalCode.validate(false);
		txt_prefecture.validate(false);
		txa_address.validate(false);
		txa_address2.validate(false);
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
			// 出荷先コード 
			param[0].setCustomerCode(txt_customerCode.getText());
			// 出荷先名称
			param[0].setCustomerName(txt_customerName.getText());
			// 郵便番号
			param[0].setPostalCode(txt_postalCode.getText());
			// 県名
			param[0].setPrefecture(txt_prefecture.getText());
			// 住所
			param[0].setAddress(txa_address.getText());
			// ビル名等
			param[0].setAddress2(txa_address2.getText());
			// 連絡先1
			param[0].setContact1(txa_contact1.getText());
			// 連絡先2
			param[0].setContact2(txa_contact2.getText());
			// 連絡先3
			param[0].setContact3(txa_contact3.getText());
			
			// スケジュールの宣言
			WmsScheduler schedule = new RegistCustomerMasterSCH();
			
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
		txt_customerCode.setText("");
		txt_customerName.setText("");
		txt_postalCode.setText("");
		txt_prefecture.setText("");
		txa_address.setText("");
		txa_address2.setText("");
		txa_contact1.setText("");
		txa_contact2.setText("");
		txa_contact3.setText("");

		// カーソルを作業者コードに移動する
		setFocus(txt_workerCode);
	}
}
//end of class

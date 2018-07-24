// $Id: ShippingPlanDeleteBusiness.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingplandelete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingPlanDeleteSCH;

/**
 * Designer : B.Shibayama <BR>
 * Maker    : B.Shibayama <BR>
 * <BR>
 * 出荷予定データ削除画面クラスです。<BR>
 * 画面から入力された内容をパラメータにセットしスケジュールに渡し、表示処理を行います。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで<BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示時 （<CODE>page_Load</CODE>メソッド） <BR>
 * <BR>
 * <DIR>
 *  画面初期表示で出荷予定情報に荷主コードが1件のみ存在した場合、表示します。<BR>
 *  <BR>
 *   [パラメータ] <BR>
 *  <BR>
 *   <DIR>
 *       なし<BR>
 *   </DIR>
 *  <BR>
 *  [返却データ] <BR>
 *  <BR>
 *    <DIR>
 *       荷主コード <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理（<CODE>btn_View_Click</CODE>メソッド）<BR>
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
 *     開始出荷予定日 <BR>
 * 	   終了出荷予定日 <BR>
 *   </DIR>
 * <BR>
 *    [返却データ] <BR>
 * <BR>
 *    <DIR>
 *     各メッセージ <BR>
 *     選択チェックBoxの有効／無効 <BR>
 *     出荷予定日 <BR>
 *     出荷先コード <BR>
 *     出荷先名 <BR>
 *    </DIR>
 * </DIR>
 * 3.削除ボタン押下処理（<CODE>btn_Delete_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容を全データをパラメータとして渡します。 <BR>
 *   それにより削除処理を行います。メッセージを取得し表示します。 <BR>
 * <BR>
 *   [パラメータ] *必須入力 <BR>
 * <BR>
 *   <DIR>
 *     作業者コード * <BR>
 *     パスワード * <BR>
 *     荷主コード  <BR>
 *     開始出荷予定日  <BR>
 *     終了出荷予定日  <BR>
 *     選択チェック条件  <BR>
 *     出荷予定日  <BR>
 *     出荷先コード  <BR>
 *     出荷先名称  <BR>
 *     リスト発行要求  <BR>
 *     ためうち行NO <BR>
 *     <BR>
 *   </DIR>
 * <BR>
 *    [返却データ] <BR>
 * <BR>
 *    <DIR>
 *     各メッセージ <BR>
 *     選択チェックBoxの有効／無効 <BR>
 *     出荷予定日 <BR>
 *     出荷先コード <BR>
 *     出荷先名 <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/24</TD><TD>B.Shibayama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */
public class ShippingPlanDeleteBusiness extends ShippingPlanDelete implements WMSConstants
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
	 *    1.タイトルを表示します。<BR>
	 *    2.入力エリアを初期設定します。<BR>
	 *    3.スケジュールを開始します。 <BR>
	 *    4.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを無効にします。
	 *    5.カーソルを作業コードにセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 作業者コード[なし] <BR>
	 * パスワード[なし] <BR>
	 * 荷主コード[検索値] <BR>
	 * 開始出荷予定日[なし] <BR>
	 * 終了出荷予定日[なし] <BR>
	 * 出荷予定削除一覧リスト発行する[true] <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{		
		// 各入力フィールドに初期値をセットします。
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtShpPlanDate.setText("");
		txt_EdShpPlanDate.setText("");
		chk_CommonUse.setChecked(true);

		// ためうち部のボタンを無効にする
		setBtnTrueFalse(false);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

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
			//ヘルプファイルへのパスをセットする
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		// ダイアログを表示します MSG-W0007=削除しますか？
		btn_Delete.setBeforeConfirm("MSG-W0007");
		// ダイアログを表示します MSG-W0012=一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
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
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		Date startshippingplandate = 
		          WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY ),this.getHttpRequest().getLocale());
		Date endshippingplandate =
			      WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY),this.getHttpRequest().getLocale());
		String consignorcode = param.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);

		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 開始出荷予定日
		if (!StringUtil.isBlank(startshippingplandate))
		{
			txt_StrtShpPlanDate.setDate(startshippingplandate);
		}
		// 終了出荷予定日
		if (!StringUtil.isBlank(endshippingplandate))
		{
			txt_EdShpPlanDate.setDate(endshippingplandate);
		}
		
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * スケジュールから荷主コードを取得するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。<BR>
	 * <DIR>
	 *   1.荷主コードが1件しかない場合該当荷主を返します。そうでない場合はnullを返します。 <BR>
	 * <DIR>
	 * @return 荷主コード
	 * @throws Exception 全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクションを取得します。
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			// パラメータの宣言
			ShippingParameter param = new ShippingParameter();

			// スケジュールから荷主コードを取得します。
			WmsScheduler schedule = new ShippingPlanDeleteSCH();
			// スケジュールを開始します。
			param = (ShippingParameter) schedule.initFind(conn, param);

			// paramにデータがあります。
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
			try
			{
				// コネクションのクローズを行います。
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;

	}

	/**
	 * ボタンの編集可否を設定するメソッドです。<BR>
	 * <BR>
	 * 概要：false or trueを受け取ってボタンの編集可否を設定します。<BR>
	 * @param  status(false or true)
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		// ためうち部のボタン
		btn_AllCheck.setEnabled(arg);
		btn_AllCheckClear.setEnabled(arg);
		btn_Delete.setEnabled(arg);
		btn_ListClear.setEnabled(arg);
	}

	/**
	 * ためうちエリアに検索結果をマッピングするためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した検索結果をためうちエリアに表示するのに使用します。<BR>
	 * ためうちエリアに荷主コード、荷主名称、出荷予定日とリストセルを表示します。<BR>
	 * <BR>
	 * リストセルの列番号一覧
	 * <DIR>
	 * 1：選択box <BR>
	 * 2：出荷予定日 <BR>
	 * 3：出荷先コード <BR>
	 * 4：出荷先名称 <BR>
	 * </DIR>
	 *
	 * @param param ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void addList(ShippingParameter[] param) throws Exception
	{
		// ためうちエリアに検索結果を表示します。
		// 荷主
		txt_SRConsignorCode.setText(this.getViewState().getString("CONSIGNOR_CODE"));
		txt_SRConsignorName.setText(param[0].getConsignorName());

		// リストセルに値をセット
		for (int i = 0; i < param.length; i++)
		{
			// リストセルに行を追加
			lst_SShippingPlanDtaDlt.addRow();
			// 値をセットする行を選択します
			lst_SShippingPlanDtaDlt.setCurrentRow(i + 1);

			// 出荷予定日
			lst_SShippingPlanDtaDlt.setValue(2, WmsFormatter.toDispDate(param[i].getPlanDate(), this.getHttpRequest().getLocale()));
			// 出荷先コード
			lst_SShippingPlanDtaDlt.setValue(3, param[i].getCustomerCode());
			// 出荷先名称
			lst_SShippingPlanDtaDlt.setValue(4, param[i].getCustomerName());
		}
	}

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
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：メニュー画面に遷移します。
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		// メニュー画面に遷移します。
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
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 *  画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 *  該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     </DIR>
	 * <BR>
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 予定検索
		param.setParameter(
			ListShippingConsignorBusiness.SEARCHCONSIGNOR_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);

        // 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingconsignor/ListShippingConsignor.do",
			param,
			"/progress.do");
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_StrtShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StrtShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StrtShpPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_StrtShpPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchStart_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 開始出荷予定日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷予定日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStart_Click(ActionEvent e) throws Exception
	{
		//	開始出荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 開始出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));

        // 開始フラグをセット
		param.setParameter(
			ListShippingPlanDateBusiness.RANGESHIPPINGPLANDATE_KEY,
			ShippingParameter.RANGE_START);
			
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
        // 作業状態をセット
		param.setParameter(ListShippingPlanDateBusiness.WORKSTATUSSHIPPINGPLANDATE_KEY, search);
		
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingplandate/ListShippingPlanDate.do",
			param,
			"/progress.do");
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_W_EdShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EdShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EdShpPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_EdShpPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchEnd_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 終了出荷日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     終了出荷日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEnd_Click(ActionEvent e) throws Exception
	{
		// 終了出荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 終了出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
		
		// 終了フラグをセット
		param.setParameter(
			ListShippingPlanDateBusiness.RANGESHIPPINGPLANDATE_KEY,
			ShippingParameter.RANGE_END);
		
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
        // 作業状態をセット
		param.setParameter(ListShippingPlanDateBusiness.WORKSTATUSSHIPPINGPLANDATE_KEY, search);	
		
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingplandate/ListShippingPlanDate.do",
			param,
			"/progress.do");
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ShpPlanDltLst_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアの入力項目を条件に、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *     1.カーソルを作業者コードにセットします。
	 *     2.入力エリアの入力項目のチェックを行います。 <BR>
	 *       <DIR>
	 *      （必須入力、文字数、文字属性） <BR>
	 *       (開始出荷日、終了出荷日より小さい) <BR>
	 *       </DIR>
	 *     3.スケジュールを開始します。 <BR>
	 *     4.スケジュールの結果をためうちエリアに表示を行います。 <BR>
	 *     5.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを有効にします。 <BR>
	 *     6.入力エリアの条件内容を保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>
	 *     1:選択 <BR>
	 *     2:出荷予定日 <BR>
	 *     3:出荷先コード <BR>
	 *     4:出荷先名称 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
        // ため打ち部分をすべて初期化します
		btn_ListClear_Click(e);
		
		// 入力チェックを行う（書式、必須、禁止文字）
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();

		// 開始出荷予定日が終了出荷予定日より小さいこと
		if (!StringUtil.isBlank(txt_StrtShpPlanDate.getDate())
			&& !StringUtil.isBlank(txt_EdShpPlanDate.getDate()))
		{
			if (txt_StrtShpPlanDate.getDate().after(txt_EdShpPlanDate.getDate()))
			{
				// 6023045 = 開始出荷予定日は、終了出荷予定日より前の日付にしてください。
				message.setMsgResourceKey("6023045");
				return;
			}
		}

		Connection conn = null;

		try
		{
			/* スケジュールを開始する */
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// パラメータの宣言
			ShippingParameter param = new ShippingParameter();

			// スケジュールパラメータをセットします。
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 開始出荷予定日
			param.setFromPlanDate(WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
			// 終了出荷予定日
			param.setToPlanDate(WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));

			// スケジュールの宣言
			WmsScheduler schedule = new ShippingPlanDeleteSCH();
            // スケジュールを開始する
			ShippingParameter[] viewParam = (ShippingParameter[]) schedule.query(conn, param);

			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了します。
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// ViewStateに検索値を保持します。 
			// 荷主コード
			this.getViewState().setString("CONSIGNOR_CODE", txt_ConsignorCode.getText());
			// 開始出荷予定日
			this.getViewState().setString("FROM_SHIPPING_DATE", param.getFromPlanDate());
			// 終了出荷予定日
			this.getViewState().setString("TO_SHIPPING_DATE", param.getToPlanDate());

			// ためうちに値を表示します。
			addList(viewParam);

			// ためうちのボタンを有効にします。
			setBtnTrueFalse(true);
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			// エラー発生時トランザクションをロールバックする。
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
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
	 * クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。 <BR>
	 * </BR>
	 * <DIR>
	 *     1.入力エリアの項目をクリアします。 <BR>
	 *     <DIR>
	 *         作業者コード[そのまま] <BR>
	 *         パスワード[そのまま] <BR>
	 *         荷主コード[検索値] <BR>
	 *         開始出荷予定日[クリア] <BR>
	 *         終了出荷予定日[クリア] <BR>
	 *         出荷予定削除一覧リスト発行する[ture] <BR>
	 *     </DIR>
	 *     2.カーソルを作業者コードにセットします。 <BR>
	 *     3.ためうちエリアの内容は保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		//荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		txt_StrtShpPlanDate.setText("");
		txt_EdShpPlanDate.setText("");
		chk_CommonUse.setChecked(true);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 全件選択ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの選択チェックボックスに全てチェックを入れます。 <BR>
	 * </BR>
	 * <DIR>
	 *     1.選択boxが有効のものにチェックを入れます。 <BR>
	 *     2.カーソルを作業者コードにセットします。 <BR>
	 *     3.入力エリアの内容は保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		// 全リストセル分チェックします // FTTB

		// 選択boxが有効のものにチェックを入れます。
		for (int i = 1; i < lst_SShippingPlanDtaDlt.getMaxRows(); i++)
		{
			// 作業行を選択します
			lst_SShippingPlanDtaDlt.setCurrentRow(i);
			lst_SShippingPlanDtaDlt.setChecked(1, true);
		}

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 全選択解除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの選択チェックボックスに全てチェックをクリアします。 <BR>
	 * </BR>
	 * <DIR>
	 *     1.選択boxが有効のもののチェックをクリアします。 <BR>
	 *     2.カーソルを作業者コードにセットします。 <BR>
	 *     3.入力エリアの内容は保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		// 選択boxが有効のものにチェックを入れます。
		for (int i = 1; i < lst_SShippingPlanDtaDlt.getMaxRows(); i++)
		{
			// 作業行を選択します
			lst_SShippingPlanDtaDlt.setCurrentRow(i);
			lst_SShippingPlanDtaDlt.setChecked(1, false);
		}
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 削除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、出荷予定情報の削除を行います。 <BR>
	 * </BR>
	 * <DIR>
	 *     1.カーソルを作業者コードにセットします。 <BR>
	 *     2.viewStateから値を取得してパラメータにセットします。<BR>
	 *     3.ためうちエリアの全情報をパラメータにセットします。 <BR>
	 *     4.スケジュールを開始します。 <BR>
	 *     5.ためうちエリアに、更新後のスケジュールからの情報を再取込みし、表示します。 <BR>
	 *     6.ためうち情報が存在しない場合、全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンは無効にします。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

		// 作業者コード,パスワードの入力チェックを行う。
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 削除するデータがあるか調べる
			boolean wCheck = false;

			// ためうちの要素数分チェックをかけ、更新を行う行のみVectorに値をセットしていく。
			Vector vecParam = new Vector(this.lst_SShippingPlanDtaDlt.getMaxRows());
			
			for (int i = 1; i < this.lst_SShippingPlanDtaDlt.getMaxRows(); i++)
			{
				
				// 操作する行を取得します。
				lst_SShippingPlanDtaDlt.setCurrentRow(i);

				// 選択チェック条件なしの場合、修正しない。
				if (!lst_SShippingPlanDtaDlt.getChecked(1))		continue;
				wCheck = true;

				ShippingParameter param = new ShippingParameter();
				// 作業者コード
				param.setWorkerCode(txt_WorkerCode.getText());
				// パスワードのセット
				param.setPassword(txt_Password.getText());
				// 出荷予定削除一覧リスト発行をするかをセット
				param.setStorageListFlg(chk_CommonUse.getChecked());

				// 再表示用の検索条件のセット(荷主、開始出荷予定日、終了出荷予定日)
				param.setConsignorCode(this.getViewState().getString("CONSIGNOR_CODE"));
				param.setFromPlanDate(this.getViewState().getString("FROM_SHIPPING_DATE"));
				param.setToPlanDate(this.getViewState().getString("TO_SHIPPING_DATE"));

				// 選択チェック条件
				param.setSelectBoxCheck(lst_SShippingPlanDtaDlt.getChecked(1));
				// 出荷予定日
				param.setPlanDate(WmsFormatter.toParamDate(lst_SShippingPlanDtaDlt.getValue(2), this.getHttpRequest().getLocale()));
				// 出荷先コード
				param.setCustomerCode(lst_SShippingPlanDtaDlt.getValue(3));
				// 出荷先名称
				param.setCustomerName(lst_SShippingPlanDtaDlt.getValue(4));
				// ためうち行NO
				param.setRowNo(i);

				vecParam.addElement(param);
			}

			// 選択ボックスのチェックが入っていない
			if (wCheck == false)
			{
				// 6023111 = 削除するデータをチェックしてください。
				message.setMsgResourceKey("6023111");
				return;
			}

			// パラメータの宣言
			ShippingParameter[] paramArray = new ShippingParameter[vecParam.size()];
            // パラメータに値をコピーします
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			WmsScheduler schedule = new ShippingPlanDeleteSCH();

			ShippingParameter[] viewParam =
				(ShippingParameter[]) schedule.startSCHgetParams(conn, paramArray);

			// 返却データがnullならば更新に失敗。
			// ロールバックとメッセージのセットを行う。(ためうちは前データを表示したまま)
			if (viewParam == null)
			{
				// ロールバック
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			// 返却データの要素数が0以上ならば更新に成功。
			// トランザクションの確定とためうちの再表示を行う。
			else if (viewParam.length >= 0)
			{
				// コミット 
				conn.commit();

				// 表示するデータがない場合
				if (viewParam.length == 0)
				{
					// ためうち部をクリアします
					btn_ListClear_Click(e);
				}
				else
				{
					// スケジュールが正常に完了して表示データを取得した場合、表示します。
					lst_SShippingPlanDtaDlt.clearRow();
					// ためうちに再表示します
					addList(viewParam);
				}

			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			// エラー発生時トランザクションをロールバックする。
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
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
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 一覧クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの表示をクリアするか、ダイアログボックスを表示し、確認します。 <BR>
	 * </BR>
	 * <DIR>
	 *     1.確認ダイアログボックスを表示します。"一覧入力情報がクリアされます。宜しいですか?" <BR>
	 *       [確認ダイアログ OK] <BR>
	 *       <DIR>
	 *         1.リストセルを初期化します。 <BR>
	 *         2.ためうちエリアの条件を消します。 <BR>
	 *         3.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを無効にします。 <BR>
	 *         4.カーソルを作業者コードにセットします。 <BR>
	 *       </DIR>
	 *       [確認ダイアログ キャンセル] <BR>
	 *       <DIR>
	 *          なにもしません <BR>
	 *       </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行情報を全て削除します。
		lst_SShippingPlanDtaDlt.clearRow();
		// ためうちエリアの荷主部分を消す
		txt_SRConsignorCode.setText("");
		txt_SRConsignorName.setText("");
		// ためうち部のボタンを無効にします。
		setBtnTrueFalse(false);
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_SConsignor_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_ColumClick(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lst_SShippingPlanDtaDlt_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

}
//end of class

// $Id: ShippingInspectionBusiness.java,v 1.2 2006/11/10 00:38:32 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippinginspection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingcustomer.ListShippingCustomerBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingInspectionSCH;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * 出荷検品設定画面クラスです。<BR>
 * 出荷検品設定を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが表示用のデータを検索します。<BR>
 *   スケジュールからためうちエリア出力用のデータを受け取り、ためうちエリアに出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <DIR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   出荷予定日* <BR>
 *   出荷先コード* <BR>
 *   商品コード <BR>
 *   表示順* <BR>
 *   </DIR>
 *   <BR>
 *   [出力用のデータ] <BR>
 *   <DIR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   出荷予定日 <BR>
 *   出荷先コード <BR>
 *   出荷先名称 <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   表示順 <BR>
 *   出荷伝票No. <BR>
 *   出荷伝票行No. <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   作業予定ケース数 <BR>
 *   作業予定ピース数 <BR>
 *   出荷ケース数 <BR>
 *   出荷ピース数 <BR>
 *   作業No. <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_Submit_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが出荷検品設定を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力 +どちらか必須入力 <BR>
 *   <DIR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   ケース入数 <BR>
 *   作業予定ケース数 <BR>
 *   作業予定ピース数 <BR>
 *   出荷ケース数+ <BR>
 *   出荷ピース数+ <BR>
 *   欠品フラグ <BR>
 *   賞味期限 <BR>
 *   行No. <BR>
 *   作業No. <BR>
 *   最終更新日時 <BR>
 *   </DIR> 
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>Y.Kubo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:38:32 $
 * @author  $Author: suresh $
 */
public class ShippingInspectionBusiness extends ShippingInspection implements WMSConstants
{
	// Class fields --------------------------------------------------
	// 商品コードを保持するためのキー
	private final static String ITEMCODE = "ITEM_CODE";

	// 表示順(伝票No.順)を保持するためのキー
	private final static String DISPTICKET = "DISP_TICKET";

	// 表示順(商品コード順)を保持するためのキー
	private final static String DISPITEM = "DISP_ITEM";
	
	// 作業No.を保持するためのキー
	private final static String JOBNO = "JOB_NO";
	 
	// 最終更新日時を保持するためのキー
	private final static String LASTUPDATE = "LASTUP_DATE";
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * <DIR>
	 * 	 1.フォーカスを作業者コードに初期セットします。<BR>
	 * 	 <BR>
	 * </DIR>
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
		// フォーカスを作業者コードにセットする
		setFocus(txt_WorkerCode);

		// MSG-W0009 = 登録しますか？
		btn_Submit.setBeforeConfirm("MSG-W0009");
		// MSG-W0012 = 一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。クリアボタン押下時もこの処理を行います。
	 * <BR>
	 * <DIR>
	 *   1.登録ボタン・出荷数クリアボタン・一覧クリアボタンを無効にします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// ラジオボタンのデフォルト
			rdo_TicTkt.setChecked(true);
			rdo_ItemCode.setChecked(false);
			
			// 出荷数初期表示設定チェックボックス
			chk_CommonUse.setChecked(false);
			
			// ボタン押下を不可にする
			// 登録ボタン
			btn_Submit.setEnabled(false);
			// 出荷数クリアボタン
			btn_ShippingQtyClear.setEnabled(false);
			// 一覧クリアボタン
			btn_ListClear.setEnabled(false);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingInspectionSCH();
			ShippingParameter param = (ShippingParameter) schedule.initFind(conn, null);

			// 荷主コードが1件の場合、初期表示させる。
			if( param != null )
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
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
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @param rowNo チェックを行う行No.
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		
		WmsCheckker checker = new WmsCheckker();

		lst_SShpIsp.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SShpIsp.getValue(7) ,
				rowNo,
				lst_SShpIsp.getListCellColumn(7).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
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
	 * 概要:入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *   1.画面の初期化を行います。<BR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			txt_ConsignorCode.setText("");
			txt_ShippingPlanDate.setText("");
			txt_CustomerCode.setText("");
			txt_ItemCode.setText("");
			// チェックボックスのデフォルト
			chk_CommonUse.setChecked(false);

			// ラジオボタンのデフォルト
			rdo_TicTkt.setChecked(true);
			rdo_ItemCode.setChecked(false);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingInspectionSCH();
			ShippingParameter param = (ShippingParameter) schedule.initFind(conn, null);

			// 荷主コードが1件の場合、初期表示させる。
			if( param != null )
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
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
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。<BR>
	 * Pageに定義されているpage_DlgBackをオーバライドします。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);
		
		// 出荷予定日
		Date shippingplandate = WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY)
												 ,this.getHttpRequest().getLocale());
		
		// 出荷先コード
		String customercode = param.getParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY);
		
		// 商品コード
		String itemcode = param.getParameter(ListShippingItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(shippingplandate))
		{
			txt_ShippingPlanDate.setDate(shippingplandate);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
	}

	/** 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchConsignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * 荷主一覧リストボックスを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_SearchConsignor_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		
		// 検索フラグ(予定データ)
		param.setParameter(ListShippingConsignorBusiness.SEARCHCONSIGNOR_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		param.setParameter(ListShippingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingconsignor/ListShippingConsignor.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * 出荷予定日一覧リストボックスを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_SearchDate_Click(ActionEvent e) throws Exception
	{
		// 出荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		// Date型からString型(YYYYMMDD)への変換を行います。
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		param.setParameter(ListShippingPlanDateBusiness.WORKSTATUSSHIPPINGPLANDATE_KEY, search);
		
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingplandate/ListShippingPlanDate.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchCustomer_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * 出荷先一覧リストボックスを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_SearchCustomer_Click(ActionEvent e) throws Exception
	{
		// 出荷先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		// Date型からString型(YYYYMMDD)への変換を行います。
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コード
		param.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		
		// 検索フラグ(予定データ)
		param.setParameter(ListShippingCustomerBusiness.SEARCHCUSTOMER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		param.setParameter(ListShippingCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
		
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingcustomer/ListShippingCustomer.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchItem_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * 商品一覧リストボックスを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_SearchItem_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		// Date型からString型(YYYYMMDD)への変換を行います。
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コード
		param.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 商品コード
		param.setParameter(ListShippingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		
		// 検索フラグ(予定データ)
		param.setParameter(ListShippingItemBusiness.SEARCHITEM_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		param.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY, search);
		
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingitem/ListShippingItem.do", param, "/progress.do");
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
	 * 概要:入力エリアの入力項目を条件に、出荷予定情報を検索し、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力エリアの入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   2.スケジュールを開始します。<BR>
	 *   3.ためうちエリアに表示を行います。<BR>
	 *   <BR>
	 *     <DIR>
	 *     ･出荷数の初期入力を行うにチェックあり:作業者入力領域に予定数をセットします。<BR>
	 *     <BR>
	 *     ･出荷数の初期入力を行うにチェックなし:作業者入力領域にNULLをセットします。<BR>
	 *     </DIR>
	 *   <BR>
	 *   4.登録ボタン・出荷数クリアボタン・一覧クリアボタンを有効にします。<BR>
	 *   5.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   [リストセルの列番号一覧]<BR>
	 *   <BR>
	 *   <DIR>
	 *   1：伝票No.<BR>
	 *   2：商品コード<BR>
	 *   3：ケース入数<BR>
	 *   4：予定ケース数<BR>
	 *   5：出荷ケース数<BR>
	 *   6：欠品<BR>
	 *   7：賞味期限<BR>
	 *   8：行№<BR>
	 *   9：商品名称<BR>
	 *   10：ボール入数<BR>
	 *   11：予定ピース数<BR>
	 *   12：出荷ピース数<BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// 入力エラーの場合、ためうちエリアをクリアする。
			btn_ListClear_Click(e);

			// 入力チェック
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			txt_ShippingPlanDate.validate();
			txt_CustomerCode.validate();
			// パターンマッチング文字
			txt_ItemCode.validate(false);

			// スケジュールパラメータへセット
			ShippingParameter param = new ShippingParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 出荷予定日
			// Date型からString型(YYYYMMDD)への変換を行います。
			param.setPlanDate(WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
			// 出荷先コード
			param.setCustomerCode(txt_CustomerCode.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 表示順
			// 伝票No.順
			if (rdo_TicTkt.getChecked())
			{
				param.setDspOrder(ShippingParameter.DSPORDER_TICKET);
				rdo_TicTkt.setChecked(true);
				rdo_ItemCode.setChecked(false);
			}
			// 商品コード順
			else if (rdo_ItemCode.getChecked())
			{
				param.setDspOrder(ShippingParameter.DSPORDER_ITEM);
				rdo_TicTkt.setChecked(false);
				rdo_ItemCode.setChecked(true);
			}
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingInspectionSCH();
			ShippingParameter[] viewParam = (ShippingParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// リストセルのセット
			setList(viewParam);

			// ViewStateへ保持する
			// 商品コード
			this.getViewState().setString(ITEMCODE, txt_ItemCode.getText());
			// 表示順
			this.getViewState().setBoolean(DISPTICKET, rdo_TicTkt.getChecked());
			this.getViewState().setBoolean(DISPITEM, rdo_ItemCode.getChecked());
			
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
					conn.close();
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
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、出荷先別出荷検品を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力領域チェックを行います。<BR>
	 *     <DIR>
	 *     <BR>
	 *     ･数量入力領域に入力がない(null)場合:"更新対象データがありません。"のメッセージを表示します。<BR>
	 *     <BR>
	 *     ･入力がある場合:登録確認ダイアログボックスを表示します。"出荷確定しますか?"<BR>
	 *     <BR>
	 *     [確認ダイアログ キャンセル]<BR>
	 *     <BR>
	 *       <DIR>
	 *       なにも行いません。<BR>
	 *       </DIR>
	 *     <BR>
	 *     [確認ダイアログ OK]<BR>
	 *     <BR>
	 *       <DIR>
	 *       1.ためうちエリアの入力項目のチェックを行います。<BR>
	 *       2.スケジュールを開始します。<BR>
	 *       3.ためうちエリアに、更新後の情報を再取込し、表示します。<BR>
	 *       </DIR>
	 *     </DIR>
	 *   <BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   [リストセルの列番号一覧]<BR>
	 *   <BR>
	 *   <DIR>
	 *   1：伝票No.<BR>
	 *   2：商品コード<BR>
	 *   3：ケース入数<BR>
	 *   4：予定ケース数<BR>
	 *   5：出荷ケース数<BR>
	 *   6：欠品<BR>
	 *   7：賞味期限<BR>
	 *   8：行№<BR>
	 *   9：商品名称<BR>
	 *   10：ボール入数<BR>
	 *   11：予定ピース数<BR>
	 *   12：出荷ピース数<BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		// 入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();

		// リストセルのタイトルを取得し、ListCellColumnに格納
		ArrayList lst = (ArrayList) lst_SShpIsp.getListCellColumns();
		ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
		for (int l = 0; l < lst.size(); l++)
		{
			ListCellColumn listtemp = (ListCellColumn) lst.get(l);
			List_Title[l + 1] = new ListCellColumn();
			List_Title[l + 1] = (ListCellColumn) listtemp;
		}

		// 最大行数を取得
		int index = lst_SShpIsp.getMaxRows();
		// マイナス入力のチェックを行う
		for (int i = 1; i < index; i++)
		{
			try
			{
				// 行指定
				lst_SShpIsp.setCurrentRow(i);
				// 指定した列(賞味期限)の必須入力以外の妥当性チェックを行う
				lst_SShpIsp.validate(7, false);

				// eWareNavi用入力文字チェック
				if (!checkContainNgText(i))
				{
					return;
				}

				// 出荷ｹｰｽ数、出荷ﾋﾟｰｽ数のマイナス値のチェックを行う
				String itemname = null;
				itemname = checkNumber(WmsFormatter.getInt(lst_SShpIsp.getValue(5)), List_Title[5]);
				if (StringUtil.isBlank(itemname))
				{
					itemname = checkNumber(WmsFormatter.getInt(lst_SShpIsp.getValue(12)), List_Title[12]);
				}
				else
				{
					itemname = Integer.toString(i) + " " + itemname;
					// エラーメッセージを表示し、終了する。
					// 6023162=No.{0}には{1}以上の値を入力してください。
					message.setMsgResourceKey("6023162" + wDelim + itemname + wDelim + "0");
					return;
				}
			}
			catch(ValidateException ve)
			{
				// メッセージをセット
				String errorMessage = MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}
		
		Connection conn = null;
		try
		{
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			String termNo = userHandler.getTerminalNo();
			
			Vector vecParam = new Vector(index);
			for (int i = 1; i < index; i++)
			{
				// 行指定
				lst_SShpIsp.setCurrentRow(i);
				
				// 出荷ケース数と出荷ピースが空白かつ"欠品"にチェックがない場合は更新対象外
				if (StringUtil.isBlank(lst_SShpIsp.getValue(5))
				&& StringUtil.isBlank(lst_SShpIsp.getValue(12))
				&& !lst_SShpIsp.getChecked(6))
				{
					continue;
				}

				// スケジュールパラメータへセット
				ShippingParameter param = new ShippingParameter();
				// 作業者コード
				param.setWorkerCode(txt_WorkerCode.getText());
				// パスワード
				param.setPassword(txt_Password.getText());
				// 荷主コード(読み取り専用)
				param.setConsignorCode(txt_RConsignorCode.getText());
				// 出荷予定日(読み取り専用)
				// Date型からString型(YYYYMMDD)への変換を行います。
				param.setPlanDate(WmsFormatter.toParamDate(txt_RShippingPlanDate.getDate()));
				// 出荷先コード(読み取り専用)
				param.setCustomerCode(txt_RCustomerCode.getText());
				// 商品コード(ViewState)
				param.setItemCode(this.getViewState().getString(ITEMCODE));
				// 表示順(ViewState)
				// 伝票No.順
				if (this.getViewState().getBoolean(DISPTICKET))
				{
					param.setDspOrder(ShippingParameter.DSPORDER_TICKET);
				}
				// 商品コード順
				else if (this.getViewState().getBoolean(DISPITEM))
				{
					param.setDspOrder(ShippingParameter.DSPORDER_ITEM);
				}
				
				param.setShippingTicketNo(lst_SShpIsp.getValue(1));
				param.setItemCodeL(lst_SShpIsp.getValue(2));
				// カンマ編集された数値から数値型への変換を行います。
				param.setEnteringQty(WmsFormatter.getInt(lst_SShpIsp.getValue(3)));
				param.setPlanCaseQty(WmsFormatter.getInt(lst_SShpIsp.getValue(4)));
				if (lst_SShpIsp.getValue(5).equals(""))
				{
					param.setResultCaseQty(0);
				}
				else
				{
					param.setResultCaseQty(WmsFormatter.getInt(lst_SShpIsp.getValue(5)));
				}
				param.setShortage(lst_SShpIsp.getChecked(6));
				param.setUseByDate(lst_SShpIsp.getValue(7));
				if(StringUtil.isBlank(lst_SShpIsp.getValue(8)))
				{
					param.setShippingLineNo(0);					
				}
				else
				{
					param.setShippingLineNo(Integer.parseInt(lst_SShpIsp.getValue(8)));					
				}
				param.setItemName(lst_SShpIsp.getValue(9));
				// カンマ編集された数値から数値型への変換を行います。
				param.setBundleEnteringQty(WmsFormatter.getInt(lst_SShpIsp.getValue(10)));
				param.setPlanPieceQty(WmsFormatter.getInt(lst_SShpIsp.getValue(11)));
				if (lst_SShpIsp.getValue(12).equals(""))
				{
					param.setResultPieceQty(0);
				}
				else
				{
					param.setResultPieceQty(WmsFormatter.getInt(lst_SShpIsp.getValue(12)));
				}

				// 作業No.
				param.setJobNo(CollectionUtils.getString(3, lst_SShpIsp.getValue(0)));
				// 最終更新日時
				// String型(YYYYMMDDHHMMSS)からDate型への変換を行います。
				Date lastupdate = WmsFormatter.getTimeStampDate(CollectionUtils.getString(4, lst_SShpIsp.getValue(0)));
				param.setLastUpdateDate(lastupdate);
				// 端末No
				param.setTerminalNumber(termNo);
				// 行No.
				param.setRowNo(i);

				vecParam.addElement(param);
			}
			
			// 更新対象がない場合終了
			if (vecParam.size() <= 0)
			{
				// 6023154 = 更新対象データがありません。
				message.setMsgResourceKey("6023154");
				return;
			}

			ShippingParameter[] paramArray = new ShippingParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			WmsScheduler schedule = new ShippingInspectionSCH();
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			ShippingParameter[] viewParam = (ShippingParameter[]) schedule.startSCHgetParams(conn, paramArray);

			// スケジュールに失敗した場合トランザクションをロールバック
			if (viewParam == null)
			{
				conn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			// スケジュールに成功した場合、トランザクションを確定
			else
			{
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
				// 対象データがなくなった場合、ためうちエリアをクリアする。
				btn_ListClear_Click(e);

				if (viewParam.length != 0)
				{
					// リストセルのセット
					setList(viewParam);
				}
			}
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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ShippingQtyClear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷数クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの出荷ケース/ピース数、欠品につけたチェックをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *   1.ためうちエリアの項目をクリアします。<BR>
	 *   2.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ShippingQtyClear_Click(ActionEvent e) throws Exception
	{
		// リストセルのセット
		for (int i = 1; i < lst_SShpIsp.getMaxRows(); i++)
		{
			lst_SShpIsp.setCurrentRow(i);
			lst_SShpIsp.setValue(5, (""));
			lst_SShpIsp.setChecked(6, false);
			lst_SShpIsp.setValue(12, (""));
				
			// リストセルに保持したパラメータをクリアする。
			// 出荷ケース数＋出荷ピース数＋欠品チェックあり＋作業No.＋最終更新日時
			List list = new Vector();
			list.add("");
			list.add("");
			list.add("false");
			// 作業No.(ViewState)
			list.add(this.getViewState().getString(JOBNO));
			// 最終更新日時(ViewState)
			list.add(this.getViewState().getString(LASTUPDATE));
			lst_SShpIsp.setValue(0, CollectionUtils.getConnectedString(list));
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
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 *   <DIR>
	 *   1.確認ダイアログボックスを表示します。<BR>
	 *   <BR>
	 *     <DIR>
	 *     [確認ダイアログ キャンセル]<BR>
	 *     <BR>
	 *       <DIR>
	 *       なにも行いません。<BR>
	 *       </DIR>
	 *     <BR>
	 *     [確認ダイアログ OK]<BR>
	 *     <BR>
	 *       <DIR>
	 *       1.登録ボタン・出荷数クリアボタン・一覧クリアボタンを無効にします。<BR>
	 *       2.入力エリアの内容は保持します。<BR>
	 *       </DIR>
	 *     </DIR>
	 *   <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行をすべて削除
		lst_SShpIsp.clearRow();

		// 荷主コード(読み取り専用)
		txt_RConsignorCode.setText("");
		// 荷主名称(読み取り専用)
		txt_RConsignorName.setText("");
		// 出荷予定日(読み取り専用)
		txt_RShippingPlanDate.setText("");
		// 出荷先コード(読み取り専用)
		txt_RCustomerCode.setText("");
		// 出荷先名称(読み取り専用)
		txt_RCustomerName.setText("");

		// ボタン押下を不可にする
		// 登録ボタン
		btn_Submit.setEnabled(false);
		// 出荷数クリアボタン
		btn_ShippingQtyClear.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * ため打ちエリアに値をセットします。
	 * @param param ためうちエリアにセットする値
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(Parameter[] listParams) throws Exception
	{
		// 行をすべて削除
		lst_SShpIsp.clearRow();

		ShippingParameter param = (ShippingParameter) listParams[0];

		// 荷主コード(読み取り専用)
		txt_RConsignorCode.setText(param.getConsignorCode());
		// 荷主名称(読み取り専用)
		txt_RConsignorName.setText(param.getConsignorName());

		// 出荷予定日(読み取り専用)
		// String型(YYYYMMDD)からDate型への変換を行います。
		txt_RShippingPlanDate.setDate(WmsFormatter.toDate(param.getPlanDate()));
		// 出荷先コード(読み取り専用)
		txt_RCustomerCode.setText(param.getCustomerCode());
		// 出荷先名称(読み取り専用)
		txt_RCustomerName.setText(param.getCustomerName());

		ShippingParameter[] viewParam = (ShippingParameter[]) listParams;
		
		String label_itemname = DisplayText.getText("LBL-W0103");
		
		for (int i = 0; i < viewParam.length; i++)
		{
			// 行追加
			lst_SShpIsp.setCurrentRow(i + 1);

			lst_SShpIsp.addRow();

			lst_SShpIsp.setValue(1, viewParam[i].getShippingTicketNo());
			lst_SShpIsp.setValue(2, viewParam[i].getItemCodeL());
            // 数値型からカンマ編集された文字列への変換を行います。
			lst_SShpIsp.setValue(3, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_SShpIsp.setValue(4, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));

			// 作業No.
			String jobno = viewParam[i].getJobNo();
			// 最終更新日時
			// Date型からString型(YYYYMMDDHHMMSS)への変換を行います。
			String lastupdate = WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate());
			
			// パラメータを連結して、連結したパラメータをリストセルに保持する。
			// パラメータと入力された値と違いがあるかどうかのチェックに使用する。
			// 出荷数の初期表示
			if(chk_CommonUse.getChecked())
			{
				// 出荷ケース数＋出荷ピース数＋欠品チェックあり＋作業No.＋最終更新日時
				List list = new Vector();
				list.add(WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				list.add(WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				list.add("true");
				list.add(jobno);
				list.add(lastupdate);
				lst_SShpIsp.setValue(0, CollectionUtils.getConnectedString(list));
				
				// 出荷ケース数、出荷ピース数に予定数をセット
				lst_SShpIsp.setValue(5, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				lst_SShpIsp.setValue(12, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			}
			else
			{
				// 出荷ケース数＋出荷ピース数＋欠品チェックなし＋作業No.＋最終更新日時
				List list = new Vector();
				list.add("");
				list.add("");
				list.add("false");
				list.add(jobno);
				list.add(lastupdate);
				lst_SShpIsp.setValue(0, CollectionUtils.getConnectedString(list));
				
				// 出荷ケース数、出荷ピース数に空白をセット
				lst_SShpIsp.setValue(5, (""));
				lst_SShpIsp.setValue(12, (""));
			}
			
			lst_SShpIsp.setChecked(6, viewParam[i].getShortage());
			lst_SShpIsp.setValue(7, viewParam[i].getUseByDate());
			lst_SShpIsp.setValue(8, Integer.toString(viewParam[i].getShippingLineNo()));
			lst_SShpIsp.setValue(9, viewParam[i].getItemName());
			lst_SShpIsp.setValue(10, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_SShpIsp.setValue(11, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			
			// ViewStateへ保持する
			// 作業No.
			this.getViewState().setString(JOBNO, jobno);
			// 最終更新日時
			this.getViewState().setString(LASTUPDATE, lastupdate);
			
			// tool tipをセット
			ToolTipHelper toolTip = new ToolTipHelper();
			// 商品名称
			toolTip.add(label_itemname, viewParam[i].getItemName());

			lst_SShpIsp.setToolTip(i+1, toolTip.getText());

		}

		// ボタン押下を可能にする
		// 登録ボタン
		btn_Submit.setEnabled(true);
		// 出荷数クリアボタン
		btn_ShippingQtyClear.setEnabled(true);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(true);

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
	 * メニューボタンが押された時に呼ばれます。
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
	public void btn_SearchWorker_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_SearchWorker_Click(ActionEvent e) throws Exception
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
	public void lbl_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicketNo_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void ID8_Server(ActionEvent e) throws Exception
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
	public void lst_SShpIsp_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpIsp_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpIsp_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpIsp_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpIsp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpIsp_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpIsp_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicTkt_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Customer_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RCustomerName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShippingPlanDateShp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RShippingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RShippingPlanDate_TabKey(ActionEvent e) throws Exception
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
	 * 値チェックを行うメソッドです。<BR>
	 * <BR>
	 * 値が0以上かをチェックします。<BR>
	 * 
	 * @param Num 	     値チェックするための値です。
	 * @param ListName   値チェックする項目名です。
	 * @return itemName  値が1以上じゃなかった場合項目名を返します。
	 * @throws Exception 全ての例外を報告します。
	 */
	private String checkNumber(int Num, ListCellColumn ListName) throws Exception
	{
		String itemName = null;

		// 0以上か
		if (!StringUtil.isBlank(Integer.toString(Num)))
		{
			if (Num < 0)
			{
				// itemNameに項目名をセット
				itemName = DisplayText.getText(ListName.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

}
//end of class

// $Id: MasterRetrievalPlanRegistBusiness.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterretrievalplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.schedule.MasterRetrievalPlanRegistSCH;
import jp.co.daifuku.wms.master.schedule.MasterRetrievalSupportParameter;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalconsignor.ListRetrievalConsignorBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalcustomer.ListRetrievalCustomerBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalitem.ListRetrievalItemBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievallocation.ListRetrievalLocationBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalorderno.ListRetrievalOrdernoBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplandate.ListRetrievalPlanDateBusiness;
import jp.co.daifuku.wms.retrieval.display.web.listbox.listretrievalplanregist.ListRetrievalPlanRegistBusiness;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * 出庫予定登録クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが出庫予定登録をします。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.入力ボタン押下処理（<CODE>btn_Input_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *  結果がtrueの時は入力エリアの情報をためうちエリアに追加します。<BR>
 *  修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *  <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主コード* <BR>
 * 		荷主名称 <BR>
 * 		出庫予定日* <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		オーダーNo <BR>
 * 		出荷先コード*2 <BR>
 * 		出荷先名称*2 <BR>
 * 		伝票No <BR>
 * 		行No <BR>
 * 		ケース入数*3 <BR>
 * 		ボール入数 <BR>
 * 		ホスト予定ケース数*1 *3<BR>
 * 		ホスト予定ピース数*1 *4<BR>
 * 		ケースITF <BR>
 * 		ボールITF <BR>
 * 		ロケーションNo <BR>
 * 		ケースピース区分* <BR>
 *		<BR>
 * 		*1 <BR>
 * 		いずれか選択必須入力 <BR>
 * 		*2 <BR>
 * 		オーダーNo.入力時、必須入力 <BR>
 * 		*3 <BR>
 * 		ケース/ピース区分でケース選択時、必須入力 <BR>
 * 		*4 <BR>
 * 		ケース/ピース区分でピース選択時、必須入力 <BR>

 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	荷主コード <BR>
 *  	荷主名称 <BR>
 *  	出庫予定日 <BR>
 *  	商品コード <BR>
 *  	商品名称 <BR>
 * 		オーダーNo. <BR>
 * 		出荷先コード <BR>
 * 		出荷先名称 <BR>
 * 		伝票No. <BR>
 * 		行No. <BR>
 *  	ケース入数 <BR>
 *  	ボール入数 <BR>
 *  	ホスト予定ケース数 <BR>
 *  	ホスト予定ピース数 <BR>
 *  	ケースITF <BR>
 *  	ボールITF <BR>
 *  	出庫棚 <BR>
 *  	区分 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_Submit_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、<BR>
 *  そのパラメータをもとにスケジュールが出庫予定登録を行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *  結果がtrueの時はためうちの登録情報をクリアします。<BR>
 *  <BR>
 * 	<DIR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主コード* <BR>
 * 		荷主名称* <BR>
 * 		出庫予定日* <BR>
 * 		商品コード* <BR>
 * 		商品名称* <BR>
 * 		オーダーNo* <BR>
 * 		出荷先コード* <BR>
 * 		出荷先名称* <BR>
 * 		伝票No* <BR>
 * 		行No* <BR>
 * 		ケース入数* <BR>
 * 		ボール入数* <BR>
 * 		予定ケース数* <BR>
 * 		予定ピース数* <BR>
 * 		ケースITF* <BR>
 * 		ボールITF* <BR>
 * 		ロケーションNo* <BR>
 * 		ケースピース区分* <BR>
 * 		端末No.* <BR>
 *  </DIR>
 *  <BR>
 *  <DIR>
 *   リストセルの列番号一覧<BR>
 * 		<DIR>
 * 		0.ケース/ピース区分(HIDDEN) <BR>
 * 		1.取消ボタン <BR>
 * 		2.修正ボタン <BR>
 * 		3.荷主コード <BR>
 * 		4.出庫予定日 <BR>
 * 		5.商品コード <BR>
 * 		6.オーダーNo. <BR>
 * 		7.出荷先コード <BR>
 * 		8.伝票No. <BR>
 * 		9.ケース入数 <BR>
 * 		10.ホスト予定ケース数 <BR>
 * 		11.ケースITF <BR>
 * 		12.出庫棚 <BR>
 * 		13.区分 <BR>
 * 		14.荷主名称 <BR>
 * 		15.商品名称 <BR>
 * 		16.出荷先名称 <BR>
 * 		17.行No. <BR>
 * 		18.ボール入数 <BR>
 * 		19.ホスト予定ピース数 <BR>
 * 		20.ボールITF <BR>
 * 		</DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterRetrievalPlanRegistBusiness extends MasterRetrievalPlanRegist implements WMSConstants
{
	// Class fields --------------------------------------------------

	// 遷移先アドレス
	/**
	 * 荷主検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_CONSIGNOR =
		"/master/listbox/listmasterconsignor/ListMasterConsignor.do";

	/**
	 * 出庫予定日検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_RETRIEVALPLANDATE =
		"/retrieval/listbox/listretrievalplandate/ListRetrievalPlanDate.do";

	/**
	 * 商品一覧検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_ITEM =
		"/master/listbox/listmasteritem/ListMasterItem.do";

	/**
	 * 出庫棚一覧検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_LOCATION =
		"/retrieval/listbox/listretrievallocation/ListRetrievalLocation.do";

	/**
	 * オーダーNo.一覧検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_ORDER =
		"/retrieval/listbox/listretrievalorderno/ListRetrievalOrderno.do";

	/**
	 * 出荷先一覧検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_CUSTOMER =
		"/master/listbox/listmastercustomer/ListMasterCustomer.do";

	/**
	 * 出庫予定検索ポップアップアドレス
	 */
	protected static final String DO_SEARCH_RETRIEVALPLAN =
		"/retrieval/listbox/listretrievalplanregist/ListRetrievalPlanRegist.do";

	/**
	 * 検索ポップアップ呼び出し中画面アドレス
	 */
	protected static final String DO_SEARCH_PROCESS = "/progress.do";

	/**
	 * ViewState用：ためうち行No.
	 */
	protected static final String LINENO = "LINENO";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ViewStateのためうち行No.に初期値:-1を設定します。<BR>
	 *    2.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 *    3.カーソルを作業者コードに初期セットします。<BR>
	 * <BR>
	
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 *       荷主コード[出荷予定情報の未作業荷主が1件の場合、荷主コードを初期表示します。]<BR>
	 *   	 荷主名称[出荷予定情報の未作業荷主が1件の場合、荷主名称を初期表示します。]<BR>
	 * 		 その他項目[なし] <BR>
	 * 		 ケース/ピース区分[ケース] <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセットします
		// (デフォルト:-1)
		this.getViewState().setInt(LINENO, -1);

		// 入力エリアの初期化をします
		// (作業者コード、パスワードクリア)
		inputDataClear(true);
		// ボタン押下を不可にする
		setBtnTrueFalse(false);
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * 概要:確認ダイアログを表示します。<BR>
	 * <BR>
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
		// 登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。<BR>
	 * <CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。<BR>
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
		// 荷主コード
		String consignorcode = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListRetrievalConsignorBusiness.CONSIGNORNAME_KEY);
		// 出庫予定日
		Date retrievalplandate =
			WmsFormatter.toDate(param.getParameter
				(ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY));
		// 商品コード
		String itemcode = param.getParameter(ListRetrievalItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListRetrievalItemBusiness.ITEMNAME_KEY);
		// 出荷先コード
		String customercode = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY);
		// 出荷先名称
		String customername = param.getParameter(ListRetrievalCustomerBusiness.CUSTOMERNAME_KEY);
		// オーダーNo.
		String orderno = "";
		// 出庫棚No.
		String location = "";
		// ケース／ピースフラグ
		String casepieceflag = param.getParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY);
		// ケース入数
		String caseetr = param.getParameter(ListRetrievalPlanRegistBusiness.CASEETR_KEY);
		// ボール入数
		String bundleetr = param.getParameter(ListRetrievalPlanRegistBusiness.BUNDLEETR_KEY);
		// ケースITF
		String caseitf = param.getParameter(ListRetrievalPlanRegistBusiness.CASEITF_KEY);
		// ボールITF
		String bundleitf = param.getParameter(ListRetrievalPlanRegistBusiness.BUNDLEITF_KEY);

		// 出庫棚セット
		// 指定なし
		if (param.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY) != null)
		{
			location = param.getParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY);		
		// ケース
		}
		else if(param.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY) != null)
		{
			location = param.getParameter(ListRetrievalLocationBusiness.CASERETRIEVALLOCATION_KEY);					
		}
		else if(param.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY) != null)
		{
			location = param.getParameter(ListRetrievalLocationBusiness.PIECERETRIEVALLOCATION_KEY);		
		}
		// オーダーNo.セット
		if (param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY) != null)
		{
			orderno = param.getParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY);	
		}
		else if (param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY) != null)
		{
			orderno = param.getParameter(ListRetrievalOrdernoBusiness.CASEORDERNO_KEY);	
		}
		else if (param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY) != null)
		{
			orderno = param.getParameter(ListRetrievalOrdernoBusiness.PIECEORDERNO_KEY);	
		}

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 荷主名称
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		// 出庫予定日
		if (!StringUtil.isBlank(retrievalplandate))
		{
			txt_RtrivlPlanDate.setDate(retrievalplandate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);
		}
		// ケース／ピースフラグ
		if (!StringUtil.isBlank(casepieceflag))
		{
			if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
				rdo_CpfCase.setChecked(true);
			else if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
				rdo_CpfPiece.setChecked(true);
			else
				rdo_CpfAppointOff.setChecked(true);
		}
		// 出庫棚
		if (!StringUtil.isBlank(location))
		{
			txt_RetrievalLocation.setText(location);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		// 出荷先名称
		if (!StringUtil.isBlank(customername))
		{
			txt_CustomerName.setText(customername);
		}
		// オーダーNo.
		if (!StringUtil.isBlank(orderno))
		{
			txt_OrderNo.setText(orderno);
		}
		// ケース入数
		if (!StringUtil.isBlank(caseetr))
		{
			txt_CaseEntering.setInt(WmsFormatter.getInt(caseetr));
		}
		// ボール入数
		if (!StringUtil.isBlank(bundleetr))
		{
			txt_BundleEntering.setInt(WmsFormatter.getInt(bundleetr));
		}
		// ケースITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		// ボールITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}

		// フォーカスを作業者コードにセットする
		setFocus(txt_WorkerCode);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * <DIR>
	 * 概要：異常があった場合は、メッセージをセットし、falseを返します。
	 * </DIR>
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();

		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_RetrievalLocation))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CustomerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_OrderNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_TicketNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}
	// Private methods -----------------------------------------------

	/**
	 * 入力エリアを初期状態にします。 <BR>
	 * <BR>
	 * 概要:入力エリアを空にします。 <BR>
	 *      workerclearFlgがtrueの場合は作業者コード/パスワードをクリアします。
	 *      workerclearFlgがfalseの場合は作業者コード/パスワードはそのままにします。
	 * 
	 * <DIR>
	 * 	1.カーソルを作業者コードにセットします。 <BR>
	 *  2.入力エリアを初期状態にします。 <BR>
	 * </DIR>
	 * <BR>
	 *  
	 * @throws Exception 全ての例外を報告します。
	 */
	private void inputDataClear(boolean workerclearFlg) throws Exception
	{
		// 作業者コードにカーソルをセットします
		setFocus(txt_WorkerCode);

		// workerclearFlgがtrueの場合は作業者コード/パスワードをクリアします。
		if (workerclearFlg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
		}

		Connection conn = null;

		try
		{
			// 荷主コード
			txt_ConsignorCode.setText("");
			// 荷主名称
			txt_ConsignorName.setText("");
			// 出庫予定日
			txt_RtrivlPlanDate.setText("");
			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// ケース/ピース区分(ケース):初期セット
			rdo_CpfCase.setChecked(true);
			// ケース/ピース区分(ピース)
			rdo_CpfPiece.setChecked(false);
			// ケース/ピース区分(指定なし)
			rdo_CpfAppointOff.setChecked(false);
			// 出庫棚
			txt_RetrievalLocation.setText("");
			// 出荷先コード
			txt_CustomerCode.setText("");
			// 出荷先名称
			txt_CustomerName.setText("");
			// オーダーNo.
			txt_OrderNo.setText("");
			// ケース入数
			txt_CaseEntering.setText("");
			// ホスト予定ケース数
			txt_HostCasePlanQty.setText("");
			// ケースITF
			txt_CaseItf.setText("");
			// ボール入数
			txt_BundleEntering.setText("");
			// ホスト予定ピース数
			txt_HostPiesePlanQty.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 伝票No.
			txt_TicketNo.setText("");
			// 行No.
			txt_LineNo.setText("");
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterRetrievalPlanRegistSCH(conn);

			MasterRetrievalSupportParameter param =
				(MasterRetrievalSupportParameter) schedule.initFind(conn, null);
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			if (param.getMergeType() == MasterRetrievalSupportParameter.MERGE_TYPE_OVERWRITE)
			{
				txt_ConsignorName.setReadOnly(true);
				txt_CustomerName.setReadOnly(true);
				txt_ItemName.setReadOnly(true);
				txt_CaseEntering.setReadOnly(true);
				txt_BundleEntering.setReadOnly(true);
				txt_CaseItf.setReadOnly(true);
				txt_BundleItf.setReadOnly(true);
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
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * <DIR>
	 * 	1.ためうちエリアに値をセットします。<BR>
	 *  2.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * </DIR>
	 * <BR>
	 *  <DIR>
	 *   リストセルの列番号一覧<BR>
	 * 		<DIR>
	 * 		0.ケース/ピース区分(HIDDEN) <BR>
	 * 		1.取消ボタン <BR>
	 * 		2.修正ボタン <BR>
	 * 		3.荷主コード <BR>
	 * 		4.出庫予定日 <BR>
	 * 		5.商品コード <BR>
	 * 		6.オーダーNo. <BR>
	 * 		7.出荷先コード <BR>
	 * 		8.ケース入数 <BR>
	 * 		9.ホスト予定ケース数 <BR>
	 * 		10.ケースITF <BR>
	 * 		11.伝票No. <BR>
	 * 		12.出庫棚 <BR>
	 * 		13.区分 <BR>
	 * 		14.荷主名称 <BR>
	 * 		15.商品名称 <BR>
	 * 		16.出荷先名称 <BR>
	 * 		17.ボール入数 <BR>
	 * 		18.ホスト予定ピース数 <BR>
	 * 		19.ボールITF <BR>
	 * 		20.行No. <BR>
	 * 		</DIR>
	 *   </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setListRow(MasterRetrievalSupportParameter mergeParam) throws Exception
	{
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 荷主名称
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		// 出荷先コード
		toolTip.add(DisplayText.getText("LBL-W0034"), txt_CustomerCode.getText());
		// 出荷先名称
		toolTip.add(DisplayText.getText("LBL-W0036"), txt_CustomerName.getText());
		// ケースITF
		toolTip.add(DisplayText.getText("LBL-W0338"), txt_CaseItf.getText());
		// ボールITF
		toolTip.add(DisplayText.getText("LBL-W0337"), txt_BundleItf.getText());
		// 伝票No.
		toolTip.add(DisplayText.getText("LBL-W0266"), txt_TicketNo.getText());
		// 行No.
		toolTip.add(DisplayText.getText("LBL-W0109"), txt_LineNo.getText());

		//カレント行にTOOL TIPをセットする
		lst_SRetrievalPlanRegist.setToolTip(
			lst_SRetrievalPlanRegist.getCurrentRow(),
			toolTip.getText());

		// ケース/ピース区分(HIDDDEN)
		//lst_SRetrievalPlanRegist.setValue(0, getCasePieceFlagFromInputArea());
		List hiddenList = new Vector();
		hiddenList.add(0, getCasePieceFlagFromInputArea());
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		hiddenList.add(2, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		hiddenList.add(3, WmsFormatter.getTimeStampString(mergeParam.getCustomerLastUpdateDate()));
		lst_SRetrievalPlanRegist.setValue(0, CollectionUtils.getConnectedString(hiddenList));
		// 荷主コード
		lst_SRetrievalPlanRegist.setValue(3, txt_ConsignorCode.getText());
		// 出庫予定日
		lst_SRetrievalPlanRegist.setValue(4, txt_RtrivlPlanDate.getText());
		// 商品コード
		lst_SRetrievalPlanRegist.setValue(5, txt_ItemCode.getText());
		// 区分
		lst_SRetrievalPlanRegist.setValue(6,
			DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		// 出庫棚
		lst_SRetrievalPlanRegist.setValue(7, txt_RetrievalLocation.getText());
		// 出荷先コード
		lst_SRetrievalPlanRegist.setValue(8, txt_CustomerCode.getText());
		// オーダーNo.
		lst_SRetrievalPlanRegist.setValue(9, txt_OrderNo.getText());

		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(10, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(10,
				WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// ホスト予定ケース数
		if (StringUtil.isBlank(txt_HostCasePlanQty.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(11, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(11,
				WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		}
		// ケースITF
		lst_SRetrievalPlanRegist.setValue(12, txt_CaseItf.getText());
		// 伝票No.
		lst_SRetrievalPlanRegist.setValue(13, txt_TicketNo.getText());
		
		// 荷主名称
		lst_SRetrievalPlanRegist.setValue(14, txt_ConsignorName.getText());
		// 商品名称
		lst_SRetrievalPlanRegist.setValue(15, txt_ItemName.getText());
		// 出荷先名称
		lst_SRetrievalPlanRegist.setValue(16, txt_CustomerName.getText());
		// ボール入数
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(17, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(17,
				WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		// ホスト予定ピース数
		if (StringUtil.isBlank(txt_HostPiesePlanQty.getText()))
		{
			lst_SRetrievalPlanRegist.setValue(18, "0");
		}
		else
		{
			lst_SRetrievalPlanRegist.setValue(18,
				WmsFormatter.getNumFormat(txt_HostPiesePlanQty.getInt()));
		}
		// ボールITF
		lst_SRetrievalPlanRegist.setValue(19, txt_BundleItf.getText());
		// 行No.
		lst_SRetrievalPlanRegist.setValue(20, Integer.toString(txt_LineNo.getInt()));
		
		// 修正状態をデフォルトに戻します
		this.getViewState().setInt(LINENO, -1);

	}

	/** Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1)<BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。<BR>
	 * 3.登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1)<BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			修正対象ためうち行No.* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>RetrievalSupportParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private MasterRetrievalSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_SRetrievalPlanRegist.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_SRetrievalPlanRegist.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterRetrievalSupportParameter param = new MasterRetrievalSupportParameter();
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(lst_SRetrievalPlanRegist.getValue(3));
			// 出庫予定日
			param.setRetrievalPlanDate(
				WmsFormatter.toParamDate(
					lst_SRetrievalPlanRegist.getValue(4),
					this.httpRequest.getLocale()));
			// 商品コード
			param.setItemCode(lst_SRetrievalPlanRegist.getValue(5));
			// ケース／ピース区分(HIDDEN項目よりセット)
			//param.setCasePieceflg(lst_SRetrievalPlanRegist.getValue(0));
			param.setCasePieceflg(CollectionUtils.getString(0, lst_SRetrievalPlanRegist.getValue(0)));
			// 出庫棚
			param.setRetrievalLocation(lst_SRetrievalPlanRegist.getValue(7));
			// 出荷先コード
			param.setCustomerCode(lst_SRetrievalPlanRegist.getValue(8));
			// オーダーNo.
			param.setOrderNo(lst_SRetrievalPlanRegist.getValue(9));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(10)));
			// ホスト予定ケース数
			param.setPlanCaseQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(11)));
			// ケースITF
			param.setITF(lst_SRetrievalPlanRegist.getValue(12));
			// 伝票No.
			param.setShippingTicketNo(lst_SRetrievalPlanRegist.getValue(13));
			
			// 荷主名称
			param.setConsignorName(lst_SRetrievalPlanRegist.getValue(14));
			// 商品名称
			param.setItemName(lst_SRetrievalPlanRegist.getValue(15));
			// 出荷先名称
			param.setCustomerName(lst_SRetrievalPlanRegist.getValue(16));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(17)));
			// ホスト予定ピース数
			param.setPlanPieceQty(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(18)));
			// ボールITF
			param.setBundleITF(lst_SRetrievalPlanRegist.getValue(19));
			// 行No.
			param.setShippingLineNo(Formatter.getInt(lst_SRetrievalPlanRegist.getValue(20)));
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			// 行No.を保持しておく
			param.setRowNo(i);

			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_SRetrievalPlanRegist.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, lst_SRetrievalPlanRegist.getValue(0))));
			// 出荷先コード最終使用日
			param.setCustomerLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(3, lst_SRetrievalPlanRegist.getValue(0))));
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterRetrievalSupportParameter[] listparam = new MasterRetrievalSupportParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			// セットするためうちデータがなければnullをセット
			return null;
		}
	}

	/**
	 * 入力エリア.ケースピース区分ラジオボタンから対応する出庫パラメータ.ケース/ピース区分を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		// ケース/ピース区分
		if (rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			return RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_CpfCase.getChecked())
		{
			// ケース
			return RetrievalSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			// ピース
			return RetrievalSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	/**
	 * 出庫パラメータ.ケースピース区分から対応するケース/ピース区分を取得します。<BR>
	 * <BR>
	 * @param value ラジオボタンに対応する出庫パラメータ.ケース/ピース区分です。
	 * @throws Exception 全ての例外を報告します。  
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		// ケース/ピース区分
		if (value.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なし
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	/**
	 * リストセル.区分の内容を入力エリアのケース/ピース区分チェックボックスへ表示します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (CollectionUtils.getString(0, lst_SRetrievalPlanRegist
			.getValue(0))
			.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なしにチェック
			rdo_CpfAppointOff.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
		}
		else if (
				CollectionUtils.getString(0, lst_SRetrievalPlanRegist.getValue(0)).equals(
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			// ケースにチェック
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
		}
		else if (
				CollectionUtils.getString(0, lst_SRetrievalPlanRegist.getValue(0)).equals(
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピースにチェック
			rdo_CpfPiece.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
		}
	}

	/**
	 * ボタンの編集可否を設定するメソッドです。<BR>
	 * <BR>
	 * 概要：false or trueを受け取ってボタンの編集可否を設定します。<BR>
	 * @param  status(false or true)
	 * @return なし
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		// ボタン押下を不可にする
		// 登録ボタン
		btn_Submit.setEnabled(arg);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(arg);
	}
	
	// Event handler methods -----------------------------------------

	/** 
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SEARCH_CONSIGNOR, param, DO_SEARCH_PROCESS);
	}

	/** 
	 * 出庫予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出庫予定日一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       出庫予定日 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchRetrievalPlanDate_Click(ActionEvent e) throws Exception
	{
		// 出庫予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 出庫予定日
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		// 検索フラグ
		param.setParameter(
			ListRetrievalPlanDateBusiness.SEARCHRETRIEVALPLANDATE_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect(DO_SEARCH_RETRIEVALPLANDATE, param, DO_SEARCH_PROCESS);
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SEARCH_ITEM, param, DO_SEARCH_PROCESS);
	}

	/** 
	 * 出庫予定の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出庫予定一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 * 		 出庫予定日 <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 * 		 出庫棚 <BR>
	 *       出荷先コード <BR>
	 *       オーダーNo. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PRtrivlPlanSrch_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 出庫予定日
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		// 商品コード
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース/ピース区分
		if(rdo_CpfCase.getChecked())
		{
			// ケース
			param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			// ピース
			param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 出庫棚
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());
		// 出荷先コード
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		// オーダーNo.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		// 処理中画面->結果画面
		redirect(DO_SEARCH_RETRIEVALPLAN, param, DO_SEARCH_PROCESS);

	}

	/** 
	 * 出庫棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出庫棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 * 		 出庫予定日 <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 * 		 出庫棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchRetrievalLocation_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 出庫予定日
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		// 商品コード
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース/ピース区分
		if(rdo_CpfCase.getChecked())
		{
			// ケース
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			// ピース
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 出庫棚
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());

		// ケース出庫棚／ピース出庫棚判断フラグ
		param.setParameter(ListRetrievalLocationBusiness.CASE_PIECE_KEY,
			RetrievalSupportParameter.LISTBOX_RETRIEVAL);

		// 処理中画面->結果画面
		redirect(DO_SEARCH_LOCATION, param, DO_SEARCH_PROCESS);

	}

	/** 
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出荷先一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       出荷先コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 出荷先コード
		param.setParameter(ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY, 
			txt_CustomerCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SEARCH_CUSTOMER, param, DO_SEARCH_PROCESS);
	}

	/** 
	 * オーダーNo.の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件でオーダーNo.一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 * 		 出庫予定日 <BR>
	 *       商品コード <BR>
	 * 		 ケース/ピース区分 <BR>
	 * 		 出庫棚 <BR>
	 *       出荷先コード <BR>
	 *       オーダーNo. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchOrderNo_Click(ActionEvent e) throws Exception
	{
		// オーダーNo.検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListRetrievalConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 出庫予定日
		param.setParameter(
			ListRetrievalPlanDateBusiness.RETRIEVALPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
		// 商品コード
		param.setParameter(ListRetrievalItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース/ピース区分
		if(rdo_CpfCase.getChecked())
		{
			// ケース
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_CASE);
		}
		if(rdo_CpfPiece.getChecked())
		{
			// ピース
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_PIECE);
		}
		if(rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			param.setParameter(ListRetrievalLocationBusiness.CASEPIECEFLAG_KEY,
				RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING);
		}
		// ケース出庫棚
		param.setParameter(ListRetrievalLocationBusiness.RETRIEVALLOCATION_KEY,
			txt_RetrievalLocation.getText());
		// 検索フラグ
		param.setParameter(
			ListRetrievalOrdernoBusiness.SEARCH_RETRIEVAL_ORDERNO_KEY,
			RetrievalSupportParameter.SEARCHFLAG_PLAN);
		// オーダーNo.
		param.setParameter(ListRetrievalOrdernoBusiness.ORDERNO_KEY, txt_OrderNo.getText());
		// ケースオーダーNo.／ピースオーダーNo.判断フラグ
		param.setParameter(ListRetrievalOrdernoBusiness.CASE_PIECE_KEY, RetrievalSupportParameter.LISTBOX_RETRIEVAL);
		// 出荷先コード
		param.setParameter(
			ListRetrievalCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SEARCH_ORDER, param, DO_SEARCH_PROCESS);
	}

	/** 
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をチェックし、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.カーソルを作業者コードにセットします。<BR>
	 *   2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 * 	[パラメータ] *必須入力<BR>
	 * 	<BR>
	 * 	<DIR>
	 * 		作業者コード* <BR>
	 * 		パスワード* <BR>
	 * 		荷主コード* <BR>
	 * 		荷主名称 <BR>
	 * 		出庫予定日* <BR>
	 * 		商品コード* <BR>
	 * 		商品名称 <BR>
	 * 		ケースピース区分* <BR>
	 * 		ロケーションNo <BR>
	 * 		出荷先コード*2 <BR>
	 * 		出荷先名称*2 <BR>
	 * 		オーダーNo <BR>
	 * 		ケース入数*3 <BR>
	 * 		ボール入数 <BR>
	 * 		ホスト予定ケース数*1 *3<BR>
	 * 		ホスト予定ピース数*1 *4<BR>
	 * 		ケースITF <BR>
	 * 		ボールITF <BR>
	 * 		伝票No <BR>
	 * 		行No <BR>
	 *		<BR>
	 * 		*1 <BR>
	 * 		いずれか選択必須入力 <BR>
	 * 		*2 <BR>
	 * 		オーダーNo.入力時、必須入力 <BR>
	 * 		*3 <BR>
	 * 		ケース/ピース区分でケース選択時、必須入力 <BR>
	 * 		*4 <BR>
	 * 		ケース/ピース区分でピース選択時、必須入力 <BR>
	 * 	</DIR>
	 *   <BR>
	 *   4.スケジュールの結果がtrueであれば、ためうちエリアに追加します。<BR>
	 *     修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *   5.スケジュールの結果がtrueであれば、登録ボタン・一覧クリアボタンを有効にします。<BR>
	 *   6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *   7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 * 	 8.入力エリアの内容は保持します。<BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。  
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// 必須入力チェック
		// パターンマッチング
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();
		// 荷主コード
		txt_ConsignorCode.validate();
		// 荷主名称
		txt_ConsignorName.validate(false);
		// 出庫予定日
		txt_RtrivlPlanDate.validate();
		// 商品コード
		txt_ItemCode.validate();
		// 商品名称
		txt_ItemName.validate(false);		
		// 出庫棚
		txt_RetrievalLocation.validate(false);

		// 入力チェックを開始します
		// オーダーNo.入力時、出荷先コードと出荷先名称は必須入力
		if (!StringUtil.isBlank(txt_OrderNo.getText()))
		{
			// 出荷先コード
			txt_CustomerCode.validate(true);
			// 出荷先名称
			txt_CustomerName.validate(false);
		}
		else
		{
			// 出荷先コード
			txt_CustomerCode.validate(false);
			// 出荷先名称
			txt_CustomerName.validate(false);
		}
		
		// オーダーNo.
		txt_OrderNo.validate(false);
		// ケース入数
		txt_CaseEntering.validate(false);
		// ホスト予定ケース数
		txt_HostCasePlanQty.validate(false);
		// ケースITF
		txt_CaseItf.validate(false);
		// ボール入数
		txt_BundleEntering.validate(false);
		// ホスト予定ピース数
		txt_HostPiesePlanQty.validate(false);
		// ボールITF
		txt_BundleItf.validate(false);
		// 伝票No.
		txt_TicketNo.validate(false);
		// 行No.
		txt_LineNo.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			MasterRetrievalSupportParameter mergeParam = new MasterRetrievalSupportParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setCustomerCode(txt_CustomerCode.getText());
			mergeParam.setCustomerName(txt_CustomerName.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterRetrievalPlanRegistSCH(conn);
			
			mergeParam = (MasterRetrievalSupportParameter) schedule.query(conn, mergeParam)[0];
			// 入力エリアに補完結果を表示
			txt_ConsignorName.setText(mergeParam.getConsignorName());
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());
			txt_CustomerName.setText(mergeParam.getCustomerName());
			
			// スケジュールパラメータへセット
			// 入力エリア
			MasterRetrievalSupportParameter param = new MasterRetrievalSupportParameter();

			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(txt_ConsignorName.getText());
			// 出庫予定日
			param.setRetrievalPlanDate(WmsFormatter.toParamDate(txt_RtrivlPlanDate.getDate()));
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケースピース区分
			param.setCasePieceflg(getCasePieceFlagFromInputArea());
			// 出庫棚
			param.setRetrievalLocation(txt_RetrievalLocation.getText());
			// ケース出庫棚
			param.setCaseLocation(txt_RetrievalLocation.getText());
			// ピース出庫棚
			param.setPieceLocation(txt_RetrievalLocation.getText());
			// 出荷先コード
			param.setCustomerCode(txt_CustomerCode.getText());
			// 出荷先名称
			param.setCustomerName(txt_CustomerName.getText());
			// オーダーNo.
			param.setOrderNo(txt_OrderNo.getText());
			// ケースオーダーNo.
			param.setCaseOrderNo(txt_OrderNo.getText());
			// ピースオーダーNo.
			param.setPieceOrderNo(txt_OrderNo.getText());
			// ケース入数 
			param.setEnteringQty(Formatter.getInt(txt_CaseEntering.getText()));
			// ボール入数 
			param.setBundleEnteringQty(Formatter.getInt(txt_BundleEntering.getText()));
			// ホスト予定ケース数 
			param.setPlanCaseQty(Formatter.getInt(txt_HostCasePlanQty.getText()));
			// ホスト予定ピース数 
			param.setPlanPieceQty(Formatter.getInt(txt_HostPiesePlanQty.getText()));
			// ケースITF 
			param.setITF(txt_CaseItf.getText());
			// ボールITF 
			param.setBundleITF(txt_BundleItf.getText());
			// 伝票No.
			param.setShippingTicketNo(txt_TicketNo.getText());
			// 行No.
			param.setShippingLineNo(txt_LineNo.getInt());

			// スケジュールパラメータへわたすためうちエリアの情報をセットします
			// ためうちエリア情報格納用
			MasterRetrievalSupportParameter[] listparam = null;

			if (lst_SRetrievalPlanRegist.getMaxRows() == 1)
			{
				// ためうちにデータがなければnullをセット
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			// コネクションと入力エリア情報、ためうちエリア情報(修正行を除く)でスケジュールを実行します
			if (schedule.check(conn, param, listparam))
			{
				param.setConsignorLastUpdateDate(mergeParam.getConsignorLastUpdateDate());
				param.setCustomerLastUpdateDate(mergeParam.getCustomerLastUpdateDate());
				param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				// 結果がtrueであればためうちエリアにデータをセットします
				if (this.getViewState().getInt(LINENO) == -1)
				{
					// 新規入力であれば、ためうちに追加します
					// ためうちエリアに行を追加します
					lst_SRetrievalPlanRegist.addRow();
					// 追加した行を編集対象行にします
					lst_SRetrievalPlanRegist.setCurrentRow(
						lst_SRetrievalPlanRegist.getMaxRows() - 1);
					// データをセットします
					setListRow(param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新します
					// 編集行を指定します
					lst_SRetrievalPlanRegist.setCurrentRow(this.getViewState().getInt(LINENO));
					// データをセットします
					setListRow(param);
					// 選択行の色を元に戻すします
					lst_SRetrievalPlanRegist.resetHighlight();
				}

				// ためうちエリアのボタン押下を可能にします
				// 登録ボタン
				btn_Submit.setEnabled(true);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(true);
			}

			// メッセージをセットします
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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *      (作業者コード/パスワードはそのままにします。)
	 *    <DIR>
	 *  	･初期値については<CODE>inputDataClear(boolean)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力データを初期化します
		// (作業者コードとパスワードは初期化しません)
		inputDataClear(false);
	}

	/** 
	 * 登録開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、出庫予定登録を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.登録を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "登録しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ]*必須入力<BR>
	 * 					<DIR>
	 * 						作業者コード* <BR>
	 * 						パスワード* <BR>
	 * 						荷主コード* <BR>
	 * 						荷主名称* <BR>
	 * 						出庫予定日* <BR>
	 * 						商品コード* <BR>
	 * 						商品名称* <BR>
	 * 						ケースピース区分* <BR>
	 * 						ロケーションNo* <BR>
	 * 						出荷先コード* <BR>
	 * 						出荷先名称* <BR>
	 * 						オーダーNo* <BR>
	 * 						ケース入数* <BR>
	 * 						ボール入数* <BR>
	 * 						予定ケース数* <BR>
	 * 						予定ピース数* <BR>
	 * 						ケースITF* <BR>
	 * 						ボールITF* <BR>
	 * 						伝票No* <BR>
	 * 						行No* <BR>
	 * 						端末No.* <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時はためうちエリアの情報をクリアします。<BR>
	 *				3.修正状態を解除します。(ViewStateのためうち行No.にデフォルト:-1を設定します。)<BR>
	 *    			4.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		// 必須入力チェック
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();

		Connection conn = null;
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// 更新データ格納用
			RetrievalSupportParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1);

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterRetrievalPlanRegistSCH(conn);

			// スケジュールを開始します
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、コミットします
				conn.commit();

				// ためうちエリアの一覧をクリアします
				lst_SRetrievalPlanRegist.clearRow();

				// 修正状態をデフォルトに戻します
				this.getViewState().setInt(LINENO, -1);

				// ボタン押下を不可にします
				// 入庫開始ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}
			else
			{
				// 結果がfalseであれば、ロールバックします
				conn.rollback();
			}

			// スケジュールからのメッセージをセットします
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();

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
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 * 	    "一覧入力情報がクリアされます。宜しいですか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.ためうちの表示情報を全てクリアします。<BR>
	 *				2.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 * 				3.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				4.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// ためうち行をすべて削除します
		lst_SRetrievalPlanRegist.clearRow();

		// ボタン押下を不可にする
		setBtnTrueFalse(false);

		// 修正状態をデフォルト(-1)にします
		this.getViewState().setInt(LINENO, -1);

		// 作業者コードにカーソルをセットします
		setFocus(txt_WorkerCode);

	}

	/** 
	 * 取消、修正ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 取消ボタン概要:ためうちの該当データをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "取消しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.入力エリア、ためうちの該当データをクリアします。<BR>
	 * 				2.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *              3.ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にします。<BR>
	 *				4.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.選択されたためうち情報を上部の入力エリアに表示します。<BR>
	 *    2.選択された行を薄黄色にします。<BR>
	 *    3.ViewStateのためうち行No.に選択された行の行番号を設定します。
	 *    4.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SRetrievalPlanRegist_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_SRetrievalPlanRegist.getActiveCol() == 1)
		{
			// リスト削除
			lst_SRetrievalPlanRegist.removeRow(lst_SRetrievalPlanRegist.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンを無効にします
			// ためうちにデータがなければnullをセット
			if (lst_SRetrievalPlanRegist.getMaxRows() == 1)
			{
				// ためうちエリアのボタン押下を不可にします
				// 登録ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}

			// 修正状態をデフォルトに戻します(ためうち行Noと背景色)
			this.getViewState().setInt(LINENO, -1);
			lst_SRetrievalPlanRegist.resetHighlight();

		}
		// 修正ボタンクリック時
		else if (lst_SRetrievalPlanRegist.getActiveCol() == 2)
		{
			// ボタンが押下された行を入力エリアに表示します。
			// 修正対象行を指定します
			lst_SRetrievalPlanRegist.setCurrentRow(lst_SRetrievalPlanRegist.getActiveRow());

			// 荷主コード
			txt_ConsignorCode.setText(lst_SRetrievalPlanRegist.getValue(3));
			// 出庫予定日
			txt_RtrivlPlanDate.setDate(
				WmsFormatter.toDate(
					lst_SRetrievalPlanRegist.getValue(4),
					this.getHttpRequest().getLocale()));
			// 商品コード
			txt_ItemCode.setText(lst_SRetrievalPlanRegist.getValue(5));
			// ケース／ピース区分
			setCasePieceRBFromList();
			// 出庫棚
			txt_RetrievalLocation.setText(lst_SRetrievalPlanRegist.getValue(7));
			// 出荷先コード
			txt_CustomerCode.setText(lst_SRetrievalPlanRegist.getValue(8));
			// オーダーNo. 
			txt_OrderNo.setText(lst_SRetrievalPlanRegist.getValue(9));
			// ケース入数
			txt_CaseEntering.setText(lst_SRetrievalPlanRegist.getValue(10));
			// ホスト予定ケース数
			txt_HostCasePlanQty.setText(lst_SRetrievalPlanRegist.getValue(11));
			// ケースITF
			txt_CaseItf.setText(lst_SRetrievalPlanRegist.getValue(12));
			// 伝票No.
			txt_TicketNo.setText(lst_SRetrievalPlanRegist.getValue(13));
			
			// 荷主名称
			txt_ConsignorName.setText(lst_SRetrievalPlanRegist.getValue(14));
			// 商品名称
			txt_ItemName.setText(lst_SRetrievalPlanRegist.getValue(15));
			// 出荷先名称 
			txt_CustomerName.setText(lst_SRetrievalPlanRegist.getValue(16));
			// ボール入数
			txt_BundleEntering.setText(lst_SRetrievalPlanRegist.getValue(17));
			// ホスト予定ピース数
			txt_HostPiesePlanQty.setText(lst_SRetrievalPlanRegist.getValue(18));
			// ボールITF
			txt_BundleItf.setText(lst_SRetrievalPlanRegist.getValue(19));
			// 行No.
			txt_LineNo.setText(lst_SRetrievalPlanRegist.getValue(20));
			
			// ViewStateに保存
			// 修正状態を判断するためにViewStateにためうち行No.をセットします
			this.getViewState().setInt(LINENO, lst_SRetrievalPlanRegist.getActiveRow());
			// 修正行を薄黄色に変更します
			lst_SRetrievalPlanRegist.setHighlight(lst_SRetrievalPlanRegist.getActiveRow());
		}

		// カーソルを作業者コードにセットします
		setFocus(txt_WorkerCode);

	}

}
//end of class

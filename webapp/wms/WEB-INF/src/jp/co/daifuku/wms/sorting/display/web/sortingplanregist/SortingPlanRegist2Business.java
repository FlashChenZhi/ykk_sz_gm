// $Id: SortingPlanRegist2Business.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingplanregist;
import java.sql.Connection;
import java.sql.SQLException;
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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingcustomer.ListSortingCustomerBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortinglocation.ListSortingLocationBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingsupplier.ListSortingSupplierBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingPlanRegistSCH;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

/**
 * Designer : S.Ishigane <BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * 仕分予定登録(詳細情報登録)クラスです。<BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示します。<BR>
 * 仕分予定登録を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.入力ボタン押下処理(<CODE>btn_Input_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時は入力エリアの情報をためうちエリアに追加します。<BR>
 *   修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		出荷先コード* <BR>
 * 		出荷先名称 <BR>
 * 		出荷伝票No* <BR>
 * 		出荷伝票行No.* <BR>
 *		予定ケース数* <BR>
 *		予定ピース数* <BR>
 *		仕分場所* <BR>
 *		クロス／ＤＣ区分 <BR>
 *		仕入先コード*1 <BR>
 *		仕入先伝票No*1 <BR>
 *		仕入先伝票行No*1 <BR>
 *   <BR>
 * 		*1 <BR>
 * 		クロス/ＤＣ区分．クロスの場合必須項目 <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_Submit_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが仕分予定登録を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時はためうちの登録情報をクリアします。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		viewState.作業者コード* <BR>
 * 		viewState.パスワード* <BR>
 * 		出荷先コード* <BR>
 * 		出荷先名称 <BR>
 * 		出荷伝票No* <BR>
 * 		出荷伝票行No.* <BR>
 *		予定ケース数* <BR>
 *		予定ピース数* <BR>
 *		仕分場所* <BR>
 *		クロス／ＤＣ区分 <BR>
 *		仕入先コード*1 <BR>
 * 		仕入先名称 <BR>
 *		仕入先伝票No*1 <BR>
 *		仕入先伝票行No*1 <BR>
 *		端末No* <BR>
 *	 <DIR>
 *		*1 はクロス/DC区分.クロス選択時必須項目
 *	 </DIR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * リストセル<BR>
 * <DIR>
 *		1.取消ボタン<BR>
 *		2.修正ボタン<BR>
 *		3.ｹｰｽ/ﾋﾟｰｽ区分<BR>
 *		4.ｸﾛｽ/DC区分<BR>
 *		5.出荷先コード<BR>
 *		6.出荷伝票No<BR>
 *		7.ﾎｽﾄ予定ｹｰｽ数<BR>
 *		8.仕分場所<BR>
 *		9.仕入先ｺｰﾄﾞ<BR>
 *		10.入荷伝票No<BR>
 *		11.出荷先名称<BR>
 *		12.出荷伝票行No<BR>
 *		13.ﾎｽﾄ予定ﾋﾟｰｽ数<BR>
 *		14.仕入先名称<BR>
 *		14.入荷伝票行No<BR>
 * </DIR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingPlanRegist2Business extends SortingPlanRegist2 implements WMSConstants
{

	// viewState用KEY(隠しパラメータ)
	// ためうち行No.
	private static final String LINENO = "LINENO";
	// 仕分予定一意ｷｰ
	private static final String SORTID = "SORTID";
	// 最終更新日時
	private static final String LASTUPDDATE = "LASTUPDDATE";

	// リストセル列指定定数
	// 隠しパラメータ(リストセル)
	private static final int LST_HDN = 0;
	// 取消ボタン(リストセル)
	private static final int LST_CLRBTN = 1;
	// 修正ボタン(リストセル)
	private static final int LST_MODBTN = 2;
	// ケース/ピース区分(リストセル)
	private static final int LST_CPFNM = 3;
	// クロス／ＤＣ(リストセル)
	private static final int LST_CROSSDC = 4;
	// 出荷先コード(リストセル)
	private static final int LST_CUSTCD = 5;
	// 出荷伝票No(リストセル)
	private static final int LST_CUSTTKNO = 6;
	// ホスト予定ケース数(リストセル)
	private static final int LST_PLANCASEQTY = 7;
	// 仕分場所(リストセル)
	private static final int LST_SORTPLACE = 8;
	// 仕入先コード(リストセル)
	private static final int LST_SPLCD = 9;
	// 入荷伝票No(リストセル)
	private static final int LST_INSTKTKNO = 10;
	// 出荷先名称(リストセル)
	private static final int LST_CUSTNM = 11;
	// 出荷伝票行No(リストセル)
	private static final int LST_CUSTLINENO = 12;
	// ホスト予定ピース数(リストセル)
	private static final int LST_PLANPIECEQTY = 13;
	// 仕入先名称(リストセル)
	private static final int LST_SPLNM = 14;
	// 入荷伝票行No(リストセル)
	private static final int LST_INSTKLINENO = 15;

	// リストセル隠し項目順序
	// 仕分予定一意ｷｰ
	private static final int HDNIDX_UKEY = 0;
	// 最終更新日時
	private static final int HDNIDX_UPDAY = 1;

	// 遷移先アドレス
	// 出荷先検索ポップアップアドレス
	private static final String DO_SRCH_CUSTOMER = "/sorting/listbox/listsortingcustomer/ListSortingCustomer.do";
	// 仕入先検索ポップアップアドレス
	private static final String DO_SRCH_SUPPLIER = "/sorting/listbox/listsortingsupplier/ListSortingSupplier.do";
	// 仕分場所検索ポップアップアドレス
	private static final String DO_SRCH_SORTINGPLACE = "/sorting/listbox/listsortinglocation/ListSortingLocation.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
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
		//　登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		//　一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		// カーソルを出荷先コードにセットする
		setFocus(txt_CustomerCode);
	}

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 荷主コード [基本情報で設定した内容] <BR>
	 * 荷主名称 [基本情報で設定した内容] <BR>
	 * 仕分予定日 [基本情報で設定した内容] <BR>
	 * 商品コード [基本情報で設定した内容] <BR>
	 * 商品名称 [基本情報で設定した内容] <BR>
	 * ケース入数 [基本情報で設定した内容] <BR>
	 * ケースITF [基本情報で設定した内容] <BR>
	 * ボール入数 [基本情報で設定した内容] <BR>
	 * ボールITF [基本情報で設定した内容] <BR>
	 * ケース/ピース区分 ["ケース"選択] <BR>
	 * クロス／ＤＣ区分 ["ＤＣ"選択] <BR>
	 * 出荷先コード [なし] <BR>
	 * 出荷先名称 [なし] <BR>
	 * 出荷伝票No [なし] <BR>
	 * 出荷伝票行No. [なし] <BR>
	 * 予定ケース数 [なし] <BR>
	 * 予定ピース数 [なし] <BR>
	 * 仕分場所 [なし] <BR>
	 * 仕入先コード [なし] <BR>
	 * 仕入先伝票No [なし] <BR>
	 * 仕入先伝票行No [なし] <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(SortingPlanRegistBusiness.TITLE)));

		// 画面を初期化します
		setInitView();

		// 詳細情報登録のタブを前出しします
		tab_BscDtlRst.setSelectedIndex(2);

		// 入力エリアの固定項目を設定します
		// 荷主コード
		lbl_JavaSetConsignorCode.setText(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY));
		// 荷主名称
		lbl_JavaSetConsgnorName.setText(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORNAME_KEY));
		// 仕分予定日
		txt_FSortingPlanDate.setDate(WmsFormatter.toDate(this.getViewState().getString(ListSortingDateBusiness.SORTINGPLANDATE_KEY)));
		// 商品コード
		lbl_JavaSetItemCode.setText(this.getViewState().getString(ListSortingItemBusiness.ITEMCODE_KEY));
		// 商品名称
		lbl_JavaSetItemName.setText(this.getViewState().getString(ListSortingItemBusiness.ITEMNAME_KEY));
		// ケース入数
		lbl_JavaSetCaseEntering.setText(WmsFormatter.getNumFormat(this.getViewState().getInt(SortingPlanRegistBusiness.CASEQTY)));
		// ボール入数
		lbl_JavaSetBundleEntering.setText(WmsFormatter.getNumFormat(this.getViewState().getInt(SortingPlanRegistBusiness.BUNDLEQTY)));
		// ケースITF
		lbl_JavaSetCaseItf.setText(this.getViewState().getString(SortingPlanRegistBusiness.CASEITF));
		// ボールITF
		lbl_JavaSetBundleItf.setText(this.getViewState().getString(SortingPlanRegistBusiness.BUNDLEITF));
		// ためうち行No.初期化
		this.getViewState().setInt(LINENO, -1);

		// ためうちエリアを初期化します
		lst_SortingPlanRegist.clearRow();

		// 登録、一覧クリアボタンを無効化します
		btn_Submit.setEnabled(false);
		btn_ListClear.setEnabled(false);

	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 出荷先コード
		String _customercode = param.getParameter(ListSortingCustomerBusiness.CUSTOMERCODE_KEY);
		// 出荷先名称名称
		String _customername = param.getParameter(ListSortingCustomerBusiness.CUSTOMERNAME_KEY);
		// 仕入先コード
		String _supliercd = param.getParameter(ListSortingSupplierBusiness.SUPPLIERCODE_KEY);
		// 仕入先名称
		String _supliername = param.getParameter(ListSortingSupplierBusiness.SUPPLIERNAME_KEY);
		// 仕入先名称
		String _sortinglocation = param.getParameter(ListSortingLocationBusiness.SORTINGLOCATION_KEY);

		// 空ではないときに値をセットする
		// 出荷先コード
		if (!StringUtil.isBlank(_customercode))
		{
			txt_CustomerCode.setText(_customercode);

		}
		// 出荷先名称
		if (!StringUtil.isBlank(_customername))
		{
			txt_CustomerName.setText(_customername);

		}
		// 仕入先コード
		if (!StringUtil.isBlank(_supliercd))
		{
			txt_SupplierCode.setText(_supliercd);

		}
		// 仕入先名称
		if (!StringUtil.isBlank(_supliername))
		{
			txt_SupplierName.setText(_supliername);

		}
		// 仕分場所
		if (!StringUtil.isBlank(_sortinglocation))
		{
			txt_PickingPlace.setText(_sortinglocation);

		}
		// カーソルを行No.にセットする
		setFocus(txt_CustomerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
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
		if (!checker.checkContainNgText(txt_ShippingTicketNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_PickingPlace))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_SupplierCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_SupplierName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_InstockTicketNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	// Private methods -----------------------------------------------

	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の入力エリアの入力項目を初期化します。 <BR>
	 * <BR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView() throws Exception
	{
		// フォーカスをセットします
		setFocus(txt_CustomerCode);
		// ケース／ピース区分
		rdo_CpfCase.setChecked(true);
		rdo_CpfPiece.setChecked(false);
		// クロス／ＤＣ区分
		rdo_CrossDCFlagDC.setChecked(false);
		rdo_CrossDCFlagCross.setChecked(true);
		// 出荷先コード
		txt_CustomerCode.setText("");
		// 出荷先名称
		txt_CustomerName.setText("");
		// 出荷伝票No
		txt_ShippingTicketNo.setText("");
		// 出荷伝票行No
		txt_ShippingTicketLineNo.setText("");
		// 予定ケース数
		txt_PlanCaseQty.setText("");
		// 予定ピース数
		txt_PlanPieceQty.setText("");
		// 仕分場所
		txt_PickingPlace.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 仕入先名称
		txt_SupplierName.setText("");
		// 入荷伝票No
		txt_InstockTicketNo.setText("");
		// 入荷伝票行No
		txt_InstockTicketLineNo.setText("");
	}

	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面のためうちエリアを初期化します。 <BR>
	 * <BR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitViewDetail() throws Exception
	{
		// ためうち行をすべて削除します
		lst_SortingPlanRegist.clearRow();

		// ボタン押下を不可にします
		// 登録ボタン
		btn_Submit.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 修正状態をデフォルト(-1)にします
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
	 * 			ためうちエリアの内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private SortingParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();

		// 端末No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String termNo = userHandler.getTerminalNo();

		for (int i = 1; i < lst_SortingPlanRegist.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_SortingPlanRegist.setCurrentRow(i);

			// スケジュールパラメータへセット
			SortingParameter param = new SortingParameter();
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(SortingPlanRegistBusiness.WORKERCODE));
			// パスワード
			param.setPassword(this.getViewState().getString(SortingPlanRegistBusiness.PASSWORD));
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY));
			// 荷主名称
			param.setConsignorName(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORNAME_KEY));
			// 仕分予定日
			param.setPlanDate(this.getViewState().getString(ListSortingDateBusiness.SORTINGPLANDATE_KEY));
			// 商品コード
			param.setItemCode(this.getViewState().getString(ListSortingItemBusiness.ITEMCODE_KEY));
			// 商品名称
			param.setItemName(this.getViewState().getString(ListSortingItemBusiness.ITEMNAME_KEY));
			// ケース入数
			param.setEnteringQty(Integer.parseInt(this.getViewState().getString(SortingPlanRegistBusiness.CASEQTY)));
			// ボール入数
			param.setBundleEnteringQty(Integer.parseInt(this.getViewState().getString(SortingPlanRegistBusiness.BUNDLEQTY)));
			// ケースITF
			param.setITF(this.getViewState().getString(SortingPlanRegistBusiness.CASEITF));
			// ボールITF
			param.setBundleITF(this.getViewState().getString(SortingPlanRegistBusiness.BUNDLEITF));
			// 端末No
			param.setTerminalNumber(termNo);

			// ためうちエリア情報
			// ケース/ピース区分 名称からパラメータをセットする
			param.setCasePieceFlag(getCasePieceFlagParam(lst_SortingPlanRegist.getValue(LST_CPFNM)));
			// クロス/DC区分 名称からパラメータをセットする
			param.setTcdcFlag(getCrossDcParam(lst_SortingPlanRegist.getValue(LST_CROSSDC)));
			// 出荷先コード
			param.setCustomerCode(lst_SortingPlanRegist.getValue(LST_CUSTCD));
			// 出荷先名称
			param.setCustomerName(lst_SortingPlanRegist.getValue(LST_CUSTNM));
			// 出荷伝票No
			param.setShippingTicketNo(lst_SortingPlanRegist.getValue(LST_CUSTTKNO));
			// 出荷伝票行No
			param.setShippingLineNo(Formatter.getInt(lst_SortingPlanRegist.getValue(LST_CUSTLINENO)));
			// 予定ケース数
			param.setPlanCaseQty(Formatter.getInt(lst_SortingPlanRegist.getValue(LST_PLANCASEQTY)));
			// 予定ピース数
			param.setPlanPieceQty(Formatter.getInt(lst_SortingPlanRegist.getValue(LST_PLANPIECEQTY)));
			// 仕分場所
			param.setSortingLocation(lst_SortingPlanRegist.getValue(LST_SORTPLACE));

			// 仕入先コード
			param.setSupplierCode(lst_SortingPlanRegist.getValue(LST_SPLCD));
			// 仕入先名称
			param.setSupplierName(lst_SortingPlanRegist.getValue(LST_SPLNM));
			// 入荷伝票No
			param.setInstockTicketNo(lst_SortingPlanRegist.getValue(LST_INSTKTKNO));
			// 入荷伝票行No
			param.setInstockLineNo(Formatter.getInt(lst_SortingPlanRegist.getValue(LST_INSTKLINENO)));

			// 行No.を保持しておく
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			SortingParameter[] listparam = new SortingParameter[vecParam.size()];
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
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setListRow() throws Exception
	{
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 出荷先名称
		toolTip.add(DisplayText.getText("LBL-W0036"), txt_CustomerName.getText());
		// 仕入先コード
		toolTip.add(DisplayText.getText("LBL-W0251"), txt_SupplierCode.getText());
		// 仕入先名称
		toolTip.add(DisplayText.getText("LBL-W0253"), txt_SupplierName.getText());
		// 入荷伝票No
		toolTip.add(DisplayText.getText("LBL-W0091"), txt_InstockTicketNo.getText());
		// 入荷伝票行No
		toolTip.add(DisplayText.getText("LBL-W0090"), Integer.toString(txt_InstockTicketLineNo.getInt()));

		//カレント行にTOOL TIPをセットする
		lst_SortingPlanRegist.setToolTip(lst_SortingPlanRegist.getCurrentRow(), toolTip.getText());

		// ケースピース区分
		lst_SortingPlanRegist.setValue(LST_CPFNM, getCasePieceFlagName());
		// クロス/DC
		lst_SortingPlanRegist.setValue(LST_CROSSDC, getCrossDcName());
		// 出荷先コード
		lst_SortingPlanRegist.setValue(LST_CUSTCD, txt_CustomerCode.getText());
		// 出荷先名称
		lst_SortingPlanRegist.setValue(LST_CUSTNM, txt_CustomerName.getText());
		// 出荷伝票No
		lst_SortingPlanRegist.setValue(LST_CUSTTKNO, txt_ShippingTicketNo.getText());
		// 出荷伝票行No
		lst_SortingPlanRegist.setValue(LST_CUSTLINENO, Integer.toString(txt_ShippingTicketLineNo.getInt()));
		// 予定ケース数
		lst_SortingPlanRegist.setValue(LST_PLANCASEQTY, WmsFormatter.getNumFormat(txt_PlanCaseQty.getInt()));
		// 予定ピース数
		lst_SortingPlanRegist.setValue(LST_PLANPIECEQTY, WmsFormatter.getNumFormat(txt_PlanPieceQty.getInt()));
		// 仕分場所
		lst_SortingPlanRegist.setValue(LST_SORTPLACE, txt_PickingPlace.getText());
		// 仕入先コード
		lst_SortingPlanRegist.setValue(LST_SPLCD, txt_SupplierCode.getText());
		// 仕入先名称
		lst_SortingPlanRegist.setValue(LST_SPLNM, txt_SupplierName.getText());
		// 入荷伝票No
		lst_SortingPlanRegist.setValue(LST_INSTKTKNO, txt_InstockTicketNo.getText());
		// 入荷伝票行No
		lst_SortingPlanRegist.setValue(LST_INSTKLINENO, Integer.toString(txt_InstockTicketLineNo.getInt()));

		// 修正状態をデフォルトに戻します
		this.getViewState().setInt(LINENO, -1);
	}

	/**
	 * クロス/DC区分から対応する名称を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCrossDcName() throws Exception
	{
		// クロス/DC
		if (rdo_CrossDCFlagDC.getChecked())
		{
			// DC
			return DisplayText.getText("LBL-W0358");
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			// クロス
			return DisplayText.getText("LBL-W0359");
		}

		return "";
	}
	/**
	 * クロス/DC区分名称から入力エリアのクロス/DCを設定します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void getCrossDcChk(String str) throws Exception
	{
		// クロス/DC
		if (str.equals(DisplayText.getText("LBL-W0358")))
		{
			// DCにチェックをつける
			rdo_CrossDCFlagDC.setChecked(true);
			rdo_CrossDCFlagCross.setChecked(false);
		}
		else if (str.equals(DisplayText.getText("LBL-W0359")))
		{
			// クロスにチェックをつける
			rdo_CrossDCFlagDC.setChecked(false);
			rdo_CrossDCFlagCross.setChecked(true);
		}
	}
	/**
	 * ためうちエリアのクロス/DC区分から対応するパラメータを取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCrossDcParam(String str)
	{
		// クロス/DC
		if (str.equals(DisplayText.getText("LBL-W0358")))
		{
			// DC
			return SortingParameter.TCDC_FLAG_DC;
		}
		else if (str.equals(DisplayText.getText("LBL-W0359")))
		{
			// クロス
			return SortingParameter.TCDC_FLAG_CROSSTC;
		}

		return "";
	}

	/**
	 * ためうちエリアのケースピース区分から対応するケース/ピース区分(パラメータ)を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCasePieceFlagParam(String str) throws Exception
	{
		if (str.equals(DisplayText.getText("LBL-W0375")))
		{
			// ケース
			return StorageSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (str.equals(DisplayText.getText("LBL-W0376")))
		{
			// ケース
			return StorageSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}
	/**
	 * ケースピース区分名称からケースピース区分ラジオボタンにチェックをつけます。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void getCasePieceFlagChk(String str) throws Exception
	{
		if (str.equals(DisplayText.getText("LBL-W0375")))
		{
			// ケースにチェックをつける
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
		}
		else if (str.equals(DisplayText.getText("LBL-W0376")))
		{
			// ピースにチェックをつける
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(true);
		}
	}
	/**
	 * ラジオボタンのケースピース区分ラジオﾎﾞﾀﾝから対応するｹｰｽ/ﾋﾟｰｽ区分名称を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCasePieceFlagName() throws Exception
	{
		// 区分
		if (rdo_CpfCase.getChecked())
		{
			// ケース
			return DisplayText.getText("LBL-W0375");
		}
		else if (rdo_CpfPiece.getChecked())
		{
			// ピース
			return DisplayText.getText("LBL-W0376");
		}

		return "";
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
	public void btn_Back_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 戻るボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:仕分予定修正・削除(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		this.getViewState().setString(SortingPlanRegistBusiness.MESSAGE, "");
		// 詳細情報登録画面->基本情報設定画面
		forward(SortingPlanRegistBusiness.DO_REGIST);
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FSortingPlanDate_Server(ActionEvent e) throws Exception
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
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsgnorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Flag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCpf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetBundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetBundleItf_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出荷先検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 *       商品コード <BR>
	 *       viewState.ケース/ピース区分 <BR>
	 *       出荷先コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		// 出荷先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetConsignorCode.getText());
		// 出荷先コード
		param.setParameter(ListSortingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());

		// 処理中画面->結果画面
		redirect(DO_SRCH_CUSTOMER, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CustomerName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShippingTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShpTktLineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketLineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingTicketLineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PlanCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PlanPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanPieceQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PlanPieceQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlace_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlace_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlace_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分場所の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕分場所検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 *       商品コード <BR>
	 *       出荷先コード <BR>
	 *       仕分場所 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchPickingPlace_Click(ActionEvent e) throws Exception
	{
		// 仕分場所検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetConsignorCode.getText());
		// 仕分場所
		param.setParameter(ListSortingLocationBusiness.SORTINGLOCATION_KEY, txt_PickingPlace.getText());
		
		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLACE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagDC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagDC_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagCross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagCross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchSupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕入先検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 *       商品コード <BR>
	 *       viewState.ケース/ピース区分 <BR>
	 *       出荷先コード <BR>
	 *       仕入先コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 仕入先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetConsignorCode.getText());
		// 仕入先コード
		param.setParameter(ListSortingSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 処理中画面->結果画面
		redirect(DO_SRCH_SUPPLIER, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketLineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketLineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockTicketLineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をチェックし、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.カーソルを出荷先コードにセットします。<BR>
	 *   2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 *   [パラメータ] *必須入力<BR>
	 *   <BR>
	 *   <DIR>
	 * 		出荷先コード* <BR>
	 * 		出荷先名称 <BR>
	 * 		出荷伝票No* <BR>
	 * 		出荷伝票行No.* <BR>
	 *		予定ケース数* <BR>
	 *		予定ピース数* <BR>
	 *		仕分場所* <BR>
	 *		クロス／ＤＣ区分 <BR>
	 *		仕入先コード*1 <BR>
	 * 		仕入先名称 <BR>
	 *		仕入先伝票No*1 <BR>
	 *		仕入先伝票行No*1 <BR>
	 *   <BR>
	 * 		*1 <BR>
	 * 		クロス/ＤＣ区分．クロスの場合必須項目 <BR>
	 *	 </DIR>
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
		// カーソルを出荷先コードにセットする
		setFocus(txt_CustomerCode);

		// 入力チェック
		// 出荷先コード
		txt_CustomerCode.validate();
		// 出荷先名称
		txt_CustomerName.validate(false);
		// 出荷伝票No
		txt_ShippingTicketNo.validate(false);
		// 出荷伝票行No
		txt_ShippingTicketLineNo.validate(false);
		// 予定ケース数
		txt_PlanCaseQty.validate(false);
		// 予定ピース数
		txt_PlanPieceQty.validate(false);
		// 仕分場所
		txt_PickingPlace.validate();
		// 仕入先コード
		// 仕入先名称
		// 入荷伝票No
		// 入荷伝票行No
		if (rdo_CrossDCFlagCross.getChecked())
		{
			txt_SupplierCode.validate();
			txt_SupplierName.validate(false);
			txt_InstockTicketNo.validate();
			txt_InstockTicketLineNo.validate();
		}
		else
		{
			txt_SupplierCode.validate(false);
			txt_SupplierName.validate(false);
			txt_InstockTicketNo.validate(false);
			txt_InstockTicketLineNo.validate(false);
		}

		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			// スケジュールパラメータへセットします
			SortingParameter param = new SortingParameter();
			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(SortingPlanRegistBusiness.WORKERCODE));
			// パスワード
			param.setPassword(this.getViewState().getString(SortingPlanRegistBusiness.PASSWORD));
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY));
			// 荷主名称（桁数チェック）
			param.setConsignorName(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORNAME_KEY));
			// 仕分予定日
			param.setPlanDate(this.getViewState().getString(ListSortingDateBusiness.SORTINGPLANDATE_KEY));
			// 商品コード
			param.setItemCode(this.getViewState().getString(ListSortingItemBusiness.ITEMCODE_KEY));
			// 商品名称
			param.setItemName(this.getViewState().getString(ListSortingItemBusiness.ITEMNAME_KEY));
			// ケース入数
			param.setEnteringQty(Integer.parseInt(this.getViewState().getString(SortingPlanRegistBusiness.CASEQTY)));
			// ボール入数
			param.setBundleEnteringQty(Integer.parseInt(this.getViewState().getString(SortingPlanRegistBusiness.BUNDLEQTY)));
			// ケースITF
			param.setITF(this.getViewState().getString(SortingPlanRegistBusiness.CASEITF));
			// ボールITF
			param.setBundleITF(this.getViewState().getString(SortingPlanRegistBusiness.BUNDLEITF));

			// ケース/ピース区分
			if (rdo_CpfCase.getChecked())
			{
				// ケース
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_CASE);
			}
			else
			{
				// ピース
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_PIECE);
			}
			// クロス/DC
			if (rdo_CrossDCFlagDC.getChecked())
			{
				// DC
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
			}
			else
			{
				// クロスTC
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_CROSSTC);
			}
			// 出荷先コード
			param.setCustomerCode(txt_CustomerCode.getText());
			// 出荷先名称
			param.setCustomerName(txt_CustomerName.getText());
			// 出荷伝票No
			param.setShippingTicketNo(txt_ShippingTicketNo.getText());
			// 出荷伝票行No
			param.setShippingLineNo(Formatter.getInt(txt_ShippingTicketLineNo.getText()));
			// 予定ケース数
			param.setPlanCaseQty(txt_PlanCaseQty.getInt());
			// 予定ピース数
			param.setPlanPieceQty(txt_PlanPieceQty.getInt());
			// 仕分場所
			param.setSortingLocation(txt_PickingPlace.getText());
			// ケース仕分場所
			param.setCaseSortingLocation(txt_PickingPlace.getText());
			// ピース仕分場所
			param.setPieceSortingLocation(txt_PickingPlace.getText());
			// 仕入先コード
			param.setSupplierCode(txt_SupplierCode.getText());
			// 仕入先名称
			param.setSupplierName(txt_SupplierName.getText());
			// 入荷伝票No
			param.setInstockTicketNo(txt_InstockTicketNo.getText());
			// 入荷伝票行No
			param.setInstockLineNo(Formatter.getInt(txt_InstockTicketLineNo.getText()));

			// スケジュールパラメータへわたすためうちエリアの情報をセットします
			// ためうちエリア情報格納用
			SortingParameter[] listparam = null;

			if (lst_SortingPlanRegist.getMaxRows() == 1)
			{
				// ためうちにデータがなければnullをセット
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールクラスを生成します
			WmsScheduler schedule = new SortingPlanRegistSCH();

			// コネクションと入力エリア情報、ためうちエリア情報(修正行を除く)でスケジュールを実行します
			if (schedule.check(conn, param, listparam))
			{
				// 結果がtrueであればためうちエリアにデータをセットします
				if (this.getViewState().getInt(LINENO) == -1)
				{
					// 新規入力であれば、ためうちに追加します
					// ためうちエリアに行を追加します
					lst_SortingPlanRegist.addRow();
					// 追加した行を編集対象行にします
					lst_SortingPlanRegist.setCurrentRow(lst_SortingPlanRegist.getMaxRows() - 1);
					// データをセットします
					setListRow();
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新します
					// 編集行を指定します
					lst_SortingPlanRegist.setCurrentRow(this.getViewState().getInt(LINENO));
					// データをセットします
					setListRow();
					// 選択行の色を元に戻すします
					lst_SortingPlanRegist.resetHighlight();
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
	 * 概要：入力エリアをクリアします。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.入力エリアの項目を初期値に戻します。 <BR>
	 * <DIR>
	 * ･初期値については <CODE>setInitView()</CODE> メソッドを参照してください。 <BR>
	 * </DIR> 
	 * 2.カーソルを出荷先コードにセットします。 <BR>
	 * 3.ためうちエリアの内容は保持します。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力エリアを初期化します
		setInitView();
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
	 * 概要:ためうちエリアの情報で、仕分予定登録を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを出荷先コードにセットします。<BR>
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
	 * 						viewState.作業者コード* <BR>
	 * 						viewState.パスワード* <BR>
	 * 						荷主コード* <BR>
	 * 						荷主名称 <BR>
	 *						仕分予定日* <BR>
	 *						商品コード* <BR>
	 *						商品名称 <BR>
	 *						区分* <BR>
	 *						ケース入数 <BR>
	 *						ボール入数 <BR>
	 *						ケースITF <BR>
	 *						ボールITF <BR>
	 *						ためうち.出荷先コード <BR>
	 *						ためうち.出荷先名称 <BR>
	 *						ためうち.出荷伝票No <BR>
	 *						ためうち.出荷伝票行No <BR>
	 *						ためうち.予定ケース数 <BR>
	 *						ためうち.予定ピース数 <BR>
	 *						ためうち.仕分場所 <BR>
	 *						ためうち.クロス/DC <BR>
	 *						ためうち.仕入先コード <BR>
	 *						ためうち.仕入先名称 <BR>
	 *						ためうち.入荷伝票No <BR>
	 *						ためうち.入荷伝票行No <BR>
	 *						端末No.<BR>
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
		Connection conn = null;
		try
		{
			// カーソルを出荷先コードにセットする
			setFocus(txt_CustomerCode);

			// 更新データ格納用
			SortingParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1);

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new SortingPlanRegistSCH();

			// スケジュールを開始します
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、コミットします
				conn.commit();

				// ためうちエリアを初期化します
				setInitViewDetail();
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
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
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
	 *				4.カーソルを出荷先コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// カーソルを出荷先コードに設定します
		setFocus(txt_CustomerCode);

		// ためうちエリアを初期化します
		setInitViewDetail();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanRegist_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanRegist_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanRegist_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanRegist_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanRegist_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanRegist_Change(ActionEvent e) throws Exception
	{
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
	 *				4.カーソルを出荷先コードにセットします。<BR>
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
	 *    4.カーソルを出荷先コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingPlanRegist_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_SortingPlanRegist.getActiveCol() == LST_CLRBTN)
		{
			// リスト削除
			lst_SortingPlanRegist.removeRow(lst_SortingPlanRegist.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンを無効にします
			// ためうちにデータがなければnullをセット
			if (lst_SortingPlanRegist.getMaxRows() == 1)
			{
				// ためうちエリアのボタン押下を不可にします
				// 登録ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}

			// 修正状態をデフォルトに戻します(ためうち行Noと背景色)
			this.getViewState().setInt(LINENO, -1);
			lst_SortingPlanRegist.resetHighlight();

			// カーソルを出荷先コードにセットします
			setFocus(txt_CustomerCode);

		}
		// 修正ボタンクリック時
		else if (lst_SortingPlanRegist.getActiveCol() == LST_MODBTN)
		{
			// ボタンが押下された行を入力エリアに表示します。
			// 修正対象行を指定します
			lst_SortingPlanRegist.setCurrentRow(lst_SortingPlanRegist.getActiveRow());

			// ケースピース区分
			getCasePieceFlagChk(lst_SortingPlanRegist.getValue(LST_CPFNM));
			// クロス／ＤＣ区分
			getCrossDcChk(lst_SortingPlanRegist.getValue(LST_CROSSDC));
			// 出荷先コード
			txt_CustomerCode.setText(lst_SortingPlanRegist.getValue(LST_CUSTCD));
			// 出荷先名称
			txt_CustomerName.setText(lst_SortingPlanRegist.getValue(LST_CUSTNM));
			// 出荷伝票No
			txt_ShippingTicketNo.setText(lst_SortingPlanRegist.getValue(LST_CUSTTKNO));
			// 出荷伝票行No
			txt_ShippingTicketLineNo.setText(lst_SortingPlanRegist.getValue(LST_CUSTLINENO));
			// 予定ケース数
			txt_PlanCaseQty.setText(lst_SortingPlanRegist.getValue(LST_PLANCASEQTY));
			// 予定ピース数
			txt_PlanPieceQty.setText(lst_SortingPlanRegist.getValue(LST_PLANPIECEQTY));
			// 仕分場所
			txt_PickingPlace.setText(lst_SortingPlanRegist.getValue(LST_SORTPLACE));
			// 仕入先コード
			txt_SupplierCode.setText(lst_SortingPlanRegist.getValue(LST_SPLCD));
			// 仕入先名称
			txt_SupplierName.setText(lst_SortingPlanRegist.getValue(LST_SPLNM));
			// 入荷伝票No
			txt_InstockTicketNo.setText(lst_SortingPlanRegist.getValue(LST_INSTKTKNO));
			// 入荷伝票行No
			txt_InstockTicketLineNo.setText(lst_SortingPlanRegist.getValue(LST_INSTKLINENO));

			// ViewStateに保存
			// 修正状態を判断するためにViewStateにためうち行No.をセットします
			this.getViewState().setInt(LINENO, lst_SortingPlanRegist.getActiveRow());
			// 修正行を薄黄色に変更します
			lst_SortingPlanRegist.setHighlight(lst_SortingPlanRegist.getActiveRow());
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostPlanCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_HostPlanPieceQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FSortingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FSortingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_BscDtlRst_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class

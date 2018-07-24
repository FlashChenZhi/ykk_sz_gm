// $Id: SortingCustomerBusiness.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingcustomer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingCustomerSCH;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : S.Ishigane<BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * 出荷先別仕分設定画面クラスです。 <BR>
 * 出荷先別仕分設定を行うスケジュールにパラメータを引き渡します。 <BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.表示ボタン押下処理( <CODE>btn_View_Click()</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 画面から入力された内容をパラメータとして、ためうちエリア出力用のデータをデータベースから取得します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * 作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * 仕分予定日* <BR>
 * 商品コード <BR>
 * ケース／ピース区分 <BR>
 * クロス／ＤＣ区分 <BR>
 * <BR>
 * 2.仕分開始ボタン押下処理( <CODE>btn_PickingStart_Click()</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 画面から入力した内容のパラメータを入力チェックをします。 <BR>
 * そのパラメータをviewStateに格納し、スケジュールに引き渡します。 <BR>
 * スケジュールから受け取った値とパラメータを元に仕分予定メンテナンス修正･削除(詳細)画面へ遷移します。 <BR>
 * <BR>
 * [ViewStateパラメータ] *必須入力 <BR>
 * <BR>
 * 作業者コード* <BR>
 * パスワード* <BR>
 * 仕分作業リスト発行区分 <BR>
 * ためうち行No.* <BR>
 * 作業No.* <BR>
 * 最終更新日時* <BR>
 * 端末No.*
 * <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SortingCustomerBusiness extends SortingCustomer implements WMSConstants
{
	// Class fields --------------------------------------------------
	// 遷移先アドレス
	/**
	 * 荷主検索ポップアップアドレス
	 */ 
	private static final String DO_SRCH_CONSIGNOR = "/sorting/listbox/listsortingconsignor/ListSortingConsignor.do";

	/**
	 * 仕分予定日検索ポップアップアドレス
	 */
	private static final String DO_SRCH_SORTINGPLANDATE = "/sorting/listbox/listsortingdate/ListSortingDate.do";
	
	/**
	 * 商品一覧検索ポップアップアドレス
	 */
	private static final String DO_SRCH_ITEM = "/sorting/listbox/listsortingitem/ListSortingItem.do";

	/**
	 * 仕分予定検索ポップアップアドレス
	 */
	private static final String DO_SRCH_SORTINGPLAN = "/sorting/listbox/listsortingplanregist/ListsortingPlanRegist.do";
	
	/**
	 * 検索ポップアップ呼び出し中画面アドレス
	 */
	private static final String DO_SRCH_PROCESS = "/progress.do";
	
	/**
	 * 作業者コード
	 */
	private static final String WORKERCODE = "WORKERCODE";
	
	/**
	 * パスワード
	 */
	private static final String PASSWORD = "PASSWORD";
	
	/**
	 * メッセージ
	 */
	private static final String MESSAGE = "MESSAGE";
	
	/**
	 * クロス/DC名称
	 */
	private static String CROSSDC_PARAM = "CROSSDC_PARAM";
	
	/**
	 * ケース/ピース区分名称
	 */
	private static String CPF_PARAM = "CPF_PARAM";
	
	
	// リストセル列指定番号
	// 隠しパラメータ(リストセル)
	private static final int LST_HDN = 0;
	// 取消ボタン(リストセル)
	private static final int LST_CLRBTN = 1;
	// 商品コード
	private static final int LST_ITEMCD = 2;
	// ケース/ピース区分(リストセル)
	private static final int LST_CPF = 3;
	// クロス／ＤＣ(リストセル)
	private static final int LST_CROSSDC = 4;
	// 仕分総数
	private static final int LST_SORTTOTAL = 5;
	// ケース入数
	private static final int LST_CASEETR = 6;
	// 仕分ケース数
	private static final int LST_SORTCASEQTY = 7;
	// 仕分先数
	private static final int LST_SORTPLACEQTY = 8;
	// 商品名称
	private static final int LST_ITEMNM = 9;
	// ボール入数
	private static final int LST_BUNDLEETR = 10;
	// 仕分ピース数
	private static final int LST_SORTPIECEQTY = 11;

	// リストセル隠しパラメータ
	// 作業No.（配列の要素数）
	private static final int HDNARYJOBNO = 0;
	// 最終更新日時（配列の要素数）
	private static final int HDNARYLASTUPDATEDATE = 1;
	// 作業No.
	private static final int HDNJOBNO = 2;
	// 最終更新日時
	private static final int HDNLASTUPDATEDATE = 3;
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 概要:このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *       1.ダイアログメッセージを設定します。 <BR>
	 *       2.カーソルを作業者コードにセットします。 <BR>
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
		}
		// 仕分開始ボタン押下時確認ダイアログMSG-W0019=開始しますか？
		btn_PickingStart.setBeforeConfirm("MSG-W0019");

		// 一覧クリアボタン押下時確認ダイアログMSG-W0012=一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		//　作業者コードにフォーカスセットします
		setFocus(txt_WorkerCode);
	}
	
	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者コード [なし] <BR>
	 * パスワード [なし] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 仕分予定日 [なし] <BR>
	 * 商品コード [なし] <BR>
	 * ケース/ピース区分 ["全て（ケースピース同一）"選択] <BR>
	 * クロス/ＤＣ区分 ["全て"選択] <BR>
	 * 仕分作業リスト発行 ["チェックあり"] <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		this.setInitView(true);
		this.setInitDetailView();
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
		// 荷主コード
		String consignorcode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 予定日
		Date plandate =
			WmsFormatter.toDate(
			param.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY),
			this.getHttpRequest().getLocale());
		// 商品コード
		String itemcode = param.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 出庫予定日
		if (!StringUtil.isBlank(plandate))
		{
			txt_PickingPlanDate.setDate(plandate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 入力エリアの初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * [パラメーター] <BR>
	 * <DIR>
	 * wkrClrFlg trueの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化、falseの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化しない <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者ｺｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * ﾊﾟｽﾜｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 仕分予定日 [なし] <BR>
	 * 商品コード [なし] <BR>
	 * ケース/ピース区分 ["全て（ケースピース同一）"選択] <BR>
	 * クロス/ＤＣ区分 ["全て"選択] <BR>
	 * 仕分作業リスト発行 ["チェックあり"] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR>
	 * @param wkrClrFlg 作業者コードの初期化を行うかどうかのフラグ
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView(final boolean wkrClrFlg) throws Exception
	{
		// 荷主コードにカーソル遷移
		setFocus(this.txt_WorkerCode);
		
		// 引数がtrueの場合は作業者コード、パスワードを初期化します
		if (wkrClrFlg)
		{
			this.txt_WorkerCode.setText("");
			this.txt_Password.setText("");
		}
		
		// 荷主コード
		this.txt_ConsignorCode.setText("");
		// 仕分予定日
		this.txt_PickingPlanDate.setText("");
		// 商品コード
		this.txt_ItemCode.setText("");
		// ケースピース区分(全て-ケースピース別)
		this.rdo_CpfAllDist.setChecked(false);
		// ケースピース区分(ケース)
		this.rdo_CpfCase.setChecked(false);
		// ケースピース区分(ピース)
		this.rdo_CpfPiece.setChecked(false);
		// ケースピース区分(全て-ケースピース同一)
		this.rdo_CpfAllAnd.setChecked(true);
		// クロスＤＣ区分(クロス)
		this.rdo_CrossDCFlagCross.setChecked(false);
		// クロスＤＣ区分(ＤＣ)
		this.rdo_CrossDCFlagDC.setChecked(false);
		// クロスＤＣ区分(全て)
		this.rdo_CrossDCFlagAll.setChecked(true);
		// 仕分作業リストを発行するチェックボックス
		this.chk_CommonUseEvent.setChecked(true);
		
		Connection conn = null;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new SortingCustomerSCH();
		
			SortingParameter param = (SortingParameter)schedule.initFind(conn,null);
			
			// 荷主コードが1件の場合、初期表示させる。
			if( param != null )
			{
				this.txt_ConsignorCode.setText(param.getConsignorCode());
			}
			else
			{
				this.txt_ConsignorCode.setText("");
			}
		}
		catch( Exception ex )
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
	}

	/**
	 * ためうちエリアの初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの初期表示を行います。 <BR>
	 * <DIR>
	 * <BR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitDetailView() throws Exception
	{
		// 荷主コード(ためうち)
		txt_JavaSetConsignorCode.setText("");
		// 荷主名称(ためうち)
		txt_JavaSetConsignorName.setText("");
		// 仕分予定日(ためうち)
		txt_JavaSetPickingDate.setText("");
		// ためうちエリアボタン項目を無効化します
		setButtonEnabled(false);
		// リストセルを初期化します
		this.lst_SortingCustomer.clearRow();
	}
	/** 
	 * ためうちにあるボタンのEnabledプロパティを変更します。<BR>
	 * 
	 * @param enabled 変更後のEnabledの状態。 
	 */
	private void setButtonEnabled(boolean enabled)
	{
		btn_PickingStart.setEnabled(enabled);
		btn_ListClear.setEnabled(enabled);
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
	 * 			ためうち情報* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @param lineNo 対象行No
	 * @throws Exception 全ての例外を報告します。 
	 */
	private SortingParameter[] setListParam(int lineNo) throws Exception
	{
		Vector vecParam = new Vector();

		// 端末No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String termNo = userHandler.getTerminalNo();
		int col = 1;
		int linecount = lst_SortingCustomer.getMaxRows() + 1;
		
		for (int i = 1; i < lst_SortingCustomer.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineNo)
			{
				continue;
			}

			col++;
			
			// 行指定
			lst_SortingCustomer.setCurrentRow(i);

			// スケジュールパラメータへセット
			SortingParameter param = new SortingParameter();
			
			// スケジュールパラメータをセットします。
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// リスト発行フラグ
			param.setSortingWorkListFlg(chk_CommonUseEvent.getChecked());
			
			// ためうちエリア情報
			// 荷主コード
			param.setConsignorCode(txt_JavaSetConsignorCode.getText());
			// 仕分予定日
			param.setPlanDate(WmsFormatter.toParamDate(this.txt_JavaSetPickingDate.getText(), this.httpRequest.getLocale()));
			
			// リストセル情報
			// 商品コード
			param.setItemCode(lst_SortingCustomer.getValue(LST_ITEMCD));
			// 商品名称
			param.setItemName(lst_SortingCustomer.getValue(LST_ITEMNM));
			// ケース・ピース区分
			param.setCasePieceFlag(getCpfToCd());
			// クロス・ＤＣ区分
			param.setTcdcFlag(getCrossDcToCd());
			// ケース入数
			param.setEnteringQty(WmsFormatter.getInt(lst_SortingCustomer.getValue(LST_CASEETR)));
			// ボール入数
			param.setBundleEnteringQty(WmsFormatter.getInt(lst_SortingCustomer.getValue(LST_BUNDLEETR)));
			// 仕分総数
			param.setTotalPlanQty(WmsFormatter.getInt(lst_SortingCustomer.getValue(LST_SORTTOTAL)));
			// 仕分ケース数
			param.setPlanCaseQty(WmsFormatter.getInt(lst_SortingCustomer.getValue(LST_SORTCASEQTY)));
			// 仕分ピース数
			param.setPlanPieceQty(WmsFormatter.getInt(lst_SortingCustomer.getValue(LST_SORTPIECEQTY)));
			// 仕分先数
			param.setSortingQty(WmsFormatter.getInt(lst_SortingCustomer.getValue(LST_SORTPLACEQTY)));
			// HIDDEN項目セット(作業No.(0),最終更新日時(1))
			String hidden = lst_SortingCustomer.getValue(0);
			String orgAryJobNo = CollectionUtils.getString(0, hidden);
			String orgAryLastDate = CollectionUtils.getString(1, hidden);


			//隠し項目の作業No、最終更新日時は可変の為、適用範囲をintStrPos、intEndPosに保持する
			 int intStrPos = 2;
			 int intEndPos = 2 + Formatter.getInt(orgAryJobNo);
			 // 作業No.を取得
			 Vector orgJobNoList = new Vector();
			 for(int j = intStrPos; j < intEndPos; j++)
			 {
				 orgJobNoList.add(CollectionUtils.getList(hidden).get(j));
			 }
	
	
			 // 最終更新日時を取得
			 Vector orgLastDateList = new Vector();
			 intStrPos = intEndPos;
			 intEndPos = intEndPos + Formatter.getInt(orgAryLastDate);
			 for(int j = intStrPos; j < intEndPos; j++)
			 {
				 String strLastUpDate = (String) CollectionUtils.getList(hidden).get(j);
				 orgLastDateList.add(WmsFormatter.getTimeStampDate(strLastUpDate));
			 }

			// 行No.を保持しておく
			param.setRowNo(i);
			// 作業No.
			param.setJobNoList(orgJobNoList);
			// 最終更新日時
			param.setLastUpdateDateList(orgLastDateList);



			// viewState情報等など
			// リスト発行用ケースピース区分(ヘッダ)
			param.setCasePieceName(this.viewState.getString(CPF_PARAM));
			// リスト発行用クロスDC区分(ヘッダ)
			param.setTcdcName(this.viewState.getString(CROSSDC_PARAM));
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 端末No
			param.setTerminalNumber(termNo);
			
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
	 * HIDDEN項目を１つの文字列に連結するためのメソッドです。<BR>
	 * <BR>
	 * HIDDENの項目順一覧 <BR>
	 * <DIR>
	 *    0:作業No. <BR>
	 *    1:最終更新日時 <BR>
	 * </DIR>
	 * 
	 * @param viewParam ShippingParameter HIDDEN項目を含む<CODE>Parameter</CODE>クラス。
	 * @return 連結したHIDDEN項目
	 */
	private String createHiddenList(SortingParameter viewParam)
	{
		// 隠し項目文字列作成 
		ArrayList hiddenList = new ArrayList();
		// 隠し項目
		// 作業No.（要素数）
		hiddenList.add(HDNARYJOBNO, Integer.toString(viewParam.getJobNoList().size()));
		
		// 最終更新日時（要素数）
		hiddenList.add(HDNARYLASTUPDATEDATE, Integer.toString(viewParam.getLastUpdateDateList().size()));
		// 作業No.
		Vector vecJobNo = viewParam.getJobNoList();
		for(int i = 0; i < vecJobNo.size(); i++)
		{
			hiddenList.add(vecJobNo.get(i));
		}
		
		// 最終更新日時 	
		Vector vecLastDate = viewParam.getLastUpdateDateList();
		for(int i = 0; i < vecLastDate.size(); i++)
		{
			hiddenList.add(WmsFormatter.getTimeStampString((Date)vecLastDate.get(i)));
		}
		return CollectionUtils.getConnectedString(hiddenList);
	}
	
	/**
	 * クロス/DC区分名称からパラメータにセットする値を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCrossDcToCd() throws Exception
	{
		// クロス/DC
		if (lst_SortingCustomer.getValue(4).equals(DisplayText.getText("LBL-W0358")))
		{
			// DC
			return SortingParameter.TCDC_FLAG_DC;
		}
		else if (lst_SortingCustomer.getValue(4).equals(DisplayText.getText("LBL-W0359")))
		{
			// クロス
			return SortingParameter.TCDC_FLAG_CROSSTC;
		}
		else
		{
			return "";
		}
	}

	/**
	 * ケース／ピース区分名称からパラメータにセットする値を取得します。<BR>
	 * <BR>
	 * @return ケースピース区分
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCpfToCd() throws Exception
	{
		// ケース
		if (lst_SortingCustomer.getValue(3).equals(DisplayText.getText("LBL-W0375")))
		{
			// ケース
			return SortingParameter.CASEPIECE_FLAG_CASE;
		}
		else if (lst_SortingCustomer.getValue(3).equals(DisplayText.getText("LBL-W0376")))
		{
			// ピース
			return SortingParameter.CASEPIECE_FLAG_PIECE;
		}
		// LBL-W0432=ケース／ピース
		else if (lst_SortingCustomer.getValue(3).equals(DisplayText.getText("LBL-W0432")))
		{
			// 混合
			return SortingParameter.CASEPIECE_FLAG_ALL;
		}
		return "";
	}
	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
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
		    ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 画面情報
		param.setParameter(
		    ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY,
		    SortingParameter.SEARCH_SORTING_WORK);

		param.setParameter(
			ListSortingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY,
			SortingParameter.STATUS_FLAG_UNSTARTED);

		// 処理中画面->結果画面
		redirect(
			SortingCustomerBusiness.DO_SRCH_CONSIGNOR,
			param,
			DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_PickingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_PickingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchPickingPlanDate_Server(ActionEvent e) throws Exception
	{
		
	}

	/** 
	 * 仕分予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕分予定日一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchPickingPlanDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 仕分予定日
		param.setParameter(
			ListSortingDateBusiness.SORTINGPLANDATE_KEY,
			WmsFormatter.toParamDate(this.txt_PickingPlanDate.getDate()));

		// 検索フラグ
		param.setParameter(
			ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY,
			SortingParameter.SEARCH_SORTING_WORK);

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		
		// 作業状態をセット
		param.setParameter(ListSortingDateBusiness.WORKSTATUSSORTINGPLANDATE_KEY, search);

		// 処理中画面->結果画面
		redirect(
			SortingCustomerBusiness.DO_SRCH_SORTINGPLANDATE,
			param,
			DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_All_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_All_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_Case_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_Case_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_Piece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_Piece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_AppointOff_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_Cpf_AppointOff_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_CrossDCTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlag_All_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlag_All_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlag_Cross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlag_Cross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlag_DC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlag_DC_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_PickingWorkList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * <BR>
	 * 表示ボタン押下処理 <BR>
	 * <BR>
	 * <DIR>
	 * 画面から入力された内容をパラメータとして、ためうちエリア出力用のデータをデータベースから取得します。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <BR>
	 * 作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 仕分予定日* <BR>
	 * 商品コード <BR>
	 * ケース／ピース区分 <BR>
	 * クロス／ＤＣ区分 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{	
		Connection conn = null;
		
		try
		{
			// 作業者コードにカーソル遷移
			setFocus(txt_WorkerCode);
			
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// ためうちエリアを初期化します
			setInitDetailView();
						
			// 入力チェックを行う（書式、必須、禁止文字）
			// 作業者コード
			this.txt_WorkerCode.validate();
			// パスワード
			this.txt_Password.validate();
			// 荷主コード
			this.txt_ConsignorCode.validate();
			// 仕分予定日
			this.txt_PickingPlanDate.validate();
			// 商品コード
			this.txt_ItemCode.validate(false);
			
			// スケジュールパラメータをセットする
			SortingParameter param = new SortingParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 仕分予定日
			param.setPlanDate(WmsFormatter.toParamDate(this.txt_PickingPlanDate.getDate()));
			// 商品コード
			param.setItemCode(this.txt_ItemCode.getText());
			
			// ケース／ピース区分
			if ( this.rdo_CpfAllAnd.getChecked())
			{
				// 全て（ケースピース同一）
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_ALL_SAME);
			}
			else if (this.rdo_CpfAllDist.getChecked())
			{
				// 全て（ケースピース別）
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_ALL_DISTINCTION);
			}
			else if (this.rdo_CpfCase.getChecked())
			{
				// ケース
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_CASE);
			}
			else if (this.rdo_CpfPiece.getChecked())
			{
				// ピース
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_PIECE);
			}
			
			// クロス/DC
			if(this.rdo_CrossDCFlagAll.getChecked())
			{
				// 全て
			    param.setTcdcFlag(SortingParameter.TCDC_FLAG_ALL);
			}
			else if (this.rdo_CrossDCFlagCross.getChecked())
			{
				// クロス
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_CROSSTC);
			}
			else if (this.rdo_CrossDCFlagDC.getChecked())
			{
				// DC
			    param.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
			}

			// スケジュールを開始する
			WmsScheduler schedule = new SortingCustomerSCH();
			SortingParameter[] viewParam = (SortingParameter[])schedule.query(conn, param);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if( viewParam == null || viewParam.length == 0 )
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			// スケジュールが正常に完了して表示データを取得した場合、表示する。
			// 荷主コード（ためうち）
			this.txt_JavaSetConsignorCode.setText(viewParam[0].getConsignorCode());
			// 荷主名（ためうち）
			this.txt_JavaSetConsignorName.setText(viewParam[0].getConsignorName());
			// 仕分予定日（ためうち）
			this.txt_JavaSetPickingDate.setDate(WmsFormatter.toDate(viewParam[0].getPlanDate()));
			
			// 仕分ケース数
			String label_plancaseqty = DisplayText.getText("LBL-W0440");
			// 仕分ピース数
			String label_planpieceqty = DisplayText.getText("LBL-W0441");
			// 仕分先数
			String label_totalplanqty = DisplayText.getText("LBL-W0256");	
					
			// リストセルに値をセットする
			for(int i = 0; i < viewParam.length; i++)
			{
				// 行追加
			    this.lst_SortingCustomer.addRow();
			    this.lst_SortingCustomer.setCurrentRow(i + 1);
				
			    int col = 0;
			    
				// 検索結果を各セルにセットしていく
			    // 隠しパラメータ情報
				// Hidden項目をセット
			    this.lst_SortingCustomer.setValue(LST_HDN, createHiddenList(viewParam[i]));
			    
				// （1列目）
			    // 商品コード
			    this.lst_SortingCustomer.setValue(LST_ITEMCD, viewParam[i].getItemCode());
			    // ケースピース区分
			    this.lst_SortingCustomer.setValue(LST_CPF, viewParam[i].getCasePieceName());
			    // クロスＤＣ区分
			    this.lst_SortingCustomer.setValue(LST_CROSSDC, viewParam[i].getTcdcName());
			    // 仕分総数
			    this.lst_SortingCustomer.setValue(LST_SORTTOTAL, Formatter.getNumFormat(viewParam[i].getTotalPlanQty()));
				// ケース入数
			    this.lst_SortingCustomer.setValue(LST_CASEETR, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
			    // 仕分ケース数
			    this.lst_SortingCustomer.setValue(LST_SORTCASEQTY, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			    // 仕分先数
			    this.lst_SortingCustomer.setValue(LST_SORTPLACEQTY, Formatter.getNumFormat(viewParam[i].getSortingQty()));
			    
				// （2列目）
				// 商品名称
			    this.lst_SortingCustomer.setValue(LST_ITEMNM, viewParam[i].getItemName());
				// ボール入数
			    this.lst_SortingCustomer.setValue(LST_BUNDLEETR, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// 仕分ピース数
			    this.lst_SortingCustomer.setValue(LST_SORTPIECEQTY, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));

				//ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				// 仕分ケース数			
				toolTip.add(label_plancaseqty, viewParam[i].getPlanCaseQty());
				// 仕分ピース数		
				toolTip.add(label_planpieceqty, viewParam[i].getPlanPieceQty());
				// 仕分先数		
				toolTip.add(label_totalplanqty, viewParam[i].getSortingQty());			

				//カレント行にTOOL TIPをセットする
				this.lst_SortingCustomer.setToolTip(this.lst_SortingCustomer.getCurrentRow(), toolTip.getText());
			}
			
			// ためうちエリアのボタンを有効化します
			setButtonEnabled(true);
			
			// 20050831 add start T.Kishimoto
			// ViewStateに保持する
			this.getViewState().setString(CPF_PARAM, param.getCasePieceFlag());
			this.getViewState().setString(CROSSDC_PARAM, param.getTcdcFlag());
			
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}		
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：作業者コード・パスワード以外のフィールドを初期表示状態に戻します。
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		this.setInitView(false);
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PickingStart_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分開始ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、仕分開始処理を行います。 <BR>
	 * </BR> <DIR>1.カーソルを作業者コードにセットします。 <BR>
	 * 2.viewStateから値を取得してパラメータにセットします。 <BR>
	 * 3.ためうちエリアの全情報をパラメータにセットします。 <BR>
	 * 4.スケジュールを開始します。 <BR>
	 * 5.ためうちエリアに、更新後のスケジュールからの情報を再取込みし、表示します。 <BR>
	 * 6.ためうち情報が存在しない場合、仕分開始ボタン・一覧クリアボタンは無効にします。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PickingStart_Click(ActionEvent e) throws Exception
	{	
		// 入力チェックを行う（書式、必須、禁止文字）
		this.txt_WorkerCode.validate();
		this.txt_Password.validate();
		
		this.txt_ConsignorCode.validate(false);
		this.txt_PickingPlanDate.validate(false);
		this.txt_ItemCode.validate(false);
		
		Connection conn = null;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// 更新データ格納用
			SortingParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1);

			// スケジュールの宣言
			WmsScheduler schedule = new SortingCustomerSCH();
			
			// スケジュールを開始する
			if (schedule.startSCH(conn, param))
			{
				// トランザクションをコミットします。
				conn.commit();
				// ため打ち部分をすべて初期化します
				this.setInitDetailView();
			}
			else
			{
				// トランザクションをロールバックします。
				conn.rollback();
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
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}


	/** 
	 * 一覧クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：ためうちエリアのフィールドを初期表示状態に戻します。
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		this.setInitDetailView();
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_JavaSetConsignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_SPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_JavaSetPickingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 取消ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの該当データをクリアします。<BR>
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
	 *              2.ためうち情報が存在しない場合、仕分開始ボタン･一覧クリアボタンは無効にします。<BR>
	 *				3.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SortingCustomer_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_SortingCustomer.getActiveCol() == LST_CLRBTN)
		{
			// リスト削除
			lst_SortingCustomer.removeRow(lst_SortingCustomer.getActiveRow());

			// ためうち情報が存在しない場合、ためうちエリアを初期化します
			if (lst_SortingCustomer.getMaxRows() == 1)
			{
				// ためうちエリア初期化します
				this.setInitDetailView();
			}

			// カーソルを作業者コードにセットします
			setFocus(txt_WorkerCode);
		}
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 商品検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 * 		 商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品コード検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 出荷予定日
		param.setParameter(
			ListSortingDateBusiness.SORTINGPLANDATE_KEY,
			WmsFormatter.toParamDate(this.txt_PickingPlanDate.getDate()));
			
		// 商品コード
		param.setParameter(
			ListSortingItemBusiness.ITEMCODE_KEY,
			this.txt_ItemCode.getText());
		
		// 画面情報
		param.setParameter(
			ListSortingItemBusiness.SEARCHITEM_KEY,
			SortingParameter.SEARCH_SORTING_WORK);
			
		param.setParameter(
			ListSortingItemBusiness.WORKSTATUSITEM_KEY,
			SortingParameter.STATUS_FLAG_UNSTARTED);

					
		// 処理中画面->結果画面
		redirect(
			SortingCustomerBusiness.DO_SRCH_ITEM,
			param,
			DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagAll_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagCross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagCross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagDC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagDC_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void chk_CommonUseEvent_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void chk_CommonUseEvent_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lbl_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
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
	public void rdo_CpfAllAnd_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAllAnd_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAllDist_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAllDist_Click(ActionEvent e) throws Exception
	{
	}


	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPickingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPickingDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPickingDate_TabKey(ActionEvent e) throws Exception
	{
	}


}
//end of class

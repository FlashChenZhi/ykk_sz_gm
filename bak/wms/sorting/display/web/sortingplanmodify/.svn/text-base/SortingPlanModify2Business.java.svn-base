// $Id: SortingPlanModify2Business.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingplanmodify;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.co.daifuku.wms.sorting.schedule.SortingPlanModifySCH;

/**
 * Designer :T.Uehata<BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * 仕分予定修正・削除(詳細情報修正削除)クラスです。 <BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示し、
 * ためうちエリア出力用のデータをためうちエリアに表示します。 <BR>
 * 仕分予定修正・削除を行うスケジュールにパラメータを引き渡します。 <BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。 <BR>
 * <BR>
 * リストセルの列番号一覧 <BR>
 * <DIR>
 * 1.削除ボタン <BR>
 * 2.修正ボタン <BR>
 * 3.クロス／ＤＣ <BR>
 * 4.出荷先コード <BR>
 * 5.出荷伝票Ｎｏ <BR>
 * 6.ホスト予定ケース数 <BR>
 * 7.ケース仕分場所 <BR>
 * 8.仕入先コード <BR>
 * 9.入荷伝票Ｎｏ <BR>
 * 10.出荷先名称 <BR>
 * 11.出荷伝票行Ｎｏ <BR>
 * 12.ホスト予定ピース数 <BR>
 * 13.ピース仕分場所 <BR>
 * 14.仕入先名称 <BR>
 * 14.入荷伝票行Ｎｏ <BR>
 * </DIR> <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理( <CODE>page_Load</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 基本情報入力画面の入力情報の一部を、上部表示領域に表示します。 <BR>
 * ViewStateから取得した内容をパラメータにセットし、ためうちエリア出力用のデータをスケジュールから取得します。 <BR>
 * 処理が正常に完了した場合ははスケジュールから取得したためうちエリア出力用のデータを元に、ためうちエリアの表示を行います。 <BR>
 * 該当データが見つからない場合は要素数0のParameter配列を、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>
 * [パラメータ] <BR>
 * <DIR>
 * 荷主コード <BR>
 * 荷主名称 <BR>
 * 仕分予定日 <BR>
 * 商品コード <BR>
 * 商品名称 <BR>
 * ケース入数 <BR>
 * ケースITF <BR>
 * ボール入数 <BR>
 * ボールITF <BR>
 * </DIR>
 * <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>
 * クロス/DC <BR> 
 * 出荷先コード <BR> 
 * 出荷先名称 <BR>
 * 出荷伝票No <BR>
 * 出荷伝票行No <BR>
 * ホスト予定ケース数 <BR>
 * ホスト予定ピース数 <BR>
 * ケース仕分場所 <BR>
 * ピース仕分場所 <BR>
 * 仕入先コード <BR>
 * 仕入先名称 <BR>
 * 入荷伝票No <BR>
 * 入荷伝票行No <BR>
 * 仕分予定一意ｷｰ <BR>
 * 最終更新日時 <BR>
 * </DIR> <BR>
 * </DIR> <BR>
 * 2.入力ボタン押下処理( <CODE>btn_Input_Click</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 入力エリアから入力した内容をパラメータにセットし、
 * そのパラメータを基にスケジュールが入力条件のチェックを行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、 <BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * 結果がtrueの時は、入力エリアの情報でためうちの修正対象データを更新します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <DIR>
 * クロス/DC* <BR>
 * 出荷先コード* <BR>
 * 出荷先名称 <BR>
 * 出荷伝票No* <BR>
 * 出荷伝票行No* <BR>
 * ホスト予定ケース数* <BR>
 * ホスト予定ピース数* <BR>
 * ケース仕分場所 <BR>
 * ピース仕分場所 <BR>
 * 仕入先コード*1 <BR>
 * 仕入先名称 <BR>
 * 入荷伝票No*1 <BR>
 * 入荷伝票行No*1 <BR>
 * 仕分予定一意ｷｰ* <BR>
 * 最終更新日時* <BR>
 * <BR>
 * *1はクロス/DC.クロス選択時必須入力
 * </DIR>
 * </DIR>
 * </DIR> <BR>
 * <BR>
 * 3.修正登録ボタン押下処理( <CODE>btn_ModifySubmit_Click</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * ためうちエリアから入力した内容をパラメータにセットし、
 * そのパラメータを基にスケジュールが仕分予定データ修正処理を行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合は
 * はためうちエリア出力用のデータをスケジュールから再取得して、ためうちエリアの再表示を行います。 <BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はnullを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>
 * 作業者コード <BR>
 * パスワード <BR>
 * 仕分予定日 <BR>
 * 荷主コード <BR>
 * ケース入数 <BR>
 * ボール入数 <BR>
 * ケースITF <BR>
 * ボールITF <BR>
 * ためうち.クロス/DC <BR>
 * ためうち.出荷先コード <BR>
 * ためうち.出荷先名称 <BR>
 * ためうち.出荷伝票No <BR>
 * ためうち.出荷伝票行No <BR>
 * ためうち.ホスト予定ケース数 <BR>
 * ためうち.ホスト予定ピース数 <BR>
 * ためうち.ケース仕分場所 <BR>
 * ためうち.ピース仕分場所 <BR>
 * ためうち.仕入先コード <BR>
 * ためうち.仕入先名称 <BR>
 * ためうち.入荷伝票No <BR>
 * ためうち.入荷伝票行No <BR>
 * ためうち.仕分予定一意ｷｰ <BR>
 * ためうち.最終更新日時 <BR>
 * </DIR>
 * <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>
 * クロス/DC <BR>
 * 出荷先コード <BR>
 * 出荷先名称 <BR>
 * 出荷伝票No <BR>
 * 出荷伝票行No <BR>
 * ホスト予定ケース数 <BR>
 * ホスト予定ピース数 <BR>
 * ケース仕分場所 <BR>
 * ピース仕分場所 <BR>
 * 仕入先コード <BR>
 * 仕入先名称 <BR>
 * 入荷伝票No <BR>
 * 入荷伝票行No <BR>
 * 仕分予定一意ｷｰ <BR>
 * 最終更新日時 <BR>
 * </DIR>
 * </DIR> <BR>
 * 4.削除、全削除ボタン押下処理( <CODE>lst_SShpPlnDaMdDlt_Click</CODE> メソッド) <BR>
 * <BR>
 * <DIR>ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが仕分予定削除を行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、 <BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>
 * 荷主コード* <BR>
 * 商品コード* <BR>
 * 仕分予定日* <BR>
 * ケース入数 <BR>
 * ボール入数 <BR>
 * ケースITF <BR>
 * ボールITF <BR>
 * ためうち.仕分予定一意ｷｰ* <BR>
 * ためうち.最終更新日時* <BR>
 * 端末No* <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * リストセル<BR>
 * <DIR>
 *		1.削除ボタン<BR>
 *		2.修正ボタン<BR>
 *		3.ｸﾛｽ/DC区分<BR>
 *		4.出荷先コード<BR>
 *		5.出荷伝票No<BR>
 *		6.ﾎｽﾄ予定ｹｰｽ数<BR>
 *		7.ｹｰｽ仕分場所<BR>
 *		8.仕入先ｺｰﾄﾞ<BR>
 *		9.入荷伝票No<BR>
 *		10.出荷先名称<BR>
 *		11.出荷伝票行No<BR>
 *		12.ﾎｽﾄ予定ﾋﾟｰｽ数<BR>
 *		13.ﾋﾟｰｽ仕分場所<BR>
 *		14.仕入先名称<BR>
 *		15.入荷伝票行No<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingPlanModify2Business extends SortingPlanModify2 implements WMSConstants
{
	// viewState用KEY(隠しパラメータ)
	// ためうち行No.
	private static final String LINENO = "LINENO";
	// 仕分予定一意ｷｰ
	private static final String SORTINGPLANUKEY = "SORTINGPLANUKEY";
	// 最終更新日時
	private static final String LASTUPDDATE = "LASTUPDDATE";
	// クロスDC区分
	private static final String CROSSDCFLG = "CROSSDCFLG";

	// リストセル列指定変数
	// 隠しパラメータ(リストセル)
	private static final int LST_HDN = 0;
	// 削除ボタン(リストセル)
	private static final int LST_DELBTN = 1;
	// 修正ボタン(リストセル)
	private static final int LST_MODBTN = 2;
	// 出荷先コード(リストセル)
	private static final int LST_CUSTCD = 4;
	// 出荷先伝票No(リストセル)
	private static final int LST_SHIPTKNO = 5;
	// 予定ケース数(リストセル)
	private static final int LST_PLANCASEQTY = 6;
	// ケース仕分場所(リストセル)
	private static final int LST_CASESORTPLACE = 7;
	// クロス/DC(リストセル)
	private static final int LST_CROSSDC = 3;
	// 仕入先コード(リストセル)
	private static final int LST_SPLCD = 8;
	// 入荷伝票No(リストセル)
	private static final int LST_INSTKTKNO = 9;
	// 出荷先名称(リストセル)
	private static final int LST_CUSTNM = 10;
	// 出荷伝票行No(リストセル)
	private static final int LST_SIHPLINENO = 11;
	// 予定ピース数(リストセル)
	private static final int LST_PLANPIECEQTY = 12;
	// ピース仕分場所(リストセル)
	private static final int LST_PIECESORTPLACE = 13;
	// 仕入先名称(リストセル)
	private static final int LST_SPLNM = 14;
	// 入荷伝票行No(リストセル)
	private static final int LST_INSTKLINENO = 15;

	// リストセル隠し項目順序
	// 仕分予定一意ｷｰ
	private static final int HDNIDX_UKEY = 0;
	// 最終更新日時
	private static final int HDNIDX_UPDAY = 1;
	/**
	 * 更新フラグ(隠しパラメータ順序)
	 */
	private static final int HDN_UPDATEFLAG = 2;
	/**
	 * ｸﾛｽ/DCフラグ(DBの値)(隠しパラメータ順序)
	 */
	private static final int HDN_ORG_TCDCFLAG = 3;
	/**
	 * 出荷先コード(隠しパラメータ順序)
	 */
	private static final int HDN_CUSTOMERCODE = 4;
	/**
	 * 出荷先名称(隠しパラメータ順序)
	 */
	private static final int HDN_CUSTOMERNAME = 5;
	/**
	 * 出荷伝票No.(隠しパラメータ順序)
	 */
	private static final int HDN_SHIP_TICKETNO = 6;
	/**
	 * 出荷伝票行No.(隠しパラメータ順序)
	 */
	private static final int HDN_SHIP_LINENO = 7;
	/**
	 * ホスト予定ケース数(隠しパラメータ順序)
	 */
	private static final int HDN_HOSTCASEQTY = 8;
	/**
	 * ホスト予定ピース数(隠しパラメータ順序)
	 */
	private static final int HDN_HOSTPIECEQTY = 9;
	/**
	 * ケース仕分場所(隠しパラメータ順序)
	 */
	private static final int HDN_CASELOCATION = 10;
	/**
	 * ピース仕分場所(隠しパラメータ順序)
	 */
	private static final int HDN_PIECELOCATION = 11;
	/**
	 * 仕入先コード(隠しパラメータ順序)
	 */
	private static final int HDN_SUPPLIERCODE = 12;
	/**
	 * 仕入先名称(隠しパラメータ順序)
	 */
	private static final int HDN_SUPPLIERNAME = 13;
	/**
	 * 入荷伝票No.(隠しパラメータ順序)
	 */
	private static final int HDN_INST_TICKETNO = 14;
	/**
	 * 入荷伝票行No.(隠しパラメータ順序)
	 */
	private static final int HDN_INST_LINENO = 15;
	/**
	 * ｸﾛｽ/DCフラグ(現在の値)(隠しパラメータ順序)
	 */
	private static final int HDN_NEW_TCDCFLAG = 16;

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
		//　修正登録ボタン押下時確認ダイアログ "修正登録しますか？"
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		//　全削除ボタン押下時確認ダイアログ "全てのデータを削除しますか？"
		btn_AllDelete.setBeforeConfirm("MSG-W0031");
		
		// カーソルを出荷先コードに設定します
		setFocus(txt_CustomerCode);
	}

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * [パラメータ] <BR>
	 * <DIR>
	 * 荷主コード <BR>
	 * 荷主名称 <BR>
	 * 仕分予定日 <BR>
	 * 商品コード <BR>
	 * 商品名称 <BR>
	 * ケース入数 <BR>
	 * ケースITF <BR>
	 * ボール入数 <BR>
	 * ボールITF <BR>
	 * </DIR>
	 * <BR>
	 * [返却データ] <BR>
	 * <BR>
	 * <DIR>
	 * クロス/DC <BR>
	 * 出荷先コード <BR>
	 * 出荷先名称 <BR>
	 * 出荷伝票No <BR>
	 * 出荷伝票行No <BR>
	 * ホスト予定ケース数 <BR>
	 * ホスト予定ピース数 <BR>
	 * ケース仕分場所 <BR>
	 * ピース仕分場所 <BR>
	 * 仕入先コード <BR>
	 * 仕入先名称 <BR>
	 * 入荷伝票No <BR>
	 * 入荷伝票行No <BR>
	 * 仕分予定一意ｷｰ <BR>
	 * 最終更新日時 <BR>
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
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(SortingPlanModifyBusiness.TITLE)));		

		Connection conn = null;

		try
		{
			// 画面を初期化します
			setInitView();
			setInitViewDetail();

			// 詳細情報修正・削除のタブを前出しします
			tab_BscDtlMdfyDlt.setSelectedIndex(2);

			// スケジュールパラメータへセットします
			SortingParameter param = new SortingParameter();
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY));
			// 仕分予定日
			param.setPlanDate(this.getViewState().getString(ListSortingDateBusiness.SORTINGPLANDATE_KEY));
			// 商品コード
			param.setItemCode(this.getViewState().getString(ListSortingItemBusiness.ITEMCODE_KEY));

			// ためうちエリアを初期化します
			lst_PickingPlanModify.clearRow();

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new SortingPlanModifySCH();

			// ためうちエリアに表示するデータを取得します
			SortingParameter[] viewparam = (SortingParameter[]) schedule.query(conn, param);

			// エラーなどが発生した場合はメッセージを受け取り、基本情報設定画面に戻る
			if (viewparam == null || viewparam.length == 0)
			{
				// ViewStateにメッセージをセット
				this.getViewState().setString(SortingPlanModifyBusiness.MESSAGE, schedule.getMessage());
				// 詳細情報修正・削除画面->基本情報設定画面
				forward(SortingPlanModifyBusiness.DO_MODIFY);

				return;
			}

			// 入力エリアの固定項目を設定します
			// 荷主コード
			lbl_JavaSetConsignorCode.setText(viewparam[0].getConsignorCode());
			// 荷主名称
			lbl_JavaSetConsgnorName.setText(viewparam[0].getConsignorName());
			// 仕分予定日
			txt_FSortingPlanDate.setDate(WmsFormatter.toDate(viewparam[0].getPlanDate()));
			// 商品コード
			lbl_JavaSetItemCode.setText(viewparam[0].getItemCode());
			// 商品名称
			lbl_JavaSetItemName.setText(viewparam[0].getItemName());

			// ケース入数
			lbl_JavaSetCaseEntering.setText(WmsFormatter.getNumFormat(viewparam[0].getEnteringQty()));
			// ボール入数
			lbl_JavaSetBundleEntering.setText(WmsFormatter.getNumFormat(viewparam[0].getBundleEnteringQty()));
			// ケースITF
			lbl_JavaSetCaseItf.setText(viewparam[0].getITF());
			// ボールITF
			lbl_JavaSetBundleItf.setText(viewparam[0].getBundleITF());

			// ためうちエリア.リストセルに取得データを表示します
			setList(viewparam);

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
			
			// ためうち行にデータがないとき
			if (lst_PickingPlanModify.getMaxRows() == 1)
			{
				// 修正登録・全削除ボタンを無効化します
				// 登録修正ボタン
				btn_ModifySubmit.setEnabled(false);
				// 全削除ボタン
				btn_AllDelete.setEnabled(false);
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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
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
		// ケース仕分場所
		String _casesortingplace = param.getParameter(ListSortingLocationBusiness.CASESORTINGLOCATION_KEY);
		// ピース仕分場所
		String _piecesortingplace = param.getParameter(ListSortingLocationBusiness.PIECESORTINGLOCATION_KEY);
		// 仕入先コード
		String _supliercd = param.getParameter(ListSortingSupplierBusiness.SUPPLIERCODE_KEY);
		// 仕入先名称
		String _supliername = param.getParameter(ListSortingSupplierBusiness.SUPPLIERNAME_KEY);
		// 

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
		// ケース仕分場所
		if (!StringUtil.isBlank(_casesortingplace))
		{
			txt_CasePickingPlace.setText(_casesortingplace);
		}
		// ピース仕分場所
		if (!StringUtil.isBlank(_piecesortingplace))
		{
			txt_PiecePickingPlace.setText(_piecesortingplace);
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
		// フォーカスを出荷先コードにセット
		setFocus(txt_CustomerCode);
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
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
		if (!checker.checkContainNgText(txt_CasePickingPlace))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_PiecePickingPlace))
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
		// ケース仕分場所
		txt_CasePickingPlace.setText("");
		// ピース仕分場所
		txt_PiecePickingPlace.setText("");
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
	 * 概要:画面のためうちエリアの設定を初期化します。 <BR>
	 * <BR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitViewDetail() throws Exception
	{
		// 入力エリアのボタンを無効化します
		// 入力ボタン
		btn_Input.setEnabled(false);
		// クリアボタン
		btn_Clear.setEnabled(false);

		// 修正状態(ためうち行No)を初期値へリセットします
		this.getViewState().setInt(LINENO, -1);

		// 選択行の色を初期値へリセットします
		lst_PickingPlanModify.resetHighlight();
	}

	/**
	 * ためうちエリアにParameterの値をセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:ためうちエリアにParameterの値をセットします。 <BR>
	 * <DIR>
	 * 隠し項目 
	 * <DIR>
	 * 0.仕分予定一意ｷｰ <BR>
	 * 1.最終更新日時 <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * <DIR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * ためうちエリアの内容を持つ <CODE>SortingParameter</CODE> クラスのインスタンスの配列 <BR>
	 * </DIR>
	 * </DIR>
	 * page_Load(ActionEvent)から呼ばれます。
	 * 
	 * @param param SortingParameter[] ためうちエリア情報
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setList(SortingParameter[] param) throws Exception
	{
		// バルーンヘルプ表示用に名称を取得する
		String customerName = DisplayText.getText("LBL-W0036");
		String supplierCode = DisplayText.getText("LBL-W0251");
		String supplierName = DisplayText.getText("LBL-W0253");
		String instktktno = DisplayText.getText("LBL-W0091");
		String instktktlineno = DisplayText.getText("LBL-W0090");
		

		for (int i = 0; i < param.length; i++)
		{
			// 行追加
			lst_PickingPlanModify.addRow();

			// 編集行を指定します
			lst_PickingPlanModify.setCurrentRow(i + 1);

			//ToolTip用のデータを編集
			ToolTipHelper toolTip = new ToolTipHelper();
			// 出荷先名称
			toolTip.add(customerName, param[i].getCustomerName());
			// 仕入先コード
			toolTip.add(supplierCode, param[i].getSupplierCode());
			// 仕入先名称
			toolTip.add(supplierName, param[i].getSupplierName());
			// 入荷伝票No
			toolTip.add(instktktno, param[i].getInstockTicketNo());
			// 入荷伝票行No
			toolTip.add(instktktlineno, param[i].getInstockLineNo());
			//カレント行にTOOL TIPをセットする
			lst_PickingPlanModify.setToolTip(lst_PickingPlanModify.getCurrentRow(), toolTip.getText());

			// 隠しパラメータをセットする
			Vector hidden = new Vector();
			// 隠しパラメータ.仕分予定一意ｷｰ
			hidden.add(param[i].getSortingPlanUkey());
			// 隠しパラメータ.最終更新日時
			hidden.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			// 更新フラグ：更新なし
			hidden.add(SortingParameter.UPDATEFLAG_NO);
			// TC/DCフラグ(DBのもの)
			hidden.add(param[i].getTcdcFlag());
			// 出荷先コード
			hidden.add(param[i].getCustomerCode());
			// 出荷先名称
			hidden.add(param[i].getCustomerName());
			// 出荷伝票No.
			hidden.add(param[i].getShippingTicketNo());
			// 出荷伝票行No.
			hidden.add(WmsFormatter.getNumFormat(param[i].getShippingLineNo()));
			// ホスト予定ケース数
			hidden.add(WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			// ホスト予定ピース数
			hidden.add(WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			// ケース仕分場所
			hidden.add(param[i].getCaseSortingLocation());
			// ピース仕分場所
			hidden.add(param[i].getPieceSortingLocation());
			// 仕入先コード
			hidden.add(param[i].getSupplierCode());
			// 仕入先名称
			hidden.add(param[i].getSupplierName());
			// 入荷伝票No.
			hidden.add(param[i].getInstockTicketNo());
			// 入荷伝票行No.
			hidden.add(WmsFormatter.getNumFormat(param[i].getInstockLineNo()));
			// TC/DCフラグ(現在のもの)
			hidden.add(param[i].getTcdcFlag());
			
			// 隠しパラメータをリストセルに格納
			lst_PickingPlanModify.setValue(LST_HDN, CollectionUtils.getConnectedString(hidden));
			// クロス/DC
			lst_PickingPlanModify.setValue(LST_CROSSDC, param[i].getTcdcName());
			// 出荷先コード
			lst_PickingPlanModify.setValue(LST_CUSTCD, param[i].getCustomerCode());
			// 出荷先名称
			lst_PickingPlanModify.setValue(LST_CUSTNM, param[i].getCustomerName());
			// 出荷伝票No
			lst_PickingPlanModify.setValue(LST_SHIPTKNO, param[i].getShippingTicketNo());
			// 出荷伝票行No
			lst_PickingPlanModify.setValue(LST_SIHPLINENO, Formatter.getNumFormat(param[i].getShippingLineNo()));
			// ホスト予定ケース数
			lst_PickingPlanModify.setValue(LST_PLANCASEQTY, Formatter.getNumFormat(param[i].getPlanCaseQty()));
			// ホスト予定ピース数
			lst_PickingPlanModify.setValue(LST_PLANPIECEQTY, Formatter.getNumFormat(param[i].getPlanPieceQty()));
			// ケース仕分場所
			lst_PickingPlanModify.setValue(LST_CASESORTPLACE, param[i].getCaseSortingLocation());
			// ピース仕分場所
			lst_PickingPlanModify.setValue(LST_PIECESORTPLACE, param[i].getPieceSortingLocation());
			// 仕入先コード
			lst_PickingPlanModify.setValue(LST_SPLCD, param[i].getSupplierCode());
			// 仕入先名称
			lst_PickingPlanModify.setValue(LST_SPLNM, param[i].getSupplierName());
			// 入荷伝票No
			lst_PickingPlanModify.setValue(LST_INSTKTKNO, param[i].getInstockTicketNo());
			// 入荷伝票行No
			lst_PickingPlanModify.setValue(LST_INSTKLINENO, Formatter.getNumFormat(param[i].getInstockLineNo()));
		}
	}

	/**
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。 <BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1) <BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。 <BR>
	 * 3.修正登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1) <BR>
	 * <DIR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * 修正対象ためうち行No.*(修正対象がない場合は-1をセットする) <BR>
	 * </DIR>
	 * [返却データ] <BR>
	 * <DIR>
	 * ためうちエリアの内容を持つ <CODE>SortingParameter</CODE> クラスのインスタンスの配列 <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param lineno 対象となる行No
	 * @param check データを更新するかどうか
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private SortingParameter[] setListParam(int lineno,boolean check) throws Exception
	{
		Vector vecParam = new Vector();

		String workercd = viewState.getString(SortingPlanModifyBusiness.WORKERCODE);
		String password = viewState.getString(SortingPlanModifyBusiness.PASSWORD);

		// 端末No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String termNo = userHandler.getTerminalNo();

		for (int i = 1; i < lst_PickingPlanModify.getMaxRows(); i++)
		{
			// 修正対象行は処理しません
			if (i == lineno)
			{
				continue;
			}

			// 編集行を指定します
			lst_PickingPlanModify.setCurrentRow(i);

			// 登録時、変更されていないデータはセットしない
			if (check && lineno < 0 
			 && CollectionUtils.getString(HDN_UPDATEFLAG, lst_PickingPlanModify.getValue(LST_HDN)).equals(SortingParameter.UPDATEFLAG_NO))
			{
				continue;
			}

			// スケジュールパラメータへセットします
			SortingParameter param = new SortingParameter();

			// 基本画面情報
			// 作業者コード
			param.setWorkerCode(workercd);
			// パスワード
			param.setPassword(password);
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(lbl_JavaSetConsgnorName.getText());
			// 仕分予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FSortingPlanDate.getDate()));
			// 商品コード
			param.setItemCode(lbl_JavaSetItemCode.getText());
			// 商品名称
			param.setItemName(lbl_JavaSetItemName.getText());
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lbl_JavaSetCaseEntering.getText()));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lbl_JavaSetBundleEntering.getText()));
			// ケースITF
			param.setITF(lbl_JavaSetCaseItf.getText());
			// ボールITF
			param.setBundleITF(lbl_JavaSetBundleItf.getText());

			// ためうちエリア.リストセルの内容
			// 出荷先コード
			param.setCustomerCode(lst_PickingPlanModify.getValue(LST_CUSTCD));
			// 出荷先名称
			param.setCustomerName(lst_PickingPlanModify.getValue(LST_CUSTNM));
			// 出荷伝票No
			param.setShippingTicketNo((lst_PickingPlanModify.getValue(LST_SHIPTKNO)));
			// 出荷伝票行No
			param.setShippingLineNo(Formatter.getInt(lst_PickingPlanModify.getValue(LST_SIHPLINENO)));
			// ホスト予定ケース数
			param.setPlanCaseQty(Formatter.getInt(lst_PickingPlanModify.getValue(LST_PLANCASEQTY)));
			// ホスト予定ピース数
			param.setPlanPieceQty(Formatter.getInt(lst_PickingPlanModify.getValue(LST_PLANPIECEQTY)));
			// ケース仕分場所
			param.setCaseSortingLocation(lst_PickingPlanModify.getValue(LST_CASESORTPLACE));
			// ピース仕分場所
			param.setPieceSortingLocation(lst_PickingPlanModify.getValue(LST_PIECESORTPLACE));
			// 仕入先コード
			param.setSupplierCode(lst_PickingPlanModify.getValue(LST_SPLCD));
			// 仕入先名称
			param.setSupplierName(lst_PickingPlanModify.getValue(LST_SPLNM));
			// 入荷伝票No
			param.setInstockTicketNo(lst_PickingPlanModify.getValue(LST_INSTKTKNO));
			// 入荷伝票行No
			param.setInstockLineNo(Formatter.getInt(lst_PickingPlanModify.getValue(LST_INSTKLINENO)));

			// 隠し項目
			// 仕分予定一意ｷｰ
			param.setSortingPlanUkey(CollectionUtils.getString(HDNIDX_UKEY, lst_PickingPlanModify.getValue(LST_HDN)));
			// 最終更新日時
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HDNIDX_UPDAY, lst_PickingPlanModify.getValue(LST_HDN))));
			// クロス/DC区分(現在の)
			param.setTcdcFlag(CollectionUtils.getString(HDN_NEW_TCDCFLAG, lst_PickingPlanModify.getValue(LST_HDN)));

			// 端末No.
			param.setTerminalNumber(termNo);
			// ためうち行No
			param.setRowNo(i);

			// 1行分をひとまとめにして追加
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセットします
			SortingParameter[] listparam = new SortingParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			// セットするためうちデータがなければnullをセットします
			return null;
		}
	}

	/**
	 * ためうちエリアのクロス/DC区分から対応するパラメータを取得します。<BR>
	 * <BR>
	 * @param crossFlg クロス/DC区分
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCrossDcParam(boolean crossFlg)
	{
		// クロス/DC
		if (crossFlg)
		{
			// クロス
			return SortingParameter.TCDC_FLAG_CROSSTC;
		}
		else 
		{
			// DC
			return SortingParameter.TCDC_FLAG_DC;
		}
	}
	
	/**
	 * ためうちエリアに入力エリアの修正データセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:ためうちエリアに入力エリアの修正データをセットします。 <BR>
	 * このメソッドを呼ぶ前に、リストセルをsetCurrentRow(int)で編集対象行を指定してください。 <BR>
	 * <DIR>
	 * 隠し項目 <BR>
	 * <DIR>
	 * 0.仕分予定一意ｷｰ <BR>
	 * 1.最終更新日時 <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
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
		toolTip.add(DisplayText.getText("LBL-W0090"), WmsFormatter.getNumFormat(txt_InstockTicketLineNo.getInt()));

		// カレント行にTOOL TIPをセットする
		lst_PickingPlanModify.setToolTip(lst_PickingPlanModify.getCurrentRow(), toolTip.getText());

		// 隠しパラメータ
		ArrayList hidden = (ArrayList) CollectionUtils.getList(lst_PickingPlanModify.getValue(LST_HDN));
		// 隠しパラメータ.更新フラグ
		if (updateCheck())
		{
			hidden.set(HDN_UPDATEFLAG, SortingParameter.UPDATEFLAG_YES);
		}
		else
		{
			hidden.set(HDN_UPDATEFLAG, SortingParameter.UPDATEFLAG_NO);
		}
		// 隠しパラメータ．現在のTC/DCフラグ
		if (rdo_CrossDCFlagDC.getChecked())
		{
			hidden.set(HDN_NEW_TCDCFLAG, SortingParameter.TCDC_FLAG_DC);
		}
		else
		{
			hidden.set(HDN_NEW_TCDCFLAG, SortingParameter.TCDC_FLAG_CROSSTC);
		}

		// 隠しパラメータをリストセルに格納
		lst_PickingPlanModify.setValue(LST_HDN, CollectionUtils.getConnectedString(hidden));
		// クロス/DC
		lst_PickingPlanModify.setValue(LST_CROSSDC, getCrossDcName());
		// 出荷先コード
		lst_PickingPlanModify.setValue(LST_CUSTCD, txt_CustomerCode.getText());
		// 出荷先名称
		lst_PickingPlanModify.setValue(LST_CUSTNM, txt_CustomerName.getText());
		// 出荷伝票No
		lst_PickingPlanModify.setValue(LST_SHIPTKNO, txt_ShippingTicketNo.getText());
		// 出荷伝票行No
		lst_PickingPlanModify.setValue(LST_SIHPLINENO, WmsFormatter.getNumFormat(txt_ShippingTicketLineNo.getInt()));
		// ホスト予定ケース数
		lst_PickingPlanModify.setValue(LST_PLANCASEQTY, WmsFormatter.getNumFormat(txt_PlanCaseQty.getInt()));
		// ホスト予定ピース数
		lst_PickingPlanModify.setValue(LST_PLANPIECEQTY, WmsFormatter.getNumFormat(txt_PlanPieceQty.getInt()));
		// ケース仕分場所
		lst_PickingPlanModify.setValue(LST_CASESORTPLACE, txt_CasePickingPlace.getText());
		// ピース仕分場所
		lst_PickingPlanModify.setValue(LST_PIECESORTPLACE, txt_PiecePickingPlace.getText());
		// 仕入先コード
		lst_PickingPlanModify.setValue(LST_SPLCD, txt_SupplierCode.getText());
		// 仕入先名称
		lst_PickingPlanModify.setValue(LST_SPLNM, txt_SupplierName.getText());
		// 入荷伝票No
		lst_PickingPlanModify.setValue(LST_INSTKTKNO, txt_InstockTicketNo.getText());
		// 入荷伝票行No
		lst_PickingPlanModify.setValue(LST_INSTKLINENO, WmsFormatter.getNumFormat(txt_InstockTicketLineNo.getInt()));

	}
	
	/**
	 * 入力エリアとためうちエリアで変更箇所があるか判断するメソッドです。<BR>
	 * <BR>
	 * 概要:入力エリアとためうちエリアを比較し、変更があればtrueを返します。<BR>
	 * @return 変更がある場合はtrue
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean updateCheck() throws Exception
	{
		String hiddenParam = lst_PickingPlanModify.getValue(LST_HDN);
		
		// TC/DCフラグ(DBのものと比較)
		if (rdo_CrossDCFlagDC.getChecked())
		{
			if (!CollectionUtils.getString(HDN_ORG_TCDCFLAG, hiddenParam).equals(SortingParameter.TCDC_FLAG_DC))
				return true;
		}
		else
		{
			if (!CollectionUtils.getString(HDN_ORG_TCDCFLAG, hiddenParam).equals(SortingParameter.TCDC_FLAG_CROSSTC))
				return true;
		}
		// 出荷先コード
		if (!txt_CustomerCode.getText().equals(CollectionUtils.getString(HDN_CUSTOMERCODE, hiddenParam)))
		{
			return true;
		}
		// 出荷先名称
		if (!txt_CustomerName.getText().equals(CollectionUtils.getString(HDN_CUSTOMERNAME, hiddenParam)))
		{
			return true;
		}
		// 出荷伝票No.
		if (!txt_ShippingTicketNo.getText().equals(CollectionUtils.getString(HDN_SHIP_TICKETNO, hiddenParam)))
		{
			return true;
		}
		// 出荷伝票行No.
		if (txt_ShippingTicketLineNo.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_SHIP_LINENO, hiddenParam)))
		{
			return true;
		}
		// ホスト予定ケース数
		if (txt_PlanCaseQty.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_HOSTCASEQTY, hiddenParam)))
		{
			return true;
		}
		// ホスト予定ピース数
		if (txt_PlanPieceQty.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_HOSTPIECEQTY, hiddenParam)))
		{
			return true;
		}
		// ケース仕分場所
		if (!txt_CasePickingPlace.getText().equals(CollectionUtils.getString(HDN_CASELOCATION, hiddenParam)))
		{
			return true;
		}
		// ピース仕分場所
		if (!txt_PiecePickingPlace.getText().equals(CollectionUtils.getString(HDN_PIECELOCATION, hiddenParam)))
		{
			return true;
		}
		// 仕入先コード
		if (!txt_SupplierCode.getText().equals(CollectionUtils.getString(HDN_SUPPLIERCODE, hiddenParam)))
		{
			return true;
		}
		// 仕入先名称
		if (!txt_SupplierName.getText().equals(CollectionUtils.getString(HDN_SUPPLIERNAME, hiddenParam)))
		{
			return true;
		}
		// 入荷伝票No.
		if (!txt_InstockTicketNo.getText().equals(CollectionUtils.getString(HDN_INST_TICKETNO, hiddenParam)))
		{
			return true;
		}
		// 入荷伝票行No.
		if (txt_InstockTicketLineNo.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_INST_LINENO, hiddenParam)))
		{
			return true;
		}

		return false;
	}

	/**
	 * TC/DC区分をためうちエリアの文字列表記を取得し、
	 * ラジオボタンにチェックをつけます。<BR>
	 * 
	 */
	private void getCrossDcChk()
	{
		if ((CollectionUtils.getString(HDN_NEW_TCDCFLAG, lst_PickingPlanModify.getValue(LST_HDN))).equals(SortingParameter.TCDC_FLAG_DC))
		{
			// DC
			rdo_CrossDCFlagDC.setChecked(true);
			rdo_CrossDCFlagCross.setChecked(false);
		}
		else
		{
			// クロスTC
			rdo_CrossDCFlagDC.setChecked(false);
			rdo_CrossDCFlagCross.setChecked(true);
		}
	}

	/**
	 * クロス/DC区分から対応する名称を取得します。<BR>
	 * 入力ボタン押下時、ためうち表示する名称を取得します。<BR>
	 * @return チェック状態に対応する文字列を取得します。
	 */
	private String getCrossDcName()
	{
		// クロス/DC
		if (rdo_CrossDCFlagDC.getChecked())
		{
			// ＤＣ
			return DisplayText.getText("LBL-W0358");
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			// クロス
			return DisplayText.getText("LBL-W0359");
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
		this.getViewState().setString(SortingPlanModifyBusiness.MESSAGE, "");
		// 詳細情報登録画面->基本情報設定画面
		forward(SortingPlanModifyBusiness.DO_MODIFY);
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
	public void lbl_JavaSetPickingPlanDate_Server(ActionEvent e) throws Exception
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
	public void lbl_CasePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickingPlace_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickingPlace_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CasePickingPlace_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickingPlace_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickingPlace_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PiecePickingPlace_InputComplete(ActionEvent e) throws Exception
	{
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
	 * 入力ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、仕分予定情報を検索し、ためうちに表示します。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.カーソルを出荷先コードにセットします。 <BR>
	 * 2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性) <BR>
	 * 3.スケジュールを開始します。 <BR>
	 * <DIR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * クロス/DC* <BR>
	 * 出荷先コード* <BR>
	 * 出荷先名称 <BR>
	 * 出荷伝票No* <BR>
	 * 出荷伝票行No* <BR>
	 * ホスト予定ケース数* <BR>
	 * ホスト予定ピース数* <BR>
	 * ケース仕分場所 <BR>
	 * ピース仕分場所 <BR>
	 * 仕入先コード*1 <BR>
	 * 仕入先名称 <BR>
	 * 入荷伝票No*1 <BR>
	 * 入荷伝票行No*1 <BR>
	 * 仕分予定一意ｷｰ* <BR>
	 * 最終更新日時* <BR>
	 * <BR>
	 * *1はクロス/DC.クロス選択時必須入力
	 * </DIR>
	 * </DIR> <BR>
	 * 4.スケジュールの結果がtrueであれば、入力エリアの情報でためうちの修正対象データを更新します。 <BR>
	 * 5.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。 <BR>
	 * 6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 7.スケジュールの結果がtrueであれば、入力ボタン・クリアボタンを無効にします。 <BR>
	 * 8.スケジュールの結果がtrueであれば、入力エリアの内容はクリアします。 <BR>
	 * 9.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを出荷先コードへセットします
		setFocus(txt_CustomerCode);
		
		// 必須入力チェック
		// 出荷先コード
		txt_CustomerCode.validate();
		// パターンマッチング
		// 出荷先名称
		txt_CustomerName.validate(false);
		// 出荷伝票No
		txt_ShippingTicketNo.validate(false);
		// 出荷伝票行No
		txt_ShippingTicketLineNo.validate(false);
		// ホスト予定ケース数
		txt_PlanCaseQty.validate(false);
		// ホスト予定ピース数
		txt_PlanPieceQty.validate(false);
		// ケース仕分場所
		txt_CasePickingPlace.validate(false);
		// ピース仕分場所
		txt_PiecePickingPlace.validate();
		
		//if PieceLocation is not empty and Case qty is input, then CaseLocation should be input
		if(!txt_PiecePickingPlace.getText().equals("") && (Formatter.getInt(txt_PlanCaseQty.getText()) > 0))
		{
			if(txt_CasePickingPlace.getText().equals(""))
			{	            
				//メッセージをセット
				// 6023375=ホスト予定ケース数が1以上の場合、ケース仕分場所を入力してください。
				message.setMsgResourceKey("6023375");				
				//throw new Exception();
				return;
	            
			}
		}

		// ｸﾛｽ/DC区分.ｸﾛｽ選択時チェック
		if (rdo_CrossDCFlagCross.getChecked())
		{
			// 仕入先コード
			txt_SupplierCode.validate();
			// 仕分先名称
			txt_SupplierName.validate(false);
			// 入荷伝票No
			txt_InstockTicketNo.validate();
			// 入荷伝票行No
			txt_InstockTicketLineNo.validate();	
		}
		
		// ｸﾛｽ/DC区分.ｸﾛｽ以外選択時パターンマッチング
		if (!rdo_CrossDCFlagCross.getChecked())
		{
			// 仕入先コード
			txt_SupplierCode.validate(false);
			// 仕分先名称
			txt_SupplierName.validate(false);
			// 入荷伝票No
			txt_InstockTicketNo.validate(false);
			// 入荷伝票行No
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
		    
		    
			// スケジュールパラメータへ画面内容をセットします
			SortingParameter param = new SortingParameter();

			// ユーザーが入力できない値(ラベル項目、隠しパラメータ)
			// 仕分予定一意ｷｰ
			param.setSortingPlanUkey(this.getViewState().getString(SORTINGPLANUKEY));
			// 最終更新日時
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDDATE)));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(lbl_JavaSetConsgnorName.getText());
			// 仕分予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FSortingPlanDate.getDate()));
			// 商品コード
			param.setItemCode(lbl_JavaSetItemCode.getText());
			// 商品名称
			param.setItemName(lbl_JavaSetItemName.getText());
			// ITF
			param.setITF(lbl_JavaSetCaseItf.getText());
			// ボールITF
			param.setBundleITF(lbl_JavaSetBundleItf.getText());
			// ケース入数
			param.setEnteringQty(WmsFormatter.getInt(lbl_JavaSetCaseEntering.getText()));
			// ボール入数
			param.setBundleEnteringQty(WmsFormatter.getInt(lbl_JavaSetBundleEntering.getText()));
			

			// ユーザーが入力できる項目(テキストボックス)
			// クロス/DC
			if (rdo_CrossDCFlagDC.getChecked())
			{
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
			}
			else if (rdo_CrossDCFlagCross.getChecked())
			{
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
			// ホスト予定ケース数
			param.setPlanCaseQty(Formatter.getInt(txt_PlanCaseQty.getText()));
			// ホスト予定ピース数
			param.setPlanPieceQty(Formatter.getInt(txt_PlanPieceQty.getText()));
			// ケース仕分場所
			param.setCaseSortingLocation(txt_CasePickingPlace.getText());
			// ピース仕分場所
			param.setPieceSortingLocation(txt_PiecePickingPlace.getText());
			// 仕入先コード
			param.setSupplierCode(txt_SupplierCode.getText());
			// 仕入先名称
			param.setSupplierName(txt_SupplierName.getText());
			// 入荷伝票No
			param.setInstockTicketNo(txt_InstockTicketNo.getText());
			// 入荷伝票行No
			param.setInstockLineNo(Formatter.getInt(txt_InstockTicketLineNo.getText()));

			// 最終更新日時
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDDATE)));
			// 仕分予定一意キー
			param.setSortingPlanUkey(this.getViewState().getString(SORTINGPLANUKEY));

			// スケジュールパラメータへためうち情報をセットします(スケジュール側の入力チェックで必要になります)
			// ためうちエリア
			SortingParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_PickingPlanModify.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt(LINENO), true);
			}		
		    
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールクラスのインスタンスを生成します
			WmsScheduler schedule = new SortingPlanModifySCH();

			// スケジュールを開始します
			// スケジュールクラスで、入力チェックと存在チェックを行います
			if (schedule.check(conn, param, listparam))
			{
				// 修正対象行を編集対象とします
				lst_PickingPlanModify.setCurrentRow(this.getViewState().getInt(LINENO));

				// ためうちに入力エリアの値をセット
				setListRow();

				// 入力エリアの初期化を行います
				setInitView();
				// ためうちエリアの状態を初期化します
				setInitViewDetail();
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
	 * 2.カーソルを商品コードにセットします。 <BR>
	 * 3.ためうちエリアの内容は保持します。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力データを空にします
		setInitView();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 修正登録ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、在庫データ修正登録を行います。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.カーソルを商品コードにセットします。 <BR>
	 * 2.修正登録を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <DIR>
	 * "修正登録しますか？"  <BR>
	 * [確認ダイアログ キャンセル] <BR>
	 * <DIR>
	 * 何も行いません。 
	 * </DIR>
	 * [確認ダイアログ OK] <BR>
	 * <DIR>
	 * 1.スケジュールを開始します。 <BR>
	 * <DIR>
	 * [パラメータ] <BR>
	 * <BR>
	 * <DIR>
	 * 作業者コード <BR>
	 * パスワード <BR>
	 * 仕分予定日 <BR>
	 * 荷主コード <BR>
	 * ケース入数 <BR>
	 * ボール入数 <BR>
	 * ケースITF <BR>
	 * ボールITF <BR>
	 * ためうち.クロス/DC <BR>
	 * ためうち.出荷先コード <BR>
	 * ためうち.出荷先名称 <BR>
	 * ためうち.出荷伝票No <BR>
	 * ためうち.出荷伝票行No <BR>
	 * ためうち.ホスト予定ケース数 <BR>
	 * ためうち.ホスト予定ピース数 <BR>
	 * ためうち.ケース仕分場所 <BR>
	 * ためうち.ピース仕分場所 <BR>
	 * ためうち.仕入先コード <BR>
	 * ためうち.仕入先名称 <BR>
	 * ためうち.入荷伝票No <BR>
	 * ためうち.入荷伝票行No <BR>
	 * ためうち.仕分予定一意ｷｰ <BR>
	 * ためうち.最終更新日時 <BR>
	 * </DIR>
	 * <BR>
	 * [返却データ] <BR>
	 * <BR>
	 * <DIR>
	 * クロス/DC <BR>
	 * 出荷先コード <BR>
	 * 出荷先名称 <BR>
	 * 出荷伝票No <BR>
	 * 出荷伝票行No <BR>
	 * ホスト予定ケース数 <BR>
	 * ホスト予定ピース数 <BR>
	 * ケース仕分場所 <BR>
	 * ピース仕分場所 <BR>
	 * 仕入先コード <BR>
	 * 仕入先名称 <BR>
	 * 入荷伝票No <BR>
	 * 入荷伝票行No <BR>
	 * 仕分予定一意ｷｰ <BR>
	 * 最終更新日時 <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * <BR>
	 * 2.スケジュールの結果がtrueの時は、返却データを元にためうちのデータを再表示します。 <BR>
	 * 3.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。 <BR>
	 * 4.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 5.スケジュールの結果がtrueの時は、入力・クリアボタンを無効にします。 <BR>
	 * 6.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。 <BR>
	 * 7.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * </DIR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを出荷先コードにセットします
			setFocus(txt_CustomerCode);

			// スケジュールパラメータへセットします
			SortingParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1,true);

			if (param == null || param.length <= 0)
			{
				// メッセージをセットします
				// 6003013=修正対象データがありませんでした。
				message.setMsgResourceKey("6003013");
				return;
			}
			
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 仕分予定修正・削除スケジュールクラスのインスタンスを生成します
			WmsScheduler schedule = new SortingPlanModifySCH();

			// 仕分予定の修正処理を実行します(修正後の表示データが返却されます)
			SortingParameter[] viewParam = (SortingParameter[]) schedule.startSCHgetParams(conn, param);

			// エラーなどが発生した場合は、メッセージを受け取って終了します
			if (viewParam == null || viewParam.length == 0)
			{
				// コネクションをロールバックします
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// 正常に完了した場合はコミットします
			conn.commit();

			// 処理が正常に完了した場合は、返却データを元にためうちエリアの再表示を行う
			// 前回のためうちエリアを削除
			lst_PickingPlanModify.clearRow();

			// ためうちエリアを再表示します
			setList(viewParam);

			// 入力エリアをクリアします
			setInitView();

			//ためうち行Noを初期化します
			viewState.setInt(LINENO, -1);

			// 背景色を初期化します
			lst_PickingPlanModify.resetHighlight();

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
	public void btn_AllDelete_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 全削除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全て削除します。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.ためうち情報の全削除を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <DIR>
	 * "全てのデータを削除しますか？" <BR>
	 * [確認ダイアログ キャンセル] <BR>
	 * <DIR>
	 * 何も行いません。 
	 * </DIR>
	 * [確認ダイアログ OK] <BR>
	 * <DIR>
	 * 1.スケジュールを開始します。 <BR>
	 * <DIR>
	 * [パラメータ] <BR>
	 * <DIR>
	 * ためうち.仕分予定一意キー
	 * 荷主コード
	 * 荷主名称
	 * 仕分予定日
	 * 商品コード
	 * 商品名称
	 * ケースITF
	 * ボールITF
	 * ケース入数
	 * ボール入数
	 * ためうち.最終更新日時 
	 * </DIR>
	 * </DIR>
	 * 2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。 <BR>
	 * 3.スケジュールの結果がtrueの時は、ためうちの表示情報を全て削除します。 <BR>
	 * 4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 5.スケジュールの結果がtrueの時は、修正登録・全削除・入力・クリアボタンを無効にします。 <BR>
	 * 6.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * 7.カーソルを出荷先コードにセットします。 <BR>
	 * </DIR> <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_AllDelete_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			// スケジュールパラメータへセット
			SortingParameter[] listparam = null;

			listparam = setListParam(-1, false);

			// 在庫修正・削除スケジュールクラスのインスタンスを生成します
			WmsScheduler schedule = new SortingPlanModifySCH();

			// コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 削除処理を実行
			if (schedule.startSCH(conn, listparam))
			{
				// 処理が正常に完了すればコミットします
				conn.commit();

				// ためうち行を全削除します
				lst_PickingPlanModify.clearRow();

				// 画面をクリアします
				setInitView();
				setInitViewDetail();

				// ボタンを無効化します
				// 修正登録ボタン
				btn_ModifySubmit.setEnabled(false);
				// 全削除ボタン
				btn_AllDelete.setEnabled(false);

				// メッセージをセットします
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				// 異常終了時はロールバックします
				conn.rollback();
				// メッセージをセットします
				message.setMsgResourceKey(schedule.getMessage());
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
	public void lst_PickingPlanModify_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanModify_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanModify_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanModify_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanModify_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanModify_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 削除、修正ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 削除ボタン概要:ためうちの該当データを削除します。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.ためうち情報の削除を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <DIR>
	 * "削除しますか？" <BR>
	 * [確認ダイアログ キャンセル] <BR>
	 * <DIR>
	 * 何も行いません。 <BR></DIR>
	 * [確認ダイアログ OK] <BR>
	 * <DIR>
	 * 1.スケジュールを開始します。 <BR>
	 * <DIR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * ためうち.仕分予定一意キー*
	 * 荷主コード
	 * 仕分予定日
	 * 商品コード
	 * ケース入数
	 * ボール入数
	 * ケースITF
	 * ボールITF
	 * ためうち.最終更新日時* 
	 * </DIR>
	 * </DIR>
	 * 2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。 <BR>
	 * 3.スケジュールの結果がtrueの時は、ためうちの該当データを削除します。 <BR>
	 * 4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 5.スケジュールの結果がtrueの時は、入力・クリアボタンを無効にします。 <BR>
	 * 6.ためうち情報が存在しない場合、修正登録・全削除ボタンを無効にします。 <BR>
	 * 7.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * 8.カーソルを出荷先コードにセットします。 <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR> <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.選択されたためうち行情報を上部の入力エリアに表示します。 <BR>
	 * 2.選択されたためうち行を薄黄色にします。 <BR>
	 * 3.ViewState.ためうち行No.に修正対象行を設定します。 <BR>
	 * 4.カーソルを出荷先コードにセットします。 <BR>
	 * 5.入力ボタン・クリアボタンを有効にします。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_PickingPlanModify_Click(ActionEvent e) throws Exception
	{
		// 削除ボタンクリック
		if (lst_PickingPlanModify.getActiveCol() == LST_DELBTN)
		{
			Connection conn = null;

			try
			{
				// 削除ボタンが押された行のデータを扱います
				lst_PickingPlanModify.setCurrentRow(lst_PickingPlanModify.getActiveRow());

				// スケジュールパラメータへセットします
				SortingParameter[] param = new SortingParameter[1];
				param[0] = new SortingParameter();

				// 仕分予定一意ｷｰ
				param[0].setSortingPlanUkey(CollectionUtils.getString(HDNIDX_UKEY, lst_PickingPlanModify.getValue(LST_HDN)));
				// 最終更新時間
				param[0].setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HDNIDX_UPDAY, lst_PickingPlanModify.getValue(LST_HDN))));
				// 作業者コード
				param[0].setWorkerCode(this.getViewState().getString(SortingPlanModifyBusiness.WORKERCODE));
				// パスワード
				param[0].setPassword(this.getViewState().getString(SortingPlanModifyBusiness.PASSWORD));
				// ためうち行No
				param[0].setRowNo(lst_PickingPlanModify.getActiveRow());
				
				// 仕分予定修正・削除スケジュールクラスのインスタンスを生成します
				WmsScheduler schedule = new SortingPlanModifySCH();

				// コネクションを取得します
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				// 削除処理を実行
				if (schedule.startSCH(conn, param))
				{
					// 処理が正常に完了すればコミットします
					conn.commit();

					// 削除されたためうち行を削除します
					lst_PickingPlanModify.removeRow(lst_PickingPlanModify.getActiveRow());

					// 画面の状態を初期化します
					setInitView();
					setInitViewDetail();

					// ためうち情報が存在しないとき、修正登録・全削除ボタンを無効化します
					if (lst_PickingPlanModify.getMaxRows() == 1)
					{
						// ボタンを無効化します
						// 修正登録ボタン
						btn_ModifySubmit.setEnabled(false);
						// 全削除ボタン
						btn_AllDelete.setEnabled(false);
					}

					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
				}
				else
				{
					// 異常終了時はロールバックします
					conn.rollback();
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
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
		// 修正ボタンクリック時
		else if (lst_PickingPlanModify.getActiveCol() == LST_MODBTN)
		{
			// カーソルを出荷先コードにセットします
			setFocus(txt_CustomerCode);

			// 修正ボタンが押された行のデータを扱います
			lst_PickingPlanModify.setCurrentRow(lst_PickingPlanModify.getActiveRow());

			// ためうちエリアの内容を、入力エリアへ展開します
			// TD/DC区分
			getCrossDcChk();
			// 出荷先コード
			txt_CustomerCode.setText(lst_PickingPlanModify.getValue(LST_CUSTCD));
			// 出荷先名称
			txt_CustomerName.setText(lst_PickingPlanModify.getValue(LST_CUSTNM));
			// 出荷伝票No
			txt_ShippingTicketNo.setText(lst_PickingPlanModify.getValue(LST_SHIPTKNO));
			// 出荷伝票行No
			txt_ShippingTicketLineNo.setText(lst_PickingPlanModify.getValue(LST_SIHPLINENO));
			// ホスト予定ケース数
			txt_PlanCaseQty.setText(lst_PickingPlanModify.getValue(LST_PLANCASEQTY));
			// ホスト予定ピース数
			txt_PlanPieceQty.setText(lst_PickingPlanModify.getValue(LST_PLANPIECEQTY));
			// ケース仕分場所
			txt_CasePickingPlace.setText(lst_PickingPlanModify.getValue(LST_CASESORTPLACE));
			// ピース仕分場所
			txt_PiecePickingPlace.setText(lst_PickingPlanModify.getValue(LST_PIECESORTPLACE));
			// 仕入先コード
			txt_SupplierCode.setText(lst_PickingPlanModify.getValue(LST_SPLCD));
			// 仕入先名称
			txt_SupplierName.setText(lst_PickingPlanModify.getValue(LST_SPLNM));
			// 入荷伝票No
			txt_InstockTicketNo.setText(lst_PickingPlanModify.getValue(LST_INSTKTKNO));
			// 入荷伝票行No
			txt_InstockTicketLineNo.setText(lst_PickingPlanModify.getValue(LST_INSTKLINENO));

			// 隠しパラメータをViewStateにセットします
			// 修正選択行
			this.getViewState().setInt(LINENO, lst_PickingPlanModify.getActiveRow());
			// 仕分予定一意ｷｰ
			this.getViewState().setString(SORTINGPLANUKEY, CollectionUtils.getString(HDNIDX_UKEY, lst_PickingPlanModify.getValue(LST_HDN)));
			// 最終更新日時
			this.getViewState().setString(LASTUPDDATE, CollectionUtils.getString(HDNIDX_UPDAY, lst_PickingPlanModify.getValue(LST_HDN)));
			// 対象修正行を黄色に変更します
			lst_PickingPlanModify.setHighlight(lst_PickingPlanModify.getActiveRow());

			// 入力エリア内ボタンを有効化します
			// 入力ボタン
			btn_Input.setEnabled(true);
			// クリアボタン
			btn_Clear.setEnabled(true);
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PiecePickingPlace_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchCasePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * ケース仕分場所の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕分場所検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       ケース仕分場所 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchCasePickingPlace_Click(ActionEvent e) throws Exception
	{
		// 仕分場所検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetConsignorCode.getText());
		// ケース仕分場所
		param.setParameter(ListSortingLocationBusiness.CASESORTINGLOCATION_KEY, txt_CasePickingPlace.getText());
		// 仕分場所範囲フラグ
		param.setParameter(ListSortingLocationBusiness.RANGESORTINGLOCATION_KEY, SortingParameter.RANGE_CASE);
		
		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLACE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPiecePickingPlace_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * ピース仕分場所の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕分場所検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       ピース仕分場所 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchPiecePickingPlace_Click(ActionEvent e) throws Exception
	{
		// 仕分場所検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetConsignorCode.getText());
		// ピース仕分場所
		param.setParameter(ListSortingLocationBusiness.PIECESORTINGLOCATION_KEY, txt_PiecePickingPlace.getText());
		// 仕分場所範囲フラグ
		param.setParameter(ListSortingLocationBusiness.RANGESORTINGLOCATION_KEY, SortingParameter.RANGE_PIECE);
		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLACE, param, DO_SRCH_PROCESS);
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


}
//end of class

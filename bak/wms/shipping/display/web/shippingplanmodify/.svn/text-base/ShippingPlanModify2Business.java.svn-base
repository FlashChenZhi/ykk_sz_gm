// $Id: ShippingPlanModify2Business.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingplanmodify;
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
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingsupplier.ListShippingSupplierBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingPlanModifySCH;

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * 出荷予定データ修正・削除(詳細情報登録)画面クラスです。<BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示し、ためうちエリア出力用のデータをためうちエリアに表示します。<BR>
 * 出荷予定データ修正・削除を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>page_Load</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   基本情報入力画面の入力情報を、上部表示領域に表示します。<BR>
 *	 ViewStateから取得した内容をパラメータにセットし、ためうちエリア出力用のデータをスケジュールから取得します。<BR>
 *   処理が正常に完了した場合はスケジュールから取得したためうちエリア出力用のデータを元に、ためうちエリアの表示を行います。<BR>
 *	 該当データが見つからない場合は要素数0のParameter配列を、条件エラーなどが発生した場合はnullを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *	 作業情報の状態フラグが未開始のデータのみを取得し、行No.順に表示を行います。 <BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		荷主コード* <BR>
 * 		出荷予定日* <BR>
 * 		出荷先コード* <BR>
 * 		出荷伝票No.* <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ]<BR>
 *   <BR>
 *   <DIR>
 * 		出荷予定日 <BR>
 * 		荷主コード <BR>
 * 		荷主名称 <BR>
 * 		出荷先コード <BR>
 * 		出荷先名称 <BR>
 * 		出荷伝票No. <BR>
 * 		出荷伝票行No. <BR>
 * 		商品コード <BR>
 * 		商品名称 <BR>
 * 		ケース入数 <BR>
 * 		ボール入数 <BR>
 * 		ホスト予定ケース数 <BR>
 * 		ホスト予定ピース数 <BR>
 * 		ケースITF <BR>
 * 		ボールITF <BR>
 * 		TC/DC区分 <BR>
 * 		仕入先コード <BR>
 * 		仕入先名称 <BR>
 * 		入荷伝票No. <BR>
 * 		入荷伝票行No. <BR>
 * 		出荷予定一意キー <BR>
 * 		最終更新日時 <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2.入力ボタン押下処理(<CODE>btn_Input_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *   <BR>
 * 	 [パラメータ] *必須入力 <BR>
 * 	 <BR>
 * 	 <DIR>
 *  	出荷予定一意キー* <BR>
 * 		出荷予定日* <BR>
 * 		荷主コード* <BR>
 *		出荷先コード* <BR>
 * 		出荷伝票No.* <BR>
 * 		出荷伝票行No.* <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		ケース入数 ※ホスト予定ケース数入力時* <BR>
 * 		ボール入数 <BR>
 * 		ホスト予定ケース数 <BR>
 * 		ホスト予定ピース数 <BR>
 * 		ケースITF <BR>
 * 		ボールITF <BR>
 * 		TC/DC区分* <BR>
 * 		仕入先コード ※TC/DC区分：クロスまたはTC指定時* <BR>
 * 		仕入先名称 <BR>
 * 		入荷伝票No. ※TC/DC区分：クロス指定時* <BR>
 * 		入荷伝票行No. ※TC/DC区分：クロス指定時* <BR>
 * 		最終更新日時* <BR>
 * 		ためうち行No.* <BR>
 * 	 </DIR>
 * </DIR>
 * <BR>
 * 3.修正登録ボタン押下処理(<CODE>btn_ModifySubmit_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが出荷予定データ修正処理を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はためうちエリア出力用のデータをスケジュールから再取得して、
 * 	 ためうちエリアの再表示を行います。<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		出荷予定一意キー* <BR>
 * 		荷主コード* <BR>
 * 		荷主名称 <BR>
 * 		出荷予定日* <BR>
 * 		出荷先コード* <BR>
 * 		出荷先名称 <BR>
 * 		出荷伝票No.* <BR>
 * 		出荷伝票行No.* <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		ケース入数 ※予定ケース数入力時* <BR>
 * 		ボール入数 <BR>
 * 		ホスト予定ケース数 <BR>
 * 		ホスト予定ピース数 <BR>
 * 		ケースITF <BR>
 * 		ボールITF <BR>
 * 		TC/DC区分* <BR>
 * 		仕入先コード ※TC/DC区分：クロスまたはTC指定時* <BR>
 * 		仕入先名称 <BR>
 * 		入荷伝票No. ※TC/DC区分：クロス指定時* <BR>
 * 		入荷伝票行No. ※TC/DC区分：クロス指定時* <BR> 
 * 		ためうち行No.* <BR>
 *   </DIR>
 *   <BR>
 *   [返却データ]<BR>
 *   <BR>
 *   <DIR>
 * 		荷主コード <BR>
 * 		荷主名称 <BR>
 * 		出荷予定日 <BR>
 * 		出荷先コード <BR>
 * 		出荷先名称 <BR>
 * 		出荷伝票No. <BR>
 * 		出荷伝票行No. <BR>
 * 		商品コード <BR>
 * 		商品名称 <BR>
 * 		ケース入数 <BR>
 * 		ボール入数 <BR>
 * 		ホスト予定ケース数 <BR>
 * 		ホスト予定ピース数 <BR>
 * 		ケースITF <BR>
 * 		ボールITF <BR>
 * 		TC/DC区分 <BR>
 * 		仕入先コード <BR>
 * 		仕入先名称 <BR>
 * 		入荷伝票No. <BR>
 * 		入荷伝票行No. <BR>
 * 		出荷予定一意キー <BR>
 * 		最終更新日時 <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 4.削除、全削除ボタン押下処理(<CODE>lst_SShpPlnDaMdDlt_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが出荷予定データ削除を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		出荷予定一意キー* <BR>
 * 		最終更新日時* <BR>
 * 		ためうち行No.* <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */ 
public class ShippingPlanModify2Business extends ShippingPlanModify2 implements WMSConstants
{
	// Class fields --------------------------------------------------
	// リストセル列指定変数
	/**
	 * 隠しパラメータ(リストセル)
	 */
	private static final int LST_HDN = 0;
	/**
	 * 削除ボタン(リストセル)
	 */
	private static final int LST_DEL_BTN = 1;
	/**
	 * 修正ボタン(リストセル)
	 */
	private static final int LST_MOD_BTN = 2;
	/**
	 * 行No.(リストセル)
	 */
	private static final int LST_LINE_NO = 3;
	/**
	 * 商品コード(リストセル)
	 */
	private static final int LST_ITEM_CODE = 4;
	/**
	 * ケース入数(リストセル)
	 */
	private static final int LST_CASEQTY = 5;
	/**
	 * 予定ケース数(リストセル)
	 */
	private static final int LST_HOSTCASEQTY = 6;
	/**
	 * ケースITF(リストセル)
	 */
	private static final int LST_CASE_ITF = 7;
	/**
	 * TC/DC(リストセル)
	 */
	private static final int LST_TCDC_FLAG = 8;
	/**
	 * 仕入先コード(リストセル)
	 */
	private static final int LST_SUPPLIER_CODE = 9;
	/**
	 * 入荷伝票No.(リストセル)
	 */
	private static final int LST_INST_TICKET_NO = 10;
	/**
	 * 商品名称(リストセル)
	 */
	private static final int LST_ITEM_NAME = 11;
	/**
	 * ボール入数(リストセル)
	 */
	private static final int LST_BUNDLEQTY = 12;
	/**
	 * 予定ケース数(リストセル)
	 */
	private static final int LST_HOSTPIECEQTY = 13;
	/**
	 * ボールITF(リストセル)
	 */
	private static final int LST_BUNDLEITF = 14;
	/**
	 * 仕入先名称(リストセル)
	 */
	private static final int LST_SUPPLIER_NAME = 15;
	/**
	 * 入荷伝票行No.(リストセル)
	 */
	private static final int LST_INST_LINE_NO = 16;

	// リストセル隠し項目順序
	/**
	 * 出荷予定一意キー(隠しパラメータ順序)
	 */
	private static final int HDN_UKEY = 0;
	/**
	 * 最終更新日時(隠しパラメータ順序)
	 */
	private static final int HDN_UPDATEDAY = 1;
	/**
	 * TC/DC区分(隠しパラメータ順序)
	 */
	private static final int HDN_UPDATETCDCFLAG = 2;
	/**
	 * 更新フラグ(隠しパラメータ順序)
	 */
	private static final int HDN_UPDATEFLAG = 3;
	/**
	 * 行No.(隠しパラメータ順序)
	 */
	private static final int HDN_LINENO = 4;
	/**
	 * 商品コード(隠しパラメータ順序)
	 */
	private static final int HDN_ITEMCODE = 5;
	/**
	 * 商品名称(隠しパラメータ順序)
	 */
	private static final int HDN_ITEMNAME = 6;
	/**
	 * ケース入数(隠しパラメータ順序)
	 */
	private static final int HDN_ENTERINGQTY = 7;
	/**
	 * ボール入数(隠しパラメータ順序)
	 */
	private static final int HDN_BUNDLEENTERINGQTY = 8;
	/**
	 * ホスト予定ケース数(隠しパラメータ順序)
	 */
	private static final int HDN_HOSTCASEQTY = 9;
	/**
	 * ホスト予定ピース数(隠しパラメータ順序)
	 */
	private static final int HDN_HOSTPIECEQTY = 10;
	/**
	 * ケースITF(隠しパラメータ順序)
	 */
	private static final int HDN_ITF = 11;
	/**
	 * ボールITF(隠しパラメータ順序)
	 */
	private static final int HDN_BUNDLEITF = 12;
	/**
	 * TC/DCフラグ(隠しパラメータ順序)
	 */
	private static final int HDN_TCDCFLAG = 13;
	/**
	 * 仕入先コード(隠しパラメータ順序)
	 */
	private static final int HDN_SUPPLIERCODE = 14;
	/**
	 * 仕入先名称(隠しパラメータ順序)
	 */
	private static final int HDN_SUPPLIERNAME = 15;
	/**
	 * 入荷伝票No.(隠しパラメータ順序)
	 */
	private static final int HDN_INST_TICKETNO = 16;
	/**
	 * 入荷伝票行No.(隠しパラメータ順序)
	 */
	private static final int HDN_INST_LINENO = 17;

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

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
		// 修正登録ボタン押下時確認ダイアログ "修正登録しますか？"
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");		

		// 全削除ボタン押下時確認ダイアログ "全てのデータを削除しますか？"
		btn_AllDelete.setBeforeConfirm("MSG-W0031");
	}

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 	 1.スケジュールを開始します。
	 *   <DIR>
	 *  	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			荷主コード* <BR>
	 * 			出荷予定日* <BR>
	 * 			出荷先コード* <BR>
	 * 			出荷伝票No.* <BR>
	 *   	</DIR>
	 *   	<BR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			荷主コード <BR>
	 * 			荷主名称 <BR>
	 * 			出荷予定日 <BR>
	 * 			出荷先コード <BR>
	 * 			出荷先名称 <BR>
	 * 			出荷伝票No. <BR>
	 * 			出荷伝票行No. <BR>
	 * 			商品コード <BR>
	 * 			商品名称 <BR>
	 * 			ケース入数 <BR>
	 * 			ボール入数 <BR>
	 * 			ホスト予定ケース数 <BR>
	 * 			ホスト予定ピース数 <BR>
	 * 			ケースITF <BR>
	 * 			ボールITF <BR>
	 * 			TC/DC区分 <BR>
	 * 			仕入先コード <BR>
	 * 			仕入先名称 <BR>
	 * 			入荷伝票No. <BR>
	 * 			入荷伝票行No. <BR>
	 * 			出荷予定一意キー <BR>
	 * 			最終更新日時 <BR>
	 *  	 </DIR>
	 * 	 </DIR>
	 *	 2.処理が正常に完了した場合はスケジュールから取得したためうちエリア出力用のデータを元に、
	 *   入力エリアとためうちエリアの表示を行います。<BR>
	 *   <DIR>
	 *   	ためうち隠し項目
	 * 		<DIR>
	 * 			TC/DCフラグ <BR>
	 * 			出荷予定一意キー <BR>
	 * 			最終更新日時 <BR>
	 *      </DIR>
	 *   	項目名[初期値]<BR>
	 *   	<DIR>
	 * 			出荷予定日[返却データ[0].出荷予定日] <BR>
	 * 			荷主コード[返却データ[0].荷主コード] <BR>
	 * 			荷主名称[返却データ[0].荷主名称] <BR>
	 * 			出荷先コード[返却データ[0].出荷先コード] <BR>
	 * 			出荷先名称[返却データ[0].出荷先名称] <BR>
	 * 			伝票No.[返却データ[0].伝票No.] <BR>
	 * 			行No.[なし] <BR>
	 * 			商品コード[なし] <BR>
	 * 			商品名称[なし] <BR>
	 * 			ケース入数[なし] <BR>
	 * 			ボール入数[なし] <BR>
	 * 			ホスト予定ケース数[なし] <BR>
	 * 			ホスト予定ピース数[なし] <BR>
	 * 			ケースITF[なし] <BR>
	 * 			ボールITF[なし] <BR>
	 * 			ＴＣ／ＤＣ区分[なし] <BR>
	 * 			仕入先コード[なし] <BR>
	 * 			仕入先名称[なし] <BR>
	 * 			入荷伝票No.[なし] <BR>
	 * 			入荷伝票行No.[なし] <BR>
	 * 	 	</DIR>
	 *   </DIR>
	 *	 該当データが見つからない場合は要素数0のParameter配列を、条件エラーなどが発生した場合はnullを受け取り、前画面に戻ります。<BR>
	 *   3.スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
	 *   4.ViewStateのためうち行No.に初期値:-1を設定します。<BR>
	 *   5.入力ボタン・クリアボタンを無効にします。<BR>
	 *   6.カーソルを行No.に初期セットします。<BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			// 画面名をセットする
			// タイトル
			lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(ShippingPlanModifyBusiness.TITLE_KEY)));		

			// 詳細情報登録のタブを前出しする
			tab_BscDtlMdfyDlt.setSelectedIndex(2);
			
			// スケジュールパラメータへセット
			ShippingParameter param = new ShippingParameter();
			
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString("ConsignorCode"));
			// 出荷予定日
			// Date型からString型(YYYYMMDD)への変換を行います。
			param.setPlanDate(WmsFormatter.toParamDate
				(this.getViewState().getString("ShippingPlanDate"),this.getHttpRequest().getLocale()));
			// 出荷先コード
			param.setCustomerCode(this.getViewState().getString("CustomerCode"));
			// 出荷伝票No.
			param.setShippingTicketNo(this.getViewState().getString("TicketNo"));

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanModifySCH();
			
			ShippingParameter[] viewParam = (ShippingParameter[]) schedule.query(conn, param);

			// エラーなどが発生した場合はメッセージを受け取り、前画面に戻る
			if (viewParam == null || ( viewParam != null && viewParam.length == 0))
			{
				// ViewStateにメッセージをセット
				this.getViewState().setString("Message", schedule.getMessage());
				// 詳細情報登録画面->基本情報設定画面
				forward("/shipping/shippingplanmodify/ShippingPlanModify.do");
				
				return;
			}
			
			// 処理が正常に完了した場合は、入力エリアとためうちエリアの表示を行う
			// 入力エリア
			// 出荷予定日
			txt_FDateShp.setDate(WmsFormatter.toDate(viewParam[0].getPlanDate()));
			// 荷主コード
			lbl_JavaSetCnsgnrCd.setText(viewParam[0].getConsignorCode());
			// 荷主名称
			lbl_JavaSetCnsgnrNm.setText(viewParam[0].getConsignorName());
			// 出荷先コード
			lbl_JavaSetCustCd.setText(viewParam[0].getCustomerCode());
			// 出荷先名称
			lbl_JavaSetCustNm.setText(viewParam[0].getCustomerName());
			// 伝票No.
			lbl_JavaSetTktNo.setText(viewParam[0].getShippingTicketNo());
			
			// TC/DC区分
			rdo_CrossDCFlagTC.setChecked(true);
			rdo_CrossDCFlagCross.setChecked(false);
			rdo_CrossDCFlagDC.setChecked(false);
			
			// ためうちエリア
			setList(viewParam);

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			// (デフォルト:-1)
			this.getViewState().setInt("LineNo", -1);
			
			// ボタン押下を無効にする
			// 入力ボタン
			btn_Input.setEnabled(false);
			// クリアボタン
			btn_Clear.setEnabled(false);
			
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
			
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
			catch(SQLException se)
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
		DialogParameters param = ((DialogEvent)e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 商品コード
		String itemcode = param.getParameter(ListShippingItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListShippingItemBusiness.ITEMNAME_KEY);
		// 仕入先コード
		String suppliercode = param.getParameter(ListShippingSupplierBusiness.SUPPLIERCODE_KEY);
		// 仕入先名称
		String suppliername = param.getParameter(ListShippingSupplierBusiness.SUPPLIERNAME_KEY);
		
		// 空ではないときに値をセットする
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
			txt_ItemName.setText(itemname);
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(suppliercode))
		{
			txt_SupplierCode.setText(suppliercode);
			txt_SupplierName.setText(suppliername);
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
		}
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
	 * 概要:出荷予定データ修正・削除(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		// 詳細情報登録画面->基本情報設定画面
		forward("/shipping/shippingplanmodify/ShippingPlanModify.do");		
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
	public void lbl_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateShp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateShp_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateShp_TabKey(ActionEvent e) throws Exception
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
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
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
	public void lbl_JavaSetCustCd_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCustNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetTktNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_LineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_LineNo_InputComplete(ActionEvent e) throws Exception
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
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		// 商品コード
		param.setParameter(ListShippingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(ListShippingItemBusiness.SEARCHITEM_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY, search);
				
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingitem/ListShippingItem.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
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
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
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
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
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
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
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
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TCDC_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchSpl_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕入先コード一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       仕入先コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchSpl_Click(ActionEvent e) throws Exception
	{
		// 仕入先コード検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		// 仕入先コード
		param.setParameter(ListShippingSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 検索フラグ
		param.setParameter(ListShippingSupplierBusiness.SEARCHSUPPLIER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingSupplierBusiness.WORKSTATUSSUPPLIER_KEY, search);
				
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingsupplier/ListShippingSupplier.do", param, "/progress.do");
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
	public void lbl_InstkTktLineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTktLineNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTktLineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTktLineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstkTktLineNo_InputComplete(ActionEvent e) throws Exception
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
	 * 概要：入力エリアの入力項目を条件に、出荷予定情報を検索し、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.カーソルを行No.にセットします。<BR>
	 *    2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *    3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 * 	 	[パラメータ] *必須入力 <BR>
	 * 	 	<DIR>
	 *  		出荷予定一意キー* <BR>
	 * 			出荷予定日* <BR>
	 * 			荷主コード* <BR>
	 * 			出荷先コード* <BR>
	 * 			出荷伝票No.* <BR>
	 * 			出荷伝票行No.* <BR>
	 * 			商品コード* <BR>
	 * 			商品名称 <BR>
	 * 			ケース入数 ※予定ケース数入力時* <BR>
	 * 			ボール入数 <BR>
	 * 			ホスト予定ケース数 <BR>
	 * 			ホスト予定ピース数 <BR>
	 * 			ケースITF <BR>
 	 * 			ボールITF <BR>
	 * 			TC/DC区分* <BR>
	 * 			仕入先コード ※TC/DC区分：クロスまたはTC指定時* <BR>
	 * 			仕入先名称 <BR>
	 * 			入荷伝票No. ※TC/DC区分：クロス指定時* <BR>
	 * 			入荷伝票行No. ※TC/DC区分：クロス指定時* <BR>
	 * 			最終更新日時* <BR>
	 * 			ためうち行No. <BR>
	 * 	 	</DIR>
	 * 	 </DIR>
	 *   <BR>
	 *    4.スケジュールの結果がtrueであれば、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *    5.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。<BR>
	 *    6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *    7.スケジュールの結果がtrueであれば、入力ボタン・クリアボタンを無効にします。<BR>
	 * 	  8.スケジュールの結果がtrueであれば、入力エリアの内容はクリアします。<BR>
	 *    9.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *   <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.行No. <BR>
	 * 		4.商品コード <BR>
	 * 		5.ケース入数 <BR>
	 * 		6.ホスト予定ケース数 <BR>
	 * 		7.ケースITF <BR>
	 * 		8.ＴＣ／ＤＣ <BR>
	 * 		9.仕入先コード <BR>
	 * 		10.入荷伝票No. <BR>
	 * 		11.商品名称 <BR>
	 * 		12.ボール入数 <BR>
	 * 		13.ホスト予定ピース数 <BR>
	 * 		14.ボールITF <BR>
	 * 		15.仕入先名称 <BR>
	 * 		16.入荷伝票行No. <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを行No.にセットする
		setFocus(txt_LineNo);
			
		// 入力チェック
		txt_LineNo.validate();
		txt_ItemCode.validate();
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_PlanCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_PlanPieceQty.validate(false);
		txt_BundleItf.validate(false);
		// 2006/07/25 modify start T.Kishimoto 項目チェックを修正
		if (rdo_CrossDCFlagDC.getChecked())
		{
			txt_SupplierCode.validate(false);
		}
		else
		{
			// DC以外は必須チェック
			txt_SupplierCode.validate();
		}
		
		txt_SupplierName.validate(false);
		
		if (rdo_CrossDCFlagCross.getChecked())
		{
			// クロスの場合は必須チェック
			txt_InstockTicketNo.validate();
			txt_InstkTktLineNo.validate();
		}
		else
		{
			txt_InstockTicketNo.validate(false);
			txt_InstkTktLineNo.validate(false);
		}
		// 2006/07/25 modify end T.Kishimoto

		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		
		Connection conn = null;

		try
		{
			// スケジュールパラメータへセット
			// 入力エリア
			ShippingParameter param = new ShippingParameter();
			
			// ユーザーが入力できない値(ラベル項目、隠しパラメータ)
			// 出荷予定一意キー
			param.setShippingPlanUkey(this.getViewState().getString("ShippingPlanUkey"));
			// 最終更新日時
			param.setLastUpdateDate(
				WmsFormatter.getTimeStampDate(this.getViewState().getString("LastUpdateDate")));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主名称
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 出荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FDateShp.getDate()));
			// 出荷先コード
			param.setCustomerCode(lbl_JavaSetCustCd.getText());
			// 出荷先名称
			param.setCustomerName(lbl_JavaSetCustNm.getText());
			// 伝票No.
			param.setShippingTicketNo(lbl_JavaSetTktNo.getText());

			// ユーザーが入力できる項目(テキストボックス)
			// 行No.
			param.setShippingLineNo(WmsFormatter.getInt(txt_LineNo.getText()));
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケース入数
			param.setEnteringQty(WmsFormatter.getInt(txt_CaseEntering.getText()));
			// ボール入数
			param.setBundleEnteringQty(WmsFormatter.getInt(txt_BundleEntering.getText()));
			// ホスト予定ケース数
			param.setPlanCaseQty(WmsFormatter.getInt(txt_PlanCaseQty.getText()));
			// ホスト予定ピース数
			param.setPlanPieceQty(WmsFormatter.getInt(txt_PlanPieceQty.getText()));
			// ケースITF
			param.setITF(txt_CaseItf.getText());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());
			// TC/DC区分
			if (rdo_CrossDCFlagTC.getChecked())
			{
				// TC
				param.setTcdcFlag(ShippingParameter.TCDC_FLAG_TC);
			}
			else if (rdo_CrossDCFlagCross.getChecked())
			{
				// クロス
				param.setTcdcFlag(ShippingParameter.TCDC_FLAG_CROSSTC);
			}
			else if (rdo_CrossDCFlagDC.getChecked())
			{
				// DC
				param.setTcdcFlag(ShippingParameter.TCDC_FLAG_DC);
			}
			// 仕入先コード
			param.setSupplierCode(txt_SupplierCode.getText());
			// 仕入先名称
			param.setSupplierName(txt_SupplierName.getText());
			// 入荷伝票No.
			param.setInstockTicketNo(txt_InstockTicketNo.getText());
			// 入荷伝票行No.
			param.setInstockLineNo(WmsFormatter.getInt(txt_InstkTktLineNo.getText()));				
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// スケジュールパラメータへセット
			// ためうちエリア
			ShippingParameter[] listparam = null;
			
			// ためうちにデータがなければnullをセット
			if(lst_SShpPlnDaMdDlt.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt("LineNo"),true);
			}

			WmsScheduler schedule = new ShippingPlanModifySCH();

			if(schedule.check(conn, param, listparam))
			{
				// 修正対象行のデータを更新
				lst_SShpPlnDaMdDlt.setCurrentRow(this.getViewState().getInt("LineNo"));
				// ためうちに入力エリアの値をセット
				setListRow();
				
				// 入力エリアの項目を全クリア
				txt_LineNo.setText("");
				txt_ItemCode.setText("");
				txt_ItemName.setText("");
				txt_CaseEntering.setText("");
				txt_PlanCaseQty.setText("");
				txt_CaseItf.setText("");
				txt_BundleEntering.setText("");
				txt_PlanPieceQty.setText("");
				txt_BundleItf.setText("");
				rdo_CrossDCFlagTC.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagDC.setChecked(false);
				txt_SupplierCode.setText("");
				txt_SupplierName.setText("");
				txt_InstockTicketNo.setText("");
				txt_InstkTktLineNo.setText("");

				// ボタン押下を無効にする
				// 入力ボタン
				btn_Input.setEnabled(false);
				// クリアボタン
				btn_Clear.setEnabled(false);

				// 状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);
				// 選択行の色を元に戻す
				lst_SShpPlnDaMdDlt.resetHighlight();
				// カーソルを行No.へセットします
				setFocus(txt_LineNo);						
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
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを行No.にセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 初期値をセット
		txt_LineNo.setText("");
		txt_ItemCode.setText("");
		txt_ItemName.setText("");
		txt_CaseEntering.setText("");
		txt_PlanCaseQty.setText("");
		txt_CaseItf.setText("");
		txt_BundleEntering.setText("");
		txt_PlanPieceQty.setText("");
		txt_BundleItf.setText("");
		rdo_CrossDCFlagTC.setChecked(true);	
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		txt_SupplierCode.setText("");
		txt_SupplierName.setText("");
		txt_InstockTicketNo.setText("");
		txt_InstkTktLineNo.setText("");
		
		// カーソルを行No.にセットする
		setFocus(txt_LineNo);
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
	 * 修正登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、出荷予定データ修正登録を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを行No.にセットします。<BR>
	 *    2.修正登録を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "修正登録しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ]<BR>
	 * 					<DIR>
	 * 						出荷予定一意キー <BR>
	 * 						出荷予定日 <BR>
	 * 						荷主コード <BR>
	 * 						荷主名称 <BR>
	 * 						出荷先コード <BR>
	 * 						出荷先名称 <BR>
	 * 						出荷伝票No. <BR>
	 *						ためうち.出荷行No. <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.ホスト予定ケース数 <BR>
	 *						ためうち.ホスト予定ピース数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールITF <BR>
	 *						ためうち.ＴＣ／ＤＣ区分 <BR>
	 *						ためうち.仕入先コード <BR>
	 *						ためうち.仕入先名称 <BR>
	 *						ためうち.入荷伝票No. <BR>
	 *						ためうち.入荷伝票行No. <BR>
	 * 						ためうち行No. <BR>
	 * 						出荷予定一意キー <BR>
	 * 						最終更新日時 <BR>
	 *	 				</DIR>
	 *					<BR>
	 *   				[返却データ]<BR>
	 *   				<DIR>
	 * 						荷主コード <BR>
	 * 						荷主名称 <BR>
	 * 						出荷予定日 <BR>
	 * 						出荷先コード <BR>
	 * 						出荷先名称 <BR>
	 * 						出荷伝票No. <BR>
	 * 						出荷伝票行No. <BR>
	 * 						商品コード <BR>
	 * 						商品名称 <BR>
	 * 						ケース入数 <BR>
	 * 						ボール入数 <BR>
	 * 						ホスト予定ケース数 <BR>
	 * 						ホスト予定ピース数 <BR>
	 * 						ケースITF <BR>
	 * 						ボールITF <BR>
	 * 						TC/DC区分 <BR>
	 * 						仕入先コード <BR>
	 * 						仕入先名称 <BR>
	 * 						入荷伝票No. <BR>
	 * 						入荷伝票行No. <BR>
	 * 						出荷予定一意キー <BR>
	 * 						最終更新日時 <BR>
	 *   				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時は、返却データを元にためうちのデータを再表示します。<BR>
	 *				3.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。<BR>
	 * 				4.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.スケジュールの結果がtrueの時は、入力・クリアボタンを無効にします。<BR>
	 *    			6.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。<BR>
	 *    			7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.行No. <BR>
	 * 		4.商品コード <BR>
	 * 		5.ケース入数 <BR>
	 * 		6.ホスト予定ケース数 <BR>
	 * 		7.ケースITF <BR>
	 * 		8.ＴＣ／ＤＣ <BR>
	 * 		9.仕入先コード <BR>
	 * 		10.入荷伝票No. <BR>
	 * 		11.商品名称 <BR>
	 * 		12.ボール入数 <BR>
	 * 		13.ホスト予定ピース数 <BR>
	 * 		14.ボールITF <BR>
	 * 		15.仕入先名称 <BR>
	 * 		16.入荷伝票行No. <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
			
			// スケジュールパラメータへセット
			ShippingParameter[] param = null;
			// ためうちエリアの全データをセット
			param = setListParam(-1,true);

			if (param == null || param.length <= 0)
			{
				// メッセージをセットします
				// 6003013=修正対象データがありませんでした。
				message.setMsgResourceKey("6003013");
				return;
			}
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanModifySCH();

			ShippingParameter[] viewParam = (ShippingParameter[]) schedule.startSCHgetParams(conn, param);

			// エラーなどが発生した場合はメッセージを受け取り、終了
			if (viewParam == null || ( viewParam != null && viewParam.length == 0))
			{
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			// 正常に完了した場合はコミット
			conn.commit();
			
			// 処理が正常に完了した場合は、返却データを元にためうちエリアの再表示を行う
			// 前回のためうちエリアを削除
			lst_SShpPlnDaMdDlt.clearRow();

			// ためうちエリア
			setList(viewParam);
			
			// 入力エリアクリア
			btn_Clear_Click(e);

			// 状態をデフォルトに戻す
			this.getViewState().setInt("LineNo", -1);
						
			// ボタン押下を無効にする
			// 入力ボタン
			btn_Input.setEnabled(false);
			// クリアボタン
			btn_Clear.setEnabled(false);

			// 選択行の色を元に戻す
			lst_SShpPlnDaMdDlt.resetHighlight();

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
	public void btn_AllDelete_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 全削除ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全て削除します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報の全削除を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 * 	    "全てのデータを削除しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ] *必須入力<BR>
	 *   				<DIR>
	 * 						出荷予定一意キー* <BR>
	 * 						最終更新日時* <BR>
	 * 						ためうち行No.* <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。<BR>
	 *				3.スケジュールの結果がtrueの時は、ためうちの表示情報を全て削除します。<BR>
	 *    			4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.スケジュールの結果がtrueの時は、修正登録・全削除・入力・クリアボタンを無効にします。<BR>
	 *    			6.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *				7.カーソルを行No.にセットします。
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_AllDelete_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// スケジュールパラメータへセット
			ShippingParameter[] listparam = null;
			
			Vector vecParam = new Vector();
		
			for (int i = 1; i < lst_SShpPlnDaMdDlt.getMaxRows(); i++)
			{

				// 行指定
				lst_SShpPlnDaMdDlt.setCurrentRow(i);

				// スケジュールパラメータへセット
				ShippingParameter param = new ShippingParameter();
				// ためうち行No.
				param.setRowNo(i);
				// 出荷予定一意キー
				param.setShippingPlanUkey(CollectionUtils.getString(0,lst_SShpPlnDaMdDlt.getValue(0)));
				// 最終更新日時
				param.setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(1,lst_SShpPlnDaMdDlt.getValue(0))));

				vecParam.addElement(param);
			}
			
			if(vecParam.size() > 0)
			{
				// セットするためうちデータがあれば値をセット
				listparam = new ShippingParameter[vecParam.size()];
				vecParam.copyInto(listparam);
			}
			else
			{
				// セットするためうちデータがなければnullをセット
				listparam = null;
			}
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanModifySCH();
			
			if(schedule.startSCH(conn, listparam))
			{
				// 処理が正常に完了すればコミット
				conn.commit();
				
				// 結果がtrueの時は、入力エリアとためうちの項目を全クリア
				// 入力エリアクリア
				btn_Clear_Click(e);

				// ためうち行をすべて削除
				lst_SShpPlnDaMdDlt.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を無効にする
				// 入力ボタン
				btn_Input.setEnabled(false);
				// クリアボタン
				btn_Clear.setEnabled(false);
				// 修正登録ボタン
				btn_ModifySubmit.setEnabled(false);
				// 全削除ボタン
				btn_AllDelete.setEnabled(false);

				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				// 異常終了時はロールバック
				conn.rollback();
				
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			
			}

			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
			
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
	public void lst_SShpPlnDaMdDlt_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlnDaMdDlt_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlnDaMdDlt_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlnDaMdDlt_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlnDaMdDlt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlnDaMdDlt_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 削除、修正ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 削除ボタン概要:ためうちの該当データを削除します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報の削除を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "削除しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ] *必須入力<BR>
	 *   				<DIR>
	 * 						ためうち行No. <BR>
	 * 						出荷予定一意キー* <BR>
	 * 						最終更新日時* <BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。<BR>
	 *				3.スケジュールの結果がtrueの時は、ためうちの該当データを削除します。<BR>
	 *    			4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.スケジュールの結果がtrueの時は、入力・クリアボタンを無効にします。<BR>
	 *				6.ためうち情報が存在しない場合、修正登録・全削除ボタンを無効にします。<BR>
	 *    			7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *				8.カーソルを行No.にセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.選択情報を上部の入力領域に表示します。<BR>
	 *    2.選択情報部を薄黄色にします。<BR>
	 *    3.ViewStateのためうち行No.に現在行を設定します。<BR>
	 *    4.カーソルを行No.にセットします。<BR>
	 * 	  5.入力ボタン・クリアボタンを有効にします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SShpPlnDaMdDlt_Click(ActionEvent e) throws Exception
	{
		// 削除ボタンクリック時
		if (lst_SShpPlnDaMdDlt.getActiveCol() == 1)
		{
			Connection conn = null;
			
			try
			{
				// 現在の行をセット
				lst_SShpPlnDaMdDlt.setCurrentRow(lst_SShpPlnDaMdDlt.getActiveRow());

				// スケジュールパラメータへセット
				ShippingParameter[] param = new ShippingParameter[1];
				param[0] = new ShippingParameter();
				
				// ためうち行No.
				param[0].setRowNo(lst_SShpPlnDaMdDlt.getActiveRow());
				// 出荷予定一意キー
				param[0].setShippingPlanUkey(CollectionUtils.getString(0,lst_SShpPlnDaMdDlt.getValue(0)));
				// 最終更新日時
				param[0].setLastUpdateDate(
					WmsFormatter.getTimeStampDate(CollectionUtils.getString(1,lst_SShpPlnDaMdDlt.getValue(0))));
				
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				WmsScheduler schedule = new ShippingPlanModifySCH();

				if(schedule.startSCH(conn, param))
				{
					// 処理が正常に完了すればコミット
					conn.commit();
				
					// 入力エリアクリア
					btn_Clear_Click(e);

					// 削除されたためうち行を削除
					lst_SShpPlnDaMdDlt.removeRow(lst_SShpPlnDaMdDlt.getActiveRow());
					
					// 修正状態をデフォルトに戻す
					this.getViewState().setInt("LineNo", -1);

					// ボタン押下を無効にする
					// 入力ボタン
					btn_Input.setEnabled(false);
					// クリアボタン
					btn_Clear.setEnabled(false);
					
					// 選択行の色を元に戻す
					lst_SShpPlnDaMdDlt.resetHighlight();

					// ためうち情報が存在しない場合、修正登録・全削除・入力・クリアボタンは無効にする
					if(lst_SShpPlnDaMdDlt.getMaxRows() == 1)
					{
						// ボタン押下を無効にする
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
					// 異常終了時はロールバック
					conn.rollback();
					// メッセージをセット
					message.setMsgResourceKey(schedule.getMessage());
				}
			
				// カーソルを行No.にセットする
				setFocus(txt_LineNo);
			
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
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_SShpPlnDaMdDlt.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_SShpPlnDaMdDlt.setCurrentRow(lst_SShpPlnDaMdDlt.getActiveRow());
			// 行No.
			txt_LineNo.setText(lst_SShpPlnDaMdDlt.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_SShpPlnDaMdDlt.getValue(4));
			// ケース入数
			txt_CaseEntering.setText(lst_SShpPlnDaMdDlt.getValue(5));
			// ホスト予定ケース数
			txt_PlanCaseQty.setText(lst_SShpPlnDaMdDlt.getValue(6));
			// ケースITF
			txt_CaseItf.setText(lst_SShpPlnDaMdDlt.getValue(7));
			// TC/DC(隠し項目)
			if (CollectionUtils.getString(HDN_UPDATETCDCFLAG,lst_SShpPlnDaMdDlt.getValue(0)).equals(ShippingParameter.TCDC_FLAG_DC))
			{
				rdo_CrossDCFlagDC.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(false);
			}
			else if (CollectionUtils.getString(HDN_UPDATETCDCFLAG,lst_SShpPlnDaMdDlt.getValue(0)).equals(ShippingParameter.TCDC_FLAG_CROSSTC))
			{
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(true);
				rdo_CrossDCFlagTC.setChecked(false);
			}
			else if (CollectionUtils.getString(HDN_UPDATETCDCFLAG,lst_SShpPlnDaMdDlt.getValue(0)).equals(ShippingParameter.TCDC_FLAG_TC))
			{
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(true);
			}
			// 仕入先コード
			txt_SupplierCode.setText(lst_SShpPlnDaMdDlt.getValue(9));
			// 入荷伝票No.
			txt_InstockTicketNo.setText(lst_SShpPlnDaMdDlt.getValue(10));
			// 商品名称
			txt_ItemName.setText(lst_SShpPlnDaMdDlt.getValue(11));
			// ボール入数
			txt_BundleEntering.setText(lst_SShpPlnDaMdDlt.getValue(12));
			// ホスト予定ピース数
			txt_PlanPieceQty.setText(lst_SShpPlnDaMdDlt.getValue(13));
			// ボールITF
			txt_BundleItf.setText(lst_SShpPlnDaMdDlt.getValue(14));
			// 仕入先名称
			txt_SupplierName.setText(lst_SShpPlnDaMdDlt.getValue(15));
			// 入荷伝票行No.
			txt_InstkTktLineNo.setText(lst_SShpPlnDaMdDlt.getValue(16));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt("LineNo", lst_SShpPlnDaMdDlt.getActiveRow());
			// 出荷予定一意キー(隠し項目)
			this.getViewState().setString("ShippingPlanUkey", 
				CollectionUtils.getString(HDN_UKEY,lst_SShpPlnDaMdDlt.getValue(0)));
			// 最終更新日時(隠し項目)
			this.getViewState().setString("LastUpdateDate", 
				CollectionUtils.getString(HDN_UPDATEDAY,lst_SShpPlnDaMdDlt.getValue(0)));
			
			//修正行を黄色に変更する
			lst_SShpPlnDaMdDlt.setHighlight(lst_SShpPlnDaMdDlt.getActiveRow());

			// 入力、クリアボタンを有効にする
			// 入力ボタン
			btn_Input.setEnabled(true);
			// クリアボタン
			btn_Clear.setEnabled(true);
			
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
		}
	}

	// 共通メソッド------------------------------------------
	
	/**
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。<BR>
	 * 3.修正登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			修正対象ためうち行No.* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private ShippingParameter[] setListParam(int lineno,boolean check) throws Exception
	{

		Vector vecParam = new Vector();
		
		// 端末No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String termNo = userHandler.getTerminalNo();

		for (int i = 1; i < lst_SShpPlnDaMdDlt.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if(i == lineno)
			{
				continue;
			}
			
			// 行指定
			lst_SShpPlnDaMdDlt.setCurrentRow(i);

			// 変更されていないデータはセットしない
			if (check && lineno < 0 
			 && CollectionUtils.getString(HDN_UPDATEFLAG, lst_SShpPlnDaMdDlt.getValue(LST_HDN)).equals(ShippingParameter.UPDATEFLAG_NO))
			{
				continue;
			}

			// スケジュールパラメータへセット
			ShippingParameter param = new ShippingParameter();
			// 入力エリア情報	
			// 出荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FDateShp.getDate()));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());	
			// 荷主名称
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 出荷先コード
			param.setCustomerCode(lbl_JavaSetCustCd.getText());
			// 出荷先名称
			param.setCustomerName(lbl_JavaSetCustNm.getText());
			// 伝票No.
			param.setShippingTicketNo(lbl_JavaSetTktNo.getText());
			
			// ためうちエリア情報
			// 行No.	
			param.setShippingLineNo(WmsFormatter.getInt(lst_SShpPlnDaMdDlt.getValue(3)));
			// 商品コード
			param.setItemCode(lst_SShpPlnDaMdDlt.getValue(4));
			// ケース入数
			param.setEnteringQty(WmsFormatter.getInt(lst_SShpPlnDaMdDlt.getValue(5)));
			// ホスト予定ケース数
			param.setPlanCaseQty(WmsFormatter.getInt(lst_SShpPlnDaMdDlt.getValue(6)));
			// ケースITF
			param.setITF(lst_SShpPlnDaMdDlt.getValue(7));
			// 仕入先コード
			param.setSupplierCode(lst_SShpPlnDaMdDlt.getValue(9));
			// 入荷伝票No.
			param.setInstockTicketNo(lst_SShpPlnDaMdDlt.getValue(10));
			// 商品名称
			param.setItemName(lst_SShpPlnDaMdDlt.getValue(11));
			// ボール入数
			param.setBundleEnteringQty(WmsFormatter.getInt(lst_SShpPlnDaMdDlt.getValue(12)));
			// ホスト予定ピース数
			param.setPlanPieceQty(WmsFormatter.getInt(lst_SShpPlnDaMdDlt.getValue(13)));
			// ボールITF
			param.setBundleITF(lst_SShpPlnDaMdDlt.getValue(14));
			// 仕入先名称
			param.setSupplierName(lst_SShpPlnDaMdDlt.getValue(15));
			// 入荷伝票行No.
			param.setInstockLineNo(WmsFormatter.getInt(lst_SShpPlnDaMdDlt.getValue(16)));

			// 出荷予定一意キー(隠し項目)
			param.setShippingPlanUkey(CollectionUtils.getString(0,lst_SShpPlnDaMdDlt.getValue(0)));
			// 最終更新日時(隠し項目)
			param.setLastUpdateDate(
				WmsFormatter.getTimeStampDate(CollectionUtils.getString(1,lst_SShpPlnDaMdDlt.getValue(0))));
			// TC/DC区分(隠し項目)
			param.setTcdcFlag(CollectionUtils.getString(HDN_UPDATETCDCFLAG,lst_SShpPlnDaMdDlt.getValue(0)));
			// 行No.を保持しておく
			param.setRowNo(i);

			// 作業者コード
			param.setWorkerCode(this.getViewState().getString("WorkerCode"));

			// 端末No
			param.setTerminalNumber(termNo);
			
			// 1行分をひとまとめにして追加
			vecParam.addElement(param);
		}

		if(vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			ShippingParameter[] listparam = new ShippingParameter[vecParam.size()];
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
	 * ためうちエリアにParameterの値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアにParameterの値をセットします。<BR>
	 * <DIR>
	 * 		隠し項目
	 * 		<DIR>
	 * 			0.TC/DCフラグ <BR>
	 * 			1.出荷予定一意キー <BR>
	 * 			2.最終更新日時 <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setList(ShippingParameter[] param) throws Exception
	{
		// バルーンヘルプ用名称取得
		// 商品名称
		String itemName = DisplayText.getText("LBL-W0103");
		// 仕入先名称
		String supplierName = DisplayText.getText("LBL-W0253");
		// 入荷伝票No.
		String instockTicketNo = DisplayText.getText("LBL-W0091");
		// 入荷伝票行No.
		String instockLineNo = DisplayText.getText("LBL-W0090");
		
		for(int i = 0; i < param.length; i++)
		{
			// 行追加
			lst_SShpPlnDaMdDlt.addRow();
			// 現在の行をセット
			lst_SShpPlnDaMdDlt.setCurrentRow(i + 1);
			
			//ToolTip用のデータを編集
			ToolTipHelper toolTip = new ToolTipHelper();
			// 商品名称
			toolTip.add(itemName, param[i].getItemName());
			// 仕入先名称
			toolTip.add(supplierName, param[i].getSupplierName());
			// 入荷伝票No.
			toolTip.add(instockTicketNo, param[i].getInstockTicketNo());
			// 入荷伝票行No.
			toolTip.add(instockLineNo, param[i].getInstockLineNo());
			//カレント行にTOOL TIPをセットする
			lst_SShpPlnDaMdDlt.setToolTip(i + 1, toolTip.getText());
			
			// 隠し項目セット準備
			Vector list = new Vector();
			// 出荷予定一意キー
			list.add(param[i].getShippingPlanUkey());
			// 最終更新日時
			list.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			// TC/DCフラグ
			list.add(param[i].getTcdcFlag());
			// 更新フラグ：更新なし
			list.add(ShippingParameter.UPDATEFLAG_NO);
			// 行No.
			list.add(WmsFormatter.getNumFormat(param[i].getShippingLineNo()));
			// 商品コード
			list.add(param[i].getItemCode());
			// 商品名称
			list.add(param[i].getItemName());
			// ケース入数
			list.add(WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			// ボール入数
			list.add(WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			// ホスト予定ケース数
			list.add(WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			// ホスト予定ピース数
			list.add(WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			// ケースITF
			list.add(param[i].getITF());
			// ボールITF
			list.add(param[i].getBundleITF());
			// TC/DCフラグ
			list.add(param[i].getTcdcFlag());
			// 仕入先コード
			list.add(param[i].getSupplierCode());
			// 仕入先名称
			list.add(param[i].getSupplierName());
			// 入荷伝票No.
			list.add(param[i].getInstockTicketNo());
			// 入荷伝票行No.
			list.add(WmsFormatter.getNumFormat(param[i].getInstockLineNo()));
			
			// 隠し項目にTC/DCフラグ、出荷予定一意キー、最終更新日時をセットする
			lst_SShpPlnDaMdDlt.setValue(0, CollectionUtils.getConnectedString(list));
			// 行No.
			lst_SShpPlnDaMdDlt.setValue(3, WmsFormatter.getNumFormat(param[i].getShippingLineNo()));
			// 商品コード
			lst_SShpPlnDaMdDlt.setValue(4, param[i].getItemCode());
			// ケース入数
			lst_SShpPlnDaMdDlt.setValue(5, WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			// 予定ケース数
			lst_SShpPlnDaMdDlt.setValue(6, WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			// ケースITF
			lst_SShpPlnDaMdDlt.setValue(7, param[i].getITF());
			// TC/DC区分
			lst_SShpPlnDaMdDlt.setValue(8, param[i].getTcdcFlagName());
			// 仕入先コード
			lst_SShpPlnDaMdDlt.setValue(9, param[i].getSupplierCode());
			// 入荷伝票No.
			lst_SShpPlnDaMdDlt.setValue(10, param[i].getInstockTicketNo());
			// 商品名称
			lst_SShpPlnDaMdDlt.setValue(11, param[i].getItemName());
			// ボール入数
			lst_SShpPlnDaMdDlt.setValue(12, WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			// 予定ピース数
			lst_SShpPlnDaMdDlt.setValue(13, WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			// ボールITF
			lst_SShpPlnDaMdDlt.setValue(14, param[i].getBundleITF());
			// 仕入先名称
			lst_SShpPlnDaMdDlt.setValue(15, param[i].getSupplierName());
			// 入荷伝票行No.
			lst_SShpPlnDaMdDlt.setValue(16, WmsFormatter.getNumFormat(param[i].getInstockLineNo()));
		}
	}
		
	/**
	 * ためうちエリアに入力エリアの修正データセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに入力エリアの修正データをセットします。<BR>
	 * <DIR>
	 * 		隠し項目
	 * 		<DIR>
	 * 			0.TC/DCフラグ <BR>
	 * 			1.出荷予定一意キー <BR>
	 * 			2.最終更新日時 <BR>
	 *	 	</DIR>
	 * </DIR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setListRow() throws Exception
	{
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		// 仕入先名称
		toolTip.add(DisplayText.getText("LBL-W0253"), txt_SupplierName.getText());
		// 入荷伝票No.
		toolTip.add(DisplayText.getText("LBL-W0091"), txt_InstockTicketNo.getText());
		// 入荷伝票行No.
		toolTip.add(DisplayText.getText("LBL-W0090"), txt_InstkTktLineNo.getInt());
		//カレント行にTOOL TIPをセットする
		lst_SShpPlnDaMdDlt.setToolTip(this.getViewState().getInt("LineNo"), toolTip.getText());
		
		// 隠しパラメータ
		ArrayList hidden = (ArrayList) CollectionUtils.getList(lst_SShpPlnDaMdDlt.getValue(LST_HDN));

		// 隠しパラメータ.更新フラグ
		if (updateCheck())
		{
			hidden.set(HDN_UPDATEFLAG, ShippingParameter.UPDATEFLAG_YES);
		}
		else
		{
			hidden.set(HDN_UPDATEFLAG, ShippingParameter.UPDATEFLAG_NO);
		}
		// 現在のTC/DCフラグ
		if (rdo_CrossDCFlagTC.getChecked())
		{
			// TC
			hidden.set(HDN_UPDATETCDCFLAG, ShippingParameter.TCDC_FLAG_TC);
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			// CROSS
			hidden.set(HDN_UPDATETCDCFLAG, ShippingParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			// DC
			hidden.set(HDN_UPDATETCDCFLAG, ShippingParameter.TCDC_FLAG_DC);
		}
		// 隠しパラメータをリストセルに格納
		lst_SShpPlnDaMdDlt.setValue(LST_HDN, CollectionUtils.getConnectedString(hidden));
		// 行No.
		lst_SShpPlnDaMdDlt.setValue(3, WmsFormatter.getNumFormat(txt_LineNo.getInt()));
		// 商品コード
		lst_SShpPlnDaMdDlt.setValue(4,txt_ItemCode.getText());
		// ケース入数
		lst_SShpPlnDaMdDlt.setValue(5,WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		// ホスト予定ケース数
		lst_SShpPlnDaMdDlt.setValue(6,WmsFormatter.getNumFormat(txt_PlanCaseQty.getInt()));
		// ケースITF
		lst_SShpPlnDaMdDlt.setValue(7,txt_CaseItf.getText());
		// TC/DC区分
		if (rdo_CrossDCFlagDC.getChecked())
		{
			lst_SShpPlnDaMdDlt.setValue(8,DisplayUtil.getTcDcValue(ShippingParameter.TCDC_FLAG_DC));
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			lst_SShpPlnDaMdDlt.setValue(8,DisplayUtil.getTcDcValue(ShippingParameter.TCDC_FLAG_CROSSTC));
		}
		else if (rdo_CrossDCFlagTC.getChecked())
		{
			lst_SShpPlnDaMdDlt.setValue(8,DisplayUtil.getTcDcValue(ShippingParameter.TCDC_FLAG_TC));
		}

		// 仕入先コード
		lst_SShpPlnDaMdDlt.setValue(9,txt_SupplierCode.getText());
		// 入荷伝票No.
		lst_SShpPlnDaMdDlt.setValue(10,txt_InstockTicketNo.getText());
		// 商品名称
		lst_SShpPlnDaMdDlt.setValue(11,txt_ItemName.getText());
		// ボール入数
		lst_SShpPlnDaMdDlt.setValue(12,WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		// ホスト予定ピース数
		lst_SShpPlnDaMdDlt.setValue(13,WmsFormatter.getNumFormat(txt_PlanPieceQty.getInt()));
		// ボールITF
		lst_SShpPlnDaMdDlt.setValue(14,txt_BundleItf.getText());
		// 仕入先名称
		lst_SShpPlnDaMdDlt.setValue(15,txt_SupplierName.getText());
		// 入荷伝票行No.
		lst_SShpPlnDaMdDlt.setValue(16,WmsFormatter.getNumFormat(txt_InstkTktLineNo.getInt()));
	}
	
	/**
	 * 入力エリアとためうちエリアで変更箇所があるか判断するメソッドです。<BR>
	 * <BR>
	 * 概要:入力エリアとためうちエリアを比較し、変更があればtrueを返します。<BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean updateCheck() throws Exception
	{
		String hiddenParam = lst_SShpPlnDaMdDlt.getValue(LST_HDN);
		
		// 行No.
		if (txt_LineNo.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_LINENO, hiddenParam)))
		{
			return true;
		}
		// 商品コード
		if (!txt_ItemCode.getText().equals(CollectionUtils.getString(HDN_ITEMCODE, hiddenParam)))
		{
			return true;
		}
		// 商品名称
		if (!txt_ItemName.getText().equals(CollectionUtils.getString(HDN_ITEMNAME, hiddenParam)))
		{
			return true;
		}
		// ケース入数
		if (txt_CaseEntering.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_ENTERINGQTY, hiddenParam)))
		{
			return true;
		}
		// ボール入数
		if (txt_BundleEntering.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_BUNDLEENTERINGQTY, hiddenParam)))
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
		// ケースITF
		if (!txt_CaseItf.getText().equals(CollectionUtils.getString(HDN_ITF, hiddenParam)))
		{
			return true;
		}
		// ボールITF
		if (!txt_BundleItf.getText().equals(CollectionUtils.getString(HDN_BUNDLEITF, hiddenParam)))
		{
			return true;
		}
		// TC/DCフラグ
		if (rdo_CrossDCFlagTC.getChecked())
		{
			if (!ShippingParameter.TCDC_FLAG_TC.equals(CollectionUtils.getString(HDN_TCDCFLAG, hiddenParam)))
			{
				return true;
			}
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			if (!ShippingParameter.TCDC_FLAG_CROSSTC.equals(CollectionUtils.getString(HDN_TCDCFLAG, hiddenParam)))
			{
				return true;
			}
		}
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			if (!ShippingParameter.TCDC_FLAG_DC.equals(CollectionUtils.getString(HDN_TCDCFLAG, hiddenParam)))
			{
				return true;
			}
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
		if (txt_InstkTktLineNo.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDN_INST_LINENO, hiddenParam)))
		{
			return true;
		}

		return false;
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
	public void rdo_CrossDCFlagTC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagTC_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_BscDtlMdfyDlt_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class

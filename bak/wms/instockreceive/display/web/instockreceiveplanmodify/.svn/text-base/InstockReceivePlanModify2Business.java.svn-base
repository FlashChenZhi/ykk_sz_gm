// $Id: InstockReceivePlanModify2Business.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveplanmodify;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
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
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceivePlanModifySCH;

/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * 入荷予定修正・削除(詳細情報修正削除)クラスです。 <BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示し、 ためうちエリア出力用のデータをためうちエリアに表示します。 <BR>
 * 入荷予定修正・削除を行うスケジュールにパラメータを引き渡します。 <BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。 <BR>
 * <BR>
 * リストセルの列番号一覧 <BR>
 * <DIR>1.削除ボタン <BR>
 * 2.修正ボタン <BR>
 * 3.行No. <BR>
 * 4.商品コード <BR>
 * 5.ケース入数 <BR>
 * 6.ホスト予定ケース数 <BR>
 * 7.ケースITF <BR>
 * 8.ボール入数 <BR>
 * 9.ホスト予定ピース数 <BR>
 * 10.ボールITF <BR>
 * </DIR> <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理( <CODE>page_Load</CODE> メソッド) <BR>
 * <BR>
 * <DIR>基本情報入力画面の入力情報の一部を、上部表示領域に表示します。 <BR>
 * ViewStateから取得した内容をパラメータにセットし、ためうちエリア出力用のデータをスケジュールから取得します。 <BR>
 * 処理が正常に完了した場合ははスケジュールから取得したためうちエリア出力用のデータを元に、ためうちエリアの表示を行います。 <BR>
 * 該当データが見つからない場合は要素数0のParameter配列を、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 +条件によっては必須入力<BR>
 * <BR>
 * <DIR>作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * 入荷予定日* <BR>
 * 仕入先コード* <BR>
 * TC/DC区分* <BR>
 * 出荷先コード+ <BR>
 * 伝票No.*<BR>
 * </DIR> <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>荷主コード <BR>
 * 荷主名称 <BR>
 * 商品コード <BR>
 * 商品名称 <BR>
 * ケース入数 <BR>
 * ボール入数 <BR>
 * ホスト予定ケース数 <BR>
 * ホスト予定ピース数 <BR>
 * ケースITF <BR>
 * ボールITF <BR>
 * 入荷予定一意ｷｰ <BR>
 * 最終更新日時 <BR>
 * ケース/ピース区分 <BR>
 * </DIR> <BR>
 * </DIR> <BR>
 * 2.入力ボタン押下処理( <CODE>btn_Input_Click</CODE> メソッド) <BR>
 * <BR>
 * <DIR>入力エリアから入力した内容をパラメータにセットし、 そのパラメータを基にスケジュールが入力条件のチェックを行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、 <BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * 結果がtrueの時は、入力エリアの情報でためうちの修正対象データを更新します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 +条件により必須入力<BR>
 * <BR>
 * <DIR>荷主コード* <BR>
 * 入荷予定日* <BR>
 * 仕入先コード* <BR>
 * TC/DC区分* <BR>
 * 出荷先コード* <BR>
 * 伝票No.* <BR>
 * 商品コード* <BR>
 * ケース入数* <BR>
 * ボール入数* <BR>
 * ホスト予定ケース数+ <BR>
 * ホスト予定ピース数+ <BR>
 * 入荷予定一意キー* <BR>
 * 最終更新日時* <BR>
 * </DIR> <BR>
 * </DIR> <BR>
 * <BR>
 * 3.修正登録ボタン押下処理( <CODE>btn_ModifySubmit_Click</CODE> メソッド) <BR>
 * <BR>
 * <DIR>ためうちエリアから入力した内容をパラメータにセットし、 そのパラメータを基にスケジュールが入荷予定データ修正処理を行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合は はためうちエリア出力用のデータをスケジュールから再取得して、ためうちエリアの再表示を行います。
 * <BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はnullを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>作業者コード*<BR>
 * パスワード*<BR>
 * 荷主コード*<BR>
 * 荷主名称*<BR>
 * 入荷予定日*<BR>
 * 仕入先コード*<BR>
 * 仕入先名称*<BR>
 * TC/DC区分*<BR>
 * 出荷先コード*<BR>
 * 出荷先名称*<BR>
 * 伝票No.*<BR>
 * 行No.*<BR>
 * 商品コード*<BR>
 * 商品名称*<BR>
 * ケース入数*<BR>
 * ボール入数*<BR>
 * ホスト予定ケース数*<BR>
 * ホスト予定ピース数*<BR>
 * ケースITF*<BR>
 * ピースITF*<BR>
 * 入荷予定一意キー*<BR>
 * 最終更新日時*<BR>
 * ためうち行No.*<BR>
 * 端末No.*<BR>
 * </DIR> <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>荷主コード<BR>
 * 荷主名称(登録日時が一番新しいもの)<BR>
 * 入荷予定日<BR>
 * 仕入先コード<BR>
 * 仕入先名称(登録日時が一番新しいもの)<BR>
 * TC/DC区分<BR>
 * TC/DC区分名称<BR>
 * 出荷先コード<BR>
 * 出荷先名称(登録日時が一番新しいもの)<BR>
 * 伝票No.<BR>
 * 行No.<BR>
 * 商品コード<BR>
 * 商品名称<BR>
 * ケース入数<BR>
 * ボール入数<BR>
 * ホスト予定ケース数<BR>
 * ホスト予定ピース数<BR>
 * ケースITF<BR>
 * ボールITF<BR>
 * 入荷予定一意キー<BR>
 * 最終更新日時<BR>
 * 
 * </DIR> <BR>
 * </DIR> <BR>
 * 4.削除、全削除ボタン押下処理( <CODE>lst_SShpPlnDaMdDlt_Click</CODE> メソッド) <BR>
 * <BR>
 * <DIR>ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入荷予定削除を行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、 <BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>荷主コード* <BR>
 * 商品コード* <BR>
 * 入荷予定日* <BR>
 * ためうち.入荷予定一意ｷｰ* <BR>
 * ためうち.最終更新日時* <BR>
 * ためうち.ためうち行No.* <BR>
 * </DIR> </DIR> <BR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/14</TD>
 * <TD>T.Uehata</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author $Author: mori $
 */
public class InstockReceivePlanModify2Business extends InstockReceivePlanModify2 implements WMSConstants
{
	// 商品コード
	private static final String DO_SRCH_ITEM = "/instockreceive/listbox/listinstockreceiveitem/ListInstockReceiveItem.do";

	// viewState用KEY(隠しパラメータ)
	// ためうち行No.
	private static final String LINENO = "LINENO";

	// 入荷予定一意ｷｰ
	private static final String INSTOCKID = "INSTOCKID";

	// 最終更新日時
	private static final String LASTUPDDATE = "LASTUPDDATE";

	// リストセル列指定変数
	// 隠しパラメータ(リストセル)
	private static final int LST_HDN = 0;
	// 削除ボタン(リストセル)
	private static final int LST_DELBTN = 1;
	// 修正ボタン(リストセル)
	private static final int LST_MODBTN = 2;
	// 行No.(リストセル)
	private static final int LST_LINE_NO = 3;
	// 商品コード(リストセル)
	private static final int LST_ITEM_CODE = 4;
	// ケース入数(リストセル)
	private static final int LST_CASEQTY = 5;
	// ホスト予定ケース数(リストセル)
	private static final int LST_HOSTCASEQTY = 6;
	// ケースITF(リストセル)
	private static final int LST_CASEITF = 7;
	// 商品名称(リストセル)
	private static final int LST_ITEM_NAME = 8;
	// ボール入数(リストセル)
	private static final int LST_BUNDLEQTY = 9;
	// ホスト予定ケース数(リストセル)
	private static final int LST_HOSTPIECEQTY = 10;
	// ボールITF(リストセル)
	private static final int LST_BUNDLEITF = 11;

	// リストセル隠し項目順序
	// 入荷予定一意キー(隠しパラメータ順序)
	private static final int HDNIDX_UKEY = 0;
	// 最終更新日時(隠しパラメータ順序)
	private static final int HDNIDX_UPDAY = 1;
	// 更新フラグ(隠しパラメータ順序)
	private static final int HDNUPDATEFLAG_IDX = 2;
	// 行No.(隠しパラメータ順序)
	private static final int HDNLINENO_IDX = 3;
	// 商品コード(隠しパラメータ順序)
	private static final int HDNITEMCODE_IDX = 4;
	// 商品名称(隠しパラメータ順序)
	private static final int HDNITEMNAME_IDX = 5;
	// ケース入数(隠しパラメータ順序)
	private static final int HDNENTERINGQTY_IDX = 6;
	// ボール入数(隠しパラメータ順序)
	private static final int HDNBUNDLEENTERINGQTY_IDX = 7;
	// ホスト予定ケース数(隠しパラメータ順序)
	private static final int HDNHOSTCASEQTY_IDX = 8;
	// ホスト予定ピース数(隠しパラメータ順序)
	private static final int HDNHOSTPIECEQTY_IDX = 9;
	// ケースITF(隠しパラメータ順序)
	private static final int HDNITF_IDX = 10;
	// ボールITF(隠しパラメータ順序)
	private static final int HDNBUNDLEITF_IDX = 11;

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 概要:確認ダイアログを表示します。 <BR>
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
		// 修正登録ボタン押下時確認ダイアログ "修正登録しますか？"
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		// 全削除ボタン押下時確認ダイアログ "全てのデータを削除しますか？"
		btn_AllDelete.setBeforeConfirm("MSG-W0031");
	}

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <BR>
	 * <DIR>1.スケジュールを開始します。 <BR>
	 * <DIR>[パラメータ] *必須入力 +条件によっては必須入力<BR>
	 * <BR>
	 * <DIR>荷主コード* <BR>
	 * 入荷受付日* <BR>
	 * 仕入先コード* <BR>
	 * TC/DC区分* <BR>
	 * 出荷先コード+ <BR>
	 * 伝票No.* <BR>
	 * </DIR> <BR>
	 * [返却データ] <BR>
	 * <BR>
	 * <DIR>荷主コード <BR>
	 * 荷主名称(登録日時が一番新しいもの) <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * 仕入先名称(登録日時が一番新しいもの) <BR>
	 * TC/DC区分 <BR>
	 * TC/DC区分名称 <BR>
	 * 出荷先コード <BR>
	 * 出荷先名称(登録日時が一番新しいもの) <BR>
	 * 伝票No. <BR>
	 * 行No. <BR>
	 * 商品コード <BR>
	 * 商品名称 <BR>
	 * ケース入数 <BR>
	 * ボール入数 <BR>
	 * ホスト予定ケース数 <BR>
	 * ホスト予定ピース数 <BR>
	 * ケースITF <BR>
	 * ボールITF <BR>
	 * 入荷予定一意ｷｰ <BR>
	 * 最終更新日時 <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 2.該当データが見つからない場合は要素数0のParameter配列を、条件エラーなどが発生した場合はnullを受け取り、前画面に戻ります。
	 * <BR>
	 * <BR>
	 * 3.スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * <BR>
	 * 4.ViewStateのためうち行No.に初期値:-1を設定します。 <BR>
	 * <BR>
	 * 5.入力ボタン・クリアボタンを無効にします。 <BR>
	 * <BR>
	 * 6.カーソルを行No.に初期セットします。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(InstockReceivePlanModifyBusiness.TITLE)));

		setFocus(txt_LineNo);

		Connection conn = null;

		try
		{

			// 詳細情報修正・削除のタブを前出しします
			tab_BscDtlMdfyDlt.setSelectedIndex(2);

			// スケジュールパラメータへセットします
			InstockReceiveParameter param = new InstockReceiveParameter();
			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(InstockReceivePlanModifyBusiness.WORKERCODE));
			// パスワード
			param.setPassword(this.getViewState().getString(InstockReceivePlanModifyBusiness.PASSWORD));
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
			// 入荷受付日
			param.setPlanDate(this.getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY));
			// 仕入先コード
			param.setSupplierCode(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY));
			// TC/DC区分
			param.setTcdcFlag(this.getViewState().getString(InstockReceivePlanModifyBusiness.TCDCFLAG));
			// 出荷先コード
			param.setCustomerCode(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY));
			// 伝票No.
			param.setInstockTicketNo(this.getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY));

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new InstockReceivePlanModifySCH();

			// ためうちエリアに表示するデータを取得します
			InstockReceiveParameter[] viewparam = (InstockReceiveParameter[]) schedule.query(conn, param);

			// エラーなどが発生した場合はメッセージを受け取り、基本情報設定画面に戻る
			if (viewparam == null || viewparam.length == 0)
			{
				// ViewStateにメッセージをセット
				this.getViewState().setString(InstockReceivePlanModifyBusiness.MESSAGE, schedule.getMessage());
				// 詳細情報修正・削除画面->基本情報設定画面
				forward(InstockReceivePlanModifyBusiness.DO_MODIFY);

				return;
			}

			// 入力エリア、ラベル項目を設定します
			// 荷主コード
			lbl_JavaSetCnsgnrCd.setText(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
			// 荷主名称
			lbl_JavaSetCnsgnrNm.setText(viewparam[0].getConsignorName());
			// 入荷予定日
			txt_FDate.setDate(WmsFormatter.toDate(viewparam[0].getPlanDate()));
			// 仕入先コード
			lbl_JavaSetSplCd.setText(viewparam[0].getSupplierCode());
			// 仕入先名称
			lbl_JavaSetSplNm.setText(viewparam[0].getSupplierName());
			// ＴＣ／ＤＣ区分
			lbl_JavaSetTCDCFlag.setText(viewparam[0].getTcdcName());
			// 出荷先コード
			lbl_JavaSetCustCd.setText(viewparam[0].getCustomerCode());
			// 出荷先名称
			lbl_JavaSetCustNm.setText(viewparam[0].getCustomerName());
			// 伝票No.
			lbl_JavaSetTicketNo.setText(viewparam[0].getInstockTicketNo());

			// ためうちエリア.リストセルに取得データを表示します
			setList(viewparam);

			// 入力エリアを初期化します
			inputAreaClear();

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

			// ためうち行にデータがないとき
			if (lst_SInstkRcvPlanModify.getMaxRows() == 1)
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
	 * ポップアップウインドから、戻ってくるときにこのメソッドが 呼ばれます。 <CODE>Page</CODE> に定義されている <CODE>
	 * page_DlgBack</CODE> をオーバライドします。 <BR>
	 * <BR>
	 * 概要:検索画面の返却データを取得しセットします <BR>
	 * <BR>
	 * <DIR>1.ポップアップの検索画面から返される値を取得します。 <BR>
	 * 2.値が空でないときに画面にセットします。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();

		// 空ではないときに値をセットする
		String itemCode = param.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		String itemName = param.getParameter(ListInstockReceiveItemBusiness.ITEMNAME_KEY);

		// 商品コード
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemName))
		{
			txt_ItemName.setText(itemName);
		}

		setFocus(txt_LineNo);
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
		
		return true;
		
	}

	// Private methods -----------------------------------------------
	/**
	 * 入力エリアを初期状態にします。 <BR>
	 * <BR>
	 * 概要:入力エリアを全て空にします。 <BR>
	 * <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void inputDataClear() throws Exception
	{
		// 行No.
		txt_LineNo.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// 商品名称
		txt_ItemName.setText("");
		// ケース入数
		txt_CaseEntering.setText("");
		// ボール入数
		txt_BundleEntering.setText("");
		// ホスト予定ケース数
		txt_HostCasePlanQty.setText("");
		// ホスト予定ピース数
		txt_HostPiecePlanQty.setText("");
		// ケースITF
		txt_CaseItf.setText("");
		// ボールITF
		txt_BundleItf.setText("");
	}

	/**
	 * 入力エリアを初期状態にします。 <BR>
	 * <BR>
	 * 概要:入力エリアを全て空にし、入力ボタン・クリアボタンを無効化します。 <BR>
	 * <BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	private void inputAreaClear() throws Exception
	{
		// 入力項目を空にします
		inputDataClear();

		// 入力エリアのボタンを無効化します
		// 入力ボタン
		btn_Input.setEnabled(false);
		// クリアボタン
		btn_Clear.setEnabled(false);

		// 修正状態(ためうち行No)を初期値へリセットします
		this.viewState.setInt(LINENO, -1);

		// 選択行の色を初期値へリセットします
		lst_SInstkRcvPlanModify.resetHighlight();

		// カーソルを行No.へセットします
		setFocus(txt_LineNo);
	}

	/**
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。 <BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1) <BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。 <BR>
	 * 3.修正登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1) <BR>
	 * <DIR>[パラメータ] *必須入力 <BR>
	 * <DIR>修正対象ためうち行No.*(修正対象がない場合は-1をセットする) <BR>
	 * </DIR> [返却データ] <BR>
	 * <DIR>ためうちエリアの内容を持つ <CODE>InstockReceiveParameter</CODE> クラスのインスタンスの配列
	 * <BR>
	 * </DIR> </DIR>
	 * 
	 * @param lineNo
	 * @throws Exception 全ての例外を報告します。
	 */
	private InstockReceiveParameter[] setListParam(int lineNo) throws Exception
	{
		setFocus(txt_LineNo);

		Vector vecParam = new Vector();

		Locale locale = this.getHttpRequest().getLocale();

		String workerCd = viewState.getString(InstockReceivePlanModifyBusiness.WORKERCODE);
		String password = viewState.getString(InstockReceivePlanModifyBusiness.PASSWORD);

		for (int i = 1; i < lst_SInstkRcvPlanModify.getMaxRows(); i++)
		{
			// 修正対象行は処理しません
			if (i == lineNo)
			{
				continue;
			}

			// 編集行を指定します
			lst_SInstkRcvPlanModify.setCurrentRow(i);

			// 変更されていないデータはセットしない
			if (lineNo < 0 
			 && CollectionUtils.getString(HDNUPDATEFLAG_IDX, lst_SInstkRcvPlanModify.getValue(LST_HDN)).equals(InstockReceiveParameter.UPDATEFLAG_NO))
			{
				continue;
			}

			// スケジュールパラメータへセットします
			InstockReceiveParameter param = new InstockReceiveParameter();

			// 基本画面情報
			// 作業者コード
			param.setWorkerCode(workerCd);
			// パスワード
			param.setPassword(password);
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主名称
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 入荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FDate.getDate()));
			// 仕入先コード
			param.setSupplierCode(lbl_JavaSetSplCd.getText());
			// 仕入先名称
			param.setSupplierName(lbl_JavaSetSplNm.getText());
			// ＴＣ／ＤＣ区分
			param.setTcdcFlag(getViewState().getString(InstockReceivePlanModifyBusiness.TCDCFLAG));
			// 出荷先コード
			param.setCustomerCode(lbl_JavaSetCustCd.getText());
			// 出荷先名称
			param.setCustomerName(lbl_JavaSetCustNm.getText());
			// 伝票No.
			param.setInstockTicketNo(lbl_JavaSetTicketNo.getText());

			// ためうちエリア.リストセルの内容
			// ためうち行No.
			param.setRowNo(i);

			// 行No.
			param.setInstockLineNo(WmsFormatter.getInt(lst_SInstkRcvPlanModify.getValue(LST_LINE_NO)));
			// 商品コード
			param.setItemCode(lst_SInstkRcvPlanModify.getValue(LST_ITEM_CODE));
			// 商品名称
			param.setItemName(lst_SInstkRcvPlanModify.getValue(LST_ITEM_NAME));
			// ケース入数
			param.setEnteringQty(WmsFormatter.getInt(lst_SInstkRcvPlanModify.getValue(LST_CASEQTY)));
			// ボール入数
			param.setBundleEnteringQty(WmsFormatter.getInt(lst_SInstkRcvPlanModify.getValue(LST_BUNDLEQTY)));
			// ホスト予定ケース数
			param.setPlanCaseQty(WmsFormatter.getInt(lst_SInstkRcvPlanModify.getValue(LST_HOSTCASEQTY)));
			// ホスト予定ピース数
			param.setPlanPieceQty(WmsFormatter.getInt(lst_SInstkRcvPlanModify.getValue(LST_HOSTPIECEQTY)));
			// ケースITF
			param.setITF(lst_SInstkRcvPlanModify.getValue(LST_CASEITF));
			// ボールITF
			param.setBundleITF(lst_SInstkRcvPlanModify.getValue(LST_BUNDLEITF));

			// 隠し項目
			// 入荷予定一意ｷｰ
			param.setInstockPlanUkey(CollectionUtils.getString(HDNIDX_UKEY, lst_SInstkRcvPlanModify.getValue(LST_HDN)));
			// 最終更新日時
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HDNIDX_UPDAY, lst_SInstkRcvPlanModify.getValue(LST_HDN))));

			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			// 1行分をひとまとめにして追加
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセットします
			InstockReceiveParameter[] listparam = new InstockReceiveParameter[vecParam.size()];
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
	 * ためうちエリアにParameterの値をセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:ためうちエリアにParameterの値をセットします。 <BR>
	 * <DIR>隠し項目 <DIR>0.入荷予定一意ｷｰ <BR>
	 * 1.最終更新日時 <BR>
	 * </DIR> </DIR> <BR>
	 * <DIR>[パラメータ] *必須入力 <BR>
	 * <DIR>ためうちエリアの内容を持つ <CODE>InstockReceiveParameter</CODE> クラスのインスタンスの配列
	 * <BR>
	 * </DIR> </DIR> page_Load(ActionEvent)から呼ばれます。
	 * 
	 * @param param
	 *            InstockReceiveParameter[] ためうちエリア情報
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setList(InstockReceiveParameter[] param) throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();

		// ToolTip項目名称
		String ttparam01 = DisplayText.getText("LBL-W0103");

		for (int i = 0; i < param.length; i++)
		{
			// 行追加
			lst_SInstkRcvPlanModify.addRow();

			// 編集行を指定します
			lst_SInstkRcvPlanModify.setCurrentRow(i + 1);

			// 隠しパラメータ
			Vector hdnpara = new Vector();
			// 隠しパラメータ.入荷予定一意ｷｰ
			hdnpara.add(param[i].getInstockPlanUkey());
			// 隠しパラメータ.最終更新日時
			hdnpara.add(WmsFormatter.getTimeStampString(param[i].getLastUpdateDate()));
			// 隠しパラメータ.更新フラグ
			hdnpara.add(InstockReceiveParameter.UPDATEFLAG_NO);
			// 隠しパラメータ.行No.
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getInstockLineNo()));
			// 隠しパラメータ.商品コード
			hdnpara.add(param[i].getItemCode());
			// 隠しパラメータ.商品名称
			hdnpara.add(param[i].getItemName());
			// 隠しパラメータ.ケース入数
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			// 隠しパラメータ.ボール入数
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			// 隠しパラメータ.ホスト予定ケース数
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			// 隠しパラメータ.ホスト予定ピース数
			hdnpara.add(WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			// 隠しパラメータ.ケースITF
			hdnpara.add(param[i].getITF());
			// 隠しパラメータ.ボールITF
			hdnpara.add(param[i].getBundleITF());

			// 隠しパラメータをリストセルに格納
			lst_SInstkRcvPlanModify.setValue(LST_HDN, CollectionUtils.getConnectedString(hdnpara));

			// 行No.
			lst_SInstkRcvPlanModify.setValue(LST_LINE_NO, WmsFormatter.getNumFormat(param[i].getInstockLineNo()));
			// 商品コード
			lst_SInstkRcvPlanModify.setValue(LST_ITEM_CODE, param[i].getItemCode());
			// 商品名称
			lst_SInstkRcvPlanModify.setValue(LST_ITEM_NAME, param[i].getItemName());
			// ケース入数
			lst_SInstkRcvPlanModify.setValue(LST_CASEQTY, WmsFormatter.getNumFormat(param[i].getEnteringQty()));
			// ホスト予定ケース数
			lst_SInstkRcvPlanModify.setValue(LST_HOSTCASEQTY, WmsFormatter.getNumFormat(param[i].getPlanCaseQty()));
			// ケースITF
			lst_SInstkRcvPlanModify.setValue(LST_CASEITF, param[i].getITF());
			// ボール入数
			lst_SInstkRcvPlanModify.setValue(LST_BUNDLEQTY, WmsFormatter.getNumFormat(param[i].getBundleEnteringQty()));
			// ホスト予定ピース数
			lst_SInstkRcvPlanModify.setValue(LST_HOSTPIECEQTY, WmsFormatter.getNumFormat(param[i].getPlanPieceQty()));
			// ボールITF
			lst_SInstkRcvPlanModify.setValue(LST_BUNDLEITF, param[i].getBundleITF());

			// Toolチップは無し
		}
	}

	/**
	 * ためうちエリアに入力エリアの修正データセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:ためうちエリアに入力エリアの修正データをセットします。 <BR>
	 * このメソッドを呼ぶ前に、リストセルをsetCurrentRow(int)で編集対象行を指定してください。 <BR>
	 * <DIR>隠し項目 <BR>
	 * <DIR>0.入荷予定一意ｷｰ <BR>
	 * 1.最終更新日時 <BR>
	 * </DIR> </DIR> <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setListRow() throws Exception
	{
		// 隠しパラメータ
		ArrayList hdnpara = (ArrayList) CollectionUtils.getList(lst_SInstkRcvPlanModify.getValue(LST_HDN));
		// 隠しパラメータ.更新フラグ
		if (updateCheck())
		{
			hdnpara.set(HDNUPDATEFLAG_IDX, InstockReceiveParameter.UPDATEFLAG_YES);
		}
		else
		{
			hdnpara.set(HDNUPDATEFLAG_IDX, InstockReceiveParameter.UPDATEFLAG_NO);
		}
		// 隠しパラメータをリストセルに格納
		lst_SInstkRcvPlanModify.setValue(LST_HDN, CollectionUtils.getConnectedString(hdnpara));

		// 行No.
		lst_SInstkRcvPlanModify.setValue(LST_LINE_NO, txt_LineNo.getText());
		// 商品コード
		lst_SInstkRcvPlanModify.setValue(LST_ITEM_CODE, txt_ItemCode.getText());
		// 商品名称
		lst_SInstkRcvPlanModify.setValue(LST_ITEM_NAME, txt_ItemName.getText());
		// ケース入数
		lst_SInstkRcvPlanModify.setValue(LST_CASEQTY, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		// ホスト予定ケース数
		lst_SInstkRcvPlanModify.setValue(LST_HOSTCASEQTY, WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		// ケースITF
		lst_SInstkRcvPlanModify.setValue(LST_CASEITF, txt_CaseItf.getText());
		// ボール入数
		lst_SInstkRcvPlanModify.setValue(LST_BUNDLEQTY, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		// ホスト予定ピース数
		lst_SInstkRcvPlanModify.setValue(LST_HOSTPIECEQTY, WmsFormatter.getNumFormat(txt_HostPiecePlanQty.getInt()));
		// ボールITF
		lst_SInstkRcvPlanModify.setValue(LST_BUNDLEITF, txt_BundleItf.getText());
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
		String hiddenParam = lst_SInstkRcvPlanModify.getValue(LST_HDN);
		
		// 行No.
		if (txt_LineNo.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDNLINENO_IDX, hiddenParam)))
		{
			return true;
		}
		// 商品コード
		if (!txt_ItemCode.getText().equals(CollectionUtils.getString(HDNITEMCODE_IDX, hiddenParam)))
		{
			return true;
		}
		// 商品名称
		if (!txt_ItemName.getText().equals(CollectionUtils.getString(HDNITEMNAME_IDX, hiddenParam)))
		{
			return true;
		}
		// ケース入数
		if (txt_CaseEntering.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDNENTERINGQTY_IDX, hiddenParam)))
		{
			return true;
		}
		// ボール入数
		if (txt_BundleEntering.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDNBUNDLEENTERINGQTY_IDX, hiddenParam)))
		{
			return true;
		}
		// ホスト予定ケース数
		if (txt_HostCasePlanQty.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDNHOSTCASEQTY_IDX, hiddenParam)))
		{
			return true;
		}
		// ホスト予定ピース数
		if (txt_HostPiecePlanQty.getInt() != WmsFormatter.getInt(CollectionUtils.getString(HDNHOSTPIECEQTY_IDX, hiddenParam)))
		{
			return true;
		}
		// ケースITF
		if (!txt_CaseItf.getText().equals(CollectionUtils.getString(HDNITF_IDX, hiddenParam)))
		{
			return true;
		}
		// ボールITF
		if (!txt_BundleItf.getText().equals(CollectionUtils.getString(HDNBUNDLEITF_IDX, hiddenParam)))
		{
			return true;
		}

		return false;
	}
	
	// Event handler methods -----------------------------------------
	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * メニューボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。 <BR>
	 * <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void ID4_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_FDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_FDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_FDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_Supplier_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetSplCd_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetSplNm_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_TCDCFlag_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetTCDCFlag_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_Customer_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetCustCd_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetCustNm_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_JavaSetTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_LineNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LineNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LineNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_LineNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 商品検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>荷主コード <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * TC/DC区分 <BR>
	 * 出荷先コード <BR>
	 * 伝票No. <BR>
	 * 商品コード <BR>
	 * 検索フラグ <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		// 商品コード
		param.setParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(ListInstockReceiveItemBusiness.SEARCHITEM_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);

		// 処理中画面->結果画面
		redirect(DO_SRCH_ITEM, param, InstockReceivePlanModifyBusiness.DO_SRCH_PROCESS);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_HostCasePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_HostCasePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_HostCasePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_HostPiecePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_HostPiecePlanQty_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_HostPiecePlanQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_HostPiecePlanQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 入力ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、入荷予定情報を検索し、ためうちに表示します。 <BR>
	 * <BR>
	 * <DIR>1.カーソルを行No.にセットします。 <BR>
	 * 2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性) <BR>
	 * 3.スケジュールを開始します。 <BR>
	 * <DIR>[パラメータ] *必須入力 +条件により必須入力<BR>
	 * <DIR>荷主コード* <BR>
	 * 入荷予定日* <BR>
	 * 仕入先コード* <BR>
	 * TC/DC区分* <BR>
	 * 出荷先コード* <BR>
	 * 伝票No.* <BR>
	 * 行No.* <BR>
	 * 商品コード* <BR>
	 * ケース入数* <BR>
	 * ホスト予定ケース数+ <BR>
	 * ホスト予定ピース数+ <BR>
	 * 入荷予定一意ｷｰ* <BR>
	 * 最終更新日時* <BR>
	 * </DIR> </DIR> <BR>
	 * 4.スケジュールの結果がtrueであれば、入力エリアの情報でためうちの修正対象データを更新します。 <BR>
	 * 5.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。 <BR>
	 * 6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 7.スケジュールの結果がtrueであれば、入力ボタン・クリアボタンを無効にします。 <BR>
	 * 8.スケジュールの結果がtrueであれば、入力エリアの内容はクリアします。 <BR>
	 * 9.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを行No.へセットします
		setFocus(txt_LineNo);

		// パターンマッチングを行います
		// 行No.
		txt_LineNo.validate();
		// 商品コード
		txt_ItemCode.validate();
		// 商品名称
		txt_ItemName.validate(false);
		// ケース入数
		txt_CaseEntering.validate(false);
		// ボール入数
		txt_BundleEntering.validate(false);
		// ホスト予定ケース数
		txt_HostCasePlanQty.validate(false);
		// ホスト予定ピース数
		txt_HostPiecePlanQty.validate(false);
		// ケースITF
		txt_CaseItf.validate(false);
		// ボールITF
		txt_BundleItf.validate(false);

		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}

		Connection conn = null;

		try
		{
			// スケジュールパラメータへ画面内容をセットします
			InstockReceiveParameter param = new InstockReceiveParameter();

			// ユーザーが入力できない値(ラベル項目、隠しパラメータ)
			// 入荷予定一意ｷｰ
			param.setInstockPlanUkey(this.getViewState().getString(INSTOCKID));
			// 最終更新日時
			param.setLastUpdateDate(WmsFormatter.getTimeStampDate(this.getViewState().getString(LASTUPDDATE)));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主名称(桁数チェック用）
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 入荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FDate.getDate()));
			// 仕入先コード
			param.setSupplierCode(lbl_JavaSetSplCd.getText());
			// 仕入先名称(桁数チェック用）
			param.setSupplierName(lbl_JavaSetSplNm.getText());
			// TC/DC区分
			param.setTcdcFlag(this.getViewState().getString(InstockReceivePlanModifyBusiness.TCDCFLAG));
			// 出荷先コード
			param.setCustomerCode(lbl_JavaSetCustCd.getText());
			// 出荷先名称(桁数チェック用）
			param.setCustomerName(lbl_JavaSetCustNm.getText());
			// 伝票No.
			param.setInstockTicketNo(lbl_JavaSetTicketNo.getText());

			// ユーザーが入力できる項目(テキストボックス)
			// 行No.
			param.setInstockLineNo(txt_LineNo.getInt());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケース入数
			param.setEnteringQty(WmsFormatter.getInt(txt_CaseEntering.getText()));
			// ボール入数
			param.setBundleEnteringQty(WmsFormatter.getInt(txt_BundleEntering.getText()));
			// ホスト予定ケース数
			param.setPlanCaseQty(WmsFormatter.getInt(txt_HostCasePlanQty.getText()));
			// ホスト予定ピース数
			param.setPlanPieceQty(WmsFormatter.getInt(txt_HostPiecePlanQty.getText()));
			// ケースITF
			param.setITF(txt_CaseItf.getText());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());

			// スケジュールパラメータへためうち情報をセットします(スケジュール側の入力チェックで必要になります)
			// ためうちエリア
			InstockReceiveParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_SInstkRcvPlanModify.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			// スケジュールクラスのインスタンスを生成します
			WmsScheduler schedule = new InstockReceivePlanModifySCH();

			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールを開始します
			// スケジュールクラスで、入力チェックと存在チェックを行います
			if (schedule.check(conn, param, listparam))
			{
				// 修正対象行を編集対象とします
				lst_SInstkRcvPlanModify.setCurrentRow(this.getViewState().getInt(LINENO));

				// ためうちに入力エリアの値をセット
				setListRow();

				// 入力エリアの初期化を行います
				inputAreaClear();
			}

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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
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
	 * <DIR>1.入力エリアの項目を初期値に戻します。 <BR>
	 * <DIR>･初期値については <CODE>inputDataClear()</CODE> メソッドを参照してください。 <BR>
	 * </DIR> 2.カーソルを行No.にセットします。 <BR>
	 * 3.ためうちエリアの内容は保持します。 <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力データを空にします
		inputDataClear();
		// フォーカスを行No.にセットします
		setFocus(txt_LineNo);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 修正登録ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、入荷予定データ修正登録を行います。 <BR>
	 * <BR>
	 * <DIR>1.カーソルを行No.にセットします。 <BR>
	 * 2.修正登録を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <DIR>"修正登録しますか？" <BR>
	 * [確認ダイアログ キャンセル] <BR>
	 * <DIR>何も行いません。 </DIR> [確認ダイアログ OK] <BR>
	 * <DIR>1.スケジュールを開始します。 <BR>
	 * <DIR>[パラメータ] *必須入力 <BR>
	 * <BR>
	 * <DIR>作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 荷主名称* <BR>
	 * 入荷予定日* <BR>
	 * 仕入先コード* <BR>
	 * 仕入先名称* <BR>
	 * TC/DC区分* <BR>
	 * 出荷先コード* <BR>
	 * 出荷先名称* <BR>
	 * 伝票No.* <BR>
	 * 行No.* <BR>
	 * 商品コード* <BR>
	 * 商品名称* <BR>
	 * ケース入数* <BR>
	 * ボール入数* <BR>
	 * ホスト予定ケース数* <BR>
	 * ホスト予定ピース数* <BR>
	 * ケースITF* <BR>
	 * ピースITF* <BR>
	 * 入荷予定一意キー* <BR>
	 * 最終更新日時* <BR>
	 * ためうち行No.* <BR>
	 * 端末No.* <BR>
	 * </DIR> <BR>
	 * [返却データ] <BR>
	 * <BR>
	 * <DIR>荷主コード <BR>
	 * 荷主名称(登録日時が一番新しいもの) <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * 仕入先名称(登録日時が一番新しいもの) <BR>
	 * TC/DC区分 <BR>
	 * TC/DC区分名称 <BR>
	 * 出荷先コード <BR>
	 * 出荷先名称(登録日時が一番新しいもの) <BR>
	 * 伝票No. <BR>
	 * 行No. <BR>
	 * 商品コード <BR>
	 * 商品名称 <BR>
	 * ケース入数 <BR>
	 * ボール入数 <BR>
	 * ホスト予定ケース数 <BR>
	 * ホスト予定ピース数 <BR>
	 * ケースITF <BR>
	 * ボールITF <BR>
	 * 入荷予定一意キー <BR>
	 * 最終更新日時 <BR>
	 *  <BR>
	 * </DIR> </DIR> <BR>
	 * <BR>
	 * 2.スケジュールの結果がtrueの時は、返却データを元にためうちのデータを再表示します。 <BR>
	 * 3.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。 <BR>
	 * 4.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 5.スケジュールの結果がtrueの時は、入力・クリアボタンを無効にします。 <BR>
	 * 6.スケジュールの結果がtrueであれば、修正対象行の色を元に戻します。 <BR>
	 * 7.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * </DIR> </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを行No.にセットします
			setFocus(txt_LineNo);

			// スケジュールパラメータへセットします
			InstockReceiveParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1);

			if (param == null || param.length <= 0)
			{
				// メッセージをセットします
				// 6003013=修正対象データがありませんでした。
				message.setMsgResourceKey("6003013");
				return;
			}
			
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 入荷予定修正・削除スケジュールクラスのインスタンスを生成します
			WmsScheduler schedule = new InstockReceivePlanModifySCH();

			// 在庫の修正処理を実行します(修正後の表示データが返却されます)
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) schedule.startSCHgetParams(conn, param);

			// エラーなどが発生した場合は、メッセージを受け取って終了します
			if (viewParam == null)
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
			lst_SInstkRcvPlanModify.clearRow();

			// ためうちエリアを再表示します
			setList(viewParam);

			// 入力エリアをクリアします
			// (ためうち行Noのクリア、背景色の変更も同時に行われます)
			inputAreaClear();

			// スケジュールからのメッセージをセットします
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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
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
	 * <DIR>1.ためうち情報の全削除を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <DIR>"全てのデータを削除しますか？" <BR>
	 * [確認ダイアログ キャンセル] <BR>
	 * <DIR>何も行いません。 </DIR> [確認ダイアログ OK] <BR>
	 * <DIR>1.スケジュールを開始します。 <BR>
	 * <DIR>[パラメータ] *必須入力 <BR>
	 * <DIR>入荷予定一意キー* <BR>
	 * 最終更新日時*  <BR>
	 * ためうち行No.*  <BR>
	 * </DIR>
	 * </DIR> 2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。 <BR>
	 * 3.スケジュールの結果がtrueの時は、ためうちの表示情報を全て削除します。 <BR>
	 * 4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 5.スケジュールの結果がtrueの時は、修正登録・全削除・入力・クリアボタンを無効にします。 <BR>
	 * 6.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * 7.カーソルをケース入荷棚にセットします。 <BR>
	 * </DIR> <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_AllDelete_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		Locale locale = this.httpRequest.getLocale();

		try
		{
			// スケジュールパラメータへセット
			InstockReceiveParameter[] listparam = null;

			Vector vecParam = new Vector();

			// ためうち行のパラメータをセットします
			for (int i = 1; i < lst_SInstkRcvPlanModify.getMaxRows(); i++)
			{
				// 取得対象となるリストセルの行番号を指定します
				lst_SInstkRcvPlanModify.setCurrentRow(i);

				// 1行分のデータを格納するパラメータクラスを作成します
				InstockReceiveParameter param = new InstockReceiveParameter();

				// パラメータをセットします
				// 対象行No.
				param.setRowNo(i);
				// 入荷予定一意ｷｰ
				param.setInstockPlanUkey(CollectionUtils.getString(HDNIDX_UKEY, lst_SInstkRcvPlanModify.getValue(LST_HDN)));
				// 最終更新日時
				param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HDNIDX_UPDAY, lst_SInstkRcvPlanModify.getValue(LST_HDN))));
				// 荷主コード
				param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());

				// 配列に1行単位でデータをセットします
				vecParam.addElement(param);
			}

			if (vecParam.size() > 0)
			{
				// ためうち行が存在した場合、値をセットします
				listparam = new InstockReceiveParameter[vecParam.size()];
				vecParam.copyInto(listparam);
			}

			// 在庫修正・削除スケジュールクラスのインスタンスを生成します
			WmsScheduler schedule = new InstockReceivePlanModifySCH();

			// コネクションを取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 削除処理を実行
			if (schedule.startSCH(conn, listparam))
			{
				// 処理が正常に完了すればコミットします
				conn.commit();

				// ためうち行を全削除します
				lst_SInstkRcvPlanModify.clearRow();

				// 入力エリアをクリアします
				inputAreaClear();

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
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_SInstkRcvPlanModify_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_SInstkRcvPlanModify_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_SInstkRcvPlanModify_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_SInstkRcvPlanModify_ColumClick(ActionEvent e) throws Exception
	{

	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_SInstkRcvPlanModify_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lst_SInstkRcvPlanModify_Change(ActionEvent e) throws Exception
	{
	}

	/**
	 * 削除、修正ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 削除ボタン概要:ためうちの該当データを削除します。 <BR>
	 * <BR>
	 * <DIR>1.ためうち情報の削除を行うかを、ダイアログボックスを表示し、確認します。 <BR>
	 * <DIR>"削除しますか？" <BR>
	 * [確認ダイアログ キャンセル] <BR>
	 * <DIR>何も行いません。 <BR>
	 * </DIR> [確認ダイアログ OK] <BR>
	 * <DIR>1.スケジュールを開始します。 <BR>
	 * <DIR>[パラメータ] *必須入力 <BR>
	 * <DIR>入荷予定一意キー* <BR>
	 * 最終更新日時* <BR>
	 * ためうち行No.* <BR>
	 * </DIR> 
	 * </DIR>
	 * 2.スケジュールの結果がtrueの時は、入力エリアの項目を全てクリアします。 <BR>
	 * 3.スケジュールの結果がtrueの時は、ためうちの該当データを削除します。 <BR>
	 * 4.スケジュールの結果がtrueの時は、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。 <BR>
	 * 5.スケジュールの結果がtrueの時は、入力・クリアボタンを無効にします。 <BR>
	 * 6.ためうち情報が存在しない場合、修正登録・全削除ボタンを無効にします。 <BR>
	 * 7.スケジュールから取得したメッセージを画面に出力します。 <BR>
	 * 8.カーソルを行No.にセットします。 <BR>
	 * </DIR> </DIR> </DIR> <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。 <BR>
	 * <BR>
	 * <DIR>1.選択されたためうち行情報を上部の入力エリアに表示します。 <BR>
	 * 2.選択されたためうち行を薄黄色にします。 <BR>
	 * 3.ViewState.ためうち行No.に修正対象行を設定します。 <BR>
	 * 4.カーソルを行No.にセットします。 <BR>
	 * 5.入力ボタン・クリアボタンを有効にします。 <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void lst_SInstkRcvPlanModify_Click(ActionEvent e) throws Exception
	{
		// 削除ボタンクリック
		if (lst_SInstkRcvPlanModify.getActiveCol() == LST_DELBTN)
		{
			Connection conn = null;

			try
			{
				// 削除ボタンが押された行のデータを扱います
				lst_SInstkRcvPlanModify.setCurrentRow(lst_SInstkRcvPlanModify.getActiveRow());

				// スケジュールパラメータへセットします
				InstockReceiveParameter[] param = new InstockReceiveParameter[1];
				param[0] = new InstockReceiveParameter();

				// 入荷予定一意ｷｰ
				param[0].setInstockPlanUkey(CollectionUtils.getString(HDNIDX_UKEY, lst_SInstkRcvPlanModify.getValue(LST_HDN)));
				// 最終更新日時
				param[0].setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HDNIDX_UPDAY, lst_SInstkRcvPlanModify.getValue(LST_HDN))));
				// ためうち行No.
				param[0].setRowNo(lst_SInstkRcvPlanModify.getActiveRow());

				// 入荷予定修正・削除スケジュールクラスのインスタンスを生成します
				WmsScheduler schedule = new InstockReceivePlanModifySCH();

				// コネクションを取得します
				conn = ConnectionManager.getConnection(DATASOURCE_NAME);

				// 削除処理を実行
				if (schedule.startSCH(conn, param))
				{
					// 処理が正常に完了すればコミットします
					conn.commit();

					// 削除されたためうち行を削除します
					lst_SInstkRcvPlanModify.removeRow(lst_SInstkRcvPlanModify.getActiveRow());

					// 入力エリアクリア
					inputAreaClear();

					// ためうち情報が存在しないとき、修正登録・全削除ボタンを無効化します
					if (lst_SInstkRcvPlanModify.getMaxRows() == 1)
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
					message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
				}
			}
		}
		// 修正ボタンクリック時
		else if (lst_SInstkRcvPlanModify.getActiveCol() == LST_MODBTN)
		{
			// 修正ボタンが押された行のデータを扱います
			lst_SInstkRcvPlanModify.setCurrentRow(lst_SInstkRcvPlanModify.getActiveRow());

			// ためうちエリアの内容を、入力エリアへ展開します
			// 行No.
			txt_LineNo.setText(lst_SInstkRcvPlanModify.getValue(LST_LINE_NO));
			// 商品コード
			txt_ItemCode.setText(lst_SInstkRcvPlanModify.getValue(LST_ITEM_CODE));
			// 商品名称
			txt_ItemName.setText(lst_SInstkRcvPlanModify.getValue(LST_ITEM_NAME));
			// ケース入数
			txt_CaseEntering.setText(lst_SInstkRcvPlanModify.getValue(LST_CASEQTY));
			// ボール入数
			txt_BundleEntering.setText(lst_SInstkRcvPlanModify.getValue(LST_BUNDLEQTY));
			// ホスト予定ケース数
			txt_HostCasePlanQty.setText(lst_SInstkRcvPlanModify.getValue(LST_HOSTCASEQTY));
			// ホスト予定ピース数
			txt_HostPiecePlanQty.setText(lst_SInstkRcvPlanModify.getValue(LST_HOSTPIECEQTY));
			// ケースITF
			txt_CaseItf.setText(lst_SInstkRcvPlanModify.getValue(LST_CASEITF));
			// ボールITF
			txt_BundleItf.setText(lst_SInstkRcvPlanModify.getValue(LST_BUNDLEITF));

			// 隠しパラメータをViewStateにセットします
			// 修正選択行
			this.viewState.setInt(LINENO, lst_SInstkRcvPlanModify.getActiveRow());
			// 入荷予定一意ｷｰ
			this.viewState.setString(INSTOCKID, CollectionUtils.getString(HDNIDX_UKEY, lst_SInstkRcvPlanModify.getValue(LST_HDN)));
			// 最終更新日時
			this.viewState.setString(LASTUPDDATE, CollectionUtils.getString(HDNIDX_UPDAY, lst_SInstkRcvPlanModify.getValue(LST_HDN)));
			// 対象修正行を黄色に変更します
			lst_SInstkRcvPlanModify.setHighlight(lst_SInstkRcvPlanModify.getActiveRow());

			// 入力エリア内ボタンを有効化します
			// 入力ボタン
			btn_Input.setEnabled(true);
			// クリアボタン
			btn_Clear.setEnabled(true);
		}

		// カーソルを行No.にセットします
		setFocus(txt_LineNo);
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
	 * 概要:出庫予定データ修正・削除(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		// 詳細情報修正・削除画面->基本情報設定画面
		forward(InstockReceivePlanModifyBusiness.DO_MODIFY);
	}

}
//end of class

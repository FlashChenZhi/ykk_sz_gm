package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;
import jp.co.daifuku.wms.base.rft.WorkDetails;

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * WEBTC入荷作業メンテナンスを行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、TC入荷作業メンテナンス処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   作業情報（入荷）に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     状態フラグ：削除(9)以外 <BR>
 *     TC/DC区分：TC(1) <BR>
 *     作業区分：入荷(1) </DIR></DIR>
 * <BR>
 * 2.表示ボタン押下処理（<CODE>query()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   仕入先コード、出荷先コード、商品コード、状態フラグ、伝票No.、行No.順に表示を行います。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     状態フラグ：削除(9)以外 <BR>
 *     TC/DC区分：TC(1) <BR>
 *     作業区分：入荷(1) </DIR>
 * <BR>
 *   ＜パラメータ＞ <BR><DIR>
 *     * 必須の入力項目 <BR>
 *     + どちらかが必須の入力項目 <BR>
 * <BR>
 *     作業者コード	：WorkerCode* <BR>
 *     パスワード	：Password* <BR>
 *     荷主コード	：ConsignorCode* <BR>
 *     作業状態		：StatusFlag* <BR>
 *     入荷予定日	：PlanDate* <BR>
 *     仕入先コード	：SupplierCode <BR>
 *     開始伝票No.	：FromTicketNo <BR>
 *     終了伝票No.	：ToTicketNo <BR>
 *     商品コード	：ItemCode <BR>
 *     出荷先コード	：CustomerCode <BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード							：ConsignorCode <BR>
 *     荷主名称								：ConsignorName <BR>
 *     入荷予定日							：PlanDate <BR>
 *     仕入先コード							：SupplierCode <BR>
 *     仕入先名称							：SupplierName <BR>
 *     商品コード							：ItemCode <BR>
 *     商品名称								：ItemName <BR>
 *     ケース入数							：EnteringQty <BR>
 *     ボール入数							：BundleEnteringQty <BR>
 *     予定ケース数(作業可能数/ケース入数)	：PlanCaseQty <BR>
 *     予定ピース数(作業可能数%ケース入数)	：PlanPieceQty <BR>
 *     実績ケース数(実績数/ケース入数)		：ResultCaseQty <BR>
 *     実績ピース数(実績数%ケース入数)		：ResultPieceQty <BR>
 *     作業状態(リストセル)					：StatusFlagL <BR>
 *     賞味期限 (result_use_by_date)		：UseByDate <BR>
 *     実績報告区分							：ReportFlag <BR>
 *     実績報告区分名称						：ReportFlagName <BR>
 *     入荷伝票No.							：InstockTicketNo <BR>
 *     入荷行No.							：InstockLineNo <BR>
 *     出荷先コード							：CustomerCode <BR>
 *     出荷先名称							：CustomerName <BR>
 *     作業者コード							：WorkerCode <BR>
 *     作業者名								：WorkerName <BR>
 *     作業No.								：JobNo <BR>
 *     最終更新日時							：LastUpdateDate <BR></DIR>
 * </DIR>
 * <BR>
 * 3.修正登録、一括作業中解除ボタン押下処理（<CODE>startSCHgetParams()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、TC入荷作業メンテナンス処理を行います。 <BR>
 *   パラメータの押下ボタンの種類が0であれば修正登録処理を、1であれば一括作業中解除処理を行います。 <BR>
 *   作業中のデータの状態を変更した場合は、RFT作業情報を検索し、その作業を行っている端末の応答電文項目をNULLに更新します。<BR>
 *   また、作業中データ保存情報を検索し該当データが存在した場合はそのレコードを削除します。<BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   更新順は作業情報、予定情報、在庫情報の順とする。<BR>
 * <BR>
 *   ＜パラメータ＞ <BR><DIR>
 *     * 必須の入力項目 <BR>
 *     + どちらかが必須の入力項目 <BR>
 *     $ 検索に必要な項目 <BR>
 *     # 更新に必要な項目 <BR>
 * <BR>
 *     作業者コード			：WorkerCode*# <BR>
 *     パスワード			：Password* <BR>
 *     作業No.				：JobNo*# <BR>
 *     荷主コード			：ConsignorCode*$ <BR>
 *     作業状態				：StatusFlag*$ <BR>
 *     入荷予定日			：PlanDate*$ <BR>
 *     仕入先コード			：SupplierCode$ <BR>
 *     開始伝票No.			：FromTicketNo$ <BR>
 *     終了伝票No.			：ToTicketNo$ <BR>
 *     商品コード			：ItemCode$ <BR>
 *     出荷先コード			：CustomerCode$ <BR>
 *     ケース入数			：EnteringQty <BR>
 *     実績ケース数			：ResultCaseQty*# <BR>
 *     実績ピース数			：ResultPieceQty*# <BR>
 *     作業状態(リストセル)	：StatusFlagL*# <BR>
 *     賞味期限				：UseByDate# <BR>
 *     最終更新日時			：LastUpdateDate*# <BR>
 *     ボタン種別			：ButtonType* <BR>
 *     端末No.				：TerminalNumber*# <BR>
 *     ためうち行No			：RowNo* <BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード							：ConsignorCode <BR>
 *     荷主名称								：ConsignorName <BR>
 *     入荷予定日							：PlanDate <BR>
 *     仕入先コード							：SupplierCode <BR>
 *     仕入先名称							：SupplierName <BR>
 *     商品コード							：ItemCode <BR>
 *     商品名称								：ItemName <BR>
 *     ケース入数							：EnteringQty <BR>
 *     ボール入数							：BundleEnteringQty <BR>
 *     予定ケース数(作業可能数/ケース入数)	：PlanCaseQty <BR>
 *     予定ピース数(作業可能数%ケース入数)	：PlanPieceQty <BR>
 *     実績ケース数(実績数/ケース入数)	 	：ResultCaseQty <BR>
 *     実績ピース数(実績数%ケース入数)		：ResultPieceQty <BR>
 *     作業状態(リストセル)					：StatusFlagL <BR>
 *     賞味期限 (result_use_by_date)		：UseByDate <BR>
 *     実績報告区分							：ReportFlag <BR>
 *     実績報告区分名称						：ReportFlagName <BR>
 *     入荷伝票No.							：InstockTicketNo <BR>
 *     入荷行No.							：InstockLineNo <BR>
 *     出荷先コード							：CustomerCode <BR>
 *     出荷先名称							：CustomerName <BR>
 *     作業者コード							：WorkerCode <BR>
 *     作業者名								：WorkerName <BR>
 *     作業No.								：JobNo <BR>
 *     最終更新日時							：LastUpdateDate <BR></DIR>
 * <BR>
 *   ＜修正登録処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.作業No.の作業情報テーブルがデータベースに存在すること。 <BR>
 *     3.パラメータの最終更新日時と作業情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 *     4.ケース数に値が入っていた場合にはケース入数が1以上であること。 <BR>
 *     5.パラメータの入荷ケース数または入荷ピース数に1以上の入力があること。 <BR>
 *     6.日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜更新処理＞ <BR>
 *     -入荷予定情報テーブル(DNINSTOCKPLAN)の更新 <BR>
 *       1.状態フラグを未開始→開始済み、未開始→作業中に更新する場合 <BR><DIR>
 *         入荷予定情報の状態フラグが未開始の場合のみ、状態フラグを作業中に更新する。 <BR></DIR>
 *       2.状態フラグを開始済み→未開始、作業中→未開始に更新する場合 <BR><DIR>
 *         状態フラグを更新する。<BR><DIR>
 *           ・紐づく全ての作業情報が未開始の場合：未開始 <BR>
 *           ・紐づく作業情報の中で作業中がある場合：作業中 <BR>
 *           ・紐づく作業情報の中で未開始、完了がある場合：一部完了 <BR></DIR></DIR> <BR>
 *       3.状態フラグを開始済み→完了、作業中→完了、未開始→完了に更新する場合 <BR><DIR>
 *         状態フラグを更新する。<BR><DIR>
 *           ・紐づく全ての作業情報が完了の場合：完了<BR>
 *           ・紐づく作業情報の中で作業中がある場合：作業中<BR>
 *           ・紐づく作業情報の中で未開始、完了がある場合：一部完了<BR>
 *           </DIR> <BR>
 *         パラメータの入荷実績数を入荷予定情報の入荷実績数に代入する。 <BR>
 *         また、入荷欠品数を作業情報の入荷作業可能数からパラメータの入荷実績数を減算した値に更新する。 <BR></DIR>
 *       4.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         入荷実績数をパラメータの入荷実績数の値に更新する。 <BR>
 *         今回の欠品数を予定情報の欠品数に加算する。<BR></DIR>
 *       5.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -作業情報テーブル(DNWORKINFO)の更新 <BR>
 *       1.状態フラグを未開始→作業中、作業中→未開始に更新する場合 <BR><DIR>
 *         状態フラグをパラメータの状態フラグの値に更新する。 <BR></DIR>
 *       2.状態フラグをに作業中→完了、未開始→完了に更新する場合 <BR><DIR>
 *         パラメータの入荷実績数を作業情報の作業実績数に加算する。 <BR>
 *         また、作業欠品数を作業予定数からパラメータの入荷実績数を減算した値に更新する。 <BR>
 *         パラメータに賞味期限がセットされている場合は、その内容で賞味期限(result_use_by_date)を更新する。 <BR>
 *         ※在庫情報(DMSTOCK)の更新、実績情報(HOSTSEND)の登録を入荷完了処理にて行う。 <BR></DIR>
 *       3.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         パラメータの入荷実績数を作業情報の作業実績数に代入する。 <BR>
 *         また、作業欠品数を作業予定数からパラメータの入荷実績数を減算した値に更新する。 <BR>
 *         パラメータに賞味期限がセットされている場合は、その内容で賞味期限(result_use_by_date)を更新する。 <BR>
 *         ※実績数の更新範囲は作業可能数－保留数とする。 <BR></DIR>
 *       4.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -在庫情報テーブル(DMSTOCK)の更新（入荷完了処理の中で行う。） 注）完了→完了への変更時は在庫情報は変更しない。 <BR>
 *      詳細は<code>InstockReceiveCompleteOperator</code>を参照のこと。 <BR>
 * <BR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       状態フラグが「完了」→「完了」に更新する場合、元々ある実績を打ち消す実績送信情報を作成し画面で登録された実績を作成する。 <BR>
 *       「未開始」「作業中」→「完了」では、入荷完了処理中で実績を作成する。詳細は<code>InstockReceiveCompleteOperator</code>を参照のこと。<BR>
 * <BR>
 *     -作業者実績情報テーブル(DNWORKERRESULT)の更新登録 <BR>
 *       完了に更新する場合、パラメータの内容をもとに作業者実績情報を更新登録する。 <BR></DIR>
 * <BR>
 *   ＜一括作業中解除処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.作業No.の作業情報テーブルがデータベースに存在すること。 <BR>
 *     3.該当する作業情報テーブルの状態フラグが作業中であること。 <BR>
 *     4.最終更新日時と作業情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 *     5.日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜更新処理＞ 更新順は作業情報、予定情報の順とする。<BR>
 *     -作業情報テーブル(DNWORKINFO)の更新 <BR>
 *       受け取ったパラメータの内容をもとに作業情報を更新する。 <BR><DIR>
 *         状態フラグを未開始に更新する。 <BR>
 *         最終更新処理名を更新する。 <BR></DIR>
 * <BR>
 *     -入荷予定情報テーブル(DNINSTOCKPLAN)の更新 <BR>
 *       入荷予定情報の状態フラグが作業中の場合のみ、受け取ったパラメータの内容をもとに入荷予定情報を更新する。 <BR><DIR>
 *         次の通りに更新する。 <BR>
 *         ・紐づく全ての作業情報が未開始の場合：未開始 <BR>
 *         ・紐づく作業情報の中で作業中がある場合：作業中 <BR>
 *         ・紐づく作業情報の中で未開始、完了がある場合：一部完了</DIR>
 * <BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/26</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class TcInstockReceiveWorkMaintenanceSCH extends AbstractInstockReceiveSCH
{

	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	protected static final String wProcessName = "TcInstockWorkMaintenance";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public TcInstockReceiveWorkMaintenanceSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder workingFinder = new WorkingInformationReportFinder(conn);
		WorkingInformation[] wWorking = null;

		try
		{
			// 検索条件を設定する。
			// 状態フラグ：「削除」以外
			searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			// 作業区分：入荷(1)
			searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// TC/DC区分：TC(1)
			searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			// グルーピング条件に荷主コードをセット
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (workingFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 状態フラグ：「削除」以外
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				// 作業区分：入荷(1)
				searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
				// TC/DC区分：TC(1)
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
				// ソート順に登録日時を設定
				searchKey.setRegistDateOrder(1, false);
				
				searchKey.setConsignorNameCollect("");
				searchKey.setConsignorCodeCollect("");

				if (workingFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
					wWorking = (WorkingInformation[]) workingFinder.getEntities(1);
					wParam.setConsignorName(wWorking[0].getConsignorName());
					wParam.setConsignorCode(wWorking[0].getConsignorCode());
				}
			}
			workingFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずCollectionをクローズする
			workingFinder.close();
		}
		
		return wParam;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 検索結果
		InstockReceiveParameter[] wSParam = null;
		// 荷主名称
		String wConsignorName = "";

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey wNameKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;

		// パラメータの検索条件
		InstockReceiveParameter wParam = (InstockReceiveParameter)searchParam;

		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, wParam))
		{
			return null;
		}
		
		// 必須入力チェック
		if (StringUtil.isBlank(wParam.getWorkerCode())
		||  StringUtil.isBlank(wParam.getPassword())
		||  StringUtil.isBlank(wParam.getConsignorCode())
		||  StringUtil.isBlank(wParam.getPlanDate()))
		{
			throw (new ScheduleException("mandatory error!"));
		}
		
		// 検索条件を設定する。
		// 作業区分：入荷(1)
		wKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		wNameKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		// TC/DC区分：TC(1)
		wKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		wNameKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		wNameKey.setConsignorCode(wParam.getConsignorCode());
		// 作業状態
		if (wParam.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_ALL))
		{
			// パラメータの作業状態が「全て」であれば、未開始、開始済み、作業中、完了データを表示する。
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
		}
		else if (wParam.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
		{
			// 完了
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		}
		else if (wParam.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
		{
			// 未開始
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		}
		else if (wParam.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
		{
			// 開始済
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
		}
		else if (wParam.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
		{
			// 作業中
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			wNameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		}
		// 入荷予定日
		wKey.setPlanDate(wParam.getPlanDate());
		wNameKey.setPlanDate(wParam.getPlanDate());
		// 仕入先コード
		if (!StringUtil.isBlank(wParam.getSupplierCode()))
		{
			wKey.setSupplierCode(wParam.getSupplierCode());
			wNameKey.setSupplierCode(wParam.getSupplierCode());
		}
		// 開始伝票No.
		if (!StringUtil.isBlank(wParam.getFromTicketNo()))
		{
			wKey.setInstockTicketNo(wParam.getFromTicketNo(), ">=");
			wNameKey.setInstockTicketNo(wParam.getFromTicketNo(), ">=");
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(wParam.getToTicketNo()))
		{
			wKey.setInstockTicketNo(wParam.getToTicketNo(), "<=");
			wNameKey.setInstockTicketNo(wParam.getToTicketNo(), "<=");
		}
		// 商品コード
		if (!StringUtil.isBlank(wParam.getItemCode()))
		{
			wKey.setItemCode(wParam.getItemCode());
			wNameKey.setItemCode(wParam.getItemCode());
		}
		// 出荷先コード
		if (!StringUtil.isBlank(wParam.getCustomerCode()))
		{
			wKey.setCustomerCode(wParam.getCustomerCode());
			wNameKey.setCustomerCode(wParam.getCustomerCode());
		}

		// 仕入先コード、出荷先コード、商品コード、状態フラグ、伝票No.、行No.順でソート順をセット
		wKey.setSupplierCodeOrder(1, true);
		wKey.setCustomerCodeOrder(2, true);
		wKey.setItemCodeOrder(3, true);
		wKey.setStatusFlagOrder(4, true);
		wKey.setInstockTicketNoOrder(5, true);
		wKey.setInstockLineNoOrder(6, true);

		// 該当する作業情報のデータ件数を取得する。
		if (!canLowerDisplay(wObj.count(wKey)))
		{
			return returnNoDisplayParameter();
		}

		// 作業情報を取得する。
		wWorkinfo = (WorkingInformation[]) wObj.find(wKey);

		// 登録日時の最も新しい荷主名称を取得する。
		wNameKey.setRegistDateOrder(1, false);
		wNameKey.setConsignorNameCollect("");
		wWorkinfoName = (WorkingInformation[]) wObj.find(wNameKey);
		if (wWorkinfoName != null && wWorkinfoName.length > 0)
		{
			wConsignorName = wWorkinfoName[0].getConsignorName();
		}

		// Vectorインスタンスの生成
		Vector vec = new Vector();

		// 検索結果を返却パラメータにセットする。
		for (int i = 0; i < wWorkinfo.length; i++)
		{
			InstockReceiveParameter wTemp = new InstockReceiveParameter();
			// 荷主コード
			wTemp.setConsignorCode(wWorkinfo[i].getConsignorCode());
			// 荷主名称
			wTemp.setConsignorName(wConsignorName);
			// 入荷予定日
			wTemp.setPlanDate(wWorkinfo[i].getPlanDate());
			// 仕入先コード
			wTemp.setSupplierCode(wWorkinfo[i].getSupplierCode());
			// 仕入先名称
			wTemp.setSupplierName(wWorkinfo[i].getSupplierName1());
			// 商品コード
			wTemp.setItemCode(wWorkinfo[i].getItemCode());
			// 商品名称
			wTemp.setItemName(wWorkinfo[i].getItemName1());
			// ケース入数
			wTemp.setEnteringQty(wWorkinfo[i].getEnteringQty());
			// ボール入数
			wTemp.setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());
			// 予定ケース数
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 予定ピース数
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 実績ケース数
			wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 実績ピース数
			wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getResultQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 状態フラグ
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				// 未開始
				wTemp.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				wTemp.setStatusName(DisplayUtil.getWorkingStatusValue(InstockReceiveParameter.STATUS_FLAG_UNSTARTED));
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
			{
				// 開始済み
				wTemp.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_STARTED);
				wTemp.setStatusName(DisplayUtil.getWorkingStatusValue(InstockReceiveParameter.STATUS_FLAG_STARTED));
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				// 作業中
				wTemp.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_WORKING);
				wTemp.setStatusName(DisplayUtil.getWorkingStatusValue(InstockReceiveParameter.STATUS_FLAG_WORKING));
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				// 完了
				wTemp.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
				wTemp.setStatusName(DisplayUtil.getWorkingStatusValue(InstockReceiveParameter.STATUS_FLAG_COMPLETION));
			}
			// 賞味期限
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				wTemp.setUseByDate(wWorkinfo[i].getResultUseByDate());
			}
			else
			{
				wTemp.setUseByDate("");
			}
			// 実績報告フラグ
			if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
			{
				// 未報告
				wTemp.setReportFlag(InstockReceiveParameter.REPORT_FLAG_NOT_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_NOT_SENT));
			}
			else
			{
				// 報告済
				wTemp.setReportFlag(InstockReceiveParameter.REPORT_FLAG_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_SENT));
			}
			// 入荷伝票No.
			wTemp.setInstockTicketNo(wWorkinfo[i].getInstockTicketNo());
			// 入荷伝票行No.
			wTemp.setInstockLineNo(wWorkinfo[i].getInstockLineNo());
			// 出荷先コード
			wTemp.setCustomerCode(wWorkinfo[i].getCustomerCode());
			// 出荷先名称
			wTemp.setCustomerName(wWorkinfo[i].getCustomerName1());
			// 作業者コード
			wTemp.setWorkerCode(wWorkinfo[i].getWorkerCode());
			// 作業者名
			wTemp.setWorkerName(wWorkinfo[i].getWorkerName());
			// 作業No.
			wTemp.setJobNo(wWorkinfo[i].getJobNo());
			// 最終更新日時
			wTemp.setLastUpdateDate(wWorkinfo[i].getLastUpdateDate());
			vec.addElement(wTemp);
		}

		wSParam = new InstockReceiveParameter[vec.size()];
		vec.copyInto(wSParam);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		
		return wSParam;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、TC入荷作業メンテナンスのスケジュールを開始します。<BR>
	 * 作業中のデータを更新した場合で、正常に処理が終了した場合はRFT作業情報の応答電文項目をNULLに更新します。<BR>
	 * また、作業中データ保存情報を検索し、該当データが存在した場合はそのレコードを削除します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュールが正常終了した場合は、最新の入荷予定情報<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          失敗した場合はnullを返します。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			InstockReceiveParameter[] wParam = (InstockReceiveParameter[]) startParams;

			// 作業者コード、パスワードのチェックを行う。
			if (!checkWorker(conn, wParam[0]))
			{
				return null;
			}

			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return null;
			}
			
			// 対象データ全件の排他チェック・ロックを行う。
			if (!lockAll(conn, wParam))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return null;
			}

			// ためうちエリアの入力、排他チェックを行う。
			if (!checkList(conn, wParam))
			{
				return null;
			}
			
			// 作業者名を取得する。
			wParam[0].setWorkerName(getWorkerName(conn, wParam[0].getWorkerCode()));

			// 作業日(システム定義日付)
			String wSysDate = getWorkDate(conn);

			CrossDocOperator crossDocOperator = null;
			// WARENAVI_SYSTEMテーブルからクロスドックパッケージONかどうかチェック
			if(isCrossDockPack(conn))
			{
				 crossDocOperator = new CrossDocOperator(); 
			}

			// 作業数量
			int workQty = 0;
			// 今回作業分の作業数
			int inputQty = 0;
			
			// 作業中状態を解除したデータの端末リスト
			ArrayList terminalList = new ArrayList();

			for (int i = 0; i < wParam.length; i ++)
			{
				// 一括作業中解除ボタンが押下された場合
				if (!StringUtil.isBlank(wParam[0].getButtonType())
				&&  wParam[0].getButtonType().equals(InstockReceiveParameter.BUTTON_ALLWORKINGCLEAR))
				{
					// パラメータの状態フラグを未開始にする。
					wParam[i].setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				}

				// 作業情報を更新する。（更新前の作業情報を取得します）
				WorkingInformation wWorkinfo = (WorkingInformation) updateWorkinfo(conn, wParam[i], wParam[0]);

				if (wWorkinfo != null)
				{
					// 入荷予定情報を更新する。
					updateInStockPlan(conn, wParam[i], wWorkinfo.getPlanUkey(), wWorkinfo.getResultQty());

					// 「未開始」→「完了」「開始済み」→「完了」「作業中」→「完了」の場合、入荷完了処理クラスより
					// 在庫情報(DMSTOCK)の更新、実績情報(HOSTSEND)の登録、作業者実績情報の登録を行う。
					if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION)
					&&  (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART)
					||   wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
					||   wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)))
					{
						// 入荷完了処理クラスのインスタンス生成
						InstockRecieveCompleteOperator wCompObj = new InstockRecieveCompleteOperator();
						// 入荷完了処理メソッド
						wCompObj.complete(conn, wParam[i].getJobNo(), WorkingInformation.JOB_TYPE_INSTOCK,
										  wProcessName);

						if(crossDocOperator!=null)
						{
							int compelteQty = (wParam[i].getEnteringQty() * wParam[i].getResultCaseQty()) + wParam[i].getResultPieceQty();
							int shortageQty = wWorkinfo.getPlanEnableQty() - compelteQty;
							crossDocOperator.complete(conn,wWorkinfo.getPlanUkey(),compelteQty,shortageQty);
						}

						// 作業者実績更新のため、今回作業分の作業数を加算する。
						inputQty = wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + wParam[i].getResultPieceQty();
						workQty = addWorkQty(workQty, inputQty);
					}

					// 「完了」→「完了」の場合、在庫情報を更新し、実績の差分を実績情報に登録する。
					if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION)
					&&  wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
					{
						// 作業者実績更新のため、今回作業分の作業数を加算する。
						// 計算式は今回の結果 - 変更前の作業情報の実績数とし、マイナスの場合でも加算するため絶対値を取る。
						inputQty = Math.abs((wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + 
						                      wParam[i].getResultPieceQty()) - wWorkinfo.getResultQty());
						workQty = addWorkQty(workQty, inputQty);
							
						// 在庫情報、実績情報を更新登録する。	
						completionProcess(conn, wParam[i], wWorkinfo.getResultQty(), wParam[0], wWorkinfo, wSysDate);
					}

					String rftNo = wWorkinfo.getTerminalNo();
					// 変更前の作業状態が作業中かつ端末Noが空でない場合
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
							&& ! StringUtil.isBlank(rftNo))
					{
						// 作業中データファイルを削除するため、
						// RFT号機Noのリストを生成する。
						if (! terminalList.contains(rftNo))
						{
							terminalList.add(rftNo);
						}
					}
				}
			}
			if (workQty > 0)
			{
				// 作業者実績情報テーブル(DNWORKERRESULT)を登録する。
				updateWorkerResult(conn, wParam[0].getWorkerCode(), wParam[0].getWorkerName(), wSysDate, 
				                   wParam[0].getTerminalNumber(), WorkingInformation.JOB_TYPE_INSTOCK, workQty);
			}

			// 作業中データファイル削除処理で必要なため、PackageManagerを初期化する。
			PackageManager.initialize(conn);
			// 作業中データファイルを削除する。
			IdMessage.deleteWorkingDataFile(terminalList,
					WorkingInformation.JOB_TYPE_INSTOCK,
					WorkDetails.INSTOCK_CUSTOMER,
					wProcessName,
					conn);

			// 入荷予定情報を再検索する。
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) this.query(conn, wParam[0]);

			// 6001018 = 更新しました。
			wMessage = "6001018";

			// 最新の入荷予定情報を返す。
			return viewParam;
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリアの入力チェック、排他チェックを行います。
	 * @param conn 			データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 	画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 * @return 入力エラーが無ければtrueを、入力エラー発生した場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean checkList(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;

		try
		{
			// ためうちエリアの内容
			InstockReceiveParameter[] wParam = (InstockReceiveParameter[]) checkParam;
			// 入力された入荷数
			long wResultQty = 0;
			// 入荷予定数
			long wPlanQty = 0;

			for (int i = 0; i < wParam.length; i++)
			{
				// 検索条件を設定する。
				wKey.KeyClear();
				// 作業No.
				wKey.setJobNo(wParam[i].getJobNo());
				// 作業情報の検索結果を取得する。
				wWorkinfo = (WorkingInformation) wObj.findPrimaryForUpdate(wKey);

				if (wWorkinfo != null)
				{
					// 報告済みデータはメンテできません。
					if (wWorkinfo.getReportFlag().equals(WorkingInformation.REPORT_FLAG_SENT))
					{
						if ((!wParam[ i ].getStatusFlagL().equals( InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION)) 
						|| !wParam[ i ].getStatusFlagL().equals( InstockReceiveParameter.STATUS_FLAG_COMPLETION))
						{
							// 6023364=No.{0} 既にデータ報告済みのため、変更できません。
							wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
							return false;
						}
						else
						{
							// 入庫数を求める
							wResultQty = ( long )wParam[ i ].getResultCaseQty() * ( long )wParam[ i ].getEnteringQty() + ( long )wParam[ i ].getResultPieceQty();
							if (wResultQty != wWorkinfo.getResultQty())
							{
								// 6023364=No.{0} 既にデータ報告済みのため、変更できません。
								wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
								return false;
							}
						}
					}
					// 完了→完了以外の場合
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
					&&  !wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
					{
						// 6023328 = No.{0} 既に入荷完了処理を行っているため、状態を変更できません。
						wMessage = "6023328" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// ケース入数が0の場合、入荷ケース数入力チェック
					if (wParam[i].getEnteringQty() == 0 && wParam[i].getResultCaseQty() > 0)
					{
						// 6023329 = No.{0} ケース入数が0の場合は、入荷ケース数は入力できません。
						wMessage = "6023329" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// 入荷数を求める
					wResultQty = (long)wParam[i].getResultCaseQty() * (long)wParam[i].getEnteringQty() + (long)wParam[i].getResultPieceQty();
					// 予定数を求める
					wPlanQty = (long)wWorkinfo.getPlanEnableQty();

					if (wResultQty > 0 && wParam[0].getButtonType().equals(InstockReceiveParameter.BUTTON_MODIFYSUBMIT))
					{
						// 状態フラグ＝「完了」 かつ 入荷数＞入荷予定数の場合
						if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION) && wResultQty > wPlanQty)
						{
							// 6023155 = No.{0} 完了数が予定数を超えています。確認後再登録を行ってください。
							wMessage = "6023155" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// 状態フラグ＝「未開始」 かつ 入荷数＞０
						if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED) && wResultQty > 0)
						{
							// 6023330 = No.{0} 状態が未開始の場合は、入荷数は入力できません。
							wMessage = "6023330" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// 状態フラグ＝「開始済」 かつ 入荷数＞０
						if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_STARTED) && wResultQty > 0)
						{
							// 6023331 = No.{0} 状態が開始済の場合は、入荷数は入力できません。
							wMessage = "6023331" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// 状態フラグ＝「作業中」 かつ 入荷数＞０
						if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_WORKING) && wResultQty > 0)
						{
							// 6023332 = No.{0} 状態が作業中の場合は、入荷数は入力できません。
							wMessage = "6023332" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// オーバーフローチェック
						if (wResultQty > WmsParam.MAX_TOTAL_QTY)
						{
							// 6023272 = No.{0} {1}には{2}以下の値を入力してください。
							wMessage =
								"6023272" + wDelim + wParam[i].getRowNo() + wDelim + DisplayText.getText("LBL-W0087") + wDelim + MAX_TOTAL_QTY_DISP;
							return false;
						}

						// 完了→完了の場合、入荷数＜＝作業可能数－保留数チェック
						if (wParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION)
						&&  wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
						&&  wResultQty > wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty())
						{
							// 6023333 = No.{0} 保留作業があるため入荷数(ﾊﾞﾗ総量)は{1}以下の値を入力してください。
							wMessage = "6023333" + wDelim + wParam[i].getRowNo() + wDelim + (wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty());
							return false;
						}
					}
					// 排他チェック
					if (!wParam[i].getLastUpdateDate().equals(wWorkinfo.getLastUpdateDate()))
					{
						// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
						wMessage = "6023209" + wDelim + wParam[i].getRowNo();
						return false;
					}
				}
				else
				{
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + wParam[i].getRowNo();
					return false;
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
		return true;
	}

	/**
	 * 作業情報の更新処理を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param      画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 * @param inputParam 画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 *                   （配列の先頭の値が保持されています）
	 * @return 更新前の作業情報の内容を持つWorkingInformationクラスのインスタンス。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected WorkingInformation updateWorkinfo(Connection conn, InstockReceiveParameter param, 
												  InstockReceiveParameter inputParam) throws ReadWriteException, ScheduleException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationAlterKey wAKey = new WorkingInformationAlterKey();
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;
		
		// 実績総数
		int wTotalResultQty = 0;

		try
		{
			// 検索条件を設定する。
			// 作業No.
			wKey.setJobNo(param.getJobNo());
			// 作業情報の検索結果を取得する。
			wWorkinfo = (WorkingInformation) wObj.findPrimary(wKey);

			if (wWorkinfo != null)
			{
				// デッドロックを防止するために予定情報に紐づく他の作業情報をロックする。
				wKey.KeyClear();
				wKey.setPlanUkey(wWorkinfo.getPlanUkey());
				wKey.setJobNo(param.getJobNo(), "!=");
				wObj.findForUpdate(wKey);
				
				// 更新条件を設定する。
				// 作業No.
				wAKey.setJobNo(param.getJobNo());

				// 更新値を設定する。
				if (!wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					// 開始済みに変更
					if (param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_START);
						wAKey.updateWorkerCode("");
						wAKey.updateWorkerName("");
						wAKey.updateTerminalNo("");
					}
					// 未開始に変更
					else if (param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
						// 2005/11/11 Y.Okamura UPDATE START
						// RFTの作業集約を行い、一部完了させた場合に
						// 他端末エラーが発生する不具合の対応
						// 集約作業No.に自分が持つ作業No.をセットすることで対応する
						wAKey.updateCollectJobNo(wWorkinfo.getJobNo());
						wAKey.updateWorkerCode("");
						wAKey.updateWorkerName("");
						wAKey.updateTerminalNo("");
						// 2005/11/11 Y.Okamura UPDATE END
					}
					// 作業中に変更
					else if (param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
					{
						wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
					}
				}
				// 最終更新処理名
				wAKey.updateLastUpdatePname(wProcessName);

				// 状態フラグが完了の場合
				if (param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
				{
					// 作業実績数を求める
					wTotalResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// 作業実績数（パラメータの作業実績数を加算する）
					wAKey.updateResultQty(wTotalResultQty);
					// 欠品数（作業可能数から作業実績数と保留数を減算する）
					wAKey.updateShortageCnt(wWorkinfo.getPlanEnableQty() - wTotalResultQty - wWorkinfo.getPendingQty());
					// 賞味期限
					wAKey.updateResultUseByDate(param.getUseByDate());
					// 状態フラグ
					wAKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					// 端末No.
					wAKey.updateTerminalNo(inputParam.getTerminalNumber());
					// 作業者コード
					wAKey.updateWorkerCode(inputParam.getWorkerCode());
					// 作業者名
					wAKey.updateWorkerName(inputParam.getWorkerName());
					// 2005/11/11 Y.Okamura UPDATE START
					// RFTの作業集約を行い、一部完了させた場合に
					// 他端末エラーが発生する不具合の対応
					// 集約作業No.に自分が持つ作業No.をセットすることで対応する
					wAKey.updateCollectJobNo(wWorkinfo.getJobNo());
					// 2005/11/11 Y.Okamura UPDATE END
				}
				
				// 作業情報の更新
				wObj.modify(wAKey);
				
				return wWorkinfo;
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

		return null;
	}

	/**
	 * 入荷予定情報の更新処理を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param 画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 * @param key 更新対象となる入荷予定一意キー
	 * @param resultQty 作業情報の元実績数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void updateInStockPlan(Connection conn, InstockReceiveParameter param, String key, int resultQty) throws ReadWriteException
	{

		// 入荷予定情報ハンドラ類のインスタンス生成
		InstockPlanHandler wObj = new InstockPlanHandler(conn);
		InstockPlanAlterKey wAKey = new InstockPlanAlterKey();
		InstockPlanSearchKey wKey = new InstockPlanSearchKey();
		InstockPlan wInstock = null;
		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wWObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wWKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;
		// パラメータ入荷実績数
		int wParamResultQty = 0;
		// 入荷実績数
		int wResultQty = 0;

		try
		{
			// 検索条件を設定する。
			// 予定一意キー
			wWKey.setPlanUkey(key);
			// 作業情報の検索結果を取得する。
			wWorkinfo = (WorkingInformation[]) wWObj.find(wWKey);

			// 検索条件を設定する。
			// 入荷予定一意キー
			wKey.setInstockPlanUkey(key);
			// ロックした出荷予定情報の検索結果を取得する。
			wInstock = (InstockPlan) wObj.findPrimaryForUpdate(wKey);

			if (wInstock != null)
			{
				// 更新条件を設定する。
				// 入荷予定一意キー
				wAKey.setInstockPlanUkey(key);

				// 更新値を設定する。
				// 最終更新処理名
				wAKey.updateLastUpdatePname(wProcessName);

				// 状態フラグ
				// 「未開始」→「開始済み」または「作業中」の場合
				if (wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART)
				&&  (param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_STARTED)
				||	 param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_WORKING)))
				{
					wAKey.updateStatusFlag(InstockPlan.STATUS_FLAG_NOWWORKING);
				}

				// 「開始済み」または「作業中」→「未開始」の場合
				if (wInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING)
				&&  param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
				{
					// 未開始をセットする。
					String wStatusFlag = InstockPlan.STATUS_FLAG_UNSTART;
					
					if (wWorkinfo != null)
					{
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							// 作業中データが1件でもあった場合は作業中とする。
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = InstockPlan.STATUS_FLAG_NOWWORKING;
								break;
							}
							// 完了作業が1件でもあった場合は一部完了とする。
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
							{
								wStatusFlag = InstockPlan.STATUS_FLAG_COMPLETE_IN_PART;
							}
						}
					}
					wAKey.updateStatusFlag(wStatusFlag);
				}

				// 「未開始」「開始済み」「作業中」→「完了」または「完了」→「完了」の場合
				if (param.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
				{
					// 作業情報の実績数の総和
					int wTotalQty = 0;
					
					// 完了をセットする。
					String wStatusFlag = InstockPlan.STATUS_FLAG_COMPLETION;
					
					if (wWorkinfo != null)
					{
						for (int i = 0; i < wWorkinfo.length; i ++)
						{
							// 実績数の総和を求める
							wTotalQty += wWorkinfo[i].getResultQty();
						}
						
						for (int i = 0; i < wWorkinfo.length; i ++)
						{
							// 1件でも作業データのうち作業中データがある場合は予定情報の状態は作業中とする
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = InstockPlan.STATUS_FLAG_NOWWORKING;
								break;
							}
							// 作業情報が割れた時、未開始データが作業情報に存在した場合、入荷予定情報の状態フラグには一部完了をセットする。
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
							{
								wStatusFlag = InstockPlan.STATUS_FLAG_COMPLETE_IN_PART;
							}
						}
					}

					wAKey.updateStatusFlag(wStatusFlag);
					// パラメータ入荷実績数を求める
					wParamResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// 入荷実績数を求める（元実績数とパラメータの入荷実績数の差異を元実績数から減算する）
					wResultQty = wInstock.getResultQty() - (resultQty - wParamResultQty);
					// 入荷実績数
					wAKey.updateResultQty(wResultQty);
					if (wInstock.getPlanQty() - wTotalQty > 0)
					{
						// 欠品数（入荷予定数から入荷実績数を減算する）
						wAKey.updateShortageCnt(wInstock.getPlanQty() - wTotalQty);
					}
					else
					{
						wAKey.updateShortageCnt(0);
					}
				}

				// 入荷予定情報の更新
				wObj.modify(wAKey);
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	/**
	 * メンテナンス完了時の在庫情報の更新処理を行います。
	 * @param conn       データベースとのコネクションを保持するインスタンス。
	 * @param param      画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 * @param resultQty  元実績数
	 * @param inputParam 画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 *                   （配列の先頭の値が保持されています）
	 * @param oldWorkInfo 変更前の作業情報
	 * @param workDate    作業日
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected void completionProcess(Connection conn, InstockReceiveParameter param, 
		int resultQty, InstockReceiveParameter inputParam, WorkingInformation oldWorkinfo, String workDate) throws ReadWriteException, ScheduleException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;     // HostSend作成用
		// 在庫情報ハンドラ類のインスタンス生成
		StockHandler wSObj = new StockHandler(conn);
		StockAlterKey wAKey = new StockAlterKey();
		StockSearchKey wSKey = new StockSearchKey();
		Stock wStock = null;
		// 入荷実績数
		int wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();

		try
		{
			// 検索条件を設定する。
			// 作業No.
			wKey.setJobNo(param.getJobNo());
			// 作業情報の検索結果を取得する。
			wWorkinfo = (WorkingInformation) wObj.findPrimary(wKey);
			
			// 検索条件を設定する。
			// 在庫ID
			wSKey.setStockId(wWorkinfo.getStockId());
			// ロックした在庫情報の検索結果を取得する。
			wStock = (Stock) wSObj.findPrimaryForUpdate(wSKey);

			if (wStock != null)
			{
				// 更新条件を設定する。
				// 在庫ID
				wAKey.setStockId(wStock.getStockId());
				// 更新値を設定する。
				// 在庫数（元在庫数－(パラメータの入荷実績数－元入荷実績数)）
				if (wStock.getStockQty() - (resultQty - wResultQty) > 0)
				{
					wAKey.updateStockQty(wStock.getStockQty() - (resultQty - wResultQty));
				}
				else
				{
					// マイナス値になる場合は0をSET
					wAKey.updateStockQty(0);
				}

				// 最終更新処理名
				wAKey.updateLastUpdatePname(wProcessName);
				// 在庫情報を更新
				wSObj.modify(wAKey);
			}
			
			// もともとあった実績を打ち消す実績送信情報を登録する。
			negateHostSendData(conn, oldWorkinfo, inputParam, workDate);

			// 新たに実績送信情報を登録する
			createHostSendData(conn, wWorkinfo, inputParam, workDate);
			
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	/**
	 * メンテナンス完了時、もともとある実績送信情報を打ち消す情報を作成します。
	 * @param conn        データベースとのコネクションを保持するインスタンス。
	 * @param oldWorkInfo 変更前の作業情報
	 * @param inputParam  画面から入力された内容を持つInstockReceiveParameterクラスのインスタンス。
	 * @param workDate    作業日
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected void negateHostSendData(Connection conn, WorkingInformation oldWorkInfo, InstockReceiveParameter inputParam, String workDate)
		throws ReadWriteException, ScheduleException
	{
		// 実績送信情報ハンドラ類のインスタンス生成
		HostSendHandler wObj = new HostSendHandler(conn);
		HostSend wHostSend = null;
		
		try
		{
			// 実績数
			oldWorkInfo.setResultQty(-oldWorkInfo.getResultQty());
			// 欠品数
			oldWorkInfo.setShortageCnt(-oldWorkInfo.getShortageCnt());
			// 端末No.
			oldWorkInfo.setTerminalNo(inputParam.getTerminalNumber());
			// 作業者コード
			oldWorkInfo.setWorkerCode(inputParam.getWorkerCode());
			// 作業者名
			oldWorkInfo.setWorkerName(inputParam.getWorkerName());
			// 最終更新処理名
			oldWorkInfo.setLastUpdatePname(wProcessName);
			// 報告フラグ
			oldWorkInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			
			// 作業情報のエンティティから実績送信情報のエンティティを生成する。
			wHostSend = new HostSend(oldWorkInfo, workDate, wProcessName);
			// 実績送信情報を登録(変更の実績)
			wObj.create(wHostSend);
			
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	/**
	 * 完了から完了時の実績を作成します。
	 * @param conn        データベースとのコネクションを保持するインスタンス。
	 * @param workInfo 更新後の作業情報
	 * @param inputParam  画面から入力された内容を持つStorageSupportParameterクラスのインスタンス。
	 * @param workDate    作業日
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected void createHostSendData(Connection conn, WorkingInformation workInfo, InstockReceiveParameter inputParam, String workDate)
		throws ReadWriteException, ScheduleException
	{
		// 実績送信情報ハンドラ類のインスタンス生成
		HostSendHandler wObj = new HostSendHandler(conn);
		HostSend wHostSend = null;
		
		try
		{
			// 賞味期限
			workInfo.setResultUseByDate(inputParam.getUseByDate());
			// 端末No.
			workInfo.setTerminalNo(inputParam.getTerminalNumber());
			// 作業者コード
			workInfo.setWorkerCode(inputParam.getWorkerCode());
			// 作業者名
			workInfo.setWorkerName(inputParam.getWorkerName());
			// 報告フラグ
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			// 最終更新処理名
			workInfo.setLastUpdatePname(wProcessName);
			
			// 作業情報のエンティティから実績送信情報のエンティティを生成する。
			wHostSend = new HostSend(workInfo, workDate, wProcessName);
			// 実績送信情報を登録(変更の実績)
			wObj.create(wHostSend);
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	// Private methods -----------------------------------------------
}
//end of class

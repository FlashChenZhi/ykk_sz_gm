
package jp.co.daifuku.wms.sorting.schedule;

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
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdMessage;


/**
 * Designer : Y.Hirata <BR>
 * Maker : Y.Hirata  <BR>
 * <BR>
 * WEB仕分け作業メンテナンスを行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分け作業メンテナンス処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   作業情報（仕分け）に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     状態フラグが削除以外<BR>
 *     作業区分：仕分(04) </DIR></DIR>
 * <BR>
 * 2.表示ボタン押下処理（<CODE>query()</CODE>メソッド）<BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   ケース/ピース区分、TC/DC区分、出荷先コード、仕分け場所、状態順に表示を行います。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     作業区分：仕分(04) </DIR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR><DIR>
 *     作業者コード			：WorkerCode* <BR>
 *     パスワード			：Password* <BR>
 *     荷主コード			：ConsignorCode* <BR>
 *     作業状態				：StatusFlag* <BR>
 *     仕分予定日			：PlanDate* <BR>
 *     商品コード 			：ItemCode*<BR>
 *     ケース/ピース区分	：CasePieceFlag*<BR>
 *     TC/DC区分			：TcdcFlag*<BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード								：ConsignorCode <BR>
 *     荷主名称									：ConsignorName <BR>
 *     仕分予定日								：PlanDate <BR>
 *     商品コード								：ItemCode <BR>
 *     商品名称									：ItemName <BR>
 *     ケース/ピース区分							：CasePieceFlag<BR>
 *     ケース/ピース区分名称						：CasePieceName<BR>
 *     TC/DC区分								：TcdcFlag<BR>
 *     TC/DC区分名称							：TcdcName<BR>
 *     出荷先コード								：CustomerCode <BR>
 *     出荷先名称								：CustomerName <BR>
 *     ケース入数								：EnteringQty <BR>
 *     ボール入数								：BundleEnteringQty <BR>
 *     作業予定ケース数(作業可能数/ケース入数)	：PlanCaseQty <BR>
 *     作業予定ピース数(作業可能数%ケース入数)	：PlanPieceQty <BR>
 *     仕分ケース数(実績数/ケース入数)			：ResultCaseQty <BR>
 *     仕分ピース数(実績数%ケース入数)			：ResultPieceQty <BR>
 *     作業状態(リストセル)						：StatusFlagL <BR>
 *     仕分場所									：SortingLocation <BR>
 *     実績報告（未報告/報告済）<BR>
 *     作業者コード								：WorkerCode <BR>
 *     作業者名									：WorkerName <BR>
 *     作業No.									：JobNo <BR>
 *     最終更新日時								：LastUpdateDate <BR></DIR></DIR>
 * <BR>
 * 3.修正登録、一括作業中解除ボタン押下処理（<CODE>startSCHgetParams()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、仕分作業メンテナンス処理を行います。 <BR>
 *   パラメータの押下ボタンの種類が0であれば修正登録処理を、1であれば一括作業中解除処理を行います。 <BR>
 *   作業中のデータの状態を変更した場合はRFT作業情報を検索し作業を行っている端末の電文項目をNULLに更新します。<BR>
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
 * <BR><DIR>
 *     作業者コード			：WorkerCode*# <BR>
 *     パスワード			：Password* <BR>
 *     作業No.				：JobNo*# <BR>
 *     荷主コード			：ConsignorCode*$ <BR>
 *     作業状態				：StatusFlag*$ <BR>
 *     仕分予定日			：PlanDate*$ <BR>
 *     商品コード			：ItemCode*$ <BR>
 *     ケース/ピース区分		：CasePieceFlag*$<BR>
 *     TC/DC区分			：TcdcFlag*$<BR>
 *     出荷先コード			：CustomerCode$ <BR>
 *     ケース入数			：EnteringQty <BR>
 *     仕分ケース数			：ResultCaseQty*# <BR>
 *     仕分ピース数			：ResultPieceQty*# <BR>
 *     作業状態(リストセル)	：StatusFlagL*# <BR>
 *     仕分場所				：SortingLocation <BR>
 *     最終更新日時			：LastUpdateDate*# <BR>
 *     ボタン種別			：ButtonType* <BR>
 *     端末No.				：TerminalNumber*# <BR>
 *     ためうち行No			：RowNo* <BR></DIR>
 * <BR>
 *   ＜返却データ＞ <BR><DIR>
 *     荷主コード								：ConsignorCode <BR>
 *     荷主名称									：ConsignorName <BR>
 *     仕分予定日								：PlanDate <BR>
 *     商品コード								：ItemCode <BR>
 *     商品名称									：ItemName <BR>
 *     ケース/ピース区分							：CasePieceFlag<BR>
 *     ケース/ピース区分名称						：CasePieceName<BR>
 *     TC/DC区分								：TcdcFlag<BR>
 *     TC/DC区分名称							：TcdcName<BR>
 *     出荷先コード								：CustomerCode <BR>
 *     出荷先名称								：CustomerName <BR>
 *     ケース入数								：EnteringQty <BR>
 *     ボール入数								：BundleEnteringQty <BR>
 *     作業予定ケース数(作業可能数/ケース入数)	：PlanCaseQty <BR>
 *     作業予定ピース数(作業可能数%ケース入数)	：PlanPieceQty <BR>
 *     仕分ケース数(実績数/ケース入数)			：ResultCaseQty <BR>
 *     仕分ピース数(実績数%ケース入数)			：ResultPieceQty <BR>
 *     作業状態(リストセル)						：StatusFlagL <BR>
 *     仕分場所									：SortingLocation <BR>
 *     実績報告（未報告/報告済）<BR>
 *     作業者コード								：WorkerCode <BR>
 *     作業者名									：WorkerName <BR>
 *     作業No.									：JobNo <BR>
 *     最終更新日時								：LastUpdateDate <BR></DIR></DIR>
 * <BR>
 *   ＜修正登録処理＞ <BR>
 * <DIR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.作業No.の作業情報テーブルがデータベースに存在すること。 <BR>
 *     3.パラメータの最終更新日時と作業情報テーブルの最終更新日時の値が一致すること。（排他チェック） <BR>
 *     4.ケース数に値が入っていた場合にはケース入数が1以上であること。（状態が完了に変更された場合） <BR>
 *     5.入力数が予定数以下であること。（状態が完了の場合） <BR>
 *     6.日次更新処理中でないこと。 <BR></DIR>
 * 
 * <BR>
 *   ＜更新処理＞ <BR>
 *     -仕分予定情報テーブル(DNSORTINGPLAN)の更新 <BR>
 *       1.状態フラグを未開始→作業中に更新する場合 <BR><DIR>
 *         仕分予定情報の状態フラグが未開始の場合のみ、状態フラグを作業中に更新する。 <BR></DIR>
 *       2.状態フラグを作業中→未開始に更新する場合 <BR><DIR>
 *           ・紐づく全ての作業情報が未開始の場合：未開始 <BR>
 *           ・紐づく作業情報の中で作業中がある場合：作業中 <BR>
 *           ・紐づく作業情報の中で未開始、完了がある場合：一部完了
 *           </DIR> <BR> <BR>
 *       3.状態フラグを作業中→完了、未開始→完了に更新する場合 <BR><DIR>
 *         状態フラグを更新する。<BR><DIR>
 *           ・紐づく全ての作業情報が完了の場合：完了<BR>
 *           ・紐づく作業情報の中で作業中がある場合：作業中<BR>
 *           ・紐づく作業情報の中で未開始、完了がある場合：一部完了<BR>
 *           </DIR> <BR>
 *         パラメータの仕分実績数を仕分予定情報の仕分実績数に加算する。 <BR>
 *         また、仕分欠品数を今回の欠品数から元作業情報の欠品数を減算した値を加算した値に更新する。 <BR></DIR>
 *       4.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         仕分実績数をパラメータの仕分実績数の値に更新する。 <BR>
 *         今回の欠品数を予定情報の欠品数に加算する。<BR></DIR>
 *       5.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -作業情報テーブル(DNWORKINFO)の更新 <BR>
 *       1.状態フラグを未開始→作業中、作業中→未開始に更新する場合 <BR><DIR>
 *         状態フラグをパラメータの状態フラグの値に更新する。 <BR></DIR>
 *       2.状態フラグをに作業中→完了、未開始→完了に更新する場合 <BR><DIR>
 *         パラメータの仕分実績数を作業情報の作業実績数に加算する。 <BR>
 *         また、作業欠品数を作業予定数からパラメータの仕分実績数を減算した値に更新する。 <BR>
 *         ※在庫情報(DMSTOCK)の更新、実績情報(HOSTSEND)の登録を仕分完了処理にて行う。 <BR>
 *         詳細は<code>SortingCompleteOperator</code>を参照のこと。 <BR></DIR>
 *       3.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         パラメータの仕分実績数を作業情報の作業実績数に代入する。 <BR>
 *         また、作業欠品数を作業予定数からパラメータの仕分実績数を減算した値に更新する。 <BR></DIR>
 *       4.最終更新処理名を更新する。 <BR>
 * <BR>
 *     -在庫情報テーブル(DMSTOCK)の更新 <BR>
 *       1.状態フラグを未開始→完了、作業中→完了に更新する場合 <BR><DIR>
 *         仕分完了処理の中で行う。 <BR>
 *         詳細は<code>SortingCompleteOperator</code>を参照のこと。 <BR></DIR>
 *       2.状態フラグを完了→完了に更新する場合 <BR><DIR>
 *         在庫数をパラメータの仕分実績数と元作業情報の実績数の差分を減算した値に更新する。 <BR></DIR>
 *      
 * <BR>
 *     -実績送信情報テーブル(DNHOSTSEND)の登録 <BR>
 *       状態フラグが「完了」→「完了」に更新する場合、元々ある実績を打ち消す実績送信情報を作成し画面で登録された実績を作成する。
 *       「未開始」「作業中」→「完了」では、仕分完了処理中で実績を作成する。詳細は<code>SortingCompleteOperator</code>を参照のこと。<BR>
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
 *     -仕分予定情報テーブル(DNSORTINGPLAN)の更新 <BR>
 *       仕分予定情報の状態フラグが作業中の場合のみ、受け取ったパラメータの内容をもとに仕分予定情報を更新する。 <BR>
 *         次の通りに更新する。 <BR><DIR>
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
 * <TR><TD>2004/11/08</TD><TD>Y.Hirata</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingWorkMaintenanceSCH extends AbstractSortingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	protected static final String wProcessName = "SortingWorkMaintenance";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public SortingWorkMaintenanceSCH()
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
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 該当する荷主コードがセットされます。
		SortingParameter wParam = new SortingParameter();

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;

		// 検索条件を設定する。
		// 状態フラグ！＝「削除」
		// 作業区分　仕分け
		wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		wKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// グルーピング条件に荷主コードをセット
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect("DISTINCT");
		if (wObj.count(wKey) == 1)
		{
			// 該当する荷主コードの件数を取得する
			wWorkinfo = (WorkingInformation[])wObj.find(wKey);
	
			// 件数が1件の場合
			if (wWorkinfo != null && wWorkinfo.length == 1)
			{
				// 該当する荷主コードを取得し返却パラメータにセットする。
				wParam.setConsignorCode(wWorkinfo[0].getConsignorCode());
			}
		}
		return wParam;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>SortingParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 検索結果
		SortingParameter[] wSParam = null;
		// 荷主名称
		String wConsignorName = "";
		// 商品名称
		String wItemName = "";

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformationHandler wObj = new WorkingInformationHandler(conn);
		WorkingInformation[] wWorkinfo = null;
		WorkingInformation[] wWorkinfoName = null;

		// パラメータの検索条件
		SortingParameter wParam = (SortingParameter)searchParam;

		// 作業者コード、パスワードのチェックを行う。
		if (!checkWorker(conn, wParam))
		{
			return null;
		}
		// 必須入力チェック
		if (StringUtil.isBlank(wParam.getWorkerCode())
		||	StringUtil.isBlank(wParam.getPassword())
		||	StringUtil.isBlank(wParam.getConsignorCode())
		||	StringUtil.isBlank(wParam.getPlanDate())
		||	StringUtil.isBlank(wParam.getItemCode()))
		{
			throw (new ScheduleException("mandatory error!"));
		}
		
		// 検索条件を設定する。
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		// 作業状態 全て
		if (wParam.getStatusFlag().equals(SortingParameter.STATUS_FLAG_ALL))
		{
			// パラメータの作業状態が「全て」であれば、未開始、開始済み、作業中、完了データを表示する。
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "<=", "(", "", "OR");
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION, "=", "", ")", "AND");
		}
		// 完了
		else if(wParam.getStatusFlag().equals(SortingParameter.STATUS_FLAG_COMPLETION))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		}
		// 未開始
		else if(wParam.getStatusFlag().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		}
		// 開始
		else if(wParam.getStatusFlag().equals(SortingParameter.STATUS_FLAG_STARTED))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_START);
		}
		// 作業中
		else if(wParam.getStatusFlag().equals(SortingParameter.STATUS_FLAG_WORKING))
		{
			wKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		}
		
		// 仕分予定日
		wKey.setPlanDate(wParam.getPlanDate());

		// 商品コード
		wKey.setItemCode(wParam.getItemCode());
		
		// 作業区分:「仕分」
		wKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		
		// ケースピース区分
		if (wParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (wParam.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			wKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}

		// TC/DC区分
		if (wParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// クロス
			wKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
		}
		else if (wParam.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
		{
			// DC
			wKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
		}

		// ケースピース、TCDC、出荷先コード、仕分場所、状態順
		wKey.setCasePieceFlagOrder(1, true);
		wKey.setTcdcFlagOrder(2, false);
		wKey.setCustomerCodeOrder(3, true);
		wKey.setLocationNoOrder(4, true);
		wKey.setStatusFlagOrder(5, true);

		// 表示件数チェック
		if(!canLowerDisplay(wObj.count(wKey)))
		{
			return returnNoDisplayParameter();
		}
		
		// 作業情報を取得する。
		wWorkinfo = (WorkingInformation[])wObj.find(wKey);

		// 登録日時の最も新しい荷主名称、商品コードを取得する。
		wKey.setRegistDateOrder(1, false);
		wWorkinfoName = (WorkingInformation[])wObj.find(wKey);
		if (wWorkinfoName != null && wWorkinfoName.length > 0)
		{
			wConsignorName = wWorkinfoName[0].getConsignorName();
			wItemName = wWorkinfoName[0].getItemName1();
		}

		// Vectorインスタンスの生成
		Vector vec = new Vector();

		// 検索結果を返却パラメータにセットする。
		for (int i = 0; i < wWorkinfo.length; i++)
		{
			SortingParameter wTemp = new SortingParameter();
			// 荷主コード
			wTemp.setConsignorCode(wWorkinfo[i].getConsignorCode());
			// 荷主名称
			wTemp.setConsignorName(wConsignorName);
			// 仕分予定日
			wTemp.setPlanDate(wWorkinfo[i].getPlanDate());
			// 商品コード
			wTemp.setItemCode(wWorkinfo[i].getItemCode());
			// 商品名称
			wTemp.setItemName(wItemName);
			// ケースピース区分
			if (wWorkinfo[i].getCasePieceFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE))
			{
				// ケース
				wTemp.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_CASE);
				wTemp.setCasePieceName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_CASE));
			}
			else if (wWorkinfo[i].getCasePieceFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				// ピース
				wTemp.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_PIECE);
				wTemp.setCasePieceName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_PIECE));
			}
			else if (wWorkinfo[i].getCasePieceFlag().equals(WorkingInformation.CASEPIECE_FLAG_NOTHING))
			{
				// 指定なし
				wTemp.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_NOTHING);
				wTemp.setCasePieceName(DisplayUtil.getPieceCaseValue(WorkingInformation.CASEPIECE_FLAG_NOTHING));
			}
			// TC/DC区分
			if (wWorkinfo[i].getTcdcFlag().equals(WorkingInformation.TCDC_FLAG_CROSSTC))
			{
				// クロス
				wTemp.setTcdcFlag(SortingParameter.TCDC_FLAG_CROSSTC);
				wTemp.setTcdcName(DisplayUtil.getTcDcValue(WorkingInformation.TCDC_FLAG_CROSSTC));
			}
			else if (wWorkinfo[i].getTcdcFlag().equals(WorkingInformation.TCDC_FLAG_DC))
			{
				// ＤＣ
				wTemp.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
				wTemp.setTcdcName(DisplayUtil.getTcDcValue(WorkingInformation.TCDC_FLAG_DC));
			}
			// 出荷先コード
			wTemp.setCustomerCode(wWorkinfo[i].getCustomerCode());
			// 出荷先名称
			wTemp.setCustomerName(wWorkinfo[i].getCustomerName1());
			// ケース入数
			wTemp.setEnteringQty(wWorkinfo[i].getEnteringQty());
			// ボール入数
			wTemp.setBundleEnteringQty(wWorkinfo[i].getBundleEnteringQty());
			// 作業予定数
			wTemp.setTotalPlanQty(wWorkinfo[i].getPlanEnableQty());
			// 作業実績数
			wTemp.setTotalResultQty(wWorkinfo[i].getResultQty());
			// 仕分場所
			wTemp.setSortingLocation(wWorkinfo[i].getLocationNo());
			// 実績報告フラグ
			wTemp.setReportFlag(wWorkinfo[i].getReportFlag());
			// 実績報告名称
			if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_NOT_SENT))
			{
				// 未報告
				wTemp.setReportFlag(SortingParameter.REPORT_FLAG_NOT_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_NOT_SENT));
			}
			else if (wWorkinfo[i].getReportFlag().equals(WorkingInformation.REPORT_FLAG_SENT))
			{
				// 報告済
				wTemp.setReportFlag(SortingParameter.REPORT_FLAG_SENT);
				wTemp.setReportFlagName(DisplayUtil.getReportFlagValue(WorkingInformation.REPORT_FLAG_SENT));
			}
			
			// 作業予定ケース数に、作業可能数をケースで割った商をセットします。
			wTemp.setPlanCaseQty(DisplayUtil.getCaseQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 作業予定ピース数に、作業可能数をケースで割った余りをセットします。
			wTemp.setPlanPieceQty(DisplayUtil.getPieceQty(wWorkinfo[i].getPlanEnableQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 実績ケース数
			wTemp.setResultCaseQty(DisplayUtil.getCaseQty(wTemp.getTotalResultQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));
			// 実績ピース数
			wTemp.setResultPieceQty(DisplayUtil.getPieceQty(wTemp.getTotalResultQty(), wWorkinfo[i].getEnteringQty(), wWorkinfo[i].getCasePieceFlag()));

			// 状態フラグ
			if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				// 未開始
				wTemp.setStatusFlagL(SortingParameter.STATUS_FLAG_UNSTARTED);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START))
			{
				// 開始済み
				wTemp.setStatusFlagL(SortingParameter.STATUS_FLAG_STARTED);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				// 作業中
				wTemp.setStatusFlagL(SortingParameter.STATUS_FLAG_WORKING);
			}
			else if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
			{
				// 完了
				wTemp.setStatusFlagL(SortingParameter.STATUS_FLAG_COMPLETION);
			}
			

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

		wSParam = new SortingParameter[vec.size()];
		vec.copyInto(wSParam);

		// 6001013 = 表示しました。
		wMessage = "6001013";

		return wSParam;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕分作業メンテナンスのスケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * このメソッドはスケジュールの結果をもとに、画面表示内容を再表示する場合に使用します。
	 * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
	 * 作業中のデータの状態を変更した場合はRFT作業情報を検索し作業を行っている端末の電文項目をNULLに更新します。<BR>
	 * また、作業中データ保存情報を検索し該当データが存在した場合はそのレコードを削除します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *         SortingParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{

		SortingParameter[] wParam = (SortingParameter[])startParams;
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
		// 対象データ全件の排他チェックを行う。
		if (!lockAll(conn, startParams))
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
		WorkerHandler wObj = new WorkerHandler(conn);
		WorkerSearchKey wKey = new WorkerSearchKey();
		wKey.setWorkerCode(wParam[0].getWorkerCode());
		wKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
		Worker[] wWorker = (Worker[])wObj.find(wKey);
		String workerName = wWorker[0].getName();
		wParam[0].setWorkerName(workerName);

		// 作業日の取得
		String sysdate = getWorkDate(conn);

		CrossDocOperator crossDocOperator = null;
	
		if(isCrossDockPack(conn))
		{	
			 crossDocOperator = new CrossDocOperator(); 
		}

		int workqty = 0;
		int inputqty = 0;
		
		// 作業中状態を解除したデータの端末リスト
		ArrayList terminalList = new ArrayList();

		for (int i = 0; i < wParam.length; i++)
		{
			// 一括作業中解除ボタンが押下された場合
			if (!StringUtil.isBlank(wParam[0].getButtonType())
				&& wParam[0].getButtonType().equals(SortingParameter.BUTTON_ALLWORKINGCLEAR))
			{
				// パラメータの状態フラグを未開始にする。
				wParam[i].setStatusFlagL(SortingParameter.STATUS_FLAG_UNSTARTED);
			}

			// 作業情報を更新する。（更新前の作業情報を取得します）
			WorkingInformation wWorkinfo = (WorkingInformation)updateWorkinfo(conn, wParam[i], wParam[0]);

			if (wWorkinfo != null)
			{
				// 仕分完了処理クラスのインスタンス生成
				SortingCompleteOperator wCompObj = new SortingCompleteOperator();
				// 仕分予定情報を更新する。
				updateSortingPlan(conn, wParam[i], wWorkinfo);
				// 「未開始」→「完了」「開始済み」→「完了」「作業中」→「完了」の場合、完了処理クラスより
				// 在庫情報(DMSTOCK)の更新、実績情報(HOSTSEND)の登録を行う。
				if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION)
					&& (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART)
						|| wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
						|| wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)))
				{

					// 完了処理 
					wCompObj.complete(conn, wParam[i].getJobNo(), WorkingInformation.JOB_TYPE_SORTING, wProcessName);

					if(crossDocOperator!=null)
					{
						int compelteQty = (wParam[i].getEnteringQty() * wParam[i].getResultCaseQty()) + wParam[i].getResultPieceQty();
						int shortageQty = wWorkinfo.getPlanEnableQty() - compelteQty;
						crossDocOperator.complete(conn,wWorkinfo.getPlanUkey(),compelteQty,shortageQty);
					}	
					
					// 作業者実績更新のため、今回作業分の作業数を加算する。
					inputqty = wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + wParam[i].getResultPieceQty();
					workqty = addWorkQty(workqty, inputqty);
				}

				// 「完了」→「完了」の場合、もともとあった実績を打ち消す実績を作成する。
				if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION)
					&& wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					// 作業者実績更新のため、今回作業分の作業数を加算する。
					// 計算式は今回の結果 - 変更前の作業情報の実績数とし、マイナスの場合でも加算するため絶対値を取る。
					inputqty = Math.abs(
							(wParam[i].getEnteringQty() * wParam[i].getResultCaseQty() + wParam[i].getResultPieceQty())
								- wWorkinfo.getResultQty());
					workqty = addWorkQty(workqty, inputqty);
										 
					// 元々あった実績を打ち消す実績を作成し、今回の実績を作成します。また、在庫を更新します。
					completionProcess( conn, wParam[ i ], wWorkinfo.getResultQty(),wWorkinfo.getShortageCnt());
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
		if (workqty > 0)
		{

			// 作業者実績情報テーブル(DNWORKERRESULT)を登録する。
			updateWorkerResult(
				conn,
				wParam[0].getWorkerCode(),
				workerName,
				sysdate,
				wParam[0].getTerminalNumber(),
				WorkingInformation.JOB_TYPE_SORTING,
				workqty);
		}

		// 作業中データファイル削除処理で必要なため、PackageManagerを初期化する。
		PackageManager.initialize(conn);
		// 作業中データファイルを削除する。
		try
		{
			IdMessage.deleteWorkingDataFile(terminalList,
					WorkingInformation.JOB_TYPE_SORTING,
					"",
					wProcessName,
					conn);
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		// 仕分予定情報を再検索する。
		SortingParameter[] viewParam = (SortingParameter[])this.query(conn, wParam[0]);

		// 6001018 = 更新しました。
		wMessage = "6001018";

		// 最新の仕分予定情報を返す。
		return viewParam;
		
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリアの入力チェック、排他チェックを行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param 画面から入力された内容を持つSortingParameterクラスのインスタンス。
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
			SortingParameter[] wParam = (SortingParameter[])checkParam;
			// 入力された仕分数
			long wResultQty = 0;
			// 仕分予定数
			long wPlanQty = 0;

			for (int i = 0; i < wParam.length; i++)
			{
				// 検索条件を設定する。
				wKey.KeyClear();
				// 作業No.
				wKey.setJobNo(wParam[i].getJobNo());
				// 作業情報の検索結果を取得する。
				wWorkinfo = (WorkingInformation)wObj.findPrimaryForUpdate(wKey);

				if (wWorkinfo != null)
				{
					// 報告済みデータはメンテできません。
					if (wWorkinfo.getReportFlag().equals(WorkingInformation.REPORT_FLAG_SENT))
					{
						if ((!wParam[ i ].getStatusFlagL().equals( SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION)) 
						|| !wParam[ i ].getStatusFlagL().equals( SortingParameter.STATUS_FLAG_COMPLETION))
						{
							// 6023364=No.{0} 既にデータ報告済みのため、変更できません。
							wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
							return false;
						}
						else
						{
							// 仕分数を求める
							wResultQty = ( long )wParam[ i ].getResultCaseQty() * ( long )wParam[ i ].getEnteringQty() + ( long )wParam[ i ].getResultPieceQty();
							if (wResultQty != wWorkinfo.getResultQty())
							{
								// 6023364=No.{0} 既にデータ報告済みのため、変更できません。
								wMessage = "6023364" + wDelim + wParam[ i ].getRowNo();
								return false;
							}
						}
					}
					// 作業開始フラグが未開始の場合はメンテ不可
					if (wWorkinfo.getBeginningFlag().equals(WorkingInformation.BEGINNING_FLAG_NOT_STARTED))
					{
						// 6023377 = No.{0} 前工程の処理が完了していないため、処理できません。
						wMessage = "6023377" + wDelim + wParam[i].getRowNo();
						return false;
					}
					
					// 完了→完了以外の場合
					if (wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
						&& !wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION))
					{
						// 6023349 = No.{0} 既に仕分完了処理を行っているため、状態を変更できません。
						wMessage = "6023349" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// ケース入数が0の場合、仕分ケース数入力チェック
					if (wParam[i].getEnteringQty() == 0 && wParam[i].getResultCaseQty() > 0)
					{
						// 6023350=No.{0} ケース入数が0の場合は、仕分ケース数は入力できません。
						wMessage = "6023350" + wDelim + wParam[i].getRowNo();
						return false;
					}

					// 仕分数を求める
					wResultQty = (long)wParam[i].getResultCaseQty() * (long)wParam[i].getEnteringQty() + (long)wParam[i].getResultPieceQty();
					// 予定数を求める
					wPlanQty = (long)wWorkinfo.getPlanEnableQty();

					if (wResultQty > 0 && wParam[0].getButtonType().equals(SortingParameter.BUTTON_MODIFYSUBMIT))
					{
						// 状態フラグ＝「完了」 かつ 仕分数＞仕分予定数の場合
						if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION) && wResultQty > wPlanQty)
						{
							// 6023155 = No.{0} 完了数が予定数を超えています。確認後再登録を行ってください。
							wMessage = "6023155" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// 状態フラグ＝「未開始」 かつ 入庫数＞０
						if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_UNSTARTED) && wResultQty > 0)
						{
							// 6023351=No.{0} 状態が未開始の場合は、仕分数は入力できません。
							wMessage = "6023351" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// 状態フラグ＝「開始済」 かつ 入庫数＞０
						if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_STARTED) && wResultQty > 0)
						{
							// 6023352=No.{0} 状態が開始済の場合は、仕分数は入力できません。
							wMessage = "6023352" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// 状態フラグ＝「作業中」 かつ 入庫数＞０
						if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_WORKING) && wResultQty > 0)
						{
							// 6023353=No.{0} 状態が作業中の場合は、仕分数は入力できません。
							wMessage = "6023353" + wDelim + wParam[i].getRowNo();
							return false;
						}

						// オーバーフローチェック
						if (wResultQty > WmsParam.MAX_TOTAL_QTY)
						{
							// 6023272 = No.{0} {1}には{2}以下の値を入力してください。
							wMessage =
								"6023272" + wDelim + wParam[i].getRowNo() + wDelim + DisplayText.getText("LBL-W0198") + wDelim + MAX_TOTAL_QTY_DISP;
							return false;
						}

						//　完了→完了の場合、入庫数＜＝作業可能数－保留数チェック
						if (wParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION)
							&& wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION)
							&& wResultQty > wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty())
						{
							// 6023354=No.{0} 保留作業があるため仕分数(ﾊﾞﾗ総量)は{1}以下の値を入力してください。
							wMessage =
								"6023354" + wDelim + wParam[i].getRowNo() + wDelim + (wWorkinfo.getPlanEnableQty() - wWorkinfo.getPendingQty());
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
	 * メンテナンス完了時の在庫情報と実績送信情報、作業者実績情報の更新登録処理を行います。
	 * @param conn       データベースとのコネクションを保持するインスタンス。
	 * @param param      画面から入力された内容を持つSortingParameterクラスのインスタンス。
	 * @param resultQty  元実績数
	 * @param inputParam 画面から入力された内容を持つSortingParameterクラスのインスタンス。
	 *                   （配列の先頭の値が保持されています）
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected void completionProcess( Connection conn, SortingParameter param, int resultQty, int resultShortCnt) throws ReadWriteException, ScheduleException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wObj = new WorkingInformationHandler( conn );
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;     // HostSend作成用
		// 実績送信情報ハンドラ類のインスタンス生成
		HostSendHandler wHObj = new HostSendHandler( conn );
		HostSend wHostSend = null;
		// 在庫情報ハンドラ類のインスタンス生成
		StockHandler wSObj = new StockHandler( conn );
		StockAlterKey wAKey = new StockAlterKey();
		StockSearchKey wSKey = new StockSearchKey();
		Stock wStock = null;

		// 仕分実績数
		int wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();

		try
		{
			// 検索条件を設定する。
			// 作業No.
			wKey.setJobNo( param.getJobNo() );
			// 作業情報の検索結果を取得する。
			wWorkinfo = ( WorkingInformation )wObj.findPrimary( wKey );

			wWorkinfo.setResultQty(-resultQty);
			wWorkinfo.setShortageCnt(-resultShortCnt );

			// 検索条件を設定する。
			// 在庫ID
			wSKey.setStockId( wWorkinfo.getStockId() );
			// ロックした在庫情報の検索結果を取得する。
			wStock = ( Stock )wSObj.findPrimaryForUpdate( wSKey );

			if ( wStock != null )
			{
				// 更新条件を設定する。
				// 在庫ID
				wAKey.setStockId( wStock.getStockId() );
				// 更新値を設定する。
				// 在庫数（元在庫数－(パラメータの仕分実績数－元仕分実績数)）
				if (wStock.getStockQty() - (wResultQty - resultQty) > 0)
				{
					wAKey.updateStockQty( wStock.getStockQty() - (wResultQty - resultQty) );
				}
				else
				{
					// マイナス値になる場合は0をSET
					wAKey.updateStockQty( 0 );
				}

				// 最終更新処理名
				wAKey.updateLastUpdatePname( wProcessName );
				// 在庫情報を更新
				wSObj.modify( wAKey );
			}

			// WareNaviシステムの作業日を取得する。
			String sysDate = getWorkDate(conn);

			// 作業情報のエンティティから実績送信情報のエンティティを生成する。
			wHostSend = new HostSend( wWorkinfo, sysDate, wProcessName );

			// 実績送信情報を登録
			wHObj.create( wHostSend );

			wWorkinfo.setResultQty( wResultQty);

			wWorkinfo.setShortageCnt( wWorkinfo.getPlanEnableQty() - wWorkinfo.getResultQty() - wWorkinfo.getPendingQty());
			// 作業情報のエンティティから実績送信情報のエンティティを生成する。
			wHostSend = new HostSend( wWorkinfo, sysDate, wProcessName );
			// 実績送信情報を登録(変更の実績)
			wHObj.create( wHostSend );
			
		}
		catch ( DataExistsException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( InvalidDefineException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NotFoundException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NoPrimaryException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
	}
	/**
	 * 作業情報の更新処理を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param      画面から入力された内容を持つSortingParameterクラスのインスタンス。
	 * @param inputParam 画面から入力された内容を持つSortingParameterクラスのインスタンス。
	 *                   （配列の先頭の値が保持されています）
	 * @return 更新前の作業情報の内容を持つWorkingInformationクラスのインスタンス。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected WorkingInformation updateWorkinfo(Connection conn, SortingParameter param, SortingParameter inputParam)
		throws ReadWriteException, ScheduleException
	{

		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
		WorkingInformationAlterKey wiAltKey = new WorkingInformationAlterKey();
		WorkingInformationSearchKey wiSrchKey = new WorkingInformationSearchKey();
		WorkingInformation wWorkinfo = null;
		// 作業実績数
		int wResultQty = 0;

		try
		{
			// 検索条件を設定する。
			// 作業No.
			wiSrchKey.setJobNo(param.getJobNo());
			// 作業情報の検索結果を取得する。
			wWorkinfo = (WorkingInformation)wiHandle.findPrimary(wiSrchKey);

			if (wWorkinfo != null)
			{
				
				// デッドロックを防止するために予定情報に紐づく他の作業情報をロックする。
				// ここから
				wiSrchKey.KeyClear();
				wiSrchKey.setPlanUkey(wWorkinfo.getPlanUkey());
				wiSrchKey.setJobNo(param.getJobNo(), "!=");
				wiHandle.findForUpdate(wiSrchKey);
				// ここまで
				
				// 更新条件を設定する。
				// 作業No.
				wiAltKey.setJobNo(param.getJobNo());

				// 更新値を設定する。
				if (!wWorkinfo.getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					// 開始済みに変更
					if (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_STARTED))
					{
						wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_START);
						wiAltKey.updateWorkerCode("");
						wiAltKey.updateWorkerName("");
						wiAltKey.updateTerminalNo("");
					}
					// 未開始に変更
					else if (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
					{
						wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
						wiAltKey.updateWorkerCode("");
						wiAltKey.updateWorkerName("");
						wiAltKey.updateTerminalNo("");
					}
					// 作業中に変更
					else if (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_WORKING))
					{
						wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
					}
				}
				// 最終更新処理名
				wiAltKey.updateLastUpdatePname(wProcessName);

				// 状態フラグが完了の場合
				if (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					// ロケーションNo.を作業結果ロケーションNo.に賞味期限を作業結果賞味期限にSETします
					wiAltKey.updateResultLocationNo(wWorkinfo.getLocationNo());
					wiAltKey.updateResultUseByDate(wWorkinfo.getUseByDate());
					// 作業実績数を求める
					wResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// 作業実績数（パラメータの作業実績数を設定する）
					wiAltKey.updateResultQty(wResultQty);
					// 欠品数（作業可能数から作業実績数を減算する）
					wiAltKey.updateShortageCnt(wWorkinfo.getPlanEnableQty() - wResultQty);
					// 状態フラグ
					wiAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					// 端末No.
					wiAltKey.updateTerminalNo(inputParam.getTerminalNumber());
					// 作業者コード
					wiAltKey.updateWorkerCode(inputParam.getWorkerCode());
					// 作業者名
					wiAltKey.updateWorkerName(inputParam.getWorkerName());
				}

				// 作業情報の更新
				wiHandle.modify(wiAltKey);

				return wWorkinfo;
			}
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		return null;
	}

	/**
	 * 仕分予定情報の更新処理を行います。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param param 画面から入力された内容を持つSortingParameterクラスのインスタンス。
	 * @param oldWorkInfo 更新前の作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected void updateSortingPlan(Connection conn, SortingParameter param, WorkingInformation oldWorkInfo)
		throws ReadWriteException, ScheduleException
	{

		// 仕分予定情報ハンドラ類のインスタンス生成
		SortingPlanHandler sortingPlanHandle = new SortingPlanHandler(conn);
		SortingPlanAlterKey sortingPlanAltKey = new SortingPlanAlterKey();
		SortingPlanSearchKey sortingPlanSrchKey = new SortingPlanSearchKey();
		SortingPlan sortingPlan = null;
		// 作業情報ハンドラ類のインスタンス生成
		WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wiSrchKey = new WorkingInformationSearchKey();
		WorkingInformation[] wWorkinfo = null;
		// パラメータ仕分実績数
		int wParamResultQty = 0;
		// 仕分実績数
		int wResultQty = 0;
		try
		{
			// 検索条件を設定する。
			// 仕分予定一意キー
			sortingPlanSrchKey.setSortingPlanUkey(oldWorkInfo.getPlanUkey());
			// ロックした仕分予定情報の検索結果を取得する。
			sortingPlan = (SortingPlan)sortingPlanHandle.findPrimaryForUpdate(sortingPlanSrchKey);

			if (sortingPlan != null)
			{
				// 検索条件を設定する。
				// 予定一意キー
				wiSrchKey.setPlanUkey(oldWorkInfo.getPlanUkey());
				// 作業情報の検索結果を取得する。
				wWorkinfo = (WorkingInformation[])wiHandle.find(wiSrchKey);
				// 更新条件を設定する。
				// 仕分予定一意キー
				sortingPlanAltKey.setSortingPlanUkey(oldWorkInfo.getPlanUkey());

				// 更新値を設定する。
				// 最終更新処理名
				sortingPlanAltKey.updateLastUpdatePname(wProcessName);

				// 状態フラグ
				// 「未開始」→「作業中」の場合
				if (sortingPlan.getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART)
					&& (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_WORKING)))
				{
					sortingPlanAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				}

				// 「作業中」→「未開始」の場合
				if (sortingPlan.getStatusFlag().equals(SortingPlan.STATUS_FLAG_NOWWORKING)
					&& (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_UNSTARTED)))
				{
					String wStatusFlag = SortingPlan.STATUS_FLAG_UNSTART;
					if (wWorkinfo != null)
					{
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							// 1件でも作業データのうち作業中データがある場合は予定情報の状態は作業中とする
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = SortingPlan.STATUS_FLAG_NOWWORKING;
								break;
							}
							// 完了作業が1件でもあった場合は一部完了とする。
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
							{
								wStatusFlag = SortingPlan.STATUS_FLAG_COMPLETE_IN_PART;
							}
						}
					}
					sortingPlanAltKey.updateStatusFlag(wStatusFlag);
				}

				// 「未開始」「作業中」→「完了」または「完了」→「完了」の場合
				if (param.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION))
				{
					int totalShrt = 0;
					String wStatusFlag = SortingPlan.STATUS_FLAG_COMPLETION;
					if (wWorkinfo != null)
					{
						for (int i = 0; i < wWorkinfo.length; i++)
						{
							// 1件でも作業データのうち作業中データがある場合は予定情報の状態は作業中とする
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
							{
								wStatusFlag = SortingPlan.STATUS_FLAG_NOWWORKING;
								break;
							}
							// 作業情報が割れた時、未開始データが作業情報に存在した場合、予定情報の状態フラグには一部完了をセットする。
							if (wWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
							{
								wStatusFlag = SortingPlan.STATUS_FLAG_COMPLETE_IN_PART;
							}
							totalShrt = totalShrt + wWorkinfo[i].getShortageCnt();
						}
					}

					sortingPlanAltKey.updateStatusFlag(wStatusFlag);
					// パラメータ実績数を求める
					wParamResultQty = param.getEnteringQty() * param.getResultCaseQty() + param.getResultPieceQty();
					// 実績数を求める（元実績数とパラメータの実績数の差異を元実績数から減算する）
					wResultQty = sortingPlan.getResultQty() - (oldWorkInfo.getResultQty() - wParamResultQty);
					// 実績数
					sortingPlanAltKey.updateResultQty(wResultQty);
					// 欠品数（仕分予定数から仕分実績数を減算する
					// 紐づく作業情報の欠品数の総和
					sortingPlanAltKey.updateShortageCnt(totalShrt);
				}

				// 仕分予定情報の更新
				sortingPlanHandle.modify(sortingPlanAltKey);
			}
		}
		catch ( InvalidDefineException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
	}
	
	// Private methods -----------------------------------------------

}
//end of class

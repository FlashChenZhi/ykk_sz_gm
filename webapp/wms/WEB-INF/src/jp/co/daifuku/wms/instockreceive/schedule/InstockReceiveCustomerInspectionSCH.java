package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * 出荷先別入荷検品設定処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、入荷検品処理を行います。<BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   作業区分が入荷<BR> 
 *   状態フラグが未開始<BR> 
 *   TC/DCフラグがTCのデータ<BR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   表示順の値が伝票No.順の場合、伝票No.、行No.順に表示を行います。 <BR>
 *   表示順の値が商品コード順の場合、商品コード、伝票No.、行No.順に表示を行います。 <BR>
 *   検索するテーブルは作業情報テーブル(DNWORKINFO)。<BR>
 *   検索対象が1000件(WMSParamに定義されたMAX_NUMBER_OF_DISP)を超えた場合、表示は行いません。<BR>
 *   リストセルのヘッダに表示する荷主名称は登録日時の新しい値を取得します。<BR>
 *   <BR>
 *   [検索条件] <BR><DIR>
 *   <BR>
 *   状態フラグが未開始<BR> 
 *   作業区分が入荷検品<BR> 
 *   <BR></DIR>
 *   [入力データ]<BR><DIR>
 *      *必須入力<BR>
 *   <BR>
 *   作業者コード	:WorkerCode*# <BR>
 *   パスワード		:Password* <BR>
 *   荷主コード		:ConsignorCode* <BR>
 *   入荷予定日		:PlanDate* <BR>
 *   仕入先コード	:SupplierCode* <BR>
 *   出荷先コード	:CustomerCode* <BR>
 *   表示順			:DspOrder* <BR>
 *   入荷残数の初期入力を行うチェック:RemnantDisplayFlag<BR></DIR>
 *   <BR>
 *   [返却データ] <BR><DIR>
 *   <BR>
 *   荷主コード		:ConsignorCode <BR>
 *   荷主名称		:ConsignorName<BR>
 *   入荷予定日		:PlanDate <BR>
 *   仕入先コード	:SupplierCode <BR>
 *   仕入先名称		:SupplierName <BR>
 *   出荷先コード	:CustomerCode <BR>
 *   出荷先名称		:CustomerName <BR>
 *   伝票No.		:InstockTicketNo <BR>
 *   行No.			:InstockLineNo <BR>
 *   商品コード		:ItemCode <BR>
 *   商品名称		:ItemName <BR>
 *   入荷総数		:TotalPlanQty <BR>
 *   ケース入数		:EnteringQty <BR>
 *   ボール入数		:BundleEnteringQty <BR>
 *   作業予定ケース数:PlanCaseQty<BR>
 *   作業予定ピース数		:PlanPieceQty<BR>
 *   入荷ケース数	:ResultCaseQty<BR>
 *   入荷ピース数	:ResultPieceQty<BR>
 *   賞味期限		:UseByDate<BR>
 *   作業No.		:JobNo <BR>
 *   最新更新日時	:LastUpdateDate<BR>
 *   TC/DCフラグ	:TcdcFlag<BR>
 * </DIR>
 * <BR>
 * 3.登録ボタン押下処理(<CODE>startSCHgetParams()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、入荷検品処理を行います。 <BR>
 *   処理が正常に完了した場合はためうちエリア出力用のデータをデータベースから再取得して返します。 <BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はnullを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   <BR>
 *   [入力データ]<BR><DIR>
 * 	      * 必須の入力項目<BR>
 *	      + どちらかが必須の入力項目<BR>
 *	      $ 検索に必要な項目<BR>
 *	      # 更新に必要な項目<BR>
 *   <BR>
 *   作業者コード	:WorkerCode* <BR>
 *   パスワード		:Password* <BR>
 *   荷主コード		:ConsignorCode*$ <BR>
 *   入荷予定日		:PlanDate*$ <BR>
 *   仕入先コード	:SupplierCode*$ <BR>
 *   出荷先コード	:CustomerCode*$<BR>
 *   TC/DCフラグ	:TcdcFlag$<BR>
 *   表示順			:DspOrder*$ <BR>
 *   入荷残数の初期入力を行うチェック:RemnantDisplayFlag$<BR>
 *   入荷ケース数	:ResultCaseQty +#<BR>
 *   入荷ピース数	:ResultPieceQty +#<BR>
 *   予定入荷ケース数	:PlanCaseQty *#<BR>
 *   予定入荷ピース数	:PlanPieceQty *#<BR>
 *   ケース入数		:EnteringQty*# <BR>
 *   分納フラグ		:RemnantFlag#<BR>
 *   欠品フラグ		:ShortageFlag#<BR>
 *   賞味期限		:UseByDate# <BR>
 *   端末No			:TerminalNumber*#<BR>
 *   作業No.		:JobNo*# <BR>
 *   最終更新日時	:LastUpdateDate*# <BR>
 *   TC/DCフラグ	:TcdcFlagL*#<BR></DIR>
 *   <BR>
 *   [返却データ] <BR><DIR>
 *   <BR>
 *   荷主コード		:ConsignorCode<BR>
 *   荷主名称		:ConsignorName<BR>
 *   入荷予定日		:PlanDate<BR>
 *   仕入先コード	:SupplierCode<BR>
 *   仕入先名称		:SupplierName<BR>
 *   出荷先コード	:CustomerCode <BR>
 *   出荷先名称		:CustomerName <BR>
 *   伝票No.		:InstockTicketNo<BR>
 *   行No.			:InstockLineNo<BR>
 *   商品コード		:ItemCode <BR>
 *   商品名称		:ItemName <BR>
 *   入荷総数		:TotalPlanQty<BR>
 *   ケース入数		:EnteringQty<BR>
 *   ボール入数		:BundleEnteringQty<BR>
 *   作業予定ケース数:PlanCaseQty<BR>
 *   作業予定ピース数:PlanPieceQty<BR>
 *   入荷ケース数	:ResultCaseQty<BR>
 *   入荷ピース数	:ResultPieceQty<BR>
 *   賞味期限		:UseByDate<BR>
 *   作業No.		:JobNo<BR>
 *   最新更新日時	:LastUpdateDate<BR>
 *   TC/DCフラグ	:TcdcFlag<BR>
 *   </DIR>
 *   <BR>
 *   [処理条件チェック] <BR>
 *   <BR>
 *   1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR>
 *     <DIR>
 *     作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR> 
 *     </DIR>
 *   <BR>
 *   2.日次更新中で無いこと。 <BR>
 *   <BR>
 *   [更新処理] <BR>
 *   <BR>
 *   -入力チェック<BR>
 *   過剰入荷は許可しない<BR>
 *   <BR>
 *   -排他チェック<BR>
 *   作業No.、作業区分(入荷検品)、状態フラグ(未開始)にてデータを検索し、データをロックする。<BR>
 *   <BR>
 *   -データの更新<BR>
 *   <BR>
 *   デッドロックを防ぐため、作業情報、入荷予定情報、在庫情報の順番でテーブルの更新を行う。<BR>
 *   1設定分の入荷完了処理までが終わってから、作業者実績情報の登録または更新を行う。<BR>
 *   <BR>
 *   -作業情報テーブル(DNWORKINFO)の更新<BR> 
 *   <BR>
 *   1.作業情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)<BR>
 *     分納が発生した場合は、分納分の作業情報を新規に作成する。<BR>
 *   2.最終更新処理名を更新する。<BR>  
 *   3.受け取ったパラメータの内容をもとに作業実績数、作業欠品数、賞味期限を更新する。<BR> 
 *     <DIR>
 *     入荷数総数で作業情報の作業実績数を更新する。<BR> 
 *     欠品の場合、作業欠品数を作業可能数から入荷総数を減算した値に更新する。<BR>
 *     パラメータの賞味期限を作業情報の賞味期限(result_use_by_date)に更新する。<BR>
 *   4.受け取ったパラメータの内容をもとに作業者コード、作業者名、端末No.を更新する。<BR>
 *     作業者名の検索時、削除区分が使用可能かどうかは検索条件には含みません。<BR> 
 *     </DIR>
 *   <BR>
 *   -入荷予定情報テーブル(DNINSTOCKPLAN)の更新 <BR>
 *   <BR>
 *   1.入荷予定情報の状態フラグを更新する。(全数完了・欠品の場合、「完了」。分納の場合、「一部完了」)<BR>
 *   2.最終更新処理名を更新する。<BR> 
 *   3.受け取ったパラメータの内容をもとに出荷実績数、出荷欠品数を更新する。<BR> 
 *     <DIR>
 *     入荷総数を入荷予定情報の入荷実績数に加算する。<BR>
 *     欠品の場合、欠品数を作業情報の作業可能数から入荷総数を減算した値に更新する。<BR> 
 *     </DIR>
 *   <BR>
 *   -入荷完了処理-(<CODE>InstockCompleteOperator()</CODE>クラス)<BR>  
 *   <BR>
 *   作業No.、作業区分(入荷検品)、処理名、作業者コード、作業者名、端末No.をセットする。<BR>
 *   作業No.と作業区分(入荷検品)をもとに作業情報の検索を行い、在庫情報の更新を行う。<BR>
 *   また、作業に対する実績情報を<code>DNHOSTSEND</code>表に登録する。<BR>
 *   処理名、作業者コード、作業者名、端末No.は各テーブル登録用に使用する。<BR> 
 *   <BR>
 *   -作業者実績情報テーブル(DNWORKERRESULT)の登録または更新 <BR>
 *   <BR>
 *   作業者コード、作業日、端末No.、作業区分をもとに作業者実績情報の検索を行う。<BR>
 *   データが存在する場合、作業終了日時、作業者名称、作業数量、作業回数を更新する。<BR>
 *     <DIR>
 *     作業終了日時に、現在日時をセットする。<BR>
 *     作業者名称を更新する。<BR>
 *     作業数量 = 作業数量 + 作業情報の実績数(1設定分の作業情報の実績数をトータルする)<BR>
 *     作業回数 = 作業回数 + 1<BR>
 *     </DIR>
 *     <BR>
 *   データが存在しない場合、作業者実績情報を登録する。<BR>
 *     <DIR>
 *     作業日は、システム定義日付をセットする。<BR>
 *     作業開始日時、作業終了日時の両方に、現在日時をセットする。<BR>
 *     作業数量は、1設定分の作業情報の実績数をトータルしてセットする。<BR>
 *     作業回数は、1をセットする。<BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>M.Inoue</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockReceiveCustomerInspectionSCH extends AbstractInstockReceiveSCH
{
	// Class variables -----------------------------------------------
	/**
	 * クラス名(出荷先別入荷検品設定処理)
	 */
	protected String CLASS_INSTOCK = "InstockReceivePlanCustomerInspection";

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
	 * @param conn データベースとのコネクションオブジェクト
	 */
	public InstockReceiveCustomerInspectionSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
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
			// 作業区分(入荷検品)
			searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// 状態フラグ(未開始)
			searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			// TCのみ
			searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			// グルーピング条件に荷主コードをセット
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (workingFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// データの検索
				// 作業区分(入荷検品)
				searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
				// 状態フラグ(未開始)
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				// TCのみ
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
	 * @param conn データベースとのコネクションを保持するインスタンス。<BR>
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。<BR>
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 作業者コード、パスワードをチェック
		InstockReceiveParameter param = (InstockReceiveParameter)searchParam;
		if (!checkWorker(conn, param))
		{
			return null;
		}

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey namesearchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(入荷検品)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		namesearchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		// 状態フラグ(未開始)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		namesearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		// TCのみ
		searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		namesearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());
		// 出荷予定日
		searchKey.setPlanDate(param.getPlanDate());
		namesearchKey.setPlanDate(param.getPlanDate());
		// 仕入先コード
		searchKey.setSupplierCode(param.getSupplierCode());
		namesearchKey.setSupplierCode(param.getSupplierCode());
		// 出荷先コード
		searchKey.setCustomerCode(param.getCustomerCode());
		namesearchKey.setCustomerCode(param.getCustomerCode());

		// 表示順
		String dsporder = param.getDspOrder();
		// 伝票No.順
		if (dsporder.equals(InstockReceiveParameter.DISPLAY_ORDER_TICKET))
		{
			//伝票No.、行No.の昇順でソート
			searchKey.setInstockTicketNoOrder(1, true);
			searchKey.setInstockLineNoOrder(2, true);
		}
		// 商品コード順
		else if (dsporder.equals(InstockReceiveParameter.DISPLAY_ORDER_ITEM))
		{
			// 商品コード、伝票No.、行No.の昇順でソート
			searchKey.setItemCodeOrder(1, true);
			searchKey.setInstockTicketNoOrder(2, true);
			searchKey.setInstockLineNoOrder(3, true);
		}
		
		// タメうちエリアに対象データを表示できるかチェック
		if (!canLowerDisplay(workingHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		
		WorkingInformation[] resultEntity = (WorkingInformation[])workingHandler.find(searchKey);
		
		// 登録日時の新しい荷主名称と出荷先名称を取得します。
		namesearchKey.setRegistDateOrder(1, false);
		WorkingInformation[] working = (WorkingInformation[])workingHandler.find(namesearchKey);
		String consignorname = "";
		String customername = "";
		String suppliername = "";
		if(working != null && working.length != 0)
		{
			consignorname = working[0].getConsignorName();
			suppliername = working[0].getSupplierName1();
			customername = working[0].getCustomerName1();
		}	

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			InstockReceiveParameter dispData = new InstockReceiveParameter();
			// 荷主コード
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称)
			dispData.setConsignorName(consignorname);
			// 入荷予定日
			dispData.setPlanDate(resultEntity[i].getPlanDate());
			// 仕入先コード
			dispData.setSupplierCode(resultEntity[i].getSupplierCode());
			// 仕入先名称(登録日時の新しい出荷先名称)
			dispData.setSupplierName(suppliername);
			// 出荷先コード
			dispData.setCustomerCode(resultEntity[i].getCustomerCode());
			// 出荷先名称(登録日時の新しい出荷先名称)
			dispData.setCustomerName(customername);
			// 入荷伝票No.
			dispData.setInstockTicketNo(resultEntity[i].getInstockTicketNo());
			// 入荷伝票行No.
			dispData.setInstockLineNo(resultEntity[i].getInstockLineNo());
			// 商品コード
			dispData.setItemCode(resultEntity[i].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[i].getItemName1());
			// 入荷総数
			dispData.setTotalPlanQty(resultEntity[i].getPlanEnableQty());
			// ケース入数
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			// ボール入数
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			// 残ケース数
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
			// 残ピース数
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
			// 入荷ケース数、入荷ピース数
			// 入荷残数の初期入力を行うにチェックを入れた場合。
			if (param.getRemnantDisplayFlag())
			{
				dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty()));
				dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty()));
			}
			else
			{
				dispData.setResultCaseQty(0);
				dispData.setResultPieceQty(0);
			}
			// 賞味期限
			dispData.setUseByDate(resultEntity[i].getUseByDate());
			// TC/DC区分
			dispData.setTcdcFlag(resultEntity[i].getTcdcFlag());
			// 作業No.
			dispData.setJobNo(resultEntity[i].getJobNo());
			// 最終更新日時
			dispData.setLastUpdateDate(resultEntity[i].getLastUpdateDate());

			vec.addElement(dispData);
		}

		InstockReceiveParameter[] paramArray = new InstockReceiveParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

	/**
	 * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
	 * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
	 * このメソッドはスケジュールの結果をもとに、画面表示内容を再表示する場合に使用します。
	 * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @return スケジュールが正常終了した場合は、最新の入荷予定情報<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          失敗した場合はnullを返します。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			// 作業情報検索用
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		
			// 作業者コード、パスワードのチェック
			InstockReceiveParameter workparam = (InstockReceiveParameter)startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}
			// 日次更新中のチェック
			if (isDailyUpdate(conn))
			{
				return null;
			}
			
			// 対象データ全件の排他チェック・ロックを行う。
			if (!lockAll(conn, startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return null;
			}

			// ためうちの入力チェック
			if (!checkList(startParams))
			{
				return null;
			}
			// 作業区分(入荷検品)
			String jobtype = WorkingInformation.JOB_TYPE_INSTOCK;
			// 処理名
			String pname = CLASS_INSTOCK;
			// 作業者コード
			String workercode = workparam.getWorkerCode();
			// 作業者名称
			String workername = getWorkerName(conn, workercode);
			// 端末№
			String terminalno = workparam.getTerminalNumber();
			
			// クロスドックパッケージ有りの場合、クロスドック処理クラス生成	
			CrossDocOperator crossdoc = null;		
			if (isCrossDockPack(conn))
			{
				crossdoc = new CrossDocOperator();					
			}

			// 作業日(システム定義日付)
			String sysdate = getWorkDate(conn);
			
			// 作業数量
			int workqty = 0;
			
			// デッドロックを防ぐため、作業情報、入荷予定情報、在庫情報の順番で更新を行う。
			// 1設定分の出荷完了処理までが終わってから、作業者実績情報の登録または更新を行う。
			for (int i = 0; i < startParams.length; i++)
			{
				InstockReceiveParameter param = (InstockReceiveParameter)startParams[i];
	
				// 作業No.
				String jobno = param.getJobNo();
				// 最終更新日時
				Date lastupdate = param.getLastUpdateDate();
				// 行No.
				int rowno = param.getRowNo();
				
				String usebydate = param.getUseByDate();
				int enteringqty = param.getEnteringQty();
				int planqty = param.getPlanCaseQty() * enteringqty + param.getPlanPieceQty();
				int inputqty = param.getResultCaseQty() * enteringqty + param.getResultPieceQty();
				
				// 作業数量(1設定分の作業情報の実績数をトータルする)
				workqty = addWorkQty(workqty, inputqty);
				
				// 排他チェック
				if (!lock(conn, jobno, lastupdate))
				{
					// 6023209 = No.{0} このデータは、他の端末で更新されたため処理できません。
					wMessage = "6023209" + wDelim + rowno;
					return null;
				}
	
				// 作業情報テーブル(DNWORKINFO)の更新
				if (!updateWorkinginfo(conn, jobno, planqty, inputqty, usebydate, workercode, workername, terminalno, param.getRemnantFlag()))
				{
					return null;
				}
	
				// 入荷予定情報テーブル(DNINSTOCKPLAN)の更新
				if (!updateInstockPlan(conn, jobno, planqty, inputqty, param.getRemnantFlag(),
					param.getShortageFlag()))
				{
					return null;
				}
	
				// 入荷完了処理(DMSTOCK, DNHOSTSEND)
				InstockRecieveCompleteOperator instockOpe = new InstockRecieveCompleteOperator();
				if (!instockOpe.complete(conn, jobno, jobtype, pname))
				{
					return null;
				}
				
				// クロスドックパッケージ有りの運用であれば、クロスドック完了処理を実行する。		
				if (isCrossDockPack(conn))
				{	
					
					// 検索条件に作業No.セット
					WorkingInformationSearchKey worksearchKey = new WorkingInformationSearchKey();				
					worksearchKey.setJobNo(param.getJobNo());
					
					WorkingInformation resultEntity = (WorkingInformation) workingHandler.findPrimary(worksearchKey);					

					// 入荷一意キー				
					String planUkey = resultEntity.getPlanUkey();
					// ケース入数
					int enteringQty = param.getEnteringQty();
					// 完了(実績)数
					int completeQty = param.getResultCaseQty() * enteringQty + param.getResultPieceQty();
					// 欠品数
					int shortageQty = 0;
					// 欠品フラグにチェックが入っている場合は、欠品数を取得する。そうでなければ欠品数は0
					if (param.getShortageFlag() == true)
					{
						shortageQty = (param.getPlanCaseQty() * enteringQty + param.getPlanPieceQty())
						- (param.getResultCaseQty() * enteringQty + param.getResultPieceQty());
					}
					//	クロスドックの更新処理を行います。
					crossdoc.complete(conn, planUkey, completeQty, shortageQty);
				}					
			}
			
			// 作業者実績情報テーブル(DNWORKERRESULT)の登録または更新
			updateWorkerResult(conn, workercode, workername, sysdate, terminalno, jobtype, workqty);
			
			InstockReceiveParameter[] viewParam = null;
			// スケジュール成功
			viewParam = (InstockReceiveParameter[])query(conn, workparam);

			// 6001003=登録しました。
			wMessage = "6001003";

			return viewParam;
			
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/** 
	 * ためうち入力チェックを行います。<BR>
	 * @param startParams データベースとのコネクションオブジェクト
	 * @return 入力値が妥当な時はtrue。何か問題がある時は、wMessageにメッセージ番号等をセットし、falseを返します。
	 */
	protected boolean checkList(Parameter[] searchParams)
	{
		for (int i = 0; i < searchParams.length; i++)
		{
			InstockReceiveParameter param = (InstockReceiveParameter)searchParams[i];

			// パラメータからケース入数、入荷ケース数、入荷ピース数を取得する。
			int enteringqty = param.getEnteringQty();
			int planqty = param.getPlanCaseQty() * enteringqty + param.getPlanPieceQty();
			int caseqty = param.getResultCaseQty();
			int pieceqty = param.getResultPieceQty();
			long inputqty = (long)caseqty * (long)enteringqty + pieceqty;
			// 行No.
			int rowno = param.getRowNo();			

			// 入荷数のオーバーフローチェック
			if (inputqty > WmsParam.MAX_TOTAL_QTY)
			{
				// 6023272 = No.{0} {1}には{2}以下の値を入力してください。
				// LBL-W0087=入荷数（バラ総数）
				wMessage = "6023272" + wDelim + rowno + wDelim + DisplayText.getText("LBL-W0087") + wDelim + MAX_TOTAL_QTY_DISP;
				return false;
			}

			// ケース入数が0の場合、入荷ケース数入力チェック
			if (enteringqty == 0 && caseqty > 0)
			{
				// 6023329 = No.{0} ケース入数が0の場合は、入荷ケース数は入力できません。
				wMessage = "6023329" + wDelim + rowno;
				return false;
			}
			
			// 入荷数が０の場合
			if (inputqty == 0)
			{
				// 分納
				if (param.getRemnantFlag() == true)
				{
					// 6023343=No.{0} 分納する場合には、入荷ケース数または入荷ピース数に1以上の値を入力してください。
					wMessage = "6023343" + wDelim + rowno;
					return false;

				}
				// 欠品
				else if (param.getShortageFlag() == false)
				{
					// 6023117 = No.{0} 予定数と完了数に差異があります。確認後再登録を行って下さい。
					wMessage = "6023117" + wDelim + rowno;
					return false;	
				}
				
			}
			else if (inputqty != 0)
			{
				if (param.getRemnantFlag() == true && param.getShortageFlag() == true)
				{
					// 6023500=No.{0} 分割と欠品両方を選択することはできません。
					wMessage = "6023500" + wDelim + rowno;
					return false;
				}
				
				// 入力値が予定数より少ない
				if (inputqty < planqty)
				{
					// 欠品フラグ(なし)、分納フラグ（なし）
					if (param.getShortageFlag() == false && param.getRemnantFlag() == false)
					{
						// 6023117 = No.{0} 予定数と完了数に差異があります。確認後再登録を行って下さい。
						wMessage = "6023117" + wDelim + rowno;
						return false;
					}
				}
				// 入力値と予定数が等しい、欠品フラグ
				else if (inputqty == planqty)
				{
					// 欠品フラグあり
					if (param.getShortageFlag() == true)
					{
						// 6023119=No.{0} 完了数に作業予定数と同じ値を入力した場合、欠品は指定できません。
						wMessage = "6023119" + wDelim + rowno;
						return false;
					}
					// 分納フラグあり
					if (param.getRemnantFlag() == true)
					{
						// No.{0} 完了数に作業予定数と同じ値を入力した場合、分納は指定できません。
						wMessage = "6023341" + wDelim + rowno;
						return false;
					}
				}
				// 入力値が予定数より多い
				else if (inputqty > planqty)
				{
				
					// クロス、TCの場合は過剰入荷不可
					if (!param.getTcdcFlagL().equals(InstockReceiveParameter.TCDC_FLAG_DC))
					{
						// 6023155 = No.{0} 完了数が予定数を超えています。確認後再登録を行って下さい。
						wMessage = "6023155" + wDelim + rowno;
						return false;
					}

					// 欠品フラグあり
					if (param.getShortageFlag() == true)
					{
						// 6023366=No.{0} 作業予定数より完了数が多い行に、欠品が指定されています。
						wMessage = "6023366" + wDelim + rowno;
						return false;
					}
					// 分納フラグあり
					if (param.getRemnantFlag() == true)
					{
						// 6023367=No.{0} 作業予定数より完了数が多い行に、分納が指定されています。
						wMessage = "6023367" + wDelim + rowno;
						return false;
					}
				}
			}
		}

		return true;
	}
	
	/**
	 * 他の端末で既に変更されたかどうかの確認を行います。
	 * パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。
	 * 比較の結果、双方の最終更新日時が等しい場合は他の端末で変更されていないとし、
	 * 等しくない場合は他の端末で既に変更されていると見なします。
	 * 対象データは、作業情報テーブル(DNWORKINFO)とします。
	 * @param conn       データベースとのコネクションオブジェクト
	 * @param jobno      作業No.
	 * @param lastupdate 最終更新日時
	 * @return 他の端末ですでに変更されている場合はtrue、まだ変更されていない場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected boolean lock(Connection conn, String jobno, Date lastupdate) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データのロック
		// 作業No.
		searchKey.setJobNo(jobno);
				
		WorkingInformation[] working = (WorkingInformation[])workingHandler.findForUpdate(searchKey);

		// 作業No.がデータに存在しない(データが削除された場合)
		if (working == null || working.length == 0)
		{
			return false;
		}
		// 状態フラグが未開始以外(取込が行われた場合／メンテナンスで状態がかわる場合)
		if (!(working[0].getStatusFlag()).equals(WorkingInformation.STATUS_FLAG_UNSTART))
		{
			return false;
		}

		// パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。
		// 等しくない場合は他の端末で既に変更されていると見なします。
		if (!working[0].getLastUpdateDate().equals(lastupdate))
		{
			return false;
		}

		return true;
	}

	/**
	 * 作業情報テーブルの更新を行う。<BR>
	 * 分納が発生した場合は分納分の作業情報を新規に作成する。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param jobno      作業No.
	 * @param planqty    作業可能数
	 * @param inputqty   作業実績数
	 * @param usebydate  賞味期限
	 * @param workercode 作業者コード
	 * @param workername 作業者名
	 * @param terminalno 端末No.
	 * @param remnantFlag 分納フラグ
	 * @return 正常に更新できた場合はtrue、対象データが見つからない場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean updateWorkinginfo(Connection conn, String jobno, int planqty, int inputqty, String usebydate,
				 String workercode, String workername, String terminalno, boolean remnantFlag) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationAlterKey alterKey = new WorkingInformationAlterKey();
			WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
			WorkingInformation[] workInfo = null; 

			// 作業Noをセット
			alterKey.setJobNo(jobno);
			sKey.setJobNo(jobno);
			workInfo = (WorkingInformation[])workingHandler.find(sKey);
			if (workInfo == null || workInfo.length <= 0)
			{
				return false;
			}

			// 保留数の更新
			int pendqty = planqty - inputqty;
			if (pendqty < 0)
				pendqty = 0;
			//作業情報の状態フラグを完了に更新する。(完了、欠品どちらの場合も完了とする。)
			alterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			// 分納が発生した場合保留数をセットする。
			if (remnantFlag)
			{
				alterKey.updatePendingQty(pendqty);
			}
			
			// 最終更新処理名を更新する。  
			alterKey.updateLastUpdatePname(CLASS_INSTOCK);

			// 受け取ったパラメータの内容をもとに作業実績数、作業欠品数、賞味期限を更新する。 
			// パラメータの出荷数を作業情報の作業実績数に更新する。 
			alterKey.updateResultQty(inputqty);

			// 欠品の場合、作業欠品数を作業可能数からパラメータの出荷数を減算した値に更新する。
			if (!remnantFlag)
			{
				if (planqty != inputqty)
				{
					int shortage = planqty - inputqty;
					if (shortage < 0)
						shortage = 0;
					alterKey.updateShortageCnt(shortage);
				}
			}
			
			// パラメータの賞味期限を作業情報の賞味期限(result_use_by_date)に更新する。(完了、欠品どちらの場合もセットする。) 
			alterKey.updateResultUseByDate(usebydate);

			// 受け取ったパラメータの内容をもとに作業者コード、作業者名、端末No.を更新する。
			alterKey.updateWorkerCode(workercode);
			alterKey.updateWorkerName(workername);
			alterKey.updateTerminalNo(terminalno); 

			// データの更新
			workingHandler.modify(alterKey);
			
			// 分納分の作業情報を作成
			if (remnantFlag)
			{
				// 分納した場合の新規データ用作業情報エンティティ
				WorkingInformation newWorkinfo = null;
				// 分割する新しい方のレコードの値をセットする
				newWorkinfo = (WorkingInformation) workInfo[0].clone();
				newWorkinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				newWorkinfo.setPlanQty(planqty - inputqty);
				newWorkinfo.setPlanEnableQty(planqty - inputqty);
				newWorkinfo.setResultQty(0);
				newWorkinfo.setPendingQty(0);
				newWorkinfo.setTerminalNo("");
				newWorkinfo.setWorkerCode("");
				newWorkinfo.setWorkerName("");
				newWorkinfo.setResultUseByDate("");
				newWorkinfo.setRegistPname(CLASS_INSTOCK);
				newWorkinfo.setLastUpdatePname(CLASS_INSTOCK);
				// 新しい作業Noを割り当てる
				SequenceHandler sh = new SequenceHandler(conn);
				newWorkinfo.setJobNo(sh.nextJobNo());
				// 集約作業Noに作業Noをセットする
				newWorkinfo.setCollectJobNo(newWorkinfo.getJobNo());
				workingHandler.create(newWorkinfo);
			}
			return true;

		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}

	/**
	 * 入荷予定情報テーブルの更新を行う。<BR>
	 * @param conn データベースとのコネクションオブジェクト<BR>
	 * @param jobno     作業No.
	 * @param planqty   作業可能数
	 * @param inputqty  作業実績数
	 * @param remnantFlag 分納フラグ
	 * @param shortageFlag 欠品区分
	 * @return 正常に更新できた場合はtrue、対象データが見つからない場合はfalseを返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected boolean updateInstockPlan(Connection conn, String jobno, int planqty, int inputqty, 
		boolean remnantFlag, boolean shortageFlag) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			// データの検索
			// 作業No.
			workingsearchKey.setJobNo(jobno);
			WorkingInformation working = (WorkingInformation)workingHandler.findPrimary(workingsearchKey);
			
			if (working != null)
			{
				String planukey = working.getPlanUkey();
							
				InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
				InstockPlanSearchKey instocksearchKey = new InstockPlanSearchKey();
				InstockPlanAlterKey alterKey = new InstockPlanAlterKey();
	
				// データの検索
				// 予定一意キー
				instocksearchKey.setInstockPlanUkey(planukey);
				// 状態フラグ(未開始、一部完了)にてデータを検索すること。
				String[] statusflg = { InstockPlan.STATUS_FLAG_UNSTART, InstockPlan.STATUS_FLAG_COMPLETE_IN_PART };
				instocksearchKey.setStatusFlag(statusflg);
				
				InstockPlan instock = (InstockPlan)instockHandler.findPrimary(instocksearchKey);
				
				if (instock != null)
				{
					// 予定一意キーをセット
					alterKey.setInstockPlanUkey(planukey);
		
					// 1.予定情報の状態フラグを完了に更新する。
					// 分納発生時は一部完了
					if (remnantFlag)
					{
						alterKey.updateStatusFlag(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART);
					}
					else
					{
						alterKey.updateStatusFlag(InstockPlan.STATUS_FLAG_COMPLETION);
					}
					// 2.最終更新処理名を更新する。 
					alterKey.updateLastUpdatePname(CLASS_INSTOCK);
		
					// 3.受け取ったパラメータの内容をもとに出荷実績数、出荷欠品数を更新する。 
					// パラメータの出荷数を出荷予定情報の出荷実績数に加算する。
					int resultqty = instock.getResultQty() + inputqty;
					alterKey.updateResultQty(resultqty);
		
					// 欠品の場合、出荷欠品数を作業情報の出荷可能数からパラメータの出荷数を減算した値に更新する。 
					if (shortageFlag)
					{
						if (planqty != inputqty)
						{
							int shortage = planqty - inputqty;
							if (shortage < 0)
								shortage = 0;
							alterKey.updateShortageCnt(shortage);
						}
					}
		
					// データの更新
					instockHandler.modify(alterKey);
	
					return true;
				}
				else
				{
					// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
					RmiMsgLogClient.write("6006040" + wDelim + planukey, "ShippingInspectionSCH");
					// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
					throw (new ScheduleException());
				}
			}
			else
			{
				// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
				RmiMsgLogClient.write("6006040" + wDelim + jobno, "ShippingInspectionSCH");
				// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
				throw (new ScheduleException());
			}
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	// Private methods -----------------------------------------------
}
//end of class

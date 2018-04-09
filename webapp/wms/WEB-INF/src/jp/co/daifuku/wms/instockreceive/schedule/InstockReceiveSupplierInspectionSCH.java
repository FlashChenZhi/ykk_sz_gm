package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * 仕入先別入荷検品設定処理を行うためのクラスです。<BR>
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
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   クロス/DCの区分が全ての場合、ソート時は検索順をクロス、DCとする。<BR>
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
 *   TC/DC区分がTC以外<BR>
 *   <BR></DIR>
 *   [入力データ]<BR><DIR>
 *      *必須入力<BR>
 *   <BR>
 *   作業者コード	:WorkerCode* <BR>
 *   パスワード		:Password* <BR>
 *   荷主コード		:ConsignorCode* <BR>
 *   入荷予定日		:PlanDate* <BR>
 *   仕入先コード	:SupplierCode* <BR>
 *   商品コード		:ItemCode <BR>
 *   クロス/DC		:TcdcFlag*<BR>
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
 *   伝票No.		:InstockTicketNo <BR>
 *   行No.			:InstockLineNo <BR>
 *   商品コード		:ItemCode <BR>
 *   商品名称		:ItemName <BR>
 *   入荷総数		:TotalPlanQty <BR>
 *   ケース入数		:EnteringQty <BR>
 *   ボール入数		:BundleEnteringQty <BR>
 *   残ケース数		:PlanCaseQty<BR>
 *   残ピース数		:PlanPieceQty<BR>
 *   入荷ケース数	:ResultCaseQty<BR>
 *   入荷ピース数	:ResultPieceQty<BR>
 *   賞味期限		:UseByDate<BR>
 *   作業No.		:JobNo <BR>
 *   最新更新日時	:LastUpdateDate<BR>
 *   賞味期限		:UseByDate<BR>
 *   TC/DCフラグ	:TcdcFlag<BR>
 *   クロス/DC(名称):TcdcName<BR>
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
 *   作業者コード	:WorkerCode*# <BR>
 *   パスワード		:Password* <BR>
 *   荷主コード		:ConsignorCode*$ <BR>
 *   入荷予定日		:PlanDate*$ <BR>
 *   仕入先コード	:SupplierCode*$ <BR>
 *   商品コード		:ItemCode$ <BR>
 *   表示順			:DspOrder*$ <BR>
 *   入荷残数の初期入力を行うチェック:RemnantDisplayFlag$<BR>
 *   端末No			:TerminalNumber*#<BR>
 *   入荷ケース数	:ResultCaseQty +#<BR>
 *   入荷ピース数	:ResultPieceQty +#<BR>
 *   予定入荷ケース数	:PlanCaseQty *#<BR>
 *   予定入荷ピース数	:PlanPieceQty *#<BR>
 *   ケース入数		:EnteringQty*# <BR>
 *   分納フラグ		:RemnantFlag#<BR>
 *   欠品フラグ		:ShortageFlag#<BR>
 *   賞味期限		:UseByDate# <BR>
 *   作業No.		:JobNo*# <BR>
 *   最終更新日時	:LastUpdateDate*# <BR>
 *   TC/DCフラグ	:TcdcFlag*#<BR></DIR>
 *   <BR>
 *   [返却データ] <BR><DIR>
 *   <BR>
 *   荷主コード		:ConsignorCode <BR>
 *   荷主名称		:ConsignorName<BR>
 *   入荷予定日		:PlanDate <BR>
 *   仕入先コード	:SupplierCode <BR>
 *   仕入先名称		:SupplierName <BR>
 *   伝票No.		:InstockTicketNo <BR>
 *   行No.			:InstockLineNo <BR>
 *   商品コード		:ItemCode <BR>
 *   商品名称		:ItemName <BR>
 *   入荷総数		:TotalPlanQty <BR>
 *   ケース入数		:EnteringQty <BR>
 *   ボール入数		:BundleEnteringQty <BR>
 *   残ケース数		:PlanCaseQty<BR>
 *   残ピース数		:PlanPieceQty<BR>
 *   入荷ケース数	:ResultCaseQty<BR>
 *   入荷ピース数	:ResultPieceQty<BR>
 *   賞味期限		:UseByDate<BR>
 *   作業No.		:JobNo <BR>
 *   最新更新日時	:LastUpdateDate<BR>
 *   賞味期限		:UseByDate<BR>
 *   TC/DCフラグ	:TcdcFlag<BR>
 *   クロス/DC(名称):TcdcName<BR>
 * </DIR><BR>
 * 
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
 *   -入力チェック<BR>
 *   過剰入荷は許可しない（DCを除く）<BR>
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
public class InstockReceiveSupplierInspectionSCH extends InstockReceiveCustomerInspectionSCH
{
	// Class variables -----------------------------------------------
	/**
	 * クラス名(仕入先別入荷検品設定処理)
	 */
	protected String CLASS_INSTOCK = "InstockReceiveSupplierInspection";

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
	public InstockReceiveSupplierInspectionSCH()
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
			// TC以外
			searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC,"!=");
		
			// グルーピング条件に荷主コードをセット
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (workingFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 作業区分(入荷検品)
				searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
				// 状態フラグ(未開始)
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				// TC以外
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC,"!=");
		
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
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());
		// 入荷予定日
		searchKey.setPlanDate(param.getPlanDate());
		namesearchKey.setPlanDate(param.getPlanDate());
		// 仕入先コード
		searchKey.setSupplierCode(param.getSupplierCode());
		namesearchKey.setSupplierCode(param.getSupplierCode());
		// 商品コード
		String itemcode = param.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}
		// クロス/DC
		if (StringUtil.isBlank(param.getTcdcFlag()) || param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_ALL))
		{
			// TC以外
			searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC,"!=");
			namesearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC, "!=");
			searchKey.setTcdcFlagOrder(0, false);
		}
		else
		{
			if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
			{
				// CROSS
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
				namesearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
			}
			else if (param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_DC))
			{
				// DC
				searchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
				namesearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			}
		}
		
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
		
		// 表示件数でためうちエリアに表示できるかチェックします
		if (!canLowerDisplay(workingHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		
		WorkingInformation[] resultEntity = (WorkingInformation[])workingHandler.find(searchKey);
		
		// 登録日時の新しい荷主名称と仕入先名称を取得します。
		namesearchKey.setRegistDateOrder(1, false);
		WorkingInformation[] working = (WorkingInformation[])workingHandler.find(namesearchKey);
		String consignorname = "";
		String suppliername = "";
		if(working != null && working.length != 0)
		{
			consignorname = working[0].getConsignorName();
			suppliername = working[0].getSupplierName1();
		}	

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			InstockReceiveParameter dispData = new InstockReceiveParameter();
			// 荷主コード
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称)
			dispData.setConsignorName(consignorname);
			// 予定日
			dispData.setPlanDate(resultEntity[i].getPlanDate());
			// 仕入先コード
			dispData.setSupplierCode(resultEntity[i].getSupplierCode());
			// 仕入先名称(登録日時の新しい仕入先名称)
			dispData.setSupplierName(suppliername);
			// 商品コード
			dispData.setItemCode(resultEntity[i].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[i].getItemName1());
			// 伝票No.
			dispData.setInstockTicketNo(resultEntity[i].getInstockTicketNo());
			// 伝票行No.
			dispData.setInstockLineNo(resultEntity[i].getInstockLineNo());
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
				dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
				dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanEnableQty(), resultEntity[i].getEnteringQty(), resultEntity[i].getCasePieceFlag()));
			}
			else
			{
				dispData.setResultCaseQty(0);
				dispData.setResultPieceQty(0);
			}
			// 賞味期限
			dispData.setUseByDate(resultEntity[i].getUseByDate());
			// クロス/DC
			dispData.setTcdcName(DisplayUtil.getTcDcValue(resultEntity[i].getTcdcFlag()));
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
	 * スケジュールを開始します。
	 * 詳細は継承元を参照してください。
	 * クラス名を本メソッドでセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams データベースとのコネクションオブジェクト
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュールが正常終了した場合は、最新の入荷予定情報<CODE>InstockReceiveParameter</CODE>インスタンスの配列。<BR>
	 *          失敗した場合はnullを返します。<BR>
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		super.CLASS_INSTOCK = this.CLASS_INSTOCK;
		return super.startSCHgetParams(conn, startParams);
		
	}

	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	// Private methods -----------------------------------------------
}
//end of class

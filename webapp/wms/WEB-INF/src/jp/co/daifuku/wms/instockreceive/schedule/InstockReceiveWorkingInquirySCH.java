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
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * 入荷状況照会処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、入荷状況照会処理を行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   1-1 入荷予定情報の入荷予定日を返します。<BR> 
 * 	 <BR>
 *   [検索条件] <BR>
 *   <BR>
 *   状態フラグが削除以外<BR>
 *   入荷予定日の昇順でパラメータ配列にセットする。<BR> 
 *   <BR> 
 *   [返却データ] <BR>
 *   <BR>
 *   入荷予定日:PlanDateP* <BR>
 *   <BR>
 *   1-2 入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *	     該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   状態フラグが削除以外<BR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR>
 *   荷主コード:ConsignorCode <BR>
 *   <BR>
 * </DIR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、入荷状況照会用のデータを入荷予定情報から取得します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   ＴＣ入荷とＤＣ入荷とクロスＴＣ入荷の仕入先数、伝票枚数、アイテム数、ケース数、ピース数、荷主数を作業状態別に返します。<BR>
 *   また、これらの値の合計と進捗率を返します。<BR>
 *   検索するテーブルは入荷予定情報テーブル(DNINSTOCKPLAN)です。<BR>
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   状態フラグが削除以外<BR>
 *   荷主コードが空白の場合、全荷主コードが対象となります。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   荷主コード			:ConsignorCode <BR>
 *   入荷予定日			:PlanDate* <BR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR> 
 * 	 未処理_仕入先数	:UnstartSupplierCount<BR>
 *   未処理_伝票枚数	:UnstartTicketCount<BR>
 *   未処理_アイテム数	:UnstartItemCount<BR>
 *   未処理_ケース数	:UnstartCaseCount<BR>
 *   未処理_ピース数	:UnstartPieceCount<BR>
 *   未処理_荷主数		:UnstartConsignorCount<BR>
 *   <BR>
 * 	 作業中_仕入先数	:NowSupplierCount<BR>
 *   作業中_伝票枚数	:NowTicketCount<BR>
 *   作業中_アイテム数	:NowItemCount<BR>
 *   作業中_ケース数	:NowCaseCount<BR>
 *   作業中_ピース数	:NowPieceCount<BR>
 *   作業中_荷主数		:NowConsignorCount<BR>
 *   <BR> 
 *   処理済_仕入先数	:FinishSupplierCount<BR>
 *   処理済_伝票枚数	:FinishTicketCount<BR>
 *   処理済_アイテム数	:FinishItemCount<BR>
 *   処理済_ケース数	:FinishCaseCount<BR>
 *   処理済_ピース数	:FinishPieceCount<BR>
 *   処理済_荷主数		:FinishConsignorCount<BR>
 *   <BR>
 *   合計_仕入先数		:TotalSupplierCount<BR>
 *   合計_伝票枚数		:TotalTicketCount<BR>
 *   合計_アイテム数	:TotalItemCount<BR>
 *   合計_ケース数		:TotalCaseCount<BR>
 *   合計_ピース数		:TotalPieceCount<BR>
 *   合計_荷主数		:TotalConsignorCount<BR>
 *   <BR>
 *   進捗率_仕入先数	:SupplierProgressiveRate<BR>
 *   進捗率_伝票枚数	:TicketProgressiveRate<BR>
 *   進捗率_アイテム数	:ItemProgressiveRate<BR>
 *   進捗率_ケース数	:CaseProgressiveRate<BR>
 *   進捗率_ピース数	:PieceProgressiveRate<BR>
 *   進捗率_荷主数		:ConsignorProgressiveRate<BR>
 *   <BR>
 *   [検索/計算処理]<BR>
 *   <BR>
 *   1-1 荷主数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getConsignor()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、
 *     入荷予定情報から荷主コード・作業状態で集約し検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getStatus()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードが同じデータの状態フラグを、
 *     下表のパターンにあてはめて、その荷主コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     該当ステータスの荷主数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-2 仕入先数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getSupplier()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、
 *     入荷予定情報から荷主コード・仕入先コード・作業状態で集約し検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getStatus()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと仕入先コードが同じデータの状態フラグを、
 *     下表のパターンにあてはめて、その仕入先コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     該当ステータス仕入先数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-3 伝票枚数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getTicket()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、
 *     入荷予定情報から荷主コード・仕入先コード・伝票No.・作業状態で集約し検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getStatus()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと仕入先コードと伝票No.が同じデータの状態フラグを、
 *     下表のパターンにあてはめて、その伝票No.のステータス(未処理、作業中、処理済)を決定します。<BR>
 *     該当ステータスの伝票枚数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-4 アイテム数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getItem()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとケース/ピース区分(1:ケース、2:ピース)をもとに、
 *     入荷予定情報から荷主コード・商品コード・作業状態で集約し検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getStatus()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと商品コードが同じデータの状態フラグを、
 *     下表のパターンにあてはめて、その商品コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     該当ステータスのアイテム数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-5 ケース数<BR>
 *   1-6 ピース数<BR>
 *   <BR>
 *   <DIR>
 *     -検索/計算処理-(<CODE>getCase()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、
 *     入荷予定情報から作業数の検索を行います。<BR>
 *     検索した結果、下表のパターンにあてはめて、データのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     該当ステータスのケース数・ピースをカウントアップします。<BR>
 *     作業可能数をケース入数で割った商をケース数とします。<BR>
 *     作業可能数をケース入数で割った余りをピース数とします。<BR>
 *     ただし、該当情報がピースの場合、作業可能数全てをピース数とします。<BR>
 *     <BR>
 *   </DIR>
 *   1-7 合計<BR>
 *   <BR>
 *   <DIR>
 *     仕入先数、伝票枚数、アイテム数、ケース数、ピース数をそれぞれ合計します。<BR>
 *     <BR>
 *   </DIR>
 *   1-8 進捗率<BR>
 *   <BR>
 *   <DIR>
 *     処理済の仕入先数、伝票枚数、アイテム数、ケース数、ピース数をそれぞれ合計します。<BR>
 *     それぞれの値を1-6で計算した値で割った商に100を乗算します。<BR>
 *   <BR>
 *   </DIR>
 *   [パターン表]<BR>
 *   <BR>
 * 	   Ａ.未処理<BR>
 *     Ｂ.作業中、一部完了<BR>
 *     Ｃ.処理済<BR>
 *     <BR>
 * 	 		-	｜	Ａ	｜	Ｂ	｜	Ｃ	｜ステータス(画面)<BR>
 * 	 	--------------------------------------------------<BR>
 *	 		1 	｜	○	｜	－	｜	－	｜	未処理<BR>
 * 	 	--------------------------------------------------<BR>
 *			2	｜	○	｜	○	｜	－	｜	作業中<BR>
 * 	 	--------------------------------------------------<BR>
 *			3	｜	○	｜	○	｜	○	｜	作業中<BR>
 * 		--------------------------------------------------<BR>
 *			4	｜	○	｜	－	｜	○	｜	作業中<BR>
 * 		--------------------------------------------------<BR>
 *			5	｜	－	｜	○	｜	－	｜	作業中<BR>
 * 		--------------------------------------------------<BR>
 *			6	｜	－	｜	○	｜	○	｜	作業中<BR>
 * 		--------------------------------------------------<BR>
 *			7	｜	－	｜	－	｜	○	｜	処理済<BR>
 * 		--------------------------------------------------<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * <TR><TD>2005/03/07</TD><TD>Y.Okamura</TD><TD>検索方法変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockReceiveWorkingInquirySCH extends AbstractInstockReceiveSCH
{
	// Class variables -----------------------------------------------

	/**
	 * 作業状態：未開始
	 */
	private final int STATUS_UNSTART = 0;
	
	/**
	 * 作業状態：作業中
	 */
	private final int STATUS_NOWWORKING = 1;
	
	/**
	 * 作業状態：処理済
	 */
	private final int STATUS_COMPLETE = 2;
	
	/**
	 * ケースピース区分：ケース
	 */
	private final int CASE = 0;
	
	/**
	 * ケースピース区分：ピース
	 */
	private final int PIECE = 1;
	
	/**
	 * TCDC区分：TC
	 */
	private final int TCDC_TC = 0;
	
	/**
	 * TCDC区分：DC 
	 */
	private final int TCDC_DC = 1;
	
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
	public InstockReceiveWorkingInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		InstockReceiveWorkingInquiryParameter dispData = new InstockReceiveWorkingInquiryParameter();

		// 入荷予定日の検索
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "<>");
		searchKey.setPlanDateGroup(1);
		searchKey.setPlanDateCollect("");
		// 入荷予定日の昇順でパラメータ配列にセットする。
		searchKey.setPlanDateOrder(1, true);

		InstockPlan[] plandate = (InstockPlan[]) instockHandler.find(searchKey);

		// 該当データなし
		if (plandate == null || plandate.length <= 0)
		{
			return new InstockReceiveWorkingInquiryParameter();
		}

		String date[] = new String[plandate.length];
		for (int i = 0; i < plandate.length; i++)
		{
			date[i] = plandate[i].getPlanDate();
		}

		dispData.setPlanDateP(date);
		
		
		// 荷主コードの検索
		InstockPlanReportFinder instockFinder = new InstockPlanReportFinder(conn);
		
		// 状態フラグ(削除以外)
		searchKey.KeyClear();
		searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "<>");
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");
		InstockPlan[] wInstock = null;

		try
		{
			// 検索条件を設定する。
			// 状態フラグ(削除以外)
			searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "<>");
			// グルーピング条件に荷主コードをセット
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (instockFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// 状態フラグ(削除以外)
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "<>");
				// ソート順に登録日時を設定
				searchKey.setRegistDateOrder(1, false);
				
				searchKey.setConsignorCodeCollect("");

				if (instockFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
					wInstock = (InstockPlan[]) instockFinder.getEntities(1);
					dispData.setConsignorCode(wInstock[0].getConsignorCode());
				}
			}
			instockFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずCollectionをクローズする
			instockFinder.close();
		}
		
		return dispData;

	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、入荷状況照会用のデータをデータベースから取得します。<BR>
	 * ＴＣ入荷とＤＣ入荷とクロスＴＣ入荷の仕入先数、伝票枚数、アイテム数、ケース数、ピース数、荷主数をステータス別に返します。<BR>
	 * また、これらの値の合計と進捗率を返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンス。
	 *         <CODE>StorageSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。
	 * @return 検索結果を持つ<CODE>StorageSupportParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveWorkingInquiryParameter param = (InstockReceiveWorkingInquiryParameter) searchParam;

		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		// 検索条件をセットする
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "<>");

		// 入荷予定日
		String plandate = param.getPlanDate();
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}		
		// 荷主コード
		String consignorcode = param.getConsignorCode();
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}

		// 該当データがない場合、処理を終了する
		if (instockHandler.count(searchKey) == 0)
		{
			// 対象データはありませんでした。
			wMessage = "6003018";

			return new InstockReceiveWorkingInquiryParameter[0];
		}

		Vector vec = new Vector();
		// ＴＣ/ＤＣ区分ごとに各状態の値を取得する(tcdc 0:TC 1:DC 2:CROSSTC)
		for (int tcdc = 0; tcdc < 3; tcdc++)
		{
			// 作業数量の合計
			int TotalSupplier = 0;
			int TotalTicket = 0;
			int TotalItem = 0;
			long TotalCase = 0;
			long TotalPiece = 0;
			int TotalConsignor = 0;

			InstockReceiveWorkingInquiryParameter dispData = new InstockReceiveWorkingInquiryParameter();

			// 数量を取得する
			int Supplier[] = getSupplierCount(conn, consignorcode, plandate, tcdc);
			int Ticket[] = getTicketCount(conn, consignorcode, plandate, tcdc);
			int item[] = getItemCount(conn, consignorcode, plandate, tcdc);
			int Consignor[] = getConsignorCount(conn, consignorcode, plandate, tcdc);
			long[][] qty = getWorkQty(conn, consignorcode, plandate, tcdc);

			// 合計を求める(区分ごとでの未処理、作業中、処理済の合計)
			TotalSupplier = Supplier[STATUS_UNSTART] + Supplier[STATUS_NOWWORKING] + Supplier[STATUS_COMPLETE];
			TotalTicket = Ticket[STATUS_UNSTART] + Ticket[STATUS_NOWWORKING] + Ticket[STATUS_COMPLETE];
			TotalItem = item[STATUS_UNSTART] + item[STATUS_NOWWORKING] + item[STATUS_COMPLETE];
			TotalConsignor = Consignor[STATUS_UNSTART] + Consignor[STATUS_NOWWORKING] + Consignor[STATUS_COMPLETE];
			TotalCase = qty[CASE][STATUS_UNSTART] + qty[CASE][STATUS_NOWWORKING] + qty[CASE][STATUS_COMPLETE];
			TotalPiece = qty[PIECE][STATUS_UNSTART] + qty[PIECE][STATUS_NOWWORKING] + qty[PIECE][STATUS_COMPLETE];

			// 未処理についての処理の場合
			// 返却データの未処理数量をセットする
			dispData.setUnstartSupplierCount(Supplier[STATUS_UNSTART]);
			dispData.setUnstartTicketCount(Ticket[STATUS_UNSTART]);
			dispData.setUnstartConsignorCount(Consignor[STATUS_UNSTART]);
			dispData.setUnstartItemCount(item[STATUS_UNSTART]);
			dispData.setUnstartCaseCount(qty[CASE][STATUS_UNSTART]);
			dispData.setUnstartPieceCount(qty[PIECE][STATUS_UNSTART]);
			
			// 作業中についての処理の場合
			// 返却データの作業中数量をセットする
			dispData.setNowSupplierCount(Supplier[STATUS_NOWWORKING]);
			dispData.setNowTicketCount(Ticket[STATUS_NOWWORKING]);
			dispData.setNowItemCount(item[STATUS_NOWWORKING]);
			dispData.setNowConsignorCount(Consignor[STATUS_NOWWORKING]);
			dispData.setNowCaseCount(qty[CASE][STATUS_NOWWORKING]);
			dispData.setNowPieceCount(qty[PIECE][STATUS_NOWWORKING]);
			
			// 返却データの完了数量をセットする
			dispData.setFinishSupplierCount(Supplier[STATUS_COMPLETE]);
			dispData.setFinishTicketCount(Ticket[STATUS_COMPLETE]);
			dispData.setFinishConsignorCount(Consignor[STATUS_COMPLETE]);
			dispData.setFinishItemCount(item[STATUS_COMPLETE]);
			dispData.setFinishCaseCount(qty[CASE][STATUS_COMPLETE]);
			dispData.setFinishPieceCount(qty[PIECE][STATUS_COMPLETE]);

			// 返却データに合計数をセットする
			dispData.setSupplierTotal(TotalSupplier);
			dispData.setTicketTotal(TotalTicket);
			dispData.setItemTotal(TotalItem);
			dispData.setCaseTotal(TotalCase);
			dispData.setPieceTotal(TotalPiece);
			dispData.setConsignorTotal(TotalConsignor);

			// 返却データに進捗率をセットする
			dispData.setSupplierRate(getRate(Supplier[STATUS_COMPLETE], TotalSupplier) + "%");
			dispData.setTicketRate(getRate(Ticket[STATUS_COMPLETE], TotalTicket) + "%");
			dispData.setItemRate(getRate(item[STATUS_COMPLETE], TotalItem) + "%");
			dispData.setCaseRate(getRate(qty[CASE][STATUS_COMPLETE], TotalCase) + "%");
			dispData.setPieceRate(getRate(qty[PIECE][STATUS_COMPLETE], TotalPiece) + "%");
			dispData.setConsignorRate(getRate(Consignor[STATUS_COMPLETE], TotalConsignor) + "%");

			vec.addElement(dispData);

		}

		InstockReceiveWorkingInquiryParameter[] paramArray = new InstockReceiveWorkingInquiryParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		
		return paramArray;
		
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	
	/**
	 * 検索条件をセットします。<BR>
	 * @param searchKey 入荷予定情報の検索キー
	 * @param consignorcode 荷主コード
	 * @param plandate 予定日
	 * @param tcdc TC/DC区分
	 * @throws ReadWriteException データベースエラーが発生した時、通知されます。
	 */
	private void setSearchKey(InstockPlanSearchKey searchKey, String consignorcode,
								String plandate, int tcdc) throws ReadWriteException
	{
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "<>");
		// 入荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		
		// ＴＣ/ＤＣ入荷(tcdc 0:TC 1:DC 2:CROSSTC)
		if (tcdc == TCDC_TC)
		{
			searchKey.setTcdcFlag(InstockPlan.TCDC_FLAG_TC);
		}
		else if (tcdc == TCDC_DC)
		{
			searchKey.setTcdcFlag(InstockPlan.TCDC_FLAG_DC);
		}
		else
		{
			searchKey.setTcdcFlag(InstockPlan.TCDC_FLAG_CROSSTC);
		}
	}
	
	/**
	 * 検索条件に一致する荷主コードの件数を、作業状態単位にセットして返します。<BR>
	 * int[0]：未開始<BR>
	 * int[1]：作業中<BR>
	 * int[2]：完了<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      入荷予定日
	 * @param tcdc          ＴＣ/ＤＣ区分
	 * @return 荷主数(配列)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getConsignorCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		// データの検索
		setSearchKey(searchKey, consignorcode, plandate, tcdc);
		
		// ORDER BY条件
		searchKey.setConsignorCodeOrder(1, true);
		// GROUP BY条件
		searchKey.setConsignorCodeGroup(1);
		searchKey.setStatusFlagGroup(2);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 該当情報がない場合は検索を行わない
		if (instockHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}
		
		// 入荷予定情報を検索する
		InstockPlan[] instock = (InstockPlan[]) instockHandler.find(searchKey);
		
		if (instock == null || instock.length <= 0)
		{
			return returnCount;
		}

		// 作業区分を調べるために荷主コード単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < instock.length; i++)
		{
			// 初回のみの処理
			if (i == 0)
			{
				vec.addElement(instock[i]);
				continue;
			}
			
			// 2回目～最終までの処理
			// 荷主コードが同じ場合、配列にセットする。
			if (instock[i].getConsignorCode().equals(instock[i - 1].getConsignorCode()))
			{
				vec.addElement(instock[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(instock[i]);
			}
			
			// 最終の処理
			if (i == instock.length - 1)
			{
				// 今回の要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}

		}
		// 要素数が1つの場合、parentVecに要素をセットしていないのでここでセットする
		if (instock.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			InstockPlan instockArray[] = new InstockPlan[child.size()];
			child.copyInto(instockArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(instockArray)]++ ;

		}

		parentVec.clear();
		vec.clear();
		
		return returnCount;

	}

	/**
	 * 検索条件に一致する仕入先数(仕入先コード)の件数を、作業状態単位にセットして返します。<BR>
	 * int[0]：未開始<BR>
	 * int[1]：作業中<BR>
	 * int[2]：完了<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      入荷予定日
	 * @param tcdc          ＴＣ/ＤＣ入荷
	 * @return 仕入先数(配列)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getSupplierCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		// 検索条件をセットする
		// 状態フラグ(削除以外)
		setSearchKey(searchKey, consignorcode, plandate, tcdc);
		
		// 荷主コード、仕入先コードでソートする。
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setSupplierCodeOrder(2, true);
		// GROUP BY条件
		searchKey.setConsignorCodeGroup(1);
		searchKey.setSupplierCodeGroup(2);
		searchKey.setStatusFlagGroup(3);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setSupplierCodeCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 該当データがない場合は検索を行わない
		if (instockHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}
		
		// 検索を行う
		InstockPlan[] instock = (InstockPlan[]) instockHandler.find(searchKey);

		if (instock == null || instock.length <= 0)
		{
			return returnCount;
		}

		// 作業区分を調べるために荷主コード・仕分先コード単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < instock.length; i++)
		{
			// 初回のみここを通る
			if (i == 0)
			{
				vec.addElement(instock[i]);
				continue;
			}

			// ループ2回目～最終
			// 荷主コードと仕分先コードが同じ場合、配列にセットする。
			if (instock[i].getConsignorCode().equals(instock[i - 1].getConsignorCode()) 
				&& instock[i].getSupplierCode().equals(instock[i - 1].getSupplierCode()))
			{
				vec.addElement(instock[i]);
			}
			else
			{
				// 前回までの分をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の分を記憶する
				vec.addElement(instock[i]);
			}

			// 最終のみここを通る
			if (i == instock.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素数が1つの場合、parentVecに要素をセットしていないのでここでセットする
		if (instock.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			InstockPlan instockArray[] = new InstockPlan[child.size()];
			child.copyInto(instockArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(instockArray)]++ ;

		}
		
		parentVec.clear();
		vec.clear();

		return returnCount;

	}

	/**
	 * 伝票枚数を返します。<BR>
	 * int[0]：未開始<BR>
	 * int[1]：作業中<BR>
	 * int[2]：完了<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      入荷予定日
	 * @param tcdc          ＴＣ/ＤＣ入荷
	 * @return 伝票枚数(配列)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getTicketCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		// 検索条件をセットする
		setSearchKey(searchKey, consignorcode, plandate, tcdc);
		
		// 荷主コード、仕入先コード、伝票No.でソートする。
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setSupplierCodeOrder(2, true);
		searchKey.setInstockTicketNoOrder(3, true);
		// GROUP BY条件
		searchKey.setConsignorCodeGroup(1);
		searchKey.setSupplierCodeGroup(2);
		searchKey.setInstockTicketNoGroup(3);
		searchKey.setStatusFlagGroup(4);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setSupplierCodeCollect("");
		searchKey.setInstockTicketNoCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 該当データがない場合、検索を行いません
		if (instockHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// 検索を行う
		InstockPlan[] instock = (InstockPlan[]) instockHandler.find(searchKey);

		if (instock == null || instock.length <= 0)
		{
			return returnCount;
		}

		// 作業区分を調べるために荷主コード・仕分先コード・伝票No.単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < instock.length; i++)
		{
			// 初回のみここを通る
			if (i == 0)
			{
				vec.addElement(instock[i]);
				continue;
			}
			
			// ２回目～最終ここを通る
			// 荷主コードと仕分先コードと伝票No.が同じ場合、配列にセットする。
			if (instock[i].getConsignorCode().equals(instock[i - 1].getConsignorCode()) 
				&& instock[i].getSupplierCode().equals(instock[i - 1].getSupplierCode())
				&& instock[i].getInstockTicketNo().equals(instock[i - 1].getInstockTicketNo()))
			{
				vec.addElement(instock[i]);
			}
			else
			{
				// 前回までの分をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の分を記憶する
				vec.addElement(instock[i]);
			}

			// 最終の場合、今回の分をVectorにセットする
			if (i == instock.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
			
		}
		
		// 要素数が1つの場合、parentVecに要素をセットしていないのでここでセットする
		if (instock.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			InstockPlan instockArray[] = new InstockPlan[child.size()];
			child.copyInto(instockArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(instockArray)]++ ;

		}

		parentVec.clear();
		vec.clear();

		return returnCount;
		
	}

	/**
	 * アイテム数を返します。<BR>
	 * int[0]：未開始<BR>
	 * int[1]：作業中<BR>
	 * int[2]：完了<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      入荷予定日
	 * @param tcdc          ＴＣ/ＤＣ入荷
	 * @return アイテム数(配列)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getItemCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		// 検索条件をセットする
		setSearchKey(searchKey, consignorcode, plandate, tcdc);
		
		// 荷主コード、商品コードでソートする。
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setItemCodeOrder(2, true);
		// GROUP BY条件
		searchKey.setConsignorCodeGroup(1);
		searchKey.setItemCodeGroup(2);
		searchKey.setStatusFlagGroup(3);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setItemCodeCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 該当データがない場合、検索を行いません
		if (instockHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}
		
		// 検索を行う
		InstockPlan[] instock = (InstockPlan[]) instockHandler.find(searchKey);

		if (instock == null || instock.length <= 0)
		{
			return returnCount;
		}

		// 作業区分を調べるために商品コード単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < instock.length; i++)
		{
			// 初回のみの処理
			if (i == 0)
			{
				vec.addElement(instock[i]);
				continue;
			}
			
			// 2回目～最終までの処理
			// 荷主コードと商品コードが同じ場合、配列にセットする。
			if (instock[i].getConsignorCode().equals(instock[i - 1].getConsignorCode())
				&& instock[i].getItemCode().equals(instock[i - 1].getItemCode()))
			{
				vec.addElement(instock[i]);
			}
			else
			{
				// 前回までの分をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の分を記憶する
				vec.addElement(instock[i]);
			}

			// 最終の場合、今回の分をVectorにセットする
			if (i == instock.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素数が1つの場合、parentVecに要素をセットしていないのでここでセットする
		if (instock.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			InstockPlan instockArray[] = new InstockPlan[child.size()];
			child.copyInto(instockArray);
			
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(instockArray)]++ ;

		}
		
		parentVec.clear();
		vec.clear();

		return returnCount;
		
	}

	/**
	 * ケース数・ピース数を作業状態ごとに分類して返します。<BR>
	 * 以下の型で結果を返します。<BR>
	 * long[A][B]：ケース数、ピース数を格納した結果<BR>
	 *   <DIR>
	 *   A：ケースピース区分<BR>
	 *   B：作業状態<BR>
	 *   </DIR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      入荷予定日
	 * @param tcdc          ＴＣ/ＤＣ入荷
	 * @return ケース数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private long[][] getWorkQty(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();

		// 検索条件をセットする
		setSearchKey(searchKey, consignorcode, plandate, tcdc);
		
		// 取得条件
		searchKey.setPlanQtyCollect("");
		searchKey.setEnteringQtyCollect("");
		searchKey.setCasePieceFlagCollect("");
		searchKey.setStatusFlagCollect("");
		
		// 返却データ
		long[][] returnQty = {{0, 0, 0}, {0, 0, 0}};
		
		// 該当データがない場合、検索を行わない
		if (instockHandler.count(searchKey) <= 0)
		{
			return returnQty;
		}

		// 検索を行う
		InstockPlan[] resultEntity = (InstockPlan[]) instockHandler.find(searchKey);

		if (resultEntity == null || resultEntity.length <= 0)
		{
			return returnQty;
		}

		// ケース数・ピース数を算出する
		int status = 0;
		for (int i = 0; i < resultEntity.length; i++)
		{
			// 画面上での作業区分を取得する
			status = getStatus(resultEntity[i]);
			
			// ケース、指定なし、混合の場合入数からケース数とピース数を求める
			if (!resultEntity[i].getCasePieceFlag().equals(InstockPlan.CASEPIECE_FLAG_PIECE))
			{
				// ケース数
				returnQty[CASE][status] += DisplayUtil.getCaseQty(resultEntity[i].getPlanQty(), resultEntity[i].getEnteringQty());
				
				// ピース数
				returnQty[PIECE][status] += DisplayUtil.getPieceQty(resultEntity[i].getPlanQty(), resultEntity[i].getEnteringQty());
			}
			// ピースの場合はピース数のみ足しこむ
			else
			{
				returnQty[PIECE][status] += resultEntity[i].getPlanQty();
			}
			 
		}

		return returnQty;
	}
	
	/**
	 * 入荷予定情報より、画面上でのどの区分にあたるのかを返すためのメソッドです。<BR>
	 * 入荷予定情報．未開始のみ：未開始<BR>
	 * 入荷予定情報．完了のみ：処理済<BR>
	 * 上記以外：処理中<BR>
	 * 
	 * @param pInstock 予定情報の内容を持つInStockPlanクラスのインスタンス。
	 * @return 作業状態 0：未処理、1：作業中、2：処理済
	 */
	private int getStatus(InstockPlan[] pInstock)
	{
		// 未開始の存在フラグ
		boolean unstart = false;
		// 作業中の存在フラグ
		boolean working = false;
		// 完了分の存在フラグ
		boolean complete = false;
		
		// 作業状態の存在チェックを行う
		for (int i = 0; i < pInstock.length; i++)
		{
			// 未開始の場合
			if (pInstock[i].getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
			{
				unstart = true;
			}
			// 作業中、一部完了の場合
			else if(pInstock[i].getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING)
							|| pInstock[i].getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART))
			{
				working = true;
			}
			// 完了の場合
			else if (pInstock[i].getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETION))
			{
				complete = true;
			}
		}

		// 未開始のみの場合、未処理
		if (unstart && !working && !complete)
		{
			return STATUS_UNSTART;
		}
		// 完了のみの場合、処理済
		else if (!unstart && !working && complete)
		{
			return STATUS_COMPLETE;
		}
		// そのほかの場合、作業中
		else
		{
			return STATUS_NOWWORKING;
		}

	}

	/**
	 * 入荷予定情報より、画面上でのどの区分にあたるのかを返すためのメソッドです。<BR>
	 * 入荷予定情報．未開始：未開始<BR>
	 * 入荷予定情報．作業中または一部完了：作業中<BR>
	 * 入荷予定情報．完了：処理済<BR>
	 * 
	 * @param pInstock 予定情報の内容を持つInStockPlanクラスのインスタンス。
	 * @return 作業状態 0：未処理、1：作業中、2：処理済
	 */
	private int getStatus(InstockPlan pInstock)
	{
		// 未開始の場合
		if (pInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_UNSTART))
		{
			return STATUS_UNSTART;
		}
		// 作業中の場合
		else if(pInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING)
						|| pInstock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART))
		{
			return STATUS_NOWWORKING;
		}
		// 完了の場合
		else
		{
			return STATUS_COMPLETE;
		}

	}
	
	/**
	 * 完了数と、合計数から作業進捗率(String)を求めます。
	 * @param pFinishQty 完了数
	 * @param pTotalQty 合計数
	 * @return 作業進捗率
	 */
	private String getRate(double pFinishQty, double pTotalQty)
	{
		if (pTotalQty <= 0)
		{
			return "0.0";
		}
		
		double returnRate;
		
		returnRate = pFinishQty / pTotalQty * 100;
		returnRate = java.lang.Math.round(returnRate * 10.0) / 10.0;
		
		return Double.toString(returnRate);
	}
	

}
//end of class

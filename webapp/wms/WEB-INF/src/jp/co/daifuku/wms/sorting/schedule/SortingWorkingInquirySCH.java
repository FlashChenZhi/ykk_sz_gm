package jp.co.daifuku.wms.sorting.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * 仕分状況照会処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分状況照会処理を行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   1-1 作業情報の仕分予定日を返します。<BR> 
 * 	 <BR>
 *   [検索条件] <BR>
 *   <BR>
 *   作業区分が仕分<BR>
 *   状態フラグが削除以外<BR>
 *   仕分予定日の昇順でパラメータ配列にセットする。<BR> 
 *   <BR> 
 *   [返却データ] <BR>
 *   <BR>
 *   仕分予定日:PlanDateP* <BR>
 *   <BR>
 *   1-2 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *	     該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   作業区分が仕分<BR>
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
 *   画面から入力された内容をパラメータとして受け取り、仕分状況照会用のデータをデータベースから取得します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   ケース仕分とピース仕分の仕分先数(出荷先数)、アイテム数、数量(ケース数、ピース数)、荷主数をステータス別に返します。<BR>
 *   また、これらの値の合計と進捗率を返します。<BR>
 *   検索するテーブルは作業情報テーブル(DNWORKINFO)。<BR>
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   作業区分が仕分<BR>
 *   状態フラグが削除以外<BR>
 *   荷主コードが空白の場合、全荷主コードが対象となります。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   荷主コード			:ConsignorCode <BR>
 *   仕分予定日			:PlanDate* <BR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR> 
 * 	 未処理_仕分先数(出荷先数)		:UnstartSoringCount<BR>
 *   未処理_アイテム数				:UnstartItemCount<BR>
 *   未処理_数量(ケース数、ピース数):UnstartQtyCount<BR>
 *   未処理_荷主数					:UnstartConsignorCount<BR>
 *   <BR>
 * 	 作業中_仕分先数(出荷先数)		:NowSoringCount<BR>
 *   作業中_アイテム数				:NowItemCount<BR>
 *   作業中_数量(ケース数、ピース数):NowQtyCount<BR>
 *   作業中_荷主数					:NowConsignorCount<BR>
 *   <BR> 
 *   処理済_仕分先数(出荷先数)		:FinishSoringCount<BR>
 *   処理済_アイテム数				:FinishItemCount<BR>
 *   処理済_数量(ケース数、ピース数):FinishQtyCount<BR>
 *   処理済_荷主数					:FinishConsignorCount<BR>
 *   <BR>
 *   合計_仕分先数(出荷先数)		:TotalSoringCount<BR>
 *   合計_アイテム数				:TotalItemCount<BR>
 *   合計_数量(ケース数、ピース数)	:TotalQtyCount<BR>
 *   合計_荷主数					:TotalConsignorCount<BR>
 *   <BR>
 *   進捗率_仕分先数(出荷先数)		:SoringProgressiveRate<BR>
 *   進捗率_アイテム数				:ItemProgressiveRate<BR>
 *   進捗率_数量(ケース数、ピース数):QtyProgressiveRate<BR>
 *   進捗率_荷主数					:ConsignorProgressiveRate<BR>
 *   <BR>
 *   [検索/計算処理]<BR>
 *   <BR>
 *   1-1 荷主数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getCinsignor()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとケース/ピース区分(1:ケース、2:ピース)をもとに、作業情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードが同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードがかわったら、荷主数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-2 仕分先数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getCustomer()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとケース/ピース区分(1:ケース、2:ピース)をもとに、作業情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと仕分先コード(出荷先コード)が同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードと仕分先コード(出荷先コード)のステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードと仕分先コード(出荷先コード)がかわったら、仕分先数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-3 アイテム数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getItem()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとケース/ピース区分(1:ケース、2:ピース)をもとに、作業情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと商品コードが同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードと商品コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードと商品コードがかわったら、アイテム数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-4 数量(ケース数、ピース数)<BR>
 *   <BR>
 *   <DIR>
 *     -検索/計算処理-(<CODE>getQty()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとケース/ピース区分(1:ケース、2:ピース)、状態フラグ(0:未開始、2:作業中、4:完了)をもとに、作業情報の検索を行います。<BR>
 *     検索した結果、ケース/ピース区分が1:ケースの場合、作業可能数をケース入数で割った商をカウントします。<BR>
 *     ケース/ピース区分が2:ピースの場合、作業可能数をカウントします。<BR>
 *     <BR>
 *   </DIR>
 *   1-5 合計<BR>
 *   <BR>
 *   <DIR>
 *     仕分先数(出荷先数)、アイテム数、数量(ケース数、ピース数)をそれぞれ合計します。<BR>
 *     <BR>
 *   </DIR>
 *   1-6 進捗率<BR>
 *   <BR>
 *   <DIR>
 *     処理済の仕分先数(出荷先数)、アイテム数、数量(ケース数、ピース数)をそれぞれ合計します。<BR>
 *     それぞれの値を1-6で計算した値で割った商に100を乗算します。<BR>
 *   <BR>
 *   </DIR>
 *   [パターン表]<BR>
 *   <BR>
 * 	   Ａ.未処理<BR>
 *     Ｂ.作業中<BR>
 *     Ｃ.処理済<BR>
 *　   <BR>
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
 * <TR><TD>2004/11/08</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingWorkingInquirySCH extends AbstractSortingSCH
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
	public SortingWorkingInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		SortingWorkingInquiryParameter dispData = new SortingWorkingInquiryParameter();

		// 仕分予定日の検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		searchKey.setPlanDateGroup(1);
		searchKey.setPlanDateCollect("");
		// 仕分予定日の昇順でパラメータ配列にセットする。
		searchKey.setPlanDateOrder(1, true);

		WorkingInformation[] plandate = (WorkingInformation[]) workingHandler.find(searchKey);

		// 該当データなし
		if (plandate == null || plandate.length <= 0)
		{
			return new SortingWorkingInquiryParameter();
		}

		String date[] = new String[plandate.length];
		for (int i = 0; i < plandate.length; i++)
		{
			date[i] = plandate[i].getPlanDate();
		}

		dispData.setPlanDateP(date);

		searchKey.KeyClear();
		// 荷主コードの検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (workingHandler.count(searchKey) == 1)
		{
			try
			{
				WorkingInformation consignor = (WorkingInformation) workingHandler.findPrimary(searchKey);

				dispData.setConsignorCode(consignor.getConsignorCode());

			}
			catch (NoPrimaryException e)
			{
				dispData.setConsignorCode(null);

				return dispData;
			}
		}

		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕分状況照会用のデータをデータベースから取得します。<BR>
	 * ケース仕分とピース仕分の仕分先数(出荷先数)、アイテム数、数量(ケース数、ピース数)、荷主数をステータス別に返します。<BR>
	 * また、これらの値の合計と進捗率を返します。<BR>* 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param locale 地域コード、画面表示用にローカライズした値を取得するために使用します。
	 * @param searchParam 表示データ取得条件を持つ<CODE>SortingParameter</CODE>クラスのインスタンス。
	 *         <CODE>SortingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。
	 * @return 検索結果を持つ<CODE>SortingtParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		SortingWorkingInquiryParameter param = (SortingWorkingInquiryParameter) searchParam;

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		String consignorcode = param.getConsignorCode();
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 仕分予定日
		String plandate = param.getPlanDate();
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		
		if (workingHandler.count(searchKey) == 0)
		{
			// 対象データはありませんでした。
			wMessage = "6003018";
			return new SortingWorkingInquiryParameter[0];
		}

		Vector vec = new Vector();

		// ケース/ピース仕分(casePieceFlag=1 ケース仕分、casePieceFlag=2 ピース仕分)
		for (int casePieceFlag = 1; casePieceFlag < 3; casePieceFlag++)
		{
			// ケースピース区分ごとの合計数量
			int TotalCustomer = 0;
			int TotalItem = 0;
			long TotalQty = 0;
			int TotalConsignor = 0;

			SortingWorkingInquiryParameter dispData = new SortingWorkingInquiryParameter();

			// ケースピース区分・作業状態ごとの各数量
			int Customer[] = getCustomerCount(conn, consignorcode, plandate, casePieceFlag);
			int Item[] = getItemCount(conn, consignorcode, plandate, casePieceFlag);
			long Qty[] = getQty(conn, consignorcode, plandate, casePieceFlag);
			int Consignor[] = getConsignorCount(conn, consignorcode, plandate, casePieceFlag);

			// ケースピース区分ごとの合計数量を計上する
			TotalCustomer = Customer[STATUS_UNSTART] + Customer[STATUS_NOWWORKING] + Customer[STATUS_COMPLETE];
			TotalItem = Item[STATUS_UNSTART] + Item[STATUS_NOWWORKING] + Item[STATUS_COMPLETE];
			TotalQty = Qty[STATUS_UNSTART] + Qty[STATUS_NOWWORKING] + Qty[STATUS_COMPLETE];
			TotalConsignor = Consignor[STATUS_UNSTART] + Consignor[STATUS_NOWWORKING] + Consignor[STATUS_COMPLETE];

			// 返却データの未処理に数量をセット
			dispData.setUnstartSoringCount(Customer[STATUS_UNSTART]);
			dispData.setUnstartItemCount(Item[STATUS_UNSTART]);
			dispData.setUnstartQtyCount(Qty[STATUS_UNSTART]);
			dispData.setUnstartConsignorCount(Consignor[STATUS_UNSTART]);
			// 返却データの作業中に数量をセット
			dispData.setNowSoringCount(Customer[STATUS_NOWWORKING]);
			dispData.setNowItemCount(Item[STATUS_NOWWORKING]);
			dispData.setNowQtyCount(Qty[STATUS_NOWWORKING]);
			dispData.setNowConsignorCount(Consignor[STATUS_NOWWORKING]);
			// 返却データの処理済に数量をセット
			dispData.setFinishSoringCount(Customer[STATUS_COMPLETE]);
			dispData.setFinishItemCount(Item[STATUS_COMPLETE]);
			dispData.setFinishQtyCount(Qty[STATUS_COMPLETE]);
			dispData.setFinishConsignorCount(Consignor[STATUS_COMPLETE]);

			// 返却データの合計に数量をセット
			dispData.setTotalSoringCount(TotalCustomer);
			dispData.setTotalItemCount(TotalItem);
			dispData.setTotalQtyCount(TotalQty);
			dispData.setTotalConsignorCount(TotalConsignor);

			// 返却データの進捗率に数量をセット
			dispData.setSoringProgressiveRate(getRate(Customer[STATUS_COMPLETE], TotalCustomer) + "%");
			dispData.setItemProgressiveRate(getRate(Item[STATUS_COMPLETE], TotalItem) + "%");
			dispData.setQtyProgressiveRate(getRate(Qty[STATUS_COMPLETE], TotalQty) + "%");
			dispData.setConsignorProgressiveRate(getRate(Consignor[STATUS_COMPLETE], TotalConsignor) + "%");

			vec.addElement(dispData);

		}

		SortingWorkingInquiryParameter[] paramArray = new SortingWorkingInquiryParameter[vec.size()];
		vec.copyInto(paramArray);
		
		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 荷主数を返します。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      仕分予定日
	 * @param casepiece     ケース/ピース区分
	 * @param status        ステータス
	 * @return 荷主数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getConsignorCount(Connection conn, String consignorcode, String plandate, int casepiece) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// 検索条件をセットする
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 仕分予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ケース/ピース仕分(casepiece=1 ケース仕分、casepiece=2 ピース仕分)
		if (casepiece == 1)
		{
			searchKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (casepiece == 2)
		{
			searchKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}
		// 集約条件
		searchKey.setConsignorCodeGroup(1);
		searchKey.setStatusFlagGroup(2);
		// ソート条件
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setStatusFlagOrder(2, true);
		// 取得項目
		searchKey.setConsignorCodeCollect();
		searchKey.setStatusFlagCollect();
		
		int[] returnCount = {0, 0, 0};
		
		// 該当データがない場合は検索を行いません
		if (workingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}
		
		// 検索を行う
		WorkingInformation[] working = (WorkingInformation[]) workingHandler.find(searchKey);

		if (working == null || working.length <= 0)
		{
			return returnCount;
		}
		
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < working.length; i++)
		{
			// 初回のみの処理
			if (i == 0)
			{
				vec.addElement(working[i]);
				continue;
			}
			
			// 2回目～最終までの処理
			// 荷主コードが同じ場合、配列にセットする。
			if (working[i].getConsignorCode().equals(working[i - 1].getConsignorCode()))
			{
				vec.addElement(working[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(working[i]);
			}
			
			// 最終のみの処理
			if (i == working.length - 1)
			{
				// 今回の要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}

		}
		// 要素数が1つの場合、Vectorにセットしていないのでここでセットする
		if (working.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を求める
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation wiArray[] = new WorkingInformation[child.size()];
			child.copyInto(wiArray);
			
			returnCount[getStatus(wiArray)]++;

		}

		return returnCount;
		
	}

	/**
	 * 仕分先数(出荷先数)を返します。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      仕分予定日
	 * @param casepiece     ケース/ピース仕分
	 * @param status        ステータス
	 * @return 仕分先数(出荷先数)を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getCustomerCount(Connection conn, String consignorcode, String plandate, int casepiece) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 仕分予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ケース/ピース仕分(casepiece=1 ケース仕分、casepiece=2 ピース仕分)
		if (casepiece == 1)
		{
			searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (casepiece == 2)
		{
			searchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}

		// 荷主コード、仕分先コード(出荷先コード)でソートする。
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setCustomerCodeOrder(2, true);
		searchKey.setStatusFlagOrder(3, true);
		// 集約条件
		searchKey.setConsignorCodeGroup(1);
		searchKey.setCustomerCodeGroup(2);
		searchKey.setStatusFlagGroup(3);
		// 取得項目
		searchKey.setConsignorCodeCollect();
		searchKey.setCustomerCodeCollect();
		searchKey.setStatusFlagCollect();
		
		int[] returnCount = {0, 0, 0};
		
		// 対象データがない場合は検索を行いません
		if (workingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}
		
		// 検索を行う
		WorkingInformation[] working = (WorkingInformation[]) workingHandler.find(searchKey);

		if (working == null || working.length <= 0)
		{
			return returnCount;
		}

		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < working.length; i++)
		{
			// 初回、要素を記憶する
			if (i == 0)
			{
				vec.addElement(working[i]);
				continue;
			}
			
			// 荷主コードと仕分先コード(出荷先コード)が同じ場合、配列にセットする。
			if (working[i].getConsignorCode().equals(working[i - 1].getConsignorCode())
			 && working[i].getCustomerCode().equals(working[i - 1].getCustomerCode()))
			{
				vec.addElement(working[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();

				// 今回の要素を記憶する
				vec.addElement(working[i]);
			}

			// 最終データの場合、今回の要素をVectorにセットする
			if (i == working.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素が１の場合、Vectorにデータをセットしていないので、ここでセットする
		if (working.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation wiArray[] = new WorkingInformation[child.size()];
			child.copyInto(wiArray);
			
			returnCount[getStatus(wiArray)]++;

		}
		
		parentVec.clear();
		vec.clear();

		return returnCount;

	}

	/**
	 * アイテム数を返します。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      仕分予定日
	 * @param casepiece     ケース/ピース仕分
	 * @param status        ステータス
	 * @return アイテム数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getItemCount(Connection conn, String consignorcode, String plandate, int casepiece) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 仕分予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ケース/ピース仕分(casepiece=1 ケース仕分、casepiece=2 ピース仕分)
		if (casepiece == 1)
		{
			searchKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (casepiece == 2)
		{
			searchKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}
		// 荷主コード、商品コードでソートする。
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setItemCodeOrder(2, true);
		searchKey.setStatusFlagOrder(3, true);
		// グループ順
		searchKey.setConsignorCodeGroup(1);
		searchKey.setItemCodeGroup(2);
		searchKey.setStatusFlagGroup(3);
		// 取得条件
		searchKey.setConsignorCodeCollect();
		searchKey.setItemCodeCollect("");
		searchKey.setStatusFlagCollect();
		
		int[] returnCount = {0, 0, 0};
		
		// 該当データがない場合は検索を行いません
		if (workingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// 検索を行う
		WorkingInformation[] working = (WorkingInformation[]) workingHandler.find(searchKey);

		if (working == null || working.length <= 0)
		{
			return returnCount;
		}
		
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < working.length; i++)
		{
			// 初回のみの処理。要素を記憶する
			if (i == 0)
			{
				vec.addElement(working[i]);
				continue;
			}
			
			// 荷主コードと商品コードが同じ場合、配列にセットする。
			if (working[i].getConsignorCode().equals(working[i - 1].getConsignorCode())
				&& working[i].getItemCode().equals(working[i - 1].getItemCode()))
			{
				vec.addElement(working[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(working[i]);
			}

			// 最後の情報の場合、要素をVectorにセットする
			if (i == working.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素が１つの場合、Vectorに情報をセットしていないのでここでセットする
		if (working.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation wiArray[] = new WorkingInformation[child.size()];
			child.copyInto(wiArray);
			
			returnCount[getStatus(wiArray)]++;

		}
			
		parentVec.clear();
		vec.clear();

		return returnCount;
		
	}

	/**
	 * 数量(ケース数、ピース数)を返します。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      仕分予定日
	 * @param casepiece     ケース/ピース区分
	 * @param status        ステータス
	 * @return 数量(ケース数、ピース数)を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private long[] getQty(Connection conn, String consignorcode, String plandate, int casepiece) throws ReadWriteException
	{
		WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();

		// データの検索
		// 作業区分(仕分)
		searchKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");

		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 仕分予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ケース/ピース仕分(casepiece=1 ケース仕分、casepiece=2 ピース仕分)
		if (casepiece == 1)
		{
			searchKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (casepiece == 2)
		{
			searchKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}

		// 予定情報からみての作業状態を判断するために予定一意キーにてソートします
		searchKey.setPlanUkeyOrder(1, true);
		// 取得項目
		searchKey.setPlanUkeyCollect();
		searchKey.setStatusFlagCollect();
		searchKey.setPlanEnableQtyCollect("");
		searchKey.setEnteringQtyCollect("");
		searchKey.setCasePieceFlagCollect("");
		
		long[] returnCount = {0, 0, 0};
		
		if (workingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}
		
		WorkingInformation[] working = (WorkingInformation[]) workingHandler.find(searchKey);

		if (working == null || working.length <= 0)
		{
			return returnCount;
		}

		Vector parentVec = new Vector();
		Vector vec = new Vector();

		// 一致する予定一意キーにて作業情報をまとめる
		for (int i = 0; i < working.length; i++)
		{
			// 初回のみの処理。要素を記憶する
			if (i == 0)
			{
				vec.addElement(working[i]);
				continue;
			}
			
			// 予定一意キーが同じ場合、配列にセットする。
			if (working[i].getPlanUkey().equals(working[i - 1].getPlanUkey()))
			{
				vec.addElement(working[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(working[i]);
			}

			// 最後の情報の場合、要素をVectorにセットする
			if (i == working.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素が１つの場合、Vectorに情報をセットしていないのでここでセットする
		if (working.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当情報の作業数量を計上する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			WorkingInformation wiArray[] = new WorkingInformation[child.size()];
			child.copyInto(wiArray);
			
			// 計算用
			int tempQty = 0;
		
			// ケース作業の場合
			if (casepiece == 1)
			{
				// クロスドック処理により作業がわれている場合があるため、
				// 予定一意キー単位で作業可能数を合算し、ケース作業数量を求める。
				int sumQty = 0;
				for (int j = 0; j < wiArray.length; j++)
				{
					sumQty += wiArray[j].getPlanEnableQty();
				}
				tempQty += DisplayUtil.getCaseQty(sumQty, wiArray[0].getEnteringQty());
				
			}
			// ピース作業の場合
			else
			{
				for (int j = 0; j < wiArray.length; j++)
				{
					tempQty += wiArray[j].getPlanEnableQty();
				}
			}
			
			returnCount[getStatus(wiArray)] += tempQty;

		}
		
		parentVec.clear();
		vec.clear();
		
		return returnCount;

	}

	/**
	 * 入荷予定情報より、画面上でのどの区分にあたるのかを返すためのメソッドです。<BR>
	 * 入荷予定情報．未開始のみ：未開始<BR>
	 * 入荷予定情報．完了のみ：処理済<BR>
	 * 上記以外：処理中<BR>
	 * 
	 * @param pInstock 予定情報の内容を持つInStockPlanクラスのインスタンス。
	 * @return 作業状態　0：未処理、1：作業中、2：処理済
	 */
	private int getStatus(WorkingInformation[] pWorkinfo)
	{
		// 未開始の存在フラグ
		boolean unstart = false;
		// 作業中の存在フラグ
		boolean working = false;
		// 完了分の存在フラグ
		boolean complete = false;
		
		// 作業状態の存在チェックを行う
		for (int i = 0; i < pWorkinfo.length; i++)
		{
			// 未開始の場合
			if (pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_UNSTART))
			{
				unstart = true;
			}
			// 開始済、作業中、一部完了の場合
			else if(pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_START)
							|| pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
							|| pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART))
			{
				working = true;
			}
			// 完了の場合
			else if (pWorkinfo[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_COMPLETION))
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

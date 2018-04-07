//$Id: ShippingWorkingInquirySCH.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * 出荷状況照会処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷状況照会処理を行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   1-1 出荷予定情報の出荷予定日を返します。<BR> 
 * 	 <BR>
 *   [検索条件] <BR>
 *   <DIR>
 *   状態フラグが削除以外<BR>
 *   </DIR>
 *   出荷予定日の昇順でパラメータ配列にセットする。<BR> 
 *   <BR>
 *   1-2 出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *	     該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <DIR>
 *   状態フラグが削除以外<BR>
 *   </DIR>
 * </DIR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、出荷状況照会用のデータをデータベースから取得します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   ＴＣ出荷とＤＣ出荷とクロスＴＣ出荷と出荷の出荷先数、伝票枚数、アイテム数、ケース数、ピース数、荷主数をステータス別に返します。<BR>
 *   また、これらの値の合計と進捗率を返します。<BR>
 *   検索するテーブルは出荷予定情報テーブル(DNSHIPPINGPLAN)。<BR>
 *   <BR>
 *   [検索条件] <BR> 
 *   <DIR>
 *   状態フラグが削除以外<BR>
 *   荷主コードが空白の場合、全荷主コードが対象となります。<BR>
 *   </DIR>
 *   [パラメータ] *必須入力<BR>
 *   <DIR>
 *   荷主コード			:ConsignorCode <BR>
 *   出荷予定日			:PlanDate* <BR>
 *   </DIR>
 *   [検索/計算処理]<BR>
 *   <BR>
 *   1-1 荷主数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getConsignor()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、出荷予定情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードが同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードがかわったら、荷主数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-2 出荷先数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getCustomer()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ出荷(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、出荷予定情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと出荷先コードが同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードと出荷先コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードと出荷先コードがかわったら、出荷先数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-3 伝票枚数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getTicket()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)をもとに、出荷予定情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと出荷先コードと伝票No.が同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードと出荷先コードと伝票No.のステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードと出荷先コードと伝票No.がかわったら、伝票枚数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-4 アイテム数<BR>
 *   <BR>
 *   <DIR>
 *     -検索処理-(<CODE>getItem()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとケース/ピース区分(1:ケース、2:ピース)をもとに、入荷予定情報の検索を行います。<BR>
 *     <BR>
 *     -計算処理-(<CODE>getCount()</CODE>メソッド)<BR>
 *     <BR>
 *     検索した結果、荷主コードと商品コードが同じデータの状態フラグを、<BR>
 *     下表のパターンにあてはめて、その荷主コードと商品コードのステータス(未処理、作業中、処理済)を決定します。<BR>
 *     荷主コードと商品コードがかわったら、アイテム数をカウントアップします。<BR>
 *     <BR>
 *   </DIR>
 *   1-5 ケース数<BR>
 *   <BR>
 *   <DIR>
 *     -検索/計算処理-(<CODE>getCase()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)、状態フラグ(0:未開始、2:作業中、4:完了)をもとに、出荷予定情報の検索を行います。<BR>
 *     作業可能数をケース入数で割った商をカウントします。<BR>
 *     <BR>
 *   </DIR>
 *   1-6 ピース数<BR>
 *   <BR>
 *   <DIR>
 *     -検索/計算処理-(<CODE>getPiece()</CODE>メソッド)<BR>
 *     <BR>
 *     パラメータとＴＣ/ＤＣ区分(0:ＤＣ 1:クロスＴＣ 2:ＴＣ)、状態フラグ(0:未開始、2:作業中、4:完了)をもとに、出荷予定情報の検索を行います。<BR>
 *     作業可能数をカウントします。<BR>
 *     <BR>
 *   </DIR>
 *   1-7 合計<BR>
 *   <BR>
 *   <DIR>
 *     出荷先数、伝票枚数、アイテム数、ケース数、ピース数をそれぞれ合計します。<BR>
 *     <BR>
 *   </DIR>
 *   1-8 進捗率<BR>
 *   <BR>
 *   <DIR>
 *     処理済の出荷先数、伝票枚数、アイテム数、ケース数、ピース数をそれぞれ合計します。<BR>
 *     それぞれの値を1-6で計算した値で割った商に100を乗算します。<BR>
 *   <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ShippingWorkingInquirySCH extends AbstractShippingSCH 
{
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
	
	/**
	 * TCDC区分：クロス
	 */
	private final int TCDC_CROSS = 2;
	

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingWorkingInquirySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
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
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		ShippingWorkingInquiryParameter dispData = new ShippingWorkingInquiryParameter();

		// 出荷予定日の検索
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		searchKey.setPlanDateGroup(1);
		searchKey.setPlanDateCollect("");
		// 出荷予定日の昇順でパラメータ配列にセットする。
		searchKey.setPlanDateOrder(1, true);

		ShippingPlan[] plandate = (ShippingPlan[]) shippingHandler.find(searchKey);

		// 該当データなし
		if (plandate == null || plandate.length <= 0)
		{
			return new ShippingWorkingInquiryParameter();
		}

		String date[] = new String[plandate.length];
		for (int i = 0; i < plandate.length; i++)
		{
			date[i] = plandate[i].getPlanDate();
		}

		dispData.setPlanDateP(date);

		// 荷主コードの検索
		// 状態フラグ(削除以外)
		searchKey.KeyClear();
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

	    ShippingPlanReportFinder sRFinder = new ShippingPlanReportFinder(conn) ;
	    
	    try
		{
		    int count = sRFinder.search(searchKey) ;
		    ShippingPlan[] sPlan = null ;
		    if(count == 1)
		    {
		        sPlan = (ShippingPlan[])sRFinder.getEntities(1) ;
		        dispData.setConsignorCode(sPlan[0].getConsignorCode());
		    }
		}
		catch(ReadWriteException e)
		{

		    //6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), this.getClass().getName() );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnShippingPlan" ) ) ;
		    
		}
		finally
		{
			sRFinder.close();
		}
		
		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、出荷状況照会用のデータをデータベースから取得します。<BR>
	 * ＴＣ出荷とＤＣ出荷とクロスＴＣ出荷と出荷の出荷先数、伝票枚数、アイテム数、ケース数、ピース数、荷主数をステータス別に返します。<BR>
	 * また、これらの値の合計と進捗率を返します。<BR>* 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>ShippingParameter</CODE>クラスのインスタンス。
	 *         <CODE>ShippingParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。
	 * @return 検索結果を持つ<CODE>ShippingParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ShippingWorkingInquiryParameter param = (ShippingWorkingInquiryParameter) searchParam;

		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		// データの検索
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		String consignorcode = param.getConsignorCode();
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 出荷予定日
		String plandate = param.getPlanDate();
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}

		if (shippingHandler.count(searchKey) == 0)
		{
			// 対象データはありませんでした。
			wMessage = "6003018";

			return new ShippingWorkingInquiryParameter[0];
		}

		Vector vec = new Vector();

		// ＴＣ/ＤＣ出荷(i=0 ＴＣ出荷、i=1 ＤＣ出荷、i=2 クロスＴＣ出荷)
		for (int tcdc = 0; tcdc < 3; tcdc++)
		{
			// TC/DC区分ごとの合計数量
			int TotalCustomer = 0;
			int TotalTicket = 0;
			int TotalItem = 0;
			long TotalCase = 0;
			long TotalPiece = 0;
			int TotalConsignor = 0;

			ShippingWorkingInquiryParameter dispData = new ShippingWorkingInquiryParameter();

			// TC/DC区分・作業状態ごとに各数量を取得する
			int[] Customer = getCustomerCount(conn, consignorcode, plandate, tcdc);
			int[] Ticket = getTicketCount(conn, consignorcode, plandate, tcdc);
			int[] Item = getItemCount(conn, consignorcode, plandate, tcdc);
			int[] Consignor = getConsignorCount(conn, consignorcode, plandate, tcdc);
			long[][] qty = getWorkQty(conn, consignorcode, plandate, tcdc);

			// 各作業状態の数量を合計する
			TotalCustomer = Customer[STATUS_UNSTART] + Customer[STATUS_NOWWORKING] + Customer[STATUS_COMPLETE];
			TotalTicket = Ticket[STATUS_UNSTART] + Ticket[STATUS_NOWWORKING] + Ticket[STATUS_COMPLETE];
			TotalItem = Item[STATUS_UNSTART] + Item[STATUS_NOWWORKING] + Item[STATUS_COMPLETE];
			TotalConsignor = Consignor[STATUS_UNSTART] + Consignor[STATUS_NOWWORKING] + Consignor[STATUS_COMPLETE];
			TotalCase = qty[CASE][STATUS_UNSTART] + qty[CASE][STATUS_NOWWORKING] + qty[CASE][STATUS_COMPLETE];
			TotalPiece = qty[PIECE][STATUS_UNSTART] + qty[PIECE][STATUS_NOWWORKING] + qty[PIECE][STATUS_COMPLETE];

			// 未処理についての処理の場合、返却データに未処理分をセットする
			dispData.setUnstartCustomerCount(Customer[STATUS_UNSTART]);
			dispData.setUnstartTicketCount(Ticket[STATUS_UNSTART]);
			dispData.setUnstartItemCount(Item[STATUS_UNSTART]);
			dispData.setUnstartConsignorCount(Consignor[STATUS_UNSTART]);
			dispData.setUnstartCaseCount(qty[CASE][STATUS_UNSTART]);
			dispData.setUnstartPieceCount(qty[PIECE][STATUS_UNSTART]);
			// 作業中についての処理の場合、返却データに作業中分をセットする
			dispData.setNowCustomerCount(Customer[STATUS_NOWWORKING]);
			dispData.setNowTicketCount(Ticket[STATUS_NOWWORKING]);
			dispData.setNowItemCount(Item[STATUS_NOWWORKING]);
			dispData.setNowConsignorCount(Consignor[STATUS_NOWWORKING]);
			dispData.setNowCaseCount(qty[CASE][STATUS_NOWWORKING]);
			dispData.setNowPieceCount(qty[PIECE][STATUS_NOWWORKING]);
			// 完了についての処理の場合、返却データに完了分をセットする
			dispData.setFinishCustomerCount(Customer[STATUS_COMPLETE]);
			dispData.setFinishTicketCount(Ticket[STATUS_COMPLETE]);
			dispData.setFinishItemCount(Item[STATUS_COMPLETE]);
			dispData.setFinishConsignorCount(Consignor[STATUS_COMPLETE]);
			dispData.setFinishCaseCount(qty[CASE][STATUS_COMPLETE]);
			dispData.setFinishPieceCount(qty[PIECE][STATUS_COMPLETE]);
			// 返却データに合計数量をセットする
			dispData.setCustomerTotal(TotalCustomer);
			dispData.setTicketTotal(TotalTicket);
			dispData.setItemTotal(TotalItem);
			dispData.setCaseTotal(TotalCase);
			dispData.setPieceTotal(TotalPiece);
			dispData.setConsignorTotal(TotalConsignor);
			// 返却データに進捗率をセットする
			dispData.setCustomerRate(getRate(Customer[STATUS_COMPLETE], TotalCustomer) + "%");
			dispData.setTicketRate(getRate(Ticket[STATUS_COMPLETE], TotalTicket) + "%");
			dispData.setItemRate(getRate(Item[STATUS_COMPLETE], TotalItem) + "%");
			dispData.setCaseRate(getRate(qty[CASE][STATUS_COMPLETE], TotalCase) + "%");
			dispData.setPieceRate(getRate(qty[PIECE][STATUS_COMPLETE], TotalPiece) + "%");
			dispData.setConsignorRate(getRate(Consignor[STATUS_COMPLETE], TotalConsignor) + "%");

			vec.addElement(dispData);

		}
		
		ShippingWorkingInquiryParameter[] paramArray = null;
		paramArray = new ShippingWorkingInquiryParameter[vec.size()];
		vec.copyInto(paramArray);
		// TC、DC、クロスTCの検索結果から合計(出荷)を求める
		vec.addElement(getSumParam(paramArray));
		paramArray = new ShippingWorkingInquiryParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";

		return paramArray;
	}
	
	/**
	 * 検索条件に一致する荷主コードの件数を、作業状態単位にセットして返します。<BR>
	 * int[0]：未開始<BR>
	 * int[1]：作業中<BR>
	 * int[2]：完了<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      出荷予定日
	 * @param tcdc          ＴＣ/ＤＣ出荷
	 * @return 荷主数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getConsignorCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		// 検索条件をセットする
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ＴＣ/ＤＣ出荷(tcdc=0 ＴＣ出荷、tcdc=1 ＤＣ出荷、tcdc=2 クロスＴＣ出荷)
		if (tcdc == TCDC_TC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_TC);
		}
		else if (tcdc == TCDC_DC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_DC);
		}
		else if (tcdc == TCDC_CROSS)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_CROSSTC);
		}
		// OrderBy
		searchKey.setConsignorCodeOrder(1, true);
		// GroupBy
		searchKey.setConsignorCodeGroup(1);
		searchKey.setStatusFlagGroup(2);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setStatusFlagCollect("");
		
		// 返却値
		int[] returnCount = {0, 0, 0};

		// 対象データがない場合、検索は行いません
		if (shippingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// 検索を行う
		ShippingPlan[] shipping = (ShippingPlan[]) shippingHandler.find(searchKey);

		if (shipping == null || shipping.length <= 0)
		{
			return returnCount;
		}
		
		// 作業区分を調べるために荷主コード単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < shipping.length; i++)
		{
			// 初回の処理
			// 要素を記憶する
			if (i == 0)
			{
				vec.addElement(shipping[i]);
				continue;
			}
			
			// 荷主コードが同じ場合、配列にセットする。
			if (shipping[i].getConsignorCode().equals(shipping[i - 1].getConsignorCode()))
			{
				vec.addElement(shipping[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(shipping[i]);
			}

			// 処理が最後の要素の場合、Vectorにデータをセットする
			if (i == shipping.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素が１つの場合、Vectorにデータをセットしていないので、ここでセットする
		if (shipping.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			ShippingPlan shippingArray[] = new ShippingPlan[child.size()];
			child.copyInto(shippingArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(shippingArray)]++;

		}

		return returnCount;

	}

	/**
	 * 検索条件に一致する出荷先数(出荷先コード)の件数を、作業状態単位にセットして返します。<BR>
	 * int[0]：未開始<BR>
	 * int[1]：作業中<BR>
	 * int[2]：完了<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param consignorcode 荷主コード
	 * @param plandate      出荷予定日
	 * @param tcdc          ＴＣ/ＤＣ出荷
	 * @return 出荷先数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getCustomerCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		// 検索条件をセットする
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ＴＣ/ＤＣ出荷(tcdc=0 ＴＣ出荷、tcdc=1 ＤＣ出荷、tcdc=2 クロスＴＣ出荷)
		if (tcdc == TCDC_TC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_TC);
		}
		else if (tcdc == TCDC_DC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_DC);
		}
		else if (tcdc == TCDC_CROSS)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_CROSSTC);
		}
		// ソート順
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setCustomerCodeOrder(2, true);
		// グループ順
		searchKey.setConsignorCodeGroup(1);
		searchKey.setCustomerCodeGroup(2);
		searchKey.setStatusFlagGroup(3);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setCustomerCodeCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 対象データがない場合、検索は行いません
		if (shippingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// 検索を行う
		ShippingPlan[] shipping = (ShippingPlan[]) shippingHandler.find(searchKey);

		if (shipping == null || shipping.length <= 0)
		{
			return returnCount;
		}
		
		// 作業区分を調べるために荷主コード・出荷先コード単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < shipping.length; i++)
		{
			// 初回のみ処理を行う。
			// 初回の要素を記憶する
			if (i == 0)
			{
				vec.addElement(shipping[i]);
				continue;
			}
			
			// 荷主コードと出荷先コードが同じ場合、配列にセットする。
			if (shipping[i].getConsignorCode().equals(shipping[i - 1].getConsignorCode()) 
				&& shipping[i].getCustomerCode().equals(shipping[i - 1].getCustomerCode()))
			{
				vec.addElement(shipping[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(shipping[i]);
			}

			// 要素の最後のデータの場合、Vectorに要素をセットする
			if (i == shipping.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素が１つの場合、Vectorにデータをセットしていないため、ここでセットする
		if (shipping.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			ShippingPlan shippingArray[] = new ShippingPlan[child.size()];
			child.copyInto(shippingArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(shippingArray)]++;

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
	 * @param plandate      出荷予定日
	 * @param tcdc          ＴＣ/ＤＣ出荷
	 * @return 伝票枚数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getTicketCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		// 検索条件をセットする
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ＴＣ/ＤＣ出荷(tcdc=0 ＴＣ出荷、tcdc=1 ＤＣ出荷、tcdc=2 クロスＴＣ出荷)
		if (tcdc == TCDC_TC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_TC);
		}
		else if (tcdc == TCDC_DC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_DC);
		}
		else if (tcdc == TCDC_CROSS)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_CROSSTC);
		}
		// ソート順
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setCustomerCodeOrder(2, true);
		searchKey.setShippingTicketNoOrder(3, true);
		// グループ順
		searchKey.setConsignorCodeGroup(1);
		searchKey.setCustomerCodeGroup(2);
		searchKey.setShippingTicketNoGroup(3);
		searchKey.setStatusFlagGroup(4);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setCustomerCodeCollect("");
		searchKey.setShippingTicketNoCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 対象データがない場合、検索は行いません
		if (shippingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// 検索を行う
		ShippingPlan[] shipping = (ShippingPlan[]) shippingHandler.find(searchKey);

		if (shipping == null || shipping.length <= 0)
		{
			return returnCount;
		}
		
		// 作業区分を調べるために荷主コード・出荷先コード・伝票No.単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();
		for (int i = 0; i < shipping.length; i++)
		{
			// 初回のみ処理を行う
			// 要素を記憶する
			if (i == 0)
			{
				vec.addElement(shipping[i]);
				continue;
			}
			
			// 荷主コードと出荷先コードと伝票No.が同じ場合、配列にセットする。
			if (shipping[i].getConsignorCode().equals(shipping[i - 1].getConsignorCode()) 
				&& shipping[i].getCustomerCode().equals(shipping[i - 1].getCustomerCode())
				&& shipping[i].getShippingTicketNo().equals(shipping[i - 1].getShippingTicketNo()))
			{
				vec.addElement(shipping[i]);
			}
			else
			{
				// 前回までの要素をVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(shipping[i]);
			}

			// 要素のうち最後のデータの場合、Vectorにデータをセットする
			if (i == shipping.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		// 要素が１つの場合、Vectorにデータをセットしていないのでここでセットする
		if (shipping.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			ShippingPlan shippingArray[] = new ShippingPlan[child.size()];
			child.copyInto(shippingArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(shippingArray)]++;
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
	 * @param plandate      出荷予定日
	 * @param tcdc          ＴＣ/ＤＣ出荷
	 * @return アイテム数を返します。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private int[] getItemCount(Connection conn, String consignorcode, String plandate, int tcdc) throws ReadWriteException
	{
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		// データの検索
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ＴＣ/ＤＣ出荷(tcdc=0 ＴＣ出荷、tcdc=1 ＤＣ出荷、tcdc=2 クロスＴＣ出荷)
		if (tcdc == TCDC_TC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_TC);
		}
		else if (tcdc == TCDC_DC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_DC);
		}
		else if (tcdc == TCDC_CROSS)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_CROSSTC);
		}
		// OrderBy
		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setItemCodeOrder(2, true);
		// GroupBy
		searchKey.setConsignorCodeGroup(1);
		searchKey.setItemCodeGroup(2);
		searchKey.setStatusFlagGroup(3);
		// 取得項目
		searchKey.setConsignorCodeCollect("");
		searchKey.setItemCodeCollect("");
		searchKey.setStatusFlagCollect("");

		// 返却値
		int[] returnCount = {0, 0, 0};

		// 対象データがない場合、検索は行いません
		if (shippingHandler.count(searchKey) <= 0)
		{
			return returnCount;
		}

		// 検索を行う
		ShippingPlan[] shipping = (ShippingPlan[]) shippingHandler.find(searchKey);

		if (shipping == null || shipping.length <= 0)
		{
			return returnCount;
		}
		
		// 作業区分を調べるために荷主コード・商品コード単位で集約を行う
		Vector parentVec = new Vector();
		Vector vec = new Vector();

		for (int i = 0; i < shipping.length; i++)
		{
			// 初回に処理を行う
			// 要素を記憶する
			if (i == 0)
			{
				vec.addElement(shipping[i]);
				continue;
			}
			
			// 荷主コードと商品コードが同じ場合、配列にセットする。
			if (shipping[i].getConsignorCode().equals(shipping[i - 1].getConsignorCode())
				&& shipping[i].getItemCode().equals(shipping[i - 1].getItemCode()))
			{
				vec.addElement(shipping[i]);
			}
			else
			{
				// 前回までのデータをVectorにセットする
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
				
				// 今回の要素を記憶する
				vec.addElement(shipping[i]);
			}

			// 要素の最後のデータの場合、Vectorにデータをセットする
			if (i == shipping.length - 1)
			{
				Vector vec2 = new Vector();
				vec2 = (Vector) vec.clone();
				parentVec.addElement(vec2);
				vec.clear();
			}
		}
		
		// 要素が１つの場合、Vectorにデータをセットしていないため、ここでセットする
		if (shipping.length == 1)
		{
			parentVec.addElement(vec);
		}
		
		// 該当件数を取得する
		for (int i = 0; i < parentVec.size(); i++)
		{
			Vector child = (Vector) parentVec.get(i);
			ShippingPlan shippingArray[] = new ShippingPlan[child.size()];
			child.copyInto(shippingArray);
			// 該当作業状態の件数をカウントアップする
			returnCount[getStatus(shippingArray)]++;

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
		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();

		// 検索条件をセットする
		// 状態フラグ(削除以外)
		searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			searchKey.setConsignorCode(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
		}
		// ＴＣ/ＤＣ出荷(tcdc=0 ＴＣ出荷、tcdc=1 ＤＣ出荷、tcdc=2 クロスＴＣ出荷)
		if (tcdc == TCDC_TC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_TC);
		}
		else if (tcdc == TCDC_DC)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_DC);
		}
		else if (tcdc == TCDC_CROSS)
		{
			searchKey.setTcdcFlag(ShippingPlan.TCDC_FLAG_CROSSTC);
		}
		//情報取得順をセット
		searchKey.setPlanQtyCollect("");
		searchKey.setEnteringQtyCollect("");
		searchKey.setCasePieceFlagCollect("");
		searchKey.setStatusFlagCollect("");
		
		// 返却データ
		long[][] returnQty = {{0, 0, 0}, {0, 0, 0}};
		
		// 該当データがない場合、検索を行わない
		if (shippingHandler.count(searchKey) <= 0)
		{
			return returnQty;
		}

		// 検索を行う
		ShippingPlan[] resultEntity = (ShippingPlan[]) shippingHandler.find(searchKey);

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
			if (!resultEntity[i].getCasePieceFlag().equals(ShippingPlan.CASEPIECE_FLAG_PIECE))
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
	 * 出荷予定情報より、画面上でのどの区分にあたるのかを返すためのメソッドです。<BR>
	 * 出荷予定情報．未開始のみ：未開始<BR>
	 * 出荷予定情報．完了のみ：処理済<BR>
	 * 上記以外：処理中<BR>
	 * 
	 * @param pShipping 予定情報の内容を持つShippingPlanクラスのインスタンス。
	 * @return 作業状態 0：未処理、1：作業中、2：処理済
	 */
	private int getStatus(ShippingPlan[] pShipping)
	{
		// 未開始の存在フラグ
		boolean unstart = false;
		// 作業中の存在フラグ
		boolean working = false;
		// 完了分の存在フラグ
		boolean complete = false;
		
		// 作業状態の存在チェックを行う
		for (int i = 0; i < pShipping.length; i++)
		{
			// 未開始の場合
			if (pShipping[i].getStatusFlag().equals(ShippingPlan.STATUS_FLAG_UNSTART))
			{
				unstart = true;
			}
			// 作業中、一部完了の場合
			else if(pShipping[i].getStatusFlag().equals(ShippingPlan.STATUS_FLAG_NOWWORKING)
							|| pShipping[i].getStatusFlag().equals(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART))
			{
				working = true;
			}
			// 完了の場合
			else if (pShipping[i].getStatusFlag().equals(ShippingPlan.STATUS_FLAG_COMPLETION))
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
	private int getStatus(ShippingPlan pShipping)
	{
		// 未開始の場合
		if (pShipping.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_UNSTART))
		{
			return STATUS_UNSTART;
		}
		// 作業中の場合
		else if(pShipping.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_NOWWORKING)
						|| pShipping.getStatusFlag().equals(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART))
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
	
	/**
	 * パラメータから取得したデータを合計し、出荷状況の合計を求め、返します。<BR>
	 * 
	 * @param paramArray 各TC/DC区分の出荷状況
	 * @return 出荷状況の合計
	 */
	private ShippingWorkingInquiryParameter getSumParam(ShippingWorkingInquiryParameter paramArray[])
	{
		ShippingWorkingInquiryParameter sumParam = new ShippingWorkingInquiryParameter();
		
		// 完了数量
		int FinishCustomer = 0;
		int FinishTicket = 0;
		int FinishItem = 0;
		long FinishCase = 0;
		long FinishPiece = 0;
		int FinishConsignor = 0;

		// 未処理、作業中、処理済について数量を求める
		for (int sumCount = 0; sumCount < paramArray.length; sumCount++)
		{
			// 未処理についての処理の場合、返却データに未処理分をセットする
			sumParam.setUnstartCustomerCount(sumParam.getUnstartCustomerCount() + paramArray[sumCount].getUnstartCustomerCount());
			sumParam.setUnstartTicketCount(sumParam.getUnstartTicketCount() + paramArray[sumCount].getUnstartTicketCount());
			sumParam.setUnstartItemCount(sumParam.getUnstartItemCount() + paramArray[sumCount].getUnstartItemCount());
			sumParam.setUnstartConsignorCount(sumParam.getUnstartConsignorCount() + paramArray[sumCount].getUnstartConsignorCount());
			sumParam.setUnstartCaseCount(sumParam.getUnstartCaseCount() + paramArray[sumCount].getUnstartCaseCount());
			sumParam.setUnstartPieceCount(sumParam.getUnstartPieceCount() + paramArray[sumCount].getUnstartPieceCount());
			
			// 作業中についての処理の場合、返却データに作業中分をセットする
			sumParam.setNowCustomerCount(sumParam.getNowCustomerCount() + paramArray[sumCount].getNowCustomerCount());
			sumParam.setNowTicketCount(sumParam.getNowTicketCount() + paramArray[sumCount].getNowTicketCount());
			sumParam.setNowItemCount(sumParam.getNowItemCount() + paramArray[sumCount].getNowItemCount());
			sumParam.setNowConsignorCount(sumParam.getNowConsignorCount() + paramArray[sumCount].getNowConsignorCount());
			sumParam.setNowCaseCount(sumParam.getNowCaseCount() + paramArray[sumCount].getNowCaseCount());
			sumParam.setNowPieceCount(sumParam.getNowPieceCount() + paramArray[sumCount].getNowPieceCount());
			
			// 処理済の数量を記憶する(進捗率を求めるため)
			FinishCustomer += paramArray[sumCount].getFinishCustomerCount();
			FinishTicket += paramArray[sumCount].getFinishTicketCount();
			FinishItem += paramArray[sumCount].getFinishItemCount();
			FinishConsignor += paramArray[sumCount].getFinishConsignorCount();
			FinishCase += paramArray[sumCount].getFinishCaseCount();
			FinishPiece += paramArray[sumCount].getFinishPieceCount();
			
		}			
		// 完了についての処理の場合、返却データに完了分をセットする
		sumParam.setFinishCustomerCount(FinishCustomer);
		sumParam.setFinishTicketCount(FinishTicket);
		sumParam.setFinishItemCount(FinishItem);
		sumParam.setFinishConsignorCount(FinishConsignor);
		sumParam.setFinishCaseCount(FinishCase);
		sumParam.setFinishPieceCount(FinishPiece);
		
		// 合計数量
		int TotalCustomer = 0;
		int TotalTicket = 0;
		int TotalItem = 0;
		long TotalCase = 0;
		long TotalPiece = 0;
		int TotalConsignor = 0;
		// TC/DC区分ごとの合計数量
		TotalCustomer = sumParam.getUnstartCustomerCount() + sumParam.getNowCustomerCount() + sumParam.getFinishCustomerCount();
		TotalTicket = sumParam.getUnstartTicketCount() + sumParam.getNowTicketCount() + sumParam.getFinishTicketCount();
		TotalItem = sumParam.getUnstartItemCount() + sumParam.getNowItemCount() + sumParam.getFinishItemCount();
		TotalCase = sumParam.getUnstartCaseCount() + sumParam.getNowCaseCount() + sumParam.getFinishCaseCount();
		TotalPiece = sumParam.getUnstartPieceCount() + sumParam.getNowPieceCount() + sumParam.getFinishPieceCount();
		TotalConsignor = sumParam.getUnstartConsignorCount() + sumParam.getNowConsignorCount() + sumParam.getFinishConsignorCount();
		// 返却データに合計数量をセットする
		sumParam.setCustomerTotal(TotalCustomer);
		sumParam.setTicketTotal(TotalTicket);
		sumParam.setItemTotal(TotalItem);
		sumParam.setCaseTotal(TotalCase);
		sumParam.setPieceTotal(TotalPiece);
		sumParam.setConsignorTotal(TotalConsignor);
		
		// 返却データに進捗率をセットする
		sumParam.setCustomerRate(getRate(FinishCustomer, TotalCustomer) + "%");
		sumParam.setTicketRate(getRate(FinishTicket, TotalTicket) + "%");
		sumParam.setItemRate(getRate(FinishItem, TotalItem) + "%");
		sumParam.setCaseRate(getRate(FinishCase, TotalCase) + "%");
		sumParam.setPieceRate(getRate(FinishPiece, TotalPiece) + "%");
		sumParam.setConsignorRate(getRate(FinishConsignor, TotalConsignor) + "%");
		
		return sumParam;
	}

}
//end of class

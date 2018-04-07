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
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 仕分予定照会処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分予定照会処理を行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   仕分予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   状態フラグが未開始、作業中、一部完了、完了<BR>
 *   TCDC区分が『クロス』もしくは『ＤＣ』<BR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   ケースピース区分、クロスＤＣ区分、出荷先コードの順に表示を行います。<BR>
 *   検索するテーブルは仕分予定テーブル(DNSORTINGPLAN)。<BR>
 *   検索対象が1000件(WMSParamに定義されたMAX_NUMBER_OF_DISP)を超えた場合、表示は行いません。<BR>
 *   リストセルのヘッダに表示する荷主名称は登録日時の新しい値を取得します。<BR>
 *   <BR>
 *   [検索条件] <BR> 
 *   <BR>
 *   状態フラグが未開始、作業中、一部完了、完了<BR>
 *   TCDC区分が『クロス』もしくは『ＤＣ』<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   荷主コード* : ConsignorCode <BR>
 *   仕分予定日* : PlanDate <BR>
 *   商品コード* : ItemCode <BR>
 *   ケースピース区分 : CasePieceFlag <BR>
 *   クロス／ＤＣ区分 : TcdcFlag <BR>
 *   状態フラグ* : StatusFlag <BR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR>
 *   荷主コード : ConsignorCode <BR>
 *   荷主名称 : ConsignorName <BR>
 *   仕分予定日 : PlanDate <BR>
 *   商品コード : ItemCode <BR>
 *   商品名称 : ItemName <BR>
 *   ケースピース区分 : CasePieceFlag <BR>
 *   クロスＤＣ区分 : TcdcFlag <BR>
 *   出荷先コード : CustomerCode <BR>
 *   出荷先名称 : CustomerName <BR>
 *   ケース入数 : EnteringQty <BR>
 *   ボール入数 : BundleEnteringQty <BR>
 *   ホスト予定ケース数 : PlanCaseQty <BR>
 *   ホスト予定ピース数 : PlanPieceQty<BR>
 *   実績ケース数 : ResultCaseQty <BR>
 *   実績ピース数 : ResultPieceQty<BR>
 *   作業状態 : StatusFlag <BR>
 *   状態名称 : StatusName <BR>
 *   ケース仕分場所 : CaseSortingLocation <BR>
 *   ピース仕分場所 : PieceSortingLocation <BR>
 *   仕入先コード : SupplierCode <BR>
 *   仕入先名称 : SupplierName <BR>
 *   ケースITF : ITF <BR>
 *   ボールITF : BundleITF <BR>
 *   出荷伝票No : ShippingTicketNo <BR>
 *   出荷伝票行No : ShippingLineNo <BR>
 *   入荷伝票No : InstockTicketNo <BR>
 *   入荷伝票行No : InstockLineNo <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/4</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingPlanInquirySCH extends AbstractSortingSCH
{

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public SortingPlanInquirySCH()
	{
		wMessage = null;
	}

	// Public methods -----------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 仕分予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param locale 地域コードがセットされた<CODE>Locale</CODE>オブジェクト 
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		SortingPlanHandler sortingHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey searchKey = new SortingPlanSearchKey();

		// データの検索
		// 状態フラグ(未開始、作業中、一部完了、完了) 
		String[] statusflg = { SortingPlan.STATUS_FLAG_UNSTART, SortingPlan.STATUS_FLAG_NOWWORKING, SortingPlan.STATUS_FLAG_COMPLETE_IN_PART, SortingPlan.STATUS_FLAG_COMPLETION };
		searchKey.setStatusFlag(statusflg);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		SortingParameter dispData = new SortingParameter();

		if (sortingHandler.count(searchKey) == 1)
		{
			try
			{
				SortingPlan working = (SortingPlan)sortingHandler.findPrimary(searchKey);
				dispData.setConsignorCode(working.getConsignorCode());
			}
			catch (NoPrimaryException e)
			{
				return new SortingParameter();
			}
		}
		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param locale 地域コード、画面表示用にローカライズした値を取得するために使用します。
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

		SortingParameter param = (SortingParameter)searchParam;

		SortingPlanHandler sortingHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey searchKey = new SortingPlanSearchKey();
		SortingPlanSearchKey namesearchKey = new SortingPlanSearchKey();

		// データの検索
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());

		// 仕分予定日
		String plandate = param.getPlanDate();
		if (!StringUtil.isBlank(plandate))
		{
			searchKey.setPlanDate(plandate);
			namesearchKey.setPlanDate(plandate);
		}

		// 商品コード
		String itemcode = param.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}

		//ケース/ピース区分
		if(!StringUtil.isBlank(param.getCasePieceFlag()))
		{
			if(param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
			{
				//ケース区分
				searchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE) ;
				namesearchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE) ;
			}
			else if(param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
			{
				//ピース区分
				searchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE) ;
				namesearchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE) ;
			}
			else if(param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
			{ 
				//ケース・ピース
				searchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_MIX) ;
				namesearchKey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_MIX) ;
			}
		}
		
		//クロス/ＤＣ
		if(!StringUtil.isBlank(param.getTcdcFlag()))
		{
			if(param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
			{
				//クロスＴＣ
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC) ;
				namesearchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC) ;
			}
			else if(param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
			{
				//ＤＣ
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC) ;
				namesearchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC) ;
			}
			else if(param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_ALL))
			{
				//TC 以外
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "<>") ;
				namesearchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "<>") ;
			}
		}
		
		// 状態フラグ(パラメータから全て、未開始、作業中、一部完了、完了の何れかを取得する。)
		if (!param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_ALL))
		{
			// 未開始
			if(param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
			{
				searchKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
				namesearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
			}
			// 作業中
			else if(param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_WORKING))
			{
				searchKey.setStatusFlag(SortingPlan.STATUS_FLAG_NOWWORKING);
				namesearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_NOWWORKING);
			}
			// 一部完了
			else if(param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				searchKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART);
				namesearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			// 完了
			else if(param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_COMPLETION))
			{
				searchKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
				namesearchKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
			}
		}
		// 全て
		else
		{
			String[] statusflg = { SortingPlan.STATUS_FLAG_UNSTART, SortingPlan.STATUS_FLAG_NOWWORKING, SortingPlan.STATUS_FLAG_COMPLETE_IN_PART, SortingPlan.STATUS_FLAG_COMPLETION };
			searchKey.setStatusFlag(statusflg);
			namesearchKey.setStatusFlag(statusflg);
		}

		// ケースピース区分、クロスＤＣ区分、出荷先コード、ケース仕分場所でソート(クロス/DCのみ降順) 
		searchKey.setCasePieceFlagOrder(1, true);
		searchKey.setTcdcFlagOrder(2, false);
		searchKey.setCustomerCodeOrder(3, true);
		searchKey.setCaseLocationOrder(4,true);
		
		// ためうちエリアに対象データを表示できるかチェック
		if(!canLowerDisplay(sortingHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		
		SortingPlan[] resultEntity = (SortingPlan[])sortingHandler.find(searchKey);

		// 登録日時の新しい荷主名称と出荷先名称を取得します。
		namesearchKey.setRegistDateOrder(1, false);
		SortingPlan[] sorting = (SortingPlan[])sortingHandler.find(namesearchKey);
		String consignorname = "";
		if(sorting != null && sorting.length != 0)
		{
			consignorname = sorting[0].getConsignorName();
		}	

		Vector vec = new Vector();

		for (int loop = 0; loop < resultEntity.length; loop++)
		{
			SortingParameter dispData = new SortingParameter();
			
			// 荷主コード
			dispData.setConsignorCode(resultEntity[loop].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称)
			dispData.setConsignorName(consignorname);
			// 仕分予定日
			dispData.setPlanDate(resultEntity[loop].getPlanDate());
			// 商品コード
			dispData.setItemCode(resultEntity[loop].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[loop].getItemName1());
			// ケースピース区分
			dispData.setCasePieceFlag(DisplayUtil.getPieceCaseValue(resultEntity[loop].getCasePieceFlag()));
			// クロス／ＤＣ区分
			dispData.setTcdcFlag(DisplayUtil.getTcDcValue(resultEntity[loop].getTcdcFlag()));
			// 出荷先コード
			dispData.setCustomerCode(resultEntity[loop].getCustomerCode());
			// 出荷先名称
			dispData.setCustomerName(resultEntity[loop].getCustomerName1());			
			// ケース入数
			dispData.setEnteringQty(resultEntity[loop].getEnteringQty());
			// ボール入数
			dispData.setBundleEnteringQty(resultEntity[loop].getBundleEnteringQty());
			// ホスト予定ケース数
			// 出荷予定数をケースで割った商がケース数になります。
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getPlanQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getCasePieceFlag()));
			// ホスト予定ピース数
			// 出荷予定数をケースで割った余りがピース数になります。
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getPlanQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getCasePieceFlag()));
			// 実績ケース数
			// 出荷実績数をケースで割った商がケース数になります。
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getCasePieceFlag()));
			// 実績ピース数
			// 出荷実績数をケースで割った余りがピース数になります。
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(),resultEntity[loop].getCasePieceFlag()));
			// 作業状態名称
			// 状態フラグから状態名称を取得します。
			String statusname = DisplayUtil.getSortingPlanStatusValue(resultEntity[loop].getStatusFlag());
			dispData.setStatusName(statusname);
			// ケース仕分場所
			dispData.setCaseSortingLocation(resultEntity[loop].getCaseLocation());
			// ピース仕分場所
			dispData.setPieceSortingLocation(resultEntity[loop].getPieceLocation());
			// 仕入先コード
			dispData.setSupplierCode(resultEntity[loop].getSupplierCode());
			// 仕入先名称
			dispData.setSupplierName(resultEntity[loop].getSupplierName1());
			// ケースＩＴＦ
			dispData.setITF(resultEntity[loop].getItf());
			// ボールＩＴＦ
			dispData.setBundleITF(resultEntity[loop].getBundleItf());
			// 出荷伝票No
			dispData.setShippingTicketNo(resultEntity[loop].getShippingTicketNo());
			// 出荷伝票行No
			dispData.setShippingLineNo(resultEntity[loop].getShippingLineNo());
			// 入荷伝票No
			dispData.setInstockTicketNo(resultEntity[loop].getInstockTicketNo());
			// 入荷伝票行No
			dispData.setInstockLineNo(resultEntity[loop].getInstockLineNo());
			
			vec.addElement(dispData);
		}

		SortingParameter[] paramArray = new SortingParameter[vec.size()];

		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

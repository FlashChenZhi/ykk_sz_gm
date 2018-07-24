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
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * クロス／ＤＣ 入荷予定照会処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、クロス／ＤＣ 入荷予定照会処理を行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
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
 *   入荷予定日、仕入先コード、伝票No、行No、商品コードの順に表示を行います。<BR>
 *   検索するテーブルは入荷予定テーブル(DNINSTOCKPLAN)。<BR>
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
 *   開始入荷予定日 : FromPlanDate <BR>
 *   終了入荷予定日 : ToPlanDate<BR>
 *   仕入先コード : SupplierCode <BR>
 *   開始伝票No : FromTicketNo <BR>
 *   終了伝票No : ToTicketNo <BR>
 *   商品コード : ItemCode <BR>
 *   クロス／ＤＣ区分 : TcdcFlag <BR>
 *   状態フラグ* : StatusFlag <BR>
 *   <BR>
 *   [返却データ] <BR>
 *   <BR>
 *   荷主コード : ConsignorCode <BR>
 *   荷主名称 : ConsignorName <BR>
 *   入荷予定日 : PlanDate <BR>
 *   仕入先コード : SupplierCode <BR>
 *   仕入先名称 : SupplierName <BR>
 *   伝票No : InstockTicketNo <BR>
 *   行No : InstockLineNo <BR>
 *   商品コード : ItemCode <BR>
 *   商品名称 : ItemName <BR>
 *   ケース入数 : EnteringQty <BR>
 *   ボール入数 : BundleEnteringQty <BR>
 *   ホスト予定ケース数 : PlanCaseQty <BR>
 *   ホスト予定ピース数 : PlanPieceQty <BR>
 *   実績ケース数 : ResultCaseQty <BR>
 *   実績ピース数 : ResultPieceQty <BR>
 *   クロス／ＤＣ区分 : TcdcFlag <BR>
 *   作業状態 : StatusFlag <BR>
 *   状態名称 : StatusName <BR>
 *   ケースITF : ITF <BR>
 *   ボールITF : BundleITF <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/1</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockReceivePlanInquirySCH extends AbstractInstockReceiveSCH
{

	// Class variables -----------------------------------------------

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
	public InstockReceivePlanInquirySCH()
	{
		wMessage = null;
	}

	// Public methods -----------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * 検索条件を必要としない場合、<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		InstockPlanReportFinder instockFinder = new InstockPlanReportFinder(conn);
		InstockPlan[] wInstock = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 状態フラグ(未開始、作業中、一部完了、完了) 
			String[] statusflg = { InstockPlan.STATUS_FLAG_UNSTART, InstockPlan.STATUS_FLAG_NOWWORKING, InstockPlan.STATUS_FLAG_COMPLETE_IN_PART, InstockPlan.STATUS_FLAG_COMPLETION };
			searchKey.setStatusFlag(statusflg);
			// ＴＣＤＣフラグ＝『クロス』もしくは『ＤＣ』
			String[] tcdcflg = { SystemDefine.TCDC_FLAG_CROSSTC, SystemDefine.TCDC_FLAG_DC };
			searchKey.setTcdcFlag(tcdcflg);
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (instockFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// データの検索
				// 状態フラグ(未開始、作業中、一部完了、完了) 
				searchKey.setStatusFlag(statusflg);
				// ＴＣＤＣフラグ＝『クロス』もしくは『ＤＣ』
				searchKey.setTcdcFlag(tcdcflg);
				// ソート順に登録日時を設定
				searchKey.setRegistDateOrder(1, false);
				
				searchKey.setConsignorNameCollect("");
				searchKey.setConsignorCodeCollect("");

				if (instockFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
					wInstock = (InstockPlan[]) instockFinder.getEntities(1);
					wParam.setConsignorName(wInstock[0].getConsignorName());
					wParam.setConsignorCode(wInstock[0].getConsignorCode());
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
		InstockReceiveParameter param = (InstockReceiveParameter)searchParam;
		if (!check(conn, param))
		{
			return null;
		}

		InstockPlanHandler instockHandler = new InstockPlanHandler(conn);
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		InstockPlanSearchKey namesearchKey = new InstockPlanSearchKey();

		// データの検索
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());
		
		// 開始入荷受付日、終了入荷受付日
		String fromplandate = param.getFromPlanDate();
		String toplandate = param.getToPlanDate();
		if (!StringUtil.isBlank(fromplandate))
		{
			searchKey.setPlanDate(fromplandate, ">=");
			namesearchKey.setPlanDate(fromplandate, ">=");
		}
		if (!StringUtil.isBlank(toplandate))
		{
			searchKey.setPlanDate(toplandate, "<=");
			namesearchKey.setPlanDate(toplandate, "<=");
		}
		
		// 仕入先コード
		String suppliercode = param.getSupplierCode();
		if (!StringUtil.isBlank(suppliercode))
		{
			searchKey.setSupplierCode(suppliercode);
			namesearchKey.setSupplierCode(suppliercode);			
		}

		// 開始伝票No、終了伝票No
		String fromticketno = param.getFromTicketNo();
		String toticketno = param.getToTicketNo();
		if (!StringUtil.isBlank(fromticketno))
		{
			searchKey.setInstockTicketNo(fromticketno, ">=");
			namesearchKey.setInstockTicketNo(fromticketno, ">=");
		}
		if (!StringUtil.isBlank(toticketno))
		{
			searchKey.setInstockTicketNo(toticketno, "<=");
			namesearchKey.setInstockTicketNo(toticketno, "<=");
		}
		
		// 商品コード
		String itemcode = param.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}

		// クロス／ＤＣフラグ（クロスもしくはＤＣ）
		if (!param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_ALL))
		{
			// クロス
			if(param.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
			{
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC);
				namesearchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_CROSSTC);
			}
			// ＤＣ
			else
			{
				searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
				namesearchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
			}
		}
		// クロス／ＤＣフラグ（全て）
		else
		{
			String[] tcdcflg = { SystemDefine.TCDC_FLAG_CROSSTC, SystemDefine.TCDC_FLAG_DC };
			searchKey.setTcdcFlag(tcdcflg);
			namesearchKey.setTcdcFlag(tcdcflg);
		}

		// 状態フラグ(パラメータから全て、未開始、作業中、保留(一部完了)、完了の何れかを取得する。)
		if (!param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_ALL))
		{
			// 未開始
			if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
			{
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
				namesearchKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
			}
			// 作業中
			else if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
			{
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_NOWWORKING);
				namesearchKey.setStatusFlag(InstockPlan.STATUS_FLAG_NOWWORKING);
			}
			// 保留(一部完了)
			else if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART);
				namesearchKey.setStatusFlag(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			// 完了
			else if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
			{
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_COMPLETION);
				namesearchKey.setStatusFlag(InstockPlan.STATUS_FLAG_COMPLETION);
			}
		}
		// 全て
		else
		{
			String[] statusflg = { InstockPlan.STATUS_FLAG_UNSTART, InstockPlan.STATUS_FLAG_NOWWORKING, InstockPlan.STATUS_FLAG_COMPLETE_IN_PART, InstockPlan.STATUS_FLAG_COMPLETION };
			searchKey.setStatusFlag(statusflg);
			namesearchKey.setStatusFlag(statusflg);
		}

		// 入荷予定日、仕入先コード、伝票No、行No、商品コードの昇順でソート 
		searchKey.setPlanDateOrder(1, true);
		searchKey.setSupplierCodeOrder(2, true);
		searchKey.setInstockTicketNoOrder(3, true);
		searchKey.setInstockLineNoOrder(4, true);
		searchKey.setItemCodeOrder(5, true);
		
		// 表示件数チェックを行います
		if (!canLowerDisplay(instockHandler.count(searchKey)))
		{
			return returnNoDisplayParameter();
		}
		
		InstockPlan[] resultEntity = (InstockPlan[])instockHandler.find(searchKey);

		// 登録日時の新しい荷主名称を取得します。
		namesearchKey.setRegistDateOrder(1, false);
		InstockPlan[] instock = (InstockPlan[])instockHandler.find(namesearchKey);
		String consignorname = "";
		if(instock != null && instock.length != 0)
		{
			consignorname = instock[0].getConsignorName();
		}	

		Vector vec = new Vector();

		// 画面下部詳細情報
		for (int loop = 0; loop < resultEntity.length; loop++)
		{
			InstockReceiveParameter dispData = new InstockReceiveParameter();
			// 荷主コード
			dispData.setConsignorCode(resultEntity[loop].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称)
			dispData.setConsignorName(consignorname);
			// 入荷予定日
			dispData.setPlanDate(resultEntity[loop].getPlanDate());
			// 仕入先コード
			dispData.setSupplierCode(resultEntity[loop].getSupplierCode());
			// 仕入先名称
			dispData.setSupplierName(resultEntity[loop].getSupplierName1());
			// 伝票No
			dispData.setInstockTicketNo(resultEntity[loop].getInstockTicketNo());
			// 行No
			dispData.setInstockLineNo(resultEntity[loop].getInstockLineNo());
			// 商品コード
			dispData.setItemCode(resultEntity[loop].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[loop].getItemName1());
			// ケース入数
			dispData.setEnteringQty(resultEntity[loop].getEnteringQty());
			// ボール入数
			dispData.setBundleEnteringQty(resultEntity[loop].getBundleEnteringQty());
			// ホスト予定ケース数
			// 出荷予定数をケースで割った商がケース数になります。
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getPlanQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getCasePieceFlag()));
			// ホスト予定ピース数
			// 出荷予定数をケースで割った余りがピース数になります。
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getPlanQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getCasePieceFlag()));
			// 実績ケース数
			// 出荷実績数をケースで割った商がケース数になります。
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getCasePieceFlag()));
			// 実績ピース数
			// 出荷実績数をケースで割った余りがピース数になります。
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[loop].getResultQty(), resultEntity[loop].getEnteringQty(), resultEntity[loop].getCasePieceFlag()));
			// クロス／ＤＣ区分
			dispData.setTcdcFlag(DisplayUtil.getTcDcValue(resultEntity[loop].getTcdcFlag()));
			// 作業状態名称
			// 状態フラグから状態名称を取得します。
			String statusname = DisplayUtil.getInstockPlanStatusValue(resultEntity[loop].getStatusFlag());
			dispData.setStatusName(statusname);
			// ケースITF
			dispData.setITF(resultEntity[loop].getItf());
			// ボールITF
			dispData.setBundleITF(resultEntity[loop].getBundleItf());
			vec.addElement(dispData);
		}

		InstockReceiveParameter[] paramArray = new InstockReceiveParameter[vec.size()];

		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

	/** 
	 * 入力エリアの入力チェックを行います。<BR>
	 * 正常の時はtrue、異常の時はfalseを返します。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 入力されたデータを持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 * @return 入力チェックの結果を返します。正常はtrue、異常はfalse<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。<BR>
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter param = (InstockReceiveParameter)searchParam;
		
		// パラメータから開始入荷予定日と終了入荷予定日を取得する。
		String fromplandate = param.getFromPlanDate();
		String toplandate = param.getToPlanDate();
		
		// 入荷予定日 大小チェック
		if (!StringUtil.isBlank(fromplandate) && !StringUtil.isBlank(toplandate))
		{
			if (fromplandate.compareTo(toplandate) > 0)
			{
				// 6023036 = 開始入荷予定日は、終了入荷予定日より前の日付にしてください。
				wMessage = "6023036";
				return false;
			}
		}
		
		// パラメータから開始伝票Noと終了伝票Noを取得する。
		String fromticketno = param.getFromTicketNo();
		String toticketno = param.getToTicketNo();
		
		// 伝票No 大小チェック
		if (!StringUtil.isBlank(fromticketno) && !StringUtil.isBlank(toticketno))
		{
			if (fromticketno.compareTo(toticketno) > 0)
			{
				// 6023037 = 開始伝票Noは、終了伝票Noより前の値にしてください。
				wMessage = "6023037";
				return false;
			}
		}

		return true;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

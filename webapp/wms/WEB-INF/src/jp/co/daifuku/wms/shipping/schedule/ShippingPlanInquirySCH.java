//$Id: ShippingPlanInquirySCH.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
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
 * 出荷予定照会処理を行うためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷予定照会処理を行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR> 
 * <BR>
 * <DIR>
 *   出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR> 
 *   <BR>
 *   [検索条件] <BR> 
 *   <DIR>
 *   状態フラグが未開始、作業中、一部完了、完了<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>query()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を返します。また、条件エラーなどが発生した場合はnullを返します。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   出荷予定日、出荷先コード、出荷伝票No.、出荷伝票行No.、商品コード、状態の順に表示を行います。 <BR>
 *   検索するテーブルは出荷予定テーブル(DNSHIPPINGPLAN)。<BR>
 *   検索対象が1000件(WMSParamに定義されたMAX_NUMBER_OF_DISP)を超えた場合、表示は行いません。<BR>
 *   リストセルのヘッダに表示する荷主名称と出荷先名称は登録日時の新しい値を取得します。<BR>
 *   <BR>
 *   [検索条件] <BR> 
 *   <DIR>
 *   状態フラグが未開始、作業中、一部完了、完了<BR>
 *   </DIR>
 *   [パラメータ] *必須入力<BR>
 *   <DIR>
 *   荷主コード* <BR>
 *   開始出荷予定日 <BR>
 *   終了出荷予定日 <BR>
 *   出荷先コード <BR>
 *   開始伝票No.<BR>
 *   終了伝票No.<BR>
 *   商品コード <BR>
 *   状態フラグ* <BR>
 *   </DIR>
 *   [返却データ] <BR>
 *   <DIR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   出荷予定日 <BR>
 *   出荷先コード <BR>
 *   出荷先名称 <BR>
 *   出荷伝票No. <BR>
 *   出荷伝票行No. <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   ホスト予定ケース数 <BR>
 *   ホスト予定ピース数 <BR>
 *   実績ケース数 <BR>
 *   実績ピース数 <BR>
 *   状態 <BR>
 *   仕入先コード <BR>
 *   仕入先名称 <BR>
 *   入荷伝票No. <BR>
 *   入荷伝票行No. <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingPlanInquirySCH extends AbstractShippingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingPlanInquirySCH()
	{
		wMessage = null;
	}
	
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 出荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
	    ShippingPlanReportFinder sRFinder = new ShippingPlanReportFinder(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();
		// データの検索
		// 状態フラグ(未開始、作業中、一部完了、完了)
		String[] statusflg = { ShippingPlan.STATUS_FLAG_UNSTART, ShippingPlan.STATUS_FLAG_NOWWORKING, ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART, ShippingPlan.STATUS_FLAG_COMPLETION };
		searchKey.setStatusFlag(statusflg);
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		ShippingParameter dispData = new ShippingParameter();
		
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
	 * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>StorageSupportParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
	 * @return 検索結果を持つ<CODE>StorageSupportParameter</CODE>インスタンスの配列。<BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
	 *          検索結果が1000件を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
	 *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException,ScheduleException 
	{
		ShippingParameter param = (ShippingParameter)searchParam;

		ShippingPlanHandler shippingHandler = new ShippingPlanHandler(conn);
		ShippingPlanSearchKey searchKey = new ShippingPlanSearchKey();
		ShippingPlanSearchKey namesearchKey = new ShippingPlanSearchKey();
		
		// データの検索
		// 荷主コード
		searchKey.setConsignorCode(param.getConsignorCode());
		namesearchKey.setConsignorCode(param.getConsignorCode());
		// 開始出荷予定日、終了出荷予定日
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
		// 出荷先コード
		String customercode = param.getCustomerCode();
		if (!StringUtil.isBlank(customercode))
		{
			searchKey.setCustomerCode(customercode);
			namesearchKey.setCustomerCode(customercode);
		}
		// 開始伝票No.、終了伝票No.
		String fromticketno = param.getFromTicketNo();
		String toticketno = param.getToTicketNo();
		if (!StringUtil.isBlank(fromticketno))
		{
			searchKey.setShippingTicketNo(fromticketno, ">=");
			namesearchKey.setShippingTicketNo(fromticketno, ">=");
		}
		if (!StringUtil.isBlank(toticketno))
		{
			searchKey.setShippingTicketNo(toticketno, "<=");
			namesearchKey.setShippingTicketNo(toticketno, "<=");
		}
		// 商品コード
		String itemcode = param.getItemCode();
		if (!StringUtil.isBlank(itemcode))
		{
			searchKey.setItemCode(itemcode);
			namesearchKey.setItemCode(itemcode);
		}
		// 状態フラグ(パラメータから全て、未開始、作業中、保留(一部完了)、完了の何れかを取得する。)
		if (!param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_ALL))
		{
			// 未開始
			if(param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_UNSTARTING))
			{
				searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
				namesearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_UNSTART);
			}
			// 作業中
			else if(param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_NOWWORKING))
			{
				searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_NOWWORKING);
				namesearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_NOWWORKING);
			}
			// 保留(一部完了)
			else if(param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART))
			{
				searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART);
				namesearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			// 完了
			else if(param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_FINISH))
			{
				searchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETION);
				namesearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETION);
			}
		}
		// 全て
		else
		{
			String[] statusflg = { ShippingPlan.STATUS_FLAG_UNSTART, ShippingPlan.STATUS_FLAG_NOWWORKING, ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART, ShippingPlan.STATUS_FLAG_COMPLETION };
			searchKey.setStatusFlag(statusflg);
			namesearchKey.setStatusFlag(statusflg);
		}
		// 出荷予定日、出荷先コード、出荷伝票No.、出荷伝票行No.、商品コード、状態の昇順でソート
		searchKey.setPlanDateOrder(1, true);
		searchKey.setCustomerCodeOrder(2, true);
		searchKey.setShippingTicketNoOrder(3, true);
		searchKey.setShippingLineNoOrder(4, true);
		searchKey.setItemCodeOrder(5, true);
		searchKey.setStatusFlagOrder(6, true);

		if(!super.canLowerDisplay(shippingHandler.count(searchKey)))
		{
			return super.returnNoDiaplayParameter();
		}
		ShippingPlan[] resultEntity = (ShippingPlan[])shippingHandler.find(searchKey);
		

		// 登録日時の新しい荷主名称と出荷先名称を取得します。
		namesearchKey.setRegistDateOrder(1, false);
		ShippingPlan[] shipping = (ShippingPlan[])shippingHandler.find(namesearchKey);
		String consignorname = "";
		if(shipping != null && shipping.length != 0)
		{
			consignorname = shipping[0].getConsignorName();
		}	

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			ShippingParameter dispData = new ShippingParameter();
			// 荷主コード
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			// 荷主名称(登録日時の新しい荷主名称)
			dispData.setConsignorName(consignorname);
			// 出荷予定日
			dispData.setPlanDate(resultEntity[i].getPlanDate());
			// 出荷先コード
			dispData.setCustomerCode(resultEntity[i].getCustomerCode());
			// 出荷先名称
			dispData.setCustomerName(resultEntity[i].getCustomerName1());
			// 出荷伝票No.
			dispData.setShippingTicketNo(resultEntity[i].getShippingTicketNo());
			// 出荷伝票行No.
			dispData.setShippingLineNo(resultEntity[i].getShippingLineNo());
			// 商品コード
			dispData.setItemCode(resultEntity[i].getItemCode());
			// 商品名称
			dispData.setItemName(resultEntity[i].getItemName1());
			// ケース入数
			dispData.setEnteringQty(resultEntity[i].getEnteringQty());
			// ボール入数
			dispData.setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());
			// ホスト予定ケース数
			// 出荷予定数をケース入数で割った商がケース数になります。
			dispData.setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanQty(), resultEntity[i].getEnteringQty()));
			// ホスト予定ピース数
			// 出荷予定数をケース入数で割った余りがピース数になります。
			dispData.setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanQty(), resultEntity[i].getEnteringQty()));
			// 実績ケース数
			// 出荷実績数をケース入数で割った商がケース数になります。
			dispData.setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty()));
			// 実績ピース数
			// 出荷実績数をケース入数で割った余りがピース数になります。
			dispData.setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty()));
			// 作業状態名称
			// 状態フラグから状態名称を取得します。
			String statusname = DisplayUtil.getShippingPlanStatusValue(resultEntity[i].getStatusFlag());
			dispData.setStatusName(statusname);
			// 仕入先コード
			dispData.setSupplierCode(resultEntity[i].getSupplierCode());
			// 仕入先名称
			dispData.setSupplierName(resultEntity[i].getSupplierName1());
			// 入荷伝票No.
			dispData.setInstockTicketNo(resultEntity[i].getInstockTicketNo());
			// 入荷伝票行No.
			dispData.setInstockLineNo(resultEntity[i].getInstockLineNo());
			// ケースITF
			dispData.setITF(resultEntity[i].getItf());
			// ボールITF
			dispData.setBundleITF(resultEntity[i].getBundleItf());

			vec.addElement(dispData);
		}

		ShippingParameter[] paramArray = new ShippingParameter[vec.size()];
		vec.copyInto(paramArray);

		// 6001013 = 表示しました。
		wMessage = "6001013";
		return paramArray;
	}

}
//end of class

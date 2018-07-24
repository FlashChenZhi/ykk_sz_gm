//$Id: ShippingPlanListSCH.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.shipping.report.ShippingPlanWriter;

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 *
 * <CODE>ShippingPlanListSCH</CODE>クラスは、出荷予定情報のリスト発行を行います。<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷予定リスト発行処理を行います。<BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)
 * <DIR>
 * 	状態が削除以外の荷主コードを出荷予定情報から検索し、同一の荷主コードしか存在しない場合に、<BR>
 * 	荷主コードを返します。<BR>
 * 	荷主コードが複数存在する場合は、nullを返します。<BR>
 * </DIR>
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>startSCH()</CODE>メソッド)<BR>
 * <DIR>
 * 	画面から入力された内容をパラメータとして受け取り、それを条件に出荷予定リストを発行します。<BR>
 * 	処理が正常に完了した場合はtrueを返します。<BR>
 * 	リスト発行中にエラーが発生した場合はfalseを返します。<BR>
 * 	エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 * 	＜パラメータ＞ *必須入力<BR>
 * <DIR>
 * 	荷主コード*<BR>
 * 	開始出荷予定日<BR>
 * 	終了出荷予定日<BR>
 * 	出荷先コード<BR>
 * 	伝票No.<BR>
 * 	商品コード<BR>
 * </DIR>
 * 	＜印刷処理＞<BR>
 * <DIR>
 * 	1.パラメータにセットされた値を<CODE>ShippingPlanWriter</CODE>クラスにセットします。<BR>
 * 	2.<CODE>ShippingPlanWriter</CODE>クラスを使用して、出荷予定リストを発行します。<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>K.Matsuda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingPlanListSCH extends AbstractShippingSCH
{
	// Class variables -----------------------------------------------
	/**
	 * メッセージ保持エリア<BR>
	 * 各メソッドの呼び出しで条件エラー等が発生した場合にその内容を保持するために使用します。
	 */
    protected String wDelim = MessageResource.DELIM;

	// Class method --------------------------------------------------

	/**
	 * 初期表示処理を行います。<BR>
	 * 状態が削除以外の荷主コードを出荷予定情報から検索し、同一の荷主コードしか存在しない場合に、<BR>
	 * <CODE>ShippingParameter</CODE>クラスに荷主コードをセットします。<BR>
	 * @param	conn データベースとのコネクションオブジェクト<BR>
	 * @param	searchParam <CODE>ShippingParameter</CODE>クラス<BR>
	 * @return	検索結果が含まれた<CODE>ShippingParameter</CODE>クラス<BR>
	 * @throws	ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws	ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。<BR>
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		// ParameterをShippingParameterに変換
		ShippingParameter shippingParameter = new ShippingParameter();
		//ReportFinder instance
		ShippingPlanReportFinder sRFinder = new ShippingPlanReportFinder(conn) ;
		 
		// Handler類のインスタンス生成
		ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey();
		
		// 検索条件のセット
		shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		shippingPlanSearchKey.setConsignorCodeCollect("");
		shippingPlanSearchKey.setConsignorCodeGroup(1);
		
		try
		{
		    int count = sRFinder.search(shippingPlanSearchKey) ;
		    ShippingPlan[] sPlan = null ;
		    if(count == 1)
		    {
		        sPlan = (ShippingPlan[])sRFinder.getEntities(1) ;
		        shippingParameter.setConsignorCode(sPlan[0].getConsignorCode());
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
		    // 必ずCollectionをクローズする
			sRFinder.close();
		}		
		return shippingParameter;
	}
	/**
	 * 画面から入力された内容をパラメータとして受け取り、印刷処理を行います。
	 * 印刷処理が成功した場合はtrueを返します。<BR>
	 * 条件エラーなどで印刷処理が失敗した場合はfalseを返します。<BR>
	 * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
	 * @param	conn		データベースとのコネクションオブジェクト
	 * @param	startParams	画面からの入力内容
	 * @return	スケジュール処理が正常に完了した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 * @throws	ReadWriteException	データベースとの接続で異常が発生した場合に通知されます。
	 * @throws	ScheduleException	スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		ShippingParameter shippingParameter = null;
		
		try
		{
			// パラメータは1件しか渡されないはずなので先頭データを取得する。
			shippingParameter = (ShippingParameter) startParams[0];
		}
		catch(Exception e)
		{
			doScheduleExceptionHandling(this.getClass().getName());
		}
		
		// 印刷クラスを作成します
		ShippingPlanWriter writer = createWriter(conn, shippingParameter);
		// 印刷結果ごとにメッセージをセット
		if(writer.startPrint())
		{
			wMessage = "6001010";
			return true;
		}
		else
		{
			wMessage = writer.getMessage();
			return false;
		}

	}
	
	/** 
	 * 画面から入力された情報をもとに、印刷対象の件数を取得します。<BR>
	 * 対象データがなかった場合、入力エラーがあった場合は0件を返します。<BR>
	 * 0件だった場合、呼び出し元の処理にて<CODE>getMessage</CODE>を使用し、
	 * エラーメッセージを取得してください。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param countParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
	 * @return 印刷対象の件数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		ShippingParameter param = (ShippingParameter) countParam;
		
		// 検索条件をセットし印刷処理クラスを作成する
		ShippingPlanWriter writer = createWriter(conn, param);
		// 対象件数を取得する
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = 印刷データはありませんでした。
			wMessage = "6003010";
		}
		
		return result;
	}
	
	/** 
	 * 画面から入力された情報を印刷処理クラスにセットし、
	 * 印刷処理クラスを生成します。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param parameter 検索条件を含むパラメータオブジェクト
	 * @return 印刷処理クラス
	 */
	protected ShippingPlanWriter createWriter(Connection conn, ShippingParameter parameter)
	{
		// Writerクラスのインスタンスを生成
		ShippingPlanWriter shippingPlanWriter = new ShippingPlanWriter(conn);
		
		// Writerクラスにパラメータの値をセット
		shippingPlanWriter.setConsignorCode(parameter.getConsignorCode());
		shippingPlanWriter.setFromPlanDate(parameter.getFromPlanDate());
		shippingPlanWriter.setToPlanDate(parameter.getToPlanDate());
		shippingPlanWriter.setCustomerCode(parameter.getCustomerCode());
		shippingPlanWriter.setShippingTicketNo(parameter.getShippingTicketNo());
		shippingPlanWriter.setItemCode(parameter.getItemCode());
		shippingPlanWriter.setStatusFlag(parameter.getStatusFlag());
		
		return shippingPlanWriter;

	}
	
}
//end of class

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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.shipping.report.ShippingWriter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 出荷作業リスト発行処理を呼び出すためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷作業リスト発行処理クラスを呼び出します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR>
 * <DIR>
 *   作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
 * <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     作業区分：出荷<BR>
 *     状態フラグ：未開始<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.印刷ボタン押下処理(<CODE>startSCH()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、作業リスト発行処理クラスにパラメータを渡します。<BR>
 *   印刷内容の検索はWriterクラスで行います。<BR>
 *   印刷に成功した場合は、作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取ります。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ *必須入力<BR>
 *   <DIR>
 *     荷主コード* <BR>
 *     開始出荷予定日 <BR>
 *     終了出荷予定日 <BR>
 *     出荷先コード <BR>
 *     伝票No. <BR>
 *     商品コード <BR>
 *   </DIR>
 *   <BR>
 *   ＜処理条件チェック＞
 *   <DIR>
 *     なし<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class ShippingListSCH extends AbstractShippingSCH
{

	// Class variables -----------------------------------------------
	/**
	 * メッセージ保持エリアです。<BR>
	 * 各メソッドの呼び出しで条件エラー等が発生した場合にその内容を保持するために使用します。
	 */
    protected String wDelim = MessageResource.DELIM;
	private String wMessage;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * 
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
	public ShippingListSCH()
	{
		wMessage = null;
	}

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としないためsearchParamにはnullがセットされています。 <BR>
	 * <BR>
	 * 
	 * @param conn Connection データベースとのコネクションオブジェクト
	 * @param searchParam Parameter 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		
	    WorkingInformationReportFinder wRFinder = new WorkingInformationReportFinder(conn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		// 作業区分：出荷
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
		// 状態フラグ：削除以外
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		// GROUP BY条件に荷主コード
		workInfoSearchKey.setConsignorCodeGroup(1);
		workInfoSearchKey.setConsignorCodeCollect("");

		ShippingParameter dispData = new ShippingParameter();
		
		try
		{
		    int count = wRFinder.search(workInfoSearchKey) ;		
		    WorkingInformation[] working = null ;
		    if (count == 1)
		    {				
			    working = (WorkingInformation[])wRFinder.getEntities(1) ;
				dispData.setConsignorCode(working[0].getConsignorCode());		
		    }
		}
		catch(ReadWriteException e)
		{
		    //6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), this.getClass().getName() );
			throw ( new ReadWriteException( "6006002" + wDelim + "DnWorkInfo" ) ) ;
		    
		}
		finally
		{
		    // 必ずCollectionをクローズする
			wRFinder.close();
		}		
		return dispData;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、作業リスト発行処理クラスにパラメータを渡します。<BR>
	 * 印刷データがない場合は印刷処理を行いません。<BR>
	 * 印刷に成功した場合は、作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取り、
	 * その結果を返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
	 * 
	 * @param conn Connection データベースとのコネクションオブジェクト
	 * @param startParams Parameter[] 設定内容を持つ<CODE>ShippingSupportParameter</CODE>クラスのインスタンスの配列。<BR>
	 * <CODE>ShippingSupportParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。
	 * 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * 
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) 
		throws ReadWriteException, ScheduleException
	{	
		// パラメータは1件しか渡されないはずなので先頭データを取得する。
		ShippingParameter	param = (ShippingParameter) startParams[0];
		
		// 印刷クラスを作成します

		ShippingWriter writer = createWriter(conn, param);
		// 印刷処理を開始する。
		if (writer.startPrint())
		{
			// 6001010 = 印刷は正常に終了しました。
			wMessage = "6001010";
			return true;
		}
		else
		{
			// エラーメッセージを表示します。
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
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 * @return 印刷対象の件数
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		ShippingParameter param = (ShippingParameter) countParam;
		
		// 検索条件をセットし印刷処理クラスを作成する
		ShippingWriter writer = createWriter(conn, param);
		// 対象件数を取得する
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = 印刷データはありませんでした。
			wMessage = "6003010";
		}
		
		return result;
	
	}
	
	public String getMessage()
	{

		return wMessage;
	}
	
	// Protected methods ------------------------------------------------
	/** 
	 * 画面から入力された情報を印刷処理クラスにセットし、
	 * 印刷処理クラスを生成します。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param parameter 検索条件を含むパラメータオブジェクト
	 * @return 印刷処理クラス
	 */
	protected ShippingWriter createWriter(Connection conn, ShippingParameter parameter)
	{
		// 印刷処理クラスに画面で入力された印刷条件をセットする。
		ShippingWriter writer = new ShippingWriter(conn);
		writer.setConsignorCode(parameter.getConsignorCode());
		writer.setFromPlanDate(parameter.getFromPlanDate());
		writer.setToPlanDate(parameter.getToPlanDate());
		writer.setCustomerCode(parameter.getCustomerCode());
		writer.setTicketNo(parameter.getShippingTicketNo());
		writer.setItemCode(parameter.getItemCode());
		writer.setStatusFlag(parameter.getStatusFlag());
		
		return writer;
	}

}
//end of class

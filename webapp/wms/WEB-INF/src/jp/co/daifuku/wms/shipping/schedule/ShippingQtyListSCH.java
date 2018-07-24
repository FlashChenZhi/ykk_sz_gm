//$Id: ShippingQtyListSCH.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.shipping.report.ShippingQtyWriter;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 出荷実績リスト発行処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、出荷実績リスト発行処理を行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   出荷実績情報<CODE>(ResultView)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR> 
 *   <BR>
 *   ＜検索条件＞ <BR> 
 *   <BR>
 *   <DIR>
 *   作業区分が出荷 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>startSCH()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、 <BR>
 *   印刷対象データが１件以上存在する場合は、<BR>
 *   <CODE>ShippingQtyWriter</CODE>クラスを使用して出荷実績リストの印刷処理を行います。<BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力 <BR>
 *   <BR>
 *   <DIR>
 *   荷主コード* <BR>
 *   開始出荷日 <BR>
 *   終了出荷日 <BR>
 *   出荷先コード <BR>
 *   伝票No <BR>
 *   商品コード <BR>
 *   </DIR>
 *   <BR>
 *   ＜返却データ＞ <BR>
 *   <BR>
 *   <DIR>
 *   検索件数 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ShippingQtyListSCH extends AbstractShippingSCH
{
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $");
	}
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingQtyListSCH()
	{
		wMessage = null;
	}
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。 <BR>
	 * 出荷実績情報<CODE>(ResultView)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
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
	    ResultViewReportFinder rRFinder = new ResultViewReportFinder(conn);
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		// データの検索
		// 作業区分(出荷)
		searchKey.setJobType(ResultView.JOB_TYPE_SHIPINSPECTION);
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorCodeGroup(1);
		ShippingParameter dispData = new ShippingParameter();
		try
		{
		    int count = rRFinder.search(searchKey) ;
		    if (count == 1)
		    {				
		    	ResultView[] resultView = (ResultView[])rRFinder.getEntities(1) ;
				dispData.setConsignorCode(resultView[0].getConsignorCode());		
		    }
		}
		catch(ReadWriteException e)
		{
		    //6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler( 6006002, e ), this.getClass().getName() );
			throw ( new ReadWriteException( "6006002" + wDelim + "DvResultView" ) ) ;
		    
		}
		finally
		{
		    rRFinder.close();
		}
		
		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、 <BR>
	 * ShippingQtyWriterクラスを使用して出荷実績リストの印刷処理を行います。 <BR>    
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列。
	 *         <CODE>ShippingParameter</CODE>インスタンス以外が指定された場合ScheduleExceptionをスローします。 <BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return 検索結果を持つ<CODE>ShippingParameter</CODE>インスタンスの配列。 <BR>
	 *          該当レコードが一件もみつからない場合は要素数0の配列を返します。 <BR>
	 *          入力条件にエラーが発生した場合はnullを返します。 <BR>
	 *          nullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			if (!this.check(conn, startParams[0]))
			{
				return false;
			}

			// パラメータは1件しか渡されないはずなので先頭データを取得する。
			ShippingParameter parameter = (ShippingParameter) startParams[0];
		
			// 印刷クラスを作成します
			ShippingQtyWriter writer = createWriter(conn, parameter);

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
		catch(Exception e)
		{
			// エラーをログファイルに書き込む
			RmiMsgLogClient.write(new TraceHandler(6027005, e), "ShippingQtyListSCH");
			// メッセージをセット 6027005 = 内部エラーが発生しました。ログを参照してください。
			wMessage = "6027005";
			throw new ScheduleException(e.getMessage());
		}
	}

	/** 
	 * パラメータの入力チェックを行います。<BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam <CODE>ShippingParameter</CODE>クラスのエンティティ。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		ShippingParameter param = (ShippingParameter)searchParam;

		// パラメータから開始日と終了日を取得する。
		String fromshippingdate = param.getFromShippingDate();
		String toshippingdate = param.getToShippingDate();
		
		// 出荷日 大小チェック
		if (!StringUtil.isBlank(fromshippingdate) && !StringUtil.isBlank(toshippingdate))
		{
			if (fromshippingdate.compareTo(toshippingdate) > 0)
			{
				// 6023049 = 開始出荷日は、終了出荷日より前の日付にしてください。
				wMessage = "6023049";
				return false;
			}
		}
		return true;
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
		ShippingQtyWriter writer = createWriter(conn, param);
		// 対象件数を取得する
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = 印刷データはありませんでした。
			wMessage = "6003010";
		}
		
		return result;
	
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
	protected ShippingQtyWriter createWriter(Connection conn, ShippingParameter parameter)
	{
		// Writerクラスのインスタンスを生成
		ShippingQtyWriter shippingQtyWriter = new ShippingQtyWriter(conn);
		
		// Writerクラスにパラメータの値をセット
		shippingQtyWriter.setConsignorCode(parameter.getConsignorCode());
		shippingQtyWriter.setFromWorkDate(parameter.getFromShippingDate());
		shippingQtyWriter.setToWorkDate(parameter.getToShippingDate());
		shippingQtyWriter.setCustomerCode(parameter.getCustomerCode());
		shippingQtyWriter.setTicketNo(parameter.getShippingTicketNo());
		shippingQtyWriter.setItemCode(parameter.getItemCode());
		return shippingQtyWriter;

	}
	
}
//end of class

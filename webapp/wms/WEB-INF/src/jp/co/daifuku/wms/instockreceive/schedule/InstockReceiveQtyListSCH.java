package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.instockreceive.report.InstockReceiveQtyWriter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * クロス/DC入荷実績リスト発行処理を呼び出すためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、クロス/DC入荷実績リスト発行処理クラスを呼び出します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind(Connection, Parameter)</CODE>メソッド)<BR>
 * <DIR>
 *   実績情報Viewに荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
 * <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     作業区分：01(入荷)<BR>
 *     TC/DC区分：TC以外 <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.印刷ボタン押下処理(<CODE>startSCH(Connection, Parameter[])</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、実績リストファイル作成処理クラスにパラメータを渡します。<BR>
 *   印刷内容の検索はWriterクラスで行います。<BR>
 *   印刷に成功した場合は、実績リストファイル作成処理クラスからtrueを、失敗した場合はfalseを受け取ります。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 *   ＜入力データ＞ *必須入力<BR>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>開始入荷受付日</td><td>：</td><td>FromInstockReceiveDate</td></tr>
 *     <tr><td></td><td>終了入荷受付日</td><td>：</td><td>ToInstockReceiveDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>開始伝票No.</td><td>：</td><td>FromTicketNo</td></tr>
 *     <tr><td></td><td>終了伝票No.</td><td>：</td><td>ToTicketNo</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td>*</td><td>クロス/DC</td><td>：</td><td>TcdcFlag</td></tr>
 *   </table>
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
 * <TR><TD>2004/10/27</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockReceiveQtyListSCH extends AbstractInstockReceiveSCH
{

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * 
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
	public InstockReceiveQtyListSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 実績情報Viewに荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としないためsearchParamにはnullがセットしてください。 <BR>
	 * <BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param searchParam 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{

		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		ResultViewSearchKey searchKey = new ResultViewSearchKey();
		ResultViewReportFinder resultFinder = new ResultViewReportFinder(conn);
		ResultView[] wResult = null;

		try
		{
			// 検索条件を設定する。
			// データの検索
			// 作業区分：入荷
			searchKey.setJobType(SystemDefine.JOB_TYPE_INSTOCK);
			// ＴＣＤＣフラグ＝「ＴＣ以外」
			searchKey.setTcdcFlag(SystemDefine.TCDC_FLAG_TC, "!=");
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (resultFinder.search(searchKey) == 1)
			{
			    // 荷主コードを取得します。
				wResult = (ResultView[]) resultFinder.getEntities(1);
				wParam.setConsignorCode(wResult[0].getConsignorCode());
			}
			resultFinder.close();
			
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずConnectionをクローズする
			resultFinder.close();
		}
		
		return wParam;
		
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、クロス/DC入荷実績リストファイル作成処理クラスにパラメータを渡します。<BR>
	 * 印刷データがない場合は印刷処理を行いません。<BR>
	 * 印刷に成功した場合は、作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取り、
	 * その結果を返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。<BR>
	 * <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。
	 * 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * 
	 * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter param = null;

		// パラメータは1件しか渡されないはずなので先頭データを取得する。
		param = (InstockReceiveParameter) startParams[0];

		// 印刷クラスを作成します
		InstockReceiveQtyWriter writer = createWriter(conn, param);
		
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
		InstockReceiveParameter param = (InstockReceiveParameter) countParam;
		
		// 検索条件をセットし印刷処理クラスを作成する
		InstockReceiveQtyWriter writer = createWriter(conn, param);
		// 対象件数を取得する
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = 印刷データはありませんでした。
			wMessage = "6003010";
		}
		
		return result;
	
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/** 
	 * 画面から入力された情報を印刷処理クラスにセットし、
	 * 印刷処理クラスを生成します。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param parameter 検索条件を含むパラメータオブジェクト
	 * @throws ScheduleException TCDC区分にTC(想定外の値)を指定された場合に通知されます。
	 * @return 印刷処理クラス
	 */
	protected InstockReceiveQtyWriter createWriter(Connection conn, InstockReceiveParameter parameter) throws ScheduleException
	{
		// 印刷処理クラスに画面で入力された印刷条件をセットする。
		InstockReceiveQtyWriter writer = new InstockReceiveQtyWriter(conn);
		writer.setConsignorCode(parameter.getConsignorCode());
		writer.setFromDate(parameter.getFromInstockReceiveDate());
		writer.setToDate(parameter.getToInstockReceiveDate());
		writer.setSupplierCode(parameter.getSupplierCode());
		writer.setFromTicketNo(parameter.getFromTicketNo());
		writer.setToTicketNo(parameter.getToTicketNo());
		writer.setItemCode(parameter.getItemCode());
		
		// 全てが選択された場合は何もセットしない
		if (parameter.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
		{
			writer.setTcDcFlag(ResultView.TCDC_FLAG_CROSSTC);
		}
		else if (parameter.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_DC))
		{
			writer.setTcDcFlag(ResultView.TCDC_FLAG_DC);
		}
		else if (parameter.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_ALL))
		{
			writer.setTcDcFlag("");
		}
		else
		{
			// TCがセットされた場合は例外を通知する
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "param.getTcdcFlag()";
			tObj[2] = parameter.getTcdcFlag();
			// 6006009=範囲外の値を指定されました。セットできません。Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ScheduleException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
		
		return writer;

	}
	// Private methods -----------------------------------------------

}
//end of class

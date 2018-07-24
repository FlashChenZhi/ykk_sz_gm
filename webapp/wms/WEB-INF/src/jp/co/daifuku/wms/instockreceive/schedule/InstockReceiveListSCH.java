package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.instockreceive.report.InstockReceiveWriter;

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * クロス/DC入荷作業リスト発行処理を呼び出すためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、クロス/DC入荷作業リスト発行処理クラスを呼び出します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)<BR>
 * <DIR>
 *   作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
 *   <BR>
 *   [検索条件]
 *   <BR>
 *   作業区分が入荷<BR>
 *   TC/DC区分がDC、クロスTC <BR>
 *   <BR>
 * </DIR>
 * 2.印刷ボタン押下処理(<CODE>startSCH()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、クロス/DC入荷作業リスト発行処理クラスにパラメータを渡します。<BR>
 *   印刷内容の検索はWriterクラスで行います。<BR>
 *   印刷に成功した場合は、クロス/DC入荷作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取ります。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <table>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>表示順</td><td>：</td><td>DspOrder</td></tr>
 *     <tr><td></td><td>作業状態</td><td>：</td><td>StatusFlag</td></tr>
 *   </table>
 *   <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockReceiveListSCH extends AbstractInstockReceiveSCH
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
	public InstockReceiveListSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
	 * 検索条件を必要としないためsearchParamにはnullがセットされています。 <BR>
	 * <BR>
	 * @param conn Connection データベースとのコネクションオブジェクト
	 * @param searchParam Parameter 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		// 該当する荷主コードがセットされます。
		InstockReceiveParameter wParam = new InstockReceiveParameter();

		// 出庫予定情報ハンドラ類のインスタンス生成
		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		WorkingInformationReportFinder workingFinder = new WorkingInformationReportFinder(conn);
		WorkingInformation[] wWorking = null;

		try
		{
			// 検索条件を設定する。
			// 状態フラグ：「削除」以外
			searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			// 作業区分(入荷)
			searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// TC/DC区分(ＤＣ、クロスTC)
			String[] tcdcflag =
				{ WorkingInformation.TCDC_FLAG_DC, WorkingInformation.TCDC_FLAG_CROSSTC };
			searchKey.setTcdcFlag(tcdcflag);
			// グルーピング条件に荷主コードをセット
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (workingFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// データの検索
				// 状態フラグ：「削除」以外
				searchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				// 作業区分(入荷)
				searchKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
				// TC/DC区分(ＤＣ、クロスTC)
				searchKey.setTcdcFlag(tcdcflag);
				// ソート順に登録日時を設定
				searchKey.setRegistDateOrder(1, false);

				searchKey.setConsignorNameCollect("");
				searchKey.setConsignorCodeCollect("");

				if (workingFinder.search(searchKey) > 0)
				{
					// 登録日時が最も新しい荷主名称を取得します。
					wWorking = (WorkingInformation[]) workingFinder.getEntities(1);
					wParam.setConsignorName(wWorking[0].getConsignorName());
					wParam.setConsignorCode(wWorking[0].getConsignorCode());
				}
			}
			workingFinder.close();

		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			// 必ずCollectionをクローズする
			workingFinder.close();
		}

		return wParam;

	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、作業リスト発行処理クラスにパラメータを渡します。<BR>
	 * 印刷データがない場合は印刷処理を行いません。<BR>
	 * 印刷に成功した場合は、作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取り、
	 * その結果を返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
	 * 
	 * @param conn Connection データベースとのコネクションオブジェクト
	 * @param startParams Parameter[] 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。<BR>
	 * <CODE>InstockReceiveParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。
	 * 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * 
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter parameter = (InstockReceiveParameter) startParams[0];

		if (parameter == null)
		{
			wMessage = "6027005";
			throw new ScheduleException();
		}
		
		// 印刷クラスを作成します
		InstockReceiveWriter writer = createWriter(conn, parameter);
		
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
		InstockReceiveWriter writer = createWriter(conn, param);
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
	 * @return 印刷処理クラス
	 */
	protected InstockReceiveWriter createWriter(Connection conn, InstockReceiveParameter parameter)
	{
		// 印刷処理クラスを生成する
		InstockReceiveWriter writer = new InstockReceiveWriter(conn);
		// 印刷処理クラスに画面からの入力情報をセットする
		writer.setConsignorCode(parameter.getConsignorCode());
		writer.setInstockPlanDay(parameter.getPlanDate());
		writer.setSupplierCode(parameter.getSupplierCode());
		writer.setItemCode(parameter.getItemCode());
		writer.setCrossDc(parameter.getTcdcFlag());
		writer.setDisplayOrder(parameter.getDspOrder());
		writer.setStatusFlag(parameter.getStatusFlag());
		
		return writer;

	}
	// Private methods -----------------------------------------------

}
//end of class

package jp.co.daifuku.wms.sorting.schedule;

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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.sorting.report.SortingPlanWriter;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * 仕分予定リスト発行処理を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分予定リスト発行処理を行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド) <BR> 
 * <BR>
 * <DIR>
 *   仕分予定情報<CODE>(dnsortingplan)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR> 
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR> 
 *   <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>startSCH()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、<BR>
 *   印刷対象データが１件以上存在する場合は、<BR>
 *   <CODE>SortingPlanWriter</CODE>クラスを使用して仕分予定リストの印刷処理を行います。<BR>
 *   <BR>
 *   ＜パラメータ＞ *必須入力 <BR>
 *   <BR>
 *   <DIR>
 *   荷主コード* : ConsignorCode <BR>
 *   仕分予定日 : PlanDate <BR>
 *   商品コード : ItemCode <BR>
 *   ケースピース区分 : CasePieceFlag <BR>
 *   クロス／ＤＣ : TcdcFlag <BR>
 *   作業状態 : StatusFlag <BR>
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
 * <TR><TD>2004/11/5</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingPlanListSCH extends AbstractSortingSCH
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
	public SortingPlanListSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------

	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。 <BR>
	 * 仕分予定情報<CODE>(dnsortingplan)</CODE>に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。 <BR>
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
		SortingPlanHandler sortingplanHandler = new SortingPlanHandler(conn);
		SortingPlanSearchKey searchKey = new SortingPlanSearchKey();

		// データの検索
		// 状態フラグ＝「削除」以外
		searchKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorCodeGroup(1);

		SortingParameter dispData = new SortingParameter();

		if (sortingplanHandler.count(searchKey) == 1)
		{
			try
			{
				SortingPlan[] sortingplan = (SortingPlan[])sortingplanHandler.find(searchKey);
				dispData.setConsignorCode(sortingplan[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new SortingParameter();
			}
		}

		return dispData;
	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、 <BR>
	 * SortingPlanWriterクラスを使用して仕分予定リストの印刷処理を行います。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。
	 *         <CODE>SortingParameter</CODE>インスタンス以外が指定された場合ScheduleExceptionをスローします。 <BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return 検索結果を持つ<CODE>SortingParameter</CODE>インスタンスの配列。 <BR>
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
			// パラメータは1件しか渡されないはずなので先頭データを取得する。
			SortingParameter sortingParameter = (SortingParameter) startParams[0];
		

			// 印刷クラスを作成します
			SortingPlanWriter writer = createWriter(conn, sortingParameter);
			// 印刷を開始します
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
			RmiMsgLogClient.write(new TraceHandler(6027005, e), "SortingPlanListSCH");
			// メッセージをセット 6027005 = 内部エラーが発生しました。ログを参照してください。
			wMessage = "6027005";
			throw new ScheduleException(e.getMessage());
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
		SortingParameter param = (SortingParameter) countParam;
		
		// 検索条件をセットし印刷処理クラスを作成する
		SortingPlanWriter writer = createWriter(conn, param);
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
	protected SortingPlanWriter createWriter(Connection conn, SortingParameter parameter)
	{
		// Writerクラスのインスタンスを生成
		SortingPlanWriter sortingplanWriter = new SortingPlanWriter(conn);
		
		// 荷主コード
		sortingplanWriter.setConsignorCode(parameter.getConsignorCode());
		// 仕分予定日
		sortingplanWriter.setPlanDate(parameter.getPlanDate());
		// 商品コード
		sortingplanWriter.setItemCode(parameter.getItemCode());
		// ケースピース区分
		sortingplanWriter.setCasePieceFlag(parameter.getCasePieceFlag());
		// クロス／ＤＣ区分
		sortingplanWriter.setTcdcFlag(parameter.getTcdcFlag());
		// 作業状態
		sortingplanWriter.setWorkStatus(parameter.getStatusFlag());
		
		return sortingplanWriter;

	}
	
}
//end of class

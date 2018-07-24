package jp.co.daifuku.wms.sorting.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.sorting.report.SortingWorkWriter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 仕分作業リスト発行処理を呼び出すためのクラスです。<BR>
 * 画面から入力された内容をパラメータとして受け取り、仕分作業リスト発行処理クラスを呼び出します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>initFind(Connection, Parameter)</CODE>メソッド)<BR>
 * <DIR>
 *   作業情報に該当荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。<BR>
 * <BR>
 *   ＜検索条件＞
 *   <DIR>
 *     作業区分：04(仕分)<BR>
 *     状態フラグ：9以外(削除以外)<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.印刷ボタン押下処理(<CODE>startSCH(Connection, Parameter[])</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力された内容をパラメータとして受け取り、仕分作業リストファイル作成処理クラスにパラメータを渡します。<BR>
 *   印刷内容の検索はWriterクラスで行います。<BR>
 *   印刷に成功した場合は、仕分作業リストファイル作成処理クラスからtrueを、失敗した場合はfalseを受け取ります。<BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 *   ＜入力データ＞ *必須入力<BR>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面上の項目名</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td>*</td><td>ケース／ピース区分</td><td>：</td><td>CasePieceFlag</td></tr>
 *     <tr><td>*</td><td>クロス／ＤＣ</td><td>：</td><td>TcDcFlag</td></tr>
 *     <tr><td>*</td><td>作業状態</td><td>：</td><td>StatusFlag</td></tr>
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
 * <TR><TD>2004/10/28</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingWorkListSCH extends AbstractSortingSCH
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public SortingWorkListSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 作業情報に荷主コードが1件のみ存在した場合、該当する荷主コードを返します。<BR>
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
		// 返却データ
		SortingParameter resultParam = null;

		WorkingInformationHandler workHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();

		try
		{
			// 作業区分：仕分
			workKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
			// 状態フラグ：削除以外
			workKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			// GROUP BY条件に荷主コード
			workKey.setConsignorCodeGroup(1);
			workKey.setConsignorCodeCollect("");

			if (workHandler.count(workKey) == 1)
			{
				// 荷主コードを検索する
				WorkingInformation workInfo = (WorkingInformation) workHandler.findPrimary(workKey);

				resultParam = new SortingParameter();
				// 検索結果を返却値にセット
				resultParam.setConsignorCode(workInfo.getConsignorCode());
			}

		}
		catch (NoPrimaryException pe)
		{
			return null;
		}
		return resultParam;

	}

	/**
	 * 画面から入力された内容をパラメータとして受け取り、仕分作業リストファイル作成処理クラスにパラメータを渡します。<BR>
	 * 印刷データがない場合は印刷処理を行いません。<BR>
	 * 印刷に成功した場合は、作業リスト発行処理クラスからtrueを、失敗した場合はfalseを受け取り、
	 * その結果を返します。<BR>
	 * エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
	 * 
	 * @param conn データベースとのコネクションオブジェクト
	 * @param startParams 設定内容を持つ<CODE>SortingParameter</CODE>クラスのインスタンスの配列。<BR>
	 * <CODE>SortingParameter</CODE>インスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。
	 * 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * 
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// パラメータは1件しか渡されないはずなので先頭データを取得する。
		SortingParameter param = (SortingParameter) startParams[0];
		
		// 印刷クラスを作成します
		SortingWorkWriter writer = createWriter(conn, param);
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
		SortingParameter param = (SortingParameter) countParam;
		
		// 検索条件をセットし印刷処理クラスを作成する
		SortingWorkWriter writer = createWriter(conn, param);
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
	protected SortingWorkWriter createWriter(Connection conn, SortingParameter param)
	{
		// Writerクラスのインスタンスを生成
		SortingWorkWriter writer = new SortingWorkWriter(conn);
		
		writer.setConsignorCode(param.getConsignorCode());
		writer.setPlanDate(param.getPlanDate());
		writer.setItemCode(param.getItemCode());
		writer.setCasePieceFlag(param.getCasePieceFlag());
		writer.setTcdcFlag(param.getTcdcFlag());
		writer.setStatusFlag(param.getStatusFlag());
		
		return writer;

	}

}
//end of class

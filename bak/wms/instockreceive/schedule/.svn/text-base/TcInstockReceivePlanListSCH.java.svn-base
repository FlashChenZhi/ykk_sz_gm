package jp.co.daifuku.wms.instockreceive.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.instockreceive.report.TcInstockReceivePlanWriter;

/**
 * 設計者 :   Muneendra <BR>
 * 製作者 :   Muneendra <BR>
 *
 * <CODE>TcInstockReceivePlanListSCH</CODE>クラスは、入荷予定情報のリスト発行を行います。<BR>
 * 画面から入力された内容をパラメータとして受け取り、TC入荷予定リスト発行処理を行います。<BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、<BR>
 * トランザクションのコミット・ロールバックは行いません。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR> 
 * 1.初期表示処理(<CODE>initFind()</CODE>メソッド)
 * <DIR>
 * 	状態が削除以外の荷主コードを入荷予定情報から検索し、同一の荷主コードしか存在しない場合に、<BR>
 * 	荷主コードを返します。<BR>
 * 	荷主コードが複数存在する場合は、nullを返します。<BR>
 * </DIR>
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>startSCH()</CODE>メソッド)<BR>
 * <DIR>
 * 	画面から入力された内容をパラメータとして受け取り、それを条件に入荷予定リストを発行します。<BR>
 * 	処理が正常に完了した場合はtrueを返します。<BR>
 * 	リスト発行中にエラーが発生した場合はfalseを返します。<BR>
 * 	エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。<BR>
 * <BR>
 * 	＜パラメータ＞ *必須入力<BR>
 * <BR>
 * 		荷主コード*<BR>
 *      開始入荷予定日<BR>
 * 		終了入荷予定日<BR>
 * 		仕分先コード<BR>
 * 		出荷先コード<BR>
 * 		開始伝票№<BR>
 * 		終了伝票№<BR>
 * 		商品コード<BR>
 * 		作業状態<BR>
 * <BR>
 * 	＜印刷処理＞<BR>
 * 	1.パラメータにセットされた値を<CODE>TcInstockReceivePlanWriter</CODE>クラスにセットします。<BR>
 * 	2.<CODE>TcInstockReceivePlanWriter</CODE>クラスを使用して、TC入荷予定リストを発行します。<BR>
 * 
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class TcInstockReceivePlanListSCH extends AbstractInstockReceiveSCH
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
	public TcInstockReceivePlanListSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 初期表示処理を行います。<BR>
	 * 状態が削除以外の荷主コードを入荷予定情報から検索し、同一の荷主コードしか存在しない場合に、<BR>
	 * <CODE>InstockReceiveParameter</CODE>クラスに荷主コードをセットします。<BR>
	 * @param conn Connection データベースとのコネクションオブジェクト
	 * @param searchParam Parameter 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return 検索結果が含まれた<CODE>Parameter</CODE>インターフェースを実装したクラス
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)throws ReadWriteException, ScheduleException
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
			// 状態フラグ：削除以外
			searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			// TC/DC区分(ＴＣ)
			searchKey.setTcdcFlag(InstockPlan.TCDC_FLAG_TC);
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (instockFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// データの検索
				// 状態フラグ：削除以外
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
				// TC/DC区分(ＴＣ)
				searchKey.setTcdcFlag(InstockPlan.TCDC_FLAG_TC);
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
	 * 画面から入力された内容をパラメータとして受け取り、TC入荷予定リスト発行スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * <CODE>TcInstockPlanWriter</CODE>クラスを使用してTC入荷予定リストの印刷処理を行います。<BR>
	 * 
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>TcInstockPlanWriter</CODE>クラスのインスタンスの配列。
	 *         InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *         エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
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
		
		// 
		// 印刷クラスを作成します
		TcInstockReceivePlanWriter writer = createWriter(conn, parameter);
		
		// 結果ごとにメッセージをセット
		if (writer.startPrint())
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
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 * @return 印刷対象の件数
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter param = (InstockReceiveParameter) countParam;
		
		// 検索条件をセットし印刷処理クラスを作成する
		TcInstockReceivePlanWriter writer = createWriter(conn, param);
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
	
	protected TcInstockReceivePlanWriter createWriter(Connection conn, InstockReceiveParameter parameter)
	{
		// 印刷処理クラスを生成する
		TcInstockReceivePlanWriter writer = new TcInstockReceivePlanWriter(conn);
		// 印刷処理クラスに画面からの入力情報をセットする
		writer.setConsignorCode(parameter.getConsignorCode());		
		writer.setFromDate(parameter.getFromPlanDate());
		writer.setToDate(parameter.getToPlanDate());
		writer.setSupplierCode(parameter.getSupplierCode());
		writer.setFromTicketNo(parameter.getFromTicketNo());
		writer.setToTicketNo(parameter.getToTicketNo());
		writer.setItemCode(parameter.getItemCode());
		writer.setCustomerCode(parameter.getCustomerCode());
		writer.setStatusFlag(parameter.getStatusFlag());
		
		return writer;

	}
}

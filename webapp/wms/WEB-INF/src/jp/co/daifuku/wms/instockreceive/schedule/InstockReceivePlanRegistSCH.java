package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.instockreceive.dbhandler.InstockReceiveWorkingInformationHandler;


/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida  <BR>
 * <BR>
 * WEB入荷予定データ登録を行うためのクラスです。 <BR>
 * 画面から入力された内容をパラメータとして受け取り、入荷予定データ登録処理を行います。 <BR>
 * このクラスが持つ各メソッドは、コネクションオブジェクトを受け取りデータベースの更新処理をおこないますが、 <BR>
 * トランザクションのコミット・ロールバックは行いません。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期表示処理（<CODE>initFind()</CODE>メソッド） <BR><BR><DIR>
 *   入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。 <BR>
 *   該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
 * <BR>
 *   ＜検索条件＞ <BR><DIR>
 *     状態フラグ：削除(9)以外
 * </DIR>
 * </DIR>
 * <BR>
 * 2.次へボタン押下処理 (<CODE>nextCheck()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、作業者コード、パスワードのチェック、必須チェック結果を返します。 <BR>
 *   作業者コード、パスワードの内容が正しい場合はtrueを返します。パラメータの内容に問題がある場合はfalseを返します。
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
 * <BR>
 *   ＜パラメータ＞ <BR><DIR>
 *     * 必須の入力項目 <BR>
 *     + どちらかが必須の入力項目 <BR>
 * <BR>
 *     作業者コード	：WorkerCode* <BR>
 *     パスワード	：Password* <BR>
 *     荷主コード	：ConsignorCode <BR>
 *     入荷予定日	：PlanDate <BR>
 *     仕入先コード	：SupplierCode <BR>
 *     TC/DC区分	：TcdcFlag* <BR>
 *     出荷先コード	：CustomerCode+ <BR>
 *     入荷伝票No.	：InstockTicketNo <BR></DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR>
 *     1.TC/DC区分：クロスまたはTC指定時、出荷先コードが入力されていること。 <BR>
 * <BR>
 * </DIR>
 * <BR>  
 * 3.入力ボタン押下処理（<CODE>check()</CODE>メソッド） <BR><BR><DIR>
 *   画面から入力された内容をパラメータとして受け取り、必須チェック・オーバーフローチェック・重複チェック・桁数チェックを行い、チェック結果を返します。 <BR>
 *   入荷予定情報に同一行No.の該当データが存在しなかった場合はtrueを、条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 *   荷主コード、入荷予定日、仕入先コード、入荷伝票No.、入荷伝票行No.をキーを重複チェックを行います。  <BR>
 *   また、状態フラグが未開始または削除の同一行No.が存在した場合は重複エラーとせず、trueを返します。 <BR>
 * <BR>
 *   ＜パラメータ＞ <BR><DIR>
 *     * 必須の入力項目 <BR>
 *     + どちらかが必須の入力項目 <BR>
 * <BR>
 *     荷主コード	：ConsignorCode* <BR>
 *     入荷予定日	：PlanDate* <BR>
 *     仕入先コード	：SupplierCode* <BR>
 *     入荷伝票No.	：InstockTicketNo* <BR>
 *     入荷行No.	：InstockLineNo* <BR>
 *     商品コード	：ItemCode* <BR>
 *     ケース入数	：EnteringQty+ <BR>
 *     予定ケース数	：PlanCaseQty+ <BR>
 *     予定ピース数	：PlanPieceQty+ <BR>
 *     TC/DC区分	：TcdcFlag* <BR>
 *     出荷先コード	：CustomerCode+ <BR></DIR>
 * <BR>
 *   ＜必須チェック内容＞ <BR>
 *     1.TC/DC区分：クロスまたはTC指定時、出荷先コードが入力されていること。 <BR>
 *     2.予定ケース数または予定ピース数には1以上の値が入力されていること。 <BR>
 *     3.予定ケース数に値が入っていた場合にはケース入数が1以上であること。 <BR>
 * <BR>
 * </DIR>
 * <BR>
 * 4.登録ボタン押下処理（<CODE>startSCH()</CODE>メソッド） <BR><BR><DIR>
 *   ためうちエリアに表示されている内容をパラメータとして受け取り、入荷予定データ登録処理を行います。 <BR>
 *   処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを返します。 <BR>
 *   エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。 <BR>
 * <BR>
 *   ＜パラメータ＞ <BR><DIR>
 *     * 必須の入力項目 <BR>
 *     + どちらかが必須の入力項目 <BR>
 *     # 更新に必要な項目 <BR>
 * <BR>
 *     作業者コード	：WorkerCode*# <BR>
 *     パスワード	：Password* <BR>
 *     荷主コード	：ConsignorCode*# <BR>
 *     荷主名称		：ConsignorName# <BR>
 *     入荷予定日	：PlanDate*# <BR>
 *     仕入先コード	：SupplierCode*# <BR>
 *     仕入先名称	：SupplierName# <BR>
 *     入荷伝票No.	：InstockTicketNo*# <BR>
 *     入荷行No.	：InstockLineNo*# <BR>
 *     商品コード	：ItemCode*# <BR>
 *     商品名称		：ItemName# <BR>
 *     ケース入数	：EnteringQty+# <BR>
 *     ボール入数	：BundleEnteringQty# <BR>
 *     予定ケース数	：PlanCaseQty+# <BR>
 *     予定ピース数	：PlanPieceQty+# <BR>
 *     ケースITF	：ITF# <BR>
 *     ボールITF	：BundleITF# <BR>
 *     TC/DC区分	：TcdcFlag*# <BR>
 *     出荷先コード	：CustomerCode+# <BR>
 *     出荷先名称	：CustomerName# <BR>
 *     端末No. 		：TerminalNumber*# <BR></DIR>
 * <BR>
 *   ＜処理条件チェック＞ <BR>
 *     1.作業者コードとパスワードが作業者マスターに定義されていること。 <BR><DIR>
 *       作業者コードとパスワードの値は、配列の先頭の値のみチェックする。 <BR></DIR>
 *     2.同一行No.の入荷予定情報がデータベースに存在しないこと。（排他チェック） <BR><DIR>
 *       ※状態フラグが未開始または削除の入荷予定情報は重複対象データとしない。<BR></DIR>
 *     3.日次更新処理中でないこと。 <BR>
 * <BR>
 *   ＜更新処理＞ <code>InstockPlanOperator</code>クラスにて以下の処理を行います。 <BR>
 *     ※デッドロックを防ぐため、作業情報、入荷予定情報、在庫情報の順番でテーブルの更新を行う。 <BR>
 *     1.荷主コード、入荷予定日、仕入先コード、入荷伝票No.、入荷伝票行No.をキーに入荷予定情報を検索する。 <BR>
 *     2.状態フラグが未開始または完了の入荷予定情報が存在した場合、該当データの状態フラグを削除に更新する。 <BR>
 *     3.更新した入荷予定情報の入荷予定一意キーに紐づく作業情報の状態フラグを削除に更新する。 <BR>
 *     4.更新した作業情報の在庫IDに紐づく在庫情報の在庫数、引当数を入荷予定数減算した値に更新する。 <BR>
 *     詳細は<code>InstockPlanOperator</code>を参照のこと。 <BR>
 * <BR>
 *   ＜登録処理＞ <code>InstockPlanOperator</code>クラスにて以下の処理を行います。 <BR>
 *     詳細は<code>InstockPlanOperator</code>を参照のこと。 <BR>
 *     -作業情報テーブル(DNWORKINFO)の登録 <BR>
 *       今回登録した入荷予定情報および在庫情報の内容をもとに作業情報を登録する。 <BR>
 * <BR>
 *     -入荷予定情報テーブル(DNINSTOCKPLAN)の更新登録 <BR>
 *       受け取ったパラメータの内容をもとに入荷予定情報を登録する。 <BR>
 * <BR>
 *     ※パラメータのTC/DC区分がTCまたはクロスの場合、以下の処理を行う。 <BR>
 *     -次作業情報テーブル(DNNEXTPROC)の更新登録 <BR>
 *       1.入荷予定日、仕入先コード、入荷伝票No.、入荷伝票行No.をキーに次作業情報を検索する。 <BR>
 *       2.次作業情報が存在した場合、次作業情報の更新を行う。 <BR><DIR>
 *         入荷予定一意キーを今回登録した入荷予定情報の一意キーに更新する。 <BR></DIR>
 * <BR>
 *     -在庫情報テーブル(DMSTOCK)の登録 <BR>
 *       今回登録した入荷予定情報の内容をもとに在庫情報を登録する。 <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/29</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:46 $
 * @author  $Author: suresh $
 */
public class InstockReceivePlanRegistSCH extends AbstractInstockReceiveSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private static String wProcessName = "InstockReceivePlanRegist";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/21 04:22:46 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public InstockReceivePlanRegistSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
	 * 入荷予定情報に荷主コードが1件のみ存在した場合、該当する荷主コード、荷主名称を返します。<BR>
	 * 該当データが存在しなかった場合、または2件以上存在した場合nullを返します。 <BR>
	 * 検索条件を必要としないため<CODE>searchParam</CODE>にはnullをセットします。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param searchParam 表示データ取得条件を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。<BR>
	 *         <CODE>InstockReceiveParameter</CODE>インスタンス以外を指定された場合ScheduleExceptionをスローします。<BR>
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
			// Status other than Dalete
			searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			searchKey.setConsignorCodeGroup(1);
			searchKey.setConsignorCodeCollect("DISTINCT");

			if (instockFinder.search(searchKey) == 1)
			{
				// 検索条件を設定する。				
				searchKey.KeyClear();
				// Status other than Delete
				searchKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
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
	 * 画面から入力された内容をパラメータとして受け取り、該当データの存在チェックの結果を返します。<BR>
	 * 入荷予定情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合はfalseを返します。 <BR>
	 * 状態フラグが未開始または完了の同一行No.が存在した場合は重複エラーとせず、trueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。 <BR>
	 *        InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ScheduleException, ReadWriteException
	{
		// 入荷予定情報ハンドラ類のインスタンス生成
		InstockPlanSearchKey wKey = new InstockPlanSearchKey();
		InstockPlanHandler wObj = new InstockPlanHandler(conn);
		
		// パラメータの検索条件
		InstockReceiveParameter wParam = wParam = (InstockReceiveParameter) checkParam;
		
		// 検索条件を設定する。
		// Consignor code
		wKey.setConsignorCode(wParam.getConsignorCode());
		// Planned receiving date
		wKey.setPlanDate(wParam.getPlanDate());
		// Supplier code
		wKey.setSupplierCode(wParam.getSupplierCode());
		// 入荷伝票No.
		wKey.setInstockTicketNo(wParam.getInstockTicketNo());
		// 入荷伝票行No.
		wKey.setInstockLineNo(wParam.getInstockLineNo());
		// 状態フラグ（未開始以外）
		wKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART, "!=", "(", "", "AND");
		// 状態フラグ（削除以外）
		wKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=", "", ")", "AND");
		
		// 入荷予定情報の検索結果件数を取得する。
		if (wObj.count(wKey) > 0)
		{
			return false;
		}
		
		return true;
	}

	/** 
	 * 画面から入力された溜めうちエリアの内容をパラメータとして受け取り、 <BR>
	 * 必須チェック・オーバーフローチェック・重複チェックを行い、チェック結果を返します。 <BR>
	 * 入荷予定情報に同一行No.の該当データが存在しなかった場合はtrueを、 <BR>
	 * 条件エラーが発生した場合や該当データが存在した場合は排他エラーとし、falseを返します。 <BR>
	 * 状態フラグが未開始または完了の同一行No.が存在した場合は排他エラーとせず、trueを返します。 <BR>
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param checkParam 入力内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。 <BR>
	 *        InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @param inputParams ためうちエリアの内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams)
		throws ScheduleException, ReadWriteException
	{
		// 入荷予定情報ハンドラ類のインスタンス生成
		InstockPlanSearchKey wKey = new InstockPlanSearchKey();
		InstockPlanHandler wObj = new InstockPlanHandler(conn);
		InstockPlan[] wInStock = null;
		
		// 入力エリアの内容
		InstockReceiveParameter wParam = (InstockReceiveParameter) checkParam;
		// ためうちエリアの内容
		InstockReceiveParameter[] wParamList = (InstockReceiveParameter[]) inputParams;
		
		// 条件による必須チェックを行う。
		// 入荷予定ケース数入力時、ケース入数必須チェック 
		if (wParam.getPlanCaseQty() > 0 && wParam.getEnteringQty() == 0)
		{
			// 6023019 = ケース入数を入力してください。
			wMessage = "6023019";
			return false;
		}
		
		// 入荷予定ケース数または入荷予定ピース数に1以上の入力チェック
		if (wParam.getPlanCaseQty() == 0 && wParam.getPlanPieceQty() == 0)
		{
			// 6023093 = 予定ケース数または予定ピース数を入力してください。
			wMessage = "6023093";
			return false;
		}
		
		// TC/DC区分にTC指定時、出荷先コード必須チェック
		if (wParam.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC)
		&&	StringUtil.isBlank(wParam.getCustomerCode()))
		{
			// 6023007 = 出荷先コードを入力してください。
			wMessage = "6023007";
			return false;
		}
		
		// オーバーフローチェックを行う。
		if ((long)wParam.getEnteringQty() * (long)wParam.getPlanCaseQty() + (long)wParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY)
		{
			// 6023058 = {0}には{1}以下の値を入力してください。
			wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0085") + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}
		
		// 表示件数チェックを行う。
		if (wParamList != null && wParamList.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
		{
			// 6023096 = 件数が{0}件を超えるため、入力できません。
			wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
			return false;
		}
		
		// 重複チェックを行う。
		if (wParamList != null)
		{
			for (int i = 0; i < wParamList.length; i ++)
			{
				if (wParamList[i].getInstockLineNo() == wParam.getInstockLineNo())
				{
					// 6023091 = 既に同一の行No．が存在するため、入力できません。
					wMessage = "6023091";
					return false;
				}
			}
		}
		
		// 検索条件を設定する。
		// 荷主コード
		wKey.setConsignorCode(wParam.getConsignorCode());
		// 入荷予定日
		wKey.setPlanDate(wParam.getPlanDate());
		// 仕入先コード
		wKey.setSupplierCode(wParam.getSupplierCode());
		// 入荷伝票No.
		wKey.setInstockTicketNo(wParam.getInstockTicketNo());
		// 入荷伝票行No.
		wKey.setInstockLineNo(wParam.getInstockLineNo());
		// 状態フラグ（未開始以外）
		wKey.setStatusFlag(InstockPlan.STATUS_FLAG_UNSTART, "!=", "(", "", "AND");
		// 状態フラグ（削除以外）
		wKey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=", "", ")", "AND");
		
		// 入荷予定情報の検索結果を取得する。
		wInStock = (InstockPlan[]) wObj.find(wKey);
		
		for (int i = 0; i < wInStock.length; i++)
		{
			if (wInStock[i].getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING))
			{
				// 6023269 = 作業中の同一データが存在するため、入力できません。
				wMessage = "6023269";
				return false;
			}
			
			if (wInStock[i].getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART))
			{
				// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
				wMessage = "6023270";
				return false;
			}
			
			// 6023090 = 既に同一データが存在するため、入力できません。
			wMessage = "6023090";
			return false;
		}

		// 6001019 = 入力を受け付けました。
		wMessage = "6001019";
		
		return true;
	}
	
	/**
	 * 画面から入力された内容をパラメータとして受け取り、作業者コード、パスワードのチェック、必須チェック結果を返します。 <BR>
	 * 作業者コード、パスワードの内容が正しい場合はtrueを返します。<BR>
	 * パラメータの内容に問題がある場合はfalseを返します。<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * @param conn データベースとのコネクションオブジェクト
	 * @param checkParam 入力内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンス。 <BR>
	 *        InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		InstockReceiveParameter wParam = (InstockReceiveParameter) checkParam;

		// 作業者コードとパスワードのチェック
		if (!checkWorker(conn, wParam))
		{
			return false;
		}
		
		// 必須入力チェック
		if (StringUtil.isBlank(wParam.getWorkerCode())
		||  StringUtil.isBlank(wParam.getPassword())
		||  StringUtil.isBlank(wParam.getConsignorCode())
		||  StringUtil.isBlank(wParam.getPlanDate())
		||  StringUtil.isBlank(wParam.getSupplierCode()))
		{
			throw (new ScheduleException("mandatory error!"));
		}
		
		// TC、クロスドック運用フラグチェック
		// TC区分１：クロスTC品の場合はクロスドック運用がありになっている事
		if (wParam.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_CROSSTC)
			&& !isCrossDockOperation(conn))
		{
			// 6023369=クロスＤＣ区分の予定データ登録時は、クロスＤＣ運用が有効になっている必要があります。
			wMessage = "6023369";
			return false;
		}

		// TC区分２：TC品の場合はTC運用がありになっている事
		if (wParam.getTcdcFlag().equals(InstockPlan.TCDC_FLAG_TC)
			&& !isTcOperation(conn))
		{
			// 6023368=ＴＣ区分の予定データ登録時は、ＴＣ運用が有効になっている必要があります。
			wMessage = "6023368";
			return false;
		}
		
		// TC/DC区分：TC時、出荷先コード必須チェック
		if (wParam.getTcdcFlag().equals(InstockReceiveParameter.TCDC_FLAG_TC)
		&&  StringUtil.isBlank(wParam.getCustomerCode()))
		{
			// 6023007 = 出荷先コードを入力してください。
			wMessage = "6023007";
			return false;
		}
		return true;
	}
		
	/**
	 * 画面から入力された内容をパラメータとして受け取り、入荷予定データ登録スケジュールを開始します。<BR>
	 * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
	 * 詳しい動作はクラス説明の項を参照してください。<BR>
	 * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
	 * @param conn データベースとのコネクションを保持するインスタンス。
	 * @param startParams 設定内容を持つ<CODE>InstockReceiveParameter</CODE>クラスのインスタンスの配列。 <BR>
	 *        InstockReceiveParameterインスタンス以外が指定された場合<CODE>ScheduleException</CODE>をスローします。<BR>
	 *        エラーの内容については<CODE>getMessage()</CODE>メソッドを使用して参照することができます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		// 登録処理が行われたかどうかのチェックフラグ
		boolean registFlag = false;
		// 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
		boolean updateLoadDataFlag = false;
		
		// 入荷予定データ取込み用共通クラスのインスタンス生成
		InstockPlanOperator wObj = new InstockPlanOperator(conn);
		// 入荷エンティティクラス
		InstockPlan wInStock = null;
		// SequenceHandlerのインスタンス生成
		SequenceHandler wSequence = new SequenceHandler(conn);
		// バッチNo.
		String batch_seqno = null;
		
		try
		{
			// パラメータの入力情報
			InstockReceiveParameter[] wParam = (InstockReceiveParameter[]) startParams;
			
			// 日次更新処理中のチェック
			if (isDailyUpdate(conn))
			{
				return false;
			}
			
			// 取り込みフラグチェック処理 false：取り込み中	
			if (isLoadingData(conn))
			{
				return false;
			}
			
			// 取り込みフラグ更新：取り込み中
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;
			
			InstockReceiveWorkingInformationHandler wiHandle = new InstockReceiveWorkingInformationHandler(conn);
			if (!wiHandle.lockPlanData(startParams))
			{
				// 6003006=このデータは、他の端末で更新されたため処理できません。
				wMessage = "6003006";
				return false;
			}
			
			// 作業者名を取得する。
			String workerName = getWorkerName(conn, wParam[0].getWorkerCode());
			
			if (wParam != null)
			{
				for (int i = 0; i < wParam.length; i ++)
				{
					// 既に登録されている出荷予定情報を取得する。
					wInStock = wObj.findInstockPlanForUpdate(wParam[i]);

					// 排他チェックを行う。
					if (!check(conn, wParam[i]))
					{
						// 一部完了の場合は登録できない
						if (wInStock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETE_IN_PART))
						{
							// 6023273=No.{0}{1}
							// 6023270 = 一部受付済みの同一データが存在するため、入力できません。
							wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023270");
							return false;
						}
						// 作業中の場合は登録できない
						else if (wInStock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_NOWWORKING))
						{
							// 6023273=No.{0}{1}
							// 6023269=作業中の同一データが存在するため、入力できません。
							wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023269");
							return false;
						}
						// 完了の場合登録できない
						else if (wInStock.getStatusFlag().equals(InstockPlan.STATUS_FLAG_COMPLETION))
						{
							// 6023273=No.{0}{1}
							// 6023090 = 既に同一データが存在するため、入力できません。
							wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023090");
							return false;
						}
					}
						
					// 既に登録されている場合
					if (wInStock != null)
					{
							// 入荷予定情報、作業情報、在庫情報の状態を削除に更新し、紐づく次作業情報を更新します。
							wObj.updateInstockPlan(wInStock.getInstockPlanUkey(), wProcessName);
					}
					
					// バッチNo.を取得する。
					if (batch_seqno == null)
					{
                        batch_seqno = wSequence.nextInstockPlanBatchNo();
					}
					
					// パラメータに登録する値をセットする。
					// 作業予定数
					wParam[i].setTotalPlanQty(wParam[i].getEnteringQty() * wParam[i].getPlanCaseQty() + wParam[i].getPlanPieceQty());
					// バッチNo.
					wParam[i].setBatchNo(batch_seqno);
					// 作業者コード
					wParam[i].setWorkerCode(wParam[0].getWorkerCode());
					// 作業者名
					wParam[i].setWorkerName(workerName);
					// 端末No.
					wParam[i].setTerminalNumber(wParam[0].getTerminalNumber());
					// 登録区分
					wParam[i].setRegistKbn(InstockReceiveParameter.REGIST_KIND_WMS);
					// 登録処理名
					wParam[i].setRegistPName(wProcessName);
					// 入荷予定情報、作業情報、在庫情報の登録処理呼び出し
					wObj.createInstockPlan(wParam[i]);
					
				}
			
				// 6001003 = 登録しました。
				wMessage = "6001003";
				registFlag = true;
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			// 登録に失敗した場合はトランザクションをロールバックする
			if (!registFlag)
			{
				doRollBack(conn, wProcessName);
			}
			
			// 取り込み中フラグが自クラスで更新されたものであるならば、
			// 取り込み中フラグを、0：停止中にする
			if( updateLoadDataFlag )
			{
				// 取り込み中フラグ更新：停止中
				updateLoadDataFlag(conn, false);
				
				doCommit(conn, wProcessName);
			}
		}	
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

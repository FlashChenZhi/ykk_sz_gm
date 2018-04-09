package jp.co.daifuku.wms.instockreceive.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.coordinated.instockstorage.InstockStoragePlanCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * 入荷完了処理を行うためのクラスです。<BR>
 * 作業No.、作業区分、処理名、作業者コード、作業者名、端末No.を受け取り、入荷完了処理を行います。(<CODE>complete()</CODE>メソッド) <BR>
 * 作業No.と作業区分をもとに作業情報の検索を行い、在庫情報の更新を行います。<BR>
 * 処理名、作業者コード、作業者名、端末No.は各テーブル登録用に使用します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <DIR>
 *   -在庫情報テーブル(DMSTOCK)の更新 <BR>
 *   <BR>
 *   1.在庫情報の在庫ステータスを完了に更新する。(完了、欠品どちらの場合も完了とする。)<BR>
 *   2.最終更新処理名を更新する。<BR> 
 *   3.作業No.と作業区分をもとに作業情報の検索を行い、在庫数、引当数を更新する。<BR> 
 *     <DIR>
 *     在庫数 = 在庫数 - 出荷数(作業情報の実績数)<BR>
 *     引当数 = 引当数 - (作業情報の実績数 + 欠品数)<BR>
 *     在庫数、引当数は予定データ取り込み時にセットされる。引当数は、上記処理の結果0になる。<BR>
 *     分納が行われた場合は、確定分ずつ減算されて、最終的に0になる。<BR>
 *     </DIR> 
 *   <BR> 
 *   -実績送信情報テーブル(DNHOSTSEND)の登録 <BR> 
 *   <BR>
 *   作業情報のエンティティから実績送信情報のエンティティを生成する。<BR> 
 *     <DIR>
 *     作業日(システム定義日付)と処理名をセットする。<BR>
 *     </DIR>
 *   <BR> 
 *   -入庫予定作成処理(InstockStoragePlanCreator)の呼び出しを行う <BR> 
 *   <BR>
 *   作業情報のTC/DC区分がDCの場合、作業情報より入庫予定、作業情報、在庫情報を生成する。<BR> 
 *     <DIR>
 *     作業日(システム定義日付)と処理名をセットする。<BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>M.Inoue</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class InstockRecieveCompleteOperator extends AbstractInstockReceiveSCH
{
	//	Class variables -----------------------------------------------
	/**
	 * デリミタ
	 */
	protected String wDelim = MessageResource.DELIM;

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
	public InstockRecieveCompleteOperator()
	{
	}

	// Public methods ------------------------------------------------

	/**
	 * 作業No.と作業区分をもとに作業情報テーブルの検索を行い、在庫情報テーブルの更新を行います。<BR>
	 * また、実績送信情報テーブルに実績を作成します。<BR>
	 * @param conn データベースとのコネクションオブジェクト<BR>
	 * @param jobno      作業No.<BR>
	 * @param jobtype    作業区分<BR>
	 * @param pname      処理名<BR>
	 * @return 更新に成功した場合trueを返します。(失敗した場合、原因となるExceptionをスローするので、現状falseを返すことはありません。)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 対象のデータが見つからない場合など、データ不整合時に通知されます。
	 */
	public boolean complete(Connection conn, String jobno, String jobtype, String pname) throws ReadWriteException, ScheduleException
	{
		try
		{
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingsearchKey = new WorkingInformationSearchKey();

			// データの検索
			// 作業No.
			workingsearchKey.setJobNo(jobno);
			// 作業区分
			workingsearchKey.setJobType(jobtype);

			WorkingInformation working = (WorkingInformation) workingHandler.findPrimary(workingsearchKey);

			if (working != null)
			{
				// 在庫ID
				String stockid = working.getStockId();
				// 作業実績数
				int resultqty = working.getResultQty();
				// 作業情報の実績数 + 欠品数
				int totalqty = working.getResultQty() + working.getShortageCnt();
								
				WareNaviSystemHandler warenaviHandler = new WareNaviSystemHandler(conn);
				WareNaviSystemSearchKey wsearchKey = new WareNaviSystemSearchKey();
		
				// データの検索
				WareNaviSystem wms = (WareNaviSystem) warenaviHandler.findPrimary(wsearchKey);
				if (wms == null)
				{
					// 6027006=データの不整合が発生しました。ログを参照してください。TABLE={0}
					RmiMsgLogClient.write("6006040" + wDelim + "WareNaviSystem", "ShippingCompleteOperator");
					// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
					throw (new ScheduleException());
				}
				
				// 作業日(システム定義日付)
				String sysdate = getWorkDate(conn);
							 
				// 在庫情報テーブル(DMSTOCK)の更新
				updateStock(conn, stockid, pname, resultqty, totalqty);
				
				// 実績送信情報テーブル(DNHOSTSEND)の登録
				// 作業情報のエンティティから実績送信情報のエンティティを生成する。
				// 作業日(システム定義日付)と処理名をセットする。
				HostSendHandler hostsendHandler = new HostSendHandler(conn);
		
				HostSend hostsend = new HostSend(working, sysdate, pname);
				
				// データの登録
				hostsendHandler.create(hostsend);
				
				// 入庫予定作成処理（入荷入庫連携）
				InstockStoragePlanCreator planCre = new InstockStoragePlanCreator(conn);
				planCre.startMakePlan(conn, working);
				
				return true;
				
			}
			else
			{
				// 6006040 = データの不整合が発生しました。ログを参照してください。{0}
				RmiMsgLogClient.write("6006040" + wDelim + jobno, "ShippingCompleteOperator");
				// ここで、ScheduleExceptionをthrowする。(エラーメッセージはセット不要です)
				throw (new ScheduleException());
			}
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//	Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 在庫情報テーブルを更新します。
	 * @param conn データベースとのコネクションオブジェクト
	 * @param stockid   在庫ID
	 * @param pname     処理名
	 * @param resultqty 作業実績数
	 * @param totalqty  作業情報の実績数 + 欠品数
	 * @return 更新に成功した場合trueを返します。(失敗した場合、原因となるExceptionをスローするので、現状falseを返すことはありません。)
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 対象のデータが見つからない場合など、データ不整合時に通知されます。
	 */
	protected boolean updateStock(Connection conn, String stockid, String pname, int resultqty, int totalqty) throws ReadWriteException, ScheduleException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			StockAlterKey stockalterKey = new StockAlterKey();
			StockSearchKey stocksearchKey = new StockSearchKey();
	
			// 在庫IDをセット
			stockalterKey.setStockId(stockid);

			// 最終更新処理名を更新する。 
			stockalterKey.updateLastUpdatePname(pname);
	
			// 作業No.と作業区分をもとに作業情報の検索を行い、在庫数、引当数を更新する。
			// 在庫数、引当数は予定データ取り込み時にセットされる。引当数は、上記処理の結果0になる。
			// ＨＴ処理で保留が行われた場合は、確定分ずつ減算されて、最終的に0になる。 
			stocksearchKey.setStockId(stockid);
			Stock stock = (Stock) stockHandler.findPrimary(stocksearchKey);
	
			if (stock != null)
			{
				// 在庫数 = 在庫数 - 入荷数(作業情報の実績数)
				int stockqty = stock.getStockQty() + resultqty;
				stockalterKey.updateStockQty(stockqty);
	
				// 予定数 = 予定数 - (作業情報の実績数 + 欠品数)
				int plan_qty = stock.getPlanQty() - totalqty;
				if (plan_qty < 0)
					plan_qty = 0;
				stockalterKey.updatePlanQty(plan_qty);
				// 完了
				if (plan_qty <= 0)
				{
					// 在庫情報の在庫ステータスを完了に更新する
					stockalterKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				}
				// データの更新
				stockHandler.modify(stockalterKey);
				
				return true;
	
			}
			else
			{
				// 6006040 = データの不整合が発生しました。{0}
				RmiMsgLogClient.write("6006040", "ShippingCompleteOperator");
				//ここで、ScheduleExceptionをエラーメッセージ付きでthrowする。
				// 6027006 = データの不整合が発生しました。ログを参照してください。TABLE={0}
				throw (new ScheduleException("6027006" + wDelim + "DmStock"));
			}
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//	Private methods -----------------------------------------------
}
//end of class

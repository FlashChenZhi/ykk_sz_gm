// $Id: Id0631Operate.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.stockcontrol.rft;
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.merger.StockMGWrapper;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId5631;

/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * RFTからの予定外入庫実績送信に対する処理を行います。<BR>
 * 在庫情報の更新を行います。<BR>
 * Id0151Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/15</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class Id0631Operate extends IdOperate
{
	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static final String PROCESS_NAME = "ID0631";
	/**
	 * クラス名
	 */
	private static final String CLASS_NAME = "Id0631Operate";
	
	/**
	 * エラー詳細
	 */
	private String errorDetail = RFTId5631.ErrorDetails.NORMAL;
	
	/**
	 * 予定データ補完ラッパクラス
	 */
	private StockMGWrapper wMGWrapper = null;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します<BR>
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $";
	}
	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	
	/**
	 * インスタンスの初期化をします。<BR>
	 */
    public void initialize() throws ReadWriteException
    {
		wMGWrapper = new StockMGWrapper(wConn);
    }

	
	/**
	 * 予定外入庫実績送信の処理を行います。<BR>
	 * 確定処理を行います。<BR>
	 * <BR>
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param  stock           在庫情報Entity（電文内容保持）
	 * @param  wrokaTime		作業時間
	 * @return					応答電文の応答フラグ
	*/
	public String doComplete(
		String workerCode,
		String rftNo,
		Stock stock,
		int workTime)
	{
		try
		{
			//-----------------
			// 日次処理中チェック
			//-----------------
			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			if (baseOperate.isLoadingDailyUpdate())
			{
				// 応答フラグを 5:日次更新処理中 でリターン
				return RFTId5631.ANS_CODE_DAILY_UPDATING;
			}

			// 完了処理を行う
			noplanStorageComplete(
					workerCode,
					rftNo,
					stock,
					workTime);

			wConn.commit();
		}
		catch (LockTimeOutException e)
		{			
			// SELECT FOR UPDATE タイムアウト
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				// 6006002=データベースエラーが発生しました。{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：他端末でメンテナンス中
			return RFTId5631.ANS_CODE_MAINTENANCE ;
		}
		// 検索した情報が見つからない場合
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + stock.getConsignorCode() +
							" ItemCode:" + stock.getItemCode() +
							" LocationNo:" + stock.getLocationNo() +
							" CasePieceFlag:" + stock.getCasePieceFlag() +
							" UseByDate:" + stock.getUseByDate() +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			// 6026016=更新対象データが見つかりません。{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.NULL;
			// 応答フラグ：エラー
			return RFTId5631.ANS_CODE_ERROR ;
		}
		// データアクセスでエラーがあった場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			if(errorDetail.equals(RFTId5631.ErrorDetails.NORMAL))
			{
				errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			}

			// 応答フラグ：エラー
			return RFTId5631.ANS_CODE_ERROR ;
		}
		// 在庫作成時にオーバーフローのエラーがあった場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + stock.getConsignorCode() +
							" ItemCode:" + stock.getItemCode() +
							" LocationNo:" + stock.getLocationNo() +
							" CasePieceFlag:" + stock.getCasePieceFlag() +
							" UseByDate:" + stock.getUseByDate() +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			// 6026027=オーバーフロー発生のため、処理を中止しました。{0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.OVERFLOW;
			// 応答フラグ：エラー
			return RFTId5631.ANS_CODE_ERROR ;
		}
		// その他のエラーがあった場合
		catch (InvalidDefineException e)
		{
			// 6026022=指定された値は、空白か、禁止文字が含まれています。{0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
			// 応答フラグ：エラー
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			errorDetail =  RFTId5631.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return  RFTId5631.ANS_CODE_ERROR;
		}
		catch (UpdateByOtherTerminalException e)
		{
			//6026017=更新対象データは、他で更新された為更新できません。{0}
			RftLogMessage.printStackTrace(6026017, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			errorDetail =  RFTId5631.ErrorDetails.UPDATE_FINISH;
			return  RFTId5631.ANS_CODE_ERROR;
		}
		catch (SQLException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細		
			errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			// 応答フラグ：エラー
			return RFTId5631.ANS_CODE_ERROR ;
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "InstockReceiveOperate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (InvalidStatusException e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (ScheduleException e)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (ShelfInvalidityException e)
		{
		    String errData = "[ConsignorCode:" + stock.getConsignorCode() +
							" ItemCode:" + stock.getItemCode() +
							" LocationNo:" + stock.getLocationNo() +
							" CasePieceFlag:" + stock.getCasePieceFlag() +
							" UseByDate:" + stock.getUseByDate() +
		    				" RftNo:" + rftNo +
							" WorkerCode:" + workerCode + "]";
		    
			// 6022039=指定された棚は自動倉庫の棚のため入力できません。{0}
			RftLogMessage.print(6022039, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6023443, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.SHELF_INVALIDITY;
			return RFTId5631.ANS_CODE_ERROR;
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// エラー詳細
			errorDetail = RFTId5631.ErrorDetails.INTERNAL_ERROR;			
			// 応答フラグ：エラー
			return RFTId5631.ANS_CODE_ERROR ;
		}

		
		return RFTId5631.ANS_CODE_NORMAL;
	}

	/**
	 * 予定外入庫実績送信 [確定処理]<BR>
	 * 予定外入庫実績情報について、以下の確定処理を実行します。<BR>
	 * <BR>
	 * 在庫情報の該当データをロックします。(排他処理用)<BR>
	 * ロック時にタイムアウトが発生した場合(排他処理の引当と重複した場合等)は<BR>
	 * エラー応答(他端末で引当処理中)を返します。<BR>
	 * 入庫棚よりAS/RS棚情報を検索します。該当データが存在した場合(自動倉庫の棚を指定された場合)は<BR>
	 * エラー応答(入力ロケーション無効)を返します。<BR>
	 * 在庫情報の更新。在庫数を加算します。<BR>
	 *  (同一在庫が存在する場合は積み増し。存在しない場合は新規作成します。)<BR>
	 * 補完機能が有効の場合で、新規在庫を作成する場合、荷主コードを条件に荷主マスタに登録されている荷主名称を取得し、
	 * 新規在庫データに荷主名称をセットします。また、商品コード、荷主コードを条件に商品マスタに登録されている商品名称を取得し、
	 * 新規在庫データに商品名称をセットし、補完処理を行います。
	 * ({@link MergerWrapper#complete(AbstractEntity) complete()})<BR>
	 * 入庫棚に該当するロケーション情報の更新を行います。<BR>
	 * 実績送信情報の作成。予定外入庫完了した実績を作成します。<BR>
	 * 作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 * <BR>
	 * AS/RS棚情報の検索(共通棚検索メソッドを呼び出す)<BR>
	 * <BR>
	 * 在庫情報のロック<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *    <LI>ロケーションNo=電文のロケーション</LI>
	 *    <LI>商品コード=電文のJANコード</LI>
	 *    <LI>在庫ステータス=2:センター在庫</LI>
	 *    <LI>賞味期限=電文の賞味期限</LI>
	 *    <LI>荷主コード=電文の荷主コード</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * 在庫情報の更新（共通入庫メソッドを呼び出す）<BR>
	 * 実績送信情報の作成<BR>
	 * <BR>
	 * 作業者実績情報の更新<BR>
	 * <BR>
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	locationNo		ロケーション
	 * @param	consignorCode	荷主コード
	 * @param	janCode			JANコード
	 * @param	useByDate		賞味期限
	 * @param	resultQty		予定外入庫実績数
	 * @throws NotFoundException 更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定された値が異常（空白や、禁止文字が含まれている）時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException 移動情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException 作業中に該当データが他で更新された時に通知されます。
	 * 			更新された内容によっては、該当データが見つからず、NotFoundExceptionとなることがあります。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 * @throws ShelfInvalidityException 入力ロケーションが無効の場合に通知されます。
	 * @throws ScheduleException 
	 * @throws InvalidStatusException 
	*/
	public void noplanStorageComplete(
			String workerCode,
			String rftNo,
			Stock stock,
			int workTime)
		throws 
			NotFoundException, 
			InvalidDefineException, 
			ReadWriteException, 
			DataExistsException, 
			UpdateByOtherTerminalException,
			LockTimeOutException,
			OverflowException, InvalidStatusException, ScheduleException, ShelfInvalidityException
	{
		
		String stockId = null;

		LocateOperator lOperator = new LocateOperator(wConn);

		if(lOperator.isAsrsLocation(stock.getLocationNo()))
		{
			// 6023443=No.{0} 指定された棚は自動倉庫の棚のため入力できません。
			throw new ShelfInvalidityException();
		}
		
		if (SystemParameter.withStockManagement())
		{
			
			// 在庫情報のロック
			Stock findStock = stockSearchForUpdate(stock);
			if (findStock == null)
			{
				// 在庫情報の新規登録
				stockId = createStock(stock);
			}
			else
			{
				// 在庫情報の更新
				stockId = findStock.getStockId();
				updateStock(stock, findStock.getStockId(), findStock.getStockQty(), findStock.getAllocationQty());
			}
		}
		
		// 入庫した棚の、ロケーション情報の更新を行います。(2005/06/14 Add By:T.T)
		lOperator.modifyLocateStatus(stock.getLocationNo(), PROCESS_NAME);
		// エリアをセットします。
		stock.setAreaNo(lOperator.getAreaNo(stock.getLocationNo()));
		
		// 作業者実績の更新(作成)
		createWorkerResult(workerCode, rftNo, stock.getStockQty(), workTime);
			
		// 実績送信情報の登録
		createHostsend(stockId, stock, workerCode, rftNo);
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	
	// Private methods -----------------------------------------------
	/**
	 * 作業者実績を更新します。
	 * <DIR>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>作業終了日時</TD>	<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業区分</TD>		<TD>21:予定外入庫</TD></TR>
	 *      <TR><TD>作業数量</TD>		<TD>+予定外入庫実績数</TD></TR>
	 *      <TR><TD>作業回数</TD>		<TD>+1 </TD></TR>
	 *      <TR><TD>作業回数(オーダー数)<TD>+1</TD></TR>
	 *      <TR><TD>作業時間</TD>		<TD>電文の作業時間</TD></TR>
	 *      <TR><TD>実作業時間</TD>		<TD>電文の作業時間</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	resultQty		移動出庫実績数
	 * @param	workTime		作業時間
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	private void createWorkerResult(
			String workerCode, 
			String rftNo, 
			int resultQty,
			int workTime) throws ReadWriteException
	{
		BaseOperate bo = new BaseOperate(wConn);
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			// システム定義情報から作業日を取得できなかった場合は
			// ReadWriteExceptionをthrowする
			errorDetail = RFTId5631.ErrorDetails.NULL;
			throw (new ReadWriteException());
		}

		// 作業者別実績の更新
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_EX_STORAGE);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkQty(resultQty);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);
		
		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			// 更新対象作業者実績が存在しない場合は新規作成
			// （RFTで作業中に日次更新された場合等）
			// 6022004=該当作業者実績データが存在しない為、新規作成します。{0}
			String errData = "[WorkDate:" + workingDate +
			 				 " JobType:" + WorkerResult.JOB_TYPE_EX_STORAGE +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				// 作業者実績作成
				bo.createWorkerResult(WorkerResult.JOB_TYPE_EX_STORAGE, workerCode, rftNo);
				// 作業者実績更新
				bo.alterWorkerResult(wr);
			}
			catch(NotFoundException e1)
			{
				errorDetail = RFTId5631.ErrorDetails.NULL;
				throw (new ReadWriteException());
			}
			catch (Exception e1)
			{
				// 例外が発生した場合はReadWriteExceptionをthrowする
				throw (new ReadWriteException());
			}
		}
	}

	/**
	 * <BR>
	 * 在庫情報をロックします。<BR>
	 *   <DIR>
	 *     荷主コード、ロケーションNo.、商品コード、在庫ステータス(センター在庫)、荷姿、
	 *     賞味期限（在庫を一意にする項目としてWmsParamに定義されている場合）にてデータを検索する。
	 *     <BR>
	 *     データが存在する場合、<BR>
	 *	   該当する在庫情報をロックし、Trueを返します。<BR>
	 *     <BR>
	 *     データが存在しない場合、Falseを返します。<BR>
	 *     <BR>
	 *   </DIR>
	 * <BR>
	 * @param	stock 在庫情報Entity
	 * @return 在庫情報         
	 * @throws ReadWriteException
	 * @throws LockTimeOutException
	 * @throws DataExistsException 
	 */
	private Stock stockSearchForUpdate(Stock stock) throws ReadWriteException, LockTimeOutException, DataExistsException
	{
		// 在庫情報ハンドラ類のインスタンス生成
		StockHandler stockHandler = new StockHandler(wConn);
		StockSearchKey searchKey = new StockSearchKey();

		// 検索条件を設定する
		// 荷主コード
		searchKey.setConsignorCode(stock.getConsignorCode());
		// ロケーションNo.
		searchKey.setLocationNo(stock.getLocationNo());
		// 商品コード
		searchKey.setItemCode(stock.getItemCode());
		// 在庫ステータス＝センター在庫
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// ケース/ピース区分(荷姿)
		// 指定なし
		if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		// ケース
		else if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if(stock.getCasePieceFlag().equals(Stock.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		// 賞味期限
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			if (!StringUtil.isBlank(stock.getUseByDate()))
			{
				searchKey.setUseByDate(stock.getUseByDate());
			}
			else
			{
				searchKey.setUseByDate("", "");
			}
		}
		
		// データのロック
		int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;
		Stock findStock[] = (Stock[])stockHandler.findForUpdate(searchKey, timeout);
		
		if (findStock.length == 1)
		{
			return findStock[0];
		}
		else if (findStock.length > 1)
		{
			// 対象在庫が複数ある場合はエラーとする
			throw new DataExistsException();
		}

		return null;
	}

	/**
	 * <BR>
	 * 在庫情報を登録します。<BR>
	 * @param	stock	在庫情報Entity
	 * @return stockId_seqno シーケンスNo
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws InvalidStatusException 
	 * @throws DataExistsException 
	 * @throws ScheduleException 
	 */
	protected String createStock(Stock stock) throws ReadWriteException, InvalidStatusException, DataExistsException, ScheduleException
	{

			StockHandler stockHandler = new StockHandler(wConn);

			// 登録する各一意キーを取得する。
			SequenceHandler sequence = new SequenceHandler(wConn);
			// 在庫ID
			String stockId_seqno = sequence.nextStockId();
			stock.setStockId(stockId_seqno);
			// エリアNo.
			// ロケーション情報よりエリアNoの取得を行います。(2005/06/14 Add By:T.T)
			LocateOperator lOperator = new LocateOperator(wConn);
			stock.setAreaNo(lOperator.getAreaNo(stock.getLocationNo()));
			
			// 在庫ステータス(センター在庫)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// 引当可能数
			stock.setAllocationQty(stock.getStockQty());
			// 入庫予定数
			stock.setPlanQty(0);
			// 入庫日時
			stock.setInstockDate(new Date());
			// 登録処理名
			stock.setRegistPname(PROCESS_NAME);
			// 最終更新処理名
			stock.setLastUpdatePname(PROCESS_NAME);
			// マスタデータからの補完
			wMGWrapper.complete(stock);
			// データの登録
			stockHandler.create(stock);

			return stockId_seqno;
	}

	/**
	 * <BR>
	 * 在庫情報を更新します。<BR>
	 * @param	stock	 在庫情報Entity
	 * @param  stockId  在庫ID
	 * @param  stockQty 在庫数
	 * @param boolean 処理結果
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 */
	protected boolean updateStock(Stock stock, String stockId, int stockQty, int allocationQty) throws ReadWriteException, OverflowException
	{
		try
		{
			
			// 在庫数のオーバーフローチェックを行う
			if (WmsParam.MAX_STOCK_QTY < (long)stockQty + (long)stock.getStockQty())
			{
				// ここで、OverflowExceptionをthrowする
				throw (new OverflowException());
			}
			
			// 商品名称、在庫数、入庫日時、荷主名称、ケース入数、ボール入数、ケースITF、ボールITF、最終更新処理名を更新します。
			// 引当中の在庫の更新が可能である為、引当数、入庫予定数の更新は行いません。

            // 在庫情報ハンドラ類のインスタンス生成			
			StockHandler stockHandler = new StockHandler(wConn);
			StockAlterKey alterKey = new StockAlterKey();
			
			// 更新条件をセットする
			// 在庫ID
			alterKey.setStockId(stockId);
		    // 更新値をセットする

			// 在庫数
			alterKey.updateStockQty(stockQty + stock.getStockQty());
			// 引当可能数
			alterKey.updateAllocationQty(allocationQty + stock.getStockQty());
			// 入庫日時
			alterKey.updateInstockDate(new Date());
			// 在庫情報の検索条件に、賞味期限を含まない場合
			if (!WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				alterKey.updateUseByDate(stock.getUseByDate());
			}
			// マスタデータからの補完
			wMGWrapper.complete(stock);
			// 荷主名称
			alterKey.updateConsignorName(stock.getConsignorName());
			// 商品名称
			alterKey.updateItemName1(stock.getItemName1());
			// ケース入数
			alterKey.updateEnteringQty(stock.getEnteringQty());
			// ボール入数
			alterKey.updateBundleEnteringQty(stock.getBundleEnteringQty());
			// ケースITF
			alterKey.updateItf(stock.getItf());
			// ボールITF
			alterKey.updateBundleItf(stock.getBundleItf());
			// 最終更新処理名
			alterKey.updateLastUpdatePname(PROCESS_NAME);

			// データの更新
			stockHandler.modify(alterKey);

			return true;
			
		}
		catch (InvalidDefineException e)
		{
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			errorDetail = RFTId5631.ErrorDetails.NULL;
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	/**
	 * 実績送信情報テーブル(DNHOSTSEND)の登録を行います。 <BR> 
	 * <BR>     
	 * 受け取ったパラメータの内容をもとに実績送信情報を登録します。 <BR>
	 * <BR>
	 * @param stockid     在庫ID
	 * @param stock       在庫情報
	 * @param workercode  作業者コード
	 * @param workername  作業者名
	 * @param rftNo	   RFTNo
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	 */
	protected boolean createHostsend(
		String stockid,
        Stock stock,
        String workerCode,
        String rftNo)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			// 実績送信情報ハンドラ類のインスタンス生成
			HostSendHandler hostsendHandler = new HostSendHandler(wConn);
			HostSend hostsend = new HostSend();
			BaseOperate bo = new BaseOperate(wConn);

			// 登録する各一意キーを取得する。
			SequenceHandler sequence = new SequenceHandler(wConn);
			// 作業No.
			String job_seqno = sequence.nextJobNo();
			// 作業日
			hostsend.setWorkDate(bo.getWorkingDate());
			// 作業No.
			hostsend.setJobNo(job_seqno);
			// 作業区分
			hostsend.setJobType(HostSend.JOB_TYPE_EX_STORAGE);
			// 集約作業No.(作業No.と同じ)
			hostsend.setCollectJobNo(job_seqno);
			// 状態フラグ(完了)
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			// 作業開始フラグ(開始済)
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			// 予定一意キー
			hostsend.setPlanUkey("");
			// 在庫ID
			hostsend.setStockId(stockid);
			// エリアNo.
			hostsend.setAreaNo(stock.getAreaNo());
			// ロケーションNo.
			hostsend.setLocationNo(stock.getLocationNo());
			// 予定日
			hostsend.setPlanDate(bo.getWorkingDate());
			// 荷主コード
			hostsend.setConsignorCode(stock.getConsignorCode());
			// 荷主名称
			hostsend.setConsignorName(stock.getConsignorName());
			// 仕入先コード
			hostsend.setSupplierCode("");
			// 仕入先名称
			hostsend.setSupplierName1("");
			// 入荷伝票No.
			hostsend.setInstockTicketNo("");
			// 入荷伝票行No.
			hostsend.setInstockLineNo(0);
			// 出荷先コード
			hostsend.setCustomerCode("");
			// 出荷先名
			hostsend.setCustomerName1("");
			// 出荷伝票No.
			hostsend.setShippingTicketNo("");
			// 出荷伝票行No.
			hostsend.setShippingLineNo(0);
			// 商品コード
			hostsend.setItemCode(stock.getItemCode());
			// 商品名称
			hostsend.setItemName1(stock.getItemName1());
			// 作業予定数(ホスト予定数)
			hostsend.setHostPlanQty(0);
			// 作業予定数
			hostsend.setPlanQty(0);
			// 作業可能数
			hostsend.setPlanEnableQty(0);
			// 作業実績数
			hostsend.setResultQty(stock.getStockQty());
			// 作業欠品数
			hostsend.setShortageCnt(0);
			// 保留数
			hostsend.setPendingQty(0);
			// ケース入数
			hostsend.setEnteringQty(stock.getEnteringQty());
			// ボール入数
			hostsend.setBundleEnteringQty(stock.getBundleEnteringQty());
			// ケース/ピース区分(荷姿)
			hostsend.setCasePieceFlag(stock.getCasePieceFlag());
			// ケース/ピース区分(作業形態)
			hostsend.setWorkFormFlag(stock.getCasePieceFlag());
			// ケースITF
			hostsend.setItf(stock.getItf());
			// ボールITF
			hostsend.setBundleItf(stock.getBundleItf());
			// TC/DC区分
			hostsend.setTcdcFlag(HostSend.TCDC_FLAG_DC);
			// 賞味期限
			hostsend.setUseByDate(stock.getUseByDate());
			// ロットNo.
			hostsend.setLotNo("");
			// 予定情報コメント
			hostsend.setPlanInformation("");
			// オーダーNo.
			hostsend.setOrderNo("");
			// 発注日
			hostsend.setOrderingDate("");
			// 賞味期限
			hostsend.setResultUseByDate(stock.getUseByDate());
			// ロットNo.
			hostsend.setResultLotNo("");
			// 作業結果ロケーション
			hostsend.setResultLocationNo(stock.getLocationNo());
			// 作業報告フラグ(未報告)
			hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			// バッチNo.(スケジュールNo.)
            //String batch_seqno = sequence.nextInstockPlanBatchNo();
            String batch_seqno = sequence.nextNoPlanBatchNo();
			hostsend.setBatchNo(batch_seqno);
			AreaOperator areaOpe = new AreaOperator(wConn);
			// システム識別キー
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(stock.getAreaNo())));
			// 作業者コード
			hostsend.setWorkerCode(workerCode);
			// 作業者名
			hostsend.setWorkerName(bo.getWorkerName(workerCode));
			// 端末No.、RFTNo.
			hostsend.setTerminalNo(rftNo);
			// 予定情報登録日時
			hostsend.setPlanRegistDate(null);
			// 登録日時
			hostsend.setRegistDate(new Date());
			// 登録処理名
			hostsend.setRegistPname(PROCESS_NAME);
			// 最終更新日時
			hostsend.setLastUpdateDate(new Date());
			// 最終更新処理名
			hostsend.setLastUpdatePname(PROCESS_NAME);

			// データの登録
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_INVALID_VALUE;
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			errorDetail = RFTId5631.ErrorDetails.DB_ACCESS_ERROR;
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			errorDetail = RFTId5631.ErrorDetails.NULL;
			throw new ReadWriteException(e.getMessage());
		}
		catch (NumberFormatException e) 
		{
			throw new ScheduleException(e.getMessage());
		} 
	}

	/**
	 * エラー詳細を取得する。
	 * 
	 * @return	エラー詳細
	 */
	public String getErrorDetails()
	{
		return errorDetail;
	}
}
//end of class

// $Id: Id0831Operate.java,v 1.1.1.1 2006/08/17 09:34:21 mori Exp $
package jp.co.daifuku.wms.master.storage.rft;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.master.merger.InventoryMGWrapper;
import jp.co.daifuku.wms.storage.rft.RFTId0831;
import jp.co.daifuku.wms.storage.rft.RFTId5831;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;

/**
 * Designer : Y.Taki <BR>
 * Maker : E.Takeda  <BR>
 * <BR>
 * RFTからの棚卸し実績データ送信に対する処理を行います。<BR>
 * 処理の種類には、 新規、上書き、追加 があります。<BR>
 * Id0831Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:21 $
 * @author  $Author: mori $
 */
public class Id0831Operate extends IdOperate
{
	// Class variables -----------------------------------------------
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
	protected static final String PROCESS_NAME = "ID0831";

	/**
	 * 作業開始処理名（登録処理名、最終更新処理名用）
	 */
	protected static final String START_PROCESS_NAME = "ID0830";

	/**
	 * クラス名（ログ出力用）
	 */
	protected static final String CLASS_NAME = "Id0831Operate";
	
	/**
	 * 在庫管理ありなしのフラグ
	 */
	protected boolean withStockManagement = true;
	
	/**
	 * エラー詳細
	 */
	private String errDetails = RFTId5831.ErrorDetails.NORMAL;
	
	/**
	 * 棚卸データ補完ラッパクラス
	 */
	private InventoryMGWrapper wInventoryMGWrapper = null;

	
	/**
	 * インスタンスの初期化をします。<BR>
	 */
    public void initialize() throws ReadWriteException
    {
    	wInventoryMGWrapper = new InventoryMGWrapper(wConn);
    }


	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:21 $";
	}

	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	/**
	 * 棚卸し実績データ [確定処理]<BR>
	 * 完了区分に応じて棚卸し情報を登録・更新します。<BR>
	 * 
	 * <OL>
	 *   <LI>日時更新処理中かどうかをチェックします。</LI>
	 *   <LI>在庫管理ありの場合、棚卸し対象在庫が存在するかどうかをチェックします。</LI>
	 *   <LI>完了区分に応じて以下のいずれかの処理を呼び出します。
	 *     <UL>
	 *       <LI>棚卸し情報新規登録処理
	 *        （{@link #createInventoryData(InventoryCheck)}）</LI>
	 *       <LI>既存棚卸し情報更新処理
	 *        （{@link #updateInventoryData(InventoryCheck)}）</LI>
	 *       <LI>既存棚卸し情報追加処理
	 *        （{@link #addInventoryQty(InventoryCheck)}）</LI>
	 *     </UL></LI>
	 * </OL>
	 * 
	 * 棚卸し確定処理完了後、作業者実績の作成を行います。
	 * （{@link #updateWorkerResult(String,String,int)}）
	 * <BR>
	 * @param	resultData				棚卸し情報
	 * @param	originalInventoryQty	元棚卸し数
	 * @param	completionFlag			完了区分（0：新規、1：上書き、2：追加）
	 * @param	enableCreateNewStock	新規在庫作成区分（0：作成不可、1：作成可）
	 * @param	workTime				作業時間
	 * @return							応答電文の応答フラグ
	*/
	public String doComplete(
		InventoryCheck resultData,
		int originalInventoryQty,
		String completionFlag,
		String enableCreateNewStock,
		int workTime)
	{
	    String ansCode = "";

	    try
		{
			// 日時処理中かどうかをチェックする
			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			
			// 日次処理中かどうかをチェックする
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 5:日次更新処理中 でリターン
				return RFTId5831.ANS_CODE_DAILY_UPDATING;
			}

			// 在庫管理あり／なしを判定する
			withStockManagement = isWithStockManagement();
			
			// 在庫管理ありの場合、棚卸し対象在庫が存在するかどうかをチェックする
			StockAllocateOperator sao = null;
			Stock[] stock = null;
			if (withStockManagement)
			{
				sao = new StockAllocateOperator();
				try
				{
					stock = sao.stockSearch(
					        wConn,
					        resultData.getConsignorCode(),
					        resultData.getAreaNo(),
					        resultData.getLocationNo(),
					        resultData.getItemCode(),
					        resultData.getUseByDate());
				}
				catch (InvalidDefineException e)
				{
					// 6026022=指定された値は、空白か、禁止文字が含まれています。{0}
					RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
					errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
					// 応答フラグ：エラー
					
					return RFTId5831.ANS_CODE_ERROR;	
				}
			}

			// 新規在庫作成区分が0で対象在庫が存在しない場合、
			// 端末側に在庫データなしを返す
			if (withStockManagement
			        && enableCreateNewStock.equals("0")
			        && stock.length == 0 )
			{
			    return RFTId5831.ANS_CODE_NULL;
			}
			
			// 対象在庫が存在した場合、棚卸し作業時点の在庫数を
			// 棚卸し実績の在庫数にセットする
			if (stock != null && stock.length > 0)
			{
			    int stockQty = 0;
			    for (int i = 0; i < stock.length; i ++)
			    {
			        stockQty += stock[i].getStockQty();
			    }
			    resultData.setStockQty(stockQty);
			}
			else
			{
			    // 対象在庫が存在しない場合は在庫数0とする
			    resultData.setStockQty(0);
			}
			
			// 作業者名称取得
			BaseOperate bo = new BaseOperate(wConn);
			try
			{
				resultData.setWorkerName(bo.getWorkerName(resultData.getWorkerCode()));
			}
			catch (NotFoundException e)
			{
				// 作業者コードなし 開始～データ要求までの間に作業者マスターを使用停止にされた場合
				// 6026019=作業者マスタテーブルに該当作業者が見つかりません。作業者コード:{0}
				RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, resultData.getWorkerCode());
				try
				{
					wConn.rollback();
				}
				catch (SQLException sqlex)
				{
					RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
				}
				// 応答フラグ：エラー
				errDetails = RFTId5831.ErrorDetails.NULL;
				return RFTId5831.ANS_CODE_ERROR;
			}
			
			// 完了区分が 0：新規 の場合
			if (completionFlag.equals(RFTId0831.COMPLETION_FLAG_NEW))
			{
				// createInventoryDataメソッドにて、データを新規登録
				ansCode = createInventoryData(resultData);
			}
			// 完了区分が 1：上書き の場合
			else if (completionFlag.equals(RFTId0831.COMPLETION_FLAG_UPDATE))
			{
				// updateInventoryDataメソッドにて、データを上書き
				ansCode = updateInventoryData(resultData);
			}
			// 完了区分が 2：追加 の場合
			else if (completionFlag.equals(RFTId0831.COMPLETION_FLAG_ADD))
			{
				// addInventoryQtyメソッドにて、棚卸数を追加
				ansCode = addInventoryQty(resultData, originalInventoryQty);
			}
			//それ以外
			else
			{
				// 6026015=ID対応処理で異常が発生しました。{0}
			    String msg = "CompletionFlag = " + completionFlag;
				RftLogMessage.print(6026015, LogMessage.F_ERROR, CLASS_NAME, msg);
				errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
				// エラー
				ansCode = RFTId5831.ANS_CODE_ERROR;
			}

			if (ansCode.equals(RFTId5831.ANS_CODE_NORMAL))
			{
				// 正常に処理が完了した場合のみ作業者実績を作成する
				updateWorkerResult(resultData.getWorkerCode(),
				        resultData.getTerminalNo(),
				        resultData.getResultStockQty(),
				        workTime);
				
				wConn.commit();
			}
			else
			{
			    wConn.rollback();
			}
		}
		// 検索した情報が見つからない場合
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" ItemCode:" + resultData.getItemCode() +
							" Location:" + resultData.getLocationNo() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
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

			// 応答フラグ：エラー
			errDetails = RFTId5831.ErrorDetails.NULL;
			return RFTId5831.ANS_CODE_ERROR;
		}
		// 更新対象データが他端末で更新されていた場合
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" ItemCode:" + resultData.getItemCode() +
							" Location:" + resultData.getLocationNo() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
			// 6026017=更新対象データは、他で更新された為更新できません。{0}
			RftLogMessage.print(6026017, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			// 応答フラグ：エラー
			errDetails = RFTId5831.ErrorDetails.UPDATE_FINISH;
			return RFTId5831.ANS_CODE_ERROR;
			
		}
		// 棚卸し情報作成時にオーバーフローのエラーがあった場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" ItemCode:" + resultData.getItemCode() +
							" Location:" + resultData.getLocationNo() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
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
			// 応答フラグ：エラー
			errDetails = RFTId5831.ErrorDetails.OVERFLOW;
			return RFTId5831.ANS_CODE_ERROR;
		}
		// データアクセスでエラーがあった場合
		catch (ReadWriteException e)
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
			errDetails = RFTId5831.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5831.ANS_CODE_ERROR;
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
			errDetails = RFTId5831.ErrorDetails.DB_ACCESS_ERROR;
			// 応答フラグ：エラー
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (InvalidDefineException e)
        {
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
			// 応答フラグ：エラー
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (NoPrimaryException e)
        {
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			// 応答フラグ：エラー
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (InvalidStatusException e)
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
			errDetails = RFTId5831.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5831.ANS_CODE_ERROR;
        }
        catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.INSTACIATE_ERROR;	
			// 応答フラグ：エラー
			return RFTId5831.ANS_CODE_ERROR;
        }
		catch (ShelfInvalidityException e)
		{
		    String errData = " [LocationNo:" + resultData.getLocationNo() +
		    				 " RftNo:" + resultData.getTerminalNo() +
							 " WorkerCode:" + resultData.getWorkerCode() + "]";
		    
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
			errDetails = RFTId5831.ErrorDetails.SHELF_INVALIDITY;
			return RFTId5831.ANS_CODE_ERROR;
		}
		// その他のエラーがあった場合
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
			errDetails = RFTId5831.ErrorDetails.INTERNAL_ERROR;
			// 応答フラグ：エラー
			return RFTId5831.ANS_CODE_ERROR ;
		}
		
		return ansCode;
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 既存の棚卸し情報を更新する。
	 * 既存データがなかった場合は他端末で更新されたものとみなす。
	 * また、複数件該当した場合もエラーとする。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>棚卸しNo</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>処理フラグ</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>棚卸結果バーコード</TD>	<TD>ID0831.JANコード</TD></TR>
	 *      <TR><TD>在庫数</TD>				<TD>ID0831.在庫数</TD></TR>
	 *      <TR><TD>引当数</TD>				<TD>0</TD></TR>
	 *      <TR><TD>作業予定数</TD>			<TD>0</TD></TR>
	 *      <TR><TD>棚卸し結果数</TD>		<TD>ID0831.棚卸実績数</TD></TR>
	 *      <TR><TD>ケース・ピース区分</TD>	<TD>ID0831.ケース・ピース区分</TD></TR>
	 *      <TR><TD>ケース入数</TD>			<TD>ID0831.ケース入り数</TD></TR>
	 *      <TR><TD>ボール入数</TD>			<TD>ID0831.ボール入り数</TD></TR>
	 *      <TR><TD>端末No</TD>				<TD>ID0831.ハンディ号機No</TD></TR>
	 *      <TR><TD>最終更新日時</TD>		<TD>システム日時</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>		<TD>"ID0831"</TD></TR>
	 *    </TABLE>
	 *   処理フラグが未確定のデータを更新する。
	 *   これらが一致しない場合、他端末で更新されたものとみなす。
	 * </DIR>
	 * @param	inventoryCheck	棚卸し情報
	 * @param	originalInventoryQty	元棚卸し数
	 * @return	正常の場合RFTId1094.ANS_CODE_NORMAL
	 * @throws NotFoundException	更新(削除)対象データがなかった場合に通知されます。
	 * @throws ReadWriteException	データベースとの接続で異常が発生した場合に通知されます。
	 * @throws UpdateByOtherTerminalException 作業中に該当データが他で更新された時に通知されます。
	 * @throws InvalidStatusException
	 * @throws UpdateNByOtherTerminalException	更新対象データが多端末で更新されていた場合に通知されます。
	 */
	protected String updateInventoryData(InventoryCheck inventoryCheck)
		throws NotFoundException, ReadWriteException, InvalidStatusException, UpdateByOtherTerminalException
	{
	    // 荷主、エリアNo、棚、商品コードを元に棚卸し情報を検索する。
	    // （賞味期限違いの在庫があっても全て削除する）
	    InventoryCheckSearchKey skey = new InventoryCheckSearchKey();
	    skey.setConsignorCode(inventoryCheck.getConsignorCode());
	    skey.setAreaNo(inventoryCheck.getAreaNo());
	    skey.setLocationNo(inventoryCheck.getLocationNo());
	    skey.setItemCode(inventoryCheck.getItemCode());
	    skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
	    
	    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
	    ihandler.drop(skey);

	    createNewInventoryCheckData(inventoryCheck);
	    
	    return RFTId5831.ANS_CODE_NORMAL;
	}
	
	/**
	 * 棚卸し情報を新規に作成する。<BR>
	 * 既に同一の棚卸し情報が存在する場合はエラーとする。
	 * 棚No.よりAS/RS棚情報を検索します。該当データが存在した場合(自動倉庫の棚を指定された場合)は<BR>
	 * エラー応答(入力ロケーション無効)を返します。<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>荷主コード</LI>
	 *     <LI>エリアNo</LI>
	 *     <LI>棚No</LI>
	 *     <LI>商品コード</LI>
	 *     <LI>賞味期限（賞味期限管理ありの場合のみ）</LI>
	 *     <LI>処理フラグ = 未確定("0")</LI>
	 *    </UL>
	 *   この条件に該当するデータがあった場合は他端末で更新されたものとみなす。
	 *   該当するデータがなかった場合、データをINSERTする。
	 * </DIR>
	 * 
	 * @param	inventoryCheck	棚卸し情報
	 * @return	正常の場合RFTId1094.ANS_CODE_NORMAL
	 * @throws NoPrimaryException
	 * @throws ReadWriteException
	 * @throws DataExistsException
	 * @throws InvalidStatusException
	 * @throws ReadWriteException
	 * @throws ShelfInvalidityException
	 * @throws ScheduleException
	 */
	protected String createInventoryData(InventoryCheck inventoryCheck)
			throws UpdateByOtherTerminalException, NoPrimaryException, InvalidStatusException, ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		LocateOperator lOperator = new LocateOperator(wConn);
		if(lOperator.isAsrsLocation(inventoryCheck.getLocationNo()))
		{
			// 6023443=No.{0} 指定された棚は自動倉庫の棚のため入力できません。
			throw new ShelfInvalidityException();
		}
		
	    // 荷主、エリアNo、棚、商品コード、賞味期限を元に棚卸し情報を検索する。
	    InventoryCheck target = getInventoryCheckData(inventoryCheck, false);

	    // 同一棚卸しNoを持つデータが存在した場合、他端末で更新済みのエラーとする
	    if (target != null)
	    {
	        throw new UpdateByOtherTerminalException();
	    }
	    
	    createNewInventoryCheckData(inventoryCheck);

	    return RFTId5831.ANS_CODE_NORMAL;
	}

	/**
	 * 既存の棚卸し情報の棚卸数を追加する。<BR>
	 * 既存データがなかった場合は他端末で更新されたものとみなす。
	 * また、複数件該当した場合もエラーとする。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>棚卸しNo</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>処理フラグ</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>棚卸結果バーコード</TD>	<TD>ID0831.JANコード</TD></TR>
	 *      <TR><TD>在庫数</TD>				<TD>ID0831.在庫数</TD></TR>
	 *      <TR><TD>棚卸し結果数</TD>		<TD>(+) ID0831.棚卸実績数</TD></TR>
	 *      <TR><TD>ケース・ピース区分</TD>	<TD>ID0831.ケース・ピース区分</TD></TR>
	 *      <TR><TD>ケース入数</TD>			<TD>ID0831.ケース入り数</TD></TR>
	 *      <TR><TD>ボール入数</TD>			<TD>ID0831.ボール入り数</TD></TR>
	 *      <TR><TD>端末No</TD>				<TD>ID0831.ハンディ号機No</TD></TR>
	 *      <TR><TD>最終更新日時</TD>		<TD>システム日時</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>		<TD>"ID0831"</TD></TR>
	 *    </TABLE>
	 *   処理フラグが未確定、端末Noが自端末、最終更新処理名が"ID0831"、
	 *   棚卸し結果数 = ID0831.元棚卸数のデータを更新する。
	 *   これらが一致しない場合、他端末で更新されたものとみなす。
	 * </DIR>
	 * @param	inventoryCheck	棚卸し情報
	 * @param	originalInventoryQty	元棚卸し数
	 * @return	正常の場合RFTId5831.ANS_CODE_NORMAL
	 * @throws NotFoundException	更新対象データがなかった場合に通知されます。
	 * @throws InvalidDefineException 指定された値が異常（空白や、禁止文字が含まれている）時に通知されます。
	 * @throws NoPrimaryException ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws UpdateByOtherTerminalException	更新対象データが多端末で更新されていた場合に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 */
	protected String addInventoryQty(InventoryCheck inventoryCheck, int originalInventoryQty)
		throws NotFoundException, InvalidDefineException, NoPrimaryException, ReadWriteException, InvalidStatusException, UpdateByOtherTerminalException, OverflowException
	{
	    // 荷主、エリアNo、棚、商品コード、賞味期限を元に棚卸し情報を検索する。
	    InventoryCheck target = getInventoryCheckData(inventoryCheck, false);
	    
	    int inventoryQty = getInventoryQty(
	            inventoryCheck.getConsignorCode(),
	            inventoryCheck.getAreaNo(),
	            inventoryCheck.getLocationNo(),
	            inventoryCheck.getItemCode());

	    // 取得したデータの元棚卸数をチェックする
	    if (inventoryQty != originalInventoryQty)
	    {
	        // 作業開始時から棚卸数が更新されていれば他端末で更新されいる
	        throw new UpdateByOtherTerminalException();
	    }
	    
	    if (target == null)
	    {
	        // 賞味期限違いの場合、新しい賞味期限で棚卸し情報を作成する。
	        createNewInventoryCheckData(inventoryCheck);
	    }
	    else
	    {
		    // 取得したデータの処理フラグをチェックする
		    if (! target.getStatusFlag().equals(InventoryCheck.STATUS_FLAG_NOTDECISION))
		    {
		        // 未確定でなければ他端末で更新されている
		        throw new UpdateByOtherTerminalException();
		    }

		    // AlterKeyに更新値をセットする
		    InventoryCheckAlterKey akey = new InventoryCheckAlterKey();
		    akey.setJobNo(target.getJobNo());
		    
		    akey.updateInvcheckBcr(inventoryCheck.getItemCode());
		    akey.updateStockQty(inventoryCheck.getStockQty());
		    long resultStockQty = target.getResultStockQty() + inventoryCheck.getResultStockQty();
			// 在庫数のオーバーフローチェック
			if (resultStockQty > SystemParameter.MAXSTOCKQTY)
			{
				// 6026026=棚卸数が{0}をこえるため設定できませんでした。
				RftLogMessage.print(6026026, LogMessage.F_ERROR, CLASS_NAME, SystemParameter.DISPMAXSTOCKQTY);
				throw (new OverflowException());
			}

		    akey.updateResultStockQty((int)resultStockQty);
		    akey.updateCasePieceFlag(inventoryCheck.getCasePieceFlag());
		    akey.updateBundleEnteringQty(inventoryCheck.getBundleEnteringQty());
		    akey.updateEnteringQty(inventoryCheck.getEnteringQty());
		    akey.updateTerminalNo(inventoryCheck.getTerminalNo());
		    akey.updateLastUpdatePname(PROCESS_NAME);
			// マスタデータからの補完
	    	wInventoryMGWrapper.complete(target);
		    
		    // 棚卸し情報を更新する
		    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
		    ihandler.modify(akey);
	    }
	    
	    return RFTId5831.ANS_CODE_NORMAL;
	}
	
	/**
	 * 作業者実績を作成する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>作業日 = WMS作業日</LI>
	 *     <LI>作業者コード</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>作業区分 = 棚卸し</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>作業回数</TD>		<TD>(+) 1</TD></TR>
	 *      <TR><TD>作業数</TD>			<TD>(+) ID0831.棚卸実績数</TD></TR>
	 *      <TR><TD>作業終了日時</TD>	<TD>システム日時</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 該当する作業者実績が存在しなかった場合は作成してから再度更新する。
	 * 
	 * @param workerCode		作業者コード
	 * @param rftNo			端末No
	 * @param inventoryQty		棚卸実績数
	 * @param workTiem			作業時間
	 * @throws NotFoundException 更新対象データがなかった場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void updateWorkerResult(String workerCode,
	        String rftNo,
	        int inventoryQty,
	        int workTime) throws NotFoundException, ReadWriteException
	{
		WorkerResult wr = new WorkerResult();
		BaseOperate bo = new BaseOperate(wConn);

		wr.setWorkDate(bo.getWorkingDate());
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_INVENTORY);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkQty(inventoryQty);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);

		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			String errData = "[RftNo:" + workerCode
					+ " WorkerCode:" + rftNo
					+ " JobType:" + WorkerResult.JOB_TYPE_INVENTORY + "]";
			// 6026016=更新対象データが見つかりません。{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
		    try
            {
                bo.createWorkerResult(wr.getJobType(), wr.getWorkerCode(), wr.getTerminalNo());
    			bo.alterWorkerResult(wr);
            }
		    catch (NotFoundException e1)
            {
				//6006002 = データベースエラーが発生しました。{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				errDetails = RFTId5831.ErrorDetails.NULL;
		        throw new ReadWriteException("6006002");
            }
		    catch (DataExistsException e1)
            {
				//6006002 = データベースエラーが発生しました。{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
				errDetails = RFTId5831.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		        throw new ReadWriteException("6006002");
            }
		}
	}
	
	/**
	 * 新しい棚卸し情報を作成する。<BR>
	 *    (作成内容)
	 *    <TABLE border="1">
	 *      <TR><TD>作業No</TD>				<TD>採番</TD></TR>
	 *      <TR><TD>エリアNo</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>ロケーションNo</TD>		<TD>ID0831.棚卸しロケーション</TD></TR>
	 *      <TR><TD>処理フラグ</TD>			<TD>'0':未確定</TD></TR>
	 *      <TR><TD>棚卸結果バーコード</TD>	<TD>ID0831.JANコード</TD></TR>
	 *      <TR><TD>JANコード</TD>			<TD>ID0831.JANコード</TD></TR>
	 *      <TR><TD>商品名称</TD>			<TD>ID0831.商品名称</TD></TR>
	 *      <TR><TD>在庫数</TD>				<TD>在庫情報.在庫数</TD></TR>
	 *      <TR><TD>引当数</TD>				<TD>0</TD></TR>
	 *      <TR><TD>作業予定数</TD>			<TD>0</TD></TR>
	 *      <TR><TD>棚卸し結果数</TD>		<TD>ID0831.棚卸実績数</TD></TR>
	 *      <TR><TD>ケース・ピース区分</TD>	<TD>ID0831.ケース・ピース区分</TD></TR>
	 *      <TR><TD>入庫日</TD>				<TD>' '</TD></TR>
	 *      <TR><TD>最終出庫日</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>賞味期限</TD>			<TD>ID0831.賞味期限または' '</TD></TR>
	 *      <TR><TD>ロットNo</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>予定情報コメント</TD>	<TD>' '</TD></TR>
	 *      <TR><TD>荷主コード</TD>			<TD>ID0831.荷主コード</TD></TR>
	 *      <TR><TD>荷主名称</TD>			<TD>ID0831.荷主名称</TD></TR>
	 *      <TR><TD>仕入先コード</TD>		<TD>' '</TD></TR>
	 *      <TR><TD>仕入先名称</TD>			<TD>' '</TD></TR>
	 *      <TR><TD>ケース入数</TD>			<TD>ID0831.ケース入り数</TD></TR>
	 *      <TR><TD>ボール入数</TD>			<TD>ID0831.ボール入り数</TD></TR>
	 *      <TR><TD>ケースITF</TD>			<TD>ID0831.ケースITF</TD></TR>
	 *      <TR><TD>ボールITF</TD>			<TD>ID0831.ボールITF</TD></TR>
	 *      <TR><TD>担当者コード</TD>		<TD>ID0831.担当者コード</TD></TR>
	 *      <TR><TD>担当者名</TD>			<TD>ID0831.担当者名</TD></TR>
	 *      <TR><TD>端末No</TD>				<TD>ID0831.ハンディ号機No</TD></TR>
	 *      <TR><TD>登録日時</TD>			<TD>システム日時</TD></TR>
	 *      <TR><TD>登録処理名</TD>			<TD>"ID0831"</TD></TR>
	 *      <TR><TD>最終更新日時</TD>		<TD>システム日時</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>		<TD>"ID0831"</TD></TR>
	 *    </TABLE>
	 * 補完機能が有効の場合は、荷主コードを条件にマスタを検索し、登録されている荷主名称、商品名称を取得し、
	 * 棚卸作業情報に反映する。<BR>
	 * @param inventoryCheck	棚卸し作業実績
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws UpdateByOtherTerminalException	対象データが他端末で更新されていた場合に通知されます。
	 */
	protected void createNewInventoryCheckData(InventoryCheck inventoryCheck) 
		throws ReadWriteException, InvalidStatusException, UpdateByOtherTerminalException
	{
		// 新しい棚卸し作業Noを取得する
		SequenceHandler sh = new SequenceHandler(wConn);
		String jobNo = sh.nextJobNo();
		
		// InventoryCheckエンティティにデータをセットする
		InventoryCheck target = new InventoryCheck();
		target.setJobNo(jobNo);
		target.setAreaNo(inventoryCheck.getAreaNo());
		target.setLocationNo(inventoryCheck.getLocationNo());
		target.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		target.setInvcheckBcr(inventoryCheck.getItemCode());
		target.setItemCode(inventoryCheck.getItemCode());
		target.setItemName1(inventoryCheck.getItemName1());
		target.setStockQty(inventoryCheck.getStockQty());
		// 引当可能数に修正
		target.setAllocationQty(inventoryCheck.getStockQty());
		target.setPlanQty(0);
		target.setResultStockQty(inventoryCheck.getResultStockQty());
		target.setCasePieceFlag(inventoryCheck.getCasePieceFlag());
		target.setInstockDate(" ");
		target.setLastShippingDate(" ");
		target.setUseByDate(inventoryCheck.getUseByDate());
		target.setLotNo(inventoryCheck.getLotNo());
		target.setPlanInformation(" ");
		target.setConsignorCode(inventoryCheck.getConsignorCode());
		target.setConsignorName(inventoryCheck.getConsignorName());
		target.setSupplierCode(" ");
		target.setSupplierName1(" ");
		target.setEnteringQty(inventoryCheck.getEnteringQty());
		target.setBundleEnteringQty(inventoryCheck.getBundleEnteringQty());
		target.setItf(inventoryCheck.getItf());
		target.setBundleItf(inventoryCheck.getBundleItf()); 
		target.setWorkerCode(inventoryCheck.getWorkerCode());
		target.setWorkerName(inventoryCheck.getWorkerName());
		target.setTerminalNo(inventoryCheck.getTerminalNo());
		target.setRegistPname(PROCESS_NAME);
		target.setLastUpdatePname(PROCESS_NAME);
		// マスタデータからの補完
		wInventoryMGWrapper.complete(target);
		
		try
		{
		    // 棚卸し情報を新規作成する
		    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
		    ihandler.create(target);
		}
		catch (DataExistsException e)
		{
		    throw new UpdateByOtherTerminalException();
		}
	}
	
	/**
	 * システム定義情報より在庫管理のあり・なしを取得します。<BR>
	 * @return 在庫管理ありの場合true、なしの場合falseを返す。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public boolean isWithStockManagement() throws ReadWriteException
	{
		WareNaviSystemHandler wnSystemHandler = new WareNaviSystemHandler(wConn);
		WareNaviSystemSearchKey wnSystemSearchKey = new WareNaviSystemSearchKey();
		WareNaviSystem[] wnSystem = (WareNaviSystem[])wnSystemHandler.find(wnSystemSearchKey);

		// システム定義情報が取得できない場合はNotFoundExceptionをthrowする。
		if (wnSystem == null || wnSystem.length == 0)
		{
			// 6006040 = データの不整合が発生しました。{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, "WareNavi_System");
			throw new ReadWriteException();
		}
		
		// 在庫パッケージのインストール状態を取得する。
		String stockManagement = wnSystem[0].getStockPack();
	    return stockManagement.equals(WareNaviSystem.PACKAGE_FLAG_ADDON);
	}
	
	/**
	 * 荷主コード、エリアNo, 棚No、商品コード、賞味期限（賞味期限管理ありの場合のみ）で
	 * 処理フラグが未確定の棚卸し情報を検索する。<BR>
	 * 
	 * @param inventoryCheck	棚卸し作業実績
	 * @param isLock			検索したレコードをロックするかどうか
	 * @return	棚卸し情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NoPrimaryException	複数件該当した場合に通知されます
	 */
	public InventoryCheck getInventoryCheckData(
	        InventoryCheck inventoryCheck,
	        boolean isLock) throws ReadWriteException, NoPrimaryException
	{
	    // 荷主、エリアNo、棚、商品コード、賞味期限を元に棚卸し情報を検索する。
	    InventoryCheckSearchKey skey = new InventoryCheckSearchKey();
	    skey.setConsignorCode(inventoryCheck.getConsignorCode());
	    skey.setAreaNo(inventoryCheck.getAreaNo());
	    skey.setLocationNo(inventoryCheck.getLocationNo());
	    skey.setItemCode(inventoryCheck.getItemCode());
	    if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
	    {
	        // 賞味期限管理ありの場合は賞味期限を条件に含める
		    skey.setUseByDate(inventoryCheck.getUseByDate());
	    }
	    skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
	    
	    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
	    InventoryCheck target = null;
	    if (isLock)
	    {
		    target = (InventoryCheck) ihandler.findPrimaryForUpdate(skey);
	    }
	    else
	    {
	        InventoryCheck[] buf = (InventoryCheck[]) ihandler.find(skey);
	        if (buf.length > 0)
	        {
	            target = buf[0];
	        }
	    }

	    return target;
	}

	/**
	 * 荷主コード、棚No、商品コードで現在の棚卸数を取得する。<BR>
	 * 処理フラグが未確定のデータを対象にする<BR>
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	locationNo		棚No
	 * @param	itemCode 		商品コード
	 * @return	棚卸数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public int getInventoryQty(
	        String consignorCode,
	        String areaNo,
	        String locationNo,
	        String itemCode) throws ReadWriteException
	{
	    // 荷主、エリアNo、棚、商品コード、賞味期限を元に棚卸し情報を検索する。
	    InventoryCheckSearchKey skey = new InventoryCheckSearchKey();
	    skey.setConsignorCode(consignorCode);
	    skey.setAreaNo(areaNo);
	    skey.setLocationNo(locationNo);
	    skey.setItemCode(itemCode);
	    skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
	    
	    InventoryCheckHandler ihandler = new InventoryCheckHandler(wConn);
	    InventoryCheck[] inventoryCheck = (InventoryCheck[]) ihandler.find(skey);

	    int inventoryQty = 0;
	    for (int i = 0; i < inventoryCheck.length; i ++)
	    {
	        inventoryQty += inventoryCheck[i].getResultStockQty();
	    }
	    
	    return inventoryQty;
	}
	
	/**
	 * エラー詳細を取得します。
	 * @return エラー詳細
	 */
	public String getErrDetails()
	{
		return errDetails;
	}
}
//end of class

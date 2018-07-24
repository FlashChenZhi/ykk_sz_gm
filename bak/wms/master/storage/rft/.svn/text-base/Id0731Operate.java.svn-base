// $Id: Id0731Operate.java,v 1.1.1.1 2006/08/17 09:34:21 mori Exp $
package jp.co.daifuku.wms.master.storage.rft;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.AllocateException;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.master.merger.MovementMGWrapper;
import jp.co.daifuku.wms.storage.schedule.StockAllocateOperator;
import jp.co.daifuku.wms.storage.rft.RFTId5731;


/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * RFTからの移動出庫実績データ送信に対する処理を行います。<BR>
 * 在庫引当処理及び移動作業情報の作成を行います。<BR>
 * Id0084Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/11</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:21 $
 * @author  $Author: mori $
 */
public class Id0731Operate extends IdOperate
{
	// Class variables -----------------------------------------------
	/**
	 * 処理名
	 */
	private static final String PROCESS_NAME = "ID0731";
	
	/**
	 * クラス名
	 */
	private static final String CLASS_NAME = "Id0731Operate";
	
	/**
	 * エラー詳細
	 */
	private String errorDetails = RFTId5731.ErrorDetails.NORMAL;
	
	/**
	 * 出庫可能在庫数
	 */
	private int storageQty = 0;
	
	/**
	 * 棚卸データ補完ラッパクラス
	 */
	private MovementMGWrapper wMovementMGWrapper = null;

	// Class method --------------------------------------------------	
	/**
	 * インスタンスの初期化をします。<BR>
	 */
    public void initialize() throws ReadWriteException
    {
    	wMovementMGWrapper = new MovementMGWrapper(wConn);
    }

	/**
	 * このクラスのバージョンを返します<BR>
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:21 $");
	}
	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 移動出庫実績データ(ID0084)の処理を行います。<BR>
	 * 電文から該当在庫を判定し、引当処理及び移動作業情報作成処理を行います。<BR>
	 * <BR>
	 * 在庫管理パッケージありの場合
	 * 在庫情報の該当データをロックします。(排他処理用)<BR>
	 * ロック時にタイムアウトが発生した場合(他処理の引当と重複した場合等)は<BR>
	 * エラー応答(他端末で引当処理中)を返します。<BR>
	 * 在庫情報の更新(引当処理)。引当順序に従い、該当在庫の引当数を計上します。<BR>
	 * 移動作業情報の作成。引当結果の移動作業情報を作成します。<BR>
	 * 作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 * <BR>
	 * 在庫管理パッケージなしの場合
	 * 移動作業情報の作成。電文から移動作業情報を作成します。<BR>
	 * 作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 * <BR>
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	consignorCode	荷主コード
	 * @param	consignorName	荷主名称
	 * @param	locationNo		移動元ロケーション
	 * @param	janCode			JANコード
	 * @param	itemName1		商品名称
	 * @param	enteringQty		ケース入数
	 * @param	bundleEntQty	ボール入数
	 * @param	itf				ケースITF
	 * @param	bundleItf		ボールITF
	 * @param	useByDate		賞味期限
	 * @param	resultQty		移動出庫実績数
	 * @param	workTiem		作業時間
	 * @return					応答電文の応答フラグ
	*/
	public String doComplete(
		String workerCode,
		String rftNo,
		String consignorCode,
		String consignorName,
		String locationNo,
		String janCode,
		String itemName1,
		int    enteringQty,
		int    bundleEntQty,
		String itf,
		String bundleItf,
		String useByDate,
		int    resultQty,
		int    workTime)
	{
		try
		{

			// 日時処理中かどうかをチェックする
			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate)PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);
			
			// 日次処理中かどうかをチェックする
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 5:日次更新処理中 でリターン
				return RFTId5731.ANS_CODE_DAILY_UPDATING;
			}

			WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(wConn);
			WareNaviSystem[] wms = (WareNaviSystem[])wmsHandler.find(new WareNaviSystemSearchKey());

			if (wms[0].getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
			{
				// 在庫パッケージありの場合
				CompleteWithStockManagement(
						workerCode,
						rftNo,
						consignorCode,
						locationNo,
						janCode,
						useByDate,
						resultQty,
						workTime);
			}
			else
			{
				// 在庫パッケージなしの場合
				CompleteWithOutStockManagement(
						workerCode,
						rftNo,
						consignorCode,
						consignorName,
						locationNo,
						janCode,
						itemName1,
						enteringQty,
						bundleEntQty,
						itf,
						bundleItf,
						useByDate,
						resultQty,
						workTime);
			}
						
			wConn.commit();
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

			// 応答フラグ：エラー
			errorDetails = RFTId5731.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5731.ANS_CODE_ERROR;
		}
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							 " LocationNo:" + locationNo +
							 " JANCode:" + janCode +
							 " UseByDate:" + useByDate +"]";
			// 6026016=更新対象データが見つかりません。{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				// 6006002=データベースエラーが発生しました。{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：該当在庫なし
			return RFTId5731.ANS_CODE_NULL ;			
		}
		catch (AllocateException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
			 				 " LocationNo:" + locationNo +
							 " JANCode:" + janCode +
							 " UseByDate:" + useByDate +"]";
			// 6026016=更新対象データが見つかりません。{0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				// 6006002=データベースエラーが発生しました。{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：移動可能数超過
			return RFTId5731.ANS_CODE_MOVEMENT_OVER ;			
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
			return RFTId5731.ANS_CODE_MAINTENANCE ;
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
			if(errorDetails.equals(RFTId5731.ErrorDetails.NORMAL))
			{
				errorDetails = RFTId5731.ErrorDetails.DB_ACCESS_ERROR;
			}

			// 応答フラグ：エラー
			return RFTId5731.ANS_CODE_ERROR ;
		}
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
			errorDetails = RFTId5731.ErrorDetails.PARAMETER_ERROR;
			// 応答フラグ：エラー
			return RFTId5731.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
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
			errorDetails = RFTId5731.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			// 応答フラグ：エラー
			return RFTId5731.ANS_CODE_ERROR;
			
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
			errorDetails = RFTId5731.ErrorDetails.DB_ACCESS_ERROR;
			// 応答フラグ：エラー
			return RFTId5731.ANS_CODE_ERROR;
			
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
			// エラー詳細
			errorDetails = RFTId5731.ErrorDetails.INTERNAL_ERROR;
			// 応答フラグ：エラー
			return RFTId5731.ANS_CODE_ERROR ;
		}
		
		return RFTId5731.ANS_CODE_NORMAL;
	}

	/**
	 * 移動出庫実績データ(ID0084)の処理を行います。[在庫管理パッケージあり]<BR>
	 * 電文から該当在庫を判定し、引当処理及び移動作業情報作成処理を行います。<BR>
	 * <BR>
	 * 在庫情報の該当データをロックします。(排他処理用)<BR>
	 * ロック時にタイムアウトが発生した場合(他処理の引当と重複した場合等)は<BR>
	 * エラー応答(他端末で引当処理中)を返します。<BR>
	 * 在庫情報の更新(引当処理)。引当順序に従い、該当在庫の引当数を計上します。<BR>
	 * 移動作業情報の作成。引当結果の移動作業情報を作成します。<BR>
	 * 作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 * <BR>
	 * 在庫情報のロック<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *    <LI>ロケーションNo=電文の移動元ロケーション</LI>
	 *    <LI>商品コード=電文のJANコード</LI>
	 *    <LI>在庫ステータス=2:センター在庫</LI>
	 *    <LI>賞味期限=電文の賞味期限</LI>
	 *    <LI>荷主コード=電文の荷主コード</LI>
	 *    </UL>
	 * </DIR>
	 * <BR>
	 * 在庫情報の引当処理<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *    <LI>ロケーションNo=電文の移動元ロケーション</LI>
	 *    <LI>商品コード=電文のJANコード</LI>
	 *    <LI>在庫ステータス=2:センター在庫</LI>
	 *    <LI>荷主コード=電文の荷主コード</LI>
	 *    </UL>
	 *    (ソート順)引当順
	 *    <UL>
	 *    <LI>ケース/ピース区分</LI>
	 *    <LI>賞味期限</LI>
	 *    <LI>入庫日</LI>
	 *    </UL>
	 *    上記順序で在庫の引当を行い、引当可能数(在庫数-引当数)を上限として<BR>
	 *    移動出庫実績数を分配し、引当数を計上します。
	 * </DIR>
	 * <BR>
	 * 移動作業情報の作成<BR>
	 * <BR>
	 * 作業者実績情報の更新<BR>
	 * <DIR>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>作業終了日時</TD>	<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業区分</TD>		<TD>11:移動出庫</TD></TR>
	 *      <TR><TD>作業数量</TD>		<TD>+移動出庫実績数</TD></TR>
	 *      <TR><TD>作業回数</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 作業者実績が存在しない場合(RFTで移動出庫作業中に日次処理された場合等)は<BR>
	 * 作業者実績情報の作成を行います。<BR>
	 * <BR>
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	consignorCode	荷主コード
	 * @param	locationNo		移動元ロケーション
	 * @param	janCode			JANコード
	 * @param	useByDate		賞味期限
	 * @param	resultQty		移動出庫実績数
	 * @param  workTime		作業時間
	 * @throws InvalidDefineException 指定された値が異常（空白や、禁止文字が含まれている）時に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException 移動情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws NotFoundException 更新対象データが存在しない場合に通知されます。
	 * @throws AllocateException 引当て可能数以上に引き当てようとした(可能数以上の移動出庫をした)場合に通知されます。
	*/
	public void CompleteWithStockManagement(
			String workerCode,
			String rftNo,
			String consignorCode,
			String locationNo,
			String janCode,
			String useByDate,
			int    resultQty,
			int    workTime) 
		throws 
			InvalidDefineException, 
			LockTimeOutException, 
			ReadWriteException, 
			DataExistsException, 
			NotFoundException,
			AllocateException
	{
		try
		{
			StockAllocateOperator stockAllocateOperator = 
				new StockAllocateOperator(WmsParam.WMS_DB_LOCK_TIMEOUT);
	
			// 在庫情報のロック
			String[] checkLocationNo = { locationNo };
			Stock[] stock0 = stockAllocateOperator.stockSearchForUpdate(
					wConn,
					consignorCode,
					checkLocationNo,
					janCode,
					useByDate );
			// 在庫情報が取得できなかった場合はNotFoundExceptionをthrowする
			if( stock0.length == 0 )
			{
				throw (new NotFoundException());
			}
			
			// 在庫引当処理
			Stock[] stock = stockAllocateOperator.stockMovementAllocate(
					wConn,
					consignorCode,
					locationNo,
					janCode,
					useByDate,
					(long)resultQty,
					PROCESS_NAME );
			// 引当可能数が不足していた場合はAllocateExceptionをthrowする
			if( stock.length == 0 )
			{

				for(int i = 0; i < stock0.length; i++)
				{
					storageQty += stock0[i].getAllocationQty();
				}
				throw (new AllocateException());
			}		
		
			// 作業情報の作成
			createMovement( workerCode, rftNo, stock );
	
			// 作業者実績の更新(作成)
			createWorkerResult( workerCode, rftNo, resultQty ,workTime);
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DMSTOCK");
			throw e;
		}
		catch (InvalidDefineException e)
		{
			// 指定値が異常なとき
			String errData =
				"<ConsignorCode[" + consignorCode
					+ "] locationNo[" + locationNo
					+ "] itemCode[" + janCode + "]>";
			throw new InvalidDefineException(errData);
		}
	}

	/**
	 * 移動出庫実績データ(ID0084)の処理を行います。[在庫管理パッケージなし]<BR>
	 * 電文から該当在庫を判定し、引当処理及び移動作業情報作成処理を行います。<BR>
	 * <BR>
	 * 移動作業情報の作成。電文から移動作業情報を作成します。<BR>
	 * 作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 * <BR>
	 * 移動作業情報の作成<BR>
	 * <BR>
	 * 作業者実績情報の更新<BR>
	 * <DIR>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>作業終了日時</TD>	<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業区分</TD>		<TD>11:移動出庫</TD></TR>
	 *      <TR><TD>作業数量</TD>		<TD>+移動出庫実績数</TD></TR>
	 *      <TR><TD>作業回数</TD>		<TD>+1 </TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 作業者実績が存在しない場合(RFTで移動出庫作業中に日次処理された場合等)は<BR>
	 * 作業者実績情報の作成を行います。<BR>
	 * <BR>
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	consignorCode	荷主コード
	 * @param	consignorName	荷主名称
	 * @param	locationNo		移動元ロケーション
	 * @param	janCode			JANコード
	 * @param	itemName1		商品名称
	 * @param	enteringQty		ケース入数
	 * @param	bundleEntQty	ボール入数
	 * @param	itf				ケースITF
	 * @param	bundleItf		ボールITF
	 * @param	useByDate		賞味期限
	 * @param	resultQty		移動出庫実績数
	 * @throws DataExistsException 移動情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	*/
	public void CompleteWithOutStockManagement(
			String workerCode,
			String rftNo,
			String consignorCode,
			String consignorName,
			String locationNo,
			String janCode,
			String itemName1,
			int    enteringQty,
			int    bundleEntQty,
			String itf,
			String bundleItf,
			String useByDate,
			int    resultQty,
			int    workTime) throws DataExistsException, ReadWriteException
	{		
		// 作業情報の作成
		createMovement(
				workerCode,
				rftNo,
				consignorCode,
				consignorName,
				locationNo,
				janCode,
				itemName1,
				enteringQty,
				bundleEntQty,
				itf,
				bundleItf,
				useByDate,
				resultQty);

		// 作業者実績の更新(作成)
		createWorkerResult( workerCode, rftNo, resultQty, workTime );
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 移動作業情報の作成<BR>
	 * <DIR>
	 *    (作成内容)
	 *    <TABLE>
	 *      <TR><TD>作業No</TD>							<TD>採番(レコード単位)</TD></TR>
	 *      <TR><TD>作業区分</TD>						<TD>12:移動入庫</TD></TR>
	 *      <TR><TD>集約作業No</TD>						<TD>採番(作業単位)</TD></TR>
	 *      <TR><TD>在庫ID</TD>							<TD>引当在庫ID</TD></TR>
	 *      <TR><TD>状態フラグ</TD>						<TD>1:入庫待ち</TD></TR>
	 *      <TR><TD>作業開始フラグ</TD>					<TD>1:開始済み</TD></TR>
	 *      <TR><TD>作業日</TD>							<TD>システム定義</TD></TR>
	 *      <TR><TD>作業予定数</TD>						<TD>引当数</TD></TR>
	 *      <TR><TD>作業実績数</TD>						<TD>0</TD></TR>
	 *      <TR><TD>作業欠品数</TD>						<TD>0</TD></TR>
	 *      <TR><TD>作業結果エリアNo</TD>				<TD>(空白)</TD></TR>
	 *      <TR><TD>作業結果ロケーション</TD>			<TD>(空白)</TD></TR>
	 *      <TR><TD>未作業報告フラグ</TD>				<TD>0:未送信</TD></TR>
	 *      <TR><TD>帳票発行フラグ</TD>					<TD>(空白)</TD></TR>
	 *      <TR><TD>バッチNo</TD>						<TD>(空白)</TD></TR>
	 *      <TR><TD>作業者コード</TD>					<TD>(空白)</TD></TR>
	 *      <TR><TD>端末No,RFTNo</TD>					<TD>(空白)</TD></TR>
	 *      <TR><TD>出庫作業者コード</TD>				<TD>電文の作業者コード</TD></TR>
	 *      <TR><TD>出庫作業者名</TD>					<TD>電文の作業者コードより取得</TD></TR>
	 *      <TR><TD>出庫端末No,RFTNo</TD>				<TD>電文のハンディ号機No</TD></TR>
	 *      <TR><TD>登録日時</TD>						<TD>システム日時</TD></TR>
	 *      <TR><TD>登録処理名</TD>						<TD>ID0084</TD></TR>
	 *      <TR><TD>最終更新日時</TD>					<TD>システム日時</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>					<TD>ID0084</TD></TR>
	 *    </TABLE>
	 *   上記以外の項目は引当在庫情報より取得します。
	 *   引当結果により、複数レコード作成します。(引当在庫レコード数と同一)<BR>
	 * </DIR>
	 * 
	 * @param workerCode	作業者コード
	 * @param rftNo			RFTNo
	 * @param stock			引当在庫情報一覧
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException 移動情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 */
	protected void createMovement(String workerCode, String rftNo, Stock[] stock) throws  ReadWriteException, DataExistsException
	{
		SequenceHandler sh = new SequenceHandler(wConn);
		String collectJobNo = "";
		MovementHandler movementHandler = new MovementHandler(wConn);

		BaseOperate bo = new BaseOperate(wConn);
		// システム作業日を取得
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			errorDetails = RFTId5731.ErrorDetails.NULL;
			// システム定義情報から作業日を取得できなかった場合はReadWriteExceptionをthrowする
			throw (new ReadWriteException());
		}
		// 作業者名を取得
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e1)
		{
			// 作業者名が取得できない場合はログ作成し正常処理を行う
			// 6004500=未登録のユーザIDが入力されました：ユーザID={0}
			String errData = "[" + workerCode + "]";
			RftLogMessage.print(6004500, LogMessage.F_WARN, CLASS_NAME, errData);
		}
		
		for( int i=0; i < stock.length; i++ )
		{
			Movement movement = new Movement();
			movement.setJobNo( sh.nextJobNo() );
			if( i == 0 )
			{
				collectJobNo = movement.getJobNo();
			}
			movement.setJobType( Movement.JOB_TYPE_MOVEMENT_STORAGE );
			movement.setCollectJobNo( collectJobNo );
			movement.setStockId( stock[i].getStockId() );
			movement.setAreaNo( stock[i].getAreaNo() );
			movement.setLocationNo( stock[i].getLocationNo() );
			movement.setStatusFlag( Movement.STATUSFLAG_UNSTART );
			movement.setBeginningFlag( Movement.BEGINNING_FLAG_STARTED );
			movement.setWorkDate( workingDate );
			movement.setConsignorCode( stock[i].getConsignorCode() );
			movement.setConsignorName( stock[i].getConsignorName() );
			movement.setSupplierCode( stock[i].getSupplierCode() );
			movement.setSupplierName1( stock[i].getSupplierName1() );
			movement.setItemCode( stock[i].getItemCode() );
			movement.setItemName1( stock[i].getItemName1() );
			// ここでは本来引当可能数が格納される部分に引当数が格納されている。
			movement.setPlanQty( stock[i].getAllocationQty() );
			movement.setResultQty( 0 );
			movement.setShortageCnt( 0 );
			movement.setEnteringQty( stock[i].getEnteringQty() );
			movement.setBundleEnteringQty( stock[i].getBundleEnteringQty() );
			movement.setCasePieceFlag( stock[i].getCasePieceFlag() );
			movement.setWorkFormFlag( stock[i].getCasePieceFlag() );
			movement.setItf( stock[i].getItf() );
			movement.setBundleItf( stock[i].getBundleItf() );
			movement.setUseByDate( stock[i].getUseByDate() );
			movement.setLotNo( stock[i].getLotNo() );
			movement.setPlanInformation( stock[i].getPlanInformation() );
			movement.setReportFlag( Movement.REPORT_FLAG_NOT_SENT );
			movement.setRetrievalWorkerCode( workerCode );
			movement.setRetrievalWorkerName( workerName );
			movement.setRetrievalTerminalNo( rftNo );
			movement.setRegistPname( PROCESS_NAME );
			movement.setLastUpdatePname( PROCESS_NAME );
			
			movementHandler.create( movement );
		}
	}

	/**
	 * 移動作業情報の作成<BR>
	 * <DIR>
	 *    (作成内容)
	 *    <TABLE>
	 *      <TR><TD>作業No</TD>							<TD>採番(レコード単位)</TD></TR>
	 *      <TR><TD>作業区分</TD>						<TD>12:移動入庫</TD></TR>
	 *      <TR><TD>集約作業No</TD>						<TD>採番(作業単位)</TD></TR>
	 *      <TR><TD>ロケーションNo</TD>					<TD>電文より取得</TD></TR>
	 *      <TR><TD>状態フラグ</TD>						<TD>1:入庫待ち</TD></TR>
	 *      <TR><TD>作業開始フラグ</TD>					<TD>1:開始済み</TD></TR>
	 *      <TR><TD>作業日</TD>							<TD>システム定義</TD></TR>
	 *      <TR><TD>荷主コード</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>荷主名称</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>商品コード</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>商品名称</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>作業予定数</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>作業実績数</TD>						<TD>0</TD></TR>
	 *      <TR><TD>作業欠品数</TD>						<TD>0</TD></TR>
	 *      <TR><TD>ケース入数</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>ボール入数</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>ケースITF</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>ボールITF</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>賞味期限</TD>						<TD>電文より取得</TD></TR>
	 *      <TR><TD>賞味期限(Result)</TD>				<TD>電文より取得</TD></TR>
	 *      <TR><TD>未作業報告フラグ</TD>				<TD>0:未送信</TD></TR>
	 *      <TR><TD>出庫作業者コード</TD>				<TD>電文の作業者コード</TD></TR>
	 *      <TR><TD>出庫作業者名</TD>					<TD>電文の作業者コードより取得</TD></TR>
	 *      <TR><TD>出庫端末No,RFTNo</TD>				<TD>電文のハンディ号機No</TD></TR>
	 *      <TR><TD>登録日時</TD>						<TD>システム日時</TD></TR>
	 *      <TR><TD>登録処理名</TD>						<TD>ID0084</TD></TR>
	 *      <TR><TD>最終更新日時</TD>					<TD>システム日時</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>					<TD>ID0084</TD></TR>
	 *    </TABLE>
	 *   上記以外の項目は空白とします。
	 * </DIR>
	 * 
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	consignorCode	荷主コード
	 * @param	consignorName	荷主名称
	 * @param	locationNo		移動元ロケーション
	 * @param	janCode			JANコード
	 * @param	itemName1		商品名称
	 * @param	enteringQty		ケース入数
	 * @param	bundleEntQty	ボール入数
	 * @param	itf				ケースITF
	 * @param	bundleItf		ボールITF
	 * @param	useByDate		賞味期限
	 * @param	resultQty		移動出庫実績数
	 * @throws DataExistsException 移動情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createMovement(
			String workerCode,
			String rftNo,
			String consignorCode,
			String consignorName,
			String locationNo,
			String janCode,
			String itemName1,
			int    enteringQty,
			int    bundleEntQty,
			String itf,
			String bundleItf,
			String useByDate,
			int    resultQty) throws DataExistsException, ReadWriteException
	{
		SequenceHandler sh = new SequenceHandler(wConn);
		BaseOperate bo = new BaseOperate(wConn);
		// システム作業日を取得
		String workingDate = "";
		try
		{
			workingDate = bo.getWorkingDate();
		}
		catch (NotFoundException e)
		{
			errorDetails = RFTId5731.ErrorDetails.NULL;
			// システム定義情報から作業日を取得できなかった場合はReadWriteExceptionをthrowする
			throw (new ReadWriteException());
		}
		// 作業者名を取得
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e1)
		{
			// 作業者名が取得できない場合はログ作成し正常処理を行う
			// 6004500=未登録のユーザIDが入力されました：ユーザID={0}
			String errData = "[" + workerCode + "]";
			RftLogMessage.print(6004500, LogMessage.F_WARN, CLASS_NAME, errData);
		}
		
		Movement movement = new Movement();
		movement.setJobNo( sh.nextJobNo() );
		movement.setJobType( Movement.JOB_TYPE_MOVEMENT_STORAGE );
		movement.setCollectJobNo( movement.getJobNo() );
		movement.setAreaNo(Area.Area_VACANTSEARCHTYPE_BANKHORIZONTAL);
		movement.setLocationNo( locationNo );
		movement.setStatusFlag( Movement.STATUSFLAG_UNSTART );
		movement.setBeginningFlag( Movement.BEGINNING_FLAG_STARTED );
		movement.setWorkDate( workingDate );
		movement.setConsignorCode( consignorCode );
		movement.setConsignorName( consignorName );
		movement.setItemCode( janCode );
		movement.setItemName1( itemName1 );
		movement.setPlanQty( resultQty );
		movement.setResultQty( 0 );
		movement.setShortageCnt( 0 );
		movement.setEnteringQty( enteringQty );
		movement.setBundleEnteringQty( bundleEntQty );
		movement.setCasePieceFlag( Movement.CASEPIECE_FLAG_NOTHING );
		movement.setWorkFormFlag( Movement.CASEPIECE_FLAG_NOTHING );
		movement.setItf( itf );
		movement.setBundleItf( bundleItf );
		movement.setUseByDate( useByDate );
		movement.setReportFlag( Movement.REPORT_FLAG_NOT_SENT );
		movement.setRetrievalWorkerCode( workerCode );
		movement.setRetrievalWorkerName( workerName );
		movement.setRetrievalTerminalNo( rftNo );
		movement.setRegistPname( PROCESS_NAME );
		movement.setLastUpdatePname( PROCESS_NAME );
		wMovementMGWrapper.complete(movement);
		MovementHandler movementHandler = new MovementHandler(wConn);
		movementHandler.create( movement );
	}

	/**
	 * 作業者実績を更新します。
	 * <DIR>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>作業終了日時</TD>		<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業数量</TD>			<TD>+今回作業した実績数の総数</TD></TR>
	 *      <TR><TD>作業回数</TD>			<TD>+1 </TD></TR>
	 *      <TR><TD>作業回数（オーダー数）	</TD><TD>+1</TD></TR>
	 *      <TR><TD>作業時間</TD>			<TD>+パラメータの作業時間</TD></TR>
	 *      <TR><TD>実作業時間</TD>			<TD>+パラメータの作業時間</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	resultQty		移動出庫実績数
	 * @param	workTime		作業時間
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createWorkerResult(
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
			errorDetails = RFTId5731.ErrorDetails.NULL;
			// システム定義情報から作業日を取得できなかった場合は
			// ReadWriteExceptionをthrowする
			throw (new ReadWriteException());
		}

		// 作業者別実績の更新
		WorkerResult wr = new WorkerResult();
		wr.setWorkDate(workingDate);
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL);
		wr.setWorkCnt(1);
		wr.setOrderCnt(1);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(workTime);
		wr.setWorkQty(resultQty);
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
			 				 " JobType:" + WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL +
							 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode +"]";
			RftLogMessage.print(6022004, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				// 作業者実績作成
				bo.createWorkerResult(WorkerResult.JOB_TYPE_MOVEMENT_RETRIEVAL, workerCode, rftNo);
				// 作業者実績更新
				bo.alterWorkerResult(wr);
			}
			catch(NotFoundException e1)
			{
				errorDetails = RFTId5731.ErrorDetails.NULL;
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
	 * エラー詳細を取得する。
	 * 
	 * @return	エラー詳細
	 */
	public String getErrorDetails()
	{
		return errorDetails;
	}
	
	/**
	 * 出庫可能在庫数を取得する。
	 * 
	 * @return 出庫可能在庫数
	 */
	public int getStorageQty()
	{
		return storageQty;
	}
	// Private methods -----------------------------------------------
	
}
//end of class

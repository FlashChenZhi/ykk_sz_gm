// $Id: Id0141Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの出荷先単位入荷検品実績送信に対する処理を行います。<BR>
 * 処理の種類には、 分納、確定、キャンセル があります。<BR>
 * Id0141Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0141Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
	private static final String PROCESS_NAME = "ID0141";

	/**
	 * 作業開始処理名（登録処理名、最終更新処理名用）
	 */
	private static final String START_PROCESS_NAME = "ID0140";

	/**
	 * クラス名（ログ出力用）
	 */
	private static final String CLASS_NAME = "Id0141Operate";
	
	// Class variables -----------------------------------------------
	private String errDetails = RFTId5141.ErrorDetails.NORMAL;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}
	
	// Constructors --------------------------------------------------
	
	// Public methods ------------------------------------------------
	/**
	 * 出荷先単位入荷検品実績送信処理<BR>
	 * データファイルから検品実績情報(作業情報エンティティ配列)を取します。<BR>
	 * 作業情報について、受付区分に応じて以下の確定処理を実行します。<BR>
	 * 
	 *  <UL>
	 *   <LI>入荷検品確定処理</LI>
	 *   <LI>入荷検品分納処理</LI>
	 *   <LI>キャンセル処理</LI>
	 *  </UL>
	 * 
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	supplierCode	仕入先コード
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	receiptClass	受付区分
	 * @param	resultFileName	オーダー出庫実績ファイル名
	 * @param	numRecords		ファイルレコード数
	 * @param	workTime		作業時間
	 * @param	missScanCnt		誤検回数
	 * @return					応答電文の応答フラグ
	 */
	public String doComplete(
		String consignorCode,
		String planDate,
		String supplierCode,
		String customerCode,
		String rftNo,
		String workerCode,
		String receiptClass,
		String resultFileName,
		int numRecords,
		int workTime,
		int missScanCnt)
	{
		try
		{
			// ファイル操作オブジェクトを生成する
			Id5140DataFile dataFile = (Id5140DataFile) PackageManager.getObject("Id5140DataFile");
			dataFile.setFileName(resultFileName);

			// データファイルから実績を取得する
			WorkingInformation[] resultData = (WorkingInformation[])dataFile.getCompletionData();
			
			if(!receiptClass.equals(ReceiptClass.CANCEL))
			{
				for (int i = 0; i < resultData.length; i ++)
				{
					if(DisplayText.isPatternMatching(resultData[i].getResultUseByDate()))
					{
						throw new InvalidDefineException("USE_BY_DATE[" + resultData[i].getResultUseByDate() +"]");
					}
				}
			}

		    // 実績データに荷主コード、予定日を入れておく
			for (int i = 0; i < resultData.length; i ++)
			{
			    resultData[i].setConsignorCode(consignorCode);
			    resultData[i].setPlanDate(planDate);
			    resultData[i].setSupplierCode(supplierCode);
			    resultData[i].setCustomerCode(customerCode);
			}

			// RetrievalOperateクラスのインスタンスを生成
			InstockReceiveOperate instockReceiveOperate =
			    (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
			instockReceiveOperate.setConnection(wConn);
			instockReceiveOperate.setProcessName(PROCESS_NAME);
			instockReceiveOperate.setStartProcessName(START_PROCESS_NAME);

			// 受付区分が9:キャンセルの場合
			if(receiptClass.equals(ReceiptClass.CANCEL))
			{
				// 状態フラグを更新する
			    instockReceiveOperate.receivingCancel(
			            resultData,
			            workerCode,
			            rftNo
			            );
			}
			// 受付区分がキャンセル以外
			else
			{
				// ファイルのレコード数をチェックする
				if (resultData.length != numRecords)
				{
					throw new IOException();
				}

				// 完了フラグに応じて完了処理を行う
				if(receiptClass.equals(ReceiptClass.INSTALLMENT))
				{
					// 保留の場合、保留の処理を行う
				    instockReceiveOperate.receivingSuspend(
				            					resultData,
												rftNo,
												workerCode,
												workTime,
												missScanCnt);
				}
				else
				{
					// 正常または欠品の場合、完了処理を行う
				    instockReceiveOperate.receivingComplete(
				            					resultData,
												rftNo, 
												workerCode,
												workTime,
												missScanCnt);
				}
			}
		}
		// 検索した情報が見つからない場合
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" SupplierCode:" + supplierCode +
							" CustomerCode:" + customerCode +
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
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.NULL;
			return RFTId5141.ANS_CODE_ERROR;
		}
		// 更新対象データが他端末で更新されていた場合
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" SupplierCode:" + supplierCode +
							" CustomerCode:" + customerCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
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
			errDetails = RFTId5141.ErrorDetails.UPDATE_FINISH;
			return RFTId5141.ANS_CODE_ERROR ;
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
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5141.ANS_CODE_ERROR ;
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
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5141.ANS_CODE_ERROR ;
        }
        catch (IOException e)
        {
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, resultFileName);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.I_O_ERROR;
			return RFTId5141.ANS_CODE_ERROR;
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
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5141.ANS_CODE_ERROR;
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
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.PARAMETER_ERROR;
			return RFTId5141.ANS_CODE_ERROR;
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
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			return RFTId5141.ANS_CODE_MAINTENANCE;
        }
        catch (DataExistsException e)
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
			// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return RFTId5141.ANS_CODE_ERROR;
        }
        catch (ScheduleException e)
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
			errDetails = RFTId5141.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5141.ANS_CODE_ERROR;
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
    		// 応答フラグ：エラー
			errDetails = RFTId5141.ErrorDetails.INTERNAL_ERROR;
    		return RFTId5141.ANS_CODE_ERROR;
    	}
		
		return RFTId5141.ANS_CODE_NORMAL;
	}

	/**
	 * エラー詳細を取得する。
	 * 
	 * @return	エラー詳細
	 */
	public String getErrorDetails()
	{
		return errDetails;
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

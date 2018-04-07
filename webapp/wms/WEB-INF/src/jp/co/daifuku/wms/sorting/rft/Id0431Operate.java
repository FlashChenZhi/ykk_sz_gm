// $Id: Id0431Operate.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.instockreceive.rft.ReceiptClass;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの仕分実績送信に対する処理を行います。<BR>
 * 処理の種類には、 確定(正常、欠品)、キャンセル があります。<BR>
 * Id0431Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class Id0431Operate extends IdOperate
{
	// Class variables -----------------------------------------------
	private static final String PROCESS_NAME = "ID0431";
	private static final String START_PROCESS_NAME = "ID0430";
	private static final String CLASS_NAME = "Id0431Operate";
	
	private String errDetails = RFTId5431.ErrorDetails.NORMAL;
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:33 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public Id0431Operate()
	{
		super();
	}
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0431Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	// Public methods ------------------------------------------------
	/**
	 * 仕分実績送信(ID0431)の処理を行います。<BR>
	 * 電文の完了区分から処理を判定し、確定処理又はキャンセル処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *      <UL>
	 *      <LI>完了区分 0:正常、1:欠品 ［確定処理］</LI>
	 *      <LI>完了区分 9:キャンセル   ［キャンセル処理］</LI>
	 *      </UL>
	 * </DIR>
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	consignorCode	荷主コード
	 * @param	planDate		仕分予定日
	 * @param	janCode			JANコード
	 * @param	workForm		作業形態(ケース・ピース区分)
	 * @param	receiptClass	完了区分
	 * @param	workerTime		作業時間
	 * @param	missScanCnt		ミス回数
	 * @param	NumRecords		ファイルレコード数
	 * @param	resultFileName	実績ファイル名
	 * @return					応答電文の応答フラグ
	*/
	public String doComplete(
		String workerCode,
		String rftNo,
		String consignorCode,
		String planDate,
		String janCode,
		String workForm,
		int workerTime,
		int missScanCnt,
		int numRecords,
		String receiptClass,
		String resultFileName)
	{
		try
		{
			// ファイル操作オブジェクトを生成する
			Id5430DataFile dataFile = (Id5430DataFile) PackageManager.getObject("Id5430DataFile");
			dataFile.setFileName(resultFileName);

			// データファイルから実績を取得する
			WorkingInformation[] resultData = (WorkingInformation[])dataFile.getCompletionData();
			
			// 実績データに荷主コード、予定日を入れておく
			for (int i = 0; i < resultData.length; i ++)
			{
				resultData[i].setConsignorCode(consignorCode);
				resultData[i].setPlanDate(planDate);
			}

			// SortingOperateクラスのインスタンスを生成
			SortingOperate sortingOperate = (SortingOperate) PackageManager.getObject("SortingOperate");
			sortingOperate.setConnection(wConn);
			sortingOperate.setProcessName(PROCESS_NAME);
			sortingOperate.setStartProcessName(START_PROCESS_NAME);

			if(receiptClass.equals(ReceiptClass.CANCEL))
			{
				// 受付区分が9:キャンセルの場合、状態フラグを更新する
				sortingOperate.sortingCancel(
						resultData,
						rftNo,
						workerCode);
			}
			else
			{
				// ファイルのレコード数をチェックする
				if (resultData.length != numRecords)
				{
					throw new IOException();
				}
				
				// 正常または欠品の場合、完了処理を行う
				sortingOperate.sortingComplete(
						resultData,
						rftNo,
						workerCode,
						workerTime,
						missScanCnt
						);
			}
		}
		// 検索した情報が見つからない場合
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" janCode:" + janCode +
							" workForm:" + workForm +
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
			errDetails = RFTId5431.ErrorDetails.NULL;
			return RFTId5431.ANS_CODE_ERROR;
		}
		// 更新対象データが他端末で更新されていた場合
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
							" janCode:" + janCode +
							" workForm:" + workForm +
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
			errDetails = RFTId5431.ErrorDetails.UPDATE_FINISH;
			return RFTId5431.ANS_CODE_ERROR ;
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
			errDetails = RFTId5431.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5431.ANS_CODE_ERROR ;
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
			errDetails = RFTId5431.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5431.ANS_CODE_ERROR ;
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
			errDetails = RFTId5431.ErrorDetails.I_O_ERROR;
			return RFTId5431.ANS_CODE_ERROR;
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
			errDetails = RFTId5431.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5431.ANS_CODE_ERROR;
		}
		catch (InvalidDefineException e)
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
			errDetails = RFTId5431.ErrorDetails.PARAMETER_ERROR;
			return RFTId5431.ANS_CODE_ERROR;
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
			return RFTId5431.ANS_CODE_MAINTENANCE;
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
			// 応答フラグ：エラー
			errDetails = RFTId5431.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5431.ANS_CODE_ERROR ;
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
			errDetails = RFTId5431.ErrorDetails.INTERNAL_ERROR;
			return RFTId5431.ANS_CODE_ERROR;
		}
		
		return RFTId5431.ANS_CODE_NORMAL;

	}
	
	// Private methods -----------------------------------------------
	/**
	 * エラー詳細を取得する。
	 * 
	 * @return	エラー詳細
	 */
	public String getErrorDetails()
	{
		return errDetails;
	}

}
//end of class

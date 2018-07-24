// $Id: Id0131Operate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * RFTからの商品単位入荷検品実績送信に対する処理を行います。<BR>
 * 処理の種類には、 分納、確定、キャンセル があります。<BR>
 * Id0131Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0131Operate extends IdOperate
{
	// Class fields --------------------------------------------------
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
	private static final String PROCESS_NAME = "ID0131";

	/**
	 * 作業開始処理名（登録処理名、最終更新処理名用）
	 */
	private static final String START_PROCESS_NAME = "ID0130";

	/**
	 * クラス名（ログ出力用）
	 */
	private static final String CLASS_NAME = "Id0131Operate";
	
	// Class variables -----------------------------------------------
	private String errDetails = RFTId5131.ErrorDetails.NORMAL;

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
	 * 商品単位入荷検品実績データ処理<BR>
	 * 作業情報について、受付区分に応じて以下の確定処理を実行します。<BR>
	 * 
	 *  <UL>
	 *   <LI>入荷検品確定処理</LI>
	 *   <LI>入荷検品分納処理</LI>
	 *   <LI>キャンセル処理</LI>
	 *  </UL>
	 * 
	 * <BR>
	 * @param	resultData		作業実績データ
	 * @param	receiptClass	受付区分
	 * @return					応答電文の応答フラグ
	 */
	public String doComplete(
		WorkingInformation resultData,
		String receiptClass,
		int missScanCnt)
	{
		try
		{
			// InstockReceiveOperateクラスのインスタンスを生成
			InstockReceiveOperate instockOperate =
			    (InstockReceiveOperate) PackageManager.getObject("InstockReceiveOperate");
			instockOperate.setConnection(wConn);
			instockOperate.setProcessName(PROCESS_NAME);
			instockOperate.setStartProcessName(START_PROCESS_NAME);

			// 受付区分が9:キャンセルの場合
			if (receiptClass.equals(ReceiptClass.CANCEL))
			{	WorkingInformation[] cancelResultData = getCancelData(resultData);
				
				// 状態フラグを更新する
			    instockOperate.receivingCancel(cancelResultData,
			            resultData.getWorkerCode(),
			            resultData.getTerminalNo());
			}
			// 受付区分がキャンセル以外
			else
			{
				WorkingInformation[] resultDataArray =
				    (WorkingInformation[]) PackageManager.getArrayObject("WorkingInformation", 1);
				resultDataArray[0] = resultData;

				// 完了フラグに応じて完了処理を行う
				if (! receiptClass.equals(ReceiptClass.INSTALLMENT))
				{
					// 正常または欠品の場合、完了処理を行う
				    instockOperate.receivingComplete(
				            					resultDataArray,
				            					resultData.getTerminalNo(),
				            					resultData.getWorkerCode(),
												resultData.getWorkTime(),
												missScanCnt);
				}
				else
				{
					// 保留の場合、保留の処理を行う
				    instockOperate.receivingSuspend(
				            					resultDataArray,
				            					resultData.getTerminalNo(),
				            					resultData.getWorkerCode(),
												resultData.getWorkTime(),
												missScanCnt);
				}
			}
			wConn.commit();
		}
		// 検索した情報が見つからない場合
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" PlanDate:" + resultData.getPlanDate() +
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
			errDetails = RFTId5131.ErrorDetails.NULL;
			return RFTId5131.ANS_CODE_ERROR;
		}
		// 更新対象データが他端末で更新されていた場合
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" PlanDate:" + resultData.getPlanDate() +
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
			errDetails = RFTId5131.ErrorDetails.UPDATE_FINISH;
			return RFTId5131.ANS_CODE_ERROR ;
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
			errDetails = RFTId5131.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5131.ANS_CODE_ERROR ;
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
			errDetails = RFTId5131.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5131.ANS_CODE_ERROR;
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
			errDetails = RFTId5131.ErrorDetails.PARAMETER_ERROR;
			return RFTId5131.ANS_CODE_ERROR;
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
			return RFTId5131.ANS_CODE_MAINTENANCE;
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
			errDetails = RFTId5131.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5131.ANS_CODE_ERROR;
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
			errDetails = RFTId5131.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return RFTId5131.ANS_CODE_ERROR;
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
			errDetails = RFTId5131.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5131.ANS_CODE_ERROR;
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
			errDetails = RFTId5131.ErrorDetails.SCHEDULE_ERROR;
			return RFTId5131.ANS_CODE_ERROR ;
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
			errDetails = RFTId5131.ErrorDetails.INTERNAL_ERROR;
			return RFTId5131.ANS_CODE_ERROR ;
		}
		
		return RFTId5131.ANS_CODE_NORMAL;
	}

	/**
	 * 商品単位入荷検品の要求時に作業中にしたデータを取得します。<BR>
	 * ※キャンセル時のみ使用します。<BR> 
	 * @param resultData		作業実績データ
	 * @return	作業中にしたデータ(WorkingInfoの配列)
	 * @throws ReadWriteException  データベースとの接続で異常が発生した場合に通知されます。
	 */
	public WorkingInformation[] getCancelData(WorkingInformation resultData) throws ReadWriteException
	{
		// WorkingInfoを検索した結果を保持する変数
		WorkingInformation[] workinfo = null;

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler obj = new WorkingInformationHandler(wConn);
		//-----------------
		// 作業情報の検索
		// 要求と同じ条件で検索
		//-----------------
		skey.setTerminalNo(resultData.getTerminalNo());
		skey.setWorkerCode(resultData.getWorkerCode());
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setItemCodeOrder(1, true);
		skey.setItfOrder(2, true);
		skey.setBundleItfOrder(3, true);
		skey.setCollectJobNoOrder(4, true);
		
		workinfo = (WorkingInformation[]) obj.find(skey);
		// WorkingInfoを検索した結果を保持する変数

		if (workinfo != null && workinfo.length > 0)
		{
			return workinfo;
		}
		return null;

	}
	
	
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * エラー詳細を取得する。
	 * 
	 * @return	エラー詳細
	 */
	public String getErrorDetails()
	{
		return errDetails;
	}

	// Private methods -----------------------------------------------
}
//end of class

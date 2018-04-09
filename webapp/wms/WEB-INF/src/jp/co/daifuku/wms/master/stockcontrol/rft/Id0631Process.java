// $Id: Id0631Process.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.stockcontrol.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId0631;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId5631;

/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * RFTからの予定外入庫実績送信(ID0631)に対する処理を行います。<BR>
 * Id0631Operateクラスの提供する機能を使用し、在庫情報の更新を行い
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 予定外入庫実績送信処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   ID0631Operateクラスの機能で、在庫情報更新の処理を呼び出し、応答フラグを取得します。<BR>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class Id0631Process extends IdProcess
{
	// Class fields----------------------------------------------------
	
	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0631Process";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $";
	}

	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	/**
	 * 予定外入庫実績送信に対する処理を行います。
	 * StockOperateを使用して、予定外入庫の完了処理を行います。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		// 受信電文解析用のインスタンスを生成
		RFTId0631 rftId0631 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5631 rftId5631 = null;
		
		// 在庫情報Entityのインスタンスを生成
		Stock stock = new Stock();
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0631 = (RFTId0631)PackageManager.getObject("RFTId0631");
			rftId0631.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5631 = (RFTId5631)PackageManager.getObject("RFTId5631");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}
		
		// 受信電文から作業者コードを取得
		String workerCode = rftId0631.getWorkerCode();
		
		// 受信電文からRFT号機No.を取得
		String rftNo = rftId0631.getRftNo();
		
		// 受信電文から荷主コードを取得
		stock.setConsignorCode(rftId0631.getConsignorCode());
		
		// 受信電文から荷主名称を取得
		stock.setConsignorName(rftId0631.getConsignorName());
		
		// 受信電文からJANコードを取得
		stock.setItemCode(rftId0631.getJANCode());
		
		// 受信電文からボールITFを取得
		stock.setBundleItf(rftId0631.getBundleITF());
		
		// 受信電文からケースITFを取得
		stock.setItf(rftId0631.getITF());
		
		// 受信電文から商品名称を取得
		stock.setItemName1(rftId0631.getItemName());
		
		// 受信電文から賞味期限を取得
		stock.setUseByDate(rftId0631.getUseByDate());
		
		// 受信電文から入庫完了ロケーションNo.を取得
		stock.setLocationNo(rftId0631.getStorageCompletionLocationNo());
		
		// 受信電文からケース・ピース区分を取得
		stock.setCasePieceFlag(rftId0631.getCasePieceFlag());
		
		// 応答フラグを保持する変数
		String ansCode = "" ;
		
		// エラー詳細を保持する変数
		String errorDetail = RFTId5631.ErrorDetails.NORMAL;
		
		try
		{
			if(DisplayText.isPatternMatching(rftId0631.getConsignorCode()))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE[" + rftId0631.getConsignorCode() +"]");				
			}
			if(DisplayText.isPatternMatching(rftId0631.getStorageCompletionLocationNo()))
			{
				throw new InvalidDefineException("LOCATION_NO[" + rftId0631.getStorageCompletionLocationNo() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0631.getJANCode()))
			{
				throw new InvalidDefineException("JAN_CODE[" + rftId0631.getJANCode() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0631.getUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + rftId0631.getUseByDate() +"]");
			}
			
			// 受信電文からボール入数を取得
			stock.setBundleEnteringQty(Integer.parseInt(rftId0631.getBundleEnteringQty().trim()));
			
			// 受信電文からケース入数を取得
			stock.setEnteringQty(Integer.parseInt(rftId0631.getEnteringQty().trim()));
			
			// 受信電文から入庫完了数を取得
			stock.setStockQty(Integer.parseInt(rftId0631.getStorageCompletionQty()));
			
			// 受信電文から作業時間を取得
			int workSeconds = rftId0631.getWorkSeconds();
			
			// Id0151Operateのインスタンスを生成
			Id0631Operate id0631Operate = (Id0631Operate) PackageManager.getObject("Id0631Operate");
			id0631Operate.setConnection(wConn);
			id0631Operate.initialize();

			ansCode = id0631Operate.doComplete(
							workerCode,
							rftNo,
							stock,
							workSeconds);
			errorDetail = id0631Operate.getErrorDetails();
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6006003, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NumberFormatException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
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
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.INTERNAL_ERROR;
			
		}
		// 応答電文の作成
		// STX
		rftId5631.setSTX() ;
		// SEQ
		rftId5631.setSEQ(0) ;
		// ID
		rftId5631.setID(RFTId5631.ID) ;
		// RFT送信時間
		rftId5631.setRftSendDate(rftId0631.getRftSendDate()) ;
		// SERVER送信時間
		rftId5631.setServSendDate() ;
		// RFT号機
		rftId5631.setRftNo(rftId0631.getRftNo()) ;

		// 担当者コード
		rftId5631.setWorkerCode(rftId0631.getWorkerCode()) ;
		
		// 応答フラグ
		rftId5631.setAnsCode(ansCode) ;
		
		// エラー詳細
		rftId5631.setErrDetails(errorDetail);
		
		// ETX
		rftId5631.setETX() ;

		// 応答電文を獲得する。
		rftId5631.getSendMessage(sdt) ;

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

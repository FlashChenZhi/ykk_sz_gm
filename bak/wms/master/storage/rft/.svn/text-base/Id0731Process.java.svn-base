// $Id: Id0731Process.java,v 1.1.1.1 2006/08/17 09:34:21 mori Exp $
package jp.co.daifuku.wms.master.storage.rft;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.storage.rft.RFTId0731;
import jp.co.daifuku.wms.storage.rft.RFTId5731;

/**
 * Designer : K.Shimizu <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * RFTからの移動出庫実績送信(ID0731)に対する処理を行います。<BR>
 * Id0731Operateクラスの提供する機能を使用し、在庫引当処理及び移動作業情報の作成を行い
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 移動出庫実績処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   ID0731Operateクラスの機能で、在庫引当･移動作業情報作成の処理を呼び出し、応答フラグを取得します。<BR>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:21 $
 * @author  $Author: mori $
 */
public class Id0731Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0731Process";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:21 $");
	}

	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	/**
	 * 移動出庫実績(ID0731)に対する処理を行います。
	 * 受信データから作業情報を検索し、検索結果を送信電文にセットします。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		// 受信電文解析用のインスタンスを生成
		RFTId0731 rftId0731 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5731 rftId5731 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0731 = (RFTId0731)PackageManager.getObject("RFTId0731");
			rftId0731.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5731 = (RFTId5731)PackageManager.getObject("RFTId5731");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//応答フラグ保持用変数
		String ansCode = RFTId5731.ANS_CODE_NORMAL ;
		
		// エラー詳細保持用変数
		String errDetails = RFTId5731.ErrorDetails.NORMAL;
		
		// 移動出庫可能数保持用変数
		int storageQty = 0;

		// 受信電文からRFT号機No.を取得
		String rftNo = rftId0731.getRftNo() ;
		
		// 受信電文から作業者コードを取得
		String workerCode = rftId0731.getWorkerCode() ;

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0731.getConsignorCode() ;
		
		// 受信電文から荷主名称を取得
		String consignorName = rftId0731.getConsignorName() ;
		
		// 受信電文からエリアNo.を取得
		String sourceAreaNo = rftId0731.getSourceAreaNo() ;

		// 受信電文から移動元ロケーションNo.を取得
		String sourceLocationNo = rftId0731.getSourceLocationNo() ;

		// 受信電文からJANコードを取得
		String JANCode = rftId0731.getJANCode() ;
		
		// 受信電文からボールITFを取得
		String bundleITF = rftId0731.getBundleITF() ;
		
		// 受信電文からケースITFを取得
		String itf = rftId0731.getITF() ;
		
		// 受信電文から商品名称を取得
		String itemName = rftId0731.getItemName() ;

		// 受信電文から賞味期限を取得
		String useByDate = rftId0731.getUseByDate();


		try
		{
			if(DisplayText.isPatternMatching(rftId0731.getConsignorCode()))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE");				
			}

			if(DisplayText.isPatternMatching(rftId0731.getSourceLocationNo()))
			{
				throw new InvalidDefineException("LOCATION_NO");
			}
			if(DisplayText.isPatternMatching(JANCode))
			{
				throw new InvalidDefineException("JAN_CODE");
			}
			// 受信電文からボール入数を取得
			int bundleEnteringQty = rftId0731.getBundleEnteringQty();
			
			// 受信電文からケース入数を取得
			int enteringQty = rftId0731.getEnteringQty();
					
			// 受信電文から移動出庫実績数を取得
			int movementResultQty = rftId0731.getMovementResultQty() ;
			// 受信電文から作業時間を取得
			int workSeconds = rftId0731.getWorkSeconds();

			// Id0731Operateのインスタンスを生成
			Id0731Operate id0731Operate = (Id0731Operate)PackageManager.getObject("Id0731Operate");
			id0731Operate.setConnection(wConn);
			id0731Operate.initialize();

			ansCode = id0731Operate.doComplete(
							workerCode,
							rftNo,
							consignorCode,
							consignorName,
							sourceLocationNo,
							JANCode,
							itemName,
							enteringQty,
							bundleEnteringQty,
							itf,
							bundleITF,
							useByDate,
							movementResultQty,
							workSeconds);
			errDetails = id0731Operate.getErrorDetails();
			storageQty = id0731Operate.getStorageQty();
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6006003, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NumberFormatException e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.PARAMETER_ERROR;
		}
		catch (InvalidDefineException e)
		{
			// 6026022=指定された値は、空白か、禁止文字が含まれています。{0}
			RftLogMessage.printStackTrace(6026022, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.PARAMETER_ERROR;
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.INTERNAL_ERROR;

		}

		// 応答電文の作成
		// STX
		rftId5731.setSTX() ;

		// SEQ
		rftId5731.setSEQ(0) ;

		// ID
		rftId5731.setID(RFTId5731.ID) ;

		// RFT送信時間
		rftId5731.setRftSendDate( rftId0731.getRftSendDate() ) ;

		// SERVER送信時間
		rftId5731.setServSendDate() ;

		// RFT号機
		rftId5731.setRftNo( rftId0731.getRftNo() ) ;

		// 担当者コード
		rftId5731.setWorkerCode( rftId0731.getWorkerCode() ) ;
		
		// 出庫可能在庫数
		rftId5731.setStorageQty(storageQty);
		
		// 応答フラグ
		rftId5731.setAnsCode( ansCode ) ;
		
		// エラー詳細
		rftId5731.setErrDetails(errDetails) ;
		
		// ETX
		rftId5731.setETX() ;

		// 応答電文を獲得する。
		rftId5731.getSendMessage( sdt ) ;

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

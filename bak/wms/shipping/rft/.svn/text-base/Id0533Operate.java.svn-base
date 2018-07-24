// $Id: Id0533Operate.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷先単位出荷検品実績送信(ID0533)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 処理の種類には、 保留、確定、キャンセル があります。<BR>
 * Id0533Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0533Operate extends IdOperate
{
	// Class variables -----------------------------------------------
	private static final String PROCESS_NAME = "ID0533";
	private static final String START_PROCESS_NAME = "ID0532";
	private static final String CLASS_NAME = "Id0533Operate";

	// エラー詳細を保持する変数
	private String errDetails = RFTId5533.ErrorDetails.NORMAL;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0533Operate()
	{
		super();
	}
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0533Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	// Public methods ------------------------------------------------
	/**
	 * 出荷先単位出荷検品実績データ(ID0231)の処理を行います。<BR>
	 * 電文の完了区分から処理を判定し、確定処理又はキャンセル処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *      <UL>
	 *      <LI>完了区分 0:正常、2:保留 ［確定処理］</LI>
	 *      <LI>完了区分 9:キャンセル   ［キャンセル処理］</LI>
	 *      </UL>
	 * </DIR>

	 * @param	consignorCode	荷主コード
	 * @param	planDate	    予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	completionFlag	完了区分
	 * @param	resultFileName	実績ファイル名
	 * @param	numRecords	    レコード件数
	 * @param	workTime     	作業時間
	 * @param	missScanCnt     ミスカウント
	 * @return					応答電文の応答フラグ
	*/
	public String doComplete(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode,
		String completionFlag,
		String resultFileName,
		int numRecords,
		int workTime,
		int missScanCnt)
	{
		try
		{
			// 受付区分が9:キャンセルの場合
			if(completionFlag.equals(RFTId0533.COMPLETION_FLAG_CANCEL))
			{
				ShippingWorkOperator shippingWorkOperator;
				// ShippingOperateのインスタンスを生成
				shippingWorkOperator = (ShippingWorkOperator) PackageManager.getObject("ShippingWorkOperator");
				shippingWorkOperator.initialize(wConn, SystemDefine.JOB_TYPE_SHIPINSPECTION, false);
				shippingWorkOperator.setProcessName(PROCESS_NAME);
				shippingWorkOperator.setStartProcessName(START_PROCESS_NAME);

				shippingWorkOperator.cancel(
						consignorCode,
						planDate,
						workerCode,
						customerCode,
						null,
						rftNo);
			}
			// 受付区分がキャンセル以外
			else
			{
				// ファイル操作オブジェクトを生成する
				Id5532DataFile dataFile = new Id5532DataFile(resultFileName);

				WorkingInformation[] resultData = (WorkingInformation[])dataFile.getCompletionData();
							
				// ファイルのレコード数をチェックする
				if (resultData.length != numRecords)
				{
					throw new IOException();
				}
				
				// 賞味期限の禁止文字チェック
				for (int i = 0; i < resultData.length; i ++)
				{
					if(DisplayText.isPatternMatching(resultData[i].getResultUseByDate()))
					{
						throw new InvalidDefineException("USE_BY_DATE[" + resultData[i].getResultUseByDate() +"]");
					}
				}
				
				// 完了フラグに応じて完了処理を行う
				if(! completionFlag.equals(RFTId0533.COMPLETION_FLAG_RESERVE))
				{
					// 正常または欠品の場合、完了処理を行う
					shippingCompleteOnCustomer(consignorCode	, 
												planDate, 
												customerCode	,
												rftNo, 
												workerCode		,
												resultData,
												workTime,
												missScanCnt) ;
				}
				else
				{
					// 保留の場合、保留の処理を行う
					shippingSuspendOnCustomer(consignorCode	, 
												planDate, 
												customerCode	,
												rftNo,
												workerCode		, 
												resultData,
												workTime,
												missScanCnt);
				}
				
			}

		}
		// データベースロックの場合
		catch(LockTimeOutException le)
		{
			try
			{
				wConn.rollback() ;
			}
			catch(SQLException se)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,CLASS_NAME,le);
			}
						
			return RFTId5533.ANS_CODE_MAINTENANCE;
			
		}
		// 検索した情報が見つからない場合
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
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

			errDetails = RFTId5533.ErrorDetails.NULL;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR ;
		}
		// 更新対象データが他端末で更新されていた場合
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + planDate +
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

			errDetails = RFTId5533.ErrorDetails.UPDATE_FINISH;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR ;
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

			errDetails = RFTId5533.ErrorDetails.DB_ACCESS_ERROR;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR ;
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "ShippingWorkOperator", e.getMessage());

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			errDetails = RFTId5533.ErrorDetails.INSTACIATE_ERROR;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR ;
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
			errDetails = RFTId5533.ErrorDetails.PARAMETER_ERROR;
			return RFTId5533.ANS_CODE_ERROR;
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
			errDetails = RFTId5533.ErrorDetails.DB_INVALID_VALUE;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR;
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
			errDetails = RFTId5533.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR;
		}
		catch (IOException e)
		{
		    // 6006020=ファイルの入出力エラーが発生しました。{0}
		    RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5533.ErrorDetails.I_O_ERROR;
			return RFTId5533.ANS_CODE_ERROR;
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

			errDetails = RFTId5533.ErrorDetails.INTERNAL_ERROR;
			// 応答フラグ：エラー
			return RFTId5533.ANS_CODE_ERROR;
		}
		
		return RFTId5533.ANS_CODE_NORMAL;
	}

	/**
	 * 出荷先別出荷検品実績データ [確定処理]<BR>
	 * 出荷実績情報について、以下の確定処理を実行します。<BR>
	 * 
	 *  作業情報の更新。該当するデータを検索し、状態フラグを完了に更新し、実績数などの項目をセットします。<BR>
	 *  出荷予定情報の更新。該当するデータを検索し、状態フラグを完了に更新し、実績数などの項目をセットします。<BR>
	 *  在庫情報の更新。該当するデータを検索し、在庫数と引当数を減算します。<BR>
	 *  実績送信情報の作成。検品完了した実績を作成します。<BR>
	 *  作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 *  在庫移行処理を実行します。<BR>
	 * 
	 * <BR>
	 * 作業情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>商品コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 * </DIR>
	 * <BR>
	 * 出荷予定情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>出荷予定一意キー=作業情報.作業No (作業情報に対応して、1件の出荷予定情報があります。)
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   出荷実績数、出荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * <BR>
	 * 在庫情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫ID=作業情報.作業No (作業情報に対応して、1件の在庫情報があります。)
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>在庫数</TD>			<TD>在庫数 - 作業情報.予定数</TD></TR>
	 *      <TR><TD>引当数</TD>			<TD>引当数 - 作業情報.予定数</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * <BR>
	 * 実績送信情報の作成<BR>
	 * <DIR>
	 *   作業日をWareNavi_Systemから取得し、作業日にセットします。<BR>
	 *   登録処理名セットします。<BR>
	 *   その他の項目は、作業情報から取得し、セットします。<BR>
	 * </DIR>
	 * 
	 * 作業者実績情報の更新<BR>
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>作業日=WareNavi_Systemの作業日</LI>
	 *     <LI>作業者コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *     <LI>作業区分=出荷:05</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>作業終了日時</TD>	<TD>現在のシステム日付</TD></TR>
	 *      <TR><TD>作業数量</TD>		<TD>+ 今回作業した実績数の総数</TD></TR>
	 *      <TR><TD>作業回数</TD>		<TD>+ 検品実績ファイルの有効行（実績数が0以上の行)の行数</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @param	workTime     	作業時間
	 * @param	missScanCnt     ミスカウント
	 * @throws InvalidStatusException 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException      該当データが見つからない場合に通知されます。
	 * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
	 * @throws ReadWriteException     データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException    情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException  DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 * @throws LockTimeOutException   他の処理がロック中でロックの獲得に失敗した場合に通知されます。
	 * @throws IllegalAccessException 指定されたクラスの定義にアクセスできない場合に通知されます。
	 */
	public void shippingCompleteOnCustomer(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode,
		WorkingInformation[] workingInformation,
		int workTime,
		int missScanCnt)
		throws InvalidStatusException, NotFoundException, InvalidDefineException, ReadWriteException, 
		DataExistsException, UpdateByOtherTerminalException,LockTimeOutException, IllegalAccessException   
	{
		ShippingWorkOperator shippingWorkOperator;
		try
		{
			// ShippingOperateのインスタンスを生成
			shippingWorkOperator = (ShippingWorkOperator) PackageManager.getObject("ShippingWorkOperator");
			shippingWorkOperator.initialize(wConn, SystemDefine.JOB_TYPE_SHIPINSPECTION, false);
			shippingWorkOperator.setProcessName(PROCESS_NAME);
			shippingWorkOperator.setStartProcessName(START_PROCESS_NAME);
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "ShippingWorkOperator", e.getMessage());
			throw e;
		}

		String[] planUkeyList =
			shippingWorkOperator.lockUpdateData(consignorCode, planDate, customerCode, null, rftNo, workerCode);

		if (updateWorkingInformation(
			consignorCode,
			planDate,
			customerCode,
			rftNo,
			workerCode,
			workingInformation,
			true))
		{
			shippingWorkOperator.updateCompletionStatus(planUkeyList);
			
			shippingWorkOperator.updateWorkerResult(workerCode, rftNo, workTime, missScanCnt, workingInformation, false);
		}
	}
	
	/**
	 * 出荷先単位出荷検品実績データ [保留処理]<BR>
	 * 出荷実績情報について、以下の確定処理を実行します。<BR>
	 * 
	 *  作業情報の更新と新規作業情報の作成。該当するデータを検索し、完了した分だけ完了にし、完了していない分を未開始にします。<BR>
	 *  (1レコード内の一部が検品作業済みでの時は、未開始のレコードを新規作成します。)<BR>
	 *  作業情報の更新。該当するデータを検索し、状態フラグを完了又は一部完了に更新し、実績数などの項目をセットします。<BR>
	 *  在庫情報の更新。該当するデータを検索し、在庫数と引当数を減算します。<BR>
	 *  実績送信情報の作成。検品完了した実績を作成します。<BR>
	 *  作業者実績情報の更新。該当するデータを検索し、作業数、作業回数を加算します。<BR>
	 *  完了した分について、在庫移行処理を実行します。<BR>
	 * 
	 * <BR>
	 * 作業情報の更新<BR>
	 * <DIR>
	 *   (検索条件)<BR>
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>商品コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 *   不足の場合は、そのレコードの実績が0になる場合と、実績が1以上（作業可能数よりは小さい値）になる場合で処理が分かれます。<BR>
	 * 
	 *     実績が0になる場合<BR>
	 *         ...状態フラグを未開始の状態に更新します。また、このレコードに対する実績処理は行いません。<BR>
	 *     実績が1以上になる場合<BR>
	 *         ...レコードを分割します。<BR>
	 *            元レコードは、作業実績数に実績数をセットします。また、保留数に可能数-実績数をセットします。<BR>
	 *            状態フラグを完了にセットします。賞味期限、処理名をセットします。<BR>
	 *            状態フラグを完了にした分の実績送信情報の処理を行います。<BR>
	 *            新レコードは、作業Noを新規に取得してセットします。<BR>
	 *            作業可能数と予定数は、分割前の作業可能数から実績数を減算した値（残りの作業）をセットします。作業実績数は0をセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 出荷予定情報の更新<BR>
	 * <DIR>
	 *    (検索条件)<BR>
	 *     ･出荷予定一意キー=作業情報から取得 (作業情報に対応して、1件の出荷予定情報があります。)<BR>
	 *   出荷実績数、出荷欠品数に、対応した作業情報の実績数を加算します。（補足:保留の場合、欠品分は分割されるので、欠品数は必ず0です。）<BR>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。<BR>
	 *   処理名をセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 在庫情報の更新<BR>
	 * <DIR>
	 *    (検索条件)<BR>
	 *     ･在庫ID=作業情報から取得 (作業情報に対応して、1件の在庫情報があります。)<BR>
	 *   在庫情報の在庫数、引当数から、対応した作業情報の予定数を減算します。<BR>
	 *   処理名をセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 実績送信情報の作成<BR>
	 * <DIR>
	 *   作業日をシステム定義から取得し、作業日にセットします。<BR>
	 *   登録処理名セットします。<BR>
	 *   その他の項目は、作業情報から取得し、セットします。<BR>
	 * </DIR>
	 * <BR>
	 * 作業者実績情報の更新<BR>
	 * <DIR>
	 *    (検索条件)<BR>
	 *     ･作業日=システム定義の作業日 ･作業者コード ･端末No ･作業区分=05:出荷<BR>
	 *   作業終了日時に、現在のシステム日付をセットします。<BR>
	 *   作業数量に、今回作業した実績数の総数を加算します。<BR>
	 *   作業回数に、検品実績ファイルの有効行（実績数が0以上の行)の行数を加算します。<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @param	workTime     	作業時間
	 * @param	missScanCnt     ミスカウント
	 * @throws InvalidStatusException 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException      該当データが見つからない場合に通知されます。
	 * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
	 * @throws ReadWriteException     データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException    情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException  DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 * @throws LockTimeOutException   他の処理がロック中でロックの獲得に失敗した場合に通知されます。
	 * @throws IllegalAccessException 指定されたクラスの定義にアクセスできない場合に通知されます。
	 */
	public void shippingSuspendOnCustomer(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode,
		WorkingInformation[] workingInformation,
		int workTime,
		int missScanCnt)
		throws InvalidStatusException, NotFoundException, InvalidDefineException, ReadWriteException, 
		DataExistsException, UpdateByOtherTerminalException,LockTimeOutException, IllegalAccessException
	{
		ShippingWorkOperator shippingWorkOperator;
		try
		{
			// ShippingOperateのインスタンスを生成
			shippingWorkOperator = (ShippingWorkOperator) PackageManager.getObject("ShippingWorkOperator");
			shippingWorkOperator.initialize(wConn, SystemDefine.JOB_TYPE_SHIPINSPECTION, false);
			shippingWorkOperator.setProcessName(PROCESS_NAME);
			shippingWorkOperator.setStartProcessName(START_PROCESS_NAME);
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "ShippingWorkOperator", e.getMessage());
			throw e;
		}
		
		String[] planUkeyList =
			shippingWorkOperator.lockUpdateData(consignorCode, planDate, customerCode, null, rftNo, workerCode);
		
		if (updateWorkingInformation(
			consignorCode,
			planDate,
			customerCode,
			rftNo,
			workerCode,
			workingInformation,
			false))
		{
			shippingWorkOperator.updateCompletionStatus(planUkeyList);

			shippingWorkOperator.updateWorkerResult(workerCode, rftNo, workTime, missScanCnt, workingInformation, true);
		}
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 作業情報を更新する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 *     <LI>予定日</LI>
	 *     <LI>荷主コード</LI>
	 *     <LI>出荷先コード</LI>
	 *     <LI>商品コード</LI>
	 * 	   <LI>端末No=RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>"ID0061"</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 * </DIR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	workingInformation	出荷実績情報  作業情報エンティティの配列
	 * @param	isShortage		数量不足のときに欠品にするかどうかのフラグ
	 * @return DB更新時はtrue、既に完了している場合はfalseを返します
	 * @throws InvalidStatusException 状態を持つオブジェクトに対して、範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException      該当データが見つからない場合に通知されます。
	 * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
	 * @throws ReadWriteException     データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException    情報を登録しようとした際に、既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException  DB更新時に、先に他端末で更新されていて更新できない場合に通知されます。
	 * @throws IllegalAccessException 指定されたクラスの定義にアクセスできない場合に通知されます。
	 */
	protected boolean updateWorkingInformation(
		String consignorCode,
		String planDate,
		String customerCode,
		String rftNo,
		String workerCode,
		WorkingInformation[] workingInformation,
		boolean isShortage)
		throws InvalidStatusException, NotFoundException, InvalidDefineException, ReadWriteException, 
		DataExistsException, UpdateByOtherTerminalException, IllegalAccessException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler =
			new WorkingInformationHandler(wConn);
			
		ShippingOperate shippingOperate;
		try
		{
			// ShippingOperateのインスタンスを生成
			shippingOperate = (ShippingOperate) PackageManager.getObject("ShippingOperate");
			shippingOperate.setConnection(wConn);
			shippingOperate.setProcessName(PROCESS_NAME);
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "ShippingOperate", e.getMessage());
			throw e;
		}

		ShippingWorkOperator shippingWorkOperator;
		try
		{
			// ShippingOperateのインスタンスを生成
			shippingWorkOperator = (ShippingWorkOperator) PackageManager.getObject("ShippingWorkOperator");
			shippingWorkOperator.initialize(wConn, SystemDefine.JOB_TYPE_SHIPINSPECTION, false);
			shippingWorkOperator.setProcessName(PROCESS_NAME);
			shippingWorkOperator.setStartProcessName(START_PROCESS_NAME);
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "ShippingWorkOperator", e.getMessage());
			throw e;
		}

		int alreadycomp = 0;
		boolean alreadycomp_flag = false;

		for (int i = 0; i < workingInformation.length; i++)
		{
			// DBを検索する
			skey.KeyClear();
			skey.setConsignorCode(consignorCode);
			skey.setPlanDate(planDate);
			skey.setCustomerCode(customerCode);
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			// TC->DCの順にデータ取得し、欠品時にTCを優先して完了されるようにする。
			skey.setTcdcFlagOrder(1, false);
			skey.setCollectJobNoOrder(2, true);
			skey.setJobNoOrder(3, true);
			// 集約作業No
			skey.setCollectJobNo(workingInformation[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);

			if (handler.count(skey) <= 0)
			{
				if (shippingOperate.alreadyCompleteCheck(
						consignorCode,
						planDate,
						customerCode,
						"",
						rftNo,
						workerCode,
						PROCESS_NAME,
						workingInformation[i]
						))
				{
					// 既に完了していた作業情報数をカウント
					alreadycomp++;
					alreadycomp_flag = true;
				}
			}
			
			// 集約データが他端末で更新されたかどうかチェック
			// 更新されていた場合は、UpdateByOtherTerminalExceptionがthrowされます。
			shippingOperate.checkCollectData(workingInformation[i], wi, START_PROCESS_NAME);

			// 出荷実績情報の実績数を保持
			int workQty = workingInformation[i].getResultQty();

			for (int j = 0; j < wi.length; j++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setConsignorCode(consignorCode);
				akey.setPlanDate(planDate);
				akey.setCustomerCode(customerCode);
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);
				
				if( workQty > 0 || isShortage )
				{
					// 分配する数がある時又は欠品完了の場合
					// 保留分割した場合の新規データ用作業情報エンティティ
					jp.co.daifuku.wms.base.entity.WorkingInformation newWorkinfo = null;

					int resultQty = workQty;
					// 更新する値をセットする
					// 次データある場合で作業可能数より出荷実績情報の実績数が大きい場合
					if (j < wi.length - 1 && resultQty > wi[j].getPlanEnableQty())
					{
						// 実績数に作業可能数をセット
						resultQty = wi[j].getPlanEnableQty();
					}
					workQty -= resultQty;

					akey.updateResultQty(resultQty);
					// 現在の作業情報エンティティの実績数をセット
					wi[j].setResultQty(resultQty);
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					// 現在の作業情報エンティティの状態フラグをセット
					wi[j].setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
					akey.updateLastUpdatePname(PROCESS_NAME);

					if (wi[j].getResultQty() < wi[j].getPlanEnableQty())
					{
						// 実績数より作業可能数が大きい場合(欠品or保留の場合)
						// 残数を取得
						int restQty = wi[j].getPlanEnableQty() - wi[j].getResultQty();
						if (isShortage)
						{
							// 欠品の場合
							akey.updateShortageCnt(restQty);
							// 現在の作業情報エンティティの欠品数をセット
							wi[j].setShortageCnt(restQty);
						}
						else
						{
							// 保留の場合
							akey.updatePendingQty(restQty);
							// 現在の作業情報エンティティの保留数をセット
							wi[j].setPendingQty(restQty);

							// 分割する新しい方のレコードの値をセットする
							newWorkinfo = wi[j].getBaseInstance();
							newWorkinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
							newWorkinfo.setPlanQty(restQty);
							newWorkinfo.setPlanEnableQty(restQty);
							newWorkinfo.setResultQty(0);
							newWorkinfo.setPendingQty(0);
							newWorkinfo.setTerminalNo("");
							newWorkinfo.setWorkerCode("");
							newWorkinfo.setWorkerName("");
							newWorkinfo.setRegistPname(PROCESS_NAME);
							newWorkinfo.setLastUpdatePname(PROCESS_NAME);
							// 新しい作業Noを割り当てる
							SequenceHandler sh = new SequenceHandler(wConn);
							newWorkinfo.setJobNo(sh.nextJobNo());
							// 集約作業Noに作業Noをセットする
							newWorkinfo.setCollectJobNo(newWorkinfo.getJobNo());
						}
					}
	
					akey.updateResultUseByDate(workingInformation[i].getResultUseByDate());
					// 現在の作業情報エンティティの賞味期限をセット
					wi[j].setResultUseByDate(workingInformation[i].getResultUseByDate());

					// 集約作業Noを元に戻す(作業Noに戻す)
					akey.updateCollectJobNo( wi[j].getJobNo() );
					
					// DBを更新する
					handler.modify(akey);
					if (newWorkinfo != null)
					{
						// 分割レコード作成
						handler.create(newWorkinfo);
					}
	
					// 出荷予定情報の数量を更新する
					shippingWorkOperator.updatePlanInformation(wi[j]);
					// 在庫情報の数量を更新する
					shippingWorkOperator.updateStockQty(wi[j]);
					// 実績送信情報を作成する
					shippingWorkOperator.createResultData(wi[j]);
				}
				else
				{
					// 保留処理で分配する数がない場合
					
					// キャンセル処理を行う
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
					akey.updateWorkerCode("");
					akey.updateWorkerName("");
					akey.updateTerminalNo("");
					akey.updateLastUpdatePname(PROCESS_NAME);
					// 集約作業Noを元に戻す(作業Noに戻す)
					akey.updateCollectJobNo( wi[j].getJobNo() );
					// DBを更新する
					handler.modify(akey);
				}
			}			
		}
		// 全作業情報が同一端末No.、同一作業者コード、同一プロセス名称で完了済みかのチェック
		if (alreadycomp_flag)
		{
			if (workingInformation.length == alreadycomp)
			{
				// 既にDBは更新されている
				return false;
			}

			throw new UpdateByOtherTerminalException();
		}
		
		return true;
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
	// Private methods -----------------------------------------------
}
//end of class

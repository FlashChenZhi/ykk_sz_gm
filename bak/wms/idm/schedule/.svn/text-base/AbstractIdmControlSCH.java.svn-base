package jp.co.daifuku.wms.idm.schedule;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 移動ラックパッケージのスケジュール処理を行なう抽象クラスです。<BR>
 * WmsSchedulerインターフェースを実装し、このインターフェースの実装に必要な処理を実装します。<BR>
 * 共通メソッドはこのクラスに実装され、スケジュール処理の個別の振る舞いについては、
 * このクラスを継承したクラスによって実装されます。
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/07</TD><TD>C.Kaminishizono</TD><TD>新規作成</TD></TR>
 * <TR><TD>2005/08/04</TD><TD>Y.Okamura</TD><TD>AbstractSCHを継承するように変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public abstract class AbstractIdmControlSCH extends AbstractSCH
{
	//	Class variables -----------------------------------------------
	/**
	 * クラス名
	 */
	public static final String CLASS_NAME = "AbstractIdmControlSCH";
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}
	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	
	// Protected methods ---------------------------------------------
	/**
	 * 作業者コード、パスワードの内容が正しいかチェックを行います。<BR>
	 * 内容が正しい場合はtrueを、正しくない場合はfalseを返します。<BR>
	 * 返り値がfalseの場合
	 * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
	 * 
	 * @param  conn               データベースとのコネクションオブジェクト
	 * @param  checkParam        入力チェックを行う内容が含まれたパラメータクラス
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return 作業者コード、パスワードの内容が正しい場合はtrueを、正しくない場合はfalse
	 */
	protected boolean checkWorker(Connection conn, IdmControlParameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		// パラメータから作業者コード、パスワードを取得する。
		String workerCode = checkParam.getWorkerCode();
		String password = checkParam.getPassword();
		
		return correctWorker(conn, workerCode, password);

	}

	/**
	 * ためうちエリアに表示できない場合、理由ごとに値を返します。<BR>
	 * 対象データがない：IdmControlParameter[0]<BR>
	 * 最大表示件数を超えている：null<BR>
	 * <BR>
	 * <U>このメソッドを使用する場合、必ず先にAbstractSCHクラスのcanLowerDisplayメソッドを使用してください</U>
	 * 
	 * @return 理由ごとによる返り値
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected IdmControlParameter[] returnNoDisplayParameter() throws ScheduleException, ReadWriteException
	{
		if (getDisplayNumber() <= 0)
		{
			return new IdmControlParameter[0];
		}
		
		if (getDisplayNumber() > WmsParam.MAX_NUMBER_OF_DISP)
		{
			return null;
		}
		
		doScheduleExceptionHandling(CLASS_NAME);
		return null;

	}
	
	/**
	 * 入力値のチェックを行います。<BR>
	 * チェック結果を返します。 <BR>
	 * <CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * このメソッドでは以下の処理を行います。 <BR>
	 * <BR>
	 * 1.ケース/ピース区分は、指定範囲内の値が入力されていること。 <BR>
	 * <BR>
	 * 2.ケース/ピース区分が指定なしの場合 <BR>
	 *   <DIR>
	 *   ケース数が入力されている場合、ケース入数が入力されていること。 <BR>
	 *   入庫ケース数または入庫ピース数には1以上の値が入力されていること。 <BR>
	 *   </DIR>
	 * <BR>
	 * 3.ケース/ピース区分がケースの場合 <BR>
	 *   <DIR>
	 *   ケース入数が入力されていること。 <BR>
	 *   入庫ケース数には1以上の値が入力されていること。 <BR>
	 *   </DIR>
	 * <BR>
	 * 4.ケース/ピース区分がピースの場合 <BR>
	 *   <DIR>
	 *   入庫ケース数の入力はできません。 <BR>
	 *   入庫ピース数には1以上の値が入力されていること。 <BR>
	 *   </DIR>
	 * <BR>
	 * @param  casepieceflag      ケース/ピース区分
	 * @param  enteringqty        ケース入数
	 * @param  caseqty            入庫ケース数
	 * @param  pieceqty           入庫ピース数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	protected boolean storageInputCheck(
		String casepieceflag,
		int enteringqty,
		int caseqty,
		int pieceqty)
		throws ReadWriteException
	{
		//	ケース/ピース区分チェック
		if (!(casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING)
			|| casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_CASE)
			|| casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_PIECE)))
		{
			// 6023491 = ケース／ピース区分は、ケース、ピース、又は指定なし を選択してください。
			wMessage = "6023491";
			return false;
		}

		// ケース/ピース区分が指定なしの場合
		if (casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			// ケース入数が1以上
			if (enteringqty > 0)
			{
				// 入庫ケース数と入庫ピース数が0の場合
				if (caseqty == 0 && pieceqty == 0)
				{
					// 6023198 = 入庫ケース数または入庫ピース数には1以上の値を入力してください。
					wMessage = "6023198";
					return false;
				}
			}
			else
			{
				// 入庫ケース数が1以上
				if (caseqty > 0)
				{
					// 6023019 = ケース入数には1以上の値を入力してください。
					wMessage = "6023019";
					return false;
				}
				// 入庫ケース数と入庫ピース数が0の場合
				else if (caseqty == 0 && pieceqty == 0)
				{
					// 6023198 = 入庫ケース数または入庫ピース数には1以上の値を入力してください。
					wMessage = "6023198";
					return false;
				}
			}
		}
		// ケース/ピース区分がケースの場合
		else if (casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース入数が1以上
			if (enteringqty > 0)
			{
				// 入庫ケース数が0の場合
				if (caseqty == 0)
				{
					// 6023129 = 入庫ケース数には1以上の値を入力してください。
					wMessage = "6023129";
					return false;
				}
			}
			else
			{
				// 6023019 = ケース入数には1以上の値を入力してください。
				wMessage = "6023019";
				return false;
			}
		}
		// ケース/ピース区分がピースの場合
		else if (casepieceflag.equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
		{
			// 入庫ケース数が1以上
			if (caseqty > 0)
			{
				// 6023285 = ケース／ピース区分がピースの場合、入庫ケース数の入力はできません。
				wMessage = "6023285";
				return false;
			}

			// 入庫ピース数が0の場合
			if (pieceqty == 0)
			{
				// 6023130 = 入庫ピース数には1以上の値を入力してください。
				wMessage = "6023130";
				return false;
			}

		}

		return true;

	}

	/**
	 * 予定外出庫時の入力チェックを行います。<BR>
	 * チェック結果を返します。 <BR>
	 * <CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
	 * このメソッドでは以下の処理を行います。 <BR>
	 * <BR>
	 * 1.ケース/ピース区分がケースまたは指定なしの場合 <BR>
	 *   <DIR>
	 *   ケース入数が0の場合、ケース数が入力されていないこと。 <BR>
	 *   ケース入数が0の場合、ピース数が1以上入力されていること。 <BR>
	 *   ケース入数が1以上の場合、ケース数またはピース数が入力されていること。 <BR>
	 *   </DIR>
	 * <BR>
	 * 2.ケース/ピース区分がピースの場合 <BR>
	 *   <DIR>
	 *   出庫ケース数に1以上の値が入力されていないこと。 <BR>
	 *   出庫ピース数に1以上の値が入力されていること。 <BR>
	 *   </DIR>
	 * <BR>
	 * @param  pCasePieceFlag      ケース/ピース区分
	 * @param  pEnteringQty        ケース入数
	 * @param  pCaseQty            出庫ケース数
	 * @param  pPieceQty           出庫ピース数
	 * @param  pRowNo              行No.
	 * @return 入力に不備がない場合はtrue、不備がある場合はfalseを返します。
	 */
	protected boolean stockRetrievalInputCheck(
		String pCasePieceFlag,
		int pEnteringQty,
		int pCaseQty,
		int pPieceQty,
		int pRowNo)
	{

		// ケース/ピース区分がケースまたは指定なしの場合
		if (pCasePieceFlag.equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING)
		|| pCasePieceFlag.equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
		{
			if (pEnteringQty == 0)
			{
				if (pCaseQty > 0)
				{
					// 6023299=No.{0} ケース入数が0の場合は、出庫ケース数は入力できません。
					wMessage = "6023299" + wDelim + pRowNo;
					return false;
				}
				else if (pPieceQty == 0)
				{
					// 6023273=No.{0} {1}
					// 6023336 = 出庫ピース数には1以上の値を入力してください。
					wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023336");
					return false;
				}
			}
			else
			{
				// 出庫ケース数と出庫ピース数が0の場合
				if (pCaseQty == 0 && pPieceQty == 0)
				{
					// 6023334 = 出庫ケース数または出庫ピース数には1以上の値を入力してください。
					wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023334");
					return false;
				}
			}
		}
		// ケース/ピース区分がピースの場合
		else if (pCasePieceFlag.equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
		{
			// 出庫ケース数が1以上
			if (pCaseQty > 0)
			{
				// 6023286 = ケース／ピース区分がピースの場合、出庫ケース数は入力できません。
				wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023286");
				return false;
			}
			
			// 出庫ピース数が0の場合
			if (pPieceQty == 0)
			{
				// 6023336 = 出庫ピース数には1以上の値を入力してください。
				wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023336");
				return false;
			}

		}

		return true;

	}

	/**
	 * 実績送信情報テーブル(DNHOSTSEND)の登録を行います。 <BR> 
	 * <BR>     
	 * 受け取ったパラメータの内容をもとに実績送信情報を登録します。 <BR>
	 * <BR>
	 * @param conn        データベースとのコネクションを保持するインスタンス。
	 * @param param       画面から入力された内容を持つIdmControlParameterクラスのインスタンス。
	 * @param stockid     在庫ID
	 * @param workercode  作業者コード
	 * @param workername  作業者名
	 * @param sysdate     作業日(システム定義日付)
	 * @param terminalno  端末No.
	 * @param jobtype     作業区分
	 * @param processname 処理名
	 * @param batchno     バッチNo.
	 * @param inputqty	   作業実績数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException  スケジュール処理内で予期しない例外が発生した場合に通知されます。
	 * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
	 */
	protected boolean createHostsend(
		Connection conn,
		IdmControlParameter param,
		String stockid,
		String workercode,
		String workername,
		String sysdate,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		int inputqty)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();

			// 登録する各一意キーを取得する。
			SequenceHandler sequence = new SequenceHandler(conn);
			
			String jobno = sequence.nextJobNo();
			hostsend.setWorkDate(sysdate);
			hostsend.setJobNo(jobno);
			hostsend.setJobType(jobtype);
			hostsend.setCollectJobNo(jobno);
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			hostsend.setPlanUkey("");
			hostsend.setStockId(stockid);
			hostsend.setAreaNo(param.getAreaNo());
			hostsend.setLocationNo(param.getLocationNo());
			hostsend.setPlanDate("");
			hostsend.setConsignorCode(param.getConsignorCode());
			hostsend.setConsignorName(param.getConsignorName());
			hostsend.setSupplierCode("");
			hostsend.setSupplierName1("");
			hostsend.setInstockTicketNo("");
			hostsend.setInstockLineNo(0);
			hostsend.setCustomerCode(param.getCustomerCode());
			hostsend.setCustomerName1(param.getCustomerName());
			hostsend.setShippingTicketNo("");
			hostsend.setShippingLineNo(0);
			hostsend.setItemCode(param.getItemCode());
			hostsend.setItemName1(param.getItemName());
			hostsend.setHostPlanQty(0);
			hostsend.setPlanQty(0);
			hostsend.setPlanEnableQty(0);
			hostsend.setResultQty(inputqty);
			hostsend.setShortageCnt(0);
			hostsend.setPendingQty(0);
			hostsend.setEnteringQty(param.getEnteringQty());
			hostsend.setBundleEnteringQty(param.getBundleEnteringQty());
			hostsend.setCasePieceFlag(param.getCasePieceFlag());
			hostsend.setWorkFormFlag(param.getCasePieceFlag());
			hostsend.setItf(param.getITF());
			hostsend.setBundleItf(param.getBundleITF());
			hostsend.setTcdcFlag(HostSend.TCDC_FLAG_DC);
			hostsend.setUseByDate(param.getUseByDate());
			hostsend.setLotNo("");
			hostsend.setPlanInformation("");
			hostsend.setOrderNo("");
			hostsend.setOrderingDate("");
			hostsend.setResultUseByDate(param.getUseByDate());
			hostsend.setResultLotNo("");
			hostsend.setResultLocationNo(param.getLocationNo());
			hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			hostsend.setBatchNo(batchno);
			AreaOperator areaOpe = new AreaOperator(conn);
			// システム識別キー
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(param.getAreaNo())));
			hostsend.setWorkerCode(workercode);
			hostsend.setWorkerName(workername);
			hostsend.setTerminalNo(terminalno);
			hostsend.setPlanRegistDate(null);
			hostsend.setRegistDate(new Date());
			hostsend.setRegistPname(processname);
			hostsend.setLastUpdateDate(new Date());
			hostsend.setLastUpdatePname(processname);

			// データの登録
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NumberFormatException e) 
		{
			throw new ScheduleException(e.getMessage());
		} 
	}

}
//end of class

// $Id: Id0430Operate.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.WorkForm;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * 仕分開始要求に対するデータ処理を行ないます。<BR>
 * 仕分データを取得し、そのデータを作業中に更新します。<BR>
 * Id0430Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * ！注意！<BR>
 * このクラス内で使用するWorkingInformationクラスとWorkingInformationHandlerクラスは、
 * ＲＦＴ用パッケージ(jp.co.daifuku.wms.base.rftパッケージ)を使用します。<BR>
 * (親クラスのインスタンスを子クラスにキャストできない為、ＲＦＴ用WorkingInformationHandlerを使用する必要があります。)<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class Id0430Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	private static final String PROCESS_NAME = "ID0430";
	private static final String CLASS_NAME = "Id0430Operate";
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public Id0430Operate()
	{
		super();
	}

	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public Id0430Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:33 $");
	}

	/**
	 * 仕分開始要求に対するデータを取得し、作業可能であれば、そのデータの状態を作業中にします。<BR>
	 * コミットは本メソッド呼び出し元で行います。<BR>
	 * 最初に次の条件で作業データを検索します。<BR>
	 * <DIR>
	 *   荷主コード、仕分予定日付、JANコード、ケース･ピース区分 = パラメータより取得<BR>
	 *   作業区分=04(仕分)<BR>
	 *   状態フラグ=0:未開始 or (2:作業中...但し作業者コードとRFTNoが同じであること)<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * データが取得できない場合、RFTに戻す状態フラグを決定する為に、次の条件で作業データを検索します。<BR>
	 *  データの取得が出来ない場合は、8:該当データ無しをメッセージとしてNotFoundExceptionを返します。<BR>
	 *  取得データに１件でも作業中が含まれている時は、1:他端末作業中をメッセージとしてNotFoundExceptionを返します。<BR>
	 *  全件完了している時は、2:作業完了済みをメッセージとしてNotFoundExceptionを返します。<BR>
	 * <DIR>
	 *   荷主コード、仕分予定日付、JANコード、ケース･ピース区分 = パラメータより取得<BR>
	 *   作業区分=04(仕分)<BR>
	 *   状態フラグ=9(削除)以外<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * 集約の処理を呼び出します。<BR>
	 * <DIR>
	 * 集約の有無をパラメタファイルから取得し、集約を行なう場合は、集約作業Noを振り直します。
	 * (集約する中の１件目の集約作業Noを使用します。)<BR>
	 * 振り直した結果は、DB更新します。<BR>
	 * 作業可能なデータが１件以上の場合は、DBから取得したデータを、配列にセットします。<BR>
	 * この時、集約作業Noで集約して配列にセットします。<BR>
	 * またこのデータは、次の更新を行います。<BR>
	 *  作業情報の状態フラグを作業中に更新します。<BR>
	 *  作業情報の端末NoにRFT号機No、作業者コードにRFT作業者コード、作業者名に作業者名をセットします。<BR>
	 *  仕分予定情報の状態フラグを作業中に更新します。(作業情報の予定一意キーで、仕分予定情報を検索します)<BR>
	 * </DIR>
	 * 更新後、配列にセットした作業情報の一覧を戻り値として、返します。<BR>
	 * <BR>
	 * @param	consignorCode		荷主コード<BR>
	 * @param	planDate			予定日<BR>
	 * @param	workForm			作業形態(ケース・ピース区分)<BR>
	 * @param	scanCode			スキャン商品コード1<BR>
	 * @param	convertedJanCode	スキャン商品コード2<BR>
	 * @param	rftNo				RFT号機番号<BR>
	 * @param	workerCode			作業者コード<BR>
	 * @return 仕分予定情報		作業情報エンティティの配列<BR>
	 * @throws NotFoundException 作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 * @throws IllegalAccessException
	*/
	public WorkingInformation[] startSorting(
		String consignorCode,
		String planDate,
		String workForm,
		String scanCode,
		String convertedJanCode,
		String rftNo,
		String workerCode)
		throws NotFoundException, InvalidDefineException, ReadWriteException, LockTimeOutException, OverflowException
	{
	
		// WorkingInfoを検索した結果を保持する変数
		WorkingInformation[] workinfo = null;

		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

		//-----------------
		// 作業情報の検索
		//-----------------
		SortingOperate sortingOperate = new SortingOperate();
		sortingOperate.setConnection(wConn);
		sortingOperate.setProcessName(PROCESS_NAME);
		
		// 作業可能な出荷予定データを検索します。
		workinfo = getWorkableData(
				consignorCode,
				planDate,
				workForm,
				rftNo,
				workerCode,
				scanCode,
				convertedJanCode);
		
		if ( workinfo == null)
		{
			// 4回の検索でデータが該当しない時の処理
			pskey.KeyClear();
			//-----------------
			// 作業情報の検索(状態フラグ確認のため)
			//-----------------
			pskey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
			pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			// 状態フラグが「削除」以外を対象にする。
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");
			pskey.setConsignorCode(consignorCode);
			pskey.setPlanDate(planDate);
			pskey.setItemCode(scanCode, "=", "(", "", "OR");
			pskey.setItemCode(convertedJanCode, "=", "", "", "OR");
			pskey.setItf(scanCode, "=", "", "", "OR");
			pskey.setBundleItf(scanCode, "=", "", ")", "AND");
			if (workForm.equals(WorkForm.CASE))
			{
				pskey.setWorkFormFlag(workForm);
			}
			else
			{
				pskey.setWorkFormFlag(WorkForm.PIECE, "=", "(", "", "OR");
				pskey.setWorkFormFlag(WorkForm.NOTSPECIFIED, "=", "", ")", "AND");
			}

			workinfo = (WorkingInformation[]) pObj.find(pskey);
			// 該当データが見つからない時は、状態フラグを 8:該当データなし でリターン
			if (workinfo.length == 0)
			{
				NotFoundException e = new NotFoundException(RFTId5430.ANS_CODE_NULL);
				throw e;
			}
			// 該当データに、作業中or開始済データが見つかった時は、状態フラグを 1:他端末作業中 でリターン
			for (int i = 0; i < workinfo.length; i++)
			{
				if (workinfo[i].getStatusFlag()
					.equals(WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| workinfo[i].getStatusFlag().equals(
						WorkingInformation.STATUS_FLAG_START))
				{
					NotFoundException e = new NotFoundException(RFTId5430.ANS_CODE_WORKING);
					throw e;
				}
			}
			// 該当データに、作業中or開始済データが見つからなかった時は、状態フラグを 2:作業完了済み でリターン
			NotFoundException e = new NotFoundException(RFTId5430.ANS_CODE_COMPLETION);
			throw e;
		}

		// 作業集約処理
		WorkingInformation[] collectedData = null;
		collectedData = sortingOperate.collectSortingData(workinfo, workerCode, rftNo);

		return collectedData;
	}		

	/**
	 * 仕分開始要求に対する作業可能データを検索します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *   荷主コード、予定日、出荷先コード = パラメータより取得<BR>
	 *   作業区分=04(仕分)<BR>
	 *   状態フラグ=0:未開始 or (2:作業中...但し作業者コードとRFTNoが同じであること)<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * 
	 * この条件に加え、スキャンコードをJANコード、ケースITF、ボールITFの順に当てはめて検索します。<BR>
	 * 順に検索し、作業可能なデータが見つかった段階で検索を中断し、
	 * 取得したデータを返します。<BR>
	 * この3回の検索のいずれでも作業可能データが見つからなかった場合かつ、ITFtoJAN変換されたコードが
	 * 空でない場合、<CODE>convertedJanCode</CODE>をJANコードとして扱い、検索します。
	 * <BR>
	 * 計4回の検索で作業可能データが見つからなかった場合はnullを返します。
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	customerCode	出荷先コード
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @param	scanCode		スキャン商品コード
	 * @param	convertedJanCode	ITFtoJAN変換されたJANコード
	 * @return	出荷予定情報  作業情報エンティティの配列<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 *          LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 */
	protected WorkingInformation[] getWorkableData(
			String consignorCode,
			String planDate,
			String workForm,
			String rftNo,
			String workerCode,
			String scanCode,
			String convertedJanCode) throws ReadWriteException, LockTimeOutException
	{
		try
		{
			// WorkingInfoを検索した結果を保持する変数
			WorkingInformation[] workingDbSortingData = null;
			WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);

			//-----------------
			// 作業情報の検索(１回目)
			// スキャンされたコードをJANコードとして検索を行う
			//-----------------
			WorkingInformationSearchKey skey = getBaseCondition(
					consignorCode,
					planDate,
					workForm,
					rftNo,
					workerCode);
			skey.setItemCode(scanCode);
			int timeout = WmsParam.WMS_DB_LOCK_TIMEOUT;

			workingDbSortingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

			if (workingDbSortingData != null && workingDbSortingData.length > 0)
			{
				return workingDbSortingData;
			}
			//-----------------
			// 作業情報の検索(２回目)
			// スキャンされたコードをケースITFとして検索を行う
			//-----------------
			skey = getBaseCondition(
					consignorCode,
					planDate,
					workForm,
					rftNo,
					workerCode);
			skey.setItf(scanCode);
			workingDbSortingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

			if (workingDbSortingData != null && workingDbSortingData.length > 0)
			{
				return workingDbSortingData;
			}
			//-----------------
			// 作業情報の検索(３回目)
			// スキャンされたコードをボールITFとして検索を行う
			//-----------------
			skey = getBaseCondition(
					consignorCode,
					planDate,
					workForm,
					rftNo,
					workerCode);
			skey.setBundleItf(scanCode);
			workingDbSortingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

			if (workingDbSortingData != null && workingDbSortingData.length > 0)
			{
				return workingDbSortingData;
			}
			if (! StringUtil.isBlank(convertedJanCode))
			{
				//-----------------
				// 作業情報の検索(４回目)
				// ITFtoJAN変換されたコードがセットされている場合、
				// それをJANコードとして検索を行う
				//-----------------
				skey = getBaseCondition(
						consignorCode,
						planDate,
						workForm,
						rftNo,
						workerCode);
				skey.setItemCode(convertedJanCode);
				workingDbSortingData = (WorkingInformation[]) pObj.findForUpdate(skey, timeout);

				if (workingDbSortingData != null && workingDbSortingData.length > 0)
				{
					return workingDbSortingData;
				}
			}
		}
		catch (LockTimeOutException e)
		{
			// 6026018=一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME, "DNWORKINFO");
			throw e;
		}
		
		return null;
	}
	/**
	 * 仕分開始要求に対する作業可能データを検索する際の基本となる検索条件を生成します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *   荷主コード、予定日、作業区分 = パラメータより取得<BR>
	 *   作業区分=04(仕分)<BR>
	 *   状態フラグ=0:未開始 or (2:作業中...但し作業者コードとRFTNoが同じであること)<BR>
	 *   作業開始フラグ=1(開始済)<BR>
	 * </DIR>
	 * 
	 * @param	consignorCode	荷主コード
	 * @param	planDate		予定日
	 * @param	workform		作業区分
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @return	作業情報検索キー	作業情報サーチキー<BR>
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。<BR>
	 *          LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 */
	protected WorkingInformationSearchKey getBaseCondition(
			String consignorCode,
			String planDate,
			String workForm,
			String rftNo,
			String workerCode) throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
	
		skey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		skey.setConsignorCode(consignorCode);
		skey.setPlanDate(planDate);
		if (workForm.equals(WorkForm.CASE))
		{
			skey.setWorkFormFlag(workForm);
		}
		else
		{
			skey.setWorkFormFlag(WorkForm.PIECE, "=", "(", "", "OR");
			skey.setWorkFormFlag(WorkForm.NOTSPECIFIED, "=", "", ")", "AND");
		}

		// 状態フラグが「未開始」又は、「作業中」(但し、作業者コードとRFTNoが同じに限る)を対象にする。 
		//  SQL文・・・ (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");

		skey.setLocationNoOrder(1, true);
		skey.setItemCodeOrder(2, true);
		skey.setItfOrder(3, true);
		skey.setBundleItfOrder(4, true);
		skey.setCollectJobNoOrder(5, true);

		return skey;
	}

	
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	// Private methods -----------------------------------------------
}
//end of class

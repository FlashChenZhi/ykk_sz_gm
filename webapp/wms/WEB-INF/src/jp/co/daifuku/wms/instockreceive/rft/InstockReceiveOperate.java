// $Id: InstockReceiveOperate.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ScheduleException;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.crossdoc.CrossDocOperator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;
import jp.co.daifuku.wms.base.coordinated.instockstorage.InstockStoragePlanCreator;

/*
 * Copyright 2003-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 入荷処理から呼び出される共通関数が定義されます。<BR>
 * <BR>
 * ！注意！<BR>
 * このクラス内で使用するWorkingInformationクラスとWorkingInformationHandlerクラスは、
 * ＲＦＴ用(jp.co.daifuku.wms.base.rftパッケージ)を使用します。<BR>
 * (親クラスのインスタンスを子クラスにキャストできない為、ＲＦＴ用WorkingInformationHandlerを使用する必要があります。)<BR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class InstockReceiveOperate
{
	// Class fields --------------------------------------------------
    /**
	 * クラス名を表すフィールド
	 */
    private static final String CLASS_NAME = "InstockReceiveOperate";

	// Class variables -----------------------------------------------   
	/**
	 * 処理名（登録処理名、最終更新処理名用）
	 */
    protected String processName = "";
    
	/**
	 * 作業開始処理名（登録処理名、最終更新処理名用）
	 */
    protected String startProcessName = "";

    /**
	 * データベースとの接続
	 */
    protected Connection wConn = null;
    
    /**
     * 情報を保持しておく為のHashtable
     */
	protected Hashtable prevWorkData = new Hashtable();

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
    public InstockReceiveOperate()
    {
    	super();
    }
    
	// Public methods ------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>をセットします。<BR>
	 * 
	 * @param c データベース接続用 Connection
	 */
    public void setConnection(Connection c)
    {
        wConn = c;
    }

    /**
     * 更新処理名をセットする
     * 
     * @param name		更新処理名
     */
    public void setProcessName(String name)
    {
        processName = name;
    }
    
    /**
     * 作業開始処理名をセットする
     * 
     * @param name		作業開始処理名
     */
    public void setStartProcessName(String name)
    {
        startProcessName = name;
    }

    /**
	 * 入荷検品実績データ [確定処理]<BR>
	 * 入荷実績情報について、以下の確定処理を実行します。<BR>
	 * 
	 * <OL>
	 *  <LI>更新対象データをロックする。
	 * （{@link #lockUpdateData(Connection,WorkingInformation[],String,String,boolean,String) lockUpdateData()}）</LI>
	 *  <LI>作業情報を完了状態に更新し、
	 * それに関連する予定情報、在庫情報、実績送信情報を作成・更新する。
	 * （{@link #updateWorkingInformation(Connection,String,String,WorkingInformation[]) updateWorkingInformation()}）
	 * また入庫予定作成処理(InstockStoragePlanCreator)の呼出しを行う。 </LI>
	 *  <LI>入荷予定情報を完了状態に更新する。
	 * （{@link #updateCompletionStatus(Connection,String[],String) updateCompletionStatus()}）</LI>
	 *  <LI>作業者実績情報を更新する。
	 * （{@link #updateWorkerResult(Connection,String,String,WorkingInformation[]) updateWorkerResult()}）</LI>
	 *  <LI>在庫移行処理を実行する。（未実装）</LI>
	 * </OL>
	 * 
	 * @param	resultData		入荷実績情報  作業情報エンティティの配列
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	workTime		作業時間
	 * @param	missScanCnt		誤検回数
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws DataExistsException 既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	*/
	public void receivingComplete(
			WorkingInformation[] resultData,
			String rftNo,
			String workerCode,
			int workTime,
			int missScanCnt)
		throws 
			InvalidStatusException, 
			NotFoundException, 
			InvalidDefineException, 
			DataExistsException, 
			UpdateByOtherTerminalException, 
			ReadWriteException, 
			LockTimeOutException, 
			ScheduleException
	{		
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			false);

		updateWorkingInformation(
			rftNo,
			workerCode,
			resultData,
			true);

		updateCompletionStatus(planUkeyList);
			
		createWorkerResult(workerCode, rftNo, workTime, missScanCnt, resultData, false);
		
		// クロスドック処理を行います
		crossDocProcess();
	}

	/**
	 * 入荷検品実績データ [分納処理]<BR>
	 * 入荷実績情報について、以下の確定処理を実行します。<BR>
	 * 
	 * <OL>
	 *  <LI>更新対象データをロックする。
	 * （{@link #lockUpdateData(Connection,WorkingInformation[],String,String,boolean,String) lockUpdateData()}）</LI>
	 *  <LI>入荷数分の作業情報を完了状態に更新し、未納分を新しい作業情報として新規作成します。
	 * それに関連する予定情報、在庫情報、実績送信情報を作成・更新する。
	 * （{@link #updateWorkingInformation(Connection,String,String,WorkingInformation[]) updateWorkingInformation()}
	 * また入庫予定作成処理(InstockStoragePlanCreator)の呼出しを行う。 </LI>
	 *  <LI>入荷予定情報を完了状態に更新する。
	 * （{@link #updateCompletionStatus(Connection,String[],String) updateCompletionStatus()}）</LI>
	 *  <LI>作業者実績情報を更新する。
	 * （{@link #updateWorkerResult(Connection,String,String,WorkingInformation[]) updateWorkerResult()}）</LI>
	 *  <LI>在庫移行処理を実行する。（未実装）</LI>
	 * </OL>
	 * 
	 * <BR>
	 * @param	resultData	入荷実績情報  作業情報エンティティの配列
	 * @param	rftNo		RFT番号
	 * @param	workerCode	作業者コード
	 * @param	workTime	作業時間
	 * @param	missScanCnt	誤検回数
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws DataExistsException 既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	public void receivingSuspend(
			WorkingInformation[] resultData,
			String rftNo,
			String workerCode,
			int workTime,
			int missScanCnt)
		throws 
			InvalidStatusException, 
			NotFoundException, 
			InvalidDefineException, 
			DataExistsException, 
			UpdateByOtherTerminalException, 
			ReadWriteException, 
			LockTimeOutException, 
			ScheduleException
	{
		String[] planUkeyList = lockUpdateData(
		    resultData,
			rftNo,
			workerCode,
			false);
		
		updateWorkingInformation(
			rftNo,
			workerCode,
			resultData,
			false);

		updateCompletionStatus(planUkeyList);

		createWorkerResult(workerCode, rftNo, workTime, missScanCnt, resultData, true);
		
		// クロスドック処理を行います
		crossDocProcess();
	}

	/**
	 * 入荷検品実績データ [キャンセル処理]<BR>
	 * 入荷検品のキャンセル処理として、以下の処理を行います。
	 * 
	 * <OL>
	 *  <LI>更新対象データをロックする。
	 * （{@link #lockUpdateData(Connection,WorkingInformation[],String,String,boolean,String) lockUpdateData()}）</LI>
	 *  <LI>作業情報の該当データを未開始状態に更新する。
	 * （{@link #updateWorkingInformation(Connection,String,String,WorkingInformation[]) updateWorkingInformation()}）</LI>
	 *  <LI>入荷予定情報を未開始状態に更新する。
	 * （{@link #updateCompletionStatus(Connection,String[],String) updateCompletionStatus()}）</LI>
	 * </OL>
	 * 
	 * <DIR>
	 * 作業情報の更新<BR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 * 	   <LI>作業者コード</LI>
	 * 	   <LI>作業種別 = 入荷</LI>
	 * 	   <LI>端末No = RFT番号</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE border="1">
	 *      <TR><TD>集約作業No</TD>		<TD>作業No</TD></TR>
	 *      <TR><TD>状態フラグ</TD>		<TD>未開始:0</TD></TR>
	 *      <TR><TD>端末No</TD>			<TD>空白</TD></TR>
	 *      <TR><TD>作業者コード</TD>	<TD>空白</TD></TR>
	 *      <TR><TD>作業者名称</TD>		<TD>空白</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 * </DIR>
	 * 
	 * @param	resultData		入荷作業実績
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws LockTimeOutException 一定時間データベースのロックが解除されない時に通知されます。
	 */
	public void receivingCancel(
			WorkingInformation[] resultData,
			String workerCode,
			String rftNo)
		throws NotFoundException, InvalidDefineException, UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
	    // 更新対象データをロックする
		String[] planUkeyList = lockUpdateData(
			resultData,
			rftNo,
			workerCode,
			true);
		
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationAlterKey akey = new WorkingInformationAlterKey();

		for (int i = 0; i < resultData.length; i ++)
		{
		    skey.KeyClear();
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			skey.setLastUpdatePname(startProcessName);
			
			WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
			WorkingInformation[] wi = (WorkingInformation[])handler.find(skey);
			
			for (int j = 0; j < wi.length; j ++)
			{
			    akey.KeyClear();
				akey.setJobNo(wi[j].getJobNo());
				
				// 集約作業Noを元に戻す(作業Noに戻す)
				akey.updateCollectJobNo(wi[j].getJobNo());
				akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				akey.updateWorkerCode("");
				akey.updateWorkerName("");
				akey.updateTerminalNo("");
				akey.updateLastUpdatePname(processName);
				handler.modify(akey);
			}
		}

		// 作業情報の状態を基に入荷予定情報の状態を元に戻す
		updateCompletionStatus(planUkeyList);
	}

	/**
	 * 仕入先一括および商品単位の入荷検品の作業データを取得するための条件を格納した
	 * SearchKeyを返す。<BR>
	 * 集約等の条件は呼び出し元でセットすること。<BR>
	 * <BR>
	 * (検索条件)
	 * <UL>
	 *  <LI>入荷予定日</LI>
	 *  <LI>荷主コード</LI>
	 *  <LI>仕入先コード（nullでない場合のみ）</LI>
	 *  <LI>商品コード（nullでない場合のみ）</LI>
	 *  <LI>作業種別 = 入荷</LI>
	 *  <LI>TC/DC区分 = DCまたはクロスTC</LI>
	 *  <LI>開始フラグ = 開始済み</LI>
	 *  <LI>状態フラグ = 未開始または自端末、自作業者作業中にしたもの</LI>
	 *  <LI>作業者コード</LI>
	 *  <LI>端末No = RFT番号</LI>
	 * </UL>
	 * 
	 * @param planDate			作業予定日
	 * @param consignorCode		荷主コード
	 * @param supplierCode		仕入先コード（nullが指定された場合は条件から外す）
	 * @param itemCode			商品コード（nullが指定された場合は条件から外す）
	 * @param workerCode		作業者コード
	 * @param rftNo				端末No
	 * @return		作業データ検索用SearchKey
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public WorkingInformationSearchKey getConditionOfInstockBySupplier(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String itemCode,
	        String workerCode,
	        String rftNo) throws ReadWriteException
	{
	    WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		skey.setPlanDate(planDate);
	    skey.setConsignorCode(consignorCode);
	    if (supplierCode != null)
	    {
		    skey.setSupplierCode(supplierCode);
	    }
	    if (itemCode != null)
	    {
		    skey.setItemCode(itemCode);
	    }
		skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		// DCまたはクロスTC
		skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
		skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		// 状態フラグが「未開始」又は、「作業中」(但し、作業者コードとRFTNoが同じに限る)を対象にする。 
		//  SQL文・・・ (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		
	    return skey;
	}
	
	/**
	 * 出荷先別の入荷検品の作業データを取得するための条件を格納した
	 * SearchKeyを返す。<BR>
	 * 集約等の条件は呼び出し元でセットすること。<BR>
	 * <BR>
	 * (検索条件)
	 * <UL>
	 *  <LI>入荷予定日</LI>
	 *  <LI>荷主コード</LI>
	 *  <LI>仕入先コード（nullでない場合のみ）</LI>
	 *  <LI>出荷先コード（nullでない場合のみ）</LI>
	 *  <LI>作業種別 = 入荷</LI>
	 *  <LI>TC/DC区分 = TC</LI>
	 *  <LI>開始フラグ = 開始済み</LI>
	 *  <LI>状態フラグ = 未開始または自端末、自作業者作業中にしたもの</LI>
	 *  <LI>作業者コード</LI>
	 *  <LI>端末No = RFT番号</LI>
	 * </UL>
	 * 
	 * @param planDate			作業予定日
	 * @param consignorCode		荷主コード
	 * @param supplierCode		仕入先コード（nullが指定された場合は条件から外す）
	 * @param customerCode		出荷先コード（nullが指定された場合は条件から外す）
	 * @param workerCode		作業者コード
	 * @param rftNo				端末No
	 * @return		作業データ検索用SearchKey
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public WorkingInformationSearchKey getConditionOfInstockByCustomer(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String customerCode,
	        String workerCode,
	        String rftNo) throws ReadWriteException
	{
	    WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		skey.setPlanDate(planDate);
	    skey.setConsignorCode(consignorCode);
	    if (supplierCode != null)
	    {
		    skey.setSupplierCode(supplierCode);
	    }
	    if (customerCode != null)
	    {
		    skey.setCustomerCode(customerCode);
	    }
		skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		// DCまたはクロスTC
		skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		// 状態フラグが「未開始」又は、「作業中」(但し、作業者コードとRFTNoが同じに限る)を対象にする。 
		//  SQL文・・・ (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		
	    return skey;
	}

	/**
	 * 仕入先一括および商品単位の入荷検品の集約された作業データを取得する。
	 * (データの残り件数取得に使用する。)
	 * 
	 * @param planDate			作業予定日
	 * @param consignorCode		荷主コード
	 * @param supplierCode		仕入先コード
	 * @param itemCode			商品コード（nullが指定された場合は条件から外す）
	 * @param workerCode		作業者コード
	 * @param rftNo				端末No
	 * @return		集約された作業データ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public WorkingInformation[] getInstockDataBySupplier(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String itemCode,
	        String workerCode,
	        String rftNo) throws ReadWriteException
	{
	    WorkingInformationSearchKey skey = null;
	    skey = getConditionOfInstockBySupplier(
	            planDate,
	            consignorCode,
	            supplierCode,
	            itemCode,
	            workerCode,
	            rftNo);
		skey.setItemCodeOrder(1, true);

		// 集約条件をセットする
		setCollectCondition(skey);
		
		WorkingInformation[] workinfo = null;
		WorkingInformationHandler wHandler = new WorkingInformationHandler(wConn);
		workinfo = (WorkingInformation[]) wHandler.find(skey);

		for (int i = 0; i < workinfo.length; i ++)
		{
		    if (workinfo[i].getHostPlanQty() > 1)
		    {
		        // データが集約されている場合は伝票No、伝票行Noの値を変更する
		        workinfo[i].setInstockTicketNo(WorkingInformation.getCollectedTicketNo());
		        workinfo[i].setInstockLineNo(WorkingInformation.collectedLineNo);
		    }
		}
		
	    return workinfo;
	}

    /**
	 * 作業の合計件数を取得する。（商品単位入荷検品用）
	 * 
	 * @param planDate			作業予定日
	 * @param consignorCode		荷主コード
	 * @param supplierCode		仕入先コード
	 * @param itemCode			商品コード
	 * @return			合計作業件数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public int countTotalItem(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String itemCode) throws ReadWriteException

	{
	    WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		skey.setPlanDate(planDate);
	    skey.setConsignorCode(consignorCode);
	    if (supplierCode != null)
	    {
		    skey.setSupplierCode(supplierCode);
	    }
	    if (itemCode != null)
	    {
		    skey.setItemCode(itemCode);
	    }
		skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		// DCまたはクロスTC
		skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
		skey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		// 状態フラグが「削除」以外を対象にする。
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "<>");

		// 集約条件をセットする
		setCollectCondition(skey);
		
		WorkingInformationHandler wHandler = new WorkingInformationHandler(wConn);
		
	    return wHandler.count(skey);
	}

	/**
	 * 作業の残り件数を取得する。（商品単位入荷検品用）
	 * 
	 * @param planDate			作業予定日
	 * @param consignorCode		荷主コード
	 * @param supplierCode		仕入先コード
	 * @param itemCode			商品コード
	 * @param workerCode		作業者コード
	 * @param rftNo				端末No
	 * @return			残り作業件数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public int countRemainingItem(
	        String planDate,
	        String consignorCode,
	        String supplierCode,
	        String itemCode,
	        String workerCode,
	        String rftNo) throws ReadWriteException

	{
	    // 入荷作業情報を取得するためのSearchKeyをセットする
	    WorkingInformationSearchKey skey = null;
	    skey = getConditionOfInstockBySupplier(
	            planDate,
	            consignorCode,
	            supplierCode,
	            itemCode,
	            workerCode,
	            rftNo);
		skey.setItemCodeOrder(1, true);

		// 集約条件をセットする
		setCollectCondition(skey);

	    // データ件数をカウントする
		WorkingInformationHandler wHandler = new WorkingInformationHandler(wConn);

		return wHandler.count(skey);
	}
	
	/**
	 * <P>
	 * パラメータとして受け取った作業情報エンティティ配列に対し、
	 * 集約可能なデータを全て集約し、新しい作業情報エンティティを返します。<BR>
	 * 集約する過程において、集約作業Noを振りなおし、DBの更新も行ないます。<BR>
	 * この時、作業状態等も同時に更新します。
	 * さらにその結果を入荷予定情報に反映させます。<BR>
	 * </P>
	 * 
	 * 集約を行うかどうかはパラメタファイル(WMSParam.properties)から取得します。<BR>
	 * <DIR>
	 * 集約を行なう場合は、集約作業Noを振り直します。
	 * (集約する中で商品コードが最も小さいデータの作業Noを使用します。)<BR>
	 * 振り直した結果で、DBを更新します。<BR>
	 * (更新内容)
	 * <TABLE border="1">
	 *   <TR><TD>集約作業No</TD>		<TD>作業中</TD></TR>
	 *   <TR><TD>状態フラグ</TD>		<TD>集約する中で商品コードが最も小さいデータの作業No</TD></TR>
	 *   <TR><TD>作業者コード</TD>		<TD>作業者コード</TD></TR>
	 *   <TR><TD>作業者名称</TD>		<TD>作業者マスタ.作業者名称</TD></TR>
	 *   <TR><TD>端末No</TD>			<TD>RFT No</TD></TR>
	 *   <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名("ID0130" or "ID0132" or "ID0140")</TD></TR>
	 * </TABLE>
	 * 集約キー項目を、商品コードで集約するパターンと、商品コード＋ITF＋ボールITFで集約するパターンの、
	 * ２パターン用意し、どちらにするのかパラメタファイルから取得します。<BR>
	 * 集約対象のTC/DC区分がDCであれば、集約後のデータをDCとします。<BR>
	 * </DIR>
	 * @param  instockData	作業情報エンティティの配列(商品コード順にソートされている事)
	 * 						この配列は空であってはなりません。
	 * @param	workerCode	作業者コード
	 * @param	rftNo		端末No
	 * @return			集約後の作業情報エンティティ配列
	 * @throws NotFoundException  		作業可能なデータが見つからない時に通知されま
	 * @throws InvalidDefineException 	指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException 		データベースとの接続で異常が発生した場合に通知されます。
	 * @throws OverflowException		数値項目の桁数が超過した時に通知されます。
	 */
	public WorkingInformation[] collectInstockReceiveData(
	        WorkingInformation[] instockData,
	        String workerCode,
	        String rftNo) 
		throws NotFoundException, InvalidDefineException, ReadWriteException, OverflowException
	{
        
		// 作業者名称取得
		BaseOperate bo = new BaseOperate(wConn);
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e)
		{
			// 作業者コードなし 開始～データ要求までの間に作業者マスターを使用停止にされた場合
			// 6026019=作業者マスタテーブルに該当作業者が見つかりません。作業者コード:{0}
			RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME, workerCode);
			NotFoundException ex = new NotFoundException(RFTId5130.ANS_CODE_ERROR);
			throw ex;
		}

		// 作業集約処理
	    Vector v = new Vector();
	    
        int jobType = SystemParameter.getJobType(instockData[0].getJobType());

        // 集約作業Noを付け直しする場合（集約する場合）
		WorkingInformation currentData = (WorkingInformation) instockData[0].clone();
		instockData[0].setWorkerCode(workerCode);
		instockData[0].setWorkerName(workerName);
		instockData[0].setTerminalNo(rftNo);
		for (int i = 1; i < instockData.length; i++)
		{
			if (SystemParameter.isRftWorkCollect(jobType))
	        {
		        if (currentData.hasSameKey(instockData[i]))
				{
					try
					{
				    	// 作業情報を集約する
				    	currentData.collect(instockData[i]); 
				    	// 集約対象のTC/DC区分がDCであれば、集約後のデータをDCとする。
				    	if (instockData[i].getTcdcFlag().equals(WorkingInformation.TCDC_FLAG_DC))
				    	{
				    		currentData.setTcdcFlag(instockData[i].getTcdcFlag());
				    	}
				    	// 集約する場合は集約作業Noを更新する
				    	instockData[i].setCollectJobNo(currentData.getCollectJobNo());
					}
					catch (OverflowException e)
					{
						// 6026028=集約処理でオーバーフローが発生しました。テーブル名:{0}
						RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNWORKINFO");
						throw e;
					}
					catch (InvalidStatusException e)
					{
						// 6026015=ID対応処理で異常が発生しました。{0}
						RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
						throw new InvalidDefineException();
					}
				}
				else
				{
				    // キーが変わったらVectorに登録する
				    v.addElement(currentData);

				    // 集約結果保持用に作業情報をコピーする
				    currentData = (WorkingInformation) instockData[i].clone();
				}
	        }

			instockData[i].setWorkerCode(workerCode);
			instockData[i].setWorkerName(workerName);
			instockData[i].setTerminalNo(rftNo);
		}
		
	    // 最後のデータをVectorに登録する
	    v.addElement(currentData);

	    // 状態を更新する
		for (int i = 0; i < instockData.length; i ++)
		{
			// 作業情報を作業中に更新する
			updateWorkInfoStatusToNowWorking(instockData[i]);
			
			// 入荷予定情報を作業中に更新する
			updateInstockPlanStatusToNowWorking(instockData[i]);
		}
		
		if (instockData.length != 1 && SystemParameter.isRftWorkCollect(jobType))
		{
			// 集約する場合は集約されたデータを返す
			WorkingInformation[] collectedData = new WorkingInformation[v.size()];
			v.copyInto(collectedData);

			return collectedData;
		}

	    // 集約しない場合は元のデータをそのまま返す
	    return instockData;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 作業情報およびそれに関連する予定情報、在庫情報、実績送信情報を作成・更新する。
	 * <OL>
	 * <LI>作業情報を更新する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>状態フラグ=2:作業中</LI>
	 * 	   <LI>作業者コード</LI>
	 * 	   <LI>作業種別 = 入荷</LI>
	 * 	   <LI>端末No = RFT番号</LI>
	 *    </UL>
	 *    (ソート順)
	 *    <UL>
	 *     <LI>作業No</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>データファイル.実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>データファイル.実績数が作業可能数に満たない場合、不足分をセットする（欠品時のみ）</TD></TR>
	 *      <TR><TD>保留数</TD>			<TD>データファイル.実績数が作業可能数に満たない場合、不足分をセットする（分納時のみ）</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>データファイル.賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名("ID0131" or "ID0133" or "ID0141")</TD></TR>
	 *    </TABLE>
	 *   複数レコードが該当する事を考慮する必要があります。<BR>
	 *   実際に検品した実績数を、該当レコードの作業実績数に分配します。分配する数量は「作業可能数」です。<BR>
	 *   過剰の場合は、最後のレコードに分配します。<BR>
	 *   また、分納の場合は納品分を完了とし、未納分の作業情報を新たに作成します。
	 *   作成するレコードの内容は以下のとおりで、これ以外のデータは元作業情報と同じとする。
	 *    (作成内容)
	 *    <TABLE>
	 *      <TR><TD>作業No</TD>			<TD>採番</TD></TR>
	 *      <TR><TD>集約作業No</TD>		<TD>作業No</TD></TR>
	 *      <TR><TD>状態フラグ</TD>		<TD>未作業</TD></TR>
	 *      <TR><TD>予定数</TD>			<TD>元作業可能数 - データファイル.実績数</TD></TR>
	 *      <TR><TD>作業可能数</TD>		<TD>元作業可能数 - データファイル.実績数</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>0</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>0</TD></TR>
	 *      <TR><TD>保留数</TD>			<TD>0</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>空白</TD></TR>
	 *      <TR><TD>登録処理名</TD>		<TD>呼び出し元の処理名("ID0131" or "ID0133" or "ID0141")</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>空白</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * </LI>
	 * <LI>出荷予定情報の数量を更新する。
	 * （{@link #updateInstockPlan(WorkingInformation,String) updateInstockPlan()}）</LI>
	 * <LI>在庫情報を更新する。
	 * （{@link #updateStockQty(WorkingInformation[],String) updateStockQty()}）</LI>
	 * <LI>実績送信情報を作成する。
	 * （{@link #createResultData(WorkingInformation[],String) createResultData()}）</LI>
	 * </OL>
	 * 
	 * @param	rftNo			RFT番号
	 * @param	workerCode		作業者コード
	 * @param	resultData	入荷実績情報  作業情報エンティティの配列
	 * @param	isShortage		数量不足のときに欠品にするかどうかのフラグ
	 * @throws InvalidStatusException 範囲外の状態をセットした場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws DataExistsException 既に同じ情報が登録済みの場合に通知されます。
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 */
	protected void updateWorkingInformation(
			String rftNo,
			String workerCode,
			WorkingInformation[] resultData,
			boolean isShortage)
		throws 
			InvalidStatusException, 
			NotFoundException, 
			InvalidDefineException, 
			ReadWriteException, 
			DataExistsException, 
			UpdateByOtherTerminalException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		WorkingInformationHandler handler =
			new WorkingInformationHandler(wConn);

		for (int i = 0; i < resultData.length; i++)
		{
			// DBを検索する
			skey.KeyClear();
			skey.setTerminalNo(rftNo);
			skey.setWorkerCode(workerCode);
			skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
			// TC->DCの順にデータ取得し、超過時にDCに加算されるようにする。
			skey.setTcdcFlagOrder(1,false);
			skey.setCollectJobNoOrder(2, true);
			skey.setJobNoOrder(3, true);
			// 集約作業No
			skey.setCollectJobNo(resultData[i].getCollectJobNo());
			WorkingInformation[] wi = (WorkingInformation[]) handler.find(skey);

			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			
			if (handler.count(skey) <= 0)
			{
				throw new NotFoundException();
			}
			
			// 集約データが他端末で更新されたかどうかチェック
			// 更新されていた場合は、UpdateByOtherTerminalExceptionがthrowされます。
			checkCollectData(resultData[i], wi);
			
			// 入荷実績情報の実績数を保持
			int workQty = resultData[i].getResultQty();

			for (int j = 0; j < wi.length; j ++)
			{
				WorkingInformationAlterKey akey = new WorkingInformationAlterKey();
				akey.setJobNo(wi[j].getJobNo());
				akey.setTerminalNo(rftNo);
				akey.setWorkerCode(workerCode);
				
				if( workQty > 0 || isShortage )
				{
					// ◆分配する数がある時又は欠品完了の場合
					// 分納した場合の新規データ用作業情報エンティティ
					jp.co.daifuku.wms.base.entity.WorkingInformation newWorkinfo = null;

					int resultQty = workQty;
					// 更新する値をセットする
					// 次データある場合で作業可能数より入荷実績情報の実績数が大きい場合
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
					akey.updateLastUpdatePname(processName);

					if (wi[j].getResultQty() < wi[j].getPlanEnableQty())
					{
						// 実績数より作業可能数が大きい場合(欠品or分納の場合)
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
							// 分納の場合
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
							newWorkinfo.setRegistPname(processName);
							newWorkinfo.setLastUpdatePname(processName);
							// 新しい作業Noを割り当てる
							SequenceHandler sh = new SequenceHandler(wConn);
							newWorkinfo.setJobNo(sh.nextJobNo());
							// 集約作業Noに作業Noをセットする
							newWorkinfo.setCollectJobNo(newWorkinfo.getJobNo());
						}
					}
	
					akey.updateResultUseByDate(resultData[i].getResultUseByDate());
					// 現在の作業情報エンティティの賞味期限をセット
					wi[j].setResultUseByDate(resultData[i].getResultUseByDate());

					// 集約作業Noを元に戻す(作業Noに戻す)
					akey.updateCollectJobNo(wi[j].getJobNo());
					
					// DBを更新する
					handler.modify(akey);
					if (newWorkinfo != null)
					{
						// 分割レコード作成
						handler.create(newWorkinfo);
					}
	
					// 出荷予定情報の数量を更新する
					updateInstockPlan(wi[j]);
					// 在庫情報の数量を更新する
					updateStockQty(wi[j]);					
					// 実績送信情報を作成する
					createResultData(wi[j]);
				}
				else
				{
					// ◆保留処理で分配する数がない場合		
					// キャンセル処理を行う
					akey.updateStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
					akey.updateWorkerCode("");
					akey.updateWorkerName("");
					akey.updateTerminalNo("");
					akey.updateLastUpdatePname(processName);
					// 集約作業Noを元に戻す(作業Noに戻す)
					akey.updateCollectJobNo(wi[j].getJobNo());
					// DBを更新する
					handler.modify(akey);
				}
				
				// 入庫予定作成処理（入荷入庫連携）
				InstockStoragePlanCreator planCre = new InstockStoragePlanCreator(wConn);
				planCre.startMakePlan(wConn, wi[j]);
			}			
		}
	}

    /**
	 * 実績送信情報の作成<BR>
	 * <DIR>
	 *   作業日をWareNavi_Systemから取得し、作業日にセットします。<BR>
	 *   登録処理名セットします。<BR>
	 *   その他の項目は、作業情報から取得し、セットします。<BR>
	 * </DIR>
	 * 
	 * @param workinfo	作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void createResultData(WorkingInformation workinfo) throws ReadWriteException
	{
		HostSend result;
		BaseOperate baseOperate = new BaseOperate(wConn);
		
		try
		{
			result = new HostSend(
							workinfo,
							baseOperate.getWorkingDate(),
							processName);
			HostSendHandler handler = new HostSendHandler(wConn);
			handler.create(result);
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw new ReadWriteException();
		}
	}

	/**
	 * 更新した作業情報を元に、入荷予定情報を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>入荷予定情報.入荷予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の入荷予定情報があります。)
	 *     <LI>状態フラグ != 削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>完了:4</TD></TR>
	 *      <TR><TD>作業実績数</TD>		<TD>電文の実績数</TD></TR>
	 *      <TR><TD>作業欠品数</TD>		<TD>電文の実績数が作業可能数に満たない場合、不足分をセットする</TD></TR>
	 *      <TR><TD>賞味期限</TD>		<TD>電文の賞味期限</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名("ID0131" or "ID0133" or "ID0141")</TD></TR>
	 *    </TABLE>
	 *   入荷実績数、入荷欠品数に、対応した作業情報の実績数、欠品数を加算します。<BR>
	 *   ここでは状態フラグは更新しません。
	 * </DIR>
	 * 
	 * @param workinfo	作業情報
	 * @throws InvalidStatusException ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateInstockPlan(WorkingInformation workinfo)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		try
		{
			// 該当する入荷予定情報を検索する
			InstockPlanSearchKey skey = new InstockPlanSearchKey();
			skey.setInstockPlanUkey(workinfo.getPlanUkey());
			skey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			InstockPlan plan = new InstockPlan();
			InstockPlanHandler handler = new InstockPlanHandler(wConn);
			plan = (InstockPlan) handler.findPrimaryForUpdate(skey);

			// 検索した入荷予定情報を更新する
			InstockPlanAlterKey akey = new InstockPlanAlterKey();
			akey.setInstockPlanUkey(workinfo.getPlanUkey());
			akey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			akey.updateResultQty(plan.getResultQty() + workinfo.getResultQty());
			akey.updateShortageCnt(plan.getShortageCnt() + workinfo.getShortageCnt());
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
			
			setHashTable(workinfo);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DnInStockPlan" + 
								" INSTOCK_PLAN_UKEY = " + workinfo.getPlanUkey() +"]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			// 適切なExceptionに変更する
			throw new InvalidStatusException();
		}
	}

	/**
	 * 更新した作業情報の入荷予定一意キーを元に、入荷予定情報の状態フラグを更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>入荷予定一意キー=作業情報.予定一意キー (作業情報に対応して、1件の入荷予定情報があります。)
	 *     <LI>状態フラグ != 削除</LI>
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>作業情報の状態による（未開始 or 一部完了 or 完了）</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名("ID0131" or "ID0133" or "ID0141")</TD></TR>
	 *    </TABLE>
	 *   状態フラグを更新します。実績数+欠品数 >= 予定数 を満たす時は「完了」、それ以外は｢一部完了｣。 <BR>
	 * </DIR>
	 * 
	 * @param	planUkey	作業情報の入荷予定一意キーの配列
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateCompletionStatus(String[] planUkey)
		throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey =
				new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);
				
			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler =
				new WorkingInformationHandler(wConn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			InstockPlanHandler sHandler = new InstockPlanHandler(wConn);
			InstockPlanAlterKey akey = new InstockPlanAlterKey();
			akey.setInstockPlanUkey(planUkey[i]);
			akey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
			boolean existWorkingData = false;
			for (int j = 0; j < workinfo.length; j++)
			{
				String status = workinfo[j].getStatusFlag();
				if (status.equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					existCompleteData = true;
				}
				else if (status.equals(WorkingInformation.STATUS_FLAG_UNSTART))
				{
					existUnstartData = true;
				}
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (! existCompleteData && ! existUnstartData))
			{
				// 作業中データがある場合、または完了、未開始データが共にない場合は
				// 状態フラグは更新しない
				// （通常はありえない）
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					// 完了データ、未開始データの両方が存在する場合は一部完了にする
					akey.updateStatusFlag(
					        InstockPlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					// 完了データのみが存在する場合は完了にする
					akey.updateStatusFlag(
					        InstockPlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				// 未開始データのみが存在する場合は未開始にする
				akey.updateStatusFlag(InstockPlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}

	/**
	 * 更新した作業情報を元に、在庫情報の在庫数、作業予定数を更新する
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫情報.在庫ID=作業情報.在庫ID
	 *    </UL>
	 *    (更新内容)
	 *    <TABLE>
	 *      <TR><TD>状態フラグ</TD>		<TD>センター在庫</TD></TR>
	 *      <TR><TD>在庫数</TD>			<TD>(+)実績送信電文.実績数</TD></TR>
	 *      <TR><TD>作業予定数</TD>		<TD>(-)実績送信電文.予定数(マイナス値になる時は0をセット)</TD></TR>
	 *      <TR><TD>在庫ステータス</TD>	<TD>作業予定数が0の時は、9:完了をセット</TD></TR>
	 *      <TR><TD>最終更新処理名</TD>	<TD>呼び出し元の処理名("ID0131" or "ID0133" or "ID0141")</TD></TR>
	 *    </TABLE>
	 * </DIR>
	 * 
	 * @param workinfo	作業情報
	 * @throws InvalidStatusException ユニークキーでの検索に複数件該当した場合に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateStockQty(WorkingInformation workinfo)
		throws
			InvalidStatusException,
			ReadWriteException,
			NotFoundException,
			InvalidDefineException
	{
		try
		{
			// 在庫数、引当数から作業数を減算する
			StockSearchKey skey = new StockSearchKey();
			skey.setStockId(workinfo.getStockId());
			Stock stock = new Stock();
			StockHandler handler = new StockHandler(wConn);
			stock = (Stock) handler.findPrimary(skey);
			StockAlterKey akey = new StockAlterKey();
			akey.setStockId(workinfo.getStockId());
			// 入荷した数量分在庫を増やす
			akey.updateStockQty(stock.getStockQty() + workinfo.getResultQty());
			int planQty = workinfo.getResultQty() + workinfo.getShortageCnt();
			if ((stock.getPlanQty() - planQty) < 0)
			{
				akey.updatePlanQty(0);
			}
			else
			{
				akey.updatePlanQty(stock.getPlanQty() - planQty);
			}
			if ((stock.getPlanQty() - planQty) <= 0)
			{
				// 在庫ステータス(9:完了)更新
				akey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
			}
			akey.updateLastUpdatePname(processName);
			handler.modify(akey);
		}
		catch (NoPrimaryException e)
		{
			String errString = "[Table:DmStock" + 
								" STOCK_ID = " + workinfo.getStockId() +"]";
			// 6026020=ユニークキーでの検索に複数件該当しました。{0}
			RftLogMessage.print(6026020, LogMessage.F_ERROR, CLASS_NAME, errString);
			// 適切なExceptionに変更する
			throw new InvalidStatusException();
		}
	}

	/**
	 * 更新対象データの入荷予定一意キーのリストを取得する。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>作業者コード</LI>
	 *     <LI>RFTNo</LI>
	 *     <LI>作業区分 = 入荷</LI>
	 *     <LI>状態フラグ = 作業中</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	collectJobNoList	集約作業Noのリスト
	 * @param	rftNo				RFT番号
	 * @param	workerCode			作業者コード
	 * @return					条件に該当する入荷予定一意キーの配列
	 * @throws ReadWriteException		データベースエラー発生
	 */
	protected String[] getPlanUkeyList(
		String[] collectJobNoList,
		String rftNo,
		String workerCode)
		throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setCollectJobNo(collectJobNoList);
		skey.setTerminalNo(rftNo);
		skey.setWorkerCode(workerCode);
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		skey.setPlanUkeyCollect("DISTINCT");
		skey.setPlanUkeyOrder(1, true);
		WorkingInformationHandler wHandler =
			new WorkingInformationHandler(wConn);
		WorkingInformation[] workinfo =
			(WorkingInformation[]) wHandler.find(skey);

		String[] planUkey = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			planUkey[i] = workinfo[i].getPlanUkey();
		}
		return planUkey;
	}

	/**
	 * 作業者実績を作成する。
	 * 
	 * @param	workerCode		作業者コード
	 * @param	rftNo			RFT番号
	 * @param	workTime		作業時間
	 * @param	workinfo		出荷実績情報  作業情報エンティティの配列
	 * @param	isPending		保留かどうかのフラグ
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 */
	protected void createWorkerResult(
			String workerCode, 
			String rftNo,
			int workTime,
			int missScanCnt,
			WorkingInformation[] workinfo,
			boolean isPending) throws ReadWriteException, NotFoundException
	{
		int workQty = 0;
		int workCnt = 0;
		int realWorkTime = 0;
		for (int i = 0; i < workinfo.length; i++)
		{
			workQty += workinfo[i].getResultQty();
			realWorkTime += workinfo[i].getWorkTime();
			if( isPending )
			{
				// 分納の場合、全数保留は作業回数に計上しない
				if( workinfo[i].getResultQty() > 0 )
				{
					workCnt++;
				}
			}
			else
			{
				workCnt++;
			}
		}
		
		WorkerResult wr = new WorkerResult();
		BaseOperate bo = new BaseOperate(wConn);

		wr.setWorkDate(bo.getWorkingDate());
		wr.setWorkerCode(workerCode);
		wr.setTerminalNo(rftNo);
		wr.setJobType(WorkerResult.JOB_TYPE_INSTOCK);
		wr.setWorkCnt(workCnt);
		wr.setOrderCnt(1);
		wr.setWorkQty(workQty);
		wr.setWorkTime(workTime);
		wr.setRealWorkTime(realWorkTime);			
		if (workQty > 0 || ! isPending)
		{
			// 全数保留でなければ誤検回数をセットする
			wr.setMissScanCnt(missScanCnt);
		}

		try
		{
			bo.alterWorkerResult(wr);
		}
		catch (NotFoundException e)
		{
			String errData = "[RftNo:" + workerCode
							+ " WorkerCode:" + rftNo
							+ " JobType:" + WorkerResult.JOB_TYPE_INSTOCK + "]";
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
		        throw new ReadWriteException("6006002");
            }
		    catch (DataExistsException e1)
            {
				//6006002 = データベースエラーが発生しました。{0}
				RftLogMessage.print(6006002, CLASS_NAME, "DnWorkerResult");
				//ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
		        throw new ReadWriteException("6006002");
            }
		}
	}
	
	/**
	 * 更新対象データをロックし、そのデータの入荷予定一意キーのリストを返す。<BR>
	 * 更新の対象となるテーブルは作業情報、入荷予定情報、
	 * 在庫情報（キャンセルでない場合のみ）である。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>集約作業No</LI>
	 *     <LI>作業区分 = 入荷</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workinfo	出荷実績情報  作業情報エンティティの配列
	 * @param	rftNo		RFT番号
	 * @param	workerCode	作業者コード
	 * @param	isCancel	確定処理がキャンセルかどうかのフラグ
	 * 							（キャンセル時には在庫をロックしない）
	 * @return				条件に該当する入荷予定一意キーの配列
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException		データベースエラー発生
	 * @throws LockTimeOutException	ロックタイムアウト発生
	 */
	protected String[] lockUpdateData(
		WorkingInformation[] workinfo,
		String rftNo,
		String workerCode,
		boolean isCancel) throws UpdateByOtherTerminalException, ReadWriteException, LockTimeOutException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);

		String[] collectJobNoList = new String[workinfo.length];
		for (int i = 0; i < workinfo.length; i ++)
		{
		    collectJobNoList[i] = workinfo[i].getCollectJobNo();
		}

		skey.setCollectJobNo(collectJobNoList);
		skey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		skey.setCollectJobNoOrder(1, true);
		skey.setJobNoOrder(2, true);

		// WorkingInformationをロックする
		WorkingInformation[] wi = null;
		
		wi = (WorkingInformation[])handler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		
		// 他端末で更新されていないかどうかをチェックする
		if (wi.length <= 0)
		{
	        throw new UpdateByOtherTerminalException();
		}
		
		for (int i = 0; i < wi.length; i ++)
		{
		    // 自端末が作業中にしたデータでない場合
		    if (! rftNo.equals(wi[i].getTerminalNo()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    // 作業中にした作業者が異なる場合
		    if (! workerCode.equals(wi[i].getWorkerCode()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }
		    
		    // 状態が作業中でない場合
		    if (! wi[i].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
		    {
		        throw new UpdateByOtherTerminalException();
		    }

		    // 最終更新処理名が対応するデータ要求処理でない場合
		    if (! startProcessName.equals(wi[i].getLastUpdatePname()))
		    {
		        throw new UpdateByOtherTerminalException();
		    }
		}
		
		// 出庫予定一意キーのリストを生成する
		String[] planUkeyList = getPlanUkeyList(collectJobNoList, rftNo, workerCode);

		// 出庫予定情報をロックする
		InstockPlanHandler iHandler = new InstockPlanHandler(wConn);
		InstockPlanSearchKey iskey = new InstockPlanSearchKey();
		
		iskey.setInstockPlanUkey(planUkeyList);
		iskey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");		
		iHandler.findForUpdate(iskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
		
		// 更新対象の在庫情報をロックする
		if (! isCancel)
		{
		    // 在庫管理ありかつキャンセル処理でない場合のみ在庫をロックする
		    lockStockData(wi);
		}
		
		return planUkeyList;
	}

	/**
	 * 更新対象の在庫情報をロックする。<BR>
	 * 引数で指定された作業情報の配列から在庫IDの配列を生成し、
	 * 該当する在庫情報をロックする。
	 * <DIR>
	 *    (検索条件)
	 *    <UL>
	 *     <LI>在庫ID</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param	workinfo		作業情報の配列
	 * @throws ReadWriteException		データベースエラー発生
	 * @throws LockTimeOutException	ロックタイムアウト発生
	 */
	protected void lockStockData(WorkingInformation[] workinfo)
		throws ReadWriteException, LockTimeOutException
	{
	    String[] stockIdList = new String[workinfo.length];
	    for (int i = 0; i < workinfo.length; i ++)
	    {
            stockIdList[i] = workinfo[i].getStockId();
	    }
		StockSearchKey skey = new StockSearchKey();
		skey.setStockId(stockIdList);
		
		StockHandler shandler = new StockHandler(wConn);
		shandler.findForUpdate(skey, WmsParam.WMS_DB_LOCK_TIMEOUT);
	}

	/**
	 * 作業情報を作業中に更新する。<BR>
	 * RFTに送信するデータが集約されている場合、集約作業Noも更新する。
	 * 
	 * @param instockData	作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws NotFoundException  更新対象データが存在しない場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateWorkInfoStatusToNowWorking(WorkingInformation instockData) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		// 作業情報の更新 (集約作業Noを変更したレコードを更新)
		WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();

		// 作業情報を作業中に更新する
		// 更新条件セット
		workingAlterKey.setJobNo(instockData.getJobNo());
		// 更新内容セット
		workingAlterKey.updateCollectJobNo(instockData.getCollectJobNo());
		workingAlterKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		workingAlterKey.updateTerminalNo(instockData.getTerminalNo());
		workingAlterKey.updateWorkerCode(instockData.getWorkerCode());
		workingAlterKey.updateWorkerName(instockData.getWorkerName());
		workingAlterKey.updateLastUpdatePname(processName);

		WorkingInformationHandler workingHandler = new WorkingInformationHandler(wConn);
		workingHandler.modify(workingAlterKey);
	}
	
	/**
	 * 入荷予定情報を作業中に更新する。
	 * 
	 * @param instockData	作業情報
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)の時に通知されます。
	 */
	protected void updateInstockPlanStatusToNowWorking(WorkingInformation instockData) throws ReadWriteException, InvalidDefineException
	{
		// 入荷予定情報を作業中に更新する
		InstockPlanAlterKey instockAlterKey = new InstockPlanAlterKey();
		// 更新条件セット
		instockAlterKey.setInstockPlanUkey(instockData.getPlanUkey());
		// 更新内容セット
		instockAlterKey.updateStatusFlag(InstockPlan.STATUS_FLAG_NOWWORKING);
		instockAlterKey.updateLastUpdatePname(processName);

		InstockPlanHandler instockHandler = new InstockPlanHandler(wConn);
		try
		{
			instockHandler.modify(instockAlterKey);
		}
		catch (NotFoundException e)
		{
		    // 6006005=更新対象データがありません。テーブル名:{0}
			RftLogMessage.print(6006005, LogMessage.F_ERROR, CLASS_NAME, "DnInstockPlan");
		    throw new ReadWriteException("6006005\tDnInstockPlan");
		}
	}
	
	/**
	 * ハッシュテーブルに値を保持します。 <BR>
	 * 
	 * @param workinfo 保持する実績情報です。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void setHashTable(WorkingInformation workinfo) throws ReadWriteException
	{
		// クロスドックパッケージが入っていた場合、ハッシュテーブルに値を追加します
		if (SystemParameter.withCrossDocManagement())
		{
			int[] wQty = new int[2]; 
			// 入荷予定一意キーをキーに実績数と欠品数をHashTableに保持しておく
			if (prevWorkData.containsKey(workinfo.getPlanUkey()))
			{
				// ◆予定一意キーがハッシュテーブルに存在する場合
				
				// ハッシュテーブルから値を取得します
				wQty = (int[])prevWorkData.get(workinfo.getPlanUkey());				
				// 実績数をハッシュテーブルの実績数に加算します
				wQty[0] += workinfo.getResultQty();
				// 欠品数をハッシュテーブルの欠品数に加算します
				wQty[1] += workinfo.getShortageCnt();
						
				prevWorkData.remove(workinfo.getPlanUkey());
				// ハッシュテーブルに値を保持します
				prevWorkData.put(workinfo.getPlanUkey(), wQty);
			}
			else
			{
				// ◆予定一意キーがハッシュテーブルに存在しない場合
						
				// 実績数
				wQty[0] += workinfo.getResultQty();
				// 欠品数
				wQty[1] += workinfo.getShortageCnt();
						
				prevWorkData.remove(workinfo.getPlanUkey());
				// ハッシュテーブルに値を保持します
				prevWorkData.put(workinfo.getPlanUkey(), wQty);
			}
		}
	}
	
	/**
	 * CrossDocOperatorに値を渡してクロスドック処理を行います。 <BR>
	 * 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
	 */
	protected void crossDocProcess() throws ReadWriteException, ScheduleException
	{
		// クロスドックパッケージが入っていた場合、クロスドック処理を行う
		if (SystemParameter.withCrossDocManagement())
		{
			CrossDocOperator wCrossDocOperator = new CrossDocOperator();
			
			for( Enumeration e = prevWorkData.keys(); e.hasMoreElements();)
			{
				// ハッシュテーブルから値を取得します
				String key = e.nextElement().toString();
				
				int[] wQty = new int[2];
				
				wQty = (int[])prevWorkData.get(key);
				
				// CrossDocOperatorに値を渡しますコネクション、予定一意キー、実績数、欠品数)
				if (!wCrossDocOperator.complete(wConn, key, wQty[0], wQty[1]))
				{
					throw new ScheduleException();
				}
			}
			// ハッシュテーブルをクリアします
			prevWorkData.clear();
		}
	}

	/**
	 * 集約対象データが他端末で更新されているかをチェックします。
	 * RFTが保持する予定数と、集約作業No.で検索した作業情報データの予定数の合計
	 * で判断します。
	 * 予定数が一致しない場合は、他端末で更新されたものとして、
	 * UpdateByOtherTerminalExceptionをthrowします。
	 * 
	 * @param collectDataOnRft RFTが保持するデータ
	 * @param collectDataOnDB	現在の作業情報
	 * @throws UpdateByOtherTerminalException 該当データが他で更新された時に通知されます。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void checkCollectData(WorkingInformation collectDataOnRft, WorkingInformation[] collectDataOnDB) 
		throws UpdateByOtherTerminalException, ReadWriteException
	{
		// DB上の予定数の合計を算出
		int count = 0;
		for(int i=0; i < collectDataOnDB.length; i++)
		{
			count += collectDataOnDB[i].getPlanEnableQty();
		}
		
		// 予定数がRFTで持っている数と一致しない場合、他端末で更新された
		if(collectDataOnRft.getPlanEnableQty() != count)
		{
			throw new UpdateByOtherTerminalException();
		}
		
		// 状態をチェックする
		// 全て作業中で、最終更新処理名がこのクラスの場合のみOK
		for (int j = 0; j < collectDataOnDB.length; j++)
		{
		    // 状態が作業中以外であればエラー
			if (! collectDataOnDB[j].getStatusFlag().equals(WorkingInformation.STATUS_FLAG_NOWWORKING))
			{
				throw new UpdateByOtherTerminalException();
			}

			// 最終更新処理名が要求スレッド以外であればエラー
			if (! collectDataOnDB[j].getLastUpdatePname().equals(startProcessName))
			{
				throw new UpdateByOtherTerminalException();
			}
		}
	}
	
	/**
	 * 件数をカウントする際の集約条件をSearchKeyにセットする
	 * 
	 * @param skey		検索条件がセットされたSearchKey
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	protected void setCollectCondition(WorkingInformationSearchKey skey) throws ReadWriteException
	{
		// 集約する場合のみ集約条件をセットする
		if (SystemParameter.isRftWorkCollect(SystemParameter.JOB_TYPE_INSTOCK))
		{
			skey.setItemCodeGroup(1);
			if (! SystemParameter.isRftWorkCollectItemCodeOnly(SystemParameter.JOB_TYPE_INSTOCK))
			{
			    // ITF違いを別商品として作業する場合、それらを集約条件に含める
				skey.setItfGroup(2);
				skey.setBundleItfGroup(3);
			}

			skey.setPlanEnableQtyCollect("SUM");
			skey.setResultQtyCollect("SUM");
			// ホスト予定数を集約件数で代用する
			skey.setHostPlanQtyCollect("COUNT");
			skey.setJobNoCollect("MIN");
			skey.setInstockTicketNoCollect("MAX");
			skey.setInstockLineNoCollect("MAX");
			skey.setItfCollect("MIN");
			skey.setBundleItfCollect("MIN");
			skey.setItemName1Collect("MAX");
			skey.setEnteringQtyCollect("MAX");
			skey.setBundleEnteringQtyCollect("MAX");
		}

		return;
	}
	// Private methods -----------------------------------------------
}
//end of class

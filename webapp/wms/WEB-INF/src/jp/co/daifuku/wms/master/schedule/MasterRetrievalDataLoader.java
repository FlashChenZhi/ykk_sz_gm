//$Id: MasterRetrievalDataLoader.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.RetrievalPlanMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.CustomerOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalDataLoader;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanOperator;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

/**
 * Designer : M.Takeuchi <BR>
 * Maker : M.Takeuchi <BR>
 * <BR>
 * 出庫データ取込み時にマスタデータの補完処理を行うためのクラスです。<BR>
 * 実際の取り込み処理（重複データチェック、DBへの書き込みなど）は
 * 継承元の親クラスで行います。<BR>
 * 本クラスでは、読み込み行へのマスタデータの補完処理、
 * 読み込み行に入力されているコードがマスタに存在するかのチェックを行います。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>M.Inoue</TD><TD>新規作成</TD></TR>
 * <TR><TD>2006/05/31</TD><TD>Y.Okamura</TD><TD>補完とチェック処理のみ継承するよう変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterRetrievalDataLoader extends RetrievalDataLoader
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * クラス名
	 */
	protected final String pName = "MasterRetrievalDataLoader";

	/**
	 * 荷主マスタオペレータクラス
	 */
	private ConsignorOperator wConsignorOperator = null;

	/**
	 * 出荷先マスタオペレータクラス
	 */
	private CustomerOperator wCustomerOperator = null;

	/**
	 * 商品マスタオペレータクラス
	 */
	private ItemOperator wItemOperator = null;

	/**
	 * 補完クラス
	 */
	private MergerWrapper wMerger = null;
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 出庫予定データ取り込み処理を行います。<BR>
	 * 処理は継承元の処理を呼び出します。
	 * 本メソッドでは、マスタデータ補完時に使用する
	 * オペレータクラスの生成を行います。
	 * 
	 * @see jp.co.daifuku.wms.retrieval.schedule.RetrievalDataLoader#load
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		wConsignorOperator = new ConsignorOperator(conn);
		wCustomerOperator = new CustomerOperator(conn);
		wItemOperator = new ItemOperator(conn);
		wMerger = new RetrievalPlanMGWrapper(conn);
		return super.load(conn, wns, searchParam);

	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * ファイルからの読み込みデータをマスタデータで補完しパラメータクラスにセットし返します。<BR>
	 * <BR>
	 * <B>処理順</B>
	 * <DIR>
	 * 1.ファイルからの読み込みデータをパラメータにセット（継承元で行う）<BR>
	 * 2.マスタデータを補完する<BR>
	 * 3.補完したデータを返却<BR>
	 * </DIR>
	 * 
	 * @see jp.co.daifuku.wms.retrieval.schedule.RetrievalDataLoader#setParameter
	 */
	protected RetrievalSupportParameter setParameter(DataLoadCsvReader reader, String bachNo, String workercode, String workername, String terminal)
		throws ReadWriteException, ScheduleException, CsvIllegalDataException
	{
		RetrievalSupportParameter retParam = super.setParameter(reader, bachNo, workercode, workername, terminal);

		RetrievalPlan retrievalPlan = new RetrievalPlan();

		// 入力値から補完処理を行う
		retrievalPlan.setConsignorCode(retParam.getConsignorCode());
		retrievalPlan.setConsignorName(retParam.getConsignorName());
		retrievalPlan.setCustomerCode(retParam.getCustomerCode());
		retrievalPlan.setCustomerName1(retParam.getCustomerName());
		retrievalPlan.setItemCode(retParam.getItemCode());
		retrievalPlan.setItemName1(retParam.getItemName());
		retrievalPlan.setEnteringQty(retParam.getEnteringQty());
		retrievalPlan.setBundleEnteringQty(retParam.getBundleEnteringQty());
		retrievalPlan.setItf(retParam.getITF());
		retrievalPlan.setBundleItf(retParam.getBundleITF());
		wMerger.complete(retrievalPlan);

		// 補完結果を返却パラメータにセットする
		if (retrievalPlan != null)
		{
			retParam.setConsignorName(retrievalPlan.getConsignorName());
			retParam.setCustomerName(retrievalPlan.getCustomerName1());
			retParam.setItemName(retrievalPlan.getItemName1());
			retParam.setEnteringQty(retrievalPlan.getEnteringQty());
			retParam.setBundleEnteringQty(retrievalPlan.getBundleEnteringQty());
			retParam.setITF(retrievalPlan.getItf());
			retParam.setBundleITF(retrievalPlan.getBundleItf());
		}

		return retParam;
	}

	/**
	 * 入力データのコードがマスタデータに存在するかチェックを行います。<BR>
	 * マスタに存在しない場合は、ログ出力を行い、falseを返します。
	 * また継承元のチェックメソッドを呼び、チェック処理を行います。
	 * 
	 * @see jp.co.daifuku.wms.retrieval.schedule.RetrievalDataLoader#check
	 */
	protected boolean check(
		RetrievalSupportParameter param,
		RetrievalPlanOperator operator,
		WareNaviSystem wns,
		DataLoadCsvReader DRR,
		DataLoadStatusCsvFileWriter errlist)
		throws ScheduleException, ReadWriteException
	{
		String fileName = DRR.getFileName();

		int iRet = 0;
		MasterParameter masterParam = new MasterParameter();
		masterParam.setConsignorCode(param.getConsignorCode());
		masterParam.setConsignorName(param.getConsignorName());
		masterParam.setCustomerCode(param.getCustomerCode());
		masterParam.setCustomerName(param.getCustomerName());
		masterParam.setItemCode(param.getItemCode());
		masterParam.setItemName(param.getItemName());

		iRet = wConsignorOperator.checkConsignorMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
			if (iRet == MasterParameter.RET_NG)
			{
				// 6023463=荷主コードがマスタに登録されていません。ファイル:{0} 行:{1}
				setMessage("6023463" + wDelim + fileName + wDelim + DRR.getLineNo());
				String[] msg = { DRR.getFileName(), DRR.getLineNo()};
				writeErrorLog(DRR, errlist, "6023463", msg);
				return false;
			}
			else if (iRet == MasterParameter.RET_CONSIST_NAME_EXIST)
			{
				// 6023474=指定された荷主名称はすでに使用されているため登録できません。ファイル:{0} 行:{1}
				setMessage("6023474" + wDelim + fileName + wDelim + DRR.getLineNo());
				String[] msg = { DRR.getFileName(), DRR.getLineNo()};
				writeErrorLog(DRR, errlist, "6023474", msg);
				return false;
			}
		}
		try{
		    if(DRR.getEnable("CUSTOMER_CODE")){						
				iRet = wCustomerOperator.checkCustomerMaster(masterParam);
				if (iRet != MasterParameter.RET_OK)
				{
					if (iRet == MasterParameter.RET_NG)
					{
						// 6023465=出荷先コードがマスタに登録されていません。ファイル:{0} 行:{1}
						setMessage("6023465" + wDelim + fileName + wDelim + DRR.getLineNo());
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						writeErrorLog(DRR, errlist, "6023465", msg);
						return false;
					}
					else if (iRet == MasterParameter.RET_CONSIST_NAME_EXIST)
					{
						// 6023476=指定された出荷先名称はすでに使用されているため登録できません。ファイル:{0} 行:{1}
						setMessage("6023476" + wDelim + fileName + wDelim + DRR.getLineNo());
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						writeErrorLog(DRR, errlist, "6023476", msg);
						return false;
					}
				}
		    }
		}
		catch(CsvIllegalDataException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		iRet = wItemOperator.checkItemMaster(masterParam);
		if (iRet != MasterParameter.RET_OK)
		{
			if (iRet == MasterParameter.RET_NG)
			{
				// 6023466=商品コードがマスタに登録されていません。ファイル:{0} 行:{1}
				setMessage("6023466" + wDelim + fileName + wDelim + DRR.getLineNo());
				String[] msg = { DRR.getFileName(), DRR.getLineNo()};
				writeErrorLog(DRR, errlist, "6023466", msg);
				return false;
			}
			else if (iRet == MasterParameter.RET_CONSIST_NAME_EXIST)
			{
				// 6023477=指定された商品名称はすでに使用されているため登録できません。ファイル:{0} 行:{1}
				setMessage("6023477" + wDelim + fileName + wDelim + DRR.getLineNo());
				String[] msg = { DRR.getFileName(), DRR.getLineNo()};
				writeErrorLog(DRR, errlist, "6023477", msg);
				return false;
			}
		}

		return super.check(param, operator, wns, DRR, errlist);

	}
	
	//	Private methods ----------------------------------------------
	/**
	 * ログへの書き込みと、取り込みエラーファイル作成クラスにエラー内容の追記を行います。
	 * 
	 * @param DRR DataLoadCsvReaderオブジェクトを指定します。
	 * @param errlist DataLoadStatusCsvFileWriterオブジェクトを指定します。
	 * @param errorCode セットするエラーメッセージ番号
	 * @param msg エラーメッセージにセットするパラメータ
	 */
	private void writeErrorLog(DataLoadCsvReader DRR, DataLoadStatusCsvFileWriter errlist, String errorCode, String[] msg)
	{
		// 処理の異常をログへ記録
		RmiMsgLogClient.write(Integer.parseInt(errorCode), pName, msg);
		// 取込みエラーファイル作成クラスにエラー内容を追記する。
		errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage());

		return;
	}

}
//end of class

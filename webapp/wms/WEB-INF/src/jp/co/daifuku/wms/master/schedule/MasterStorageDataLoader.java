package jp.co.daifuku.wms.master.schedule;

// $Id: MasterStorageDataLoader.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;
import jp.co.daifuku.wms.master.merger.MergerWrapper;
import jp.co.daifuku.wms.master.merger.StoragePlanMGWrapper;
import jp.co.daifuku.wms.master.operator.ConsignorOperator;
import jp.co.daifuku.wms.master.operator.ItemOperator;
import jp.co.daifuku.wms.storage.schedule.StorageDataLoader;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

/**
 * <FONT COLOR="BLUE">
 * Designer : T.Yamashita <BR>
 * Update-Designer : C.Kaminishizono <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * 入庫データ取込み時にマスタデータの補完処理を行うためのクラスです。<BR>
 * 実際の取り込み処理（重複データチェック、DBへの書き込みなど）は
 * 継承元の親クラスで行います。<BR>
 * 本クラスでは、読み込み行へのマスタデータの補完処理、
 * 読み込み行に入力されているコードがマスタに存在するかのチェックを行います。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/11</TD><TD>C.Kaminishizono</TD><TD>ロケーション管理機能追加に伴う修正</TD></TR>
 * <TR><TD>2006/05/31</TD><TD>Y.Okamura</TD><TD>補完とチェック処理のみ継承するよう変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class MasterStorageDataLoader extends StorageDataLoader
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * クラス名
	 */
	private final String pName = "MasterStorageDataLoader";

	/**
	 * 荷主マスタオペレータクラス
	 */
	private ConsignorOperator wConsignorOperator = null;

	/**
	 * 商品マスタオペレータクラス
	 */
	private ItemOperator wItemOperator = null;

	/**
	 * 補完クラス
	 */
	protected MergerWrapper wMerger = null;

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
	 * 入庫予定データ取り込み処理を行います。<BR>
	 * 処理は継承元の処理を呼び出します。
	 * 本メソッドでは、マスタデータ補完時に使用する
	 * オペレータクラスの生成を行います。
	 * 
	 * @see jp.co.daifuku.wms.storage.schedule.StorageDataLoader#load
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		wConsignorOperator = new ConsignorOperator(conn);
		wItemOperator = new ItemOperator(conn);
		wMerger = new StoragePlanMGWrapper(conn);

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
	 * @see jp.co.daifuku.wms.storage.schedule.StorageDataLoader#setStorageSupportParameter
	 */
	protected StorageSupportParameter setStorageSupportParameter(
		DataLoadCsvReader DRR,
		SystemParameter param,
		String batno,
		String pName)
		throws ReadWriteException, CsvIllegalDataException, InvalidStatusException
	{
		StorageSupportParameter storageParam = super.setStorageSupportParameter(DRR, param, batno, pName);
		StoragePlan storagePlan = new StoragePlan();

		// 入力値から補完処理を行う
		storagePlan.setConsignorCode(storageParam.getConsignorCode());
		storagePlan.setConsignorName(storageParam.getConsignorName());
		storagePlan.setItemCode(storageParam.getItemCode());
		storagePlan.setItemName1(storageParam.getItemName());
		storagePlan.setEnteringQty(storageParam.getEnteringQty());
		storagePlan.setBundleEnteringQty(storageParam.getBundleEnteringQty());
		storagePlan.setItf(storageParam.getITF());
		storagePlan.setBundleItf(storageParam.getBundleITF());
		wMerger.complete(storagePlan);

		// 補完結果を返却パラメータにセットする
		if (storagePlan != null)
		{
			storageParam.setConsignorName(storagePlan.getConsignorName());
			storageParam.setItemName(storagePlan.getItemName1());
			storageParam.setEnteringQty(storagePlan.getEnteringQty());
			storageParam.setBundleEnteringQty(storagePlan.getBundleEnteringQty());
			storageParam.setITF(storagePlan.getItf());
			storageParam.setBundleITF(storagePlan.getBundleItf());

		}

		// ケース数・ピース数をSET
		if (storageParam.getTotalPlanQty() > 0)
		{
			// ケース数・ピース数算出
			if (storageParam.getEnteringQty() > 0)
			{
				storageParam.setPlanCaseQty(storageParam.getTotalPlanQty() / storageParam.getEnteringQty());
				storageParam.setPlanPieceQty(storageParam.getTotalPlanQty() % storageParam.getEnteringQty());
			}
			else
			{
				storageParam.setPlanCaseQty(0);
				storageParam.setPlanPieceQty(storageParam.getTotalPlanQty());
			}
		}

		// 予定数が入っている場合のみケース／ピース区分を設定
		if (storageParam.getTotalPlanQty() > 0)
		{
			// ケース・ピース区分割り出し
			storageParam.setCasePieceflg(CasePieceFlagCHECK(storageParam));
		}
		
		return storageParam;
	}

	/**
	 * 入力データのコードがマスタデータに存在するかチェックを行います。<BR>
	 * マスタに存在しない場合は、ログ出力を行い、falseを返します。
	 * また継承元のチェックメソッドを呼び、チェック処理を行います。
	 * 
	 * @see jp.co.daifuku.wms.storage.schedule.StorageDataLoader#check
	 */
	protected boolean check(
		StorageSupportParameter parameter,
		DataLoadCsvReader DRR,
		DataLoadStatusCsvFileWriter errlist)
		throws ReadWriteException
	{
		String fileName = DRR.getFileName();

		int iRet = 0;

		MasterParameter masterParam = new MasterParameter();

		masterParam.setConsignorCode(parameter.getConsignorCode());
		masterParam.setConsignorName(parameter.getConsignorName());

		masterParam.setItemCode(parameter.getItemCode());
		masterParam.setItemName(parameter.getItemName());

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

		return super.check(parameter, DRR, errlist);

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
	private void writeErrorLog(
		DataLoadCsvReader DRR,
		DataLoadStatusCsvFileWriter errlist,
		String errorCode,
		String[] msg)
	{
		// 処理の異常をログへ記録
		RmiMsgLogClient.write(Integer.parseInt(errorCode), pName, msg);
		// 取込みエラーファイル作成クラスにエラー内容を追記する。
		errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage());

		return;
	}

}
//end of class

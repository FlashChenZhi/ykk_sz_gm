package jp.co.daifuku.wms.master.schedule;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : T.Yamashita <BR>
 * Maker : T.Yamashita   <BR>
 *
 * <CODE>MasterPlanLoadDataSCH</CODE>クラスはデータ取込み処理を行うクラスです。<BR>
 * 但し、実際の取込み処理はパッケージ単位に共通のインターフェースを用いて実装された<BR>
 * クラスを使用します。<BR>
 * 取込み処理クラスのインスタンス化は動的に行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.インスタンス生成処理（<CODE>makeExternalDataLoaderInstance(Connection conn, String classname)</CODE>メソッド）<BR>
 * <BR>
 * 指定されたクラス名より<code>ExternalDataLoader</code>インターフェースを実装したクラスの<BR>
 * インスタンスを生成します。 <BR>
 * <BR>
 * 2.データ取込み処理（<CODE>startSCH(Connection conn, Parameter[] startParams)</CODE>メソッド）<BR>
 * <BR>
 * このメソッド内部で1.インスタンス生成処理のメソッドを呼び出し、実際の取込み処理はパッケージ単位に<BR>
 * 共通のインターフェースを用いて実装されたクラスを使用します。<BR>
 * <BR>
 * eWareNavi-Basicで呼び出されるデータ取込み処理クラス一覧<BR>
 * <DIR>
 * ･入荷予定取込み処理（InStockDataLoader）<BR>
 * ･入庫予定取込み処理（StorageDataLoader）<BR>
 * ･出庫予定取込み処理（RetrievalDataLoader）<BR>
 * ･仕分け予定取込み処理（SortingDataLoader）<BR>
 * ･出荷予定取込み処理（ShippingDataLoader）<BR>
 * </DIR>
 * <BR>
 * 3.画面初期表示処理（<CODE>initFind(Connection conn, Parameter searchParam)</CODE>メソッド）<BR>
 * <BR>
 * 外部データ取り込み画面のボタン初期表示処理を行います。<BR>
 * システム定義表(WARENAVI_SYSTEM)を参照し、パッケージフラグが「1:あり」になっている予定データのチェックボックス<BR>
 * のみ値をTrueにSETし、返します。<BR>
 * <BR>
 * 4.入力チェック処理（<CODE>check(Connection conn, Parameter checkParam)</CODE>メソッド）<BR>
 * <BR>
 * 作業者コード、パスワード、権限のチェックを行います。<BR>
 * 2.データ取込み処理で呼び出されます。<BR>
 * <BR>
 * その他のメソッドはeWareNavi-Basicでは使用していません<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/02</TD><TD>T.Yamashita</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:48 $
 * @author  $Author: suresh $
 */
public class MasterPlanLoadDataSCH extends jp.co.daifuku.wms.base.system.schedule.LoadDataSCH
{

	// Class fields --------------------------------------------------
	/**
	 * Load Data class (Receiving Inspection)<BR>
	 */
	public static final String INSTOCK_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterInstockDataLoader";

	/**
	 * Load Data class (Storage)<BR>
	 */
	public static final String STORAGE_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterStorageDataLoader";

	/**
	 * Load Data class (Picking)<BR>
	 */
	public static final String RETRIEVAL_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterRetrievalDataLoader";

	/**
	 * Load Data class (Sorting)<BR>
	 */
	public static final String SORTING_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterSortingDataLoader";

	/**
	 * Load Data class (Shipping Inspection)<BR>
	 */
	public static final String SHIPPING_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterShippingDataLoader";

	// Class variables -----------------------------------------------

	/**
	 * Class Name (Load Data)
	 */
	private final String wProcessName = "MasterPlanLoadDataSCH";
	
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>,<BR>
	 * execute the process for the schedule. Implement a scheduling process depending on the class implementing this interface.<BR>
	 * Return true when the schedule process completed successfully.<BR>
	 * Return false if failed to schedule due to condition error or other causes.<BR>
	 * In this case, use the <CODE>getMessage()</CODE> method to obtain the contents.
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParams Database connection object
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		boolean resultFlag = false;

		// This flag determines whether "Processing Load" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		try
		{
			// Translate the type of startParams.
			SystemParameter[] param = (SystemParameter[]) startParams;

			// execute the check process.
			if (!check(conn, param[0]))
			{
				return resultFlag;
			}
 
			// Change the Load Data flag of the WareNavi System table to 1 before executing the process for loading.
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;

			// Check the daily update in process.
			if (isDailyUpdate(conn))
			{
				return resultFlag;
			}

			// Check whether a checkbox is ticked off or not.
			if (!param[0].getSelectLoadInstockData()
				&& !param[0].getSelectLoadStorageData()
				&& !param[0].getSelectLoadRetrievalData()
				&& !param[0].getSelectLoadSortingData()
				&& !param[0].getSelectLoadShippingData())
			{
				// 6023028=Please select the target data.
				wMessage = "6023028";
				return resultFlag;
			}

			// Obtain the worker name.
			WorkerHandler workerHandler = new WorkerHandler(conn);
			WorkerSearchKey searchKey = new WorkerSearchKey();
			searchKey.setWorkerCode(param[0].getWorkerCode());
			// Check whether Worker Code is valid or not.
			searchKey.setDeleteFlag(Worker.DELETE_FLAG_OPERATION);
			Worker[] worker = (Worker[]) workerHandler.find(searchKey);
			if (worker != null && worker.length > 0)
			{
				param[0].setWorkerName(worker[0].getName());
			}

			// Generate a Load Data class.
			boolean skip_flag = false;
			boolean overwrite_flag = false;
			boolean regist_flag = false;

			// List that stores paths for classes.
			Vector classPathList = new Vector();

			// Receiving Plan
			if (isInstockPack(conn) && param[0].getSelectLoadInstockData())
			{
				classPathList.add(INSTOCK_LOAD_CLASS);
			}
			// Storage Plan
			if (isStoragePack(conn) && param[0].getSelectLoadStorageData())
			{
				classPathList.add(STORAGE_LOAD_CLASS);
			}
			// Picking Plan
			if (isRetrievalPack(conn) && param[0].getSelectLoadRetrievalData())
			{
				classPathList.add(RETRIEVAL_LOAD_CLASS);
			}
			// Sorting Plan
			if (isSortingPack(conn) && param[0].getSelectLoadSortingData())
			{
				classPathList.add(SORTING_LOAD_CLASS);
			}
			// Shipping Plan
			if (isShippingPack(conn) && param[0].getSelectLoadShippingData())
			{
				classPathList.add(SHIPPING_LOAD_CLASS);
			}

			// Copy the contents to be stored.
			String[] path = new String[classPathList.size()];
			classPathList.copyInto(path);

			WareNaviSystem wnsys = new WareNaviSystem();
			wnsys = WareNaviSystemPackageCheck(conn);

			// Start the process for loading data according to the path which stores it.
			for (int i = 0; i < path.length; i++)
			{
				wExternalDataLoader = MasterPlanLoadDataSCH.makeExternalDataLoaderInstance(path[i]);
				// Execute the process for loading.
				if (wExternalDataLoader.load(conn, wnsys, param[0]))
				{
					wMessage = wExternalDataLoader.getMessage();
					if (wExternalDataLoader.isSkipFlag())
						skip_flag = true;
					if (wExternalDataLoader.isOverWriteFlag())
						overwrite_flag = true;
					if (wExternalDataLoader.isRegistFlag())
						regist_flag = true;
					resultFlag = true;
				}
				else
				{
					wMessage = wExternalDataLoader.getMessage();
					resultFlag = false;
					break;
				}
			}

			// Set the message if all the data are loaded normally.
			if (resultFlag)
			{
				if (regist_flag == true)
				{
					if (skip_flag && overwrite_flag)
						wMessage = "6021007"; // Data was skipped and loaded. (duplicate of data was found.)
					else if (overwrite_flag)
						wMessage = "6021006"; // Data was loaded successfully. (duplicate of data was found.)
					else if (skip_flag)
						wMessage = "6021004"; // Data was skipped and the loading completed.
					else
						wMessage = "6001008"; // Loading of data completed successfully.
				}
				else
				{
					if (skip_flag)
						wMessage = "6021008"; // Data was skipped and no target data was found.
					else
						wMessage = "6003018"; // No target data was found.
				}
			}
		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			/* 
			 * データ取り込み処理の前に１になっているデータ取り込みフラグを０にする。
			 * データ取り込み処理中にどのような例外が発生してもフラグは０にすること。
			 * フラグを０にするとeWareNaviシステムからユーザーがデータにアクセスできるようになる。
			 */

			// Failing to add rolls back the transaction.
			if (!resultFlag)
			{
				doRollBack(conn, wProcessName);
			}

			// If "Processing Load" flag was updated in its own class,
			// change the "Processing Load" flag to "0: Stopping".
			if (updateLoadDataFlag)
			{
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
		return resultFlag;
	}

}
//end of class

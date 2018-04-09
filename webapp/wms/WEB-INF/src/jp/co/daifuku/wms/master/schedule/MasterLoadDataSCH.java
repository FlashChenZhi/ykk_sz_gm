//$Id: MasterLoadDataSCH.java,v 1.2 2006/11/21 04:22:47 suresh Exp $
package jp.co.daifuku.wms.master.schedule;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkerHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.Worker;
import jp.co.daifuku.wms.base.system.schedule.LoadDataSCH;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

/**
 * Designer : hota <BR>
 * Maker : hota   <BR>
 *
 * <CODE>MasterLoadDataSCH</CODE>クラスはデータ取込み処理を行うクラスです。<BR>
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
 * ･荷主マスタ取込み処理（InStockDataLoader）<BR>
 * ･仕入先マスタ取込み処理（StorageDataLoader）<BR>
 * ･出荷先マスタ取込み処理（RetrievalDataLoader）<BR>
 * ･商品マスタ予定取込み処理（SortingDataLoader）<BR>
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
 * @version $Revision: 1.2 $, $Date: 2006/11/21 04:22:47 $
 * @author  $Author: suresh $
 */
public class MasterLoadDataSCH extends LoadDataSCH
{

	// Class fields --------------------------------------------------

	/**
	 * Load Data class (Consignor master)<BR>
	 */
	public static final String CONSIGNOR_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterConsignorDataLoader";

	/**
	 * Load Data class (Supplier master)<BR>
	 */
	public static final String SUPPLIER_LOAD_CLASS =
		"jp.co.daifuku.wms.master.schedule.MasterSupplierDataLoader";

	/**
	 * Load Data class (Customer master)<BR>
	 */
	public static final String CUSTOMER_LOAD_CLASS =
	    "jp.co.daifuku.wms.master.schedule.MasterCustomerDataLoader";

	/**
	 * Load Data class (Item master)<BR>
	 */
	public static final String ITEM_LOAD_CLASS =
	    "jp.co.daifuku.wms.master.schedule.MasterItemDataLoader";
	

	// Class variables -----------------------------------------------

	/**
	 * Class Name (Load Data)
	 */
	private final String wProcessName = "LoadDataSCH";

	/**
	 * Delimiter
	 * A separator between parameters of MessageDef Message when exception occurs.<BR>
	 * 	Example String msginfo = "9000000" + wDelimta + "Palette" + wDelim + "Stock";
	 */
	public static String wDelimta = MessageResource.DELIM;

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Constructor
	 */
	public MasterLoadDataSCH()
	{
	}

	// Public methods ------------------------------------------------


	/**
	 * Check the contents of the parameter for its properness.According to the contents set for the parameter designated in <CODE>checkParam</CODE>,<BR>
	 * execute the process for checking the input in the parameter.Implement of the check process depends on the class that implements this interface.<BR>
	 * Return true if the contents of the parameter is correct.<BR>
	 * Return false if the content of the parameter has some problem,Use the <CODE>getMessage()</CODE> method to obtain the contents .
	 * @param conn Database connection object
	 * @param checkParam This parameter class includes contents to be checked for input.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{

		// Translate the data type of checkParam.
		SystemParameter param = (SystemParameter) checkParam;

		// Check the Worker Code, password, and Access Privileges.
		if (!checkWorker(conn, param, true))
		{
			return false;
		}

		// Check for loading the Plan data in progress.
		if (isLoadingData(conn))
		{
			return false;
		}

		return true;
	}

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
			if (!param[0].getSelectLoadConsignorMasterData()
				&& !param[0].getSelectLoadSupplierMasterData()
				&& !param[0].getSelectLoadCustomerMasterData()
				&& !param[0].getSelectLoadItemMasterData())
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


			// Consignor master
			if (param[0].getSelectLoadConsignorMasterData())
			{
				classPathList.add(CONSIGNOR_LOAD_CLASS);
			}
			// Supplier master
			if (param[0].getSelectLoadSupplierMasterData())
			{
				classPathList.add(SUPPLIER_LOAD_CLASS);
			}
			// Customer master
			if (param[0].getSelectLoadCustomerMasterData())
			{
				classPathList.add(CUSTOMER_LOAD_CLASS);
			}
			// Item master
			if (param[0].getSelectLoadItemMasterData())
			{
				classPathList.add(ITEM_LOAD_CLASS);
			}

			// Copy the contents to be stored.
			String[] path = new String[classPathList.size()];
			classPathList.copyInto(path);

			WareNaviSystem wnsys = new WareNaviSystem();
			wnsys = WareNaviSystemPackageCheck(conn);

			// Start the process for loading data according to the path which stores it.
			for (int i = 0; i < path.length; i++)
			{
				wExternalDataLoader = MasterLoadDataSCH.makeExternalDataLoaderInstance(path[i]);
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
					if (skip_flag)
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

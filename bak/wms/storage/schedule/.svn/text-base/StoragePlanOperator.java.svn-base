package jp.co.daifuku.wms.storage.schedule;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.coordinated.instockstorage.InstockStoragePlanCreator;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : T.Nakajima(UTC) <BR>
 * Maker : T.Nakajima(UTC) <BR>
 * <BR>
 * This class searches and updates data to generate a plan data of storage.<BR>
 * Use this class to extract, add, modify,or delete a planned data.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/25</TD><TD>T.Nakajima</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:47:41 $
 * @author  $Author: suresh $
 */
public class StoragePlanOperator extends AbstractStorageSCH
{
	// Class fields --------------------------------------------------
	/**
	 * Storage Plan info handler
	 */
	protected StoragePlanHandler wPlanHandler = null;

	/**
	 * Storage Plan info search key
	 */
	private StoragePlanSearchKey wPlanKey = null;

	/**
	 * Storage Plan infoUpdating Key
	 */
	private StoragePlanAlterKey wPlanAltKey = null;

	/**
	 * Work Status handler
	 */
	private WorkingInformationHandler wWorkHandler = null;

	/**
	 * Work Status search key
	 */
	private WorkingInformationSearchKey wWorkKey = null;

	/**
	 * Work StatusUpdating Key
	 */
	private WorkingInformationAlterKey wWorkAltKey = null;

	/**
	 * inventory information handler
	 */
	private StockHandler wStockHandler = null;

	/**
	 * inventory information search key
	 */
	private StockSearchKey wStockKey = null;

	/**
	 * Update key for inventory information
	 */
	private StockAlterKey wStockAltKey = null;

	/**
	 * Sequence handler
	 */
	protected SequenceHandler wSequenceHandler = null;

	/**
	 * Connection with database
	 */
	protected Connection wConn = null;

	// Class variables -----------------------------------------------
	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:47:41 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Use this constructor, if not searching through database or updating data using this class.
	 */
	public StoragePlanOperator()
	{
	}

	/**
	 * Use this constructor, if searching through database or updating data using this class. <BR>
	 * Generate an instance needed for searching through each DB and updating.<BR>
	 * @param conn Instance to hold database connection object
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public StoragePlanOperator(Connection conn) throws ReadWriteException, ScheduleException
	{
		// Storage Plan info
		wPlanAltKey = new StoragePlanAlterKey();
		wPlanHandler = new StoragePlanHandler(conn);
		wPlanKey = new StoragePlanSearchKey();

		// Work Status
		wWorkAltKey = new WorkingInformationAlterKey();
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();

		// inventory information
		wStockAltKey = new StockAlterKey();
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();

		// Sequence
		wSequenceHandler = new SequenceHandler(conn);

		// DB connection info maintain
		setConnection(conn);
	}


	// Public methods ------------------------------------------------
	/**
	 * This method searches Work status, Storage Plan info based on the input or extracted plan data. <BR>
	 * Require to set the parameter forming the search info in <CODE>StorageSupportParameter</CODE>. <BR>
	 * For screen with unclear Case/Piece division, clarify the division using <CODE>getCasePieceFlag</CODE> method in this class.<BR>
	 * Return null if no data corresponding to the search conditions in the DB, <BR>
	 * Return the search result info mapped in <CODE>StoragePlan</CODE>, if corresponding data exist. <BR>
	 * This method disables to check for exclusion (checkout of the last update date/time). <BR>
	 * <BR>
	 * [Parameter] <BR>
	 * * Mandatory item (count) <BR>
	 * + Mandatory item (count) depending on condition <BR>
	 * <BR>
	 * <DIR>
	 * Planned storage date* : StoragePlanDate <BR>
	 * Consignor code* : ConsignorCode <BR>
	 * Item Code* : ItemCode <BR>
	 * Packed qty per Case* : PlanCaseQty <BR>
	 * Storage Planned Qty+ : TotalPlanQty <BR>
	 * Case/Piece division* : CasePieceFlag <BR>
	 * Piece Item Storage Location : PieceLocation <BR>
	 * Case Item Storage Location : CaseLocation <BR>
	 * <BR>
	 * </DIR>
	 * *If setting no mandatory item (count) or containing forbidden characterReturn <CODE>ScheduleException</CODE>,  <BR>
	 * execute the processes in the sequence as below.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Check for presence of the same information in DnWorkInfo. Lock the same information if any and obtain it. <BR>
	 *   Return null when no same info exists. <BR>
	 *   Use the correspondence conditions as follows. <BR>
	 *     <BR>
	 *     <DIR>
	 *     -Planned storage date<BR>
	 *     -Consignor code<BR>
	 *     -Item Code<BR>
	 *     -Case/Piece division<BR>
	 *     -Piece Item Storage Location<BR>
	 *     -Case Item Storage Location<BR>
	 *     -Work division:Storage<BR>
	 *     -Work status: other than Deleted<BR>
	 *     </DIR>
	 *     <BR>
	 *   2.Check for presence of the same information in DnStoragePlan. Lock the same information if any and obtain it. <BR>
	 *   Return null when no same info exists. <BR>
	 *   Use the correspondence conditions as follows.<BR>
	 *     <BR>
	 *     <DIR>
	 *     -Planned storage date <BR>
	 *     -Consignor code <BR>
	 *     -Item Code  <BR>
	 *     -Case/Piece division <BR>
	 *     -Piece Item Storage Location <BR>
	 *     -Case Item Storage Location <BR>
	 *     -Work status: other than Deleted <BR>
	 *     -ADD division : other than Linked to Receiving <BR>
	 *     </DIR>
	 *     <BR>
	 * </DIR>
	 * <BR>
	 * @param searchParam <CODE>StorageParameter</CODE> that includes information to generate Storage Plan info.
	 * @return StoragePlan Storage Plan info
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public StoragePlan[] findStoragePlan(StorageSupportParameter searchParam) throws ReadWriteException, ScheduleException
	{
		// Obtain it if Case/Piece division is not set,
		if (StringUtil.isBlank(searchParam.getCasePieceflg()))
		{
			searchParam.setCasePieceflg(getCasePieceFlag(searchParam));
		}

		// Check for input.
		checkString(searchParam.getStoragePlanDate());
		checkString(searchParam.getConsignorCode());
		checkString(searchParam.getItemCode());
		checkString(searchParam.getCasePieceflg());

		// Set the search conditions.
		setWorkinfoSearchKey(searchParam);

		// Obtain the Instance of Work status.
		wWorkHandler.find(wWorkKey);
		// Return null if no corresponding data found.
		if (wWorkHandler.count(wWorkKey) <= 0)
		{
			return null;
		}

		// Search through the Storage Plan information.
		StoragePlan[] resultPlan = null;
		// Set the search conditions.
		setStoragePlanSearchKey(searchParam);
		// Obtain the Storage Plan information instance.
		resultPlan = (StoragePlan[]) wPlanHandler.find(wPlanKey);
		// Return null if no corresponding data found.
		if (resultPlan == null)
		{
			return null;
		}

		return resultPlan;
	}

	/**
	 * This method allows to lock the Work StatusStorage Plan info based on the entered or extracted plan data. <BR>
	 * Require to set the parameter forming the search info in <CODE>StorageSupportParameter</CODE>. <BR>
	 * For screen with unclear Case/Piece division, clarify the division using <CODE>getCasePieceFlag</CODE> method in this class.<BR>
	 * Return null if no data corresponding to the search conditions in the DB, <BR>
	 * Return the search result info mapped in <CODE>StoragePlan</CODE>, if corresponding data exist. <BR>
	 * This method disables to check for exclusion (checkout of the last update date/time). <BR>
	 * <BR>
	 * [Parameter] <BR>
	 * * Mandatory item (count) <BR>
	 * + Mandatory item (count) depending on condition <BR>
	 * <BR>
	 * <DIR>
	 * Planned storage date* : StoragePlanDate <BR>
	 * Consignor code* : ConsignorCode <BR>
	 * Item Code* : ItemCode <BR>
	 * Packed qty per Case* : PlanCaseQty <BR>
	 * Storage Planned Qty+ : TotalPlanQty <BR>
	 * Case/Piece division* : CasePieceFlag <BR>
	 * Piece Item Storage Location : PieceLocation <BR>
	 * Case Item Storage Location : CaseLocation <BR>
	 * </DIR>
	 * <BR>
	 * *Setting no Mandatory item (count) or containing forbidden character returns <CODE>ScheduleException</CODE>. <BR>
	 * Execute the processes in the sequence as below.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Check for presence of the same information in DnWorkInfo. Lock the same information if any and obtain it. <BR>
	 *   Return null when no same info exists. <BR>
	 *   Use the correspondence conditions as follows. <BR>
	 *     <BR>
	 *     <DIR>
	 *     -Planned storage date <BR>
	 *     -Consignor code <BR>
	 *     -Item Code <BR>
	 *     -Case/Piece division <BR>
	 *     -Piece Item Storage Location <BR>
	 *     -Case Item Storage Location <BR>
	 *     -Work division:Storage <BR>
	 *     -Work status: other than Deleted <BR>
	 *     </DIR>
	 *     <BR>
	 *   2.Check for presence of the same information in DnStoragePlan. Lock the same information if any and obtain it. <BR>
	 *   Return null when no same info exists. <BR>
	 *   Use the correspondence conditions as follows. <BR>
	 *     <BR>
	 *     <DIR>
	 *     -Planned storage date <BR>
	 *     -Consignor code <BR>
	 *     -Item Code  <BR>
	 *     -Case/Piece division <BR>
	 *     -Piece Item Storage Location <BR>
	 *     -Case Item Storage Location <BR>
	 *     -Work status: other than Deleted <BR>
	 *     -ADD division : other than Linked to Receiving <BR>
	 *     </DIR>
	 *     <BR>
	 * </DIR>
	 * <BR>
	 * @param lockParam <CODE>StorageParameter</CODE> that includes information to generate Storage Plan info.
	 * @return StoragePlan Storage Plan info
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public StoragePlan[] findStoragePlanForUpdate(StorageSupportParameter lockParam) throws ReadWriteException, ScheduleException
	{
		// Obtain it if Case/Piece division is not set,
		if (StringUtil.isBlank(lockParam.getCasePieceflg()))
		{
			lockParam.setCasePieceflg(getCasePieceFlag(lockParam));
		}

		// Check for input.
		checkString(lockParam.getStoragePlanDate());
		checkString(lockParam.getConsignorCode());
		checkString(lockParam.getItemCode());
		checkString(lockParam.getCasePieceflg());

		// Search for the Work status.
		WorkingInformation[] wInfo = null;
		// Set the search conditions.
		setWorkinfoSearchKey(lockParam);
		// Obtain the Instance of Work status.
		wInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);
		// Return null if no corresponding data found.
		if (wInfo == null || wInfo.length == 0)
		{
			return null;
		}

		// Search through the Storage Plan information.
		StoragePlan[] resultPlan = null;
		// Set the search conditions.
		setStoragePlanSearchKey(lockParam);
		// Obtain the Storage Plan information instance.
		resultPlan = (StoragePlan[]) wPlanHandler.findForUpdate(wPlanKey);
		// Return null if no corresponding data found.
		if (resultPlan == null)
		{
			return null;
		}

		return resultPlan;
	}

	/**
	 * Designate the Storage Plan unique key and
	 * update the status flag of its linked work status, Storage Plan info, and inventory information. <BR>
	 * However, the target of this method is information that was locked in <CODE>findStoragePlanForUpdate</CODE>. <BR>
	 * Setting no value for storagePrimaryKey and processName or containing forbidden character
	 * returns <CODE>ScheduleException</CODE>. <BR>
	 * This method executes processes in the sequence as below. <BR>
	 * <BR>
	 *   <DIR>
	 *     1.Update the work status (DnWorkInfo). <BR>
	 *      -Update the Work status linked with Storage Plan unique key. <BR>
	 *      -Status flag: Delete <BR>
	 *      -Last update process name:processName <BR>
	 *     <BR>
	 *     2.Update the Storage Plan information (DnSortingPlan). <BR>
	 *     -Update the Storage Plan information linked with Storage Plan unique key. <BR>
	 *     -Status flag: Delete <BR>
	 *     -Last update process name:processName <BR>
	 *     <BR>
	 *     3.Update the inventory information (DmStock) (Update the data for the count of the updated Work status). <BR>
	 *     -Lock the inventory information linked with obtained Stock ID via Updated Work status and update it. <BR>
	 *     -Status flag: Completed <BR>
	 *     -Last update process name:processName <BR>
	 *   </DIR>
	 * <BR>
	 *
	 * @param storagePrimaryKey Storage Plan unique key to be processed
	 * @param processName Invoking Source Process name
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void updateStoragePlan(String storagePrimaryKey, String processName) throws ReadWriteException, ScheduleException
	{
		// Check for input.
		checkString(storagePrimaryKey);
		checkString(processName);

		try
		{
			// Update the Work status.
			wWorkAltKey.KeyClear();
			wWorkAltKey.setPlanUkey(storagePrimaryKey);
			wWorkAltKey.updateStatusFlag(WorkingInformation.STATUS_FLAG_DELETE);
			wWorkAltKey.updateLastUpdatePname(processName);
			wWorkHandler.modify(wWorkAltKey);

			// Obtain the Work status that was updated to obtain the stock update key.
			wWorkKey.KeyClear();
			wWorkKey.setPlanUkey(storagePrimaryKey);
			WorkingInformation[] workInfo = (WorkingInformation[]) wWorkHandler.find(wWorkKey);
			// Announces exceptions when no target information exits.
			if (workInfo == null || workInfo.length == 0)
			{
				// 6006004=Specified column does not exist in the table. COLUMN={0} TABLE={1}COLUMN={0} TABLE={1}
				RmiMsgLogClient.write("6006004" + wDelim + storagePrimaryKey + wDelim + "DnWorkInfo", this.getClass().getName());
				throw new ReadWriteException("6006004" + wDelim + storagePrimaryKey + wDelim + "DnWorkInfo");
			}

			// Update the Storage Plan info.
			wPlanAltKey.KeyClear();
			wPlanAltKey.setStoragePlanUkey(storagePrimaryKey);
			wPlanAltKey.updateStatusFlag(StoragePlan.STATUS_FLAG_DELETE);
			wPlanAltKey.updateLastUpdatePname(processName);
			wPlanHandler.modify(wPlanAltKey);

			// Update inventory information using the Stock ID obtained via the updated Work status.
			for (int i = 0; i < workInfo.length; i++)
			{
				// Search for the corresponding inventory information and lock it.
				wStockKey.KeyClear();
				wStockKey.setStockId(workInfo[i].getStockId());
				Stock stock = (Stock) wStockHandler.findPrimaryForUpdate(wStockKey);
				if (stock == null)
				{
					// 6006004=Specified column does not exist in the table. COLUMN={0} TABLE={1}COLUMN={0} TABLE={1}
					RmiMsgLogClient.write("6006004" + wDelim + storagePrimaryKey + wDelim + "DmStock", this.getClass().getName());
					throw new ReadWriteException("6006004" + wDelim + storagePrimaryKey + wDelim + "DmStock");
				}
				// Update the stock status to Completed.
				wStockAltKey.KeyClear();
				wStockAltKey.setStockId(stock.getStockId());
				wStockAltKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
				wStockAltKey.updateLastUpdatePname(processName);
				wStockHandler.modify(wStockAltKey);
			}

		}
		catch (NotFoundException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidDefineException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (NoPrimaryException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

	}

	/**
	 * Add the storage Plan info, Work status, and inventory information via the input or extracted Plan info. <BR>
	 * Check for mandatory input in Check for mandatory input of each parameter on each screen. <BR>
	 * This method processes as below. <BR>
	 * <BR>
	 *   <DIR>
	 *     1.Add the Storage Plan information (DnStragePlan). <BR>
	 *      -Generate the Storage Plan information based on the input data.<BR>
	 *     <BR>
	 *     2.Add the Work status (DnWorkInfo). <BR>
	 *      -Generate Work status based on the added Storage Plan info.<BR>
	 *    <BR>
	 *     3.Add the inventory information (DmStock) (Add the added Work status data). <BR>
	 *      -Add up the inventory information based on the added Work status.<BR>
	 *   <BR>
	 *   </DIR>
	 * For details on setting information, enable to refer to the File Flow Chart. <BR>
	 * <BR>
	 * [Input data]
	 * <BR>
	 *   <DIR>
	 *     -Planned storage date: StoragePlanDate <BR>
	 *     -Consignor code:ConsignorCode <BR>
	 *     -Consignor Name: ConsignorName <BR>
	 *     -Item Code: ItemCode <BR>
	 *     -Item Name:ItemName <BR>
	 *     -Storage Planned Qty(Total qty):TotalPlanQty <BR>
	 *     -Packed qty per Case:EnteringQty <BR>
	 *     -Packed qty per bundle:BundleEnteringQty <BR>
	 *     -Case/Piece division:CasePieceFlag <BR>
	 *     -Case ITF:ITF <BR>
	 *     -Bundle ITF:BundleITF <BR>
	 *     -Case Item Storage Location: CaseLocation <BR>
	 *     -Piece Item Storage Location:PieceLocation <BR>
	 *     -Batch No.:BatchNo <BR>
	 *     -Worker Code:WorkerCode <BR>
	 *     -Worker Name:WorkerName <BR>
	 *     -Terminal No.:TerminalNumber <BR>
	 *     -Add dvision:RegistKbn <BR>
	 *     -Process name:RegistPName <BR>
	 *   </DIR>
	 * <BR>
	 *
	 * @param insertParam <CODE>StorageSupportParameter</CODE> that includes information to generate Storage Plan info.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public void createStoragePlan(StorageSupportParameter insertParam) throws ReadWriteException, ScheduleException
	{
		// Obtain it if Case/Piece division is not set,
		if (StringUtil.isBlank(insertParam.getCasePieceflg()))
		{
			insertParam.setCasePieceflg(getCasePieceFlag(insertParam));
		}

		// Add the Storage Plan information based on the entered data.
		StoragePlan storagePlan = null;
		storagePlan = createStorage(insertParam);
		// Add the Work Status and inventory information
		// Process to generate a Storage Plan (Shipping Storage Link)
		InstockStoragePlanCreator planCre = new InstockStoragePlanCreator(wConn);
		planCre.createStorageWorkInfo(storagePlan, insertParam.getRegistPName());

	}

	// Package methods -----------------------------------------------



	// Protected methods ---------------------------------------------
	/**
	 * Lock the Work status and inventory information to ensure exclusivity with other data.
	 * Conditions: ( Status: Standby and also Work division: Storage )
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *     <LI>Status division(Standby)</LI>
	 *     <LI>Work division(Storage)</LI>
	 *    </UL>
	 * </DIR>
	 *
	 * @throws ReadWriteException		Generate a database error.
	 */
	protected void lockWorkingInfoStockData()
		throws ReadWriteException
	{
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		wWorkHandler.lock(wWorkKey);
	}



	// Private methods -----------------------------------------------

	/**
	 * Set the Search key of Work status.
	 *
	 * @param searchParam Parameter that includes search conditions
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void setWorkinfoSearchKey(StorageSupportParameter searchParam) throws ReadWriteException
	{
		// Clear the search info.
		wWorkKey.KeyClear();
		// Planned storage date
		wWorkKey.setPlanDate(searchParam.getStoragePlanDate());
		// Consignor code
		wWorkKey.setConsignorCode(searchParam.getConsignorCode());
		// Item Code
		wWorkKey.setItemCode(searchParam.getItemCode());
		// Case
		if (searchParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			wWorkKey.setWorkFormFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			wWorkKey.setLocationNo(searchParam.getCaseLocation());
		}
		// Piece
		else if (searchParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			wWorkKey.setWorkFormFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			wWorkKey.setLocationNo(searchParam.getPieceLocation());
		}
		// None
		else if (searchParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			wWorkKey.setWorkFormFlag(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			if (!searchParam.getCaseLocation().equals(""))
				wWorkKey.setLocationNo(searchParam.getCaseLocation());
			else if (!searchParam.getPieceLocation().equals(""))
				wWorkKey.setLocationNo(searchParam.getPieceLocation());
			else
				wWorkKey.setLocationNo("");
		}
		// Mixed
		else
		{
			wWorkKey.setWorkFormFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
			wWorkKey.setLocationNo(searchParam.getCaseLocation(), "=", "", ")", "OR");
			wWorkKey.setWorkFormFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE, "=", "(", "", "AND");
			wWorkKey.setLocationNo(searchParam.getPieceLocation(), "=", "", "))", "AND");
		}
		// With status flag other than Deleted
		wWorkKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"!=");
		// Work division
		wWorkKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		// Storage Plan unique key: Set this if needed to search through other worker's information.
		if(!StringUtil.isBlank(searchParam.getStoragePlanUKey()))
		{
			wWorkKey.setPlanUkey(searchParam.getStoragePlanUKey(), "!=");
		}
	}

	/**
	 * Set the Search key of Storage Plan info.
	 *
	 * @param searchParam Parameter that includes search conditions
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void setStoragePlanSearchKey(StorageSupportParameter searchParam) throws ReadWriteException
	{
		//Clear the search info.
		wPlanKey.KeyClear();
		// Planned storage date
		wPlanKey.setPlanDate(searchParam.getStoragePlanDate());
		// Consignor code
		wPlanKey.setConsignorCode(searchParam.getConsignorCode());
		// Item Code
		wPlanKey.setItemCode(searchParam.getItemCode());
		// Case
		if (searchParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			// ((division: Mixed OR division: Case) AND Work location: Case Location )
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED, "=", "((", "", "OR");
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE, "=", "", ")", "AND");
			wPlanKey.setCaseLocation(searchParam.getCaseLocation(), "=", "", ")", "AND");
		}
		// Piece
		else if (searchParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			// ((Division:Mixed OR Division:Piece) AND Work Location:Piece Location)
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED, "=", "((", "", "OR");
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE, "=", "", ")", "AND");
			wPlanKey.setPieceLocation(searchParam.getPieceLocation(), "=", "", ")", "AND");
		}
		// None
		else if (searchParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			wPlanKey.setCaseLocation(searchParam.getCaseLocation(), "=", "(", "", "AND");
			wPlanKey.setPieceLocation(searchParam.getPieceLocation(), "=", "", ")", "AND");

		}
		// Mixed
		else
		{
			// ((Division:Case AND Work Location:Case Location) OR
			// (Division:Piece  AND Work Location:Piece Location) OR
			// (Division is Mixed, AND Work location is Case Location, AND Work location is Piece Location )
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
			wPlanKey.setCaseLocation(searchParam.getCaseLocation(), "=", "", ")", "OR");
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE, "=", "(", "", "AND");
			wPlanKey.setPieceLocation(searchParam.getPieceLocation(), "=", "", ")", "OR");
			wPlanKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED, "=", "(", "", "AND");
			wPlanKey.setCaseLocation(searchParam.getCaseLocation(), "=", "", "", "AND");
			wPlanKey.setPieceLocation(searchParam.getPieceLocation(), "=", "", "))", "AND");
		}
		// With status flag other than Deleted
		wPlanKey.setStatusFlag(StorageSupportParameter.STATUS_FLAG_DELETE,"!=");

		if (!searchParam.getRegistKbn().equals(StoragePlan.REGIST_KIND_INSTOCK))
		{
			// Data with Add division other than "2: Linked to Receiving"
			wPlanKey.setRegistKind(StoragePlan.REGIST_KIND_INSTOCK, "!=");
		}
		else
		{
			// Data with Add division "2: Linked to Receiving"
			wPlanKey.setRegistKind(StoragePlan.REGIST_KIND_INSTOCK, "=");
		}
	}

	/**
	 * Generate a Storage Plan info from the input info.
	 *
	 * @param  insertParam Input info
	 * @return The generated Storage Plan unique key
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private StoragePlan createStorage(StorageSupportParameter insertParam) throws ReadWriteException
	{
		StoragePlan resultPlan = new StoragePlan();
		try
		{
			// Storage Plan unique key
			String planUkey_seqno = wSequenceHandler.nextStoragePlanKey();
			resultPlan.setStoragePlanUkey(planUkey_seqno);
			// Status flag: Standby
			resultPlan.setStatusFlag(StorageSupportParameter.STATUS_FLAG_UNSTARTED);
			// Planned storage date
			resultPlan.setPlanDate(insertParam.getStoragePlanDate());
			// Consignor code
			resultPlan.setConsignorCode(insertParam.getConsignorCode());
			// Consignor name
			resultPlan.setConsignorName(insertParam.getConsignorName());
			// Supplier Code
			resultPlan.setSupplierCode(insertParam.getSupplierCode());
			// Supplier Name
			resultPlan.setSupplierName1(insertParam.getSupplierName1());
			// Item Code
			resultPlan.setItemCode(insertParam.getItemCode());
			// Item Name
			resultPlan.setItemName1(insertParam.getItemName());
			// Storage Planned Qty
			resultPlan.setPlanQty(insertParam.getTotalPlanQty());
			// Storage Result qty
			resultPlan.setResultQty(0);
			// Storage Shortage Qty
			resultPlan.setShortageCnt(0);
			// Packed qty per Case
			resultPlan.setEnteringQty(insertParam.getEnteringQty());
			// Packed qty per bundle
			resultPlan.setBundleEnteringQty(insertParam.getBundleEnteringQty());
			// Case/Piece division
			if (insertParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				resultPlan.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (insertParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				resultPlan.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (insertParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
			{
				resultPlan.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED);
			}
			else if (insertParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				resultPlan.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			}
			// CaseITF
			resultPlan.setItf(insertParam.getITF());
			// Bundle ITF
			resultPlan.setBundleItf(insertParam.getBundleITF());
			// Piece Item Storage Location
			resultPlan.setPieceLocation(insertParam.getPieceLocation());
			// Case Item Storage Location
			resultPlan.setCaseLocation(insertParam.getCaseLocation());
			//Batch No.(Schedule No.)
			resultPlan.setBatchNo(insertParam.getBatchNo());
			// Worker Code
			resultPlan.setWorkerCode(insertParam.getWorkerCode());
			// Worker Name
			resultPlan.setWorkerName(insertParam.getWorkerName());
			// Terminal No. RFTNo.
			resultPlan.setTerminalNo(insertParam.getTerminalNumber());
			// Add division
			resultPlan.setRegistKind(insertParam.getRegistKbn());
			// Name of Add Process
			resultPlan.setRegistPname(insertParam.getRegistPName());
			// Last update process name
			resultPlan.setLastUpdatePname(insertParam.getRegistPName());

			// Add Storage Plan Information.
			wPlanHandler.create(resultPlan);
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return resultPlan;
	}

	/**
	 * This method determines work division of Plan info to generate a Storage Plan info. <BR>
	 * Return the Case/Piece division specified for Storage Plan info. <BR>
	 * Determine by following procedures. <BR>
	 * <DIR>
	 *   <BR>
	 *     1.Determine as None, if inputting either one in the storage location.<BR>
	 *     <BR>
	 *     2.If inputting both case and piece or neither of them in the storage location, or both case and piece is input in the  is input to both are entered, determine as below. <BR>
	 *       <DIR>
	 *       -When Case Qty>0 Piece Qty<=0:Case <BR>
	 *       -When Case Qty<=0 Piece Qty>0:Piece <BR>
	 *       -When Case Qty>0 Piece Qty>0:Mixed <BR>
	 *       </DIR>
	 *   <BR>
	 * </DIR>
	 * Throw ScheduleException when both case qty and piece qty are not more than 0.<BR>
	 * <BR>
	 * <Parameter><BR>
	 * * Mandatory Input<BR>
	 * <DIR>
	 *   -Total Storage Planned Qty* <BR>
	 *   -Packed qty per Case* <BR>
	 *   -Case Item Storage Location <BR>
	 *   -Piece Item Storage Location <BR>
	 * </DIR>
	 *
	 * @param param <CODE>RetrievalSupportParameter</CODE> contains information to compute the Case/Piece division.
	 * @return String Case/Piece division
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public String getCasePieceFlag(StorageSupportParameter param) throws ScheduleException
	{
		// Calculate the Planned Case Qty using Total Storage Planned Qty, Packed qty per case.
		int planCaseQty = DisplayUtil.getCaseQty(param.getTotalPlanQty(), param.getEnteringQty());
		// Calculate the planned Piece qty using the total planned storage qty and Packed qty per Case.
		int planPieceQty = DisplayUtil.getPieceQty(param.getTotalPlanQty(), param.getEnteringQty());
		// Case Item Storage Location
		String caseLocation = param.getCaseLocation();
		// Piece Item Storage Location
		String pieceLocation = param.getPieceLocation();
		// Case/Piece division
		String casePieceFlag = null;

		// If the Packed qty per Case is 0 or smaller and the planned case qty is over 0.
		if (param.getEnteringQty() <= 0 && planCaseQty > 0)
		{
			// 6004001=Invalid Data was found.
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		// If both planned Case stand planned Piece qty are not more than 0 (zero)
		if (param.getPlanCaseQty() <= 0 && param.getPlanPieceQty() <= 0)
		{
			// 6004001=Invalid Data was found.
			RmiMsgLogClient.write("6004001", this.getClass().getName());
			throw new ScheduleException("6004001");
		}

		// When either one of Case Item Storage Location or Piece Item Storage Location is input: None
		if (caseLocation.equals("") && !pieceLocation.equals("")
			|| !caseLocation.equals("") && pieceLocation.equals(""))
		{
			casePieceFlag = StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else
		{
			// If planned Case qty is more than 0 (zero) and planned Piece qty is not more than 0 (zero): Case
			if (planCaseQty > 0 && planPieceQty <= 0)
			{
				casePieceFlag = StorageSupportParameter.CASEPIECE_FLAG_CASE;
			}
			// If planned Case qty is not more than 0 (zero) and planned Piece qty is more than 0 (zero): Piece
			if (planCaseQty <= 0 && planPieceQty > 0)
			{
				casePieceFlag = StorageSupportParameter.CASEPIECE_FLAG_PIECE;
			}
			// If both planned Case qty and planned Piece qty are more than 0 (zero): Mixed
			if (planCaseQty > 0 && planPieceQty > 0)
			{
				casePieceFlag = StorageSupportParameter.CASEPIECE_FLAG_MIXED;
			}
		}
		return casePieceFlag;
	}

	/**
	 * This method checks for mandatory input and forbidden character in the entered string.<BR>
	 * Return ScheduleException if the entered string is null or empty, or if includes forbidden character.<BR>
	 *
	 * @param str String to be checked
	 * @throws ScheduleException Return ScheduleException if the entered string is null or empty, or if includes forbidden character.
	 */
	private void checkString(String str) throws ScheduleException
	{
		if (StringUtil.isBlank(str))
		{
			// Record the log.
			// 6006023=Cannot update database. No condition to update is set. TABLE = {0}
			RmiMsgLogClient.write("6006023" + wDelim + "DnSortingPlan", this.getClass().getName());
			throw new ScheduleException("6006023" + wDelim + "DnSortingPlan");
		}
		// Throw Exception if the string includes forbidden character.
		if (str.indexOf(WmsParam.PATTERNMATCHING) != -1)
		{
			// Record the log.
			// 6003106=A prohibition character is contained in {0}.
			RmiMsgLogClient.write("6003106" + wDelim + str, this.getClass().getName());
			throw new ScheduleException("6003106" + wDelim + str);
		}
	}

	/**
	 * Set <code>Connection</code> for database connection.<BR>
	 * @param c For database connection Connection
	 */
	private void setConnection(Connection conn)
	{
		wConn = conn;
	}

} //end of class

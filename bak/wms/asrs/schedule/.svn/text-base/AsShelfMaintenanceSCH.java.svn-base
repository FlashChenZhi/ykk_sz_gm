//#CM45504
//$Id: AsShelfMaintenanceSCH.java,v 1.2 2006/10/30 00:48:55 suresh Exp $

//#CM45505
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM45506
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to do WEB ASRS stock Maintenance(Change) Processing.  <BR>
 * Receive the content input from the screen as parameter, and do Change Processing of ASRS Inventory information.<BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and <BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR>
 * <BR>
 * <DIR>
 * 	Do the edit of the list of ASRSWarehouse. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Warehouse table < warehouse >.  <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Add button is pressed(<CODE>check()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, do the mandatory check and return the check result. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input  +Any one is Mandatory Input <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No.* <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 3.Processing when Modify button is pressed(<CODE>check()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, do the mandatory check and return the check result. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input  +Any one is Mandatory Input <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No.* <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 4.Processing when Delete button is pressed(<CODE>check()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, do the mandatory check and return the check result. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] *Mandatory Input  +Any one is Mandatory Input <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No.* <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 5.Processing when Submit button is pressed.(<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the content displayed in the filtering area as parameter, and do Change Processing of the ASRS stock.<BR>
 *   Return true when processing is normally completed and return false when it does not complete the schedule because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No.* <BR>
 *     Consignor Code* <BR>
 *     Consignor Name <BR>
 *     Item Code* <BR>
 *     Item Name <BR>
 *     Case/Piece flag* <BR>
 *     Qty per case <BR>
 *     Qty per bundle <BR>
 *     Stock case qty+ <BR>
 *     Stock piece qty+ <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     Storage Flag <BR>
 *     Expiry date <BR>
 *     Storage date <BR>
 *     Storage time <BR>
 *     Terminal No. <BR>
 *   </DIR>
 *   <BR>
 *   [Registration processing] <BR>
 *   <BR>
 *   -Inventory informationChecking for duplicationProcessing- <BR>
 *    -Do Inventory information and Checking for duplication of specified Location No.<BR>
 *     Make Consignor Code, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication. <BR>
 *     The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. <BR>
 *   <BR>
 *   [Add Update process] <BR><DIR>
 *     -Do Deletion of object information when Inventory information of specified Location No stocks empty Palette.<BR>
 *     -Registration of Inventory information(DMSTOCK) <BR>
 *      Register Inventory information based on the content of parameter. <BR>
 *     <BR>
 *     -Renewal of palette table (PALETTE) <BR>
 *       -When Inventory information of specified Location No stocks empty Palette<BR>
 *        1.Set Usual palette in Empty palette status. <BR>
 *       -Inventory information of specified Location No : at an empty shelf. <BR>
 *        Register the Palette table based on the content of parameter. <BR>
 *     <BR>
 *     -Renewal of shelf table (SHELF) <BR>
 *       -Inventory information of specified Location No : at an empty shelf. <BR>
 *        Set Real shelf in Status flag. <BR>
 *     <BR>
 *     -Registration of Results transmission information table (DNHOSTSEND) <BR>
 *       Register Results transmission information on the Maintenance increase based on the content of parameter. <BR>
 *     <BR>
 *     -Renewal registration of worker Results information table (DNWORKERRESULT) <BR>
 *       Register worker Results information updating based on the content of parameter. <BR>
 *   </DIR>
 *   [Correction processing] <BR>
 *   <BR>
 *   -Inventory informationChecking for duplicationProcessing- <BR>
 *    -Do Inventory information and Checking for duplication of specified Location No.<BR>
 *     Make Consignor Code, Item Code, Case/Piece flag, and Expiry date a key and do Checking for duplication. (Excluding parameter information : With Stock ID)<BR>
 *     The key to Checking for duplication contains Expiry date when defined in WmsParam as an item which uniquely stocks it. <BR>
 *   <BR>
 *   [Modify Update process] <BR><DIR>
 *     -Registration of Results transmission information table (DNHOSTSEND) <BR>
 *       Register Results information on the Maintenance decrease based on Inventory information to be corrected. <BR>
 *     -Update of inventory information (DMSTOCK) <BR>
 *       Renew Inventory information based on the content of parameter. <BR>
 *     <BR>
 *     -Registration of Results transmission information table (DNHOSTSEND) <BR>
 *       Register Results transmission information on the Maintenance increase based on the content of parameter. <BR>
 *     <BR>
 *     -Renewal registration of worker Results information table (DNWORKERRESULT) <BR>
 *       Register worker Results information updating based on the content of parameter. <BR>
 *   </DIR>
 *   [DeletionUpdate process] <BR>
 *   <DIR>
 *     -Registration of Results transmission information table (DNHOSTSEND) <BR>
 *       Register Results information on the Maintenance decrease based on Inventory information of parameter. <BR>
 *     <BR>
 *     -Inventory information(DMSTOCK) Deletion <BR>
 *       Deletes Inventory information based on the content of parameter.  <BR>
 *     <BR>
 *     -When in object Location, there is no Inventory information <BR>
 *     <DIR>
 *       -Process Deletion of the palette table. -<BR>
 *         Do Deletion of the palette table with Palette ID of Inventory information.  <BR>
 *       <BR>
 *       -Update process of shelf table (SHELF)-<BR>
 *       <DIR>
 *         Location No of Inventory information is renewed.  <BR>
 *         1.Set an empty shelf in Location Status.  <BR>
 *       </DIR>
 *     </DIR>
 *     <BR>
 *     -Renewal registration of worker Results information table (DNWORKERRESULT) <BR>
 *       Register worker Results information updating based on the content of parameter. <BR></DIR>
 *   </DIR>
 *   <BR>
 *</FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/05</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:48:55 $
 * @author  $Author: suresh $
 */
public class AsShelfMaintenanceSCH extends AbstractAsrsControlSCH
{
	//#CM45507
	//	Class variables -----------------------------------------------

	//#CM45508
	/**
	 * Class Name(ASStockMaintenance)
	 */
	public static String PROCESSNAME = "AsShelfMaintenanceSCH";

	//#CM45509
	// Class method --------------------------------------------------
	//#CM45510
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:48:55 $");
	}
	//#CM45511
	// Constructors --------------------------------------------------
	//#CM45512
	/**
	 * Initialize this Class. 
	 */
	public AsShelfMaintenanceSCH()
	{
		wMessage = null;
	}

	//#CM45513
	/**
	 * No Processing
	 */
	public static final int M_NOPROCESS = -1;
	//#CM45514
	/**
	 * Add
	 */
	public static final int M_CREATE = 1;
	//#CM45515
	/**
	 * Modify
	 */
	public static final int M_MODIFY = 2;
	//#CM45516
	/**
	 * Delete
	 */
	public static final int M_DELETE = 3;

	//#CM45517
	// Public methods ------------------------------------------------
	//#CM45518
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return corresponding Consignor Code and Consignor Name when only one Consignor Code exists in Inventory information. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>IdmControlParameter</CODE>Class with display data acquisition condition. 
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		return null;
	}

	//#CM45519
	/** 
	 * Receive the content of the area of the accumulation input from the screen as parameter, do the mandatory check, Overflow check, Duplication Check and return the check result.   <BR>
	 * Do Mandatory check, Overflow check, Duplicate check and return the check result.  <BR>
	 * Assume true to be an exclusive error when an occurrence of the condition error and pertinent data exist, and return false when pertinent data of same Line  No. does not exist in Inventory information.   <BR>
	 * Assume an exclusive error, and return false when the condition error occurs and pertinent data exists.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param checkParam Instance of < CODE>IdmControlParameter</CODE>Class with content of input. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM45520
			// Content of input area
			AsScheduleParameter param = (AsScheduleParameter) checkParam;
			
			StockHandler wStockHandler = new StockHandler(conn);
			StockSearchKey wStockSearchKey = new StockSearchKey();
			Stock rStock = null;

			//#CM45521
			// Check on Worker code and Password
			if (!checkWorker(conn, param))
			{
				return false;
			}

			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getStockCaseQty();
			int pieceqty = param.getStockPieceQty();

			int processkey = Integer.parseInt(param.getProcessStatus());
			
			//#CM45522
			// Check it by Process Flag. 
			if (processkey == M_CREATE)
			{
				//#CM45523
				// Check Location Status. 
				if (!checkLocation(conn, processkey, param.getLocationNo()))
				{
					return false;
				}
				//#CM45524
				// Input Condition check of empty Palette
				if (!isCorrectEmptyPB(param.getConsignorCode(), param.getItemCode(), 
											param.getCasePieceFlag(), param.getUseByDate(),
											param.getStockCaseQty(), param.getStockPieceQty(),
											param.getEnteringQty(), param.getBundleEnteringQty()))
				{
					return false;
				}
				//#CM45525
				// Check the Abnormal shelf. 
				if (!checkIrregularCode(param.getConsignorCode(), param.getItemCode()))
				{
					return false;
				}
				//#CM45526
				// Input value check
				if (!stockInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
				{
					return false;
				}
				//#CM45527
				// Do Checking for duplication in the shelf stock. 
				if (!multiStockCheck(conn, param))
				{
					return false;
				}
				//#CM45528
				// Consolidate it in the shelf stock Condition check. 
				if (!mixedloadStockCheck(conn, param))
				{
					return false;
				}
			}
			else if (processkey == M_MODIFY)
			{
				//#CM45529
				// Acquire Inventory information with Stock ID again. (with lock)
				wStockSearchKey.KeyClear();
				wStockSearchKey.setStockId(param.getStockId());
				wStockSearchKey.setLastUpdateDate(param.getLastUpdateDate());
				
				try
				{
					rStock = (Stock)wStockHandler.findPrimaryForUpdate(wStockSearchKey);

					if (rStock == null)
					{
						//#CM45530
						// 6027008 = This data is not useable because it is updating it with other terminals. 
						wMessage = "6027008";
						return false;
					}
				}
				catch (NoPrimaryException ex)
				{
					//#CM45531
					// 6027008 = This data is not useable because it is updating it with other terminals. 
					wMessage = "6027008";
					return false;
				}

				//#CM45532
				// Check Location Status. 
				if (!checkLocation(conn, processkey, param.getLocationNo()))
				{
					return false;
				}
				//#CM45533
				// Input Condition check
				if (!isCorrectEmptyPB(param.getConsignorCode(), param.getItemCode(), 
									param.getCasePieceFlag(), param.getUseByDate(),
									param.getStockCaseQty(), param.getStockPieceQty(),
									param.getEnteringQty(), param.getBundleEnteringQty()))
				{
					return false;
				}
				//#CM45534
				// Input value check
				if (!stockInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
				{
					return false;
				}				
				//#CM45535
				// Do Checking for duplication in the shelf stock. 
				if (!multiStockCheck(conn, param))
				{
					return false;
				}
			}
			else
			{
				//#CM45536
				// Acquire Inventory information with Stock ID again. (with lock)
				wStockSearchKey.KeyClear();
				wStockSearchKey.setStockId(param.getStockId());
				wStockSearchKey.setLastUpdateDate(param.getLastUpdateDate());
				
				try
				{
					rStock = (Stock)wStockHandler.findPrimaryForUpdate(wStockSearchKey);

					if (rStock == null)
					{
						//#CM45537
						// 6027008 = This data is not useable because it is updating it with other terminals. 
						wMessage = "6027008";
						return false;
					}
				}
				catch (NoPrimaryException ex)
				{
					//#CM45538
					// 6027008 = This data is not useable because it is updating it with other terminals. 
					wMessage = "6027008";
					return false;
				}

				//#CM45539
				// Check Location Status. 
				if (!checkLocation(conn, processkey, param.getLocationNo()))
				{
					return false;
				}
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (Exception e)
		{
			throw new ScheduleException(e.getMessage());
		}

		return true;
	}

	//#CM45540
	/**
	 * Receive the content input from the screen as parameter, and begin the No plan storage setting schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Return true when the schedule ends normally and return false when failing.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>IdmControlParameter</CODE>Class with set content. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{

		try
		{
			//#CM45541
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}

			//#CM45542
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			AsScheduleParameter param = (AsScheduleParameter) startParams[0];
			AsScheduleParameter mdparam = new AsScheduleParameter();

			//#CM45543
			// Worker code
			String workercode = param.getWorkerCode();
			//#CM45544
			// Worker Name
			String workerName = getWorkerName(conn, workercode);

			//#CM45545
			// Work date(System definition date)
			String sysdate = getWorkDate(conn);

			//#CM45546
			// Terminal No.
			String terminalno = param.getTerminalNumber();
			//#CM45547
			// Work Flag(Maintenance)
			String jobtype = "";
			String iojobtype = "";
			//#CM45548
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM45549
			// Batch No.(One setting commonness)
			String batch_seqno = sequence.nextNoPlanBatchNo();
			//#CM45550
			// Set the date now in Storage date & time. (one setting and commonness)
			Date instockdate = param.getInStockDate();
			//#CM45551
			// Inventory information
			Stock resultStock = new Stock();

			try
			{
				//#CM45552
				// Lock processing (Shelf-Palette Information)
				lockStockData(conn, param.getLocationNo());
			}
			catch(LockTimeOutException e)
			{
				//#CM45553
				// 6027008 = This data is not useable because it is updating it with other terminals. 
				wMessage = "6027008";
				return false;
			}
			
			int processkey = Integer.parseInt(param.getProcessStatus());
			int inputqty = 0;
			int pltkind = 0;

			//#CM45554
			// Acquire Item Code of empty PB.
			String wEmpItemCode = AsrsParam.EMPTYPB_ITEMKEY;
			//#CM45555
			// Palette Status 
			if (wEmpItemCode.equals(param.getItemCode()) && param.getStockQty() == 1)
			{
				pltkind = Palette.STATUS_EMPTY;
			}
			else
			{
				pltkind = Palette.NORMAL;
			}

			if (wEmpItemCode.equals(param.getItemCode()))
			{
				param.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
			
			if (processkey == M_CREATE)
			{
				//#CM45556
				// Registration processing
				//#CM45557
				// Check the number of consolidation. 
				StockHandler stkh = new StockHandler(conn);
				StockSearchKey stkKey = new StockSearchKey();
				stkKey.setLocationNo(param.getLocationNo());
				stkKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
				//#CM45558
				// When the number of maximum consolidation is exceeded, it is assumed that it makes an error. 
				if (stkh.count(stkKey) >= getMaxMixedPalette(conn, param.getWareHouseNo()))
				{
					//#CM45559
					// 6023488=Because the number of consolidation exceeds the number of maximum consolidation, it is not possible to set it. 
					wMessage = "6023488";
					return false;
				}
				
				//#CM45560
				// Input Stock qty 
				inputqty = param.getStockCaseQty() * param.getEnteringQty() + param.getStockPieceQty();
				//#CM45561
				// Register Inventory information. 
				resultStock = registStockData(conn, param, inputqty, instockdate, -1);

				jobtype = Stock.JOB_TYPE_MAINTENANCE_PLUS;
				iojobtype = InOutResult.WORKTYPE_AS_MAINTENANCE_PLUS;
				
				//#CM45562
				// Registration of insertion PickingResults information table (INOUTRESULT)
				if (!createInOutResult(conn,
					param,
					iojobtype,
					pltkind,
					resultStock.getPaletteid(),
					resultStock.getStockQty(),
					InOutResult.MAINTENANCE_PLUS))
				{
					return false;
				}
				
				if (!param.getItemCode().equals(wEmpItemCode))
				{
					//#CM45563
					// Registration of Results transmission information table (DNHOSTSEND)
					if (!createHostsend(conn,
						param,
						resultStock.getStockId(),
						workercode,
						workerName,
						sysdate,
						terminalno,
						jobtype,
						PROCESSNAME,
						batch_seqno,
						inputqty))
					{
						return false;
					}
				}
							
				//#CM45564
				// Registration of worker Results information table (DNWORKERRESULT)
				updateWorkerResult(conn,workercode,workerName,sysdate,terminalno,jobtype,inputqty);

				//#CM45565
				// Registered. 
				wMessage = "6001003";
				
			}
			else if (processkey == M_MODIFY)
			{
				//#CM45566
				// Correction processing
				//#CM45567
				// Delete Inventory information.
				resultStock = deleteStockData(conn, param.getStockId());

				//#CM45568
				// Stock qty 
				inputqty = resultStock.getStockQty();
				jobtype = Stock.JOB_TYPE_MAINTENANCE_MINUS;
				iojobtype = InOutResult.WORKTYPE_AS_MAINTENANCE_MINUS;

				mdparam.setWareHouseNo(resultStock.getAreaNo());
				mdparam.setAreaNo(resultStock.getAreaNo());
				mdparam.setLocationNo(resultStock.getLocationNo());
				mdparam.setConsignorCode(resultStock.getConsignorCode());
				mdparam.setConsignorName(resultStock.getConsignorName());
				mdparam.setItemCode(resultStock.getItemCode());
				mdparam.setItemName(resultStock.getItemName1());
				mdparam.setEnteringQty(resultStock.getEnteringQty());
				mdparam.setBundleEnteringQty(resultStock.getBundleEnteringQty());
				mdparam.setCasePieceFlag(resultStock.getCasePieceFlag());
				mdparam.setITF(resultStock.getItf());
				mdparam.setBundleITF(resultStock.getBundleItf());
				mdparam.setUseByDate(resultStock.getUseByDate());				
				mdparam.setStoringStatus(Integer.toString(resultStock.getRestoring()));	
							
				//#CM45569
				// Registration of insertion PickingResults information table (INOUTRESULT)
				if (!createInOutResult(conn,
					mdparam,
					iojobtype,
					pltkind,
					resultStock.getPaletteid(),
					resultStock.getStockQty() * -1,
					InOutResult.MAINTENANCE_MINUS))
				{
					return false;
				}
				
				if (!mdparam.getItemCode().equals(wEmpItemCode))
				{
					//#CM45570
					// Registration of Results transmission information table (DNHOSTSEND)
					if (!createHostsend(conn,
						mdparam,
						param.getStockId(),
						workercode,
						workerName,
						sysdate,
						terminalno,
						jobtype,
						PROCESSNAME,
						batch_seqno,
						inputqty))
					{
						return false;
					}
				}
							
				//#CM45571
				// Registration of worker Results information table (DNWORKERRESULT)
				updateWorkerResult(conn,workercode,workerName,sysdate,terminalno,jobtype,inputqty);
				//#CM45572
				// Stock qty 
				inputqty = param.getStockCaseQty() * param.getEnteringQty()+ param.getStockPieceQty();
				//#CM45573
				// Register Inventory information. 
				resultStock = registStockData(conn, param, inputqty, instockdate, resultStock.getPaletteid());

				//#CM45574
				// Stock qty 
				inputqty = param.getStockCaseQty() * param.getEnteringQty()+ param.getStockPieceQty();
				jobtype = Stock.JOB_TYPE_MAINTENANCE_PLUS;
				iojobtype = InOutResult.WORKTYPE_AS_MAINTENANCE_PLUS;
				
				//#CM45575
				// Registration of insertion PickingResults information table (INOUTRESULT)
				if (!createInOutResult(conn,
					param,
					iojobtype,
					pltkind,
					resultStock.getPaletteid(),
					inputqty,
					InOutResult.MAINTENANCE_PLUS))
				{
					return false;
				}
				
				if (!param.getItemCode().equals(wEmpItemCode))
				{
					//#CM45576
					// Registration of Results transmission information table (DNHOSTSEND)
					if (!createHostsend(conn,
						param,
						resultStock.getStockId(),
						workercode,
						workerName,
						sysdate,
						terminalno,
						jobtype,
						PROCESSNAME,
						batch_seqno,
						inputqty))
					{
						return false;
					}
				}
							
				//#CM45577
				// Registration of worker Results information table (DNWORKERRESULT)
				updateWorkerResult(conn,workercode,workerName,sysdate,terminalno,jobtype,inputqty);

				//#CM45578
				// Corrected.
				wMessage = "6001004";
			}
			else
			{
				//#CM45579
				// Delete process
				//#CM45580
				// Delete Inventory information.
				resultStock = deleteStockData(conn, param.getStockId());

				jobtype = Stock.JOB_TYPE_MAINTENANCE_MINUS;
				iojobtype = InOutResult.WORKTYPE_AS_MAINTENANCE_MINUS;
				
				//#CM45581
				// Registration of insertion PickingResults information table (INOUTRESULT)
				if (!createInOutResult(conn,
					param,
					iojobtype,
					pltkind,
					resultStock.getPaletteid(),
					resultStock.getStockQty() * -1,
					InOutResult.MAINTENANCE_MINUS))
				{
					return false;
				}
				
				if (!param.getItemCode().equals(wEmpItemCode))
				{
					//#CM45582
					// Registration of Results transmission information table (DNHOSTSEND)
					if (!createHostsend(conn,
						param,
						param.getStockId(),
						workercode,
						workerName,
						sysdate,
						terminalno,
						jobtype,
						PROCESSNAME,
						batch_seqno,
						resultStock.getStockQty()))
					{
						return false;
					}
				}
							
				//#CM45583
				// Registration of worker Results information table (DNWORKERRESULT)
				updateWorkerResult(conn,workercode,workerName,sysdate,terminalno,jobtype,resultStock.getStockQty());

				//#CM45584
				// Deleted.
				wMessage = "6001005";
			}
				
			return true;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM45585
	/**
	 * Whether Status of the shelf be able to be checked and maintain it are decided. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param processType Process Flag
	 * @param currstnumber Location No..
	 * @return Return True when it is possible to maintain it. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean checkLocation(Connection conn, int processType, String currstnumber) throws Exception
	{
		ASFindUtil findutil = new ASFindUtil(conn);

		int status = findutil.isNormalShelf(currstnumber);
		switch(status)
		{
			case 0:
				//#CM45586
				//<en> There is no stock in specified location.</en>
				wMessage = "6013134";	
				return false;
			case 2:
				//#CM45587
				//<en> Unacceptable if process type is modification or deletion</en>
				if (processType == AsShelfMaintenanceSCH.M_MODIFY || processType == AsShelfMaintenanceSCH.M_DELETE)
				{
					//#CM45588
					//<en> The location is reserved. Unable to make corrections.</en>
					wMessage = "6013158";	
					return false;
				}
				break;
			case -1:
				//#CM45589
				//<en> Please enter the existing location no.</en>
				wMessage = "6013090";	
				return false;
			case -2:
				//#CM45590
				//<en> The location is inaccessible. Unable to make corrections.</en>
				wMessage = "6013160";	
				return false;
			case -3:
				//#CM45591
				//<en> The location is restricted. Unable to make corrections.</en>
				wMessage = "6013159";	
				return false;
		}

		//#CM45592
		//<en> If the location is occupied, check the allcation status and erroe location.</en>
		if (status == 1)
		{
			int pltstatus = findutil.getPaletteStatus(currstnumber);
			switch (pltstatus)
			{
				//#CM45593
				//<en> occupied location</en>
				case Palette.REGULAR:
					break;
				//#CM45594
				//<en> reserved for storage</en>
				case Palette.STORAGE_PLAN:
				//#CM45595
				//<en> reserved for retrieval</en>
				case Palette.RETRIEVAL_PLAN:
				//#CM45596
				//<en> being retrieved</en>
				case Palette.RETRIEVAL:
					//#CM45597
					//<en> Specified location is allocated at moment.</en>
					wMessage = "6013135";	
					return false;
				//#CM45598
				//<en> error</en>
				case Palette.IRREGULAR:
					//#CM45599
					//<en> Unable to make correction of error locations if rpocess type is </en>
					//<en> either registeration or modification.</en>
					if (processType == AsShelfMaintenanceSCH.M_CREATE || processType == AsShelfMaintenanceSCH.M_MODIFY)
					{
						//#CM45600
						//<en> Error location is specified. Unable to make corrections.</en>
						wMessage = "6013157";	
						return false;
					}
					break;
			}
		}
		
		return true;
	}

	//#CM45601
	/**
	 * Check the presence of the same commodity with location Inventory information. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param rParam < CODE>Parameter</CODE > object including Search condition
	 * @return Return True when the same commodity does not exist and False when existing..
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean multiStockCheck(Connection conn, Parameter rParam) throws Exception
	{
		//#CM45602
		// Content of input area
		AsScheduleParameter param = (AsScheduleParameter) rParam;
			
		StockHandler wStockHandler = new StockHandler(conn);
		StockSearchKey wStockSearchKey = new StockSearchKey();

		//#CM45603
		// Do the stock retrieval with Location No. 
		wStockSearchKey.KeyClear();
		wStockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		wStockSearchKey.setLocationNo(param.getLocationNo());
		if (!StringUtil.isBlank(param.getStockId()))
		{
			wStockSearchKey.setStockId(param.getStockId(), "!=");		
		}
		
		Stock[] stk = (Stock[])wStockHandler.find(wStockSearchKey);

		for (int i = 0; i < stk.length; i++)
		{		
			//#CM45604
			// Expiry date management Available
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				if (stk[i].getConsignorCode().equals(param.getConsignorCode()) &&
					stk[i].getItemCode().equals(param.getItemCode()) &&
					stk[i].getCasePieceFlag().equals(param.getCasePieceFlag()) &&
					stk[i].getUseByDate().equals(param.getUseByDate()))
				{
					//#CM45605
					// 6023090 = Same data already exists. It is not possible to input it. 
					wMessage = "6023090";
					return false;
				}
			}
			else
			{
				if (stk[i].getConsignorCode().equals(param.getConsignorCode()) &&
					stk[i].getItemCode().equals(param.getItemCode()) &&
					stk[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
				{
					//#CM45606
					// 6023090 = Same data already exists. It is not possible to input it. 
					wMessage = "6023090";
					return false;
				}
			}
		}

		return true;
	}

	//#CM45607
	/**
	 * Check the consolidation condition with location Inventory information. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param rParam < CODE>Parameter</CODE > object including Search condition
	 * @return Return True when it is possible to exist together and False when it is not possible to exist together. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private boolean mixedloadStockCheck(Connection conn, Parameter rParam) throws Exception
	{
		//#CM45608
		// Content of input area
		AsScheduleParameter param = (AsScheduleParameter) rParam;

		//#CM45609
		// Acquire Item Code of empty PB.
		String empItemCode = AsrsParam.EMPTYPB_ITEMKEY;

		boolean emptyFlag = false;
		if (param.getItemCode().equals(empItemCode))
		{
			emptyFlag = true;			
		}
		
		StockHandler wStockHandler = new StockHandler(conn);
		StockSearchKey wStockSearchKey = new StockSearchKey();

		//#CM45610
		// Do the stock retrieval with Location No. 
		wStockSearchKey.KeyClear();
		wStockSearchKey.setLocationNo(param.getLocationNo());
		
		Stock[] stk = (Stock[])wStockHandler.find(wStockSearchKey);

		for (int i = 0; i < stk.length; i++)
		{
			if (stk[i].getItemCode().equals(empItemCode))
			{
				//#CM45611
				// Empty PB inventory
				//#CM45612
				// When empty PBStock qty is one or more, consolidation to the empty Palette steps product stock is assumed to be improper. 
				if (stk[i].getStockQty() > 1)
				{
					//#CM45613
					// 6013262 = It is not possible to consolidate it to the empty Palette steps volume stock. 
					wMessage = "6013262";
					return false;
				}
				//#CM45614
				// The consolidation of empty PB is assumed to be improper. 
				if (emptyFlag)
				{
					//#CM45615
					// 6013263 = The empty Palette stock. Change the amount with Correction processing. 
					wMessage = "6013263";
					return false;
				}
			}
			else
			{
				//#CM45616
				// When the real inventory exists, the consolidation of Empty PB inventory is assumed to be improper. 
				if (emptyFlag)
				{
					//#CM45617
					// 6013092 = A working stock and empty Palette cannot be consolidated. 
					wMessage = "6013092";
					return false;
				}
			}
		}

		return true;
	}

	//#CM45618
	/** 
	 * Do Input check for empty Palette for the stock. <BR>
	 * Overrides isCorrectEmptyPBMethod of abstraction Class. <BR>
	 * Return true when both Consignor Code and Item Code are not for empty Palette. <BR>
	 * The condition judged to be input NG is as follows. <BR>
	 * <BR>
	 * 1.When Consignor Code is neither Consignor for empty Palette Item nor a commodity for empty Palette.<BR>
	 * 2.When Item Code is neither Consignor for empty Palette Item nor a commodity for empty Palette.<BR>
	 * 3.When it is specified for Case/Piece flag excluding Unspecified<BR>
	 * 4.The input of Storage Case qty (Or, Stock case qty) is improper. <BR>
	 * 5.The input of StorageQty per case (Or, entering stock Piece qty) is improper. <BR>
	 * 6.The input of StorageQty per bundle (Or, stock Qty per bundle) is improper. <BR>
	 * 7.The input of Expiry date is improper.<BR>
	 * <BR>
	 * @param  pConsignorCode Consignor Code<BR>
	 * @param  pItemCode Item code<BR>
	 * @param  pCasePiece Case/Piece flag<BR>
	 * @param  pUseBydate Expiry date<BR>
	 * @param  pCaseqty Case qty<BR>
	 * @param  pPieceqty Piece qty<BR>
	 * @param  pCaseEntQty Qty per case<BR>
	 * @param  pBundleEntQty Qty per bundle<BR>
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 */
	protected boolean isCorrectEmptyPB(
		String pConsignorCode,
		String pItemCode,
		String pCasePiece,
		String pUseBydate,
		int pCaseqty,
		int pPieceqty,
		int pCaseEntQty,
		int pBundleEntQty)
	{
		String emppbConsignCode = WmsParam.EMPTYPB_CONSIGNORCODE;
		String emppbItemCode = AsrsParam.EMPTYPB_ITEMKEY;

		if (!pConsignorCode.equals(emppbConsignCode) && !pItemCode.equals(emppbItemCode))
		{
			return true;
		}
		
		if (pConsignorCode.equals(emppbConsignCode) && !pItemCode.equals(emppbItemCode))
		{
			//#CM45619
			// 6023431=Specify "{0}" Item Code for an empty palette. 
			wMessage = "6023431" + wDelim + emppbItemCode;
			return false;
		}
		else if (!pConsignorCode.equals(emppbConsignCode) && pItemCode.equals(emppbItemCode))
		{
			//#CM45620
			// 6023432=Specify "{0}" Consignor Code for an empty palette. 
			wMessage = "6023432" + wDelim + emppbConsignCode;
			return false;
		}
		else
		{
			if (!pCasePiece.equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM45621
				// 6023430=Specify "There is no specification" for Case/Piece flag for an empty palette. 
				wMessage = "6023430";
				return false;
			}

			if (pCaseEntQty > 0)
			{
				//#CM45622
				// 6023483=Qty per case cannot be specified for Stock qty of empty Palette. 
				wMessage = "6023483";
				return false;
			}
		
			if (pCaseqty > 0)
			{
				//#CM45623
				// 6023428=Case qty cannot be specified for Stock qty of empty Palette. Input Piece qty. 
				wMessage = "6023428";
				return false;
			}

			if (pBundleEntQty > 0)
			{
				//#CM45624
				// 6023484=Qty per bundle cannot be specified for Stock qty of empty Palette. 
				wMessage = "6023484";
				return false;
			}

			if (pPieceqty == 0)
			{
				//#CM45625
				// 6023283=Input the value of one or more to Stock piece qty. 
				wMessage = "6023283";
				return false;
			}

			if (!StringUtil.isBlank(pUseBydate))
			{
				//#CM45626
				// 6023429=Expiry date cannot be input for an empty palette. 
				wMessage = "6023429";
				return false;
			}
		}

		return true;
	}

	//#CM45627
	/**
	 * Lock stock-location-Palette information to be updated. 
	 * Lock corresponding stock-location-Palette information from Shelf  No. specified by the argument. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Location No..</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn         Instance to maintain connection with data base. 
	 * @param p_locationno Station No. 
	 * @throws NoPrimaryException	 Notifies when definition information is abnormal. 
	 * @throws ReadWriteException   Notifies when abnormality occurs by the connection with the data base.
	 * @throws LockTimeOutException Notifies when the lock is not opened even if specified time passes. 
	 */
	protected void lockStockData(Connection conn, String p_locationno)
		throws NoPrimaryException, ReadWriteException, LockTimeOutException
	{
		ShelfHandler wSlHandler = new ShelfHandler(conn);
		ShelfSearchKey wSlSearchKey = new ShelfSearchKey();
		
		PaletteHandler wPlHandler = new PaletteHandler(conn);
		PaletteSearchKey wPlSearchKey = new PaletteSearchKey();

		//#CM45628
		// Location information search condition
		wSlSearchKey.KeyClear();
		wSlSearchKey.setStationNumber(p_locationno);
		wSlHandler.lockNowait(wSlSearchKey);
		
		//#CM45629
		// Palette information search condition
		wPlSearchKey.KeyClear();
		wPlSearchKey.setCurrentStationNumber(p_locationno);
		wPlHandler.lockNowait(wPlSearchKey);
		
	}

	//#CM45630
	/**
	 * Do Registration processing of stock-location-Palette information. 
	 * Register corresponding stock-location-Palette information from parameter specified by the argument. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Connection(Connection)</LI>
	 *     <LI>Parameter(AsScheduleParameter)</LI>
	 *     <LI>StockQty(Stock qty)</LI>
	 *     <LI>storingdate(Storage date & time)</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn          Instance to maintain connection with data base. 
	 * @param rParam        Instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @param	p_StockQty	 Stock qty
	 * @param	p_stoingdate Storage date & time
	 * @param	p_PltId		 Palette ID
	 * @return Inventory information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	protected Stock registStockData(Connection conn, Parameter rParam, int p_StockQty, Date p_stoingdate, int p_PltId)
		throws ReadWriteException, ScheduleException
	{
		StockHandler wStHandler = new StockHandler(conn);
		StockSearchKey wStSearchKey = new StockSearchKey();
			
		ShelfHandler wSlHandler = new ShelfHandler(conn);
		ShelfAlterKey wSlAlterKey = new ShelfAlterKey();
			
		PaletteHandler wPlHandler = new PaletteHandler(conn);
			
		AsScheduleParameter param = (AsScheduleParameter)rParam;
		AsScheduleParameter mdparam = new AsScheduleParameter();
		SequenceHandler sequence = new SequenceHandler(conn);
		
		//#CM45631
		// Acquire Item Code of empty PB.
		String wEmpItemCode = AsrsParam.EMPTYPB_ITEMKEY;
			
		String newStockId = "";

		//#CM45632
		// Area for registration of Inventory information
		Stock regStock = new Stock();

		try
		{
			if (!StringUtil.isBlank(param.getStockId()))
			{
				newStockId = param.getStockId();
			}
			else
			{
				newStockId = sequence.nextStockId();
			}
			
			//#CM45633
			// Acquire Inventory information in Shelf  No.. (Acquire consolidation or empty PB or empty shelf information.)
			wStSearchKey.KeyClear();
			wStSearchKey.setLocationNo(param.getLocationNo());
			wStSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			
			Stock[] readStock = (Stock[])wStHandler.find(wStSearchKey);
			
			boolean isNeedCreatePalette = false;
			int newPaletteId = 0;
			//#CM45634
			// Make Palette information because an empty shelf when there is no stock in the shelf registered now. 
			if (readStock == null || readStock.length <= 0)
			{
				isNeedCreatePalette = true;
				if (p_PltId == -1)
				{
					newPaletteId = sequence.nextPaletteId();
				}
				else
				{
					newPaletteId = p_PltId;
				}
			}
			//#CM45635
			// Deletes Empty PB inventory for empty PB. 
			else
			{
				//#CM45636
				// Delete Empty PB inventory when Empty PB inventory and Stock qty is 1. 
				if (readStock[0].getStockQty() == 1 && readStock[0].getItemCode().equals(wEmpItemCode))
				{
					//#CM45637
					// Delete Inventory information (Delete Stock and Palette)
					deleteStockData(conn, readStock[0].getStockId());
					
					mdparam.setAreaNo(readStock[0].getAreaNo());
					mdparam.setLocationNo(readStock[0].getLocationNo());
					mdparam.setConsignorCode(readStock[0].getConsignorCode());
					mdparam.setConsignorName(readStock[0].getConsignorName());
					mdparam.setItemCode(readStock[0].getItemCode());
					mdparam.setItemName(readStock[0].getItemName1());
					mdparam.setEnteringQty(readStock[0].getEnteringQty());
					mdparam.setBundleEnteringQty(readStock[0].getBundleEnteringQty());
					mdparam.setCasePieceFlag(readStock[0].getCasePieceFlag());
					mdparam.setITF(readStock[0].getItf());
					mdparam.setBundleITF(readStock[0].getBundleItf());
					mdparam.setUseByDate(readStock[0].getUseByDate());				
					mdparam.setStoringStatus(Integer.toString(readStock[0].getRestoring()));	

					//#CM45638
					// Registration of insertion PickingResults information table (INOUTRESULT)
					if (!createInOutResult(conn,
						mdparam,
						InOutResult.WORKTYPE_AS_MAINTENANCE_MINUS,
						Palette.STATUS_EMPTY,
						readStock[0].getPaletteid(),
						readStock[0].getStockQty() * -1,
						InOutResult.MAINTENANCE_MINUS))
					{
						throw new ScheduleException();
					}
					isNeedCreatePalette = true;
				}
				newPaletteId = readStock[0].getPaletteid();
			}
			
			//#CM45639
			// Register Palette information when Location Status is an empty shelf. 
			if (isNeedCreatePalette)
			{
				Palette regPalette = new Palette();
				//#CM45640
				// Palette ID
				regPalette.setPaletteId(newPaletteId);
				//#CM45641
				// Present Station No
				regPalette.setCurrentStationNumber(param.getLocationNo());
				//#CM45642
				// Warehouse Station No
				regPalette.setWHStationNumber(param.getWareHouseNo());
				//#CM45643
				// Palette typeID
				regPalette.setPaletteTypeId(Palette.PALETTE_TYPE_ID);
				//#CM45644
				// Stock Status
				regPalette.setStatus(Palette.REGULAR);
				//#CM45645
				// Drawing qty
				regPalette.setAllocation(Palette.NOT_ALLOCATED);
				//#CM45646
				// Palette Status 
				if (wEmpItemCode.equals(param.getItemCode()) && p_StockQty == 1)
				{
					regPalette.setEmpty(Palette.STATUS_EMPTY);
				}
				else
				{
					regPalette.setEmpty(Palette.NORMAL);
				}
				//#CM45647
				// Updated date & time
				regPalette.setRefixDate(p_stoingdate);
				//#CM45648
				// Filling rate
				regPalette.setRate(0);
				//#CM45649
				// Total height of Palette
				regPalette.setHeight(0);
				//#CM45650
				// Bar code information
				regPalette.setBcData("");
				
				wPlHandler.create(regPalette);
				
			}
				
			//#CM45651
			// Do Update process of Location information. 
			wSlAlterKey.KeyClear();
			wSlAlterKey.setStationNumber(param.getLocationNo());
			wSlAlterKey.updatePresence(Shelf.PRESENCE_STORAGED);
			try
			{
				wSlHandler.modify(wSlAlterKey);
			}
			catch (NotFoundException e)
			{
				//#CM45652
				// 6003006 = This data is not useable because it was updated in other terminals. 
				wMessage = "6003006";
				throw new ScheduleException(e.getMessage());
			}
			catch (InvalidDefineException e)
			{
				throw new ScheduleException(e.getMessage());
			}

			//#CM45653
			// Make Inventory information
			regStock.setStockId(newStockId);
			regStock.setPlanUkey("");
			regStock.setAreaNo(param.getWareHouseNo());
			regStock.setLocationNo(param.getLocationNo());
			regStock.setItemCode(param.getItemCode());
			regStock.setItemName1(param.getItemName());
			regStock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			regStock.setStockQty(p_StockQty); 
			regStock.setAllocationQty(p_StockQty);
			regStock.setPlanQty(0);
			regStock.setCasePieceFlag(param.getCasePieceFlag());
			regStock.setInstockDate(p_stoingdate);
			regStock.setLastShippingDate("");
			regStock.setUseByDate(param.getUseByDate());
			regStock.setLotNo("");
			regStock.setPlanInformation("");
			regStock.setConsignorCode(param.getConsignorCode());
			regStock.setConsignorName(param.getConsignorName());
			regStock.setSupplierCode("");
			regStock.setSupplierName1("");
			regStock.setEnteringQty(param.getEnteringQty());
			regStock.setBundleEnteringQty(param.getBundleEnteringQty());
			regStock.setItf(param.getITF());
			regStock.setBundleItf(param.getBundleITF());
			regStock.setRegistPname(PROCESSNAME);
			regStock.setLastUpdatePname(PROCESSNAME);
			regStock.setPaletteid(newPaletteId);
			regStock.setRestoring(Integer.parseInt(param.getStoringStatus()));
				
			wStHandler.create(regStock);
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return (regStock);
	}

	//#CM45654
	/**
	 * Do Delete process of stock-location-Palette information. 
	 * Deletes corresponding stock-location-Palette information from parameter specified by the argument. 
	 * <DIR>
	 *    (Search condition)
	 *    <UL>
	 *     <LI>Connection(Connection)</LI>
	 *     <LI>Parameter(AsScheduleParameter)</LI>
	 *     <LI>StockQty(Stock qty)</LI>
	 *     <LI>storingdate(Storage date & time)</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param conn Connection object with database
	 * @param p_stockid Stock ID
	 * @return	Inventory information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	protected Stock deleteStockData(Connection conn, String p_stockid)
		throws ReadWriteException, ScheduleException
	{
		StockHandler wStHandler = new StockHandler(conn);
		StockSearchKey wStSearchKey = new StockSearchKey();
		Stock readStock = new Stock();
		
		ShelfHandler wSlHandler = new ShelfHandler(conn);
		ShelfAlterKey wSlAlterKey = new ShelfAlterKey();
		
		PaletteHandler wPlHandler = new PaletteHandler(conn);
		PaletteSearchKey wPlSearchKey = new PaletteSearchKey();

		//#CM45655
		// Do Deletion with Stock ID. 
		wStSearchKey.KeyClear();
		wStSearchKey.setStockId(p_stockid);

		try
		{
			readStock = (Stock)wStHandler.findPrimary(wStSearchKey);
			
			wStHandler.drop(wStSearchKey);
		}
		catch (NoPrimaryException e)
		{
			//#CM45656
			// 6003006 = This data is not useable because it was updated in other terminals. 
			wMessage = "6003006";
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			//#CM45657
			// 6003006 = This data is not useable because it was updated in other terminals. 
			wMessage = "6003006";
			throw new ScheduleException(e.getMessage());
		}
		
		//#CM45658
		// Confirm whether there is assortment of the stock in same Palette. 
		wStSearchKey.KeyClear();
		wStSearchKey.setPaletteid(readStock.getPaletteid());
		wStSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		
		//#CM45659
		// Do Deletion of update-Palette information on Location information when Inventory information does not exist. 
		if (wStHandler.count(wStSearchKey) == 0)
		{
			//#CM45660
			// Update of Shelf information.
			wSlAlterKey.KeyClear();
			//#CM45661
			// Information to which Station No is corresponding by Location No
			wSlAlterKey.setStationNumber(readStock.getLocationNo());
			//#CM45662
			// Renew Location Status of an empty shelf. 
			wSlAlterKey.updatePresence(Shelf.PRESENCE_EMPTY);
			
			try
			{
				wSlHandler.modify(wSlAlterKey);
			}
			catch (NotFoundException e)
			{
				//#CM45663
				// 6003006 = This data is not useable because it was updated in other terminals. 
				wMessage = "6003006";
				throw new ScheduleException(e.getMessage());
			}
			catch (InvalidDefineException e)
			{
				throw new ScheduleException(e.getMessage());
			}
			
			//#CM45664
			// Do Deletion of Palette information. 
			wPlSearchKey.KeyClear();
			wPlSearchKey.setPaletteId(readStock.getPaletteid());
			
			try
			{
				wPlHandler.drop(wPlSearchKey);
			}
			catch (NotFoundException e)
			{
				//#CM45665
				// 6003006 = This data is not useable because it was updated in other terminals. 
				wMessage = "6003006";
				throw new ScheduleException(e.getMessage());
			}
		}
						
		return (readStock);
	}
}
//#CM45666
//end of class

// $Id: AsShelfMaintenanceDeleteSCH.java,v 1.2 2006/10/30 00:51:09 suresh Exp $

//#CM45435
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.common.Parameter;
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

//#CM45436
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to do WEB ASRS stock maintenance (Deletion) processing.  <BR>
 * Receive the content input from the screen as parameter, and process Deletion of ASRS Inventory information.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and do the <BR>
 * update processing of the receipt data base.   <BR>
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
 * 2.Processing when Submit button is pressed.(<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *   Receive the content displayed in the filtering area as parameter, and process Deletion of the ASRS stock.  <BR>
 *   Return true when processing is normally completed and return false when it does not complete the schedule because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   [parameter] <BR>
 *   <DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Warehouse* <BR>
 *     Shelf No.* <BR>
 *     Terminal No. <BR>
 *   </DIR>
 *   <BR>
 *   [Processing condition check] <BR>
 *   <BR>
 *   -Worker code check processing-(<CODE>AbstructStorageSCH()</CODE>Class)<BR>
 *   <BR>
 *   -Next day update Processing check processing-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Inventory information processing check- <BR>
 *    1.In Shelf No of parameter, Inventory information must exist.  <BR>
 *    2.No impossible Deletion stock (- Picking is middle in Drawing).  <BR>
 *   <BR>
 *   [Update process] <BR>
 *   <DIR>
 *     <BR>
 *     -Registration of Results transmission information table (DNHOSTSEND) <BR>
 *      Register Results information on the maintenance decrease to all Inventory information based on Shelf No of parameter.  <BR>
 *     -Inventory information(DMSTOCK) Deletion <BR>
 *      Deletes Inventory information based on the content of parameter.  <BR>
 *     -When in object Location, there is no Inventory information <BR>
 *     <DIR>
 *       -Process Deletion of the palette table. -<BR>
 *        Do Deletion of the palette table with Palette ID of Inventory information.  <BR>
 *       -Update process of shelf table (SHELF)-<BR>
 *       <DIR>
 *         Location No of Inventory information is renewed.  <BR>
 *         1.Set an empty shelf in Location Status.  <BR>
 *       </DIR>
 *     </DIR>
 *     <BR>
 *     -Renewal registration of worker Results information table (DNWORKERRESULT) <BR>
 *      Register worker Results information to all Inventory information updating based on Shelf No of parameter.  <BR></DIR>
 *   </DIR>
 *   <BR>
 *</FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/05</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:51:09 $
 * @author  $Author: suresh $
 */
public class AsShelfMaintenanceDeleteSCH extends AbstractAsrsControlSCH
{
	//#CM45437
	//	Class variables -----------------------------------------------

	//#CM45438
	/**
	 * Class Name(AS/RS Stock Maintenance(Deletion))
	 */
	public static String PROCESSNAME = "AsShelfMaintenanceDeleteSCH";

	//#CM45439
	// Class method --------------------------------------------------
	//#CM45440
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:51:09 $");
	}
	//#CM45441
	// Constructors --------------------------------------------------
	//#CM45442
	/**
	 * Initialize this Class. 
	 */
	public AsShelfMaintenanceDeleteSCH()
	{
		wMessage = null;
	}
	//#CM45443
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

	//#CM45444
	/** 
	 * Receive the content of the area of the accumulation input from the screen as parameter, do the mandatory check, Overflow check, Duplication Check and return the check result.   <BR>
	 * Do Mandatory check, Overflow check, Duplicate check and return the check result.  <BR>
	 * Assume true to be an exclusive error when an occurrence of the condition error and pertinent data exist, and return false when pertinent data of same Line  No. does not exist in Inventory information.   <BR>
	 * Assume an exclusive error, and return false when the condition error occurs and pertinent data exists. 
	 * @param conn Instance to maintain connection with data base. 
	 * @param checkParam Instance of < CODE>IdmControlParameter</CODE>Class with content of input. 
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ScheduleException
	{
		throw new ScheduleException();
	}

	//#CM45445
	/**
	 * Receive the content input from the screen as parameter, and begin the No plan storage setting schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Return true when the schedule ends normally and return false when failing.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Array of instance of < CODE>IdmControlParameter</CODE>Class with set content.  <BR>
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{

		try
		{
			//#CM45446
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}

			//#CM45447
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return false;
			}

			AsScheduleParameter param = (AsScheduleParameter) startParams[0];
			AsScheduleParameter mdparam = new AsScheduleParameter();

			try
			{
				//#CM45448
				// Check Location Status. 
				if (!checkLocation(conn, param.getLocationNo()))
				{
					return false;
				}
			}
			catch (Exception e)
			{
				throw new ScheduleException(e.getMessage());
			}

			//#CM45449
			// Worker code
			String workercode = param.getWorkerCode();
			//#CM45450
			// Worker Name
			String workerName = getWorkerName(conn, workercode);

			//#CM45451
			// Work date(System definition date)
			String sysdate = getWorkDate(conn);

			//#CM45452
			// Terminal No.
			String terminalno = param.getTerminalNumber();
			//#CM45453
			// Work Flag(Maintenance)
			String jobtype = Stock.JOB_TYPE_MAINTENANCE_MINUS;
			String iojobtype = InOutResult.WORKTYPE_AS_MAINTENANCE_MINUS;
			//#CM45454
			// Acquire each registered one mind key. 
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM45455
			// Batch No.(One setting commonness)
			String batch_seqno = sequence.nextNoPlanBatchNo();
			//#CM45456
			// Inventory information
			Stock[] readStock = null;
			Stock resultStock = new Stock();
			
			try
			{
				//#CM45457
				// Lock processing (Shelf-Palette Information)
				readStock = lockStockData(conn, param.getLocationNo());
			}
			catch(LockTimeOutException e)
			{
				//#CM45458
				// 6027008 = This data is not useable because it is updating it with other terminals. 
				wMessage = "6027008";
				return false;
			}

			//#CM45459
			// Process the Inventory information flag for Location.
			for (int lc=0; lc<readStock.length; lc++)
			{
				int pltkind = 0;
	
				//#CM45460
				// Acquire Item Code of empty PB.
				String wEmpItemCode = AsrsParam.EMPTYPB_ITEMKEY;
				//#CM45461
				// Palette Status 
				if (!wEmpItemCode.equals(readStock[lc].getItemCode()))
				{
					pltkind = Palette.NORMAL;
				}
				else
				{
					pltkind = Palette.STATUS_EMPTY;
				}
				
				//#CM45462
				// Delete process
				//#CM45463
				// Delete Inventory information.
				resultStock = deleteStockData(conn, readStock[lc].getStockId());

				mdparam.setAreaNo(resultStock.getAreaNo());
				mdparam.setWareHouseNo(resultStock.getAreaNo());
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
				
				//#CM45464
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
				
				if (!readStock[lc].getItemCode().equals(wEmpItemCode))
				{
					//#CM45465
					// Registration of Results transmission information table (DNHOSTSEND)
					if (!createHostsend(conn,
						mdparam,
						resultStock.getStockId(),
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
							
				//#CM45466
				// Registration of worker Results information table (DNWORKERRESULT)
				updateWorkerResult(conn,workercode,workerName,sysdate,terminalno,jobtype,resultStock.getStockQty());
			}

			//#CM45467
			// Deleted.
			wMessage = "6001005";
				
			return true;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM45468
	/**
	 * Whether Status of the shelf be able to be checked and maintain it are decided. 
	 * @param conn Connection object with database
	 * @param p_location Station No.
	 * @return Return True if Maintenance is possible.
	 * @throws Exception It reports on all exceptions.  
	 */
	private boolean checkLocation(Connection conn, String p_location) throws Exception
	{
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();
				
		wShSearchKey.KeyClear();
		//#CM45469
		// WarehouseFlag+Input Shelf No.
		wShSearchKey.setStationNumber(p_location);
		
		Shelf wShelf = (Shelf)wShHandler.findPrimary(wShSearchKey);

		if (wShelf == null)
		{
			//#CM45470
			// Return True if Maintenance is possible.
			wMessage = "6013090" ;
			return false;
		}

		//#CM45471
		// The inaccessible shelf is assumed to be improper Maintenance.
		if (wShelf.getAccessNgFlag() == Shelf.ACCESS_NG)
		{		
			//#CM45472
			// The specified location is not good at Maintenance because of the inaccessible shelf.
			wMessage = "6013160" ;	
			return false;
		}

		//#CM45473
		// Check the restricted location.
		if (wShelf.getStatus() == Shelf.STATUS_NG)
		{		
			//#CM45474
			// The specified shelf is not good at Maintenance because of the restricted shelf.
			wMessage = "6013159" ;	
			return false;
		}
		
		//#CM45475
		// Status of the location is checked. 
		if (wShelf.getPresence() == (Shelf.PRESENCE_RESERVATION))
		{
			//#CM45476
			//<en> The specified shelf is not good at Maintenance because of the reservation shelf.</en>
			wMessage = "6013158" ;	
			return false;
		}
		
		//#CM45477
		// Status of the location is checked. 
		if (wShelf.getPresence() == (Shelf.PRESENCE_EMPTY))
		{
			//#CM45478
			//<en> The stock does not exist in the specified shelf. </en>
			wMessage = "6013134" ;	
			return false;
		}

		Palette[] wPalette = null;
		//#CM45479
		// Drawing and checking the Abnormal shelf in case of Real shelf.
		if (wShelf.getPresence() == Shelf.PRESENCE_STORAGED)
		{
			PaletteHandler wPlHandler = new PaletteHandler(conn);
			PaletteSearchKey wPlSearchKey = new PaletteSearchKey();
			
			//#CM45480
			// Set Search condition. 
			wPlSearchKey.KeyClear();
			wPlSearchKey.setCurrentStationNumber(p_location);
			
			wPalette = (Palette[])wPlHandler.find(wPlSearchKey);
			
			int pltstatus = wPalette[0].getStatus();
			switch (pltstatus)
			{
				//#CM45481
				// Real shelf
				case Palette.REGULAR:
					break;
				//#CM45482
				// Stock reservation
				case Palette.STORAGE_PLAN:
				//#CM45483
				// Picking reservation
				case Palette.RETRIEVAL_PLAN:
				//#CM45484
				// Picking
				case Palette.RETRIEVAL:
					//#CM45485
					// The stock of the specified shelf is Drawing now. 
					wMessage = "6013135" ;	
					return false;
				//#CM45486
				// Abnormal
				case Palette.IRREGULAR:
					break;
			}
		}
		
		return true;
	}

	//#CM45487
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
	 * @param conn Connection object with database
	 * @param p_location Station No.
	 * @return Inventory information
	 * @throws NoPrimaryException	 Notifies when definition information is abnormal. 
	 * @throws ReadWriteException   Notified when abnormality occurs by the connection with the data base.
	 * @throws LockTimeOutException It is notified when the lock is not opened even if specified time passes. 
	 */
	protected Stock[] lockStockData(Connection conn, String p_locationno)
		throws NoPrimaryException, ReadWriteException, LockTimeOutException
	{
		StockHandler wStHandler = new StockHandler(conn);
		StockSearchKey wStSearchKey = new StockSearchKey();
		
		ShelfHandler wSlHandler = new ShelfHandler(conn);
		ShelfSearchKey wSlSearchKey = new ShelfSearchKey();
		
		PaletteHandler wPlHandler = new PaletteHandler(conn);
		PaletteSearchKey wPlSearchKey = new PaletteSearchKey();

		//#CM45488
		// Stock information Search condition
		wStSearchKey.KeyClear();
		wStSearchKey.setLocationNo(p_locationno);
		wStSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		Stock[] rStock = (Stock[])wStHandler.findForUpdateNowait(wStSearchKey);

		//#CM45489
		// Location information search condition
		wSlSearchKey.KeyClear();
		wSlSearchKey.setStationNumber(p_locationno);
		wSlHandler.lockNowait(wSlSearchKey);
		
		//#CM45490
		// Palette information search condition
		wPlSearchKey.KeyClear();
		wPlSearchKey.setCurrentStationNumber(p_locationno);
		wPlHandler.lockNowait(wPlSearchKey);
		
		return (rStock);
		
	}

	//#CM45491
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
	 * @return Inventory information
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

		//#CM45492
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
			//#CM45493
			// 6003006 = This data is not useable because it was updated in other terminals. 
			wMessage = "6003006";
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			//#CM45494
			// 6003006 = This data is not useable because it was updated in other terminals. 
			wMessage = "6003006";
			throw new ScheduleException(e.getMessage());
		}
		
		//#CM45495
		// Confirm whether there is assortment of the stock in same Palette. 
		wStSearchKey.KeyClear();
		wStSearchKey.setPaletteid(readStock.getPaletteid());
		wStSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		
		//#CM45496
		// Do Deletion of update-Palette information on Location information when Inventory information does not exist. 
		if (wStHandler.count(wStSearchKey) == 0)
		{
			//#CM45497
			// Update of Shelf information.
			wSlAlterKey.KeyClear();
			//#CM45498
			// Information to which Station No is corresponding by Location No
			wSlAlterKey.setStationNumber(readStock.getLocationNo());
			//#CM45499
			// Renew Location Status of an empty shelf. 
			wSlAlterKey.updatePresence(Shelf.PRESENCE_EMPTY);
			
			try
			{
				wSlHandler.modify(wSlAlterKey);
			}
			catch (NotFoundException e)
			{
				//#CM45500
				// 6003006 = This data is not useable because it was updated in other terminals. 
				wMessage = "6003006";
				throw new ScheduleException(e.getMessage());
			}
			catch (InvalidDefineException e)
			{
				throw new ScheduleException(e.getMessage());
			}
			
			//#CM45501
			// Do Deletion of Palette information. 
			wPlSearchKey.KeyClear();
			wPlSearchKey.setPaletteId(readStock.getPaletteid());
			
			try
			{
				wPlHandler.drop(wPlSearchKey);
			}
			catch (NotFoundException e)
			{
				//#CM45502
				// 6003006 = This data is not useable because it was updated in other terminals. 
				wMessage = "6003006";
				throw new ScheduleException(e.getMessage());
			}
		}
						
		return (readStock);
	}
}
//#CM45503
//end of class

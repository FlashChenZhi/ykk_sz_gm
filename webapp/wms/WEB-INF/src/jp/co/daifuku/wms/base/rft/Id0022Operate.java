// $Id: Id0022Operate.java,v 1.2 2006/11/14 06:09:04 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701342
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.RftConsignor;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM701343
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do Consignor  list request (ID0022) processing. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive Work flag and Plan date as a parameter, and acquire Consignor  List information,<BR>
 * Make Consignor  list (Consignor Code ascending order) file. <BR>
 * <BR>
 * Consignor list acquisition(<CODE>findConsignorCode()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Work flag and Plan date as a parameter, and return Consignor  List information. <BR>
 *   Consignor  List information acquisition destination is distinguished by Work flag. <BR>
 *   Acquire it from inventory information (dmstock) for Work flag "Movement Picking and ELocation wholesale". <BR>
 *   Acquire it from Work information(dnworkinfo) for Work flag "Receiving , Storage, Picking , Sorting, and Shipping ". <BR>
 *   Acquire it from movement Work information(dnmovement) for Work flag "Movement Storage". <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Consignor  list file making(<CODE>createTableFile()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Consignor  List information and File Name (full path) as a parameter, and make the Consignor  list file. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:04 $
 * @author  $Author: suresh $
 */
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do Consignor  list request (ID0022) processing. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive Work flag and Plan date as a parameter, and acquire Consignor  List information,<BR>
 * Make Consignor  list (Consignor Code ascending order) file. <BR>
 * <BR>
 * Consignor list acquisition(<CODE>findConsignorCode()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Work flag and Plan date as a parameter, and return Consignor  List information. <BR>
 *   Consignor  List information acquisition destination is distinguished by Work flag. <BR>
 *   Acquire it from inventory information (dmstock) for Work flag "Movement Picking and ELocation wholesale". <BR>
 *   Acquire it from Work information(dnworkinfo) for Work flag "Receiving , Storage, Picking , Sorting, and Shipping ". <BR>
 *   Acquire it from movement Work information(dnmovement) for Work flag "Movement Storage". <BR>
 * </DIR>
 * <BR>
 * <BR>
 * Consignor  list file making(<CODE>createTableFile()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Consignor  List information and File Name (full path) as a parameter, and make the Consignor  list file. <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:04 $
 * @author  $Author: suresh $
 */
public class Id0022Operate extends IdOperate
{
	//#CM701344
	// Class fields----------------------------------------------------
	//#CM701345
	// Class variables -----------------------------------------------
	//#CM701346
	// Constructors --------------------------------------------------
	//#CM701347
	/**
	 * Generate the instance. 
	 */
	public Id0022Operate()
	{
		super();
	}

	//#CM701348
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0022Operate(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM701349
	// Class method --------------------------------------------------
	//#CM701350
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:04 $";
	}

	//#CM701351
	// Public methods ------------------------------------------------
	//#CM701352
	/**
	 * Acquire Consignor  List information in the condition of specifying it. <BR>
	 * It depends on Work flag and hereafter, it becomes a content Consignor list acquisition ahead. <BR>
	 *   <DIR>
	 *   [1:Receiving ,2:Storage,3:Picking ,4:Sorting,5:Shipping ]<BR>
	 *     <DIR>
	 *     Acquire it from Work information. <BR>
	 *     </DIR>
	 *   [11:Movement Picking ]<BR>
	 *     <DIR>
	 *     Acquire it from the inventory information. <BR>
	 *     </DIR>
	 *   [12:Movement Storage]<BR>
	 *     <DIR>
	 *     Acquire it from the movement information.  <BR>
	 *     </DIR>
	 *   [40:Inventory]<BR>
	 *     <DIR>
	 *     Acquire it from the inventory information. <BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  workType Work flag<BR>
	 * <DIR>
	 * <DIR>
	 *                 01:Receiving <BR>
	 *                 02:Storage<BR>
	 *                 03:Picking <BR>
	 *                 04:Sorting<BR>
	 *                 05:Shipping <BR>
	 *                 11:Movement Picking <BR>
	 *                 12:Movement Storage<BR>
	 *                 21:Exception Storage<BR>
	 *                 22:Exception Picking <BR>
	 *                 40:Inventory<BR>
	 * </DIR>
	 * </DIR>
	 * @param  planDate		Work plan date
	 * @param  workType    Work type
	 * @param  workDetails Detail Work type
	 * @param	rftNo		RFT Number
	 * @param	workerCode	Worker code
	 * @return	Consignor list
	 * @throws IllegalArgumentException It is notified for illegal Work flag. 
	 * @throws NotFoundException It is notified when Data does not exist. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	 */
	public RftConsignor[] findConsignorCode(
			String planDate,
			String workType,
			String workDetails,
			String rftNo,
			String workerCode)
		throws IllegalArgumentException, NotFoundException, ReadWriteException, ScheduleException
	{
		//#CM701353
		// Maintain acquired Consignor  information. 
		RftConsignor[] data = null;
		//#CM701354
		// Consignor list acquisition
		if (workType.equals(SystemDefine.JOB_TYPE_INSTOCK)
			|| workType.equals(SystemDefine.JOB_TYPE_STORAGE)
			|| workType.equals(SystemDefine.JOB_TYPE_RETRIEVAL)
			|| workType.equals(SystemDefine.JOB_TYPE_SORTING)
			|| workType.equals(SystemDefine.JOB_TYPE_SHIPINSPECTION))
		{
			//#CM701355
			// Acquisition from Work information
			data = findConsignorFromWorkInfo(planDate, workType, workDetails, rftNo, workerCode);
		}
		else if (workType.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL)
				|| workType.equals(SystemDefine.JOB_TYPE_EX_STORAGE)
				|| workType.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			//#CM701356
			// Acquisition from inventory information
			data = findConsignorFromStock(workType);
		}
		else if (workType.equals(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE))
		{
			//#CM701357
			// Acquisition from Movement Work information
			data = findConsignorFromMovement();
		}
		else if (workType.equals(SystemDefine.JOB_TYPE_INVENTORY))
		{
			//#CM701358
			// Acquisition from inventory information
			data = findConsignorFromInventoryCheckAndStock();
		}
		else
		{
			//#CM701359
			// Work flag is abnormal. 
			throw new IllegalArgumentException(workType);
		}

		return data;
	}

	//#CM701360
	// Package methods ----------------------------------------------
	//#CM701361
	// Protected methods --------------------------------------------
	//#CM701362
	// Private methods -----------------------------------------------
	//#CM701363
	/**
	 * Acquire Consignor list from Stock information(dmstock). <BR>
	 * The stock status and the quantity of stock acquires Consignor Name of correspondence <BR>
	 * Consignor Code from stock Data of one or more in a central stock. <BR>
	 * However, exclude repetition Data. <BR>
	 * <BR>
	 *     <DIR>
	 *     [Acquisition item]<BR>
	 *         <DIR>
	 *         Consignor Code<BR>
	 *         Consignor Name<BR>
	 *         </DIR>
	 *     [Search condition]<BR>
	 *         <DIR>
	 *         Stock status:Center stocking<BR>
	 *         Stock qty        : One or more only in case of Movement Picking  and Exception Picking. <BR>
	 *         Stock information.Area No.=Area Master information.Area No.<BR>
	 *         Area Master information.Area flag =2:Other than ASRS<BR> 
	 *         </DIR>
	 *     [Consolidating condition]<BR>
	 *         <DIR>
	 *         Consignor Code<BR>
	 *         Consignor Name<BR>
	 *         </DIR>
	 *     [Sorting order]<BR>
	 *         <DIR>
	 *         Consignor Code<BR>
	 *         Consignor Name<BR>
	 *         </DIR>
	 *     </DIR>
	 * @param	jobType	Work type
	 * @return	Consignor List information
	 * @throws NotFoundException  It is notified when Consignor  information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	 */
	private RftConsignor[] findConsignorFromStock(String jobType) throws NotFoundException, ReadWriteException, ScheduleException
	{
		StockSearchKey stockSearchKey = new StockSearchKey();

		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//#CM701364
		//-----------------
		//#CM701365
		// Set Acquisition item
		//#CM701366
		//-----------------
		//#CM701367
		// Consignor Code
		stockSearchKey.setConsignorCodeCollect();
		//#CM701368
		// Consignor Name
		stockSearchKey.setConsignorNameCollect();

		//#CM701369
		//-----------------
		//#CM701370
		// Set Search condition
		//#CM701371
		//-----------------
		//#CM701372
		// Status flag(Center stocking)
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM701373
		// Stock qty targets only Stock of one or more for Movement Picking  and Exception Picking. 
		if (jobType.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL)
			|| jobType.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			stockSearchKey.setAllocationQty(0, ">");
		}
		//#CM701374
		// Acquire Area of Other than ASRS, and add to Search condition. 
		//#CM701375
		// IS NULL retrieval when there is no correspondence Area
		areaNo = AreaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNo);

		//#CM701376
		//-----------------
		//#CM701377
		// Consolidating condition set
		//#CM701378
		//-----------------
		stockSearchKey.setConsignorCodeGroup(1);
		stockSearchKey.setConsignorNameGroup(2);

		//#CM701379
		//-----------------
		//#CM701380
		// The sorting order set
		//#CM701381
		//-----------------
		stockSearchKey.setConsignorCodeOrder(1, true);
		stockSearchKey.setConsignorNameOrder(2, true);

		//#CM701382
		//-----------------
		//#CM701383
		// Retrieval processing
		//#CM701384
		//-----------------
		StockHandler stockHandler = new StockHandler(wConn);
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);

		//#CM701385
		// When Stock information cannot be acquired, throws NotFoundException. 
		if (stock == null || stock.length == 0)
		{
			throw (new NotFoundException());
		}

		//#CM701386
		// Consignor List information acquisition
		RftConsignor[] data = new RftConsignor[stock.length];
		for (int i = 0; i < stock.length; i++)
		{
			data[i] =
				new RftConsignor(
					stock[i].getConsignorCode(),
					stock[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

	//#CM701387
	/**
	 * Acquire Consignor list from Work information(dnworkinfo) by specifying it in the condition. <BR>
	 * From the data whose Status flag is Stand by and Work start flag is Started,<BR>
	 * In condition (Work type,Detail Work type,Work plan date) of specifying
	 * Acquire the Data list of Consignor Code and Consignor Name in Consignor Code ascending order. <BR>
	 * However, exclude repetition Data . Moreover, exclude it from Search condition when Consignor Work plan date is space. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *         <DIR>
	 *         Consignor Code<BR>
	 *         Consignor Name<BR>
	 *         </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Work flag      :Acquisition from parameter<BR>
	 *     Work plan date    :Acquisition from parameter (Remove from the condition for the blank. ) <BR>
	 *     Status flag    :Thing done while Stand by or automatic terminal and self worker are working<BR>
	 *     Work start flag:Started<BR>
	 *     TC/DC flag     :DC or Cross TC (When work type is 01 and a work detailed type is only 1 or 2) <BR>
	 *     TC/DC flag     :TC (When work type is 01 and a work detailed type is only 3) <BR>
	 *     Order No.   :The one which is not empty (Only when work type is 03 and work detailed type is only 1) <BR>
	 *     Order No.   :The one which is empty (Only when work type is 03 and work detailed type is only 2) <BR>
	 *     </DIR>
	 *   [Consolidating condition]<BR>
	 *     <DIR>
	 *       Consignor Code<BR>
	 *       Consignor Name<BR>
	 *     </DIR>
	 *   [Sorting order]<BR>
	 *     <DIR>
	 *       Consignor Code<BR>
	 *       Consignor Name<BR>
	 *     </DIR>
	 *   </DIR>
	 * 
	 * @param  planDate 	Work plan date
	 * @param	workType	Work type
	 * <DIR>
	 * <DIR>
	 *                  1:Receiving <BR>
	 *                  2:Storage<BR>
	 *                  3:Picking <BR>
	 *                  4:Sorting<BR>
	 *                  5:Shipping <BR>
	 * </DIR>
	 * </DIR>
	 * @param  planDate    Work plan date
	 * @param  workType    Work type
	 * @param	workDetails	Detail Work type
	 * @param	rftNo		RFT Number
	 * @param	workerCode	Worker code
	 * @return	Consignor List information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when Consignor  information is not found. 
	 */
	private RftConsignor[] findConsignorFromWorkInfo(
	        String planDate,
	        String workType,
	        String workDetails,
	        String rftNo,
	        String workerCode)
		throws ReadWriteException, NotFoundException
	{
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		//#CM701388
		//-----------------
		//#CM701389
		// Set Acquisition item
		//#CM701390
		//-----------------
		//#CM701391
		// Consignor Code
		workInfoSearchKey.setConsignorCodeCollect();
		//#CM701392
		// Consignor Name
		workInfoSearchKey.setConsignorNameCollect();

		//#CM701393
		//-----------------
		//#CM701394
		// Set Search condition
		//#CM701395
		//-----------------
		//#CM701396
		// Work flag
		workInfoSearchKey.setJobType(workType);
		//#CM701397
		// Do not add it to the condition for the Work plan date space. 
		if (planDate.trim().length() > 0)
		{
			workInfoSearchKey.setPlanDate(planDate);
		}
		//#CM701398
		// Status flag
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		workInfoSearchKey.setWorkerCode(workerCode);
		workInfoSearchKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM701399
		// Work start flag
		workInfoSearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				//#CM701400
				// Retrieve DC and crossing TC of the unit of the supplier or each item. 
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				//#CM701401
				// Retrieve TC for each and every customer
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				//#CM701402
				// Retrieve data where order No is set at the order picking. 
				workInfoSearchKey.setOrderNo("", "IS NOT NULL");
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				//#CM701403
				// Order No retrieves empty data at the item picking. 
				workInfoSearchKey.setOrderNo("", "=");
			}
		}

		//#CM701404
		//-----------------
		//#CM701405
		// Consolidating condition set
		//#CM701406
		//-----------------
		workInfoSearchKey.setConsignorCodeGroup(1);
		workInfoSearchKey.setConsignorNameGroup(2);

		//#CM701407
		//-----------------
		//#CM701408
		// The sorting order set
		//#CM701409
		//-----------------
		workInfoSearchKey.setConsignorCodeOrder(1, true);
		workInfoSearchKey.setConsignorNameOrder(2, true);

		//#CM701410
		//-----------------
		//#CM701411
		// Retrieval processing
		//#CM701412
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workInfo =
			(WorkingInformation[]) workInfoHandler.find(workInfoSearchKey);

		//#CM701413
		// When Work information cannot be acquired, throws NotFoundException. 
		if (workInfo == null || workInfo.length == 0)
		{
			throw (new NotFoundException());
		}

		//#CM701414
		// Consignor List information acquisition
		RftConsignor[] data = new RftConsignor[workInfo.length];
		for (int i = 0; i < workInfo.length; i++)
		{
			data[i] =
				new RftConsignor(
					workInfo[i].getConsignorCode(),
					workInfo[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

	//#CM701415
	/**
	 * Acquire Consignor list from Movement Work information(dnmovement). <BR>
	 * Status flag and Work start flag acquires Consignor Name of correspondence <BR>
	 * Consignor Code from movement work Data of Started in the Storage waiting. <BR>
	 * However, exclude repetition Data. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Consignor Code<BR>
	 *     Consignor Name<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Status flag    :Waiting for Storage<BR>
	 *     Work start flag:Started<BR>
	 *     Movement Work information.Area No.=Area Master information.Area No.<BR>
	 *     Area Master information.Area flag =2:Other than ASRS<BR>
	 *     </DIR>
	 *   [Consolidating condition]<BR>
	 *     <DIR>
	 *     Consignor Code<BR>
	 *     Consignor Name<BR>
	 *     </DIR>
	 *   [Sorting order]<BR>
	 *     <DIR>
	 *     Consignor Code<BR>
	 *     Consignor Name<BR>
	 *     </DIR>
	 *   </DIR>
	 * @return	Consignor List information
	 * @throws NotFoundException  It is notified when Consignor  information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	 */
	private RftConsignor[] findConsignorFromMovement() throws NotFoundException, ReadWriteException, ScheduleException
	{
		MovementSearchKey movementSearchKey = new MovementSearchKey();

		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//#CM701416
		//-----------------
		//#CM701417
		// Set Acquisition item
		//#CM701418
		//-----------------
		//#CM701419
		// Consignor Code
		movementSearchKey.setConsignorCodeCollect();
		//#CM701420
		// Consignor Name
		movementSearchKey.setConsignorNameCollect();

		//#CM701421
		//-----------------
		//#CM701422
		// Set Search condition
		//#CM701423
		//-----------------
		//#CM701424
		// Status flag(Waiting for Storage)
		movementSearchKey.setStatusFlag(Movement.STATUSFLAG_UNSTART);
		//#CM701425
		// Work start flag
		movementSearchKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);

		//#CM701426
		// Acquire Area of Other than ASRS, and add to Search condition. 
		//#CM701427
		// IS NULL retrieval when there is no correspondence Area
		areaNo = AreaOperator.getAreaNo(areaType);
		movementSearchKey.setAreaNo(areaNo);

		//#CM701428
		//-----------------
		//#CM701429
		// Consolidating condition set
		//#CM701430
		//-----------------
		movementSearchKey.setConsignorCodeGroup(1);
		movementSearchKey.setConsignorNameGroup(2);

		//#CM701431
		//-----------------
		//#CM701432
		// The sorting order set
		//#CM701433
		//-----------------
		movementSearchKey.setConsignorCodeOrder(1, true);
		movementSearchKey.setConsignorNameOrder(2, true);

		//#CM701434
		//-----------------
		//#CM701435
		// Retrieval processing
		//#CM701436
		//-----------------
		MovementHandler movementHandler = new MovementHandler(wConn);
		Movement[] movement = (Movement[]) movementHandler.find(movementSearchKey);

		//#CM701437
		// When Stock information cannot be acquired, throws NotFoundException. 
		if (movement == null || movement.length == 0)
		{
			throw (new NotFoundException());
		}

		//#CM701438
		// Consignor List information acquisition
		RftConsignor[] data = new RftConsignor[movement.length];
		for (int i = 0; i < movement.length; i++)
		{
			data[i] =
				new RftConsignor(
					movement[i].getConsignorCode(),
					movement[i].getConsignorName(),
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"");
		}

		return data;
	}

	//#CM701439
	/**
	 * Inventory Work information(dninventorycheck),Acquire Consignor list from Stock information(dmstock). <BR>
	 * Inventory work Data of Processing flag of Excluding the deletion<BR>
	 * Stock status is Center stocking and Stock qty is Stock of one or more. <BR>
	 * The string is applied to Area  No. of Stock information and Area flag of turning over Area Master information is 2: From StockData of Other than ASRS<BR>
	 * Acquire the Data list of Consignor Code and Consignor Name in Consignor Code ascending order. <BR>
	 * However, exclude repetition Data. <BR>
	 * <BR>
	 * @return	Consignor List information
	 * @throws NotFoundException  It is notified when Consignor  information is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	 */
	private RftConsignor[] findConsignorFromInventoryCheckAndStock() throws NotFoundException, ReadWriteException
	{
		Statement stmt        = null;
		ResultSet rset        = null;
		RftConsignor[] consignor = null;
		try
		{
			stmt = wConn.createStatement();

			String sqlstring =	"SELECT CONSIGNOR_CODE, CONSIGNOR_NAME FROM DNINVENTORYCHECK " + 
									"WHERE STATUS_FLAG != '" + InventoryCheck.STATUS_FLAG_DELETE + "' " +
								"UNION " + 
								"SELECT CONSIGNOR_CODE, CONSIGNOR_NAME FROM DMSTOCK, DMAREA " +
									"WHERE STATUS_FLAG = '" + Stock.STOCK_STATUSFLAG_OCCUPIED + "'" +
									" AND STOCK_QTY > 0" +
									" AND DMSTOCK.AREA_NO = DMAREA.AREA_NO " +
									" AND (DMAREA.AREA_TYPE = '" + Area.SYSTEM_DISC_KEY_WMS + "' OR DMAREA.AREA_TYPE = '"  + Area.SYSTEM_DISC_KEY_IDM + "') " +
								"GROUP BY CONSIGNOR_CODE, CONSIGNOR_NAME " +
								"ORDER BY CONSIGNOR_CODE, CONSIGNOR_NAME";
			
			rset = stmt.executeQuery(sqlstring);

			if (rset != null)
			{
				Vector vec = new Vector();
				String consignorCode = "";
				String consignorName = "";
				while (rset.next())
				{
					consignorCode = DBFormat.replace(rset.getString("CONSIGNOR_CODE"));
					consignorName = DBFormat.replace(rset.getString("CONSIGNOR_NAME"));
					vec.addElement(
						new RftConsignor(consignorCode, consignorName,
									   "",	"",	"",	"",	"",	"",	"",	"",	"")
					);
				}
				consignor = new RftConsignor[vec.size()];
				vec.copyInto(consignor);
			}
		}
		catch(SQLException e)
		{
			//#CM701440
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, "Id0022Operate", e);
			//#CM701441
			// Here, do ReadWriteException and do throw with the error message. 
			throw (new ReadWriteException("6006002" + MessageResource.DELIM + "dninventorycheck,dmstock")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close();  rset = null; }
				if (stmt != null) { stmt.close();  stmt = null; }
			}
			catch(SQLException e)
			{
				//#CM701442
				// 6006002 = Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, "Id0022Operate", e);
				//#CM701443
				// Here, do ReadWriteException and do throw with the error message. 
				throw (new ReadWriteException("6006002" + MessageResource.DELIM + "dninventorycheck,dmstock")) ;
			}
		}
		//#CM701444
		// When Consignor list cannot be acquired, throws NotFoundException. 
		if (consignor == null || consignor.length == 0)
		{
			throw (new NotFoundException());
		}
		return consignor;
	}

}
//#CM701445
//end of class

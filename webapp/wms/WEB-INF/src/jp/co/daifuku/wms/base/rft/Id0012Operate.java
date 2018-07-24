// $Id: Id0012Operate.java,v 1.3 2006/11/14 06:24:36 kamala Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700953
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MovementHandler;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to process Consignor Code Inquiry(ID0012). <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive Work flag, Consignor Code, and Work plan date as a parameter, and acquire the Consignor Name. <BR>
 * Consignor Name acquisition(<CODE>getConsignorName()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Work flag, Consignor Code, and Plan date as a parameter, and return the Consignor Name.<BR>
 *   The consignor information acquisition destination is distinguished by Work flag. <BR>
 *   Acquire it from inventory information (dmstock) for Work flag "Movement Picking and ELocation wholesale". <BR>
 *   Acquire it from Work information(dnworkinfo) for Work flag "Receiving , Storage, Picking , Sorting, and Shipping ". <BR>
 *   Acquire it from movement Work information(dnmovement) for Work flag "Movement Storage". <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/14 06:24:36 $
 * @author  $Author: kamala $
 */
public class Id0012Operate extends IdOperate
{
	//#CM700955
	// Class fields----------------------------------------------------
	//#CM700956
	// Class variables -----------------------------------------------
	//#CM700957
	// Constructors --------------------------------------------------
	//#CM700958
	/**
	 * Generate the instance.
	 */
	public Id0012Operate()
	{
		super();
	}

	//#CM700959
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance.
	 * @param conn Database connection
	 */
	public Id0012Operate(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM700960
	// Class method --------------------------------------------------
	//#CM700961
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2006/11/14 06:24:36 $";
	}

	//#CM700962
	// Public methods ------------------------------------------------
	//#CM700963
	/**
	 * Acquire the Consignor Name in the condition of specifying it.<BR>
	 * The consignor information acquisition destination depends on Work flag, and, hereafter, becomes a content. <BR>
	 * <BR>
	 *   <DIR>
	 *   [1:Receiving ,2:Storage,3:Picking ,4:Sorting,5:Shipping ]<BR>
	 *     <DIR>
	 *     Acquire it from Work information. <BR>
	 *     </DIR>
	 *   [11:Movement Picking ,21:Exception Storage,22:Exception Picking ]<BR>
	 *     <DIR>
	 *     Acquire it from the inventory information. <BR>
	 *     </DIR>
	 *   [12:Movement Storage]<BR>
	 *     <DIR>
	 *     Acquire it from the movement information.  <BR>
	 *     </DIR>
	 *   [40:Inventory]<BR>
	 *     <DIR>
	 *     Inventory Work information,Acquire it from the inventory information. <BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  jobType Work flag<BR>
	 * <DIR>
	 * <DIR>
	 *                 01:Receiving <BR>
	 *                 02:Storage<BR>
	 *                 03:Picking <BR>
	 *                 04:Sorting<BR>
	 *                 05:Shipping <BR>
	 *                 11:Movement Picking <BR>
	 *                 12:Movement Storage<BR>
	 * 				   21:Exception Storage<BR>
	 *                 22:Exception Picking <BR>
	 *                 40:Inventory<BR>
	 * </DIR>
	 * </DIR>
	 * @param	jobType			Work type
	 * @param	workDetails		Detail Work type
	 * @param  consignorCode Consignor Code
	 * @param  planDate      Work plan date
	 * @param	rftNo			RFT Number
	 * @param	workerCode		Worker code
	 * @return	Consignor Name
	 * @throws IllegalArgumentException It is notified for illegal Work flag.
	 * @throws NotFoundException It is notified when Data does not exist.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated.
	 */
	public String getConsignorName(
	        String jobType,
	        String workDetails,
	        String consignorCode,
	        String planDate,
	        String rftNo,
	        String workerCode)
		throws IllegalArgumentException, NotFoundException, ReadWriteException, ScheduleException
	{
		//#CM700964
		// Maintain the acquired Consignor Name.
		String data = null;

		//#CM700965
		// Consignor list acquisition
		if (jobType.equals(SystemDefine.JOB_TYPE_INSTOCK)
			|| jobType.equals(SystemDefine.JOB_TYPE_STORAGE)
			|| jobType.equals(SystemDefine.JOB_TYPE_RETRIEVAL)
			|| jobType.equals(SystemDefine.JOB_TYPE_SORTING)
			|| jobType.equals(SystemDefine.JOB_TYPE_SHIPINSPECTION))
		{
			//#CM700966
			// Acquisition from Work information
			data = getConsignorNameFromWorkInfo(jobType, workDetails, consignorCode, planDate, rftNo, workerCode);
		}
		else if (jobType.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL)
		         || jobType.equals(SystemDefine.JOB_TYPE_EX_STORAGE)
		         || jobType.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL))
		{
			//#CM700967
			// Acquisition from inventory information
			data = getConsignorNameFromStock(consignorCode, jobType);
		}
		else if (jobType.equals(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE))
		{
			//#CM700968
			// Acquisition from Movement Work information
			data = getConsignorNameFromMovement(consignorCode);
		}
		else if (jobType.equals(SystemDefine.JOB_TYPE_INVENTORY))
		{
			try{
				//#CM700969
				// Inventory Acquisition from Work information
				data = getConsignorNameFromInventoryCheck(consignorCode);
			}
			catch(NotFoundException e)
			{
				//#CM700970
				// Acquisition from inventory information when there is no Data in Inventory Work information
				data = getConsignorNameFromStock(consignorCode, jobType);
			}
		}
		else
		{
			//#CM700971
			// Work flag is abnormal.
			throw (new IllegalArgumentException(jobType));
		}

		return data;
	}

	//#CM700972
	// Package methods ----------------------------------------------
	//#CM700973
	// Protected methods --------------------------------------------
	//#CM700974
	// Private methods -----------------------------------------------
	//#CM700975
	/**
	 * Acquire the Consignor Name in the condition of specifying it from inventory information (dmstock). <BR>
	 * The stock status and the quantity of stock acquires Consignor Name of correspondence <BR>
	 * Consignor Code from stock Data of one or more in a central stock. <BR>
	 * Return ""when there is no stock control package.
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Consignor Name(Repetition removal)<BR>
	 *   </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Consignor Code    :Acquisition from parameter<BR>
	 *     Stock status:Center stocking<BR>
	 *     Stock qty        :One or more in case of Movement Picking , Exception Picking , and Inventory. <BR>
	 *     Stock information.Area No.=Area Master information.Area No.<BR>
	 *     Area Master information.Area flag =2:Other than ASRS<BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  consignorCode Consignor Code
	 * @param  jobType		 Work type
	 * @return	Consignor Name
	 * @throws NotFoundException  It is notified when Consignor  information is not found.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated.
	 */
	private String getConsignorNameFromStock(String consignorCode, String jobType)
		throws NotFoundException, ReadWriteException, ScheduleException
	{
		AreaOperator areaOperator = new AreaOperator(wConn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM700976
		// Return ""when there is no stock control package.
		SystemParameter.withStockManagement = null;
		if (! SystemParameter.withStockManagement())
		{
			return "";
		}

		StockSearchKey stockSearchKey = new StockSearchKey();
		//#CM700977
		//-----------------
		//#CM700978
		// Set Acquisition item
		//#CM700979
		//-----------------
		//#CM700980
		// Consignor Name
		stockSearchKey.setConsignorNameCollect("DISTINCT");
		//#CM700981
		//-----------------
		//#CM700982
		// Set Search condition
		//#CM700983
		//-----------------
		//#CM700984
		// Consignor Code
		stockSearchKey.setConsignorCode(consignorCode);
		//#CM700985
		// Status flag(Center stocking)
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM700986
		// Stock qty targets only the stock of one or more for Movement Picking , Exception Picking , and Inventory.
		if (jobType.equals(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL)
			|| jobType.equals(SystemDefine.JOB_TYPE_EX_RETRIEVAL)
			|| jobType.equals(SystemDefine.JOB_TYPE_INVENTORY))
		{
			stockSearchKey.setStockQty(0, ">");
		}
		//#CM700987
		// Acquire Area of Other than ASRS, and add to Search condition.
		//#CM700988
		// IS NULL retrieval when there is no correspondence Area
		areaNo = areaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNo);


		//#CM700989
		//-----------------
		//#CM700990
		// Retrieval processing
		//#CM700991
		//-----------------
		StockHandler stockHandler = new StockHandler(wConn);
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);
		//#CM700992
		// When Stock information cannot be acquired, throws NotFoundException.
		if (stock == null || stock.length == 0)
		{
			throw (new NotFoundException());
		}

		return stock[0].getConsignorName();
	}

	//#CM700993
	/**
	 * Acquire the Consignor Name in the condition of specifying it from Work information(dnworkinfo). <BR>
	 * From the data whose Status flag is Stand by and Work start flag is Started,<BR>
	 * Acquire Consignor Name in condition (Work flag,Consignor Code,Work plan date) of specifying it. <BR>
	 * However, exclude it from Search condition when Work plan date is space. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Consignor Name(Repetition removal)<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Work flag      :Acquisition from parameter<BR>
	 *     Consignor Code    :Acquisition from parameter<BR>
	 *     Work plan date    :Acquisition from parameter<BR>
	 *     Status flag    :Stand by<BR>
	 *     Work start flag:Started<BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  jobType Work flag<BR>
	 * <DIR>
	 * <DIR>
	 *                  1:Receiving <BR>
	 *                  2:Storage<BR>
	 *                  3:Picking <BR>
	 *                  4:Sorting<BR>
	 *                  5:Shipping <BR>
	 * </DIR>
	 * </DIR>
	 * @param	jobType		  Work type
	 * @param  workDetails   Detail Work type
	 * @param  consignorCode Consignor Code
	 * @param  planDate      Work plan date
	 * @param	rftNo		  RFT Number
	 * @param	workerCode	  Worker code
	 * @return	Consignor Name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws NotFoundException  It is notified when Consignor  information is not found.
	 */
	private String getConsignorNameFromWorkInfo(
		String jobType,
		String workDetails,
		String consignorCode,
		String planDate,
		String rftNo,
		String workerCode)
		throws ReadWriteException, NotFoundException
	{
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		//#CM700994
		//-----------------
		//#CM700995
		// Set Acquisition item
		//#CM700996
		//-----------------
		//#CM700997
		// Consignor Name
		workInfoSearchKey.setConsignorNameCollect("DISTINCT");

		//#CM700998
		//-----------------
		//#CM700999
		// Set Search condition
		//#CM701000
		//-----------------
		//#CM701001
		// Work flag
		workInfoSearchKey.setJobType(jobType);
		//#CM701002
		// Consignor Code
		workInfoSearchKey.setConsignorCode(consignorCode);
		//#CM701003
		// Do not add it to the condition for the Work plan date space.
		if (planDate.trim().length() > 0)
		{
			workInfoSearchKey.setPlanDate(planDate);
		}
		//#CM701004
		// Status flag
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		workInfoSearchKey.setWorkerCode(workerCode);
		workInfoSearchKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM701005
		// Work start flag
		workInfoSearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (jobType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				//#CM701006
				// Retrieve DC and crossing TC of the unit of the supplier or each item.
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				//#CM701007
				// Retrieve TC for each and every customer
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (jobType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				//#CM701008
				// Retrieve data where order No is set at the order picking.
				workInfoSearchKey.setOrderNo("", "IS NOT NULL");
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				//#CM701009
				// Order No retrieves empty data at the item picking.
				workInfoSearchKey.setOrderNo("", "=");
			}
		}

		//#CM701010
		//-----------------
		//#CM701011
		// Retrieval processing
		//#CM701012
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workInfo =
			(WorkingInformation[]) workInfoHandler.find(workInfoSearchKey);
		//#CM701013
		// When Work information cannot be acquired, throws NotFoundException.
		if (workInfo == null || workInfo.length == 0)
		{
			throw (new NotFoundException());
		}

		return workInfo[0].getConsignorName();
	}

	//#CM701014
	/**
	 * Acquire the Consignor Name in the condition of specifying it from movement Work information(dnmovement). <BR>
	 * Status flag and Work start flag acquires Consignor Name of correspondence <BR>
	 * Consignor Code from movement work Data of Started in the Storage waiting. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Consignor Name(Repetition removal)<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Consignor Code    :Acquisition from parameter<BR>
	 *     Status flag    :Waiting for Storage<BR>
	 *     Work start flag:Started<BR>
	 *     Movement Work information.Area No.=Area Master information.Area No.<BR>
	 *     Area Master information.Area flag =2:Other than ASRS<BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  consignorCode Consignor Code
	 * @return	Consignor Name
	 * @throws NotFoundException  It is notified when Consignor  information is not found.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated.
	 */
	private String getConsignorNameFromMovement(String consignorCode)
		throws NotFoundException, ReadWriteException, ScheduleException
	{
		MovementSearchKey movementSearchKey = new MovementSearchKey();

		AreaOperator AreaOperator = new AreaOperator(wConn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM701015
		//-----------------
		//#CM701016
		// Set Acquisition item
		//#CM701017
		//-----------------
		//#CM701018
		// Consignor Name
		movementSearchKey.setConsignorNameCollect("DISTINCT");

		//#CM701019
		//-----------------
		//#CM701020
		// Set Search condition
		//#CM701021
		//-----------------
		//#CM701022
		// Consignor Code
		movementSearchKey.setConsignorCode(consignorCode);
		//#CM701023
		// Status flag(Waiting for Storage)
		movementSearchKey.setStatusFlag(Movement.STATUSFLAG_UNSTART);
		//#CM701024
		// Work start flag
		movementSearchKey.setBeginningFlag(Movement.BEGINNING_FLAG_STARTED);
		//#CM701025
		// Acquire Area of Other than ASRS, and add to Search condition.
		//#CM701026
		// IS NULL retrieval when there is no correspondence Area
		areaNo = AreaOperator.getAreaNo(areaType);
		movementSearchKey.setAreaNo(areaNo);

		//#CM701027
		//-----------------
		//#CM701028
		// Retrieval processing
		//#CM701029
		//-----------------
		MovementHandler movementHandler = new MovementHandler(wConn);
		Movement[] movement = (Movement[]) movementHandler.find(movementSearchKey);
		//#CM701030
		// When Stock information cannot be acquired, throws NotFoundException.
		if (movement == null || movement.length == 0)
		{
			throw (new NotFoundException());
		}

		return movement[0].getConsignorName();
	}

	//#CM701031
	/**
	 * Acquire the Consignor Name from Inventory Work information(dminventorycheck) by specifying it in the condition. <BR>
	 * The processing flag acquires Consignor Name of correspondence <BR>
	 * Consignor Code from Inventory work Data other than the deletion. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Consignor Name(Repetition removal)<BR>
	 *   </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Consignor Code:Acquisition from parameter<BR>
	 *     Processing flag:Excluding the deletion<BR>
	 *     Inventory Work information.Area No.=Area Master information.Area No.<BR>
	 *     Area Master information.Area flag =2:Other than ASRS<BR>
	 *     </DIR>
	 *   </DIR>
	 * @param  consignorCode Consignor Code
	 * @return		Consignor Name
	 * @throws NotFoundException  It is notified when Consignor  information is not found.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated.
	 */
	private String getConsignorNameFromInventoryCheck(String consignorCode)
		throws NotFoundException, ReadWriteException, ScheduleException
	{
		InventoryCheckSearchKey inventoryCheckSearchKey = new InventoryCheckSearchKey();

		AreaOperator AreaOperator = new AreaOperator(wConn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM701032
		//-----------------
		//#CM701033
		// Set Acquisition item
		//#CM701034
		//-----------------
		//#CM701035
		// Consignor Name
		inventoryCheckSearchKey.setConsignorNameCollect("DISTINCT");
		//#CM701036
		//-----------------
		//#CM701037
		// Set Search condition
		//#CM701038
		//-----------------
		//#CM701039
		// Consignor Code
		inventoryCheckSearchKey.setConsignorCode(consignorCode);
		//#CM701040
		// Status flag(Excluding the deletion)
		inventoryCheckSearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_DELETE, "!=");

		//#CM701041
		// Acquire Area of Other than ASRS, and add to Search condition.
		//#CM701042
		// IS NULL retrieval when there is no correspondence Area
		areaNo = AreaOperator.getAreaNo(areaType);
		if (areaNo != null)
		inventoryCheckSearchKey.setAreaNo(areaNo);

		//#CM701043
		//-----------------
		//#CM701044
		// Retrieval processing
		//#CM701045
		//-----------------
		InventoryCheckHandler inventoryCheckHandler = new InventoryCheckHandler(wConn);
		InventoryCheck[] inventoryCheck = (InventoryCheck[]) inventoryCheckHandler.find(inventoryCheckSearchKey);
		//#CM701046
		// When Stock information cannot be acquired, throws NotFoundException.
		if (inventoryCheck == null || inventoryCheck.length == 0)
		{
			throw (new NotFoundException());
		}

		return inventoryCheck[0].getConsignorName();
	}

}
//#CM701047
//end of class

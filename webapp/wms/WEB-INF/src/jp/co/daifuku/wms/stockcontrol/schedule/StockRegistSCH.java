package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM12011
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM12012
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This class executes the process for adding the Stock. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for adding the stock. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. However, <BR>
 * disable to commit or roll-back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   -Initial Display Process-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>  
 * </DIR>
 * <BR>
 * 2.Process by clicking on the Input button(<CODE>check()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and check for mandatory, overflow and duplication and return the checking results. <BR>
 *   Return true when no corresponding data with the corresponding line No. exists in the inventory information,Return false when condition error occurs or corresponding data exists. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   [Parameter] *Mandatory Input  +additional Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Consignor name <BR>
 *   Item Code* <BR>
 *   Item Name <BR>
 *   Case/Piece division* <BR>
 *   Storage Location* <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Stock Case Qty+ <BR>
 *   stock piece qty+ <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Expiry Date <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   -Worker Code check process-(<CODE>AbstructStockControlSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Check Input Value Process-(<CODE>AbstructStockControlSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Check Overflow.- <BR>
 *   <BR>
 *   -Shelf table check-(<CODE>isAsrsLocation()</CODE>Method)<BR>
 *   <BR>
 *   -Check Count Displayed- <BR>
 *   <BR>
 *   -Duplicate Check- <BR>
 *   <BR>  
 *     <DIR>
 *     Check for duplication using Consignor code, Location No., item code, Case/Piece division, and Expiry date as keys.<BR>
 *     Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam. <BR>
 *     </DIR>
 *   <BR>
 *   -Check Stock Presence- <BR>
 *   <BR>
 *     <DIR>
 *     Search for the data using Consignor code, Location No., item code, stock status ( Center stock), Case/Piece division, and expiry date. <BR>
 *     Check for presence of the inventory information.<BR>
 *     Include expiry date in the search conditions if it is defined in the WmsParam as an item (count) that makes stock unique. <BR>
 *     <BR>
 *     Regard as error if data exists. <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * 3.Process by clicking on Add button(<CODE>startSCH()</CODE>Method) <BR><BR>
 * <BR>
 * <DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process to add the stock. <BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   [Parameter] <BR>
 *   <BR>
 *   Worker Code <BR>
 *   Password <BR>
 *   Consignor code <BR>
 *   Consignor name <BR>
 *   Item Code <BR>
 *   Item Name <BR>
 *   Case/Piece division <BR>
 *   Storage Location <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Stock Case Qty <BR>
 *   stock piece qty <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Expiry Date <BR>
 *   Terminal No. <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   -Worker Code check process-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>
 *   <BR>
 *   -Check process for executing daily update Process-(<CODE>AbstructStorageSCH()</CODE>Class) <BR>
 *   <BR>
 *   -Shelf table check-(<CODE>isAsrsLocation()</CODE>Method)<BR>
 *   <BR>
 *   [Update/Add Process] <BR>
 *   <BR>
 *   -Add or Update Inventory Information Table (DMSTOCK).-<BR>
 *   <BR>
 *   Search for the data using Consignor code, Location No., item code, stock status ( Center stock), Case/Piece division, and expiry date. If the inventory information exist, lock the target record.<BR>
 *   Include expiry date in the search conditions if it is defined in the WmsParam as an item (count) that makes stock unique. <BR>
 *   <BR>
 *   If there is no data<BR>
 *   Add the inventory information based on the contents of the Received parameter.<BR>
 *   <BR>
 *   -Add Sending Result Info Table (DNHOSTSEND)-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>  
 *   <BR>
 *   Add the Sending Result Information based on the contents of the Received parameter.<BR>
 *   <BR>
 *   -Add or update the Worker Result data inquiry table (DNWORKERRESULT).-(<CODE>AbstructStockControlSCH()</CODE>Class)<BR>  
 *   <BR>
 *   Add or update the Worker Result data inquiry based on the Received contents of the parameter.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/14</TD><TD>Y.Kubo</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 05:05:44 $
 * @author  $Author: suresh $
 */
public class StockRegistSCH extends AbstractStockControlSCH
{
	//#CM12013
	//	Class variables -----------------------------------------------
	//#CM12014
	/**
	 * Class Name(Stock Add)
	 */
	public static String PROCESSNAME = "StockRegistSCH";

	//#CM12015
	// Class method --------------------------------------------------
	//#CM12016
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/10 05:05:44 $");
	}
	//#CM12017
	// Constructors --------------------------------------------------
	//#CM12018
	/**
	 * Initialize this class.
	 */
	public StockRegistSCH()
	{
		wMessage = null;
	}

	//#CM12019
	// Public methods ------------------------------------------------
	//#CM12020
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in the inventory information, return the corresponding Consignor Code and Consignor Name.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam <CODE>StockControlParameter</CODE> class instance with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StockControlParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		AreaOperator AreaOperator = new AreaOperator(conn);
		
		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM12021
		// Returned data
		StockControlParameter dispData = new StockControlParameter();

		//#CM12022
		// Work Date (System defined date)
		dispData.setStorageDateString(getWorkDate(conn));
		
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();
		//#CM12023
		// Set Search Condition
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		searchKey.setStockQty(1, ">=");
		//#CM12024
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM12025
		// Search for IS NULL if no corresponding area found.
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		//#CM12026
		// Set Condition for Obtaining
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM12027
			// Search Data			
			searchKey.KeyClear();
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			searchKey.setStockQty(1, ">=");
			//#CM12028
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			//#CM12029
			// Area No
			searchKey.setAreaNo(areaNo);

			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				Stock[] consignor = (Stock[]) stockFinder.getEntities(1);
				
				dispData.setConsignorName(consignor[0].getConsignorName());
				dispData.setConsignorCode(consignor[0].getConsignorCode());
			}
		}
		stockFinder.close();

		return dispData;
	}

	//#CM12030
	/** 
	 * Receive the contents entered via screen as a parameter and <BR>
	 * Check for mandatory, overflow and duplication and return the checking results. <BR>
	 * Return true when no corresponding data with the corresponding line No. exists in the inventory information, <BR>
	 * Regard such a case as an exclusion error where condition error occurs or the corresponding data exists, and return false. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Array of <CODE>StockControlParameter</CODE> class instance with input contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @param inputParams Array of <CODE>StockControlParameter</CODE> class instance with preset area contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
     * @return true: if the input content is proper  false: if the input content is not proper
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams) throws ScheduleException, ReadWriteException
	{
		try
		{
			//#CM12031
			// Contents of the input area
			StockControlParameter param = (StockControlParameter) checkParam;
			//#CM12032
			// Contents of Preset area
			StockControlParameter[] paramlist = (StockControlParameter[]) inputParams;

			//#CM12033
			// Check the Worker code and password
			if (!checkWorker(conn, param))
			{
				 return false;
			}
			
			String casepieceflag = param.getCasePieceFlag();
			int enteringqty = param.getEnteringQty();
			int caseqty = param.getStockCaseQty();
			int pieceqty = param.getStockPieceQty();
			String locationno = param.getLocationNo();
									
			//#CM12034
			// Check Input Value
			if (!stockInputCheck(casepieceflag, enteringqty, caseqty, pieceqty))
			{
				return false;
			}
			
			//#CM12035
			//Check Location.
			if (!StringUtil.isBlank(locationno))
			{
				LocateOperator locateOperator = new LocateOperator(conn);
				if (locateOperator.isAsrsLocation(locationno))
				{
					//#CM12036
					// 6023442=The specified location is in automatic warehouse. Cannot enter.
					wMessage = "6023442";
					return false;
				}
			}

			//#CM12037
			// Check Overflow.
			long inputqty = (long)param.getStockCaseQty() * (long)param.getEnteringQty() + (long)param.getStockPieceQty();
			if (inputqty > WmsParam.MAX_STOCK_QTY)
			{
				//#CM12038
				// 6023058 = Please enter {1} or smaller for {0}by checking with Stock Qty(Total Bulk Qty)
				wMessage = "6023058" + wDelim + DisplayText.getText("LBL-W0378") + wDelim + MAX_STOCK_QTY_DISP;
				return false;
			}
			
			//#CM12039
			// Check Count Displayed
			if (paramlist != null && paramlist.length + 1 > WmsParam.MAX_NUMBER_OF_DISP)
			{
				//#CM12040
				// 6023096 = More than {0} data exist. Data cannot be entered.
				wMessage = "6023096" + wDelim + WmsParam.MAX_NUMBER_OF_DISP;
				return false;
			}

			//#CM12041
			// Duplicate Check
			//#CM12042
			// Check for duplication using Consignor code, Location No., item code, Case/Piece division, and Expiry date as keys.
			//#CM12043
			// Include the Expiry date as a duplication check key if it is defined as an item to make the stock unique in WmsParam.
			if (paramlist != null)
			{
				for (int i = 0; i < paramlist.length; i++)
				{
					//#CM12044
					// Expiry Date control is disabled
					if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode()) &&
							paramlist[i].getLocationNo().equals(param.getLocationNo()) &&
							paramlist[i].getItemCode().equals(param.getItemCode()) &&
							paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()) &&
							paramlist[i].getUseByDate().equals(param.getUseByDate()))
						{
							//#CM12045
							// 6023090 = The data already exists.
							wMessage = "6023090";
							return false;
						}
					}
					//#CM12046
					// Expiry date control is disabled
					else
					{
						if (paramlist[i].getConsignorCode().equals(param.getConsignorCode()) &&
							paramlist[i].getLocationNo().equals(param.getLocationNo()) &&
							paramlist[i].getItemCode().equals(param.getItemCode()) &&
							paramlist[i].getCasePieceFlag().equals(param.getCasePieceFlag()))
						{
							//#CM12047
							// 6023090 = The data already exists.
							wMessage = "6023090";
							return false;
						}
					}
				}
			}
			
			//#CM12048
			// Disable to add any data if the same stock already resides. (Disable to replenish for Stock Add)
			if(!duplicationDBCheck(conn, param))
			{
				//#CM12049
				// 6023292 = Cannot enter. Inventory data already exists.
				wMessage = "6023292";
				return false;
			}
		}
		catch (ReadWriteException e)
		{
			throw new ScheduleException(e.getMessage());
		}

		//#CM12050
		// 6001019=Entry was accepted.
		wMessage = "6001019";
		return true;
	}

	//#CM12051
	/**
	 * Receive the contents entered via screen as a parameter and start the Add schedule of the stock.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StockControlParameter</CODE> class instance with commitment contents. <BR>
	 *        Designating any instance other than StockControlParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{							
			//#CM12052
			// Check the Worker code and password
			StockControlParameter workparam = (StockControlParameter)startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return false;
			}
			
			//#CM12053
			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return false;
			}
			
			StockControlParameter[] param = (StockControlParameter[]) startParams;

			if (param != null)
			{
				
				//#CM12054
				// Check Automated Warehouse.
				for (int i = 0; i < param.length; i++)
				{

					LocateOperator locateOperator = new LocateOperator(conn);

					//#CM12055
					// Location
					String locationno = param[i].getLocationNo();
					
					//#CM12056
					// Check Location.
					if (!StringUtil.isBlank(locationno))
					{
						if (locateOperator.isAsrsLocation(locationno))
						{
							//#CM12057
							// 6023443=No.{0} The specified location is in automatic warehouse. Cannot enter.
							wMessage = "6023443" + wDelim + param[i].getRowNo();
							return false;
						}
					}
				}

				//#CM12058
				// Worker Code
				String workercode = param[0].getWorkerCode();
				//#CM12059
				// Worker Name
				String workername = getWorkerName(conn, workercode); 
				
				//#CM12060
				// Work Date ( System defined date)
				String sysdate = getWorkDate(conn);
								
				//#CM12061
				// Terminal No.
				String terminalno = param[0].getTerminalNumber();
				//#CM12062
				// Work division(for maintenance increasing)
				String jobtype = Stock.JOB_TYPE_MAINTENANCE_PLUS;
				//#CM12063
				// Obtain each unique key to add.
				SequenceHandler sequence = new SequenceHandler(conn);
				//#CM12064
				// Batch No.(Common for one commitment)
				String batch_seqno = sequence.nextNoPlanBatchNo();
				//#CM12065
				// TC/DC division (Set the DC)
				String tcdckbn = HostSend.TCDC_FLAG_DC;
								
				int workqty = 0;
				
				try
				{
					//#CM12066
					// Lock Process(inventory information)
					lockStockData(conn, (StockControlParameter[])startParams);		
					//#CM12067
					// Lock process ( location info)
					lockLocateData(conn, (StockControlParameter[])startParams);		
				}
				catch(LockTimeOutException e)
				{
					//#CM12068
					// 6027008 = This data is not treatable because it is updating it with other terminals.
					wMessage = "6027008";
					return false;
				}
				
				for (int i = 0; i < param.length; i++)
				{
					//#CM12069
					// Stock Qty
					int inputqty = param[i].getStockCaseQty() * param[i].getEnteringQty() + param[i].getStockPieceQty();
					
					//#CM12070
					// Disable to add if the same stock was already added (Disable to replenish for Stock Add, Update the process, however, for stock qty equal to 0)
					if(!duplicationDBCheck(conn, param[i]))
					{
						//#CM12071
						// 6023273=No.{0} {1}
						//#CM12072
						// 6007007=The data already exists.
						wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6007007");
						return false;
					}
					
					//#CM12073
					// Work Quantity (Totalize the result qty of Work status for one commitment)
					workqty = addWorkQty(workqty, inputqty);

					String newstockid = "";
					try
					{
						newstockid = processStockData(conn, param[i], inputqty);
					}
					catch(DataExistsException e)
					{
						//#CM12074
						// 6023209 = No.{0} The data has been updated via other terminal.
						wMessage = "6023209" + wDelim + param[i].getRowNo();
						return false;
					}
					//#CM12075
					// Update the Location Info with Completed Storage.
					LocateOperator lOperator = new LocateOperator(conn);
					lOperator.modifyLocateStatus(param[i].getLocationNo(), PROCESSNAME);
					
					//#CM12076
					// Set the storage location area.
					param[i].setAreaNo(lOperator.getAreaNo(param[i].getLocationNo()));
					
					//#CM12077
					// Add Sending Result Info Table (DNHOSTSEND)
					if(!createHostsend(conn, param[i], newstockid, workercode, workername, sysdate, terminalno, jobtype, PROCESSNAME, batch_seqno, tcdckbn, inputqty))
					{
						return false;
					}
										
				}
				
				//#CM12078
				// Add or update the Worker Result data inquiry table (DNWORKERRESULT).
				//#CM12079
				// Add or update the Worker Result data inquiry, after completed adding or updating the inventory information table for one commitment.
				updateWorkerResult(conn, workercode, workername, sysdate, terminalno, jobtype, workqty);

				//#CM12080
				// 6001003 = Added.
				wMessage = "6001003";
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM12081
	// Package methods -----------------------------------------------

	//#CM12082
	// Protected methods ---------------------------------------------
	//#CM12083
	/**
	 * Add or Update the Inventory Information table
	 * @param  conn        Instance to maintain database connection.
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @param  inputqty    Storage qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 * @throws DataExistsException Announce this if the same Information has been already added when trying to add a relocation information.
	 * @return Stock ID
	 */
	private String processStockData(Connection conn, StockControlParameter param, int inputqty) 
		throws ReadWriteException, NoPrimaryException, DataExistsException, ScheduleException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
	
		//#CM12084
		// Search Data
		//#CM12085
		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCode());
		//#CM12086
		// Location No.
		searchKey.setLocationNo(param.getLocationNo());
		//#CM12087
		// Item Code
		searchKey.setItemCode(param.getItemCode());
		//#CM12088
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM12089
		// Stock qty is not more than 0.
		searchKey.setStockQty(0, "<=");
		//#CM12090
		// Case/Piece division(Load Size)
		//#CM12091
		// None
		if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		//#CM12092
		// Case
		else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		//#CM12093
		// Piece
		else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		//#CM12094
		// If the search conditions for inventory information include expiry date,
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			if (!StringUtil.isBlank(param.getUseByDate()))
			{
				searchKey.setUseByDate(param.getUseByDate());
			}
			else
			{
				searchKey.setUseByDate("", "");
			}
		}
		
		//#CM12095
		// Lock Data.
		Stock stock = (Stock)stockHandler.findPrimaryForUpdate(searchKey);
		
		//#CM12096
		// Stock ID
		String stockid = "";
		//#CM12097
		// If there is no stock data
		if(stock == null)
		{
			//#CM12098
			// Add Inventory Information Table (DMSTOCK).
			stockid = createStock(conn, param, inputqty);
		}
		else
		{
			//#CM12099
			// Update Inventory Information Table (DMSTOCK).
			stockid = stock.getStockId();
			updateStock(conn, stockid, param, inputqty);
		}
		return stockid;
	}
	
	//#CM12100
	/**
	 * Add the inventory information table.
	 * @param  conn        Instance to maintain database connection.
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @param  inputqty    Storage qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 * @throws DataExistsException Announce this if the same Information has been already added when trying to add a relocation information.
	 * @return Stock ID
	 */
	protected String createStock(Connection conn, StockControlParameter param, int inputqty)
		throws ReadWriteException, ScheduleException, DataExistsException
	{
		try
		{
			StockHandler stockHandler = new StockHandler(conn);
			Stock stock = new Stock();

			//#CM12101
			// Obtain each unique key to add.
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM12102
			// Stock ID
			String stockId_seqno = sequence.nextStockId();
			stock.setStockId(stockId_seqno);
			//#CM12103
			// Plan unique key
			stock.setPlanUkey("");
			//#CM12104
			// Area No.
			//#CM12105
			// Obtain the Area No. from the location info.
			LocateOperator lOperator = new LocateOperator(conn);
			stock.setAreaNo(lOperator.getAreaNo(param.getLocationNo()));
			//#CM12106
			// Location No.
			stock.setLocationNo(param.getLocationNo());
			//#CM12107
			// Item Code
			stock.setItemCode(param.getItemCode());
			//#CM12108
			// Item Name
			stock.setItemName1(param.getItemName());
			//#CM12109
			// Stock status(Center Inventory)
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM12110
			// Stock Qty
			stock.setStockQty(inputqty);
			//#CM12111
			// allocated qty
			stock.setAllocationQty(inputqty);
			//#CM12112
			// Storage Planned Qty
			stock.setPlanQty(0);
			//#CM12113
			// Case/Piece division(Load Size)
			//#CM12114
			// None
			if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM12115
			// Case
			else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			//#CM12116
			// Piece
			else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				stock.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
		
			//#CM12117
			// Storage Date/Time
			stock.setInstockDate(param.getStorageDate());
			//#CM12118
			// Last Picking Date
			stock.setLastShippingDate("");
			//#CM12119
			// Expiry Date
			stock.setUseByDate(param.getUseByDate());
			//#CM12120
			// Lot No.
			stock.setLotNo("");
			//#CM12121
			// Plan information Comment
			stock.setPlanInformation("");
			//#CM12122
			// Consignor code
			stock.setConsignorCode(param.getConsignorCode());
			//#CM12123
			// Consignor name
			stock.setConsignorName(param.getConsignorName());
			//#CM12124
			// Supplier Code
			stock.setSupplierCode("");
			//#CM12125
			// Supplier Name
			stock.setSupplierName1("");
			//#CM12126
			// Packed qty per Case
			stock.setEnteringQty(param.getEnteringQty());
			//#CM12127
			// Packed qty per bundle
			stock.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM12128
			// Case ITF
			stock.setItf(param.getITF());
			//#CM12129
			// Bundle ITF
			stock.setBundleItf(param.getBundleITF());
			//#CM12130
			// Name of Add Process
			stock.setRegistPname(PROCESSNAME);
			//#CM12131
			// Last update process name
			stock.setLastUpdatePname(PROCESSNAME);

			//#CM12132
			// Data addition
			stockHandler.create(stock);

			return stockId_seqno;
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM12133
	/**
	 * Update stock table (DMSTOCK). <BR>
	 * @param  conn        Instance to maintain database connection.
	 * @param  stockid     Stock ID
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @param  inputqty    Storage qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	protected void updateStock(Connection conn, String stockid, StockControlParameter param, int inputqty)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM12134
			// Generate inventory info instance of handlers.
			StockAlterKey stockAlterKey = new StockAlterKey();
			StockHandler stockObj = new StockHandler( conn );

			stockAlterKey.KeyClear();
			//#CM12135
			// Update using stock ID.
			stockAlterKey.setStockId(stockid);
			//#CM12136
			// Item Name
			stockAlterKey.updateItemName1(param.getItemName());
			//#CM12137
			// Stock Qty
			stockAlterKey.updateStockQty(inputqty);
			//#CM12138
			// Allocatable Qty
			stockAlterKey.updateAllocationQty(inputqty);
			//#CM12139
			// Storage Date
			stockAlterKey.updateInstockDate(param.getStorageDate());
			//#CM12140
			// Expiry Date
			stockAlterKey.updateUseByDate(param.getUseByDate());
			//#CM12141
			// Consignor name
			stockAlterKey.updateConsignorName(param.getConsignorName());
			//#CM12142
			// Case/Piece division
			stockAlterKey.updateCasePieceFlag(param.getCasePieceFlag());
			//#CM12143
			// Packed qty per Case
			stockAlterKey.updateEnteringQty(param.getEnteringQty());
			//#CM12144
			// Packed qty per bundle
			stockAlterKey.updateBundleEnteringQty(param.getBundleEnteringQty());
			//#CM12145
			// Case ITF
			stockAlterKey.updateItf(param.getITF());
			//#CM12146
			// Bundle ITF
			stockAlterKey.updateBundleItf(param.getBundleITF());
			//#CM12147
			// Last update process name
			stockAlterKey.updateLastUpdatePname(PROCESSNAME);
			
			stockObj.modify(stockAlterKey);
		}
		catch (NotFoundException e)
		{
			//#CM12148
			// Close the process when error via other terminal occurs.
			//#CM12149
			// 6023209 = No.{0} The data has been updated via other terminal.
			wMessage = "6023209" + wDelim + param.getRowNo();
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM12150
	/**
	 * Lock the inventory information to be updated.
	 * Generate array of Stock ID from the array of parameter designated by argument.
	 * Lock the corresponding inventory information.
	 * <DIR>
	 *    (search conditions)
	 *    <UL>
	 *     <LI>Consignor code</LI>
	 *     <LI>Location No.</LI>
	 *     <LI>Item Code</LI>
	 *     <LI>Case/Piece division</LI>
	 *     <LI>Stock status[Center Inventory]</LI>
	 *     <LI>Expiry Date(Expiry Date control is disabled)</LI>
	 *    </UL>
	 * </DIR>
	 * 
	 * @param  conn        Instance to maintain database connection.
	 * @param  param       StockControlParameter class instance with contents that were input via screen.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws LockTimeOutException Announce when database is unlocked for the specified time.
	 * @throws NoPrimaryException Announce when the defined information is abnormal.
	 */
	protected void lockStockData(Connection conn, StockControlParameter[] param)
		throws NoPrimaryException, ReadWriteException,LockTimeOutException
	{
		String[] stockIdList = null;
		Vector vec = new Vector();
		
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
		
		//#CM12151
		// Obtain the corresponding Stock ID from the inventory information if any input info via screen already reside.
		for (int i = 0; i < param.length; i ++)
		{			
			searchKey.KeyClear();
			
			setStockSearchKey(param[i], searchKey);
			//#CM12152
			// Stock qty is not more than 0.
			searchKey.setStockQty(0, "<=");

			//#CM12153
			// Search Existing Info.
			Stock stock = (Stock)stockHandler.findPrimary(searchKey);
			
			//#CM12154
			// Reserve in Vector
			if (stock != null)
			{
				vec.addElement(stock.getStockId());
			}
		}
		
		//#CM12155
		// If any existing inventory information resides,
		if (vec.size() > 0 )
		{
			stockIdList = new String[vec.size()];
			vec.copyInto(stockIdList);
		
			searchKey.KeyClear();
			searchKey.setStockId(stockIdList);
		
			//#CM12156
			// Lock all the inventory information reserved in Vector.
			stockHandler.findForUpdateNowait(searchKey);
		}
	}
	
   //#CM12157
   /**
	* Lock the location info to be updated.
	* Generate array of location No. from the array of parameter designated by argument.
	* Lock the corresponding location info.
	* <DIR>
	*    (search conditions)
	*    <UL>
	*     <LI>Location No</LI>
	*    </UL>
	* </DIR>
	* 
	* @param  conn        Instance to maintain database connection.
	* @param  param       StockControlParameter class instance with contents that were input via screen.
	* @throws ReadWriteException Announce when error occurs on the database connection.
	* @throws LockTimeOutException Announce when database is unlocked for the specified time.
    * @throws NoPrimaryException Announce when the defined information is abnormal.
	*/
   protected void lockLocateData(Connection conn, StockControlParameter[] param)
	   throws NoPrimaryException, ReadWriteException,LockTimeOutException
   {
		String[] LocationNoList = null;
		Vector vec2 = new Vector();
		String[] AreaNoList = null;
		Vector vec3 = new Vector();
		
		LocateHandler locateHandler = new LocateHandler(conn);
		LocateSearchKey locatesearchkey = new LocateSearchKey();
		
		//#CM12158
		// Obtain the relocation rack area No. from WmsParam.
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
	   //#CM12159
	   // Obtain the corresponding Stock ID from the inventory information if any input info via screen already reside.
	   for (int i = 0; i < param.length; i ++)
	   {			
		   locatesearchkey.KeyClear();
			
		   //#CM12160
		   // Search Data
		   if (!param[i].getLocationNo().equals(""))
		   {
			   //#CM12161
			   // Area No.Location No.
			   locatesearchkey.setAreaNo(w_AreaNo);
			   locatesearchkey.setLocationNo(param[i].getLocationNo());
			
			   //#CM12162
			   // Search Existing Location Info.
			   locatesearchkey.setAreaNo(w_AreaNo);
			   Locate locate = (Locate)locateHandler.findPrimary(locatesearchkey);
			
			   //#CM12163
			   // Reserve in Vector
			   if (locate != null)
			   {
				   vec2.addElement(locate.getLocationNo());				
			   }
		   }
	   }
		
	   //#CM12164
	   // If any existing location info resides,
	   if (vec2.size() > 0 )
	   {
		   LocationNoList = new String[vec2.size()];
		   vec2.copyInto(LocationNoList);
		   AreaNoList = new String[vec2.size()];
		   vec3.copyInto(AreaNoList);
		
		   locatesearchkey.KeyClear();
		   locatesearchkey.setAreaNo(AreaNoList);
		   locatesearchkey.setLocationNo(LocationNoList);
		
		   //#CM12165
		   // Lock all the location info reserved in Vector.
		   locateHandler.findForUpdateNowait(locatesearchkey);
	   }
   }

	//#CM12166
	// Private methods -----------------------------------------------
	//#CM12167
	/**
	 * Check for any same stock data.<BR>
	 * @param conn Instance to maintain database connection.<BR>
	 * @param param search conditions maintaining<CODE>StockControlParameter</CODE><BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
     * @return True if there is no stock data and false wen exists
	 */
	protected boolean duplicationDBCheck(Connection conn, StockControlParameter param) throws ReadWriteException
	{
		StockHandler stockHandler = new StockHandler(conn);
		StockSearchKey searchKey = new StockSearchKey();
			
		setStockSearchKey(param, searchKey);
		
		//#CM12168
		// Stock qty is 1 or larger
		searchKey.setStockQty(1, ">=");
		
		//#CM12169
		// If stock data exists,
		if(stockHandler.count(searchKey) > 0)
		{
			return false;
		}
		return true;
	}
	
	//#CM12170
	/**
	 * Set the search conditions.<BR>
	 * @param param Search conditions maintaining <CODE>StockControlParameter</CODE><BR>
	 * @param searchKey Search Key<BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void setStockSearchKey(StockControlParameter param, StockSearchKey searchKey)
		throws ReadWriteException
	{
		//#CM12171
		// Search Data
		//#CM12172
		// Consignor code
		searchKey.setConsignorCode(param.getConsignorCode());
		//#CM12173
		// Location No.
		searchKey.setLocationNo(param.getLocationNo());
		//#CM12174
		// Item Code
		searchKey.setItemCode(param.getItemCode());
		//#CM12175
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM12176
		// Case/Piece division(Load Size)
		//#CM12177
		// None
		if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
		}
		//#CM12178
		// Case
		else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
		}
		//#CM12179
		// Piece
		else if(param.getCasePieceFlag().equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
		}
		//#CM12180
		// If the search conditions for inventory information include expiry date,
		if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
		{
			if (!StringUtil.isBlank(param.getUseByDate()))
			{
				searchKey.setUseByDate(param.getUseByDate());
			}
			else
			{
				searchKey.setUseByDate("" , "");
			}
		}
	}
}
//#CM12181
//end of class

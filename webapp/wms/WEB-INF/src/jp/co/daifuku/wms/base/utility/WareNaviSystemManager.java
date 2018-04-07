package jp.co.daifuku.wms.base.utility;

//#CM687007
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM687008
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * <DIR>
 *   Fetch WareNaviSystem info and update<BR>
 *   WareNaviSystem table is fetched during instance generation.
 *   If required search using <code>getWareNaviSystem</code> method is possible<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/07/25</TD><TD>Y.Okamura</TD><TD>v2.4 New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 07:13:37 $
 * @author  $Author: suresh $
 */
public class WareNaviSystemManager 
{
	//#CM687009
	// Class variables -----------------------------------------------
	//#CM687010
	/**
	 * connection
	 */
	private Connection wConn = null;
	
	//#CM687011
	/**
	 * WareNaviSystem object
	 */
	private WareNaviSystem wWms = null;
	
	//#CM687012
	/**
	 * delimiter
	 */
	protected String wDelim = MessageResource.DELIM;
	
	//#CM687013
	// Class method --------------------------------------------------
	//#CM687014
	/**
	 * Return the version of this class
	 * @return version and timestamp
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 07:13:37 $");
	}
	
	//#CM687015
	// Constructors --------------------------------------------------
	//#CM687016
	/**
	 * Initialize this class<BR>
	 * This constructor searches the WareNavi System table<BR>
	 * 
	 * @param conn database connection object
	 * @throws ReadWriteException if abnormal error occurs in database access
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public WareNaviSystemManager(Connection conn) throws ReadWriteException, ScheduleException
	{
		wConn = conn;
		
		//#CM687017
		// fetch WareNaviSystem
		getWareNaviSystemInstance();
		
	}

	//#CM687018
	// Public methods ------------------------------------------------
	//#CM687019
	/**
	 * Search again WareNaviSystem table
	 * @throws ReadWriteException if abnormal error occurs in database access
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public void getWareNaviSystem() throws ReadWriteException, ScheduleException
	{
		getWareNaviSystemInstance();
	}
	//#CM687020
	/**
	 * Search again WareNaviSystem table<BR>
	 * Change the connection stored in this class<BR>
	 * @param conn database connection object
	 * @throws ReadWriteException if abnormal error occurs in database access
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	public void getWareNaviSystem(Connection conn) throws ReadWriteException, ScheduleException
	{
		wConn = conn;
		getWareNaviSystemInstance();
	}
	
	//#CM687021
	/**
	 * Fetch system no.
	 * 
	 * @return system no.
	 */
	public int getSystemNo()
	{
		return wWms.getSystemNo();
	}
	
	//#CM687022
	/**
	 * fetch work date
	 * 
	 * @return work date
	 */
	public String getWorkDate()
	{
		return wWms.getWorkDate();
	}
	
	//#CM687023
	/**
	 * Fetch whether DC operation or not
	 * 
	 * @return true:DC operation false:No DC operation
	 */
	public boolean isDcOperation()
	{
		return isIntroducedOperation(wWms.getDcOperation());
	}
	
	//#CM687024
	/**
	 * Fetch whether TC operation or not
	 * 
	 * @return true:TC operation  false:No TC operation
	 */
	public boolean isTcOperation()
	{
		return isIntroducedOperation(wWms.getTcOperation());
	}

	//#CM687025
	/**
	 * Fetch whether Crossdock operation or not
	 * 
	 * @return true: Crossdoc operation false:No Crossdoc operation
	 */
	public boolean isCrossDockOperation()
	{
		return isIntroducedOperation(wWms.getCrossdockOperation());
	}
	
	//#CM687026
	/**
	 * fetch result hold period
	 * 
	 * @return result hold period
	 */
	public int getResultHoldPeriod()
	{
		return wWms.getResultHoldPeriod();
	}
	
	//#CM687027
	/**
	 * Fetch plan info hold period
	 * 
	 * @return plan info hold period
	 */
	public int getPlanHoldPeriod()
	{
		return wWms.getPlanHoldPeriod();
	}
	
	//#CM687028
	/**
	 * Check whether Receiving package is available or not
	 * 
	 * @return true: Receiving package available false:not available
	 */
	public boolean isInstockPack()
	{
		return isIntroducedPackage(wWms.getInstockPack());
	}
	
	//#CM687029
	/**
	 * Check whether Storage package is available or not
	 * 
	 * @return true:Storage package available false:not available
	 */
	public boolean isStoragePack()
	{
		return isIntroducedPackage(wWms.getStoragePack());
	}
	
	//#CM687030
	/**
	 * Check whether Picking package is available or not
	 * 
	 * @return true:Picking package available false:not available
	 */
	public boolean isRetrievalPack()
	{
		return isIntroducedPackage(wWms.getRetrievalPack());
	}
	
	//#CM687031
	/**
	 * Check whether Sorting package is available or not
	 * 
	 * @return true:Sorting package available false:not available
	 */
	public boolean isSortingPack()
	{
		return isIntroducedPackage(wWms.getSortingPack());
	}
	
	//#CM687032
	/**
	 * Check whether Shipping package is available or not
	 * 
	 * @return true:Shipping package available false:not available
	 */
	public boolean isShippingPack()
	{
		return isIntroducedPackage(wWms.getShippingPack());
	}
	
	//#CM687033
	/**
	 * Check whether Inventory package is available or not
	 * 
	 * @return true:Inventory package available false:not available
	 */
	public boolean isStockPack()
	{
		return isIntroducedPackage(wWms.getStockPack());
	}
	
	//#CM687034
	/**
	 * Check whether Crossdoc package is available or not
	 * 
	 * @return true:Crossdoc package available false:not available
	 */
	public boolean isCrossDockPack()
	{
		return isIntroducedPackage(wWms.getCrossdockPack());
	}
	
	//#CM687035
	/**
	 * Check whether Relocation package is available or not
	 * 
	 * @return true:Relocation package available false:not available
	 */
	public boolean isIdmPack()
	{
		return isIntroducedPackage(wWms.getIdmPack());
	}
	
	//#CM687036
	/**
	 * Fetch whether daily update is underway or not
	 * 
	 * @return true:daily update in process false:daily update not in process
	 */
	public boolean isDailyUpdate()
	{
		if (WareNaviSystem.DAILYUPDATE_LOADING.equals(wWms.getDailyUpdate()))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	//#CM687037
	/**
	 * Fetch whether plan data load is underway or not
	 * 
	 * @return true:plan data loading in process false:no plan data loading in process
	 */
	public boolean isLoadData()
	{
		if (WareNaviSystem.LOADDATA_LOADING.equals(wWms.getLoadData()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//#CM687038
	/**
	 * Fetch whether report data creation is underway or not
	 * 
	 * @return true:report data creation in process false:report data creation not in process
	 */
	public boolean isReportData()
	{
		if (WareNaviSystem.REPORTDATA_LOADING.equals(wWms.getReportData()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//#CM687039
	/**
	 * Update daily update in process flag<BR>
	 * Depending on the parameter, change the flag to in process or terminate<BR>
	 * 
	 * @param isLoad true:in process false:terminate
	 * @throws ReadWriteException if abnormal error occurs in database access
	 */
	public void updateLoadDataFlag(boolean isLoad) throws ReadWriteException
	{
		try
		{
			WareNaviSystemAlterKey wmsAltKey = new WareNaviSystemAlterKey();
			WareNaviSystemHandler wmsHandler = new WareNaviSystemHandler(wConn);
			//#CM687040
			// If WHERE condition is not set while using AlterKey
			//#CM687041
			// exception will occur. so set SystemNo in the search condition
			wmsAltKey.setSystemNo(WareNaviSystem.SYSTEM_NO);
			if (isLoad)
			{
				wmsAltKey.updateLoadData(WareNaviSystem.LOADDATA_LOADING);
			}
			else
			{
				wmsAltKey.updateLoadData(WareNaviSystem.LOADDATA_STOP);
			}
			
			wmsHandler.modify(wmsAltKey);
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
	}
	
	//#CM687042
	// Package methods -----------------------------------------------

	//#CM687043
	// Protected methods ---------------------------------------------
	
	//#CM687044
	// Private methods -----------------------------------------------
	//#CM687045
	/**
	 * Seearch WareNaviSystem table
	 * 
	 * @throws ReadWriteException if abnormal error occurs in database access
	 * @throws ScheduleException if unexpected error occurs in check process
	 */
	private void getWareNaviSystemInstance() throws ReadWriteException, ScheduleException
	{
		WareNaviSystemHandler wmsHandle = new WareNaviSystemHandler(wConn);
		WareNaviSystemSearchKey wmsKey = new WareNaviSystemSearchKey();
		
		try
		{
			wWms = (WareNaviSystem) wmsHandle.findPrimary(wmsKey);
			if (wWms == null)
			{
				//#CM687046
				// 6006039=Failed to search for {0}.
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, "WARENAVI_SYSTEM", null);
				//#CM687047
				// 6007039={0} search failed. See log.
				throw new ScheduleException("6007039" + wDelim + "WARENAVI_SYSTEM");
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		
	}
	
	//#CM687048
	/**
	 * Return whether the package supplied in the parameter is included or not
	 * 
	 * @param checkPackage package to be checked
	 * @return true:included false:not available
	 */
	private boolean isIntroducedPackage(String checkPackage)
	{
		if (WareNaviSystem.PACKAGE_FLAG_ADDON.equals(checkPackage))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	//#CM687049
	/**
	 * Return whether the operation supplied in the parameter is included or not
	 * 
	 * @param checkOperation operation to be checked
	 * @return true:included false:not available
	 */
	private boolean isIntroducedOperation(String checkOperation)
	{
		if (WareNaviSystem.OPERATION_FLAG_ADDON.equals(checkOperation))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	
	
}
//#CM687050
//end of class

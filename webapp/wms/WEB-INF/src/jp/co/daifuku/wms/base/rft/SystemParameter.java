// $Id: SystemParameter.java,v 1.2 2006/11/14 06:09:21 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM702513
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702514
/**
 * Class to acquire setting concerning system of eWareNavi. 
 * Use it without generating the instance. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:21 $
 * @author  $Author: suresh $
 */
public class SystemParameter
{
	//#CM702515
	// Class fields --------------------------------------------------
    private static final String CLASS_NAME = "SystemParameter";

    //#CM702516
    /**
     * Value of key to decide whether to consolidate work in RFT and to send it
     */
    protected static final boolean[] IsCollectKey = {
    	WmsParam.INSTOCK_JOBCOLLECT,
    	WmsParam.STORAGE_JOBCOLLECT,
    	WmsParam.RETRIEVAL_JOBCOLLECT,
    	WmsParam.SORTING_JOBCOLLECT,
    	WmsParam.SHIPPING_JOBCOLLECT
    };

    //#CM702517
    /**
     * Value of key to decide whether to consolidate it with Item Code only when work 
     * is consolidated in RFT and it sends it
     */
    protected static final String[] IsCollectItemCodeOnlyKey = {
    	Integer.toString(WmsParam.INSTOCK_JOBCOLLECT_KEY),
    	Integer.toString(WmsParam.STORAGE_JOBCOLLECT_KEY),
    	Integer.toString(WmsParam.RETRIEVAL_JOBCOLLECT_KEY),
    	"",
    	Integer.toString(WmsParam.SHIPPING_JOBCOLLECT_KEY)
    };
    
    //#CM702518
    /**
     * Item Code only
     */
    private static final String ITEM_CODE_ONLY = "1";

    //#CM702519
    /**
     * Work type : Receiving 
     */
    public static final int JOB_TYPE_INSTOCK = 0;
    //#CM702520
    /**
     * Work type : Storage
     */
    public static final int JOB_TYPE_STORAGE = 1;
    //#CM702521
    /**
     * Work type : Picking 
     */
    public static final int JOB_TYPE_RETRIEVAL = 2;
    //#CM702522
    /**
     * Work type : Sorting
     */
    public static final int JOB_TYPE_SORTING = 3;
    //#CM702523
    /**
     * Work type : Shipping 
     */
    public static final int JOB_TYPE_SHIPPING = 4;

	//#CM702524
	/**
	 * For overflow check of Storage qty and quantity of stock
	 */
	public static final int MAXSTOCKQTY = WmsParam.MAX_STOCK_QTY;

	//#CM702525
	/**
	 * For overflow check of Storage qty and quantity of stock(one that comma was edited for screen display)
	 */
	public static final String DISPMAXSTOCKQTY = WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY);

	//#CM702526
	/**
	 * The maximum number of lines of FTP file(When Hit is done voluminously, the file making is done by this number of cases. )
	 */
	public static final int FTP_FILE_MAX_RECORD = WmsParam.RFT_FTP_FILE_MAX_RECORD;
	
	//#CM702527
	// Class variables -----------------------------------------------
    //#CM702528
    /**
     * Connection
     */
    protected static Connection wConn = null;
    //#CM702529
    /**
     * Stock package is available or not?
     */
	protected static Boolean withStockManagement= null;
    //#CM702530
    /**
     * Crossdock package is available or not?
     */
	protected static Boolean withCrossDocManagement= null;
    //#CM702531
    /**
     * Expiry date available or not?
     */
	protected static Boolean withUseByDateManagement= null;
    //#CM702532
    /**
     * Whether consolidate work in RFT or not?
     */
	protected static boolean[] isRftWorkCollect= null;
    //#CM702533
    /**
     * Whether the key when work is consolidated in RFT is only Item Code or not?
     */
	protected static boolean[] isRftWorkCollectItemCodeOnly = null;
	
	//#CM702534
	// Class method --------------------------------------------------
	//#CM702535
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:21 $";
	}

	//#CM702536
	// Constructors --------------------------------------------------
	//#CM702537
	/**
	 * Constructor.<BR>
	 * The constructor is declared with private so that this class may use 
	 * it without generating the instance. 
	 */
	private SystemParameter()
	{
	}

	//#CM702538
	// Public methods ------------------------------------------------
	//#CM702539
	/**
	 * Set <code>Connection</code> for the Data base connection. 
	 * @param c Database connection
	 */
	public static void setConnection(Connection c)
	{
	    wConn = c;
	}
	
	//#CM702540
	/**
	 * Decide if there is a stock control or not.
	 * @return		Return true when there is a stock control. 
	 * 				Otherwise, return false. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public static boolean withStockManagement() throws ReadWriteException
	{
	    if (withStockManagement == null)
	    {
	        //#CM702541
	        // Set it if the value is not set. 
			WareNaviSystemHandler wnSystemHandler = new WareNaviSystemHandler(wConn);
			WareNaviSystemSearchKey wnSystemSearchKey = new WareNaviSystemSearchKey();
			WareNaviSystem[] wnSystem = (WareNaviSystem[])wnSystemHandler.find(wnSystemSearchKey);

			//#CM702542
			// When system definition information cannot be acquired, throws NotFoundException. 
			if (wnSystem == null || wnSystem.length == 0)
			{
				//#CM702543
				// 6006040 = Data mismatch occurred. {0}
				RftLogMessage.print(6006040, LogMessage.F_ERROR, CLASS_NAME, "WareNavi_System");
				throw new ReadWriteException();
			}
			
			//#CM702544
			// Acquire the installation of the stock package. 
			String stockManagement = wnSystem[0].getStockPack();
		    withStockManagement = Boolean.valueOf(stockManagement.equals(WareNaviSystem.PACKAGE_FLAG_ADDON));
	    }

	    return withStockManagement.booleanValue();
	}
	
	//#CM702545
	/**
	 * Decide if the cross dock management exist or not.
	 * @return		Return true when there is crossing dock management. 
	 * 				Otherwise, return false. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public static boolean withCrossDocManagement() throws ReadWriteException
	{
		if (withCrossDocManagement == null)
		{
			//#CM702546
			// Set it if the value is not set. 
			WareNaviSystemHandler wnSystemHandler = new WareNaviSystemHandler(wConn);
			WareNaviSystemSearchKey wnSystemSearchKey = new WareNaviSystemSearchKey();
			WareNaviSystem[] wnSystem = (WareNaviSystem[])wnSystemHandler.find(wnSystemSearchKey);

			//#CM702547
			// When system definition information cannot be acquired, throws NotFoundException. 
			if (wnSystem == null || wnSystem.length == 0)
			{
				//#CM702548
				// 6006040 = Data mismatch occurred. {0}
				RftLogMessage.print(6006040, LogMessage.F_ERROR, CLASS_NAME, "WareNavi_System");
				throw new ReadWriteException();
			}
			
			//#CM702549
			// Acquire the installation of the stock package. 
			String wCrossDocManagement = wnSystem[0].getCrossdockPack();
			withCrossDocManagement = Boolean.valueOf(wCrossDocManagement.equals(WareNaviSystem.PACKAGE_FLAG_ADDON));
		}

		return withCrossDocManagement.booleanValue();
	}

	//#CM702550
	/**
	 * Decide if there is Expiry date or not.
	 * @return		Return true when there is Expiry date management. 
	 * 				Otherwise, return false. 
	 */
	public static boolean withUseByDateManagement()
	{
	    if (withUseByDateManagement == null)
	    {
	        withUseByDateManagement = Boolean.valueOf(WmsParam.IS_USE_BY_DATE_UNIQUEKEY);
	    }
	    
	    return withUseByDateManagement.booleanValue();
	}

	//#CM702551
	/**
	 * Convert it into the value of Work type which manages Work type of Work information in this class. 
	 * @param str	Work information.Work type
	 * @return	SystemParameter.Work type
	 */
	public static int getJobType(String str)
	{
	    return Integer.parseInt(str) - 1;
	}
	
	//#CM702552
	/**
	 * Decide whether to consolidate work in RFT. 
	 * @param	jobType		Work type<BR>
	 * 						0: Receiving <BR>
	 * 						1: Storage<BR>
	 * 						2: Picking <BR>
	 * 						3: Sorting<BR>
	 * 						4: Shipping 
	 * @return		Return true when consolidating it. 
	 * 				Otherwise, return false. 
	 */
	public static boolean isRftWorkCollect(int jobType)
	{
	    if (isRftWorkCollect == null)
	    {
	        isRftWorkCollect = new boolean[IsCollectKey.length];
	        isRftWorkCollect[0] = IsCollectKey[0];
	        isRftWorkCollect[1] = IsCollectKey[1];
	        isRftWorkCollect[2] = IsCollectKey[2];
	        isRftWorkCollect[3] = IsCollectKey[3];
	        isRftWorkCollect[4] = IsCollectKey[4];
	    }
	    
	    return isRftWorkCollect[jobType];
	}

	//#CM702553
	/**
	 * Decide whether the key when work is consolidated in RFT is only Item Code. 
	 * (Return false always for the Sorting disregarding because Item is not a key. )
	 * @param	jobType		Work type<BR>
	 * 						0: Receiving <BR>
	 * 						1: Storage<BR>
	 * 						2: Picking <BR>
	 * 						3: Sorting<BR>
	 * 						4: Shipping 
	 * @return		Return true when consolidating it only with Item Code. 
	 * 				Otherwise (When you include even ITF), Return false. 
	 */
	public static boolean isRftWorkCollectItemCodeOnly(int jobType)
	{
	    if (isRftWorkCollectItemCodeOnly == null)
	    {
	        isRftWorkCollectItemCodeOnly = new boolean[IsCollectItemCodeOnlyKey.length];
	        isRftWorkCollectItemCodeOnly[0] = 
	            IsCollectItemCodeOnlyKey[0].equals(ITEM_CODE_ONLY);
	        isRftWorkCollectItemCodeOnly[1] = 
	            IsCollectItemCodeOnlyKey[1].equals(ITEM_CODE_ONLY);
	        isRftWorkCollectItemCodeOnly[2] = 
	            IsCollectItemCodeOnlyKey[2].equals(ITEM_CODE_ONLY);
	        isRftWorkCollectItemCodeOnly[3] = false;
	        isRftWorkCollectItemCodeOnly[4] = 
	            IsCollectItemCodeOnlyKey[4].equals(ITEM_CODE_ONLY);
	    }
	    
	    return isRftWorkCollectItemCodeOnly[jobType];
	}

	//#CM702554
	// Package methods -----------------------------------------------

	//#CM702555
	// Protected methods ---------------------------------------------

	//#CM702556
	// Private methods -----------------------------------------------
}
//#CM702557
//end of class

// $Id: SessionInventoryListRet.java,v 1.2 2006/12/07 08:57:33 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM569956
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SQLSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.storage.dbhandler.StorageInventoryCheckFinder;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569957
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do Retrieval and to display Inventory Work information. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionInventoryListRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   <CODE>find</CODE> Method is called and Inventory Work information is retrieved. <BR>
 *   Moreover, check display Status at Retrieval, and change the acquisition method. <BR>
 *   <DIR>
 *     1.In case of different display : Retrieval of the one that Stock qty is not equal to the Inventory qty. <BR>
 *     2.In case of numerical display : Retrieval of the one that Stock qty is equal to the Inventory qty.<BR>
 *     3.In case of All display : Retrieval of all information.<BR>
 *   </DIR>
 * <BR>
 *   <Search condition> *MandatoryItem
 *   <DIR>
 *     Consignor Code*<BR>
 *     Start Location<BR>
 *     End Location<BR>
 *     Item Code<BR>
 *     Processing flag : Undefined*<BR>
 *   </DIR>
 *   <Retrieval table>
 *   <DIR>
 *     DNINVENTORYCHECK<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item>
 *   <DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     Location<BR>
 *     Item Code<BR>
 *     Item Name<BR>
 *     Packed qty per case<BR>
 *     Packed qty per bundle<BR>
 *     Inventory Case qty<BR>
 *     Inventory Piece qty<BR>
 *     Stock Case qty<BR>
 *     Stock Piece qty<BR>
 *     Expiry Date<BR>
 *     Worker Code<BR>
 *     Worker Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/07</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:33 $
 * @author  $Author: suresh $
 */
public class SessionInventoryListRet extends SessionRet
{
	//#CM569958
	// Class fields --------------------------------------------------

	//#CM569959
	// Class variables -----------------------------------------------
	//#CM569960
	/**
	 * For Consignor Name acquisition
	 */
	private String wConsignorName = "";

	//#CM569961
	/**
	 * Display type
	 */
	private String wDispStatus = "";

	//#CM569962
	// Class method --------------------------------------------------
	//#CM569963
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:33 $");
	}

	//#CM569964
	// Constructors --------------------------------------------------
	//#CM569965
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionInventoryListRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM569966
		// Retrieval
		find(param);
	}

	//#CM569967
	// Public methods ------------------------------------------------
	//#CM569968
	/**
	 * Return partial qty of Retrieval result of <CODE>DnInventoryCheck</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Consignor Code<BR>
	 *      , Consignor Name<BR>
	 *      , Location<BR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 *      , Packed qty per case<BR>
	 *      , Packed qty per bundle<BR>
	 *      , Inventory Case qty<BR>
	 *      , Inventory Piece qty<BR>
	 *      , Stock Case qty<BR>
	 *      , Stock Piece qty<BR>
	 *      , Expiry Date<BR>
	 *      , Worker Code<BR>
	 *      , Worker Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnInventoryCheck. 
	 */
	public StorageSupportParameter[] getEntities()
	{
		InventoryCheck[] resultArray = null;
		StorageSupportParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM569969
				// The retrieval result is acquired.
				resultArray = (InventoryCheck[]) ((StorageInventoryCheckFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM569970
				// Set it in StorageSupportParameter again. 
				param = getDispData(resultArray);

			}
			catch (Exception e)
			{
				//#CM569971
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return param;
	}

	//#CM569972
	// Package methods -----------------------------------------------

	//#CM569973
	// Protected methods ---------------------------------------------

	//#CM569974
	// Private methods -----------------------------------------------
	//#CM569975
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>InventoryCheckFinder</code> as instance variable to Retrieve it. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{
		wDispStatus = param.getDispStatus();
		int count = 0;

		InventoryCheckSearchKey inventoryKey = new InventoryCheckSearchKey();
		InventoryCheckSearchKey consignorkey = new InventoryCheckSearchKey();
		//#CM569976
		// Set the Search condition
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			inventoryKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			inventoryKey.setLocationNo(param.getFromLocation(), ">=");
			consignorkey.setLocationNo(param.getFromLocation(), ">=");
		}
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			inventoryKey.setLocationNo(param.getToLocation(), "<=");
			consignorkey.setLocationNo(param.getToLocation(), "<=");
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			inventoryKey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		//#CM569977
		// Processing flag : Undefined
		inventoryKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		consignorkey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		
		//#CM569978
		// Location, Item Code, Expiry Date
		inventoryKey.setLocationNoOrder(1, true);
		inventoryKey.setItemCodeOrder(2, true);
		inventoryKey.setUseByDateOrder(3, true);
		
		wFinder = new StorageInventoryCheckFinder(wConn);
		//#CM569979
		// Cursor open
		wFinder.open();
		//#CM569980
		// Change the Retrieval method by display Status. 
		count = getDispCount(wFinder, inventoryKey);
		//#CM569981
		// Initialization
		wLength = count;
		wCurrent = 0;

		//#CM569982
		// Acquire Consignor Name. 
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		StorageInventoryCheckFinder consignorFinder = new StorageInventoryCheckFinder(wConn);
		consignorFinder.open();
		int nameCount = getDispCount(consignorFinder, consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			InventoryCheck winfo[] = (InventoryCheck[]) consignorFinder.getEntities(0, 1);

			if (winfo != null && winfo.length != 0)
			{
				wConsignorName = winfo[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	//#CM569983
	/**
	 * Set Retrieval result in <CODE>StorageSupportParameter</CODE>. <BR>
	 * <BR>
	 * @param result InventoryCheck[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] getDispData(InventoryCheck[] result)
	{
		StorageSupportParameter[] param = new StorageSupportParameter[result.length];

		for (int i = 0; i < result.length; i++)
		{
			param[i] = new StorageSupportParameter();
			param[i].setConsignorCode(result[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setLocation(result[i].getLocationNo());
			param[i].setItemCode(result[i].getItemCode());
			param[i].setItemName(result[i].getItemName1());
			param[i].setEnteringQty(result[i].getEnteringQty());
			param[i].setBundleEnteringQty(result[i].getBundleEnteringQty());
			param[i].setInventoryCheckCaseQty(DisplayUtil.getCaseQty(result[i].getResultStockQty(), result[i].getEnteringQty()));
			param[i].setInventoryCheckPieceQty(DisplayUtil.getPieceQty(result[i].getResultStockQty(), result[i].getEnteringQty()));
			param[i].setStockCaseQty(DisplayUtil.getCaseQty(result[i].getStockQty(), result[i].getEnteringQty()));
			param[i].setStockPieceQty(DisplayUtil.getPieceQty(result[i].getStockQty(), result[i].getEnteringQty()));
			param[i].setUseByDate(result[i].getUseByDate());
			param[i].setWorkerCode(result[i].getWorkerCode());
			param[i].setWorkerName(result[i].getWorkerName());
		}

		return param;
	}

	//#CM569984
	/**
	 * Method to acquire the display qty. <BR>
	 * The Retrieval method changes by Display type. <BR>
	 * <BR>
	 * @param finder DatabaseFinder Instance of DatabaseFinder.
	 * @param skey SQLSearchKey Retrieval key.
	 * @return Retrieval Result qty. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private int getDispCount(DatabaseFinder finder, SQLSearchKey skey) throws Exception
	{
		int dispCnt = 0;
		//#CM569985
		// In case of all qty display
		if (wDispStatus == null || wDispStatus.equals(StorageSupportParameter.DISP_STATUS_ALL))
		{
			dispCnt = ((StorageInventoryCheckFinder) finder).search(skey);
		}
		//#CM569986
		// In case of different display
		else if (wDispStatus.equals(StorageSupportParameter.DISP_STATUS_DIFFERENCE))
		{
			dispCnt = ((StorageInventoryCheckFinder) finder).search(skey, StorageSupportParameter.DISP_STATUS_DIFFERENCE);
		}
		//#CM569987
		// In case of numerical display
		else if (wDispStatus.equals(StorageSupportParameter.DISP_STATUS_EQUAL))
		{
			dispCnt = ((StorageInventoryCheckFinder) finder).search(skey, StorageSupportParameter.DISP_STATUS_EQUAL);
		}

		return dispCnt;

	}
}
//#CM569988
//end of class

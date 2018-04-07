package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570669
/*
 * Created on 2004/09/29
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570670
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 * <BR>
 * The class to do data retrieval for the item list box (stock) in Retrieval. <BR>
 * Receive Search condition as Parameter, and do Retrieval of the item list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageStockItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Do the Retrieval of inventory information by calling <CODE>find</CODE> Method. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Item Code<BR>
 *      , Stock Status : Central stock* <BR>
 *      , Stock qty : More than 1*<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DMSTOCK <BR>
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
 *      , Item Code<BR>
 *      , Item Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:23 $
 * @author  $Author: suresh $
 */
public class SessionStorageStockItemRet extends SessionRet
{
	//#CM570671
	// Class fields --------------------------------------------------

	//#CM570672
	// Class variables -----------------------------------------------

	//#CM570673
	// Class method --------------------------------------------------
	//#CM570674
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:23 $");
	}

	//#CM570675
	// Constructors --------------------------------------------------
	//#CM570676
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageStockItemRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570677
	// Public methods ------------------------------------------------
	//#CM570678
	/**
	 * Return Retrieval result of <CODE>DmStock</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DmStock
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		Stock[] stock = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				stock = (Stock[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(stock);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570679
	// Package methods -----------------------------------------------

	//#CM570680
	// Protected methods ---------------------------------------------

	//#CM570681
	// Private methods -----------------------------------------------
	//#CM570682
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StockFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter stParam) throws Exception
	{
		StockSearchKey sKey = new StockSearchKey();
		//#CM570683
		//Set Search condition
		//#CM570684
		//Consignor Code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}

		//#CM570685
		//Start Location
		if (!StringUtil.isBlank(stParam.getFromLocation()))
		{
			sKey.setLocationNo(stParam.getFromLocation(), ">=");
		}
		//#CM570686
		//End Location
		if (!StringUtil.isBlank(stParam.getToLocation()))
		{
			sKey.setLocationNo(stParam.getToLocation(), "<=");
		}
		//#CM570687
		//	Set Item Code
		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			sKey.setItemCode(stParam.getItemCode());
		}

		//#CM570688
		// Central stock
		sKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM570689
		// Stock qty is one or more. 
		sKey.setStockQty(1, ">=");

		if (stParam.getAreaTypeFlag().equals(StorageSupportParameter.AREA_TYPE_FLAG_NOASRS))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM570690
			// Acquire the areas other than ASRS, and add to Search condition. 
			//#CM570691
			// IS NULL Retrieval when there is no pertinent area
			areaNo = AreaOperator.getAreaNo(areaType);
			sKey.setAreaNo(areaNo);
		}

		//#CM570692
		//Set the order of acquisition. 
		//#CM570693
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM570694
		//Item name
		sKey.setItemName1Collect("");

		//#CM570695
		//Set the order of the group. 
		sKey.setItemCodeGroup(1);
		sKey.setItemName1Group(2);

		//#CM570696
		//Set the order of sorting. 
		sKey.setItemCodeOrder(1, true);
		sKey.setItemName1Order(2, true);

		wFinder = new StockFinder(wConn);

		//#CM570697
		//Open cursor
		wFinder.open();

		int count = wFinder.search(sKey);
		//#CM570698
		//Set count in wLength.
		wLength = count;
		wCurrent = 0;
	}

	//#CM570699
	/**
	 * Convert the Stock entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		StorageSupportParameter[] stParam = null;
		Stock[] stock = (Stock[]) ety;
		if ((stock != null) && (stock.length != 0))
		{
			stParam = new StorageSupportParameter[stock.length];
			for (int i = 0; i < stock.length; i++)
			{
				stParam[i] = new StorageSupportParameter();
				if ((stock[i].getItemCode() != null) && !stock[i].getItemCode().equals(""))
				{
					stParam[i].setItemCode(stock[i].getItemCode()); //商品コード
				}
				if ((stock[i].getItemName1() != null) && !stock[i].getItemName1().equals(""))
				{
					stParam[i].setItemName(stock[i].getItemName1()); //商品名
				}
			}
		}
		return stParam;
	}
}
//#CM570700
//end of class

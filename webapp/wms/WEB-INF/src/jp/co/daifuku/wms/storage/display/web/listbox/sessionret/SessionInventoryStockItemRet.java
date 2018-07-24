// $Id: SessionInventoryStockItemRet.java,v 1.2 2006/12/07 08:57:32 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570032
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.storage.dbhandler.StorageStockFinder;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570033
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * The class to do data retrieval for item list box Retrieval processing (for Inventory (Inventory + stock table)).<BR>
 * Receive Search condition with Parameter, and do Retrieval of the item list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionInventoryStockItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called, Inventory Work information and the inventory information are UNIONed, and Retrieval is done. <BR>
 * <BR>
 *   <Search condition> MandatoryItem*<BR>
 *   <DIR>
 *     None
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNINVENTORYCHECK + DMSTOCK<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
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
 * <TR><TD>2008/11/18</TD><TD>Muneendra</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:32 $
 * @author  $Author: suresh $
 */
public class SessionInventoryStockItemRet extends SessionRet
{
	//#CM570034
	// Class fields --------------------------------------------------

	//#CM570035
	// Class variables -----------------------------------------------

	//#CM570036
	// Class method --------------------------------------------------
	//#CM570037
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:32 $");
	}

	//#CM570038
	// Constructors --------------------------------------------------
	//#CM570039
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionInventoryStockItemRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;		
		find(param);
	}

	//#CM570040
	// Public methods ------------------------------------------------
	//#CM570041
	/**
	 * Return partial qty of Retrieval Result of UNIONed <CODE>DnInventoryCheck</CODE> and <CODE>DmStock</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Item Code<BR>
	 *  , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnInventoryCheck + DmStock
	 */
	public Parameter[] getEntities()
	{
		Stock[] inventory = null;
		StorageSupportParameter[] resultArray = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				inventory = (Stock[]) ((StorageStockFinder)wFinder).getEntities(wStartpoint, wEndpoint);				
				resultArray = convertToStorageSupportParams(inventory);
			}
			catch (Exception e)
			{
				//#CM570042
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM570043
	// Package methods -----------------------------------------------

	//#CM570044
	// Protected methods ---------------------------------------------

	//#CM570045
	// Private methods -----------------------------------------------
	//#CM570046
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StorageStockFinder</code > as instance variable for Retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param parameter StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter parameter) throws Exception
	{
		
		wFinder = new StorageStockFinder(wConn);
		//#CM570047
		// Cursor open
		wFinder.open();
		int count = ((StorageStockFinder)wFinder).searchItem(parameter);
		
		//#CM570048
		// Initialization
		wLength = count;
		wCurrent = 0;
	}

	//#CM570049
	/**
	 * Convert the Stock entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param stock Stock[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] convertToStorageSupportParams(Stock[] stock)
	{	
		if (stock == null || stock.length==0)
		{	
		 	return null;
		}
		StorageSupportParameter[] stParam = new StorageSupportParameter[stock.length];
		for (int i = 0; i < stock.length; i++)
		{
			stParam[i] = new StorageSupportParameter();
			stParam[i].setItemCode(stock[i].getItemCode());
			stParam[i].setItemName(stock[i].getItemName1());
			
		}
	
		return stParam;
	}

}
//#CM570050
//end of class

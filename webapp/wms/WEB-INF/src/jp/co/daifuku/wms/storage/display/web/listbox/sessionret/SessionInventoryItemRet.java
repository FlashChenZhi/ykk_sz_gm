// $Id: SessionInventoryItemRet.java,v 1.2 2006/12/07 08:57:33 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM569932
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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckFinder;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM569933
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do data retrieval for item list list box Retrieval processing (for Inventory). <BR>
 * Receive Search condition with Parameter, and do Retrieval of the item list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionInventoryItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Do Retrieval Inventory Work information by calling <CODE>find(StorageSupportParameter param)</CODE> Method.<BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
 *      , Start Location<BR>
 *      , End Location<BR>
 *      , Item Code<BR>
 *      , Processing flag : Undefined*<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNINVENTORYCHECK <BR>
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
 * <TR><TD>2008/10/07</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:33 $
 * @author  $Author: suresh $
 */
public class SessionInventoryItemRet extends SessionRet
{
	//#CM569934
	// Class fields --------------------------------------------------

	//#CM569935
	// Class variables -----------------------------------------------

	//#CM569936
	// Class method --------------------------------------------------
	//#CM569937
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:33 $");
	}

	//#CM569938
	// Constructors --------------------------------------------------
	//#CM569939
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionInventoryItemRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM569940
		// Retrieval
		find(param);
	}

	//#CM569941
	// Public methods ------------------------------------------------
	//#CM569942
	/**
	 * Return partial qty of Retrieval result of <CODE>DnInventoryCheck</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Item Code<BR>
	 *  , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnInventoryCheck. 
	 */
	public InventoryCheck[] getEntities()
	{
		InventoryCheck[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (InventoryCheck[]) ((InventoryCheckFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM569943
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM569944
	// Package methods -----------------------------------------------

	//#CM569945
	// Protected methods ---------------------------------------------

	//#CM569946
	// Private methods -----------------------------------------------
	//#CM569947
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>InventoryCheckFinder</code> as instance variable to Retrieve it. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It reports on all exceptions. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{
		int count = 0;

		InventoryCheckSearchKey skey = new InventoryCheckSearchKey();

		//#CM569948
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM569949
		// Start Location
		if (!StringUtil.isBlank(param.getFromLocation()))
		{
			skey.setLocationNo(param.getFromLocation(), ">=");
		}
		//#CM569950
		// End Location
		if (!StringUtil.isBlank(param.getToLocation()))
		{
			skey.setLocationNo(param.getToLocation(), "<=");
		}
		//#CM569951
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}
		//#CM569952
		// Processing flag : Undefined
		skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);

		skey.setItemCodeGroup(1);
		skey.setItemName1Group(2);
		skey.setItemCodeCollect("");
		skey.setItemName1Collect("");
		skey.setItemCodeOrder(1, true);
		skey.setItemName1Order(2, true);

		wFinder = new InventoryCheckFinder(wConn);
		//#CM569953
		// Cursor open
		wFinder.open();
		count = ((InventoryCheckFinder) wFinder).search(skey);
		//#CM569954
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
}
//#CM569955
//end of class

// $Id: SessionInventoryConsignorRet.java,v 1.2 2006/12/07 08:57:34 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM569911
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

//#CM569912
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to retrieve data for the Consignor list list box retrieval processing (for Inventory). <BR>
 * Receive the search condition with Parameter, and retrieve the Consignor list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionInventoryConsignorRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   <CODE>find</CODE> Method is called and Inventory Work information is retrieved. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
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
 *      , Consignor Code<BR>
 *      , Consignor Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/07</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:34 $
 * @author  $Author: suresh $
 */
public class SessionInventoryConsignorRet extends SessionRet
{
	//#CM569913
	// Class fields --------------------------------------------------

	//#CM569914
	// Class variables -----------------------------------------------

	//#CM569915
	// Class method --------------------------------------------------
	//#CM569916
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:34 $");
	}

	//#CM569917
	// Constructors --------------------------------------------------
	//#CM569918
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionInventoryConsignorRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		
		//#CM569919
		// Retrieval
		find(param);
	}

	//#CM569920
	// Public methods ------------------------------------------------
	//#CM569921
	/**
	 * Returns the partial qty of Retrieval result of <CODE>DnInventoryCheck</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Consignor Code<BR>
	 *  , Consignor Name<BR>
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
				resultArray = (InventoryCheck[]) ((InventoryCheckFinder)wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM569922
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM569923
	// Package methods -----------------------------------------------

	//#CM569924
	// Protected methods ---------------------------------------------

	//#CM569925
	// Private methods -----------------------------------------------
	//#CM569926
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

		//#CM569927
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM569928
		// Processing flag : Undefined
		skey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);

		wFinder = new InventoryCheckFinder(wConn);
		//#CM569929
		// Cursor open
		wFinder.open();
		count = ((InventoryCheckFinder)wFinder).search(skey);
		//#CM569930
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
}
//#CM569931
//end of class

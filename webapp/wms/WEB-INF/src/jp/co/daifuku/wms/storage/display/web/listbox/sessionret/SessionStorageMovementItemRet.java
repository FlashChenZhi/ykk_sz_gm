// $Id: SessionStorageMovementItemRet.java,v 1.2 2006/12/07 08:57:28 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570258
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
import jp.co.daifuku.wms.base.dbhandler.MovementFinder;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570259
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * The class to do data retrieval for item list box Retrieval processing  (movement).<BR>
 * Receive Search condition with Parameter, and do Retrieval of the item list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageMovementItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Retrieval of movement Work information is done by calling of <CODE>find</CODE> Method.<BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Item Code<BR>
 *      , Processing flag : Excluding the deletion<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNMOVEMENT <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities()</CODE> Method)<BR>
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
 * <TR><TD>2008/10/26</TD><TD>Y.Okamura</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:28 $
 * @author  $Author: suresh $
 */
public class SessionStorageMovementItemRet extends SessionRet
{
	//#CM570260
	// Class fields --------------------------------------------------

	//#CM570261
	// Class variables -----------------------------------------------

	//#CM570262
	// Class method --------------------------------------------------
	//#CM570263
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:28 $");
	}

	//#CM570264
	// Constructors --------------------------------------------------
	//#CM570265
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageMovementItemRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM570266
		// Retrieval
		find(param);
	}

	//#CM570267
	// Public methods ------------------------------------------------
	//#CM570268
	/**
	 * Return partial qty of Retrieval result of <CODE>DnMovement</CODE><BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Item Code<BR>
	 *  , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnMovement
	 */
	public Movement[] getEntities()
	{
		Movement[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (Movement[]) ((MovementFinder) wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//#CM570269
				// 6006002=The data base error occurred. {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570270
	// Package methods -----------------------------------------------

	//#CM570271
	// Protected methods ---------------------------------------------

	//#CM570272
	// Private methods -----------------------------------------------
	//#CM570273
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>MovementFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{
		int count = 0;

		MovementSearchKey skey = new MovementSearchKey();

		//#CM570274
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570275
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}
		//#CM570276
		// Processing flag : Undefined
		skey.setStatusFlag(Movement.STATUSFLAG_DELETE, "!=");

		skey.setItemCodeGroup(1);
		skey.setItemName1Group(2);
		skey.setItemCodeCollect("");
		skey.setItemName1Collect("");
		skey.setItemCodeOrder(1, true);
		skey.setItemName1Order(2, true);

		wFinder = new MovementFinder(wConn);
		//#CM570277
		// Cursor open
		wFinder.open();
		count = ((MovementFinder) wFinder).search(skey);
		//#CM570278
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
	
}
//#CM570279
//end of class

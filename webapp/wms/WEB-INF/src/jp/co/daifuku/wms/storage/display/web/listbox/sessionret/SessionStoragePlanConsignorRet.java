package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570309
/*
 * Created on Sep 30, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570310
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * The class to do data retrieval for Consignor list box (Storage Plan).<BR>
 * Receive Search condition as Parameter, and do Retrieval of the Consignor list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStoragePlanConsignorRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Retrieval of stock Plan information is done by calling <CODE>find</CODE> Method.<BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNSTORAGEPLAN <BR>
 *   </DIR>
 * </DIR>
 * 
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
 * <TR><TD>2008/08/16</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:27 $
 * @author  $Author: suresh $
 */
public class SessionStoragePlanConsignorRet extends SessionRet
{
	//#CM570311
	// Class fields --------------------------------------------------

	//#CM570312
	// Class variables -----------------------------------------------

	//#CM570313
	// Class method --------------------------------------------------
	//#CM570314
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:27 $");
	}

	//#CM570315
	// Constructors --------------------------------------------------
	//#CM570316
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStoragePlanConsignorRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM570317
	// Public methods ------------------------------------------------
	//#CM570318
	/**
	 * Return partial qty of Retrieval result of <CODE>DnStoragePlan</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Location<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnStoragePlan
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		StoragePlan temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (StoragePlan[]) ((StoragePlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToStorageSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM570319
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570320
	// Package methods -----------------------------------------------

	//#CM570321
	// Protected methods ---------------------------------------------

	//#CM570322
	// Private methods -----------------------------------------------
	//#CM570323
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StoragePlanFinder</code> as instance variable for retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{

		StoragePlanSearchKey skey = new StoragePlanSearchKey();
		//#CM570324
		// Retrieval execution
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570325
		// Set the order of the group. 
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		//#CM570326
		// Set the order of acquisition. 
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");

		//#CM570327
		// Set the order of sorting. 
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);

		if (param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for (int i = 0; i < param.getSearchStatus().length; i++)
			{
				if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = StoragePlan.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
		}
		wFinder = new StoragePlanFinder(wConn);
		//#CM570328
		// Cursor open
		wFinder.open();
		int count = ((StoragePlanFinder) wFinder).search(skey);
		//#CM570329
		// Initialization
		wLength = count;
		wCurrent = 0;
	}

	//#CM570330
	/**
	 * Convert the StoragePlan entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param storagePlan StoragePlan[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] convertToStorageSupportParams(StoragePlan[] storagePlan)
	{
		StorageSupportParameter[] stParam = null;

		if (storagePlan == null || storagePlan.length == 0)
		{
			return null;
		}
		stParam = new StorageSupportParameter[storagePlan.length];
		for (int i = 0; i < storagePlan.length; i++)
		{
			stParam[i] = new StorageSupportParameter();
			stParam[i].setConsignorCode(storagePlan[i].getConsignorCode());
			stParam[i].setConsignorName(storagePlan[i].getConsignorName());

		}

		return stParam;
	}

}
//#CM570331
//end of class

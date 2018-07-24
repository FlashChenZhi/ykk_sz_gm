package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570332
/*
 * Created on 2004/09/27 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570333
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * The class to do data retrieval for item list box (Storage Plan).<BR>
 * Receive Search condition as Parameter, and do Retrieval of the item list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStoragePlanItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Retrieval of stock Plan information is done by calling <CODE>find</CODE> Method.<BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Storage plan date<BR>
 * 	    , Start Storage plan date<BR>
 * 	    , End Storage plan date<BR>
 *      , Item Code<BR>
 *      , Work Status<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNSTORAGEPLAN <BR>
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
 * <TR><TD>2004/09/27</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:27 $
 * @author  $Author: suresh $
 */
public class SessionStoragePlanItemRet extends SessionRet
{
	//#CM570334
	// Class fields --------------------------------------------------

	//#CM570335
	// Class variables -----------------------------------------------

	//#CM570336
	// Class method --------------------------------------------------
	//#CM570337
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:27 $");
	}

	//#CM570338
	// Constructors --------------------------------------------------
	//#CM570339
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStoragePlanItemRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570340
	// Public methods ------------------------------------------------
	//#CM570341
	/**
	 * Return partial qty of Retrieval result of <CODE>DnStoragePlan</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Item Code<BR>
	 *  , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnStoragePlan
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		StoragePlan[] storagePlan = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				storagePlan = (StoragePlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(storagePlan);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570342
	// Package methods -----------------------------------------------

	//#CM570343
	// Protected methods ---------------------------------------------

	//#CM570344
	// Private methods -----------------------------------------------
	//#CM570345
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StoragePlanFinder</code> as instance variable for retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter stParam) throws Exception
	{
		StoragePlanSearchKey sKey = new StoragePlanSearchKey();

		//#CM570346
		//Set Search condition
		//#CM570347
		//Consignor Code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM570348
		//When Storage plan date is set
		if (!StringUtil.isBlank(stParam.getStoragePlanDate()))
		{
			sKey.setPlanDate(stParam.getStoragePlanDate());
		}
		else
		{
			//#CM570349
			//Start Storage plan date
			if (!StringUtil.isBlank(stParam.getFromStoragePlanDate()))
			{
				//#CM570350
				//Set Start Storage plan date.
				sKey.setPlanDate(stParam.getFromStoragePlanDate(), ">=");
			}
			if (!StringUtil.isBlank(stParam.getToStoragePlanDate()))
			{
				//#CM570351
				//End Storage plan date
				sKey.setPlanDate(stParam.getToStoragePlanDate(), "<=");
			}
		}
		//#CM570352
		//Set Item Code
		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			sKey.setItemCode(stParam.getItemCode());
		}

		if (stParam.getSearchStatus() != null && stParam.getSearchStatus().length > 0)
		{
			String[] search = new String[stParam.getSearchStatus().length];
			for (int i = 0; i < stParam.getSearchStatus().length; i++)
			{
				if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_UNSTART;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_START;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = StoragePlan.STATUS_FLAG_NOWWORKING;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			sKey.setStatusFlag(search);
		}
		else
		{
			sKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
		}

		//#CM570353
		// Set the order of acquisition. 
		//#CM570354
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM570355
		//Item name
		sKey.setItemName1Collect("");

		//#CM570356
		//Set the order of the group. 

		//#CM570357
		//Item Code
		sKey.setItemCodeGroup(1);
		//#CM570358
		//Item name
		sKey.setItemName1Group(2);

		//#CM570359
		//Set the order of sorting. 
		sKey.setItemCodeOrder(1, true);
		sKey.setItemName1Order(2, true);

		wFinder = new StoragePlanFinder(wConn);
		//#CM570360
		//Open cursor
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM570361
		//Set count in wLength.
		wLength = count;
		wCurrent = 0;

	}

	//#CM570362
	/**
	 * Convert the StoragePlan entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		StorageSupportParameter[] stParam = null;
		StoragePlan[] storagePlan = (StoragePlan[]) ety;
		if ((storagePlan != null) && (storagePlan.length != 0))
		{
			stParam = new StorageSupportParameter[storagePlan.length];
			for (int i = 0; i < storagePlan.length; i++)
			{
				stParam[i] = new StorageSupportParameter();
				if (!StringUtil.isBlank(storagePlan[i].getItemCode()))
				{
					stParam[i].setItemCode(storagePlan[i].getItemCode()); //商品コード
				}
				if (!StringUtil.isBlank(storagePlan[i].getItemName1()))
				{
					stParam[i].setItemName(storagePlan[i].getItemName1()); //商品名
				}
			}
		}

		return stParam;
	}

}
//#CM570363
//end of class

package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570775
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;
//#CM570776
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * The class to do data for Consignor list box (Work) in Retrieval. <BR>
 * Receive Search condition as Parameter, and do Retrieval of the Consignor list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageWorkInfoConsignorRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called and Retrieval of Work information is done. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Storage plan date<BR>
 *      , WorkFlag : Storage * <BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNWORKINFO <BR>
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
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/10/27</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:22 $
 * @author  $Author: suresh $
 */
public class SessionStorageWorkInfoConsignorRet extends SessionRet
{
	//#CM570777
	// Class fields --------------------------------------------------

	//#CM570778
	// Class variables -----------------------------------------------

	//#CM570779
	// Class method --------------------------------------------------
	//#CM570780
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:22 $");
	}

	//#CM570781
	// Constructors --------------------------------------------------
	//#CM570782
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageWorkInfoConsignorRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM570783
	// Public methods ------------------------------------------------
	//#CM570784
	/**
	 * Return Retrieval result of <CODE>DnWorkInfo</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Consignor Code<BR>
	 *      , Consignor Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnWorkInfo
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		WorkingInformation temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToStorageSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM570785
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570786
	// Package methods -----------------------------------------------

	//#CM570787
	// Protected methods ---------------------------------------------

	//#CM570788
	// Private methods -----------------------------------------------
	//#CM570789
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		//#CM570790
		// Retrieval execution
		//#CM570791
		// Storage plan date
		if (!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			skey.setPlanDate(param.getStoragePlanDate());
		}
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570792
		// WorkFlag : Storage 
		skey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);

		//#CM570793
		// Set the order of the group. 
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		//#CM570794
		// Set the order of acquisition. 
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");

		//#CM570795
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
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
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
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		//#CM570796
		// Retrieval excluding ASRS Work.
		if (StorageSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(param.getAreaTypeFlag()))
		{

			skey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		}
		wFinder = new WorkingInformationFinder(wConn);
		//#CM570797
		// Cursor open
		wFinder.open();
		int count = ((WorkingInformationFinder) wFinder).search(skey);
		//#CM570798
		// Initialization
		wLength = count;
		wCurrent = 0;
	}

	//#CM570799
	/**
	 * Convert the Working Information entity into <CODE>StorageSupportParameter</CODE>. <BR>
	 * <BR>
	 * @param workinfo WorkingInformation[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] convertToStorageSupportParams(WorkingInformation[] workinfo)
	{
		StorageSupportParameter[] stParam = null;

		if (workinfo == null || workinfo.length == 0)
		{
			return null;
		}
		stParam = new StorageSupportParameter[workinfo.length];
		for (int i = 0; i < workinfo.length; i++)
		{
			stParam[i] = new StorageSupportParameter();
			stParam[i].setConsignorCode(workinfo[i].getConsignorCode());
			stParam[i].setConsignorName(workinfo[i].getConsignorName());

		}

		return stParam;
	}

}
//#CM570800
//end of class

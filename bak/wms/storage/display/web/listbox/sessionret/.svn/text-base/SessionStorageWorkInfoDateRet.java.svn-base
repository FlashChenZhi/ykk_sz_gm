package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570801
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570802
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * The class to do data retrieval for list box (Work) of the Plan day.<BR>
 * Receive Search condition as Parameter, and do Retrieval of the Plan date list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageWorkInfoDateRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called and Retrieval of Work information is done. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Storage plan date<BR>
 *     Start Storage plan date<BR>
 *     End Storage plan date<BR>
 *     WorkFlag : Storage * <BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *     DNWORKINFO <BR>
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
 *     Storage plan date<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>Muneendra</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:22 $
 * @author  $Author: suresh $
 */
public class SessionStorageWorkInfoDateRet extends SessionRet
{
	//#CM570803
	// Class fields --------------------------------------------------

	//#CM570804
	// Class variables -----------------------------------------------

	//#CM570805
	// Class method --------------------------------------------------
	//#CM570806
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:22 $");
	}

	//#CM570807
	// Constructors --------------------------------------------------
	//#CM570808
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageWorkInfoDateRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570809
	// Public methods ------------------------------------------------
	//#CM570810
	/**
	 * Return Retrieval result of <CODE>DnWorkInfo</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Storage plan date<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnWorkInfo
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		WorkingInformation[] workingInformation = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				workingInformation = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(workingInformation);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570811
	// Package methods -----------------------------------------------

	//#CM570812
	// Protected methods ---------------------------------------------

	//#CM570813
	// Private methods -----------------------------------------------
	//#CM570814
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter stParam) throws Exception
	{
		WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();

		//#CM570815
		//Set Search condition
		//#CM570816
		//Consignor Code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM570817
		//When Storage plan date is set
		if (!StringUtil.isBlank(stParam.getStoragePlanDate()))
		{
			sKey.setPlanDate(stParam.getStoragePlanDate());
		}
		else
		{
			//#CM570818
			//Start Storage plan date
			if (!StringUtil.isBlank(stParam.getFromStoragePlanDate()))
			{
				//#CM570819
				//Set Start Storage plan date.
				sKey.setPlanDate(stParam.getFromStoragePlanDate());
			}
			if (!StringUtil.isBlank(stParam.getToStoragePlanDate()))
			{
				//#CM570820
				//End Storage plan date
				sKey.setPlanDate(stParam.getToStoragePlanDate());
			}
		}
		//#CM570821
		// WorkFlag : Storage 
		sKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);

		//#CM570822
		//Set the order of acquisition. 
		//#CM570823
		//Storage plan date
		sKey.setPlanDateCollect("");
		//#CM570824
		//Set the order of the group. 
		sKey.setPlanDateGroup(1);
		//#CM570825
		//Set the order of sorting. 
		sKey.setPlanDateOrder(1, true);
		if (stParam.getSearchStatus() != null && stParam.getSearchStatus().length > 0)
		{
			String[] search = new String[stParam.getSearchStatus().length];
			for (int i = 0; i < stParam.getSearchStatus().length; i++)
			{
				if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (stParam.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
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
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		
		//#CM570826
		// Retrieval excluding ASRS Work, except when area type is ASRS Work. 
		if (StorageSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(stParam.getAreaTypeFlag()))
		{
		    sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		}

		wFinder = new WorkingInformationFinder(wConn);

		wFinder.open();
		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM570827
	/**
	 * Convert the Working Information entity into <CODE>StorageSupportParameter</CODE>. <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		StorageSupportParameter[] stParam = null;
		WorkingInformation[] workingInformation = (WorkingInformation[]) ety;
		if ((workingInformation != null) && (workingInformation.length != 0))
		{
			stParam = new StorageSupportParameter[workingInformation.length];
			for (int i = 0; i < workingInformation.length; i++)
			{
				stParam[i] = new StorageSupportParameter();
				if (!StringUtil.isBlank(workingInformation[i].getPlanDate()))
				{
					stParam[i].setStoragePlanDate(workingInformation[i].getPlanDate()); //入庫予定日
				}
			}
		}
		return stParam;
	}
	
}
//#CM570828
//end of class

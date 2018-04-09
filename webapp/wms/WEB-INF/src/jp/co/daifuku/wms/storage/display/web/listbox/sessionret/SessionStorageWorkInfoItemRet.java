package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570829
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

//#CM570830
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * The class to do data retrieval for item list box (Work).<BR>
 * Receive Search condition as Parameter, and do Retrieval of the item list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageWorkInfoItemRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called and Retrieval of Work information is done. <BR>
 * <BR>
 *   <Search condition> MandatoryItem*<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Storage plan date<BR>
 *     Start Storage plan date<BR>
 *     End Storage plan date<BR>
 *     Work Status<BR>
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
 *     Item Code<BR>
 *     Item Name<BR>
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
public class SessionStorageWorkInfoItemRet extends SessionRet
{
	//#CM570831
	// Class fields --------------------------------------------------

	//#CM570832
	// Class variables -----------------------------------------------

	//#CM570833
	// Class method --------------------------------------------------
	//#CM570834
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:22 $");
	}

	//#CM570835
	// Constructors --------------------------------------------------
	//#CM570836
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageWorkInfoItemRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570837
	// Public methods ------------------------------------------------
	//#CM570838
	/**
	 * Return Retrieval result of <CODE>DnWorkInfo</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnWorkInfo
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		WorkingInformation[] workInfo = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				workInfo = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(workInfo);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570839
	// Package methods -----------------------------------------------

	//#CM570840
	// Protected methods ---------------------------------------------

	//#CM570841
	// Private methods -----------------------------------------------
	//#CM570842
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

		//#CM570843
		//Set Search condition
		//#CM570844
		//Consignor Code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM570845
		//When Storage plan date is set
		if (!StringUtil.isBlank(stParam.getStoragePlanDate()))
		{
			sKey.setPlanDate(stParam.getStoragePlanDate());
		}
		else
		{
			//#CM570846
			//Start Storage plan date
			if (!StringUtil.isBlank(stParam.getFromStoragePlanDate()))
			{
				//#CM570847
				//Set Start Storage plan date
				sKey.setPlanDate(stParam.getFromStoragePlanDate(), ">=");
			}
			if (!StringUtil.isBlank(stParam.getToStoragePlanDate()))
			{
				//#CM570848
				//End Storage plan date
				sKey.setPlanDate(stParam.getToStoragePlanDate(), "<=");
			}
		}
		//#CM570849
		//Set Item Code
		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			sKey.setItemCode(stParam.getItemCode());
		}
		//#CM570850
		// WorkFlag : Storage 
		sKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);

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

		//#CM570851
		//Set the order of acquisition. 

		//#CM570852
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM570853
		//Item name
		sKey.setItemName1Collect("");

		//#CM570854
		//Set the order of the group. 

		//#CM570855
		//Item Code
		sKey.setItemCodeGroup(1);
		//#CM570856
		//Item name
		sKey.setItemName1Group(2);

		//#CM570857
		//Set the order of sorting. 
		sKey.setItemCodeOrder(1, true);
		sKey.setItemName1Order(2, true);
		
		//#CM570858
		// Retrieval excluding ASRS Work, except when area type is ASRS Work. 
		if (StorageSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(stParam.getAreaTypeFlag()))
		{
		    sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		}
		
		wFinder = new WorkingInformationFinder(wConn);
		//#CM570859
		//Cursor open
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM570860
		//Set count in wLength.
		wLength = count;
		wCurrent = 0;

	}

	//#CM570861
	/**
	 * Convert the Working Information entity into <CODE>StorageSupportParameter</CODE>. <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		StorageSupportParameter[] stParam = null;
		WorkingInformation[] workInfo = (WorkingInformation[]) ety;
		if ((workInfo != null) && (workInfo.length != 0))
		{
			stParam = new StorageSupportParameter[workInfo.length];
			for (int i = 0; i < workInfo.length; i++)
			{
				stParam[i] = new StorageSupportParameter();
				if (!StringUtil.isBlank(workInfo[i].getItemCode()))
				{
					stParam[i].setItemCode(workInfo[i].getItemCode()); //商品コード
				}
				if (!StringUtil.isBlank(workInfo[i].getItemName1()))
				{
					stParam[i].setItemName(workInfo[i].getItemName1()); //商品名
				}
			}
		}

		return stParam;
	}

}
//#CM570862
//end of class

// $Id: SessionStorageCaseLocationPlanRet.java,v 1.2 2006/12/07 08:57:30 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570123
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570124
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * This class is a class for Retrieval of Case item Storage Location.<BR>
 * Receive Search condition with Parameter, and do Retrieval. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageCaseLocationPlanRet</CODE>Constructor)<BR>
 * 	<DIR>
 * 		It is done when initial data of the list box is displayed.<BR>
 * 		<CODE>find</CODE> Method is called, Retrieve storage Plan information, Maintain the list of Case Storage Location. <BR>
 *  	Retrieves data other than the deletion in the case of Status of Search condition is null or ""<BR>
 * 		<BR>
 *   	<Search condition> *MandatoryItem<BR>
 *   	<DIR>
 * 			 , Consignor Code*<BR>
 *  		 , Work Status <BR>
 * 			 , Storage plan date<BR>
 *  		 , Item Code<BR>
 *  		 , CaseStorage Location<BR>
 *   	</DIR>
 *   	<Retrieval table> <BR>
 *   	<DIR>
 *     	 , DNSTORAGEPLAN <BR>
 *   	</DIR>
 * 	</DIR>
 * 	<BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * 	<DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item>
 *   <DIR>
 *      , Storage Location<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:30 $
 * @author  $Author: suresh $
 */
public class SessionStorageCaseLocationPlanRet extends SessionRet
{
	//#CM570125
	// Class fields --------------------------------------------------

	//#CM570126
	// Class variables -----------------------------------------------

	//#CM570127
	// Class method --------------------------------------------------
	//#CM570128
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:30 $");
	}
	
	//#CM570129
	// Constructors --------------------------------------------------
	//#CM570130
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageCaseLocationPlanRet(Connection conn, StorageSupportParameter param) throws ReadWriteException
	{
		//#CM570131
		// Maintenance of connection
		wConn = conn ;
		
		//#CM570132
		// Retrieval
		find(param);
	}

	//#CM570133
	// Public methods ------------------------------------------------
	//#CM570134
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
	public StoragePlan[] getEntities()
	{
		StoragePlan[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray =
					(StoragePlan[]) ((StoragePlanFinder) wFinder).getEntities(
						wStartpoint,
						wEndpoint);
			}
			catch (Exception e)
			{
				//#CM570135
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
		
	}

	//#CM570136
	// Package methods -----------------------------------------------

	//#CM570137
	// Protected methods ---------------------------------------------

	//#CM570138
	// Private methods -----------------------------------------------
	//#CM570139
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StoragePlanFinder</code> as instance variable for retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws ReadWriteException
	{
		//#CM570140
		// Finder instance generation
		wFinder = new StoragePlanFinder(wConn);
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();
		
		//#CM570141
		// Set Search condition
		storagePlanSearchKey.setCaseLocationCollect("");
		//#CM570142
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			storagePlanSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570143
		// Storage plan date
		if(!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			storagePlanSearchKey.setPlanDate(param.getStoragePlanDate());
		}
		//#CM570144
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			storagePlanSearchKey.setItemCode(param.getItemCode());
		}
		//#CM570145
		// CaseStorage Location
		if(!StringUtil.isBlank(param.getCaseLocation()))
		{
			storagePlanSearchKey.setCaseLocation(param.getCaseLocation()); 
		}
		storagePlanSearchKey.setCaseLocation("", "IS NOT NULL");
		
		//#CM570146
		// Status
		if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for(int i = 0; i < param.getSearchStatus().length; i++)
			{
				if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_UNSTART;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_START;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = StoragePlan.STATUS_FLAG_NOWWORKING;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETION;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_ALL))
				{
					search[i] = "*";
				}
			}
			storagePlanSearchKey.setStatusFlag(search);
		}
		else
		{
			storagePlanSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
		}
		
		storagePlanSearchKey.setCaseLocationGroup(1);
		storagePlanSearchKey.setCaseLocationOrder(1, true);
		
		//#CM570147
		// Cursor open of Finder
		wFinder.open();
		
		//#CM570148
		// Retrieval (The result qty is acquired) 
		wLength = wFinder.search(storagePlanSearchKey);
		
		//#CM570149
		// Initialize the current display qty.
		wCurrent = 0;
	}
}
//#CM570150
//end of class

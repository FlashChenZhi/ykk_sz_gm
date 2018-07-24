// $Id: SessionStorageLocationWorkInfoRet.java,v 1.2 2006/12/07 08:57:29 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570206
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570207
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * The class to do data retrieval for Location list box Retrieval processing (for Work).<BR>
 * Receive Search condition with Parameter, and do Retrieval of the Location list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageLocationWorkInfoRet</CODE>Constructor)<BR>
 * 	<DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called and Retrieval of Work information is done. <BR>
 * 	<BR>
 * 	<Search condition> *Mandatory input<BR>
 * <DIR>
 * 	 , Consignor Code*<BR>
 *   , Status <BR>
 * 	 , Storage plan date<BR>
 *   , Item Code<BR>
 *   , CasePieceFlag<BR>
 *  </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNWORKINFO <BR>
 *   </DIR>
 * 	</DIR>
 * 	<BR>
 * 2.Display record acquisition processing (<CODE>getEntities</CODE> Method)<BR>
 * 	<DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * 	<BR>
 * 	<Item><BR>
 * 	<DIR>
 * 	 , Storage Location<BR>
 * </DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:29 $
 * @author  $Author: suresh $
 */
public class SessionStorageLocationWorkInfoRet extends SessionRet
{
	//#CM570208
	// Class fields --------------------------------------------------

	//#CM570209
	// Class variables -----------------------------------------------

	//#CM570210
	// Class method --------------------------------------------------
	//#CM570211
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:29 $");
	}
	
	//#CM570212
	// Constructors --------------------------------------------------
	//#CM570213
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageLocationWorkInfoRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		//#CM570214
		// Maintenance of connection
		wConn = conn ;
		
		//#CM570215
		// Retrieval
		find(param);
	}

	//#CM570216
	// Public methods ------------------------------------------------
	//#CM570217
	/**
	 * Return partial qty of Retrieval result of <CODE>DnWorkingInfo</CODE><BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Storage Location<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnWorkingInfo
	 */
	public WorkingInformation[] getEntities()
	{
		WorkingInformation[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray =
					(WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(
						wStartpoint,
						wEndpoint);
			}
			catch (Exception e)
			{
				//#CM570218
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
		
	}

	//#CM570219
	// Package methods -----------------------------------------------

	//#CM570220
	// Protected methods ---------------------------------------------

	//#CM570221
	// Private methods -----------------------------------------------
	//#CM570222
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
		//#CM570223
		// Finder instance generation
		wFinder = new WorkingInformationFinder(wConn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		
		//#CM570224
		// Set Search condition
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		workInfoSearchKey.setLocationNoCollect("");
		//#CM570225
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			workInfoSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570226
		// Storage plan date
		if(!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			workInfoSearchKey.setPlanDate(param.getStoragePlanDate());
		}
		//#CM570227
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			workInfoSearchKey.setItemCode(param.getItemCode());
		}
		//#CM570228
		// CasePieceFlag
		if(!StringUtil.isBlank(param.getCasePieceflg()))
		{
			if(StorageSupportParameter.CASEPIECE_FLAG_CASE.equals(param.getCasePieceflg()))
			{			
				workInfoSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);			
			}else if(StorageSupportParameter.CASEPIECE_FLAG_PIECE.equals(param.getCasePieceflg()))
			{			
				workInfoSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);			
			}else if(StorageSupportParameter.CASEPIECE_FLAG_NOTHING.equals(param.getCasePieceflg()))
			{			
				workInfoSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);			
			}
		}
		//#CM570229
		// Storage Location
		if(!StringUtil.isBlank(param.getStorageLocation()))
		{
			workInfoSearchKey.setLocationNo(param.getStorageLocation());
		}
		workInfoSearchKey.setLocationNo("", "IS NOT NULL");
		
		//#CM570230
		// Status
		if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for(int i = 0; i < param.getSearchStatus().length; i++)
			{
				if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_ALL))
				{
					search[i] = "*";
				}
			}
			workInfoSearchKey.setStatusFlag(search);
		}
		else
		{
			workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		
		workInfoSearchKey.setLocationNoGroup(1);
		workInfoSearchKey.setLocationNoOrder(1, true);
		
		//#CM570231
		// Cursor open of Finder
		wFinder.open();
		
		//#CM570232
		// Retrieval (The result qty is acquired) 
		wLength = wFinder.search(workInfoSearchKey);
		
		//#CM570233
		// Initialize the current display qty.
		wCurrent = 0;
	}
	
}
//#CM570234
//end of class

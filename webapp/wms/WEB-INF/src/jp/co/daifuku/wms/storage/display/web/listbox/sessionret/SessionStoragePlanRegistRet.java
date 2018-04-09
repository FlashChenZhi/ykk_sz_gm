// $Id: SessionStoragePlanRegistRet.java,v 1.2 2006/12/07 08:57:26 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570481
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
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570482
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * The class to do data retrieval for Retrieval processing of Storage Plan list (Storage PlanAdd).<BR>
 * Receive Search condition with Parameter, and do Retrieval of Storage Plan list (Storage PlanAdd). <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStoragePlanRegistRet</CODE>Constructor)<BR>
 * 	<DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   < CODE>find</CODE> Method is called and Retrieval of Work information is done. <BR>
 * 	<BR>
 * 	<Search condition> *Mandatory input<BR>
 * <DIR>
 * 	 , Consignor Code*<BR>
 * 	 , Storage plan date<BR>
 *   , Item Code<BR>
 *   , Status<BR>
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
 * <BR>
 *   <Display Item><BR>
 *   <DIR>
 *   , Consignor Code<BR>
 *   , Consignor Name<BR>
 *   , Storage plan date<BR>
 *   , Item Code<BR>
 *   , Item Name<BR>
 *   , Case/Piece flag<BR>
 *   , Storage Location<BR>
 *   , Packed qty per case<BR>
 *   , Packed qty per bundle<BR>
 *   , CaseITF<BR>
 *   , Bundle ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:26 $
 * @author  $Author: suresh $
 */
public class SessionStoragePlanRegistRet extends SessionRet
{
	//#CM570483
	// Class fields --------------------------------------------------

	//#CM570484
	// Class variables -----------------------------------------------
	//#CM570485
	/**
	 * Consignor Name to be displayed
	 */
	private String wConsignorName = "" ;
	
	//#CM570486
	// Class method --------------------------------------------------
	//#CM570487
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:26 $");
	}

	//#CM570488
	// Constructors --------------------------------------------------
	//#CM570489
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStoragePlanRegistRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		wConn = conn ;
		find(param);
	}

	//#CM570490
	// Public methods ------------------------------------------------
	//#CM570491
	/**
	 * Return partial qty of Retrieval result of <CODE>DnWorkInfo</CODE><BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *   , Consignor Code<BR>
	 *   , Consignor Name<BR>
	 *   , Storage plan date<BR>
	 *   , Item Code<BR>
	 *   , Item Name<BR>
	 *   , Case/Piece flag<BR>
	 *   , Storage Location<BR>
	 *   , Packed qty per case<BR>
	 *   , Packed qty per bundle<BR>
	 *   , CaseITF<BR>
	 *   , Bundle ITF<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnWorkInfo
	 */
	public StorageSupportParameter[] getEntities()
	{
		WorkingInformation[] resultArray = null;
		StorageSupportParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray =
					(WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(
						wStartpoint,
						wEndpoint);
				
				//#CM570492
				// Data for the display is acquired. 
				param = getDispData(resultArray);
			}
			catch (Exception e)
			{
				//#CM570493
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return param;
		
	}

	//#CM570494
	// Package methods -----------------------------------------------

	//#CM570495
	// Protected methods ---------------------------------------------

	//#CM570496
	// Private methods -----------------------------------------------
	//#CM570497
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
		//#CM570498
		// Finder instance generation
		wFinder = new WorkingInformationFinder(wConn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		
		//#CM570499
		// Set Search condition
		//#CM570500
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			workInfoSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570501
		// Storage plan date
		if(!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			workInfoSearchKey.setPlanDate(param.getStoragePlanDate());
		}
		//#CM570502
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			workInfoSearchKey.setItemCode(param.getItemCode());
		}

		//#CM570503
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
		
		//#CM570504
		// WorkFlag = Storage 
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		
		//#CM570505
		// Consolidating condition
		workInfoSearchKey.setConsignorCodeGroup(1);
		workInfoSearchKey.setPlanDateGroup(2);
		workInfoSearchKey.setItemCodeGroup(3);
		workInfoSearchKey.setWorkFormFlagGroup(4);
		workInfoSearchKey.setLocationNoGroup(5);

		//#CM570506
		// Content of acquisition
		workInfoSearchKey.setConsignorCodeCollect("");
		workInfoSearchKey.setPlanDateCollect("");
		workInfoSearchKey.setItemCodeCollect("");
		workInfoSearchKey.setWorkFormFlagCollect("");
		workInfoSearchKey.setLocationNoCollect("");

		//#CM570507
		// The order of order
		workInfoSearchKey.setPlanDateOrder(1, true);
		workInfoSearchKey.setItemCodeOrder(2, true);
		workInfoSearchKey.setWorkFormFlagOrder(3, true);
		workInfoSearchKey.setLocationNoOrder(4, true);
		
		//#CM570508
		// Cursor open of Finder
		wFinder.open();
		
		//#CM570509
		// Retrieval (The result qty is acquired) 
		wLength = wFinder.search(workInfoSearchKey);
		
		//#CM570510
		// Initialize the current display qty.
		wCurrent = 0;
		
		//#CM570511
		// Consignor Name acquisition
		getDisplayConsignorName(param);
		
	}
	
	//#CM570512
	/**
	 * Set the Working Information entity in <CODE>StorageSupportParameter</CODE> and return it. <BR>
	 * <BR>
	 * @param workInfo WorkingInformation[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] getDispData(WorkingInformation[] workInfo) throws Exception
	{
		StorageSupportParameter[] param = new StorageSupportParameter[workInfo.length];
		
		WorkingInformation newData = new WorkingInformation();
		for(int i = 0; i < workInfo.length; i++)
		{
			//#CM570513
			//  Description acquisition Method call
			newData = getDisplayName(workInfo[i]);
			
			param[i] = new StorageSupportParameter();
			param[i].setConsignorCode(workInfo[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setStoragePlanDate(workInfo[i].getPlanDate());
			param[i].setItemCode(workInfo[i].getItemCode());
			param[i].setItemName(newData.getItemName1());
			//#CM570514
			// Acquire Case/Piece flag description from Case/Piece flag (Work form). 
			String casepiecename = DisplayUtil.getPieceCaseValue(workInfo[i].getWorkFormFlag());
			
			if (workInfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_CASE ))
			{
				param[i].setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_CASE);
			}
			else if (workInfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE ))
			{
				param[i].setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_PIECE);
			}
			if (workInfo[i].getWorkFormFlag().equals(WorkingInformation.CASEPIECE_FLAG_NOTHING ))
			{
				param[i].setCasePieceflg(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			}
			param[i].setCasePieceflgName(casepiecename);
			param[i].setStorageLocation(workInfo[i].getLocationNo());
			param[i].setEnteringQty(newData.getEnteringQty());
			param[i].setBundleEnteringQty(newData.getBundleEnteringQty());
			param[i].setITF(newData.getItf());
			param[i].setBundleITF(newData.getBundleItf());
		}
		
		return param;
	}
	
	//#CM570515
	/**
	 * Acquire latest Consignor Name to display it in the list. <BR>
	 * <BR>
	 * @param param StorageSupportParameter Search condition.
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void getDisplayConsignorName(StorageSupportParameter param) throws Exception
	{
		//#CM570516
		// Finder instance generation
		WorkingInformationFinder consignorFinder = new WorkingInformationFinder(wConn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();
		
		//#CM570517
		// Set Search condition
		//#CM570518
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			workInfoSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570519
		// Storage plan date
		if(!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			workInfoSearchKey.setPlanDate(param.getStoragePlanDate());
		}
		//#CM570520
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			workInfoSearchKey.setItemCode(param.getItemCode());
		}
		//#CM570521
		// Status!=Deletion
		workInfoSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		//#CM570522
		// WorkFlag=Storage 
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		
		//#CM570523
		// Acquire Consignor Code. 
		workInfoSearchKey.setConsignorNameCollect();
		//#CM570524
		// The order of order
		workInfoSearchKey.setRegistDateOrder(1, false);
		
		//#CM570525
		// Consignor NameRetrieval
		consignorFinder.open();
		int nameCount = consignorFinder.search(workInfoSearchKey);
		if (nameCount > 0)
		{
			WorkingInformation[] workInfo = (WorkingInformation[]) consignorFinder.getEntities(0, 1);

			if (workInfo != null && workInfo.length != 0)
			{
				wConsignorName = workInfo[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	//#CM570526
	/**
	 * Work information is retrieved with Search condition set in Parameter while Retrieving. 
	 * The Add date acquires latest Item Name, Case , Packed qty per bundle, Case , and Bundle ITF. <BR>
	 * <BR>
	 * @param pWinfo WorkingInformation Search condition.
	 * @return WorkingInformation <CODE>WorkingInformation</CODE> entity which sets acquired data. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private WorkingInformation getDisplayName(WorkingInformation pWinfo) throws Exception
	{
		//#CM570527
		// Finder instance generation
		WorkingInformationFinder dispNameFinder = new WorkingInformationFinder(wConn);
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();	

		//#CM570528
		// Set Search condition
		//#CM570529
		// Consignor Code
		if(!StringUtil.isBlank(pWinfo.getConsignorCode()))
		{
			workInfoSearchKey.setConsignorCode(pWinfo.getConsignorCode());
		}
		//#CM570530
		// Storage plan date
		if(!StringUtil.isBlank(pWinfo.getPlanDate()))
		{
			workInfoSearchKey.setPlanDate(pWinfo.getPlanDate());
		}
		//#CM570531
		// Item Code
		if(!StringUtil.isBlank(pWinfo.getItemCode()))
		{
			workInfoSearchKey.setItemCode(pWinfo.getItemCode());
		}
		//#CM570532
		// Status!=Deletion
		workInfoSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		//#CM570533
		// WorkFlag=Storage 
		workInfoSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		
		//#CM570534
		// Set the order of acquisition. 
		//#CM570535
		// Item Code
		workInfoSearchKey.setItemName1Collect("");
		//#CM570536
		// Packed qty per case
		workInfoSearchKey.setEnteringQtyCollect("");
		//#CM570537
		// Packed qty per bundle
		workInfoSearchKey.setBundleEnteringQtyCollect("");
		//#CM570538
		// CaseITF
		workInfoSearchKey.setItfCollect("");
		//#CM570539
		// Bundle ITF
		workInfoSearchKey.setBundleItfCollect("");
				
		//#CM570540
		// The order of order
		workInfoSearchKey.setRegistDateOrder(1, false);
		
		WorkingInformation result = new WorkingInformation();
		dispNameFinder.open();
		int dataCount = dispNameFinder.search(workInfoSearchKey);
		if (dataCount > 0)
		{
			WorkingInformation[] workInfo = (WorkingInformation[]) dispNameFinder.getEntities(0, dataCount);
	
			if (workInfo != null && workInfo.length != 0)
			{
				result = new WorkingInformation();
				
				//#CM570541
				// Item Name
				result.setItemName1(workInfo[0].getItemName1());
				//#CM570542
				// Packed qty per case
				result.setEnteringQty(workInfo[0].getEnteringQty());
				//#CM570543
				// Packed qty per bundle
				result.setBundleEnteringQty(workInfo[0].getBundleEnteringQty());
				//#CM570544
				// CaseITF
				result.setItf(workInfo[0].getItf());
				//#CM570545
				// Bundle ITF
				result.setBundleItf(workInfo[0].getBundleItf());							
					
			}
		}
		
		dispNameFinder.close();
		
		return result;
		
	}

}
//#CM570546
//end of class

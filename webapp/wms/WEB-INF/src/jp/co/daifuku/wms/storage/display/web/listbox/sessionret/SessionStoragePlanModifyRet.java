// $Id: SessionStoragePlanModifyRet.java,v 1.2 2006/12/07 08:57:26 suresh Exp $
package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;

//#CM570434
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
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570435
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * The class to do data retrieval for Retrieval processing of Storage Plan list (Stock Plan correction and deletion).<BR>
 * Receive Search condition with Parameter, and do Retrieval of Storage Plan list (Stock Plan correction and deletion). <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStoragePlanModifyRet</CODE>Constructor)<BR>
 * 	<DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Retrieval of stock Plan information is done by calling <CODE>find</CODE> Method.<BR>
 * 	<BR>
 * 	<Search condition> *Mandatory input<BR>
 * <DIR>
 * 	 , Consignor Code*<BR>
 * 	 , Storage plan date<BR>
 *   , Item Code<BR>
 *   , CaseStorage Location<BR>
 *   , PieceStorage Location<BR>
 *   , Status<BR>
 *  </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNSTORAGEPLAN <BR>
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
 *   , CaseStorage Location<BR>
 *   , PieceStorage Location<BR>
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
public class SessionStoragePlanModifyRet extends SessionRet
{
	//#CM570436
	// Class fields --------------------------------------------------

	//#CM570437
	// Class variables -----------------------------------------------
	//#CM570438
	/**
	 * Consignor Name to be displayed
	 */
	private String wConsignorName = "" ;

	//#CM570439
	// Class method --------------------------------------------------
	//#CM570440
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:26 $");
	}
	

	//#CM570441
	// Constructors --------------------------------------------------
	//#CM570442
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStoragePlanModifyRet(Connection conn, StorageSupportParameter param) throws ReadWriteException
	{
		//#CM570443
		// Maintenance of connection
		wConn = conn ;
		
		//#CM570444
		// Retrieval
		find(param);
	}

	//#CM570445
	// Public methods ------------------------------------------------
	//#CM570446
	/**
	 * Return partial qty of Retrieval result of <CODE>DnStoragePlan</CODE>.<BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Consignor Code<BR>
	 *  , Consignor Name<BR>
	 *  , Storage plan date<BR>
	 *  , Item Code<BR>
	 *  , Item Name<BR>
	 *  , CaseStorage Location<BR>
	 *  , PieceStorage Location<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnStoragePlan
	 */
	public StorageSupportParameter[] getEntities()
	{
		StoragePlan[] resultArray = null;
		StorageSupportParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray =
					(StoragePlan[]) ((StoragePlanFinder) wFinder).getEntities(
						wStartpoint,
						wEndpoint);
						
				//#CM570447
				// Data for the display is acquired. 
				param = getDispData(resultArray);
			}
			catch (Exception e)
			{
				//#CM570448
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return param;
		
	}

	//#CM570449
	// Package methods -----------------------------------------------

	//#CM570450
	// Protected methods ---------------------------------------------

	//#CM570451
	// Private methods -----------------------------------------------
	//#CM570452
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
		//#CM570453
		// Finder instance generation
		wFinder = new StoragePlanFinder(wConn);
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();
		
		//#CM570454
		// Set Search condition
		//#CM570455
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			storagePlanSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570456
		// Storage plan date
		if(!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			storagePlanSearchKey.setPlanDate(param.getStoragePlanDate());
		}
		//#CM570457
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			storagePlanSearchKey.setItemCode(param.getItemCode());
		}
		//#CM570458
		// CaseStorage Location
		if(!StringUtil.isBlank(param.getCaseLocation()))
		{
			storagePlanSearchKey.setCaseLocation(param.getCaseLocation());
		}
		//#CM570459
		// PieceStorage Location
		if(!StringUtil.isBlank(param.getPieceLocation()))
		{
			storagePlanSearchKey.setPieceLocation(param.getPieceLocation());
		}
		
		//#CM570460
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
		
		//#CM570461
		// Consolidating condition
		storagePlanSearchKey.setConsignorCodeGroup(1);
		storagePlanSearchKey.setPlanDateGroup(2);
		storagePlanSearchKey.setItemCodeGroup(3);
		storagePlanSearchKey.setItemName1Group(4);
		storagePlanSearchKey.setCaseLocationGroup(5);
		storagePlanSearchKey.setPieceLocationGroup(6);
		
		//#CM570462
		// Content of acquisition
		storagePlanSearchKey.setConsignorCodeCollect();
		storagePlanSearchKey.setPlanDateCollect();
		storagePlanSearchKey.setItemCodeCollect();
		storagePlanSearchKey.setItemName1Collect();
		storagePlanSearchKey.setCaseLocationCollect();
		storagePlanSearchKey.setPieceLocationCollect();

		//#CM570463
		// The order of acquisition
		storagePlanSearchKey.setPlanDateOrder(1, true);
		storagePlanSearchKey.setItemCodeOrder(2, true);
		storagePlanSearchKey.setCaseLocationOrder(3, true);
		storagePlanSearchKey.setPieceLocationOrder(4, true);
		
		//#CM570464
		// Cursor open of Finder
		wFinder.open();
		
		//#CM570465
		// Retrieval (The result qty is acquired) 
		wLength = wFinder.search(storagePlanSearchKey);
		
		//#CM570466
		// Initialize the current display qty.
		wCurrent = 0;
		
		//#CM570467
		// Consignor Name acquisition
		getDisplayConsignorName(param);
	}
	
	//#CM570468
	/**
	 * Set the storagePlan entity in <CODE>StorageSupportParameter</CODE> and return it.  <BR>
	 * <BR>
	 * @param storagePlan StoragePlan[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] getDispData(StoragePlan[] storagePlan)
	{
		StorageSupportParameter[] param = new StorageSupportParameter[storagePlan.length];
		
		for(int i = 0; i < storagePlan.length; i++)
		{
			param[i] = new StorageSupportParameter();
			param[i].setConsignorCode(storagePlan[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setStoragePlanDate(storagePlan[i].getPlanDate());
			param[i].setItemCode(storagePlan[i].getItemCode());
			param[i].setItemName(storagePlan[i].getItemName1());
			param[i].setCaseLocation(storagePlan[i].getCaseLocation());
			param[i].setPieceLocation(storagePlan[i].getPieceLocation());
		}
		
		return param;
	}
	
	//#CM570469
	/**
	 * Acquire latest Consignor Name to display it in the list. <BR>
	 * <BR>
	 * @param param StorageSupportParameter Search condition.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	private void getDisplayConsignorName(StorageSupportParameter param) throws ReadWriteException
	{
		//#CM570470
		// Finder instance generation
		StoragePlanFinder consignorFinder = new StoragePlanFinder(wConn);
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();
		
		//#CM570471
		// Set Search condition
		//#CM570472
		// Consignor Code
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			storagePlanSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570473
		// Storage plan date
		if(!StringUtil.isBlank(param.getStoragePlanDate()))
		{
			storagePlanSearchKey.setPlanDate(param.getStoragePlanDate());
		}
		//#CM570474
		// Item Code
		if(!StringUtil.isBlank(param.getItemCode()))
		{
			storagePlanSearchKey.setItemCode(param.getItemCode());
		}
		//#CM570475
		// CaseStorage Location
		if(!StringUtil.isBlank(param.getCaseLocationCondition()))
		{
			storagePlanSearchKey.setCaseLocation(param.getCaseLocationCondition());
		}
		//#CM570476
		// PieceStorage Location
		if(!StringUtil.isBlank(param.getPieceLocationCondition()))
		{
			storagePlanSearchKey.setPieceLocation(param.getPieceLocationCondition());
		}
		//#CM570477
		// Status!=Deletion
		storagePlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		
		//#CM570478
		// The order of order
		storagePlanSearchKey.setRegistDateOrder(1, false);
		
		//#CM570479
		// Consignor NameRetrieval
		consignorFinder.open();
		int nameCount = consignorFinder.search(storagePlanSearchKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			StoragePlan[] storagePlan = (StoragePlan[]) consignorFinder.getEntities(0, 1);

			if (storagePlan != null && storagePlan.length != 0)
			{
				wConsignorName = storagePlan[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}
}
//#CM570480
//end of class

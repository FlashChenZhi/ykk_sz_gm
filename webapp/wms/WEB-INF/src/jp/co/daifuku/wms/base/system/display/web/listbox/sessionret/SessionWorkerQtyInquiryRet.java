//#CM693362
//$Id: SessionWorkerQtyInquiryRet.java,v 1.2 2006/11/13 08:21:00 suresh Exp $

//#CM693363
/*
 * Created on 2004/08/17
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection ;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.system.dbhandler.SystemWorkerResultFinder;
import jp.co.daifuku.wms.base.system.dbhandler.SystemWorkerResultSearchKey;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693364
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR> 
 *
 * Allow this class to search for the Result info by worker and display it.<BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Allow users to enter the following info.<BR>
 *   Enable to input the following parameters.<BR>
 * <BR>
 * <DIR>
 * 	<Parameter>* Mandatory item (count)<BR>
 * </DIR>
 * 		Work type *<BR>
 * 		<DIR>	
 * 		[ All (All)<BR>
 * 			Receiving (Arrival)<BR>
 * 			Storage (Storage)<BR>
 * 			Picking (Retrieval)<BR>
 * 			Sorting (Sorting)<BR>
 * 			Shipping (Shipping)<BR>
 * 			Inventory Check (Location)<BR>
 * 			Increase Inventory Check <BR>
 * 			Decrease Inventory Check <BR>
 * 			Relocation for Storage (RelocateforStorage)<BR>
 * 			Relocation for Retrieval (RelocateforRetrieval)<BR>
 * 			Unplanned Storage (ExceptionalStorage)<BR>
 * 			Unplanned Picking (ExceptionalRetrieval)<BR>
 * 			Increase in Inventory Maintenance <BR>
 * 			Decrease in Inventory Maintenance <BR>
 * 		]<BR>
 * 		</DIR>
 * 		Start work date (FromWorkDate)<BR>
 * 		End Work Date (ToWorkDate)<BR>
 * 		Worker Code (WorkerCode)<BR>
 * 		Aggregation Conditions *<BR>
 * 		<DIR>
 * 		[ <BR>
 * 			Display the total within a period. (Term/Total)<BR>
 * 			Display the daily total. (Daily)<BR>
 * 			Display the detail. (Detailed)<BR>
 * 		]<BR>
 * 		</DIR> 		
 * </DIR>
 * <BR>
 * <BR>
 * 
 * 2.Click on Display button<BR>
 * <DIR>
 *   Obtain the data to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result in the <CODE>Parameter</CODE> array and return it.<BR>
 * </DIR>
 *<BR>
 *<BR>
 * 		Case : 1 (to display the total within the period)<BR>
 * 		<DIR>
 * 			gather the following parameters as one record in a HTML table and print it.<BR>
 *			
 *			Worker Code (WorkerCode)<BR>
 *			Worker Name (WorkerName)<BR>
 *			Work type (WorkDetail) <BR>
 *			Work Time (WorkTime) in HHH:MM:SS format<BR>
 *			Work Quantity (WorkQty) <BR>
 *			Work Count (WorkCnt) <BR>
 *			Work Quantity/h (WorkQty/hour) <BR>
 *			Work Count/h (WorkCnt/hour) <BR>
 *			
 *		</DIR>
 *		<BR>
 *		
 *		Case : 2 (to display the daily total)<BR>
 *		<DIR>
 * 			Display the following parameters in the table.<BR>
 *			
 *			Work date (WorkDate) in YYYY/mm/DD format<BR>
 *			Worker Code (WorkerCode)<BR>
 *			Worker Name (WorkerName)<BR>
 *			Work type (WorkDetail) <BR>
 *			Start Time (StartTime) in HH:MM:SS format<BR>
 *			End Time (EndTime) in HH:MM:SS format<BR>
 *			Work Time (WorkTime) in HHH:MM:SS format<BR>
 *			Work Quantity (WorkQty) <BR>
 *			Work Count (WorkCnt) <BR>
 *			Work Quantity/h (WorkQty/hour) <BR>
 *			Work Count/h (WorkCnt/hour) <BR>
 *		</DIR>
 *		<BR>
 *		Case : 3 (to display the detail)<BR>
 *		<DIR>
 * 			Display the following parameters in the table.<BR>
 *			
 *			Work date (WorkDate)<BR>
 *			Worker Code (WorkerCode)<BR>
 *			Worker Name (WorkerName)<BR>
 *			Work type (WorkDetail) <BR>
 *			Start Time (StartTime) MIN(StartTime)<BR>
 *			End Time (EndTime) MAX(EndTime)<BR>
 *			Work Time (WorkTime) in HH:MM:SS  SUM(EndTime - StartTime)<BR>
 *			Work Quantity (WorkQty) <BR>
 *			Work Count (WorkCnt) <BR>
 *			Work Quantity/h (WorkQty/hour) <BR>
 *			Work Count/h (WorkCnt/hour) <BR>
 *			RFTNo	(TerminalNo) <BR>
 *		</DIR>
 *		<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:00 $
 * @author  $Author: suresh $
 */

public class SessionWorkerQtyInquiryRet extends SessionRet
{
	//#CM693365
	// Class fields --------------------------------------------------

	//#CM693366
	// Class variables -----------------------------------------------
	//#CM693367
	/**	
	 * SystemWorkerResultFinder object
	 */
	private SystemWorkerResultFinder swRF = null ;
	
	//#CM693368
	/**
	 * Display type.
	 */
	private String dispCond = "" ;	
	
	//#CM693369
	// Class method --------------------------------------------------
	//#CM693370
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 08:21:00 $");
	}

	//#CM693371
	// Constructors --------------------------------------------------
	//#CM693372
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching.<BR>
	 * Allow the <CODE>find(SystemParameter param)</CODE> method to set the count of obtained data.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param sysParam   Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */	
	public SessionWorkerQtyInquiryRet(Connection conn, SystemParameter sysParam) throws Exception
	{
		wConn = conn ;
		//#CM693373
		// Search
		find(sysParam) ;
	}
	
	//#CM693374
	// Public methods ------------------------------------------------
	//#CM693375
	/**
	 * Return the designated number of the search results of the Result info by worker.<BR>
	 * Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain the Result info by worker. from the result set.<BR>
	 *   3.Obtain the display data from the Result info by worker and set it in <CODE>Parameter</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * 
	 * @return <CODE>SystemParameter</CODE> class that contains information to be displayed.
	 */	
	public Parameter[] getEntities()
	{
		SystemParameter[] resultArray = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	    	
			try
			{
				//#CM693376
				// Obtain the search result.
				resultArray = (SystemParameter[]) swRF.getParamsEntitys(wStartpoint,wEndpoint,dispCond);
			}
			catch (Exception e)
			{
				//#CM693377
				// Record the error in the log file.	    		
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
		
	}
	
	//#CM693378
	// Package methods -----------------------------------------------

	//#CM693379
	// Protected methods ---------------------------------------------

	//#CM693380
	// Private methods -----------------------------------------------
	//#CM693381
	/**
	 * Allow this method to obtain the search conditions from parameter and search for the Result info by worker.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions.
	 * @throws Exception Announce when some error occurs during loading data.
	 */	
	private void find(SystemParameter param) throws Exception 
	{
		int count = 0 ;				

		SystemWorkerResultSearchKey wRsk = new SystemWorkerResultSearchKey() ;
	
		//#CM693382
		// Set the search conditions.
		if( (param.getFromWorkDate()!= null) && !param.getFromWorkDate().equals(""))
		{
			if((param.getToWorkDate() != null) && !param.getToWorkDate().equals(""))
			{
				//#CM693383
				//(WORK_DATE < "XXX" AND WORK_DATE > "XXX")
				wRsk.setWorkDate(param.getFromWorkDate(),">=" , "(" , "", "AND") ;
				wRsk.setWorkDate(param.getToWorkDate(),"<=","",")","AND") ;
			}
			else
			{
				//#CM693384
				//WORK_DATE > "XXX"
				wRsk.setWorkDate(param.getFromWorkDate(),">=") ;
			}
		}	
		else if((param.getToWorkDate() != null) && !param.getToWorkDate().equals(""))
		{
			//#CM693385
			//WORK_DATE < "XXX"
			wRsk.setWorkDate(param.getToWorkDate(),"<=") ;
		}
		
		//#CM693386
		//	WORKER_CODE = "XXX" in WHERE CLAUSE
		if((param.getWorkerCode()!= null) && !param.getWorkerCode().equals(""))
		{
			wRsk.setWorkerCode(param.getWorkerCode()) ;
		}
		
		//#CM693387
		//JOB_TYPE = "XXX" ; WHERE CLAUSE
		if((param.getSelectWorkDetail()!= null) && !param.getSelectWorkDetail().equals(""))
		{				
			if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INSTOCK))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_INSTOCK) ;  //入荷 : 01
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_STORAGE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_STORAGE) ; //入庫 : 02
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_RETRIEVAL))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL) ; //出庫 : 03
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_SORTING))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_SORTING) ; //仕分 : 04
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_SHIPPING))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION) ; //出荷 : 05
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_RETRIEVAL))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_MOVEMENT_RETRIEVAL) ; //移動出庫 : 11
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MOVEMENT_STORAGE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE) ; //移動入庫 : 12
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDSTORAGE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_EX_STORAGE) ; //例外入庫 : 21
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_UNSCHEDULEDRETRIEVAL))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_EX_RETRIEVAL) ; //例外出庫 : 22
			}		
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_INCREASE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_MAINTENANCE_PLUS) ; //メンテナンス増 :31
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_MAINTENANCE_DECREASE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_MAINTENANCE_MINUS) ; //メンテナンス減 :32
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_INVENTORY) ; //棚卸 : 40
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_INCREASE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_INVENTORY_PLUS) ; //棚卸増 : 41
			}
			else if(param.getSelectWorkDetail().equals(SystemParameter.SELECTWORKDETAIL_INVENTORY_DECREASE))
			{
				wRsk.setJobType(SystemDefine.JOB_TYPE_INVENTORY_MINUS) ; //棚卸減 : 42
			}
			
			
			
		}
		
		if(param.getSelectAggregateCondition().equals(SystemParameter.SELECTAGGREGATECONDITION_TERM))
		{
			
			//#CM693388
			// GROUP BY
			//#CM693389
			// COLLECT conditions
			wRsk.setWorkerCodeGroup(1) ;
			wRsk.setWorkerCodeCollect("") ;
			
			wRsk.setWorkerNameGroup(2);
			wRsk.setWorkerNameCollect("") ;
			
			wRsk.setJobTypeGroup(3);
			wRsk.setJobTypeCollect("") ;

			wRsk.setWorkQtyCollect("SUM") ;
			wRsk.setWorkCntCollect("SUM") ;
							
			//#CM693390
			//Display the aggregation conditions and the total within a period
			dispCond = SystemParameter.SELECTAGGREGATECONDITION_TERM ;

			//#CM693391
			// Set the search order.
			wRsk.setWorkerCodeOrder(1,true) ;
			wRsk.setJobTypeOrder(2,true) ;

			
		}
		else if(param.getSelectAggregateCondition().equals(SystemParameter.SELECTAGGREGATECONDITION_DAILY))
		{
			
			//#CM693392
			// GROUP BY
			//#CM693393
			// COLLECT conditions
			wRsk.setWorkDateCollect("") ;
			wRsk.setWorkDateGroup(1) ;
			
			wRsk.setWorkerCodeCollect("") ;
			wRsk.setWorkerCodeGroup(2) ;
			
			wRsk.setWorkerNameCollect("") ;
			wRsk.setWorkerNameGroup(3);			
			
			wRsk.setJobTypeCollect("") ;
			wRsk.setJobTypeGroup(4) ;
							
			wRsk.setWorkQtyCollect("SUM") ;
			wRsk.setWorkCntCollect("SUM") ;
			
			//#CM693394
			//Aggregation Conditions Display the daily total.
			dispCond = SystemParameter.SELECTAGGREGATECONDITION_DAILY ;
			
			//#CM693395
			// Set the search order.
			wRsk.setWorkDateOrder(1, true) ;
			wRsk.setWorkerCodeOrder(2, true) ;
			wRsk.setJobTypeOrder(3, true) ;
			wRsk.setWorkStartTimeOrder(4, true);
			
		}
		else if(param.getSelectAggregateCondition().equals(SystemParameter.SELECTAGGREGATECONDITION_DETAIL))
		{

			//#CM693396
			// COLLECT conditions
			wRsk.setWorkDateCollect("") ;
			wRsk.setWorkerCodeCollect("") ;
			wRsk.setWorkerNameCollect("") ;
			wRsk.setJobTypeCollect("") ;
			wRsk.setWorkQtyCollect("") ;
			wRsk.setWorkCntCollect("") ;
			wRsk.setTerminalNoCollect("") ;			
			
			//#CM693397
			//Aggregation Conditions Display the detail.
			dispCond = SystemParameter.SELECTAGGREGATECONDITION_DETAIL ;

			//#CM693398
			// Set the search order.
			wRsk.setWorkDateOrder(1, true) ;
			wRsk.setWorkerCodeOrder(2, true) ;
			wRsk.setJobTypeOrder(3, true) ;
			wRsk.setWorkStartTimeOrder(4, true);

		}

		//#CM693399
		//Generate a SystemWorkerResultFinder object.
		swRF = new SystemWorkerResultFinder(wConn);
		
		//#CM693400
		// Open the connection.
		swRF.open() ;			
		count = swRF.searchTerm(wRsk ,dispCond);

		wLength = count;

		wCurrent = 0;
	}
}
//#CM693401
//end of class

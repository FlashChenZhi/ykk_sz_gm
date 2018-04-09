package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712459
/*
 * Created on 2004/10/12
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM712460
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to Search through the Picking Plan Info and display it in the listbox.<BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalOrderPlanInquiryRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions>Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Work status:<BR>
 *   </DIR>
 *   <Search table> 
 *   <DIR>
 *     DNRETRIEVALPLAN<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the <CODE>RetrievalSupportParameter</CODE> array and return it. <BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *     Planned Picking Date<BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *     Customer Code<BR>
 *     Customer Name<BR>
 *     Item Code<BR>
 *     Item Name<BR>
 *     Case/Piece division<BR>
 *     Packed Qty per Case<BR>
 *     Packed qty per bundle<BR>
 *     Host planned Case qty <BR>
 *     Host Planned Piece Qty<BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Case ITF<BR>
 *     Bundle ITF<BR>
 *     Work status:<BR>
 *   </DIR>
 * </DIR>
 * 
 *
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/12</TD>
 * <TD>suresh kayamboo</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:01 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalOrderPlanInquiryRet extends SessionRet
{
	//#CM712461
	// Class fields --------------------------------------------------

	//#CM712462
	// Class variables -----------------------------------------------

	//#CM712463
	// Class method --------------------------------------------------
	//#CM712464
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:01 $");
	}

	//#CM712465
	// Constructors --------------------------------------------------
	//#CM712466
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param rtParam    Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalOrderPlanInquiryRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}
	
	//#CM712467
	// Public methods ------------------------------------------------
	//#CM712468
	/**
	 * Obtain the specified number of search results of the Picking Plan Info and return as an array of Parameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Picking Plan Info from the result set.<BR>
	 *   3.Obtain the display data from the Picking Plan Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return <CODE>RetrievalSupportParameter</CODE> class that contains Picking Plan Info.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		RetrievalPlan[] retrievalPlan = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				retrievalPlan = (RetrievalPlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToRetrievalSupportParams(retrievalPlan);
			} 
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
		
	}
	
	//#CM712469
	// Package methods -----------------------------------------------

	//#CM712470
	// Protected methods ---------------------------------------------

	//#CM712471
	// Private methods -----------------------------------------------
	//#CM712472
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey() ;
		
		//#CM712473
		// Set the search conditions. 
		//#CM712474
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		
		//#CM712475
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		} else
		{   //現在の画面には使わない。画面変えるなら使える。<BR>
			//#CM712476
			//Start Planned Picking Date
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM712477
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM712478
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate(), "<=");
			}
		}
		
		//#CM712479
		//Case Order No.
		if(!StringUtil.isBlank(rtParam.getCaseOrderNo()))
		{
		    sKey.setCaseOrderNo(rtParam.getCaseOrderNo()) ;
		}
		
		//#CM712480
		//Piece Order No.
		if(!StringUtil.isBlank(rtParam.getPieceOrderNo()))
		{
		    sKey.setPieceOrderNo(rtParam.getPieceOrderNo()) ;
		}

		//#CM712481
		//Status flag
		if(!StringUtil.isBlank(rtParam.getWorkStatus()))
		{
		    if(rtParam.equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
		    {
		        //#CM712482
		        // Standby 
		        sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART) ;
		    }
		    else if(rtParam.equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
		    {
		        //#CM712483
		        //Start
		        sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START) ;
		    }
		    else if(rtParam.equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
		    {
		        //#CM712484
		        // Working 
		        sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING) ;
		    }
		    else if(rtParam.equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
		    {
		        //#CM712485
		        // Completed 
		        sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION) ;
		    }
		    else if(rtParam.equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
		    {
		        //#CM712486
		        // All 
		    }
		}
		
		//#CM712487
		//Set the Conditions for obtaining data. 
		//#CM712488
		//Consignor Code
		sKey.setConsignorCodeCollect("");
		//#CM712489
		//Consignor Name
		sKey.setConsignorNameCollect("");
		//#CM712490
		//Planned Picking Date
		sKey.setPlanDateCollect("");
		//#CM712491
		//Case Order No.
		sKey.setCaseOrderNoCollect("");
		//#CM712492
		//Piece Order No.
		sKey.setPieceOrderNoCollect("");
		//#CM712493
		//Customer Code
		sKey.setCustomerCodeCollect("");
		//#CM712494
		// Customer Name 
		sKey.setCustomerName1Collect("");
		//#CM712495
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM712496
		//Item Name
		sKey.setItemName1Collect("");
		//#CM712497
		// Division 
		sKey.setCasePieceFlagCollect("");
		//#CM712498
		//Packed Qty per Case
		sKey.setEnteringQtyCollect("");
		//#CM712499
		//Packed qty per bundle
		sKey.setBundleEnteringQtyCollect("");
		//#CM712500
		// Planned qty 
		sKey.setPlanQtyCollect("");
		//#CM712501
		// Picking Case Location 
		sKey.setCaseLocationCollect("");
		//#CM712502
		// Picking Piece Location 
		sKey.setPieceLocationCollect("");
		//#CM712503
		//Case ITF
		sKey.setItfCollect("") ;
		//#CM712504
		//Bundle ITF
		sKey.setBundleItfCollect("");
		//#CM712505
		// status 
		sKey.setStatusFlagCollect("");
		//#CM712506
		// Added date 
		sKey.setRegistDateCollect("");
				
		//#CM712507
		// Set the sorting order. 
		sKey.setPlanDateOrder(1,true);
		sKey.setItemCodeOrder(2,true);
		sKey.setCasePieceFlagOrder(3,true);
		
		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712508
		//Open the cursor.
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM712509
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;
		
	}
	
	//#CM712510
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>Parameter</CODE>. <BR>
	 * 
	 * @param ety Picking Plan Info 
	 * @return Parameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private Parameter[] convertToRetrievalSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		RetrievalPlan[] retrievalPlan = (RetrievalPlan[])ety ;
		rtParam = new RetrievalSupportParameter[retrievalPlan.length];
		String registDate = "1";
		for (int i = 0; i < retrievalPlan.length; i++)
		{
		    rtParam[i] = new RetrievalSupportParameter();
		    
		    if((i==0) || (Long.parseLong(registDate) < Long.parseLong(getDateValue(retrievalPlan[i].getRegistDate()))))
		    {
		        registDate = getDateValue(retrievalPlan[i].getRegistDate());
		        //#CM712511
		        // Set the Consignor Code. 
		        rtParam[0].setConsignorCode(retrievalPlan[i].getConsignorCode());
		        //#CM712512
		        // Set the Consignor Name. 
		        rtParam[0].setConsignorName(retrievalPlan[i].getConsignorName());
		    }
		   
		    //#CM712513
		    //Planned Picking Date
		    if(!StringUtil.isBlank(retrievalPlan[i].getPlanDate()))
		    {
		        rtParam[i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate());
		    }
		    //#CM712514
		    //Case Order No.
		    if(!StringUtil.isBlank(retrievalPlan[i].getCaseOrderNo()))
		    {
		        rtParam[i].setCaseOrderNo(retrievalPlan[i].getCaseOrderNo()) ;
		    }
		    //#CM712515
		    //Piece Order No.
		    if(!StringUtil.isBlank(retrievalPlan[i].getPieceOrderNo()))
		    {
		        rtParam[i].setPieceOrderNo(retrievalPlan[i].getPieceOrderNo()) ;
		    }
		    //#CM712516
		    //Customer Code
		    if(!StringUtil.isBlank(retrievalPlan[i].getCustomerCode()))
		    {
		        rtParam[i].setCustomerCode(retrievalPlan[i].getCustomerCode()) ;
		    }
		    //#CM712517
		    // Customer Name 
		    if(!StringUtil.isBlank(retrievalPlan[i].getCustomerName1()))
		    {
		        rtParam[i].setCustomerName(retrievalPlan[i].getCustomerName1()) ;
		    }
		    //#CM712518
		    //Item Code
		    if(!StringUtil.isBlank(retrievalPlan[i].getItemCode()))
		    {
		        rtParam[i].setItemCode(retrievalPlan[i].getItemCode()) ;
		    }
		    //#CM712519
		    //Item Name
		    if(!StringUtil.isBlank(retrievalPlan[i].getItemName1()))
		    {
		        rtParam[i].setItemName(retrievalPlan[i].getItemName1()) ;
		    }
		    //#CM712520
		    // Division 
		    if(!StringUtil.isBlank(retrievalPlan[i].getCasePieceFlag()))
		    {
		        
		        if(retrievalPlan[i].getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_CASE))
		        {
		            //#CM712521
		            // Case 
		            rtParam[i].setCasePieceflgName(SystemDefine.CASEPIECE_FLAG_CASE);
		        }
		        else if(retrievalPlan[i].getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_PIECE))
		        {
		            //#CM712522
		            // Piece 
		            rtParam[i].setCasePieceflgName(SystemDefine.CASEPIECE_FLAG_PIECE);
		        }
		        else if(retrievalPlan[i].getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_NOTHING))
		        {
		            //#CM712523
		            // None 
		            rtParam[i].setCasePieceflgName(SystemDefine.CASEPIECE_FLAG_NOTHING);
		        }
		        else if(retrievalPlan[i].getCasePieceFlag().equals(RetrievalPlan.CASEPIECE_FLAG_MIX))
		        {
		            //#CM712524
		            // Case/Piece 
		            rtParam[i].setCasePieceflgName(SystemDefine.CASEPIECE_FLAG_MIX);
		        }
		    }
		    
		    if(retrievalPlan[i].getEnteringQty() > 0)
		    {
		        //#CM712525
		        //Packed Qty per Case
		        rtParam[i].setEnteringQty(retrievalPlan[i].getEnteringQty()) ;
		        //#CM712526
		        // Host planned Case qty 
		        rtParam[i].setPlanCaseQty(DisplayUtil.getCaseQty(retrievalPlan[i].getPlanQty(),retrievalPlan[i].getEnteringQty())) ;
			    //#CM712527
			    //Host Planned Piece Qty
		        rtParam[i].setPlanPieceQty(DisplayUtil.getPieceQty(retrievalPlan[i].getPlanQty(),retrievalPlan[i].getEnteringQty()));
		    }
		    
		    //#CM712528
		    // Packed qty per piece 
		    rtParam[i].setBundleEnteringQty(retrievalPlan[i].getBundleEnteringQty());
		    
		    //#CM712529
		    //Case Picking Location
		    if(!StringUtil.isBlank(retrievalPlan[i].getCaseLocation()))
		    {
		        rtParam[i].setCaseLocation(retrievalPlan[i].getCaseLocation()) ;
		    }
		    //#CM712530
		    // Piece Picking Location 
		    if(!StringUtil.isBlank(retrievalPlan[i].getPieceLocation()))
		    {
		        rtParam[i].setPieceLocation(retrievalPlan[i].getPieceLocation()) ;
		    }
		    //#CM712531
		    //Case ITF
		    if(!StringUtil.isBlank(retrievalPlan[i].getItf()))
		    {
		        rtParam[i].setITF(retrievalPlan[i].getItf()) ;
		    }
		    //#CM712532
		    // Piece ITF 
		    if(!StringUtil.isBlank(retrievalPlan[i].getBundleItf()))
		    {
		        rtParam[i].setBundleITF(retrievalPlan[i].getBundleItf()) ;
		    }
		    
		    //#CM712533
		    // status 
		    //#CM712534
		    //For a logic that displays status 
		    //#CM712535
		    //with the same Order No. and  
		    //#CM712536
		    //with Case division Working and with Piece division Started,
		    //#CM712537
		    //regard its status as "Working". 
		    if(!StringUtil.isBlank(retrievalPlan[i].getStatusFlag()))
		    {
		        if(retrievalPlan[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_UNSTART))
		        {
		            //#CM712538
		            // Standby 
		            rtParam[i].setWorkStatus(SystemDefine.STATUS_FLAG_UNSTART);			            
		        }
		        else if(retrievalPlan[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_START))
		        {
		            //#CM712539
		            //Start
		            rtParam[i].setWorkStatus(SystemDefine.STATUS_FLAG_START);			            
		        }
		        else if(retrievalPlan[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_NOWWORKING))
		        {
		            //#CM712540
		            // Working 
		            rtParam[i].setWorkStatus(SystemDefine.STATUS_FLAG_NOWWORKING);			            
		        }
		        else if(retrievalPlan[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART))
		        {
		            //#CM712541
		            // Partially Completed 
		            rtParam[i].setWorkStatus(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);			            
		        }
		        else if(retrievalPlan[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_COMPLETION))
		        {
		            //#CM712542
		            // Completed 
		            rtParam[i].setWorkStatus(SystemDefine.STATUS_FLAG_COMPLETION);			            
		        }
		    }
		}
		
		return rtParam;
	}
	
	//#CM712543
	/**
	 * Allow this method to translate the Date into String (yyyyMMddHHmmss) and return it. 
	 * Set the Added date as a Parameter. 
	 * Return the Added date in string type.
	 * @param date Date 
	 * @return String String of date (yyyyMMddHHmmss) 
	 */
	private String getDateValue(Date date)
	{	    
	    String datNumS = null ;
	    
	    if(date != null)
	    {
	        //#CM712544
	        // Generate pattern according to 24 hours. 
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss") ;
	        datNumS = sdf.format(date).trim() ;       
	    }
	    
	    return datNumS ;
	}

}
//#CM712545
//end of class

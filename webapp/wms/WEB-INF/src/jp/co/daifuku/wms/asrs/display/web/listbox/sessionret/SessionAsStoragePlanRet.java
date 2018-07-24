//#CM40746
//$Id: SessionAsStoragePlanRet.java,v 1.2 2006/10/26 06:32:14 suresh Exp $

//#CM40747
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.sessionret;

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM40748
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * This class is used to search data for ASRS storage plan date list box<BR>
 * Receive the search condition as a parameter, and retrieve work information. <BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * 1.search process(<CODE>SessionAsStoragePlanRet(Connection conn,AsScheduleParameter param)</CODE>method )<BR>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter param)</CODE> Call work method information is retrieved. <BR>
 * <BR>
 *   <search condition> mandatory item*<BR>
 *   <DIR>
 *     consignor code *<BR>
 *     storage plan date <BR>
 *     item code <BR>
 *     case piece flag* <BR>
 *     status flag is standby <BR>
 *     job type is storage <BR>
 *   </DIR>
 *   <search table><BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.display process(<CODE>getEntities()</CODE>method )<BR>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   1.fetch the display info from the search result<BR>
 *   Set search result in the work information array and return it. <BR>
 * <BR>
 *   <display items>
 *   <DIR>
 *     storage plan date <BR>
 *     item code <BR>
 *     item name <BR>
 *     packed qty per case <BR>
 *     remaining qty per case <BR>
 *     packed qty per piece <BR>
 *     remaining qty per piece <BR>
 *   </DIR>
 *   <The order of display>
 *   <DIR>
 *     Ascending order of Storage Plan Date,Item code,case piece flag <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/27</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 06:32:14 $
 * @author  $Author: suresh $
 */
public class SessionAsStoragePlanRet  extends SessionRet
{
	//#CM40749
	// Class fields --------------------------------------------------
	//#CM40750
	/**
	 * For consignor name acquisition
	 */
	private String wConsignorName = "";

	//#CM40751
	/**
	 * For Item name acquisition
	 */
	private String wItemName = "";


	//#CM40752
	// Class variables -----------------------------------------------

	//#CM40753
	// Class method --------------------------------------------------
	//#CM40754
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn instance to store database connection
	 * @param stParam      AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsStoragePlanRet(Connection conn, AsScheduleParameter stParam) throws Exception
	{
		wConn = conn ;		
		find(stParam) ;
	}
	
	//#CM40755
	// Public methods ------------------------------------------------
	//#CM40756
	/**
	 * Return work info search result. 
	 * @return Work info search result. 
	 */
	public AsScheduleParameter[] getEntities()
	{		
		AsScheduleParameter[] resultArray = null ;
		WorkingInformation[] rData = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{
				rData = (WorkingInformation[])wFinder.getEntities(wStartpoint,wEndpoint) ;
				resultArray = convertToParams(rData) ;
			}
			catch (Exception e)
			{	    		
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		
		return resultArray;
	}
	
	//#CM40757
	// Package methods -----------------------------------------------

	//#CM40758
	// Protected methods ---------------------------------------------

	//#CM40759
	// Private methods -----------------------------------------------
	//#CM40760
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>WorkingInformationFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{		
		WorkingInformationSearchKey stKey = new WorkingInformationSearchKey() ;
		WorkingInformationSearchKey nameKey = new WorkingInformationSearchKey() ;
		
		if(!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			stKey.setConsignorCode(stParam.getConsignorCode()) ;
			nameKey.setConsignorCode(stParam.getConsignorCode()) ;
		}
	
		if(!StringUtil.isBlank(stParam.getPlanDate()))
		{
			stKey.setPlanDate(stParam.getPlanDate()) ;
			nameKey.setPlanDate(stParam.getPlanDate()) ;
		}
	
		if(!StringUtil.isBlank(stParam.getItemCode()))
		{
			stKey.setItemCode(stParam.getItemCode()) ;
			nameKey.setItemCode(stParam.getItemCode()) ;
		}
		
		if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
		{
			stKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE) ;
			nameKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE) ;
		}
		else if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
		{
			stKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE) ;
			nameKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE) ;
		}
		else if(stParam.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
		{
			stKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING) ;
			nameKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING) ;
		}
		
		//#CM40761
		// status flag is standby
		stKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		nameKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		//#CM40762
		// job type is storage
		stKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
		nameKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
						
		//#CM40763
		// set sorting order			
		stKey.setPlanDateOrder(1, true);
		stKey.setItemCodeOrder(2, true);
		stKey.setCasePieceFlagOrder(3, true);

		wFinder = new WorkingInformationFinder(wConn) ;
		//#CM40764
		// open cursor
		wFinder.open() ;
		int count = wFinder.search(stKey);
		wLength = count ;
		wCurrent = 0 ;
		
		//#CM40765
		// fetch the latest name
		nameKey.setConsignorNameCollect("");
		nameKey.setItemName1Collect("");
		nameKey.setRegistDateOrder(1, false);

		WorkingInformationFinder consignorFinder = new WorkingInformationFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(nameKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			WorkingInformation workInfo[] = (WorkingInformation[]) consignorFinder.getEntities(0, 1);

			if (workInfo != null && workInfo.length != 0)
			{
				if (!StringUtil.isBlank(stParam.getConsignorCode()))
				{
					wConsignorName = workInfo[0].getConsignorName();
				}
				//#CM40766
				// item code
				if (!StringUtil.isBlank(stParam.getItemCode()))
				{
					wItemName = workInfo[0].getItemName1();
				}
			}
		}
		consignorFinder.close();
	}
	
	//#CM40767
	/**
	 * This class converts the Working Information entity into the AsScheduleParameter parameter. 
	 * @param rData entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE> class which set work information
	 */
	private AsScheduleParameter[] convertToParams(WorkingInformation[] rData)
	{
		AsScheduleParameter[] stParam = null ;
		if((rData != null) && (rData.length != 0))
		{
			stParam = new AsScheduleParameter[rData.length] ;
			
			for(int i=0; i < rData.length; i++)
			{
				stParam[i] = new AsScheduleParameter() ;
				//#CM40768
				// consignor name
				stParam[i].setConsignorNameDisp(wConsignorName);
				//#CM40769
				// storage plan date
				stParam[i].setPlanDate(rData[i].getPlanDate()) ; 
				//#CM40770
				// item code
				stParam[i].setItemCode(rData[i].getItemCode()); 
				//#CM40771
				// item name
				stParam[i].setItemName(rData[i].getItemName1()); 
				stParam[i].setItemNameDisp(wItemName);
				//#CM40772
				// packed qty per case
				stParam[i].setEnteringQty(rData[i].getEnteringQty()); 
				//#CM40773
				// remaining qty per case
				stParam[i].setStorageCaseQty(DisplayUtil.getCaseQty(rData[i].getPlanEnableQty(),
									rData[i].getEnteringQty(), rData[i].getCasePieceFlag())); 
				//#CM40774
				// packed qty per piece
				stParam[i].setBundleEnteringQty(rData[i].getBundleEnteringQty()); 
				//#CM40775
				// remaining qty per piece
				stParam[i].setStoragePieceQty(DisplayUtil.getPieceQty(rData[i].getPlanEnableQty(),
									rData[i].getEnteringQty(), rData[i].getCasePieceFlag())); 
				//#CM40776
				// case piece flag
				stParam[i].setCasePieceFlag(rData[i].getCasePieceFlag());
				//#CM40777
				// case piece flag name
				stParam[i].setCasePieceFlagNameDisp(DisplayUtil.getPieceCaseValue(rData[i].getCasePieceFlag())); 
				//#CM40778
				// job no.
				stParam[i].setWorkingNo(rData[i].getJobNo()) ; 
				//#CM40779
				// update date/time
				stParam[i].setLastUpdateDate(rData[i].getLastUpdateDate()) ; 
				//#CM40780
				// location no.
				stParam[i].setLocationNo(rData[i].getLocationNo()) ; 
			}
		}		
		return stParam ;
	}
}
//#CM40781
//end of class

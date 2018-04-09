package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570547
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570548
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR> 
 * The class to do data retrieval for Storage Results list box Retrieval processing.<BR>
 * Receive Search condition with Parameter, and do Retrieval of the Storage Results list.<BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageQtyListRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   <CODE>find</CODE> Method is called and Retrieval of Results View information is done. <BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
 *      , Start Storage date<BR>
 *      , End Storage date<BR>
 *      , Item Code<BR>
 *      , Case , PieceFlag<BR>
 *      , WorkFlag : Storage <BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DVRESULTVIEW <BR>
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
 *      , Consignor Code<BR>
 *      , Consignor Name<BR>
 *      , Storage date<BR>
 *      , Storage plan date<BR>
 *      , Item Code<BR>
 *      , Item Name<BR>
 *      , Case , PieceFlag Description<BR>
 *      , Storage Location<BR>
 *      , Packed qty per case<BR>
 *      , Packed qty per bundle<BR>
 *      , PlanCase qty<BR>
 *      , PlanPiece qty<BR>
 *      , Result Case qty<BR>
 *      , Result Piece qty<BR>
 *      , Shortage Case qty<BR>
 *      , Shortage Piece qty<BR>
 *      , Expiry Date<BR>
 *      , CaseITF<BR>
 *	    , Bundle ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:25 $
 * @author  $Author: suresh $
 */
public class SessionStorageQtyListRet extends SessionRet
{
	//#CM570549
	// Class fields --------------------------------------------------

	//#CM570550
	// Class variables -----------------------------------------------

	//#CM570551
	// Class method --------------------------------------------------
	//#CM570552
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:25 $");
	}

	//#CM570553
	// Constructors --------------------------------------------------
	//#CM570554
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageQtyListRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570555
	// Public methods ------------------------------------------------
	//#CM570556
	/**
	 * Return Retrieval result of < CODE>DvResultView</CODE >. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Consignor Code<BR>
	 *      , Consignor Name<BR>
	 *      , Storage date<BR>
	 *      , Storage plan date<BR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 *      , Case , PieceFlag Description<BR>
	 *      , Storage Location<BR>
	 *      , Packed qty per case<BR>
	 *      , Packed qty per bundle<BR>
	 *      , PlanCase qty<BR>
	 *      , PlanPiece qty<BR>
	 *      , Result Case qty<BR>
	 *      , Result Piece qty<BR>
	 *      , Shortage Case qty<BR>
	 *      , Shortage Piece qty<BR>
	 *      , Expiry Date<BR>
	 *      , CaseITF<BR>
	 *	    , Bundle ITF<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DvResultView
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		ResultView[] resultView = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultView = (ResultView[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(resultView);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;

	}

	//#CM570557
	// Package methods -----------------------------------------------

	//#CM570558
	// Protected methods ---------------------------------------------

	//#CM570559
	// Private methods -----------------------------------------------
	//#CM570560
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>ResultViewFinder</code > as instance variable for Retrieval.<BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter stParam) throws Exception
	{
		ResultViewSearchKey sKey = new ResultViewSearchKey();
		//#CM570561
		// Set Search condition
		//#CM570562
		// Consignor Code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM570563
		// Start Storage date
		if (!StringUtil.isBlank(stParam.getFromStorageDate()))
		{
			sKey.setWorkDate(WmsFormatter.toParamDate(stParam.getFromStorageDate()), ">=");
		}
		//#CM570564
		// End Storage date
		if (!StringUtil.isBlank(stParam.getToStorageDate()))
		{
			sKey.setWorkDate(WmsFormatter.toParamDate(stParam.getToStorageDate()), "<=");
		}
		//#CM570565
		// Item Code
		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			sKey.setItemCode(stParam.getItemCode());
		}
		//#CM570566
		// Flag
		if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			sKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_NOTHING);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			sKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			sKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
		{
			sKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_MIX);
		}
		else if (stParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_ALL))
		{
			//#CM570567
			//do nothing
		}
		//#CM570568
		// WorkFlag
		sKey.setJobType(SystemDefine.JOB_TYPE_STORAGE);

		//#CM570569
		// The order of display
		sKey.setWorkDateOrder(1, true);
		sKey.setPlanDateOrder(2, true);
		sKey.setItemCodeOrder(3, true);
		sKey.setWorkFormFlagOrder(4, true);
		sKey.setRegistDateOrder(5, true);
		sKey.setResultQtyOrder(6, true);

		wFinder = new ResultViewFinder(wConn);
		//#CM570570
		// Cursor open
		wFinder.open();

		int count = wFinder.search(sKey);
		wLength = count;
		wCurrent = 0;

	}

	//#CM570571
	/**
	 * Convert the DvResultView entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety) throws Exception
	{
		StorageSupportParameter[] stParam = null;
		ResultView[] resultView = (ResultView[]) ety;
		if ((resultView != null) && (resultView.length != 0))
		{
			stParam = new StorageSupportParameter[resultView.length];
			String registDate = "1";
			for (int i = 0; i < resultView.length; i++)
			{
				stParam[i] = new StorageSupportParameter();

				//#CM570572
				//Set the newest Consignor Code matching with Add date when Consignor Name differs for Same Consignor Code.
				if ((i == 0)
					|| (Long.parseLong(registDate) < Long.parseLong(getDateValue(resultView[i].getRegistDate()))))
				{
					registDate = getDateValue(resultView[i].getRegistDate());
					stParam[0].setConsignorCode(resultView[i].getConsignorCode());
					stParam[0].setConsignorName(resultView[i].getConsignorName());
				}
				stParam[i].setStorageDate(resultView[i].getWorkDate());
				stParam[i].setStoragePlanDate(resultView[i].getPlanDate());
				stParam[i].setItemCode(resultView[i].getItemCode());
				stParam[i].setItemName(resultView[i].getItemName1());
				stParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(resultView[i].getWorkFormFlag()));
				stParam[i].setStorageLocation(resultView[i].getResultLocationNo());
				stParam[i].setEnteringQty(resultView[i].getEnteringQty());
				stParam[i].setBundleEnteringQty(resultView[i].getBundleEnteringQty());
				stParam[i].setPlanCaseQty(
					DisplayUtil.getCaseQty(
						resultView[i].getPlanEnableQty(),
						resultView[i].getEnteringQty(),
						resultView[i].getWorkFormFlag()));
				stParam[i].setPlanPieceQty(
					DisplayUtil.getPieceQty(
						resultView[i].getPlanEnableQty(),
						resultView[i].getEnteringQty(),
						resultView[i].getWorkFormFlag()));
				stParam[i].setResultCaseQty(
					DisplayUtil.getCaseQty(
						resultView[i].getResultQty(),
						resultView[i].getEnteringQty(),
						resultView[i].getWorkFormFlag()));
				stParam[i].setResultPieceQty(
					DisplayUtil.getPieceQty(
						resultView[i].getResultQty(),
						resultView[i].getEnteringQty(),
						resultView[i].getWorkFormFlag()));
				stParam[i].setShortageQty(
					DisplayUtil.getCaseQty(
						resultView[i].getShortageCnt(),
						resultView[i].getEnteringQty(),
						resultView[i].getWorkFormFlag()));
				stParam[i].setShortagePieceQty(
					DisplayUtil.getPieceQty(
						resultView[i].getShortageCnt(),
						resultView[i].getEnteringQty(),
						resultView[i].getWorkFormFlag()));
				stParam[i].setITF(resultView[i].getItf());
				stParam[i].setBundleITF(resultView[i].getBundleItf());
				stParam[i].setUseByDate(resultView[i].getResultUseByDate());
				stParam[i].setSystemDiscKey(resultView[i].getSystemDiscKey());

				AreaOperator ao = new AreaOperator(wConn);
				stParam[i].setRetrievalAreaName(ao.getAreaName(resultView[i].getAreaNo()));
			}
		}
		return stParam;
	}

	//#CM570573
	/**
	 * This Method converts the date into the character string. <BR>
	 * <BR>
	 * @param date Date Date
	 * @return Converted character string. 
	 */
	private String getDateValue(Date date)
	{
		String datNumS = null;
		if (date != null)
		{
			//#CM570574
			//Make the pattern according to 24 hours. 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			datNumS = sdf.format(date).trim();
		}

		return datNumS;
	}

}
//#CM570575
//end of class

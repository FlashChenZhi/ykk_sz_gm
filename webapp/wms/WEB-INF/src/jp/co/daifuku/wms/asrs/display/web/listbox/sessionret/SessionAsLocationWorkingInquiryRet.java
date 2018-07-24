// $Id: SessionAsLocationWorkingInquiryRet.java,v 1.2 2006/10/26 07:46:42 suresh Exp $

//#CM40602
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.sessionret;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM40603
/**
 * Designer : Y.Osawa<BR>
 * Maker 	: Y.Osawa<BR> 
 * <BR>
 * This class is used to search data for ASRS location based stock list<BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session <BR>
 * delete from session after use <BR>
 * The following process are called in this class <BR>
 * <BR>
 * 1.search process(<CODE>SessionAsLocationWorkingInquiryRet(Connection conn,AsScheduleParameter param)</CODE>method ) <BR>
 * <DIR>
 *   executed during listbox screen initialization <BR>
 *   <CODE>find(AsScheduleParameter param)</CODE>method is called and stock info is retrieved.  <BR>
 * <BR>
 *   <search condition> mandatory item* <BR>
 *   <DIR>
 *     warehouse * <BR>
 *     consignor code *<BR>
 *     start location <BR>
 *     end location <BR>
 *     item code <BR>
 *     case piece flag <BR>
 *     Stock status is center stock*<BR>
 *     Stock qty is more than 1*<BR>
 *   </DIR>
 *   <search table><BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 *   <return data> <BR>
 *   <DIR>
 *     Qty<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * 2.display process(<CODE>getEntities()</CODE>method )<BR>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   1.fetch the display info from the search result<BR>
 *   Set search result in the stock info array and return it. <BR>
 *   <display items><BR>
 *   <DIR>
 *     warehouse <BR>
 *     consignor code <BR>
 *     consignor name <BR>
 *     location no. <BR>
 *     item code <BR>
 *     item name <BR>
 *     packed qty per case <BR>
 *     packed qty per piece <BR>
 *     stock case qty <BR>
 *     stock piece qty <BR>
 *     available case qty <BR>
 *     available piece qty<BR>
 *     case piece flag <BR>
 *     case piece flag name <BR>
 *     expiry date <BR>
 *     Case ITF <BR>
 *     Bundle ITF <BR>
 *     storage date/time <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/17</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 07:46:42 $
 * @author  $Author: suresh $
 */
public class SessionAsLocationWorkingInquiryRet extends SessionRet
{
	//#CM40604
	// Class fields --------------------------------------------------

	//#CM40605
	// Class variables -----------------------------------------------
	//#CM40606
	/**
	 * For consignor name acquisition
	 */
	private String wConsignorName = "";

	//#CM40607
	// Class method --------------------------------------------------
	//#CM40608
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 07:46:42 $");
	}

	//#CM40609
	// Constructors --------------------------------------------------
	//#CM40610
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsLocationWorkingInquiryRet(Connection conn, AsScheduleParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM40611
		// search
		find(param);
	}

	//#CM40612
	// Public methods ------------------------------------------------
	//#CM40613
	/**
	 * Return search result of <CODE>DMSTOCK</CODE>. 
	 * @return DMSTOCK search result
	 */
	public AsScheduleParameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		Stock[] wStock = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				wStock = (Stock[]) ((StockFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM40614
				// The acquisition value is converted into the return data and it sets it in AsScheduleParameter. 
				resultArray = getDispData(wStock);

			}
			catch (Exception e)
			{
				//#CM40615
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40616
	// Package methods -----------------------------------------------

	//#CM40617
	// Protected methods ---------------------------------------------

	//#CM40618
	// Private methods -----------------------------------------------
	//#CM40619
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	private void find(AsScheduleParameter param) throws Exception
	{
		int count = 0;

		StockSearchKey wkey = new StockSearchKey();
		StockSearchKey consignorkey = new StockSearchKey();
		//#CM40620
		// do search
		//#CM40621
		// warehouse (area no.)
		if (!StringUtil.isBlank(param.getAreaNo()))
		{
			wkey.setAreaNo(param.getAreaNo());
			consignorkey.setAreaNo(param.getAreaNo());
		}
		//#CM40622
		// consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			wkey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		//#CM40623
		// start location no.
		if (!StringUtil.isBlank(param.getFromLocationNo()))
		{
			wkey.setLocationNo(param.getFromLocationNo(), ">=");
			consignorkey.setLocationNo(param.getFromLocationNo(), ">=");
		}
		//#CM40624
		// end locationNo
		if (!StringUtil.isBlank(param.getToLocationNo()))
		{
			wkey.setLocationNo(param.getToLocationNo(), "<=");
			consignorkey.setLocationNo(param.getToLocationNo(), "<=");
		}
		//#CM40625
		// item code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			wkey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		//#CM40626
		// Case piece flag(Do not set all the search condition in Search key at all. )
		if (!StringUtil.isBlank(param.getCasePieceFlag()))
		{
			if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				wkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_NOTHING);
			}
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				wkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
			}
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				wkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
				consignorkey.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
		}

		//#CM40627
		// stock status is "center stock"
		wkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		consignorkey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM40628
		// stock qty is 1 or more
		wkey.setStockQty(0, ">");
		consignorkey.setStockQty(0, ">");

		wkey.setLocationNoOrder(1, true);
		wkey.setItemCodeOrder(2, true);
		wkey.setCasePieceFlagOrder(3, true);
		wkey.setUseByDateOrder(4,true);
		wFinder = new StockFinder(wConn);
		//#CM40629
		// open cursor
		wFinder.open();
		count = ((StockFinder) wFinder).search(wkey);
		//#CM40630
		// initialize
		wLength = count;
		wCurrent = 0;

		//#CM40631
		// fetch consignor name
		consignorkey.setConsignorNameCollect("");
		consignorkey.setLastUpdateDateOrder(1, false);

		StockFinder consignorFinder = new StockFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			Stock stock[] = (Stock[]) consignorFinder.getEntities(0, 1);

			if (stock != null && stock.length != 0)
			{
				wConsignorName = stock[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	//#CM40632
	/**
	 * This class converts stock entity to AsScheduleParameter
	 * @param result entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class set with stock info
	 */
	private AsScheduleParameter[] getDispData(Stock[] result)
	{
		Vector vec = new Vector();
		AsScheduleParameter[] resultArray = null;

		for (int lc = 0; lc < result.length; lc++)
		{
			AsScheduleParameter param = new AsScheduleParameter();
			
			//#CM40633
			// save case piece flag
			String casepieceFlag = result[lc].getCasePieceFlag(); 
			
			//#CM40634
			// warehouse (area no.)
			param.setAreaNo(result[lc].getAreaNo());
			//#CM40635
			// consignor code
			param.setConsignorCode(result[lc].getConsignorCode());
			//#CM40636
			// consignor name
			param.setConsignorName(wConsignorName);
			//#CM40637
			// location no.
			param.setLocationNo(result[lc].getLocationNo());
			//#CM40638
			// item code
			param.setItemCode(result[lc].getItemCode());
			//#CM40639
			// item name
			param.setItemName(result[lc].getItemName1());
			//#CM40640
			// packed qty per case
			param.setEnteringQty(result[lc].getEnteringQty());
			//#CM40641
			// packed qty per piece
			param.setBundleEnteringQty(result[lc].getBundleEnteringQty());

			//#CM40642
			// stock case qty
			param.setStockCaseQty(DisplayUtil.getCaseQty(result[lc].getStockQty(), result[lc].getEnteringQty(), casepieceFlag));
			//#CM40643
			// stock piece qty
			param.setStockPieceQty(DisplayUtil.getPieceQty(result[lc].getStockQty(), result[lc].getEnteringQty(), casepieceFlag));
			//#CM40644
			// available case qty
			param.setAllocateCaseQty(DisplayUtil.getCaseQty((result[lc].getAllocationQty()), result[lc].getEnteringQty(), casepieceFlag));
			//#CM40645
			// available piece qty
			param.setAllocatePieceQty(DisplayUtil.getPieceQty((result[lc].getAllocationQty()), result[lc].getEnteringQty(), casepieceFlag));

			//#CM40646
			// case piece flag name
			param.setCasePieceFlagNameDisp(DisplayUtil.getPieceCaseValue(casepieceFlag));
			//#CM40647
			// Case ITF
			param.setITF(result[lc].getItf());
			//#CM40648
			// Bundle ITF
			param.setBundleITF(result[lc].getBundleItf());
			//#CM40649
			// storage date/time
			param.setInStockDate(result[lc].getInstockDate());
			//#CM40650
			// expiry date
			param.setUseByDate(result[lc].getUseByDate());

			vec.addElement(param);
		}
		resultArray = new AsScheduleParameter[vec.size()];
		vec.copyInto(resultArray);

		return resultArray;

	}
}
//#CM40651
//end of class

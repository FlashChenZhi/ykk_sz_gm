// $Id: SessionAsLocationDetailRet.java,v 1.2 2006/10/26 07:47:33 suresh Exp $

//#CM40532
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.sessionret;

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASStockFinder;
import jp.co.daifuku.wms.asrs.entity.ASStock;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM40533
/**
 * Designer : C.Kaminishizono<BR>
 * Maker : C.Kaminishizono<BR>
 * <BR>
 * The class to retrieve data for the stock list box according to ASRSlocation no. <BR>
 * fetch the search conditions as parameter and search stockinfo<BR>
 * in addition, keep the instance of this class in a session<BR>
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionAsLocationDetailRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(SessionAsLocationDetailRet)</CODE>method is called and stock info is retrieved. <BR>
 * <BR>
 *   <Input data>*mandatory items
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th><tr>
 *     <tr><td>*</td><td>warehouse No(StationNo)</td><td>:</td><td>WareHouseNo</td></tr>
 *     <tr><td>*</td><td>location status</td><td>:</td><td>SearchStatus</td></tr>
 *     <tr><td></td><td>consignor code</td><td>:</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>Consignor description(For display)</td><td>:</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>item code</td><td>:</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>Item description(For display)</td><td>:</td><td>ItemName</td></tr>
 *     <tr><td>*</td><td>Case piece flag</td><td>:</td><td>CasePieceFlag</td></tr>
 *   </table>
 *   </DIR>
 *   <search table>
 *   <DIR>
 *     Location Table:SHELF <BR>
 *     Palette Table:PALETTE <BR>
 *     Stock Table:DMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2.display process(<CODE>getEntities()</CODE>method )<BR></B>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   fetch the display info from the search result<BR>
 *   set and return the search results as <CODE>AsScheduleParameter</CODE> array<BR>
 * <BR>
 *   <return data>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th></tr>
 *     <tr><td></td><td>location</td><td>:</td><td>LocationNo</td></tr>
 *     <tr><td></td><td>status</td><td>:</td><td>StatusName</td></tr>
 *     <tr><td></td><td>consignor code</td><td>:</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>consignor name</td><td>:</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>Item code</td><td>:</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>item name</td><td>:</td><td>ItemName</td></tr>
 *     <tr><td></td><td>packed qty per case</td><td>:</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>packed qty per bundle</td><td>:</td><td>BudleEnteringQty</td></tr>
 *     <tr><td></td><td>Stock Case qty</td><td>:</td><td>StockCaseQty</td></tr>
 *     <tr><td></td><td>Stock Piece qty</td><td>:</td><td>StockPieceQty</td></tr>
 *     <tr><td></td><td>Case ITF</td><td>:</td><td>Itf</td></tr>
 *     <tr><td></td><td>Bundle ITF</td><td>:</td><td>BundleItf</td></tr>
 *     <tr><td></td><td>classification(For display)</td><td>:</td><td>CasePiceFlagNameDisp</td></tr>
 *     <tr><td></td><td>storage date/time</td><td>:</td><td>InstockDate</td></tr>
 *     <tr><td></td><td>expiry date</td><td>:</td><td>UseByDate</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>C.Kaminishizono</TD><TD>New</TD></TR>
 * <TR><TD>2006/01/17</TD><TD>Y.Okamura</TD><TD>Add stock to Retrieval table</TD></TR>
 * </TABLE> <BR>
 * 
 * @author $Revision: 1.2 $, $Date: 2006/10/26 07:47:33 $
 * @version $Author: suresh $
 */
public class SessionAsLocationDetailRet extends SessionRet
{
	//#CM40534
	// Class fields --------------------------------------------------
	//#CM40535
	/**
	 * For consignor name acquisition
	 */
	private String wConsignorName = "";

	//#CM40536
	/**
	 * For Item name acquisition
	 */
	private String wItemName = "";


	//#CM40537
	// Class variables -----------------------------------------------
	//#CM40538
	/**
	 * Maintain <code>DatabaseFinder</code> reference. <BR>
	 * Each class has the reference to Finder by all classes which have succeeded to this < code>SessionRet</code > now. 
	 */
	protected ASStockFinder wFinder;

	//#CM40539
	// Class method --------------------------------------------------

	//#CM40540
	// Constructors --------------------------------------------------
	//#CM40541
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param conn  instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsLocationDetailRet(Connection conn, AsScheduleParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM40542
	// Public methods ------------------------------------------------
	//#CM40543
	/**
	 * Return search result which contains the location, the palette, and stock. 
	 * 
	 * @return Location, Palette, Search result of Stock
	 */
	public AsScheduleParameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		ASStock[] rsltEnt = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				rsltEnt = (ASStock[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rsltEnt);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM40544
	// Package methods -----------------------------------------------

	//#CM40545
	// Protected methods ---------------------------------------------
	//#CM40546
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>StockFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result
	 * @param param AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	protected void find(AsScheduleParameter param) throws Exception
	{
		StockSearchKey searchKey = new StockSearchKey();
		StockSearchKey nameKey = new StockSearchKey();

		//#CM40547
		// consignor code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			searchKey.setConsignorCode(param.getConsignorCode());
			nameKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM40548
		// item code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			searchKey.setItemCode(param.getItemCode());
			nameKey.setItemCode(param.getItemCode());
		}
		//#CM40549
		// Case piece flag
		if (!StringUtil.isBlank(param.getCasePieceFlag()))
		{
			if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_NOTHING))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
				nameKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_CASE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
				nameKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE);
			}
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_PIECE))
			{
				searchKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
				nameKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE);
			}
			else if (param.getCasePieceFlag().equals(AsScheduleParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM40550
				// do nothing
			}
		}

		searchKey.setConsignorCodeOrder(1, true);
		searchKey.setItemCodeOrder(2, true);
		searchKey.setCasePieceFlagOrder(3, true);
		searchKey.setUseByDateOrder(4, true);
		
		String[] status = param.getSearchStatus();
		String whStNo = param.getWareHouseNo();
		
		wFinder = new ASStockFinder(wConn);
		//#CM40551
		//Open Cursor
		wFinder.open();
		int count = wFinder.search(searchKey, whStNo, status);
		//#CM40552
		//Set count in wLength. 

		wLength = count;
		wCurrent = 0;
		
		//#CM40553
		// fetch the latest name
		nameKey.setAreaNo(whStNo);

		nameKey.setConsignorNameCollect("");
		nameKey.setItemName1Collect("");
		nameKey.setLastUpdateDateOrder(1, false);

		StockFinder consignorFinder = new StockFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(nameKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			Stock stock[] = (Stock[]) consignorFinder.getEntities(0, 1);

			if (stock != null && stock.length != 0)
			{
				if (!StringUtil.isBlank(param.getConsignorCode()))
				{
					wConsignorName = stock[0].getConsignorName();
				}
				//#CM40554
				// item code
				if (!StringUtil.isBlank(param.getItemCode()))
				{
					wItemName = stock[0].getItemName1();
				}
			}
		}
		consignorFinder.close();
	}
	
	//#CM40555
	/**
	 * This class converts the AsStock entity into the AsScheduleParameter parameter. 
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class set with stock info
	 */
	protected Parameter[] convertToParams(Entity[] ety)
	{
	
		ASStock[] resultEnt = (ASStock[]) ety;
		if (resultEnt == null || resultEnt.length==0)
		{
			return null;
		}
		
		AsScheduleParameter[] param = new AsScheduleParameter[resultEnt.length];
		for (int i = 0; i < resultEnt.length; i++)
		{
			int entQty = resultEnt[i].getEnteringQty();
			String cpFlg = resultEnt[i].getCasePieceFlag();
			param[i] = new AsScheduleParameter();
			param[i].setLocationNo(resultEnt[i].getStationNumber());
			param[i].setLocationStatusName(getStatusName(resultEnt[i]));
			param[i].setConsignorCode(resultEnt[i].getConsignorCode());
			param[i].setConsignorName(resultEnt[i].getConsignorName());
			param[i].setConsignorNameDisp(wConsignorName);
			param[i].setItemCode(resultEnt[i].getItemCode());
			param[i].setItemName(resultEnt[i].getItemName1());
			param[i].setItemNameDisp(wItemName);
			param[i].setEnteringQty(entQty);
			param[i].setBundleEnteringQty(resultEnt[i].getBundleEnteringQty());
			param[i].setStockCaseQty(DisplayUtil.getCaseQty(resultEnt[i].getStockQty(), entQty, cpFlg));
			param[i].setStockPieceQty(DisplayUtil.getPieceQty(resultEnt[i].getStockQty(), entQty, cpFlg));
			param[i].setITF(resultEnt[i].getItf());
			param[i].setBundleITF(resultEnt[i].getBundleItf());
			param[i].setCasePieceFlagNameDisp(DisplayUtil.getPieceCaseValue(cpFlg));
			param[i].setInStockDate(resultEnt[i].getInstockDate());
			param[i].setUseByDate(resultEnt[i].getUseByDate());
			param[i].setStatusFlag(Integer.toString(getStatusFlag(resultEnt[i])));
		}
		
		return param;
	}
	
	//#CM40556
	/**
	 * search Return status to which the location corresponds from result by the name mark. 
	 * @param stk search Entity instance which contains result
	 * @return Location status (Description)
	 */
	protected String getStatusName(ASStock stk)
	{
		String statusName = "";
		//#CM40557
		// Location status description
		if (stk.getAccessNGFlag() == Shelf.ACCESS_NG)
		{
			//#CM40558
			// in accessible location
			statusName = DisplayText.getText("LBL-A0089");
		}
		else if (stk.getShelfStatus() == Shelf.STATUS_NG)
		{
			//#CM40559
			// restricted location
			statusName = DisplayText.getText("LBL-A0088");
		}
		else if (stk.getShelfPresence() == Shelf.PRESENCE_EMPTY)
		{
			//#CM40560
			// empty location
			statusName = DisplayText.getText("LBL-A0081");
		}
		else if (stk.getPaletteEmpty() == Palette.STATUS_EMPTY)
		{
			//#CM40561
			// Empty Palette
			statusName = DisplayText.getText("LBL-A0091");
		}
		else if (stk.getPaletteStatus() == Palette.IRREGULAR)
		{
			//#CM40562
			// Error location
			statusName = DisplayText.getText("LBL-A0087");
		}
		else if (stk.getPaletteStatus() == Palette.REGULAR)
		{
			//#CM40563
			// result location
			statusName = DisplayText.getText("LBL-A0086");
		}
		return statusName;

	}
	
	//#CM40564
	/**
	 * search Return status to which the location corresponds from result by the name mark. 
	 * @param stk search Entity instance which contains result
	 * @return Location status (Description)
	 */
	protected int getStatusFlag(ASStock stk)
	{
		int statusFlag = 0;
		//#CM40565
		// Location status description
		if (stk.getShelfStatus() == Shelf.STATUS_NG)
		{
			//#CM40566
			// restricted location
			statusFlag = AsScheduleParameter.STATUS_UNAVAILABLE;
		}
		else if (stk.getShelfPresence() == Shelf.PRESENCE_EMPTY)
		{
			//#CM40567
			// empty location
			statusFlag = AsScheduleParameter.STATUS_EMPTY;
		}
		else if (stk.getPaletteEmpty() == Palette.STATUS_EMPTY)
		{
			//#CM40568
			// Empty Palette
			statusFlag = AsScheduleParameter.STATUS_EMPTYPALETTE;
		}
		else if (stk.getPaletteStatus() == Palette.IRREGULAR)
		{
			//#CM40569
			// Error location
			statusFlag = AsScheduleParameter.STATUS_IRREGULAR;
		}
		else if (stk.getPaletteStatus() == Palette.REGULAR)
		{
			//#CM40570
			// result location
			statusFlag = AsScheduleParameter.STATUS_STORAGED;
		}
		return statusFlag;

	}

	//#CM40571
	// Private methods -----------------------------------------------
}
//#CM40572
//end of class

package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570701
/*
 * Created on 2004/09/29
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570702
/**
 * Designer : Y.Hirata <BR>
 * Maker : Y.Hirata <BR>
 * <BR>
 * The class to do data retrieval for the Stock List box (stock movement).<BR>
 * Receive Search condition as Parameter, and do Retrieval of Stock List. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * This class processes it as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageStockMoveRet</CODE>Constructor)<BR>
 * 	<DIR>
 * 	It is done when initial data of the list box is displayed.<BR>
 * 	<CODE>find</CODE> Method is called, the inventory information is done and Retrieval is done. <BR>
 * 	<BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code*<BR>
 *      , Item Code*<BR>
 *      , Location before movement<BR>
 *      , Expiry Date<BR>
 *      , Stock Status : Central stock* <BR>
 *      , Allocated qty : More than 1*<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DMSTOCK <BR>
 *   </DIR>
 * 	</DIR>
 * 	<BR>
 * 2.Display record acquisition processing (<CODE>getEntities</CODE> Method)<BR>
 * 	<DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * 	<BR>
 *   <Display Item>
 *   <DIR>
 *      , Consignor Code<BR>
 *      , Consignor Name<BR>
 *      , Item Code<BR>
 *      , Item Name<BR>
 *      , Location<BR>
 *      , Packed qty per case<BR>
 *      , Packed qty per bundle<BR>
 *      , Allocated Case qty<BR>
 *      , Allocated Piece qty<BR>
 *      , Expiry Date<BR>
 *   </DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/05</TD><TD>Y.Hirata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author: suresh $
 * @version $Revision: 1.2 $ $Date: 2006/12/07 08:57:23 $
 */
public class SessionStorageStockMoveRet extends SessionRet
{
	//#CM570703
	// Class fields --------------------------------------------------

	//#CM570704
	// Class variables -----------------------------------------------
	//#CM570705
	/**
	 * Consignor Name to be displayed
	 */
	private String wConsignorName = "";

	//#CM570706
	// Class method --------------------------------------------------
	//#CM570707
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:23 $");
	}

	//#CM570708
	// Constructors --------------------------------------------------
	//#CM570709
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageStockMoveRet(Connection conn, StorageSupportParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM570710
	// Public methods ------------------------------------------------
	//#CM570711
	/**
	 * Return Retrieval result of <CODE>DmStock</CODE>. <BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *      , Consignor Code<BR>
	 *      , Consignor Name<BR>
	 *      , Item Code<BR>
	 *      , Item Name<BR>
	 *      , Location<BR>
	 *      , Packed qty per case<BR>
	 *      , Packed qty per bundle<BR>
	 *      , Allocated Case qty<BR>
	 *      , Allocated Piece qty<BR>
	 *      , Expiry Date<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DmStock
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		Stock[] stock = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				stock = (Stock[]) ((StockFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = (StorageSupportParameter[]) convertToStorageSupportParams(stock);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM570712
	// Package methods -----------------------------------------------

	//#CM570713
	// Protected methods ---------------------------------------------
	//#CM570714
	/**
	 * Acquire the inventory information to display it in the list. <BR>
	 * Acquire and retrieve the latest inventory information of Add Date for the parameter set in the Search condition.<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Do the inventory information in Retrieval with Consignor Code, Item Code, and Location and acquire latest Item Name. <BR>
	 * 		2.Add Expiry Date to the condition of one, acquire latest Packed qty, and calculate Allocated Case qty  and Allocated Piece qty. <BR>
	 * </DIR>
	 * <BR>
	 * @param param Search condition.
	 * @return Parameter where acquisition data is stored. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	protected StockControlParameter getDisplayStockData(Stock param) throws Exception
	{

		StockControlParameter dispStock = new StockControlParameter();

		//#CM570715
		// Finder instance generation
		StockFinder stockFinder = new StockFinder(wConn);
		StockSearchKey stockSearchKey = new StockSearchKey();
		StockHandler wObj = new StockHandler(wConn);

		//#CM570716
		/*
		 *  Do the inventory information in Retrieval with Consignor Code and Item Code and acquire latest Item Name. 
		 */
		//#CM570717
		// Set Search condition
		//#CM570718
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			stockSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570719
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			stockSearchKey.setItemCode(param.getItemCode());
		}
		
		//#CM570720
		//Location No
		stockSearchKey.setLocationNo(param.getLocationNo());
		
		//#CM570721
		//Status flag : Central stock
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM570722
		// The order of order
		stockSearchKey.setLastUpdateDateOrder(1, false);

		//#CM570723
		// Latest Item Name is acquired from Consignor Code and Item Code. 
		stockFinder.open();
		int nameCount = stockFinder.search(stockSearchKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			Stock[] stock = (Stock[]) stockFinder.getEntities(0, 1);

			if (stock != null && stock.length != 0)
			{
				dispStock.setItemName(stock[0].getItemName1());
			}
		}
		stockFinder.close();

		//#CM570724
		/*
		 *  Do the inventory information in Retrieval with Consignor Code, Item Code, Location, and Expiry Date and acquire latest Packed qty per case  and Packed qty per bundle. 
		 *  Acquire Total of Allocated Case qty and Allocated Piece qty. 
		 */
		//#CM570725
		// To succeed current Search condition, do not clear the key.

		//#CM570726
		// Location
		if (!StringUtil.isBlank(param.getLocationNo()))
		{
			stockSearchKey.setLocationNo(param.getLocationNo());
		}
		//#CM570727
		// Expiry Date(Do not do the blank check because there is data of NULL, too. )
		stockSearchKey.setUseByDate(param.getUseByDate());

		long allocateCaseQty = 0;
		long allocatePieceQty = 0;
		//#CM570728
		// Acquire latest Packed qty per case  in Consignor Code, Item Code, Location, and Expiry Date and Packed qty per bundle. 
		//#CM570729
		// Allocated Case qty and Allocated Piece qty are calculated. 
		Stock stock[] = (Stock[]) wObj.find(stockSearchKey);
		if (stock != null && stock.length > 0 && stock.length <= DatabaseFinder.MAXDISP)
		{

			for (int i = 0; i < stock.length; i++)
			{
				if (i == 0)
				{
					//#CM570730
					// Packed qty per case
					dispStock.setEnteringQty(stock[0].getEnteringQty());
					//#CM570731
					// Packed qty per bundle
					dispStock.setBundleEnteringQty(stock[0].getBundleEnteringQty());
				}

				int allocationQty = 0;

				//#CM570732
				// Allocated qty
				allocationQty = stock[i].getAllocationQty();
				//#CM570733
				// Accumulation of Allocated Case qty
				allocateCaseQty = allocateCaseQty + DisplayUtil.getCaseQty(allocationQty, stock[i].getEnteringQty());
				//#CM570734
				// Accumulation of Allocated Piece qty
				allocatePieceQty = allocatePieceQty + DisplayUtil.getPieceQty(allocationQty, stock[i].getEnteringQty());
			}

			//#CM570735
			// Allocated Case qty
			dispStock.setTotalAllocateCaseQty(allocateCaseQty);
			//#CM570736
			// Allocated Piece qty
			dispStock.setTotalAllocatePieceQty(allocatePieceQty);

		}

		return dispStock;
	}

	//#CM570737
	// Private methods -----------------------------------------------
	//#CM570738
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>StockFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param stParam StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter stParam) throws Exception
	{
		StockSearchKey sKey = new StockSearchKey();
		//#CM570739
		//Set Search condition
		//#CM570740
		//Consignor Code
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			sKey.setConsignorCode(stParam.getConsignorCode());
		}
		//#CM570741
		//Item Code
		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			sKey.setItemCode(stParam.getItemCode());
		}
		//#CM570742
		//Location before movement
		if (!StringUtil.isBlank(stParam.getSourceLocationNo()))
		{
			sKey.setLocationNo(stParam.getSourceLocationNo());
		}
		//#CM570743
		//Expiry Date
		if (!StringUtil.isBlank(stParam.getUseByDate()))
		{
			sKey.setUseByDate(stParam.getUseByDate());
		}
		//#CM570744
		//Status flag : Central stock
		sKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM570745
		// The number which can be drawn does not display the one of 0. 
		sKey.setAllocationQty(0, ">");

		if (stParam.getAreaTypeFlag().equals(StorageSupportParameter.AREA_TYPE_FLAG_NOASRS))
		{
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNo = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			
			//#CM570746
			// Acquire the areas other than ASRS, and add to Search condition. 
			//#CM570747
			// IS NULL Retrieval when there is no pertinent area
			areaNo = AreaOperator.getAreaNo(areaType);
			sKey.setAreaNo(areaNo);
		}

		//#CM570748
		//Set Content of acquisition. 
		sKey.setConsignorCodeCollect("");
		sKey.setItemCodeCollect("");
		sKey.setLocationNoCollect("");
		sKey.setUseByDateCollect("");

		//#CM570749
		//Set the order of the group. 
		sKey.setConsignorCodeGroup(1);
		sKey.setItemCodeGroup(2);
		sKey.setLocationNoGroup(3);
		sKey.setUseByDateGroup(4);

		//#CM570750
		//Set the order of sorting. 
		sKey.setItemCodeOrder(1, true);
		sKey.setLocationNoOrder(2, true);
		sKey.setUseByDateOrder(3, true);

		wFinder = new StockFinder(wConn);

		//#CM570751
		//Cursor open
		wFinder.open();

		int count = ((StockFinder) wFinder).search(sKey);
		//#CM570752
		//Set count in wLength.

		wLength = count;
		wCurrent = 0;

		//#CM570753
		// Consignor Name acquisition
		getDisplayConsignorName(stParam);

	}

	//#CM570754
	/**
	 * Acquire latest Consignor Name to display it in the list. <BR>
	 * <BR>
	 * @param param StorageSupportParameter Search condition.
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void getDisplayConsignorName(StorageSupportParameter param) throws Exception
	{
		//#CM570755
		// Finder instance generation
		StockFinder consignorFinder = new StockFinder(wConn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		//#CM570756
		// Set Search condition
		//#CM570757
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			stockSearchKey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570758
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			stockSearchKey.setItemCode(param.getItemCode());
		}
		//#CM570759
		//Status flag : Central stock
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM570760
		// The order of order
		stockSearchKey.setLastUpdateDateOrder(1, false);

		//#CM570761
		// Consignor NameRetrieval
		consignorFinder.open();
		int nameCount = consignorFinder.search(stockSearchKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			Stock[] stock = (Stock[]) consignorFinder.getEntities(0, 1);

			if (stock != null && stock.length != 0)
			{
				wConsignorName = stock[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}

	//#CM570762
	/**
	 * Convert the Stock entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param ety Entity[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety) throws Exception
	{
		StorageSupportParameter stParam = null;
		StorageSupportParameter[] vstParam = null;
		Vector vec = new Vector();
		Stock[] stock = (Stock[]) ety;
		if ((stock != null) && (stock.length != 0))
		{

			//#CM570763
			// Consignor Code of the acquired inventory information, Item Code , Location , Expiry Date, and the inventory information is Retrieved.
			for (int i = 0; i < stock.length; i++)
			{

				StockControlParameter dispStock = getDisplayStockData(stock[i]);

				stParam = new StorageSupportParameter();

				//#CM570764
				// Consignor Name
				stParam.setConsignorName(wConsignorName);
				//#CM570765
				// Item Code
				stParam.setItemCode(stock[i].getItemCode());
				//#CM570766
				// Item Name
				stParam.setItemName(dispStock.getItemName());
				//#CM570767
				// Location
				stParam.setLocation(stock[i].getLocationNo());
				//#CM570768
				// Packed qty per case
				stParam.setEnteringQty(dispStock.getEnteringQty());
				//#CM570769
				// Packed qty per bundle
				stParam.setBundleEnteringQty(dispStock.getBundleEnteringQty());
				//#CM570770
				// Stock Case qty
				stParam.setTotalStockCaseQty(dispStock.getTotalAllocateCaseQty());
				//#CM570771
				// Stock Piece qty
				stParam.setTotalStockPieceQty(dispStock.getTotalAllocatePieceQty());
				//#CM570772
				// Expiry Date
				stParam.setUseByDate(stock[i].getUseByDate());

				//#CM570773
				// The value is set in vector. 
				vec.addElement(stParam);

			}
		}
		vstParam = new StorageSupportParameter[vec.size()];
		vec.copyInto(vstParam);
		return vstParam;
	}

}
//#CM570774
//end of class

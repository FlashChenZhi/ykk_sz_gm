// $Id: Id0921Operate.java,v 1.2 2006/09/27 03:00:36 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

//#CM10091
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM10092
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * This is the class to execute processing of stock table requesting(ID0921).<BR>
 * Inherit <CODE>IdOperate</CODE> class and implement the required processes.<BR>
 * Receive the Consignor code<BR>
 * Create stock list (item code ascending order) file.<BR>
 * <BR>
 * Obtain the stock list (<CODE>findStockData ()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Consignor code<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:36 $
 * @author  $Author: suresh $
 */
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * This is the class to execute processing of stock table requesting(ID0921).<BR>
 * Inherit <CODE>IdOperate</CODE> class and implement the required processes.<BR>
 * Receive the Consignor code<BR>
 * Create stock list (item code ascending order) file.<BR>
 * <BR>
 * Obtain the stock list (<CODE>findStockData ()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Consignor code<BR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:36 $
 * @author  $Author: suresh $
 */
public class Id0921Operate extends IdOperate
{
	//#CM10093
	// Class fields----------------------------------------------------
	//#CM10094
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0921Operate";
	//#CM10095
	// Class variables -----------------------------------------------
	//#CM10096
	// Constructors --------------------------------------------------
	//#CM10097
	/**
	 * Generate Instance.
	 */
	public Id0921Operate()
	{
		super();
	}

	//#CM10098
	/**
	 * Generate Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn For database connection Connection
	 */
	public Id0921Operate(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM10099
	// Class method --------------------------------------------------
	//#CM10100
	/**
	 * Return Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:36 $";
	}

	//#CM10101
	// Public methods ------------------------------------------------
	//#CM10102
	/**
	 * Obtain the stock list info by the designated condition.<BR>
	 * Obtain the list of stock possible for picking from the Stock info (dmstock).<BR>
	 * Specified Consignor code, Location No., Load size and<BR>
	 * stock status is in Center stock and possible picking qty ( stock qty - allocated qty) is 1 or more and<BR>
	 * Area division of the Area master information linked with the Area No.is 3: from stock data other than ASRS<BR>
	 * Obtain the Stock info list in ascending order of the item code.<BR>
	 * <BR>
	 *     <DIR>
	 *     [Obtained item (count)]<BR>
	 *         <DIR>
	 *         Item code<BR>
	 *         Item name<BR>
	 *         Expiry Date<BR>
	 *         </DIR>
	 *     [search conditions]<BR>
	 *         <TABLE border="1">
	 *         <TR><TD>Consignor code</TD>		             <TD>Consignor code of the electronic statement</TD></TR>
	 *         <TR><TD>Location No</TD>		                 <TD>Location No of the electronic statement</TD></TR>
	 *         <TR><TD>Load size</TD>		                 <TD>Load size of the electronic statement</TD></TR>
	 *         <TR><TD>Stock status</TD>	             <TD>Center Inventory</TD></TR>
	 *         <TR><TD>possible picking qty</TD>		             <TD>1 or more</TD></TR>
	 *         <TR><TD>Area No.</TD>                    <TD>Area Master InfoArea No.</TD></TR>
	 *         <TR><TD>Area Master InfoAreaDivision</TD>  <TD>2:ASRS以外</TD></TR>
	 *         </TABLE>
	 *     [Sorting sequence]<BR>
	 *         <DIR>
	 *         Item code<BR>
	 *         Expiry Date<BR>
	 *         </DIR>
	 *     </DIR>
	 * <DIR>
	 * </DIR>
	 * @param	consignorCode	Consignor code
	 * @param	areaNo			AreaNo
	 * @param	locationNo		Location No
	 * @param	itemForm		Load size
	 * @return		picking possible stock table
	 * @throws NotFoundException  Announce this when picking possible Stock info is not found.
	 * @throws IllegalArgumentException Announce this when illegal Work division is found
	 * @throws OverflowException Announce this when digit number of numeric item overflows.
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws ScheduleException Announce this when unexpected exception occurs in the check Process.
	 */
	public Stock[] findStockData(String consignorCode, String areaNo, String locationNo, String itemForm)
		throws NotFoundException, IllegalArgumentException, OverflowException, ReadWriteException, ScheduleException
	{
		//#CM10103
		// Variable to maintain the possible picking stock after aggregation.
		Stock[] retpossibleStockData = null;
		
		StockSearchKey stockSearchKey = new StockSearchKey();
	
		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		String[] areaNoASRS = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
		
		//#CM10104
		//-----------------
		//#CM10105
		// Set the obtained item (count).
		//#CM10106
		//-----------------
		//#CM10107
		// Item code
		stockSearchKey.setItemCodeCollect("");
		//#CM10108
		// Item name
		stockSearchKey.setItemName1Collect("");
		//#CM10109
		// Expiry Date
		stockSearchKey.setUseByDateCollect("");
		
		//#CM10110
		// Stock qty - This is for calculation of search condition of allocated qty is 1 or more.
		//#CM10111
		// Stock qty
		stockSearchKey.setStockQtyCollect("");
		//#CM10112
		// Allocation No.
		stockSearchKey.setAllocationQtyCollect("");
		
		//#CM10113
		//-----------------
		//#CM10114
		// Set the search conditions.
		//#CM10115
		//-----------------
		//#CM10116
		// Status flag(Center Inventory)
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockSearchKey.setConsignorCode(consignorCode);
		if (! StringUtil.isBlank(areaNo))
		{
			stockSearchKey.setAreaNo(areaNo);
		}
		stockSearchKey.setLocationNo(locationNo);
		if (! itemForm.equals(RFTId0921.ITEM_FORM_All))
		{
			stockSearchKey.setCasePieceFlag(itemForm);
		}
		//#CM10117
		// possible picking qty is 1 or more
		stockSearchKey.setAllocationQty(0, ">");
		
		//#CM10118
		// Obtain the Area other than ASRS and add it to the search conditions.
		//#CM10119
		// Search IS NULL if no corresponding area found.
		areaNoASRS = AreaOperator.getAreaNo(areaType);
		stockSearchKey.setAreaNo(areaNoASRS);

		//#CM10120
		//-----------------
		//#CM10121
		// Set the aggregation condition.
		//#CM10122
		//-----------------
		stockSearchKey.setItemCodeGroup(1);
		stockSearchKey.setItemName1Group(2);
		stockSearchKey.setUseByDateGroup(3);
		stockSearchKey.setStockQtyGroup(4);
		stockSearchKey.setAllocationQtyGroup(5);
		
		//#CM10123
		//-----------------
		//#CM10124
		// Set the sorting sequence.
		//#CM10125
		//-----------------
		stockSearchKey.setItemCodeOrder(1, true);
		stockSearchKey.setItemName1Order(2, true);
		stockSearchKey.setUseByDateOrder(3, true);

		//#CM10126
		//-----------------
		//#CM10127
		// Search Process
		//#CM10128
		//-----------------
		StockHandler stockHandler = new StockHandler(wConn);
		Stock[] stock = (Stock[]) stockHandler.find(stockSearchKey);

		//#CM10129
		// Throw NotFoundException when Stock info cannot be obtained.
		if (stock == null || stock.length == 0)
		{
			throw new NotFoundException();
		}
		
		//#CM10130
		// Aggregate possible picking stock
		retpossibleStockData = collectPossibleStockData(stock);

		return retpossibleStockData;
	}
	
	//#CM10131
	// for aggregation of data that meets the condition "stock qty- allocation qty > 0 ".
	//#CM10132
	/**
	 * Aggregation stock data.<BR>
	 * Received inventory information entity array as parameter<BR>
	 * received inventory information entity array is<BR>
	 * when you aggregate it<BR>
	 * result of aggregation
	 * <BR>
	 * @param	possibleStockData	Stock infoEntity Array<BR>
	 * @return	Stock infoEntity Array<BR>
	 * @throws OverflowException Announce this when digit number of numeric item overflows.
	 */
	protected Stock[] collectPossibleStockData(Stock[] possibleStockData) throws OverflowException
	{
		if (possibleStockData.length == 0)
		{
			return new Stock[0];
		}
		//#CM10133
		// Vector for work 
		Vector workVec = new Vector();
		//#CM10134
		// Stock Entity for work 
		Stock workStock = null;
		workStock = possibleStockData[0];
		for (int i = 1; i < possibleStockData.length; i++)
		{
			if (workStock.getUseByDate().equals(possibleStockData[i].getUseByDate()) &&
			    workStock.getItemCode().equals(possibleStockData[i].getItemCode()))
			{
				if ((workStock.getStockQty() + possibleStockData[i].getStockQty() <= SystemParameter.MAXSTOCKQTY)
					&& (workStock.getAllocationQty() + possibleStockData[i].getAllocationQty() <= SystemParameter.MAXSTOCKQTY))
				{
					workStock.setStockQty(workStock.getStockQty() + possibleStockData[i].getStockQty());
					workStock.setAllocationQty(workStock.getAllocationQty() + possibleStockData[i].getAllocationQty());
				}
				else
				{
					//#CM10135
					// 6026028=Overflow occurred during record integration processing. Table Name: {0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DMSTOCK");
					throw new OverflowException();
				}
			}
			else
			{
				workVec.addElement(workStock);
				workStock = possibleStockData[i];
			}
		}
		workVec.addElement(workStock);
		return (Stock[])workVec.toArray(new Stock[workVec.size()]);		
	}

	//#CM10136
	// Package methods ----------------------------------------------
	//#CM10137
	// Protected methods --------------------------------------------
	//#CM10138
	// Private methods -----------------------------------------------
}
//#CM10139
//end of class

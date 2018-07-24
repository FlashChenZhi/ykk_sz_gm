// $Id: Id0013Operate.java,v 1.3 2006/11/14 06:24:37 kamala Exp $
package jp.co.daifuku.wms.base.rft;
//#CM701105
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM701106
/**
 * Designer : Y.Taki <BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Process Data to the Exception Storage Item Data request by RFT. <BR>
 * The business logic called from Id0013Process is mounted. <BR>
 * <BR>
 * Exception StorageDataRetrieval processing(<CODE>findStockData()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *  Retrieve Stock information with Consignor Code+JANCode, and acquire newest Data on the Storage day. <BR>
 *  Return NotFoundException when there is no correspondence Data. <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/14 06:24:37 $
 * @author  $Author: kamala $
 */
public class Id0013Operate extends IdOperate
{
	//#CM701107
	// Class fields----------------------------------------------------
	//#CM701108
	// Class variables -----------------------------------------------
	//#CM701109
	// Constructors --------------------------------------------------
	//#CM701110
	/**
	 * Constructor.
	 * @param None
	 */
	public Id0013Operate()
	{
		super();
	}

	//#CM701111
	/**
	 * Pass Constructor DBConnection information. 
	 * @param conn		DBConnection information
	 */
	public Id0013Operate(Connection conn)
	{
		super();
		wConn = conn;
	}

	//#CM701112
	/**
	 * WareNaviManager
	 */

	
	//#CM701113
	// Class method --------------------------------------------------
	//#CM701114
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2006/11/14 06:24:37 $";
	}

	//#CM701115
	/**
	 * Acquire ItemData from Stock information. <BR>
	 * Receive Consignor Code, scanning Item Code, Scan Item Code2, Mode of packing, 
	 * the RFT machine number, and Worker code as a parameter. <BR>
	 * Assume Consignor Code, scanning Item Code, Scan Item Code2, and Mode of packing to be a parameter, 
	 * and call stock Data retrieval Method(getStockData). <BR>
	 * When the retrieval result is null
	 * Stock Data retrieval (Remove Mode of packing from the retrieval article) Call Method(getStockDataNoItemForm). <BR>
	 * <DIR>
	 *  When Data was not able to be acquired in stock Data retrieval Method, eight (correspondence Data none) was set in Detailed message. 
	 * 	Throws NotFoundException.<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor Code
	 * @param	Item Code 		Item Code
	 * @param	convertedJanCodeScan Item Code2
	 * @param	itemForm	 	Mode of packing
	 * @param	rftNo			RFT machine Number
	 * @param	workerCode		Worker code
	 * @return	Stock information entity array
	 * @throws NotFoundException It is notified when Data which can work is not found. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	*/
	public Stock startNoPlanStorage(
		String consignorCode,
		String itemCode,
		String convertedJanCode,
		String itemForm,
		String rftNo,
		String workerCode)
		throws NotFoundException, ReadWriteException
	{

		//#CM701116
		// Variable which maintains result of retrieving Stock information
		Stock stockData = null;
		
		//#CM701117
		// Retrieve Stock information. (Contain Mode of packing in Search condition. )
		stockData = getStockData(consignorCode, itemCode, convertedJanCode, itemForm);
		if (stockData == null)
		{
			//#CM701118
			// When the retrieval result is null
			
			//#CM701119
			// Retrieve Stock information. (Remove Mode of packing from Search condition. )
			stockData = getStockData(consignorCode, itemCode, convertedJanCode, null);
			if (stockData != null)
			{
				//#CM701120
				// When the retrieval result is not null
				//#CM701121
				// Empty Character string in Location No. and Area No. 
				stockData.setAreaNo("");
				stockData.setLocationNo("");
			}
		}

		if (stockData == null)
		{
			//#CM701122
			// Processing when Data does not correspond by retrieval
			NotFoundException e = new NotFoundException(RFTId5013.ANS_CODE_NULL);
			throw e;
		}

		return stockData;
	}


	//#CM701123
	// Package methods -----------------------------------------------
	//#CM701124
	// Protected methods ---------------------------------------------
	//#CM701125
	/**
	 * Acquire Item information which corresponds from Stock information to a specified condition. <BR>
	 * <BR>
	 * Retrieve Stock information on the following condition. 
	 * Consignor Code    =Acquisition from parameter<BR>
	 * Stock status=Stock<BR>
	 * Stock information.Area No.=Area Master information.Area No.<BR>
	 * Area Master information.Area flag =2:Other than ASRS<BR>
	 * Retrieve Stock information by the following item. <BR>
	 * Scan Item Code1 is JANCode->Case ITF->Bundle ITF->. 
	 * Retrieve Scan Item Code2 in order of the ITF to JANCode conversion. <BR>
	 * Sort the Storage date in the descending order, and acquire the first Data. <BR>
	 * Return the Data when there is correspondence Data. Return null when there is no correspondence Data. 
	 * 
	 * <DIR>
	 *  Consignor Code : Argument Consignor Code<BR>
	 *  Item Code : Argument Item Code<BR>
	 *  Status flag : Center stocking<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	Consignor Code<BR>
	 * @param	Item Code 		Scan Item Code1<BR>
	 * @param	convertedJanCodeScan Item Code2<BR>
	 * @param	itemForm 		Mode of packing<BR>
	 * @return	Stock information entity<BR>
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	*/
	protected Stock getStockData(
			String consignorCode,
			String itemCode,
			String convertedJanCode,
			String itemForm
			)
			throws ReadWriteException
		{
			//#CM701126
			// Array which maintains return value
			Stock[] stockData	= null;
			
			StockSearchKey skey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(wConn);
			//#CM701127
			//-----------------
			//#CM701128
			// Retrieval of first and fifth Stock information
			//#CM701129
			// Retrieve scanned Code as JANCode. 
			//#CM701130
			//-----------------
			skey = getBaseCondition(consignorCode,itemForm);
			skey.setItemCode(itemCode);	
			//#CM701131
			// Retrieve it. 
			stockData = (Stock[]) stockHandler.find(skey);

			if (stockData != null && stockData.length > 0)
			{
				return stockData[0];
			}
			//#CM701132
			//-----------------
			//#CM701133
			// Retrieval of second and sixth Stock information
			//#CM701134
			// Scanned Code is Retrieved as Case ITF. 
			//#CM701135
			//-----------------
			skey = getBaseCondition(consignorCode,itemForm);
			skey.setItf(itemCode);	
			//#CM701136
			// Retrieve it. 
			stockData = (Stock[]) stockHandler.find(skey);

			if (stockData != null && stockData.length > 0)
			{
				return stockData[0];
			}
			//#CM701137
			//-----------------
			//#CM701138
			// Retrieval of third and seventh Stock information
			//#CM701139
			// Scanned Code is Retrieved as Bundle ITF. 
			//#CM701140
			//-----------------
			skey = getBaseCondition(consignorCode,itemForm);
			skey.setBundleItf(itemCode);	
			//#CM701141
			// Retrieve it. 
			stockData = (Stock[]) stockHandler.find(skey);

			if (stockData != null && stockData.length > 0)
			{
				return stockData[0];
			}
			if (! StringUtil.isBlank(convertedJanCode))
			{		
				//#CM701142
				//-----------------
				//#CM701143
				// Retrieval of fourth and eighth Stock information
				//#CM701144
				// When Code into which ITFtoJAN is converted is set,
				//#CM701145
				// It is Retrieved as JAN Code. 
				//#CM701146
				//-----------------
				skey = getBaseCondition(consignorCode,itemForm);
				skey.setItemCode(convertedJanCode);		
				//#CM701147
				// Retrieve it. 
				stockData = (Stock[]) stockHandler.find(skey);

				if (stockData != null && stockData.length > 0)
				{
					return stockData[0];
				}				
			}
			return null;
		}

	//#CM701148
	/**
	 * Generate Search condition which is basic when Data which can be work for the Item information inquiry request is retrieved. <BR>
	 * The basic condition of the retrieval is as follows. 
	 * <DIR>
	 *  Status flag 	= 2(Stock) 
	 * </DIR>
	 * @param	consignorCode	Consignor Code
	 * @param	itemForm		Mode of packing
	 * @return	Work information retrieval key
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in Processing Check is generated. 
	 */
	protected StockSearchKey getBaseCondition(
			String consignorCode,String itemForm) throws ReadWriteException
	{
		StockSearchKey skey = new StockSearchKey();
	
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		skey.setConsignorCode(consignorCode);
		
		skey.setInstockDateOrder(1, false);
		if(itemForm != null)
		{
			skey.setCasePieceFlag(itemForm);
		}

		return skey;
	}
	
	//#CM701149
	// Private methods -----------------------------------------------

}
//#CM701150
//end of class

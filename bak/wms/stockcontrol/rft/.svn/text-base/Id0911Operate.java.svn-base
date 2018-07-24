// $Id: Id0911Operate.java,v 1.2 2006/09/27 03:00:37 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;
//#CM9920
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
import jp.co.daifuku.wms.base.rft.DataColumn;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.Idutils;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;
//#CM9921
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Execute data process for exceptions of possible picking stock inquiry process request.<BR>
 * Implement Business logic invoked from Id0152Process.<BR>
 * <BR>
 * Obtain the stock possible for unplanned picking (<CODE>getStockData ()</CODE> method).<BR>
 * <BR>
 * <DIR>
 *  when list select flag is 0 (initial time)
 *  Return NotFoundException if no corresponding data found.
 * 
 *  when list select flag is 1 (when selected from the list)
 * 	Serch StockAllocateOperator by classstockSearch method.
 *  Return NotFoundException if no corresponding data found.
 *  If corresponding data found
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:37 $
 * @author  $Author: suresh $
 */
public class Id0911Operate extends IdOperate
{
	//#CM9922
	// Class fields----------------------------------------------------
	//#CM9923
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0911Operate";
	//#CM9924
	// Class variables -----------------------------------------------
	//#CM9925
	// Constructors --------------------------------------------------
	//#CM9926
	// Class method --------------------------------------------------
	//#CM9927
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:37 $";
	}

	//#CM9928
	/**
	 * Obtain the stock data.<BR>
	 * <BR>
	 * list select flag<BR>
	 * list select flag<BR>
	 * <BR>
	 * if no search result and no corresponding stock data<BR>
	 * Exclude data with possible picking qty equal to 0. As the result<BR>
	 * when corresponding stock data is found<BR>
	 * aggregate the result<BR>
	 * <BR>
	 * @param	consignorCode	Consignor code<BR>
	 * @param	areaNo 			Area<BR>
	 * @param	locationNo 		Location <BR>
	 * @param	casePieceFlag	Load size<BR>
	 * @param  scanCode1		Scan code1
	 * @param  scanCode2		Scan code2
	 * @param	listSelectionFlag	List select flag<BR>
	 * @param	useByDate 		Expiry Date<BR>
	 * @return	preStockData    Stock infoEntity<BR>
	 * @throws NotFoundException Announce this when workable data is not found.
	 * @throws OverflowException Announce this when digit number of numeric item overflows.
	 * @throws IllegalAccessException Announce this when creation of instance is failed.
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws ScheduleException check Announce this when unexpected exception occurs in the Process.
	*/
	public Stock[] getDeliverableStockData(
		String consignorCode,
		String areaNo,
		String locationNo,
		String casePieceFlag,
		String scanCode1,
		String scanCode2,
		String listSelectionFlag,
		String useByDate
		)
		throws NotFoundException, OverflowException, IllegalAccessException, ReadWriteException, ScheduleException
	{
		//#CM9929
		// Variable to maintain the search result for stock.
		Stock[] preStockData = null;
		//#CM9930
		// Variable to maintain the possible picking stock after aggregation.
		Stock[] retpossibleStockData = null;
		
		if (listSelectionFlag.equals(RFTId0911.LIST_SELECTION_FALSE))
		{
			//#CM9931
			// list select flag
			preStockData = getStockData(consignorCode, areaNo, locationNo, casePieceFlag, null, scanCode1, scanCode2);
		}
		else{
			//#CM9932
			// list select flag
			preStockData = getStockData(consignorCode, areaNo, locationNo, casePieceFlag, useByDate, scanCode1, scanCode2);
		}
		if (preStockData == null)
		{
			NotFoundException e = new NotFoundException();
			throw e;
		}
				
		//#CM9933
		// aggregate possible picking stock
		retpossibleStockData = collectStockData(preStockData);

		//#CM9934
		// Return search result.
		return retpossibleStockData;
	}
				
	//#CM9935
	// Return search result.
	
	

	//#CM9936
	/**
	 * Execute processing of stock expiry date list file creation.<BR>
	 * Write inventory information data to specified file name file in sequence.<BR>
	 * @param  data  List data
	 * @param  filename  stock Expiry DateTable File Name
	 * @throws IOException Annouce this when file I/O error is generated.
	 */
	public void createTableFile(Stock[] data, String filename) throws IOException
	{
		//#CM9937
		// Generate file writing stream 
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename))) ;
		
		//#CM9938
		// Execute looping in the same number of array elements.
		for(int i = 0 ; i < data.length ; i++)
		{
			//#CM9939
			// Take expiry date out from Stock instance and write in the file.
			//#CM9940
			// Make the data in specified length
			//#CM9941
			// Expiry Date
			String useByDate = Idutils.createDataLeft(data[i].getUseByDate(), DataColumn.LEN_USE_BY_DATE) ;
			
			//#CM9942
			// unify strings
			StringBuffer buffer = new StringBuffer() ;
			buffer.append(useByDate) ;
			
			//#CM9943
			// Write into the file and carrier return / line feed
			pw.println(buffer.toString()) ;
		}
		
		//#CM9944
		// Close file writing stream
		pw.close() ;
	}


	//#CM9945
	// Package methods -----------------------------------------------
	//#CM9946
	// Protected methods ---------------------------------------------
	

	//#CM9947
	/**
	 * Aggregate stock data<BR>
	 * Inventory information entity array received as parameter<BR>
	 * Received inventory information entity array<BR>
	 * when you aggregate<BR>
	 * aggregated result is
	 * <BR>
	 * @param	possibleStockData	Stock infoEntity Array<BR>
	 * @return	Stock infoEntity Array<BR>
	 * @throws OverflowException Announce this when digit number of numeric item overflows.
	 */
	protected Stock[] collectStockData(Stock[] possibleStockData) throws OverflowException
	{
		if (possibleStockData.length == 0)
		{
			return new Stock[0];
		}
		//#CM9948
		// Vector for work 
		Vector workVec = new Vector();
		//#CM9949
		// Stock Entity for work
		Stock workStock = null;
		workStock = possibleStockData[0];
		for (int i = 1; i < possibleStockData.length; i++)
		{
			if (workStock.getUseByDate().equals(possibleStockData[i].getUseByDate()))
			{
				if ((workStock.getStockQty() + possibleStockData[i].getStockQty() <= SystemParameter.MAXSTOCKQTY)
					&& (workStock.getAllocationQty() + possibleStockData[i].getAllocationQty() <= SystemParameter.MAXSTOCKQTY))
				{
					workStock.setStockQty(workStock.getStockQty() + possibleStockData[i].getStockQty());
					workStock.setAllocationQty(workStock.getAllocationQty() + possibleStockData[i].getAllocationQty());
				}
				else
				{
					//#CM9950
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
	
	//#CM9951
	/**
	 * Search stock data for inventory information inquiry
	 * Base condition of search is as follows
	 * <DIR>
	 *   Consignor code = Obtain from parameter.<BR>
	 *   Location No = Obtain from parameter.<BR>
	 *   Stock status= 2：Center Inventory<BR>
	 *   AreaNo = Obtain from parameter.<BR>
	 *   Stock info. Area No. = area master info. Area No.<BR>
	 *   Area Master InfoAreaDivision = 2：Other than ASRS<BR>
	 * </DIR>
	 * 
	 * adding to this condition<BR>
	 * Search in sequence
	 * Return the obtained data.<BR>
	 * when stock data in not found in every these searches, and
	 * when it is not empty
	 * <BR>
	 * Return null if no work possible data found by search 4 times.
	 * <BR>
	 * @param	consignorCode		Consignor code
	 * @param	areaNo				Area
	 * @param	locationNo			Location 
	 * @param  casePieceFlag       Load size
	 * @param  useByDate           Expiry Date
	 * @param  scanCode1           Scan code1
	 * @param  scanCode2           Scan code2
	 * @return	Stock info  			Stock infoEntity Array<BR>
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws ScheduleException Announce this when unexpected exception occurs in the check Process.
	 */
	protected Stock[] getStockData(
			String consignorCode,
			String areaNo,
			String locationNo,
			String casePieceFlag,
			String useByDate,
			String scanCode1,
			String scanCode2) throws ReadWriteException, ScheduleException
	{
		
			//#CM9952
			// Variable to maintain the search result for Stock.
			Stock[] stock = null;

			StockSearchKey skey = new StockSearchKey();
			StockHandler obj = new StockHandler(wConn);
			//#CM9953
			//-----------------
			//#CM9954
			// Search Stock info(1st time)
			//#CM9955
			// Execute search by Search scanned code as JAN code.
			//#CM9956
			//-----------------
			skey = getStockSearchKey(consignorCode,areaNo, locationNo, casePieceFlag, useByDate);
			skey.setItemCode(scanCode1);
			
			stock = (Stock[]) obj.find(skey);
			//#CM9957
			// Variable to maintain the search result for stock.

			if (stock != null && stock.length > 0)
			{
				return stock;
			}
			
			//#CM9958
			//-----------------
			//#CM9959
			// Search of Stock info(2nd time)
			//#CM9960
			// Execute search by Search scanned code as Case ITF.
			//#CM9961
			//-----------------
			skey = getStockSearchKey(consignorCode, areaNo, locationNo, casePieceFlag, useByDate);
			skey.setItf(scanCode1);
			
			stock = (Stock[]) obj.find(skey);

			if (stock != null && stock.length > 0)
			{
				return stock;
			}

			//#CM9962
			//-----------------
			//#CM9963
			// Search of Stock info(3rd time)
			//#CM9964
			// Execute search by scanned code as Bundle ITF.
			//#CM9965
			//-----------------
			skey = getStockSearchKey(consignorCode, areaNo, locationNo, casePieceFlag, useByDate);
			skey.setBundleItf(scanCode1);
			
			stock = (Stock[]) obj.find(skey);

			if (stock != null && stock.length > 0)
			{
				return stock;
			}

			if (! StringUtil.isBlank(scanCode2))
			{
				//#CM9966
				//-----------------
				//#CM9967
				// Serch Stock info(4th time)
				//#CM9968
				// when ITFtoJAN translated code is set
				//#CM9969
				// Execute search by this as JAN code.
				//#CM9970
				//-----------------
				skey = getStockSearchKey(consignorCode, areaNo, locationNo, casePieceFlag, useByDate);
				skey.setItemCode(scanCode2);

				stock = (Stock[]) obj.find(skey);

				if (stock != null && stock.length > 0)
				{
					return stock;
				}
			}
				
		return null;
	}
	
	//#CM9971
	/**
	 * Return SearchKey which stores the common condition
	 * to obtain Stock info.<BR>
	 * Require to set the aggregation condition or similar at the invoking source.<BR>
	 * <BR>
	 * (search conditions)
	 * <UL>
	 *  <LI>Consignor code</LI>
	 *  <LI>Stock status = Center Inventory
	 *  <LI>AreaNo</LI>
	 *  <LI>Location No</LI>
	 *  <LI>Case/Piece division =When all is specified, expel it from the search conditions</LI>
	 *  <LI>expiry date =When empty is specified, expel it from the search conditions</LI>
	 *  <LI>possible number of move = other than 0</LI>
	 * </UL>
	 * 
	 * @param consignorCode	 Consignor code
	 * @param areaNo		 AreaNo
	 * @param locationNo	 Location No
	 * @param casePieceFlag	 Case/Piece Division
	 * @param useByDate		 Expiry Date
	 * @return		Work data For SearchSearchKey
	 * @throws ReadWriteException Announce this when error generated in connection with database
	 * @throws ScheduleException Announce this when unexpected exception occursin the check Process.
	 */
	public StockSearchKey getStockSearchKey(
	        String consignorCode,
	        String areaNo,
	        String locationNo,
	        String casePieceFlag,
	        String useByDate
	        ) throws ReadWriteException, ScheduleException
	{
	    StockSearchKey skey = new StockSearchKey();

		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);	// センター在庫
	    skey.setConsignorCode(consignorCode);					// 荷主コード
	    if (! StringUtil.isBlank(areaNo))
	    {
		    skey.setAreaNo (areaNo);							// エリアNo
	    }
	    else
	    {
			AreaOperator AreaOperator = new AreaOperator(wConn);
			
			String[] areaNoASRS = null;
			int[] areaType = new int[2];
			areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
			areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
			//#CM9972
			// Obtain the Area other than ASRS and add it to the search conditions.
			//#CM9973
			// Search IS NULL if no corresponding area found.
			areaNoASRS = AreaOperator.getAreaNo(areaType);
			skey.setAreaNo(areaNoASRS);
	    }
	    skey.setLocationNo (locationNo);						// ロケーションNo
	    if (! casePieceFlag.equals(RFTId0911.CASE_PIECE_FLAG_ALL))
	    {
		    skey.setCasePieceFlag(casePieceFlag);				// ｹｰｽﾋﾟｰｽ区分
	    }
	    if (useByDate != null)
	    {
		    skey.setUseByDate(useByDate);						// 賞味期限
	    }
	    skey.setAllocationQty(0, "!=");							// 出庫可能数0以外
  
		skey.setUseByDateOrder(1, true);
		skey.setItemCodeOrder(2, true);
		skey.setItfOrder(3, true);
		skey.setBundleItfOrder(4, true);

	    return skey;
	}


	//#CM9974
	// Private methods -----------------------------------------------

}
//#CM9975
//end of class

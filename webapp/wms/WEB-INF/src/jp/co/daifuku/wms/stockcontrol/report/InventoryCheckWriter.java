//#CM9051
//$Id: InventoryCheckWriter.java,v 1.5 2006/12/13 09:02:18 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.report;

//#CM9052
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;

//#CM9053
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR> 
 * <CODE>InventoryCheckWriter</CODE>class creates a ticket data file for Inventory Check fill in list and execute print process. <BR>
 * Search Stock Info(DMSTOCK) based on the conditions set at <CODE>InventoryCheckListSCH</CODE> class and <BR>
 * Creates the result as a ticket data file for Inventory Check fill in list. <BR>
 * This class processes as follows. <BR>
 * <BR>
 * a ticket data file creation Process(<CODE>startPrint()</CODE>Method) <BR>
 * <DIR>
 *	1.Search for Stock Info(DMSTOCK) Count that meets the set condition via <CODE>InventoryCheckListSCH</CODE>class. <BR>
 *	2.Create a report data file if result count is one or more. If it is 0, return false and close. <BR>
 *	3.Start the print process. <BR>
 *  4.Return true when print Process is normal <BR>
 *    Return false when error occurred during the print process. <BR>
 * <BR>
 * 	＜parameter＞*Mandatory Input <BR><DIR>
 * <BR>
 * 		Consignor Code*<br>
 * 		Location№ <br>
 * 		Item Code  <br>
 * 		Print Condition(Aggregation with corresponding location items, w/out aggregation)<br>
 * 		(quantity printed; quantity not printed)
 * <BR>
 *	＜Print data＞ <BR><DIR>
 *  	LocationNo<BR>
 * 		Item Code<BR>
 * 		Item Name<BR>
 * 		Case/Piece division<BR>
 * 		Packed Qty per Case<BR>
 * 		Packed Qty per Bundle<BR>
 * 		Stock Case Qty<BR>
 * 		Stock Piece Qty<BR>
 * 		Consignor Code<BR>
 * 		Consignor Name<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/15</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:02:18 $
 * @author  $Author: suresh $
 */
public class InventoryCheckWriter extends CSVWriter
{
	//#CM9054
	// Class fields --------------------------------------------------

	//#CM9055
	// Class variables -----------------------------------------------
	//#CM9056
	// Consignor Code
	private String consignorCode;

	//#CM9057
	// Consignor Name
	private String consignorName;

	//#CM9058
	// Location№
	private String locationNumber;

	//#CM9059
	// Item Code
	private String itemCode;

	//#CM9060
	// Item Name
	private String itemName;

	//#CM9061
	// Case/Piece division
	private String casePieceFlag;

	//#CM9062
	// Packed Qty per Case
	private String enteringQty;

	//#CM9063
	// Packed Qty per Bundle
	private String bundleQty;

	//#CM9064
	// Start Location№	
	private String fromLocationNumber;

	//#CM9065
	// End Location№	
	private String toLocationNumber;

	//#CM9066
	// Case ITF
	private String wItf;

	//#CM9067
	// Bundle ITF
	private String wBundleItf;

	//#CM9068
	// Case/Piece division
	private String wCasePieceFlag;

	//#CM9069
	/**
	 * Print Condition1
	 */
	private String printCondition1;

	//#CM9070
	/**
	 * Print Condition2
	 */
	private String printCondition2;

	//#CM9071
	// Class method --------------------------------------------------
	//#CM9072
	/**
	 * return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 09:02:18 $");
	}

	//#CM9073
	// Constructors --------------------------------------------------
	//#CM9074
	/**
	 * InventoryCheckWriter Construct Object.
	 * @param conn <CODE>Connection</CODE>
	 */
	public InventoryCheckWriter(Connection conn)
	{
		super(conn);
	}

	//#CM9075
	// Public methods ------------------------------------------------
	//#CM9076
	/**
	 * Obtain Packed Qty per Bundle.
	 * @return Packed Qty per Bundle
	 */
	public String getBundleQty()
	{
		return bundleQty;
	}
	
	//#CM9077
	/**
	 * Set Packed Qty per Bundle
	 * @param bundleQty Set Packed Qty per Bundle
	 */
	public void setBundleQty(String bundleQty)
	{
		this.bundleQty = bundleQty;
	}
	
	//#CM9078
	/**
	 * Obtain Case/Piece division.
	 * @return Case/Piece division
	 */
	public String getCasePieceFlag()
	{
		return casePieceFlag;
	}
	
	//#CM9079
	/**
	 * Set Case/Piece division
	 * @param casePieceFlag Case/Piece division to be set
	 */
	public void setCasePieceFlag(String casePieceFlag)
	{
		this.casePieceFlag = casePieceFlag;
	}
	
	//#CM9080
	/**
	 * Obtain Consignor Code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return consignorCode;
	}
	
	//#CM9081
	/**
	 * Set Consignor Code
	 * @param consignorCode Consignor Code to be set
	 */
	public void setConsignorCode(String consignorCode)
	{
		this.consignorCode = consignorCode;
	}
	
	//#CM9082
	/**
	 * Obtain Consignor Name.
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return consignorName;
	}
	
	//#CM9083
	/**
	 * Set Consignor Name.
	 * @param consignorName Set Consignor Name
	 */
	public void setConsignorName(String consignorName)
	{
		this.consignorName = consignorName;
	}
	
	//#CM9084
	/**
	 * Obtain Packed Qty per Case.
	 * @return Packed Qty per Case
	 */
	public String getEnterignQty()
	{
		return enteringQty;
	}
	
	//#CM9085
	/**
	 * Set Packed Qty per Case
	 * @param enterignQty Set Packed Qty per Case
	 */
	public void setEnterignQty(String enterignQty)
	{
		this.enteringQty = enterignQty;
	}
	
	//#CM9086
	/**
	 * Obtain Item Code.
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return itemCode;
	}
	
	//#CM9087
	/**
	 * Set Item Code
	 * @param itemCode Item Code to be set
	 */
	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}
	
	//#CM9088
	/**
	 * Obtain Item Name.
	 * @return Item Name
	 */
	public String getItemName()
	{
		return itemName;
	}
	
	//#CM9089
	/**
	 * Set Item Name
	 * @param itemName Set Item Name
	 */
	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}
	
	//#CM9090
	/**
	 * Obtain location№
	 * @return Location№
	 */
	public String getLocationNumber()
	{
		return locationNumber;
	}
	
	//#CM9091
	/**
	 * Set location№
	 * @param locationNumber Set Location№
	 */
	public void setLocationNumber(String locationNumber)
	{
		this.locationNumber = locationNumber;
	}

	//#CM9092
	/**
	 * Set Start Location No.
	 * @param arg Start Location to be set No..
	 */
	public void setFromLocationNo(String arg)
	{
		fromLocationNumber = arg;
	}

	//#CM9093
	/**
	 * Obtain Start Location No.
	 * @return Start Location No.
	 */
	public String getFromLocationNo()
	{
		return fromLocationNumber;
	}

	//#CM9094
	/**
	 * Set Closed Location No
	 * @param arg Set Closed Location No..
	 */
	public void setToLocationNo(String arg)
	{
		toLocationNumber = arg;
	}

	//#CM9095
	/**
	 * Obtain Closed Location No..
	 * @return Closed Location No.
	 */
	public String getToLocationNo()
	{
		return toLocationNumber;
	}

	//#CM9096
	/**
	 * Set Print Condition1
	 * @param arg Set Print Condition1
	 */
	public void setPrintCondition1(String arg)
	{
		printCondition1 = arg;
	}

	//#CM9097
	/**
	 * Obtain Print Condition1.
	 * @return Print Condition
	 */
	public String getPrintCondition1()
	{
		return printCondition1;
	}

	//#CM9098
	/**
	 * Set Print Condition2
	 * @param arg Set Print Condition2
	 */
	public void setPrintCondition2(String arg)
	{
		printCondition2 = arg;
	}

	//#CM9099
	/**
	 * Obtain Print Condition2.
	 * @return Print Condition
	 */
	public String getPrintCondition2()
	{
		return printCondition2;
	}

	//#CM9100
	/**
	 * Obtain print data count.<BR>
	 * Use it to determine for the search result to allow printing.<BR>
	 * Use this method from a schedule class for display.<BR>
	 * 
	 * @return Count of print data
	 * @throws ReadWriteException Inform when data connection error occurs.
	 * @throws ScheduleException  Inform when unexpected exception occurs in the check Process.
	 */
	public int count() throws ReadWriteException, ScheduleException
	{
		StockHandler instockHandle = new StockHandler(wConn);
		StockSearchKey searchKey = new StockSearchKey();
		//#CM9101
		// Set Search condition.
		setStockSearchKey(searchKey);

		//#CM9102
		// Set aggregation condition corresponds to screen input and search Count
		//#CM9103
		// Aggregation with corresponding location items
		if (printCondition1.equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON))
		{
			searchKey.setLocationNoCollect();
			searchKey.setItemCodeCollect();
			searchKey.setUseByDateCollect();

			searchKey.setLocationNoGroup(1);
			searchKey.setItemCodeGroup(2);
			searchKey.setUseByDateGroup(4);
		}
		//#CM9104
		// No Aggregation

		return instockHandle.count(searchKey);

	}

	//#CM9105
	/**
	 * Create the CSV file for Inventory Check fill in List and execute print. <BR>
	 * Search for Stock Info Count that meets the set condition.<BR>
	 * Create a report data file if result count is one or more. If it is 0, return false and close.<BR>
	 * Start the print process.<BR>
	 * Return true when the print process completed normally.<BR>
	 * Return false when error occurred during the print process.<BR>
	 * 
	 * @return when the print process completed normally：true<BR>
	 *          when error occurred during the print process：false
	 */
	public boolean startPrint()
	{
		StockReportFinder reportFinder = new StockReportFinder(wConn);

		try
		{

			StockSearchKey searchKey = new StockSearchKey();

			setStockSearchKey(searchKey);

			//#CM9106
			// Order Set the data retrieval.		
			searchKey.setLocationNoCollect("");
			searchKey.setItemCodeCollect("");
			searchKey.setItemName1Collect("");
			searchKey.setCasePieceFlagCollect("");
			searchKey.setEnteringQtyCollect("");
			searchKey.setBundleEnteringQtyCollect("");
			searchKey.setConsignorCodeCollect("");
			searchKey.setConsignorNameCollect("");
			searchKey.setItfCollect("");
			searchKey.setBundleItfCollect("");
			searchKey.setUseByDateCollect("");
			searchKey.setStockQtyCollect("");

			//#CM9107
			// Aggregation with corresponding location items
			if (printCondition1.equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON))
			{
				searchKey.setLocationNoOrder(1, true);
				searchKey.setItemCodeOrder(2, true);
				searchKey.setUseByDateOrder(3, true);
				searchKey.setLastUpdateDateOrder(4,false) ;
			}
			//#CM9108
			// No Aggregation
			else
			{
				searchKey.setLocationNoOrder(1, true);
				searchKey.setItemCodeOrder(2, true);
				searchKey.setCasePieceFlagOrder(3, true);
				searchKey.setUseByDateOrder(4, true);
				searchKey.setLastUpdateDateOrder(5, false);
			}

			//#CM9109
			// Cursor open
			int count = reportFinder.search(searchKey);

			//#CM9110
			// No target data
			if (count <= 0)
			{
				//#CM9111
				// Print data was not found.
				wMessage = "6003010";
				return false;
			}

			if (!createPrintWriter(FNAME_INVENTORY))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_INVENTORY);
            
			Stock stock[] = null;

			//#CM9112
			// 	Create the content of the data file per 100 data until search result is lost.
			while (reportFinder.isNext())
			{
				//#CM9113
				// Retrieve up to 100 data of the search result.
				stock = (Stock[]) reportFinder.getEntities(100);

				//#CM9114
				// In the case of aggregation display
				if (printCondition1.equals(StockControlParameter.PRINTINGCONDITION_INTENSIVEPRINTING_ON)
					&& (stock.length != 0))
				{
					intensiveprintingOn(stock);
				}
				//#CM9115
				// Except aggregation display
				else
				{
					for (int i = 0; i < stock.length; i++)
					{

						wStrText.append(re + "");
						//#CM9116
						// Consignor Code
						wStrText.append(ReportOperation.format(stock[i].getConsignorCode()) + tb);
						//#CM9117
						// Consignor Name
						wStrText.append(ReportOperation.format(stock[i].getConsignorName()) + tb);
						//#CM9118
						// Location
						wStrText.append(ReportOperation.format(stock[i].getLocationNo()) + tb);
						//#CM9119
						// Item Code
						wStrText.append(ReportOperation.format(stock[i].getItemCode()) + tb);
						//#CM9120
						// Item Name
						wStrText.append(ReportOperation.format(stock[i].getItemName1()) + tb);
						//#CM9121
						// Case/Piece division
						wStrText.append(
							ReportOperation.format(DisplayUtil.getPieceCaseValue(stock[i].getCasePieceFlag())) + tb);
						//#CM9122
						// Packed Qty per Case
						wStrText.append(ReportOperation.format(Integer.toString(stock[i].getEnteringQty())) + tb);
						//#CM9123
						// Packed Qty per Bundle
						wStrText.append(ReportOperation.format(Integer.toString(stock[i].getBundleEnteringQty())) + tb);
						//#CM9124
						// To print the quantity
						if (StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON.equals(printCondition2))
						{

							//#CM9125
							// Stock Case Qty
							wStrText.append(
								DisplayUtil.getCaseQty(
									stock[i].getStockQty(),
									stock[i].getEnteringQty(),
									stock[i].getCasePieceFlag())
									+ tb);
							//#CM9126
							// Stock Piece Qty
							wStrText.append(
								DisplayUtil.getPieceQty(
									stock[i].getStockQty(),
									stock[i].getEnteringQty(),
									stock[i].getCasePieceFlag())
									+ tb);
						}
						else
						{
							//#CM9127
							// As there’s no good method at this moment, it shall be made to Set balank
							//#CM9128
							// when 11 digits and more is set at Form side
							//#CM9129
							// Stock Case Qty
							wStrText.append("11111111111" + tb);

							//#CM9130
							// Stock Piece Qty
							wStrText.append("11111111111" + tb);
						}
						//#CM9131
						// Case ITF
						wStrText.append(ReportOperation.format(stock[i].getItf()) + tb);
						//#CM9132
						// Bundle ITF
						wStrText.append(ReportOperation.format(stock[i].getBundleItf()) + tb);
						//#CM9133
						// Expiry Date
						wStrText.append(ReportOperation.format(stock[i].getUseByDate()) + tb);
						//#CM9134
						// Aggregation condition
						wStrText.append(ReportOperation.format(DisplayUtil.getPrintCondition(printCondition1)) + tb);
						//#CM9135
						// writing
						wPWriter.print(wStrText);

						wStrText.setLength(0);
					}
				}
			}

			//#CM9136
			// Close Stream
			wPWriter.close();

			//#CM9137
			// Execute UCXSingle
			if (!executeUCX(JOBID_INVENTORY))
			{
				//#CM9138
				// Printing failed after setup. Please refer to log.
				return false;
			}

			//#CM9139
			// Execute relocation data file to backup holder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{
			//#CM9140
			// Printing failed after setup. Please refer to log.
			setMessage("6007034");

			return false;
		}
		catch (ScheduleException e)
		{
			//#CM9141
			// Printing failed after setup. Please refer to log.
			setMessage("6007034");
			return false;

		}
		finally
		{
			try
			{
				//#CM9142
				// Execute close Process of opened database cursor.
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM9143
				// Database error occurred. Please refer to log.
				setMessage("6007002");
				return false;
			}
		}
		return true;
	}

	//#CM9144
	// Package methods -----------------------------------------------

	//#CM9145
	// Protected methods ---------------------------------------------

	//#CM9146
	// Private methods -----------------------------------------------

	//#CM9147
	/**
	 * Set search condition.<BR>
	 * 
	 * @param searchKey Search key of Stock Info
	 * @throws ReadWriteException Inform when data connection error occurs.
	 * @throws ScheduleException  Inform when unexpected exception occurs in the check Process.
	 */
	private void setStockSearchKey(StockSearchKey searchKey) throws ReadWriteException, ScheduleException
	{
		//#CM9148
		// Set Consignor Code
		searchKey.setConsignorCode(consignorCode);
		//#CM9149
		// Statsu flag other than Delete
		searchKey.setStatusFlag(Stock.STATUS_FLAG_DELETE, "!=");

		//#CM9150
		// Set Item Code
		if (itemCode != null && !"".equals(itemCode))
		{
			searchKey.setItemCode(itemCode);
		}
		//#CM9151
		// Set location№
		if (fromLocationNumber != null && !"".equals(fromLocationNumber))
		{
			searchKey.setLocationNo(fromLocationNumber, ">=");
		}
		if (toLocationNumber != null && !"".equals(toLocationNumber))
		{
			searchKey.setLocationNo(toLocationNumber, "<=");
		}

		//#CM9152
		// Stock Packed Qty > 0
		searchKey.setStockQty(0, ">");

		AreaOperator AreaOperator = new AreaOperator(wConn);

		String[] areaNo = null;
		int[] areaType = new int[2];
		areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
		areaType[1] = Area.SYSTEM_DISC_KEY_IDM;

		//#CM9153
		// Obtain Area other than ASRS and add it to a search condition.
		//#CM9154
		// Search IS NULL when no corresponding area found
		areaNo = AreaOperator.getAreaNo(areaType);
		searchKey.setAreaNo(areaNo);

		//#CM9155
		// status = Center Inventory
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
	}

	//#CM9156
	/**
	 * Set Stock entity to StockControlParameter. <BR>
	 * 
	 * @param ety Stock entity array
	 * @throws ReadWriteException Inform when data connection error occurs.
	 */
	private void intensiveprintingOn(Entity[] ety) throws ReadWriteException
	{
		StockControlParameter stParam = null;
		StockControlParameter[] vstParam = null;
		Vector vec = new Vector();
		Stock[] stock = (Stock[]) ety;
		if ((stock != null) && (stock.length != 0))
		{

			//#CM9157
			// Swap valiable for breakProcess
			String consignorcode = "";
			String itemcode = "";
			String location = "";
			String usebydate = "";

			boolean firstFlg = false;
			long stockQty = 0;

			//#CM9158
			// Aggregate obtained Stock Info by Consignor Code・Item Code・Location and obtain the total of Stock Case Qty・stock piece qty.
			for (int i = 0; i < stock.length; i++)
			{

				//#CM9159
				// breakProcess
				if (consignorcode.equals(stock[i].getConsignorCode())
					&& location.equals(stock[i].getLocationNo())
					&& itemcode.equals(stock[i].getItemCode())
					&& usebydate.equals(stock[i].getUseByDate()))
				{

					//#CM9160
					// Aggregated Stock Qty
					stockQty = (long) stockQty + (long) stock[i].getStockQty();
					//#CM9161
					// Stock Case Qty
					stParam.setTotalStockCaseQty(
						DisplayUtil.getCaseQty(stockQty, stParam.getEnteringQty()));
					//#CM9162
					// Stock Piece Qty
					stParam.setTotalStockPieceQty(
						DisplayUtil.getPieceQty(stockQty, stParam.getEnteringQty()));

				}
				else
				{
					if (firstFlg)
					{
						//#CM9163
						// Set value on vector when it breaks.
						vec.addElement(stParam);
					}

					firstFlg = true;

					stParam = new StockControlParameter();

					//#CM9164
					// Set value on variable for break.
					consignorcode = stock[i].getConsignorCode();
					itemcode = stock[i].getItemCode();
					itemName = stock[i].getItemName1();
					location = stock[i].getLocationNo();
					usebydate = stock[i].getUseByDate();
					//#CM9165
					// Clear variables for aggregation.
					stockQty = 0;
					//#CM9166
					// Stock Qty
					stockQty = stock[i].getStockQty();

					//#CM9167
					// Search with the latest info.
					getConsignorName(consignorcode, itemcode, location, usebydate);

					//#CM9168
					// Consignor Code
					stParam.setConsignorCode(stock[i].getConsignorCode());
					//#CM9169
					// Consignor Name
					stParam.setConsignorName(consignorName);
					//#CM9170
					// Location
					stParam.setLocationNo(stock[i].getLocationNo());
					//#CM9171
					// Item Code
					stParam.setItemCode(stock[i].getItemCode());
					//#CM9172
					// Item Name
					stParam.setItemName(itemName);
					//#CM9173
					// Case/Piece flag
					stParam.setCasePieceFlag(wCasePieceFlag);
					//#CM9174
					// Case/Piece division Name
					stParam.setCasePieceFlagName(DisplayUtil.getPieceCaseValue(wCasePieceFlag));
					//#CM9175
					// Packed Qty per Case
					stParam.setEnteringQty(stock[i].getEnteringQty());
					//#CM9176
					// Packed Qty per Bundle
					stParam.setBundleEnteringQty(stock[i].getBundleEnteringQty());
					//#CM9177
					// Stock Case Qty
					stParam.setTotalStockCaseQty(DisplayUtil.getCaseQty(stockQty, stock[i].getEnteringQty()));
					//#CM9178
					// Stock Piece Qty
					stParam.setTotalStockPieceQty(DisplayUtil.getPieceQty(stockQty, stock[i].getEnteringQty()));
					//#CM9179
					// Case ITF
					stParam.setITF(wItf);
					//#CM9180
					// Bundle ITF
					stParam.setBundleITF(wBundleItf);
					//#CM9181
					// Expiry Date
					stParam.setUseByDate(stock[i].getUseByDate());

				}

			}
		}
		vec.addElement(stParam);
		vstParam = new StockControlParameter[vec.size()];
		vec.copyInto(vstParam);
		if ((vstParam != null) && (vstParam.length != 0))
		{
			for (int i = 0; i < vstParam.length; i++)
			{
				wStrText.append(re + "");
				//#CM9182
				// Consignor Code
				wStrText.append(ReportOperation.format(vstParam[i].getConsignorCode()) + tb);
				//#CM9183
				// Consignor Name
				wStrText.append(ReportOperation.format(vstParam[i].getConsignorName()) + tb);
				//#CM9184
				// Location
				wStrText.append(ReportOperation.format(vstParam[i].getLocationNo()) + tb);
				//#CM9185
				// Item Code
				wStrText.append(ReportOperation.format(vstParam[i].getItemCode()) + tb);
				//#CM9186
				// Item Name
				wStrText.append(ReportOperation.format(vstParam[i].getItemName()) + tb);
				//#CM9187
				// Case/Piece division
				wStrText.append(DisplayText.getText("LBL-W0078") + tb);
				//#CM9188
				// Packed Qty per Case
				wStrText.append(ReportOperation.format(Integer.toString(vstParam[i].getEnteringQty())) + tb);
				//#CM9189
				// Packed Qty per Bundle
				wStrText.append(ReportOperation.format(Integer.toString(vstParam[i].getBundleEnteringQty())) + tb);

				if (StockControlParameter.PRINTINGCONDITION_QTYPRINTING_ON.equals(printCondition2))
				{
					//#CM9190
					// Stock Case Qty
					wStrText.append(vstParam[i].getTotalStockCaseQty() + tb);

					//#CM9191
					// Stock Piece Qty
					wStrText.append(vstParam[i].getTotalStockPieceQty() + tb);
				}
				else
				{
					//#CM9192
					// As there’s no good method at this moment, it shall be made to Set balank
					//#CM9193
					// when 11 digits and more is set at Form side.
					//#CM9194
					// Stock Case Qty
					wStrText.append("11111111111" + tb);

					//#CM9195
					// Stock Piece Qty
					wStrText.append("11111111111" + tb);
				}

				//#CM9196
				// Case ITF
				wStrText.append(ReportOperation.format(vstParam[i].getITF()) + tb);
				//#CM9197
				// Bundle ITF
				wStrText.append(ReportOperation.format(vstParam[i].getBundleITF()) + tb);
				//#CM9198
				// Expiry Date
				wStrText.append(ReportOperation.format(vstParam[i].getUseByDate()) + tb);
				//#CM9199
				// Aggregation condition
				wStrText.append(ReportOperation.format(DisplayUtil.getPrintCondition(printCondition1)));

				//#CM9200
				// writing
				wPWriter.print(wStrText);

				wStrText.setLength(0);
			}
		}
	}

	//#CM9201
	/**
	 * Item(count) to be indicated on the list.<BR>
	 * Added Date/Time searches the latest Work Status based on the Search condition set as the parameter<BR>
	 * Obtain Consignor Name, Item Name, Case ITF, Bundle ITF, Case/Piece division of the initial data.<BR>
	 * 
	 * @param consignorcode	 Consignor Code
	 * @param itemcode Item Code
	 * @param location Location
	 * @param usebydate Expiry Date
	 * @throws ReadWriteException Inform when data connection error occurs.
	 */
	private void getConsignorName(String consignorcode, String itemcode, String location, String usebydate) throws ReadWriteException
	{
		//#CM9202
		// Generate FinderInstance
		StockReportFinder consignorFinder = new StockReportFinder(wConn);
		StockSearchKey stockSearchKey = new StockSearchKey();

		//#CM9203
		// Set Search condition.
		//#CM9204
		// Consignor Code
		if (!StringUtil.isBlank(consignorcode))
		{
			stockSearchKey.setConsignorCode(consignorcode);
		}
		//#CM9205
		// Item Code
		if (!StringUtil.isBlank(itemcode))
		{
			stockSearchKey.setItemCode(itemcode);
		}
		//#CM9206
		// Location
		if (!StringUtil.isBlank(location))
		{
			stockSearchKey.setLocationNo(location);
		}
		//#CM9207
		// Expiry Date
		if (!StringUtil.isBlank(usebydate))
		{
			stockSearchKey.setUseByDate(usebydate);
		}
		
		//#CM9208
		// Status flag:  Center Inventory
		stockSearchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);

		//#CM9209
		// Order sequence
		stockSearchKey.setLastUpdateDateOrder(1, false);

		//#CM9210
		// Search
		consignorFinder.open();
		if (consignorFinder.search(stockSearchKey) > 0)
		{
			Stock[] stock = (Stock[]) consignorFinder.getEntities(1);

			if (stock != null && stock.length != 0)
			{
				consignorName = stock[0].getConsignorName();
				wItf = stock[0].getItf();
				wBundleItf = stock[0].getBundleItf();
				wCasePieceFlag = stock[0].getCasePieceFlag();
			}
		}
		consignorFinder.close();
	}
}

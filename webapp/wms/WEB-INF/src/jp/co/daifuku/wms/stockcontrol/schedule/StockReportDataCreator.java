package jp.co.daifuku.wms.stockcontrol.schedule;

// $Id: StockReportDataCreator.java,v 1.3 2006/11/21 04:23:06 suresh Exp $

//#CM12182
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CSVItemNotFoundException;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;

//#CM12183
/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino <BR>
 * <BR>
 * <CODE>StockReportDataCreator</CODE> class executes the process to report the inventory information.<BR>
 * Inherit <CODE>AbstractExternalReportDataCreator</CODE> abstract class and implement the required processes.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Data reporting process(<CODE>report(Connection conn, Parameter startParam)</CODE>Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Connection object and Parameter object as a parameter and , database<BR>
 *   Generate inventory information file (CSV file).<BR>
 *   <BR>
 * <BR>
 *   <Details of processes> <BR>
 *      1.Initialize the data count.<BR>
 *        <DIR>
 *        Report-data count=0<BR>
 *        </DIR>
 *      2.Declare the Report Data flag.<BR>
 *        <DIR>
 *        Declare false. True if one or more report-data exist.<BR>
 *        </DIR>
 *      3.Generate a class instance for searching through the inventory information (Dmstock).<BR>
 *        <DIR>
 *        -Generate a SearchKey instance for obtaining inventory information(Dmstock)<BR>
 *        -Generate a Finder instance for obtaining inventory information(Dmstock)<BR>
 *        </DIR> 
 * 		4.Set the search conditions.
 *        <DIR>
 *        Consignor code<BR>
 *        inventory information, Status flag(Inventory)<BR>
 *        </DIR>
 * 		5.Set the search sequence.
 *        <DIR>
 *        Consignor code<BR>
 *        Item Code<BR>
 *        Location No�D<BR>
 *        Case/Piece division<BR>
 *        Storage Date<BR>
 *        Expiry Date<BR>
 *        </DIR>
 *      6.Generate Class Instance.<BR>
 *        <DIR>
 *        -Generate Generation of DataReportCsvWriter instance.<BR>
 *           <DIR>
 *           This class generates a data report CSV.<BR>
 *           </DIR>
 *        </DIR>
 *      7.Obtain the search result data in a unit of 100 data from the inventory information using Finder.<BR>
 *        Output every line of the inventory information file (CSV) to the end of the data.<BR>
 *  	<BR>
 *      8.Close the inventory information file (CSV).<BR>
 *   <BR>
 *   <inventory information file (CSV) output process outline> <BR>
 *      <DIR>
 *      Compile each item (count) in the Stock result file (CSV) and output them to the CSV file.<BR>
 *      1.Compile one line of each item (count) of the inventory information file (CSV).<BR>
 *        <DIR>
 *        (Contents of compile)<BR>
 *           <DIR>
 *           Consignor code  		 = Consignor code<BR>
 *           Consignor name    		 = Consignor name<BR>
 *           JAN         		 = Item Code<BR>
 *           Item Name        		 = Item Name<BR>
 *           Bundle ITF   		 = Bundle ITF<BR>
 *           Case ITF   		 = Case ITF<BR>
 *           Packed qty per bundle  		 = Packed qty per bundle<BR>
 *           Packed qty per Case  		 = Packed qty per Case<BR>
 *           Stock Qty         	 = Stock Qty<BR>
 *           Location No.   			 = Location No.<BR>
 *           Case/Piece division  = Case/Piece division<BR>
 *           Storage Date   			 = Storage Date<BR>
 *           Expiry Date   		 = Expiry Date<BR>
 * 			 </DIR>
 * 		  </DIR>
 *           <BR>
 *      </DIR>
 *      2.Output one line of inventory information file (CSV).<BR>
 *        <DIR>
 *        Output to the inventory information data using writeLine method of DataReportCsvWriter<BR>
 *        </DIR>
 *   </DIR> 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>M.INoue</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:06 $
 * @author  $Author: suresh $
 */
public class StockReportDataCreator extends AbstractExternalReportDataCreator
{
	//#CM12184
	// Class fields --------------------------------------------------

	//#CM12185
	// Class variables -----------------------------------------------

	//#CM12186
	// Class method --------------------------------------------------
	//#CM12187
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:23:06 $");
	}

	//#CM12188
	// Constructors --------------------------------------------------
	//#CM12189
	/**
	 * Constructors' comment
	 */
	public StockReportDataCreator()
	{
	}

	//#CM12190
	// Public methods ------------------------------------------------
	//#CM12191
	/**
	 * Receive the Connection object and Locale object as a Parameter and generate storage report-data.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParam This Class inherits the <CODE>Parameter</CODE> class.
	 * @throws IOException Announce it when I/O error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @return Return true if scceeded in generating report data, or return false if no date to be reported or failed to generate it.
	 */
	public boolean report(Connection conn, Parameter startParam)
		throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		//#CM12192
		// Initialize the Report Data count.
		setReportCount(0);

		//#CM12193
		// Generate a class instance for searching through the inventory information (Dmstock).
		StockReportFinder sfinder = new StockReportFinder(conn);
		StockSearchKey skey = new StockSearchKey();

		skey.KeyClear();

		//#CM12194
		// Set the search conditions.
		//#CM12195
		// Consignor code = Consignor code input from teh screen
		if (!sparam.getConsignorCode().equals(""))
		{
			skey.setConsignorCode(sparam.getConsignorCode());
		}

		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		skey.setStockQty(0, ">");

		//#CM12196
		// Set the search sequence.
		//#CM12197
		// Consignor code + Item Code + Location No + Case/Piece division + Storage Date(descending sequence)+ Expiry Date sequence
		skey.setConsignorCodeOrder(1, true);
		skey.setItemCodeOrder(2, true);
		skey.setLocationNoOrder(3, true);
		skey.setCasePieceFlagOrder(4, true);
		skey.setInstockDateOrder(5, true);
		skey.setUseByDateOrder(6, true);

		//#CM12198
		// Execute search for inventory information.

		//#CM12199
		// If no inventory information exists,
		if (sfinder.search(skey) <= 0)
		{
			//#CM12200
			// No target data was found.
			setMessage("6003018");
			return false;
		}

		//#CM12202
		// Declare the local variable.
		Stock[] stock = null;

		// CSV出力クラスのインスタンスを作成します。
		DataReportCsvWriter csvw = null;
		try
		{
			// CSV出力クラスのインスタンスを作成します。
			if (!(csvw instanceof DataReportCsvWriter))
			{
				csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_STOCK);
			}
			
			while (sfinder.isNext())
			{
				//#CM12203
				// Obtain every 100 search results.
				stock = (Stock[]) sfinder.getEntities(100);

				for (int i = 0; i < stock.length; i++)
				{
					//#CM12204
					// Output the changed contents of the aggregation key to the Inventory information data (CSV).
					writeCsv(csvw, stock[i]);

					//#CM12205
					// Add up the Report-data count.
					setReportCount(getReportCount() + 1);
				}
			}
			boolean t = true;
			if(t)
			{
				throw new ReadWriteException();
			}

			//#CM12206
			// Successfully completed writing data.
			setMessage("6001009");
			return true;
		}
		catch (FileNotFoundException e)
		{
			// 6006020=ファイルの入出力エラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
			// 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
			setMessage("6007031");
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006001, e), this.getClass().getName() ) ;
			// 6027009=予期しないエラーが発生しました。ログを参照してください。
			setMessage("6027009");
			
			// ファイルの削除処理
			if (csvw != null)
			{
				csvw.delete();
			}
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			try
			{
				// クローズ処理
				if (csvw != null)
				{
					csvw.close();
				}
			}
			catch (IOException e)
			{
				// 6006020=ファイルの入出力エラーが発生しました。{0}
				RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
				// 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
				setMessage("6007031");
				// ファイルの削除処理
				csvw.delete();
				throw new ReadWriteException(e.getMessage());
			}
			finally
			{
				sfinder.close();
			}
		}
	}

	//#CM12207
	// Package methods -----------------------------------------------

	//#CM12208
	// Protected methods ---------------------------------------------

	//#CM12209
	/**
	 * Output one line of the inventory information.<BR>
	 * 
	 * @param csvw CSV Writer object
	 * @param stock inventory information
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void writeCsv(DataReportCsvWriter csvw, Stock stock)
		throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		//#CM12210
		// Set the inventory information data for item (count).
		//#CM12211
		// Consignor code
		csvw.setValue("CONSIGNOR_CODE", stock.getConsignorCode());
		//#CM12212
		// Consignor name
		csvw.setValue("CONSIGNOR_NAME", stock.getConsignorName());
		//#CM12213
		// �i�`�m
		csvw.setValue("ITEM_CODE", stock.getItemCode());
		//#CM12214
		// Item Name
		csvw.setValue("ITEM_NAME1", stock.getItemName1());
		//#CM12215
		// Bundle ITF
		csvw.setValue("BUNDLE_ITF", stock.getBundleItf());
		//#CM12216
		// Case ITF
		csvw.setValue("ITF", stock.getItf());
		//#CM12217
		// Packed qty per bundle
		csvw.setValue("BUNDLE_ENTERING_QTY", new Integer(stock.getBundleEnteringQty()));
		//#CM12218
		// Packed qty per Case
		csvw.setValue("ENTERING_QTY", new Integer(stock.getEnteringQty()));
		//#CM12219
		// Stock Qty
		csvw.setValue("STOCK_QTY", new Integer(stock.getStockQty()));
		//#CM12220
		// Location No�D
		csvw.setValue("LOCATION_NO", stock.getLocationNo());
		//#CM12221
		// Case/Piece division
		csvw.setValue("CASE_PIECE_FLAG", stock.getCasePieceFlag());
		//#CM12222
		// Storage Date
		//#CM12223
		// Receiving null sets blank.
		csvw.setValue("INSTOCK_DATE", WmsFormatter.toParamDate(stock.getInstockDate()));
		//#CM12224
		// Expiry Date
		csvw.setValue("USE_BY_DATE", stock.getUseByDate());

		//#CM12225
		// Output the inventory information data to CSV.
		csvw.writeLine();

		return;
	}
	//#CM12226
	// Private methods -----------------------------------------------
}
//#CM12227
//end of class

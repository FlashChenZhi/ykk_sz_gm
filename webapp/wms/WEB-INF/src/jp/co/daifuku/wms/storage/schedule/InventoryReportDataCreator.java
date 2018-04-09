package jp.co.daifuku.wms.storage.schedule;

// $Id: InventoryReportDataCreator.java,v 1.3 2006/11/21 04:23:09 suresh Exp $

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.utility.CSVItemNotFoundException;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendReportFinder;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * <CODE>InventoryReportDataCreator</CODE> class executes the process to report the Inventory Check result data.<BR>
 * Inherit <CODE>AbstractExternalReportDataCreator</CODE> abstract class and implement the required processes.<BR>
 * The method in this class receives the connection object and executes process for updating the database.<BR>
 * However, this method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Data reporting process(<CODE>report(Connection conn, Parameter startParam)</CODE>Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Connection object and Parameter object as a parameter and <BR>
 *   Generate the inventory check result data file (CSV file) from the Sending Result Info in the database.<BR>
 * <BR>
 *   Return false when error occurs in the method.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> Mandatory Input<BR>
 *     <DIR>
 *     Connection object <BR>
 *     SystemParameter object <BR>
 *     </DIR>
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
 *      3.Generate a class instance for searching the Sending Result Info.<BR>
 *        <DIR>
 *        -Generate a sending Result Information SearchKey instance.<BR>
 *        -Generate a sending Result Information Finder instance.<BR>
 *        </DIR>
 *      4.Set the Search key to search a sending Result Info for SearchKey.<BR>
 *          <DIR>
 *          -Search conditions<BR>
 *             <DIR>
 *             Work Date = Work Date that was input via screen<BR>
 *             Work division = Increase in Inventory Check or Decrease Inventory Check<BR>
 *             Work Report flag = Not Sent<BR>
 *             </DIR>
 *          -Sequence<BR>
 *             <DIR>
 *             Consignor Code + Item Name code + Location No. + Stock ID + the added date/time<BR>
 *             </DIR>
 *          <BR>
 *          As for report data,<BR>
 *            <DIR>
 *            report the System Stock Qty and the Inventory Check Result qty in unit of the Consignor Code  +   Item Code  +  Inventory Check Location.<BR>
 *            </DIR>
 *          </DIR>
 *      5.Search using the Sending Result Information Finder instance "Search".<BR>
 *        <DIR>
 *        If no result or no corresponding data exists, execute as below.<BR>
 *          <DIR>
 *          Message = No target data was found.<BR>
 *          Close with no data reported (false).<BR>
 *          </DIR>
 *        </DIR>
 *      6.Generate local variable.<BR>
 *        <DIR>
 *        Generate the following area and set the initial value.<BR>
 *          <DIR>
 *          Aggregation key : Consignor Code, Item Code, Location No. (Inventory Check Location)<BR>
 *            <DIR>
 *            Consignor code = ""<BR>
 *            Item Code = ""<BR>
 *            Location No(Inventory Check Location) = ""<BR>
 *            </DIR>
 *          Stock ID = ""<BR>
 *          System Stock Qty = 0<BR>
 *          Inventory Check Result Qty = 0<BR>
 *          </DIR>
 *        </DIR>
 *      7.Generate Class Instance.<BR>
 *        <DIR>
 *        -Generate Generation of DataReportCsvWriter instance.<BR>
 *           <DIR>
 *           This class generates a data report CSV.<BR>
 *           </DIR>
 *        </DIR>
 *      8.Loop to the end of the target sending Result Info.<BR>
 *        <DIR>
 *        <BR>
 *        - Start looping -<BR>
 *        <BR>
 *          <DIR>
 *          9.Obtain the search result data in a unit of 100 data from the Sending Result Info using getEntityes of Finder.<BR>
 *         10.Loop until closing the obtained sending Result Info Entity.<BR>
 *          <BR>
 *          - Start looping -<BR>
 *          <BR>
 *            <DIR>
 *           11.Refer to the Consignor code of the Update local variable and update the local variable if Consignor code = " ".<BR>
 *              <DIR>
 *              Consignor code = " " means the initial time.<BR>
 *              <BR>
 *              Consignor code = Consignor code of the Sending Result Info.<BR>
 *              Item Code = Item Code of the Sending Result Information<BR>
 *              Location No(Inventory Check Location) = Location No. in the Sending Result Information<BR>
 *              Stock ID = Stock ID of the Sending Result Information<BR>
 *              System Stock Qty = Planned Work Qty of the Sending Result Information(Host Planned Qty)<BR>
 *              Inventory Check Result Qty = Work Result qty of the Sending Result Information<BR>
 *                <DIR>
 *                If work division  =  inventory check decrease, however, set the work result qty of the Sending result info to negative value (-).<BR>
 *                </DIR>
 *              </DIR>
 *           12.Compare the local variable and the aggregation key (except for Work Date) of the Sending Result Info.<BR>
 *              <DIR>
 *              -Execute the following processes, if changed to the next aggregation key.<BR>
 *                 <DIR>
 *                 a. Output the inventory check result.<BR>
 *                   <DIR>
 *                   -Output the Inventory Check result file (CSV).<BR>
 *                    (Opening no CSV file allows to open the Inventory Check Result CSV file.)<BR>
 *                   -Add up the Report-data count.<BR>
 *                   -Set true Report Data flag.<BR>
 *                   </DIR>
 *                 b.Update the local variable.<BR>
 *                   <DIR>
 *                   -Update the aggregation key.<BR>
 *                      <DIR>
 *                      Same as 11.<BR>
 *                      </DIR>
 *                   -System Stock Qty = Planned Work Qty of the Sending Result Information(Host Planned Qty)<BR>
 *                   -Inventory Check Result Qty = Work Result qty of the Sending Result Information<BR>
 *                      <DIR>
 *                      If work division  =  inventory check decrease, however, set the work result qty of the Sending result info to negative value (-).<BR>
 *                      </DIR>
 *                   </DIR>
 *                 </DIR>
 *              -When not changed to the next aggregation key,<BR>
 *                 <DIR>
 *                 a. Compare the Stock ID of local variable and the Stock ID of the Sending Result Info. If those Stock ID are not equal,<BR>
 *                   execute the following processes.<BR>
 *                   <DIR>
 *                   System Stock Qty = System Stock Qty + Planned Work Qty of the Sending Result Information(Host Planned Qty)<BR>
 *                   Determine the Inventory Check result qty as below.<BR>
 *                     <DIR>
 *                     For data with Work division "Increase Inventory Check",<BR>
 *                       <DIR>
 *                       Inventory Check Result Qty = Inventory Check Result Qty + Work Result qty of the Sending Result Information<BR>
 *                       </DIR>
 *                     If it is Work division=Decrease Inventory Check<BR>
 *                       <DIR>
 *                       Inventory Check Result Qty = Inventory Check Result Qty - Work Result qty of the Sending Result Information<BR>
 *                       </DIR>
 *                     </DIR>
 *                   </DIR>
 *                 </DIR>
 *              </DIR>
 *            </DIR>
 *          <BR>
 *          - Close looping -<BR>
 *          <BR>
 *          </DIR>
 *        <BR>
 *        - Close looping -<BR>
 *        <BR>
 *        </DIR>
 *     13.Output the currently processing sending Result Info to the Inventory Check result file (CSV).<BR>
 *        <DIR>
 *        -Output the Inventory Check result file (CSV).<BR>
 *         (Opening no CSV file allows to open the Inventory Check Result CSV file.)<BR>
 *        -Add up the Report-data count.<BR>
 *        -Set true Report Data flag.<BR>
 *        </DIR>
 *     14.Close the Storage Result file (CSV).<BR>
 *     15.Update the Report flag.<BR>
 *          <DIR>
 *         15-1.Generate Handler instance for the Sending Result Info.<BR>
 *         15-2.Generate AlterKey instance for the Sending Result Info.<BR>
 *         15-3.Set the Search key to update sending result info and update data to AlterKey.<BR>
 *              <DIR>
 *              -Search conditions<BR>
 *                 <DIR>
 *                 Work Date = Work Date that was input via screen<BR>
 *                 Work division = Increase in Inventory Check or Decrease Inventory Check<BR>
 *                 Work Report flag = Not Sent<BR>
 *                 </DIR>
 *              -Updated data<BR>
 *                 <DIR>
 *                 Work report flag = Sent<BR>
 *                 </DIR>
 *              </DIR>
 *         15-4.Modify the Sending Result Info and click on Modify button to update it.<BR>
 *          </DIR>
 *     16.Set true Report Data flag.<BR>
 *     17.Set the message.<BR>
 *        <DIR>
 *        Message = Closed generating report-data.<BR>
 *        </DIR>
 * <BR>
 *   <Location check result file (CSV) output process outline> <BR>
 *      <DIR>
 *      Compile each item (count) of Inventory Check result file (CSV) and output to the CSV file.<BR>
 *      1.Compile each item (count) on one line of Inventory Check result file (CSV) from the Sending Result Info.<BR>
 *        <DIR>
 *        (Contents of compile)<BR>
 *           <DIR>
 *           Consignor code   = Consignor code<BR>
 *           Consignor name     = Consignor name<BR>
 *           JAN          = Item Code<BR>
 *           Item Name         = Item Name<BR>
 *           Packed qty per Case   = Packed qty per Case<BR>
 *           System Stock Qty = Sum System Stock Qty<BR>
 *           Inventory Check Result Qty   = Sum Work Result Qty<BR>
 *             <DIR>
 *             Sum of Planned Work Qty (Host Planned Qty) of the Sending Result Information  +  Sum of Work Result qty of the Sending Result Information<BR>
 *             <BR>
 *             (Note)<BR>
 *             Regard the Work result qty of the Sending Result Info with Work division "Increase Inventory Check" as a positive value ( + ), or regard that with Work division "Decrease Inventory Check" as <BR>
 *             as a negative value (-).<BR>
 *             </DIR>
 *           Inventory Check Location       = Location No<BR>
 *           Relocation Result Date   = Work Date<BR>
 *           </DIR>
 *        </DIR>
 *      2.Output one line of inventory check result file (CSV).<BR>
 *        <DIR>
 *        Output to the Inventory Check result data using writeLine method of DataReportCsvWriter.<BR>
 *        </DIR>
 *      </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>T.Nakai</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:09 $
 * @author  $Author: suresh $
 */
public class InventoryReportDataCreator extends AbstractExternalReportDataCreator
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private final String pName = "InventoryReportData";

	// Class method --------------------------------------------------
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:23:09 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * Receive Connection object and Locale object as a parameter and generate inventory check report-data.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParam This Class inherits the <CODE>Parameter</CODE> class.
	 * @return Return true if scceeded in generating report data, or return false if no date to be reported or failed to generate it.
	 * @throws IOException Announce it when I/O error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// Initialize the Report Data count.
		setReportCount(0);

		// Generate instance to search for the Sending Result Info.
		HostSendSearchKey shkey = new HostSendSearchKey();
		HostSendReportFinder shfinder = new HostSendReportFinder(conn);

		// Set the search conditions.
		// Work Date = Work Date that was input via screen
		shkey.setWorkDate(sparam.getWorkDate());
		// Work division. (Increase Inventory Check, Decrease Inventory Check)
		String[] jobType = { SystemDefine.JOB_TYPE_INVENTORY_PLUS,
							 SystemDefine.JOB_TYPE_INVENTORY_MINUS };
		shkey.setJobType(jobType);
		// Work Report flag = Not Sent
		shkey.setReportFlag(SystemDefine.REPORT_FLAG_NOT_SENT);

		// Set the search sequence.
		// Consignor code + Item Code + Location No + Expiry Date + Stock ID + Added Date/Time(descending sequence)  sequence
		shkey.setConsignorCodeOrder(1, true);
		shkey.setItemCodeOrder(2, true);
		shkey.setLocationNoOrder(3,true);
		shkey.setUseByDateOrder(4,true);
		shkey.setStockIdOrder(5,true);
		shkey.setRegistDateOrder(6,false);

		// Search for the Result data inquiry.
		if( shfinder.search(shkey) <= 0 )
		{
			// No target data was found.
			setMessage("6003018");
			return false;
		}

		// Declare the local variable.
		HostSend[] hostSend = null;
		HostSend hostSendSv = null;
		String consignorCode = "";
		String itemCode = "";
		String locatioNo = "";
		String useByDate = "";
		String stockId = "";
		int systemQty = 0;
		int resultQty = 0;

		//Generate CSV output class instance.
		DataReportCsvWriter csvw = null;
		try
		{
			//Generate CSV output class instance.
			if (!(csvw instanceof DataReportCsvWriter))
			{
				csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_STOCKTAKINGSUPPOR);
			}

			while (shfinder.isNext())
			{
				// Obtain every 100 search results.
				hostSend = (HostSend[]) shfinder.getEntities(100);

				// Set the value for the local variable of the leading data if any.
				if(StringUtil.isBlank(consignorCode))
				{
					hostSendSv = hostSend[0];
					consignorCode = hostSend[0].getConsignorCode();
					itemCode = hostSend[0].getItemCode();
					locatioNo = hostSend[0].getLocationNo();
					useByDate = hostSend[0].getUseByDate();
					stockId = hostSend[0].getStockId();
					// Enable the latest System stock qty and Relocation result qty of data with the same Stock ID.
					systemQty = hostSend[0].getHostPlanQty();
					resultQty = QtyCalculation(hostSend[0].getJobType(), hostSend[0].getResultQty());
				}

				for( int i = 0 ; i < hostSend.length ; i++ )
				{
					// Check whether the Aggregation key has changed or not?
					// Compare using Consignor Code  +   Item Code  +  Location No.
					if ( !(hostSend[i].getConsignorCode().equals(consignorCode))
						|| !(hostSend[i].getItemCode().equals(itemCode))
						|| !(hostSend[i].getLocationNo().equals(locatioNo))
						|| !(hostSend[i].getUseByDate().equals(useByDate)) )
					{
						// Output the changed contents of the aggregation key to the Inventory Check Result data (CSV).
						CsvWrite(csvw, hostSendSv, systemQty, resultQty);

						// Add up the Report-data count.
						setReportCount(getReportCount() + 1);

						// Update the local variable.
						hostSendSv = hostSend[i];
						consignorCode = hostSend[i].getConsignorCode();
						itemCode = hostSend[i].getItemCode();
						locatioNo = hostSend[i].getLocationNo();
						useByDate = hostSend[i].getUseByDate();
						stockId = hostSend[i].getStockId();
						systemQty = hostSend[i].getHostPlanQty();
						resultQty = QtyCalculation(hostSend[i].getJobType(), hostSend[i].getResultQty());
					}
					else
					{
						// Twice stock relocations for inventory check generates the same Stock ID in the Aggregation. Therefore,
						// if there are two or more data with the same Stock ID, disregard the older Results.
						if(!(hostSend[i].getStockId().equals(stockId)))
						{
							// For data with Stock ID: not equal
							// Sum up the system stock qty and Relocated qty in the aggregation.
							systemQty = systemQty + hostSend[i].getHostPlanQty();
							resultQty = resultQty + QtyCalculation(hostSend[i].getJobType(), hostSend[i].getResultQty());
						}
					}
				}
			}

			//Output it to the Inventory Check result data (CSV) that has not yet been output.
			CsvWrite(csvw, hostSendSv, systemQty, resultQty);
			//Add up the Report-data count.。
			setReportCount(getReportCount() + 1);

			// Update the Report flag and the last update date/time.
			// 	Sending Result Information Updating Key Instance
			HostSendHandler hhandle = new HostSendHandler(conn);
			HostSendAlterKey hakey = new HostSendAlterKey();

			// Update the Report flag of the Sending Result Info.
			// Set the Update key.
			// Work Date = Work Date that was input via screen
			hakey.setWorkDate(sparam.getWorkDate());
			// Work division. (Increase Inventory Check, Decrease Inventory Check)
			String[] altJobType = { SystemDefine.JOB_TYPE_INVENTORY_PLUS,
									SystemDefine.JOB_TYPE_INVENTORY_MINUS };
			hakey.setJobType(altJobType);

			//  Report flag = Not Sent
			hakey.setReportFlag(SystemDefine.REPORT_FLAG_NOT_SENT);

			// Set the update item (count).
			// Update the Report flag of the Sending Result Info to "Reported".
			hakey.updateReportFlag(SystemDefine.REPORT_FLAG_SENT);
			hakey.updateLastUpdatePname(pName);
			// Update the Sending Result Info.
			hhandle.modify(hakey);

			//Closed generating report-data.
			setMessage("6001009");

			return true;
		}
		catch (FileNotFoundException e)
		{
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
			//6007031=File I/O error occurred. See log.
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
				// 6006020=File I/O error occurred. {0}
				RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
				//6007031=File I/O error occurred. See log.
				setMessage("6007031");
				// ファイルの削除処理
				csvw.delete();
				throw new ReadWriteException(e.getMessage());
			}
			finally
			{
				shfinder.close();
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * Calculate the Inventory Check result qty.<BR>
	 *  @param jobType Work division(Increase in Inventory Check or Decrease Inventory Check)
	 * @param resultQty Inventory Check Result Qty(Work Result qty)
	 * @return calculation result
	 */
	private int QtyCalculation(String jobType, int resultQty)
	{
		int qty = resultQty;

		if( jobType.equals(SystemDefine.JOB_TYPE_INVENTORY_MINUS) )
		{
			// For data with work division Decrease Inventory Check, change the Inventory Check Result qty to negative.
			qty = 0 - resultQty;
		}
		return qty;
	}

	/**
	 * Output one line of the result.<BR>
	 * @param csvw CSV Writer object
	 * @param hostSend Sending Result Information
	 * @param systemQty System Stock Qty
	 * @param resultQty Inventory Check Result Qty
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void CsvWrite(DataReportCsvWriter csvw, HostSend hostSend, int systemQty, int resultQty)
													throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		// Set the Inventory Check Result Data for item (count).

		// Consignor code
		csvw.setValue("CONSIGNOR_CODE", hostSend.getConsignorCode());
		// Consignor name
		csvw.setValue("CONSIGNOR_NAME", hostSend.getConsignorName());
		// JAN
		csvw.setValue("ITEM_CODE", hostSend.getItemCode());
		// Item Name
		csvw.setValue("ITEM_NAME1", hostSend.getItemName1());
		//Packed qty per Case
		csvw.setValue("ENTERING_QTY", new Integer(hostSend.getEnteringQty()));
		// System Stock Qty
		csvw.setValue("PLAN_QTY", new Integer(systemQty));
		// Inventory Check Result Qty
		int qty = systemQty + resultQty;
		csvw.setValue("RESULT_QTY_CASE", new Integer(qty));
		// Inventory Check Location
		csvw.setValue("JOB_LOCATION_CASE", hostSend.getLocationNo());
		//Inventory Check Result Date
		csvw.setValue("COMPLETE_DATE", hostSend.getWorkDate());
		// Expiry Date
		csvw.setValue("USE_BY_DATE", hostSend.getUseByDate());

		// Output the Inventory Check Result Data to the CSV.
		csvw.writeLine();

		return;
	}
}
//end of class
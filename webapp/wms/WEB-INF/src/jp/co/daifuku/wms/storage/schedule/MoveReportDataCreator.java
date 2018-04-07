package jp.co.daifuku.wms.storage.schedule;

// $Id: MoveReportDataCreator.java,v 1.3 2006/11/21 04:23:10 suresh Exp $

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
import jp.co.daifuku.wms.base.utility.CSVItemNotFoundException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.storage.dbhandler.StorageHostSendSearchKey;
import jp.co.daifuku.wms.storage.dbhandler.StorageHostSendFinder;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * <CODE>MoveReportDataCreator</CODE> class executes the process to report the Stock Relocation result data<BR>
 * Inherit <CODE>AbstractExternalReportDataCreator</CODE> abstract class and implement the required processes.<BR>
 * The method in this class receives the connection object and executes process for updating the database.<BR>
 * However, this method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Data reporting process(<CODE>report(Connection conn, Parameter startParam)</CODE>Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Connection object and Parameter object as a parameter and <BR>
 *   generate a stock relocation result data file (CSV file) from the Sending Result Info in the database.<BR>
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
 *        -Generate SearchKey instance for obtaining the stock relocation of the Sending Result Info.<BR>
 *        -Generate Finder instance for obtaining the stock relocation of the Sending Result Info.<BR>
 *        </DIR>
 *      4.Set the Search key to search a sending Result Info for SearchKey.<BR>
 *          <DIR>
 *          -Search conditions<BR>
 *             <DIR>
 *             Work Date = Work Date that was input via screen<BR>
 *             Work division =Relocate Storage<BR>
 *             Status flag = Completed (3)<BR>
 *             Work report flag = Standby Work<BR>
 *             </DIR>
 *          -Sequence<BR>
 *             <DIR>
 *             Consignor Code + Item Code + Location No. + work result location<BR>
 *             </DIR>
 *          <BR>
 *          As for report data,<BR>
 *            <DIR>
 *            report the relocation qty in unit of Consignor + Item Name + relocation picking location + relocation storage location.<BR>
 *            </DIR>
 *          </DIR>
 *      5.Search for the Sending Result Information using the Search of Finder instance for obtaining Inventory Relocation and <BR>
 *        obtain it in the Sending Result Information Entity.<BR>
 *        <DIR>
 *        If the count of obtained sending Result Info is 0:<BR>
 *          <DIR>
 *          Message = No target data was found.<BR>
 *          Close with no data reported (false).<BR>
 *          </DIR>
 *        </DIR>
 *      6.Generate Class Instance.<BR>
 *        <DIR>
 *        -Generate Generation of DataReportCsvWriter instance.<BR>
 *           <DIR>
 *           This class generates a data report CSV.<BR>
 *           </DIR>
 *        </DIR>
 *      7.Obtain the search result data in a unit of 100 data from the Sending Result Info using getEntityesStockMove of Finder and <BR>
 *        output every data to Stock Relocation result file (CSV) to the end of the data.<BR>
 *          <DIR>
 *          Update the Report-data count.<BR>
 *          </DIR>
 *      8.Close the Storage Result file (CSV).<BR>
 *      9.Update the Report flag.<BR>
 *          <DIR>
 *          9-1.Generate Handler instance for the Sending Result Info.<BR>
 *          9-2.Generate AlterKey instance for the Sending Result Info.<BR>
 *          9-3.Set the Search key to update sending result info and update data to AlterKey.<BR>
 *              <DIR>
 *              -Search conditions<BR>
 *                 <DIR>
 *                 Work Date, Work division (Relocation storage), Status flag (complete), "Report" flag (Not Sent)<BR>
 *                 </DIR>
 *              -Updated data<BR>
 *                 <DIR>
 *                 Report flag = Reported<BR>
 *                 </DIR>
 *              </DIR>
 *          9-4.Modify the Sending Result Info and click on Modify button to update it.<BR>
 *          </DIR>
 *     10.Set true Report Data flag.<BR>
 *     11.Set the message.<BR>
 *        <DIR>
 *        Message = Closed generating report-data.<BR>
 *        </DIR>
 * <BR>
 *   <Output to the Inventory Relocation Result file (CSV) process outline> <BR>
 *      <DIR>
 *      Compile each item (count) in the Stock Relocation result file (CSV) and output them to the CSV file.<BR>
 *      1.Compile one line of items (count) in the Stock Relocation result file (CSV).<BR>
 *        <DIR>
 *        (Contents of compile)<BR>
 *           <DIR>
 *           Consignor code   = Consignor code<BR>
 *           Consignor name     = Consignor name<BR>
 *           JAN          = Item Code<BR>
 *           Item Name         = Item Name<BR>
 *           Packed qty per Case   = Packed qty per Case<BR>
 *           Relocated qty(Total bulk qty) = Work Result qty<BR>
 *           Relocation Picking Location   = Location No<BR>
 *           Relocation Storage Location   = Work Result Location<BR>
 *           Relocation Result Date   = Work Date<BR>
 *           </DIR>
 *        </DIR>
 *      2.Output one line of Storage Result file (CSV).<BR>
 *        <DIR>
 *        Output to the stock relocation result data using writeLine method of DataReportCsvWriter<BR>
 *        </DIR>
 *      </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>T.Nakai</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:10 $
 * @author  $Author: suresh $
 */
public class MoveReportDataCreator extends AbstractExternalReportDataCreator
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private final String pName = "MoveReportData";

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
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:23:10 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * Receive Connection object and Locale object as a parameter and generate the Stock Relocation report-data.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParam This Class inherits the <CODE>Parameter</CODE> class.
	 * @return Return true if scceeded in generating report data, or return false if no date to be reported or failed to generate it.
	 * @throws IOException Announce it when I/O error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 *
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// Initialize the Report Data count.
		setReportCount(0);

		// Generate instance to search for the Sending Result Info.
		StorageHostSendSearchKey shkey = new StorageHostSendSearchKey();
		StorageHostSendFinder shfinder = new StorageHostSendFinder(conn);

		// Set the column to be obtained.
		// Consignor code
		shkey.setConsignorCodeCollect("");
		// Consignor name
		shkey.setConsignorNameCollect("");
		// Item Code
		shkey.setItemCodeCollect("");
		// Item Name
		shkey.setItemName1Collect("");
		// Packed qty per Case
		shkey.setEnteringQtyCollect("");
		// Relocated qty
		shkey.setResultQtyCollect("");
		// Location
		shkey.setLocationNoCollect("");
		// Work Result Location
		shkey.setResultLocationNoCollect("");
		// Work Date
		shkey.setWorkDateCollect("");
		// Expiry Date
		shkey.setResultUseByDateCollect("");

		// Set the search conditions.
		// Work Date = Work Date that was input via screen
		shkey.setWorkDate(sparam.getWorkDate());
		// Work division =Relocate Storage
		shkey.setJobType(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE);
		// Status flag = Completed
		shkey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
		// Report flag = Not Sent
		shkey.setReportFlag(SystemDefine.REPORT_FLAG_NOT_SENT);

		// Set the search sequence.
		// In the order of Consignor Code + Item Code + Location No. + Work Result Location + Expiry Date
		shkey.setConsignorCodeOrder(1, true);
		shkey.setItemCodeOrder(2, true);
		shkey.setLocationNoOrder(3,true);
		shkey.setResultLocationNoOrder(4,true);
		shkey.setResultUseByDateOrder(5,true);

		// Search for the Result data inquiry.
		if( shfinder.search(shkey) <= 0 )
		{
			// No target data was found.
			setMessage("6003018");
			return false;
		}

		HostSend[] hostSend = null;
		// Generate CSV output class instance.
		DataReportCsvWriter csvw = null;
		try
		{
			// Generate CSV output class instance.
			if (!(csvw instanceof DataReportCsvWriter))
			{
				csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_MOVINGSUPPOR);
			}

			// This flag determines whether the initial process has been done or not.
			boolean firstFlag = true;

			// Instance to maintain Inventory Relocation Result Information for writing
			HostSend reportHostSend = new HostSend();

			while (shfinder.isNext())
			{
				// Output every 100 search results.
				hostSend = (HostSend[]) shfinder.getEntityesStockMove(100);

				for( int i = 0 ; i < hostSend.length ; i++ )
				{
					//  Disable to output to CSV for the initial time.
					if (firstFlag)
					{
						reportHostSend.setConsignorCode(hostSend[i].getConsignorCode());
						reportHostSend.setConsignorName(hostSend[i].getConsignorName());
						reportHostSend.setItemCode(hostSend[i].getItemCode());
						reportHostSend.setItemName1(hostSend[i].getItemName1());
						reportHostSend.setEnteringQty(hostSend[i].getEnteringQty());
						reportHostSend.setResultQty(hostSend[i].getResultQty());
						reportHostSend.setLocationNo(hostSend[i].getLocationNo());
						reportHostSend.setResultLocationNo(hostSend[i].getResultLocationNo());
						reportHostSend.setWorkDate(hostSend[i].getWorkDate());
						reportHostSend.setResultUseByDate(hostSend[i].getResultUseByDate());

						// Change the status to the Initial Processed.
						firstFlag = false;

						continue;
					}

					// Consignor if changed in Code, Item Code, Relocation picking location, Relocation storage location, or expiry date.
					if (!reportHostSend.getConsignorCode().equals(hostSend[i].getConsignorCode())
					 || !reportHostSend.getItemCode().equals(hostSend[i].getItemCode())
					 || !reportHostSend.getLocationNo().equals(hostSend[i].getLocationNo())
					 || !reportHostSend.getResultLocationNo().equals(hostSend[i].getResultLocationNo())
					 || !reportHostSend.getResultUseByDate().equals(hostSend[i].getResultUseByDate()))
					{
						// Output to the Stock Relocation result data (CSV).
						StorageCsvWrite(csvw, reportHostSend);
						// Adds the report count.
						setReportCount(getReportCount() + 1);

						// Initialize the result qty.
						reportHostSend.setResultQty(0);
					}

					// Maintain the current target data ( Replenish the result qty).
					reportHostSend.setConsignorCode(hostSend[i].getConsignorCode());
					reportHostSend.setConsignorName(hostSend[i].getConsignorName());
					reportHostSend.setItemCode(hostSend[i].getItemCode());
					reportHostSend.setItemName1(hostSend[i].getItemName1());
					reportHostSend.setEnteringQty(hostSend[i].getEnteringQty());
					reportHostSend.setResultQty(reportHostSend.getResultQty() + hostSend[i].getResultQty());
					reportHostSend.setLocationNo(hostSend[i].getLocationNo());
					reportHostSend.setResultLocationNo(hostSend[i].getResultLocationNo());
					reportHostSend.setWorkDate(hostSend[i].getWorkDate());
					reportHostSend.setResultUseByDate(hostSend[i].getResultUseByDate());
				}
			}

			// Output Remaining lines after the current bottom line to the Stock Relocation result data (CSV).。
			StorageCsvWrite(csvw, reportHostSend);
			// Adds the report count.
			setReportCount(getReportCount() + 1);

			// Update the Report flag and the last update date/time.
			// Sending Result Information Updating Key Instance
			HostSendHandler hhandle = new HostSendHandler(conn);
			HostSendAlterKey hakey = new HostSendAlterKey();

			// Update the Report flag of the Sending Result Info.
			// Set the Update key.
			// Work Date = Work Date that was input via screen
			hakey.setWorkDate(sparam.getWorkDate());
			// Work division =Relocate Storage
			hakey.setJobType(SystemDefine.JOB_TYPE_MOVEMENT_STORAGE);
			// Status flag = Completed
			hakey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			// Report flag = Not Sent
			hakey.setReportFlag(SystemDefine.REPORT_FLAG_NOT_SENT);

			// Set the update item (count).
			// Update the Report flag of the Sending Result Info to "Reported".
			hakey.updateReportFlag(SystemDefine.REPORT_FLAG_SENT);
			hakey.updateLastUpdatePname(pName);
			// Update the Sending Result Info.
			hhandle.modify(hakey);

			// Closed generating report-data.
			setMessage("6001009");

			return true;
		}
		catch (FileNotFoundException e)
		{
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
			// 6007031=File I/O error occurred. See log.
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
				// 6007031=File I/O error occurred. See log.
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
	 * Output one line of Storage Result.<BR>
	 *
	 * @param csvw CSV Writer object
	 * @param hostSend Sending Result Information
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void StorageCsvWrite(DataReportCsvWriter csvw, HostSend hostSend)
													throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		// Set the Stock Relocation result data for item (count).
		// Consignor code
		csvw.setValue("CONSIGNOR_CODE", hostSend.getConsignorCode());
		// Consignor name
		csvw.setValue("CONSIGNOR_NAME", hostSend.getConsignorName());
		// Item Code
		csvw.setValue("ITEM_CODE", hostSend.getItemCode());
		// Item Name
		csvw.setValue("ITEM_NAME1", hostSend.getItemName1());
		// Packed qty per Case
		csvw.setValue("ENTERING_QTY", new Integer(hostSend.getEnteringQty()));
		// Relocated qty (Total Bulk Qty)
		csvw.setValue("RESULT_QTY_CASE", new Integer(hostSend.getResultQty()));
		// Relocation Picking Location
		csvw.setValue("JOB_LOCATION_PIECE", hostSend.getLocationNo());
		// Relocation Storage Location
		csvw.setValue("JOB_LOCATION_CASE", hostSend.getResultLocationNo());
		// Storage Result Date
		csvw.setValue("COMPLETE_DATE", hostSend.getWorkDate());
		// Expiry Date
		csvw.setValue("USE_BY_DATE", hostSend.getResultUseByDate());

		// Output the Stock result data to the CSV.
		csvw.writeLine();

		return;
	}
}
//end of class
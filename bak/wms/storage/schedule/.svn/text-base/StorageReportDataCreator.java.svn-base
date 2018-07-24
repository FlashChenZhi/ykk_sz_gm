package jp.co.daifuku.wms.storage.schedule;

// $Id: StorageReportDataCreator.java,v 1.3 2006/11/21 04:23:11 suresh Exp $

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.utility.CSVItemNotFoundException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.HostSendView;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.wms.storage.dbhandler.StorageHostSendViewFinder;
import jp.co.daifuku.wms.storage.dbhandler.StorageHostSendViewSearchKey;
import jp.co.daifuku.wms.storage.dbhandler.StorageWorkingInformationHandler;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 *
 * <CODE>StorageReportDataCreator</CODE> class executes the process to report the Storage Result data.<BR>
 * Inherit <CODE>AbstractExternalReportDataCreator</CODE> abstract class and implement the required processes.<BR>
 * The method in this class receives the connection object and executes process for updating the database.<BR>
 * However, this method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Data reporting process(<CODE>report(Connection conn, Parameter startParam)</CODE>Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Connection object and Parameter object as a parameter and <BR>
 *   Generate the storage result data file (CSV file) from the VIEW tied-up with the Sending Result Information and the Work Status in the database.<BR>
 *   <BR>
 *   (Note)<BR>
 *   Generate data with "Not Processed" and "Completed Storage".<BR>
 *     <DIR>
 *     Not Processed data refers to the following status:<BR>
 *       <DIR>
 *       a.Data with status Standby.<BR>
 *       b.Data with Standby and Completed mixed in the data with the same plan unique key.<BR>
 *           <DIR>
 *           In the current specification, the target is a data of which Case/Piece division changed into Mixed during extraction.<BR>
 *           For a work data with status Completed Case Work only and Standby Piece Work, for example, regard it as Not-Processed data.<BR>
 *           </DIR>
 *       </DIR>
 *     </DIR>
 * <BR>
 *     <DIR>
 *     VIEW generating condition<BR>
 *       <DIR>
 *       -Work Status(dnworkinfo)<BR>
 *          <DIR>
 *          Status flag <> Delete<BR>
 *          Status flag <> Completed<BR>
 *          Flag to report standby work = Not Sent<BR>
 *          </DIR>
 *       -the Sending Result Information (dnhostsend)<BR>
 *          <DIR>
 *          Work Report flag = Not Sent<BR>
 *          </DIR>
 *       Generate VIEW using items of Work status and sending Result Info other than follows.<BR>
 *         <DIR>
 *         Lot No. (lot_no, result_lot_no), Work Report flag, Worker Code,<BR>
 *         Terminal No., RFT No., added process name, last update date/time, last Update process name<BR>
 *         (Note)<BR>
 *           <DIR>
 *           a.No Work Date in Work Status causes to set Null for Work Date of View Information obtained from Work Status.<BR>
 *           b.Expiry Date<BR>
 *             <DIR>
 *             Obtain the Work status from the expiry date (use_by_date) and obtain the Sending Result Info from expiry date (result_use_by_date).<BR>
 *             </DIR>
 *           </DIR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
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
 *      3.Generate a class instance for searching through the VIEW.<BR>
 *        <DIR>
 *        -Generate Handler instance for VIEW.<BR>
 *        -Generate SearchKey instance for VIEW.<BR>
 *        </DIR>
 *      4.Set the Search key to search VIEW for SearchKey.<BR>
 *          <DIR>
 *          -Search conditions<BR>
 *             <DIR>
 *             Work division = Storage<BR>
 *             Status flag = Completed or Standby<BR>
 *             Work Date = Work Date that was input via screen or ""<BR>
 *             </DIR>
 *          -Sequence<BR>
 *             <DIR>
 *             Plan unique key + Added Date/Time + Work Result qty + Expiry Date + Case/Piece division + Location No.<BR>
 *             </DIR>
 *          </DIR>
 *      5.Search through the VIEW using VIEW Handler instance "find" and obtain the result in the Sending Result View Entity.<BR>
 *        <DIR>
 *         If the count of data obtained in the Sending Result View is zero:<BR>
 *          <DIR>
 *          Message = No target data was found.<BR>
 *          Close with no data reported (false).<BR>
 *          </DIR>
 *        </DIR>
 *      6.Generate local variable.<BR>
 *        <DIR>
 *        Generate the following area and set the initial value.<BR>
 *          <DIR>
 *          -Set the contents of each item (count) of the leading data in the Sending Result View.<BR>
 *             <DIR>
 *             Plan unique key = Plan unique key of the leading Sending Result View<BR>
 *             Expiry date = Expiry date residing on the top line of the Sending Result View<BR>
 *             Case/Piece division = Case/Piece division of the leading data in the Sending Result View<BR>
 *             Location No = Location No. of the leading data in the Sending Result View<BR>
 *             </DIR>
 *          -Result division = Completed Storage<BR>
 *          -Storage Result qty = 0<BR>
 *          -Vector variables of the Sending Result View Information and Storage Result qty<BR>
 *          -Generate a area for the Result output flag by each Aggregation key = false<BR>
 *          </DIR>
 *        <BR>
 *        </DIR>
 *      7.Generate Class Instance.<BR>
 *        <DIR>
 *        -Generate Generation of DataReportCsvWriter instance.<BR>
 *           <DIR>
 *           This class generates a data report CSV.<BR>
 *           </DIR>
 *        </DIR>
 *      8.Loop to the end of the Sending Result View Entity that was obtained from VIEW.<BR>
 *        <DIR>
 * <BR>
 *        - Start looping -<BR>
 * <BR>
 *          <DIR>
 *          9.Execute the following processes, when plan unique key of the Sending Result View changed to the next plan unique key.<BR>
 *            <DIR>
 *            9-1.If there is data to be reported (result output flag for each plan unique key is true).<BR>
 *               <DIR>
 *               However, if [Report Work in process]  is not designated and Result division = Not Processed <BR>
 *               Disable to output any result data by the plan unique key data.<BR>
 *               Execute the following processes to output data.<BR>
 *                 <DIR>
 *                 -Output the Storage Result data that is stored in a Vector variable to Storage Result file (CSV).<BR>
 *                 (Opening no CSV file allows to open the Storage Result CSV file.)<BR>
 *                 <BR>
 *                 (Note)<BR>
 *                    <DIR>
 *                    Disable to output any data with both Case Storage Result qty and Piece Storage Result qty equal to 0 to the report-data.<BR>
 *                    </DIR>
 *                 -Add up the Report-data count.<BR>
 *                 -Set true Report Data flag.<BR>
 *                 </DIR>
 *               </DIR>
 *            9-2.Update the local variable.<BR>
 *               <DIR>
 *               -Set the contents of each item (count) of the next data in the Sending Result View.<BR>
 *                  <DIR>
 *                  Plan unique key = Next Plan unique key shown in the Sending Result View<BR>
 *                  Expiry date = the expiry date residing on the next line of the Sending Result View<BR>
 *                  Case/Piece division = Case/Piece division of the next data in the Sending Result View<BR>
 *                  Location No = Location No. of the next data in the Sending Result View<BR>
 *                  </DIR>
 *               -Result division = Completed Storage<BR>
 *               -Storage Result qty = 0<BR>
 *               -Initialize Vector variables of the Sending Result View Information and Storage Result qty<BR>
 *               -Set false for the result output flag by each plan unique key.<BR>
 *               </DIR>
 *            </DIR>
 *         10.When plan unique key of the Sending Result View does not change to the next plan key,<BR>
 *            Compare the Sending Result View and the following local variable.<BR>
 *            <DIR>
 *            Expiry Date, Case/Piece division, Location No.<BR>
 *            <BR>
 *            If either one of them is not equal, store the Sending Result View info and update the local variable.<BR>
 *              <DIR>
 *              10-1.Store the Sending Result View Information, Storage Result qty in a Vector variable.<BR>
 *              10-2.Set the contents of each item (count) of the next data in the Sending Result View.<BR>
 *                  <DIR>
 *                  Expiry date = the expiry date residing on the next line of the Sending Result View<BR>
 *                  Case/Piece division = Case/Piece division of the next data in the Sending Result View<BR>
 *                  Location No = Location No. of the next data in the Sending Result View<BR>
 *                  Storage Result qty = 0<BR>
 *                  </DIR>
 *              </DIR>
 *            </DIR>
 *         11.Add up the Storage Result qty.<BR>
 *            <DIR>
 *            Storage Result qty =  Storage Result qty  +   Storage Result qty (the Sending Result View)<BR>
 *            </DIR>
 *         12.Refer to the status flag.<BR>
 *            <DIR>
 *            -For the data with Standby (Status flag = Standby),<BR>
 *               <DIR>
 *               Result division = Not Processed<BR>
 *               </DIR>
 *            </DIR>
 *         13.Set true for the result output flag by each plan unique key.<BR>
 *          </DIR>
 * <BR>
 *        - Close looping -<BR>
 *        </DIR>
 * <BR>
 *     14.Output Storage Result data in process of sum up in Loop, if any, to the Storage Result file (CSV).<BR>
 *        <DIR>
 *        However, if [Report Work in process]  is not designated and Result division = Not Processed ,<BR>
 *        Disable to output any result data by the plan unique key data.<BR>
 *        Execute the following processes to output data.<BR>
 *          <DIR>
 *          -Output the Storage Result data that is stored in a Vector variable to Storage Result file (CSV).<BR>
 *          (Opening no CSV file allows to open the Storage Result CSV file.)<BR>
 *          <BR>
 *          (Note)<BR>
 *             <DIR>
 *             Disable to output any data with both Case Storage Result qty and Piece Storage Result qty equal to 0 to the report-data.<BR>
 *             </DIR>
 *          -Add up the Report-data count.<BR>
 *          -Set true Report Data flag.<BR>
 *          </DIR>
 *        </DIR>
 *     15.Close the Storage Result file (CSV).<BR>
 *     16.Set the message.<BR>
 *        <DIR>
 *        If generated one or more Storage Result file file(s) (CSV)(CSV),  <BR>
 *          <DIR>
 *          set "Closed generating report-data."<BR>
 *          </DIR>
 *        in the area for maintaining messages.<BR>
 *        If generated no file,<BR>
 *          <DIR>
 *          set "No target data was found." <BR>
 *          </DIR>
 *        in the area for maintaining the messages area<BR>
 *        </DIR>
 * <BR>
 *   <Aggregate/Output Storage Result process outline> <BR>
 *      <DIR>
 *      Aggregate the Storage Result.<BR>
 *      Aggregate by expiry date + case item storage location or piece item storage location.<BR>
 *      </DIR>
 *   <Output Storage Result file (CSV) Details of processes> <BR>
 *      <DIR>
 *      1.Agregate the Storage Result Data.<BR>
 *        <DIR>
 *        For Result data that are aggregated by Expiry Date   +  Case/Piece division  +   Storage location,  <BR>
 *        Aggregate it additionaly using expiry date + case item storage location, or using piece item storage location.<BR>
 *        (Aggregate the Case Item and Piece Item in a single line by the same Expiry Date)<BR>
 *          <DIR>
 *          Aggregate using the conditions as follows:<BR>
 *            <DIR>
 *            -If Case item and Piece item with the same expiry dates are successively output, enable to aggregate case item and piece item in a single line.<BR>
 *              <BR>
 *            -Disable to aggregate any data with successive same Case/Piece division in a single line (case item, case item, etc.).<BR>
 *            -Data with Regard Case/Piece division "None" as a result of Piece item.<BR>
 *             Generate, however, 1 line of the report-data using the result data only with None. (Aggregation with Case item is not allowed)<BR>
 *            </DIR>
 *          </DIR>
 *        </DIR>
 *      2.Output every line of aggregated Storage Result data to the Storage Result file (CSV).<BR>
 * <BR>
 *   <Output Storage Result file (CSV) process outline> <BR>
 *      <DIR>
 *      Compile each item (count) of the Storage Result file (CSV) and output to the CSV file.<BR>
 *      If the output line shows Result division=Completed, update the Sending Result Information of the Completed Storage data and Report flag of Work Status<BR>
 *      to Reported using Work Date + plan unique key.<BR>
 *      1.Compile one line of items in the Storage Result file (CSV),<BR>
 *        <DIR>
 *        except Result division, Piece Item Storage Location, Case Item Storage Location, Case Storage Result qty, and Piece Storage Result qty <BR>
 *        Set the contents of the Sending result info.<BR>
 *        (Contents of compile)<BR>
 *           <DIR>
 *           Planned storage date   = Planned date<BR>
 *           Consignor code   = Consignor code<BR>
 *           Consignor name     = Consignor name<BR>
 *           JAN          = Item Code<BR>
 *           Bundle ITF    = Bundle ITF<BR>
 *           Case ITF    = Case ITF<BR>
 *           Packed qty per bundle   = Packed qty per bundle<BR>
 *           Packed qty per Case   = Packed qty per Case<BR>
 *           Item Name         = Item Name<BR>
 *           Storage qty(Total bulk qty) = Work Planned Qty(Host Planned Qty)<BR>
 *           <BR>
 *           Case Item Storage Location = Case Item Storage Location aggregated by Plan unique key + Expiry Date + Case Item Storage Location<BR>
 *           Piece Item Storage Location = Piece Item Storage Location aggregated by Plan unique key + Expiry Date + Piece Item Storage Location<BR>
 *           <BR>
 *           Expiry Date     = Expiry Date<BR>
 *           (If both Case Item Storage Result qty and Piece Item Storage Result qty are zero, set a space.<BR>
 *            This shows a data with Process division Not Processed)<BR>
 *           <BR>
 *           Case Storage Result qty = Total qty of Case Item aggrecated by Plan unique key + Expiry Date + Piece Item Storage Location.<BR>
 *           Piece Storage Result qty = Total Piece item qty aggregated by Plan unique key + Expiry Date + Case Item Storage Location<BR>
 *           <BR>
 *           Storage result date depends on the contents of the result division as below:<BR>
 *             <DIR>
 *             Result division<BR>
 *               <DIR>
 *               0: Not Processed<BR>
 *                 <DIR>
 *                 -Storage Result Date = ""<BR>
 *                 </DIR>
 *               1:Completed Storage<BR>
 *                 <DIR>
 *                 -Storage Result Date = Work Date(Work Date that was input via screen)<BR>
 *                 </DIR>
 *               </DIR>
 *             </DIR>
 *           </DIR>
 *        </DIR>
 *      2.Output one line of Storage Result file (CSV).<BR>
 *        <DIR>
 *        Output to the Storage Result data using writeLine method of DataReportCsvWriter.<BR>
 *        </DIR>
 *      3.Update the Report flag.<BR>
 *        <DIR>
 *        If Result division is Completed Storage:<BR>
 *          <DIR>
 *          3-1.Generate Handler instance for the Sending Result Info.<BR>
 *          3-2.Generate AlterKey instance for the Sending Result Info.<BR>
 *          3-3.Generate Handler instance of Work status.<BR>
 *          3-4.Generate AlterKey instance of Work status.<BR>
 *          3-5.Set the Search key to update sending result info and update data to AlterKey.<BR>
 *              <DIR>
 *              -Search conditions<BR>
 *                 <DIR>
 *                 Work Date, Plan unique key<BR>
 *                 </DIR>
 *              -Updated data<BR>
 *                 <DIR>
 *                 Report flag = Reported<BR>
 *                 </DIR>
 *              </DIR>
 *          3-6.Modify the Sending Result Info and click on Modify button to update it.<BR>
 *          3-7.Set the search key to update Work status and a data to be updated for AlterKey.<BR>
 *              <DIR>
 *              -Search conditions<BR>
 *                 <DIR>
 *                 Work Date, Plan unique key<BR>
 *                 </DIR>
 *              -Updated data<BR>
 *                 <DIR>
 *                 Report flag = Reported<BR>
 *                 </DIR>
 *              </DIR>
 *          3-8.Modify the Work status and click on Modify button to update it.<BR>
 *              <DIR>
 *              Use Work status handler "modify" of System package is used to update.<BR>
 *              </DIR>
 *          </DIR>
 *        </DIR>
 *      </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>T.Nakai</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:11 $
 * @author  $Author: suresh $
 */
public class StorageReportDataCreator extends AbstractExternalReportDataCreator
{
	// Class fields --------------------------------------------------

	/**
	 * Result division  (Not Processed)
	 */
	static final String UN_PROCESSING = "0";

	/**
	 * Result division  (Completed All Storage)
	 */
	static final String STORAGE_COMPLETE = "1";

	/**
	 * Result division  (Partially Completed)
	 */
	static final String STORAGE_COMPLETE_IN_PART = "2";

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private final String pName = "StorageReportData";

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
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:23:11 $");
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * Receive Connection object and Locale object as a parameter and generate storage report-data.<BR>
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

		// Declare the initial value of Report flag as False.
		boolean reportFlag = false;

		// Generate a class instance to search VIEW.
		StorageHostSendViewFinder vfinder = new StorageHostSendViewFinder(conn);
		StorageHostSendViewSearchKey vkey = new StorageHostSendViewSearchKey();

		//---- Generate Sub-inquiry statement with FROM phrase to extract a Storage Result data from sending Result Information. ----

		// Set the column to be obtained.
		// Plan unique key
		vkey.setPlanUkeyCollect("");

		// Set the search conditions.

		// For data with Work Status Completed and Standby, which are divided into caused by pending within a single Aggregation key, regard such data as [ Work in process], and
		// regard the data only with Work Status Standby and with the same aggregation key as [Standby]. Therefore,
		// search targets are data with:
		//   Work division = Storage and
		//   ( Status flag = Completed and Work Date = Designated Work Date via screen ) or
		//   ( ( Status flag = Standby or Started or Processing ) and Work Date =  " " and Planned Date <= Designated Work Date via screen )
		vkey.setJobType(SystemDefine.JOB_TYPE_STORAGE, "=", "", "", "and");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION, "=", "((", "", "and");
		vkey.setWorkDate(sparam.getWorkDate(), "=", "", ")", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART, "=", "((", "", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_START, "=", "", "", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING, "=", "", ")", "and");
		vkey.setWorkDate("", "=", "", "", "and");
		vkey.setPlanDate(sparam.getWorkDate(), "<=", "", "))", "or");

		// Set the Sequence of group.
		vkey.setPlanUkeyGroup(1);

		// Execute the method to generate sub-inquiry statement.
		String subSql = vfinder.createFindSql(vkey);

		vkey.KeyClear();

		// Set the table union condition.
		// Set the value connected with the plan unique key.
		vkey.setPlanUkeyJoin();

		// Set the search Sequence for VIEW.
		// Sort the data in the following order to setup the mixed data with Case and Piece into one record.
		// Plan unique key + Location No. order + Expiry date + Case/Piece division + Added date + Work result qty
		vkey.setPlanUkeyOrder(1, true);
		vkey.setLocationNoOrder(2,true);
		vkey.setUseByDateOrder(3, true);
		vkey.setCasePieceFlagOrder(4, true);
		vkey.setRegistDateOrder(5, true);
		vkey.setResultQtyOrder(6, true);

		// Search for the Result data inquiry.
		if( vfinder.search(vkey, subSql) <= 0 )
		{
			// No target data was found.
			setMessage("6003018");
			return false;
		}

		// Declare a local variable for compiling the report-data and initialize it.
		// Aggregation key (Plan unique key)
		// (Plan unique key)
		String planUkey = "";
		//  (Expiry Date)
		String useByDate = "";
		// Case/Piece division
		String casePieceFlag = "";
		// Location
		String locationNo = "";
		// Storage Result qty
		int resultQty = 0;
		// Storage Shortage Qty
		int shortageCnt = 0;

		// Data with status flag "Standby" exists (True if data with status Standby exists in "the same plan unique key" )
		boolean unStarting = false;
		// There is a data with status flag Completed (For data with Completed in "the same plan unique key", make it true).
		boolean completion = false;

		// Temporary storage area for Sending Result View info, result qty, and shortage qty to generate a report-data in the unit that includes expiry date as an aggregation key.
		//
		Vector hostSendViewVec = new Vector();
		Vector resultQtyVec = new Vector();
		Vector shortageCntVec = new Vector();

		// Generate CSV output class instance.
		DataReportCsvWriter csvw = null;
		// Last update date/time (Use this to update the work report flag).
		Date lastUpdate = null;

		HostSendView[] hostSendView = null;
		HostSendView hostSendViewSv = null;
		try
		{
			while (vfinder.isNext())
			{
				// Output every 100 search results.
				hostSendView = (HostSendView[]) vfinder.getEntities(100);

				// Confirm it to be the leading data.
				if(StringUtil.isBlank(planUkey))
				{
					// Set the value of aggregation key for the local variable, in the case of the leading data,
					// Aggregation key (Plan unique key)
					// Plan unique key
					planUkey = hostSendView[0].getPlanUkey();
					// Expiry Date
					useByDate = hostSendView[0].getUseByDate();
					// Case/Piece division
					casePieceFlag = hostSendView[0].getCasePieceFlag();
					// Location
					locationNo = hostSendView[0].getLocationNo();
					// Storage Result qty
					resultQty = 0;
					// Sum Work Shortage Qty
					shortageCnt = 0;
					// Last update date/time
					lastUpdate = hostSendView[0].getLastUpdateDate();

					hostSendViewSv = hostSendView[0];
				}

				for( int count = 0 ; count < hostSendView.length ; count++ )
				{
					// Check for change in the Plan unique key.
					if( !(hostSendView[count].getPlanUkey().equals(planUkey)) )
					{
						// Store the Sending Result View info, storage result qty, and shortage qty in Vector.
						resultVectorSet(hostSendViewSv, resultQty, shortageCnt
														, hostSendViewVec, resultQtyVec, shortageCntVec);
						// Obtain the Result division.
						String resultKind = getResultKind(conn, planUkey, unStarting, completion);
						// Determine to report or not.
						if( reportDetermin(startParam, resultKind) )
						{
							// Output to the Storage Result Data (CSV).
							// Update the "Report" flag of the Sending Result Info to "Reported".
							if(!(csvw instanceof DataReportCsvWriter))
							{
								csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_STRAGESUPPOR);
							}
							int i = StorageCsvWrite(conn, csvw, sparam.getWorkDate(), hostSendViewVec
																, resultQtyVec, shortageCntVec, resultKind, lastUpdate);
							if( i > 0 )
							{
								// Add up the Report-data count.
								setReportCount(getReportCount() + i);
								// Set the Report-data Available.
								reportFlag = true;
							}
						}
						// Shift the Plan unique key to the Next.
						planUkey = hostSendView[count].getPlanUkey();

						// As the plan unique key changed, update the local variable of the local variables of expiry date, Case/Piece division, location, Storage Result qty.
						//
						useByDate = hostSendView[count].getUseByDate();
						casePieceFlag = hostSendView[count].getCasePieceFlag();
						locationNo = hostSendView[count].getLocationNo();
						resultQty = 0;
						shortageCnt = 0;
						lastUpdate = hostSendView[count].getLastUpdateDate();

						// Initialize the temporary memory area of status flag.
						unStarting = false;
						completion = false;

						// Initialize Sending temporary memory area of Result View info, result qty, shortage qty
						hostSendViewVec.clear();
						resultQtyVec.clear();
						shortageCntVec.clear();
					}
					else
					{
						// Check whether Expiry Date, Case/Piece division, or Location changed.
						if( !(hostSendView[count].getUseByDate().equals(useByDate)) ||
							!(hostSendView[count].getCasePieceFlag().equals(casePieceFlag)) ||
							!(hostSendView[count].getLocationNo().equals(locationNo)) )
						{
							// Store expiry date, Case/Piece division, storage location, Storage Result qty, and shortage qty in Vector.
							resultVectorSet(hostSendViewSv, resultQty, shortageCnt
															, hostSendViewVec, resultQtyVec, shortageCntVec);
							// Set the New expiry date, Case/Piece division, and location and clear the result qty.
							useByDate = hostSendView[count].getUseByDate();
							casePieceFlag = hostSendView[count].getCasePieceFlag();
							locationNo = hostSendView[count].getLocationNo();
							resultQty = 0;
							shortageCnt = 0;
						}
					}

					// Sum up the data only with status Completed of Result qty and Shortage qty that were designated in the Work Date via the screen.
					if( ( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) ) &&
						( hostSendView[count].getWorkDate().equals(sparam.getWorkDate())) )
					{
						// Sum up the storage result qty.
						resultQty = resultQty + hostSendView[count].getResultQty();
						// Add up the Storage shortage qty.
						shortageCnt = shortageCnt + hostSendView[count].getShortageCnt();
					}

					// Status flag "Standby"
					if( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART) )
					{
						// Make it true to store the fact that there are data with status flag "Standby ".
						unStarting = true;
					}
					// With Status flag Completed
					if( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) )
					{
						// Make it true to store the fact that there were data with status flag Complete
						completion = true;
					}

					if(  lastUpdate.compareTo(hostSendView[count].getLastUpdateDate()) < 0 )
					{
						// Obtain the last update date/time of the aggregation key.
						lastUpdate = hostSendView[count].getLastUpdateDate();
					}

					hostSendViewSv = hostSendView[count];
				}
			}

			// Execute the Process for the last aggregation data.
			// Store the Sending Result View info, storage result qty, and shortage qty in Vector.
			resultVectorSet(hostSendViewSv, resultQty, shortageCnt
											, hostSendViewVec, resultQtyVec, shortageCntVec);
			// Obtain the Result division.
			String resultKind = getResultKind(conn, planUkey, unStarting, completion);
			// Determine to report or not.
			if( reportDetermin(startParam, resultKind) )
			{
				// Output to the Storage Result Data (CSV).
				// Update the "Report" flag of the Sending Result Info to "Reported".
				if(!(csvw instanceof DataReportCsvWriter))
				{
					csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_STRAGESUPPOR);
				}
				int i = StorageCsvWrite(conn, csvw, sparam.getWorkDate(), hostSendViewVec
													, resultQtyVec, shortageCntVec, resultKind, lastUpdate);
				if( i > 0 )
				{
					// Add up the Report-data count.
					setReportCount(getReportCount() + i);
					// Set the Report-data Available.
					reportFlag = true;
				}
			}

			if( getReportCount() == 0 )
			{
				// No target data was found.
				setMessage("6003018");
			}
			else
			{
				// Closed generating report-data.
				setMessage("6001009");
			}

			return reportFlag;
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
				vfinder.close();
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * Set the Result data inquiry for Vector.<BR>
	 *
	 * @param hostSendView Sending Result View Information
	 * @param resultQty Storage Result qty
	 * @param shortageCnt Storage Shortage Qty
	 * @param hostSendViewVec the Sending Result View Information (Plan unique key + Expiry Date + Case/Piece division + storage location qty )
	 * @param resultQtyVec Storage Result qty (Plan unique key + Expiry Date + Case/Piece division + storage location qty )
	 * @param shortageCntVec Shortage Qty (Plan unique key + Expiry Date + Case/Piece division + storage location qty )
	 */
	private void resultVectorSet(HostSendView hostSendView, int resultQty, int shortageCnt
													, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec)
	{
		// Sending Result Information
		hostSendViewVec.addElement(hostSendView);
		// Storage Result qty
		resultQtyVec.addElement(new Integer(resultQty));
		//Shortage Qty
		shortageCntVec.addElement(new Integer(shortageCnt));
	}

	/**
	 * Determine the Result division.
	 * @param conn Database connection object
	 * @param planUkey unique key
	 * @param unStarting Presence of Standby data in the Plan unique key
	 * @param completion Presence of completed data in the Plan unique key
	 * @return completion flag
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected String getResultKind(Connection conn, String planUkey, boolean unStarting, boolean completion)
																						throws ReadWriteException
	{
		// Regard any storage data only with Completed as Completed Storage.
		if( !unStarting && completion )
		{
			// Result division  (Completed All Storage)
			return STORAGE_COMPLETE;
		}

		// Data only With Standby may include some data with Standby or Reported.
		HostSendSearchKey skey = new HostSendSearchKey();
		HostSendHandler handle = new HostSendHandler(conn);
		skey.setPlanUkey(planUkey);
		skey.setReportFlag(HostSend.REPORT_FLAG_SENT);

		// Regard the data only with Standby as Not Processed.
		if( unStarting && !completion && handle.count(skey) == 0 )
		{
			return UN_PROCESSING;
		}

		// (Partially Completed)
		return STORAGE_COMPLETE_IN_PART;
	}

	/**
	 * Determine the acceptability to report the aggregation key data.<BR>
	 *
	 * @param startParam This Class inherits the <CODE>Parameter</CODE> class.
	 * @param resultKind Result division
	 * @return Return the value to repor the data or not.
	 */
	private boolean reportDetermin(Parameter startParam, String resultKind)
	{
		SystemParameter sparam = (SystemParameter) startParam;

		// Check for omitting to place a check in the "Report Standby Work" option in the Result division = Not Processed.
		if( (resultKind.equals(UN_PROCESSING)) &&
			(!sparam.getSelectReportStorageData_Unworking()) )
		{
			// Disable to report if placing no check in "Standby work to be reported" option in the Not Processed options.
			return false;
		}

		// Check for omitting to place a check in the" Report Work option in process" in the Result division = Partially Completed
		if( (resultKind.equals(STORAGE_COMPLETE_IN_PART)) &&
			(!sparam.getSelectReportStorageData_InProgress()) )
		{
			// No check placed on "Report Processing Work" for data with Partially Completed disables to report.
			return false;
		}

		return true;
	}

	/**
	 * Aggregate the Storage Result and output it.<BR>
	 *
	 * @param conn Database connection object
	 * @param csvw CSV Writer object
	 * @param workDate Work Date
	 * @param hostSendViewVec the Sending Result View Information (Plan unique key + Expiry Date + Case/Piece division + storage location qty )
	 * @param resultQtyVec Storage Result qty (for one aggregation key)
	 * @param shortageCntVec Shortage Qty (for one aggregation key)
	 * @param resultKind Result division
	 * @param lastUpdate Last update date/time
	 * @return Return the count of data that have been output to the Report Data.
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private int StorageCsvWrite(Connection conn, DataReportCsvWriter csvw, String workDate
												, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec
												, String resultKind, Date lastUpdate)
													throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		// Exclude the data other than report targets (result qty=0, shortage qty=0) from the stored result data. If "Report Data with Not Worked" is designated, however,
		// regard data with Not Worked (result qty=0, shortage qty=0) as a data to be reported.
		// Sending Result View Information
		HostSendView[] hostSendViewTempAry = new HostSendView[hostSendViewVec.size()];
		hostSendViewVec.copyInto(hostSendViewTempAry);
		// Result qty
		Integer[] resultQtyTempAry = new Integer[resultQtyVec.size()];
		resultQtyVec.copyInto(resultQtyTempAry);
		// Shortage Qty
		Integer[] shortageCntTempAry = new Integer[shortageCntVec.size()];
		shortageCntVec.copyInto(shortageCntTempAry);

		// Temporarily storage area for Sending Result View info, result qty, and shortage qty to generate report-data by each aggregation key.
		Vector hostSendViewTempVec = new Vector();
		Vector resultQtyTempVec = new Vector();
		Vector shortageCntTempVec = new Vector();

		for( int i = 0 ; i < hostSendViewTempAry.length ; i++ )
		{
			if( (resultQtyTempAry[i].intValue() == 0) && (shortageCntTempAry[i].intValue() == 0) &&
				(!resultKind.equals(UN_PROCESSING)) )
			{
				// Disable to output if both result qty and shortage qty are zero, any status other than Not Processed.
				continue;
			}
			// Sending Result Information
			hostSendViewTempVec.addElement(hostSendViewTempAry[i]);
			// Storage Result qty
			resultQtyTempVec.addElement(resultQtyTempAry[i]);
			// Shortage Qty
			shortageCntTempVec.addElement(shortageCntTempAry[i]);
		}

		if( hostSendViewTempVec.size() <= 0 )
		{
			// No result report-data
			return 0;
		}


		// Copy the result data to be reported to the array.
		// Sending Result View Information
		HostSendView[] hostSendViewAry = new HostSendView[hostSendViewTempVec.size()];
		hostSendViewTempVec.copyInto(hostSendViewAry);
		// Result qty
		Integer[] resultQtyAry = new Integer[resultQtyTempVec.size()];
		resultQtyTempVec.copyInto(resultQtyAry);
		// Shortage Qty
		Integer[] shortageCntAry = new Integer[shortageCntTempVec.size()];
		shortageCntTempVec.copyInto(shortageCntAry);

		// Aggregate the Result data, which were aggregated by Expiry Date   +  Case/Piece division  +   storage location,
		// additionally using expiry date + case item storage location, or using piece item storage location.

		// Refer to the Case/Piece division and generate an aggregated information of Case Item Storage Location and  Piece Item Storage Location.
		String useByDate = "";


		// Make the CasePieceMix flag TRUE if Case data and Piece data are mixed in the entered hostSendViewAry array.
   		boolean CasePieceMix = false;

  		boolean existCase = false;
  		boolean existPiece = false;
		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			if( hostSendViewAry[i].getUseByDate().equals(useByDate) )
			{
				// With Case/Piece division  None
				if( hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING) )
				{
					CasePieceMix = false;
					break;
				}
				else
				{
					if (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
					{
						existCase = true;
						if (existPiece)
						{
							CasePieceMix = true;
							break;
						}
					}
					else if (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
					{
						existPiece = true;
						if (existCase)
						{
							CasePieceMix = true;
							break;
						}
					}
				}
			}
			else
			{
				// With Case/Piece division  None
				if( hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING) )
				{
					CasePieceMix = false;
					break;
				}
				else
				{
					if (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
					{
						existCase = true;
						if (existPiece)
						{
							CasePieceMix = true;
							break;
						}
					}
					else if (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
					{
						existPiece = true;
						if (existCase)
						{
							CasePieceMix = true;
							break;
						}
					}
				}
			}
			useByDate = hostSendViewAry[i].getUseByDate();
		}

		String caseLocation = "";
		String pieceLocation = "";
		int caseQty = 0;
		int caseShortageCnt = 0;
		int pieceQty = 0;
		int pieceShortageCnt = 0;
		int reportCount = 0;
		boolean printOut = false;
		// Based on the aggregated information of Case Item Storage Location and  Piece Item Storage Location generated by referring to Case/Piece division,
		// output the result.
		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			if (!CasePieceMix)
			{
				// For data with Case/Piece division None, or Piece, set the result for the piece info.
				if( (hostSendViewAry[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING)) ||
					(hostSendViewAry[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE)) )
				{
					// Disable to report any Location of data with Not Completed.
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						pieceLocation = "";
					}
					else
					{
						pieceLocation = hostSendViewAry[i].getLocationNo();
					}
					int qty = resultQtyAry[i].intValue();
					pieceQty = qty;
					pieceShortageCnt = shortageCntAry[i].intValue();
					caseLocation = "";
					caseQty = 0;
					caseShortageCnt = 0;
				}
				else
				{
					// Disable to report any Location of data with Not Completed.
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						caseLocation = "";
					}
					else
					{
						caseLocation = hostSendViewAry[i].getLocationNo();
					}
					caseQty = resultQtyAry[i].intValue();
					caseShortageCnt = shortageCntAry[i].intValue();
					pieceLocation = "";
					pieceQty = 0;
					pieceShortageCnt = 0;
				}
				printOut = true;
			}
			// Case data and Piece data mixed
			else
			{
				// Refer to the Case/Piece division and obtain the Case Item Storage Location, and Piece Item Storage Location.
				// Case/Piece division(Case)
				if( hostSendViewAry[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE) )
				{
					// Data with status flag "Standby" makes the location blank (to report the work that has not been yet done).
					if(hostSendViewAry[i].getStatusFlag().equals((HostSendView.STATUS_FLAG_UNSTART)))
					{
						caseLocation = "";
					}
					else
					{
						caseLocation = hostSendViewAry[i].getLocationNo();
					}
					caseQty = resultQtyAry[i].intValue();
					caseShortageCnt = shortageCntAry[i].intValue();
				}
				// Case/Piece division(Piece)
				else
				{

			   		// Data with status flag "Standby" makes the location blank (to report the work that has not been yet done).
					if(hostSendViewAry[i].getStatusFlag().equals((HostSendView.STATUS_FLAG_UNSTART)))
					{
						pieceLocation = "";
					}
					else
					{
						pieceLocation = hostSendViewAry[i].getLocationNo();
					}
					pieceQty = resultQtyAry[i].intValue();
					pieceShortageCnt = shortageCntAry[i].intValue();
				}

				// Make the printOut flag TRUE to output report-data.
				// Not Processed
				if(resultKind.equals(UN_PROCESSING))
				{
					// For data with status Not Processed, there are always two data. Accordingly, output the second data to the CSV to integrate into one record. there are always two data if.
					if(i == 1)
					{
						printOut = true;
					}
				}
				// Completed Storage
				else if(resultKind.equals(STORAGE_COMPLETE))
				{
					if(i == hostSendViewAry.length-1)
					{
						printOut = true;
					}
					else
					{
						// Refer to the next data to integrate into one record.
						// No change in expiry date of the next data disables to output it to CSV.
						if(hostSendViewAry[i].getUseByDate().equals(hostSendViewAry[i+1].getUseByDate()))
						{
							printOut = false;
						}
						else
						{
							printOut = true;
						}
					}
				}
				// Partially Completed
				else if(resultKind.equals(STORAGE_COMPLETE_IN_PART))
				{
					// Disable to output any data with status flag "Standby" is not output to CSV.
					if(hostSendViewAry[i].getStatusFlag().equals((HostSendView.STATUS_FLAG_UNSTART)))
					{
						printOut = false;
					}
					else
					{
						printOut = true;
					}
				}
			}

			// Output Report-data
			if( printOut )
			{
				CsvWriteLine(csvw, workDate, hostSendViewAry[i], caseLocation, caseQty, caseShortageCnt
													 , pieceLocation, pieceQty, pieceShortageCnt, resultKind);
				reportCount++;

				caseLocation = "";
				pieceLocation = "";
				caseQty = 0;
				pieceQty = 0;
				caseShortageCnt = 0;
				pieceShortageCnt = 0;
				printOut = false;
			}
		}

		try
		{

			// For data with Result division "Completed Storage" or "Partially Completed", update the Report flag and the last update date/time.
			if( resultKind.equals(STORAGE_COMPLETE) || resultKind.equals(STORAGE_COMPLETE_IN_PART) )
			{
				// Update the Report flag of the Sending Result Info.
				HostSendHandler hhandle = new HostSendHandler(conn);
				HostSendSearchKey hsKey = new HostSendSearchKey();
				hsKey.setPlanUkey(hostSendViewAry[0].getPlanUkey());
				hsKey.setWorkDate(workDate);
				hsKey.setLastUpdateDate(lastUpdate, "<=");
				if (hhandle.count(hsKey) > 0)
				{
					// 	Sending Result Information Updating Key Instance
					HostSendAlterKey hakey = new HostSendAlterKey();

					// Update the Report flag of the Sending Result Info.
					// Set the aggregation key for Update key.
					hakey.setPlanUkey(hostSendViewAry[0].getPlanUkey());
					hakey.setWorkDate(workDate);
					hakey.setLastUpdateDate(lastUpdate, "<=");

					// Set the update item (count).
					// Update the Report flag of the Sending Result Info to "Reported".
					hakey.updateReportFlag(SystemDefine.REPORT_FLAG_SENT);
					hakey.updateLastUpdatePname(pName);

					// Update the Sending Result Info.
					hhandle.modify(hakey);
				}

				// Update the Work Status Report flag.
				StorageWorkingInformationHandler swhandle = new StorageWorkingInformationHandler(conn);
				swhandle.updateReportFlag( hostSendViewAry[0].getPlanUkey(), workDate, pName ) ;
			}
		}
		catch(NotFoundException e)
		{
			throw (new ReadWriteException("6006002" + wDelim + "DNHOSTSEND"));
		}
		catch(InvalidDefineException e)
		{
			throw (new ReadWriteException("6006002" + wDelim + "DNHOSTSEND"));
		}

		return(reportCount);
	}

	/**
	 * Output one line of Storage Result.<BR>
	 * After output, update the Sending Result Info, "Report" flag of Work status, and the last update date/time.
	 *
	 * @param csvw CSV Writer object
	 * @param workDate Work Date
	 * @param hostSendView Sending Result View Information
	 * @param caseLocation Case Item Storage Location
	 * @param caseQty Case Storage Result qty
	 * @param caseShortageCnt Case Shortage Qty
	 * @param pieceLocation Piece Item Storage Location
	 * @param pieceQty Piece Storage Result qty
	 * @param pieceShortageCnt Piece Shortage Qty
	 * @param resultKind Result division
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void CsvWriteLine(DataReportCsvWriter csvw, String workDate, HostSendView hostSendView
											 , String caseLocation, int caseQty, int caseShortageCnt
											 , String pieceLocation, int pieceQty, int pieceShortageCnt, String resultKind)
															throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		// Set the Storage Result Data for the item (count).
		// Planned storage date
		csvw.setValue("PLAN_DATE", hostSendView.getPlanDate());
		// Consignor code
		csvw.setValue("CONSIGNOR_CODE", hostSendView.getConsignorCode());
		// Consignor name
		csvw.setValue("CONSIGNOR_NAME", hostSendView.getConsignorName());
		// JAN
		csvw.setValue("ITEM_CODE", hostSendView.getItemCode());
		// Bundle ITF
		csvw.setValue("BUNDLE_ITF", hostSendView.getBundleItf());
		// Case ITF
		csvw.setValue("ITF", hostSendView.getItf());
		// Packed qty per bundle
		csvw.setValue("BUNDLE_ENTERING_QTY", new Integer(hostSendView.getBundleEnteringQty()));
		// Packed qty per Case
		csvw.setValue("ENTERING_QTY", new Integer(hostSendView.getEnteringQty()));
		// Item Name
		csvw.setValue("ITEM_NAME1", hostSendView.getItemName1());
		// Storage qty(Total Bulk Qty)
		csvw.setValue("PLAN_QTY", new Integer(hostSendView.getHostPlanQty()));

		// Piece Item Storage Location
		csvw.setValue("JOB_LOCATION_PIECE", pieceLocation);
		// Case Item Storage Location
		csvw.setValue("JOB_LOCATION_CASE", caseLocation);

		// Piece Storage Result qty
		csvw.setValue("RESULT_QTY_PIECE", new Integer(pieceQty));
		// Case Storage Result qty
		csvw.setValue("RESULT_QTY_CASE", new Integer(caseQty));

		// With Result division Not Processed
		if( resultKind.equals(UN_PROCESSING) )
		{
			// Set a space for Storage Result Date.
			csvw.setValue("COMPLETE_DATE", "");
		}
		else
		{
			// Storage Result Date
			csvw.setValue("COMPLETE_DATE", workDate);
		}

		// Result division
		csvw.setValue("RESULT_FLAG", resultKind);

		// Expiry Date
		if( (pieceQty != 0) || (caseQty != 0) || (pieceShortageCnt != 0) || (caseShortageCnt != 0) )
		{
			csvw.setValue("USE_BY_DATE", hostSendView.getUseByDate());
		}
		else
		{
			csvw.setValue("USE_BY_DATE", "");
		}

		// Output Storage Result Data to the CSV.
		csvw.writeLine();

		return;
	}
}
//end of class
package jp.co.daifuku.wms.retrieval.schedule;

// $Id: RetrievalReportDataCreator.java,v 1.4 2007/02/07 04:20:03 suresh Exp $

//#CM725376
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
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalHostSendViewFinder;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalHostSendViewSearchKey;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalWorkingInformationHandler;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

//#CM725377
/**
 * Designer : T.Nakai <BR>
 * Maker : T.Nakai <BR>
 * <BR>
 * Allow the <CODE>RetrievalReportDataCreator</CODE> class to execute the process for reporting Picking Result data.<BR>
 * Inherit <CODE>AbstractExternalReportDataCreator</CODE> abstract class and implement the required processes. <BR>
 * Allow the methods contained in this class to receive the connection object and execute process for updating the database, but
 * disable to commit nor roll-back the transaction. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for reporting data(<CODE>report</CODE> method) <BR>
 * <BR>
 *   <DIR>
 *   Receive Connection object and Parameter object as a parameter, and 
 *   generate a Picking result data file (CSV file) from the VIEW in which the Result Sent Information and the Work Status Info in the database are combined. <BR>
 *   <BR>
 *   (Note) <BR>
 *   Generate Not Processed data or Picking Completed data depending on the status. <BR>
 *     <DIR>
 *     Not Processed data refers to the following status: <BR>
 *       <DIR>
 *       a. Data with status Standby. <BR>
 *       b. Data with Standby and Completed mixed in the data with the same plan unique key. <BR>
 *           <DIR>
 *           In this current version of the specifications, target the data of which Case/Piece division was regarded as Mixed when loading it. <BR>
 *           For a work data with status Completed Case Work only and Standby Piece Work, for example, regard it as Not-Processed data. <BR>
 *           </DIR>
 *       </DIR>
 *     </DIR>
 * <BR>
 *     <DIR>
 *     Condition to generate VIEW <BR>
 *       <DIR>
 *       - Work Status Info (dnworkinfo) <BR>
 *          <DIR>
 *          Status flag <> Deleted, and Status flag <> Completed <BR>
 *          Flag to report standby work = Not Sent <BR>
 *          </DIR>
 *       - Result Sent Information (dnhostsend)<BR>
 *          <DIR>
 *          Work Report flag = Not Sent<BR>
 *          </DIR>
 *       Generate VIEW using items of Work Status Info and Result Sent Information other than follows. <BR>
 *         <DIR>
 *         Lot No. (lot_no, result_lot_no), Work Result Location, Work Report flag, Worker Code,
 *         Worker Name, Terminal No., RFT No., Plan information, Added Date/Time, Name of Add Process, and Name of the Last update process <BR>
 *         (Note) <BR>
 *           <DIR>
 *           a. No Work Date in Work Status Info causes to set Null for Work Date of View Information obtained from Work Status Info. <BR>
 *           b.Expiry Date<BR>
 *             <DIR>
 *             Obtain the Work Status Info from the expiry date (use_by_date) and obtain the Result Sent Information from expiry date (result_use_by_date). <BR>
 *             </DIR>
 *           </DIR>
 *         </DIR>
 *       </DIR>
 *     </DIR>
 * <BR>
 *   Return false when error occurs in the method. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error.<BR>
 * <BR>
 *   <Parameter> Mandatory Input<BR>
 *     <DIR>
 *     Connection object  <BR>
 *     Parameter object  <BR>
 *     </DIR>
 * <BR>
 * [Input data] <BR>
 *    <DIR>
 *    *Mandatory input field item <BR>
 * <BR>
 *    Work date                        : WorkDate *<BR>
 *    Report the Picking Result_Not Worked. : SelectReportRetrievalData <BR> 
 *    </DIR>
 * <BR>
 *   <Details of processes>  <BR>
 *      1. Initialize the data count. <BR>
 *        <DIR>
 *        Report-data count=0 <BR>
 *        </DIR>
 *      2. Declare the Report Data flag. <BR>
 *        <DIR>
 *        Declare false. Regard true if one or more entry (added) data exists. <BR>
 *        </DIR>
 *      3. Generate a class instance for searching through the VIEW. <BR>
 *        <DIR>
 *        - Generate Handler instance for VIEW. <BR>
 *        - Generate SearchKey instance for VIEW. <BR>
 *        </DIR>
 *      4. Set the Search key to search VIEW for SearchKey. <BR>
 *          <DIR>
 *          Search for the Plan unique key (Aggregation key) using the following search conditions, and obtain all the Work Status Info that have the Plan unique key and
 *          the Result Sent Information. <BR>
 *            <DIR>
 *            - Search conditions<BR>
 *               <DIR>
 *               work type = Picking and<BR>
 *               ( Status flag = Completed, and Work date = Work date specified via screen ) or<BR>
 *               ( ( Status flag = Standby OR Started OR Working )  and<BR>
 *                 Work date =  and Planned date <= Work date specified via screen )<BR>
 *               </DIR>
 *            - Sequence <BR>
 *               <DIR>
 *               Plan unique key + Added Date/Time + Work Result qty + Expiry Date + Case/Piece division + Location No.+ Order No. <BR>
 *               </DIR>
 *            </DIR>
 *          </DIR>
 *      5. Search through the VIEW using VIEW Handler instance find and obtain the result in the Result Sent View Entity. <BR>
 *        <DIR>
 *        If the count of the obtained Result Sent View is 0, execute the following process. <BR>
 *          <DIR>
 *          Message = No target data was found. <BR>
 *          Close with no data reported (false). <BR>
 *          </DIR>
 *        </DIR>
 *      6. Generate local variable. <BR>
 *        <DIR>
 *        Result Sent Information View <BR>
 *        Aggregation key (Plan unique key), Expiry Date, Case/Piece division, Location No., and Order No. <BR>
 *        Picking Result Qty, Picking Shortage Qty <BR>
 *        <BR>
 *        The count of case picking data and the count of case picking completed data. <BR>
 *        Count of Piece Picking Data, Count of Piece Picking Completed Data<BR>
 *        "Not Processed" flag <BR>
 *        <BR>
 *        Last update date/time (Use this to update the work report flag). <BR>
 *        Generate the report data in the unit of Aggregation key. Therefore, allow this area to store Result Sent View Information and Result qty temporarily. <BR>
 *        </DIR>
 *      7. Generate Class Instance. <BR>
 *        <DIR>
 *        - Generate Generation of DataReportCsvWriter instance. <BR>
 *           <DIR>
 *           Allow this class to generate a data report CSV. <BR>
 *           </DIR>
 *        </DIR>
 *      8. Loop to the end of the Result Sent View Entity that was obtained from VIEW. <BR>
 *        <DIR>
 * <BR>
 *        - Start looping  -<BR>
 * <BR>
 *          <DIR>
 *          9.Obtain every 100 Result Sent View information. <BR>
 *         10. Set the contents of each item (count) of the leading data in the Result Sent View if no value is set for the local variable. <BR>
 *             <DIR>
 *             Result Sent View = Leading data of the Result Sent View <BR>
 *             Plan unique key = Plan unique key of the leading data of the Result Sent View <BR>
 *             Expiry Date = Expiry Date of the leading data of the Result Sent View <BR>
 *             Case/Piece division = Case/Piece division of the leading data of the Result Sent View <BR>
 *             Location No.  = Location No. of the leading data of the Result Sent View <BR>
 *             Order No. = Order No. of the leading data of the Result Sent View <BR>
 *             Last update date/time = Last update date/time of the leading data of the Result Sent View <BR>
 *             Picking Result qty = 0<BR>
 *             Sum-up the Work Shortage Qty.  = 0<BR>
 *             Case Picking Data count  = 0 <BR>
 *             Case Picking Completed Data count  = 0 <BR>
 *             Count of Piece Picking Data = 0<BR>
 *             Piece Picking Completed Data count  = 0<BR>
 *             "Not Processed" flag  = true<BR>
 *             </DIR>
 * <BR>
 *             - Start looping  -<BR>
 * <BR>
 *               <DIR>
 *               11.Compare the values between Plan unique key of the Result Sent View and the Plan unique key of the local variable. <BR>
 *                  <DIR>
 *                  - Execute the following processes, when plan unique key of the Result Sent View changed to the next plan unique key. <BR>
 *                     <DIR>
 *                     a. To output the Picking Result based on the determination of conditions to output it: <BR>
 *                       <DIR>
 *                       - Determine the Result division. <BR>
 *                       - Output one group of the aggregated picking result data (one or more data) stored in Vector variable to Picking Result file (CSV). 
 *                        <BR>
 *                       (Opening no CSV file allows to open the Picking Result CSV file.) <BR>
 *                       - Add up the Report-data count. <BR>
 *                       - Set true for the Report Data flag. <BR>
 *                       </DIR>
 *                     b. Update the local variable.<BR>
 *                       <DIR>
 *                       - Set the contents of each field item of the next data in the Result Sent View. <BR>
 *                          <DIR>
 *                          Result Sent Information View = Next Result Sent View <BR>
 *                          Plan unique key = Next Plan unique key shown in the Result Sent View <BR>
 *                          Expiry date = the expiry date residing on the next line of the Result Sent View <BR>
 *                          Case/Piece division = Case/Piece division of the next data in the Result Sent View <BR>
 *                          Location No.  = Location No. of the next data in the Result Sent View <BR>
 *                          Order No.  = The following Order No. of Result Sent View <BR>
 *                          Picking Result qty = 0<BR>
 *                          Sum-up the Work Shortage Qty.  = 0<BR>
 *                          Last update date/time = Last update date/time of the leading data of the Result Sent View <BR>
 *                          Case Picking Data count  = 0 <BR>
 *                          Case Picking Completed Data count  = 0 <BR>
 *                          Count of Piece Picking Data = 0<BR>
 *                          Piece Picking Completed Data count  = 0<BR>
 *                          "Not Processed" flag  = true<BR>
 *                          Generate the report data in the unit of Aggregation key. Therefore, allow this area to store Result Sent View Information and Result qty temporarily. <BR>
 *                          </DIR>
 *                       </DIR>
 *                     </DIR>
 *                  - Execute the following processes, when plan unique key of the Result Sent View did not change to the next plan unique key. <BR>
 *                     <DIR>
 *                     Compare the values between Result Sent View and the following local variables. <BR>
 *                       <DIR>
 *                       Expiry Date , Case/Piece division, Location No., Order No. <BR>
 *                       <BR>
 *                       If either one of them is not equal, store the Result Sent View info and update the local variable. <BR>
 *                         <DIR>
 *                         a.Store the Result Sent View Information in the Vector variable. <BR>
 *                         b. Set the contents of each field item of the next data in the Result Sent View. <BR>
 *                           <DIR>
 *                           Result Sent Information View = Next Result Sent View <BR>
 *                           Expiry date = the expiry date residing on the next line of the Result Sent View <BR>
 *                           Case/Piece division = Case/Piece division of the next data in the Result Sent View <BR>
 *                           Location No.  = Location No. of the next data in the Result Sent View <BR>
 *                           Order No.  = The following Order No. of Result Sent View <BR>
 *                           Picking Result qty = 0<BR>
 *                           Sum-up the Work Shortage Qty.  = 0<BR>
 *                           "Not Processed" flag  = true<BR>
 *                           </DIR>
 *                         </DIR>
 *                       </DIR>
 *                     </DIR>
 *                  </DIR>
 *               12.Sum-up the Picking Result qty and Work shortage qty. <BR>
 *                  <DIR>
 *                  Picking Result qty  = Picking Result qty  + Picking Result qty (Result Sent View) <BR>
 *                  Work shortage qty = Work shortage qty + Work shortage Qty (Result Sent View) <BR>
 *                  </DIR>
 *               13. Refer to the status flag. <BR>
 *                  <DIR>
 *                  - For Completed data (Status flag = Completed): <BR>
 *                     <DIR>
 *                     a. "Not Processed" flag  = false<BR>
 *                     b.Sum-up the count of Completed Case/Piece data. <BR>
 *                       <DIR>
 *                       Refer to the Case/Piece division and determine Case or Piece for the division. <BR>
 *                         <DIR>
 *                         Case Picking Completed Data count  = Case Picking Completed Data count  + 1 <BR>
 *                         Or, <BR>
 *                         Piece Picking Completed Data count  = Piece Picking Completed Data count  + 1 <BR>
 *                         </DIR>
 *                       </DIR>
 *                     </DIR>
 *                  </DIR>
 *               14. Add Count of Case/Piece. <BR>
 *                  <DIR>
 *                  Refer to the Case/Piece division and determine Case or Piece for the division. <BR>
 *                    <DIR>
 *                    Case data Count = Case data Count + 1 <BR>
 *                    Or, <BR>
 *                    Piece data Count = Piece data Count + 1 <BR>
 *                    </DIR>
 *                  </DIR>
 *               15.Obtain the Last update date/time. <BR>
 *                  <DIR>
 *                  Obtain the aggregation key with the last update date/time of the Aggregation keys. <BR>
 *                  </DIR>
 *             </DIR>
 * <BR>
 *             - Close looping  -<BR>
 * <BR>
 *          </DIR>
 * <BR>
 *        - Close looping  -<BR>
 *        </DIR>
 * <BR>
 *     16.Process the Picking result data in progress of summing-up the Loop process. <BR>
 *        <DIR>
 *        To output the Picking Result based on the determination of conditions to output it: <BR>
 *          <DIR>
 *          Compile it in the same manner where the unique key stated in paragraph 11. changes to the Next. <BR>
 *          </DIR>
 *        </DIR>
 *     17.Close the Picking Result file (CSV). <BR>
 *     18. Set the message. <BR>
 *        <DIR>
 *        Generating one or more Picking Result file (CSV) sets a message in the area for maintaining the message:<BR>
 *          <DIR>
 *          "Closed generating report-data." <BR>
 *          </DIR>
 *        <BR>
 *        If generating no info, set a message<BR>
 *          <DIR>
 *          "No target data was found." <BR>
 *          </DIR>
 *        <BR>
 *        </DIR>
 * <BR>
 *   < Outline of processes: Aggregation and output of Picking Result>  <BR>
 *      <DIR>
 *      Aggregate the Picking Result. <BR>
 *      Aggregate the data using a set of Expiry Date + Picking Location + Order No. <BR>
 *      For Item with division Piece, therefore, aggregate using a set of Expiry Date + Piece Item Picking Location + Order No. (Piece).
 *      For Item with division Case, aggregate using a set of Expiry Date + Case Item Picking Location + Order No. (Case).  <BR>
 *      </DIR>
 *   <Details on process to output Picking Result file (CSV)> <BR>
 *      <DIR>
 *      1.Aggregate the Picking result data. <BR>
 *        <DIR>
 *        For the result data already aggregated by a set of Expiry Date + Case/Piece division + Picking Location + Order No.,  
 *        aggregate them further using a set of Expiry Date + Case Item Picking Location + Order No. (Case),  
 *        or a set of Expiry Date + Piece Item Picking Location + Order No. (Piece). <BR>
 *        (For such data with the same Expiry Date, aggregate the Case item and the Piece Item in a single line. <BR>
 *          <DIR>
 *          Aggregate using the conditions as follows: <BR>
 *            <DIR>
 *            - For data of Case Item or Piece Item data with the same Expiry date which are output successively, 
 *             enable to aggregate them in a single line. <BR>
 *            - If data with the same Case/Piece division located successively (for example, data with Case item and the next data also with Case item), disable to aggregate such data in a single line. <BR>
 *            - Regard data with Case/Piece division None as a Piece item result. <BR>
 *             Generate a single lined report data using the Result data with None designated only. (Disable to aggregate with Case item) <BR>
 *            </DIR>
 *          </DIR>
 *        </DIR>
 *      2.Output every line of the aggregated Picking result data one by one to the Picking Result file (CSV). <BR>
 *      3. Update the Report flag. <BR>
 *        <DIR>
 *        Data with Result division "Picking Completed": <BR>
 *          <DIR>
 *          3-1. Generate Handler instance for the Result Sent Information. <BR>
 *          3-2. Generate AlterKey instance for the Result Sent Information. <BR>
 *          3-3. Generate Handler instance of Work Status Info. <BR>
 *          3-4. Generate AlterKey instance of Work Status Info. <BR>
 *          3-5. Set the Search key to update Result Sent Information and update data for AlterKey. <BR>
 *              <DIR>
 *              - Search conditions<BR>
 *                 <DIR>
 *                 Work Date, Plan unique key <BR>
 *                 </DIR>
 *              - Data to be updated <BR>
 *                 <DIR>
 *                 Report flag = Reported <BR>
 *                 </DIR>
 *              </DIR>
 *          3-6. Modify the Result Sent Information and update it. <BR>
 *          3-7. Set the search key to update Work Status Info and a data to be updated for AlterKey. <BR>
 *              <DIR>
 *              - Search conditions<BR>
 *                 <DIR>
 *                 Work Date, Plan unique key <BR>
 *                 </DIR>
 *              - Data to be updated <BR>
 *                 <DIR>
 *                 Report flag = Reported <BR>
 *                 </DIR>
 *              </DIR>
 *          3-8.Modify the Work Status Info and update it. <BR>
 *              <DIR>
 *              Use Work Status Info handler modify of System package is used to update. <BR>
 *              </DIR>
 *          </DIR>
 *        </DIR>
 *      </DIR>
 * <BR>
 *   < Outline of processes: Picking Result file (CSV) output>  <BR>
 *      <DIR>
 *      Compile each field item of the Picking Result file (CSV) and output it to the CSV file. <BR>
 *      If the Result division of the output line = Completed, update the Report flag of the Result Sent Information and the Work Status Info of the completed Picking data
 *      to "Reported" using Work Date + Plan unique key. <BR>
 *      1.Compile each field item on one line of the Picking Result file (CSV). <BR>
 *        <DIR>
 *        Set the contents of the Result Sent Information, 
 *        except for Result division, Piece Item Picking Location, Case Item Picking Location, Case Picking Result qty, and Piece Picking Result qty. <BR>
 *        (Contents of compile) <BR>
 *           <DIR>
 *           Planned Picking Date   = Planned date<BR>
 *           Consignor Code   = Consignor Code<BR>
 *           Consignor Name     = Consignor Name<BR>
 *           Customer Code = Customer Code<BR>
 *           Customer Name   = Customer Name <BR>
 *           Ticket No.        = Ticket No. <BR>
 *           Line No         = Line No<BR>
 *           Item Code   = Item Code<BR>
 *           Bundle ITF    = Bundle ITF<BR>
 *           Case ITF    = Case ITF<BR>
 *           Packed qty per bundle   = Packed qty per bundle<BR>
 *           Packed Qty per Case   = Packed Qty per Case<BR>
 *           Item Name     = Item Name<BR>
 *           <BR>
 *           Picking qty (Total Bulk Qty) <BR>
 *             <DIR>
 *             Total of Planned work qty (Host Planned QTY) that is aggregated by Plan unique key + Expiry Date + Case/Piece Item Picking Location + Order No. (Case/Piece). <BR>
 *             Total Work Planned qty (Host Planned Qty) <BR>
 *             </DIR>
 *           Piece Picking Location <BR>
 *             <DIR>
 *             Picking Location that is aggregated by Plan unique key + Expiry Date + Piece Item Picking Location + Order No. (Piece). <BR>
 *             </DIR>
 *           Case Picking Location<BR>
 *             <DIR>
 *             Picking Location that is aggregated by Plan unique key + Expiry Date + Case Item Picking Location + Order No. (Case). <BR>
 *             </DIR>
 *           Piece Order No.<BR>
 *             <DIR>
 *             Order No. that is aggregated by Plan unique key + Expiry Date + Piece Item Picking Location + Order No. (Piece). <BR>
 *             </DIR>
 *           Case Order No.<BR>
 *             <DIR>
 *             Order No. that is aggregated by Plan unique key + Expiry Date + Case Item Picking Location + Order No. (Case). <BR>
 *             </DIR>
 *           Result Piece Qty <BR>
 *             <DIR>
 *             Total Work Result qty of Piece item that is aggregated by Plan unique key + Expiry Date + Case Item Picking Location + Order No. (Piece). <BR>
 *             </DIR>
 *           Result Case Qty <BR>
 *             <DIR>
 *             Total Work Result qty of Case item that is aggregated by Plan unique key+Expiry Date + Case Item Picking Location + Order No. (Case). <BR>
 *             </DIR>
 *           <BR>
 *           Use the Picking Result Date determined according to the content of the Result division: <BR>
 *             <DIR>
 *             Result division <BR>
 *               <DIR>
 *               0: Not Processed <BR>
 *                 <DIR>
 *                 - Picking Result Date  = <BR>
 *                 </DIR>
 *               1: Picking Completed <BR>
 *                 <DIR>
 *                 - Picking Result Date = Work date(Work Date that was input via screen) <BR>
 *                 </DIR>
 *               </DIR>
 *             </DIR>
 *           <BR>
 *           Expiry Date = Expiry Date <BR>
 *             <DIR>
 *             (Set a space if both values of the Result Case Qty and the Result Piece Qty are equal to 0. <BR>
 *              This refers to the case where the Process division is "Not Processed") <BR>
 *             </DIR>
 *           <BR>
 *           </DIR>
 *        </DIR>
 *      2.Output one line from the Picking Result file (CSV). <BR>
 *        <DIR>
 *        Using the writeLinemethod of DataReportCsvWriter, output to the Picking result data. <BR>
 *        </DIR>
 *      </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>T.Nakai</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.4 $, $Date: 2007/02/07 04:20:03 $
 * @author  $Author: suresh $
 */
public class RetrievalReportDataCreator extends AbstractExternalReportDataCreator
{
	//#CM725378
	// Class fields --------------------------------------------------

	//#CM725379
	// Result division (Not Processed) 
	static final String UN_PROCESSING = "0";
	//#CM725380
	// Result division (Picking Completed) 
	static final String RETRIEVAL_COMPLETE = "1";
	//#CM725381
	// Result division (Partially Completed) 
	static final String RETRIEVAL_COMPLETE_IN_PART = "2";

	//#CM725382
	// Class variables -----------------------------------------------
	private final String pName = "RetrievalReportData";

	//#CM725383
	// Class method --------------------------------------------------
	//#CM725384
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM725385
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.4 $,$Date: 2007/02/07 04:20:03 $");
	}

	//#CM725386
	// Constructors --------------------------------------------------
	//#CM725387
	/**
	 * Constructor
	 */
	public RetrievalReportDataCreator()
	{
	}

	//#CM725388
	// Public methods ------------------------------------------------
	//#CM725389
	/**
	 * Receive Connection object and Parameter object as a parameter and generate a picking report-data. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Database connection object
	 * @param startParam Class that inherits the <CODE>Parameter</CODE> class. 
	 * @return Return true if succeeded in generating report data, or return false if no date to be reported or failed to generate it. 
	 * @throws IOException Announce it when I/O error occurs. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		//#CM725390
		// Initialize the Report Data count. 
		setReportCount(0);

		//#CM725391
		// Declare the initial value of Report flag as False. 
		boolean reportFlag = false;

		//#CM725392
		// Generate a class instance to search VIEW. 
		RetrievalHostSendViewFinder vfinder = new RetrievalHostSendViewFinder(conn);
		RetrievalHostSendViewSearchKey vkey = new RetrievalHostSendViewSearchKey();

		//#CM725393
		//---- Generate a sub-inquiry statement of FROM clause to extract the Picking result data from the Sending Result Information. ----

		//#CM725394
		// Set the column to be obtained. 
		//#CM725395
		// Plan unique key 
		vkey.setPlanUkeyCollect("");

		//#CM725396
		// Set the search conditions. 

		//#CM725397
		// Pending results in dividing the Work Status Info data into two status: regard the data with status Completed or Standby in the same Aggregation key as Working and
		//#CM725398
		// regard the other data with status "Not Started" only in the same Aggregation key as Not Status, and therefore,
		//#CM725399
		// The search target is data with:
		//#CM725400
		//   work type = Picking and
		//#CM725401
		//   ( Status flag = Completed, and Work date = Work date specified via screen ) or
		//#CM725402
		//   ( ( Status flag = Not Started or Started or Working ) and Work date =  and Planned date <= Work date specified via screen ) 
		vkey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL, "=", "", "", "and");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION, "=", "((", "", "and");
		vkey.setWorkDate(sparam.getWorkDate(), "=", "", ")", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART, "=", "((", "", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_START, "=", "", "", "or");
		vkey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING, "=", "", ")", "and");
		vkey.setWorkDate("", "=", "", "", "and");
		vkey.setPlanDate(sparam.getWorkDate(), "<=", "", "))", "or");

		//#CM725403
		// Set the grouping order. 
		vkey.setPlanUkeyGroup(1);

		//#CM725404
		// Execute the method to generate sub-inquiry statement. 
		String subSql = vfinder.createFindSql(vkey);

		vkey.KeyClear();

		//#CM725405
		//---- Generate conditions to extract the Picking result data from the Sending Result Information. ----

		//#CM725406
		// Set the table union condition. 
		//#CM725407
		// Set the value connected with the plan unique key.
		vkey.setPlanUkeyJoin();

		//#CM725408
		// Set the search order of VIEW. 
		//#CM725409
		// In the order of Plan unique key + Location No. + Expiry Date + Case/Piece division + Added Date/Time + Work Result qty 
		vkey.setPlanUkeyOrder(1, true);
		vkey.setLocationNoOrder(2,true);
		vkey.setUseByDateOrder(3, true);
		vkey.setWorkFormFlagOrder(4, true);
		vkey.setRegistDateOrder(5, true);
		vkey.setResultQtyOrder(6, true);

		//#CM725410
		// Search through the Result Info. 
		int j = vfinder.search(vkey, subSql);
		if( j <= 0 )
		{
			//#CM725411
			// No target data was found. 
			setMessage("6003018");
			return false;
		}

		//#CM725412
		// Declare the local variable for compiling report data. 
		//#CM725413
		//   Result Sent View 
		HostSendView[] hostSendView = null;
		//#CM725414
		//   Aggregation key (Plan unique key) 
		String planUkey = "";
		//#CM725415
		//   Location No. 
		String locationNo = "";
		//#CM725416
		//   Expiry Date
		String useByDate = "";
		//#CM725417
		//   Case/Piece division 
		String workFormFlag = "";
		//#CM725418
		//   Picking Result qty
		int resultQty = 0;
		//#CM725419
		//   Picking Shortage Qty 
		int shortageCnt = 0;

		//#CM725420
		// Data with status flag Standby exists (True if data with status Standby exists in the same plan unique key) 
		boolean unStarting = false;
		//#CM725421
		// There is a data with status flag Completed (true if there is data with status Completed in the same plan unique key). 
		boolean completion = false;

		//#CM725422
		// Last update date/time (Use this to update the work report flag). 
		Date lastUpdate = null;

		//#CM725423
		// Generate the report data in the unit of Aggregation key. Therefore, allow this area to store Result Sent View Information, Result qty, and Shortage qty temporarily. 
		Vector hostSendViewVec = new Vector();
		Vector resultQtyVec = new Vector();
		Vector shortageCntVec = new Vector();

		HostSendView hostSendViewSv = null;

		//#CM725424
		// Generate CSV output class instance. 
		DataReportCsvWriter csvw = null;

		try
		{
			while (vfinder.isNext())
			{
				//#CM725425
				// Obtain every 100 search results. 
				hostSendView = (HostSendView[]) vfinder.getEntities(100);

				//#CM725426
				// Set the value for the local variable of the leading data if any. 
				if(StringUtil.isBlank(planUkey))
				{
					//#CM725427
					// Plan unique key 
					planUkey = hostSendView[0].getPlanUkey();
					//#CM725428
					// Location No. 
					locationNo = hostSendView[0].getLocationNo();
					//#CM725429
					// Expiry Date
					useByDate = hostSendView[0].getUseByDate();
					//#CM725430
					// Case/Piece division 
					workFormFlag = hostSendView[0].getWorkFormFlag();
					//#CM725431
					// Picking Result qty
					resultQty = 0;
					//#CM725432
					// Work shortage qty
					shortageCnt = 0;
					//#CM725433
					// Last update date/time
					lastUpdate = hostSendView[0].getLastUpdateDate();

					hostSendViewSv = hostSendView[0];
				}

				for( int count = 0 ; count < hostSendView.length ; count++ )
				{
					//#CM725434
					// Check for change in the Plan unique key. 
					if( !(hostSendView[count].getPlanUkey().equals(planUkey)) )
					{
						//#CM725435
						// Store the Result Sent View Information, Picking Result qty, and Shortage Qty in the Vector. 
						resultVectorSet(hostSendViewSv, resultQty, shortageCnt
														, hostSendViewVec, resultQtyVec, shortageCntVec);
						//#CM725436
						// Obtain the Result division. 
						String resultKind = getResultKind(conn, planUkey, unStarting, completion);
						//#CM725437
						// Determine to report or not. 
						if( reportDetermin(startParam, resultKind) )
						{
							//#CM725438
							// Output it to the Picking result data(CSV). 
							//#CM725439
							// Update the Report flag of the Result Sent Information to Reported. 
							if(!(csvw instanceof DataReportCsvWriter))
							{
								csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_RETRIEVALSUPPORT);
							}
							int i = RetrievalCsvWrite(conn, csvw, sparam.getWorkDate(), hostSendViewVec
														  , resultQtyVec, shortageCntVec, resultKind, lastUpdate);
							if( i > 0 )
							{
								//#CM725440
								// Add up the Report-data count. 
								setReportCount(getReportCount() + i);
								//#CM725441
								// Set the Report-data Available. 
								reportFlag = true;
							}
						}
						//#CM725442
						// Shift the Plan unique key to the Next. 
						planUkey = hostSendView[count].getPlanUkey();

						//#CM725443
						// Ensure that the Plan unique key has changed. Therefore, update also the Expiry Date, Case/Piece division, Location, 
						//#CM725444
						// , and the local variable of the Picking Result qty. 
						locationNo = hostSendView[count].getLocationNo();
						useByDate = hostSendView[count].getUseByDate();
						workFormFlag = hostSendView[count].getWorkFormFlag();
						resultQty = 0;
						shortageCnt = 0;
						lastUpdate = hostSendView[count].getLastUpdateDate();

						//#CM725445
						// Initialize the temporary memory area of status flag. 
						unStarting = false;
						completion = false;

						//#CM725446
						// Initialize the temporary memory area of Result Sent View info, result qty, and shortage qty 
						hostSendViewVec.clear();
						resultQtyVec.clear();
						shortageCntVec.clear();
					}
					else
					{
						//#CM725447
						// Check whether any change is found in Location, Expiry Date, and/or Case/Piece division? 
						if( !(hostSendView[count].getLocationNo().equals(locationNo)) ||
							!(hostSendView[count].getUseByDate().equals(useByDate)) ||
							!(hostSendView[count].getWorkFormFlag().equals(workFormFlag)) )
						{
							//#CM725448
							// Store the Expiry Date, Case/Piece division, Picking Location, Picking Result qty, and Shortage Qty in Vector. 
							resultVectorSet(hostSendViewSv, resultQty, shortageCnt
															, hostSendViewVec, resultQtyVec, shortageCntVec);

							//#CM725449
							// Set the new Location, Expiry Date, and Case/Piece division, and 
							//#CM725450
							// clear the Result qty. 
							locationNo = hostSendView[count].getLocationNo();
							useByDate = hostSendView[count].getUseByDate();
							workFormFlag = hostSendView[count].getWorkFormFlag();
							resultQty = 0;
							shortageCnt = 0;
						}
					}

					//#CM725451
					// Sum up the data only with status Completed of Result qty and Shortage qty that were designated in the Work Date via the screen. 
					if( ( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) ) &&
						( hostSendView[count].getWorkDate().equals(sparam.getWorkDate())) )
					{
						//#CM725452
						// Sum-up the Picking Result qty. 
						resultQty = resultQty + hostSendView[count].getResultQty();
						//#CM725453
						// Sum-up the Picking Shortage Qty. 
						shortageCnt = shortageCnt + hostSendView[count].getShortageCnt();
					}

					//#CM725454
					// Determine whether the status flag is "Standby" or not. 
					if( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART) )
					{
						//#CM725455
						// Make it true to store the fact that there are data with status flag Standby . 
						unStarting = true;
					}
					//#CM725456
					// Determine whether the status flag is "Completed" or not. 
					if( hostSendView[count].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION) )
					{
						//#CM725457
						// Make it true to store the fact that there are data with status flag Completed. 
						completion = true;
					}

					if(  lastUpdate.compareTo(hostSendView[count].getLastUpdateDate()) < 0 )
					{
						//#CM725458
						// Obtain the latest one of the last update date/time of the aggregation keys. 
						lastUpdate = hostSendView[count].getLastUpdateDate();
					}

					hostSendViewSv = hostSendView[count];
				}
			}

			//#CM725459
			// Execute the Process for the last aggregation data. 
			//#CM725460
			// Store the Result Sent View Information, Picking Result qty, and Shortage Qty in the Vector. 
			resultVectorSet(hostSendViewSv, resultQty, shortageCnt
											, hostSendViewVec, resultQtyVec, shortageCntVec);
			//#CM725461
			// Obtain the Result division. 
			String resultKind = getResultKind(conn, planUkey, unStarting, completion);
			//#CM725462
			// Determine to report or not. 
			if( reportDetermin(startParam, resultKind) )
			{
				//#CM725463
				// Output it to the Picking result data(CSV). 
				//#CM725464
				// Update the Report flag of the Result Sent Information to Reported. 
				if(!(csvw instanceof DataReportCsvWriter))
				{
					csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_RETRIEVALSUPPORT);
				}
				int i = RetrievalCsvWrite(conn, csvw, sparam.getWorkDate(), hostSendViewVec
											  , resultQtyVec, shortageCntVec, resultKind, lastUpdate);
				if( i > 0 )
				{
					//#CM725465
					// Add up the Report-data count. 
					setReportCount(getReportCount() + i);
					//#CM725466
					// Set the Report-data Available. 
					reportFlag = true;
				}
			}

			if( getReportCount() == 0 )
			{
				//#CM725467
				// No target data was found. 
				setMessage("6003018");
			}
			else
			{
				//#CM725468
				// Closed generating report-data. 
				setMessage("6001009");
			}

			return reportFlag;
		}
		catch (FileNotFoundException e)
		{
			//#CM725469
			// 6006020=File I/O error occurred. {0}
			RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
			//#CM725470
			// 6007031=File I/O error occurred. See log. 
			setMessage("6007031");
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			if(!(e instanceof ReadWriteException))
			{
				//#CM725471
				// 6006001=The unanticipated error occurred. {0}
				RmiMsgLogClient.write( new TraceHandler(6006001, e), this.getClass().getName() ) ;
			}
			//#CM725472
			// 6027009=The unanticipated error occurred. Refer to the log. 
			setMessage("6027009");
			
			//#CM725473
			// Deletion processing of file
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
				//#CM725474
				// Close processing
				if (csvw != null)
				{
					csvw.close();
				}
			}
			catch (IOException e)
			{
				//#CM725475
				// 6006020=The input/output error of the file occurred. {0}
				RmiMsgLogClient.write( new TraceHandler(6006020, e), this.getClass().getName() ) ;
				//#CM725476
				// 6007031=The input/output error of the file occurred. Refer to the log. 
				setMessage("6007031");
				//#CM725477
				// Deletion processing of file
				csvw.delete();
				throw new ReadWriteException(e.getMessage());
			}
			finally
			{
				vfinder.close();
			}
		}
	}

	//#CM725478
	// Package methods -----------------------------------------------

	//#CM725479
	// Protected methods ---------------------------------------------

	//#CM725480
	/**
	 * Set the Result data inquiry for Vector. 
	 * @param hostSendView Result Sent View Information 
	 * @param resultQty Picking Result qty
	 * @param shortageCnt Picking Shortage Qty 
	 * @param hostSendViewVec Result Sent View Information (with Plan unique key + Expiry Date + Case/Piece division + Picking Location + Order No.  ) 
	 * @param resultQtyVec Picking Result qty (Plan unique key + Expiry Date + Case/Piece division + Picking Location + Order No. ) 
	 * @param shortageCntVec Shortage Qty (Plan unique key + Expiry Date + Case/Piece division + Picking Location + Order No. ) 
	 */
	protected void resultVectorSet(HostSendView hostSendView, int resultQty, int shortageCnt
															, Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec)
	{
		//#CM725481
		// Result Sent Information 
		hostSendViewVec.addElement(hostSendView);
		//#CM725482
		// Picking Result qty
		resultQtyVec.addElement(new Integer(resultQty));
		//#CM725483
		// Shortage Qty
		shortageCntVec.addElement(new Integer(shortageCnt));
	}

	//#CM725484
	/**
	 * Determine the Result division. 
	 * @param conn Database connection object
	 * @param planUkey Plan unique key 
	 * @param unStarting Presence of "Standby" data in the Plan unique key 
	 * @param completion Presence of "Completed" data in the Plan unique key 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected String getResultKind(Connection conn, String planUkey, boolean unStarting, boolean completion)
																						throws ReadWriteException
	{
		//#CM725485
		// If all data is Completed, regard the Picking as "Completed". 
		if( !unStarting && completion )
		{
			return RETRIEVAL_COMPLETE;
		}

		//#CM725486
		// Regard data only with Standby (or some data with status Reported) as Standby data. 
		HostSendSearchKey skey = new HostSendSearchKey();
		HostSendHandler handle = new HostSendHandler(conn);
		skey.setPlanUkey(planUkey);
		skey.setReportFlag(HostSend.REPORT_FLAG_SENT);
		int sendCount = handle.count(skey);

		//#CM725487
		// Regard data only with Standby as Not Processed. 
		if( unStarting && !completion && sendCount == 0 )
		{
			return UN_PROCESSING;
		}

		//#CM725488
		// (Partially Completed) 
		return RETRIEVAL_COMPLETE_IN_PART;
	}

	//#CM725489
	/**
	 * Determine the acceptability to report the aggregation key data. 
	 * @param startParam Class that inherits the <CODE>Parameter</CODE> class. 
	 * @param resultKind Result division 
	 * @return Return the value to report the data or not. 
	 */
	protected boolean reportDetermin(Parameter startParam, String resultKind)
	{
		SystemParameter sparam = (SystemParameter) startParam;

		//#CM725490
		// Data with Result division = Not Processedand no check placed in the option checbox Report Workiing Work
		if( (resultKind.equals(UN_PROCESSING)) &&
			(!sparam.getSelectReportRetrievalData_Unworking()) )
		{
			//#CM725491
			// Disable to report the data with status Not Processed and with no check placed in Report Standby Work option.
			return false;
		}

		//#CM725492
		// Data with Result division = Partially Completedand no check placed in the option checbox Report Workiing Work
		if( (resultKind.equals(RETRIEVAL_COMPLETE_IN_PART)) &&
			(!sparam.getSelectReportRetrievalData_InProgress()) )
		{
			//#CM725493
			// No check placed on Report Processing Work for data with Partially Completed disables to report. 
			return false;
		}

		return true;
	}

	//#CM725494
	/**
	 * Aggregate and output the Picking Result. 
	 * @param conn Database connection object
	 * @param csvw CSV Writer Object 
	 * @param workDate Work date
	 * @param hostSendViewVec Result Sent View Information (with Plan unique key + Expiry Date + Case/Piece division + Picking Location + Order No.  ) 
	 * @param resultQtyVec Picking Result qty (for 1 Aggregation key) 
	 * @param shortageCntVec Shortage Qty (for one aggregation key) 
	 * @param resultKind Result division 
	 * @param lastUpdate Change the Send flag for the data on/before the latest update date/time. 
	 * @return Return the count of data that have been output to the Report Data. 
	 * @throws IOException  Announce it when I/O error occurs. 
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment information. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected int RetrievalCsvWrite(Connection conn, DataReportCsvWriter csvw, String workDate
												   , Vector hostSendViewVec, Vector resultQtyVec, Vector shortageCntVec
												   , String resultKind, Date lastUpdate)
													throws IOException, CSVItemNotFoundException, ReadWriteException
	{

		//#CM725495
		// Exclude the data other than report targets (result qty=0, shortage qty=0) from the stored result data. If Report Data with Not Worked is designated, however,
		//#CM725496
		// regard Standby Work data (result qty=0, shortage qty=0) as a data to be reported. 

		//#CM725497
		// Result Sent View Information 
		HostSendView[] hostSendViewTempAry = new HostSendView[hostSendViewVec.size()];
		hostSendViewVec.copyInto(hostSendViewTempAry);
		//#CM725498
		// Result qty
		Integer[] resultQtyTempAry = new Integer[resultQtyVec.size()];
		resultQtyVec.copyInto(resultQtyTempAry);
		//#CM725499
		// Shortage Qty
		Integer[] shortageCntTempAry = new Integer[shortageCntVec.size()];
		shortageCntVec.copyInto(shortageCntTempAry);

		//#CM725500
		// Generate the report data in the unit of Aggregation key. Therefore, allow this area to store Result Sent View Information, Result qty, and Shortage qty temporarily. 
		Vector hostSendViewTempVec = new Vector();
		Vector resultQtyTempVec = new Vector();
		Vector shortageCntTempVec = new Vector();

		for( int i = 0 ; i < hostSendViewTempAry.length ; i++ )
		{
			if( (resultQtyTempAry[i].intValue() == 0) && (shortageCntTempAry[i].intValue() == 0) &&
				(!resultKind.equals(UN_PROCESSING)) )
			{
				//#CM725501
				// If both result qty and shortage qty are zero, disable to output any status other than Not Processed. 
				continue;
			}
			//#CM725502
			// Result Sent Information 
			hostSendViewTempVec.addElement(hostSendViewTempAry[i]);
			//#CM725503
			// Picking Result qty
			resultQtyTempVec.addElement(resultQtyTempAry[i]);
			//#CM725504
			// Shortage Qty
			shortageCntTempVec.addElement(shortageCntTempAry[i]);
		}

		if( hostSendViewTempVec.size() <= 0 )
		{
			//#CM725505
			// No Result Report-data 
			return 0;
		}

		//#CM725506
		//  Copy the result data to be reported to the array. 

		//#CM725507
		// Result Sent View Information 
		HostSendView[] hostSendViewAry = new HostSendView[hostSendViewTempVec.size()];
		hostSendViewTempVec.copyInto(hostSendViewAry);
		//#CM725508
		// Result qty
		Integer[] resultQtyAry = new Integer[resultQtyTempVec.size()];
		resultQtyTempVec.copyInto(resultQtyAry);
		//#CM725509
		// Shortage Qty
		Integer[] shortageCntAry = new Integer[shortageCntTempVec.size()];
		shortageCntTempVec.copyInto(shortageCntAry);

		//#CM725510
		// Based on the Result data collected using a set of Picking Location  + Expiry Date + Case/Piece division, 
		//#CM725511
		// aggregate the Case/Piece division within the set of Picking Location  + Expiry Date. 

		//#CM725512
		// Make the CasePieceMix flag TRUE if Case data and Piece data are mixed in the entered hostSendViewAry array. 

		boolean CasePieceMix = false;
		boolean existCase = false;
		boolean existPiece = false;

		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			//#CM725513
			// With Case/Piece division None 
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

		String caseLocation = "";
		String pieceLocation = "";
		String caseOrder = "";
		String pieceOrder = "";
		int caseQty = 0;
		int pieceQty = 0;
		int caseShortageCnt = 0;
		int pieceShortageCnt = 0;
		int reportCount = 0;
		boolean printOut = false;

		//#CM725514
		// Output the result, based on the aggregated information of Case Item Picking Location  and  Piece Item Picking Location  which were generated referring to the Case/Piece division.
		//#CM725515
		// 
		for( int i = 0 ; i < hostSendViewAry.length ; i++ )
		{
			if (!CasePieceMix)
			{
				//#CM725516
				// For data with Case/Piece division None or Piece, set the result for the piece info. 
				if( (hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING)) ||
					(hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE)) )
				{
					//#CM725517
					// Disable to report any Location of data with Not Completed. 
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						pieceLocation = "";
						pieceOrder = "";
					}
					else
					{
						pieceLocation = hostSendViewAry[i].getLocationNo();
						pieceOrder = hostSendViewAry[i].getOrderNo();
					}
					int qty = resultQtyAry[i].intValue();
					pieceQty = qty;
					pieceShortageCnt = shortageCntAry[i].intValue();
					caseLocation = "";
					caseOrder = "";
					caseQty = 0;
					caseShortageCnt = 0;
				}
				else
				{
					//#CM725518
					// Disable to report any Location of data with Not Completed. 
					if (hostSendViewAry[i].getStatusFlag().equals(HostSendView.STATUS_FLAG_UNSTART))
					{
						caseLocation = "";
						caseOrder = "";
					}
					else
					{
						caseLocation = hostSendViewAry[i].getLocationNo();
						caseOrder = hostSendViewAry[i].getOrderNo();
					}
					caseQty = resultQtyAry[i].intValue();
					caseShortageCnt = shortageCntAry[i].intValue();
					pieceLocation = "";
					pieceOrder = "";
					pieceQty = 0;
					pieceShortageCnt = 0;
				}
				printOut = true;
			}
			//#CM725519
			// Case data and Piece data are mixed:
			else
			{
				//#CM725520
				// Refer to the Case/Piece division and obtain the Case Item Picking Location and the Piece Item Picking Location. 
				//#CM725521
				// Case/Piece division (Case) 
				if( hostSendViewAry[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE) )
				{
					//#CM725522
					// Data with status flag Standby makes the location blank (to report the work that has not been yet done). 
					if(hostSendViewAry[i].getStatusFlag().equals((HostSendView.STATUS_FLAG_UNSTART)))
					{
						caseLocation = "";
						caseOrder = "";
					}
					else
					{
						caseLocation = hostSendViewAry[i].getLocationNo();
						caseOrder = hostSendViewAry[i].getOrderNo();
					}
					caseQty = resultQtyAry[i].intValue();
					caseShortageCnt = shortageCntAry[i].intValue();
				}
				//#CM725523
				// Case/Piece division (Piece) 
				else
				{
					//#CM725524
					// Data with status flag Standby makes the location blank (to report the work that has not been yet done). 
					if(hostSendViewAry[i].getStatusFlag().equals((HostSendView.STATUS_FLAG_UNSTART)))
					{
						pieceLocation = "";
						pieceOrder = "";
					}
					else
					{
						pieceLocation = hostSendViewAry[i].getLocationNo();
						pieceOrder = hostSendViewAry[i].getOrderNo();
					}
					pieceQty = resultQtyAry[i].intValue();
					pieceShortageCnt = shortageCntAry[i].intValue();
				}

				//#CM725525
				// Make the printOut flag TRUE to output report-data. 

				//#CM725526
				// Not Processed 
				if(resultKind.equals(UN_PROCESSING))
				{
					//#CM725527
					// For data with status Not Processed, there are always two data. Accordingly, output the second data to the CSV to integrate into one record. there are always two data if. 
					if(i == 1)
					{
						printOut = true;
					}
				}
				//#CM725528
				// Picking Completed 
				else if(resultKind.equals(RETRIEVAL_COMPLETE))
				{
					if(i == hostSendViewAry.length-1)
					{
						printOut = true;
					}
					else
					{
						//#CM725529
						// Disable to output CSV when Expiry Date is unknown to aggregate into one record. 
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
				//#CM725530
				// Partially Completed 
				else if(resultKind.equals(RETRIEVAL_COMPLETE_IN_PART))
				{
					//#CM725531
					// Disable to output any data with status flag Standby is not output to CSV. 
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

			//#CM725532
			// Output Report-data 
			if( printOut )
			{
				CsvWriteLine(conn, csvw, workDate, hostSendViewAry[i], caseLocation, caseOrder, caseQty, caseShortageCnt
										, pieceLocation, pieceOrder, pieceQty, pieceShortageCnt, resultKind);
				reportCount++;

				caseLocation = "";
				pieceLocation = "";
				caseOrder = "";
				pieceOrder = "";
				caseQty = 0;
				pieceQty = 0;
				caseShortageCnt = 0;
				pieceShortageCnt = 0;
				printOut = false;
			}
		}

		try
		{
			//#CM725533
			// For data with Result division Picking Completed or Partially Completed, update the Report flag and the last update date/time. 
			if( (resultKind.equals(RETRIEVAL_COMPLETE)) || (resultKind.equals(RETRIEVAL_COMPLETE_IN_PART)) )
			{
				//#CM725534
				/* Update the Report flag of the Result Sent Information. */

				HostSendHandler hhandle = new HostSendHandler(conn);
				HostSendSearchKey hsKey = new HostSendSearchKey();
				hsKey.setPlanUkey(hostSendViewAry[0].getPlanUkey());
				hsKey.setWorkDate(workDate);
				hsKey.setLastUpdateDate(lastUpdate, "<=");
				if (hhandle.count(hsKey) > 0)
				{
					//#CM725535
					// 	 Instance of Update key  for Result Sent Information 
					HostSendAlterKey hakey = new HostSendAlterKey();

					//#CM725536
					// Update the Report flag of the Result Sent Information. 
					//#CM725537
					// Set the aggregation key for Update key. 
					hakey.setPlanUkey(hostSendViewAry[0].getPlanUkey());
					hakey.setWorkDate(workDate);
					hakey.setLastUpdateDate(lastUpdate, "<=");

					//#CM725538
					// Set the update field item. 
					//#CM725539
					// Update the Report flag of the Result Sent Information to Reported. 
					hakey.updateReportFlag(SystemDefine.REPORT_FLAG_SENT);
					hakey.updateLastUpdatePname(pName);
					//#CM725540
					// Update the Result Sent Information. 
					hhandle.modify(hakey);
				}

				//#CM725541
				/* Update the Report flag of Work Status Info. */

				RetrievalWorkingInformationHandler swhandle = new RetrievalWorkingInformationHandler(conn); 
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

	//#CM725542
	/**
	 * Execute Picking result output of one line <BR>
	 * After output, update the Result Sent Information, Report flag of Work Status Info, and the last update date/time. 
	 * @param conn Database connection object
	 * @param csvw CSV Writer Object 
	 * @param workDate Work date
	 * @param hostSendView Result Sent View Information 
	 * @param caseLocation Case Picking Location
	 * @param caseOrder Case Order No.
	 * @param caseQty Result Case Qty 
	 * @param caseShortageCnt Case Shortage Qty 
	 * @param pieceLocation Piece Picking Location 
	 * @param pieceOrder Piece Order No.
	 * @param pieceQty Result Piece Qty 
	 * @param pieceShortageCnt Piece Shortage Qty 
	 * @param resultKind Result division 
	 * @throws IOException  Announce it when I/O error occurs. 
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment information. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void CsvWriteLine(Connection conn, DataReportCsvWriter csvw, String workDate, HostSendView hostSendView
											 , String caseLocation, String caseOrder, int caseQty, int caseShortageCnt
											 , String pieceLocation, String pieceOrder, int pieceQty, int pieceShortageCnt
											 , String resultKind)
															throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		//#CM725543
		// Set the Picking Result data for field item. 
		//#CM725544
		// Planned Picking Date
		csvw.setValue("PLAN_DATE", hostSendView.getPlanDate());
		//#CM725545
		// Consignor Code
		csvw.setValue("CONSIGNOR_CODE", hostSendView.getConsignorCode());
		//#CM725546
		// Consignor Name
		csvw.setValue("CONSIGNOR_NAME", hostSendView.getConsignorName());
		//#CM725547
		// Customer Code
		csvw.setValue("CUSTOMER_CODE", hostSendView.getCustomerCode());
		//#CM725548
		// Customer Name
		csvw.setValue("CUSTOMER_NAME", hostSendView.getCustomerName1());
		//#CM725549
		// Ticket No. 
		csvw.setValue("SHIPPING_TICKET_NO", hostSendView.getShippingTicketNo());
		//#CM725550
		// Line No
		csvw.setValue("SHIPPING_LINE_NO", new Integer(hostSendView.getShippingLineNo()));
		//#CM725551
		// Item Code
		csvw.setValue("ITEM_CODE", hostSendView.getItemCode());
		//#CM725552
		// Bundle ITF
		csvw.setValue("BUNDLE_ITF", hostSendView.getBundleItf());
		//#CM725553
		// Case ITF
		csvw.setValue("ITF", hostSendView.getItf());
		//#CM725554
		// Packed qty per bundle
		csvw.setValue("BUNDLE_ENTERING_QTY", new Integer(hostSendView.getBundleEnteringQty()));
		//#CM725555
		// Packed Qty per Case
		csvw.setValue("ENTERING_QTY", new Integer(hostSendView.getEnteringQty()));
		//#CM725556
		// Item Name
		csvw.setValue("ITEM_NAME1", hostSendView.getItemName1());
		//#CM725557
		// Picking qty (Total Bulk Qty) 
		csvw.setValue("PLAN_QTY", new Integer(hostSendView.getHostPlanQty()));
		//#CM725558
		// Piece Picking Location 
		csvw.setValue("JOB_LOCATION_PIECE", pieceLocation);
		//#CM725559
		// Case Picking Location
		csvw.setValue("JOB_LOCATION_CASE", caseLocation);
		//#CM725560
		// Piece Order No.
		csvw.setValue("ORDER_NO_PIECE", pieceOrder);
		//#CM725561
		// Case Order No.
		csvw.setValue("ORDER_NO_CASE", caseOrder);
		//#CM725562
		// Result Piece Qty 
		csvw.setValue("RESULT_QTY_PIECE", new Integer(pieceQty));
		//#CM725563
		// Result Case Qty 
		csvw.setValue("RESULT_QTY_CASE", new Integer(caseQty));
		//#CM725564
		// Data with Result division Not Processed 
		if( resultKind.equals(UN_PROCESSING) )
		{
			//#CM725565
			// Set a space in the Picking Result Date. 
			csvw.setValue("COMPLETE_DATE", "");
		}
		else
		{
			//#CM725566
			// Picking Result Date 
			csvw.setValue("COMPLETE_DATE", workDate);
		}

		//#CM725567
		// Result division 
		csvw.setValue("RESULT_FLAG", resultKind);

		//#CM725568
		// Expiry Date
		if( (pieceQty != 0) || (caseQty != 0) || (pieceShortageCnt != 0) || (caseShortageCnt != 0) )
		{
			csvw.setValue("USE_BY_DATE", hostSendView.getUseByDate());
		}
		else
		{
			csvw.setValue("USE_BY_DATE", "");
		}

		//#CM725569
		// Output the picking result data to CSV. 
		csvw.writeLine();

		return;
	}
	
	//#CM725570
	// Private methods -----------------------------------------------	
}
//#CM725571
//end of class

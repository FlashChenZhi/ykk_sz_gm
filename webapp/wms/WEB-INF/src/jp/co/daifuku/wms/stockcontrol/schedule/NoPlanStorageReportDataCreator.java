package jp.co.daifuku.wms.stockcontrol.schedule;

// $Id: NoPlanStorageReportDataCreator.java,v 1.3 2006/11/21 04:23:05 suresh Exp $

//#CM11166
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
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.DataReportCsvWriter;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendReportFinder;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalReportDataCreator;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;

//#CM11167
/**
 * Designer : T.Terasaki <BR>
 * Maker : T.Terasaki <BR>
 * <BR>
 * <CODE>NoPlanStorageReportDataCreator</CODE> class executes the process to report the Unplanned Storage result data.<BR>
 * Inherit <CODE>AbstractExternalReportDataCreator</CODE> abstract class and implement the required processes.<BR>
 * The method in this class receives the connection object and executes process for updating the database.<BR>
 * However, this method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Data reporting process(<CODE>report(Connection conn, Parameter startParam)</CODE>Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Connection object and Parameter object as a parameter and , database<BR>
 *   Generate a Unplanned Storage result data file (CSV file) from the Sending Result Info.<BR>
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
 *        -Generate Handler instance for the Sending result information.<BR>
 *        -Generate sending Result Info SearchKey instance for the Sending Result Info.<BR>
 *        </DIR>
 *      4.Set the Search key to search a sending Result Info for SearchKey.<BR>
 *          <DIR>
 *          -Search conditions<BR>
 *             <DIR>
 *             Work division = Unplanned storage<BR>
 *             Work Status flag = Not Sent<BR>
 *             Work Date = Work Date of the screen parameter<BR>
 *             </DIR>
 *          -Sequence<BR>
 *             <DIR>
 *             Consignor code<BR>
 *             Item Code<BR>
 *             Work Result Location<BR>
 *             Expiry Date<BR>
 *             </DIR>
 *          </DIR>
 *      5.Search through the Sending Result Information using the find of the Handler instance and obtain the result in the Sending Result Info Entity.<BR>
 *        <DIR>
 *        If the count of obtained sending Result Info is 0: <BR>
 *          <DIR>
 *          Message = No target data was found.<BR>
 *          Close with no data reported (false).<BR>
 *          </DIR>
 *        </DIR>
 *      6.Generate local variable.<BR>
 *        <DIR>
 *        Generate the following area and set the initial value.<BR>
 *          <DIR>
 *          -Sending Result Information info<BR>
 *          -Sending Result Info (for storing case, piece info)<BR>
 *          -Consignor code, item code, Location No., case Picking Result qty, piece Picking Result qty<BR>
 *          </DIR>
 *        Generate a area for <BR>
 *        </DIR>
 *      7.Generate Class Instance.<BR>
 *        <DIR>
 *        -Generate Generation of DataReportCsvWriter instance.<BR>
 *           <DIR>
 *           This class generates a data report CSV.<BR>
 *           </DIR>
 *        </DIR>
 *      8.Loop until closing the obtained sending Result Info Entity.<BR>
 *        <DIR>
 * <BR>
 *        - Start looping -<BR>
 * <BR>
 *          <DIR>
 *          9.Execute the following processes.<BR>
 *            <DIR>
 *            9-1.Output the stored Unplanned Picking result data to the Unplanned Storage result file (CSV) when the aggregation key changed.<BR>
 *                Aggregation key:Consignor code + Item Code + Location No<BR>
 *                 (Opening no CSV file allows to open the Unplanned Storageg Result CSV file.)<BR>
 *                 -Set true Report Data flag.<BR>
 *            9-2.Update the local variable.<BR>
 *               <DIR>
 *               -Initialize the Sending Result Info and other temporarily stored variables.<BR>
 *               </DIR>
 *            </DIR>
 *          </DIR>
 * <BR>
 *        - Close looping -<BR>
 *        </DIR>
 * <BR>
 *      9.Output the Unplanned picking result that has not yet been output.<BR>
 *     10.Close the Unplanned Storage Result file (CSV).<BR>
 *     11.Set the message.<BR>
 *        <DIR>
 *        Generate one or more file(s) (CSV) for Unplanned storage result file (CSV)  in the area for maintaining the messages area<BR>
 *        Set the "Closed generating the report-data ".<BR>
 *        Set "No target data was found." if no data generated.<BR>
 *        </DIR>
 * <BR>
 *   <Aggregate/Output Unplanned storage result process outline> <BR>
 *      <DIR>
 *      Disable to aggregate any Unplanned Storage result.<BR>
 *      </DIR>
 * <BR>
 *   <Output Unplanned storage result file (CSV) process outline> <BR>
 *      <DIR>
 *      Compile each item (count) of the Unplanned storage result file (CSV) and output to the CSV file.<BR>
 *      Update "Report" flag of the Sending Result Info to "Reported" using the work date + plan unique key.<BR>
 *      1.Compile one line of items in the Unplanned storage result file (CSV).<BR>
 *        <DIR>
 *        (Contents of compile)<BR>
 *           <DIR>
 *           Consignor code   = Consignor code<BR>
 *           Consignor name     = Consignor name<BR>
 *           JAN          = Item Code<BR>
 *           Bundle ITF    = Bundle ITF (which are output to the piece item result)<BR>
 *           Case ITF    = Case ITF (which are output to the case item result)<BR>
 *           Packed qty per bundle   = Packed qty per bundle (which are output to the piece item result)<BR>
 *           Packed qty per Case   = Packed qty per Case (which are output to the case item result)<BR>
 *           Item Name         = Item Name<BR>
 *           <BR>
 *           Case Item Storage Location = Work Result Location, if the Case/Piece division is Case item.<BR>
 *           Piece Item Storage Location = Work Result Location, if the Case/Piece division is other than Case item.<BR>
 *           <BR>
 *           Expiry Date     = Expiry Date<BR>
 *           <BR>
 *           Case Storage Result qty = Work Result qty, if the Case/Piece division is Case item.<BR>
 *           Piece Storage Result qty = Work Result qty, if the Case/Piece division is other than Case item.<BR>
 *           <BR>
 *           Storage Result Date   = Work Date<BR>
 *           </DIR>
 *        </DIR>
 *      2.Output one line of Unplanned storage result file (CSV).<BR>
 *        <DIR>
 *        Output to the Unplanned Storage Result data using the writeLine method of DataReportCsvWriter.<BR>
 *        </DIR>
 *      3.Update the Report flag.<BR>
 *          <DIR>
 *          3-1.Generate Handler instance for the Sending Result Info.<BR>
 *          3-2.Generate AlterKey instance for the Sending Result Info.<BR>
 *          3-3.Set the Search key to update sending result info and update data to AlterKey.<BR>
 *              <DIR>
 *              -Search conditions<BR>
 *                 <DIR>
 *                 Work Date+Work No.+Work division+Consignor code+Item Code+Location+Expiry Date<BR>
 *                 </DIR>
 *              -Updated data<BR>
 *                 <DIR>
 *                 Report flag = Reported<BR>
 *                 Last update process name = Program ID<BR>
 *                 </DIR>
 *              </DIR>
 *          3-4.Modify the Sending Result Info and click on Modify button to update it.<BR>
 *          </DIR>
 *      </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/18</TD><TD>T.Terasaki</TD><TD>Create New</TD></TR>
 * <TR><TD>2005/03/02</TD><TD>T.Terasaki</TD><TD>Modification such as using HostSendReportFinder from HostSendFinder</TD></TR>
 * <TR><TD>2005/03/17</TD><TD>T.Nakajima</TD><TD>Storage qty (Total bulk qty)item (count) of Report Data was deleted.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:05 $
 * @author  $Author: suresh $
 */
public class NoPlanStorageReportDataCreator extends AbstractExternalReportDataCreator
{
	//#CM11168
	// Class fields --------------------------------------------------

	//#CM11169
	// Class variables -----------------------------------------------
	private final String pName = "NoPlanStorageReportData";
	private final int    DATA_PROC_CNT = 100;		// データ処理件数

	//#CM11170
	// Class method --------------------------------------------------
	//#CM11171
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM11172
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:23:05 $");
	}

	//#CM11173
	// Constructors --------------------------------------------------

	//#CM11174
	// Public methods ------------------------------------------------
	//#CM11175
	/**
	 * Receive Connection object and Locale object as a parameter and generate storage report-data.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param startParam This Class inherits the <CODE>Parameter</CODE> class.
	 * @throws IOException Announce it when I/O error occurs.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @return Return true if scceeded in generating report data, or return false if no date to be reported or failed to generate it.
	 */
	public boolean report(Connection conn, Parameter startParam) throws IOException, ReadWriteException
	{
		SystemParameter sparam = (SystemParameter) startParam;

		//#CM11176
		// Initialize the Report Data count.
		setReportCount(0);

		//#CM11177
		// Declare the initial value of Report flag as False.
		boolean reportFlag = false;

		//#CM11178
		// Generate a class instance to search VIEW.
		HostSendReportFinder vfinder = new HostSendReportFinder(conn);
		HostSendSearchKey vkey = new HostSendSearchKey();

		//#CM11179
		// Set the column to be obtained.

		//#CM11180
		// Set the search conditions.
		//#CM11181
		//   Work division       = Unplanned storage and
		//#CM11182
		//   Work Report flag = Not Sent   and
		//#CM11183
		//   Work Date         = Work Date of the screen parameter
		vkey.KeyClear();
		vkey.setJobType(SystemDefine.JOB_TYPE_EX_STORAGE);
		vkey.setReportFlag(SystemDefine.REPORT_FLAG_NOT_SENT);
		vkey.setWorkDate(sparam.getWorkDate());

		//#CM11184
		// Set search sequence of the Sending result info.
		//#CM11185
		//  Consignor code
		//#CM11186
		//  Item Code
		//#CM11187
		//  Work Result Location
		//#CM11188
		//  Expiry Date
		vkey.setConsignorCodeOrder(1, true);
		vkey.setItemCodeOrder(2, true);
		vkey.setResultLocationNoOrder(3, true);
		vkey.setResultUseByDateOrder(4, true);
		vkey.setRegistDateOrder(5, false);

		//#CM11189
		// Declare a local variable for compiling the report-data and initialize it.
		HostSend[] hostSend = null;
		HostSend hostSendSv = null;
		HostSend caseHostSend = null;
		HostSend pieceHostSend = null;
		//#CM11190
		// Local variable for compiling the report-data (Consignor code, Item code, Location No., Case storage result qty, Piece storage result qty)
		String consignorCode = "";
		String itemCode = "";
		String locatioNo = "";
		String useByDate = "";
		int caseResultQty = 0;
		int pieceResultQty = 0;

		// CSV出力クラスのインスタンスを作成します。
		DataReportCsvWriter csvw = null;
		try
		{
			vfinder.open();
			//#CM11191
			// Search for sending Result Info.
			if (vfinder.search(vkey) <= 0)
			{
				//#CM11192
				// No target data was found.
				setMessage("6003018");
			}
			else
			{
				// CSV出力クラスのインスタンスを作成します。
				if (!(csvw instanceof DataReportCsvWriter))
				{
					csvw = new DataReportCsvWriter(DataReportCsvWriter.REPORTTYPE_NOPLANSTRAGESUPPOR);
				}

				//#CM11194
				// Generate a data file per 100 search results until there remains no result data.
				while (vfinder.isNext())
				{
					//#CM11195
					// Obtain up to 100 search results.
					hostSend = (HostSend[]) vfinder.getEntities(DATA_PROC_CNT);

					//#CM11196
					// Set the value for the local variable of the leading data if any.
					if(StringUtil.isBlank(consignorCode))
					{
						hostSendSv = hostSend[0];
						consignorCode = hostSend[0].getConsignorCode();
						itemCode = hostSend[0].getItemCode();
						locatioNo = hostSend[0].getLocationNo();
						useByDate = hostSend[0].getUseByDate();
						caseHostSend = null;
						pieceHostSend = null;
						caseResultQty = 0;
						pieceResultQty = 0;
					}

					for (int count = 0; count < hostSend.length; count++)
					{
						//#CM11197
						// Check whether the Aggregation key has changed or not?
						//#CM11198
						// Compare using Consignor Code  +   Item Code  +  Location No.
						if ( !(hostSend[count].getConsignorCode().equals(consignorCode))
							|| !(hostSend[count].getItemCode().equals(itemCode))
							|| !(hostSend[count].getLocationNo().equals(locatioNo))
							|| !(hostSend[count].getUseByDate().equals(useByDate)) )
						{
							//#CM11199
							// Output the changed contents of the aggregation key to the Unplanned Storage result data (CSV).
							//#CM11200
							// Update the "Report" flag of the Sending Result Info to "Reported".
							int wrtCnt = StorageCsvWrite(conn, csvw, hostSendSv,
															caseHostSend, pieceHostSend, caseResultQty, pieceResultQty);

							//#CM11201
							// Add up the Report-data count.
							setReportCount(getReportCount() + wrtCnt);

							//#CM11202
							// Update the local variable.
							hostSendSv = hostSend[count];
							consignorCode = hostSend[count].getConsignorCode();
							itemCode = hostSend[count].getItemCode();
							locatioNo = hostSend[count].getLocationNo();
							useByDate = hostSend[count].getUseByDate();
							caseHostSend = null;
							pieceHostSend = null;
							caseResultQty = 0;
							pieceResultQty = 0;
						}

						//#CM11203
						// Case/Piece division�� Case�H
						if (hostSend[count].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
						{
							if( caseHostSend == null )
							{
								caseHostSend = hostSend[count];
							}
							caseResultQty = caseResultQty + hostSend[count].getResultQty();
						}
						else
						{
							if( pieceHostSend == null )
							{
								pieceHostSend = hostSend[count];
							}
							pieceResultQty = pieceResultQty + hostSend[count].getResultQty();
						}
					}
				}

				//#CM11204
				// Output it to the Inventory Check result data (CSV) that has not yet been output.
				int wrtCnt = StorageCsvWrite(conn, csvw, hostSendSv,
												caseHostSend, pieceHostSend, caseResultQty, pieceResultQty);
				//#CM11205
				// Add up the Report-data count.
				setReportCount(getReportCount() + wrtCnt);

				//#CM11206
				// Message output
				if( getReportCount() == 0 )
				{
					//#CM11207
					// No target data was found.
					setMessage("6003018");
				}
				else
				{
					//#CM11208
					// Set the Report-data Available.
					reportFlag = true;

					//#CM11209
					// Closed generating report-data.
					setMessage("6001009");
				}
			}
			return reportFlag;
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
				vfinder.close();
			}
		}
	}

	//#CM11213
	// Package methods -----------------------------------------------

	//#CM11214
	// Protected methods ---------------------------------------------

	//#CM11215
	// Private methods -----------------------------------------------

	//#CM11216
	/**
	 * Output the Unplanned Storage result.<BR>
	 * 
	 * @param conn Database connection object
	 * @param csvw CSV Writer object
	 * @param hostSend Sending Result Information info
	 * @param caseHostSend Case Sending Result Information
	 * @param pieceHostSend Piece Sending Result Information
	 * @param caseResultQty Case Storage Result qty
	 * @param pieceResultQty Piece Storage Result qty
	 * @return Return the count of data that have been output to the Report Data.
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private int StorageCsvWrite(Connection conn, DataReportCsvWriter csvw, HostSend hostSend,
									HostSend caseHostSend, HostSend pieceHostSend, int caseResultQty, int pieceResultQty)
								throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		int reportCount = 0;
		//#CM11217
		// Output the result.
		CsvWriteLine(csvw, hostSend, caseHostSend, pieceHostSend, caseResultQty, pieceResultQty);
		reportCount++;

		try
		{
			//#CM11218
			// Update the Report flag and the last update date/time.
			//#CM11219
			// Update the Report flag of the Sending Result Info.
			HostSendHandler hhandle = new HostSendHandler(conn);
			HostSendSearchKey hsKey = new HostSendSearchKey();
			//#CM11220
			// Work Date
			hsKey.setWorkDate(hostSend.getWorkDate());
			//#CM11221
			// Work division
			hsKey.setJobType(hostSend.getJobType());
			//#CM11222
			// Consignor code
			hsKey.setConsignorCode(hostSend.getConsignorCode());
			//#CM11223
			// Item Code
			hsKey.setItemCode(hostSend.getItemCode());
			//#CM11224
			// Location
			hsKey.setLocationNo(hostSend.getLocationNo());
			//#CM11225
			// Expiry Date
			hsKey.setUseByDate(hostSend.getUseByDate());
			if (hhandle.count(hsKey) > 0)
			{
				//#CM11226
				// Sending Result Information Updating Key Instance
				HostSendAlterKey hakey = new HostSendAlterKey();

				//#CM11227
				// Update the Report flag of the Sending Result Info.
				//#CM11228
				// Set the update key.
				//#CM11229
				// Work Date
				hakey.setWorkDate(hostSend.getWorkDate());
				//#CM11230
				// Work division
				hakey.setJobType(hostSend.getJobType());
				//#CM11231
				// Consignor code
				hakey.setConsignorCode(hostSend.getConsignorCode());
				//#CM11232
				// Item Code
				hakey.setItemCode(hostSend.getItemCode());
				//#CM11233
				// Location
				hakey.setLocationNo(hostSend.getLocationNo());
				//#CM11234
				// Expiry Date
				hakey.setUseByDate(hostSend.getUseByDate());

				//#CM11235
				// Set the update item (count).
				//#CM11236
				// Update the Report flag of the Sending Result Info to "Reported".
				hakey.updateReportFlag(SystemDefine.REPORT_FLAG_SENT);
				hakey.updateLastUpdatePname(pName);

				//#CM11237
				// Update the Sending Result Info.
				hhandle.modify(hakey);
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

	//#CM11238
	/**
	 * Output one line of Storage Result.<BR>
	 * 
	 * @param csvw CSV Writer object
	 * @param hostSend Sending Result Information info
	 * @param caseHostSend Case Sending Result Information
	 * @param pieceHostSend Piece Sending Result Information
	 * @param caseResultQty Case Storage Result qty
	 * @param pieceResultQty Piece Storage Result qty
	 * @throws IOException  Announce it when I/O error occurs.
	 * @throws CSVItemNotFoundException Announce when no designated item (count) exists in the CSV file or Environment Information.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private void CsvWriteLine(DataReportCsvWriter csvw, HostSend hostSend,
									HostSend caseHostSend, HostSend pieceHostSend, int caseResultQty, int pieceResultQty)
									throws IOException, CSVItemNotFoundException, ReadWriteException
	{
		//#CM11239
		// Set the Storage Result Data for the item (count).
		//#CM11240
		// Consignor code
		csvw.setValue("CONSIGNOR_CODE", hostSend.getConsignorCode());
		//#CM11241
		// Consignor name
		csvw.setValue("CONSIGNOR_NAME", hostSend.getConsignorName());
		//#CM11242
		// JAN
		csvw.setValue("ITEM_CODE", hostSend.getItemCode());
		//#CM11243
		// Bundle ITF
		csvw.setValue("BUNDLE_ITF", hostSend.getBundleItf());
		//#CM11244
		// Case ITF
		csvw.setValue("ITF", hostSend.getItf());
		//#CM11245
		// Packed qty per bundle
		csvw.setValue("BUNDLE_ENTERING_QTY", new Integer(hostSend.getBundleEnteringQty()));
		//#CM11246
		// Packed qty per Case
		csvw.setValue("ENTERING_QTY", new Integer(hostSend.getEnteringQty()));
		//#CM11247
		// Item Name
		csvw.setValue("ITEM_NAME1", hostSend.getItemName1());
		//#CM11248
		// Piece Item Storage Location
		csvw.setValue("JOB_LOCATION_PIECE", "");
		//#CM11249
		// Piece Storage Result qty
		csvw.setValue("RESULT_QTY_PIECE", new Integer(0));
		//#CM11250
		// Case Item Storage Location
		csvw.setValue("JOB_LOCATION_CASE", "");
		//#CM11251
		// Case Storage Result qty
		csvw.setValue("RESULT_QTY_CASE", new Integer(0));
		//#CM11252
		// Storage Result Date
		csvw.setValue("COMPLETE_DATE", hostSend.getWorkDate());
		//#CM11253
		// Expiry Date
		csvw.setValue("USE_BY_DATE", hostSend.getResultUseByDate());
		//#CM11254
		// Is there any Case result?
		if( caseResultQty > 0 )
		{
			if( caseHostSend != null )
			{
				//#CM11255
				// Case Item Storage Location
				csvw.setValue("JOB_LOCATION_CASE", caseHostSend.getResultLocationNo());
			}
			//#CM11256
			// Case Storage Result qty
			csvw.setValue("RESULT_QTY_CASE", new Integer(caseResultQty));
		}
		//#CM11257
		// Is there any Piece result?
		if( pieceResultQty > 0)
		{
			if( pieceHostSend != null )
			{
				//#CM11258
				// Piece Item Storage Location
				csvw.setValue("JOB_LOCATION_PIECE", pieceHostSend.getResultLocationNo());
			}
			//#CM11259
			// Piece Storage Result qty
			csvw.setValue("RESULT_QTY_PIECE", new Integer(pieceResultQty));
		}
		//#CM11260
		// Output Storage Result Data to the CSV.
		csvw.writeLine();

		return;
	}
}
//#CM11261
//end of class

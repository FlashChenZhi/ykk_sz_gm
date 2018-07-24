//#CM720734
//$Id: Id0331Process.java,v 1.3 2007/02/07 04:19:40 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.io.IOException;
import java.sql.SQLException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM720735
/**
 * Designer :E.Takeda <BR>
 * Maker :E.Takeda<BR>
 * <BR>
 * Allow this class to execute the process for sending Order Picking Result from RFT.<BR>
 * Execute the process to complete the Order Picking using the functions provided by Id0331Operate class.<BR>
 * To use "Processing" data, obtain the corresponding data from the "processing" data storage info, and generate a work file in recv folder.
 *   (<CODE>restoreDataFile()</CODE>)<BR>
 *   <OL><LI>Search for storage info of data with status "Processing".<BR>
 *    [search conditions] 
 *   <UL><LI>RFTNo</LI></UL>
 *    [Sorting order] 
 *   <UL><LI>Line No</LI></UL>
 *    [ Obtained contents ] 
 *   <UL><LI>RFTNo</LI>
 *       <LI>file name: ID5330.txt</LI>
 *       <LI>Line No</LI>
 *       <LI>Data Content: Data content in one (1) line</LI>
 *   </LI></UL>
 *   <LI>Generate a work file in recvFolder using the obtained content.</LI>
 *   </OL>
 * If the process completed normally except for change-box process, delete the corresponding data in the "Processing" data storage information, and update the electronic statement field item of the RFT management info to NULL.<BR>
 * <BR>
 *   <OL>
 *   <LI>Search for storage info of data with status "Processing".<BR>
 *    [search conditions] 
 *   <UL><LI>RFTNo</LI></UL></LI>
 *   <LI>Delete the corresponding record.</LI>
 *   <LI>Search through the RFT Work Status and update the corresponding info.<BR>
 *    [Update Conditions] 
 *   <UL><LI>RFTNo</LI></UL>
 *    [UpdateContent] 
 *   <UL><LI>the response electronic statement: NULL</LI>
 *       <LI>Last update date/time: Date/time on system</LI>
 *       <LI>Last update process name: ID0331</LI>
 *   </UL>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/04</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:40 $
 * @author  $Author: suresh $
 */
public class Id0331Process extends IdProcess
{
	//#CM720736
	//  Class fields --------------------------------------------------
	private static final String CLASS_NAME = "Id0331Process";
	
	private static final String PROCESS_NAME = "ID0331";

	private String className = "";

	//#CM720737
	//Class variables -----------------------------------------------

	//#CM720738
	//Class methods -------------------------------------------------

	//#CM720739
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:40 $";
	}

	//#CM720740
	//Constructors --------------------------------------------------
	//#CM720741
	//Public methods ------------------------------------------------
	//#CM720742
	/**
	 * Order Picking the Result data Sent
	 * @param  rdt  Received buffer
	 * @param  sdt  Buffer from which sends data
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM720743
		// Generate the instance for analyzing the Received electronic statement.
		RFTId0331 rftId0331 = null;

		//#CM720744
		// Generate an instance for generating a sending electronic statement
		RFTId5331 rftId5331 = null;
		try
		{
			//#CM720745
			// Generate the instance for analyzing the Received electronic statement.
			rftId0331 = (RFTId0331) PackageManager.getObject("RFTId0331");
			rftId0331.setReceiveMessage(rdt);

			//#CM720746
			// Generate an instance for generating a sending electronic statement
			rftId5331 = (RFTId5331) PackageManager.getObject("RFTId5331");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR,
					CLASS_NAME, e);
			throw e;
		}
		
		//#CM720747
		//Variable for maintaining the Response flag
		String ansCode = "";

		//#CM720748
		//Variable for maintaining the detail of the error.
		String errorDetail = RFTId5331.ErrorDetails.NORMAL;

		//#CM720749
		// Obtain the RFT machine from the Received electronic statement.
		String rftNo = rftId0331.getRftNo();

		//#CM720750
		// Obtain the Personnel Code from the received electronic statement.
		String workerCode = rftId0331.getWorkerCode();

		//#CM720751
		// Obtain the Consignor code from the Received electronic statement.
		String consignorCode = rftId0331.getConsignorCode();

		//#CM720752
		// Obtain the Planned Picking Date from the received electronic statement.
		String planDate = rftId0331.getPickingPlanDate();

		//#CM720753
		//Variable for maintaining the Order No.
		String[] orderNo = new String[4];

		//#CM720754
		//Obtain Order No. 1 using RFTId0331.
		orderNo[0] = rftId0331.getOrderNo_1();

		//#CM720755
		//Obtain Order No. 2 using RFTId0331.
		orderNo[1] = rftId0331.getOrderNo_2();

		//#CM720756
		//Obtain Order No. 3 using RFTId0331.
		orderNo[2] = rftId0331.getOrderNo_3();

		//#CM720757
		//Obtain Order No. 4 using RFTId0331.
		orderNo[3] = rftId0331.getOrderNo_4();

		//#CM720758
		//Obtain the Completion class.
		String completionFlag = rftId0331.getCompletionFlag();

		//#CM720759
		// Obtain the Result file name.
		String resultFileName;

		try
		{
			//#CM720760
			//Obtain the Work Time.
			int workTime = rftId0331.getWorkTime();
			
			//#CM720761
			//Obtain the file record count.
			int fileRecordNo = rftId0331.getFileRecord();
			
			//#CM720762
			//Obtain the Count of mis-scanning.
			int missCnt = rftId0331.getMissCount();
			
			//#CM720763
			// Obtain the Result file name.
			if (completionFlag.equals(RFTId0331.COMPLETION_FLAG_CANCEL))
			{
				//#CM720764
				// Use the contents of the sent file for the data with Reception division "Cancelled"
				String sendpath = WmsParam.RFTSEND; // wms/rft/send/
				resultFileName = sendpath + rftNo + "\\"
						+ Id5330DataFile.ANS_FILE_NAME;
			}
			else
			{
				resultFileName = rftId0331.getResultFileName();
				if (resultFileName.trim().equals(""))
				{			
						resultFileName = Id5330DataFile.restoreDataFile(rftNo, false, wConn);
				}
			}	
			
			//#CM720765
			// Generate BaseOperate instance
			className = "BaseOperate";
			BaseOperate baseOperate = (BaseOperate) PackageManager
					.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM720766
			//-----------------
			//#CM720767
			// Check Daily Maintenance Processing
			//#CM720768
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM720769
				// Return the Status flag as  "5: Daily Update Processing". 
				ansCode = RFTId5331.ANS_CODE_DAILY_UPDATING;

			}
			else
			{
				//#CM720770
				//-----------------
				//#CM720771
				// Check for presence of Result by Worker.
				//#CM720772
				//-----------------
				WorkerResult[] workerResult = baseOperate.getWorkerResult(
						WorkerResult.JOB_TYPE_RETRIEVAL, workerCode, rftNo);
				if (workerResult.length == 0)
				{
					//#CM720773
					// Create New Worker Result
					baseOperate.createWorkerResult(
							WorkerResult.JOB_TYPE_RETRIEVAL, workerCode, rftNo);
					//#CM720774
					// commit
					wConn.commit();
				}

				//#CM720775
				//Obtain the Box-change Order.
				int boxIndex = -1;
				try
				{
					boxIndex = Integer.parseInt(rftId0331.getBoxIndex());
				}
				catch (NumberFormatException e)
				{
				}

				//#CM720776
				// Generate the Id0331Operate instance.
				className = "Id0331Operate";
				Id0331Operate id0331Operate = (Id0331Operate) PackageManager
						.getObject("Id0331Operate");
				id0331Operate.setConnection(wConn);
				id0331Operate.setProcessName(RFTId0331.CLASS_NAME);

				ansCode = id0331Operate.doComplete(consignorCode, planDate,
						orderNo, boxIndex, rftNo, workerCode, completionFlag,
						resultFileName, fileRecordNo, workTime, missCnt);
				errorDetail = id0331Operate.getErrorDetail();
			}
			//#CM720777
			// For data file with Completion class other than "Cancel", store the work data history file.
			if (!completionFlag.equals(RFTId0331.COMPLETION_FLAG_CANCEL))
			{
				//#CM720778
				// Store the history of work data file.
				className = "Id5330DataFile";
				Id5330DataFile dataFile = (Id5330DataFile) PackageManager
						.getObject("Id5330DataFile");
				dataFile.setFileName(resultFileName);
				dataFile.saveHistoryFile();
			}
			
			if (ansCode.equals(RFTId5331.ANS_CODE_NORMAL)
					&& ! completionFlag.equals(RFTId0331.COMPLETION_FLAG_BOX_CHANGE))
			{
				//#CM720779
				// For data with Response flag "0: Normal" and no box-change, delete the processing data.
				RFTId5331.deleteWorkingData(rftNo, PROCESS_NAME,  wConn);

			}
			if (ansCode.equals(RFTId5331.ANS_CODE_NORMAL))
			{
				wConn.commit();
			}
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,
					className, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
	
			//#CM720780
			// Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode + " PlanDate:"
					+ planDate + " RftNo:" + rftNo + " WorkerCode:"
					+ workerCode + "]";
			//#CM720781
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME,
					errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}

			//#CM720782
			// Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.NULL;
		}
		catch (ReadWriteException e)
		{
			//#CM720783
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			//#CM720784
			// Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (DataExistsException e)
		{
			//#CM720785
			// 6006002=Database error occurred.{0}
			RftLogMessage.print(6006002, LogMessage.F_ERROR, CLASS_NAME,
					className, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}

			//#CM720786
			// Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		catch (SQLException e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}

			//#CM720787
			// Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IOException e)
		{
			//#CM720788
			// Output the log.
			//#CM720789
			// 6006020=File I/O error occurred. {0}
			RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			errorDetail = RFTId5331.ErrorDetails.I_O_ERROR;
			ansCode = RFTId5331.ANS_CODE_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM720790
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errorDetail = RFTId5331.ErrorDetails.PARAMETER_ERROR;
			ansCode = RFTId5331.ANS_CODE_ERROR;

		}
		catch(NumberFormatException e)
		{
			//#CM720791
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);		
			}
			//#CM720792
			//Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.PARAMETER_ERROR;
			
		}
		catch (Exception e)
		{
			//#CM720793
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);		
			}
			//#CM720794
			//Response flag: Error
			ansCode = RFTId5331.ANS_CODE_ERROR;
			errorDetail = RFTId5331.ErrorDetails.INTERNAL_ERROR;
		}


		//#CM720795
		// Generate Response Electronic Statement.
		//#CM720796
		//STX
		rftId5331.setSTX();
		//#CM720797
		//SEQ
		rftId5331.setSEQ(0);
		//#CM720798
		//ID
		rftId5331.setID(RFTId5331.ID);
		//#CM720799
		//Period for sending by Handy
		rftId5331.setRftSendDate(rftId0331.getRftSendDate());
		//#CM720800
		//Period for sending by SERVER
		rftId5331.setServSendDate();
		//#CM720801
		//RFT Machine
		rftId5331.setRftNo(rftId0331.getRftNo());
		//#CM720802
		//Personnel Code
		rftId5331.setWorkerCode(rftId0331.getWorkerCode());
		//#CM720803
		//Response flag
		rftId5331.setAnsCode(ansCode);
		//#CM720804
		//Details of error
		rftId5331.setErrDetails(errorDetail);
		//#CM720805
		//ETX
		rftId5331.setETX();

		//#CM720806
		//Generate electronic statement
		rftId5331.getSendMessage(sdt);


	}
	//#CM720807
	//Package methods -----------------------------------------------

	//#CM720808
	//Protected methods ---------------------------------------------

	//#CM720809
	//Private methods -----------------------------------------------
}
//#CM720810
//end of class

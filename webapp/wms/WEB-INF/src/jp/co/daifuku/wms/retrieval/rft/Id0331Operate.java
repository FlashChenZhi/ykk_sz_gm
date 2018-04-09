//#CM720681
//$Id: Id0331Operate.java,v 1.3 2007/02/07 04:19:40 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;
//#CM720682
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;


//#CM720683
/**
 * Designer : E.Takeda<BR>
 * Maker :   E.Takeda<BR>
 * <BR>
 * Execute the process for sending Order Picking Result data from RFT.<BR>
 * Provide two processes:  Commit and Cancel .<BR>
 * Implement the business logic to be invoked from Id0331Process.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/08</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:40 $
 * @author  $Author: suresh $
 */
public class Id0331Operate extends IdOperate
{
	//#CM720684
	//  Class variables -----------------------------------------------
	//#CM720685
	/**
	 * Process name(for Name of Add Process and Name of Last update process)
	 */
	protected static final String PROCESS_NAME = "ID0331";

	//#CM720686
	/**
	 * Name of process for starting work (for Name of Add Process and Name of the Last update process)
	 */
	protected static final String START_PROCESS_NAME = "ID0330";

	//#CM720687
	/**
	 * Class Name (for output the log)
	 */
	protected static final String CLASS_NAME = "Id0331Operate";

	//#CM720688
	/**
	 * Process name(for Name of Add Process and Name of Last update process)
	 */
	protected String processName = "";

	//#CM720689
	/**
	 * Variable for maintaining the detail of the error.
	 */
	private String errorDetail = RFTId5331.ErrorDetails.NORMAL;

	//#CM720690
	// Class method --------------------------------------------------
	//#CM720691
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:40 $";
	}

	//#CM720692
	// Constructors --------------------------------------------------
	//#CM720693
	// Public methods ------------------------------------------------	


	//#CM720694
	/**
	 * Set the Name of Process for update.
	 * 
	 * @param name		Name of Process for updating
	 */
	public void setProcessName(String name)
	{
		this.processName = name;
	}

	//#CM720695
	/**
	 * Process for Order Picking result data<BR>
	 * Execute the following process for submitting the Work Status.<BR>
	 *  <UL>
	 *   <LI>Process for completing Order Picking </LI>
	 *   <OL>
	 *    <LI>Lock the target Order data to update, and obtain the list of the Picking Plan unique key of the data.<BR>
	 *        Target the following tables for update: Work Status and Picking Plan Info
	 *       ({@link #lockUpdateDataForOrderRetrieval(WorkingInformation [] ,String,String,boolean) lockUpdateDataForOrderRetrieval()})</LI>
	 *    <LI>Update the work status to "Completed", and 
	 *         generate the related Picking Plan Info, Sending Result Information, and inventory information, and update the data.
	 *       ({@link #updateWorkingInformationForInPart(String,String,WorkingInformation [] ) updateWorkingInformationForInPart()})<BR>
	 *        Renumber the Order Split No. before updating the work status to "Completed".
	 *       ({@link #getNextOrderSeqNo(String,String,String) getNextOrderSeqNo()})<BR>
	 *    <LI>Based on the updated Picking Plan unique key of work status, 
	 *        update the Status flag of the Picking Plan Info.
	 *       ({@link #updateCompletionStatus(String [] ) updateCompletionStatus()})</LI>
	 *   </OL>
	 * 
	 *   <LI>Process for cancel</LI>
	 *   <OL>
	 *    <LI>Lock the target data to update.<BR>
	 *        Target the following tables for update: Work Status and Picking Plan Info<BR>
	 *        Update the corresponding data with status "Processing" in the Work Status, to "Standby".<BR>
	 *        the Status flag of the Picking Plan Info to the appropriate status based on the Picking Plan unique key of the updated Work Status.
	 *      ({@link #cancelRetrieval(WorkingInformation [] ,String,String) cancelRetrieval()})</LI>
	 *   </OL>
	 * 
	 *   <LI>Process for Box-change</LI>
	 *   <OL>
	 *    <LI>Lock the target Order data to update, and obtain the list of the Picking Plan unique key of the data.<BR>
	 *        Target the following tables for update: Work Status and Picking Plan Info
	 *       ({@link #lockUpdateDataForOrderRetrieval(WorkingInformation [] ,String,String,boolean) lockUpdateDataForOrderRetrieval()})</LI>
	 *    <LI>Update the work status to "Completed", and 
	 *         generate the related Work Status and Picking Plan Info, Sending Result Information, and inventory information, and update the data.<BR>
	 *        If the instructed qty is larger than the Result qty, create a new work status for the work not executed Picking.
	 *       ({@link #updateWorkingInformationByChangedBox(String,String,String,WorkingInformation [] ,String) updateWorkingInformationByChangedBox()})</LI>
	 *        Renumber the Order Split No. before updating the work status to "Completed".
	 *       ({@link #getNextOrderSeqNo(String,String,String) getNextOrderSeqNo()})<BR>
	 *    <LI>Based on the updated Picking Plan unique key of work status, 
	 *        update the Status flag of the Picking Plan Info.
	 *       ({@link #updateCompletionStatus(String [] ) updateCompletionStatus()})</LI>
	 *   </OL>
	 *  </UL>
	 * <BR>
	 * @param	consignorCode	Consignor Code
	 * @param	planDate		Planned Picking Date
	 * @param	orderNo			Order No. list
	 * @param	workForm		Work Type
	 * @param  operationTime   Operation time
	 * @param  orderIndex      Index of Box-change Order
	 * @param	rftNo			RFT No.
	 * @param	workerCode		Worker Code
	 * @param	completionFlag	"Complete" flag
	 * @param	resultFileName	Order Picking Result file name
	 * @param	fileRecordNo	Number of records in a file.
	 * @param	workTime 		Work Time
	 * @param	missCnt			Count of mis-scanning
	 */
	public String doComplete(String consignorCode, String planDate,
			String[] orderNo, int orderIndex, String rftNo, String workerCode,
			String completionFlag, String resultFileName, int fileRecordNo, int workTime, int missCnt)
	{
		try
		{
			//#CM720696
			// Obtain the data info.
			Id5330DataFile id5330DataFile = (Id5330DataFile) PackageManager
					.getObject("Id5330DataFile");

			id5330DataFile.setFileName(resultFileName);
			WorkingInformation[] resultData = (WorkingInformation[]) id5330DataFile
					.getCompletionData();
			
			//#CM720697
			/* 2006/6/28 v2.6.0 START K.Mukai It was revised to disable to check if cancelled. */

			if (!completionFlag.equals(RFTId0331.COMPLETION_FLAG_CANCEL))
			{
				//#CM720698
				/* 2006/6/20 v2.6.0 START K.Mukai Checking for prohibited characters used in Expiry Date was added. */

				for (int i = 0; i < resultData.length; i ++)
				{
					if(DisplayText.isPatternMatching(resultData[i].getResultUseByDate()))
					{
						throw new InvalidDefineException("USE_BY_DATE[" + resultData[i].getResultUseByDate() +"]");
					}
				}
				//#CM720699
				/* 2006/6/20 END */

			}
			//#CM720700
			/* 2006/6/28 END */

						
			//#CM720701
			// Generate the RetrievalOperate class instance.
			RetrievalOperate retrievalOperate =
			    (RetrievalOperate) PackageManager.getObject("RetrievalOperate");
			retrievalOperate.initialize(wConn);
			retrievalOperate.setProcessName(PROCESS_NAME);
			retrievalOperate.setStartProcessName(START_PROCESS_NAME);

			for (int i = 0; i < resultData.length; i++)
			{
				resultData[i].setConsignorCode(consignorCode);
				resultData[i].setPlanDate(planDate);
			}

			//#CM720702
			// If Reception division is "9: Cancel":
			if (completionFlag.equals(RFTId0331.COMPLETION_FLAG_CANCEL))
			{
				//#CM720703
				// Update the status flag.
				retrievalOperate.cancelRetrieval(resultData, workerCode, rftNo);
			}
			//#CM720704
			// Data with Reception division other than " Cancel"
			else
			{
				//#CM720705
				// Check the number of records in a file.
				if(fileRecordNo != resultData.length)
				{			
					throw new IOException();
				}

				//#CM720706
				// Include Consignor Code and Planned date in the Result data.
				for (int i = 0; i < resultData.length; i++)
				{
					resultData[i].setConsignorCode(consignorCode);
					resultData[i].setPlanDate(planDate);
				}

				//#CM720707
				// For data with Normal or Shortage, execute the process for completing.
				if (completionFlag.equals(RFTId0331.COMPLETION_FLAG_DECISION))
				{
					retrievalOperate.completeRetrievalForOrderRetrieval(resultData, rftNo,
							workerCode, completionFlag, workTime, missCnt);
				}
				else
				{
					//#CM720708
					// Execute the process for Box-change.
					retrievalOperate.changeBox(orderNo[orderIndex - 1], completionFlag, resultData, rftNo,
							workerCode, resultFileName, workTime, missCnt);
				}
			}

		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,
					"Id0330DataFile", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
				
			}

			//#CM720709
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (IOException e)
		{
			//#CM720710
			// 6006020=File I/O error occurred. {0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME,
					resultFileName);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);				
			}

			//#CM720711
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.I_O_ERROR;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (InvalidStatusException e)
		{
			//#CM720712
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

			//#CM720713
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM720714
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
			//#CM720715
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode + " PlanDate:"
					+ planDate + " RftNo:" + rftNo + " WorkerCode:"
					+ workerCode + "]";
			//#CM720716
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
			//#CM720717
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.NULL;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM720718
			// 6026022=Blank or prohibited character is included in the specified value. {0}
			//#CM720719
			/* 2006/6/20 v2.6.0 START K.Mukai Checking for prohibited characters used in Expiry Date was added. */

			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			//#CM720720
			/* 2006/6/20 END */

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}

			//#CM720721
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.PARAMETER_ERROR;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (DataExistsException e)
		{
			//#CM720722
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
			errorDetail = RFTId5330.ErrorDetails.DB_UNIQUE_KEY_ERROR;
			return RFTId5331.ANS_CODE_ERROR;
		}
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + consignorCode + " PlanDate:"
					+ planDate + " RftNo:" + rftNo + " WorkerCode:"
					+ workerCode + "]";
			//#CM720723
			// 6026017=Cannot update. The data you try to update was updated in other process. {0}
			RftLogMessage.print(6026017, LogMessage.F_ERROR, CLASS_NAME,
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
			//#CM720724
			// Response flag: Error
			errorDetail = RFTId5331.ErrorDetails.UPDATE_FINISH;
			return RFTId5331.ANS_CODE_UPDATE_FINISH;
		}
		catch (LockTimeOutException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			//#CM720725
			// Response flag: Error
			return RFTId5331.ANS_CODE_MAINTENANCE;
		}

		return RFTId5331.ANS_CODE_NORMAL;
	}

	//#CM720726
	/**
	 * Based on the updated Picking Plan unique key of work status, update the Status flag of the Picking Plan Info.<BR>
	 * <DIR>
	 *     (search conditions) 
	 *    <UL type="disc">
	 *     <LI>Picking Plan unique key = Work Status.Plan unique key  (One Shipping Plan Info corresponds the work status) </LI>
	 *     <LI>Status flag != "Deleted"</LI>
	 *    </UL>
	 *     (Content to be updated) 
	 *    <TABLE border="1">
	 *      <TR><TD>Status flag</TD>		<TD>Depend on Work Status.</TD></TR>
	 *      <TR><TD>Last update process name</TD>	<TD>"ID0331"</TD></TR>
	 *    </TABLE><BR>
	 *   Update the Status flag.<BR>
	 *   If all the work completed, update the data to "Completed".<BR>
	 *   If there is one or more processing work, regard the data as "Processing".<BR>
	 *   Otherwise, update the status to "Partially Completed". <BR>
	 * </DIR>
	 * 
	 * @param	planUkey				Picking Plan unique key list
	 * @throws ReadWriteException		Generate a database error.
	 * @throws NotFoundException
	 * @throws InvalidDefineException
	 */
	protected void updateCompletionStatus(String[] planUkey)
			throws ReadWriteException, NotFoundException,
			InvalidDefineException
	{
		for (int i = 0; i < planUkey.length; i++)
		{
			WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
			skey.setPlanUkey(planUkey[i]);

			WorkingInformation[] workinfo;
			WorkingInformationHandler wHandler = new WorkingInformationHandler(
					wConn);
			workinfo = (WorkingInformation[]) wHandler.find(skey);

			RetrievalPlanHandler sHandler = new RetrievalPlanHandler(wConn);
			RetrievalPlanAlterKey akey = new RetrievalPlanAlterKey();
			akey.setRetrievalPlanUkey(planUkey[i]);
			akey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

			boolean existCompleteData = false;
			boolean existUnstartData = false;
			boolean existWorkingData = false;
			for (int j = 0; j < workinfo.length; j++)
			{
				String status = workinfo[j].getStatusFlag();
				if (status.equals(WorkingInformation.STATUS_FLAG_COMPLETION))
				{
					existCompleteData = true;
				}
				else if (status.equals(WorkingInformation.STATUS_FLAG_UNSTART))
				{
					existUnstartData = true;
				}
				else
				{
					existWorkingData = true;
					break;
				}
			}
			if (existWorkingData || (!existCompleteData && !existUnstartData))
			{
				//#CM720727
				// If there is one or more data with status "Processing" or if there is no data with "Completed" or "Standby", 
				//#CM720728
				// disable to update the Status flag.
				//#CM720729
				//  (Generally, such case is impossible) 
				continue;
			}
			if (existCompleteData)
			{
				if (existUnstartData)
				{
					//#CM720730
					// Update the data with both "Completed" and "Standby" status, to "Partially Completed".
					akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
				}
				else
				{
					//#CM720731
					// If the status of all the existing data is "Completed", update to "Completed".
					akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
				}
			}
			else
			{
				//#CM720732
				// If the status of all the existing data is "Standby", update it to "Standby".
				akey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			akey.updateLastUpdatePname(processName);
			sHandler.modify(akey);
		}
	}


	//#CM720733
	/**
	 * Obtain the details of the error.
	 * @return
	 */
	public String getErrorDetail()
	{
		return errorDetail;
	}

}

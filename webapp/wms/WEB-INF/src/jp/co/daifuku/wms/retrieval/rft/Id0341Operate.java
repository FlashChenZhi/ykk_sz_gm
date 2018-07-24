// $Id: Id0341Operate.java,v 1.3 2007/02/07 04:19:41 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.UpdateByOtherTerminalException;
import jp.co.daifuku.wms.instockreceive.rft.RFTId5133;
//#CM720940
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * Execute the process for sending Item Picking Result data from RFT.<BR>
 * Available processes are Commit, Cancel, and Cancel/Request Next Data.<BR>
 * Implement the business logic to be invoked from Id0037Process.<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:41 $
 * @author  $Author: suresh $
 */
public class Id0341Operate extends IdOperate
{
	//#CM720941
	// Class variables -----------------------------------------------
	//#CM720942
	/**
	 * Process name(for Name of Add Process and Name of Last update process)
	 */
	protected static final String PROCESS_NAME = "ID0341";

	//#CM720943
	/**
	 * Name of process for starting work (for Name of Add Process and Name of the Last update process)
	 */
	protected static final String START_PROCESS_NAME = "ID0340";

	//#CM720944
	/**
	 * Class Name (for output the log)
	 */
	protected static final String CLASS_NAME ="Id0341Operate";
	
	//#CM720945
	/**
	 * Details of error
	 */
	private String errDetails = RFTId5133.ErrorDetails.NORMAL;
	//#CM720946
	// Class method --------------------------------------------------
	//#CM720947
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:41 $";
	}
	//#CM720948
	// Constructors --------------------------------------------------
	//#CM720949
	/**
	 * Generate an instance.
	 */

	//#CM720950
	// Public methods ------------------------------------------------
	//#CM720951
	/**
	 * Process for Item Picking result data<BR>
	 * Execute the following process for submitting the Work Status.<BR>
	 *    <UL>
	 *     <LI>Process for completing Item Picking</LI>
	 *     <LI>Process for cancel</LI>
	 *    </UL>
	 * <BR>
	 * @param	resultData		Picking Work Result info (Array of Work Status Entity)
	 * @param	completionFlag	"Complete" flag
	 * @return	If completed normally, return RFTId5341.ANS_CODE_NORMAL.<BR>
	 * 			Return RFTId5341.ANS_CODE_NULL if next data is not found (for Commit or Cancel/Request Next Data)<BR>
	 * 			Return RFTId5341.ANS_CODE_MAINTENANCE when DB lock time-out occurs.<BR>
	 * 			Return RFTId5341.ANS_CODE_ERROR when error occurs in process.<BR>
	 */
	public String doComplete(
		WorkingInformation resultData,
		String completionFlag,
		int workTime,
		int missScanCnt)
	{
		try
		{
		    WorkingInformation[] workinfo = {resultData};
		    
			//#CM720952
			// Generate the RetrievalOperate class instance.
			RetrievalOperate retrievalOperate = (RetrievalOperate) PackageManager.getObject("RetrievalOperate");
			retrievalOperate.initialize(wConn);
			retrievalOperate.setProcessName(PROCESS_NAME);
			retrievalOperate.setStartProcessName(START_PROCESS_NAME);

			//#CM720953
			// For data file with Completion class "9: Cancel":
			if (completionFlag.equals(RFTId0341.COMPLETION_FLAG_CANCEL))
			{
				//#CM720954
				// Update the status flag.
				retrievalOperate.cancelRetrieval(
				        workinfo,
				        resultData.getWorkerCode(),
						resultData.getTerminalNo());
			}
			//#CM720955
			// For data file with Completion class other than 8 or 9:
			else
			{
				//#CM720956
				// Execute the process for completing the Item Picking.
				retrievalOperate.completeRetrieval(
				        workinfo,
				        resultData.getTerminalNo(),
				        resultData.getWorkerCode(),
				        completionFlag,
				        workTime,
				        missScanCnt);
			}

		}
		//#CM720957
		// If no search info found:
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" PlanDate:" + resultData.getPlanDate() +
							" LocationNo:" + resultData.getLocationNo() +
							" ItemCode:" + resultData.getItemCode() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
			//#CM720958
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM720959
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.NULL;
			return RFTId5341.ANS_CODE_ERROR ;
		}
		//#CM720960
		// If the Update target data has been updated via other terminal:
		catch (UpdateByOtherTerminalException e)
		{
			String errData = "[ConsignorCode:" + resultData.getConsignorCode() +
							" PlanDate:" + resultData.getPlanDate() +
							" LocationNo:" + resultData.getLocationNo() +
							" ItemCode:" + resultData.getItemCode() +
							" RftNo:" + resultData.getTerminalNo() +
							" WorkerCode:" + resultData.getWorkerCode() +"]";
			//#CM720961
			// 6026017=Cannot update. The data you try to update was updated in other process. {0}
			RftLogMessage.print(6026017, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM720962
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.UPDATE_FINISH;
			return RFTId5341.ANS_CODE_ERROR;
		}
		//#CM720963
		// In the event of time-out on the access to DB (Allocation in Process):
		catch (LockTimeOutException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM720964
			// Response flag: Maintenance in process via other terminal
			return RFTId5341.ANS_CODE_MAINTENANCE;
		}
		//#CM720965
		// In the event of error in data access:
		catch (ReadWriteException e)
		{
			//#CM720966
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM720967
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.DB_ACCESS_ERROR;
			return RFTId5341.ANS_CODE_ERROR ;
		}
		catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM720968
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.INSTACIATE_ERROR;
			return RFTId5341.ANS_CODE_ERROR ;
        }
		catch (InvalidDefineException e)
        {
			//#CM720969
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
			//#CM720970
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.PARAMETER_ERROR;
			return RFTId5341.ANS_CODE_ERROR ;
        }
        catch (InvalidStatusException e)
        {
			//#CM720971
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
			//#CM720972
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.DB_INVALID_VALUE;
			return RFTId5341.ANS_CODE_ERROR ;
        }
        //#CM720973
        //If any other error occurs:
		catch (Exception e)
		{
			//#CM720974
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
			//#CM720975
			// Response flag: Error
			errDetails = RFTId5341.ErrorDetails.INTERNAL_ERROR;
			return RFTId5341.ANS_CODE_ERROR ;
		}
		
		return RFTId5341.ANS_CODE_NORMAL;
	}
	
	//#CM720976
	/**
	 * Obtain the details of error.
	 * 
	 * @return	Details of error
	 */
	public String getErrorDetails()
	{
		return errDetails;
	}

	//#CM720977
	// Package methods -----------------------------------------------

	//#CM720978
	// Protected methods ---------------------------------------------
	
	//#CM720979
	// Private methods -----------------------------------------------
}
//#CM720980
//end of class

// $Id: Id0741Process.java,v 1.2 2006/12/07 09:00:09 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM575914
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * Relocation storage result (ID0741) process from RFT<BR>
 * Using Id0741Operate class functions, update relocation work info and stock info.
 * It creates the response id to be send to RFT<BR>
 * <BR>
 * Relocation storage result data process (<CODE>processReceivedId</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Fetch the required info from received data<BR>
 *   Using ID0741Operate class functions, call update process for Relocation Work info, stock info and fetch response flag<BR>
 *   If Response flag is 0: Normal, update RFTWork info telegraph data to NULL<BR>
 *   [Update conditions]
 *   <UL><LI>RFTNo</LI></UL>
 *   [Update contents]
 *   <UL><LI>Response id:NULL</LI>
 *       <LI>Last update date/time:system date/time</LI>
 *       <LI>Last update Process name:ID0741</LI>
 *   </UL>
 *   Create telegraph send data and set the text values<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:09 $
 * @author  $Author: suresh $
 */
public class Id0741Process extends IdProcess
{
	//#CM575915
	// Class fields----------------------------------------------------
	
	//#CM575916
	// Class variables -----------------------------------------------
	//#CM575917
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0741Process";
	
	//#CM575918
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0741";

	//#CM575919
	// Class method --------------------------------------------------
	//#CM575920
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/12/07 09:00:09 $");
	}

	//#CM575921
	// Constructors --------------------------------------------------
	//#CM575922
	// Public methods ------------------------------------------------
	//#CM575923
	/**
	 * This is the Relocation storage result send (ID0741) process<BR>
	 * Fetch telegraph data as byte array and create corresponding (ID5741) byte string<BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		//#CM575924
		// Create instance to receive telegraph data
		RFTId0741 rftId0741 = null;

		//#CM575925
		// Create instance to send telegraph data
		RFTId5741 rftId5741 = null;
		
		try
		{
			//#CM575926
			// Create instance to receive telegraph data
			rftId0741 = (RFTId0741)PackageManager.getObject("RFTId0741");
			rftId0741.setReceiveMessage(rdt);

			//#CM575927
			// Create instance to send telegraph data
			rftId5741 = (RFTId5741)PackageManager.getObject("RFTId5741");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}
				
		//#CM575928
		// Fetch worker code from telegraph data
		String workerCode = rftId0741.getWorkerCode() ;

		//#CM575929
		// Fetch from RFT no. from telegraph data
		String rftNo = rftId0741.getRftNo() ;

		//#CM575930
		// Fetch Relocation origin area from telegraph data
		String sourceAreaNo = rftId0741.getSourceAreaNo();
		
		//#CM575931
		// Fetch origin location from telegraph data
		String sourceLocationNo = rftId0741.getSourceLocationNo() ;
		
		//#CM575932
		// Fetch consignor code from telegraph data
		String consignorCode = rftId0741.getConsignorCode() ;
		
		//#CM575933
		// Fetch JAN code from telegraph data
		String JANCode = rftId0741.getJANCode() ;

		//#CM575934
		// Fetch Expiry date from telegraph data
		String useByDate = rftId0741.getUseByDate();
		
		//#CM575935
		// Fetch destination area from telegraph data
		String destAreaNo = rftId0741.getDestAreaNo();
		
		//#CM575936
		// Fetch Destination location no. from telegraph data
		String destLocationNo = rftId0741.getDestLocationNo() ;
		
		//#CM575937
		// Fetch Relocation Job no. from telegraph data
		String moveJobNo = rftId0741.getMoveJobNo();
		
		//#CM575938
		// Fetch completion flag from telegraph data
		String completionFlag = rftId0741.getCompletionFlag() ;
		
		//#CM575939
		// Variable for response flag
		String ansCode = RFTId5741.ANS_CODE_NORMAL ;
		
		//#CM575940
		// Variable for error details
		String errDetails = RFTId5741.ErrorDetails.NORMAL;
		
		try
		{
			//#CM575941
			// Fetch Actual relocation storage result qty from telegraph data
			int movementInstoreResultQty = rftId0741.getMovementInstoreResultQty();
			
			//#CM575942
			// Fetch work time from telegraph data
			int workTime = rftId0741.getWorkSeconds();

			//#CM575943
			// Create Id0741Operate instance
			Id0741Operate id0741Operate = (Id0741Operate)PackageManager.getObject("Id0741Operate");
			id0741Operate.setConnection(wConn);

			ansCode = id0741Operate.doComplete(
							moveJobNo,
							workerCode,
							rftNo,
							sourceAreaNo,
							sourceLocationNo,
							consignorCode,
							JANCode,
							useByDate,
							movementInstoreResultQty,
							destAreaNo,
							destLocationNo,
							completionFlag,
							workTime);
			errDetails = id0741Operate.getErrorDetails();
			
			if (ansCode.equals(RFTId5741.ANS_CODE_NORMAL))
			{
			    //#CM575944
			    // If the response flag is normal, delete in process data
			    RFTId5741.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();	
			}	
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6006003, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5741.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM575945
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
			//#CM575946
			// Response flag : Error
			ansCode = RFTId5741.ANS_CODE_ERROR;
			errDetails = RFTId5741.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM575947
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
			//#CM575948
			// Response flag : Error
			ansCode = RFTId5741.ANS_CODE_ERROR;
			errDetails =RFTId5741.ErrorDetails.PARAMETER_ERROR; 
		}
		catch (SQLException e)
		{
			//#CM575949
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
			//#CM575950
			// Response flag : Error
			ansCode = RFTId5741.ANS_CODE_ERROR;
			errDetails = RFTId5741.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (NumberFormatException e)
		{
			//#CM575951
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
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5741.ErrorDetails.PARAMETER_ERROR;
		}
		catch (Exception e)
		{
			//#CM575952
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
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5741.ErrorDetails.INTERNAL_ERROR;			
		}

		//#CM575953
		// Create response id
		//#CM575954
		// STX
		rftId5741.setSTX() ;
		//#CM575955
		// SEQ
		rftId5741.setSEQ(0) ;
		//#CM575956
		// ID
		rftId5741.setID(RFTId5741.ID) ;
		//#CM575957
		// RFT send time
		rftId5741.setRftSendDate(rftId0741.getRftSendDate()) ;
		//#CM575958
		// Server send time
		rftId5741.setServSendDate() ;
		//#CM575959
		// RFT no.
		rftId5741.setRftNo(rftId0741.getRftNo()) ;

		//#CM575960
		// Worker code
		rftId5741.setWorkerCode(rftId0741.getWorkerCode()) ;
		
		//#CM575961
		// Response flag
		rftId5741.setAnsCode(ansCode) ;
		
		//#CM575962
		// Error details
		rftId5741.setErrDetails(errDetails);
		
		//#CM575963
		// ETX
		rftId5741.setETX() ;

		//#CM575964
		// Acquire response id
		rftId5741.getSendMessage(sdt) ;

	}

	//#CM575965
	// Package methods -----------------------------------------------

	//#CM575966
	// Protected methods ---------------------------------------------

	//#CM575967
	// Private methods -----------------------------------------------

}
//#CM575968
//end of class

// $Id: Id0731Process.java,v 1.2 2006/12/07 09:00:11 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId5631;

//#CM575516
/**
 * Designer : K.Shimizu <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * This is the Relocation picking result send (ID0731) process<BR>
 * The Id0731Operate class does Stock allocation process and creates relocation work info.
 * It creates the response id to be send to RFT<BR>
 * <BR>
 * Relocation picking result process (<CODE>processReceivedId</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Fetch the required info from received data<BR>
 *   Using ID0731Operate class function, call stock allocate, relocation work info create process and fetch Response flag<BR>
 *   Create telegraph send data and set the text values<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:11 $
 * @author  $Author: suresh $
 */
public class Id0731Process extends IdProcess
{
	//#CM575517
	// Class fields----------------------------------------------------

	//#CM575518
	// Class variables -----------------------------------------------
	//#CM575519
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0731Process";

	//#CM575520
	// Class method --------------------------------------------------
	//#CM575521
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/12/07 09:00:11 $");
	}

	//#CM575522
	// Constructors --------------------------------------------------
	//#CM575523
	// Public methods ------------------------------------------------
	//#CM575524
	/**
	 * This is the Relocation picking result (ID0731) process<BR>
	 * Search Work info using receive data, and set the result to send data (ID5731)<BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		//#CM575525
		// Create instance to receive telegraph data
		RFTId0731 rftId0731 = null;

		//#CM575526
		// Create instance to send telegraph data
		RFTId5731 rftId5731 = null;
		
		try
		{
			//#CM575527
			// Create instance to receive telegraph data
			rftId0731 = (RFTId0731)PackageManager.getObject("RFTId0731");
			rftId0731.setReceiveMessage(rdt);

			//#CM575528
			// Create instance to send telegraph data
			rftId5731 = (RFTId5731)PackageManager.getObject("RFTId5731");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM575529
		// Variable to store response flag
		String ansCode = RFTId5731.ANS_CODE_NORMAL ;
		
		//#CM575530
		// Variable to store error details
		String errDetails = RFTId5731.ErrorDetails.NORMAL;
		
		//#CM575531
		// Variable to store relocation picking possible qty
		int storageQty = 0;

		//#CM575532
		// Fetch from RFT no. from telegraph data
		String rftNo = rftId0731.getRftNo() ;
		
		//#CM575533
		// Fetch worker code from telegraph data
		String workerCode = rftId0731.getWorkerCode() ;

		//#CM575534
		// Fetch consignor code from telegraph data
		String consignorCode = rftId0731.getConsignorCode() ;
		
		//#CM575535
		// Fetch Consignor name from telegraph data
		String consignorName = rftId0731.getConsignorName() ;

		//#CM575536
		// Fetch Origin location no. from telegraph data
		String sourceLocationNo = rftId0731.getSourceLocationNo() ;

		//#CM575537
		// Fetch JAN code from telegraph data
		String JANCode = rftId0731.getJANCode() ;
		
		//#CM575538
		// Fetch Bundle ITF from telegraph data
		String bundleITF = rftId0731.getBundleITF() ;
		
		//#CM575539
		// Fetch Case ITF from telegraph data
		String itf = rftId0731.getITF() ;
		
		//#CM575540
		// Fetch Item name from telegraph data
		String itemName = rftId0731.getItemName() ;

		//#CM575541
		// Fetch Expiry date from telegraph data
		String useByDate = rftId0731.getUseByDate();


		try
		{
			if(DisplayText.isPatternMatching(rftId0731.getConsignorCode()))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE");				
			}

			if(DisplayText.isPatternMatching(rftId0731.getSourceLocationNo()))
			{
				throw new InvalidDefineException("LOCATION_NO");
			}
			if(DisplayText.isPatternMatching(JANCode))
			{
				throw new InvalidDefineException("JAN_CODE");
			}

			//#CM575542
			// Fetch Qty per bundle from telegraph data
			int bundleEnteringQty = rftId0731.getBundleEnteringQty();
			
			//#CM575543
			// Fetch Qty per case from telegraph data
			int enteringQty = rftId0731.getEnteringQty();
					
			//#CM575544
			// Fetch Relocation picking result qty from telegraph data
			int movementResultQty = rftId0731.getMovementResultQty() ;
			//#CM575545
			// Fetch work time from telegraph data
			int workSeconds = rftId0731.getWorkSeconds();

			//#CM575546
			// Create Id0731Operate instance
			Id0731Operate id0731Operate = (Id0731Operate)PackageManager.getObject("Id0731Operate");
			id0731Operate.setConnection(wConn);

			ansCode = id0731Operate.doComplete(
							workerCode,
							rftNo,
							consignorCode,
							consignorName,
							sourceLocationNo,
							JANCode,
							itemName,
							enteringQty,
							bundleEnteringQty,
							itf,
							bundleITF,
							useByDate,
							movementResultQty,
							workSeconds);
			errDetails = id0731Operate.getErrorDetails();
			storageQty = id0731Operate.getStorageQty();
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6006003, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NumberFormatException e)
		{
			//#CM575547
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.PARAMETER_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM575548
			// 6026022=Blank or prohibited character is included in the specified value. {0}
			RftLogMessage.printStackTrace(6026022, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.PARAMETER_ERROR;
		}

		catch (Exception e)
		{
			//#CM575549
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			ansCode = RFTId5731.ANS_CODE_ERROR;
			errDetails = RFTId5731.ErrorDetails.INTERNAL_ERROR;

		}

		//#CM575550
		// Create response id
		//#CM575551
		// STX
		rftId5731.setSTX() ;

		//#CM575552
		// SEQ
		rftId5731.setSEQ(0) ;

		//#CM575553
		// ID
		rftId5731.setID(RFTId5731.ID) ;

		//#CM575554
		// RFT send time
		rftId5731.setRftSendDate( rftId0731.getRftSendDate() ) ;

		//#CM575555
		// Server send time
		rftId5731.setServSendDate() ;

		//#CM575556
		// RFT no.
		rftId5731.setRftNo( rftId0731.getRftNo() ) ;

		//#CM575557
		// Worker code
		rftId5731.setWorkerCode( rftId0731.getWorkerCode() ) ;
		
		//#CM575558
		// Stock qty available for picking
		rftId5731.setStorageQty(storageQty);
		
		//#CM575559
		// Response flag
		rftId5731.setAnsCode( ansCode ) ;
		
		//#CM575560
		// Error details
		rftId5731.setErrDetails(errDetails) ;
		
		//#CM575561
		// ETX
		rftId5731.setETX() ;

		//#CM575562
		// Acquire response id
		rftId5731.getSendMessage( sdt ) ;

	}

	//#CM575563
	// Package methods -----------------------------------------------

	//#CM575564
	// Protected methods ---------------------------------------------

	//#CM575565
	// Private methods -----------------------------------------------

}
//#CM575566
//end of class

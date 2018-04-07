// $Id: Id0641Process.java,v 1.2 2006/09/27 03:00:38 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM9857
/**
 * Designer : K.Shimizu <BR>
 * Maker : E.Takeda<BR>
 * <BR>
 * Execute processing of Unplanned Picking Result send (ID0641) from RFT.<BR>
 * using the function offered by Id0641Operate class
 * Generate the response electronic statement to be sent to RFT.<BR>
 * <BR>
 * Unplanned picking result sending process(<CODE>processReceivedId()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   required info from the received data and store it in the stock entity.<BR>
 *   Invoke the Picking<BR>
 *   Generate a sending electronic statement and set the characters to be sent to the sending buffer.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/15</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:38 $
 * @author  $Author: suresh $
 */
public class Id0641Process extends IdProcess
{
	//#CM9858
	// Class fields----------------------------------------------------

	//#CM9859
	// Class variables -----------------------------------------------
	//#CM9860
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0641Process";

	//#CM9861
	// Class method --------------------------------------------------
	//#CM9862
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:38 $";
	}

	//#CM9863
	// Constructors --------------------------------------------------
	//#CM9864
	// Public methods ------------------------------------------------
	//#CM9865
	/**
	 * Execute the process for Unplanned Picking Result send (ID0153).
	 * Search and Work status from the received data and set the search result in the sending electronic statement.
	 * @param  rdt  Receiving buffer
	 * @param  sdt  sending  buffer
	 * @throws Exception Report all exceptions.
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//#CM9866
		// Generate instance for analyzing the received electronic statement.
		RFTId0641 rftId0641 = null;

		//#CM9867
		// Generate instance for generating a sending electronic statement
		RFTId5641 rftId5641 = null;
		
		//#CM9868
		// Generate Stock info Entity instance
		Stock stock = null;	
		
		try
		{
			//#CM9869
			// Generate instance for analyzing the received electronic statement.
			rftId0641 = (RFTId0641)PackageManager.getObject("RFTId0641");
			rftId0641.setReceiveMessage(rdt);

			//#CM9870
			// Generate instance for generating a sending electronic statement
			rftId5641 = (RFTId5641)PackageManager.getObject("RFTId5641");
			
			//#CM9871
			//Generate Stock info Entity instance
			 stock = new Stock();
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}
		
		//#CM9872
		//Variable to maintain the Response flag
		String ansCode = RFTId5641.ANS_CODE_NORMAL ;
		
		//#CM9873
		// Variable to maintain the detail of the error.
		String errDetails = RFTId5641.ErrorDetails.NORMAL;
		
		//#CM9874
		// Variable to maintain the picking possible Stock qty
		int storageQty = 0;

		//#CM9875
		// Obtain the RFT Machine No. from the received electronic statement.
		String rftNo = rftId0641.getRftNo() ;
		
		//#CM9876
		// Obtain the Worker code from the received electronic statement.
		String workerCode = rftId0641.getWorkerCode() ;

		//#CM9877
		// Obtain the Consignor code from the received electronic statement.
		String consignorCode = rftId0641.getConsignorCode() ;
		
		//#CM9878
		// Obtain the JAN code from the received electronic statement.
		String JANCode = rftId0641.getJANCode() ;
		
		//#CM9879
		// Obtain Case/Piece Division from the received electronic statement.
		String casePieceFlag = rftId0641.getCasePieceFlag();
	
		//#CM9880
		// Obtain the Consignor name from the received electronic statement.
		String consignorName = rftId0641.getConsignorName() ;
			
		//#CM9881
		// Obtain the relocation source Location No. from the received electronic statement.
		String locationNo = rftId0641.getLocationNo() ;
	
		//#CM9882
		// Obtain the item name from the received electronic statement.
		String itemName = rftId0641.getItemName() ;
		
		//#CM9883
		// Obtain the expiry date from the received electronic statement.
		String useByDate =rftId0641.getUseByDate();
		
		//#CM9884
		// Obtain the result expiry date from the received electronic statement.
		String resultUseByDate = rftId0641.getResultUseByDate();		
		
		try
		{
			if(DisplayText.isPatternMatching(consignorCode))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE[" + consignorCode +"]");
			}

			if(DisplayText.isPatternMatching(locationNo))
			{
				throw new InvalidDefineException("LOCATION_NO[" + locationNo +"]");
			}
			if(DisplayText.isPatternMatching(JANCode))
			{
				throw new InvalidDefineException("JAN_CODE[" + JANCode +"]");
			}
			if(DisplayText.isPatternMatching(resultUseByDate))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + resultUseByDate +"]");
			}

			//#CM9885
			// Obtain the packed qty per bundle from the received electronic statement.
			int bundleEnteringQty = rftId0641.getBundleEnteringQty();
		
			//#CM9886
			// Obtain the packed qty per case from the received electronic statement.
			int enteringQty = rftId0641.getEnteringQty();
		
			//#CM9887
			// Obtain the picking result qty from the received electronic statement.
			int resultQty = rftId0641.getResultQty();
			
			//#CM9888
			// Obtain the work time from the received electronic statement.
			int workSecond = rftId0641.getWorkSecond();
			
			//#CM9889
			// Set the Load Size for the stock.
			stock.setCasePieceFlag(casePieceFlag);
			//#CM9890
			// Set the Location No. for the stock.
			stock.setLocationNo(locationNo);
			//#CM9891
			// Set the item code for the stock.
			stock.setItemCode(JANCode);
			//#CM9892
			// Set the expiry date for the stock.
			stock.setUseByDate(useByDate);
			//#CM9893
			// Set the Consignor code for the stock.
			stock.setConsignorCode(consignorCode);
			
			//#CM9894
			// for no stock package
			//#CM9895
			// Set the Consignor name for the stock.
			stock.setConsignorName(consignorName);
			//#CM9896
			// Set the item name for the stock.
			stock.setItemName1(itemName);
			//#CM9897
			// Set the packed qty per bundle for the stock.
			stock.setBundleEnteringQty(bundleEnteringQty);
			//#CM9898
			// Set the packed qty per bundle for the stock.
			stock.setEnteringQty(enteringQty);

			//#CM9899
			// Generate instance of Id0153Operate.
			Id0641Operate id0641Operate = (Id0641Operate)PackageManager.getObject("Id0641Operate");
			id0641Operate.setConnection(wConn);

			ansCode = id0641Operate.doComplete(
							workerCode,
							rftNo,
							stock,
							resultQty,
							resultUseByDate,
							workSecond
							);
			errDetails = id0641Operate.getErrorDetails();
			if(ansCode.equals(RFTId5641.ANS_CODE_RETRIEVAL_OVER))
			{
				storageQty = id0641Operate.getStorageQty();
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

			errDetails = RFTId5641.ErrorDetails.INSTACIATE_ERROR;
			ansCode = RFTId5641.ANS_CODE_ERROR;
		}
		catch (NumberFormatException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);

			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9900
			// Response flag：error
			ansCode = RFTId5641.ANS_CODE_ERROR;
			errDetails = RFTId5641.ErrorDetails.PARAMETER_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM9901
			// 6026022=Blank or prohibited character is included in the specified value.{0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			ansCode = RFTId5641.ANS_CODE_ERROR;
			errDetails = RFTId5641.ErrorDetails.PARAMETER_ERROR;
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM9902
			// Response flag：error
			ansCode = RFTId5641.ANS_CODE_ERROR;
			errDetails = RFTId5641.ErrorDetails.INTERNAL_ERROR;

		}

		//#CM9903
		// Create the response electronic statement
		//#CM9904
		// STX
		rftId5641.setSTX() ;

		//#CM9905
		// SEQ
		rftId5641.setSEQ(0) ;

		//#CM9906
		// ID
		rftId5641.setID(RFTId5641.ID);

		//#CM9907
		// RFTTransmission time
		rftId5641.setRftSendDate( rftId0641.getRftSendDate() ) ;

		//#CM9908
		// SERVERTransmission time
		rftId5641.setServSendDate() ;

		//#CM9909
		// RFT machine
		rftId5641.setRftNo( rftId0641.getRftNo() ) ;

		//#CM9910
		// 
		rftId5641.setWorkerCode( rftId0641.getWorkerCode() ) ;
		
		//#CM9911
		// picking possible Stock qty
		rftId5641.setStorageQty( storageQty );
		
		//#CM9912
		// Response flag
		rftId5641.setAnsCode( ansCode ) ;
		
		//#CM9913
		// error detail
		rftId5641.setErrDetails(errDetails);
		
		//#CM9914
		// ETX
		rftId5641.setETX() ;

		//#CM9915
		// Obtain the response electronic statement.
		rftId5641.getSendMessage( sdt ) ;

	}

	//#CM9916
	// Package methods -----------------------------------------------

	//#CM9917
	// Protected methods ---------------------------------------------

	//#CM9918
	// Private methods -----------------------------------------------

}
//#CM9919
//end of class

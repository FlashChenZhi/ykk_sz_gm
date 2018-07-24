// $Id: Id0631Process.java,v 1.2 2006/09/27 03:00:38 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM9653
/**
 * Designer : K.Shimizu <BR>
 * Maker : K.Shimizu <BR>
 * <BR>
 * Execute processing of Unplanned storage result sending (ID0631) from RFT.<BR>
 * Update Id0631 using functions offered by Id0631Operate class.
 * Generate the response electronic statement to be sent to RFT.<BR>
 * <BR>
 * Unplanned storage result sending process (<CODE>processReceivedId ()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the required info from the received data.<BR>
 *   Invoke the inventory information update process using the feature of ID0631Operate class and obtain the response flag.<BR>
 *   Generate a sending electronic statement and set the characters to be sent to the sending buffer.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:38 $
 * @author  $Author: suresh $
 */
public class Id0631Process extends IdProcess
{
	//#CM9654
	// Class fields----------------------------------------------------
	
	//#CM9655
	// Class variables -----------------------------------------------
	//#CM9656
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0631Process";

	//#CM9657
	// Class method --------------------------------------------------
	//#CM9658
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:38 $";
	}

	//#CM9659
	// Constructors --------------------------------------------------
	//#CM9660
	// Public methods ------------------------------------------------
	//#CM9661
	/**
	 * Execute the process for Unplanned storage result sending.<BR>
	 * Using StockOperate
	 * @param  rdt  Receiving buffer
	 * @param  sdt  sending  buffer
	 * @throws Exception Report all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		//#CM9662
		// Generate instance for analyzing from the received electronic statement.
		RFTId0631 rftId0631 = null;

		//#CM9663
		// Generate instance for generating a sending electronic statement
		RFTId5631 rftId5631 = null;
		
		//#CM9664
		// Generate Stock info Entity instance
		Stock stock = new Stock();
		
		try
		{
			//#CM9665
			// Generate instance for analyzing from the received electronic statement.
			rftId0631 = (RFTId0631)PackageManager.getObject("RFTId0631");
			rftId0631.setReceiveMessage(rdt);

			//#CM9666
			// Generate instance for generating a sending electronic statement
			rftId5631 = (RFTId5631)PackageManager.getObject("RFTId5631");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}
		
		//#CM9667
		// Obtain the Worker code from the received electronic statement.
		String workerCode = rftId0631.getWorkerCode();
		
		//#CM9668
		// Obtain the RFT Machine No. from the received electronic statement.
		String rftNo = rftId0631.getRftNo();
		
		//#CM9669
		// Obtain the Consignor code from the received electronic statement.
		stock.setConsignorCode(rftId0631.getConsignorCode());
		
		//#CM9670
		// Obtain the Consignor name from the received electronic statement.
		stock.setConsignorName(rftId0631.getConsignorName());
		
		//#CM9671
		// Obtain the JAN code from the received electronic statement.
		stock.setItemCode(rftId0631.getJANCode());
		
		//#CM9672
		// Obtain Bundle ITF from received statement.
		stock.setBundleItf(rftId0631.getBundleITF());
		
		//#CM9673
		// Obtain the Case ITF from the received electronic statement.
		stock.setItf(rftId0631.getITF());
		
		//#CM9674
		// Obtain the item name from the received electronic statement.
		stock.setItemName1(rftId0631.getItemName());
		
		
		//#CM9675
		// Obtain the expiry date from the received electronic statement.
		stock.setUseByDate(rftId0631.getUseByDate());
		
		
		//#CM9676
		// Obtain the storage complete Location No. from the received electronic statement.
		stock.setLocationNo(rftId0631.getStorageCompletionLocationNo());
		
		//#CM9677
		// Obtain the Case/Piece division from received statement.
		stock.setCasePieceFlag(rftId0631.getCasePieceFlag());
		 
		//#CM9678
		// Variable maintaining Response flag
		String ansCode = "" ;
		
		//#CM9679
		// Variable to maintain the detail of the error.
		String errorDetail = RFTId5631.ErrorDetails.NORMAL;
		
		try
		{
			if(DisplayText.isPatternMatching(rftId0631.getConsignorCode()))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE[" + rftId0631.getConsignorCode() +"]");				
			}
			if(DisplayText.isPatternMatching(rftId0631.getAreaNo()))
			{
				throw new InvalidDefineException("AREA_NO[" + rftId0631.getAreaNo() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0631.getStorageCompletionLocationNo()))
			{
				throw new InvalidDefineException("LOCATION_NO[" + rftId0631.getStorageCompletionLocationNo() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0631.getJANCode()))
			{
				throw new InvalidDefineException("JAN_CODE[" + rftId0631.getJANCode() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0631.getUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + rftId0631.getUseByDate() +"]");
			}

			//#CM9680
			// Obtain the packed qty per bundle from the received electronic statement.
			stock.setBundleEnteringQty(Integer.parseInt(rftId0631.getBundleEnteringQty().trim()));
			
			//#CM9681
			// Obtain the packed qty per case from the received electronic statement.
			stock.setEnteringQty(Integer.parseInt(rftId0631.getEnteringQty().trim()));

			//#CM9682
			// Obtain the completed storage qty from the received electronic statement.
			stock.setStockQty(Integer.parseInt(rftId0631.getStorageCompletionQty()));

			//#CM9683
			// Obtain the work time from the received electronic statement.
			int workSeconds = rftId0631.getWorkSeconds();
			
			//#CM9684
			// Generate Id0151Operate instance
			Id0631Operate id0631Operate = (Id0631Operate) PackageManager.getObject("Id0631Operate");
			id0631Operate.setConnection(wConn);

			ansCode = id0631Operate.doComplete(
							workerCode,
							rftNo,
							stock,
							workSeconds);
			errorDetail = id0631Operate.getErrorDetails();
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
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM9685
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
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.PARAMETER_ERROR;
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
			ansCode = RFTId5631.ANS_CODE_ERROR;
			errorDetail = RFTId5631.ErrorDetails.INTERNAL_ERROR;
			
		}

		//#CM9686
		// Create the response electronic statement
		//#CM9687
		// STX
		rftId5631.setSTX() ;
		//#CM9688
		// SEQ
		rftId5631.setSEQ(0) ;
		//#CM9689
		// ID
		rftId5631.setID(RFTId5631.ID) ;
		//#CM9690
		// RFTTransmission time
		rftId5631.setRftSendDate(rftId0631.getRftSendDate()) ;
		//#CM9691
		// SERVERTransmission time
		rftId5631.setServSendDate() ;
		//#CM9692
		// RFT machine
		rftId5631.setRftNo(rftId0631.getRftNo()) ;

		//#CM9693
		// 
		rftId5631.setWorkerCode(rftId0631.getWorkerCode()) ;
		
		//#CM9694
		// Response flag
		rftId5631.setAnsCode(ansCode) ;
		
		//#CM9695
		// error detail
		rftId5631.setErrDetails(errorDetail);
		
		//#CM9696
		// ETX
		rftId5631.setETX() ;

		//#CM9697
		// Obtain the response electronic statement.
		rftId5631.getSendMessage(sdt) ;

	}

	//#CM9698
	// Package methods -----------------------------------------------

	//#CM9699
	// Protected methods ---------------------------------------------

	//#CM9700
	// Private methods -----------------------------------------------

}
//#CM9701
//end of class

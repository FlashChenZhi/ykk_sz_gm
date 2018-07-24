// $Id: Id0911Process.java,v 1.2 2006/09/27 03:00:37 suresh Exp $
package jp.co.daifuku.wms.stockcontrol.rft;

import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM9976
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Execute processing of inventory information inquiry from RFT.
 * using the function offered by Id0911Operate class
 * Generate the response electronic statement to be sent to RFT.<BR>
 * <BR>
 * inventory information inquiry process (<CODE>processReceivedId ()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Obtain the work condition from the received data.<BR>
 *  Check for presence of stockcontrolPackage from the system info.<BR>
 *  If stockcontrol package is available search Stock info by the function offered by the Id0911Operate class and obtain Unplanned picking possible data.<BR>
 *  Generate a sending electronic statement and set the characters to be sent to the sending buffer.<BR>
 *  In the case there is available stockcontrolPackage, expiry date is not designeted and corrensponds with multiple expiry date<BR>
 * Set 7 (multiple corresponding data) in the response flag and generate the list file.
 * In the case stockcontrolPackage is not available, do not execute stock search.<BR>
 * Create the sending electronic statement on the information of stock with  blank 0.
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/27 03:00:37 $
 * @author  $Author: suresh $
 */
public class Id0911Process extends IdProcess
{
	//#CM9977
	// Class fields----------------------------------------------------
	//#CM9978
	/**
	 * Class Name
	 */
	private static final String CLASS_NAME = "Id0911Process";

	//#CM9979
	/**
	 * Field that represents stock expiry date list file name
	 */
	private static final String TABLE_FILE_NAME = "ID5911.txt";

	//#CM9980
	// Class variables -----------------------------------------------

	//#CM9981
	// Class method --------------------------------------------------
	//#CM9982
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/09/27 03:00:37 $";
	}

	//#CM9983
	// Constructors --------------------------------------------------
	//#CM9984
	// Public methods ------------------------------------------------
	//#CM9985
	/**
	 * Execute inventory information inquiry process (ID0911).<BR>
	 * Receive electronic statement as byte array and creates the responce  (ID5911) in byte array.<BR>
	 * <BR>
	 * Check if it is not in daily process
	 * Return the error "5: daily process" when daily update is in process.<BR>
	 * using WareNaviSystem class<BR>
	 * when stockcontrolPackage is available<BR>
	 * If one corresponding stock for possible picking exits
	 * Set the "0: Stock available for possible picking " for the response flag.<BR>
	 * Generate the expiry date list file and set "7: multiple data corresponding " in the response flag if any two or more stock possible for picking.<BR>
	 * If no stock possible for picking, set 8: No picking possible stock" to the response flag<BR>
	 * If stockcontrolPackage is not available, set the response flag to "3: No stockcontrol"<BR>
	 * Generate sending electronic statement and set the characters to be sent to the sending buffer.<BR>
	 * <BR>
	 * @param  rdt  Receiving buffer
	 * @param  sdt  sending  buffer
	 * @throws Exception Report all exceptions.
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		RFTId0911 rftId0911 = null;
		RFTId5911 rftId5911 = null;
		try
		{
			//#CM9986
			// Generate instance for analyzing the received electronic statement.
			rftId0911 = (RFTId0911) PackageManager.getObject("RFTId0911");
			rftId0911.setReceiveMessage(rdt);
			//#CM9987
			// Generate instance for generating a sending electronic statement
			rftId5911 = (RFTId5911) PackageManager.getObject("RFTId5911");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM9988
		// Obtain the RFT machine from the received electronic statement.
		String rftNo = rftId0911.getRftNo();

		//#CM9989
		// Obtain the Worker code from the received electronic statement.
		String workerCode = rftId0911.getWorkerCode();

		//#CM9990
		// Obtain the Consignor code from the received electronic statement.
		String consignorCode = rftId0911.getConsignorCode();
		
		//#CM9991
		// Obtain the Area No. from the received electronic statement.
		String areaNo = rftId0911.getAreaNo();
		
		//#CM9992
		// Obtain the Location No. from the received electronic statement.
		String locationNo = rftId0911.getLocationNo();
		
		//#CM9993
		// Obtain the Case/Piece Division from received statement（Case/Piece Division）
		String casePieceFlag =rftId0911.getCasePieceFlag();
		
		//#CM9994
		// Obtain the scan code1 from the received electronic statement.	
		String scanCode1 = rftId0911.getScanCode1();
		
		//#CM9995
		// Obtain the scan code2 from the received electronic statement.
		String scanCode2 = rftId0911.getScanCode2();
				
		//#CM9996
		// Obtain the list select flag from the received electronic statement.
		String listSelectionFlag = rftId0911.getListSelectionFlag();

		//#CM9997
		// Obtain the expiry date from the received electronic statement.
		String useByData = rftId0911.getUseByDate();

		//#CM9998
		// Variable to maintain the Consignor Name.
		String consignorName = "";
		
		//#CM9999
		// Variable to maintain the Item ID Code.
		String itemID = "";

		//#CM10000
		// Variable to maintain the item code.
		String itemCode = "" ;
		
		//#CM10001
		// Variable to maintain the JAN code.
		String JANCode = "";

		//#CM10002
		// Variable to maintain the Bundle ITF.
		String bundleITF = "";

		//#CM10003
		// Variable to maintain the Case ITF.
		String ITF = "";

		//#CM10004
		// Variable to maintain the item name.
		String itemName = "";
		
		//#CM10005
		// Variable to maintain the packed qty per bundle.
		int bundleEnteringQty = 0;

		//#CM10006
		// Variable to maintain the packed qty per case.
		int enteringQty = 0;
		
		//#CM10007
		// Variable to maintain the possible picking qty.
		int stockQty = 0;	
		
		//#CM10008
		// Variable to maintain the sending file name.
		String sendFileName = "";
		
		//#CM10009
		// Variable to maintain the file record count.
		int fileRecordNo = 0;

		//#CM10010
		// Variable to maintain the Response flag.
		String ansCode = "";
		
		//#CM10011
		// Variable to maintain the detail of the error.
		String errorDetail = RFTId5911.ErrorDetails.NORMAL;

		//#CM10012
		// Variable to maintain the list file name.
		String saveFileName = "";

		try
		{
			if(DisplayText.isPatternMatching(locationNo))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(areaNo))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(scanCode1))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(scanCode2))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}

			//#CM10013
			// Generate BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM10014
			//-----------------
			//#CM10015
			// Check if in daily process
			//#CM10016
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM10017
				// 5: Return status flag daily update under process
				ansCode = RFTId5911.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//#CM10018
				// Generate WareNaviSystemHandler instance
				WareNaviSystemHandler wareNaviSystemHandler = new WareNaviSystemHandler(wConn);

				//#CM10019
				//	Search stockcontrolPackage
				WareNaviSystem[] wareNaviSystem =
					(WareNaviSystem[]) wareNaviSystemHandler.find(new WareNaviSystemSearchKey());

				//#CM10020
				//When stockcontrol exists
				if (wareNaviSystem[0].getStockPack().equals("0"))
				{
					//#CM10021
					// Set the scan item code (1) for the jan code.
					JANCode = scanCode1;
					//#CM10022
					//Response flag：No stockcontrol
					ansCode = RFTId5911.ANS_CODE_NO_STOCK_PACK;
				}
				//#CM10023
				//When stockcontrol not exists.
				else
				{
					//#CM10024
					// Generate Id0152Operate instance
					Id0911Operate id0911Operate = (Id0911Operate)PackageManager.getObject("Id0911Operate");
					id0911Operate.setConnection(wConn);

					//#CM10025
					// Search the Stock info and obtain it.
					Stock[] stock = null;	
					stock=id0911Operate.getDeliverableStockData(
										consignorCode,		// 荷主コード
										areaNo,				// エリアNo.
										locationNo,			// ロケーションNo.
										casePieceFlag,		// ｹｰｽ・ﾋﾟｰｽ区分
										scanCode1,			// スキャンコード1
										scanCode2,			// スキャンコード2
										listSelectionFlag,	// 一覧選択フラグ
										useByData			// 賞味期限
										);	
						
					//#CM10026
					// Set the JAN code.
					JANCode = stock[0].getItemCode();
					//#CM10027
					// Set the Consignor Name.
					consignorName = stock[0].getConsignorName();
					//#CM10028
					// Set the item distinct code.
					itemID = stock[0].getStockId();				
					//#CM10029
					// Set the Bundle ITF.
					bundleITF = stock[0].getBundleItf();
					//#CM10030
					// Set the Case ITF.
					ITF = stock[0].getItf();
					//#CM10031
					// Set the item name.
					itemName = stock[0].getItemName1();
					//#CM10032
					// Set the packed qty per bundle.
					bundleEnteringQty = stock[0].getBundleEnteringQty();
					//#CM10033
					// Set the packed qty per case.
					enteringQty = stock[0].getEnteringQty();
					//#CM10034
					// Set the expiry date.
					useByData = stock[0].getUseByDate();
					//#CM10035
					// Calculate relocation possible picking qty.
					stockQty = stock[0].getAllocationQty();
					//#CM10036
					//	Response flag：Normal
					ansCode = RFTId5911.ANS_CODE_NORMAL;
					
					//#CM10037
					// When multiple data exist:
					if (stock.length > 1)
					{
						//#CM10038
						// Create file name
						String datapath = WmsParam.DAIDATA; // c:/daifuku/data/
						String sendpath = WmsParam.RFTSEND; // wms/rft/send/
						//#CM10039
						// sending file name
						sendFileName = sendpath + rftNo + "\\" + TABLE_FILE_NAME;
						//#CM10040
						// Save file name
						saveFileName = datapath + sendFileName;

						//#CM10041
						// Create list file
						id0911Operate.createTableFile(stock, saveFileName);
						//#CM10042
						// Obtain the file record count.
						fileRecordNo = stock.length;
						//#CM10043
						// Response flag：hit multiple items
						ansCode = RFTId5911.ANS_CODE_SOME_DATA;
					}
				}
			}
		}
		//#CM10044
		// Generate error when no info obtained from stockOperate instance.
		catch (NotFoundException e)
		{
			//#CM10045
			// Response flag：No corresponding data
			ansCode = RFTId5911.ANS_CODE_NULL;
		}
		//#CM10046
		// In the case Overflow is generated
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" AreaNo:" + areaNo +
							" LocationNo:" + locationNo +
							" ItemCode:" + JANCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM10047
			// 6026027=Length overflow. Process was aborted.{0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.COLLECTION_OVERFLOW;
		}
		//#CM10048
		// In the case other error exists
		catch (ReadWriteException e)
		{
			//#CM10049
			// 6006002=Database error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM10050
			// Response flag：error
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.DB_ACCESS_ERROR;
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

			//#CM10051
			// Response flag：error
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.INSTACIATE_ERROR;
        }

		catch (IOException e)
		{
			//#CM10052
			// Output the log
			//#CM10053
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
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.I_O_ERROR;
			
		}
		catch (ScheduleException e)
		{
			//#CM10054
			// Output the log
			//#CM10055
			// 6006001=Unexpected error occurred.{0}{0}
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR,
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
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.SCHEDULE_ERROR;
			
		}
		catch (Exception e)
		{
			//#CM10056
			// 6026015=Error occurred during ID process.{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			//#CM10057
			// Response flag：error
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM10058
		// Create the response electronic statement
		//#CM10059
		// STX
		rftId5911.setSTX();
		//#CM10060
		// SEQ
		rftId5911.setSEQ(0);
		//#CM10061
		// ID
		rftId5911.setID(RFTId5911.ID);
		//#CM10062
		// RFTTransmission time
		rftId5911.setRftSendDate(rftId0911.getRftSendDate());
		//#CM10063
		// SERVERTransmission time
		rftId5911.setServSendDate();
		//#CM10064
		// RFT machine
		rftId5911.setRftNo(rftNo);

		//#CM10065
		// 
		rftId5911.setWorkerCode(workerCode);

		//#CM10066
		// Consignor code
		rftId5911.setConsignorCode(consignorCode);
		
		//#CM10067
		// AreaNo
		rftId5911.setAreaNo(areaNo);	
		
		//#CM10068
		// Location No
		rftId5911.setLocationNo(locationNo);	

		//#CM10069
		// Case/Piece Division
		rftId5911.setCasePieceFlag(casePieceFlag);
		
		//#CM10070
		// Set the Consignor Name.
		rftId5911.setConsignorName(consignorName);
		
		//#CM10071
		// Item ID Code
		rftId5911.setItemId(itemID);

		//#CM10072
		// Item code
		rftId5911.setItemCode(itemCode);
		
		//#CM10073
		// JANCode
		rftId5911.setJANCode(JANCode);
		
		//#CM10074
		// Set the Bundle ITF.
		rftId5911.setBundleITF(bundleITF);

		//#CM10075
		// Set the Case ITF.
		rftId5911.setITF(ITF);

		//#CM10076
		// Set the item name.
		rftId5911.setItemName(itemName);

		//#CM10077
		// Set the packed qty per bundle.
		rftId5911.setBundleEnteringQty(bundleEnteringQty);

		//#CM10078
		// Set the packed qty per case.
		rftId5911.setEnteringQty(enteringQty);
		
		//#CM10079
		// Set the expiry date.
		rftId5911.setUseByDate(useByData);

		//#CM10080
		// Set the list file name.
		rftId5911.setFileName(sendFileName);
		
		//#CM10081
		// Set the file record count.
		rftId5911.setFileRecordNo(fileRecordNo);
		
		//#CM10082
		// Set the possible picking qty.
		rftId5911.setStockQty(stockQty);

		//#CM10083
		// Response flag
		rftId5911.setAnsCode(ansCode);
		
		//#CM10084
		// Set the detail of the error.
		rftId5911.setErrDetails(errorDetail);

		//#CM10085
		// ETX
		rftId5911.setETX();

		//#CM10086
		// Obtain the response electronic statement.
		rftId5911.getSendMessage(sdt);

	}

	//#CM10087
	// Package methods -----------------------------------------------

	//#CM10088
	// Protected methods ---------------------------------------------

	//#CM10089
	// Private methods -----------------------------------------------

}
//#CM10090
//end of class

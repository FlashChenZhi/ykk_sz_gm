// $Id: Id0740Process.java,v 1.2 2006/12/07 09:00:10 suresh Exp $
package jp.co.daifuku.wms.storage.rft ;

//#CM575705
/*
 * Copyright 2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM575706
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * This class processes the Relocation storage start request (ID0740) from the RFT<BR>
 * Using Id0740Operate class functions, this retrieves the Relocation storage possible data from Relocation Work info<BR>
 * It creates the response id to be send to RFT<BR>
 * <BR>
 * Relocation storage start request process (<CODE>processReceivedId</CODE> method)<BR>
 * <BR>
 * <DIR>
 *  Fetch the required info from received data<BR>
 *  Using Id0740Operate class functions, search relocation info and fetch relocation possible data<BR>
 *  (If the "Relocation Job no." is empty,  search using Consignor code+JAN code as the key<BR>
 *  Else search using Collect Job no. as the key<BR>
 *  If there is only one corresponding data, keep it as the Relocation storage item <BR>
 *  If there are multiple data, create relocation storage list and return it in the response<BR>
 *  If the response flag is 0 : normal, update RFT Work Info using the telegraph id contents<BR>
 *  Write to log if error occurs during update<BR>
 *  [Update conditions]
 *  <UL><LI>RFTNo</LI></UL>
 *  [Update contents]
 *  <UL><LI>Response id: created response id</LI>
 *      <LI>Last update date/time:system date/time</LI>
 *      <LI>Last update Process name:ID0740</LI>
 *  </UL>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/16</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 09:00:10 $
 * @author  $Author: suresh $
 */
public class Id0740Process extends IdProcess
{
	//#CM575707
	// Class fields----------------------------------------------------
	//#CM575708
	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "Id0740Process";
	
	//#CM575709
	/**
	 * Process name
	 */
	private static final String PROCESS_NAME = "ID0740";
	
	//#CM575710
	/**
	 * Relocation storageList file name field
	 */
	private static final String TABLE_FILE_NAME = "ID5740.txt";

	//#CM575711
	// Class variables -----------------------------------------------

	//#CM575712
	// Class method --------------------------------------------------
	//#CM575713
	/**
	 * Return the version of this class
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/12/07 09:00:10 $");
	}

	//#CM575714
	// Constructors --------------------------------------------------
	//#CM575715
	// Public methods ------------------------------------------------
	//#CM575716
	/**
	 * This class process the Relocation storage start request (ID0740)<BR>
	 * Fetch the received telegraph data in a byte array and create (ID5740) byte string<BR>
	 * <BR>
	 * Ensure that daily update is not in process<BR>
	 * In daily update is in progress, return error 5 : Daily update in progress<BR>
	 * Confirm whether worker result record exist or not.If not create new<BR>
	 * Use ID0740Operate class and search relocation info<BR>
	 * If only one data, set that item to send telegraph data<BR>
	 * If there are multiple data, create Relocation storage list file and set file name and response flag 7 : multiple data exist to send data<BR>
	 * If the response flag is 0 : normal, update RFT Work Info using the telegraph id contents<BR>
	 * Write to log if error occurs during update<BR>
 	 * [Update conditions]
 	 * <UL><LI>RFTNo</LI></UL>
 	 * [Update contents]
 	 * <UL><LI>Response id: created response id</LI>
 	 *     <LI>Last update date/time:system date/time</LI>
 	 *     <LI>Last update Process name:ID0740</LI>
 	 * </UL>	
	 * <BR>
	 * @param  rdt  Receive buffer
	 * @param  sdt  Send buffer
	 * @throws Exception Throw all exceptions
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		RFTId0740 rftId0740 = null;
		RFTId5740 rftId5740 = null;
		try
		{
			//#CM575717
			// Create instance to receive telegraph data
			rftId0740 = (RFTId0740) PackageManager.getObject("RFTId0740");
			rftId0740.setReceiveMessage(rdt);

			//#CM575718
			// Create instance to send telegraph data
			rftId5740 = (RFTId5740) PackageManager.getObject("RFTId5740");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		//#CM575719
		// Fetch RFT no. from telegraph data
		String rftNo = rftId0740.getRftNo() ;

		//#CM575720
		// Fetch worker code from telegraph data
		String workerCode = rftId0740.getWorkerCode();
		
		//#CM575721
		// Fetch consignor code from telegraph data
		String consignorCode = rftId0740.getConsignorCode() ;
		
		//#CM575722
		// Fetch Item code1 from telegraph data
		String scanCode1 = rftId0740.getScanCode1() ;
		
		//#CM575723
		// Fetch Item code2 from telegraph data
		String scanCode2 = rftId0740.getScanCode2();
		
		//#CM575724
		// Fetch Relocation Job no. from telegraph data
		String moveJobNo = rftId0740.getMoveJobNo() ;
		
		//#CM575725
		// Fetch Expiry date from telegraph data
		String useByDate = rftId0740.getUseByDate();

		//#CM575726
		// Variable to store consignor name
		String consignorName = "" ;
		
		//#CM575727
		// Variable to store Relocation origin area
		String sourceAreaNo = "" ;

		//#CM575728
		// Variable to store Origin location
		String sourceLocationNo = "" ;
		
		//#CM575729
		// Variable to store JAN code
		String janCode = "";
		
		//#CM575730
		// Variable for Bundle ITF
		String bundleITF = "" ;

		//#CM575731
		// Variable for Case ITF
		String ITF = "" ;

		//#CM575732
		// Variable for Item name
		String itemName = "" ;

		//#CM575733
		// Variable for qty per bundle
		String bundleEnteringQty = "" ;

		//#CM575734
		// Variable for qty per case
		String enteringQty = "" ;

		//#CM575735
		// Variable to store Relocation storage possible qty
		String movementInstoreAbleQty = "0" ;
		
		//#CM575736
		// Variable to store send file name
		String sendFileName = "";

		//#CM575737
		// Variable to store file record count
		int fileRecordNo = 0;
		
		//#CM575738
		// Variable for response flag
		String ansCode = RFTId5740.ANS_CODE_NORMAL ;
		
		//#CM575739
		// Variable for error details
		String errDetails = RFTId5740.ErrorDetails.NORMAL;
		
		//#CM575740
		// Variable to store storage file name
		String saveFileName = "";

		try
		{
			if(DisplayText.isPatternMatching(scanCode1))
			{
				throw new NotFoundException(RFTId5740.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(scanCode2))
			{
				throw new NotFoundException(RFTId5740.ANS_CODE_NULL);
			}

			//#CM575741
			// Create BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM575742
			//-----------------
			//#CM575743
			// Daily update process check
			//#CM575744
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM575745
				// Return response flag  5:Daily update under process
				ansCode = RFTId5740.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//#CM575746
				//-----------------
				//#CM575747
				// Check if exist in worker result
				//#CM575748
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_MOVEMENT_STORAGE,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					//#CM575749
					// Create worker result
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_MOVEMENT_STORAGE,
						workerCode,
						rftNo);
					//#CM575750
					// commit
					wConn.commit();
				}

				//#CM575751
				// Create Id0740Operate instance
				Id0740Operate id0740Operate = (Id0740Operate)PackageManager.getObject("Id0740Operate");
				id0740Operate.setConnection(wConn);
				
				//#CM575752
				//-----------------
				//#CM575753
				// Fetch relocation possible data from relocation info
				//#CM575754
				//-----------------				
				Movement[] movementList = id0740Operate.startMovementStorage(
					consignorCode,
					scanCode1,
					scanCode2,
					moveJobNo,
					rftNo,
					workerCode);

				if (movementList.length == 0)
				{
					//#CM575755
					// Dont process this since NotFoundException will be thrown if there is no relocation data
					//#CM575756
					// Return response flag 8: No target data
					ansCode = RFTId5740.ANS_CODE_NULL;
				}
				else if (movementList.length == 1)
				{
					//#CM575757
					// Fetch relocation possible data and set to variable
					consignorName = movementList[0].getConsignorName() ;
					sourceAreaNo  = movementList[0].getAreaNo();
					sourceLocationNo = movementList[0].getLocationNo();
					moveJobNo = movementList[0].getCollectJobNo();
					bundleITF = movementList[0].getBundleItf() ;
					ITF = movementList[0].getItf() ;
					janCode = movementList[0].getItemCode();
					itemName = movementList[0].getItemName1() ;
					bundleEnteringQty = Integer.toString( movementList[0].getBundleEnteringQty() ) ;
					enteringQty = Integer.toString( movementList[0].getEnteringQty() ) ;
					movementInstoreAbleQty = Integer.toString(movementList[0].getPlanQty()) ;
					useByDate = movementList[0].getUseByDate();

					ansCode = RFTId5740.ANS_CODE_NORMAL;
				}
				else
				{
					//#CM575758
					// create file name
					String datapath = WmsParam.DAIDATA; // c:/daifuku/data/
					String sendpath = WmsParam.RFTSEND; // wms/rft/send/
					//#CM575759
					// Send file name
					sendFileName = sendpath + rftNo + "\\" + TABLE_FILE_NAME;
					//#CM575760
					// Storage file name
					saveFileName = datapath + sendFileName;

					//#CM575761
					// Create relocation storage list file
					id0740Operate.createAnsFile(movementList, saveFileName) ;
					fileRecordNo = movementList.length;
							
					//#CM575762
					// Fetch possible work from inventory data and set it to variable
					consignorName = movementList[0].getConsignorName() ;
					bundleITF = movementList[0].getBundleItf() ;
					ITF = movementList[0].getItf() ;
					itemName = movementList[0].getItemName1() ;
					janCode = movementList[0].getItemCode();
					bundleEnteringQty = Integer.toString( movementList[0].getBundleEnteringQty() ) ;
					enteringQty = Integer.toString( movementList[0].getEnteringQty() ) ;
					ansCode = RFTId5740.ANS_CODE_SOME_DATA;
				}
				//#CM575763
				// commit
				wConn.commit();
			}

		}
		catch (IllegalAccessException e)
		{
		    //#CM575764
		    // 6006003 = Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,  e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575765
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR ;
			errDetails = RFTId5740.ErrorDetails.INSTACIATE_ERROR;
		}
		//#CM575766
		// SELECT FOR UPDATE timeout
		catch (LockTimeOutException e)
		{			
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				//#CM575767
				// 6006002=Database error occurred.{0}
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM575768
			// Response flag:Under maintenance in another terminal
			ansCode = RFTId5741.ANS_CODE_MAINTENANCE ;
		}
		//#CM575769
		// Throw error if info can't be fetched
		catch(NotFoundException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = e.getMessage();
			if (! RFTId5740.checkAnsCode(ansCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5740.ErrorDetails.NULL;
				ansCode = RFTId5740.ANS_CODE_ERROR;
			}
			else if (ansCode.equals(RFTId5740.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5740.ErrorDetails.NULL;
			}
		}
		//#CM575770
		// If overflow occurs
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" ItemCode:" + janCode +
							" MoveJobNo:" + moveJobNo +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM575771
			// 6026027=Length overflow. Process was aborted. {0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5740.ErrorDetails.COLLECTION_OVERFLOW;
			ansCode = RFTId5740.ANS_CODE_ERROR;
		}
		//#CM575772
		// Other error occurred
		catch(ReadWriteException e)
		{
			//#CM575773
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
			//#CM575774
			// Response flag : Error
			errDetails = RFTId5740.ErrorDetails.DB_ACCESS_ERROR;
			ansCode = RFTId5740.ANS_CODE_ERROR ;
		}
		catch (IOException e)
		{
		    //#CM575775
		    // 6006020 = File I/O error occurred. {0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575776
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR ;
			errDetails = RFTId5740.ErrorDetails.I_O_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM575777
			// 6026022=Blank or prohibited character is included in the specified value. {0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			//#CM575778
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR;
			errDetails = RFTId5740.ErrorDetails.PARAMETER_ERROR;
		}
		catch (ScheduleException e)
		{
			//#CM575779
			// 6026022=Blank or prohibited character is included in the specified value. {0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			//#CM575780
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR;
			errDetails = RFTId5740.ErrorDetails.SCHEDULE_ERROR;
		}
		catch (DataExistsException e)
		{
			//#CM575781
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
			//#CM575782
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR;
			errDetails = RFTId5740.ErrorDetails.DB_UNIQUE_KEY_ERROR;			
		}
		catch (SQLException e)
		{
			//#CM575783
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
			//#CM575784
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR;
			errDetails = RFTId5740.ErrorDetails.DB_ACCESS_ERROR;
		}

		catch(Exception e)
		{
			//#CM575785
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
			//#CM575786
			// Response flag : Error
			ansCode = RFTId5740.ANS_CODE_ERROR ;
			errDetails = RFTId5740.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM575787
		// Create response id
		//#CM575788
		// STX
		rftId5740.setSTX() ;
		//#CM575789
		// SEQ
		rftId5740.setSEQ(0) ;
		//#CM575790
		// ID
		rftId5740.setID(RFTId5740.ID) ;
		//#CM575791
		// RFT send time
		rftId5740.setRftSendDate(rftId0740.getRftSendDate()) ;
		//#CM575792
		// Server send time
		rftId5740.setServSendDate() ;
		//#CM575793
		// RFT no.
		rftId5740.setRftNo(rftNo) ;

		//#CM575794
		// Worker code
		rftId5740.setWorkerCode(workerCode) ;
		
		//#CM575795
		// Consignor code
		rftId5740.setConsignorCode(consignorCode) ;
		
		//#CM575796
		// Set Consignor code
		rftId5740.setConsignorName(consignorName) ;
		
		//#CM575797
		// Set Relocation origin area No.
		rftId5740.setSourceAreaNo(sourceAreaNo) ;

		//#CM575798
		// Set relocation original location no.
		rftId5740.setSourceLocationNo(sourceLocationNo) ;
		
		//#CM575799
		// Set relocation Job no.
		rftId5740.setMovileJobNo(moveJobNo) ;
		
		//#CM575800
		// JAN code
		rftId5740.setJANCode(janCode) ;
		
		//#CM575801
		// Set Bundle ITF
		rftId5740.setBundleITF(bundleITF) ;
		
		//#CM575802
		// Set Case ITF
		rftId5740.setITF(ITF) ;
		
		//#CM575803
		// Set Item Name
		rftId5740.setItemName(itemName) ;
		
		//#CM575804
		// Set packed qty per bundle
		rftId5740.setBundleEnteringQty(bundleEnteringQty) ;
		
		//#CM575805
		// Set packed qty per case
		rftId5740.setEnteringQty(enteringQty) ;
		
		//#CM575806
		// Relocation storage possible qty
		rftId5740.setMovementInstoreAbleQty(movementInstoreAbleQty) ;
		
		//#CM575807
		// Set expiry date
		rftId5740.setUseByDate(useByDate);

		//#CM575808
		// List file name
		rftId5740.setAnsFileName(sendFileName) ;
		
		//#CM575809
		// File record count
		if(fileRecordNo != 0)
		{
			rftId5740.setFileRecordNo(fileRecordNo);
		}
		
		//#CM575810
		// Response flag
		rftId5740.setAnsCode(ansCode) ;
		
		//#CM575811
		// Error details
		rftId5740.setErrDetails(errDetails);
		
		//#CM575812
		// ETX
		rftId5740.setETX() ;

		//#CM575813
		// Acquire response id
		rftId5740.getSendMessage(sdt) ;

		//#CM575814
		// If the response flag is 0: normal, save the response id to file
		try
		{
			if(ansCode.equals(RFTId5740.ANS_CODE_NORMAL))
			{
				rftId5740.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
		}
		catch (Exception e)
		{
			//#CM575815
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
			
		}

	}

	//#CM575816
	// Package methods -----------------------------------------------

	//#CM575817
	// Protected methods ---------------------------------------------

	//#CM575818
	// Private methods -----------------------------------------------

	
}
//#CM575819
//end of class

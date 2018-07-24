package jp.co.daifuku.wms.storage.schedule;

//#CM271
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SQLSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM272
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This is an abstract class and allows to execute the process for scheduling the storage package.
 * Implement WmsScheduler interface and also implement the processes required for implementing this interface.
 * This class implements the common method. The class, which inherits this class, implements the individual behaviors for processing schedules, 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>Y.Kubo</TD><TD>Create New</TD></TR>
 * <TR><TD>2005/08/04</TD><TD>Y.Okamura</TD><TD>AbstractSCH was amended to allow to inherit.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/11 06:03:41 $
 * @author  $Author: suresh $
 */
public abstract class AbstractStorageSCH extends AbstractSCH
{
	//#CM273
	//	Class variables -----------------------------------------------
	//#CM274
	/**
	 * Class Name
	 */
	public static final String CLASS_NAME = "AbstractStorageSCH";
	
	//#CM275
	// Class method --------------------------------------------------
	//#CM276
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/11 06:03:41 $");
	}
	//#CM277
	// Constructors --------------------------------------------------

	//#CM278
	// Public methods ------------------------------------------------
	
	//#CM279
	// Protected methods ---------------------------------------------
	//#CM280
	/**
	 * Check that the contents of Worker Code and password are correct.<BR>
	 * Return true if the content is proper, or return false if improper.<BR>
	 * If the returned value is false,
	 * Require to obtain the result using <CODE>getMessage()</CODE> method.<BR>
	 * 
	 * @param  conn               Database connection object
	 * @param  checkParam        This parameter class includes contents to be checked for input.
	 * @return Return true if the contents of Worker Code and password are proper, or return false if improper.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected boolean checkWorker(Connection conn, StorageSupportParameter searchParam) throws ReadWriteException, ScheduleException
	{
		String workerCode = searchParam.getWorkerCode();
		String password = searchParam.getPassword();
		
		return correctWorker(conn, workerCode, password);
	}
	
	//#CM281
	/**
	 * Return values by reason when not allowing to display in the preset area.<BR>
	 * No target data: StorageSupportParameter[0]<BR>
	 * If exceeding the maximum count of data to be displayed: null<BR>
	 * <BR>
	 * <U>Use canLowerDisplay method of AbstractSCH class always before using this method.</U>
	 * 
	 * @return Values returned by each reason
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 */
	protected StorageSupportParameter[] returnNoDisplayParameter() throws ScheduleException, ReadWriteException
	{
		if (getDisplayNumber() <= 0)
		{
			return new StorageSupportParameter[0];
		}
		
		if (getDisplayNumber() > WmsParam.MAX_NUMBER_OF_DISP)
		{
			return null;
		}

		doScheduleExceptionHandling(CLASS_NAME);
		return null;

	}
	
	//#CM282
	/**
	 * Lock the target work status and its linked inventory information together using Work No. as a key.<BR>
	 * 
	 * @param conn Instance to maintain database connection.
	 * @param param Parameter instance that contains Work No. of data to be locked.
	 * @return Return false if no lock target. Return true if the target is locked.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter[] wParam = (StorageSupportParameter[]) checkParam;
		Vector vec = new Vector();

		for (int i = 0; i < wParam.length; i ++)
		{
			vec.addElement(wParam[i].getJobNo());				
		}
		if (vec.isEmpty())
		{
			return false;
		} 			

		String[] jobNoArray = new String[vec.size()];
		vec.copyInto(jobNoArray);
		
		return this.lockAll(conn, jobNoArray);
		
	}

	//#CM283
	/**
	 * Lock the target work status and its linked inventory information together using Storage Plan unique key.<BR>
	 * 
	 * @param conn Instance to maintain database connection.
	 * @param param Parameter instance containing Storage Plan unique key of data to be locked.
	 * @return Return false if no lock target. Return true if the target is locked.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected boolean lockPlanUkeyAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter[] wParam = (StorageSupportParameter[]) checkParam;
		Vector vec = new Vector();

		for (int i = 0; i < wParam.length; i ++)
		{
			vec.addElement(wParam[i].getStoragePlanUKey());				
		}
		if (vec.isEmpty())
		{
			return false;
		}			

		String[] planUKeyArray= new String[vec.size()];
		vec.copyInto(planUKeyArray);
		
		return lockPlanUkeyAll(conn, planUKeyArray);
		
	}

	//#CM284
	/**
	 * Update the Exclusion process to Center Stock to be updated.<BR>
	 * @param conn Database connection object
	 * @return True for normal; False for error
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected void lockOccupiedStock(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{
		StockSearchKey skey = new StockSearchKey();
		StockHandler stockhdl = new StockHandler(conn);

		skey = (StockSearchKey)setOccupiedStockSearchKey(checkParam);
		stockhdl.findForUpdate(skey);
		
	}
	//#CM285
	/**
	 * Update the Exclusion process to Center Stock to be updated.<BR>
	 * @param conn Database connection object
	 * @return True for normal; False for error
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected SQLSearchKey setOccupiedStockSearchKey(Parameter[] checkParam) throws ReadWriteException
	{
		//#CM286
		// Input information of the parameter
		StorageSupportParameter[] wParam = ( StorageSupportParameter[] )checkParam;

		StockSearchKey skey = new StockSearchKey();
		//#CM287
		// Center Inventory
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM288
		// Consignor code
		skey.setConsignorCode(wParam[0].getConsignorCode());

		for (int i = 0;i < wParam.length; i++)
		{
			//#CM289
			// Item Code
			if (i == 0)
			{
				skey.setItemCode(wParam[i].getItemCode(),"=","((","","AND");
			}
			else
			{
				skey.setItemCode(wParam[i].getItemCode(),"=","(","","AND");
			}
			//#CM290
			// To use expiry date as a key to make a stock unique,
			if (WmsParam.IS_USE_BY_DATE_UNIQUEKEY)
			{
				//#CM291
				// Expiry Date
				skey.setUseByDate(wParam[i].getUseByDate());
			}
			//#CM292
			// Storage qty/Piece division (None)
			if (wParam[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				skey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING);
			}
			//#CM293
			// Case/Piece division ( Case)
			else if (wParam[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				skey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE );
			}
			//#CM294
			// Case/Piece division ( Piece)
			else if (wParam[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				skey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE );
			}
			//#CM295
			// Location No.
			if(i == wParam.length-1)
			{
				skey.setLocationNo(wParam[i].getStorageLocation(),"=","","))","OR");
			}
			else
			{
				skey.setLocationNo(wParam[i].getStorageLocation(),"=","",")","OR");
			}
		}
		//#CM296
		// Obtain the STOCK_ID only.
		skey.setStockIdCollect();		
		
		return skey;
	}
	
	 //#CM297
	 /**
	  * Check the input value.<BR>
	  * Return the check result. <BR>
	  * Enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
	  * Allow this method to execute the following processes. <BR>
	  * <BR>
	  * 1.Require to input any value within the designated range for Case/Piece division. <BR>
	  * <BR>
	  * 2.With Case/Piece division None, <BR>
	  *   <DIR>
	  *   Require to input Packed qty per Case if Case qty is input. <BR>
	  *   Require to input any value 1 or larger for Storage Case Qty or Storage Piece Qty. <BR>
	  *   </DIR>
	  * <BR>
	  * 3.With Case/Piece division Case <BR>
	  *   <DIR>
	  *   Requireto ensure that Packed qty per case is input. <BR>
	  * 	 Disable to input Storage Case Qty. <BR>
	  *   Require to input any value 1 or larger for Storage Case Qty. <BR>
	  *   </DIR>
	  * <BR>
	  * 4.With Case/Piece division Piece, <BR>
	  *   <DIR>
	  *   Disable to input Storage Case Qty. <BR>
	  *   Require to input any value 1 or larger for Storage Piece Qty. <BR>
	  *   Any input for Piece Qty equal to Packed qty per Case or more is not accepted.<BR>
	  *   </DIR>
	  * <BR>
	  * @param  casepieceflag      Case/Piece division
	  * @param  enteringqty        Packed qty per Case
	  * @param  caseqty            Storage Case Qty
	  * @param  pieceqty           Storage Piece Qty
	  * @throws ReadWriteException Announce when error occurs on the database connection.
	  * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	  * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	  */
	 protected boolean storageInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty) 
		 throws ReadWriteException, ScheduleException
	 {
		
		 //#CM298
		 //	Check the Case/Piece division.
		 if (!casepieceflag.equals( StorageSupportParameter.CASEPIECE_FLAG_NOTHING ) &&
			 !casepieceflag.equals( StorageSupportParameter.CASEPIECE_FLAG_CASE ) &&
			 !casepieceflag.equals( StorageSupportParameter.CASEPIECE_FLAG_PIECE ) )
		 {
			 //#CM299
			 // 6023145 = Please enter value of specified range for the case/piece division.
			 wMessage = "6023145";
			 return false;
		 }
		
		 //#CM300
		 // With Case/Piece division None,
		 if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		 {
			 //#CM301
			 // Packed qty per Case is 1 or larger
			 if(enteringqty > 0)
			 {
				 //#CM302
				 // If Storage Case Qty and Storage Piece Qty are 0,
				 if(caseqty == 0 && pieceqty == 0)
				 {
					 //#CM303
					 // 6023325 = Enter 1 or greater value in {0} or {1}.
					 wMessage = "6023325" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + DisplayText.getText("LBL-W0386");
					 return false;
				 }
			 }
			 else
			 {
				 //#CM304
				 // If Storage Case Qty is 1 or larger,
				 if(caseqty > 0)
				 {
					 //#CM305
					 // 6023019 = Please enter 1 or greater in the packed quantity per case.
					 wMessage = "6023019";
					 return false;
				 }
				
				 //#CM306
				 // If Storage Case Qty and Storage Piece Qty are 0,
				 if(caseqty == 0 && pieceqty == 0)
				 {
					 //#CM307
					 // 6023325 = Enter 1 or greater value in {0} or {1}.
					 wMessage = "6023325" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + DisplayText.getText("LBL-W0386");
					 return false;
				 }
			 }
		 }
		 //#CM308
		 // With Case/Piece division Case
		 else if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		 {
			 //#CM309
			 // Packed qty per Case is 1 or larger
			 if(enteringqty > 0)
			 {
				 //#CM310
				 // If Storage Case Qty is 0,
				 if(caseqty == 0)
				 {
					 //#CM311
					 // 6023057 = Please enter {1} or greater for {0}.
					 wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + "1";
					 return false;
				 }
				
				 //#CM312
				 // When Case/Piece division is Case, input of Piece Qty is not allowed except for inventory package.
				 //#CM313
				 // If Storage Piece Qty is 1 or larger,
				 if(pieceqty > 0)
				 {
					 //#CM314
					 // 6023356 = If Case is specified in Case/Piece
					 wMessage = "6023356";
					 return false;
				 }
			 }
			 else
			 {
				 //#CM315
				 // 6023019 = Please enter 1 or greater in the packed quantity per case.
				 wMessage = "6023019";
				 return false;
			 }
		 }
		 //#CM316
		 // With Case/Piece division Piece,
		 else if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		 {
			 //#CM317
			 // If Storage Case Qty is 1 or larger,
			 if(caseqty > 0)
			 {
				 //#CM318
				 // 6023340 = Cannot enter Host Planned Case Qty. if Case/Piece Class is Piece.
				 wMessage = "6023340";
				 return false;
			 }
			 else
			 {
				 //#CM319
				 // If Storage Piece Qty is 0,
				 if(pieceqty == 0)
				 {
					 //#CM320
					 // 6023057 = Please enter {1} or greater for {0}.
					 wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0386") + wDelim + "1";
					 return false;
				 }
				
			 }
		 }
		
		 return true;
	
	 }
	


}
//#CM321
//end of class

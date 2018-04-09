package jp.co.daifuku.wms.retrieval.schedule;

//#CM722330
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

//#CM722331
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow this abstract class to execute the schedule process for Picking package. 
 * Allow this class to implement the common method for Picking package.
 * Allow the class that inherits this class to implement the individual behavior of schedule process. 
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/17</TD><TD>M.Inoue</TD><TD>New creation</TD></TR>
 * <TR><TD>2005/08/04</TD><TD>Y.Okamura</TD><TD>It was revised so as to inherit AbstractSCH.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:50 $
 * @author  $Author: suresh $
 */
public abstract class AbstractRetrievalSCH extends AbstractSCH
{
	//#CM722332
	//	Class variables -----------------------------------------------
	//#CM722333
	/**
	 * Class Name 
	 */
	public static final String CLASS_NAME = "AbstractRetrievalSCH";
	
	//#CM722334
	// Class method --------------------------------------------------
	//#CM722335
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:50 $");
	}
	//#CM722336
	// Constructors --------------------------------------------------

	//#CM722337
	// Public methods ------------------------------------------------

	//#CM722338
	// Protected methods ---------------------------------------------
	//#CM722339
	/**
	 * Check that the contents of Worker Code and password are correct. <BR>
	 * Return true if the content is proper, or return false if improper. <BR>
	 * If the returned value is false, 
	 * Require to obtain the result using <CODE>getMessage()</CODE> method. <BR>
	 * 
	 * @param  conn Database connection object
	 * @param  checkParam Parameter class that contains info to be checked for input. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling. 
	 * @return true if the contents of Worker Code and Password are correct, or false if not correct. 
	 */
	protected boolean checkWorker(Connection conn, RetrievalSupportParameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		//#CM722340
		// Obtain the Worker code and password from the parameter. 
		String workerCode = checkParam.getWorkerCode();
		String password = checkParam.getPassword();
		
		return correctWorker(conn, workerCode, password);

	}
	
	//#CM722341
	/**
	 * Return values by reason when not allowing to display in the preset area. <BR>
	 * No target data: RetrievalSupportParameter[0] <BR>
	 * If exceeding the maximum count of data to be displayed: null <BR>
	 * <BR>
	 * <U> Use canLowerDisplay method of AbstractSCH class always before using this method. </U>
	 * 
	 * @return Values returned by each reason 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling. 
	 */
	protected RetrievalSupportParameter[] returnNoDisplayParameter() throws ScheduleException, ReadWriteException
	{
		if (getDisplayNumber() <= 0)
		{
			return new RetrievalSupportParameter[0];
		}
		
		if (getDisplayNumber() > WmsParam.MAX_NUMBER_OF_DISP)
		{
			return null;
		}

		doScheduleExceptionHandling(CLASS_NAME);
		return null;
		

	}
	
	//#CM722342
	/**
	 * Lock the target Work Status Info and its linked Inventory Info together using Work No. as a key. <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam Parameter instance that contains Work No. of data to be locked. 
	 * @return Return false if no lock target. Return true if the target is locked. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean lockAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) checkParam;
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
		
		//#CM722343
		// Lock the Work Status Info and its linked Inventory Info. 
		return super.lockAll(conn, jobNoArray);
		
	}
	
	//#CM722344
	/**
	 * Lock the target Work Status Info and its linked Inventory Info together using Work No. as a key. <BR>
	 * In the screen where two or more data of work status are set in a single line of the preset area, 
	 * use this method. <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam Parameter instance that contains Work No. of data to be locked. 
	 * @return Return false if no lock target. Return true if the target is locked. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean lockSummarilyAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) checkParam;
		Vector vec = new Vector();

		for (int i = 0; i < wParam.length; i++)
		{
			String[] summeryJobNoArray = new String[wParam[i].getJobNoList().size()];
			wParam[i].getJobNoList().copyInto(summeryJobNoArray);
			
			for (int j = 0 ; j < summeryJobNoArray.length ; j++)
			{
				vec.addElement(summeryJobNoArray[j]);
			}
		}
		if (vec.isEmpty())
		{
			return false;
		} 			

		String[] jobNoArray = new String[vec.size()];
		vec.copyInto(jobNoArray);
		
		return super.lockAll(conn, jobNoArray);
		
	}
	
	//#CM722345
	/**
	 * Lock the target Work Status and its linked Inventory Info together using Picking Plan unique key. <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param checkParam Parameter instance that contains Picking Plan unique key of the target data to be locked 
	 * @return Return false if no lock target. Return true if the target is locked. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected boolean lockPlanUkeyAll(Connection conn, Parameter[] checkParam) throws ReadWriteException, ScheduleException
	{

		RetrievalSupportParameter[] wParam = (RetrievalSupportParameter[]) checkParam;
		Vector vec = new Vector();

		for (int i = 0; i < wParam.length; i ++)
		{
			vec.addElement(wParam[i].getRetrievalPlanUkey());
		}
		if (vec.isEmpty())
		{
			return false;
		} 			

		String[] planUKeyArray = new String[vec.size()];
		vec.copyInto(planUKeyArray);
		
		return lockPlanUkeyAll(conn, planUKeyArray);
	}
	
	//#CM722346
	/**
	 * Check the input value. <BR>
	 * Return the check result. <BR>
	 * Use <CODE>getMessage()</CODE> method to obtain the contents. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * 1. Require to input any value within the designated range for Case/Piece division. <BR>
	 * <BR>
	 * 2.Data with Case/Piece division None <BR>
	 *   <DIR>
	 *   Require to enter 1 or greater in the Picking Case Qty or Picking Piece Qty. <BR>
	 *   Require to input Packed qty per Case if Picking Case qty is input. <BR>
	 *   Require that any value larger than the Packed Qty per Case is not input in the Picking Piece Qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 3.Data with Case/Piece division Case <BR>
	 *   <DIR>
	 *   Require to ensure that Packed qty per case is input. <BR>
	 *   Require to enter 1 or greater in the Picking Case Qty. <BR>
	 * 	 Require that Picking Piece Qty is 0. <BR>
	 *   </DIR>
	 * <BR>
	 * 4.Data with Case/Piece division Piece,  <BR>
	 *   <DIR>
	 *   Require that the Picking Case Qty is 0.  <BR>
	 *   Require to enter 1 or larger value in the Picking Piece Qty.  <BR>
	 *   </DIR>
	 * <BR>
	 * @param  casepieceflag      Case/Piece division 
	 * @param  enteringqty        Packed Qty per Case
	 * @param  caseqty            Picking Case Qty 
	 * @param  pieceqty           Picking Piece Qty 
	 * @return Return true if the input is correct, or return false if not correct. 
	 */
	protected boolean retrievalInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty)
		throws ReadWriteException
	{

		//#CM722347
		//	 Check the Case/Piece division. 
		if (!(casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING)
			|| casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE)
			|| casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE)))
		{
			//#CM722348
			// 6023145 = Case /Require to input any value within the designated range for Piece division. 
			wMessage = "6023145";
			return false;
		}

		//#CM722349
		// Data with Case/Piece division None 
		if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM722350
			// Data with values 0 of both Picking Case Qty and Picking Piece Qty: 
			if (caseqty <= 0 && pieceqty <= 0)
			{
				//#CM722351
				// 6023325=Enter 1 or greater value in {0} or {1}. 
				//#CM722352
				// LBL-W0385=Host planned Case qty LBL-W0386=Host Planned Piece Qty 
				wMessage = "6023325" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + DisplayText.getText("LBL-W0386");
				return false;
			}

			//#CM722353
			// For data with input in Picking Case Qty but no input in Packed Qty per Case: 
			if (caseqty > 0 && enteringqty <= 0)
			{
				//#CM722354
				// 6023019=Please enter 1 or greater in the packed quantity per case. 
				wMessage = "6023019";
				return false;
			}

		}
		//#CM722355
		// Data with Case/Piece division Case 
		else if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM722356
			// If Packed Qty per Case is not input: 
			if (enteringqty <= 0)
			{
				//#CM722357
				// 6023019=Please enter 1 or greater in the packed quantity per case. 
				wMessage = "6023019";
				return false;
			}

			//#CM722358
			// For data with Picking Case Qty "0": 
			if (caseqty <= 0)
			{
				//#CM722359
				// 6023057=Please enter {1} or greater for {0}. 
				//#CM722360
				// LBL-W0385=Host planned Case qty 
				wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0385") + wDelim + "1";
				return false;
			}

			//#CM722361
			// Require that the value of Picking Piece Qty is 1 or larger. 
			if (pieceqty > 0)
			{
				//#CM722362
				// 6023356=If Case is specified in Case/Piece, you are not allowed to enter Host Planned Piece Qty. 
				wMessage = "6023356";
				return false;
			}

		}
		//#CM722363
		// Data with Case/Piece division Piece, 
		else if (casepieceflag.equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM722364
			// The value of Picking case qty is 1 or larger. 
			if (caseqty > 0)
			{
				//#CM722365
				// 6023340=Cannot enter Host Planned Case Qty. if Case/Piece Class is Piece. 
				wMessage = "6023340";
				return false;
			}
			//#CM722366
			// For data with Picking Piece qty "0 ": 
			if (pieceqty <= 0)
			{
				//#CM722367
				// 6023057=Please enter {1} or greater for {0}. 
				//#CM722368
				// LBL-W0386=Host Planned Piece Qty 
				wMessage = "6023057" + wDelim + DisplayText.getText("LBL-W0386") + wDelim + "1";
				return false;
			}

		}

		return true;

	}

}
//#CM722369
//end of class

//#CM720434
//$Id: Id0330Operate.java,v 1.3 2007/02/07 04:19:39 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.WorkingInformationHandler;

//#CM720435
/**
 * Designer : E.Takeda <BR>
 * Maker :<BR>
 * <BR>
 * Execute the data process responding to the request for starting Order Picking. <BR>
 * Obtain the Order Picking data and update it to "Processing". <BR>
 * Implement the business logic to be invoked from Id0330Process. <BR>
 * <BR>
 * Contents of process depend on the Picking Work division. <BR>
 * <OL>
 * <Li>For data with Order <BR>
 * {@link #getRetrievalOrderData(String,String,String [] ,String,String,String) getRetrievalOrderData()}
 * Execute the following process within the method. <BR>
 * <UL type="disc">
 * <LI>Check for all the Orders designated as a Parameter whether there is one or more data possible to work.
 * {@link #isWorkableOrders(String,String,String,String [] ,String,String) (isWorkableOrders())}
 * <LI>If all the target data is workable, obtain the Order Picking work data.
 * {@link #startRetrievalOrder(String,String,String,String [] ,String,String,String,String) (startRetrievalOrder())}
 * <BR>
 * Renumber the Aggregation Work No.
 * {@link #reattachCollectNo(WorkingInformation [] ) (reattachCollectNo())}<BR>
 * Obtain the Worker Name, and update the Work Status and the Picking Plan Info to "Processing".
 * {@link #updateToWorking(String,String,String,WorkingInformation [] ) (updateToWorking())}
 * <BR>
 * Aggregate data to generate an Order Picking data file.
 * {@link #collectWorkData(WorkingInformation [] ) (collectWorkData())}<BR>
 * Maintain the work status temporarily for the response electronic statement.
 * {@link #pickUpOrderData(String,WorkingInformation [] ) (pickUpOrderData())}
 * <BR>
 * 
 * <LI>If any or all of the target Orders is impossible to work, check the status of the designated Order No.
 * {@link #pickUpAndCheckOrderData(String,String,String,String,String,String,String,String) (picUpAndCheckOrderData())}
 * <BR>
 * Search for the work status and set the corresponding order status.
 * {@link #getOrderDataList(String,String,String,String) (getOrderDataList())}
 * <BR>
 * </UL>
 * <LI>For data with division "Area" <BR>
 * {@link #getRetrievalAreaData(String,String,String,String,String,String,String) getRetrievalAreaData()}
 * Execute the following process within the method. <BR>
 * <UL type="disc">
 * <LI>Obtain the Picking data that corresponds to the Area No. and the Zone No. designated in the parameter, from the Work Status.
 * {@link #getWorkableData(String,String,String,String [] ,String,String,String,String,String) (getWorkableData())}
 * <LI>Pass it to the class to determine the Order No. for specifying Area, and determine the Order No. to be worked.
 * {@link jp.co.daifuku.wms.retrieval.rft.PickingOrderScheduler#getTargetOrderNo(String,String,WorkingInformation [] ) (getTargetOrderNo())}
 * <LI>Obtain the work data of the determined Order No. that includes the designated Area No. Zone No.
 * {@link #getWorkData(String,String,String,String,String,String,String,String) (getWorkData())}
 * <BR>
 * Obtain the work data with the determined Order No. from the work data with the value equal to or larger than the designated Area No. and Order No., in the ascending order, and allow the work with the value less than the designated Area No. and Zone No. to follow it.
 * <BR>
 * <LI>Obtain the Order Picking work data.
 * {@link #startRetrievalOrder(String,String,String,String [] ,String,String,String,String) (startRetrievalOrder())}
 * <BR>
 * Renumber the Aggregation Work No.
 * {@link #reattachCollectNo(WorkingInformation [] ) (reattachCollectNo())}<BR>
 * Obtain the Worker Name, and update the Work Status and the Picking Plan Info to "Processing".
 * {@link #updateToWorking(String,String,String,WorkingInformation [] ) (updateToWorking())}
 * <BR>
 * Aggregate data to generate an Order Picking data file.
 * {@link #collectWorkData(WorkingInformation [] ) (collectWorkData())}<BR>
 * </UL>
 *  !Attention!  <BR>
 * Allow the WorkingInformation class and the WorkingInformationHandler class, which are used in this class,
 * to use a RFT Package ("jp.co.daifuku.wms.base.rft" package). <BR>
 *  (Disabling to cast the parent class instance to the child class, require to use WorkingInformationHandler for RFT)  <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2005/11/04</TD>
 * <TD>etaeda</TD>
 * <TD>New creation</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:39 $
 * @author $Author: suresh $
 */
public class Id0330Operate extends IdOperate
{
	//#CM720436
	//  Class fields----------------------------------------------------
	//#CM720437
	/**
	 * A field that represents process name of this class.
	 */
	private static final String PROCESS_NAME = "ID0330";

	//#CM720438
	/**
	 * A field that represents this class.
	 */
	private static final String CLASS_NAME = "Id0330Operate";

	//#CM720439
	// Class variables -----------------------------------------------
	//#CM720440
	/**
	 * Variable for maintaining the Order Picking data
	 */
	private WorkingInformation[] retrievalOrderData = null;

	//#CM720441
	// Constructors --------------------------------------------------

	//#CM720442
	// Class method --------------------------------------------------
	//#CM720443
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:39 $";
	}

	//#CM720444
	/**
	 * Check for all the Orders designated as a Parameter whether there is one or more data possible to work. <BR>
	 * <p>
	 * Obtain the data attached with "For Update". <BR>
	 * Maintain the searched workable data inside. <BR>
	 * <BR>
	 * Search through the work status using the conditions designated as a Parameter, such as Consignor or Order. (
	 * {@link #getWorkableData(String,String,String,String [] ,String,String,String,String,String) getWorkableOrderData()})
	 * <BR>
	 * <DIR>
	 * <p>
	 * Ensure that there is one or more workable data on each Order. <BR>
	 * <p>
	 * </DIR> Return False if some Order has no work, or return True if all Orders have Work. <BR>
	 * <BR>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned date
	 * @param workForm
	 *            Work Type
	 * @param orderNo
	 *            Order No.  Array
	 * @param rftNo
	 *            RFT Machine No.
	 * @param workerCode
	 *            Worker Code
	 * @return Return true if all the designated Orders can be worked, or return false if there is one or more Order with no work.
	 * @throws LockTimeOutException
	 *             Announce when database lock is not cancelled for the specified period.
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws IllegalAccessException
	 *             Announce when failed to generate instance.
	 */
	public boolean isWorkableOrders(String consignorCode, String planDate,
			String workForm, String[] orderNo, String rftNo, String workerCode)
			throws LockTimeOutException, ReadWriteException,
			IllegalAccessException
	{
		try
		{
			//#CM720445
			// Search through the Work Status
			retrievalOrderData = getWorkableData(consignorCode, planDate,
					workForm, orderNo, null, null, rftNo, workerCode,
					RFTId0330.JOB_TYPE_ORDER);
		}
		catch (LockTimeOutException e)
		{
			//#CM720446
			// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
			RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME,
					"DNWORKINFO");
			throw e;
		}
		if (retrievalOrderData.length == 0)
		{
			//#CM720447
			// When workable data is not found.
			return false;
		}

		//#CM720448
		// Obtain the valid order qty from the array of the Order No. designated by the parameter.
		int validOrderCount = 0;
		for (int i = 0; i < orderNo.length; i++)
		{
			if(DisplayText.isPatternMatching(orderNo[i]))
			{
				return false;
			}

			if (!StringUtil.isBlank(orderNo[i]))
			{
				validOrderCount++;
			}
		}
		if (validOrderCount == 0)
		{
			return false;
		}

		//#CM720449
		// Check for all data if there is any data possible to work for all Order.
		Vector orderNoList = new Vector();
		for (int i = 0; i < retrievalOrderData.length; i++)
		{
			if (!orderNoList.contains(retrievalOrderData[i].getOrderNo()))
			{
				orderNoList.add(retrievalOrderData[i].getOrderNo());
			}
			if (orderNoList.size() >= validOrderCount)
			{
				return true;
			}
		}
		return false;
	}

	//#CM720450
	/**
	 * Update the Order Picking Work Status to "Processing", and aggregate the data to generate an Order Picking data file. <BR>
	 * <BR>
	 * Renumber the Aggregation Work No. of the maintained Order Picking data. (
	 * {@link #reattachCollectNo(WorkingInformation [] ) reattachCollectNo()})
	 * <BR>
	 * <p>
	 * <DIR>For data with the same values in the fields of Order No., Area No., Zone No., Location No., Item (Set the Item field in WMSParam to Item Code or
	 * Item Code + ITF + Bundle ITF), Case/Piece division, and Expiry Date, aggregate them and renumber the minimum value to an Aggregation Work No.
	 * <BR>
	 * <p>
	 * Obtain ON/OFF of Aggregation from parameter, renumber the Aggregation Work No. before aggregation. <BR>
	 *  (Use the Aggregation Work No. of the leading data of the aggregation targets)  <BR>
	 * Update the data to the result from renumbering into the Aggregation Work No. in the Work Status. <BR>
	 * </DIR>
	 * <P>
	 * Obtain the Worker Name, and update the Work Status and the Picking Plan Info to "Processing". (
	 * {@link #updateToWorking(String,String,String,WorkingInformation [] ) updateToWorking()})
	 * <BR>
	 * <p>
	 * <DIR>Update the Status flag of the Work Status to "Processing". <BR>
	 * Update the Status flag of the corresponding Picking Plan Info to "Processing". <BR>
	 * </DIR>
	 * <p>
	 * Aggregate using Aggregation Work No. and set it in the array. (
	 * {@link #collectWorkData(WorkingInformation [] ) collectWorkData()})<BR>
	 * <p>
	 * <DIR>Return the Work Status list set in the array as a return value. <BR>
	 * </DIR> <BR>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned date
	 * @param workForm
	 *            Work Type
	 * @param orderNo
	 *            Order No.  Array
	 * @param areaNo
	 *            Area No.
	 * @param zoneNo
	 *            Zone No.
	 * @param rftNo
	 *            RFT Machine No.
	 * @param workerCode
	 *            Worker Code
	 * @return Array of Order Picking info/Work Status entity
	 * @throws NotFoundException
	 *             Announce when workable data is not found.
	 * @throws InvalidDefineException
	 *             Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws OverflowException
	 *             Announce when the length of value in the numeral field item exceeds its maximum length.
	 */
	public WorkingInformation[] startRetrievalOrder(String consignorCode,
			String planDate, String rftNo, String workerCode)
			throws NotFoundException, InvalidDefineException,
			ReadWriteException, OverflowException
	{
		if (retrievalOrderData == null)
		{
			//#CM720451
			// When workable data is not found.
			return new WorkingInformation[0];
		}

		//#CM720452
		// Renumber the Aggregation Work No.
		reattachCollectNo(retrievalOrderData);

		//#CM720453
		// Obtain from the Worker Name.
		BaseOperate bo = new BaseOperate(wConn);
		String workerName = "";
		try
		{
			workerName = bo.getWorkerName(workerCode);
		}
		catch (NotFoundException e)
		{
			//#CM720454
			// No worker code: Worker master is suspended between the Start process and the Request Data process.
			//#CM720455
			// 6026019=No matching worker data was found in the Worker Master Table. Worker Code: {0}
			RftLogMessage.print(6026019, LogMessage.F_ERROR, CLASS_NAME,
					workerCode);
			NotFoundException ex = new NotFoundException(
					RFTId5330.ANS_CODE_ERROR);
			throw ex;
		}

		//#CM720456
		// Update the status to "Processing".
		try
		{
			updateToWorking(rftNo, workerCode, workerName, retrievalOrderData);
		}
		catch (NotFoundException e)
		{
			String errData = "[ConsignorCode:" + consignorCode + " PlanDate:"
					+ planDate + " RftNo:" + rftNo + " WorkerCode:"
					+ workerCode + "]";
			//#CM720457
			// 6026016=No data you try to update is found. {0}
			RftLogMessage.print(6026016, LogMessage.F_ERROR, CLASS_NAME,
					errData);
			//#CM720458
			// Return the Status flag as  "Error". 
			NotFoundException ex = new NotFoundException(
					RFTId5330.ANS_CODE_ERROR);
			throw ex;
		}

		//#CM720459
		// Aggregate the Work Status.
		WorkingInformation[] retRetrievalOrderData = collectWorkData(retrievalOrderData);

		return retRetrievalOrderData;
	}

	//#CM720460
	/**
	 * Obtain the work data for specifying Area. <BR>
	 * <OL>
	 * <LI>Obtain the Picking data that corresponds to the Area No. and the Zone No. obtained from the parameter, (
	 * {@link #getWorkableData(String,String,String,String [] ,String,String,String,String,String) getWorkableData()})
	 * <BR>
	 * <LI>Determine the Order No. to be worked. <BR>
	 * <CODE>PickingOrderScheduler</CODE> class
	 * {@link jp.co.daifuku.wms.retrieval.rft.PickingOrderScheduler#getTargetOrderNo(String,String,WorkingInformation [] ) (getTargetOrderNo())}
	 * method
	 * <LI>Obtain the work data with the determined Order No. in the order of ascending order from the work with values equal to both designated Area No. and Zone No. or greater, store it in the 
	 * <CODE>WorkingInformation</CODE> array. --(a)
	 * <LI>Obtain the work data with the determined Order No. in the order of ascending order from the work with values smaller than both designated Area No. and Zone No. and store it in the 
	 * <CODE>WorkingInformation</CODE> array. --(b)
	 * <LI> Combine the (a) and (b) arrays. 
	 * {@link #getWorkData(String,String,String,String,String,String,String,String) (getWorkData())}
	 * <LI>Obtain the Order Picking work data from the array 5.
	 * {@link #startRetrievalOrder(String,String,String,String [] ,String,String,String,String) (startRetrievalOrder())}
	 * <BR>
	 * Renumber the Aggregation Work No.
	 * {@link #reattachCollectNo(WorkingInformation [] ) (rattachCollectNo())}
	 * <BR>
	 * Obtain the Worker Name, and update the Work Status and the Picking Plan Info to "Processing".
	 * {@link #updateToWorking(String,String,String,WorkingInformation [] ) (updateToWorking())}
	 * <BR>
	 * Aggregate data to generate an Order Picking data file.
	 * {@link #collectWorkData(WorkingInformation [] ) (collectWorkData())}<BR>
	 * <BR>
	 * </OL>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned Picking Date
	 * @param workForm
	 *            Case/Piece division
	 * @param areaNo
	 *            Area No.
	 * @param zoenNo
	 *            Zone No.
	 * @param rftNo
	 *            Terminal No.
	 * @param workerCode
	 *            Worker Code
	 * @return workinfo Array of Area-specified Order Picking info entity
	 * @throws LockTimeOutException
	 *             Announce when database lock is not cancelled for the specified period.
	 * @throws IllegalAccessException
	 *             Announce when failed to generate instance.
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws OverflowException
	 *             Announce when the length of value in the numeral field item exceeds its maximum length.
	 * @throws InvalidDefineException
	 *             Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws NotFoundException
	 *             Announce when workable data is not found.
	 */
	public WorkingInformation[] getRetrievalAreaData(String consignorCode,
			String planDate, String workForm, String areaNo, String zoneNo,
			String rftNo, String workerCode) throws LockTimeOutException,
			IllegalAccessException, NotFoundException, InvalidDefineException,
			ReadWriteException, OverflowException
	{

		retrievalOrderData = getWorkableData(consignorCode, planDate, workForm,
				null, areaNo, zoneNo, rftNo, workerCode,
				RFTId0330.JOB_TYPE_AREA);
		//#CM720461
		// Obtain the Area-specified Order Picking data.
		//#CM720462
		// If failed to obtain Picking data:
		if (retrievalOrderData.length == 0)
		{
			retrievalOrderData[0] = pickUpAndCheckOrderData(consignorCode,
					planDate, workForm, null, rftNo, workerCode, areaNo,
					zoneNo, RFTId0330.JOB_TYPE_AREA);
			return null;
		}

		retrievalOrderData = getWorkData(retrievalOrderData, areaNo, zoneNo);
		
		return startRetrievalOrder(consignorCode, planDate, rftNo, workerCode);

	}

	//#CM720463
	/**
	 * Obtain the work data for specifying Order. <BR>
	 * <OL>
	 * <LI>Search for all the Orders designated as a Parameter, through the Work status.
	 * {@link #getWorkableData(String,String,String,String [] ,String,String,String,String,String) (getWorkableData())}
	 * <BR>
	 * Check for presence of data possible to work through the search result.
	 * {@link #isWorkableOrders(String,String,String,String [] ,String,String) (isWorkableOrders())}
	 * <BR>
	 * <BR>
	 * <LI>Process depending on the return value of <CODE>isWorkableOrders()</CODE> 
	 * <UL type="disc">
	 * <LI>If the returned value is  <CODE>true</CODE> : all of the target Orders are workable. <BR>
	 * Obtain the Order Picking work data.
	 * {@link #startRetrievalOrder(String,String,String,String [] ,String,String,String,String) (startRetrievalOrder())}
	 * <BR>
	 * Renumber the Aggregation Work No.
	 * {@link #reattachCollectNo(WorkingInformation [] ) (rattachCollectNo())}
	 * <BR>
	 * Obtain the Worker Name, and update the Work Status and the Picking Plan Info to "Processing".
	 * {@link #updateToWorking(String,String,String,WorkingInformation [] ) (updateToWorking())}
	 * <BR>
	 * Aggregate data to generate an Order Picking data file.
	 * {@link #collectWorkData(WorkingInformation [] ) (collectWorkData())}<BR>
	 * <BR>
	 * <LI>If the returned value is  <CODE>false</CODE> : Disable to work any or all of Orders. <BR>
	 * Search for the Work status.
	 * {@link #getOrderDataList(String,String,String,String) (getOrderDataList())}
	 * <BR>
	 * Check the order status of the designated Order.
	 * {@link #pickUpAndCheckOrderData(String,String,String,String,String,String,String,String) (picUpAndCheckOrderData())}
	 * <BR>
	 * </UL>
	 * </OL>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned Picking Date
	 * @param workForm
	 *            Case/Piece division
	 * @param orderNo
	 *            Array of Order No.
	 * @param rftNo
	 *            Terminal No.
	 * @param workerCode
	 *            Worker Code
	 * @return workinfo Array of Area-specified Order Picking info entity
	 * @throws IllegalAccessException
	 *             Announce when failed to generate instance.
	 * @throws LockTimeOutException
	 *             Announce when database lock is not cancelled for the specified period.
	 * @throws NotFoundException
	 *             Announce when workable data is not found.
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws OverflowException
	 *             Announce when the length of value in the numeral field item exceeds its maximum length.
	 * @throws InvalidDefineException
	 *             Announce when the designated value is abnormal (blank or containing prohibited characters).
	 */
	public WorkingInformation[] getRetrievalOrderData(String consignorCode,
			String planDate, String[] orderNo, String workForm, String rftNo,
			String workerCode) throws IllegalAccessException,
			LockTimeOutException, NotFoundException, InvalidDefineException,
			ReadWriteException, OverflowException
	{
		if (isWorkableOrders(consignorCode, planDate, workForm, orderNo, rftNo,
				workerCode))
		{
			return startRetrievalOrder(consignorCode, planDate, rftNo, workerCode);
		}
		return null;
	}

	//#CM720464
	/**
	 * Search for data with the designated Order No. from the array of the Order Picking data. <BR>
	 * Return the leading data of the corresponding data. Return null if no corresponding data found. <BR>
	 * 
	 * @param orderNo
	 *            Order No.
	 * @param data
	 *            Array of Order Picking info (Work Status Entity)
	 * @return Order Picking info (Work Status Entity)
	 */
	public WorkingInformation pickUpOrderData(String orderNo,
			WorkingInformation[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			if (orderNo.equals(data[i].getOrderNo()))
			{
				return data[i];
			}
		}
		return null;
	}

	//#CM720465
	/**
	 * Check the status of the data with the designated Order No. <BR>
	 * 1.If some data with Picking Work division "Order" maintains array of Order Picking data, search for the data with the designated Order No., and <BR>
	 * return the leading data of the corresponding data. If no corresponding data found, search through the Work status using the following condition. <BR>
	 * <DIR><table BORDER="1">
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Work division</td>
	 * <td>03 (Picking) </td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>other than 9 (Deleted)</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * </table> </DIR> 2.If the Specify Area is ON, search through the Work Status in the following condition, and set the corresponding error status and throw it. <BR>
	 * <DIR><table BORDER="1">
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Area No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Zone No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Work division</td>
	 * <td>03 (Picking) </td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>other than 9 (Deleted)</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * </table> </DIR>
	 * <p>
	 * No workable data existing throws NotFoundException that is set to  8 (No corresponding data) for the Detail Message. <BR>
	 * Check the Status flag in the obtained data, and if one or more "Processing" status is found, throw NotFoundException that is set to 
	 * 1 ("Processing via other terminal") for the Detail Message. <BR>
	 * When all data are "Completed", throw NotFoundException that is set to 2 (Work Completed) for the Detail Message. <BR>
	 * <BR>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned date
	 * @param workForm
	 *            Work Type
	 * @param orderNo
	 *            Order No.
	 * @param rftNo
	 *            RFT Machine No.
	 * @param workerCode
	 *            Worker Code
	 * @param areaNo
	 *            Area No.
	 * @param zoneNo
	 *            Zone No.
	 * @param retrievalJobType
	 *            Picking Work division
	 * @return Order Picking info (Work Status Entity)
	 * @throws NotFoundException
	 *             Announce when workable data is not found.
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws LockTimeOutException
	 *             Announce when database lock is not cancelled for the specified period.
	 */
	public WorkingInformation pickUpAndCheckOrderData(String consignorCode,
			String planDate, String workForm, String orderNo, String rftNo,
			String workerCode, String areaNo, String zoneNo,
			String retrievalJobType) throws NotFoundException,
			ReadWriteException, LockTimeOutException
	{

		//#CM720466
		// Search through the WorkingInfo.
		WorkingInformation[] retrievalData = null;
		//#CM720467
		// For Order-specified data:
		if (retrievalJobType.equals(RFTId0330.JOB_TYPE_ORDER))
		{
			if (retrievalOrderData != null)
			{
				for (int i = 0; i < retrievalOrderData.length; i++)
				{
					if (orderNo.equals(retrievalOrderData[i].getOrderNo()))
					{
						return retrievalOrderData[i];
					}
				}
			}
			retrievalData = getOrderDataList(consignorCode, planDate, orderNo,
					workForm, null, null, retrievalJobType);
		}
		//#CM720468
		// For Area-specified data:
		else
		{
			retrievalData = getOrderDataList(consignorCode, planDate, orderNo,
					workForm, areaNo, zoneNo, retrievalJobType);
			
		}

		//#CM720469
		// If no corresponding data found, return the Status flag as  "8: No corresponding data". 
		if (retrievalData.length == 0 || retrievalData == null)
		{
			NotFoundException e = new NotFoundException(RFTId5330.ANS_CODE_NULL);
			throw e;
		}
		boolean isWorking = false;
		for (int i = 0; i < retrievalData.length; i++)
		{
			//#CM720470
			// If "Standby" or "Processing" data, of which work was started by own terminal, was found in the corresponding data, return this found data (normal)
			if (retrievalData[i].getStatusFlag().equals(
					WorkingInformation.STATUS_FLAG_UNSTART)
					|| (retrievalData[i].getStatusFlag().equals(
							WorkingInformation.STATUS_FLAG_NOWWORKING)
							&& (retrievalData[i].getTerminalNo().equals(rftNo)) && (retrievalData[i]
							.getWorkerCode().equals(workerCode))))
			{
				return retrievalData[i];
			}

			if (retrievalData[i].getStatusFlag().equals(
					WorkingInformation.STATUS_FLAG_NOWWORKING)
					|| retrievalData[i].getStatusFlag().equals(
							WorkingInformation.STATUS_FLAG_START))
			{
				isWorking = true;
			}
		}
		if (isWorking)
		{
			//#CM720471
			// If data with status "Processing" or "Started" is found in the corresponding data, return the Status flag as "1: "Processing via other terminal". 
			NotFoundException e = new NotFoundException(
					RFTId5330.ANS_CODE_WORKING);
			
			throw e;
		}
		else
		{
			//#CM720472
			// If data with status "Processing" or "Started" is not found in the corresponding data, return the Status flag as "2: Work Completed". 
			NotFoundException e = new NotFoundException(
					RFTId5330.ANS_CODE_COMPLETION);
			throw e;
		}
	}

	//#CM720473
	/**
	 * Obtain the Order Picking data (workable data). <BR>
	 * Search through the work status using the following conditions. <BR>
	 * <BR>
	 *  [Work division of Parameter is "1: Order" ]  <DIR>Obtain the work data of the designated Order No. attached with "For Update".  <BR>
	 * <OL>
	 * <LI> [search conditions]  <table BORDER="1">
	 * <tr>
	 * <td>Work division</td>
	 * <td>03:Picking</td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>0: Standby, or, 2: Processing. Require the data to have the same Worker Code and the same RFTNo.</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * </table> <BR>
	 * <LI>Sorting order: Area > Zone > Location
	 * </OL>
	 * </DIR> <BR>
	 *  [Work division of Parameter is "2: Area"]  <DIR>
	 * Obtain the workable data, with the designated Area No. and Zone No. as well as with Order No. that is not blank. <BR>
	 * <BR>
	 * <OL>
	 * <LI> [search conditions]: Obtain the work data in the designated Area No. or Zone No.  <table BORDER="1">
	 * <tr>
	 * <td>Work division</td>
	 * <td>03:Picking</td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>0: Standby, or, 2: Processing. Require the data to have the same Worker Code and the same RFTNo.</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Area No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Zone No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Argument parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>other than empty</td>
	 * </tr>
	 * </table>
	 * <p>
	 * <BR>
	 * <LI>Lock all the Work Status of the obtained Order No. <BR>
	 *  [search conditions]  <table BORDER="1">
	 * <tr>
	 * <td>Work division</td>
	 * <td>03:Picking</td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>0: Standby, or, 2: Processing. Require the data to have the same Worker Code and the same RFTNo.</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Argument parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>Determined Order No.</td>
	 * </tr>
	 * </table> <BR>
	 * <LI>Pass the obtained work data to the class ( <CODE>PickingOrderScheduler
	 * </CODE> ) to determine the order for specifying area, and obtain the Order No. <BR>
	 * <BR>
	 * <LI> [search conditions] : Obtain all the Work data of the corresponding Order using the determined Order No. as a search conditions. <table BORDER="1">
	 * <tr>
	 * <td>Work division</td>
	 * <td>03:Picking</td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>0: Standby, or, 2: Processing. Require the data to have the same Worker Code and the same RFTNo.</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Argument parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>Determined Order No.</td>
	 * </tr>
	 * </table>
	 * 
	 * </OL>
	 * </DIR> <BR>
	 * Failing to obtain workable data to work throws NotFoundException that is set to  8 (No corresponding data) for the Detail Message. <BR>
	 * <BR>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned date
	 * @param areaNo
	 *            Area No.
	 * @param zoneNo
	 *            Zone No.
	 * @param orderNo
	 *            Array of Order No.
	 * @param workForm
	 *            Work Type
	 * @param rftNo
	 *            RFT Machine No.
	 * @param workerCode
	 *            Worker Code
	 * @param retrievalType
	 *            Picking Work division (1: Order 2: Area)
	 * @return Specify Area Picking data
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws LockTimeOutException
	 *             Announce when database lock is not cancelled for the specified period.
	 * @throws IllegalAccessException
	 *             Announce when failed to generate instance.
	 * @throws NotFoundException
	 *             Throw when all data are "Completed".
	 * @throws OverflowException
	 *             Announce when the length of value in the numeral field item exceeds its maximum length.
	 */
	protected WorkingInformation[] getWorkableData(String consignorCode,
			String planDate, String workForm, String[] orderNo, String areaNo,
			String zoneNo, String rftNo, String workerCode, String retrievalType)
			throws ReadWriteException, LockTimeOutException,
			IllegalAccessException
	{
		//#CM720474
		// Variable for maintaining the result of searching through WorkingInfo
		WorkingInformation[] workableRetrievalData = null;
		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
		//#CM720475
		// For data with Picking Work division "Order":
		if (retrievalType.equals(RFTId0330.JOB_TYPE_ORDER))
		{
			//#CM720476
			// Obtain the valid Order from the array of Order No.
			Vector validOrderList = new Vector();
			for (int i = 0; i < orderNo.length; i++)
			{
				if (!(orderNo[i] == null || orderNo[i].trim().equals("")))
				{
					validOrderList.add(orderNo[i]);
				}
			}
			if (validOrderList.size() == 0)
			{
				return new WorkingInformation[0];
			}

			//#CM720477
			//-----------------
			//#CM720478
			// Search through the Work Status.
			//#CM720479
			//-----------------
			//#CM720480
			// Target the data with Work division "Picking". 
			pskey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM720481
			// Target the data with Status flag "Standby" or "Processing" (only for data with the same Worker Code and the same RFTNo.)
			//#CM720482
			//  SQL statement ---  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG
			//#CM720483
			// = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND
			//#CM720484
			// DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=",
					"(", "", "OR");
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=",
					"(", "", "AND");
			pskey.setWorkerCode(workerCode);
			pskey.setTerminalNo(rftNo, "=", "", "))", "AND");

			//#CM720485
			// Target the data with Start Work flag "1: "Started" ".
			pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			pskey.setConsignorCode(consignorCode);
			pskey.setPlanDate(planDate);
			if (!workForm.equals(RFTId0330.WORK_FORM_All))
			{
				pskey.setWorkFormFlag(workForm);
			}

			//#CM720486
			// Target the Order that is contained in the array.
			pskey.setOrderNo((String[]) (validOrderList
					.toArray(new String[validOrderList.size()])));

			pskey.setAreaNoOrder(1, true);
			pskey.setZoneNoOrder(2, true);
			pskey.setLocationNoOrder(3, true);

			try
			{
				workableRetrievalData = (WorkingInformation[]) pObj
						.findForUpdate(pskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
			}
			catch (LockTimeOutException e)
			{
				//#CM720487
				// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
				RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME,
						"DNWORKINFO");
				throw e;
			}
			if (workableRetrievalData.length == 0 || workableRetrievalData == null)
			{
				//#CM720488
				// When workable data is not found.
				return new WorkingInformation[0];
			}
		}
		else
		{

			//#CM720489
			//-----------------
			//#CM720490
			// Search through the Work Status.
			//#CM720491
			//-----------------
			//#CM720492
			// Target the data with Work division "Picking". 
			pskey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM720493
			// Target the data with Status flag "Standby" or "Processing" (only for data with the same Worker Code and the same RFTNo.)
			//#CM720494
			//  SQL statement ---  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG
			//#CM720495
			// = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND
			//#CM720496
			// DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=",
					"(", "", "OR");
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=",
					"(", "", "AND");
			pskey.setWorkerCode(workerCode);
			pskey.setTerminalNo(rftNo, "=", "", "))", "AND");

			//#CM720497
			// Target the data with Start Work flag "1: "Started" ".
			pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			pskey.setConsignorCode(consignorCode);
			pskey.setPlanDate(planDate);
			if (!workForm.equals(RFTId0330.WORK_FORM_All))
			{
				pskey.setWorkFormFlag(workForm);
			}
			pskey.setAreaNo(areaNo); // エリアNo
			pskey.setZoneNo(zoneNo); // ゾーンNo
			pskey.setOrderNo("", "IS NOT NULL");

			pskey.setOrderNoOrder(1, true);

			try
			{
				workableRetrievalData = (WorkingInformation[]) pObj
						.findForUpdate(pskey, WmsParam.WMS_DB_LOCK_TIMEOUT);
			}
			catch (LockTimeOutException e)
			{
				//#CM720498
				// 6026018=Data base lock was not released even after the certain period of time. Table Name: {0}
				RftLogMessage.print(6026018, LogMessage.F_WARN, CLASS_NAME,
						"DNWORKINFO");
				throw e;
			}
			if (workableRetrievalData.length == 0 || workableRetrievalData == null)
			{
				//#CM720499
				// When workable data is not found.
				return new WorkingInformation[0];
			}

			PickingOrderScheduler areaPickingOrderSc = (PickingOrderScheduler) PickingOrderSchedulerFactory
					.getInstance();
			String decidedOorderNo = areaPickingOrderSc.getTargetOrderNo(
					areaNo, zoneNo, workableRetrievalData);

			//#CM720500
			//-----------------
			//#CM720501
			// Search through the Work Status.
			//#CM720502
			//-----------------
			pskey.KeyClear();
			//#CM720503
			// Target the data with Work division "Picking". 
			pskey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
			//#CM720504
			// Target the data with Status flag "Standby" or "Processing" (only for data with the same Worker Code and the same RFTNo.)
			//#CM720505
			//  SQL statement ---  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG
			//#CM720506
			// = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND
			//#CM720507
			// DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=",
					"(", "", "OR");
			pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=",
					"(", "", "AND");
			pskey.setWorkerCode(workerCode);
			pskey.setTerminalNo(rftNo, "=", "", "))", "AND");

			//#CM720508
			// Target the data with Start Work flag "1: "Started" ".
			pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			pskey.setConsignorCode(consignorCode);
			pskey.setPlanDate(planDate);
			if (!workForm.equals(RFTId0330.WORK_FORM_All))
			{
				pskey.setWorkFormFlag(workForm);
			}
			pskey.setOrderNo(decidedOorderNo);
			pskey.setAreaNoOrder(1, true);
			pskey.setZoneNoOrder(2, true);
			pskey.setLocationNoOrder(3, true);

			workableRetrievalData = (WorkingInformation[]) pObj
					.findForUpdate(pskey);
		}
		return workableRetrievalData;
	}

	//#CM720509
	/**
	 * Obtain the Order Picking data. <BR>
	 * Search through the work status using the following conditions. <BR>
	 * <DIR>For Order-specified data: <table BORDER="1">
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Case/Piece division (Work Type) </td>
	 * <td>Obtain from the Argument parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Work division</td>
	 * <td>03 (Picking) </td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>other than 9 (Deleted)  </td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * </table> </DIR> <DIR>For Area-specified data <table BORDER = "1">
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Area No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Zone No.</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Case/Piece division (Work Type) </td>
	 * <td>Obtain from the Argument parameter (Disable to use it for a search conditions if it is "0: All")</td>
	 * </tr>
	 * <tr>
	 * <td>Work division</td>
	 * <td>03 (Picking) </td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>other than 9 (Deleted)  </td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>1 ("Started") </td>
	 * </tr>
	 * </table>
	 * 
	 * <p>
	 * Return the obtained data in the form of array of Work Status entity. <BR>
	 * Obtaining no corresponding data returns empty array. <BR>
	 * <BR>
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned date
	 * @param workForm
	 *            Work Type
	 * @return Picking Plan Info (Array of Work Status entity)
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 * @throws LockTimeOutException
	 *             Announce when database lock is not cancelled for the specified period.
	 */
	protected WorkingInformation[] getOrderDataList(String consignorCode,
			String planDate, String orderNo, String workForm, String areaNo,
			String zoneNo, String retrievalJobType) throws ReadWriteException,
			LockTimeOutException
	{
		WorkingInformationSearchKey pskey = new WorkingInformationSearchKey();
		WorkingInformationHandler pObj = new WorkingInformationHandler(wConn);
		//#CM720510
		//-----------------
		//#CM720511
		// Search through the Work Status.
		//#CM720512
		//-----------------
		//#CM720513
		// Target the data with Work division "Picking". 
		pskey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM720514
		// Target the data with Status flag other than "Deleted".
		pskey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM720515
		// Target the data with Start Work flag "1: "Started" ".
		pskey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		pskey.setConsignorCode(consignorCode);
		pskey.setPlanDate(planDate);
		if (!workForm.equals(RFTId0330.WORK_FORM_All))
		{
			pskey.setWorkFormFlag(workForm);
		}
		if (retrievalJobType.equals(RFTId0330.JOB_TYPE_ORDER))
		{
			pskey.setOrderNo(orderNo);
			pskey.setAreaNoOrder(1, true);
			pskey.setZoneNoOrder(2, true);
			pskey.setLocationNoOrder(3, true);
		}
		else
		{
			pskey.setAreaNo(areaNo);
			pskey.setZoneNo(zoneNo);
			pskey.setOrderNoOrder(1, true);
		}

		WorkingInformation[] retrievalData = (WorkingInformation[]) pObj
				.findForUpdate(pskey, 1);
		return retrievalData;
	}

	//#CM720516
	/**
	 * Execute the process for renumbering the Aggregation Work No. <BR>
	 * <p>
	 * From the array of Work Status entity received as a parameter, 
	 * renumber the Aggregation Work No. of allocatable data. Update the corresponding Work Status. <BR>
	 *  (Obtain from the presence of aggregation from the Parameter file (Obtain from  <CODE>WMSParam.properties</CODE> ))  <BR>
	 * <p>
	 * Renumber the Aggregation Work No. before aggregation (Use the Aggregation Work No. of the leading data of the allocation target). <BR>
	 * Update the data to the result from renumbering into the Aggregation Work No. in the Work Status. <BR>
	 * <p>
	 * Prepare two patterns: one pattern that aggregates Aggregation key field items with Area No., Zone No., Location + Item Code + Work Type + Expiry Date + Order No., and 
	 * another pattern that aggregates them with Area No., Zone No,. Location + Item Code + Work Type + Expiry Date + Order No. + ITF + Bundle ITF, and
	 * obtain the choice of pattern from the Parameter file. <BR>
	 * <p>
	 *  !Attention!  <BR>
	 * Allow this method only to renumber the Aggregation Work No. based on the aggregation condition and do not allow it to aggregate.) <BR>
	 * <BR>
	 * 
	 * @param retrievalOrderData
	 *            Require to sort in the order of Array of Work Status Entity (Area > Zone > Location > Item Code > Case/Piece division > Expiry Date > Order No. > ITF > Bundle ITF)
	 * @throws NotFoundException
	 *             Announce when no data is found in the database to be updated.
	 * @throws InvalidDefineException
	 *             Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 */
	protected void reattachCollectNo(WorkingInformation[] retrievalOrderData)
			throws NotFoundException, InvalidDefineException,
			ReadWriteException
	{
		//#CM720517
		// String for "Aggregation key: Item Code". Otherwise, determine by "Aggregation key: Item Code + ITF + Bundle ITF".
		final int STR_ITEMCODE = 1;

		//#CM720518
		// Process for aggregating work
		boolean collectItemCodeOnly = false;

		if (WmsParam.RETRIEVAL_JOBCOLLECT_KEY == STR_ITEMCODE)
		{
			collectItemCodeOnly = true;
		}

		if (WmsParam.RETRIEVAL_JOBCOLLECT)
		{
			for (int i = 0; i < retrievalOrderData.length - 1; i++)
			{
				if ((retrievalOrderData[i].getAreaNo().equals(retrievalOrderData[i + 1].getAreaNo())
						&&(retrievalOrderData[i].getZoneNo()
								.equals(retrievalOrderData[i + 1].getZoneNo())))
						&&(retrievalOrderData[i].getLocationNo()
								.equals(retrievalOrderData[i + 1].getLocationNo()))
						&& (retrievalOrderData[i].getItemCode()
								.equals(retrievalOrderData[i + 1].getItemCode()))
						&& (retrievalOrderData[i].getWorkFormFlag()
								.equals(retrievalOrderData[i + 1].getWorkFormFlag()))
						&& (retrievalOrderData[i].getUseByDate()
								.equals(retrievalOrderData[i + 1].getUseByDate()))
						&& (retrievalOrderData[i].getOrderNo()
								.equals(retrievalOrderData[i + 1].getOrderNo()))
						&& (collectItemCodeOnly || retrievalOrderData[i].getItf()
								.equals(retrievalOrderData[i + 1].getItf()))
						&& (collectItemCodeOnly || retrievalOrderData[i].getBundleItf()
								.equals(retrievalOrderData[i + 1].getBundleItf())))
				{
					//#CM720519
					// Renumber the Aggregation Work No. of data with the same key
					retrievalOrderData[i + 1].setCollectJobNo(retrievalOrderData[i].getCollectJobNo());
					//#CM720520
					//-----------------
					//#CM720521
					// Update Work Status.  (Update the record of which Aggregation Work No. was modified) 
					//#CM720522
					//-----------------
					WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();
					//#CM720523
					// Set the Update condition.
					workingAlterKey.setJobNo(retrievalOrderData[i + 1]
							.getJobNo());
					//#CM720524
					// Set the content to be updated.
					workingAlterKey.updateCollectJobNo(retrievalOrderData[i + 1].getCollectJobNo());
					workingAlterKey.updateLastUpdatePname(PROCESS_NAME);

					WorkingInformationHandler workingHandler = new WorkingInformationHandler(wConn);
					//#CM720525
					// "Update" Process
					workingHandler.modify(workingAlterKey);
				}
			}
		}
	}

	//#CM720526
	/**
	 * Update the work data to "Processing". <BR>
	 * Update the Work Status and the Picking Plan Info corresponding to the array of the Work Status entity received as a parameter, to "Processing". <BR>
	 * <BR>
	 *  [Update Picking Plan Info]  <BR>
	 * <BR>
	 * <DIR> [Update Conditions]  <BR>
	 * <table BORDER="1">
	 * <tr>
	 * <td>Picking unique key</td>
	 * <td>Obtain from the Work Status.</td>
	 * </tr>
	 * </table> <BR>
	 *  [Array to be updated]  <BR>
	 * <table BORDER="1">
	 * <tr>
	 * <td>Status flag</td>
	 * <td>2 Processing</td>
	 * </tr>
	 * <tr>
	 * <td>Last update date/time</td>
	 * <td>Date on system</td>
	 * </tr>
	 * <tr>
	 * <td>Last update process name</td>
	 * <td>ID0330</td>
	 * </tr>
	 * </table> </DIR> <BR>
	 *  [Update Work Status.]  <BR>
	 * <DIR> [Update Conditions]  <BR>
	 * <table BORDER="1">
	 * <tr>
	 * <td>Work No.</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work division</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Start Work flag</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Planned Picking Date</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Consignor Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Work Type</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Order No.</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Area No.</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Zone No.</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Location No.</td>
	 * <td>Obtain from the array of Work Status entity of the parameter.</td>
	 * </tr>
	 * </table> <BR>
	 *  [Array to be updated]  <BR>
	 * <table BORDER="1">
	 * <tr>
	 * <td>Aggregation Work No.</td>
	 * <td>Obtain from the array of Work Status entity in the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Status flag</td>
	 * <td>2 Processing</td>
	 * </tr>
	 * <tr>
	 * <td>Worker Code</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Worker Name</td>
	 * <td>Obtain from the Worker info.</td>
	 * </tr>
	 * <tr>
	 * <td>Terminal RFT</td>
	 * <td>Obtain from the Parameter.</td>
	 * </tr>
	 * <tr>
	 * <td>Last update date/time</td>
	 * <td>Date on system</td>
	 * </tr>
	 * <tr>
	 * <td>Last update process</td>
	 * <td>ID0330</td>
	 * </tr>
	 * </table> </DIR> <BR>
	 * 
	 * @param rftNo
	 *            Terminal RFT No.
	 * @param workingData
	 *            Array of Work Status entity (Work type, Status flag, and Start Work flag)
	 * @throws NotFoundException
	 *             Announce when no data is found in the database to be updated.
	 * @throws InvalidDefineException
	 *             Announce when the designated value is abnormal (blank or containing prohibited characters).
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 */
	protected void updateToWorking(String rftNo, String workerCode,
			String workerName, WorkingInformation[] workingData)
			throws NotFoundException, InvalidDefineException,
			ReadWriteException
	{
		for (int i = 0; i < workingData.length; i++)
		{
			//#CM720527
			// Update the status to "Processing" (Update the Work Status and the Picking Plan Info).
			//#CM720528
			//-----------------
			//#CM720529
			// Update Work Status.
			//#CM720530
			//-----------------
			WorkingInformationAlterKey workingAlterKey = new WorkingInformationAlterKey();
			//#CM720531
			// Set the Update condition.
			workingAlterKey.setJobNo(workingData[i].getJobNo());
			//#CM720532
			// Set the content to be updated.
			workingAlterKey
					.updateStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			workingAlterKey.updateTerminalNo(rftNo);
			workingAlterKey.updateWorkerCode(workerCode);
			workingAlterKey.updateWorkerName(workerName);
			workingAlterKey.updateLastUpdatePname(PROCESS_NAME);
			WorkingInformationHandler workingHandler = new WorkingInformationHandler(
					wConn);
			//#CM720533
			// "Update" Process
			workingHandler.modify(workingAlterKey);

			//#CM720534
			//-----------------
			//#CM720535
			// Update the Picking Plan Info.
			//#CM720536
			//-----------------
			RetrievalPlanAlterKey retrievalAlterKey = new RetrievalPlanAlterKey();
			//#CM720537
			// Set the Update condition.
			retrievalAlterKey
					.setRetrievalPlanUkey(workingData[i].getPlanUkey());
			//#CM720538
			// Set the content to be updated.
			retrievalAlterKey
					.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			retrievalAlterKey.updateLastUpdatePname(PROCESS_NAME);
			RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(
					wConn);
			//#CM720539
			// "Update" Process
			retrievalHandler.modify(retrievalAlterKey);
		}
	}

	//#CM720540
	/**
	 * Aggregate the work data. <BR>
	 * <p>
	 * Aggregate the the array of the Work Status entity received as a parameter, using Aggregation Work No. as a key. <BR>
	 * Sum up the instructed qty and the result qty, and data with array[0] as for other field items, to aggregate data if necessary. <BR>
	 * Return the aggregation result in the form of array of Work Status entity. <BR>
	 * 
	 * @param workingData
	 *            Array of Work Status entity
	 * @return Picking work data (Array of Work Status entity)
	 */
	protected WorkingInformation[] collectWorkData(
			WorkingInformation[] workingData) throws OverflowException
	{
		//#CM720541
		// Process for aggregating work
		Vector v = new Vector();

		int jobType = SystemParameter.getJobType(workingData[0].getJobType());

		//#CM720542
		// To renumber the Aggregation Work No. (for aggregation):
		WorkingInformation currentData = (WorkingInformation) workingData[0]
				.clone();
		for (int i = 1; i < workingData.length; i++)
		{
			if (SystemParameter.isRftWorkCollect(jobType))
			{
				if (currentData.getCollectJobNo().equals(
						workingData[i].getCollectJobNo()))
				{
					try
					{
						//#CM720543
						// Aggregate the Work Status.
						currentData.collect(workingData[i]);
					}
					catch (OverflowException e)
					{
						//#CM720544
						// 6026028=Overflow occurred during record integration processing. Table Name: {0}
						RftLogMessage.print(6026028, LogMessage.F_ERROR,
								CLASS_NAME, "DNWORKINFO");
						throw e;
					}
				}
				else
				{
					//#CM720545
					// Add the key if changed.
					v.addElement(currentData);

					//#CM720546
					// Copy the work status for maintaining the aggregation result.
					currentData = (WorkingInformation) workingData[i].clone();
				}
			}
		}

		//#CM720547
		// Add the last data to Vector.
		v.addElement(currentData);

		if (SystemParameter.isRftWorkCollect(jobType))
		{
			//#CM720548
			// Return the aggregated data for aggregation.
			WorkingInformation[] collectedData = new WorkingInformation[v
					.size()];
			v.copyInto(collectedData);

			return collectedData;
		}
		else
		{
			//#CM720549
			// Return its source data as it is for no aggregation to be executed.
			return workingData;
		}
	}

	//#CM720550
	/**
	 * Sort the work data for specifying Area. <BR>
	 * Return the array sorted so that the data with designated Area No. and Zone No. resides at the leading position.
	 * 
	 * @param workinfo
	 *            Array of Work Status entity
	 * @param areaNo
	 *            Area No.
	 * @param zoneNo
	 *            Zone No.
	 * @return Array of <CODE>WorkingInformation</CODE> class entity of Area-specified Order Picking data
	 */
	protected WorkingInformation[] getWorkData(WorkingInformation[] workinfo,
			String areaNo, String zoneNo)
	{
		WorkingInformation[] resultAreaData = new WorkingInformation[workinfo.length];
		int baseIndex = -1;
		for (int i = 0; i < workinfo.length; i++)
		{
			if (areaNo.equals(workinfo[i].getAreaNo())
					&& zoneNo.equals(workinfo[i].getZoneNo()))
			{
				baseIndex = i;
				break;
			}
		}
		
		if (baseIndex >= 0)
		{
			int j = 0;
			for (int i = baseIndex; i < workinfo.length; i ++, j ++)
			{
				resultAreaData[j] = workinfo[i];
			}

			for (int i = 0; i < baseIndex; i ++, j ++)
			{
				resultAreaData[j] = workinfo[i];
			}
		}
		else
		{
			resultAreaData = workinfo;
		}

		return resultAreaData;
	}

	//#CM720551
	/**
	 * Obtain the standard condition to search through work status to obtain Area-specified Order Picking method.
	 * 
	 * @param consignorCode
	 *            Consignor Code
	 * @param planDate
	 *            Planned Picking Date
	 * @param workForm
	 *            Work Type
	 * @param orderNo
	 *            Order No.
	 * @param rftNo
	 *            Machine No.
	 * @param workerCode
	 *            Worker Code
	 * @return skey SearchKey that is set for standard search conditions.
	 * @throws ReadWriteException
	 *             Announce when error occurs on the database connection.
	 */
	public WorkingInformationSearchKey getSearchKey(String consignorCode,
			String planDate, String workForm, String orderNo, String rftNo,
			String workerCode) throws ReadWriteException
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();

		//#CM720552
		//--------------
		//#CM720553
		// search conditions
		//#CM720554
		//--------------
		skey.KeyClear();
		//#CM720555
		// Target the data with Work division "Picking". 
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM720556
		// Target the data with Status flag "Standby" or "Processing" (only for data with the same Worker Code and the same RFTNo.)
		//#CM720557
		//  SQL statement ---  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG =
		//#CM720558
		// '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND
		//#CM720559
		// DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(",
				"", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(",
				"", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");

		//#CM720560
		// Target the data with Start Work flag "1: "Started" ".
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		skey.setConsignorCode(consignorCode); //荷主コード
		skey.setPlanDate(planDate); //出庫予定日
		if (!workForm.equals(RFTId0330.WORK_FORM_All))
		{
			skey.setWorkFormFlag(workForm);
		}
		skey.setOrderNo(orderNo); //オーダーNo
		skey.setAreaNoOrder(1, true);
		skey.setZoneNoOrder(2, true);
		skey.setLocationNoOrder(3, true);

		return skey;
	}
}

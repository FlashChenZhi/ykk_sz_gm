// $Id: AsWorkDisplaySCH.java,v 1.2 2006/10/30 00:46:34 suresh Exp $
package jp.co.daifuku.wms.asrs.schedule ;

//#CM45818
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkingInformation;


//#CM45819
/**
 * Designer : K.Toda <BR>
 * Maker : Y.Okamura  <BR>
 * <BR>
 * Class to do WEB Work instruction.  <BR>
 * Receive the content input from the screen as parameter, and process Work instruction.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>query()</CODE>Method)<BR><BR><DIR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Station No* <BR></DIR>
 * <BR>
 *   -Retrieve Station information (<CODE>carryinfo</CODE>) and Acquire StationType (StorageorPicking).  <BR>
 *     Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *     It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *     <Search condition> <BR><DIR>
 *      Station No of the Station which was input from the screen. <BR></DIR>
 * <BR>
 *   -Retrieve Work display information (<CODE>operationdisplay</CODE>) and Acquire Transportation Key .  <BR>
 *     Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *     It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *     <Search condition> <BR><DIR>
 *      Corresponding Station No of the work display information, which matches with Station No input from screen. <BR></DIR>
 * <BR>
 *   -Retrieve transportation information table (<CODE>carryinfo</CODE>), and check whether transportation information exists. <BR>
 *     Retrieve the following Work display information when transportation information does not exist. Set the retrieval by one when existing and set the End doing and the item in Return data.  <BR>
 *     Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *     It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *     <Search condition> <BR><DIR>
 *       The above-mentioned Transportation Key is acquired by the Transportation key of corresponding transportation information table. <BR>
 *       Data of same Transportation Flag as StationType acquired by Station retrieval <BR>
 *       However, count the Storage data for Picking.  <BR></DIR>
 * <BR>
 *   -Retrieve Work information table (<CODE>dnworkinfo</CODE>), and acquire each item data displayed on the screen. <BR>
 *     Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *     It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 *     Display it in the order of Consignor Code, Item Code, and order of Case/Piece flag.  <BR>
 * <BR>
 *     <Search condition> <BR><DIR>
 *       System connection Key besides corresponding Work information to Transportation Key of transportation information <BR></DIR>
 * <BR>
 *     <return data><BR><DIR>
 *       Consignor Code <BR>
 *       Consignor Name <BR>
 *       Item code <BR>
 *       Item name <BR>
 *       Lot No. <BR>
 *       Acquires Stock qty which retrieves Inventory information(<CODE>dmstock</CODE>) with Stock ID.  <BR>
 *       Work possible qty  <BR>
 *       Transportation Flag of transportation information <BR>
 *       Storage date & time which retrieves Inventory information(<CODE>dmstock</CODE>) with Stock ID and acquires it <BR></DIR></DIR>
 * <BR>
 * 2.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do the End processing and Disbursement End.  <BR>
 *   Retain the button set this time because of the screen and pass it to schedule Processing as parameter.  <BR>
 *   Method of the execution processing divided by the kind of the pushed button.  <BR>
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     The flag of pushed button is either [End] or [Disbursement End]. * <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     true or false <BR></DIR>
 * <BR>
 *   <Check processing> <BR><DIR>
 *    -The Next day update inside is enabled to be processed.  <BR></DIR>
 * <BR>
 *   <End Processing> <BR><DIR>
 *       1.Retrieve whether Work display information data (<CODE>operationdisplay</CODE>) exists.  <BR>
 *         Output Error Message and finish processing when data does not exist.  <BR>
 *         <Search condition> <BR><DIR>
 *           Acquired Transportation Key of the Work display information data corresponding to Transportation Key that is retrieved when the display is processed.  <BR>
 *           Acquired arrival Station No of the Work display information data corresponding to Station No that is retrieved when the display is processed. <BR></DIR>
 * <BR>
 *       2.Retrieve whether transportation information data (<CODE>carryinfo</CODE>) exists.  <BR>
 *         Output Error Message and finish processing when data does not exist.  <BR>
 *         <Search condition> <BR><DIR>
 *           acquired Transportation Key of the transportation information data corresponding to Transportation Key that is retrieved when the display is processed.  <BR></DIR>
 * <BR>
 *       3.Transportation information is executed, and call operationDisplayUpdateMethod of stationClass   <BR>
 *         in parameter, and execute End Processing.<BR>
 *   </DIR>
 * <BR>
 *   <Disbursement End Processing> <BR><DIR>
 *       1.Retrieve whether Work display information data (<CODE>operationdisplay</CODE>) exists.  <BR>
 *         Output Error Message and finish processing when data does not exist.  <BR>
 *         <Search condition> <BR><DIR>
 *           Acquired Transportation Key of the Work display information data corresponding to Transportation Key that is retrieved when the display is processed.  <BR>
 *           Acquired arrival Station No of the Work display information data corresponding to Station No that is retrieved when the display is processed. <BR></DIR>
 * <BR>
 *       2.Retrieve whether transportation information data (<CODE>carryinfo</CODE>) exists.  <BR>
 *         Output Error Message and finish processing when data does not exist.  <BR>
 *         <Search condition> <BR><DIR>
 *           acquired Transportation Key of the transportation information data corresponding to Transportation Key that is retrieved when the display is processed.  <BR></DIR>
 * <BR>
 *       3.Transportation information is executed, and call unitRetrievalMethod of CarryOperationClass in parameter, and execute Disbursement End Processing. <BR>
 * </DIR>
 * </DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>Y.Okamura</TD><TD>Implementation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:46:34 $
 * @author  $Author: suresh $
 */

public class AsWorkDisplaySCH extends AbstractAsrsControlSCH
{
	//#CM45820
	// Class fields --------------------------------------------------

	//#CM45821
	// Class variables -----------------------------------------------
	//#CM45822
	/**
	 * Processing name
	 */
	private final String CLASS_NAME = "AsWorkDisplaySCH";

	//#CM45823
	// Class method --------------------------------------------------
	//#CM45824
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:46:34 $");
	}

	//#CM45825
	// Constructors --------------------------------------------------
	//#CM45826
	/**
	 * Initialize this Class. 
	 * @param conn Connection object with database
	 * @param kind Process Flag
	 */
	//#CM45827
	/**
	 * Initialize this Class. 
	 */
	public AsWorkDisplaySCH()
	{
		wMessage = null;
	}

	//#CM45828
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn Connection object with database
	 * @param searchParam Class which succeeds to < CODE>Parameter</CODE> Class with Search condition
	 * @return Class which mounts < CODE>Parameter</CODE > interface where retrieval result is included
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		return null;
	}

	//#CM45829
	/**
	 * The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output.  <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsSystemScheduleParameter</CODE>Class with display data acquisition condition.  <BR>
	 *         <CODE>AsSystemScheduleParameter</CODE> ScheduleException when specified excluding the instance is slow. <BR>
	 * @return Have the retrieval result <CODE>AsSystemScheduleParameter</CODE> Array of Instances. <BR>
	 *          Return the empty array when not even one pertinent record is found.  <BR>
	 *          Return null when the error occurs in the input condition.  <BR>
	 *          When null is returned, the content of the error can be acquired as a message in <CODE>getMessage()</CODE>Method. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter param = (AsScheduleParameter) searchParam;
		
		WorkingInformationReportFinder wirf = new WorkingInformationReportFinder(conn);

		Vector vec = new Vector();
		String stationNo = param.getStationNo();

		//#CM45830
		// Displays the oldest among Station No Arrival date and retrieval date. 
		OperationDisplaySearchKey odKey = new OperationDisplaySearchKey();
		OperationDisplayHandler oph = new OperationDisplayHandler(conn);
		odKey.setStationNumber(stationNo);
		odKey.setArrivalDateOrder(1,true);
		OperationDisplay[] operationDisp = (OperationDisplay[])oph.find(odKey);

		//#CM45831
		// Generate Station instance to display the work data corresponding to insertion Picking mode of Station. 
		Station st = null;
		try
		{
			st = StationFactory.makeStation(conn, stationNo);

			CarryInformation carryinfo = null;
			CarryInformationHandler cih = new CarryInformationHandler(conn);
			//#CM45832
			// The string is applied to Work display information, and turn over and retrieved.
			//#CM45833
			// When the string is applied and there is no turning over,  The next string is applied to Work display information, and turn over and retrieved.
			for (int i = 0; i < operationDisp.length; i++)
			{
				CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
				ciKey.setCarryKey(operationDisp[i].getCarryKey());
	
				//#CM45834
				// Retrieve the Storage transportation data when Work mode of Station is Storage. 
				if (st.getCurrentMode() == Station.STORAGE_MODE)
				{
					ciKey.setCarryKind(CarryInformation.CARRYKIND_STORAGE);
				}
				//#CM45835
				// When Work mode of Station is Picking
				//#CM45836
				// Mode switch Type : when in the automatic mode of operation switch, there is no transportation data of Picking in correspondence Station.  
				//#CM45837
				// The Storage transportation data is retrieved. 
				else if (st.getCurrentMode() == Station.RETRIEVAL_MODE)
				{
					if(st.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
					{
						CarryInformationSearchKey rtrvlCarryKey = new CarryInformationSearchKey();
						rtrvlCarryKey.setDestStationNumber(st.getStationNumber());
						rtrvlCarryKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
						if (cih.count(rtrvlCarryKey) > 0)
						{
							ciKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
						}
					}
					else
					{
						ciKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
					}
	
				}
	
				carryinfo = (CarryInformation) cih.findPrimary(ciKey);
				//#CM45838
				// When the pertinent transportation data exists, Ends the retrieval processing here. 
				if (carryinfo != null)
				{
					break;
				}
	
				//#CM45839
				// Retrieve the transportation data of Going directly when there is no transportation data of Storage or Picking. 
				if (st.getCurrentMode() == Station.STORAGE_MODE )
				{
					ciKey.KeyClear();
					ciKey.setCarryKey(operationDisp[i].getCarryKey());
					ciKey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL);
					ciKey.setSourceStationNumber(st.getStationNumber());
					carryinfo = (CarryInformation) cih.findPrimary(ciKey);
				}
				else if (st.getCurrentMode() == Station.RETRIEVAL_REQUEST )
				{
					ciKey.KeyClear();
					ciKey.setCarryKey(operationDisp[i].getCarryKey());
					ciKey.setCarryKind(CarryInformation.CARRYKIND_DIRECT_TRAVEL);
					ciKey.setDestStationNumber(st.getStationNumber());
					carryinfo = (CarryInformation) cih.findPrimary(ciKey);
				}
				
			}
	
			if(carryinfo == null)
			{
				//#CM45840
				// 6003018=There was no object data. .
				setMessage("6003018");
				return new AsScheduleParameter[0];
			}
	
			//#CM45841
			// -------- Setting of Return data. From here. -------- //
			AsScheduleParameter dispData = new AsScheduleParameter();
	
			//#CM45842
			// --- Data for Input area ---//
			//#CM45843
			// Decide Shelf  No. and Transportation Flag for the return from Transportation Flag. 
			String locationNo = "";
			int carryKind = -1;
			//#CM45844
			// When Transportation Flag is Storage, Acquire the transportation destinationStation No.
			if(carryinfo.getCarryKind() == CarryInformation.CARRYKIND_STORAGE)
			{
				locationNo = carryinfo.getDestStationNumber();
				carryKind = AsScheduleParameter.CARRY_KIND_STORAGE;
			}
			//#CM45845
			// When Transportation Flag is Picking, Acquire Transportation originStation No.
			else if(carryinfo.getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL)
			{
				locationNo = carryinfo.getSourceStationNumber();
				carryKind = AsScheduleParameter.CARRY_KIND_RETRIEVAL;
			}
			//#CM45846
			// When Transportation Flag is going directly, Set '----' to Shelf No.
			else if(carryinfo.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
			{
				locationNo = DisplayUtil.getRetrievalDetailValue(carryinfo.getRetrievalDetail());
				carryKind = AsScheduleParameter.CARRY_KIND_DIRECT_TRAVEL;
				//#CM45847
				// Return Transportation originStation No for Going directly. 
				dispData.setFromStationNo(carryinfo.getSourceStationNumber());
			}
			//#CM45848
			// Acquire Retrieval Station No in Shelf No when The detailed delivery instruction is Stock confirmation. 
			if(carryinfo.getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL 
			&& carryinfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
			{
				locationNo = carryinfo.getRetrievalStationNumber();
			}
			
			int operationType = -1;
			//#CM45849
			// Set Work display operationType. 
			if (st.getOperationDisplay() == Station.NOT_OPERATIONDISPLAY)
			{
				operationType = AsScheduleParameter.OPERATION_DISPLAY_NO;
			}
			else if (st.getOperationDisplay() == Station.OPERATIONDISPONLY)
			{
				operationType = AsScheduleParameter.OPERATION_DISP_ONLY;
			}
			else
			{
				operationType = AsScheduleParameter.OPERATION_INSTRUCTION;
			}
	
			//#CM45850
			// Detailed delivery instruction is decided from Transportation Flag. 
			String detail = "";
			if(carryinfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_PICKING
			|| carryinfo.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT)
			{
				detail = DisplayUtil.getRetrievalDetailValue(carryinfo.getRetrievalDetail());
			}
			else
			{
				detail = DisplayUtil.getRetrievalDetailValue(CarryInformation.RETRIEVALDETAIL_UNKNOWN);
			}

			//#CM45851
			// Non-display data
			dispData.setStationNo(stationNo);
			dispData.setCarryKey(carryinfo.getCarryKey());
			dispData.setCarryKind(carryKind);
			dispData.setOperationDisplay(operationType);
			dispData.setWareHouseNo(st.getWHStationNumber());
			//#CM45852
			// Display data
			dispData.setWorkingNo(carryinfo.getWorkNumber());
			dispData.setWorkingType(DisplayUtil.getCarryWorkTypeValue(carryinfo.getWorkType()));
			dispData.setRetrievalDetail(detail);
			dispData.setLocationNo(locationNo);
			vec.addElement(dispData);
	
			//#CM45853
			// --- Data for Filtering area --- //
			//#CM45854
			// Set Display data to acquire, and to return Work information. 
			WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();
			wiKey.setSystemConnKey(carryinfo.getCarryKey());
			//#CM45855
			// The display order set
			wiKey.setConsignorCodeOrder(1, true);
			wiKey.setItemCodeOrder(2, true);
			wiKey.setWorkFormFlagOrder(3, true);
			wiKey.setUseByDateOrder(4, true);
			
			//#CM45856
			// It is assumed the display up to List cell (area of filtering) maximum display number. 
			int count = wirf.search(wiKey);
			if(!canLowerDisplay(count))
			{
				//#CM45857
				// 6023478={0} corresponded, and the {1} of Latest was displayed. Confirm it with List so that the number of cases may exceed the {2}.
				wMessage = "6023478" + wDelim + count + wDelim + 
							WmsParam.MAX_NUMBER_OF_DISP + wDelim + WmsParam.MAX_NUMBER_OF_DISP;
			}

			//#CM45858
			// Acquired from the retrieval result up to List cell (filtering area) maximum display number.
			WorkingInformation[] winfos = (WorkingInformation[]) wirf.getEntities(WmsParam.MAX_NUMBER_OF_DISP);
			if (winfos == null || winfos.length == 0)
			{
				//#CM45859
				// 6006040=No adjustment of data was generated.{0}
				RmiMsgLogClient.write("6006040" + wDelim + "DnWorkinfo", this.getClass().getName()) ;
				//#CM45860
				// 6027006=No adjustment of data was generated.Refer to the log. .TABLE={0}
				throw new ScheduleException("6027006" + wDelim + "DnWorkinfo");
			}
			StockHandler stkh = new StockHandler(conn);
			StockSearchKey stkKey = new StockSearchKey();
			Stock stk = null;
			for(int i = 0; i < winfos.length; i++)
			{
				//#CM45861
				// Apply the string to Work information and acquire turning over Inventory information. 
				stkKey.KeyClear();
				stkKey.setStockId(winfos[i].getStockId());
				try
				{
					stk = (Stock) stkh.findPrimary(stkKey);
				}
				catch (NoPrimaryException e)
				{
					throw new ReadWriteException(e.getMessage());
				}
				
				dispData = new AsScheduleParameter();
				
				//#CM45862
				// When returning Storage and doing with the close system etc. as empty Palette after all qty Picking 
				//#CM45863
				// Set a fixed value because the STOCK table is lost after the stock is updated. 
				if (stk != null)
				{
					dispData.setInStockDate(stk.getInstockDate());
					dispData.setStockCaseQty(
						DisplayUtil.getCaseQty(stk.getStockQty(), stk.getEnteringQty(), stk.getCasePieceFlag()));
					dispData.setStockPieceQty(
						DisplayUtil.getPieceQty(stk.getStockQty(), stk.getEnteringQty(), stk.getCasePieceFlag()));
					dispData.setEnteringQty(stk.getEnteringQty());
					dispData.setBundleEnteringQty(stk.getBundleEnteringQty());
				}
				else
				{
					//#CM45864
					//Fix Stock qty in "0". Storage date & time : like the space of an initial value. 
					dispData.setInStockDate(null);
					dispData.setStockCaseQty(0);
					dispData.setStockPieceQty(0);
					dispData.setEnteringQty(0);
					dispData.setBundleEnteringQty(0);
				}
				dispData.setConsignorCode(winfos[i].getConsignorCode());
				dispData.setConsignorName(winfos[i].getConsignorName());
				dispData.setItemCode(winfos[i].getItemCode());
				dispData.setItemName(winfos[i].getItemName1());
				dispData.setPlanCaseQty(
					DisplayUtil.getCaseQty(
						winfos[i].getPlanEnableQty(), winfos[i].getEnteringQty(), winfos[i].getWorkFormFlag()));
				dispData.setPlanPieceQty(
					DisplayUtil.getPieceQty(
						winfos[i].getPlanEnableQty(), winfos[i].getEnteringQty(), winfos[i].getWorkFormFlag()));
				dispData.setCasePieceFlagNameDisp(DisplayUtil.getPieceCaseValue(winfos[i].getWorkFormFlag()));
	
				vec.addElement(dispData);
			}
	
			AsScheduleParameter[] resultData = new AsScheduleParameter[vec.size()];
			vec.toArray(resultData);
			return resultData;
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		finally
		{
			try
			{
				//#CM45865
				// Do the close processing of the opening data base cursor. 
				wirf.close();
			}
			catch (ReadWriteException e)
			{
				//#CM45866
				// The data base error occurred. Refer to the log. 
				setMessage("6007002");
			}
		}
	}

	//#CM45867
	/**
	 * The content input from the screen is received as parameter, and Starts the Work instruction schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
	 * Return null when the schedule does not End because of the condition error etc.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Set the content <CODE>AsSystemScheduleParameter</CODE>Class Array of Instances. <BR>
	 *         AsSystemScheduleParameter <CODE>ScheduleException</CODE> when things except the instance are specified is slow.<BR>
	 *         It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter param = (AsScheduleParameter) startParams[0];
		
		//#CM45868
		// Do Input check of Worker code and Password. 
		if (!checkWorker(conn, param))
		{
			return false;
		}
		
		//#CM45869
		// Next day update Processing check
		if (isDailyUpdate(conn))
		{
			return false;
		}

		OperationDisplayHandler odh = new OperationDisplayHandler(conn);
		OperationDisplaySearchKey odKey = new OperationDisplaySearchKey();

		odKey.setStationNumber(param.getStationNo());
		odKey.setCarryKey(param.getCarryKey());
		if(odh.count(odKey) == 0)
		{
			//#CM45870
			// 6003018=There was no object data. .
			setMessage("6003018");
			return false;
		}

		if (AsScheduleParameter.BUTTON_COMPLETE.equals(param.getButtonType()))
		{
			if (!complete(conn, param))
			{
				return false;
			}
		}
		else
		{
			if (!completeUnitRetrieval(conn, param))
			{
				return false;
			}
		}
		
		return true;
		
	}
	
	//#CM45871
	/**
	 * Do Work display End Processing. 
	 * Do End Processing based on the item which becomes the key to parameter Array. 
	 * Return true when succeeding in the Maintenance processing and return false when failing. 
	 * The reason can be acquired in <code>getMessage</code> when failing in processing. 
	 * @param conn Connection object with database
	 * @return Return true when succeeding in processing and return false when failing. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated occurs in Maintenance Processing. 
	 */
	protected boolean complete(Connection conn, AsScheduleParameter param) throws ReadWriteException, ScheduleException
	{
		try
		{
			CarryInformationHandler cih = new CarryInformationHandler(conn);
			CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
			ciKey.setCarryKey(param.getCarryKey());
			if(cih.count(ciKey) == 0)
			{
				//#CM45872
				// 6003018=There was no object data. .
				setMessage("6003018");
				return false;
			}
			CarryInformation ci = (CarryInformation) cih.findPrimary(ciKey);

			//#CM45873
			// Make Station processing Class, and do End Processing. 
			Station station = StationFactory.makeStation(conn, param.getStationNo());
			StationOperator stOperate = StationOperatorFactory.makeOperator(conn, station.getStationNumber(), station.getClassName());
			stOperate.operationDisplayUpdate(ci);
			
			//#CM45874
			// 6001014= Ended.
			setMessage("6001014");
			return true;

		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}
	
	//#CM45875
	/**
	 * Do Work display disbursement End Processing. 
	 * Do End Processing based on the item which becomes the key to parameter Array. 
	 * Return true when succeeding in the Maintenance processing and return false when failing. 
	 * The reason can be acquired in <code>getMessage</code> when failing in processing. 
	 * @param conn Connection object with database
	 * @return Return true when succeeding in processing and return false when failing. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated occurs in Maintenance Processing. 
	 */
	protected boolean completeUnitRetrieval(Connection conn, AsScheduleParameter param) throws ReadWriteException, ScheduleException
	{
		try
		{
			CarryInformationHandler cih = new CarryInformationHandler(conn);
			CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
			ciKey.setCarryKey(param.getCarryKey());
			if(cih.count(ciKey) == 0)
			{
				//#CM45876
				// 6003018=There was no object data. .
				setMessage("6003018");
				return false;
			}
			CarryInformation ci = (CarryInformation) cih.findPrimary(ciKey);


			//#CM45877
			// Generate Station instance. 
			Station station = StationFactory.makeStation(conn, param.getStationNo());
			int stationType = station.getStationType();

			if(stationType == Station.STATION_TYPE_IN || stationType == Station.STATION_TYPE_OUT)
			{
				CarryCompleteOperator carryComp = new CarryCompleteOperator();
				carryComp.setClassName(CLASS_NAME);
				if (station.isReStoringOperation())
				{
					//#CM45878
					//<en> Unit Picking stock update (Restorage Plan data making)</en>
					carryComp.unitRetrieval(conn, ci, true);
				}
				else
				{
					//#CM45879
					//<en> Unit Picking stock update (There is not Restorage Plan data making).</en>
					carryComp.unitRetrieval(conn, ci, false);
				}
			}
			else if(stationType == Station.STATION_TYPE_INOUT)
			{
				ci.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_UNIT);
				//#CM45880
				// Make Station processing Class, and do End Processing. 
				StationOperator stOperate = StationOperatorFactory.makeOperator(conn, station.getStationNumber(), station.getClassName());
				stOperate.operationDisplayUpdate(ci);
			
			}

			//#CM45881
			// 6001020=Disbursement Ended.
			setMessage("6001020");
			return true;

		}
		catch(SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch(NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch(NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

}

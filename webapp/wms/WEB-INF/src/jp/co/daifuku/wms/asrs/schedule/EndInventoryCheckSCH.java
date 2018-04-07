// $Id: EndInventoryCheckSCH.java,v 1.2 2006/10/30 00:43:01 suresh Exp $

//#CM46165
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.control.AllocationClearance;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.asrs.location.AisleOperator;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.ASInventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.ASInventoryCheck;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Station;

//#CM46166
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to do WEB Stock confirmation End setting Processing.  <BR>
 * Receive the content input from the screen as parameter, and do Stock confirmation End setting Processing.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>query()</CODE>Method)<BR><BR><DIR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR></DIR>
 * <BR>
 *   -Retrieve Stock confirmation information (<CODE>inventorycheck</CODE>), and acquire the display item. <BR>
 *     Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *     It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *     <Search condition> <BR><DIR>
 *       Retrieve Stock confirmation information (<CODE>inventorycheck</CODE>), and acquire the display item.  <BR></DIR>
 * <BR>
 *     <return data> <BR><DIR>
 *       Schedule No <BR>
 *       Warehouse Station No <BR>
 *       Station No. <BR>
 *       Beginning shelfNo <BR>
 *       End shelfNo <BR>
 *       Consignor Code <BR>
 *       Consignor Name <BR>
 *       Start Item Code <BR>
 *       End Item Code <BR></DIR>
 * <BR>
 * 2.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do Stock confirmationEnd setting Processing.  <BR>
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Worker code* <BR>
 *     Password* <BR>
 *     Schedule No* <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     true or false <BR></DIR>
 * <BR>
 *   <Stock confirmationEnd Processing> <BR>
 * <DIR>
 *   <Processing condition check> <BR>
 *     -The setting is enabled.  <BR>
 * <BR>
 *   <Confirm Termination> <BR><DIR>
 *     1.Execute drop with Schedule no Method Processing of Allocation Clearance Class.  <BR>
 *       <Parameter> <BR>
 *         -Schedule No of Stock confirmation information file <BR>
 *     2.Update Stock confirmation information (<CODE>asinventorycheck</CODE>).  <BR><DIR>
 *       <Update condition> <BR>
 *         -Corresponding Stock confirmation information data to Schedule No of parameter <BR>
 *       <Content of update> <BR>
 *         -Renew Processing flag to the Processing settlement.  <BR></DIR>
 *     3.The INVENTORY CHECK FLAG item of Aisle information is assumed to be Stock confirmation unwork: <BR></DIR></DIR></DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/09</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:43:01 $
 * @author  $Author: suresh $
 */

public class EndInventoryCheckSCH extends AbstractAsrsControlSCH 
{
	//#CM46167
	// Class fields --------------------------------------------------

	//#CM46168
	// Class variables -----------------------------------------------

	//#CM46169
	// Class method --------------------------------------------------
	//#CM46170
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:43:01 $");
	}

	//#CM46171
	// Constructors --------------------------------------------------
	//#CM46172
	/**
	 * Initialize this Class. 
	 */
	public EndInventoryCheckSCH()
	{
		wMessage = null;
	}

	//#CM46173
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

	//#CM46174
	/**
	 * The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output.  <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation.  <BR>
	 * @param conn Instance to maintain connection with data base. 
	 * @param searchParam Instance of < CODE>AsSystemScheduleParameter</CODE>Class with display data acquisition condition. 
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

		//#CM46175
		// Check on Worker code and Password
		if (!checkWorker(conn, param))
		{
			return null;
		}

		//#CM46176
		// AS/RSStock confirmation retrieval Processing
		ASInventoryCheckHandler invchkHandler = new ASInventoryCheckHandler(conn);
		ASInventoryCheckSearchKey invchksearchKey = new ASInventoryCheckSearchKey();

		//#CM46177
		// Data retrieval
		//#CM46178
		// Status (Processing)
		invchksearchKey.setStatus(Aisle.INVENTORYCHECK);

		//#CM46179
		// The order of display
		invchksearchKey.setScheduleNumberOrder(1,true);

		ASInventoryCheck[] resultEntity = (ASInventoryCheck[]) invchkHandler.find(invchksearchKey);

		Vector vec = new Vector();

		for (int i = 0; i < resultEntity.length; i++)
		{
			AsScheduleParameter dispData = new AsScheduleParameter();
			
			//#CM46180
			// Schedule No
			dispData.setBatchNo(resultEntity[i].getScheduleNumber());
			//#CM46181
			// Warehouse Station No.(Warehouse)
			dispData.setWareHouseNo(resultEntity[i].getWHStationNumber());
			//#CM46182
			// Station No.(Workshop)
			dispData.setStationNo(resultEntity[i].getStationNumber());
			//#CM46183
			// Beginning shelf
			dispData.setFromLocationNo(resultEntity[i].getFromLocation());
			//#CM46184
			// End shelf
			dispData.setToLocationNo(resultEntity[i].getToLocation());
			//#CM46185
			// Consignor Code
			dispData.setConsignorCode(resultEntity[i].getConsignorCode());
			//#CM46186
			// Consignor Name	
			dispData.setConsignorName(resultEntity[i].getConsignorName());
			//#CM46187
			// Start Item Code
			dispData.setStartItemCode(resultEntity[i].getFromItemCode());
			//#CM46188
			// End Item Code
			dispData.setEndItemCode(resultEntity[i].getToItemCode());
			
			vec.addElement(dispData);
		}

		AsScheduleParameter[] paramArray = new AsScheduleParameter[vec.size()];
		vec.copyInto(paramArray);

		if (resultEntity.length == 0)
		{
			//#CM46189
			// 6003011=There was no object data. 
			wMessage = "6003011";
		}
		else
		{
			//#CM46190
			// 6001013 = Displayed..
			wMessage = "6001013";
		}
		return paramArray;
	}

	//#CM46191
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
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		try
		{
			ASFindUtil findutil = new ASFindUtil(conn);

			AllocationClearance alloccle = new AllocationClearance(conn);
	
			//#CM46192
			// Check on Worker code and Password
			AsScheduleParameter workparam = (AsScheduleParameter) startParams[0];
			if (!checkWorker(conn, workparam))
			{
				return null;
			}
	
			//#CM46193
			// Online check
			if (!findutil.isSystemOnLine())
			{
				//#CM46194
				// Make System Status online. 
				wMessage = "6013023";
				return null;
			}
	
			//#CM46195
			// Next day update Processing check
			if (isDailyUpdate(conn))
			{
				return null;
			}
	
			//#CM46196
			// Check Status of Station
			if (!checkStation(conn, startParams))
			{
				return null;
			}
			
			//#CM46197
			// Processed the parameter extraction qty. .
			for (int i = 0; i < startParams.length; i++)
			{
				AsScheduleParameter param = (AsScheduleParameter) startParams[i];
	
				//#CM46198
				// Schedule No
				String schno = param.getBatchNo();

				//#CM46199
				// Stock confirmation information (ASINVENTORYCHECK) retrieval
				ASInventoryCheckHandler invchkHandler = new ASInventoryCheckHandler(conn);
				ASInventoryCheckSearchKey invchkSearchKey = new ASInventoryCheckSearchKey();
				
				invchkSearchKey.setScheduleNumber(schno);
				ASInventoryCheck invchk = (ASInventoryCheck) invchkHandler.findPrimary(invchkSearchKey);
	
				if (invchk == null)
				{
					return null ;
				}

				//#CM46200
				// Drawing release Processing 
				try
				{
					alloccle.dropwithScheduleno(schno);
				}
				catch (NotFoundException ex)
				{
					//#CM46201
					// Even if the Drawing release object does not exist, it is not assumed that it makes an error. 
				}

				//#CM46202
				// Stock confirmation setting (ASINVENTORYCHECK) Update process
				if (!updateAsInventoryCheck(conn, schno))
				{
					return null ;
				}

				//#CM46203
				// Aisle (AISLE) information Update process
				if (!updateAisle(conn, invchk.getStationNumber()))
				{
					return null;
				}
			}
			
			//#CM46204
			// Schedule success
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) this.query(conn, workparam);
			//#CM46205
			// 6001006 = Submitted.
			wMessage = "6001006";
			return viewParam;
		}

		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM46206
	// Private methods -----------------------------------------------
	//#CM46207
	/**
	 * Renew the Aisle information table. <BR>
	 * Turn off the Confirming Stock flag of Aisle when the Stock confirmation transportation data of Aisle is confirmed, <BR>
	 * and there is no transportation information on Stock confirmation in Aisle. <BR>
	 * @param conn Connection object with database
	 * @param stno Station No.
	 * @return Return True when the update normally Ends, False when failing. .
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.<BR>
	 * @throws ScheduleException It is notified when this Method is called. 
	 */
	private boolean updateAisle(Connection conn, String stno) throws ReadWriteException, ScheduleException
	{
		try
		{
			CarryInformationHandler cryHandle = new CarryInformationHandler(conn);
			CarryInformationSearchKey cryKey = new CarryInformationSearchKey();

			//#CM46208
			// Retrieve Aisle of input Station (Station or Workshop) with the route. 
			AisleOperator aop = new AisleOperator();
			String[] stn_aisles = aop.getAisleStationNumber(conn, stno);

			for( int i = 0 ; i < stn_aisles.length ; i++ )
			{
				cryKey.KeyClear();
				cryKey.setAisleStationNumber(stn_aisles[i]);
				cryKey.setCarryKind(CarryInformation.CARRYKIND_RETRIEVAL);
				cryKey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "<");
				cryKey.setRetrievalDetail(CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK, "=", "(", "", "OR");
				cryKey.setPriority(CarryInformation.PRIORITY_CHECK_EMPTY, "=", "", ")", "AND");

				if (cryHandle.count(cryKey) <= 0)
				{
					//#CM46209
					// Turn off the Confirming Stock flag in the Aisle table. 
					AisleHandler aileHandler = new AisleHandler(conn);
					AisleAlterKey aileAlterKey = new AisleAlterKey();

					aileAlterKey.setStationNumber(stn_aisles[i]);
					//#CM46210
					// Renew the Confirming Stock flag. (Stock confirmation unwork)
					aileAlterKey.updateInventoryCheckFlag(Aisle.NOT_INVENTORYCHECK);
					//#CM46211
					// Update of data
					aileHandler.modify(aileAlterKey);
				}
			}

			return true;
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM46212
	/**
	 * Renew the Stock confirmation setting information table. <BR>
	 * @param conn Connection object with database
	 * @param schno  Schedule No.
	 * @return Return True when the update normally Ends. , False when failing. .
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.<BR>
	 * @throws ScheduleException It is notified when this Method is called. 
	 */
	private boolean updateAsInventoryCheck(Connection conn, String schno)	throws ReadWriteException, ScheduleException
	{
		try
		{
			ASInventoryCheckHandler inchkHandler = new ASInventoryCheckHandler(conn);
			ASInventoryCheckAlterKey inchkAlterKey = new ASInventoryCheckAlterKey();
						
			//#CM46213
			// Schedule No
			inchkAlterKey.setScheduleNumber(schno);
			//#CM46214
			// Renew the Confirming Stock flag. (Processing settlement)
			inchkAlterKey.updateStatus(Aisle.NOT_INVENTORYCHECK);
			//#CM46215
			// Update of data
			inchkHandler.modify(inchkAlterKey);

			return true;

		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM46216
	/**
	 * Check it while interrupting Station. 
	 * When Station is not interrupting, Processing cannot be done. 
	 * 
	 * @param conn Connection object with database
	 * @param param To set it, it is informational. 
	 * @return true:It is interrupting.  false:It is not interrupting.
	 * @throws ScheduleException  It is notified when there is no object data. 
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	private boolean checkStation(Connection conn, Parameter[] param) throws ReadWriteException,  ScheduleException
	{
		CarryInformationHandler cih = new CarryInformationHandler(conn);
		CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
		StationHandler sth = new StationHandler(conn);
		StationSearchKey stKey = new StationSearchKey();
		
		for (int i = 0; i < param.length; i++)
		{
			AsScheduleParameter schParam = (AsScheduleParameter) param[i];
			
			ciKey.KeyClear();
			ciKey.setScheduleNumber(schParam.getBatchNo());
			ciKey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL, "<");
			ciKey.setDestStationNumberCollect();
			ciKey.setDestStationNumberGroup(1);
			
			CarryInformation[] ci = (CarryInformation[]) cih.find(ciKey);
			if (ci == null || ci.length == 0)
			{
				continue;
			}
			
			for (int j = 0; j < ci.length; j++)
			{
				stKey.KeyClear();
				stKey.setStationNumber(ci[j].getDestStationNumber());
				stKey.setSuspend(Station.SUSPENDING, "!=");
				if (sth.count(stKey) > 0)
				{
					//#CM46217
					// 6023508=No.{0} Configuration failed due to station is not suspension.
					wMessage = "6023508" + wDelim + schParam.getRowNo();
					return false;
				}
			}
		}
		return true;
	}
}
//#CM46218
//end of class

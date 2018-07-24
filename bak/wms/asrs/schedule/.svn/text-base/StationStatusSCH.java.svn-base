// $Id: StationStatusSCH.java,v 1.2 2006/10/30 00:42:01 suresh Exp $

//#CM46538
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Station;


//#CM46539
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to set WEB Station Status. <BR>
 * Receive the content input from the screen as parameter, and do Station Status setting Processing.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and <BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Processing when Display button is pressed (<CODE>query()</CODE>Method)<BR><BR><DIR>
 *   Retrieve Station table (<CODE>station</CODE>), and display Status etc. of present Station. <BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 *   Displayed in order of Station No.  <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Controller (AGC) No of the Station management table is data other than 0.  <BR></DIR>
 * <BR>
 *   <return data><BR><DIR>
 *     Station No. <BR>
 *     StationName <BR>
 *     Operation mode <BR>
 *     Work mode <BR>
 *     Machine Status  <BR>
 *     Work status <BR></DIR>
 * <BR>
 *   Retrieve transportation table (<CODE>carryinfo</CODE>), <BR>
 *   Acquire the number of cases of present transportation work. <BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 * <BR>
 *   <Search condition> <BR><DIR>
 *     Data that Station No of Station table is corresponding to Transportation origin Station No of transportation information or,  <BR>
 *     Data to which transportation destination Station No of Status of transportation and Station No of Station table are corresponding <BR></DIR>
 * <BR>
 *   <return data><BR><DIR>
 *     Work qty <BR></DIR></DIR>
 * <BR>
 * 2.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method)<BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do StationStatus setting Processing.  <BR>	
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Station No* <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     Suspension Flag <BR></DIR></DIR>
 * <BR>
 *   <Station Status Settting Processing> <BR>
 * <DIR>
 *   <Resume Processing> <BR><DIR>
 *       1.Renew the flag to the [Resume] while suspending correspondence Station with Station No of parameter.  <BR></DIR>
 *   <Suspend Processing> <BR><DIR>
 *       1.Renew the flag to the [Suspend] while suspending correspondence Station with Station No of parameter.  <BR></DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/01</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:42:01 $
 * @author  $Author: suresh $
 */
public class StationStatusSCH extends AbstractAsrsControlSCH
{
	//#CM46540
	// Class fields --------------------------------------------------

	//#CM46541
	// Class variables -----------------------------------------------

	//#CM46542
	// Class method --------------------------------------------------
	//#CM46543
	/**
	 * Class Name(StationStatus Setting of [Resume - Suspend])
	 */
	public static String PROCESSNAME = "StationStatusSCH";

	//#CM46544
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:42:01 $");
	}

	//#CM46545
	// Constructors --------------------------------------------------
	//#CM46546
	/**
	 * Initialize this Class. 
	 */
	public StationStatusSCH()
	{
		wMessage = null;
	}

	//#CM46547
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

	//#CM46548
	/**
	 * The content input from the screen is received as parameter, and filtered, and acquire from the database and return data for the area output.  <BR>
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
		AsScheduleParameter param = (AsScheduleParameter)searchParam;

		StationHandler wStHandler = new StationHandler(conn);
		StationSearchKey wStSearchKey = new StationSearchKey();
		CarryInformationHandler wCiHandler = new CarryInformationHandler(conn);
		CarryInformationSearchKey wCiSearchKey = new CarryInformationSearchKey();

		//#CM46549
		// Reset Station information Search condition. 
		wStSearchKey.KeyClear();
		
		//#CM46550
		// Only Station where controller (AGC) No is set
		wStSearchKey.setControllerNumber(0, "!=");
		//#CM46551
		// Displayed in order of Station No. 
		wStSearchKey.setStationNumberOrder(1, true);

		Station[] rStation = (Station[])wStHandler.find(wStSearchKey);
		
		if (rStation == null || rStation.length == 0)
		{
			//#CM46552
			// 6003018 = There was no object data. .
			wMessage = "6003018";
			return null;				
		}

		Vector vec = new Vector();

		for (int lc = 0; lc < rStation.length; lc++)
		{
			AsScheduleParameter dispData = new AsScheduleParameter();

			//#CM46553
			// Acquire Work qty in correspondence Station from the transportation table. 
			wCiSearchKey.KeyClear();
			wCiSearchKey.setSourceStationNumber(rStation[lc].getStationNumber(), "=", "(", "", "OR");
			wCiSearchKey.setDestStationNumber(rStation[lc].getStationNumber(), "=", "", ")", "OR");
			
			//#CM46554
			// Consolidating condition
			//#CM46555
			// Transportation Key
			wCiSearchKey.setCarryKeyCollect("DISTINCT");
			
			int rCount = wCiHandler.count(wCiSearchKey);

			//#CM46556
			// Return it by the Processing screen with selection check box Status. 
			if (Integer.parseInt(param.getProcessStatus()) == Station.NOT_SUSPEND)
			{			
				//#CM46557
				// At [Resume] screen
				if (rStation[lc].getSuspend() == Station.NOT_SUSPEND)
				{
					dispData.setSelectMode("0");
				}
				else
				{
					dispData.setSelectMode("1");
				}
			}
			else
			{
				//#CM46558
				// All the selection check boxes are assumed turning off at the Suspension screen. 
				dispData.setSelectMode("0");
			}
			//#CM46559
			// Station No
			dispData.setStationNo(rStation[lc].getStationNumber());
			//#CM46560
			// StationName
			dispData.setDispStationName(rStation[lc].getStationName());
			//#CM46561
			// Operation modeName
			dispData.setDispModeTypeName(DisplayText.getText("STATION","MODETYPE", Integer.toString(rStation[lc].getModeType())));
			//#CM46562
			// Work mode
			int wCurrentMode = 0;
			switch(rStation[lc].getCurrentMode())
			{
				//#CM46563
				//<en> [0:neutral mode] and [1:storage mode] are the storage mode.</en>
				case Station.NEUTRAL:
				    break;
				case Station.STORAGE_MODE:
					wCurrentMode = Station.STORAGE_MODE;
					break ;
				case Station.RETRIEVAL_MODE:
					wCurrentMode = Station.RETRIEVAL_MODE;
					break ;
				//#CM46564
				//<en> All others are undefined station status.</en>
				default:
					break;
			}
			//#CM46565
			// Work modeName
			dispData.setDispCurrentModeName(DisplayText.getText("STATION","CURRENTMODE", Integer.toString(wCurrentMode)));
			//#CM46566
			// Machine Status Name
			dispData.setDispControllerStatusName(DisplayText.getText("STATION","STATUS", Integer.toString(rStation[lc].getStatus())));
			//#CM46567
			// Work statusName
			dispData.setDispWorkingStatusName(DisplayText.getText("STATION","SUSPEND", Integer.toString(rStation[lc].getSuspend())));
			//#CM46568
			// Work qty
			dispData.setWorkingCount(rCount);

			vec.addElement(dispData);
		}

		AsScheduleParameter[] paramArray = new AsScheduleParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM46569
		// 6001013 = Displayed..
		wMessage = "6001013";
		return paramArray;

	}
	
	//#CM46570
	/**
	 * Check whether the content of parameter is correct. 
	 * According to the content set in parameter specified by <CODE>checkParam</CODE>, <BR>
	 * do content Check processing of the parameter input.  <BR>
	 * Mount by each succession Class if necessary. 
	 * @param conn Connection object with database
	 * @param checkParam ParameterClass where content which does input check is included
	 * @return True when processing is normal, False when schedule processing fails or it is not possible to schedule it.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 */
	public boolean check(Connection conn, Parameter checkParam) throws ReadWriteException, ScheduleException
	{
		AsScheduleParameter param = (AsScheduleParameter)checkParam;
		//#CM46571
		// Do Worker code Input check. 
		if (isPatternMatching(param.getWorkerCode()))
		{
			//#CM46572
			// 6003101 = Makes {0} and the character which cannot be used is included. 
			setMessage("6003101"+ wDelim + DisplayText.getText("LBL-W0274"));
			return false;
		}
		
		//#CM46573
		// Do Input check of Password.
		if (isPatternMatching(param.getPassword()))
		{
			//#CM46574
			// 6003101 = Makes {0} and the character which cannot be used is included. 
			setMessage("6003101"+ wDelim + DisplayText.getText("LBL-W0129"));
			return false;
		}
		
		//#CM46575
		// Check whether the content of Worker code and Password are correct.
		if( !correctWorker(conn, param.getWorkerCode(), param.getPassword()) )
		{
			return false;
		}
		
		return true;
	}
	
	//#CM46576
	/**
	* It is verified whether Prohibited character defined by the system in the specified character string is included. 
	* Specify the definition of Prohibited character with Common Param.
	* @param pattern Specify the character string which becomes an object. 
	* @return Return true when the prohibition character is included in the character string, false when the prohibition character is not included.
	*/
	private boolean isPatternMatching(String pattern)
	{
		return (isPatternMatching(pattern, WmsParam.NG_PARAMETER_TEXT));
	}
	
	//#CM46577
	/**
	 * It is verified whether Prohibited character defined by the system in the specified character string is included. 
	 * Specify the definition of Prohibited character with Common Param.
	 * @param pattern Specify the character string which becomes an object. 
	 * @param ngshars Prohibited character
	 * @return Return true when the prohibition character is included in the character string, false when the prohibition character is not included.
	 */
	private boolean isPatternMatching(String pattern, String ngshars)
	{
		if (pattern != null && !pattern.equals(""))
		{
			for (int i = 0; i < ngshars.length(); i++)
			{
				if (pattern.indexOf(ngshars.charAt(i)) > -1)
				{
					return true;
				}
			}
		}
		return false;
	}

	//#CM46578
	/**
	 * The content input from the screen is received as parameter, and Starts the Station Status setting schedule.<BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
	 * Return null when the schedule does not End because of the condition error etc.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Set the content <CODE>AsSystemScheduleParameter</CODE>Class  Array of Instances.
	 * @return <CODE>AsSystemScheduleParameter</CODE>Class Array of Instances.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		StationHandler wStHandler = new StationHandler(conn);
		StationAlterKey wStAlterKey = new StationAlterKey();

		//#CM46579
		// Check on Worker code and Password
		if (!checkWorker(conn, (AsScheduleParameter) startParams[0]))
		{
			 return null;
		}
		
		try
		{		
			for (int lc=0; lc<startParams.length; lc++)
			{
				AsScheduleParameter rparam = (AsScheduleParameter) startParams[lc];

				//#CM46580
				// Condition check
				if (!checkList(conn, rparam))		return null;

				//#CM46581
				// Return it by the Processing screen with selection check box Status. 
				if (Integer.parseInt(rparam.getProcessStatus()) == Station.NOT_SUSPEND)
				{			
					//#CM46582
					//<en> Updated while driving the flag when Station is being interrupted. </en>
					wStAlterKey.KeyClear();
					wStAlterKey.setStationNumber(rparam.getStationNo());
					wStAlterKey.updateSuspend(Station.NOT_SUSPEND);
				
					wStHandler.modify(wStAlterKey);
				}
				else
				{
					//#CM46583
					//<en> Updated while interrupting the flag when Station is being interrupted. </en>
					wStAlterKey.KeyClear();
					wStAlterKey.setStationNumber(rparam.getStationNo());
					wStAlterKey.updateSuspend(Station.SUSPENDING);
				
					wStHandler.modify(wStAlterKey);
				}
			}

			//#CM46584
			// Schedule success
			AsScheduleParameter[] viewParam = (AsScheduleParameter[]) this.query(conn, startParams[0]);

			//#CM46585
			//<en>Status of Station was switched. </en>
			setMessage("6011003");

			return viewParam;

		}
		catch(ReadWriteException rwe)
		{
			setMessage(rwe.getMessage());
			return null;
		}
		catch(NotFoundException nfe)
		{
			setMessage(nfe.getMessage());
			return null;
		}
		catch(InvalidDefineException ide)
		{
			setMessage(ide.getMessage());
			return null;
		}
	}

	//#CM46586
	/**<en>
	 * parameterCheck Do processing. 
	 * @param conn Connection object with database
	 * @param checkParam Instance of <CODE>AsScheduleParameter</CODE> Class to be checked
	 * @return Return true when there is no Abnormal in parameter, false when there is Abnormal.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated while checking parameter occurs. 
	 </en>*/
	public boolean checkList(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException
	{
		StationHandler wStHandler = new StationHandler(conn);
		StationSearchKey wStSearchKey = new StationSearchKey();
		GroupControllerHandler wGcHandler = new GroupControllerHandler(conn);
		GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();

		try
		{
			AsScheduleParameter param = (AsScheduleParameter)checkParam;

			//#CM46587
			// Set Station information Search condition. 
			wStSearchKey.KeyClear();
			wStSearchKey.setStationNumber(param.getStationNo());

			Station[] rStation = (Station[])wStHandler.findForUpdateNowait(wStSearchKey);

			//#CM46588
			//<en> System Status check</en>
			wGcSearchKey.KeyClear();
			wGcSearchKey.setControllerNumber(rStation[0].getControllerNumber());
			
			GroupController[] rGroupControll = (GroupController[])wGcHandler.find(wGcSearchKey);
			
			if (rGroupControll[0].getStatus() != GroupController.STATUS_ONLINE)
			{
				//#CM46589
				//<en> Please set the system status on-line.</en>
				setMessage("6013023");
				return false;
			}
			
			return true;
		}
		catch (LockTimeOutException ex)
		{
			//#CM46590
			// 6027008=This data is not useable because it is updating it with other terminals. 
			setMessage("6027008");
			return false;
		}
	}
}
//#CM46591
//end of class

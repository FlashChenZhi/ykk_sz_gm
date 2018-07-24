// $Id: AsWorkOperationSCH.java,v 1.2 2006/10/30 00:43:36 suresh Exp $

//#CM46088
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule ;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id03;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.dbhandler.ASCarryInformationHandler;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerAlterKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.entity.GroupController;

//#CM46089
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * Class to do WEB ASRS work Start, work End, and Force Stop.  <BR>
 * Receive the content input from the screen as parameter, and do work Start, work End, and Force Stop.  <BR>
 * Do not do Comment-rollback of the transaction though each Method of this Class must use the Connection object and<BR>
 * do the update processing of the receipt data base.   <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method) <BR><BR><DIR>
 *   Retrieve table (<CODE>groupcontroller</CODE>) of the management of the controller of the group, and display Status of a present system.<BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 *   Display it in order of AGC  No. <BR>
 * <BR>
 *   <return data><BR><DIR>
 *     Controller number <BR>
 *     Status  <BR>
 *     Number of remainder work <BR></DIR></DIR>
 * <BR>
 * 2.Processing when Display button is pressed (<CODE>query()</CODE>Method)<BR><BR><DIR>
 *   Retrieve table (<CODE>groupcontroller</CODE>) of the management of the controller of the group, and display Status of a present system.<BR>
 *   Return empty array of <CODE>Parameter</CODE> when pertinent data is not found. Moreover, return null when the condition error etc. occur. <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. <BR>
 *   Display it in order of AGC  No. <BR>
 * <BR>
 *   <return data><BR><DIR>
 *     Controller number <BR>
 *     Status  <BR>
 *     Number of remainder work <BR></DIR></DIR>
 * <BR>
 * 3.Processing when Submit button is pressed.(<CODE>startSCHgetParams()</CODE>Method) <BR><BR><DIR>
 *   Receive the content displayed in the filtering area as parameter, and do work Start, work End, and Force Stop.  <BR>
 *   Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
 *   Return null when the schedule does not End because of the condition error etc.<BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     AGCNo* <BR>
 *     Confirmation flag(Work Start, Work End, Force Stop) <BR></DIR>
 * <BR>
 *   <return data> <BR><DIR>
 *     System Status <BR>
 *     Number of remainder work <BR></DIR>
 * <BR>
 *   <Execution Processing> <BR>
 *     -Distribute Processing of Work Start, Work End, and each Force Stop.  <BR>
 * <DIR>
 *   -Work Start <DIR><BR>
 *     <Processing condition check> <BR>
 *       1.Work Start cannot be done while clearing Drawing Next day update.  <BR>
 *       2.If the flag is up to (AwcSystem.BATCH) in the batch in the acquired batch, Work Start cannot be done.  <BR>
 *       3.When external data takes, and is making the report data, Work Start cannot be done.  <BR>
 * <BR>
 *     <StartProcessing> <BR>
 *         1.Parameter usually transmits Work Start request cable (ID-1) in Start.  <BR></DIR>
 * <BR>
 *   -Work End <DIR><BR>
 *     <Processing condition check> <BR>
 *       1.Thing that there is no remainder work in selection AGCNo when usual is selected by mode selection <BR>
 * <BR>
 *     <Termination> <BR>
 *         -For the mode (usually) <BR><DIR>
 *         1.Check on presence of the remainder work <BR>
 *         2.Set Status of correspondence AGCNo of table (<CODE>groupcontroller</CODE>) of the management of the controller of the group and set an off-line reservation.  <BR>
 *         3.Parameter usually transmits Work End demand cable (ID-3) in End.  <BR></DIR>
 * <BR>
 *         -For the mode (data storage) <BR><DIR>
 *         1.Set Status of correspondence AGCNo of table (<CODE>groupcontroller</CODE>) of the management of the controller of the group and set an off-line reservation.  <BR>
 *         2.Parameter usually transmits Work End request cable (ID-3) in End(Data maintenance).  <BR></DIR></DIR>
 * <BR>
 *   -Force Stop <DIR><BR>
 *     <Termination> <BR>
 *         -For the mode (single awc) <BR><DIR>
 *         1.Work Termination <BR>
 *           Make Status of correspondence AGCNo of table (<CODE>groupcontroller</CODE>) of the management of the controller of the group off-line.  <BR></DIR></DIR>
 * </DIR>
 * <BR></DIR>
 * </DIR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/09/30</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:43:36 $
 * @author  $Author: suresh $
 */

public class AsWorkOperationSCH extends AbstractAsrsControlSCH
{
    //#CM46090
    // Class fields --------------------------------------------------

    //#CM46091
    // Class variables -----------------------------------------------

    //#CM46092
    // Class method --------------------------------------------------

	//#CM46093
	/**
	 * Class Name(ASWork Start-End)
	 */
	public static String PROCESSNAME = "AsWorkOperationSCH";

    //#CM46094
    /**
     * Return the version of this class. 
     * @return Version and date
     */
    public static String getVersion()
    {
        return ("$Revision: 1.2 $,$Date: 2006/10/30 00:43:36 $");
    }

    //#CM46095
    // Constructors --------------------------------------------------
    //#CM46096
    /**
     * Initialize this Class. 
     * @param conn Connection object with database
     * @param kind Process Flag
     */
	//#CM46097
	/**
	 * Initialize this Class. 
	 */
	public AsWorkOperationSCH()
	{
		wMessage = null;
	}

	//#CM46098
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

	//#CM46099
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

		AsScheduleParameter param = (AsScheduleParameter)searchParam;

		GroupControllerHandler wGCHandler = new GroupControllerHandler(conn);
		GroupControllerSearchKey searchKey = new GroupControllerSearchKey();
		ASCarryInformationHandler wASCarryHandler = new ASCarryInformationHandler(conn);

		//#CM46100
		// Displayed in order of AGC No.
		searchKey.setControllerNumberOrder(1, true);

		GroupController[] resultEntity = (GroupController[]) wGCHandler.find(searchKey);

		Vector vec = new Vector();

		for (int lc = 0; lc < resultEntity.length; lc++)
		{
			AsScheduleParameter dispData = new AsScheduleParameter();
			//#CM46101
			// Selection check box
			if (param.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_START))
			{
				if (resultEntity[lc].getStatus() == GroupController.STATUS_OFFLINE)
				{
					dispData.setSelectCurrentMode("1");
				}
				else
				{
					dispData.setSelectCurrentMode("0");
				}
			}
			else if (param.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_END) ||
					  param.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_END_DATAKEEP))
			{
				if (resultEntity[lc].getStatus() == GroupController.STATUS_ONLINE)
				{
					dispData.setSelectCurrentMode("1");
				}
				else
				{
					dispData.setSelectCurrentMode("0");
				}
			}
			else
			{
				//#CM46102
				// Turning off during Force Stop
				dispData.setSelectCurrentMode("0");
			}
			//#CM46103
			// AGC-No
			dispData.setAgcNo(Integer.toString(resultEntity[lc].getControllerNumber()));
			//#CM46104
			// System Status
			if (resultEntity[lc].getStatus() >= 0)
			{
				dispData.setSystemStatus(DisplayText.getText("GROUPCONTROLLER", "STATUS", 
																Integer.toString(resultEntity[lc].getStatus())));
			}
			else
			{
				dispData.setSystemStatus(DisplayText.getText("GROUPCONTROLLER", "STATUS", "0"));
			}
			//#CM46105
			// Remainder Work qty
			dispData.setWorkingCount(wASCarryHandler.remainderWorksCount(resultEntity[lc].getControllerNumber()));

			vec.addElement(dispData);
		}

		AsScheduleParameter[] paramArray = new AsScheduleParameter[vec.size()];
		vec.copyInto(paramArray);

		//#CM46106
		// 6001013 = Displayed..
		wMessage = "6001013";
		return paramArray;

	}

	//#CM46107
	/**<en>
	 * parameterCheck Do processing. 
	 * Check Work Start<BR>
	 * Check whether it is batch Processing.  <BR>
	 * Work Start cannot be done while clearing Drawing Next day update.  <BR>
	 * Check Work End<BR>
	 * Do the error notification as a pertinent controller at remainder work Available when you specify Data maintenance.  <BR>
	 * @param conn Connection object with database
	 * @param param Content of checked parameter
	 * @return Return true when there is no Abnormal in parameter, false when there is Abnormal.
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the error not anticipated while checking parameter occurs. 
	 </en>*/
	public boolean checkList(Connection conn, Parameter[] param)
		throws ReadWriteException, ScheduleException
	{
		ASCarryInformationHandler wASCarryHandler = new ASCarryInformationHandler(conn);

		for (int lc=0; lc<param.length; lc++)
		{
			AsScheduleParameter rparam = (AsScheduleParameter)param[lc];
			
			if (rparam.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_START))
			{
				//#CM46108
				// Next day Processing is assumed to be an error for Work Start. 
				//#CM46109
				// Next day update Processing check
				if (isDailyUpdate(conn))
				{
					return false;
				}
			}
			else if (rparam.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_END))
			{
				//#CM46110
				// Check Remainder Work qty with Work End for Data maintenance. 
				if(wASCarryHandler.remainderWorksCount(Integer.parseInt(rparam.getAgcNo())) > 0)
				{
					//#CM46111
					//<en> Because AGC-No {0} has the remainder work, Work End cannot be done. </en>
					setMessage("6013031" + wDelim + rparam.getAgcNo());
					return false;
				}
			}
			else
			{
				//#CM46112
				// No check during Force Stop.
			}
		}
		return true;
	}

	//#CM46113
	/**
	 * The content input from the screen is received as parameter, and Starts Work Start, End, and the Force Stop schedule. <BR>
	 * Receive parameter by the array because the input of two or more of set of straightening data is assumed. <BR>
	 * Refer to the paragraph of the Class explanation for detailed operation. <BR>
	 * Filtered and acquired from the data base again and return data for the area output when processing normally Ends. <BR>
	 * Return null when the schedule does not End because of the condition error etc.
	 * @param conn Instance to maintain connection with data base. 
	 * @param startParams Set the content <CODE>AsSystemScheduleParameter</CODE>Class  Array of Instances. <BR>
	 *         AsSystemScheduleParameter <CODE>ScheduleException</CODE> when things except the instance are specified is slow.<BR>
	 *         It is possible to refer by using < CODE>getMessage() </ CODE>Method for the content of the error. 
	 * @return Instance of AsScheduleParameter
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams) throws ReadWriteException, ScheduleException
	{
		//#CM46114
		// Check on Worker code and Password
		if (!checkWorker(conn, (AsScheduleParameter) startParams[0]))
		{
			 return null;
		}

		//#CM46115
		// Filtered and input is checked. 
		if (!checkList(conn, startParams))
		{
			return null;
		}

		for (int lc=0; lc<startParams.length; lc++)
		{
			AsScheduleParameter rparam = (AsScheduleParameter) startParams[lc];
			
			if (rparam.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_START))
			{
				//#CM46116
				// Work Start
				if (!this.start(Integer.parseInt(rparam.getAgcNo())))	return null;
			}
			else if (rparam.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_END))
			{
				//#CM46117
				// Work End usually
				if (!this.normalend(conn, Integer.parseInt(rparam.getAgcNo())))	return null;
			}
			else if (rparam.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_END_DATAKEEP))
			{
				//#CM46118
				// Data maintenanceWork End
				if (!this.endDatakeep(conn, Integer.parseInt(rparam.getAgcNo())))	return null;
			}
			else if (rparam.getSelectMode().equals(AsScheduleParameter.ASRS_WORKING_ONLYEND))
			{
				//#CM46119
				// Single Work End(Force Stop setting)
				if (!this.onlyend(conn, Integer.parseInt(rparam.getAgcNo())))	return null;
			}
		}

		return new AsScheduleParameter[0];
	}

	//#CM46120
	/**<en>
	 * Process the start of work.
	 * @param pAgcNo ControllerNo.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	private boolean start(int pAgcNo) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM46121
			//<en> Work start</en>
			SystemTextTransmission.id01send(pAgcNo);

			//#CM46122
			//<en>Requested to start the work.</en>
			setMessage("6011005");
			return true;
		}
		//#CM46123
		//<en> rmiregistry and msgserver are not started; Cannot send.</en>
		catch (ConnectException ce)
		{
			//#CM46124
			//<en> Cannot send; no connection with the message log server.</en>
			setMessage("6017003");
			return false;
		}
		//#CM46125
		//<en> Though rmiregistry and msgserver are started, no connection is gained with AGC; Cannot send.</en>
		catch (NotBoundException nbe)
		{
			//#CM46126
			//<en> The connection with message log server has been obtained, however no connection has been</en>
			//<en> established between AWC and AGC. Cannot send.</en>
			setMessage("6017004");
			return false;
		}
		catch (ReadWriteException rwe)
		{
			setMessage(rwe.getMessage());
			return false;
		}
		//#CM46127
		//<en> Record Log if cannot send for other reason while rmi has been started up.</en>
		catch (Exception e)
		{
			//#CM46128
			//<en> Unexpected error occurred.{0}</en>
			RmiMsgLogClient.write(
				new TraceHandler(6006001, e),
				(String) this.getClass().getName());
			//#CM46129
			//<en> Fatal error occurred.</en>
			setMessage("6017011");
			return false;
		}
	}

	//#CM46130
	/**<en>
	 * Process the start of work.
	 * @param conn Instance that holds the database and connection.
	 * @param pAgcNo ControllerNo.
	 * @return Return true if the schedule process completes normal. otherwise return false.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	private boolean normalend(Connection conn, int pAgcNo) throws ReadWriteException, ScheduleException
	{
		try
		{
			GroupControllerHandler wGCHandler = new GroupControllerHandler(conn);
			GroupControllerAlterKey wGCAlterKey = new GroupControllerAlterKey();
			
			//#CM46131
			//<en> Work normal end</en>
			//#CM46132
			//<en> Set 'reserved for termination' to the Status of GroupController.</en>
			wGCAlterKey.KeyClear();
			//#CM46133
			// Controller No and agreement
			wGCAlterKey.setControllerNumber(pAgcNo);
			//#CM46134
			// Set the End reservation in Status. 
			wGCAlterKey.updateStatus(GroupController.STATUS_END_RESERVATION);
			
			wGCHandler.modify(wGCAlterKey);
			
			//#CM46135
			//<en> A communication message requesting for the work termination</en>
			SystemTextTransmission.id03send(pAgcNo, As21Id03.GENERAL_END);

			//#CM46136
			//<en> Submitted the work termination request.</en>
			setMessage("6011006");
			return true;
		}
		//#CM46137
		//<en> rmiregistry and msgserver are not started; Cannot send.</en>
		catch (ConnectException ce)
		{
			//#CM46138
			//<en> Cannot send; no connection with the message log server.</en>
			setMessage("6017003");
			return false;
		}
		//#CM46139
		//<en> Though rmiregistry and msgserver are started, no connection is gained with AGC; Cannot send.</en>
		catch (NotBoundException nbe)
		{
			//#CM46140
			//<en> The connection with message log server has been obtained, however no connection has been</en>
			//<en> established between AWC and AGC. Cannot send.</en>
			setMessage("6017004");
			return false;
		}
		catch (ReadWriteException rwe)
		{
			setMessage(rwe.getMessage());
			return false;
		}
		//#CM46141
		//<en> Record Log if cannot send for other reason while rmi has been started up.</en>
		catch (Exception e)
		{
			//#CM46142
			//<en> Unexpected error occurred.{0}</en>
			RmiMsgLogClient.write(
				new TraceHandler(6006001, e),
				(String) this.getClass().getName());
			//#CM46143
			//<en> Fatal error occurred.</en>
			setMessage("6017011");
			return false;
		}
	}

	//#CM46144
	/**<en>
	 * Process the start of work.
	 * @param conn Instance that holds the database and connection.
	 * @param pAgcNo ControllerNo.
	 * @return Return true if the schedule process completes normal. otherwise return false.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	private boolean endDatakeep(Connection conn, int pAgcNo) throws ReadWriteException, ScheduleException
	{
		try
		{
			GroupControllerHandler wGCHandler = new GroupControllerHandler(conn);
			GroupControllerAlterKey wGCAlterKey = new GroupControllerAlterKey();
			
			//#CM46145
			//<en> Work normal end</en>
			//#CM46146
			//<en> Set 'reserved for termination' to the Status of GroupController.</en>
			wGCAlterKey.KeyClear();
			//#CM46147
			// Controller No and agreement
			wGCAlterKey.setControllerNumber(pAgcNo);
			//#CM46148
			// Set the End reservation in Status. 
			wGCAlterKey.updateStatus(GroupController.STATUS_END_RESERVATION);
			
			wGCHandler.modify(wGCAlterKey);
			
			//#CM46149
			//<en> A communication message requesting for the work termination</en>
			SystemTextTransmission.id03send(pAgcNo, As21Id03.EXTRAORDINARY_END_ONE);

			//#CM46150
			//<en> Submitted the work termination request.</en>
			setMessage("6011006");
			return true;
		}
		//#CM46151
		//<en> rmiregistry and msgserver are not started; Cannot send.</en>
		catch (ConnectException ce)
		{
			//#CM46152
			//<en> Cannot send; no connection with the message log server.</en>
			setMessage("6017003");
			return false;
		}
		//#CM46153
		//<en> Though rmiregistry and msgserver are started, no connection is gained with AGC; Cannot send.</en>
		catch (NotBoundException nbe)
		{
			//#CM46154
			//<en> The connection with message log server has been obtained, however no connection has been</en>
			//<en> established between AWC and AGC. Cannot send.</en>
			setMessage("6017004");
			return false;
		}
		catch (ReadWriteException rwe)
		{
			setMessage(rwe.getMessage());
			return false;
		}
		//#CM46155
		//<en> Record Log if cannot send for other reason while rmi has been started up.</en>
		catch (Exception e)
		{
			//#CM46156
			//<en> Unexpected error occurred.{0}</en>
			RmiMsgLogClient.write(
				new TraceHandler(6006001, e),
				(String) this.getClass().getName());
			//#CM46157
			//<en> Fatal error occurred.</en>
			setMessage("6017011");
			return false;
		}
	}

	//#CM46158
	/**<en>
	 * Process the start of work.
	 * @param conn Instance that holds the database and connection.
	 * @param pAgcNo ControllerNo.
	 * @return Return true if the schedule process completes normal. otherwise return false.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	private boolean onlyend(Connection conn, int pAgcNo) throws ReadWriteException, ScheduleException
	{
		try
		{
			GroupControllerHandler wGCHandler = new GroupControllerHandler(conn);
			GroupControllerAlterKey wGCAlterKey = new GroupControllerAlterKey();
			
			//#CM46159
			//<en> Single Work End</en>
			//#CM46160
			//<en> Set off-line in Status of GroupController. </en>
			wGCAlterKey.KeyClear();
			//#CM46161
			// Controller No and agreement
			wGCAlterKey.setControllerNumber(pAgcNo);
			//#CM46162
			// Set the End reservation in Status. 
			wGCAlterKey.updateStatus(GroupController.STATUS_OFFLINE);
			
			wGCHandler.modify(wGCAlterKey);

			//#CM46163
			//<en> Set off-line solely.</en>
			setMessage("6011007");
			return true;
		}
		catch (ReadWriteException rwe)
		{
			setMessage(rwe.getMessage());
			return false;
		}
		catch (NotFoundException rwe)
		{
			setMessage(rwe.getMessage());
			return false;
		}
		catch (InvalidDefineException rwe)
		{
			setMessage(rwe.getMessage());
			return false;
		}
	}
}
//#CM46164
//end of class

// $Id: CreaterFactory.java,v 1.2 2006/10/30 02:52:06 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50425
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;

//#CM50426
/**<en>
 * This class is used to invoke the maintentance class which performs the maintenance.
 * It determines which schedule to use, based on the specified shceduler ID, via diplay applications 
 * and execute the process.
 * It determines the schedule should be executed whether over remopte server or on the same local VM,
 * according to the definition of SCHEDULETYPE table.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>torigaki</TD><TD>The logic which passes a message is added.<BR>
 * A logic has been added here so that a message could be acquired from the creater instance.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:06 $
 * @author  $Author: suresh $
 </en>*/
public class CreaterFactory implements ToolScheduleInterface
{
	//#CM50427
	// Class fields --------------------------------------------------
	
	//#CM50428
	/**<en> Warehouse setting </en>*/

	public final static int WAREHOUSE = 1;
	//#CM50429
	/**<en> Terminal information setting </en>*/

	public final static int TERMINALINFORMATION = 2;
	//#CM50430
	/**<en> User setting </en>*/

	public final static int AWCUSER = 3;
	//#CM50431
	/**<en> Terminal area setting </en>*/

	public final static int TERMINALAREA = 4;
	//#CM50432
	/**<en> Group controller setting </en>*/

	public final static int GROUPCONTROLLER = 5;
	//#CM50433
	/**<en> Soft zone setting </en>*/

	public final static int SOFTZONE = 6;
	//#CM50434
	/**<en> Aisle setting </en>*/

	public final static int AISLE = 7;	
	//#CM50435
	/**<en> Station setting </en>*/

	public final static int STATION = 8;		
	//#CM50436
	/**<en> Terminal menu setting </en>*/

	public final static int TERMINALMENU = 9;		
	//#CM50437
	/**<en> Machine information setting  </en>*/

	public final static int MACHINE = 10;
	//#CM50438
	/**<en> User menu setting </en>*/

	public final static int AWCMENU = 11;
	//#CM50439
	/**<en> Hard zone setting </en>*/

	public final static int HARDZONE = 12;
	//#CM50440
	/**<en> Carry route setting </en>*/

	public final static int ROUTE = 13;
	//#CM50441
	/**<en> Unavailable location setting </en>*/

	public final static int UNAVAILABLELOCATION = 14;
	//#CM50442
	/**<en> Workshop setting </en>*/

	public final static int WORKPLACE = 15;
	//#CM50443
	/**<en> Dummy station setting </en>*/

	public final static int DUMMYSTATION = 16;
	//#CM50444
	/**<en> Hard zone setting (individulally) </en>*/

	public final static int INDIVIDUALLYHARDZONE = 17;
	//#CM50445
	/**<en> Clasificaiton setting </en>*/

	public final static int CLASSIFICATION = 18;
	//#CM50446
	/**<en> Area setting </en>*/

	public final static int AREA = 19;
	//#CM50447
	/**<en> Soft zone setting (individual) </en>*/

	public final static int INDIVIDUALLYSOFTZONE = 20;
	//#CM50448
	/**<en>	Station setting (floor storage/ general) </en>*/

	public final static int FLOORALL = 21 ;
	//#CM50449
	/**<en> Zone association setting </en>*/

	public final static int ZONERELATION = 22;
	//#CM50450
	// Class variables -----------------------------------------------

	//#CM50451
	/**<en>
	 * The Creater instance that this instance preserves.
	 </en>*/
	protected Creater wCreater = null;

	//#CM50452
	/**<en>
	 * Scheduler ID of the scheduler that this SchedulerFactory instance uses.
	 </en>*/
	protected int wId = 0;

	//#CM50453
	/**<en>
	 * Store the messages received from Scheduler class.
	 </en>*/
	 protected String wMessage = "";
	 
	//#CM50454
	/**<en>
	 * Updating flag
	 </en>*/
	 //#CM50455
	 //	protected int wUpdating = NO_UPDATING;
	 
	//#CM50456
	 /**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured
	 </en>*/
	public static String wDelim = MessageResource.DELIM ;

	//#CM50457
	// Class method --------------------------------------------------
	//#CM50458
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:06 $") ;
	}

	//#CM50459
	// Constructors --------------------------------------------------

	//#CM50460
	/**<en>
	 * Initialize this class. 
	 * @param conn :connection object with database
	 * @param id   :scheduler ID to generate
	 * @param kind :process type
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if specified scheduler ID does not exist in SCHEDULETYPE table.
	 </en>*/
	public CreaterFactory(Connection conn, int id, int kind)throws ReadWriteException, ScheduleException
	{
		wMessage = "";

		createInstance(conn, id, kind);
		wCreater.setUpdatingFlag(NO_UPDATING);
	}

	//#CM50461
	// Public methods ------------------------------------------------
	//#CM50462
	/**<en>
	 * Retrieve data to isplay on the screen.<BR>
	 * @param searchParam :search parameter
	 * @return :the array of the schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		Parameter[] ret= wCreater.query(conn, locale, searchParam);
		wMessage = wCreater.getMessage();
		return ret;
	}

	//#CM50463
	/**<en>
	 * Return the number of schedule parameters that the class implemented with this interface preserves.
	 * @return :the number of schedule parameters
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public int getParametersLength() throws ReadWriteException, ScheduleException
	{
		return wCreater.getParameters().length;
	}

	//#CM50464
	/**<en>
	 * Submit request of form issue. This will be executed when issuing the work list.<BR>
	 * @param  :<code>Locale</code> object the local code has been set to
	 * @return :result of print job. Return true if the printing succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean print(Connection conn, Locale locale) throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM50465
	/**<en>
	 * Implement the process to run when the print-issue button was pressed on the display.<BR>
	 * @param <code>Locale</code> object
	 * @param listParam :schedule parameter
	 * @return :result of print job
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean print(Connection conn, Locale locale, Parameter listParam) throws ReadWriteException, ScheduleException
	{
		return true;
	}

	//#CM50466
	/**<en>
	 * Return the schedule parameters that the class implemented with this interface preserves.
	 * If there is any updating data, the array excluded of those updating data will be returned.
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public Parameter[] getParameters() throws ReadWriteException, ScheduleException
	{
		return wCreater.getParameters();

	}

//#CM50467
/* 2003.05.29 tahara change start */

	//#CM50468
	/**<en>
	 * Return the schedule parameters that the class implemented with this interface preserves.
	 * Also return the updating data.
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public Parameter[] getAllParameters() throws ReadWriteException, ScheduleException
	{
		return wCreater.getAllParameters();
	}

	//#CM50469
	/**<en>
	 * Return the line no. of updating data.
	 * @return :returns the position of the modificaiton in entered data summary.
	 </en>*/
	public int changeLineNo()
	{
		return wCreater.getUpdatingFlag();
	}


//#CM50470
/* 2003.05.29 tahara change end */


	//#CM50471
	/**<en>
	 * Return the entered parameters currently targeted to modify.
	 * @return :the array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public Parameter getUpdatingParameter() throws ReadWriteException, ScheduleException
	{
		return wCreater.getUpdatingParameter();
	}

	//#CM50472
	/**<en>
	 * Return whether/not the input data is being modified at moment.
	 * Return true if data is being modified, or false if not.
	 * @return :return true if data is being modified, or false if not.
	 </en>*/
	public boolean isUpdating()
	{
		if (wCreater.getUpdatingFlag() == NO_UPDATING)
		{
			return false;
		}
		return true;
	}

	//#CM50473
	/**<en>
	 * Check the entered data for the specified parameter.
	 * If normally processed, the specified parameter should be preserved.
	 * The check for inpu data will be carried out by ScheduleChcker implementation class that Scheduler
	 * implementation class preserves.
	 * After execution, the message generated by the checker class store the messages in the message area.
	 * @param param :schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean addParameter(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		//#CM50474
		//<en> Conduct the additional process if data is not being modified.</en>
		if (wCreater.getUpdatingFlag() == NO_UPDATING)
		{
			if (wCreater.addParameter(conn, param))
			{
				//#CM50475
				//<en> The data entry was accepted.</en>
				wMessage = "6121006";
				return true;
			}
		}
		else
		{
			//#CM50476
			//<en> Modification process</en>
			if (wCreater.changeParameter(conn, wCreater.getUpdatingFlag(), param))
			{
				//#CM50477
				//<en> Modified data.</en>
				wMessage = "6121002";
				//#CM50478
				//<en> If the parameter was replaced successfully, the index value will be </en>
				//<en> reset to initial value.</en>
				wCreater.setUpdatingFlag(NO_UPDATING);
				return true;
			}
		}
		//#CM50479
		//<en> Retrieve the message for check failure and set.</en>
		wMessage = wCreater.getMessage();
		return false;
	}

	//#CM50480
	/**<en>
	 * Preserve the specified parameter.
	 * @param param :schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException
	{
		if (wCreater.addInitialParameter(param))
		{
			return true;
		}
		//#CM50481
		//<en> Retrieve the message for check failure and set.</en>
		wMessage = wCreater.getMessage();
		return false;
	}

	//#CM50482
	/**<en>
	 * Preserve the position (index) of data modification.
	 * Parameters will be switched when addParameter is called.
	 * @param index :index of the parameter to modify
	 * @param param :schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean changeParameter(int index) throws ReadWriteException, ScheduleException
	{
		wMessage = "";
		wCreater.setUpdatingFlag(index);
		return true;
	}

	//#CM50483
	/**<en>
	 * Delete the parameter of specified position.
	 * @param index :index of the parameter to be deleted
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean removeParameter(Connection conn, int index) throws ReadWriteException, ScheduleException
	{
		//#CM50484
		//<en> Compare the length of the arrays of the data before deletion and that of after deletion, </en>
		//<en> in order to check if the modificaiton succeeded.</en>
		int beforeLength = 0 ;
		int afterLength = 0; 
		//#CM50485
		//<en> Before modification</en>
		Parameter[] beforeArray = (Parameter[])getAllParameters();
		beforeLength = beforeArray.length ;
		//#CM50486
		//<en> Set the message nefor deleting data.</en>
		wCreater.removeParameter(conn, index);
		wMessage = wCreater.getMessage();

		//#CM50487
		//<en> After modification</en>
		Parameter[] afterArray = (Parameter[])getAllParameters();
		afterLength = afterArray.length ;
		//#CM50488
		//<en> If modification succeeded, carry out the following process;</en>
		if( beforeLength != afterLength )
		{
			//#CM50489
			//<en> If the line above the modification target line was deleted, the line no. of</en>
			//<en> the modification target line should be renewed by subtracing 1. (current line no. -1).</en>
			if (wCreater.getUpdatingFlag() > index)
			{
				wCreater.setUpdatingFlag(wCreater.getUpdatingFlag() - 1 );
			}
			//#CM50490
			//<en> If the modification target line itself was deleted, alter the updating flag to 'OFF'.</en>
			else if (wCreater.getUpdatingFlag() == index)
			{
				wCreater.setUpdatingFlag(NO_UPDATING);
			}
		}
		return true;
		
	}
	//#CM50491
	/**<en>
	 * Delete all the parameters.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public boolean removeAllParameters(Connection conn) throws ReadWriteException, ScheduleException
	{
		wMessage = "";
		if (getAllParameters().length != 0)
		{
			wCreater.removeAllParameters(conn);
			//#CM50492
			//<en> Alter the updating flag to 'OFF'.</en>
			wCreater.setUpdatingFlag(NO_UPDATING);
		}
		wMessage = wCreater.getMessage() ;
		return true;

	}
	
	//#CM50493
	/**<en>
	 * Start the schedule process.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exceptions occur during teh schedule procesing.
	 </en>*/
	public boolean startScheduler(Connection conn) throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM50494
			//<en> Carry out the processing.</en>
			boolean status = wCreater.startMaintenance(conn);
			wMessage = wCreater.getMessage();
			if (status)
			{
				schCommit(conn);
			}
			else
			{
				schRollBack(conn);
			}
			return status;
		}
		catch(ReadWriteException e)
		{
			e.printStackTrace();
			throw new ReadWriteException(e.getMessage());
		}
		catch(ScheduleException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			schRollBack(conn);
		}
	}

	//#CM50495
	/**<en>
	 * Return the message which has been set in message storage area.
	 </en>*/
	public String getMessage()
	{
		return wMessage;
	}

	//#CM50496
	// Package methods -----------------------------------------------

	//#CM50497
	// Protected methods ---------------------------------------------
	//#CM50498
	/**<en>
	 * Generate the instance of the class implemetned with <code>Creater</code> interface
	 * according to the scheduler ID.
	 * @param id    :scheduler ID
	 * @param kind  :proess type
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if the specifeid scheduler ID does not exist.
	 </en>*/ 
	protected void createInstance(Connection conn, int id, int kind) throws ReadWriteException, ScheduleException
	{
		//#CM50499
		//<en>Warehouse setting</en>
		if(id == WAREHOUSE)
		{
			wCreater = new WarehouseCreater(conn, kind);
		}
		//#CM50500
		//<en>Group contrrleer setting</en>
		else if(id == GROUPCONTROLLER)
		{
			wCreater = new GroupControllerCreater(conn, kind);
		}
		//#CM50501
		//<en>Hard zone setting</en>
		else if(id == HARDZONE)
		{
			wCreater = new HardZoneCreater(conn, kind);
		}
		//#CM50502
		//<en>Aisle setting</en>
		else if(id == AISLE)
		{
			wCreater = new AisleCreater(conn, kind);
		}
		//#CM50503
		//<en>Station setting</en>
		else if(id == STATION)
		{
			wCreater = new StationCreater(conn, kind);
		}
		//#CM50504
		//<en>Machine information setting</en>
		else if(id == MACHINE)
		{
			wCreater = new MachineCreater(conn, kind);
		}
		//#CM50505
		//<en>Carry route setting</en>
		else if(id == ROUTE)
		{
			wCreater = new RouteCreater(conn, kind);
		}
		//#CM50506
		//<en>Unavailable location setting</en>
		else if(id == UNAVAILABLELOCATION)
		{
			wCreater = new UnavailableLocationCreater(conn, kind);
		}
		//#CM50507
		//<en>Workshop setting</en>
		else if(id == WORKPLACE)
		{
			wCreater = new WorkPlaceCreater(conn, kind);
		}
		//#CM50508
		//<en>Dummy station setting</en>
		else if(id == DUMMYSTATION)
		{
			wCreater = new DummyStationCreater(conn, kind);
		}
		//#CM50509
		//<en>Hard zone setting (individual)</en>
		else if(id == INDIVIDUALLYHARDZONE)
		{
			wCreater = new IndividuallyHardZoneCreater(conn, kind);
		}
		else
		{
			throw new ScheduleException("Can't find SchedulerID.");
		}
	}
	//#CM50510
	 /**<en>
	 * Set the database conection.
	 * @param conn :database connection
	 </en>*/

	//#CM50511
	// Private methods -----------------------------------------------
	//#CM50512
	/**<en>
	 * Process the confirmation of the schedule. Concretely it commits the transaction.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @param msg :message
	 </en>*/
	private void schCommit(Connection conn) throws ReadWriteException
	{
		try
		{
			conn.commit();
		}
		catch (SQLException e)
		{
			//#CM50513
			//<en>6126001 = Database error occurred.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), (String)this.getClass().getName() ) ;
			schRollBack(conn);
			throw new ReadWriteException();
		}
	}

	//#CM50514
	/**<en>
	 * Process the cancelation of the schedule. Concretely it rollbacks the transaction.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @param msg :message
	 </en>*/
	private void schRollBack(Connection conn) throws ReadWriteException
	{
		try
		{
			conn.rollback();
		}
		catch (SQLException e)
		{
			//#CM50515
			//<en>6126001 = Database error occurred.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), (String)this.getClass().getName() ) ;
			throw new ReadWriteException();
		}
	}
}
//#CM50516
//end of class


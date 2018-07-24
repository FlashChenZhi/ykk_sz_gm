// $Id: AbstractCreater.java,v 1.2 2006/10/30 02:52:08 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50089
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;

//#CM50090
/**<en>
 * This is the abstruct class which operates the environment setting process.
 * It implements the Creater interface and the processes requried to implement this interface itself.
 * The common method is implemented in this class, and individual behaviours such as actual maintenance
 * and other are implemented by the classes derived from this class.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:08 $
 * @author  $Author: suresh $
 </en>*/
public abstract class AbstractCreater implements Creater
{
	//#CM50091
	// Class fields --------------------------------------------------
	//#CM50092
	// Class variables -----------------------------------------------

	//#CM50093
	/**<en>
	 * Delimiters
	 * his is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;
	
	
	//#CM50094
	/**<en>
	 * Preserve the max number of parameters which can be preserved in wParamVec.
	 </en>*/
	private final int wInputMaxCount = 50;
	
	//#CM50095
	/**<en>
	 * Store the detail message for what occurred during the execution of maintenance process.
	 </en>*/
	protected String wMessage = "";
	
	//#CM50096
	/**<en>
	 * Preserve the parameter array.
	 </en>*/
	protected Vector wParamVec ;

	//#CM50097
	/**<en>
	 * Product number
	 </en>*/
	private String wProductionNumber = "";

	//#CM50098
	/**<en>
	 * Process type
	 </en>*/
	private int wProcessingKind ;

	//#CM50099
	/**<en>
	 * Update flag
	 </en>*/
	protected int wUpdating = ScheduleInterface.NO_UPDATING;
	//#CM50100
	// Class method --------------------------------------------------
	//#CM50101
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:08 $") ;
	}

	//#CM50102
	// Constructors --------------------------------------------------

	//#CM50103
	/**<en>
	 * Initialize this class.
	 * @param conn :connection object with database
	 * @param kind :process type
	 * @param pno  :product number
	 </en>*/
	public AbstractCreater(Connection conn, int kind, String pno)
	{
		wProcessingKind = kind;
		wProductionNumber = pno;
		wParamVec = new Vector(wInputMaxCount);
	}
	//#CM50104
	/**<en>
	 * Initialize this class.
	 * @param conn :Connection object with database
	 * @param kind :process type
	 </en>*/
	public AbstractCreater(Connection conn, int kind)
	{
		this(conn, kind, "");
	}


	//#CM50105
	// Public methods ------------------------------------------------
	//#CM50106
	/**<en>
	 * Return the number of parameter arrays that the class implemented with the interface preserves.
	 * Or return -1 if data is being updated. 
	 * @return :number of schedule parameters
	 </en>*/
	public int getParameterCount()
	{
		return getParameters().length;
	}
	//#CM50107
	/**<en>
	 * Start the maintenance process. 
	 * Actual processing of maintenance will be implemented by <code>doStart()</code>.
	 * This method is implemented in order to ensure htat parameters should be checked prior to
	 * the maintenance process.
	 * Return true if maintenance process succeeded, or false if failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :returns true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean startMaintenance(Connection conn) throws ReadWriteException, ScheduleException
	{
		//#CM50108
		//<en> Initialization of the message</en>
		setMessage("");
		try
		{
			//#CM50109
			//<en>Check the prarmaters.</en>
			Parameter paramarray[] = (Parameter[])getParameters(); 
			for(int i = 0; i < paramarray.length; i++)
			{
				if(!check(conn, (Parameter)paramarray[i]))
				{
					return false;
				}
			}
			//#CM50110
			//<en>Execute the maintenance process.</en>
			if(doStart(conn))
			{
				return true;
			}
			return false;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}
	
	//#CM50111
	/**<en>
	 * Append the specified parameter. This method will be used for initial display in input data sumary.<BR>
	 * It will not process internal checks, only appends the parameters.<BR>
	 * @param param :contents of parameter to append
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the check process.
	 </en>*/
	public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException
	{
		//#CM50112
		//<en> Initialization of the message</en>
		setMessage("");
		//#CM50113
		//<en>Complement the parameters.</en>
		Parameter cParam = complementParameter(param);
		if(cParam == null)
		{
			return false;
		}
		wParamVec.add(cParam);
		return true;
	}

	
	//#CM50114
	/**<en>
	 * Append the specified parameters.<BR>
	 * It returns true if the parameters are appended successfully, or returns false if it failed.<BR>
	 * If this process failed, its reason can be obtained by <code>getMessage</code>.<BR>
	 * @param param :contents of parameters to add
	 * @return :returns true if the process succeeded or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the check process.
	 </en>*/
	public boolean addParameter(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		//#CM50115
		//<en> Initialization of the message</en>
		setMessage("");
		//#CM50116
		//<en>Check when appending the parameter.</en>
		if (addCheck(conn, param))
		{
			//#CM50117
			//<en>Complement the parameters.</en>
			Parameter cParam = complementParameter(param);
			if(cParam == null)
			{
				return false;
			}
			wParamVec.add(cParam);
			return true;
		}
		
		return false;
	}

	//#CM50118
	/**<en>
	 * Replace the parameter of the specified position.<BR>
	 * It returns true if modification of the parameter succeeded or returns false if it failed.<BR>
	 * If the modification of the parameter failed, its reason can be obtained by <code>getMessage</code>.<BR>
	 * @param index :position of the parameter to modify
	 * @param param :contents of the parameter to modify
	 * @return :returns true if modification of the parameter succeeded or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check process.
	 * @throws ScheduleException  :Notifies if there is no parameter in specified position.
	 </en>*/
	public boolean changeParameter(Connection conn, int index, Parameter param) throws ReadWriteException, ScheduleException
	{
		//#CM50119
		//<en> Initialization of the message</en>
		setMessage("");
		Parameter cParam = complementParameter(param);

		if (addCheck(conn, cParam))
		{
			try
			{
				wParamVec.set(index, cParam);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				throw new ScheduleException(e.getMessage());
			}
			return true;
		}
		return false;
	}

	//#CM50120
	/**<en>
	 * Delete the parameter of specified position.<BR>
	 * @param index :position of the parameter to delete
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ScheduleException
	{
		//#CM50121
		//<en> Initialization of the message</en>
		setMessage("");
		try
		{
			wParamVec.remove(index);
			setMessage("6121003");
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM50122
	/**<en>
	 * Delete all parameters.<BR>
	 </en>*/
	public void removeAllParameters(Connection conn) throws ScheduleException
	{
		wParamVec.removeAllElements();
		setMessage("6121003");
	}

	//#CM50123
	/**<en>
	 * Returns the parameter arrays that the class, inplmented with this interface, preserves.
	 * As parameters to return during the maintenance process differ depending on the process,
	 * this method will be implemented by each processing class.
	 * @return :parameter array
	 </en>*/
	public  Parameter[] getParameters()
	{
		//#CM50124
		//<en> Initialization of the message</en>
		setMessage("");
		if  (getUpdatingFlag() == ScheduleInterface.NO_UPDATING)
		{

			Parameter[] params = new Parameter[wParamVec.size()];
			wParamVec.copyInto(params);
			return params;
		}
		else
		{
			Parameter[] params = new Parameter[wParamVec.size()];
			wParamVec.copyInto(params);

			Parameter[] rparams = new Parameter[params.length - 1];
			for (int i = 0, ins = 0 ; ins < rparams.length ; i++)
			{
				if (i != getUpdatingFlag())
				{
					rparams[ins] = params[i];
					ins++;
				}
			}
			return rparams;
		}

	}

//#CM50125
/* 2003.05.29 tahara change start */

	//#CM50126
	/**<en>
	 * Returns the parameter arrays that the class, inplmented with this interface, preserves.
	 * As parameters to return during the maintenance process differ depending on the process,
	 * this method will be implemented by each processing class.
	 * Also return the updating data.
	 * @return :parameter array
	 </en>*/
	public  Parameter[] getAllParameters()
	{
		//#CM50127
		//<en> Initialization of the message</en>
		setMessage("");

		Parameter[] params = new Parameter[wParamVec.size()];
		wParamVec.copyInto(params);
		return params;
	}
//#CM50128
/* 2003.05.29 tahara change end */

	
	//#CM50129
	/**<en>
	 * Return teh entered parameter currently is the target of modification.
	 * @return :arry of the schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check process.
	 </en>*/
	public Parameter getUpdatingParameter() throws ReadWriteException, ScheduleException
	{
		//#CM50130
		//<en> Initialization of the message</en>
		setMessage("");
		if (getUpdatingFlag() == ScheduleInterface.NO_UPDATING)
		{
			//#CM50131
			//<en> The data is not in update process.</en>
			wMessage = "9999999";
			return null;
		}
		Parameter[] params = new Parameter[wParamVec.size()];
		wParamVec.copyInto(params);
		return params[getUpdatingFlag()];
	}
	//#CM50132
	/**<en>
	 * Processes the paramter check. It will be called before the execution of maintenance process.
	 * As parameter check differs depending on the type of maintenance, this method will be implemented 
	 * by the class derived from <code>AbstractCreater</code> class.
	 * It returns true if the parameter check succeeded or returns false if it failed.
	 * If parameter check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :content of parameter to check
	 * @return :returns true if the parameter check succeeded or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check process.
	 </en>*/
	public abstract boolean check(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;

	//#CM50133
	/**<en>
	 * Processes the parameter duplicate check.
	 * As parameter duplicate check differs depending on the maintenance type, this method will be 
	 * implemented by the class which inherited <code>AbstractCreater</code> class.
	 * In ordinaly process, there are 2 different checks are considered to be necessary.<BR>
	 * 1: Check of identical data on the display aspect.(check whether/not the identical data were selected
	 *    from the listbox.<BR>
	 * 2: Check the identical data in entered data summary (including the DB checks)<BR>
	 * Check of identical data on the display aspect will be defined by a method called isSameData().
	 * For the duplicate data check of entered data summary ane the DB data which have been already entered,
	 * mew methods will be created in accordance with the contents of each process.
	 * Normally the only #2 will be checked for data registrations. If data is modified, #1 and #2 will 
	 * be checked. In case of data deletion, only #1 will be checked.
	 * Please refer to the example below for the implementation method.
	 *	<pre>
	 *	public boolean duplicationCheck(Parameter param) throws ReadWriteException, ScheduleException
	 * 	{
	 *		ZoneMaintenanceParameter[] mArray = (ZoneMaintenanceParameter[])getParameters();
	 *		ZoneMaintenanceParameter mParam = (ZoneMaintenanceParameter)param;
	 *
	 *		//In case of data registration,
	 *		if(getProcessingKind() == M_CREATE)
	 *		{
	 *			//Check the zone range:
	 *			if(isSameZoneRange(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 *			return true;
	 *		}
	 *		//In case of data modification,
	 *		else if(getProcessingKind() == M_MODIFY)
	 *		{
	 *			//Check the identical data:
	 *			if(isSameData(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 *			//Check the range of the zones:
	 *			if(isSameZoneRange(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 * 			return true;
	 *		}
	 *		//In case of data deletion:
	 *		else
	 *		{
	 *			//Check the identical data:
	 *			if(isSameData(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 *			return true;
	 *		}
	 *	}
	 *	</pre>
	 * It returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :contens of parameter to check
	 * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	/**<en>
	 * Processes the parameter duplicate check.
	 * As parameter duplicate check differs depending on the maintenance type, this method will be 
	 * implemented by the class which inherited <code>AbstractCreater</code> class.
	 * In ordinaly process, there are 2 different checks are considered to be necessary.<BR>
	 * 1: Check of identical data on the display aspect.(check whether/not the identical data were selected
	 *    from the listbox.<BR>
	 * 2: Check the identical data in entered data summary (including the DB checks)<BR>
	 * Check of identical data on the display aspect will be defined by a method called isSameData().
	 * For the duplicate data check of entered data summary ane the DB data which have been already entered,
	 * mew methods will be created in accordance with the contents of each process.
	 * Normally the only #2 will be checked for data registrations. If data is modified, #1 and #2 will 
	 * be checked. In case of data deletion, only #1 will be checked.
	 * Please refer to the example below for the implementation method.
	 *	<pre>
	 *	public boolean duplicationCheck(Parameter param) throws ReadWriteException, ScheduleException
	 * 	{
	 *		ZoneMaintenanceParameter[] mArray = (ZoneMaintenanceParameter[])getParameters();
	 *		ZoneMaintenanceParameter mParam = (ZoneMaintenanceParameter)param;
	 *
	 *		//In case of data registration,
	 *		if(getProcessingKind() == M_CREATE)
	 *		{
	 *			//Check the zone range:
	 *			if(isSameZoneRange(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 *			return true;
	 *		}
	 *		//In case of data modification,
	 *		else if(getProcessingKind() == M_MODIFY)
	 *		{
	 *			//Check the identical data:
	 *			if(isSameData(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 *			//Check the range of the zones:
	 *			if(isSameZoneRange(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 * 			return true;
	 *		}
	 *		//In case of data deletion:
	 *		else
	 *		{
	 *			//Check the identical data:
	 *			if(isSameData(mParam, mArray))
	 *			{
	 *				return false;
	 *			}
	 *			return true;
	 *		}
	 *	}
	 *	</pre>
	 * It returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * If parameter duplicate check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :contens of parameter to check
	 * @return :returns true if the parameter duplicate check has succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter duplicate check.
	 </en>*/
	public abstract boolean duplicationCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;


	//#CM50134
	/**<en>
	 * Retrieve the message for what occurred during the maintenance processing.
	 * @return :message
	 </en>*/
	public String getMessage()
	{
		return wMessage;
	}

	//#CM50135
	/**<en>
	 * Set the update flag.
	 * @param flag :update flag
	 </en>*/
	public void setUpdatingFlag(int flag)
	{
		wUpdating = flag;
	}
	//#CM50136
	/**<en>
	 * Retrieve the update flag.
	 * @return :update flag
	 </en>*/
	public int getUpdatingFlag()
	{
		return wUpdating;
	}
	//#CM50137
	/**<en>
	 * Implement the maintenance process.
	 * As maintenance process differs depending on the maintenance type, this method will be 
	 * implemented by the class which inherited <code>AbstractMaintenance</code> class.
	 * It is necessary that type of the maintentace should be determined internally according to
	 * the process type (obtained by getProcessingKind() method.)
	 * Return true if maintenance process succeeded, or false if failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :true if process succeeded, or false if failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public abstract boolean doStart(Connection conn) throws ReadWriteException, ScheduleException;
	
	//#CM50138
	// Package methods -----------------------------------------------

	//#CM50139
	// Protected methods ---------------------------------------------
	//#CM50140
	/**<en>
	 * Check the number of entered data in entered data summary.
	 * This method compares the value of (DISPLAY_INPUT_MAX_COUNT) defined in the AWC resource
	 * parameter file with the number of entered data that this instance currently preserves
	 * in the <code>wParamVec</code>.
	 * If number of entered data is lower than the value set by DISPLAY_INPUT_MAX_COUNT, it will 
	 * return true.
	 * And if number of entered data is higher than the valud set by DISPLAY_INPUT_MAX_COUNT, 
	 * it will return false.
	 * @return :true if the number of entered data is lower than the value set by 
	 * DISPLAY_INPUT_MAX_COUNT, or return false if higher than DISPLAY_INPUT_MAX_COUNT.
	 </en>*/
	protected boolean checkInputMaxCount()
	{
		if (getParameterCount() >= wInputMaxCount)
		{
			//#CM50141
			//<en> Return false if the number of entered data exceeded the max number of enterd data acceptable.</en>
			setMessage("6123276");
			return false;
		}
		return true;
	}
	
	
	//#CM50142
	/**<en>
	 * Retrieve the product no.
	 * @return :product no.
	 </en>*/
	protected String getProductionNumber()
	{
		return wProductionNumber;
	}
	//#CM50143
	/**<en>
	 * Set the product no.
	 * @param product no.
	 </en>*/
	protected void setProductionNumber(String pno)
	{
		wProductionNumber = pno;
	}
	
	//#CM50144
	/**<en>
	 * Retrieve the process type.
	 * @return :process type
	 </en>*/
	protected int getProcessingKind()
	{
		return wProcessingKind;
	}
	//#CM50145
	/**<en>
	 * Set the process type.
	 * @param process type
	 </en>*/
	protected void setProcessingKind(int kind)
	{
		wProcessingKind = kind;
	}
	//#CM50146
	/**<en>
	 * Complement the parameters.
	 * It implements the process such as setting the instance as a aprameter in order to check 
	 * if data has been modified by other terminal, or setting the article name based on the item code.
	 * As this process differs depending on the maintenance type, this method will be implemented by
	 * the class which inherited <code>AbstractCreater</code> class.
	 * Please implement accordingly so that the complemented parameter to be returned if the process
	 * succeeded, and null to be retruned if it failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter to set the instance
	 * @return :return the complemented parameters. Or return null if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the process.
	 </en>*/
	protected abstract Parameter complementParameter(Parameter param)throws ReadWriteException, ScheduleException;

	//#CM50147
	/**<en>
	 * Process the parameter check. This method will be called when appending the parameters.
	 * As parameter check differs depending on the type of maintenance, this method will be 
	 * implemented  by the class derived from <code>AbstractCreater</code> class.
	 * It returns true if the parameter check succeeded or returns false if it failed.
	 * If parameter check failed, its reason can be obtained by <code>getMessage</code>.
	 * @param param :parameter contents to check
	 * @return :returns true if the parameter check succeeded or returns false if it failed.e.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check. 
	 </en>*/
	protected boolean addCheck(Connection conn, Parameter param) throws ReadWriteException, ScheduleException
	{
		//#CM50148
		//<en>Check the duplicate data.</en>
		if(duplicationCheck(conn, param))
		{
			//#CM50149
			//<en>Carry out the checks.</en>
			if(check(conn, param))
			{
				return true;
			}
		}
		return false;
	}

	//#CM50150
	/**<en>
	 * Set the specified message in the message storage area.
	 * @param msg :message
	 </en>*/
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM50151
	// Private methods -----------------------------------------------
}
//#CM50152
//end of class


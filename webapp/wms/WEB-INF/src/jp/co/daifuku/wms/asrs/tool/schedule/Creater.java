// $Id: Creater.java,v 1.2 2006/10/30 02:52:06 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50394
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
//#CM50395
/**<en>
 * This is a common interface which conducts the environment setting.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD> kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:06 $
 * @author  $Author: suresh $
 </en>*/
public interface Creater
{
	//#CM50396
	// Class fields --------------------------------------------------
	//#CM50397
	/**<en>
	 * Value of process type ( to specify when calling the method externally and 
	 * when the process type is not specified.)
	 </en>*/
	public static final int M_NOPROCESS= 0;
	//#CM50398
	/**<en>
	 * Value of process type (query)
	 </en>*/
	public static final int M_QUERY = 4;
	//#CM50399
	/**<en>
	 * Value of process type (modification)
	 </en>*/
	public static final int M_MODIFY = 1;
	
	//#CM50400
	/**<en>
	 * Value of process type (registration)
	 </en>*/
	public static final int M_CREATE = 2;
	
	//#CM50401
	/**<en>
	 * Value of process type (deletion)
	 </en>*/
	public static final int M_DELETE = 3;

	//#CM50402
	// Class variables -----------------------------------------------
	//#CM50403
	// Class method --------------------------------------------------
	//#CM50404
	// Constructors --------------------------------------------------

	//#CM50405
	// Public methods ------------------------------------------------
	//#CM50406
	/**<en>
	 * Retrieve data to isplay on the screen.<BR>
	 * @param <code>Locale</code> object
	 * @param searchParam :schedule parameter
	 * @return :array of schedule parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected exception occurred during the check process.
	 </en>*/
	public Parameter[] query(Connection conn, Locale locale, Parameter searchParam) throws ReadWriteException, ScheduleException;

	//#CM50407
	/**<en>
	 * Append a specified parameter. This method will be used at the initial display in entered data summary.<BR>
	 * The parameter will be appended without processing internal checks.<BR>
	 * @param param :contents of appending parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the check.
	 </en>*/
	public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException;


	//#CM50408
	/**<en>
	 * Append a specified parameter. <BR>
	 * It returns true if the parameter was appended successfully, or false if failed.<BR>
	 * If parameter was not appended, the reason can be obtained by <code>getMessage</code>.<BR>
	 * @param param :contents of parameter to append
	 * @return :returns true if the parameter was appended successfully, or false if failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the check.
	 </en>*/
	public boolean addParameter(Connection conn, Parameter param) throws ReadWriteException, ScheduleException;

	//#CM50409
	/**<en>
	 * Replace the parameter of specified position.<BR>
	 * It returns true if modification of parameter succeeded, or returns false if it failed.<BR>
	 * If modification of parameter failed, the reason can be retrieved by <code>getMessage</code>. <BR>
	 * @param index :position (index) of the parameter to be modified
	 * @param param :contents of the parameter to be modified
	 * @return :returns true if modification of parameter succeeded, or returns false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException :Notifies if unexpected error occurred during the parameter check.
	 * @throws ScheduleException :Notifies if there are no parameters in specified position.
	 </en>*/
	public boolean changeParameter(Connection conn, int index, Parameter param) throws ReadWriteException, ScheduleException;

	//#CM50410
	/**<en>
	 * Delete the parameter of the specified position.<BR>
	 * @param index :position (index) of the parameter to be deleted
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 * @throws ScheduleException  :Notifies if there are no parameters in specified position.
	 </en>*/
	public void removeParameter(Connection conn, int index) throws ReadWriteException, ScheduleException;

	//#CM50411
	/**<en>
	 * Delete the all parameters.<BR>
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public void removeAllParameters(Connection conn) throws ReadWriteException, ScheduleException;

	//#CM50412
	/**<en>
	 * Return the array of the parameter that the class implemented with this interface preserves.
	 * @return :parrameter array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public Parameter[] getParameters() throws ReadWriteException, ScheduleException;

//#CM50413
/* 2003.05.29 tahara change start */

	//#CM50414
	/**<en>
	 * Return the array of the parameter that the class implemented with this interface preserves.
	 * Also returns the data in modificaiton.
	 * @return :the array of parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public Parameter[] getAllParameters() throws ReadWriteException, ScheduleException;

//#CM50415
/* 2003.05.29 tahara change end */


	//#CM50416
	/**<en>
	 * Return the array of the parameter that the class implemented with this interface preserves.
	 * @return :the array of parameter
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the parameter check.
	 </en>*/
	public Parameter getUpdatingParameter() throws ReadWriteException, ScheduleException;
	//#CM50417
	/**<en>
	 * Start the maintenance process.
	 * Return true if maintenance process succeeded, or false if failed.
	 * If the process failed, its reason can be obtained by <code>getMessage</code>.
	 * @return :true if the process succeeded, or false if it failed.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the maintenance process.
	 </en>*/
	public boolean startMaintenance(Connection conn) throws ReadWriteException, ScheduleException;

	//#CM50418
	/**<en>
	 * Retrieve the message concerning what occurred during the maintenance process.
	 * @return :message
	 </en>*/
	public String getMessage();

	//#CM50419
	/**<en>
	 * Set the updating flag.
	 * @param flag :updating flag
	 </en>*/
	public void setUpdatingFlag(int flag);
	//#CM50420
	/**<en>
	 * Retrieve the updating flag.
	 * @return :updating flag
	 </en>*/
	public int getUpdatingFlag();

	//#CM50421
	// Package methods -----------------------------------------------

	//#CM50422
	// Protected methods ---------------------------------------------

	//#CM50423
	// Private methods -----------------------------------------------
}
//#CM50424
//end of class


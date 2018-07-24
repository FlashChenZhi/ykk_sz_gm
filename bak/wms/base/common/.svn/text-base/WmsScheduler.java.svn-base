package jp.co.daifuku.wms.base.common;

//#CM643596
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;

//#CM643597
/**
 * Designer : K.Mori <BR>
 * Maker : K.Mori <BR>
 *
 * <CODE>WmsScheduler</CODE> interface provides for the operation of the schedule processing. <BR>
 * The class such as <CODE>xxxBusiness</CODE> which processes the screen does the schedule processing through this interface. 
 * Do <CODE>implement</CODE> this interface, and mount processing each schedule processing. <BR>
 * 
 * <CODE>query(Connection conn, Parameter searchParam)</CODE><BR>
 * The method used to acquire the display data when data is displayed in Filtering area to press the display button. <BR>
 * 
 * <CODE>check(Connection conn, Parameter checkParam)</CODE><BR>
 * The method used before data is moved to Filtering area for the input button pressing to do the input check. <BR>
 * 
 * <CODE>startSCH(Connection conn, Parameter[] startParams)</CODE><BR>
 * The method used when the data update processing and the schedule processing are executed by pressing the Start button.<BR>
 *
 * Do <CODE>implement</CODE> this interface, and mount processing each schedule processing. <BR>
 * Mount <CODE>SchedulerException</CODE> on slow about it is not necessary to use mount method. 
 *  
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/21</TD><TD>K.Mori</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:03:28 $
 * @author  $Author: suresh $
 */
public interface WmsScheduler
{

	//#CM643598
	/**
	 * The method corresponding to the operation to acquire necessary data when default is displayed the screen. <BR>
	 * It is assumed Ex, an initial display of Consignor Code exists, and there is a button initial display of the external data loading. <BR>
	 * Search condition passes the class which succeeds to <CODE>Parameter</CODE> class. <BR>
	 * Set null in <CODE>searchParam</CODE> when you do not need Search condition. 
	 * @param conn Connection object with data base
	 * @param searchParam Class which succeeds to <CODE>Parameter</CODE> class with search condition
	 * @return Class which mounts <CODE>Parameter</CODE> interface where retrieval result is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException;

	//#CM643599
	/**
	 * Acquire the data displayed to the screen. The method corresponding to the operation when the display button is pressed. <BR>
	 * Search condition passes the class which succeeds to <CODE>Parameter</CODE> class. <BR>
	 * Return the result with Array because the display data might have two or more records. <BR>
	 * Return empty Array when pertinent data is not found. <BR>
	 * Return null when the retrieval processing fails because of the input condition error etc.<BR>
	 * In this case, the content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param searchParam Class which succeeds to <CODE>Parameter</CODE> class with search condition
	 * @return Class which mounts <CODE>Parameter</CODE> interface where retrieval result is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the check processing is generated. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException;

	//#CM643600
	/**
	 * Check whether the content of the parameter is correct. Do the parameter input content check processing according to the content set in the <BR>
	 * parameter specified by <CODE>checkParam</CODE>. Mounting the check processing is different according to the class which mounts this interface. <BR>
	 * Return true when the content of the parameter is correct. <BR>
	 * Return false when there is a problem in the content of the parameter. The content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param checkParam Parameter class where content which does input check is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True: When the content of the parameter is normal  False : Otherwise
	 */
	public boolean check(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException;

	//#CM643601
	/**
	 * Check whether the content of the parameter is correct. Do the parameter input content check processing according to the content set in the <BR>
	 * parameter specified by <CODE>checkParam</CODE>. Mounting the check processing is different according to the class which mounts this interface. <BR>
	 * To check whether to have input it, the parameter specified by checking Filtering area the input <BR>
	 * has passed the list of the parameter input with inputParams together. <BR>
	 * Return true when the content of the parameter is correct. <BR>
	 * Return false when there is a problem in the content of the parameter. The content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param checkParam Parameter class where content which does input check is included
	 * @param inputParams Array of parameter class which passes it as data which has been input
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True: When the content of the parameter is normal  False : Otherwise
	 */
	public boolean check(Connection conn, Parameter checkParam, Parameter[] inputParams)
		throws ReadWriteException, ScheduleException;

	//#CM643602
	/**
	 * Check whether the content of the parameter is correct. Do the parameter input content check processing according to the content set in the <BR>
	 * parameter specified by <CODE>checkParam</CODE>. Mounting the check processing is different according to the class which mounts this interface. <BR>
	 * Return true when the content of the parameter is correct. <BR>
	 * Return false when there is a problem in the content of the parameter. The content can be acquired by using <CODE>getMessage() </CODE> method. <BR>
	 * This method mounts the input check when changing from the 1st screen to the second screen in two screen transition.
	 * @param conn Connection object with data base
	 * @param checkParam Parameter class where content which does input check is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return True: When the content of the parameter is normal  False : Otherwise
	 */
	public boolean nextCheck(Connection conn, Parameter checkParam)
		throws ReadWriteException, ScheduleException;

	//#CM643603
	/**
	 * Begin scheduling. According to the content set in parameter Array specified by < CODE>startParams</CODE ><BR>
	 * Do the schedule processing. Mounting the schedule processing is different according to the class which mounts this interface. <BR>
	 * Return true when the schedule processing succeeds. <BR>
	 * Return false when the schedule processing fails because of the condition error etc.<BR>
	 * In this case, the content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param startParams Array of parameter class where set content is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return The schedule processing returns true in case of normality and false when the schedule processing fails.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException;

	//#CM643604
	/**
	 * Begin scheduling. According to the content set in parameter Array specified by <CODE>startParams</CODE><BR>
	 * Do the schedule processing. Mounting the schedule processing is different according to the class which mounts this interface. <BR>
	 * Use this method when you display the content of the screen display again based on the result of the schedule. 
	 * Return null when the schedule processing fails because of the condition error etc.<BR>
	 * In this case, the content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param startParams Array of parameter class where set content is included
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return Instance to mount <CODE>Parameter</CODE> class where retrieval result is included
	 */
	public Parameter[] startSCHgetParams(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException;

	//#CM643605
	/**
	 * The specified parameter is acquired with <CODE>countParam</CODE> and the number of cases of the object data is acquired in the search condition.<BR>
	 * Mounting the schedule processing is different according to the class which mounts this interface. <BR>
	 * Return 0 when there is no object data or is an input error. <BR>
	 * The content can be acquired by using <CODE>getMessage() </CODE> method. 
	 * @param conn Connection object with data base
	 * @param countParam Parameter including search condition
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws ScheduleException It is notified when the exception not anticipated in the schedule processing is generated. 
	 * @return Object qty(0 when there is input error or there is no object)
	 */
	public int count(Connection conn, Parameter countParam)
		throws ReadWriteException, ScheduleException;

	//#CM643606
	/**
	 * When false is returned by <CODE>startSCH</CODE> method and <CODE>check method </CODE>, acquire Message to display the reason. <BR>
	 * Use this method to acquire the content of the display of the screen to the message area. 
	 * @return Details of Message
	 */
	public String getMessage();

}

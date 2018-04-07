// $Id: ToolScheduleInterface.java,v 1.2 2006/10/30 02:52:00 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM51804
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ScheduleInterface;


//#CM51805
/**<en>
 * This is the interface which declared the common method for the exection of 
 * schedule processing via screen.<BR>
 * It prescribes the methods required for display operatrion.<BR>
 * The class, which implements this interface, should implement following operations.<BR>
 *   input of <code>addInitialParameter()</code> parameter<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/06/03</TD><TD>Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:00 $
 * @author  $Author: suresh $
 </en>*/
public interface ToolScheduleInterface extends ScheduleInterface
{
	//#CM51806
	// Class fields --------------------------------------------------
	//#CM51807
	// Public methods ------------------------------------------------
	//#CM51808
	/**<en>
	 * Append the specified parameters. This method will be used for the initial display of entered
	 * data summary.<BR>
	 * It appends parameters without conducting the internal checks.<BR>
	 * @param param :contents of the parameter to add
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ScheduleException  :Notifies if unexpected error occurred during the check.
	 </en>*/
	public boolean addInitialParameter(Parameter param) throws ReadWriteException, ScheduleException;

	//#CM51809
	// Package methods -----------------------------------------------

	//#CM51810
	// Protected methods ---------------------------------------------

	//#CM51811
	// Private methods -----------------------------------------------

}
//#CM51812
//end of class


// $Id: MachineState.java,v 1.2 2006/10/26 08:25:44 suresh Exp $
package jp.co.daifuku.wms.asrs.equipment ;

//#CM42093
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Entity;

//#CM42094
/**<en>
 * This is the virtual class which will be used to control the machine status. The implementations
 * of machine status differ depending on the controllers of lower class, therefore, please utilize the implementations of each protocol.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/03/19</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:25:44 $
 * @author  $Author: suresh $
 </en>*/
public abstract class MachineState extends Entity
{
	//#CM42095
	// Class variables -----------------------------------------------
	//#CM42096
	/**<en>
	 * Machine type
	 </en>*/
	protected int wType = 0 ;
	
	//#CM42097
	/**<en>
	 * Machine no.
	 </en>*/
	protected int wNumber = 0 ;

	//#CM42098
	// Class method --------------------------------------------------
     //#CM42099
     /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:25:44 $") ;
	}
	
	//#CM42100
	// Constructors --------------------------------------------------

	//#CM42101
	// Public methods ------------------------------------------------
	//#CM42102
	/**<en>
	 * Set the machine status. It is necessary that subclasses should implement the detailed processes.
	 * @param stat  machine status
	 </en>*/
	public abstract void setState(int stat) ;
	
	//#CM42103
	/**<en>
	 * Retrieves the machine status. It is necessary that subclasses should implement the detailed processes.
	 * @return    machine status
	 </en>*/
	public abstract int getState() ;

	//#CM42104
	/**<en>
 	 * Error code of the machine is set. Detaile processing must be implemented by sub class.
 	 * @param errcode  :machine error code
	</en>*/
	public abstract void setErrorCode(String errcode) ;
	
	//#CM42105
	/**<en>
	 * Retrieves the error codes of machine.
	 * It is necessary that subclasses should implement the detailed processes.
	 * @return    errror codes of machine
	 </en>*/
	public abstract String getErrorCode() ;

	//#CM42106
	/**<en>
	 * Investigate whether/not the machine is starting. 
	 * It is necessary that subclasses should implement the detailed processes.
	 * @return    true if starting
	 </en>*/
	public abstract boolean isRunning() ;
	
	//#CM42107
	/**<en>
	 * Checks if the machine is disconnected or not.
	 * It is necessary that subclasses should implement the detailed processes.
	 * @return    'true' if it is disconnected.
	 </en>*/
	public abstract boolean isDisconnected() ;

	//#CM42108
	// Package methods -----------------------------------------------

	//#CM42109
	// Protected methods ---------------------------------------------

	//#CM42110
	// Private methods -----------------------------------------------

}
//#CM42111
//end of class


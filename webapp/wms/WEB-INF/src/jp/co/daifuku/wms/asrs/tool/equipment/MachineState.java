// $Id: MachineState.java,v 1.2 2006/10/30 02:27:55 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.equipment ;

//#CM49313
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

//#CM49314
/**<en>
 * This is the virtual class which controls the machine status. As the implementations
 * of machine status differ depending on the controllers of the lower classes, 
 * It is necessary to usitlzie the implementation of respective protocols.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/03/19</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:27:55 $
 * @author  $Author: suresh $
 </en>*/
public abstract class MachineState extends ToolEntity
{
	//#CM49315
	// Class variables -----------------------------------------------
	//#CM49316
	/**<en>
	 * type of machine
	 </en>*/
	protected int wType = 0 ;
	
	//#CM49317
	/**<en>
	 * machine no.
	 </en>*/
	protected int wNumber = 0 ;

	//#CM49318
	// Class method --------------------------------------------------
     //#CM49319
     /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:27:55 $") ;
	}
	
	//#CM49320
	// Constructors --------------------------------------------------

	//#CM49321
	// Public methods ------------------------------------------------
	//#CM49322
	/**<en>
	 * Set the status of machines. Detailed processings must be implemented in subclasses.
	 * @param stat  :set the status of machines.
	 </en>*/
	public abstract void setState(int stat) ;
	
	//#CM49323
	/**<en>
	 * Retrieve the status of machines. Detailed processings must be implemented in subclasses.
	 * @return    :the status of machines
	 </en>*/
	public abstract int getState() ;

	//#CM49324
	/**<en>
	 * Set the error codes of mahines. Detailed processings must be implemented in subclasses.
 	 * @param errcode  :machine error code
	 </en>*/
	public abstract void setErrorCode(String errcode) ;
	
	//#CM49325
	/**<en>
	 * Retrieve the error codes of mahines.
	 * Detailed processings must be implemented in subclasses.
	 * @return    :error codes of machines
	 </en>*/
	public abstract String getErrorCode() ;

	//#CM49326
	/**<en>
	 * Examine whether/not the machine has been started up. Detailed processings must be implemented 
	 * in subclasses.
	 * @return   :true if started up
	 </en>*/
	public abstract boolean isRunning() ;
	
	//#CM49327
	/**<en>
	 * Checks if the machine is disconnected or not.
	 * It is necessary that subclasses should implement the detailed processes.
	 * @return    'true' if it is disconnected.
	 </en>*/
	public abstract boolean isDisconnected() ;

	//#CM49328
	// Package methods -----------------------------------------------

	//#CM49329
	// Protected methods ---------------------------------------------

	//#CM49330
	// Private methods -----------------------------------------------

}
//#CM49331
//end of class


// $Id: ToolEntity.java,v 1.2 2006/10/30 01:40:55 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46698
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.MessageResource;

//#CM46699
/**<en>
 * This is a parent class of entity system object. It preserves handlers 
 * for the storage and retrieval of instances.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:55 $
 * @author  $Author: suresh $
 </en>*/
public class ToolEntity extends Object
{

	//#CM46700
	// Class fields --------------------------------------------------
	//#CM46701
	/**<en> Delimiter used in messages creation </en>*/

	protected final String wDelim = MessageResource.DELIM;

	//#CM46702
	// Class variables -----------------------------------------------
	//#CM46703
	/**<en>
	 * Instance handler
	 </en>*/
	protected ToolEntityHandler wHandler = null ;

	//#CM46704
	// Class method --------------------------------------------------
	//#CM46705
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 01:40:55 $") ;
	}

	//#CM46706
	// Constructors --------------------------------------------------

	//#CM46707
	// Public methods ------------------------------------------------
	//#CM46708
	/**<en>
	 * Set the handler in order to store and retrieve the instance.
	 * @param hndler :entity handler
	 </en>*/
	public void setHandler(ToolEntityHandler hndler)
	{
		wHandler = hndler ;
	}

	//#CM46709
	/**<en>
	 * Retrieves the handler in order to store and retrieve the instance.
	 * @return :entity handler
	 </en>*/
	public ToolEntityHandler getHandler()
	{
		return(wHandler) ;
	}


	//#CM46710
	// Package methods -----------------------------------------------

	//#CM46711
	// Protected methods ---------------------------------------------

	//#CM46712
	// Private methods -----------------------------------------------

}
//#CM46713
//end of class



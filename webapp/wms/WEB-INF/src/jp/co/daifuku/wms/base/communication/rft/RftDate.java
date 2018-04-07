// $Id: RftDate.java,v 1.2 2006/11/07 06:47:11 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM644001
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

//#CM644002
/**
 * Class to treat date and time with RFT. 
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:47:11 $
 * @author  $Author: suresh $
 */
public class RftDate
{
	//#CM644003
	// Class fields --------------------------------------------------
	//#CM644004
	// Class variables -----------------------------------------------
	//#CM644005
	// Class method --------------------------------------------------
	//#CM644006
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:47:11 $");
	}

	//#CM644007
	// Constructors --------------------------------------------------
	//#CM644008
	// Public methods ------------------------------------------------
	//#CM644009
	/**
	 * Acquire present date, and edit it in the form of HHmmss. 
	 * 
	 * @return dateString Present date in the form of HHmmss
	 */
	public String getSysTime()
	{
		SimpleDateFormat formatter = new SimpleDateFormat ("HHmmss");
		Date curDate = new Date();
		String dateString = formatter.format(curDate);
		return dateString;
	}
	//#CM644010
	// Package methods -----------------------------------------------
	//#CM644011
	// Protected methods ---------------------------------------------
	//#CM644012
	// Private methods -----------------------------------------------
}
//#CM644013
//end of class

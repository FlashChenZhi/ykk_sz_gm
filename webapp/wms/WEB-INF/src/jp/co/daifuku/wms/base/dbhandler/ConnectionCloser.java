// $Id: ConnectionCloser.java,v 1.2 2006/11/15 04:25:37 kamala Exp $

//#CM708428
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.dbhandler;

import javax.servlet.http.HttpSession;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;

//#CM708429
//import jp.co.daifuku.awc.display.web.listbox.ListParameter;
//#CM708430
//import jp.co.daifuku.common.ReadWriteException;

//#CM708431
/**<jp>
 * - Logout button click<BR>
 * - Time-out of session<BR>
 * - When the window is shut using "X" button of IE window<BR>
 * Return the connection to the connection pool because of these session time-out events. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/06/30</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:37 $
 * @author  $Author: kamala $
 </jp>*/
//#CM708432
/**<en>
 * A connection is returned at the time of the session time-out.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/06/30</TD><TD>N.Sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:37 $
 * @author  $Author: kamala $
 </en>*/
public final class ConnectionCloser
{
	//#CM708433
	// Class fields --------------------------------------------------

	//#CM708434
	// Class variables -----------------------------------------------

	//#CM708435
	// Class method --------------------------------------------------
	//#CM708436
	/** <jp>
	 * Return the version of this class. 
	 * @return Version and date
	</jp> */
	//#CM708437
	/** <en>
	 * Returns the version of this class and date.
	 * @return - version and date.
	</en> */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/15 04:25:37 $");
	}

	//#CM708438
	/**<jp>
	 * Return the connection not returned to the connection pool. 
	 * @param session <code>HttpSession</code>
	 </jp>*/
	//#CM708439
	/**<en>
	 * A connection is returned at the time of the session time-out.
	 * @param session <code>HttpSession</code>
	 </en>*/
	public void close(HttpSession session)
	{
		try
		{
			//#CM708440
			//Close the connection of the object left in Session. 
			SessionRet sRet = (SessionRet) session.getAttribute("LISTBOX");
			if (sRet != null)
			{
				DatabaseFinder finder = sRet.getFinder();
				if (finder != null)
				{
					finder.close();
				}
				//#CM708441
				//	Close of connection
				sRet.closeConnection();
			}
		}
		catch (ReadWriteException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//#CM708442
	// Public methods ------------------------------------------------

	//#CM708443
	// Package methods -----------------------------------------------

	//#CM708444
	// Protected methods ---------------------------------------------

	//#CM708445
	// Private methods -----------------------------------------------

}
//#CM708446
//end of class

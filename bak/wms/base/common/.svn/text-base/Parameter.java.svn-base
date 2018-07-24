package jp.co.daifuku.wms.base.common;

//#CM642784
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
//#CM642785
/**
 * Designer : K.Mori <BR>
 * Maker : K.Mori <BR>
 *
 * <CODE>Parameter</CODE> class is the class prepared to hand over the parameter between the class such as BlueDog which processes 
 * the screen and the class which does the schedule processing. <BR>
 * Maintain the item which hands over the value between schedules screen-in the instance variable in the Paramerer class. <BR>
 * Succeed to this class, and do a necessary item in Addition when you define a peculiar item to each package. 
 * 
 * <PRE>
 * Ex.:
 * 
 *   Parameter param = new Parameter();
 *   param.setUserId("user1");
 *   param.setTerminalNumber(1);
 *   Scheduler sch = new SetStorageSCH();
 *   boolean bool = sch.checkParameter(param);
 * </PRE>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/20</TD><TD>K.Mori</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:01:33 $
 * @author  $Author: suresh $
 */
public class Parameter
{
	//#CM642786
	// Class variables -----------------------------------------------
	//#CM642787
	/**
	 * User Name. Maintain the user name at log in. 
	 */
	private String wUserName;

	//#CM642788
	/**
	 * Terminal No
	 */
	private String wTerminalNumber;

	//#CM642789
	// Public methods ------------------------------------------------
	//#CM642790
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:01:33 $") ;
	}

	//#CM642791
	/**
	 * Set the user name.
	 * @param name User Name
	 */
	public void setUserName(String name)
	{
		wUserName = name ;
	}

	//#CM642792
	/**
	 * Acquire User Name. 
	 * @return User Name
	 */
	public String getUserName()
	{
		return wUserName ;
	}

	//#CM642793
	/**
	 * Set the terminal name. 
	 * @param name User Name
	 */
	public void setTerminalNumber(String termno)
	{
		wTerminalNumber = termno ;
	}

	//#CM642794
	/**
	 * Acquire the terminal name. 
	 * @return User Name
	 */
	public String getTerminalNumber()
	{
		return wTerminalNumber ;
	}



	//#CM642795
	// Package methods -----------------------------------------------

	//#CM642796
	// Protected methods ---------------------------------------------

	//#CM642797
	// Private methods -----------------------------------------------
}
//#CM642798
//end of class

// $Id: DEBUG.java,v 1.2 2006/11/07 05:58:59 suresh Exp $
package jp.co.daifuku.wms.base.common ;

//#CM642578
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.MissingResourceException;

//#CM642579
/**
 * The class to do WareNavi system debugging Message output. 
 * The default of the resource name is <code>DEBUG</code>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:58:59 $
 * @author  $Author: suresh $
 */
public class DEBUG extends Object
{
	//#CM642580
	/**
	 * Default resource
	 */
	public static final String DEFAULT_RESOURCE = "DEBUG" ;

	//#CM642581
	// Class method --------------------------------------------------
	//#CM642582
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 05:58:59 $") ;
	}

	//#CM642583
	// Constructors --------------------------------------------------
	//#CM642584
	// No Constructors! all of method is static.

	//#CM642585
	// Public methods ------------------------------------------------
	//#CM642586
	/**
	 * Acquire the content of the parameter from the key. 
	 * @param key  Message output condition key
	 * @param pmsg  Content of output Message
	 * @return   None
	 */
	public static void MSG(String key, String pmsg)
	{
		try
		{
			String pout = DebugParam.getParam(key) ;

			switch (Integer.parseInt(pout))
			{
				case 0 :
					break ;

				case 1 :
					System.out.println(pmsg) ;
					break ;

				case 2 :
					System.out.println("No Support Parameter" + pout) ;
					break ;

				case 3 :
					System.out.println("No Support Parameter" + pout) ;
					break ;

				default :
					System.out.println("No Support Parameter" + pout) ;
					break ;
			}
		}
		catch (MissingResourceException e)
		{
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
			System.out.println("Please check the following file by connection at the time of error generating") ;
			System.out.println("File Name[C:/daifuku/wms/data/ini/DebugParam.properties]") ;
		}
	}

	//#CM642587
	// Package methods -----------------------------------------------

	//#CM642588
	// Protected methods ---------------------------------------------

	//#CM642589
	// Private methods -----------------------------------------------

	//#CM642590
	// debug methods -----------------------------------------------
	//#CM642591
	/**
	 * Main Method<BR>
	 * Do WareNavi system debugging Message output. 
	 */
	public static void main(String[] argv)
	{
		String[] keys = { "HANDLER"
						,"METHOD"
						,"SCHEDULE"
						,"CONTROL"
						,"NOPARAM"
						,"XXXXXXX"
			} ;
		for (int i=0; i < keys.length; i++)
		{
			MSG(keys[i], ("SAMPLE TEST MESSAGE Key:" + keys[i] + " point:" + i)) ;
		}
	}
}
//#CM642592
//end of class


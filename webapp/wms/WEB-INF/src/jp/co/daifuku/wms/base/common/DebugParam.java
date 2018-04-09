// $Id: DebugParam.java,v 1.2 2006/11/07 05:59:19 suresh Exp $
package jp.co.daifuku.wms.base.common ;

//#CM642593
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Locale;
import java.util.ResourceBundle;

//#CM642594
/**
 * The class to acquire the parameter of WareNavi system debugging Message output condition from the resource. 
 * The default resource name is <code>DebugParam</code>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:59:19 $
 * @author  $Author: suresh $
 */
public class DebugParam extends Object
{
	//#CM642595
	/**
	 * Default resource
	 */
	public static final String DEFAULT_RESOURCE = "DebugParam" ;

	//#CM642596
	// Class method --------------------------------------------------
	//#CM642597
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 05:59:19 $") ;
	}

	//#CM642598
	// Constructors --------------------------------------------------
	//#CM642599
	// No Constructors! all of method is static.

	//#CM642600
	// Public methods ------------------------------------------------
	//#CM642601
	/**
	 * Acquire the content of the parameter from the key. 
	 * @param key  Key to acquire parameter
	 * @return   Expression of character string of parameter
	 */
	public static String getParam(String key)
	{
		ResourceBundle rb = getBundle(DEFAULT_RESOURCE, Locale.getDefault()) ;
		return (rb.getString(key)) ;
	}

	//#CM642602
	// Package methods -----------------------------------------------

	//#CM642603
	// Protected methods ---------------------------------------------

	//#CM642604
	// Private methods -----------------------------------------------
	//#CM642605
	/**
	 * Acquire Resource bundle.
	 * @param res  Resource
	 * @param locale LocaleObject
	 * @return Resource Bundle
	 */
	private static ResourceBundle getBundle(String res, Locale locale)
	{
		return (ResourceBundle.getBundle(res, locale)) ;
	}

	//#CM642606
	// debug methods -----------------------------------------------
	//#CM642607
	/**
	 * Main Method.<BR>
	 */
	public static void main(String[] argv)
	{
		String[] keys = { "HANDLER"
						,"SCHEDULE"
						,"CONTROL"
			} ;
		for (int i=0; i < keys.length; i++)
		{
			try {
				System.out.println(keys[i] + "[" + getParam(keys[i]) + "]") ;
			}

				catch (Exception e)
			{
				e.printStackTrace() ;
				System.out.println("Please check the following file by connection at the time of error generating") ;
				System.out.println("File Name[C:/daifuku/wms/data/ini/DebugParam.properties]") ;
			}
		}
	}
}
//#CM642608
//end of class


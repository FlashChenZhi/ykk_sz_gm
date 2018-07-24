// $Id: DispResourceFile.java,v 1.2 2006/11/07 05:50:32 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643007
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Locale;
import java.util.ResourceBundle;

//#CM643008
/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 *  The class to acquire label Character string displayed in Component from DispResource.
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:50:32 $
 * @author  $Author: suresh $
 */
public class DispResourceFile
{
	//#CM643009
	/**
	 * Default resource
	 */
	public static final String DISP_RESOURCE = "DispResource" ;

	//#CM643010
	/**
	 * Acquire the content of the parameter from the key in the Character string expression. 
	 * @param key  Key to acquire parameter
	 * @return   Expression of character string of parameter
	 */
	public static String getText(String key)
	{
		try
		{
			ResourceBundle rb = ResourceBundle.getBundle(DISP_RESOURCE, Locale.getDefault());
			if (rb != null)
			{
				return rb.getString(key);
			}
		}
		catch(Exception e)
		{
			return key;
		}
		return key;
	}
}

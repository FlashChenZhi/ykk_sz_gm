// $Id: MessageResourceFile.java,v 1.2 2006/11/07 05:55:22 suresh Exp $
package jp.co.daifuku.wms.base.common.tool.logViewer;

//#CM643254
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Locale;
import java.util.ResourceBundle;

//#CM643255
/**
 * Designer : T.Konishi <BR>
 * Maker : T.Konishi  <BR>
 * <BR>
 *  The class to acquire label Character string displayed in Component from MessageResource. 
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 05:55:22 $
 * @author  $Author: suresh $
 */
public class MessageResourceFile
{
	//#CM643256
	/**
	 * Default resource
	 */
	public static final String MESSAGE_RESOURCE = "MessageResource" ;

	//#CM643257
	/**
	 * Acquire the content of the parameter from the key in the Character string expression. 
	 * @param key  Key to acquire parameter
	 * @return   Expression of character string of parameter
	 */
	public static String getText(String key)
	{
		try
		{
			ResourceBundle rb = ResourceBundle.getBundle(MESSAGE_RESOURCE, Locale.getDefault());
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

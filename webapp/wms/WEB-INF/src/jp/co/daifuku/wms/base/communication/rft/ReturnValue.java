// $Id: ReturnValue.java,v 1.2 2006/11/07 06:48:19 suresh Exp $
package jp.co.daifuku.wms.base.communication.rft;

//#CM643949
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM643950
/**
 * The class which maintains the variable when normal and making an error. 
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:48:19 $
 * @author $Author: suresh $
 */
public interface ReturnValue
{
	//#CM643951
	// Class fields --------------------------------------------------
	//#CM643952
	/**
	 * Error
	 */
	public static final int RET_NG = -1;

	//#CM643953
	/**
	 * Normal
	 */
	public static final int RET_OK = 0;

}
//#CM643954
//end of interface

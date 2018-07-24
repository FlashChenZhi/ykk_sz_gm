// $Id: WMSConstants.java,v 1.2 2006/11/07 06:02:31 suresh Exp $
//#CM643455
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.common;

import jp.co.daifuku.Constants;
import jp.co.daifuku.common.MessageResource;

//#CM643456
/**
 * The constant definition interface for WMS. 
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/06/26</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:02:31 $
 * @author  $Author: suresh $
 */
public interface WMSConstants extends Constants
{

	//#CM643457
	// Class fields --------------------------------------------------
	
	//#CM643458
	/**
	 * Data source name used with WMS
	 */
	public static final String DATASOURCE_NAME = "wms";
	
	//#CM643459
	/**
	 * Date format passed to plan parameter used with WMS
	 */
	public static final String PARAM_DATE_FORMAT = "yyyyMMdd";

	//#CM643460
	/**
	 * Delimiter
	 */
	public static final String wDelim = MessageResource.DELIM;	
	//#CM643461
	// Class variables -----------------------------------------------

	//#CM643462
	// Class method --------------------------------------------------

	//#CM643463
	// Constructors --------------------------------------------------

	//#CM643464
	// Public methods ------------------------------------------------

	//#CM643465
	// Package methods -----------------------------------------------

	//#CM643466
	// Protected methods ---------------------------------------------

	//#CM643467
	// Private methods -----------------------------------------------

}
//#CM643468
//end of class

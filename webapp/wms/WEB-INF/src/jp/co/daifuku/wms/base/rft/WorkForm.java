// $Id: WorkForm.java,v 1.2 2006/11/14 06:09:23 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM702653
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM702654
/**
 * Define the value of acceptance flag with telegram of the RFT communication. 
 * The value not defined here uses the value of Completion Class. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:23 $
 * @author  $Author: suresh $
 */
public interface WorkForm
{
	//#CM702655
	// Class fields --------------------------------------------------
	//#CM702656
	/**
	 * Work form : Field which shows Case
	 */
	public static final String CASE = "1";

	//#CM702657
	/**
	 * Work form : Field which shows Case
	 */
	public static final String PIECE = "2";

	//#CM702658
	/**
	 * Work form : Field which shows Case
	 */
	public static final String NOTSPECIFIED = "3";

	//#CM702659
	/**
	 * Work form : Field which shows Case
	 */
	public static final String ALL = "0";
}
//#CM702660
//end of interface

// $Id: ItemForm.java,v 1.2 2006/11/14 06:09:09 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701844
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701845
/**
 * Define the value of Mode of packing in telegram of the RFT communication. 
 * 
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:09 $
 * @author  $Author: suresh $
 */
public interface ItemForm
{
	//#CM701846
	// Class fields --------------------------------------------------
	//#CM701847
	/**
	 * Mode of packing : Field which shows Case
	 */
	public static final String CASE = "1";

	//#CM701848
	/**
	 * Mode of packing : Field which shows Piece
	 */
	public static final String PIECE = "2";

	//#CM701849
	/**
	 * Mode of packing : Field which shows specified doing
	 */
	public static final String NOTSPECIFIED = "3";

	//#CM701850
	/**
	 * Mode of packing : Field which shows everything
	 */
	public static final String ALL = "0";
}
//#CM701851
//end of interface

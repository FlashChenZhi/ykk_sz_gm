//#CM41748
//$Id: ASInOutResult.java,v 1.2 2006/10/26 08:24:57 suresh Exp $
package jp.co.daifuku.wms.asrs.entity;

//#CM41749
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.InOutResult;

//#CM41750
/**
 * This class stores the operation result<BR>
 * used from ASInOutResultFinder,ASInOutResultHandler<BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- revision history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:24:57 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */
public class ASInOutResult
		extends InOutResult
{
	//#CM41751
	//------------------------------------------------------------
	//#CM41752
	// class variables (prefix '$')
	//#CM41753
	//------------------------------------------------------------
	//#CM41754
	//	private String	$classVar ;

	//#CM41755
	//------------------------------------------------------------
	//#CM41756
	// properties (prefix 'p_')
	//#CM41757
	//------------------------------------------------------------
	//#CM41758
	//	private String	p_Name ;

	//#CM41759
	//------------------------------------------------------------
	//#CM41760
	// instance variables (prefix '_')
	//#CM41761
	//------------------------------------------------------------
	//#CM41762
	//	private String	_instanceVar ;

	//#CM41763
	//------------------------------------------------------------
	//#CM41764
	// constructors
	//#CM41765
	//------------------------------------------------------------

	//#CM41766
	/**
	 * prepare column name list and generate instance
	 */
	public ASInOutResult()
	{
		super() ;
	}

	//#CM41767
	/**
	 * set to storage/picking qty
	 * @param arg storage/picking qty to be set
	 */
	public void setInOutQuantity(long arg)
	{
		setValue(INOUTQUANTITY, new Long(arg));
	}

	//#CM41768
	/**
	 * fetch storage/picking qty
	 * @return storage/picking qty
	 */
	public long getSumInOutQuantity()
	{
		return getBigDecimal(InOutResult.INOUTQUANTITY).longValue();
	}

	//#CM41769
	/**
	 * @see dbhandler.AbstractEntity#getTablename()
	 */

	//#CM41770
	//------------------------------------------------------------
	//#CM41771
	// package methods
	//#CM41772
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM41773
	//------------------------------------------------------------

	//#CM41774
	//------------------------------------------------------------
	//#CM41775
	// protected methods
	//#CM41776
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM41777
	//------------------------------------------------------------

	//#CM41778
	//------------------------------------------------------------
	//#CM41779
	// private methods
	//#CM41780
	//------------------------------------------------------------

	//#CM41781
	//------------------------------------------------------------
	//#CM41782
	// utility methods
	//#CM41783
	//------------------------------------------------------------
	//#CM41784
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASInOutResult.java,v 1.2 2006/10/26 08:24:57 suresh Exp $" ;
	}
}

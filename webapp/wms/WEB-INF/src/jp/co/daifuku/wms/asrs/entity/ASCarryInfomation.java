//#CM41734
//$Id: ASCarryInfomation.java,v 1.2 2006/10/26 08:25:16 suresh Exp $
package jp.co.daifuku.wms.asrs.entity;

//#CM41735
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM41736
/**
 * Entity class to store stock info and work info n CarryInformation
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 * <!-- revision history -->
 * <tr><td nowrap>2006/01/27</td><td nowrap>Y.Okamura</td><td>Class created.</td></tr>
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:25:16 $
 * @author  Y.Okamura
 * @author  Last commit: $Author: suresh $
 */
public class ASCarryInfomation extends CarryInformation
{
	//#CM41737
	// Class variables -----------------------------------------------
	//#CM41738
	/**
	 * stock info instance
	 */
	private Stock wStock = null;

	//#CM41739
	/**
	 * work info instance
	 */
	private AsWorkingInformation wWinfo = null;

	//#CM41740
	// Class method --------------------------------------------------
	
	//#CM41741
	// Constructors --------------------------------------------------
	//#CM41742
	/**
	 * constructor
	 */
	public ASCarryInfomation()
	{
		super() ;
		setInitCreateColumn();
		wStock = new Stock();
		wWinfo = new AsWorkingInformation();		
	}
	
	//#CM41743
	// Public methods ------------------------------------------------
	//#CM41744
	/**
	 * set value to stock info
	 * @param stk stock info to be set
	 */
	public void setStock(Stock stk)
	{
		wStock = stk;
	}

	//#CM41745
	/**
	 * fetch stock info
	 * @return stock info
	 */
	public Stock getStock()
	{
		return wStock;
	}

	//#CM41746
	/**
	 * set value to work info
	 * @param wi work into to be set
	 */
	public void setWorkInfo(AsWorkingInformation wi)
	{
		wWinfo = wi;
	}

	//#CM41747
	/**
	 * fetch work info
	 * @return work info
	 */
	public AsWorkingInformation getWorkInfo()
	{
		return wWinfo;
	}
}

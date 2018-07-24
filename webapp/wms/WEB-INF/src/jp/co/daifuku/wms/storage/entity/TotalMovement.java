package jp.co.daifuku.wms.storage.entity;

//#CM574488
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.entity.Movement;

//#CM574489
/**
 * The class to manage movement work information by the stock movement processing. <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/10</TD><TD>Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:59:02 $
 * @author  $Author: suresh $
 */
public class TotalMovement extends Movement
{
	//#CM574490
	/**
	 * Actual Total qty
	 */
	protected long wtotalResultQty = 0;


	//#CM574491
	/**
	 * Acquire Actual Total qty. 
	 * @return Actual Total qty
	 */
	public long getTotalResultQty()
	{
		return wtotalResultQty;
	}
	//#CM574492
	/**
	 * Set Actual Total qty. 
	 * @param totalResultQty long Actual Total qty to be set.
	 */
	public void setTotalResultQty(long totalResultQty)
	{
		this.wtotalResultQty = totalResultQty;
	}
}

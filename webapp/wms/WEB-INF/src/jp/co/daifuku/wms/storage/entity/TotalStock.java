package jp.co.daifuku.wms.storage.entity;

//#CM574493
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.entity.Stock;

//#CM574494
/**
 * The class to manage the inventory information by the inventory processing. <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/10</TD><TD>Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:59:02 $
 * @author  $Author: suresh $
 */
public class TotalStock extends Stock
{
	//#CM574495
	/**
	 * Total Stock quantity
	 */
	protected long wtotalStockQty = 0;

	//#CM574496
	/**
	 * Total Allocated qty
	 */
	protected long wtotalAllocationQty = 0;


	//#CM574497
	/**
	 * Acquire Total Stock quantity. 
	 * @return Total Stock quantity
	 */
	public long getTotalStockQty()
	{
		return wtotalStockQty;
	}
	//#CM574498
	/**
	 * Set Total Stock quantity. 
	 * @param totalStockQty long Total Stock quantity to be set
	 */
	public void setTotalStockQty(long totalStockQty)
	{
		this.wtotalStockQty = totalStockQty;
	}

	//#CM574499
	/**
	 * Acquire Total Allocated qty. 
	 * @return Total Allocated qty
	 */
	public long getTotalAllocationQty()
	{
		return wtotalAllocationQty;
	}
	//#CM574500
	/**
	 * Set Total Allocated qty. 
	 * @param totalAllocationQty long Total Allocated qty to be set
	 */
	public void setTotalAllocationQty(long totalAllocationQty)
	{
		this.wtotalAllocationQty = totalAllocationQty;
	}
}

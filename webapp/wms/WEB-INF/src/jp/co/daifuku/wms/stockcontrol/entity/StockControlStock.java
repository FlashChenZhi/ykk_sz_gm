package jp.co.daifuku.wms.stockcontrol.entity;

import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM8949
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM8950
/**
 * This is an entity class that preserves Stock Info of search results.<BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Revision history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/09/22 06:20:24 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */

public class StockControlStock extends Stock
{
	//#CM8951
	/** Definition of column (STOCK_CASE_QTY) */



	public static final FieldName STOCK_CASE_QTY = new FieldName("STOCK_CASE_QTY");

	//#CM8952
	/** Definition of column (STOCK_PIECE_QTY) */



	public static final FieldName STOCK_PIECE_QTY = new FieldName("STOCK_PIECE_QTY");

	//#CM8953
	/** Definition of column (ALLOCATION_CASE_QTY) */



	public static final FieldName ALLOCATION_CASE_QTY = new FieldName("ALLOCATION_CASE_QTY");

	//#CM8954
	/** Definition of column (ALLOCATION_PIECE_QTY) */



	public static final FieldName ALLOCATION_PIECE_QTY = new FieldName("ALLOCATION_PIECE_QTY");

	//#CM8955
	/**
	 * Set a value for Stock Case Qty.
	 * @param arg Stock Case Qty to be set
	 */
	public void setStockCaseQty(long arg)
	{
		setValue(STOCK_CASE_QTY, new Long(arg));
	}

	//#CM8956
	/**
	 * Obtain Stock Case Qty.
	 * @return Stock Case Qty
	 */
	public long getStockCaseQty()
	{
		return getBigDecimal(StockControlStock.STOCK_CASE_QTY).longValue();
	}

	//#CM8957
	/**
	 * Set a value for Stock Piece Qty.
	 * @param arg Piece Qty to be set
	 */
	public void setStockPieceQty(long arg)
	{
		setValue(STOCK_PIECE_QTY, new Long(arg));
	}

	//#CM8958
	/**
	 * Obtain Stock Case Qty.
	 * @return Stock Piece Qty
	 */
	public long getStockPieceQty()
	{
		return getBigDecimal(StockControlStock.STOCK_PIECE_QTY).longValue();
	}

	//#CM8959
	/**
	 * Set a value for Allocatable Case Qty.
	 * @param arg Allocatable Case Qty to be set
	 */
	public void setAllocationCaseQty(long arg)
	{
		setValue(ALLOCATION_CASE_QTY, new Long(arg));
	}
	
	//#CM8960
	/**
	 * Obtain allocatable Case Qty.
	 * @return Allocatable Case Qty
	 */
	public long getAllocationCaseQty()
	{
		return getBigDecimal(StockControlStock.ALLOCATION_CASE_QTY).longValue();
	}

	//#CM8961
	/**
	 * Set a value for Allocatable Piece Qty.
	 * @param arg Allocatable Piece Qty to be set
	 */
	public void setAllocationPieceQty(long arg)
	{
		setValue(ALLOCATION_PIECE_QTY, new Long(arg));
	}
	
	//#CM8962
	/**
	 * Obtain allocatable Piece Qty.
	 * @return Allocatable Piece Qty
	 */
	public long getAllocationPieceQty()
	{
		return getBigDecimal(StockControlStock.ALLOCATION_PIECE_QTY).longValue();
	}

}

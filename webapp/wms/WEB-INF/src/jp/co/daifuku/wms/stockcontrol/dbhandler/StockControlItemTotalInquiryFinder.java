package jp.co.daifuku.wms.stockcontrol.dbhandler;

//#CM3954
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.stockcontrol.entity.StockControlStock;

//#CM3955
/**
 * Designer : Y.Okamura<BR>
 * Maker 	: Y.Okamura<BR> 
 * <BR>
 * This is a class to search a DmStock table from database and map it on the InventoryCheck instance.
 * Use this class from a list box.
 * <BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/04/11</TD><TD>Y.Osawa</TD><TD>New Creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/09/22 06:20:18 $
 * @author  $Author: suresh $
 */
public class StockControlItemTotalInquiryFinder extends StockFinder
{	
	//#CM3956
	// Class filelds -----------------------------------------------

	//#CM3957
	// Class method --------------------------------------------------
	//#CM3958
	/**
	 * Return version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/09/22 06:20:18 $");
	}

	//#CM3959
	// Constructors --------------------------------------------------
	//#CM3960
	/**
	 * Designate <code>Connection</code> for database connection and generate an instance.
	 * @param conn For database connection Connection
	 */
	public StockControlItemTotalInquiryFinder(Connection conn)
	{
		super(conn) ;    
	}

	//#CM3961
	/**
	 * @see dbhandler.AbstractDBFinder#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new StockControlStock());
	}

	//#CM3962
	// Public methods ------------------------------------------------
	//#CM3963
	/**
	 * Search and obtain DmStock.<BR>
	 * Use this from a list box for Stock Inquiry by Item.<BR>
	 * As for the corresponding Consignor Cord and Item Cord, obtain the last Consignor Name, Item Name, Packed Qty per Case, and Packed Qty per Bundle.<BR>
	 * Calculate the respecive Case Qty and Piece Qty for each obtained data.<BR>
	 * 
	 * @param key Key for searching
	 * @return Count of search results
	 * @throws ReadWriteException Inform the exception generated in the database connection as it is.
	 */
	public int search(StockSearchKey key) throws ReadWriteException
	{
		Object[] fmtObj = new Object[3] ;
		Object[] cntObj = new Object[1];
		Object[] calculationObj = new Object[1] ;
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			//#CM3964
			// SQL for main
			String fmtCountSQL = "SELECT COUNT(*) COUNT FROM (SELECT COUNT(*) COUNT FROM DMSTOCK {0}" + 
					"GROUP BY DMSTOCK.CONSIGNOR_CODE, DMSTOCK.ITEM_CODE)";
			String fmtSQL = "SELECT DMSTOCK.CONSIGNOR_CODE, DMSTOCK.ITEM_CODE, MAX(DMSTOCK.ITEM_NAME1) ITEM_NAME1, " +
					"MAX(DMSTOCK.ENTERING_QTY) ENTERING_QTY, MAX(DMSTOCK.BUNDLE_ENTERING_QTY) BUNDLE_ENTERING_QTY, " +
					"SUM(DMSTOCK.STOCK_CASE_QTY) STOCK_CASE_QTY, SUM(DMSTOCK.STOCK_PIECE_QTY) STOCK_PIECE_QTY, " +
					"SUM(DMSTOCK.ALLOCATION_CASE_QTY) ALLOCATION_CASE_QTY, SUM(DMSTOCK.ALLOCATION_PIECE_QTY) ALLOCATION_PIECE_QTY " +
					"FROM {0} DMSTOCK {1} GROUP BY DMSTOCK.CONSIGNOR_CODE, DMSTOCK.ITEM_CODE {2}";
			//#CM3965
			// SQL for calculation of Case Qty and Piece Qty
			String calculationSQL = "(SELECT DMSTOCK.CONSIGNOR_CODE, DMSTOCK.ITEM_CODE, DMSTOCK.ITEM_NAME1, " +
					"DMSTOCK.ENTERING_QTY, DMSTOCK.BUNDLE_ENTERING_QTY, DMSTOCK.CASE_PIECE_FLAG, DMSTOCK.LAST_UPDATE_DATE, " +
					"DECODE(DMSTOCK.ENTERING_QTY, 0, 0, DECODE(DMSTOCK.CASE_PIECE_FLAG, 2, 0, TRUNC(DMSTOCK.STOCK_QTY / DMSTOCK.ENTERING_QTY))) STOCK_CASE_QTY, " +
					"DECODE(DMSTOCK.ENTERING_QTY, 0, DMSTOCK.STOCK_QTY, DECODE(DMSTOCK.CASE_PIECE_FLAG, 2, DMSTOCK.STOCK_QTY, MOD(DMSTOCK.STOCK_QTY, DMSTOCK.ENTERING_QTY))) STOCK_PIECE_QTY, " +
					"DECODE(DMSTOCK.ENTERING_QTY, 0, 0, DECODE(DMSTOCK.CASE_PIECE_FLAG, 2, 0, TRUNC(DMSTOCK.ALLOCATION_QTY / DMSTOCK.ENTERING_QTY))) ALLOCATION_CASE_QTY, " +
					"DECODE(DMSTOCK.ENTERING_QTY, 0, DMSTOCK.ALLOCATION_QTY, DECODE(DMSTOCK.CASE_PIECE_FLAG, 2, DMSTOCK.ALLOCATION_QTY, MOD(DMSTOCK.ALLOCATION_QTY, DMSTOCK.ENTERING_QTY))) ALLOCATION_PIECE_QTY " +
					"FROM DMSTOCK {0})";
		  
			//#CM3966
			// Compile Search condition.
			if (key.getReferenceCondition() != null)
			{
				cntObj[0] = " WHERE " + key.getReferenceCondition() +
					" AND DMSTOCK.STATUS_FLAG = " + Stock.STOCK_STATUSFLAG_OCCUPIED + " AND DMSTOCK.STOCK_QTY > 0";
				calculationObj[0] = " WHERE " + key.getReferenceCondition() + 
					" AND DMSTOCK.STATUS_FLAG = " + Stock.STOCK_STATUSFLAG_OCCUPIED + " AND DMSTOCK.STOCK_QTY > 0";
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}

			//#CM3967
			// Compile Search Sub-inquiry table.
			fmtObj[0] = SimpleFormat.format(calculationSQL, calculationObj) ;

			//#CM3968
			// Compile Loading order.				
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = "ORDER BY " + key.getSortCondition();
			}
			
			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", "StockControlItemTotalInquiryFinder Finder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM3969
			// Execute search only when the Count is equal to MAXDISP or less.
			if ( count <= MAXDISP )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "executeQuery Finder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
			}
			else
			{
				p_ResultSet = null;
			}
		}
		catch(SQLException e)
		{
			//#CM3970
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), this.getClass().getName() ) ;
			//#CM3971
			// Throw ReadWriteException here with an error message.
			throw (new ReadWriteException("6025038" + wDelim + "DmStock")) ;
		}

		return count;
	}

	//#CM3972
	// Package methods -----------------------------------------------

	//#CM3973
	// Protected methods ---------------------------------------------

	//#CM3974
	// Private methods -----------------------------------------------
}
//#CM3975
//end of class

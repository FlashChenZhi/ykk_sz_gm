//#CM33996
//$Id: ASPaletteHandler.java,v 1.2 2006/10/30 07:13:08 suresh Exp $

package jp.co.daifuku.wms.asrs.dbhandler ;

//#CM33997
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;

//#CM33998
/**
 * This class operates combined Palette, Stock
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/02/16</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:13:08 $
 * @author  $Author: suresh $
 */
public class ASPaletteHandler extends PaletteHandler
{
	//#CM33999
	// Class fields --------------------------------------------------

	//#CM34000
	// Class variables -----------------------------------------------

	//#CM34001
	// Class method --------------------------------------------------
	//#CM34002
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:13:08 $") ;
	}

	//#CM34003
	// Constructors --------------------------------------------------
	//#CM34004
	/**
	 * Specify <code>Connection</code> object for database connection and generate instance
	 * @param conn connection object for database connection
	 */
	public ASPaletteHandler(Connection conn)
	{
		super(conn);
		setConnection(conn) ;
	}

	//#CM34005
	// Public methods ------------------------------------------------
	//#CM34006
	/**
	 * Lock the palette that refers to the location no. in the paramter and the stock info that relates to the palette
	 * <BR>
	 * Throw LockTimeOutException if another process has locked already and current lock attempt fails
	 * 
	 * @param location Location no. for lock target
	 * @return whether locked or not
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws LockTimeOutException Throw LockTimeOutException if attempt to lock fails
	 */
	public boolean lockNowait(String location)
			throws ReadWriteException, LockTimeOutException
	{
		Statement stmt = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			setTableName("PALETTE, DMSTOCK");
			
			stmt = getConnection().createStatement() ;
			String fmtSQL = "SELECT * FROM " + getTableName() 
							+ " WHERE PALETTE.PALETTEID = DMSTOCK.PALETTEID "
							+ " AND PALETTE.CURRENTSTATIONNUMBER = '" + location + "' " 
							+ " FOR UPDATE NOWAIT" ;

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " LOCK SQL[" + sqlstring + "]") ;
			return stmt.execute(sqlstring) ;
		}
		catch (SQLException e)
		{
			//#CM34007
			// since oracle specific error code,
			//#CM34008
			// alter this while using a different database than oracle
			if (e.getErrorCode() == 54)
			{
				throw new LockTimeOutException("SELECT NOWAIT TABLE " + getTableName() );
			}
			//#CM34009
			// 6006002 = database error occurred{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName() )) ;
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM34010
				// 6006002 = database error occurred{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				throw (new ReadWriteException("6006002" + wDelim + getTableName() )) ;
			}
		}
	}

	//#CM34011
	// Package methods -----------------------------------------------

	//#CM34012
	// Protected methods ---------------------------------------------

	//#CM34013
	// Private methods -----------------------------------------------
}
//#CM34014
//end of class


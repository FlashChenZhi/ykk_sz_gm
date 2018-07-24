//#CM33806
//$Id: ASCarryInformationReportFinder.java,v 1.2 2006/10/30 07:09:26 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

//#CM33807
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.entity.ASCarryInfomation;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationReportFinder;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM33808
/**
 * This class operates the AS/RS carry info (CarryInfo)<BR>
 * Describe SQL that can't be used with normal ReportFinder
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/27</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:09:26 $
 * @author  $Author: suresh $
 */
public class ASCarryInformationReportFinder extends CarryInformationReportFinder
{
	//#CM33809
	// Class feilds ------------------------------------------------
	
	//#CM33810
	// Class variables -----------------------------------------------

	//#CM33811
	// Class method --------------------------------------------------
	//#CM33812
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:09:26 $");
	}

	//#CM33813
	// Constructors --------------------------------------------------
	//#CM33814
	/**
	 * Specify <code>Connection</code> object for database connection and generate instance
	 * @param conn connection object for database connection
	 */
	public ASCarryInformationReportFinder(Connection conn)
	{
		super(conn);
	}
	
	//#CM33815
	/**
	 * @see dbhandler.AbstractDBFinder#createEntity()
	 */	
	protected ASCarryInfomation createASEntity()
	{
		return (new ASCarryInfomation()) ;
	}

	//#CM33816
	// Public methods ------------------------------------------------
	//#CM33817
	/**
	 * Search database and fetch results<br>
	 * Fetch carry data, work info that relates to it, stock info<br>
	 * and fetch from stock info if work info that can be divided from stock does not exist <br>
	 * @param carryKey conveyance key to search
	 * @return search result count
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int search(String carryKey) throws ReadWriteException
	{
		close();
		open();

		Object[] fmtObj = new Object[2];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			setTableName("carryinfo, dnworkinfo, dmstock");
			
			String fmtCountSQL = "SELECT COUNT(*) COUNT " 
								+ " {0}";

			String fmtSQL = "SELECT * " 
								+ " {0} {1}";

			fmtObj[0] = "FROM CARRYINFO, DMSTOCK, "
						+ " ("
						+ "  SELECT STOCK_ID, CONSIGNOR_CODE as workConsig, PLAN_ENABLE_QTY, USE_BY_DATE as WORK_USE_BY_DATE "
						+ "  FROM DNWORKINFO "
						+ "  WHERE DNWORKINFO.SYSTEM_CONN_KEY = '" + carryKey + "' "
						+ "  AND DNWORKINFO.STATUS_FLAG != '" + WorkingInformation.STATUS_FLAG_DELETE + "' "
						+ "  AND DNWORKINFO.STATUS_FLAG != '" + WorkingInformation.STATUS_FLAG_COMPLETION + "' "
						+ " ) DNWORKINFO "
						+ " WHERE CARRYINFO.CARRYKEY = '" + carryKey + "' "
						+ " AND CARRYINFO.PALETTEID = DMSTOCK.PALETTEID "
						+ " AND DNWORKINFO.STOCK_ID(+) = DMSTOCK.STOCK_ID ";
			//#CM33818
			// Fetch in this order so that allocated ones are fetched first
			fmtObj[1] = "ORDER BY dnworkinfo.workConsig,"
						+ " dmstock.consignor_code,"
						+ " dmstock.item_code,"
						+ " dmstock.case_piece_flag,"
						+ " dmstock.use_by_date ";
			
			String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			//#CM33819
			//execute search if the count is equal to 1 or more
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM33820
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}
	
	//#CM33821
	/**
	 * search database and fetch results<br>
	 * Carry data,work info that relates to it,fetch stock info<br>
	 * Use StockID rather than palette id that relates to stock<br>
	 * @param carryKey conveyance key to search
	 * @return search result count
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int searchNoParette(String carryKey) throws ReadWriteException
	{
		close();
		open();

		Object[] fmtObj = new Object[2];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			setTableName("carryinfo, dnworkinfo, dmstock");
			
			String fmtCountSQL = "SELECT COUNT(*) COUNT " 
								+ " {0}";

			String fmtSQL = "SELECT * " 
								+ " {0} {1}";

			fmtObj[0] = "FROM CARRYINFO, DMSTOCK, "
						+ " ("
						+ "  SELECT STOCK_ID, CONSIGNOR_CODE as workConsig, PLAN_ENABLE_QTY, USE_BY_DATE as WORK_USE_BY_DATE "
						+ "  FROM DNWORKINFO "
						+ "  WHERE DNWORKINFO.SYSTEM_CONN_KEY = '" + carryKey + "' "
						+ "  AND DNWORKINFO.STATUS_FLAG != '" + WorkingInformation.STATUS_FLAG_DELETE + "' "
						+ "  AND DNWORKINFO.STATUS_FLAG != '" + WorkingInformation.STATUS_FLAG_COMPLETION + "' "
						+ " ) DNWORKINFO "
						+ " WHERE CARRYINFO.CARRYKEY = '" + carryKey + "' "
						+ " AND DNWORKINFO.STOCK_ID = DMSTOCK.STOCK_ID ";
			//#CM33822
			// Fetch in this order so that allocated ones are fetched first
			fmtObj[1] = "ORDER BY dnworkinfo.workConsig,"
						+ " dmstock.consignor_code,"
						+ " dmstock.item_code,"
						+ " dmstock.case_piece_flag,"
						+ " dmstock.use_by_date ";
			
			String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			//#CM33823
			//execute search if the count is equal to 1 or more
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM33824
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}
	
	//#CM33825
	/**
	 * map the result set
	 *
	 * @param Search result count
	 * @return Entity array
	 * @throws ReadWriteException Throw any database exception as it is
	 * @return Entity[]
	 */
	public ASCarryInfomation[] getASEntities(int count) throws ReadWriteException
	{
		Vector entityList = new Vector() ;
		ASCarryInfomation[] entityArr = null ;
		ASCarryInfomation tmpEntity = createASEntity() ;
		try
		{			
			//#CM33826
			// count the number read this time
			int readCount = 0;
			//#CM33827
			// Flag to decide whether to close the ResultSet or not. Close after the last record is read
			boolean endFlag = true;
			
			while ( p_ResultSet.next() )
			{			
				for (int i = 1; i <= p_ResultSet.getMetaData().getColumnCount(); i++)
				{
					String colname = p_ResultSet.getMetaData().getColumnName(i) ;
					FieldName field = new FieldName(colname) ;

					//#CM33828
					// Fetch milliseconds
					Object value = p_ResultSet.getObject(i) ;
					if (value instanceof java.util.Date)
					{
						value = p_ResultSet.getTimestamp(i) ;
					}
					
					tmpEntity.setValue(field, value) ;
					tmpEntity.getStock().setValue(field, value);
					tmpEntity.getWorkInfo().setValue(field, value);
				}
				entityList.addElement(tmpEntity) ;
	
				tmpEntity = createASEntity() ;
				
				//#CM33829
				// Pass a while after reading the qty
				readCount++;
				if ( count <= readCount )
				{
					endFlag = false;
					break;
				}
			}
			//#CM33830
			// Close after reading the last record
			if ( endFlag )
			{
				isNextFlag = false ;
				close();
			}
	
			//#CM33831
			// Cast with respect to every table Entity[]
			entityArr = (ASCarryInfomation[])java.lang.reflect.Array.newInstance(
					tmpEntity.getClass(), entityList.size()) ;
			entityList.copyInto(entityArr) ;

		}
		catch (SQLException e)
		{
			//#CM33832
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM33833
			//Here, throw ReadWriteException with error message attached. 
			//#CM33834
			//6006039 = Failed to search for {0}.
			throw (new ReadWriteException("6006039" + wDelim + getTableName())) ;
		}
		
		return entityArr ;
	}

	//#CM33835
	// Package methods -----------------------------------------------

	//#CM33836
	// Protected methods ---------------------------------------------

	//#CM33837
	// Private methods -----------------------------------------------
}
//#CM33838
// end of class

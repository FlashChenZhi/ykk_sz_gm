//#CM34308
/*
 * Created date: 2005/11/08
 *
 * To update the template that inserts the generated comment
 * Window > setting > Java > generate code > code and comment
 */
package jp.co.daifuku.wms.asrs.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM34309
/**
 * This class operates the AS/RS warehouse table (WareHouse)<BR>
 * Describe SQL that can't be used with normal handler
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/08</TD><TD>T.Nakajima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:57:45 $
 * @author  $Author: suresh $
 */
public class ASWareHouseHandler extends WareHouseHandler
{
	//#CM34310
	// Class feilds ------------------------------------------------
	
	//#CM34311
	// Class variables -----------------------------------------------

	//#CM34312
	// Class method --------------------------------------------------
	//#CM34313
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:57:45 $");
	}

	//#CM34314
	// Constructors --------------------------------------------------
	//#CM34315
	/**
	 * Specify <code>Connection</code> object for database connection and generate instance
	 * @param conn connection object for database connection
	 */
	public ASWareHouseHandler(Connection conn)
	{
		super(conn);
	}
	
	//#CM34316
	// Public methods ------------------------------------------------
	//#CM34317
	/**
	 * Search and get data of location to location move.
	 * @param p_TerminalNo  :terminal no.
	 * @param p_type        :warehouse type
	 * @return CarryInformation instance searched
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public WareHouse[] getWareHouseNo(String p_TerminalNo, int p_type) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		WareHouse[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT WAREHOUSE.STATIONNUMBER, "
							+ "        WAREHOUSE.WAREHOUSENUMBER, "
							+ "        WAREHOUSE.WAREHOUSENAME "
							+ " FROM   WAREHOUSE "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(WHSTATIONNUMBER) "
							+ "     FROM   STATION "
							+ "     WHERE  STATIONNUMBER "
							+ " IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ) ";
			if(p_type != WareHouse.ALL_WAREHOUSE)
			{
				fmtSQL = fmtSQL + " AND WAREHOUSETYPE = " + p_type;
			}
			fmtSQL = fmtSQL + " ORDER BY WAREHOUSE.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				WareHouse wEntity = new WareHouse();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setWareHouseNumber(rset.getInt("WAREHOUSENUMBER"));
				wEntity.setWareHouseName(DBFormat.replace(rset.getString("WAREHOUSENAME")));
				
				vec.addElement(wEntity);
			}
			rData = new WareHouse[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34318
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsWareHouseHandler" ) ;
			//#CM34319
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "WareHouse")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34320
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsWareHouseHandler" ) ;
				//#CM34321
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "WareHouse")) ;
			}
		}
		return rData;
	}

	//#CM34322
	// Package methods -----------------------------------------------

	//#CM34323
	// Protected methods ---------------------------------------------

	//#CM34324
	// Private methods -----------------------------------------------
}
//#CM34325
//end of class


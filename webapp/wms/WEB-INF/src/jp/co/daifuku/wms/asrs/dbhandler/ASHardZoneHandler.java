//#CM33839
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
import jp.co.daifuku.wms.base.entity.HardZone;

//#CM33840
/**
 * This class operates the AS/RS Hard Zone table (Hard Zone)<BR>
 * Describe SQL that can't be used with normal handler
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/08</TD><TD>T.Nakajima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:09:25 $
 * @author  $Author: suresh $
 */
public class ASHardZoneHandler extends WareHouseHandler
{
	//#CM33841
	// Class feilds ------------------------------------------------
	
	//#CM33842
	// Class variables -----------------------------------------------

	//#CM33843
	// Class method --------------------------------------------------
	//#CM33844
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:09:25 $");
	}

	//#CM33845
	// Constructors --------------------------------------------------
	//#CM33846
	/**
	 * Specify <code>Connection</code> object for database connection and generate instance
	 * @param conn connection object for database connection
	 */
	public ASHardZoneHandler(Connection conn)
	{
		super(conn);
	}
	
	//#CM33847
	// Public methods ------------------------------------------------
	//#CM33848
	/**
	 * Search and get data of location to location move.
	 * @param p_TerminalNo :terminal no. subject to search
	 * @return CarryInformation instance searched
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public HardZone[] getHardZoneNo(String p_TerminalNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		HardZone[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT HARDZONE.WHSTATIONNUMBER, "
							+ "        HARDZONE.HARDZONEID "
							+ " FROM   HARDZONE "
							+ " WHERE  HARDZONE.WHSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(WHSTATIONNUMBER) "
							+ "     FROM   STATION "
							+ "     WHERE  STATIONNUMBER "
							+ " IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ) ";
			fmtSQL = fmtSQL + " ORDER BY HARDZONE.WHSTATIONNUMBER, HARDZONE.HARDZONEID ";
 
			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				HardZone wEntity = new HardZone();
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				wEntity.setHardZoneID(rset.getInt("HARDZONEID"));
				
				vec.addElement(wEntity);
			}
			rData = new HardZone[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM33849
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsHardZoneHandler" ) ;
			//#CM33850
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "HardZone")) ;
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
				//#CM33851
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsHardZoneHandler" ) ;
				//#CM33852
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "HardZone")) ;
			}
		}
		return rData;
	}


	//#CM33853
	// Package methods -----------------------------------------------

	//#CM33854
	// Protected methods ---------------------------------------------

	//#CM33855
	// Private methods -----------------------------------------------
}
//#CM33856
//end of class


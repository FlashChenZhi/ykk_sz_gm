//#CM33774
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
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM33775
/**
 * This class operates the AS/RS carry info (CarryInfo)<BR>
 * Describe SQL that can't be used with normal handler
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/08</TD><TD>T.Nakajima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:09:26 $
 * @author  $Author: suresh $
 */
public class ASCarryInformationHandler extends CarryInformationHandler
{
	//#CM33776
	// Class feilds ------------------------------------------------
	//#CM33777
	/**
	 * Number of retrieval data retrieved (default:2 data)
	 */
	protected static final int RETRIEVAL_DATA_COUNT = 2;
	
	//#CM33778
	// Class variables -----------------------------------------------

	//#CM33779
	// Class method --------------------------------------------------
	//#CM33780
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:09:26 $");
	}

	//#CM33781
	// Constructors --------------------------------------------------
	//#CM33782
	/**
	 * Specify <code>Connection</code> object for database connection and generate instance
	 * @param conn connection object for database connection
	 */
	public ASCarryInformationHandler(Connection conn)
	{
		super(conn);
	}
	
	//#CM33783
	// Public methods ------------------------------------------------
	//#CM33784
	/**
	 * Search and get data of location to location move.
	 * @return CarryInformation instance searched
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
	 */
	public CarryInformation[] getRackMoveInfoForUpdate() throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		CarryInformation[] carray  = null;

		try
		{

			stmt = getConnection().createStatement();

			//#CM33785
			// Search the CarryInformation list with location to location move. 
			//#CM33786
			// it is possible to read from both DoubleDeepRetrievalSender,AutomaticChangeSender
			//#CM33787
			// lock the side first read and avoid reduntant location movement instruction
			String fmtSQL = "SELECT CARRYINFO.CARRYKEY, CARRYINFO.PALETTEID "
						  + "FROM CARRYINFO, PALETTE "
						  + "WHERE CARRYINFO.PALETTEID = PALETTE.PALETTEID"
						  + " AND CARRYINFO.CARRYKIND = " + CarryInformation.CARRYKIND_RACK_TO_RACK
						  + " AND CARRYINFO.CMDSTATUS = " + CarryInformation.CMDSTATUS_START
						  + " ORDER BY CARRYINFO.CREATEDATE FOR UPDATE";
			rset = stmt.executeQuery(fmtSQL);

			//#CM33788
			// Generate the data for the number of retireval instruction generated, or loop until 
			// no data is left any more.
			
			//#CM33789
			// variable to store picking instruction data
			Vector vec = new Vector(RETRIEVAL_DATA_COUNT);
			
			//#CM33790
			// variable to distinct whether the same PaletteId exists or not
			Hashtable hs = new Hashtable();
			
			while ((vec.size() < RETRIEVAL_DATA_COUNT) && rset.next())
			{
				//#CM33791
				// Not generate more than just one CarryInformation instance which has the same pallet ID.
				Integer pid = new Integer(rset.getInt("PALETTEID"));
				if (hs.containsKey(pid))
				{
					continue;
				}
				else
				{
					CarryInformationSearchKey ckey = new CarryInformationSearchKey();
					ckey.setCarryKey(DBFormat.replace(rset.getString("CARRYKEY")));
					CarryInformation[] cis = (CarryInformation[])find(ckey);
					if (cis.length > 0) vec.addElement(cis[0]);
				}
				hs.put(pid, pid);
			}
			carray = new CarryInformation[vec.size()];
			vec.copyInto(carray);
		}
		catch(SQLException e)
		{
			//#CM33792
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "CarryInformationHandler" ) ;
			//#CM33793
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "CarryInfo")) ;
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
				//#CM33794
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "CarryInformationHandler" ) ;
				//#CM33795
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "CarryInfo")) ;
			}
		}		
		return carray;
	}

	//#CM33796
	/**
	 * Search the work qty of every controller
	 * @param pAgcNo Controller no.
	 * @return remaining work qty
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int remainderWorksCount(int pAgcNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		int wCount = 0;
		
		try
		{
			stmt = getConnection().createStatement();

			//#CM33797
			// Count the carry data of station that belongs to the specified controller
			String fmtSQL = "SELECT COUNT(DISTINCT CARRYINFO.CARRYKEY) COUNT "
						  + " FROM CARRYINFO, STATION "
						  + " WHERE STATION.CONTROLLERNUMBER = " + pAgcNo
						  + " AND ( CARRYINFO.SOURCESTATIONNUMBER = STATION.STATIONNUMBER "
						  + "     OR CARRYINFO.DESTSTATIONNUMBER = STATION.STATIONNUMBER ) ";

			rset = stmt.executeQuery(fmtSQL);

			if (rset != null)
			{
				while (rset.next())
					wCount = rset.getInt("COUNT") ;
			}
		}
		catch(SQLException e)
		{
			//#CM33798
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "CarryInformationHandler" ) ;
			//#CM33799
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "CarryInfo")) ;
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
				//#CM33800
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "CarryInformationHandler" ) ;
				//#CM33801
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "CarryInfo")) ;
			}
		}		
		return wCount;
	}

	//#CM33802
	// Package methods -----------------------------------------------

	//#CM33803
	// Protected methods ---------------------------------------------

	//#CM33804
	// Private methods -----------------------------------------------
}
//#CM33805
//end of class


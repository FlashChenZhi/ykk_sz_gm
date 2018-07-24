//#CM34015
//$Id: ASShelfHandler.java,v 1.2 2006/10/30 07:09:24 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

//#CM34016
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

//#CM34017
/**
 * This class is used to obtain <code>Shelf</code> class from, or to store one in database.
 * Concerning the acquisition of <code>Shelf</code> class, please use <code>StationFactory</code>.
 * As <code>getHandler</code> method is prepared in <code>Shelf</code>class, 
 * please use <code>getHandler</code> method if any support of Handler is necessary.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:09:24 $
 * @author  $Author: suresh $
 */
public class ASShelfHandler extends ShelfHandler
{
	//#CM34018
	// Class fields --------------------------------------------------

	//#CM34019
	// Class variables -----------------------------------------------
	//#CM34020
	/**
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 */
	private Connection wConn;

	//#CM34021
	/**
	 * Variable which controls the statement
	 */
	protected Statement wStatement = null ;

	//#CM34022
	// Constructors --------------------------------------------------
	//#CM34023
	/**
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 */
	public ASShelfHandler(Connection conn)
	{
		super(conn) ;
		wConn = conn;
	}

	//#CM34024
	// Class method --------------------------------------------------
	//#CM34025
	/**
	 * Return the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:09:24 $") ;
	}

	//#CM34026
	// Public methods ------------------------------------------------

	//#CM34027
	/**
	 * Search the empty location.
	 * Search in the Shlf table according to the specified aisle and zone, then return one instance of Shelf
	 * which is an empty pallet.
	 * Or it returns null if empty pallet was not found.
	 * Please remember, the caller must either commit of rollback the transaction as this method locks the Shelf table
	 * until thd transaction should complete.
	 * @param tAisle :aisle subject to empty location search
	 * @param tZone :zone subject to empty location search
	 * @return :Shelf instance searched
	 * @throws ReadWriteException :Notifies of the exception occured during the database processing.
	 * @throws InvalidDefineException ShelfSelecotor: Notifies if the selected direction of empty location search is not
	 * defined with interface.
	 */
	public Shelf findEmptyPalette(Aisle tAisle, Zone[] tZone) throws ReadWriteException, InvalidDefineException
	{
		ResultSet rset     = null;
		Shelf[] fndStation = null;
		String sqlstring   = null;

		try
		{
			String fmtSQL = "SELECT * FROM SHELF, PALETTE WHERE" +
							" SHELF.STATIONNUMBER = PALETTE.CURRENTSTATIONNUMBER " +
							" AND PALETTE.STATUS = "          + Palette.REGULAR +
							" AND PALETTE.ALLOCATION = "      + Palette.NOT_ALLOCATED +
							" AND PALETTE.EMPTY = "          + Palette.STATUS_EMPTY +
							" AND SHELF.PARENTSTATIONNUMBER = {0}" +
							" AND SHELF.HARDZONEID = {1} " +
							" AND SHELF.SOFTZONEID = {2} " +
							" AND SHELF.PRESENCE = "     + Shelf.PRESENCE_STORAGED +
							" AND SHELF.STATUS   = "     + Station.STATION_OK   +
							" AND SHELF.ACCESSNGFLAG = " + Shelf.ACCESS_OK      +
							" ORDER BY {3} FOR UPDATE";

			Object[] fmtObj = new Object[4] ;

			fmtObj[0] = tAisle.getStationNumber();

			for (int i = 0 ; i < tZone.length ; i++)
			{
				fmtObj[1] = Integer.toString(tZone[i].getHardZone().getHardZoneID());
				fmtObj[2] = Integer.toString(tZone[i].getSoftZoneID());
				switch (tZone[i].getDirection())
				{
					//#CM34028
					// HP, from lower shelf
					case Zone.HP_LOWER:
						fmtObj[3] = "NLEVEL, NBAY, NBANK";
						break;
						
					//#CM34029
					// HP, from front shelf
					case Zone.HP_FRONT:
						fmtObj[3] = "NBAY, NLEVEL, NBANK";
						break;
						
					//#CM34030
					// OP, from lower shelf
					case Zone.OP_LOWER:
						fmtObj[3] = "NLEVEL, NBAY DESC, NBANK";
						break;
						
					//#CM34031
					// OP, from front shelf
					case Zone.OP_FRONT:
						fmtObj[3] = "NBAY DESC, NLEVEL, NBANK";
						break;
						
					default:
						//#CM34032
						//undifined , throw exception!
						Object[] tObj = new Object[3] ;
						tObj[0] = this.getClass().getName() ;
						tObj[1] = "wDirection";
						tObj[2] = Integer.toString(tZone[i].getDirection()) ;
						String classname = (String)tObj[0];
						RmiMsgLogClient.write(6016061, LogMessage.F_ERROR, classname, tObj);
						throw (new InvalidDefineException("6016061" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
				}

				sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
				//#CM34033
				// Run the query, then generate the Shle instance from the leading data of result set.
				rset = executeSQL(sqlstring, true);
				//#CM34034
				// Generates only one Shelf instance.
				fndStation = makeShelf(rset, 1) ;
				if (fndStation.length > 0)
				{
					return fndStation[0];
				}
			}
		}
		catch (NotFoundException nfe)
		{
			//#CM34035
			// This will not happen, however in case it did;
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM34036
			// This will not happen, however in case it did;
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		
		return null;
	}

	//#CM34037
	/**
	 * Fetch location status with respect to warehouse, bank no.
	 * Search shelf table, palette table with specified warehouse, bank no. as the base and return the location status
	 * @param pWhNumber   Warehouse No.
	 * @param pBank       Bank
	 * @return Shelf instance 
	 * @throws ReadWriteException Throw any error during database processing
	 */
	public Shelf[] findLocationStatus(String pWhNumber, int pBank) throws ReadWriteException
	{
		ResultSet rset     = null;

		try
		{
			String fmtSQL = "SELECT SHELF.STATIONNUMBER, SHELF.WHSTATIONNUMBER, " +
							" SHELF.NBANK, SHELF.NBAY, SHELF.NLEVEL, " +
							" SHELF.PRESENCE, SHELF.ACCESSNGFLAG, SHELF.STATUS, " +
							" DECODE(PALETTE.STATUS, NULL, 999, PALETTE.STATUS) P_STATUS, " +
							" DECODE(PALETTE.EMPTY, NULL, 999, PALETTE.EMPTY) P_EMPTY " +
							" FROM SHELF, PALETTE WHERE " +
							" SHELF.STATIONNUMBER = PALETTE.CURRENTSTATIONNUMBER(+) " +
							" AND SHELF.WHSTATIONNUMBER = " + DBFormat.format(pWhNumber) +
							" AND SHELF.NBANK =  " + pBank +
							" ORDER BY SHELF.NLEVEL DESC, SHELF.NBAY ASC";

			//#CM34038
			// Run the query, then generate the Shle instance from the leading data of result set.
			rset = executeSQL(fmtSQL, true);

			Vector conVec = new Vector();
			
			while(rset.next())
			{
				Shelf rShelf = new Shelf();
				
				//#CM34039
				// Generates Shelf instance.
				//#CM34040
				// station number
				rShelf.setStationNumber(rset.getString("STATIONNUMBER"));
				//#CM34041
				// bank
				rShelf.setNBank(rset.getInt("NBANK")) ;
				//#CM34042
				// bay
				rShelf.setNBay(rset.getInt("NBAY")) ;
				//#CM34043
				// level
				rShelf.setNLevel(rset.getInt("NLEVEL")) ;
				//#CM34044
				// warehouse
				rShelf.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				//#CM34045
				// location availability
				rShelf.setStatus(rset.getInt("STATUS")) ;
				//#CM34046
				// presence
				rShelf.setPresence(rset.getInt("PRESENCE")) ;
				//#CM34047
				// accsess ng flag
				rShelf.setAccessNgFlag(rset.getInt("ACCESSNGFLAG")) ;
				//#CM34048
				// stock status flag
				rShelf.setPriority(rset.getInt("P_STATUS")) ;
				//#CM34049
				// empty status
				rShelf.setSide(rset.getInt("P_EMPTY")) ;
				
				conVec.add(rShelf);
			}
			
			//#CM34050
			// move instance from vector to array of Shelf.
			Shelf[] rstarr = new Shelf[conVec.size()] ;
			conVec.copyInto(rstarr);

			return rstarr;
		}
		catch (SQLException e)
		{
			//#CM34051
			//6006002 = Database error occured.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASShelfHandler" ) ;
			throw (new ReadWriteException("6007030" + wDelim +"Shelf")) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM34052
			// This will not happen, however in case it did;
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM34053
			// This will not happen, however in case it did;
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		finally
		{
			closeStatement() ;
		}
	}

	//#CM34054
	/**
	 * Search Shelf, Palette info and return the result count<BR>
	 * Fetch data with SHELF.StationNumber and PALETTE.CurrentStationNumber
	 * in the search key
	 * @param shelfKey location info search key
	 * @param pltKey   palette info search key
	 * @return search result count
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public int count(ShelfSearchKey shelfKey, PaletteSearchKey pltKey)
			throws ReadWriteException
	{
		int wCount = 0 ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[1] ;
		String tableName = "SHELF, PALETTE";

		try
		{
			stmt = getConnection().createStatement() ;
			String fmtSQL = "SELECT COUNT( * ) COUNT FROM " + tableName + " {0}" ;

			//#CM34055
			// add search conditions
			if (shelfKey.getReferenceCondition() != null && pltKey.getReferenceCondition() != null)
			{
				fmtObj[0] = " WHERE SHELF.STATIONNUMBER = PALETTE.CURRENTSTATIONNUMBER AND " 
				//#CM34056
				// Set shelf search key
				+ shelfKey.getReferenceCondition() + " AND "
				//#CM34057
				// Set palette search key
				+ pltKey.getReferenceCondition();
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", tableName + " COUNT SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			if (rset != null)
			{
				while (rset.next())
				{
					wCount = rset.getInt("COUNT") ;
				}
			}
		}
		catch (SQLException e)
		{
			//#CM34058
			// 6006002 = database error occurred{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM34059
			// throw ReadWriteException error message
			throw (new ReadWriteException("6006002" + wDelim + tableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM34060
				// 6006002 = database error occurred{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM34061
				// throw ReadWriteException error message
				throw (new ReadWriteException("6006002" + wDelim + tableName)) ;
			}
		}
		
		return wCount ;
	}

	//#CM34062
	/**
	 * This method returns the empty location count<BR>
	 * Search empty location combining location and palette<BR>
	 * Empty location is decided if it meets the following conditions<BR>
	 * <DIR>
	 * SHELF:Access NG flag:OK<BR>
	 * SHELF:Status ; available<BR>
	 * SHELF:Status ; empty location<BR>
	 * <BR>
	 * SHELF:Access NG flag:OK<BR>
	 * SHELF:Status ; available<BR>
	 * SHELF:Status ; reserved location<BR>
	 * PALETTE:Stock Status ; storage reserved location<BR>
	 * </DIR>
	 * @param key  search key
	 * @return search result count
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public int countEmptyShelf(ShelfSearchKey key)
			throws ReadWriteException
	{
		int wCount = 0 ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[1] ;
		String tableName = "SHELF, PALETTE";

		try
		{
			stmt = getConnection().createStatement() ;
			String fmtSQL = "SELECT COUNT(*) COUNT FROM " + tableName + " {0}" ;

			//#CM34063
			// add search conditions
			if (key.getReferenceCondition() != null)
			{
				fmtObj[0] = "WHERE " + key.getReferenceCondition()
				+ " AND SHELF.STATIONNUMBER = PALETTE.CURRENTSTATIONNUMBER(+)"
				+ " AND SHELF.ACCESSNGFLAG = " + Shelf.ACCESS_OK
				+ " AND SHELF.STATUS = " + Shelf.STATUS_OK
				+ " AND (SHELF.PRESENCE = " + Shelf.PRESENCE_EMPTY
				+ " OR (SHELF.PRESENCE = " + Shelf.PRESENCE_RESERVATION
				+ " AND PALETTE.STATUS = " + Palette.STORAGE_PLAN + "))";
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", tableName + " COUNT SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			if (rset != null)
			{
				while (rset.next())
				{
					wCount = rset.getInt("COUNT") ;
				}
			}
		}
		catch (SQLException e)
		{
			//#CM34064
			// 6006002 = database error occurred{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM34065
			// throw ReadWriteException error message
			throw (new ReadWriteException("6006002" + wDelim + tableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM34066
				// 6006002 = database error occurred{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM34067
				// throw ReadWriteException error message
				throw (new ReadWriteException("6006002" + wDelim + tableName)) ;
			}
		}
		return wCount ;
	}
	//#CM34068
	// Package methods -----------------------------------------------

	//#CM34069
	// Protected methods ---------------------------------------------
	//#CM34070
	/**
	 * Generate an array of <code>Shelf</code> instance from <code>ResultSet</code>.
	 * @param rset :result set from SHELF table search
	 * @param maxcreate : number of Shelf instance to generate. If it is 0, generate instances of 
	 * all result set.
	 * @return :Shelf instance array generated
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 */
	protected Shelf[] makeShelf(ResultSet rset, int maxcreate) throws ReadWriteException
	{
		//#CM34071
		// temporary store for Shelf instances
		Vector tmpShelfVect = new Vector() ; 

		//#CM34072
		// data get from resultset and make new Shelf instance
		try
		{
			int count = 0;
			while(rset.next())
			{
				if ((maxcreate != 0) && (count > maxcreate))
				{
					//#CM34073
					// Gets out the loop when the cycle exceeded the ceiling number of of instance generation.
					break;
				}
				//#CM34074
				// Generates Shelf instance.
				Shelf tmpShelf = new Shelf() ;
				setShelf(rset, tmpShelf);
				tmpShelfVect.add(tmpShelf) ;

				//#CM34075
				// count up.
				count++;
			}
		}
		catch (InvalidStatusException ise)
		{
			ise.printStackTrace() ;
			throw new ReadWriteException(ise.getMessage());
		}
		catch (SQLException e)
		{
			//#CM34076
			//6006002 = Database error occured.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASShelfHandler" ) ;
			throw (new ReadWriteException("6007030" + wDelim +"Shelf")) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM34077
		// move instance from vector to array of Shelf.
		Shelf[] rstarr = new Shelf[tmpShelfVect.size()] ;
		tmpShelfVect.copyInto(rstarr);

		return (rstarr) ;
	}

	//#CM34078
	/**
	 * Get each item from <code>ResultSet</code>, and set each item for <code>Shelf</code> instance.
	 * @param rset :result set from SHELF table search
	 * @param tmpShelf :Shelf instance to be set 
	 * @return :Shelf instance generated
	 * @throws SQLException :Notifies if error occured in connection with database. 
	 */
	protected Shelf setShelf(ResultSet rset, Shelf tmpShelf) throws SQLException, InvalidStatusException
	{
		//#CM34079
		// station number
		tmpShelf.setStationNumber(rset.getString("STATIONNUMBER"));
		//#CM34080
		// bank
		tmpShelf.setNBank(rset.getInt("NBANK")) ;
		//#CM34081
		// bay
		tmpShelf.setNBay(rset.getInt("NBAY")) ;
		//#CM34082
		// level
		tmpShelf.setNLevel(rset.getInt("NLEVEL")) ;
		//#CM34083
		// warehouse
		tmpShelf.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
		//#CM34084
		// location availability
		tmpShelf.setStatus(rset.getInt("STATUS")) ;
		//#CM34085
		// presence
		tmpShelf.setPresence(rset.getInt("PRESENCE")) ;
		//#CM34086
		// hardzone
		tmpShelf.setHardZoneID(rset.getInt("HARDZONEID")) ;
		//#CM34087
		// softzone
		tmpShelf.setSoftZoneID(rset.getInt("SOFTZONEID")) ;
		//#CM34088
		// parent station
		tmpShelf.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER"))) ;
		//#CM34089
		// aile station
		tmpShelf.setAisleStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER"))) ;
		//#CM34090
		// accsess ng flag
		tmpShelf.setAccessNgFlag(rset.getInt("ACCESSNGFLAG")) ;
		//#CM34091
		// priority
		tmpShelf.setPriority(rset.getInt("PRIORITY"));
		//#CM34092
		// pair station number
		tmpShelf.setPairStationNumber(DBFormat.replace(rset.getString("PAIRSTATIONNUMBER")));
		//#CM34093
		// side
		tmpShelf.setSide(rset.getInt("SIDE"));

		return tmpShelf;
	}

	//#CM34094
	/**
	 * Close the wStatement, which is the instance variable.
	 * The cursor generated by executeSQL method must call this method to close.
	 */
	protected void closeStatement()
	{
		try
		{
			if (wStatement != null) wStatement.close() ;
			wStatement = null ;
		}
		catch (SQLException se)
		{
			//#CM34095
			//6006002 = Database error occured.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, se), "ASShelfHandler" ) ;
		}
		catch (NullPointerException npe)
		{
			//#CM34096
			// Also perform the outputting og error log.
			Object[] tObj = new Object[1] ;
			if (wStatement != null)
			{
				tObj[0] = wStatement.toString() ;
			}
			else
			{
				tObj[0] = "null" ;
			}
			RmiMsgLogClient.write(6016066, LogMessage.F_ERROR, "ASShelfHandler", tObj) ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//#CM34097
	/**
	 * Accept the SQL statement and execute.
	 * @param sqlstr :SQL statement to execute
	 * @param query : true if it is query
	 * @return <code>ResultSet</code> of the result.  null for anything else
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if executed result was 0.
	 * @throws DataExistsException :Notifies if it broke the uniqye restriction at Insert.
	 */
	protected ResultSet executeSQL(String sqlstr, boolean query) throws ReadWriteException, NotFoundException, DataExistsException
	{
		ResultSet rset = null ;

		try
		{
			//#CM34098
			// Scroll cursor is required in order to view line 0 by first() in query.
			wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
										, ResultSet.CONCUR_READ_ONLY
									) ;
			if (query)
			{
				//#CM34099
				// SELECT
				rset = wStatement.executeQuery(sqlstr);
			}
			else
			{
				int rrows = wStatement.executeUpdate(sqlstr);
				//#CM34100
				// When there is no updating line, the place which was not closing cursor is corrected
				closeStatement() ;
				if (rrows == 0)
				{
					throw (new NotFoundException("6003018")) ;
				}
			}
		}
		catch (SQLException se)
		{
			if (se.getErrorCode() == AsrsParam.DATAEXISTS)
			{
				//#CM34101
				// 6026034=can't register since same data already exist
				RmiMsgLogClient.write(6026034, LogMessage.F_ERROR, "ASShelfHandler", null);
				throw (new DataExistsException("6026034")) ;
			}
			//#CM34102
			//6006002 = Database error occured.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, se), "ASShelfHandler" ) ;
			//#CM34103
			// Database error occured.
			throw (new ReadWriteException("6006002")) ;
		}
		
		return (rset) ;
	}
}
//#CM34104
//end of class


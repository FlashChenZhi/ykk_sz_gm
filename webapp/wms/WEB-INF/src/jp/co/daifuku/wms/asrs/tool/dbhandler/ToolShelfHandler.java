//#CM47916
//$Id: ToolShelfHandler.java,v 1.2 2006/10/30 02:17:16 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47917
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

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.Zone;
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
import jp.co.daifuku.common.text.StringUtil;

//#CM47918
/**<en>
 * This class is used to retrieve/store the <code>Shelf</code> class from/to the database.
 * Please utilize <code>StationFactory</code> when retrieving the <code>Shelf</code> class.
 * As <code>getHandler</code> method has been prepared in the <code>Shelf</code> class,
 * if a support of the Handler is needed, please use <code>getHandler</code> method and obtain.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/09</TD><TD>okamura</TD><TD>Correction: a method to retrieve the zone ID (findZones) was added.<br>
 * This is used in soft zone inquiry of Tool screen.<br>
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:16 $
 * @author  $Author: suresh $
 </en>*/
public class ToolShelfHandler extends ToolStationHandler
{

	//#CM47919
	// Class fields --------------------------------------------------
	private static final String SHELF_HANDLE = "jp.co.daifuku.wms.base.dbhandler.ShelfHandler";

	private static final String DOUBLEDEEPSHELF_HANDLE = "jp.co.daifuku.wms.base.dbhandler.DoubleDeepShelfHandler";
	
	//#CM47920
	// Class variables -----------------------------------------------
	private boolean isScreen = false ;

	//#CM47921
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_SHELF";

	//#CM47922
	// Constructors --------------------------------------------------
	//#CM47923
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolShelfHandler(Connection conn)
	{
		super(conn) ;
	}
	//#CM47924
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolShelfHandler(Connection conn, String tablename)
	{
		super (conn) ;
		wTableName = tablename;
	}

	//#CM47925
	// Class method --------------------------------------------------
	//#CM47926
	/**<en>
	 * Returns the version of this class.
	 * @return Versin and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:16 $") ;
	}

	//#CM47927
	// Public methods ------------------------------------------------
	//#CM47928
	/**<en>
	 * isScreen is a flag which enables the quicker responses in Shelf search results display 
	 * by avoiding unrequried items to show by placing makeShelf().
	 * @return isScreen
	 </en>*/
	public boolean getisScreen()
	{
		return isScreen;
	}

	//#CM47929
	/**<en>
	 * isScreen is a flag which enables the quicker responses in Shelf search results display 
	 * by avoiding unrequried items to show by placing makeShelf().
	 * F.Y.I. In case of searching 2000 shelves, if makeStation(wConn, psnum), which generates the 
	 * ParentStation in makeShelf(), gets executed, it takes 30 - 40 seconds, while it only takes
	 * 2 - 4 seconds if this flag is set.
	 </en>*/
	public void setisScreen()
	{
		isScreen = true ;
	}
	//#CM47930
	/**<en>
	 * Delete all data from the table.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public void truncate() throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		try
		{
			stmt = wConn.createStatement();
			String sqlstring = "TRUNCATE TABLE "+ wTableName ;
			//#CM47931
			// execute the sql
			rset = stmt.executeQuery(sqlstring);
			
			sqlstring = "DELETE FROM " + wStationTypeTableName +
						" WHERE HANDLERCLASS = " + "'" + SHELF_HANDLE + "'" + 
						" OR HANDLERCLASS = " + "'" + DOUBLEDEEPSHELF_HANDLE + "'";
			//#CM47932
			// execute the sql

			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47933
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47934
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM47935
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47936
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}

	//#CM47937
	/**<en>
	 * Search and retrieve the location.
	 * @param key :Key for search
	 * @return    :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		//#CM47938
		//-------------------------------------------------
		//#CM47939
		// variable define
		//#CM47940
		//-------------------------------------------------
		Shelf[] fndStation = null ;
		Object[]  fmtObj = new Object[2] ;

		//#CM47941
		// for database access
		ResultSet rset = null ;

		String fmtSQL = "SELECT * FROM "+ wTableName +" {0} {1}" ;

		if (key.ReferenceCondition() != null)
		{
			if (key.SortCondition() != null)
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
				fmtObj[1] = " ORDER BY " + key.SortCondition();
			}
			else
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
			}
		}
		else if (key.SortCondition() != null)
		{
			fmtObj[0] = " ORDER BY " + key.SortCondition();
		}

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method
		}
		catch (NotFoundException nfe)
		{
			//#CM47942
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM47943
			//<en> This should not happen;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}

		//#CM47944
		// make Shelf instances from resultset
		fndStation = makeShelf(rset) ;

		return(fndStation) ;
	}


	//#CM47945
	/**<en>
	 * Return the list of bank of the specified warehouse.
	 * <CODE>Statement</CODE> uses wStatement to open a cursor.<BR>
	 * wStatement is used only as a temporary measure; a modification will be made at some stage
	 * so taht a cursor will be generated within this method.
	 * @param whstno   :warehouse station no.
	 * @return :int array of the Bank that meets the specified conditions
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int[] findBanks(String whstno) throws ReadWriteException
	{
		Vector vec = new Vector();
		int[] Banks = null;
		ResultSet rset = null;

		String sqlstring = "SELECT NBANK FROM "+ wTableName 
						 + " WHERE WHSTATIONNUMBER = " + DBFormat.format(whstno) + " "
						 + " GROUP BY NBANK "
						 + " ORDER BY NBANK ";

		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method

			while(rset.next())
			{
				vec.add(new Integer(rset.getInt("NBANK"))) ;
			}

			Banks = new int[vec.size()] ;
			for (int i=0; i < Banks.length; i++)
			{
				Integer bank = (Integer)vec.remove(0) ;
				Banks[i] = bank.intValue() ;
			}
		}
		catch (DataExistsException dee)
		{
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch(NotFoundException nfe)
		{
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch(SQLException e)
		{
			//#CM47946
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47947
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			closeStatement() ;
		}

		return(Banks) ;
	}

	//#CM47948
	/**<en>
	 * Return a list of Bay Max value per bank of the specified warehouse.<BR>
	 * Example case: bank 1 with 44 bays, bank 2 with 44 bays, bank 3 with 18 bays;<BR>
	 * Returns Bays[0]=44, Bays[1]=44, Bays[2]=18.<BR>
	 * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
	 * wStatement is used only as a temporary measure; a modification will be made at some stage
	 * so that the cursors will be generated within this method.
	 * @param whstno   :warehouse station no.
	 * @return :group of Bay Max value under specified conditions
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int[] findBays(String whstno) throws ReadWriteException
	{
		Vector vec = new Vector();
		int Bays[] = null;
		ResultSet rset = null;

		String sqlstring = "SELECT MAX(NBAY) NBAY FROM "+ wTableName 
						 + " WHERE WHSTATIONNUMBER = " + DBFormat.format(whstno) + " "
						 + " GROUP BY NBANK "
						 + " ORDER BY NBANK ";

		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method

			while(rset.next())
			{
				vec.add(new Integer(rset.getInt("NBAY"))) ;
			}

			Bays = new int[vec.size()] ;
			for (int i=0; i < Bays.length; i++)
			{
				Integer bay = (Integer)vec.remove(0) ;
				Bays[i] = bay.intValue() ;
			}
		}
		catch (DataExistsException dee)
		{
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch(NotFoundException nfe)
		{
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch(SQLException e)
		{
			//#CM47949
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47950
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			closeStatement() ;
		}

		return(Bays) ;
	}

	//#CM47951
	/**<en>
	 * Return a list of level Max value per bank of the warehouse specified.<BR>
	 * Example case: bank1 with 22 levels, bank2 with 22 levels, bank 3 with 4 levels;
	 * Returns Levels[0]=22, Levels[1]=22, Levels[2]=4.<BR>
	 * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
	 * wStatement is used only as a temporary measure; a modification will be made at some stage
	 * so that the cursors will be generated within this method.
	 * @param whstno   :warehouse station no.
	 * @return :group of Level Max value under specified conditions
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int[] findLevels(String whstno) throws ReadWriteException
	{
		Vector vec = new Vector();
		int[] Levels = null;
		ResultSet rset = null;

		String sqlstring = "SELECT MAX(NLEVEL) NLEVEL FROM "+ wTableName 
						 + " WHERE WHSTATIONNUMBER = " + DBFormat.format(whstno) + " "
						 + " GROUP BY NBANK "
						 + " ORDER BY NBANK ";

		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method

			while(rset.next())
			{
				vec.add(new Integer(rset.getInt("NLEVEL"))) ;
			}
			Levels = new int[vec.size()] ;
			for (int i=0; i < Levels.length; i++)
			{
				Integer level = (Integer)vec.remove(0) ;
				Levels[i] = level.intValue() ;
			}
		}
		catch (DataExistsException dee)
		{
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch(NotFoundException nfe)
		{
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch(SQLException e)
		{
			//#CM47952
			//<en>6126001 = Database error occured. {0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47953
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>

			throw (new ReadWriteException()) ;
		}
		finally
		{
			closeStatement() ;
		}
		return(Levels) ;
	}

	//#CM47954
	/*  2003/12/09  INSERT  Y.OKAMURA START  */

	//#CM47955
	/**<en>
	 * Return the soft zone ID acording to the specified consitions.<BR>
	 * <CODE>Statement</CODE> uses wStatement to open cursors.<BR>
	 * wStatement is used only as a temporary measure; a modification will be made at some stage
	 * so that the cursors will be generated within this method.
	 * @param key :Key for search
	 * @return :SOFTZONEID which meet the specified conditions
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int[] findZones(ToolSearchKey key) throws ReadWriteException
	{
		//#CM47956
		//-------------------------------------------------
		//#CM47957
		// variable define
		//#CM47958
		//-------------------------------------------------
		Vector vec = new Vector();
		int[] softzoneid = null ;
		Object[]  fmtObj = new Object[2] ;

		//#CM47959
		// for database access
		ResultSet rset = null ;

		String fmtSQL = "SELECT DISTINCT(SOFTZONEID) SOFTZONEID FROM "+ wTableName +" {0} {1}" ;

		if (key.ReferenceCondition() != null)
		{
			if (key.SortCondition() != null)
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
				fmtObj[1] = " ORDER BY " + key.SortCondition();
			}
			else
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
			}
		}
		else if (key.SortCondition() != null)
		{
			fmtObj[0] = " ORDER BY " + key.SortCondition();
		}

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method
			while(rset.next())
			{
				vec.add(new Integer(rset.getInt("SOFTZONEID"))) ;
			}
			softzoneid = new int[vec.size()] ;
			for (int i=0; i < softzoneid.length; i++)
			{
				Integer id = (Integer)vec.remove(0) ;
				softzoneid[i] = id.intValue() ;
			}

		}
		catch (DataExistsException dee)
		{
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch(NotFoundException nfe)
		{
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch(SQLException e)
		{
			//#CM47960
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47961
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			closeStatement() ;
		}
		return(softzoneid) ;
	}
	//#CM47962
	/*  2003/12/09  INSERT  Y.OKAMURA END  */


	//#CM47963
	/**<en>
	 * Based on the conditions given by <CODE>ToolSearchKey</CODE>, it returns the number of 
	 * corresponding data.
	 * @param key :Key for search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt      = null;
		ResultSet rset      = null;
		int       count     = 0;
		Object[] fmtObj = new Object[1] ;

	 	try
	 	{
			String fmtSQL = "SELECT COUNT(*) COUNT FROM "+ wTableName +" {0} " ;

			if (key.ReferenceCondition() != null)
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			stmt = wConn.createStatement();
			rset = stmt.executeQuery(sqlstring);

			while (rset.next())
			{
				count = rset.getInt("count");
			}
		}
		catch(SQLException e)
		{
			//#CM47964
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47965
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM47966
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47967
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
		return count ;
	}

	//#CM47968
	/**<en>
	 * Based on the conditions given by <CODE>ToolSearchKey</CODE>, it returns the number of 
	 * corresponding data.
	 * @param key :Key for search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(int fbank,int fbay,int flevel,int tbank,int tbay,int tlevel,String whstno) throws ReadWriteException
	{
		Statement stmt      = null;
		ResultSet rset      = null;
		int       count     = 0;
		Object[] fmtObj = new Object[3] ;

	 	try
	 	{
			String fmtSQL = "SELECT COUNT(*) COUNT FROM "+ wTableName +
							" WHERE WHSTATIONNUMBER = '" + whstno + "' {0} {1} {2} " ;

			if (fbank > 0)
			{
				fmtObj[0] = " AND (NBANK >= " + fbank + " AND NBANK <= " + tbank + ")";
			}
			if (fbay > 0)
			{
				fmtObj[1] = " AND (NBAY >= " + fbay + " AND NBAY <= " + tbay + ")";
			}
			if (flevel > 0)
			{
				fmtObj[2] = " AND (NLEVEL >= " + flevel + " AND NLEVEL <= " + tlevel + ")";
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			stmt = wConn.createStatement();
			rset = stmt.executeQuery(sqlstring);

			while (rset.next())
			{
				count = rset.getInt("count");
			}
		}
		catch(SQLException e)
		{
			//#CM47969
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47970
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM47971
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47972
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
		return count ;
	}

	//#CM47973
	/**<en>
	 * Newly create the location information in database.
	 * @param tgt :entity instance which preserves the location information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if identical location is already registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		//#CM47974
		//-------------------------------------------------
		//#CM47975
		// variable define
		//#CM47976
		//-------------------------------------------------
		String fmtSQL = "INSERT INTO "+ wTableName +" (" +
						"  STATIONNUMBER" +				// 0
						", NBANK" +						// 1
						", NBAY" +						// 2
						", NLEVEL" +					// 3
						", WHSTATIONNUMBER" +			// 4
						", STATUS" +					// 5
						", PRESENCE" +					// 6
						", HARDZONEID" +				// 7
						", SOFTZONEID" +				// 8
						", PARENTSTATIONNUMBER" +		// 9
						", ACCESSNGFLAG" +				// 10
						", PRIORITY" +					// 11
						", PAIRSTATIONNUMBER" +			// 12
						", SIDE" +						// 13
						") values (" +
						"{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13}" +
						")" ;
		String fmtSQL_StationType = "INSERT INTO "+wStationTypeTableName+" (" +
						"  STATIONNUMBER" +				// 0
						",  HANDLERCLASS" +				// 1
						") VALUES (" +
						"{0}, '" + SHELF_HANDLE + "'" + 
						")" ;


		Shelf tgtShelf ;
		String sqlstring ;
		//#CM47977
		//-------------------------------------------------
		//#CM47978
		// process
		//#CM47979
		//-------------------------------------------------
		if (tgt instanceof Shelf)
		{
			tgtShelf = (Shelf)tgt ;
		}
		else
		{
			//#CM47980
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Shelf Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM47981
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtShelf) ;
			//#CM47982
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM47983
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM47984
			//for stationtype table
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47985
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM47986
	/**<en>
	 * Newly create the location information in database.
	 * @param tgt :entity instance which preserves the location information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if identical location is already registered in database.
	 </en>*/
	public void createDoubleDeep(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		//#CM47987
		//-------------------------------------------------
		//#CM47988
		// variable define
		//#CM47989
		//-------------------------------------------------
		String fmtSQL = "INSERT INTO "+ wTableName +" (" +
						"  STATIONNUMBER" +				// 0
						", NBANK" +						// 1
						", NBAY" +						// 2
						", NLEVEL" +					// 3
						", WHSTATIONNUMBER" +			// 4
						", STATUS" +					// 5
						", PRESENCE" +					// 6
						", HARDZONEID" +				// 7
						", SOFTZONEID" +				// 8
						", PARENTSTATIONNUMBER" +		// 9
						", ACCESSNGFLAG" +				// 10
						", PRIORITY" +					// 11
						", PAIRSTATIONNUMBER" +			// 12
						", SIDE" +						// 13
						") values (" +
						"{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13}" +
						")" ;
		String fmtSQL_StationType = "INSERT INTO "+wStationTypeTableName+" (" +
						"  STATIONNUMBER" +				// 0
						",  HANDLERCLASS" +				// 1
						") VALUES (" +
						"{0}, '" + DOUBLEDEEPSHELF_HANDLE + "'" + 
						")" ;


		Shelf tgtShelf ;
		String sqlstring ;
		//#CM47990
		//-------------------------------------------------
		//#CM47991
		// process
		//#CM47992
		//-------------------------------------------------
		if (tgt instanceof Shelf)
		{
			tgtShelf = (Shelf)tgt ;
		}
		else
		{
			//#CM47993
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Shelf Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM47994
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtShelf) ;
			//#CM47995
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM47996
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM47997
			//for stationtype table
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47998
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM47999
	/**<en>
	 * Modify the location information in database.
	 * @param tgt :entity instance which preserves the location information to create
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if location to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM48000
		//<en> UPdate DB.</en>
		//#CM48001
		//<en>-------------------------------------------------</en>
		//#CM48002
		// variable define
		//#CM48003
		//-------------------------------------------------
		String fmtSQL = "UPDATE shelf set" +
						"  NBANK = {1}" +					// 1
						", NBAY = {2}" +					// 2
						", NLEVEL = {3}" +					// 3
						", WHSTATIONNUMBER = {4}" +			// 4
						", STATUS = {5}" +					// 5
						", PRESENCE = {6}" +				// 6
						", HARDZONE = {7}" +				// 7
						", PARENTSTATIONNUMBER = {8}" +		// 8
						", ACCESSNGFLAG = {9}" +			// 9
						", PRIORITY = {10}" +				// 10
						", PAIRSTATIONNUMBER = {11}" +		// 11
						", SIDE = {12}" +				// 12
						" WHERE STATIONNUMBER = {0}" ;		// 0


		Shelf tgtShelf;
		String sqlstring ;
		//#CM48004
		//-------------------------------------------------
		//#CM48005
		// process
		//#CM48006
		//-------------------------------------------------
		if (tgt instanceof Shelf)
		{
			tgtShelf = (Shelf)tgt ;
		}
		else
		{
			//#CM48007
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Shelf Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM48008
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtShelf) ;
			//#CM48009
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM48010
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48011
			//<en> This should not happen;</en>

			dee.printStackTrace();
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48012
	/**<en>
	 * Modify the location information in database. The contents and conditions of the modification
	 * will be obtained by AlterKey.
	 * @param  key :AlterKey isntance which preserves the contents and conditions of modification 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM48013
		//-------------------------------------------------
		//#CM48014
		// variable define
		//#CM48015
		//-------------------------------------------------
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM48016
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM48017
			//<en> Exception if update value has not been set;</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, this.getClass().getName(), tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM48018
			//<en> Exception if update conditions have not been set;</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, this.getClass().getName(), tobj);
			throw (new InvalidDefineException("6126006"));
		}
		fmtObj[2] = " WHERE " + key.ReferenceCondition(table);

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			rset = executeSQL(sqlstring, false) ;	// private exec sql method
		}
		catch (DataExistsException dee)
		{
			//#CM48019
			//<en> This should not happen;</en>

			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48020
	/**<en>
	 * Update the specified values in the bank, bay and level range that zone instance preserves.
	 * @param zone  :zone instance
	 * @param alkey :key which preserve the update value
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found in database..
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolEntity zone, ToolAlterKey alkey) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		Zone ins = (Zone)zone ;
		String fmtSQL = " UPDATE {0} " +  
						   " SET {1} " + 
						   " WHERE NBANK  >= " + ins.getStartBank()  + " AND NBANK  <= " + ins.getEndBank()  +
						   " AND   NBAY   >= " + ins.getStartBay()   + " AND NBAY   <= " + ins.getEndBay()   +
						   " AND   NLEVEL >= " + ins.getStartLevel() + " AND NLEVEL <= " + ins.getEndLevel() +
						   " AND {2} " ;
		//#CM48021
		// for database access
		ResultSet rset = null ;

		Object[]  fmtObj     = new Object[3] ;
		fmtObj[0] = wTableName ;
		if (alkey.ModifyContents(wTableName) == null)
		{
			//#CM48022
			//<en> Exception if update value has not been set;</en>
			Object[] tobj = {wTableName};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, this.getClass().getName(), tobj) ;
			throw ( new InvalidDefineException("6126005") ) ;
		}
		fmtObj[1] = alkey.ModifyContents(wTableName) ;
		if (alkey.ReferenceCondition(wTableName) == null)
		{
			//#CM48023
			//<en> Exception if update conditiosn have not been set;</en>
			Object[] tobj = {wTableName};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, this.getClass().getName(), tobj);
			throw (new InvalidDefineException("6126006"));
		}
		fmtObj[2] = alkey.ReferenceCondition(wTableName);

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;	

		try
		{
			rset = executeSQL(sqlstring, false) ;	// private exec sql method
		}
		catch (DataExistsException dee)
		{
			//#CM48024
			//<en> This should not happen;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}
	

	//#CM48025
	/**<en>
	 * MOdify the location information in database. It updates the hard zone items with no condition.
	 * @param  zone :contents of modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if target data to modify cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modifyHardZone(int zone) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		String sqlstring = "UPDATE "+ wTableName +" SET HARDZONEID = " + zone;
		try
		{
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48026
			//<en> This should not happen.</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48027
	/**<en>
	 * Modify the location information in database. Update the hard zone items with not conditions.
	 * @param  zone :contents  of modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if target data to modify cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modifyHardZone(int fbank,int fbay,int flevel,int tbank,int tbay,int tlevel,int zoneid,String whstno) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		String sqlstring = "UPDATE "+ wTableName +" SET HARDZONEID = " + zoneid + 
							" WHERE (NBANK >= " + fbank + " AND NBAY >= " + fbay + " AND NLEVEL >= " + flevel + " ) " + 
							" AND   (NBANK <= " + tbank + " AND NBAY <= " + tbay + " AND NLEVEL <= " + tlevel + " ) " + 
							" AND WHSTATIONNUMBER = " + DBFormat.format(whstno) + " ";

		try
		{
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48028
			//<en> This should not happen.</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}
	

	//#CM48029
	/**<en>
	 * Delete from database the information that match the key passed through parameter.
	 * @param key :key for the information to delete
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException
	{
		//#CM48030
		//<en> Delete from DB.</en>
		ToolEntity[] tgts = find(key) ;
		for (int i=0; i < tgts.length; i++)
		{
			drop(tgts[i]) ;
		}
	}
	//#CM48031
	/**<en>
	 * Delete from database the location information passed through parameter.
	 * @param tgt :entity instance which preserves the location information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if location to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM48032
		//-------------------------------------------------
		//#CM48033
		// variable define
		//#CM48034
		//-------------------------------------------------
		String fmtSQL = "DELETE FROM " + wTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0

		String fmtSQL_StationType = "DELETE FROM "+wStationTypeTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0
		Station tgtShelf ;
		String sqlstring ;
		//#CM48035
		//-------------------------------------------------
		//#CM48036
		// process
		//#CM48037
		//-------------------------------------------------
		if (tgt instanceof Shelf)
		{
			tgtShelf = (Shelf)tgt ;
		}
		else
		{
			//#CM48038
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Shelf Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM48039
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtShelf) ;
			//#CM48040
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM48041
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM48042
			//for stationtype table.
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			//#CM48043
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48044
			//<en> This should not happen;</en>

			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48045
	/**<en>
	 * Set the min. bank and the max. bank in the array, then return.<BR>
	 * example:
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getBankRange(wareHouseStationNumber);
	 * int minBank = range[0];
	 * int maxBank = range[1];
	 * </pre>
	 * @param WHStationNo		:warehouse station no.
	 * @param parentStationNo	:parent station no (aisle station no.)
	 * @return range			:return the min. bank and the max. bank of specified warehouse 
	 * in int type array.
	 </en>*/
	public int[] getBankRange(String WHStationNo, String parentStationNo) throws ReadWriteException
	{
		int[] range = new int[2]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			String sqlString = 	"SELECT MIN(NBANK) MINBANK,MAX(NBANK) MAXBANK FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNo + "'" 
										+ "and PARENTSTATIONNUMBER = '" + parentStationNo + "'";
			rset = stmt.executeQuery( sqlString );
			while (rset.next())
			{
				range[0] = rset.getInt("MINBANK");
				range[1] = rset.getInt("MAXBANK");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48046
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48047
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48048
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48049
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}

	//#CM48050
	/**<en>
	 * Set the min. bay and teh max. bay of hte specified warehouse in the array, then return. <BR>
	 * example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getBayRange(wareHouseStationNumber);
	 * int minBay = range[0];
	 * int maxBay = range[1];
	 * </pre>
	 * @param WHStationNo		:warehouse station no.
	 * @param parentStationNo	:parent station no. (aisle station no.)
	 * @return range				:return the min.bank and the max. bank in the int type array.
	 </en>*/
	public int[] getBayRange(String WHStationNo, String parentStationNo) throws ReadWriteException
	{
		int[] range = new int[2]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			String sqlString = 	"SELECT MIN(NBAY) MINBAY,MAX(NBAY) MAXBAY FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNo + "'" 
										+ "and PARENTSTATIONNUMBER = '" + parentStationNo + "'";

			rset = stmt.executeQuery( sqlString );
			while (rset.next())
			{
				range[0] = rset.getInt("MINBAY");
				range[1] = rset.getInt("MAXBAY");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48051
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48052
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48053
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48054
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}

	//#CM48055
	/**<en>
	 * Set the min. level and the max. level of the specified warehouse in the array, then return.<BR>
	 * example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getLevelRange(wareHouseStationNumber);
	 * int minLevel = range[0];
	 * int maxLevel = range[1];
	 * </pre>
	 * @param WHStationNo		:warehouse station no.
	 * @param parentStationNo	:parent station no. (aisle station no.)
	 * @return range				:return the min.level and the max. level in int type array.
	 </en>*/
	public int[] getLevelRange(String WHStationNo, String parentStationNo) throws ReadWriteException
	{
		int[] range = new int[2]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			String sqlString = 	"SELECT MIN(NLEVEL) MINLEVEL,MAX(NLEVEL) MAXLEVEL FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNo + "'" 
										+ "and PARENTSTATIONNUMBER = '" + parentStationNo + "'";
			rset = stmt.executeQuery( sqlString );
			while (rset.next())
			{
				range[0] = rset.getInt("MINLEVEL");
				range[1] = rset.getInt("MAXLEVEL");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48056
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48057
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48058
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48059
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}

	//#CM48060
	/**
	 * Set Start and End Aisle position of specified Warehouse in the array and return it. <BR>
	 * Use example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getLevelRange(wareHouseStationNumber);
	 * int minAislePosition = range[0];
	 * int manAislePosition = range[1];
	 * </pre>
	 * @param WHStationNo		Warehouse Station No
	 * @param parentStationNo	Number of parents Station(Aisle Station No.)
	 * @return range				Return minimum Level and maximum Level by the int type array. 
	 */
	public int[] getAislePostion(String WHStationNo, String parentStationNo) throws ReadWriteException
	{
		int[] range = new int[2]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			String sqlString = 	"SELECT MIN(NBANK) MINLEVEL,MAX(NBANK) MAXLEVEL FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNo + "'" 
										+ " and PARENTSTATIONNUMBER = '" + parentStationNo + "'"
										+ " and SIDE = '" + Bank.NEAR + "'";
			rset = stmt.executeQuery( sqlString );
			while (rset.next())
			{
				range[0] = rset.getInt("MINLEVEL");
				range[1] = rset.getInt("MAXLEVEL");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48061
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48062
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48063
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48064
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}	
	
	//#CM48065
	/**<en>
	 * Set the min. bay and the max. bay of the specified warehouse and bank to the array then return.<BR>
	 * example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getBayRange(wareHouseStationNumber);
	 * int minBay = range[0];
	 * int maxBay = range[1];
	 * </pre>
	 * @param WHStationNo		:warehouse station no.
	 * @param parentStationNo	:parent station no. (aisle station no.)
	 * @return range				:return the min. bank and max. bank in int type array.
	 </en>*/
	public int[] getBayRange(String WHStationNo, int bank) throws ReadWriteException
	{
		int[] range = new int[2]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			String sqlString = 	"SELECT MIN(NBAY) MINBAY,MAX(NBAY) MAXBAY FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNo + "'" 
										+ "and NBANK = '" + Integer.toString(bank) + "'";

			rset = stmt.executeQuery( sqlString );
			while (rset.next())
			{
				range[0] = rset.getInt("MINBAY");
				range[1] = rset.getInt("MAXBAY");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48066
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48067
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48068
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48069
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}

	//#CM48070
	/**<en>
	 * Set the min.level and max. level of the specified warehouse to the array, then return.<BR>
	 * example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getLevelRange(wareHouseStationNumber);
	 * int minLevel = range[0];
	 * int maxLevel = range[1];
	 * </pre>
	 * @param WHStationNo		:warehouse station no.
	 * @param parentStationNo	:parent station no. (aisle station no.)
	 * @return range				:return the min. level and the max. level in int type array.
	 </en>*/
	public int[] getLevelRange(String WHStationNo, int bank) throws ReadWriteException
	{
		int[] range = new int[2]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			String sqlString = 	"SELECT MIN(NLEVEL) MINLEVEL,MAX(NLEVEL) MAXLEVEL FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNo + "'" 
										+ "and NBANK = '" + Integer.toString(bank) + "'";
			rset = stmt.executeQuery( sqlString );
			while (rset.next())
			{
				range[0] = rset.getInt("MINLEVEL");
				range[1] = rset.getInt("MAXLEVEL");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48071
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48072
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48073
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48074
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}



	//#CM48075
	/**<en>
	 * Set the max. bank, bay and level of specified warehouse to the array then return.<BR>
	 * example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getShelfRange(wareHouseStationNumber);
	 * int maxBank = range[0];
	 * int maxBay = range[1];
	 * int maxLevel = range[2];
	 * </pre>
	 * @param WHStationNumber		:warehouse station no.
	 * @return range				returns the max. bank, bay and the level in int type array.
	 </en>*/
	public int[] getShelfRange(String WHStationNumber) throws ReadWriteException
	{
		int[] range = new int[3]; 
		Statement stmt = null;
		ResultSet rset = null;

		try
		{
			stmt = wConn.createStatement();
			rset = stmt.executeQuery( 	"SELECT MAX(NBANK) MAXBANK,MAX(NBAY) MAXBAY, MAX(NLEVEL) MAXLEVEL FROM " + wTableName + 
										" WHERE WHSTATIONNUMBER = '" + WHStationNumber + "'");
			while (rset.next())
			{
				range[0] = rset.getInt("MAXBANK");
				range[1] = rset.getInt("MAXBAY");
				range[2] = rset.getInt("MAXLEVEL");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48076
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48077
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48078
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48079
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>

				throw (new ReadWriteException()) ;
			}
		}
	}
	
	//#CM48080
	/**<en>
	 * Set the max. bank, bay and level of specified warehouse to the array then return.<BR>
	 * example
	 * <pre>
	 * ShelfHandler shelfHandle = new ShelfHandler(wConn);
	 * int[] range = shelfHandle.getShelfRange(wareHouseStationNumber);
	 * int maxBank = range[0];
	 * int maxBay = range[1];
	 * int maxLevel = range[2];
	 * </pre>
	 * @param WHStationNumber		:warehouse station no.
	 * @return range				:return the max. bank, bay and the level in int-type array.
	 </en>*/
	public int[] getShelfRange(String WHStationNumber,int fbank,int tbank) throws ReadWriteException
	{
		int[] range = new int[3]; 
		Statement stmt = null;
		ResultSet rset = null;
		String sqlstring = "";

		try
		{
			sqlstring = "SELECT MAX(NBANK) MAXBANK,MAX(NBAY) MAXBAY, MAX(NLEVEL) MAXLEVEL FROM " + wTableName + 
						" WHERE WHSTATIONNUMBER = '" + WHStationNumber + "'" +
						" AND NBANK >= " + fbank + " AND NBANK <= " + tbank + " ";

			stmt = wConn.createStatement();
			rset = stmt.executeQuery( sqlstring);
			while (rset.next())
			{
				range[0] = rset.getInt("MAXBANK");
				range[1] = rset.getInt("MAXBAY");
				range[2] = rset.getInt("MAXLEVEL");
			}
			return range;
		}
		catch(SQLException e)
		{
			//#CM48081
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48082
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (stmt != null){
					stmt.close();
				}
				if (rset != null){
					rset.close();
				}
			}
			catch(SQLException e)
			{
				//#CM48083
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48084
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}

	//#CM48085
	/**<en>
	 * Retrieve the total number of locations of the specified zone range.
	 * Using the warehouse and the zone as parameters, return the number of locations of 
	 * specified warehouse.
	 * <BR><contents of process><BR>
	 *<B>1: in case of hard zone,</B><BR>
	 *Create the conditional SQL string of hard zone range.<BR>
	 *Under condition that WHSTATIONNUMBER should  be retrieved from st,<BR>
	 *count the number of corresponding locations from the SHELF table.
	 *<BR><example SQL to execute><BR>
	 * <PRE>
	 *	SELECT COUNT(1) COUNT  
	 *	FROM SHELF
	 * 	WHERE WHSTATIONNUMBER = 9000 
	 *	AND HARDZONE IN( 2 , 3 )  
	 * </PRE>
	 *<BR><B>2: in case of soft zone;</B><BR>
	 *Create the conditional SQL string of the soft zone range.<BR>
	 *.WHSTATIONNUMBER should be retrieved from st. <BR>
	 *Set above as a condition, then count the number of corresponding locations in the SHELF table.<BR>
	 *<BR><example SQL to execute><BR>
	 * <PRE>
	 *	SELECT COUNT(1) COUNT  
	 *	FROM SHELF
	 *	WHERE WHSTATIONNUMBER = 9000 
	 *	AND NBAY >= 4 AND NBAY <= 19 AND NLEVEL >= 1 AND NLEVEL <= 10 
	 *	OR NBAY >= 10 AND NBAY <= 20 AND NLEVEL >= 20 AND NLEVEL <= 28
	 * </PRE>
 	 *
	 * @param whSTNO :warehouse station no.		
	 * @param  zone		:the array of the zone instance
	 * @return :number of locations
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
	 </en>*/
	public int countShelf(String whSTNO, Zone[] zone) throws ReadWriteException
	{
		ResultSet countret  = null ;
		//#CM48086
		//<en>number of empty locations</en>
		int count = 0;
		//#CM48087
		//<en>SQL string for search</en>
		String sqlsting  = "";
		//#CM48088
		//<en>Create the conditional string with the zone range.</en>
		String zoneRangeString = "";
		try
		{
			//#CM48089
			//<en>Retrieve the warehouse station no.</en>
			String whStNo = whSTNO;
			//#CM48090
			//<en>****** in case of hard zone,*******// </en>

 			if(zone[0].getType() == Zone.HARD)
			{
				String hardZoneIDbuf = "";
				//#CM48091
				//<en>Create the conditional statement of hard zone range.</en>

				for(int i = 0; i < zone.length; i++)
				{
					if(i == 0)
					{
						hardZoneIDbuf = Integer.toString(zone[0].getZoneID());
					}
					else
					{
						hardZoneIDbuf = hardZoneIDbuf + " , " + Integer.toString(zone[i].getZoneID());
					}
				}
				zoneRangeString = " AND HARDZONE IN ( " + hardZoneIDbuf + " ) " ;

				sqlsting = "SELECT COUNT(1) COUNT"
							+ " FROM " + wTableName
							+ " WHERE WHSTATIONNUMBER = " + whStNo
							+ zoneRangeString;
				countret = executeSQL(sqlsting, true);

				while (countret.next())
				{
					count = countret.getInt("COUNT");
				}
				return count;
			}
			//#CM48092
			//<en>****** in case of soft zone, *******// </en>

			else
			{
				//#CM48093
				//<en>Create the conditional string of soft zone range.</en>

				for(int i = 0; i < zone.length; i++)
				{
					//#CM48094
					//<en>set AND condition for the 1st search.</en>
					if(i == 0)
					{
						zoneRangeString 
									= " NBAY >= " + zone[0].getStartBay() 
									+ " AND NBAY <= " + zone[0].getEndBay()
									+ " AND NLEVEL >= " + zone[0].getStartLevel()
									+ " AND NLEVEL <= " + zone[0].getEndLevel();
					}
					//#CM48095
					//<en>set OR condition for the 2nd search and on.</en>
					else
					{
						zoneRangeString = "(" + zoneRangeString
									+ " OR NBAY >= " + zone[i].getStartBay() 
									+ " AND NBAY <= " + zone[i].getEndBay()
									+ " AND NLEVEL >= " + zone[i].getStartLevel()
									+ " AND NLEVEL <= " + zone[i].getEndLevel() + ")";

					}
				}
				String zoneString = "";
				if(StringUtil.isBlank(zoneRangeString))
				{
					zoneString = "";
				}
				else
				{
					zoneString = " AND " + zoneRangeString;
				}

				sqlsting =  "SELECT COUNT(1) COUNT"
							+ " FROM " + wTableName
							+ " WHERE WHSTATIONNUMBER = " + whStNo 
							+ zoneString;
				countret = executeSQL(sqlsting, true);

				while (countret.next())
				{
					count = countret.getInt("COUNT");
				}
				closeStatement() ;
				return count;
			}
		}
		catch(SQLException e)
		{
			//#CM48096
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48097
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException());
		}
		catch(NotFoundException e)
		{
			return 0;
		}
		catch(DataExistsException e)
		{
			return 0;
		}
	}

	//#CM48098
	/**<en>
	 * Return the number of empty locations of the specified warehouse by using 
	 * the station and zone as parameters.
	 * <BR><contents of process><BR>
	 * 
	 * <B>1. in case of hard zone </B><BR>
	 * Create tje conditional SQL string for the range of hard zone.<BR>
	 * -WHSTATIONNUMBER:retrieve from st,<BR>
	 * -PRESENCE:Shelf.PRESENCE_EMPTY<BR>
	 * -STATUS:Station.STATION_OK<BR>
	 * -ACCESSNGFLAG:Shelf.ACCESS_OK<BR>
	 *Set above as conditions, then count the number of corresponding locations in SHELF table.<BR>
	 * <remark> in case of stand alone system, add PARENTSTATIONNUMBER to above conditions, then
	 *          count the number of locations.<BR>
	 *<BR><SQL example to execute><BR>
	 * <PRE>
	 *	SELECT COUNT(1) COUNT  
	 *	FROM SHELF
	 * 	WHERE WHSTATIONNUMBER = 9000 
	 *	AND HARDZONE IN( 2 , 3 )
	 *	AND PRESENCE = 0
	 *	AND STATUS   = 1
	 *	AND ACCESSNGFLAG = 0
	 * </PRE>
	 *<BR><B>2:in case of soft zone,</B><BR>
	 *Create tje conditional SQL string for the range of soft zone.<BR>
	 * -WHSTATIONNUMBER:retrieve from st,<BR>
	 * -PRESENCE:Shelf.PRESENCE_EMPTY<BR>
	 * -STATUS:Station.STATION_OK<BR>
	 * -ACCESSNGFLAG:Shelf.ACCESS_OK<BR>
	 *Set above as conditions, then count the number of corresponding locations in SHELF table.<BR>
	 *<remark> in case of stand alone system, add PARENTSTATIONNUMBER to above conditions, then
	 *          count the number of locations.<BR>
	 *<BR><SQL example to execute><BR>
	 * <PRE>
	 *	SELECT COUNT(1) COUNT  
	 *	FROM SHELF
	 *	WHERE WHSTATIONNUMBER = 9000 
	 *	AND PRESENCE = 0
	 *	AND STATUS   = 1 
	 *	AND ACCESSNGFLAG = 0 
	 *	AND NBAY >= 4 AND NBAY <= 19 AND NLEVEL >= 1 AND NLEVEL <= 10 
	 *	OR NBAY >= 10 AND NBAY <= 20 AND NLEVEL >= 20 AND NLEVEL <= 28
	 * </PRE>
 	 *
	 * @param  aisleSTNO :aisle station no.
	 * @param whSTNO     :warehouse station no.
	 * @param  zone		 :the array of zone instance
	 * @return           :number of empty locations
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system.
	 </en>*/
	public int countEmptyShelf(String aisleSTNO, String whSTNO, Zone[] zone) throws ReadWriteException
	{
		ResultSet countret  = null ;
		//#CM48099
		//<en>number of empty locations</en>
		int count = 0;
		//#CM48100
		//<en>SQL string for search</en>
		String sqlsting  = "";
		//#CM48101
		//<en>Create the conditional statement of zone range.</en>
		String zoneRangeString = "";
		try
		{
			//#CM48102
			//<en>Retrieve the aisle station no.</en>
			String aisleStNo = aisleSTNO;
			//#CM48103
			//<en>Retrieve the warehouse station no.</en>
			String whStNo = whSTNO;
			//#CM48104
			//<en>****** in case of hard zone, *******// </en>
 			if(zone[0].getType() == Zone.HARD)
			{
				String hardZoneIDbuf = "";
				//#CM48105
				//<en>Create the conditional statement of hard zone range.</en>

				for(int i = 0; i < zone.length; i++)
				{
					if(i == 0)
					{
						hardZoneIDbuf = Integer.toString(zone[0].getZoneID());
					}
					else
					{
						hardZoneIDbuf = hardZoneIDbuf + " , " + Integer.toString(zone[i].getZoneID());
					}
				}
				zoneRangeString = " AND HARDZONE IN ( " + hardZoneIDbuf + " ) " ;

				sqlsting = "SELECT COUNT(1) COUNT"
							+ " FROM " + wTableName
							+ " WHERE WHSTATIONNUMBER = " + whStNo
							+ zoneRangeString
							+ " AND PRESENCE = " + Shelf.PRESENCE_EMPTY
							+ " AND STATUS = " + Station.STATION_OK
							+ " AND ACCESSNGFLAG = " + Shelf.ACCESS_OK;

				//#CM48106
				//<en>**** in case of aisle stand alone system: add the aisle no. to the conditions.****//</en>

				if(aisleStNo != null && aisleStNo != "")
				{
					sqlsting = sqlsting + " AND PARENTSTATIONNUMBER = " + aisleStNo;
				}
				countret = executeSQL(sqlsting, true);

				while (countret.next())
				{
					count = countret.getInt("COUNT");
				}
				return count;
			}
			//#CM48107
			//<en>****** in case of soft zone, *******// </en>

			else
			{
				//#CM48108
				//<en>Create the conditional statement of soft zone range.</en>

				for(int i = 0; i < zone.length; i++)
				{
					//#CM48109
					//<en>set AND condition for the 1st search.</en>
					if(i == 0)
					{
						zoneRangeString 
									= " NBAY >= " + zone[0].getStartBay() 
									+ " AND NBAY <= " + zone[0].getEndBay()
									+ " AND NLEVEL >= " + zone[0].getStartLevel()
									+ " AND NLEVEL <= " + zone[0].getEndLevel();
					}
					//#CM48110
					//<en>set OR condition for 2nd search and on.</en>
					else
					{
						zoneRangeString = "(" + zoneRangeString
									+ " OR NBAY >= " + zone[i].getStartBay() 
									+ " AND NBAY <= " + zone[i].getEndBay()
									+ " AND NLEVEL >= " + zone[i].getStartLevel()
									+ " AND NLEVEL <= " + zone[i].getEndLevel() + ")";

					}
				}
				String zoneString = "";
				if(StringUtil.isBlank(zoneRangeString))
				{
					zoneString = "";
				}
				else
				{
					zoneString = " AND " + zoneRangeString;
				}

				sqlsting =  "SELECT COUNT(1) COUNT"
							+ " FROM " + wTableName
							+ " WHERE WHSTATIONNUMBER = " + whStNo 
							+ " AND PRESENCE = " + Shelf.PRESENCE_EMPTY
							+ " AND STATUS = " + Station.STATION_OK
							+ " AND ACCESSNGFLAG = " + Shelf.ACCESS_OK
							+ zoneString;
				//#CM48111
				//<en>**** in case of aisle stand alone system; add the aisle no. to the conditions. ****//</en>
				if(aisleStNo != null && aisleStNo != "")
				{
					sqlsting = sqlsting + " AND PARENTSTATIONNUMBER = " + aisleStNo;
				}
				countret = executeSQL(sqlsting, true);

				while (countret.next())
				{
					count = countret.getInt("COUNT");
				}
				closeStatement() ;
				return count;
			}
		}
		catch(SQLException e)
		{
			//#CM48112
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48113
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException());
		}
		catch(NotFoundException nfe)
		{
			return 0;
		}
		catch(DataExistsException dee)
		{
			return 0;
		}
	}

	//#CM48114
	// Package methods -----------------------------------------------

	//#CM48115
	// Protected methods ---------------------------------------------
	//#CM48116
	/**<en>
	 * Retrieve the information from the <code>Shelf</code> instane and set to the Object array
	 * as a string (<code>String</code>) .
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * For string type items(VARCHAR), enclose them with '(single quotations).
	 * @param tgtShelf :<code>Shelf</code> instance to retrieve data
	 * @return :Object array
	 * <p>
	 * The order of arrays should be as follows.<br>
	 * <pre>
	 * stationnumber	// 0
	 * bank				// 1
	 * bay				// 2
	 * level			// 3
	 * warehouse		// 4
	 * status			// 5
	 * presence			// 6
	 * hardzone			// 7
	 * softzone			// 8
	 * parentstationid	// 9
	 * accessngflag		// 10
	 * </pre></p>
	 </en>*/
	/**<en>
	 * Retrieve the information from the <code>Shelf</code> instane and set to the Object array
	 * as a string (<code>String</code>) .
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * For string type items(VARCHAR), enclose them with '(single quotations).
	 * @param tgtShelf :<code>Shelf</code> instance to retrieve data
	 * @return :Object array
	 * <p>
	 * The order of arrays should be as follows.<br>
	 * <pre>
	 * stationnumber	// 0
	 * bank				// 1
	 * bay				// 2
	 * level			// 3
	 * warehouse		// 4
	 * status			// 5
	 * presence			// 6
	 * hardzone			// 7
	 * softzone			// 8
	 * parentstationid	// 9
	 * accessngflag		// 10
	 * </pre></p>
	 </en>*/
	protected Object[] setToArray(Shelf tgtShelf)
	{
		Object[] fmtObj = new Object[14] ;
		//#CM48117
		// set parameters
		//#CM48118
		// station number
		fmtObj[0] = DBFormat.format(tgtShelf.getNumber()) ;
		//#CM48119
		// bank
		fmtObj[1] = Integer.toString(tgtShelf.getBank()) ;
		//#CM48120
		// bay
		fmtObj[2] = Integer.toString(tgtShelf.getBay()) ;
		//#CM48121
		// level
		fmtObj[3] = Integer.toString(tgtShelf.getLevel()) ;
		//#CM48122
		// parentstationid
		fmtObj[4] = DBFormat.format(tgtShelf.getWarehouseStationNumber()) ;
		//#CM48123
		// status
		fmtObj[5] = Integer.toString(tgtShelf.getStatus()) ;
		//#CM48124
		// presence
		fmtObj[6] = Integer.toString(tgtShelf.getPresence()) ;
		//#CM48125
		// hardzone
		fmtObj[7] = Integer.toString(tgtShelf.getHardZone()) ;
		//#CM48126
		// softzone
		fmtObj[8] = Integer.toString(tgtShelf.getSoftZone()) ;
		//#CM48127
		// parentstationid
		fmtObj[9] = DBFormat.format(tgtShelf.getParentStationNumber()) ;
		//#CM48128
		// accsess Status
		if (tgtShelf.isAccessNgFlag())
		{
			fmtObj[10] = Integer.toString(Shelf.ACCESS_NG) ;
		}
		else
		{
			fmtObj[10] = Integer.toString(Shelf.ACCESS_OK) ;
		}
		//#CM48129
		// priority
		fmtObj[11] = Integer.toString(tgtShelf.getPriority()) ;
		//#CM48130
		// pairstationnumber
		fmtObj[12] = DBFormat.format(tgtShelf.getPairStationNumber()) ;
		//#CM48131
		// side
		fmtObj[13] = Integer.toString(tgtShelf.getSide()) ;

		return (fmtObj) ;
	}

	//#CM48132
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>Shelf</code> instance.
	 * @param rset :result set of SHELF table search
	 * @return :the array of the generated Shelf instance
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	protected Shelf[] makeShelf(ResultSet rset) throws ReadWriteException
	{
		return makeShelf(rset, 0);
	}

	//#CM48133
	/**<en>
	 * Generate the array of <code>Shelf</code> instance based on the <code>ResultSet</code>.
	 * @param rset :result set of teh SHELF table search
	 * @param maxcreate :number of Shelf instance to generate; if it is 0, it should generate 
	 * instances of all result set.
	 * @return :the array of the generated Shelf instance
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	protected Shelf[] makeShelf(ResultSet rset, int maxcreate) throws ReadWriteException
	{
		Vector tmpShelfVect = new Vector() ; // temporary store for Station instances

		//#CM48134
		// data get from resultset and make new Station instance
		try
		{
			int count = 0;
			while(rset.next())
			{
				if ((maxcreate != 0) && (count > maxcreate))
				{
					//#CM48135
					//<en> Exits loop when exceeded the upper limit of instance generation.</en>
					break;
				}
				//#CM48136
				//<en> Generate the Shelf instance.</en>
				Shelf tmpShelf = new Shelf(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				setShelf(rset, tmpShelf);
				tmpShelfVect.add(tmpShelf) ;

				//#CM48137
				// count up
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
			//#CM48138
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolShelfHandler" ) ;
			throw (new ReadWriteException("6126001" + wDelim +"Shelf")) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM48139
		// move instance from vector to array of Station
		Shelf[] rstarr = new Shelf[tmpShelfVect.size()] ;
		tmpShelfVect.copyInto(rstarr);

		return (rstarr) ;
	}

	//#CM48140
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and set each item of <code>Shelf</code> instance.
	 * @param rset :result set of the SHELF table search
	 * @param tmpShelf :Shelf instance to set
	 * @return :Shelf instance to generate
	 * @throws SQLException :Notifies if error occured in connection with database.
	 </en>*/
	protected Shelf setShelf(ResultSet rset, Shelf tmpShelf) throws SQLException, InvalidStatusException
	{
		//#CM48141
		// bank
		tmpShelf.setBank(rset.getInt("NBANK")) ;
		//#CM48142
		// bay
		tmpShelf.setBay(rset.getInt("NBAY")) ;
		//#CM48143
		// level
		tmpShelf.setLevel(rset.getInt("NLEVEL")) ;
		//#CM48144
		// warehouse
		tmpShelf.setWarehouseStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
		//#CM48145
		// station status
		tmpShelf.setStatus(rset.getInt("STATUS")) ;
		//#CM48146
		// presence
		tmpShelf.setPresence(rset.getInt("PRESENCE")) ;
		//#CM48147
		// hardzone
		tmpShelf.setHardZone(rset.getInt("HARDZONEID")) ;
		//#CM48148
		// softzone
		tmpShelf.setSoftZone(rset.getInt("SOFTZONEID")) ;
		//#CM48149
		// parent station
		tmpShelf.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER"))) ;
		//#CM48150
		//<en> aile station :in future the use of ParentStationNumber should be discontinued and </en>
		//<en>               be replaced by AISLESTATIONNUMBER.</en>
		tmpShelf.setAisleStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER"))) ;
		//#CM48151
		// accsess ng flag
		boolean acs = (rset.getInt("ACCESSNGFLAG") == Shelf.ACCESS_NG) ;
		tmpShelf.setAccessNgFlag(acs) ;
		//#CM48152
		// side
		tmpShelf.setSide(rset.getInt("SIDE")) ;
		//#CM48153
		// priority
		tmpShelf.setPriority(rset.getInt("PRIORITY")) ;
		//#CM48154
		// pairstationnumber
		tmpShelf.setPairStationNumber(DBFormat.replace(rset.getString("PAIRSTATIONNUMBER")));
		//#CM48155
		// station handler
		tmpShelf.setHandler(this) ;

		return tmpShelf;
	}

	//#CM48156
	// Private methods -----------------------------------------------
}
//#CM48157
//end of class


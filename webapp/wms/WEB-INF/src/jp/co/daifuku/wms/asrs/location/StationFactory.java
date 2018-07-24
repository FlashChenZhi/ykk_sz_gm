// $Id: StationFactory.java,v 1.2 2006/10/26 08:32:51 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM43011
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.dbhandler.ASLocationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseHandler;
import jp.co.daifuku.wms.base.entity.Station;

//#CM43012
/**<en>
 * This class generates the Station classes and their subclasses.
 * In this class, they are generated without distinguishing between Station and subclass.
 * If directly generating each station subclass, please use respective Handler classes.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:32:51 $
 * @author  $Author: suresh $
 </en>*/
public class StationFactory extends Object
{
	//#CM43013
	// Class fields --------------------------------------------------

	//#CM43014
	// Class variables -----------------------------------------------
	//#CM43015
	/**<en>
	 * Connection with database
	 </en>*/
	protected Connection wConn ;

	protected Statement wStatement ;

	//#CM43016
	/**<en>
	 * This is in LogWrite when Exception occured. 
	 </en>*/
	public StringWriter wSW = new StringWriter();

	//#CM43017
	/**<en>
	 * This is in LogWrite when Exception occured. 
	 </en>*/
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM43018
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM43019
	// Class method --------------------------------------------------
	//#CM43020
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:32:51 $") ;
	}
	
	//#CM43021
	/**<en>
	 * Generates the Station instance from the station no.
	 * It may return the subclass dpending on the type of corresponding station.
	 * This will be used to obtain the instance of the station which the direct instantiation of its subclass
	 * is not possible. Or rather when it is not clear which subclass should be used to work with.
	 * If corresponding station cannot be found, it returns null pointer.
	 * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
	 * @param conn  :<code>Connection</code> to connect with database.<BR>
	 * @param snum  :station no.
	 * @return :instance of Station (or its subclass)
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 * @throws InvalidDefineException :Notifies if the definition of the class was incorrect.
	 * @throws ReadWriteException     :Notifies if error occured in connection with database.
	 * @throws NotFoundException   :Notifies if such class was not found.
	 </en>*/
	public static Station makeStation(Connection conn, String snum) throws SQLException, InvalidDefineException, ReadWriteException, NotFoundException
	{
		StationFactory sf = new StationFactory(conn) ;
		return (sf.getStation(snum)) ;
	}

	//#CM43022
	// Constructors --------------------------------------------------
	//#CM43023
	/**<en>
	 * Creates an instance by specifying the connection with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public StationFactory(Connection conn)
	{
		setConnection(conn) ;
	}

	//#CM43024
	// Public methods ------------------------------------------------

	//#CM43025
	// Package methods -----------------------------------------------

	//#CM43026
	// Protected methods ---------------------------------------------
	//#CM43027
	/**<en>
	 * Gets Station instance from the station no.
	 * @param  snum :station no. to create
	 * @return :Station instance obtained
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 * @throws InvalidDefineException :Notifies when definition of the class was incorrect.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if such class was not found.
	 </en>*/
	protected Station getStation(String snum) throws SQLException, InvalidDefineException, ReadWriteException, NotFoundException
	{
		//#CM43028
		//-------------------------------------------------
		//#CM43029
		//<en> variable define</en>
		//#CM43030
		//-------------------------------------------------
		String fmtSQL = "SELECT * FROM stationtype WHERE stationnumber = {0}" ;

		//#CM43031
		//<en> Retrieval key making</en>
		ASLocationSearchKey sskey = new ASLocationSearchKey();
		sskey.setStationNumber(snum);

		Station tgtStation = null;
		Object[] stobj = null;

		ResultSet rset = null ;
		//#CM43032
		//-------------------------------------------------
		//#CM43033
		//<en> process start</en>
		//#CM43034
		//-------------------------------------------------
		//#CM43035
		//-------------------------------------------------
		//#CM43036
		//<en> create sql</en>
		//#CM43037
		//-------------------------------------------------
		Object[] fmtObj = new Object[1] ;
		fmtObj[0] = "'"+ snum +"'" ;
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			rset = executeSQL(sqlstring) ;

			if (rset.next() == false)
			{
				//#CM43038
				//<en> There is no corresponding data in STATIONTYPE table.</en>
				//#CM43039
				//<en> 6026044=Station No.{0} is not found in table {1}.</en>
				Object[] tObj = new Object[2] ;
				tObj[0] = ":"+snum+":";
				tObj[1] = "STATIONTYPE";
				RmiMsgLogClient.write(6026044, LogMessage.F_ERROR, "StationFactory", tObj);
				throw (new NotFoundException("6026044" + wDelim + tObj[0] + wDelim + tObj[1])) ;
			}

			DatabaseHandler handler = makeHandlerInstance(DBFormat.replace(rset.getString("handlerclass"))) ;
			rset.close();

			//#CM43040
			//<en> Finding station (by station id)</en>
			stobj = handler.find(sskey) ;
		}
		catch (SQLException se)
		{
			se.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = wSW.toString() ;
			//#CM43041
			//<en> 6007030=Database error occured. error code={0}</en>
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "StationFactory", tObj);
			throw (new SQLException("6007030" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (wStatement != null) { wStatement.close(); }
			}
			catch(SQLException se)
			{
				se.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = wSW.toString() ;
				//#CM43042
				//<en> 6007030=Database error occured. error code={0}</en>
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "StationFactory", tObj);
				throw (new SQLException("6007030" + wDelim + tObj[0])) ;
			}
		}
		
		//#CM43043
		//<en> Exception if teh station was not found by Number specified.</en>
		if (stobj.length == 0)
		{
			//#CM43044
			//<en> There is no objective data.</en>
			//#CM43045
			//<en> 6026044=Station No.{0} is not found in table {1}.</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = snum;
			tObj[1] = " ";
			RmiMsgLogClient.write(6026044, LogMessage.F_ERROR, "StationFactory", tObj);
			throw (new NotFoundException("6026044" + wDelim + tObj[0] + wDelim + tObj[1])) ;
		}
		
		//#CM43046
		//<en> Exception if more than one station is found.</en>
		if (stobj.length != 1)
		{
			//#CM43047
			//<en> 2 or more stations with identical IDs exist. StationID={0}</en>
			//#CM43048
			//<en> 6026031=Multiple stations with the same ID exist. StationID=({0})</en>
			Object[] tObj = new Object[1] ;
			tObj[0] = snum;
			RmiMsgLogClient.write(6026031, LogMessage.F_ERROR, "StationFactory", tObj);
			throw (new InvalidDefineException("6026031" + wDelim + tObj[0])) ;
		}
		
		//#CM43049
		//<en> Error if anything other than station returned.</en>
		if (stobj[0] instanceof Station)
		{
			tgtStation = (Station)stobj[0] ;
		}
		else
		{
			Object[] tObj = new Object[1] ;
			tObj[0] = "Station";
			//#CM43050
			//<en> 6006008=Object other than {0} was returned.</en>
			RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "StationFactory", tObj);
			throw (new InvalidDefineException("6006008" + wDelim + tObj[0])) ;
		}

		return(tgtStation) ;
	}

	//#CM43051
	/**<en>
	 * Generate the instance of <code>DatabaseHandler</code> according to the name of the class and return.
	 * @param classname :name of the class of which the instance should generate
	 * <p>Example:<br>
	 * "jp.co.daifuku.awc.dbhandler.StationHandler"
	 * </p>
	 * @return <code>DatabaseHandler</code>
	 * @throws InvalidDefineException :Notifies if any instance other than DatabaseHandler was generated.
	 * @throws InvalidDefineException :Notifies if generation of the instance failed.
	 </en>*/
	protected DatabaseHandler makeHandlerInstance(String classname) throws InvalidDefineException
	{
		try
		{
			//#CM43052
			//<en> load class</en>
			Class lclass = Class.forName(classname) ;

			//#CM43053
			//<en>The parameter type is set. </en>
			//#CM43054
			//<en> set parameter types</en>
			Class[] typeparams = new Class[1] ;
			typeparams[0] = Class.forName("java.sql.Connection") ;
			Constructor cconst = lclass.getConstructor(typeparams) ;

			//#CM43055
			//<en> set actual parameter</en>
			Object[] tparams = new Object[1] ;
			tparams[0] = wConn ;

			//#CM43056
			//<en> getting Object</en>
			Object tgt = cconst.newInstance(tparams) ;
			if (tgt instanceof DatabaseHandler)
			{
				return ((DatabaseHandler)tgt) ;
			}
			else
			{
				Object[] tObj = new Object[1] ;
				tObj[0] = "DatabaseHandler" ;
				//#CM43057
				//<en> 6006008=Object other than {0} was returned.</en>
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "StationFactory", tObj);
				throw (new InvalidDefineException("6006008" + wDelim + tObj[0])) ;
			}
		}
		catch (Exception e)
		{
			//#CM43058
			//<en>Output of error log.</en>
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = classname ;
			tObj[1] = wSW.toString() ;
			//#CM43059
			//<en> 6006003=Failed to generate the instance. Class name={0} {1}</en>
			RmiMsgLogClient.write(6006003, LogMessage.F_ERROR, "StationFactory", tObj);
			throw (new InvalidDefineException("6006003" + wDelim + tObj[0])) ;
		}
	}

	//#CM43060
	/**<en>
	 * Carry out the SQL statement.
	 * @param sqlstring SQL statement. It must be a statement of inquiry style in order to return result.
	 * @return :ResultSet of the result
	 * @throws SQLException : Notifies if database error occurrs .
	 </en>*/
	protected ResultSet executeSQL(String sqlstring) throws SQLException
	{
		wStatement = wConn.createStatement() ;
		ResultSet rset = wStatement.executeQuery(sqlstring) ;
		return (rset) ;
	}

	//#CM43061
	// Private methods --------------------------------------------------------------
	//#CM43062
	/**<en>
	 * Sets the database connection.
	 * @param conn :database connection
	 </en>*/
	private void setConnection(Connection conn)
	{
		wConn = conn;
	}
}
//#CM43063
//end of class


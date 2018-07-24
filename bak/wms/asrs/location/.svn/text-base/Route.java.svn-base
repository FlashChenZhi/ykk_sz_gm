// $Id: Route.java,v 1.2 2006/10/26 08:36:15 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42616
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
//#CM42617
/**<en>
 * This class preserves the carry route information.
 * The definition of carry route is stored in resource file. If the route information which have been 
 * already defined is needed, please do not directly create an instance but use <code>getInstance</code> method.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:36:15 $
 * @author  $Author: suresh $
 </en>*/
public class Route extends Object
{
	//#CM42618
	// Class fields --------------------------------------------------
	//#CM42619
	/**<en>
	 * Field of route status - <code>ACTIVE</code> :available
	 </en>*/
	public static final int ACTIVE = 1 ;

	//#CM42620
	/**<en>
	 * Field of route status- <code>NOT_ACTIVE_OFFLINE</code>
	 * :Not available due to Equipments off-line.
	 </en>*/
	public static final int NOT_ACTIVE_OFFLINE = 0 ;

	//#CM42621
	/**<en>
	 * Field of route status - <code>NOT_ACTIVE_FAIL</code>
	 * : Unavailable due to Equipment error
	 </en>*/
	public static final int NOT_ACTIVE_FAIL = 2 ;

	//#CM42622
	/**<en>
	 * Field of route status - <code>UNKNOWN</code>
	 * : Route check undone
	 </en>*/
	public static final int UNKNOWN = -1 ;

	//#CM42623
	/**<en>
	 * Field of route status - <code>NOTFOUND</code>
	 * : The specified route definition cannot be found.
	 </en>*/
	public static final int NOTFOUND = -2 ;

	//#CM42624
	// Class variables -----------------------------------------------
	//#CM42625
	/**<en>
	 * Connection with database
	 </en>*/
	private Connection wConn ;

	//#CM42626
	/**<en>
	 * Variable which preserves the result of route check
	 </en>*/
	private boolean wStatus = false;

	//#CM42627
	/**<en>
	 * Variable which preserves the result of route check
	 </en>*/
	private int wRouteStatus = UNKNOWN;

	//#CM42628
	/**<en>
	 * Variable which preserves stations which compose the route
	 </en>*/
	private Station[] wStations ;

	//#CM42629
	/**<en>
	 * The station no. which starts the route
	 </en>*/
	private String wStartStationNumber ;

	//#CM42630
	/**<en>
	 * The station no. which is the end of the route
	 </en>*/
	private String wEndStationNumber ;

	//#CM42631
	/**<en>
	 * LineNumberReader to read the route file
	 </en>*/
	private LineNumberReader wRouteFileReader ;

	//#CM42632
	/**<en>
	 * Delimiter
	 * Delimiter of parameter in MessageDef when Exception occurs.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM42633
	// Class method --------------------------------------------------
	//#CM42634
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:36:15 $") ;
	}

	//#CM42635
	/**<en>
	 * Based on the station no., return the previous station to which this station is connected. 
	 * If more than 2 previous stations are defined, return one which was firstly found.
	 * @param toStnum :station no. to be searched
	 * @return :previous station which toStnum is connected to
	 * @throws IOException  : Notifies if file I/O error occured.
	 </en>*/
	public static String getConnectorStation(String toStnum) throws IOException
	{
		//#CM42636
		//<en> This is used in LogWrite when Exception occurs.</en>
		StringWriter wSW = new StringWriter();  
		//#CM42637
		//<en> This is used in LogWrite when Exception occurs.</en>
		PrintWriter  wPW = new PrintWriter(wSW); 
		//#CM42638
		//<en> delimiter</en>
		String wDelim = MessageResource.DELIM ;  
		RoutePiece rp;

		try
		{
			rp = new RoutePiece(new LineNumberReader(new FileReader(AsrsParam.ROUTE_FILE)), toStnum) ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace(wPW) ;
			//#CM42639
			//<en> 6026030=Route definition file read error. Detail=({0})</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = ioe.getMessage();
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6026030, LogMessage.F_ERROR, "Route", tObj);
			throw (new IOException("6026030" + wDelim + tObj[0])) ;
		}
		
		return rp.getConnectorStation();
	}

	//#CM42640
	/**<en>
	 * Based on the specified statin no., it returns the move-destinated station that this station is connected to.
	 * This method wil be used when searching the storage station to be paired for free station of U-shaped conveyor.
	 * @param toStnum :station no. to be searched
	 * @return :station no. of move-destination, to be a pair with toStnum.
	 * @throws IOException  : Notifies if file I/O error occured.
	 </en>*/
	public static String getConnectorStationTo(String toStnum) throws IOException
	{
		//#CM42641
		//<en> This is used in LogWrite when Exception occurs.</en>
		StringWriter wSW = new StringWriter();
		//#CM42642
		//<en> This is used in LogWrite when Exception occurs.</en>
		PrintWriter  wPW = new PrintWriter(wSW); 
		//#CM42643
		//<en> delimiter</en>
		String wDelim = MessageResource.DELIM ;
		RoutePiece rp;

		try
		{
			rp = new RoutePiece(new LineNumberReader(new FileReader(AsrsParam.ROUTE_FILE)), toStnum) ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace(wPW) ;
			//#CM42644
			//<en> 6026030=Route definition file read error. Detail=({0})</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = ioe.getMessage();
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6026030, LogMessage.F_ERROR, "Route", tObj);
			throw (new IOException("6026030" + wDelim + tObj[0])) ;
		}
		
		return rp.getConnectorStationTo();
	}

	//#CM42645
	/**<en>
	 * Create the instance of <code>Route</code> based on the From,To of station no., and return.
	 * @param con :database connection in order to generate the station instance
	 * @param :previoud station of from <code>Route</code>(String type)
	 * @param :next station of to <code>Route</code>(String type)
	 * @return :<code>Route</code> object created according to paramter
	 </en>*/
	public static Route getInstance(Connection con, String from, String to) throws IOException
	{
		//#CM42646
		//<en> This is used in LogWrite when Exception occurs.</en>
		StringWriter wSW = new StringWriter();
		//#CM42647
		//<en> This is used in LogWrite when Exception occurs.</en>
		PrintWriter  wPW = new PrintWriter(wSW);
		//#CM42648
		//<en> delimiter</en>
		String wDelim = MessageResource.DELIM ;

		Route nRoute = new Route(con) ;
		nRoute.setStartStationNumber(from) ;
		nRoute.setEndStationNumber(to) ;

		try
		{
			nRoute.setLineNumberReader(new LineNumberReader(new FileReader(AsrsParam.ROUTE_FILE))) ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace(wPW) ;
			//#CM42649
			//<en> 6026030=Route definition file read error. Detail=({0})</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = ioe.getMessage();
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6026030, LogMessage.F_ERROR, "Route", tObj);
			throw (new IOException("6026030" + wDelim + tObj[0])) ;
		}
		
		return(nRoute) ;
	}

	//#CM42650
	/**<en>
	 * Generate the instance of <code>Route</code> based on the From,To of the station, and return.
	 * @param con :database connection in order to generate the station instance.
	 * @param :previous station of from <code>Route</code>(Station type)
	 * @param :next station of to <code>Route</code>(Station type)
	 * @return : object of <code>Route</code> created according to parameter
	 </en>*/
	public static Route getInstance(Connection con, Station from, Station to) throws IOException
	{
		Route nRoute = getInstance(con, from.getStationNumber(), to.getStationNumber()) ;
		return(nRoute) ;
	}

	//#CM42651
	/**<en>
	 * Creates the instance of <code>Route</code> based on the From,To of the station, and return.
	 * @param con :database connection in order to generate the station instance
	 * @param : previous station of from <code>Route</code>(Station type)
	 * @param :next station of to <code>Route</code>(String type)
	 * @return :object of <code>Route</code> created according to parameter
	 </en>*/
	public static Route getInstance(Connection con, Station from, String to) throws IOException
	{
		Route nRoute = getInstance(con, from.getStationNumber(), to) ;
		return(nRoute) ;
	}

	//#CM42652
	// Constructors --------------------------------------------------
	//#CM42653
	/**<en>
	 * Generates the instance which has an empty carry route. If the instance which has already
	 * defined carry route is needed, please use <code>getInstance</code> method.
	 * @param conn :database connection in order to generate the station instance
	 </en>*/
	public Route(Connection conn)
	{
		wConn = conn ;
	}

	//#CM42654
	// Public methods ------------------------------------------------
	//#CM42655
	/**<en>
	 * Checks the route status. It returns 'true' if available and 'false' if unavailable.
	 * It also records the status of the route (normal, of-line, error).
	 * One can get a reference for the detail in <code>getRouteStatus()</code>.
	 * @return    :status of the route
	 * @throws InvalidDefineException :Notifies if the station defined in the carry route was not found.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ReadWriteException :Notifies if error occured in reading the carry route definition.
	 </en>*/
	public boolean check() throws InvalidDefineException, ReadWriteException
	{
		//#CM42656
		//<en> Checks the route; it returns 'true' if is is correct and 'false' if not.</en>
		switch (checkStatus())
		{
			case ACTIVE:
				wRouteStatus = ACTIVE;
				wStatus = true;
				break;
			case NOT_ACTIVE_OFFLINE:
				wRouteStatus = NOT_ACTIVE_OFFLINE;
				wStatus = false;
				break;
			case NOT_ACTIVE_FAIL:
				wRouteStatus = NOT_ACTIVE_FAIL;
				wStatus = false;
				break;
			case NOTFOUND:
				wRouteStatus = NOTFOUND;
				wStatus = false;
				break;
			default:
				wRouteStatus = UNKNOWN;
				wStatus = false;
				break;
		}
		return wStatus;
	}

	//#CM42657
	/**<en>
	 * Returns whether/not these 2 stations are directly connected.
	 * @return  :True if directly connected.
	 * @throws ReadWriteException :Notifies if error occured in reading the carry route definition.
	 </en>*/
	public boolean isDirect() throws ReadWriteException
	{
		try
		{
			RoutePiece rp = new RoutePiece(wRouteFileReader, wStartStationNumber) ;
			String[] stations = rp.getConnectedStations() ;
			if (stations != null)
			{
				for (int i=0; i < stations.length; i++)
				{
					if (stations[i].equals(wEndStationNumber))
					{
						return (true) ;
					}
				}
			}
			return (false) ;
		}
		catch (IOException ioe)
		{
			//#CM42658
			// From RoutePiece
			throw (new ReadWriteException(ioe.getMessage())) ;
		}
	}

	//#CM42659
	/**<en>
	 * Returns the status of route (error, off-line, etc.) which have been set as a result of route check.<BR>
	 * If the route check is not done, it returns <code>UNKNOWN</code>.<BR>
	 * In order to retrieve the valid value by calling method, it is alos necessary to call <code>check</code>.<BR>
	 * @return :result of route check. If the route check is not done, it returns <code>UNKNOWN</code>.
	 </en>*/
	public int getRouteStatus()
	{
		return wRouteStatus;
	}

	//#CM42660
	/**<en>
	 * Returns the result of route check process that this instance preserves.<BR>
	 * This will be used when retrieving the check results again without checking the route.
	 * In order to retrieve the valid value by calling method, it is alos necessary to call <code>check</code>.<BR>
	 * @return :result of route check. It retunrs 'true' if the route is available, or 'false' if unavailable.
	 * It returns 'false' if route check is not done.
	 </en>*/
	public boolean getStatus()
	{
		return wStatus;
	}

	//#CM42661
	// Package methods -----------------------------------------------

	//#CM42662
	// Protected methods ---------------------------------------------
	//#CM42663
	/**<en>
	 * Retrieves the status of the route. The list of status is defined as a field of this class.
	 * @return :status of the route
	 * @throws InvalidDefineException :Notifies if the station defined in the carry route was not found.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws ReadWriteException :Notifies if error occured in reading the carry route definition.
	 </en>*/
	protected int checkStatus() throws InvalidDefineException, ReadWriteException
	{
		try
		{
			//#CM42664
			//<en> Retrieving the list of station.(to be retrieved evey time, as the latest information is required)</en>
			Vector stVect = new Vector(10) ;
			RoutePiece rp = new RoutePiece(wRouteFileReader, wStartStationNumber) ;
			if (!rp.exists(wEndStationNumber, new Vector(10), stVect))
			{
				//#CM42665
				//<en> The specified carry route does not exist in the definition.</en>
				return NOTFOUND;
			}
			
			//#CM42666
			//<en> As RoutePiece instance does not return tje receiving station, add.</en>
			stVect.insertElementAt(wEndStationNumber, 0);

			wStations = new Station[stVect.size()] ;
			if (wStations.length < 1)
			{
				//#CM42667
				//<en> There is no station information for the route check.</en>
				return NOTFOUND;
			}
			
			for (int i = 0 ; i < wStations.length ; i++)
			{
				String st = (String)(stVect.remove(0)) ;
				wStations[i] = StationFactory.makeStation(wConn, st) ;
			}
			
			//#CM42668
			//<en> Checking the status of station</en>
			//<en> If error or off-line is included in the status of the station, record them </en>
			//<en> with a priority for off-line.</en>
			//#CM42669
			//<en>It gives priority to cutting off when abnormality and cutting off are included in the state of the CMENJP4018$CM station and it memorizes it.</en>
		int rstat = ACTIVE ;
			for (int i = 0 ; i < wStations.length ; i++)
			{
				int sts = wStations[i].getStatus() ;
				switch (sts)
				{
					case Station.STATION_FAIL :
						if (rstat != NOT_ACTIVE_OFFLINE) rstat = NOT_ACTIVE_FAIL;
						break ;
						
					case Station.STATION_NG :
						rstat = NOT_ACTIVE_OFFLINE ;
						break ;
				}
			}
			
			return rstat ;
		}
		catch (IOException ioe)
		{
			//#CM42670
			//<en> From RoutePiece</en>
			throw (new ReadWriteException(ioe.getMessage())) ;
		}
		catch (SQLException se)
		{
			//#CM42671
			//<en> From StationFactory</en>
			throw (new ReadWriteException(se.getMessage())) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM42672
			//<en> From StationFactory</en>
			throw (new InvalidDefineException(nfe.getMessage())) ;
		}
	}

	//#CM42673
	// Private methods -----------------------------------------------
	//#CM42674
	/**<en>
	 * Sets the station no. from which the route to start.
	 * @param sst :station no. the route starts from
	 </en>*/
	private void setStartStationNumber(String sst)
	{
		wStartStationNumber = sst ;
	}

	//#CM42675
	/**<en>
	 * Sets the station no. which the route ends at
	 * @param est :station no. the route ends by
	 </en>*/
	private void setEndStationNumber(String est)
	{
		wEndStationNumber = est ;
	}

	//#CM42676
	/**<en>
	 * Sets <code>LineNumberReader</code> in order to read the route file.
	 * @param lnr LineNumberReader
	 </en>*/
	private void setLineNumberReader(LineNumberReader lnr)
	{
		wRouteFileReader = lnr ;
	}
}
//#CM42677
//end of class

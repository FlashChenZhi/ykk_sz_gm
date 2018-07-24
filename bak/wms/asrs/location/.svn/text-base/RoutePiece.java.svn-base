// $Id: RoutePiece.java,v 1.2 2006/10/26 08:34:15 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42909
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;

//#CM42910
/**<en>
 * This class preserves the relations between individual stations for the route checks.
 * LineNumberReader is used for the access to definition file. Opening and closdure of 
 * the file is not implemented in this class.
 * <p><pre>example pf LineNumberReader:
 * LineNumberReader nr = new LineNumberReader(new FileReader("routedef.txt")) ;</pre></p>
 * <p>Format of the definition file is as follows.</p>
 * <p>FROM:TOST1,TOST2,TOST3</p>
 * <p>FROM is a defining station; it rows stations which are connected to FROM, delimiting 
 * respective station with ",".
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:34:15 $
 * @author  $Author: suresh $
 </en>*/
public class RoutePiece extends Object
{
	//#CM42911
	// Class fields --------------------------------------------------
	//#CM42912
	/**<en>
	 * Delimiter in the file (between defined station and connected station)
	 </en>*/
	public static final String DELM_DEFINEST = ":" ;
	//#CM42913
	/**<en>
	 * Delimiter in the file (row of connected stations)
	 </en>*/
	public static final String DELM_CONNECTST = "," ;
	//#CM42914
	/**<en>
	 * Comment characters in the file
	 </en>*/
	public static final char COMMENT = '#' ;

	//#CM42915
	// Class variables -----------------------------------------------
	//#CM42916
	/**<en>
	 * Preserves information of definition file.
	 </en>*/
	protected LineNumberReader wDefineFile ;
	//#CM42917
	/**<en>
	 * The station no. of the base station
	 </en>*/
	protected String wMyStationNumber ;
	//#CM42918
	/**<en>
	 * List of connected station
	 </en>*/
	protected Vector wConnectedStations ;
	//#CM42919
	/**<en>
	 * List of checked station no. 
	 </en>*/
	protected Vector wCheckedStations ;

	//#CM42920
	/**<en>
	 * This is in LogWrite when Exception occured. 
	 </en>*/
	public StringWriter wSW = new StringWriter();

	//#CM42921
	/**<en>
	 * This is in LogWrite when Exception occured. 
	 </en>*/
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM42922
	/**<en>
	 * Delimiter
	 * This is delimiter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM42923
	// Class method --------------------------------------------------
	//#CM42924
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:34:15 $") ;
	}

	//#CM42925
	// Constructors --------------------------------------------------
	//#CM42926
	/**<en>
	 * Generates an instance by specifing instance of <code>LineNumberReader</code> in order
	 * to read the definition file and the base station no.  
	 * In order to secure the performance, the opening/closure of the definition file is not implemented.
	 * @param fr     :<code>LineNumberReader</code> used to read the definition file
	 * @param stnum  :station no. of the base station
	 </en>*/
	public RoutePiece(LineNumberReader fr, String stnum)
	{
		wDefineFile = fr ;
		wMyStationNumber = stnum ;
		wConnectedStations = new Vector(10) ;
	}

	//#CM42927
	// Public methods ------------------------------------------------
	//#CM42928
	/**<en>
	 * Check to see if target station is included in the connected stations.
	 * It avoids the check overlap by providing station no. that check is already done.
	 * If target station is not directly connected, it recursively checks its presence.
	 * @param tgt  :station no. subject to search
	 * @param chkd :list of checked station no.
	 * @param result  :if corrensponding station is held, this station will be added. 
	 * @return     :true if it holds the corrensponding station.
	 * @throws IOException            : Notifies if file I/O error occured.
	 </en>*/
	public boolean exists(String tgt, Vector chkd, Vector result) throws IOException
	{
		Object wkSt ;
		//#CM42929
		//<en> Reads the definition file if it has not done the reading.</en>
		if (wConnectedStations.isEmpty())
		{
			loadConnectedStations(wConnectedStations) ;
		}

		//#CM42930
		//<en> Add the own self station to the searched stations.</en>
		chkd.add(wMyStationNumber) ;

		//#CM42931
		//<en> Check whether/not the target is included in defined station.</en>
		//#CM42932
		//<en> If included, add own self station to the result and return.</en>
		if (wConnectedStations.indexOf(tgt) >= 0)
		{
			result.add(wMyStationNumber) ;
			return (true) ;
		}

		//#CM42933
		//<en> Check the defined stations respectively.</en>
		int ccsCount = wConnectedStations.size() ;
		for (int i=0; i < ccsCount; i++)
		{
			wkSt = wConnectedStations.remove(0) ;
			//#CM42934
			//<en> Compare with searched stations, and if the station has not searched yet, check it further.</en>
			if (chkd.indexOf(wkSt) < 0)
			{
				//#CM42935
				//<en> Create the RoutePiece object, and search to see if there is the target.</en>
				RoutePiece wrp = new RoutePiece(wDefineFile, (String)wkSt) ;
				if (wrp.exists(tgt, chkd, result))
				{
					//#CM42936
					//<en> If the target is found, add the target station to the result and return.</en>
					result.add(wMyStationNumber) ;
					return (true) ;
				}
			}
		}
		
		return(false) ;
	}

	//#CM42937
	/**<en>
	 * Returns a list of connected stations.
	 * @return :It returns null if there is no station connected.
	 * @throws IOException            : Notifies if file I/O error occured.
	 </en>*/
	public String[] getConnectedStations() throws IOException
	{
		Vector snv = new Vector(10) ;
		if (loadConnectedStations(snv))
		{
			String[] rst = new String[snv.size()] ;
			for (int i=0; i < rst.length; i++)
			{
				rst[i] = snv.remove(0).toString() ;
			}
			return (rst) ;
		}
		else
		{
			return (null) ;
		}
	}

	//#CM42938
	/**<en>
	 * Returns stations connected with this base station specified.
	 * This will be used when searching for storage staion pairing the free station of U-shaped conveyor.
	 * @return station no. (next station the carry moves to)
	 * @throws IOException            : Notifies if file I/O error occured.
	 </en>*/
	public String getConnectorStationTo() throws IOException
	{
		try
		{
			//#CM42939
			//<en> Regular files are mark-supported. Marks in start positions.</en>
			wDefineFile.mark(10240) ;

			String line ;
			do
			{
				line = wDefineFile.readLine() ;
				if (line == null) break ;

				//#CM42940
				//<en> If the searched station is found, get the defined station data in that station.</en>
				if (getMainStation(line) == null) continue;
				if (getMainStation(line).equals(wMyStationNumber))
				{
					String[] dst = getDefinedStations(line) ;
					return dst[0];
				}
				else
				{
					continue ;
				}
			} while (line != null) ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace(wPW) ;
			//#CM42941
			//<en> 6026030=Route definition file read error. Detail=({0})</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = ioe.getMessage();
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6026030, LogMessage.F_ERROR, "RoutePice", tObj);
			throw (new IOException("6026030" + wDelim + tObj[0])) ;
		}
		
		return null;
	}

	//#CM42942
	/**<en>
	 * Returns the previous station that this station is connected to.
	 * If more than 1 previous stations are defined, return the 1st one found.
	 * @return :previous station
	 * @throws IOException :Notifies if error occued when reading the carry route definition file.
	 </en>*/
	public String getConnectorStation() throws IOException
	{
		String cStation = null ;

		try
		{
			//#CM42943
			//<en> Regular files are mark-supported. It is marked in start positions.</en>
			wDefineFile.mark(10240) ;

			String line ;
			boolean found = false ;

			do
			{
				line = wDefineFile.readLine() ;
				if (line == null) break ;

				String mSta = getMainStation(line) ;
				if (mSta != null)
				{
					String[] dst = getDefinedStations(line) ;
					for (int i=0; i < dst.length; i++)
					{
						if (wMyStationNumber.equals(dst[i]))
						{
							found = true ;
							cStation = mSta ;
							break ;
						}
					}
				}
				else
				{
					continue ;
				}
			} while (!found) ;
			//#CM42944
			//<en> Set the pointer back to the marked position (start position).</en>
			wDefineFile.reset() ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace(wPW) ;
			//#CM42945
			//<en> 6026030=Route definition file read error. Detail=({0})</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = ioe.getMessage();
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6026030, LogMessage.F_ERROR, "RoutePice", tObj);
			throw (new IOException("6026030" + wDelim + tObj[0])) ;
		}
		
		return (cStation) ;
	}

	//#CM42946
	// Package methods -----------------------------------------------

	//#CM42947
	// Protected methods ---------------------------------------------
	//#CM42948
	/**<en>
	 * Reads the list of connected stations.
	 * @param tgt :list of connected stations
	 * @return    :false if not defined.
	 * @throws IOException :It throws exception if file I/O error occured.
	 </en>*/
	protected boolean loadConnectedStations(Vector tgt) throws IOException
	{
		//#CM42949
		//<en> Finds the definition for this instance from wDefineFile, then set to Vector.</en>
		boolean found = false ;
		try 
		{
			//#CM42950
			//<en> Regular files are mark-supported. The start positions are marked.</en>
			wDefineFile.mark(10240) ;

			String line ;
			do
			{
				line = wDefineFile.readLine() ;
				if (line == null) break ;

				//#CM42951
				//<en> If the searched station is found, get the defined station data in that station.</en>
				if (getMainStation(line) == null) continue;
				if (getMainStation(line).equals(wMyStationNumber))
				{
					found = true ;
					String[] dst = getDefinedStations(line) ;
					for (int i=0; i < dst.length; i++)
					{
						tgt.add(dst[i]) ;
					}
				}
				else
				{
					continue ;
				}
			} while (line != null) ;

			//#CM42952
			//<en> Set the pointer back to the marked position (start position).</en>
			wDefineFile.reset() ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace(wPW) ;
			//#CM42953
			//<en> 6026030=Route definition file read error. Detail=({0})</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = ioe.getMessage();
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6026030, LogMessage.F_ERROR, "RoutePice", tObj);
			throw (new IOException("6026030" + wDelim + tObj[0])) ;
		}
		return (found) ;
	}

	//#CM42954
	// Private methods -----------------------------------------------
	//#CM42955
	/**<en>
	 * Get main-defined stations from information loaded from the route definition file, then return.
	 * @param line :information of definition file
	 * @return :main-defined station (returns null if it is the comment lines.)
	 </en>*/
	private String getMainStation(String line)
	{
		StringTokenizer mtoken ;
		if (line.charAt(0) == COMMENT)
		{
			return (null) ;
		}
		mtoken = new StringTokenizer(line, DELM_DEFINEST, false) ;
		return (mtoken.nextToken()) ;
	}

	//#CM42956
	/**<en>
	 * Get station defined as conenct to point from the route definition file loaded, then return.
	 * @param line :information of definition file
	 * @return :station defined as connect-to point (or it returns null if it is the comment lines.)
	 </en>*/
	private String[] getDefinedStations(String line)
	{
		StringTokenizer deftoken ;
		StringTokenizer mtoken ;

		if (line.charAt(0) == COMMENT)
		{
			return (null) ;
		}

		//#CM42957
		//<en> Skips main-defined station as is unnecessary.</en>
		mtoken = new StringTokenizer(line, DELM_DEFINEST, false) ;
		mtoken.nextToken() ;

		deftoken = new StringTokenizer(mtoken.nextToken(), DELM_CONNECTST, false) ;

		String[] rStations = new String[deftoken.countTokens()] ;
		for (int i=0; i < rStations.length ; i++)
		{
			rStations[i] = deftoken.nextToken() ;
		}
		return (rStations) ;
	}
}
//#CM42958
//end of class

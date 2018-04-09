// $Id: WorkPlace.java,v 1.2 2006/10/26 08:30:56 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM43167
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.entity.Station;

//#CM43168
/**<en>
 * This class is used for workshop control.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:30:56 $
 * @author  $Author: suresh $
 </en>*/
public class WorkPlace extends Station
{
	//#CM43169
	// Class fields --------------------------------------------------
	//#CM43170
	/**<en>
	 * Preserves station no.
	 </en>*/
	private String[] wStationNumber;
	//#CM43171
	/**<en>
	 * Hashtable which preserves the stations
	 </en>*/
	private Hashtable wHashtable = null ;

	//#CM43172
	// Class variables -----------------------------------------------
	private int wStationIndex = 0 ;
	
	//#CM43173
	// Class method --------------------------------------------------
	//#CM43174
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:30:56 $") ;
	}

	//#CM43175
	// Constructors --------------------------------------------------
	//#CM43176
	/**<en>
     * Default constructor
     </en>*/
	public WorkPlace()
	{
		super();
	}
	
	//#CM43177
	/**
	 * Make the instance of new < code>WorkPlace</code >. Use < code>StationFactory</code > class when
	 * the instance with the station which has already been defined is necessary. 
	 * @param snum  Station number
	 * @see StationFactory
	 */
	public WorkPlace(String snum)
	{
		super();
		setStationNumber(snum);
	}

	//#CM43178
	/**<en>
	 * Creates an instance of WorkPlace.
	 * This instance will be used in workshop designation in processes such as scheduling, etc.
	 * @param workplace :work place (station no.)
	 * @param wpstn     :array of station no. the work place has, that are ready for instuructions
	 </en>*/
	public WorkPlace(String workplace, String[] wpstn)
	{
		super();
		setStationNumber(workplace);
		this.setWPStations(wpstn);
	}

	//#CM43179
	// Public methods ------------------------------------------------
	//#CM43180
	/**<en>
	 * Sets the collection of station no. 
	 * @param sts :collection of stations to set
	 </en>*/
	public void setWPStations(String[] sts)
	{
		this.wStationNumber = sts ;
	}

	//#CM43181
	/**<en>
	 * Gets the collection of station no. 
	 * @return :colldction of station no. preserved
	 </en>*/
	public String[] getWPStations()
	{
		return this.wStationNumber ;
	}

	//#CM43182
	/**<en>
	 * Gets the station no. of the work place.
	 * @param conn :database connection in order ot generate the station instance
	 * @return :station in the work place searched
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws InvalidDefineException :Notifies if specified station no. was invalid.
	 </en>*/
	public Station getWPStation(Connection conn) throws ReadWriteException, InvalidDefineException
	{
		if (wStationNumber.length == 0) return null;

		String str = this.wStationNumber[wStationIndex];
		wStationIndex++;
		if (wStationIndex >= wStationNumber.length)
		{
			wStationIndex = 0 ;
		}
		Station st = null;
		
		try
		{
			if (wHashtable == null)
			{
				wHashtable = new Hashtable();
			}
			//#CM43183
			//<en> If it was already registered, it returns station from Hash.</en>
			if (wHashtable.containsKey(str))
			{
				st = (Station)wHashtable.get(str);
			}
			else
			{
				st = StationFactory.makeStation(conn,str);
				wHashtable.put(str, st);
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
		return st ;
	}

	//#CM43184
	// Package methods -----------------------------------------------

	//#CM43185
	// Protected methods ---------------------------------------------

	//#CM43186
	// Private methods -----------------------------------------------

}
//#CM43187
//end of class


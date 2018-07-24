//#CM34378
//$Id: ASWorkPlaceHandler.java,v 1.2 2006/10/30 06:56:16 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

//#CM34379
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.common.ReadWriteException;

//#CM34380
/**
 * This class is used to retrieve <code>WorkPlace</code> class from database, or to store this class in database.
 * For retrieval of <code>WorkPlace</code> class, please use <code>StationFactory</code>.
 * Since <code>WorkPlace</code>class is prepared with <code>getHandler</code> method, if the Handler is required,
 * please use <code>getHandler</code> method.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:56:16 $
 * @author  $Author: suresh $
 */
public class ASWorkPlaceHandler extends StationHandler
{

	//#CM34381
	// Class fields --------------------------------------------------
	
	//#CM34382
	// Class variables -----------------------------------------------

	//#CM34383
	/**
	 * Connection to connect with database
	 */
	protected Connection wConn = null;

	//#CM34384
	// Class method --------------------------------------------------	
	//#CM34385
	/**
	 * Return the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:56:16 $") ;
	}

	//#CM34386
	// Constructors --------------------------------------------------
	//#CM34387
	/**
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection with database
	 */
	public ASWorkPlaceHandler(Connection conn)
	{
		super (conn) ;
		wConn = conn;
	}

	//#CM34388
	// Public methods ------------------------------------------------
	//#CM34389
	/**
	 * Searches the station. <code>StationSearchKey</code> must be used for the search key.<BR>
	 * In this method, wStatement of <code>StationHandler</code>, its parent class, will not be used.<BR>
	 * <B>generation of the Statement</B> is conducted in this find method.<BR>
	 * It is becaouse getChildStationNumbers is used in this method, via makeWorkPlace method.<BR>
	 * getChildStationNumbers method calls makeStation method, and runs SQL using wStatement.<BR>
	 * Therefore, if wStatement is used by find method, it will not able to close the Statement.<BR>
	 * So, be careful with the generation of Statement when customizing this method.
	 * @param key :Key for the search
	 * @return :object array created
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 */
	public Entity[] find(SearchKey key) throws ReadWriteException
	{
		//#CM34390
		//-------------------------------------------------
		//#CM34391
		// variable define
		//#CM34392
		//-------------------------------------------------
		StationHandler wStationHandler = new StationHandler(wConn);
		Station[] ent = (Station[]) wStationHandler.find(key);
		String[] fname = ent[0].getColumnArray();

		Vector vec = new Vector();
		for( int ary = 0 ; ary < ent.length ; ary++ )
		{
			WorkPlace wp = new WorkPlace();
			for( int i = 0 ; i < fname.length ; i++ )
			{
				FieldName field = new FieldName(fname[i]);
				Object value = ent[ary].getValueMap().get(field);
				wp.setValue(field, value);
			}
			vec.addElement(wp);
		}

		WorkPlace[] wkp = new WorkPlace[vec.size()];
		vec.copyInto(wkp);

		return makeWorkPlace(wkp) ;
	}

	//#CM34393
	// Package methods -----------------------------------------------

	//#CM34394
	// Protected methods ---------------------------------------------
	
	//#CM34395
	// Private methods -----------------------------------------------

	//#CM34396
	/**
	 * Search station numbers of workshops specified, then based on the final station used, rearrange the order
	 * of station numbers and return.
	 * If the station belongs to is the workhop itself(NOT_SENDABLE), conduct search for station numbers belong
	 * to that workshop then rearrange the order of result data.
	 * The caller, therefore, receives a list of station numbers sorted in order of designated stations.
	 * @param parentStnum :workshop
	 * @param lastStnum   :final station number used
	 * @return :a list of station numbers sorted in order of designated stations
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 */
	private String[] getChildStationNumbers(String parentStnum, String lastStnum) throws ReadWriteException
	{
		StationSearchKey key = new StationSearchKey();
		key.setParentStationNumber(parentStnum);
		key.setStationNumberOrder(1, true);
		StationHandler wStationHandler = new StationHandler(wConn);
		Station[] childStation = (Station[]) wStationHandler.find(key);

		Vector vec = new Vector();
		for (int i = 0 ; i < childStation.length ; i++)
		{
			if (childStation[i].isSendable() == false)
			{
				//#CM34397
				// If generated station was not sendable, it determines that the station is a workshop
				// and generates a list of station numbers belong to.
				String[] grandChildStnums = getChildStationNumbers(childStation[i].getStationNumber(),
																	childStation[i].getLastUsedStationNumber());
				for (int j = 0 ; j < grandChildStnums.length ; j++)
				{
					vec.addElement(grandChildStnums[j]);
				}
			}
			else
			{
				vec.addElement(childStation[i].getStationNumber());
			}
		}

		String[] stnumArray = new String[vec.size()];
		vec.copyInto(stnumArray);

		//#CM34398
		// According to the final station no. used, rearrange the order of stations.
		String[] sortStnumArray = sortStationNumbers(lastStnum, stnumArray);

		return sortStnumArray;
	}

	//#CM34399
	/**
	 * Returns a list of station numbers based on teh final station used. If the final station number used is
	 * null, or not included in the list of station numbers, it will not rearrange the data, but returns it as 
	 * it is.
	 * EX:  list of station no. (1301, 1302, 1303), the number of final station used :1301
	 *     the list will be rearranged to the following order;
	 *     1302
	 *     1303
	 *     1301
	 * @param lastStnum  :final station used
	 * @param stnumArray :list of station no. subject to rearrangemet
	 * @return :a list of station numbers sorted in order of designations of station
	 */
	private String[] sortStationNumbers(String lastStnum, String[] stnumArray)
	{
		if (lastStnum == null)
		{
			return (stnumArray);
		}
		else
		{
			//#CM34400
			// If the final station number from the warehouse information was set, rearrange the order of list.
			String[] newsts = new String[stnumArray.length];
			for (int i = 0, pt = 0 ; i < stnumArray.length ; i++)
			{
				//#CM34401
				// If these station numbers match, place the following station in the beginning of array.
				if (stnumArray[i].equals(lastStnum))
				{
					for (int j = i + 1 ; j < stnumArray.length ; j++)
					{
						newsts[pt] = stnumArray[j];
						pt++;
					}
					for (int k = 0 ; k < i + 1 ; k++)
					{
						newsts[pt] = stnumArray[k];
						pt++;
					}
					return newsts;
				}
			}
		}
		
		//#CM34402
		// If the final station from warehouse information cannot be found in aisle station no. list, return as it is.
		return (stnumArray);
	}

	//#CM34403
	/**
	 * Get each item from <code>ResultSet</code> and generate <code>WorkPlace</code> instance.
	 * If retrieved data was the sendable station, it will not be regarded as workshop, therefore, instance
	 * will not be generated.
	 * @param rset <CODE>ResultSet</CODE> search result
	 * @return :Entity array mapped with result set
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 */
	protected WorkPlace[] makeWorkPlace(WorkPlace[] wp) throws ReadWriteException
	{
		for( int i = 0 ; i < wp.length ; i++ )
		{
			wp[i].setWPStations(getChildStationNumbers(wp[i].getStationNumber(),
														wp[i].getLastUsedStationNumber()));
		}

		return (wp) ;
	}
}
//#CM34404
//end of class


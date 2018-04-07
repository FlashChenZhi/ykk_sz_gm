// $Id: AisleOperator.java,v 1.2 2006/10/26 08:40:28 suresh Exp $
package jp.co.daifuku.wms.asrs.location;

//#CM42112
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.BankSelectHandler;
import jp.co.daifuku.wms.base.dbhandler.BankSelectSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

//#CM42113
/**<en>
 * The class which collects processing which operates it to aisle information. <BR> 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/22</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:28 $
 * @author  $Author: suresh $
 </en>*/
public class AisleOperator extends Object
{
	//#CM42114
	// Class fields --------------------------------------------------

	//#CM42115
	// Class variables -----------------------------------------------

	//#CM42116
	/**<en>
	 * Connection to connect with database
	 </en>*/
	protected Connection wConn = null;

	//#CM42117
	/**
	 * Aisle Instance
	 */
	Aisle wAisle = null;

	//#CM42118
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 * 	ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM42119
	// Class method --------------------------------------------------
	//#CM42120
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:28 $") ;
	}

	//#CM42121
	/**<en>
	 * Return the list of station no. of Aisles included in location numbers of specified range.
	 * @param  conn     :Connection with database
	 * @param  frShfNum :leading location no.
	 * @param  toShfNum :last location no.
	 * @return :list of aisle station no.
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws IllegalArgumentException :Notifies if specified location no. was undefined.
	 * @throws IllegalArgumentException :Notifies if warehouse which controls 2 location no. specified is different.
	 * @throws IllegalArgumentException :Notifies if invalid bank range has been specified.
	 </en>*/
	public static String[] getAisleStationNumbers(Connection conn, String frShfNum, String toShfNum)
																		throws  ReadWriteException,
																				IllegalArgumentException
	{
		int startBank = 0;
		int endBank = 0;
		String whnum = null;
		
		//#CM42122
		//<en> delimiter</en>
		String wDelim = MessageResource.DELIM ; 

		//#CM42123
		//<en> Returns exception if leading location no. is lower than the last location no.</en>
		if (frShfNum.compareTo(toShfNum) > 0)
		{
			//#CM42124
			//<en> 6022020=Error with the specified range of location.</en>
			RmiMsgLogClient.write(6022020, LogMessage.F_NOTICE, "AisleOperator", null);
			throw new IllegalArgumentException("6022020");
		}

		try
		{
			//#CM42125
			//<en> Getting bank no. based on the leafing location no.</en>
			Station fst = StationFactory.makeStation(conn, frShfNum);
			if (fst instanceof Shelf)
			{
				Shelf frshf = (Shelf)fst;
				startBank = frshf.getNBank();
				whnum = frshf.getWHStationNumber();
			}
			else
			{
				//#CM42126
				//<en> Throws exception if station no. other than locations is specified.</en>
				//#CM42127
				//<en> 6026047=Data of specified location no. does not exist. Location No.={0}</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = frShfNum;
				RmiMsgLogClient.write(6026047, LogMessage.F_ERROR, "AisleOperator", tObj);
				throw new IllegalArgumentException("6026047" + wDelim + tObj[0]);
			}
			
			//#CM42128
			//<en> Getting bank no. based on the last locatio no.</en>
			Station tst = StationFactory.makeStation(conn, toShfNum);
			if (tst instanceof Shelf)
			{
				Shelf toshf = (Shelf)tst;
				endBank = toshf.getNBank();
				if (!whnum.equals(toshf.getWHStationNumber()))
				{
					//#CM42129
					//<en> Throws exception if the range of locations surpassed  one warehouse.</en>
					//#CM42130
					//<en> 6022020=Error with the specified range of location.</en>
					RmiMsgLogClient.write(6022020, LogMessage.F_NOTICE, "AisleOperator", null);
					throw new IllegalArgumentException("6022020");
				}
			}
			else
			{
				//#CM42131
				//<en> Throws exception if station no. other than locations is specified.</en>
				//#CM42132
				//<en> 6026047=Data of specified location no. does not exist. Location No.={0}</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = frShfNum;
				RmiMsgLogClient.write(6026047, LogMessage.F_ERROR, "AisleOperator", tObj);
				throw new IllegalArgumentException("6026047" + wDelim + tObj[0]);
			}
		}
		//#CM42133
		//<en> Exception from StationFactory.makeStation.</en>
		catch (SQLException se)
		{
			throw new ReadWriteException(se.getMessage());
		}
		catch (NotFoundException ne)
		{
			throw new IllegalArgumentException(ne.getMessage());
		}
		catch (InvalidDefineException ie)
		{
			throw new IllegalArgumentException(ie.getMessage());
		}
		
		return getAisleStationNumbers(conn, whnum, startBank, endBank);
	}

	//#CM42134
	/**<en>
	 * Returns a list of aisle station no. included in the range of warehouse set, between leading bank and the last bank.
	 * @param conn   :Connection to connect with database
	 * @param whnum  :warehouse no.
	 * @param frBank :leading bank no.
	 * @param toBank :last bank no.
	 * @return :list of aisle station no.
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws IllegalArgumentException :Notifies if there is no definition in the range of specified warehouse no. and 
	 * the location no.
	 </en>*/
	public static String[] getAisleStationNumbers(Connection conn, String whnum, int frBank, int toBank)
																		throws  ReadWriteException,
																				IllegalArgumentException
	{
		String[] strAisleStation = null;

		//#CM42135
		//<en> delimiter</en>
		String wDelim = MessageResource.DELIM ;  

		BankSelectSearchKey bselKey = new BankSelectSearchKey();

		bselKey.setAisleStationNumberCollect("DISTINCT");
		bselKey.setWHStationNumber(whnum);
		bselKey.setNbank(frBank, ">=", "(", "", "and");
		bselKey.setNbank(toBank, "<=", "", ")", "and");
		bselKey.setAisleStationNumberOrder(1, true);

		BankSelectHandler bselh = new BankSelectHandler(conn);
		BankSelect[] bselArry = (BankSelect[]) bselh.find(bselKey);
		if( bselArry != null )
		{
			Vector vec = new Vector();
			for( int i = 0 ; i < bselArry.length ; i++ )
			{
				vec.addElement(bselArry[i].getAisleStationNumber());
			}
			strAisleStation = new String[vec.size()];
			vec.copyInto(strAisleStation);
		}
		else
		{
			//#CM42136
			//<en> 6026048=Aisle data does not exist. Warehouse={0} Start Bank No.={0} End Bank No.={1}</en>
			Object[] tObj = new Object[3] ;
			tObj[0] = whnum;
			tObj[1] = new Integer(frBank);
			tObj[2] = new Integer(toBank);
			RmiMsgLogClient.write(6026048, LogMessage.F_ERROR, "AisleSelector", tObj);
			throw new IllegalArgumentException("6026048" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]);
		}

		return(strAisleStation);
	}

	//#CM42137
	/**<en>
	 * Returns a list of aisle station no. included in the range of warehouse set.
	 * specified.
	 * @param conn :Connection with database
	 * @param wstnum :warehouse station no. to retrieve the aisle station no. from
	 * @return :list of aisle station no.
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws IllegalArgumentException :Notifies if no aisle station of such wstnum does not exist.
	 </en>*/
	public static String[] getAisleStationNumbers(Connection conn, String whnum)
																		throws  ReadWriteException,
																				IllegalArgumentException
	{
		String[] strAisleStation = null;

		BankSelectSearchKey bselKey = new BankSelectSearchKey();

		bselKey.setAisleStationNumberCollect("DISTINCT");
		bselKey.setWHStationNumber(whnum);
		bselKey.setAisleStationNumberOrder(1, true);

		BankSelectHandler bselh = new BankSelectHandler(conn);
		BankSelect[] bselArry = (BankSelect[]) bselh.find(bselKey);
		if( bselArry != null )
		{
			Vector vec = new Vector();
			for( int i = 0 ; i < bselArry.length ; i++ )
			{
				vec.addElement(bselArry[i].getAisleStationNumber());
			}
			
			strAisleStation = new String[vec.size()];
			vec.copyInto(strAisleStation);
		}

		return(strAisleStation);
	}

	//#CM42138
	/**<en>
	 * Check to see if that station no. specified exists as an aisle station no.
	 * @param conn Connection with database
	 * @param stnum station no.
	 * @return true if it does, false if it does not exist.
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws NotFoundException :Notifies if the number other than the aisle station no. is specified.
	 </en>*/
	public static boolean isAisleStation(Connection conn, String stnum) throws  ReadWriteException, NotFoundException
	{
		//#CM42139
		//<en> Generates instance according to the specified station no.</en>
		//#CM42140
		//<en> It returns true if the generated station is the aisle.</en>
		Station st = null;
		try
		{
			st = StationFactory.makeStation(conn, stnum);
		}
		catch (SQLException se)
		{
			throw new ReadWriteException(se.getMessage());
		}
		catch (NotFoundException ne)
		{
			//#CM42141
			//<en> 6026046=The specified station is not an aisle. ST No={0}</en>
			Object[] tObj = new Object[1] ;
			tObj[0] = stnum;
			throw new NotFoundException("6026046" + MessageResource.DELIM + tObj[0]);
		}
		catch (InvalidDefineException ie)
		{
			//#CM42142
			//<en> 6026046=The specified station is not an aisle. ST No={0}</en>
			Object[] tObj = new Object[1] ;
			tObj[0] = stnum;
			throw new NotFoundException("6026046" + MessageResource.DELIM + tObj[0]);
		}
		
		if (st instanceof Aisle)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//#CM42143
	// Constructors --------------------------------------------------
	//#CM42144
	/**
	 * Make the instance of new < code>AisleOperator</code >.
	 */
	public AisleOperator()
	{
	}

	//#CM42145
	/**
	 * Make the instance of new < code>AisleOperator</code >.
	 * Generate and maintain the aisle instance from aisle No. passed by the argument.
	 * @param conn Database connection
	 * @param aisleNo Aisle No.
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public AisleOperator(Connection conn, String aisleNo) throws ReadWriteException
	{
		wConn = conn;
		wAisle = getStation(conn, aisleNo);
	}

	//#CM42146
	/**
	 * Acquire station information from the data base and generate the Aisle instance.
	 * @param conn Connection with database
	 * @param aisleNo Aisle No.
	 * @return Aisle instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	private Aisle getStation(Connection conn, String aisleNo) throws ReadWriteException
	{
		AisleSearchKey key = new AisleSearchKey();
		key.setStationNumber(aisleNo);
		AisleHandler wAisleHandler = new AisleHandler(conn);
		Entity[] ent = wAisleHandler.find(key);

		return (Aisle)ent[0];
	}

	//#CM42147
	// Public methods ------------------------------------------------

	//#CM42148
	/**<en>
	 * Based on the entered station no., it determines whether/not the aisles on the carry route are 
	 * in process of invneotry check.<BR>
	 * Please confirm about the inventory check, by this method, with storage station if storing, with retrieval 
	 * station if retrieving and with both sending  and receiving stations if direct traveing.<BR>
	 * In case the workshop has been specified, check all stations that belong to the specified workshop then
	 * return true if one or more station in inventory checking process is found.
	 * Create a station instance based on the station no., and pass to the check method for empty location.
	 * <BR><Process detail><BR>
	 * 1.  Get AISLESTATIONNUMBER according to the method: Station.getAisleStationNumber().<BR>
	 * 2. If one or more INVENTORYCHECKFLAG is found INVENTORYCHECK, the inventory check is in progress.<BR>
	 * @param conn Connection with database
	 * @param stno station no. to check.
	 * @return True:inventory check is in progress, False:inventory check is not done
	 * @throws ReadWriteException  :Notifies of exception as it is that occured in accessing database.
	 * @throws InvalidDefineException  :Notifies if definition of the class was incorrect.
	 * @throws NotFoundException       :Notifies if corresponding class was not found.
	 </en>*/
	public boolean isInventoryCheck(Connection conn, String stno) throws
											ReadWriteException, InvalidDefineException, NotFoundException
	{
		try
		{
			String aisleStNumbers[] = getAisleStationNumber(conn, stno);
			
			//#CM42149
			//<en>Searches through obtained aisle for the ones in process of inventory checks.</en>
			for(int i = 0; i < aisleStNumbers.length; i++)
			{
				//#CM42150
				//<en>Creates an instance of aisle station.</en>
				Station aisleSt = StationFactory.makeStation(conn, aisleStNumbers[i]);
				
				//#CM42151
				//<en>If inventory check is in progress,</en>
				if(aisleSt.getInventoryCheckFlag() == Station.INVENTORYCHECK)
				{
					//#CM42152
					//<en> inventory check in progress</en>
					return true;
				}
			}
			return false;
		}
		catch (SQLException sqle)
		{
			throw new ReadWriteException(sqle.getMessage());
		}
	}

	//#CM42153
	/**<en>
	 * Based on the entered station no., it determines whether/not the aisles on the carry route are
	 * in process of empty location check.<BR>
	 * Please confirm about the empty location checkes with sending station if storing, with receiving station
	 * if retrieving and with both sending and receiving stations if direct traveing.<BR>
	 * In case the workshop has been specified, check all stations that belong to the specified workshop then
	 * return true if one or more station in empty locations checking process is found.
	 * <BR><Process detail><BR>
	 * 1.  Get aisle station no. from specified station.<BR>
	 * 2.  If one or more INVENTORYCHECKFLAG is found EMPTY_LOCATION_CHECK, the empty location check is<BR>
	 *     in progress.<BR>
	 * @param conn Connection with database
	 * @param stno station no. to check.
	 * @return true :empty location check is in progress,  false: empty location check is not done.
	 * @throws ReadWriteException :Notifies of exception as it is that occured in accessing database.
	 * @throws InvalidDefineException  :Notifies if definition of the class was incorrect.
	 * @throws NotFoundException       :Notifies if corresponding class was not found.
	 </en>*/
	public boolean isEmptyLocationCheck(Connection conn, String stno) throws
										ReadWriteException, InvalidDefineException, NotFoundException
	{
		try
		{
			String aisleStNumbers[] = getAisleStationNumber(conn, stno);

			//#CM42154
			//<en>Searches through obtained aisle for the ones in process of empty location checks.</en>
			for (int i = 0; i < aisleStNumbers.length; i++)
			{
				//#CM42155
				//<en>Creates an instance of aisle station.</en>
				Station aisleSt = StationFactory.makeStation(conn, aisleStNumbers[i]);
				
				//#CM42156
				//<en>If empty location check is in progress,</en>
				if (aisleSt.getInventoryCheckFlag() == Station.EMPTY_LOCATION_CHECK)
				{
					//#CM42157
					//<en> Sets the message classification for empty location check in progress.</en>
					return true;
				}
			}
			return false;
		}
		catch (SQLException sqle)
		{
			throw new ReadWriteException(sqle.getMessage());
		}
	}

	//#CM42158
	/**<en>
	 * Return the aisle station in form of array that has routes to connect with entered station.<BR>
	 * If any workplace is specified, check all the stations that belong to that workplace then return
	 * the conected aisle station by routes.<BR>
	 * <BR><detail of process><BR>
	 * 1. Create the station instance based on the sending/receiving station No.<BR><BR>
	 * 2. Get the AISLESTATIONNUMBER using Station.getAisleStationNumber() method.<BR><BR>
	 * 3-A. If AISLESTATIONNUMBER is defined (stand alone system),<BR>
	 *  - Create the aisle station instance based on the AISLESTATIONNUMBER.<BR>
	 *  - Add the acquired AISLESTATIONNUMBER to the array.<BR><BR>
	 * 3-B  If AISLESTATIONNUMBER is not defined (connected aisle system),<BR>
	 *  - Get WHSTATIONNUMBER using Station.getWareHouseStationNumber().<BR>
	 *  - Get the AISLESTATIONNUMBER that belog to that warehouse by using 
	 *     AisleSelector.getAisleStationNumbers(Connection conn, String whnum) method.<BR>
	 *  - Create the aisle station instance based on the AISLESTATIONNUMBER.<BR>
	 *  - Add the acquired AISLESTATIONNUMBER to the array.<BR>
	 * @param conn Connection with database
	 * @param stno station no. to check.
	 * @return :array of aisle station No. that belong to the entered station
	 * @throws ReadWriteException :Notify the exception as it is that occurred in connection with database.
	 * @throws InvalidDefineException  :Notify if definition of the class is incorrect.
	 * @throws NotFoundException   :Notify if corresponding class cannot be found.
	 * @throws SQLException : Notifies if database error occurrs .
	 </en>*/
	public String[] getAisleStationNumber(Connection conn, String stno) throws
											ReadWriteException, InvalidDefineException
												, NotFoundException, SQLException
	{
		//#CM42159
		//<en>Create the station instance.</en>
		Station st = StationFactory.makeStation(conn, stno);

		String aisleStNo = null;
		Vector vec = new Vector();
		Hashtable hst = new Hashtable();
		String[] aisleNumArray = null;
		try
		{
			//#CM42160
			//<en>For workplace</en>
			if(st instanceof WorkPlace)
			{
				//#CM42161
				//<en>Cast to WorkPlace</en>
				WorkPlace  wPlace = (WorkPlace)st;
				
				//#CM42162
				//<en>Get the stations that belong to the workplace.</en>
				String[] stNumbers = wPlace.getWPStations();
				
				//#CM42163
				//<en> Get the aisle station No. that the station belongs to.</en>
				for(int i = 0; i < stNumbers.length; i++)
				{
					 String[] aisle = getAisleStationNumber(conn, stNumbers[i]);
					 for (int j = 0 ; j < aisle.length; j++)
					 {
						//#CM42164
						//<en> Add the acquired aisle No. to the array. (repeat as many as existing data)</en>
						if (!hst.containsKey(aisle[j]))
						{
							vec.addElement(aisle[j]);
							hst.put(aisle[j],"");
						}
					 }
				}
			}
			//#CM42165
			//<en> Other than workplace</en>
			else
			{
				//#CM42166
				//<en> Get the aisle station No. that the station belongs to.</en>
				aisleStNo = st.getAisleStationNumber();
				
				//#CM42167
				//<en> If the aisle station No. is not in defined (due to aisle connected station)</en>
				if(StringUtil.isBlank(aisleStNo))
				{
					//#CM42168
					//<en> Get the warehouse No.</en>
					String whStNo = st.getWHStationNumber();
					
					//#CM42169
					//<en> Generate the list of aisle station No. of warehouse using the warehouse No.</en>
					String[] aisleStNumbers = getAisleStationNumbers(conn, whStNo);

					//#CM42170
					//<en> Add each of retrieved aisle No. to the array.</en>
					for(int i = 0; i < aisleStNumbers.length; i++)
					{					
						if (!hst.containsKey(aisleStNumbers[i]))
						{
							vec.addElement(aisleStNumbers[i]);
							hst.put(aisleStNumbers[i],"");
						}
					}
				}
				//#CM42171
				//<en> If the aisle station No. is defined (stand alone station)</en>
				else
				{
					//#CM42172
					//<en> Add each of retrieved aisle No. to the array.</en>
					if (!hst.containsKey(aisleStNo))
					{
						vec.addElement(aisleStNo);
						hst.put(aisleStNo,"");
					}
				}				
			}
		}
		finally
		{
			aisleNumArray = new String[vec.size()];
			vec.copyInto(aisleNumArray);
		}
		return aisleNumArray;
	}

	//#CM42173
	/**<en>
	 * Modifies the status of aisle. It updates the database using handler.
	 * @param  sts :status to be modified
     * @throws InvalidStatusException :Notifies if the contents of sts is outside the scope.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if error occured when accessing database.
	 * @throws NotFoundException      :Notifies if this station was not found in database.
	 </en>*/
	public void alterStatus(int sts) throws
									InvalidStatusException,
									InvalidDefineException,
									ReadWriteException,
									NotFoundException
	{
		AisleAlterKey ak = new AisleAlterKey();
		ak.setStationNumber(wAisle.getStationNumber());
		ak.updateStatus(sts);
		AisleHandler ah = new AisleHandler(wConn);
		ah.modify(ak);
	}

	//#CM42174
	/**<en>
	 * Modifies the bank of end use. Using handler, it updates the database.
	 * @param  bank :bank of end use
     * @throws InvalidStatusException :Notifies if contents of bank is outside the scope.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if error occured when accessing database.
	 * @throws NotFoundException      :Notifies if this station was not found in database.
	 </en>*/
	public void alterLastUsedBank(int bank) throws
									InvalidStatusException,
									InvalidDefineException,
									ReadWriteException,
									NotFoundException
	{
		AisleAlterKey ak = new AisleAlterKey();
		ak.setStationNumber(wAisle.getStationNumber());
		ak.updateLastUsedBank(bank);
		AisleHandler ah = new AisleHandler(wConn);
		ah.modify(ak);
	}

	//#CM42175
	/**<en>
	 * Modifies teh state of inventory check of the station. It updates database using handler.
	 * @param  flg :flag for inventory check
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if error occured when accessing database.
	 * @throws NotFoundException      :Notifies if this station was not found in database.
     * @throws InvalidStatusException :Notifies if contents of flg is outside the scope.
	 </en>*/
	public void alterInventoryCheckFlag(int flg) throws InvalidDefineException, ReadWriteException, 
														NotFoundException, InvalidStatusException
	{
		AisleAlterKey ak = new AisleAlterKey();
		ak.setStationNumber(wAisle.getStationNumber());
		ak.updateInventoryCheckFlag(flg);
		AisleHandler ah = new AisleHandler(wConn);
		ah.modify(ak);
	}

	//#CM42176
	// Package methods -----------------------------------------------

	//#CM42177
	// Protected methods ---------------------------------------------

	//#CM42178
	// Private methods -----------------------------------------------

}
//#CM42179
//end of class


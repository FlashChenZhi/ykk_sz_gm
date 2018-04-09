//#CM42959
//$Id: ShelfOperator.java,v 1.2 2006/10/26 08:33:40 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42960
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.Statement;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;

//#CM42961
/**<en>
 * The class which collected processing which operates it to shelf information. <BR> 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:33:40 $
 * @author  $Author: suresh $
 </en>*/
public class ShelfOperator extends Entity
{
	//#CM42962
	// Class fields --------------------------------------------------

	//#CM42963
	// Class variables -----------------------------------------------
	//#CM42964
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM42965
	/**
	 * Shelf instance
	 */
	Shelf wShelf = null;

	//#CM42966
	/**<en>
	 * Variable which controls the statement
	 </en>*/
	protected Statement wStatement = null ;

	//#CM42967
	/**
	 * Delimiter
	 * When Exception is generated, the delimiter of the parameter of the message of MessageDef.
	 * 	Ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM42968
	// Constructors --------------------------------------------------
	//#CM42969
	/**
	 * Make the instance of new <code>ShelfOperator</code>.
	 */
	public ShelfOperator()
	{
	}

	//#CM42970
	/**
	 * Make the instance of new <code>ShelfOperator</code>.
	 * Generate and maintain the Shelf instance from Database connection passed by the argument.
	 * @param conn Database connection
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public ShelfOperator(Connection conn) throws ReadWriteException
	{
		wConn = conn;
	}

	//#CM42971
	/**
	 * Make the instance of new <code>ShelfOperator</code>.
	 * It was passed by the argument, and generate and maintain Database connection and Station No Shelf or more instances.
	 * @param conn Database connection
	 * @param stno Station No.
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access. 
	 */
	public ShelfOperator(Connection conn, String stno) throws ReadWriteException
	{
		wConn = conn;
		wShelf = getStation(conn, stno);
	}

	//#CM42972
	/**
	 * Acquire station information from the data base and generate the Station instance. 
	 * @param conn Connection with database
	 * @param stno Station No. of Station
	 * @return Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access. 
	 */
	private Shelf getStation(Connection conn, String stno) throws ReadWriteException
	{
		ShelfSearchKey key = new ShelfSearchKey();
		key.setStationNumber(stno);
		ShelfHandler wShelfHandler = new ShelfHandler(conn);
		Entity[] ent = wShelfHandler.find(key);
		
		return (Shelf)ent[0];
	}

	//#CM42973
	// Class method --------------------------------------------------
	//#CM42974
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:33:40 $") ;
	}

	//#CM42975
	// Public methods ------------------------------------------------

	//#CM42976
	/**<en>
	 * Search the empty location.
	 * According to the specified aisle and zone, search in the Shlf table. Return one Shlf instance which is an
	 * empty location.
	 * Or return null if no empty location was found.
	 * Please remember, the caller must commit or rollback the transaction, as this method locks the Shelf table
	 * until the transaction should complete.
	 * @param tAisle :aisle subject to empty location search
	 * @param tZone  :zone subject to empty location search
	 * @return : Shelf instance searched
	 * @throws ReadWriteException :Notifies of the exception occured during the database processing.
	 * @throws InvalidDefineException ShelfSelecotor :Notifies if interface-undefined direction of empty location 
	 * search has been selected.
     </en>*/
	public Shelf findEmptyShelf(Aisle tAisle, Zone[] tZone) throws
														ReadWriteException, InvalidDefineException
	{
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		ShelfHandler shelfHdl = new ShelfHandler(wConn);
		Shelf shelf = null;

		for( int i = 0 ; i < tZone.length ; i++ )
		{
			shelfKey.KeyClear();
			shelfKey.setParentStationNumber(tAisle.getStationNumber());
			shelfKey.setHardZoneID(tZone[i].getHardZone().getHardZoneID());
			shelfKey.setSoftZoneID(tZone[i].getSoftZoneID());
			shelfKey.setPresence(Shelf.PRESENCE_EMPTY);
			shelfKey.setStatus(Station.STATION_OK);
			shelfKey.setAccessNgFlag(Shelf.ACCESS_OK);

			switch (tZone[i].getDirection())
			{
				//#CM42977
				//<en> HP, from lower shelf</en>
				case Zone.HP_LOWER:
					//#CM42978
					// LEVEL, BAY, BANK
					shelfKey.setNLevelOrder(1, true);
					shelfKey.setNBayOrder(2, true);
					shelfKey.setNBankOrder(3, true);
					break;
					
				//#CM42979
				//<en> HP, from front shelf</en>
				case Zone.HP_FRONT:
					//#CM42980
					// BAY, LEVEL, BANK
					shelfKey.setNBayOrder(1, true);
					shelfKey.setNLevelOrder(2, true);
					shelfKey.setNBankOrder(3, true);
					break;
					
				//#CM42981
				//<en> OP, from lower shelf</en>
				case Zone.OP_LOWER:
					//#CM42982
					// LEVEL, BAY DESC, BANK
					shelfKey.setNLevelOrder(1, true);
					shelfKey.setNBayOrder(2, false);
					shelfKey.setNBankOrder(3, true);
					break;
					
				//#CM42983
				//<en> OP, from front shelf</en>
				case Zone.OP_FRONT:
					//#CM42984
					// BAY DESC, LEVEL, BANK
					shelfKey.setNBayOrder(1, false);
					shelfKey.setNLevelOrder(2, true);
					shelfKey.setNBankOrder(3, true);
					break;
					
				default:
					//#CM42985
					//<en>undifined , throw exception!</en>
					Object[] tObj = new Object[3] ;
					tObj[0] = this.getClass().getName() ;
					tObj[1] = "wDirection";
					tObj[2] = Integer.toString(tZone[i].getDirection()) ;
					String classname = (String)tObj[0];
					//#CM42986
					//<en> 6016061=Specified value is out of range. Cannot set. Class={0} Variable={1} Value={2}</en>
					RmiMsgLogClient.write(6016061, LogMessage.F_ERROR, classname, tObj);
					throw (new InvalidDefineException("6016061" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
			}

			Shelf[] shelfArry = (Shelf[]) shelfHdl.findForUpdate(shelfKey);
			if( shelfArry.length > 0 )
			{
				shelf = shelfArry[0];
				break;
			}
		}

		return shelf;
	}

	//#CM42987
	/**<en>
	 * Search the empty location available for empty location check within the specified range of warehouse numbers 
	 * and location numbers.
	 * Please remember, the caller must either confirm or cancel the transaction since the loaded will be locked.
	 * @param whnum warehouse station no.
	 * @param frloc :location no. starting from
	 * @param toloc :location no. ending 
	 * @return :object array created
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public Entity[] allocateEmptyLocation(String whnum, String frloc, String toloc) throws ReadWriteException
	{
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		ShelfHandler shelfHdl = new ShelfHandler(wConn);

		shelfKey.setWHStationNumber(whnum);
		if (StringUtil.isBlank(frloc) == false)
		{
			shelfKey.setStationNumber(frloc, ">=");
		}
		if (StringUtil.isBlank(toloc) == false)
		{
			shelfKey.setStationNumber(toloc, "<= ");
		}
		shelfKey.setAccessNgFlag(Shelf.ACCESS_OK);
		shelfKey.setStatus(Station.STATION_OK);
		shelfKey.setPresence(Shelf.PRESENCE_EMPTY);
		shelfKey.setNBankOrder(1, true);
		shelfKey.setNBayOrder(2, true);
		shelfKey.setNLevelOrder(3, true);

		return shelfHdl.findForUpdate(shelfKey);
	}

	//#CM42988
	/**<en>
	 * Search the empty location available for empty location check within the specified range of warehouse numbers 
	 * and location numbers adn according to the aisle station no..
	 * Please remember, the caller must either confirm or cancel the transaction since the loaded will be locked.
	 * @param whnum :warehouse station no.
	 * @param frloc :location no. starting from
	 * @param toloc : location no. ending
	 * @param aile :aisle station no.
	 * @return :object array created
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public Entity[] allocateEmptyLocation(String whnum, String frloc, String toloc, String aile) throws ReadWriteException
	{
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		ShelfHandler shelfHdl = new ShelfHandler(wConn);

		shelfKey.setWHStationNumber(whnum);
		if (StringUtil.isBlank(frloc) == false)
		{
			shelfKey.setStationNumber(frloc, ">=");
		}
		if (StringUtil.isBlank(toloc) == false)
		{
			shelfKey.setStationNumber(toloc, "<= ");
		}
		shelfKey.setAccessNgFlag(Shelf.ACCESS_OK);
		shelfKey.setStatus(Station.STATION_OK);
		shelfKey.setPresence(Shelf.PRESENCE_EMPTY);
		shelfKey.setParentStationNumber(aile);
		shelfKey.setNBankOrder(1, true);
		shelfKey.setNBayOrder(2, true);
		shelfKey.setNLevelOrder(3, true);

		return shelfHdl.findForUpdate(shelfKey);
	}

	//#CM42989
	/**<en>
	 * Return a list of Zone IDs in hard zone by specifying warehouse.
	 * <CODE>Statement</CODE> currently uses wStatement to open cursors.<BR>
	 * wStatement is used as a temporary measure; in future this process will be changed so that cursors can
	 * be created within this method.*
	 * @param whstno   warehouse station no.
	 * @return int array of Zone IDs under specified conditions
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public int[] getHardZoneIDArray(String whstno) throws ReadWriteException
	{
		ShelfSearchKey shelfKey = new ShelfSearchKey();
		ShelfHandler shelfHdl = new ShelfHandler(wConn);

		shelfKey.setHardZoneIDCollect();
		shelfKey.setWHStationNumber(whstno);
		shelfKey.setHardZoneIDGroup(1);
		shelfKey.setHardZoneIDOrder(1, true);

		Shelf[] shelfArry = (Shelf[]) shelfHdl.find(shelfKey);

		int[] zoneIDArray = new int[shelfArry.length];
		for (int i=0; i < zoneIDArray.length; i++)
		{
			zoneIDArray[i] = shelfArry[i].getHardZoneID();
		}

		return(zoneIDArray) ;
	}

	//#CM42990
	/**<en>
	 * Alter the shelf status(location availability). Update database by using handler.
	 * @param sts :status
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such station in database. 
	 </en>*/
	public void alterStatus(int sts) throws
									InvalidDefineException,
									ReadWriteException,
									NotFoundException
	{
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler hdl = new ShelfHandler(wConn);

		sk.setStationNumber(wShelf.getStationNumber());
		sk.updateStatus(sts);
		hdl.modify(sk);
	}

	//#CM42991
	/**<en>
	 * Alter the shelf status. Update database by using handler.
	 * @param pre :status of the shelf
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such station in database. 
	 </en>*/
	public void alterPresence(int pre) throws 
									InvalidDefineException,
									ReadWriteException,
									NotFoundException
	{
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler hdl = new ShelfHandler(wConn);

		sk.setStationNumber(wShelf.getStationNumber());
		sk.updatePresence(pre);
		hdl.modify(sk);
	}

	//#CM42992
	/**<en>
	 *Alter the locaiton access flag of the shelf. Update database by using handler.
	 * @param acc :inaccesible location flag
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access. 
	 * @throws NotFoundException      :Notifies if there is no such station in database. 
	 </en>*/
	public void alterAccessNgFlag(int acc) throws InvalidDefineException, ReadWriteException, NotFoundException
	{
		ShelfAlterKey sk = new ShelfAlterKey();
		ShelfHandler hdl = new ShelfHandler(wConn);

		sk.setStationNumber(wShelf.getStationNumber());
		sk.updateAccessNgFlag(acc);
		hdl.modify(sk);
	}

	//#CM42993
	// Package methods -----------------------------------------------

	//#CM42994
	// Protected methods ---------------------------------------------

}
//#CM42995
//end of class


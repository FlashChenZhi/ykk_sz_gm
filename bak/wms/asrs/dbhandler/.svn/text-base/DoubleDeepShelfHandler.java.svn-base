//#CM34405
//$Id: DoubleDeepShelfHandler.java,v 1.2 2006/10/30 06:27:53 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler ;

//#CM34406
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

import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM34407
/**
 * This class is used to generate <code>DoubleDeepShelf</code> instance based on SHELF table
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>M.INOUE</TD><TD>Add double deep target</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:27:53 $
 * @author  $Author: suresh $
 */
public class DoubleDeepShelfHandler extends ASShelfHandler
{
	//#CM34408
	// Class fields --------------------------------------------------
	//#CM34409
	/**
	 * Connection to connect with database
	 */
	protected Connection wConn = null;

	//#CM34410
	// Class variables -----------------------------------------------

	//#CM34411
	// Constructors --------------------------------------------------
	//#CM34412
	/**
	 * Specify <code>Connection</code> object for database connection and generate instance
	 * @param conn connection object for database connection
	 */
	public DoubleDeepShelfHandler(Connection conn)
	{
		super(conn) ;
		wConn = conn;
	}

	//#CM34413
	// Class method --------------------------------------------------
	//#CM34414
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:27:53 $") ;
	}

	//#CM34415
	// Public methods ------------------------------------------------
	//#CM34416
	/**
	 * Search empty location
	 * Search shelf table using specified aisle, zone and return a shelf instance of an empty location
	 * Return null if no empty location exist
	 * Since this table locks the shelf table until the transaction is complete, please commit or rollback without fail
	 * @param tAisle aisle target for empty location search
	 * @param tZone  zone target for empty location search
	 * @return Shelf instance 
	 * @throws ReadWriteException Throw any error during database processing
	 * @throws InvalidDefineException ShelfSelecotor If empty location search direction not in the definition file is specified, throw exception
	 */
	public Shelf findEmptyShelfForDoubleDeep(Aisle tAisle, Zone[] tZone) throws ReadWriteException, InvalidDefineException
	{
		ResultSet rset     = null;
		Shelf[] fndStation = null;
		String sqlstring   = null;
		try
		{
			String fmtSQL = "SELECT * FROM SHELF WHERE" +
						" PARENTSTATIONNUMBER = {0}" +
						" AND HARDZONEID = {1} " +
						" AND SOFTZONEID = {2} " +
						" AND PRESENCE = "     + Shelf.PRESENCE_EMPTY +
						" AND STATUS   = "     + Station.STATION_OK   +
						" AND ACCESSNGFLAG = " + Shelf.ACCESS_OK;
			
			//#CM34417
			// object for empty location search
			Object[] fmtObj = new Object[4] ;
			fmtObj[0] = tAisle.getStationNumber();
			//#CM34418
			// object for empty location check
			Object[] checkObj = new Object[4] ;
			checkObj[0] = tAisle.getStationNumber();
			
			for (int i = 0 ; i < tZone.length ; i++)
			{
				String findSQL = fmtSQL;

				checkObj[1] = Integer.toString(tZone[i].getHardZone().getHardZoneID());
				checkObj[2] = Integer.toString(tZone[i].getSoftZoneID());

				//#CM34419
				// search the inner side of a pair shelf
				String checkSQL = "SELECT * FROM ("+fmtSQL+") S1, ("+fmtSQL+") S2"+
							" WHERE S1.STATIONNUMBER = S2.PAIRSTATIONNUMBER AND S1.PRIORITY < S2.PRIORITY {3} ";

				switch (tZone[0].getDirection())
				{
					//#CM34420
					//HP Back
					case Zone.HP_LOWER:
						checkObj[3] = "ORDER BY S1.NLEVEL, S1.NBAY, S1.PRIORITY";
						break;
						
					//#CM34421
					// HP Front
					case Zone.HP_FRONT:
						checkObj[3] = "ORDER BY S1.NBAY, S1.NLEVEL, S1.PRIORITY";
						break;
						
					//#CM34422
					// OP Back
					case Zone.OP_LOWER:
						checkObj[3] = "ORDER BY S1.NLEVEL, S1.NBAY DESC, S1.PRIORITY";
						break;
						
					//#CM34423
					//OP Front
					case Zone.OP_FRONT:
						checkObj[3] = "ORDER BY S1.NBAY DESC, S1.NLEVEL, S1.PRIORITY";
						break;
						  
					default:
						//#CM34424
						//Throw exception if the empty location search direction is not specified in definition file
						Object[] tObj = new Object[3] ;
						tObj[0] = this.getClass().getName() ;
						tObj[1] = "wDirection";
						tObj[2] = Integer.toString(tZone[0].getDirection()) ;
						String classname = (String)tObj[0];
						RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
						throw (new InvalidDefineException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
				}

				sqlstring = SimpleFormat.format(checkSQL, checkObj);

				rset = executeSQL(sqlstring, true);
				Shelf[] checkShelf = makeShelf(rset, 2) ;
				findSQL = findSQL + " {3}";
				fmtObj[1] = Integer.toString(tZone[i].getHardZone().getHardZoneID());
				fmtObj[2] = Integer.toString(tZone[i].getSoftZoneID());
				switch (tZone[i].getDirection())
				{
					//#CM34425
					//HP Back
					case Zone.HP_LOWER:
						fmtObj[3] = "ORDER BY NLEVEL, NBAY, PRIORITY FOR UPDATE";
						break;
						  
					//#CM34426
					// HP Front
					case Zone.HP_FRONT:
						fmtObj[3] = "ORDER BY NBAY, NLEVEL, PRIORITY FOR UPDATE";
						break;
						  
					//#CM34427
					// OP Back
					case Zone.OP_LOWER:
						fmtObj[3] = "ORDER BY NLEVEL, NBAY DESC, PRIORITY FOR UPDATE";
						break;
						  
					//#CM34428
					//OP Front
					case Zone.OP_FRONT:
						fmtObj[3] = "ORDER BY NBAY DESC, NLEVEL, PRIORITY FOR UPDATE";
						break;
						  
					default:
						//#CM34429
						//Throw exception if the empty location search direction is not specified in definition file
						Object[] tObj = new Object[3] ;
						tObj[0] = this.getClass().getName() ;
						tObj[1] = "wDirection";
						tObj[2] = Integer.toString(tZone[i].getDirection()) ;
						String classname = (String)tObj[0];
						RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
						throw (new InvalidDefineException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
				}

				sqlstring = SimpleFormat.format(findSQL, fmtObj);

				//#CM34430
				//run query and generate shelf instance based on the first data of result
				rset = executeSQL(sqlstring, true);
				//#CM34431
				// Generate only a single shelf instance
				fndStation = (DoubleDeepShelf[])makeShelf(rset, 3) ;
				if (fndStation.length > 0)
				{
					PaletteHandler pltHandle = new PaletteHandler(wConn);
					PaletteSearchKey pltSrchKey = null;
					if (checkShelf.length > 1)
					{
						//#CM34432
						// Allocate depth location if 2 or more empty pair is available
						for (int j = 0 ; j < fndStation.length; j++)
						{
							//#CM34433
							// If the empty location search result is front shelf
							if (fndStation[j].getSide() == BankSelect.NEAR)
							{
								pltSrchKey = new PaletteSearchKey();
								pltSrchKey.setCurrentStationNumber(fndStation[j].getPairStationNumber());
								Palette plt[] = (Palette[])pltHandle.find(pltSrchKey);
								//#CM34434
								// If there is no palette in the depth shelf, storage is not done in the front shelf
								if (plt == null || plt.length <= 0)
								{
									continue;
								}
								
								//#CM34435
								// If the depth shelf is reserved for picking or under picking, storage is not done in front shelf
								if (plt[0].getStatus() == Palette.RETRIEVAL_PLAN || plt[0].getStatus() == Palette.RETRIEVAL)
								{
									continue;
								}
								else
								{
									return fndStation[j];
								}
							}
							//#CM34436
							//If the empty location search result is depth shelf
							else
							{
								return fndStation[j];
							}
						}
					}
					//#CM34437
					//With empty pair single status, return null if the empty location search returns nothing
					else if (checkShelf.length == 1) 
					{
						for (int j = 0 ; j < fndStation.length ; j++)
						{
							if (checkShelf[0].getStationNumber().equals(fndStation[j].getStationNumber()))
							{
								continue;
							}
							else
							{
								String sqlPair = "SELECT * FROM SHELF WHERE PAIRSTATIONNUMBER ='"+checkShelf[0].getStationNumber()+"'";
								rset = executeSQL(sqlPair, true);
								Shelf pairShelf[] = makeShelf(rset, 1) ;
								if (pairShelf[0].getStationNumber().equals(fndStation[j].getStationNumber()))
								{
									continue;
								}
								else
								{
									pltSrchKey = new PaletteSearchKey();
									pltSrchKey.setCurrentStationNumber(fndStation[j].getPairStationNumber());	
									Palette plt[] = (Palette[])pltHandle.find(pltSrchKey);
									//#CM34438
									// If there is no palette in the depth shelf, storage is not done in the front shelf
									if (plt == null || plt.length <= 0)
									{
										return fndStation[j];
									}
									  
									//#CM34439
									// If the depth shelf is reserved for picking or under picking, storage is not done in front shelf
									if (plt[0].getStatus() == Palette.RETRIEVAL_PLAN || plt[0].getStatus() == Palette.RETRIEVAL)
									{
										return checkShelf[0];
									}
									else
									{
										return fndStation[j];
									}
								}
							}
						}
					}
					else
					{
						//#CM34440
						//Is it exception
						return null;
					}
				}
			}
		}
		catch (NotFoundException nfe)
		{
			//#CM34441
			//Can't be generated since it is find
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM34442
			//Can't be generated since it is find
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		
		return null;
	}

	//#CM34443
	/**
	 * Search empty location in case of doubledeep relocation
	 * Search shelf table using specified aisle, zone and return a shelf instance of an empty location
	 * Empty location here means both depth and front shelfs being empty
	 * Return null if no empty location exist
	 * Since this table locks the shelf table until the transaction is complete, please commit or rollback without fail
	 * @param tAisle aisle target for empty location search
	 * @param tZone zone target for empty location search
	 * @return Shelf instance 
	 * @throws ReadWriteException Throw any error during database processing
	 * @throws InvalidDefineException ShelfSelecotorIf empty location not specified in the definition file is specified, throw exception
	 */
	public Shelf findEmptyShelfForRackToRack(Aisle tAisle, Zone[] tZone) throws ReadWriteException, InvalidDefineException
	{
		ResultSet rset     = null;
		Shelf[] fndStation = null;
		String sqlstring   = null;
		try
		{
			String fmtSQL = "SELECT * FROM SHELF WHERE" +
						" PARENTSTATIONNUMBER = {0}" +
						" AND HARDZONEID = {1} " +
						" AND SOFTZONEID = {2} " +
						" AND PRESENCE = "     + Shelf.PRESENCE_EMPTY +
						" AND STATUS   = "     + Station.STATION_OK   +
						" AND ACCESSNGFLAG = " + Shelf.ACCESS_OK;
			
			//#CM34444
			// search the inner side of a pair shelf
			fmtSQL = "SELECT * FROM ("+fmtSQL+") S1, ("+fmtSQL+") S2"+
				" WHERE S1.STATIONNUMBER = S2.PAIRSTATIONNUMBER AND S1.PRIORITY < S2.PRIORITY {3}";

			Object[] fmtObj = new Object[4] ;

			fmtObj[0] = tAisle.getStationNumber();

			for (int i = 0 ; i < tZone.length ; i++)
			{
				fmtObj[1] = Integer.toString(tZone[i].getHardZone().getHardZoneID());
				fmtObj[2] = Integer.toString(tZone[i].getSoftZoneID());
				switch (tZone[i].getDirection())
				{
					//#CM34445
					//HP Back
					case Zone.HP_LOWER:
						fmtObj[3] = "ORDER BY S1.NLEVEL, S1.NBAY, S1.PRIORITY";
						break;
						
					//#CM34446
					//HP Front
					case Zone.HP_FRONT:
						fmtObj[3] = "ORDER BY S1.NBAY, S1.NLEVEL, S1.PRIORITY";
						break;
						
					//#CM34447
					//OP Back
					case Zone.OP_LOWER:
						fmtObj[3] = "ORDER BY S1.NLEVEL, S1.NBAY DESC, S1.PRIORITY";
						break;
						
					//#CM34448
					//OP Front
					case Zone.OP_FRONT:
						fmtObj[3] = "ORDER BY S1.NBAY DESC, S1.NLEVEL, S1.PRIORITY";
						break;
						
					default:
						//#CM34449
						//Throw exception if the empty location search direction is not specified in definition file
						Object[] tObj = new Object[3] ;
						tObj[0] = this.getClass().getName() ;
						tObj[1] = "wDirection";
						tObj[2] = Integer.toString(tZone[i].getDirection()) ;
						String classname = (String)tObj[0];
						RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
						throw (new InvalidDefineException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
				}

				sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
				//#CM34450
				//run query and generate shelf instance based on the first data of result
				rset = executeSQL(sqlstring, true);
				//#CM34451
				//Generate only a single shelf instance
				fndStation = makeShelf(rset, 1) ;
				if (fndStation.length > 0)
				{
					//#CM34452
					//Search with FOR UPDATE
					String sql = "SELECT * FROM SHELF WHERE STATIONNUMBER =" +fndStation[0].getStationNumber()+  " FOR UPDATE ";
					rset = executeSQL(sql, true);
					fndStation = makeShelf(rset, 1) ;
					return fndStation[0];
				}
			}
		}
		catch (NotFoundException nfe)
		{
			//#CM34453
			// Can't be generated since it is find
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM34454
			//Can't be generated since it is find
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}

		return null;
	}
	//#CM34455
	// Package methods -----------------------------------------------

	//#CM34456
	// Protected methods ---------------------------------------------

	//#CM34457
	/**
	 * Take each item from <code>ResultSet</code> and generate <code>Shelf</code> instance
	 * @param rset result set of shelf table search
	 * @param maxcreate max no. of shelf instance. if 0, generate all instance
	 * @return generated shelf instance array
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	protected Shelf[] makeShelf(ResultSet rset, int maxcreate) throws ReadWriteException
	{
		//#CM34458
		// temporary store for Shelf instances
		Vector tmpShelfVect = new Vector() ; 

		//#CM34459
		// data get from resultset and make new Shelf instance
		try
		{
			int count = 0;
			while(rset.next())
			{
				if ((maxcreate != 0) && (count > maxcreate))
				{
					//#CM34460
					// Gets out the loop when the cycle exceeded the ceiling number of of instance generation.
					break;
				}
				//#CM34461
				// Generates Shelf instance.
				DoubleDeepShelf tmpShelf = new DoubleDeepShelf(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				setShelf(rset, tmpShelf);
				
				//#CM34462
				// set pair location info
				setPairShelf(tmpShelf) ;
				tmpShelfVect.add(tmpShelf) ;
				
				//#CM34463
				// count up.
				count++;
			}
		}
		catch (InvalidDefineException e)
		{
			//#CM34464
			// notify if pair location is not found
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (SQLException e)
		{
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode()).toString() ;
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "ShelfHandler", tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM34465
		// move instance from vector to array of Shelf.
		DoubleDeepShelf[] rstarr = new DoubleDeepShelf[tmpShelfVect.size()] ;
		tmpShelfVect.copyInto(rstarr);

		return (rstarr) ;
	}

	//#CM34466
	// Private methods -----------------------------------------------
	//#CM34467
	/**
	 * Search Bank info for pair shelf with DoubleDeepShelf instance and add the result content to DoubleDeepShelf
	 * If the pair location is not found, throw InvalidDefineException
	 * @param shf DoubleDeepShelf instance that sets pair location info
	 * @return DoubleDeepShelf instance that includes pair location info
	 * @throws ReadWriteException Throw any error during database processing
	 * @throws InvalidDefineException Bank Throw exception if the pair location info is not found in the shelf table as per definition
	 * @throws InvalidDefineException Bank Throw exception if there is any false setting in the definition (BANKSELECT)
	 * @throws InvalidDefineException Throw exception if the contents of the pair location info is not proper
	 */
	private void setPairShelf(DoubleDeepShelf shf) throws ReadWriteException, InvalidDefineException
	{
		//#CM34468
		// for database access
		Statement stmt = null;
		ResultSet rset = null ;

		//#CM34469
		//SQL that fetches Bank info based on warehouse, aisle station, bank
		String fmtSQL  = "SELECT PRESENCE FROM SHELF WHERE PAIRSTATIONNUMBER = {0}" ;

		Object[]  fmtObj = new Object[1] ;
		fmtObj[0] = DBFormat.format(shf.getStationNumber());

		try
		{
			stmt = wConn.createStatement();
			//#CM34470
			//acquire pair bank, this side, shell depth type
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);
			rset = stmt.executeQuery(sqlstring);
			if (rset.next())
			{
				try
				{
					//#CM34471
					//fetch PRESENCE
					shf.setPairPresence(rset.getInt("PRESENCE"));
				}
				catch (InvalidStatusException e)
				{
					//#CM34472
					//if the contents to be set has improper characters, return exception
					throw new InvalidDefineException();
				}
			}
			else
			{
				//#CM34473
				//Throw exception if location info does not exist
				throw new InvalidDefineException();
			}
		}
		catch (SQLException e)
		{
			//#CM34474
			//call error log output process
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(e.getErrorCode()).toString() ;
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "ShelfHandler", tObj);
			//#CM34475
			//throw ReadWriteException error message
			throw (new ReadWriteException("6007030" + wDelim + tObj[0]));
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM34476
				//call error log output process
				e.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(e.getErrorCode()).toString() ;
				tObj[1] = wSW.toString() ;
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "ShelfHandler", tObj);
				//#CM34477
				//throw ReadWriteException error message
				throw (new ReadWriteException("6007030" + wDelim + "Shelf"));
			}
		}
	}
}
//#CM34478
//end of class

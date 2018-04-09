//#CM48410
//$Id: ToolStationFinder.java,v 1.2 2006/10/30 02:17:14 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48411
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
import java.sql.Timestamp;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM48412
/**<en>
 * This class is used to search the Station table from database and to map into  
 * <CODE>ToolStationFinder</CODE>.
 * This class is used when displaying the list of workshop.<BR>
 * This class will be used when displaying the list of search results on the screen.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:14 $
 * @author  $Author: suresh $
 </en>*/
public class ToolStationFinder extends ToolStationHandler implements ToolDatabaseFinder
{
	//#CM48413
	// Class fields --------------------------------------------------

	//#CM48414
	// Class variables -----------------------------------------------
	//#CM48415
	/**<en> name of the table </en>*/


	private String wTableName = "TEMP_STATION";
	//#CM48416
	/**<en>
	 * Variables which control the statements.
	 </en>*/
	protected Statement wStatement = null ;

	//#CM48417
	/**<en>
	 * Variables which preserve the search results
	 </en>*/
	protected ResultSet wResultSet = null ;

	//#CM48418
	// Class method --------------------------------------------------
	//#CM48419
	/**<en>
	 * Returns the version of this class.
	 * @return :Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:14 $") ;
	}

	//#CM48420
	// Constructors --------------------------------------------------
	//#CM48421
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolStationFinder(Connection conn)
	{
		super(conn) ;
	}
	//#CM48422
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolStationFinder(Connection conn, String tablename)
	{
		super(conn) ;
		wTableName = tablename;
	}

	//#CM48423
	// Public methods ------------------------------------------------
	//#CM48424
	/**<en>
	 * Generate a statement and open cursors.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public void open() throws ReadWriteException
	{
		try
		{
			wStatement = wConn.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE, 
											  	ResultSet.CONCUR_READ_ONLY
										      ) ;
		}
		catch (SQLException e)
		{
			//#CM48425
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48426
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw new ReadWriteException("6126001" + wDelim + Integer.toString(e.getErrorCode())) ;
		}
	}

	//#CM48427
	/**<en>
	 * Return the result of database search in form of entity array.
	 * @param :start position of the search result
	 * @param :end position of the search result
	 * @return :entity array
 	 * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
 	 * @throws InvalidStatusException :Notifies if error was found in the scope of search specified.
	 </en>*/
	public ToolEntity[] getEntitys(int start, int end) throws ReadWriteException, InvalidStatusException
	{
		Vector 		vec			= new Vector();
		Station[]     stationarray  = null;
		Station tmpst = null;

		try
		{
			//#CM48428
			//<en> number of data to display</en>
			int count = end - start;

			if (wResultSet.absolute(start+1))
			{
				for (int i = 0; i < count; i++)
				{
					if(i > 0)
					{
						wResultSet.next();
					}

					String clsname = DBFormat.replace(wResultSet.getString("CLASSNAME"));
					tmpst = new Station(DBFormat.replace(wResultSet.getString("STATIONNUMBER"))) ;

//#CM48429
//					if (StringUtil.isBlank(clsname))
//#CM48430
//					{
//#CM48431
//						// station number
//#CM48432
//						tmpst = new Station(DBFormat.replace(wResultSet.getString("STATIONNUMBER"))) ;
//#CM48433
//					}
//#CM48434
//					else
//#CM48435
//					{
//#CM48436
//						// load class
//#CM48437
//						Class lclass = Class.forName(clsname) ;
//#CM48438
//						Class[] typeparams = new Class[1] ;
//#CM48439
//						typeparams[0] = Class.forName("java.lang.String") ;
//#CM48440
//						Constructor cconst = lclass.getConstructor(typeparams) ;
//#CM48441
//						// set actual parameter
//#CM48442
//						Object[] tparams = new Object[1] ;
//#CM48443
//						tparams[0] = DBFormat.replace(wResultSet.getString("STATIONNUMBER"));
//#CM48444
//						// getting Object
//#CM48445
//						Object tgt = cconst.newInstance(tparams) ;
//#CM48446
//						if (tgt instanceof Station)
//#CM48447
//						{
//#CM48448
//							tmpst = (Station)tgt; 
//#CM48449
//						}
//#CM48450
//						else
//#CM48451
//						{
//#CM48452
//							Object[] tObj = new Object[1] ;
//#CM48453
//							tObj[0] = wTableName ;
//#CM48454
//							RmiMsgLogClient.write(6126011, LogMessage.F_ERROR, this.getClass().getName(), tObj);
//#CM48455
//							throw (new InvalidDefineException("6126011" + wDelim + tObj[0])) ;
//#CM48456
//						}
//#CM48457
//
//#CM48458
//					}
					//#CM48459
					// max palette quantity</

					tmpst.setMaxPaletteQuantity(wResultSet.getInt("MAXPALETTEQUANTITY")) ;
					//#CM48460
					//<en> max number of carry instructions</en>
					tmpst.setMaxInstruction(wResultSet.getInt("MAXINSTRUCTION")) ;
					//#CM48461
					// sendable
					boolean snd = (wResultSet.getInt("SENDABLE") == Station.SENDABLE) ;
					tmpst.setSendable(snd) ;
					//#CM48462
					// station status
					tmpst.setStatus(wResultSet.getInt("STATUS")) ;
					//#CM48463
					// group controller
					tmpst.setGroupController(GroupController.getInstance(wConn, wResultSet.getInt("CONTROLLERNUMBER"))) ;
					//#CM48464
					// station type
					tmpst.setType(wResultSet.getInt("STATIONTYPE")) ;
					//#CM48465
					// setting type
					tmpst.setInSettingType(wResultSet.getInt("SETTINGTYPE")) ;
					//#CM48466
					// workplace type
					tmpst.setWorkPlaceType(wResultSet.getInt("WORKPLACETYPE")) ;
					//#CM48467
					// operationdisplay
					tmpst.setOperationDisplay(wResultSet.getInt("OPERATIONDISPLAY")) ;
					//#CM48468
					// station name
					tmpst.setName(DBFormat.replace(wResultSet.getString("STATIONNAME"))) ;
					//#CM48469
					// suspend
					boolean sus = (wResultSet.getInt("SUSPEND") == Station.SUSPEND) ;
					tmpst.setSuspend(sus) ;
					//#CM48470
					// arrivalCheck
					boolean arr = (wResultSet.getInt("ARRIVALCHECK") == Station.ARRIVALCHECK) ;
					tmpst.setArrivalCheck(arr) ;
					//#CM48471
					// loadSizeCheck
					boolean load = (wResultSet.getInt("LOADSIZECHECK") == Station.LOADSIZECHECK) ;
					tmpst.setLoadSizeCheck(load) ;
					//#CM48472
					//<en> removal</en>
					boolean pay = (wResultSet.getInt("REMOVE") == Station.PAYOUT_OK) ;
					tmpst.setRemove(pay) ;
					//#CM48473
					// inventory check Flag
					tmpst.setInventoryCheckFlag(wResultSet.getInt("INVENTORYCHECKFLAG")) ;
					//#CM48474
					// Restoring Operation
					boolean res = (wResultSet.getInt("RESTORINGOPERATION") == Station.CREATE_RESTORING) ;
					tmpst.setReStoringOperation(res) ;
					//#CM48475
					// Restoring Instruction
					tmpst.setReStoringInstruction(wResultSet.getInt("RESTORINGINSTRUCTION")) ;
					//#CM48476
					// PoperationNeed
					boolean pop = (wResultSet.getInt("POPERATIONNEED") == Station.POPERATIONNEED) ;
					tmpst.setPoperationNeed(pop) ;
					//#CM48477
					// WareHouse
					tmpst.setWarehouseStationNumber(DBFormat.replace(wResultSet.getString("WHSTATIONNUMBER")));
					//#CM48478
					// station parent station
					tmpst.setParentStationNumber(DBFormat.replace(wResultSet.getString("PARENTSTATIONNUMBER")));
					//#CM48479
					// ails station number
					tmpst.setAisleStationNumber(DBFormat.replace(wResultSet.getString("AISLESTATIONNUMBER"))) ;
					//#CM48480
					// next station number
					tmpst.setNextStationNumber(DBFormat.replace(wResultSet.getString("NEXTSTATIONNUMBER"))) ;
					//#CM48481
					// last station number
					tmpst.setLastUsedStationNumber(DBFormat.replace(wResultSet.getString("LASTUSEDSTATIONNUMBER"))) ;
					//#CM48482
					// reject station number
					tmpst.setRejectStationNumber(DBFormat.replace(wResultSet.getString("REJECTSTATIONNUMBER")));
					//#CM48483
					// mode type
					tmpst.setModeType(wResultSet.getInt("MODETYPE")) ;
					//#CM48484
					// cyrrent mode
					tmpst.setCurrentMode(wResultSet.getInt("CURRENTMODE")) ;
					//#CM48485
					// change mode request
					tmpst.setChangeModeRequest(wResultSet.getInt("MODEREQUEST")) ;
					//#CM48486
					// change mode request time 
					Timestamp tims = wResultSet.getTimestamp("MODEREQUESTTIME");
					java.util.Date mdate = null;
					if (tims != null)
						mdate = new java.util.Date(tims.getTime());
					else
						mdate = null;
					tmpst.setChangeModeRequestTime(mdate) ;
					//#CM48487
					// cached carrykey
					tmpst.setCarryKey(DBFormat.replace(wResultSet.getString("CARRYKEY"))) ;
					//#CM48488
					// cached height of palette
					tmpst.setHeight(wResultSet.getInt("HEIGHT")) ;
					//#CM48489
					// cached bar code data of palette
					tmpst.setBCData(DBFormat.replace(wResultSet.getString("BCDATA"))) ;
					//#CM48490
					// class name
					tmpst.setClassName(DBFormat.replace(wResultSet.getString("CLASSNAME"))) ;

					//#CM48491
					// station handler
					tmpst.setHandler(this) ;

					//#CM48492
					// append new Station instance to Vector
					vec.addElement(tmpst) ;

				}

				stationarray = new Station[vec.size()];
				vec.copyInto(stationarray);
			}
			else
			{
				//#CM48493
				//<en> Incorrect line has been selected.</en>
				RmiMsgLogClient.write(6126012, LogMessage.F_ERROR, this.getClass().getName(), null);
				throw new InvalidStatusException("6126012");
			}
		}
		catch(SQLException e)
		{
			//#CM48494
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48495
			//<en>Here, the ReadWriteException will be thrown with an error message</en>
			throw (new ReadWriteException("6126001" + wDelim + wTableName)) ;
		}
		catch (Exception e)
		{
			if (e instanceof ReadWriteException)
			{
				throw (ReadWriteException)e;
			}
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = wTableName ;
			tObj[1] = wSW.toString() ;
			RmiMsgLogClient.write(6126003, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw (new ReadWriteException("6126003" + wDelim + tObj[0])) ;
		}
		return stationarray;
	}

	//#CM48496
	/**<en>
	 * Search database and return the entity array.
	 * @return :the entity array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] next() throws ReadWriteException
	{
		ToolEntity[] toolentity = null;
		return toolentity;
	}

	//#CM48497
	/**<en>
	 * Search database and return the object
	 * @return :the entity array
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] back() throws ReadWriteException
	{
		ToolEntity[] toolentity = null;
		return toolentity;
	}

	//#CM48498
	/**<en>
	 * Close the statement.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public void close() throws ReadWriteException
	{
		try
		{
			wStatement.close() ;
			wStatement = null ;
		}
		catch (Exception e)
		{
		}
	}

	//#CM48499
	/**<en>
	 * Conduct search in station control table. This method will be used when the station no. is sprcified.
	 * Target data of this method will be the data of station type [3:storage/retireval available] and of 
	 * mode switch type anything other than [0:no mode switching, 2:AGC mode switch].
	 * Please be certain of this condition when using thie method.
	 * @param  stnumber  :station no.
	 * @return :number of search result
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
	 </en>*/
	public int search(String stnumber) throws ReadWriteException
	{
		int count = 0;
		ResultSet countret = null ;
		Object fmtObj[] = new Object[1];
		try
	 	{
			String fmtCountSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + 
								 " WHERE STATIONTYPE = 3 " +
								 " AND MODETYPE not in (0,2)  {0} ";

			String fmtSQL = "SELECT * FROM "+ wTableName +
								 " WHERE STATIONTYPE = 3 " +
								 " AND MODETYPE not in (0,2)  {0} ";

			if ((stnumber != null) && (!stnumber.equals("")))
			{
					fmtObj[0] = "AND STATIONNUMBER = '" + stnumber + "'" ;
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj) ;
			countret = wStatement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM48500
			//<en>Carry out the search only when the number of data is lower than MAXDISP.</en>

			if ( count <= MAXDISP )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
				wResultSet = wStatement.executeQuery(sqlstring);
			}
			else
			{
				wResultSet = null;
			}
		}
		catch(SQLException e)
		{
			//#CM48501
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48502
			//<en>Here, the ReadWriteException will be thrown with an error message</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName)) ;
		}
		finally
		{
		}
		return count;
	}

	//#CM48503
	/**<en>
	 * Search the station control table based on the specified SerchKey.
	 * Please be careful when using this method.
	 * @param key   :Key for the search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
	 </en>*/
	public int search(ToolSearchKey key) throws ReadWriteException
	{
		int count = 0;
		ResultSet countret = null ;
		Object fmtObj[] = new Object[2];
		try
	 	{
			String fmtCountSQL = "SELECT COUNT(1) COUNT FROM "+ wTableName +
								 "  {0} {1} ";

			String fmtSQL = "SELECT * FROM "+ wTableName +
								 "  {0} {1} ";

			if (key.ReferenceCondition() != null)
			{
				fmtObj[0] = "WHERE " + key.ReferenceCondition();
				if (key.SortCondition() != null)
				{
					fmtObj[1] = "ORDER BY " + key.SortCondition();
				}
			}
			else if (key.SortCondition() != null)
			{
				fmtObj[0] = "ORDER BY " + key.SortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj) ;
			countret = wStatement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM48504
			//<en>Carry out the search only when the number of data is lower than MAXDISP.</en>

			if ( count <= MAXDISP )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
				wResultSet = wStatement.executeQuery(sqlstring);
			}
			else
			{
				wResultSet = null;
			}
		}
		catch(SQLException e)
		{
			//#CM48505
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48506
			//<en>Here, the ReadWriteException will be thrown with an error message</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName)) ;
		}
		finally
		{
		}
		return count;
	}
	//#CM48507
	/**<en>
	 * Search the station control table based on the specified SerchKey.
	 * Please be careful when using this method.
	 * @param key   :Key for the search
	 * @param ptst  :Key for the search
	 * @param flg   :Key for the search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database. 
	 </en>*/
	public int search(ToolSearchKey key, String parentst, int flg) throws ReadWriteException
	{
		int count = 0;
		ResultSet countret = null ;
		Object fmtObj[] = new Object[3];
		try
	 	{
			String fmtCountSQL = "SELECT COUNT(1) COUNT FROM "+ wTableName +
								 "  {0} {1} {2}";

			String fmtSQL = "SELECT * FROM "+ wTableName +
								 "  {0} {1} {2}";

			if(flg == 0)
			{
				//#CM48508
				/*2004/11/29 MODIFY INOUE START*/

				//#CM48509
				//fmtObj[0] = " WHERE STATIONNUMBER != '" + parentst + "' AND AISLESTATIONNUMBER != ' ' ";
				fmtObj[0] = " WHERE STATIONNUMBER != '" + parentst + "' AND (TRIM(AISLESTATIONNUMBER) IS NOT NULL OR WORKPLACETYPE ="+Station.STAND_ALONE_STATIONS+")";
				//#CM48510
				/*2004/11/29 MODIFY INOUE END*/

			}
			else
			{
				fmtObj[0] = " WHERE STATIONNUMBER != '" + parentst + "' AND TRIM(AISLESTATIONNUMBER) IS NULL ";
			}

			if (key.ReferenceCondition() != null)
			{
				fmtObj[1] = "AND " + key.ReferenceCondition();
				if (key.SortCondition() != null)
				{
					fmtObj[2] = "ORDER BY " + key.SortCondition();
				}
			}
			else if (key.SortCondition() != null)
			{
				fmtObj[1] = "ORDER BY " + key.SortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj) ;
System.out.println("SQL[" + sqlcountstring + "]");
			countret = wStatement.executeQuery(sqlcountstring);

			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM48511
			//<en>Carry out the search only when the number of data is lower than MAXDISP.</en>
			if ( count <= MAXDISP )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
				wResultSet = wStatement.executeQuery(sqlstring);
			}
			else
			{
				wResultSet = null;
			}
		}
		catch(SQLException e)
		{
			//#CM48512
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48513
			//<en>Here, the ReadWriteException will be thrown with an error message</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName)) ;
		}
		finally
		{
		}
		return count;
	}
	//#CM48514
	// Package methods -----------------------------------------------

	//#CM48515
	// Protected methods ---------------------------------------------

	//#CM48516
	// Private methods -----------------------------------------------
}

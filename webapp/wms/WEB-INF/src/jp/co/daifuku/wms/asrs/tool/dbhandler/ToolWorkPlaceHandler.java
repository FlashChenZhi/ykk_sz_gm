//#CM49153
//$Id: ToolWorkPlaceHandler.java,v 1.2 2006/10/30 02:17:09 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM49154
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM49155
/**<en>
 * This class is used to retrieve/store the <code>WorkPlace</code> class from/to database.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:09 $
 * @author  $Author: suresh $
 </en>*/
public class ToolWorkPlaceHandler extends ToolStationHandler
{

	//#CM49156
	// Class fields --------------------------------------------------
	
	private static final String WORKPLACE_HANDLE = "jp.co.daifuku.wms.asrs.dbhandler.ASWorkPlaceHandler";
	
	//#CM49157
	// Class variables -----------------------------------------------
	//#CM49158
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_STATION";


	//#CM49159
	// Class method --------------------------------------------------
	//#CM49160
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:09 $") ;
	}

	//#CM49161
	// Constructors --------------------------------------------------
	//#CM49162
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolWorkPlaceHandler(Connection conn)
	{
		super (conn) ;
	}
	//#CM49163
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolWorkPlaceHandler(Connection conn, String tablename)
	{
		super (conn) ;
		wTableName = tablename;
	}

	//#CM49164
	// Public methods ------------------------------------------------

	//#CM49165
	/**<en>
	 * Create new information in database.
	 * @param tgt :entity instance which has the information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if the same warehouse has been registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		//#CM49166
		//-------------------------------------------------
		//#CM49167
		// variable define
		//#CM49168
		//-------------------------------------------------
		String fmtSQL = "INSERT INTO "+wTableName+" (" +
						"  STATIONNUMBER" +				// 0
						", MAXPALETTEQUANTITY" +		// 1
						", MAXINSTRUCTION" +			// 2
						", SENDABLE" +					// 3
						", STATUS" +					// 4
						", CONTROLLERNUMBER" +			// 5
						", STATIONTYPE" +				// 6
						", SETTINGTYPE" +				// 7
						", WORKPLACETYPE" +				// 8
						", OPERATIONDISPLAY" +			// 9
						", STATIONNAME" +				// 10
						", SUSPEND" +					// 11
						", ARRIVALCHECK" +				// 12
						", LOADSIZECHECK" +				// 13
						", REMOVE" + 					// 14
						", INVENTORYCHECKFLAG" +		// 15
						", RESTORINGOPERATION" + 		// 16
						", RESTORINGINSTRUCTION" + 		// 17
						", POPERATIONNEED" +			// 18
						", WHSTATIONNUMBER" +			// 19
						", PARENTSTATIONNUMBER" +		// 20
						", AISLESTATIONNUMBER" +			// 21
						", NEXTSTATIONNUMBER" +			// 22
						", LASTUSEDSTATIONNUMBER" +		// 23
						", REJECTSTATIONNUMBER" +		// 24
						", MODETYPE" +					// 25
						", CURRENTMODE" +				// 26
						", MODEREQUEST" +				// 27
						", MODEREQUESTTIME" +			// 28
						", CARRYKEY" +					// 29
						", HEIGHT" +					// 30
						", BCDATA" +					// 31
						", CLASSNAME" +					// 32
						") VALUES (" +
						"{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21},{22},{23},{24},{25},{26},{27},{28},{29},{30},{31},{32}" +
						")" ;

		String fmtSQL_StationType = "INSERT INTO "+wStationTypeTableName+" (" +
						"  STATIONNUMBER" +				// 0
						",  HANDLERCLASS" +				// 1
						") VALUES (" +
						"{0}, '" + WORKPLACE_HANDLE + "'" + 
						")" ;
						


		//#CM49169
		// for database access
		ResultSet rset = null ;

		Station tgtSt ;
		String sqlstring ;
		//#CM49170
		//-------------------------------------------------
		//#CM49171
		// process
		//#CM49172
		//-------------------------------------------------
		if (tgt instanceof Station)
		{
			tgtSt = (Station)tgt ;
		}
		else
		{
			//#CM49173
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Station Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM49174
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtSt) ;
			//#CM49175
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM49176
			// execute the sql
			executeSQL(sqlstring, false) ;

			//#CM49177
			//for stationtype table.
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			//#CM49178
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM49179
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM49180
	/**<en>
	 * Modify the information in database.
	 * @param tgt :entity instance which has the information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data of warehouse to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM49181
		//<en> UPdate DB.</en>
		//#CM49182
		//<en>-------------------------------------------------</en>
		//#CM49183
		//<en> variable define</en>
		//#CM49184
		//<en>-------------------------------------------------</en>
		String fmtSQL = "UPDATE "+wTableName+" set" +
						"  MAXPALETTEQUANTITY = {1} " +			// 1
						", MAXINSTRUCTION = {2} " +				// 2
						", SENDABLE = {3} " +					// 3
						", STATUS = {4} " +						// 4
						", CONTROLLERNUMBER = {5} " +			// 5
						", STATIONTYPE = {6} " +				// 6
						", SETTINGTYPE = {7} " +				// 7
						", WORKPLACETYPE = {8} " +				// 8
						", OPERATIONDISPLAY = {9} " +			// 9
						", STATIONNAME = {10} " +				// 10
						", SUSPEND = {11} " +					// 11
						", ARRIVALCHECK = {12} " +				// 12
						", LOADSIZECHECK = {13} " +				// 13
						", REMOVE = {14} " +					// 14
						", INVENTORYCHECKFLAG = {15} " +		// 15
						", RESTORINGOPERATION = {16} " + 		// 16
						", RESTORINGINSTRUCTION = {17} " + 		// 17
						", POPERATIONNEED = {18} " +			// 18
						", WHSTATIONNUMBER = {19} " +			// 19
						", PARENTSTATIONNUMBER = {20} " +		// 20
						", AISLESTATIONNUMBER = {21} " +			// 21
						", NEXTSTATIONNUMBER = {22} " +			// 22
						", LASTUSEDSTATIONNUMBER = {23} " +		// 23
						", REJECTSTATIONNUMBER = {24} " +		// 24
						", MODETYPE = {25} " +					// 25
						", CURRENTMODE = {26} " +				// 26
						", MODEREQUEST = {27} " +				// 27
						", MODEREQUESTTIME = {28} " +			// 28
						", CARRYKEY = {29} " +					// 29
						", HEIGHT = {30} " +					// 30
						", BCDATA = {31} " +					// 31
						", CLASSNAME = {32} " +					// 32
						" WHERE STATIONNUMBER = {0}" ;			// 0

		//#CM49185
		// for database access
		ResultSet rset = null ;

		Station tgtSt ;
		String sqlstring ;
		//#CM49186
		//-------------------------------------------------
		//#CM49187
		// process
		//#CM49188
		//-------------------------------------------------
		if (tgt instanceof Station)
		{
			tgtSt = (Station)tgt ;
		}
		else
		{
			//#CM49189
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Station Instance.", "ToolStationHandler");
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM49190
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtSt) ;
			//#CM49191
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM49192
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM49193
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM49194
	/**<en>
	 * Modify the shelf information in database. The contents and conditions of the modificaiton will be 
	 * obtained by ToolAlterKey.
	 * @param  key :ToolAlterKey instance preserved by contents and conditions of the modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies when data to modify cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM49195
		//-------------------------------------------------
		//#CM49196
		// variable define
		//#CM49197
		//-------------------------------------------------
		Station[] fndStation = null ;
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM49198
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM49199
			//<en> Exception if the update values have not been set.</en>
			Object[] tobj = {table};
			//#CM49200
			//<en>6126005 = Cannot update database; the update value has not been set. TABLE={0}</en>
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolStationHandler", tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM49201
			//<en> Exception if the update conditions have not been set.</en>
			Object[] tobj = {table};
			//#CM49202
			//<en>6126006 = Cannot update database; the update conditions have not been set.  TABLE={0}</en>
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolStationHandler", tobj);
			throw (new InvalidDefineException("6126006"));
		}
		fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
		try
		{
			rset = executeSQL(sqlstring, false) ;	// private exec sql method
		}
		catch (DataExistsException dee)
		{
			//#CM49203
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM49204
	/**<en>
	 * Delete from database the warehouse information which has been passed through parameter.
	 * @param tgt :entity instance which has the informtion to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM49205
		//<en> Delet from DB</en>
		//#CM49206
		//<en>-------------------------------------------------</en>
		//#CM49207
		//<en> variable define</en>
		//#CM49208
		//<en>-------------------------------------------------</en>
		String fmtSQL = "DELETE FROM " + wTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0

		String fmtSQL_StationType = "DELETE FROM "+wStationTypeTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0


		//#CM49209
		// for database access
		ResultSet rset = null ;

		Station tgtSt ;
		String sqlstring ;
		//#CM49210
		//-------------------------------------------------
		//#CM49211
		// process
		//#CM49212
		//-------------------------------------------------
		if (tgt instanceof Station)
		{
			tgtSt = (Station)tgt ;
		}
		else
		{
			//#CM49213
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Station Instance.", "ToolStationHandler");
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM49214
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtSt) ;
			//#CM49215
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM49216
			// execute the sql
			executeSQL(sqlstring, false) ;

			//#CM49217
			//for stationtype table.
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			//#CM49218
			// execute the sql
			executeSQL(sqlstring, false) ;

		}
		catch (DataExistsException dee)
		{
			//#CM49219
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM49220
	/**<en>
	 * Delete from database the information that match the key which has been passed through parameter.
	 * @param key :key of the data to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
	{
		//#CM49221
		//<en> Delete from DB.</en>
		ToolEntity[] tgts = find(key) ;
		for (int i=0; i < tgts.length; i++)
		{
			drop(tgts[i]) ;
		}
	}

	//#CM49222
	// Package methods -----------------------------------------------

	//#CM49223
	// Protected methods ---------------------------------------------

	//#CM49224
	// Private methods -----------------------------------------------
}
//#CM49225
//end of class


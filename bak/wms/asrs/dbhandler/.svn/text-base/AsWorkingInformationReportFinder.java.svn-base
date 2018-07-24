// $Id: AsWorkingInformationReportFinder.java,v 1.2 2006/10/30 06:57:45 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.entity.AsWorkingInformation;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationReportFinder;
import jp.co.daifuku.wms.base.entity.AbstractEntity;


//#CM34326
/**
 * This <CODE>AsWorkingInformationReportFinder</CODE> class operates the database (table)
 * This class is used to search data that relates to DNWORKINFO and CARRYINFO
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>new</b></td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/10/30 06:57:45 $
 * @author  C.Kaminishizono
 * @author  Last commit: $Author: suresh $
 */
public class AsWorkingInformationReportFinder extends WorkingInformationReportFinder
{
	//#CM34327
	// Class variables -----------------------------------------------

	//#CM34328
	// Constructors --------------------------------------------------
	//#CM34329
	/**
	 * specify database connection and create instance
	 * @param conn database connection object
	 */
	public AsWorkingInformationReportFinder(Connection conn)
	{
		super(conn);
	}

	//#CM34330
	/**
	 * @see dbhandler.AbstractDBFinder#createEntity()
	 */	
	protected AbstractEntity createEntity()
	{
		return (new AsWorkingInformation()) ;
	}

	//#CM34331
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:57:45 $") ;
	}

	//#CM34332
	// Public methods ------------------------------------------------
	//#CM34333
	/**
	 * search database and fetch results
	 * @param key  search key
	 * @return search result count
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int search(SearchKey key) throws ReadWriteException
	{
		close();
		open();
		
		setTableName("DNWORKINFO, CARRYINFO");

		Object[] fmtObj = new Object[4];
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3}";
				
			//#CM34334
			// fetch conditions
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				//#CM34335
				//If the fetch conditions are not specified, fetch all the items
				cntObj[0] = " * ";
			}
			
			//#CM34336
			// fetch conditions
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			} 
			else 
			{
				//#CM34337
				// If the fetch conditions are not specified, fetch all the items
				fmtObj[0] = " * " ;
			}

			//#CM34338
			// add search conditions
			fmtObj[1] = "WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;
			cntObj[1] = "WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;

			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + "AND " + key.getReferenceCondition();
				cntObj[1] = cntObj[1] + "AND " + key.getReferenceCondition();
			}

			//#CM34339
			// add collect condition			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			//#CM34340
			// reading order				
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = "ORDER BY " + key.getSortCondition();
				cntObj[3] = "ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			//#CM34341
			//execute search if the count is equal to 1 or more
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM34342
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}

	//#CM34343
	/**
	 * search database and fetch results
	 * @param key  key to search work info
	 * @param ckey carry info search key
	 * @return search result count
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int search(SearchKey key, SearchKey ckey) throws ReadWriteException
	{
		close();
		open();
		
		setTableName("DNWORKINFO, CARRYINFO");

		Object[] fmtObj = new Object[4];
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3}";
				
			//#CM34344
			// fetch conditions
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();

				if (ckey.getCollectConditionForCount() != null)
				{
					cntObj[0] = cntObj[0] + ckey.getCollectConditionForCount();
				}
			}
			else
			{
				if (ckey.getCollectConditionForCount() != null)
				{
					cntObj[0] = ckey.getCollectConditionForCount();
				}
				else
				{
					//#CM34345
					// If the fetch conditions are not specified, fetch all the items
					cntObj[0] = " * ";
				}
			}
			
			//#CM34346
			// fetch conditions
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;

				if (ckey.getCollectCondition() != null)
				{
					fmtObj[0] = fmtObj[0] + ckey.getCollectCondition() ;
				} 
			} 
			else 
			{
				if (ckey.getCollectCondition() != null)
				{
					fmtObj[0] = fmtObj[0] + ckey.getCollectCondition() ;
				} 
				else
				{
					//#CM34347
					// If the fetch conditions are not specified, fetch all the items
					fmtObj[0] = " * " ;
				}
			}

			//#CM34348
			// add search conditions
			fmtObj[1] = " WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;
			cntObj[1] = " WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;

			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = fmtObj[1] + " AND " + key.getReferenceCondition();
				cntObj[1] = cntObj[1] + " AND " + key.getReferenceCondition();

				if (ckey.getReferenceCondition() != null)
				{
					fmtObj[1] = fmtObj[1] + " AND " + ckey.getReferenceCondition();
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}
			else
			{
				if (ckey.getReferenceCondition() != null)
				{
					fmtObj[1] = fmtObj[1] + " AND " + ckey.getReferenceCondition();
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}

			//#CM34349
			// add collect condition			
			if (ckey.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + ckey.getGroupCondition();
				cntObj[2] = " GROUP BY " + ckey.getGroupCondition();

				if (key.getGroupCondition() != null)
				{
					fmtObj[2] = fmtObj[2] + ", " + key.getGroupCondition();
					cntObj[2] = cntObj[2] + ", " + key.getGroupCondition();
				}
			}
			else
			{
				if (key.getGroupCondition() != null)
				{
					fmtObj[2] = " GROUP BY " + key.getGroupCondition();
					cntObj[2] = " GROUP BY " + key.getGroupCondition();
				}
			}

			//#CM34350
			// reading order
			String[] _ckeySortTable = null;
			String[] _wkeySortTable = null;
			
			_ckeySortTable = ckey.getSortConditionTable();
			_wkeySortTable = key.getSortConditionTable();
			
			int _tableCount = 0;
			if (_ckeySortTable != null)
			{
				_tableCount = _ckeySortTable.length;
			}
			if (_wkeySortTable != null)
			{
				_tableCount = _tableCount + _wkeySortTable.length;
			}
			
			int[] _keyTableOrder = new int[_tableCount];
			String[] _keyOrderColumn = new String[_tableCount];
			int _plc = 0;

			if (_ckeySortTable != null)
			{
				for (int _slc=0; _slc<_ckeySortTable.length; _slc++, _plc++)
				{
					int _idx = _ckeySortTable[_slc].indexOf(",");
					_keyTableOrder[_plc] = Integer.parseInt(_ckeySortTable[_slc].substring(0, _idx));
					_keyOrderColumn[_plc] = _ckeySortTable[_slc].substring(_idx+1);
				}
			}

			if (_wkeySortTable != null)
			{
				for (int _slc=0; _slc<_wkeySortTable.length; _slc++, _plc++)
				{
					int _idx = _wkeySortTable[_slc].indexOf(",");
					_keyTableOrder[_plc] = Integer.parseInt(_wkeySortTable[_slc].substring(0, _idx));
					_keyOrderColumn[_plc] = _wkeySortTable[_slc].substring(_idx+1);
				}
			}
			if (_plc > 0)
			{
				for (int _stpt=0; _stpt<_plc; _stpt++)
				{
					for (int _ckpt=_stpt; _ckpt<_plc; _ckpt++)
					{
						if (_keyTableOrder[_stpt] > _keyTableOrder[_ckpt])
						{
							int _svOrder = _keyTableOrder[_stpt];
							String _svSortCond = _keyOrderColumn[_stpt];
							_keyTableOrder[_stpt] = _keyTableOrder[_ckpt];
							_keyOrderColumn[_stpt] = _keyOrderColumn[_ckpt];
							_keyTableOrder[_ckpt] = _svOrder;
							_keyOrderColumn[_ckpt] = _svSortCond;
						}
					}
				}
				String _setSortCondition = "";
				for (int _stpt=0; _stpt<_plc; _stpt++)
				{
					_setSortCondition = _setSortCondition + _keyOrderColumn[_stpt] + ", ";
				}
				int _ep = _setSortCondition.lastIndexOf(",") ;

				fmtObj[3] = " ORDER BY " + _setSortCondition.substring(0, _ep);
				cntObj[3] = " ORDER BY " + _setSortCondition.substring(0, _ep);
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			//#CM34351
			//execute search if the count is equal to 1 or more
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM34352
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;

	}

	//#CM34353
	/**
	 * Search the database using the parameter info and return the result count
	 * @param key[] search key array
	 * @return search result count
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public int searchStock(SearchKey[] key) throws ReadWriteException
	{
		close();
		open();
		setTableName("DNWORKINFO, CARRYINFO, DMSTOCK");

		Object[] fmtObj = new Object[4];
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3}";

			//#CM34354
			// fetch conditions
			String _wstring = "";
			int _ep = 0;
			
			for (int _plc=0; _plc<key.length; _plc++)
			{
				if (key[_plc].getCollectConditionForCount() != null)
				{
					_wstring = _wstring + key[_plc].getCollectConditionForCount() + ", ";
				}
			}
			
			if (!StringUtil.isBlank(_wstring))
			{
				_ep = _wstring.lastIndexOf(",") ;
				cntObj[0] = _wstring.substring(0, _ep);
			}
			else
			{
				//#CM34355
				// If the fetch conditions are not specified, fetch all the items
				cntObj[0] = " * ";
			}
			_wstring = "";

			for (int _plc=0; _plc<key.length; _plc++)
			{
				if (key[_plc].getCollectCondition() != null)
				{
					_wstring = _wstring + key[_plc].getCollectCondition() + ", ";
				}
			}
			
			if (!StringUtil.isBlank(_wstring))
			{
				_ep = _wstring.lastIndexOf(",") ;
				fmtObj[0] = _wstring.substring(0, _ep);
			}
			else
			{
				//#CM34356
				// If the fetch conditions are not specified, fetch all the items
				fmtObj[0] = " * ";
			}
			
			//#CM34357
			// add search conditions
			cntObj[1] = " WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY ";
			cntObj[1] = cntObj[1] + " AND DNWORKINFO.STOCK_ID = DMSTOCK.STOCK_ID ";
			fmtObj[1] = " WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY ";
			fmtObj[1] = fmtObj[1] + " AND DNWORKINFO.STOCK_ID = DMSTOCK.STOCK_ID ";
			_wstring = "";
			
			for (int _plc=0; _plc<key.length; _plc++)
			{
				if (key[_plc].getReferenceCondition() != null)
				{
					_wstring = " AND " + _wstring + key[_plc].getReferenceCondition();
				}
			}
			
			if (!StringUtil.isBlank(_wstring))
			{
				cntObj[1] = cntObj[1] + _wstring;
				fmtObj[1] = fmtObj[1] + _wstring;
			}

			//#CM34358
			// add collect condition			
			_wstring = "";
			
			for (int _plc=0; _plc<key.length; _plc++)
			{
				if (key[_plc].getGroupCondition() != null)
				{
					_wstring = _wstring + key[_plc].getGroupCondition() + ", ";
				}
			}
			
			if (!StringUtil.isBlank(_wstring))
			{
				_ep = _wstring.lastIndexOf(",") ;
				cntObj[2] = " GROUP BY " + _wstring.substring(0, _ep);
				fmtObj[2] = " GROUP BY " + _wstring.substring(0, _ep);
			}

			//#CM34359
			// reading order
			String[] _keySortTable = null;
			Vector vec = new Vector();
			for (int _plc=0; _plc<key.length; _plc++)
			{
				_keySortTable = key[_plc].getSortConditionTable();
				if (_keySortTable != null)
				{
					for (int _tsetlc = 0; _tsetlc<_keySortTable.length; _tsetlc++)
					{
						vec.addElement(_keySortTable[_tsetlc]);
					}
				}
			}
			String[] _wOrderString = new String[vec.size()];
			vec.copyInto(_wOrderString);
			int[] _keyTableOrder = new int[vec.size()];
			String[] _keyOrderColumn = new String[vec.size()];
			int _plc = 0;

			for (int _slc=0; _slc<_wOrderString.length; _slc++, _plc++)
			{
				int _idx = _wOrderString[_slc].indexOf(",");
				_keyTableOrder[_plc] = Integer.parseInt(_wOrderString[_slc].substring(0, _idx));
				_keyOrderColumn[_plc] = _wOrderString[_slc].substring(_idx+1);
			}
			
			if (_plc > 0)
			{
				for (int _stpt=0; _stpt<_plc; _stpt++)
				{
					for (int _ckpt=_stpt; _ckpt<_plc; _ckpt++)
					{
						if (_keyTableOrder[_stpt] > _keyTableOrder[_ckpt])
						{
							int _svOrder = _keyTableOrder[_stpt];
							String _svSortCond = _keyOrderColumn[_stpt];
							_keyTableOrder[_stpt] = _keyTableOrder[_ckpt];
							_keyOrderColumn[_stpt] = _keyOrderColumn[_ckpt];
							_keyTableOrder[_ckpt] = _svOrder;
							_keyOrderColumn[_ckpt] = _svSortCond;
						}
					}
				}
				String _setSortCondition = "";
				for (int _stpt=0; _stpt<_plc; _stpt++)
				{
					_setSortCondition = _setSortCondition + _keyOrderColumn[_stpt] + ", ";
				}
				_ep = _setSortCondition.lastIndexOf(",") ;

				fmtObj[3] = " ORDER BY " + _setSortCondition.substring(0, _ep);
				cntObj[3] = " ORDER BY " + _setSortCondition.substring(0, _ep);
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			//#CM34360
			//execute search if the count is equal to 1 or more
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM34361
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}

	//#CM34362
	/**
	 * Search the database using the parameter info and return the result count
	 * @param key  key to search work info
	 * @param ckey carry info search key
	 * @return search result count
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public int count(SearchKey key, SearchKey ckey) throws ReadWriteException
	{
		close();
		open();
		setTableName("DNWORKINFO, CARRYINFO");

		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			//#CM34363
			// fetch conditions
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();

				if (ckey.getCollectConditionForCount() != null)
				{
					cntObj[0] = cntObj[0] + ckey.getCollectConditionForCount();
				}
			}
			else
			{
				if (ckey.getCollectConditionForCount() != null)
				{
					cntObj[0] = ckey.getCollectConditionForCount();
				}
				else
				{
					//#CM34364
					// If the fetch conditions are not specified, fetch all the items
					cntObj[0] = " * ";
				}
			}
			
			//#CM34365
			// add search conditions
			cntObj[1] = " WHERE DNWORKINFO.SYSTEM_CONN_KEY = CARRYINFO.CARRYKEY " ;

			if (key.getReferenceCondition() != null)
			{
				cntObj[1] = cntObj[1] + " AND " + key.getReferenceCondition();

				if (ckey.getReferenceCondition() != null)
				{
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}
			else
			{
				if (ckey.getReferenceCondition() != null)
				{
					cntObj[1] = cntObj[1] + " AND " + ckey.getReferenceCondition();
				}
			}

			//#CM34366
			// add collect condition			
			if (ckey.getGroupCondition() != null)
			{
				cntObj[2] = " GROUP BY " + ckey.getGroupCondition();

				if (key.getGroupCondition() != null)
				{
					cntObj[2] = cntObj[2] + ", " + key.getGroupCondition();
				}
			}
			else
			{
				if (key.getGroupCondition() != null)
				{
					cntObj[2] = " GROUP BY " + key.getGroupCondition();
				}
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
		}
		catch (SQLException e)
		{
			//#CM34367
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}

	//#CM34368
	/**
	 * map the result set
	 *
	 * @param search result
	 * @return Entity array
	 * @throws ReadWriteException Throw any database exception as it is
	 */
	public Entity[] getEntities(int count) throws ReadWriteException
	{
		Vector entityList = new Vector() ;
		AbstractEntity[] entityArr = null ;
		AbstractEntity tmpEntity = createEntity() ;
		try
		{
			//#CM34369
			// count the number read this time
			int readCount = 0;
			//#CM34370
			// Flag to decide whether to close the ResultSet or not. Close after the last record is read
			boolean endFlag = true;
			while ( p_ResultSet.next() )
			{			
				for (int i = 1; i <= p_ResultSet.getMetaData().getColumnCount(); i++)
				{
					String colname = p_ResultSet.getMetaData().getColumnName(i) ;
					FieldName field = new FieldName(colname) ;
	
					//#CM34371
					// Fetch milliseconds
					Object value = p_ResultSet.getObject(i) ;
					if (value instanceof java.util.Date)
					{
						value = p_ResultSet.getTimestamp(i) ;
					}

					tmpEntity.setValue(field, value) ;
				}
				entityList.addElement(tmpEntity) ;
	
				tmpEntity = createEntity() ;
				
				//#CM34372
				// Pass a while after reading the qty
				readCount++;
				if ( count <= readCount )
				{
					endFlag = false;
					break;
				}
			}
			//#CM34373
			// Close after reading the last record
			if ( endFlag )
			{
				isNextFlag = false ;
				close();
			}
	
			//#CM34374
			// Cast with respect to every table Entity[]
			entityArr = (AbstractEntity[])java.lang.reflect.Array.newInstance(
					tmpEntity.getClass(), entityList.size()) ;
			entityList.copyInto(entityArr) ;

		}
		catch (SQLException e)
		{
			//#CM34375
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM34376
			//Here, throw ReadWriteException with error message attached. 
			//#CM34377
			//6006039 = Failed to search for {0}.
			throw (new ReadWriteException("6006039" + wDelim + "DNWORKINFO, CARRYINFO")) ;
		}
		
		return entityArr ;
	}
}

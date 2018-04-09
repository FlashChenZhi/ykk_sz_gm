//$Id: StationTypeSearchKey.java,v 1.3 2006/11/13 04:32:58 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.StationType;

/**
 * This is the search key class for STATIONTYPE use.
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- update history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:58 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class StationTypeSearchKey
		extends AbstractSQLSearchKey
{
	//------------------------------------------------------------
	// class variables (Prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (Prefix 'p_')
	//------------------------------------------------------------
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (Prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

	private static String _Prefix = "";

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * Prepare table name and column list and generate instance
	 *
	 */
	public StationTypeSearchKey()
	{
		super(new StationType()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationnumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + StationType.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationnumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + StationType.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationnumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + StationType.STATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationnumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + StationType.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationnumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + StationType.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationnumberGroup(int num)
	{
		setGroup(_Prefix + StationType.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationnumberCollect()
	{
		setCollect(_Prefix + StationType.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationnumberCollect(String compcode)
	{
		setCollect(_Prefix + StationType.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  handler class
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHandlerclass(String arg) throws ReadWriteException
	{
		setValue(_Prefix + StationType.HANDLERCLASS.toString(), arg);
	}
	/**
	 *Set search value for  handler class
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHandlerclass(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + StationType.HANDLERCLASS.toString(), arg);
	}
	/**
	 *Set search value for  handler class
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHandlerclass(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + StationType.HANDLERCLASS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  handler class
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHandlerclass(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + StationType.HANDLERCLASS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for handler class
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHandlerclassOrder(int num, boolean bool)
	{
		setOrder(_Prefix + StationType.HANDLERCLASS.toString(), num, bool);
	}

	/**
	 * Set grouping order handler class
	 * @param num grouping order
	 */
	public void setHandlerclassGroup(int num)
	{
		setGroup(_Prefix + StationType.HANDLERCLASS.toString(), num);
	}

	/**
	 * Fetch handler class info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHandlerclassCollect()
	{
		setCollect(_Prefix + StationType.HANDLERCLASS.toString(), "");
	}

	/**
	 * Fetch handler class info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHandlerclassCollect(String compcode)
	{
		setCollect(_Prefix + StationType.HANDLERCLASS.toString(), compcode);
	}

	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: StationTypeSearchKey.java,v 1.3 2006/11/13 04:32:58 suresh Exp $" ;
	}
}

//$Id: ShelfSearchKey.java,v 1.3 2006/11/13 04:33:01 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.Shelf;

/**
 * This is the search key class for SHELF use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:01 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ShelfSearchKey
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
	public ShelfSearchKey()
	{
		super(new Shelf()) ;
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
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg, compcode);
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
	public void setStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num) 
	{
		setGroup(_Prefix + Shelf.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + Shelf.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  bank
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg);
	}
	/**
	 *Set search value for  bank
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg);
	}
	/**
	 *Set search value for  bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for bank
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNBankOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.NBANK.toString(), num, bool);
	}

	/**
	 * Set grouping order bank
	 * @param num grouping order
	 */
	public void setNBankGroup(int num)
	{
		setGroup(_Prefix + Shelf.NBANK.toString(), num);
	}

	/**
	 * Fetch bank info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNBankCollect()
	{
		setCollect(_Prefix + Shelf.NBANK.toString(), "");
	}

	/**
	 * Fetch bank info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNBankCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.NBANK.toString(), compcode);
	}

	/**
	 *Set search value for  bay
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg);
	}
	/**
	 *Set search value for  bay
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg);
	}
	/**
	 *Set search value for  bay
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  bay
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for bay
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNBayOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.NBAY.toString(), num, bool);
	}

	/**
	 * Set grouping order bay
	 * @param num grouping order
	 */
	public void setNBayGroup(int num)
	{
		setGroup(_Prefix + Shelf.NBAY.toString(), num);
	}

	/**
	 * Fetch bay info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNBayCollect()
	{
		setCollect(_Prefix + Shelf.NBAY.toString(), "");
	}

	/**
	 * Fetch bay info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNBayCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.NBAY.toString(), compcode);
	}

	/**
	 *Set search value for  level
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  level
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for level
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNLevelOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.NLEVEL.toString(), num, bool);
	}

	/**
	 * Set grouping order level
	 * @param num grouping order
	 */
	public void setNLevelGroup(int num)
	{
		setGroup(_Prefix + Shelf.NLEVEL.toString(), num);
	}

	/**
	 * Fetch level info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNLevelCollect()
	{
		setCollect(_Prefix + Shelf.NLEVEL.toString(), "");
	}

	/**
	 * Fetch level info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNLevelCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.NLEVEL.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + Shelf.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + Shelf.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order status
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + Shelf.STATUS.toString(), num);
	}

	/**
	 * Fetch status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + Shelf.STATUS.toString(), "");
	}

	/**
	 * Fetch status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.STATUS.toString(), compcode);
	}

	/**
	 *Set search value for  location status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg);
	}
	/**
	 *Set search value for  location status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg);
	}
	/**
	 *Set search value for  location status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for location status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPresenceOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.PRESENCE.toString(), num, bool);
	}

	/**
	 * Set grouping order location status
	 * @param num grouping order
	 */
	public void setPresenceGroup(int num)
	{
		setGroup(_Prefix + Shelf.PRESENCE.toString(), num);
	}

	/**
	 * Fetch location status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPresenceCollect()
	{
		setCollect(_Prefix + Shelf.PRESENCE.toString(), "");
	}

	/**
	 * Fetch location status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPresenceCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.PRESENCE.toString(), compcode);
	}

	/**
	 *Set search value for  hard zone
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg);
	}
	/**
	 *Set search value for  hard zone
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg);
	}
	/**
	 *Set search value for  hard zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  hard zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for hard zone
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHardZoneIDOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.HARDZONEID.toString(), num, bool);
	}

	/**
	 * Set grouping order hard zone
	 * @param num grouping order
	 */
	public void setHardZoneIDGroup(int num)
	{
		setGroup(_Prefix + Shelf.HARDZONEID.toString(), num);
	}

	/**
	 * Fetch hard zone info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHardZoneIDCollect()
	{
		setCollect(_Prefix + Shelf.HARDZONEID.toString(), "");
	}

	/**
	 * Fetch hard zone info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHardZoneIDCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.HARDZONEID.toString(), compcode);
	}

	/**
	 *Set search value for  soft zone
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg);
	}
	/**
	 *Set search value for  soft zone
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg);
	}
	/**
	 *Set search value for  soft zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  soft zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for soft zone
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSoftZoneIDOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.SOFTZONEID.toString(), num, bool);
	}

	/**
	 * Set grouping order soft zone
	 * @param num grouping order
	 */
	public void setSoftZoneIDGroup(int num)
	{
		setGroup(_Prefix + Shelf.SOFTZONEID.toString(), num);
	}

	/**
	 * Fetch soft zone info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSoftZoneIDCollect()
	{
		setCollect(_Prefix + Shelf.SOFTZONEID.toString(), "");
	}

	/**
	 * Fetch soft zone info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSoftZoneIDCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.SOFTZONEID.toString(), compcode);
	}

	/**
	 *Set search value for  parent station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  parent station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  parent station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  parent station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for parent station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setParentStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order parent station number
	 * @param num grouping order
	 */
	public void setParentStationNumberGroup(int num)
	{
		setGroup(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch parent station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setParentStationNumberCollect()
	{
		setCollect(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch parent station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setParentStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  location restriction flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  location restriction flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  location restriction flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location restriction flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for location restriction flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAccessNgFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.ACCESSNGFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order location restriction flag
	 * @param num grouping order
	 */
	public void setAccessNgFlagGroup(int num)
	{
		setGroup(_Prefix + Shelf.ACCESSNGFLAG.toString(), num);
	}

	/**
	 * Fetch location restriction flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAccessNgFlagCollect()
	{
		setCollect(_Prefix + Shelf.ACCESSNGFLAG.toString(), "");
	}

	/**
	 * Fetch location restriction flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAccessNgFlagCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.ACCESSNGFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  location search order
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  location search order
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  location search order
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location search order
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for location search order
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPriorityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.PRIORITY.toString(), num, bool);
	}

	/**
	 * Set grouping order location search order
	 * @param num grouping order
	 */
	public void setPriorityGroup(int num)
	{
		setGroup(_Prefix + Shelf.PRIORITY.toString(), num);
	}

	/**
	 * Fetch location search order info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPriorityCollect()
	{
		setCollect(_Prefix + Shelf.PRIORITY.toString(), "");
	}

	/**
	 * Fetch location search order info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPriorityCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.PRIORITY.toString(), compcode);
	}

	/**
	 *Set search value for  pair station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  pair station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  pair station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  pair station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for pair station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPairStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order pair station number
	 * @param num grouping order
	 */
	public void setPairStationNumberGroup(int num) 
	{
		setGroup(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch pair station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPairStationNumberCollect()
	{
		setCollect(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch pair station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPairStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  from side, deep number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg);
	}
	/**
	 *Set search value for  from side, deep number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg);
	}
	/**
	 *Set search value for  from side, deep number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  from side, deep number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for from side, deep number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSideOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Shelf.SIDE.toString(), num, bool);
	}

	/**
	 * Set grouping order from side, deep number
	 * @param num grouping order
	 */
	public void setSideGroup(int num)
	{
		setGroup(_Prefix + Shelf.SIDE.toString(), num);
	}

	/**
	 * Fetch from side, deep number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSideCollect()
	{
		setCollect(_Prefix + Shelf.SIDE.toString(), "");
	}

	/**
	 * Fetch from side, deep number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSideCollect(String compcode)
	{
		setCollect(_Prefix + Shelf.SIDE.toString(), compcode);
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
		return "$Id: ShelfSearchKey.java,v 1.3 2006/11/13 04:33:01 suresh Exp $" ;
	}
}

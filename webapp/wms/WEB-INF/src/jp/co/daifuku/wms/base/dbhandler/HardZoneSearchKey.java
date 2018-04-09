//$Id: HardZoneSearchKey.java,v 1.3 2006/11/13 04:33:09 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.HardZone;

/**
 * This is the search key class for HARDZONE use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:09 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class HardZoneSearchKey
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
	public HardZoneSearchKey()
	{
		super(new HardZone()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  hard zone id
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg);
	}
	/**
	 *Set search value for  hard zone id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg);
	}
	/**
	 *Set search value for  hard zone id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  hard zone id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for hard zone id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHardZoneIDOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.HARDZONEID.toString(), num, bool);
	}

	/**
	 * Set grouping order hard zone id
	 * @param num grouping order
	 */
	public void setHardZoneIDGroup(int num)
	{
		setGroup(_Prefix + HardZone.HARDZONEID.toString(), num);
	}

	/**
	 * Fetch hard zone id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHardZoneIDCollect()
	{
		setCollect(_Prefix + HardZone.HARDZONEID.toString(), "");
	}

	/**
	 * Fetch hard zone id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHardZoneIDCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.HARDZONEID.toString(), compcode);
	}

	/**
	 *Set search value for  zone name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg);
	}
	/**
	 *Set search value for  zone name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg);
	}
	/**
	 *Set search value for  zone name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  zone name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for zone name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.NAME.toString(), num, bool);
	}

	/**
	 * Set grouping order zone name
	 * @param num grouping order
	 */
	public void setNameGroup(int num)
	{
		setGroup(_Prefix + HardZone.NAME.toString(), num);
	}

	/**
	 * Fetch zone name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNameCollect()
	{
		setCollect(_Prefix + HardZone.NAME.toString(), "");
	}

	/**
	 * Fetch zone name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNameCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.NAME.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + HardZone.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + HardZone.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  height of load
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  height of load
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  height of load
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  height of load
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for height of load
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHeightOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.HEIGHT.toString(), num, bool);
	}

	/**
	 * Set grouping order height of load
	 * @param num grouping order
	 */
	public void setHeightGroup(int num)
	{
		setGroup(_Prefix + HardZone.HEIGHT.toString(), num);
	}

	/**
	 * Fetch height of load info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHeightCollect()
	{
		setCollect(_Prefix + HardZone.HEIGHT.toString(), "");
	}

	/**
	 * Fetch height of load info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHeightCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.HEIGHT.toString(), compcode);
	}

	/**
	 *Set search value for  zone priority order
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  zone priority order
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  zone priority order
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  zone priority order
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for zone priority order
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPriorityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.PRIORITY.toString(), num, bool);
	}

	/**
	 * Set grouping order zone priority order
	 * @param num grouping order
	 */
	public void setPriorityGroup(int num)
	{
		setGroup(_Prefix + HardZone.PRIORITY.toString(), num);
	}

	/**
	 * Fetch zone priority order info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPriorityCollect()
	{
		setCollect(_Prefix + HardZone.PRIORITY.toString(), "");
	}

	/**
	 * Fetch zone priority order info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPriorityCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.PRIORITY.toString(), compcode);
	}

	/**
	 *Set search value for  starting bank number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg);
	}
	/**
	 *Set search value for  starting bank number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg);
	}
	/**
	 *Set search value for  starting bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for starting bank number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStartBankOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.STARTBANK.toString(), num, bool);
	}

	/**
	 * Set grouping order starting bank number
	 * @param num grouping order
	 */
	public void setStartBankGroup(int num)
	{
		setGroup(_Prefix + HardZone.STARTBANK.toString(), num);
	}

	/**
	 * Fetch starting bank number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStartBankCollect()
	{
		setCollect(_Prefix + HardZone.STARTBANK.toString(), "");
	}

	/**
	 * Fetch starting bank number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStartBankCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.STARTBANK.toString(), compcode);
	}

	/**
	 *Set search value for  starting bay number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg);
	}
	/**
	 *Set search value for  starting bay number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg);
	}
	/**
	 *Set search value for  starting bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for starting bay number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStartBayOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.STARTBAY.toString(), num, bool);
	}

	/**
	 * Set grouping order starting bay number
	 * @param num grouping order
	 */
	public void setStartBayGroup(int num)
	{
		setGroup(_Prefix + HardZone.STARTBAY.toString(), num);
	}

	/**
	 * Fetch starting bay number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStartBayCollect()
	{
		setCollect(_Prefix + HardZone.STARTBAY.toString(), "");
	}

	/**
	 * Fetch starting bay number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStartBayCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.STARTBAY.toString(), compcode);
	}

	/**
	 *Set search value for  starting level
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  starting level
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  starting level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for starting level
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStartLevelOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.STARTLEVEL.toString(), num, bool);
	}

	/**
	 * Set grouping order starting level
	 * @param num grouping order
	 */
	public void setStartLevelGroup(int num)
	{
		setGroup(_Prefix + HardZone.STARTLEVEL.toString(), num);
	}

	/**
	 * Fetch starting level info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStartLevelCollect()
	{
		setCollect(_Prefix + HardZone.STARTLEVEL.toString(), "");
	}

	/**
	 * Fetch starting level info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStartLevelCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.STARTLEVEL.toString(), compcode);
	}

	/**
	 *Set search value for  last bank number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg);
	}
	/**
	 *Set search value for  last bank number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg);
	}
	/**
	 *Set search value for  last bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last bank number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEndBankOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.ENDBANK.toString(), num, bool);
	}

	/**
	 * Set grouping order last bank number
	 * @param num grouping order
	 */
	public void setEndBankGroup(int num)
	{
		setGroup(_Prefix + HardZone.ENDBANK.toString(), num);
	}

	/**
	 * Fetch last bank number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEndBankCollect()
	{
		setCollect(_Prefix + HardZone.ENDBANK.toString(), "");
	}

	/**
	 * Fetch last bank number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEndBankCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.ENDBANK.toString(), compcode);
	}

	/**
	 *Set search value for  last bay number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg);
	}
	/**
	 *Set search value for  last bay number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg);
	}
	/**
	 *Set search value for  last bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last bay number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEndBayOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.ENDBAY.toString(), num, bool);
	}

	/**
	 * Set grouping order last bay number
	 * @param num grouping order
	 */
	public void setEndBayGroup(int num)
	{
		setGroup(_Prefix + HardZone.ENDBAY.toString(), num);
	}

	/**
	 * Fetch last bay number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEndBayCollect()
	{
		setCollect(_Prefix + HardZone.ENDBAY.toString(), "");
	}

	/**
	 * Fetch last bay number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEndBayCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.ENDBAY.toString(), compcode);
	}

	/**
	 *Set search value for  last level number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  last level number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  last level number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last level number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last level number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEndLevelOrder(int num, boolean bool)
	{
		setOrder(_Prefix + HardZone.ENDLEVEL.toString(), num, bool);
	}

	/**
	 * Set grouping order last level number
	 * @param num grouping order
	 */
	public void setEndLevelGroup(int num)
	{
		setGroup(_Prefix + HardZone.ENDLEVEL.toString(), num);
	}

	/**
	 * Fetch last level number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEndLevelCollect()
	{
		setCollect(_Prefix + HardZone.ENDLEVEL.toString(), "");
	}

	/**
	 * Fetch last level number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEndLevelCollect(String compcode)
	{
		setCollect(_Prefix + HardZone.ENDLEVEL.toString(), compcode);
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
		return "$Id: HardZoneSearchKey.java,v 1.3 2006/11/13 04:33:09 suresh Exp $" ;
	}
}

//$Id: PaletteSearchKey.java,v 1.3 2006/11/13 04:33:04 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.Palette;

/**
 * This is the search key class for PALETTE use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:04 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class PaletteSearchKey
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
	public PaletteSearchKey()
	{
		super(new Palette()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPaletteIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.PALETTEID.toString(), num, bool);
	}

	/**
	 * Set grouping order palette id
	 * @param num grouping order
	 */
	public void setPaletteIdGroup(int num)
	{
		setGroup(_Prefix + Palette.PALETTEID.toString(), num);
	}

	/**
	 * Fetch palette id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPaletteIdCollect()
	{
		setCollect(_Prefix + Palette.PALETTEID.toString(), "");
	}

	/**
	 * Fetch palette id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPaletteIdCollect(String compcode)
	{
		setCollect(_Prefix + Palette.PALETTEID.toString(), compcode);
	}

	/**
	 *Set search value for  current station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  current station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  current station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  current station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for current station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCurrentStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order current station number
	 * @param num grouping order
	 */
	public void setCurrentStationNumberGroup(int num)
	{
		setGroup(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch current station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCurrentStationNumberCollect()
	{
		setCollect(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch current station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCurrentStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + Palette.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + Palette.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Palette.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette type id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPaletteTypeIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.PALETTETYPEID.toString(), num, bool);
	}

	/**
	 * Set grouping order palette type id
	 * @param num grouping order
	 */
	public void setPaletteTypeIdGroup(int num)
	{
		setGroup(_Prefix + Palette.PALETTETYPEID.toString(), num);
	}

	/**
	 * Fetch palette type id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPaletteTypeIdCollect()
	{
		setCollect(_Prefix + Palette.PALETTETYPEID.toString(), "");
	}

	/**
	 * Fetch palette type id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPaletteTypeIdCollect(String compcode)
	{
		setCollect(_Prefix + Palette.PALETTETYPEID.toString(), compcode);
	}

	/**
	 *Set search value for  stock status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  stock status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  stock status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  stock status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for stock status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order stock status
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + Palette.STATUS.toString(), num);
	}

	/**
	 * Fetch stock status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + Palette.STATUS.toString(), "");
	}

	/**
	 * Fetch stock status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + Palette.STATUS.toString(), compcode);
	}

	/**
	 *Set search value for  allocation status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  allocation status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  allocation status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  allocation status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for allocation status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAllocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.ALLOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order allocation status
	 * @param num grouping order
	 */
	public void setAllocationGroup(int num)
	{
		setGroup(_Prefix + Palette.ALLOCATION.toString(), num);
	}

	/**
	 * Fetch allocation status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAllocationCollect()
	{
		setCollect(_Prefix + Palette.ALLOCATION.toString(), "");
	}

	/**
	 * Fetch allocation status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAllocationCollect(String compcode)
	{
		setCollect(_Prefix + Palette.ALLOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  empty palette status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg);
	}
	/**
	 *Set search value for  empty palette status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg);
	}
	/**
	 *Set search value for  empty palette status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  empty palette status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for empty palette status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEmptyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.EMPTY.toString(), num, bool);
	}

	/**
	 * Set grouping order empty palette status
	 * @param num grouping order
	 */
	public void setEmptyGroup(int num)
	{
		setGroup(_Prefix + Palette.EMPTY.toString(), num);
	}

	/**
	 * Fetch empty palette status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEmptyCollect()
	{
		setCollect(_Prefix + Palette.EMPTY.toString(), "");
	}

	/**
	 * Fetch empty palette status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEmptyCollect(String compcode)
	{
		setCollect(_Prefix + Palette.EMPTY.toString(), compcode);
	}

	/**
	 *Set search value for  updatation date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg);
	}
	/**
	 *Set search value for  updatation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg);
	}
	/**
	 *Set search value for  updatation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  updatation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for updatation date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRefixDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.REFIXDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order updatation date
	 * @param num grouping order
	 */
	public void setRefixDateGroup(int num)
	{
		setGroup(_Prefix + Palette.REFIXDATE.toString(), num);
	}

	/**
	 * Fetch updatation date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRefixDateCollect()
	{
		setCollect(_Prefix + Palette.REFIXDATE.toString(), "");
	}

	/**
	 * Fetch updatation date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRefixDateCollect(String compcode)
	{
		setCollect(_Prefix + Palette.REFIXDATE.toString(), compcode);
	}

	/**
	 *Set search value for  filling rate
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg);
	}
	/**
	 *Set search value for  filling rate
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg);
	}
	/**
	 *Set search value for  filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for filling rate
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.RATE.toString(), num, bool);
	}

	/**
	 * Set grouping order filling rate
	 * @param num grouping order
	 */
	public void setRateGroup(int num)
	{
		setGroup(_Prefix + Palette.RATE.toString(), num);
	}

	/**
	 * Fetch filling rate info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRateCollect()
	{
		setCollect(_Prefix + Palette.RATE.toString(), "");
	}

	/**
	 * Fetch filling rate info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRateCollect(String compcode)
	{
		setCollect(_Prefix + Palette.RATE.toString(), compcode);
	}

	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  load height
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for load height
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHeightOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.HEIGHT.toString(), num, bool);
	}

	/**
	 * Set grouping order load height
	 * @param num grouping order
	 */
	public void setHeightGroup(int num)
	{
		setGroup(_Prefix + Palette.HEIGHT.toString(), num);
	}

	/**
	 * Fetch load height info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHeightCollect()
	{
		setCollect(_Prefix + Palette.HEIGHT.toString(), "");
	}

	/**
	 * Fetch load height info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHeightCollect(String compcode)
	{
		setCollect(_Prefix + Palette.HEIGHT.toString(), compcode);
	}

	/**
	 *Set search value for  palette info
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  palette info
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  palette info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette info
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBcDataOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Palette.BCDATA.toString(), num, bool);
	}

	/**
	 * Set grouping order palette info
	 * @param num grouping order
	 */
	public void setBcDataGroup(int num)
	{
		setGroup(_Prefix + Palette.BCDATA.toString(), num);
	}

	/**
	 * Fetch palette info info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBcDataCollect()
	{
		setCollect(_Prefix + Palette.BCDATA.toString(), "");
	}

	/**
	 * Fetch palette info info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBcDataCollect(String compcode)
	{
		setCollect(_Prefix + Palette.BCDATA.toString(), compcode);
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
		return "$Id: PaletteSearchKey.java,v 1.3 2006/11/13 04:33:04 suresh Exp $" ;
	}
}

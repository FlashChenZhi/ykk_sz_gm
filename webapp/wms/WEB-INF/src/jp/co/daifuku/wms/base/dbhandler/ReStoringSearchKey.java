//$Id: ReStoringSearchKey.java,v 1.5 2006/11/20 04:28:03 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.ReStoring;

/**
 * This is the search key class for RESTORING use.
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
 * @version $Revision: 1.5 $, $Date: 2006/11/20 04:28:03 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ReStoringSearchKey
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
	public ReStoringSearchKey()
	{
		super(new ReStoring()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for work number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.WORKNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order work number
	 * @param num grouping order
	 */
	public void setWorkNumberGroup(int num)
	{
		setGroup(_Prefix + ReStoring.WORKNUMBER.toString(), num);
	}

	/**
	 * Fetch work number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkNumberCollect()
	{
		setCollect(_Prefix + ReStoring.WORKNUMBER.toString(), "");
	}

	/**
	 * Fetch work number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkNumberCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.WORKNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for creation date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCreateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.CREATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order creation date
	 * @param num grouping order
	 */
	public void setCreateDateGroup(int num)
	{
		setGroup(_Prefix + ReStoring.CREATEDATE.toString(), num);
	}

	/**
	 * Fetch creation date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCreateDateCollect()
	{
		setCollect(_Prefix + ReStoring.CREATEDATE.toString(), "");
	}

	/**
	 * Fetch creation date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCreateDateCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.CREATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + ReStoring.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + ReStoring.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg, compcode);
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
	public void setPaletteTypeID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette type id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPaletteTypeIDOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.PALETTETYPEID.toString(), num, bool);
	}

	/**
	 * Set grouping order palette type id
	 * @param num grouping order
	 */
	public void setPaletteTypeIDGroup(int num)
	{
		setGroup(_Prefix + ReStoring.PALETTETYPEID.toString(), num);
	}

	/**
	 * Fetch palette type id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPaletteTypeIDCollect()
	{
		setCollect(_Prefix + ReStoring.PALETTETYPEID.toString(), "");
	}

	/**
	 * Fetch palette type id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPaletteTypeIDCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.PALETTETYPEID.toString(), compcode);
	}

	/**
	 *Set search value for  item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.ITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order item code
	 * @param num grouping order
	 */
	public void setItemCodeGroup(int num)
	{
		setGroup(_Prefix + ReStoring.ITEMCODE.toString(), num);
	}

	/**
	 * Fetch item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemCodeCollect()
	{
		setCollect(_Prefix + ReStoring.ITEMCODE.toString(), "");
	}

	/**
	 * Fetch item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.ITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for lot number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLotNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.LOTNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order lot number
	 * @param num grouping order
	 */
	public void setLotNumberGroup(int num)
	{
		setGroup(_Prefix + ReStoring.LOTNUMBER.toString(), num);
	}

	/**
	 * Fetch lot number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLotNumberCollect()
	{
		setCollect(_Prefix + ReStoring.LOTNUMBER.toString(), "");
	}

	/**
	 * Fetch lot number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLotNumberCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.LOTNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  stock qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  stock qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  stock qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  stock qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for stock qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setQuantityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.QUANTITY.toString(), num, bool);
	}

	/**
	 * Set grouping order stock qty
	 * @param num grouping order
	 */
	public void setQuantityGroup(int num)
	{
		setGroup(_Prefix + ReStoring.QUANTITY.toString(), num);
	}

	/**
	 * Fetch stock qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setQuantityCollect()
	{
		setCollect(_Prefix + ReStoring.QUANTITY.toString(), "");
	}

	/**
	 * Fetch stock qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setQuantityCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.QUANTITY.toString(), compcode);
	}

	/**
	 *Set search value for  storage date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for storage date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInCommingDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.INCOMMINGDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order storage date
	 * @param num grouping order
	 */
	public void setInCommingDateGroup(int num)
	{
		setGroup(_Prefix + ReStoring.INCOMMINGDATE.toString(), num);
	}

	/**
	 * Fetch storage date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInCommingDateCollect()
	{
		setCollect(_Prefix + ReStoring.INCOMMINGDATE.toString(), "");
	}

	/**
	 * Fetch storage date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInCommingDateCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.INCOMMINGDATE.toString(), compcode);
	}

	/**
	 *Set search value for  inventory check date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg);
	}
	/**
	 *Set search value for  inventory check date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg);
	}
	/**
	 *Set search value for  inventory check date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  inventory check date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for inventory check date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInventoryCheckDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order inventory check date
	 * @param num grouping order
	 */
	public void setInventoryCheckDateGroup(int num)
	{
		setGroup(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), num);
	}

	/**
	 * Fetch inventory check date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInventoryCheckDateCollect()
	{
		setCollect(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), "");
	}

	/**
	 * Fetch inventory check date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInventoryCheckDateCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), compcode);
	}

	/**
	 *Set search value for  last modified date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg);
	}
	/**
	 *Set search value for  last modified date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg);
	}
	/**
	 *Set search value for  last modified date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last modified date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last modified date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastConfirmDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order last modified date
	 * @param num grouping order
	 */
	public void setLastConfirmDateGroup(int num)
	{
		setGroup(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), num);
	}

	/**
	 * Fetch last modified date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastConfirmDateCollect()
	{
		setCollect(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), "");
	}

	/**
	 * Fetch last modified date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastConfirmDateCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), compcode);
	}

	/**
	 *Set search value for  re-storage type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg);
	}
	/**
	 *Set search value for  re-storage type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg);
	}
	/**
	 *Set search value for  re-storage type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg, compcode);
	}
	/**
	 *Set search value for  re-storage type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for re-storage type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReStoringOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.RESTORING.toString(), num, bool);
	}

	/**
	 * Set grouping order re-storage type
	 * @param num grouping order
	 */
	public void setReStoringGroup(int num)
	{
		setGroup(_Prefix + ReStoring.RESTORING.toString(), num);
	}

	/**
	 * Fetch re-storage type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReStoringCollect()
	{
		setCollect(_Prefix + ReStoring.RESTORING.toString(), "");
	}

	/**
	 * Fetch re-storage type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReStoringCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.RESTORING.toString(), compcode);
	}

	/**
	 *Set search value for  barcode info
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  barcode info
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  barcode info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  barcode info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for barcode info
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBcDataOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.BCDATA.toString(), num, bool);
	}

	/**
	 * Set grouping order barcode info
	 * @param num grouping order
	 */
	public void setBcDataGroup(int num)
	{
		setGroup(_Prefix + ReStoring.BCDATA.toString(), num);
	}

	/**
	 * Fetch barcode info info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBcDataCollect()
	{
		setCollect(_Prefix + ReStoring.BCDATA.toString(), "");
	}

	/**
	 * Fetch barcode info info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBcDataCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.BCDATA.toString(), compcode);
	}

	/**
	 *Set search value for  process flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  process flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  process flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  process flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for process flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setProcessingFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ReStoring.PROCESSINGFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order process flag
	 * @param num grouping order
	 */
	public void setProcessingFlagGroup(int num)
	{
		setGroup(_Prefix + ReStoring.PROCESSINGFLAG.toString(), num);
	}

	/**
	 * Fetch process flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setProcessingFlagCollect()
	{
		setCollect(_Prefix + ReStoring.PROCESSINGFLAG.toString(), "");
	}

	/**
	 * Fetch process flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setProcessingFlagCollect(String compcode)
	{
		setCollect(_Prefix + ReStoring.PROCESSINGFLAG.toString(), compcode);
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
		return "$Id: ReStoringSearchKey.java,v 1.5 2006/11/20 04:28:03 suresh Exp $" ;
	}
}

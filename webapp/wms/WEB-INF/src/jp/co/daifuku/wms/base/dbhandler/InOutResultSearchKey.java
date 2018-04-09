//$Id: InOutResultSearchKey.java,v 1.2 2006/11/09 07:52:59 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.InOutResult;

/**
 * This is the search key class for INOUTRESULT use.
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:52:59 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class InOutResultSearchKey
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
	public InOutResultSearchKey()
	{
		super(new InOutResult()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  storage result date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoreDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STOREDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage result date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoreDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STOREDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage result date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoreDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STOREDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  storage result date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoreDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STOREDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for storage result date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStoreDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.STOREDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order storage result date
	 * @param num grouping order
	 */
	public void setStoreDateGroup(int num)
	{
		setGroup(_Prefix + InOutResult.STOREDATE.toString(), num);
	}

	/**
	 * Fetch storage result date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStoreDateCollect()
	{
		setCollect(_Prefix + InOutResult.STOREDATE.toString(), "");
	}

	/**
	 * Fetch storage result date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStoreDateCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.STOREDATE.toString(), compcode);
	}

	/**
	 *Set search value for  result work type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESULTKIND.toString(), arg);
	}
	/**
	 *Set search value for  result work type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultKind(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESULTKIND.toString(), arg);
	}
	/**
	 *Set search value for  result work type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultKind(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESULTKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  result work type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultKind(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESULTKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for result work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.RESULTKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order result work type
	 * @param num grouping order
	 */
	public void setResultKindGroup(int num)
	{
		setGroup(_Prefix + InOutResult.RESULTKIND.toString(), num);
	}

	/**
	 * Fetch result work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultKindCollect()
	{
		setCollect(_Prefix + InOutResult.RESULTKIND.toString(), "");
	}

	/**
	 * Fetch result work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultKindCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.RESULTKIND.toString(), compcode);
	}

	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + InOutResult.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + InOutResult.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for consignor name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.CONSIGNORNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order consignor name
	 * @param num grouping order
	 */
	public void setConsignorNameGroup(int num)
	{
		setGroup(_Prefix + InOutResult.CONSIGNORNAME.toString(), num);
	}

	/**
	 * Fetch consignor name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorNameCollect()
	{
		setCollect(_Prefix + InOutResult.CONSIGNORNAME.toString(), "");
	}

	/**
	 * Fetch consignor name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorNameCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.CONSIGNORNAME.toString(), compcode);
	}

	/**
	 *Set search value for  item key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  item key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  item key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  item key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for item key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.ITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order item key
	 * @param num grouping order
	 */
	public void setItemCodeGroup(int num)
	{
		setGroup(_Prefix + InOutResult.ITEMCODE.toString(), num);
	}

	/**
	 * Fetch item key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemCodeCollect()
	{
		setCollect(_Prefix + InOutResult.ITEMCODE.toString(), "");
	}

	/**
	 * Fetch item key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.ITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOTNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + InOutResult.LOTNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for lot number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLotNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.LOTNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order lot number
	 * @param num grouping order
	 */
	public void setLotNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.LOTNUMBER.toString(), num);
	}

	/**
	 * Fetch lot number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLotNumberCollect()
	{
		setCollect(_Prefix + InOutResult.LOTNUMBER.toString(), "");
	}

	/**
	 * Fetch lot number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLotNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.LOTNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKTYPE.toString(), arg);
	}
	/**
	 *Set search value for  work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKTYPE.toString(), arg);
	}
	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.WORKTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order work type
	 * @param num grouping order
	 */
	public void setWorkTypeGroup(int num)
	{
		setGroup(_Prefix + InOutResult.WORKTYPE.toString(), num);
	}

	/**
	 * Fetch work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkTypeCollect()
	{
		setCollect(_Prefix + InOutResult.WORKTYPE.toString(), "");
	}

	/**
	 * Fetch work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkTypeCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.WORKTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + InOutResult.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + InOutResult.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for aisle station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAisleStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order aisle station number
	 * @param num grouping order
	 */
	public void setAisleStationNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch aisle station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAisleStationNumberCollect()
	{
		setCollect(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch aisle station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAisleStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  quantity per case
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  quantity per case
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  quantity per case
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  quantity per case
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for quantity per case
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.ENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order quantity per case
	 * @param num grouping order
	 */
	public void setEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + InOutResult.ENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch quantity per case info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEnteringQtyCollect()
	{
		setCollect(_Prefix + InOutResult.ENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch quantity per case info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.ENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  quantity per bundle
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  quantity per bundle
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  quantity per bundle
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  quantity per bundle
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for quantity per bundle
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order quantity per bundle
	 * @param num grouping order
	 */
	public void setBundleEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch quantity per bundle info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleEnteringQtyCollect()
	{
		setCollect(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch quantity per bundle info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  storage/picking qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInOutQuantity(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INOUTQUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  storage/picking qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInOutQuantity(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INOUTQUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  storage/picking qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInOutQuantity(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INOUTQUANTITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  storage/picking qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInOutQuantity(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INOUTQUANTITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for storage/picking qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInOutQuantityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.INOUTQUANTITY.toString(), num, bool);
	}

	/**
	 * Set grouping order storage/picking qty
	 * @param num grouping order
	 */
	public void setInOutQuantityGroup(int num)
	{
		setGroup(_Prefix + InOutResult.INOUTQUANTITY.toString(), num);
	}

	/**
	 * Fetch storage/picking qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInOutQuantityCollect()
	{
		setCollect(_Prefix + InOutResult.INOUTQUANTITY.toString(), "");
	}

	/**
	 * Fetch storage/picking qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInOutQuantityCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.INOUTQUANTITY.toString(), compcode);
	}

	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + InOutResult.WORKNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for work number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.WORKNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order work number
	 * @param num grouping order
	 */
	public void setWorkNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.WORKNUMBER.toString(), num);
	}

	/**
	 * Fetch work number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkNumberCollect()
	{
		setCollect(_Prefix + InOutResult.WORKNUMBER.toString(), "");
	}

	/**
	 * Fetch work number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.WORKNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  palette type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEKIND.toString(), arg);
	}
	/**
	 *Set search value for  palette type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteKind(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEKIND.toString(), arg);
	}
	/**
	 *Set search value for  palette type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteKind(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteKind(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPaletteKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.PALETTEKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order palette type
	 * @param num grouping order
	 */
	public void setPaletteKindGroup(int num)
	{
		setGroup(_Prefix + InOutResult.PALETTEKIND.toString(), num);
	}

	/**
	 * Fetch palette type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPaletteKindCollect()
	{
		setCollect(_Prefix + InOutResult.PALETTEKIND.toString(), "");
	}

	/**
	 * Fetch palette type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPaletteKindCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.PALETTEKIND.toString(), compcode);
	}

	/**
	 *Set search value for  location number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOCATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  location number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOCATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  location number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOCATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOCATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for location number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLocationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.LOCATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order location number
	 * @param num grouping order
	 */
	public void setLocationNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.LOCATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch location number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLocationNumberCollect()
	{
		setCollect(_Prefix + InOutResult.LOCATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch location number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLocationNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.LOCATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.SCHEDULENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.SCHEDULENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for schedule number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setScheduleNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.SCHEDULENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order schedule number
	 * @param num grouping order
	 */
	public void setScheduleNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.SCHEDULENUMBER.toString(), num);
	}

	/**
	 * Fetch schedule number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setScheduleNumberCollect()
	{
		setCollect(_Prefix + InOutResult.SCHEDULENUMBER.toString(), "");
	}

	/**
	 * Fetch schedule number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setScheduleNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.SCHEDULENUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEID.toString(), arg, compcode);
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
		setValue(_Prefix + InOutResult.PALETTEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPaletteIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.PALETTEID.toString(), num, bool);
	}

	/**
	 * Set grouping order palette id
	 * @param num grouping order
	 */
	public void setPaletteIdGroup(int num)
	{
		setGroup(_Prefix + InOutResult.PALETTEID.toString(), num);
	}

	/**
	 * Fetch palette id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPaletteIdCollect()
	{
		setCollect(_Prefix + InOutResult.PALETTEID.toString(), "");
	}

	/**
	 * Fetch palette id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPaletteIdCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.PALETTEID.toString(), compcode);
	}

	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CARRYKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CARRYKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for carry key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarryKeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.CARRYKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order carry key
	 * @param num grouping order
	 */
	public void setCarryKeyGroup(int num)
	{
		setGroup(_Prefix + InOutResult.CARRYKEY.toString(), num);
	}

	/**
	 * Fetch carry key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarryKeyCollect()
	{
		setCollect(_Prefix + InOutResult.CARRYKEY.toString(), "");
	}

	/**
	 * Fetch carry key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarryKeyCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.CARRYKEY.toString(), compcode);
	}

	/**
	 *Set search value for  customer code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for customer code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.CUSTOMERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order customer code
	 * @param num grouping order
	 */
	public void setCustomerCodeGroup(int num)
	{
		setGroup(_Prefix + InOutResult.CUSTOMERCODE.toString(), num);
	}

	/**
	 * Fetch customer code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerCodeCollect()
	{
		setCollect(_Prefix + InOutResult.CUSTOMERCODE.toString(), "");
	}

	/**
	 * Fetch customer code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerCodeCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.CUSTOMERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  item name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  item name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for item name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.ITEMNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order item name
	 * @param num grouping order
	 */
	public void setItemName1Group(int num)
	{
		setGroup(_Prefix + InOutResult.ITEMNAME1.toString(), num);
	}

	/**
	 * Fetch item name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemName1Collect()
	{
		setCollect(_Prefix + InOutResult.ITEMNAME1.toString(), "");
	}

	/**
	 * Fetch item name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemName1Collect(String compcode)
	{
		setCollect(_Prefix + InOutResult.ITEMNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  customer name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERNAME.toString(), arg);
	}
	/**
	 *Set search value for  customer name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERNAME.toString(), arg);
	}
	/**
	 *Set search value for  customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for customer name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.CUSTOMERNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order customer name
	 * @param num grouping order
	 */
	public void setCustomerNameGroup(int num)
	{
		setGroup(_Prefix + InOutResult.CUSTOMERNAME.toString(), num);
	}

	/**
	 * Fetch customer name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerNameCollect()
	{
		setCollect(_Prefix + InOutResult.CUSTOMERNAME.toString(), "");
	}

	/**
	 * Fetch customer name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerNameCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.CUSTOMERNAME.toString(), compcode);
	}

	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESTORING.toString(), arg);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESTORING.toString(), arg);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESTORING.toString(), arg, compcode);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESTORING.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for restorage flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReStoringOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.RESTORING.toString(), num, bool);
	}

	/**
	 * Set grouping order restorage flag
	 * @param num grouping order
	 */
	public void setReStoringGroup(int num)
	{
		setGroup(_Prefix + InOutResult.RESTORING.toString(), num);
	}

	/**
	 * Fetch restorage flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReStoringCollect()
	{
		setCollect(_Prefix + InOutResult.RESTORING.toString(), "");
	}

	/**
	 * Fetch restorage flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReStoringCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.RESTORING.toString(), compcode);
	}

	/**
	 *Set search value for  storage date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INCOMMINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INCOMMINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INCOMMINGDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INCOMMINGDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for storage date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInCommingDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.INCOMMINGDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order storage date
	 * @param num grouping order
	 */
	public void setInCommingDateGroup(int num)
	{
		setGroup(_Prefix + InOutResult.INCOMMINGDATE.toString(), num);
	}

	/**
	 * Fetch storage date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInCommingDateCollect()
	{
		setCollect(_Prefix + InOutResult.INCOMMINGDATE.toString(), "");
	}

	/**
	 * Fetch storage date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInCommingDateCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.INCOMMINGDATE.toString(), compcode);
	}

	/**
	 *Set search value for  status flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  status flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order status flag
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + InOutResult.STATUS.toString(), num);
	}

	/**
	 * Fetch status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + InOutResult.STATUS.toString(), "");
	}

	/**
	 * Fetch status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.STATUS.toString(), compcode);
	}

	/**
	 *Set search value for  host report flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReport(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.REPORT.toString(), arg);
	}
	/**
	 *Set search value for  host report flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReport(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.REPORT.toString(), arg);
	}
	/**
	 *Set search value for  host report flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReport(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.REPORT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  host report flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReport(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.REPORT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for host report flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReportOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.REPORT.toString(), num, bool);
	}

	/**
	 * Set grouping order host report flag
	 * @param num grouping order
	 */
	public void setReportGroup(int num)
	{
		setGroup(_Prefix + InOutResult.REPORT.toString(), num);
	}

	/**
	 * Fetch host report flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReportCollect()
	{
		setCollect(_Prefix + InOutResult.REPORT.toString(), "");
	}

	/**
	 * Fetch host report flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReportCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.REPORT.toString(), compcode);
	}

	/**
	 *Set search value for  order number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ORDERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  order number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ORDERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  order number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ORDERNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  order number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ORDERNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for order number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setOrderNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.ORDERNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order order number
	 * @param num grouping order
	 */
	public void setOrderNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.ORDERNUMBER.toString(), num);
	}

	/**
	 * Fetch order number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setOrderNumberCollect()
	{
		setCollect(_Prefix + InOutResult.ORDERNUMBER.toString(), "");
	}

	/**
	 * Fetch order number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setOrderNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.ORDERNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  line number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LINENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  line number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LINENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  line number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LINENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  line number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LINENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for line number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLineNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + InOutResult.LINENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order line number
	 * @param num grouping order
	 */
	public void setLineNumberGroup(int num)
	{
		setGroup(_Prefix + InOutResult.LINENUMBER.toString(), num);
	}

	/**
	 * Fetch line number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLineNumberCollect()
	{
		setCollect(_Prefix + InOutResult.LINENUMBER.toString(), "");
	}

	/**
	 * Fetch line number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLineNumberCollect(String compcode)
	{
		setCollect(_Prefix + InOutResult.LINENUMBER.toString(), compcode);
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
		return "$Id: InOutResultSearchKey.java,v 1.2 2006/11/09 07:52:59 suresh Exp $" ;
	}
}

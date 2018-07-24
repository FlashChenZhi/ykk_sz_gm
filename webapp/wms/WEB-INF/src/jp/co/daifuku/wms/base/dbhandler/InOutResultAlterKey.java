//$Id: InOutResultAlterKey.java,v 1.3 2006/11/20 04:28:51 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.InOutResult;

/**
 * Update key class for INOUTRESULT use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/20 04:28:51 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class InOutResultAlterKey
		extends AbstractSQLAlterKey
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
	public InOutResultAlterKey()
	{
		super(new InOutResult()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for storage result date
	 * @param arg storage result date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoreDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STOREDATE.toString(), arg);
	}
	/**
	 * Set search value for storage result date
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
	 * Set update value for storage result date
	 * @param arg Update value for storage result date
	 */
	public void updateStoreDate(Date arg)
	{
		setUpdValue(_Prefix + InOutResult.STOREDATE.toString(), arg);
	}

	/**
	 * Set search value for result work type
	 * @param arg result work type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESULTKIND.toString(), arg);
	}
	/**
	 * Set search value for result work type
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
	 * Set update value for result work type
	 * @param arg Update value for result work type
	 */
	public void updateResultKind(int arg)
	{
		setUpdValue(_Prefix + InOutResult.RESULTKIND.toString(), arg);
	}

	/**
	 * Set search value for consignor code
	 * @param arg consignor code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for consignor code
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
	 * Set update value for consignor code
	 * @param arg Update value for consignor code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + InOutResult.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for consignor name
	 * @param arg consignor name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CONSIGNORNAME.toString(), arg);
	}
	/**
	 * Set search value for consignor name
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
	 * Set update value for consignor name
	 * @param arg Update value for consignor name
	 */
	public void updateConsignorName(String arg)
	{
		setUpdValue(_Prefix + InOutResult.CONSIGNORNAME.toString(), arg);
	}

	/**
	 * Set search value for item key
	 * @param arg item key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for item key
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
	 * Set update value for item key
	 * @param arg Update value for item key
	 */
	public void updateItemCode(String arg)
	{
		setUpdValue(_Prefix + InOutResult.ITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for lot number
	 * @param arg lot number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOTNUMBER.toString(), arg);
	}
	/**
	 * Set search value for lot number
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
	 * Set update value for lot number
	 * @param arg Update value for lot number
	 */
	public void updateLotNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.LOTNUMBER.toString(), arg);
	}

	/**
	 * Set search value for work type
	 * @param arg work type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKTYPE.toString(), arg);
	}
	/**
	 * Set search value for work type
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
	 * Set update value for work type
	 * @param arg Update value for work type
	 */
	public void updateWorkType(String arg)
	{
		setUpdValue(_Prefix + InOutResult.WORKTYPE.toString(), arg);
	}

	/**
	 * Set search value for station number
	 * @param arg station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
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
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
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
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for aisle station number
	 * @param arg aisle station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for aisle station number
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
	 * Set update value for aisle station number
	 * @param arg Update value for aisle station number
	 */
	public void updateAisleStationNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.AISLESTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for quantity per case
	 * @param arg quantity per case<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for quantity per case
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
	 * Set update value for quantity per case
	 * @param arg Update value for quantity per case
	 */
	public void updateEnteringQty(int arg)
	{
		setUpdValue(_Prefix + InOutResult.ENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for quantity per bundle
	 * @param arg quantity per bundle<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for quantity per bundle
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
	 * Set update value for quantity per bundle
	 * @param arg Update value for quantity per bundle
	 */
	public void updateBundleEnteringQty(int arg)
	{
		setUpdValue(_Prefix + InOutResult.BUNDLEENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for storage/picking qty
	 * @param arg storage/picking qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInOutQuantity(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INOUTQUANTITY.toString(), arg);
	}
	/**
	 * Set search value for storage/picking qty
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
	 * Set update value for storage/picking qty
	 * @param arg Update value for storage/picking qty
	 */
	public void updateInOutQuantity(int arg)
	{
		setUpdValue(_Prefix + InOutResult.INOUTQUANTITY.toString(), arg);
	}

	/**
	 * Set search value for work number
	 * @param arg work number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.WORKNUMBER.toString(), arg);
	}
	/**
	 * Set search value for work number
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
	 * Set update value for work number
	 * @param arg Update value for work number
	 */
	public void updateWorkNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.WORKNUMBER.toString(), arg);
	}

	/**
	 * Set search value for palette type
	 * @param arg palette type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEKIND.toString(), arg);
	}
	/**
	 * Set search value for palette type
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
	 * Set update value for palette type
	 * @param arg Update value for palette type
	 */
	public void updatePaletteKind(int arg)
	{
		setUpdValue(_Prefix + InOutResult.PALETTEKIND.toString(), arg);
	}

	/**
	 * Set search value for location number
	 * @param arg location number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LOCATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for location number
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
	 * Set update value for location number
	 * @param arg Update value for location number
	 */
	public void updateLocationNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.LOCATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for schedule number
	 * @param arg schedule number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 * Set search value for schedule number
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
	 * Set update value for schedule number
	 * @param arg Update value for schedule number
	 */
	public void updateScheduleNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.SCHEDULENUMBER.toString(), arg);
	}

	/**
	 * Set search value for palette id
	 * @param arg palette id<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.PALETTEID.toString(), arg);
	}
	/**
	 * Set search value for palette id
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
	 * Set update value for palette id
	 * @param arg Update value for palette id
	 */
	public void updatePaletteId(int arg)
	{
		setUpdValue(_Prefix + InOutResult.PALETTEID.toString(), arg);
	}

	/**
	 * Set search value for carry key
	 * @param arg carry key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CARRYKEY.toString(), arg);
	}
	/**
	 * Set search value for carry key
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
	 * Set update value for carry key
	 * @param arg Update value for carry key
	 */
	public void updateCarryKey(String arg)
	{
		setUpdValue(_Prefix + InOutResult.CARRYKEY.toString(), arg);
	}

	/**
	 * Set search value for customer code
	 * @param arg customer code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERCODE.toString(), arg);
	}
	/**
	 * Set search value for customer code
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
	 * Set update value for customer code
	 * @param arg Update value for customer code
	 */
	public void updateCustomerCode(String arg)
	{
		setUpdValue(_Prefix + InOutResult.CUSTOMERCODE.toString(), arg);
	}

	/**
	 * Set search value for item name
	 * @param arg item name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ITEMNAME1.toString(), arg);
	}
	/**
	 * Set search value for item name
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
	 * Set update value for item name
	 * @param arg Update value for item name
	 */
	public void updateItemName1(String arg)
	{
		setUpdValue(_Prefix + InOutResult.ITEMNAME1.toString(), arg);
	}

	/**
	 * Set search value for customer name
	 * @param arg customer name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.CUSTOMERNAME.toString(), arg);
	}
	/**
	 * Set search value for customer name
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
	 * Set update value for customer name
	 * @param arg Update value for customer name
	 */
	public void updateCustomerName(String arg)
	{
		setUpdValue(_Prefix + InOutResult.CUSTOMERNAME.toString(), arg);
	}

	/**
	 * Set search value for restorage flag
	 * @param arg restorage flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.RESTORING.toString(), arg);
	}
	/**
	 * Set search value for restorage flag
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
	 * Set update value for restorage flag
	 * @param arg Update value for restorage flag
	 */
	public void updateReStoring(int arg)
	{
		setUpdValue(_Prefix + InOutResult.RESTORING.toString(), arg);
	}

	/**
	 * Set search value for storage date
	 * @param arg storage date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.INCOMMINGDATE.toString(), arg);
	}
	/**
	 * Set search value for storage date
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
	 * Set update value for storage date
	 * @param arg Update value for storage date
	 */
	public void updateInCommingDate(Date arg)
	{
		setUpdValue(_Prefix + InOutResult.INCOMMINGDATE.toString(), arg);
	}

	/**
	 * Set search value for status flag
	 * @param arg status flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.STATUS.toString(), arg);
	}
	/**
	 * Set search value for status flag
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
	 * Set update value for status flag
	 * @param arg Update value for status flag
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + InOutResult.STATUS.toString(), arg);
	}

	/**
	 * Set search value for host report flag
	 * @param arg host report flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReport(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.REPORT.toString(), arg);
	}
	/**
	 * Set search value for host report flag
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
	 * Set update value for host report flag
	 * @param arg Update value for host report flag
	 */
	public void updateReport(int arg)
	{
		setUpdValue(_Prefix + InOutResult.REPORT.toString(), arg);
	}

	/**
	 * Set search value for order number
	 * @param arg order number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.ORDERNUMBER.toString(), arg);
	}
	/**
	 * Set search value for order number
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
	 * Set update value for order number
	 * @param arg Update value for order number
	 */
	public void updateOrderNumber(String arg)
	{
		setUpdValue(_Prefix + InOutResult.ORDERNUMBER.toString(), arg);
	}

	/**
	 * Set search value for line number
	 * @param arg line number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + InOutResult.LINENUMBER.toString(), arg);
	}
	/**
	 * Set search value for line number
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
	 * Set update value for line number
	 * @param arg Update value for line number
	 */
	public void updateLineNumber(int arg)
	{
		setUpdValue(_Prefix + InOutResult.LINENUMBER.toString(), arg);
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
		return "$Id: InOutResultAlterKey.java,v 1.3 2006/11/20 04:28:51 suresh Exp $" ;
	}
}

//$Id: CustomerSearchKey.java,v 1.2 2006/11/09 07:53:09 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Customer;

/**
 * This is the search key class for CUSTOMER use.
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:53:09 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class CustomerSearchKey
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
	public CustomerSearchKey()
	{
		super(new Customer()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONSIGNORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + Customer.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + Customer.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + Customer.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CUSTOMERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer code
	 * @param num grouping order
	 */
	public void setCustomerCodeGroup(int num)
	{
		setGroup(_Prefix + Customer.CUSTOMERCODE.toString(), num);
	}

	/**
	 * Fetch Customer code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerCodeCollect()
	{
		setCollect(_Prefix + Customer.CUSTOMERCODE.toString(), "");
	}

	/**
	 * Fetch Customer code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerCodeCollect(String compcode)
	{
		setCollect(_Prefix + Customer.CUSTOMERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CUSTOMERNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer name
	 * @param num grouping order
	 */
	public void setCustomerName1Group(int num)
	{
		setGroup(_Prefix + Customer.CUSTOMERNAME1.toString(), num);
	}

	/**
	 * Fetch Customer name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerName1Collect()
	{
		setCollect(_Prefix + Customer.CUSTOMERNAME1.toString(), "");
	}

	/**
	 * Fetch Customer name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerName1Collect(String compcode)
	{
		setCollect(_Prefix + Customer.CUSTOMERNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Carrier code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Carrier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Carrier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Carrier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Carrier code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarrierCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CARRIERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Carrier code
	 * @param num grouping order
	 */
	public void setCarrierCodeGroup(int num)
	{
		setGroup(_Prefix + Customer.CARRIERCODE.toString(), num);
	}

	/**
	 * Fetch Carrier code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarrierCodeCollect()
	{
		setCollect(_Prefix + Customer.CARRIERCODE.toString(), "");
	}

	/**
	 * Fetch Carrier code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarrierCodeCollect(String compcode)
	{
		setCollect(_Prefix + Customer.CARRIERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Carrier name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Carrier name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Carrier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Carrier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Carrier name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarrierNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CARRIERNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Carrier name
	 * @param num grouping order
	 */
	public void setCarrierNameGroup(int num)
	{
		setGroup(_Prefix + Customer.CARRIERNAME.toString(), num);
	}

	/**
	 * Fetch Carrier name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarrierNameCollect()
	{
		setCollect(_Prefix + Customer.CARRIERNAME.toString(), "");
	}

	/**
	 * Fetch Carrier name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarrierNameCollect(String compcode)
	{
		setCollect(_Prefix + Customer.CARRIERNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Route
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRoute(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ROUTE.toString(), arg);
	}
	/**
	 *Set search value for  Route
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRoute(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ROUTE.toString(), arg);
	}
	/**
	 *Set search value for  Route
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRoute(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ROUTE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Route
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRoute(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ROUTE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Route
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRouteOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.ROUTE.toString(), num, bool);
	}

	/**
	 * Set grouping order Route
	 * @param num grouping order
	 */
	public void setRouteGroup(int num)
	{
		setGroup(_Prefix + Customer.ROUTE.toString(), num);
	}

	/**
	 * Fetch Route info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRouteCollect()
	{
		setCollect(_Prefix + Customer.ROUTE.toString(), "");
	}

	/**
	 * Fetch Route info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRouteCollect(String compcode)
	{
		setCollect(_Prefix + Customer.ROUTE.toString(), compcode);
	}

	/**
	 *Set search value for  Postal code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPostalCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.POSTALCODE.toString(), arg);
	}
	/**
	 *Set search value for  Postal code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPostalCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.POSTALCODE.toString(), arg);
	}
	/**
	 *Set search value for  Postal code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPostalCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.POSTALCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Postal code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPostalCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.POSTALCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Postal code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPostalCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.POSTALCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Postal code
	 * @param num grouping order
	 */
	public void setPostalCodeGroup(int num)
	{
		setGroup(_Prefix + Customer.POSTALCODE.toString(), num);
	}

	/**
	 * Fetch Postal code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPostalCodeCollect()
	{
		setCollect(_Prefix + Customer.POSTALCODE.toString(), "");
	}

	/**
	 * Fetch Postal code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPostalCodeCollect(String compcode)
	{
		setCollect(_Prefix + Customer.POSTALCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Prefecture
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrefecture(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.PREFECTURE.toString(), arg);
	}
	/**
	 *Set search value for  Prefecture
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrefecture(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.PREFECTURE.toString(), arg);
	}
	/**
	 *Set search value for  Prefecture
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrefecture(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.PREFECTURE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Prefecture
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrefecture(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.PREFECTURE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Prefecture
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPrefectureOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.PREFECTURE.toString(), num, bool);
	}

	/**
	 * Set grouping order Prefecture
	 * @param num grouping order
	 */
	public void setPrefectureGroup(int num)
	{
		setGroup(_Prefix + Customer.PREFECTURE.toString(), num);
	}

	/**
	 * Fetch Prefecture info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPrefectureCollect()
	{
		setCollect(_Prefix + Customer.PREFECTURE.toString(), "");
	}

	/**
	 * Fetch Prefecture info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPrefectureCollect(String compcode)
	{
		setCollect(_Prefix + Customer.PREFECTURE.toString(), compcode);
	}

	/**
	 *Set search value for  Address
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS.toString(), arg);
	}
	/**
	 *Set search value for  Address
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS.toString(), arg);
	}
	/**
	 *Set search value for  Address
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Address
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Address
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAddressOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.ADDRESS.toString(), num, bool);
	}

	/**
	 * Set grouping order Address
	 * @param num grouping order
	 */
	public void setAddressGroup(int num)
	{
		setGroup(_Prefix + Customer.ADDRESS.toString(), num);
	}

	/**
	 * Fetch Address info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAddressCollect()
	{
		setCollect(_Prefix + Customer.ADDRESS.toString(), "");
	}

	/**
	 * Fetch Address info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAddressCollect(String compcode)
	{
		setCollect(_Prefix + Customer.ADDRESS.toString(), compcode);
	}

	/**
	 *Set search value for  Building name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress2(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS2.toString(), arg);
	}
	/**
	 *Set search value for  Building name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress2(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS2.toString(), arg);
	}
	/**
	 *Set search value for  Building name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress2(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS2.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Building name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress2(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS2.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Building name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAddress2Order(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.ADDRESS2.toString(), num, bool);
	}

	/**
	 * Set grouping order Building name
	 * @param num grouping order
	 */
	public void setAddress2Group(int num)
	{
		setGroup(_Prefix + Customer.ADDRESS2.toString(), num);
	}

	/**
	 * Fetch Building name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAddress2Collect()
	{
		setCollect(_Prefix + Customer.ADDRESS2.toString(), "");
	}

	/**
	 * Fetch Building name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAddress2Collect(String compcode)
	{
		setCollect(_Prefix + Customer.ADDRESS2.toString(), compcode);
	}

	/**
	 *Set search value for  Contact1
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT1.toString(), arg);
	}
	/**
	 *Set search value for  Contact1
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT1.toString(), arg);
	}
	/**
	 *Set search value for  Contact1
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Contact1
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Contact1
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setContact1Order(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CONTACT1.toString(), num, bool);
	}

	/**
	 * Set grouping order Contact1
	 * @param num grouping order
	 */
	public void setContact1Group(int num)
	{
		setGroup(_Prefix + Customer.CONTACT1.toString(), num);
	}

	/**
	 * Fetch Contact1 info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setContact1Collect()
	{
		setCollect(_Prefix + Customer.CONTACT1.toString(), "");
	}

	/**
	 * Fetch Contact1 info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setContact1Collect(String compcode)
	{
		setCollect(_Prefix + Customer.CONTACT1.toString(), compcode);
	}

	/**
	 *Set search value for  Contact2
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact2(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT2.toString(), arg);
	}
	/**
	 *Set search value for  Contact2
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact2(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT2.toString(), arg);
	}
	/**
	 *Set search value for  Contact2
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact2(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT2.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Contact2
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact2(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT2.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Contact2
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setContact2Order(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CONTACT2.toString(), num, bool);
	}

	/**
	 * Set grouping order Contact2
	 * @param num grouping order
	 */
	public void setContact2Group(int num)
	{
		setGroup(_Prefix + Customer.CONTACT2.toString(), num);
	}

	/**
	 * Fetch Contact2 info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setContact2Collect()
	{
		setCollect(_Prefix + Customer.CONTACT2.toString(), "");
	}

	/**
	 * Fetch Contact2 info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setContact2Collect(String compcode)
	{
		setCollect(_Prefix + Customer.CONTACT2.toString(), compcode);
	}

	/**
	 *Set search value for  Contact3
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact3(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT3.toString(), arg);
	}
	/**
	 *Set search value for  Contact3
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact3(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT3.toString(), arg);
	}
	/**
	 *Set search value for  Contact3
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact3(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT3.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Contact3
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact3(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT3.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Contact3
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setContact3Order(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.CONTACT3.toString(), num, bool);
	}

	/**
	 * Set grouping order Contact3
	 * @param num grouping order
	 */
	public void setContact3Group(int num)
	{
		setGroup(_Prefix + Customer.CONTACT3.toString(), num);
	}

	/**
	 * Fetch Contact3 info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setContact3Collect()
	{
		setCollect(_Prefix + Customer.CONTACT3.toString(), "");
	}

	/**
	 * Fetch Contact3 info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setContact3Collect(String compcode)
	{
		setCollect(_Prefix + Customer.CONTACT3.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + Customer.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + Customer.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + Customer.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEPNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + Customer.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + Customer.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + Customer.LASTUPDATEPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last used date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUSEDDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last used date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUSEDDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last used date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUSEDDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last used date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUSEDDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last used date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUsedDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.LASTUSEDDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last used date
	 * @param num grouping order
	 */
	public void setLastUsedDateGroup(int num)
	{
		setGroup(_Prefix + Customer.LASTUSEDDATE.toString(), num);
	}

	/**
	 * Fetch Last used date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUsedDateCollect()
	{
		setCollect(_Prefix + Customer.LASTUSEDDATE.toString(), "");
	}

	/**
	 * Fetch Last used date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUsedDateCollect(String compcode)
	{
		setCollect(_Prefix + Customer.LASTUSEDDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Customer.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Customer.DELETEFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Customer.DELETEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Delete flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDeleteFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Customer.DELETEFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Delete flag
	 * @param num grouping order
	 */
	public void setDeleteFlagGroup(int num)
	{
		setGroup(_Prefix + Customer.DELETEFLAG.toString(), num);
	}

	/**
	 * Fetch Delete flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDeleteFlagCollect()
	{
		setCollect(_Prefix + Customer.DELETEFLAG.toString(), "");
	}

	/**
	 * Fetch Delete flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDeleteFlagCollect(String compcode)
	{
		setCollect(_Prefix + Customer.DELETEFLAG.toString(), compcode);
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
		return "$Id: CustomerSearchKey.java,v 1.2 2006/11/09 07:53:09 suresh Exp $" ;
	}
}

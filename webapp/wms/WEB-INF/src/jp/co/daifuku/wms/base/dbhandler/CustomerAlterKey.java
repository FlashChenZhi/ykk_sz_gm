//$Id: CustomerAlterKey.java,v 1.2 2006/11/09 07:53:11 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Customer;

/**
 * Update key class for CUSTOMER use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:53:11 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class CustomerAlterKey
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
	public CustomerAlterKey()
	{
		super(new Customer()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Consignor code
	 * @param arg Consignor code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for Consignor code
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
	 * Set update value for Consignor code
	 * @param arg Update value for Consignor code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + Customer.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for Customer code
	 * @param arg Customer code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERCODE.toString(), arg);
	}
	/**
	 * Set search value for Customer code
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
	 * Set update value for Customer code
	 * @param arg Update value for Customer code
	 */
	public void updateCustomerCode(String arg)
	{
		setUpdValue(_Prefix + Customer.CUSTOMERCODE.toString(), arg);
	}

	/**
	 * Set search value for Customer name
	 * @param arg Customer name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Customer name
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
	 * Set update value for Customer name
	 * @param arg Update value for Customer name
	 */
	public void updateCustomerName1(String arg)
	{
		setUpdValue(_Prefix + Customer.CUSTOMERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Carrier code
	 * @param arg Carrier code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERCODE.toString(), arg);
	}
	/**
	 * Set search value for Carrier code
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
	 * Set update value for Carrier code
	 * @param arg Update value for Carrier code
	 */
	public void updateCarrierCode(String arg)
	{
		setUpdValue(_Prefix + Customer.CARRIERCODE.toString(), arg);
	}

	/**
	 * Set search value for Carrier name
	 * @param arg Carrier name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarrierName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CARRIERNAME.toString(), arg);
	}
	/**
	 * Set search value for Carrier name
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
	 * Set update value for Carrier name
	 * @param arg Update value for Carrier name
	 */
	public void updateCarrierName(String arg)
	{
		setUpdValue(_Prefix + Customer.CARRIERNAME.toString(), arg);
	}

	/**
	 * Set search value for Route
	 * @param arg Route<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRoute(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ROUTE.toString(), arg);
	}
	/**
	 * Set search value for Route
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
	 * Set update value for Route
	 * @param arg Update value for Route
	 */
	public void updateRoute(String arg)
	{
		setUpdValue(_Prefix + Customer.ROUTE.toString(), arg);
	}

	/**
	 * Set search value for Postal code
	 * @param arg Postal code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPostalCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.POSTALCODE.toString(), arg);
	}
	/**
	 * Set search value for Postal code
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
	 * Set update value for Postal code
	 * @param arg Update value for Postal code
	 */
	public void updatePostalCode(String arg)
	{
		setUpdValue(_Prefix + Customer.POSTALCODE.toString(), arg);
	}

	/**
	 * Set search value for Prefecture
	 * @param arg Prefecture<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrefecture(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.PREFECTURE.toString(), arg);
	}
	/**
	 * Set search value for Prefecture
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
	 * Set update value for Prefecture
	 * @param arg Update value for Prefecture
	 */
	public void updatePrefecture(String arg)
	{
		setUpdValue(_Prefix + Customer.PREFECTURE.toString(), arg);
	}

	/**
	 * Set search value for Address
	 * @param arg Address<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS.toString(), arg);
	}
	/**
	 * Set search value for Address
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
	 * Set update value for Address
	 * @param arg Update value for Address
	 */
	public void updateAddress(String arg)
	{
		setUpdValue(_Prefix + Customer.ADDRESS.toString(), arg);
	}

	/**
	 * Set search value for Building name
	 * @param arg Building name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAddress2(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.ADDRESS2.toString(), arg);
	}
	/**
	 * Set search value for Building name
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
	 * Set update value for Building name
	 * @param arg Update value for Building name
	 */
	public void updateAddress2(String arg)
	{
		setUpdValue(_Prefix + Customer.ADDRESS2.toString(), arg);
	}

	/**
	 * Set search value for Contact1
	 * @param arg Contact1<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT1.toString(), arg);
	}
	/**
	 * Set search value for Contact1
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
	 * Set update value for Contact1
	 * @param arg Update value for Contact1
	 */
	public void updateContact1(String arg)
	{
		setUpdValue(_Prefix + Customer.CONTACT1.toString(), arg);
	}

	/**
	 * Set search value for Contact2
	 * @param arg Contact2<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact2(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT2.toString(), arg);
	}
	/**
	 * Set search value for Contact2
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
	 * Set update value for Contact2
	 * @param arg Update value for Contact2
	 */
	public void updateContact2(String arg)
	{
		setUpdValue(_Prefix + Customer.CONTACT2.toString(), arg);
	}

	/**
	 * Set search value for Contact3
	 * @param arg Contact3<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContact3(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.CONTACT3.toString(), arg);
	}
	/**
	 * Set search value for Contact3
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
	 * Set update value for Contact3
	 * @param arg Update value for Contact3
	 */
	public void updateContact3(String arg)
	{
		setUpdValue(_Prefix + Customer.CONTACT3.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
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
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Customer.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
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
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Customer.LASTUPDATEPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last used date
	 * @param arg Last used date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.LASTUSEDDATE.toString(), arg);
	}
	/**
	 * Set search value for Last used date
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
	 * Set update value for Last used date
	 * @param arg Update value for Last used date
	 */
	public void updateLastUsedDate(Date arg)
	{
		setUpdValue(_Prefix + Customer.LASTUSEDDATE.toString(), arg);
	}

	/**
	 * Set search value for Delete flag
	 * @param arg Delete flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Customer.DELETEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Delete flag
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
	 * Set update value for Delete flag
	 * @param arg Update value for Delete flag
	 */
	public void updateDeleteFlag(String arg)
	{
		setUpdValue(_Prefix + Customer.DELETEFLAG.toString(), arg);
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
		return "$Id: CustomerAlterKey.java,v 1.2 2006/11/09 07:53:11 suresh Exp $" ;
	}
}

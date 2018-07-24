//$Id: SortingPlanSearchKey.java,v 1.3 2006/11/13 04:33:00 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.SortingPlan;

/**
 * This is the search key class for SORTINGPLAN use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:00 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class SortingPlanSearchKey
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
	public SortingPlanSearchKey()
	{
		super(new SortingPlan()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Sorting plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSortingPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Sorting plan unique key
	 * @param num grouping order
	 */
	public void setSortingPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Sorting plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSortingPlanUkeyCollect()
	{
		setCollect(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Sorting plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSortingPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.SORTINGPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.STATUSFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.STATUSFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Status flag
	 * @param num grouping order
	 */
	public void setStatusFlagGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.STATUSFLAG.toString(), num);
	}

	/**
	 * Fetch Status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusFlagCollect()
	{
		setCollect(_Prefix + SortingPlan.STATUSFLAG.toString(), "");
	}

	/**
	 * Fetch Status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusFlagCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.STATUSFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.PLANDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan date
	 * @param num grouping order
	 */
	public void setPlanDateGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.PLANDATE.toString(), num);
	}

	/**
	 * Fetch Plan date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanDateCollect()
	{
		setCollect(_Prefix + SortingPlan.PLANDATE.toString(), "");
	}

	/**
	 * Fetch Plan date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanDateCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.PLANDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORCODE.toString(), arg, compcode);
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
		setValue(_Prefix + SortingPlan.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + SortingPlan.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.CONSIGNORNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor name
	 * @param num grouping order
	 */
	public void setConsignorNameGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.CONSIGNORNAME.toString(), num);
	}

	/**
	 * Fetch Consignor name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorNameCollect()
	{
		setCollect(_Prefix + SortingPlan.CONSIGNORNAME.toString(), "");
	}

	/**
	 * Fetch Consignor name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorNameCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.CONSIGNORNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CUSTOMERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + SortingPlan.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.CUSTOMERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer code
	 * @param num grouping order
	 */
	public void setCustomerCodeGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.CUSTOMERCODE.toString(), num);
	}

	/**
	 * Fetch Customer code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerCodeCollect()
	{
		setCollect(_Prefix + SortingPlan.CUSTOMERCODE.toString(), "");
	}

	/**
	 * Fetch Customer code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerCodeCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.CUSTOMERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer name
	 * @param num grouping order
	 */
	public void setCustomerName1Group(int num)
	{
		setGroup(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), num);
	}

	/**
	 * Fetch Customer name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerName1Collect()
	{
		setCollect(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), "");
	}

	/**
	 * Fetch Customer name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerName1Collect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.CUSTOMERNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping ticket no
	 * @param num grouping order
	 */
	public void setShippingTicketNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), num);
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingTicketNoCollect()
	{
		setCollect(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), "");
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.SHIPPINGTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping line no
	 * @param num grouping order
	 */
	public void setShippingLineNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), num);
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingLineNoCollect()
	{
		setCollect(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), "");
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingLineNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.SHIPPINGLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.ITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Item code
	 * @param num grouping order
	 */
	public void setItemCodeGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.ITEMCODE.toString(), num);
	}

	/**
	 * Fetch Item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemCodeCollect()
	{
		setCollect(_Prefix + SortingPlan.ITEMCODE.toString(), "");
	}

	/**
	 * Fetch Item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.ITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.ITEMNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Item name
	 * @param num grouping order
	 */
	public void setItemName1Group(int num)
	{
		setGroup(_Prefix + SortingPlan.ITEMNAME1.toString(), num);
	}

	/**
	 * Fetch Item name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemName1Collect()
	{
		setCollect(_Prefix + SortingPlan.ITEMNAME1.toString(), "");
	}

	/**
	 * Fetch Item name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemName1Collect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.ITEMNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Sorting plan qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Sorting plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Sorting plan qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.PLANQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Sorting plan qty
	 * @param num grouping order
	 */
	public void setPlanQtyGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.PLANQTY.toString(), num);
	}

	/**
	 * Fetch Sorting plan qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanQtyCollect()
	{
		setCollect(_Prefix + SortingPlan.PLANQTY.toString(), "");
	}

	/**
	 * Fetch Sorting plan qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanQtyCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.PLANQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Sorting result qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.RESULTQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Sorting result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Sorting result qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.RESULTQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Sorting result qty
	 * @param num grouping order
	 */
	public void setResultQtyGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.RESULTQTY.toString(), num);
	}

	/**
	 * Fetch Sorting result qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultQtyCollect()
	{
		setCollect(_Prefix + SortingPlan.RESULTQTY.toString(), "");
	}

	/**
	 * Fetch Sorting result qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultQtyCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.RESULTQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Storage count
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Storage count
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Storage count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHORTAGECNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Storage count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SHORTAGECNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Storage count
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShortageCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.SHORTAGECNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Storage count
	 * @param num grouping order
	 */
	public void setShortageCntGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.SHORTAGECNT.toString(), num);
	}

	/**
	 * Fetch Storage count info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShortageCntCollect()
	{
		setCollect(_Prefix + SortingPlan.SHORTAGECNT.toString(), "");
	}

	/**
	 * Fetch Storage count info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShortageCntCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.SHORTAGECNT.toString(), compcode);
	}

	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Entering case qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.ENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Entering case qty
	 * @param num grouping order
	 */
	public void setEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.ENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch Entering case qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEnteringQtyCollect()
	{
		setCollect(_Prefix + SortingPlan.ENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch Entering case qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.ENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bundle qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Bundle qty
	 * @param num grouping order
	 */
	public void setBundleEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch Bundle qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleEnteringQtyCollect()
	{
		setCollect(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch Bundle qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.BUNDLEENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case/Piece flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCasePieceFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Case/Piece flag
	 * @param num grouping order
	 */
	public void setCasePieceFlagGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), num);
	}

	/**
	 * Fetch Case/Piece flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCasePieceFlagCollect()
	{
		setCollect(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), "");
	}

	/**
	 * Fetch Case/Piece flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCasePieceFlagCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.CASEPIECEFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITF.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.ITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case ITF
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItfOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.ITF.toString(), num, bool);
	}

	/**
	 * Set grouping order Case ITF
	 * @param num grouping order
	 */
	public void setItfGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.ITF.toString(), num);
	}

	/**
	 * Fetch Case ITF info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItfCollect()
	{
		setCollect(_Prefix + SortingPlan.ITF.toString(), "");
	}

	/**
	 * Fetch Case ITF info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItfCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.ITF.toString(), compcode);
	}

	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEITF.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BUNDLEITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bundle ITF
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleItfOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.BUNDLEITF.toString(), num, bool);
	}

	/**
	 * Set grouping order Bundle ITF
	 * @param num grouping order
	 */
	public void setBundleItfGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.BUNDLEITF.toString(), num);
	}

	/**
	 * Fetch Bundle ITF info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleItfCollect()
	{
		setCollect(_Prefix + SortingPlan.BUNDLEITF.toString(), "");
	}

	/**
	 * Fetch Bundle ITF info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleItfCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.BUNDLEITF.toString(), compcode);
	}

	/**
	 *Set search value for  Piece Sorting location
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PIECELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Piece Sorting location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PIECELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Piece Sorting location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PIECELOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Piece Sorting location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PIECELOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Piece Sorting location
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPieceLocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.PIECELOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Piece Sorting location
	 * @param num grouping order
	 */
	public void setPieceLocationGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.PIECELOCATION.toString(), num);
	}

	/**
	 * Fetch Piece Sorting location info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPieceLocationCollect()
	{
		setCollect(_Prefix + SortingPlan.PIECELOCATION.toString(), "");
	}

	/**
	 * Fetch Piece Sorting location info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPieceLocationCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.PIECELOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  Case Sorting location
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Case Sorting location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Case Sorting location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASELOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case Sorting location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.CASELOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case Sorting location
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCaseLocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.CASELOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Case Sorting location
	 * @param num grouping order
	 */
	public void setCaseLocationGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.CASELOCATION.toString(), num);
	}

	/**
	 * Fetch Case Sorting location info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCaseLocationCollect()
	{
		setCollect(_Prefix + SortingPlan.CASELOCATION.toString(), "");
	}

	/**
	 * Fetch Case Sorting location info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCaseLocationCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.CASELOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  TC/DC Flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC Flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC Flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TCDCFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  TC/DC Flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TCDCFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for TC/DC Flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTcdcFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.TCDCFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order TC/DC Flag
	 * @param num grouping order
	 */
	public void setTcdcFlagGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.TCDCFLAG.toString(), num);
	}

	/**
	 * Fetch TC/DC Flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTcdcFlagCollect()
	{
		setCollect(_Prefix + SortingPlan.TCDCFLAG.toString(), "");
	}

	/**
	 * Fetch TC/DC Flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTcdcFlagCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.TCDCFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Supplier code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSupplierCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.SUPPLIERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Supplier code
	 * @param num grouping order
	 */
	public void setSupplierCodeGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.SUPPLIERCODE.toString(), num);
	}

	/**
	 * Fetch Supplier code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSupplierCodeCollect()
	{
		setCollect(_Prefix + SortingPlan.SUPPLIERCODE.toString(), "");
	}

	/**
	 * Fetch Supplier code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSupplierCodeCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.SUPPLIERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Supplier name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSupplierName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Supplier name
	 * @param num grouping order
	 */
	public void setSupplierName1Group(int num)
	{
		setGroup(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), num);
	}

	/**
	 * Fetch Supplier name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSupplierName1Collect()
	{
		setCollect(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), "");
	}

	/**
	 * Fetch Supplier name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSupplierName1Collect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.SUPPLIERNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving Ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving Ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving Ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving Ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving Ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving Ticket no
	 * @param num grouping order
	 */
	public void setInstockTicketNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), num);
	}

	/**
	 * Fetch Receiving Ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockTicketNoCollect()
	{
		setCollect(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), "");
	}

	/**
	 * Fetch Receiving Ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.INSTOCKTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKLINENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.INSTOCKLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.INSTOCKLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving line no
	 * @param num grouping order
	 */
	public void setInstockLineNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.INSTOCKLINENO.toString(), num);
	}

	/**
	 * Fetch Receiving line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockLineNoCollect()
	{
		setCollect(_Prefix + SortingPlan.INSTOCKLINENO.toString(), "");
	}

	/**
	 * Fetch Receiving line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockLineNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.INSTOCKLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.USEBYDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.USEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Use By Date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setUseByDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.USEBYDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Use By Date
	 * @param num grouping order
	 */
	public void setUseByDateGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.USEBYDATE.toString(), num);
	}

	/**
	 * Fetch Use By Date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setUseByDateCollect()
	{
		setCollect(_Prefix + SortingPlan.USEBYDATE.toString(), "");
	}

	/**
	 * Fetch Use By Date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setUseByDateCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.USEBYDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Lot
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LOTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Lot
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Lot
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLotNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.LOTNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Lot
	 * @param num grouping order
	 */
	public void setLotNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.LOTNO.toString(), num);
	}

	/**
	 * Fetch Lot info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLotNoCollect()
	{
		setCollect(_Prefix + SortingPlan.LOTNO.toString(), "");
	}

	/**
	 * Fetch Lot info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLotNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.LOTNO.toString(), compcode);
	}

	/**
	 *Set search value for  Plan
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANINFORMATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.PLANINFORMATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanInformationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.PLANINFORMATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan
	 * @param num grouping order
	 */
	public void setPlanInformationGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.PLANINFORMATION.toString(), num);
	}

	/**
	 * Fetch Plan info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanInformationCollect()
	{
		setCollect(_Prefix + SortingPlan.PLANINFORMATION.toString(), "");
	}

	/**
	 * Fetch Plan info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanInformationCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.PLANINFORMATION.toString(), compcode);
	}

	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BATCHNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.BATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Batch No (Schedule No)
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBatchNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.BATCHNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Batch No (Schedule No)
	 * @param num grouping order
	 */
	public void setBatchNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.BATCHNO.toString(), num);
	}

	/**
	 * Fetch Batch No (Schedule No) info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBatchNoCollect()
	{
		setCollect(_Prefix + SortingPlan.BATCHNO.toString(), "");
	}

	/**
	 * Fetch Batch No (Schedule No) info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBatchNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.BATCHNO.toString(), compcode);
	}

	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.WORKERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker code
	 * @param num grouping order
	 */
	public void setWorkerCodeGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.WORKERCODE.toString(), num);
	}

	/**
	 * Fetch Worker code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerCodeCollect()
	{
		setCollect(_Prefix + SortingPlan.WORKERCODE.toString(), "");
	}

	/**
	 * Fetch Worker code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerCodeCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.WORKERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.WORKERNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker name
	 * @param num grouping order
	 */
	public void setWorkerNameGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.WORKERNAME.toString(), num);
	}

	/**
	 * Fetch Worker name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerNameCollect()
	{
		setCollect(_Prefix + SortingPlan.WORKERNAME.toString(), "");
	}

	/**
	 * Fetch Worker name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerNameCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.WORKERNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Terminal No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TERMINALNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Terminal No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Terminal No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTerminalNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.TERMINALNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Terminal No
	 * @param num grouping order
	 */
	public void setTerminalNoGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.TERMINALNO.toString(), num);
	}

	/**
	 * Fetch Terminal No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTerminalNoCollect()
	{
		setCollect(_Prefix + SortingPlan.TERMINALNO.toString(), "");
	}

	/**
	 * Fetch Terminal No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTerminalNoCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.TERMINALNO.toString(), compcode);
	}

	/**
	 *Set search value for  Registration type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTKIND.toString(), arg);
	}
	/**
	 *Set search value for  Registration type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTKIND.toString(), arg);
	}
	/**
	 *Set search value for  Registration type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registration type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registration type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.REGISTKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order Registration type
	 * @param num grouping order
	 */
	public void setRegistKindGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.REGISTKIND.toString(), num);
	}

	/**
	 * Fetch Registration type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistKindCollect()
	{
		setCollect(_Prefix + SortingPlan.REGISTKIND.toString(), "");
	}

	/**
	 * Fetch Registration type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistKindCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.REGISTKIND.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + SortingPlan.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTPNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + SortingPlan.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + SortingPlan.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: SortingPlanSearchKey.java,v 1.3 2006/11/13 04:33:00 suresh Exp $" ;
	}
}

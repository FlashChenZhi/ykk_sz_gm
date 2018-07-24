//$Id: RetrievalPlanSearchKey.java,v 1.3 2006/11/13 04:33:03 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

/**
 * This is the search key class for RETRIEVALPLAN use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:03 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class RetrievalPlanSearchKey
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
	public RetrievalPlanSearchKey()
	{
		super(new RetrievalPlan()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Retrieval Plan Unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval Plan Unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval Plan Unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval Plan Unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Retrieval Plan Unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRetrievalPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Retrieval Plan Unique key
	 * @param num grouping order
	 */
	public void setRetrievalPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Retrieval Plan Unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRetrievalPlanUkeyCollect()
	{
		setCollect(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Retrieval Plan Unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRetrievalPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.STATUSFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Status flag
	 * @param num grouping order
	 */
	public void setStatusFlagGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.STATUSFLAG.toString(), num);
	}

	/**
	 * Fetch Status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusFlagCollect()
	{
		setCollect(_Prefix + RetrievalPlan.STATUSFLAG.toString(), "");
	}

	/**
	 * Fetch Status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusFlagCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.STATUSFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Retrieval Plan Date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval Plan Date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval Plan Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval Plan Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Retrieval Plan Date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PLANDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Retrieval Plan Date
	 * @param num grouping order
	 */
	public void setPlanDateGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.PLANDATE.toString(), num);
	}

	/**
	 * Fetch Retrieval Plan Date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanDateCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PLANDATE.toString(), "");
	}

	/**
	 * Fetch Retrieval Plan Date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanDateCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PLANDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor name
	 * @param num grouping order
	 */
	public void setConsignorNameGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), num);
	}

	/**
	 * Fetch Consignor name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorNameCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), "");
	}

	/**
	 * Fetch Consignor name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorNameCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer code
	 * @param num grouping order
	 */
	public void setCustomerCodeGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), num);
	}

	/**
	 * Fetch Customer code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerCodeCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), "");
	}

	/**
	 * Fetch Customer code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerCodeCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Customer name1
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name1
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name1
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer name1
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer name1
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer name1
	 * @param num grouping order
	 */
	public void setCustomerName1Group(int num) 
	{
		setGroup(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), num);
	}

	/**
	 * Fetch Customer name1 info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerName1Collect()
	{
		setCollect(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), "");
	}

	/**
	 * Fetch Customer name1 info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerName1Collect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping ticket no
	 * @param num grouping order
	 */
	public void setShippingTicketNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), num);
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingTicketNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), "");
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping line no
	 * @param num grouping order
	 */
	public void setShippingLineNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), num);
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingLineNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), "");
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingLineNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMCODE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.ITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Item code
	 * @param num grouping order
	 */
	public void setItemCodeGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.ITEMCODE.toString(), num);
	}

	/**
	 * Fetch Item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemCodeCollect()
	{
		setCollect(_Prefix + RetrievalPlan.ITEMCODE.toString(), "");
	}

	/**
	 * Fetch Item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.ITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.ITEMNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Item name
	 * @param num grouping order
	 */
	public void setItemName1Group(int num) 
	{
		setGroup(_Prefix + RetrievalPlan.ITEMNAME1.toString(), num);
	}

	/**
	 * Fetch Item name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemName1Collect()
	{
		setCollect(_Prefix + RetrievalPlan.ITEMNAME1.toString(), "");
	}

	/**
	 * Fetch Item name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemName1Collect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.ITEMNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Plan qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PLANQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan qty
	 * @param num grouping order
	 */
	public void setPlanQtyGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.PLANQTY.toString(), num);
	}

	/**
	 * Fetch Plan qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanQtyCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PLANQTY.toString(), "");
	}

	/**
	 * Fetch Plan qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanQtyCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PLANQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RESULTQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Result qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.RESULTQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Result qty
	 * @param num grouping order
	 */
	public void setResultQtyGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.RESULTQTY.toString(), num);
	}

	/**
	 * Fetch Result qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultQtyCollect()
	{
		setCollect(_Prefix + RetrievalPlan.RESULTQTY.toString(), "");
	}

	/**
	 * Fetch Result qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultQtyCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.RESULTQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shortage qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShortageCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Shortage qty
	 * @param num grouping order
	 */
	public void setShortageCntGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), num);
	}

	/**
	 * Fetch Shortage qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShortageCntCollect()
	{
		setCollect(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), "");
	}

	/**
	 * Fetch Shortage qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShortageCntCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), compcode);
	}

	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Entering case qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Entering case qty
	 * @param num grouping order
	 */
	public void setEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch Entering case qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEnteringQtyCollect()
	{
		setCollect(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch Entering case qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bundle qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Bundle qty
	 * @param num grouping order
	 */
	public void setBundleEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch Bundle qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleEnteringQtyCollect()
	{
		setCollect(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch Bundle qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case/Piece flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCasePieceFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Case/Piece flag
	 * @param num grouping order
	 */
	public void setCasePieceFlagGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), num);
	}

	/**
	 * Fetch Case/Piece flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCasePieceFlagCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), "");
	}

	/**
	 * Fetch Case/Piece flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCasePieceFlagCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITF.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.ITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case ITF
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItfOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.ITF.toString(), num, bool);
	}

	/**
	 * Set grouping order Case ITF
	 * @param num grouping order
	 */
	public void setItfGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.ITF.toString(), num);
	}

	/**
	 * Fetch Case ITF info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItfCollect()
	{
		setCollect(_Prefix + RetrievalPlan.ITF.toString(), "");
	}

	/**
	 * Fetch Case ITF info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItfCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.ITF.toString(), compcode);
	}

	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEITF.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.BUNDLEITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bundle ITF
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleItfOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.BUNDLEITF.toString(), num, bool);
	}

	/**
	 * Set grouping order Bundle ITF
	 * @param num grouping order
	 */
	public void setBundleItfGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.BUNDLEITF.toString(), num);
	}

	/**
	 * Fetch Bundle ITF info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleItfCollect()
	{
		setCollect(_Prefix + RetrievalPlan.BUNDLEITF.toString(), "");
	}

	/**
	 * Fetch Bundle ITF info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleItfCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.BUNDLEITF.toString(), compcode);
	}

	/**
	 *Set search value for  Piece Retrieval Area
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceArea(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEAREA.toString(), arg);
	}
	/**
	 *Set search value for  Piece Retrieval Area
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceArea(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEAREA.toString(), arg);
	}
	/**
	 *Set search value for  Piece Retrieval Area
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceArea(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEAREA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Piece Retrieval Area
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceArea(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEAREA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Piece Retrieval Area
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPieceAreaOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PIECEAREA.toString(), num, bool);
	}

	/**
	 * Set grouping order Piece Retrieval Area
	 * @param num grouping order
	 */
	public void setPieceAreaGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.PIECEAREA.toString(), num);
	}

	/**
	 * Fetch Piece Retrieval Area info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPieceAreaCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PIECEAREA.toString(), "");
	}

	/**
	 * Fetch Piece Retrieval Area info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPieceAreaCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PIECEAREA.toString(), compcode);
	}

	/**
	 *Set search value for  Case Retrieval Area
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseArea(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEAREA.toString(), arg);
	}
	/**
	 *Set search value for  Case Retrieval Area
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseArea(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEAREA.toString(), arg);
	}
	/**
	 *Set search value for  Case Retrieval Area
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseArea(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEAREA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case Retrieval Area
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseArea(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEAREA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case Retrieval Area
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCaseAreaOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CASEAREA.toString(), num, bool);
	}

	/**
	 * Set grouping order Case Retrieval Area
	 * @param num grouping order
	 */
	public void setCaseAreaGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CASEAREA.toString(), num);
	}

	/**
	 * Fetch Case Retrieval Area info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCaseAreaCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CASEAREA.toString(), "");
	}

	/**
	 * Fetch Case Retrieval Area info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCaseAreaCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CASEAREA.toString(), compcode);
	}

	/**
	 *Set search value for  Piece Retrieval Zone
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceZone(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEZONE.toString(), arg);
	}
	/**
	 *Set search value for  Piece Retrieval Zone
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceZone(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEZONE.toString(), arg);
	}
	/**
	 *Set search value for  Piece Retrieval Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceZone(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEZONE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Piece Retrieval Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceZone(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEZONE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Piece Retrieval Zone
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPieceZoneOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PIECEZONE.toString(), num, bool);
	}

	/**
	 * Set grouping order Piece Retrieval Zone
	 * @param num grouping order
	 */
	public void setPieceZoneGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.PIECEZONE.toString(), num);
	}

	/**
	 * Fetch Piece Retrieval Zone info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPieceZoneCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PIECEZONE.toString(), "");
	}

	/**
	 * Fetch Piece Retrieval Zone info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPieceZoneCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PIECEZONE.toString(), compcode);
	}

	/**
	 *Set search value for  Case Retrieval Zone
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseZone(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEZONE.toString(), arg);
	}
	/**
	 *Set search value for  Case Retrieval Zone
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseZone(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEZONE.toString(), arg);
	}
	/**
	 *Set search value for  Case Retrieval Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseZone(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEZONE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case Retrieval Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseZone(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEZONE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case Retrieval Zone
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCaseZoneOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CASEZONE.toString(), num, bool);
	}

	/**
	 * Set grouping order Case Retrieval Zone
	 * @param num grouping order
	 */
	public void setCaseZoneGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CASEZONE.toString(), num);
	}

	/**
	 * Fetch Case Retrieval Zone info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCaseZoneCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CASEZONE.toString(), "");
	}

	/**
	 * Fetch Case Retrieval Zone info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCaseZoneCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CASEZONE.toString(), compcode);
	}

	/**
	 *Set search value for  Piece Retrieval Location
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Piece Retrieval Location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Piece Retrieval Location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECELOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Piece Retrieval Location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECELOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Piece Retrieval Location
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPieceLocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PIECELOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Piece Retrieval Location
	 * @param num grouping order
	 */
	public void setPieceLocationGroup(int num) 
	{
		setGroup(_Prefix + RetrievalPlan.PIECELOCATION.toString(), num);
	}

	/**
	 * Fetch Piece Retrieval Location info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPieceLocationCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PIECELOCATION.toString(), "");
	}

	/**
	 * Fetch Piece Retrieval Location info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPieceLocationCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PIECELOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  Case Retrieval Location
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Case Retrieval Location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASELOCATION.toString(), arg);
	}
	/**
	 *Set search value for  Case Retrieval Location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASELOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case Retrieval Location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASELOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case Retrieval Location
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCaseLocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CASELOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Case Retrieval Location
	 * @param num grouping order
	 */
	public void setCaseLocationGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CASELOCATION.toString(), num);
	}

	/**
	 * Fetch Case Retrieval Location info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCaseLocationCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CASELOCATION.toString(), "");
	}

	/**
	 * Fetch Case Retrieval Location info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCaseLocationCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CASELOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  Piece Order No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceOrderNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Piece Order No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceOrderNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Piece Order No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceOrderNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Piece Order No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceOrderNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Piece Order No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPieceOrderNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Piece Order No
	 * @param num grouping order
	 */
	public void setPieceOrderNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), num);
	}

	/**
	 * Fetch Piece Order No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPieceOrderNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), "");
	}

	/**
	 * Fetch Piece Order No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPieceOrderNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), compcode);
	}

	/**
	 *Set search value for  Case Order No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseOrderNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Case Order No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseOrderNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Case Order No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseOrderNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEORDERNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case Order No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseOrderNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEORDERNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case Order No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCaseOrderNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.CASEORDERNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Case Order No
	 * @param num grouping order
	 */
	public void setCaseOrderNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.CASEORDERNO.toString(), num);
	}

	/**
	 * Fetch Case Order No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCaseOrderNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.CASEORDERNO.toString(), "");
	}

	/**
	 * Fetch Case Order No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCaseOrderNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.CASEORDERNO.toString(), compcode);
	}

	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.USEBYDATE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.USEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Use By Date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setUseByDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.USEBYDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Use By Date
	 * @param num grouping order
	 */
	public void setUseByDateGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.USEBYDATE.toString(), num);
	}

	/**
	 * Fetch Use By Date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setUseByDateCollect()
	{
		setCollect(_Prefix + RetrievalPlan.USEBYDATE.toString(), "");
	}

	/**
	 * Fetch Use By Date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setUseByDateCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.USEBYDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Lot
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LOTNO.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.LOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Lot
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLotNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.LOTNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Lot
	 * @param num grouping order
	 */
	public void setLotNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.LOTNO.toString(), num);
	}

	/**
	 * Fetch Lot info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLotNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.LOTNO.toString(), "");
	}

	/**
	 * Fetch Lot info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLotNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.LOTNO.toString(), compcode);
	}

	/**
	 *Set search value for  Plan
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanInformationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan
	 * @param num grouping order
	 */
	public void setPlanInformationGroup(int num) 
	{
		setGroup(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), num);
	}

	/**
	 * Fetch Plan info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanInformationCollect()
	{
		setCollect(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), "");
	}

	/**
	 * Fetch Plan info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanInformationCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), compcode);
	}

	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BATCHNO.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.BATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Batch No (Schedule No)
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBatchNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.BATCHNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Batch No (Schedule No)
	 * @param num grouping order
	 */
	public void setBatchNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.BATCHNO.toString(), num);
	}

	/**
	 * Fetch Batch No (Schedule No) info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBatchNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.BATCHNO.toString(), "");
	}

	/**
	 * Fetch Batch No (Schedule No) info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBatchNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.BATCHNO.toString(), compcode);
	}

	/**
	 *Set search value for  Host collection batch key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostCollectBatchno(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Host collection batch key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostCollectBatchno(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Host collection batch key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostCollectBatchno(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Host collection batch key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostCollectBatchno(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Host collection batch key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHostCollectBatchnoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Host collection batch key
	 * @param num grouping order
	 */
	public void setHostCollectBatchnoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), num);
	}

	/**
	 * Fetch Host collection batch key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHostCollectBatchnoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), "");
	}

	/**
	 * Fetch Host collection batch key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHostCollectBatchnoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), compcode);
	}

	/**
	 *Set search value for  Schedule flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSchFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SCHFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Schedule flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSchFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SCHFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Schedule flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSchFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SCHFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Schedule flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSchFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SCHFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Schedule flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSchFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.SCHFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Schedule flag
	 * @param num grouping order
	 */
	public void setSchFlagGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.SCHFLAG.toString(), num);
	}

	/**
	 * Fetch Schedule flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSchFlagCollect()
	{
		setCollect(_Prefix + RetrievalPlan.SCHFLAG.toString(), "");
	}

	/**
	 * Fetch Schedule flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSchFlagCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.SCHFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.WORKERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker code
	 * @param num grouping order
	 */
	public void setWorkerCodeGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.WORKERCODE.toString(), num);
	}

	/**
	 * Fetch Worker code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerCodeCollect()
	{
		setCollect(_Prefix + RetrievalPlan.WORKERCODE.toString(), "");
	}

	/**
	 * Fetch Worker code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerCodeCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.WORKERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERNAME.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.WORKERNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker name
	 * @param num grouping order
	 */
	public void setWorkerNameGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.WORKERNAME.toString(), num);
	}

	/**
	 * Fetch Worker name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerNameCollect()
	{
		setCollect(_Prefix + RetrievalPlan.WORKERNAME.toString(), "");
	}

	/**
	 * Fetch Worker name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerNameCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.WORKERNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.TERMINALNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Terminal no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTerminalNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.TERMINALNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Terminal no
	 * @param num grouping order
	 */
	public void setTerminalNoGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.TERMINALNO.toString(), num);
	}

	/**
	 * Fetch Terminal no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTerminalNoCollect()
	{
		setCollect(_Prefix + RetrievalPlan.TERMINALNO.toString(), "");
	}

	/**
	 * Fetch Terminal no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTerminalNoCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.TERMINALNO.toString(), compcode);
	}

	/**
	 *Set search value for  Registration type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTKIND.toString(), arg);
	}
	/**
	 *Set search value for  Registration type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTKIND.toString(), arg);
	}
	/**
	 *Set search value for  Registration type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTKIND.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.REGISTKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registration type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.REGISTKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order Registration type
	 * @param num grouping order
	 */
	public void setRegistKindGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.REGISTKIND.toString(), num);
	}

	/**
	 * Fetch Registration type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistKindCollect()
	{
		setCollect(_Prefix + RetrievalPlan.REGISTKIND.toString(), "");
	}

	/**
	 * Fetch Registration type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistKindCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.REGISTKIND.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + RetrievalPlan.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + RetrievalPlan.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: RetrievalPlanSearchKey.java,v 1.3 2006/11/13 04:33:03 suresh Exp $" ;
	}
}

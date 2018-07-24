//$Id: ShippingPlanAlterKey.java,v 1.2 2006/11/09 07:50:43 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.ShippingPlan;

/**
 * Update key class for SHIPPINGPLAN use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:50:43 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ShippingPlanAlterKey
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
	public ShippingPlanAlterKey()
	{
		super(new ShippingPlan()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Shipping Plan Unique key
	 * @param arg Shipping Plan Unique key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGPLANUKEY.toString(), arg);
	}
	/**
	 * Set search value for Shipping Plan Unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Plan Unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Plan Unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Plan Unique key
	 * @param arg Update value for Shipping Plan Unique key
	 */
	public void updateShippingPlanUkey(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.SHIPPINGPLANUKEY.toString(), arg);
	}

	/**
	 * Set search value for Status flag
	 * @param arg Status flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 * Set search value for Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Status flag
	 * @param arg Update value for Status flag
	 */
	public void updateStatusFlag(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.STATUSFLAG.toString(), arg);
	}

	/**
	 * Set search value for Shipping Plan date
	 * @param arg Shipping Plan date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANDATE.toString(), arg);
	}
	/**
	 * Set search value for Shipping Plan date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Plan date
	 * @param arg Update value for Shipping Plan date
	 */
	public void updatePlanDate(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.PLANDATE.toString(), arg);
	}

	/**
	 * Set search value for Consignor Code
	 * @param arg Consignor Code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for Consignor Code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor Code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Consignor Code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Consignor Code
	 * @param arg Update value for Consignor Code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for Consignor Name
	 * @param arg Consignor Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 * Set search value for Consignor Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Consignor Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Consignor Name
	 * @param arg Update value for Consignor Name
	 */
	public void updateConsignorName(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.CONSIGNORNAME.toString(), arg);
	}

	/**
	 * Set search value for Customer Code
	 * @param arg Customer Code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 * Set search value for Customer Code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer Code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer Code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Customer Code
	 * @param arg Update value for Customer Code
	 */
	public void updateCustomerCode(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.CUSTOMERCODE.toString(), arg);
	}

	/**
	 * Set search value for Customer Name
	 * @param arg Customer Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Customer Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CUSTOMERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Customer Name
	 * @param arg Update value for Customer Name
	 */
	public void updateCustomerName1(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.CUSTOMERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Shipping Ticket no
	 * @param arg Shipping Ticket no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 * Set search value for Shipping Ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Ticket no
	 * @param arg Update value for Shipping Ticket no
	 */
	public void updateShippingTicketNo(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.SHIPPINGTICKETNO.toString(), arg);
	}

	/**
	 * Set search value for Shipping Line no
	 * @param arg Shipping Line no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 * Set search value for Shipping Line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGLINENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHIPPINGLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Line no
	 * @param arg Update value for Shipping Line no
	 */
	public void updateShippingLineNo(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.SHIPPINGLINENO.toString(), arg);
	}

	/**
	 * Set search value for Item Code
	 * @param arg Item Code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for Item Code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item Code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Item Code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Item Code
	 * @param arg Update value for Item Code
	 */
	public void updateItemCode(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.ITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for Item Name
	 * @param arg Item Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 * Set search value for Item Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Item Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Item Name
	 * @param arg Update value for Item Name
	 */
	public void updateItemName1(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.ITEMNAME1.toString(), arg);
	}

	/**
	 * Set search value for Shipping Plan qty
	 * @param arg Shipping Plan qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANQTY.toString(), arg);
	}
	/**
	 * Set search value for Shipping Plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Plan qty
	 * @param arg Update value for Shipping Plan qty
	 */
	public void updatePlanQty(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.PLANQTY.toString(), arg);
	}

	/**
	 * Set search value for Shipping Result qty
	 * @param arg Shipping Result qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.RESULTQTY.toString(), arg);
	}
	/**
	 * Set search value for Shipping Result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.RESULTQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Result qty
	 * @param arg Update value for Shipping Result qty
	 */
	public void updateResultQty(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.RESULTQTY.toString(), arg);
	}

	/**
	 * Set search value for Shipping Shortage qty
	 * @param arg Shipping Shortage qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 * Set search value for Shipping Shortage qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shipping Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHORTAGECNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SHORTAGECNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping Shortage qty
	 * @param arg Update value for Shipping Shortage qty
	 */
	public void updateShortageCnt(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.SHORTAGECNT.toString(), arg);
	}

	/**
	 * Set search value for Entering Case qty
	 * @param arg Entering Case qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Entering Case qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering Case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Entering Case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Entering Case qty
	 * @param arg Update value for Entering Case qty
	 */
	public void updateEnteringQty(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.ENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Bundle qty
	 * @param arg Bundle qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Bundle qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BUNDLEENTERINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bundle qty
	 * @param arg Update value for Bundle qty
	 */
	public void updateBundleEnteringQty(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.BUNDLEENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Case/Piece flag
	 * @param arg Case/Piece flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Case/Piece flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.CASEPIECEFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.CASEPIECEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case/Piece flag
	 * @param arg Update value for Case/Piece flag
	 */
	public void updateCasePieceFlag(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.CASEPIECEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Case ITF
	 * @param arg Case ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITF.toString(), arg);
	}
	/**
	 * Set search value for Case ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ITF.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.ITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case ITF
	 * @param arg Update value for Case ITF
	 */
	public void updateItf(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.ITF.toString(), arg);
	}

	/**
	 * Set search value for Bundle ITF
	 * @param arg Bundle ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 * Set search value for Bundle ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BUNDLEITF.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.BUNDLEITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bundle ITF
	 * @param arg Update value for Bundle ITF
	 */
	public void updateBundleItf(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.BUNDLEITF.toString(), arg);
	}

	/**
	 * Set search value for Ordering Date
	 * @param arg Ordering Date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ORDERINGDATE.toString(), arg);
	}
	/**
	 * Set search value for Ordering Date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ORDERINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  Ordering Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ORDERINGDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Ordering Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.ORDERINGDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Ordering Date
	 * @param arg Update value for Ordering Date
	 */
	public void updateOrderingDate(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.ORDERINGDATE.toString(), arg);
	}

	/**
	 * Set search value for TC/DC flag
	 * @param arg TC/DC flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TCDCFLAG.toString(), arg);
	}
	/**
	 * Set search value for TC/DC flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TCDCFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TCDCFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for TC/DC flag
	 * @param arg Update value for TC/DC flag
	 */
	public void updateTcdcFlag(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.TCDCFLAG.toString(), arg);
	}

	/**
	 * Set search value for Supplier code
	 * @param arg Supplier code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SUPPLIERCODE.toString(), arg);
	}
	/**
	 * Set search value for Supplier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SUPPLIERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.SUPPLIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Supplier code
	 * @param arg Update value for Supplier code
	 */
	public void updateSupplierCode(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.SUPPLIERCODE.toString(), arg);
	}

	/**
	 * Set search value for Supplier name
	 * @param arg Supplier name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Supplier name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.SUPPLIERNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.SUPPLIERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Supplier name
	 * @param arg Update value for Supplier name
	 */
	public void updateSupplierName1(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.SUPPLIERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Receiving Ticket No
	 * @param arg Receiving Ticket No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 * Set search value for Receiving Ticket No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving Ticket No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving Ticket No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Receiving Ticket No
	 * @param arg Update value for Receiving Ticket No
	 */
	public void updateInstockTicketNo(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.INSTOCKTICKETNO.toString(), arg);
	}

	/**
	 * Set search value for Receiving Line no
	 * @param arg Receiving Line no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKLINENO.toString(), arg);
	}
	/**
	 * Set search value for Receiving Line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving Line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKLINENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving Line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.INSTOCKLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Receiving Line no
	 * @param arg Update value for Receiving Line no
	 */
	public void updateInstockLineNo(int arg)
	{
		setUpdValue(_Prefix + ShippingPlan.INSTOCKLINENO.toString(), arg);
	}

	/**
	 * Set search value for Use By Date
	 * @param arg Use By Date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.USEBYDATE.toString(), arg);
	}
	/**
	 * Set search value for Use By Date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.USEBYDATE.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.USEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Use By Date
	 * @param arg Update value for Use By Date
	 */
	public void updateUseByDate(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.USEBYDATE.toString(), arg);
	}

	/**
	 * Set search value for Lot No
	 * @param arg Lot No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LOTNO.toString(), arg);
	}
	/**
	 * Set search value for Lot No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LOTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Lot No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Lot No
	 * @param arg Update value for Lot No
	 */
	public void updateLotNo(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.LOTNO.toString(), arg);
	}

	/**
	 * Set search value for Plan detail comments
	 * @param arg Plan detail comments<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 * Set search value for Plan detail comments
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan detail comments
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANINFORMATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan detail comments
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.PLANINFORMATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan detail comments
	 * @param arg Update value for Plan detail comments
	 */
	public void updatePlanInformation(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.PLANINFORMATION.toString(), arg);
	}

	/**
	 * Set search value for Batch No (Schedule No)
	 * @param arg Batch No (Schedule No)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BATCHNO.toString(), arg);
	}
	/**
	 * Set search value for Batch No (Schedule No)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.BATCHNO.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.BATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Batch No (Schedule No)
	 * @param arg Update value for Batch No (Schedule No)
	 */
	public void updateBatchNo(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.BATCHNO.toString(), arg);
	}

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.WORKERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Worker name
	 * @param arg Worker name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.WORKERNAME.toString(), arg);
	}
	/**
	 * Set search value for Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.WORKERNAME.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker name
	 * @param arg Update value for Worker name
	 */
	public void updateWorkerName(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.WORKERNAME.toString(), arg);
	}

	/**
	 * Set search value for Terminal no
	 * @param arg Terminal no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TERMINALNO.toString(), arg);
	}
	/**
	 * Set search value for Terminal no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.TERMINALNO.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Terminal no
	 * @param arg Update value for Terminal no
	 */
	public void updateTerminalNo(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.TERMINALNO.toString(), arg);
	}

	/**
	 * Set search value for Registration type
	 * @param arg Registration type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTKIND.toString(), arg);
	}
	/**
	 * Set search value for Registration type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTKIND.toString(), arg);
	}
	/**
	 *Set search value for  Registration type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTKIND.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.REGISTKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registration type
	 * @param arg Update value for Registration type
	 */
	public void updateRegistKind(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.REGISTKIND.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + ShippingPlan.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + ShippingPlan.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ShippingPlan.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + ShippingPlan.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + ShippingPlan.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: ShippingPlanAlterKey.java,v 1.2 2006/11/09 07:50:43 suresh Exp $" ;
	}
}

//$Id: RetrievalPlanAlterKey.java,v 1.2 2006/11/09 07:51:34 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

/**
 * Update key class for RETRIEVALPLAN use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:51:34 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class RetrievalPlanAlterKey
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
	public RetrievalPlanAlterKey()
	{
		super(new RetrievalPlan()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Retrieval Plan Unique key
	 * @param arg Retrieval Plan Unique key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), arg);
	}
	/**
	 * Set search value for Retrieval Plan Unique key
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
	 * Set update value for Retrieval Plan Unique key
	 * @param arg Update value for Retrieval Plan Unique key
	 */
	public void updateRetrievalPlanUkey(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.RETRIEVALPLANUKEY.toString(), arg);
	}

	/**
	 * Set search value for Status flag
	 * @param arg Status flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.STATUSFLAG.toString(), arg);
	}
	/**
	 * Set search value for Status flag
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
	 * Set update value for Status flag
	 * @param arg Update value for Status flag
	 */
	public void updateStatusFlag(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.STATUSFLAG.toString(), arg);
	}

	/**
	 * Set search value for Retrieval Plan Date
	 * @param arg Retrieval Plan Date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANDATE.toString(), arg);
	}
	/**
	 * Set search value for Retrieval Plan Date
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
	 * Set update value for Retrieval Plan Date
	 * @param arg Update value for Retrieval Plan Date
	 */
	public void updatePlanDate(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PLANDATE.toString(), arg);
	}

	/**
	 * Set search value for Consignor code
	 * @param arg Consignor code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for Consignor code
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
	 * Set update value for Consignor code
	 * @param arg Update value for Consignor code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for Consignor name
	 * @param arg Consignor name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), arg);
	}
	/**
	 * Set search value for Consignor name
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
	 * Set update value for Consignor name
	 * @param arg Update value for Consignor name
	 */
	public void updateConsignorName(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CONSIGNORNAME.toString(), arg);
	}

	/**
	 * Set search value for Customer code
	 * @param arg Customer code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), arg);
	}
	/**
	 * Set search value for Customer code
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
	 * Set update value for Customer code
	 * @param arg Update value for Customer code
	 */
	public void updateCustomerCode(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CUSTOMERCODE.toString(), arg);
	}

	/**
	 * Set search value for Customer name1
	 * @param arg Customer name1<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Customer name1
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
	 * Set update value for Customer name1
	 * @param arg Update value for Customer name1
	 */
	public void updateCustomerName1(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CUSTOMERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Shipping ticket no
	 * @param arg Shipping ticket no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 * Set search value for Shipping ticket no
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
	 * Set update value for Shipping ticket no
	 * @param arg Update value for Shipping ticket no
	 */
	public void updateShippingTicketNo(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.SHIPPINGTICKETNO.toString(), arg);
	}

	/**
	 * Set search value for Shipping line no
	 * @param arg Shipping line no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 * Set search value for Shipping line no
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
	 * Set update value for Shipping line no
	 * @param arg Update value for Shipping line no
	 */
	public void updateShippingLineNo(int arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.SHIPPINGLINENO.toString(), arg);
	}

	/**
	 * Set search value for Item code
	 * @param arg Item code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for Item code
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
	 * Set update value for Item code
	 * @param arg Update value for Item code
	 */
	public void updateItemCode(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.ITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for Item name
	 * @param arg Item name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITEMNAME1.toString(), arg);
	}
	/**
	 * Set search value for Item name
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
	 * Set update value for Item name
	 * @param arg Update value for Item name
	 */
	public void updateItemName1(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.ITEMNAME1.toString(), arg);
	}

	/**
	 * Set search value for Plan qty
	 * @param arg Plan qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANQTY.toString(), arg);
	}
	/**
	 * Set search value for Plan qty
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
	 * Set update value for Plan qty
	 * @param arg Update value for Plan qty
	 */
	public void updatePlanQty(int arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PLANQTY.toString(), arg);
	}

	/**
	 * Set search value for Result qty
	 * @param arg Result qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.RESULTQTY.toString(), arg);
	}
	/**
	 * Set search value for Result qty
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
	 * Set update value for Result qty
	 * @param arg Update value for Result qty
	 */
	public void updateResultQty(int arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.RESULTQTY.toString(), arg);
	}

	/**
	 * Set search value for Shortage qty
	 * @param arg Shortage qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), arg);
	}
	/**
	 * Set search value for Shortage qty
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
	 * Set update value for Shortage qty
	 * @param arg Update value for Shortage qty
	 */
	public void updateShortageCnt(int arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.SHORTAGECNT.toString(), arg);
	}

	/**
	 * Set search value for Entering case qty
	 * @param arg Entering case qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Entering case qty
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
	 * Set update value for Entering case qty
	 * @param arg Update value for Entering case qty
	 */
	public void updateEnteringQty(int arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.ENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Bundle qty
	 * @param arg Bundle qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Bundle qty
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
	 * Set update value for Bundle qty
	 * @param arg Update value for Bundle qty
	 */
	public void updateBundleEnteringQty(int arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.BUNDLEENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Case/Piece flag
	 * @param arg Case/Piece flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Case/Piece flag
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
	 * Set update value for Case/Piece flag
	 * @param arg Update value for Case/Piece flag
	 */
	public void updateCasePieceFlag(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CASEPIECEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Case ITF
	 * @param arg Case ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.ITF.toString(), arg);
	}
	/**
	 * Set search value for Case ITF
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
	 * Set update value for Case ITF
	 * @param arg Update value for Case ITF
	 */
	public void updateItf(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.ITF.toString(), arg);
	}

	/**
	 * Set search value for Bundle ITF
	 * @param arg Bundle ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BUNDLEITF.toString(), arg);
	}
	/**
	 * Set search value for Bundle ITF
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
	 * Set update value for Bundle ITF
	 * @param arg Update value for Bundle ITF
	 */
	public void updateBundleItf(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.BUNDLEITF.toString(), arg);
	}

	/**
	 * Set search value for Piece Retrieval Area
	 * @param arg Piece Retrieval Area<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceArea(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEAREA.toString(), arg);
	}
	/**
	 * Set search value for Piece Retrieval Area
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
	 * Set update value for Piece Retrieval Area
	 * @param arg Update value for Piece Retrieval Area
	 */
	public void updatePieceArea(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PIECEAREA.toString(), arg);
	}

	/**
	 * Set search value for Case Retrieval Area
	 * @param arg Case Retrieval Area<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseArea(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEAREA.toString(), arg);
	}
	/**
	 * Set search value for Case Retrieval Area
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
	 * Set update value for Case Retrieval Area
	 * @param arg Update value for Case Retrieval Area
	 */
	public void updateCaseArea(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CASEAREA.toString(), arg);
	}

	/**
	 * Set search value for Piece Retrieval Zone
	 * @param arg Piece Retrieval Zone<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceZone(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEZONE.toString(), arg);
	}
	/**
	 * Set search value for Piece Retrieval Zone
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
	 * Set update value for Piece Retrieval Zone
	 * @param arg Update value for Piece Retrieval Zone
	 */
	public void updatePieceZone(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PIECEZONE.toString(), arg);
	}

	/**
	 * Set search value for Case Retrieval Zone
	 * @param arg Case Retrieval Zone<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseZone(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEZONE.toString(), arg);
	}
	/**
	 * Set search value for Case Retrieval Zone
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
	 * Set update value for Case Retrieval Zone
	 * @param arg Update value for Case Retrieval Zone
	 */
	public void updateCaseZone(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CASEZONE.toString(), arg);
	}

	/**
	 * Set search value for Piece Retrieval Location
	 * @param arg Piece Retrieval Location<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECELOCATION.toString(), arg);
	}
	/**
	 * Set search value for Piece Retrieval Location
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
	 * Set update value for Piece Retrieval Location
	 * @param arg Update value for Piece Retrieval Location
	 */
	public void updatePieceLocation(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PIECELOCATION.toString(), arg);
	}

	/**
	 * Set search value for Case Retrieval Location
	 * @param arg Case Retrieval Location<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASELOCATION.toString(), arg);
	}
	/**
	 * Set search value for Case Retrieval Location
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
	 * Set update value for Case Retrieval Location
	 * @param arg Update value for Case Retrieval Location
	 */
	public void updateCaseLocation(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CASELOCATION.toString(), arg);
	}

	/**
	 * Set search value for Piece Order No
	 * @param arg Piece Order No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPieceOrderNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), arg);
	}
	/**
	 * Set search value for Piece Order No
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
	 * Set update value for Piece Order No
	 * @param arg Update value for Piece Order No
	 */
	public void updatePieceOrderNo(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PIECEORDERNO.toString(), arg);
	}

	/**
	 * Set search value for Case Order No
	 * @param arg Case Order No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCaseOrderNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.CASEORDERNO.toString(), arg);
	}
	/**
	 * Set search value for Case Order No
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
	 * Set update value for Case Order No
	 * @param arg Update value for Case Order No
	 */
	public void updateCaseOrderNo(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.CASEORDERNO.toString(), arg);
	}

	/**
	 * Set search value for Use By Date
	 * @param arg Use By Date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.USEBYDATE.toString(), arg);
	}
	/**
	 * Set search value for Use By Date
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
	 * Set update value for Use By Date
	 * @param arg Update value for Use By Date
	 */
	public void updateUseByDate(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.USEBYDATE.toString(), arg);
	}

	/**
	 * Set search value for Lot
	 * @param arg Lot<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LOTNO.toString(), arg);
	}
	/**
	 * Set search value for Lot
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
	 * Set update value for Lot
	 * @param arg Update value for Lot
	 */
	public void updateLotNo(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.LOTNO.toString(), arg);
	}

	/**
	 * Set search value for Plan
	 * @param arg Plan<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), arg);
	}
	/**
	 * Set search value for Plan
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
	 * Set update value for Plan
	 * @param arg Update value for Plan
	 */
	public void updatePlanInformation(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.PLANINFORMATION.toString(), arg);
	}

	/**
	 * Set search value for Batch No (Schedule No)
	 * @param arg Batch No (Schedule No)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.BATCHNO.toString(), arg);
	}
	/**
	 * Set search value for Batch No (Schedule No)
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
	 * Set update value for Batch No (Schedule No)
	 * @param arg Update value for Batch No (Schedule No)
	 */
	public void updateBatchNo(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.BATCHNO.toString(), arg);
	}

	/**
	 * Set search value for Host collection batch key
	 * @param arg Host collection batch key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostCollectBatchno(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), arg);
	}
	/**
	 * Set search value for Host collection batch key
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
	 * Set update value for Host collection batch key
	 * @param arg Update value for Host collection batch key
	 */
	public void updateHostCollectBatchno(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.HOSTCOLLECTBATCHNO.toString(), arg);
	}

	/**
	 * Set search value for Schedule flag
	 * @param arg Schedule flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSchFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.SCHFLAG.toString(), arg);
	}
	/**
	 * Set search value for Schedule flag
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
	 * Set update value for Schedule flag
	 * @param arg Update value for Schedule flag
	 */
	public void updateSchFlag(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.SCHFLAG.toString(), arg);
	}

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
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
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Worker name
	 * @param arg Worker name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.WORKERNAME.toString(), arg);
	}
	/**
	 * Set search value for Worker name
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
	 * Set update value for Worker name
	 * @param arg Update value for Worker name
	 */
	public void updateWorkerName(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.WORKERNAME.toString(), arg);
	}

	/**
	 * Set search value for Terminal no
	 * @param arg Terminal no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.TERMINALNO.toString(), arg);
	}
	/**
	 * Set search value for Terminal no
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
	 * Set update value for Terminal no
	 * @param arg Update value for Terminal no
	 */
	public void updateTerminalNo(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.TERMINALNO.toString(), arg);
	}

	/**
	 * Set search value for Registration type
	 * @param arg Registration type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistKind(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTKIND.toString(), arg);
	}
	/**
	 * Set search value for Registration type
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
	 * Set update value for Registration type
	 * @param arg Update value for Registration type
	 */
	public void updateRegistKind(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.REGISTKIND.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
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
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
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
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
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
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
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
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + RetrievalPlan.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: RetrievalPlanAlterKey.java,v 1.2 2006/11/09 07:51:34 suresh Exp $" ;
	}
}

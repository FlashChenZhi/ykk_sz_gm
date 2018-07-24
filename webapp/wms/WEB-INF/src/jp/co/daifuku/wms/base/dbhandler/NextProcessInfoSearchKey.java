//$Id: NextProcessInfoSearchKey.java,v 1.3 2006/11/13 04:33:05 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.NextProcessInfo;

/**
 * This is the search key class for NEXTPROC use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:05 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class NextProcessInfoSearchKey
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
	public NextProcessInfoSearchKey()
	{
		super(new NextProcessInfo()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Next process unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextProcUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Next process unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextProcUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Next process unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextProcUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Next process unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextProcUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Next process unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNextProcUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Next process unique key
	 * @param num grouping order
	 */
	public void setNextProcUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), num);
	}

	/**
	 * Fetch Next process unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNextProcUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), "");
	}

	/**
	 * Fetch Next process unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNextProcUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.NEXTPROCUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.PLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan unique key
	 * @param num grouping order
	 */
	public void setPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.PLANUKEY.toString(), num);
	}

	/**
	 * Fetch Plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.PLANUKEY.toString(), "");
	}

	/**
	 * Fetch Plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.PLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Receiving plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Receiving plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving plan unique key
	 * @param num grouping order
	 */
	public void setInstockPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Receiving plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockPlanUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Receiving plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.INSTOCKPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Storage plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Storage plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Storage plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Storage plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Storage plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStoragePlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Storage plan unique key
	 * @param num grouping order
	 */
	public void setStoragePlanUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Storage plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStoragePlanUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Storage plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStoragePlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.STORAGEPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Retrieval plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Retrieval plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRetrievalPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Retrieval plan unique key
	 * @param num grouping order
	 */
	public void setRetrievalPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Retrieval plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRetrievalPlanUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Retrieval plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRetrievalPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.RETRIEVALPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Sorting plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Sorting plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSortingPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Sorting plan unique key
	 * @param num grouping order
	 */
	public void setSortingPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Sorting plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSortingPlanUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Sorting plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSortingPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SORTINGPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Shipping plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Shipping plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping plan unique key
	 * @param num grouping order
	 */
	public void setShippingPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), num);
	}

	/**
	 * Fetch Shipping plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingPlanUkeyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), "");
	}

	/**
	 * Fetch Shipping plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPINGPLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkKind(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.WORKKIND.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkKind(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.WORKKIND.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkKind(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.WORKKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkKind(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.WORKKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.WORKKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order Work type
	 * @param num grouping order
	 */
	public void setWorkKindGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.WORKKIND.toString(), num);
	}

	/**
	 * Fetch Work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkKindCollect()
	{
		setCollect(_Prefix + NextProcessInfo.WORKKIND.toString(), "");
	}

	/**
	 * Fetch Work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkKindCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.WORKKIND.toString(), compcode);
	}

	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work plan qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.PLANQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Work plan qty
	 * @param num grouping order
	 */
	public void setPlanQtyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.PLANQTY.toString(), num);
	}

	/**
	 * Fetch Work plan qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanQtyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.PLANQTY.toString(), "");
	}

	/**
	 * Fetch Work plan qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanQtyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.PLANQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Work result qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RESULTQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work result qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.RESULTQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Work result qty
	 * @param num grouping order
	 */
	public void setResultQtyGroup(int num) 
	{
		setGroup(_Prefix + NextProcessInfo.RESULTQTY.toString(), num);
	}

	/**
	 * Fetch Work result qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultQtyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.RESULTQTY.toString(), "");
	}

	/**
	 * Fetch Work result qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultQtyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.RESULTQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Work shortage qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work shortage qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work shortage qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShortageQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Work shortage qty
	 * @param num grouping order
	 */
	public void setShortageQtyGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), num);
	}

	/**
	 * Fetch Work shortage qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShortageQtyCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), "");
	}

	/**
	 * Fetch Work shortage qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShortageQtyCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SHORTAGEQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Area no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.AREANO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.AREANO.toString(), num, bool);
	}

	/**
	 * Set grouping order Area no
	 * @param num grouping order
	 */
	public void setAreaNoGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.AREANO.toString(), num);
	}

	/**
	 * Fetch Area no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaNoCollect()
	{
		setCollect(_Prefix + NextProcessInfo.AREANO.toString(), "");
	}

	/**
	 * Fetch Area no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaNoCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.AREANO.toString(), compcode);
	}

	/**
	 *Set search value for  Location no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LOCATIONNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Location no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLocationNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.LOCATIONNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Location no
	 * @param num grouping order
	 */
	public void setLocationNoGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.LOCATIONNO.toString(), num);
	}

	/**
	 * Fetch Location no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLocationNoCollect()
	{
		setCollect(_Prefix + NextProcessInfo.LOCATIONNO.toString(), "");
	}

	/**
	 * Fetch Location no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLocationNoCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.LOCATIONNO.toString(), compcode);
	}

	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.STATUSFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Status flag
	 * @param num grouping order
	 */
	public void setStatusFlagGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.STATUSFLAG.toString(), num);
	}

	/**
	 * Fetch Status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusFlagCollect()
	{
		setCollect(_Prefix + NextProcessInfo.STATUSFLAG.toString(), "");
	}

	/**
	 * Fetch Status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusFlagCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.STATUSFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving plan date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Receiving plan date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Receiving plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstPlanDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving plan date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstPlanDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving plan date
	 * @param num grouping order
	 */
	public void setInstPlanDateGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), num);
	}

	/**
	 * Fetch Receiving plan date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstPlanDateCollect()
	{
		setCollect(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), "");
	}

	/**
	 * Fetch Receiving plan date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstPlanDateCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.INSTPLANDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Supplier code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSupplierCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Supplier code
	 * @param num grouping order
	 */
	public void setSupplierCodeGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), num);
	}

	/**
	 * Fetch Supplier code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSupplierCodeCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), "");
	}

	/**
	 * Fetch Supplier code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSupplierCodeCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SUPPLIERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving ticket no
	 * @param num grouping order
	 */
	public void setInstockTicketNoGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), num);
	}

	/**
	 * Fetch Receiving ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockTicketNoCollect()
	{
		setCollect(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), "");
	}

	/**
	 * Fetch Receiving ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.INSTOCKTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving line no
	 * @param num grouping order
	 */
	public void setInstockLineNoGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), num);
	}

	/**
	 * Fetch Receiving line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockLineNoCollect()
	{
		setCollect(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), "");
	}

	/**
	 * Fetch Receiving line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockLineNoCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.INSTOCKLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.ITEMCODE.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.ITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Item code
	 * @param num grouping order
	 */
	public void setItemCodeGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.ITEMCODE.toString(), num);
	}

	/**
	 * Fetch Item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemCodeCollect()
	{
		setCollect(_Prefix + NextProcessInfo.ITEMCODE.toString(), "");
	}

	/**
	 * Fetch Item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.ITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.TCDCFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.TCDCFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for TC/DC flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTcdcFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.TCDCFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order TC/DC flag
	 * @param num grouping order
	 */
	public void setTcdcFlagGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.TCDCFLAG.toString(), num);
	}

	/**
	 * Fetch TC/DC flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTcdcFlagCollect()
	{
		setCollect(_Prefix + NextProcessInfo.TCDCFLAG.toString(), "");
	}

	/**
	 * Fetch TC/DC flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTcdcFlagCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.TCDCFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping plan date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShipPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Shipping plan date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShipPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Shipping plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShipPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShipPlanDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping plan date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShipPlanDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping plan date
	 * @param num grouping order
	 */
	public void setShipPlanDateGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), num);
	}

	/**
	 * Fetch Shipping plan date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShipPlanDateCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), "");
	}

	/**
	 * Fetch Shipping plan date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShipPlanDateCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPLANDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer code
	 * @param num grouping order
	 */
	public void setCustomerCodeGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), num);
	}

	/**
	 * Fetch Customer code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerCodeCollect()
	{
		setCollect(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), "");
	}

	/**
	 * Fetch Customer code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerCodeCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.CUSTOMERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping ticket no
	 * @param num grouping order
	 */
	public void setShippingTicketNoGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), num);
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingTicketNoCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), "");
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPINGTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping line no
	 * @param num grouping order
	 */
	public void setShippingLineNoGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), num);
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingLineNoCollect()
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), "");
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingLineNoCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.SHIPPINGLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + NextProcessInfo.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + NextProcessInfo.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + NextProcessInfo.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: NextProcessInfoSearchKey.java,v 1.3 2006/11/13 04:33:05 suresh Exp $" ;
	}
}

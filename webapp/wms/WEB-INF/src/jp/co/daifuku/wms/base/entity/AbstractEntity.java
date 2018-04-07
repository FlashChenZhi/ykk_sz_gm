// $Id: AbstractEntity.java,v 1.2 2006/11/09 08:56:15 suresh Exp $
package jp.co.daifuku.wms.base.entity;

//#CM702722
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.FieldName;


//#CM702723
/**
 * A virtual class which is basic of the entity class to maintain the combination 
 * of the item name and the value. 
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2003/05/11</td><td nowrap>The person who created this file.</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/09 08:56:15 $
 * @author  S.Suzuki
 * @author  Last commit: $Author: suresh $
 */
public abstract class AbstractEntity
		extends Entity implements SystemDefine
{
	//#CM702724
	//------------------------------------------------------------
	//#CM702725
	// class variables (prefix '$')
	//#CM702726
	//------------------------------------------------------------
	//#CM702727
	//	private String	$classVar ;

	//#CM702728
	//------------------------------------------------------------
	//#CM702729
	// fields (upper case only)
	//#CM702730
	//------------------------------------------------------------
	//#CM702731
	/** Type definition (int) */

	public static final int DEFINE_TYPE_INT = Types.NUMERIC ;

	//#CM702732
	/** Type definition (String) */

	public static final int DEFINE_TYPE_STRING = Types.VARCHAR ;

	//#CM702733
	/** Type definition (Date) */

	public static final int DEFINE_TYPE_DATE = Types.TIMESTAMP ;

	//#CM702734
	//------------------------------------------------------------
	//#CM702735
	// properties (prefix 'p_')
	//#CM702736
	//------------------------------------------------------------
	//#CM702737
	/** Map which maintains item name (String) and value (Object) */

	private Map p_valueMap ;

	//#CM702738
	/** List which maintains array of item name (String) */

	private List p_columnList ;

	//#CM702739
	//------------------------------------------------------------
	//#CM702740
	// instance variables (prefix '_')
	//#CM702741
	//------------------------------------------------------------

	//#CM702742
	//------------------------------------------------------------
	//#CM702743
	// constructors
	//#CM702744
	//------------------------------------------------------------
	//#CM702745
	/**
	 * Generate the entity instance. 
	 */
	public AbstractEntity()
	{
		p_valueMap = new HashMap() ;
		p_columnList = new ArrayList() ;
	}

	//#CM702746
	//------------------------------------------------------------
	//#CM702747
	// public methods
	//#CM702748
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702749
	//------------------------------------------------------------

	//#CM702750
	//------------------------------------------------------------
	//#CM702751
	// accessor methods
	//#CM702752
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702753
	//------------------------------------------------------------
	//#CM702754
	/**
	 * Set the item in this entity instance. 
	 *
	 * @param columnName String :Item name
	 * @param value Object :Value of item
	 */
	public void setValue(FieldName columnName, Object value)
	{
		//#CM702755
		// Reserved. Do not comment and do not erase it. 2005/09/15 okamura 
		//#CM702756
		// There is a possibility of it is necessary to try to getTimestamp() at time of the date. 
		Map valueMap = getValueMap() ;
		valueMap.put(columnName, value) ;
	}

	//#CM702757
	/**
	 * Acquire Value of item. 
	 *
	 * @param columnName String: Acquired Item name
	 * @return Object :Value of item
	 * <br>"" (empty String) is returned when Value of item is null. 
	 */
	public Object getValue(FieldName columnName)
	{
		return getValue(columnName, "") ;
	}

	//#CM702758
	/**
	 * The initial value when the value unsets it is specified and Acquire Value of item. 
	 *
	 * @param columnName String: Acquired Item name
	 * @param defaultValue Object :Initial value returned when value is null
	 * @return Object :Value of item
	 */
	public Object getValue(FieldName columnName, Object defaultValue)
	{
		Object value = getValueMap().get(columnName) ;
		if (value == null)
		{
			return defaultValue ;
		}
		return value ;
	}

	//#CM702759
	/**
	 * Setting a decimal value as String.
	 *
	 * @param columnName String: Set Item name
	 * @param decimalval String: Set value(There needs to be a numerical value as a character. )
	 */
	public void setDecimalString(FieldName columnName, String decimalval)
	{
		setValue(columnName, new BigDecimal(decimalval)) ;
	}

	//#CM702760
	/**
	 * Getting a column value as BigDecimal.
	 * <br>If type of column is not match, The ClassCastException occurs.
	 *
	 * @param columnName String: Acquired Item name
	 * @return :Value of item
	 */
	public BigDecimal getBigDecimal(FieldName columnName)
	{
		Object val = getValue(columnName) ;
		if (val instanceof Integer)
		{
			return new BigDecimal(val.toString());
		}
		else if (val instanceof BigDecimal)
		{
			return (BigDecimal) val;
		}
		
		return new BigDecimal(0);
	}

	//#CM702761
	/**
	 * Acquire the map of the value. 
	 *
	 * @return Map : Map of value
	 */
	public Map getValueMap()
	{
		return p_valueMap ;
	}

	//#CM702762
	/**
	 * Return the column name list. 
	 * 
	 * @return List : List of column names
	 */
	public List getColumnList()
	{
		return p_columnList ;
	}

	//#CM702763
	/**
	 * Return the column name list.  (Table name. Array of column names) 
	 * 
	 * @return List : List of column names
	 */
	public String[] getTableColumnArray()
	{
		return (String[])p_columnList.toArray(new String[0]) ;
	}

	//#CM702764
	/**
	 * Return the column name list.  (Array only of column name) 
	 * 
	 * @return List : List of column names
	 */
	public String[] getColumnArray()
	{
		String[] strArr = (String[])p_columnList.toArray(new String[0]) ;
		List lst = new ArrayList() ;

		for (int i = 0; i < strArr.length; i++)
		{
			String str = strArr[i].substring(strArr[i].indexOf(".") + 1, strArr[i].length()) ;
			lst.add(str) ;
		}
		return (String[])lst.toArray(new String[0]) ;
	}
	
	//#CM702765
	/**
	 * Set the automatic update row name. <BR>
	 * Use it when newly registering. <BR>
	 * Override if necessary in each entity class. 
	 */
	public  void setInitCreateColumn()
	{
	}
	//#CM702766
	/**
	 * Set the automatic update row name. <BR>
	 * Use it when updating it. <BR>
	 * Override if necessary in each entity class. 
	 * @return Array of automatic update row name
	 */
	public String[] getAutoUpdateColumnArray()
	{
		return new String[0];
	}

	//#CM702767
	/**
	 * Return the table name that this entity targets. 
	 * 
	 * @return Table name
	 */
	public abstract String getTablename() ;

	//#CM702768
	//------------------------------------------------------------
	//#CM702769
	// package methods
	//#CM702770
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702771
	//------------------------------------------------------------

	//#CM702772
	//------------------------------------------------------------
	//#CM702773
	// protected methods
	//#CM702774
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702775
	//------------------------------------------------------------
	//#CM702776
	//------------------------------------------------------------
	//#CM702777
	// private methods
	//#CM702778
	//------------------------------------------------------------

	//#CM702779
	//------------------------------------------------------------
	//#CM702780
	// utility methods
	//#CM702781
	//------------------------------------------------------------
	//#CM702782
	/**
	 * Return Rivision of this class. 
	 * @return Rivision character string. 
	 */
	public static String getVersion()
	{
		return "$Id: AbstractEntity.java,v 1.2 2006/11/09 08:56:15 suresh Exp $" ;
	}
}

//$Id: WorkingDataSearchKey.java,v 1.3 2006/11/13 04:32:53 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingData;

/**
 * This is the search key class for WORKINGDATA use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:53 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkingDataSearchKey
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
	public WorkingDataSearchKey()
	{
		super(new WorkingData()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  machine number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.RFTNO.toString(), arg);
	}
	/**
	 *Set search value for  machine number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.RFTNO.toString(), arg);
	}
	/**
	 *Set search value for  machine number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.RFTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  machine number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.RFTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for machine number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRftNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkingData.RFTNO.toString(), num, bool);
	}

	/**
	 * Set grouping order machine number
	 * @param num grouping order
	 */
	public void setRftNoGroup(int num)
	{
		setGroup(_Prefix + WorkingData.RFTNO.toString(), num);
	}

	/**
	 * Fetch machine number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRftNoCollect()
	{
		setCollect(_Prefix + WorkingData.RFTNO.toString(), "");
	}

	/**
	 * Fetch machine number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRftNoCollect(String compcode)
	{
		setCollect(_Prefix + WorkingData.RFTNO.toString(), compcode);
	}

	/**
	 *Set search value for  file name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFileName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.FILENAME.toString(), arg);
	}
	/**
	 *Set search value for  file name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFileName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.FILENAME.toString(), arg);
	}
	/**
	 *Set search value for  file name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFileName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.FILENAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  file name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFileName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.FILENAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for file name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setFileNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkingData.FILENAME.toString(), num, bool);
	}

	/**
	 * Set grouping order file name
	 * @param num grouping order
	 */
	public void setFileNameGroup(int num)
	{
		setGroup(_Prefix + WorkingData.FILENAME.toString(), num);
	}

	/**
	 * Fetch file name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setFileNameCollect()
	{
		setCollect(_Prefix + WorkingData.FILENAME.toString(), "");
	}

	/**
	 * Fetch file name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setFileNameCollect(String compcode)
	{
		setCollect(_Prefix + WorkingData.FILENAME.toString(), compcode);
	}

	/**
	 *Set search value for  line number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.LINENO.toString(), arg);
	}
	/**
	 *Set search value for  line number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.LINENO.toString(), arg);
	}
	/**
	 *Set search value for  line number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.LINENO.toString(), arg, compcode);
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
	public void setLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.LINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for line number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkingData.LINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order line number
	 * @param num grouping order
	 */
	public void setLineNoGroup(int num)
	{
		setGroup(_Prefix + WorkingData.LINENO.toString(), num);
	}

	/**
	 * Fetch line number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLineNoCollect()
	{
		setCollect(_Prefix + WorkingData.LINENO.toString(), "");
	}

	/**
	 * Fetch line number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLineNoCollect(String compcode)
	{
		setCollect(_Prefix + WorkingData.LINENO.toString(), compcode);
	}

	/**
	 *Set search value for  data
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContents(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.CONTENTS.toString(), arg);
	}
	/**
	 *Set search value for  data
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContents(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.CONTENTS.toString(), arg);
	}
	/**
	 *Set search value for  data
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContents(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.CONTENTS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  data
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContents(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.CONTENTS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for data
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setContentsOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkingData.CONTENTS.toString(), num, bool);
	}

	/**
	 * Set grouping order data
	 * @param num grouping order
	 */
	public void setContentsGroup(int num)
	{
		setGroup(_Prefix + WorkingData.CONTENTS.toString(), num);
	}

	/**
	 * Fetch data info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setContentsCollect()
	{
		setCollect(_Prefix + WorkingData.CONTENTS.toString(), "");
	}

	/**
	 * Fetch data info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setContentsCollect(String compcode)
	{
		setCollect(_Prefix + WorkingData.CONTENTS.toString(), compcode);
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
		return "$Id: WorkingDataSearchKey.java,v 1.3 2006/11/13 04:32:53 suresh Exp $" ;
	}
}

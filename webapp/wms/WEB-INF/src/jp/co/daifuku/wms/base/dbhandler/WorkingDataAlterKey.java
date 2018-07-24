//$Id: WorkingDataAlterKey.java,v 1.3 2006/11/13 04:32:53 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.WorkingData;

/**
 * Update key class for WORKINGDATA use
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


public class WorkingDataAlterKey
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
	public WorkingDataAlterKey()
	{
		super(new WorkingData()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for machine number
	 * @param arg machine number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.RFTNO.toString(), arg);
	}
	/**
	 * Set search value for machine number
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
	 * Set update value for machine number
	 * @param arg Update value for machine number
	 */
	public void updateRftNo(String arg)
	{
		setUpdValue(_Prefix + WorkingData.RFTNO.toString(), arg);
	}

	/**
	 * Set search value for file name
	 * @param arg file name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFileName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.FILENAME.toString(), arg);
	}
	/**
	 * Set search value for file name
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
	 * Set update value for file name
	 * @param arg Update value for file name
	 */
	public void updateFileName(String arg)
	{
		setUpdValue(_Prefix + WorkingData.FILENAME.toString(), arg);
	}

	/**
	 * Set search value for line number
	 * @param arg line number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.LINENO.toString(), arg);
	}
	/**
	 * Set search value for line number
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
	 * Set update value for line number
	 * @param arg Update value for line number
	 */
	public void updateLineNo(int arg)
	{
		setUpdValue(_Prefix + WorkingData.LINENO.toString(), arg);
	}

	/**
	 * Set search value for data
	 * @param arg data<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setContents(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkingData.CONTENTS.toString(), arg);
	}
	/**
	 * Set search value for data
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
	 * Set update value for data
	 * @param arg Update value for data
	 */
	public void updateContents(String arg)
	{
		setUpdValue(_Prefix + WorkingData.CONTENTS.toString(), arg);
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
		return "$Id: WorkingDataAlterKey.java,v 1.3 2006/11/13 04:32:53 suresh Exp $" ;
	}
}

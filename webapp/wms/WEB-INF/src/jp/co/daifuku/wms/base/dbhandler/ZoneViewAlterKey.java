//$Id: ZoneViewAlterKey.java,v 1.4 2006/11/13 04:32:52 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.ZoneView;

/**
 * Update key class for DVZONEVIEW use
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:32:52 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ZoneViewAlterKey
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
	public ZoneViewAlterKey()
	{
		super(new ZoneView()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Area No
	 * @param arg Area No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANO.toString(), arg);
	}
	/**
	 * Set search value for Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area No
	 * @param arg Update value for Area No
	 */
	public void updateAreaNo(String arg)
	{
		setUpdValue(_Prefix + ZoneView.AREANO.toString(), arg);
	}

	/**
	 * Set search value for Area Name
	 * @param arg Area Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANAME.toString(), arg);
	}
	/**
	 * Set search value for Area Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.AREANAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area Name
	 * @param arg Update value for Area Name
	 */
	public void updateAreaName(String arg)
	{
		setUpdValue(_Prefix + ZoneView.AREANAME.toString(), arg);
	}

	/**
	 * Set search value for Zone No
	 * @param arg Zone No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENO.toString(), arg);
	}
	/**
	 * Set search value for Zone No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENO.toString(), arg);
	}
	/**
	 *Set search value for  Zone No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Zone No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Zone No
	 * @param arg Update value for Zone No
	 */
	public void updateZoneNo(String arg)
	{
		setUpdValue(_Prefix + ZoneView.ZONENO.toString(), arg);
	}

	/**
	 * Set search value for Zone Name
	 * @param arg Zone Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENAME.toString(), arg);
	}
	/**
	 * Set search value for Zone Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENAME.toString(), arg);
	}
	/**
	 *Set search value for  Zone Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Zone Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ZoneView.ZONENAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Zone Name
	 * @param arg Update value for Zone Name
	 */
	public void updateZoneName(String arg)
	{
		setUpdValue(_Prefix + ZoneView.ZONENAME.toString(), arg);
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
		return "$Id: ZoneViewAlterKey.java,v 1.4 2006/11/13 04:32:52 suresh Exp $" ;
	}
}

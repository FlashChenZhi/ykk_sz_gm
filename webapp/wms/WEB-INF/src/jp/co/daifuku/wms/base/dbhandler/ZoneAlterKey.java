//$Id: ZoneAlterKey.java,v 1.2 2006/11/09 07:50:12 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Zone;

/**
 * Update key class for ZONE use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:50:12 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ZoneAlterKey
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
	public ZoneAlterKey()
	{
		super(new Zone()) ;
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
		setValue(_Prefix + Zone.AREANO.toString(), arg);
	}
	/**
	 * Set search value for Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.AREANO.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area No
	 * @param arg Update value for Area No
	 */
	public void updateAreaNo(String arg)
	{
		setUpdValue(_Prefix + Zone.AREANO.toString(), arg);
	}

	/**
	 * Set search value for Zone No
	 * @param arg Zone No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.ZONENO.toString(), arg);
	}
	/**
	 * Set search value for Zone No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.ZONENO.toString(), arg);
	}
	/**
	 *Set search value for  Zone No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.ZONENO.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.ZONENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Zone No
	 * @param arg Update value for Zone No
	 */
	public void updateZoneNo(String arg)
	{
		setUpdValue(_Prefix + Zone.ZONENO.toString(), arg);
	}

	/**
	 * Set search value for Zone Name
	 * @param arg Zone Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.ZONENAME.toString(), arg);
	}
	/**
	 * Set search value for Zone Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.ZONENAME.toString(), arg);
	}
	/**
	 *Set search value for  Zone Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.ZONENAME.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.ZONENAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Zone Name
	 * @param arg Update value for Zone Name
	 */
	public void updateZoneName(String arg)
	{
		setUpdValue(_Prefix + Zone.ZONENAME.toString(), arg);
	}

	/**
	 * Set search value for Delete flag
	 * @param arg Delete flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.DELETEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Delete flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.DELETEFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.DELETEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Delete flag
	 * @param arg Update value for Delete flag
	 */
	public void updateDeleteFlag(String arg)
	{
		setUpdValue(_Prefix + Zone.DELETEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + Zone.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + Zone.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Zone.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Zone.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Zone.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Zone.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Zone.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Zone.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: ZoneAlterKey.java,v 1.2 2006/11/09 07:50:12 suresh Exp $" ;
	}
}

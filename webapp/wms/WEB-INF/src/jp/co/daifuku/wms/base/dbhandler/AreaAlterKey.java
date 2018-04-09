//$Id: AreaAlterKey.java,v 1.2 2006/11/09 07:53:21 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Area;

/**
 * Update key class for AREA use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:53:21 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class AreaAlterKey
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
	public AreaAlterKey()
	{
		super(new Area()) ;
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
		setValue(_Prefix + Area.AREANO.toString(), arg);
	}
	/**
	 * Set search value for Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANO.toString(), arg, compcode);
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
		setValue(_Prefix + Area.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area No
	 * @param arg Update value for Area No
	 */
	public void updateAreaNo(String arg)
	{
		setUpdValue(_Prefix + Area.AREANO.toString(), arg);
	}

	/**
	 * Set search value for Area Name
	 * @param arg Area Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg);
	}
	/**
	 * Set search value for Area Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg, compcode);
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
		setValue(_Prefix + Area.AREANAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area Name
	 * @param arg Update value for Area Name
	 */
	public void updateAreaName(String arg)
	{
		setUpdValue(_Prefix + Area.AREANAME.toString(), arg);
	}

	/**
	 * Set search value for Area Type
	 * @param arg Area Type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg);
	}
	/**
	 * Set search value for Area Type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg);
	}
	/**
	 *Set search value for  Area Type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area Type
	 * @param arg Update value for Area Type
	 */
	public void updateAreaType(String arg)
	{
		setUpdValue(_Prefix + Area.AREATYPE.toString(), arg);
	}

	/**
	 * Set search value for Area Type Name
	 * @param arg Area Type Name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg);
	}
	/**
	 * Set search value for Area Type Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Type Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Type Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area Type Name
	 * @param arg Update value for Area Type Name
	 */
	public void updateAreaTypeName(String arg)
	{
		setUpdValue(_Prefix + Area.AREATYPENAME.toString(), arg);
	}

	/**
	 * Set search value for Area Duty
	 * @param arg Area Duty<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg);
	}
	/**
	 * Set search value for Area Duty
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg);
	}
	/**
	 *Set search value for  Area Duty
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Duty
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area Duty
	 * @param arg Update value for Area Duty
	 */
	public void updateAreaDuty(String arg)
	{
		setUpdValue(_Prefix + Area.AREADUTY.toString(), arg);
	}

	/**
	 * Set search value for Delete flag
	 * @param arg Delete flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Delete flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Delete flag
	 * @param arg Update value for Delete flag
	 */
	public void updateDeleteFlag(String arg)
	{
		setUpdValue(_Prefix + Area.DELETEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Vacant Search type
	 * @param arg Vacant Search type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg);
	}
	/**
	 * Set search value for Vacant Search type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Vacant Search type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Vacant Search type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Vacant Search type
	 * @param arg Update value for Vacant Search type
	 */
	public void updateVacantSearchType(String arg)
	{
		setUpdValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg);
	}

	/**
	 * Set search value for Last used bank no
	 * @param arg Last used bank no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg);
	}
	/**
	 * Set search value for Last used bank no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg);
	}
	/**
	 *Set search value for  Last used bank no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last used bank no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last used bank no
	 * @param arg Update value for Last used bank no
	 */
	public void updateLastUsedBankNo(int arg)
	{
		setUpdValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Area.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + Area.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + Area.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: AreaAlterKey.java,v 1.2 2006/11/09 07:53:21 suresh Exp $" ;
	}
}

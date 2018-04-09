//#CM707993
//$Id: Zone.java,v 1.4 2006/11/13 04:30:54 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707994
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Zone;

//#CM707995
/**
 * Entity class of ZONE
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:30:54 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Zone
		extends AbstractEntity
{
	//#CM707996
	//------------------------------------------------------------
	//#CM707997
	// class variables (prefix '$')
	//#CM707998
	//------------------------------------------------------------
	//#CM707999
	//	private String	$classVar ;

	//#CM708000
	//------------------------------------------------------------
	//#CM708001
	// fields (upper case only)
	//#CM708002
	//------------------------------------------------------------
	//#CM708003
	/*
	 *  * Table name : ZONE
	 * Area No :                       AREANO              varchar2(16)
	 * Zone No :                       ZONENO              varchar2(3)
	 * Zone Name :                     ZONENAME            varchar2(40)
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM708004
	/**Table name definition*/

	public static final String TABLE_NAME = "DMZONE";

	//#CM708005
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM708006
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM708007
	/** Column Definition (ZONENAME) */

	public static final FieldName ZONENAME = new FieldName("ZONE_NAME");

	//#CM708008
	/** Column Definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");

	//#CM708009
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM708010
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM708011
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM708012
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM708013
	//------------------------------------------------------------
	//#CM708014
	// properties (prefix 'p_')
	//#CM708015
	//------------------------------------------------------------
	//#CM708016
	//	private String	p_Name ;


	//#CM708017
	//------------------------------------------------------------
	//#CM708018
	// instance variables (prefix '_')
	//#CM708019
	//------------------------------------------------------------
	//#CM708020
	//	private String	_instanceVar ;

	//#CM708021
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM708022
	//------------------------------------------------------------
	//#CM708023
	// constructors
	//#CM708024
	//------------------------------------------------------------

	//#CM708025
	/**
	 * Prepare class name list and generate instance
	 */
	public Zone()
	{
		super() ;
		prepare() ;
	}

	//#CM708026
	//------------------------------------------------------------
	//#CM708027
	// accessors
	//#CM708028
	//------------------------------------------------------------

	//#CM708029
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM708030
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(Zone.AREANO).toString();
	}

	//#CM708031
	/**
	 * Set value to Zone No
	 * @param arg Zone No to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM708032
	/**
	 * Fetch Zone No
	 * @return Zone No
	 */
	public String getZoneNo()
	{
		return getValue(Zone.ZONENO).toString();
	}

	//#CM708033
	/**
	 * Set value to Zone Name
	 * @param arg Zone Name to be set
	 */
	public void setZoneName(String arg)
	{
		setValue(ZONENAME, arg);
	}

	//#CM708034
	/**
	 * Fetch Zone Name
	 * @return Zone Name
	 */
	public String getZoneName()
	{
		return getValue(Zone.ZONENAME).toString();
	}

	//#CM708035
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM708036
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Zone.DELETEFLAG).toString();
	}

	//#CM708037
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM708038
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Zone.REGISTDATE);
	}

	//#CM708039
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM708040
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Zone.REGISTPNAME).toString();
	}

	//#CM708041
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM708042
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Zone.LASTUPDATEDATE);
	}

	//#CM708043
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM708044
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Zone.LASTUPDATEPNAME).toString();
	}


	//#CM708045
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM708046
	//------------------------------------------------------------
	//#CM708047
	// package methods
	//#CM708048
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708049
	//------------------------------------------------------------


	//#CM708050
	//------------------------------------------------------------
	//#CM708051
	// protected methods
	//#CM708052
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708053
	//------------------------------------------------------------


	//#CM708054
	//------------------------------------------------------------
	//#CM708055
	// private methods
	//#CM708056
	//------------------------------------------------------------
	//#CM708057
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + AREANO);
		lst.add(prefix + ZONENO);
		lst.add(prefix + ZONENAME);
		lst.add(prefix + DELETEFLAG);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM708058
	//------------------------------------------------------------
	//#CM708059
	// utility methods
	//#CM708060
	//------------------------------------------------------------
	//#CM708061
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Zone.java,v 1.4 2006/11/13 04:30:54 suresh Exp $" ;
	}
}

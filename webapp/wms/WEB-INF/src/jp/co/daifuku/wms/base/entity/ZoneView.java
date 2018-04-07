//#CM708062
//$Id: ZoneView.java,v 1.4 2006/11/13 04:30:54 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM708063
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.ZoneView;

//#CM708064
/**
 * Entity class of DVZONEVIEW
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


public class ZoneView
		extends AbstractEntity
{
	//#CM708065
	//------------------------------------------------------------
	//#CM708066
	// class variables (prefix '$')
	//#CM708067
	//------------------------------------------------------------
	//#CM708068
	//	private String	$classVar ;

	//#CM708069
	//------------------------------------------------------------
	//#CM708070
	// fields (upper case only)
	//#CM708071
	//------------------------------------------------------------
	//#CM708072
	/*
	 *  * Table name : DVZONEVIEW
	 * Area No :                       AREANO              varchar2(16)
	 * Area Name :                     AREANAME            varchar2(40)
	 * Zone No :                       ZONENO              varchar2(3)
	 * Zone Name :                     ZONENAME            varchar2(40)
	 */

	//#CM708073
	/**Table name definition*/

	public static final String TABLE_NAME = "DVZONEVIEW";

	//#CM708074
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM708075
	/** Column Definition (AREANAME) */

	public static final FieldName AREANAME = new FieldName("AREA_NAME");

	//#CM708076
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM708077
	/** Column Definition (ZONENAME) */

	public static final FieldName ZONENAME = new FieldName("ZONE_NAME");


	//#CM708078
	//------------------------------------------------------------
	//#CM708079
	// properties (prefix 'p_')
	//#CM708080
	//------------------------------------------------------------
	//#CM708081
	//	private String	p_Name ;


	//#CM708082
	//------------------------------------------------------------
	//#CM708083
	// instance variables (prefix '_')
	//#CM708084
	//------------------------------------------------------------
	//#CM708085
	//	private String	_instanceVar ;

	//#CM708086
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM708087
	//------------------------------------------------------------
	//#CM708088
	// constructors
	//#CM708089
	//------------------------------------------------------------

	//#CM708090
	/**
	 * Prepare class name list and generate instance
	 */
	public ZoneView()
	{
		super() ;
		prepare() ;
	}

	//#CM708091
	//------------------------------------------------------------
	//#CM708092
	// accessors
	//#CM708093
	//------------------------------------------------------------

	//#CM708094
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM708095
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(ZoneView.AREANO).toString();
	}

	//#CM708096
	/**
	 * Set value to Area Name
	 * @param arg Area Name to be set
	 */
	public void setAreaName(String arg)
	{
		setValue(AREANAME, arg);
	}

	//#CM708097
	/**
	 * Fetch Area Name
	 * @return Area Name
	 */
	public String getAreaName()
	{
		return getValue(ZoneView.AREANAME).toString();
	}

	//#CM708098
	/**
	 * Set value to Zone No
	 * @param arg Zone No to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM708099
	/**
	 * Fetch Zone No
	 * @return Zone No
	 */
	public String getZoneNo()
	{
		return getValue(ZoneView.ZONENO).toString();
	}

	//#CM708100
	/**
	 * Set value to Zone Name
	 * @param arg Zone Name to be set
	 */
	public void setZoneName(String arg)
	{
		setValue(ZONENAME, arg);
	}

	//#CM708101
	/**
	 * Fetch Zone Name
	 * @return Zone Name
	 */
	public String getZoneName()
	{
		return getValue(ZoneView.ZONENAME).toString();
	}


	//#CM708102
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM708103
	//------------------------------------------------------------
	//#CM708104
	// package methods
	//#CM708105
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708106
	//------------------------------------------------------------


	//#CM708107
	//------------------------------------------------------------
	//#CM708108
	// protected methods
	//#CM708109
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM708110
	//------------------------------------------------------------


	//#CM708111
	//------------------------------------------------------------
	//#CM708112
	// private methods
	//#CM708113
	//------------------------------------------------------------
	//#CM708114
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + AREANO);
		lst.add(prefix + AREANAME);
		lst.add(prefix + ZONENO);
		lst.add(prefix + ZONENAME);
	}


	//#CM708115
	//------------------------------------------------------------
	//#CM708116
	// utility methods
	//#CM708117
	//------------------------------------------------------------
	//#CM708118
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ZoneView.java,v 1.4 2006/11/13 04:30:54 suresh Exp $" ;
	}
}

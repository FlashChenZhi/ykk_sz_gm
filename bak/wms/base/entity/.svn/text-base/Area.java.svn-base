//#CM702905
//$Id: Area.java,v 1.5 2006/11/16 02:15:48 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM702906
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Area;

//#CM702907
/**
 * Entity class of AREA
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:48 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Area
		extends AbstractEntity
{
	//#CM702908
	//------------------------------------------------------------
	//#CM702909
	// class variables (prefix '$')
	//#CM702910
	//------------------------------------------------------------
	//#CM702911
	//	private String	$classVar ;
	//#CM702912
	/**
	 * Method of retrieving empty location (horizontal bank)
	 */
	public static final String Area_VACANTSEARCHTYPE_BANKHORIZONTAL = "0" ;

	//#CM702913
	/**
	 * Method of retrieving empty location (horizontal aisle)
	 */
	public static final String Area_VACANTSEARCHTYPE_AISLEHORIZONTAL = "1" ;

	//#CM702914
	//------------------------------------------------------------
	//#CM702915
	// fields (upper case only)
	//#CM702916
	//------------------------------------------------------------
	//#CM702917
	/*
	 *  * Table name : AREA
	 * Area No :                       AREANO              varchar2(16)
	 * Area Name :                     AREANAME            varchar2(40)
	 * Area Type :                     AREATYPE            varchar2(2)
	 * Area Type Name :                AREATYPENAME        varchar2(40)
	 * Area Duty :                     AREADUTY            varchar2(1)
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 * Vacant Search type :            VACANTSEARCHTYPE    varchar2(1)
	 * Last used bank no :             LASTUSEDBANKNO      number
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM702918
	/**Table name definition*/

	public static final String TABLE_NAME = "DMAREA";

	//#CM702919
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM702920
	/** Column Definition (AREANAME) */

	public static final FieldName AREANAME = new FieldName("AREA_NAME");

	//#CM702921
	/** Column Definition (AREATYPE) */

	public static final FieldName AREATYPE = new FieldName("AREA_TYPE");

	//#CM702922
	/** Column Definition (AREATYPENAME) */

	public static final FieldName AREATYPENAME = new FieldName("AREA_TYPE_NAME");

	//#CM702923
	/** Column Definition (AREADUTY) */

	public static final FieldName AREADUTY = new FieldName("AREA_DUTY");

	//#CM702924
	/** Column Definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");

	//#CM702925
	/** Column Definition (VACANTSEARCHTYPE) */

	public static final FieldName VACANTSEARCHTYPE = new FieldName("VACANT_SEARCH_TYPE");

	//#CM702926
	/** Column Definition (LASTUSEDBANKNO) */

	public static final FieldName LASTUSEDBANKNO = new FieldName("LAST_USED_BANK_NO");

	//#CM702927
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM702928
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM702929
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM702930
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM702931
	//------------------------------------------------------------
	//#CM702932
	// properties (prefix 'p_')
	//#CM702933
	//------------------------------------------------------------
	//#CM702934
	//	private String	p_Name ;


	//#CM702935
	//------------------------------------------------------------
	//#CM702936
	// instance variables (prefix '_')
	//#CM702937
	//------------------------------------------------------------
	//#CM702938
	//	private String	_instanceVar ;

	//#CM702939
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM702940
	//------------------------------------------------------------
	//#CM702941
	// constructors
	//#CM702942
	//------------------------------------------------------------

	//#CM702943
	/**
	 * Prepare class name list and generate instance
	 */
	public Area()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM702944
	//------------------------------------------------------------
	//#CM702945
	// accessors
	//#CM702946
	//------------------------------------------------------------

	//#CM702947
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM702948
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(Area.AREANO).toString();
	}

	//#CM702949
	/**
	 * Set value to Area Name
	 * @param arg Area Name to be set
	 */
	public void setAreaName(String arg)
	{
		setValue(AREANAME, arg);
	}

	//#CM702950
	/**
	 * Fetch Area Name
	 * @return Area Name
	 */
	public String getAreaName()
	{
		return getValue(Area.AREANAME).toString();
	}

	//#CM702951
	/**
	 * Set value to Area Type
	 * @param arg Area Type to be set
	 */
	public void setAreaType(String arg)
	{
		setValue(AREATYPE, arg);
	}

	//#CM702952
	/**
	 * Fetch Area Type
	 * @return Area Type
	 */
	public String getAreaType()
	{
		return getValue(Area.AREATYPE).toString();
	}

	//#CM702953
	/**
	 * Set value to Area Type Name
	 * @param arg Area Type Name to be set
	 */
	public void setAreaTypeName(String arg)
	{
		setValue(AREATYPENAME, arg);
	}

	//#CM702954
	/**
	 * Fetch Area Type Name
	 * @return Area Type Name
	 */
	public String getAreaTypeName()
	{
		return getValue(Area.AREATYPENAME).toString();
	}

	//#CM702955
	/**
	 * Set value to Area Duty
	 * @param arg Area Duty to be set
	 */
	public void setAreaDuty(String arg)
	{
		setValue(AREADUTY, arg);
	}

	//#CM702956
	/**
	 * Fetch Area Duty
	 * @return Area Duty
	 */
	public String getAreaDuty()
	{
		return getValue(Area.AREADUTY).toString();
	}

	//#CM702957
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM702958
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Area.DELETEFLAG).toString();
	}

	//#CM702959
	/**
	 * Set value to Vacant Search type
	 * @param arg Vacant Search type to be set
	 */
	public void setVacantSearchType(String arg) throws InvalidStatusException
	{
		if ((arg.equals(Area_VACANTSEARCHTYPE_BANKHORIZONTAL))
			|| (arg.equals(Area_VACANTSEARCHTYPE_AISLEHORIZONTAL)))
		{
			setValue(VACANTSEARCHTYPE, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wVacantSearchType";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM702960
	/**
	 * Fetch Vacant Search type
	 * @return Vacant Search type
	 */
	public String getVacantSearchType()
	{
		return getValue(Area.VACANTSEARCHTYPE).toString();
	}

	//#CM702961
	/**
	 * Set value to Last used bank no
	 * @param arg Last used bank no to be set
	 */
	public void setLastUsedBankNo(int arg)
	{
		setValue(LASTUSEDBANKNO, new Integer(arg));
	}

	//#CM702962
	/**
	 * Fetch Last used bank no
	 * @return Last used bank no
	 */
	public int getLastUsedBankNo()
	{
		return getBigDecimal(Area.LASTUSEDBANKNO).intValue();
	}

	//#CM702963
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM702964
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Area.REGISTDATE);
	}

	//#CM702965
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM702966
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Area.REGISTPNAME).toString();
	}

	//#CM702967
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM702968
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Area.LASTUPDATEDATE);
	}

	//#CM702969
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM702970
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Area.LASTUPDATEPNAME).toString();
	}


	//#CM702971
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM702972
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUSEDBANKNO, new Integer(0));
		setValue(REGISTDATE, "");
		setValue(LASTUPDATEDATE, "");
	}

	/**
	 * <BR>
	 * <BR>
	 * @return
	 */
	public String[] getAutoUpdateColumnArray()
	{
		String prefix = TABLE_NAME + "." ;

		Vector autoColumn = new Vector();
		autoColumn.add(prefix + LASTUPDATEDATE);

		String[] autoColumnArray = new String[autoColumn.size()];
		autoColumn.copyInto(autoColumnArray);

		return autoColumnArray;
	}
	//------------------------------------------------------------
	//#CM702973
	// package methods
	//#CM702974
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702975
	//------------------------------------------------------------


	//#CM702976
	//------------------------------------------------------------
	//#CM702977
	// protected methods
	//#CM702978
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702979
	//------------------------------------------------------------


	//#CM702980
	//------------------------------------------------------------
	//#CM702981
	// private methods
	//#CM702982
	//------------------------------------------------------------
	//#CM702983
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
		lst.add(prefix + AREATYPE);
		lst.add(prefix + AREATYPENAME);
		lst.add(prefix + AREADUTY);
		lst.add(prefix + DELETEFLAG);
		lst.add(prefix + VACANTSEARCHTYPE);
		lst.add(prefix + LASTUSEDBANKNO);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM702984
	//------------------------------------------------------------
	//#CM702985
	// utility methods
	//#CM702986
	//------------------------------------------------------------
	//#CM702987
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Area.java,v 1.5 2006/11/16 02:15:48 suresh Exp $" ;
	}
}

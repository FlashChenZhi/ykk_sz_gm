//#CM705046
//$Id: OperationDisplay.java,v 1.4 2006/11/13 04:31:02 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705047
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
import jp.co.daifuku.wms.base.entity.OperationDisplay;

//#CM705048
/**
 * Entity class of OPERATIONDISPLAY
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:31:02 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class OperationDisplay
		extends AbstractEntity
{
	//#CM705049
	//------------------------------------------------------------
	//#CM705050
	// class variables (prefix '$')
	//#CM705051
	//------------------------------------------------------------
	//#CM705052
	//	private String	$classVar ;

	//#CM705053
	//------------------------------------------------------------
	//#CM705054
	// fields (upper case only)
	//#CM705055
	//------------------------------------------------------------
	//#CM705056
	/*
	 *  * Table name : OPERATIONDISPLAY
	 * carry key :                     CARRYKEY            varchar2(16)
	 * station number :                STATIONNUMBER       varchar2(16)
	 * arrival date :                  ARRIVALDATE         date
	 */

	//#CM705057
	/**Table name definition*/

	public static final String TABLE_NAME = "OPERATIONDISPLAY";

	//#CM705058
	/** Column Definition (CARRYKEY) */

	public static final FieldName CARRYKEY = new FieldName("CARRYKEY");

	//#CM705059
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM705060
	/** Column Definition (ARRIVALDATE) */

	public static final FieldName ARRIVALDATE = new FieldName("ARRIVALDATE");


	//#CM705061
	//------------------------------------------------------------
	//#CM705062
	// properties (prefix 'p_')
	//#CM705063
	//------------------------------------------------------------
	//#CM705064
	//	private String	p_Name ;


	//#CM705065
	//------------------------------------------------------------
	//#CM705066
	// instance variables (prefix '_')
	//#CM705067
	//------------------------------------------------------------
	//#CM705068
	//	private String	_instanceVar ;

	//#CM705069
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705070
	//------------------------------------------------------------
	//#CM705071
	// constructors
	//#CM705072
	//------------------------------------------------------------

	//#CM705073
	/**
	 * Prepare class name list and generate instance
	 */
	public OperationDisplay()
	{
		super() ;
		prepare() ;
	}

	//#CM705074
	//------------------------------------------------------------
	//#CM705075
	// accessors
	//#CM705076
	//------------------------------------------------------------

	//#CM705077
	/**
	 * Set value to carry key
	 * @param arg carry key to be set
	 */
	public void setCarryKey(String arg)
	{
		setValue(CARRYKEY, arg);
	}

	//#CM705078
	/**
	 * Fetch carry key
	 * @return carry key
	 */
	public String getCarryKey()
	{
		return getValue(OperationDisplay.CARRYKEY).toString();
	}

	//#CM705079
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM705080
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(OperationDisplay.STATIONNUMBER).toString();
	}

	//#CM705081
	/**
	 * Set value to arrival date
	 * @param arg arrival date to be set
	 */
	public void setArrivalDate(Date arg)
	{
		setValue(ARRIVALDATE, arg);
	}

	//#CM705082
	/**
	 * Fetch arrival date
	 * @return arrival date
	 */
	public Date getArrivalDate()
	{
		return (Date)getValue(OperationDisplay.ARRIVALDATE);
	}


	//#CM705083
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705084
	//------------------------------------------------------------
	//#CM705085
	// package methods
	//#CM705086
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705087
	//------------------------------------------------------------


	//#CM705088
	//------------------------------------------------------------
	//#CM705089
	// protected methods
	//#CM705090
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705091
	//------------------------------------------------------------


	//#CM705092
	//------------------------------------------------------------
	//#CM705093
	// private methods
	//#CM705094
	//------------------------------------------------------------
	//#CM705095
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + CARRYKEY);
		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + ARRIVALDATE);
	}


	//#CM705096
	//------------------------------------------------------------
	//#CM705097
	// utility methods
	//#CM705098
	//------------------------------------------------------------
	//#CM705099
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: OperationDisplay.java,v 1.4 2006/11/13 04:31:02 suresh Exp $" ;
	}
}

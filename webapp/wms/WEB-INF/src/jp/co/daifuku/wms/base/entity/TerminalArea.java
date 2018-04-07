//#CM707296
//$Id: TerminalArea.java,v 1.5 2006/11/16 02:15:35 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707297
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.TerminalArea;

//#CM707298
/**
 * Entity class of TERMINALAREA
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:35 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class TerminalArea
		extends AbstractEntity
{
	//#CM707299
	//------------------------------------------------------------
	//#CM707300
	// class variables (prefix '$')
	//#CM707301
	//------------------------------------------------------------
	//#CM707302
	//	private String	$classVar ;

	//#CM707303
	//------------------------------------------------------------
	//#CM707304
	// fields (upper case only)
	//#CM707305
	//------------------------------------------------------------
	//#CM707306
	/*
	 *  * Table name : TERMINALAREA
	 * terminal number :               TERMINALNUMBER      varchar2(4)
	 * area id :                       AREAID              number
	 * station number :                STATIONNUMBER       varchar2(4)
	 */

	//#CM707307
	/**Table name definition*/

	public static final String TABLE_NAME = "TERMINALAREA";

	//#CM707308
	/** Column Definition (TERMINALNUMBER) */

	public static final FieldName TERMINALNUMBER = new FieldName("TERMINALNUMBER");

	//#CM707309
	/** Column Definition (AREAID) */

	public static final FieldName AREAID = new FieldName("AREAID");

	//#CM707310
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");


	//#CM707311
	//------------------------------------------------------------
	//#CM707312
	// properties (prefix 'p_')
	//#CM707313
	//------------------------------------------------------------
	//#CM707314
	//	private String	p_Name ;


	//#CM707315
	//------------------------------------------------------------
	//#CM707316
	// instance variables (prefix '_')
	//#CM707317
	//------------------------------------------------------------
	//#CM707318
	//	private String	_instanceVar ;

	//#CM707319
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707320
	//------------------------------------------------------------
	//#CM707321
	// constructors
	//#CM707322
	//------------------------------------------------------------

	//#CM707323
	/**
	 * Prepare class name list and generate instance
	 */
	public TerminalArea()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM707324
	//------------------------------------------------------------
	//#CM707325
	// accessors
	//#CM707326
	//------------------------------------------------------------

	//#CM707327
	/**
	 * Set value to terminal number
	 * @param arg terminal number to be set
	 */
	public void setTerminalNumber(String arg)
	{
		setValue(TERMINALNUMBER, arg);
	}

	//#CM707328
	/**
	 * Fetch terminal number
	 * @return terminal number
	 */
	public String getTerminalNumber()
	{
		return getValue(TerminalArea.TERMINALNUMBER).toString();
	}

	//#CM707329
	/**
	 * Set value to area id
	 * @param arg area id to be set
	 */
	public void setAreaId(long arg)
	{
		setValue(AREAID, new Long(arg));
	}

	//#CM707330
	/**
	 * Fetch area id
	 * @return area id
	 */
	public long getAreaId()
	{
		return getBigDecimal(TerminalArea.AREAID).longValue();
	}

	//#CM707331
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM707332
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(TerminalArea.STATIONNUMBER).toString();
	}


	//#CM707333
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707334
	public void setInitCreateColumn()
	{
		setValue(AREAID, new Long(0));
	}
	//------------------------------------------------------------
	//#CM707335
	// package methods
	//#CM707336
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707337
	//------------------------------------------------------------


	//#CM707338
	//------------------------------------------------------------
	//#CM707339
	// protected methods
	//#CM707340
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707341
	//------------------------------------------------------------


	//#CM707342
	//------------------------------------------------------------
	//#CM707343
	// private methods
	//#CM707344
	//------------------------------------------------------------
	//#CM707345
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + TERMINALNUMBER);
		lst.add(prefix + AREAID);
		lst.add(prefix + STATIONNUMBER);
	}


	//#CM707346
	//------------------------------------------------------------
	//#CM707347
	// utility methods
	//#CM707348
	//------------------------------------------------------------
	//#CM707349
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: TerminalArea.java,v 1.5 2006/11/16 02:15:35 suresh Exp $" ;
	}
}

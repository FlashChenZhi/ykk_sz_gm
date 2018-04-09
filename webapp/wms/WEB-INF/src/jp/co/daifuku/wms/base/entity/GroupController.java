//#CM703469
//$Id: GroupController.java,v 1.5 2006/11/16 02:15:45 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703470
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.GroupController;

//#CM703471
/**
 * Entity class of GROUPCONTROLLER
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:45 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class GroupController
		extends AbstractEntity
{
	//#CM703472
	/**
	 * Field which shows group controller's state(uncertainty)
	 */
	public static final int STATUS_UNKNOWN = 0 ;

	//#CM703473
	/**
	 * Field which shows group controller's state(online)
	 */
	public static final int STATUS_ONLINE = 1 ;

	//#CM703474
	/**
	 * Field which shows group controller's state(Off-line/connected)
	 */
	public static final int STATUS_OFFLINE = 2 ;

	//#CM703475
	/**
	 * Field which shows group controller's state(end reservation)
	 */
	public static final int STATUS_END_RESERVATION = 3 ;
	//#CM703476
	//------------------------------------------------------------
	//#CM703477
	// class variables (prefix '$')
	//#CM703478
	//------------------------------------------------------------
	//#CM703479
	//	private String	$classVar ;

	//#CM703480
	//------------------------------------------------------------
	//#CM703481
	// fields (upper case only)
	//#CM703482
	//------------------------------------------------------------
	//#CM703483
	/*
	 *  * Table name : GROUPCONTROLLER
	 * controller number :             CONTROLLERNUMBER    number
	 * status :                        STATUS              number
	 * IP address :                    IPADDRESS           varchar2(64)
	 * port number :                   PORT                number
	 */

	//#CM703484
	/**Table name definition*/

	public static final String TABLE_NAME = "GROUPCONTROLLER";

	//#CM703485
	/** Column Definition (CONTROLLERNUMBER) */

	public static final FieldName CONTROLLERNUMBER = new FieldName("CONTROLLERNUMBER");

	//#CM703486
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM703487
	/** Column Definition (IPADDRESS) */

	public static final FieldName IPADDRESS = new FieldName("IPADDRESS");

	//#CM703488
	/** Column Definition (PORT) */

	public static final FieldName PORT = new FieldName("PORT");


	//#CM703489
	//------------------------------------------------------------
	//#CM703490
	// properties (prefix 'p_')
	//#CM703491
	//------------------------------------------------------------
	//#CM703492
	//	private String	p_Name ;


	//#CM703493
	//------------------------------------------------------------
	//#CM703494
	// instance variables (prefix '_')
	//#CM703495
	//------------------------------------------------------------
	//#CM703496
	//	private String	_instanceVar ;

	//#CM703497
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703498
	//------------------------------------------------------------
	//#CM703499
	// constructors
	//#CM703500
	//------------------------------------------------------------

	//#CM703501
	/**
	 * Prepare class name list and generate instance
	 */
	public GroupController()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703502
	//------------------------------------------------------------
	//#CM703503
	// accessors
	//#CM703504
	//------------------------------------------------------------

	//#CM703505
	/**
	 * Set value to controller number
	 * @param arg controller number to be set
	 */
	public void setControllerNumber(int arg)
	{
		setValue(CONTROLLERNUMBER, new Integer(arg));
	}

	//#CM703506
	/**
	 * Fetch controller number
	 * @return controller number
	 */
	public int getControllerNumber()
	{
		return getBigDecimal(GroupController.CONTROLLERNUMBER).intValue();
	}

	//#CM703507
	/**
	 * Set value to status
	 * @param arg status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM703508
	/**
	 * Fetch status
	 * @return status
	 */
	public int getStatus()
	{
		return getBigDecimal(GroupController.STATUS).intValue();
	}

	//#CM703509
	/**
	 * Set value to IP address
	 * @param arg IP address to be set
	 */
	public void setIPAddress(String arg)
	{
		setValue(IPADDRESS, arg);
	}

	//#CM703510
	/**
	 * Fetch IP address
	 * @return IP address
	 */
	public String getIPAddress()
	{
		return getValue(GroupController.IPADDRESS).toString();
	}

	//#CM703511
	/**
	 * Set value to port number
	 * @param arg port number to be set
	 */
	public void setPort(int arg)
	{
		setValue(PORT, new Integer(arg));
	}

	//#CM703512
	/**
	 * Fetch port number
	 * @return port number
	 */
	public int getPort()
	{
		return getBigDecimal(GroupController.PORT).intValue();
	}


	//#CM703513
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703514
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(CONTROLLERNUMBER, new Integer(0));
		setValue(STATUS, new Integer(0));
		setValue(PORT, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM703515
	// package methods
	//#CM703516
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703517
	//------------------------------------------------------------


	//#CM703518
	//------------------------------------------------------------
	//#CM703519
	// protected methods
	//#CM703520
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703521
	//------------------------------------------------------------


	//#CM703522
	//------------------------------------------------------------
	//#CM703523
	// private methods
	//#CM703524
	//------------------------------------------------------------
	//#CM703525
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + CONTROLLERNUMBER);
		lst.add(prefix + STATUS);
		lst.add(prefix + IPADDRESS);
		lst.add(prefix + PORT);
	}


	//#CM703526
	//------------------------------------------------------------
	//#CM703527
	// utility methods
	//#CM703528
	//------------------------------------------------------------
	//#CM703529
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: GroupController.java,v 1.5 2006/11/16 02:15:45 suresh Exp $" ;
	}
}

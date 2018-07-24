//#CM704655
//$Id: Machine.java,v 1.5 2006/11/16 02:15:42 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704656
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Machine;

//#CM704657
/**
 * Entity class of MACHINE
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:42 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Machine
		extends AbstractEntity
{
	//#CM704658
	//------------------------------------------------------------
	//#CM704659
	// class variables (prefix '$')
	//#CM704660
	//------------------------------------------------------------
	//#CM704661
	//	private String	$classVar ;
//#CM704662
/**<jp>
	 * Field which shows state of machine (disconnected)
	 </jp>*/
	//#CM704663
	/**<en>
	 * Field for the machine state (disconnected)
	 </en>*/
	public static final int STATE_DISCONNECT = -1 ;
	//#CM704664
	/**<jp>
	 * Field which shows state of machine (active)
	 </jp>*/
	//#CM704665
	/**<en>
	 * Field for the machine state (active)
	 </en>*/
	public static final int STATE_ACTIVE = 0 ;
	//#CM704666
	/**<jp>
	 * Field which shows state of machine (stopped)
	 </jp>*/
	//#CM704667
	/**<en>
	 * Field for the machine state (stopped)
	 </en>*/
	public static final int STATE_STOP = 1 ;
	//#CM704668
	/**<jp>
	 * Field which shows state of machine (failed)
	 </jp>*/
	//#CM704669
	/**<en>
	 * Field for the machine state (failed)
	 </en>*/
	public static final int STATE_FAIL = 2 ;
	//#CM704670
	/**<jp>
	 * Field which shows state of machine (off-line)
	 </jp>*/
	//#CM704671
	/**<en>
	 * Field for the machine state (off-line)
	 </en>*/
	public static final int STATE_OFFLINE = 3 ;

	//#CM704672
	//------------------------------------------------------------
	//#CM704673
	// fields (upper case only)
	//#CM704674
	//------------------------------------------------------------
	//#CM704675
	/*
	 *  * Table name : MACHINE
	 * station number :                STATIONNUMBER       varchar2(16)
	 * machine type :                  MACHINETYPE         number
	 * machine number :                MACHINENUMBER       number
	 * status :                        STATE               number
	 * error code :                    ERRORCODE           varchar2(16)
	 * controller number :             CONTROLLERNUMBER    number
	 */

	//#CM704676
	/**Table name definition*/

	public static final String TABLE_NAME = "MACHINE";

	//#CM704677
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM704678
	/** Column Definition (MACHINETYPE) */

	public static final FieldName MACHINETYPE = new FieldName("MACHINETYPE");

	//#CM704679
	/** Column Definition (MACHINENUMBER) */

	public static final FieldName MACHINENUMBER = new FieldName("MACHINENUMBER");

	//#CM704680
	/** Column Definition (STATE) */

	public static final FieldName STATE = new FieldName("STATE");

	//#CM704681
	/** Column Definition (ERRORCODE) */

	public static final FieldName ERRORCODE = new FieldName("ERRORCODE");

	//#CM704682
	/** Column Definition (CONTROLLERNUMBER) */

	public static final FieldName CONTROLLERNUMBER = new FieldName("CONTROLLERNUMBER");


	//#CM704683
	//------------------------------------------------------------
	//#CM704684
	// properties (prefix 'p_')
	//#CM704685
	//------------------------------------------------------------
	//#CM704686
	//	private String	p_Name ;


	//#CM704687
	//------------------------------------------------------------
	//#CM704688
	// instance variables (prefix '_')
	//#CM704689
	//------------------------------------------------------------
	//#CM704690
	//	private String	_instanceVar ;

	//#CM704691
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704692
	//------------------------------------------------------------
	//#CM704693
	// constructors
	//#CM704694
	//------------------------------------------------------------

	//#CM704695
	/**
	 * Prepare class name list and generate instance
	 */
	public Machine()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704696
	//------------------------------------------------------------
	//#CM704697
	// accessors
	//#CM704698
	//------------------------------------------------------------

	//#CM704699
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM704700
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(Machine.STATIONNUMBER).toString();
	}

	//#CM704701
	/**
	 * Set value to machine type
	 * @param arg machine type to be set
	 */
	public void setMachineType(int arg)
	{
		setValue(MACHINETYPE, new Integer(arg));
	}

	//#CM704702
	/**
	 * Fetch machine type
	 * @return machine type
	 */
	public int getMachineType()
	{
		return getBigDecimal(Machine.MACHINETYPE).intValue();
	}

	//#CM704703
	/**
	 * Set value to machine number
	 * @param arg machine number to be set
	 */
	public void setMachineNumber(int arg)
	{
		setValue(MACHINENUMBER, new Integer(arg));
	}

	//#CM704704
	/**
	 * Fetch machine number
	 * @return machine number
	 */
	public int getMachineNumber()
	{
		return getBigDecimal(Machine.MACHINENUMBER).intValue();
	}

	//#CM704705
	/**
	 * Set value to status
	 * @param arg status to be set
	 */
	public void setState(int arg)
	{
		setValue(STATE, new Integer(arg));
	}

	//#CM704706
	/**
	 * Fetch status
	 * @return status
	 */
	public int getState()
	{
		return getBigDecimal(Machine.STATE).intValue();
	}

	//#CM704707
	/**
	 * Set value to error code
	 * @param arg error code to be set
	 */
	public void setErrorCode(String arg)
	{
		setValue(ERRORCODE, arg);
	}

	//#CM704708
	/**
	 * Fetch error code
	 * @return error code
	 */
	public String getErrorCode()
	{
		return getValue(Machine.ERRORCODE).toString();
	}

	//#CM704709
	/**
	 * Set value to controller number
	 * @param arg controller number to be set
	 */
	public void setControllerNumber(int arg)
	{
		setValue(CONTROLLERNUMBER, new Integer(arg));
	}

	//#CM704710
	/**
	 * Fetch controller number
	 * @return controller number
	 */
	public int getControllerNumber()
	{
		return getBigDecimal(Machine.CONTROLLERNUMBER).intValue();
	}


	//#CM704711
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704712
	/**
	 *<BR>
	 *<BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(MACHINETYPE, new Integer(0));
		setValue(MACHINENUMBER, new Integer(0));
		setValue(STATE, new Integer(0));
		setValue(CONTROLLERNUMBER, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM704713
	// package methods
	//#CM704714
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704715
	//------------------------------------------------------------


	//#CM704716
	//------------------------------------------------------------
	//#CM704717
	// protected methods
	//#CM704718
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704719
	//------------------------------------------------------------


	//#CM704720
	//------------------------------------------------------------
	//#CM704721
	// private methods
	//#CM704722
	//------------------------------------------------------------
	//#CM704723
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + MACHINETYPE);
		lst.add(prefix + MACHINENUMBER);
		lst.add(prefix + STATE);
		lst.add(prefix + ERRORCODE);
		lst.add(prefix + CONTROLLERNUMBER);
	}


	//#CM704724
	//------------------------------------------------------------
	//#CM704725
	// utility methods
	//#CM704726
	//------------------------------------------------------------
	//#CM704727
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Machine.java,v 1.5 2006/11/16 02:15:42 suresh Exp $" ;
	}
}

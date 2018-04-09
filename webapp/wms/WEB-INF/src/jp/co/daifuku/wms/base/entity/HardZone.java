//#CM703530
//$Id: HardZone.java,v 1.5 2006/11/16 02:15:45 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703531
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.HardZone;

//#CM703532
/**
 * Entity class of HARDZONE
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


public class HardZone
		extends AbstractEntity
{
	//#CM703533
	//------------------------------------------------------------
	//#CM703534
	// class variables (prefix '$')
	//#CM703535
	//------------------------------------------------------------
	//#CM703536
	//	private String	$classVar ;

	//#CM703537
	//------------------------------------------------------------
	//#CM703538
	// fields (upper case only)
	//#CM703539
	//------------------------------------------------------------
	//#CM703540
	/*
	 *  * Table name : HARDZONE
	 * hard zone id :                  HARDZONEID          number
	 * zone name :                     NAME                varchar2(40)
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * height of load :                HEIGHT              number
	 * zone priority order :           PRIORITY            varchar2(10)
	 * starting bank number :          STARTBANK           number
	 * starting bay number :           STARTBAY            number
	 * starting level :                STARTLEVEL          number
	 * last bank number :              ENDBANK             number
	 * last bay number :               ENDBAY              number
	 * last level number :             ENDLEVEL            number
	 */

	//#CM703541
	/**Table name definition*/

	public static final String TABLE_NAME = "HARDZONE";

	//#CM703542
	/** Column Definition (HARDZONEID) */

	public static final FieldName HARDZONEID = new FieldName("HARDZONEID");

	//#CM703543
	/** Column Definition (NAME) */

	public static final FieldName NAME = new FieldName("NAME");

	//#CM703544
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM703545
	/** Column Definition (HEIGHT) */

	public static final FieldName HEIGHT = new FieldName("HEIGHT");

	//#CM703546
	/** Column Definition (PRIORITY) */

	public static final FieldName PRIORITY = new FieldName("PRIORITY");

	//#CM703547
	/** Column Definition (STARTBANK) */

	public static final FieldName STARTBANK = new FieldName("STARTBANK");

	//#CM703548
	/** Column Definition (STARTBAY) */

	public static final FieldName STARTBAY = new FieldName("STARTBAY");

	//#CM703549
	/** Column Definition (STARTLEVEL) */

	public static final FieldName STARTLEVEL = new FieldName("STARTLEVEL");

	//#CM703550
	/** Column Definition (ENDBANK) */

	public static final FieldName ENDBANK = new FieldName("ENDBANK");

	//#CM703551
	/** Column Definition (ENDBAY) */

	public static final FieldName ENDBAY = new FieldName("ENDBAY");

	//#CM703552
	/** Column Definition (ENDLEVEL) */

	public static final FieldName ENDLEVEL = new FieldName("ENDLEVEL");


	//#CM703553
	//------------------------------------------------------------
	//#CM703554
	// properties (prefix 'p_')
	//#CM703555
	//------------------------------------------------------------
	//#CM703556
	//	private String	p_Name ;


	//#CM703557
	//------------------------------------------------------------
	//#CM703558
	// instance variables (prefix '_')
	//#CM703559
	//------------------------------------------------------------
	//#CM703560
	//	private String	_instanceVar ;

	//#CM703561
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703562
	//------------------------------------------------------------
	//#CM703563
	// constructors
	//#CM703564
	//------------------------------------------------------------

	//#CM703565
	/**
	 * Prepare class name list and generate instance
	 */
	public HardZone()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703566
	//------------------------------------------------------------
	//#CM703567
	// accessors
	//#CM703568
	//------------------------------------------------------------

	//#CM703569
	/**
	 * Set value to hard zone id
	 * @param arg hard zone id to be set
	 */
	public void setHardZoneID(int arg)
	{
		setValue(HARDZONEID, new Integer(arg));
	}

	//#CM703570
	/**
	 * Fetch hard zone id
	 * @return hard zone id
	 */
	public int getHardZoneID()
	{
		return getBigDecimal(HardZone.HARDZONEID).intValue();
	}

	//#CM703571
	/**
	 * Set value to zone name
	 * @param arg zone name to be set
	 */
	public void setName(String arg)
	{
		setValue(NAME, arg);
	}

	//#CM703572
	/**
	 * Fetch zone name
	 * @return zone name
	 */
	public String getName()
	{
		return getValue(HardZone.NAME).toString();
	}

	//#CM703573
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM703574
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(HardZone.WHSTATIONNUMBER).toString();
	}

	//#CM703575
	/**
	 * Set value to height of load
	 * @param arg height of load to be set
	 */
	public void setHeight(int arg)
	{
		setValue(HEIGHT, new Integer(arg));
	}

	//#CM703576
	/**
	 * Fetch height of load
	 * @return height of load
	 */
	public int getHeight()
	{
		return getBigDecimal(HardZone.HEIGHT).intValue();
	}

	//#CM703577
	/**
	 * Set value to zone priority order
	 * @param arg zone priority order to be set
	 */
	public void setPriority(String arg)
	{
		setValue(PRIORITY, arg);
	}

	//#CM703578
	/**
	 * Fetch zone priority order
	 * @return zone priority order
	 */
	public String getPriority()
	{
		return getValue(HardZone.PRIORITY).toString();
	}

	//#CM703579
	/**
	 * Set value to starting bank number
	 * @param arg starting bank number to be set
	 */
	public void setStartBank(int arg)
	{
		setValue(STARTBANK, new Integer(arg));
	}

	//#CM703580
	/**
	 * Fetch starting bank number
	 * @return starting bank number
	 */
	public int getStartBank()
	{
		return getBigDecimal(HardZone.STARTBANK).intValue();
	}

	//#CM703581
	/**
	 * Set value to starting bay number
	 * @param arg starting bay number to be set
	 */
	public void setStartBay(int arg)
	{
		setValue(STARTBAY, new Integer(arg));
	}

	//#CM703582
	/**
	 * Fetch starting bay number
	 * @return starting bay number
	 */
	public int getStartBay()
	{
		return getBigDecimal(HardZone.STARTBAY).intValue();
	}

	//#CM703583
	/**
	 * Set value to starting level
	 * @param arg starting level to be set
	 */
	public void setStartLevel(int arg)
	{
		setValue(STARTLEVEL, new Integer(arg));
	}

	//#CM703584
	/**
	 * Fetch starting level
	 * @return starting level
	 */
	public int getStartLevel()
	{
		return getBigDecimal(HardZone.STARTLEVEL).intValue();
	}

	//#CM703585
	/**
	 * Set value to last bank number
	 * @param arg last bank number to be set
	 */
	public void setEndBank(int arg)
	{
		setValue(ENDBANK, new Integer(arg));
	}

	//#CM703586
	/**
	 * Fetch last bank number
	 * @return last bank number
	 */
	public int getEndBank()
	{
		return getBigDecimal(HardZone.ENDBANK).intValue();
	}

	//#CM703587
	/**
	 * Set value to last bay number
	 * @param arg last bay number to be set
	 */
	public void setEndBay(int arg)
	{
		setValue(ENDBAY, new Integer(arg));
	}

	//#CM703588
	/**
	 * Fetch last bay number
	 * @return last bay number
	 */
	public int getEndBay()
	{
		return getBigDecimal(HardZone.ENDBAY).intValue();
	}

	//#CM703589
	/**
	 * Set value to last level number
	 * @param arg last level number to be set
	 */
	public void setEndLevel(int arg)
	{
		setValue(ENDLEVEL, new Integer(arg));
	}

	//#CM703590
	/**
	 * Fetch last level number
	 * @return last level number
	 */
	public int getEndLevel()
	{
		return getBigDecimal(HardZone.ENDLEVEL).intValue();
	}


	//#CM703591
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703592
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(HARDZONEID, new Integer(0));
		setValue(HEIGHT, new Integer(0));
		setValue(STARTBANK, new Integer(0));
		setValue(STARTBAY, new Integer(0));
		setValue(STARTLEVEL, new Integer(0));
		setValue(ENDBANK, new Integer(0));
		setValue(ENDBAY, new Integer(0));
		setValue(ENDLEVEL, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM703593
	// package methods
	//#CM703594
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703595
	//------------------------------------------------------------


	//#CM703596
	//------------------------------------------------------------
	//#CM703597
	// protected methods
	//#CM703598
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703599
	//------------------------------------------------------------


	//#CM703600
	//------------------------------------------------------------
	//#CM703601
	// private methods
	//#CM703602
	//------------------------------------------------------------
	//#CM703603
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + HARDZONEID);
		lst.add(prefix + NAME);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + HEIGHT);
		lst.add(prefix + PRIORITY);
		lst.add(prefix + STARTBANK);
		lst.add(prefix + STARTBAY);
		lst.add(prefix + STARTLEVEL);
		lst.add(prefix + ENDBANK);
		lst.add(prefix + ENDBAY);
		lst.add(prefix + ENDLEVEL);
	}


	//#CM703604
	//------------------------------------------------------------
	//#CM703605
	// utility methods
	//#CM703606
	//------------------------------------------------------------
	//#CM703607
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: HardZone.java,v 1.5 2006/11/16 02:15:45 suresh Exp $" ;
	}
}

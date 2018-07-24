//#CM704552
//$Id: Locate.java,v 1.5 2006/11/16 02:15:42 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704553
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
import jp.co.daifuku.wms.base.entity.Locate;

//#CM704554
/**
 * Entity class of LOCATE
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


public class Locate
		extends AbstractEntity
{
	//#CM704555
	//------------------------------------------------------------
	//#CM704556
	// class variables (prefix '$')
	//#CM704557
	//------------------------------------------------------------
	//#CM704558
	//	private String	$classVar ;
//#CM704559
/**
	 * State flag(empty location)
	 */
	public static final String Locate_StatusFlag_EMPTY = "0" ;

	//#CM704560
	/**
	 * State flag(Reserved storage location)
	 */
	public static final String Locate_StatusFlag_STORINGRESERVED = "1" ;

	//#CM704561
	/**
	 * State flag(actual location)
	 */
	public static final String Locate_StatusFlag_OCCUPIED = "2" ;

	//#CM704562
	/**
	 * Restricted usage flag (Can be used)
	 */
	public static final String Locate_ProhibitionFlag_NOPROHIBITED = "0" ;

	//#CM704563
	/**
	 * Restricted usage flag (Restricted location)
	 */
	public static final String Locate_ProhibitionFlag_PROHIBITED = "1" ;

	//#CM704564
	/**
	 * Registration flag (System registration)
	 */
	public static final String Locate_RegistFlag_SYSTEM = "0" ;

	//#CM704565
	/**
	 * Registration flag (Maintenance registration)
	 */
	public static final String Locate_RegistFlag_MAINTE = "1" ;

	//#CM704566
	//------------------------------------------------------------
	//#CM704567
	// fields (upper case only)
	//#CM704568
	//------------------------------------------------------------
	//#CM704569
	/*
	 *  * Table name : LOCATE
	 * Area No :                       AREANO              varchar2(3)
	 * Location No (Block No) :        LOCATIONNO          varchar2(16)
	 * Aisle No :                      AISLENO             number
	 * Bank No :                       BANKNO              varchar2(3)
	 * Bay No :                        BAYNO               varchar2(3)
	 * Level No :                      LEVELNO             varchar2(3)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Prohibition flag :              PROHIBITIONFLAG     varchar2(1)
	 * Filling rate :                  FILLINGRATE         number
	 * Plan filling rate :             PLANFILLINGRATE     number
	 * Mixed load count :              MIXEDLOADCNT        number
	 * Zone :                          ZONE                varchar2(3)
	 * Registration flag :             REGISTFLAG          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM704570
	/**Table name definition*/

	public static final String TABLE_NAME = "DMLOCATE";

	//#CM704571
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM704572
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM704573
	/** Column Definition (AISLENO) */

	public static final FieldName AISLENO = new FieldName("AISLE_NO");

	//#CM704574
	/** Column Definition (BANKNO) */

	public static final FieldName BANKNO = new FieldName("BANK_NO");

	//#CM704575
	/** Column Definition (BAYNO) */

	public static final FieldName BAYNO = new FieldName("BAY_NO");

	//#CM704576
	/** Column Definition (LEVELNO) */

	public static final FieldName LEVELNO = new FieldName("LEVEL_NO");

	//#CM704577
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM704578
	/** Column Definition (PROHIBITIONFLAG) */

	public static final FieldName PROHIBITIONFLAG = new FieldName("PROHIBITION_FLAG");

	//#CM704579
	/** Column Definition (FILLINGRATE) */

	public static final FieldName FILLINGRATE = new FieldName("FILLING_RATE");

	//#CM704580
	/** Column Definition (PLANFILLINGRATE) */

	public static final FieldName PLANFILLINGRATE = new FieldName("PLAN_FILLING_RATE");

	//#CM704581
	/** Column Definition (MIXEDLOADCNT) */

	public static final FieldName MIXEDLOADCNT = new FieldName("MIXED_LOAD_CNT");

	//#CM704582
	/** Column Definition (ZONE) */

	public static final FieldName ZONE = new FieldName("ZONE");

	//#CM704583
	/** Column Definition (REGISTFLAG) */

	public static final FieldName REGISTFLAG = new FieldName("REGIST_FLAG");

	//#CM704584
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM704585
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM704586
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM704587
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM704588
	//------------------------------------------------------------
	//#CM704589
	// properties (prefix 'p_')
	//#CM704590
	//------------------------------------------------------------
	//#CM704591
	//	private String	p_Name ;


	//#CM704592
	//------------------------------------------------------------
	//#CM704593
	// instance variables (prefix '_')
	//#CM704594
	//------------------------------------------------------------
	//#CM704595
	//	private String	_instanceVar ;

	//#CM704596
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704597
	//------------------------------------------------------------
	//#CM704598
	// constructors
	//#CM704599
	//------------------------------------------------------------

	//#CM704600
	/**
	 * Prepare class name list and generate instance
	 */
	public Locate()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704601
	//------------------------------------------------------------
	//#CM704602
	// accessors
	//#CM704603
	//------------------------------------------------------------

	//#CM704604
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM704605
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(Locate.AREANO).toString();
	}

	//#CM704606
	/**
	 * Set value to Location No (Block No)
	 * @param arg Location No (Block No) to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM704607
	/**
	 * Fetch Location No (Block No)
	 * @return Location No (Block No)
	 */
	public String getLocationNo()
	{
		return getValue(Locate.LOCATIONNO).toString();
	}

	//#CM704608
	/**
	 * Set value to Aisle No
	 * @param arg Aisle No to be set
	 */
	public void setAisleNo(int arg)
	{
		setValue(AISLENO, new Integer(arg));
	}

	//#CM704609
	/**
	 * Fetch Aisle No
	 * @return Aisle No
	 */
	public int getAisleNo()
	{
		return getBigDecimal(Locate.AISLENO).intValue();
	}

	//#CM704610
	/**
	 * Set value to Bank No
	 * @param arg Bank No to be set
	 */
	public void setBankNo(String arg)
	{
		setValue(BANKNO, arg);
	}

	//#CM704611
	/**
	 * Fetch Bank No
	 * @return Bank No
	 */
	public String getBankNo()
	{
		return getValue(Locate.BANKNO).toString();
	}

	//#CM704612
	/**
	 * Set value to Bay No
	 * @param arg Bay No to be set
	 */
	public void setBayNo(String arg)
	{
		setValue(BAYNO, arg);
	}

	//#CM704613
	/**
	 * Fetch Bay No
	 * @return Bay No
	 */
	public String getBayNo()
	{
		return getValue(Locate.BAYNO).toString();
	}

	//#CM704614
	/**
	 * Set value to Level No
	 * @param arg Level No to be set
	 */
	public void setLevelNo(String arg)
	{
		setValue(LEVELNO, arg);
	}

	//#CM704615
	/**
	 * Fetch Level No
	 * @return Level No
	 */
	public String getLevelNo()
	{
		return getValue(Locate.LEVELNO).toString();
	}

	//#CM704616
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(Locate_StatusFlag_EMPTY))		||
			(arg.equals(Locate_StatusFlag_OCCUPIED))	||
			(arg.equals(Locate_StatusFlag_STORINGRESERVED)))
		{
			setValue(STATUSFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wStatusFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704617
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(Locate.STATUSFLAG).toString();
	}

	//#CM704618
	/**
	 * Set value to Prohibition flag
	 * @param arg Prohibition flag to be set
	 */
	public void setProhibitionFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(Locate_ProhibitionFlag_NOPROHIBITED))		||
			(arg.equals(Locate_ProhibitionFlag_PROHIBITED)))
		{
			setValue(PROHIBITIONFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wProhibitionFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704619
	/**
	 * Fetch Prohibition flag
	 * @return Prohibition flag
	 */
	public String getProhibitionFlag()
	{
		return getValue(Locate.PROHIBITIONFLAG).toString();
	}

	//#CM704620
	/**
	 * Set value to Filling rate
	 * @param arg Filling rate to be set
	 */
	public void setFillingRate(int arg)
	{
		setValue(FILLINGRATE, new Integer(arg));
	}

	//#CM704621
	/**
	 * Fetch Filling rate
	 * @return Filling rate
	 */
	public int getFillingRate()
	{
		return getBigDecimal(Locate.FILLINGRATE).intValue();
	}

	//#CM704622
	/**
	 * Set value to Plan filling rate
	 * @param arg Plan filling rate to be set
	 */
	public void setPlanFillingRate(int arg)
	{
		setValue(PLANFILLINGRATE, new Integer(arg));
	}

	//#CM704623
	/**
	 * Fetch Plan filling rate
	 * @return Plan filling rate
	 */
	public int getPlanFillingRate()
	{
		return getBigDecimal(Locate.PLANFILLINGRATE).intValue();
	}

	//#CM704624
	/**
	 * Set value to Mixed load count
	 * @param arg Mixed load count to be set
	 */
	public void setMixedLoadCnt(int arg)
	{
		setValue(MIXEDLOADCNT, new Integer(arg));
	}

	//#CM704625
	/**
	 * Fetch Mixed load count
	 * @return Mixed load count
	 */
	public int getMixedLoadCnt()
	{
		return getBigDecimal(Locate.MIXEDLOADCNT).intValue();
	}

	//#CM704626
	/**
	 * Set value to Zone
	 * @param arg Zone to be set
	 */
	public void setZone(String arg)
	{
		setValue(ZONE, arg);
	}

	//#CM704627
	/**
	 * Fetch Zone
	 * @return Zone
	 */
	public String getZone()
	{
		return getValue(Locate.ZONE).toString();
	}

	//#CM704628
	/**
	 * Set value to Registration flag
	 * @param arg Registration flag to be set
	 */
	public void setRegistFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(Locate_RegistFlag_SYSTEM))		||
			(arg.equals(Locate_RegistFlag_MAINTE)))
		{
			setValue(REGISTFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wRegistFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704629
	/**
	 * Fetch Registration flag
	 * @return Registration flag
	 */
	public String getRegistFlag()
	{
		return getValue(Locate.REGISTFLAG).toString();
	}

	//#CM704630
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM704631
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Locate.REGISTDATE);
	}

	//#CM704632
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM704633
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Locate.REGISTPNAME).toString();
	}

	//#CM704634
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM704635
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Locate.LASTUPDATEDATE);
	}

	//#CM704636
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM704637
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Locate.LASTUPDATEPNAME).toString();
	}


	//#CM704638
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704639
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(AISLENO, new Integer(0));
		setValue(FILLINGRATE, new Integer(0));
		setValue(PLANFILLINGRATE, new Integer(0));
		setValue(MIXEDLOADCNT, new Integer(0));
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
	//#CM704640
	// package methods
	//#CM704641
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704642
	//------------------------------------------------------------


	//#CM704643
	//------------------------------------------------------------
	//#CM704644
	// protected methods
	//#CM704645
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704646
	//------------------------------------------------------------


	//#CM704647
	//------------------------------------------------------------
	//#CM704648
	// private methods
	//#CM704649
	//------------------------------------------------------------
	//#CM704650
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + AREANO);
		lst.add(prefix + LOCATIONNO);
		lst.add(prefix + AISLENO);
		lst.add(prefix + BANKNO);
		lst.add(prefix + BAYNO);
		lst.add(prefix + LEVELNO);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + PROHIBITIONFLAG);
		lst.add(prefix + FILLINGRATE);
		lst.add(prefix + PLANFILLINGRATE);
		lst.add(prefix + MIXEDLOADCNT);
		lst.add(prefix + ZONE);
		lst.add(prefix + REGISTFLAG);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM704651
	//------------------------------------------------------------
	//#CM704652
	// utility methods
	//#CM704653
	//------------------------------------------------------------
	//#CM704654
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Locate.java,v 1.5 2006/11/16 02:15:42 suresh Exp $" ;
	}
}

//#CM702832
//$Id: Aisle.java,v 1.5 2006/11/16 02:15:48 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM702833
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.util.List;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Aisle;

//#CM702834
/**
 * Entity class of AISLE
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


public class Aisle extends Station
{
	//#CM702835
	//------------------------------------------------------------
	//#CM702836
	// class variables (prefix '$')
	//#CM702837
	//------------------------------------------------------------
	//#CM702838
	//	private String	$classVar ;
//#CM702839
/**<jp>
	 * Field which shows kind of fork(single deep)
	 </jp>*/
	//#CM702840
	/**<en>
	 * Field for fork type: single deep
	 </en>*/
	public static final int    SINGLE_DEEP = 0 ;

	//#CM702841
	/**<jp>
	 * Double field (deep which shows kind of fork)
	 </jp>*/
	//#CM702842
	/**<en>
	 * Field for fork type: double deep
	 </en>*/
	public static final int    DOUBLE_DEEP = 1 ;

	//#CM702843
	//------------------------------------------------------------
	//#CM702844
	// fields (upper case only)
	//#CM702845
	//------------------------------------------------------------
	//#CM702846
	/*
	 *  * Table name : AISLE
	 * station number :                STATIONNUMBER       varchar2(16)
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * aisle number :                  AISLENUMBER         number
	 * controller number :             CONTROLLERNUMBER    number
	 * double deep type :              DOUBLEDEEPKIND      number
	 * status :                        STATUS              number
	 * inventory controll check flag : INVENTORYCHECKFLAG  number
	 * last used bank :                LASTUSEDBANK        number
	 */

	//#CM702847
	/**Table name definition*/

	public static final String TABLE_NAME = "AISLE";

	//#CM702848
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM702849
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM702850
	/** Column Definition (AISLENUMBER) */

	public static final FieldName AISLENUMBER = new FieldName("AISLENUMBER");

	//#CM702851
	/** Column Definition (CONTROLLERNUMBER) */

	public static final FieldName CONTROLLERNUMBER = new FieldName("CONTROLLERNUMBER");

	//#CM702852
	/** Column Definition (DOUBLEDEEPKIND) */

	public static final FieldName DOUBLEDEEPKIND = new FieldName("DOUBLEDEEPKIND");

	//#CM702853
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM702854
	/** Column Definition (INVENTORYCHECKFLAG) */

	public static final FieldName INVENTORYCHECKFLAG = new FieldName("INVENTORYCHECKFLAG");

	//#CM702855
	/** Column Definition (LASTUSEDBANK) */

	public static final FieldName LASTUSEDBANK = new FieldName("LASTUSEDBANK");


	//#CM702856
	//------------------------------------------------------------
	//#CM702857
	// properties (prefix 'p_')
	//#CM702858
	//------------------------------------------------------------
	//#CM702859
	//	private String	p_Name ;


	//#CM702860
	//------------------------------------------------------------
	//#CM702861
	// instance variables (prefix '_')
	//#CM702862
	//------------------------------------------------------------
	//#CM702863
	//	private String	_instanceVar ;

	//#CM702864
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM702865
	//------------------------------------------------------------
	//#CM702866
	// constructors
	//#CM702867
	//------------------------------------------------------------

	//#CM702868
	/**
	 * Prepare class name list and generate instance
	 */
	public Aisle()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM702869
	//------------------------------------------------------------
	//#CM702870
	// accessors
	//#CM702871
	//------------------------------------------------------------

	//#CM702872
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM702873
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(Aisle.STATIONNUMBER).toString();
	}

	//#CM702874
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM702875
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(Aisle.WHSTATIONNUMBER).toString();
	}

	//#CM702876
	/**
	 * Set value to aisle number
	 * @param arg aisle number to be set
	 */
	public void setAisleNumber(int arg)
	{
		setValue(AISLENUMBER, new Integer(arg));
	}

	//#CM702877
	/**
	 * Fetch aisle number
	 * @return aisle number
	 */
	public int getAisleNumber()
	{
		return getBigDecimal(Aisle.AISLENUMBER).intValue();
	}

	//#CM702878
	/**
	 * Set value to controller number
	 * @param arg controller number to be set
	 */
	public void setControllerNumber(int arg)
	{
		setValue(CONTROLLERNUMBER, new Integer(arg));
	}

	//#CM702879
	/**
	 * Fetch controller number
	 * @return controller number
	 */
	public int getControllerNumber()
	{
		return getBigDecimal(Aisle.CONTROLLERNUMBER).intValue();
	}

	//#CM702880
	/**
	 * Set value to double deep type
	 * @param arg double deep type to be set
	 */
	public void setDoubleDeepKind(int arg)
	{
		setValue(DOUBLEDEEPKIND, new Integer(arg));
	}

	//#CM702881
	/**
	 * Fetch double deep type
	 * @return double deep type
	 */
	public int getDoubleDeepKind()
	{
		return getBigDecimal(Aisle.DOUBLEDEEPKIND).intValue();
	}

	//#CM702882
	/**
	 * Set value to status
	 * @param arg status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM702883
	/**
	 * Fetch status
	 * @return status
	 */
	public int getStatus()
	{
		return getBigDecimal(Aisle.STATUS).intValue();
	}

	//#CM702884
	/**
	 * Set value to inventory controll check flag
	 * @param arg inventory controll check flag to be set
	 */
	public void setInventoryCheckFlag(int arg)
	{
		setValue(INVENTORYCHECKFLAG, new Integer(arg));
	}

	//#CM702885
	/**
	 * Fetch inventory controll check flag
	 * @return inventory controll check flag
	 */
	public int getInventoryCheckFlag()
	{
		return getBigDecimal(Aisle.INVENTORYCHECKFLAG).intValue();
	}

	//#CM702886
	/**
	 * Set value to last used bank
	 * @param arg last used bank to be set
	 */
	public void setLastUsedBank(int arg)
	{
		setValue(LASTUSEDBANK, new Integer(arg));
	}

	//#CM702887
	/**
	 * Fetch last used bank
	 * @return last used bank
	 */
	public int getLastUsedBank()
	{
		return getBigDecimal(Aisle.LASTUSEDBANK).intValue();
	}


	//#CM702888
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM702889
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(AISLENUMBER, new Integer(0));
		setValue(CONTROLLERNUMBER, new Integer(0));
		setValue(DOUBLEDEEPKIND, new Integer(0));
		setValue(STATUS, new Integer(0));
		setValue(INVENTORYCHECKFLAG, new Integer(0));
		setValue(LASTUSEDBANK, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM702890
	// package methods
	//#CM702891
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702892
	//------------------------------------------------------------


	//#CM702893
	//------------------------------------------------------------
	//#CM702894
	// protected methods
	//#CM702895
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM702896
	//------------------------------------------------------------


	//#CM702897
	//------------------------------------------------------------
	//#CM702898
	// private methods
	//#CM702899
	//------------------------------------------------------------
	//#CM702900
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + AISLENUMBER);
		lst.add(prefix + CONTROLLERNUMBER);
		lst.add(prefix + DOUBLEDEEPKIND);
		lst.add(prefix + STATUS);
		lst.add(prefix + INVENTORYCHECKFLAG);
		lst.add(prefix + LASTUSEDBANK);
	}


	//#CM702901
	//------------------------------------------------------------
	//#CM702902
	// utility methods
	//#CM702903
	//------------------------------------------------------------
	//#CM702904
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Aisle.java,v 1.5 2006/11/16 02:15:48 suresh Exp $" ;
	}
}

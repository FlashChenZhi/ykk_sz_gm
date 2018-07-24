//#CM706820
//$Id: StationType.java,v 1.4 2006/11/13 04:30:58 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM706821
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.StationType;

//#CM706822
/**
 * Entity class of STATIONTYPE
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:30:58 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class StationType
		extends AbstractEntity
{
	//#CM706823
	//------------------------------------------------------------
	//#CM706824
	// class variables (prefix '$')
	//#CM706825
	//------------------------------------------------------------
	//#CM706826
	//	private String	$classVar ;

	//#CM706827
	//------------------------------------------------------------
	//#CM706828
	// fields (upper case only)
	//#CM706829
	//------------------------------------------------------------
	//#CM706830
	/*
	 *  * Table name : STATIONTYPE
	 * station number :                STATIONNUMBER       varchar2(16)
	 * handler class :                 HANDLERCLASS        varchar2(128)
	 */

	//#CM706831
	/**Table name definition*/

	public static final String TABLE_NAME = "STATIONTYPE";

	//#CM706832
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM706833
	/** Column Definition (HANDLERCLASS) */

	public static final FieldName HANDLERCLASS = new FieldName("HANDLERCLASS");


	//#CM706834
	//------------------------------------------------------------
	//#CM706835
	// properties (prefix 'p_')
	//#CM706836
	//------------------------------------------------------------
	//#CM706837
	//	private String	p_Name ;


	//#CM706838
	//------------------------------------------------------------
	//#CM706839
	// instance variables (prefix '_')
	//#CM706840
	//------------------------------------------------------------
	//#CM706841
	//	private String	_instanceVar ;

	//#CM706842
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706843
	//------------------------------------------------------------
	//#CM706844
	// constructors
	//#CM706845
	//------------------------------------------------------------

	//#CM706846
	/**
	 * Prepare class name list and generate instance
	 */
	public StationType()
	{
		super() ;
		prepare() ;
	}

	//#CM706847
	//------------------------------------------------------------
	//#CM706848
	// accessors
	//#CM706849
	//------------------------------------------------------------

	//#CM706850
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationnumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM706851
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationnumber()
	{
		return getValue(StationType.STATIONNUMBER).toString();
	}

	//#CM706852
	/**
	 * Set value to handler class
	 * @param arg handler class to be set
	 */
	public void setHandlerclass(String arg)
	{
		setValue(HANDLERCLASS, arg);
	}

	//#CM706853
	/**
	 * Fetch handler class
	 * @return handler class
	 */
	public String getHandlerclass()
	{
		return getValue(StationType.HANDLERCLASS).toString();
	}


	//#CM706854
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM706855
	//------------------------------------------------------------
	//#CM706856
	// package methods
	//#CM706857
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706858
	//------------------------------------------------------------


	//#CM706859
	//------------------------------------------------------------
	//#CM706860
	// protected methods
	//#CM706861
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706862
	//------------------------------------------------------------


	//#CM706863
	//------------------------------------------------------------
	//#CM706864
	// private methods
	//#CM706865
	//------------------------------------------------------------
	//#CM706866
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + HANDLERCLASS);
	}


	//#CM706867
	//------------------------------------------------------------
	//#CM706868
	// utility methods
	//#CM706869
	//------------------------------------------------------------
	//#CM706870
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: StationType.java,v 1.4 2006/11/13 04:30:58 suresh Exp $" ;
	}
}

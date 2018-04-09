//#CM33960
//$Id: ASLocationSearchKey.java,v 1.2 2006/10/30 07:13:08 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

//#CM33961
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.entity.ASLocation;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;

//#CM33962
/**
 * Used in Station Factory<BR>
 * Search row without specifying table name<BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>2005/11/11</b></td><td><b>Y.Okamura</b></td><td><b>Create this class</b></td></tr>
 *
 * <!-- revision history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:13:08 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ASLocationSearchKey
		extends AbstractSQLSearchKey
{
	//#CM33963
	//------------------------------------------------------------
	//#CM33964
	// class variables (p_Prefix '$')
	//#CM33965
	//------------------------------------------------------------
	//#CM33966
	//	private String	$classVar ;
	private static String p_Prefix = "";
	
	//#CM33967
	/**
	 * Search key:stationNo.
	 */
	private static String STATIONNUMBER = "STATIONNUMBER";

	//#CM33968
	//------------------------------------------------------------
	//#CM33969
	// fields (upper case only)
	//#CM33970
	//------------------------------------------------------------
	//#CM33971
	//	public static final int FIELD_VALUE = 1 ;

	//#CM33972
	//------------------------------------------------------------
	//#CM33973
	// properties (p_Prefix 'p_')
	//#CM33974
	//------------------------------------------------------------
	//#CM33975
	//	private String	p_Name ;

	//#CM33976
	//------------------------------------------------------------
	//#CM33977
	// instance variables (p_Prefix '_')
	//#CM33978
	//------------------------------------------------------------
	//#CM33979
	//	private String	_instanceVar ;

	//#CM33980
	//------------------------------------------------------------
	//#CM33981
	// constructors
	//#CM33982
	//------------------------------------------------------------
	//#CM33983
	/**
	 * Prepare the table and column names and generate instance
	 * 
	 */
	public ASLocationSearchKey()
	{
		super(new ASLocation()) ;
	}

	//#CM33984
	/**
	 * Set search value for station no.
	 * @param : arg Set the string value to search
	 * @throws ReadWriteException Throw exception in case of invalid character
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(p_Prefix + STATIONNUMBER, arg);
	}
	
	//#CM33985
	/**
	 * Set search value for station no.
	 * @param : arg[] Set string array to search
	 * @throws ReadWriteException Throw exception in case of invalid character
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(p_Prefix + STATIONNUMBER, arg);
	}
	
	//#CM33986
	/**
	 * Set search value for station no.
	 * @param : arg Set the string value to search
	 * @param : compcode [>], [<], [=] etc.,
	 * @throws ReadWriteException Throw exception in case of invalid character
	 */	
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(p_Prefix + STATIONNUMBER, arg, compcode);
	}
	
	//#CM33987
	/**
	 * Set search value for station no.
	 * @param : arg Set the string value to search
	 * @param : compcode [>], [<], [=] etc.,
	 * @param : left_paren no. of left parenthesis
	 * @param : right_paren no. of right parenthesis
	 * @param : and_or [AND] [OR] comparison condition
	 * @throws ReadWriteException Throw exception in case of invalid character
	 */	
	public void setStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(p_Prefix + STATIONNUMBER, arg, compcode, left_paren, right_paren, and_or);
	}

	//#CM33988
	/**
	 * Set sort order for station no.
	 * @param : num specify sort order
	 * @param : bool ascending (true) or descending (false)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(p_Prefix + STATIONNUMBER, num, bool);
	}

	//#CM33989
	/**
	 * Set group order for station no.
	 * @param : num Specify group fetch order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(p_Prefix + STATIONNUMBER, num);
	}

	//#CM33990
	/**
	 * acquire station no. info fetch order
	 * Pattern that sets empty characters in case of no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(p_Prefix + STATIONNUMBER, "");
	}

	//#CM33991
	/**
	 * acquire station no. info fetch order
	 * @param : compcode specify the grouping condition(MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(p_Prefix + STATIONNUMBER, compcode);
	}

	//#CM33992
	//------------------------------------------------------------
	//#CM33993
	// utility methods
	//#CM33994
	//------------------------------------------------------------
	//#CM33995
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASLocationSearchKey.java,v 1.2 2006/10/30 07:13:08 suresh Exp $" ;
	}
}

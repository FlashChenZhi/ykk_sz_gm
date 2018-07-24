//#CM41785
//$Id: ASLocation.java,v 1.2 2006/10/26 08:24:33 suresh Exp $
package jp.co.daifuku.wms.asrs.entity;

//#CM41786
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.AbstractEntity;

//#CM41787
/**
 * This class stores the station no. from search result<BR>
 * used from ASLocationSearchKey<BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- revision history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:24:33 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */
public class ASLocation
		extends AbstractEntity
{
	//#CM41788
	//------------------------------------------------------------
	//#CM41789
	// class variables (prefix '$')
	//#CM41790
	//------------------------------------------------------------
	//#CM41791
	//	private String	$classVar ;

	//#CM41792
	//------------------------------------------------------------
	//#CM41793
	// fields (upper case only)
	//#CM41794
	//------------------------------------------------------------
	//#CM41795
	/*
	 * table name: 
	 * station no. :                STATIONNUMBER       varchar2(16)
	 */

	//#CM41796
	/** table name definition */

	public static final String TABLE_NAME = "";

	//#CM41797
	/** column definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM41798
	//------------------------------------------------------------
	//#CM41799
	// properties (prefix 'p_')
	//#CM41800
	//------------------------------------------------------------
	//#CM41801
	//	private String	p_Name ;
	//#CM41802
	/**
	 * delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM41803
	//------------------------------------------------------------
	//#CM41804
	// instance variables (prefix '_')
	//#CM41805
	//------------------------------------------------------------
	//#CM41806
	//	private String	_instanceVar ;

	//#CM41807
	//------------------------------------------------------------
	//#CM41808
	// constructors
	//#CM41809
	//------------------------------------------------------------

	//#CM41810
	/**
	 * prepare column name list and generate instance
	 */
	public ASLocation()
	{
		super() ;
		prepare() ;
	}

	//#CM41811
	/**
	 * set value to station no.
	 * @param arg station no. to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM41812
	/**
	 * fetch station no.
	 * @return station no.
	 */
	public String getStationNumber()
	{
		return getValue(ASLocation.STATIONNUMBER).toString();
	}

	//#CM41813
	/**
	 * @see dbhandler.AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM41814
	//------------------------------------------------------------
	//#CM41815
	// package methods
	//#CM41816
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM41817
	//------------------------------------------------------------

	//#CM41818
	//------------------------------------------------------------
	//#CM41819
	// protected methods
	//#CM41820
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM41821
	//------------------------------------------------------------

	//#CM41822
	//------------------------------------------------------------
	//#CM41823
	// private methods
	//#CM41824
	//------------------------------------------------------------
	//#CM41825
	/**
	 * prepare column name list(SearchKey,AlterKey use)
	 * match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		//#CM41826
		// fetch a string name using getColumn
		//#CM41827
		// convert to string data type
		lst.add(STATIONNUMBER.toString());
	}

	//#CM41828
	//------------------------------------------------------------
	//#CM41829
	// utility methods
	//#CM41830
	//------------------------------------------------------------
	//#CM41831
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASLocation.java,v 1.2 2006/10/26 08:24:33 suresh Exp $" ;
	}	
}

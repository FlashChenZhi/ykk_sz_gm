//#CM33874
//$Id: ASInOutResultHandler.java,v 1.2 2006/10/30 07:13:09 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

//#CM33875
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.dbhandler.InOutResultHandler;
import jp.co.daifuku.wms.asrs.entity.ASInOutResult;

//#CM33876
/**
 * database handler for INOUTRESULT
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
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:13:09 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ASInOutResultHandler
		extends InOutResultHandler
{
	//#CM33877
	//------------------------------------------------------------
	//#CM33878
	// class variables (prefix '$')
	//#CM33879
	//------------------------------------------------------------
	//#CM33880
	//	private String	$classVar ;

	//#CM33881
	//------------------------------------------------------------
	//#CM33882
	// fields (upper case only)
	//#CM33883
	//------------------------------------------------------------
	//#CM33884
	//	public static final int FIELD_VALUE = 1 ;

	//#CM33885
	//------------------------------------------------------------
	//#CM33886
	// properties (prefix 'p_')
	//#CM33887
	//------------------------------------------------------------
	//#CM33888
	//	private String	p_Name ;

	//#CM33889
	//------------------------------------------------------------
	//#CM33890
	// instance variables (prefix '_')
	//#CM33891
	//------------------------------------------------------------
	//#CM33892
	//	private String	_instanceVar ;

	//#CM33893
	//------------------------------------------------------------
	//#CM33894
	// constructors
	//#CM33895
	//------------------------------------------------------------
	//#CM33896
	/**
	 * specify database connection and create instance
	 * @param conn database connection object
	 */
	public ASInOutResultHandler(Connection conn)
	{
		super(conn) ;
	}


	//#CM33897
	//------------------------------------------------------------
	//#CM33898
	// public methods
	//#CM33899
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM33900
	//------------------------------------------------------------


	//#CM33901
	//------------------------------------------------------------
	//#CM33902
	// accessor methods
	//#CM33903
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM33904
	//------------------------------------------------------------


	//#CM33905
	//------------------------------------------------------------
	//#CM33906
	// package methods
	//#CM33907
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM33908
	//------------------------------------------------------------


	//#CM33909
	//------------------------------------------------------------
	//#CM33910
	// protected methods
	//#CM33911
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM33912
	//------------------------------------------------------------
	//#CM33913
	/**
	 * @see AbstractDBHandler#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new ASInOutResult()) ;
	}


	//#CM33914
	//------------------------------------------------------------
	//#CM33915
	// private methods
	//#CM33916
	//------------------------------------------------------------


	//#CM33917
	//------------------------------------------------------------
	//#CM33918
	// utility methods
	//#CM33919
	//------------------------------------------------------------
	//#CM33920
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASInOutResultHandler.java,v 1.2 2006/10/30 07:13:09 suresh Exp $" ;
	}


}

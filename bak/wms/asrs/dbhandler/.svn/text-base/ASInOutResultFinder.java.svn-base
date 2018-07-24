//#CM33857
//$Id: ASInOutResultFinder.java,v 1.2 2006/10/30 07:13:10 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

//#CM33858
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.base.dbhandler.InOutResultFinder;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.asrs.entity.ASInOutResult;

//#CM33859
/**
 * This class operates the AS/RS Operation Result info table (InOutResult)<BR>
 * Describe SQL that can't be used with normal handler
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:13:10 $
 * @author  $Author: suresh $
 */
public class ASInOutResultFinder extends InOutResultFinder
{
	//#CM33860
	// Class filelds -----------------------------------------------

	//#CM33861
	// Class method --------------------------------------------------

	//#CM33862
	// Constructors --------------------------------------------------
	//#CM33863
	/**
	 * specify database connection and create instance
	 * @param conn database connection object
	 */
	public ASInOutResultFinder(Connection conn)
	{
		super(conn) ;
	}

	//#CM33864
	//------------------------------------------------------------
	//#CM33865
	// protected methods
	//#CM33866
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM33867
	//------------------------------------------------------------
	//#CM33868
	/**
	 * @see AbstractDBFinder#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new ASInOutResult()) ;
	}

	//#CM33869
	/**
	 * returns the version of this class
	 * @return version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:13:10 $") ;
	}

	//#CM33870
	// Package methods -----------------------------------------------

	//#CM33871
	// Protected methods ---------------------------------------------

	//#CM33872
	// Private methods -----------------------------------------------

}
//#CM33873
//end of class


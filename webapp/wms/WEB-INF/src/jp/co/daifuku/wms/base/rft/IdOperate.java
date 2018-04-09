// $Id: IdOperate.java,v 1.2 2006/11/14 06:09:08 suresh Exp $
package jp.co.daifuku.wms.base.rft;

import java.sql.Connection;

//#CM701809
/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM701810
/**
 * The base class of the IdXXXXOperate class. 
 * Derive this class without fail and make the IdXXXXOperate class. 
 *
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:08 $
 * @author $Author: suresh $
 */
public class IdOperate
{
	//#CM701811
	// Class fields----------------------------------------------------
	//#CM701812
	// Class variables -----------------------------------------------
	//#CM701813
	/**
	 * Connection
	 */
	protected Connection wConn = null;

	//#CM701814
	// Public methods ------------------------------------------------
	//#CM701815
	/**
	 * Acquire <code>Connection</code> for the Data base connection. 
	 * @return	Database connection
	 */
	public Connection getConnection()
	{
		return wConn;
	}

	//#CM701816
	/**
	 * Set <code>Connection</code> for the Data base connection. 
	 * @param connection	Database connection
	 */
	public void setConnection(Connection connection)
	{
		wConn = connection;
	}
	
	//#CM701817
	// Package methods ----------------------------------------------
	//#CM701818
	// Protected methods --------------------------------------------
	//#CM701819
	// Private methods -----------------------------------------------

}
//#CM701820
//end of class

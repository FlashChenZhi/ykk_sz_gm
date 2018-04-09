// $Id: DatabaseHandler.java,v 1.2 2006/11/15 04:25:38 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708458
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection ;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.AlterKey;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.EntityHandler;
import jp.co.daifuku.wms.base.common.SearchKey;

//#CM708459
/**
 * The interface to keep the class in the data base, and to acquire information from the data base and to generate the instance. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:38 $
 * @author  $Author: kamala $
 */
public interface DatabaseHandler
		extends EntityHandler
{
	//#CM708460
	// Public methods ------------------------------------------------
	//#CM708461
	/**
	 * Set <code>Connection</code> for the data base connection. 
	 * @param conn Connection to be set
	 */
	public void setConnection(Connection conn) ;

	//#CM708462
	/**
	 * Acquire <code>Connection</code> for the data base connection. 
	 * @return <code>Connection</code> being maintained
	 */
	public Connection getConnection() ;

	//#CM708463
	/**
	 * Retrieve information on the data base based on the parameter, and return the object. 
	 * @param key Key for retrieval
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public Entity[] find(SearchKey key)
			throws ReadWriteException ;

	//#CM708464
	/**
	 * Retrieve information on the data base based on the parameter, and return the object. 
	 * Retrieval information must be Primary (Only one). NoPrimaryException is notified when existing by the plural. 
	 * 	 * @param key Key for retrieval
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NoPrimaryException
	 * @throws InvalidStatusException
	 */
	public Entity findPrimary(SearchKey key)
			throws ReadWriteException,
				NoPrimaryException,
				InvalidStatusException ;

	//#CM708465
	/**
	 * Retrieve information on the data base based on the parameter, and return the object. 
	 * Lock acquired information. 
	 * 	 * @param key Key for retrieval
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws InvalidStatusException
	 */
	public Entity[] findForUpdate(SearchKey key)
			throws ReadWriteException,
				InvalidStatusException ;

	//#CM708466
	/**
	 * Retrieve information on the data base based on the parameter, and return the object. 
	 * Retrieval information must be Primary (Only one). NoPrimaryException is notified when existing by the plural. 
	 * Lock acquired information. 
	 * 	 * @param key Key for retrieval
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NoPrimaryException
	 * @throws InvalidStatusException
	 */
	public Entity findPrimaryForUpdate(SearchKey key)
			throws ReadWriteException,
				NoPrimaryException,
				InvalidStatusException ;

	//#CM708467
	/**
	 * Retrieve information on the data base based on the parameter, and return the number of results. 
	 * @param key Key for retrieval
	 * @return Retrieval results qty
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public int count(SearchKey key)
			throws ReadWriteException ;

	//#CM708468
	/**
	 * Make the database new information.
	 * @param tgt Entity instance with made information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws DataExistsException It has already been notified when same information has registered in the data base. 
	 */
	public void create(Entity tgt)
			throws ReadWriteException,
				DataExistsException ;

	//#CM708469
	/**
	 * Change information on the data base. Acquire the content of the change and the change condition from SearchKey. 
	 * @param key Entity instance with changed information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException It is notified when information which should be changed is not found. 
	 * @throws InvalidDefineException It is notified when the content of the update is not set. 
	 */
	public void modify(AlterKey key)
			throws ReadWriteException,
				NotFoundException,
				InvalidDefineException ;

	//#CM708470
	/**
	 * Delete information on the entity instance passed by the parameter from the data base. 
	 * @param tgt Entity instance with deleted information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException It is notified when information which should be deleted is not found. 
	 */
	public void drop(Entity tgt)
			throws ReadWriteException,
				NotFoundException ;

	//#CM708471
	/**
	 * Delete information which agrees with the key passed by the parameter from the data base. 
	 * @param key Key to deleted information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException It is notified when information which should be deleted is not found. 
	 */
	public void drop(SearchKey key)
			throws ReadWriteException,
				NotFoundException ;

	//#CM708472
	// Package methods -----------------------------------------------

	//#CM708473
	// Protected methods ---------------------------------------------

	//#CM708474
	// Private methods -----------------------------------------------

}
//#CM708475
//end of interface


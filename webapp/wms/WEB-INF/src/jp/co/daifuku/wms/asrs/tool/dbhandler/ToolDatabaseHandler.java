// $Id: ToolDatabaseHandler.java,v 1.2 2006/10/30 02:17:19 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47590
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
//#CM47591
/**<en>
 * This is an interface which stores/retrieves classes to/from database to
 * generate instances.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:19 $
 * @author  $Author: suresh $
 </en>*/
public interface ToolDatabaseHandler extends ToolEntityHandler
{


	//#CM47592
	// Public methods ------------------------------------------------
	//#CM47593
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn) ;

	//#CM47594
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection() ;
	

	//#CM47595
	/**<en>
	 * Search information in database based on the parameter, and return the object.
	 * @param key :Key for search
	 * @return    :the array of created objects
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException ;

	//#CM47596
	/**<en>
	 * Search information in database based on the parameter, and return the number of result data.
	 * @param key :Key for search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException ;

	//#CM47597
	/**<en>
	 * Create the information in database.
	 * @param tgt :entity instance which preserves the information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if the same data is already registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException ;

	//#CM47598
	/**<en>
	 * Modify the information in database to the entity information passed through parameter.
	 * @param tgt :entity instance which preserves the information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException ;

	//#CM47599
	/**<en>
	 * Modify the information in database. The contents and conditions of the modification
	 * will be gained by ToolSearchKey.
	 * @param tgt :entity instance which preserves the information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException ;

	//#CM47600
	/**<en>
	 * Delete from database the information passed through parameter.
	 * @param tgt :entity instance which preserves the information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException ;

	//#CM47601
	/**<en>
	 * Delete from database the information that match the key passed through parameter.
	 * @param key :key for the information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException ;


	//#CM47602
	// Package methods -----------------------------------------------

	//#CM47603
	// Protected methods ---------------------------------------------

	//#CM47604
	// Private methods -----------------------------------------------

}
//#CM47605
//end of interface


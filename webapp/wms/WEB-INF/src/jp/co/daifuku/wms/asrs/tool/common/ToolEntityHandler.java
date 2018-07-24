// $Id: ToolEntityHandler.java,v 1.2 2006/10/30 01:40:55 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.common ;

//#CM46714
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */ 
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolAlterKey;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolSearchKey;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
//#CM46715
/**<en>
 * This is an interface which stores the instanc, and generages the instance by
 * retrieving the stored information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 01:40:55 $
 * @author  $Author: suresh $
 </en>*/
public interface ToolEntityHandler
{
	//#CM46716
	// Class variables -----------------------------------------------
	//#CM46717
	/**<en>
	 * This is used in LogWrite when Exception occurs.
	 </en>*/
	public StringWriter wSW = new StringWriter();
	//#CM46718
	/**<en>
	 * This is used in LogWrite when Exception occurs.
	 * <pre>
	 * 	Ex/
	 * 		try
	 * 		{
	 * 			String name = "aiueo";
	 * 			int    val  = Integer.parseInt( name );
	 * 		}
	 * 		catch (Exception e)
	 * 		{
	 * 			e.printStackTrace(wPW);
	 * 			Object[] tObj = new Object[1] ;
	 * 			tObj[0] = wSW.toString();
	 * 			RmiMsgLogClient.write(0, LogMessage.F_ERROR, "ItemHandler", tObj);
	 * 		}
	 * </pre>
	 </en>*/
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM46719
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 * 	Ex/ String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM46720
	// Public methods ------------------------------------------------
	//#CM46721
	/**<en>
	 * Search data based on the parameters, then return the object.
	 * @param key :Key for the search
	 * @return    :the array of created object
	 * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
	 * @throws ReadWriteException :Notifies if reading from the storage system failed.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws NotFoundException, ReadWriteException ;

	//#CM46722
	/**<en>
	 * Search data based on the parameters, then return the number of results.
	 * @param key :Key for the search
	 * @return    :number of search results
	 * @throws ReadWriteException :Notifies if reading from the storage system failed.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException ;

	//#CM46723
	/**<en>
	 * Store the new information.
	 * @param tgt :entity instance which has data to create new data from
	 * @throws DataExistsException :Notifies if the same data has already been registered.
	 * @throws ReadWriteException  :Notifies if writing in the storage system failed.
	 </en>*/
	public void create(ToolEntity tgt) throws DataExistsException, ReadWriteException ;

	//#CM46724
	/**<en>
	 * Modify the stored data to the entity inforamtion passed through parameter.
	 * @param tgt :entity instance which has data to modify
	 * @throws NotFoundException  :Notifies if target data to modify cannot be found.
	 * @throws ReadWriteException :Notifies if writing/reading of the storage system failed.
	 </en>*/
	public void modify(ToolEntity tgt) throws NotFoundException, ReadWriteException ;

	//#CM46725
	/**<en>
	 * Modify teh stored information. The contents and condition of modification will be 
	 * obtained by SearchKey.
	 * @param tgt :entity instance which has data to modify
	 * @throws ReadWriteException  :Notifies if writing/reading of the storage system failed.
	 * @throws NotFoundException   :Notifies if data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if  contents of the update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws NotFoundException, ReadWriteException, InvalidDefineException ;

	//#CM46726
	/**<en>
	 * Delete the information of entity instance passed through parameter.
	 * @param tgt :entity instance which preserves target data to delete.
	 * @throws NotFoundException  :Notifies if target data to delete cannot be found.
	 * @throws ReadWriteException :Notifies if deleting of data in storage system failed.
	 </en>*/
	public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException ;

	//#CM46727
	/**<en>
	 * Delete the information that matches the key passed through parameter.
	 * @param key :Key of target data to delete
	 * @throws NotFoundException  :Notifies if target data to delete cannot be found.
	 * @throws ReadWriteException :Notifies if deleting of data in storage system failed.
	 </en>*/
	public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException ;


	//#CM46728
	// Package methods -----------------------------------------------

	//#CM46729
	// Protected methods ---------------------------------------------

	//#CM46730
	// Private methods -----------------------------------------------

}
//#CM46731
//end of interface


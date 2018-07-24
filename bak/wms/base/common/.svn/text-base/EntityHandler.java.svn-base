// $Id: EntityHandler.java,v 1.2 2006/11/07 06:00:42 suresh Exp $
package jp.co.daifuku.wms.base.common;

//#CM642744
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.* ;

import jp.co.daifuku.common.* ;

//#CM642745
/**
 * The interface to keep the instance, and to acquire kept information and to generate the instance. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:00:42 $
 * @author  $Author: suresh $
 */
public interface EntityHandler
{
	//#CM642746
	// Class variables -----------------------------------------------
	//#CM642747
	/**
	 * Use it for LogWrite when Exception is generated. 
	 */
	public StringWriter wSW = new StringWriter() ;

	//#CM642748
	/**
	 * Use it for LogWrite when Exception is generated. 
	 * <pre>
	 * 	Ex.
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
	 */
	public PrintWriter wPW = new PrintWriter(wSW) ;

	//#CM642749
	/**
	 * Delimiter
	 * The delimiter of the parameter of Message of MessageDef When Exception is generated.
	 * 	Ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM642750
	// Public methods ------------------------------------------------
	//#CM642751
	/**
	 * Retrieve information based on the parameter, and return Object. 
	 * @param key Key for retrieval
	 * @return Entity Array
	 * @throws NotFoundException It is notified when information is not found since it retrieves it. 
	 * @throws ReadWriteException It is notified when failing in reading from keeping information. 
	 */
	public Entity[] find(SearchKey key)
			throws NotFoundException,
				ReadWriteException ;

	//#CM642752
	/**
	 * Retrieve information based on the parameter, and return the result qty.
	 * @param key Key for retrieval
	 * @return Retrieval result qty
	 * @throws ReadWriteException It is notified when failing in reading from the keeping mechanism. 
	 */
	public int count(SearchKey key)
			throws ReadWriteException ;

	//#CM642753
	/**
	 * Keep new information. 
	 * @param tgt Entity instance with made information
	 * @throws DataExistsException It has already been notified when same information has registered. 
	 * @throws ReadWriteException It is notified when failing in writing in the keeping mechanism. 
	 */
	public void create(Entity tgt)
			throws DataExistsException,
				ReadWriteException ;

	//#CM642754
	/**
	 * Change kept information. Acquire the content of the change and the change condition from SearchKey. 
	 * @param key Entity instance with changed information
	 * @throws ReadWriteException It is notified when failing in writing or reading in the keeping mechanism. 
	 * @throws NotFoundException It is notified when information which should be changed is not found. 
	 * @throws InvalidDefineException It is notified when Content of update is not set. 
	 */
	public void modify(AlterKey key)
			throws NotFoundException,
				ReadWriteException,
				InvalidDefineException ;

	//#CM642755
	/**
	 * Deletes information on the entity instance passed by the parameter. 
	 * @param tgt Entity instance with information which does Deletion
	 * @throws NotFoundException It is notified when information which should do Deletion is not found. 
	 * @throws ReadWriteException It is notified when failing in Deletion from the keeping mechanism. 
	 */
	public void drop(Entity tgt)
			throws NotFoundException,
				ReadWriteException ;

	//#CM642756
	/**
	 * Deletes information which matches with the key passed by the parameter. 
	 * @param key Key to information which does Deletion
	 * @throws NotFoundException It is notified when information which should do Deletion is not found. 
	 * @throws ReadWriteException It is notified when failing in Deletion from the keeping mechanism. 
	 */
	public void drop(SearchKey key)
			throws ReadWriteException,
				NotFoundException ;


	//#CM642757
	// Package methods -----------------------------------------------

	//#CM642758
	// Protected methods ---------------------------------------------

	//#CM642759
	// Private methods -----------------------------------------------

}
//#CM642760
//end of interface


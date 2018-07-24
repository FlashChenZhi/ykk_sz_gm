// $Id: EntityFinder.java,v 1.2 2006/11/07 06:00:26 suresh Exp $
package jp.co.daifuku.wms.base.common;

//#CM642731
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */ 
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;

//#CM642732
/**
 * The interface to keep the instance, and to acquire kept information and to generate the instance. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:00:26 $
 * @author  $Author: suresh $
 */
public interface EntityFinder
{
	//#CM642733
	// Class variables -----------------------------------------------
	//#CM642734
	/**
	 * Use it for LogWrite when Exception is generated. 
	 */
	public StringWriter wSW = new StringWriter();
	//#CM642735
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
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM642736
	/**
	 * Delimiter
	 * The delimiter of the parameter of Message of MessageDef When Exception is generated.
	 * 	Ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM642737
	// Public methods ------------------------------------------------
	//#CM642738
	/**
	 * Retrieve information based on the parameter, and return Object. 
	 * @param key Key for retrieval
	 * @return Retrieval result qty
	 * @throws ReadWriteException It is notified when failing in reading from keeping information. 
	 */
	public int search(SearchKey key) throws ReadWriteException ;

	//#CM642739
	/**
	 * Make the retrieval result of the data base entity Array and return it. 
	 * @param  start Starting position for which retrieval result is specified
	 * @param  end End position in which retrieval result was specified
	 * @return Entity Array
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws InvalidStatusException Notify when abnormality is found within the specified range of the retrieval result. 
	 */
	public Entity[] getEntities(int start, int end) throws ReadWriteException, InvalidStatusException;

	//#CM642740
	// Package methods -----------------------------------------------

	//#CM642741
	// Protected methods ---------------------------------------------

	//#CM642742
	// Private methods -----------------------------------------------

}
//#CM642743
//end of interface


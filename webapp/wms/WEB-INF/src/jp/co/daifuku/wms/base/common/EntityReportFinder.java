// $Id: EntityReportFinder.java,v 1.2 2006/11/07 06:00:59 suresh Exp $
package jp.co.daifuku.wms.base.common;

//#CM642761
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

//#CM642762
/**
 * The interface to keep the instance, and to acquire kept information and to generate the instance. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:00:59 $
 * @author  $Author: suresh $
 */
public interface EntityReportFinder
{
	//#CM642763
	// Class variables -----------------------------------------------
	//#CM642764
	/**
	 * Use it for LogWrite when Exception is generated. 
	 */
	public StringWriter wSW = new StringWriter();
	//#CM642765
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

	//#CM642766
	/**
	 * Delimiter
	 * The delimiter of the parameter of Message of MessageDef When Exception is generated.
	 * 	Ex. String msginfo = "9000000" + wDelim + "Palette" + wDelim + "Stock" ;
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM642767
	// Public methods ------------------------------------------------
	//#CM642768
	/**
	 * Retrieve information based on the parameter, and return the Object qty. 
	 * @param key Key for retrieval
	 * @return Retrieval result qty
	 * @throws ReadWriteException It is notified when failing in reading from keeping information. 
	 */
	public int search(SearchKey key) throws ReadWriteException ;

	//#CM642769
	/**
	 * Make the retrieval result of the data base entity Array and return it. 
	 * @param  count Acquire retrieval results qty
	 * @return Entity Array
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws InvalidStatusException Notify when abnormality is found within the specified range of the retrieval result. 
	 */
	public Entity[] getEntities(int count) throws ReadWriteException, InvalidStatusException;

	//#CM642770
	// Package methods -----------------------------------------------

	//#CM642771
	// Protected methods ---------------------------------------------

	//#CM642772
	// Private methods -----------------------------------------------

}
//#CM642773
//end of interface


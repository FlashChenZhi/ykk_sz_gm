// $Id: ToolDatabaseFinder.java,v 1.2 2006/10/30 02:17:19 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47572
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolEntityHandler;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.ReadWriteException;
//#CM47573
/**<en>
 * This is an interface which stores/retrieves classes to/from database in order to
 * generate instances.
 * If displaying of the data list on the screen is required, please implement this class,
 * and implement a class which is to return <CODE>Entity</CODE>.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:19 $
 * @author  $Author: suresh $
 </en>*/
public interface ToolDatabaseFinder extends ToolEntityHandler
{
	//#CM47574
	// Class fields --------------------------------------------------
	//#CM47575
	/**<en>
	 * LISTBOX :upper limit number of search results  
	 </en>*/
	public static final int MAXDISP = Integer.parseInt(ToolParam.getParam("MAX_NUMBER_OF_DISP_LISTBOX"));

	//#CM47576
	// Public methods ------------------------------------------------
	//#CM47577
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
//#CM47578
//	public void setConnection(Connection conn) ;

	//#CM47579
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> curently preserved
	 </en>*/

//#CM47580
//	public Connection getConnection() ;
	//#CM47581
	/**<en>
	 * Generate a statement and open a cursor.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/

	public void open() throws ReadWriteException ;

	//#CM47582
	/**<en>
	 * Return the result of database search in form of entity array.
	 * @param  start :specified start position of search result
	 * @param  start :specified end position of search result
	 * @return :entity array
	 * @throws ReadWriteException     :Notifies if error occured in connection with database. 
	 * @throws InvalidStatusException :Notifies if error was found in the scope of search specified.
	 </en>*/
	public ToolEntity[] getEntitys(int start, int end) throws ReadWriteException, InvalidStatusException ;

	//#CM47583
	/**<en>
	 * Search database and return the entity array.
	 * @return :the entity array
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] next() throws ReadWriteException ;

	//#CM47584
	/**<en>
	 * Search database and return the entity array.
	 * @return :the entity array
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] back() throws ReadWriteException ;

	//#CM47585
	/**<en>
	 * Close the statement.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public void close() throws ReadWriteException ;


	//#CM47586
	// Package methods -----------------------------------------------

	//#CM47587
	// Protected methods ---------------------------------------------

	//#CM47588
	// Private methods -----------------------------------------------

}
//#CM47589
//end of interface


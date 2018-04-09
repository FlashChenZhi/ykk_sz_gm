// $Id: ZoneSelector.java,v 1.2 2006/10/26 08:30:27 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM43188
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;

//#CM43189
/**<en>
 * This is an interface used to obtain and store the Zone instance.
 * Zone is the control unit of space which is gained by deviding the space of warehouse according to
 * a certain rule.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:30:27 $
 * @author  $Author: suresh $
 </en>*/
public interface ZoneSelector
{
	//#CM43190
	/**<en>
	 * This is in LogWrite when Exception occured. 
	 </en>*/
	public StringWriter wSW = new StringWriter();

	//#CM43191
	/**<en>
	 * This is in LogWrite when Exception occured. 
	 </en>*/
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM43192
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;
	
	//#CM43193
	// Public methods ------------------------------------------------


	//#CM43194
	// Package methods -----------------------------------------------
	//#CM43195
	/**<en>
	 * Search the zone and return the result.
	 * If there are more than one possible zones, return them in fprm of array according to the 
	 * prioritized order. <br>
	 * This method is prepared for <code>WareHouse</code> to call.
	 * If actual location search is wanted, please use <code>WareHouse</code>.
	 * @param plt :Palette instance subject to zone search
	 * @param wh  :Warehouse instance subject to zone search
	 * @return    :Object of <code>Zone</code> created based on parameter
	 * @throws ReadWriteException :Notifies if it failed to access data.
	 </en>*/
	public Zone[] select(Palette plt, WareHouse wh) throws ReadWriteException, InvalidDefineException ;

	//#CM43196
	/**<en>
	 * Search zone and return its result.
	 * If there are more than one possible zones, return them in fprm of array according to the 
	 * prioritized order. <br>
	 * This will be used when counting the empty locations.
	 * @param itemkey  :ItemKey subject to zone search
	 * @param height :load height subject to zone search
	 * @param wh     : Warehouse instance of the warehouse subject to zone search
	 * @return       :Object of <code>Zone</code> created based on parameter
	 * @throws ReadWriteException :Notifies if it failed to access data.
	 </en>*/
	public Zone[] selectcount(String itemkey, int height, WareHouse wh) throws ReadWriteException, InvalidDefineException ;

	//#CM43197
	// Protected methods ---------------------------------------------

	//#CM43198
	// Private methods -----------------------------------------------

}
//#CM43199
//end of interface


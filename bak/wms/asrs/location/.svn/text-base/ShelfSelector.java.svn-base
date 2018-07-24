// $Id: ShelfSelector.java,v 1.2 2006/10/26 08:33:10 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42996
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;

//#CM42997
/**<en>
 * This is an interface for the location search.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:33:10 $
 * @author  $Author: suresh $
 </en>*/
public interface ShelfSelector
{
	//#CM42998
	// Class fields --------------------------------------------------
	//#CM42999
	// Class variables -----------------------------------------------
	//#CM43000
	/**<en>
	 * This is used in LogWrite when Exception occurs.
	 </en>*/
	public StringWriter wSW = new StringWriter();

	//#CM43001
	/**<en>
	 * This is used in LogWrite when Exception occurs.
	 </en>*/
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM43002
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM43003
	// Public methods ------------------------------------------------
	//#CM43004
	/**<en>
	 * Searches the empty location based on the pallet which preserves information of 
	 * specified warehouse and sending station.
	 * @param plt :pallet subject to empty location search
	 * @param wh  :warehouse subject to empty location search
	 * @return    :empty location searched
	 * @throws ReadWriteException:Notifies if exception occured in data I/O process.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
	 </en>*/
	public Shelf select(Palette plt, WareHouse wh) throws ReadWriteException, InvalidDefineException;

	//#CM43005
	/**<en>
	 * Sets <code>ZoneSelector</code> instance for use in zone search.
	 * @param zs :<code>ZoneSelector</code> used in location search
	 </en>*/
	public void setZoneSelector(ZoneSelector zs) ;

	//#CM43006
	/**<en>
	 * Retrieves <code>ZoneSelector</code> instance for use in zone search.
	 * @return :<code>ZoneSelector</code> set
	 </en>*/
	public ZoneSelector getZoneSelector() ;

	//#CM43007
	// Package methods -----------------------------------------------

	//#CM43008
	// Protected methods ---------------------------------------------

	//#CM43009
	// Private methods -----------------------------------------------

}
//#CM43010
//end of interface


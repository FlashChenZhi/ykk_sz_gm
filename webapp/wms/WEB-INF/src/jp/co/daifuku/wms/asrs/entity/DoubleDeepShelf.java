// $Id: DoubleDeepShelf.java,v 1.2 2006/10/26 08:12:33 suresh Exp $
package jp.co.daifuku.wms.asrs.entity;

//#CM42019
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.entity.Shelf;

//#CM42020
/**
 * The shelf class which added necessary information to operate it double deep.
 * Have information on the position (this side and interior) in the bank and the pair shelves in addition to information and the operation that the Shelf instance maintains.
 * When an empty shelf is retrieved, pair shelf information on is used.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/1</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>M.INOUE</TD><TD>Double deep addition</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:12:33 $
 * @author  $Author: suresh $
 */
public class DoubleDeepShelf extends Shelf
{
	//#CM42021
	// Class fields --------------------------------------------------

	//#CM42022
	// Class variables -----------------------------------------------

	//#CM42023
	/**
	 * Maintain the state of the shelf of the pair shelf.
	 */
	private int wPairPresence ;

	//#CM42024
	// Class method --------------------------------------------------
	//#CM42025
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:12:33 $") ;
	}

	//#CM42026
	// Constructors --------------------------------------------------
	//#CM42027
	/**
	 * Make < code>DoubleDeepShelf</code > instance to manage the shelf newly.
	 * @param snum  Station number of shelf(shelf number)
	 */
	public DoubleDeepShelf(String snum)
	{
		super() ;
		setStationNumber(snum);
	}

	//#CM42028
	// Public methods ------------------------------------------------

	//#CM42029
	/**
	 * Set the state of the shelf of the pair shelf.
	 * @param pre  State of shelf of pair shelf
     * @throws InvalidStatusException Notify when the content of pre is outside the range.
	 */
	public void setPairPresence(int pre) throws InvalidStatusException
	{
		//#CM42030
		// Check on state of shelf
		switch(pre)
		{
			//#CM42031
			// List of correct state of shelf
			case Shelf.PRESENCE_EMPTY:
			case Shelf.PRESENCE_STORAGED:
			case Shelf.PRESENCE_RESERVATION:
				wPairPresence = pre;
				break ;
				
			//#CM42032
			// Do not generate the exception, and do not change the state of the shelf when you set a state of the shelf not correct.
			default:
				//#CM42033
				// 6006009=The value was specified outside the range. It is not possible to set it. Class={0} Variable={1} Value={2}
				Object[] tObj = new Object[3] ;
				tObj[0] = this.getClass().getName() ;
				tObj[1] = "wPairPresence";
				tObj[2] = Integer.toString(pre) ;
				String classname = (String)tObj[0];
				RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
				throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM42034
	/**
	 * Acquire the state of the shelf of the pair shelf.
	 * @return   State of shelf of pair shelf
	 */
	public int getPairPresence()
	{
		return wPairPresence;
	}

	//#CM42035
	/**
	 * Return the item of pair shelf information and the value by the character string expression.
	 * @return Pair shelf information
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		try
		{
			buf.append(super.toString());
			buf.append("\nPair Presence:" + wPairPresence) ;
		}
		catch (Exception e)
		{
		}
		
		return (buf.toString()) ;
	}

	//#CM42036
	// Package methods -----------------------------------------------

	//#CM42037
	// Protected methods ---------------------------------------------

	//#CM42038
	// Private methods -----------------------------------------------

}
//#CM42039
//end of class


//#CM49332
//$Id: Aisle.java,v 1.2 2006/10/30 02:33:27 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49333
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidStatusException;

//#CM49334
/**<en>
 * This class is used to control aisles. The aisles inherits the Station class and 
 * preserves the station no. to identify the aisle machine no.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:27 $
 * @author  $Author: suresh $
 </en>*/
public class Aisle extends Station
{
	//#CM49335
	// Class fields --------------------------------------------------
	//#CM49336
	/**<en>
	 * Field of fork type (single deep)
	 </en>*/
	public static final int    SINGLE_DEEP = 0 ;

	//#CM49337
	/**<en>
	 * Field of fork type (double deep)
	 </en>*/
	public static final int    DOUBLE_DEEP = 1 ;

	//#CM49338
	// Class variables -----------------------------------------------
	//#CM49339
	/**<en>
	 * Preserve the aisle no.
	 </en>*/
	private int wAisleNumber ;

	//#CM49340
	/**<en>
	 * Preserve the fork type.
	 </en>*/
	private int wDoubleDeepKind ;

	//#CM49341
	/**<en>
	 * Preserve the end-use bank.
	 </en>*/
	private int wLastUsedBank ;

	//#CM49342
	// Class method --------------------------------------------------

	//#CM49343
	// Constructors --------------------------------------------------
	//#CM49344
	/**<en>
	 * Create the <code>Aisle</code> instance in order to control the aisle information.
	 * The aisle station no. will set its own station no.
	 * @param snum  :aisle station no.
	 * @see StationFactory
	 </en>*/
	public Aisle(String snum)
	{
		super(snum) ;
		wAisleStationNumber = snum;
	}

	//#CM49345
	// Public methods ------------------------------------------------
	//#CM49346
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:27 $") ;
	}

	//#CM49347
	/**<en>
	 * Set the aisle no.
	 * @param  aile :aisle no.
	 </en>*/
	public void setAisleNumber(int aile)
	{
		wAisleNumber = aile ;
	}

	//#CM49348
	/**<en>
	 * Return the aisle no.
	 * @return   :aisle no.
	 </en>*/
	public int getAisleNumber()
	{
		return (wAisleNumber) ;
	}

	//#CM49349
	/**<en>
	 * Set the fork type.
	 * @param  type :fork type
     * @throws InvalidStatusException :Notifies is the contents of type is invalid.
	 </en>*/
	public void setDoubleDeepKind(int type) throws InvalidStatusException
	{
		//#CM49350
		//<en> Check the status.</en>
		switch(type)
		{
			//#CM49351
			//<en> List of correct status</en>
			case SINGLE_DEEP:
			case DOUBLE_DEEP:
				wDoubleDeepKind = type;
				break ;
				
			//#CM49352
			//<en> If incorrect status is to set, it lets the exception occurs; it will not modify the status.</en>
			default:
				//#CM49353
				//<en> This station status is undefined.</en>
				throw new InvalidStatusException() ;
		}
	}

	//#CM49354
	/**<en>
	 * Retrieve the fork type.
	 * @return   :fork type
	 </en>*/
	public int getDoubleDeepKind()
	{
		return wDoubleDeepKind;
	}

	//#CM49355
	/**<en>
	 * Set the end-use bank.
	 * @param  bank :end-use bank
	 </en>*/
	public void setLastUsedBank(int bank)
	{
		wLastUsedBank = bank ;
	}

	//#CM49356
	/**<en>
	 * Return the end-use bank.
	 * @return   :end-use bank
	 </en>*/
	public int getLastUsedBank()
	{
		return (wLastUsedBank) ;
	}

	//#CM49357
	/**<en>
	 * Retrieve the aisle station no. available for sotrage/retrieval.
	 * If it is the Aisle class, it will return the own station no.
	 * @return :the aisle station no. available for sotrage/retrieval
	 </en>*/
	public String getAisleStationNumber()
	{
		return wNumber;
	}

	//#CM49358
	/**<en>
	 * Return the string representation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		buf.append("\nStation Number:" + wNumber) ;
		buf.append("\nWarehouseStationNumber:" + wWarehouseStationNumber) ;
		buf.append("\nAisleNumber:" + wAisleNumber) ;
		buf.append("\nDoubleDeepKind:" + wDoubleDeepKind) ;
		buf.append("\nStatus:" + wStatus) ;
		buf.append("\nInventoryCheckFlag:" + wInventoryCheckFlag) ;
		buf.append("\nLastUsedBank:" + wLastUsedBank) ;
		buf.append("\nAisleStationNumber:" + wAisleStationNumber) ;

		return (buf.toString()) ;
	}

	//#CM49359
	// Package methods -----------------------------------------------

	//#CM49360
	// Protected methods ---------------------------------------------

	//#CM49361
	// Private methods -----------------------------------------------

}
//#CM49362
//end of class


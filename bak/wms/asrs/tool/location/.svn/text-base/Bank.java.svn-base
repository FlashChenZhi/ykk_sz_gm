// $Id: Bank.java,v 1.2 2006/10/30 02:33:26 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49421
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

//#CM49422
/**<en>
 * This class controls the bank information. This will be used in bank designation process when 
 * searching the empty locations. 
 * The bank information preserves data such as the warehouse and aisle station the bank belong to,
 * the bank no. and the number of empty locations in the bank.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:26 $
 * @author  $Author: suresh $
 </en>*/
public class Bank extends ToolEntity
{
	//#CM49423
	// Class fields --------------------------------------------------
	//#CM49424
	/**<en>
	 * Field of rear/front bank (front)
	 </en>*/
	public static final int NEAR = 0 ;

	//#CM49425
	/**<en>
	 * Field of rear/front bank  (rear bank)
	 </en>*/
	public static final int FAR   = 1 ;

	//#CM49426
	// Class variables -----------------------------------------------
	//#CM49427
	/**<en>
	 * Warehouse station no.
	 </en>*/
	protected String wWareHouseStationNumber;

	//#CM49428
	/**<en>
	 * Aisle station no.
	 </en>*/
	protected String wAisleStationNumber;

	//#CM49429
	/**<en>
	 * Bank no.
	 </en>*/
	protected int wBank ;

	//#CM49430
	/**<en>
	 * Paired bank no. -set of 2 bank numbers used in double deep layout.
	 </en>*/
	protected int wPairBank ;

	//#CM49431
	/**<en>
	 * The segment to distinguish the front bank/ rear bank
	 * In single deep layout, only the front banks will always be used.
	 </en>*/
	protected int wSide ;

	//#CM49432
	/**<en>
	 * Number of empty locations in the bank
	 </en>*/
	private int wEmptyCount = 0 ;

	//#CM49433
	// Class method --------------------------------------------------

	//#CM49434
	// Constructors --------------------------------------------------
	//#CM49435
	/**<en>
	 * Construct a new <CODE>Bank</CODE>.
	 </en>*/
	public Bank()
	{}

	//#CM49436
	/**<en>
	 * Construct a new <CODE>Bank</CODE>.
	 * This will be used when setting the search result in vector().
	 * @param wnum   :warehouse station no.
	 * @param stno   :aisle station no.
	 * @param bank   :bank no.
	 * @param pbank  :paired bank no.
	 * @param side   :the segment to distinguish the front bank/ rear bank
	 </en>*/
	public Bank
					(
						String			wnum,
						String			stno,
						int 			bank,
						int				pbank,
						int				side
					)
	{
		wWareHouseStationNumber = wnum;
		wAisleStationNumber = stno;
		wBank = bank;
		wPairBank = pbank;
		wSide = side;
	}

	//#CM49437
	/**<en>
	 * Construct a new <CODE>Bank</CODE>.
	 * This will be used when setting the search result in vector().
	 * In this method, set the number of empty locations in the bank.
	 * @param wnum   :warehouse station no.
	 * @param stno   :aisle station no.
	 * @param bank   :bank no.
	 * @param pbank  :paired bank no.
	 * @param side   :the segment to distinguish the front bank/ rear bank
	 * @param cnt    :Number of empty locations in the bank
	 </en>*/
	public Bank
					(
						String			wnum,
						String			stno,
						int 			bank,
						int				pbank,
						int				side,
						int				cnt
					)
	{
		wWareHouseStationNumber = wnum;
		wAisleStationNumber = stno;
		wBank = bank;
		wPairBank = pbank;
		wSide = side;
		wEmptyCount = cnt;
	}

	//#CM49438
	// Public methods ------------------------------------------------
	//#CM49439
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:26 $") ;
	}
	
	//#CM49440
	/**<en>
	 * Set the warehouse no. that this station belongs to.
	 * @param whnum :the warehouse no. that this station belongs to
	 </en>*/
	public void setWareHouseStationNumber(String whnum)
	{
		wWareHouseStationNumber = whnum;
	}

	//#CM49441
	/**<en>
	 * Retrieve the warehouse no. that this station belongs to.
	 * @return :warehouse no.
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return wWareHouseStationNumber;
	}

	//#CM49442
	/**<en>
	 * Set the aisle station no. available for storage/retrieval.
	 * @param anum :the aisle station no. available for storage/retrieval
	 </en>*/
	public void setAisleStationNumber(String anum)
	{
		wAisleStationNumber = anum;
	}

	//#CM49443
	/**<en>
	 * Retrieve the aisle station no. available for storage/retrieval.
	 * @return :the aisle station no. available for storage/retrieval
	 </en>*/
	public String getAisleStationNumber()
	{
		return wAisleStationNumber;
	}

	//#CM49444
	/**<en>
	 * Set the bank no.
	 * @param bank :bank no.
	 </en>*/
	public void setBank(int bank)
	{
		wBank = bank ;
	}

	//#CM49445
	/**<en>
	 * Return the bank no.
	 * @return :bank no.
	 </en>*/
	public int getBank()
	{
		return (wBank) ;
	}

	//#CM49446
	/**<en>
	 * Set the paired bank no.
	 * @param pbank :paired bank no.
	 </en>*/
	public void setPairBank(int pbank)
	{
		wPairBank = pbank ;
	}

	//#CM49447
	/**<en>
	 * Return the paired bank no.
	 * @return :paired bank no.
	 </en>*/
	public int getPairBank()
	{
		return (wPairBank) ;
	}

	//#CM49448
	/**<en>
	 * Set the segment of rear bank/front bank.
	 * @param side :segment of rear bank/front bank
	 </en>*/
	public void setSide(int side)
	{
		wSide = side ;
	}

	//#CM49449
	/**<en>
	 * Return the segment of rear bank/front bank.
	 * @return :segment of rear bank/front bank
	 </en>*/
	public int getSide()
	{
		return (wSide) ;
	}

	//#CM49450
	/**<en>
	 * Set the number of empty locations in the bank.
	 * @param cnt :number of empty locations in the bank
	 </en>*/
	public void setEmptyCount(int cnt)
	{
		wEmptyCount = cnt ;
	}

	//#CM49451
	/**<en>
	 * Returns the number of empty locations in the bank.
	 * @return :number of empty locations in the bank
	 </en>*/
	public int getEmptyCount()
	{
		return (wEmptyCount) ;
	}

	//#CM49452
	/**<en>
	 * Return the string representation.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		buf.append("\nWareHouseStationNumber:" + wWareHouseStationNumber) ;
		buf.append("\nAisleStationNumber:" + wAisleStationNumber) ;
		buf.append("\nPairBank:" + wPairBank) ;
		buf.append("\nBank:" + wBank) ;
		buf.append("\nSide:" + wSide) ;
		buf.append("\nEmptyCount:" + wEmptyCount) ;

		return (buf.toString()) ;
	}

	//#CM49453
	// Package methods -----------------------------------------------

	//#CM49454
	// Protected methods ---------------------------------------------

	//#CM49455
	// Private methods -----------------------------------------------

}
//#CM49456
//end of class



package jp.co.daifuku.wms.base.crossdoc;

//#CM644624
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Parameter;

//#CM644625
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>Shortage</CODE> Class uses information Pending in the CrossDoc package ahead to manage. <BR>
 * <BR>
 * <DIR>
 * < CODE>Shortage</CODE> Class is a maintained item. <BR>
 * <BR>
 *     Unique key of next work information <BR>
 *     Plan unique key <BR>
 *     Receiving Plan unique key <BR>
 *     Storage Plan unique key <BR>
 *     Picking Plan unique key <BR>
 *     Sorting Plan unique key <BR>
 *     Shipping Plan unique key <BR>
 *     Work plan qty <BR>
 *     Work actual qty <BR>
 *     Actual shortage qty <BR>
 *     Pending allocation qty <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/01</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $
 * @author  $Author: suresh $
 */
public class Shortage extends Parameter
{
	//#CM644626
	// Class variables -----------------------------------------------
	//#CM644627
	/**
	 * Unique key of next work information
	 */
	private String wNextProcUkey;

	//#CM644628
	/**
	 * Plan unique key
	 */
	private String wPlanUkey;

	//#CM644629
	/**
	 * Receiving Plan unique key
	 */
	private String wInstockPlanUkey;

	//#CM644630
	/**
	 * Storage Plan unique key
	 */
	private String wStoragePlanUkey;

	//#CM644631
	/**
	 * Picking Plan unique key
	 */
	private String wRetrievalPlanUkey;

	//#CM644632
	/**
	 * Sorting Plan unique key
	 */
	private String wSortingPlanUkey;

	//#CM644633
	/**
	 * Shipping Plan unique key
	 */
	private String wShippingPlanUkey;

	//#CM644634
	/**
	 * Work plan qty
	 */
	private int wPlanQty;

	//#CM644635
	/**
	 * Work actual qty
	 */
	private int wResultQty;

	//#CM644636
	/**
	 * Actual shortage qty
	 */
	private int wShortageQty;

	//#CM644637
	/**
	 * Pending qty
	 */
	private int wInsufficientQty;

	//#CM644638
	// Class method --------------------------------------------------
	//#CM644639
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Date: 2006/11/07 06:11:58 $");
	}

	//#CM644640
	// Constructors --------------------------------------------------
	//#CM644641
	/**
	 * Initialize this Class. 
	 */
	public Shortage()
	{
	}

	//#CM644642
	// Public methods ------------------------------------------------
	//#CM644643
	/**
	 * Set Unique key of next work information. 
	 * @param arg Unique key of next work information to be set
	 */
	public void setNextProcUkey(String arg)
	{
		wNextProcUkey = arg ;
	}

	//#CM644644
	/**
	 * Acquire Unique key of next work information. 
	 * @return Unique key of next work information
	 */
	public String getNextProcUkey()
	{
		return wNextProcUkey ;
	}

	//#CM644645
	/**
	 * Set Plan unique key.
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		wPlanUkey = arg ;
	}

	//#CM644646
	/**
	 * Acquire Plan unique key.
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return wPlanUkey ;
	}

	//#CM644647
	/**
	 * Set Receiving Plan unique key.
	 * @param arg Receiving Plan unique key to be set
	 */
	public void setInstockPlanUkey(String arg)
	{
		wInstockPlanUkey = arg ;
	}

	//#CM644648
	/**
	 * Acquire Receiving Plan unique key.
	 * @return Receiving Plan unique key
	 */
	public String getInstockPlanUkey()
	{
		return wInstockPlanUkey ;
	}

	//#CM644649
	/**
	 * Set Storage Plan unique key.
	 * @param arg Storage Plan unique key to be set
	 */
	public void setStoragePlanUkey(String arg)
	{
		wStoragePlanUkey = arg ;
	}

	//#CM644650
	/**
	 * Acquire Storage Plan unique key.
	 * @return Storage Plan unique key
	 */
	public String getStoragePlanUkey()
	{
		return wStoragePlanUkey ;
	}

	//#CM644651
	/**
	 * Set Picking Plan unique key.
	 * @param arg Picking Plan unique key to be set
	 */
	public void setRetrievalPlanUkey(String arg)
	{
		wRetrievalPlanUkey = arg ;
	}

	//#CM644652
	/**
	 * Acquire Picking Plan unique key.
	 * @return Picking Plan unique key
	 */
	public String getRetrievalPlanUkey()
	{
		return wRetrievalPlanUkey ;
	}

	//#CM644653
	/**
	 * Set Sorting Plan unique key.
	 * @param arg Sorting Plan unique key to be set
	 */
	public void setSortingPlanUkey(String arg)
	{
		wSortingPlanUkey = arg ;
	}

	//#CM644654
	/**
	 * Acquire Sorting Plan unique key.
	 * @return Sorting Plan unique key
	 */
	public String getSortingPlanUkey()
	{
		return wSortingPlanUkey ;
	}

	//#CM644655
	/**
	 * Set Shipping Plan unique key.
	 * @param arg Shipping Plan unique key to be set
	 */
	public void setShippingPlanUkey(String arg)
	{
		wShippingPlanUkey = arg;
	}

	//#CM644656
	/**
	 * Acquire Shipping Plan unique key.
	 * @return Shipping Plan unique key
	 */
	public String getShippingPlanUkey()
	{
		return wShippingPlanUkey;
	}

	//#CM644657
	/**
	 * Set Work plan qty. 
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		wPlanQty = arg ;
	}

	//#CM644658
	/**
	 * Acquire Work plan qty. 
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return wPlanQty;
	}

	//#CM644659
	/**
	 * Set Work actual qty. 
	 * @param arg Work actual qty to be set
	 */
	public void setResultQty(int arg)
	{
		wResultQty = arg ;
	}

	//#CM644660
	/**
	 * Acquire Work actual qty. 
	 * @return Work actual qty
	 */
	public int getResultQty()
	{
		return wResultQty;
	}

	//#CM644661
	/**
	 * Set Actual shortage qty. 
	 * @param arg Actual shortage qty to be set
	 */
	public void setShortageQty(int arg)
	{
		wShortageQty = arg ;
	}

	//#CM644662
	/**
	 * Acquire Actual shortage qty. 
	 * @return Actual shortage qty
	 */
	public int getShortageQty()
	{
		return wShortageQty;
	}

	//#CM644663
	/**
	 * Set Pending allocation qty.
	 * @param arg Pending allocation qty to be Set
	 */
	public void setInsufficientQty(int arg)
	{
		wInsufficientQty = arg ;
	}

	//#CM644664
	/**
	 * Acquire Pending allocation qty. 
	 * @return Pending allocation qty
	 */
	public int getInsufficientQty()
	{
		return wInsufficientQty;
	}
}


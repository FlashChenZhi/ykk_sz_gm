package jp.co.daifuku.wms.base.crossdoc;

//#CM644342
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Parameter;

//#CM644343
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * < CODE>Payment</CODE> Class uses information paying in installments ahead in the CrossDoc package to manage. <BR>
 * <BR>
 * <DIR>
 * < CODE>Payment</CODE> Class is a maintained item. <BR>
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
 *     Completed allocated qty <BR>
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
public class Payment extends Parameter
{
	//#CM644344
	// Class variables -----------------------------------------------
	//#CM644345
	/**
	 * Unique key of next work information
	 */
	private String wNextProcUkey;

	//#CM644346
	/**
	 * Plan unique key
	 */
	private String wPlanUkey;

	//#CM644347
	/**
	 * Receiving Plan unique key
	 */
	private String wInstockPlanUkey;

	//#CM644348
	/**
	 * Storage Plan unique key
	 */
	private String wStoragePlanUkey;

	//#CM644349
	/**
	 * Picking Plan unique key
	 */
	private String wRetrievalPlanUkey;

	//#CM644350
	/**
	 * Sorting Plan unique key
	 */
	private String wSortingPlanUkey;

	//#CM644351
	/**
	 * Shipping Plan unique key
	 */
	private String wShippingPlanUkey;

	//#CM644352
	/**
	 * Work plan qty
	 */
	private int wPlanQty;

	//#CM644353
	/**
	 * Work actual qty
	 */
	private int wResultQty;

	//#CM644354
	/**
	 * Actual shortage qty
	 */
	private int wShortageQty;

	//#CM644355
	/**
	 * Completed discounted qty
	 */
	private int wCompleteQty;

	//#CM644356
	// Class method --------------------------------------------------
	//#CM644357
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Date: 2006/11/07 06:13:47 $");
	}

	//#CM644358
	// Constructors --------------------------------------------------
	//#CM644359
	/**
	 * Initialize this Class. 
	 */
	public Payment()
	{
	}

	//#CM644360
	// Public methods ------------------------------------------------
	//#CM644361
	/**
	 * Set Unique key of next work information. 
	 * @param arg Unique key of next work information to be set
	 */
	public void setNextProcUkey(String arg)
	{
		wNextProcUkey = arg ;
	}

	//#CM644362
	/**
	 * Acquire Unique key of next work information. 
	 * @return Unique key of next work information
	 */
	public String getNextProcUkey()
	{
		return wNextProcUkey ;
	}

	//#CM644363
	/**
	 * Set Plan unique key.
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		wPlanUkey = arg ;
	}

	//#CM644364
	/**
	 * Acquire Plan unique key.
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return wPlanUkey ;
	}

	//#CM644365
	/**
	 * Set Receiving Plan unique key.
	 * @param arg Receiving Plan unique key to be set
	 */
	public void setInstockPlanUkey(String arg)
	{
		wInstockPlanUkey = arg ;
	}

	//#CM644366
	/**
	 * Acquire Receiving Plan unique key.
	 * @return Receiving Plan unique key
	 */
	public String getInstockPlanUkey()
	{
		return wInstockPlanUkey ;
	}

	//#CM644367
	/**
	 * Set Storage Plan unique key.
	 * @param arg Storage Plan unique key to be set
	 */
	public void setStoragePlanUkey(String arg)
	{
		wStoragePlanUkey = arg ;
	}

	//#CM644368
	/**
	 * Acquire Storage Plan unique key.
	 * @return Storage Plan unique key
	 */
	public String getStoragePlanUkey()
	{
		return wStoragePlanUkey ;
	}

	//#CM644369
	/**
	 * Set Picking Plan unique key.
	 * @param arg Picking Plan unique key to be set
	 */
	public void setRetrievalPlanUkey(String arg)
	{
		wRetrievalPlanUkey = arg ;
	}

	//#CM644370
	/**
	 * Acquire Picking Plan unique key.
	 * @return Picking Plan unique key
	 */
	public String getRetrievalPlanUkey()
	{
		return wRetrievalPlanUkey ;
	}

	//#CM644371
	/**
	 * Set Sorting Plan unique key.
	 * @param arg Sorting Plan unique key to be set
	 */
	public void setSortingPlanUkey(String arg)
	{
		wSortingPlanUkey = arg ;
	}

	//#CM644372
	/**
	 * Acquire Sorting Plan unique key.
	 * @return Sorting Plan unique key
	 */
	public String getSortingPlanUkey()
	{
		return wSortingPlanUkey ;
	}

	//#CM644373
	/**
	 * Set Shipping Plan unique key.
	 * @param arg Shipping Plan unique key to be set
	 */
	public void setShippingPlanUkey(String arg)
	{
		wShippingPlanUkey = arg;
	}

	//#CM644374
	/**
	 * Acquire Shipping Plan unique key.
	 * @return Shipping Plan unique key
	 */
	public String getShippingPlanUkey()
	{
		return wShippingPlanUkey;
	}

	//#CM644375
	/**
	 * Set Work plan qty. 
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		wPlanQty = arg ;
	}

	//#CM644376
	/**
	 * Acquire Work plan qty. 
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return wPlanQty;
	}

	//#CM644377
	/**
	 * Set Work actual qty. 
	 * @param arg Work actual qty to be set
	 */
	public void setResultQty(int arg)
	{
		wResultQty = arg ;
	}

	//#CM644378
	/**
	 * Acquire Work actual qty. 
	 * @return Work actual qty
	 */
	public int getResultQty()
	{
		return wResultQty;
	}

	//#CM644379
	/**
	 * Set Actual shortage qty. 
	 * @param arg Actual shortage qty to be set
	 */
	public void setShortageQty(int arg)
	{
		wShortageQty = arg ;
	}

	//#CM644380
	/**
	 * Acquire Actual shortage qty. 
	 * @return Actual shortage qty
	 */
	public int getShortageQty()
	{
		return wShortageQty;
	}

	//#CM644381
	/**
	 * Set Completed allocated qty. 
	 * @param arg Completed allocated qty to be set
	 */
	public void setCompleteQty(int arg)
	{
		wCompleteQty = arg ;
	}

	//#CM644382
	/**
	 * Acquire Completed allocated qty. 
	 * @return Completed allocated qty
	 */
	public int getCompleteQty()
	{
		return wCompleteQty;
	}
}

